package com.example.tvbrowser;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryManager {
    private static final String PREFS_NAME = "BrowserHistory";
    private static final String KEY_HISTORY = "history";
    private final SharedPreferences prefs;

    public HistoryManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void addHistoryItem(String url, String title) {
        Set<String> historySet = prefs.getStringSet(KEY_HISTORY, new HashSet<>());
        Set<String> newSet = new HashSet<>(historySet);
        newSet.add(title + " - " + url);
        prefs.edit().putStringSet(KEY_HISTORY, newSet).apply();
    }

    public List<String> getHistory() {
        Set<String> historySet = prefs.getStringSet(KEY_HISTORY, new HashSet<>());
        return new ArrayList<>(historySet);
    }

    public void clearHistory() {
        prefs.edit().remove(KEY_HISTORY).apply();
    }
}
