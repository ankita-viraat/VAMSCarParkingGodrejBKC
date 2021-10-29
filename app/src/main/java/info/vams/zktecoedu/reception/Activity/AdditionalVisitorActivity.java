package info.vams.zktecoedu.reception.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.squareup.picasso.Picasso;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import info.vams.zktecoedu.reception.Adapter.CountryFlagAdapter;
import info.vams.zktecoedu.reception.Adapter.QuestionAdapter;
import info.vams.zktecoedu.reception.Adapter.SexualOffenderAdapter;
import info.vams.zktecoedu.reception.Adapter.VisitorQuestionAdapter;

import info.vams.zktecoedu.reception.Model.AdditionalDetail;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.Model.DLFields;
import info.vams.zktecoedu.reception.Model.ImageUploadObject;
import info.vams.zktecoedu.reception.Model.MasterResponse;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.Question;
import info.vams.zktecoedu.reception.Model.RepeatedVisitorAppoinrmtnt;
import info.vams.zktecoedu.reception.Model.SexualOffend;
import info.vams.zktecoedu.reception.Model.SexualOffendedList;
import info.vams.zktecoedu.reception.Model.VisitorList;
import info.vams.zktecoedu.reception.Model.VisitorQuestion;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.CommonPlaceForOjects;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.Imageutils;
import info.vams.zktecoedu.reception.Util.PicassoTrustAllCerificate;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;
import info.vams.zktecoedu.reception.Util.Utilities;
import info.vams.zktecoedu.reception.Util.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static info.vams.zktecoedu.reception.Activity.VisitorCheckInActivity.actIsdCode;
import static info.vams.zktecoedu.reception.Activity.VisitorCheckInActivity.visitorListsArrayList;

public class AdditionalVisitorActivity extends AppCompatActivity implements View.OnClickListener, Imageutils.ImageAttachmentListener  {

    static EditText etFirstName;
    static EditText etLastName;
    EditText etAuthenticationKey;
    public EditText etMobile, etEmail;
    RecyclerView rvQuestions;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Question> questionArrayList;
    CheckBox cbAddMore,sheriffCheckbox,adminCheckbox;
    ImageView ivCancel, ivAdditionalVisitorIsdFlag,ivSos;
    Button btnconfirm, btnBypass, btn_Add_Details, btnAdditionalReAuth, btn_ScanDL, btnResendKey,btnCancelDialog,btnYes;
    TextView tvTakenPhoto, tvMobileNoStarAdditional, tvEmailStarAdditional, tvAdditionalQuestionStarVisitor, tv_Additional_Registered_Image, tvAdditionalSexualOffendMatch,tvHeader,tvAdmin,tvSheriff;
    LinearLayout llAuthentication, ll_AdditionalAuthenticateKey, llAddQuestion, ll_takenPhoto,
            ll_registeredPhoto, ll_AdditionalVisitorMobile, llAdditionalFlagIsd, ll_AdditionalVisitorEmail,
            llByPassAndReSend, llAddReAuth;
    CircleImageView profilePic, cvRegisteredImage;
    VisitorList visitorList;
    VisitorList visitorListForAdditonal;
    AutoCompleteTextView etIsd;
    boolean visible = false;
    VisitorList visitorListForedit;
    boolean isAddVisitorChecked = false;
    Integer positionAt = -0;
    Context context;
    private static int MEDIA_TYPE_IMAGE = 1;
    private static final int CAPTURE_DOCUMENT = 20;
    private static final int PICK_IMAGE_ID = 100;
    private static Uri fileUri;
    ImageUploadObject imageUploadObject;
    boolean byPass = false;
    public MasterResponse masterResponse;
    boolean flag = true;
    VisitorQuestionAdapter visitorQuestionAdapter = null;
    QuestionAdapter questionAdapter = null;
    boolean clickedOnce = false;
    String serverIdPtoofNo = "";
    String dataToSearch;
    String isdSearch;
    String dataIsdMobile;
    boolean isAllHide = false;


