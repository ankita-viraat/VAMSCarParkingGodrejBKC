package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nithin on 8/13/2018.
 */

public class ContractorLoginLogout implements Serializable {

    @SerializedName("contractorEmployeeId")
    @Expose
    private Integer contractorEmployeeId;
    @SerializedName("contractorId")
    @Expose
    private Integer contractorId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("isBlacklisted")
    @Expose
    private Boolean isBlacklisted;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("cardValidityStartDate")
    @Expose
    private String cardValidityStartDate;
    @SerializedName("cardValidityExpiryDate")
    @Expose
    private String cardValidityExpiryDate;
    @SerializedName("shiftStartTime")
    @Expose
    private Object shiftStartTime;
    @SerializedName("shiftEndTime")
    @Expose
    private Object shiftEndTime;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("modifiedById")
    @Expose
    private Integer modifiedById;
    @SerializedName("modifiedDateUtc")
    @Expose
    private String modifiedDateUtc;
    /*@SerializedName("contractorEmployeePhoto")
    @Expose
    private Object contractorEmployeePhoto;*/
    @SerializedName("contractors")
    @Expose
    private Object contractors;
    @SerializedName("emailAlternate")
    @Expose
    private Object emailAlternate;
    @SerializedName("emailPrimary")
    @Expose
    private String emailPrimary;
    @SerializedName("isdCode")
    @Expose
    private String isdCode;
    @SerializedName("mobilePrimary")
    @Expose
    private String mobilePrimary;
    @SerializedName("isdCodeAlternate")
    @Expose
    private Object isdCodeAlternate;
    @SerializedName("mobileAlternate")
    @Expose
    private Object mobileAlternate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("rejectReason")
    @Expose
    private Object rejectReason;
    @SerializedName("passId")
    @Expose
    private String passId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("countries")
    @Expose
    private Object countries;

    @SerializedName("companyName")
    @Expose
    private String companyName;

    @SerializedName("checkInStatus")
    @Expose
    private String checkInStatus;

    @SerializedName("contractorEmployeeIdProof")
    @Expose
    private Object contractorEmployeeIdProof;
    @SerializedName("contractorEmployeeIdProofList")
    @Expose
    private ArrayList<Object> contractorEmployeeIdProofList = null;
    @SerializedName("contractorEmployeeFieldDataList")
    @Expose
    private Object contractorEmployeeFieldDataList;
    @SerializedName("contractor")
    @Expose
    private Contractor contractor;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getContractorEmployeeId() {
        return contractorEmployeeId;
    }

    public void setContractorEmployeeId(Integer contractorEmployeeId) {
        this.contractorEmployeeId = contractorEmployeeId;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
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

    public Boolean getBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(Boolean blacklisted) {
        isBlacklisted = blacklisted;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCardValidityStartDate() {
        return cardValidityStartDate;
    }

    public void setCardValidityStartDate(String cardValidityStartDate) {
        this.cardValidityStartDate = cardValidityStartDate;
    }

    public String getCardValidityExpiryDate() {
        return cardValidityExpiryDate;
    }

    public void setCardValidityExpiryDate(String cardValidityExpiryDate) {
        this.cardValidityExpiryDate = cardValidityExpiryDate;
    }

    public Object getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(Object shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    public Object getShiftEndTime() {
        return shiftEndTime;
    }

    public void setShiftEndTime(Object shiftEndTime) {
        this.shiftEndTime = shiftEndTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getModifiedDateUtc() {
        return modifiedDateUtc;
    }

    public void setModifiedDateUtc(String modifiedDateUtc) {
        this.modifiedDateUtc = modifiedDateUtc;
    }

    /*public Object getContractorEmployeePhoto() {
        return contractorEmployeePhoto;
    }

    public void setContractorEmployeePhoto(Object contractorEmployeePhoto) {
        this.contractorEmployeePhoto = contractorEmployeePhoto;
    }*/

    public Object getContractors() {
        return contractors;
    }

    public void setContractors(Object contractors) {
        this.contractors = contractors;
    }

    public Object getEmailAlternate() {
        return emailAlternate;
    }

    public void setEmailAlternate(Object emailAlternate) {
        this.emailAlternate = emailAlternate;
    }

    public String getEmailPrimary() {
        return emailPrimary;
    }

    public void setEmailPrimary(String emailPrimary) {
        this.emailPrimary = emailPrimary;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public void setMobilePrimary(String mobilePrimary) {
        this.mobilePrimary = mobilePrimary;
    }

    public Object getIsdCodeAlternate() {
        return isdCodeAlternate;
    }

    public void setIsdCodeAlternate(Object isdCodeAlternate) {
        this.isdCodeAlternate = isdCodeAlternate;
    }

    public Object getMobileAlternate() {
        return mobileAlternate;
    }

    public void setMobileAlternate(Object mobileAlternate) {
        this.mobileAlternate = mobileAlternate;
    }


    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public Object getCountries() {
        return countries;
    }

    public void setCountries(Object countries) {
        this.countries = countries;
    }

    public Object getContractorEmployeeIdProof() {
        return contractorEmployeeIdProof;
    }

    public void setContractorEmployeeIdProof(Object contractorEmployeeIdProof) {
        this.contractorEmployeeIdProof = contractorEmployeeIdProof;
    }

    public ArrayList<Object> getContractorEmployeeIdProofList() {
        return contractorEmployeeIdProofList;
    }

    public void setContractorEmployeeIdProofList(ArrayList<Object> contractorEmployeeIdProofList) {
        this.contractorEmployeeIdProofList = contractorEmployeeIdProofList;
    }

    public Object getContractorEmployeeFieldDataList() {
        return contractorEmployeeFieldDataList;
    }

    public void setContractorEmployeeFieldDataList(Object contractorEmployeeFieldDataList) {
        this.contractorEmployeeFieldDataList = contractorEmployeeFieldDataList;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        this.contractor = contractor;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }
}
