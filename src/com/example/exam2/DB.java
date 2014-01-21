package com.example.exam2;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

class DB {
    private static volatile DB instance;
    private DBHelper helper;

    private DB() {
    }

    public static synchronized DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public void init(Context context) {
        if (helper == null)
            helper = OpenHelperManager.getHelper(context, DBHelper.class);
    }

    public void release() {
        if (helper != null)
            OpenHelperManager.releaseHelper();
    }

    public DBHelper getHelper() {
        return helper;
    }
}