    //QuestionAdapter questionAdapter;
    ArrayList<VisitorQuestion> visitorQuestionArrayList = new ArrayList<>();
    ArrayList<VisitorQuestion> visitorRequiredQuestionArrayList;
    ;
    public AdditionalDetail additionalDetail = new AdditionalDetail();
    ArrayList<SexualOffend> sexualOffends = null;
    AlertDialog alertDialog;
    public static final int MY_BLINKID_REQUEST_CODE = 123;
//    protected RecognizerBundle mRecognizerBundle;
    private ListView mListView;
//    UsdlRecognizer usdlRecognizer;
    DLFields dlFields;
    public static boolean isFrontCamera = LoginActivity.isSelfeHeplKiosk;
    Imageutils imageUtils;
    private static final int CAPTURE_ID = 200;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_visitor);
        context = AdditionalVisitorActivity.this;
        imageUtils = new Imageutils(this);
        init();
    }

    private void init() {
        etIsd = (AutoCompleteTextView) findViewById(R.id.et_IsdAddtionalVisitor);
        ivAdditionalVisitorIsdFlag = (ImageView) findViewById(R.id.ivAdditionalVisitorIsdFlag);
        etMobile = (EditText) findViewById(R.id.et_mobileAddtionalVisitor);
        etFirstName = (EditText) findViewById(R.id.et_NameAdditionalVisitor);
        etLastName = (EditText) findViewById(R.id.et_LastNameAdditionalVisitor);
        etEmail = (EditText) findViewById(R.id.et_emailAdditionalVisitor);
        llAdditionalFlagIsd = (LinearLayout) findViewById(R.id.llAdditionalFlagIsd);
        ll_AdditionalVisitorMobile = (LinearLayout) findViewById(R.id.ll_AdditionalVisitorMobile);
        ll_AdditionalVisitorEmail = (LinearLayout) findViewById(R.id.ll_AdditionalVisitorEmail);
        llByPassAndReSend = (LinearLayout) findViewById(R.id.llByPassAndReSend);
        etAuthenticationKey = (EditText) findViewById(R.id.etAdditionalAuthenticationKey);
        ivCancel = (ImageView) findViewById(R.id.iv_addational_visitor_cancel);
        ivCancel.setOnClickListener(this);
        cbAddMore = (CheckBox) findViewById(R.id.cb_addMore);
        btnconfirm = (Button) findViewById(R.id.btnadditionalVisitorConfirm);
        profilePic = (CircleImageView) findViewById(R.id.ivAdditionalVisitorProfPic);
        cvRegisteredImage = (CircleImageView) findViewById(R.id.ivRegistered_additional);

        profilePic.setOnClickListener(this);
        btnconfirm.setOnClickListener(this);

        ivSos = (ImageView) findViewById(R.id.ivSos);
        ivSos.setOnClickListener(this);
        ivSos.setVisibility(View.GONE);


        btnBypass = (Button) findViewById(R.id.btnAdditionalBypass);
        btnResendKey = (Button) findViewById(R.id.btnAdditionalResendKey);
        btnBypass.setOnClickListener(this);
        btnResendKey.setOnClickListener(this);

        btn_Add_Details = (Button) findViewById(R.id.btn_AddDetails);
        btn_Add_Details.setOnClickListener(this);


        ll_takenPhoto = (LinearLayout) findViewById(R.id.ll_additionaltakenPhoto);
        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
            ll_takenPhoto.setVisibility(View.GONE);
        }
        ll_registeredPhoto = (LinearLayout) findViewById(R.id.ll_additionalregisterImage);
        tvTakenPhoto = (TextView) findViewById(R.id.tvAdditional_takenPhoto);
        tvTakenPhoto.setOnClickListener(this);
        tv_Additional_Registered_Image = (TextView) findViewById(R.id.tv_Additional_Registered_Image);


        llAuthentication = (LinearLayout) findViewById(R.id.ll_AdditionalAuthentication);
        ll_AdditionalAuthenticateKey = (LinearLayout) findViewById(R.id.ll_AdditionalAuthenticateKey);


        llAddQuestion = (LinearLayout) findViewById(R.id.llAddQuestion);

        rvQuestions = (RecyclerView) findViewById(R.id.rv_questionsAdditionalVisitor);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvQuestions.setLayoutManager(layoutManager);

        btnAdditionalReAuth = (Button) findViewById(R.id.btnAdditionalReAuthenticate);
        btnAdditionalReAuth.setOnClickListener(this);
        llAddReAuth = (LinearLayout) findViewById(R.id.llAdditionalReAuth);
        tvMobileNoStarAdditional = (TextView) findViewById(R.id.tvMobileNoStarAdditional);
        tvEmailStarAdditional = (TextView) findViewById(R.id.tvEmailStarAdditional);
        tvAdditionalSexualOffendMatch = (TextView) findViewById(R.id.tvSexualOffendMatch);


        llAuthentication.setVisibility(View.GONE);
        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etMobile));
        etMobile.addTextChangedListener(addLineNumberFormatter);

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getDefaultIsdCode() != null) {
            etIsd.setText("+" + CommonPlaceForOjects.settings.getDefaultIsdCode());
            ivAdditionalVisitorIsdFlag.setImageResource(Utilities.setDrawableFlage(etIsd.getText().toString().trim()));
        }

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                equalsIgnoreCase("E")) {
            tvMobileNoStarAdditional.setText("");
            tvEmailStarAdditional.setText("*");
            ll_AdditionalVisitorMobile.setVisibility(View.GONE);
            ll_AdditionalVisitorEmail.setVisibility(View.VISIBLE);
        } else {
            tvMobileNoStarAdditional.setText("*");
            tvEmailStarAdditional.setText("");
            ll_AdditionalVisitorEmail.setVisibility(View.GONE);
            ll_AdditionalVisitorMobile.setVisibility(View.VISIBLE);
        }

        //bindQuestions();

       /* if (VisitorCheckInActivity.GlobalByPass) {
            llAuthentication.setVisibility(View.GONE);
            tvEmailStarAdditional.setVisibility(View.GONE);
            tvMobileNoStarAdditional.setVisibility(View.GONE);
        }*/
        btn_ScanDL = (Button) findViewById(R.id.btn_ScanDL);
        visitorList = new VisitorList();
        visitorListForAdditonal = new VisitorList();
        imageUploadObject = new ImageUploadObject();
        visitorList.setRequireAuthentication(false);
        cbAddMore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isAddVisitorChecked = true;
                } else {
                    isAddVisitorChecked = false;
                }
            }
        });

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
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (etIsd.getText().toString().trim().equalsIgnoreCase("+91") ||
                        etIsd.getText().toString().trim().equalsIgnoreCase("+1")) {
                    etMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    etMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                getAuthenticateVstrBy().equalsIgnoreCase("M")) {

            etMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {

                        isdSearch = etIsd.getText().toString().trim();
                        String mobile = Utilities.getReplaceText(etMobile.getText().toString());
                        isdSearch = isdSearch.replaceAll("\\+", "");
                        dataToSearch = isdSearch + mobile.trim();

                        boolean shouldCall = true;

                        for (int i = 0; i < visitorListsArrayList.size(); i++) {
                            if (!mobile.isEmpty()) {
                                if (visitorListsArrayList.get(i).getMobile().contains(mobile)) {
                                    if (!mobile.trim().isEmpty() && dataToSearch.equals(visitorListsArrayList.get(i).getIsdCode() + visitorListsArrayList.get(i).getMobile())) {
                                        etMobile.setError(getResources().getString(R.string.mobileAlreadyRegister));
                                        shouldCall = false;
                                    }
                                }
                                if (mobile.trim().isEmpty()) {
                                    shouldCall = false;
                                }
                                if (etIsd.getText().toString().equalsIgnoreCase("+91") &&
                                        mobile.length() != 10) {
                                    etMobile.setError("Minimum 10 Digits Required");
                                    shouldCall = false;
                                }

                                if (mobile.trim().length() < 8) {
                                    etMobile.setError("Minimum 8 Digits Required");
                                    shouldCall = false;
                                }

                            }
                        }

                        if (mobile.trim().isEmpty()) {
                            shouldCall = false;
                        }

                        if (VisitorCheckInActivity.GlobalByPass || byPass) {
                            if (etIsd.getText().toString().equalsIgnoreCase("91") &&
                                    !mobile.isEmpty() &&
                                    mobile.trim().length() != 10) {
                                etMobile.setError("Minimum 10 Digits Required");
                                shouldCall = false;
                            }

                            if (!mobile.isEmpty() && mobile.length() < 8) {
                                etMobile.setError("Minimum 8 Digits Required");
                                shouldCall = false;
                            }
                        }


                        if (shouldCall) {
                            clearFieldsforEmailNumber(false);
                            callGetRepeatedVisitor(dataToSearch);
                        }

                    } else {

                    }
                }
            });


        } else if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.
                getAuthenticateVstrBy().equalsIgnoreCase("E")) {

            etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if (!hasFocus) {

                        /*isdSearch = etIsd.getText().toString().trim();
                        dataToSearch = isdSearch + etMobile.getText().toString().trim();
*/
                        boolean shouldCall = true;

                        for (int i = 0; i < visitorListsArrayList.size(); i++) {
                            if (!etEmail.getText().toString().isEmpty()) {
                                if (visitorListsArrayList.get(i).getEmail().equalsIgnoreCase(etEmail.getText().toString())) {
                                    if (etEmail.getText().toString().equals(visitorListsArrayList.get(i).getEmail())) {
                                        etEmail.setError(getResources().getString(R.string.emailAlreadyRegister));
//                                    etEmail.requestFocus();
                                        shouldCall = false;
                                    }
                                    if (etEmail.getText().toString().trim().isEmpty()) {
                                        shouldCall = false;
                                    }
                                    if (!Utilities.isValidEmail(etEmail.getText().toString().trim())) {
                                        etEmail.setError(getString(R.string.invalid_email));
                                        shouldCall = false;
                                    }
                                }
                            }

                            if (etEmail.getText().toString().trim().isEmpty()) {
                                shouldCall = false;
                            }

                            if (VisitorCheckInActivity.GlobalByPass || byPass) {
                                if (!etEmail.getText().toString().isEmpty()) {
                                    if (!Utilities.isValidEmail(etEmail.getText().toString().trim())) {
                                        etEmail.setError(getString(R.string.invalid_email));
                                        shouldCall = false;
                                    }
                                }
                            }

                        }


                        if (shouldCall) {
//                            etEmail.setEnabled(false);
                            clearFieldsforEmailNumber(true);
                            callGetRepeatedVisitor(etEmail.getText().toString().trim());
                        }
                    }
                }
            });
        }


        try {
            visitorListForedit = (VisitorList) getIntent().getSerializableExtra("visitor");
            positionAt = getIntent().getIntExtra("visitorId", -0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bindEditData(visitorListForedit, positionAt);
        bindQuestions(rvQuestions);


       /* if (getMandatoryQuestionsCount() > 0) {
            tvAdditionalQuestionStarVisitor.setText("*");
        } else {
            tvAdditionalQuestionStarVisitor.setText("");
        }*/


      /*  try {
            MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                    getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);
            if (masterResponse.getQuestions() != null && !masterResponse.getQuestions().isEmpty()) {
                llQuestionAdditionalField.setVisibility(View.GONE);
            } else {
                llQuestionAdditionalField.setVisibility(View.GONE);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }*/

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getBypassedAllowed()) {
            btnBypass.setVisibility(View.VISIBLE);
            btnResendKey.setVisibility(View.GONE);
        }else{
            llByPassAndReSend.setVisibility(View.GONE);
        }

/*
        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus &&
                        !etFirstName.getText().toString().isEmpty() &&
                        !etLastName.getText().toString().isEmpty()) {
                    String name = etFirstName.getText().toString() + " " + etLastName.getText().toString();
                    CallSexualOffender(name);
                }
            }
        });
*/


        etIsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                llAdditionalFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_focude_effect_for_isd));
                if (!s.toString().isEmpty()) {
                    ivAdditionalVisitorIsdFlag.setImageResource(Utilities.setDrawableFlage(s.toString().trim()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    etIsd.append("+");
                }
            }
        });

        /*if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
            ll_AdditionalAuthenticateKey.setVisibility(View.GONE);
            btnResendKey.setVisibility(View.GONE);
        }*/
        populateIsdCode(context, etIsd);
        Utilities.addTextChangeListener(context, etMobile);
        Utilities.addTextChangeListener(context, etFirstName);
        Utilities.addTextChangeListener(context, etLastName);
        Utilities.addTextChangeListener(context, etAuthenticationKey);
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
                            ivAdditionalVisitorIsdFlag.setImageResource(county.getFlag());
                        }
                    }
                }
            });

        }
    }

    // bind spinner data
  /*  private void populateSpinnerData() {

        ArrayList<String> idProofType = new ArrayList<String>();
        ArrayList<String> typeOfVisitor = new ArrayList<String>();
        ArrayList<String> purposeOfVisit = new ArrayList<String>();

        if (masterResponse == null) {

            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(
                    Constants.MASTER_RESPONSE, ""), MasterResponse.class);

        }

        idProofType = Utilities.getStringArrayForIdproofSpinner(context, masterResponse, idProofType);
        typeOfVisitor = Utilities.getStringArrayForTypeOfVisitorSpinner(context, masterResponse, typeOfVisitor);
        purposeOfVisit = Utilities.getStringArrayForPurposeOfVisitSpinner(context, masterResponse, purposeOfVisit);

        if (idProofType != null) {
            ArrayAdapter<String> idProofAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, idProofType);
            spIdProofType.setAdapter(idProofAdapter);
        }

        if (typeOfVisitor != null) {
            ArrayAdapter<String> typeOfVisitAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, typeOfVisitor);
            spTypeOfVisitor.setAdapter(typeOfVisitAdapter);
        }

        if (purposeOfVisit != null) {
            ArrayAdapter<String> purposeOfVisitAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, purposeOfVisit);
            spPurposeOfVisit.setAdapter(purposeOfVisitAdapter);
        }
    }*/

    // Bind Questions data
    private void bindQuestions(RecyclerView recyclerView) {
        visitorQuestionAdapter = null;
        MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);

        ArrayList<Question> questionsList = new ArrayList<>();

        try {
            questionArrayList = masterResponse.getQuestions();
            for (Question question : questionArrayList) {
                if (question.getRequired()) {
                    questionsList.add(question);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Tag", "Question Array List =" + new Gson().toJson(questionsList));
        if (!Utils.isEmpty(questionsList)) {
            llAddQuestion.setVisibility(View.VISIBLE);
            questionAdapter = new QuestionAdapter(context, questionsList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(questionAdapter);
        } else {
            llAddQuestion.setVisibility(View.GONE);
        }
    }

    private void bindfilledQuestions(ArrayList<VisitorQuestion> visitorQuestions) {
        try {
            questionAdapter = null;
            ArrayList<VisitorQuestion> questionArrayList = visitorQuestions;

            visitorQuestionAdapter = new VisitorQuestionAdapter(context, questionArrayList);
            rvQuestions.setAdapter(visitorQuestionAdapter);

        } catch (Exception e) {
            //write log method
            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
            e.printStackTrace();
        }
    }

    /**
     * Call alresdy registered visitor
     */
    private void callGetRepeatedVisitor(String content) {
        String moblieNo = Utilities.getReplaceText(etMobile.getText().toString().trim());
        if (Utilities.isInternetConnected(context)) {
            Utilities.showprogressDialogue("Loading", getString(R.string.please_wait), context, false);

            HashMap<String, Object> map = new HashMap<>();
            map.put("searchString", content);
            map.put("isdCode", isdSearch);
            map.put("mobile", moblieNo.trim());
            map.put("email", etEmail.getText().toString().trim());

            if (VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId() != null) {
                map.put("complexId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId());
            }
            map.put("tenantId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId());
            map.put("requestClientDetails", Utilities.requestclientDetails(context));

            Call<ResponseBody> call = ApiUtils.getAPIService().getRepeatedvisitorAppointment(Utilities.getToken(context), map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Utilities.hideProgress();
                    Log.d("Tag", "Response Code For Get Repeated Visitor In Additional Visitor Activity =" + response.code());
                    if (response.code() == 200 || response.code() == 201) {
                        if (response != null) {
                            try {

                                String visitorData = response.body().string().toString();
                                RepeatedVisitorAppoinrmtnt repeatedVisitorAppoinrmtnt = new Gson().fromJson(visitorData, RepeatedVisitorAppoinrmtnt.class);
                                visitorList = repeatedVisitorAppoinrmtnt.getVisitorList();
                                if (visitorList != null) {
                                    bindEditData(visitorList, -2);
                                } else {
                                    llAuthentication.setVisibility(View.VISIBLE);
                                    llByPassAndReSend.setVisibility(View.VISIBLE);
                                    btnBypass.setVisibility(View.VISIBLE);
                                    btnResendKey.setVisibility(View.VISIBLE);
                                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                                        btnBypass.setVisibility(View.GONE);
                                    } else {
                                        btnBypass.setVisibility(View.VISIBLE);
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
                        llAuthentication.setVisibility(View.VISIBLE);
                        llByPassAndReSend.setVisibility(View.VISIBLE);
                        btnBypass.setVisibility(View.VISIBLE);
                        btnResendKey.setVisibility(View.VISIBLE);
                        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                            btnBypass.setVisibility(View.GONE);
                        } else {
                            btnBypass.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable e) {
                    Utilities.hideProgress();
                    try {
                        Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));
                    } catch (Resources.NotFoundException e1) {
                        e1.printStackTrace();
                    }
                    //write log method
                    Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                            "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n");
                    e.printStackTrace();
                }
            });
        } else {
            Utilities.showNoInternetPopUp(context);
        }
    }

    private void bindEditData(VisitorList visitorListForedit, int positionAt) {

        try {

            if (visitorListForedit != null && positionAt == -2) {// this data is fetched from
                // regestered visitor api so it is denoted as -2


                if (CommonPlaceForOjects.settings != null &&
                        CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("M")) {
                    if (!Utils.isEmpty(visitorListForedit.getIsdCode())) {
                        etIsd.setText(etIsd.getText().toString());
                        etIsd.setEnabled(false);


                    }

                    if (!Utils.isEmpty(visitorListForedit.getMobile())) {
                        etMobile.setText(etMobile.getText().toString());
                        etMobile.setEnabled(false);
                    }


                } else {

                    if (!Utils.isEmpty(visitorListForedit.getEmail())) {
                        etEmail.setText(visitorListForedit.getEmail());
                        etEmail.setEnabled(false);
                    }
                }


                if (!Utils.isEmpty(visitorListForedit.getFirstName())) {
                    etFirstName.setText(visitorListForedit.getFirstName());
                }

                if (!Utils.isEmpty(visitorListForedit.getLastName())) {
                    etLastName.setText(visitorListForedit.getLastName());
                }

                if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                    llAuthentication.setVisibility(View.VISIBLE);
                    llByPassAndReSend.setVisibility(View.VISIBLE);
                    btnBypass.setVisibility(View.VISIBLE);
                    btnResendKey.setVisibility(View.VISIBLE);
                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                        btnBypass.setVisibility(View.GONE);
                    } else {
                        btnBypass.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!visitorListForedit.getRequireAuthentication()) {
                        llAuthentication.setVisibility(View.GONE);
                        llByPassAndReSend.setVisibility(View.GONE);
                    } else {
                        llAuthentication.setVisibility(View.VISIBLE);
                        llByPassAndReSend.setVisibility(View.VISIBLE);
                        btnBypass.setVisibility(View.VISIBLE);
                        btnResendKey.setVisibility(View.VISIBLE);
                        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                            btnBypass.setVisibility(View.GONE);
                        } else {
                            btnBypass.setVisibility(View.VISIBLE);
                        }
                    }
                }


                if (!Utils.isEmpty(visitorList.getRegisteredVisitorId())) {
                    etFirstName.setEnabled(false);
                    etLastName.setEnabled(false);
                    llAddReAuth.setVisibility(View.VISIBLE);
                }

               /* if (!visitorListForedit.getRequireAuthentication()) {
                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                        ll_AdditionalAuthenticateKey.setVisibility(View.GONE);
                        btnResendKey.setVisibility(View.GONE);
                        btnBypass.setVisibility(View.GONE);
                    } else {
                        ll_AdditionalAuthenticateKey.setVisibility(View.VISIBLE);
                        btnResendKey.setVisibility(View.VISIBLE);

                    }

                }*/

                if (!Utils.isEmpty(visitorListForedit.getmImageUrl())) {
                    if (visitorList.getBypassedVisitorId() != null) {
                        tv_Additional_Registered_Image.setText(getResources().getString(R.string.byPassRegisterImage));
                    }
                    ll_registeredPhoto.setVisibility(View.VISIBLE);
                    PicassoTrustAllCerificate.getInstance(context).
                            load(visitorList.getmImageUrl()).
                            error(context.getResources().getDrawable(R.drawable.profile)).
                            placeholder(context.getResources().getDrawable(R.drawable.loading)).
                            into(cvRegisteredImage);
                    //imageUploadObject.setUrl(visitorList.getmImageUrl());
                }
            } else if (visitorListForedit != null) {

                if (!Utils.isEmpty(visitorListForedit.getIsdCode())) {
                    etIsd.setText(visitorListForedit.getIsdCode());
                }

                if (!Utils.isEmpty(visitorListForedit.getMobile())) {
                    etMobile.setText(visitorListForedit.getMobile());
                }

                if (!Utils.isEmpty(visitorListForedit.getFirstName())) {
                    etFirstName.setText(visitorListForedit.getFirstName());
                }

                if (!Utils.isEmpty(visitorListForedit.getLastName())) {
                    etLastName.setText(visitorListForedit.getLastName());
                }
                if (!Utils.isEmpty(visitorListForedit.getEmail())) {
                    etEmail.setText(visitorListForedit.getEmail());
                }

                if (!Utils.isEmpty(visitorListForedit.getVisitorQuestions())) {
                    bindfilledQuestions(visitorListForedit.getVisitorQuestions());
                }


                if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                    llAuthentication.setVisibility(View.VISIBLE);
                    llByPassAndReSend.setVisibility(View.VISIBLE);
                    btnBypass.setVisibility(View.VISIBLE);
                    btnResendKey.setVisibility(View.VISIBLE);
                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                        btnBypass.setVisibility(View.GONE);
                    } else {
                        btnBypass.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!visitorListForedit.getRequireAuthentication()) {
                        llAuthentication.setVisibility(View.GONE);
                        llByPassAndReSend.setVisibility(View.GONE);
                    } else {
                        llAuthentication.setVisibility(View.VISIBLE);
                        llByPassAndReSend.setVisibility(View.VISIBLE);
                        btnBypass.setVisibility(View.VISIBLE);
                        btnResendKey.setVisibility(View.VISIBLE);
                        if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()) {
                            btnBypass.setVisibility(View.GONE);
                        } else {
                            btnBypass.setVisibility(View.VISIBLE);
                        }
                    }
                }

                /*if(CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getBypassedAllowed()){
                    btnBypass.setVisibility(View.GONE);
                }else{
                    btnBypass.setVisibility(View.VISIBLE);
                }*/

               /* if (!visitorListForedit.getRequireAuthentication()) {
                    if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVstrAuthenticatiOnEveryTime()) {
                        ll_AdditionalAuthenticateKey.setVisibility(View.GONE);
                        btnResendKey.setVisibility(View.GONE);
                        btnBypass.setVisibility(View.GONE);
                    } else {
                        ll_AdditionalAuthenticateKey.setVisibility(View.VISIBLE);
                        btnResendKey.setVisibility(View.VISIBLE);

                    }
                }*/


                if (!Utils.isEmpty(visitorListForedit.getmImageUrl())) {
                    if (visitorListForedit.getBypassedVisitorId() != null) {
                        tv_Additional_Registered_Image.setText(getResources().getString(R.string.byPassRegisterImage));
                    }
                    ll_registeredPhoto.setVisibility(View.VISIBLE);
                    PicassoTrustAllCerificate.getInstance(context).
                            load(visitorList.getmImageUrl()).
                            error(context.getResources().getDrawable(R.drawable.profile)).
                            placeholder(context.getResources().getDrawable(R.drawable.loading)).
                            into(profilePic);

                    cvRegisteredImage.setEnabled(false);
                    //imageUploadObject.setUrl(visitorList.getmImageUrl());


                }
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {

        try {

            if (CommonPlaceForOjects.settings != null &&
                    CommonPlaceForOjects.settings.getDefaultIsdCode() != null &&
                    !CommonPlaceForOjects.settings.getDefaultIsdCode().isEmpty()) {
                etIsd.setText("+" + CommonPlaceForOjects.settings.getDefaultIsdCode());
            } else {
                etIsd.setText("+91");
            }
            etIsd.setEnabled(true);
            etMobile.setText("");
            etMobile.setEnabled(true);
            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                    equalsIgnoreCase("M")) {
                tvMobileNoStarAdditional.setText("*");
            } else {
                tvEmailStarAdditional.setText("*");
            }
            etMobile.requestFocus();
            etFirstName.setText("");
            etFirstName.setEnabled(true);
            etLastName.setText("");
            etLastName.setEnabled(true);
            etEmail.setText("");
            etEmail.setEnabled(true);

            etAuthenticationKey.setText("");
            etAuthenticationKey.setEnabled(true);
            if (isAddVisitorChecked) {
                cbAddMore.setChecked(true);
            } else {
                cbAddMore.setChecked(false);
            }


            if (clickedOnce) {
                clickedOnce = false;
                btnResendKey.setEnabled(true);
                btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtp));
            }/*else{
                clickedOnce = true;
                btnResendKey.setEnabled(false);
                btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));
            }*/

            serverIdPtoofNo = "";
            byPass = false;

            if (CommonPlaceForOjects.settings != null && !CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
                ll_takenPhoto.setVisibility(View.GONE);
            } else {
                tvTakenPhoto.setText(getString(R.string.live_picture));
                profilePic.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
            }
            cvRegisteredImage.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
            ll_registeredPhoto.setVisibility(View.GONE);


            llAuthentication.setVisibility(View.GONE);
            llByPassAndReSend.setVisibility(View.VISIBLE);
            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getBypassedAllowed()) {
                btnBypass.setVisibility(View.VISIBLE);
                btnResendKey.setVisibility(View.GONE);
            }else{
                llByPassAndReSend.setVisibility(View.GONE);
            }
