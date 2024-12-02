package phrase.addons.utils;

import net.md_5.bungee.api.ChatColor;
import phrase.addons.Plugin;

public class UtilHexColor {

    public static String colorize(String hex, String text) {
        if (!hex.matches("^#([0-9a-fA-F]{6})$")) {
            return text;
        }

        ChatColor chatColor = ChatColor.of(hex);
        ChatColor bold = ChatColor.BOLD;
        if(Plugin.getInstance().getConfig().getBoolean("bold")) {
            return chatColor + (bold + text);
        }

        return chatColor + text;
    }
}
