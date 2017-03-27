package com.microware.intrahealth.object;

public class MstSubCenter {

	private int SubCenterID;
	private String SubCenterCode;
	private String SubCenterName;
	private int IsDeleted;
	private int PHC_id;
	private int LanguageID;

	public int getPHC_id() {
		return PHC_id;
	}

	public void setPHC_id(int pHC_id) {
		PHC_id = pHC_id;
	}

	public int getLanguageID() {
		return LanguageID;
	}

	public void setLanguageID(int languageID) {
		LanguageID = languageID;
	}

	public int getSubCenterID() {
		return SubCenterID;
	}

	public void setSubCenterID(int subCenterID) {
		SubCenterID = subCenterID;
	}

	public String getSubCenterCode() {
		return SubCenterCode;
	}

	public void setSubCenterCode(String subCenterCode) {
		SubCenterCode = subCenterCode;
	}

	public String getSubCenterName() {
		return SubCenterName;
	}

	public void setSubCenterName(String subCenterName) {
		SubCenterName = subCenterName;
	}

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

}
