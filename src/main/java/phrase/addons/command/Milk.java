package phrase.addons.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;

import java.util.List;
import java.util.stream.Collectors;

public class Milk implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage("0");
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.milk")) {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

        List<PotionEffect> harmFulEffect =  player.getActivePotionEffects().stream().filter(effect -> effect.getType().getEffectCategory() ==
                PotionEffectType.Category.HARMFUL).collect(Collectors.toList());

        if(harmFulEffect.isEmpty()) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fУ вас нет негативных эффектов!"));
            return true;
        }

        harmFulEffect.stream().forEach(effect -> player.removePotionEffect(effect.getType()));
        commandSender.sendMessage(color("&a[>>] Инфо: &fВы очистили с себя негативные эффекты."));



        return true;
    }
    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
