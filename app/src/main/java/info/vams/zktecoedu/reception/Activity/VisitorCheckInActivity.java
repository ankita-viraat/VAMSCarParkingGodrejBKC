package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.vams.zktecoedu.reception.Adapter.BuildingSelectionAdapter;
import info.vams.zktecoedu.reception.Adapter.CountryFlagAdapter;
import info.vams.zktecoedu.reception.Adapter.SelectionDialogueAdapter;
import info.vams.zktecoedu.reception.Model.Building;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RepeatedVisitorAppoinrmtnt;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.SelectedBuilding;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.Model.VisitorLogMobileViewModel;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.BuildingSpinner;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VisitorCheckInActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button preAppointment_btnNext, btnBypass_PreAppointmentActivity,btnCancelDialog,btnYes;
    ImageView ivShowPassword, ivShowEmail, ivLogout, ivLogo_visitorCheckInActivity, ivIsdFlag,ivSos;
    TextView tv_errorMobile, tv_errorEmail, tvMobileEmail,tvHeader,tvAdmin,tvSheriff;
    CheckBox sheriffCheckbox,adminCheckbox;
    public static EditText etMobileNo, etEmailId;
    public static AutoCompleteTextView actIsdCode;
    public static LinearLayout ll_preAppointmentMobile, ll_preAppointmentEmailId;
    View view_preAppintment, view_guest_mobile;
    private boolean visible;

    Dialog dialog;

    public static VisitorLogMobileViewModel visitorLogMobileViewDataModel;
    public static ArrayList<VisitorList> visitorListsArrayList;
    public static boolean GlobalByPass = false;
    public static boolean GlobalRequireAuthenticatiom = true;
    public static int GlobalByPassedBy = 0;
    public static boolean locallyBypassedOnce = false;
    public boolean isClickedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_checkin);
        context = VisitorCheckInActivity.this;

        init();
        callEmployeeList();


    }

    private void init() {

        ll_preAppointmentMobile = (LinearLayout) findViewById(R.id.ll_preAppointmentMobile);
        ll_preAppointmentEmailId = (LinearLayout) findViewById(R.id.ll_preAppointmentEmailId);

        preAppointment_btnNext = (Button) findViewById(R.id.preAppointment_btnNext);
        preAppointment_btnNext.setOnClickListener(this);

        btnBypass_PreAppointmentActivity = (Button) findViewById(R.id.btnBypass_PreAppointmentActivity);
        btnBypass_PreAppointmentActivity.setOnClickListener(this);


        ivShowPassword = (ImageView) findViewById(R.id.ivShowPassword);
        ivShowPassword.setOnClickListener(this);
        ivShowEmail = (ImageView) findViewById(R.id.ivShowEmail);
        ivShowEmail.setVisibility(View.GONE);
        ivShowEmail.setOnClickListener(this);

        ivIsdFlag = (ImageView) findViewById(R.id.ivIsdFlag);

        etMobileNo = (EditText) findViewById(R.id.etVisitorMobileNoPreAppointment);
        etEmailId = (EditText) findViewById(R.id.etVisitorEmailIdPreAppointment);
        actIsdCode = (AutoCompleteTextView) findViewById(R.id.etVisitorIsdcodePreAppointment);

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getDefaultIsdCode() != null) {
            actIsdCode.setText("+" + CommonPlaceForOjects.settings.getDefaultIsdCode());
            ivIsdFlag.setImageResource(Utilities.setDrawableFlage(actIsdCode.getText().toString().trim()));
        }

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etMobileNo));
        etMobileNo.addTextChangedListener(addLineNumberFormatter);

        tv_errorMobile = (TextView) findViewById(R.id.tv_errorMobile);
        view_preAppintment = (View) findViewById(R.id.view_preAppintment);
        view_guest_mobile = (View) findViewById(R.id.view_guest_mobile);
        tv_errorEmail = (TextView) findViewById(R.id.tv_errorEmail);
        tvMobileEmail = (TextView) findViewById(R.id.txtLabelMobileEmail);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);

        ivLogout = (ImageView) findViewById(R.id.preAppointmentLogout);
        ivLogout.setOnClickListener(this);
        ivLogo_visitorCheckInActivity = (ImageView) findViewById(R.id.ivLogo_visitorCheckInActivity);
        ivLogo_visitorCheckInActivity.setOnClickListener(this);
        visitorListsArrayList = new ArrayList<>();
        visitorLogMobileViewDataModel = new VisitorLogMobileViewModel();
        checkMasking();
        checkAuthMobileEmail();
        checkBypassButton();
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        if (profile != null && profile.getComplexName() != null) {
            visitorLogMobileViewDataModel.setComplexId((profile.getComplexId()));
            visitorLogMobileViewDataModel.setComplexName(profile.getComplexName());
        }
        Utilities.setUserLogo(context,ivLogo_visitorCheckInActivity);
        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                clearMobileErrorMessages();
                if (!s.toString().trim().isEmpty()) {
                    view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.black));
                } else {
                    view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.border_color));
                }

                if (actIsdCode.getText().toString().trim().equalsIgnoreCase("+91") ||
                        actIsdCode.getText().toString().trim().equalsIgnoreCase("+1")) {
                    etMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    etMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        actIsdCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    ivIsdFlag.setImageResource(Utilities.setDrawableFlage(s.toString().trim()));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    actIsdCode.append("+");
                }
            }
        });

        populateIsdCode(context, actIsdCode);

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                getAuthenticateVstrBy().equalsIgnoreCase("M")) {
            tvMobileEmail.setText(getString(R.string.entervisitormobile));
        } else {
            tvMobileEmail.setText(getString(R.string.entervisitoremail));
        }


        etEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clearEmailErrorMessages();
                if (!s.toString().trim().isEmpty()) {
                    view_preAppintment.setBackgroundColor(getResources().getColor(R.color.black));
                } else {
                    view_preAppintment.setBackgroundColor(getResources().getColor(R.color.border_color));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Utilities.addTextChangeListenerForIsd(context,etMobileNo,actIsdCode);


    }

    public void populateIsdCode(Context context, AutoCompleteTextView autoCompleteTextView) {
        final ArrayList<CountryForISD> country = Utilities.getCountryForIsdWithFlag();
        if (country != null) {
            CountryFlagAdapter countryFlagAdapter = new CountryFlagAdapter(context,
                    R.layout.activity_visitor_checkin, R.id.country_name_tv, country);
            autoCompleteTextView.setThreshold(2);         //will start working from first character
            autoCompleteTextView.setAdapter(countryFlagAdapter);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        CountryForISD county = (CountryForISD) view.getTag();
                        if (county.getFlag() != -1) {
                            ivIsdFlag.setImageResource(county.getFlag());
                        }
                    }
                }
            });

        }
    }

    private void callEmployeeList() {
        Call<ResponseBody> call = ApiUtils.getAPIService().getEmployeeList(Utilities.getToken(context), (RequestClientDetails) Utilities.requestclientDetails(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Tag", "Response Code For Employee List =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String employeelist_Response = response.body().string().toString();
                            new SPbean(context).setPreference(Constants.EMPLOYEE_LIST_RESPONSE, employeelist_Response);
                            Log.d("Tag", "Employee list updated successfully");
                            callTenantList();

                        } catch (Exception e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.hideProgress();
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

    private void callTenantList() {

        Call<ResponseBody> call = ApiUtils.getAPIService().getTenantList(Utilities.getToken(context),
                (RequestClientDetails) Utilities.requestclientDetails(context));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Tenant List =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String tenantList_Response = response.body().string().toString();
                            new SPbean(context).setPreference(Constants.TENANTLIST_RESPONSE, tenantList_Response);
                            callBuildingList();
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

    private void callBuildingList() {

        Call<ResponseBody> call = ApiUtils.getAPIService().getBuildingList(Utilities.getToken(context),
                (RequestClientDetails) Utilities.requestclientDetails(context));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Building List =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {

                            ArrayList<Building> buildings = null;
                            try {
                                String buildings_response = response.body().string().toString();
                                Log.e("TAG", "Result body :" + buildings_response);
                                Type type = new TypeToken<ArrayList<Building>>() {
                                }.getType();
                                buildings = new Gson().fromJson(buildings_response, type);
                                SPbean sPbean = new SPbean(context);
                                sPbean.setPreference(Constants.BUILDINGLIST_RESPONSE, buildings_response);

                                if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Both") || CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Building")) {
                                    if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Both")) {
                                        if (!LoginActivity.isSelfeHeplKiosk) {
                                            if (sPbean.existPreference(Constants.SELECTED_LEVEL)) {
                                                if (sPbean.getPreference(Constants.SELECTED_LEVEL, "").equalsIgnoreCase("Building")) {
                                                    Intent intent = new Intent(context, BuildingLevelVisitiorLogoutActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                showVisitorEntryDialogue(buildings);
                                            }
                                        }
                                    } else {
                                        showVisitorEntryDialogue(buildings);
                                    }
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.preAppointment_btnNext:
                if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                        getAuthenticateVstrBy().equalsIgnoreCase("M")) {
                    onMobileClickNext();
                } else {
                    onEmailClickNext();
                }
                //onClickNext();
                break;

            case R.id.preAppointmentLogout:
                startActivity(new Intent(VisitorCheckInActivity.this,
                        HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
               /* final Dialog builder = new Dialog(context);
                builder.setContentView(R.layout.login_confirmation_dialog);
                builder.setCancelable(true);
                builder.show();
                initConfirmDialog(builder);*/
                break;


            case R.id.ivShowPassword:
                showPassword();
                break;

            case R.id.ivSos:
             Utilities.showDialogForSos(VisitorCheckInActivity.this);
                break;


            case R.id.ivShowEmail:
                showPassword();
                break;

            case R.id.ivLogo_visitorCheckInActivity:
                Utilities.redirectToHome(context);
                break;


            case R.id.btnBypass_PreAppointmentActivity:
                final Dialog builders = new Dialog(context);
                builders.setContentView(R.layout.login_confirmation_dialog);
                builders.setCancelable(true);
                builders.show();
                initByPassConfirmDialog(builders);
                break;

        }
    }

    private void showPassword() {
        if (visible) {
            etMobileNo.setTransformationMethod(new PasswordTransformationMethod());
            ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
            etEmailId.setTransformationMethod(new PasswordTransformationMethod());
            ivShowEmail.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
            visible = false;
        } else {
            etMobileNo.setTransformationMethod(null);
            ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
            etEmailId.setTransformationMethod(null);
            ivShowEmail.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
            visible = true;
        }
    }

    private void onMobileClickNext() {
        String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
        String Isd = actIsdCode.getText().toString();
        Isd = Isd.replaceAll("\\+", "");
        String dataToSearch = Isd.trim() + etMobile.trim();
        if (valid()) {
            clearMobileErrorMessages();
            if(!isClickedOnce) {
                isClickedOnce  = true;
                callGetRepeatedVisitor(dataToSearch);
            }else{
                preAppointment_btnNext.setEnabled(false);
                btnBypass_PreAppointmentActivity.setEnabled(false);
            }
        }

    }

    private void onEmailClickNext() {
        if (isValidEmailField()) {
            clearEmailErrorMessages();
            String dataToSearch = etEmailId.getText().toString().trim();
            if(!isClickedOnce) {
                isClickedOnce  = true;
                callGetRepeatedVisitor(dataToSearch);
            }else{
                preAppointment_btnNext.setEnabled(false);
                btnBypass_PreAppointmentActivity.setEnabled(false);
            }
        }
    }

    private boolean valid() {
        int error = 0;
        String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
        if (!Utilities.isValidMobileNumber(etMobileNo.getText().toString())) {
            tv_errorMobile.setVisibility(View.VISIBLE);
            tv_errorMobile.setText(R.string.invalidMobile);     //here can change error message as per requirement
            view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.red_primary));
            if (error == 0) {

                error++;
            }
        }
        if (!Utilities.isValidCountryCode(actIsdCode.getText().toString().trim())) {
            // actIsdCode.setError(getString(R.string.error_isd_invalid));
            tv_errorMobile.setVisibility(View.VISIBLE);
            tv_errorMobile.setText(R.string.error_isd_invalid);  //here can change error message as per requirement
            view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.red_primary));
            if (error == 0) {
                error++;
            }
        }


        if (!etMobile.isEmpty()) {

            if (!Utilities.isValidIsdCodeAndMobileNo(actIsdCode.getText().toString().trim(), etMobile.trim())) {
                tv_errorMobile.setVisibility(View.VISIBLE);
                tv_errorMobile.setText(R.string.invalidMobile);  //here can change error message as per requirement
                view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.red_primary));
                if (error == 0) {
                    error++;
                }
            }

        }

        if (etMobile.isEmpty()) {
            tv_errorMobile.setVisibility(View.VISIBLE);
            tv_errorMobile.setText(R.string.mobileRequired);  //here can change error message as per requirement
            view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.red_primary));
            if (error == 0) {
                error++;
            }
        }


        return !(error > 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        locallyBypassedOnce = false;
        GlobalByPass = false;
        GlobalByPassedBy = 0;
        GlobalRequireAuthenticatiom = true;
        isClickedOnce = false;


        Utilities.bindSettings(context);

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getDefaultIsdCode() != null &&
                !CommonPlaceForOjects.settings.getDefaultIsdCode().isEmpty()) {
            //actIsdCode.setText(CommonPlaceForOjects.settings.getDefaultIsdCode());
        }
        checkMasking();
        checkAuthMobileEmail();
        checkBypassButton();
        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                getAuthenticateVstrBy().equalsIgnoreCase("M")) {
            tvMobileEmail.setText(getString(R.string.entervisitormobile));
        } else {
            tvMobileEmail.setText(getString(R.string.entervisitoremail));
        }
    }


    private void checkMasking() {
        try {
            if (CommonPlaceForOjects.settings != null) {

                if (CommonPlaceForOjects.settings.getMaskVstrMobileNo() != null &&
                        !CommonPlaceForOjects.settings.getMaskVstrMobileNo()) {

                    etMobileNo.setTransformationMethod(null);
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility));

                } else if (CommonPlaceForOjects.settings.getMaskVstrMobileNo() != null &&
                        CommonPlaceForOjects.settings.getMaskVstrMobileNo()) {

                    etMobileNo.setTransformationMethod(new PasswordTransformationMethod());
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
                }

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkAuthMobileEmail() {
        try {
            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                    getAuthenticateVstrBy().equalsIgnoreCase("E")) {
                ll_preAppointmentMobile.setVisibility(View.GONE);
                view_guest_mobile.setVisibility(View.GONE);
                tv_errorMobile.setVisibility(View.GONE);
                ll_preAppointmentEmailId.setVisibility(View.VISIBLE);
                view_preAppintment.setVisibility(View.VISIBLE);
            } else {
                ll_preAppointmentEmailId.setVisibility(View.GONE);
                view_preAppintment.setVisibility(View.GONE);
                tv_errorEmail.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkBypassButton() {
        try {
            if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                btnBypass_PreAppointmentActivity.setVisibility(View.GONE);
            }else{
                btnBypass_PreAppointmentActivity.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearMobileErrorMessages() {
        tv_errorMobile.setVisibility(View.GONE);
        view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.black));
    }

    private void clearEmailErrorMessages() {
        tv_errorEmail.setVisibility(View.GONE);
        view_preAppintment.setBackgroundColor(getResources().getColor(R.color.black));
    }

    private boolean isValidEmailField() {
        int error = 0;
        if (etEmailId.getText().toString().isEmpty()) {
            tv_errorEmail.setVisibility(View.VISIBLE);
            tv_errorEmail.setText(R.string.emailidRequired);  //here can change error message as per requirement
            view_preAppintment.setBackgroundColor(getResources().getColor(R.color.red_primary));
            if (error == 0) {
                error++;
            }
        }

        if (!etEmailId.getText().toString().isEmpty()) {
//            String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,7})$";
            if (!Utilities.isValidEmail(etEmailId.getText().toString().trim())) {
                tv_errorEmail.setVisibility(View.VISIBLE);
                tv_errorEmail.setText(R.string.invalid_email);  //here can change error message as per requirement
                view_preAppintment.setBackgroundColor(getResources().getColor(R.color.red_primary));
                if (error == 0) {
                    error++;
                }

            }
        }
        return !(error > 0);
    }

    private void showVisitorEntryDialogue(ArrayList<Building> buildings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(getViewForVisitorEntryDialogue(builder, buildings));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    private View getViewForVisitorEntryDialogue(final AlertDialog.Builder builder, ArrayList<Building> buildings) {
        Button btnSelectBuilding, btnVisitorEntryAtComplex;
        BuildingSpinner spnBuilding;
        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_visitor_entry, null, false);
        btnSelectBuilding = (Button) view.findViewById(R.id.btnSelectBuilding);
        btnVisitorEntryAtComplex = (Button) view.findViewById(R.id.btnVisitorEntryAtComplex);
        spnBuilding = (BuildingSpinner) view.findViewById(R.id.spnBuilding);
        //spnBuilding.setPrompt("Select Buildings");

        ArrayList<SelectedBuilding> selectedBuildings = new ArrayList<>();
        selectedBuildings.add(new SelectedBuilding(0, 0, "Select Buildings"));

        for (Building building :
                buildings) {
            SelectedBuilding selectedBuilding = new SelectedBuilding(building.getBuildingId(), building.getComplexId(), building.getName());
            selectedBuildings.add(selectedBuilding);
        }

        final BuildingSelectionAdapter[] myAdapter = {new BuildingSelectionAdapter(VisitorCheckInActivity.this, 0,
                selectedBuildings)};
        spnBuilding.setAdapter(myAdapter[0]);

        if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Building")) {
            btnVisitorEntryAtComplex.setVisibility(View.GONE);
        }

        spnBuilding.setSpinnerEventsListener(new BuildingSpinner.OnSpinnerEventsListener() {
            @Override
            public void onSpinnerOpened(AppCompatSpinner spinner) {
                //Toast.makeText(context, "Spinner Opened", Toast.LENGTH_SHORT).show();
                ArrayList<SelectedBuilding> selectedBuildingArrayList = myAdapter[0].getSelectedBuildingList();
                selectedBuildingArrayList.get(0).setName("Select Buildings");

                myAdapter[0] = new BuildingSelectionAdapter(VisitorCheckInActivity.this, 0,
                        selectedBuildingArrayList);
                spinner.setAdapter(myAdapter[0]);

            }

            @Override
            public void onSpinnerClosed(AppCompatSpinner spinner) {
                //Toast.makeText(context, "Spinner Closed", Toast.LENGTH_SHORT).show();

                ArrayList<SelectedBuilding> selectedBuildingArrayList = myAdapter[0].getSelectedBuildingList();
                final int selectedCount = myAdapter[0].getSelectedCount();

                if (selectedCount > 1) {
                    selectedBuildingArrayList.get(0).setName(selectedCount + " Buildings Selected");
                } else {
                    for (int i = 1; i < selectedBuildingArrayList.size(); i++) {
                        if (selectedBuildingArrayList.get(i).isSelected()) {
                            if (!selectedBuildingArrayList.get(0).getName().equalsIgnoreCase("Select Buildings")) {
                                selectedBuildingArrayList.get(0).setName(selectedBuildingArrayList.get(0).getName() + ", " + selectedBuildingArrayList.get(i).getName());
                            } else {
                                selectedBuildingArrayList.get(0).setName("" + selectedBuildingArrayList.get(i).getName() + " Selected");
                            }
                        }
                    }
                    //selectedBuildingArrayList.get(0).setName(selectedBuildingArrayList.get(1).getName()+" Selected");
                }


                myAdapter[0] = new BuildingSelectionAdapter(VisitorCheckInActivity.this, 0,
                        selectedBuildingArrayList);
                spinner.setAdapter(myAdapter[0]);

            }
        });

        btnSelectBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*for (int i = 1; i < myAdapter.getSelectedBuildingList().size(); i++) {
                    Log.d("TAG","position : "+i+" isSelected : "+myAdapter.getSelectedBuildingList().get(i).isSelected());
                }*/
                if (myAdapter[0].getSelectedCount() == 0) {
                    Utilities.showPopup(context, "Error", "Select Atleast one building ");
                } else {
                    SPbean sPbean = new SPbean(context);
                    sPbean.setSelectedBuildings(Constants.BUILDING_LIST, myAdapter[0].getSelectedBuildingList());
                    sPbean.setSelectedBuildingIds(Constants.BUILDING_ID_LIST, myAdapter[0].getSelectedBildingIds());
                    sPbean.setPreference(Constants.SELECTED_LEVEL, "Building");

                    if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Both")) {
                        Intent intent = new Intent(context, BuildingLevelVisitiorLogoutActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    dialog.dismiss();
                }
            }
        });

        btnVisitorEntryAtComplex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(context, PreAppointmentActivity.class));
                finish();*/

                SPbean sPbean = new SPbean(context);
                sPbean.setPreference(Constants.SELECTED_LEVEL, "Complex");
                dialog.dismiss();
            }
        });

        return view;
    }

    private void callGetRepeatedVisitor(String dataToSearch) {

        if (Utilities.isInternetConnected(context)) {

            String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
            String Isd = actIsdCode.getText().toString();
            Isd = Isd.replaceAll("\\+", "");
            Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), VisitorCheckInActivity.this, true);
            /*WebRequest webRequest = new WebRequest();
            webRequest.setSearchString(actIsdCodeVisitorMobileActivity.getText().toString() + "" + etVisitorMobileNoVisitorMobileActivity.getText().toString());
            webRequest.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));*/

            HashMap<String, Object> map = new HashMap<>();
            map.put("searchString", dataToSearch);
            map.put("isdCode", Isd.trim());
            map.put("complexId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId() != null ? VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId() : 0);
            map.put("tenantId", VisitorCheckInActivity.visitorLogMobileViewDataModel != null ? VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId() : 0);
            map.put("mobile", etMobile.trim());
            map.put("email", etEmailId.getText().toString().trim());
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().getRepeatedvisitorAppointment(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Tag", "Response Code For Get Repeated Visitor =" + response.code());
                    if (response.code() == 200 || response.code() == 201) {
                        if (response != null) {
                            isClickedOnce = true;
                            try {
                                String getrepeatedvisitor_Response = response.body().string().toString();
                                //if (!getrepeatedvisitor_Response.equals("null")){
                                Log.d("Tag", "Response For Get Repeated Visitor = " + getrepeatedvisitor_Response);

                                RepeatedVisitorAppoinrmtnt repeatedVisitorAppoinrmtnt = new Gson().fromJson(getrepeatedvisitor_Response, RepeatedVisitorAppoinrmtnt.class);
                                if (repeatedVisitorAppoinrmtnt != null) {

                                    if (repeatedVisitorAppoinrmtnt.getVisitorLogMobileViewModels() != null &&
                                            !repeatedVisitorAppoinrmtnt.getVisitorLogMobileViewModels().isEmpty()) {

                                        Utilities.hideProgress();
                                        showSelectionDialogue(repeatedVisitorAppoinrmtnt);

                                    } else if (repeatedVisitorAppoinrmtnt.getVisitorList() != null) {
                                        VisitorList resultData = repeatedVisitorAppoinrmtnt.getVisitorList();
                                        visitorListsArrayList.add(resultData);
                                        visitorLogMobileViewDataModel.setVisitorList(visitorListsArrayList);
                                        Utilities.hideProgress();
                                        Intent i = new Intent(VisitorCheckInActivity.this, VisitorEntryActivityOne.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    } else {
                                        VisitorList resultData = new VisitorList();
                                        if (ll_preAppointmentMobile.getVisibility() == View.VISIBLE) {
                                            String isd = actIsdCode.getText().toString();
                                            isd = isd.replaceAll("\\+", "");
                                            resultData.setIsdCode(isd.trim());
                                            String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
                                            resultData.setMobile(etMobile.trim());
                                        } else {
                                            resultData.setEmail(etEmailId.getText().toString());
                                        }
                                        Utilities.hideProgress();
                                        visitorListsArrayList.add(resultData);
                                        Intent i = new Intent(VisitorCheckInActivity.this, VisitorEntryActivityOne.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                }

                                /*}else {

                                    Intent i = new Intent(VisitorComplexMobileNoActivity.this, VisitorEntryActivityOne.class);
                                    startActivity(i);
                                }*/

                            } catch (Exception e) {
                                //write log method
                                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                                e.printStackTrace();
                            }
                        } else {
                            Utilities.showPopup(context, "", "Response is null");
                            Utilities.hideProgress();
                        }
                    } else {
                        Utilities.hideProgress();
                        isClickedOnce = false;
                        try {
                            if (response.errorBody() != null) {
                                String errorMsg = response.errorBody().string().toString();
                                String error = "";
                                if (errorMsg.trim().charAt(0) == '[') {
                                    Log.d("TAG", "JSONArray");
                                    JSONArray jsonArray = new JSONArray(errorMsg);
                                    if (jsonArray != null) {
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        error = object.getString("message");
                                        Utilities.showPopup(context, "", "" + error);
                                    }
                                } else if (errorMsg.trim().charAt(0) == '{') {
                                    Log.d("TAG", "JSONObject");
                                    JSONObject object = new JSONObject(errorMsg);
                                    error = object.getString("message");
                                    Utilities.showPopup(context, "", "" + error);
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
                    isClickedOnce = false;
                    Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                    Utilities.hideProgress();
                    //write log method
                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                    e.printStackTrace();
                }
            });
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }

    private void showSelectionDialogue(RepeatedVisitorAppoinrmtnt repeatedVisitorAppoinrmtnt) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.scheduled_Appointment));
            builder.setView(getSelectingDialogue(repeatedVisitorAppoinrmtnt));

            dialog = builder.create();
            dialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private View getSelectingDialogue(final RepeatedVisitorAppoinrmtnt repeatedVisitorAppoinrmtnt) {
        RecyclerView rvSelectionDialogue;
        Button btnSelectWalkIn;
        RecyclerView.LayoutManager layoutManager;

        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_selection_for_appointment, null, false);
        rvSelectionDialogue = (RecyclerView) view.findViewById(R.id.rvDialogueSelection);
        btnSelectWalkIn = (Button) view.findViewById(R.id.btnDialogueWalkin);

        btnSelectWalkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (repeatedVisitorAppoinrmtnt.getVisitorList() != null) {

                    VisitorCheckInActivity.visitorListsArrayList.add(repeatedVisitorAppoinrmtnt.getVisitorList());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("isdCode", repeatedVisitorAppoinrmtnt.getVisitorList().getIsdCode());
                    map.put("mobile", repeatedVisitorAppoinrmtnt.getVisitorList().getMobile());
                    map.put("email", repeatedVisitorAppoinrmtnt.getVisitorList().getEmail());
                    map.put("requestClientDetails", Utilities.requestclientDetails(context));
                    VisitorEntryActivityOne.mathodCallToReSendOtp(map, context);


                    Intent i = new Intent(VisitorCheckInActivity.this, VisitorEntryActivityOne.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                } else {
                    HashMap<String, Object> map = new HashMap<>();
                    String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
                    String Isd = actIsdCode.getText().toString();
                    Isd = Isd.replaceAll("\\+", "");
                    map.put("isdCode", Isd.trim());
                    map.put("mobile", etMobile.trim());
                    map.put("email", etEmailId.getText().toString().trim());
                    map.put("requestClientDetails", Utilities.requestclientDetails(context));
                    VisitorEntryActivityOne.mathodCallToReSendOtp(map, context);

                    Intent i = new Intent(VisitorCheckInActivity.this, VisitorEntryActivityOne.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

            }
        });
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvSelectionDialogue.setLayoutManager(layoutManager);

        SelectionDialogueAdapter selectionDialogueAdapter = new SelectionDialogueAdapter(view.getContext(), repeatedVisitorAppoinrmtnt.getVisitorLogMobileViewModels(),
                null, VisitorCheckInActivity.this, dialog);
        rvSelectionDialogue.setAdapter(selectionDialogueAdapter);

        return view;
    }

    private void initByPassConfirmDialog(final Dialog builder) {
        final EditText etLoginId_confirmId = (EditText) builder.findViewById(R.id.etLoginId_confirmId);
        final EditText etPassword_confirmPassword = (EditText) builder.findViewById(R.id.etPassword_confirmPassword);
        final Button btnConfirm_loginConfirmActivity = (Button) builder.findViewById(R.id.btnConfirm_loginConfirmActivity);
        final Button btnCancel_loginConfirmActivity = (Button) builder.findViewById(R.id.btnCancel_loginConfirmActivity);
        final ImageView ivShowPassword = (ImageView) builder.findViewById(R.id.ivShowPassword);

        btnCancel_loginConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.cancel();
            }
        });

        ivShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visible) {
                    etPassword_confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
                    visible = false;
                } else {
                    etPassword_confirmPassword.setTransformationMethod(null);
                    ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
                    visible = true;
                }
            }
        });

        btnConfirm_loginConfirmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (byPassValidation(etLoginId_confirmId, etPassword_confirmPassword)) {

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", etLoginId_confirmId.getText().toString().trim());
                    map.put("password", etPassword_confirmPassword.getText().toString().trim());
                    map.put("language", etPassword_confirmPassword.getText().toString().trim());
                    map.put("requestClientDetails", Utilities.requestclientDetails(context));

                    Utilities.showprogressDialogue("", getString(R.string.please_wait), context, false);
                    callByPassLogin(map, builder);
                }
            }
        });
    }

    private boolean byPassValidation(EditText etLoginId_confirmId, EditText etPassword_confirmPassword) {
        int error = 0;
        if (etLoginId_confirmId.getText().toString().isEmpty()) {
            etLoginId_confirmId.setError(getString(R.string.error_login_id_required));
            if (error == 0) {
                etLoginId_confirmId.requestFocus();
                error++;
            }
        }

        if (etPassword_confirmPassword.getText().toString().isEmpty()) {
            etPassword_confirmPassword.setError(getString(R.string.error_password_required));
            if (error == 0) {
                etPassword_confirmPassword.requestFocus();
                error++;
            }
        }

        return !(error > 0);

    }

    private void callByPassLogin(HashMap<String, Object> map, final Dialog builder) {
        Call<ResponseBody> call = ApiUtils.getAPIService().callByPassLogin(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    try {
                        String result = response.body().string().toString();
                        Profile profile = new Gson().fromJson(result, Profile.class);
                        if (profile != null) {

                            GlobalByPass = true;
                            GlobalRequireAuthenticatiom = false;
                            GlobalByPassedBy = profile.getEmployeeId();

                            VisitorList resultData = new VisitorList();
                            if (ll_preAppointmentMobile.getVisibility() == View.VISIBLE) {
                                //resultData.sactIsdCode(actIsdCodeVisitorMobileActivity.getText().toString());
                                resultData.setMobile(etMobileNo.getText().toString());
                            } else {
                                resultData.setEmail(etEmailId.getText().toString());
                            }
                            visitorListsArrayList.add(resultData);

                            startActivity(new Intent(VisitorCheckInActivity.this, VisitorEntryActivityOne.class));

                            builder.dismiss();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utilities.hideProgress();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));

            }
        });
    }


}