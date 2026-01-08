package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.TagType;
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
    public List<Identifier> getBukkitTag(NamespacedKey key) {
        return getPlugin()
                .getFramework()
                .getNMS()
                .getBiome()
                .getTag(key)
                .stream()
                .map(Identifier::new)
                .toList();
    }
}
