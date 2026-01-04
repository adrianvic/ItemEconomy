package io.github.adrianvic.itemeconomy.commands;

import io.github.adrianvic.itemeconomy.Config;
import io.github.adrianvic.itemeconomy.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Utils {
    public static Locale localeOrDefault(CommandSender sender) {
        return (sender instanceof Player p) ? Locale.forLanguageTag(p.getLocale().replace('_', '-')) : Config.getServerLocale();
    }

}
