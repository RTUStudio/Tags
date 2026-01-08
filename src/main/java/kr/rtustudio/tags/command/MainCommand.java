package kr.rtustudio.tags.command;

import kr.rtustudio.framework.bukkit.api.command.RSCommand;
import kr.rtustudio.framework.bukkit.api.command.RSCommandData;
import kr.rtustudio.tags.Tags;

public class MainCommand extends RSCommand<Tags> {

    public MainCommand(Tags plugin) {
        super(plugin, "tags");
    }

    @Override
    protected void reload(RSCommandData data) {
        getPlugin().initLoader();
    }
}
