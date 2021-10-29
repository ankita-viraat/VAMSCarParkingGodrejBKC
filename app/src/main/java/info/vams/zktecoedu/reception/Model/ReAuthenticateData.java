package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nithin on 8/29/2018.
 */

public class ReAuthenticateData implements Serializable{

    @SerializedName("registeredVisitorId")
    @Expose
    private Integer registeredVisitorId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isdCode")
    @Expose
    private Integer isdCode;
    @SerializedName("mobile")
    @Expose
    private Integer mobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("authenticatedBy")
    @Expose
    private String authenticatedBy;
    @SerializedName("modifiedById")
    @Expose
    private Integer modifiedById;
    @SerializedName("modifiedDateUtc")
    @Expose
    private String modifiedDateUtc;
    @SerializedName("isUnRegisteredVisitor")
    @Expose
    private boolean isUnRegisteredVisitor;
    @SerializedName("authenticationKey")
    @Expose
    private Integer authenticationKey;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;


    public Integer getRegisteredVisitorId() {
        return registeredVisitorId;
    }

    public void setRegisteredVisitorId(Integer registeredVisitorId) {
        this.registeredVisitorId = registeredVisitorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(Integer isdCode) {
        this.isdCode = isdCode;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthenticatedBy() {
        return authenticatedBy;
    }

    public void setAuthenticatedBy(String authenticatedBy) {
        this.authenticatedBy = authenticatedBy;
    }

    public Integer getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getModifiedDateUtc() {
        return modifiedDateUtc;
    }

    public void setModifiedDateUtc(String modifiedDateUtc) {
        this.modifiedDateUtc = modifiedDateUtc;
    }

    public boolean isUnRegisteredVisitor() {
        return isUnRegisteredVisitor;
    }

    public void setUnRegisteredVisitor(boolean unRegisteredVisitor) {
        isUnRegisteredVisitor = unRegisteredVisitor;
    }

    public Integer getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(Integer authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
