package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SuperGroupOperationPlayer {
    public static void OperationPlayer(Player player){
        Inventory inv = Bukkit.createInventory(null,6*9, GroupConfig.PlayerOperationTitle);
        ItemStack Button_Block = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)4);
        ItemMeta itemMeta =Button_Block.getItemMeta();
        itemMeta.setDisplayName("§e§l成员操作");
        ItemStack Tag_Button = new ItemStack(Material.SIGN,1);
        for (int i=0;i<=53;i++){
            if (i==20){
                itemMeta.setDisplayName("提升职位");
                Tag_Button.setItemMeta(itemMeta);
                inv.setItem(i,Tag_Button);
            }else if (i==24){
                itemMeta.setDisplayName("降低职位");
                Tag_Button.setItemMeta(itemMeta);
                inv.setItem(i,Tag_Button);
            }else if (i==38){
                itemMeta.setDisplayName("踢出工会");
                Tag_Button.setItemMeta(itemMeta);
                inv.setItem(i,Tag_Button);
            }else if (i==42){
                itemMeta.setDisplayName("转让会长");
                Tag_Button.setItemMeta(itemMeta);
                inv.setItem(i,Tag_Button);
            }else {
                inv.setItem(i,Button_Block);
            }
        }
    }
}
