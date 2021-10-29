package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nithin on 7/30/2018.
 */

public class RepeatedVisitorAppoinrmtnt implements Serializable{

    @SerializedName("visitorLogPreappointment")
    @Expose
    private ArrayList<VisitorLogMobileViewModel> visitorLogMobileViewModels = null;
    @SerializedName("visitorRepeatedVisitor")
    @Expose
    private VisitorList visitorList;

    public ArrayList<VisitorLogMobileViewModel> getVisitorLogMobileViewModels() {
        return visitorLogMobileViewModels;
    }

    public void setVisitorLogMobileViewModels(ArrayList<VisitorLogMobileViewModel> visitorLogMobileViewModels) {
        this.visitorLogMobileViewModels = visitorLogMobileViewModels;
    }

    public VisitorList getVisitorList() {
        return visitorList;
    }

    public void setVisitorList(VisitorList visitorList) {
        this.visitorList = visitorList;
    }
}
