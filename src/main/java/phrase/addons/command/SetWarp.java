package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.WarpInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SetWarp implements CommandExecutor  {

    private HashMap<String, Warp> warps = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        if(strings.length < 1) {
            commandSender.sendMessage(color("&a[>>] Инфо: &f/setwarp <name>"));
            return true;
        }

        Player player = (Player) commandSender;

        if(warps.containsKey(strings[1])) {
            commandSender.sendMessage("&a[>>] Инфо: &fВарп с таким названием уже существует!");
            return true;
        }
        Location locationWarp = player.getLocation();
        WarpInfo warp = new WarpInfo(player.getUniqueId(), locationWarp);
        warps.put(strings[1], warp);
        commandSender.sendMessage("&a[>>] Инфо: &fВы успешно создали варп с названием &6" + strings[1]);

        return true;
    }

    public WarpInfo findWarp(String name) {
        WarpInfo warp = null;
        if(!warps.containsKey(name)) {
        }
        for(Map.Entry<String, Warp> entry : warps.entrySet()) {
            warp = (WarpInfo) entry.getValue();
            return warp;
        }
        return warp;
    }

    public boolean delWarp(String name) {
        if(!warps.containsKey(name)) {
            return false;
        }
        warps.remove(name);
        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
