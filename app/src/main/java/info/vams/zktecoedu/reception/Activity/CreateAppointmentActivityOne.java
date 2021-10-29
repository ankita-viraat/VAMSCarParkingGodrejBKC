package info.vams.zktecoedu.reception.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brother.ptouch.sdk.Printer;
import com.brother.ptouch.sdk.PrinterInfo;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
//import com.microblink.entities.recognizers.Recognizer;
//import com.microblink.entities.recognizers.RecognizerBundle;
//import com.microblink.entities.recognizers.blinkbarcode.usdl.UsdlRecognizer;
//import com.microblink.result.extract.BaseResultExtractor;
//import com.microblink.result.extract.RecognitionResultEntry;
//import com.microblink.result.extract.ResultExtractorFactoryProvider;
//import com.microblink.uisettings.ActivityRunner;
//import com.microblink.uisettings.DocumentUISettings;
//import com.microblink.uisettings.UISettings;
//import com.microblink.uisettings.options.BeepSoundUIOptions;
//import com.microblink.uisettings.options.OcrResultDisplayMode;
//import com.microblink.uisettings.options.OcrResultDisplayUIOptions;
//import com.microblink.util.ImageSettings;
//import com.squareup.picasso.Picasso;

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

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Adapter.PersonToMeetAdapter;
import info.vams.zktecoedu.reception.Adapter.QuestionAdapter;
import info.vams.zktecoedu.reception.Adapter.SexualOffenderAdapter;
import info.vams.zktecoedu.reception.Adapter.VisitorQuestionAdapter;
import info.vams.zktecoedu.reception.Helper.FileTransferHelper;
import info.vams.zktecoedu.reception.Model.AdditionalDetail;
import info.vams.zktecoedu.reception.Model.DLFields;
import info.vams.zktecoedu.reception.Model.EmployeeList;

import info.vams.zktecoedu.reception.Model.ImageUploadObject;
import info.vams.zktecoedu.reception.Model.MasterResponse;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.PurposeVisit;
import info.vams.zktecoedu.reception.Model.Question;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.Model.SexualOffendedList;
import info.vams.zktecoedu.reception.Model.TypeOfVisitor;
import info.vams.zktecoedu.reception.Model.VisitorEntryModel;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.Model.VisitorLogMobileViewModel;
import info.vams.zktecoedu.reception.Model.VisitorLogPersonToVisit;
import info.vams.zktecoedu.reception.Model.VisitorQuestion;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Common;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.Imageutils;
import info.vams.zktecoedu.reception.Util.MsgDialog;
import info.vams.zktecoedu.reception.Util.MsgHandle;
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

import static info.vams.zktecoedu.reception.Activity.PreAppointmentActivity.visitorLogMobileViewModel;


public class CreateAppointmentActivityOne extends BaseActivity implements View.OnClickListener, Imageutils.ImageAttachmentListener {

    static Context context;
    Button btnNext_CreateAppointmentOne, btn_Add_Details, btnBypass, btnResendKey, btnPreApptReAuthenticate, btn_ScanDL, btnSubmit_CreateAppointmentTwo,btnLogin,btnCancelDialog,btnYes;
    ImageView ivReset_createAppointmentActivityOne, ivIsdFlag, ivLogo_createAppointmentActivity,ivSos;
    CircleImageView ivProfile, cvRegisteredImage;
    EditText etTenantName;
    EditText etBuildingName;
    EditText etComplexName;
    static EditText etFirstName;
    static EditText etLastName;
    EditText etMobileNo;
    EditText etEmail;
    EditText etCompanyName;
    EditText etaccessCardnNo;
    EditText etAuthenticationKey;
    Spinner spnTypeOfVisitor, spnPurposeOfVisit;
    CheckBox sheriffCheckbox,adminCheckbox;
    RecyclerView rvCreateAppointmentPersonToMeet, rvQuestions;
    RecyclerView.LayoutManager layoutManager;
    TextView tvTakenPhoto, tvMobileSrarCreareAppointment, tvEmailSrarCreareAppointment, tvPreAppointmentQuestionStarVisitor, tvSexualOffendMatch,tvHeader,tvAdmin,tvSheriff;
    LinearLayout ll_tenantName, ll_buildingName, ll_takeImageCreateAppointment, ll_registerImageCreateAppointment,
            ll_addVisitor,llPreApptReAuth, llPersontTomeet, ll_VisitorEmail, ll_VisitorMobileNo, llCreateFlagIsd, llAuthenticationFields, llAuthenticationButton, llQuestion;
    ;
    AutoCompleteTextView actPersonToMeet, actvIsdCode;
    ArrayList<VisitorLogMobileViewModel> visitorLogMobileViewModelArrayList;
    ArrayList<VisitorList> visitorListArrayList;
    ArrayList<PurposeVisit> purposeVisitArrayList;
    ArrayList<TypeOfVisitor> typeOfVisitorArrayList;
    public static VisitorList visitorList = new VisitorList();
    VisitorLogPersonToVisit visitorLogPersonToVisit = null;
    ArrayList<EmployeeList> employeeListArrayList;
    //public static VisitorLogMobileViewModel visitorLogMobileViewModel = null;
    EmployeeList employee;
    MasterResponse masterResponse;
    private static int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_DOCUMENT = 20;
    private static final int PICK_IMAGE_ID = 100;
    private Uri fileUri;
    ArrayList<ImageUploadObject> imageUploadObjects;
    ImageUploadObject imageUploadObject;
    public static ArrayList<VisitorLogPersonToVisit> employeeListsCollection = null;
    PersonToMeetAdapter personToMeetAdapter;
    String serverIdProofName = "";
    boolean flag = true;
  //  AlertDialog alertDialog=null;
 //   Locale myLocale;
    private AlertDialog dialog;

    QuestionAdapter questionAdapter = null;
    VisitorQuestionAdapter visitorQuestionAdapter = null;
    VisitorLogMobileViewModel model;

    boolean isQuestionsValid = false;
    private boolean visible;

    AlertDialog alertDialog=null;

    ArrayList<SexualOffend> sexualOffends = null;

    public AdditionalDetail additionalDetail = new AdditionalDetail();


    ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<>();
    ArrayList<VisitorQuestion> visitorRequiredQuestionArrayList = new ArrayList<>();

