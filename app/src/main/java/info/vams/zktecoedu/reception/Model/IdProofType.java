
package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IdProofType {

    @SerializedName("idProofTypeId")
    @Expose
    private Integer idProofTypeId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("modifiedById")
    @Expose
    private Integer modifiedById;
    @SerializedName("modifiedDateUtc")
    @Expose
    private String modifiedDateUtc;

    public Integer getIdProofTypeId() {
        return idProofTypeId;
    }

    public void setIdProofTypeId(Integer idProofTypeId) {
        this.idProofTypeId = idProofTypeId;
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

    public Integer getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getModifiedDateUtc() {
        return modifiedDateUtc;
    }

    public void setModifiedDateUtc(String modifiedDateUtc) {
        this.modifiedDateUtc = modifiedDateUtc;
    }
}
