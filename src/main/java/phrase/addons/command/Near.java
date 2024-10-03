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
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;

        StringBuilder nearblyPlayers = new StringBuilder(color("&a[>>] Инфо: &fРядом с вами: "));

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p != player && p.getLocation().distance(player.getLocation()) <= 100) {
                nearblyPlayers.append(p.getName());
                nearblyPlayers.append(", ");
                return true;
            }
        }

        if(nearblyPlayers.toString().equals(color("&a[>>] Инфо: &fРядом с вами: "))) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fРядом с вами нет игроков!"));
            return true;
        }

        commandSender.sendMessage(nearblyPlayers.toString());

        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
