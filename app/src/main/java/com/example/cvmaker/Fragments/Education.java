package com.example.cvmaker.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.Model.CVModel;
import com.example.cvmaker.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Education extends Fragment implements View.OnClickListener{
    Button btnEdu, btnNext, addBtn;
    TextInputEditText textInputEditTextEdu, textInputEditTextFrom, textInputEditTextTo,textInputEditTextMarks;

    ArrayList<String> instituteList, fromList, toList,marksList;
    String institute, from, to,marks;
    boolean check = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CVModel cvModel;
    String fromDate, toDate;
    TextInputLayout textInputLayout,marksLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.education, container, false);
        textInputLayout = view.findViewById(R.id.educationLayout);
        marksLayout=view.findViewById(R.id.marksLayout);
        btnEdu = view.findViewById(R.id.eduBtn);
        addBtn = view.findViewById(R.id.addBtn);
        textInputEditTextEdu = view.findViewById(R.id.eduEdittext);
        textInputEditTextFrom = view.findViewById(R.id.fromDate);
        textInputEditTextTo = view.findViewById(R.id.toDate);
        textInputEditTextMarks=view.findViewById(R.id.marks);
        btnNext = view.findViewById(R.id.nextBtn);
        cvModel = new CVModel();
        btnEdu.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        textInputEditTextFrom.setOnClickListener(this);
        textInputEditTextTo.setOnClickListener(this);
        instituteList = new ArrayList<>();
        fromList = new ArrayList<>();
        toList = new ArrayList<>();
        marksList=new ArrayList<>();
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        check = sharedPreferences.getBoolean("third", false);
        if (check) {
            textInputEditTextEdu.setText(sharedPreferences.getString("institute", ""));
            textInputEditTextFrom.setText(sharedPreferences.getString("from", ""));
            textInputEditTextTo.setText(sharedPreferences.getString("to", ""));
            textInputEditTextMarks.setText(sharedPreferences.getString("marks",""));
            editor.putBoolean("third", false);
            editor.apply();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromDate:
                selectFromDate();
                break;
            case R.id.toDate:
                selectToDate();
                break;
            case R.id.eduBtn:
                prevFragment();
                break;
            case R.id.nextBtn:
                nextFragment();
                break;
            case R.id.addBtn:
                if (textInputEditTextTo.getText().toString().isEmpty()
                        || textInputEditTextFrom.getText().toString().isEmpty()
                        || textInputEditTextTo.getText().toString().isEmpty() || textInputEditTextMarks.toString().isEmpty()) {
                    Toast.makeText(getActivity(), "please enter all fields", Toast.LENGTH_SHORT).show();

                } else {
                    addEducation();
                    if(instituteList.size()>=3 && fromList.size()>=3 && toList.size()>=3 ){
                        btnNext.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
        }
    }

    private void addEducation() {
        institute = Objects.requireNonNull(textInputEditTextEdu.getText()).toString().trim();
        marks= Objects.requireNonNull(textInputEditTextMarks.getText()).toString().trim();
        from = fromDate;
        to = toDate;
        instituteList.add(institute);
        fromList.add(fromDate);
        toList.add(toDate);
        marksList.add(marks);
        if (instituteList.size() == 1 && fromList.size() == 1 && toList.size() == 1 && marksList.size()==1) {
            textInputLayout.setHint("College");
            textInputEditTextEdu.getText().clear();
            textInputEditTextFrom.getText().clear();
            textInputEditTextTo.getText().clear();
            textInputEditTextMarks.getText().clear();
        }else {
            marksLayout.setHelperTextEnabled(true);
            marksLayout.setHelperText("e.g  3.4/4 CGPA");
            marksLayout.setHelperTextColor(ColorStateList.valueOf(Color.GRAY));
            textInputLayout.setHint("University");
            marksLayout.setHint("CGPA");
            textInputEditTextEdu.getText().clear();
            textInputEditTextFrom.getText().clear();
            textInputEditTextTo.getText().clear();
            textInputEditTextMarks.getText().clear();
        }

    }

    private void nextFragment() {

        if (instituteList.size() == 0 || fromList.size() == 0 || toList.size() == 0) {
            Toast.makeText(getActivity(), "Complete Education required", Toast.LENGTH_SHORT).show();

        } else {
            StringBuilder allInstitute = new StringBuilder();
            StringBuilder allFrom = new StringBuilder();
            StringBuilder allTo = new StringBuilder();
            StringBuilder allMarks=new StringBuilder();
            for (String i : instituteList) {
                allInstitute.append(i).append(",");
            }
            for (String df : fromList) {
                allFrom.append(df).append(",");
            }
            for (String dt : toList) {
                allTo.append(dt).append(",");
            }
            for (String dt : marksList) {
                allMarks.append(dt).append(",");
            }
            PersonalInformation.cvModel.setInstitute(allInstitute.toString());
            PersonalInformation.cvModel.setFrom(allFrom.toString());
            PersonalInformation.cvModel.setTo(allTo.toString());
            PersonalInformation.cvModel.setMarks(allMarks.toString());
            editor.putString("institute", institute);
            editor.putString("from", fromDate);
            editor.putString("to", toDate);
            editor.putString("marks",marks);
            editor.putBoolean("third", true);
            editor.apply();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, new Experienced());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new About());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Educations");
    }
    private void selectFromDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        textInputEditTextFrom.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        fromDate = dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year;

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
    private void selectToDate(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        textInputEditTextTo.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                        toDate = dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year;

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
}
