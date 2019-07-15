package com.samkecy.spim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask(){
            public void run() {
                Splash.this.runOnUiThread(new Runnable() {
                    public void run() {
                        finish();
                        startActivity(new Intent(Splash.this, login.class));
                    }
                });
            }
        }, 2000);
    }
}
