
package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TenantList {
    @SerializedName("tenantId")
    @Expose
    private Integer tenantId=0;
    @SerializedName("complexId")
    @Expose
    private Integer complexId=0;
    @SerializedName("buildingId")
    @Expose
    private Integer buildingId=0;
    @SerializedName("buildingName")
    @Expose
    private String buildingName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("emailPrimary")
    @Expose
    private String emailPrimary;
    @SerializedName("emailAlternate")
    @Expose
    private Object emailAlternate;
    @SerializedName("stdCodePrimary")
    @Expose
    private String stdCodePrimary;
    @SerializedName("phoneNoPrimary")
    @Expose
    private String phoneNoPrimary;
    @SerializedName("stdCodeAlternate")
    @Expose
    private Object stdCodeAlternate;
    @SerializedName("phoneNoAlternate")
    @Expose
    private Object phoneNoAlternate;
    @SerializedName("postalAddress")
    @Expose
    private PostalAddress postalAddress;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("officeNumber")
    @Expose
    private String officeNumber;
    @SerializedName("floor")
    @Expose
    private String floor;
    @SerializedName("visitorPassPrefix")
    @Expose
    private Object visitorPassPrefix;
    @SerializedName("contractorPassPrefix")
    @Expose
    private Object contractorPassPrefix;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("employees")
    @Expose
    private Object employees;
    @SerializedName("countries")
    @Expose
    private Object countries;
    @SerializedName("buildings")
    @Expose
    private Object buildings;
    @SerializedName("logoBytes")
    @Expose
    private Object logoBytes;
    @SerializedName("address")
    @Expose
    private String address;

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public String getEmailPrimary() {
        return emailPrimary;
    }

    public void setEmailPrimary(String emailPrimary) {
        this.emailPrimary = emailPrimary;
    }

    public Object getEmailAlternate() {
        return emailAlternate;
    }

    public void setEmailAlternate(Object emailAlternate) {
        this.emailAlternate = emailAlternate;
    }

    public String getStdCodePrimary() {
        return stdCodePrimary;
    }

    public void setStdCodePrimary(String stdCodePrimary) {
        this.stdCodePrimary = stdCodePrimary;
    }

    public String getPhoneNoPrimary() {
        return phoneNoPrimary;
    }

    public void setPhoneNoPrimary(String phoneNoPrimary) {
        this.phoneNoPrimary = phoneNoPrimary;
    }

    public Object getStdCodeAlternate() {
        return stdCodeAlternate;
    }

    public void setStdCodeAlternate(Object stdCodeAlternate) {
        this.stdCodeAlternate = stdCodeAlternate;
    }

    public Object getPhoneNoAlternate() {
        return phoneNoAlternate;
    }

    public void setPhoneNoAlternate(Object phoneNoAlternate) {
        this.phoneNoAlternate = phoneNoAlternate;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public Object getVisitorPassPrefix() {
        return visitorPassPrefix;
    }

    public void setVisitorPassPrefix(Object visitorPassPrefix) {
        this.visitorPassPrefix = visitorPassPrefix;
    }

    public Object getContractorPassPrefix() {
        return contractorPassPrefix;
    }

    public void setContractorPassPrefix(Object contractorPassPrefix) {
        this.contractorPassPrefix = contractorPassPrefix;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Object getEmployees() {
        return employees;
    }

    public void setEmployees(Object employees) {
        this.employees = employees;
    }

    public Object getCountries() {
        return countries;
    }

    public void setCountries(Object countries) {
        this.countries = countries;
    }

    public Object getBuildings() {
        return buildings;
    }

    public void setBuildings(Object buildings) {
        this.buildings = buildings;
    }

    public Object getLogoBytes() {
        return logoBytes;
    }

    public void setLogoBytes(Object logoBytes) {
        this.logoBytes = logoBytes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
