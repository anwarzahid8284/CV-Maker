package com.example.cvmaker.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cvmaker.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.File;

public class OpenCvActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener {
    PDFView pdfView;
     String mFileName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv);
        pdfView= (PDFView)findViewById(R.id.pdfView);
        Intent intent=getIntent();
        if(intent!=null){
            mFileName=intent.getStringExtra("fileName");
            displayFromAsset(mFileName);
        }
    }
    private void displayFromAsset(String assetFileName) {
        File file1 = new File(Environment.getExternalStorageDirectory()+"/Resume/"+assetFileName);
        pdfView.fromFile(file1)
                .defaultPage(1)
                .onPageChange(this)
                .load();
    }

    @Override
    public void loadComplete(int nbPages) {

    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }
}