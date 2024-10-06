package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;

public class EnderChest implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.enderchest")) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        Inventory inv = Bukkit.createInventory(player, InventoryType.ENDER_CHEST, color("&8Эндер сундук"));

        for(int i = 0; i<player.getEnderChest().getSize(); i++) {
            ItemStack item = player.getEnderChest().getItem(i);
            inv.setItem(i, item);
        }


        commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.enderchest.open")));

        return true;
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        if(e.getView().getTitle().equals(color("&8Эндер сундук"))) {
            e.getPlayer().getEnderChest().clear();
            for(int i = 0; i<e.getInventory().getSize(); i++) {
                ItemStack item = e.getInventory().getItem(i);
                e.getPlayer().getEnderChest().setItem(i, item);
            }
        }
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
