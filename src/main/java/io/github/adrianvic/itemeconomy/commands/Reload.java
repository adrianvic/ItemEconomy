package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import io.github.adrianvic.itemeconomy.UnrealConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        try {
            Config.loadConfig(new UnrealConfig(Main.getInstance(), Main.getInstance().getDataFolder(), "config.yml"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
