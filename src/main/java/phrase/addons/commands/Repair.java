package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Repair implements CommandExecutor {

    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.repair")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.repair.item")));
            return true;
        }

        player.getInventory().getItemInMainHand().setDurability((short) 0);
        commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix") + Plugin.getInstance().getConfig().getString("message.command.repair.succesful")));

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
