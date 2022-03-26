package com.github.wallyco.shopper.bot.location;

import com.epicbot.api.shared.model.Area;

public enum Shops {
    VARROCK_ARCHERY(new Area(3236,3432,3231,3428), new String[5], "Lowe","Trade",  "Varrock archery store");

    private Area area;
    private String[] buyItems;
    private String name;
    private String interact;
    private String description;

    Shops(Area area, String[] buyItems, String name,String interact, String description){
        this.area = area;
        this.buyItems = buyItems;
        this.name = name;
        this.interact = interact;
        this.description = description;
    }

    public String[] getBuyItems(){
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

}