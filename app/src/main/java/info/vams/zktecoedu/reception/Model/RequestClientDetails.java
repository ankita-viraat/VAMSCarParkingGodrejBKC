package info.vams.zktecoedu.reception.Model;

/**
 * Created by RahulK on 6/28/2018.
 */

public class RequestClientDetails {
    String uuid;
    String fcmId;
    String deviceType;
    String ip;
    String language;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "RequestClientDetails{" +
                "uuid='" + uuid + '\'' +
                ", fcmId='" + fcmId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", ip='" + ip + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
