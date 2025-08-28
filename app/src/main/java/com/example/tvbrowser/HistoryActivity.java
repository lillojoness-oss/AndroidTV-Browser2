package com.example.tvbrowser;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private HistoryManager historyManager;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyManager = new HistoryManager(this);

        ListView listView = findViewById(R.id.historyListView);
        Button clearButton = findViewById(R.id.clearHistoryButton);

        List<String> history = historyManager.getHistory();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);
        listView.setAdapter(adapter);

        clearButton.setOnClickListener(v -> {
            historyManager.clearHistory();
            adapter.clear();
            adapter.notifyDataSetChanged();
        });
    }
}
