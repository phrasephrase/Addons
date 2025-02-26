package phrase.addons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Teleport implements CommandExecutor {

    private static Map<UUID, UUID> players = new HashMap<>();
    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(player.hasPermission("addons.tp")) {
            if(s.equalsIgnoreCase("tp")) {

                if(strings.length < 1) {
                    commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.tpUsage")));
                    return true;
                }

                Player targetPlayer = Bukkit.getPlayer(strings[0]);

                if(targetPlayer.getUniqueId().equals(player.getUniqueId())) {
                    commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color( Plugin.instance.getConfig().getString("message.command.teleport.noTp")));
                    return true;
                }

                if(targetPlayer != null) {

                    player.teleport(targetPlayer.getLocation());
                    String tp = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.succesful"));
                    tp = tp.replace("{player}", targetPlayer.getName());
                    commandSender.sendMessage(tp);
                    return true;
                } else {
                    commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.player")));
                }

                return true;
            }
        } else {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

      if(!player.hasPermission("addons.tpa")) {
          commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.permission")));
          return true;
      }

        if(s.equalsIgnoreCase("tpa")) {

            if(strings.length < 1) {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.tpaUsage")));
                return true;
            }

            Player targetPlayer = Bukkit.getPlayer(strings[0]);

            if(targetPlayer != null) {

                players.put(targetPlayer.getUniqueId(), player.getUniqueId());
                String tpaPlayer = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.tpaRequestPlayer"));
                tpaPlayer = tpaPlayer.replace("{player}", targetPlayer.getName());
                String tpaTargetPlayer = UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.tpaRequestTargetPlayer"));
                tpaTargetPlayer = tpaTargetPlayer.replace("{player}", player.getName());
                commandSender.sendMessage(tpaPlayer);
                targetPlayer.sendMessage(tpaTargetPlayer);
                return true;
            } else {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.player")));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpaccept")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                targetPlayer.teleport(player);
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.acceptTargetPlayer")));
                targetPlayer.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.acceptPlayer")));
                return true;
            } else {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.request")));
            }

            return true;
        }

        if(s.equalsIgnoreCase("tpdeny")) {

            UUID senderPlayer = players.remove(player.getUniqueId());
            Player targetPlayer = Bukkit.getPlayer(senderPlayer);

            if(targetPlayer != null) {

                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.denyTargetPlayer")));
                targetPlayer.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.denyPlayer")));
                return true;
            } else {
                commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.teleport.request")));
            }

            return true;
        }

        return true;
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
