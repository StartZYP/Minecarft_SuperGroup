package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.GroupEntity;
import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupContributionView;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupPlayerListView;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupShopView;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

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
                SuperGroupShopView.PlayerOpenShopView(player);
                //打开商店
            }else if (Slot==40){
                UUID playerUUid = player.getUniqueId();
                int GroupId = DaoTool.GetPlayerHaveGroup(playerUUid);
                if (DaoTool.GetPlayerEntity(playerUUid).getPostionType()<=2){
                    GroupEntity TempGroup = DaoTool.GetSuperGroup(GroupId);
                    long CreateTime = new Date().getTime()-TempGroup.getCreateTime().getTime();
                    for(Map.Entry<Integer,String[]> TempLevel:GroupConfig.GroupLevel.entrySet()){
                        if ((TempGroup.GetGroupLevel()+1)==TempLevel.getKey()){
                            if (CreateTime>(Integer.parseInt(TempLevel.getValue()[1])*60*1000*24)){
                                int GroupContributionPoist = TempGroup.getGroupContributionPoint();
                                if (GroupContributionPoist>Integer.parseInt(TempLevel.getValue()[3])){
                                    DaoTool.UpdateUsedGroupContribution(GroupId,GroupContributionPoist,false);
                                    DaoTool.UpdateGroupContribution(GroupId,GroupContributionPoist,true);
                                    player.sendMessage("成功升级公会");
                                }else {
                                    player.sendMessage("§c§l工会现存贡献度不够.需要"+TempLevel.getValue()[3]);
                                }
                            }else {
                                player.sendMessage("§c§l您创建未满天数升级不了");
                            }
                            break;
                        }
                    }
                }else {
                    player.sendMessage("§c你不是精英以上的工会成员无权限");
                }
            } else if (Slot==43){
                //离开工会
                UUID PlayerUUid = player.getUniqueId();
                int GroupId = DaoTool.GetPlayerHaveGroup(PlayerUUid);
                if (DaoTool.GetGroupOder(GroupId).equals(PlayerUUid)){
                    DaoTool.GroupRemovePlayer(GroupId,PlayerUUid,true);
                    player.sendMessage("您是工会会长成功解散了工会");
                }else {
                    DaoTool.GroupRemovePlayer(GroupId,PlayerUUid,false);
                    player.sendMessage("成功离开了工会");
                }
            }else {
                Player p = (Player) event.getWhoClicked();
                p.sendMessage(GroupConfig.SuperGroupInfo);
                player.closeInventory();
            }
        }
    }
}