//            usdlRecognizer = new UsdlRecognizer();
//            ImageSettings.enableAllImages(usdlRecognizer);
            dlFields = new DLFields();
            visitorQuestionArrayList.clear();
            //visitorRequiredQuestionArrayList.clear();
            additionalDetail = new AdditionalDetail();
            visitorQuestionAdapter = null;
            llAddReAuth.setVisibility(View.GONE);
            questionAdapter = null;
            bindQuestions(rvQuestions);

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnadditionalVisitorConfirm:

                if (llAuthentication.getVisibility() == View.GONE && llByPassAndReSend.getVisibility() == View.GONE) {
                    isAllHide = true;
                } else {
                    isAllHide = false;
                }

                if (isValid()) {
                    String firstAndLastName = etFirstName.getText().toString() + " " + etLastName.getText().toString();
                    CallSexualOffender(!etLastName.getText().toString().isEmpty() ? firstAndLastName.trim() : etFirstName.getText().toString());
                }
                break;

            case R.id.ivAdditionalVisitorProfPic:

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

                break;

            case R.id.iv_addational_visitor_cancel:
                finish();
                break;

            case R.id.ivSos:
               Utilities.showDialogForSos(AdditionalVisitorActivity.this);
                break;

            case R.id.tvAdditional_takenPhoto:

                if (tvTakenPhoto.getText().toString().equalsIgnoreCase(getResources().getString(R.string.reset_forimage))) {
                    try {
                        tvTakenPhoto.setText(getResources().getString(R.string.live_picture));
                        profilePic.setImageBitmap(null);
                        profilePic.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
                        imageUploadObject = null;

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;


            case R.id.btn_AddDetails:
                showAdditionalDetailDialog();
                break;

            case R.id.btnAdditionalReAuthenticate:
                callGetAuthenticateVisitor();
                break;

            case R.id.btnAdditionalResendKey:

                if (!etMobile.getText().toString().isEmpty()) {

                    String mobile = Utilities.getReplaceText(etMobile.getText().toString());
                    String isd = etIsd.getText().toString();
                    isd = isd.replaceAll("\\+", "");

                    if (!clickedOnce) {
                        try {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("isdCode", isd.trim());
                            map.put("mobile", mobile.trim());
                            map.put("email", etEmail.getText().toString().trim());
                            map.put("complexId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getComplexId());
                            map.put("tenantId", VisitorCheckInActivity.visitorLogMobileViewDataModel.getTenantId());
                            map.put("requestClientDetails", Utilities.requestclientDetails(context));
                            Utilities.showprogressDialogue("", "please wait", context, false);
                            mathodCallToReSendOtp(map);
                            clickedOnce = true;
                            btnResendKey.setEnabled(false);
                            btnResendKey.setBackgroundColor(getResources().getColor(R.color.resendOtpDissabled));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    etMobile.setError(getString(R.string.mobileRequired));
                }

                break;

            case R.id.btnAdditionalBypass:

                if (VisitorCheckInActivity.GlobalByPass) {
                    llAuthentication.setVisibility(View.GONE);
                    llByPassAndReSend.setVisibility(View.GONE);
                    tvMobileNoStarAdditional.setText(" ");
                    tvEmailStarAdditional.setText(" ");
                    //byPass = true; //Changed By Me For certain Condition
                } else if (VisitorCheckInActivity.locallyBypassedOnce) {
                    llAuthentication.setVisibility(View.GONE);
                    llByPassAndReSend.setVisibility(View.GONE);
                    tvMobileNoStarAdditional.setText(" ");
                    tvEmailStarAdditional.setText(" ");
                    byPass = true;
                } else {
                    final Dialog builder = new Dialog(context);
                    builder.setContentView(R.layout.login_confirmation_dialog);
                    builder.setCancelable(true);
                    builder.show();
                    initConfirmDialog(builder);
                }

                break;

        }
    }

    private void submitAfterSexualOffenderList() {
        if (isAddVisitorChecked) {

            if (isAllHide) {
                stayAndAddVisitor();
            } else {
                validateOtp(true, false);
            }
        } else {
            if (isAllHide) {
                dontStayAndSubmit();
            } else {
                validateOtp(false, true);
            }
        }
    }

    private void showAdditionalDetailDialog() {
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


        ArrayList<String> typeOfVisitor = new ArrayList<String>();
        ArrayList<String> purposeOfVisit = new ArrayList<String>();

        if (masterResponse == null) {

            masterResponse = new Gson().fromJson(new SPbean(context).getPreference(
                    Constants.MASTER_RESPONSE, ""), MasterResponse.class);

        }

        typeOfVisitor = Utilities.getStringArrayForTypeOfVisitorSpinner(context, masterResponse, typeOfVisitor);
        purposeOfVisit = Utilities.getStringArrayForPurposeOfVisitSpinner(context, masterResponse, purposeOfVisit);

        if (typeOfVisitor != null) {
            ArrayAdapter<String> typeOfVisitAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, typeOfVisitor);
            spnTypeOfVisitor.setAdapter(typeOfVisitAdapter);
        }

        if (purposeOfVisit != null) {
            ArrayAdapter<String> purposeOfVisitAdapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_spinner_item, purposeOfVisit);
            spnPurposeOfVisit.setAdapter(purposeOfVisitAdapter);
        }


        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        RecyclerView rv_questions = (RecyclerView) dialog.findViewById(R.id.rv_AddDetailQuestions);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnAddDetailConfirm);

        if (visitorList != null) {
            if (!Utils.isEmpty(visitorList.getCompany())) {
                etCompany.setText(visitorList.getCompany());
                if (visitorList.getRegisteredVisitorId() != null && visitorList.getRegisteredVisitorId() != 0) {
                    etCompany.setEnabled(false);
                }
            }
        } else {
            if (additionalDetail.getmCompany() != null && !additionalDetail.getmCompany().isEmpty()) {
                etCompany.setText(additionalDetail.getmCompany());
            }
        }

        if (visitorList != null) {
            if (visitorList.getRegisteredVisitorId() != null && visitorList.getRegisteredVisitorId() != 0) {
                etCompany.setEnabled(false);
            }
        }

        if (additionalDetail.getmAccessCardNo() != null && !additionalDetail.getmAccessCardNo().isEmpty()) {
            etBadge.setText(additionalDetail.getmAccessCardNo());
        }

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
            etDOB.setText(dlFields.getDateOfBirth() != null ? dlFields.getDateOfBirth() : "");
            etStateCode.setText(dlFields.getStateCode() != null ? dlFields.getStateCode() : "");
            etCity.setText(dlFields.getCity() != null ? dlFields.getCity() : "");
            etZipCode.setText(dlFields.getZipCode() != null ? dlFields.getZipCode() : "");
        }


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
                    visitorListForAdditonal.setCompany(etCompany.getText().toString());
                    visitorListForAdditonal.setAccessCardNo(etBadge.getText().toString());
                    visitorListForAdditonal.setBirthdate(etDOB.getText().toString());
                    visitorListForAdditonal.setStatecode(etStateCode.getText().toString());
                    visitorListForAdditonal.setCity(etCity.getText().toString());
                    visitorListForAdditonal.setZipcode(etZipCode.getText().toString());
                    visitorListForAdditonal.setIdnumber(etDLNumber.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    visitorListForAdditonal.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                            spnTypeOfVisitor.getSelectedItem().toString()));
                    visitorListForAdditonal.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                            spnPurposeOfVisit.getSelectedItem().toString()));
                    additionalDetail = data(visitorListForAdditonal);
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
                    visitorListForAdditonal.setCompany(etCompany.getText().toString());
                    visitorListForAdditonal.setAccessCardNo(etBadge.getText().toString());
                    visitorListForAdditonal.setBirthdate(etDOB.getText().toString());
                    visitorListForAdditonal.setStatecode(etStateCode.getText().toString());
                    visitorListForAdditonal.setCity(etCity.getText().toString());
                    visitorListForAdditonal.setZipcode(etZipCode.getText().toString());
                    visitorListForAdditonal.setIdnumber(etDLNumber.getText().toString());
                    dlFields.setZipCode(etZipCode.getText().toString());
                    dlFields.setCity(etCity.getText().toString());
                    dlFields.setStateCode(etStateCode.getText().toString());
                    dlFields.setDocumentNumber(etDLNumber.getText().toString());
                    dlFields.setDateOfBirth(etDOB.getText().toString());
                    visitorListForAdditonal.setTypeOfVisitorId(Utilities.GetTypeOfVisitorId(masterResponse,
                            spnTypeOfVisitor.getSelectedItem().toString()));
                    visitorListForAdditonal.setPurposeOfVisitId(Utilities.GetPurposeOfVisitId(masterResponse,
                            spnPurposeOfVisit.getSelectedItem().toString()));
                    additionalDetail = data(visitorListForAdditonal);
                    if (visitorQuestionAdapter != null) {
                        visitorQuestionArrayList = visitorQuestionAdapter.getFilledQuestions();
                    }
                    dialog.dismiss();

                }
            });
        }

        dialog.show();
    }

    public AdditionalDetail data(VisitorList visitorList) {
        AdditionalDetail data = new AdditionalDetail();
        data.setmCompany(visitorList.getCompany());
        data.setStatecode(visitorList.getStatecode());
        data.setZipcode(visitorList.getZipcode());
        data.setCity(visitorList.getCity());
        data.setIdnumber(visitorList.getIdnumber());
        data.setmAccessCardNo(visitorList.getAccessCardNo());
        if (visitorList.getTypeOfVisitorId() != null) {
            data.setmTypeOfVisitorId(visitorList.getTypeOfVisitorId());
        }
        if (visitorList.getPurposeOfVisitId() != null) {
            data.setmPurposeOfVisitId(visitorList.getPurposeOfVisitId());
        }
        return data;
    }

    public boolean isQuestionValid(ArrayList<VisitorQuestion> questions) {
        int error = 0;

        //if (questionAdapter != null) {
        try {
            //ArrayList<VisitorQuestion> questions = questionAdapter.getFilledQuestions();
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
        //}


        return !(error > 0);
    }

    public int getMandatoryQuestionsCount() {
        MasterResponse masterResponse = new Gson().fromJson(new SPbean(context).
                getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);

        int count = 0;

        ArrayList<Question> questionArrayList = new ArrayList<Question>();
        if (masterResponse.getQuestions() != null) {

            questionArrayList = masterResponse.getQuestions();

            for (Question question :
                    questionArrayList) {
                if (question.getRequired()) {
                    count++;
                }
            }
        }
        return count;
    }

    private void dontStayAndSubmit() {
        if (visitorListForedit != null) {
            bindVisitorsToArrayList(visitorListForedit, positionAt);
            visitorListForedit = null;
            byPass = false;
            positionAt = 0;
            finish();
        } else {
            bindVisitorsToArrayList(visitorList, -1);
            byPass = false;
            finish();
        }
    }

    private void stayAndAddVisitor() {
        if (visitorListForedit != null) {
            bindVisitorsToArrayList(visitorListForedit, positionAt);
            visitorListForedit = null;
            positionAt = 0;
            visitorList = new VisitorList();
            clearFields();

        } else {

            bindVisitorsToArrayList(visitorList, -1);
            visitorList = new VisitorList();
            clearFields();

        }
    }

    // resend otp method call
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
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));

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

    private boolean byPassValidation(EditText etLoginId_confirmId, EditText
            etPassword_confirmPassword) {
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
                        Utilities.hideProgress();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));

            }
        });
    }

    private void hideFields(Integer employeeId) {
        llAuthentication.setVisibility(View.GONE);
        llByPassAndReSend.setVisibility(View.GONE);
        VisitorCheckInActivity.locallyBypassedOnce = true;
        VisitorCheckInActivity.GlobalByPassedBy = employeeId;
        tvMobileNoStarAdditional.setText(" ");
        tvEmailStarAdditional.setText(" ");
        byPass = true;
    }

    private void bindVisitorsToArrayList(VisitorList visitorList, int position) {

        String mobileNo = Utilities.getReplaceText(etMobile.getText().toString().trim());
        String etIsdCode = etIsd.getText().toString();
        etIsdCode = etIsdCode.replaceAll("\\+", "");
        if (imageUploadObject != null) {
            //imageUploadObject.setMobileNo(etMobile.getText().toString().trim());
            if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getAuthenticateVstrBy().
                    equalsIgnoreCase("E")) {
                imageUploadObject.setEmailId(etEmail.getText().toString().trim());
            } else {
                imageUploadObject.setMobileNo(mobileNo);
            }
            if (visitorList != null) {
                imageUploadObject.setRegisteredVisitorId(visitorList.getRegisteredVisitorId() != null ?
                        visitorList.getRegisteredVisitorId() : 0);

                imageUploadObject.setByPassVisitorId(visitorList.getBypassedVisitorId() != null ?
                        visitorList.getBypassedVisitorId() : 0);
            }
            if(!etLastName.getText().toString().isEmpty()){
                imageUploadObject.setvName(etFirstName.getText().toString().trim() + " " + etLastName.getText().toString().trim());
            }else{
                imageUploadObject.setvName(etFirstName.getText().toString().trim());
            }
            VisitorEntryActivityOne.imageUploadObjects.add(imageUploadObject);
        }

        if (visitorList != null) {
            visitorList.setIsdCode(etIsdCode.trim());
            visitorList.setMobile(mobileNo);
            visitorList.setEmail(etEmail.getText().toString());
            visitorList.setFirstName(etFirstName.getText().toString());
            visitorList.setLastName(etLastName.getText().toString());
            visitorList.setName(etFirstName.getText().toString() + " " + etLastName.getText().toString());

            /*if (questionAdapter != null) {
                visitorList.setVisitorQuestions(questionAdapter.getFilledQuestions());
            } else if (visitorQuestionAdapter != null) {
                visitorList.setVisitorQuestions(visitorQuestionAdapter.getFilledQuestions());
            }*/

            visitorList.setAccessCardNo(visitorListForAdditonal.getAccessCardNo() != null ? visitorListForAdditonal.getAccessCardNo() : "");
            visitorList.setCompany(visitorListForAdditonal.getCompany() != null ? visitorListForAdditonal.getCompany() : "");
            visitorList.setBirthdate(visitorListForAdditonal.getBirthdate() != null ? visitorListForAdditonal.getBirthdate() : "");
            //visitorList.setCardexpirydate(visitorListForAdditonal.getCardexpirydate() != null ? visitorListForAdditonal.getCardexpirydate() : "");
            //visitorList.setCardissuedate(visitorListForAdditonal.getCardissuedate() != null ? visitorListForAdditonal.getCardissuedate() : "");
            //visitorList.setGender(visitorListForAdditonal.getGender() != null ? visitorListForAdditonal.getGender() : "");
            visitorList.setIdnumber(visitorListForAdditonal.getIdnumber() != null ? visitorListForAdditonal.getIdnumber() : "");
            visitorList.setStatecode(visitorListForAdditonal.getStatecode() != null ? visitorListForAdditonal.getStatecode() : "");
            visitorList.setCity(visitorListForAdditonal.getCity() != null ? visitorListForAdditonal.getCity() : "");
            visitorList.setZipcode(visitorListForAdditonal.getZipcode() != null ? visitorListForAdditonal.getZipcode() : "");
            visitorList.setTypeOfVisitorId(visitorListForAdditonal.getTypeOfVisitorId() != null ? visitorListForAdditonal.getTypeOfVisitorId() : null);
            visitorList.setPurposeOfVisitId(visitorListForAdditonal.getPurposeOfVisitId() != null ? visitorListForAdditonal.getPurposeOfVisitId() : null);

            try {
                visitorRequiredQuestionArrayList = new ArrayList<>();
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

            if (visitorList.getRegisteredVisitorId() != null) {
                visitorList.setRegisteredVisitorId(visitorList.getRegisteredVisitorId());
            }
            if (VisitorCheckInActivity.GlobalByPass || byPass) {
                visitorList.setIsAuthenticationByPassed(true);
                visitorList.setIsAuthenticationByPassedBy(VisitorCheckInActivity.GlobalByPassedBy);
                visitorList.setRequireAuthentication(false);
                if (ll_AdditionalAuthenticateKey.getVisibility() == View.VISIBLE && btnResendKey.getVisibility() == View.VISIBLE) {
                    visitorList.setAuthenticationKey(etAuthenticationKey.getText().toString().trim());
                }
            } else {
                visitorList.setIsAuthenticationByPassed(false);
                //visitorList.setIsAuthenticationByPassedBy(PlaceSelectionActivity.GlobalByPassedBy);
                if (ll_AdditionalAuthenticateKey.getVisibility() == View.VISIBLE && btnResendKey.getVisibility() == View.VISIBLE) {
                    visitorList.setAuthenticationKey(etAuthenticationKey.getText().toString().trim());
                }
            }

        } else {

            visitorList = new VisitorList();
            visitorList.setIsdCode(etIsdCode.trim());
            visitorList.setMobile(mobileNo);
            visitorList.setEmail(etEmail.getText().toString());
            visitorList.setFirstName(etFirstName.getText().toString());
            visitorList.setLastName(etLastName.getText().toString());
            visitorList.setName(etFirstName.getText().toString() + " " + etLastName.getText().toString());

            /*if (questionAdapter != null) {
                visitorList.setVisitorQuestions(questionAdapter.getFilledQuestions());
            } else if (visitorQuestionAdapter != null) {
                visitorList.setVisitorQuestions(visitorQuestionAdapter.getFilledQuestions());
            }*/
            visitorList.setAccessCardNo(visitorListForAdditonal.getAccessCardNo() != null ? visitorListForAdditonal.getAccessCardNo() : "");
            visitorList.setCompany(visitorListForAdditonal.getCompany() != null ? visitorListForAdditonal.getCompany() : "");
            visitorList.setBirthdate(visitorListForAdditonal.getBirthdate() != null ? visitorListForAdditonal.getBirthdate() : "");
            //visitorList.setCardexpirydate(visitorListForAdditonal.getCardexpirydate() != null ? visitorListForAdditonal.getCardexpirydate() : "");
            //visitorList.setCardissuedate(visitorListForAdditonal.getCardissuedate() != null ? visitorListForAdditonal.getCardissuedate() : "");
            // visitorList.setGender(visitorListForAdditonal.getGender() != null ? visitorListForAdditonal.getGender() : "");
            visitorList.setIdnumber(visitorListForAdditonal.getIdnumber() != null ? visitorListForAdditonal.getIdnumber() : "");
            visitorList.setStatecode(visitorListForAdditonal.getStatecode() != null ? visitorListForAdditonal.getStatecode() : "");
            visitorList.setCity(visitorListForAdditonal.getCity() != null ? visitorListForAdditonal.getCity() : "");
            visitorList.setZipcode(visitorListForAdditonal.getZipcode() != null ? visitorListForAdditonal.getZipcode() : "");
            visitorList.setTypeOfVisitorId(visitorListForAdditonal.getTypeOfVisitorId() != null ? visitorListForAdditonal.getTypeOfVisitorId() : null);
            visitorList.setPurposeOfVisitId(visitorListForAdditonal.getPurposeOfVisitId() != null ? visitorListForAdditonal.getPurposeOfVisitId() : null);


            try {
                visitorRequiredQuestionArrayList = new ArrayList<>();
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

            if (visitorList.getRegisteredVisitorId() != null) {
                visitorList.setRegisteredVisitorId(visitorList.getRegisteredVisitorId());
            }
            if (VisitorCheckInActivity.GlobalByPass || byPass) {
                visitorList.setIsAuthenticationByPassed(true);
                visitorList.setIsAuthenticationByPassedBy(VisitorCheckInActivity.GlobalByPassedBy);
                visitorList.setRequireAuthentication(false);
                if (ll_AdditionalAuthenticateKey.getVisibility() == View.VISIBLE && btnResendKey.getVisibility() == View.VISIBLE) {
                    visitorList.setAuthenticationKey(etAuthenticationKey.getText().toString().trim());
                }
            } else {
                visitorList.setIsAuthenticationByPassed(false);
                visitorList.setRequireAuthentication(true);
                if (ll_AdditionalAuthenticateKey.getVisibility() == View.VISIBLE && btnResendKey.getVisibility() == View.VISIBLE) {
                    visitorList.setAuthenticationKey(etAuthenticationKey.getText().toString().trim());
                }
            }
        }

        if (position == -1) {
            visitorListsArrayList.add(visitorList);
        } else {
            visitorListsArrayList.set(position, visitorList);
        }

        imageUploadObject = null;
    }

    public boolean isValid() {
        int error = 0;
        boolean needToValidate = true;
        String mobileNo = Utilities.getReplaceText(etMobile.getText().toString().trim());


        if (VisitorCheckInActivity.GlobalByPass || byPass) {
            needToValidate = false;
        }

        if (needToValidate) {

            if (CommonPlaceForOjects.settings != null) {

                if (CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("E")) {
                    if (etEmail.getText().toString().isEmpty()) {
                        etEmail.setError("Email Id Is Required");
                        etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));

                        if (error == 0) {
                            etEmail.requestFocus();
                            error++;
                        }
                    }

                    if (!Utilities.isValidEmail(etEmail.getText().toString().trim())) {
                        etEmail.setError(getResources().getString(R.string.invalid_email));
                        etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                        if (error == 0) {
                            etEmail.requestFocus();
                            error++;
                        }
                    }/*else {
                        if (visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0) {
                            etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
                        }
                    }*/
                } else {

                    if (!Utilities.isValidCountryCode(etIsd.getText().toString())) {
                        etIsd.setError(getString(R.string.error_isd_invalid));
                        llAdditionalFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
                        if (error == 0) {
                            error++;
                        }
                    }/*else{
                        if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                            llAdditionalFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_valid_for_isd));
                        }
                    }*/


                    if (etIsd.getText().toString().isEmpty()) {
                        etIsd.setError(getString(R.string.error_isd_required));
                        llAdditionalFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
                        if (error == 0) {
                            etIsd.requestFocus();
                            error++;
                        }
                    }

                    if (!Utilities.isValidMobile(mobileNo.trim())) {
                        etMobile.setError(getString(R.string.mobileInValid));
                        etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                        if (error == 0) {
                            etMobile.requestFocus();
                            error++;
                        }
                    }/*else{
                        if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                            etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
                        }
                    }*/

                    if (etIsd.getText().toString().equals("+91") && mobileNo.length() != 10) {
                        etMobile.setError(getString(R.string.ten_digits_required));
                        etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                        if (error == 0) {
                            etMobile.requestFocus();
                            error++;
                        }
                    }/*else{
                        if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                            etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
                        }
                    }*/

                }
            }
        }

        if (CommonPlaceForOjects.settings != null && CommonPlaceForOjects.settings.getVisitorCapturePhotoAllowed()) {
            if (CommonPlaceForOjects.settings.getVisitorCapturePhotoRequired()) {
                if (tvTakenPhoto.getText().toString().equalsIgnoreCase(getResources().getString(R.string.live_picture))) {
                    Toast.makeText(context, getString(R.string.live_picture), Toast.LENGTH_SHORT).show();
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
                etFirstName.requestFocus();
                error++;
            }
        }/*else{
            if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
                etFirstName.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
            }
        }
*/

        /*if(visitorList.getRegisteredVisitorId() == null || visitorList.getRegisteredVisitorId() == 0 ) {
            if (!etLastName.getText().toString().isEmpty()) {
                etLastName.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
            }
        }*/

        /*if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(getString(R.string.error_last_name));
            etLastName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                etLastName.requestFocus();
                error++;
            }
        }*/


        if (!mobileNo.isEmpty()) {
            if (!Utilities.isValidIsdCodeAndMobileNo(etIsd.getText().toString(), mobileNo)) {
                etMobile.setError(getResources().getString(R.string.invalidMobile));
                etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                if (error == 0) {
                    error++;
                    etMobile.requestFocus();
                }
            }
        }


        if (!mobileNo.isEmpty() &&
                !etIsd.getText().toString().equalsIgnoreCase("+91") &&
                !Utilities.isValidMobile(mobileNo)) {
            etMobile.setError(getResources().getString(R.string.invalidMobile));
            etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etMobile.requestFocus();

            }
        }


        if (!etEmail.getText().toString().isEmpty()) {

//            String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            if (!Utilities.isValidEmail(etEmail.getText().toString().trim())) {
                etEmail.setError(getString(R.string.invalid_email));
                etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));

                if (error == 0) {
                    etEmail.requestFocus();
                    error++;
                }
            }
        }


        if (llAuthentication.getVisibility() == View.VISIBLE && etAuthenticationKey.getText().toString().trim().isEmpty()) {
            etAuthenticationKey.setError(getResources().getString(R.string.error_key_required));
            etAuthenticationKey.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                etAuthenticationKey.requestFocus();
                error++;
            }
        }/* else {
            etAuthenticationKey.setBackground(getResources().getDrawable(R.drawable.edittext_valid));
        }*/

        final QuestionAdapter finalQuestionAdapter = questionAdapter;
        if (finalQuestionAdapter != null) {
            if (!isQuestionValid(finalQuestionAdapter.getFilledQuestions())) {
                Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                if (error == 0) {
                    error++;
                }
            }
        }

            /*if (getMandatoryQuestionsCount() > 0) {
                if (!isMandatoryQuestionsAnswered()) {
                    Toast.makeText(context, getString(R.string.fill_mandatory_questions), Toast.LENGTH_SHORT).show();
                    if (error == 0) {
                        error++;
                    }
                }
            }*/

        /*if (questionAdapter != null) {
            try {
                ArrayList<VisitorQuestion> questions = questionAdapter.getFilledQuestions();
                for (VisitorQuestion question : questions) {

                    if (question.isRequired() && question.getAnswer() == null) {

                        error++;
                        Toast.makeText(context, getString(R.string.error_questions), Toast.LENGTH_SHORT).show();
                        break;

                    } else if (question.isRequired() && question.getAnswer() != null && question.getAnswer().isEmpty()) {

                        error++;
                        Toast.makeText(context, getString(R.string.error_questions), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        if (positionAt < 1) {

            if (CommonPlaceForOjects.settings != null &&
                    CommonPlaceForOjects.settings.getAuthenticateVstrBy().equalsIgnoreCase("E")) {
                for (int i = 0; i < visitorListsArrayList.size(); i++) {
                    if (etEmail.getText().toString() != null && !etEmail.getText().toString().isEmpty()) {
                        if (visitorListsArrayList.get(i).getEmail().equalsIgnoreCase(etEmail.getText().toString())) {
                            etEmail.setError(getResources().getString(R.string.emailAlreadyRegister));
                            etEmail.setBackground(getResources().getDrawable(R.drawable.edittext_error));

                            if (error == 0) {
                                etEmail.requestFocus();
                                error++;
                            }
                        }
                    }
                }
            } else {
                for (int i = 0; i < visitorListsArrayList.size(); i++) {
                    dataIsdMobile = visitorListsArrayList.get(i).getIsdCode() + visitorListsArrayList.get(i).getMobile();
                    if (!mobileNo.isEmpty()) {
                        if (dataToSearch.equals(dataIsdMobile)) {
                            etMobile.setError(getResources().getString(R.string.mobileAlreadyRegister));
                            etMobile.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                            if (error == 0) {
                                etMobile.requestFocus();
                                error++;
                            }
                        }
                    }
                }
            }
        }


        return !(error > 0);
    }

    private void validateOtp(final boolean stayAndAdd, final boolean addAndSupmit) {
        String mobileNo = Utilities.getReplaceText(etMobile.getText().toString().trim());
        Utilities.showprogressDialogue("Validating OTP", getString(R.string.please_wait), context, true);
        HashMap<String, Object> map = new HashMap<>();
        map.put("isdCode", isdSearch.trim());
        map.put("mobileNo", mobileNo.trim());
        map.put("emailid", etEmail.getText().toString().trim());
        map.put("authenticationKey", etAuthenticationKey.getText().toString().trim());
        map.put("requestClientDetails", Utilities.requestclientDetails(context));
        Call<ResponseBody> call = ApiUtils.getAPIService().callValidateOtp(Utilities.getToken(context),
                map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {

                    Utilities.hideProgress();
                    if (stayAndAdd) {
                        stayAndAddVisitor();
                    } else if (addAndSupmit) {
                        dontStayAndSubmit();
                    }

                } else {

                    Utilities.hideProgress();
                    Utilities.showPopup(context, "", "Invalid Authentication Key");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Utilities.hideProgress();
                Utilities.showPopup(context, "", getResources().getString(R.string.no_internet_msg_text));

            }
        });

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
        Intent i = new Intent(AdditionalVisitorActivity.this,CaptureActivity.class);
        i.putExtra(AppConfig.BUNDLE_IS_FROM_ADDITIONAL,true);
        startActivityForResult(i,CAPTURE_ID);
        //startActivityForResult(new Intent(AdditionalVisitorActivity.this, CaptureActivity.class), CAPTURE_ID);
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
                        Bitmap tempBitMap = Utilities.modifyOrientation(bitmap, fileUri.getPath(),false);

                        Uri uri = null;
                        // make a copy of file
                        if (tempBitMap != null) {
                            uri = makePhotoCopy(tempBitMap);
                        }

                        if (fileUri != null) {
                            if (new File(fileUri.getPath()).exists()) {
                                new File(fileUri.getPath()).delete();
                            }
                        }


                        profilePic.setImageBitmap(tempBitMap);
                        tvTakenPhoto.setText(getResources().getString(R.string.reset_forimage));
                        Log.d("TAG", "Image policy = " + fileUri.getPath());
                        imageUploadObject = new ImageUploadObject();
                        imageUploadObject.setUrl(uri.getPath());
                        imageUploadObject.setCapture(true);

                        File file = new File(uri.getPath());
                        Log.d("TAG", "Image size = " + Integer.parseInt(String.valueOf(file.length() / 1024)));


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

    @Override
    protected void onResume() {
        super.onResume();

        if (CommonPlaceForOjects.settings == null) {
            Utilities.bindSettings(context);
        }
        clickedOnce = false;
    }

    public void clearFieldsforEmailNumber(final boolean isFromEmail) {

        try {

            if (CommonPlaceForOjects.settings != null &&
                    CommonPlaceForOjects.settings.getDefaultIsdCode() != null &&
                    !CommonPlaceForOjects.settings.getDefaultIsdCode().isEmpty()) {
                etIsd.setText("+" + isdSearch.trim());
            } else {
                etIsd.setText("+91");
            }
            etIsd.setEnabled(true);
            etFirstName.setText("");
            etFirstName.setEnabled(true);
            if (!isFromEmail) {
                etEmail.setText("");
                etEmail.setEnabled(true);
            } else {
                etMobile.setText("");
            }
            etAuthenticationKey.setText("");
            etAuthenticationKey.setEnabled(true);
            cbAddMore.setChecked(false);
            serverIdPtoofNo = "";
            byPass = false;
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
            cvRegisteredImage.setImageDrawable(getResources().getDrawable(R.drawable.capture_with_blue_background));
            ll_registeredPhoto.setVisibility(View.GONE);
            //llAuthentication.setVisibility(View.GONE);
            //bindQuestions();

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

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

    private void CallSexualOffender(String etName) {
        if (AppConfig.IsSexualOffender) {


            /*String Fname = dlFields.getFirstName() != null ? dlFields.getFirstName() : "";
            String Lname = dlFields.getLastName() != null ? dlFields.getLastName() : "";
            String name = Fname.trim() + "" + Lname.trim();
            String vName = !name.isEmpty() ? name : etName;*/
            String stateCode = dlFields.getStateCode() != null ? dlFields.getStateCode() : "";
            String city = dlFields.getCity() != null ? dlFields.getCity() : "";
            String zipCode = dlFields.getZipCode() != null ? dlFields.getZipCode() : "";
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

                                showOffenderList(sexualOffendedList);

                            } else {
                                submitAfterSexualOffenderList();
                                tvAdditionalSexualOffendMatch.setVisibility(View.GONE);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Utilities.hideProgress();
                        tvAdditionalSexualOffendMatch.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Utilities.hideProgress();
                    tvAdditionalSexualOffendMatch.setVisibility(View.GONE);
                }
            });
        }
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

                profilePic.setImageBitmap(image);
                tvTakenPhoto.setText(getResources().getString(R.string.reset_forimage));
                //Log.d("TAG", "Image policy = " + fileUri.getPath());
                imageUploadObject = new ImageUploadObject();
                imageUploadObject.setUrl(uri.getPath());
                imageUploadObject.setCapture(true);
                isFrontCamera = LoginActivity.isSelfeHeplKiosk;
            } else {
                Utilities.showPopup(AdditionalVisitorActivity.this, "", "No Image Found");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Utilities.writeLog(AppConfig.LOG_DIR, AppConfig.LOG_FILE,
                    "Line no:" + e.getStackTrace()[0].getLineNumber() + Utilities.getCurrentDateTime() + ":\n" + getLocalClassName());
        }
    }
    private void showOffenderList(SexualOffendedList sexualOffendedList) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setView(getSexualList(sexualOffendedList));
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

    private View getSexualList(SexualOffendedList sexualOffendedList) {

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

        tvAdditionalSexualOffendMatch.setVisibility(View.VISIBLE);

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
                                    tvAdditionalSexualOffendMatch.setVisibility(View.GONE);
                                    submitAfterSexualOffenderList();

                                }
                            });

                    alertDialog.setNegativeButton("CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    tvAdditionalSexualOffendMatch.setVisibility(View.GONE);

                                }
                            });

                    alertDialog.show();

                }
            }
        });

        return view;
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
                AdditionalVisitorActivity.etFirstName.setText(dlData.getFirstName() != null ? dlData.getFirstName() : " ");
                AdditionalVisitorActivity.etFirstName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                AdditionalVisitorActivity.etFirstName.setEnabled(true);
                AdditionalVisitorActivity.etLastName.setText(dlData.getLastName() != null ? dlData.getLastName() : " ");
                AdditionalVisitorActivity.etLastName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                AdditionalVisitorActivity.etLastName.setEnabled(true);
                dialog.dismiss();
            }
        });


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

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

