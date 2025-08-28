package com.example.tvbrowser;

import android.os.Bundle;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

public class CursorSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor_settings);

        SeekBar speedBar = findViewById(R.id.cursorSpeedSeekBar);
        speedBar.setProgress(5);
    }
}
