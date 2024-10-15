package phrase.addons.command;

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
import phrase.addons.Addons;

public class EnderSee implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(strings.length < 1) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.endersee.enderSeeUsage")));
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[0]);

        Inventory inv = Bukkit.createInventory(null, InventoryType.ENDER_CHEST, color("&8Эндер-сундук игрока " + targetPlayer.getName()));

        for(int i = 0; i<targetPlayer.getEnderChest().getSize(); i++) {
            ItemStack item = targetPlayer.getEnderChest().getItem(i);
            inv.setItem(i, item);
        }

        player.openInventory(inv);
        String open = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.endersee.open"));
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
