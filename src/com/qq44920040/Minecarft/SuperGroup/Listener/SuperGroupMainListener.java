package com.qq44920040.Minecarft.SuperGroup.Listener;



import com.qq44920040.Minecarft.SuperGroup.GroupConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

public class SuperGroupMainListener implements Listener {
    @EventHandler
    public void PlayerClickInvtoryEvent(InventoryClickEvent event){
        SuperGroupListListener.OpenSuperGroupListListener(event);
        SuperGroupMenuListener.PlayerOpenMenuListener(event);
        SuperGroupContributionListener.PlayerColickContributionView(event);
        SuperGroupPlayerListListener.PlayerColickListEvent(event);
        SuperGroupOperationListener.PlayerOperationEvent(event);
        SuperGroupShopListener.PlayerShopListener(event);
    }

    @EventHandler
    public void PlayerDragItem(InventoryDragEvent event){
        Inventory inv = event.getInventory();
        String Title = inv.getTitle();
        if (Title.equalsIgnoreCase(GroupConfig.PlayerOperationTitle)&&Title.equalsIgnoreCase(GroupConfig.PlayerListViewTitle)&&Title.equalsIgnoreCase(GroupConfig.ContributionTitle)&&Title.equalsIgnoreCase(GroupConfig.GroupMenuTitle)&&Title.equalsIgnoreCase(GroupConfig.SuperGroupListTitle)&&Title.equalsIgnoreCase(GroupConfig.ShopTitle)){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        SuperGroupPlayerJoinListener.PlayerJoinGame(event);
    }
}
