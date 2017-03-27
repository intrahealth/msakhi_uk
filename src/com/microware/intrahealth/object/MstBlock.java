package com.microware.intrahealth.object;

public class MstBlock {
	
	private int BlockID;
	private String StateCode;
	private String DistrictCode;
	private String BlockCode;
	private String BlockName;
	private int LanguageID;
	private int IsDeleted;

	
	public int getBlockID() {
		return BlockID;
	}
	public void setBlockID(int blockID) {
		BlockID = blockID;
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
	public String getBlockName() {
		return BlockName;
	}
	public void setBlockName(String blockName) {
		BlockName = blockName;
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
