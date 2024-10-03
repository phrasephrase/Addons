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


public class Warp implements CommandExecutor {

    private Map<String, WarpInfo> warps = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;

        if(s.equalsIgnoreCase("warp")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color("&a[>>] Инфо: &f/warp <name>"));
                return true;
            }

            if(warps.containsKey(strings[0])) {
                WarpInfo warp = warps.get(strings[0]);
                player.teleport(warp.getLocation());
                commandSender.sendMessage(color("&a[>>] Инфо: &fВы телепортированы на варп &6" + strings[0]));
                return true;
            } else {
                System.out.println("Отладка: else");
                commandSender.sendMessage(color("&a[>>] Инфо: &fВарп &6" + strings[0] + " &fне существует!"));
            }
                return true;
        }

        if(s.equalsIgnoreCase("setwarp")) {
            if (strings.length < 1) {
                commandSender.sendMessage(color("&a[>>] Инфо: &f/setwarp <name>"));
                return true;
            }

            if (warps.containsKey(strings[0])) {
                commandSender.sendMessage(color("&a[>>] Инфо: &fВарп с таким названием уже существует!"));
                return true;
            } else {
                warps.put(strings[0], new WarpInfo(player.getUniqueId(), player.getLocation()));
                commandSender.sendMessage(color("&a[>>] Инфо: &fВы создали варп с названием &6" + strings[0]));
            }
            return true;
        }

        if(s.equalsIgnoreCase("delwarp")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color("&a[>>] Инфо: &f/delwarp <name>"));
                return true;
            }

            if(warps.containsKey(strings[0])) {
                WarpInfo warp = warps.get(strings[0]);
                if(player.getUniqueId().equals(warp.getOwner())) {
                    warps.remove(strings[0]);
                    commandSender.sendMessage(color("&a[>>] Инфо: &fВы удалили варп &6" + strings[0]));
                } else {
                    commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь владельцем варпа!"));
                }
                    return true;
            } else {
                commandSender.sendMessage(color("&a[>>] Инфо: &fВарп &6" + strings[0] + " &fне найден!"));
            }
            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
