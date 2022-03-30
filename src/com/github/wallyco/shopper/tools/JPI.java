package com.github.wallyco.shopper.tools;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.methods.IBankAPI;
import com.epicbot.api.shared.model.ge.GrandExchangeSlot;
import com.epicbot.api.shared.util.time.Time;
import com.github.wallyco.shopper.tools.grandexchange.GrandExchange;
import com.github.wallyco.shopper.tools.item.ShopItem;


import java.util.List;

import static com.github.wallyco.shopper.tools.grandexchange.GrandExchange.slots;

public class JPI {
    public static boolean interact(NPC npc, String action, APIContext cc) {
        try {
            if (!npc.interact(action)) {
                if (!cc.camera().getDirection().equals(cc.camera().getDirectionTo(npc))) {
                    cc.camera().turnTo(npc);
                }
            }else{
                Time.sleep(5000, () -> cc.store().isOpen());
                return true;
            }
        } catch (NullPointerException e) {}
        return false;
    }

    public static boolean shop(ShopItem shopItem, APIContext cc) {
        int difference = cc.store().getCount(shopItem.getName()) - shopItem.getMin();
        if(difference >= 50){
            cc.store().buyFifty(shopItem.getName());
        }else
            if(difference >= 10){
                cc.store().buyTen(shopItem.getName());
            }
            else
                if(difference >= 5) {
                    cc.store().buyFive(shopItem.getName());
                }
                else
                    if(difference > 0){
                        cc.store().buyOne(shopItem.getName());
                    }else{
                        return false;
                    }
        difference = cc.store().getCount(shopItem.getName()) - shopItem.getMin();
                    if(difference <= 0){
                        return false;
                    }

        return true;
    }

    public static boolean placeSellOffer(String name, APIContext cc){
        Time.sleep(5000, () -> GrandExchange.getItemToSell(name).interact("Offer"));
        Time.sleep(5000, () -> cc.grandExchange().decreasePriceBy5Percent(3));
        Time.sleep(5000, () -> cc.grandExchange().confirmOffer());
        for(GrandExchangeSlot lot : slots()){
            if(lot.getOffer().getItemName().equals(name))
                return true;
        }
        return false;
    }

    public static void withdrawItems(List<ShopItem> items, APIContext cc){
        if(!cc.bank().isOpen()){
            Time.sleep(5000, () -> cc.bank().open());
        }else if(cc.bank().isOpen()){
            for(ShopItem item : items){
                if(item.isNotable()){
                    Time.sleep(5000, () -> cc.bank().selectWithdrawMode(IBankAPI.WithdrawMode.NOTE));
                }else{
                    Time.sleep(5000, () -> cc.bank().selectWithdrawMode(IBankAPI.WithdrawMode.ITEM));
                }
                if(cc.bank().contains(item.getName()))
                    Time.sleep(5000, () -> cc.bank().withdrawAll(item.getName()));
            }
        }
    }

    public static int generateRandomInputInt(int offset) {
        return (int) ((Math.random() * offset) + 10);
    }

}
