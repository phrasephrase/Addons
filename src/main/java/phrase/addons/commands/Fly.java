package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Fly implements CommandExecutor {

    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex , Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.fly")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

        if(player.getAllowFlight()) {
            player.setAllowFlight(false);
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.fly.disable")));
            return true;
        } else {
            player.setAllowFlight(true);
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.fly.enable")));

        }

        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
