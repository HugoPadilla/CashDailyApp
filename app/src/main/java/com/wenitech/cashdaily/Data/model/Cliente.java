package com.wenitech.cashdaily.Data.model;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.ServerTimestamp;

public class Cliente {
    @DocumentId()
    private String id;
    @ServerTimestamp
    private Timestamp creationDate;
    private boolean creditActive;
    private DocumentReference documentReferenceCreditActive;

    private String fullName;
    private String identificationClient;
    private String gender;
    private String phoneNumber;
    private String city;
    private String direction;

    public Cliente() {
    }

    public Cliente(boolean creditActive, DocumentReference documentReferenceCreditActive,
                   String fullName, String identificationClient,
                   String gender, String phoneNumber,
                   String city, String direction) {


        this.creditActive = creditActive;
        this.documentReferenceCreditActive = documentReferenceCreditActive;
        this.fullName = fullName;
        this.identificationClient = identificationClient;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public boolean isCreditActive() {
        return creditActive;
    }

    public DocumentReference getDocumentReferenceCreditActive() {
        return documentReferenceCreditActive;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdentificationClient() {
        return identificationClient;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getDirection() {
        return direction;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setCreditActive(boolean creditActive) {
        this.creditActive = creditActive;
    }

    public void setDocumentReferenceCreditActive(DocumentReference documentReferenceCreditActive) {
        this.documentReferenceCreditActive = documentReferenceCreditActive;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdentificationClient(String identificationClient) {
        this.identificationClient = identificationClient;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
