package com.ykf.bishe.ui.util;

import android.app.Application;

/**
 * Created by ykf on 16/5/16.
 */
public class App extends Application{
    private static App INSTANCE;
    public static String username ="";
    public static String name = "";
    public static String phone = "";
    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
