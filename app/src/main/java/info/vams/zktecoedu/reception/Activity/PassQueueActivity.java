package info.vams.zktecoedu.reception.Activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.vams.zktecoedu.reception.Adapter.PassQueueListAdapter;
import info.vams.zktecoedu.reception.Interface.OnPrint;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.VisitorPassQueueResponse;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PassQueueActivity extends AppCompatActivity implements OnPrint, View.OnClickListener, TextWatcher {

    Context context;
    RecyclerView rvLogoutList;
    RecyclerView.LayoutManager layoutManager;
    TextView tvNoDataFound;
    OnPrint onLogoutOrPrint;
    Toolbar toolbar;
    EditText etSearchBar;
    ImageView ivBackPress,ivLogo_passQueueActivity, ivRefresh;
    ArrayList<VisitorPassQueueResponse> logoutListData = null;
    PassQueueListAdapter logoutListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_queue);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar = (Toolbar) findViewById(R.id.toolbar_logoutList);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        context = PassQueueActivity.this;
        onLogoutOrPrint = PassQueueActivity.this;
        init();
    }

    private void init() {
        tvNoDataFound = (TextView) findViewById(R.id.tvNoDataFound);
        etSearchBar = (EditText) findViewById(R.id.etSearchBar);
        ivBackPress = (ImageView) findViewById(R.id.ivBackImage_Logout);
        ivLogo_passQueueActivity = (ImageView) findViewById(R.id.ivLogo_passQueueActivity);
        ivRefresh = (ImageView) findViewById(R.id.ivReset_Logout);
        rvLogoutList = (RecyclerView) findViewById(R.id.rvLogoutList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvLogoutList.setLayoutManager(layoutManager);
        ivRefresh.setOnClickListener(this);
        ivBackPress.setOnClickListener(this);
        ivLogo_passQueueActivity.setOnClickListener(this);

        if(CommonPlaceForOjects.settings != null &&
                CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")){
            etSearchBar.setHint(getString(R.string.visitor_name_mobile));
        }else{
            etSearchBar.setHint(getString(R.string.visitor_name_email));

        }
        Utilities.setUserLogo(context,ivLogo_passQueueActivity);
        etSearchBar.addTextChangedListener(this);

    }

    /**
     * Make a call to logout list
     */
    private void callLogoutApi() {
        Utilities.showprogressDialogue("", getString(R.string.please_wait), context, false);

        HashMap<String, Object> map = new HashMap<>();
        Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        map.put("complexId", profile.getComplexId()!= null ? profile.getComplexId() : 0);
        map.put("employeeId", profile.getEmployeeId() != null ? profile.getEmployeeId() : 0);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().getPassQueueVisitorList(Utilities.getToken(context),
                map);

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
                                logoutListData = new ArrayList<>();

                                //String result = response.body().string();
                                //Log.d("TAG",result);

                                logoutListData = new Gson().fromJson(response.body().string(),
                                        new TypeToken<ArrayList<VisitorPassQueueResponse>>() {
                                        }.getType());

                                if (!logoutListData.isEmpty()){
                                    logoutListAdapter = new PassQueueListAdapter(context, logoutListData, onLogoutOrPrint,false);
                                    rvLogoutList.setAdapter(logoutListAdapter);
                                }else {
                                    tvNoDataFound.setVisibility(View.VISIBLE);
                                    rvLogoutList.setVisibility(View.GONE);
                                }
                            }

                        } else {

                            try {
                                String data = response.errorBody().string();
                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject object = jsonArray.getJSONObject(0);
                                String error = object.getString("message");
                                tvNoDataFound.setVisibility(View.VISIBLE);
                                rvLogoutList.setVisibility(View.GONE);
                                Utilities.showPopup(context, "", error);
                                Utilities.hideProgress();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    @Override
    protected void onResume() {
        super.onResume();

        if (Utilities.isInternetConnected(context)) {
            callLogoutApi();
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onPrintPass(final int visitorLogId,final int visitorId) {
        Log.d("TAG", "Visitor Log id = " + visitorId);
        Utilities.showprogressDialogue(getString(R.string.update_pass), getString(R.string.please_wait), context, false);
        //Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
        HashMap<String, Object> map = new HashMap<>();
        //map.put("visitorLogId", visitorLogId);
        map.put("visitorId", visitorId);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().updatePassQueueVisitor(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();

                if (response != null) {

                    if (response.code() == 200 || response.code() == 201) {

                        String htmlData = "";
                        try {
                            String data = response.body().string();
                            JSONArray jsonArray = new JSONArray(data);
                            JSONObject object = jsonArray.getJSONObject(0);
                            htmlData = object.getString("message");

                            if (CommonPlaceForOjects.settings != null &&
                                    CommonPlaceForOjects.settings.getPrintPass()) {

                                callApiForPrintPass(visitorId);

                            }else {
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        callLogoutApi();
                                                                            }
                                };
                                Utilities.showPopuprunnable(context, "Visitor LoggIn Successfully", false, runnable);
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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivBackImage_Logout:

                try {
                    Utilities.hideKeyboard(context, v);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case R.id.ivReset_Logout:

                try {
                    Utilities.hideKeyboard(context, v);
                    callLogoutApi();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

                case R.id.ivLogo_passQueueActivity:
                    Utilities.redirectToHome(context);
                break;

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
            ArrayList<VisitorPassQueueResponse> temp = new ArrayList<>();
            for (VisitorPassQueueResponse data :
                    logoutListData) {

                if (data.getName() == null){
                    data.setName("");
                }
                if (data.getMobileNo() == null){
                    data.setMobileNo("");
                }
                if (data.getEmail() == null){
                    data.setEmail("");
                }
                if (data.getAccessCardNo() == null){
                    data.setAccessCardNo("");
                }

                /*if (data.getP() == null){
                    data.setPassId("");
                }*/


                if (context.toString().isEmpty()) {
                    temp = logoutListData;
                } else if (data.getName().toLowerCase().contains(s.toString().toString()) ||
                        data.getMobileNo().toLowerCase().contains(s.toString().toString()) ||
                        data.getEmail().toLowerCase().contains(s.toString().toString()) ||
                        data.getAccessCardNo().toLowerCase().contains(s.toString().toString()) /*||
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
    private void callApiForPrintPass(int visitorId) {

        Log.d("TAG", "Visitor Log id = " + visitorId);
        Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
        HashMap<String, Object> map = new HashMap<>();
        //map.put("visitorLogId", visitorLogId);
        map.put("visitorId", visitorId);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().callPrintPassNew(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    if (response.code() == 200 || response.code() == 201) {
                        Utilities.hideProgress();
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

                            //webView.loadData(data, "text/html; charset=utf-8", "UTF-8");
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
}
