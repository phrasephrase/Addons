package phrase.addons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

import java.util.List;
import java.util.stream.Collectors;

public class Milk implements CommandExecutor {

    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex , Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.milk")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

        List<PotionEffect> harmFulEffect =  player.getActivePotionEffects().stream().filter(effect -> effect.getType().getEffectCategory() == PotionEffectType.Category.HARMFUL).collect(Collectors.toList());

        if(harmFulEffect.isEmpty()) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.milk.noSuccesful")));
            return true;
        }

        harmFulEffect.stream().forEach(effect -> player.removePotionEffect(effect.getType()));
        commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.milk.succesful")));



        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
