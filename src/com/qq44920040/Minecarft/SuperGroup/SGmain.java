package com.qq44920040.Minecarft.SuperGroup;

import com.qq44920040.Minecarft.SuperGroup.Commands.SGCommand;
import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.DUtil.Eco;
import com.qq44920040.Minecarft.SuperGroup.Listener.SuperGroupMainListener;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupMenu;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupView;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.UUID;


public class SGmain extends JavaPlugin {
    @Override
    public void onDisable() {

        super.onDisable();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("SG") || label.equalsIgnoreCase("SuperGroup")) {
            if (sender instanceof Player) {
                Player player = (Player)sender;
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("list")){
                        SuperGroupView.OpenInventorySGList(player,0);
                    }else if (args[0].equalsIgnoreCase("sg")){
                        int GroupId =DaoTool.GetPlayerHaveGroup(player.getUniqueId());
                        if (GroupId!=-1){
                            SuperGroupMenu.PlayerOpenGroupMenu(player,GroupId);
                        }else {
                            player.sendMessage("§c§l您还没有工会无法打开工会菜单");
                        }
                    }
                }else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("Create")){
                        if (DaoTool.GetPlayerHaveGroup(player.getUniqueId())!=-1){
                            sender.sendMessage("§4§l[SuperGroup]您已经有工会了,");
                            return false;
                        }else if (SGCommand.CreateSuperGroupRegx(args[1])){
                            if (SGCommand.GetPlyaerHeldCreateCrad(player)){
                                DaoTool.AddGroupPlayer(DaoTool.AddSuperGroup(args[1]),player.getUniqueId(),1);
                                sender.sendMessage("§e§l[SuperGroup]恭喜您成为了["+args[1]+"]工会会长");
                            }else {
                                sender.sendMessage("§4§l[SuperGroup]创建失败,请您手持工会创建卡,进行创建");
                            }
                        }else {
                            sender.sendMessage("§4§l[SuperGroup]创建失败,请控制再2-8个字符内,");
                        }
                    }else if (args[0].equalsIgnoreCase("accept")){
                        UUID PlayerUUID = player.getUniqueId();
                        UUID ADDPlayerUUId = UUID.fromString(args[1]);
                        int PlayerGroupid = DaoTool.GetPlayerHaveGroup(PlayerUUID);
                        if (DaoTool.GetPlayerHaveGroup(ADDPlayerUUId)!=-1){
                            player.sendMessage("§c§l此玩家已经有工会了");
                        }else if(DaoTool.GetHumanNum(PlayerGroupid,-1)>=DaoTool.GetSuperGroup(PlayerGroupid).getMaxHuManNumber()){
                            player.sendMessage("§c§l公会满人了");
                        }else {
                            int Groupid = DaoTool.GetPlayerHaveGroup(PlayerUUID);
                            DaoTool.AddGroupPlayer(Groupid,ADDPlayerUUId,4);
                            DaoTool.GroupLogRemovePlayer(-1,ADDPlayerUUId);
                            player.sendMessage("§e§l您同意玩家:"+Bukkit.getOfflinePlayer(ADDPlayerUUId).getName()+"加入公会.");
                        }
                    }else if (args[0].equalsIgnoreCase("reject")){
                        UUID PlayerUUID = player.getUniqueId();
                        UUID ADDPlayerUUId = UUID.fromString(args[1]);
                        if (DaoTool.GetPlayerHaveGroup(ADDPlayerUUId)!=-1){
                            player.sendMessage("§c§l此玩家已经有工会了");
                        }else {
                            int Groupid = DaoTool.GetPlayerHaveGroup(PlayerUUID);
                            DaoTool.GroupLogRemovePlayer(Groupid,ADDPlayerUUId);
                        }
                    }
                }else {
                    sender.sendMessage(GroupConfig.SuperGroupInfo);
                }
            }
        }
        return super.onCommand(sender, command, label, args);
    }


    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!(file.exists())){
            saveDefaultConfig();
        }
        if (GetConfigInfo()){
            System.out.println("======SuperGroupConfigIsOK======");
            System.out.println("======QQNumber:44920040======");
            System.out.println("======SuperGroupConfigIsOK======");
            new DaoTool(getDataFolder()+File.separator+"SuperGroup");
            Bukkit.getPluginManager().registerEvents(new SuperGroupMainListener(),this);
            if (Eco.setupEconomy()){
                System.out.println("[SuperGroup]Vault初始化成功");
            }else {
                System.out.println("[SuperGroup]Vault初始化失败");
            }
        }else {
            System.out.println("======SuperGroupConfigIsErro======");
            System.out.println("======QQNumber:44920040======");
            System.out.println("======SuperGroupConfigIsErro======");
        }
        super.onEnable();
    }

    private boolean GetConfigInfo(){
        int MaxLevel = getConfig().getConfigurationSection("SuperGroup.Level").getKeys(false).size();
        for (int i =1;i<=MaxLevel;i++){
            String Tempcontribution = getConfig().getString("SuperGroup.Level."+i+".contribution");
            String TempDay = getConfig().getString("SuperGroup.Level."+i+".Day");
            String Number = getConfig().getString("SuperGroup.Level."+i+".Number");
            String LevelUpcontribution = getConfig().getString("SuperGroup.Level."+i+".LevelUpcontribution");
            GroupConfig.GroupLevel.put(i,new String[]{Tempcontribution,TempDay,Number,LevelUpcontribution});
        }
        int ShopItemNum = getConfig().getConfigurationSection("SuperGroup.GroupShop").getKeys(false).size();
        for (int i =1;i<=ShopItemNum;i++){
            String TempID = getConfig().getString("SuperGroup.GroupShop."+i+".ID");
            String TempDisPlayName = getConfig().getString("SuperGroup.GroupShop."+i+".DisPlayName");
            String Lore = getConfig().getString("SuperGroup.GroupShop."+i+".Lore");
            String MustContribution = getConfig().getString("SuperGroup.GroupShop."+i+".MustContribution");
            String DoCmd = getConfig().getString("SuperGroup.GroupShop."+i+".DoCmd");
            GroupConfig.ShopItem.put(i-1,new String[]{TempID,TempDisPlayName,Lore,MustContribution,DoCmd});
        }
        GroupConfig.PlayerOperationTitle = getConfig().getString("SuperGroup.Lang.PlayerOperationTitle");
        GroupConfig.ShopTitle = getConfig().getString("SuperGroup.Lang.GroupShopTitle");
        GroupConfig.Vice_President = getConfig().getInt("SuperGroup.VicePresidentNum");
        GroupConfig.Elite = getConfig().getInt("SuperGroup.EliteNum");
        GroupConfig.GroupCardLore = getConfig().getString("SuperGroup.GroupCardLore");
        GroupConfig.Donation =getConfig().getString("SuperGroup.Donation").split("-");
        GroupConfig.SuperGroupInfo = getConfig().getStringList("SuperGroup.Lang.SuperGroupInfo").toArray(new String[0]);
        GroupConfig.SuperGroupListTitle = getConfig().getString("SuperGroup.Lang.SuperGroupListTitle");
        GroupConfig.SGlistItem = getConfig().getStringList("SuperGroup.Lang.SGlistItem").toArray(new String[0]);
        GroupConfig.PageUpButtonDisPlay = getConfig().getString("SuperGroup.Lang.PageUpButtonDisPlay");
        GroupConfig.PageDownButtonDisPlay = getConfig().getString("SuperGroup.Lang.PageDownButtonDisPlay");
        GroupConfig.PlayerListViewTitle = getConfig().getString("SuperGroup.Lang.PlayerListViewTitle");
        GroupConfig.GroupMenuTitle = getConfig().getString("SuperGroup.Lang.GroupMenuTitle");
        GroupConfig.ContributionTitle = getConfig().getString("SuperGroup.Lang.ContributionTitle");
        GroupConfig.PlayerListItem = getConfig().getStringList("SuperGroup.Lang.PlayerItem").toArray(new String[0]);
        if (GroupConfig.GroupLevel.size()>=1&&GroupConfig.Vice_President!=0&&GroupConfig.Elite!=0&&!GroupConfig.GroupCardLore.equalsIgnoreCase("")&&GroupConfig.Donation.length==3){
            return true;
        }else{
            return false;
        }
    }
}