    public static boolean clickedOnce = false;
    boolean onBackPressedFlag = false;
    FetchPDFTask fetchPDFTask = null;
    private String downloadFileName = "";
    int visitorLogId = 0;
    int lastvisitor = 1;
    public static final int MY_BLINKID_REQUEST_CODE = 123;
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
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment_one);
        imageUtils = new Imageutils(this);
        init();
    }

    private void init() {
        context = CreateAppointmentActivityOne.this;
        imageUploadObjects = new ArrayList<ImageUploadObject>();

        //For Nougat Camera Purpose Code
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        btnNext_CreateAppointmentOne = (Button) findViewById(R.id.btnNext_CreateAppointmentOne);
        btnNext_CreateAppointmentOne.setOnClickListener(this);
        btnSubmit_CreateAppointmentTwo = (Button) findViewById(R.id.btnSubmit_CreateAppointmentTwo);
        btnSubmit_CreateAppointmentTwo.setOnClickListener(this);
        ivProfile = (CircleImageView) findViewById(R.id.ivFirstImage_createAppointmentActivityOne);
        ivProfile.setOnClickListener(this);
        ivIsdFlag = (ImageView) findViewById(R.id.ivCreateApptIsdFlag);
        ivReset_createAppointmentActivityOne = (ImageView) findViewById(R.id.ivReset_createAppointmentActivityOne);
        ivReset_createAppointmentActivityOne.setOnClickListener(this);
        ivLogo_createAppointmentActivity = (ImageView) findViewById(R.id.ivLogo_createAppointmentActivity);
        ivLogo_createAppointmentActivity.setOnClickListener(this);
        etTenantName = (EditText) findViewById(R.id.etcreateAppointmenttenantName);
        etBuildingName = (EditText) findViewById(R.id.etcreateAppointmentbuildingName);
        etComplexName = (EditText) findViewById(R.id.etCreateAppointmentOnecomplexName);
        Utilities.disableInput(etTenantName);
        Utilities.disableInput(etBuildingName);
        Utilities.disableInput(etComplexName);
        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);

        llCreateFlagIsd = (LinearLayout) findViewById(R.id.llCreateFlagIsd);
        llAuthenticationFields = (LinearLayout) findViewById(R.id.ll_createAppttwoAuthenticationEditText);
        llAuthenticationButton = (LinearLayout) findViewById(R.id.ll_createAppttwoAuthenticationButton);
        etAuthenticationKey = (EditText) findViewById(R.id.etAuthenticationKey_CreateAppointmentTwo);
        btnBypass = (Button) findViewById(R.id.btnBypass_CreateAppointmentTwo);
        btnBypass.setOnClickListener(this);
        btnResendKey = (Button) findViewById(R.id.btnResendKey_CreateAppointmentTwo);
        btnResendKey.setOnClickListener(this);
        btnPreApptReAuthenticate = (Button) findViewById(R.id.btnPreApptReAuthenticate);
        btnPreApptReAuthenticate.setOnClickListener(this);

        etaccessCardnNo = (EditText) findViewById(R.id.etaccessCardnNo_CreateAppointmentTwo);

        etFirstName = (EditText) findViewById(R.id.etCreateAppointmentOneFirstName);
        etFirstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        etLastName = (EditText) findViewById(R.id.etCreateAppointmentOneLastName);
        etLastName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        llPreApptReAuth = (LinearLayout) findViewById(R.id.llbtnPreApptReAuth);
        llPersontTomeet = (LinearLayout) findViewById(R.id.llCreateApptPersonToMeet);
        llPersontTomeet.setVisibility(View.VISIBLE);
        ll_addVisitor = (LinearLayout) findViewById(R.id.ll_addVisitor);
        ll_addVisitor.setVisibility(View.GONE);

        actvIsdCode = (AutoCompleteTextView) findViewById(R.id.actvCreateApptIsd);
        etMobileNo = (EditText) findViewById(R.id.etCreateAppointmentOneMobileNo);
        etEmail = (EditText) findViewById(R.id.etCreateAppointmentOneEmail);
        etCompanyName = (EditText) findViewById(R.id.etCreateAppointmentOneCompanyName);

        actPersonToMeet = (AutoCompleteTextView) findViewById(R.id.actCreateAppointmentOnepersonToMeet);
        actPersonToMeet.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        tvMobileSrarCreareAppointment = (TextView) findViewById(R.id.tvMobileSrarCreareAppointment);
        tvEmailSrarCreareAppointment = (TextView) findViewById(R.id.tvEmailSrarCreareAppointment);
        tvSexualOffendMatch = (TextView) findViewById(R.id.tvSexualOffendMatch);


        btn_Add_Details = (Button) findViewById(R.id.btn_AddDetails);
        btn_Add_Details.setOnClickListener(this);

        spnTypeOfVisitor = (Spinner) findViewById(R.id.spnCreateAppointmentOneTypeOfVisitor);
        spnPurposeOfVisit = (Spinner) findViewById(R.id.spnCreateAppointmentOnePurposeOfVisit);
        ll_tenantName = (LinearLayout) findViewById(R.id.ll_tenantName);
        ll_buildingName = (LinearLayout) findViewById(R.id.ll_buildingName);
        ll_VisitorEmail = (LinearLayout) findViewById(R.id.ll_VisitorEmail);
        ll_VisitorMobileNo = (LinearLayout) findViewById(R.id.ll_VisitorMobileNo);
        llQuestion = (LinearLayout) findViewById(R.id.llCreateAppointmentQuestion);
        llQuestion.setVisibility(View.GONE);
        rvQuestions = (RecyclerView) findViewById(R.id.rvCreateAppointmentQuestions);
        btn_ScanDL = (Button) findViewById(R.id.btn_ScanDL);

        tvTakenPhoto = (TextView) findViewById(R.id.tvCreateAppointmentTakenPhoto);
        tvTakenPhoto.setOnClickListener(this);

        rvCreateAppointmentPersonToMeet = (RecyclerView) findViewById(R.id.rvCreateAppointmentPersonToMeet);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvCreateAppointmentPersonToMeet.setLayoutManager(layoutManager);

        ll_registerImageCreateAppointment = (LinearLayout) findViewById(R.id.ll_registerImageCreateAppointment);
        ll_takeImageCreateAppointment = (LinearLayout) findViewById(R.id.ll_takeImageCreateAppointment);
        cvRegisteredImage = (CircleImageView) findViewById(R.id.ivRegisteredImage_createAppointmentActivityOne);
        cvRegisteredImage.setEnabled(false);

        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
            ll_takeImageCreateAppointment.setVisibility(View.GONE);
        }

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etMobileNo));
        etMobileNo.addTextChangedListener(addLineNumberFormatter);

        Utilities.setUserLogo(context, ivLogo_createAppointmentActivity);
        mDialog = new MsgDialog(this);
        mHandle = new MsgHandle(this, mDialog, false);
        myPrint = new PdfPrint(this, mHandle, mDialog);
        setPreferences();
