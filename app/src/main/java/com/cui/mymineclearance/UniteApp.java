package com.cui.mymineclearance;

import android.app.Application;
import android.util.Log;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 19:24
 */
//表示全局要用的类
public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDB(getApplicationContext());
    }
}