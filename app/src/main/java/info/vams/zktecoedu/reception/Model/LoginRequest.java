package info.vams.zktecoedu.reception.Model;

/**
 * Created by RahulK on 6/28/2018.
 */

public class LoginRequest {

    String grant_type;
    String username;
    String password;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