//        usdlRecognizer = new UsdlRecognizer();
//        ImageSettings.enableAllImages(usdlRecognizer);
        dlFields = new DLFields();
        btn_ScanDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scanAction(new DocumentUISettings(prepareRecognizerBundle(usdlRecognizer)));
                launchBarCodeActivity();
            }
        });
        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                equalsIgnoreCase("E")) {
            tvMobileSrarCreareAppointment.setText("");
            tvEmailSrarCreareAppointment.setText("*");
            ll_VisitorMobileNo.setVisibility(View.GONE);
            ll_VisitorEmail.setVisibility(View.VISIBLE);
        } else {
            tvMobileSrarCreareAppointment.setText("*");
            tvEmailSrarCreareAppointment.setText("");
            ll_VisitorMobileNo.setVisibility(View.VISIBLE);
            ll_VisitorEmail.setVisibility(View.GONE);
        }


        if (PreAppointmentActivity.GlobalByPass) {
            llAuthenticationButton.setVisibility(View.GONE);
            llAuthenticationFields.setVisibility(View.GONE);
        } /*else if (!visitorLogMobileViewModel.getVisitorList().get(0).getRequireAuthentication()) {
            llAuthenticationButton.setVisibility(View.GONE);
            llAuthenticationFields.setVisibility(View.GONE);
        }*/

        bindPurposeOfVisit(spnPurposeOfVisit);
        bindTypeOfVisitors(spnTypeOfVisitor);
        bindAppointMentData();
        bindQuestions(context, rvQuestions);

        if (visitorLogMobileViewModel == null) {
            visitorLogMobileViewModel = new VisitorLogMobileViewModel();
        }
        visitorListArrayList = new ArrayList<VisitorList>();
        visitorLogPersonToVisit = new VisitorLogPersonToVisit();

        actvIsdCode.addTextChangedListener(new TextWatcher() {
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
                    actvIsdCode.append("+");
                }
            }
        });
        Utilities.addTextChangeListenerForIsd(context, etMobileNo, actvIsdCode);

        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
            btnBypass.setVisibility(View.GONE);
        }

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getPrintPass()) {
            callPrinterSettings();
        }
    }

    private void bindQuestions(Context context, RecyclerView recyclerView) {
        MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);


        ArrayList<Question> questionArrayList = new ArrayList<Question>();
        ArrayList<Question> questionList = new ArrayList<Question>();
        ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<VisitorQuestion>();
        if (!Utils.isEmpty(visitorLogMobileViewModel.getVisitorList().get(0).getVisitorQuestions())) {
            ArrayList<VisitorQuestion> visitorQuestions = visitorLogMobileViewModel.getVisitorList().get(0).getVisitorQuestions();
            for (VisitorQuestion visitorQuestion : visitorQuestions) {
                if (visitorQuestion.isRequired()) {
                    visitorQuestionArrayList.add(visitorQuestion);
                }
            }

            if (visitorQuestionArrayList.size() > 0) {
                llQuestion.setVisibility(View.VISIBLE);
                visitorQuestionAdapter = new VisitorQuestionAdapter(context, visitorQuestionArrayList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(visitorQuestionAdapter);
            } else {
                llQuestion.setVisibility(View.GONE);
            }
        } else {
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
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onBackPressedFlag) {
            startActivity(new Intent(CreateAppointmentActivityOne.this,
                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            onBackPressedFlag = false;
            clickedOnce = false;
        }

        if (clickedOnce) {
            btnResendKey.setEnabled(false);
            btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
        }

        if (PreAppointmentActivity.GlobalByPass) {
            llAuthenticationButton.setVisibility(View.GONE);
            llAuthenticationFields.setVisibility(View.GONE);
        }/* else if (!visitorLogMobileViewModel.getVisitorList().get(0).getRequireAuthentication()) {
            llAuthenticationButton.setVisibility(View.GONE);
            llAuthenticationFields.setVisibility(View.GONE);
        }*/
    }

    private void bindAppointMentData() {

        if (!Utils.isEmpty(visitorLogMobileViewModel)) {

            if (!Utils.isEmpty(visitorLogMobileViewModel.getComplexName())) {
                etComplexName.setText(visitorLogMobileViewModel.getComplexName());
                Utilities.disableInput(etComplexName);
            }

            if (!Utils.isEmpty(visitorLogMobileViewModel.getTenantName())) {
                ll_tenantName.setVisibility(View.VISIBLE);
                etTenantName.setText(visitorLogMobileViewModel.getTenantName());
                Utilities.disableInput(etTenantName);
            }

            if (!Utils.isEmpty(visitorLogMobileViewModel.getBuildingName())) {
                ll_buildingName.setVisibility(View.VISIBLE);
                etBuildingName.setText(visitorLogMobileViewModel.getBuildingName());
                Utilities.disableInput(etBuildingName);
            }


            if (!Utils.isEmpty(visitorLogMobileViewModel.getVisitorList())) {
                visitorListArrayList = visitorLogMobileViewModel.getVisitorList();
                VisitorList visitorList = visitorListArrayList.get(0);
                setVisitorData(visitorList);
            }

            if (visitorLogMobileViewModel.getVisitorLogPersonToVisit() != null &&
                    !visitorLogMobileViewModel.getVisitorLogPersonToVisit().isEmpty()) {
                setPersonToMeetData(visitorLogMobileViewModel.getVisitorLogPersonToVisit());
            }

            if (visitorLogMobileViewModel.getVisitorLogPersonToVisit() != null &&
                    !visitorLogMobileViewModel.getVisitorLogPersonToVisit().isEmpty()) {
                setPersonToMeetData(visitorLogMobileViewModel.getVisitorLogPersonToVisit());
            }
        } else {

            Utilities.showPopup(context, "", getString(R.string.no_preappointment_found));
            ArrayList<String> employeeListInString = new ArrayList<String>();
            employeeListArrayList = new Gson().fromJson(new SPbean(context).getPreference(Constants.EMPLOYEE_LIST_RESPONSE, ""),
                    new TypeToken<ArrayList<EmployeeList>>() {
                    }.getType());
            if (!Utils.isEmpty(employeeListArrayList)) {
                for (int i = 0; i < employeeListArrayList.size(); i++) {
                    employeeListInString.add(employeeListArrayList.get(i).getFirstName() + " " + employeeListArrayList.get(i).getLastName());
                }
            }
            bindPersonToMeetAdapter(employeeListInString);
        }
    }

    private void setPersonToMeetData(ArrayList<VisitorLogPersonToVisit> visitorLogMobileViewModel) {

        try {

            ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisitArrayList = new ArrayList<VisitorLogPersonToVisit>();
            visitorLogPersonToVisitArrayList = visitorLogMobileViewModel;

            if (visitorLogMobileViewModel != null) {
                actPersonToMeet.setEnabled(false);

                personToMeetAdapter = new PersonToMeetAdapter(context, visitorLogPersonToVisitArrayList,
                        null, this);

                rvCreateAppointmentPersonToMeet.setItemAnimator(new DefaultItemAnimator());
                rvCreateAppointmentPersonToMeet.setAdapter(personToMeetAdapter);
                personToMeetAdapter.notifyDataSetChanged();

            } else {

            }

//        Log.d("Tag", "set person to meet array list =" + new Gson().toJson(visitorLogPersonToVisitArrayList));
//        if (!Utils.isEmpty(visitorLogPersonToVisitArrayList)) {
//            for (int i = 0; i < visitorLogPersonToVisitArrayList.size(); i++) {
//                visitorLogPersonToVisit = visitorLogPersonToVisitArrayList.get(i);
//                if (!Utils.isEmpty(visitorLogPersonToVisit.getName())) {
//                    actPersonToMeet.setText(visitorLogPersonToVisit.getName());
//                    actPersonToMeet.setEnabled(false);
//                }
//            }
//            visitorLogPersonToVisitArrayList.add(visitorLogPersonToVisit);
//            Log.d("Tag", "Person to meet array list prefilled =" + new Gson().toJson(visitorLogPersonToVisitArrayList));
//            visitorLogMobileViewModel.setVisitorLogPersonToVisit(visitorLogPersonToVisitArrayList);
//        }

        } catch (Exception e) {

        }


    }


    private void bindPersonToMeetAdapter(ArrayList<String> employeeListInString) {
        if (!employeeListInString.isEmpty()) {

            ArrayAdapter<String> personToMeetAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, employeeListInString);
            actPersonToMeet.setThreshold(3);         //will start working from first character
            actPersonToMeet.setAdapter(personToMeetAdapter);
            actPersonToMeet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = actPersonToMeet.getAdapter().getItem(position).toString();
                    if (selectedItem != null) {
                        employee = getEmployeeDetails(selectedItem);
                        Log.d("Tag", "Employeee data =" + new Gson().toJson(employee));
                        if (!Utils.isEmpty(employee)) {
                            ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisitArrayList1 = new ArrayList<VisitorLogPersonToVisit>();
                            visitorLogPersonToVisit.setName(employee.getFirstName() + " " + employee.getLastName());

                            visitorLogPersonToVisitArrayList1.add(visitorLogPersonToVisit);
                            visitorLogMobileViewModel.setVisitorLogPersonToVisit(visitorLogPersonToVisitArrayList1);
                            Log.d("Tag", "Person to meet array list select from employee =" + new Gson().toJson(visitorLogPersonToVisitArrayList1));
                        }
                    }
                }
            });
        }
    }

    private EmployeeList getEmployeeDetails(String selectedItem) {
        for (int i = 0; i < employeeListArrayList.size(); i++) {
            String s = employeeListArrayList.get(i).getFirstName() + " " + employeeListArrayList.get(i).getLastName();
            if (s.equals(selectedItem)) {
                return employeeListArrayList.get(i);
            }
        }
        return null;
    }

    private void setVisitorData(VisitorList visitorList) {

        if (!Utils.isEmpty(visitorList.getFirstName())) {
            etFirstName.setText(visitorList.getFirstName());
            Utilities.disableInput(etFirstName);
        }

        if (!Utils.isEmpty(visitorList.getLastName())) {
            etLastName.setText(visitorList.getLastName());
            Utilities.disableInput(etLastName);
        }

        if (!Utils.isEmpty(visitorList.getIsdCode())) {
            actvIsdCode.setText("+" + visitorList.getIsdCode());
            actvIsdCode.setInputType(InputType.TYPE_NULL);
            ivIsdFlag.setImageResource(Utilities.setDrawableFlage(actvIsdCode.getText().toString()));
            llCreateFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_bg_uneditable_for_isd));


        }
        if (!Utils.isEmpty(visitorList.getMobile())) {
            etMobileNo.setText(visitorList.getMobile());
            Utilities.disableInput(etMobileNo);
        }
        if (!Utils.isEmpty(visitorList.getEmail())) {
            etEmail.setText(visitorList.getEmail());
            Utilities.disableInput(etEmail);
        }
        if (!Utils.isEmpty(visitorList.getCompany())) {
            etCompanyName.setText(visitorList.getCompany());
            Utilities.disableInput(etCompanyName);
        }

        if (!Utils.isEmpty(visitorList.getVisitorQuestions())) {
            ArrayList<VisitorQuestion> visitorQuestions = visitorList.getVisitorQuestions();
            for (VisitorQuestion ques : visitorQuestions) {
                if (!ques.isRequired()) {
                    visitorQuestionArrayList.add(ques);
                }
            }
        }

        if (!Utils.isEmpty(visitorList.getAccessCardNo())) {
            etaccessCardnNo.setText("" + visitorList.getAccessCardNo());
            Utilities.disableInput(etaccessCardnNo);
        }

        if (!Utils.isEmpty(visitorList.getPurposeOfVisitId())) {
            int position = Utilities.getPurposeToVisitById(masterResponse, visitorList.getPurposeOfVisitId());
            spnPurposeOfVisit.setSelection(position);
            Utilities.disableSelection(spnPurposeOfVisit, context);
        }

        if (!Utils.isEmpty(visitorList.getTypeOfVisitorId())) {
            int position = Utilities.getTypeOfVisitorById(masterResponse, visitorList.getTypeOfVisitorId());
            spnTypeOfVisitor.setSelection(position);
            Utilities.disableSelection(spnTypeOfVisitor, context);
        }

        if (!Utils.isEmpty(visitorList.getmImageUrl())) {
            ll_registerImageCreateAppointment.setVisibility(View.VISIBLE);
            PicassoTrustAllCerificate.getInstance(context).
                    load(visitorList.getmImageUrl()).
                    error(context.getResources().getDrawable(R.drawable.profile)).
                    placeholder(context.getResources().getDrawable(R.drawable.loading)).
                    into(cvRegisteredImage);
        }

       /* if (!visitorList.getRequireAuthentication()) {
            if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                llAuthenticationFields.setVisibility(View.GONE);
                llAuthenticationButton.setVisibility(View.GONE);
            } else {
                llAuthenticationFields.setVisibility(View.VISIBLE);
                btnResendKey.setVisibility(View.VISIBLE);

            }
        }*/

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
            llAuthenticationFields.setVisibility(View.VISIBLE);
            llAuthenticationButton.setVisibility(View.VISIBLE);
        } else {
            if (!visitorList.getRequireAuthentication()) {
                llAuthenticationFields.setVisibility(View.GONE);
                llAuthenticationButton.setVisibility(View.GONE);
            } else {
                llAuthenticationFields.setVisibility(View.VISIBLE);
                llAuthenticationButton.setVisibility(View.VISIBLE);
            }
        }

        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
            btnBypass.setVisibility(View.GONE);
        } else {
            btnBypass.setVisibility(View.VISIBLE);
        }

        if (!Utils.isEmpty(visitorList.getRegisteredVisitorId())) {
            Utilities.disableInput(etFirstName);
            //Utilities.disableInput(etLastName);
            Utilities.disableInput(etEmail);
            Utilities.disableInput(etCompanyName);
            llPreApptReAuth.setVisibility(View.VISIBLE);
        }

    }

    private void showAdditionalDetailDialog(VisitorList visitor) {
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
        final LinearLayout llRemarks = (LinearLayout) dialog.findViewById(R.id.llAddtionalDetailRemarks);
        final EditText etRemarks = (EditText) dialog.findViewById(R.id.etAddDetailRemarks);
        Utilities.disableInput(etRemarks);
        final Spinner spnTypeOfVisitor = (Spinner) dialog.findViewById(R.id.spnAddDetail_TypeOfVisitor);
        final Spinner spnPurposeOfVisit = (Spinner) dialog.findViewById(R.id.spnAddDetail_purposeOfVisit);
        final EditText etType = (EditText) dialog.findViewById(R.id.etAddDetailTypeOfVisitor);
        final EditText etPurpose = (EditText) dialog.findViewById(R.id.etAddDetailPurposeOfVisit);
        final LinearLayout llQuestion = (LinearLayout) dialog.findViewById(R.id.llQuestion);
        llRemarks.setVisibility(View.VISIBLE);

        bindTypeOfVisitors(spnTypeOfVisitor);
        bindPurposeOfVisit(spnPurposeOfVisit);

        boolean isEditTextPurposeData = false;
        boolean isEditTextTypeData = false;

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView rv_questions = (RecyclerView) dialog.findViewById(R.id.rv_AddDetailQuestions);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnAddDetailConfirm);


        if (!Utils.isEmpty(visitor.getCompany())) {
            etCompany.setText(visitor.getCompany());
            Utilities.disableInput(etCompany);
        } else {
            if (additionalDetail.getmCompany() != null && !additionalDetail.getmCompany().isEmpty()) {
                etCompany.setText(additionalDetail.getmCompany());
            }
        }

        if (visitor.getRegisteredVisitorId() != null && visitor.getRegisteredVisitorId() != 0) {
                Utilities.disableInput(etCompany);
        }

        if (!Utils.isEmpty(visitor.getAccessCardNo())) {
            etBadge.setText("" + visitor.getAccessCardNo());
            Utilities.disableInput(etBadge);
        } else {
            if (additionalDetail.getmAccessCardNo() != null && !additionalDetail.getmAccessCardNo().isEmpty()) {
                etBadge.setText(additionalDetail.getmAccessCardNo());
            }
        }

        if (!Utils.isEmpty(visitor.getRemark())) {
            etRemarks.setText(visitor.getRemark());
            Utilities.disableInput(etRemarks);
        } else {
            if (additionalDetail.getRemarks() != null && !additionalDetail.getRemarks().isEmpty()) {
                etRemarks.setText(additionalDetail.getRemarks());
            }
        }
        if (!Utils.isEmpty(visitor.getPurposeOfVisitId())) {
            //int position = Utilities.getPurposeToVisitById(masterResponse, visitor.getPurposeOfVisitId());
            etPurpose.setVisibility(View.VISIBLE);
            spnPurposeOfVisit.setVisibility(View.GONE);
            etPurpose.setText(Utilities.GetPurposeOfVisit(masterResponse, visitor.getPurposeOfVisitId()));
            Utilities.disableInput(etPurpose);
            isEditTextPurposeData = true;
        } else {
            if (!Utils.isEmpty(additionalDetail.getmPurposeOfVisitId())) {
                int position = Utilities.getPurposeToVisitById(masterResponse, additionalDetail.getmPurposeOfVisitId());
                spnPurposeOfVisit.setSelection(position);
            }
        }

        if (!Utils.isEmpty(visitor.getTypeOfVisitorId())) {
            etType.setVisibility(View.VISIBLE);
            spnTypeOfVisitor.setVisibility(View.GONE);
            etType.setText(Utilities.GetTypeOfVisitor(masterResponse, visitor.getTypeOfVisitorId()));
            Utilities.disableInput(etType);
            isEditTextTypeData = true;
        } else {
            if (!Utils.isEmpty(additionalDetail.getmTypeOfVisitorId())) {
                int position = Utilities.getTypeOfVisitorById(masterResponse, additionalDetail.getmTypeOfVisitorId());
                spnTypeOfVisitor.setSelection(position);
            }
        }

        if (dlFields != null) {
            etDLNumber.setText(dlFields.getDocumentNumber() != null ? dlFields.getDocumentNumber() : "");
            etStateCode.setText(dlFields.getStateCode() != null ? dlFields.getStateCode() : "");
            etCity.setText(dlFields.getCity() != null ? dlFields.getCity() : "");
            etZipCode.setText(dlFields.getZipCode() != null ? dlFields.getZipCode() : "");
            etDOB.setText(dlFields.getDateOfBirth() != null ? dlFields.getDateOfBirth() : "");

        }


        final boolean finalIsEditTextPurposeData = isEditTextPurposeData;
        final boolean finalIsEditTextTypeData = isEditTextTypeData;


        if (visitorQuestionArrayList.isEmpty()) {
            final MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                    getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);

            ArrayList<Question> questionArrayList = new ArrayList<Question>();
            ArrayList<Question> questions = new ArrayList<Question>();
            if (masterResponse.getQuestions() != null) {
                questionArrayList = masterResponse.getQuestions();
                for (Question question : questionArrayList) {
                    if (!question.getRequired()) {
                        questions.add(question);
                    }
                }
            }


            QuestionAdapter questionAdapter = null;
            if (questions.size() > 0) {
                llQuestion.setVisibility(View.VISIBLE);
                questionAdapter = new QuestionAdapter(context, questions);
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
                    visitorList.setRemark(etRemarks.getText().toString());
                    visitorList.setBirthdate(etDOB.getText().toString());
                    visitorList.setZipcode(etZipCode.getText().toString());
                    visitorList.setCity(etCity.getText().toString());
                    visitorList.setStatecode(etStateCode.getText().toString());
                    visitorList.setIdnumber(etDLNumber.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    if (finalIsEditTextTypeData) {
                        visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                                etType.getText().toString()));
                    } else {
                        visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                                spnTypeOfVisitor.getSelectedItem().toString()));
                    }
                    if (finalIsEditTextPurposeData) {
                        visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                                etPurpose.getText().toString()));
                    } else {
                        visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                                spnPurposeOfVisit.getSelectedItem().toString()));
                    }
                    additionalDetail = data(visitorList);
                    if (finalQuestionAdapter != null) {
                        visitorQuestionArrayList = finalQuestionAdapter.getFilledQuestions();
                    }
                    dialog.dismiss();

                }
            });
        } else {
            llQuestion.setVisibility(View.VISIBLE);
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
                    visitorList.setIdnumber(etDLNumber.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    visitorList.setRemark(etRemarks.getText().toString());
                    if (finalIsEditTextTypeData) {
                        visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                                etType.getText().toString()));
                    } else {
                        visitorList.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                                spnTypeOfVisitor.getSelectedItem().toString()));
                    }

                    if (finalIsEditTextPurposeData) {
                        visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                                etPurpose.getText().toString()));
                    } else {
                        visitorList.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                                spnPurposeOfVisit.getSelectedItem().toString()));
                    }
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
        data.setRemarks(visitorList.getRemark());
        data.setmAccessCardNo(visitorList.getAccessCardNo());
        data.setmTypeOfVisitorId(visitorList.getTypeOfVisitorId());
        data.setmPurposeOfVisitId(visitorList.getPurposeOfVisitId());
        return data;
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void captureVisitorImageCamera2() {
        Intent i = new Intent(CreateAppointmentActivityOne.this,CaptureActivity.class);
        i.putExtra(AppConfig.BUNDLE_IS_FROM_PREAPPOINTMENT,true);
        startActivityForResult(i,CAPTURE_ID);
        //startActivityForResult(new Intent(CreateAppointmentActivityOne.this, CaptureActivity.class), CAPTURE_ID);
    }
    private void bindPurposeOfVisit(Spinner spinner) {
        ArrayList<String> purposeOfVisit = new ArrayList<String>();

        if (masterResponse == null) {
            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(
                    Constants.MASTER_RESPONSE, ""), MasterResponse.class);
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
            Utilities.showPopup(context, "", getString(R.string.purpose_of_data_not_found));
        }
    }

    private void bindTypeOfVisitors(Spinner spinner) {
        ArrayList<String> typeOfVisitor = new ArrayList<String>();
        if (masterResponse == null) {
            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(
                    Constants.MASTER_RESPONSE, ""), MasterResponse.class);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit_CreateAppointmentTwo:
                if (isValid()) {

                    String etMobile = Utilities.getReplaceText(etMobileNo.getText().toString());
                    if (Utilities.isInternetConnected(context)) {

                        if (imageUploadObject != null) {

                            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                                    equalsIgnoreCase("E")) {
                                imageUploadObject.setEmailId(etEmail.getText().toString().trim());
                            } else {
                                imageUploadObject.setMobileNo(etMobile.trim());
                            }
                            //imageUploadObject.setMobileNo(etMobileNo.getText().toString().trim());
                            imageUploadObject.setRegisteredVisitorId(visitorLogMobileViewModel.
                                    getVisitorList().get(0).getRegisteredVisitorId() != null ? visitorLogMobileViewModel.
                                    getVisitorList().get(0).getRegisteredVisitorId() : 0);
                            imageUploadObjects.add(imageUploadObject);
                        }

                        visitorList = visitorLogMobileViewModel.getVisitorList().get(0);
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setEmail(etEmail.getText().toString());
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setCompany(etCompanyName.getText().toString());
                        visitorLogMobileViewModel.getVisitorList().get(0);
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setName(etFirstName.getText().toString() + " " + etLastName.getText().toString());
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setFirstName(etFirstName.getText().toString());
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setLastName(etLastName.getText().toString());

                        if (llAuthenticationFields.getVisibility() == View.VISIBLE) {
                            visitorLogMobileViewModel.getVisitorList().get(0).
                                    setAuthenticationKey(etAuthenticationKey.getText().toString().trim());
                        }
                        visitorLogMobileViewModel.getVisitorList().get(0).
                                setAccessCardNo(etaccessCardnNo.getText().toString().trim());
                        //visitorLogMobileViewModel.getVisitorList().get(0).setVehicleRegistrationNo(etvehicleNo.getText().toString().trim());
                            /*visitorLogMobileViewModel.getVisitorList().get(0).
                                    setAuthenticationKey(etAuthenticationKey.getText().toString().trim());*/

                        // bind visitor questions
                        /*if (visitorQuestionAdapter != null && visitorQuestionAdapter.getFilledQuestions() != null) {
                            visitorLogMobileViewModel.getVisitorList().get(0).setVisitorQuestions(
                                    visitorQuestionAdapter.getFilledQuestions());
                        }*/

                        try {
                            final QuestionAdapter finalQuestionAdapter = questionAdapter;
                            final VisitorQuestionAdapter finalVisitorQuestionAdapter = visitorQuestionAdapter;
                            if (finalQuestionAdapter != null) {
                                visitorRequiredQuestionArrayList = finalQuestionAdapter.getFilledQuestions();
                            } else {
                                if (finalVisitorQuestionAdapter != null) {
                                    visitorRequiredQuestionArrayList = finalVisitorQuestionAdapter.getFilledQuestions();
                                }
                            }
                            //visitorRequiredQuestionArrayList = finalQuestionAdapter.getFilledQuestions();
                            ArrayList<VisitorQuestion> visitorQuestions = new ArrayList<>();
                            for (VisitorQuestion question : visitorQuestionArrayList) {
                                if (question.getAnswer() != null && !question.getAnswer().isEmpty()) {
                                    visitorQuestions.add(question);
                                }
                            }
                            if (visitorQuestions.size() >= 1) {
                                visitorRequiredQuestionArrayList.addAll(visitorQuestions);
                            }
                            visitorLogMobileViewModel.getVisitorList().get(0).setVisitorQuestions(visitorRequiredQuestionArrayList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //visitorLogMobileViewModel.getVisitorList().get(0).setRemark(etRemarks.getText().toString());

                        // Check for bypass for the appointment
                        if (PreAppointmentActivity.GlobalByPass || PreAppointmentActivity.locallyBypassedOnce) {

                            visitorLogMobileViewModel.getVisitorList().get(0).
                                    setRequireAuthentication(false);
                            visitorLogMobileViewModel.getVisitorList().get(0).
                                    setIsAuthenticationByPassed(true);
                            visitorLogMobileViewModel.getVisitorList().get(0).
                                    setIsAuthenticationByPassedBy(PreAppointmentActivity.GlobalByPassedBy);

                        } else {

                            visitorLogMobileViewModel.getVisitorList().get(0).
                                    setIsAuthenticationByPassed(false);
                        }

                        if (visitorLogMobileViewModel.getVisitorList().get(0).getTypeOfVisitorId() != null &&
                                visitorLogMobileViewModel.getVisitorList().get(0).getTypeOfVisitorId() != 0) {
                            visitorLogMobileViewModel.getVisitorList().get(0).setTypeOfVisitorId(visitorLogMobileViewModel.getVisitorList().get(0).getTypeOfVisitorId());
                        } else {
                            visitorLogMobileViewModel.getVisitorList().get(0).setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                                    spnTypeOfVisitor.getSelectedItem().toString()));
                        }


                        if (visitorLogMobileViewModel.getVisitorList().get(0).getPurposeOfVisitId() != null &&
                                visitorLogMobileViewModel.getVisitorList().get(0).getPurposeOfVisitId() != 0) {
                            visitorLogMobileViewModel.getVisitorList().get(0).setPurposeOfVisitId(visitorLogMobileViewModel.getVisitorList().get(0).getPurposeOfVisitId());
                        } else {
                            visitorLogMobileViewModel.getVisitorList().get(0).setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                                    spnPurposeOfVisit.getSelectedItem().toString()));
                        }

                        ArrayList<VisitorList> visitorListsArrayList = new ArrayList<>();

                        /*visitorList.setCompany(additionalDetail.getmCompany());
                        visitorList.setPurposeOfVisitId(additionalDetail.getmPurposeOfVisitId());
                        visitorList.setTypeOfVisitorId(additionalDetail.getmTypeOfVisitorId());
                        visitorList.setRemark(additionalDetail.getRemarks());
                        visitorList.setAccessCardNo(additionalDetail.getmAccessCardNo());*/

                        if (!isFromAdditional) {
                            if (isFromDL) {
                                //visitorList.setCompany(visitorList.getCompany() != null ? visitorList.getCompany() : "");
                                visitorList.setStatecode(dlFields.getStateCode() != null ? dlFields.getStateCode() : "");
                                visitorList.setCity(dlFields.getCity() != null ? dlFields.getCity() : "");
                                visitorList.setZipcode(dlFields.getZipCode() != null ? dlFields.getZipCode() : "");
                            }
                        }


                        visitorListsArrayList.add(visitorList);
                        visitorLogMobileViewModel.setVisitorList(visitorListsArrayList);
                        visitorLogMobileViewModel.setNoOfVisitor(1);


                        VisitorEntryModel visitorEntryModel = new VisitorEntryModel();
                        visitorEntryModel.setVisitorLogMobileViewModel(visitorLogMobileViewModel);


                        if (!etLastName.getText().toString().isEmpty()) {
                            CallSexualOffender(etFirstName.getText().toString() + " " + etLastName.getText().toString(), visitorEntryModel);
                        } else {
                            CallSexualOffender(etFirstName.getText().toString(), visitorEntryModel);
                        }

                        //onClickSubmit(visitorEntryModel);

                    } else {
                        Utilities.showNoInternetPopUp(context);
                    }
                    //Intent intent = new Intent(this, CreateAppointmentActivityTwo.class);
                    //startActivity(intent);
                }
                break;

            case R.id.btnBypass_CreateAppointmentTwo:

                if (PreAppointmentActivity.GlobalByPass) {
                    llAuthenticationFields.setVisibility(View.GONE);
                    llAuthenticationButton.setVisibility(View.GONE);
                } else if (PreAppointmentActivity.locallyBypassedOnce) {
                    llAuthenticationFields.setVisibility(View.GONE);
                    llAuthenticationButton.setVisibility(View.GONE);
                } else {
                    final Dialog builder = new Dialog(context);
                    builder.setContentView(R.layout.login_confirmation_dialog);
                    builder.setCancelable(true);
                    builder.show();
                    initConfirmDialog(builder);
                }

                break;

            case R.id.btnResendKey_CreateAppointmentTwo:

                if (visitorLogMobileViewModel.getVisitorList().get(0).getMobile() != null &&
                        !visitorLogMobileViewModel.getVisitorList().get(0).getMobile().isEmpty()) {

                    if (!clickedOnce) {
                        try {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("isdCode", visitorLogMobileViewModel.getVisitorList().get(0).getIsdCode());
                            map.put("mobile", visitorLogMobileViewModel.getVisitorList().get(0).getMobile());
                            map.put("email", visitorLogMobileViewModel.getVisitorList().get(0).getEmail());
                            map.put("requestClientDetails", Utilities.requestclientDetails(context));
                            Utilities.showprogressDialogue("", "please wait", context, false);
                            mathodCallToReSendOtp(map, context);
                            clickedOnce = true;
                            btnResendKey.setEnabled(false);
                            btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    Toast.makeText(context, "You Have Not Entered Mobile Number", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.ivReset_createAppointmentActivityOne:
                Intent intent1 = new Intent(this, HomeScreenActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;


            case R.id.ivLogo_createAppointmentActivity:
                Utilities.redirectToHome(context);
                break;
            case R.id.btnPreApptReAuthenticate:
                callGetAuthenticateVisitor();
                break;


            case R.id.ivFirstImage_createAppointmentActivityOne:

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);

                } else {

                    if(Utilities.getDevice().equalsIgnoreCase("samsung")){
                        captureVisitorImageCamera2();
                    }else{
                        captureDocumentImage();
                    }
                }
                break;

            case R.id.tvCreateAppointmentTakenPhoto:

                if (tvTakenPhoto.getText().toString().equalsIgnoreCase(getResources().getString(R.string.reset_forimage))) {

                    tvTakenPhoto.setText(getResources().getString(R.string.live_picture));
                    ivProfile.setImageBitmap(null);
                    ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
                    imageUploadObject = null;
                }
                break;


            case R.id.btn_AddDetails:
                showAdditionalDetailDialog(PreAppointmentActivity.visitorLogMobileViewModel.getVisitorList().get(0));
                break;

            case R.id.ivSos:
              Utilities.showDialogForSos(CreateAppointmentActivityOne.this);
                break;

        }
    }
    private void onClickSubmit(VisitorEntryModel visitorEntryModel) {

        Utilities.showprogressDialogue(getString(R.string.registering), getString(R.string.please_wait), context, true);
        Call<ResponseBody> call = ApiUtils.getAPIService().visitorEntry(Utilities.getToken(context), visitorEntryModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Tag", "visitor entry response code =" + response.code());
                if (response.code() == 200 || response.code() == 201) {
                    try {
                        //parse response here
                        Gson gson = new Gson();
                        String data = response.body().string().toString();
                        boolean isUpload = false;
                        model = new VisitorLogMobileViewModel();
                        model = gson.fromJson(data, VisitorLogMobileViewModel.class);
                        imageUploadObjects = bindVisitorIdToImage(model.getVisitorList());
                        if (imageUploadObjects != null && !imageUploadObjects.isEmpty()) {
                            ImageUploadObject object = imageUploadObject;
                            if (object.getUrl() != null && !object.getUrl().isEmpty()) {
                                isUpload = true;
                            }

                        }
                        if (isUpload) {
                            if (model.getMessage() != null && !model.getMessage().isEmpty()) {
                                Toast.makeText(CreateAppointmentActivityOne.this, "" + model.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            new SPbean(context).setPreference(Constants.DATA_TO_UPLOADED, gson.toJson(imageUploadObjects));
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
                                        visitorLogId = model.getVisitorLogId();
                                        //lastvisitor = model.getNoOfVisitor();
                                        //callApiForPrintPass(model.getVisitorLogId());
                                        if (!isPrinterNotAvailable) {
                                            callPrintPassFromHandler();
                                        } else {
                                            Runnable runnable = new Runnable() {
                                                @Override
                                                public void run() {
                                                    startActivity(new Intent(CreateAppointmentActivityOne.this,
                                                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                                }
                                            };
                                            Utilities.hideProgress();
                                            Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);
                                        }
                                    } else {
                                        Utilities.hideProgress();
                                        Runnable runnable = new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(CreateAppointmentActivityOne.this,
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
                                            startActivity(new Intent(CreateAppointmentActivityOne.this,
                                                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    };
                                    Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);

                                } else {
                                    Utilities.hideProgress();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(CreateAppointmentActivityOne.this,
                                                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    };
                                    Utilities.showPopuprunnable(context, "" + model.getMessage().toString(), false, runnable);
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
                //write log method
                Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                        "Line no:" + t.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                t.printStackTrace();
            }
        });

    }

    private void callApiForPrintPass(int visitorLogId) {
       /* String pass = AppConfig.PRINT_PASS_LINK+"visitorId="+"0"+"&VisitorLogId="+visitorLogId;
        Log.d("TAG","Pass String = "+pass);

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
                        // do your stuff here
                        Utilities.hideProgress();
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
        //map.put("visitorId", visitorLogId);
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
                //Log.d("TAG", "Image policy = " + uri.getPath());
                imageUploadObject = new ImageUploadObject();
                imageUploadObject.setUrl(uri.getPath());
                isFrontCamera = LoginActivity.isSelfeHeplKiosk;
            } else {
                Utilities.showPopup(CreateAppointmentActivityOne.this, "", "No Image Found");
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
                if (imageUploadObjects != null) {

                    for (int i = 0; i < imageUploadObjects.size(); i++) {
                        ImageUploadObject object = imageUploadObjects.get(i);
                        boolean isUploaded = FileTransferHelper.uploadFile(context, object.getVisitorid(),
                                object.getRegisteredVisitorId(), object.getByPassVisitorId(), new File(object.getUrl()));

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
            try {
                if (aBoolean) {
                    if (model != null) {
                        String nextAction = "";
                        if (model.getNextAction() != null) {
                            nextAction = model.getNextAction().trim();
                        }
                        Log.d("TAG", "Next Action" + " " + nextAction);
                        if (nextAction.equalsIgnoreCase(Constants.PRINT_PASS)) {
                            if (CommonPlaceForOjects.settings.getPrintPass()) {
                                visitorLogId = model.getVisitorLogId();
                                isFromUpload = true;
                                //lastvisitor = model.getNoOfVisitor();
                                //callApiForPrintPass(model.getVisitorLogId());
                                if (!isPrinterNotAvailable) {
                                    Utilities.hideProgress();
                                    callPrintPassFromHandler();
                                } else {
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(CreateAppointmentActivityOne.this,
                                                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                        }
                                    };
                                    Utilities.hideProgress();
                                    Utilities.showPopuprunnable(context, "" + model.getMessage(), true, runnable);
                                }
                            } else {
                                Utilities.hideProgress();
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(CreateAppointmentActivityOne.this,
                                                HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    }
                                };
                                Utilities.showPopuprunnable(context, "" + model.getMessage(), true, runnable);
                            }
                        } else if (nextAction.equalsIgnoreCase(Constants.WAIT_FOR_HOST_APPROVAL)) {
                            Utilities.hideProgress();
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(CreateAppointmentActivityOne.this,
                                            HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            };
                            Utilities.showPopuprunnable(context, "" + model.getMessage(), true, runnable);
                        } else {
                            Utilities.hideProgress();
                            startActivity(new Intent(CreateAppointmentActivityOne.this,
                                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                    onBackPressedFlag = true;
                }

            } catch (Exception e) {

            }
            // Utilities.hideProgress();
        }
    }

    private ArrayList<ImageUploadObject> bindVisitorIdToImage(ArrayList<VisitorList> visitorList) {
        for (int j = 0; j < imageUploadObjects.size(); j++) {
            ImageUploadObject object = imageUploadObjects.get(j);

            for (int i = 0; i < visitorList.size(); i++) {
                VisitorList temp = visitorList.get(i);


                if (CommonPlaceForOjects.settings != null &&
                        CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")) {
                    if (temp.getMobile().toString().trim().equals(object.getMobileNo())) {
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

        return imageUploadObjects;
    }


    private void initConfirmDialog(final Dialog builder) {
        final EditText etLoginId_confirmId = (EditText) builder.findViewById(R.id.etLoginId_confirmId);
        final EditText etPassword_confirmPassword = (EditText) builder.findViewById(R.id.etPassword_confirmPassword);
        final Button btnConfirm_loginConfirmActivity = (Button) builder.findViewById(R.id.btnConfirm_loginConfirmActivity);
        final Button btnCancel_loginConfirmActivity = (Button) builder.findViewById(R.id.btnCancel_loginConfirmActivity);
        final ImageView ivShowPassword = (ImageView) builder.findViewById(R.id.ivShowPassword);

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

    // call byPass api for bypassing appointment
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
                            hideFields(profile.getEmployeeId());
                            builder.dismiss();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Utilities.hideProgress();
                } else {
                    try {
                        JSONArray jsonArray = new JSONArray(response.errorBody().string().toString());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String error = jsonObject.getString("message");
                        Utilities.showPopup(context, "", "" + error);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
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

    private void hideFields(Integer employeeId) {

        llAuthenticationFields.setVisibility(View.GONE);
        llAuthenticationButton.setVisibility(View.GONE);
        PreAppointmentActivity.locallyBypassedOnce = true;
        PreAppointmentActivity.GlobalByPassedBy = employeeId;
    }

    private boolean isValid() {
        // TODO Validation to be done
        int error = 0;
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
                            //captureDocumentImage();
                            if(Utilities.getDevice().equalsIgnoreCase("samsung")){
                                captureVisitorImageCamera2();
                            }else{
                                captureDocumentImage();
                            }
                        }
                        error++;
                    }
                }
            }
        }


        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError(getString(R.string.error_visitor_name));
            etFirstName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etFirstName.requestFocus();
            }
        }
      /*  if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(getString(R.string.error_last_name));
            etLastName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etLastName.requestFocus();
            }
        }
*/

        if (!actvIsdCode.getText().toString().isEmpty() && !Utilities.isValidCountryCode(actvIsdCode.getText().toString())) {
            actvIsdCode.setError(getString(R.string.error_isd_invalid));
            llCreateFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
            if (error == 0) {
                error++;
            }
        }


        /*if (actPersonToMeet.getText().toString().isEmpty()) {
            actPersonToMeet.setError(getString(R.string.error_person_to_meet));
            if (error == 0) {
                error++;
                actPersonToMeet.requestFocus();
            }
        }*/

        if (!etEmail.getText().toString().isEmpty()) {
            String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            if (!etEmail.getText().toString().matches(emailRegex)) {
                etEmail.setError(getResources().getString(R.string.invalid_email));
                etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                if (error == 0) {
                    etEmail.requestFocus();
                    error++;
                }
            }
        }


        if (llAuthenticationFields.getVisibility() == View.VISIBLE) {

            if (etAuthenticationKey.getText().toString().isEmpty()) {
                etAuthenticationKey.setError(getResources().getString(R.string.authentication_error));
                etAuthenticationKey.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                if (error == 0) {
                    etAuthenticationKey.requestFocus();
                    error++;
                }
            }
        }

        if (getMandatoryQuestionsCount() > 0) {
            if (!isMandatoryQuestionsAnswered()) {
                Toast.makeText(context, "Please fill Mandatory Questions !!!", Toast.LENGTH_SHORT).show();
                if (error == 0) {
                    error++;
                }
            }
        }

        final QuestionAdapter finalQuestionAdapter = questionAdapter;
        final VisitorQuestionAdapter finalVisitorQuestionAdapter = visitorQuestionAdapter;
        if (finalQuestionAdapter != null) {
            if (!isQuestionValid(finalQuestionAdapter.getFilledQuestions())) {
                Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                if (error == 0) {
                    error++;
                }
            }
        } else {
            if (finalVisitorQuestionAdapter != null) {
                if (!isQuestionValid(finalVisitorQuestionAdapter.getFilledQuestions())) {
                    Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                    if (error == 0) {
                        error++;
                    }
                }
            }
        }


        return !(error > 0);
    }

    public int getMandatoryQuestionsCount() {
       /* MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
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

        if (resultCode != Activity.RESULT_CANCELED) {

            switch (requestCode) {
                case PICK_IMAGE_ID:

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                        Bitmap tempBitmap = Utilities.modifyOrientation(bitmap, fileUri.getPath(),false);

                        // make a copy of file
                        Uri uri = makePhotoCopy(tempBitmap);

                        if (fileUri != null) {
                            if (new File(fileUri.getPath()).exists()) {
                                new File(fileUri.getPath()).delete();
                            }
                        }

                        ivProfile.setImageBitmap(tempBitmap);
                        tvTakenPhoto.setText(getResources().getString(R.string.reset_forimage));
                        Log.d("TAG", "Image policy = " + uri.getPath());
                        imageUploadObject = new ImageUploadObject();
                        imageUploadObject.setUrl(uri.getPath());

                    } catch (IOException e) {
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
                        if(license.firstName==null || license.firstName.equalsIgnoreCase("")){
                            dlData.setFullName(license.lastName);
                            dlData.setFirstName("");
                            dlData.setLastName("");
                        }else{
                            dlData.setFirstName(license.firstName);
                            dlData.setLastName(license.lastName);
                        }
                        dlData.setAddress(license.addressStreet);
                        dlData.setCity(license.addressCity);
                        dlData.setStateCode(license.addressState);
                        dlData.setZipCode(license.addressZip);
                        dlData.setSex(license.gender.equalsIgnoreCase("1")?"Male":"Female");
                        dlData.setDocumentNumber(license.licenseNumber);
                        SimpleDateFormat df_parse = new SimpleDateFormat("yyyyMMdd");
                        SimpleDateFormat df_parse1 = new SimpleDateFormat("MMddyyyy");
                        SimpleDateFormat df_show = new SimpleDateFormat("dd MMM yyyy");
                        Date DOB = null,DOE = null;
                        try {
                            int start = Integer.parseInt(license.birthDate.substring(0,2));
                            if(start>13) {
                                DOB = df_parse.parse(license.birthDate);
                                DOE = df_parse.parse(license.expiryDate);
                            }else{
                                DOB = df_parse1.parse(license.birthDate);
                                DOE = df_parse1.parse(license.expiryDate);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        dlData.setDateOfBirth(df_show.format(DOB));
                        dlData.setDateOfExpiry(df_show.format(DOE));
                        dlData.setIssueDate(license.issueDate);
                        showDrivingLicenseDialog(dlData);
                    }
                    break;

                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
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

    // resend OTP
    public static void mathodCallToReSendOtp(HashMap<String, Object> map, final Context context) {

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
                Utilities.hideProgress();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (onBackPressedFlag) {
            startActivity(new Intent(CreateAppointmentActivityOne.this,
                    HomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            onBackPressedFlag = false;
        }
    }

    private void CallSexualOffender(String etName, final VisitorEntryModel visitorEntryModel) {
        if (AppConfig.IsSexualOffender) {
            /*String Fname = dlFields.getFirstName() != null ? dlFields.getFirstName() : "";
            String Lname = dlFields.getLastName() != null ? dlFields.getLastName() : "";
            String name = Fname.trim() + "" + Lname.trim();
            String vName = !name.isEmpty() ? name : etName;*/
            String stateCode = dlFields.getStateCode() != null ? dlFields.getStateCode() : "";
            String city = dlFields.getCity() != null ? dlFields.getCity() : "";
            String zipCode = dlFields.getZipCode() != null ? dlFields.getZipCode() : "";
          /*  String stateCode = visitorList.getStatecode() != null ? visitorList.getStatecode() : "";
            String city = visitorList.getCity() != null ? visitorList.getCity() : "";
            String zipCode = visitorList.getZipcode() != null ? visitorList.getZipcode() : "";*/
            Utilities.showprogressDialogue(context.getString(R.string.checkingsexualoffenderlist), context.getString(R.string.please_wait), context, false);
            Call<ResponseBody> call = ApiUtils.getAPIService().getSexualOffendedList(
                    etName.trim(), stateCode, city, zipCode);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() == 200 || response.code() == 201) {
                        sexualOffends = new ArrayList<>();
                        Gson gson = new Gson();
                        String s = null;
                        try {
                            Utilities.hideProgress();
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
                                onClickSubmit(visitorEntryModel);
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
                    Utilities.hideProgress();
                    tvSexualOffendMatch.setVisibility(View.GONE);
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
                                    onClickSubmit(visitorEntryModel);

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

    public void callPrintPassFromHandler() {

        if (visitorLogId != 0) {
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
                    startActivity(new Intent(CreateAppointmentActivityOne.this,
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
                Utilities.showProgressForAsynk(CreateAppointmentActivityOne.this, getResources().getString(R.string.printing_pass), getResources().getString(R.string.please_wait), fetchPDFTask);
            } else {
                Utilities.hideProgress();
                Utilities.showProgressForAsynk(CreateAppointmentActivityOne.this, getResources().getString(R.string.printing_pass), getResources().getString(R.string.please_wait), fetchPDFTask);
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
                //Utilities.hideProgressForAsync(fetchPDFTask);
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

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    public void showDrivingLicenseDialog(final DLFields dlData) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogue_drivinglicence_details);
        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.ivDrivingLicenseCancel);
        final EditText etFirstname = (EditText) dialog.findViewById(R.id.etDrivingLicenseFirstName);
        final EditText etLastname = (EditText) dialog.findViewById(R.id.etDrivingLicenseLastName);
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
                dlData.setFirstName(etFirstname.getText().toString());
                dlData.setLastName(etLastname.getText().toString());
                dlData.setDocumentNumber(etDLNumber.getText().toString());
                dlData.setStateCode(etStateCode.getText().toString());
                dlData.setCity(etCity.getText().toString());
                dlData.setZipCode(etZipcode.getText().toString());
                dlData.setDateOfBirth(etDOB.getText().toString());
                dlData.setSex(etGender.getText().toString());
                dlData.setDateOfExpiry(etExpiryDate.getText().toString());
                dlData.setIssueDate(etIssueDate.getText().toString());
                dlFields = dlData;
                CreateAppointmentActivityOne.etFirstName.setText(dlData.getFirstName() != null ? dlData.getFirstName() : "");
                CreateAppointmentActivityOne.etFirstName.setEnabled(true);
                CreateAppointmentActivityOne.etFirstName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                CreateAppointmentActivityOne.etLastName.setText(dlData.getLastName() != null ? dlData.getLastName() : "");
                CreateAppointmentActivityOne.etLastName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                CreateAppointmentActivityOne.etLastName.setEnabled(true);
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
            etFirstname.setText(dlData.getFirstName());
            etLastname.setText(dlData.getLastName());
        }else{
            etFirstname.setText(dlData.getFullName());
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

    public void callGetAuthenticateVisitor() {
        String etmobie = Utilities.getReplaceText(etMobileNo.getText().toString());
        HashMap<String, Object> map = new HashMap<>();
        String etIsdCode = actvIsdCode.getText().toString();
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
                                i.putExtra(AppConfig.BUNDLE_IS_FROM_PREAPPOINTMENT,true);
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
