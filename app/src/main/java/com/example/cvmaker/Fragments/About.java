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

public class About extends Fragment implements View.OnClickListener {
    Button btnNext, btnSave;
    TextInputEditText editTextPro, editTextYourself;
    String profession="",yourself="";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean check=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);
        editTextPro = view.findViewById(R.id.profEditID);
        editTextYourself = view.findViewById(R.id.yourselfEditID);
        btnNext = view.findViewById(R.id.nextBtn);
        btnSave = view.findViewById(R.id.saveBtn);
        sharedPreferences= getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        check=sharedPreferences.getBoolean("second",false);
        if(check){
            editTextPro.setText(sharedPreferences.getString("profession",""));
            editTextYourself.setText(sharedPreferences.getString("yourself",""));
            editor.putBoolean("second",false);
            editor.apply();
        }
        btnNext.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                nextFragment();
                break;
            case R.id.saveBtn:
                previousFrag();
                break;
            default:
        }
    }

    private void previousFrag() {
        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new PersonalInformation());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Profile");
    }

    private void nextFragment() {
         profession = Objects.requireNonNull(editTextPro.getText()).toString().trim();
          yourself= Objects.requireNonNull(editTextYourself.getText()).toString().trim();
        if (profession.isEmpty() || yourself.isEmpty()) {
            Toast.makeText(this.getActivity(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            PersonalInformation.cvModel.setProfession(profession);
            PersonalInformation.cvModel.setYourself(yourself);
            editor.putString("profession", profession);
            editor.putString("yourself", yourself);
            editor.putBoolean("second",true);
            editor.apply();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Education(),"About");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }
}
