package me.opd.drinkgamble.commands;

import me.opd.drinkgamble.GPotion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

public class SummonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
        if(cmd.getName().equalsIgnoreCase("summongamble")){

            if(!(sender instanceof Player)) return true;

            Player p = (Player) sender;

            if(!p.hasPermission("voyagegamble.summon")){
                p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                return true;
            }

            summonGambler(p);

            GPotion potion = new GPotion(10000,0.5d, new int[]{204, 0, 204});
            p.getInventory().addItem(potion.getItemStack());

            return true;
        }
        return false;
    }

    public static void summonGambler(Player p){
        Villager v = (Villager) p.getWorld().spawnEntity(p.getLocation(), EntityType.VILLAGER);
        v.setCustomName("§b§lGambler");
        v.setCustomNameVisible(true);
        v.setCanPickupItems(false);
        v.setProfession(Villager.Profession.WEAPONSMITH);
        v.setInvulnerable(true);
        v.setSilent(true);
        v.setAI(false);
        p.sendMessage(ChatColor.GREEN + "Gambler summoned!");
    }

}
