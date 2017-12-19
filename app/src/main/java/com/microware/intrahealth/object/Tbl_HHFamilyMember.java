package com.microware.intrahealth.object;

public class Tbl_HHFamilyMember {

	private int HHFamilyMemberUID;
	private String HHFamilyMemberGUID;
	private String HHSurveyGUID;
	private String HHFamilyMemberCode;
	private String UniqueIDNumber;
	private String FamilyMemberName;
	private int RelationID;
	private int GenderID;
	private int MaritialStatusID;
	private int DOBAvailable;
	private String DateOfBirth;
	private int AgeAsOnYear;
	private int AprilAgeYear;
	private int AprilAgeMonth;
	private String MotherGUID;
	private int TargetID;
	private int CreatedBy;
	private String CreatedOn;
	private int UploadedBy;
	private String UploadedOn;
	private int IsTablet;
	private int IsDeleted;
	private int StatusID;
	private String Father;
	private String Mother;
	private String Spouse;
	private String TempMember;
	private String HHCode;
	private int AshaID;
	private int ANMID;
	private int Education;
	private String DateOfDeath;
	private int PlaceOfDeath;
	private String NameofDeathplace;
	private int DeathVillage;
	
	//add
	private String Registration_Date;
	private int Occupation;
	private int Rsby_Beneficiary;
	private int Health_Condition;
	private String Any_HealthIssue;
	private int Any_PhysicalInability;
	private String Occupation_Other;
	private String  Phone_No;
	private String  Any_HealthIssue_Other;
	private int Other_Id_Type;
	private String Other_Id;
	private String  Other_Id_Name;
	
	
	public int getOther_Id_Type() {
		return Other_Id_Type;
	}

	public void setOther_Id_Type(int other_Id_Type) {
		Other_Id_Type = other_Id_Type;
	}

	public String getOther_Id() {
		return Other_Id;
	}

	public void setOther_Id(String other_Id) {
		Other_Id = other_Id;
	}

	public String getOther_Id_Name() {
		return Other_Id_Name;
	}

	public void setOther_Id_Name(String other_Id_Name) {
		Other_Id_Name = other_Id_Name;
	}

	public String getNameofDeathplace() {
		return NameofDeathplace;
	}

	public void setNameofDeathplace(String nameofDeathplace) {
		NameofDeathplace = nameofDeathplace;
	}

	public String getAny_HealthIssue() {
		return Any_HealthIssue;
	}

	public void setAny_HealthIssue(String any_HealthIssue) {
		Any_HealthIssue = any_HealthIssue;
	}

	public int getDeathVillage() {
		return DeathVillage;
	}

	public void setDeathVillage(int deathVillage) {
		DeathVillage = deathVillage;
	}

	public String getDateOfDeath() {
		return DateOfDeath;
	}

	public void setDateOfDeath(String dateOfDeath) {
		DateOfDeath = dateOfDeath;
	}

	public int getPlaceOfDeath() {
		return PlaceOfDeath;
	}

	public void setPlaceOfDeath(int placeOfDeath) {
		PlaceOfDeath = placeOfDeath;
	}

	public int getEducation() {
		return Education;
	}

	public void setEducation(int education) {
		Education = education;
	}

	public int getAshaID() {
		return AshaID;
	}

	public void setAshaID(int ashaID) {
		AshaID = ashaID;
	}

	public int getANMID() {
		return ANMID;
	}

	public void setANMID(int aNMID) {
		ANMID = aNMID;
	}

	public String getHHCode() {
		return HHCode;
	}

	public void setHHCode(String hHCode) {
		HHCode = hHCode;
	}

	
	public String getFather() {
		return Father;
	}

	public void setFather(String father) {
		Father = father;
	}

	public String getMother() {
		return Mother;
	}

	public void setMother(String mother) {
		Mother = mother;
	}

	public String getSpouse() {
		return Spouse;
	}

	public void setSpouse(String spouse) {
		Spouse = spouse;
	}

	public String getTempMember() {
		return TempMember;
	}

	public void setTempMember(String tempMember) {
		TempMember = tempMember;
	}


	public int getAgeAsOnYear() {
		return AgeAsOnYear;
	}

	public void setAgeAsOnYear(int ageAsOnYear) {
		AgeAsOnYear = ageAsOnYear;
	}

	public int getStatusID() {
		return StatusID;
	}

	public void setStatusID(int statusID) {
		StatusID = statusID;
	}

	public int getHHFamilyMemberUID() {
		return HHFamilyMemberUID;
	}

