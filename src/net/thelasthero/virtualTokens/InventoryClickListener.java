/*  1:   */ package net.thelasthero.virtualTokens;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.bukkit.configuration.file.FileConfiguration;
/*  5:   */ import org.bukkit.entity.Player;
/*  6:   */ import org.bukkit.event.EventHandler;
/*  7:   */ import org.bukkit.event.Listener;
/*  8:   */ import org.bukkit.event.inventory.InventoryClickEvent;
/*  9:   */ import org.bukkit.inventory.Inventory;
/* 10:   */ 
/* 11:   */ public class InventoryClickListener
/* 12:   */   implements Listener
/* 13:   */ {
/* 14:   */   private nTokens instance;
/* 15:   */   private ActionHandler actionhandler;
/* 16:   */   
/* 17:   */   public InventoryClickListener(nTokens instance)
/* 18:   */   {
/* 19:16 */     this.instance = instance;
/* 20:17 */     this.actionhandler = new ActionHandler(instance);
/* 21:   */   }
/* 22:   */   
/* 23:   */   @EventHandler
/* 24:   */   public void onInventoryClick(InventoryClickEvent event)
/* 25:   */     throws IOException
/* 26:   */   {
/* 27:22 */     if (event.getInventory().getTitle().equals(this.instance.config.getString("shop.name")))
/* 28:   */     {
/* 29:23 */       event.setCancelled(true);
/* 30:24 */       int slotNumber = event.getSlot() + 1;
/* 31:25 */       this.actionhandler.executeAction(slotNumber, (Player)event.getWhoClicked(), this.instance);
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Users\tony\Downloads\VirtualTokens(1).jar
 * Qualified Name:     org.netrocraft.ntokens.InventoryClickListener
 * JD-Core Version:    0.7.0.1
 */