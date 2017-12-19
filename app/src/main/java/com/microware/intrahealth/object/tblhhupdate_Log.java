package com.microware.intrahealth.object;

public class tblhhupdate_Log {

private int  UserID;
	private String hhsurveyguid;
	private String hhmemberguid;
	private int StatusId_Old;
	private int  StatusId_New;
	private String Aadhar_Old;
	private String Aadhar_New;
	private String PhoneNo_Old;
	private String PhoneNo_New;
	private String DOB_Old;
	private String DOB_New;
	private String AccountNo_Old;
	private String AccountNo_New;
	private String IFSCCode_Old;
	private String IFSCCode_New;
	private String UpdatedOn;
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getHhsurveyguid() {
		return hhsurveyguid;
	}
	public void setHhsurveyguid(String hhsurveyguid) {
		this.hhsurveyguid = hhsurveyguid;
	}
	public String getHhmemberguid() {
		return hhmemberguid;
	}
	public void setHhmemberguid(String hhmemberguid) {
		this.hhmemberguid = hhmemberguid;
	}
	public int getStatusId_Old() {
		return StatusId_Old;
	}
	public void setStatusId_Old(int statusId_Old) {
		StatusId_Old = statusId_Old;
	}
	public int getStatusId_New() {
		return StatusId_New;
	}
	public void setStatusId_New(int statusId_New) {
		StatusId_New = statusId_New;
	}
	public String getAadhar_Old() {
		return Aadhar_Old;
	}
	public void setAadhar_Old(String aadhar_Old) {
		Aadhar_Old = aadhar_Old;
	}
	public String getAadhar_New() {
		return Aadhar_New;
	}
	public void setAadhar_New(String aadhar_New) {
		Aadhar_New = aadhar_New;
	}
	public String getPhoneNo_Old() {
		return PhoneNo_Old;
	}
	public void setPhoneNo_Old(String phoneNo_Old) {
		PhoneNo_Old = phoneNo_Old;
	}
	public String getPhoneNo_New() {
		return PhoneNo_New;
	}
	public void setPhoneNo_New(String phoneNo_New) {
		PhoneNo_New = phoneNo_New;
	}
	public String getDOB_Old() {
		return DOB_Old;
	}
	public void setDOB_Old(String dOB_Old) {
		DOB_Old = dOB_Old;
	}
	public String getDOB_New() {
		return DOB_New;
	}
	public void setDOB_New(String dOB_New) {
		DOB_New = dOB_New;
	}
	public String getAccountNo_Old() {
		return AccountNo_Old;
	}
	public void setAccountNo_Old(String accountNo_Old) {
		AccountNo_Old = accountNo_Old;
	}
	public String getAccountNo_New() {
		return AccountNo_New;
	}
	public void setAccountNo_New(String accountNo_New) {
		AccountNo_New = accountNo_New;
	}
	public String getIFSCCode_Old() {
		return IFSCCode_Old;
	}
	public void setIFSCCode_Old(String iFSCCode_Old) {
		IFSCCode_Old = iFSCCode_Old;
	}
	public String getIFSCCode_New() {
		return IFSCCode_New;
	}
	public void setIFSCCode_New(String iFSCCode_New) {
		IFSCCode_New = iFSCCode_New;
	}
	public String getUpdatedOn() {
		return UpdatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
	}
	
}
