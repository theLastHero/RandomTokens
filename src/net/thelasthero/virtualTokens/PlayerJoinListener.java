/*  1:   */ package net.thelasthero.virtualTokens;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.bukkit.configuration.file.YamlConfiguration;
/*  5:   */ import org.bukkit.entity.Player;
/*  6:   */ import org.bukkit.event.EventHandler;
/*  7:   */ import org.bukkit.event.Listener;
/*  8:   */ import org.bukkit.event.player.PlayerJoinEvent;
/*  9:   */ 
/* 10:   */ public class PlayerJoinListener
/* 11:   */   implements Listener
/* 12:   */ {
/* 13:   */   nTokens instance;
/* 14:   */   
/* 15:   */   public PlayerJoinListener(nTokens instance)
/* 16:   */   {
/* 17:17 */     this.instance = instance;
/* 18:   */   }
/* 19:   */   
/* 20:   */   @EventHandler
/* 21:   */   public void onPlayerJoin(PlayerJoinEvent event)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:21 */     Player player = event.getPlayer();
/* 25:23 */     if (!this.instance.getPlayerData().contains(player.getUniqueId() + ".tokens")) {
/* 26:24 */       this.instance.getTokenManager().addTokenProfile(TokensManager.CONFIG_MODE, player);
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\tony\Downloads\VirtualTokens(1).jar
 * Qualified Name:     org.netrocraft.ntokens.PlayerJoinListener
 * JD-Core Version:    0.7.0.1
 */