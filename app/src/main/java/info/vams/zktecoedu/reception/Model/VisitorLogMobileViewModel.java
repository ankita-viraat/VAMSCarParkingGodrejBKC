
package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VisitorLogMobileViewModel implements Serializable {

    @SerializedName("appointmentId")
    private Integer mAppointmentId;
    @SerializedName("appointmentVisitorId")
    private Integer mAppointmentVisitorId;
    @SerializedName("buildingId")
    private String mBuildingId;
    @SerializedName("buildingName")
    private String mBuildingName;
    @SerializedName("complexId")
    private Integer mComplexId;
    @SerializedName("complexName")
    private String mComplexName;
    @SerializedName("noOfVisitor")
    private int mNoOfVisitor;
    @SerializedName("tenantId")
    private String mTenantId;
    @SerializedName("tenantName")
    private String mTenantName;
    @SerializedName("visitorList")
    private ArrayList<VisitorList> mVisitorList;
    @SerializedName("visitorLogId")
    private Integer mVisitorLogId;
    @SerializedName("visitorLogPersonToVisit")
    private ArrayList<VisitorLogPersonToVisit> mVisitorLogPersonToVisit;
    @SerializedName("nextAction")
    private String NextAction;
    @SerializedName("message")
    private String message;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNextAction() {
        return NextAction;
    }

    public void setNextAction(String nextAction) {
        NextAction = nextAction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAppointmentId() {
        return mAppointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        mAppointmentId = appointmentId;
    }

    public Integer getAppointmentVisitorId() {
        return mAppointmentVisitorId;
    }

    public void setAppointmentVisitorId(Integer appointmentVisitorId) {
        mAppointmentVisitorId = appointmentVisitorId;
    }

    public String getBuildingId() {
        return mBuildingId;
    }

    public void setBuildingId(String buildingId) {
        mBuildingId = buildingId;
    }

    public String getBuildingName() {
        return mBuildingName;
    }

    public void setBuildingName(String buildingName) {
        mBuildingName = buildingName;
    }

    public Integer getComplexId() {
        return mComplexId;
    }

    public void setComplexId(Integer complexId) {
        mComplexId = complexId;
    }

    public String getComplexName() {
        return mComplexName;
    }

    public void setComplexName(String complexName) {
        mComplexName = complexName;
    }

    public int getNoOfVisitor() {
        return mNoOfVisitor;
    }

    public void setNoOfVisitor(int noOfVisitor) {
        mNoOfVisitor = noOfVisitor;
    }

    public String getTenantId() {
        return mTenantId;
    }

    public void setTenantId(String tenantId) {
        mTenantId = tenantId;
    }

    public String getTenantName() {
        return mTenantName;
    }

    public void setTenantName(String tenantName) {
        mTenantName = tenantName;
    }

    public ArrayList<VisitorList> getVisitorList() {
        return mVisitorList;
    }

    public void setVisitorList(ArrayList<VisitorList> visitorList) {
        mVisitorList = visitorList;
    }

    public Integer getVisitorLogId() {
        return mVisitorLogId;
    }

    public void setVisitorLogId(Integer visitorLogId) {
        mVisitorLogId = visitorLogId;
    }

    public ArrayList<VisitorLogPersonToVisit> getVisitorLogPersonToVisit() {
        return mVisitorLogPersonToVisit;
    }

    public void setVisitorLogPersonToVisit(ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisit) {
        mVisitorLogPersonToVisit = visitorLogPersonToVisit;
    }

}
