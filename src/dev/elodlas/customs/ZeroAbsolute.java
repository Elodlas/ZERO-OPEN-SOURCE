package dev.elodlas.customs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import dev.elodlas.customs.potions.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.inventory.meta.PotionMeta;

import dev.elodlas.customs.potions.*;

public class ZeroAbsolute extends JavaPlugin implements Listener {

    private HashMap<String, PotionEffectType> potionNames;
    private static short potionDrinkable = 64;
    private static short potionThrowable = 16390;
    private static LanguageManager languageManager;

    PluginDescriptionFile pluginfile = getDescription();
    public String v = pluginfile.getVersion();

    public static Logger log;
    public static ZeroAbsolute instance;

    File configFile;
    FileConfiguration config;

    ZeroTwo cmd;

    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO - CUSTOM THINGS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "VERSION " + v);
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Apples: ON");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Pots: OFF");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Arrows: SOON");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ENABLED");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");

        instance = this;

        log = this.getLogger();

        configFile = new File(getDataFolder(), "gapple.yml");
        try {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }

        config = new YamlConfiguration();
        loadYamls();

        this.getServer().getPluginManager().registerEvents(this, this);

        cmd = new ZeroTwo(this);

        languageManager = new LanguageManager("EN_UK", new File(this.getDataFolder(), "lang"));
        potionNames = new HashMap<String, PotionEffectType>();
        for(PotionEffectType type : PotionEffectType.values()){
            potionNames.put(type.getName(), type);
        }

    }

    @EventHandler
    public void onMove (PlayerItemConsumeEvent e) {
        final Player player = e.getPlayer();
        ItemStack is = e.getItem();
        if (is.getType() == Material.GOLDEN_APPLE) {
            if (is.getData().getData() == 1) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    public void run() {
                        if (player.hasPotionEffect(PotionEffectType.ABSORPTION) && player.hasPotionEffect(PotionEffectType.REGENERATION) && player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE) && player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                            player.removePotionEffect(PotionEffectType.ABSORPTION);
                            player.removePotionEffect(PotionEffectType.REGENERATION);
                            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        }
                        Set<String> AllPotionEffects = getConfig().getConfigurationSection("").getKeys(false);
                        int power;
                        int time;
                        for (String s: AllPotionEffects) {
                            if (getConfig().getBoolean(s + ".enabled")) {
                                time = getConfig().getInt(s + ".time") * 20;
                                power = getConfig().getInt(s + ".power") - 1;
                                player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(s), time, power));
                            }
                        }
                    }
                }, 1L);
            }
        }
    }
    public void firstRun() throws Exception {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            copy(getResource("gapple.yml"), configFile);
            log.info("Config not found, Generating.");
        }
    }

    private void loadYamls() {
        try {
            config.load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf))>0) {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveYamls() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ZeroAbsolute getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,
                             String label, String[] args) {
        if(command.getName().equalsIgnoreCase("custompotions")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("help")){
                    sender.sendMessage("Custom Potions help");
                    sender.sendMessage("This will be changed in the release build");
                    sender.sendMessage("");
                    sender.sendMessage(label + " give name potion:strength:duration (-splash)");
                    return true;
                }
            }else if(args.length >= 3){
                if(args[0].equalsIgnoreCase("give")){
                    List<String> variables = new ArrayList<String>();
                    Player target = null;
                    for(Player player : Bukkit.getOnlinePlayers()){
                        if(player.getName().equalsIgnoreCase(args[1])){
                            target = player;
                        }
                    }
                    if(target == null){
                        sender.sendMessage("Player is not online");
                        return true;
                    }
                    ItemStack item = new ItemStack(Material.POTION, 1);
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    for(int i = 2; i < args.length; i++){
                        String arg = args[i];
                        if(arg.contains(":")){
                            String[] miniArgs = arg.split(":");
                            if(miniArgs.length == 3){
                                if(potionNames.containsKey(miniArgs[0])){
                                    PotionEffectType type = potionNames.get(miniArgs[0]);
                                    int duration = Integer.parseInt(miniArgs[1]) * 20;
                                    int amplifier = Integer.parseInt(miniArgs[2]);
                                    meta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
                                }else{
                                    sender.sendMessage("Unknown potion name: " + miniArgs[0]);
                                    sender.sendMessage("Ignoring");
                                }
                            }else{
                                sender.sendMessage("Malformed potion argument: " + arg);
                                sender.sendMessage("Skipping");
                            }
                        }else if(arg.startsWith("-")){
                            variables.add(arg);
                        }
                    }

                    short id = potionDrinkable;
                    if(variables.contains("-splash")){
                        id = potionThrowable;
                    }
                    item.setDurability(id);
                    item.setItemMeta(meta);
                    target.getInventory().addItem(item);
                    target.sendMessage("You have been given a Custom Potion :D");
                    return true;
                }
            }
        }
        return false;
    }

    public void onDisable(){
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO - CUSTOM THINGS");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "VERSION " + v);
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Apples: ON");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Pots: OFF");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "ZERO Arrows: SOON");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "DISABLED");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "---------------------------------");
    }
}

