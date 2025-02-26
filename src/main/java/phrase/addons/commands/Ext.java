package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Ext implements CommandExecutor {

    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.checking"));
            commandSender.sendMessage(message);
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.ext")) {
            String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission"));
            commandSender.sendMessage(message);
            return true;
        }

        if(!(player.getFireTicks() > 0)) {
            String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.ext.noSuccesful"));
            commandSender.sendMessage(message);
            return true;
        }

        player.setFireTicks(0);
        String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.ext.succesful"));
        commandSender.sendMessage(message);

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
