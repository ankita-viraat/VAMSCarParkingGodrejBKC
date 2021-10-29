package info.vams.zktecoedu.reception.Model;

import java.io.Serializable;

/**
 * Created by RahulK on 7/16/2018.
 */

public class VisitorIdProof implements Serializable {

    private Integer appointmentVisitorIdProofId;
    private Integer appointmentVisitorId;
    private Integer idProofTypeId;
    private String idProofName;
    private String number;
    private String document;
    private String documentExtension;
    private float modifiedById;
    private String modifiedDateUtc;

    public Integer getAppointmentVisitorIdProofId() {
        return appointmentVisitorIdProofId;
    }

    public void setAppointmentVisitorIdProofId(Integer appointmentVisitorIdProofId) {
        this.appointmentVisitorIdProofId = appointmentVisitorIdProofId;
    }

    public Integer getAppointmentVisitorId() {
        return appointmentVisitorId;
    }

    public void setAppointmentVisitorId(Integer appointmentVisitorId) {
        this.appointmentVisitorId = appointmentVisitorId;
    }

    public Integer getIdProofTypeId() {
        return idProofTypeId;
    }

    public void setIdProofTypeId(Integer idProofTypeId) {
        this.idProofTypeId = idProofTypeId;
    }

    public String getIdProofName() {
        return idProofName;
    }

    public void setIdProofName(String idProofName) {
        this.idProofName = idProofName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentExtension() {
        return documentExtension;
    }

    public void setDocumentExtension(String documentExtension) {
        this.documentExtension = documentExtension;
    }

    public float getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(float modifiedById) {
        this.modifiedById = modifiedById;
    }

    public String getModifiedDateUtc() {
        return modifiedDateUtc;
    }

    public void setModifiedDateUtc(String modifiedDateUtc) {
        this.modifiedDateUtc = modifiedDateUtc;
    }
}
