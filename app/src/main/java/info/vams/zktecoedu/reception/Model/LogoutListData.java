package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nithin on 8/9/2018.
 */

public class LogoutListData implements Serializable{

    @SerializedName("visitorId")
    @Expose
    private Integer visitorId;
    @SerializedName("visitorLogId")
    @Expose
    private Integer visitorLogId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isdCode")
    @Expose
    private String isdCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("remark")
    @Expose
    private Object remark;
    @SerializedName("vehicleRegistrationNo")
    @Expose
    private Object vehicleRegistrationNo;
    @SerializedName("accessCardNo")
    @Expose
    private String accessCardNo;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("qrCode")
    @Expose
    private String qrCode;
    @SerializedName("passId")
    @Expose
    private String passId;
    @SerializedName("requireAuthentication")
    @Expose
    private Boolean requireAuthentication;
    @SerializedName("authenticationKey")
    @Expose
    private String authenticationKey;
    @SerializedName("typeOfVisitorId")
    @Expose
    private Object typeOfVisitorId;
    @SerializedName("purposeOfVisitId")
    @Expose
    private Object purposeOfVisitId;
    @SerializedName("registeredVisitorId")
    @Expose
    private Object registeredVisitorId;
    @SerializedName("bypassedVisitorId")
    @Expose
    private Integer bypassedVisitorId;
    @SerializedName("visitorLog")
    @Expose
    private Object visitorLog;
    @SerializedName("visitorQuestions")
    @Expose
    private ArrayList<VisitorQuestion> visitorQuestions = null;
    @SerializedName("visitorIdProofs")
    @Expose
    private ArrayList<VisitorIdProof> visitorIdProofs = null;
    @SerializedName("visitorIdProof")
    @Expose
    private Object visitorIdProof;
    @SerializedName("idNumber")
    @Expose
    private Object idNumber;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("birthDate")
    @Expose
    private Object birthDate;
    @SerializedName("cardCountry")
    @Expose
    private Object cardCountry;
    @SerializedName("cardExpiryDate")
    @Expose
    private Object cardExpiryDate;
    @SerializedName("cardIssueDate")
    @Expose
    private Object cardIssueDate;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("isAuthenticationByPassed")
    @Expose
    private Boolean isAuthenticationByPassed;
    @SerializedName("isAuthenticationByPassedBy")
    @Expose
    private Integer isAuthenticationByPassedBy;

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Integer getVisitorLogId() {
        return visitorLogId;
    }

    public void setVisitorLogId(Integer visitorLogId) {
        this.visitorLogId = visitorLogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getVehicleRegistrationNo() {
        return vehicleRegistrationNo;
    }

    public void setVehicleRegistrationNo(Object vehicleRegistrationNo) {
        this.vehicleRegistrationNo = vehicleRegistrationNo;
    }

    public String getAccessCardNo() {
        return accessCardNo;
    }

    public void setAccessCardNo(String accessCardNo) {
        this.accessCardNo = accessCardNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public Boolean getRequireAuthentication() {
        return requireAuthentication;
    }

    public void setRequireAuthentication(Boolean requireAuthentication) {
        this.requireAuthentication = requireAuthentication;
    }

    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    public Object getTypeOfVisitorId() {
        return typeOfVisitorId;
    }

    public void setTypeOfVisitorId(Object typeOfVisitorId) {
        this.typeOfVisitorId = typeOfVisitorId;
    }

    public Object getPurposeOfVisitId() {
        return purposeOfVisitId;
    }

    public void setPurposeOfVisitId(Object purposeOfVisitId) {
        this.purposeOfVisitId = purposeOfVisitId;
    }

    public Object getRegisteredVisitorId() {
        return registeredVisitorId;
    }

    public void setRegisteredVisitorId(Object registeredVisitorId) {
        this.registeredVisitorId = registeredVisitorId;
    }

    public Integer getBypassedVisitorId() {
        return bypassedVisitorId;
    }

    public void setBypassedVisitorId(Integer bypassedVisitorId) {
        this.bypassedVisitorId = bypassedVisitorId;
    }

    public Object getVisitorLog() {
        return visitorLog;
    }

    public void setVisitorLog(Object visitorLog) {
        this.visitorLog = visitorLog;
    }

    public ArrayList<VisitorQuestion> getVisitorQuestions() {
        return visitorQuestions;
    }

    public void setVisitorQuestions(ArrayList<VisitorQuestion> visitorQuestions) {
        this.visitorQuestions = visitorQuestions;
    }

    public ArrayList<VisitorIdProof> getVisitorIdProofs() {
        return visitorIdProofs;
    }

    public void setVisitorIdProofs(ArrayList<VisitorIdProof> visitorIdProofs) {
        this.visitorIdProofs = visitorIdProofs;
    }

    public Object getVisitorIdProof() {
        return visitorIdProof;
    }

    public void setVisitorIdProof(Object visitorIdProof) {
        this.visitorIdProof = visitorIdProof;
    }

    public Object getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Object idNumber) {
        this.idNumber = idNumber;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public Object getCardCountry() {
        return cardCountry;
    }

    public void setCardCountry(Object cardCountry) {
        this.cardCountry = cardCountry;
    }

    public Object getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Object cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public Object getCardIssueDate() {
        return cardIssueDate;
    }

    public void setCardIssueDate(Object cardIssueDate) {
        this.cardIssueDate = cardIssueDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getAuthenticationByPassed() {
        return isAuthenticationByPassed;
    }

    public void setAuthenticationByPassed(Boolean authenticationByPassed) {
        isAuthenticationByPassed = authenticationByPassed;
    }

    public Integer getIsAuthenticationByPassedBy() {
        return isAuthenticationByPassedBy;
    }

    public void setIsAuthenticationByPassedBy(Integer isAuthenticationByPassedBy) {
        this.isAuthenticationByPassedBy = isAuthenticationByPassedBy;
    }
}
