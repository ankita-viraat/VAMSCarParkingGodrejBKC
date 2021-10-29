package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Interface.OnLoginOrLogout;
import info.vams.zktecoedu.reception.Model.BuildingLevelLogoutListResponse;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;

/**
 * Created by vishal on 09-Apr-19.
 */

public class BuildingLevelLogoutListAdapter extends RecyclerView.Adapter {

    Context context;
    public ArrayList<BuildingLevelLogoutListResponse> logoutListData;
    OnLoginOrLogout onLoginOrLogout;

    public BuildingLevelLogoutListAdapter(Context context, ArrayList<BuildingLevelLogoutListResponse> logoutListData, OnLoginOrLogout onLoginOrLogout) {
        this.context = context;
        this.logoutListData = logoutListData;
        this.onLoginOrLogout = onLoginOrLogout;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_building_level_logout_row, parent, false);
        BuildingLevelLogoutListAdapter.MyHolder myHolder = new BuildingLevelLogoutListAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {

        BuildingLevelLogoutListAdapter.MyHolder holder = (BuildingLevelLogoutListAdapter.MyHolder) mainHolder;
        BuildingLevelLogoutListResponse logoutListData = this.logoutListData.get(position);

        if (logoutListData != null) {


            if (logoutListData.getName() != null && !logoutListData.getName().isEmpty()) {
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText(logoutListData.getName());
            } else {
                holder.tvName.setVisibility(View.GONE);
            }

            if (logoutListData.getEmailPrimary() != null && !logoutListData.getEmailPrimary().isEmpty()) {
                holder.tvEmail.setVisibility(View.VISIBLE);
                holder.tvEmail.setText(logoutListData.getEmailPrimary());
            } else {
                holder.tvEmail.setVisibility(View.GONE);
            }

            if (logoutListData.getMobilePrimary() != null && !logoutListData.getMobilePrimary().isEmpty()) {
                holder.tvNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setText(logoutListData.getMobilePrimary());
            } else {
                holder.tvNumber.setVisibility(View.GONE);
            }

            if (logoutListData.getVisitorLogPersonToVisit().get(0).getName() != null && !logoutListData.getVisitorLogPersonToVisit().get(0).getName().isEmpty()) {
                holder.tvPersonToMeet.setVisibility(View.VISIBLE);
                /*holder.tvPersonToMeet.setText("");
                for (String personToMeet:
                     logoutListData.getPersonToMeet()) {
                    holder.tvPersonToMeet.setText(holder.tvPersonToMeet.getText()+personToMeet);
                }*/
                holder.tvPersonToMeet.setText(logoutListData.getVisitorLogPersonToVisit().get(0).getName());
            } else {
                holder.tvPersonToMeet.setVisibility(View.GONE);
            }

            /*if (logoutListData.getPassId() != null && !logoutListData.getPassId().isEmpty()){
                holder.tvPassNo.setVisibility(View.VISIBLE);
                holder.tvPassNo.setText(logoutListData.getPassId());
            }else {
                holder.tvPassNo.setVisibility(View.GONE);
            }*/

            PicassoTrustAllCerificate.getInstance(context).load(logoutListData.getImageUrl()).
                    error(context.getResources().getDrawable(R.drawable.profile)).
                    placeholder(context.getResources().getDrawable(R.drawable.loading)).
                    into(holder.cvImage);

            /*Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
            if (profile != null && profile.isVideoCallingAllowed()) {
                holder.iv_VideoCalling.setVisibility(View.VISIBLE);
                holder.iv_VideoCalling.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SPbean(context).setPreference(Constants.CALLER_NAME,logoutListData.getVisitorLogPersonToVisit().get(0).getName());

                        onVideoCall.onVideo(String.valueOf(logoutListData.getVisitorLogPersonToVisit().get(0).getPersonToVisitId()),
                                logoutListData.getVisitorLogPersonToVisit().get(0).getName());
                        *//*if (visitorEntryActivityOne != null) {
                            Log.e("TAG", " Person to meet id = " + employeeLists.get(getAdapterPosition()).getPersonToVisitId());
                            onVideoCall.onVideo(String.valueOf(employeeLists.get(getAdapterPosition()).getPersonToVisitId()),
                                    employeeLists.get(getAdapterPosition()).getName());
                        } else {
                            Log.e("TAG", " Person to meet id = " + employeeLists.get(getAdapterPosition()).getPersonToVisitId());
                            onVideoCall.onVideo(String.valueOf(employeeLists.get(getAdapterPosition()).getPersonToVisitId()),
                                    employeeLists.get(getAdapterPosition()).getName());
                        }*//*
                    }
                });
            }*/

            if (logoutListData.getBuildingInTimeUtc() != null && !logoutListData.getBuildingInTimeUtc().isEmpty() && !logoutListData.getBuildingInTimeUtc().equalsIgnoreCase("null")) {
                if (logoutListData.getBuildingOutTimeUtc() != null&& !logoutListData.getBuildingOutTimeUtc().isEmpty() && !logoutListData.getBuildingOutTimeUtc().equalsIgnoreCase("null")) {
                    holder.btnLogin.setEnabled(true);
                    holder.btnLogout.setEnabled(false);
                } else {
                    holder.btnLogin.setEnabled(false);
                    holder.btnLogout.setEnabled(true);
                }
            } else {
                holder.btnLogin.setEnabled(true);
                holder.btnLogout.setEnabled(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return logoutListData.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvNumber, tvEmail, tvLoginTime, tvTenantName, tvPersonToMeet, tvPassNo;
        Button btnLogin, btnLogout;
        CircleImageView cvImage;
        //FloatingActionButton iv_VideoCalling;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvLogoutName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvLogoutEmail);
            tvNumber = (TextView) itemView.findViewById(R.id.tvLogoutNumber);
            //tvLoginTime= (TextView)itemView.findViewById(R.id.tvLogoutLoginTime);
            //tvTenantName= (TextView)itemView.findViewById(R.id.tvLogoutTenantName);
            tvPersonToMeet = (TextView) itemView.findViewById(R.id.tvLogoutPersonToMeet);
            //tvPassNo= (TextView)itemView.findViewById(R.id.tvLogoutPassNo);
            btnLogout = (Button) itemView.findViewById(R.id.btnLogoutButton);
            btnLogin = (Button) itemView.findViewById(R.id.btnLoginButton);
            cvImage = (CircleImageView) itemView.findViewById(R.id.cvProfileLogout);

            //iv_VideoCalling = (FloatingActionButton) itemView.findViewById(R.id.iv_VideoCallings);
            btnLogin.setOnClickListener(this);
            btnLogout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnLogoutButton:
                    BuildingLevelLogoutListResponse temp = logoutListData.get(getAdapterPosition());
                    onLoginOrLogout.onLoginLogout(temp, "Logout");
                   // btnLogout.setEnabled(false);
                    break;

                case R.id.btnLoginButton:
                    BuildingLevelLogoutListResponse temp1 = logoutListData.get(getAdapterPosition());
                    onLoginOrLogout.onLoginLogout(temp1, "Login");
                    //btnLogin.setEnabled(false);
                    break;
            }
        }
    }

    public void filteredList(ArrayList<BuildingLevelLogoutListResponse> filterData) {
        this.logoutListData = filterData;
        notifyDataSetChanged();
    }
}
