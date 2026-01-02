package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import net.milkbowl.vault.economy.EconomyResponse;
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

public class Pay implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length < 2) {
            commandSender.sendMessage(command.getUsage());
            return false;
        }

        int amount;

        try {
            amount = Integer.parseInt(strings[1]);
            String amountString = Config.getCurrencyText(amount);

            if (commandSender instanceof Player player && Main.getEconomy().has(player, amount)) {
                if (Bukkit.getPlayer(strings[0]) instanceof Player target) {
                    EconomyResponse withdrawResponse = Main.getEconomy().withdrawPlayer(player.getName(), amount);
                    if (withdrawResponse.transactionSuccess()) {
                        EconomyResponse depositResponse = Main.getEconomy().depositPlayer(target.getName(), amount);
                        if (depositResponse.transactionSuccess()) {
                            commandSender.sendMessage("Transaction of %s to %s was successfully realized.".formatted(amountString, target.getName()));
                            target.sendMessage("You received %s from %s.".formatted(amountString, player.getName()));
                        } else {
                            commandSender.sendMessage("Could not realize transaction: %s".formatted(depositResponse.errorMessage));
                            Main.getEconomy().depositPlayer(player.getName(), amount);
                        }
                    } else {
                        commandSender.sendMessage("Could not realize transaction: %s".formatted(withdrawResponse.errorMessage));
                    }
                } else {
                    commandSender.sendMessage("Could not find target player.");
                }
            } else {
                commandSender.sendMessage("You don't have enough money.");
            }
        } catch (NumberFormatException nfe) {
            commandSender.sendMessage("The amount you tried to pay is not valid.");
            return true;
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                return Bukkit.getOnlinePlayers()
                        .stream()
                        .map(Player::getName)
                        .collect(Collectors.toList());
            }
        }

        return List.of();
    }
}
