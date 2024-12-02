package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

public class Hat implements CommandExecutor {
    private static String hex = Plugin.getInstance().getConfig().getString("hexColor");
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.hat")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.AIR) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.hat.item")));
            return true;
        }

        if(player.getInventory().getHelmet() != null) {
            player.getInventory().addItem(player.getInventory().getHelmet());
            player.getInventory().setHelmet(new ItemStack(Material.AIR));
        }

        player.getInventory().setHelmet(item);
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));

        commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.getInstance().getConfig().getString("message.prefix")) + color(Plugin.getInstance().getConfig().getString("message.command.hat.succesful")));

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
