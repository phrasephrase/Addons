package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Hat implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.equals(Material.AIR)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fУ вас нет предмета в руке!"));
            return true;
        }

        int amountItem = item.getAmount();

        player.getInventory().getItemInMainHand().setAmount(amountItem - 1);
        player.getInventory().setHelmet(item);

        commandSender.sendMessage(color("&a[>>] Инфо: &fВы успешно надели предмет на голову!"));

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
