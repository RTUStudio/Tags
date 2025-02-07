package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import kr.rtuserver.cdi.LightDI;
import kr.rtuserver.framework.bukkit.api.core.Framework;
import org.bukkit.NamespacedKey;

import java.util.List;

public class BiomeLoader extends Loader {

    public BiomeLoader(Tags plugin) {
        super(plugin);
    }

    @Override
    public TagType getType() {
        return TagType.BIOME;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        return getPlugin().getFramework().getNMS().biome().getTag(new NamespacedKey(namespace, key).asString());
    }
}
