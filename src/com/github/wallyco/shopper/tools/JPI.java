package com.github.wallyco.shopper.tools;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.util.time.Time;

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
}
