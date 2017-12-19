package com.microware.intrahealth.object;

public class MstDistrict {
	
	private int DistrictID;
	private String StateCode;
	private String DistrictCode;
	private String DistrictName;
	private int LanguageID;
	private int IsDeleted;

	public int getDistrictID() {
		return DistrictID;
	}
	public void setDistrictID(int districtID) {
		DistrictID = districtID;
	}
	public String getStateCode() {
		return StateCode;
	}
	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}
	public String getDistrictCode() {
		return DistrictCode;
	}
	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
	}
	public String getDistrictName() {
		return DistrictName;
	}
	public void setDistrictName(String districtName) {
		DistrictName = districtName;
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
