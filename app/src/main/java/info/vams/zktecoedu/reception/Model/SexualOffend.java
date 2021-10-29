package info.vams.zktecoedu.reception.Model;

public class SexualOffend {

    private String firstName;
    private String lastName;
    private String middleName;
    private String dateOfBirth;
    private String offenderStatus;
    private String offenderCategory;
    private String imageUrl;

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getOffenderStatus() {
        return offenderStatus;
    }

    public void setOffenderStatus(String offenderStatus) {
        this.offenderStatus = offenderStatus;
    }

    public String getOffenderCategory() {
        return offenderCategory;
    }

    public void setOffenderCategory(String offenderCategory) {
        this.offenderCategory = offenderCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
