package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Adapter.NotAllowedParentListAdapter;
import info.vams.zktecoedu.reception.Adapter.ParentListAdapter;
import info.vams.zktecoedu.reception.Adapter.StudentAutoCompleteAdapter;
import info.vams.zktecoedu.reception.Model.Parent;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.StudentList;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChildPickupActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    AutoCompleteTextView act_studentName;
    ArrayList<StudentList> studentListArrayList;
    TextView tvStudentName, tvStudentClass, tvStudentId, tvStudentMobile, tvTeacherId,tvAdmin,tvSheriff,tvHeader,tvTeacherName;
    CircleImageView cvStudentProfile, cv_teacherProfileImage;
    RecyclerView rv_Parents, rv_NotAllowedParents;
    ScrollView sv_main;
    ImageView ivReset_ChildPickup,ivLogo_ChildPickUpLogo,ivSos;
    Button btnCancelDialog,btnYes;
    CheckBox adminCheckbox,sheriffCheckbox;

    AlertDialog alertDialog=null;
    TextView tvNoDataFound, tvNotAllowedDataFound;
    ParentListAdapter parentListAdapter;
    NotAllowedParentListAdapter notAllowedParentListAdapter;
    LinearLayout llTeacherlayout,llMainLayout, ll_NotAllowedParentsTag, ll_ParentsTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_pickup);
        context = ChildPickupActivity.this;
        init();
        SPbean sPbean = new SPbean(context);

        if (sPbean.getPreference(Constants.STUDENTLIST_RESPONSE, "").equalsIgnoreCase("")) {
            callStudentList();
        } else {
            bindStudentToAutoCompleteText();
        }
    }

    private void init() {
        sv_main = findViewById(R.id.sv_main);

        act_studentName = findViewById(R.id.act_studentName);
        act_studentName.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentClass = findViewById(R.id.tvStudentClass);
        tvStudentMobile = findViewById(R.id.tvStudentMobile);
        tvStudentId = findViewById(R.id.tvStudentId);

        tvTeacherId = findViewById(R.id.tvTeacherId);
        tvTeacherName = findViewById(R.id.tvTeacherName);

        cvStudentProfile = findViewById(R.id.cvStudentProfile);
        cv_teacherProfileImage = findViewById(R.id.cv_teacherProfileImage);

        llTeacherlayout = (LinearLayout) findViewById(R.id.llTeacherlayout);
        llMainLayout = (LinearLayout) findViewById(R.id.llMainlayout);
        ll_NotAllowedParentsTag = (LinearLayout) findViewById(R.id.ll_NotAllowedParentsTag);
        ll_ParentsTag = (LinearLayout) findViewById(R.id.ll_ParentsTag);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);

        ivReset_ChildPickup = findViewById(R.id.ivReset_ChildPickup);
        ivLogo_ChildPickUpLogo = findViewById(R.id.ivLogo_ChildPickUp);
        ivReset_ChildPickup.setOnClickListener(this);
        ivLogo_ChildPickUpLogo.setOnClickListener(this);
        tvNoDataFound = findViewById(R.id.tvNoDataFound);
        tvNotAllowedDataFound = findViewById(R.id.tvNotAllowedDataFound);

        Utilities.setUserLogo(context,ivLogo_ChildPickUpLogo);
        rv_Parents = (RecyclerView) findViewById(R.id.rv_Parents);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_Parents.setLayoutManager(layoutManager);

        rv_NotAllowedParents = (RecyclerView) findViewById(R.id.rv_NotAllowedParents);
        RecyclerView.LayoutManager layoutManagers = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_NotAllowedParents.setLayoutManager(layoutManagers);

    }

    private void callParentsList(final int studentId) {
        Log.d("TAG", "Student id = " + studentId);
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
            Utilities.showprogressDialogue(getString(R.string.gettingStudentDetails), getString(R.string.please_wait), context, false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("studentId", studentId);
            map.put("complexId", profile.getComplexId() != null ? profile.getComplexId() : 0);
            map.put("employeeId", profile.getEmployeeId() != null ? profile.getEmployeeId() : 0);
            map.put("searchString", "");
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().getStudentDetailsByStudentId(Utilities.getToken(context), map);
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

                                    if (result != null) {

                                        sv_main.setVisibility(View.VISIBLE);

                                        JSONObject studentDetails = new JSONObject(result);
                                        JSONArray parentlist = studentDetails.getJSONArray("parents");
                                        ArrayList<Parent> parentArrayList = new Gson().fromJson(parentlist.toString(), new TypeToken<ArrayList<Parent>>() {
                                        }.getType());

                                        if (studentDetails != null) {
                                            act_studentName.setText("");
                                            if(!studentDetails.isNull("firstName")) {
                                                llMainLayout.setVisibility(View.VISIBLE);
                                                tvStudentName.setText(studentDetails.getString("firstName") != null ? studentDetails.getString("firstName") : "" + " " + studentDetails.getString("lastName"));
                                            }else{
                                                llMainLayout.setVisibility(View.GONE);
                                            }

                                            /*if (!studentDetails.isNull("isdCode") && !studentDetails.getString("isdCode").isEmpty()
                                                    && studentDetails.isNull("mobile") && !studentDetails.getString("mobile").isEmpty()) {
                                                tvStudentMobile.setText(studentDetails.getString("isdCode") + " " + studentDetails.getString("mobile"));
                                            } else {
                                                tvStudentMobile.setVisibility(View.GONE);
                                            }*/

                                            if (!studentDetails.isNull("className")) {
                                                tvStudentClass.setText(studentDetails.getString("className"));
                                            } else {
                                                tvStudentClass.setVisibility(View.GONE);
                                            }
                                            tvStudentId.setText(studentDetails.getString("enrollmentNo"));
                                            if (studentDetails.getString("studentImageURL") != null) {
                                                PicassoTrustAllCerificate.getInstance(context).load(studentDetails.getString("studentImageURL")).
                                                        error(context.getResources().getDrawable(R.drawable.profile)).
                                                        placeholder(context.getResources().getDrawable(R.drawable.loading)).
                                                        into(cvStudentProfile);
                                            }
                                            if (!studentDetails.isNull("classTeacherName") &&
                                                    !studentDetails.getString("classTeacherName").isEmpty()) {
                                                llTeacherlayout.setVisibility(View.VISIBLE);
                                                /*if (!studentDetails.isNull("classTeacherId")) {
                                                    tvTeacherId.setText("" + studentDetails.getInt("classTeacherId"));
                                                } else {
                                                    tvTeacherId.setVisibility(View.GONE);
                                                }*/
                                                tvTeacherName.setText(studentDetails.getString("classTeacherName"));

                                                if (studentDetails.getString("classTeacherImageURL") != null) {
                                                    PicassoTrustAllCerificate.getInstance(context).load(studentDetails.getString("classTeacherImageURL")).
                                                            error(context.getResources().getDrawable(R.drawable.profile)).
                                                            placeholder(context.getResources().getDrawable(R.drawable.loading)).
                                                            into(cv_teacherProfileImage);
                                                }
                                            } else {
                                                llTeacherlayout.setVisibility(View.GONE);
                                            }
                                        }
                                        if (parentArrayList != null) {
                                            ArrayList<Parent> allowedParents = new ArrayList<>();
                                            ArrayList<Parent> notAllowedParents = new ArrayList<>();
                                            for (Parent parent : parentArrayList) {
                                                if (parent.getAllowed()) {
                                                    allowedParents.add(parent);
                                                } else {
                                                    notAllowedParents.add(parent);
                                                }
                                            }
                                            if (allowedParents.size() > 0) {
                                                rv_Parents.setVisibility(View.VISIBLE);
                                                tvNoDataFound.setVisibility(View.GONE);
                                                ll_ParentsTag.setVisibility(View.VISIBLE);
                                                parentListAdapter = new ParentListAdapter(context, allowedParents, studentId, rv_Parents);
                                                rv_Parents.setAdapter(parentListAdapter);
                                            } else {
                                                rv_Parents.setVisibility(View.GONE);
                                                tvNoDataFound.setVisibility(View.GONE);
                                                ll_ParentsTag.setVisibility(View.GONE);
                                            }

                                            if (notAllowedParents.size() > 0) {
                                                rv_NotAllowedParents.setVisibility(View.VISIBLE);
                                                tvNotAllowedDataFound.setVisibility(View.GONE);
                                                ll_NotAllowedParentsTag.setVisibility(View.VISIBLE);
                                                notAllowedParentListAdapter = new NotAllowedParentListAdapter(context, notAllowedParents, studentId, rv_NotAllowedParents);
                                                rv_NotAllowedParents.setAdapter(notAllowedParentListAdapter);
                                            } else {
                                                rv_NotAllowedParents.setVisibility(View.GONE);
                                                tvNotAllowedDataFound.setVisibility(View.GONE);
                                                ll_NotAllowedParentsTag.setVisibility(View.GONE);
                                            }
                                        } else {
                                            rv_Parents.setVisibility(View.GONE);
                                            tvNoDataFound.setVisibility(View.GONE);
                                            rv_NotAllowedParents.setVisibility(View.GONE);
                                            tvNotAllowedDataFound.setVisibility(View.GONE);
                                            ll_ParentsTag.setVisibility(View.GONE);
                                            ll_NotAllowedParentsTag.setVisibility(View.GONE);

                                        }
                                    } else {
                                        sv_main.setVisibility(View.GONE);
                                    }

                                } else {
                                    Utilities.showPopup(context, "", getString(R.string.technical_error));
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
                    Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                }
            });

    }

    private void callStudentList() {
        Utilities.showprogressDialogue("Loading...", getString(R.string.please_wait), context, true);
        Call<ResponseBody> call = ApiUtils.getAPIService().getStudentList(Utilities.getToken(context),
                (RequestClientDetails) Utilities.requestclientDetails(context));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Student List =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String studentList_Response = response.body().string();
                            new SPbean(context).setPreference(Constants.STUDENTLIST_RESPONSE, studentList_Response);
                            bindStudentToAutoCompleteText();
                        } catch (Exception e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.showPopup(context, "", "Response is null");
                    }
                } else {
                    Utilities.hideProgress();
                    try {
                        if (response.errorBody() != null) {
                            String errorMsg = response.errorBody().string().toString();
                            String error = "";
                            JSONArray jsonArray = new JSONArray(errorMsg);
                            if (jsonArray != null) {
                                JSONObject object = jsonArray.getJSONObject(0);
                                error = object.getString("message");
                                Utilities.showPopup(context, "", "" + error);
                            } else {
                                Utilities.showPopup(context, "", "" + errorMsg);
                            }
                        } else {
                            Utilities.showPopup(context, "", getString(R.string.technical_error));
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
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                e.printStackTrace();
            }
        });
    }

    private void bindStudentToAutoCompleteText() {
        studentListArrayList = new Gson().fromJson(new SPbean(context).getPreference(Constants.STUDENTLIST_RESPONSE, ""),
                new TypeToken<ArrayList<StudentList>>() {
                }.getType());

        if (studentListArrayList != null) {
            StudentAutoCompleteAdapter studentAutoCompleteAdapter = new StudentAutoCompleteAdapter(context,
                    R.layout.activity_visitor_entry_one, R.id.lbl_name, studentListArrayList);

            act_studentName.setThreshold(1);         //will start working from first character
            act_studentName.setAdapter(studentAutoCompleteAdapter);

            act_studentName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(act_studentName.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                        /*InputMethodManager inputManager = (InputMethodManager) getSystemService(context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);*/

                        StudentList studentList = (StudentList) view.getTag();

                        if (studentList.getFirstName() == null) {
                            studentList.setFirstName("");
                        }
                        if (studentList.getLastName() == null) {
                            studentList.setLastName("");
                        }

                        if (studentList.getClassName() == null) {
                            studentList.setClassName("");
                        }

                        //Toast.makeText(ChildPickupActivity.this, ""+studentList.getFirstName()+" "+studentList.getLastName()+" | "+studentList.getStudentId()+" | "+studentList.getClassName(), Toast.LENGTH_LONG).show();
                       /* tvStudentName.setText(studentList.getFirstName()+" "+studentList.getLastName());
                        tvStudentClass.setText(studentList.getClassName());
                        tvStudentId.setText(""+studentList.getStudentId());*/

                        callParentsList(studentList.getStudentId() != null ? studentList.getStudentId() : 0);

                        /*actvTenantName.setText(tenant.getName()+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName());
                        etBuildingName.setText(tenant.getBuildingName());
                        etBuildingName.setEnabled(false);
                        etBuildingName.setTextColor(getResources().getColor(R.color.black));
                        visitorLogMobileViewModel.setTenantId(String.valueOf(tenant.getTenantId()));
                        visitorLogMobileViewModel.setBuildingId(String.valueOf(tenant.getBuildingId()));
                        visitorLogMobileViewModel.setBuildingName(tenant.getBuildingName());
                        visitorLogMobileViewModel.setTenantName(tenant.getName());*/
                    }
                }
            });
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivReset_ChildPickup:
                Intent intent1 = new Intent(this, HomeScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;

            case R.id.ivLogo_ChildPickUp:
                Utilities.redirectToHome(context);
                break;
            case R.id.ivSos:
                Utilities.showDialogForSos(ChildPickupActivity.this);
                break;


        }

    }
}
