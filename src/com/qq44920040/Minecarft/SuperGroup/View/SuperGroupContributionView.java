package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class SuperGroupContributionView {
    public static void PlayerOpenContributionView(Player player){
        Inventory inv = Bukkit.createInventory(null,6*9, GroupConfig.ContributionTitle);
        ItemStack Glass_Block = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)4);
        ItemStack Button_Block = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)8);
        ItemMeta itemMeta =Glass_Block.getItemMeta();
        itemMeta.setDisplayName("§e§l捐助菜单");
        Glass_Block.setItemMeta(itemMeta);
        for (int i=0;i<=53;i++){
            if (i==20){
                itemMeta.setDisplayName("§e§l捐助1W");
                Button_Block.setItemMeta(itemMeta);
                inv.setItem(i,Button_Block);
            }else if (i==22){
                itemMeta.setDisplayName("§e§l捐助10W");
                Button_Block.setItemMeta(itemMeta);
                inv.setItem(i,Button_Block);
            }else if (i==24){
                itemMeta.setDisplayName("§e§l捐助100W");
                Button_Block.setItemMeta(itemMeta);
                inv.setItem(i,Button_Block);
            }else{
                inv.setItem(i,Glass_Block);
            }
        }
        player.openInventory(inv);
    }
}
