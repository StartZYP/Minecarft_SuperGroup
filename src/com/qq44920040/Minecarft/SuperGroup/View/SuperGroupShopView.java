package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;

public class SuperGroupShopView {
    public static void PlayerOpenShopView(Player player){
        Inventory inv = Bukkit.createInventory(null,6*9,GroupConfig.ShopTitle);
        for(Map.Entry<Integer,String[]> TempItem: GroupConfig.ShopItem.entrySet()){
            ItemStack item;
            ItemMeta itemMeta;
            String[] TempVault = TempItem.getValue();
            if (TempVault[0].contains("-")){
                String[] Lore = TempVault[0].split("-");
                item = new ItemStack(Integer.parseInt(Lore[0]),1,(short)Integer.parseInt(Lore[1]));
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(TempVault[1]);
                itemMeta.setLore(Arrays.asList(TempVault[2].replace("[MustContribution]",TempVault[3]).split("-")));
                item.setItemMeta(itemMeta);
            }else {
                item = new ItemStack(Integer.parseInt(TempVault[0]),1);
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(TempVault[1]);
                itemMeta.setLore(Arrays.asList(TempVault[2].replace("[MustContribution]",TempVault[3]).split("-")));
                item.setItemMeta(itemMeta);
            }
            inv.setItem(TempItem.getKey(),item);
        }
        player.openInventory(inv);
    }
}
