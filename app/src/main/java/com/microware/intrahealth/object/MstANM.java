package com.microware.intrahealth.object;

public class MstANM {
	private int ANMID;
	private String ANMCode;
	private String ANMName;
	private int IsDeleted;
	private int LanguageID;
	public int getLanguageID() {
		return LanguageID;
	}
	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}
	public int getANMID() {
		return ANMID;
	}
	public void setANMID(int aNMID) {
		ANMID = aNMID;
	}
	public String getANMCode() {
		return ANMCode;
	}
	public void setANMCode(String aNMCode) {
		ANMCode = aNMCode;
	}
	public String getANMName() {
		return ANMName;
	}
	public void setANMName(String aNMName) {
		ANMName = aNMName;
	}
	public int getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

}
