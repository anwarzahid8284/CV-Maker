package com.example.cvmaker.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.DBClient.DatabaseClient;
import com.example.cvmaker.Model.CVModel;
import com.example.cvmaker.R;

import java.util.ArrayList;
import java.util.List;

public class Languages extends Fragment implements View.OnClickListener {
    Button btnSave, btnViewCV, btnAdd;
    EditText editTextLang;
    Fragment fragment;
    String language;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static boolean check = false;
    ArrayList<String> languageList;
    //AppendData appendData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.language, container, false);
        btnSave = view.findViewById(R.id.saveBtn);
        btnViewCV = view.findViewById(R.id.viewCVBtn);
        btnAdd = view.findViewById(R.id.addBtn);
        editTextLang = view.findViewById(R.id.languageEditID);
        languageList = new ArrayList<>();
        btnAdd.setOnClickListener(this);
        btnViewCV.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        sharedPreferences = getActivity().getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        check = sharedPreferences.getBoolean("eighth", false);
        if (check) {
            editTextLang.setText(sharedPreferences.getString("language", ""));
            editor.putBoolean("eighth", false);
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
            case R.id.viewCVBtn:
                viewCV();
                break;
            case R.id.addBtn:
                if (editTextLang.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "enter field", Toast.LENGTH_SHORT).show();

                } else {
                    addLanguage();
                    btnViewCV.setVisibility(View.VISIBLE);
                }
                break;
            default:
        }

    }

    private void addLanguage() {
        language = editTextLang.getText().toString().trim();
        languageList.add(language);
        editTextLang.getText().clear();

    }

    private void prevFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.userDetail_frag_container, new Achievements());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void viewCV() {
        fragment = null;
        switch (TemplateFragment.x) {
            case 0:
                fragment = new FirstTemplate();
                break;
            case 1:
                fragment = new SecondTemplate();
                break;
            case 2:
                fragment = new ThirdTemplate();
                break;
            case 3:
                fragment = new FourthTemplate();
                break;
            case 4:
                fragment = new FifthTemplate();
                break;
            case 5:
                fragment = new SixthTemplate();
                break;
            case 6:
                fragment = new SeventhTemplate();
                break;
            case 7:
                fragment = new EighthTemplate();
                break;
            case 8:
                fragment = new NinthTemplate();
                break;
            case 9:
                fragment = new TenthTemplate();
                break;
            case 10:
                fragment = new EleventhTemplate();
                break;
            case 11:
                fragment = new TwelfthTemplate();
                break;
            default:


        }
        if (fragment != null && languageList.size() != 0) {
            StringBuilder langBuilder = new StringBuilder();
            for (String sk : languageList) {
                langBuilder.append(sk).append(",");

            }
            PersonalInformation.cvModel.setLanguage(langBuilder.toString());
            check = true;
            editor.putString("language", language);
            editor.putBoolean("eighth", true);
            editor.apply();
            UserDetailActivity.textViewSaveCv.setVisibility(View.VISIBLE);
            UserDetailActivity.imageViewSave.setVisibility(View.VISIBLE);
            UserDetailActivity.backArrow.setVisibility(View.VISIBLE);
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.userDetail_frag_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            GetTasks gt = new GetTasks();
            gt.execute();
            /*appendData = new AppendData();
            appendData.dataAppend(getActivity());*/
        } else {
            Toast.makeText(getActivity(), "enter field", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class GetTasks extends AsyncTask<Void, Void, List<CVModel>> {
        @Override
        protected List<CVModel> doInBackground(Void... voids) {
            // insert value to database
            DatabaseClient.getInstance(getActivity()).getAppDatabase().cvDao().insert(PersonalInformation.cvModel);
            return null;
        }

        @Override
        protected void onPostExecute(List<CVModel> tasks) {
            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set title bar
        ((UserDetailActivity) getActivity())
                .setActionBarTitle("Language");
    }

}
