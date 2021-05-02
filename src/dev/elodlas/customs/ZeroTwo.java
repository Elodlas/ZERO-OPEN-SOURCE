package dev.elodlas.customs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;

public class ZeroTwo {

    ZeroAbsolute plugin;

    public ZeroTwo(ZeroAbsolute instance) {
        this.plugin = instance;
        init();
    }

    private void init() {
        PluginCommand customapples = plugin.getCommand("customapples");
        CommandExecutor exe;

        exe = new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
                if (args.length > 0) {
                    s.sendMessage("§6Proper Usage: §3/customapples");
                    return true;
                }
                if (!s.hasPermission("zeroapples.reload")) {
                    s.sendMessage("§cYou don't have permission to do that.");
                    return true;
                }
                plugin.reloadConfig();
                s.sendMessage("§7[§4Zero Apples§7] §aConfig Reloaded.");
                return true;
            }
        }; customapples.setExecutor(exe);
    }

}