/**
 * Class Name: com.vigram.downloader.ui;
 * App Name: Vigram
 * Package: com.vigram.downloader
 * Created: 10:18 PM GMT+7 1/6/2020
 * Developer: Vigram.cc
 * By: Zuhrul Anam (zuhrul@vigram.cc)
 * Edited: 10:19 PM GMT+7 1/6/2020
 */

package com.vigram.downloader.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.vigram.downloader.BuildConfig;
import com.vigram.downloader.MainActivity;
import com.vigram.downloader.R;

public class SplashScreen extends AppCompatActivity {
    private Handler handler;
    private TextView appVersion, splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        appVersion = findViewById(R.id.id_version_app);
        splashText = findViewById(R.id.id_text_splash_title);
        appVersion.setText(BuildConfig.VERSION_NAME);
        Intent intent = new Intent(this, MainActivity.class);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashText.setText(getString(R.string.app_name));
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashText.setText("By: @zuhrul.inc");
            }
        }, 2000);
        handler.postDelayed(() -> {
            startActivity(intent);
            finish();
        }, 3000);

    }
}
