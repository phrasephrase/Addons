package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.WarpInfo;

public class DelWarp implements CommandExecutor {

    private SetWarp setWarp = new SetWarp();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("");
            return true;
        }

        Player player = (Player) commandSender;

        WarpInfo warp = (WarpInfo) setWarp.findWarp(strings[1]);
        if(warp == null) {
            commandSender.sendMessage("");
            return true;
        }
        if(!(warp.getOwner() == player.getUniqueId())) {
            commandSender.sendMessage("");
            return true;
        }
        setWarp.delWarp(strings[1]);
        commandSender.sendMessage("");


        return true;
    }

    public void color(String string) {
        ChatColor.translateAlternateColorCodes('&', string);
    }
}
