package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SetWarp implements CommandExecutor  {

    private HashMap<String, Location> warp = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
        }

        if(strings.length < 1) {
            commandSender.sendMessage(color("&a[>>] Инфо: &f/setwarp <name>"));
        }

        Player player = (Player) commandSender;

        if(warp.containsKey(strings[1])) {
            commandSender.sendMessage("&a[>>] Инфо: &fВарп с таким названием уже существует!");
        }

        Location locationWarp = player.getLocation();
        warp.put(strings[1], locationWarp);
        commandSender.sendMessage("&a[>>] Инфо: &fВы успешно создали варп с названием &6" + strings[1]);

        return true;
    }

    public Location findWarp(String name) {
        if(!(warp.containsKey(name))) {
        }
        for(Map.Entry<String, Location> entry: warp.entrySet()) {
            if(!(entry.getKey().equals(name))) {
            }
            Location value = entry.getValue();
            return value;
        }
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
