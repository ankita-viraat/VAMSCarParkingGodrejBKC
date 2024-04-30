package info.vams.zktecoedu.reception.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbytes.barcode_reader.BarcodeReaderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Adapter.CountryFlagAdapter;
import info.vams.zktecoedu.reception.Adapter.PersonToMeetAdapter;
import info.vams.zktecoedu.reception.Adapter.PersonToMeetAutoComplete;
import info.vams.zktecoedu.reception.Adapter.QuestionAdapter;
import info.vams.zktecoedu.reception.Adapter.SexualOffenderAdapter;
import info.vams.zktecoedu.reception.Adapter.TenantAutoCompleteAdapter;
import info.vams.zktecoedu.reception.Adapter.VisitorQuestionAdapter;
import info.vams.zktecoedu.reception.Helper.FileTransferHelper;
import info.vams.zktecoedu.reception.Model.AdditionalDetail;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.Model.DLFields;
import info.vams.zktecoedu.reception.Model.EmployeeList;
import info.vams.zktecoedu.reception.Model.ImageUploadObject;
import info.vams.zktecoedu.reception.Model.MasterResponse;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.PurposeVisit;
import info.vams.zktecoedu.reception.Model.Question;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.Model.SexualOffendedList;
import info.vams.zktecoedu.reception.Model.TenantList;
import info.vams.zktecoedu.reception.Model.TypeOfVisitor;
import info.vams.zktecoedu.reception.Model.VisitorEntryModel;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.Model.VisitorLogMobileViewModel;
import info.vams.zktecoedu.reception.Model.VisitorLogPersonToVisit;
import info.vams.zktecoedu.reception.Model.VisitorQuestion;
import info.vams.zktecoedu.reception.Model.WebRequest;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Common;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.Imageutils;
import info.vams.zktecoedu.reception.Util.MsgDialog;
import info.vams.zktecoedu.reception.Util.MsgHandle;
import info.vams.zktecoedu.reception.Util.PaintView;
import info.vams.zktecoedu.reception.Util.PdfPrint;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.PrinterModelInfo;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;
import info.vams.zktecoedu.reception.Util.Utilities;
import info.vams.zktecoedu.reception.Util.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class VisitorEntryActivityOne extends BaseActivity implements View.OnClickListener, Imageutils.ImageAttachmentListener {

    Context context;
    Button btnNext_visitorEntryOne, btnSubmit_visitorEntryTwo;
    Button btn_AddMulti, btn_plus, btn_Add_Details, btnReAuthenticate, btnByPass, btn_ScanDL, btnReSendKey,btnCancelDialog,btnYes;
    CheckBox sheriffCheckbox,adminCheckbox;
    public LinearLayout llAuthentication, llBypassOrResend;
    EditText etAuthenticationKey1_visitorEntryTwo;
    Toolbar toolbar_visitorEntryActivityOne;
    ImageView ivReset_visitorEntryActivityOne, ivLogo_visitorEntryActivity, ivVisitorEntryIsdFlag,ivSos;
    TextView tvTakenPhoto, tvMobileStarVisitor, tvPersontoMeetStar, tvEmailStarVisitor, tv_Registered_Image, tvQuestionStarVisitor, tvSexualOffendMatch,tvHeader,tvAdmin,tvSheriff;
    CircleImageView ivProfile, cvRegisteredImage;
    ArrayList<TypeOfVisitor> typeOfVisitorArrayList;
    ArrayList<PurposeVisit> purposeVisitArrayList;
    ArrayList<EmployeeList> employeeListArrayList;
    ArrayList<EmployeeList> employeeListTenantArrayList;
    AutoCompleteTextView actv_visitorEntrypersonToMeet, actvVisitorEntryIsd, actvTenantName;
    RecyclerView rvPersonToMeet, rvQuestions;
    RecyclerView.LayoutManager layoutManager;
    PersonToMeetAdapter personToMeetAdapter;

    boolean localBypass = false;
    boolean requireAuthentication = true;
    boolean byPass = false;
    boolean visible = false;
    boolean onBackPressedFlag = false;
    public static boolean clickedOnce = false;
    boolean flag = true;
    LinearLayout ll_tenantName, ll_buildingName, ll_complexName, ll_takeImage,
            ll_registerImage, llbtnReAuth, llAddReAuth, ll_VisitorEmail, ll_VisitorMobileNo, llQuestion, llVisitorFlagIsd;
    public static LinearLayout llPersonToMeet;
    EditText etvisitorEntrytenantName, etvisitorEntrybuildingName, etvisitorEntrycomplexName,
            etvisitorEntryFirstName, etvisitorEntryLastName, etvisitorEntryMobileNo,
            etvisitorEntryEmail, etvisitorEntryCompanyName;
    EditText etBuildingName;
    VisitorList visitorListForIdProof;
    public EmployeeList employee;
    public VisitorList visitorList;
    public MasterResponse masterResponse;
    private static int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_DOCUMENT = 20;
    private static final int PICK_IMAGE_ID = 100;
    private Uri fileUri;
    public static ArrayList<ImageUploadObject> imageUploadObjects;
    ImageUploadObject imageUploadObject;
    public static ArrayList<VisitorLogPersonToVisit> employeeListsCollection = null;
    String serverIdProofName = "";

    boolean isQuestionsValid = false;

    VisitorLogMobileViewModel model;

    ArrayList<TenantList> tenantListArrayList;

    //QuestionAdapter questionAdapter;
    ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<>();
    ArrayList<VisitorQuestion> visitorRequiredQuestionArrayList = new ArrayList<>();
    public AdditionalDetail additionalDetail = new AdditionalDetail();
    ArrayList<Integer> buildingIds;
    AlertDialog alertDialog;

    ArrayList<SexualOffend> sexualOffends = null;
    QuestionAdapter questionAdapter = null;
    public int counter = 0;
    private int textlength = 0;

    private ArrayList<EditText> lstEtAns;
    public static TableLayout tableQuestion;
    boolean isTextChanged = false;
    FetchPDFTask fetchPDFTask = null;
    private String downloadFileName = "";
    int visitorLogId = 0;
    int lastvisitor = 1;
    //    public static final int MY_BLINKID_REQUEST_CODE = 123;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    //    protected RecognizerBundle mRecognizerBundle;
    private ListView mListView;
    //    UsdlRecognizer usdlRecognizer;
    DLFields dlFields;
    public boolean isFromUpload = false;
    public boolean isFromAdditional = false;
    public boolean isFromDL = false;
    public boolean isPrinterNotAvailable = false;
    public static boolean isFrontCamera = LoginActivity.isSelfeHeplKiosk;
    Imageutils imageUtils;
    private static final int CAPTURE_ID = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_entry_one);
        imageUtils = new Imageutils(this);
        init();

        SPbean sPbean = new SPbean(context);

        if ("".equalsIgnoreCase(sPbean.getPreference(Constants.TENANTLIST_RESPONSE, ""))) {
            callTenantList();
        } else {
            bindTenantToAutoCompleteText();
        }

        try {
            if (!Utils.isEmpty(VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId())) {
                callTenantEmployee();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        visitorListForIdProof = new VisitorList();
        context = VisitorEntryActivityOne.this;
        imageUploadObjects = new ArrayList<>();

        etAuthenticationKey1_visitorEntryTwo = (EditText) findViewById(R.id.etAuthenticationKey_visitorEntryTwo);
        llAuthentication = (LinearLayout) findViewById(R.id.ll_Visitor_AuthenticationKey);
        llBypassOrResend = (LinearLayout) findViewById(R.id.ll_bypassOrReset);

        btn_ScanDL = (Button) findViewById(R.id.btn_ScanDL);
        btnByPass = (Button) findViewById(R.id.btnBypass_visitorEntryTwo);
        btnByPass.setOnClickListener(this);
        btnReSendKey = (Button) findViewById(R.id.btnResendKey_visitorEntryTwo);
        btnReSendKey.setOnClickListener(this);

        btn_AddMulti = (Button) findViewById(R.id.btn_AddMulti);
        btn_AddMulti.setOnClickListener(this);
        btn_plus = (Button) findViewById(R.id.btn_Plus);
        btn_plus.setOnClickListener(this);

        btn_Add_Details = (Button) findViewById(R.id.btn_AddDetails);
        btn_Add_Details.setOnClickListener(this);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);


        tableQuestion = (TableLayout) findViewById(R.id.tableQuestion);

        //For Naugat Camera Purpose Code
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        rvPersonToMeet = (RecyclerView) findViewById(R.id.rvVisitorEntryPersonToMeet);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvPersonToMeet.setLayoutManager(layoutManager);
        toolbar_visitorEntryActivityOne = (Toolbar) findViewById(R.id.toolbar_visitorEntryActivityOne);
        btnReAuthenticate = (Button) findViewById(R.id.btnReAuthenticate);
        btnReAuthenticate.setOnClickListener(this);

        btnSubmit_visitorEntryTwo = (Button) findViewById(R.id.btnSubmit_visitorEntryTwo);
        btnSubmit_visitorEntryTwo.setOnClickListener(this);
        ivReset_visitorEntryActivityOne = (ImageView) findViewById(R.id.ivReset_visitorEntryActivityOne);
        ivReset_visitorEntryActivityOne.setOnClickListener(this);
        ivVisitorEntryIsdFlag = (ImageView) findViewById(R.id.ivVisitorEntryIsdFlag);
        ivLogo_visitorEntryActivity = (ImageView) findViewById(R.id.ivLogo_visitorEntryActivity);
        etvisitorEntryFirstName = (EditText) findViewById(R.id.etvisitorEntryFirstName);
        etvisitorEntryLastName = (EditText) findViewById(R.id.etvisitorEntryLastName);
        etvisitorEntryMobileNo = (EditText) findViewById(R.id.etvisitorEntryMobileNo);
        actvVisitorEntryIsd = (AutoCompleteTextView) findViewById(R.id.actvVisitorEntryIsd);
        etvisitorEntryEmail = (EditText) findViewById(R.id.etvisitorEntryEmail);
        ivProfile = (CircleImageView) findViewById(R.id.ivFirstImage_visitorEntryActivityOne);
        ivProfile.setOnClickListener(this);

        ivLogo_visitorEntryActivity.setOnClickListener(this);
        etvisitorEntryCompanyName = (EditText) findViewById(R.id.etvisitorEntryCompanyName);

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etvisitorEntryMobileNo));
        etvisitorEntryMobileNo.addTextChangedListener(addLineNumberFormatter);

        Utilities.setUserLogo(context, ivLogo_visitorEntryActivity);


        actv_visitorEntrypersonToMeet = (AutoCompleteTextView) findViewById(R.id.actv_visitorEntrypersonToMeet);
        actv_visitorEntrypersonToMeet.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        llPersonToMeet = (LinearLayout) findViewById(R.id.llPersonToMeet);
        llPersonToMeet.setVisibility(View.GONE);

        actvTenantName = (AutoCompleteTextView) findViewById(R.id.actvTenantName);
        etBuildingName = (EditText) findViewById(R.id.etBuildingName);
        Utilities.disableInput(etBuildingName);

        etvisitorEntrytenantName = (EditText) findViewById(R.id.etvisitorEntrytenantName);
        etvisitorEntrybuildingName = (EditText) findViewById(R.id.etvisitorEntrybuildingName);
        etvisitorEntrycomplexName = (EditText) findViewById(R.id.etvisitorEntrycomplexName);
        ll_tenantName = (LinearLayout) findViewById(R.id.ll_tenantName);
        ll_buildingName = (LinearLayout) findViewById(R.id.ll_buildingName);
        ll_complexName = (LinearLayout) findViewById(R.id.ll_complexName);
        ll_takeImage = (LinearLayout) findViewById(R.id.ll_takeImage);
        llbtnReAuth = (LinearLayout) findViewById(R.id.llbtnReAuth);
        llAddReAuth = (LinearLayout) findViewById(R.id.llAddReAuth);
        llAddReAuth.setGravity(Gravity.CENTER);
        ll_registerImage = (LinearLayout) findViewById(R.id.ll_registerImage);
        ll_VisitorEmail = (LinearLayout) findViewById(R.id.ll_VisitorEmail);
        ll_VisitorMobileNo = (LinearLayout) findViewById(R.id.ll_VisitorMobileNo);
        cvRegisteredImage = (CircleImageView) findViewById(R.id.ivLastImage_visitorEntryActivityOne);
        tvTakenPhoto = (TextView) findViewById(R.id.tvTakePhotoTextVisitorEntry);
        tvTakenPhoto.setOnClickListener(this);
        tvMobileStarVisitor = (TextView) findViewById(R.id.tvMobileStarVisitor);
        tvSexualOffendMatch = (TextView) findViewById(R.id.tvSexualOffendMatch);
        llVisitorFlagIsd = (LinearLayout) findViewById(R.id.llVisitorFlagIsd);
        llQuestion = (LinearLayout) findViewById(R.id.llQuestion);
        llQuestion.setVisibility(View.GONE);
        rvQuestions = (RecyclerView) findViewById(R.id.rvVisitorEntryQuestions);
        tvPersontoMeetStar = (TextView) findViewById(R.id.tvPersontoMeetStar);
        tvEmailStarVisitor = (TextView) findViewById(R.id.tvEmailStarVisitor);
        tv_Registered_Image = (TextView) findViewById(R.id.tv_Registered_Image);
        mDialog = new MsgDialog(this);
        mHandle = new MsgHandle(this, mDialog, false);
        myPrint = new PdfPrint(this, mHandle, mDialog);

        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getPersonToMeetRequierd()) {
            tvPersontoMeetStar.setVisibility(View.GONE);
        }


        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
            ll_takeImage.setVisibility(View.GONE);
        }

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                equalsIgnoreCase("E")) {
            tvMobileStarVisitor.setText("");
            tvEmailStarVisitor.setText("*");
            ll_VisitorMobileNo.setVisibility(View.GONE);
            ll_VisitorEmail.setVisibility(View.VISIBLE);
        } else {
            tvMobileStarVisitor.setText("*");
            tvEmailStarVisitor.setText("");
            ll_VisitorMobileNo.setVisibility(View.VISIBLE);
            ll_VisitorEmail.setVisibility(View.GONE);
        }

        if (!VisitorCheckInActivity.GlobalByPass) {

            try {
                if (VisitorCheckInActivity.ll_preAppointmentMobile.getVisibility() == View.VISIBLE) {

                    if (!VisitorCheckInActivity.actIsdCode.getText().toString().isEmpty()) {
                        actvVisitorEntryIsd.setText(VisitorCheckInActivity.actIsdCode.getText().toString());
                        actvVisitorEntryIsd.setInputType(InputType.TYPE_NULL);
                        ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage(actvVisitorEntryIsd.getText().toString()));
                        llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_bg_uneditable_for_isd));
                    }

                    if (!VisitorCheckInActivity.etMobileNo.getText().toString().isEmpty()) {
                        etvisitorEntryMobileNo.setText(VisitorCheckInActivity.etMobileNo.getText().toString());
                        Utilities.disableInput(etvisitorEntryMobileNo);
                    }
                }

                if (VisitorCheckInActivity.ll_preAppointmentEmailId.getVisibility() == View.VISIBLE) {
                    etvisitorEntryEmail.setText(VisitorCheckInActivity.etEmailId.getText().toString());
                    Utilities.disableInput(etvisitorEntryEmail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            tvMobileStarVisitor.setText(" ");
            tvEmailStarVisitor.setText(" ");
            if (VisitorCheckInActivity.ll_preAppointmentMobile.getVisibility() == View.VISIBLE) {

                if (!VisitorCheckInActivity.actIsdCode.getText().toString().isEmpty()) {
                    actvVisitorEntryIsd.setText(VisitorCheckInActivity.actIsdCode.getText().toString());
                    ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage(actvVisitorEntryIsd.getText().toString()));

                }

                if (!VisitorCheckInActivity.etMobileNo.getText().toString().isEmpty()) {
                    etvisitorEntryMobileNo.setText(VisitorCheckInActivity.etMobileNo.getText().toString());
                }
            }

            if (VisitorCheckInActivity.ll_preAppointmentEmailId.getVisibility() == View.VISIBLE) {
                etvisitorEntryEmail.setText(VisitorCheckInActivity.etEmailId.getText().toString());
            }

        }

        setPreferences();


        employeeListsCollection = new ArrayList<>();
        actv_visitorEntrypersonToMeet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!actv_visitorEntrypersonToMeet.getText().toString().isEmpty()) {
                        llPersonToMeet.setVisibility(View.VISIBLE);// if person to meet is not in the list add the employee to the list
                        VisitorLogPersonToVisit visitorLogPersonToVisit = new VisitorLogPersonToVisit();
                        visitorLogPersonToVisit.setName(actv_visitorEntrypersonToMeet.getText().toString().trim());
                        if (employeeListsCollection == null || employeeListsCollection.size() == 0) {
                            employeeListsCollection = new ArrayList<>();
                        }

                        if (employeeListsCollection.size() >= 1 && CommonPlaceForOjects.settings.getWalkinVisitorApproval()) {
                            Toast.makeText(context, R.string.walkin_approval_true, Toast.LENGTH_SHORT).show();
                            actv_visitorEntrypersonToMeet.setText("");
                        } else if (employeeListsCollection.size() >= 1 &&
                                CommonPlaceForOjects.settings != null &&
                                !CommonPlaceForOjects.settings.getAllowMultiplePersonToMeet()) {
                            actv_visitorEntrypersonToMeet.setError(getResources().getString(R.string.walkin_approval_true));
                        } else {
                            actv_visitorEntrypersonToMeet.setText("");
                            employeeListsCollection.add(visitorLogPersonToVisit);
                            /*if (personToMeetAdapter != null) {
                                personToMeetAdapter.notifyDataSetChanged();
                            } else {*/
                            personToMeetAdapter = new PersonToMeetAdapter(context, employeeListsCollection,
                                    VisitorEntryActivityOne.this, null);

                            rvPersonToMeet.setItemAnimator(new DefaultItemAnimator());
                            rvPersonToMeet.setAdapter(personToMeetAdapter);
                            personToMeetAdapter.notifyDataSetChanged();
                            //}

                        }
                    }
                }
            }
        });

        bindPersonToMeet(null);
        bindTenantBuildingName();

        try {
            if (VisitorCheckInActivity.visitorListsArrayList != null &&
                    !VisitorCheckInActivity.visitorListsArrayList.isEmpty()) {
                bindVisitorData();
            } else {
                visitorList = new VisitorList();
                if (!VisitorCheckInActivity.GlobalByPass) {
                    visitorList.setRequireAuthentication(true);
                } else {
                    visitorList.setRequireAuthentication(false);
                }
            }

        } catch (Exception e) {

        }


        /*if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
            llAuthentication.setVisibility(View.GONE);
            btnReSendKey.setVisibility(View.GONE);
            btnByPass.setVisibility(View.GONE);
        } else {
            if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                btnByPass.setVisibility(View.GONE);
            }
        }*/


       /* if (VisitorCheckInActivity.visitorListsArrayList != null &&
                !VisitorCheckInActivity.visitorListsArrayList.isEmpty() &&
                VisitorCheckInActivity.visitorListsArrayList.get(0).getRequireAuthentication() != null &&
                !VisitorCheckInActivity.visitorListsArrayList.get(0).getRequireAuthentication()) {
            if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                llAuthentication.setVisibility(View.GONE);
                btnByPass.setVisibility(View.GONE);
                btnReSendKey.setVisibility(View.GONE);
            } else {
                llAuthentication.setVisibility(View.VISIBLE);
                btnReSendKey.setVisibility(View.VISIBLE);
                btnByPass.setVisibility(View.VISIBLE);
            }
        }*/


        if (VisitorCheckInActivity.GlobalByPass) {

            llBypassOrResend.setVisibility(View.GONE);
            llAuthentication.setVisibility(View.GONE);
        } else {
            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                llAuthentication.setVisibility(View.VISIBLE);
                btnReSendKey.setVisibility(View.VISIBLE);
                //btnByPass.setVisibility(View.VISIBLE);
                if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                    btnByPass.setVisibility(View.GONE);
                } else {
                    btnByPass.setVisibility(View.VISIBLE);
                }
            } else {
                if (VisitorCheckInActivity.visitorListsArrayList != null &&
                        !VisitorCheckInActivity.visitorListsArrayList.isEmpty() &&
                        VisitorCheckInActivity.visitorListsArrayList.get(0).getRequireAuthentication() != null &&
                        !VisitorCheckInActivity.visitorListsArrayList.get(0).getRequireAuthentication()) {
                    llAuthentication.setVisibility(View.GONE);
                    btnByPass.setVisibility(View.GONE);
                    btnReSendKey.setVisibility(View.GONE);
                } else {
                    llAuthentication.setVisibility(View.VISIBLE);
                    btnReSendKey.setVisibility(View.VISIBLE);
                    //btnByPass.setVisibility(View.VISIBLE);
                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                        btnByPass.setVisibility(View.GONE);
                    } else {
                        btnByPass.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        actvVisitorEntryIsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().isEmpty()) {
                    ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage(s.toString().trim()));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    actvVisitorEntryIsd.append("+");
                }
            }
        });

