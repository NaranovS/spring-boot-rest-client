package com.naranov.rest_client.model;

public enum  UserProfileType {
    USER("user"),
    DBA("dba"),
    ADMIN("admin");

    String userProfileType;

    private UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }

    public String getUserProfileType(){
        return userProfileType;
    }
}
