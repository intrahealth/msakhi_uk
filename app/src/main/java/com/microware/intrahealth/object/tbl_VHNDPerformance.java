package com.microware.intrahealth.object;

public class tbl_VHNDPerformance {

	private int PID;
	private String VHND_ID;
	private int SS_ID;
	private int VillageId;
	private int AshaID;
	private int AWWID;
	private int ANMID;
	private String Date;
	private String OPT_Value;
	private String CreatedOn;
	private String CreatedBy;
	private String ModifyOn;
	private String ModifyBy;

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

	public String getModifyOn() {
		return ModifyOn;
	}

	public void setModifyOn(String modifyOn) {
		ModifyOn = modifyOn;
	}

	public String getModifyBy() {
		return ModifyBy;
	}

	public void setModifyBy(String modifyBy) {
		ModifyBy = modifyBy;
	}

	public int getPID() {
		return PID;
	}

	public void setPID(int pID) {
		PID = pID;
	}

	public String getVHND_ID() {
		return VHND_ID;
	}

	public void setVHND_ID(String vHND_ID) {
		VHND_ID = vHND_ID;
	}

	public int getSS_ID() {
		return SS_ID;
	}

	public void setSS_ID(int sS_ID) {
		SS_ID = sS_ID;
	}

	public int getVillageId() {
		return VillageId;
	}

	public void setVillageId(int villageId) {
		VillageId = villageId;
	}

	public int getAshaID() {
		return AshaID;
	}

	public void setAshaID(int ashaID) {
		AshaID = ashaID;
	}

	public int getAWWID() {
		return AWWID;
	}

	public void setAWWID(int aWWID) {
		AWWID = aWWID;
	}

	public int getANMID() {
		return ANMID;
	}

	public void setANMID(int aNMID) {
		ANMID = aNMID;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getOPT_Value() {
		return OPT_Value;
	}

	public void setOPT_Value(String oPT_Value) {
		OPT_Value = oPT_Value;
	}
}
