package com.example.cvmaker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cvmaker.R;
import com.facebook.ads.InterstitialAd;

public class SplashScreenActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        final SharedPreferences sharedPreferences = getSharedPreferences("state", MODE_PRIVATE);
        sharedPreferences.getBoolean("true", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sharedPreferences.getBoolean("true",false)){
                    Intent mainIntent = new Intent(SplashScreenActivity.this, TemplatesActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashScreenActivity.this, TermAndConditionActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }

        }, 5000);
    }
}