package info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee;

import info.vams.zktecoedu.reception.Model.RequestClientDetails;

/**
 * Created by Siddhesh gawde on 27/11/2020.
 */

public class GetParkEmployeeResp {
    int complexId;
    String firstName;
    String lastName;
    String isdCode;
    String mobile;
    String vehicleNumber;
    String buildingId;
    String tenantId;
    String tenantName;
    String typeOfVisitor;
    String parkingLocationId;

    public int getComplexId() {
        return complexId;
    }

    public void setComplexId(int complexId) {
        this.complexId = complexId;
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

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTypeOfVisitor() {
        return typeOfVisitor;
    }

    public void setTypeOfVisitor(String typeOfVisitor) {
        this.typeOfVisitor = typeOfVisitor;
    }

    public String getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(String parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
