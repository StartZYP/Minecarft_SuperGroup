package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupContributionView;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupPlayerListView;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
class SuperGroupMenuListener implements Listener {
    static void PlayerOpenMenuListener(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.GroupMenuTitle)){
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            int Slot = event.getSlot();
            if (Slot==12){
                SuperGroupPlayerListView.OpenPlayerListView(player);
                //打开玩家列表界面
            }else if (Slot==14){
                SuperGroupContributionView.PlayerOpenContributionView(player);
                //打开氪金
            }else if (Slot==16){
                //打开商店
            }else if (Slot==43){
                //离开工会
            }else {
                Player p = (Player) event.getWhoClicked();
                p.sendMessage(GroupConfig.SuperGroupInfo);
                event.setCancelled(true);
            }
        }
    }
}
