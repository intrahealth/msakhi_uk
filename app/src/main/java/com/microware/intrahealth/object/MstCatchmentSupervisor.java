package com.microware.intrahealth.object;

public class MstCatchmentSupervisor {
	private int LanguageID;
	private int CHS_ID;
	private int SubCenterID;
	private String SupervisorCode;
	private String SupervisorName;
	private int IsDeleted;

	public int getLanguageID() {
		return LanguageID;
	}

	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}

	public int getCHS_ID() {
		return CHS_ID;
	}

	public void setCHS_ID(int cHS_ID) {
		CHS_ID = cHS_ID;
	}

	public int getSubCenterID() {
		return SubCenterID;
	}

	public void setSubCenterID(int subCenterID) {
		SubCenterID = subCenterID;
	}

	public String getSupervisorCode() {
		return SupervisorCode;
	}

	public void setSupervisorCode(String supervisorCode) {
		SupervisorCode = supervisorCode;
	}

	public String getSupervisorName() {
		return SupervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		SupervisorName = supervisorName;
	}

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

}
