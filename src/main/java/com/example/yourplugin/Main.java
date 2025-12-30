package com.example.yourplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("Example plugin is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Example plugin is disabled.");
    }

}
