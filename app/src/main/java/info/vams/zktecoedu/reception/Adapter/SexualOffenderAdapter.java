package info.vams.zktecoedu.reception.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.Utils;

public class SexualOffenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<SexualOffend> sexualOffends;

    public SexualOffenderAdapter(Context context, ArrayList<SexualOffend> sexualOffends) {
        this.context = context;
        this.sexualOffends = sexualOffends;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sexual_offend_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        try {
            SexualOffend sexualOffend = sexualOffends.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.tvFirstName.setText(sexualOffend.getFirstName());
            myViewHolder.tvMiddleName.setText(sexualOffend.getMiddleName());
            myViewHolder.tvLastName.setText(sexualOffend.getLastName());
            myViewHolder.tvDob.setText(sexualOffend.getDateOfBirth());
            myViewHolder.tvOffenderStatus.setText(sexualOffend.getOffenderStatus());
            myViewHolder.tvOffenderCategory.setText(sexualOffend.getOffenderCategory());

            if (!Utils.isEmpty(sexualOffend.getImageUrl())) {
                PicassoTrustAllCerificate.getInstance(context).load(sexualOffend.getImageUrl())
                        .error(context.getResources().getDrawable(R.drawable.profile)).
                        placeholder(context.getResources().getDrawable(R.drawable.loading)).
                        into(((MyViewHolder) holder).profilePic);


            }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return sexualOffends.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFirstName, tvMiddleName, tvLastName, tvDob, tvOffenderStatus, tvOffenderCategory;
        CircleImageView profilePic,ivSexualOffendCancel;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            profilePic = (CircleImageView) itemView.findViewById(R.id.cvProfilePic);
            tvMiddleName = (TextView) itemView.findViewById(R.id.tvMiddleName);
            tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);
            tvDob = (TextView) itemView.findViewById(R.id.tvDob);
            tvOffenderStatus = (TextView) itemView.findViewById(R.id.tvOffenderStatus);
            tvOffenderCategory = (TextView) itemView.findViewById(R.id.tvOffenderCategory);
        }
    }
}
