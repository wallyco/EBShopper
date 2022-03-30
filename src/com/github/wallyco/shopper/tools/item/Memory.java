package com.github.wallyco.shopper.tools.item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Memory extends HashSet<ShopItem> {
    private static Memory mem;
    private Memory() {
    }

    public static Memory getInstance(){
        if(mem == null){
            mem = new Memory();
        }
        return mem;
    }
}
