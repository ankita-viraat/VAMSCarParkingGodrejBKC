
package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class VisitorList implements Serializable {

    @SerializedName("accessCardNo")
    private String mAccessCardNo;

    @SerializedName("authenticationKey")
    private Object mAuthenticationKey;

    @SerializedName("barcode")
    private Object mBarcode;

    @SerializedName("birthDate")
    private String mBirthDate;

    @SerializedName("bypassedVisitorId")
    private Long mBypassedVisitorId;

    @SerializedName("cardCountry")
    private Object mCardCountry;

    @SerializedName("cardExpiryDate")
    private String mCardExpiryDate;

    @SerializedName("cardIssueDate")
    private String mCardIssueDate;

    @SerializedName("company")
    private String mCompany;

    @SerializedName("email")
    private String mEmail;

    @SerializedName("gender")
    private Object mGender;

    @SerializedName("idNumber")
    private Object mIdNumber;

    @SerializedName("isdCode")
    private String mIsdCode;

    @SerializedName("mobile")
    private String mMobile;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("name")
    private String name;

    @SerializedName("passId")
    private Object mPassId;

    @SerializedName("purposeOfVisitId")
    private Integer mPurposeOfVisitId;

    @SerializedName("qrCode")
    private String mQrCode;

    @SerializedName("registeredVisitorId")
    private Integer mRegisteredVisitorId;

    @SerializedName("remark")
    private String mRemark;

    @SerializedName("requireAuthentication")
    private Boolean mRequireAuthentication;

    @SerializedName("typeOfVisitorId")
    private Integer mTypeOfVisitorId;

    @SerializedName("vehicleRegistrationNo")
    private String mVehicleRegistrationNo;

    @SerializedName("visitorId")
    private Long mVisitorId;

    @SerializedName("visitorIdProofs")
    private ArrayList<VisitorIdProof> mVisitorIdProof;

    @SerializedName("visitorLog")
    private Object mVisitorLog;

    @SerializedName("visitorLogId")
    private Long mVisitorLogId;

    @SerializedName("imageUrl")
    private String mImageUrl;

    @SerializedName("isAuthenticationByPassed")
    private boolean isAuthenticationByPassed;

    @SerializedName("isAuthenticationByPassedBy")
    private int isAuthenticationByPassedBy;

    @SerializedName("visitorQuestions")
    private ArrayList<VisitorQuestion> mVisitorQuestions;


    @SerializedName("state")
    private String mState;

    @SerializedName("statecode")
    private String mStatecode;

    @SerializedName("city")
    private String mCity;

    @SerializedName("zipcode")
    private String mZipcode;

    @SerializedName("birthdate")
    private String mBirthdate;

    @SerializedName("idnumber")
    private String mIdnumber;

    @SerializedName("cardissuedate")
    private String mCardissuedate;

    @SerializedName("cardexpirydate")
    private String mCardexpirydate;

    @SerializedName("cardtype")
    private String mCardtype;



    public boolean getIsAuthenticationByPassed() {
        return isAuthenticationByPassed;
    }

    public void setIsAuthenticationByPassed(boolean isAuthenticationByPassed) {
        this.isAuthenticationByPassed = isAuthenticationByPassed;
    }

    public int getIsAuthenticationByPassedBy() {
        return isAuthenticationByPassedBy;
    }

    public void setIsAuthenticationByPassedBy(int isAuthenticationByPassedBy) {
        this.isAuthenticationByPassedBy = isAuthenticationByPassedBy;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getAccessCardNo() {
        return mAccessCardNo;
    }

    public void setAccessCardNo(String accessCardNo) {
        mAccessCardNo = accessCardNo;
    }

    public Object getAuthenticationKey() {
        return mAuthenticationKey;
    }

    public void setAuthenticationKey(Object authenticationKey) {
        mAuthenticationKey = authenticationKey;
    }

    public Object getBarcode() {
        return mBarcode;
    }

    public void setBarcode(Object barcode) {
        mBarcode = barcode;
    }

    public Long getBypassedVisitorId() {
        return mBypassedVisitorId;
    }

    public void setBypassedVisitorId(Long bypassedVisitorId) {
        mBypassedVisitorId = bypassedVisitorId;
    }

    public Object getCardCountry() {
        return mCardCountry;
    }

    public void setCardCountry(Object cardCountry) {
        mCardCountry = cardCountry;
    }

    public String getCardExpiryDate() {
        return mCardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        mCardExpiryDate = cardExpiryDate;
    }

    public String getCardIssueDate() {
        return mCardIssueDate;
    }

    public void setCardIssueDate(String cardIssueDate) {
        mCardIssueDate = cardIssueDate;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Object getGender() {
        return mGender;
    }

    public void setGender(Object gender) {
        mGender = gender;
    }

    public Object getIdNumber() {
        return mIdNumber;
    }

    public void setIdNumber(Object idNumber) {
        mIdNumber = idNumber;
    }

    public String getIsdCode() {
        return mIsdCode;
    }

    public void setIsdCode(String isdCode) {
        mIsdCode = isdCode;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Object getPassId() {
        return mPassId;
    }

    public void setPassId(Object passId) {
        mPassId = passId;
    }

    public Integer getPurposeOfVisitId() {
        return mPurposeOfVisitId;
    }

    public void setPurposeOfVisitId(Integer purposeOfVisitId) {
        mPurposeOfVisitId = purposeOfVisitId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getQrCode() {
        return mQrCode;
    }

    public void setQrCode(String qrCode) {
        mQrCode = qrCode;
    }

    public Integer getRegisteredVisitorId() {
        return mRegisteredVisitorId;
    }

    public void setRegisteredVisitorId(Integer registeredVisitorId) {
        mRegisteredVisitorId = registeredVisitorId;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public Boolean getRequireAuthentication() {
        return mRequireAuthentication;
    }

    public void setRequireAuthentication(Boolean requireAuthentication) {
        mRequireAuthentication = requireAuthentication;
    }

    public Integer getTypeOfVisitorId() {
        return mTypeOfVisitorId;
    }

    public void setTypeOfVisitorId(Integer typeOfVisitorId) {
        mTypeOfVisitorId = typeOfVisitorId;
    }

    public String getVehicleRegistrationNo() {
        return mVehicleRegistrationNo;
    }

    public void setVehicleRegistrationNo(String vehicleRegistrationNo) {
        mVehicleRegistrationNo = vehicleRegistrationNo;
    }

    public Long getVisitorId() {
        return mVisitorId;
    }

    public void setVisitorId(Long visitorId) {
        mVisitorId = visitorId;
    }

    public ArrayList<VisitorIdProof> getVisitorIdProof() {
        return mVisitorIdProof;
    }

    public void setVisitorIdProof(ArrayList<VisitorIdProof> visitorIdProof) {
        mVisitorIdProof = visitorIdProof;
    }

    public Object getVisitorLog() {
        return mVisitorLog;
    }

    public void setVisitorLog(Object visitorLog) {
        mVisitorLog = visitorLog;
    }

    public Long getVisitorLogId() {
        return mVisitorLogId;
    }

    public void setVisitorLogId(Long visitorLogId) {
        mVisitorLogId = visitorLogId;
    }

    public ArrayList<VisitorQuestion> getVisitorQuestions() {
        return mVisitorQuestions;
    }

    public void setVisitorQuestions(ArrayList<VisitorQuestion> visitorQuestions) {
        mVisitorQuestions = visitorQuestions;
    }

    public String getState() {
        return mState;
    }

    public void setState(String mState) {
        this.mState = mState;
    }

    public String getStatecode() {
        return mStatecode;
    }

    public void setStatecode(String mStatecode) {
        this.mStatecode = mStatecode;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public void setZipcode(String mZipcode) {
        this.mZipcode = mZipcode;
    }

    public String getBirthdate() {
        return mBirthdate;
    }

    public void setBirthdate(String mBirthdate) {
        this.mBirthdate = mBirthdate;
    }

    public String getIdnumber() {
        return mIdnumber;
    }

    public void setIdnumber(String mIdnumber) {
        this.mIdnumber = mIdnumber;
    }

    public String getCardissuedate() {
        return mCardissuedate;
    }

    public void setCardissuedate(String mCardissuedate) {
        this.mCardissuedate = mCardissuedate;
    }

    public String getCardexpirydate() {
        return mCardexpirydate;
    }

    public void setCardexpirydate(String mCardexpirydate) {
        this.mCardexpirydate = mCardexpirydate;
    }

    public String getCardtype() {
        return mCardtype;
    }

    public void setCardtype(String mCardtype) {
        this.mCardtype = mCardtype;
    }

}
