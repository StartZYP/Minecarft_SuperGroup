package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.GroupEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SuperGroupMenu {
    public static void PlayerOpenGroupMenu(Player player,int haveGroupId){
        Inventory inv = Bukkit.createInventory(null,9*6, GroupConfig.GroupMenuTitle);
        ItemStack Button_Glass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)9);
        ItemMeta itemMeta =Button_Glass.getItemMeta();
        itemMeta.setDisplayName("§e§l工会菜单");
        Button_Glass.setItemMeta(itemMeta);
        ItemStack Button_Block = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)4);
        itemMeta =Button_Block.getItemMeta();
        itemMeta.setDisplayName("§e§l工会菜单");
        Button_Block.setItemMeta(itemMeta);
        for (int i=0;i<=53;i++){
            if (i<8||i>=45||i==9||i==18||i==27||i==36||i==17||i==26||i==35||i==44){
                inv.setItem(i,Button_Glass);
            }else{
                inv.setItem(i,Button_Block);
            }
        }
        GroupEntity Group = DaoTool.GetSuperGroup(haveGroupId);
        ItemStack SuperGroupItem = new ItemStack(Material.BEACON,1);
        itemMeta = SuperGroupItem.getItemMeta();
        itemMeta.setDisplayName(GroupConfig.SGlistItem[0].replace("[SuperGroupName]",Group.getGroupName()));
        itemMeta.setLore(Arrays.asList(GroupConfig.SGlistItem[1].replace("[SuperGroupLevel]",String.valueOf(Group.GetGroupLevel())),GroupConfig.SGlistItem[2].replace("[SuperGroupHumanNum]",DaoTool.GetHumanNum(Group.getGroupID(),-1)+"/"+Group.getMaxHuManNumber()),GroupConfig.SGlistItem[3].replace("[oderName]",Bukkit.getOfflinePlayer(DaoTool.GetGroupOder(Group.getGroupID())).getName()),GroupConfig.SGlistItem[4].replace("[GroupKeyId]",String.valueOf(Group.getGroupID()))));
        SuperGroupItem.setItemMeta(itemMeta);
        inv.setItem(10,SuperGroupItem);

        ItemStack HeadButton = new ItemStack(Material.SKULL_ITEM,1,(short)3);
        itemMeta =HeadButton .getItemMeta();
        itemMeta.setDisplayName("§e§l打开工会成员列表");
        HeadButton .setItemMeta(itemMeta);
        inv.setItem(12,HeadButton);

        ItemStack GoldButton = new ItemStack(Material.GOLD_NUGGET,1);
        itemMeta =GoldButton.getItemMeta();
        itemMeta.setDisplayName("§e§l捐钱页面");
        GoldButton .setItemMeta(itemMeta);
        inv.setItem(14,GoldButton);

        ItemStack GoldIngotButton = new ItemStack(Material.GOLD_INGOT,1);
        itemMeta =GoldIngotButton.getItemMeta();
        itemMeta.setDisplayName("§e§l捐献商城页面");
        GoldIngotButton .setItemMeta(itemMeta);
        inv.setItem(16,GoldIngotButton);

        ItemStack BooktButton = new ItemStack(Material.BOOK,1);
        itemMeta =BooktButton.getItemMeta();
        itemMeta.setDisplayName("§e§l命令全解");
        BooktButton .setItemMeta(itemMeta);
        inv.setItem(37,BooktButton);

        ItemStack UPLevelButton = new ItemStack(Material.FIREBALL,1);
        itemMeta =UPLevelButton.getItemMeta();
        itemMeta.setDisplayName("§e§l升级公会");
        itemMeta.setLore(Arrays.asList("工会现存额度:"+Group.getGroupContributionPoint(),"工会总额度:"+Group.getUsedGroupContributionPoist()));
        UPLevelButton .setItemMeta(itemMeta);
        inv.setItem(40,UPLevelButton);

        ItemStack LAVAButton = new ItemStack(Material.LAVA_BUCKET,1);
        itemMeta =LAVAButton.getItemMeta();
        itemMeta.setDisplayName("§e§l离开工会");
        LAVAButton .setItemMeta(itemMeta);
        inv.setItem(43,LAVAButton);
        player.openInventory(inv);
    }
}
