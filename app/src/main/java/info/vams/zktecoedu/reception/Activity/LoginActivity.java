package info.vams.zktecoedu.reception.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckInActivity;
import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckOutActivity;
import info.vams.zktecoedu.reception.Helper.FileTransferHelper;
import info.vams.zktecoedu.reception.Model.EmployeeInRoles;
import info.vams.zktecoedu.reception.Model.LoginRequest;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Service.AppStatusService;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    Context context;
    Button btnLogin;
    Toolbar loginToolbar;
    CheckBox sheriffCheckbox, adminCheckbox;
    TextView tvForgotPassword, tv_errorLoginId, tv_errorPassword, tvVersion, tvPoweredby, tvLanguage;
    ImageView ivShowPassword, ivLanguage, iv_logo;
    //  ImageButton ivSos;
    EditText etPassword, etLoginId;
    boolean visible = false;
    LoginRequest loginRequest;
    AlertDialog alertDialog = null;
    boolean onBackPressed = false;
    Locale myLocale;
    private AlertDialog dialog;
    Dialog builders = null;
    DownloadUserLogoTask downlaodUserLogoTask;
    public static boolean isSelfeHeplKiosk = true;
    private String downloadFileName = "";
    public String UserLogoDirectory = Environment.getExternalStorageDirectory() + File.separator + "Userlogo" + File.separator;
    public String PrintPassDirectory = Environment.getExternalStorageDirectory() + File.separator + "PrintPass" + File.separator;
    public String RePrintPassDirectory = Environment.getExternalStorageDirectory() + File.separator + "RePrintPass" + File.separator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        deleteDir(new File(UserLogoDirectory));
        deleteDir(new File(PrintPassDirectory));
        deleteDir(new File(RePrintPassDirectory));
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                checkPermission();
            } else {
                // Pre-Marshmallow

            }
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n" + getLocalClassName());
        }
        init();