	public void setHHFamilyMemberUID(int hHFamilyMemberUID) {
		HHFamilyMemberUID = hHFamilyMemberUID;
	}

	public String getHHFamilyMemberGUID() {
		return HHFamilyMemberGUID;
	}

	public void setHHFamilyMemberGUID(String hHFamilyMemberGUID) {
		HHFamilyMemberGUID = hHFamilyMemberGUID;
	}

	public String getHHSurveyGUID() {
		return HHSurveyGUID;
	}

	public void setHHSurveyGUID(String hHSurveyGUID) {
		HHSurveyGUID = hHSurveyGUID;
	}

	public String getHHFamilyMemberCode() {
		return HHFamilyMemberCode;
	}

	public void setHHFamilyMemberCode(String hHFamilyMemberCode) {
		HHFamilyMemberCode = hHFamilyMemberCode;
	}

	public String getUniqueIDNumber() {
		return UniqueIDNumber;
	}

	public void setUniqueIDNumber(String uniqueIDNumber) {
		UniqueIDNumber = uniqueIDNumber;
	}

	public String getFamilyMemberName() {
		return FamilyMemberName;
	}

	public void setFamilyMemberName(String familyMemberName) {
		FamilyMemberName = familyMemberName;
	}

	public int getRelationID() {
		return RelationID;
	}

	public void setRelationID(int relationID) {
		RelationID = relationID;
	}

	public int getGenderID() {
		return GenderID;
	}

	public void setGenderID(int genderID) {
		GenderID = genderID;
	}

	public int getMaritialStatusID() {
		return MaritialStatusID;
	}

	public void setMaritialStatusID(int maritialStatusID) {
		MaritialStatusID = maritialStatusID;
	}

	public int getDOBAvailable() {
		return DOBAvailable;
	}

	public void setDOBAvailable(int dOBAvailable) {
		DOBAvailable = dOBAvailable;
	}

	public String getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public int getAprilAgeYear() {
		return AprilAgeYear;
	}

	public void setAprilAgeYear(int aprilAgeYear) {
		AprilAgeYear = aprilAgeYear;
	}

	public int getAprilAgeMonth() {
		return AprilAgeMonth;
	}

	public void setAprilAgeMonth(int aprilAgeMonth) {
		AprilAgeMonth = aprilAgeMonth;
	}

	public String getMotherGUID() {
		return MotherGUID;
	}

	public void setMotherGUID(String motherGUID) {
		MotherGUID = motherGUID;
	}

	public int getTargetID() {
		return TargetID;
	}

	public void setTargetID(int targetID) {
		TargetID = targetID;
	}

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

	public int getUploadedBy() {
		return UploadedBy;
	}

	public void setUploadedBy(int uploadedBy) {
		UploadedBy = uploadedBy;
	}

	public String getUploadedOn() {
		return UploadedOn;
	}

	public void setUploadedOn(String uploadedOn) {
		UploadedOn = uploadedOn;
	}

	public int getIsTablet() {
		return IsTablet;
	}

	public void setIsTablet(int isTablet) {
		IsTablet = isTablet;
	}

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

	public String getRegistration_Date() {
		return Registration_Date;
	}

	public void setRegistration_Date(String registration_Date) {
		Registration_Date = registration_Date;
	}

	public int getOccupation() {
		return Occupation;
	}

	public void setOccupation(int occupation) {
		Occupation = occupation;
	}

	public int getRsby_Beneficiary() {
		return Rsby_Beneficiary;
	}

	public void setRsby_Beneficiary(int rsby_Beneficiary) {
		Rsby_Beneficiary = rsby_Beneficiary;
	}

	public int getHealth_Condition() {
		return Health_Condition;
	}

	public void setHealth_Condition(int health_Condition) {
		Health_Condition = health_Condition;
	}

	

	public int getAny_PhysicalInability() {
		return Any_PhysicalInability;
	}

	public void setAny_PhysicalInability(int any_PhysicalInability) {
		Any_PhysicalInability = any_PhysicalInability;
	}

	public String getOccupation_Other() {
		return Occupation_Other;
	}

	public void setOccupation_Other(String occupation_Other) {
		Occupation_Other = occupation_Other;
	}

	
	public String getPhone_No() {
		return Phone_No;
	}

	public void setPhone_No(String phone_No) {
		Phone_No = phone_No;
	}

	public String getAny_HealthIssue_Other() {
		return Any_HealthIssue_Other;
	}

	public void setAny_HealthIssue_Other(String any_HealthIssue_Other) {
		Any_HealthIssue_Other = any_HealthIssue_Other;
	}
	

}
