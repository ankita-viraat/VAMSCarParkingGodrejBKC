package info.vams.zktecoedu.reception.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import info.vams.zktecoedu.reception.Activity.CreateAppointmentActivityOne;
import info.vams.zktecoedu.reception.Activity.PreAppointmentActivity;
import info.vams.zktecoedu.reception.Activity.VisitorCheckInActivity;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.VisitorLogMobileViewModel;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import info.vams.zktecoedu.reception.Util.Utils;

/**
 * Created by Nithin on 7/28/2018.
 */

public class SelectionDialogueAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<VisitorLogMobileViewModel> visitorLogMobileViewModels;
    PreAppointmentActivity preAppointmentActivity;
    VisitorCheckInActivity visitorCheckInActivity;
    Dialog dialog;

    public SelectionDialogueAdapter(Context context, ArrayList<VisitorLogMobileViewModel> models,
                                    PreAppointmentActivity preAppointmentActivity, VisitorCheckInActivity visitorCheckInActivity, Dialog dialog) {
        this.context = context;
        this.visitorLogMobileViewModels = models;
        this.preAppointmentActivity = preAppointmentActivity;
        this.visitorCheckInActivity = visitorCheckInActivity;

        this.dialog = dialog;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_appointment_selection, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder MainHolder, int position) {

        MyViewHolder holder = (MyViewHolder) MainHolder;
        VisitorLogMobileViewModel model = visitorLogMobileViewModels.get(position);
        String data = "";

        if (!Utils.isEmpty(model.getVisitorLogPersonToVisit().get(0).getName())) {
            data = model.getVisitorLogPersonToVisit().get(0).getName();
        }

        if (!Utils.isEmpty(model.getVisitorLogPersonToVisit().get(0).getMobilePrimary())) {
            data = data + " | " + "+" + model.getVisitorLogPersonToVisit().get(0).getIsdCode() + model.getVisitorLogPersonToVisit().get(0).getMobilePrimary();
        }

        if (!Utils.isEmpty(model.getTenantName())) {
            data = data + " | " + model.getTenantName();
        }

        if (!Utils.isEmpty(model.getBuildingName())) {
            data = data + " | " + model.getBuildingName();
        }

        holder.tvPersonToMeet.setText(data);

        Date date = null, date1 = null;
        String startTime = model.getStartTime();
        String endTime = model.getEndTime();

        try {
            Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
            date = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.parse(startTime);
            date1 = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.parse(endTime);
            AppConfig.TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"+(profile.getTimeZone().startsWith("-")?profile.getTimeZone().substring(0,6):"+"+profile.getTimeZone().substring(0,5))));
            holder.tvStartTime.setText("" + AppConfig.TIME_FORMAT.format(date) + "  To  " + AppConfig.TIME_FORMAT.format(date1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return visitorLogMobileViewModels.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStartTime, tvEndTime, tvPersonToMeet, tvTenantName;
        Button btnSelect;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTimeSelection);
            tvStartTime.setVisibility(View.VISIBLE);
            tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTimeSelection);
            tvPersonToMeet = (TextView) itemView.findViewById(R.id.tvPersonToMeetSelection);
            tvTenantName = (TextView) itemView.findViewById(R.id.tvTenantNameSelection);
            btnSelect = (Button) itemView.findViewById(R.id.btnSelectSelection);
            btnSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (PreAppointmentActivity.visitorLogMobileViewModel != null) {
                PreAppointmentActivity.visitorLogMobileViewModel = visitorLogMobileViewModels.get(getAdapterPosition());
                context.startActivity(new Intent(context, CreateAppointmentActivityOne.class));

                HashMap<String, Object> map = new HashMap<>();
                map.put("isdCode", PreAppointmentActivity.visitorLogMobileViewModel.getVisitorList().get(0).getIsdCode());
                map.put("mobile", PreAppointmentActivity.visitorLogMobileViewModel.getVisitorList().get(0).getMobile());
                map.put("email", PreAppointmentActivity.visitorLogMobileViewModel.getVisitorList().get(0).getEmail());
                map.put("requestClientDetails", Utilities.requestclientDetails(context));
                CreateAppointmentActivityOne.mathodCallToReSendOtp(map, context);
                if (dialog != null) {
                    dialog.dismiss();
                }

            } else {

                PreAppointmentActivity.visitorLogMobileViewModel = new VisitorLogMobileViewModel();
                PreAppointmentActivity.visitorLogMobileViewModel = visitorLogMobileViewModels.get(getAdapterPosition());
                context.startActivity(new Intent(context, CreateAppointmentActivityOne.class));
                if (dialog != null) {
                    dialog.dismiss();
                }
            }


        }
    }
}
