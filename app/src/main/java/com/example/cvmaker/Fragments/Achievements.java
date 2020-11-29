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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Achievements extends Fragment implements View.OnClickListener {
    Button btnSave, btnNext, addBtn;
    TextInputEditText editTextAch;
    String achivement = "";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> achievementList;
    boolean check = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.achievment, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnNext = view.findViewById(R.id.nextBtn);
        addBtn = view.findViewById(R.id.addBtn);
        editTextAch = view.findViewById(R.id.achievementEditId);
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        achievementList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        check = sharedPreferences.getBoolean("seventh", false);
        if (check) {
            editTextAch.setText(sharedPreferences.getString("achievement", ""));
            editor.putBoolean("seventh", false);
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
            case R.id.addBtn:
                if (editTextAch.getText().toString().isEmpty()) {
                    Toast.makeText(this.getActivity(), "Please Enter  Field", Toast.LENGTH_SHORT).show();

                } else {
                    addAchievements();
                    btnNext.setVisibility(View.VISIBLE);
                }
                break;
            default:
        }

    }

    private void addAchievements() {
        achivement = Objects.requireNonNull(editTextAch.getText()).toString().trim();
        achievementList.add(achivement);
        editTextAch.getText().clear();
    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new Projects());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void nextFragment() {

        if (achievementList.size() == 0) {
            Toast.makeText(this.getActivity(), "Please Enter  Field", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder achBuilder = new StringBuilder();
            for (String sk : achievementList) {
                achBuilder.append(sk).append(",");

            }
            PersonalInformation.cvModel.setAchievement(achBuilder.toString());
            editor.putString("achievement", achivement);
            editor.putBoolean("seventh", true);
            editor.apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Languages());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Achievements");
    }
}
