package phrase.addons.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Addons;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teleport implements CommandExecutor {

    private static Map<UUID, UUID> players = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(color("&a[>>] Инфо: &fВы не являетесь игроком!"));
            return true;
        }

        Player player = (Player) commandSender;

        if(player.hasPermission("addons.tp")) {
            if(s.equalsIgnoreCase("tp")) {

                if(strings.length < 1) {
                    commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.tpUsage")));
                    return true;
                }

                Player targetPlayer = Bukkit.getPlayer(strings[0]);

                if(targetPlayer != null) {

                    player.teleport(targetPlayer.getLocation());
                    String tp = Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission");
                    tp = tp.replace("{player}", targetPlayer.getName());
                    commandSender.sendMessage(tp);
                    return true;
                } else {
                    commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.player")));
                }

                return true;
            }
        } else {
            commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
            return true;
        }

      if(!player.hasPermission("addons.tpa")) {
          commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.permission")));
          return true;
      }

        if(s.equalsIgnoreCase("tpa")) {

            if(strings.length < 1) {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.tpaUsage")));
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(strings[0]);

            if(targetPlayer != null) {

                players.put(targetPlayer.getUniqueId(), player.getUniqueId());
                String tpaPlayer = Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.tpaRequestPlayer");
                tpaPlayer = tpaPlayer.replace("{player}", targetPlayer.getName());
                String tpaTargetPlayer = Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.tpaRequestTargetPlayer");
                tpaTargetPlayer = tpaTargetPlayer.replace("{player}", player.getName());
                commandSender.sendMessage(tpaPlayer);
                targetPlayer.sendMessage(tpaTargetPlayer);
                return true;
            } else {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.player")));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpaccept")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                targetPlayer.teleport(player);
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.acceptTargetPlayer")));
                targetPlayer.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.acceptPlayer")));
                return true;
            } else {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.request")));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpdeny")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.denyTargetPlayer")));
                targetPlayer.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.denyPlayer")));
                return true;
            } else {
                commandSender.sendMessage(color(Addons.getInstance().getConfig().getString("message.prefix") + Addons.getInstance().getConfig().getString("message.command.teleport.request")));
            }

            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
