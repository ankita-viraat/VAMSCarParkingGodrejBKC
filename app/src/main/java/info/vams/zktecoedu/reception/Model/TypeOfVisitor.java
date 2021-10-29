package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TypeOfVisitor {
    @SerializedName("typeOfVisitorId")
    @Expose
    private Integer typeOfVisitorId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("visitorType")
    @Expose
    private String visitorType;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    public Integer getTypeOfVisitorId() {
        return typeOfVisitorId;
    }

    public void setTypeOfVisitorId(Integer typeOfVisitorId) {
        this.typeOfVisitorId = typeOfVisitorId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
