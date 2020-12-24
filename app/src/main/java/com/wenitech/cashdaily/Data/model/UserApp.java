package com.wenitech.cashdaily.Data.model;

public class UserApp {

    private Boolean fullProfile;
    private String typeAccount;
    private String subscription;

    public UserApp() {
    }

    public UserApp(Boolean fullProfile, String typeAccount, String subscription) {
        this.fullProfile = fullProfile;
        this.typeAccount = typeAccount;
        this.subscription = subscription;
    }

    public Boolean getFullProfile() {
        return fullProfile;
    }

    public void setFullProfile(Boolean fullProfile) {
        this.fullProfile = fullProfile;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
