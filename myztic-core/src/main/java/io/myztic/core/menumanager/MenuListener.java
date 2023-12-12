package io.myztic.core.menumanager;

import io.myztic.core.MyzticCore;
import io.myztic.core.bukkit.ChatUtil;
import io.myztic.core.config.coreconfig.ConfigProvider;
import io.myztic.core.exceptions.MenuManagerException;
import io.myztic.core.exceptions.MenuManagerNotSetupException;
import io.myztic.core.logging.LogUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * Based on kodysimpson's Menu implementation (github.com/Cortex-MC/SimpAPI)
 */
public class MenuListener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        InventoryHolder holder = e.getInventory().getHolder();
        // If the inventoryholder of the inventory clicked on
        // is an instance of Menu, then gg. The reason that
        // an InventoryHolder can be a Menu is because our Menu
        // class implements InventoryHolder!!
        if (holder instanceof Menu) {
            if (e.getCurrentItem() == null) { //deal with null exceptions
                return;
            }
            // Since we know our inventoryholder is a menu, get the Menu Object representing
            // the menu we clicked on
            Menu menu = (Menu) holder;

            if (menu.cancelAllClicks()) {
                e.setCancelled(true); //prevent them from fucking with the inventory
            }

            // Call the handleMenu object which takes the event and processes it
            try {
                menu.handleMenu(e);
            } catch (MenuManagerNotSetupException menuManagerNotSetupException) {
                LogUtil.logDebugWarn(
                        MyzticCore.getPrefix(),
                        ChatUtil.parseColors("&cTHE MENU MANAGER HAS NOT BEEN CONFIGURED. CALL MENUMANAGER.SETUP()", false),
                        ConfigProvider.getInst().DEBUG_MODE);
            } catch (MenuManagerException menuManagerException) {
                menuManagerException.printStackTrace();
            }
        }

    }
}
