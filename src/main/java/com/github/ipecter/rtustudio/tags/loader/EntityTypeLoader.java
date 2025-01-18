package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

import java.util.List;

public class EntityTypeLoader extends Loader {

    public EntityTypeLoader(Tags plugin) {
        super(plugin);
    }

    @Override
    public TagType getType() {
        return TagType.ENTITY_TYPE;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        org.bukkit.Tag<EntityType> tag = Bukkit.getTag("entity_types", new NamespacedKey(namespace, key), EntityType.class);
        return tag != null ? tag.getValues().stream().map(m -> m.getKey().toString()).toList() : List.of();
    }
}
