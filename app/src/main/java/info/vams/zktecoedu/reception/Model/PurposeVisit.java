
package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PurposeVisit {
    @SerializedName("purposeOfVisitId")
    @Expose
    private Integer purposeOfVisitId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    public Integer getPurposeOfVisitId() {
        return purposeOfVisitId;
    }

    public void setPurposeOfVisitId(Integer purposeOfVisitId) {
        this.purposeOfVisitId = purposeOfVisitId;
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
