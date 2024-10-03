package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InventorySee implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        if(strings.length < 0) {
            commandSender.sendMessage(color("&a[>>] Инфо: &f/invsee <name>"));
            return true;
        }

        Player player = (Player) commandSender;
        String name = strings[0];
        Player targetPlayer = Bukkit.getPlayer(name);

        if(targetPlayer.isOnline()) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fИгрок не найден!"));
        }

        player.openInventory(targetPlayer.getInventory());
        commandSender.sendMessage(color("&a[>>] Инфо: &fВы успешно открыли инвентарь &6" + targetPlayer.getName()));


        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
