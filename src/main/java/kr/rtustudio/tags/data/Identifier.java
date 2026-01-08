package kr.rtustudio.tags.data;

import org.bukkit.NamespacedKey;

import javax.annotation.Nullable;

public record Identifier(String namespace, String path, boolean required) {

    public Identifier(String namespace, String path) {
        this(namespace, path, true);
    }

    public Identifier(NamespacedKey key) {
        this(key.getNamespace(), key.getKey());
    }

    public boolean isTag() {
        return namespace.startsWith("#");
    }

    @Nullable
    public NamespacedKey toBukkit() {
        if (isTag()) return null;
        return new NamespacedKey(namespace, path);
    }

    public String asString() {
        return namespace + ":" + path;
    }
}
