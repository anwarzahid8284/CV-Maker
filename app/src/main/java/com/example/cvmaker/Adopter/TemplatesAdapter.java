package com.example.cvmaker.Adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvmaker.Model.ImageModel;
import com.example.cvmaker.R;

import java.util.List;

public class TemplatesAdapter extends RecyclerView.Adapter<TemplatesAdapter.FragmentHolder> {
    Context context;
    LayoutInflater layoutInflater;
    List<ImageModel> imageModels;

    public TemplatesAdapter(Context context, List<ImageModel> imageModels) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.imageModels = imageModels;
    }

    @NonNull
    @Override
    public FragmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.grid_view_item, parent, false);
        return new FragmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentHolder holder, int position) {
        final  ImageModel imageModel=imageModels.get(position);
        holder.imageView.setBackgroundResource(imageModel.getImageDrawable());
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    public static class FragmentHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public FragmentHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