//        usdlRecognizer = new UsdlRecognizer();
//        ImageSettings.enableAllImages(usdlRecognizer);
//        dlFields = new DLFields();
        btn_ScanDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scanAction(new DocumentUISettings(prepareRecognizerBundle(usdlRecognizer)));
                launchBarCodeActivity();
            }
        });



        /*etvisitorEntryMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (actvVisitorEntryIsd.getText().toString().trim().equalsIgnoreCase("+91") ||
                        actvVisitorEntryIsd.getText().toString().trim().equalsIgnoreCase("+1")) {
                    etvisitorEntryMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    etvisitorEntryMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });*/

        etvisitorEntryFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTextChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        bindQuestions(context, rvQuestions);
        populateIsdCode(context, actvVisitorEntryIsd);

        Utilities.addTextChangeListener(context, etvisitorEntryFirstName);
        Utilities.addTextChangeListener(context, etvisitorEntryLastName);
        Utilities.addTextChangeListener(context, etAuthenticationKey1_visitorEntryTwo);
        Utilities.addTextChangeListener(context, actv_visitorEntrypersonToMeet);
        Utilities.addTextChangeListenerForIsd(context, etvisitorEntryMobileNo, actvVisitorEntryIsd);
        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getPrintPass()) {
            callPrinterSettings();
        }
    }


    public void populateIsdCode(Context context, AutoCompleteTextView autoCompleteTextView) {
        final ArrayList<CountryForISD> country = Utilities.getCountryForIsdWithFlag();
        if (country != null) {
            CountryFlagAdapter countryFlagAdapter = new CountryFlagAdapter(context,
                    R.layout.activity_visitor_entry_one, R.id.country_name_tv, country);
            autoCompleteTextView.setThreshold(2);         //will start working from first character
            autoCompleteTextView.setAdapter(countryFlagAdapter);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        CountryForISD county = (CountryForISD) view.getTag();
                        if (county.getFlag() != -1) {
                            ivVisitorEntryIsdFlag.setImageResource(county.getFlag());
                        }
                    }
                }
            });

        }
    }


    private void bindVisitorData() {
        try {
            visitorList = VisitorCheckInActivity.visitorListsArrayList.get(0);

            if (visitorList != null) {

                if (CommonPlaceForOjects.settings != null &&
                        CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")) {


                    if (!Utils.isEmpty(visitorList.getIsdCode())) {
                        actvVisitorEntryIsd.setText(VisitorCheckInActivity.actIsdCode.getText().toString());
                        if (!VisitorCheckInActivity.GlobalByPass) {
                            actvVisitorEntryIsd.setInputType(InputType.TYPE_NULL);
                            ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage(actvVisitorEntryIsd.getText().toString()));
                            llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_bg_uneditable_for_isd));
                        }
                    }
                    if (!Utils.isEmpty(visitorList.getMobile())) {
                        etvisitorEntryMobileNo.setText(VisitorCheckInActivity.etMobileNo.getText().toString().trim());
                        if (!VisitorCheckInActivity.GlobalByPass) {
                            Utilities.disableInput(etvisitorEntryMobileNo);
                        }
                    }
                } else {
                    if (!Utils.isEmpty(visitorList.getEmail())) {
                        etvisitorEntryEmail.setText(visitorList.getEmail());
                        if (!VisitorCheckInActivity.GlobalByPass) {
                            Utilities.disableInput(etvisitorEntryEmail);
                        }
                    }
                }

                if (!Utils.isEmpty(visitorList.getFirstName())) {
                    etvisitorEntryFirstName.setText(visitorList.getFirstName());
                }

                if (!Utils.isEmpty(visitorList.getLastName())) {
                    etvisitorEntryLastName.setText(visitorList.getLastName());

                }

                if (!Utils.isEmpty(visitorList.getCompany())) {
                    etvisitorEntryCompanyName.setText(visitorList.getCompany());
                    //Utilities.disableInput(etvisitorEntryCompanyName);
                }

                if (!Utils.isEmpty(visitorList.getRegisteredVisitorId())) {
                    Utilities.disableInput(etvisitorEntryFirstName);
                    Utilities.disableInput(etvisitorEntryLastName);
                    llbtnReAuth.setVisibility(View.VISIBLE);
                    llAddReAuth.setGravity(0);
                }

                if (!Utils.isEmpty(visitorList.getmImageUrl())) {
                    if (visitorList.getBypassedVisitorId() != null) {
                        tv_Registered_Image.setText(getResources().getString(R.string.byPassRegisterImage));
                    }
                    ll_registerImage.setVisibility(View.VISIBLE);
                    PicassoTrustAllCerificate.getInstance(context).
                            load(visitorList.getmImageUrl()).
                            error(context.getResources().getDrawable(R.drawable.profile)).
                            placeholder(context.getResources().getDrawable(R.drawable.loading)).
                            into(cvRegisteredImage);
                }
            }

        } catch (Exception e) {

        }

    }

    private void callTenantEmployee() {
        WebRequest webRequest = new WebRequest();
        webRequest.setTenantId(Integer.valueOf(VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId()));

        webRequest.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(context));
        Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), context, true);
        Call<ResponseBody> call = ApiUtils.getAPIService().getEmployeeListByTenantId(Utilities.getToken(context), webRequest);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Utilities.hideProgress();
                Log.d("Tag", "Response Code For Employee List By Tenant Id =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            String tenantemployeelist_Response = response.body().string().toString();

                            employeeListTenantArrayList = new Gson().fromJson(tenantemployeelist_Response,
                                    new TypeToken<ArrayList<EmployeeList>>() {
                                    }.getType());

                            bindPersonToMeet(employeeListTenantArrayList);
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
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                e.printStackTrace();
            }
        });
    }

    private void bindTenantEmployeeName(ArrayList<EmployeeList> employeeListTenantArrayList) {

        ArrayList<String> tenantemployeeArrayListInString = new ArrayList<String>();

        if (!Utils.isEmpty(employeeListTenantArrayList)) {
            for (int i = 0; i < employeeListTenantArrayList.size(); i++) {
                tenantemployeeArrayListInString.add(employeeListTenantArrayList.get(i).getFirstName() + " "
                        + employeeListTenantArrayList.get(i).getLastName());
            }
            Log.d("Tag", "Tenanat Employee Name =" + new Gson().toJson(tenantemployeeArrayListInString));
            bindTenantEmployeeNameAdapter(tenantemployeeArrayListInString);
        } else {
            Utilities.showPopup(context, "", "Employee tenant list not available");
        }

    }

    private void bindTenantEmployeeNameAdapter(ArrayList<String> tenantemployeeArrayListInString) {
        if (!Utils.isEmpty(tenantemployeeArrayListInString)) {
            ArrayAdapter<String> personToMeetAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, tenantemployeeArrayListInString);
            actv_visitorEntrypersonToMeet.setThreshold(3);         //will start working from third character
            actv_visitorEntrypersonToMeet.setAdapter(personToMeetAdapter);
            actv_visitorEntrypersonToMeet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = actv_visitorEntrypersonToMeet.getAdapter().getItem(position).toString();
                    if (selectedItem != null) {
                        employee = getTenantEmployeeDetails(selectedItem);
                    }
                }
            });
        } else {
            Utilities.showPopup(context, "", "Unable to bind in tenant employee name");
        }
    }

    private void bindTenantBuildingName() {
        try {
            Profile profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
            if (profile != null && profile.getComplexName() != null) {
                etvisitorEntrycomplexName.setText(profile.getComplexName());
                Utilities.disableInput(etvisitorEntrycomplexName);
            }
            if (Utils.isEmpty(VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId())) {
                ll_tenantName.setVisibility(View.GONE);
                ll_buildingName.setVisibility(View.GONE);
            } else {
                ll_tenantName.setVisibility(View.GONE);
                etvisitorEntrytenantName.setText(VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantName());
                Utilities.disableInput(etvisitorEntrytenantName);
                ll_buildingName.setVisibility(View.GONE);
                etvisitorEntrybuildingName.setText(VisitorCheckInActivity.visitorLogMobileViewDataModel.getBuildingName());
                Utilities.disableInput(etvisitorEntrybuildingName);
            }
        } catch (Exception e) {

        }

    }


    private void bindPersonToMeet(ArrayList<EmployeeList> employeeListArrayList) {

        try {

            if (employeeListArrayList == null) {
                employeeListArrayList = new Gson().fromJson(new SPbean(context).getPreference(Constants.EMPLOYEE_LIST_RESPONSE, ""),
                        new TypeToken<ArrayList<EmployeeList>>() {
                        }.getType());
            }

            if (!employeeListArrayList.isEmpty()) {

                PersonToMeetAutoComplete personToMeetAdapter = new PersonToMeetAutoComplete(context,
                        R.layout.activity_visitor_entry_one, R.id.lbl_name, employeeListArrayList);

                actv_visitorEntrypersonToMeet.setThreshold(1);         //will start working from first character
                actv_visitorEntrypersonToMeet.setAdapter(personToMeetAdapter);
                actv_visitorEntrypersonToMeet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (view != null) {
                            EmployeeList employeeSelectedData = (EmployeeList) view.getTag();
                            VisitorLogPersonToVisit visitorLogPersonToVisit = new VisitorLogPersonToVisit();
                            visitorLogPersonToVisit.setPersonToVisitId(employeeSelectedData.getEmployeeId());
                            String LastName = employeeSelectedData.getLastName() != null ? employeeSelectedData.getLastName() : "";
                            visitorLogPersonToVisit.setName(employeeSelectedData.getFirstName() + " " + LastName.trim());
                            visitorLogPersonToVisit.setEmailPrimary(employeeSelectedData.getEmailPrimary());
                            visitorLogPersonToVisit.setDepartmentId(employeeSelectedData.getDepartmentId());
                            visitorLogPersonToVisit.setIsdCode(employeeSelectedData.getIsdCode());
                            visitorLogPersonToVisit.setMobilePrimary(employeeSelectedData.getMobilePrimary());
                            visitorLogPersonToVisit.setDepartmentName(employeeSelectedData.getDepartmentName()); //DueToFetchDepartmentNameWhichWasChangedByManishFromAPI

                            //DueToFetchDepartmentNameWhichWasChangedByManishFromAPI
                        /*if (employeeSelectedData.getDepartment() != null) {
                            visitorLogPersonToVisit.setDepartmentName(employeeSelectedData.getDepartment().getName());
                        }*/

                            actv_visitorEntrypersonToMeet.setText("");
                            actv_visitorEntrypersonToMeet.setBackground(getResources().getDrawable(R.drawable.edittext_focude_effect));
                            //employeeListsCollection.add(employeeSelectedData);
                            bindPersonToMeetToRecyclerView(visitorLogPersonToVisit);

                        }
                    }
                });
            }


        } catch (Exception e) {

        }


    }

    private void bindPersonToMeetToRecyclerView(VisitorLogPersonToVisit employeeSelectedData) {
        if (employeeListsCollection == null) {
            employeeListsCollection = new ArrayList<>();
        }

        boolean isDuplicate = false;

        for (VisitorLogPersonToVisit employeeList :
                employeeListsCollection) {
            if (employeeList.getPersonToVisitId() == employeeSelectedData.getPersonToVisitId()) {
                isDuplicate = true;
            }
        }

        if (CommonPlaceForOjects.settings == null) {
            Utilities.bindSettings(context);
        }

        if (!isDuplicate) {
            llPersonToMeet.setVisibility(View.VISIBLE);// if person to meet is not in the list add the employee to the list

            if (employeeListsCollection.size() >= 1 && CommonPlaceForOjects.settings.getWalkinVisitorApproval()) {

                Toast.makeText(context, R.string.walkin_approval_true, Toast.LENGTH_SHORT).show();

            }

            if (employeeListsCollection.size() >= 1 &&
                    CommonPlaceForOjects.settings != null &&
                    !CommonPlaceForOjects.settings.getAllowMultiplePersonToMeet()) {
                actv_visitorEntrypersonToMeet.setError(getResources().getString(R.string.walkin_approval_true));
            } else {

                employeeListsCollection.add(employeeSelectedData);
                // Add data to recyclerview

                personToMeetAdapter = new PersonToMeetAdapter(context, employeeListsCollection,
                        VisitorEntryActivityOne.this, null);

                rvPersonToMeet.setItemAnimator(new DefaultItemAnimator());
                rvPersonToMeet.setAdapter(personToMeetAdapter);
                personToMeetAdapter.notifyDataSetChanged();
            }


        } else {
            actv_visitorEntrypersonToMeet.setError("Already Added");
        }

    }

    private EmployeeList getTenantEmployeeDetails(String selectedItem) {
        for (int i = 0; i < employeeListTenantArrayList.size(); i++) {
            String s = employeeListTenantArrayList.get(i).getFirstName() + " " + employeeListTenantArrayList.get(i).getLastName();
            if (s.equals(selectedItem)) {
                return employeeListArrayList.get(i);
            }
        }
        return null;
    }


    private void bindPurposeOfVisit(Spinner spinner) {
        ArrayList<String> purposeOfVisit = new ArrayList<String>();
        if (masterResponse == null) {
            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);
        }
        if (masterResponse.getPurposeVisits() != null) {
            purposeVisitArrayList = masterResponse.getPurposeVisits();
            purposeOfVisit.add("Select");
            for (int i = 0; i < purposeVisitArrayList.size(); i++) {
                purposeOfVisit.add(purposeVisitArrayList.get(i).getDescription());
            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,
                    R.layout.spinner_display_text, purposeOfVisit);


            /*PurposeOfVisitAdapter purposeOfVisitAdapter = new PurposeOfVisitAdapter(context, android.R.layout.simple_list_item_1);
            purposeOfVisitAdapter.addAll(purposeOfVisit);*/
            spinner.setAdapter(stringArrayAdapter);
            spinner.setSelection(0);
        } else {
            Utilities.showPopup(context, "", "Purpose of visit data is not avalibale");
        }
    }

    private void bindTypeOfVisitors(Spinner spinner) {
        ArrayList<String> typeOfVisitor = new ArrayList<String>();
        if (masterResponse == null) {
            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);
        }
        if (masterResponse.getTypeOfVisitors() != null) {
            typeOfVisitorArrayList = masterResponse.getTypeOfVisitors();
            typeOfVisitor.add("Select");
            for (int i = 0; i < typeOfVisitorArrayList.size(); i++) {
                typeOfVisitor.add(typeOfVisitorArrayList.get(i).getVisitorType());
            }

            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,
                    R.layout.spinner_display_text, typeOfVisitor);

            /*TypeOfVisitorAdapter typeOfVisitorAdapter = new TypeOfVisitorAdapter(context, android.R.layout.simple_list_item_1);
            typeOfVisitorAdapter.addAll(typeOfVisitor);*/
            spinner.setAdapter(stringArrayAdapter);
            spinner.setSelection(0);
        } else {
            Utilities.showPopup(context, "", "Type of visitor data is not avalibale");
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit_visitorEntryTwo:

                if (isValid()) {
                    try {

                        String etMobile = Utilities.getReplaceText(etvisitorEntryMobileNo.getText().toString());
                        if (imageUploadObject != null) {
                            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                                    equalsIgnoreCase("E")) {
                                imageUploadObject.setEmailId(etvisitorEntryEmail.getText().toString().trim());
                            } else {
                                imageUploadObject.setMobileNo(etMobile.trim());
                            }
                            imageUploadObject.setRegisteredVisitorId(visitorList.getRegisteredVisitorId() != null ?
                                    visitorList.getRegisteredVisitorId() : 0);
                            imageUploadObject.setvName(etvisitorEntryFirstName.getText().toString().trim() + " " + etvisitorEntryLastName.getText().toString().trim());
                            imageUploadObjects.add(imageUploadObject);
                        }
                        Log.d("TAG", "Image array = " + new Gson().toJson(imageUploadObjects));

                        visitorList.setFirstName(etvisitorEntryFirstName.getText().toString());
                        visitorList.setLastName(etvisitorEntryLastName.getText().toString());
                        String etIsdCode = actvVisitorEntryIsd.getText().toString();
                        etIsdCode = etIsdCode.replaceAll("\\+", "");
                        visitorList.setIsdCode(etIsdCode.trim());
                        visitorList.setMobile(etMobile.trim());
                        visitorList.setEmail(etvisitorEntryEmail.getText().toString());
                        visitorList.setName(etvisitorEntryFirstName.getText().toString() + " " + etvisitorEntryLastName.getText().toString());


                        if (!isFromAdditional) {
                            if (isFromDL) {
                                visitorList.setStatecode(dlFields.getStateCode() != null ? dlFields.getStateCode() : "");
                                visitorList.setCity(dlFields.getCity() != null ? dlFields.getCity() : "");
                                visitorList.setZipcode(dlFields.getZipCode() != null ? dlFields.getZipCode() : "");
                                //visitorList.setCompany(VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).getCompany() != null ? VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).getCompany() : "");
                            }
                        }

                        if (VisitorCheckInActivity.visitorListsArrayList != null &&
                                VisitorCheckInActivity.visitorListsArrayList.isEmpty()) {

                            VisitorCheckInActivity.visitorListsArrayList.add(visitorList);
                        }

                        try {
                            final QuestionAdapter finalQuestionAdapter = questionAdapter;
                            if (finalQuestionAdapter != null) {
                                visitorRequiredQuestionArrayList = finalQuestionAdapter.getFilledQuestions();
                                ArrayList<VisitorQuestion> visitorQuestions = new ArrayList<>();
                                for (VisitorQuestion question : visitorQuestionArrayList) {
                                    if (question.getAnswer() != null && !question.getAnswer().isEmpty()) {
                                        visitorQuestions.add(question);
                                    }
                                }
                                if (visitorQuestions.size() >= 1) {
                                    visitorRequiredQuestionArrayList.addAll(visitorQuestions);
                                }
                                visitorList.setVisitorQuestions(visitorRequiredQuestionArrayList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Append person to meet data
                        VisitorCheckInActivity.visitorLogMobileViewDataModel.setVisitorLogPersonToVisit(employeeListsCollection);

                        if (ll_buildingName.getVisibility() == View.VISIBLE) {
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingName(null);
                        }
                        if (ll_tenantName.getVisibility() == View.VISIBLE) {
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantName(null);
                        }

                        VisitorCheckInActivity.visitorLogMobileViewDataModel.setVisitorList(VisitorCheckInActivity.visitorListsArrayList);
                        VisitorCheckInActivity.visitorLogMobileViewDataModel.setNoOfVisitor(VisitorCheckInActivity.visitorListsArrayList.size());

                        if (VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).getRegisteredVisitorId() != null) {
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).setRegisteredVisitorId(VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).getRegisteredVisitorId());
                        } else {
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setRegisteredVisitorId(null);
                        }
                        if (llAuthentication.getVisibility() == View.VISIBLE) {

                            String authenticationKey = etAuthenticationKey1_visitorEntryTwo.getText().toString();
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList()
                                    .get(0).setAuthenticationKey(authenticationKey);
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0).setRequireAuthentication(true);
                        }

                        if (VisitorCheckInActivity.GlobalByPass || byPass) {

                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setRequireAuthentication(false);

                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setIsAuthenticationByPassed(true);

                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setIsAuthenticationByPassedBy(VisitorCheckInActivity.GlobalByPassedBy);

                        }

                        if (VisitorCheckInActivity.GlobalByPassedBy > 0) {
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setIsAuthenticationByPassedBy(VisitorCheckInActivity.GlobalByPassedBy);
                        }

                       /* if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getNoAuthentication()) {

                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setRequireAuthentication(false);

                            VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                    .setIsAuthenticationByPassed(true);

                            Profile profile = null;
                            if (new SPbean(this).getPreference(Constants.PROFILE_RESPONSE, "") != null) {
                                String profileString = new SPbean(this).getPreference(Constants.PROFILE_RESPONSE, "");
                                profile = new Gson().fromJson(profileString, Profile.class);
                            }

                            if (profile != null) {
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.getVisitorList().get(0)
                                        .setIsAuthenticationByPassedBy(profile.getEmployeeId());
                            }

                        }*/
                        VisitorEntryModel visitorEntryModel = new VisitorEntryModel();
                        visitorEntryModel.setVisitorLogMobileViewModel(VisitorCheckInActivity.visitorLogMobileViewDataModel);


                        Log.d("Tag", "Visitor  =" + new Gson().toJson(VisitorCheckInActivity.visitorLogMobileViewDataModel));


                        if (!etvisitorEntryLastName.getText().toString().isEmpty()) {
                            CallSexualOffender(etvisitorEntryFirstName.getText().toString().trim() + " " + etvisitorEntryLastName.getText().toString().trim(), visitorEntryModel);
                        } else {
                            CallSexualOffender(etvisitorEntryFirstName.getText().toString(), visitorEntryModel);
                        }


                        //callVisitorEntry(visitorEntryModel);

                        //Intent intent = new Intent(this, VisitorEntryActivityTwo.class);
                        //startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;

            case R.id.btn_Plus:
                Intent i = new Intent(VisitorEntryActivityOne.this, AdditionalVisitorActivity.class);
                startActivity(i);
                break;

            case R.id.ivLogo_visitorEntryActivity:
                Utilities.redirectToHome(context);
                break;

            case R.id.btn_AddMulti:
                try {
                    //Toast.makeText(context, "List Count = " + PlaceSelectionActivity.visitorListsArrayList.size(), Toast.LENGTH_SHORT).show();
                    if (VisitorCheckInActivity.visitorListsArrayList != null && VisitorCheckInActivity.visitorListsArrayList.size() > 1) {
                        Intent intent = new Intent(context, AdditionalVisitorList.class);
                        startActivity(intent);
                    } else {
                        Intent i1 = new Intent(VisitorEntryActivityOne.this, AdditionalVisitorActivity.class);
                        startActivity(i1);
                    }


                } catch (Exception e) {

                }

                break;


            case R.id.btn_AddDetails:
                showAdditionalDetailDialog();
                break;


            case R.id.btnResendKey_visitorEntryTwo:

                if (VisitorCheckInActivity.visitorListsArrayList.get(0).getMobile() != null &&
                        !VisitorCheckInActivity.visitorListsArrayList.get(0).getMobile().isEmpty() ||
                        !VisitorCheckInActivity.visitorListsArrayList.get(0).getEmail().isEmpty()) {

                    if (!clickedOnce) {
                        try {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("isdCode", VisitorCheckInActivity.visitorListsArrayList.get(0).getIsdCode());
                            map.put("mobile", VisitorCheckInActivity.visitorListsArrayList.get(0).getMobile());
                            map.put("email", VisitorCheckInActivity.visitorListsArrayList.get(0).getEmail());
                            map.put("complexId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId());
                            map.put("tenantId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId());
                            map.put("requestClientDetails", Utilities.requestclientDetails(context));
                            Utilities.showprogressDialogue("", "please wait", context, false);
                            mathodCallToReSendOtp(map);
                            clickedOnce = true;
                            btnReSendKey.setEnabled(false);
                            btnReSendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "You Have Not Entered Mobile Number", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnBypass_visitorEntryTwo:

                if (VisitorCheckInActivity.GlobalByPass) {
                    llAuthentication.setVisibility(View.GONE);
                    llBypassOrResend.setVisibility(View.GONE);
                } else if (VisitorCheckInActivity.locallyBypassedOnce) {
                    llAuthentication.setVisibility(View.GONE);
                    llBypassOrResend.setVisibility(View.GONE);
                    byPass = true;
                } else {
                    final Dialog builder = new Dialog(context);
                    builder.setContentView(R.layout.login_confirmation_dialog);
                    builder.setCancelable(true);
                    builder.show();
                    initConfirmDialog(builder);
                }
                break;

            case R.id.ivReset_visitorEntryActivityOne:
                Intent intent1 = new Intent(this, HomeScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                onBackPressedFlag = false;
                clickedOnce = false;
                byPass = false;
                //onBackPressed();


                break;

            case R.id.ivFirstImage_visitorEntryActivityOne:

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);

                } else {
                    if (Utilities.getDevice().equalsIgnoreCase("samsung")) {
                        captureVisitorImageCamera2();
                    } else {
                        captureDocumentImage();
                    }

                }

                break;

            case R.id.tvTakePhotoTextVisitorEntry:
                if (tvTakenPhoto.getText().toString().equalsIgnoreCase(getResources().getString(R.string.reset_forimage))) {
                    try {
                        tvTakenPhoto.setText(getResources().getString(R.string.live_picture));
                        ivProfile.setImageBitmap(null);
                        ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
                        imageUploadObject = null;

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.ivSos:
                Utilities.showDialogForSos(VisitorEntryActivityOne.this);
                break;

            case R.id.btnReAuthenticate:
                if (Utilities.isInternetConnected(context)) {
                    callGetAuthenticateVisitor();
                } else {
                    Utilities.showNoInternetPopUp(context);
                }

                break;
        }
    }

    public void NDADialogForVisitor(final VisitorEntryModel visitorEntryModel){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nda_visitor_entry);
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.iv_nda_Cancel);
        final PaintView mPaintView;
        LinearLayout mLlCanvas;
        WebView nda_wv;

        mLlCanvas = dialog.findViewById(R.id.sign_ll);
        nda_wv = dialog.findViewById(R.id.nda_wv);
        nda_wv.setHorizontalScrollBarEnabled(false);
        nda_wv.loadData(getString(R.string.nda_sample),"text/html", "UTF-8");
        mPaintView = new PaintView(this, null);
        mLlCanvas.addView(mPaintView, 0);

        mPaintView.setPathColor(Color.BLACK);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        Button SaveBtn=dialog.findViewById(R.id.btnNDAConfirm);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPaintView.isSigned) {
                    savingFile(mPaintView);
                    dialog.dismiss();
                    callVisitorEntry(visitorEntryModel);
                }else{
                    Toast.makeText(context, "Please sign above, then proceed..", Toast.LENGTH_SHORT).show();
                }
                // Add_Front();
            }
        });


        ImageView ClearBtn = dialog.findViewById(R.id.iv_clear);
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPaintView.clear();
            }
        });

        dialog.show();
    }

    //saving drawed file to SD card
    private void savingFile(PaintView mPaintView) {
        File sdCard = Environment.getExternalStorageDirectory();
        File folder = new File(sdCard.getAbsolutePath() + "/MyFiles");

        boolean success = false;
        if (!folder.exists()) {
            success = folder.mkdirs();
            Log.i("", " " + success);
        }

        File file = new File(folder, "sign_"+new Date().getTime()+".png");

        if (!file.exists()) {
            try {
                success = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream ostream = null;
        try {
            ostream = new FileOutputStream(file);

            Bitmap well = mPaintView.getBitmap();
            Bitmap save = Bitmap.createBitmap(500, 300, Bitmap.Config.ARGB_8888);
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            Canvas now = new Canvas(save);
            now.drawRect(new Rect(0, 0, 500, 300), paint);
            now.drawBitmap(well, new Rect(0, 0, well.getWidth(), well.getHeight()), new Rect(0, 0, 500, 300), null);

            save.compress(Bitmap.CompressFormat.PNG, 70, ostream);
            BitMapToString(save);

//            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to save your sign.",
                    Toast.LENGTH_SHORT).show();
        }

        catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File error", Toast.LENGTH_SHORT).show();
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Front_Image = Base64.encodeToString(b, Base64.DEFAULT);
        return Front_Image;
    }

    String Front_Image="";

    private void callVisitorEntry(VisitorEntryModel visitorEntryModel) {

        Log.d("Tag", "Visitor entry request =" + new Gson().toJson(visitorEntryModel));
        Utilities.showprogressDialogue(getString(R.string.registering), getString(R.string.please_wait), context, true);
        Call<ResponseBody> call = ApiUtils.getAPIService().visitorEntry(Utilities.getToken(context), visitorEntryModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("Tag", "Response Code For Visitor Entry = " + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    if (response != null) {
                        try {
                            //parse response here
                            Gson gson = new Gson();
                            String data = response.body().string().toString();
                            boolean isUpload = false;
                            model = new VisitorLogMobileViewModel();
                            model = gson.fromJson(data, VisitorLogMobileViewModel.class);
                            VisitorEntryActivityOne.imageUploadObjects = bindVisitorIdToImage(model.getVisitorList());
                            if (VisitorEntryActivityOne.imageUploadObjects != null && !VisitorEntryActivityOne.imageUploadObjects.isEmpty()) {
                                for (int i = 0; i < VisitorEntryActivityOne.imageUploadObjects.size(); i++) {
                                    ImageUploadObject object = VisitorEntryActivityOne.imageUploadObjects.get(i);
                                    if (object.getUrl() != null && !object.getUrl().isEmpty()) {
                                        isUpload = true;
                                    }

                                }
                            }
                            if (isUpload) {
                                if (model.getMessage() != null && !model.getMessage().isEmpty()) {
                                    Toast.makeText(VisitorEntryActivityOne.this, "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                new SPbean(context).setPreference(Constants.DATA_TO_UPLOADED, gson.toJson(VisitorEntryActivityOne.imageUploadObjects));
                                Utilities.hideProgress();
                                UploadImageTask task = new UploadImageTask();
                                task.execute();
                            } else {
                                if (model != null) {
                                    String nextAction = "";
                                    if (model.getNextAction() != null) {
                                        nextAction = model.getNextAction().trim();
                                    }
                                    if (nextAction.equalsIgnoreCase(Constants.PRINT_PASS)) {
                                        if (CommonPlaceForOjects.settings.getPrintPass()) {
                                            //Utilities.hideProgress();
                                            visitorLogId = model.getVisitorLogId();
                                            //lastvisitor = model.getNoOfVisitor();
                                            //callApiForPrintPass(model.getVisitorLogId());
                                            if (!isPrinterNotAvailable) {
                                                callPrintPassFromHandler();
                                            } else {
                                                Utilities.hideProgress();
                                                Runnable runnable = new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        startActivity(new Intent(VisitorEntryActivityOne.this,
                                                                HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                    }
                                                };
                                                Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), true, runnable);

                                            }
                                        } else {
                                            Utilities.hideProgress();
                                            Runnable runnable = new Runnable() {
                                                @Override
                                                public void run() {
                                                    startActivity(new Intent(VisitorEntryActivityOne.this,
                                                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                }
                                            };
                                            Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), true, runnable);
                                        }

                                    } else if (nextAction.equalsIgnoreCase(Constants.WAIT_FOR_HOST_APPROVAL)) {
                                        Utilities.hideProgress();
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(VisitorEntryActivityOne.this,
                                                        HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }
                                        };
                                        Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), true, runnable);

                                    } else {
                                        Utilities.hideProgress();
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(VisitorEntryActivityOne.this,
                                                        HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }
                                        };
                                        Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), true, runnable);
                                    }
                                }
                                onBackPressedFlag = true;
                            }

                        } catch (Exception e) {
                            //write log method
                            Utilities.hideProgress();
                            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                            e.printStackTrace();
                        }

                        /*Intent intent1 = new Intent(context, VisitorCheckInActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);*/


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

    @Override
    public void image_attachment(int from, File fileloaction, String filename, Bitmap file, Uri uri) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        file.compress(Bitmap.CompressFormat.JPEG, 25 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(fileloaction);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File iconFile = new File(fileloaction.getPath());
            Bitmap image = BitmapFactory.decodeFile(iconFile.getAbsolutePath());
            if (image != null) {

                ivProfile.setImageBitmap(image);
                tvTakenPhoto.setText(getResources().getString(R.string.reset_forimage));
                //Log.d("TAG", "Image policy = " + fileUri.getPath());
                imageUploadObject = new ImageUploadObject();
                imageUploadObject.setUrl(uri.getPath());
                isFrontCamera = LoginActivity.isSelfeHeplKiosk;
            } else {
                Utilities.showPopup(VisitorEntryActivityOne.this, "", "No Image Found");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n" + getLocalClassName());
        }
    }

    private class UploadImageTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utilities.showprogressDialogue(getString(R.string.uploadingImage), getString(R.string.please_wait), context, false);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                if (VisitorEntryActivityOne.imageUploadObjects != null) {

                    for (int i = 0; i < VisitorEntryActivityOne.imageUploadObjects.size(); i++) {
                        ImageUploadObject object = VisitorEntryActivityOne.imageUploadObjects.get(i);
                        boolean isUploaded = FileTransferHelper.uploadFile(context, object.getVisitorid(), object.getRegisteredVisitorId(),
                                object.getByPassVisitorId(), new File(object.getUrl()));

                        if (!isUploaded) {
                            Log.d("TAG", "Upload failed");
                        }
                    }
                }

            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                try {
                    if (model != null) {
                        String nextAction = "";
                        if (model.getNextAction() != null) {
                            nextAction = model.getNextAction().trim();
                        }
                        Log.d("TAG", "Next Action" + " " + nextAction);
                        if (nextAction.equalsIgnoreCase(Constants.PRINT_PASS)) {
                            if (CommonPlaceForOjects.settings.getPrintPass()) {
                                visitorLogId = model.getVisitorLogId();
                                //lastvisitor = model.getNoOfVisitor();
                                isFromUpload = true;
                                //callApiForPrintPass(model.getVisitorLogId());
                                if (!isPrinterNotAvailable) {
                                    Utilities.hideProgress();
                                    callPrintPassFromHandler();
                                } else {
                                    Utilities.hideProgress();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(VisitorEntryActivityOne.this,
                                                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    };
                                    Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);

                                }
                            } else {
                                Utilities.hideProgress();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(VisitorEntryActivityOne.this,
                                                HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    }
                                };
                                Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);
                            }
                        } else if (nextAction.equalsIgnoreCase(Constants.WAIT_FOR_HOST_APPROVAL)) {
                            Utilities.hideProgress();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(VisitorEntryActivityOne.this,
                                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            };
                            Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);

                        } else {
                            Utilities.hideProgress();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(VisitorEntryActivityOne.this,
                                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            };
                            Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);
                        }
                        onBackPressedFlag = true;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                Utilities.hideProgress();
                Toast.makeText(context, R.string.visitor_entry_successfull, Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(context, HomeScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
            }
            /*Toast.makeText(context, R.string.visitor_entry_successfull, Toast.LENGTH_SHORT).show();

            Intent intent1 = new Intent(context, VisitorCheckInActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);*/

        }
    }

    private void callApiForPrintPass(int visitorLogId) {
       /* String pass = AppConfig.PRINT_PASS_LINK + "visitorId=" + "0" + "&VisitorLogId=" + visitorLogId;
        Log.d("TAG", "Pass String = " + pass);

        if (!pass.isEmpty() && Utilities.isValidURL(pass)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String htmlData = "";

                //htmlData = Utilities.gethtmlStringFromUrl(pass);
                final WebView webView = new WebView(context);
                //webView.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");

                webView.loadUrl(pass);
                webView.setWebViewClient(new WebViewClient() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onPageFinished(WebView view, String url) {
                        Utilities.hideProgress();
                        // do your stuff here
                        Utilities.createWebPagePrint(webView, context);
                    }
                });

            } else {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, pass);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);

            }
        }*/
        Log.d("TAG", "Visitor id = " + visitorLogId);
        Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
        HashMap<String, Object> map = new HashMap<>();
        //map.put("visitorId", visitorId);
        map.put("VisitorLogId", visitorLogId);
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Call<ResponseBody> call = ApiUtils.getAPIService().callPrintPassNew(Utilities.getToken(context), map);
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

    private ArrayList<ImageUploadObject> bindVisitorIdToImage(ArrayList<VisitorList> model) {

        try {

            for (int j = 0; j < VisitorEntryActivityOne.imageUploadObjects.size(); j++) {
                ImageUploadObject object = VisitorEntryActivityOne.imageUploadObjects.get(j);

                for (int i = 0; i < model.size(); i++) {
                    VisitorList temp = model.get(i);


                    if (CommonPlaceForOjects.settings != null &&
                            CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")) {
                        if (temp.getMobile().toString().trim().equals(object.getMobileNo())) {
                            if (temp.getVisitorId() != null) {
                                object.setVisitorid(temp.getVisitorId());
                            } else {
                                object.setVisitorid(0);
                            }                                           //DoneByJunedAnsari18June2019
                            if (temp.getRegisteredVisitorId() != null && object.getRegisteredVisitorId() == 0) {
                                object.setRegisteredVisitorId((Integer) temp.getRegisteredVisitorId());
                            } else {
                                object.setRegisteredVisitorId(0);
                            }
                            if (temp.getBypassedVisitorId() != null) {

                                object.setByPassVisitorId((Long) temp.getBypassedVisitorId());
                            } else {
                                object.setByPassVisitorId(0);
                            }
                        }
                    } else {
                        if (temp.getEmail().toString().trim().equals(object.getEmailId())) {
                            if (temp.getVisitorId() != null) {
                                object.setVisitorid(temp.getVisitorId());
                            } else {
                                object.setVisitorid(0);
                            }
                            if (temp.getRegisteredVisitorId() != null && object.getRegisteredVisitorId() == 0) {
                                object.setRegisteredVisitorId(temp.getRegisteredVisitorId());
                            } else {
                                object.setRegisteredVisitorId(0);
                            }
                            if (temp.getBypassedVisitorId() != null) {

                                object.setByPassVisitorId((Long) temp.getBypassedVisitorId());
                            } else {
                                object.setByPassVisitorId(0);
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }

        return VisitorEntryActivityOne.imageUploadObjects;
    }

    public void showAdditionalDetailDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_additional_details);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.ivAddDetailCancel);
        final EditText etCompany = (EditText) dialog.findViewById(R.id.etAddDetailCompanyName);
        final EditText etBadge = (EditText) dialog.findViewById(R.id.etAddDetailBadgeNo);
        final EditText etDLNumber = (EditText) dialog.findViewById(R.id.etAddDetailDLNumber);
        final EditText etDOB = (EditText) dialog.findViewById(R.id.etAddDetailDOB);
        final EditText etStateCode = (EditText) dialog.findViewById(R.id.etAddDetailState);
        final EditText etCity = (EditText) dialog.findViewById(R.id.etAddDetailNationality);
        final EditText etZipCode = (EditText) dialog.findViewById(R.id.etAddDetailCountry);
        final Spinner spnTypeOfVisitor = (Spinner) dialog.findViewById(R.id.spnAddDetail_TypeOfVisitor);
        final Spinner spnPurposeOfVisit = (Spinner) dialog.findViewById(R.id.spnAddDetail_purposeOfVisit);
        final LinearLayout llQuestion = (LinearLayout) dialog.findViewById(R.id.llQuestion);

        bindTypeOfVisitors(spnTypeOfVisitor);
        bindPurposeOfVisit(spnPurposeOfVisit);


        if (!Utils.isEmpty(visitorList.getCompany())) {
            etCompany.setText(visitorList.getCompany());
            if (visitorList.getRegisteredVisitorId() != null && visitorList.getRegisteredVisitorId() != 0) {
                Utilities.disableInput(etCompany);
            }
        } else {
            if (additionalDetail.getmCompany() != null && !additionalDetail.getmCompany().isEmpty()) {
                etCompany.setText(additionalDetail.getmCompany());
            }
        }

        if (visitorList.getRegisteredVisitorId() != null && visitorList.getRegisteredVisitorId() != 0) {
            Utilities.disableInput(etCompany);
        }

        if (additionalDetail.getmAccessCardNo() != null && !additionalDetail.getmAccessCardNo().isEmpty()) {
            etBadge.setText(additionalDetail.getmAccessCardNo());
        }

        /*if (additionalDetail.getStatecode() != null && !additionalDetail.getStatecode().isEmpty()) {
            etStateCode.setText(additionalDetail.getStatecode());
        }if (additionalDetail.getCity() != null && !additionalDetail.getCity().isEmpty()) {
            etCity.setText(additionalDetail.getCity());
        }if (additionalDetail.getZipcode() != null && !additionalDetail.getZipcode().isEmpty()) {
            etZipCode.setText(additionalDetail.getZipcode());
        }if (additionalDetail.getIdnumber() != null && !additionalDetail.getIdnumber().isEmpty()) {
            etDLNumber.setText(additionalDetail.getIdnumber());
        }if (additionalDetail.getBirthdate() != null && !additionalDetail.getBirthdate().isEmpty()) {
            etDOB.setText(additionalDetail.getBirthdate());
        }*/

        if (!Utils.isEmpty(additionalDetail.getmPurposeOfVisitId())) {
            int position = Utilities.getPurposeToVisitById(masterResponse, additionalDetail.getmPurposeOfVisitId());
            spnPurposeOfVisit.setSelection(position);
        }

        if (!Utils.isEmpty(additionalDetail.getmTypeOfVisitorId())) {
            int position = Utilities.getTypeOfVisitorById(masterResponse, additionalDetail.getmTypeOfVisitorId());
            spnTypeOfVisitor.setSelection(position);
        }

        if (dlFields != null) {
            etDLNumber.setText(dlFields.getDocumentNumber() != null ? dlFields.getDocumentNumber() : "");
            etStateCode.setText(dlFields.getStateCode() != null ? dlFields.getStateCode() : "");
            etCity.setText(dlFields.getCity() != null ? dlFields.getCity() : "");
            etZipCode.setText(dlFields.getZipCode() != null ? dlFields.getZipCode() : "");
            etDOB.setText(dlFields.getDateOfBirth() != null ? dlFields.getDateOfBirth() : "");

        }


        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        RecyclerView rv_questions = (RecyclerView) dialog.findViewById(R.id.rv_AddDetailQuestions);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnAddDetailConfirm);


        if (visitorQuestionArrayList.isEmpty()) {
            final MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                    getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);

            ArrayList<Question> questionArrayList = new ArrayList<Question>();
            ArrayList<Question> questionList = new ArrayList<Question>();
            questionArrayList = masterResponse.getQuestions();
            for (Question question : questionArrayList) {
                if (!question.getRequired()) {
                    questionList.add(question);
                }
            }

            QuestionAdapter questionAdapter = null;
            if (questionList.size() > 0) {
                llQuestion.setVisibility(View.VISIBLE);
                questionAdapter = new QuestionAdapter(context, questionList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                rv_questions.setLayoutManager(mLayoutManager);
                rv_questions.setAdapter(questionAdapter);
            } else {
                llQuestion.setVisibility(View.GONE);
            }

            final QuestionAdapter finalQuestionAdapter = questionAdapter;
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFromAdditional = true;
                    visitorList.setCompany(etCompany.getText().toString());
                    visitorList.setAccessCardNo(etBadge.getText().toString());
                    visitorList.setBirthdate(etDOB.getText().toString());
                    visitorList.setZipcode(etZipCode.getText().toString());
                    visitorList.setCity(etCity.getText().toString());
                    visitorList.setStatecode(etStateCode.getText().toString());
                    visitorList.setIdnumber(etDLNumber.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                            spnTypeOfVisitor.getSelectedItem().toString()));
                    visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                            spnPurposeOfVisit.getSelectedItem().toString()));
                    additionalDetail = data(visitorList);
                    if (finalQuestionAdapter != null) {
                        visitorQuestionArrayList = finalQuestionAdapter.getFilledQuestions();
                    }
                    dialog.dismiss();
                }
            });
        } else {
            final VisitorQuestionAdapter visitorQuestionAdapter = new VisitorQuestionAdapter(context, visitorQuestionArrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_questions.setLayoutManager(mLayoutManager);
            rv_questions.setAdapter(visitorQuestionAdapter);

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFromAdditional = true;
                    visitorList.setCompany(etCompany.getText().toString());
                    visitorList.setAccessCardNo(etBadge.getText().toString());
                    visitorList.setBirthdate(etDOB.getText().toString());
                    visitorList.setZipcode(etZipCode.getText().toString());
                    visitorList.setCity(etCity.getText().toString());
                    visitorList.setStatecode(etStateCode.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    visitorList.setIdnumber(etDLNumber.getText().toString());
                    visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                            spnTypeOfVisitor.getSelectedItem().toString()));
                    visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                            spnPurposeOfVisit.getSelectedItem().toString()));
                    additionalDetail = data(visitorList);
                    if (visitorQuestionAdapter != null) {
                        visitorQuestionArrayList = visitorQuestionAdapter.getFilledQuestions();
                    }
                    dialog.dismiss();

                }
            });
        }


        //bindQuestions(this,rv_questions);
        dialog.show();
    }

    public AdditionalDetail data(VisitorList visitorList) {
        AdditionalDetail data = new AdditionalDetail();
        data.setmCompany(visitorList.getCompany());
        data.setBirthdate(visitorList.getBirthdate());
        data.setStatecode(visitorList.getStatecode());
        data.setZipcode(visitorList.getZipcode());
        data.setCity(visitorList.getCity());
        data.setIdnumber(visitorList.getIdnumber());
        data.setmCompany(visitorList.getCompany());
        data.setmAccessCardNo(visitorList.getAccessCardNo());

        if (visitorList.getTypeOfVisitorId() != null) {
            data.setmTypeOfVisitorId(visitorList.getTypeOfVisitorId());
        }
        if (visitorList.getPurposeOfVisitId() != null) {
            data.setmPurposeOfVisitId(visitorList.getPurposeOfVisitId());
        }
        return data;
    }

    public boolean isErrorEditText(ArrayList<VisitorQuestion> questions) {
        int error = 0;
        ArrayList<VisitorQuestion> visitorQuestions = questions;
        for (VisitorQuestion question : visitorQuestions)
            if (question.isRequired() && question.getAnswer() == null) {
                error++;
            } else if (question.isRequired() && question.getAnswer() != null && question.getAnswer().isEmpty()) {
                error++;
            }

        return !(error > 0);
    }

    public boolean isEditTextError(EditText editText, VisitorQuestion question) {
        int error = 0;
        try {
            if (question.isRequired() && question.getAnswer() == null) {
                error++;
                editText.setBackground(getResources().getDrawable(R.drawable.edittext_error));

            } else if (question.isRequired() && question.getAnswer() != null && question.getAnswer().isEmpty()) {

                error++;
                editText.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return !(error > 0);
    }


    public boolean isQuestionValid(ArrayList<VisitorQuestion> questions) {
        int error = 0;


        try {
            //ArrayList<VisitorQuestion> questions= questionAdapter.getFilledQuestions();
            for (VisitorQuestion question : questions) {

                if (question.isRequired() && question.getAnswer() == null) {

                    error++;
                    Toast.makeText(context, "Please fill mandatory Questions", Toast.LENGTH_SHORT).show();
                    break;

                } else if (question.isRequired() && question.getAnswer() != null && question.getAnswer().isEmpty()) {

                    error++;
                    Toast.makeText(context, "Please fill mandatory Questions", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return !(error > 0);
    }

    public int getMandatoryQuestionsCount() {
        /*MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);*/

        int count = 0;

        ArrayList<VisitorQuestion> questionArrayList = new ArrayList<VisitorQuestion>();
        if (visitorQuestionArrayList != null) {

            questionArrayList = visitorQuestionArrayList;

            for (VisitorQuestion question :
                    questionArrayList) {
                if (question.isRequired()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void bindQuestions(Context context, RecyclerView recyclerView) {
        MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);


        ArrayList<Question> questionArrayList = new ArrayList<Question>();
        ArrayList<Question> questionList = new ArrayList<Question>();
        if (masterResponse.getQuestions() != null) {

            questionArrayList = masterResponse.getQuestions();
            for (Question question : questionArrayList) {
                if (question.getRequired()) {
                    questionList.add(question);
                }
            }

            if (questionList.size() > 0) {
                llQuestion.setVisibility(View.VISIBLE);
                questionAdapter = new QuestionAdapter(context, questionList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(questionAdapter);
            } else {
                llQuestion.setVisibility(View.GONE);
            }

            /*tableQuestion.removeAllViews();
            lstEtAns = new ArrayList<EditText>();
            for (Question question : questionList) {
                tableQuestion.addView(addQuestionView(question));
            }*/

        }
    }

    private View addQuestionView(Question question) {
        LinearLayout dynQueAnsLayout = new LinearLayout(
                context);

        LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1);
//        dynQueAnsLayout.setPadding(15,15,15,15);
        dynQueAnsLayout.setPadding(10, 10, 10, 10);
        dynQueAnsLayout.setLayoutParams(horizontal_params);
        dynQueAnsLayout.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams tv_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView dynTvQuestion = new TextView(context);

        Typeface fonts = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-Regular.ttf");
        dynTvQuestion.setTypeface(fonts);
        dynTvQuestion.setLayoutParams(tv_params);
        dynTvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.longtextsize));
        dynTvQuestion.setTextAppearance(context, R.style.customMontserratRegular);
        dynTvQuestion.setTextColor(context.getResources().getColor(R.color.black));
        dynTvQuestion.setText(question.getDescription());

        LinearLayout.LayoutParams et_params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        EditText dynEtAns = new EditText(context);
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/Montserrat-Regular.ttf");
        dynEtAns.setTypeface(font);
        dynEtAns.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.longtextsize));
        dynEtAns.setTextAppearance(context, R.style.customMontserratRegular);
        dynEtAns.setHint(getResources().getString(R.string.answer));
        dynEtAns.setTextColor(context.getResources().getColor(R.color.black));
        dynEtAns.setBackground(getResources().getDrawable(R.drawable.edittext_focude_effect));
        dynEtAns.setLayoutParams(et_params);
        et_params.setMargins(0, 15, 25, 0);

        // Setting max length to dynamic edit text
        InputFilter[] inFilterArr = new InputFilter[1];
        inFilterArr[0] = new InputFilter.LengthFilter(50);
        dynEtAns.setFilters(inFilterArr);
        tv_params.topMargin = (int) Utilities.convertDptoFloat(
                context, 5);
        dynEtAns.setEms(10);
        dynEtAns.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        lstEtAns.add(dynEtAns);


        LinearLayout.LayoutParams vvparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);

        /*View view = new View(context);
        view.setBackgroundResource(R.color.border_color);
        view.setLayoutParams(vvparams);*/

        //dynQueAnsLayout.addView(view);
        dynQueAnsLayout.addView(dynTvQuestion);
        dynQueAnsLayout.addView(dynEtAns);


        return dynQueAnsLayout;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (onBackPressedFlag) {
            startActivity(new Intent(VisitorEntryActivityOne.this,
                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            onBackPressedFlag = false;
            clickedOnce = false;
            byPass = false;
        }

        //dissable resend button
        if (clickedOnce) {
            btnReSendKey.setEnabled(false);
            btnReSendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
        }


        try {
            if (VisitorCheckInActivity.visitorListsArrayList != null &&
                    !VisitorCheckInActivity.visitorListsArrayList.isEmpty() &&
                    VisitorCheckInActivity.visitorListsArrayList.size() > 1) {

                btn_plus.setBackgroundColor(getResources().getColor(R.color.versionTextColor));
                btn_AddMulti.setBackground(getResources().getDrawable(R.drawable.btn_blue_bg));
                btn_AddMulti.setTextColor(getResources().getColor(R.color.white));
                btn_AddMulti.setText(R.string.btn_text_multi_visitor);

            } else {
                btn_plus.setBackgroundColor(getResources().getColor(R.color.versionTextColor));
                btn_AddMulti.setBackground(getResources().getDrawable(R.drawable.grey_bg));
                btn_AddMulti.setTextColor(getResources().getColor(R.color.blueColor));
                btn_AddMulti.setText(R.string.button_add);
            }


        } catch (Exception e) {

        }
    }

    private void mathodCallToReSendOtp(HashMap<String, Object> map) {
        Call<ResponseBody> call = ApiUtils.getAPIService().callReSendOtp(Utilities.getToken(context), map);
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


    private void initConfirmDialog(final Dialog builder) {
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
                    map.put("language", new SPbean(context).getPreference(Constants.LANGUAGE_CODE, ""));
                    map.put("requestClientDetails", Utilities.requestclientDetails(context));

                    Utilities.showprogressDialogue("", "please wait", context, false);
                    callByPassLogin(map, builder);
                }
            }
        });
    }

    // call byPass api for bypassing appointment
    private void callByPassLogin(HashMap<String, Object> map, final Dialog builder) {
        Call<ResponseBody> call = ApiUtils.getAPIService().callByPassLogin(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("TAG", "Response code  = " + response.code());
                if (response.code() == 200) {
                    try {

                        String result = response.body().string().toString();
                        Profile profile = new Gson().fromJson(result, Profile.class);
                        if (profile != null) {
                            hideFields(profile.getEmployeeId());
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

    public void hideFields(int employeeId) {

        llAuthentication.setVisibility(View.GONE);
        llBypassOrResend.setVisibility(View.GONE);

        localBypass = true;
        requireAuthentication = false;
        byPass = true;
        VisitorCheckInActivity.GlobalByPassedBy = employeeId;
        VisitorCheckInActivity.locallyBypassedOnce = true;

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


    @SuppressLint("ResourceType")
    private boolean isValid() {
        int error = 0;

        String etMobile = Utilities.getReplaceText(etvisitorEntryMobileNo.getText().toString());

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
            if (CommonPlaceForOjects.settings.getVisitorCapturePhotoRequired()) {
                if (tvTakenPhoto.getText().toString().equalsIgnoreCase(getResources().getString(R.string.live_picture))) {
                    Toast.makeText(context, getString(R.string.take_photo), Toast.LENGTH_SHORT).show();
                    if (error == 0) {
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                                ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(this, new String[]{
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, 1);
                        } else {

                            if (Utilities.getDevice().equalsIgnoreCase("samsung")) {
                                captureVisitorImageCamera2();
                            } else {
                                captureDocumentImage();
                            }
                        }
                        error++;
                    }
                }
            }
        }


        if (llAuthentication.getVisibility() == View.VISIBLE && etAuthenticationKey1_visitorEntryTwo.getText().toString().isEmpty()) {
            etAuthenticationKey1_visitorEntryTwo.setError(getString(R.string.error_key_required));
            etAuthenticationKey1_visitorEntryTwo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                etAuthenticationKey1_visitorEntryTwo.requestFocus();
                error++;
            }
        }/*else{
            etAuthenticationKey1_visitorEntryTwo.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
        }*/

        if (etvisitorEntryFirstName.getText().toString().isEmpty()) {
            etvisitorEntryFirstName.setError(getString(R.string.error_visitor_name));
            etvisitorEntryFirstName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etvisitorEntryFirstName.requestFocus();
            }
        }/*else{
            if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                etvisitorEntryFirstName.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
            }
        }*/

           /* if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                if (!etvisitorEntryLastName.getText().toString().isEmpty()) {
                    etvisitorEntryLastName.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
            }
        }*/

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getPersonToMeetRequierd()) {
            if (employeeListsCollection != null && employeeListsCollection.isEmpty()) {
                actv_visitorEntrypersonToMeet.setError(getString(R.string.error_person_to_meet));
                actv_visitorEntrypersonToMeet.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                if (error == 0) {
                    error++;
                    actv_visitorEntrypersonToMeet.requestFocus();
                }
            }/*else{
                actv_visitorEntrypersonToMeet.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
            }*/
        }

        SPbean sPbean = new SPbean(context);

        if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Both")) {
            if (!sPbean.getPreference(Constants.SELECTED_LEVEL, "").equalsIgnoreCase("Complex")) {
                if (actvTenantName.getText().toString().isEmpty()) {
                    actvTenantName.setError(getResources().getString(R.string.error_tenant_name));
                    if (error == 0) {
                        actvTenantName.requestFocus();
                        error++;
                    }
                }
            }
        } else if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Building")) {
            if (actvTenantName.getText().toString().isEmpty()) {
                actvTenantName.setError(getResources().getString(R.string.error_tenant_name));
                if (error == 0) {
                    actvTenantName.requestFocus();
                    error++;
                }
            }
        }


        if (!actvTenantName.getText().toString().isEmpty()) {

            if (tenantListArrayList != null && !tenantListArrayList.isEmpty()) {
                if (!isValidTenantList(tenantListArrayList)) {
                    error++;
                    actvTenantName.setText("");
                    actvTenantName.requestFocus();
                    actvTenantName.setError(getResources().getString(R.string.tenant_name_error));
                }
            } else {
                tenantListArrayList = new Gson().fromJson(new SPbean(context).getPreference(Constants.TENANTLIST_RESPONSE, ""),
                        new TypeToken<ArrayList<TenantList>>() {
                        }.getType());
                if (tenantListArrayList != null && !isValidTenantList(tenantListArrayList)) {
                    error++;
                    actvTenantName.setText("");
                    actvTenantName.requestFocus();
                    actvTenantName.setError(getResources().getString(R.string.tenant_name_error));
                } else {
                    error++;
                    actvTenantName.setText("");
                    actvTenantName.requestFocus();
                    actvTenantName.setError(getResources().getString(R.string.tenant_name_error));
                }
            }
        }


        final QuestionAdapter finalQuestionAdapter = questionAdapter;

        if (finalQuestionAdapter != null) {
            if (!isQuestionValid(finalQuestionAdapter.getFilledQuestions())) {
                Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                if (error == 0) {
                    error++;
                }
            }
        }

       /* final QuestionAdapter finalQuestion = questionAdapter;
        if (!isErrorEditText(finalQuestion.getFilledQuestions())) {
            finalQuestion.ErrorEdit(finalQuestion.getFilledQuestions());
            if (error == 0) {
                error++;
            }
        }else{
            finalQuestion.ErrorEdit(finalQuestion.getFilledQuestions());
        }*/


        /*if (getMandatoryQuestionsCount() > 0) {
            if (!isMandatoryQuestionsAnswered()) {

                Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                if (error == 0) {
                    error++;
                }
            }
        }*/


        if (!VisitorCheckInActivity.GlobalByPass) {

            if (CommonPlaceForOjects.settings != null &&
                    CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("E")) {


                String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
                if (!etvisitorEntryEmail.getText().toString().matches(emailRegex)) {
                    etvisitorEntryEmail.setError(context.getResources().getString(R.string.error_email_required));
                    etvisitorEntryEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    if (error == 0) {
                        etvisitorEntryEmail.requestFocus();
                        error++;
                    }
                }/*else{
                    if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                        etvisitorEntryEmail.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
                    }
                }*/


            } else {

                if (!actvVisitorEntryIsd.getText().toString().isEmpty() &&
                        !Utilities.isValidCountryCode(actvVisitorEntryIsd.getText().toString())) {
                    actvVisitorEntryIsd.setError(getString(R.string.error_isd_invalid));
                    llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
                    if (error == 0) {
                        error++;
                    }
                }/*else{
                    if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                        llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_valid_for_isd));
                    }
                }*/


                if (!Utilities.isValidMobile(etMobile.trim())) {
                    etvisitorEntryMobileNo.setError(context.getResources().getString(R.string.error_mobile));
                    etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    if (error == 0) {
                        actvVisitorEntryIsd.requestFocus();
                        error++;
                    }
                }/*else{
                    if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ){
                        etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
                    }
                }*/

            }
        } else {

            if (!etMobile.isEmpty()) {
                if (!Utilities.isValidIsdCodeAndMobileNo(actvVisitorEntryIsd.getText().toString(), etMobile)) {
                    etvisitorEntryMobileNo.setError(getResources().getString(R.string.invalidMobile));
                    etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    if (error == 0) {
                        error++;
                        etvisitorEntryMobileNo.requestFocus();
                    }
                }
            }

            if (!etMobile.isEmpty()) {
                if (!Utilities.isValidIsdCodeAndMobileNo(actvVisitorEntryIsd.getText().toString().trim(), etMobile.trim()) &&
                        !Utilities.isValidMobile(etMobile)) {
                    etvisitorEntryMobileNo.setError(getResources().getString(R.string.invalidMobile));
                    etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    if (error == 0) {
                        error++;
                        etvisitorEntryMobileNo.requestFocus();
                    }
                }
            }

            if (!actvVisitorEntryIsd.getText().toString().isEmpty() &&
                    !Utilities.isValidCountryCode(actvVisitorEntryIsd.getText().toString())) {
                actvVisitorEntryIsd.setError(getString(R.string.error_isd_invalid));
                llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
                if (error == 0) {
                    error++;
                }
            }

            if (!etvisitorEntryEmail.getText().toString().isEmpty()) {
                if (!Utilities.isValidEmail(etvisitorEntryEmail.getText().toString().trim())) {
                    etvisitorEntryEmail.setError("Invalid email id");
                    etvisitorEntryEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                    if (error == 0) {
                        etvisitorEntryEmail.requestFocus();
                        error++;
                    }

                }
            }

        }

        return !(error > 0);
    }

    private boolean isMandatoryQuestionsAnswered() {
        int count = 0;
        ArrayList<VisitorQuestion> questionArrayList = new ArrayList<VisitorQuestion>();
        if (visitorQuestionArrayList != null) {

            questionArrayList = visitorQuestionArrayList;

            for (VisitorQuestion question :
                    questionArrayList) {
                if (question.isRequired()) {
                    if (!question.getAnswer().isEmpty()) {
                        count++;
                    }
                }
            }
        }

        return getMandatoryQuestionsCount() == count;

    }

    private boolean isValidTenantList(ArrayList<TenantList> tenantLists) {
        boolean tenantAvailable = false;
        String selectedName = actvTenantName.getText().toString().trim();
        String[] name = selectedName.split("\\|");
        String searchName = name[0];
        for (TenantList tenantList : tenantLists) {
            if (searchName.trim().equalsIgnoreCase(tenantList.getName().toString().trim())) {
                tenantAvailable = true;
            }
        }
        return tenantAvailable;
    }

    @Override
    public void onBackPressed() {

        if (onBackPressedFlag) {
            startActivity(new Intent(VisitorEntryActivityOne.this,
                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            super.onBackPressed();
            onBackPressedFlag = false;
        }
    }

    private void captureDocumentImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (LoginActivity.isSelfeHeplKiosk) {
            intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            intent.putExtra("android.intent.extras.CAMERA_FACING", Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        startActivityForResult(intent, PICK_IMAGE_ID);

    }

    private void captureVisitorImageCamera2() {
        startActivityForResult(new Intent(VisitorEntryActivityOne.this, CaptureActivity.class), CAPTURE_ID);
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type, false));
    }

    private static File getOutputMediaFile(int type, boolean isCopy) {
        String path = AppConfig.APP_PROF_DIR
                + File.separator
                + AppConfig.APP_DOWNLOAD_PROFILE
                + File.separator;

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(), path);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(AppConfig.APP_DIR, "Oops! Failed create "
                        + AppConfig.APP_DIR + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            if (!isCopy) {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
            } else {
                mediaFile = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_Copy" + timeStamp + ".jpg");
            }
        } else {
            return null;
        }

        return mediaFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode != Activity.RESULT_CANCELED) {

                switch (requestCode) {
                    case PICK_IMAGE_ID:
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            final Bitmap tempBitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                          //  Bitmap tempBitmap = Utilities.modifyOrientation(bitmap, fileUri.getPath(), false);

                            Uri uri = null;
                            // make a copy of file
                            if (tempBitmap != null) {
                                uri = makePhotoCopy(tempBitmap);
                            }

                            if (fileUri != null) {
                                if (new File(fileUri.getPath()).exists()) {
                                    new File(fileUri.getPath()).delete();
                                }
                            }

                            ivProfile.setImageBitmap(tempBitmap);
                            tvTakenPhoto.setText(getResources().getString(R.string.reset_forimage));
                            //Log.d("TAG", "Image policy = " + fileUri.getPath());
                            imageUploadObject = new ImageUploadObject();
                            imageUploadObject.setUrl(uri.getPath());
                            File file = new File(uri.getPath());

                            Log.d("TAG", "Image size = " + Integer.parseInt(String.valueOf(file.length() / 1024)));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;

                    case CAPTURE_ID:
                        imageUtils.onActivityResult(requestCode, resultCode, data);
                        break;
                    case BARCODE_READER_ACTIVITY_REQUEST:
//                        List<Recognizer> recognizer = obtainRecognizersWithResult(data);
//                        BaseResultExtractor resultExtractor = ResultExtractorFactoryProvider.get().createExtractor(recognizer.get(0));
//                        List<RecognitionResultEntry> extractedData = resultExtractor.extractData(this, recognizer.get(0));
                        if (data != null) {
                            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
//                            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
                            Barcode.DriverLicense license = barcode.driverLicense;

                            String dl_details = "";
                            DLFields dlData = new DLFields();
                            Log.e("Raw data DL :",barcode.rawValue);
                            if(license.firstName==null || license.firstName.equalsIgnoreCase("")) {
                                if (license.lastName != null && !license.lastName.equalsIgnoreCase("")){
                                    dlData.setFullName(license.lastName);
                                    dlData.setFirstName("");
                                    dlData.setLastName("");
                                }else{
                                    String name = barcode.rawValue.substring(barcode.rawValue.indexOf("DAA")+3,barcode.rawValue.indexOf("\n",barcode.rawValue.indexOf("DAA")));
                                    String[] name_arr = name.split(" ");
                                    if(name_arr.length>1) {
                                        dlData.setFirstName(name_arr[0]);
                                        dlData.setLastName(name_arr[name_arr.length - 1]);
                                    }
                                }
                            }else{
                                dlData.setFirstName(license.firstName);
                                dlData.setLastName(license.lastName);
                            }
                            dlData.setAddress(license.addressStreet);
                            dlData.setCity(license.addressCity);
                            dlData.setStateCode(license.addressState);
                            dlData.setZipCode(license.addressZip);
                            dlData.setSex(license.gender.equalsIgnoreCase("1")?"Male":"Female");
                            dlData.setDocumentNumber(license.licenseNumber.contains("DAA")?license.licenseNumber.substring(0,license.licenseNumber.indexOf("DAA")):license.licenseNumber);
                            SimpleDateFormat df_parse = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat df_parse1 = new SimpleDateFormat("MMddyyyy");
                            SimpleDateFormat df_show = new SimpleDateFormat("dd MMM yyyy");
                            Date DOB = null, DOE = null, DOI = null;
                            try {
                                int start = Integer.parseInt(license.birthDate.substring(0,2));
                                if(start>13) {
                                    DOB = df_parse.parse(license.birthDate);
                                    DOE = df_parse.parse(license.expiryDate);
                                    DOI = df_parse.parse(license.issueDate);
                                }else{
                                    DOB = df_parse1.parse(license.birthDate);
                                    DOE = df_parse1.parse(license.expiryDate);
                                    DOI = df_parse1.parse(license.issueDate);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dlData.setDateOfBirth(df_show.format(DOB));
                            dlData.setDateOfExpiry(df_show.format(DOE));
                            dlData.setIssueDate(df_show.format(DOI));
                            showDrivingLicenseDialog(dlData);
                        }
                        break;

                    default:
                        super.onActivityResult(requestCode, resultCode, data);
                        break;
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private Uri makePhotoCopy(Bitmap tempBitmap) {

        File file = getOutputMediaFile(1, true);
        try {
            FileOutputStream out = new FileOutputStream(file);
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

            return Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Camera openFrontFacingCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("Tag", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }

    // resend OTP
    public static void mathodCallToReSendOtp(HashMap<String, Object> map, Context context) {

        Call<ResponseBody> call = ApiUtils.getAPIService().callReSendOtp(Utilities.getToken(context), map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Log.d("TAG", "OTP SENT");
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void callTenantList() {
        Utilities.showprogressDialogue("", "Loading...", context, false);
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
                            bindTenantToAutoCompleteText();
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

    private void bindTenantToAutoCompleteText() {
        tenantListArrayList = new Gson().fromJson(new SPbean(context).getPreference(Constants.TENANTLIST_RESPONSE, ""),
                new TypeToken<ArrayList<TenantList>>() {
                }.getType());

        if (tenantListArrayList != null) {
            TenantAutoCompleteAdapter tenantAutoCompleteAdapter = new TenantAutoCompleteAdapter(context,
                    R.layout.activity_visitor_entry_one, R.id.lbl_name, tenantListArrayList);

            actvTenantName.setThreshold(1);         //will start working from first character
            actvTenantName.setAdapter(tenantAutoCompleteAdapter);

            actvTenantName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {

                        if (!employeeListsCollection.isEmpty()) {
                            employeeListsCollection.clear();
                            personToMeetAdapter.notifyDataSetChanged();
                        }

                        TenantList tenant = (TenantList) view.getTag();

                        SPbean sPbean = new SPbean(context);


                        if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Both")) { //|| CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Building")) {

                            if (sPbean.getPreference(Constants.SELECTED_LEVEL, "").equalsIgnoreCase("Building")) {
                                buildingIds = new SPbean(context).getSelectedBuildingIds(Constants.BUILDING_ID_LIST);

                                if (buildingIds.contains(tenant.getBuildingId())) {
                                    //Toast.makeText(context, "Contains !!!", Toast.LENGTH_SHORT).show();
                                    if (tenant.getBuildingName() == null) {
                                        tenant.setBuildingName("");
                                    }
                                    if (tenant.getOfficeNumber() == null) {
                                        tenant.setOfficeNumber("");
                                    }

                                    actvTenantName.setText(tenant.getName()/*+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName()*/);
                                    etBuildingName.setText(tenant.getBuildingName());
                                    Utilities.disableInput(etBuildingName);
                                    etBuildingName.setEnabled(false);
                                    etBuildingName.setTextColor(getResources().getColor(R.color.black));
                                    VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantId(String.valueOf(tenant.getTenantId()));
                                    VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingId(String.valueOf(tenant.getBuildingId()));
                                    VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingName(tenant.getBuildingName());
                                    VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantName(tenant.getName());
                                } else {
                                    //Toast.makeText(context, "Not contain !!!", Toast.LENGTH_SHORT).show();
                                    Utilities.showPopup(context, "Visitor Entry", "Selected Flat is present in " + tenant.getBuildingName());
                                    etBuildingName.setText("");
                                    actvTenantName.setText("");
                                }
                            } else if (sPbean.getPreference(Constants.SELECTED_LEVEL, "").equalsIgnoreCase("Complex")) {
                                if (tenant.getBuildingName() == null) {
                                    tenant.setBuildingName("");
                                }
                                if (tenant.getOfficeNumber() == null) {
                                    tenant.setOfficeNumber("");
                                }

                                actvTenantName.setText(tenant.getName()/*+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName()*/);
                                etBuildingName.setText(tenant.getBuildingName());
                                etBuildingName.setEnabled(false);
                                Utilities.disableInput(etBuildingName);
                                etBuildingName.setTextColor(getResources().getColor(R.color.black));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantId(String.valueOf(tenant.getTenantId()));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingId(String.valueOf(tenant.getBuildingId()));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingName(tenant.getBuildingName());
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantName(tenant.getName());
                            }

                        } else if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Complex")) {
                            if (tenant.getBuildingName() == null) {
                                tenant.setBuildingName("");
                            }
                            if (tenant.getOfficeNumber() == null) {
                                tenant.setOfficeNumber("");
                            }

                            actvTenantName.setText(tenant.getName()/*+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName()*/);
                            etBuildingName.setText(tenant.getBuildingName());
                            etBuildingName.setEnabled(false);
                            Utilities.disableInput(etBuildingName);
                            etBuildingName.setTextColor(getResources().getColor(R.color.black));
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantId(String.valueOf(tenant.getTenantId()));
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingId(String.valueOf(tenant.getBuildingId()));
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingName(tenant.getBuildingName());
                            VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantName(tenant.getName());
                        } else if (CommonPlaceForOjects.settings.getVstrEntryAt().equalsIgnoreCase("Building")) {

                            buildingIds = new SPbean(context).getSelectedBuildingIds(Constants.BUILDING_ID_LIST);

                            if (buildingIds.contains(tenant.getBuildingId())) {
                                //Toast.makeText(context, "Contains !!!", Toast.LENGTH_SHORT).show();
                                if (tenant.getBuildingName() == null) {
                                    tenant.setBuildingName("");
                                }
                                if (tenant.getOfficeNumber() == null) {
                                    tenant.setOfficeNumber("");
                                }

                                actvTenantName.setText(tenant.getName()/*+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName()*/);
                                etBuildingName.setText(tenant.getBuildingName());
                                etBuildingName.setEnabled(false);
                                Utilities.disableInput(etBuildingName);
                                etBuildingName.setTextColor(getResources().getColor(R.color.black));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantId(String.valueOf(tenant.getTenantId()));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingId(String.valueOf(tenant.getBuildingId()));
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setBuildingName(tenant.getBuildingName());
                                VisitorCheckInActivity.visitorLogMobileViewDataModel.setTenantName(tenant.getName());
                            } else {
                                //Toast.makeText(context, "Not contain !!!", Toast.LENGTH_SHORT).show();
                                Utilities.showPopup(context, "Visitor Entry", "Selected Flat is present in " + tenant.getBuildingName());
                                etBuildingName.setText("");
                                actvTenantName.setText("");
                            }

                            /*if (tenant.getBuildingName() == null) {
                                tenant.setBuildingName("");
                            }
                            if (tenant.getOfficeNumber() == null) {
                                tenant.setOfficeNumber("");
                            }

                            actvTenantName.setText(tenant.getName()*//*+" | "+tenant.getOfficeNumber()+" | "+tenant.getBuildingName()*//*);
                            etBuildingName.setText(tenant.getBuildingName());
                            etBuildingName.setEnabled(false);
                            etBuildingName.setTextColor(getResources().getColor(R.color.black));
                            VisitorCheckInActivity.visitorLogMobileViewModels.setTenantId(String.valueOf(tenant.getTenantId()));
                            VisitorCheckInActivity.visitorLogMobileViewModels.setBuildingId(String.valueOf(tenant.getBuildingId()));
                            VisitorCheckInActivity.visitorLogMobileViewModels.setBuildingName(tenant.getBuildingName());
                            VisitorCheckInActivity.visitorLogMobileViewModels.setTenantName(tenant.getName());*/
                        }

                        try {
                            if (buildingIds != null && buildingIds.contains(tenant.getBuildingId())) {
                                callTenantEmployee();
                            } else if (!Utils.isEmpty(tenant.getTenantId())) {
                                callTenantEmployee();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
    }


    private void CallSexualOffender(String Name, final VisitorEntryModel visitorEntryModel) {
        if (AppConfig.IsSexualOffender) {

            /*String Fname = dlFields.getFirstName() != null ? dlFields.getFirstName() : "";
            String Lname = dlFields.getLastName() != null ? dlFields.getLastName() : "";
            String name = Fname.trim() + "" + Lname.trim();
            String vName = !name.isEmpty() ? name : Name;*/
            String stateCode = "",city="",zipCode="";
            if(dlFields!=null) {
                stateCode = dlFields.getStateCode() != null ? dlFields.getStateCode() : "";
                city = dlFields.getCity() != null ? dlFields.getCity() : "";
                zipCode = dlFields.getZipCode() != null ? dlFields.getZipCode() : "";
            }
       /*     String stateCode = visitorList.getStatecode() != null ? visitorList.getStatecode() : "";
            String city = visitorList.getCity() != null ? visitorList.getCity() : "";
            String zipCode = visitorList.getZipcode() != null ? visitorList.getZipcode() : "";*/
            Utilities.showprogressDialogue(context.getString(R.string.checkingsexualoffenderlist), context.getString(R.string.please_wait), context, false);
            Call<ResponseBody> call = ApiUtils.getAPIService().getSexualOffendedList(
                    Name.trim(), stateCode, city, zipCode);

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

                                showOffenderList(sexualOffendedList, visitorEntryModel);

                            } else {
//                                callVisitorEntry(visitorEntryModel);
                                NDADialogForVisitor(visitorEntryModel);
                                tvSexualOffendMatch.setVisibility(View.GONE);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Utilities.hideProgress();
                        tvSexualOffendMatch.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    tvSexualOffendMatch.setVisibility(View.GONE);
                    Utilities.hideProgress();
                }
            });
        }
    }


    private void showOffenderList(SexualOffendedList sexualOffendedList, VisitorEntryModel visitorEntryModel) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setView(getSexualList(sexualOffendedList, visitorEntryModel));
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

    private View getSexualList(SexualOffendedList sexualOffendedList, final VisitorEntryModel visitorEntryModel) {

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

        tvSexualOffendMatch.setVisibility(View.VISIBLE);

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
                                    tvSexualOffendMatch.setVisibility(View.GONE);
//                                    callVisitorEntry(visitorEntryModel);
                                    NDADialogForVisitor(visitorEntryModel);
                                }
                            });

                    alertDialog.setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    tvSexualOffendMatch.setVisibility(View.GONE);

                                }
                            });

                    alertDialog.show();
                }
            }
        });

        return view;
    }

    public void callGetAuthenticateVisitor() {
        String etmobie = Utilities.getReplaceText(etvisitorEntryMobileNo.getText().toString());
        HashMap<String, Object> map = new HashMap<>();
        String etIsdCode = actvVisitorEntryIsd.getText().toString();
        etIsdCode = etIsdCode.replaceAll("\\+", "");
        map.put("ISDCode", etIsdCode.trim());
        map.put("Mobile", etmobie);
        map.put("Email", "");
        map.put("requestClientDetails", Utilities.requestclientDetails(context));

        Utilities.showprogressDialogue(getString(R.string.fetching_data), "", context, false);
        Call<ResponseBody> call = ApiUtils.getAPIService().callGetAuthenticateVisitor(map, Utilities.getToken(context));
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
                            if (responseBody.trim().charAt(0) == '[') {
                                Log.d("TAG", "JSONArray");
                                JSONArray jsonMessageArray = new JSONArray(responseBody);
                                JSONObject object = jsonMessageArray.getJSONObject(0);
                                String message = object.getString("message");
                                Utilities.showPopup(context, "", message);

                            } else if (responseBody.trim().charAt(0) == '{') {
                                Log.d("TAG", "JSONObject");
                                JSONObject jsonObject = new JSONObject(responseBody);
                                Bundle extras = new Bundle();
                                extras.putString("isd", jsonObject.getString("isdCode"));
                                extras.putString("mobile", jsonObject.getString("mobile"));
                                extras.putString("email", jsonObject.getString("email"));
                                Intent i = new Intent(context, ReAuthenticateActivity.class);
                                i.putExtras(extras);
                                startActivity(i);

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
    }

    public void callPrintPassFromHandler() {

        if (visitorLogId != 0) {
            //Utilities.showprogressDialogue(getString(R.string.printing_pass), getString(R.string.please_wait), context, false);
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
        } else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(VisitorEntryActivityOne.this,
                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }
            };
            Utilities.showPopuprunnable(context, "" + model.getMessage() != null && !model.getMessage().isEmpty() ? model.getMessage() : "Visitor Entry Successfull", true, runnable);
        }


    }

    class FetchPDFTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isFromUpload) {
                Utilities.showProgressForAsynk(VisitorEntryActivityOne.this, getResources().getString(R.string.printing_pass), getResources().getString(R.string.please_wait), fetchPDFTask);
            } else {
                Utilities.hideProgress();
                Utilities.showProgressForAsynk(VisitorEntryActivityOne.this, getResources().getString(R.string.printing_pass), getResources().getString(R.string.please_wait), fetchPDFTask);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = AppConfig.WEB_URL + AppConfig.HANDLER_PRINTPASS + "?visitorLogId=" + visitorLogId;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();

            downloadFileName = "PrintPass" + timeStamp + visitorLogId + ".pdf";//Create file name by picking download file name from URL
            File folder = new File(extStorageDirectory, "PrintPass");
            if (!folder.exists()) {
                folder.mkdir();
            }

            File pdfFile = new File(folder, downloadFileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("Tag", "fileURL =" + fileUrl);
            FileTransferHelper.downloadFileForPDF(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/PrintPass/" + downloadFileName);  // -> filename = maven.pdf
                //Uri path = Uri.fromFile(pdfFile);
                String file = pdfFile.toString();
                setPdfFile(file);
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


                if (isFromUpload) {
                    Utilities.hideProgressForAsync(fetchPDFTask);
                    Log.d("TAG", "isUpload");
                } else {
                    Utilities.hideProgressForAsync(fetchPDFTask);
                    Log.d("TAG", "isLogin");
                }
                // call function to print
                Log.d("TAG", "Start and End " + startPage + "-" + endPage);
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

    public void showDrivingLicenseDialog(final DLFields dlData) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_drivinglicence_details);
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.ivDrivingLicenseCancel);
        final EditText etFirstName = (EditText) dialog.findViewById(R.id.etDrivingLicenseFirstName);
        final EditText etLastName = (EditText) dialog.findViewById(R.id.etDrivingLicenseLastName);
        final EditText etDLNumber = (EditText) dialog.findViewById(R.id.etDrivingLicenseDLNumber);
        final EditText etDOB = (EditText) dialog.findViewById(R.id.etDrivingLicenseDOB);
        final EditText etStateCode = (EditText) dialog.findViewById(R.id.etDrivingLicenseState);
        final EditText etCity = (EditText) dialog.findViewById(R.id.etDrivingLicenseNationality);
        final EditText etExpiryDate = (EditText) dialog.findViewById(R.id.etDrivingLicenseExpiryDate);
        final EditText etIssueDate = (EditText) dialog.findViewById(R.id.etDrivingLicenseIssueDate);
        final EditText etGender = (EditText) dialog.findViewById(R.id.etDrivingLicenseGender);
        final EditText etZipcode = (EditText) dialog.findViewById(R.id.etDrivingLicenseCountry);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnDrivingLicenseConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlData.setFirstName(etFirstName.getText().toString());
                dlData.setLastName(etLastName.getText().toString());
                dlData.setDocumentNumber(etDLNumber.getText().toString());
                dlData.setStateCode(etStateCode.getText().toString());
                dlData.setCity(etCity.getText().toString());
                dlData.setZipCode(etZipcode.getText().toString());
                dlData.setDateOfBirth(etDOB.getText().toString());
                dlData.setSex(etGender.getText().toString());
                dlData.setDateOfExpiry(etExpiryDate.getText().toString());
                dlData.setIssueDate(etIssueDate.getText().toString());
                dlFields = dlData;
                etvisitorEntryFirstName.setText(dlData.getFirstName() != null ? dlData.getFirstName() : " ");
                etvisitorEntryFirstName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                etvisitorEntryLastName.setText(dlData.getLastName() != null ? dlData.getLastName() : " ");
                etvisitorEntryLastName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                dialog.dismiss();
            }
        });

        isFromDL = true;
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        String Address = dlData.getAddress();
//        String[] split = Address.split("\\,");
//        String city = split[1];
//        String StateCode = split[2];
//        String ZipCode = split[3];

