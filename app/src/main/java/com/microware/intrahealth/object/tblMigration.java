package com.microware.intrahealth.object;

public class tblMigration {
	private String HHFamilyMemberGUID;
	private String DateOfMigrationIn;
	private String DateOfMigrationOut;
	private String MigrationGUID;
	private int CreatedBy;
	private String CreatedOn;
	private int UpdatedBy;
	private String UpdatedOn;
	
	public int getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(int createdBy) {
		CreatedBy = createdBy;
	}
	public String getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}
	public int getUpdatedBy() {
		return UpdatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		UpdatedBy = updatedBy;
	}
	public String getUpdatedOn() {
		return UpdatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
	}
	public String getMigrationGUID() {
		return MigrationGUID;
	}
	public void setMigrationGUID(String migrationGUID) {
		MigrationGUID = migrationGUID;
	}
	public String getHHFamilyMemberGUID() {
		return HHFamilyMemberGUID;
	}
	public void setHHFamilyMemberGUID(String hHFamilyMemberGUID) {
		HHFamilyMemberGUID = hHFamilyMemberGUID;
	}
	public String getDateOfMigrationIn() {
		return DateOfMigrationIn;
	}
	public void setDateOfMigrationIn(String dateOfMigrationIn) {
		DateOfMigrationIn = dateOfMigrationIn;
	}
	public String getDateOfMigrationOut() {
		return DateOfMigrationOut;
	}
	public void setDateOfMigrationOut(String dateOfMigrationOut) {
		DateOfMigrationOut = dateOfMigrationOut;
	}
	
}
