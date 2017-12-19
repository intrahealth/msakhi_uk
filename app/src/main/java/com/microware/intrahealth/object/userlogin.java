package com.microware.intrahealth.object;

/**
 * Created by Microware on 8/28/2017.
 */

public class userlogin {

    private int UserID;
    private String UserName;
    private String Password;
    private int RoleID;
    private int ASHAID;
    private String ASHAName;
    private int ANMID;
    private String ANMName;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getRoleID() {
        return RoleID;
    }

    public void setRoleID(int roleID) {
        RoleID = roleID;
    }

    public int getASHAID() {
        return ASHAID;
    }

    public void setASHAID(int ASHAID) {
        this.ASHAID = ASHAID;
    }

    public String getASHAName() {
        return ASHAName;
    }

    public void setASHAName(String ASHAName) {
        this.ASHAName = ASHAName;
    }

    public int getANMID() {
        return ANMID;
    }

    public void setANMID(int ANMID) {
        this.ANMID = ANMID;
    }

    public String getANMName() {
        return ANMName;
    }

    public void setANMName(String ANMName) {
        this.ANMName = ANMName;
    }
}
