package io.myztic.playerprofiles.handlers.guihandlers.menus;

import io.myztic.playerprofiles.handlers.guihandlers.Menu;
import io.myztic.playerprofiles.handlers.guihandlers.PlayerMenuUtility;
import io.myztic.playerprofiles.models.PlayerProfileModel;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProfileViewerMenu extends Menu {
    public ProfileViewerMenu(PlayerMenuUtility playerMenuUtility, PlayerProfileModel playerProfile) {
        super(playerMenuUtility, playerProfile);
    }

//    @Override
//    public String getMenuName() {
//        return null;
//    }

    @Override
    public String getMenuName(String playerName) {
        return ColorTranslator.translateColorCodes("&l» &r" + playerName + "'s Profile");
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if(event.getCurrentItem().getType().equals(Material.BARRIER) && event.getSlot() == 49) {
            event.getWhoClicked().closeInventory();
        }
        else if(event.getCurrentItem().getType().equals(Material.AIR)) {
            // do nothing
        }
    }

//    @Override
//    public void setMenuItems() {
//
//    }

    @Override
    public void setMenuItems(PlayerProfileModel playerProfile) {
        addMenuBottomBar();
        inventory.setItem(11, playerProfile.getPlayer().getEquipment().getHelmet());
        inventory.setItem(20, playerProfile.getPlayer().getEquipment().getChestplate());
        inventory.setItem(29, playerProfile.getPlayer().getEquipment().getLeggings());
        inventory.setItem(38, playerProfile.getPlayer().getEquipment().getBoots());
        inventory.setItem(19, playerProfile.getPlayer().getEquipment().getItemInMainHand());
        inventory.setItem(21, playerProfile.getPlayer().getEquipment().getItemInOffHand());
    }
}
