package com.github.ipecter.rtustudio.tags.tag;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class Tag {

    private final String path;
    private final List<ResourceLocation> values = new ArrayList<>();

    public void add(String id) {
        values.add(new ResourceLocation(id, true));
    }

    public void add(String id, boolean required) {
        values.add(new ResourceLocation(id, required));
    }

}
