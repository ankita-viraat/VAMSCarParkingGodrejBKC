package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Date;

import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckInActivity;
import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckOutActivity;
import info.vams.zktecoedu.reception.Interface.ICheckoutReloadHelper;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorResp;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckout.ParkCheckOutReq;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.Helpers.IGetResponse;
import info.vams.zktecoedu.reception.Retrofit.Helpers.PostParkCheckOutHelper;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.internal.Util;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Created by Nithin on 8/9/2018.
 */

public class ParkingCheckoutListAdapter extends RecyclerView.Adapter {
    Context context;
    public ArrayList<GetParkVisitorResp> logoutListData;
    private final int VIEW_TYPE_ITEM = 0;
    ICheckoutReloadHelper iCheckoutReloadHelper;
    private final int VIEW_TYPE_LOADING = 1;
    //    OnLogoutOrPrint onLogoutOrPrint;
    public boolean isLogout;

    public ParkingCheckoutListAdapter(Context context,
                                      ArrayList<GetParkVisitorResp> logoutListData, boolean isLogout, ICheckoutReloadHelper iCheckoutReloadHelper) {
        this.context = context;
        this.logoutListData = logoutListData;
//        this.onLogoutOrPrint = onLogoutOrPrint;
        this.isLogout = isLogout;
        this.iCheckoutReloadHelper = iCheckoutReloadHelper;
    }


    @EverythingIsNonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@EverythingIsNonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.parking_checkout_row, parent, false);
            return new MyHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@EverythingIsNonNull RecyclerView.ViewHolder mainHolder, int position) {

        MyHolder holder = (MyHolder) mainHolder;

        if (mainHolder instanceof MyHolder) {
            final GetParkVisitorResp logoutListData = this.logoutListData.get(position);
            if (logoutListData != null) {
                holder.tvName.setText(logoutListData.getFirstName() + " " + logoutListData.getLastName());
                holder.tvCellnumber.setText("+"+logoutListData.getIsdCode() + " " +  UsPhoneNumberFormatter.formatUsNumber(new SpannableStringBuilder( logoutListData.getMobile())));
                holder.tvVehicleNumber.setText(logoutListData.getVehicleNumber());
                holder.tvInTime.setText(logoutListData.getCheckInTimeLocalString());
                holder.tvStatus.setText(logoutListData.getStatus());
                holder.tvCompany.setText(logoutListData.getTenantName());
                holder.tvParkLoc.setText(logoutListData.getParkingLocationName());
                holder.tvSubLoc.setText(logoutListData.getSubLocation());
                if (logoutListData.getStatus() != null && logoutListData.getStatus().equalsIgnoreCase("out")) {
                    holder.btnLogout.setVisibility(View.GONE);
                } else {
                    holder.btnLogout.setVisibility(View.VISIBLE);
                }
                holder.btnLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParkCheckOutReq parkCheckOutReq = new ParkCheckOutReq();
                        Profile profile = null;
                        try {
                            profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        parkCheckOutReq.setParkingVisitorId(logoutListData.getParkingVisitorId());
                        parkCheckOutReq.setComplexId(profile.getComplexId());
                        parkCheckOutReq.setTenantId(logoutListData.getTenantId() == null
                                ? 0 : Integer.parseInt(logoutListData.getTenantId()));
                        parkCheckOutReq.setParkingLocationId(logoutListData.getParkingLocationId());
                        String currentDate = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.format(new Date());
                        parkCheckOutReq.setCheckOutTimeLocal(currentDate);
                        parkCheckOutReq.setCheckedOutById(profile.getEmployeeId());
                        parkCheckOutReq.setCheckedOutAtDeviceId(Utilities.getUDIDNumber(context));
                        parkCheckOutReq.setCheckedOutAtFcmId(Utilities.getFcmId());
                        parkCheckOutReq.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));
                        PostParkCheckOutHelper.callMaster(context, new IGetResponse() {
                            @Override
                            public void isSuccess(boolean val, Object obj) {
                                Toast.makeText(context, "Vehicle Check Out Successfully.", Toast.LENGTH_LONG).show();
                                iCheckoutReloadHelper.onReload();
                            }
                        }, parkCheckOutReq);
                    }
                });
            }
        } else if (mainHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) mainHolder, position);
        }


    }

    @Override
    public int getItemCount() {
        return logoutListData.size();
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
    }

    public void filteredList(ArrayList<GetParkVisitorResp> filterData) {
        this.logoutListData = filterData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return logoutListData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCellnumber, tvVehicleNumber, tvInTime, tvStatus, tvParkLoc ,tvSubLoc, tvCompany;
        Button btnLogout;

        public MyHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCellnumber = (TextView) itemView.findViewById(R.id.tvCellnumber);
            tvVehicleNumber = (TextView) itemView.findViewById(R.id.tvVehicleNumber);
            tvInTime = (TextView) itemView.findViewById(R.id.tvInTime);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            btnLogout = itemView.findViewById(R.id.btnLogoutButton);
            tvParkLoc = itemView.findViewById(R.id.tvParkLoc);
            tvSubLoc = itemView.findViewById(R.id.tvSubLoc);
            tvCompany = itemView.findViewById(R.id.tvCompany);

        }


    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
