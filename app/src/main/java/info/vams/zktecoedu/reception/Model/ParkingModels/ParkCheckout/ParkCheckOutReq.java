package info.vams.zktecoedu.reception.Model.ParkingModels.ParkCheckout;

/**
 * Created by Siddhesh gawde on 27/11/2020.
 */

import info.vams.zktecoedu.reception.Model.RequestClientDetails;

public class ParkCheckOutReq {
    int parkingVisitorId;
    int complexId;
    int tenantId;
    int parkingLocationId;
    String checkOutTimeLocal;
    int checkedOutById;
    String checkedOutAtDeviceId;
    String checkedOutAtFcmId;
    RequestClientDetails requestClientDetails;

    public int getParkingVisitorId() {
        return parkingVisitorId;
    }

    public void setParkingVisitorId(int parkingVisitorId) {
        this.parkingVisitorId = parkingVisitorId;
    }

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

    public String getCheckOutTimeLocal() {
        return checkOutTimeLocal;
    }

    public void setCheckOutTimeLocal(String checkOutTimeLocal) {
        this.checkOutTimeLocal = checkOutTimeLocal;
    }

    public int getCheckedOutById() {
        return checkedOutById;
    }

    public void setCheckedOutById(int checkedOutById) {
        this.checkedOutById = checkedOutById;
    }

    public String getCheckedOutAtDeviceId() {
        return checkedOutAtDeviceId;
    }

    public void setCheckedOutAtDeviceId(String checkedOutAtDeviceId) {
        this.checkedOutAtDeviceId = checkedOutAtDeviceId;
    }

    public String getCheckedOutAtFcmId() {
        return checkedOutAtFcmId;
    }

    public void setCheckedOutAtFcmId(String checkedOutAtFcmId) {
        this.checkedOutAtFcmId = checkedOutAtFcmId;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }
}
