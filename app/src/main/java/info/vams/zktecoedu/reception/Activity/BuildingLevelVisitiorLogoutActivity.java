package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.vams.zktecoedu.reception.Adapter.BuildingLevelLogoutListAdapter;
import info.vams.zktecoedu.reception.Interface.OnLoginOrLogout;
import info.vams.zktecoedu.reception.Model.Building;
import info.vams.zktecoedu.reception.Model.BuildingLevelLogoutListResponse;
import info.vams.zktecoedu.reception.Model.BuildingLevelLogoutRequest;
import info.vams.zktecoedu.reception.Model.LoginRequest;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.VisitorLogPersonToVisit;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BuildingLevelVisitiorLogoutActivity extends AppCompatActivity implements OnLoginOrLogout, View.OnClickListener, TextWatcher {

    Context context;
    RecyclerView rvLogoutList;
    Button btnCancelDialog,btnYes;
    RecyclerView.LayoutManager layoutManager;
    TextView tvNoDataFound,tvHeader, tvLanguage,tvAdmin,tvSheriff;
    OnLoginOrLogout onLoginOrLogout;
    Toolbar toolbar;
    CheckBox sheriffCheckbox,adminCheckbox;
    EditText etSearchBar;

    ImageView ivBackPress, ivRefresh,ivLogo_BuildingLevel,ivSos;
    ArrayList<BuildingLevelLogoutListResponse> logoutListData = new ArrayList<>();
    BuildingLevelLogoutListAdapter logoutListAdapter;

    BuildingLevelLogoutRequest buildingLevelLogoutRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_level_visitior_logout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar_logoutList);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        context = BuildingLevelVisitiorLogoutActivity.this;
        onLoginOrLogout = BuildingLevelVisitiorLogoutActivity.this;

        buildingLevelLogoutRequest = new BuildingLevelLogoutRequest();
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);

        buildingLevelLogoutRequest.setEmployeeId(profile.getEmployeeId());
        buildingLevelLogoutRequest.setComplexId(profile.getComplexId());
        buildingLevelLogoutRequest.setBuildings(getSelectedBuildingDetails());
        buildingLevelLogoutRequest.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));

        init();
    }

    private ArrayList<Building> getSelectedBuildingDetails(){
        ArrayList<Integer> buildingIds = new SPbean(context).getSelectedBuildingIds(Constants.BUILDING_ID_LIST);
        ArrayList<Building> buildings = new Gson().fromJson(new SPbean(context).getPreference(Constants.BUILDINGLIST_RESPONSE, ""),
                new TypeToken<ArrayList<Building>>() {
                }.getType());
        ArrayList<Building> selectedBuildings = new ArrayList<>();
        for (Building building:
                buildings) {
            if (buildingIds.contains(building.getBuildingId())){
                selectedBuildings.add(building);
            }
        }
        return selectedBuildings;
    }

    private void init() {
        tvNoDataFound = (TextView) findViewById(R.id.tvNoDataFound);
        etSearchBar = (EditText) findViewById(R.id.etSearchBar);
        ivBackPress = (ImageView) findViewById(R.id.ivBackImage_Logout);
        ivRefresh = (ImageView) findViewById(R.id.ivReset_Logout);;
        ivLogo_BuildingLevel = (ImageView) findViewById(R.id.ivUserLogo_BuildingLevel);;
        rvLogoutList = (RecyclerView) findViewById(R.id.rvLogoutList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvLogoutList.setLayoutManager(layoutManager);
        ivLogo_BuildingLevel.setOnClickListener(this);
        ivRefresh.setOnClickListener(this);
        ivBackPress.setOnClickListener(this);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);
        etSearchBar.addTextChangedListener(this);

    }

    /**
     * Make a call to logout list
     */
    private void callLogoutApi(BuildingLevelLogoutRequest buildingLevelLogoutRequest) {
        Utilities.showprogressDialogue("", getString(R.string.please_wait), context, false);
        Call<ResponseBody> call = ApiUtils.getAPIService().getLoggedInVisitorAgainstBuildings(buildingLevelLogoutRequest,Utilities.getToken(context)
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response != null) {

                        if (response.code() == 200) {
                            Utilities.hideProgress();
                            tvNoDataFound.setVisibility(View.GONE);
                            rvLogoutList.setVisibility(View.VISIBLE);

                            if (response.body() != null) {

                                String result = response.body().string();
                                Log.d("TAG","Response : "+result);
                                //logoutListData = new ArrayList<>();
                                /*JSONArray jsonArray = new JSONArray(response.body().string());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    BuildingLevelLogoutListResponse buildingLevelLogoutListResponse = new BuildingLevelLogoutListResponse();
                                    buildingLevelLogoutListResponse.setBuildingId(jsonObject.getInt("buildingId"));
                                    buildingLevelLogoutListResponse.setVisitorLogId(jsonObject.getInt("visitorLogId"));
                                    buildingLevelLogoutListResponse.setVisitorId(jsonObject.getInt("visitorId"));
                                    buildingLevelLogoutListResponse.setComplexId(jsonObject.getInt("complexId"));
                                    buildingLevelLogoutListResponse.setTenantId(jsonObject.getInt("tenantId"));
                                    buildingLevelLogoutListResponse.setName(jsonObject.getString("name"));
                                    buildingLevelLogoutListResponse.setPersonToMeet(new Gson().fromJson(jsonObject.getString("personToMeet"),
                                            new TypeToken<ArrayList<String>>() {
                                            }.getType()));
                                    buildingLevelLogoutListResponse.setComplexInTimeUtc(jsonObject.getString("complexInTimeUtc"));
                                    buildingLevelLogoutListResponse.setComplexOutTimeUtc(jsonObject.getString("complexOutTimeUtc"));
                                    buildingLevelLogoutListResponse.setBuildingInTimeUtc(jsonObject.getString("buildingInTimeUtc"));
                                    buildingLevelLogoutListResponse.setBuildingOutTimeUtc(jsonObject.getString("buildingOutTimeUtc"));
                                    buildingLevelLogoutListResponse.setImageUrl(jsonObject.getString("imageUrl"));


                                    buildingLevelLogoutListResponse.setVisitorLogPersonToVisit(new Gson().fromJson(jsonObject.getString("visitorLogPersonToVisit"),
                                            new TypeToken<ArrayList<VisitorLogPersonToVisit>>() {
                                            }.getType()));

                                    logoutListData.add(buildingLevelLogoutListResponse);
                                }*/

                                /*logoutListData = new Gson().fromJson(response.body().string(),
                                        new TypeToken<ArrayList<BuildingLevelLogoutListResponse>>() {
                                        }.getType());*/

                                //Gson gson = new Gson();

                                // founderListType = new TypeToken<ArrayList<BuildingLevelLogoutListResponse>>(){}.getType();

                                //List<BuildingLevelLogoutListResponse> founderList = gson.fromJson(response.body().string(), founderListType);

                                //BuildingLevelLogoutListResponse[] buildingLevelLogoutListResponses = gson.fromJson(response.body().string(),BuildingLevelLogoutListResponse[].class);

                                //Log.d("TAG","Size = "+buildingLevelLogoutListResponses.length);

                                if (!result.isEmpty()){
                                    logoutListAdapter = new BuildingLevelLogoutListAdapter(context, logoutListData, onLoginOrLogout);
                                    rvLogoutList.setAdapter(logoutListAdapter);
                                }else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    rvLogoutList.setVisibility(View.GONE);
                                }

                                parseJson(result);

                                /*if (!logoutListData.isEmpty()){
                                    logoutListAdapter = new BuildingLevelLogoutListAdapter(context, logoutListData, onLoginOrLogout,onVideoCall);
                                    rvLogoutList.setAdapter(logoutListAdapter);
                                }else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    rvLogoutList.setVisibility(View.GONE);
                                }*/
                            }

                        } else {

                            tvNoDataFound.setVisibility(View.VISIBLE);
                            rvLogoutList.setVisibility(View.GONE);
                            Utilities.showPopup(context, "", "No Data Found");
                            Utilities.hideProgress();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Utilities.hideProgress();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                tvNoDataFound.setVisibility(View.VISIBLE);
                rvLogoutList.setVisibility(View.GONE);
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
            }
        });

    }

    public void parseJson(String json){
        try {
            logoutListData.clear();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BuildingLevelLogoutListResponse buildingLevelLogoutListResponse = new BuildingLevelLogoutListResponse();
                buildingLevelLogoutListResponse.setBuildingId(jsonObject.getInt("buildingId"));
                buildingLevelLogoutListResponse.setVisitorLogId(jsonObject.getInt("visitorLogId"));
                buildingLevelLogoutListResponse.setVisitorId(jsonObject.getInt("visitorId"));
                buildingLevelLogoutListResponse.setComplexId(jsonObject.getInt("complexId"));
                buildingLevelLogoutListResponse.setTenantId(jsonObject.getInt("tenantId"));
                buildingLevelLogoutListResponse.setName(jsonObject.getString("name"));
                buildingLevelLogoutListResponse.setMobilePrimary(jsonObject.getString("mobilePrimary"));
                /*buildingLevelLogoutListResponse.setPersonToMeet(new Gson().fromJson(jsonObject.getString("personToMeet"),
                        new TypeToken<ArrayList<String>>() {
                        }.getType()));*/
                JSONArray array = jsonObject.getJSONArray("personToMeet");
                ArrayList<String> personToMeet = new ArrayList<>();
                for (int j = 0; j < array.length(); j++) {
                    personToMeet.add(array.getString(j));
                }
                buildingLevelLogoutListResponse.setPersonToMeet(personToMeet);
                buildingLevelLogoutListResponse.setComplexInTimeUtc(jsonObject.getString("complexInTimeUtc"));
                buildingLevelLogoutListResponse.setComplexOutTimeUtc(jsonObject.getString("complexOutTimeUtc"));
                buildingLevelLogoutListResponse.setBuildingInTimeUtc(jsonObject.getString("buildingInTimeUtc"));
                buildingLevelLogoutListResponse.setBuildingOutTimeUtc(jsonObject.getString("buildingOutTimeUtc"));
                buildingLevelLogoutListResponse.setImageUrl(jsonObject.getString("imageUrl"));

                JSONArray array1 = jsonObject.getJSONArray("visitorLogPersonToVisit");
                ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisits = new ArrayList<>();
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject jsonObject1 = array1.getJSONObject(j);
                    VisitorLogPersonToVisit visitorLogPersonToVisit = new VisitorLogPersonToVisit();
                    visitorLogPersonToVisit.setVisitorLogPersonToVisitId((Long) jsonObject1.get("visitorLogPersonToVisitId"));
                    visitorLogPersonToVisit.setVisitorLogId((Long) jsonObject1.get("visitorLogId"));
                    visitorLogPersonToVisit.setName(jsonObject1.optString("name",""));
                    visitorLogPersonToVisit.setIsdCode(jsonObject1.optString("isdCode",""));
                    visitorLogPersonToVisit.setMobilePrimary(jsonObject1.optString("mobilePrimary",""));
                    visitorLogPersonToVisit.setMobileSecondary(jsonObject1.optString("mobileSecondary",""));
                    visitorLogPersonToVisit.setEmailPrimary(jsonObject1.optString("emailPrimary",""));
                    visitorLogPersonToVisit.setEmailAlternate( jsonObject1.optString("emailAlternate",""));
                    visitorLogPersonToVisit.setDepartmentId(jsonObject1.optString("departmentId",""));
                    visitorLogPersonToVisit.setPersonToVisitId((Integer) jsonObject1.get("personToVisitId"));
                    visitorLogPersonToVisit.setVisitorLog(jsonObject1.optString("visitorLog",""));
                    visitorLogPersonToVisits.add(visitorLogPersonToVisit);
                }

                buildingLevelLogoutListResponse.setVisitorLogPersonToVisit(visitorLogPersonToVisits);

                /*buildingLevelLogoutListResponse.setVisitorLogPersonToVisit(new Gson().fromJson(jsonObject.getString("visitorLogPersonToVisit"),
                        new TypeToken<ArrayList<VisitorLogPersonToVisit>>() {
                        }.getType()));*/

                logoutListData.add(buildingLevelLogoutListResponse);
            }

            logoutListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Utilities.isInternetConnected(context)) {
            callLogoutApi(buildingLevelLogoutRequest);
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void callLogoutMethod(BuildingLevelLogoutListResponse buildingLevelLogoutListResponse, final String status) {

        if (status.equalsIgnoreCase("Logout")){
            Utilities.showprogressDialogue(getString(R.string.logging_out), getString(R.string.please_wait), context, false);
        }else {
            Utilities.showprogressDialogue(getString(R.string.logging), getString(R.string.please_wait), context, false);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("visitorLogId", buildingLevelLogoutListResponse.getVisitorLogId());
        map.put("visitorId", buildingLevelLogoutListResponse.getVisitorId());
        map.put("buildingId", buildingLevelLogoutListResponse.getBuildingId());
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        map.put("complexId", profile.getComplexId()!= null ? profile.getComplexId() : 0);
        map.put("employeeId", profile.getEmployeeId() != null ? profile.getEmployeeId() : 0);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        //Log.d("Token",Utilities.getToken(context));
        //Log.d("requestClientDetails",Utilities.requestclientDetails(context).toString());

        Call<ResponseBody> call = ApiUtils.getAPIService().updateBuildingVisitorLog(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    if (response != null) {

                        if (response.code() == 200) {

                            if (response.body() != null) {// positive response
                                String result = response.body().string();
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject object = jsonArray.getJSONObject(0);
                                String message = object.getString("message");
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        callLogoutApi(buildingLevelLogoutRequest);
                                    }
                                };

                                if (status.equalsIgnoreCase("Logout")){
                                    Utilities.showPopuprunnable(context, "Guest Logged Out Successfully", false, runnable);
                                }else {
                                    Utilities.showPopuprunnable(context, "Guest Logged In Successfully", false, runnable);
                                }
                                Utilities.hideProgress();
                            } else {
                                Utilities.showPopup(context, "", getString(R.string.technical_error));
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        callLogoutApi(buildingLevelLogoutRequest);
                                    }
                                };
                            }
                        } else {
                            Utilities.hideProgress();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    callLogoutApi(buildingLevelLogoutRequest);
                                }
                            };
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

    /*@Override
    public void onPrintPass(int visitorId) {
        Log.d("TAG", "Visitor id = " + visitorId);
        Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("visitorId", visitorId);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().callRePringPass(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response != null) {

                    if (response.code() == 200 || response.code() == 201) {

                        String htmlData = "";
                        try {
                            String data = response.body().string().toString();
                            JSONArray jsonArray = new JSONArray(data);
                            JSONObject object = jsonArray.getJSONObject(0);
                            htmlData = object.getString("message");
                            final WebView webView = new WebView(context);
                            webView.setWebViewClient(new WebViewClient(){
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    Utilities.hideProgress();
                                    Utilities.createWebPagePrint(webView, context);

                                }
                            });

                            webView.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                //Utilities.hideProgress();
                                //Utilities.createWebPagePrint(webView, context);
                            }



                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Utilities.hideProgress();
                        try {

                            if (response.errorBody() != null) {


                            }


                        } catch (Exception e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
            }
        });


    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivBackImage_Logout:

                /*try {
                    Utilities.hideKeyboard(context, v);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

                final Dialog builder = new Dialog(context);
                builder.setContentView(R.layout.login_confirmation_dialog);
                builder.setCancelable(true);
                builder.show();
                initConfirmDialog(builder);

                break;

            case R.id.ivReset_Logout:

                try {
                    Utilities.hideKeyboard(context, v);
                    callLogoutApi(buildingLevelLogoutRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.ivSos:
               Utilities.showDialogForSos(BuildingLevelVisitiorLogoutActivity.this);
                break;


            case  R.id.ivUserLogo_BuildingLevel:
                startActivity(new Intent(this,HomeScreenActivity.class));
                break;

        }

    }

    private void initConfirmDialog(final Dialog builder) {
        final EditText etLoginId_confirmId = (EditText) builder.findViewById(R.id.etLoginId_confirmId);
        final EditText etPassword_confirmPassword = (EditText) builder.findViewById(R.id.etPassword_confirmPassword);
        final Button btnConfirm_loginConfirmActivity = (Button) builder.findViewById(R.id.btnConfirm_loginConfirmActivity);
        final Button btnCancel_loginConfirmActivity = (Button) builder.findViewById(R.id.btnCancel_loginConfirmActivity);
        final ImageView ivShowPassword = (ImageView) builder.findViewById(R.id.ivShowPassword);
        final LoginRequest loginRequest = new Gson().fromJson(new SPbean(context).getPreference(Constants.LOGINREQUEST, ""), LoginRequest.class);

        final boolean[] visible = {false};
        ivShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible[0]) {
                    etPassword_confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
                    visible[0] = false;
                } else {
                    etPassword_confirmPassword.setTransformationMethod(null);
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
                    visible[0] = true;
                }
            }
        });


        btnCancel_loginConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.cancel();
            }
        });

        btnConfirm_loginConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean validDetails = true;
                if (etLoginId_confirmId.getText().toString().trim().isEmpty()) {
                    etLoginId_confirmId.setError(getResources().getString(R.string.error_login_id_required));
                    validDetails = false;

                } else if (etPassword_confirmPassword.getText().toString().trim().isEmpty()) {
                    etPassword_confirmPassword.setError(getResources().getString(R.string.password_error));
                    validDetails = false;
                }

                if (validDetails) {
                    callBypass(etLoginId_confirmId.getText().toString().trim(),
                            etPassword_confirmPassword.getText().toString().trim(), builder);
                }
            }
        });
    }


    // Logout call
    private void callBypass(String userName, String password, final Dialog builder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userName);
        map.put("password", password);
        map.put("language", new SPbean(context).getPreference(Constants.LANGUAGE_CODE, ""));
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Utilities.showprogressDialogue(getString(R.string.logging_out), getString(R.string.please_wait), context, false);
        Call<ResponseBody> callToken = ApiUtils.getAPIService().callByPassLogin(Utilities.getToken(context), map);
        callToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Token =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        //clearPreferanceData();
                        builder.dismiss();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(context, getResources().getString(R.string.logged_out_successfully), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        //write log method
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                        e.printStackTrace();
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
                Utilities.hideProgress();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));

            }
        });
    }

    private void clearPreferanceData() {
        try {
            SPbean sPbean = new SPbean(getBaseContext());
            sPbean.removePreference(Constants.MASTER_RESPONSE);
            Log.d("TAG_DESTROY", "Masters creared");
            sPbean.removePreference(Constants.PROFILE_RESPONSE);
            Log.d("TAG_DESTROY", "Profile Cleared");
            sPbean.removePreference(Constants.EMPLOYEE_LIST_RESPONSE);
            Log.d("TAG_DESTROY", "Employee list");
            sPbean.removePreference(Constants.TOKEN);
            Log.d("TAG_DESTROY", "Token");
            sPbean.removePreference(Constants.TENANTLIST_RESPONSE);
            Log.d("TAG_DESTROY", "Tanent list cleared");
            sPbean.removePreference(Constants.DATA_TO_UPLOADED);
            Log.d("TAG_DESTROY", "Data to upload cleared");
            sPbean.removePreference(Constants.SETTINGS);
            Log.d("TAG_DESTROY", "Data to Settings");
            sPbean.removePreference(Constants.BUILDING_LIST);
            Log.d("TAG_DESTROY", "Data to Building List");
            sPbean.removePreference(Constants.BUILDING_ID_LIST);
            Log.d("TAG_DESTROY", "Data to Building Id List");
            sPbean.removePreference(Constants.BUILDINGLIST_RESPONSE);
            Log.d("TAG_DESTROY", "Data to Building List Response");
            sPbean.removePreference(Constants.SELECTED_LEVEL);
            Log.d("TAG_DESTROY", "Data to Building Level Cleared");

        } catch (Exception e) {

        }
    }



    /**
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("TAG", "Search Text  = " + s);
        if (logoutListData != null && !logoutListData.isEmpty()) {
            ArrayList<BuildingLevelLogoutListResponse> temp = new ArrayList<>();
            for (BuildingLevelLogoutListResponse data :
                    logoutListData) {

                if (data.getName() == null){
                    data.setName("");
                }
                /*if (data.getMobile() == null){
                    data.setMobile("");
                }
                if (data.getEmail() == null){
                    data.setEmail("");
                }
                if (data.getAccessCardNo() == null){
                    data.setAccessCardNo("");
                }

                if (data.getPassId() == null){
                    data.setPassId("");
                }*/


                if (context.toString().isEmpty()) {
                    temp = logoutListData;
                } else if (data.getName().toLowerCase().contains(s.toString().toString()) /*||
                        data.getMobile().toLowerCase().contains(s.toString().toString()) ||
                        data.getEmail().toLowerCase().contains(s.toString().toString()) ||
                        data.getAccessCardNo().toLowerCase().contains(s.toString().toString()) ||
                        data.getPassId().toLowerCase().contains(s.toString().toString())*/) {

                    temp.add(data);
                }
            }
            logoutListAdapter.filteredList(temp);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onLoginLogout(final BuildingLevelLogoutListResponse buildingLevelLogoutListResponse, final String status) {
        if (status.equalsIgnoreCase("Logout")){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.confirmLogout);
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callLogoutMethod(buildingLevelLogoutListResponse,status);
                        }
                    }, 200);

                }
            });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();
        }else {
            callLogoutMethod(buildingLevelLogoutListResponse, status);
        }


    }

}
