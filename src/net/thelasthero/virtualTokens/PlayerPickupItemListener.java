package net.thelasthero.virtualTokens;

import java.io.IOException;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickupItemListener implements Listener{

	private nTokens instance;
	
	public PlayerPickupItemListener(nTokens instance)
	{
		this.instance = instance;
	}
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {

		ItemStack item = event.getItem().getItemStack();
		
		if(item != null){
			
			
			//build a token
			ItemStack paperToken = new ItemStack(Material.PAPER);
			paperToken.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			
			
			if (item.equals(paperToken)){
				
				Player player = event.getPlayer();
				
				int numTokensChance= 12;
				
				Random randomGenerator = new Random();
				int numTokens = randomGenerator.nextInt(numTokensChance);
				
				event.setCancelled(true);
				event.getItem().remove();
				player.sendMessage(ChatColor.GOLD + "[TOKEN] " + ChatColor.GREEN + "You just picked up " + numTokens +" tokens!");
				
				try {
					instance.getTokenManager().setTokens(TokensManager.CONFIG_MODE, player, (numTokens+1), 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		
		
		}
	
}
