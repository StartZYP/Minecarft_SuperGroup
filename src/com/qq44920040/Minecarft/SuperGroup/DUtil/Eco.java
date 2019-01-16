package com.qq44920040.Minecarft.SuperGroup.DUtil;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class Eco {
    public static Economy economy = null;

    public Eco() {
    }

    public static boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }

    public static boolean pay(UUID PlayerUUid, double price) {
        OfflinePlayer offplayer = Bukkit.getOfflinePlayer(PlayerUUid);
        return economy.has(offplayer,price)&&economy.withdrawPlayer(offplayer,price).transactionSuccess();
    }
}
