# ItemEconomy II
<p align="center">
<img alt="image" align="center" src="https://github.com/user-attachments/assets/06b7c885-e11d-415c-b45c-bea51f7e2cb7" />
</p>

**ItemEconomy II** is a fork of [ItemEconomy](https://modrinth.com/plugin/itemeconomy), keeping it updated to later versions of Minecraft and adding new features.

This PaperMC plugin integrates with VaultUnlocked to provide a unique, item-based economy system for your Minecraft server. Instead of relying solely on virtual balances, players use in-game items as physical currency, adding a layer of immersion and realism to your economy.

ItemEconomy is as powerful as you need and as simple as you want, with every feature being optional.

## Features
- **Item-Based Currency:** Set any Minecraft item as your server's currency (diamonds by default).
- **VaultUnlocked Integration:** Fully compatible with VaultUnlocked, enabling seamless interaction with other economy-based plugins.
- **Simple logic:** Just checks if the user has the item/how many when queried.
- **Customizable Formatting:** Define how your currency is displayed, including singular and plural forms.
- **Ender Chest support:** Items on Ender Chests are counted in the user balance.
- **Built-int optional balance and pay commands** with support for permissions.
- **Translation support** with per-user language and per-language currency name.

## Configuration
An updated example configuration file is available [here](https://github.com/adrianvic/ItemEconomy/blob/main/src/main/resources/config.yml).

## Usage
- Players can earn, trade, and store the configured item as physical currency.
- Once they spend the currency, that amount of the item will be subtracted from their inventory.
- Administrators can customize the item and formatting to match their server's theme.
