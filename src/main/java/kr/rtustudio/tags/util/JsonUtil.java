package kr.rtustudio.tags.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonUtil {

    private static final Gson GSON = new Gson();

    @Nullable
    public static JsonObject toJson(File file) {
        try {
            JsonReader reader = new JsonReader(new FileReader(file));
            return GSON.fromJson(reader, JsonObject.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

}
