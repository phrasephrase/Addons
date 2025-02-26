package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;
import phrase.addons.warp.WarpInfo;
import phrase.addons.sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Warp implements CommandExecutor {

    private static Map<String, WarpInfo> warps = new HashMap<>();

    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(player.hasPermission("addons.warp")) {
            if(s.equalsIgnoreCase("warp")) {

                if(strings.length < 1) {
                    commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.warpUsage")));
                    return true;
                }

                if(warps.containsKey(strings[0])) {
                    WarpInfo warp = warps.get(strings[0]);
                    Location locationWarp = new Location(warp.getWorld(), warp.getX(), warp.getY(), warp.getZ(), (float) warp.getYaw(), (float) warp.getPitch());
                    player.teleport(locationWarp);
                    String tp = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.tp"));
                    tp = tp.replace("{name}", strings[0]);
                    commandSender.sendMessage(tp);
                    return true;
                } else {
                    String find = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.find"));
                    find = find.replace("{name}", strings[0]);
                    commandSender.sendMessage(find);
                }
                return true;
            }
        } else {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

        if(!player.hasPermission("addons.setwarp")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

        if(s.equalsIgnoreCase("setwarp")) {
            if (strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.setWarpUsage")));
                return true;
            }

            if (warps.containsKey(strings[0])) {
                String exists = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.exists"));
                exists = exists.replace("{name}", strings[0]);
                commandSender.sendMessage(exists);
                return true;
            } else {

                WarpInfo warp = new WarpInfo(player.getUniqueId(), player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ() , player.getLocation().getYaw(), player.getLocation().getPitch());
                warps.put(strings[0], warp);

                String create = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.create"));
                create = create.replace("{name}", strings[0]);
                player.sendMessage(create);

            new BukkitRunnable() {
                @Override
                public void run() {
                    String query = "INSERT INTO Warps VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                    try(Connection connection = DatabaseManager.getServerConnection()) {
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, strings[0]);
                        preparedStatement.setDouble(2, player.getLocation().getBlockX());
                        preparedStatement.setDouble(3, player.getLocation().getBlockY());
                        preparedStatement.setDouble(4, player.getLocation().getBlockZ());
                        preparedStatement.setDouble(5, player.getLocation().getYaw());
                        preparedStatement.setDouble(6, player.getLocation().getPitch());
                        preparedStatement.setString(7, player.getWorld().getName());
                        preparedStatement.setString(8, player.getName());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        Plugin.instance.getLogger().info("Ошибка: " + e);
                    }
                }
            }.runTaskAsynchronously(Plugin.instance);
            }
            return true;
        }

        if(s.equalsIgnoreCase("delwarp")) {

            if(strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.delWarpUsage")));
                return true;
            }

            if(warps.containsKey(strings[0])) {
                WarpInfo warp = warps.get(strings[0]);
                if(player.getUniqueId().equals(warp.getOwner())) {
                    warps.remove(strings[0]);
                    String delete = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.delete"));
                    delete = delete.replace("{name}", strings[0]);
                    commandSender.sendMessage(delete);

                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            String query = "DELETE FROM WARPS WHERE name=?";

                            try(Connection connection = DatabaseManager.getServerConnection()) {
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setString(1 , strings[0]);
                                preparedStatement.executeUpdate();
                            } catch(SQLException e) {
                                Plugin.instance.getLogger().info("Ошибка: " + e);
                            }
                        }
                    }.runTaskAsynchronously(Plugin.instance);

                } else {
                    commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.creator")));
                }
                    return true;
            } else {
                String find = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.warp.find"));
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

    public static Map<String, WarpInfo> getWarps() {
        return warps;
    }
}
