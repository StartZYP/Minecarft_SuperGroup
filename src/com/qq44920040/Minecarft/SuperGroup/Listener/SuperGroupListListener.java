package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;


class SuperGroupListListener{


    static void OpenSuperGroupListListener(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.SuperGroupListTitle)){
            event.setCancelled(true);
            Player p = (Player) event.getWhoClicked();
            int ClickSlot = event.getRawSlot();
            if (ClickSlot>=9&&ClickSlot<=35){
                ItemStack Button = inv.getItem(ClickSlot);
                if (Button!=null){
                    List<String> itemlist = Button.getItemMeta().getLore();
                    String lore = itemlist.get(3);
                    String GroupKeyId = lore.replace(GroupConfig.SGlistItem[4].replace("[GroupKeyId]",""),"");
                    if (DaoTool.GetPlayerHaveGroup(p.getUniqueId())!=-1){
                        p.sendMessage("§c§l您已经有工会了.");
                    }else if (DaoTool.GetGroupJoinLogHas(GroupKeyId,p.getUniqueId())){
                        p.sendMessage("§c§l您已经申请过加入此公会了.");
                    }else{
                        lore = itemlist.get(0);
                        String GroupName = lore.replace(GroupConfig.SGlistItem[0].replace("[SuperGroupName]",""),"");
                        p.sendMessage("§e§l您已经成功申请加入公会,"+GroupName);
                        DaoTool.AddGroupJoinLog(Integer.parseInt(GroupKeyId),p.getUniqueId());
                    }
                }
            } else if (ClickSlot==53||ClickSlot==45){
                ItemStack Button = inv.getItem(ClickSlot);
                if (Button!=null){
                    int Page;
                    if (ClickSlot==53){
                        Page = Integer.parseInt(Button.getItemMeta().getDisplayName().replace(GroupConfig.PageDownButtonDisPlay,""));
                    }else {
                        Page = Integer.parseInt(Button.getItemMeta().getDisplayName().replace(GroupConfig.PageUpButtonDisPlay,""));
                    }
                    SuperGroupView.UpdateInventorySGList(p,Page);
                }
            }else {
                p.sendMessage(GroupConfig.SuperGroupInfo);
            }
            p.closeInventory();
        }
    }
}
