package io.github.adrianvic.itemeconomy;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Map<String, String> entries = new HashMap<>();
    private static UnrealConfig uConf;

    public static void loadConfig(UnrealConfig conf) {
        uConf = conf;
        entries.put("item", "diamond");
        entries.put("format", "{}$");
        entries.put("plural", "diamonds");
        entries.put("singular", "diamond");
        entries.put("ender_chest", "balance");

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
}
