package info.vams.zktecoedu.reception.Model;

/**
 * Created by vishal on 10-Apr-19.
 */

public class VisitorPassQueueResponse {

    private Integer visitorId;
    private Integer visitorLogId;
    private Integer registeredVisitorId;
    private Integer bypassedVisitorId;
    private String name;
    private Boolean printPass;
    private String email;
    private String mobileNo;
    private String company;
    private String tenantName;
    private String buildingName;
    private String accessCardNo;
    private String requestedOn;
    private String status;
    private String remark;
    private String actionBy;
    private String personToMeet;
    private String imageUrl;

    //private ArrayList<String> personToMeet;


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

    public Integer getRegisteredVisitorId() {
        return registeredVisitorId;
    }

    public void setRegisteredVisitorId(Integer registeredVisitorId) {
        this.registeredVisitorId = registeredVisitorId;
    }

    public Integer getBypassedVisitorId() {
        return bypassedVisitorId;
    }

    public void setBypassedVisitorId(Integer bypassedVisitorId) {
        this.bypassedVisitorId = bypassedVisitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrintPass() {
        return printPass;
    }

    public void setPrintPass(Boolean printPass) {
        this.printPass = printPass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAccessCardNo() {
        return accessCardNo;
    }

    public void setAccessCardNo(String accessCardNo) {
        this.accessCardNo = accessCardNo;
    }

    public String getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(String requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getPersonToMeet() {
        return personToMeet;
    }

    public void setPersonToMeet(String personToMeet) {
        this.personToMeet = personToMeet;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
