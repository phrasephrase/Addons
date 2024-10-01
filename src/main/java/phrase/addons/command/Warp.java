package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.WarpInfo;


public class Warp implements CommandExecutor {

    private SetWarp setWarp = new SetWarp();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        if(strings.length < 1) {
            commandSender.sendMessage(color("&a[>>] Инфо: &f/warp <name>"));
            return true;
        }

        Player player = (Player) commandSender;
        WarpInfo warp = setWarp.findWarp(strings[1]);
        if(warp == null) {
            return true;
        }
        player.teleport(warp.getLocation());
        commandSender.sendMessage(color("&a[>>] Инфо: &fВы успешно телепортированы на варп " + strings[1]));


        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
