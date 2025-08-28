package com.example.tvbrowser;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    private LinearLayout historyList;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.history_list);
        prefs = getSharedPreferences("browser_history", MODE_PRIVATE);

        loadHistory();

        Button clearAll = findViewById(R.id.clear_history_button);
        clearAll.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            historyList.removeAllViews();
        });
    }

    private void loadHistory() {
        historyList.removeAllViews();
        for (String url : prefs.getAll().keySet()) {
            TextView tv = new TextView(this);
            tv.setText(prefs.getString(url, url));
            historyList.addView(tv);
        }
    }
}
