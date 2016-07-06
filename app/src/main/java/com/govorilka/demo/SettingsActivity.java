package com.govorilka.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by jack on 03.07.2016.
 */

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settings);
    }

    public void callMainScreen(View view) {
        finish();
        Intent intent = new Intent(this, GovorilkaActivity.class);
        startActivity(intent);
    }

    // отключаем кнопку назад
    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Только кнопкой 'Назад'", Toast.LENGTH_LONG).show();
    }


}
