package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class Milk implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("0");
        }

        Player player = (Player) commandSender;

        List<PotionEffect> badEffect =  player.getActivePotionEffects().stream().filter(effect -> effect.getType().getEffectCategory() ==
                PotionEffectType.Category.HARMFUL).collect(Collectors.toList());

        if(badEffect == null) {
            commandSender.sendMessage("1");
        }

        badEffect.stream().forEach(effect -> player.removePotionEffect(effect.getType()));
        commandSender.sendMessage("2");



        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
