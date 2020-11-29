package com.example.cvmaker.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.DBClient.DatabaseClient;
import com.example.cvmaker.Model.CVModel;
import com.example.cvmaker.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class TwelfthTemplate extends Fragment {
    ImageView imageView;
    TextView nameText, professionText, yourselfText, expTitleText, expDurText,
            expDesText, projectNameText, projectDesText, projectToolText,
             phoneText, emailText, addressText;
    TextView firstEdu, firstFrom, firstTo, secondEdu, secondFrom, secondTo, thirdEdu, thirdFrom, thirdTo,firstMarks,secondMarks,thirdMarks;

    ConstraintLayout outerLay;
    TextView firstAch,secondAch;
    TextView firtLang,secondLang,thirdLang;
    TextView firstSkill,secondSkill,thirdSkill,fourthSkill,fifthSkill;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.twelfth_template, container, false);
        outerLay = view.findViewById(R.id.outerLay);
        imageView = view.findViewById(R.id.profileImage);
        nameText = view.findViewById(R.id.userNameId);
        professionText = view.findViewById(R.id.professionId);
        yourselfText = view.findViewById(R.id.aboutYourSelf);
        expTitleText = view.findViewById(R.id.experiencedTitleId);
        expDurText = view.findViewById(R.id.experiencedDId);
        expDesText = view.findViewById(R.id.expDescriptionId);
        projectNameText = view.findViewById(R.id.projectTitleId);
        projectDesText = view.findViewById(R.id.projectDescriptionId);
        projectToolText = view.findViewById(R.id.projectToolsId);
        firstAch = view.findViewById(R.id.firstAchievement);
        secondAch = view.findViewById(R.id.secondAchievement);

        firstSkill = view.findViewById(R.id.firstSkill);
        secondSkill = view.findViewById(R.id.secondSkill);
        thirdSkill = view.findViewById(R.id.thirdSkill);
        fourthSkill = view.findViewById(R.id.fourthSkill);
        fifthSkill = view.findViewById(R.id.fifthSkill);


        firtLang = view.findViewById(R.id.firstLanguage);
        secondLang = view.findViewById(R.id.secondLanguage);
        thirdLang = view.findViewById(R.id.thirdLanguage);

        phoneText = view.findViewById(R.id.mobileNoId);
        emailText = view.findViewById(R.id.emailID);
        addressText = view.findViewById(R.id.addressId);

        firstEdu = view.findViewById(R.id.institutionId);
        firstFrom = view.findViewById(R.id.educationFromId);
        firstTo = view.findViewById(R.id.educationToId);
        firstMarks=view.findViewById(R.id.marks1);
        secondEdu = view.findViewById(R.id.institutionId1);
        secondFrom = view.findViewById(R.id.educationFromId1);
        secondTo = view.findViewById(R.id.educationToId1);
        secondMarks=view.findViewById(R.id.marks2);
        thirdEdu = view.findViewById(R.id.institutionId2);
        thirdFrom = view.findViewById(R.id.educationFromId2);
        thirdTo = view.findViewById(R.id.educationToId2);
        thirdMarks=view.findViewById(R.id.marks3);
        if(Languages.check){
            ((UserDetailActivity) getActivity())
                    .setActionBarTitle("CV Preview");
            offVisibility();
            setData();
        }

        return view;
    }
    private void offVisibility() {
        firstAch.setVisibility(View.GONE);
        secondAch.setVisibility(View.GONE);

        firtLang.setVisibility(View.GONE);
        secondLang.setVisibility(View.GONE);
        thirdLang.setVisibility(View.GONE);

        firstSkill.setVisibility(View.GONE);
        secondSkill.setVisibility(View.GONE);
        thirdSkill.setVisibility(View.GONE);
        fourthSkill.setVisibility(View.GONE);
        fifthSkill.setVisibility(View.GONE);

    }
    private void setData() {
        class GetTasks extends AsyncTask<Void, Void, List<CVModel>> {

            @Override
            protected List<CVModel> doInBackground(Void... voids) {
                List<CVModel> cvModels = DatabaseClient
                        .getInstance(getActivity())
                        .getAppDatabase()
                        .cvDao()
                        .getLastRow();
                return cvModels;
            }

            @Override
            protected void onPostExecute(List<CVModel> tasks) {
                super.onPostExecute(tasks);
                CVModel cvModel = tasks.get(0);
                File filePath = new File(cvModel.getPicture());
                if(filePath.length()!=0){
                    Glide.with(Objects.requireNonNull(getActivity()))
                            .load(new File(filePath.getPath()))
                            .into(imageView);
                }else {
                    imageView.setVisibility(View.GONE);
                }
                nameText.setText(cvModel.getName());
                professionText.setText(cvModel.getProfession());
                yourselfText.setText(cvModel.getYourself());
                expTitleText.setText(cvModel.getExperienced());
                projectNameText.setText(cvModel.getProjectName());
                projectDesText.setText(cvModel.getProjectDes());
                projectToolText.setText(cvModel.getProjectTools());
                phoneText.setText(cvModel.getMobileNo());
                emailText.setText(cvModel.getEmail());
                addressText.setText(cvModel.getAddress());
                // achievement list
                StringTokenizer achievementsToken = new StringTokenizer(cvModel.getAchievement(), ",");
                ArrayList<String> achievementsList = new ArrayList<>();
                while (achievementsToken.hasMoreTokens()) {
                    achievementsList.add(achievementsToken.nextToken());
                }
                for (int i = 0; i < achievementsList.size(); i++) {
                    TextView nextText = new TextView(getActivity());
                    nextText.setText(achievementsList.get(i));
                    nextText.setId(View.generateViewId());
                                            nextText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen._8ssp));

                    ConstraintLayout.LayoutParams lp =
                            new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    outerLay.addView(nextText, lp);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(outerLay);
                    int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            3, getResources().getDisplayMetrics());
                    if (i == 0) {
                        set.connect(nextText.getId(), ConstraintSet.TOP, R.id.lineId7, ConstraintSet.BOTTOM, topMargin);
                        set.connect(nextText.getId(), ConstraintSet.START, R.id.lineId7, ConstraintSet.START, 0);

                    } else {
                        set.connect(nextText.getId(), ConstraintSet.TOP, (nextText.getId()) - 1, ConstraintSet.BOTTOM, topMargin);
                        set.connect(nextText.getId(), ConstraintSet.START, (nextText.getId()) - 1, ConstraintSet.START, 0);

                    }
                    set.applyTo(outerLay);
                }

                // skill list
                StringTokenizer skillsToken = new StringTokenizer(cvModel.getSkill(), ",");
                ArrayList<String> skillList = new ArrayList<>();
                while (skillsToken.hasMoreTokens()) {
                    skillList.add(skillsToken.nextToken());
                }
                for (int i = 0; i < skillList.size(); i++) {
                    TextView nextText = new TextView(getActivity());
                    nextText.setText(skillList.get(i));
                    nextText.setId(View.generateViewId());
                                            nextText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen._8ssp));

                    ConstraintLayout.LayoutParams lp =
                            new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    outerLay.addView(nextText, lp);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(outerLay);
                    int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            3, getResources().getDisplayMetrics());
                    if (i == 0) {
                        set.connect(nextText.getId(), ConstraintSet.TOP, R.id.lineId5, ConstraintSet.BOTTOM, topMargin);
                        set.connect(nextText.getId(), ConstraintSet.START, R.id.lineId5, ConstraintSet.START, 0);

                    } else {
                        set.connect(nextText.getId(), ConstraintSet.TOP, (nextText.getId())-1, ConstraintSet.TOP, 0);
                        set.connect(nextText.getId(), ConstraintSet.BOTTOM, (nextText.getId())-1, ConstraintSet.BOTTOM, 0);
                        set.connect(nextText.getId(), ConstraintSet.START, (nextText.getId())-1, ConstraintSet.END, 7);

                    }
                    set.applyTo(outerLay);
                }


                // language list
                StringTokenizer languageToken = new StringTokenizer(cvModel.getLanguage(), ",");
                ArrayList<String> languageList = new ArrayList<>();
                while (languageToken.hasMoreTokens()) {
                    languageList.add(languageToken.nextToken());
                }
                for (int i = 0; i < languageList.size(); i++) {
                    TextView nextText = new TextView(getActivity());
                    nextText.setText(languageList.get(i));
                    nextText.setId(View.generateViewId());
                                            nextText.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen._8ssp));

                    ConstraintLayout.LayoutParams lp =
                            new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    outerLay.addView(nextText, lp);
                    ConstraintSet set = new ConstraintSet();
                    set.clone(outerLay);
                    int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            3, getResources().getDisplayMetrics());
                    if (i == 0) {
                        set.connect(nextText.getId(), ConstraintSet.TOP, R.id.lineId8, ConstraintSet.BOTTOM, topMargin);
                        set.connect(nextText.getId(), ConstraintSet.START, R.id.lineId8, ConstraintSet.START, 0);

                    } else {
                        set.connect(nextText.getId(), ConstraintSet.BOTTOM, (nextText.getId()) - 1, ConstraintSet.BOTTOM, 0);
                        set.connect(nextText.getId(), ConstraintSet.TOP, (nextText.getId()) - 1, ConstraintSet.TOP, 0);
                        set.connect(nextText.getId(), ConstraintSet.START, (nextText.getId()) - 1, ConstraintSet.END, 7);

                    }
                    set.applyTo(outerLay);
                }

                // institute list
                StringTokenizer instituteToken = new StringTokenizer(cvModel.getInstitute(), ",");
                ArrayList<String> instituteList = new ArrayList<>();
                while (instituteToken.hasMoreTokens()) {
                    instituteList.add(instituteToken.nextToken());
                }
                TextView eduText = null;
                for (int i = 0; i < instituteList.size(); i++) {
                    if(i==0){
                        firstEdu.setText(instituteList.get(i));

                    }else if(i==1){
                        secondEdu.setText(instituteList.get(i));

                    }else {
                        thirdEdu.setText(instituteList.get(i));

                    }
                }

                StringTokenizer fromToken = new StringTokenizer(cvModel.getFrom(), ",");
                ArrayList<String> fromList = new ArrayList<>();
                while (fromToken.hasMoreTokens()) {
                    fromList.add(fromToken.nextToken());
                }
                TextView fromText=null;
                for (int i = 0; i < fromList.size(); i++) {
                    if(i==0){
                        firstFrom.setText(fromList.get(i));

                    }else if(i==1){
                        secondFrom.setText(fromList.get(i));

                    }else {
                        thirdFrom.setText(fromList.get(i));

                    }
                }


                StringTokenizer toToken = new StringTokenizer(cvModel.getTo(), ",");
                ArrayList<String> toList = new ArrayList<>();
                while (toToken.hasMoreTokens()) {
                    toList.add(toToken.nextToken());
                }
                for (int i = 0; i < toList.size(); i++) {
                    if(i==0){
                        firstTo.setText(toList.get(i));

                    }else if(i==1){
                        secondTo.setText(toList.get(i));

                    }else {
                        thirdTo.setText(fromList.get(i));

                    }
                }
                StringTokenizer marksToken = new StringTokenizer(cvModel.getMarks(), ",");
                ArrayList<String> marksList = new ArrayList<>();
                while (marksToken.hasMoreTokens()) {
                    marksList.add(marksToken.nextToken());
                }
                for (int i = 0; i < marksList.size(); i++) {
                    if(i==0){
                        firstMarks.setText(marksList.get(i));

                    }else if(i==1){
                        secondMarks.setText(marksList.get(i));

                    }else {
                        thirdMarks.setText(marksList.get(i));

                    }
                }

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}
