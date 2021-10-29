package info.vams.zktecoedu.reception.Activity.Parking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import info.vams.zktecoedu.reception.Activity.Activity_Settings;
import info.vams.zktecoedu.reception.Activity.BaseActivity;
import info.vams.zktecoedu.reception.Activity.CreateAppointmentActivityOne;
import info.vams.zktecoedu.reception.Activity.HomeScreenActivity;
import info.vams.zktecoedu.reception.Activity.LoginActivity;
import info.vams.zktecoedu.reception.Adapter.ParkingCheckoutListAdapter;
import info.vams.zktecoedu.reception.Helper.FileTransferHelper;
import info.vams.zktecoedu.reception.Interface.ICheckoutReloadHelper;
import info.vams.zktecoedu.reception.Interface.OnLogoutOrPrint;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetVisitor.GetParkVisitorResp;
import info.vams.zktecoedu.reception.Model.ParkingModels.NotificationParkingData;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Retrofit.Helpers.GetParkVisitorHelper;
import info.vams.zktecoedu.reception.Retrofit.Helpers.IGetResponse;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Common;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.MsgDialog;
import info.vams.zktecoedu.reception.Util.MsgHandle;
import info.vams.zktecoedu.reception.Util.PdfPrint;
import info.vams.zktecoedu.reception.Util.PrinterModelInfo;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ParkingCheckOutActivity extends BaseActivity implements OnLogoutOrPrint, View.OnClickListener, TextWatcher {

    Context context;
    RecyclerView rvLogoutList;
    RecyclerView.LayoutManager layoutManager;
    TextView tvNoDataFound, tvHeader, tvAdmin, tvSheriff;
    OnLogoutOrPrint onLogoutOrPrint;
    public static boolean showFromNotification;
    CheckBox sheriffCheckbox, adminCheckbox;
    EditText etSearchBar;
    ImageView ivBackPress, ivRefresh, ivLogo_logoutActivity, ivScanner, ivSos;
    ArrayList<GetParkVisitorResp> lstParkedList = null;
    ParkingCheckoutListAdapter parkingCheckoutListAdapter;
    int SCAN_ID = 0;
    private IntentIntegrator qrScan;
    FetchPDFTask fetchPDFTask = null;
    private String downloadFileName = "";
    int visitorIdForPrint = 0;
    int lastvisitor = 1;
    final int pageSizeConst = 100;
    public boolean isPrinterNotAvailable = false;
    Button btnSearch;
    boolean isLoading = false;
    int pageCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_checkout);
        context = ParkingCheckOutActivity.this;
        onLogoutOrPrint = ParkingCheckOutActivity.this;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {
        tvNoDataFound = (TextView) findViewById(R.id.tvNoDataFound);
        etSearchBar = (EditText) findViewById(R.id.etSearchBar);
        ivBackPress = (ImageView) findViewById(R.id.ivBackImage_Logout);
        ivRefresh = (ImageView) findViewById(R.id.ivReset_Logout);
        ivLogo_logoutActivity = (ImageView) findViewById(R.id.ivLogo_logoutActivity);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCount = 1;
                callLogoutApi(etSearchBar.getText().toString().trim());
            }
        });
