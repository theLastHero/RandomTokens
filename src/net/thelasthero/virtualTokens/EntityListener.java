package net.thelasthero.virtualTokens;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener{

	private nTokens instance;
	
	public EntityListener(nTokens instance)
	{
		this.instance = instance;
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		
		if (event.getEntity().getKiller() == null) return;
		if (!(event.getEntity().getKiller() instanceof Player)) return;
		
		//build a token
		ItemStack paperToken = new ItemStack(Material.PAPER);
		paperToken.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		
		int dropChance = 2000;
		
		Player player = event.getEntity().getKiller().getPlayer();
		
		if(player.hasPermission("thelasthero.bonustokenchance")){
			dropChance = 1500;
		}
		
		
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(dropChance);
		
		if(randomInt == 11){
			//event.getBlock().getDrops().clear();
			
			event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), paperToken);
			//event.getEntity().getKiller().sendMessage(ChatColor.GOLD + "[TOKEN] " + ChatColor.GREEN + "A token just dropped from that thing you just killed!");
		}
		}
	
}
