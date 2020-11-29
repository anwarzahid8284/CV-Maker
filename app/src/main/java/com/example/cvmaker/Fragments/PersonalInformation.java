package com.example.cvmaker.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.cvmaker.Activity.TemplatesActivity;
import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.Model.CVModel;
import com.example.cvmaker.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class PersonalInformation extends Fragment implements View.OnClickListener {
    Button btnSave, btnNext, selectPicBtn;
    TextInputEditText nameEdit, phoneEdit, addressEdit, emailEdit;
    ImageView imageView;
    String permission = "TAG";

    public static final int PICK_IMAGE = 1;
    String userName, userPhone, userEmail, userAddress, imagePath = "";
    public static CVModel cvModel;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final int REQUEST_PERMISSION = 1;
    boolean check = false;
    File root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_information, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnNext = view.findViewById(R.id.nextBtn);
        selectPicBtn = view.findViewById(R.id.selectPicBtnID);
        nameEdit = view.findViewById(R.id.nameID);
        phoneEdit = view.findViewById(R.id.phoneID);
        addressEdit = view.findViewById(R.id.addressID);
        emailEdit = view.findViewById(R.id.emailID);
        imageView = view.findViewById(R.id.profileID);

        cvModel = new CVModel();
        sharedpreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        selectPicBtn.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        check = sharedpreferences.getBoolean("first", false);
        if (check) {

            nameEdit.setText(sharedpreferences.getString("userName", ""));
            emailEdit.setText(sharedpreferences.getString("userEmail", ""));
            phoneEdit.setText(sharedpreferences.getString("userPhone", ""));
            addressEdit.setText(sharedpreferences.getString("userAddress", ""));
            String getImagePath = sharedpreferences.getString("profilePath", "");
            if (getImagePath != null) {
                File filePath = new File(getImagePath);
                Glide.with(getActivity())
                        .load(new File(filePath.getPath()))
                        .into(imageView);
            } else {

                Toast.makeText(getActivity(), "image are not load", Toast.LENGTH_SHORT).show();
            }
            editor.putBoolean("first", false);
            editor.apply();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                previousFrag();
                break;
            case R.id.nextBtn:
                nextFragment();
                break;
            case R.id.selectPicBtnID:
                if (isStoragePermissionGranted()) intentToGallery();
                break;
            default:
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Personal Information");
    }

    private void previousFrag() {
        Intent intent =new Intent(getActivity(),TemplatesActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void intentToGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                root = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/data/");
                if (!root.exists()) {
                    root.mkdirs();
                } else {
                    Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                    imagePath = getRealPathFromURI(imageUri);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private void nextFragment() {
        // data insert into
        userName = Objects.requireNonNull(nameEdit.getText()).toString().trim();
        userEmail = Objects.requireNonNull(emailEdit.getText()).toString().trim();
        userPhone = Objects.requireNonNull(phoneEdit.getText()).toString().trim();
        userAddress = Objects.requireNonNull(addressEdit.getText()).toString().trim();
        if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty() || userAddress.isEmpty() || !imageView.isShown()) {
            Toast.makeText(this.getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            cvModel.setName(userName);
            cvModel.setEmail(userEmail);
            cvModel.setMobileNo(userPhone);
            cvModel.setAddress(userAddress);
            cvModel.setPicture(imagePath);
            // save Value
            editor.putString("profilePath", imagePath);
            editor.putString("userName", userName);
            editor.putString("userPhone", userPhone);
            editor.putString("userAddress", userAddress);
            editor.putString("userEmail", userEmail);
            editor.putBoolean("first", true);
            editor.apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new About());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    // permission
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Objects.requireNonNull(getActivity()).checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Allowed", Toast.LENGTH_LONG).show();
                Log.v(permission, "Permission is granted");

                return true;
            } else {

                Log.v(permission, "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intentToGallery();
            } else {
                isStoragePermissionGranted();
            }
        }
    }
}

