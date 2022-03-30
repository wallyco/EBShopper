package com.github.wallyco.shopper.script;

import com.epicbot.api.os.model.game.GameState;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import com.epicbot.api.shared.util.paint.frame.PaintFrame;
import com.epicbot.api.shared.util.time.Time;
import com.epicbot.api.shared.webwalking.model.RSBank;
import com.github.wallyco.shopper.bot.location.Shops;
import com.github.wallyco.shopper.bot.tasks.GrandExchange;
import com.github.wallyco.shopper.test.DummyTask;
import com.github.wallyco.shopper.tools.JPI;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.TaskManager;
import com.github.wallyco.shopper.bot.tasks.Shop;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@ScriptManifest(gameType = GameType.OS, name = "jpxShop")
public class Main extends LoopScript {
    private TaskManager tm = TaskManager.getInstance();
    private int offset = 1200;
    private boolean firstrun = true;

    @Override
    public boolean onStart(String... strings) {
        tm.add(new Shop(Shops.VARROCK_ARCHERY));
        ScriptHandler.setContextContainer(this.getAPIContext());
        return true;
    }

    @Override
    protected int loop() {
        int i = JPI.generateRandomInputInt(offset);
        if(ScriptHandler.cc.client().isLoggedIn()){
            if(firstrun){
                System.out.println("Initializing script");
                Time.sleep(3000);
                firstrun = false;
            }
            Time.sleep(5000, () -> ScriptHandler.cc.game().getGameState().equals(GameState.LOGGED_IN));
            tm.execute();
        }
        return i;
    }

    @Override
    protected void onPaint(Graphics2D g, APIContext ctx) {
        PaintFrame frame = new PaintFrame("Shopper");
        frame.addLine("Current Task", tm.getCurrentTask());
        frame.draw(g, 0, 350, ctx);
    }
}

