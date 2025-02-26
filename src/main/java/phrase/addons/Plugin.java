package phrase.addons;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import phrase.addons.commands.*;
import phrase.addons.home.HomeInfo;
import phrase.addons.sql.DatabaseManager;
import phrase.addons.warp.WarpInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;


public final class Plugin extends JavaPlugin {

    public static Plugin instance;
    public static final String URL = instance.getConfig().getString("database.url");
    public static final String USERNAME = instance.getConfig().getString("database.username");;
    public static final String PASSWORD = instance.getConfig().getString("database.password");;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        DatabaseManager.registerDriver();

        getCommand("workbench").setExecutor(new Workbench());
        getCommand("ext").setExecutor(new Ext());
        getCommand("fly").setExecutor(new Fly());
        getCommand("hat").setExecutor(new Hat());
        getCommand("invsee").setExecutor(new InventorySee());
        Bukkit.getPluginManager().registerEvents(new InventorySee(), this);
        getCommand("tp").setExecutor(new Teleport());
        getCommand("tpa").setExecutor(new Teleport());
        getCommand("tpaccept").setExecutor(new Teleport());
        getCommand("tpdeny").setExecutor(new Teleport());
        getCommand("warp").setExecutor(new Warp());
        getCommand("setwarp").setExecutor(new Warp());
        getCommand("delwarp").setExecutor(new Warp());
        getCommand("milk").setExecutor(new Milk());
        getCommand("ec").setExecutor(new EnderChest());
        Bukkit.getPluginManager().registerEvents(new EnderChest(), this);
        getCommand("near").setExecutor(new Near());
        getCommand("endersee").setExecutor(new EnderSee());
        Bukkit.getPluginManager().registerEvents(new EnderSee(), this);
        getCommand("feed").setExecutor(new Feed());
        getCommand("home").setExecutor(new Home());
        getCommand("sethome").setExecutor(new Home());
        getCommand("delhome").setExecutor(new Home());
        getCommand("repair").setExecutor(new Repair());
        getCommand("addons").setExecutor(new AddonsManager());
        getLogger().info("Плагин Addons успешно загружен!");
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void getDataWarps() {
        try(Connection connection = DatabaseManager.getServerConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Warps");
            while(resultSet.next()) {
                String name = resultSet.getString(1);
                Double x = resultSet.getDouble(2);
                Double y = resultSet.getDouble(3);
                Double z = resultSet.getDouble(4);
                float yaw = (float) resultSet.getDouble(5);
                float pitch = (float) resultSet.getDouble(6);
                @Nullable World world = Bukkit.getWorld(resultSet.getString(7));
                @Nullable UUID owner = Bukkit.getPlayerUniqueId(resultSet.getString(8));
                WarpInfo warpInfo = new WarpInfo(owner, world, x, y, z, yaw, pitch);
                Warp warp = new Warp();
                warp.getWarps().put(name, warpInfo);
            }
        } catch (SQLException e) {
            getLogger().info("Не удалось извлечь данные " + e);
        }
    }

    public void getDataHomes() {
        try(Connection connection = DatabaseManager.getServerConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Homes");
            while(resultSet.next()) {
                String name = resultSet.getString(1);
                Double x = resultSet.getDouble(2);
                Double y = resultSet.getDouble(3);
                Double z = resultSet.getDouble(4);
                float yaw = (float) resultSet.getDouble(5);
                float pitch = (float) resultSet.getDouble(6);
                @Nullable World world = Bukkit.getWorld(resultSet.getString(7));
                @Nullable UUID owner = Bukkit.getPlayerUniqueId(resultSet.getString(8));
                HomeInfo homeInfo = new HomeInfo(owner, world, x, y, z, yaw, pitch);
                Warp warp = new Warp();
                Home.getHomes().put(name, homeInfo);
            }
        } catch (SQLException e) {
            getLogger().info("Не удалось извлечь данные " + e);
        }
    }


}
