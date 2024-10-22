package me.opd.drinkgamble.listeners;

import me.opd.drinkgamble.DrinkGamble;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.awt.event.ItemListener;

public class ItemClickListener implements Listener {
    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if(item==null || !item.hasItemMeta()){return;}

        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey nsk = new NamespacedKey(DrinkGamble.instance, "gamblePotion");
        item.setItemMeta(itemMeta);

        //TODO make sure not stained glass on click
        if(!data.has(nsk, PersistentDataType.STRING)){return;}

        String potionData = data.get(nsk, PersistentDataType.STRING);

        if(!e.getClickedInventory().getType().equals(InventoryType.PLAYER) && e.getView().getTitle().equals("§a§lGAMBLE MENU§7 - Select a potion")){
            e.setCancelled(true);
            if(p.getInventory().firstEmpty() == -1){
                p.sendMessage(DrinkGamble.prefix + "§cYour inventory is full!");
                p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, (float) 0.5, 2);
                return;
            }
            assert potionData != null;
            EconomyResponse r1 = DrinkGamble.economy.withdrawPlayer(p,Double.parseDouble(potionData.split(",")[2]));
            if(r1.transactionSuccess()){
                p.getInventory().addItem(item);
                p.sendMessage(DrinkGamble.prefix + "§7You bought a " + item.getItemMeta().getDisplayName() + "§r§7!");
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 2, 2);
            }else{
                p.sendMessage(DrinkGamble.prefix + "§cYou do not have enough money to buy this item!");
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, (float) 0.5, 1);
                p.closeInventory();
            }
        }
    }
}
