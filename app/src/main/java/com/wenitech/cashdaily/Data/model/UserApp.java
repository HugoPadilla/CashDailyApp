package com.wenitech.cashdaily.Data.model;

public class UserApp {

    private String FullName;
    private String typeUserAccount;
    private String typeSubscription;
    private Boolean fullProfile;

    public UserApp() {
    }

    public UserApp(String fullName, String typeUserAccount, String typeSubscription, Boolean fullProfile) {
        FullName = fullName;
        this.typeUserAccount = typeUserAccount;
        this.typeSubscription = typeSubscription;
        this.fullProfile = fullProfile;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTypeUserAccount() {
        return typeUserAccount;
    }

    public void setTypeUserAccount(String typeUserAccount) {
        this.typeUserAccount = typeUserAccount;
    }

    public String getTypeSubscription() {
        return typeSubscription;
    }

    public void setTypeSubscription(String typeSubscription) {
        this.typeSubscription = typeSubscription;
    }

    public Boolean getFullProfile() {
        return fullProfile;
    }

    public void setFullProfile(Boolean fullProfile) {
        this.fullProfile = fullProfile;
    }
}
