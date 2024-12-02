package phrase.addons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class EnderSee implements CommandExecutor, Listener {

    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(strings.length < 1) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.endersee.enderSeeUsage")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[0]);

        Inventory inv = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, color("&8Эндер-сундук игрока " + targetPlayer.getName()));

        for(int i = 0; i<targetPlayer.getEnderChest().getSize(); i++) {
            ItemStack item = targetPlayer.getEnderChest().getItem(i);
            inv.setItem(i, item);
        }

        player.openInventory(inv);
        String open = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.endersee.open"));
        open.replace("{name}", targetPlayer.getName());
        commandSender.sendMessage(open);

        return true;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getView().getTitle().startsWith("&8Эндер сундук игрока ")) {
            e.setCancelled(true);
        }
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
