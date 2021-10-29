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
import info.vams.zktecoedu.reception.Interface.OnPrint;
import info.vams.zktecoedu.reception.Model.VisitorPassQueueResponse;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;

/**
 * Created by vishal on 10-Apr-19.
 */

public class PassQueueListAdapter extends RecyclerView.Adapter {

    Context context;
    public ArrayList<VisitorPassQueueResponse> logoutListData;
    OnPrint onPrint;
    public boolean isPrintPass;

    public PassQueueListAdapter(Context context, ArrayList<VisitorPassQueueResponse> logoutListData, OnPrint onPrint, boolean isPrintPass) {
        this.context = context;
        this.logoutListData = logoutListData;
        this.onPrint = onPrint;
        this.isPrintPass = isPrintPass;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_passqueue_row, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {

        MyHolder holder = (MyHolder) mainHolder;
        VisitorPassQueueResponse logoutListData = this.logoutListData.get(position);

        if (logoutListData != null) {


            if (logoutListData.getName() != null && !logoutListData.getName().isEmpty()) {
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText(logoutListData.getName());
            } else {
                holder.tvName.setVisibility(View.GONE);
            }

            if (logoutListData.getEmail() != null && !logoutListData.getEmail().isEmpty()) {
                holder.tvEmail.setVisibility(View.VISIBLE);
                holder.tvEmail.setText(logoutListData.getEmail());
            } else {
                holder.tvEmail.setVisibility(View.GONE);
            }

            if (logoutListData.getMobileNo() != null && !logoutListData.getMobileNo().isEmpty()) {
                holder.tvNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setText(logoutListData.getMobileNo());
            } else {
                holder.tvNumber.setVisibility(View.GONE);
            }

            if (logoutListData.getCompany() != null && !logoutListData.getCompany().isEmpty()) {
                holder.tvCompany.setVisibility(View.VISIBLE);
                holder.tvCompany.setText(logoutListData.getCompany());
            } else {
                holder.tvCompany.setVisibility(View.GONE);
            }


            if (logoutListData.getPersonToMeet() != null && !logoutListData.getPersonToMeet().isEmpty()) {
                holder.tvPersonToMeet.setVisibility(View.VISIBLE);
                //holder.tvPersonToMeet.setText("");
                /*for (String personToMeet:
                        logoutListData.getPersonToMeet()) {
                    holder.tvPersonToMeet.setText(holder.tvPersonToMeet.getText()+personToMeet);
                }*/
                holder.tvPersonToMeet.setText(logoutListData.getPersonToMeet().toString());
            } else {
                holder.tvPersonToMeet.setVisibility(View.GONE);
            }

            if (logoutListData.getStatus() != null && !logoutListData.getStatus().isEmpty()) {
                if (logoutListData.getStatus().equalsIgnoreCase("APPROVED")) {
                    holder.tvGuestStatus.setVisibility(View.GONE);
                    holder.tvGuestStatus.setText("");
                    holder.btnPrintPass.setVisibility(View.VISIBLE);
                } else {
                    holder.tvGuestStatus.setVisibility(View.VISIBLE);
                    holder.tvGuestStatus.setText(logoutListData.getStatus());
                    holder.btnPrintPass.setVisibility(View.GONE);
                }
            }

            /*if(isPrintPass){
                if(logoutListData.getCompany() != null && !logoutListData.getCompany().isEmpty()) {
                    holder.tvCompany.setVisibility(View.VISIBLE);
                    holder.tvCompany.setText(logoutListData.getCompany());
                }

                if(logoutListData.getAccessCardNo() != null && !logoutListData.getAccessCardNo().isEmpty()) {
                    holder.tvAccess.setVisibility(View.VISIBLE);
                    holder.tvAccess.setText(logoutListData.getAccessCardNo());
                }
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

            /*if (logoutListData.getBuildingInTimeUtc() != null){
                if (logoutListData.getBuildingOutTimeUtc() != null){
                    holder.btnLogin.setEnabled(true);
                    holder.btnLogout.setEnabled(false);
                }else {
                    holder.btnLogin.setEnabled(false);
                    holder.btnLogout.setEnabled(true);
                }

            }else {
                holder.btnLogin.setEnabled(true);
                holder.btnLogout.setEnabled(false);
            }*/


        }
    }

    @Override
    public int getItemCount() {
        return logoutListData.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvNumber, tvEmail, tvLoginTime, tvTenantName, tvCompany, tvAccess, tvPersonToMeet, tvPassNo, tvGuestStatus;
        Button btnPrintPass, btnLogout;
        CircleImageView cvImage;
        //FloatingActionButton iv_VideoCalling;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvGuestName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvGuestEmail);
            tvNumber = (TextView) itemView.findViewById(R.id.tvGuestNumber);
            tvCompany = (TextView) itemView.findViewById(R.id.tvGuestCompany);
            tvAccess = (TextView) itemView.findViewById(R.id.tvGuestAccess);
            //tvLoginTime= (TextView)itemView.findViewById(R.id.tvLogoutLoginTime);
            //tvTenantName= (TextView)itemView.findViewById(R.id.tvLogoutTenantName);
            tvPersonToMeet = (TextView) itemView.findViewById(R.id.tvLogoutPersonToMeet);
            tvGuestStatus = (TextView) itemView.findViewById(R.id.tvGuestStatus);
            //tvPassNo= (TextView)itemView.findViewById(R.id.tvLogoutPassNo);
            //btnLogout = (Button)itemView.findViewById(R.id.btnLogoutButton);
            btnPrintPass = (Button) itemView.findViewById(R.id.btnGuestPrintPass);
            cvImage = (CircleImageView) itemView.findViewById(R.id.cvProfileLogout);

            //iv_VideoCalling = (FloatingActionButton) itemView.findViewById(R.id.iv_VideoCallings);
            btnPrintPass.setOnClickListener(this);

            if (CommonPlaceForOjects.settings.getPrintPass()) {
                btnPrintPass.setVisibility(View.VISIBLE);
                btnPrintPass.setText("Login");
            } else {
                btnPrintPass.setVisibility(View.VISIBLE);
                btnPrintPass.setText("Login");
            }


            //btnLogout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnGuestPrintPass:
                    VisitorPassQueueResponse temp = logoutListData.get(getAdapterPosition());
                    onPrint.onPrintPass(temp.getVisitorLogId(), temp.getVisitorId());
                    //btnLogout.setEnabled(false);
                    break;

            }
        }
    }

    public void filteredList(ArrayList<VisitorPassQueueResponse> filterData) {
        this.logoutListData = filterData;
        notifyDataSetChanged();
    }
}
