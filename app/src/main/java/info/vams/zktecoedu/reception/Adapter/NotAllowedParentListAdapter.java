package info.vams.zktecoedu.reception.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.daimajia.swipe.SwipeLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Model.Parent;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotAllowedParentListAdapter extends RecyclerView.Adapter {

    Context context;
    public ArrayList<Parent> parentArrayList;
    public int studentId;
    int lastSwipedPosition = -1;
    RecyclerView rv_NotAllowedParents;


    public NotAllowedParentListAdapter(Context context, ArrayList<Parent> parentArrayList, int studentId,
                                       RecyclerView rv_NotAllowedParents) {
        this.context = context;
        this.parentArrayList = parentArrayList;
        this.studentId = studentId;
        this.rv_NotAllowedParents = rv_NotAllowedParents;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parents_details_row,parent,false);
        NotAllowedParentListAdapter.MyHolder myHolder = new NotAllowedParentListAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, final int position) {

        final NotAllowedParentListAdapter.MyHolder holder = (NotAllowedParentListAdapter.MyHolder) mainHolder;
        final Parent parent = this.parentArrayList.get(position);

        if (parent != null) {
            if (!parent.getAllowed()) {

                holder.swipeLayout.setSwipeEnabled(false);
                holder.swipeLayout.setBackground(context.getResources().getDrawable(R.drawable.swipe_disabled_bg));
                holder.viewSideColor.setBackground(context.getResources().getDrawable(R.color.red_primary));


                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.itemView.findViewById(R.id.bottom_wrapper));
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.bottom_wrapper));
                if ((parent.getFirstName() != null && !parent.getFirstName().isEmpty()) || (parent.getLastName() != null && !parent.getLastName().isEmpty())) {
                    holder.tvparentName.setVisibility(View.VISIBLE);
                    holder.tvparentName.setText((parent.getFirstName()!=null ? parent.getFirstName() : "" )+ " " + (parent.getLastName()!=null ? parent.getLastName() : ""));
                } else {
                    holder.tvparentName.setVisibility(View.GONE);
                }

                if (parent.getEmail() != null && !parent.getEmail().isEmpty()) {
                    holder.tvParentEmail.setVisibility(View.VISIBLE);
                    holder.tvParentEmail.setText(parent.getEmail());
                } else {
                    holder.tvParentEmail.setVisibility(View.GONE);
                }

                if (parent.getMobile() != null && !parent.getMobile().isEmpty()) {
                    holder.tvParentMobile.setVisibility(View.VISIBLE);
                    holder.tvParentMobile.setText((parent.getIsdCode()!= null ? parent.getIsdCode() : "") + " " + (parent.getMobile()!= null ? parent.getMobile() : ""));
                } else {
                    holder.tvParentMobile.setVisibility(View.GONE);
                }

                if (parent.getImageURL() != null) {
                    PicassoTrustAllCerificate.getInstance(context).load(parent.getImageURL()).
                            error(context.getResources().getDrawable(R.drawable.profile)).
                            placeholder(context.getResources().getDrawable(R.drawable.loading)).
                            into(holder.cvParentProfile);
                }

                holder.cvParentProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            final ImagePopup imagePopup = new ImagePopup(context);
                            imagePopup.setWindowHeight(300); // Optional
                            imagePopup.setWindowWidth(400); // Optional
                            imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                            imagePopup.setFullScreen(true); // Optional
                            imagePopup.setHideCloseIcon(true);  // Optional
                            imagePopup.setImageOnClickClose(true);  // Optional

                            imagePopup.initiatePopup(holder.cvParentProfile.getDrawable());


                            imagePopup.viewPopup();

                    }
                });


                holder.ivApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.swipeLayout.close(true);

                        try {
                            callChildPickupEntry(parent.getParentId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


                holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                    @Override
                    public void onStartOpen(SwipeLayout layout) {
                        try {
                            if (lastSwipedPosition != -1 && lastSwipedPosition != position
                                    && rv_NotAllowedParents != null &&
                                    rv_NotAllowedParents.findViewHolderForAdapterPosition(lastSwipedPosition) != null &&
                                    rv_NotAllowedParents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView != null &&
                                    rv_NotAllowedParents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView.findViewById(R.id.swMain) != null) {
//                                  notifyItemChanged(lastSwipedPosition);
                                SwipeLayout swipeLayout = (SwipeLayout) rv_NotAllowedParents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView.findViewById(R.id.swMain);
                                swipeLayout.close(true);
                            }
                            lastSwipedPosition = position;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onOpen(SwipeLayout layout) {

                    }

                    @Override
                    public void onStartClose(SwipeLayout layout) {

                    }

                    @Override
                    public void onClose(SwipeLayout layout) {

                    }

                    @Override
                    public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

                    }

                    @Override
                    public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

                    }
                });
            }
        }


    }

    @Override
    public int getItemCount() {
        return parentArrayList.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        TextView tvparentName,tvParentEmail,tvParentMobile;
        CircleImageView cvParentProfile;
        SwipeLayout swipeLayout;
        View viewSideColor;
        public ImageView ivApprove;

        public MyHolder(View itemView) {
            super(itemView);
            tvparentName = (TextView)itemView.findViewById(R.id.tvparentName);
            tvParentEmail= (TextView)itemView.findViewById(R.id.tvParentEmail);
            tvParentMobile= (TextView)itemView.findViewById(R.id.tvParentMobile);
            cvParentProfile = (CircleImageView) itemView.findViewById(R.id.cvParentProfile);
            swipeLayout = (SwipeLayout)itemView.findViewById(R.id.swMain);
            viewSideColor = (View)itemView.findViewById(R.id.viewSideColor);
            ivApprove = (ImageView) itemView.findViewById(R.id.ivApprove);

        }


    }

    public void filteredList(ArrayList<Parent> filterData){
        this.parentArrayList = filterData;
        notifyDataSetChanged();
    }

    public void callChildPickupEntry(int parentId){
        Log.d("TAG", "Student id = " + studentId);
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        if (profile != null && profile.getComplexId() != null && profile.getEmployeeId() != null) {
            Utilities.showprogressDialogue(context.getString(R.string.savingchildpickupentry), context.getString(R.string.please_wait), context, false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("studentId", studentId);
            map.put("complexId", profile.getComplexId() != null ? profile.getComplexId() : 0);
            map.put("employeeId", profile.getEmployeeId()!= null ? profile.getEmployeeId() : 0);
            map.put("parentId",parentId);
            map.put("childPickupParentDetailsId", 0);
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().getChildPickupEntry(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    try {
                        Utilities.hideProgress();
                        if (response != null) {

                            if (response.code() == 200) {

                                if (response.body() != null) {// positive response
                                    String result = response.body().string();

                                    Log.d("TAG",result);

                                    showPopup(context,context.getString(R.string.child_pickup_detail_saved));

                                } else {
                                    Utilities.showPopup(context, "", context.getResources().getString(R.string.technical_error));
                                }
                            } else {
                                Utilities.hideProgress();
                                if (response.errorBody() != null) {// Negative Response


                                } else {

                                }

                            }


                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utilities.hideProgress();
                    Utilities.showPopup(context, "", context.getResources().getString(R.string.no_internet_msg_text));
                }
            });
        } else {
            Utilities.showPopup(context, "", "Profile Details are Empty");
        }

    }

    public static void showPopup(final Context context,
                                 final String msg) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            alertDialog.setCancelable(false);

           /* if (!title.isEmpty()) {
                alertDialog.setTitle(title);
            }*/
            alertDialog.setMessage(msg);

            alertDialog.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ((Activity) context).finish();
                        }
                    });

            alertDialog.show();
        }

    }
}
