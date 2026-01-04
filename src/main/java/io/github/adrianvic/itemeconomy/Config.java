package io.github.adrianvic.itemeconomy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class Config {
    private static Map<String, String> entries = new HashMap<>();
    private static UnrealConfig uConf;

    public static void loadConfig(UnrealConfig conf) {
        uConf = conf;
        entries.put("item", "diamond");
        entries.put("format", "{} $");
        entries.put("plural", "diamonds");
        entries.put("singular", "diamond");
        entries.put("ender_chest", "balance");
        entries.put("commands", "true");
        entries.put("player", "&a{}");
        entries.put("localization", "default");
        getAvailableLocales().forEach(l -> entries.put("plural_%s".formatted(l.getLanguage()), entries.get("plural")));
        getAvailableLocales().forEach(l -> entries.put("singular_%s".formatted(l.getLanguage()), entries.get("singular")));

        Map<String, String> missingValues = new HashMap<>();

        for (Map.Entry<String, String> e : entries.entrySet()) {
            String val = (String) conf.get(e.getKey());

            if (val != null) {
                entries.put(e.getKey(), val);
            } else {
                missingValues.put(e.getKey(), e.getValue());
            }
        }

        missingValues.forEach((key, value) -> {
           conf.put(key, value);
           Main.getInstance().getLogger().info("Generating new config entry that was missing: %s: %s".formatted(key, value));
        });
        conf.save();
    }

    public static String get(String entry) {
       return entries.get(entry);
    }

    public static boolean is(String entry, String value) {
       return entries.get(entry).equals(value);
    }

    public static boolean safeIs(String entry, String value) {
        return is(entry.toLowerCase(Locale.ROOT), value.toLowerCase(Locale.ROOT));
    }

    public static String getCurrencyText(int amount, String lang) {
        String plural = entries.get("plural_%s".formatted(lang));
        String singular = entries.get("singular_%s".formatted(lang));

        if (plural == null || singular == null) {
            plural = entries.get("plural");
            singular = entries.get("singular");
        }

        return ChatColor.translateAlternateColorCodes('&', entries.get("format")
                .replace("{}", String.valueOf(amount))
                .replace("$", (amount != 1) ? plural : singular)
        + ChatColor.RESET
        );
    }

    public static String getCurrencyText(int amount, Locale locale) {
        return getCurrencyText(amount, locale.getLanguage());
    }

    public static String getCurrencyText(int amount) {
        return getCurrencyText(amount, getServerLocale().getLanguage());
    }


    public static Locale getServerLocale() {
        Locale locale = Locale.forLanguageTag(entries.get("localization"));
        if (locale.getCountry().isEmpty()) {
            locale = Locale.getDefault();
        }

        return locale;
    }

    public static UnrealConfig getuConf() {
        return uConf;
    }

    public static Material ecoItem() {
        try {
            return Material.valueOf(Config.get("item").toUpperCase());
        } catch (IllegalArgumentException e) {
            Main.getInstance().getLogger().warning("Invalid item was set as economy item, disabling.");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }

        return Material.DIAMOND;
    }

    public static String playerPrefix(String playerName) {
        return ChatColor.translateAlternateColorCodes('&', entries.get("player").replace("{}", playerName)) + ChatColor.RESET;
    }

    public static String playerPrefix(Player player) {
        return playerPrefix(player.getName());
    }

    public static Set<Locale> getAvailableLocales() {
        return Set.of(
                Locale.forLanguageTag("en"),
                Locale.forLanguageTag("pt")
        );
    }
}
