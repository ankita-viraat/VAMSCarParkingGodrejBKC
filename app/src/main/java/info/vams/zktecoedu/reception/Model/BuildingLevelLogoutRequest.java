package info.vams.zktecoedu.reception.Model;

import java.util.ArrayList;

/**
 * Created by vishal on 09-Apr-19.
 */

public class BuildingLevelLogoutRequest {

    private Integer visitorLogId = 0;
    private Integer visitorId = 0;
    private Integer complexId = 0;
    private Integer buildingId = 0;
    private Integer tenantId = 0;
    private Integer employeeId = 0;
    private String searchValue;

    private ArrayList<Building> buildings;

    RequestClientDetails requestClientDetails;

    public Integer getVisitorLogId() {
        return visitorLogId;
    }

    public void setVisitorLogId(Integer visitorLogId) {
        this.visitorLogId = visitorLogId;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }
}
