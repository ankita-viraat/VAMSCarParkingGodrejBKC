package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppointmentRejectReason {
    @SerializedName("appointmentRejectReasonId")
    @Expose
    private Integer appointmentRejectReasonId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    public Integer getAppointmentRejectReasonId() {
        return appointmentRejectReasonId;
    }

    public void setAppointmentRejectReasonId(Integer appointmentRejectReasonId) {
        this.appointmentRejectReasonId = appointmentRejectReasonId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
