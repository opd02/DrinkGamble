package me.opd.drinkgamble;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GPotion {

    private double costAmount;
    private double winAmount;
    private double winChange;
    private int[] colorRGB;

    public GPotion(double costAmount, double winChange, int[] colorRGB) {
        this(costAmount, winChange, colorRGB, costAmount * 2);
    }

    public GPotion(double costAmount, double winChange, int[] colorRGB, double winAmount) {
        this.costAmount = costAmount;
        this.winChange = winChange;
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

        itemMeta.setDisplayName(ChatColor.BOLD + "" + net.md_5.bungee.api.ChatColor.of(hex) + "$" + NumberFormat.getInstance(Locale.US).format(this.costAmount)
                + " Gamble Potion");

        itemMeta.setLore(new ArrayList<>(List.of("§7Gamble potions are obtained from",
                "§7the villager at spawn.","",
                "§r§a§l58% Win chance", "§c§l42% Loss chance","","§r§8Original Cost: 100,000",
                "§f§l(!) Drink this potion to gamble its value")));

        returnItem.setItemMeta(itemMeta);
        return returnItem;
    }

    public double getCostAmount() {
        return costAmount;
    }

    public double getWinAmount() {
        return winAmount;
    }

    public double getWinChange() {
        return winChange;
    }

    public int[] getcolorRGB() {
        return colorRGB;
    }
}
