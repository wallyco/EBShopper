package com.github.wallyco.shopper.tools.grandexchange;

import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.model.ge.GrandExchangeSlot;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;

import java.util.List;

public class GrandExchange extends ScriptHandler {
    public static WidgetChild getItemToSell(String itemName) {
        return cc.widgets().query().group(467).itemName(itemName).results().first();
    }

    public static List<GrandExchangeSlot> slots(){
        if(!cc.localPlayer().hasMembership()){
            return cc.grandExchange().getSlots().subList(0,3);
        }
        return cc.grandExchange().getSlots();
    }

    public static boolean readyToCollect(){
        for(GrandExchangeSlot lot : slots()){
            if(lot.canCollect()){
                return true;
            }
        }
        return false;
    }
}
