package info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckIn;

/**
 * Created by Siddhesh gawde on 27/11/2020.
 */

import java.util.ArrayList;

import info.vams.zktecoedu.reception.Model.RequestClientDetails;

public class ParkCheckInReq {
    int complexId;
    int tenantId;
    int parkingLocationId;
    String ParkedBy;
    String subLocation;
    String firstName;
    String lastName;
    String isdCode;
    String mobile;
    String vehicleNumber;
    int typeOfVisitorId;
    String typeOfVisitor;
    String checkInTimeLocal;
    int checkedInById;
    String checkedInAtDeviceId;
    String checkedInAtFcmId;
    ArrayList<String> photo;
    RequestClientDetails requestClientDetails;

    public int getComplexId() {
        return complexId;
    }

    public void setComplexId(int complexId) {
        this.complexId = complexId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(int parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        this.subLocation = subLocation;
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

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getTypeOfVisitorId() {
        return typeOfVisitorId;
    }

    public void setTypeOfVisitorId(int typeOfVisitorId) {
        this.typeOfVisitorId = typeOfVisitorId;
    }

    public String getTypeOfVisitor() {
        return typeOfVisitor;
    }

    public void setTypeOfVisitor(String typeOfVisitor) {
        this.typeOfVisitor = typeOfVisitor;
    }

    public String getCheckInTimeLocal() {
        return checkInTimeLocal;
    }

    public void setCheckInTimeLocal(String checkInTimeLocal) {
        this.checkInTimeLocal = checkInTimeLocal;
    }

    public int getCheckedInById() {
        return checkedInById;
    }

    public void setCheckedInById(int checkedInById) {
        this.checkedInById = checkedInById;
    }

    public String getCheckedInAtDeviceId() {
        return checkedInAtDeviceId;
    }

    public void setCheckedInAtDeviceId(String checkedInAtDeviceId) {
        this.checkedInAtDeviceId = checkedInAtDeviceId;
    }

    public String getCheckedInAtFcmId() {
        return checkedInAtFcmId;
    }

    public void setCheckedInAtFcmId(String checkedInAtFcmId) {
        this.checkedInAtFcmId = checkedInAtFcmId;
    }

    public ArrayList<String> getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList<String> photo) {
        this.photo = photo;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }

    public String getParkedBy() {
        return ParkedBy;
    }

    public void setParkedBy(String parkedBy) {
        ParkedBy = parkedBy;
    }

    @Override
    public String toString() {
        return "ParkCheckInReq{" +
                "complexId=" + complexId +
                ", tenantId=" + tenantId +
                ", parkingLocationId=" + parkingLocationId +
                ", ParkedBy='" + ParkedBy + '\'' +
                ", subLocation='" + subLocation + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isdCode='" + isdCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", typeOfVisitorId=" + typeOfVisitorId +
                ", typeOfVisitor='" + typeOfVisitor + '\'' +
                ", checkInTimeLocal='" + checkInTimeLocal + '\'' +
                ", checkedInById=" + checkedInById +
                ", checkedInAtDeviceId='" + checkedInAtDeviceId + '\'' +
                ", checkedInAtFcmId='" + checkedInAtFcmId + '\'' +
                ", photo=" + photo +
                ", requestClientDetails=" + requestClientDetails +
                '}';
    }
}
