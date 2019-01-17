package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupOperationPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SuperGroupPlayerListListener {
    public static void PlayerColickListEvent(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.PlayerListViewTitle)){
            event.setCancelled(true);
            int Slot = event.getSlot();
            Player player = (Player) event.getWhoClicked();
            if (Slot>=0&&Slot<=35){
                ItemStack TempItem = inv.getItem(Slot);
                if (TempItem!=null){
                    PlayerEntity playerEntity = DaoTool.GetPlayerEntity(player.getUniqueId());
                    String ItemUUid = TempItem.getItemMeta().getLore().get(2).replace(GroupConfig.PlayerListItem[3].replace("[UUID]",""),"");
                    if (ItemUUid.equalsIgnoreCase(player.getUniqueId().toString())){
                        player.sendMessage("§c§l您不能操作自己");
                        player.closeInventory();
                    }else if (playerEntity.getPostionType()<=2){
                        SuperGroupOperationPlayer.OperationPlayer(player,TempItem);
                    }else {
                        player.sendMessage("§c§l您必须是副会长以上级别");
                        player.closeInventory();
                    }
                }else {
                    player.closeInventory();
                }
            }else {
                player.closeInventory();
            }
        }
    }

}
