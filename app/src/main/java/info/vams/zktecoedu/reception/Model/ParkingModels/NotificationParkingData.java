package info.vams.zktecoedu.reception.Model.ParkingModels;

/**
 * Created by Siddhesh gawde on 27/11/2020.
 */

import info.vams.zktecoedu.reception.Model.RequestClientDetails;

public class NotificationParkingData {
    String message;
    String parkingLocation;
    String tag;
    String vehicleNo;
    int parkingVisitorId;
    String name;
    String inTime;
    String status;
    String mobile;
    String companyName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getParkingVisitorId() {
        return parkingVisitorId;
    }

    public void setParkingVisitorId(int parkingVisitorId) {
        this.parkingVisitorId = parkingVisitorId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
