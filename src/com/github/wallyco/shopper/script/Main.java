package com.github.wallyco.shopper.script;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.github.wallyco.shopper.bot.location.Shops;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.TaskManager;
import com.github.wallyco.shopper.bot.tasks.Shop;


import java.awt.*;

@ScriptManifest(gameType = GameType.OS, name = "jpxShop")
public class Main extends LoopScript {
    private TaskManager tm = TaskManager.getInstance();
    private boolean firstrun = true;
    private int offset = 1500;

    @Override
    public boolean onStart(String... strings) {
        tm.add(new Shop(Shops.VARROCK_ARCHERY));
        ScriptHandler.setContextContainer(this.getAPIContext());
        return true;
    }

    @Override
    protected int loop() {
        int i = generateRandomInputInt(offset);
        if(ScriptHandler.cc.client().isLoggedIn()){
            tm.execute();
        }
        return i;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext ctx) {
        PaintFrame frame = new PaintFrame("Test Script");
        frame.addLine("Title", "Value");
        frame.draw(g, 0, 170, ctx);
    }

    private int generateRandomInputInt(int offset) {
        return (int) ((Math.random() * offset) + 10);
    }

}
