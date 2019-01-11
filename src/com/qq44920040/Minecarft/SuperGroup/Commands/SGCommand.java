package com.qq44920040.Minecarft.SuperGroup.Commands;

import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class SGCommand {
    public static boolean GetPlyaerHeldCreateCrad(Player player){
        ItemStack itemStack = player.getItemInHand();
        if (itemStack!=null&&itemStack.getType()!=Material.AIR){
            String tempname = itemStack.getItemMeta().getDisplayName();
            if (tempname!=null){
                return tempname.contains(GroupConfig.GroupCardLore);
            }
        }
        return false;
    }
    public static boolean CreateSuperGroupRegx(String Text){
        String pattern = "[\u4e00-\u9fa5\\w]{2,8}";
        return Pattern.matches(pattern,Text);
    }
}
