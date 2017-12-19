package com.microware.intrahealth.object;

public class tbl_DetailedModuleUsage {
	private String GUID;
	private String UserID;
	private String ModuleName;
	private String ModulePageName;
	private String StartTime;
	private String EndTime;
	private String Date;
	private int IsUpload;

	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getModuleName() {
		return ModuleName;
	}

	public void setModuleName(String moduleName) {
		ModuleName = moduleName;
	}

	public String getModulePageName() {
		return ModulePageName;
	}

	public void setModulePageName(String modulePageName) {
		ModulePageName = modulePageName;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getIsUpload() {
		return IsUpload;
	}

	public void setIsUpload(int isUpload) {
		IsUpload = isUpload;
	}
}
