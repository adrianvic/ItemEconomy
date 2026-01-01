# ItemEconomy II
ItemEconomy II is a fork of [ItemEconomy](https://modrinth.com/plugin/itemeconomy), keeping it updated to later versions of Minecraft and adding new features.

This PaperMC plugin integrates with Vault to provide a unique, item-based economy system for your Minecraft server. Instead of relying solely on virtual balances, players use in-game items as physical currency, adding a layer of immersion and realism to your economy.
Features:

- Item-Based Currency: Set any Minecraft item as your server's currency (default: diamonds).
- VaultUnlocked Integration: Fully compatible with VaultUnlocked, enabling seamless interaction with other economy-based plugins.
- Simple logic: Just checks if the user has the item/how many when queried.
- Customizable Formatting: Define how your currency is displayed, including singular and plural forms.
- Ender Chest support: Items on Ender Chests are counted in the user balance.

## Configuration Example:
```yaml
item: diamond        # Define the item to be used as currency.
singular: diamond    # Singular form of the currency.
plural: diamonds     # Plural form of the currency.
format: "{}$"        # Customize how the currency is displayed in messages.
ender_chest: balance # Either none or balance
```
This configuration will use diamonds as the currency, displayed as {amount}$, e.g., "5 diamonds" or "1 diamond".

## Usage:

- Players can earn, trade, and store the configured item as physical currency.
- Integrates seamlessly with Vault-compatible plugins for shops, auctions, and more.
- Administrators can customize the item and formatting to match their server's theme.
