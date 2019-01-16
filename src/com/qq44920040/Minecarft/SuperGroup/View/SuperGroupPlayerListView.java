package com.qq44920040.Minecarft.SuperGroup.View;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class SuperGroupPlayerListView {
    public static void OpenPlayerListView(Player player){
        Inventory inv = Bukkit.createInventory(null,6*9, GroupConfig.PlayerListViewTitle);
        ItemStack Button_Glass = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)3);
        ItemMeta itemMeta =Button_Glass.getItemMeta();
        itemMeta.setDisplayName("§e§l工会成员列表");
        Button_Glass.setItemMeta(itemMeta);
        for (int i=36;i<=44;i++){
            inv.setItem(i,Button_Glass);
        }
        int GroupId = DaoTool.GetPlayerHaveGroup(player.getUniqueId());
        ArrayList<PlayerEntity> GroupPlayerList = DaoTool.GetPlayerEntity(GroupId);
        int i = 0;
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM,1,(short)3);
        itemMeta = itemStack.getItemMeta();
        for (PlayerEntity tempPlayerEntity: GroupPlayerList){
            String PlayerName = Bukkit.getOfflinePlayer(UUID.fromString(tempPlayerEntity.getPlayerUUid())).getName();
            itemMeta.setDisplayName("玩家名:"+PlayerName);
            int playerPostion = tempPlayerEntity.getPostionType();
            int ContributionPoint = tempPlayerEntity.getHaveContributionPoint();
            itemMeta.setLore(Arrays.asList("玩家贡献点:"+ContributionPoint,"玩家职位:"+playerPostion,"玩家UUid:"+tempPlayerEntity.getPlayerUUid()));
            itemStack.setItemMeta(itemMeta);
            inv.setItem(i,itemStack);
            i++;
        }
        player.openInventory(inv);
    }
}
