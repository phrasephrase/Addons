package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teleport implements CommandExecutor {

    private Map<UUID, UUID> players = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;

        if(s.equalsIgnoreCase("tp")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color("&a[>>] Инфо: &f/tp <name>"));
            }

            Player targetPlayer = Bukkit.getPlayer(strings[0]);

            if(targetPlayer != null) {

                player.teleport(targetPlayer.getLocation());
                commandSender.sendMessage(color("&a[>>] Инфо: &fВы телепортировались к &6" + targetPlayer.getName()));
                return true;
            } else {
                commandSender.sendMessage(color("&a[>>] Инфо: &fИгрок не найден!"));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpa")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color("&a[>>] Инфо: &f/tpa <name>"));
            }

            Player targetPlayer = Bukkit.getPlayer(strings[0]);

            if(targetPlayer != null) {

                players.put(targetPlayer.getUniqueId(), player.getUniqueId());
                commandSender.sendMessage(color("&a[>>] Инфо: &fВы отправили запрос на телепортацию к &6" + targetPlayer.getName()));
                targetPlayer.sendMessage(color("&a[>>] Инфо: &6" + player.getName() + " &fотправил запрос на телепортацию к вам!"));
                targetPlayer.sendMessage(color("&a[>>] Инфо: &fВведите &6/tpaccept &fчтобы принять или &6/tpdeny &fчтобы отклонить."));
                return true;
            } else {
                commandSender.sendMessage(color("&a[>>] Инфо: &fИгрок не найден!"));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpaccept")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                targetPlayer.teleport(player);
                commandSender.sendMessage(color("&a[>>] Инфо: &fВы приняли запрос на телепортацию!"));
                targetPlayer.sendMessage(color("&a[>>] Инфо: &fИгрок принял ваш запрос на телепортацию!"));
                return true;
            } else {
                commandSender.sendMessage(color("&a[>>] Инфо: &fВам никто не отправлял запрос на телепортацию!"));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpdeny")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                commandSender.sendMessage(color("&a[>>] Инфо: &fВы отклонили запрос на телепортацию!"));
                targetPlayer.sendMessage(color("&a[>>] Инфо: &fИгрок отклонил ваш запрос на телепортацию!"));
                return true;
            } else {
                commandSender.sendMessage(color("&a[>>] Инфо: &fВам никто не отправлял запрос на телепортацию!"));
            }

            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
