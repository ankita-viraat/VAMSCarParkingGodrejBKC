package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vishal on 05-Apr-19.
 */

public class Building implements Serializable {

    @SerializedName("buildingId")
    @Expose
    private Integer buildingId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive = false;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted = false;
    @SerializedName("complex")
    @Expose
    private String complex;
    @SerializedName("tenants")
    @Expose
    private String tenants;

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getComplex() {
        return complex;
    }

    public void setComplex(String complex) {
        this.complex = complex;
    }

    public String getTenants() {
        return tenants;
    }

    public void setTenants(String tenants) {
        this.tenants = tenants;
    }
}
