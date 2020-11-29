package com.example.cvmaker.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cvmaker.Activity.TemplatesActivity;
import com.example.cvmaker.Activity.UserDetailActivity;
import com.example.cvmaker.Adopter.ViewPageAdopter;
import com.example.cvmaker.R;

import java.util.ArrayList;
import java.util.List;

public class TemplateFragment extends Fragment implements View.OnClickListener {
    ImageView imageViewForward,imageViewBackward;
    List<Fragment> fragmentList;
    ViewPager viewPager;
    ViewPageAdopter viewPageAdopter;
    public static Fragment fragment = null;
    Button btnCreate;
    int page = 0;
    public static int x = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.templates, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager_id);
        imageViewForward=(ImageView)view.findViewById(R.id.forward_arrow);
        imageViewBackward=(ImageView) view.findViewById(R.id.back_arrow);
        btnCreate=(Button)view.findViewById(R.id.create_cv_btn);
        btnCreate.setOnClickListener(this);
        imageViewBackward.setOnClickListener(this);
        imageViewForward.setOnClickListener(this);
        fragmentList = new ArrayList<>();
        fragmentsViewPager();
        viewPage();
        return view;
    }

    private void viewPage() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                x = page;
                fragment = fragmentList.get(viewPager.getCurrentItem());


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void fragmentsViewPager() {
        fragmentList.add(new FirstTemplate());
        fragmentList.add(new SecondTemplate());
        fragmentList.add(new ThirdTemplate());
        fragmentList.add(new FourthTemplate());
        fragmentList.add(new FifthTemplate());
        fragmentList.add(new SixthTemplate());
        fragmentList.add(new SeventhTemplate());
        fragmentList.add(new EighthTemplate());
        fragmentList.add(new NinthTemplate());
        fragmentList.add(new TenthTemplate());
        fragmentList.add(new EleventhTemplate());
        fragmentList.add(new TwelfthTemplate());
        viewPageAdopter = new ViewPageAdopter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPageAdopter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_arrow:
                previousTemplate();
                break;
            case R.id.forward_arrow:
                nextTemplate();
                break;
            case R.id.create_cv_btn:
                createCV();
                break;
            default:
        }
    }
    private void createCV() {
        Intent intentToUserD = new Intent(getActivity(), UserDetailActivity.class);
        startActivity(intentToUserD);
        ((TemplatesActivity) getActivity()).finish();

    }
    private void previousTemplate() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);


    }

    private void nextTemplate() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
    }
}
