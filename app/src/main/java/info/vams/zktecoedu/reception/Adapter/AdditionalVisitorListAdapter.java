package info.vams.zktecoedu.reception.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Activity.AdditionalVisitorActivity;
import info.vams.zktecoedu.reception.Activity.VisitorEntryActivityOne;
import info.vams.zktecoedu.reception.Activity.AdditionalVisitorList;
import info.vams.zktecoedu.reception.Model.ImageUploadObject;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;

/**
 * Created by RahulK on 7/17/2018.
 */

public class AdditionalVisitorListAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<VisitorList> visitorListArrayList;

    public AdditionalVisitorListAdapter(Context context, ArrayList<VisitorList> visitorListArrayList) {
        this.context = context;
        this.visitorListArrayList = visitorListArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_visitor_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {
        MyViewHolder holder = (MyViewHolder) mainHolder;
        VisitorList visitorList = visitorListArrayList.get(position);

        if (position == 0) {
            holder.tvName.setVisibility(View.GONE);
            holder.tvMobile.setVisibility(View.GONE);
            holder.tvEmail.setVisibility(View.GONE);
            holder.ivEdit.setVisibility(View.GONE);
            holder.ivDelete.setVisibility(View.GONE);
            holder.profile.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
        } else {
            if (visitorList.getLastName() != null && !visitorList.getLastName().isEmpty()) {
                holder.tvName.setText(visitorList.getFirstName()+ " " + visitorList.getLastName());
            }else{
                holder.tvName.setText(visitorList.getFirstName());
            }

            if(CommonPlaceForOjects.settings != null &&
                CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")){
                if (visitorList.getMobile() != null && !visitorList.getMobile().isEmpty()) {
                    holder.tvMobile.setVisibility(View.VISIBLE);
                    holder.tvMobile.setText("+" + visitorList.getIsdCode() + visitorList.getMobile());
                }
            }else{
                if (visitorList.getEmail() != null && !visitorList.getEmail().isEmpty()) {
                    holder.tvEmail.setVisibility(View.VISIBLE);
                    holder.tvEmail.setText(visitorList.getEmail());
                }
            }


            holder.ivDelete.setVisibility(View.VISIBLE);
        }

        if (VisitorEntryActivityOne.imageUploadObjects != null) {

            for (ImageUploadObject imageUploadObject :
                    VisitorEntryActivityOne.imageUploadObjects) {

                if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                        equalsIgnoreCase("E")) {
                    //  if (imageUploadObject.getTimeStamp().trim().equalsIgnoreCase(AdditionalVisitorActivity.currentTime.toString().trim())) {

                    if (imageUploadObject.getEmailId().trim().equals(visitorList.getEmail().trim())) {

                        if (imageUploadObject.getvName().trim().equalsIgnoreCase(holder.tvName.getText().toString())) {

                            try {
                                if (imageUploadObject.getUrl() != null && !imageUploadObject.getUrl().isEmpty()) {
                                    if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVisitorCapturePhotoRequired()) {
                                        uplaodNewImagePicasso(holder, imageUploadObject);
                                    } else {
                                        if (imageUploadObject.getByPassVisitorId() > 0 || imageUploadObject.getRegisteredVisitorId() > 0) {
                                            if (imageUploadObject.isCapture()) {
                                                uplaodNewImagePicasso(holder, imageUploadObject);
                                            } else {
                                                uploadOldImagePicasso(holder, imageUploadObject);
                                            }
                                        } else {
                                            uplaodNewImagePicasso(holder, imageUploadObject);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //}
                    }
                } else {
                    //if (imageUploadObject.getTimeStamp().trim().equalsIgnoreCase(AdditionalVisitorActivity.currentTime.toString().trim())) {
                    if (imageUploadObject.getMobileNo().trim().equals(visitorList.getMobile().trim())) {

                        if (imageUploadObject.getvName().trim().equalsIgnoreCase(holder.tvName.getText().toString())) {
                            try {
                                if (imageUploadObject.getUrl() != null && !imageUploadObject.getUrl().isEmpty()) {
                                    if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVisitorCapturePhotoRequired()) {

                                        uplaodNewImagePicasso(holder, imageUploadObject);
                                    } else {
                                        if (imageUploadObject.getByPassVisitorId() > 0 || imageUploadObject.getRegisteredVisitorId() > 0) {
                                            if (imageUploadObject.isCapture()) {
                                                uplaodNewImagePicasso(holder, imageUploadObject);
                                            } else {
                                                uploadOldImagePicasso(holder, imageUploadObject);
                                            }
                                        } else {
                                            uplaodNewImagePicasso(holder, imageUploadObject);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            //  }

                        }
                    }
                }

            }
        }

    }

    private void uploadOldImagePicasso(MyViewHolder holder, ImageUploadObject imageUploadObject) {
        PicassoTrustAllCerificate.getInstance(context).load(imageUploadObject.getUrl()).
                error(context.getResources().getDrawable(R.drawable.profile)).
                placeholder(context.getResources().getDrawable(R.drawable.loading)).
                into(holder.profile);
    }

    private void uplaodNewImagePicasso(MyViewHolder holder, ImageUploadObject imageUploadObject) {
        PicassoTrustAllCerificate.getInstance(context).load(new File(imageUploadObject.getUrl())).
                error(context.getResources().getDrawable(R.drawable.profile)).
                placeholder(context.getResources().getDrawable(R.drawable.loading)).
                into(holder.profile);


    }

    @Override
    public int getItemCount() {
        return visitorListArrayList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profile;
        TextView tvName, tvMobile, tvEmail;
        ImageView ivEdit, ivDelete;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            profile = (CircleImageView) itemView.findViewById(R.id.cv_custom_visitor_profile);
            tvName = (TextView) itemView.findViewById(R.id.tv_custom_additionalVisitorName);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_custom_additionalVisitorNumber);
            tvEmail = (TextView) itemView.findViewById(R.id.tv_custom_additionalVisitorEmail);
            ivEdit = (ImageView) itemView.findViewById(R.id.iv_custom_visitor_edit);
            ivDelete = (ImageView) itemView.findViewById(R.id.iv_custom_visitor_delete);
            view = (View) itemView.findViewById(R.id.viewAdditionalList);
            ivDelete.setOnClickListener(this);
//          ivEdit.setOnClickListener(this);
            ivEdit.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_custom_visitor_edit:
                    Intent intent = new Intent(context, AdditionalVisitorActivity.class);
                    intent.putExtra("visitor", visitorListArrayList.get(getAdapterPosition()));
                    intent.putExtra("visitorId", getAdapterPosition());
                    context.startActivity(intent);
                    break;

                case R.id.iv_custom_visitor_delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Visitor");
                    builder.setMessage("Sure you want to delete this visitor?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AdditionalVisitorList.deleteVisitor(getAdapterPosition());
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                    break;
            }

        }
    }

}
