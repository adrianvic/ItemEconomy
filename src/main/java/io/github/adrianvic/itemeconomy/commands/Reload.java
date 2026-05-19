package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import io.github.adrianvic.itemeconomy.Messages;
import io.github.adrianvic.itemeconomy.UnrealConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Reload implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        try {
            commandSender.sendMessage("[ECONOMY] %s".formatted(Messages.RELOADING));
            Config.loadConfig(new UnrealConfig(Main.getInstance(), Main.getInstance().getDataFolder(), "config.yml"));
            commandSender.sendMessage("[ECONOMY] %s".formatted(Messages.RELOAD_FINISHED));
            return true;
        } catch (Exception e) {
            commandSender.sendMessage("[ECONOMY] %s".formatted(Messages.RELOAD_ERROR));
            return false;
        }
    }
}
