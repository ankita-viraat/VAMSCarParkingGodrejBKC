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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

public class PreAppointmentActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button preAppointment_btnNext,btnLogin,btnCancelDialog,btnYes;
    ImageView ivShowPassword, ivShowEmail, ivScanner, ivLogo_preAppointmentActivity, ivLogout,ivSos, ivIsdFlag;
    TextView tv_errorMobile, tv_errorEmail, tvMobileEmail,tvHeader,tvAdmin,tvSheriff;
    public EditText etMobileNo, etEmailId;
    CheckBox sheriffCheckbox,adminCheckbox;
    public AutoCompleteTextView actIsdCode;
    public static LinearLayout ll_preAppointmentMobile, ll_preAppointmentEmailId;
    View view_preAppintment, view_guest_mobile;
    private boolean visible;
    Button btnNext;
    public static VisitorLogMobileViewModel visitorLogMobileViewModel;
    private IntentIntegrator qrScan;
    int SCAN_ID = 0;
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
        setContentView(R.layout.activity_pre_appointment);
        context = PreAppointmentActivity.this;

        init();
        callEmployeeList();
    }

    private void init() {

        ll_preAppointmentMobile = (LinearLayout) findViewById(R.id.ll_preAppointmentMobile);
        ll_preAppointmentEmailId = (LinearLayout) findViewById(R.id.ll_preAppointmentEmailId);

        preAppointment_btnNext = (Button) findViewById(R.id.preAppointment_btnNext);
        preAppointment_btnNext.setOnClickListener(this);

        ivShowPassword = (ImageView) findViewById(R.id.ivShowPassword);
        ivShowPassword.setOnClickListener(this);
        ivShowEmail = (ImageView) findViewById(R.id.ivShowEmail);
        ivShowEmail.setVisibility(View.GONE);
        ivShowEmail.setOnClickListener(this);

        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);

        btnNext = (Button) findViewById(R.id.preAppointment_btnNext);
        btnNext.setOnClickListener(this);

        ivScanner = (ImageView) findViewById(R.id.ivPreAppointmentScanner);
        ivScanner.setOnClickListener(this);
        ivIsdFlag = (ImageView) findViewById(R.id.ivIsdFlag);
        etMobileNo = (EditText) findViewById(R.id.etVisitorMobileNoPreAppointment);
        etEmailId = (EditText) findViewById(R.id.etVisitorEmailIdPreAppointment);
        actIsdCode = (AutoCompleteTextView) findViewById(R.id.etVisitorIsdcodePreAppointment);
        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getDefaultIsdCode() != null) {
            actIsdCode.setText("+" + CommonPlaceForOjects.settings.getDefaultIsdCode());
            ivIsdFlag.setImageResource(Utilities.setDrawableFlage(actIsdCode.getText().toString().trim()));
        }
        tv_errorMobile = (TextView) findViewById(R.id.tv_errorMobile);
        view_preAppintment = (View) findViewById(R.id.view_preAppintment);
        view_guest_mobile = (View) findViewById(R.id.view_guest_mobile);
        tvMobileEmail = (TextView) findViewById(R.id.txtLabelMobileEmail);
        tv_errorEmail = (TextView) findViewById(R.id.tv_errorEmail);
        qrScan = new IntentIntegrator(this);
        qrScan.addExtra("SCAN_CANERA_ID", 1);
        ivLogout = (ImageView) findViewById(R.id.preAppointmentLogout);
        ivLogout.setOnClickListener(this);
        ivLogo_preAppointmentActivity = (ImageView) findViewById(R.id.ivLogo_preAppointmentActivity);
        ivLogo_preAppointmentActivity.setOnClickListener(this);
        visitorListsArrayList = new ArrayList<>();
        visitorLogMobileViewDataModel = new VisitorLogMobileViewModel();
        checkMasking();
        checkAuthMobileEmail();
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        if (profile != null && profile.getComplexName() != null) {
            visitorLogMobileViewDataModel.setComplexId((profile.getComplexId()));
            visitorLogMobileViewDataModel.setComplexName(profile.getComplexName());
        }
        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etMobileNo));
        etMobileNo.addTextChangedListener(addLineNumberFormatter);

        Utilities.setUserLogo(context, ivLogo_preAppointmentActivity);
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

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                getAuthenticateVstrBy().equalsIgnoreCase("M")) {
            tvMobileEmail.setText(getString(R.string.entervisitormobile));
        } else {
            tvMobileEmail.setText(getString(R.string.entervisitoremail));
        }

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
        Utilities.addTextChangeListenerForIsd(context, etMobileNo, actIsdCode);
    }

    public void populateIsdCode(Context context, AutoCompleteTextView autoCompleteTextView) {
        final ArrayList<CountryForISD> country = Utilities.getCountryForIsdWithFlag();
        if (country != null) {
            CountryFlagAdapter countryFlagAdapter = new CountryFlagAdapter(context,
                    R.layout.activity_pre_appointment, R.id.country_name_tv, country);
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
                break;


            case R.id.ivPreAppointmentScanner:
                if (LoginActivity.isSelfeHeplKiosk) {
                    qrScan.addExtra("SCAN_CAMERA_ID", Utilities.getFrontCameraId());
                }
                qrScan.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
                SCAN_ID = 1;
                break;

            case R.id.preAppointmentLogout:
                startActivity(new Intent(PreAppointmentActivity.this,
                        HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;

            case R.id.ivShowPassword:
                showPassword();
                break;

            case R.id.ivLogo_preAppointmentActivity:
                Utilities.redirectToHome(context);
                break;

            case R.id.ivSos:
               Utilities.showDialogForSos(PreAppointmentActivity.this);
                break;

            case R.id.ivShowEmail:
                showPassword();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (SCAN_ID) {

            case 1:
                IntentResult scannerResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scannerResult != null) {
                    if (!isClickedOnce) {
                        isClickedOnce = true;
                        callpreAppointment(scannerResult.getContents(), true);
                    } else {
                        preAppointment_btnNext.setEnabled(false);
                    }
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private void callpreAppointment(String contents, boolean isScaned) {
        if (contents != null) {
            Utilities.showprogressDialogue(getString(R.string.fetching_data), "Please wait", PreAppointmentActivity.this, true);
            HashMap<String, Object> map = new HashMap<>();
            contents = contents.replaceAll("\\+", "");
            map.put("searchString", contents);
            map.put("isdCode", null);
            map.put("mobile", null);
            map.put("email", null);
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().getPreAppointment(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Tag", "Response Code For Pre Appointment =" + response.code());
                    if (response.code() == 200 || response.code() == 201) {
                        isClickedOnce = true;

                        try {
                            if (response.body() != null) {
                                String preappointment_Response = response.body().string().toString();
                                if (!preappointment_Response.equals("null")) {

                                    visitorLogMobileViewModel = new VisitorLogMobileViewModel();
                                /*visitorLogMobileViewModel = new Gson().fromJson(preappointment_Response,
                                        VisitorLogMobileViewModel.class);
*/
                                    ArrayList<VisitorLogMobileViewModel> models = new Gson().fromJson(preappointment_Response,
                                            new TypeToken<ArrayList<VisitorLogMobileViewModel>>() {
                                            }.getType());

                                    if (models.size() > 1) {
                                        Utilities.hideProgress();
                                        showSelectionDialogue(models);
                                    } else if (models.size() == 1) {
                                        visitorLogMobileViewModel = new VisitorLogMobileViewModel();
                                        visitorLogMobileViewModel = models.get(0);
                                        Utilities.hideProgress();
                                        Intent intent = new Intent(PreAppointmentActivity.this, CreateAppointmentActivityOne.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else if (models.isEmpty()) {
                                        Utilities.hideProgress();
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                etMobileNo.setText("");
                                            }
                                        };
                                        Utilities.showPopuprunnable(context, getResources().getString(R.string.you_dont_have_appointment_today), true, runnable);
                                    }

                                    /* new SPbean(context).setPreference(Constants.PRE_APPOINTMENT_RESPONSE, preappointment_Response);*/

                                } else {
                                    Utilities.hideProgress();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            etMobileNo.setText("");
                                        }
                                    };
                                    Utilities.showPopuprunnable(context, getResources().getString(R.string.you_dont_have_appointment_today), true, runnable);
                                }

                            }

                        } catch (Exception e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }
                        /*Utilities.hideProgress();*/
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
                    //write log method
                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                    e.printStackTrace();
                    Utilities.hideProgress();
                }
            });
        }


    }

    private void showSelectionDialogue(ArrayList<VisitorLogMobileViewModel> models) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Scheduled Appointment's");
        builder.setView(getSelectingDialogue(models));
        Dialog dialog;
        dialog = builder.create();
        dialog.show();
    }

    private View getSelectingDialogue(ArrayList<VisitorLogMobileViewModel> models) {
        RecyclerView rvSelectionDialogue;
        Button btnSelect;
        RecyclerView.LayoutManager layoutManager;

        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_selection_for_appointment, null, false);
        rvSelectionDialogue = (RecyclerView) view.findViewById(R.id.rvDialogueSelection);
        btnSelect = (Button) view.findViewById(R.id.btnDialogueWalkin);
        btnSelect.setVisibility(View.GONE);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvSelectionDialogue.setLayoutManager(layoutManager);

        SelectionDialogueAdapter selectionDialogueAdapter = new SelectionDialogueAdapter(view.getContext(), models,
                PreAppointmentActivity.this, null, dialog);
        rvSelectionDialogue.setAdapter(selectionDialogueAdapter);

        return view;
    }

    private void onMobileClickNext() {

        if (valid()) {
            clearMobileErrorMessages();
            String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
            String dataToSearch = actIsdCode.getText().toString().trim() + etMobile.trim();
            if (!isClickedOnce) {
                isClickedOnce = true;
                callpreAppointment(dataToSearch, false);
            } else {
                preAppointment_btnNext.setEnabled(false);
            }
        }

    }

    private void onEmailClickNext() {
        if (isValidEmailField()) {
            clearEmailErrorMessages();
            String dataToSearch = etEmailId.getText().toString();
            if (!isClickedOnce) {
                isClickedOnce = true;
                callpreAppointment(dataToSearch, false);
            } else {
                preAppointment_btnNext.setEnabled(false);
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
        if (!Utilities.isValidCountryCode(actIsdCode.getText().toString())) {
            // actIsdCode.setError(getString(R.string.error_isd_invalid));
            tv_errorMobile.setVisibility(View.VISIBLE);
            tv_errorMobile.setText(R.string.error_isd_invalid);  //here can change error message as per requirement
            view_guest_mobile.setBackgroundColor(getResources().getColor(R.color.red_primary));
            if (error == 0) {
                error++;
            }
        }


        if (!etMobile.isEmpty()) {

            if (!Utilities.isValidIsdCodeAndMobileNo(actIsdCode.getText().toString(), etMobile.trim())) {
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
        checkMasking();
        checkAuthMobileEmail();

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

        final BuildingSelectionAdapter[] myAdapter = {new BuildingSelectionAdapter(PreAppointmentActivity.this, 0,
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

                myAdapter[0] = new BuildingSelectionAdapter(PreAppointmentActivity.this, 0,
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


                myAdapter[0] = new BuildingSelectionAdapter(PreAppointmentActivity.this, 0,
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

}