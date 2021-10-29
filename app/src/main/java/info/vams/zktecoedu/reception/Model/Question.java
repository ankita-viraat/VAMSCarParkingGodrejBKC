
package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Question {
    @SerializedName("questionId")
    @Expose
    private Integer questionId;
    @SerializedName("complexId")
    @Expose
    private Integer complexId;
    @SerializedName("tenantId")
    @Expose
    private Integer tenantId;
    @SerializedName("complexName")
    @Expose
    private Object complexName;
    @SerializedName("tenantList")
    @Expose
    private Object tenantList;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isRequired")
    @Expose
    private Boolean isRequired;
    @SerializedName("toPrint")
    @Expose
    private Boolean toPrint;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("tenantName")
    @Expose
    private Object tenantName;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getComplexId() {
        return complexId;
    }

    public void setComplexId(Integer complexId) {
        this.complexId = complexId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public Object getComplexName() {
        return complexName;
    }

    public void setComplexName(Object complexName) {
        this.complexName = complexName;
    }

    public Object getTenantList() {
        return tenantList;
    }

    public void setTenantList(Object tenantList) {
        this.tenantList = tenantList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return isRequired;
    }

    public void setRequired(Boolean required) {
        isRequired = required;
    }

    public Boolean getToPrint() {
        return toPrint;
    }

    public void setToPrint(Boolean toPrint) {
        this.toPrint = toPrint;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Object getTenantName() {
        return tenantName;
    }

    public void setTenantName(Object tenantName) {
        this.tenantName = tenantName;
    }
}
