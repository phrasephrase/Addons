package phrase.addons;


import org.bukkit.plugin.java.JavaPlugin;
import phrase.addons.command.*;

public final class Addons extends JavaPlugin {

    private Addons instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("workbench").setExecutor(new Workbench());
        getCommand("ext").setExecutor(new Ext());
        getCommand("fly").setExecutor(new Fly());
        getCommand("hat").setExecutor(new Hat());
        getCommand("invsee").setExecutor(new InventorySee());
        getCommand("god").setExecutor(new God());
        getCommand("tpa").setExecutor(new Teleport());
        getCommand("tpaccept").setExecutor(new Teleport());
        getCommand("tpdeny").setExecutor(new Teleport());
        getCommand("warp").setExecutor(new Warp());
        getCommand("setwarp").setExecutor(new Warp());
        getCommand("delwarp").setExecutor(new Warp());
        getCommand("milk").setExecutor(new Milk());
        getCommand("ec").setExecutor(new EnderChest());
        getCommand("near").setExecutor(new Near());
        getLogger().info("Плагин Addons успешно загружен!");
    }

    @Override
    public void onDisable() {

    }

    public Addons getInstance() {
        return instance;
    }
}
