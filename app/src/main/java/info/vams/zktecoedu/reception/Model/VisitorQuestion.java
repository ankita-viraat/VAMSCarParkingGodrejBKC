
package info.vams.zktecoedu.reception.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VisitorQuestion implements Serializable {

    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("toPrint")
    private Boolean mToPrint;
    @SerializedName("visitor")
    private Object mVisitor;
    @SerializedName("visitorId")
    private Long mVisitorId;
    @SerializedName("visitorQuestionId")
    private Long mVisitorQuestionId;
    private boolean isRequired;

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Boolean getToPrint() {
        return mToPrint;
    }

    public void setToPrint(Boolean toPrint) {
        mToPrint = toPrint;
    }

    public Object getVisitor() {
        return mVisitor;
    }

    public void setVisitor(Object visitor) {
        mVisitor = visitor;
    }

    public Long getVisitorId() {
        return mVisitorId;
    }

    public void setVisitorId(Long visitorId) {
        mVisitorId = visitorId;
    }

    public Long getVisitorQuestionId() {
        return mVisitorQuestionId;
    }

    public void setVisitorQuestionId(Long visitorQuestionId) {
        mVisitorQuestionId = visitorQuestionId;
    }

}
