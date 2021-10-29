package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RahulK on 7/6/2018.
 */

public class EmployeeList implements Serializable{

    private Object tenantId;

    private int employeeId;

    private Object complex;

    private String imageUrl;

    private Object designation;

    private Object emailAlternate;

    private Object forgotHashTokenReqOnUtc;

    private String emailPrimary;

    private Object employeePhoto;

    private Object appointments;

    private Object tenant;

    private String userId;

    private Object photoBytes;

    private ArrayList<EmployeeInRoles> idProofTypes;

    private String mobilePrimary;

    private String firstName;

    private Object deviceId;

    private Object buildingId;

    private String lastName;

    private Object visitorNotification;

    private Object building;

    private Object mobileAlternate;

    private String deleted;

    private Object isdCodeAlternate;

    private String isActive;

    private String complexId;

    private String isdCode;

    private String departmentName;

    private Object forgotHashToken;

    private String departmentId;

    private DepartmentModel department;

    private Object mobileOS;

    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Object getComplex() {
        return complex;
    }

    public void setComplex(Object complex) {
        this.complex = complex;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Object getDesignation() {
        return designation;
    }

    public void setDesignation(Object designation) {
        this.designation = designation;
    }

    public Object getEmailAlternate() {
        return emailAlternate;
    }

    public void setEmailAlternate(Object emailAlternate) {
        this.emailAlternate = emailAlternate;
    }

    public Object getForgotHashTokenReqOnUtc() {
        return forgotHashTokenReqOnUtc;
    }

    public void setForgotHashTokenReqOnUtc(Object forgotHashTokenReqOnUtc) {
        this.forgotHashTokenReqOnUtc = forgotHashTokenReqOnUtc;
    }

    public String getEmailPrimary() {
        return emailPrimary;
    }

    public void setEmailPrimary(String emailPrimary) {
        this.emailPrimary = emailPrimary;
    }

    public Object getEmployeePhoto() {
        return employeePhoto;
    }

    public void setEmployeePhoto(Object employeePhoto) {
        this.employeePhoto = employeePhoto;
    }

    public Object getAppointments() {
        return appointments;
    }

    public void setAppointments(Object appointments) {
        this.appointments = appointments;
    }

    public Object getTenant() {
        return tenant;
    }

    public void setTenant(Object tenant) {
        this.tenant = tenant;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getPhotoBytes() {
        return photoBytes;
    }

    public void setPhotoBytes(Object photoBytes) {
        this.photoBytes = photoBytes;
    }

    public ArrayList<EmployeeInRoles> getIdProofTypes() {
        return idProofTypes;
    }

    public void setIdProofTypes(ArrayList<EmployeeInRoles> idProofTypes) {
        this.idProofTypes = idProofTypes;
    }

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public void setMobilePrimary(String mobilePrimary) {
        this.mobilePrimary = mobilePrimary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Object getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Object deviceId) {
        this.deviceId = deviceId;
    }

    public Object getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Object buildingId) {
        this.buildingId = buildingId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getVisitorNotification() {
        return visitorNotification;
    }

    public void setVisitorNotification(Object visitorNotification) {
        this.visitorNotification = visitorNotification;
    }

    public Object getBuilding() {
        return building;
    }

    public void setBuilding(Object building) {
        this.building = building;
    }

    public Object getMobileAlternate() {
        return mobileAlternate;
    }

    public void setMobileAlternate(Object mobileAlternate) {
        this.mobileAlternate = mobileAlternate;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Object getIsdCodeAlternate() {
        return isdCodeAlternate;
    }

    public void setIsdCodeAlternate(Object isdCodeAlternate) {
        this.isdCodeAlternate = isdCodeAlternate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Object getForgotHashToken() {
        return forgotHashToken;
    }

    public void setForgotHashToken(Object forgotHashToken) {
        this.forgotHashToken = forgotHashToken;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public Object getMobileOS() {
        return mobileOS;
    }

    public void setMobileOS(Object mobileOS) {
        this.mobileOS = mobileOS;
    }
}
