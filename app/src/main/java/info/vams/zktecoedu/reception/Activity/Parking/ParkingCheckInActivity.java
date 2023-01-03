package info.vams.zktecoedu.reception.Activity.Parking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import info.vams.zktecoedu.reception.Activity.BaseActivity;
import info.vams.zktecoedu.reception.Activity.LoginActivity;
import info.vams.zktecoedu.reception.Adapter.CountryFlagAdapter;
import info.vams.zktecoedu.reception.Adapter.ParkingLocationAdapter;
import info.vams.zktecoedu.reception.Adapter.SearchEmployeeAdapter;
import info.vams.zktecoedu.reception.Adapter.TenantAutoCompleteAdapter;
import info.vams.zktecoedu.reception.CustomView.InstantAutoComplete;
import info.vams.zktecoedu.reception.Interface.IEmployeeSelection;
import info.vams.zktecoedu.reception.Model.CountryForISD;
import info.vams.zktecoedu.reception.Model.LoginRequest;
import info.vams.zktecoedu.reception.Model.MasterResponse;
import info.vams.zktecoedu.reception.Model.ParkingLocationModel;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee.GetParkEmployeeReq;
import info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee.GetParkEmployeeResp;
import info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn.ParkCheckInReq;
import info.vams.zktecoedu.reception.Model.Profile;
import info.vams.zktecoedu.reception.Model.RequestClientDetails;
import info.vams.zktecoedu.reception.Model.TenantList;
import info.vams.zktecoedu.reception.Model.TypeOfVisitor;
import info.vams.zktecoedu.reception.R;
import info.vams.zktecoedu.reception.Retrofit.ApiUtils;
import info.vams.zktecoedu.reception.Retrofit.Helpers.GetEmployeeHelper;
import info.vams.zktecoedu.reception.Retrofit.Helpers.IGetResponse;
import info.vams.zktecoedu.reception.Retrofit.Helpers.MasterHelper;
import info.vams.zktecoedu.reception.Retrofit.Helpers.CompanyNameHelper;
import info.vams.zktecoedu.reception.Retrofit.Helpers.ParkingLocationHelper;
import info.vams.zktecoedu.reception.Util.AppConfig;
import info.vams.zktecoedu.reception.Util.Constants;
import info.vams.zktecoedu.reception.Util.Imageutils;
import info.vams.zktecoedu.reception.Util.SPbean;
import info.vams.zktecoedu.reception.Util.UsPhoneNumberFormatter;
import info.vams.zktecoedu.reception.Util.Utilities;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingCheckInActivity extends BaseActivity implements Imageutils.ImageAttachmentListener {

    public static boolean parkingCheckInPageLive = false;
    public static boolean isMasterBinded;
    static ArrayList<ParkingLocationModel> lstParkingLoc;
    Button btnCheckIn, btnClear, btnSearch;
    Spinner spnTypeOfVisitor;
    AutoCompleteTextView actvVisitorEntryIsd;
    static ArrayList<TenantList> tenantListArrayList;
    LinearLayout llVisitorFlagIsd;
    public MasterResponse masterResponse;
    ArrayList<TypeOfVisitor> typeOfVisitorArrayList;
    public static ParkCheckInReq parkCheckInReq;
    ParkingLocationModel selectedParkingLocation;
    CountryForISD selectedcountyISD;
    InstantAutoComplete atcParkingLoc;
    AutoCompleteTextView atcCompanyName;
    TenantList selectedCompany;
    EditText etSubLocation, etFirstName, etLastName, etvisitorEntryMobileNo, etVehicleNumber,etTokenNumber, etSearchEmployee;
    AlertDialog alertDialog;
    ImageView ivVisitorEntryIsdFlag, ivCheckOut, ivLogout, ivLogo_visitorEntryActivity;
    Context context;
    Profile profile;
    private boolean visible;
    private RadioGroup radioGroup;
    private String selectedParkedBy = "Employee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_checkin);
        init();
        populateIsdCode(this, actvVisitorEntryIsd);
        parkingCheckInPageLive = true;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        parkingCheckInPageLive = false;
    }

    void init() {
        SPbean sPbean = new SPbean(this);
        context = this;
        profile = new Gson().fromJson(new SPbean(ParkingCheckInActivity.this).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
        parkCheckInReq = new ParkCheckInReq();
        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnClear = findViewById(R.id.btnClear);
        ivLogout = findViewById(R.id.ivLogout);
        etSearchEmployee = findViewById(R.id.etSearchEmployee);
        actvVisitorEntryIsd = findViewById(R.id.actvVisitorEntryIsd);
        atcParkingLoc = findViewById(R.id.atcParkingLoc);
        atcParkingLoc.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        atcCompanyName = findViewById(R.id.actv_visitorEntrypersonToMeet);
        atcCompanyName.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        etSubLocation = findViewById(R.id.etSubLocation);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        spnTypeOfVisitor = findViewById(R.id.spnTypeOfVisitor);
        etvisitorEntryMobileNo = findViewById(R.id.etvisitorEntryMobileNo);
        ivVisitorEntryIsdFlag = findViewById(R.id.ivVisitorEntryIsdFlag);
        llVisitorFlagIsd = findViewById(R.id.llVisitorFlagIsd);
        etVehicleNumber = findViewById(R.id.etVehicleNumber);
        etTokenNumber = findViewById(R.id.etTokenNumber);
        ivCheckOut = (ImageView) findViewById(R.id.ivCheckOut);
        btnSearch = findViewById(R.id.btnSearch);
        ivLogo_visitorEntryActivity = findViewById(R.id.ivLogo_visitorEntryActivity);
        radioGroup = findViewById(R.id.radioGroup);
        Utilities.setUserLogo(this, ivLogo_visitorEntryActivity);

        actvVisitorEntryIsd.setText("+" + profile.getIsdCode());
        ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage("+" + profile.getIsdCode()));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rdEmployee:
                        selectedParkedBy = "Employee";
                        break;

                    case R.id.rdDriver:
                        selectedParkedBy = "Driver";
                        break;

                    case R.id.rdValet:
                        selectedParkedBy = "Valet";
                        break;
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSearchEmployee.getText().toString().trim().isEmpty()) {
                    GetParkEmployeeReq parkEmployeeReq = new GetParkEmployeeReq();
                    Profile profile = new Gson().fromJson(new SPbean(ParkingCheckInActivity.this).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
                    if (Utilities.isNumeric(etSearchEmployee.getText().toString())) {
                        parkEmployeeReq.setMobile(etSearchEmployee.getText().toString());
                    } else {
                        parkEmployeeReq.setVisitorName(etSearchEmployee.getText().toString());
                        parkEmployeeReq.setVehicleNumber(etSearchEmployee.getText().toString());
                    }
                    try {
                        parkEmployeeReq.setComplexId(profile.getComplexId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    parkEmployeeReq.setPageSize(100);
                    parkEmployeeReq.setWildCardValue(etSearchEmployee.getText().toString());
                    parkEmployeeReq.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(ParkingCheckInActivity.this));
                    GetEmployeeHelper.callHelper(ParkingCheckInActivity.this, parkEmployeeReq, new IGetResponse() {
                        @Override
                        public void isSuccess(boolean val, Object obj) {
                            ArrayList<GetParkEmployeeResp> lstParkEmployee = (ArrayList<GetParkEmployeeResp>) obj;
                            if (!lstParkEmployee.isEmpty()) {
                                Utilities.hideKeyboard(ParkingCheckInActivity.this, etSearchEmployee);
                                showEmployeeListDialog((ArrayList<GetParkEmployeeResp>) obj);
                            } else {
                                Utilities.showPopup(ParkingCheckInActivity.this, "Search Result", "No Data Found..!");
                            }
                        }
                    });
                } else {

                }

            }
        });

        ivCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParkingCheckInActivity.this,
                        ParkingCheckOutActivity.class));
            }
        });


        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog builder = new Dialog(context);
                builder.setContentView(R.layout.login_confirmation_dialog);
                builder.setCancelable(true);
                builder.show();
                initConfirmDialog(builder);
            }
        });

        etSearchEmployee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSearch.setVisibility(etSearchEmployee.getText().toString().trim().isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearAllFields();
            }
        });


        btnCheckIn = findViewById(R.id.btnCheckIn);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (isValid()) {
                        int typeOfId = masterResponse.getTypeOfVisitors().get(spnTypeOfVisitor.getSelectedItemPosition() - 1).getTypeOfVisitorId();
                        String typeOfVstr = masterResponse.getTypeOfVisitors().get(spnTypeOfVisitor.getSelectedItemPosition() - 1).getVisitorType();
                        parkCheckInReq.setParkedBy(selectedParkedBy);
                        parkCheckInReq.setComplexId(profile.getComplexId());
                        parkCheckInReq.setCheckedInById(profile.getEmployeeId());
                        parkCheckInReq.setTenantId(selectedCompany.getTenantId());
                        parkCheckInReq.setParkingLocationId(selectedParkingLocation.getParkingLocationId());
                        parkCheckInReq.setSubLocation(etSubLocation.getText().toString().trim());
                        parkCheckInReq.setFirstName(etFirstName.getText().toString().trim());
                        parkCheckInReq.setLastName(etLastName.getText().toString().trim());
                        parkCheckInReq.setIsdCode(selectedcountyISD == null ? actvVisitorEntryIsd.getText().toString() : selectedcountyISD.getDialCode());
                        parkCheckInReq.setMobile(etvisitorEntryMobileNo.getText().toString().replaceAll("[^0-9]", ""));
                        parkCheckInReq.setVehicleNumber(etVehicleNumber.getText().toString().trim());
                        parkCheckInReq.setTokenNumber(etTokenNumber.getText().toString().trim());
                        parkCheckInReq.setTypeOfVisitorId(typeOfId);
                        parkCheckInReq.setCheckedInAtDeviceId(Utilities.getUDIDNumber(ParkingCheckInActivity.this));
                        parkCheckInReq.setTypeOfVisitor(typeOfVstr);
                        String currentDate = AppConfig.SERVER_DATE_TIME_ZONE_FORMAT.format(new Date());
                        parkCheckInReq.setCheckInTimeLocal(currentDate);
                        parkCheckInReq.setCheckedInAtFcmId(Utilities.getFcmId());
                        parkCheckInReq.setPhoto(new ArrayList<String>());
                        parkCheckInReq.setRequestClientDetails((RequestClientDetails) Utilities.requestclientDetails(ParkingCheckInActivity.this));
                        Toast.makeText(ParkingCheckInActivity.this, "Parking Info Captured.", Toast.LENGTH_LONG).show();
//                        System.out.println(parkCheckInReq.toString());
                        startActivity(new Intent(ParkingCheckInActivity.this, ParkingPhotoActivity.class));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        actvVisitorEntryIsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage(s.toString().trim()));
                }

                if (actvVisitorEntryIsd.getText().toString().trim().equalsIgnoreCase("+91") ||
                        actvVisitorEntryIsd.getText().toString().trim().equalsIgnoreCase("+1")) {
                    etvisitorEntryMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
                } else {
                    etvisitorEntryMobileNo.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    actvVisitorEntryIsd.append("+");
                }
            }
        });

        Utilities.addTextChangeListener(this, etFirstName);
        Utilities.addTextChangeListener(this, etLastName);
        Utilities.addTextChangeListener(this, etvisitorEntryMobileNo);
        Utilities.addTextChangeListener(this, etVehicleNumber);
        Utilities.addTextChangeListener(this, etTokenNumber);
        Utilities.addTextChangeListener(this, atcParkingLoc);
        Utilities.addTextChangeListener(this, atcCompanyName);

        UsPhoneNumberFormatter addLineNumberFormatter = new UsPhoneNumberFormatter(
                new WeakReference<EditText>(etvisitorEntryMobileNo));
        etvisitorEntryMobileNo.addTextChangedListener(addLineNumberFormatter);
        Utilities.addTextChangeListenerForIsd(this, etvisitorEntryMobileNo, actvVisitorEntryIsd);
        if (!isMasterBinded) {
            Utilities.showprogressDialogue(getString(R.string.fetching_data), getString(R.string.please_wait), this, true);
            MasterHelper.callMaster(this, new IGetResponse() {
                @Override
                public void isSuccess(boolean val, Object o) {
                    bindTypeOfVisitors(spnTypeOfVisitor);
                    CompanyNameHelper.callHelper(ParkingCheckInActivity.this, new IGetResponse() {
                        @Override
                        public void isSuccess(boolean val, Object obj) {
                            if (obj != null && obj instanceof ArrayList) {
                                tenantListArrayList = (ArrayList<TenantList>) obj;
                                TenantList tenantList =new TenantList();
                                Profile profile = new Profile();
                                try {
                                    profile = new Gson().fromJson(new SPbean(context).getPreference(Constants.PROFILE_RESPONSE, ""), Profile.class);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                                tenantList.setName(profile.getComplexName());
                                tenantListArrayList.add(0,tenantList);
                                bindCompanyNameAutoCompleteText(tenantListArrayList);
                            } else {
                                Utilities.showPopup(ParkingCheckInActivity.this, "", "Unable to bind Company name");
                            }
                            ParkingLocationHelper.callMaster(ParkingCheckInActivity.this, new IGetResponse() {
                                @Override
                                public void isSuccess(boolean val, Object obj) {
                                    if (obj != null && obj instanceof ArrayList) {
                                        lstParkingLoc = (ArrayList<ParkingLocationModel>) obj;
                                        bindParkingLocationAutoComplete(lstParkingLoc);
                                    } else {
                                        Utilities.showPopup(ParkingCheckInActivity.this, "", "Unable to bind Parking Location");
                                    }
                                    Utilities.hideProgress();
                                    isMasterBinded = true;
                                }
                            });
                        }
                    });
                }
            });
        } else {
            bindTypeOfVisitors(spnTypeOfVisitor);
            bindCompanyNameAutoCompleteText(tenantListArrayList);
            bindParkingLocationAutoComplete(lstParkingLoc);
        }
    }

    private void clearAllFields() {
        etSearchEmployee.setText("");
        atcParkingLoc.setText("");
        etSubLocation.setText("");
        atcCompanyName.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        actvVisitorEntryIsd.setText("+" + profile.getIsdCode());
        etvisitorEntryMobileNo.setText("");
        etVehicleNumber.setText("");
        etTokenNumber.setText("");
        ivVisitorEntryIsdFlag.setImageResource(Utilities.setDrawableFlage("+" + profile.getIsdCode()));
        spnTypeOfVisitor.setSelection(0);
        atcParkingLoc.setError(null);
        etSubLocation.setError(null);
        atcCompanyName.setError(null);
        etFirstName.setError(null);
        etLastName.setError(null);
        actvVisitorEntryIsd.setError(null);
        etvisitorEntryMobileNo.setError(null);
        etVehicleNumber.setError(null);
        selectedCompany = new TenantList();
    }


    private void bindCompanyNameAutoCompleteText(final ArrayList<TenantList> lstComapanyNames) {
        if (lstComapanyNames != null) {
            TenantAutoCompleteAdapter tenantAutoCompleteAdapter = new TenantAutoCompleteAdapter(this,
                    R.layout.activity_visitor_entry_one, R.id.lbl_name, lstComapanyNames);
            atcCompanyName.setThreshold(1);
            atcCompanyName.setAdapter(tenantAutoCompleteAdapter);
            atcCompanyName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        selectedCompany = (TenantList) view.getTag();
                        System.out.println("atcCheck: selectedCompany : " + selectedCompany.getName() + " " + selectedCompany.getTenantId());
                        atcCompanyName.setError(null);
                    }
                }
            });


        }
    }

    private void bindParkingLocationAutoComplete(final ArrayList<ParkingLocationModel> lstParkingLoc) {
        if (lstParkingLoc != null) {
            ParkingLocationAdapter tenantAutoCompleteAdapter = new ParkingLocationAdapter(this,
                    R.layout.activity_visitor_entry_one, R.id.lbl_name, lstParkingLoc);
            atcParkingLoc.setThreshold(0);
            atcParkingLoc.setAdapter(tenantAutoCompleteAdapter);
            atcParkingLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view != null) {
                        selectedParkingLocation = (ParkingLocationModel) view.getTag();
                        atcParkingLoc.setError(null);
                        System.out.println("atcCheck: selectedParkingLocation : " + selectedParkingLocation.getName() + " " + selectedParkingLocation.getParkingLocationId());

                    }
                }
            });

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
                        selectedcountyISD = (CountryForISD) view.getTag();
                        if (selectedcountyISD.getFlag() != -1) {
                            ivVisitorEntryIsdFlag.setImageResource(selectedcountyISD.getFlag());

                        }
                    }
                }
            });

        }
    }


    @Override
    public void selectFileButtonOnClick() {

    }

    @Override
    public void printButtonOnClick() {

    }

    @Override
    public void image_attachment(int from, File fileloaction, String filename, Bitmap file, Uri uri) {

    }


    private boolean isValid() {
        int error = 0;
        String etMobile = Utilities.getReplaceText(etvisitorEntryMobileNo.getText().toString());


        boolean isValidParking = false;
        for (ParkingLocationModel parkingLocationModel : lstParkingLoc) {
            if (parkingLocationModel.getName().equals(atcParkingLoc.getText().toString())) {
                isValidParking = true;
                break;
            }
        }
        if (atcParkingLoc.getText().toString().trim().isEmpty()) {
            atcParkingLoc.setError(getString(R.string.error_parking_mandatory));
            atcParkingLoc.setBackground(getResources().getDrawable(R.drawable.edittext_error));

            if (error == 0) {
                error++;
                atcParkingLoc.requestFocus();
            }
        }


        if (!isValidParking && !atcParkingLoc.getText().toString().trim().isEmpty()) {
            atcParkingLoc.setError(getString(R.string.error_parking_invalid));
            atcParkingLoc.setBackground(getResources().getDrawable(R.drawable.edittext_error));

            if (error == 0) {
                error++;
                atcParkingLoc.requestFocus();
            }
        }

        boolean isValidtenant = false;
        for (TenantList tenantArray : tenantListArrayList) {
            if (tenantArray.getName().equals(atcCompanyName.getText().toString())) {
                isValidtenant = true;
                break;
            }
        }

        if (atcCompanyName.getText().toString().trim().isEmpty()) {
            atcCompanyName.setError(getString(R.string.error_company_mandatory));
            atcCompanyName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                atcCompanyName.requestFocus();
            }
        }

        if (!isValidtenant && !atcCompanyName.getText().toString().trim().isEmpty()) {
            atcCompanyName.setError(getString(R.string.error_company_invalid));
            atcCompanyName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                atcCompanyName.requestFocus();
            }
        }


        if (etFirstName.getText().toString().isEmpty()) {
            etFirstName.setError(getString(R.string.error_visitor_name_mandatory));
            etFirstName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etFirstName.requestFocus();
            }
        }

        if (etLastName.getText().toString().isEmpty()) {
            etLastName.setError(getString(R.string.error_last_name_mandatory));
            etLastName.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etLastName.requestFocus();
            }
        }

        if (etMobile.isEmpty()) {
            etvisitorEntryMobileNo.setError(getResources().getString(R.string.invalidMobile_mandatory));
            etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etvisitorEntryMobileNo.requestFocus();
            }
        } else if (!etMobile.isEmpty()
                && !Utilities.isValidMobile(etMobile.trim())) {
            etvisitorEntryMobileNo.setError(this.getResources().getString(R.string.error_mobile));
            etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                etvisitorEntryMobileNo.requestFocus();
                error++;
            }
        } else if (!etMobile.isEmpty()) {
            if (!Utilities.isValidIsdCodeAndMobileNo(actvVisitorEntryIsd.getText().toString(), etMobile)) {
                etvisitorEntryMobileNo.setError(getResources().getString(R.string.error_mobile));
                etvisitorEntryMobileNo.setBackground(getResources().getDrawable(R.drawable.edittext_error));
                if (error == 0) {
                    error++;
                    etvisitorEntryMobileNo.requestFocus();
                }
            }
        }

        if (actvVisitorEntryIsd.getText().toString().isEmpty()) {
            actvVisitorEntryIsd.setError(getString(R.string.error_isd_invalid_mandatory));
            llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
            if (error == 0) {
                error++;
                actvVisitorEntryIsd.requestFocus();
            }
        }

        if (!actvVisitorEntryIsd.getText().toString().isEmpty() &&
                !Utilities.isValidCountryCode(actvVisitorEntryIsd.getText().toString())) {
            actvVisitorEntryIsd.setError(getString(R.string.error_invalid_isd_invalid_mandatory));
            llVisitorFlagIsd.setBackground(getResources().getDrawable(R.drawable.edittext_error_for_isd));
            if (error == 0) {
                error++;
                actvVisitorEntryIsd.requestFocus();
            }
        }


