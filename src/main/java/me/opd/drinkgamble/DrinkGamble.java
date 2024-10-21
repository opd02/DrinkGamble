package me.opd.drinkgamble;

import me.opd.drinkgamble.commands.SummonCommand;
import me.opd.drinkgamble.listeners.VillagerClickListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DrinkGamble extends JavaPlugin {

    public static Economy economy;

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            Bukkit.getLogger().severe("[DrinkGamble] Could not setup economy plugin.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.getCommand("summongamble").setExecutor(new SummonCommand());

        Bukkit.getServer().getPluginManager().registerEvents(new VillagerClickListener(), this);
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
