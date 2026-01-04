package io.github.adrianvic.itemeconomy;

import io.github.adrianvic.itemeconomy.commands.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Messages {
    ENABLING("enabling"),
    BALANCE_SUCCESSFUL("balance-successful"),
    BALANCE_OTHER_SUCCESSFUL("balance-other-successful"),
    MUST_BE_PLAYER_TO_ISSUE_COMMAND("must-be-player-to-issue-command"),
    PAY_SUCCESSFUL("pay-successful"),
    PAY_RECEIVED("pay-received"),
    PAY_COULD_NOT_REALIZE_TRANSACTION("pay-could-not-realize-transaction"),
    PAY_COULD_NOT_FIND_TARGET("pay-could-not-find-target"),
    PAY_NOT_ENOUGH_MONEY("pay-not-enough-money"),
    PAY_INVALID_AMOUNT("pay-invalid-amount");

    private final String path;
    Messages(String path) { this.path = path; }

    private String raw(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        return bundle.getString(path);
    }

    public String get(Locale locale, Object... args) {
        String pattern = raw(locale);
        try {
            return ChatColor.translateAlternateColorCodes('&', args.length == 0 ? pattern : String.format(pattern, args));
        } catch (Exception e) {
            return pattern;
        }
    }

    public String get(Player player, Object... args) {
        Locale locale = Utils.localeOrDefault(player);
        return get(locale, args);
    }

    public String get(Object... args) {
        return get(Config.getServerLocale(), args);
    }
}
