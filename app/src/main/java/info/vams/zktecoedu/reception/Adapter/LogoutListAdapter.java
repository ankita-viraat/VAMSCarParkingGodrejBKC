package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Interface.OnLogoutOrPrint;
import info.vams.zktecoedu.reception.Model.LogoutListData;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;

/**
 * Created by Nithin on 8/9/2018.
 */

public class LogoutListAdapter extends RecyclerView.Adapter {
    Context context;
    public ArrayList<LogoutListData> logoutListData;
    OnLogoutOrPrint onLogoutOrPrint;
    public boolean isLogout;

    public LogoutListAdapter(Context context, ArrayList<LogoutListData> logoutListData, OnLogoutOrPrint onLogoutOrPrint, boolean isLogout) {
        this.context = context;
        this.logoutListData = logoutListData;
        this.onLogoutOrPrint = onLogoutOrPrint;
        this.isLogout = isLogout;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_logout_row, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {

        MyHolder holder = (MyHolder) mainHolder;
        LogoutListData logoutListData = this.logoutListData.get(position);


        if (logoutListData != null) {


            if (logoutListData.getName() != null && !logoutListData.getName().isEmpty()) {
                holder.tvName.setVisibility(View.VISIBLE);
                holder.tvName.setText(logoutListData.getName());
            } else {
                holder.tvName.setVisibility(View.GONE);
            }


            if (!isLogout) {
                if (CommonPlaceForOjects.settings != null &&
                        CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("E")) {
                    if (logoutListData.getEmail() != null && !logoutListData.getEmail().isEmpty()) {
                        holder.tvEmail.setVisibility(View.VISIBLE);
                        holder.tvEmail.setText(logoutListData.getEmail());
                    } else {
                        holder.tvEmail.setVisibility(View.GONE);
                    }
                } else {

                    if (logoutListData.getMobile() != null && !logoutListData.getMobile().isEmpty()) {
                        holder.tvNumber.setVisibility(View.VISIBLE);
                        holder.tvNumber.setText(logoutListData.getMobile());
                    } else {
                        holder.tvNumber.setVisibility(View.GONE);
                    }
                }

            } else {
                if (logoutListData.getEmail() != null && !logoutListData.getEmail().isEmpty()) {
                    holder.tvEmail.setVisibility(View.VISIBLE);
                    holder.tvEmail.setText(logoutListData.getEmail());
                } else {
                    holder.tvEmail.setVisibility(View.GONE);
                }

                if (logoutListData.getMobile() != null && !logoutListData.getMobile().isEmpty()) {
                    holder.tvNumber.setVisibility(View.VISIBLE);
                    holder.tvNumber.setText(logoutListData.getMobile());
                } else {
                    holder.tvNumber.setVisibility(View.GONE);
                }
            }


            if (logoutListData.getCompany() != null && !logoutListData.getCompany().isEmpty()) {
                holder.tvCompany.setVisibility(View.VISIBLE);
                holder.tvCompany.setText(logoutListData.getCompany());
            } else {
                holder.tvCompany.setVisibility(View.GONE);
            }


            if (logoutListData.getAccessCardNo() != null && !logoutListData.getAccessCardNo().isEmpty()) {
                holder.tvAccessCardNo.setVisibility(View.VISIBLE);
                holder.tvAccessCardNo.setText(logoutListData.getAccessCardNo());
            } else {
                holder.tvAccessCardNo.setVisibility(View.GONE);
            }

            if (logoutListData.getPassId() != null && !logoutListData.getPassId().isEmpty()) {
                holder.tvPassNo.setVisibility(View.VISIBLE);
                holder.tvPassNo.setText(logoutListData.getPassId());
            } else {
                holder.tvPassNo.setVisibility(View.GONE);
            }

            PicassoTrustAllCerificate.getInstance(context)
                    .load(logoutListData.getImageUrl())
                    .error(context.getResources()
                    .getDrawable(R.drawable.profile))
                    .placeholder(context.getResources().getDrawable(R.drawable.loading))
                    .into(holder.cvImage);
        }
    }

    @Override
    public int getItemCount() {
        return logoutListData.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvNumber, tvEmail, tvCompany, tvLoginTime, tvTenantName, tvAccessCardNo, tvPassNo;
        Button btnPrintPass, btnLogout;
        CircleImageView cvImage;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvLogoutName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvLogoutEmail);
            tvNumber = (TextView) itemView.findViewById(R.id.tvLogoutNumber);
            tvCompany = (TextView) itemView.findViewById(R.id.tvLogoutCompany);
            tvLoginTime = (TextView) itemView.findViewById(R.id.tvLogoutLoginTime);
            tvTenantName = (TextView) itemView.findViewById(R.id.tvLogoutTenantName);
            tvAccessCardNo = (TextView) itemView.findViewById(R.id.tvLogoutAccessCardNo);
            tvPassNo = (TextView) itemView.findViewById(R.id.tvLogoutPassNo);
            btnLogout = (Button) itemView.findViewById(R.id.btnLogoutButton);
            btnPrintPass = (Button) itemView.findViewById(R.id.btnLogoutPrintPass);
            cvImage = (CircleImageView) itemView.findViewById(R.id.cvProfileLogout);
            btnPrintPass.setOnClickListener(this);
            btnLogout.setOnClickListener(this);

        /*    UsPhoneNumberFormatterInTextView addLineNumberFormatter = new UsPhoneNumberFormatterInTextView(
                    new WeakReference<TextView>(tvNumber));
            tvNumber.addTextChangedListener(addLineNumberFormatter);*/

            if (isLogout) {
                btnLogout.setVisibility(View.VISIBLE);
            } else {
                btnLogout.setVisibility(View.GONE);
            }

            if (isLogout) {
                if (CommonPlaceForOjects.settings.getPrintPass()) {
                    btnPrintPass.setVisibility(View.VISIBLE);
                } else {
                    btnPrintPass.setVisibility(View.GONE);
                }
            } else {
                if(CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getPrintPass()){
                    btnLogout.setText("SOFT PASS");
                    btnLogout.setVisibility(View.VISIBLE);
                    btnPrintPass.setVisibility(View.GONE);
                }else{
                    btnPrintPass.setText("PRINT PASS");
                    btnPrintPass.setVisibility(View.VISIBLE);
                    btnLogout.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnLogoutButton:
                    LogoutListData temp = logoutListData.get(getAdapterPosition());
                    onLogoutOrPrint.onLogout(temp.getVisitorId());
                    break;

                case R.id.btnLogoutPrintPass:
                    LogoutListData printId = logoutListData.get(getAdapterPosition());
                    onLogoutOrPrint.onPrintPass(printId.getVisitorId());
                    break;


            }
        }
    }

    public void filteredList(ArrayList<LogoutListData> filterData) {
        this.logoutListData = filterData;
        notifyDataSetChanged();
    }
}
