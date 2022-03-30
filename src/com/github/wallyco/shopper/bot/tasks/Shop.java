package com.github.wallyco.shopper.bot.tasks;

import com.epicbot.api.os.model.game.GameState;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.util.time.Time;
import com.github.wallyco.shopper.bot.location.Shops;
import com.github.wallyco.shopper.tools.item.ShopItem;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.Task;
import com.github.wallyco.shopper.tools.JPI;
import com.github.wallyco.shopper.tools.tasking.TaskManager;

import java.util.ArrayList;

public class Shop extends ScriptHandler implements Task{
    private NPC npc;
    private String name;
    private String interact;
    private Area botlocation;

    private ArrayList<ShopItem> buyItems = new ArrayList<>();
    private ArrayList<ShopItem> boughtItems = new ArrayList<>();
    private int risk = 0;

    public Shop(Shops info) {
        name = info.getName();
        interact = info.getInteract();
        botlocation = info.getArea();
        for(ShopItem item : info.getBuyItems()){
            buyItems.add(item);
        }
        if(info.getRisk() > 0){
            risk = info.getRisk();
        }

        memory.addAll(buyItems);

    }

    @Override
    public boolean execute() {
        if(shouldExecute()){
            if(openShop()){
                buyItems();
            }
        }
        return true;
    }
    private boolean shouldExecute(){
        Time.sleep(5000, () -> ScriptHandler.cc.game().getGameState().equals(GameState.LOGGED_IN));

        //TODO DEBUGGING
        if(cc.inventory().getItem(995).getStackSize() < 150){
            TaskManager.getInstance().insertAtHeadCopy(new GrandExchange());
            TaskManager.getInstance().insertAtHeadCopy(new Bank());
            return false;
        }
        if(cc.inventory().isFull()){
            TaskManager.getInstance().insertAtHeadCopy(new Bank(risk));
            return false;
        }

        if(!cc.inventory().contains(995)
                || cc.inventory().getItem(995).getStackSize() < 100){
            Time.sleep(100);
            if(!cc.inventory().contains(995)
                    || cc.inventory().getItem(995).getStackSize() < 100) {
                TaskManager.getInstance().insertAtHeadCopy(new Bank(risk));
            }
            return false;
        }
        return true;
    }

    private boolean openShop(){
        setNpc();
        if (npc != null) {
            if(!cc.widgets().isInterfaceOpen()){
                if(npc.canReach(cc)) {
                    JPI.interact(npc, interact, cc);
                }else{
                    cc.webWalking().walkTo(npc.getLocation());
                }
            }
            return true;
        }else{
            cc.webWalking().walkTo(botlocation.getNearestTile(cc));
            return false;
        }
    }

    private void setNpc(){
        npc = cc.npcs().query().nameContains(name).results().nearest();
    }

    private void buyItems(){
        if(boughtItems.isEmpty()){
            boughtItems = (ArrayList<ShopItem>) buyItems.clone();
        }
        if(cc.widgets().isInterfaceOpen()){
            if(!JPI.shop(boughtItems.get(0), cc)){
                boughtItems.remove(boughtItems.get(0));
            }
        }

        if(boughtItems.isEmpty()){
            cc.store().close();
            TaskManager.getInstance().insertAtHeadCopy(new WorldHop());
        }
    }

    @Override
    public String toString() {
        return "Shop{" +
                "buyItems=" + boughtItems +
                '}';
    }


}

