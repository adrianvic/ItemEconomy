package io.github.adrianvic.itemeconomy;

import org.bukkit.Material;

public class Config {
   public static Material ITEM;
   public static String FORMAT;
   public static String PLURAL;
   public static String SINGULAR;

   public static void loadConfig(UnrealConfig conf) {
      ITEM = Material.valueOf(((String)conf.get("item")).toUpperCase());
      FORMAT = (String)conf.get("format");
      PLURAL = (String)conf.get("plural");
      SINGULAR = (String)conf.get("singular");
   }
}
