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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Model.Parent;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.Model.SexualOffendedList;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentListAdapter extends RecyclerView.Adapter {

    Context context;
    public ArrayList<Parent> parentArrayList;
    public int studentId;
    int lastSwipedPosition = -1;
    RecyclerView rv_Parents;

    AlertDialog alertDialog;

    ArrayList<SexualOffend> sexualOffends = null;


    public ParentListAdapter(Context context, ArrayList<Parent> parentArrayList, int studentId, RecyclerView rv_Parents) {
        this.context = context;
        this.parentArrayList = parentArrayList;
        this.studentId = studentId;
        this.rv_Parents = rv_Parents;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.parents_details_row, parent, false);
        ParentListAdapter.MyHolder myHolder = new ParentListAdapter.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, final int position) {

        final ParentListAdapter.MyHolder holder = (ParentListAdapter.MyHolder) mainHolder;
        final Parent parent = this.parentArrayList.get(position);

        if (parent != null) {
            if (parent.getAllowed()) {


                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.itemView.findViewById(R.id.bottom_wrapper));
                holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.itemView.findViewById(R.id.bottom_wrapper));

                if ((parent.getFirstName() != null && !parent.getFirstName().isEmpty()) || (parent.getLastName() != null && !parent.getLastName().isEmpty())) {
                    holder.tvparentName.setVisibility(View.VISIBLE);
                    holder.tvparentName.setText((parent.getFirstName() != null ? parent.getFirstName() : "") + " " + (parent.getLastName() != null ? parent.getLastName() : ""));
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
                    holder.tvParentMobile.setText((parent.getIsdCode() != null ? parent.getIsdCode() : "") + " " + (parent.getMobile() != null ? parent.getMobile() : ""));
                } else {
                    holder.tvParentMobile.setVisibility(View.GONE);
                }

//            if (!parent.getIsAllowed()){
//
//                holder.swipeLayout.setSwipeEnabled(false);
//                holder.swipeLayout.setBackground(context.getResources().getDrawable(R.drawable.swipe_disabled_bg));
//                holder.viewSideColor.setBackground(context.getResources().getDrawable(R.color.red_primary));
//            }


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
                            //CallSexualOffender(holder.tvparentName.getText().toString(), parent.getParentId());
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
                                    && rv_Parents != null &&
                                    rv_Parents.findViewHolderForAdapterPosition(lastSwipedPosition) != null &&
                                    rv_Parents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView != null &&
                                    rv_Parents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView.findViewById(R.id.swMain) != null) {
//                                  notifyItemChanged(lastSwipedPosition);
                                SwipeLayout swipeLayout = (SwipeLayout) rv_Parents.findViewHolderForAdapterPosition(lastSwipedPosition).itemView.findViewById(R.id.swMain);
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
        TextView tvparentName, tvParentEmail, tvParentMobile;
        CircleImageView cvParentProfile;
        SwipeLayout swipeLayout;
        View viewSideColor;
        public ImageView ivApprove;

        public MyHolder(View itemView) {
            super(itemView);
            tvparentName = (TextView) itemView.findViewById(R.id.tvparentName);
            tvParentEmail = (TextView) itemView.findViewById(R.id.tvParentEmail);
            tvParentMobile = (TextView) itemView.findViewById(R.id.tvParentMobile);
            cvParentProfile = (CircleImageView) itemView.findViewById(R.id.cvParentProfile);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swMain);
            viewSideColor = (View) itemView.findViewById(R.id.viewSideColor);
            ivApprove = (ImageView) itemView.findViewById(R.id.ivApprove);

        }


    }

    public void filteredList(ArrayList<Parent> filterData) {
        this.parentArrayList = filterData;
        notifyDataSetChanged();
    }

    public void callChildPickupEntry(int parentId) {
        Log.d("TAG", "Student id = " + studentId);
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        if (profile != null && profile.getComplexId() != null && profile.getEmployeeId() != null) {
            Utilities.showprogressDialogue(context.getString(R.string.savingchildpickupentry), context.getString(R.string.please_wait), context, false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("studentId", studentId);
            map.put("complexId", profile.getComplexId() != null ? profile.getComplexId() : 0);
            map.put("employeeId", profile.getEmployeeId() != null ? profile.getEmployeeId() : 0);
            map.put("parentId", parentId);
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

                                    Log.d("TAG", result);

                                    showPopup(context, context.getString(R.string.child_pickup_detail_saved));

                                } else {
                                    Utilities.showPopup(context, "", context.getResources().getString(R.string.technical_error));
                                }
                            } else {
                                Utilities.hideProgress();
                                try {
                                    if (response.errorBody() != null) {
                                        String errorMsg = response.errorBody().string().toString();
                                        String key = "";
                                        String error = "";
                                        JSONArray jsonArray = new JSONArray(errorMsg);
                                        if (jsonArray != null) {
                                            JSONObject object = jsonArray.getJSONObject(0);
                                            key = object.getString("key");
                                            error = object.getString("message");
                                            Utilities.showPopup(context, "", error);
                                        } else {
                                            Utilities.showPopup(context, "", "" + errorMsg);
                                        }
                                    } else {
                                        Utilities.showPopup(context, "",context.getResources().getString(R.string.technical_error));
                                    }
                                } catch (IOException e) {
                                    //write log method
                                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

    private void CallSexualOffender(String etName, final int parentId) {
        if (AppConfig.IsSexualOffender) {
            Call<ResponseBody> call = ApiUtils.getAPIService().getSexualOffendedList(
                    etName.trim(),"","","");
            Utilities.showprogressDialogue(context.getString(R.string.checkingsexualoffenderlist), context.getString(R.string.please_wait), context, false);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200 || response.code() == 201) {
                        Utilities.hideProgress();
                        sexualOffends = new ArrayList<>();
                        Gson gson = new Gson();
                        String s = null;
                        try {
                            SexualOffendedList sexualOffendedList = new SexualOffendedList();
                            ArrayList<SexualOffend> offends = new ArrayList<>();
                            //s = gson.toJson(response.body().string().toString());

                            JSONArray jsonArray = new JSONArray(response.body().string());
                            if (jsonArray.length() != 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    SexualOffend sexualOffend = new SexualOffend();
                                    sexualOffend.setFirstName(jsonObject.getString("firstName"));
                                    sexualOffend.setMiddleName(jsonObject.getString("middleName"));
                                    sexualOffend.setLastName(jsonObject.getString("lastName"));
                                    sexualOffend.setDateOfBirth(jsonObject.getString("dateOfBirth"));
                                    sexualOffend.setOffenderStatus(jsonObject.getString("offenderStatus"));
                                    sexualOffend.setOffenderCategory(jsonObject.getString("offenderCategory"));
                                    sexualOffend.setImageUrl(jsonObject.getString("imageUrl"));

                                    offends.add(sexualOffend);
                                }

                                sexualOffendedList.setSexualOffends(offends);


                        /*SexualOffendedList sexualOffendedList = new SexualOffendedList();
                        ArrayList<SexualOffend> offends = gson.fromJson(response.body().string(),new TypeToken<ArrayList<SexualOffend>>(){}.getType());
                        sexualOffendedList.setSexualOffends(offends);*/

                                showOffenderList(sexualOffendedList, parentId);

                            } else {

                                callChildPickupEntry(parentId);
                                //tvSexualOffendMatch.setVisibility(View.GONE);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //tvSexualOffendMatch.setVisibility(View.GONE);
                        Utilities.hideProgress();
                        try {
                            if (response.errorBody() != null) {
                                String errorMsg = response.errorBody().string().toString();
                                String key = "";
                                String error = "";
                                JSONArray jsonArray = new JSONArray(errorMsg);
                                if (jsonArray != null) {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    key = object.getString("key");
                                    error = object.getString("message");
                                    Utilities.showPopup(context, "", error);
                                } else {
                                    Utilities.showPopup(context, "", "" + errorMsg);
                                }
                            } else {
                                Utilities.showPopup(context, "", context.getResources().getString(R.string.technical_error));
                            }
                        } catch (IOException e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    //tvSexualOffendMatch.setVisibility(View.GONE);
                }
            });
        }
    }


    private void showOffenderList(SexualOffendedList sexualOffendedList, int parentId) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setView(getSexualList(sexualOffendedList, parentId));
        alertDialog = builder1.create();
        alertDialog.show();

       /* Rect displayRectangle = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.sexual_offend_row, null);
        layout.setMinimumWidth((int)(displayRectangle.width() * 0.7f));
        layout.setMinimumHeight((int)(displayRectangle.height() * 0.7f));

        alertDialog.setView(layout);*/
    }

    private View getSexualList(SexualOffendedList sexualOffendedList, final int parentId) {

        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        ImageView ivSexualOffendCancel;

        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_sexual_offends, null, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvSexualOffender);
        ivSexualOffendCancel = (ImageView) view.findViewById(R.id.ivSexualOffendCancel);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SexualOffenderAdapter sexualOffenderAdapter = new SexualOffenderAdapter(context, sexualOffendedList.getSexualOffends());
        recyclerView.setAdapter(sexualOffenderAdapter);

        //tvSexualOffendMatch.setVisibility(View.VISIBLE);

        ivSexualOffendCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            context);

                    alertDialog.setTitle(context.getResources().getString(R.string.sexual_offender_match_found));

                    alertDialog.setMessage("Do You Want To Proceed?");

                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    callChildPickupEntry(parentId);

                                }
                            });

                    alertDialog.setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                }
                            });

                    alertDialog.show();
                }
            }
        });

        return view;
    }
}
