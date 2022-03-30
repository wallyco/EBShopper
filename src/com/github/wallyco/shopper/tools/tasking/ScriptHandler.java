package com.github.wallyco.shopper.tools.tasking;

import com.epicbot.api.shared.APIContext;
import com.github.wallyco.shopper.tools.item.Memory;

public class ScriptHandler {
    public static APIContext cc;
    public static Memory memory = Memory.getInstance();
    public static void setContextContainer(APIContext cc){
        ScriptHandler.cc = cc;
    }
    public static void log(String s){
        System.out.println(s);
    }
}