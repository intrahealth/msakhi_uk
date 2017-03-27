package com.microware.intrahealth.object;

public class MstCommon {
	
	private int UID;
	private int ID;
	private String Value;
	private int ValueCode;
	private int LanguageID;
	private int Flag;
	private int IsDeleted;
	
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public int getLanguageID() {
		return LanguageID;
	}
	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}
	public int getFlag() {
		return Flag;
	}
	public void setFlag(int flag) {
		Flag = flag;
	}
	public int getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}
	public int getValueCode() {
		return ValueCode;
	}
	public void setValueCode(int valueCode) {
		ValueCode = valueCode;
	}

}
