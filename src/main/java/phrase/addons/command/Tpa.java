package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class Tpa implements CommandExecutor {

    private HashMap<UUID, UUID> players = new HashMap();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("0");
            return true;
        }

        Player player = (Player) commandSender;

        if(strings.length < 1) {
            commandSender.sendMessage("1");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(strings[1]);

        if(targetPlayer.isOnline() || targetPlayer == null) {
            commandSender.sendMessage("2");
            return true;
        }

        players.put(targetPlayer.getUniqueId(), player.getUniqueId());
        commandSender.sendMessage("3");
        targetPlayer.sendMessage("4");

        return true;
    }

    public HashMap<UUID, UUID> getPlayers() {
        return players;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
