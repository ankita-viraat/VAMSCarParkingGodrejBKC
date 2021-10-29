package info.vams.zktecoedu.reception.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.WebRequest;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    EditText etUsername;
    Button btnConfirm, btnCancel,btnCancelDialog,btnYes;
    ImageView ivLogo_forgotActivity,ivSos;
    CheckBox sheriffCheckbox,adminCheckbox;
    TextView tvHeader,tvAdmin,tvSheriff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }

    private void init() {
        context = ForgotPasswordActivity.this;
        etUsername = (EditText) findViewById(R.id.etforgotpasswordLoginId);
        ivLogo_forgotActivity = (ImageView) findViewById(R.id.ivUserLogo_forgotActivity);
        ivLogo_forgotActivity.setOnClickListener(this);
        btnConfirm = (Button) findViewById(R.id.btnConfirm_forgotpassword);
        btnConfirm.setOnClickListener(this);
        btnCancel = (Button) findViewById(R.id.btnCancel_forgotpassword);
        btnCancel.setOnClickListener(this);
        //Utilities.setUserLogo(context, ivLogo_forgotActivity);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnConfirm_forgotpassword:
                if (etUsername.getText().toString().isEmpty()) {
                    etUsername.setError(context.getResources().getString(R.string.usernameisrequired));
                    etUsername.requestFocus();
                } else {
                    Utilities.showprogressDialogue("Loading", getString(R.string.please_wait), context, false);
                    callForgotPasswordApi();
                }
                break;

            case R.id.btnCancel_forgotpassword:
                onBackPressed();
                break;

            case R.id.ivUserLogo_forgotActivity:
                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(i);
                break;

        }
    }

    private void callForgotPasswordApi() {
        WebRequest webRequest = new WebRequest();
        webRequest.setUserId(etUsername.getText().toString());
        webRequest.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));
        Log.d("Tag", "Forgot password request =" + new Gson().toJson(webRequest));
        Call<ResponseBody> call = ApiUtils.getAPIService().getForgotPassword(webRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Tag", "Response Code For Forgot Password =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        String forgotpassword_response = response.body().string().toString();
                        JSONArray jsonArray = new JSONArray(forgotpassword_response);
                        JSONObject object = jsonArray.getJSONObject(0);
                        String msg = object.getString("message");
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        };
                        Utilities.showPopuprunnable(context, msg, false, runnable);

                    } catch (IOException e) {
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                        e.printStackTrace();
                    } catch (JSONException e) {
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
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                e.printStackTrace();
                Utilities.hideProgress();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(i);
    }

}
