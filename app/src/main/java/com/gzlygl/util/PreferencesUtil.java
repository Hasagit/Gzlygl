package com.gzlygl.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by DengJf on 2017/11/9.
 */

public class PreferencesUtil {
    private SharedPreferences sharedPreferences;

    public PreferencesUtil(Context context){
        sharedPreferences=context.getSharedPreferences("info",context.MODE_PRIVATE);
    }

    public  String getString(String key){
        return sharedPreferences.getString(key,"");
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }


    public void setString(String key,String values){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key,values);
        editor.commit();
    }

    public void setBoolean(String key,boolean values){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,values);
        editor.commit();
    }

    public void setInt(String key,int values){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(key,values);
        editor.commit();
    }
}
