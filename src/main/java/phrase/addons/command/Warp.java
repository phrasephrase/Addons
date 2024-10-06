package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;
import phrase.addons.WarpInfo;

import java.util.HashMap;
import java.util.Map;


public class Warp implements CommandExecutor {

    private static Map<String, WarpInfo> warps = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(player.hasPermission("addons.warp")) {
            if(s.equalsIgnoreCase("warp")) {

                if(strings.length < 1) {
                    commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.warpUsage")));
                    return true;
                }

                if(warps.containsKey(strings[0])) {
                    WarpInfo warp = warps.get(strings[0]);
                    player.teleport(warp.getLocation());
                    String tp = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.tp"));
                    tp = tp.replace("{name}", strings[0]);
                    commandSender.sendMessage(tp);
                    return true;
                } else {
                    String find = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.find"));
                    find = find.replace("{name}", strings[0]);
                    commandSender.sendMessage(find);
                }
                return true;
            }
        } else {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(!player.hasPermission("addons.setwarp")) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(s.equalsIgnoreCase("setwarp")) {
            if (strings.length < 1) {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.setWarpUsage")));
                return true;
            }

            if (warps.containsKey(strings[0])) {
                String exists = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.exists"));
                exists = exists.replace("{name}", strings[0]);
                commandSender.sendMessage(exists);
                return true;
            } else {
                warps.put(strings[0], new WarpInfo(player.getUniqueId(), player.getLocation()));
                String create = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.create"));
                create = create.replace("{name}", strings[0]);
            }
            return true;
        }

        if(s.equalsIgnoreCase("delwarp")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.delWarpUsage")));
                return true;
            }

            if(warps.containsKey(strings[0])) {
                WarpInfo warp = warps.get(strings[0]);
                if(player.getUniqueId().equals(warp.getOwner())) {
                    warps.remove(strings[0]);
                    String delete = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.delete"));
                    delete = delete.replace("{name}", strings[0]);
                    commandSender.sendMessage(delete);
                } else {
                    commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.creator")));
                }
                    return true;
            } else {
                String find = color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.warp.find"));
                find = find.replace("{name}", strings[0]);
                commandSender.sendMessage(find);
            }
            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
