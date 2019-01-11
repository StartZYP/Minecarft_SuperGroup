package com.qq44920040.Minecarft.SuperGroup.Listener;

import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Entity.PlayerEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.UUID;

class SuperGroupPlayerJoinListener {
    static void PlayerJoinGame(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID playeruuid = player.getUniqueId();
        int GroupId = DaoTool.GetPlayerHaveGroup(playeruuid);
        if (GroupId!=-1){
            if (PlayerHavePostion(playeruuid,GroupId)){
                ArrayList<String> PlayerlogList = DaoTool.GetGroupJoinLogMsg(GroupId);
                PlayerlogList.forEach((String uuid)->{
                    String playerName = Bukkit.getPlayer(UUID.fromString(uuid)).getDisplayName();
                    Bukkit.getServer().getConsoleSender().sendMessage("/tellraw "+playerName+" [\"\",{\"text\":\"申请玩家名:\",\"color\":\"green\",\"bold\":true},{\"text\":\" ["+playerName+"]\",\"color\":\"gold\",\"bold\":true,\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"玩家UUID:"+uuid+"\",\"color\":\"red\"}]}}},{\"text\":\"    [同意]\",\"color\":\"yellow\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sg accept "+uuid+"\"}},{\"text\":\"     [拒绝]\",\"color\":\"gray\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/sg reject "+uuid+"\"}}]");
                });
            }
        }
    }

    private static boolean PlayerHavePostion(UUID playeruuid,int GroupId){
        ArrayList<PlayerEntity> PlayerList = DaoTool.GetPlayerEntity(GroupId);
            for (PlayerEntity playertemp:PlayerList){
                if (playertemp.getPlayerUUid().equalsIgnoreCase(playeruuid.toString())){
                    if (playertemp.getPostionType()<=3){
                        return true;
                    }
                    return false;
                }
            }
        return false;
    }
}
