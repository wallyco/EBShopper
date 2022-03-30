package com.github.wallyco.shopper.bot.tasks;

import com.epicbot.api.shared.entity.Item;
import com.epicbot.api.shared.methods.IBankAPI;
import com.epicbot.api.shared.methods.IClientAPI;
import com.epicbot.api.shared.model.ge.GrandExchangeItemDetail;
import com.epicbot.api.shared.model.ge.GrandExchangeSlot;
import com.epicbot.api.shared.util.time.Time;
import com.epicbot.api.shared.webwalking.model.RSBank;
import com.github.wallyco.shopper.tools.JPI;
import com.github.wallyco.shopper.tools.item.Memory;
import com.github.wallyco.shopper.tools.item.ShopItem;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import static com.github.wallyco.shopper.tools.grandexchange.GrandExchange.readyToCollect;
import static com.github.wallyco.shopper.tools.grandexchange.GrandExchange.slots;

public class GrandExchange extends ScriptHandler implements Task {
    private ArrayList<ShopItem> itemsToSell = new ArrayList<>();
    private ArrayList<ShopItem> itemsSold;

    public GrandExchange(){
        itemsToSell.addAll(memory);
    }

    public GrandExchange(ArrayList itemsToSell){
        this.itemsToSell = (ArrayList<ShopItem>) itemsToSell.clone();
    }

    @Override
    public boolean execute() {
        if(shouldExecute()){
            if(openGE()){
                sell();
            }
        }else{
            return false;
        }
        return true;
    }

    private boolean shouldExecute(){
        if(readyToCollect()){
            Time.sleep(5000, () -> cc.grandExchange().collectToInventory());
        }

        if(itemsSold != null && !isInUse())
            return false;

        return true;
    }

    private boolean openGE() {
        Time.sleep(600);
        if(cc.grandExchange().isOpen()){
            return true;
        }else if(RSBank.GRAND_EXCHANGE.getTile().getArea(10).contains(cc.localPlayer().get())){
            if(!cc.inventory().onlyContains(995)) {
                if (cc.grandExchange().open()) {
                    Time.sleep(5000, () -> cc.grandExchange().isOpen());
                    return true;
                }
            }else{
                JPI.withdrawItems(itemsToSell, cc);
                Time.sleep(5000, () -> cc.bank().close());
                return false;
            }
        }else{
            cc.webWalking().walkToBank(RSBank.GRAND_EXCHANGE);
        }
        return false;
    }

    private boolean sell(){
        if (itemsSold == null) {
            itemsSold = (ArrayList<ShopItem>) itemsToSell.clone();
            log("cloning");
        }
        if (!itemsSold.isEmpty() && openSlot() != null) {
            if (JPI.placeSellOffer(itemsSold.get(0).getName(), cc)) {
                itemsSold.remove(itemsSold.get(0));
                return true;
            }
        }
        return false;
    }

    private GrandExchangeSlot openSlot(){
        for(GrandExchangeSlot lot : slots()){
            if(!lot.inUse()){
                return lot;
            }
        }
        return null;
    }

    private boolean isInUse(){
        for(GrandExchangeSlot lot : slots()){
            if(lot.inUse()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "GrandExchange{" +
                "itemsSold=" + itemsSold +
                '}';
    }
}
