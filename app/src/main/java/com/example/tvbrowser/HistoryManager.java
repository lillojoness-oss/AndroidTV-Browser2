package com.example.tvbrowser;

import android.content.Context;
import android.content.SharedPreferences;

public class HistoryManager {
    private static final String PREF_NAME = "browser_history";
    private final SharedPreferences prefs;

    public HistoryManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addHistory(String url, String title) {
        prefs.edit().putString(url, title).apply();
    }
}
