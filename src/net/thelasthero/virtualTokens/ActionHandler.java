/*  1:   */ package net.thelasthero.virtualTokens;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.bukkit.Bukkit;
/*  5:   */ import org.bukkit.ChatColor;
/*  6:   */ import org.bukkit.Server;
/*  7:   */ import org.bukkit.configuration.file.FileConfiguration;
/*  8:   */ import org.bukkit.configuration.file.YamlConfiguration;
/*  9:   */ import org.bukkit.entity.Player;
/* 10:   */ 
/* 11:   */ public class ActionHandler
/* 12:   */ {
/* 13:   */   nTokens instance;
/* 14:   */   
/* 15:   */   public ActionHandler(nTokens instance)
/* 16:   */   {
/* 17:15 */     this.instance = instance;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void executeAction(int slotNumber, Player whoClicked, nTokens instance)
/* 21:   */     throws IOException
/* 22:   */   {
/* 23:18 */     String execution = instance.config.getString("execution." + slotNumber);
/* 24:19 */     if ((execution != null) && 
/* 25:20 */       (instance.getConfig().isSet("itemcost." + slotNumber)))
/* 26:   */     {
/* 27:21 */       int tokens = instance.playerData.getInt(whoClicked.getUniqueId() + ".tokens");
/* 28:22 */       String itemCost = instance.getConfig().getString("itemcost." + slotNumber);
/* 29:23 */       int cost = Integer.parseInt(itemCost);
/* 30:25 */       if (tokens >= cost)
/* 31:   */       {
/* 32:26 */         instance.getTokenManager().setTokens(TokensManager.CONFIG_MODE, whoClicked, cost, 2);
/* 33:27 */         instance.getServer().dispatchCommand(instance.getServer().getConsoleSender(), execution.replace("[player]", Bukkit.getServer().getPlayer(whoClicked.getUniqueId()).getName()));
/* 34:   */       }
/* 35:   */       else
/* 36:   */       {
/* 37:29 */         whoClicked.sendMessage(ChatColor.WHITE + "[" + ChatColor.LIGHT_PURPLE + "nTokens" + ChatColor.WHITE + "] " + ChatColor.RED + "Sorry, you do not have enough money to buy this item. To get nTokens please /vote or /bump.");
/* 38:   */       }
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\tony\Downloads\VirtualTokens(1).jar
 * Qualified Name:     org.netrocraft.ntokens.ActionHandler
 * JD-Core Version:    0.7.0.1
 */