
package info.vams.zktecoedu.reception.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VisitorLogPersonToVisit implements Serializable {

    @SerializedName("departmentId")
    private Object mDepartmentId;
    @SerializedName("emailAlternate")
    private Object mEmailAlternate;

    @SerializedName("departmentName")
    private String mdepartmentName;

    @SerializedName("emailPrimary")
    private String mEmailPrimary;
    @SerializedName("isdCode")
    private String mIsdCode;
    @SerializedName("mobilePrimary")
    private String mMobilePrimary;
    @SerializedName("mobileSecondary")
    private Object mMobileSecondary;
    @SerializedName("name")
    private String mName;
    @SerializedName("personToVisitId")
    private int mPersonToVisitId;
    @SerializedName("visitorLog")
    private Object mVisitorLog;
    @SerializedName("visitorLogId")
    private Long mVisitorLogId;
    @SerializedName("visitorLogPersonToVisitId")
    private Long mVisitorLogPersonToVisitId;


    public String getDepartmentName() {
        return mdepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.mdepartmentName = departmentName;
    }

    public Object getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(Object departmentId) {
        mDepartmentId = departmentId;
    }

    public Object getEmailAlternate() {
        return mEmailAlternate;
    }

    public void setEmailAlternate(Object emailAlternate) {
        mEmailAlternate = emailAlternate;
    }

    public String getEmailPrimary() {
        return mEmailPrimary;
    }

    public void setEmailPrimary(String emailPrimary) {
        mEmailPrimary = emailPrimary;
    }

    public String getIsdCode() {
        return mIsdCode;
    }

    public void setIsdCode(String isdCode) {
        mIsdCode = isdCode;
    }

    public String getMobilePrimary() {
        return mMobilePrimary;
    }

    public void setMobilePrimary(String mobilePrimary) {
        mMobilePrimary = mobilePrimary;
    }

    public Object getMobileSecondary() {
        return mMobileSecondary;
    }

    public void setMobileSecondary(Object mobileSecondary) {
        mMobileSecondary = mobileSecondary;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPersonToVisitId() {
        return mPersonToVisitId;
    }

    public void setPersonToVisitId(int personToVisitId) {
        mPersonToVisitId = personToVisitId;
    }

    public Object getVisitorLog() {
        return mVisitorLog;
    }

    public void setVisitorLog(Object visitorLog) {
        mVisitorLog = visitorLog;
    }

    public Long getVisitorLogId() {
        return mVisitorLogId;
    }

    public void setVisitorLogId(Long visitorLogId) {
        mVisitorLogId = visitorLogId;
    }

    public Long getVisitorLogPersonToVisitId() {
        return mVisitorLogPersonToVisitId;
    }

    public void setVisitorLogPersonToVisitId(Long visitorLogPersonToVisitId) {
        mVisitorLogPersonToVisitId = visitorLogPersonToVisitId;
    }

}
