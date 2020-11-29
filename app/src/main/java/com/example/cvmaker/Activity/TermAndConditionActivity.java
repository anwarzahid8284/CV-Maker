package com.example.cvmaker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cvmaker.R;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class TermAndConditionActivity extends AppCompatActivity {
    CheckBox checkBox;
    Button buttonGetStarted;
    private AdView adView;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_term_and_condition);
        buttonGetStarted = findViewById(R.id.getStartedId);
        checkBox = findViewById(R.id.checkbox);
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        SharedPreferences sharedPreferences=getSharedPreferences("state",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putBoolean("true",checkBox.isChecked());
                        editor.apply();
                        setmInterstitialAd();

                    }else {
                        Toast.makeText(TermAndConditionActivity.this, "Accept Term and Condition", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        // Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);
        adView = new AdView(TermAndConditionActivity.this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);

        // Add the ad view to your activity layout
        adContainer.addView(adView);
        adView.loadAd();


    }


   public void setmInterstitialAd() {
       final InterstitialAd mInterstitialAd;
       mInterstitialAd = new InterstitialAd(this);
       mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
       AdRequest adRequestInterstitial = new AdRequest.Builder().addTestDevice("deviceid").build();
       mInterstitialAd.loadAd(adRequestInterstitial);

       mInterstitialAd.setAdListener(new AdListener() {
           @Override
           public void onAdClosed() {
               Intent intent = new Intent(TermAndConditionActivity.this, TemplatesActivity.class);
               startActivity(intent);
               finish();
           }

           @Override
           public void onAdLoaded() {
               mInterstitialAd.show();
           }

           @Override
           public void onAdFailedToLoad(int errorCode) {

           }
       });
    }
    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

}