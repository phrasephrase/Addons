package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Feed implements CommandExecutor {

    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender,
                             @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            String message = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking"));
            commandSender.sendMessage(message);
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.feed")) {
            String message = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.permission"));
            commandSender.sendMessage(message);
            return true;
        }

        player.setFoodLevel(20);
        player.setSaturation(20);
        String message = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.feed.succesful"));

        commandSender.sendMessage(message);

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
