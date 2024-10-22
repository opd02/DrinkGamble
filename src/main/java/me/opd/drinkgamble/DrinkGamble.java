package me.opd.drinkgamble;

import me.opd.drinkgamble.commands.SummonCommand;
import me.opd.drinkgamble.listeners.ItemClickListener;
import me.opd.drinkgamble.listeners.ItemConsumeListener;
import me.opd.drinkgamble.listeners.VillagerClickListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DrinkGamble extends JavaPlugin {

    public static Economy economy;
    public static DrinkGamble instance;
    public static String prefix;

    @Override
    public void onEnable() {
        instance = this;
        prefix = "§7§l[§a§lGAMBLE§7§l] §r";
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("[DrinkGamble] Could not setup economy plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("summongamble").setExecutor(new SummonCommand());

        Bukkit.getServer().getPluginManager().registerEvents(new VillagerClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemConsumeListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ItemClickListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
