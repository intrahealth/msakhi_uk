package com.microware.intrahealth.object;

public class MstPanchayat {
	
	private int PanchayatID;
	private String StateCode;
	private String DistrictCode;
	private String BlockCode;
	private String PanchayatCode;
	private String PanchayatName;
	private int LanguageID;
	private int IsDeleted;
	
	public int getPanchayatID() {
		return PanchayatID;
	}
	public void setPanchayatID(int panchayatID) {
		PanchayatID = panchayatID;
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
	public String getBlockCode() {
		return BlockCode;
	}
	public void setBlockCode(String blockCode) {
		BlockCode = blockCode;
	}
	public String getPanchayatCode() {
		return PanchayatCode;
	}
	public void setPanchayatCode(String panchayatCode) {
		PanchayatCode = panchayatCode;
	}
	public String getPanchayatName() {
		return PanchayatName;
	}
	public void setPanchayatName(String panchayatName) {
		PanchayatName = panchayatName;
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
