package phrase.addons;

import org.bukkit.plugin.java.JavaPlugin;

public final class Addons extends JavaPlugin {

    private Addons instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public Addons getInstance() {
        return instance;
    }
}
