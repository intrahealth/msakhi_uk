package com.microware.intrahealth.object;

public class tblChild {

	private String pw_GUID;
	private String HHGUID;
	private String HHFamilyMemberGUID;
	private int Child_ID;
	private String ChildGUID;
	private String MotherGUID;
	private String Date_Of_Registration;
	private String Child_dob;
	private String Birth_time;
	private int Gender;
	private float Wt_of_child;
	private int place_of_birth;
	private String preTerm_fullTerm;
	private int mother_status;
	private int child_status;
	private String mother_death_dt;
	private String child_death_dt;
	private String child_mcts_id;
	private String child_name;
	private String cried_after_birth;
	private int breastfeeding_within1H;
	private String Exclusive_BF;
	private String complementry_BF;
	private String bcg;
	private String opv1;
	private String dpt1;
	private String hepb1;
	private String opv2;
	private String dpt2;
	private String hepb2;
	private String opv3;
	private String dpt3;
	private String hepb3;
	private String measeals;
	private String vitaminA;
	private String created_on;
	private int created_by;
	private String modified_on;
	private int modified_by;
	private int IsEdited;
	private int FacilityType;
	private String Facility;
	private int AshaID;
	private int ANMID;
	//add
	private String opv4="";
	private String hepb4="";
	private String Pentavalent1="";
	private String Pentavalent2="";
	private String Pentavalent3="";
	private String IPV="";
	private String DPTBooster="";
	private String OPVBooster="";
	private String MeaslesTwoDose="";
	private String VitaminAtwo="";
	private String DPTBoostertwo="";
	private String ChildTT="";
	//add jitendra
	private String JEVaccine1;
	private String JEVaccine2;
	private String VitaminA3;
	private String VitaminA4;
	private String VitaminA5;
	private String VitaminA6;
	private String VitaminA7;
	private String VitaminA8;
	private String VitaminA9;

	private String TT2;

	
	public String getTT2() {
		return TT2;
	}

	public void setTT2(String tT2) {
		TT2 = tT2;
	}

	public String getJEVaccine1() {
		return JEVaccine1;
	}

	public void setJEVaccine1(String jEVaccine1) {
		JEVaccine1 = jEVaccine1;
	}

	public String getJEVaccine2() {
		return JEVaccine2;
	}

	public void setJEVaccine2(String jEVaccine2) {
		JEVaccine2 = jEVaccine2;
	}

	public String getVitaminA3() {
		return VitaminA3;
	}

	public void setVitaminA3(String vitaminA3) {
		VitaminA3 = vitaminA3;
	}

	public String getVitaminA4() {
		return VitaminA4;
	}

	public void setVitaminA4(String vitaminA4) {
		VitaminA4 = vitaminA4;
	}

	public String getVitaminA5() {
		return VitaminA5;
	}

	public void setVitaminA5(String vitaminA5) {
		VitaminA5 = vitaminA5;
	}

	public String getVitaminA6() {
		return VitaminA6;
	}

	public void setVitaminA6(String vitaminA6) {
		VitaminA6 = vitaminA6;
	}

	public String getVitaminA7() {
		return VitaminA7;
	}

	public void setVitaminA7(String vitaminA7) {
		VitaminA7 = vitaminA7;
	}

	public String getVitaminA8() {
		return VitaminA8;
	}

	public void setVitaminA8(String vitaminA8) {
		VitaminA8 = vitaminA8;
	}

	public String getVitaminA9() {
		return VitaminA9;
	}

