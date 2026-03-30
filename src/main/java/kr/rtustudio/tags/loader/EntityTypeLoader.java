package kr.rtustudio.tags.loader;

import kr.rtustudio.tags.Tags;
import kr.rtustudio.tags.data.Identifier;
import kr.rtustudio.tags.data.TagType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
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
    public List<Identifier> getBukkitTag(NamespacedKey key) {
        Tag<EntityType> tag = Bukkit.getTag("entity_types", key, EntityType.class);
        if (tag == null) return List.of();
        return tag.getValues().stream()
                .map(EntityType::getKey)
                .map(Identifier::new)
                .toList();
    }

    @Override
    public boolean isValid(NamespacedKey key) {
        return Registry.ENTITY_TYPE.get(key) != null;
    }
}
