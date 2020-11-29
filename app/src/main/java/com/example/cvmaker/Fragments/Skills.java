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

public class Skills extends Fragment implements View.OnClickListener {
    Button btnSave, btnNext, btnAdd;
    TextInputEditText textInputEditText;
    String skill;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> skillsList;
    boolean check = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skills, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnNext = view.findViewById(R.id.nextBtn);
        btnAdd = view.findViewById(R.id.addBtn);
        textInputEditText = view.findViewById(R.id.skillEditID);
        skillsList=new ArrayList<>();
        btnSave.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        check = sharedPreferences.getBoolean("fifth", false);
        if (check) {
            textInputEditText.setText(sharedPreferences.getString("skill", ""));
            editor.putBoolean("fifth", false);
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
                if(textInputEditText.getText().toString().isEmpty()){
                    Toast.makeText(this.getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();

                }else {
                    addSkills();
                    btnNext.setVisibility(View.VISIBLE);
                }
                break;
            default:
        }
    }

    private void addSkills() {
        skill = Objects.requireNonNull(textInputEditText.getText()).toString().trim();
        skillsList.add(skill);
        textInputEditText.getText().clear();

    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new Experienced());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void nextFragment() {

        if (skillsList.size() == 0) {
            Toast.makeText(this.getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            StringBuilder skBuilder=new StringBuilder();
            for(String sk:skillsList){
                skBuilder.append(sk).append(",");

            }
            PersonalInformation.cvModel.setSkill(skBuilder.toString());
            editor.putString("skill", skill);
            editor.putBoolean("fifth", true);
            editor.apply();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Projects());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Skills");
    }
}
