package com.github.wallyco.shopper.bot.tasks;

import com.epicbot.api.os.model.game.GameState;
import com.epicbot.api.shared.util.time.Time;
import com.github.wallyco.shopper.tools.tasking.ScriptHandler;
import com.github.wallyco.shopper.tools.tasking.Task;

public class WorldHop extends ScriptHandler implements Task {
    private int currentWorld;

    public WorldHop(){
        currentWorld = cc.world().getCurrent();
    }

    @Override
    public boolean execute() {
        cc.world().openWorldMenu();
        Time.sleep(2000, () -> cc.world().hopToF2P());
        Time.sleep(10000, () -> !cc.game().getGameState().is(GameState.HOPPING));

        if(cc.world().getCurrent() != currentWorld){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "WorldHop off{" +
                "currentWorld=" + currentWorld +
                '}';
    }
}
