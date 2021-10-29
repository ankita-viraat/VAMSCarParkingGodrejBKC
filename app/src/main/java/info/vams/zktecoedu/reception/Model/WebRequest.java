package info.vams.zktecoedu.reception.Model;

/**
 * Created by RahulK on 7/6/2018.
 */

public class WebRequest{

    private Integer visitorId;

    private String searchString;

    private String userId;

    private Integer tenantId;

    private RequestClientDetails requestClientDetails;

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public RequestClientDetails getRequestClientDetails() {
        return requestClientDetails;
    }

    public void setRequestClientDetails(RequestClientDetails requestClientDetails) {
        this.requestClientDetails = requestClientDetails;
    }
}
