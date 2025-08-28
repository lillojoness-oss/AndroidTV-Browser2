package com.example.androidtvbrowser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private LinearLayout historyContainer;
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "browser_history";
    private static final String KEY_HISTORY = "history";

    private ArrayList<String> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = findViewById(R.id.search_input);
        historyContainer = findViewById(R.id.history_container);
        Button btnHistory = findViewById(R.id.btn_history);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadHistory();

        findViewById(R.id.btn_voice).setOnClickListener(v -> {
            // TODO: голосовой ввод
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });

        renderHistory();
    }

    private void loadHistory() {
        Set<String> saved = prefs.getStringSet(KEY_HISTORY, new HashSet<>());
        historyList = new ArrayList<>(saved);
    }

    private void saveHistory() {
        Set<String> saveSet = new HashSet<>(historyList);
        prefs.edit().putStringSet(KEY_HISTORY, saveSet).apply();
    }

    private void renderHistory() {
        historyContainer.removeAllViews();

        int count = Math.min(3, historyList.size());
        for (int i = 0; i < count; i++) {
            String entry = historyList.get(i);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv = new TextView(this);
            tv.setText(entry);
            tv.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            ImageButton deleteBtn = new ImageButton(this);
            deleteBtn.setImageResource(android.R.drawable.ic_delete);
            deleteBtn.setOnClickListener(v -> {
                historyList.remove(entry);
                saveHistory();
                renderHistory();
            });

            row.addView(tv);
            row.addView(deleteBtn);

            historyContainer.addView(row);
        }
    }
}
