/*   1:    */ package net.thelasthero.virtualTokens;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.IOException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
/*  10:    */ import org.bukkit.Bukkit;
/*  11:    */ import org.bukkit.ChatColor;
import org.bukkit.Location;
/*  12:    */ import org.bukkit.Material;
import org.bukkit.World;
/*  15:    */ import org.bukkit.command.Command;
/*  16:    */ import org.bukkit.command.CommandSender;
/*  17:    */ import org.bukkit.configuration.file.FileConfiguration;
/*  19:    */ import org.bukkit.configuration.file.YamlConfiguration;
/*  20:    */ import org.bukkit.enchantments.Enchantment;
/*  21:    */ import org.bukkit.entity.Player;
/*  22:    */ import org.bukkit.inventory.Inventory;
/*  23:    */ import org.bukkit.inventory.ItemStack;
/*  24:    */ import org.bukkit.inventory.meta.ItemMeta;
/*  26:    */ import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
/*   5:    */ 
/*   8:    */ 
/*  13:    */ 
/*  14:    */ 
/*  18:    */ 
/*  25:    */

/*  27:    */ 
/*  28:    */ public class nTokens
/*  29:    */   extends JavaPlugin
/*  30:    */ {
/*  31:    */   Inventory shop;
/*  32:    */   FileConfiguration config;
/*  33:    */   TokensManager tokenManager;
/*  34:    */   YamlConfiguration playerData;
/*  35:    */   File customYML;
/*  36:    */   
/*  37:    */   public void onEnable()
/*  38:    */   {
/*  39: 35 */     getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
/*  40: 36 */     getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
/*  40: 36 */     getServer().getPluginManager().registerEvents(new BlockListener(this), this);
/*  40: 36 */     getServer().getPluginManager().registerEvents(new EntityListener(this), this);
/*  40: 36 */     getServer().getPluginManager().registerEvents(new PlayerPickupItemListener(this), this);
/*  41:    */     
/*  42: 38 */     this.config = getConfig();
/*  43:    */     
/*  44: 40 */     File pf = new File(getDataFolder() + "\\config.yml");
/*  45: 42 */     if (!pf.exists())
/*  46:    */     {
/*  47: 43 */       System.out.println("[vTokens] CONFIG DOESN'T EXIST. CREATING ONE NOW!");
/*  48: 44 */       this.config.options().copyDefaults(true);
/*  49: 45 */       saveDefaultConfig();
/*  50:    */     }
/*  51: 48 */     this.tokenManager = new TokensManager(this);
/*  52: 49 */     System.out.println("[vTokens] Has started up!");
/*  53: 50 */     this.shop = Bukkit.createInventory(null, this.config.getInt("shop.slots"), this.config.getString("shop.name"));

					randomLocDropToken();
/*  54:    */     
/*  55: 52 */     loadItems(this.config);
/*  56:    */     try
/*  57:    */     {
/*  58: 54 */       loadCustomYML("playerdata");
/*  59:    */     }
/*  60:    */     catch (IOException e)
/*  61:    */     {
/*  62: 56 */       e.printStackTrace();
/*  63:    */     }

				 
/*  64:    */   }
/*  65:    */   
/*  66:    */   private void loadCustomYML(String fileName)
/*  67:    */     throws IOException
/*  68:    */   {
/*  69: 61 */     this.customYML = new File(getDataFolder() + "//" + fileName + ".yml");
/*  70: 63 */     if (!this.customYML.exists()) {
/*  71: 64 */       this.customYML.createNewFile();
/*  72:    */     }
/*  73: 67 */     this.playerData = YamlConfiguration.loadConfiguration(this.customYML);
/*  74: 68 */     this.playerData.save(this.customYML);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setNode(Player player, int amount)
/*  78:    */     throws IOException
/*  79:    */   {
/*  80: 74 */     this.playerData.set(player.getUniqueId() + ".tokens", Integer.valueOf(amount));
/*  81: 75 */     this.playerData.save(this.customYML);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setNode(String user, int amount)
/*  85:    */     throws IOException
/*  86:    */   {
/*  87: 82 */     this.playerData.set(user + ".tokens", Integer.valueOf(amount));
/*  88: 83 */     this.playerData.save(this.customYML);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void onDisable()
/*  92:    */   {
/*  93: 87 */     System.out.println("[vTokens v2] Has been disabled!");
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void loadItems(FileConfiguration config)
/*  97:    */   {
/*  98: 93 */     int items = config.getInt("shop.slots");
/*  99: 94 */     for (int i = 1; i <= items; i++)
/* 100:    */     {
/* 101: 95 */       boolean durability = false;
/* 102: 97 */       if (config.isSet("slots." + i))
/* 103:    */       {
/* 104: 98 */         String itemNumber = config.getString("slots." + i);
/* 105: 99 */         if (itemNumber.contains(":")) {
/* 106:100 */           durability = true;
/* 107:    */         }
/* 108:103 */         if (!durability)
/* 109:    */         {
/* 110:104 */           this.shop.setItem(i - 1, new ItemStack(Integer.parseInt(itemNumber), 1));
/* 111:    */         }
/* 112:    */         else
/* 113:    */         {
/* 114:106 */           String delimeter = ":";
/* 115:107 */           String[] itemNumberParts = itemNumber.split(delimeter);
/* 116:    */           
/* 117:109 */           Material itemMaterial = Material.getMaterial(Integer.parseInt(itemNumberParts[0]));
/* 118:110 */           this.shop.setItem(i - 1, new ItemStack(itemMaterial, 1, (byte)Integer.parseInt(itemNumberParts[1])));
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:117 */     for (int i = 1; i <= items; i++) {
/* 123:119 */       if (config.isSet("itemname." + i))
/* 124:    */       {
/* 125:120 */         String itemName = config.getString("itemname." + i);
/* 126:121 */         ItemStack item = this.shop.getItem(i - 1);
/* 127:122 */         ItemMeta itemMeta = item.getItemMeta();
/* 128:123 */         itemMeta.setDisplayName(itemName);
/* 129:    */         
/* 130:125 */         item.setItemMeta(itemMeta);
/* 131:126 */         this.shop.setItem(i - 1, item);
/* 132:    */       }
/* 133:    */     }
/* 134:131 */     for (int i = 1; i <= items; i++)
/* 135:    */     {
/* 136:133 */       List<String> itemData = new ArrayList<String>();
/* 137:134 */       if (config.isSet("itemamount." + i))
/* 138:    */       {
/* 139:135 */         String itemAmount = config.getString("itemamount." + i);
/* 140:136 */         itemData.add(ChatColor.DARK_PURPLE + "Amount: " + ChatColor.AQUA + itemAmount);
/* 141:    */       }
/* 142:138 */       if (config.isSet("itemcost." + i))
/* 143:    */       {
/* 144:139 */         String itemCost = config.getString("itemcost." + i);
/* 145:140 */         itemData.add(ChatColor.GREEN + "Price: " + ChatColor.AQUA + itemCost + ChatColor.DARK_PURPLE + " Tokens");
/* 146:    */       }
/* 147:143 */       if ((config.isSet("itemamount." + i)) || (config.isSet("itemcost." + i)))
/* 148:    */       {
/* 149:144 */         ItemStack item = this.shop.getItem(i - 1);
/* 150:145 */         ItemMeta itemMeta = item.getItemMeta();
/* 151:146 */         itemMeta.setLore(itemData);
/* 152:147 */         item.setItemMeta(itemMeta);
/* 153:148 */         this.shop.setItem(i - 1, item);
/* 154:    */       }
/* 155:    */     }
/* 156:153 */     for (int i = 1; i <= items; i++) {
/* 157:155 */       if (config.isSet("enchantment." + i))
/* 158:    */       {
/* 159:156 */         String entry = config.getString("enchantment." + i);
/* 160:    */         
/* 161:158 */         int amountOfEntries = StringUtils.countMatches(entry, ",");
/* 162:159 */         amountOfEntries++;
/* 163:    */         
/* 164:161 */         String delimeter = ",";
/* 165:162 */         String[] entryParts = entry.split(delimeter);
/* 166:164 */         if (amountOfEntries != 0)
/* 167:    */         {
/* 168:166 */           ItemStack item = this.shop.getItem(i - 1);
/* 169:167 */           ItemMeta itemMeta = item.getItemMeta();
/* 170:169 */           for (int x = 1; x <= amountOfEntries; x++)
/* 171:    */           {
/* 172:170 */             String enchantmentDelimeter = ":";
/* 173:171 */             String fullEnchantment = entryParts[(x - 1)].trim();
/* 174:172 */             String[] enchantmentParts = fullEnchantment.split(enchantmentDelimeter);
/* 175:173 */             int enchantmentLevel = Integer.parseInt(enchantmentParts[1]);
/* 176:174 */             String enchantment = enchantmentParts[0];
/* 177:176 */             if (enchantment.equalsIgnoreCase("Infinity")) {
/* 178:177 */               itemMeta.addEnchant(Enchantment.ARROW_INFINITE, enchantmentLevel, true);
/* 179:    */             }
/* 180:179 */             if (enchantment.equalsIgnoreCase("Unbreaking")) {
/* 181:180 */               itemMeta.addEnchant(Enchantment.DURABILITY, enchantmentLevel, true);
/* 182:    */             }
/* 183:182 */             if (enchantment.equalsIgnoreCase("Flame")) {
/* 184:183 */               itemMeta.addEnchant(Enchantment.ARROW_FIRE, enchantmentLevel, true);
/* 185:    */             }
/* 186:185 */             if (enchantment.equalsIgnoreCase("Punch")) {
/* 187:186 */               itemMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, enchantmentLevel, true);
/* 188:    */             }
/* 189:188 */             if (enchantment.equalsIgnoreCase("Sharpness")) {
/* 190:189 */               itemMeta.addEnchant(Enchantment.DAMAGE_ALL, enchantmentLevel, true);
/* 191:    */             }
/* 192:191 */             if ((enchantment.equalsIgnoreCase("Arthropods")) || (enchantment.equalsIgnoreCase("Bane of Arthropods"))) {
/* 193:192 */               itemMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, enchantmentLevel, true);
/* 194:    */             }
/* 195:194 */             if (enchantment.equalsIgnoreCase("Smite")) {
/* 196:195 */               itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, enchantmentLevel, true);
/* 197:    */             }
/* 198:197 */             if (enchantment.equalsIgnoreCase("Efficiency")) {
/* 199:198 */               itemMeta.addEnchant(Enchantment.DIG_SPEED, enchantmentLevel, true);
/* 200:    */             }
/* 201:200 */             if (enchantment.equalsIgnoreCase("Smite")) {
/* 202:201 */               itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, enchantmentLevel, true);
/* 203:    */             }
/* 204:203 */             if ((enchantment.equalsIgnoreCase("FireAspect")) || (enchantment.equalsIgnoreCase("Fire Aspect"))) {
/* 205:204 */               itemMeta.addEnchant(Enchantment.FIRE_ASPECT, enchantmentLevel, true);
/* 206:    */             }
/* 207:206 */             if (enchantment.equalsIgnoreCase("Knockback")) {
/* 208:207 */               itemMeta.addEnchant(Enchantment.KNOCKBACK, enchantmentLevel, true);
/* 209:    */             }
/* 210:209 */             if (enchantment.equalsIgnoreCase("Looting")) {
/* 211:210 */               itemMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, enchantmentLevel, true);
/* 212:    */             }
/* 213:212 */             if ((enchantment.equalsIgnoreCase("Looting Blocks")) || (enchantment.equalsIgnoreCase("LootingBlocks"))) {
/* 214:213 */               itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, enchantmentLevel, true);
/* 215:    */             }
/* 216:215 */             if ((enchantment.equalsIgnoreCase("Respiration")) || (enchantment.equalsIgnoreCase("Oxygen"))) {
/* 217:216 */               itemMeta.addEnchant(Enchantment.OXYGEN, enchantmentLevel, true);
/* 218:    */             }
/* 219:219 */             if ((enchantment.equalsIgnoreCase("Projectile Protection")) || (enchantment.equalsIgnoreCase("ProjectileProtection")) || (enchantment.equalsIgnoreCase("ProjProtection")) || (enchantment.equalsIgnoreCase("Projectile_Protection"))) {
/* 220:220 */               itemMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, enchantmentLevel, true);
/* 221:    */             }
/* 222:223 */             if ((enchantment.equalsIgnoreCase("Blast Protection")) || (enchantment.equalsIgnoreCase("BlastProtection")) || (enchantment.equalsIgnoreCase("BlastProtection")) || (enchantment.equalsIgnoreCase("Blast"))) {
/* 223:224 */               itemMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, enchantmentLevel, true);
/* 224:    */             }
/* 225:227 */             if ((enchantment.equalsIgnoreCase("Feather")) || (enchantment.equalsIgnoreCase("FeatherFalling")) || (enchantment.equalsIgnoreCase("Feather Falling")) || (enchantment.equalsIgnoreCase("Protection_Fall"))) {
/* 226:228 */               itemMeta.addEnchant(Enchantment.PROTECTION_FALL, enchantmentLevel, true);
/* 227:    */             }
/* 228:231 */             if ((enchantment.equalsIgnoreCase("Protection_Fire")) || (enchantment.equalsIgnoreCase("FireProtection")) || (enchantment.equalsIgnoreCase("FireProt")) || (enchantment.equalsIgnoreCase("Fire Protection"))) {
/* 229:232 */               itemMeta.addEnchant(Enchantment.PROTECTION_FIRE, enchantmentLevel, true);
/* 230:    */             }
/* 231:235 */             if ((enchantment.equalsIgnoreCase("Protection")) || (enchantment.equalsIgnoreCase("Prot"))) {
/* 232:236 */               itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, enchantmentLevel, true);
/* 233:    */             }
/* 234:239 */             if ((enchantment.equalsIgnoreCase("Silk_Touch")) || (enchantment.equalsIgnoreCase("SilkTouch")) || (enchantment.equalsIgnoreCase("Silk Touch"))) {
/* 235:240 */               itemMeta.addEnchant(Enchantment.SILK_TOUCH, enchantmentLevel, true);
/* 236:    */             }
/* 237:243 */             if (enchantment.equalsIgnoreCase("Thorns")) {
/* 238:244 */               itemMeta.addEnchant(Enchantment.THORNS, enchantmentLevel, true);
/* 239:    */             }
/* 240:247 */             if ((enchantment.equalsIgnoreCase("Water_Worker")) || (enchantment.equalsIgnoreCase("Aqua_Affinity")) || (enchantment.equalsIgnoreCase("Aqua Affinity"))) {
/* 241:248 */               itemMeta.addEnchant(Enchantment.WATER_WORKER, enchantmentLevel, true);
/* 242:    */             }
/* 243:251 */             item.setItemMeta(itemMeta);
/* 244:252 */             this.shop.setItem(i - 1, item);
/* 245:    */           }
/* 246:    */         }
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/* 252:    */   {
/* 253:261 */     if ((sender instanceof Player))
/* 254:    */     {
/* 255:262 */       if ((cmd.getName().equalsIgnoreCase("redeemtokens")))
/* 256:    */       {
/* 257:263 */         Player player = (Player)sender;
/* 258:264 */         openShop(player);
/* 259:265 */         return true;
/* 260:    */       }

/* 261:268 */       if ((cmd.getName().equalsIgnoreCase("vtokens")) || (cmd.getName().equalsIgnoreCase("tokens")))
/* 262:    */       {
						Player player = (Player)sender;
						if(args.length > 0 && args[0].equalsIgnoreCase("show")){
							
							 int tokens = this.playerData.getInt(player.getUniqueId() + ".tokens");
							player.sendMessage(ChatColor.GREEN + "-------------------------------");					  
							player.sendMessage(ChatColor.DARK_GRAY + "the" + ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "Last" + ChatColor.RED + "Hero " + ChatColor.WHITE + "- " + ChatColor.GOLD +" TOKENS!" );
							player.sendMessage(ChatColor.GREEN + "-------------------------------");
							player.sendMessage("You have " + ChatColor.GOLD + tokens + ChatColor.WHITE + " tokens!");
							return true;
						} else {
							

							player.sendMessage(ChatColor.GREEN + "-------------------------------");					  
							player.sendMessage(ChatColor.DARK_GRAY + "the" + ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "Last" + ChatColor.RED + "Hero " + ChatColor.WHITE + "- " + ChatColor.GOLD +" TOKENS!" );
							player.sendMessage(ChatColor.GREEN + "-------------------------------");
							player.sendMessage(ChatColor.GREEN + "/tokens show " + ChatColor.GRAY + " - shows how many tokens you have");
							player.sendMessage(ChatColor.GREEN + "/redeemtokens" + ChatColor.GRAY + " - buy rewards with tokens");
							player.sendMessage(" ");
							player.sendMessage(ChatColor.GOLD+ "Tokens can be gained through voting for our server.");
							player.sendMessage(ChatColor.GOLD + "There is also a small chance of gaining tokens when you kill a mob, player or mining a block! Sometimes you may just find a token or bunch of tokens laying on the ground as you explore the world.");
							return true;
						}
/* 263:269 */         
/* 267:    */       }

/* 268:275 */       if (((sender.hasPermission("vtokens.givetokens")) || (sender.isOp())) && 
/* 269:276 */         (cmd.getName().equalsIgnoreCase("givetokens")) && (args.length == 2))
/* 270:    */       {
/* 271:279 */         boolean playerExists = false;
/* 272:280 */         String user = null;
/* 273:281 */         Player player = Bukkit.getServer().getPlayer(args[0]);
/* 274:282 */         if (player != null)
/* 275:    */         {
/* 276:283 */           user = player.getUniqueId().toString();
/* 277:284 */           playerExists = getPlayerData().isSet(player.getUniqueId() + ".tokens");
/* 278:    */         }
/* 279:    */         else
/* 280:    */         {
/* 281:287 */           String username = args[0];
/* 282:288 */           user = Bukkit.getOfflinePlayer(username).getUniqueId().toString();
/* 283:289 */           playerExists = getPlayerData().isSet(user + ".tokens");
/* 284:    */         }
/* 285:292 */         if (playerExists)
/* 286:    */         {
/* 287:293 */           int amount = Integer.parseInt(args[1]);
/* 288:    */           try
/* 289:    */           {
/* 290:296 */             getTokenManager().setTokens(TokensManager.CONFIG_MODE, user, amount, 1);
/* 291:    */           }
/* 292:    */           catch (IOException e)
/* 293:    */           {
/* 294:298 */             e.printStackTrace();
/* 295:    */           }
/* 296:    */         }
/* 297:    */         else
/* 298:    */         {
/* 299:301 */           sender.sendMessage(ChatColor.RED + "player: " + ChatColor.GOLD + args[0] + ChatColor.RED + " doesn't have a virtual tokens profile.");
/* 300:    */         }
/* 301:    */       }
/* 302:    */     }
/* 303:307 */     else if ((cmd.getName().equalsIgnoreCase("givetokens")) && (args.length == 2))
/* 304:    */     {
/* 305:310 */       boolean playerExists = false;
/* 306:    */       
/* 307:312 */       String user = null;
/* 308:313 */       String username = args[0];
/* 309:    */       
/* 310:315 */       Player player = Bukkit.getServer().getPlayer(args[0]);
/* 311:316 */       if (player != null)
/* 312:    */       {
/* 313:317 */         user = player.getUniqueId().toString();
/* 314:318 */         playerExists = getPlayerData().isSet(player.getUniqueId() + ".tokens");
/* 315:    */       }
/* 316:    */       else
/* 317:    */       {
/* 318:320 */         user = Bukkit.getOfflinePlayer(username).getUniqueId().toString();
/* 319:321 */         playerExists = getPlayerData().isSet(user + ".tokens");
/* 320:    */       }
/* 321:325 */       if (playerExists)
/* 322:    */       {
/* 323:326 */         int amount = Integer.parseInt(args[1]);
/* 324:    */         try
/* 325:    */         {
/* 326:329 */           getTokenManager().setTokens(TokensManager.CONFIG_MODE, user, amount, 1);
						player.sendMessage(ChatColor.GREEN + "you just received " + amount + "tokens");
/* 327:    */         }
/* 328:    */         catch (IOException e)
/* 329:    */         {
/* 330:331 */           e.printStackTrace();
/* 331:    */         }
/* 332:    */       }
/* 333:    */       else
/* 334:    */       {
/* 335:335 */         sender.sendMessage("[Tokens] Cannot give tokens to people who don't exist/haven't received token profiles!");
/* 336:    */       }
/* 337:    */     }
/* 338:339 */     return false;
/* 339:    */   }
/* 340:    */   
/* 341:    */   private void openShop(Player player)
/* 342:    */   {
/* 343:343 */     if (player.getInventory() != null) {
/* 344:344 */       player.openInventory(this.shop);
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public YamlConfiguration getPlayerData()
/* 349:    */   {
/* 350:349 */     return this.playerData;
/* 351:    */   }
/* 352:    */   
/* 353:    */   public TokensManager getTokenManager()
/* 354:    */   {
/* 355:352 */     return this.tokenManager;
/* 356:    */   }

public static int generatRandomPositiveNegitiveValue(int max , int min) {
    //Random rand = new Random();
    int ii = -min + (int) (Math.random() * ((max - (-min)) + 1));
    return ii;
}

//-------------------------------------------------------------------
//
//-------------------------------------------------------------------
public void randomLocDropToken() {
	BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
		@Override
		public void run() {

			
			if(getServer().getOnlinePlayers().size() >= 1){

			List<Player> rpl = new ArrayList<Player>();
			 
			for(Player all:getServer().getOnlinePlayers()){
				rpl.add(all.getPlayer());
				}
			
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(rpl.size());
			
			int randomXDistance =+ generatRandomPositiveNegitiveValue(17,88);
			
			int randomZDistance =+ generatRandomPositiveNegitiveValue(24,77);
			
			Player rp = rpl.get(randomInt);
			World world = rp.getWorld();
			//build a token
			ItemStack paperToken = new ItemStack(Material.PAPER);
			paperToken.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
			
			Location loc = rp.getLocation();
			loc.add(randomXDistance, 3, randomZDistance);
			
			double y = world.getHighestBlockAt((int)loc.getX(), (int) loc.getZ()).getY();
			
			Location dropLoc = new Location(world, (int)loc.getX(), (int)(y+5), (int)loc.getZ());
			
			dropLoc.getBlock().getWorld().dropItemNaturally(loc.getBlock().getLocation(), paperToken);
			
			}
		}
	}, 1205660, 1220);
}







/* 357:    */ }


/* Location:           C:\Users\tony\Downloads\VirtualTokens(1).jar
 * Qualified Name:     org.netrocraft.ntokens.nTokens
 * JD-Core Version:    0.7.0.1
 */