package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.TagType;
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
    public List<Identifier> getBukkitTag(NamespacedKey key) {
        org.bukkit.Tag<Fluid> tag = Bukkit.getTag("fluids", key, Fluid.class);
        if (tag == null) return List.of();
        return tag.getValues().stream()
                .map(Fluid::getKey)
                .map(Identifier::new)
                .toList();
    }
}
