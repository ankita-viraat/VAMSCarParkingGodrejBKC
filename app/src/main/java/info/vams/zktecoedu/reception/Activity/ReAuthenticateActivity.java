package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.content.Context;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Common;
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

import static info.vams.zktecoedu.reception.Activity.PreAppointmentActivity.visitorLogMobileViewModel;

public class ReAuthenticateActivity extends AppCompatActivity {

    Context context;
    public EditText etFirstName, etMobile,etEmail, etLastName, etReAuthenticateKey;
    public AutoCompleteTextView actvIsd;
    public Button btnDeleteRegistration, btnResendKey,btnCancelDialog,btnYes;
    public TextView tvHeader,tvAdmin,tvSheriff;
    public CheckBox sheriffCheckbox,adminCheckbox;
    public ImageView ivBackPress, ivFlag, ivLogo_reAuthenticateActivity,ivSos;
    public CircleImageView ivReAuthenticate;
    public LinearLayout llTakeImage, llAuthFlagIsd,ll_ReAuthEmail,ll_ReAuthMobileNo;
    public static ArrayList<VisitorList> visitorListsArrayList;
    public boolean clickedOnce = false;
    Integer ComplexId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_authenticate);
        context = ReAuthenticateActivity.this;
        init();
    }

    public void init() {
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        actvIsd = (AutoCompleteTextView) findViewById(R.id.actvIsd);
        etMobile = (EditText) findViewById(R.id.etMobileNo);
        etEmail = (EditText) findViewById(R.id.etReAuthEmail);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etReAuthenticateKey = (EditText) findViewById(R.id.etReAuthenticateKey);
        ivBackPress = (ImageView) findViewById(R.id.imgBackImage);
        ivFlag = (ImageView) findViewById(R.id.ivIsdFlag);
        ivLogo_reAuthenticateActivity = (ImageView) findViewById(R.id.ivLogo_reAuthenticateActivity);
        ivReAuthenticate = (CircleImageView) findViewById(R.id.imgAuthenticate);
        llTakeImage = (LinearLayout) findViewById(R.id.ll_Image);
        ll_ReAuthEmail = (LinearLayout) findViewById(R.id.ll_ReAuthEmail);
        ll_ReAuthMobileNo = (LinearLayout) findViewById(R.id.ll_ReAuthMobileNo);
        llAuthFlagIsd = (LinearLayout) findViewById(R.id.llAuthFlagIsd);
        //llTakeImage.setVisibility(View.VISIBLE);
        btnResendKey = (Button) findViewById(R.id.btnResendKey);
        btnDeleteRegistration = (Button) findViewById(R.id.btnDeleteRegistration);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Utilities.showDialogForSos(ReAuthenticateActivity.this);

            }
        });
        ivSos.setVisibility(View.GONE);


        ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivLogo_reAuthenticateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilities.redirectToHome(context);
            }
        });

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etMobile));
        etMobile.addTextChangedListener(addLineNumberFormatter);
        actvIsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    ivFlag.setImageResource(Utilities.setDrawableFlage(s.toString().trim()));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    actvIsd.append("+");
                }
            }
        });
        Utilities.setUserLogo(context, ivLogo_reAuthenticateActivity);
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        if (profile != null && profile.getComplexId() != null) {
            ComplexId = profile.getComplexId();
        }
        btnDeleteRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRegistration();
            }
        });
        btnResendKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callResendOTP();
            }
        });

        if(CommonPlaceForOjects.settings != null
                && CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")){
            ll_ReAuthEmail.setVisibility(View.GONE);
        }else{
            ll_ReAuthMobileNo.setVisibility(View.GONE);
        }


        bindReAuthenticateData();

    }

    private void callResendOTP() {
        if (!clickedOnce) {
            try {
                String etmobile = Utilities.getReplaceText(etMobile.getText().toString());
                String etIsd = actvIsd.getText().toString();
                etIsd = etIsd.replaceAll("\\+", "");
                HashMap<String, Object> map = new HashMap<>();
                map.put("isdCode", etIsd.trim());
                map.put("mobile", etmobile.trim());
                map.put("email", etEmail.getText().toString());
                map.put("complexId", ComplexId);
                map.put("requestClientDetails", Utilities.requestclientDetails(context));
                mathodCallToReSendOtp(map);
                clickedOnce = true;
                btnResendKey.setEnabled(false);
                btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickedOnce = false;
    }

    private void mathodCallToReSendOtp(HashMap<String, Object> map) {
        Utilities.showprogressDialogue("", "please wait", context, false);
        Call<ResponseBody> call = ApiUtils.getAPIService().callSendReAuthenticationOtp(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Utilities.hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
            }
        });
    }


    public void bindReAuthenticateData() {
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        if (extras != null) {
            String isd = (String) extras.get("isd");
            String mobile = (String) extras.get("mobile");
            String email = (String) extras.get("email");
            etMobile.setText(mobile);
            etEmail.setText(email);
            Utilities.disableInput(etEmail);
            Utilities.disableInput(etMobile);
            actvIsd.setText("+" + isd);
            actvIsd.setInputType(InputType.TYPE_NULL);
            ivFlag.setImageResource(Utilities.setDrawableFlage(actvIsd.getText().toString()));
            llAuthFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_bg_uneditable_for_isd));

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
    }


    public void DeleteRegistration() {
        if (isValid()) {
            if (Utilities.isInternetConnected(context)) {
                String etmobile = Utilities.getReplaceText(etMobile.getText().toString());
                String etIsd = actvIsd.getText().toString();
                etIsd = etIsd.replaceAll("\\+", "");

                HashMap<String, Object> map = new HashMap<>();
                map.put("ISDCode", etIsd.trim());
                map.put("Mobile", etmobile);
                map.put("Email", etEmail.getText().toString());
                map.put("AuthenticationKey", etReAuthenticateKey.getText().toString());
                map.put("requestClientDetails", Utilities.requestclientDetails(context));

                Utilities.showprogressDialogue(getString(R.string.fetching_data), "", context, false);
                Call<ResponseBody> call = ApiUtils.getAPIService().callReAuthenticateVisitor(map, Utilities.getToken(context));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utilities.hideProgress();
                        Log.d("Tag", "Response Code For Get Authenticate Visitor =" + response.code());
                        if (response.code() == 200 || response.code() == 201) {
                            try {
                                if (response.body() != null) {
                                    String responseBody = response.body().string().toString();
                                    Log.d("TAG", "response = " + responseBody);
                                    try {
                                        JSONArray jsonArray = new JSONArray(responseBody);
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        String key = object.getString("key");
                                        String message = object.getString("message");
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                if (getIntent().hasExtra(AppConfig.BUNDLE_IS_FROM_ADDITIONAL)) {
                                                    startActivity(new Intent(ReAuthenticateActivity.this,
                                                            VisitorEntryActivityOne.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                } else if (getIntent().hasExtra(AppConfig.BUNDLE_IS_FROM_PREAPPOINTMENT)) {
                                                    startActivity(new Intent(ReAuthenticateActivity.this,
                                                            PreAppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                } else {
                                                    startActivity(new Intent(ReAuthenticateActivity.this,
                                                            VisitorCheckInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                }
                                            }
                                        };
                                        Utilities.showPopuprunnable(context, message, false, runnable);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            } catch (Exception e) {
                                e.printStackTrace();
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
                                        Utilities.showPopup(context, key, error);
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
                        //write log method
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + t.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                        t.printStackTrace();

                    }
                });
            } else {
                Utilities.showNoInternetPopUp(context);
            }
        }
    }


    private boolean isValid() {
        int error = 0;

        if (etReAuthenticateKey.getText().toString().isEmpty()) {
            etReAuthenticateKey.setError(Utilities.getSpannableString("Re-Authenticate Key is Required"));
            if (error == 0) {
                etReAuthenticateKey.requestFocus();
            }
            error++;
        }


        return !(error > 0);

    }
}
