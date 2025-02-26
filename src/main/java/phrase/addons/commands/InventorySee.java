package phrase.addons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import phrase.addons.Plugin;
import phrase.addons.utils.UtilHexColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventorySee implements CommandExecutor, Listener {

    private static String hex = Plugin.instance.getConfig().getString("hexColor");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
                             @NotNull String s, @NotNull String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.checking")));
            return true;
        }

        Player player = (Player) commandSender;

        if(!player.hasPermission("addons.invsee")) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) +color(Plugin.instance.getConfig().getString("message.permission")));
            return true;
        }

        if(strings.length < 1) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.invsee.usage")));
            return true;
        }

        String name = strings[0];
        Player targetPlayer = Bukkit.getPlayer(name);

        if(targetPlayer == null) {
            commandSender.sendMessage(UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.invsee.player")));
        }


        Inventory inv = Bukkit.createInventory(null, 54, color("&8Инвентарь игрока " + targetPlayer.getName()));

        for(int i = 0; i<targetPlayer.getInventory().getSize(); i++) {
            ItemStack item = targetPlayer.getInventory().getItem(i);
            inv.setItem(i, item);
        }

        inv.setItem(43, targetPlayer.getInventory().getHelmet());
        inv.setItem(44, targetPlayer.getInventory().getLeggings());
        inv.setItem(52, targetPlayer.getInventory().getChestplate());
        inv.setItem(53, targetPlayer.getInventory().getBoots());

        if(inv.getItem(43) == null) {
            inv.setItem(43, new ItemStack(Material.CHAINMAIL_HELMET));
        }

        if(inv.getItem(44) == null) {
            inv.setItem(44, new ItemStack(Material.CHAINMAIL_LEGGINGS));
        }

        if(inv.getItem(52) == null) {
            inv.setItem(52, new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        }

        if(inv.getItem(53) == null) {
            inv.setItem(53, new ItemStack(Material.CHAINMAIL_BOOTS));
        }

        inv.setItem(39, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        inv.setItem(40, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        inv.setItem(41, new ItemStack(Material.RED_STAINED_GLASS_PANE));
        inv.setItem(49, new ItemStack(Material.RED_STAINED_GLASS_PANE));

        inv.setItem(51, targetPlayer.getInventory().getItemInOffHand());

        List<PotionEffect> potionEffectsList = targetPlayer.getActivePotionEffects().stream().collect(Collectors.toList());
        ItemStack item = new ItemStack(Material.POTION);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(color("&fЭффекты игрока &6" + player.getName()));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        for(PotionEffect potionEffects : potionEffectsList) {
            String potionEffect = potionEffects.toString();
            List<String> lorePotionEffects = new ArrayList<>();
            lorePotionEffects.add(potionEffect);
            meta.setLore(lorePotionEffects);
        }

        item.setItemMeta(meta);

        inv.setItem(50, item);

        player.openInventory(inv);
        String openInventory = (UtilHexColor.colorize(hex, Plugin.instance.getConfig().getString("message.prefix")) + color(Plugin.instance.getConfig().getString("message.command.invsee.open")));
        openInventory = openInventory.replace("{player}", targetPlayer.getName());
        player.sendMessage(openInventory);
        return true;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getView().getTitle().startsWith(color("&8Инвентарь игрока "))) {
            e.setCancelled(true);
        }
    }

    public String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
