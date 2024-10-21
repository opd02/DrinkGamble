package me.opd.drinkgamble.listeners;

import me.opd.drinkgamble.GPotion;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VillagerClickListener implements Listener {
    @EventHandler
    public void onPlayerVillagerInteract(PlayerInteractEntityEvent e){

        Player p = e.getPlayer();
        Entity ent = e.getRightClicked();

        if(!(ent instanceof Villager)) return;

        if(ent.getCustomName() != null && ent.getCustomName().equalsIgnoreCase("§2§lGambler")){
            openGambleGUI(p);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPunchVillager(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Villager) || !(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getDamager();

        if(p.getGameMode().equals(GameMode.CREATIVE)) return;

        if(e.getEntity().getCustomName() != null && e.getEntity().getCustomName().equalsIgnoreCase("§2§lGambler")){
            openGambleGUI(p);
            e.setCancelled(true);
        }
    }

    private void openGambleGUI(Player p){
        p.playSound(p.getLocation(), Sound.BLOCK_CHEST_OPEN, (float) 0.4, (float) 1.5);

        Inventory gui = Bukkit.createInventory(p, 9, "§a§lGAMBLE MENU§7 - Select a potion");

        ItemStack blueGlass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta blueGlassMeta = blueGlass.getItemMeta();
        blueGlassMeta.setDisplayName(" ");
        blueGlass.setItemMeta(blueGlassMeta);

        gui.setItem(0, blueGlass);
        gui.setItem(1, blueGlass);
        gui.setItem(7, blueGlass);
        gui.setItem(8, blueGlass);

        gui.setItem(2, new GPotion(100000,0.5d, new int[]{155, 93, 229}).getItemStack());
        gui.setItem(3,new GPotion(250000,0.45d, new int[]{241, 91, 181}).getItemStack());
        gui.setItem(4,new GPotion(750000,0.4d, new int[]{254, 228, 64}).getItemStack());
        gui.setItem(5,new GPotion(1000000,0.35d, new int[]{0, 187, 249}).getItemStack());
        gui.setItem(6,new GPotion(2000000,0.3d, new int[]{0, 245, 212}).getItemStack());


        p.openInventory(gui);

    }
}
