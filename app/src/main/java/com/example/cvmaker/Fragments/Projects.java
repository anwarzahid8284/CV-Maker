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

public class Projects extends Fragment implements View.OnClickListener {
    Button btnSave, btnNext;

    TextInputEditText editTextPName, editTextPDes, editTextTools;
    String pName, pDesc, pTool;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean check = false;
    CVModel cvModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.projects, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnNext = view.findViewById(R.id.nextBtn);
        editTextPName = view.findViewById(R.id.projectTitleID);
        editTextPDes = view.findViewById(R.id.projectDesEditID);
        editTextTools = view.findViewById(R.id.toolsEditID);
        cvModel = new CVModel();
        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        check = sharedPreferences.getBoolean("sixth", false);

        if (check) {
            editTextPName.setText(sharedPreferences.getString("pName", ""));
            editTextPDes.setText(sharedPreferences.getString("pDesc", ""));
            editTextTools.setText(sharedPreferences.getString("pTool", ""));
            editor.putBoolean("sixth", false);
            editor.apply();

        }
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
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

        pName = Objects.requireNonNull(editTextPName.getText()).toString().trim();
        pDesc = Objects.requireNonNull(editTextPDes.getText()).toString().trim();
        pTool = Objects.requireNonNull(editTextTools.getText()).toString().trim();
        if (pName.isEmpty() || pDesc.isEmpty() || pTool.isEmpty()) {
            Toast.makeText(this.getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            PersonalInformation.cvModel.setProjectName(pName);
            PersonalInformation.cvModel.setProjectDes(pDesc);
            PersonalInformation.cvModel.setProjectTools(pTool);
            editor.putString("pName", pName);
            editor.putString("pDesc", pDesc);
            editor.putString("pTool", pTool);
            editor.putBoolean("sixth", true);
            editor.apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Achievements());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new Skills());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Projects");
    }
}
