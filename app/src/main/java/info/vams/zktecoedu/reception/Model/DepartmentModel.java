package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nithin on 8/2/2018.
 */

public class DepartmentModel implements Serializable {

    @SerializedName("departmentId")
    @Expose
    private int departmentId;
    @SerializedName("complexId")
    @Expose
    private int complexId;
    @SerializedName("tenantId")
    @Expose
    private int tenantId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("complex")
    @Expose
    private Object complex;
    @SerializedName("tenant")
    @Expose
    private Object tenant;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Object getComplex() {
        return complex;
    }

    public void setComplex(Object complex) {
        this.complex = complex;
    }

    public Object getTenant() {
        return tenant;
    }

    public void setTenant(Object tenant) {
        this.tenant = tenant;
    }
}
