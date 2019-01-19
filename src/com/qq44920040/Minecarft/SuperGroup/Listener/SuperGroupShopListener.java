package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class SuperGroupShopListener {
    static void PlayerShopListener(InventoryClickEvent event){
        Inventory inv =event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.ShopTitle)){
            int Slot= event.getSlot();
            if (GroupConfig.ShopItem.containsKey(Slot)){
                String[] temp = GroupConfig.ShopItem.get(Slot);
                int NeedContribution = Integer.parseInt(temp[3]);
                Player player = (Player)event.getWhoClicked();
                int HaveContribution = DaoTool.GetPlayerEntity(player.getUniqueId()).getHaveContributionPoint();
                if ((HaveContribution-NeedContribution)>=0){
                    DaoTool.UpdatePlayerContributionPoint(player.getUniqueId(),NeedContribution,true);
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),temp[4].replace("[Player]",player.getName()));
                    player.sendMessage("购买成功了");
                }else {
                    player.sendMessage("您没有足够的前");
                }
            }
            event.getWhoClicked().closeInventory();
        }
    }
}