//        dlData.setCity(city);
//        dlData.setStateCode(StateCode);
//        dlData.setZipCode(ZipCode);
        if(!dlData.getFirstName().equalsIgnoreCase("")) {
            etFirstName.setText(dlData.getFirstName());
            etLastName.setText(dlData.getLastName());
        }else{
            etFirstName.setText(dlData.getFullName());
        }
        etDLNumber.setText(dlData.getDocumentNumber());
        etStateCode.setText(dlData.getStateCode());
        etCity.setText(dlData.getCity());
        etZipcode.setText(dlData.getZipCode());
        etDOB.setText(dlData.getDateOfBirth());
        if (!dlData.getSex().equals("")) {
            etGender.setText(dlData.getSex());
        } else {
            etGender.setText("Not Specified");
            dlData.setSex("Not Specified");
        }
        etExpiryDate.setText(dlData.getDateOfExpiry());
        etIssueDate.setText(dlData.getIssueDate());
        dialog.show();
    }

//    protected List<Recognizer> obtainRecognizersWithResult(Intent intent) {
//        List<Recognizer> recognizersWithResult = new ArrayList<>();
//        mRecognizerBundle = new RecognizerBundle();
//        mRecognizerBundle.loadFromIntent(intent);
//        for (Recognizer<Recognizer.Result> r : mRecognizerBundle.getRecognizers()) {
//            if (r.getResult().getResultState() != Recognizer.Result.State.Empty) {
//                recognizersWithResult.add(r);
//            }
//        }
//        return recognizersWithResult;
//    }
//
//    private void setupActivitySettings(@NonNull UISettings settings) {
//        if (settings instanceof BeepSoundUIOptions) {
//            ((BeepSoundUIOptions) settings).setBeepSoundResourceID(R.raw.beep);
//        }
//        if (settings instanceof OcrResultDisplayUIOptions) {
//            ((OcrResultDisplayUIOptions) settings).setOcrResultDisplayMode(OcrResultDisplayMode.ANIMATED_DOTS);
//        }
//    }
//
//    private void scanAction(@NonNull UISettings activitySettings) {
//        setupActivitySettings(activitySettings);
//        ActivityRunner.startActivityForResult(this, MY_BLINKID_REQUEST_CODE, activitySettings);
//    }
//
//    private RecognizerBundle prepareRecognizerBundle(@NonNull Recognizer<?>... recognizers) {
//        return new RecognizerBundle(recognizers);
//    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    public void selectFileButtonOnClick() {

    }

    @Override
    public void printButtonOnClick() {

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
            showPopup(context, "", "Badge can not be printed, please set up the printer settings.");
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

        alertDialog.setPositiveButton("Skip",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        isPrinterNotAvailable = true;
                        dialog.cancel();
                        //startActivity(new Intent(context, Activity_Settings.class));

                    }
                });

        alertDialog.show();
    }


}
