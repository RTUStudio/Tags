package kr.rtustudio.tags.data;

import java.util.ArrayList;
import java.util.List;

public record Tag(String namespace, String path, List<Tag> tags, List<Identifier> values) {

    public Tag(String namespace, String path) {
        this(namespace, path, new ArrayList<>(), new ArrayList<>());
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
