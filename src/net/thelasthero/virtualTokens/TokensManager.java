/*  1:   */ package net.thelasthero.virtualTokens;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.bukkit.configuration.file.YamlConfiguration;
/*  5:   */ import org.bukkit.entity.Player;
/*  6:   */ 
/*  7:   */ public class TokensManager
/*  8:   */ {
/*  9:   */   nTokens instance;
/* 10:10 */   public static int CONFIG_MODE = 1;
/* 11:11 */   public static int MYSQL_MODE = 2;
/* 12:   */   
/* 13:   */   public TokensManager(nTokens instance)
/* 14:   */   {
/* 15:14 */     this.instance = instance;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getTokens(int mode, Player player)
/* 19:   */   {
/* 20:18 */     if (mode == CONFIG_MODE) {
/* 21:19 */       return 1;
/* 22:   */     }
/* 23:22 */     if (mode == MYSQL_MODE) {
/* 24:23 */       return 2;
/* 25:   */     }
/* 26:26 */     return 0;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setTokens(int mode, Player player, int amount, int addMode)
/* 30:   */     throws IOException
/* 31:   */   {
/* 32:30 */     if (mode == CONFIG_MODE)
/* 33:   */     {
/* 34:32 */       if (addMode == 3) {
/* 35:33 */         this.instance.setNode(player, amount);
/* 36:   */       }
/* 37:36 */       if (addMode == 1)
/* 38:   */       {
/* 39:37 */         int originalAmount = this.instance.getPlayerData().getInt(player.getUniqueId() + ".tokens");
/* 40:38 */         this.instance.setNode(player, originalAmount + amount);
/* 41:   */       }
/* 42:41 */       if (addMode == 2)
/* 43:   */       {
/* 44:42 */         int originalAmount = this.instance.getPlayerData().getInt(player.getUniqueId() + ".tokens");
/* 45:43 */         this.instance.setNode(player, originalAmount - amount);
/* 46:   */       }
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setTokens(int mode, String user, int amount, int addMode)
/* 51:   */     throws IOException
/* 52:   */   {
/* 53:49 */     if (mode == CONFIG_MODE)
/* 54:   */     {
/* 55:51 */       if (addMode == 3) {
/* 56:52 */         this.instance.setNode(user, amount);
/* 57:   */       }
/* 58:55 */       if (addMode == 1)
/* 59:   */       {
/* 60:56 */         int originalAmount = this.instance.getPlayerData().getInt(user + ".tokens");
/* 61:57 */         this.instance.setNode(user, originalAmount + amount);
/* 62:   */       }
/* 63:60 */       if (addMode == 2)
/* 64:   */       {
/* 65:61 */         int originalAmount = this.instance.getPlayerData().getInt(user + ".tokens");
/* 66:62 */         this.instance.setNode(user, originalAmount - amount);
/* 67:   */       }
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void addTokenProfile(int mode, Player player)
/* 72:   */     throws IOException
/* 73:   */   {
/* 74:70 */     if (mode == CONFIG_MODE) {
/* 75:71 */       this.instance.setNode(player, 0);
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Users\tony\Downloads\VirtualTokens(1).jar
 * Qualified Name:     org.netrocraft.ntokens.TokensManager
 * JD-Core Version:    0.7.0.1
 */