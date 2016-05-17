package com.ykf.bishe.ui.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

/**
 * Created by ykf on 16/5/16.
 */
public class SharePreferenceManager {
    private final String PREFERENCE_NAME = "YC";
    private WeakReference<Context> wr = null;
    private static SharedPreferences sharedPreferences;
    private static SharePreferenceManager sharePreferenceManager;
    private static SharedPreferences.Editor editor;

    private String NAME = PREFERENCE_NAME + "name";
    private String PHONE = PREFERENCE_NAME + "phone";
    private String USERNAME = PREFERENCE_NAME + "username";

    private SharePreferenceManager(Context context) {
        wr = new WeakReference<Context>(context);
        sharedPreferences = wr.get().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        context = null;

    }

    public static SharePreferenceManager getInstance(Context context) {

        if (sharePreferenceManager == null) {
            synchronized (SharePreferenceManager.class) {
                if (sharePreferenceManager == null) {
                    sharePreferenceManager = new SharePreferenceManager(context);
                }
            }
        }
        return sharePreferenceManager;
    }


    public String getName(){
        return sharedPreferences.getString(NAME,"");
    }
    public void setName(String name){
        editor = sharedPreferences.edit();
        editor.putString(NAME,name);
        editor.commit();
    }
    public String getPhone(){
        return sharedPreferences.getString(PHONE,"");
    }
    public void setPhone(String phone){
        editor = sharedPreferences.edit();
        editor.putString(PHONE,phone);
        editor.commit();
    }
    public String getUsername(){
        return sharedPreferences.getString(USERNAME,"");
    }
    public void setUsername(String username){
        editor = sharedPreferences.edit();
        editor.putString(USERNAME,username);
        editor.commit();
    }

}
