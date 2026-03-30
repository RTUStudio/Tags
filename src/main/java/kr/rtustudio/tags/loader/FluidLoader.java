package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.TagType;
import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;

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
        Tag<Fluid> tag = Bukkit.getTag("fluids", key, Fluid.class);
        if (tag == null) return List.of();
        return tag.getValues().stream()
                .map(Fluid::getKey)
                .map(Identifier::new)
                .toList();
    }

    @Override
    public boolean isValid(NamespacedKey key) {
        return Registry.FLUID.get(key) != null;
    }
}
