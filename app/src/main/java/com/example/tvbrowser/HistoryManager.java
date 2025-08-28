package com.example.tvbrowser;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryManager {
    private static final String PREFS_NAME = "history_prefs";
    private static final String KEY_HISTORY = "history";
    private static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void addHistory(String entry) {
        Set<String> history = prefs.getStringSet(KEY_HISTORY, new HashSet<>());
        history.add(entry);
        prefs.edit().putStringSet(KEY_HISTORY, history).apply();
    }

    public static List<String> getHistory() {
        return new ArrayList<>(prefs.getStringSet(KEY_HISTORY, new HashSet<>()));
    }

    public static void clearHistory() {
        prefs.edit().remove(KEY_HISTORY).apply();
    }
}
