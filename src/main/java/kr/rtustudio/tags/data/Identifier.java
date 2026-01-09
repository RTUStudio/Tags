package kr.rtustudio.tags.data;

import com.google.common.base.Preconditions;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public record Identifier(String namespace, String path, boolean required) {

    public Identifier(String namespace, String path) {
        this(namespace, path, true);
    }

    public Identifier(NamespacedKey key) {
        this(key.getNamespace(), key.getKey());
    }

    @Nullable
    public static Identifier fromString(@NotNull String string) {
        String[] parts = string.split(":", 3);
        if (parts.length > 2) return null;

        String namespace = parts[0];
        String path = parts.length == 2 ? parts[1] : "";

        if (parts.length == 1) {
            if (!isValidKey(namespace)) return null;
            return new Identifier("minecraft", namespace);
        }

        if (!isValidKey(path)) return null;
        if (namespace.isEmpty()) return new Identifier("minecraft", path);
        if (!isValidNamespace(namespace)) return null;

        return new Identifier(namespace, path);
    }

    private static boolean isValidNamespaceChar(char c) {
        return (c >= 'a' && c <= 'z')
                || (c >= '0' && c <= '9')
                || c == '.' || c == '_' || c == '-';
    }

    private static boolean isValidKeyChar(char c) {
        return isValidNamespaceChar(c) || c == '/';
    }

    private static boolean isValidNamespace(String namespace) {
        if (namespace.isEmpty()) return false;
        for (int i = 0; i < namespace.length(); i++) {
            if (!isValidNamespaceChar(namespace.charAt(i))) return false;
        }
        return true;
    }

    private static boolean isValidKey(String key) {
        if (key.isEmpty()) return false;
        for (int i = 0; i < key.length(); i++) {
            if (!isValidKeyChar(key.charAt(i))) return false;
        }
        return true;
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
