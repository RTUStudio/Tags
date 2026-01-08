package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.Tag;
import kr.rtustudio.tags.data.TagType;
import kr.rtustudio.tags.util.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.rtustudio.framework.bukkit.api.platform.FileResource;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class Loader {

    @Getter
    private final Tags plugin;

    @Getter
    private final Map<String, List<Identifier>> tags = new HashMap<>();
    private final Map<String, List<Tag>> cache = new HashMap<>();

    public Loader(Tags plugin) {
        this.plugin = plugin;
        File folder = FileResource.createFolder(plugin.getDataFolder() + "/Tags");
        File[] files = folder.listFiles();
        if (files == null) return;
        for (File file : files) {
            if (file.isDirectory()) load(file);
        }
    }

    @NotNull
    public List<Identifier> get(@Nullable Identifier identifier) {
        if (identifier == null) return List.of();
        if (identifier.isTag()) {
            String namespace = identifier.namespace().substring(1);
            String key = identifier.path();
            if (namespace.equalsIgnoreCase("minecraft")) {
                return getBukkitTag(new NamespacedKey(namespace, key));
            }
            return tags.getOrDefault(identifier.asString(), List.of());
        }
        return tags.getOrDefault(identifier.asString(), List.of());
    }

    void load(File file) {
        Map<String, JsonObject> json = getJson(file);
        if (json == null) return;

        List<Tag> tags = new ArrayList<>();
        String namespace = com.google.common.io.Files.getNameWithoutExtension(file.getName());
        for (String key : json.keySet()) {
            JsonElement element = json.get(key).get("values");
            if (element == null || element.isJsonNull()) continue;
            if (!element.isJsonArray()) continue;

            Tag tag = getTag(namespace, key, element);
            tags.add(tag);
        }
        cache.put(namespace, tags);
        for (Tag tag : tags) {
            List<Identifier> data = loadTag(tag.path(), tag.values());
            this.tags.put(namespace + ":" + tag.path(), data);
        }
        cache.clear();
    }

    private @NotNull Tag getTag(String namespace, String key, JsonElement element) {
        Tag tag = new Tag(namespace, key);
        for (JsonElement value : element.getAsJsonArray()) {
            if (value.isJsonPrimitive()) {
                Identifier parsed = parseIdentifier(value.getAsString(), true);
                if (parsed != null) tag.addValue(parsed);
            } else if (value.isJsonObject()) {
                JsonObject object = value.getAsJsonObject();

                JsonPrimitive id = object.getAsJsonPrimitive("id");
                JsonPrimitive required = object.getAsJsonPrimitive("required");

                if (id == null) continue;
                Identifier parsed =
                        parseIdentifier(id.getAsString(), required == null || required.getAsBoolean());
                if (parsed != null) tag.addValue(parsed);
            }
        }
        return tag;
    }

    private List<Identifier> loadTag(String identifier, List<Identifier> list) {
        List<Identifier> result = new ArrayList<>();
        for (Identifier data : list) {
            if (data.isTag()) {
                String namespace = data.namespace().substring(1);
                String key = data.path();

                if (namespace.equalsIgnoreCase("minecraft")) {
                    result.addAll(getBukkitTag(new NamespacedKey(namespace, key)));
                    continue;
                }

                List<Tag> tags = cache.getOrDefault(namespace, List.of());
                if (tags.isEmpty()) {
                    result.add(data);
                    continue;
                }

                boolean expanded = false;
                for (Tag tag : tags) {
                    if (tag.path().equalsIgnoreCase(identifier)) continue;
                    if (tag.path().equalsIgnoreCase(key)) {
                        result.addAll(loadTag(identifier, tag.values()));
                        expanded = true;
                        break;
                    }
                }
                if (!expanded) result.add(data);
            } else {
                result.add(data);
            }
        }
        return result;
    }

    private Map<String, JsonObject> getJson(File file) {
        Map<String, JsonObject> result = new HashMap<>();

        File folder = FileResource.createFolder(file + "/" + getType().name().toLowerCase());

        try (Stream<Path> stream = Files.walk(folder.toPath())) {
            List<Path> list = stream.filter(Files::isRegularFile).toList();
            for (Path path : list) {
                String name = com.google.common.io.Files.getNameWithoutExtension(folder.toPath().relativize(path).toString());
                JsonObject json = JsonUtil.toJson(path.toFile());
                if (json != null) result.put(name, json);
            }
        } catch (IOException e) {
            //TODO: 알림
            return null;
        }
        return result;
    }

    public abstract TagType getType();

    public abstract List<Identifier> getBukkitTag(NamespacedKey key);

    private Identifier parseIdentifier(String raw, boolean required) {
        boolean tagRef = raw.startsWith("#");
        String value = tagRef ? raw.substring(1) : raw;
        NamespacedKey key = NamespacedKey.fromString(value);
        if (key == null) return null;
        String namespace = tagRef ? "#" + key.getNamespace() : key.getNamespace();
        return new Identifier(namespace, key.getKey(), required);
    }

}
