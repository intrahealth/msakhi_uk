package com.microware.intrahealth.object;

public class MstVersion {

	private int ApkVersionID;
	private String Version;
	private String VersionCode;
	private int IsActive;
	private int IsDeleted;
	public int getApkVersionID() {
		return ApkVersionID;
	}
	public void setApkVersionID(int apkVersionID) {
		ApkVersionID = apkVersionID;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getVersionCode() {
		return VersionCode;
	}
	public void setVersionCode(String versionCode) {
		VersionCode = versionCode;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	public int getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}
	
	
}
