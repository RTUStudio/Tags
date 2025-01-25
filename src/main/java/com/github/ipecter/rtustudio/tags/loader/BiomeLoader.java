package com.github.ipecter.rtustudio.tags.loader;

import com.github.ipecter.rtustudio.tags.Tags;
import com.github.ipecter.rtustudio.tags.tag.TagType;
import kr.rtuserver.cdi.LightDI;
import kr.rtuserver.framework.bukkit.api.core.Framework;
import org.bukkit.NamespacedKey;

import java.util.List;

public class BiomeLoader extends Loader {

    private final Framework framework;

    public BiomeLoader(Tags plugin) {
        super(plugin);
        framework = plugin.getFramework();
    }

    @Override
    public TagType getType() {
        return TagType.BIOME;
    }

    @Override
    public List<String> getBukkitTag(String namespace, String key) {
        return framework.getNMS().biome().getBiomeTag(new NamespacedKey(namespace, key).asString());
    }
}
