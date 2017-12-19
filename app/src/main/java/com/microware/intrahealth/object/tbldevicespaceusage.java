package com.microware.intrahealth.object;

public class tbldevicespaceusage {
	private int ID;
	private int UserID;
	private String InternalSpace;
	private String AvialableSpace;
	private String IMEI;
	private String ExternalSpace;
	private String CreatedOn;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getUserID() {
		return UserID;
	}

	public void setUserID(int userID) {
		UserID = userID;
	}

	public String getInternalSpace() {
		return InternalSpace;
	}

	public void setInternalSpace(String internalSpace) {
		InternalSpace = internalSpace;
	}

	public String getAvialableSpace() {
		return AvialableSpace;
	}

	public void setAvialableSpace(String avialableSpace) {
		AvialableSpace = avialableSpace;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getExternalSpace() {
		return ExternalSpace;
	}

	public void setExternalSpace(String externalSpace) {
		ExternalSpace = externalSpace;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

}
