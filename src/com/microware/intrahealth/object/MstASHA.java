package com.microware.intrahealth.object;

public class MstASHA {

	private int ASHAID;
	private String ASHACode;
	private String ASHAName;
	private int IsDeleted;
	private int CHS_ID;
	private String ANMCode;
	private int LanguageID;

	public String getANMCode() {
		return ANMCode;
	}

	public void setANMCode(String aNMCode) {
		ANMCode = aNMCode;
	}

	public int getCHS_ID() {
		return CHS_ID;
	}

	public void setCHS_ID(int cHS_ID) {
		CHS_ID = cHS_ID;
	}

	public int getASHAID() {
		return ASHAID;
	}

	public void setASHAID(int aSHAID) {
		ASHAID = aSHAID;
	}

	public String getASHACode() {
		return ASHACode;
	}

	public void setASHACode(String aSHACode) {
		ASHACode = aSHACode;
	}

	public String getASHAName() {
		return ASHAName;
	}

	public void setASHAName(String aSHAName) {
		ASHAName = aSHAName;
	}

	public int getLanguageID() {
		return LanguageID;
	}

	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

}
