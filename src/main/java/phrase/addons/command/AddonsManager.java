package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;

public class AddonsManager implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color(phrase.addons.Addons.getInstance().getConfig().getString("message.prefix") + phrase.addons.Addons.getInstance().getConfig().getString("message.checking")));
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.manager")) {
            commandSender.sendMessage(color(phrase.addons.Addons.getInstance().getConfig().getString("message.prefix") + phrase.addons.Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        if(strings[0].equalsIgnoreCase("reload")) {
            Addons.getInstance().reloadConfig();
            commandSender.sendMessage(color(phrase.addons.Addons.getInstance().getConfig().getString("message.prefix") + phrase.addons.Addons.getInstance().getConfig().getString("message.command.reload.succesful")));
            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
