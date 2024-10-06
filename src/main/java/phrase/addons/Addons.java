package phrase.addons;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import phrase.addons.command.*;

public final class Addons extends JavaPlugin {

    private static Addons instance;
    private FileConfiguration =

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getCommand("workbench").setExecutor(new Workbench());
        getCommand("ext").setExecutor(new Ext());
        getCommand("fly").setExecutor(new Fly());
        getCommand("hat").setExecutor(new Hat());
        getCommand("invsee").setExecutor(new InventorySee());
        Bukkit.getPluginManager().registerEvents(new InventorySee(), this);
        getCommand("god").setExecutor(new God());
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
        getLogger().info("Плагин Addons успешно загружен!");
    }

    @Override
    public void onDisable() {

    }

    public static Addons getInstance() {
        return instance;
    }
}
