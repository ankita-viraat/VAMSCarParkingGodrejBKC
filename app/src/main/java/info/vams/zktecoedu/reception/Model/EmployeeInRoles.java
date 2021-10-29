package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by RahulK on 7/6/2018.
 */

public class EmployeeInRoles implements Serializable {

    @SerializedName("employeeInRolesId")
    @Expose
    private Integer employeeInRolesId;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("roleName")
    @Expose
    private String roleName;
    @SerializedName("employee")
    @Expose
    private Object employee;
    @SerializedName("checked")
    @Expose
    private Boolean checked;

    public Integer getEmployeeInRolesId() {
        return employeeInRolesId;
    }

    public void setEmployeeInRolesId(Integer employeeInRolesId) {
        this.employeeInRolesId = employeeInRolesId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Object getEmployee() {
        return employee;
    }

    public void setEmployee(Object employee) {
        this.employee = employee;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
