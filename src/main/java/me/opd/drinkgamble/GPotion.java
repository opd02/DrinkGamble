package me.opd.drinkgamble;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class GPotion {

    private double costAmount;
    private double winAmount;
    private double winChance;
    private int[] colorRGB;

    public GPotion(String string){
        this(Double.parseDouble(string.split(",")[2]),
                Double.parseDouble(string.split(",")[1]),
                new int[]{1,1,1},
                Double.parseDouble(string.split(",")[0]));
    }

    public GPotion(double costAmount, double winChance, int[] colorRGB) {
        this(costAmount, winChance, colorRGB, costAmount * 2);
    }

    public GPotion(double costAmount, double winChance, int[] colorRGB, double winAmount) {
        this.costAmount = costAmount;
        this.winChance = winChance;
        this.colorRGB = colorRGB;
        this.winAmount = winAmount;
    }

    public ItemStack getItemStack(){
        ItemStack returnItem = new ItemStack(Material.POTION,1);
        PotionMeta potionMeta = (PotionMeta) returnItem.getItemMeta();
        potionMeta.setColor(Color.fromRGB(colorRGB[0],colorRGB[1],colorRGB[2]));
        ItemMeta itemMeta = potionMeta;
        potionMeta.setBasePotionType(PotionType.WATER);
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);

        String hex = String.format("#%02x%02x%02x", colorRGB[0], colorRGB[1], colorRGB[2]);
        DecimalFormat df = new DecimalFormat("0.0");

        itemMeta.setDisplayName(net.md_5.bungee.api.ChatColor.of(hex) + "§l$" + NumberFormat.getInstance(Locale.US).format(this.costAmount)
                + " Gamble Potion");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§7Gamble potions can be obtained from");
        lore.add("§7the villager at spawn or in crates.");
        lore.add("");
        lore.add("§r§a"+ df.format(this.winChance * 100) + "% Win Chance");
        lore.add("§c"+ df.format((1 - this.winChance) * 100) + "% Loss Chance");
        lore.add("");
        lore.add("§r§eWin Amount: §6§l$" + NumberFormat.getInstance(Locale.US).format(this.winAmount));
        lore.add("§f§l(!) Drink this potion for a chance to win");
        itemMeta.setLore(lore);

        NamespacedKey nsk = new NamespacedKey(DrinkGamble.instance, "gamblePotion");
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        data.set(nsk, PersistentDataType.STRING, this.toString());

        returnItem.setItemMeta(itemMeta);
        return returnItem;
    }

    public double getCostAmount() {
        return costAmount;
    }

    public double getWinAmount() {
        return winAmount;
    }

    public double getWinChance() {
        return winChance;
    }

    public int[] getcolorRGB() {
        return colorRGB;
    }

    @Override
    public String toString() {
        return winAmount + "," + winChance + "," + costAmount;
    }
}
