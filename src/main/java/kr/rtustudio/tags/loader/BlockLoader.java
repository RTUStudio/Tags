package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.TagType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;

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
    public List<Identifier> getBukkitTag(NamespacedKey key) {
        org.bukkit.Tag<Material> tag = Bukkit.getTag("blocks", key, Material.class);
        if (tag == null) return List.of();
        return tag.getValues().stream()
                .map(Material::getKey)
                .map(Identifier::new)
                .toList();
    }
}
