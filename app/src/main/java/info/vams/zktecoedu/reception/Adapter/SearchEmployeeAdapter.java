package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Interface.IEmployeeSelection;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee.GetParkEmployeeResp;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;

public class SearchEmployeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<GetParkEmployeeResp> sexualOffends;
    IEmployeeSelection iEmployeeSelection;

    public SearchEmployeeAdapter(Context context, ArrayList<GetParkEmployeeResp> sexualOffends, IEmployeeSelection iEmployeeSelection) {
        this.context = context;
        this.sexualOffends = sexualOffends;
        this.iEmployeeSelection = iEmployeeSelection;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_employee_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        try {
            final GetParkEmployeeResp getEployeeResp = sexualOffends.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tvFirstName.setText(getEployeeResp.getFirstName()+" "+getEployeeResp.getLastName());
            myViewHolder.tvMobileNo.setText("+"+getEployeeResp.getIsdCode() + " " + UsPhoneNumberFormatter.formatUsNumber(new SpannableStringBuilder(getEployeeResp.getMobile())));
            myViewHolder.tvVehicleNumber.setText(getEployeeResp.getVehicleNumber());
            myViewHolder.tvCompanyName.setText(getEployeeResp.getTenantName());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iEmployeeSelection.onClicked(getEployeeResp);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return sexualOffends.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFirstName, tvMobileNo, tvVehicleNumber,tvCompanyName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            tvMobileNo = (TextView) itemView.findViewById(R.id.tvMobileNo);
            tvVehicleNumber = (TextView) itemView.findViewById(R.id.tvVehicleNumber);
            tvCompanyName = (TextView) itemView.findViewById(R.id.tvCompanyName);
        }
    }
}
