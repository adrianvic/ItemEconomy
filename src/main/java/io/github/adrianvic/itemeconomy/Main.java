package io.github.adrianvic.itemeconomy;

import java.util.*;

import io.github.adrianvic.itemeconomy.commands.Reload;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
   private static Main instance;

   public void onEnable() {
      instance = this;
      Config.loadConfig(new UnrealConfig(this, this.getDataFolder(), "config.yml"));
      Bukkit.getServicesManager().register(Economy.class, new VaultLayer(), this, ServicePriority.High);
      getCommand("itecoreload").setExecutor(new Reload());
   }

   public static JavaPlugin getInstance() {
      return instance;
   }

   public void onDisable() {
      super.onDisable();
   }

   public enum InventoryID {
      INVENTORY,
      ENDER_CHEST
   }

   public static Inventory getInventory(Player player, InventoryID inventory) {
      Inventory inv = player.getInventory();

      switch (inventory) {
         case INVENTORY -> {
            inv = player.getInventory();
         }
         case ENDER_CHEST -> {
            if (Config.is("ender_chest", "balance")) {
               inv = player.getEnderChest();
            } else {
               inv = getInstance().getServer().createInventory(null, 9);
            }
         }
      }

      return inv;
   }

   public static List<ItemStack> getInventoryList(Player player, InventoryID inventory) {
      Inventory inv = getInventory(player, inventory);
      return Arrays.stream(inv.getContents()).map((o) -> o == null ? new ItemStack(Material.AIR) : o).toList();
   }

   public static List<ItemStack> getInventoryList(Player player) {
      return getInventoryList(player, InventoryID.INVENTORY);
   }

   public static double getBalance(Player player, InventoryID inventory) {
      return (double) getInventoryList(player, inventory).stream().filter(Objects::nonNull).filter((i) -> {
         return i.getType().equals(Config.ecoItem());
      }).mapToInt(ItemStack::getAmount).sum();
   }

   public static double getBalance(Player player) {
      Double total = 0.0D;

      for (InventoryID id : InventoryID.values()) {
         total += getBalance(player, id);
      }

      return total;
   }

   public static double getBalance(String player) {
      return getBalance(Bukkit.getPlayer(player));
   }

   public static boolean removeItems(Player player, Material type, int amount) {
      int remaining = amount;

      remaining = removeFrom(player.getInventory(), type, remaining);
      if (remaining > 0) {
         remaining = removeFrom(player.getEnderChest(), type, remaining);
      }

      return remaining == 0;
   }

   private static int removeFrom(Inventory inv, Material type, int amount) {
      if (amount <= 0) return 0;

      for (ItemStack stack : inv.all(type).values()) {
         int take = Math.min(stack.getAmount(), amount);
         stack.setAmount(stack.getAmount() - take);
         amount -= take;
         if (amount == 0) break;
      }

      return amount;
   }


   public static void addItems(Player player, Material type, int amount) {
      HashMap<Integer, ItemStack> invOverflow = getInventory(player, InventoryID.INVENTORY).addItem(new ItemStack(type, amount));
      HashMap<Integer, ItemStack> echestOverflow = getInventory(player, InventoryID.ENDER_CHEST).addItem(new ItemStack(type, invOverflow.values()
              .stream()
              .mapToInt(ItemStack::getAmount)
              .sum()));


      for (ItemStack overflow : echestOverflow.values()){
         player.getWorld().dropItemNaturally(player.getLocation(), overflow);
      }
   }
}
