package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nithin on 8/8/2018.
 */

public class Country implements Serializable {

    @SerializedName("countryId")
    @Expose
    private Integer countryId;
    @SerializedName("isoCode")
    @Expose
    private String isoCode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("niceName")
    @Expose
    private String niceName;
    @SerializedName("isO3")
    @Expose
    private String isO3;
    @SerializedName("numCode")
    @Expose
    private Integer numCode;
    @SerializedName("phoneCode")
    @Expose
    private Integer phoneCode;
    @SerializedName("isDefault")
    @Expose
    private Boolean isDefault;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getIsO3() {
        return isO3;
    }

    public void setIsO3(String isO3) {
        this.isO3 = isO3;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public Integer getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