//        }

        if (etVehicleNumber.getText().toString().isEmpty()) {
            etVehicleNumber.setError(getString(R.string.error_vehicle_no_required));
            etVehicleNumber.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etVehicleNumber.requestFocus();
            }
        }

        //
        if (etTokenNumber.getText().toString().isEmpty()) {
            etTokenNumber.setError(getString(R.string.error_token_no_required));
            etTokenNumber.setBackground(getResources().getDrawable(R.drawable.edittext_error));
            if (error == 0) {
                error++;
                etTokenNumber.requestFocus();
            }
        }
        if (spnTypeOfVisitor.getSelectedItemPosition() == 0) {
            if (error == 0) {
                spnTypeOfVisitor.setBackground(getResources().getDrawable(R.drawable.edittext_error));

                Utilities.showPopup(ParkingCheckInActivity.this, "Field Selection Required", "Type Of Visitor Is Mandatory");
                error++;
            }
        }
        return !(error > 0);
    }


    private void bindTypeOfVisitors(Spinner spinner) {
        ArrayList<String> typeOfVisitor = new ArrayList<String>();
        if (masterResponse == null) {
            masterResponse = new Gson().fromJson(new SPbean(this).getPreference(Constants.MASTER_RESPONSE, ""), MasterResponse.class);
            if (masterResponse.getTypeOfVisitors() != null) {
                typeOfVisitorArrayList = masterResponse.getTypeOfVisitors();
                typeOfVisitor.add("Select");
                for (int i = 0; i < typeOfVisitorArrayList.size(); i++) {
                    typeOfVisitor.add(typeOfVisitorArrayList.get(i).getVisitorType());
                }
                ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                        R.layout.spinner_display_text, typeOfVisitor);
                spinner.setAdapter(stringArrayAdapter);
                spinner.setSelection(0);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (view != null) {
                            spnTypeOfVisitor.setBackground(getResources().getDrawable(R.drawable.gradient_spinner));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else {
                Utilities.showPopup(this, "", "Type of visitor data is not avalibale");
            }
        }

    }


    private void showEmployeeListDialog(ArrayList<GetParkEmployeeResp> lstGetParkEmployeeResp) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(ParkingCheckInActivity.this);
        builder1.setView(getEmployeeList(this, lstGetParkEmployeeResp));
        alertDialog = builder1.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private View getEmployeeList(final Context context, ArrayList<GetParkEmployeeResp> lstGetParkEmployeeResp) {
        RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        ImageView ivCancel;
        View view = LayoutInflater.from(context).inflate(R.layout.dialogue_search_employee, null, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvEmployees);
        ivCancel = (ImageView) view.findViewById(R.id.ivCancel);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SearchEmployeeAdapter searchEmployeeAdaptor = new SearchEmployeeAdapter(context, lstGetParkEmployeeResp, new IEmployeeSelection() {
            @Override
            public void onClicked(GetParkEmployeeResp getEployeeResp) {
                bindexistingEmployeeData(getEployeeResp);
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        recyclerView.setAdapter(searchEmployeeAdaptor);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });

        return view;
    }


    void bindexistingEmployeeData(GetParkEmployeeResp getEmployeeResp) {
        clearAllFields();
        etFirstName.setText(getEmployeeResp.getFirstName());
        etLastName.setText(getEmployeeResp.getLastName());
        String isd = (getEmployeeResp.getIsdCode() == null || getEmployeeResp.getIsdCode().trim().isEmpty()) ? "91" : getEmployeeResp.getIsdCode();
        actvVisitorEntryIsd.setText("+" + isd);
        etvisitorEntryMobileNo.setText(getEmployeeResp.getMobile());
        etVehicleNumber.setText(getEmployeeResp.getVehicleNumber());

//        for (ParkingLocationModel parkingLocationModel : lstParkingLoc) {
//            if ((getEmployeeResp.getParkingLocationId() != null) && Utilities.isNumeric(getEmployeeResp.getParkingLocationId())
//                    && Integer.parseInt(getEmployeeResp.getParkingLocationId()) == parkingLocationModel.getParkingLocationId()) {
//                selectedParkingLocation = parkingLocationModel;
//                atcParkingLoc.setText(selectedParkingLocation.getName());
//                break;
//            }
//        }

        try {
//            if (getEmployeeResp.getTypeOfVisitor() != null && getEmployeeResp.getTypeOfVisitor().equalsIgnoreCase("Employee")) {
            for (TenantList tenantList : tenantListArrayList) {
                if (getEmployeeResp.getTenantId() != null
                        && tenantList.getTenantId() == Integer.parseInt(getEmployeeResp.getTenantId())) {
                    selectedCompany = tenantList;
                    atcCompanyName.setText(tenantList.getName());
                    break;
                }
            }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (TypeOfVisitor typeOfVisitor : typeOfVisitorArrayList) {
            if (typeOfVisitor.getVisitorType().equals(getEmployeeResp.getTypeOfVisitor())) {
                spnTypeOfVisitor.setSelection(typeOfVisitorArrayList.indexOf(typeOfVisitor) + 1);
                break;
            }
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
                        Intent intent = new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Toast.makeText(context, getResources().getString(R.string.sign_out_successfully), Toast.LENGTH_SHORT).show();
                        finish();
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
            e.printStackTrace();
        }
    }


}
