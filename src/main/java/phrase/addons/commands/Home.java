package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.home.HomeInfo;
import phrase.addons.utils.UtilHexColor;

import java.util.HashMap;
import java.util.Map;

public class Home implements CommandExecutor {

    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");
    private static Map<String, HomeInfo> homes = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.home")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(s.equalsIgnoreCase("home")) {
            if(strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.homeUsage")));
                return true;
            }

            if(!homes.containsKey(strings[0])) {
                String find = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.find"));
                find = find.replace("{name}", strings[0]);
                commandSender.sendMessage(find);
                return true;
            }

            HomeInfo home = homes.get(strings[0]);
            Location location = new Location(home.getWorld(), home.getX(), home.getY(), home.getZ(), home.getYaw(), home.getPitch());
            player.teleport(location);
            String tp = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.tp"));
            tp = tp.replace("{name}", strings[0]);
            commandSender.sendMessage(tp);

            return true;
        }

        if(s.equalsIgnoreCase("sethome")) {
            if(strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.setHomeUsage")));
                return true;
            }

            HomeInfo home = new HomeInfo(player.getUniqueId(), player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            homes.put(strings[0], home);
            String create = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.create"));
            create = create.replace("{name}", strings[0]);
            commandSender.sendMessage(create);

            HomesCfg homesCfg = new HomesCfg();

            homesCfg.setHome(player.getName(), strings[0] ,home.getWorld(), home.getX(), home.getY(), home.getZ(), home.getYaw(), home.getPitch());

            /*
            new BukkitRunnable() {
                @Override
                public void run() {
                    String query = "INSERT INTO homes VALUES(?,?,?,?,?,?,?,?)";

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
                        Addons.getInstance().getLogger().info("Ошибка " + e);
                    }
                }
            }.runTaskAsynchronously(Addons.getInstance());
             */
            return true;
        }

        if(s.equalsIgnoreCase("delhome")) {
            if(strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.delHomeUsage")));
                return true;
            }

            if(!homes.containsKey(strings[0])) {
                String find = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.find"));
                find = find.replace("{name}", strings[0]);
                commandSender.sendMessage(find);
                return true;
            }

            homes.remove(strings[0]);
            String delete = UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.home.delete"));
            delete = delete.replace("{name}", strings[0]);
            commandSender.sendMessage(delete);

            HomesCfg homesCfg = new HomesCfg();

            homesCfg.delHome(player.getName(), strings[0]);

            /*
            new BukkitRunnable() {
                @Override
                public void run() {
                    String query = "DELETE FROM homes WHERE name=? AND owner=?";

                    try(Connection connection = DatabaseManager.getServerConnection()) {
                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, strings[0]);
                        preparedStatement.setString(2, player.getName());
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        Addons.getInstance().getLogger().info("Ошибка " + e);
                    }
                }
            }.runTaskAsynchronously(Addons.getInstance());
             */

            return true;
        }

        return true;
    }

    public static Map<String, HomeInfo> getHomes() {
        return homes;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
