package io.myztic.playerprofiles.handlers.guihandlers;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;

//    private List<FoundShopItemModel> playerShopSearchResult;

//    public List<FoundShopItemModel> getPlayerShopSearchResult() {
//        return playerShopSearchResult;
//    }

//    public void setPlayerShopSearchResult(List<FoundShopItemModel> playerShopSearchResult) {
//        this.playerShopSearchResult = playerShopSearchResult;
//    }

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
