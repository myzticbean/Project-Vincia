package io.myztic.playerprofiles.handlers.guihandlers;

import io.myztic.core.bukkit.ChatUtil;
import io.myztic.core.logging.LogUtil;
import io.myztic.playerprofiles.MyzticPlayerProfiles;
import io.myztic.playerprofiles.models.PlayerProfileModel;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    protected PlayerMenuUtility playerMenuUtility;
    protected ItemStack GUI_FILLER_ITEM;
    protected ItemStack closeInvButton;

    protected Menu(PlayerMenuUtility playerMenuUtility, PlayerProfileModel playerProfile) {
        createGUICloseInvButton();
        this.playerMenuUtility = playerMenuUtility;
        Material fillerMaterial = Material.getMaterial("GRAY_STAINED_GLASS_PANE");
        if(fillerMaterial == null) {
            fillerMaterial = Material.GRAY_STAINED_GLASS_PANE;
        }
        GUI_FILLER_ITEM = new ItemStack(fillerMaterial);
        ItemMeta FILLER_GLASS_meta = this.GUI_FILLER_ITEM.getItemMeta();
        assert FILLER_GLASS_meta != null;
        FILLER_GLASS_meta.setDisplayName(" ");
        this.GUI_FILLER_ITEM.setItemMeta(FILLER_GLASS_meta);
    }

//    public abstract String getMenuName();

    public abstract String getMenuName(String playerName);

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

//    public abstract void setMenuItems();

    public abstract void setMenuItems(PlayerProfileModel playerProfile);

//    public void open() {
//        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
//        this.setMenuItems();
//        playerMenuUtility.getOwner().openInventory(inventory);
//    }

    public void open(PlayerProfileModel playerProfile) {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName(playerProfile.getPlayer().getName()));
        this.setMenuItems(playerProfile);
        playerMenuUtility.getOwner().openInventory(inventory);
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void addMenuBottomBar() {
//        inventory.setItem(45, backButton);
//        inventory.setItem(53, nextButton);
        inventory.setItem(49, closeInvButton);

        inventory.setItem(0, GUI_FILLER_ITEM);
        inventory.setItem(1, GUI_FILLER_ITEM);
        inventory.setItem(2, GUI_FILLER_ITEM);
        inventory.setItem(3, GUI_FILLER_ITEM);
        inventory.setItem(4, GUI_FILLER_ITEM);
        inventory.setItem(5, GUI_FILLER_ITEM);
        inventory.setItem(6, GUI_FILLER_ITEM);
        inventory.setItem(7, GUI_FILLER_ITEM);
        inventory.setItem(8, GUI_FILLER_ITEM);
        inventory.setItem(9, GUI_FILLER_ITEM);
        inventory.setItem(17, GUI_FILLER_ITEM);
        inventory.setItem(18, GUI_FILLER_ITEM);
        inventory.setItem(26, GUI_FILLER_ITEM);
        inventory.setItem(27, GUI_FILLER_ITEM);
        inventory.setItem(35, GUI_FILLER_ITEM);
        inventory.setItem(36, GUI_FILLER_ITEM);
        inventory.setItem(44, GUI_FILLER_ITEM);
        inventory.setItem(45, GUI_FILLER_ITEM);
        inventory.setItem(46, GUI_FILLER_ITEM);
        inventory.setItem(47, GUI_FILLER_ITEM);
        inventory.setItem(48, GUI_FILLER_ITEM);
        inventory.setItem(50, GUI_FILLER_ITEM);
        inventory.setItem(51, GUI_FILLER_ITEM);
        inventory.setItem(52, GUI_FILLER_ITEM);
        inventory.setItem(53, GUI_FILLER_ITEM);
    }

    private void createGUICloseInvButton() {
        Material closeInvButtonMaterial = Material.getMaterial("");
        if(closeInvButtonMaterial == null) {
            closeInvButtonMaterial = Material.BARRIER;
        }
        closeInvButton = new ItemStack(closeInvButtonMaterial);
        ItemMeta closeInvMeta = closeInvButton.getItemMeta();
        assert closeInvMeta != null;
        if(!StringUtils.isEmpty("")) {
            closeInvMeta.setDisplayName(ChatUtil.parseColors("", false));
        }
        else {
            closeInvMeta.setDisplayName(ChatUtil.parseColors("&cClose", false));
        }
        int closeInvButtonCMD;
        try {
            if(!StringUtils.isEmpty("")) {
                closeInvButtonCMD = Integer.parseInt("123");
                closeInvMeta.setCustomModelData(closeInvButtonCMD);
            }
        } catch (NumberFormatException e) {
            LogUtil.logDebugInfo(MyzticPlayerProfiles.getPrefix(), "Invalid Custom Model Data for Close Button in config.yml", true);
        }
        closeInvButton.setItemMeta(closeInvMeta);
    }
}