//        throw new RuntimeException("Test Crash");
    }

    private void init() {
        context = LoginActivity.this;
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        loginToolbar = (Toolbar) findViewById(R.id.loginToolbar);
        ivShowPassword = (ImageView) findViewById(R.id.ivShowPassword);
        ivShowPassword.setOnClickListener(this);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(this);
        tv_errorLoginId = (TextView) findViewById(R.id.tv_errorLoginId);
        tv_errorPassword = (TextView) findViewById(R.id.tv_errorPassword);
        etLoginId = (EditText) findViewById(R.id.etLoginId);
        etPassword = (EditText) findViewById(R.id.etPassword);
//        etLoginId.setText("Nilesh");
//        etPassword.setText("Nokia@86");
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        tvPoweredby = (TextView) findViewById(R.id.tvPoweredby);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        ivLanguage = (ImageView) findViewById(R.id.ivLanguage);
        ivLanguage.setOnClickListener(this);
        etPassword.setOnFocusChangeListener(this);

        setSelectedLanguage();
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void setlanguagecodeinTextView() {
        if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").equals("en")) {
            tvLanguage.setText("EN");
        }
        /*if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").equals("ar")) {
            tvLanguage.setText("AR");
        }*/
        /*if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").equals("mr")) {
            tvLanguage.setText("MR");
        }
        if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").equals("es")) {
            tvLanguage.setText("ES");
        }
        if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").equals("pt")) {
            tvLanguage.setText("PT");
        }*/
    }

    private void setSelectedLanguage() {
        try {
            Configuration config = getBaseContext().getResources().getConfiguration();
            String language = new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "");
            if (!"".equals(language) && !config.locale.getLanguage().equals(language)) {
                Locale locale = new Locale(language);
                Locale.setDefault(locale);
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                //tv_logoText.setText("" + context.getResources().getString(R.string.welcome));
                tvPoweredby.setText("" + context.getResources().getString(R.string.powered_by));
                tvVersion.setText("" + context.getResources().getString(R.string.version));
                btnLogin.setText("" + context.getResources().getString(R.string.btn_login));
                tvForgotPassword.setText("" + context.getResources().getString(R.string.forgot));
                etLoginId.setHint("" + context.getResources().getString(R.string.hint_email));
                etPassword.setHint("" + context.getResources().getString(R.string.hint_password));
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                onLoginMethod();
                break;

            case R.id.tvForgotPassword:
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
                break;

            case R.id.ivShowPassword:
                showPassword();
                break;

            case R.id.ivLanguage:
                //showDialogForLanguage();
                break;

        }
    }


    //     ivSos.setMovementMethod(LinkMovementMethod.getInstance())
    //     ivSos.setOnClickListener(new View.OnClickListener() {
    //     @Override
    //      public void onClick(View v) {
    //           showDialogForSos();
    //       }
    //  });

    private void showDialogForLanguage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = (View) layoutInflater.inflate(R.layout.language_listview, null);
        builder.setView(view);
        final ListView listView = (ListView) view.findViewById(R.id.lv_language);
        final Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        List<String> languageList = Arrays.asList(context.getResources().getStringArray(R.array.language));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.language_item, R.id.tvLang, languageList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tvLang = (TextView) view.findViewById(R.id.tvLang);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "") == null ||
                                new SPbean(context).getPreference(Constants.LANGUAGE_CODE, "").isEmpty()) {
                            alertDialog.dismiss();
                        } else {
                            myLocale = new Locale(new SPbean(context).getPreference(Constants.LANGUAGE_CODE, ""), "");
                            Resources res = getResources();
                            DisplayMetrics dm = res.getDisplayMetrics();
                            Configuration conf = res.getConfiguration();
                            conf.locale = myLocale;
                            res.updateConfiguration(conf, dm);
                            alertDialog.dismiss();
                        }
                    }
                });
                return view;
            }
        };


        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String languageName = listView.getItemAtPosition(position).toString();
                listView.setEnabled(false);
                Log.d("Tag", "Language =" + languageName);
                if (languageName.equals("English")) {
                    setLocale("en");
                    new SPbean(context).setPreference(Constants.LANGUAGE_CODE, "en");
                }
               /* if (languageName.equals("Arabic")) {
                    setLocale("ar");
                    new SPbean(context).setPreference(Constants.LANGUAGE_CODE, "ar");
                }*/
                /*if (languageName.equals("Marathi")) {
                    setLocale("mr");
                    new SPbean(context).setPreference(Constants.LANGUAGE_CODE, "mr");
                }
                if (languageName.equals("Spanish")) {
                    setLocale("es");
                    new SPbean(context).setPreference(Constants.LANGUAGE_CODE, "es");
                }
                if (languageName.equals("Portugal")) {
                    setLocale("pt");
                    new SPbean(context).setPreference(Constants.LANGUAGE_CODE, "pt");
                }*/

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void setLocale(String localeName) {
        if (!localeName.equals(new SPbean(context).
                getPreference(Constants.LANGUAGE_CODE, ""))) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, LoginActivity.class);
            startActivity(refresh);
        } else {
            alertDialog.dismiss();
            Utilities.showPopup(context, "", getResources().getString(R.string.language_already_selected));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.etPassword:
                focusChangePassword(hasFocus);
                break;
        }

    }

    private void focusChangePassword(boolean hasFocus) {
        if (hasFocus) {
            ivShowPassword.setVisibility(View.VISIBLE);
            tvForgotPassword.setVisibility(View.GONE);
        } else {
            ivShowPassword.setVisibility(View.GONE);
            tvForgotPassword.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onLoginMethod() {
        if (valid()) {
            if (Utilities.isInternetConnected(context)) {
                clearErrorMessages();
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(etPassword.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                callAccessToken();
            } else {
                Utilities.showNoInternetPopUp(context);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void callAccessToken() {
        loginRequest = new LoginRequest();
        loginRequest.setGrant_type("password");
        loginRequest.setUsername(etLoginId.getText().toString());
        loginRequest.setPassword(etPassword.getText().toString());

        Utilities.showprogressDialogue(getString(R.string.logging), getString(R.string.please_wait), context, false);
        Call<ResponseBody> callToken = ApiUtils.getAPIService().getToken(loginRequest.getGrant_type(), loginRequest.getUsername(), loginRequest.getPassword());
        callToken.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                clearFields();
                Log.d("Tag", "Response Code For Token =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        String access_Token = response.body().string().toString();
                        new SPbean(context).setPreference(Constants.TOKEN, access_Token);    //this method token response save in shared preferences
                        new SPbean(context).setPreference(Constants.LOGINREQUEST, new Gson().toJson(loginRequest));
                        callDownloadImage();
                    } catch (Exception e) {
                        //write log method
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                        e.printStackTrace();
                    }

                } else {
                    try {
                        Utilities.hideProgress();
                        String errorMsg = response.errorBody().string().toString();
                        String error = "";

                        JSONObject object = new JSONObject(errorMsg);
                        error = object.getString("error_description");
                        Utilities.showPopup(context, "", "" + error);

                    } catch (Exception e) {
                        //write log method
                        Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                clearFields();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
            }
        });
    }

    private void callProfileData() {
        Call<ResponseBody> call = ApiUtils.getAPIService().getProfile(Utilities.getToken(context), (RequestClientDetails) Utilities.requestclientDetails(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Get Profile =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String profile_Response = response.body().string().toString();
                            Log.d("Tag", "Profile = " + profile_Response);
                            Profile profile = new Gson().fromJson(profile_Response, Profile.class);
                            if (profile != null && profile.getEmployeeInRoles() != null) {
                                boolean isReception = false, isChangedPassword = false;
                                for (EmployeeInRoles employeeInRoles : profile.getEmployeeInRoles()) {
                                    if (employeeInRoles.getRoleName().toString().trim().equalsIgnoreCase(Constants.RECEPTION)) {
                                        isReception = true;
                                        if (!profile.isChangedPassword()) {
                                            new SPbean(context).setPreference(Constants.PROFILE_RESPONSE, profile_Response);
                                            Log.d("Tag", "Profile updated successfully");
                                            /*startActivity(new Intent(context, PreAppointmentActivity.class));
                                            finish();*/
//                                            showKioskDislogue();
                                            isSelfeHeplKiosk = false;
//                                            startActivity(new Intent(context, ParkingCheckInActivity.class));
//                                            startActivity()

                                            Intent intent = new Intent(context, ParkingCheckOutActivity.showFromNotification
                                                    ? ParkingCheckOutActivity.class : ParkingCheckInActivity.class);
                                            String data = getIntent().getStringExtra(Constants.NOTIFICATION_DATA_PARKING);
                                            intent.putExtra(Constants.NOTIFICATION_DATA_PARKING, data);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();
                                        } else {
                                            isChangedPassword = true;
                                        }
                                    }
                                }

                                if (!isReception) {
                                    Utilities.showPopup(context, "", getString(R.string.not_reception_login));
                                    new SPbean(context).removePreference(Constants.TOKEN);
                                    new SPbean(context).removePreference(Constants.LOGINREQUEST);

                                } else if (isChangedPassword) {

                                    Utilities.showPopup(context, "", getString(R.string.change_password));
                                    new SPbean(context).removePreference(Constants.TOKEN);
                                    new SPbean(context).removePreference(Constants.LOGINREQUEST);
                                }

                            } else {
                                Utilities.showPopup(context, "", getString(R.string.not_reception_login));
                            }
                            // make call to Employee masters

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

    /**
     * Kiosk dialogue popup
     */
    private void showKioskDislogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(getViewForKiosk(builder));
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    private View getViewForKiosk(final AlertDialog.Builder builder) {
        Button btnKiosk, btnReception;
        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_selfhelp_kios, null, false);
        btnKiosk = (Button) view.findViewById(R.id.btn_kios);
        btnReception = (Button) view.findViewById(R.id.btnReception);
        btnKiosk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelfeHeplKiosk = true;
                startActivity(new Intent(context, ParkingCheckInActivity.class));
                finish();
                dialog.dismiss();
            }
        });

        btnReception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelfeHeplKiosk = false;
                startActivity(new Intent(context, ParkingCheckInActivity.class));
                finish();
                dialog.dismiss();
            }
        });

        return view;
    }

    private void clearFields() {
        etPassword.setText("");
    }

    private void clearErrorMessages() {
        tv_errorLoginId.setVisibility(View.GONE);
        tv_errorPassword.setVisibility(View.GONE);
    }

    private void showPassword() {
        if (visible) {
            etPassword.setTransformationMethod(new PasswordTransformationMethod());
            ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility_off));
            visible = false;
        } else {
            etPassword.setTransformationMethod(null);
            ivShowPassword.setImageDrawable(getResources().getDrawable(R.drawable.visibility));
            visible = true;
        }
    }

    private boolean valid() {
        int error = 0;
        if (etLoginId.getText().toString().isEmpty()) {
            etLoginId.setError(getResources().getString(R.string.loginId_error));
            if (error == 0) {
                etLoginId.requestFocus();
                error++;
            }
        }/*else {
            tv_errorLoginId.setVisibility(View.GONE);
        }*/

        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError(getResources().getString(R.string.password_error));
            if (error == 0) {
                etPassword.requestFocus();
                error++;
            }
        }
        return !(error > 0);
    }

    private void callDownloadImage() {
        if (Utilities.isInternetConnected(context)) {
            downlaodUserLogoTask = new DownloadUserLogoTask();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                downlaodUserLogoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                downlaodUserLogoTask.execute();
            }
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }

    // Check permission
    public void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.d("PERMISSION", "Permission is not granted, requesting");
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //return true;
            } else {
                Log.d("PERMISSION", "Permission is  granted");
                //  return false;
            }
        }
    }


    public class DownloadUserLogoTask extends AsyncTask<String, String, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Boolean doInBackground(String... strings) {

            Log.d("TAG", "do in background upload call");
            if (Utilities.isInternetConnected(context)) {

                boolean isDownloadAll = false;
                try {

                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    downloadFileName = "Userlogo" + "ForHome" + ".png";//Create file name by picking download file name from URL
                    File folder = new File(extStorageDirectory, "Userlogo");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }

                    File pdfFile = new File(folder, downloadFileName);

                    try {
                        pdfFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isDownloadAll = FileTransferHelper.downloadFile(etLoginId.getText().toString(), pdfFile);

                    if (!isDownloadAll) {
                        Log.d("TAG", "Not Download ");
                        publishProgress("Download failed", "false");
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG", "Not uploaded ");
                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n" + ((Activity) context).getLocalClassName());
                }
                Log.d("TAG", "uploaded " + isDownloadAll);
                return isDownloadAll;

            }
            return false;
        }


        @Override
        protected void onPostExecute(Boolean isDownload) {
            super.onPostExecute(isDownload);
            if (isDownload) {
                Log.d("TAG", "TRUE");
                callProfileData();
            } else {
                Log.d("TAG", "FALSE");
                deleteDir(new File(UserLogoDirectory));
                callProfileData();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
//                for (int i = 0; i < permissions.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//
//                        // permission was granted, yay! Do the
//                        // contacts-related task you need to do.
//
//                    } else {
//                        finish();
//                        // permission denied, boo! Disable the
//                        // functionality that depends on this permission.
//                    }
//                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(getBaseContext(), AppStatusService.class));
        deleteDir(new File(UserLogoDirectory));
    }
}