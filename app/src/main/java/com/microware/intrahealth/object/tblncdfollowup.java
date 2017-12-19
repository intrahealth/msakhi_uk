package com.microware.intrahealth.object;

public class tblncdfollowup {
	private int Uid;
	private String RegistrationNo;
	private String GuardianName;
	private String RegistrationDate;
	private int BlockID;
	private int VillageID;
	private String HHFamilyMemberGUID;
	private String HHSurveyGUID;
	private String NCDScreeningGUID;
	private String NCDFollowupGUID;
	private String Name;
	private String NCDDiagnosis;
	private String RBS;
	private String BP;
	private String GFRecieved;
	private String GFother;
	private String GFProblem;
	private String GFProblemother;
	private String GFDiagnosisReason;
	private String GFDiagnosisReasonoth;
	private String GForPFDiagnosisReason;
	private String GForPFDiagnosisReasonoth;
	private int NCDMedicine;
	private int NCDMedicineYes;
	private String NCDMedicineYesoth;
	private String NCDMedicineNo;
	private String NCDMedicineNooth;
	private int CounsellingTips;
	private String Suggestion;
	private int IsEdited;
	private int CreatedBy;
	private String CreatedOn;
	private int UpdatedBy;
	private String UpdatedOn;
	private int AshaID;
	private int ANMID;
	private int Referal;
	private String MedicineGiven="";
	private int MedicineContinue=0;
	private int MedicineWhere=0;
	private int ReferralYes=0;
	private String MedicineWhereOther="";

	public String getMedicineGiven() {
		return MedicineGiven;
	}

	public void setMedicineGiven(String medicineGiven) {
		MedicineGiven = medicineGiven;
	}

	public int getMedicineContinue() {
		return MedicineContinue;
	}

	public void setMedicineContinue(int medicineContinue) {
		MedicineContinue = medicineContinue;
	}

	public int getMedicineWhere() {
		return MedicineWhere;
	}

	public void setMedicineWhere(int medicineWhere) {
		MedicineWhere = medicineWhere;
	}

	public int getReferralYes() {
		return ReferralYes;
	}

	public void setReferralYes(int referralYes) {
		ReferralYes = referralYes;
	}

	public String getMedicineWhereOther() {
		return MedicineWhereOther;
	}

	public void setMedicineWhereOther(String medicineWhereOther) {
		MedicineWhereOther = medicineWhereOther;
	}

	public int getReferal() {
		return Referal;
	}

	public void setReferal(int referal) {
		Referal = referal;
	}

	public int getUid() {
		return Uid;
	}

	public void setUid(int uid) {
		Uid = uid;
	}

	public String getRegistrationNo() {
		return RegistrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		RegistrationNo = registrationNo;
	}

	public String getGuardianName() {
		return GuardianName;
	}

	public void setGuardianName(String guardianName) {
		GuardianName = guardianName;
	}

	public String getRegistrationDate() {
		return RegistrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		RegistrationDate = registrationDate;
	}

	

	public int getBlockID() {
		return BlockID;
	}

	public void setBlockID(int blockID) {
		BlockID = blockID;
	}

	public int getVillageID() {
		return VillageID;
	}

	public void setVillageID(int villageID) {
		VillageID = villageID;
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

	public String getNCDFollowupGUID() {
		return NCDFollowupGUID;
	}

	public void setNCDFollowupGUID(String nCDFollowupGUID) {
		NCDFollowupGUID = nCDFollowupGUID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getNCDDiagnosis() {
		return NCDDiagnosis;
	}

	public void setNCDDiagnosis(String nCDDiagnosis) {
		NCDDiagnosis = nCDDiagnosis;
	}

	public String getRBS() {
		return RBS;
	}

	public void setRBS(String rBS) {
		RBS = rBS;
	}

	public String getBP() {
		return BP;
	}

	public void setBP(String bP) {
		BP = bP;
	}

	public String getNCDScreeningGUID() {
		return NCDScreeningGUID;
	}

	public void setNCDScreeningGUID(String nCDScreeningGUID) {
		NCDScreeningGUID = nCDScreeningGUID;
	}

	public String getGFRecieved() {
		return GFRecieved;
	}

	public void setGFRecieved(String gFRecieved) {
		GFRecieved = gFRecieved;
	}

	public String getGFother() {
		return GFother;
	}

	public void setGFother(String gFother) {
		GFother = gFother;
	}

	public String getGFProblem() {
		return GFProblem;
	}

	public void setGFProblem(String gFProblem) {
		GFProblem = gFProblem;
	}

	public String getGFProblemother() {
		return GFProblemother;
	}

	public void setGFProblemother(String gFProblemother) {
		GFProblemother = gFProblemother;
	}

	public String getGFDiagnosisReason() {
		return GFDiagnosisReason;
	}

	public void setGFDiagnosisReason(String gFDiagnosisReason) {
		GFDiagnosisReason = gFDiagnosisReason;
	}

	public String getGFDiagnosisReasonoth() {
		return GFDiagnosisReasonoth;
	}

	public void setGFDiagnosisReasonoth(String gFDiagnosisReasonoth) {
		GFDiagnosisReasonoth = gFDiagnosisReasonoth;
	}

	public String getGForPFDiagnosisReason() {
		return GForPFDiagnosisReason;
	}

	public void setGForPFDiagnosisReason(String gForPFDiagnosisReason) {
		GForPFDiagnosisReason = gForPFDiagnosisReason;
	}

	public String getGForPFDiagnosisReasonoth() {
		return GForPFDiagnosisReasonoth;
	}

	public void setGForPFDiagnosisReasonoth(String gForPFDiagnosisReasonoth) {
		GForPFDiagnosisReasonoth = gForPFDiagnosisReasonoth;
	}

	public int getNCDMedicine() {
		return NCDMedicine;
	}

	public void setNCDMedicine(int nCDMedicine) {
		NCDMedicine = nCDMedicine;
	}

	public int getNCDMedicineYes() {
		return NCDMedicineYes;
	}

	public void setNCDMedicineYes(int nCDMedicineYes) {
		NCDMedicineYes = nCDMedicineYes;
	}

	public String getNCDMedicineYesoth() {
		return NCDMedicineYesoth;
	}

	public void setNCDMedicineYesoth(String nCDMedicineYesoth) {
		NCDMedicineYesoth = nCDMedicineYesoth;
	}

	public String getNCDMedicineNo() {
		return NCDMedicineNo;
	}

	public void setNCDMedicineNo(String nCDMedicineNo) {
		NCDMedicineNo = nCDMedicineNo;
	}

	public String getNCDMedicineNooth() {
		return NCDMedicineNooth;
	}

	public void setNCDMedicineNooth(String nCDMedicineNooth) {
		NCDMedicineNooth = nCDMedicineNooth;
	}

	public int getCounsellingTips() {
		return CounsellingTips;
	}

	public void setCounsellingTips(int counsellingTips) {
		CounsellingTips = counsellingTips;
	}

	public String getSuggestion() {
		return Suggestion;
	}

	public void setSuggestion(String suggestion) {
		Suggestion = suggestion;
	}

	public int getIsEdited() {
		return IsEdited;
	}

	public void setIsEdited(int isEdited) {
		IsEdited = isEdited;
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

}
