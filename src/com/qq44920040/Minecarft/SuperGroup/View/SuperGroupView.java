package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.GroupEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;


public class SuperGroupView {
    public static void OpenInventorySGList(Player player, int Pagenum){
        Inventory inventorySGList = Bukkit.createInventory(null,9*6,GroupConfig.SuperGroupListTitle);
        ItemStack White_Glass = new ItemStack(Material.STAINED_GLASS_PANE,1);
        ItemStack Button_Glass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)11);
        ItemStack Create_SG_Button = new ItemStack(Material.WATCH,1);
        ItemStack CloseInvButton = new ItemStack(Material.STAINED_GLASS_PANE,1,(short) 8);
        for (int i =36;i<=44;i++){
            inventorySGList.setItem(i,White_Glass);
        }
        for (int i =0;i<=8;i++){
            inventorySGList.setItem(i,White_Glass);
        }
        inventorySGList.setItem(49,CloseInvButton);
        inventorySGList.setItem(4,Create_SG_Button);
        ArrayList<GroupEntity> GroupEntityList = DaoTool.GetPageSuperGroup(Pagenum);
        int GroupListNum = GroupEntityList.size();

        if (GroupListNum!=0){
            if (GroupListNum==27){
                int temp = Pagenum+1;
                ItemMeta itemMeta =Button_Glass.getItemMeta();
                itemMeta.setDisplayName(temp+GroupConfig.PageDownButtonDisPlay);
                Button_Glass.setItemMeta(itemMeta);
                inventorySGList.setItem(53,Button_Glass);
            }
            int i =9;
            for (GroupEntity Group:GroupEntityList){
                ItemStack SuperGroupItem = new ItemStack(Material.SIGN,1);
                ItemMeta itemMeta = SuperGroupItem.getItemMeta();
                itemMeta.setDisplayName(GroupConfig.SGlistItem[0].replace("[SuperGroupName]",Group.getGroupName()));
                itemMeta.setLore(Arrays.asList(GroupConfig.SGlistItem[1].replace("[SuperGroupLevel]",String.valueOf(Group.GetGroupLevel())),GroupConfig.SGlistItem[2].replace("[SuperGroupHumanNum]",DaoTool.GetHumanNum(Group.getGroupID())+"/"+Group.getMaxHuManNumber()),GroupConfig.SGlistItem[3].replace("[oderName]",Bukkit.getOfflinePlayer(DaoTool.GetGroupOder(Group.getGroupID())).getName()),GroupConfig.SGlistItem[4].replace("[GroupKeyId]",String.valueOf(Group.getGroupID()))));
                SuperGroupItem.setItemMeta(itemMeta);
                inventorySGList.setItem(i,SuperGroupItem);
                i++;
            }
        }
        player.openInventory(inventorySGList);
    }

public static void UpdateInventorySGList(Player player, int Pagenum){
        InventoryView inventorySGList = player.getOpenInventory();
        ItemStack Button_Glass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)11);
        ArrayList<GroupEntity> GroupEntityList = DaoTool.GetPageSuperGroup(Pagenum);
        int GroupListNum = GroupEntityList.size();
        for (int i =9;i<=35;i++){
            inventorySGList.setItem(i,null);
        }
        if (GroupListNum!=0){
            inventorySGList.setItem(45,null);
            inventorySGList.setItem(53,null);
            if (GroupListNum==27){
                int temp = Pagenum+1;
                ItemMeta itemMeta =Button_Glass.getItemMeta();
                itemMeta.setDisplayName(temp+GroupConfig.PageDownButtonDisPlay);
                Button_Glass.setItemMeta(itemMeta);
                inventorySGList.setItem(53,Button_Glass);
            }
            if (Pagenum>=1){
                int temp = Pagenum-1;
                ItemMeta itemMeta =Button_Glass.getItemMeta();
                itemMeta.setDisplayName(temp+GroupConfig.PageUpButtonDisPlay);
                Button_Glass.setItemMeta(itemMeta);
                inventorySGList.setItem(45,Button_Glass);
            }
            int i =9;
            for (GroupEntity Group:GroupEntityList){
                ItemStack SuperGroupItem = new ItemStack(Material.SIGN,1);
                ItemMeta itemMeta = SuperGroupItem.getItemMeta();
                itemMeta.setDisplayName(GroupConfig.SGlistItem[0].replace("[SuperGroupName]",Group.getGroupName()));
                itemMeta.setLore(Arrays.asList(GroupConfig.SGlistItem[1].replace("[SuperGroupLevel]",String.valueOf(Group.GetGroupLevel())),GroupConfig.SGlistItem[2].replace("[SuperGroupHumanNum]",DaoTool.GetHumanNum(Group.getGroupID())+"/"+Group.getMaxHuManNumber()),GroupConfig.SGlistItem[3].replace("[oderName]",Bukkit.getOfflinePlayer(DaoTool.GetGroupOder(Group.getGroupID())).getName()),GroupConfig.SGlistItem[4].replace("[GroupKeyId]",String.valueOf(Group.getGroupID()))));
                SuperGroupItem.setItemMeta(itemMeta);
                inventorySGList.setItem(i,SuperGroupItem);
                i++;
            }
        }
        player.updateInventory();
    }
}
