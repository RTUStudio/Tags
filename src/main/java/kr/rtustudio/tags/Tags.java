package kr.rtustudio.tags;

import kr.rtustudio.framework.bukkit.api.RSPlugin;
import kr.rtustudio.tags.command.MainCommand;
import kr.rtustudio.tags.loader.BiomeLoader;
import kr.rtustudio.tags.loader.BlockLoader;
import kr.rtustudio.tags.loader.EntityTypeLoader;
import kr.rtustudio.tags.loader.FluidLoader;
import kr.rtustudio.tags.loader.ItemLoader;
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
