package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import io.github.adrianvic.itemeconomy.Messages;
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
import java.util.Locale;
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
            String amountString = Config.getCurrencyText(amount, Utils.localeOrDefault(commandSender));

            if (commandSender instanceof Player player && Main.getEconomy().has(player, amount)) {
                if (Bukkit.getPlayer(strings[0]) instanceof Player target) {
                    EconomyResponse withdrawResponse = Main.getEconomy().withdrawPlayer(player.getName(), amount);
                    if (withdrawResponse.transactionSuccess()) {
                        EconomyResponse depositResponse = Main.getEconomy().depositPlayer(target.getName(), amount);
                        if (depositResponse.transactionSuccess()) {
                            commandSender.sendMessage(Messages.PAY_SUCCESSFUL.get(
                                    Utils.localeOrDefault(commandSender),
                                    amountString,
                                    Config.playerPrefix(target)));
                            target.sendMessage(Messages.PAY_RECEIVED.get(target, amountString, Config.playerPrefix(player)));
                        } else {
                            commandSender.sendMessage(Messages.PAY_COULD_NOT_REALIZE_TRANSACTION.get(Utils.localeOrDefault(commandSender), depositResponse.errorMessage));
                            Main.getEconomy().depositPlayer(player.getName(), amount);
                        }
                    } else {
                        commandSender.sendMessage(Messages.PAY_COULD_NOT_REALIZE_TRANSACTION.get(Utils.localeOrDefault(commandSender), withdrawResponse.errorMessage));;
                    }
                } else {
                    commandSender.sendMessage(Messages.PAY_COULD_NOT_FIND_TARGET.get(Utils.localeOrDefault(commandSender)));
                }
            } else {
                commandSender.sendMessage(Messages.PAY_NOT_ENOUGH_MONEY.get(Utils.localeOrDefault(commandSender)));
            }
        } catch (NumberFormatException nfe) {
            commandSender.sendMessage(Messages.PAY_INVALID_AMOUNT.get(Utils.localeOrDefault(commandSender)));
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
