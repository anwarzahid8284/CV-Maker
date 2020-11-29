package com.example.cvmaker.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvmaker.Adopter.CVListAdapter;
import com.example.cvmaker.Fragments.Languages;
import com.example.cvmaker.Model.CVListModel;
import com.example.cvmaker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {

    String pdfPattern = ".pdf";
    List<CVListModel> cvListModel;
    RecyclerView recyclerView;
    CVListAdapter cvListAdapter;
    BottomNavigationView bottomNavigationView;
    File root;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerViewId);
        cvListModel = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottom_navigatin_id);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cvListAdapter = new CVListAdapter(HomeActivity.this, cvListModel);
        myToolbar = findViewById(R.id.custom_toolbar);
        TextView textView = new TextView(this);
        textView.setText("CV Maker");
        textView.setTextSize(20.0f);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(Color.WHITE);
        myToolbar.addView(textView);
        // set arrow back button
        navigationView = (NavigationView) findViewById(R.id.nevigation);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.customized_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.opnen_navigation,
                R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        navigationView.setItemIconTintList(null);
        toggle.syncState();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        showCVS();
    }

    private void showCVS() {
        root = new File(Environment.getExternalStorageDirectory(), "Resume/");
        File[] fileList = root.listFiles();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.getName().endsWith(pdfPattern)) {
                    DecimalFormat decimalFormat = new DecimalFormat("0.0");
                    cvListModel.add(new CVListModel(file.getName(), decimalFormat.format(file.length() / 1024)));
                }
            }
        }
        if (cvListModel.size() > 0) {
            recyclerView.setAdapter(cvListAdapter);
            SharedPreferences sharedPreferences = getSharedPreferences("CV_LIST_COUNT", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("cvList", true);
            editor.apply();
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("CV_LIST_COUNT", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("cvList", false);
            editor.apply();
        }

    }

    private void closedrowyar() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.templates:
            case R.id.templates_id:
                Intent intentTemplateAc = new Intent(this, AllTemplatesActivity.class);
                startActivity(intentTemplateAc);
                finish();
                break;
            case R.id.newCv:
            case R.id.newCv_id:
                newCv();
                break;
            case R.id.moreApps_id:
                Toast.makeText(this, "More Apps", Toast.LENGTH_SHORT).show();
                closedrowyar();
                break;
            case R.id.rateUs_id:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                closedrowyar();
                break;
            case R.id.share_id:
            case R.id.shareId:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                closedrowyar();
                break;
            default:
                return true;
        }
        return false;
    }

    public void newCv() {
        Languages.check = false;
        SharedPreferences sharedPreferences = getSharedPreferences("CV_LIST_COUNT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("cvList", false);
        editor.apply();
        Intent intentToTemplatesAc = new Intent(this, TemplatesActivity.class);
        startActivity(intentToTemplatesAc);
        finish();
    }
}