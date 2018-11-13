package com.microware.intrahealth.object;

/**
 * Created by Rakesh on 17-Oct-18.
 */

public class tblafverify {
    private int UID;
    private String AFGUID;
    private int ModuleID;
    private String ModuleGUID;
    private int Verify;
    private String VerifyOn;
    private int AshaID;
    private int ANMID;
    private int AFID;
    private int CreatedBy;
    private String CreatedOn;
    private int UpdatedBy;
    private String UpdatedOn;

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public String getAFGUID() {
        return AFGUID;
    }

    public void setAFGUID(String AFGUID) {
        this.AFGUID = AFGUID;
    }

    public int getModuleID() {
        return ModuleID;
    }

    public void setModuleID(int moduleID) {
        ModuleID = moduleID;
    }

    public String getModuleGUID() {
        return ModuleGUID;
    }

    public void setModuleGUID(String moduleGUID) {
        ModuleGUID = moduleGUID;
    }

    public int getVerify() {
        return Verify;
    }

    public void setVerify(int verify) {
        Verify = verify;
    }

    public String getVerifyOn() {
        return VerifyOn;
    }

    public void setVerifyOn(String verifyOn) {
        VerifyOn = verifyOn;
    }

    public int getAshaID() {
        return AshaID;
    }

    public void setAshaID(int ashaID) {
        AshaID = ashaID;
    }

    public int getANMID() {
        return ANMID;
    }

    public void setANMID(int ANMID) {
        this.ANMID = ANMID;
    }

    public int getAFID() {
        return AFID;
    }

    public void setAFID(int AFID) {
        this.AFID = AFID;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        UpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return UpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        UpdatedOn = updatedOn;
    }
}