//    public void showDrivingLicenseDialog(final DLFields dlData) {
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialogue_drivinglicence_details);
//        ImageView iv_cancel = (ImageView) dialog.findViewById(R.id.ivDrivingLicenseCancel);
//        final EditText etFirstname = (EditText) dialog.findViewById(R.id.etDrivingLicenseFirstName);
//        final EditText etLastname = (EditText) dialog.findViewById(R.id.etDrivingLicenseLastName);
//        final EditText etDLNumber = (EditText) dialog.findViewById(R.id.etDrivingLicenseDLNumber);
//        final EditText etDOB = (EditText) dialog.findViewById(R.id.etDrivingLicenseDOB);
//        final EditText etStateCode = (EditText) dialog.findViewById(R.id.etDrivingLicenseState);
//        final EditText etCity = (EditText) dialog.findViewById(R.id.etDrivingLicenseNationality);
//        final EditText etExpiryDate = (EditText) dialog.findViewById(R.id.etDrivingLicenseExpiryDate);
//        final EditText etIssueDate = (EditText) dialog.findViewById(R.id.etDrivingLicenseIssueDate);
//        final EditText etGender = (EditText) dialog.findViewById(R.id.etDrivingLicenseGender);
//        final EditText etZipcode = (EditText) dialog.findViewById(R.id.etDrivingLicenseCountry);
//        Button btnConfirm = (Button) dialog.findViewById(R.id.btnDrivingLicenseConfirm);
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dlData.setFirstName(etFirstname.getText().toString());
//                dlData.setLastName(etLastname.getText().toString());
//                dlData.setDocumentNumber(etDLNumber.getText().toString());
//                dlData.setStateCode(etStateCode.getText().toString());
//                dlData.setCity(etCity.getText().toString());
//                dlData.setZipCode(etZipcode.getText().toString());
//                dlData.setDateOfBirth(etDOB.getText().toString());
//                dlData.setSex(etGender.getText().toString());
//                dlData.setDateOfExpiry(etExpiryDate.getText().toString());
//                dlData.setIssueDate(etIssueDate.getText().toString());
//                dlFields = dlData;
//                etFirstName.setText(dlData.getFirstName() != null ? dlData.getFirstName() : "");
//                etFirstName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//                etFirstName.setEnabled(true);
//                etLastName.setText(dlData.getLastName() != null ? dlData.getLastName() : "");
//                etLastName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
//                etLastName.setEnabled(true);
//                dialog.dismiss();
//            }
//        });
//
//        iv_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        String Address = dlData.getAddress();
//        String[] split = Address.split("\\,");
//        String city = split[1];
//        String StateCode = split[2];
//        String ZipCode = split[3];
//
//        dlData.setCity(city);
//        dlData.setStateCode(StateCode);
//        dlData.setZipCode(ZipCode);
//
//        etFirstname.setText(dlData.getFirstName());
//        etLastname.setText(dlData.getLastName());
//        etDLNumber.setText(dlData.getDocumentNumber());
//        etStateCode.setText(dlData.getStateCode());
//        etCity.setText(dlData.getCity());
//        etZipcode.setText(dlData.getZipCode());
//        etDOB.setText(dlData.getDateOfBirth());
//        if (dlData.getSex().equals("1")) {
//            etGender.setText("Male");
//            dlData.setSex(etGender.getText().toString());
//        } else if (dlData.getSex().equals("2")) {
//            etGender.setText("Female");
//            dlData.setSex(etGender.getText().toString());
//        } else {
//            etGender.setText("Not Specified");
//            dlData.setSex(etGender.getText().toString());
//        }
//        etExpiryDate.setText(dlData.getDateOfExpiry());
//        etIssueDate.setText(dlData.getIssueDate());
//
//
//        dialog.show();
//    }

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

    public void callGetAuthenticateVisitor() {
        String etmobie = Utilities.getReplaceText(etMobile.getText().toString());
        HashMap<String, Object> map = new HashMap<>();
        String etIsdCode = etIsd.getText().toString();
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
                                i.putExtra(AppConfig.BUNDLE_IS_FROM_ADDITIONAL, true);
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

}
