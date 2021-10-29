package info.vams.zktecoedu.reception.Model;

/**
 * Created by Nithin on 7/20/2018.
 */

public class VisitorPrintPassRequestMobileViewModel {

    long visitorLogId;

    RequestClientDetails requestClientDetails;

    public long getVisitorId() {
        return visitorLogId;
    }

    public void setVisitorId(long visitorId) {
        this.visitorLogId = visitorId;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }
}
