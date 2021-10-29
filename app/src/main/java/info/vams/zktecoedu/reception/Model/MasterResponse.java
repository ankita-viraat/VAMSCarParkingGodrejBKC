package info.vams.zktecoedu.reception.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MasterResponse {

    @SerializedName("idProofTypes")
    @Expose
    private ArrayList<IdProofType> idProofTypes;
    @SerializedName("questions")
    @Expose
    private ArrayList<Question> questions;
    @SerializedName("purposeVisits")
    @Expose
    private ArrayList<PurposeVisit> purposeVisits;
    @SerializedName("typeOfVisitors")
    @Expose
    private ArrayList<TypeOfVisitor> typeOfVisitors;
    @SerializedName("appointmentRejectReason")
    @Expose
    private ArrayList<AppointmentRejectReason> appointmentRejectReason;
    @SerializedName("country")
    @Expose
    private ArrayList<Country> countries;

    public ArrayList<IdProofType> getIdProofTypes() {
        return idProofTypes;
    }

    public void setIdProofTypes(ArrayList<IdProofType> idProofTypes) {
        this.idProofTypes = idProofTypes;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<PurposeVisit> getPurposeVisits() {
        return purposeVisits;
    }

    public void setPurposeVisits(ArrayList<PurposeVisit> purposeVisits) {
        this.purposeVisits = purposeVisits;
    }

    public ArrayList<TypeOfVisitor> getTypeOfVisitors() {
        return typeOfVisitors;
    }

    public void setTypeOfVisitors(ArrayList<TypeOfVisitor> typeOfVisitors) {
        this.typeOfVisitors = typeOfVisitors;
    }

    public ArrayList<AppointmentRejectReason> getAppointmentRejectReason() {
        return appointmentRejectReason;
    }

    public void setAppointmentRejectReason(ArrayList<AppointmentRejectReason> appointmentRejectReason) {
        this.appointmentRejectReason = appointmentRejectReason;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }
}
