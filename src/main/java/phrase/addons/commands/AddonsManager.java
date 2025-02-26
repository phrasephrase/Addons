package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class AddonsManager implements CommandExecutor {

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

        if(!player.hasPermission("addons.manager")) {
            String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission"));
            commandSender.sendMessage(message);
            return true;
        }

        if(strings.length < 1) {
            return true;
        }

        if(strings[0].equalsIgnoreCase("reload")) {
            Plugin.instance.reloadConfig();
            String message = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.reload.succesful"));
            commandSender.sendMessage(message);
            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
