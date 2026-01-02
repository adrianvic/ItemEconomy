package io.github.adrianvic.itemeconomy;

import java.util.List;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultLayer implements Economy {
   public boolean isEnabled() {
      return true;
   }

   public String getName() {
      return "ItemEconomy II";
   }

   public boolean hasBankSupport() {
      return false;
   }

   public int fractionalDigits() {
      return 0;
   }

   public String format(double amount) {
      return Config.getCurrencyText((int) amount);
   }

   public String currencyNamePlural() {
      return Config.get("plural");
   }

   public String currencyNameSingular() {
      return Config.get("singular");
   }

   public boolean hasAccount(String playerName) {
      return Bukkit.getPlayer(playerName) != null;
   }

   public double getBalance(String playerName) {
      return Main.getBalance(playerName);
   }

   public boolean has(String playerName, double amount) {
      return Main.getBalance(playerName) >= amount;
   }

   public EconomyResponse withdrawPlayer(String playerName, double amount) {
      if (amount == 0.0D) {
         return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
      } else if (amount < 0.0D) {
         return this.depositPlayer(playerName, -amount);
      } else if (!this.has(playerName, amount)) {
         return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.FAILURE, "Insufficient founds.");
      } else {
         Player player;
         if ((player = Bukkit.getPlayer(playerName)) == null) {
            return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.FAILURE, "This player is offline.");
         } else {
            return !Main.removeItems(player, Config.ecoItem(), (int)amount) ? new EconomyResponse(amount, this.getBalance(playerName), ResponseType.FAILURE, "Insufficient founds.") : new EconomyResponse(amount, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
         }
      }
   }

   public EconomyResponse depositPlayer(String playerName, double amount) {
      if (amount == 0.0D) {
         return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
      } else if (amount < 0.0D) {
         return this.withdrawPlayer(playerName, -amount);
      } else {
         Player player;
         if ((player = Bukkit.getPlayer(playerName)) == null) {
            return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.FAILURE, "This player is offline.");
         } else {
            Main.addItems(player, Config.ecoItem(), (int)amount);
            return new EconomyResponse(amount, this.getBalance(playerName), ResponseType.SUCCESS, (String)null);
         }
      }
   }

   public boolean hasAccount(OfflinePlayer player) {
      return this.hasAccount(player.getName());
   }

   public boolean hasAccount(String playerName, String worldName) {
      return this.hasAccount(playerName);
   }

   public boolean hasAccount(OfflinePlayer player, String worldName) {
      return this.hasAccount(player.getName());
   }

   public double getBalance(OfflinePlayer player) {
      return this.getBalance(player.getName());
   }

   public double getBalance(String playerName, String world) {
      return this.getBalance(playerName);
   }

   public double getBalance(OfflinePlayer player, String world) {
      return this.getBalance(player.getName());
   }

   public boolean has(OfflinePlayer player, double amount) {
      return this.has(player.getName(), amount);
   }

   public boolean has(String playerName, String worldName, double amount) {
      return this.has(playerName, amount);
   }

   public boolean has(OfflinePlayer player, String worldName, double amount) {
      return this.has(player.getName(), amount);
   }

   public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
      return this.withdrawPlayer(player.getName(), amount);
   }

   public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
      return this.withdrawPlayer(playerName, amount);
   }

   public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
      return this.withdrawPlayer(player.getName(), amount);
   }

   public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
      return this.depositPlayer(player.getName(), amount);
   }

   public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
      return this.depositPlayer(playerName, amount);
   }

   public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
      return this.depositPlayer(player.getName(), amount);
   }

   public EconomyResponse createBank(String name, String player) {
      return null;
   }

   public EconomyResponse createBank(String name, OfflinePlayer player) {
      return null;
   }

   public EconomyResponse deleteBank(String name) {
      return null;
   }

   public EconomyResponse bankBalance(String name) {
      return null;
   }

   public EconomyResponse bankHas(String name, double amount) {
      return null;
   }

   public EconomyResponse bankWithdraw(String name, double amount) {
      return null;
   }

   public EconomyResponse bankDeposit(String name, double amount) {
      return null;
   }

   public EconomyResponse isBankOwner(String name, String playerName) {
      return null;
   }

   public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
      return null;
   }

   public EconomyResponse isBankMember(String name, String playerName) {
      return null;
   }

   public EconomyResponse isBankMember(String name, OfflinePlayer player) {
      return null;
   }

   public List<String> getBanks() {
      return List.of();
   }

   public boolean createPlayerAccount(String playerName) {
      return false;
   }

   public boolean createPlayerAccount(OfflinePlayer player) {
      return false;
   }

   public boolean createPlayerAccount(String playerName, String worldName) {
      return false;
   }

   public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
      return false;
   }
}
