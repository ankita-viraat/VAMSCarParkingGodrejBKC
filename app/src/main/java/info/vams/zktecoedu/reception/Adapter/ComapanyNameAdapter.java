package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import info.vams.zktecoedu.reception.Activity.CreateAppointmentActivityOne;
import info.vams.zktecoedu.reception.Activity.VisitorEntryActivityOne;
import info.vams.zktecoedu.reception.Model.VisitorLogPersonToVisit;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.Utils;

/**
 * Created by Nithin on 7/27/2018.
 */

public class ComapanyNameAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<VisitorLogPersonToVisit> employeeLists;
    VisitorEntryActivityOne visitorEntryActivityOne;
    CreateAppointmentActivityOne createAppointmentActivityOne;

    public ComapanyNameAdapter(Context context, ArrayList<VisitorLogPersonToVisit> employeeListsCollection,
                               VisitorEntryActivityOne visitorEntryActivityOne,
                               CreateAppointmentActivityOne createAppointmentActivityOne) {
        this.context = context;
        this.employeeLists = employeeListsCollection;
        this.visitorEntryActivityOne = visitorEntryActivityOne;
        this.createAppointmentActivityOne = createAppointmentActivityOne;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_auto_txt, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder MainHolder, int position) {
        MyViewHolder holder = (MyViewHolder) MainHolder;
        VisitorLogPersonToVisit employeeList = employeeLists.get(position);
        String personToMeetString = "";
        if (Utils.isEmpty(employeeList.getName())) {
            employeeList.setName("");
        } else {
            personToMeetString = employeeList.getName();
        }

        /*if (Utils.isEmpty(employeeList.getLastName())) {
            employeeList.setLastName("");
        } else {
            personToMeetString = personToMeetString + " " + employeeList.getLastName();
        }*/


        if (Utils.isEmpty(employeeList.getEmailPrimary())) {
            employeeList.setEmailPrimary("");
        } else {
            personToMeetString = personToMeetString + " | " + employeeList.getEmailPrimary();
        }

        if (Utils.isEmpty(employeeList.getDepartmentName())) {
            employeeList.setDepartmentName("");
        } else {
            personToMeetString = personToMeetString + " | " + employeeList.getDepartmentName();
        }

        /*holder.lblName.setText(employeeList.getFirstName() + " " + employeeList.getLastName() + " | " +
                employeeList.getEmailPrimary() + " | " + employeeList.getDepartment());*/

        holder.lblName.setText(personToMeetString);

    }

    @Override
    public int getItemCount() {
        return employeeLists.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lblName;
        Button btnRemove;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            lblName = (TextView) itemView.findViewById(R.id.lbl_name);
            btnRemove = (Button) itemView.findViewById(R.id.btncustomRemove);
            view = (View) itemView.findViewById(R.id.viewHorizontal);
            view.setVisibility(View.VISIBLE);
            btnRemove.setVisibility(View.VISIBLE);
            btnRemove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (visitorEntryActivityOne != null){
                visitorEntryActivityOne.employeeListsCollection.remove(employeeLists.get(getAdapterPosition()));
                if(VisitorEntryActivityOne.employeeListsCollection.size() == 0){
                    VisitorEntryActivityOne.llPersonToMeet.setVisibility(View.GONE);
                }
                //VisitorEntryActivityOne.llPersonToMeet.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
            }

        }
    }
}