	public void setVitaminA9(String vitaminA9) {
		VitaminA9 = vitaminA9;
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

	public String getOpv4() {
		return opv4;
	}

	public void setOpv4(String opv4) {
		this.opv4 = opv4;
	}

	public String getHepb4() {
		return hepb4;
	}

	public void setHepb4(String hepb4) {
		this.hepb4 = hepb4;
	}

	public String getPentavalent1() {
		return Pentavalent1;
	}

	public void setPentavalent1(String pentavalent1) {
		Pentavalent1 = pentavalent1;
	}

	public String getPentavalent2() {
		return Pentavalent2;
	}

	public void setPentavalent2(String pentavalent2) {
		Pentavalent2 = pentavalent2;
	}

	public String getPentavalent3() {
		return Pentavalent3;
	}

	public void setPentavalent3(String pentavalent3) {
		Pentavalent3 = pentavalent3;
	}

	public String getIPV() {
		return IPV;
	}

	public void setIPV(String iPV) {
		IPV = iPV;
	}

	public String getDPTBooster() {
		return DPTBooster;
	}

	public void setDPTBooster(String dPTBooster) {
		DPTBooster = dPTBooster;
	}

	public String getOPVBooster() {
		return OPVBooster;
	}

	public void setOPVBooster(String oPVBooster) {
		OPVBooster = oPVBooster;
	}

	public String getMeaslesTwoDose() {
		return MeaslesTwoDose;
	}

	public void setMeaslesTwoDose(String measlesTwoDose) {
		MeaslesTwoDose = measlesTwoDose;
	}

	public String getVitaminAtwo() {
		return VitaminAtwo;
	}

	public void setVitaminAtwo(String vitaminAtwo) {
		VitaminAtwo = vitaminAtwo;
	}

	public String getDPTBoostertwo() {
		return DPTBoostertwo;
	}

	public void setDPTBoostertwo(String dPTBoostertwo) {
		DPTBoostertwo = dPTBoostertwo;
	}

	public String getChildTT() {
		return ChildTT;
	}

	public void setChildTT(String childTT) {
		ChildTT = childTT;
	}

	public int getFacilityType() {
		return FacilityType;
	}

	public void setFacilityType(int facilityType) {
		FacilityType = facilityType;
	}

	
	public String getFacility() {
		return Facility;
	}

	public void setFacility(String facility) {
		Facility = facility;
	}

	public int getIsEdited() {
		return IsEdited;
	}

	public void setIsEdited(int isEdited) {
		IsEdited = isEdited;
	}

	private int IsDeleted;

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}

	public int getPlace_of_birth() {
		return place_of_birth;
	}

