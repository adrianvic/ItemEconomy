package io.github.adrianvic.itemeconomy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   public void onEnable() {
      Config.loadConfig(new UnrealConfig(this, this.getDataFolder(), "config.yml"));
      Bukkit.getServicesManager().register(Economy.class, new VaultLayer(), this, ServicePriority.High);
   }

   public void onDisable() {
      super.onDisable();
   }

   public static List<ItemStack> getInventory(Player player) {
      return Arrays.stream(player.getInventory().getContents()).map((o) -> {
         return o == null ? new ItemStack(Material.AIR) : o;
      }).toList();
   }

   public static boolean removeItems(Player player, Material type, int amount) {
      if (player.getInventory().all(type).values().stream().mapToInt(ItemStack::getAmount).sum() < amount) {
         return false;
      } else {
         player.getInventory().removeItem(new ItemStack[]{new ItemStack(type, amount)});
         return true;
      }
   }

   public static void addItems(Player player, Material type, int amount) {
      HashMap<Integer, ItemStack> nope = player.getInventory().addItem(new ItemStack[]{new ItemStack(type, amount)});
      Iterator var4 = nope.values().iterator();

      while(var4.hasNext()) {
         ItemStack v = (ItemStack)var4.next();
         player.getWorld().dropItemNaturally(player.getLocation(), v);
      }

   }
}
