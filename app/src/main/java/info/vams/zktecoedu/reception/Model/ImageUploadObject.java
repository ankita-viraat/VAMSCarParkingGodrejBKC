package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;

/**
 * Created by Nithin on 7/19/2018.
 */

public class ImageUploadObject implements Serializable {

    long visitorid;
    long registeredVisitorId;
    long byPassVisitorId;
    String mobileNo;
    String emailId;
    String url;
    String timeStamp;
    String vName;
    boolean isCapture;

    public long getVisitorid() {
        return visitorid;
    }

    public void setVisitorid(long visitorid) {
        this.visitorid = visitorid;
    }

    public long getRegisteredVisitorId() {
        return registeredVisitorId;
    }

    public void setRegisteredVisitorId(long registeredVisitorId) {
        this.registeredVisitorId = registeredVisitorId;
    }

    public long getByPassVisitorId() {
        return byPassVisitorId;
    }

    public void setByPassVisitorId(long byPassVisitorId) {
        this.byPassVisitorId = byPassVisitorId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean capture) {
        isCapture = capture;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }


}
