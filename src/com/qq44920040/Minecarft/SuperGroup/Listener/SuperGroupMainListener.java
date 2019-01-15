package com.qq44920040.Minecarft.SuperGroup.Listener;



import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SuperGroupMainListener implements Listener {
    @EventHandler
    public void PlayerClickInvtoryEvent(InventoryClickEvent event){
        SuperGroupListListener.OpenSuperGroupListListener(event);
        SuperGroupMenuListener.PlayerOpenMenuListener(event);
        SuperGroupContributionListener.PlayerColickContributionView(event);
    }
    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        SuperGroupPlayerJoinListener.PlayerJoinGame(event);
    }
}
