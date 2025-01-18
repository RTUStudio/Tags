package com.github.ipecter.rtustudio.tags.commands;

import com.github.ipecter.rtustudio.tags.Tags;
import kr.rtuserver.framework.bukkit.api.command.RSCommand;
import kr.rtuserver.framework.bukkit.api.command.RSCommandData;

public class MainCommand extends RSCommand<Tags> {

    public MainCommand(Tags plugin) {
        super(plugin, "tags");
    }

    protected void reload(RSCommandData data) {
        getPlugin().initLoader();
    }

}
