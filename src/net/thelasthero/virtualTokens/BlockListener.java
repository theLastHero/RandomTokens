package net.thelasthero.virtualTokens;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener{

	private nTokens instance;
	
	public BlockListener(nTokens instance)
	{
		this.instance = instance;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		
		
		int dropChance = 2000;
		
		Player player = event.getPlayer();
		if(player.hasPermission("thelasthero.bonustokenchance")){
			dropChance = 1500;
		}
		

		//build a token
		ItemStack paperToken = new ItemStack(Material.PAPER);
		paperToken.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(dropChance);
		
		if(randomInt == 1){
			//event.getBlock().getDrops().clear();
			event.getBlock().setType(Material.AIR);
			event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), paperToken);
			//event.getPlayer().sendMessage(ChatColor.GOLD + "[TOKEN] " + ChatColor.GREEN + "A token just dropped from mining that block!");
		}
		}
	
}