	public void setPlace_of_birth(int place_of_birth) {
		this.place_of_birth = place_of_birth;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

	public String getPw_GUID() {
		return pw_GUID;
	}

	public void setPw_GUID(String pw_GUID) {
		this.pw_GUID = pw_GUID;
	}

	public String getHHGUID() {
		return HHGUID;
	}

	public void setHHGUID(String hHGUID) {
		HHGUID = hHGUID;
	}

	public String getHHFamilyMemberGUID() {
		return HHFamilyMemberGUID;
	}

	public void setHHFamilyMemberGUID(String hHFamilyMemberGUID) {
		HHFamilyMemberGUID = hHFamilyMemberGUID;
	}

	public int getChild_ID() {
		return Child_ID;
	}

	public void setChild_ID(int child_ID) {
		Child_ID = child_ID;
	}

	public String getChildGUID() {
		return ChildGUID;
	}

	public void setChildGUID(String childGUID) {
		ChildGUID = childGUID;
	}

	public String getMotherGUID() {
		return MotherGUID;
	}

	public void setMotherGUID(String motherGUID) {
		MotherGUID = motherGUID;
	}

	public String getDate_Of_Registration() {
		return Date_Of_Registration;
	}

	public void setDate_Of_Registration(String date_Of_Registration) {
		Date_Of_Registration = date_Of_Registration;
	}

	public String getChild_dob() {
		return Child_dob;
	}

	public void setChild_dob(String child_dob) {
		Child_dob = child_dob;
	}

	public String getBirth_time() {
		return Birth_time;
	}

	public void setBirth_time(String birth_time) {
		Birth_time = birth_time;
	}

	public float getWt_of_child() {
		return Wt_of_child;
	}

	public void setWt_of_child(float wt_of_child) {
		Wt_of_child = wt_of_child;
	}

	public String getPreTerm_fullTerm() {
		return preTerm_fullTerm;
	}

	public void setPreTerm_fullTerm(String preTerm_fullTerm) {
		this.preTerm_fullTerm = preTerm_fullTerm;
	}

	public int getMother_status() {
		return mother_status;
	}

	public void setMother_status(int mother_status) {
		this.mother_status = mother_status;
	}

	public int getChild_status() {
		return child_status;
	}

	public void setChild_status(int child_status) {
		this.child_status = child_status;
	}

	public String getMother_death_dt() {
		return mother_death_dt;
	}

	public void setMother_death_dt(String mother_death_dt) {
		this.mother_death_dt = mother_death_dt;
	}

	public String getChild_death_dt() {
		return child_death_dt;
	}

	public void setChild_death_dt(String child_death_dt) {
		this.child_death_dt = child_death_dt;
	}

	public String getChild_mcts_id() {
		return child_mcts_id;
	}

	public void setChild_mcts_id(String child_mcts_id) {
		this.child_mcts_id = child_mcts_id;
	}

	public String getChild_name() {
		return child_name;
	}

	public void setChild_name(String child_name) {
		this.child_name = child_name;
	}

	public String getCried_after_birth() {
		return cried_after_birth;
	}

	public void setCried_after_birth(String cried_after_birth) {
		this.cried_after_birth = cried_after_birth;
	}

	public int getBreastfeeding_within1H() {
		return breastfeeding_within1H;
	}

	public void setBreastfeeding_within1H(int breastfeeding_within1H) {
		this.breastfeeding_within1H = breastfeeding_within1H;
	}

	public String getExclusive_BF() {
		return Exclusive_BF;
	}

	public void setExclusive_BF(String exclusive_BF) {
		Exclusive_BF = exclusive_BF;
	}

	public String getComplementry_BF() {
		return complementry_BF;
	}

	public void setComplementry_BF(String complementry_BF) {
		this.complementry_BF = complementry_BF;
	}

	public String getBcg() {
		return bcg;
	}

	public void setBcg(String bcg) {
		this.bcg = bcg;
	}

	public String getOpv1() {
		return opv1;
	}

	public void setOpv1(String opv1) {
		this.opv1 = opv1;
	}

	public String getDpt1() {
		return dpt1;
	}

	public void setDpt1(String dpt1) {
		this.dpt1 = dpt1;
	}

	public String getHepb1() {
		return hepb1;
	}

	public void setHepb1(String hepb1) {
		this.hepb1 = hepb1;
	}

	public String getOpv2() {
		return opv2;
	}

	public void setOpv2(String opv2) {
		this.opv2 = opv2;
	}

	public String getDpt2() {
		return dpt2;
	}

	public void setDpt2(String dpt2) {
		this.dpt2 = dpt2;
	}

	public String getHepb2() {
		return hepb2;
	}

	public void setHepb2(String hepb2) {
		this.hepb2 = hepb2;
	}

	public String getOpv3() {
		return opv3;
	}

	public void setOpv3(String opv3) {
		this.opv3 = opv3;
	}

	public String getDpt3() {
		return dpt3;
	}

	public void setDpt3(String dpt3) {
		this.dpt3 = dpt3;
	}

	public String getHepb3() {
		return hepb3;
	}

	public void setHepb3(String hepb3) {
		this.hepb3 = hepb3;
	}

	public String getMeaseals() {
		return measeals;
	}

	public void setMeaseals(String measeals) {
		this.measeals = measeals;
	}

	public String getVitaminA() {
		return vitaminA;
	}

	public void setVitaminA(String vitaminA) {
		this.vitaminA = vitaminA;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public String getModified_on() {
		return modified_on;
	}

	public void setModified_on(String modified_on) {
		this.modified_on = modified_on;
	}

	public int getModified_by() {
		return modified_by;
	}

	public void setModified_by(int modified_by) {
		this.modified_by = modified_by;
	}

}
