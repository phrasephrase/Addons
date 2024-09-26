package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class Ext implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
        }

        Player player = (Player) commandSender;

        if(!(player.getFireTicks() > 0)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не горите!"));
        }

        player.setFireTicks(0);
        commandSender.sendMessage(color("&a[>>] Инфо: &fВы были потушены!"));

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
