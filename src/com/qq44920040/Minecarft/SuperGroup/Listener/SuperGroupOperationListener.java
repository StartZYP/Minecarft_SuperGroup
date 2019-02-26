package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

 class SuperGroupOperationListener {
     static void PlayerOperationEvent(InventoryClickEvent event){
        Inventory inv = event.getInventory();
        if (inv.getTitle().equalsIgnoreCase(GroupConfig.PlayerOperationTitle)){
            event.setCancelled(true);
            int Slot = event.getSlot();
            ItemStack playeritem =inv.getItem(13);
            List<String> lore = playeritem.getItemMeta().getLore();
            String PlayerItemUUid = lore.get(2).replace(GroupConfig.PlayerListItem[3].replace("[UUID]",""),"");
            int PlayerItemPostion = Integer.parseInt(lore.get(1).replace(GroupConfig.PlayerListItem[2].replace("[Postion]",""),""));
            PlayerEntity operationplayer = DaoTool.GetPlayerEntity(event.getWhoClicked().getUniqueId());
            Player p = (Player)event.getWhoClicked();
            if (operationplayer.getPostionType()>=PlayerItemPostion){
                p.sendMessage("你的级别不够操作他吧");
                p.closeInventory();
            }
            int GroupId = DaoTool.GetPlayerHaveGroup(p.getUniqueId());
            if (Slot==20){
                if ((operationplayer.getPostionType()-1)==PlayerItemPostion){
                    p.sendMessage("您职位再升级则会相等");
                    p.closeInventory();
                }else if (PlayerItemPostion==3&&DaoTool.GetHumanNum(GroupId,PlayerItemPostion-1)<=GroupConfig.Vice_President){
                    DaoTool.UpdatePlayerPostion(UUID.fromString(PlayerItemUUid),true);
                    p.sendMessage("您成功将他升职为副会长列了");
                }else if (PlayerItemPostion==4&&DaoTool.GetHumanNum(GroupId,PlayerItemPostion-1)<=GroupConfig.Elite){
                    DaoTool.UpdatePlayerPostion(UUID.fromString(PlayerItemUUid),true);
                    p.sendMessage("您成功将他升职为精英列了");
                }else {
                    p.sendMessage("职位已经满了");
                }
            }else if (Slot==24){
                if ((PlayerItemPostion+1)>4){
                    p.sendMessage("您再给他降级就没有职位了");
                    p.closeInventory();
                }else if (PlayerItemPostion==2&&DaoTool.GetHumanNum(GroupId,PlayerItemPostion+1)<=GroupConfig.Elite){
                    DaoTool.UpdatePlayerPostion(UUID.fromString(PlayerItemUUid),false);
                    p.sendMessage("降级为精英成功");
                }else if (PlayerItemPostion==3){
                    DaoTool.UpdatePlayerPostion(UUID.fromString(PlayerItemUUid),false);
                    p.sendMessage("降级为成员成功");
                }else {
                    p.sendMessage("职位已经满了，请先降级一个精英");
                }
            }else if (Slot==38){
                DaoTool.GroupRemovePlayer(-1,UUID.fromString(PlayerItemUUid),false);
                p.sendMessage("成功将他踢出工会");
            }else if (Slot==42){
                if (PlayerItemPostion==2){
                    if (DaoTool.GetGroupOder(GroupId).toString().equalsIgnoreCase(operationplayer.getPlayerUUid())){
                        DaoTool.UpdatePlayerPostion(UUID.fromString(PlayerItemUUid),true);
                        DaoTool.UpdatePlayerPostion(UUID.fromString(operationplayer.getPlayerUUid()),false);
                    }else {
                        p.sendMessage("您不是会长");
                    }
                }else {
                    p.sendMessage("这货必须先为副会长");
                }
            }
            p.closeInventory();
        }
    }
}
