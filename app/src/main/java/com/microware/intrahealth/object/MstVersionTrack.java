package com.microware.intrahealth.object;

public class MstVersionTrack {

	private int VersionTrackId;
	private int ApkVersionID;
	private int UserID;
	private String LastSync;
	public int getVersionTrackId() {
		return VersionTrackId;
	}
	public void setVersionTrackId(int versionTrackId) {
		VersionTrackId = versionTrackId;
	}
	public int getApkVersionID() {
		return ApkVersionID;
	}
	public void setApkVersionID(int apkVersionID) {
		ApkVersionID = apkVersionID;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getLastSync() {
		return LastSync;
	}
	public void setLastSync(String lastSync) {
		LastSync = lastSync;
	}
	
}



