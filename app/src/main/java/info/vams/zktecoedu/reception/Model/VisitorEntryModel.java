
package info.vams.zktecoedu.reception.Model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class VisitorEntryModel implements Serializable{

    @SerializedName("visitorLogMobileViewModel")
    private VisitorLogMobileViewModel mVisitorLogMobileViewModel;

    public VisitorLogMobileViewModel getVisitorLogMobileViewModel() {
        return mVisitorLogMobileViewModel;
    }

    public void setVisitorLogMobileViewModel(VisitorLogMobileViewModel visitorLogMobileViewModel) {
        mVisitorLogMobileViewModel = visitorLogMobileViewModel;
    }

}
