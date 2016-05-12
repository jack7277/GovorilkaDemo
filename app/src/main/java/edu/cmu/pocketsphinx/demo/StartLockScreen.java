package edu.cmu.pocketsphinx.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
public class StartLockScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // запускаем сервис
        startService(new Intent(this, GovorilkaService.class));
        // убиваем активность
        finish();
    }
}