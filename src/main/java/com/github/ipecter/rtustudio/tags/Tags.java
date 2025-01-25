package com.github.ipecter.rtustudio.tags;

import com.github.ipecter.rtustudio.tags.commands.MainCommand;
import com.github.ipecter.rtustudio.tags.loader.*;
import kr.rtuserver.cdi.LightDI;
import kr.rtuserver.framework.bukkit.api.RSPlugin;
import kr.rtuserver.framework.bukkit.api.core.Framework;
import lombok.Getter;

@Getter
public class Tags extends RSPlugin {

    @Getter
    private static Tags instance;

    private BiomeLoader biome;
    private BlockLoader block;
    private EntityTypeLoader entityType;
    private FluidLoader fluid;
    private ItemLoader item;


    @Override
    public void enable() {
        instance = this;
        initLoader();
        registerCommand(new MainCommand(this), true);

    }

    public void initLoader() {
        biome = new BiomeLoader(this);
        block = new BlockLoader(this);
        entityType = new EntityTypeLoader(this);
        fluid = new FluidLoader(this);
        item = new ItemLoader(this);
    }

}
