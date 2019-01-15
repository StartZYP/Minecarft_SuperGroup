package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Date;

class SuperGroupContributionListener {
    static void PlayerColickContributionView(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.ContributionTitle)){
            Player player = (Player) event.getWhoClicked();
            event.setCancelled(true);
            int Slot = event.getSlot();
            PlayerEntity playerEntity = DaoTool.GetPlayerEntity(player.getUniqueId());
            long PlayerTime = playerEntity.getEnterTime().getTime()+1440000;
            if (Slot==20){
                if ((PlayerTime)>new Date().getTime()){
                    player.sendMessage("§c§l您24小时内已经捐钱了");
                }else {
                    int takeContribution = Integer.parseInt(GroupConfig.Donation[0]);
                }
            }else if (Slot==22){
                if ((PlayerTime)>new Date().getTime()){
                    player.sendMessage("§c§l您24小时内已经捐钱了");
                }else {
                    int takeContribution = Integer.parseInt(GroupConfig.Donation[1]);
                }
            }else if (Slot==24){
                if ((PlayerTime)>new Date().getTime()){
                    player.sendMessage("§c§l您24小时内已经捐钱了");
                }else {
                    int takeContribution = Integer.parseInt(GroupConfig.Donation[2]);
                }
            }
            player.closeInventory();
        }
    }

}
