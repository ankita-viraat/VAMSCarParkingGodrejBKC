package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RahulK on 7/5/2018.
 */

public class Profile implements Serializable{

    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("complexName")
    @Expose
    private String complexName;
    @SerializedName("buildingId")
    @Expose
    private Object buildingId;
    @SerializedName("buildingName")
    @Expose
    private Object buildingName;
    @SerializedName("tenantId")
    @Expose
    private Object tenantId;
    @SerializedName("tenantName")
    @Expose
    private Object tenantName;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
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
    @SerializedName("mobileAlternate")
    @Expose
    private Object mobileAlternate;
    @SerializedName("visitorNotification")
    @Expose
    private Object visitorNotification;
    @SerializedName("designation")
    @Expose
    private Object designation;
    @SerializedName("department")
    @Expose
    private Object department;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("employeeInRoles")
    @Expose
    private ArrayList<EmployeeInRoles> employeeInRoles = null;
    @SerializedName("fcmId")
    @Expose
    private String fcmId;

    @SerializedName("isChangedPassword")
    @Expose
    private boolean isChangedPassword;

    @SerializedName("timeZone")
    @Expose
    String timeZone = "";

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getComplexName() {
        return complexName;
    }

    public void setComplexName(String complexName) {
        this.complexName = complexName;
    }

    public Object getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Object buildingId) {
        this.buildingId = buildingId;
    }

    public Object getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(Object buildingName) {
        this.buildingName = buildingName;
    }

    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public Object getTenantName() {
        return tenantName;
    }

    public void setTenantName(Object tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public Object getMobileAlternate() {
        return mobileAlternate;
    }

    public void setMobileAlternate(Object mobileAlternate) {
        this.mobileAlternate = mobileAlternate;
    }

    public Object getVisitorNotification() {
        return visitorNotification;
    }

    public void setVisitorNotification(Object visitorNotification) {
        this.visitorNotification = visitorNotification;
    }

    public Object getDesignation() {
        return designation;
    }

    public void setDesignation(Object designation) {
        this.designation = designation;
    }

    public Object getDepartment() {
        return department;
    }

    public void setDepartment(Object department) {
        this.department = department;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<EmployeeInRoles> getEmployeeInRoles() {
        return employeeInRoles;
    }

    public void setEmployeeInRoles(ArrayList<EmployeeInRoles> employeeInRoles) {
        this.employeeInRoles = employeeInRoles;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public boolean isChangedPassword() {
        return isChangedPassword;
    }

    public void setChangedPassword(boolean changedPassword) {
        isChangedPassword = changedPassword;
    }
}
