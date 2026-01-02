package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class Balance implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if ((commandSender.isOp() || commandSender.hasPermission("iteco.balance.others")) && strings.length > 0) {
            double amount = Main.getEconomy().getBalance(strings[0]);
            commandSender.sendMessage("%s has %s.".formatted(
                    strings[0],
                    Config.getCurrencyText((int) amount)
                    ));
        } else {
            if (commandSender instanceof Player player) {
                double amount = Main.getEconomy().getBalance(player);
                commandSender.sendMessage("You have %s.".formatted(
                        Config.getCurrencyText((int) amount)
                ));
            } else {
                commandSender.sendMessage("One must be a player to have a balance.");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if ((sender.isOp() || sender.hasPermission("iteco.balance.others")) && args.length == 1) {
            return Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}
