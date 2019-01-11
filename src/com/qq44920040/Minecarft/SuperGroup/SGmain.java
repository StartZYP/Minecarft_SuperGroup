package com.qq44920040.Minecarft.SuperGroup;

import com.qq44920040.Minecarft.SuperGroup.Commands.SGCommand;
import com.qq44920040.Minecarft.SuperGroup.DUtil.DaoTool;
import com.qq44920040.Minecarft.SuperGroup.Listener.SuperGroupMainListener;
import com.qq44920040.Minecarft.SuperGroup.View.SuperGroupView;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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
                        //打开
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
                        int Groupid = DaoTool.GetPlayerHaveGroup(PlayerUUID);
                        DaoTool.AddGroupPlayer(Groupid,ADDPlayerUUId,4);
                        player.sendMessage("§e§l您同意玩家:"+Bukkit.getPlayer(ADDPlayerUUId).getDisplayName()+"加入公会.");
                    }else if (args[0].equalsIgnoreCase("reject")){
                        UUID PlayerUUID = player.getUniqueId();
                        UUID ADDPlayerUUId = UUID.fromString(args[1]);
                        int Groupid = DaoTool.GetPlayerHaveGroup(PlayerUUID);
                        DaoTool.GroupLogRemovePlayer(Groupid,ADDPlayerUUId);
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
            GroupConfig.GroupLevel.put(i,new String[]{Tempcontribution,TempDay,Number});
        }
        GroupConfig.Vice_President = getConfig().getInt("SuperGroup.Vice_President");
        GroupConfig.Elite = getConfig().getInt("SuperGroup.Elite");
        GroupConfig.EnterGroupTimeSetPosition = getConfig().getInt("SuperGroup.EnterGroupTimeSetPosition");
        GroupConfig.GroupCardLore = getConfig().getString("SuperGroup.GroupCardLore");
        GroupConfig.Donation =getConfig().getString("SuperGroup.Donation").split("-");
        if (GroupConfig.GroupLevel.size()>=1&&GroupConfig.Vice_President!=0&&GroupConfig.Elite!=0&&GroupConfig.EnterGroupTimeSetPosition!=0&&!GroupConfig.GroupCardLore.equalsIgnoreCase("")&&GroupConfig.Donation.length==3){
            return true;
        }else{
            return false;
        }
    }
}
