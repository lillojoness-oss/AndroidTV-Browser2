package com.example.tvbrowser.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tvbrowser.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class HistoryActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "browser_history";
    private static final String KEY_HISTORY = "history_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ListView listView = findViewById(R.id.historyList);
        Button btnClear = findViewById(R.id.btnClearHistory);

        Set<String> set = prefs.getStringSet(KEY_HISTORY, new LinkedHashSet<>());
        ArrayList<String> history = new ArrayList<>(set);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        listView.setAdapter(adapter);

        btnClear.setOnClickListener(v -> {
            prefs.edit().remove(KEY_HISTORY).apply();
            adapter.clear();
            adapter.notifyDataSetChanged();
        });
    }
}
