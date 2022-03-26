package com.github.wallyco.shopper.tools.tasking;

import com.epicbot.api.shared.APIContext;

public class ScriptHandler {
    public static APIContext cc;
    public static void setContextContainer(APIContext cc){
        ScriptHandler.cc = cc;
    }
}