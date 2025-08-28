package com.example.tvbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tvbrowser.ui.CursorSettingsActivity;
import com.example.tvbrowser.ui.HistoryActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private Button btnHistory;
    private LinearLayout recentHistoryLayout;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "browser_history";
    private static final String KEY_HISTORY = "history_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        btnHistory = findViewById(R.id.btnHistory);
        recentHistoryLayout = findViewById(R.id.recentHistory);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.endsWith(".mp4") || url.endsWith(".mkv") || url.endsWith(".avi")) {
                    Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("VIDEO_URL", url);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                addToHistory(title, url);
                showRecentHistory();
            }
        });

        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://yandex.ru");

        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HistoryActivity.class));
        });

        showRecentHistory();
    }

    private void addToHistory(String title, String url) {
        Set<String> set = prefs.getStringSet(KEY_HISTORY, new LinkedHashSet<>());
        LinkedHashSet<String> history = new LinkedHashSet<>(set);
        history.add(title + "||" + url);
        prefs.edit().putStringSet(KEY_HISTORY, history).apply();
    }

    private void showRecentHistory() {
        recentHistoryLayout.removeAllViews();
        Set<String> set = prefs.getStringSet(KEY_HISTORY, new LinkedHashSet<>());
        ArrayList<String> history = new ArrayList<>(set);

        int count = Math.min(3, history.size());
        for (int i = history.size() - count; i < history.size(); i++) {
            String entry = history.get(i);
            String[] parts = entry.split("\\|\\|");
            String title = parts[0];
            String url = parts[1];

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView tv = new TextView(this);
            tv.setText(title + " (" + url + ")");
            tv.setOnClickListener(v -> webView.loadUrl(url));

            ImageButton btnRemove = new ImageButton(this);
            btnRemove.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            btnRemove.setOnClickListener(v -> {
                history.remove(entry);
                prefs.edit().putStringSet(KEY_HISTORY, new LinkedHashSet<>(history)).apply();
                showRecentHistory();
            });

            row.addView(tv);
            row.addView(btnRemove);
            recentHistoryLayout.addView(row);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cursor_settings) {
            startActivity(new Intent(this, CursorSettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
