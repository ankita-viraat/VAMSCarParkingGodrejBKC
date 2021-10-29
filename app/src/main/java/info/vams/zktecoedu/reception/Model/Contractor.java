package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nithin on 8/29/2018.
 */

public class Contractor implements Serializable{

    @SerializedName("contractorId")
    @Expose
    private Integer contractorId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("tenantId")
    @Expose
    private Object tenantId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("companyName")
    @Expose
    private String companyName;
    @SerializedName("emailPrimary")
    @Expose
    private String emailPrimary;
    @SerializedName("emailAlternate")
    @Expose
    private Object emailAlternate;
    @SerializedName("isdCode")
    @Expose
    private String isdCode;
    @SerializedName("mobilePrimary")
    @Expose
    private String mobilePrimary;
    @SerializedName("isdCodeAlternate")
    @Expose
    private Object isdCodeAlternate;
    @SerializedName("mobileAlternate")
    @Expose
    private Object mobileAlternate;
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
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("complex")
    @Expose
    private Object complex;
    @SerializedName("tenant")
    @Expose
    private Object tenant;
    @SerializedName("url")
    @Expose
    private Object url;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("postalAddress")
    @Expose
    private String postalAddress;

    public Integer getContractorId() {
        return contractorId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public Object getTenantId() {
        return tenantId;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmailPrimary() {
        return emailPrimary;
    }

    public Object getEmailAlternate() {
        return emailAlternate;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public String getMobilePrimary() {
        return mobilePrimary;
    }

    public Object getIsdCodeAlternate() {
        return isdCodeAlternate;
    }

    public Object getMobileAlternate() {
        return mobileAlternate;
    }

    public String getStdCodePrimary() {
        return stdCodePrimary;
    }

    public String getPhoneNoPrimary() {
        return phoneNoPrimary;
    }

    public Object getStdCodeAlternate() {
        return stdCodeAlternate;
    }

    public Object getPhoneNoAlternate() {
        return phoneNoAlternate;
    }

    public Object getAddress() {
        return address;
    }

    public Object getComplex() {
        return complex;
    }

    public Object getTenant() {
        return tenant;
    }

    public Object getUrl() {
        return url;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Object getLogo() {
        return logo;
    }

    public String getPostalAddress() {
        return postalAddress;
    }
}
