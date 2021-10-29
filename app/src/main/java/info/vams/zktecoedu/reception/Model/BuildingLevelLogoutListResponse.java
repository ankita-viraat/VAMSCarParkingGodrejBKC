package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vishal on 09-Apr-19.
 */

public class BuildingLevelLogoutListResponse implements Serializable{
    @SerializedName("visitorLogId")
    private Integer visitorLogId;
    @SerializedName("visitorId")
    private Integer visitorId;
    @SerializedName("complexId")
    private Integer complexId;
    @SerializedName("buildingId")
    private Integer buildingId;
    @SerializedName("tenantId")
    private Integer tenantId;
    @SerializedName("name")
    private String name;
    @SerializedName("mobilePrimary")
    private String mobilePrimary;
    @SerializedName("mobileSecondary")
    private String mobileSecondary ;
    @SerializedName("emailPrimary")
    private String emailPrimary ;
    @SerializedName("complexInTimeUtc")
    private String complexInTimeUtc;
    @SerializedName("complexOutTimeUtc")
    private String complexOutTimeUtc;
    @SerializedName("buildingInTimeUtc")
    private String buildingInTimeUtc;
    @SerializedName("buildingOutTimeUtc")
    private String buildingOutTimeUtc;
    @SerializedName("imageUrl")
    private String imageUrl;
    //@SerializedName("visitorLogPersonToVisit")
    //private String visitorLogPersonToVisit;
    @SerializedName("personToMeet")
    private ArrayList<String> personToMeet;
    @SerializedName("visitorLogPersonToVisit")
    private ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisit;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplexInTimeUtc() {
        return complexInTimeUtc;
    }

    public void setComplexInTimeUtc(String complexInTimeUtc) {
        this.complexInTimeUtc = complexInTimeUtc;
    }

    public String getComplexOutTimeUtc() {
        return complexOutTimeUtc;
    }

    public void setComplexOutTimeUtc(String complexOutTimeUtc) {
        this.complexOutTimeUtc = complexOutTimeUtc;
    }

    public String getBuildingInTimeUtc() {
        return buildingInTimeUtc;
    }

    public void setBuildingInTimeUtc(String buildingInTimeUtc) {
        this.buildingInTimeUtc = buildingInTimeUtc;
    }

    public String getBuildingOutTimeUtc() {
        return buildingOutTimeUtc;
    }

    public void setBuildingOutTimeUtc(String buildingOutTimeUtc) {
        this.buildingOutTimeUtc = buildingOutTimeUtc;
    }

    public ArrayList<String> getPersonToMeet() {
        return personToMeet;
    }

    public void setPersonToMeet(ArrayList<String> personToMeet) {
        this.personToMeet = personToMeet;
    }

    /*public VisitorLogPersonToVisit[] getVisitorLogPersonToVisit() {
        return visitorLogPersonToVisit;
    }

    public void setVisitorLogPersonToVisit(VisitorLogPersonToVisit[] visitorLogPersonToVisit) {
        this.visitorLogPersonToVisit = visitorLogPersonToVisit;
    }*/

    public ArrayList<VisitorLogPersonToVisit> getVisitorLogPersonToVisit() {
        return visitorLogPersonToVisit;
    }

    public void setVisitorLogPersonToVisit(ArrayList<VisitorLogPersonToVisit> visitorLogPersonToVisit) {
        this.visitorLogPersonToVisit = visitorLogPersonToVisit;
    }

    /*public String getVisitorLogPersonToVisit() {
        return visitorLogPersonToVisit;
    }

    public void setVisitorLogPersonToVisit(String visitorLogPersonToVisit) {
        this.visitorLogPersonToVisit = visitorLogPersonToVisit;
    }*/

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public void setMobilePrimary(String mobilePrimary) {
        this.mobilePrimary = mobilePrimary;
    }

    public String getMobileSecondary() {
        return mobileSecondary;
    }

    public void setMobileSecondary(String mobileSecondary) {
        this.mobileSecondary = mobileSecondary;
    }

    public String getEmailPrimary() {
        return emailPrimary;
    }

    public void setEmailPrimary(String emailPrimary) {
        this.emailPrimary = emailPrimary;
    }
}
