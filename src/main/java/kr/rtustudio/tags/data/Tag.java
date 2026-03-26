package kr.rtustudio.tags.data;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public record Tag(String namespace, String path, List<Tag> tags, List<Identifier> values) {

    public Tag(String namespace, String path) {
        this(namespace, path, new ObjectArrayList<>(), new ObjectArrayList<>());
    }

    public Tag(String path) {
        this("minecraft", path);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addValue(Identifier identifier) {
        values.add(identifier);
    }
}
