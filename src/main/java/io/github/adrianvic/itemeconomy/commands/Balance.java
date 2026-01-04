package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import io.github.adrianvic.itemeconomy.Messages;
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
            commandSender.sendMessage(Messages.BALANCE_OTHER_SUCCESSFUL.get(
                    Utils.localeOrDefault(commandSender),
                    Config.playerPrefix(strings[0]),
                    Config.getCurrencyText((int) amount, Utils.localeOrDefault(commandSender))
            ));
        } else {
            if (commandSender instanceof Player player) {
                double amount = Main.getEconomy().getBalance(player);
                commandSender.sendMessage(Messages.BALANCE_SUCCESSFUL.get(player, Config.getCurrencyText((int) amount, Utils.localeOrDefault(commandSender))));
            } else {
                commandSender.sendMessage(Messages.MUST_BE_PLAYER_TO_ISSUE_COMMAND.get());
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
