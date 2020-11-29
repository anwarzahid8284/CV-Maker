package com.example.cvmaker.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridView;

import com.example.cvmaker.Adopter.TemplatesAdapter;
import com.example.cvmaker.Fragments.EighthTemplate;
import com.example.cvmaker.Fragments.EleventhTemplate;
import com.example.cvmaker.Fragments.FifthTemplate;
import com.example.cvmaker.Fragments.FirstTemplate;
import com.example.cvmaker.Fragments.FourthTemplate;
import com.example.cvmaker.Fragments.NinthTemplate;
import com.example.cvmaker.Fragments.SecondTemplate;
import com.example.cvmaker.Fragments.SeventhTemplate;
import com.example.cvmaker.Fragments.SixthTemplate;
import com.example.cvmaker.Fragments.TenthTemplate;
import com.example.cvmaker.Fragments.ThirdTemplate;
import com.example.cvmaker.Fragments.TwelfthTemplate;
import com.example.cvmaker.Model.ImageModel;
import com.example.cvmaker.R;

import java.util.ArrayList;
import java.util.List;

public class AllTemplatesActivity extends AppCompatActivity {
    TemplatesAdapter templatesAdapter;
    RecyclerView recyclerView;
    List<ImageModel> imageModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_templates);
        imageModels=new ArrayList<>();
        int[] drawable = {R.drawable.first, R.drawable.second, R.drawable.third, R.drawable.fourth
                , R.drawable.fifth, R.drawable.sixth, R.drawable.seventh, R.drawable.eighth, R.drawable.ninth
                , R.drawable.tenth, R.drawable.eleventh,R.drawable.twelfth};
        for(int i=0; i<drawable.length;i++){
            imageModels.add(new ImageModel(drawable[i]));
        }
        AllTemplatesActivity.this.setTitle("Templates");
        recyclerView = findViewById(R.id.recycler_gridView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        templatesAdapter = new TemplatesAdapter(AllTemplatesActivity.this, imageModels);
        recyclerView.setAdapter(templatesAdapter);
    }
}