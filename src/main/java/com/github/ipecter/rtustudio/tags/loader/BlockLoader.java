package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class BlockLoader extends Loader {

    public BlockLoader(Tags plugin) {
        super(plugin);
    }

    @Override
    public TagType getType() {
        return TagType.BLOCK;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        org.bukkit.Tag<Material> tag = Bukkit.getTag("blocks", new NamespacedKey(namespace, key), Material.class);
        return tag != null ? tag.getValues().stream().map(m -> m.getKey().toString()).toList() : List.of();
    }
}
