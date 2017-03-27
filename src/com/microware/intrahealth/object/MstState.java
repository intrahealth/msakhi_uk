package com.microware.intrahealth.object;

public class MstState {
	
	private int StateID;
	private String StateCode;
	private String StateName;
	private int LanguageID;
	private int IsDeleted;

	
	public int getStateID() {
		return StateID;
	}
	public void setStateID(int stateID) {
		StateID = stateID;
	}
	public String getStateCode() {
		return StateCode;
	}
	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}
	public String getStateName() {
		return StateName;
	}
	public void setStateName(String stateName) {
		StateName = stateName;
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