//        ivScanner.setOnClickListener(this);
        rvLogoutList = (RecyclerView) findViewById(R.id.rvLogoutList);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvLogoutList.setLayoutManager(layoutManager);



        rvLogoutList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == lstParkedList.size() - 1) {
                        //bottom of list!
                        try {
                            pageCount = pageCount + 1;
                            callLogoutApi("");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isLoading = true;
                    }
                }
            }
        });

        ivRefresh.setOnClickListener(this);
        ivLogo_logoutActivity.setOnClickListener(this);
        ivBackPress.setOnClickListener(this);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);
        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.addExtra("SCAN_CANERA_ID", 1);
        mDialog = new MsgDialog(this);
        mHandle = new MsgHandle(this, mDialog, true);
        myPrint = new PdfPrint(this, mHandle, mDialog);
        Utilities.setUserLogo(context, ivLogo_logoutActivity);
        ivLogo_logoutActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.redirectToHome(context);
            }
        });
        setPreferences();
        etSearchBar.addTextChangedListener(this);
        if (!showFromNotification) {
            callLogoutApi("");
        } else {
            try {
                lstParkedList = new ArrayList<GetParkVisitorResp>();
                NotificationParkingData notificationParkingData  =
                        new Gson().fromJson(getIntent().getStringExtra(Constants.NOTIFICATION_DATA_PARKING) , NotificationParkingData.class);
                GetParkVisitorResp getParkVisitorResp=new GetParkVisitorResp();
                getParkVisitorResp.setParkingVisitorId(notificationParkingData.getParkingVisitorId());

                try{
                    getParkVisitorResp.setFirstName(notificationParkingData.getName().split(" ")[0]);
                    getParkVisitorResp.setLastName(notificationParkingData.getName().split(" ")[1]);
                }catch (Exception e){
                    e.printStackTrace();
                }
                getParkVisitorResp.setVehicleNumber(notificationParkingData.getVehicleNo());
                getParkVisitorResp.setCheckInTimeLocalString(notificationParkingData.getInTime());
                getParkVisitorResp.setStatus(notificationParkingData.getStatus());
                getParkVisitorResp.setTenantName(notificationParkingData.getCompanyName());
                getParkVisitorResp.setParkingLocationName(notificationParkingData.getParkingLocation());
                getParkVisitorResp.setParkingLocationId(0);
                if(notificationParkingData.getParkingLocation().contains(" ")){
                    getParkVisitorResp.setIsdCode(notificationParkingData.getMobile().split(" ")[0]);
                    getParkVisitorResp.setMobile(notificationParkingData.getMobile().split(" ")[1]);
                }else {
                    getParkVisitorResp.setIsdCode("");
                    getParkVisitorResp.setMobile(notificationParkingData.getMobile());
                }

                lstParkedList.add(getParkVisitorResp);
                bindInitialAdapter();
                showFromNotification = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ParkingCheckInActivity.parkingCheckInPageLive = true;
                    }
                },2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ParkingCheckInActivity.parkingCheckInPageLive = false;
    }

    void bindInitialAdapter() {
        parkingCheckoutListAdapter = new ParkingCheckoutListAdapter(context, lstParkedList, true, new ICheckoutReloadHelper() {
            @Override
            public void onReload() {
                pageCount = 1;
                callLogoutApi("");
                parkingCheckoutListAdapter.notifyDataSetChanged();
            }
        });
        rvLogoutList.setAdapter(parkingCheckoutListAdapter);
        tvNoDataFound.setVisibility(View.GONE);
        rvLogoutList.setVisibility(View.VISIBLE);
    }

    /**
     * Make a call to logout list
     */


    private void callLogoutApi(String searchKey) {
        GetParkVisitorReq getParkVisitorReq = new GetParkVisitorReq();
        Profile profile = new Gson().fromJson(new SPbean(ParkingCheckOutActivity.this)
                .getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        try {
            getParkVisitorReq.setComplexId(profile.getComplexId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getParkVisitorReq.setPageSize(pageSizeConst);
        getParkVisitorReq.setWildCardValue(searchKey);
        getParkVisitorReq.setPage(pageCount);
        getParkVisitorReq.setParkingLocationId(0);
        getParkVisitorReq.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));

        GetParkVisitorHelper.callMethod(ParkingCheckOutActivity.this, new IGetResponse() {
            @Override
            public void isSuccess(boolean val, Object obj) {
                tvNoDataFound.setVisibility(View.GONE);
                rvLogoutList.setVisibility(View.VISIBLE);
                if (pageCount == 1) {
                    lstParkedList = new ArrayList<GetParkVisitorResp>();
                }
                lstParkedList.addAll((ArrayList<GetParkVisitorResp>) obj);
                if (!lstParkedList.isEmpty()) {
                    if (pageCount == 1) {
                        bindInitialAdapter();
                    } else {
                        parkingCheckoutListAdapter.notifyItemRangeInserted((pageCount * pageSizeConst) + 1, lstParkedList.size());
                        isLoading = false;
                    }
                } else {
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    rvLogoutList.setVisibility(View.GONE);
                }

            }
        }, getParkVisitorReq);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onLogout(final int visitorId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.confirmLogout);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        etSearchBar.setText("");
                        callLogoutMethod(visitorId, null, false);
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
    }

    private void callLogoutMethod(int visitorId, String barcode, boolean isFromBarcode) {
        //Log.d("TAG", "Visitor id = " + visitorId);
        Utilities.showprogressDialogue(getString(R.string.logging_out), getString(R.string.please_wait), ParkingCheckOutActivity.this, false);
        HashMap<String, Object> map = new HashMap<>();
        if (isFromBarcode) {
            map.put("searchString", barcode);
        } else {
            map.put("visitorId", visitorId);
        }
        map.put("requestClientDetails", Utilities.requestclientDetails(context));
        Log.d("TAG", "Barcode  = " + new Gson().toJson(map));
        Call<ResponseBody> call = null;
        if (isFromBarcode) {
            call = ApiUtils.getAPIService().getVisitorLogout(Utilities.getToken(context), map);
        } else {
            call = ApiUtils.getAPIService().getLogoutVIsitorById(Utilities.getToken(context), map);
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                try {
                    if (response != null) {

                        if (response.code() == 200) {

                            if (response.body() != null) {// positive response
                                String result = response.body().string().toString();
                                Log.d("TAG", "result = " + result);
                                JSONArray jsonArray = new JSONArray(result);
                                JSONObject object = jsonArray.getJSONObject(0);
                                String message = object.getString("message");
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        callLogoutApi("");
                                    }
                                };
                                Utilities.showPopuprunnable(context, message, false, runnable);
                                Utilities.hideProgress();
                            } else {
                                Utilities.showPopup(context, "", getString(R.string.technical_error));
                            }
                        } else {
                            Utilities.hideProgress();
                            try {
                                if (response.errorBody() != null) {
                                    String errorMsg = response.errorBody().string().toString();
                                    Log.d("TAG", "errormsg = " + errorMsg);
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

    @Override
    public void onPrintPass(int visitorId) {
        Log.d("TAG", "Visitor id = " + visitorId);
        //printpass(visitorId);
        visitorIdForPrint = visitorId;

//        callPrinterSettings();

    }

    private void printpass(int visitorId) {
        Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
        HashMap<String, Object> map = new HashMap<>();
        map.put("visitorId", visitorId);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().callRePringPass(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
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
                    startActivity(new Intent(this,
                            ParkingCheckInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ivReset_Logout:

                try {
                    Utilities.hideKeyboard(context, v);
                    pageCount = 1;
                    callLogoutApi("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ivLogoutScanner:
                if (LoginActivity.isSelfeHeplKiosk) {
                    qrScan.addExtra("SCAN_CAMERA_ID", Utilities.getFrontCameraId());
                }
                qrScan.initiateScan(IntentIntegrator.ALL_CODE_TYPES);
                SCAN_ID = 1;
                break;
            case R.id.ivSos:
                Utilities.showDialogForSos(ParkingCheckOutActivity.this);
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (SCAN_ID) {

            case 1:
                IntentResult logoutresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (logoutresult.getContents() != null) {
                    callLogoutMethod(0, logoutresult.getContents(), true);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.result_not_found), Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
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
    public void onTextChanged(final CharSequence s, int start, int before, int count) {
        Log.d("TAG", "Search Text  = " + s);
        try {
//            btnSearch.setVisibility(s.toString().trim().isEmpty() ? View.GONE : View.INVISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnSearch.setVisibility(s.toString().trim().isEmpty() ? View.GONE : View.VISIBLE);
                }
            }, 500);


//            if (lstParkedList != null && !lstParkedList.isEmpty()) {
//                ArrayList<GetParkVisitorResp> temp = new ArrayList<>();
//                if (s.toString().isEmpty()) {
//                    temp = lstParkedList;
//                } else {
//                    for (GetParkVisitorResp data : lstParkedList) {
//                        if (data.getFirstName() == null) {
//                            data.setFirstName("");
//                        }
//
//                        if (data.getLastName() == null) {
//                            data.setLastName("");
//                        }
//
//                        if (data.getVehicleNumber() == null) {
//                            data.setVehicleNumber("");
//                        }
//
//                        if (data.getMobile() == null) {
//                            data.setMobile("");
//                        }
//
//                        if (data.getFirstName().toLowerCase().startsWith(s.toString().toLowerCase()) ||
//                                data.getLastName().toLowerCase().startsWith(s.toString().toLowerCase()) ||
//                                data.getVehicleNumber().toLowerCase().startsWith(s.toString().toLowerCase()) ||
//                                ((data.getIsdCode() + data.getMobile()).toLowerCase().startsWith(s.toString().toLowerCase()))) {
//                            temp.add(data);
//                        }
//                    }
//                }
//
//                tvNoDataFound.setVisibility(temp.isEmpty() ? View.VISIBLE : View.GONE);
//                rvLogoutList.setVisibility(temp.isEmpty() ? View.GONE : View.VISIBLE);
//                parkingCheckoutListAdapter.filteredList(temp);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void callPrintPassFromHandler() {

        if (visitorIdForPrint != 0) {
            try {
                fetchPDFTask = new FetchPDFTask();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    fetchPDFTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    fetchPDFTask.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }/*else{
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LogoutListActvity.this,
                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            };
            Utilities.showPopuprunnable(context, "" + model.getMessage() != null && !model.getMessage().isEmpty() ?model.getMessage() : "Visitor Entry Successfull", true, runnable);
        }*/


    }

    class FetchPDFTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utilities.showProgressForAsynk(context, getResources().getString(R.string.printing_pass), getResources().getString(R.string.please_wait), fetchPDFTask);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = AppConfig.WEB_URL + AppConfig.HANDLER_PRINTPASS + "?VisitorId=" + visitorIdForPrint;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

            downloadFileName = "RePrintPass" + timeStamp + visitorIdForPrint + ".pdf";//Create file name by picking download file name from URL
            File folder = new File(extStorageDirectory, "RePrintPass");
            if (!folder.exists()) {
                folder.mkdir();
            }

            File pdfFile = new File(folder, downloadFileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileTransferHelper.downloadFileForPDF(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/RePrintPass/" + downloadFileName);  // -> filename = maven.pdf
                //Uri path = Uri.fromFile(pdfFile);
                String file = pdfFile.toString();
                setPdfFile(file);
                Utilities.hideProgressForAsync(fetchPDFTask);
                if (!checkUSB())
                    return;
                int startPage = 1;
                int endPage;
                if (lastvisitor == 1) {
                    endPage = 1;
                } else {
                    endPage = lastvisitor;
                }

                // All pages


                // error if startPage > endPage
                if (startPage > endPage) {
                    mDialog.showAlertDialog(getString(R.string.msg_title_warning),
                            getString(R.string.error_input));
                    return;
                }


                Utilities.hideProgressForAsync(fetchPDFTask);
                // call function to print
                ((PdfPrint) myPrint).setPrintPage(startPage, endPage);
                myPrint.print();
            } catch (Exception e) {
                e.printStackTrace();
                /*isFromSettings = true;
                showPopup(context, "", "Please Set Up The Printer From Settings");*/
            }
        }
    }

    private void setPdfFile(String file) {

        if (Common.isPdfFile(file)) {
            ((PdfPrint) myPrint).setFiles(file);
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


    @Override
    public void selectFileButtonOnClick() {

    }

    @Override
    public void printButtonOnClick() {

    }


    public void showPopup(final Context context, String title, final String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);
        if (!title.isEmpty()) {
            alertDialog.setTitle(Utilities.getSpannableString(title));
        }


        alertDialog.setMessage(Utilities.getSpannableString(msg));

        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //isPrinterNotAvailable = true;
                        dialog.cancel();
                        startActivity(new Intent(context, Activity_Settings.class));

                    }
                });

        alertDialog.show();
    }


}
