package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.NamespacedKey;

import java.util.List;

public class FluidLoader extends Loader {

    public FluidLoader(Tags plugin) {
        super(plugin);
    }

    @Override
    public TagType getType() {
        return TagType.FLUID;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        org.bukkit.Tag<Fluid> tag = Bukkit.getTag("fluids", new NamespacedKey(namespace, key), Fluid.class);
        return tag != null ? tag.getValues().stream().map(m -> m.getKey().toString()).toList() : List.of();
    }
}
