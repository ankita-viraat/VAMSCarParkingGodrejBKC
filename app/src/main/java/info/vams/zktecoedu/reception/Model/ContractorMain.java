package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nithin on 8/13/2018.
 */

public class ContractorMain implements Serializable{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("message")
    @Expose
    private ContractorLoginLogout message;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ContractorLoginLogout getMessage() {
        return message;
    }

    public void setMessage(ContractorLoginLogout message) {
        this.message = message;
    }
}
