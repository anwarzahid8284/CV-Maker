package com.example.cvmaker.Adopter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cvmaker.Activity.OpenCvActivity;
import com.example.cvmaker.Activity.TemplatesActivity;
import com.example.cvmaker.Fragments.Languages;
import com.example.cvmaker.Model.CVListModel;
import com.example.cvmaker.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CVListAdapter extends RecyclerView.Adapter<CVListAdapter.CVHolder> {
    List<CVListModel> listModels;
    LayoutInflater layoutInflater;
    Context context;

    public CVListAdapter(Context context, List<CVListModel> listModels) {
        this.listModels = listModels;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cv_list_item, parent, false);

        return new CVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CVHolder holder, final int position) {
        final CVListModel cvListModel = listModels.get(position);
        holder.textViewRName.setText(cvListModel.getPdfName());
        holder.resumeSize.setText("Size: " + cvListModel.getPdfSize() + " KB");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToOpenCvActivity = new Intent(context, OpenCvActivity.class);
                intentToOpenCvActivity.putExtra("fileName", cvListModel.getPdfName());
                context.startActivity(intentToOpenCvActivity);
            }
        });
        holder.dotedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item1:
                                renameCV(cvListModel.getPdfName(), position);
                                break;
                            case R.id.item2:
                                deleteCV(context,position);
                                break;
                            case R.id.item3:
                                cvProperties(cvListModel.getPdfName(), position);
                                break;
                            case R.id.item4:
                                shareCV(cvListModel.getPdfName());
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listModels.size();
    }

    private void shareCV(String fileName) {
        final File root = new File(Environment.getExternalStorageDirectory(), "Resume/");
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        File filePath = new File(root, fileName);
        Uri screenshotUri = Uri.parse(String.valueOf(filePath));
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        context.startActivity(Intent.createChooser(sharingIntent, ""));
    }

    private void cvProperties(String fileName, int position) {
        final File root = new File(Environment.getExternalStorageDirectory(), "Resume/");
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        String alert1 = "File Name: " + fileName;
        String alert2 = "File Size: " + listModels.get(position).getPdfSize()+" KB";
        String alert3 = "File Path: " + root;
        alertDialog.setMessage(alert1 + "\n" + alert2 + "\n" + alert3);
        alertDialog.show();
    }

    private void deleteCV(Context context,int position) {
        final File root = new File(Environment.getExternalStorageDirectory(), "Resume/");
        File file = new File(root, listModels.get(position).getPdfName());
        if (file.exists()) {
            file.delete();
            listModels.remove(position);
            notifyDataSetChanged();
            if(listModels.size()<1){
                Languages.check=false;
                SharedPreferences sharedPreferences = context.getSharedPreferences("CV_LIST_COUNT", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("cvList", false);
                editor.apply();
                Intent intentToTemplatesAc = new Intent(context, TemplatesActivity.class);
                context.startActivity(intentToTemplatesAc);
                ((Activity)context).finish();
            }
        }

    }

    private void renameCV(final String file, final int position) {
        final File root = new File(Environment.getExternalStorageDirectory(), "Resume/");
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Rename File");
        final EditText editText = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        alertDialog.setView(editText);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String reName = editText.getText().toString().trim();
                if (!reName.isEmpty()) {
                    File oldName = new File(root, file);
                    File newName = new File(root, reName + ".pdf");
                    if (oldName.exists()) {
                        oldName.renameTo(newName);
                        listModels.get(position).setPdfName(newName.getName());
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                    }

                } else {
                    Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static class CVHolder extends RecyclerView.ViewHolder {
        ImageView pdfImageView, dotedImageView;
        TextView textViewRName, resumeSize;

        public CVHolder(@NonNull View itemView) {
            super(itemView);
            pdfImageView = itemView.findViewById(R.id.pdfViewId);
            dotedImageView = itemView.findViewById(R.id.dotedImageViewId);
            textViewRName = itemView.findViewById(R.id.resumeNameId);
            resumeSize = itemView.findViewById(R.id.resumeSizeId);


        }


    }

}
