package com.microware.intrahealth.object;

public class TblMstuser {

	private int UserID;
	private String UserNameL;
	private String Password;
	private int RoleID;
	private int IsDeleted;
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getUserNameL() {
		return UserNameL;
	}
	public void setUserNameL(String userNameL) {
		UserNameL = userNameL;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getRoleID() {
		return RoleID;
	}
	public void setRoleID(int roleID) {
		RoleID = roleID;
	}
	public int getIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

}
