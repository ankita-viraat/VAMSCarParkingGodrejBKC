package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckInActivity;
import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckOutActivity;
import info.vams.zktecoedu.reception.Adapter.LogoutListAdapter;
import info.vams.zktecoedu.reception.Adapter.SexualOffenderAdapter;
import info.vams.zktecoedu.reception.Interface.OnLogoutOrPrint;
import info.vams.zktecoedu.reception.Model.ContractorLoginLogout;
import info.vams.zktecoedu.reception.Model.LoginRequest;
import info.vams.zktecoedu.reception.Model.LogoutListData;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.Settings;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.Model.SexualOffendedList;
import info.vams.zktecoedu.reception.Model.WebRequest;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.PrinterModelInfo;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeScreenActivity extends AppCompatActivity implements View.OnClickListener, OnLogoutOrPrint {

    Context context;
    public LinearLayout llScheduleAppointment, llVisitorBadge,
            llChildPickup, llVisitorCheckIn, llVisitorCheckOut,
            llContractorCheckInOut,llparkCheckin,llparkcheckout;
    private ImageView ivLogout, ivUserLogo_HomeSceenActivity, imageViewSetting;
    Button btnCancelDialog,btnYes;
    private IntentIntegrator qrScan;
    ImageView ivSos;
    CheckBox sheriffCheckbox,adminCheckbox;
    TextView tvHeader,tvAdmin,tvSheriff;
    int SCAN_ID = 0;
    AlertDialog dialogContractor;
    private long mLastClickTime = 0;
    private String mLastContails = "";
    private boolean visible;
    Dialog dialog;
    AlertDialog alertDialog;

    ArrayList<SexualOffend> sexualOffends = null;

    ArrayList<LogoutListData> logoutListData = null;
    LogoutListAdapter logoutListAdapter;
    OnLogoutOrPrint onLogoutOrPrint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        init();
        SPbean sPbean = new SPbean(context);
//        if ("".equalsIgnoreCase(sPbean.getPreference(Constants.MASTER_RESPONSE, "")) ||
//                "".equalsIgnoreCase(sPbean.getPreference(Constants.PROFILE_RESPONSE, "")) ||
//                "".equalsIgnoreCase(sPbean.getPreference(Constants.EMPLOYEE_LIST_RESPONSE, "")) ||
//                "".equalsIgnoreCase(sPbean.getPreference(Constants.TENANTLIST_RESPONSE, "")) ||
//                "".equalsIgnoreCase(sPbean.getPreference(Constants.SETTINGS, ""))
//        ) {
//            callMaster();
//        }

        if (sPbean.getPreference(Constants.MASTER_RESPONSE, "").isEmpty() ||
                (sPbean.getPreference(Constants.PROFILE_RESPONSE, "")).isEmpty() ||
                (sPbean.getPreference(Constants.EMPLOYEE_LIST_RESPONSE, "")).isEmpty() ||
               (sPbean.getPreference(Constants.TENANTLIST_RESPONSE, "")).isEmpty() ||
                (sPbean.getPreference(Constants.SETTINGS, "")).isEmpty()) {
            callMaster();
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void init() {
        context = HomeScreenActivity.this;
        llScheduleAppointment = findViewById(R.id.llScheduleAppointment);
        llScheduleAppointment.setOnClickListener(this);
        llChildPickup = findViewById(R.id.llChildPickup);
        llChildPickup.setOnClickListener(this);
        llVisitorCheckIn = findViewById(R.id.llVisitorCheckIn);
        llVisitorCheckIn.setOnClickListener(this);
        llVisitorBadge = findViewById(R.id.llVisitorBadge);
        llVisitorBadge.setOnClickListener(this);
        llContractorCheckInOut = findViewById(R.id.llContractorCheckInOut);
        llContractorCheckInOut.setOnClickListener(this);
        llVisitorCheckOut = findViewById(R.id.llVisitorCheckOut);
        llVisitorCheckOut.setOnClickListener(this);
        imageViewSetting = (ImageView) findViewById(R.id.imageViewSetting);
        imageViewSetting.setOnClickListener(this);
        imageViewSetting.setVisibility(View.GONE);
        ivUserLogo_HomeSceenActivity = (ImageView) findViewById(R.id.ivUserLogo_HomeSceenActivity);
        ivLogout = (ImageView) findViewById(R.id.homeScreenLogout);
        ivLogout.setOnClickListener(this);
        qrScan = new IntentIntegrator(this);
        qrScan.addExtra("SCAN_CANERA_ID", 1);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);


        llparkcheckout = findViewById(R.id.llparkcheckout);
        llparkcheckout.setOnClickListener(this);
        llparkCheckin = findViewById(R.id.llparkCheckin);
        llparkCheckin.setOnClickListener(this);

        Utilities.setUserLogo(context, ivUserLogo_HomeSceenActivity);
        setPreferences();
        //imageViewSetting.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llparkcheckout:
                startActivity(new Intent(context, ParkingCheckOutActivity.class));
                break;
            case R.id.llparkCheckin:
                startActivity(new Intent(context, ParkingCheckInActivity.class));
                break;

            case R.id.llVisitorCheckIn:
                startActivity(new Intent(context, VisitorCheckInActivity.class));
                break;

            case R.id.imageViewSetting:
                startActivity(new Intent(context, Activity_Settings.class));
                break;

            case R.id.llScheduleAppointment:
                startActivity(new Intent(context, PreAppointmentActivity.class));
                break;

            case R.id.llChildPickup:
                startActivity(new Intent(this, ChildPickupActivity.class));
                break;

            case R.id.ivSos:
               Utilities.showDialogForSos(HomeScreenActivity.this);
                break;

            case R.id.homeScreenLogout:
                final Dialog builder = new Dialog(context);
                builder.setContentView(R.layout.login_confirmation_dialog);
                builder.setCancelable(true);
                builder.show();
                initConfirmDialog(builder);
                break;

            case R.id.llVisitorBadge:
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_badge_search);
                dialog.setCancelable(true);
                dialog.show();
                showVisitorBadgeDialog(dialog);
                break;

            case R.id.llContractorCheckInOut:
                if (LoginActivity.isSelfeHeplKiosk) {
                    qrScan.addExtra("SCAN_CAMERA_ID", Utilities.getFrontCameraId());
                }
                qrScan.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
                SCAN_ID = 1;
                break;

            case R.id.llVisitorCheckOut:
                if (LoginActivity.isSelfeHeplKiosk) {
                    qrScan.addExtra("SCAN_CAMERA_ID", Utilities.getFrontCameraId());
                    qrScan.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
                    SCAN_ID = 2;
                } else {
                    startActivity(new Intent(HomeScreenActivity.this, LogoutListActvity.class));
                }

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (SCAN_ID) {

            case 1:
                IntentResult contractlogoutResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (contractlogoutResult != null) {
                    callGetContractVisitorLogout(contractlogoutResult.getContents());
                }
                break;

            case 2:
                IntentResult logoutresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (logoutresult != null) {
                    callVisitorLogout(logoutresult.getContents());
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
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

    private void callBypass(String userName, String password, final Dialog builder) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userName);
        map.put("password", password);
        map.put("language", new SPbean(context).getPreference(Constants.LANGUAGE_CODE, ""));
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Utilities.showprogressDialogue(getString(R.string.signing_out), getString(R.string.please_wait), context, false);
        Call<ResponseBody> callToken = ApiUtils.getAPIService().callByPassLogin(Utilities.getToken(context), map);
        callToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Token =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        clearPreferanceData();
                        builder.dismiss();
                        Intent intent = new Intent(context, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(context, getResources().getString(R.string.sign_out_successfully), Toast.LENGTH_SHORT).show();
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

    private void callGetContractVisitorLogout(final String contents) {

      /*  if (mLastContails.equals(contents) && SystemClock.elapsedRealtime() - mLastClickTime <
                CommonPlaceForOjects.settings.getTimeGapBetweenScanInMinutes() * 60 * 1000) {
            Toast.makeText(context, getString(R.string.toast_one) + " " + CommonPlaceForOjects.settings.getTimeGapBetweenScanInMinutes() + " " + getString(R.string.minutes), Toast.LENGTH_LONG).show();
            return;
        }
        mLastContails = contents;
        mLastClickTime = SystemClock.elapsedRealtime();*/


        Log.d("TAG", "content = " + contents);
        if (contents != null) {
            WebRequest webRequest = new WebRequest();
            webRequest.setSearchString(contents);
            webRequest.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));
            Log.d("Tag", "contractor visitor web request =" + new Gson().toJson(webRequest));
            Utilities.showprogressDialogue(getString(R.string.fetching_data), "", context, false);
            Call<ResponseBody> call = ApiUtils.getAPIService().getContractVisitorLogout(Utilities.getToken(context), webRequest);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    // Log.d("Tag", "Response =" + new Gson().toJson(response));
                    Utilities.hideProgress();
                    if (response.code() == 200 || response.code() == 201) {
                        Utilities.hideProgress();
                        try {
                            if (response.body() != null) {
                                String contractvisitorlogout_Response = response.body().string().toString();
                                Log.d("TAG", "response = " + contractvisitorlogout_Response);
                                ContractorLoginLogout contractorLoginLogout = new Gson().fromJson(
                                        contractvisitorlogout_Response, ContractorLoginLogout.class);

                                if (contractorLoginLogout != null) {
                                    showContractorDialogue(contractorLoginLogout);
                                }
                            }

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
                                String contractvisitorlogout = response.errorBody().string().toString();
                                JSONObject object = new JSONObject(contractvisitorlogout);
                                String temp = new Gson().toJson(object);
                                Log.d("TAG", "error response = " + contractvisitorlogout);

                                if (object != null) {
                                    if (object.getString("message") != null) {
                                        Utilities.showPopup(context, "", object.getString("message"));
                                    } else {
                                        Utilities.showPopup(context, "", object.getString("key"));
                                    }
                                }
                            }

                        } catch (Exception e) {
                            //write log method
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
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
                    Utilities.hideProgress();
                }
            });
        }
    }

    private void showContractorDialogue(ContractorLoginLogout contractorMain) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(showTheContractorView(contractorMain, builder));
        builder.setCancelable(false);
        dialogContractor = builder.create();
        dialogContractor.show();

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialogContractor.isShowing()) {
                    dialogContractor.dismiss();
                }
            }
        }, 20000);*/
    }

    private View showTheContractorView(final ContractorLoginLogout contractorLoginLogout, final AlertDialog.Builder builder) {

        TextView tvContractorName, tvName, tvMobileNo, tvEmail, tvStartTime, tvEndTime, tvStatus, tvTitle, tvShiftTime;
        CircleImageView cvContractorImage;
        Button btnCancel, btnCheckInOut;
        ImageView ivCancel;
        LinearLayout llContractorShiftTime;
        View view = LayoutInflater.from(context).inflate(R.layout.contractor_details_dialogue, null, false);
        tvContractorName = (TextView) view.findViewById(R.id.tvContractorName);
        tvName = (TextView) view.findViewById(R.id.tvContractorFullName);
        tvEmail = (TextView) view.findViewById(R.id.tvContractorEmail);
        tvMobileNo = (TextView) view.findViewById(R.id.tvContractorMobileNo);
        tvStatus = (TextView) view.findViewById(R.id.tvContractorStatus);
        tvStartTime = (TextView) view.findViewById(R.id.tvContractorShiftStartTime);
        tvEndTime = (TextView) view.findViewById(R.id.tvContractorShiftEndTime);
        tvTitle = (TextView) view.findViewById(R.id.tvContractorTitle);
        ivCancel = (ImageView) view.findViewById(R.id.ivContractorCancel);
        tvShiftTime = (TextView) view.findViewById(R.id.tvContractorShiftTime);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCheckInOut = (Button) view.findViewById(R.id.btnCheckIn);
        llContractorShiftTime = (LinearLayout) view.findViewById(R.id.llContractorShiftTime);
        cvContractorImage = (CircleImageView) view.findViewById(R.id.cvContractorImage);
        tvTitle.setText(getResources().getString(R.string.contractor_entry));
        btnCheckInOut.setText(contractorLoginLogout.getCheckInStatus() != null ? contractorLoginLogout.getCheckInStatus() : "");

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCheckInOutAndCancel(contractorLoginLogout, false);
            }
        });

        btnCheckInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contractorLoginLogout.getCheckInStatus() != null && contractorLoginLogout.getCheckInStatus().equalsIgnoreCase("CheckIn")) {
                    if (contractorLoginLogout.getLastName() != null && !contractorLoginLogout.getLastName().isEmpty()) {
                        String firstLastName = contractorLoginLogout.getFirstName() + " " + contractorLoginLogout.getLastName();
                        CallSexualOffender(firstLastName.trim(), contractorLoginLogout, true);
                    } else {
                        CallSexualOffender(contractorLoginLogout.getFirstName(), contractorLoginLogout, true);

                    }
                } else {
                    callCheckInOutAndCancel(contractorLoginLogout, true);
                }
            }
        });

        if (contractorLoginLogout.getImageUrl() != null && !contractorLoginLogout.getImageUrl().isEmpty()) {

            PicassoTrustAllCerificate.getInstance(context).load(contractorLoginLogout.getImageUrl()).
                    error(context.getResources().getDrawable(R.drawable.profile)).
                    placeholder(context.getResources().getDrawable(R.drawable.loading)).
                    into(cvContractorImage);
        }


        if (contractorLoginLogout.getFirstName() == null) {
            contractorLoginLogout.setFirstName("");
        }
        if (contractorLoginLogout.getLastName() == null) {
            contractorLoginLogout.setLastName("");
        }
        if (contractorLoginLogout.getEmailPrimary() == null) {
            contractorLoginLogout.setEmailPrimary("");
        }
        if (contractorLoginLogout.getMobilePrimary() == null) {
            contractorLoginLogout.setMobilePrimary("");
        }
        if (contractorLoginLogout.getIsdCode() == null) {
            contractorLoginLogout.setIsdCode("");
        }

        if (contractorLoginLogout.getCompanyName() != null && contractorLoginLogout.getCompanyName() != null
                && !contractorLoginLogout.getCompanyName().isEmpty()) {
            tvContractorName.setText(contractorLoginLogout.getCompanyName());
        }
        tvName.setText(contractorLoginLogout.getFirstName() + " " + contractorLoginLogout.getLastName());
        tvMobileNo.setText(contractorLoginLogout.getIsdCode() + " " + contractorLoginLogout.getMobilePrimary());
        tvEmail.setText(contractorLoginLogout.getEmailPrimary());
        //tvStatus.setText(key);
        tvStartTime.setText(getFormatedDate(contractorLoginLogout.getCardValidityStartDate(),
                CommonPlaceForOjects.settings.getDateTimeDisplayFormat()));
        Log.d("TAG", "DateTimeFormat = " + CommonPlaceForOjects.settings.getDateTimeDisplayFormat());

        tvEndTime.setText(getFormatedDate(contractorLoginLogout.getCardValidityExpiryDate(),
                CommonPlaceForOjects.settings.getDateTimeDisplayFormat()));


        if (contractorLoginLogout.getShiftStartTime() != null) {
            llContractorShiftTime.setVisibility(View.VISIBLE);

            tvShiftTime.setText(getFormatedTime(contractorLoginLogout.getShiftStartTime().toString(), "hh:mm aa") + " - " +
                    getFormatedTime(contractorLoginLogout.getShiftEndTime().toString(), "hh:mm aa"));
        }

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogContractor.dismiss();
            }
        });


        return view;
    }

    public String getFormatedDate(String strDate, String requiredFormat) {
        try {
            Date date = null;
            date = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.parse(strDate);
            requiredFormat = requiredFormat.replaceAll("h", "").
                    replaceAll("m", "").
                    replaceAll("s", "").
                    replaceAll("H", "").
                    replaceAll(":", "").
                    replaceAll("tt", "");

            Log.d("TAG", "converted object = " + requiredFormat);

            SimpleDateFormat DateTime = new SimpleDateFormat(requiredFormat, Locale.ENGLISH);
            /*SimpleDateFormat DateTime = new SimpleDateFormat("dd MMM yyyy hh:mm aa", Locale.ENGLISH);*/

            Log.d("TAG", "Date Time Format = " + DateTime.format(date));

            return DateTime.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getFormatedTime(String strDate, String requiredFormat) {
        try {
            Date date = null;
            Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
            date = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.parse(strDate);

            Log.d("TAG", "converted object = " + requiredFormat);

            SimpleDateFormat DateTime = new SimpleDateFormat(requiredFormat, Locale.ENGLISH);
            DateTime.setTimeZone(TimeZone.getTimeZone("GMT"+(profile.getTimeZone().startsWith("-")?profile.getTimeZone().substring(0,6):"+"+profile.getTimeZone().substring(0,5))));
            /*SimpleDateFormat DateTime = new SimpleDateFormat("dd MMM yyyy hh:mm aa", Locale.ENGLISH);*/


            return DateTime.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void callVisitorLogout(String barCodeContent) {

        if (barCodeContent != null) {

            Log.d("TAG", "Bar code content = " + barCodeContent);
            HashMap<String, Object> map = new HashMap<>();
            map.put("searchString", barCodeContent);
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Utilities.showprogressDialogue(getString(R.string.fetching_data), "", context, false);
            Call<ResponseBody> call = ApiUtils.getAPIService().getVisitorLogout(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utilities.hideProgress();
                    Log.d("Tag", "Response Code For Visitor Logout =" + response.code());
                    if (response.code() == 200 || response.code() == 201) {

                        try {
                            if (response.body() != null) {
                                String visitorlogout_Response = response.body().string().toString();
                                /*visitorlogout_Response = visitorlogout_Response.substring(1, visitorlogout_Response.length() - 2);
                                visitorlogout_Response.replaceAll("\"", "");
                                Utilities.showPopup(context, "", visitorlogout_Response);
                                Log.d("Tag", "Visitor Logout response =" + visitorlogout_Response);*/
                                JSONArray jsonArray = new JSONArray(visitorlogout_Response);
                                JSONObject object = jsonArray.getJSONObject(0);
                                String msg = object.getString("message");

                                Utilities.showPopup(context, "", "" + msg);
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
                    Utilities.hideProgress();
                }
            });
        }
    }

    private void clearPreferanceData() {
        try {
            SPbean sPbean = new SPbean(getBaseContext());
            sPbean.removePreference(Constants.MASTER_RESPONSE);
            Log.d("TAG_DESTROY", "Masters creared");
            sPbean.removePreference(Constants.PROFILE_RESPONSE);
            Log.d("TAG_DESTROY", "Profile Cleared");
            sPbean.removePreference(Constants.STUDENTLIST_RESPONSE);
            Log.d("TAG_DESTROY", "Students cleared");
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

    private void callMaster() {

        Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), context, true);

        Call<ResponseBody> call = ApiUtils.getAPIService().getMasters(Utilities.getToken(context), (RequestClientDetails) Utilities.requestclientDetails(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Tag", "Response Code For Masters =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {

                            String master_Response = response.body().string().toString();
                            new SPbean(context).setPreference(Constants.MASTER_RESPONSE, master_Response);
                            Log.d("Tag", "Master updated successfully");

                            // MAke call to Profile masters
                            callSettings();

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

    private void showVisitorBadgeDialog(final Dialog builder) {
        final EditText etVisitorBagdge = (EditText) builder.findViewById(R.id.etPreAppointmentVisitorBadge);
        final TextView tvNoDataFound = (TextView) builder.findViewById(R.id.tvNoDataFound);
        final RecyclerView rv_visitorBadgelist = (RecyclerView) builder.findViewById(R.id.rv_visitorBadgelist);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv_visitorBadgelist.setLayoutManager(layoutManager);
        Button btnSearchPrintVisitorBadge = (Button) builder.findViewById(R.id.btnSearchPrintVisitorBadge);

        if (CommonPlaceForOjects.settings != null &&
                CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")) {
            etVisitorBagdge.setHint(getString(R.string.visitor_name_mobile_access_card_no));
        } else {
            etVisitorBagdge.setHint(getString(R.string.visitor_name_email_access_card_no));

        }


        btnSearchPrintVisitorBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String visitorBadgeNameMobileEmail = etVisitorBagdge.getText().toString();
                if (logoutListData != null && !logoutListData.isEmpty()) {
                    logoutListData.clear();
                    if (logoutListAdapter != null) {
                        logoutListAdapter.notifyDataSetChanged();
                    }
                }
                etVisitorBagdge.setText("");
                if (!visitorBadgeNameMobileEmail.isEmpty()) {
                    callVisitorBadgeList(visitorBadgeNameMobileEmail, rv_visitorBadgelist, tvNoDataFound);
                }

            }
        });

    }

    public void callVisitorBadgeList(final String visitorNameEmailMobile, final RecyclerView rv_visitorBadge, final TextView tvNoDataFound) {
        if (Utilities.isInternetConnected(context)) {
            Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), context, false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("searchString", visitorNameEmailMobile);
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().callVisitorBadgeList(map, Utilities.getToken(context));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Tag", "Response Code For Get Repeated Visitor =" + response.code());
                    Utilities.hideProgress();
                    if (response.code() == 200 || response.code() == 201) {
                        if (response != null) {
                            tvNoDataFound.setVisibility(View.GONE);
                            rv_visitorBadge.setVisibility(View.VISIBLE);

                            try {
                                String visitorBadgeListDate = response.body().string().toString();
                                Log.d("TAG", "response = " + visitorBadgeListDate);
                                logoutListData = new ArrayList<>();
                                logoutListData = new Gson().fromJson(visitorBadgeListDate,
                                        new TypeToken<ArrayList<LogoutListData>>() {
                                        }.getType());

                                if (!logoutListData.isEmpty()) {
                                    logoutListAdapter = new LogoutListAdapter(context, logoutListData, onLogoutOrPrint, false);
                                    rv_visitorBadge.setAdapter(logoutListAdapter);
                                    logoutListAdapter.notifyDataSetChanged();
                                } else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    rv_visitorBadge.setVisibility(View.GONE);
                                }


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } else {
                            tvNoDataFound.setVisibility(View.VISIBLE);
                            rv_visitorBadge.setVisibility(View.GONE);
                            Utilities.showPopup(context, "", "Response is null");
                            Utilities.hideProgress();
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
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utilities.showPopup(context, "", getString(R.string.no_internet_msg_text));
                    Utilities.hideProgress();

                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE, "Line no: "
                            + t.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                    t.printStackTrace();
                }
            });

        } else {
            Utilities.showNoInternetPopUp(context);
        }

    }


    // call to settings
    private void callSettings() {
        Call<ResponseBody> call = ApiUtils.getAPIService().getSettings(Utilities.getToken(context),
                (RequestClientDetails) Utilities.requestclientDetails(context));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        String result = response.body().string().toString();
                        new SPbean(context).setPreference(Constants.SETTINGS, result);
                        Log.i("TAG", "Settings = " + new SPbean(context).getPreference(Constants.SETTINGS, ""));
                        CommonPlaceForOjects.settings = new Settings();
                        CommonPlaceForOjects.settings = new Gson().fromJson(result, Settings.class);
                        checkPrintVisitorBadge();
                        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getPrintPass()) {
                            callPrinterSettings();
                            imageViewSetting.setVisibility(View.VISIBLE);
                        }else{
                            imageViewSetting.setVisibility(View.GONE);
                        }
                    } catch (IOException e) {
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
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                try {
                    Utilities.hideProgress();
                    Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                    //write log method
                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                    e.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void checkPrintVisitorBadge() {
        try {
            if (CommonPlaceForOjects.settings != null &&
                    CommonPlaceForOjects.settings.getGenerateSoftPass()) {
                llVisitorBadge.setVisibility(View.VISIBLE);
            } else {
                llVisitorBadge.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utilities.bindSettings(context);
        checkPrintVisitorBadge();
    }

    @Override
    public void onBackPressed() {
        clearPreferanceData();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    public void onLogout(int visitorId) {
        callSoftPass(visitorId);
    }

    @Override
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
                            /*JSONArray jsonArray = new JSONArray(data);
                            JSONObject object = jsonArray.getJSONObject(0);
                            htmlData = object.getString("message");*/
                            final WebView webView = new WebView(context);
                            webView.loadData(data, "text/html; charset=utf-8", "UTF-8");
                            webView.setWebViewClient(new WebViewClient() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    Utilities.hideProgress();
                                    Utilities.createWebPagePrint(webView, context);

                                }
                            });

                            //webView.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                //Utilities.hideProgress();
                                //Utilities.createWebPagePrint(webView, context);
                            }


                        } catch (IOException e) {
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

    }

    public void callSoftPass(int visitorId) {

        if (Utilities.isInternetConnected(context)) {
            Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), context, false);
            HashMap<String, Object> map = new HashMap<>();
            map.put("visitorId", visitorId);
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().callsoftPass(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("Tag", "Response Code For Get Soft Pass =" + response.code());
                    Utilities.hideProgress();
                    if (response.code() == 200 || response.code() == 201) {
                        if (response != null) {

                            String msg = null;
                            String key = null;

                            try {
                                String data = response.body().string().toString();

                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                //key = jsonObject.getString("key");
                                msg = jsonObject.getString("message");
                                msg = msg.replace("Pass", "Badge");

                                Utilities.showPopup(context, "", msg);


                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


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
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }


    private void callCheckInOutAndCancel(ContractorLoginLogout contractorLoginLogout, boolean isFromCheckInOut) {

        if (contractorLoginLogout.getBarcode() != null) {

            Log.d("TAG", "Bar code content = " + contractorLoginLogout.getBarcode());
            HashMap<String, Object> map = new HashMap<>();
            if (isFromCheckInOut) {
                map.put("searchString", contractorLoginLogout.getBarcode());
            } else {
                map.put("contractorEmployeeId", contractorLoginLogout.getContractorEmployeeId() != null ? contractorLoginLogout.getContractorEmployeeId() : "");
                map.put("contractorId", contractorLoginLogout.getContractorId() != null ? contractorLoginLogout.getContractorId() : "");
                map.put("ComplexId", contractorLoginLogout.getComplexId() != null ? contractorLoginLogout.getComplexId() : "");
            }
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Utilities.showprogressDialogue(getString(R.string.fetching_data), "", context, false);

            Call<ResponseBody> call = null;
            if (isFromCheckInOut) {
                call = ApiUtils.getAPIService().getContractorEmplyeeCheckInOut(Utilities.getToken(context), map);
            } else {
                call = ApiUtils.getAPIService().getContractorEmployeeCancel(Utilities.getToken(context), map);

            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utilities.hideProgress();
                    Log.d("Tag", "Response Code For Visitor Logout =" + response.code());
                    if (response.code() == 200 || response.code() == 201) {

                        try {
                            if (response.body() != null) {
                                String contratorMessage_response = response.body().string().toString();
                                //JSONArray jsonArray = new JSONArray(contratorMessage_response);
                                JSONObject object = new JSONObject(contratorMessage_response);
                                String msg = object.getString("message");

                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (dialogContractor.isShowing()) {
                                            dialogContractor.dismiss();
                                        }
                                    }
                                };
                                Utilities.showPopuprunnable(context, msg, false, runnable);
                            }

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
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable e) {
                    Utilities.hideProgress();
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

    private void CallSexualOffender(String etName, final ContractorLoginLogout contractorLoginLogout, final boolean isFromCheckIn) {
        if (AppConfig.IsSexualOffender) {
            Utilities.showprogressDialogue(context.getString(R.string.checkingsexualoffenderlist), context.getString(R.string.please_wait), context, false);
            Call<ResponseBody> call = ApiUtils.getAPIService().getSexualOffendedList(
                    etName.trim(),"","","");

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

                                showOffenderList(sexualOffendedList, contractorLoginLogout, isFromCheckIn);

                            } else {
                                callCheckInOutAndCancel(contractorLoginLogout, isFromCheckIn);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Utilities.hideProgress();

                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utilities.hideProgress();
                }
            });
        }
    }


    private void showOffenderList(SexualOffendedList sexualOffendedList, final ContractorLoginLogout contractorLoginLogout, boolean isFromCheckIn) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setView(getSexualList(sexualOffendedList, contractorLoginLogout, isFromCheckIn));
        alertDialog = builder1.create();
        alertDialog.setCancelable(false);
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

    private View getSexualList(SexualOffendedList sexualOffendedList, final ContractorLoginLogout contractorLoginLogout, final boolean isFromCheckIn) {

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
                                    callCheckInOutAndCancel(contractorLoginLogout, isFromCheckIn);

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

    public void callPrinterSettings() {
        Activity_Settings.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String printerModel = Activity_Settings.sharedPreferences.getString("printerModel", "");
        String macAddress = Activity_Settings.sharedPreferences.getString("macAddress", "");
        String port = Activity_Settings.sharedPreferences.getString("port", "");
        String printer = Activity_Settings.sharedPreferences.getString("printer", "");
        String address = Activity_Settings.sharedPreferences.getString("address", "");
        if (printer.equals("") ||
                address.equals("") ||
                macAddress.equals("") ||
                port.equals("")) {
            showPopup(context, "", "Please Set Up The Printer From Settings");
        }
    }

    private void setPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        // initialization for print
        Printer printer = new Printer();
        PrinterInfo printerInfo = printer.getPrinterInfo();
        if (printerInfo == null) {
            printerInfo = new PrinterInfo();
            printer.setPrinterInfo(printerInfo);

        }
        if (sharedPreferences.getString("printerModel", "").equals("")) {
            String printerModel = printerInfo.printerModel.toString();
            PrinterModelInfo.Model model = PrinterModelInfo.Model.valueOf(printerModel);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("printerModel", printerModel);
            editor.putString("port", printerInfo.port.toString());
            editor.putString("address", printerInfo.ipAddress);
            editor.putString("macAddress", printerInfo.macAddress);
            editor.putString("localName", printerInfo.getLocalName());

            // Override SDK default paper size
            editor.putString("paperSize", model.getDefaultPaperSize());

            editor.putString("orientation", printerInfo.orientation.toString());
            editor.putString("numberOfCopies",
                    Integer.toString(printerInfo.numberOfCopies));
            editor.putString("halftone", printerInfo.halftone.toString());
            editor.putString("printMode", printerInfo.printMode.toString());
            editor.putString("pjCarbon", Boolean.toString(printerInfo.pjCarbon));
            editor.putString("pjDensity",
                    Integer.toString(printerInfo.pjDensity));
            editor.putString("pjFeedMode", printerInfo.pjFeedMode.toString());
            editor.putString("align", printerInfo.align.toString());
            editor.putString("leftMargin",
                    Integer.toString(printerInfo.margin.left));
            editor.putString("valign", printerInfo.valign.toString());
            editor.putString("topMargin",
                    Integer.toString(printerInfo.margin.top));
            editor.putString("customPaperWidth",
                    Integer.toString(printerInfo.customPaperWidth));
            editor.putString("customPaperLength",
                    Integer.toString(printerInfo.customPaperLength));
            editor.putString("customFeed",
                    Integer.toString(printerInfo.customFeed));
            editor.putString("paperPosition",
                    printerInfo.paperPosition.toString());
            editor.putString("customSetting",
                    sharedPreferences.getString("customSetting", ""));
            editor.putString("rjDensity",
                    Integer.toString(printerInfo.rjDensity));
            editor.putString("rotate180",
                    Boolean.toString(printerInfo.rotate180));
            editor.putString("dashLine", Boolean.toString(printerInfo.dashLine));

            editor.putString("peelMode", Boolean.toString(printerInfo.peelMode));
            editor.putString("mode9", Boolean.toString(printerInfo.mode9));
            editor.putString("pjSpeed", Integer.toString(printerInfo.pjSpeed));
            editor.putString("pjPaperKind", printerInfo.pjPaperKind.toString());
            editor.putString("printerCase",
                    printerInfo.rollPrinterCase.toString());
            editor.putString("printQuality", printerInfo.printQuality.toString());
            editor.putString("skipStatusCheck",
                    Boolean.toString(printerInfo.skipStatusCheck));
            editor.putString("checkPrintEnd", printerInfo.checkPrintEnd.toString());
            editor.putString("imageThresholding",
                    Integer.toString(printerInfo.thresholdingValue));
            editor.putString("scaleValue",
                    Double.toString(printerInfo.scaleValue));
            editor.putString("trimTapeAfterData",
                    Boolean.toString(printerInfo.trimTapeAfterData));
            editor.putString("enabledTethering",
                    Boolean.toString(printerInfo.enabledTethering));


            editor.putString("processTimeout",
                    Integer.toString(printerInfo.timeout.processTimeoutSec));
            editor.putString("sendTimeout",
                    Integer.toString(printerInfo.timeout.sendTimeoutSec));
            editor.putString("receiveTimeout",
                    Integer.toString(printerInfo.timeout.receiveTimeoutSec));
            editor.putString("connectionTimeout",
                    Integer.toString(printerInfo.timeout.connectionWaitMSec));
            editor.putString("closeWaitTime",
                    Integer.toString(printerInfo.timeout.closeWaitDisusingStatusCheckSec));

            editor.putString("overwrite",
                    Boolean.toString(printerInfo.overwrite));
            editor.putString("savePrnPath", printerInfo.savePrnPath);
            editor.putString("softFocusing",
                    Boolean.toString(printerInfo.softFocusing));
            editor.putString("rawMode",
                    Boolean.toString(printerInfo.rawMode));
            editor.putString("workPath", printerInfo.workPath);
            editor.putString("useLegacyHalftoneEngine",
                    Boolean.toString(printerInfo.useLegacyHalftoneEngine));
            editor.apply();
        }

    }
    public void showPopup(final Context context, String title, final String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        if (!title.isEmpty()) {
            alertDialog.setTitle(Utilities.getSpannableString(title));
        }


        alertDialog.setMessage(Utilities.getSpannableString(msg));

        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(new Intent(context, Activity_Settings.class));

                    }
                });

        alertDialog.show();
    }
}
