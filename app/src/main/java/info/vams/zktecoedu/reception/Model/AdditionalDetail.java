
package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdditionalDetail implements Serializable {

    @SerializedName("company")
    private String mCompany;

    @SerializedName("accessCardNo")
    private String mAccessCardNo;

    @SerializedName("typeOfVisitorId")
    private Integer mTypeOfVisitorId;


    @SerializedName("purposeOfVisitId")
    private Integer mPurposeOfVisitId;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @SerializedName("remarks")
    private String remarks;


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

    @SerializedName("gender")
    private Object mGender;



    public Object getGender() {
        return mGender;
    }

    public void setGender(Object gender) {
        mGender = gender;
    }

    public String getmCompany() {
        return mCompany;
    }

    public void setmCompany(String mCompany) {
        this.mCompany = mCompany;
    }

    public String getmAccessCardNo() {
        return mAccessCardNo;
    }

    public void setmAccessCardNo(String mAccessCardNo) {
        this.mAccessCardNo = mAccessCardNo;
    }

    public Integer getmTypeOfVisitorId() {
        return mTypeOfVisitorId;
    }

    public void setmTypeOfVisitorId(Integer mTypeOfVisitorId) {
        this.mTypeOfVisitorId = mTypeOfVisitorId;
    }

    public Integer getmPurposeOfVisitId() {
        return mPurposeOfVisitId;
    }

    public void setmPurposeOfVisitId(Integer mPurposeOfVisitId) {
        this.mPurposeOfVisitId = mPurposeOfVisitId;
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
