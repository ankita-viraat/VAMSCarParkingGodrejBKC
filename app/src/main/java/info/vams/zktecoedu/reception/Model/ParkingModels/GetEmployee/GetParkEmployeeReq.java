package info.vams.zktecoedu.reception.Model.ParkingModels.GetEmployee;

/**
 * Created by Siddhesh gawde on 27/11/2020.
 */

import info.vams.zktecoedu.reception.Model.RequestClientDetails;

public class GetParkEmployeeReq {
    int complexId;
    String buildingId;
    String tenantId="";
    int parkingLocationId;
    String parkingLocationName="";
    String mobile="";
    String vehicleNumber="";
    String status="";
    String visitorName="";
    String wildCardValue ="";
    int page;
    int pageSize;

    public String getWildCardValue() {
        return wildCardValue;
    }

    public void setWildCardValue(String wildCardValue) {
        this.wildCardValue = wildCardValue;
    }

    RequestClientDetails requestClientDetails;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public int getComplexId() {
        return complexId;
    }

    public void setComplexId(int complexId) {
        this.complexId = complexId;
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

    public int getParkingLocationId() {
        return parkingLocationId;
    }

    public void setParkingLocationId(int parkingLocationId) {
        this.parkingLocationId = parkingLocationId;
    }

    public String getParkingLocationName() {
        return parkingLocationName;
    }

    public void setParkingLocationName(String parkingLocationName) {
        this.parkingLocationName = parkingLocationName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }
}
