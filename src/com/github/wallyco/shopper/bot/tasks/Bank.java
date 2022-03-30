package com.github.wallyco.shopper.bot.tasks;

import com.epicbot.api.shared.webwalking.model.RSBank;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.Task;
import com.github.wallyco.shopper.tools.tasking.TaskManager;

import java.util.ArrayList;
import java.util.Objects;

public class Bank extends ScriptHandler implements Task {
    private int hash = 0002;

    private int coinAmount = 0;
    public Bank(){}

    public Bank(int coinAmount){
        this.coinAmount = coinAmount;
    }

    @Override
    public boolean execute() {
        if(shouldBank()){
            if(isBankOpen()){
                bank();
            }
        }
        return shouldBank();
    }

    private boolean shouldBank(){
        log("" + cc.inventory().onlyContains("Coins"));
        if(cc.inventory().onlyContains("Coins")){
            if(cc.bank().isOpen())
                cc.bank().close();
            return false;
        }
        return true;
    }

    private boolean isBankOpen(){
        if(!cc.bank().isOpen()) {
            if (!cc.bank().open()) {
                cc.webWalking().walkToBank();
            }
        }
        return cc.bank().isOpen();
    }

    private void bank(){
        if(!cc.bank().contains("Coins")){
            TaskManager.getInstance().insertAtHeadCopy(new GrandExchange());
        }
        if(!cc.inventory().contains("Coins")){
            if(coinAmount == 0){
                cc.bank().withdrawAll("Coins");
            }else{
                cc.bank().withdraw(coinAmount, "Coins");
            }
        }else if(coinAmount != 0){
            if(cc.inventory().getItem("Coins").getStackSize() < coinAmount){
                cc.bank().withdraw(coinAmount - cc.inventory().getItem("Coins").getStackSize(), "Coins");
            }
        }else{
            cc.bank().withdrawAll("Coins");
        }
        cc.bank().depositAllExcept("Coins");
    }

    @Override
    public String toString() {
        return "Banking";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return hash == bank.hash && coinAmount == bank.coinAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, coinAmount);
    }



}
