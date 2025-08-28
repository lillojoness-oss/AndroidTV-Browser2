package com.example.tvbrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private HistoryManager historyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyManager = new HistoryManager(this);
        webView = findViewById(R.id.webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
                historyManager.addHistoryItem(url, title);
                super.onPageFinished(view, url);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        // Загружаем стартовую страницу (Яндекс)
        webView.loadUrl("https://yandex.ru");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                webView.loadUrl("https://yandex.ru");
                return true;
            case R.id.menu_refresh:
                webView.reload();
                return true;
            case R.id.menu_history:
                startActivity(new Intent(this, HistoryActivity.class));
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, CursorSettingsActivity.class));
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
