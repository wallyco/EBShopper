package com.github.wallyco.shopper.bot.location;

import com.epicbot.api.shared.model.Area;
import com.github.wallyco.shopper.tools.item.ShopItem;

public enum Shops {
    VARROCK_ARCHERY(new Area(3232,3425,3231,3428), new ShopItem[]{new ShopItem("Steel arrow", 930)}, "Lowe","Trade",  "Varrock archery store"),
    VARROCK_GENERALSTORE(new Area(3217,3415,3212,3421), new ShopItem[]{new ShopItem("Hammer", 0), new ShopItem("Tinderbox", 0)}, "Shop keeper","Trade",  "Varrock general store"),
    FALADOR_GENERALSTORE(new Area(2956,3383,2959,3387), new ShopItem[]{new ShopItem("Hammer", 0), new ShopItem("Tinderbox", 0)}, "Shop keeper","Trade",  "Varrock general store"),
    KHARID_GEMSTORE(new Area(3284,3214,3288,3209), new ShopItem[]
            {new ShopItem("Uncut emerald", 0),
                    new ShopItem("Emerald", 0),
                    new ShopItem("Sapphire", 0),
                    new ShopItem("Uncut sapphire", 0),
                    new ShopItem("Uncut ruby", 0),
                    new ShopItem("Uncut diamond", 0),
                    new ShopItem("Ruby", 0),
                    new ShopItem("Diamond", 0)}
            , "Gem trader","Trade",  "Al Kharid gem store"),
    WILDY_SAMSHOP(new Area(3049, 3627,3047, 3631), new ShopItem[]{new ShopItem("Team-30 cape", 70)},"Sam", "Trade", "Wilderness team-cap shop (Sam)", 1550); //Team-10 cape, Team-30 cape, Team-20 cape

    private Area area;
    private ShopItem[] buyItems;
    private String name;
    private String interact;
    private String description;
    private int risk = 10000;

    Shops(Area area, ShopItem[] buyItems, String name, String interact, String description){
        this.area = area;
        this.buyItems = buyItems;
        this.name = name;
        this.interact = interact;
        this.description = description;
    }

    Shops(Area area, ShopItem[] buyItems, String name, String interact, String description, int risk) {
        this.area = area;
        this.buyItems = buyItems;
        this.name = name;
        this.interact = interact;
        this.description = description;
        this.risk = risk;
    }

        public ShopItem[] getBuyItems(){
        return buyItems;
    }

    public Area getArea() {
        return area;
    }

    public String getName() {
        return name;
    }

    public String getInteract(){
        return interact;
    }

    public String getDescription() {
        return description;
    }

    public int getRisk(){
        return risk;
    }

}