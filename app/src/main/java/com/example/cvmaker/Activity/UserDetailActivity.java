package com.example.cvmaker.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.cvmaker.Fragments.Languages;
import com.example.cvmaker.Fragments.PersonalInformation;
import com.example.cvmaker.R;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public FrameLayout frameLayout;
    Bitmap b;
    String permission = "abc";
    public static ImageView imageViewSave, backArrow;
    public static TextView textViewSaveCv;

    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        imageViewSave = findViewById(R.id.cv_save);
        frameLayout = findViewById(R.id.userDetail_frag_container);
        textViewSaveCv = findViewById(R.id.cv_save_text_id);
        textViewTitle = findViewById(R.id.title_id);
        backArrow = findViewById(R.id.back_arrow_id);
        imageViewSave.setVisibility(View.GONE);
        textViewSaveCv.setVisibility(View.GONE);
        imageViewSave.setOnClickListener(this);
        textViewSaveCv.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        imageViewSave.startAnimation(animation);
        template();
    }

    public void setActionBarTitle(String title) {
        textViewTitle.setText(title);
    }

    private void template() {
        getSupportFragmentManager().beginTransaction().replace(R.id.userDetail_frag_container, new PersonalInformation()).commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_save:
            case R.id.cv_save_text_id:
                cvSave();
                break;
            case R.id.back_arrow_id:
                Languages.check=false;
                Intent intent = new Intent(this, TemplatesActivity.class);
                startActivity(intent);
                finish();
                break;
            default:

        }

    }

    private void cvSave() {
        if (isStoragePermissionGranted()) {
            frameLayout.setDrawingCacheEnabled(true);
            b = Bitmap.createBitmap(frameLayout.getDrawingCache());
            OutputStream fOut = null;
            int counter = 0;
            // root directory
            File root = new File(Environment.getExternalStorageDirectory(), "Resume/");
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, "image" + counter + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            try {
                fOut = new FileOutputStream(file);
                b.compress(Bitmap.CompressFormat.JPEG, 100, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                fOut.flush(); // Not really required
                fOut.close(); // do not forget to close the stream
                MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
                Image image = Image.getInstance(root + "/" + "image0.jpg");
                Document document = new Document(image);
                PdfWriter.getInstance(document, new FileOutputStream(root + "/" + "Resume" + System.currentTimeMillis() + ".pdf")); //  Change pdf's name.
                document.open();
                // Change image's name and extension.

                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                        - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                image.scalePercent(scaler);
                image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);

                document.add(image);
                document.close();
                // delete file from directory
                File deleteFile = new File(root, file.getName());
                if (deleteFile.exists()) {
                    file.getCanonicalFile().delete();
                    if (file.exists()) {
                        getApplicationContext().deleteFile(file.getName());
                        Toast.makeText(this, "Image deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                // intent example
                Intent ne = new Intent(UserDetailActivity.this, HomeActivity.class);
                startActivity(ne);
                finish();
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(permission, "Permission is granted");
                return true;
            } else {

                Log.v(permission, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(permission, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(permission, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }
}