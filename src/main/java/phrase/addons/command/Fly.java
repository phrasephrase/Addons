package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Fly implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;

        if(player.getAllowFlight()) {
            player.setAllowFlight(false);
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы выключили режим полета!"));
            return true;
        } else {
            player.setAllowFlight(true);
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы включили режим полета!"));

        }

        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
