package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;

public class God implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.god")) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(player.getGameMode() == GameMode.CREATIVE) {
            player.setGameMode(GameMode.SURVIVAL);
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.god.disable")));
            return true;
        }

        player.setGameMode(GameMode.CREATIVE);
        commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.god.enable")));

        return true;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getWhoClicked().getOpenInventory().equals(e.getWhoClicked().getInventory())) {
            e.setCancelled(true);
        }

    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
