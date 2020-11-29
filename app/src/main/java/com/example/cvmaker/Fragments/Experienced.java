package com.example.cvmaker.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.Model.CVModel;
import com.example.cvmaker.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Experienced extends Fragment implements View.OnClickListener {
    Button btnSave, btnNext;
    TextInputEditText editTextExp;
    String experience;
    boolean check=false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experienced, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnNext = view.findViewById(R.id.nextBtn);
        editTextExp = view.findViewById(R.id.experiencedEditID);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        sharedPreferences= Objects.requireNonNull(getActivity()).getSharedPreferences("save", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        check=sharedPreferences.getBoolean("fourth",false);
        if(check){
                editTextExp.setText(sharedPreferences.getString("experience",""));
                editor.putBoolean("fourth",false);
                editor.apply();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saveBtn:
                prevFragment();
                break;
            case R.id.nextBtn:
                nextFragment();
                break;
            default:
        }

    }

    private void nextFragment() {
        experience = Objects.requireNonNull(editTextExp.getText()).toString().trim();
        if (experience.isEmpty()) {
            Toast.makeText(this.getActivity(), "Please Enter Field", Toast.LENGTH_SHORT).show();
        } else {
            PersonalInformation.cvModel.setExperienced(experience);
            editor.putString("experience",experience);
            editor.putBoolean("fourth",true);
            editor.apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Skills());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new Education());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Experienced");
    }
}
