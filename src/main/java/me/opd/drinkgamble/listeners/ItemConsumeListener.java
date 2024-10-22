package me.opd.drinkgamble.listeners;

import me.opd.drinkgamble.DrinkGamble;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class ItemConsumeListener implements Listener {

    private Economy economy = DrinkGamble.economy;

    @EventHandler
    public void onPlayerEat(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(!item.hasItemMeta()){return;}

        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey nsk = new NamespacedKey(DrinkGamble.instance, "gamblePotion");
        item.setItemMeta(itemMeta);

        if(!data.has(nsk, PersistentDataType.STRING)){return;}

        String potionData = data.get(nsk, PersistentDataType.STRING);
//        player.sendMessage("Potion consumed with value " + potionData);
        assert potionData != null;
        triggerPotion(player, potionData);
        player.getInventory().removeItem(item);
        event.setCancelled(true);
    }

    private void triggerPotion(Player player, String potionData){

        Random random = new Random();
        double randomDouble  = random.nextDouble();
        double winChance = Double.parseDouble(potionData.split(",")[1]);
        double winAmount = Double.parseDouble(potionData.split(",")[0]);

        if(randomDouble < winChance){
//            player.sendMessage(randomDouble + " is less than " + winChance + " therefore it is a win");
            economy.depositPlayer(player.getName(), winAmount);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            player.sendTitle("§aCongratulations!", "§aYou won!");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,40,1));
            Bukkit.getServer().broadcastMessage(DrinkGamble.prefix + ChatColor.GRAY + player.getName() + " §rwon §a$" +
                    NumberFormat.getInstance(Locale.US).format(winAmount) + " §rfrom a gamble potion!");
        }else{
//            player.sendMessage(randomDouble + " is more than " + winChance + " therefore it is a loss");
            player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, (float) 0.5);
            player.sendTitle("§cSorry!", "§cBetter luck next time!");        }
    }
}
