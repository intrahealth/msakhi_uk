package com.microware.intrahealth.object;

public class tbl_Incentive {

	private int IUID;
	private int ASHA_ID;
	private String ShortName;
	private String Description;
	private String Amount;
	private String Documents;
	private String Effect_Fromdate;
	private String Effect_todate;
	private int Status;
	private String AreaType;
	private String Query;
	private String CreatedOn;
	private String CreatedBy;
	private int Item_id;

	private int Language_Id;

	public int getIUID() {
		return IUID;
	}

	public void setIUID(int iUID) {
		IUID = iUID;
	}

	public int getASHA_ID() {
		return ASHA_ID;
	}

	public void setASHA_ID(int aSHA_ID) {
		ASHA_ID = aSHA_ID;
	}

	public String getShortName() {
		return ShortName;
	}

	public void setShortName(String shortName) {
		ShortName = shortName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getDocuments() {
		return Documents;
	}

	public void setDocuments(String documents) {
		Documents = documents;
	}

	public String getEffect_Fromdate() {
		return Effect_Fromdate;
	}

	public void setEffect_Fromdate(String effect_Fromdate) {
		Effect_Fromdate = effect_Fromdate;
	}

	public String getEffect_todate() {
		return Effect_todate;
	}

	public void setEffect_todate(String effect_todate) {
		Effect_todate = effect_todate;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getAreaType() {
		return AreaType;
	}

	public void setAreaType(String areaType) {
		AreaType = areaType;
	}

	public String getQuery() {
		return Query;
	}

	public void setQuery(String query) {
		Query = query;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public int getItem_id() {
		return Item_id;
	}

	public void setItem_id(int item_id) {
		Item_id = item_id;
	}

	public int getLanguage_Id() {
		return Language_Id;
	}

	public void setLanguage_Id(int language_Id) {
		Language_Id = language_Id;
	}
}
