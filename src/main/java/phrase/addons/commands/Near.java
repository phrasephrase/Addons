package phrase.addons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Near implements CommandExecutor {

    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.near")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        StringBuilder nearblyPlayers = new StringBuilder(color("&fРядом с вами: &f"));

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != player && p.getLocation().distance(player.getLocation()) <= 100) {
                nearblyPlayers.append(p.getName());
                nearblyPlayers.append(color("&6| &f"));
            }
        }

        if(nearblyPlayers.toString().equals(color("&fРядом с вами: &6"))) {
            commandSender.sendMessage(color(Plugin.getInstance().getConfig().getString("message.prefix") + Plugin.getInstance().getConfig().getString("message.command.near.find")));
            return true;
        }

        commandSender.sendMessage(nearblyPlayers.toString());

        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
