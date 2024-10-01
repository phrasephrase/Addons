package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Near implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("0");
        }

        Player player = (Player) commandSender;

        StringBuilder nearblyPlayers = new StringBuilder("1: ");

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != player && p.getLocation().distance(player.getLocation()) <= 100) {
                nearblyPlayers.append(p.getName());
            }
        }

        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
