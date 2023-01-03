package info.vams.zktecoedu.reception.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import androidx.annotation.RequiresApi;
import info.vams.zktecoedu.reception.Activity.CaptureActivity;
import info.vams.zktecoedu.reception.Activity.HomeScreenActivity;
import info.vams.zktecoedu.reception.Activity.LoginActivity;
import info.vams.zktecoedu.reception.Activity.Parking.ParkingCheckInActivity;
import info.vams.zktecoedu.reception.Activity.PreAppointmentActivity;
import info.vams.zktecoedu.reception.Activity.VisitorEntryActivityOne;
import info.vams.zktecoedu.reception.Model.AccessToken;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.Model.IdProofType;
import info.vams.zktecoedu.reception.Model.MasterResponse;
import info.vams.zktecoedu.reception.Model.PurposeVisit;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.Settings;
import info.vams.zktecoedu.reception.Model.TypeOfVisitor;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.PUT;

/**
 * Created by RahulK on 6/28/2018.
 */

public class Utilities {

    public static AlertDialog alertDialog;
    public static Typeface font = null;
    public static TypefaceSpan robotoRegularSpan;
    public static AlertDialog alertMsg = null;
    public static ProgressDialog pd = null;
    public static ProgressDialog pds = null;


    /**
     * Method to set font to all views
     *
     * @param context
     * @param root     parent view
     * @param fontName font name
     */
    public static void setFont(final Context context, final View root,
                               String fontName) {
        try {
            font = Typeface.createFromAsset(context.getAssets(), fontName);

            if (root instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++)
                    setFont(context, viewGroup.getChildAt(i), fontName);
            } else if (root instanceof EditText) {
                ((EditText) root).setTypeface(font);
            } else if (root instanceof AutoCompleteTextView) {
                ((AutoCompleteTextView) root).setTypeface(font);
            } else if (root instanceof TextView) {
                ((TextView) root).setTypeface(font);
            } else if (root instanceof Button) {
                ((Button) root).setTypeface(font);
            } else if (root instanceof CheckBox) {
                ((CheckBox) root).setTypeface(font);
            } else if (root instanceof RadioButton) {
                ((RadioButton) root).setTypeface(font);
            } else if (root instanceof TabWidget) {
                ((TextView) root).setTypeface(font);
            } else if (root instanceof TabHost) {
                ((TextView) root).setTypeface(font);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param editText
     */
    public static void disableInput(EditText editText) {
        editText.setInputType(InputType.TYPE_NULL);
        editText.setTextIsSelectable(false);
        int drawable = R.drawable.edittext_bg_uneditable;
        editText.setBackgroundResource(drawable);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return true;  // Blocks input from hardware keyboards.
            }
        });
    }

    public static void disableSelection(Spinner spinner, Context context) {
//        spinner.setEnabled(false);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        int drawable = R.drawable.edittext_bg_uneditable;
        spinner.setBackgroundResource(drawable);


    }

    public static void enableSelection(Spinner spinner, Context context) {
//        spinner.setEnabled(false);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
//        int drawable = R.drawable.edittext_bg_uneditable;
//        spinner.setBackgroundResource(drawable);

    }


    public static void showprogressDialogue(String Title, String message, Context context, boolean cancelableDialogue) {
        pd = new ProgressDialog(context);
        pd.setTitle(Title);
        if (cancelableDialogue) {
            pd.setCancelable(false);
        }
        pd.setMessage(message);
        pd.show();

    }

    public static void showProgressForAsynk(final Context context,String title,
                                    String progressMsg, final AsyncTask asyncTask) {

        try {

            pds = new ProgressDialog(context);
            pds.setTitle(title);
            pds.setCancelable(false);
            pds.setMessage(progressMsg);
            pds.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int getFrontCameraId() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                return cameraId;
            }
        }
        return -1;
    }

    public static void addTextChangeListener(final Context context, final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setBackground(context.getResources().getDrawable(R.drawable.edittext_focude_effect));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static void addTextChangeListenerForIsd(final Context context, final EditText editText,AutoCompleteTextView autoCompleteTextView){
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void showPopuprunnable(final Context context, final String errorMsg,
                                         final boolean isCancelable, final Runnable runnablePositive) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        if (!errorMsg.isEmpty()) {
            alertDialog.setMessage(getSpannableString(errorMsg));
        }
        alertDialog.setCancelable(isCancelable);
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (runnablePositive != null) {
                            runnablePositive.run();
                        }

                    }
                });

        alertDialog.show();
    }

    public static void hideProgress() {

        try {
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.cancel();
                    pd = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void hideProgressForAsync(AsyncTask asd) {

        try {
            if (pds != null) {
                if (pds.isShowing()) {
                    pds.dismiss();

                }
                pds = null;
            }

            /*if (asd != null) {
                asd.cancel(true);
                asd = null;
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String getRequest(final RequestBody request) {
//        try {
//            final RequestBody copy = request;
//            final okio.Buffer buffer = new okio.Buffer();
//            if (copy != null)
//                copy.writeTo(buffer);
//            else
//                return "";
//            return buffer.readUtf8();
//        } catch (final IOException e) {
//            return "did not work";
//        }
//    }

    public static void showPopup(Context context, String title, final String msg) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    context);
            if (!title.isEmpty()) {
                alertDialog.setTitle(getSpannableString(title));
            }
            alertDialog.setMessage(getSpannableString(msg));

            alertDialog.setPositiveButton("DISMISS",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

            try {
                alertDialog.show();
            } catch (WindowManager.BadTokenException e) {
                //use a log message
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    public static void showNoInternetPopUp(Context context) {
        showPopup(context, "", /*context.getResources().getString(R.string.no_internet_msg)*/"Internet Connection Is Not Available , Please Check And Try Again Later.");
    }

    public static String getCurrentDateTime() {
        Calendar cal = Calendar.getInstance();
        String currDate = AppConfig.DATE_TIME_FORMAT.format(cal
                .getTime());
        return currDate;
    }

    public static String getToken(Context context) {
        AccessToken accessToken = new Gson().fromJson(new SPbean(context).getPreference(Constants.TOKEN, ""), AccessToken.class);
        if (accessToken != null) {
            String access_token = accessToken.getToken_type() + " " + accessToken.getAccess_token();
            Log.d("Tag", "Token =" + access_token);
            return access_token;
        } else {
            Toast.makeText(context, "Your token has been expired ", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        return "";
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        String currDate = AppConfig.DATE_TIME_FORMAT.format(cal
                .getTime());
        return currDate;
    }

    public static boolean isJSONValid(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static SpannableString getSpannableString(String txt) {
        try {

            SpannableString span_tlt = new SpannableString(txt.trim());
            span_tlt.setSpan(robotoRegularSpan, 0, span_tlt.length(),
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            return span_tlt;
        } catch (Exception e) {

        }

        return null;
    }

    public static boolean isValidMobileNo(String field) {
        if (field.length() >= 8 && field.length() <= 12) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidIsdCodeAndMobileNo(String isdCode, String mobileNo) {
        if (isdCode.equals("+91")
                || isdCode.equals("+1")) {
            if (mobileNo.length() != 10) {
                return false;
            }
        }
        return true;
    }

    public static Object requestclientDetails(Context context) {
        RequestClientDetails requestClientDetails = new RequestClientDetails();
        requestClientDetails.setUuid(getUDIDNumber(context));
        requestClientDetails.setFcmId(getFcmId());
        requestClientDetails.setDeviceType(getDeviceType());
        requestClientDetails.setIp(Constants.Ip);
        requestClientDetails.setLanguage(new SPbean(context).getPreference(Constants.LANGUAGE_CODE, ""));
        return requestClientDetails;
    }

    public static boolean isValidForMobileNoEmpty(String field) {
        if (field.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidPassword(String field) {
        if (field.length() >= 6) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidIsdCode(String field) {
        if (field.length() >= 1 && field.length() <= 3) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidMobile(String field) {
        if (field.length() >= 8) {
            return true;
        } else {
            return false;
        }
    }

    public static float convertDptoFloat(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static String getReplaceText(String text) {
        text = text.replaceAll("\\(", "");
        text = text.replaceAll("\\)", "");
        text = text.replaceAll("-", "");

        return text;
    }


    /*public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//sslSocketFactory.createSocket((X509TrustManager) trustAllCerts[0])
//            (X509TrustManager) trustAllCerts[0]
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(60, TimeUnit.SECONDS);
            builder.connectTimeout(60, TimeUnit.SECONDS);
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setUserLogo(Context context,ImageView image) {
        String downloadFileName = "Userlogo"+ "ForHome" + ".png";
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Userlogo/" + downloadFileName);
        File folder = new File(Environment.getExternalStorageDirectory() + "/Userlogo/");
        if(pdfFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(pdfFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }else{
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_godrejlogo));
        }
    }

    public static String getLinkForDownloadLogoUrl(String schoolAdmin) {


        String url = AppConfig.WEB_URL + AppConfig.DOWNLOAD_LOGO + "?UserId=" + schoolAdmin;
        return url;
    }



    //Method For GetApiKey
    public static String getApiKey() {
        String apikey = "1234567890";
        Log.d("TAG", "Api Key =" + apikey);
        return apikey;
    }

    //Method For GetDeviceType
    public static String getDeviceType() {
        String devicetype = "Android";
        Log.d("TAG", "Device Type =" + devicetype);
        return devicetype;
    }

    //Method For GetFcmId
    public static String getFcmId() {
        String fcmid = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG", "Fcm Id =" + fcmid);
        return fcmid;
    }

    //Method For GetUDIDNumber
    public static String getUDIDNumber(Context context) {
        String udid = null;
        udid = android.provider.Settings.System.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.d("TAG", "UDID Number =" + udid);
        return udid;
    }

    public static void redirectToHome(Context context){
        Intent i = new Intent(context, ParkingCheckInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }


    public static void writeLog(String dirName, String fileName, String data) {
        try {
            if (AppConfig.isLogsWritingOn) {
                try {
                    data = data + "\nDevice info:";
                    data += "\n OS Version: " + System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")";
                    data += "\n OS API Level: " + Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")";
                    data += "\n Device: " + Build.DEVICE;
                    data += "\n Model (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //write error to the firebase server
                FirebaseCrash.report(new Throwable(data));

                File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory(), dirName);
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return;
                    }
                }

                File logFile = new File(mediaStorageDir.getPath() + File.separator
                        + fileName);

                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new FileWriter(logFile, true));
                    writer.write(data);
                    writer.newLine();
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    if (e != null && e.getMessage() != null) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String GetPurposeOfVisit(MasterResponse masterResponse, Integer i) {
        if (masterResponse != null) {
            ArrayList<PurposeVisit> purposeVisits = masterResponse.getPurposeVisits();
            for (PurposeVisit purposeVisit :
                    purposeVisits) {
                if (purposeVisit.getPurposeOfVisitId().equals(i)) {
                    return purposeVisit.getDescription();
                }
            }
        }


        return null;
    }


    public static String GetTypeOfVisitor(MasterResponse masterResponse, Integer i) {
        if (masterResponse != null) {
            ArrayList<TypeOfVisitor> typeOfVisitors = masterResponse.getTypeOfVisitors();
            for (TypeOfVisitor typeOfVisitor :
                    typeOfVisitors) {
                if (typeOfVisitor.getTypeOfVisitorId().equals(i)) {
                    return typeOfVisitor.getVisitorType();
                }
            }
        }
        return null;
    }




    public static Integer GetPurposeOfVisitId(MasterResponse masterResponse, String s) {
        if (masterResponse != null) {
            ArrayList<PurposeVisit> purposeVisits = masterResponse.getPurposeVisits();
            for (PurposeVisit purposeVisit :
                    purposeVisits) {
                if (purposeVisit.getDescription().toString().trim().equals(s.trim())) {
                    return purposeVisit.getPurposeOfVisitId();
                }
            }
        }
        return null;
    }

    public static Integer GetTypeOfVisitorId(MasterResponse masterResponse, String s) {
        if (masterResponse != null) {
            ArrayList<TypeOfVisitor> typeOfVisitors = masterResponse.getTypeOfVisitors();
            for (TypeOfVisitor typeOfVisitor :
                    typeOfVisitors) {
                if (typeOfVisitor.getVisitorType().toString().trim().equals(s.trim())) {
                    return typeOfVisitor.getTypeOfVisitorId();
                }
            }
        }
        return null;
    }


    public static int getPurposeToVisitById(MasterResponse masterResponse, int purposeOfVisitId) {

        if (masterResponse != null) {
            ArrayList<PurposeVisit> purposeVisits = masterResponse.getPurposeVisits();
            for (int i = 0; i < purposeVisits.size(); i++) {
                PurposeVisit purposeVisit = purposeVisits.get(i);
                if (purposeVisit.getPurposeOfVisitId() == purposeOfVisitId) {
                    return i + 1;
                }
            }
        }
        return 0;
    }

    public static int getTypeOfVisitorById(MasterResponse masterResponse, int typeOfVisitorId) {

        if (masterResponse != null) {
            ArrayList<TypeOfVisitor> typeOfVisitors = masterResponse.getTypeOfVisitors();
            for (int i = 0; i < typeOfVisitors.size(); i++) {
                TypeOfVisitor typeOfVisitor = typeOfVisitors.get(i);
                if (typeOfVisitor.getTypeOfVisitorId() == typeOfVisitorId) {
                    return i + 1;
                }
            }
        }

        return 0;
    }

    //    @SuppressWarnings("deprecation")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createWebPagePrint(WebView webView, Context context) {
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return;*/

//        showProgress(context,"Processing your print\nplease wait",null);
        String jobName = context.getString(R.string.app_name) + " Document";
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter(jobName);
        } else {
            printAdapter = webView.createPrintDocumentAdapter();
        }


        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
//        builder.setColorMode(PrintAttributes.COLOR_MODE_COLOR);

        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if (printJob.isCompleted()) {
            Toast.makeText(context, "Print Completed", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, PreAppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        } else if (printJob.isFailed()) {
            Toast.makeText(context, "Print Failed", Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context, PreAppointmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

//        hideProgress(null);
        // Save the job object for later status checking
    }

    public static String formatUsNumber(String text) {
        StringBuilder formattedString = new StringBuilder();
        String[] split = text.split("\\ ");
        String isd = split[0];
        String Mobile = split[1];
        String allDigitString = Mobile;
        int totalDigitCount = allDigitString.length();
        int alreadyPlacedDigitCount = 0;

        if (totalDigitCount - alreadyPlacedDigitCount > 3) {
            formattedString.append("("
                    + allDigitString.substring(alreadyPlacedDigitCount,
                    alreadyPlacedDigitCount + 3) + ")");
            alreadyPlacedDigitCount += 3;
        }
        if (totalDigitCount - alreadyPlacedDigitCount > 3) {
            formattedString.append(allDigitString.substring(
                    alreadyPlacedDigitCount, alreadyPlacedDigitCount + 3)
                    + "-");
            alreadyPlacedDigitCount += 3;
        }

        if (totalDigitCount > alreadyPlacedDigitCount) {
            formattedString.append(allDigitString
                    .substring(alreadyPlacedDigitCount));
        }
        return isd+" "+formattedString.toString();
    }

    public static String getDevice(){
        Log.d("TAG","Device Modal : " +  Build.MANUFACTURER);
        return Build.MANUFACTURER;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path, boolean frontCam) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        if(CaptureActivity.frontCamera){
            degrees = -90;
            matrix.postRotate(degrees);
            //matrix.postScale(1, -1, middleX, middleY);
        }else{
            matrix.postRotate(degrees);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * Bind settings to common settings objec
     *
     * @param context
     */
    public static void bindSettings(Context context) {

        CommonPlaceForOjects.settings = new Settings();
        String result = new SPbean(context).getPreference(Constants.SETTINGS, "");
        if (!"".equals(result)) {
            CommonPlaceForOjects.settings = new Gson().fromJson(result, Settings.class);
        } else {
            //callSettings(context);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        String regex = "^([\\w\\d\\-\\.]+)@{1}(([\\w\\d\\-]{1,67})|([\\w\\d\\-]+\\.[\\w\\d\\-]{1,67}))\\.(([a-zA-Z\\d]{2,7})(\\.[a-zA-Z\\d]{2})?)$";
        Pattern pattern = Pattern.compile(regex);

        if (pattern.matcher(target).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public final static boolean isValidMobileNumber(CharSequence target) {
        String phoneValidationUSCandaRegexss = "^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$";
        Pattern pattern = Pattern.compile(phoneValidationUSCandaRegexss);

        if (pattern.matcher(target).matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static String createParentDir(Context context) {
        final String image;
        String path = AppConfig.APP_DIR
                + File.separator
                + AppConfig.APP_DOWNLOAD_PROFILE
                + File.separator;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            image = Environment.getExternalStorageDirectory() + File.separator + path;
        else
            image = context.getExternalFilesDir(null).getAbsolutePath() + File.separator + path;
    return image;
    }

    // call to settings
    public static void callSettings(final Context context) {

        Call<ResponseBody> call = ApiUtils.getAPIService().getSettings(Utilities.getToken(context),
                (RequestClientDetails) Utilities.requestclientDetails(context));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        String result = response.body().string().toString();
                        new SPbean(context).setPreference(Constants.SETTINGS, result);
                        Log.i("TAG", "Settings = " + new SPbean(context).getPreference(Constants.SETTINGS, ""));
                        CommonPlaceForOjects.settings = new Settings();
                        CommonPlaceForOjects.settings = new Gson().fromJson(result, Settings.class);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "Error", e.getLocalizedMessage());
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                e.printStackTrace();
            }
        });
    }

    /**
     * Returning spinner data in string array
     *
     * @param context
     * @param masterResponse
     * @param idProofType
     * @return
     */
    public static ArrayList<String> getStringArrayForIdproofSpinner(Context context, MasterResponse masterResponse,
                                                                    ArrayList<String> idProofType) {

        try {
            if (masterResponse != null) {
                ArrayList<IdProofType> idProofTypes = masterResponse.getIdProofTypes();
                idProofType.add("Select");
                for (IdProofType type :
                        idProofTypes) {
                    if (idProofType == null) {
                        idProofType = new ArrayList<>();
                    }
                    idProofType.add(type.getDescription());
                }
                return idProofType;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getStringArrayForTypeOfVisitorSpinner(Context context, MasterResponse masterResponse,
                                                                          ArrayList<String> typeOfVisitor) {
        try {
            if (masterResponse != null) {
                ArrayList<TypeOfVisitor> idProofTypes = masterResponse.getTypeOfVisitors();
                typeOfVisitor.add("Select");
                for (TypeOfVisitor type :
                        idProofTypes) {
                    if (typeOfVisitor == null) {
                        typeOfVisitor = new ArrayList<>();
                    }
                    typeOfVisitor.add(type.getVisitorType());
                }
                return typeOfVisitor;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getStringArrayForPurposeOfVisitSpinner(Context context, MasterResponse masterResponse,
                                                                           ArrayList<String> purposeOfVisit) {
        try {
            if (masterResponse != null) {
                ArrayList<PurposeVisit> idProofTypes = masterResponse.getPurposeVisits();
                purposeOfVisit.add("Select");
                for (PurposeVisit type :
                        idProofTypes) {
                    if (purposeOfVisit == null) {
                        purposeOfVisit = new ArrayList<>();
                    }
                    purposeOfVisit.add(type.getDescription());
                }
                return purposeOfVisit;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isValidCountryCode(String etIsdcode) {
        try {
            ArrayList<CountryForISD> countries = Utilities.getCountryForIsdWithFlag();
            for (CountryForISD country : countries) {
                if (etIsdcode.equalsIgnoreCase(country.getDialCode().trim())) {
                    return true;
                }
            }

        } catch (Exception e) {
            return false;
        }

        return false;
    }


    public static int setDrawableFlage(String code) {
        try {
            ArrayList<CountryForISD> countries = Utilities.getCountryForIsdWithFlag();
            for (CountryForISD country : countries) {
                if (code.equalsIgnoreCase(country.getDialCode())) {
                    return country.getFlag();
                }
            }

        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    public static void hideKeyboard(Context context, View view) {
        try {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } else {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidURL(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }


    public static ArrayList<CountryForISD> getCountryForIsdWithFlag() {
        ArrayList<CountryForISD> countries = new ArrayList<>();
        {
            countries.add(new CountryForISD("AE", "United Arab Emirates", "+971", R.drawable.flag_ae, "AED"));
            countries.add(new CountryForISD("AF", "Afghanistan", "+93", R.drawable.flag_af, "AFN"));

            countries.add(new CountryForISD("AL", "Albania", "+355", R.drawable.flag_al, "ALL"));
            countries.add(new CountryForISD("AO", "Angola", "+244", R.drawable.flag_ao, "AOA"));
            countries.add(new CountryForISD("AQ", "Antarctica", "+672", R.drawable.flag_aq, "USD"));
            countries.add(new CountryForISD("AR", "Argentina", "+54", R.drawable.flag_ar, "ARS"));
            countries.add(new CountryForISD("AT", "Austria", "+43", R.drawable.flag_at, "EUR"));
            countries.add(new CountryForISD("AU", "Australia", "+61", R.drawable.flag_au, "AUD"));
            countries.add(new CountryForISD("AZ", "Azerbaijan", "+994", R.drawable.flag_az, "AZN"));

            countries.add(new CountryForISD("BD", "Bangladesh", "+880", R.drawable.flag_bd, "BDT"));
            countries.add(new CountryForISD("BE", "Belgium", "+32", R.drawable.flag_be, "EUR"));

            countries.add(new CountryForISD("BG", "Bulgaria", "+359", R.drawable.flag_bg, "BGN"));
            countries.add(new CountryForISD("BH", "Bahrain", "+973", R.drawable.flag_bh, "BHD"));

            countries.add(new CountryForISD("BJ", "Benin", "+229", R.drawable.flag_bj, "XOF"));


            countries.add(new CountryForISD("BR", "Brazil", "+55", R.drawable.flag_br, "BRL"));
            countries.add(new CountryForISD("BT", "Bhutan", "+975", R.drawable.flag_bt, "BTN"));
            countries.add(new CountryForISD("BW", "Botswana", "+267", R.drawable.flag_bw, "BWP"));
            countries.add(new CountryForISD("BY", "Belarus", "+375", R.drawable.flag_by, "BYR"));


            countries.add(new CountryForISD("CF", "Central African Republic", "+236", R.drawable.flag_cf, "XAF"));
            countries.add(new CountryForISD("CG", "Congo", "+242", R.drawable.flag_cg, "XAF"));
            countries.add(new CountryForISD("CH", "Switzerland", "+41", R.drawable.flag_ch, "CHF"));

            countries.add(new CountryForISD("CL", "Chile", "+56", R.drawable.flag_cl, "CLP"));
            countries.add(new CountryForISD("CM", "Cameroon", "+237", R.drawable.flag_cm, "XAF"));
            countries.add(new CountryForISD("CN", "China", "+86", R.drawable.flag_cn, "CNY"));
            countries.add(new CountryForISD("CO", "Colombia", "+57", R.drawable.flag_co, "COP"));
            countries.add(new CountryForISD("CR", "Costa Rica", "+506", R.drawable.flag_cr, "CRC"));
            countries.add(new CountryForISD("CU", "Cuba", "+53", R.drawable.flag_cu, "CUP"));


            countries.add(new CountryForISD("CZ", "Czech Republic", "+420", R.drawable.flag_cz, "CZK"));
            countries.add(new CountryForISD("DE", "Germany", "+49", R.drawable.flag_de, "EUR"));
            countries.add(new CountryForISD("DK", "Denmark", "+45", R.drawable.flag_dk, "DKK"));
            countries.add(new CountryForISD("DZ", "Algeria", "+213", R.drawable.flag_dz, "DZD"));
            countries.add(new CountryForISD("EC", "Ecuador", "+593", R.drawable.flag_ec, "USD"));
            countries.add(new CountryForISD("EE", "Estonia", "+372", R.drawable.flag_ee, "EUR"));
            countries.add(new CountryForISD("EG", "Egypt", "+20", R.drawable.flag_eg, "EGP"));
            countries.add(new CountryForISD("EH", "Western Sahara", "+212", R.drawable.flag_eh, "MAD"));
            countries.add(new CountryForISD("ES", "Spain", "+34", R.drawable.flag_es, "EUR"));
            countries.add(new CountryForISD("ET", "Ethiopia", "+251", R.drawable.flag_et, "ETB"));
            countries.add(new CountryForISD("FI", "Finland", "+358", R.drawable.flag_fi, "EUR"));
            countries.add(new CountryForISD("FJ", "Fiji", "+679", R.drawable.flag_fj, "FJD"));

            countries.add(new CountryForISD("FR", "France", "+33", R.drawable.flag_fr, "EUR"));
            countries.add(new CountryForISD("GB", "United Kingdom", "+44", R.drawable.flag_gb, "GBP"));
            countries.add(new CountryForISD("GE", "Georgia", "+995", R.drawable.flag_ge, "GEL"));
            countries.add(new CountryForISD("GF", "French Guiana", "+594", R.drawable.flag_gf, "EUR"));

            countries.add(new CountryForISD("GH", "Ghana", "+233", R.drawable.flag_gh, "GHS"));

            countries.add(new CountryForISD("GN", "Guinea", "+224", R.drawable.flag_gn, "GNF"));
            countries.add(new CountryForISD("GQ", "Equatorial Guinea", "+240", R.drawable.flag_gq, "XAF"));
            countries.add(new CountryForISD("GR", "Greece", "+30", R.drawable.flag_gr, "EUR"));
            countries.add(new CountryForISD("GT", "Guatemala", "+502", R.drawable.flag_gt, "GTQ"));

            countries.add(new CountryForISD("GW", "Guinea-Bissau", "+245", R.drawable.flag_gw, "XOF"));
            countries.add(new CountryForISD("GY", "Guyana", "+595", R.drawable.flag_gy, "GYD"));
            countries.add(new CountryForISD("HK", "Hong Kong", "+852", R.drawable.flag_hk, "HKD"));

            countries.add(new CountryForISD("HN", "Honduras", "+504", R.drawable.flag_hn, "HNL"));
            countries.add(new CountryForISD("HR", "Croatia", "+385", R.drawable.flag_hr, "HRK"));

            countries.add(new CountryForISD("HU", "Hungary", "+36", R.drawable.flag_hu, "HUF"));
            countries.add(new CountryForISD("ID", "Indonesia", "+62", R.drawable.flag_id, "IDR"));
            countries.add(new CountryForISD("IE", "Ireland", "+353", R.drawable.flag_ie, "EUR"));
            countries.add(new CountryForISD("IL", "Israel", "+972", R.drawable.flag_il, "ILS"));

            countries.add(new CountryForISD("IN", "India", "+91", R.drawable.flag_in, "INR"));
            countries.add(new CountryForISD("IO", "British Indian Ocean Territory", "+246", R.drawable.flag_io, "USD"));
            countries.add(new CountryForISD("IQ", "Iraq", "+964", R.drawable.flag_iq, "IQD"));
            countries.add(new CountryForISD("IR", "Iran, Islamic Republic of", "+98", R.drawable.flag_ir, "IRR"));
            countries.add(new CountryForISD("IS", "Iceland", "+354", R.drawable.flag_is, "ISK"));
            countries.add(new CountryForISD("IT", "Italy", "+39", R.drawable.flag_it, "EUR"));
            countries.add(new CountryForISD("JE", "Jersey", "+44", R.drawable.flag_je, "JEP"));
            countries.add(new CountryForISD("JO", "Jordan", "+962", R.drawable.flag_jo, "JOD"));
            countries.add(new CountryForISD("JP", "Japan", "+81", R.drawable.flag_jp, "JPY"));
            countries.add(new CountryForISD("KE", "Kenya", "+254", R.drawable.flag_ke, "KES"));
            countries.add(new CountryForISD("KG", "Kyrgyzstan", "+996", R.drawable.flag_kg, "KGS"));
            countries.add(new CountryForISD("KH", "Cambodia", "+855", R.drawable.flag_kh, "KHR"));


            countries.add(new CountryForISD("KP", "North Korea", "+850", R.drawable.flag_kp, "KPW"));
            countries.add(new CountryForISD("KR", "South Korea", "+82", R.drawable.flag_kr, "KRW"));
            countries.add(new CountryForISD("KW", "Kuwait", "+965", R.drawable.flag_kw, "KWD"));
            countries.add(new CountryForISD("KY", "Cayman Islands", "+345", R.drawable.flag_ky, "KYD"));
            countries.add(new CountryForISD("KZ", "Kazakhstan", "+7", R.drawable.flag_kz, "KZT"));

            countries.add(new CountryForISD("LB", "Lebanon", "+961", R.drawable.flag_lb, "LBP"));

            countries.add(new CountryForISD("LK", "Sri Lanka", "+94", R.drawable.flag_lk, "LKR"));
            countries.add(new CountryForISD("LR", "Liberia", "+231", R.drawable.flag_lr, "LRD"));
            countries.add(new CountryForISD("LS", "Lesotho", "+266", R.drawable.flag_ls, "LSL"));
            countries.add(new CountryForISD("LT", "Lithuania", "+370", R.drawable.flag_lt, "LTL"));

            countries.add(new CountryForISD("LV", "Latvia", "+371", R.drawable.flag_lv, "LVL"));
            countries.add(new CountryForISD("LY", "Libyan Arab Jamahiriya", "+218", R.drawable.flag_ly, "LYD"));
            countries.add(new CountryForISD("MA", "Morocco", "+212", R.drawable.flag_ma, "MAD"));
            countries.add(new CountryForISD("MC", "Monaco", "+377", R.drawable.flag_mc, "EUR"));

            countries.add(new CountryForISD("MF", "Saint Martin", "+590", R.drawable.flag_mf, "EUR"));
            countries.add(new CountryForISD("MG", "Madagascar", "+261", R.drawable.flag_mg, "MGA"));

            countries.add(new CountryForISD("MK", "Macedonia, The Former Yugoslav Republic of", "+389", R.drawable.flag_mk, "MKD"));

            countries.add(new CountryForISD("MM", "Myanmar", "+95", R.drawable.flag_mm, "MMK"));
            countries.add(new CountryForISD("MN", "Mongolia", "+976", R.drawable.flag_mn, "MNT"));
            countries.add(new CountryForISD("MO", "Macao", "+853", R.drawable.flag_mo, "MOP"));
            countries.add(new CountryForISD("MQ", "Martinique", "+596", R.drawable.flag_mq, "EUR"));
            countries.add(new CountryForISD("MR", "Mauritania", "+222", R.drawable.flag_mr, "MRO"));
            countries.add(new CountryForISD("MT", "Malta", "+356", R.drawable.flag_mt, "EUR"));
            countries.add(new CountryForISD("MU", "Mauritius", "+230", R.drawable.flag_mu, "MUR"));
            countries.add(new CountryForISD("MV", "Maldives", "+960", R.drawable.flag_mv, "MVR"));
            countries.add(new CountryForISD("MW", "Malawi", "+265", R.drawable.flag_mw, "MWK"));
            countries.add(new CountryForISD("MX", "Mexico", "+52", R.drawable.flag_mx, "MXN"));
            countries.add(new CountryForISD("MY", "Malaysia", "+60", R.drawable.flag_my, "MYR"));
            countries.add(new CountryForISD("MZ", "Mozambique", "+258", R.drawable.flag_mz, "MZN"));
            countries.add(new CountryForISD("NA", "Namibia", "+264", R.drawable.flag_na, "NAD"));
            countries.add(new CountryForISD("NC", "New Caledonia", "+687", R.drawable.flag_nc, "XPF"));
            countries.add(new CountryForISD("NE", "Niger", "+227", R.drawable.flag_ne, "XOF"));

            countries.add(new CountryForISD("NG", "Nigeria", "+234", R.drawable.flag_ng, "NGN"));

            countries.add(new CountryForISD("NL", "Netherlands", "+31", R.drawable.flag_nl, "EUR"));
            countries.add(new CountryForISD("NO", "Norway", "+47", R.drawable.flag_no, "NOK"));
            countries.add(new CountryForISD("NP", "Nepal", "+977", R.drawable.flag_np, "NPR"));

            countries.add(new CountryForISD("NZ", "New Zealand", "+64", R.drawable.flag_nz, "NZD"));
            countries.add(new CountryForISD("OM", "Oman", "+968", R.drawable.flag_om, "OMR"));
            countries.add(new CountryForISD("PA", "Panama", "+507", R.drawable.flag_pa, "PAB"));
            countries.add(new CountryForISD("PE", "Peru", "+51", R.drawable.flag_pe, "PEN"));

            countries.add(new CountryForISD("PH", "Philippines", "+63", R.drawable.flag_ph, "PHP"));
            countries.add(new CountryForISD("PK", "Pakistan", "+92", R.drawable.flag_pk, "PKR"));
            countries.add(new CountryForISD("PL", "Poland", "+48", R.drawable.flag_pl, "PLN"));


            countries.add(new CountryForISD("PS", "Palestinian Territory, Occupied", "+970", R.drawable.flag_ps, "ILS"));
            countries.add(new CountryForISD("PT", "Portugal", "+351", R.drawable.flag_pt, "EUR"));

            countries.add(new CountryForISD("PY", "Paraguay", "+595", R.drawable.flag_py, "PYG"));
            countries.add(new CountryForISD("QA", "Qatar", "+974", R.drawable.flag_qa, "QAR"));
            countries.add(new CountryForISD("RE", "Reunion", "+262", R.drawable.flag_re, "EUR"));
            countries.add(new CountryForISD("RO", "Romania", "+40", R.drawable.flag_ro, "RON"));
            countries.add(new CountryForISD("RS", "Serbia", "+381", R.drawable.flag_rs, "RSD"));
            countries.add(new CountryForISD("RU", "Russia", "+7", R.drawable.flag_ru, "RUB"));
            countries.add(new CountryForISD("RW", "Rwanda", "+250", R.drawable.flag_rw, "RWF"));
            countries.add(new CountryForISD("SA", "Saudi Arabia", "+966", R.drawable.flag_sa, "SAR"));
            countries.add(new CountryForISD("SB", "Solomon Islands", "+677", R.drawable.flag_sb, "SBD"));

            countries.add(new CountryForISD("SD", "Sudan", "+249", R.drawable.flag_sd, "SDG"));
            countries.add(new CountryForISD("SE", "Sweden", "+46", R.drawable.flag_se, "SEK"));
            countries.add(new CountryForISD("SG", "Singapore", "+65", R.drawable.flag_sg, "SGD"));

            countries.add(new CountryForISD("SI", "Slovenia", "+386", R.drawable.flag_si, "EUR"));

            countries.add(new CountryForISD("SK", "Slovakia", "+421", R.drawable.flag_sk, "EUR"));

            countries.add(new CountryForISD("SM", "San Marino", "+378", R.drawable.flag_sm, "EUR"));

            countries.add(new CountryForISD("SO", "Somalia", "+252", R.drawable.flag_so, "SOS"));

            countries.add(new CountryForISD("SS", "South Sudan", "+211", R.drawable.flag_ss, "SSP"));

            countries.add(new CountryForISD("SV", "El Salvador", "+503", R.drawable.flag_sv, "SVC"));

            countries.add(new CountryForISD("SY", "Syrian Arab Republic", "+963", R.drawable.flag_sy, "SYP"));
            countries.add(new CountryForISD("SZ", "Swaziland", "+268", R.drawable.flag_sz, "SZL"));

            countries.add(new CountryForISD("TH", "Thailand", "+66", R.drawable.flag_th, "THB"));
            countries.add(new CountryForISD("TJ", "Tajikistan", "+992", R.drawable.flag_tj, "TJS"));

            countries.add(new CountryForISD("TN", "Tunisia", "+216", R.drawable.flag_tn, "TND"));

            countries.add(new CountryForISD("TR", "Turkey", "+90", R.drawable.flag_tr, "TRY"));

            countries.add(new CountryForISD("TW", "Taiwan", "+886", R.drawable.flag_tw, "TWD"));
            countries.add(new CountryForISD("TZ", "Tanzania, United Republic of", "+255", R.drawable.flag_tz, "TZS"));
            countries.add(new CountryForISD("UA", "Ukraine", "+380", R.drawable.flag_ua, "UAH"));
            countries.add(new CountryForISD("UG", "Uganda", "+256", R.drawable.flag_ug, "UGX"));

            countries.add(new CountryForISD("US", "United States", "+1", R.drawable.flag_us, "USD"));
            countries.add(new CountryForISD("UY", "Uruguay", "+598", R.drawable.flag_uy, "UYU"));
            countries.add(new CountryForISD("UZ", "Uzbekistan", "+998", R.drawable.flag_uz, "UZS"));
            countries.add(new CountryForISD("VA", "Holy See (Vatican City State)", "+379", R.drawable.flag_va, "EUR"));

            countries.add(new CountryForISD("VE", "Venezuela, Bolivarian Republic of", "+58", R.drawable.flag_ve, "VEF"));

            countries.add(new CountryForISD("VN", "Vietnam", "+84", R.drawable.flag_vn, "VND"));

            countries.add(new CountryForISD("WS", "Samoa", "+685", R.drawable.flag_ws, "WST"));

            countries.add(new CountryForISD("YE", "Yemen", "+967", R.drawable.flag_ye, "YER"));
            countries.add(new CountryForISD("ZA", "South Africa", "+27", R.drawable.flag_za, "ZAR"));
            countries.add(new CountryForISD("ZM", "Zambia", "+260", R.drawable.flag_zm, "ZMW"));
            countries.add(new CountryForISD("ZW", "Zimbabwe", "+263", R.drawable.flag_zw, "USD"));
        }
        return countries;
    }

    public static void showDialogForSos(Context context) {
        TextView tvHeader,tvAdmin,tvSheriff;
       final Button btnCancelDialog,btnYes;
        final CheckBox adminCheckbox,sheriffCheckbox;

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialogue_sos_details, null);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setView(dialogView);

        dialogBuilder.setTitle(null);
        dialogBuilder.setMessage(null);
        tvHeader = (TextView) dialogView.findViewById(R.id.tvHeader);
        tvAdmin = (TextView) dialogView.findViewById(R.id.tvAdmin);
        tvSheriff = (TextView) dialogView.findViewById(R.id.tvSheriff);
        btnCancelDialog = (Button) dialogView.findViewById(R.id.btnCancelDialog);
        btnYes = (Button) dialogView.findViewById(R.id.btnYes);
        adminCheckbox = (CheckBox) dialogView.findViewById(R.id.adminCheckbox);
        sheriffCheckbox = (CheckBox) dialogView.findViewById(R.id.sheriffCheckbox);
        //      dialogBuilder.show();
        alertDialog.show();

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();



    }

    public static boolean isNumeric(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
