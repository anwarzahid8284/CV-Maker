package com.example.cvmaker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cvmaker.Fragments.TemplateFragment;
import com.example.cvmaker.R;

public class TemplatesActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageViewCV;
    TextView textViewCreateCv;

    SharedPreferences sharedPreferencesBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        imageViewCV = (ImageView) findViewById(R.id.cvImage);
        textViewCreateCv = findViewById(R.id.cv_create_text_id);
        imageViewCV.setOnClickListener(this);
        textViewCreateCv.setOnClickListener(this);
        visibilityOn();
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        imageViewCV.startAnimation(animation);
        sharedPreferencesBT = getSharedPreferences("CV_LIST_COUNT", MODE_PRIVATE);
        boolean cvList = sharedPreferencesBT.getBoolean("cvList", false);
        if (cvList) {
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
            finish();

        } else {
            template();
        }
    }

    private void template() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TemplateFragment()).commit();
    }


    private void createCV() {
        Intent intentToUserD = new Intent(this, UserDetailActivity.class);
        startActivity(intentToUserD);
        finish();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvImage:
                createCV();
                visibilityOff();
                break;
            case R.id.cv_create_text_id:
                createCV();
                visibilityOff();
                break;
            default:
        }
    }



    public void visibilityOff() {
        imageViewCV.setVisibility(View.GONE);
        textViewCreateCv.setVisibility(View.GONE);
    }

    public void visibilityOn() {
        imageViewCV.setVisibility(View.VISIBLE);
        textViewCreateCv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}