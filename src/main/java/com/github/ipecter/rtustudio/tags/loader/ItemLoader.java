package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class ItemLoader extends Loader {

    public ItemLoader(Tags plugin) {
        super(plugin);
    }

    @Override
    public TagType getType() {
        return TagType.ITEM;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        org.bukkit.Tag<Material> tag = Bukkit.getTag("items", new NamespacedKey(namespace, key), Material.class);
        return tag != null ? tag.getValues().stream().map(m -> m.getKey().toString()).toList() : List.of();
    }
}
