package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TpDeny implements CommandExecutor {

    private Tpa tpa = new Tpa();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("0");
        }

        Player targetPlayer = (Player) commandSender;

        UUID playerUUID = tpa.getPlayers().remove(targetPlayer.getUniqueId());
        Player player = Bukkit.getPlayer(playerUUID);

        if(player == null) {
            System.out.println("1");
        }

        targetPlayer.sendMessage("2");
        player.sendMessage("3");

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
