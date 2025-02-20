package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.ResourceLocation;
import com.github.ipecter.rtustudio.tags.tag.Tag;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import com.github.ipecter.rtustudio.tags.util.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import kr.rtuserver.framework.bukkit.api.utility.platform.FileResource;
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
    private final Map<String, List<ResourceLocation>> tags = new HashMap<>();
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
    public List<ResourceLocation> get(@Nullable NamespacedKey namespacedKey) {
        if (namespacedKey == null) return List.of();
        if (namespacedKey.getNamespace().equalsIgnoreCase("minecraft")) {
            return getBukkitTag(namespacedKey.getNamespace(), namespacedKey.getKey()).stream().map(s -> new ResourceLocation(s, true)).toList();
        } else return tags.getOrDefault(namespacedKey.toString(), List.of());
    }

    void load(File file) {
        Map<String, JsonObject> json = getJson(file);
        if (json == null) return;

        List<Tag> tags = new ArrayList<>();
        for (String key : json.keySet()) {
            JsonElement element = json.get(key).get("values");
            if (element == null || element.isJsonNull()) continue;
            if (!element.isJsonArray()) continue;

            Tag tag = getTag(key, element);
            tags.add(tag);
        }
        String namespace = com.google.common.io.Files.getNameWithoutExtension(file.getName());
        cache.put(namespace, tags);
        for (Tag tag : tags) {
            List<ResourceLocation> data = loadTag(tag.getPath(), tag.getValues());
            this.tags.put(namespace + ":" + tag.getPath(), data);
        }
        cache.clear();
    }

    private @NotNull Tag getTag(String key, JsonElement element) {
        Tag tag = new Tag(key);
        for (JsonElement value : element.getAsJsonArray()) {
            if (value.isJsonPrimitive()) tag.add(value.getAsString());
            else if (value.isJsonObject()) {
                JsonObject object = value.getAsJsonObject();

                JsonPrimitive id = object.getAsJsonPrimitive("id");
                JsonPrimitive required = object.getAsJsonPrimitive("required");

                if (id == null) continue;
                tag.add(id.getAsString(), required == null || required.getAsBoolean());
            }
        }
        return tag;
    }

    private List<ResourceLocation> loadTag(String path, List<ResourceLocation> list) {
        List<ResourceLocation> result = new ArrayList<>();
        for (ResourceLocation data : list) {
            if (data.id().startsWith("#")) {
                String namespacedKey = data.id().substring(1);
                String namespace;
                String key;
                if (namespacedKey.contains(":")) {
                    String[] split = namespacedKey.split(":");
                    namespace = split[0];
                    key = split[1];
                } else {
                    namespace = "minecraft";
                    key = namespacedKey;
                }

                List<Tag> tags = cache.getOrDefault(namespace, List.of());
                if (namespace.equalsIgnoreCase("minecraft")) {
                    for (String id : getBukkitTag(namespace, key)) {
                        result.add(new ResourceLocation(id, true));
                    }
                }
                if (tags.isEmpty()) continue;
                for (Tag tag : tags) {
                    String tPath = tag.getPath();
                    if (tPath.equalsIgnoreCase(path)) continue;
                    if (tag.getPath().equalsIgnoreCase(key)) {
                        result.addAll(loadTag(path, tag.getValues()));
                        break;
                    }
                }
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

    public abstract List<String> getBukkitTag(String namespace, String key);

}
