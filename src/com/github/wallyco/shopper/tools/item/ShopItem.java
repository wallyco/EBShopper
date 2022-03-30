package com.github.wallyco.shopper.tools.item;

import com.github.wallyco.shopper.tools.tasking.ScriptHandler;

public class ShopItem {
    private String name;
    private boolean condition;
    private boolean noteable;
    private int min;

    public ShopItem(String name, int min){
        this.name = name;
        this.min = min;
    }

    public boolean isNotable(){
        return !ScriptHandler.cc.bank().getItem(name).isStackable();
    }

    public String getName() {
        return name;
    }

    public boolean condition() {
        return condition;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", min=" + min +
                "}\n";
    }


}
