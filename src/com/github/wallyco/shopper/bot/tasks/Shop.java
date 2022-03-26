package com.github.wallyco.shopper.bot.tasks;

import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.model.Area;
import com.github.wallyco.shopper.bot.location.Shops;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.Task;
import com.github.wallyco.shopper.tools.JPI;

public class Shop extends ScriptHandler implements Task{
    private NPC npc;
    private String name;
    private String interact;
    private Area botlocation;
    private String[] buyItems;

    public Shop(Shops info) {
        name = info.getName();
        interact = info.getInteract();
        botlocation = info.getArea();
        buyItems = info.getBuyItems();
    }

    @Override
    public void execute() {
        if(shouldExecute()){
            if(getNpc()){
                buyItems();
            }
        }
    }
    //TODO Remove
    private boolean shouldExecute(){
        return true;
    }

    private boolean getNpc(){
        setNpc();
        if (npc != null) {
            if(!cc.widgets().isInterfaceOpen()){
                if(npc.canReach(cc)) {
                    JPI.interact(npc, interact, cc);
                }else{
                    if(npc.getLocation() != null) {
                        cc.webWalking().walkTo(npc.getLocation());
                    }
                }
            }
            return true;
        }else{
            cc.webWalking().walkTo(botlocation.getRandomTile());
            return false;
        }
    }

    private void setNpc(){
        npc = cc.npcs().query().nameContains(name).results().nearest();
    }

    private void buyItems(){
        if(cc.widgets().isInterfaceOpen()){
            System.out.println(cc.store().getCount("Bronze arrow"));
        }
    }
}

