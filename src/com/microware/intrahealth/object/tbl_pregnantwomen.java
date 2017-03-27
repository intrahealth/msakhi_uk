package com.microware.intrahealth.object;

public class tbl_pregnantwomen {

	private int PWID;
	private String PWGUID;
	private String HHGUID;
	private String HHFamilyMemberGUID;
	private String PWName;
	private int ANMID;
	private String PWImage;
	private int AshaID;
	private String LMPDate;
	private String EDDDate;
	private String PWRegistrationDate;
	private int Regwithin12weeks;
	private int RegweeksElaspsed;
	private String HusbandName;
	private String Husband_GUID;
	private String MobileNo;
	private String MotherMCTSID;
	private String IFSCCode;
	private String Accountno;
	private int JSYBenificiaryYN;
	private String JSYRegDate;
	private int JSYPaymentReceivedYN;
	private int Education;
	private String PWDOB;
	private int PWAgeYears;
	private String PWAgeRefDate;
	private double PWWeight;
	private int PWBloodGroup;
	private String PastIllnessYN;
	private int TotalPregnancy;
	private int LastPregnancyResult;
	private int LastPregnancyComplication;
	private int LTLPregnancyResult;
	private int LTLPregnancyomplication;
	private double PWHeight;
	private int LastPregDeliveryPlace;
	private int LasttolastPregDeliveryPlace;
	private String ExpFacilityforDelivery;
	private String ExpFacilityforDeliveryName;
	private int VDRLTestYN;
	private String VDRLResult;
	private int HIVTestYN;
	private String HIVResult;
	private String Visit1Date;
	private String Visit2Date;
	private String Visit3Date;
	private String Visit4Date;
	private int ISAbortion;
	private int AbortionFacilityType;
	private int NoofANCVisitsDone;
	private String LastANCVisitDate;
	private String TT1Date;
	private String TT2Date;
	private String AltMobileNo;
	private String TTBoosterDate;
	private String DangerSigns;
	private int RefferedYN;
	private String DeliveryDateTime;
	private int DeliveryPlace;
	private String DeliveryConductedBy;
	private int DeliveryType;
	private String DeliveryComplication;
	private int MotherDeathCause;
	private int DeliveryOutcome;
	private String DTMFacilityDischarge;
	private String PaymentRecieved;
	private int PlaceofDeath;
	private String DateofDeath;
	private String OtherPlaceofDeath;
	private String CreatedOn;
	private int CreatedBy;
	private String UpdatedOn;
	private int UpdatedBy;
	private int IsEdited;
	private int IsUploaded;
	private String AnyOtherPastIllness;
	private String AnyOtherLastPregCom;
	private String AnyOtherLTLPregCom;
	private int HighRisk;
	private int MaternalDeath;
	private int IsPregnant;
	private int BankAcc;
	private int ChildDeathCause;
	private String MotherDCOther;
	private String DeathPlaceOther;
	private int FacitylastPreg;
	private String FaciltyOtherLTL;
	private int Abortion_FacilityName;
	
	public int getAbortion_FacilityName() {
		return Abortion_FacilityName;
	}

	public void setAbortion_FacilityName(int abortion_FacilityName) {
		Abortion_FacilityName = abortion_FacilityName;
	}

	private int  FacityLTL;
	
	
	public String getAltMobileNo() {
		return AltMobileNo;
	}

	public void setAltMobileNo(String altMobileNo) {
		AltMobileNo = altMobileNo;
	}

	private String FacilityOtherLastpreg;
	
	public int getEducation() {
		return Education;
	}

	public void setEducation(int education) {
		Education = education;
	}

	public int getFacitylastPreg() {
		return FacitylastPreg;
	}

	public void setFacitylastPreg(int facitylastPreg) {
		FacitylastPreg = facitylastPreg;
	}

	public String getFaciltyOtherLTL() {
		return FaciltyOtherLTL;
	}

	public void setFaciltyOtherLTL(String faciltyOtherLTL) {
		FaciltyOtherLTL = faciltyOtherLTL;
	}

	public int getFacityLTL() {
		return FacityLTL;
	}

	public void setFacityLTL(int facityLTL) {
		FacityLTL = facityLTL;
	}

	public String getFacilityOtherLastpreg() {
		return FacilityOtherLastpreg;
	}

	public void setFacilityOtherLastpreg(String facilityOtherLastpreg) {
		FacilityOtherLastpreg = facilityOtherLastpreg;
	}

	public String getMotherDCOther() {
		return MotherDCOther;
	}

	public void setMotherDCOther(String motherDCOther) {
		MotherDCOther = motherDCOther;
	}

	


	public String getDeathPlaceOther() {
		return DeathPlaceOther;
	}

	public void setDeathPlaceOther(String deathPlaceOther) {
		DeathPlaceOther = deathPlaceOther;
	}

	public int getChildDeathCause() {
		return ChildDeathCause;
	}

	public void setChildDeathCause(int childDeathCause) {
		ChildDeathCause = childDeathCause;
	}

	public int getBankAcc() {
		return BankAcc;
	}

	public void setBankAcc(int bankAcc) {
		BankAcc = bankAcc;
	}

	public int getIsPregnant() {
		return IsPregnant;
	}

	public void setIsPregnant(int isPregnant) {
		IsPregnant = isPregnant;
	}

	public int getMaternalDeath() {
		return MaternalDeath;
	}

	public void setMaternalDeath(int maternalDeath) {
		MaternalDeath = maternalDeath;
	}

	public int getHighRisk() {
		return HighRisk;
	}

	public void setHighRisk(int highRisk) {
		HighRisk = highRisk;
	}

	public String getAnyOtherPastIllness() {
		return AnyOtherPastIllness;
	}

	public void setAnyOtherPastIllness(String anyOtherPastIllness) {
		AnyOtherPastIllness = anyOtherPastIllness;
	}

	public String getAnyOtherLastPregCom() {
		return AnyOtherLastPregCom;
	}

	public void setAnyOtherLastPregCom(String anyOtherLastPregCom) {
		AnyOtherLastPregCom = anyOtherLastPregCom;
	}

	public String getAnyOtherLTLPregCom() {
		return AnyOtherLTLPregCom;
	}

	public void setAnyOtherLTLPregCom(String anyOtherLTLPregCom) {
		AnyOtherLTLPregCom = anyOtherLTLPregCom;
	}

	public String getDeliveryDateTime() {
		return DeliveryDateTime;
	}

	public void setDeliveryDateTime(String deliveryDateTime) {
		DeliveryDateTime = deliveryDateTime;
	}

	public String getCreatedOn() {
		return CreatedOn;
	}

	public void setCreatedOn(String createdOn) {
		CreatedOn = createdOn;
	}

	public int getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(int createdBy) {
		CreatedBy = createdBy;
	}

	public String getUpdatedOn() {
		return UpdatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		UpdatedOn = updatedOn;
	}

	public int getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		UpdatedBy = updatedBy;
	}

	public int getPWID() {
		return PWID;
	}

	public void setPWID(int pWID) {
		PWID = pWID;
	}

	public int getIsEdited() {
		return IsEdited;
	}

	public void setIsEdited(int isEdited) {
		IsEdited = isEdited;
	}

	public int getIsUploaded() {
		return IsUploaded;
	}

	public void setIsUploaded(int isUploaded) {
		IsUploaded = isUploaded;
	}

	public int getMotherDeathCause() {
		return MotherDeathCause;
	}

	public void setMotherDeathCause(int motherDeathCause) {
		MotherDeathCause = motherDeathCause;
	}

	public int getPlaceofDeath() {
		return PlaceofDeath;
	}

	public void setPlaceofDeath(int placeofDeath) {
		PlaceofDeath = placeofDeath;
	}

	public String getDateofDeath() {
		return DateofDeath;
	}

	public void setDateofDeath(String dateofDeath) {
		DateofDeath = dateofDeath;
	}

	public String getOtherPlaceofDeath() {
		return OtherPlaceofDeath;
	}

	public void setOtherPlaceofDeath(String otherPlaceofDeath) {
		OtherPlaceofDeath = otherPlaceofDeath;
	}

	public String getPWImage() {
		return PWImage;
	}

	public void setPWImage(String pWImage) {
		PWImage = pWImage;
	}

	public double getPWWeight() {
		return PWWeight;
	}

	public void setPWWeight(double pWWeight) {
		PWWeight = pWWeight;
	}

	public double getPWHeight() {
		return PWHeight;
	}

	public void setPWHeight(double pWHeight) {
		PWHeight = pWHeight;
	}

	public String getIFSCCode() {
		return IFSCCode;
	}

	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}

	public String getAccountno() {
		return Accountno;
	}

	public void setAccountno(String accountno) {
		Accountno = accountno;
	}

	public int getLastPregDeliveryPlace() {
		return LastPregDeliveryPlace;
	}

	public void setLastPregDeliveryPlace(int lastPregDeliveryPlace) {
		LastPregDeliveryPlace = lastPregDeliveryPlace;
	}

	public int getLasttolastPregDeliveryPlace() {
		return LasttolastPregDeliveryPlace;
	}

	public void setLasttolastPregDeliveryPlace(int lasttolastPregDeliveryPlace) {
		LasttolastPregDeliveryPlace = lasttolastPregDeliveryPlace;
	}

	public String getPWGUID() {
		return PWGUID;
	}

	public void setPWGUID(String pWGUID) {
		PWGUID = pWGUID;
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

	public String getPWName() {
		return PWName;
	}

	public void setPWName(String pWName) {
		PWName = pWName;
	}

	public int getANMID() {
		return ANMID;
	}

	public void setANMID(int aNMID) {
		ANMID = aNMID;
	}

	public int getAshaID() {
		return AshaID;
	}

	public void setAshaID(int ashaID) {
		AshaID = ashaID;
	}

	public String getLMPDate() {
		return LMPDate;
	}

	public void setLMPDate(String lMPDate) {
		LMPDate = lMPDate;
	}

	public String getEDDDate() {
		return EDDDate;
	}

	public void setEDDDate(String eDDDate) {
		EDDDate = eDDDate;
	}

	public String getPWRegistrationDate() {
		return PWRegistrationDate;
	}

	public void setPWRegistrationDate(String pWRegistrationDate) {
		PWRegistrationDate = pWRegistrationDate;
	}

	public int getRegwithin12weeks() {
		return Regwithin12weeks;
	}

	public void setRegwithin12weeks(int regwithin12weeks) {
		Regwithin12weeks = regwithin12weeks;
	}

	public int getRegweeksElaspsed() {
		return RegweeksElaspsed;
	}

	public void setRegweeksElaspsed(int regweeksElaspsed) {
		RegweeksElaspsed = regweeksElaspsed;
	}

	public String getHusbandName() {
		return HusbandName;
	}

	public void setHusbandName(String husbandName) {
		HusbandName = husbandName;
	}

	public String getHusband_GUID() {
		return Husband_GUID;
	}

	public void setHusband_GUID(String husband_GUID) {
		Husband_GUID = husband_GUID;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public String getMotherMCTSID() {
		return MotherMCTSID;
	}

	public void setMotherMCTSID(String motherMCTSID) {
		MotherMCTSID = motherMCTSID;
	}

	public int getJSYBenificiaryYN() {
		return JSYBenificiaryYN;
	}

	public void setJSYBenificiaryYN(int jSYBenificiaryYN) {
		JSYBenificiaryYN = jSYBenificiaryYN;
	}

	public String getJSYRegDate() {
		return JSYRegDate;
	}

	public void setJSYRegDate(String jSYRegDate) {
		JSYRegDate = jSYRegDate;
	}

	public int getJSYPaymentReceivedYN() {
		return JSYPaymentReceivedYN;
	}

	public void setJSYPaymentReceivedYN(int jSYPaymentReceivedYN) {
		JSYPaymentReceivedYN = jSYPaymentReceivedYN;
	}

	public String getPWDOB() {
		return PWDOB;
	}

	public void setPWDOB(String pWDOB) {
		PWDOB = pWDOB;
	}

	public int getPWAgeYears() {
		return PWAgeYears;
	}

	public void setPWAgeYears(int pWAgeYears) {
		PWAgeYears = pWAgeYears;
	}

	public String getPWAgeRefDate() {
		return PWAgeRefDate;
	}

	public void setPWAgeRefDate(String pWAgeRefDate) {
		PWAgeRefDate = pWAgeRefDate;
	}

	public int getPWBloodGroup() {
		return PWBloodGroup;
	}

	public void setPWBloodGroup(int pWBloodGroup) {
		PWBloodGroup = pWBloodGroup;
	}

	

	public String getPastIllnessYN() {
		return PastIllnessYN;
	}

	public void setPastIllnessYN(String pastIllnessYN) {
		PastIllnessYN = pastIllnessYN;
	}

	public int getTotalPregnancy() {
		return TotalPregnancy;
	}

	public void setTotalPregnancy(int totalPregnancy) {
		TotalPregnancy = totalPregnancy;
	}

	public int getLastPregnancyResult() {
		return LastPregnancyResult;
	}

	public void setLastPregnancyResult(int lastPregnancyResult) {
		LastPregnancyResult = lastPregnancyResult;
	}

	public int getLastPregnancyComplication() {
		return LastPregnancyComplication;
	}

	public void setLastPregnancyComplication(int lastPregnancyComplication) {
		LastPregnancyComplication = lastPregnancyComplication;
	}

	public int getLTLPregnancyResult() {
		return LTLPregnancyResult;
	}

	public void setLTLPregnancyResult(int lTLPregnancyResult) {
		LTLPregnancyResult = lTLPregnancyResult;
	}

	public int getLTLPregnancyomplication() {
		return LTLPregnancyomplication;
	}

	public void setLTLPregnancyomplication(int lTLPregnancyomplication) {
		LTLPregnancyomplication = lTLPregnancyomplication;
	}

	public String getExpFacilityforDelivery() {
		return ExpFacilityforDelivery;
	}

	public void setExpFacilityforDelivery(String expFacilityforDelivery) {
		ExpFacilityforDelivery = expFacilityforDelivery;
	}

	public String getExpFacilityforDeliveryName() {
		return ExpFacilityforDeliveryName;
	}

	public void setExpFacilityforDeliveryName(String expFacilityforDeliveryName) {
		ExpFacilityforDeliveryName = expFacilityforDeliveryName;
	}

	public int getVDRLTestYN() {
		return VDRLTestYN;
	}

	public void setVDRLTestYN(int vDRLTestYN) {
		VDRLTestYN = vDRLTestYN;
	}

	public String getVDRLResult() {
		return VDRLResult;
	}

	public void setVDRLResult(String vDRLResult) {
		VDRLResult = vDRLResult;
	}

	public int getHIVTestYN() {
		return HIVTestYN;
	}

	public void setHIVTestYN(int hIVTestYN) {
		HIVTestYN = hIVTestYN;
	}

	public String getHIVResult() {
		return HIVResult;
	}

	public void setHIVResult(String hIVResult) {
		HIVResult = hIVResult;
	}

	public String getVisit1Date() {
		return Visit1Date;
	}

	public void setVisit1Date(String visit1Date) {
		Visit1Date = visit1Date;
	}

	public String getVisit2Date() {
		return Visit2Date;
	}

	public void setVisit2Date(String visit2Date) {
		Visit2Date = visit2Date;
	}

	public String getVisit3Date() {
		return Visit3Date;
	}

	public void setVisit3Date(String visit3Date) {
		Visit3Date = visit3Date;
	}

	public String getVisit4Date() {
		return Visit4Date;
	}

	public void setVisit4Date(String visit4Date) {
		Visit4Date = visit4Date;
	}

	public int getISAbortion() {
		return ISAbortion;
	}

	public void setISAbortion(int iSAbortion) {
		ISAbortion = iSAbortion;
	}

	public int getAbortionFacilityType() {
		return AbortionFacilityType;
	}

	public void setAbortionFacilityType(int abortionFacilityType) {
		AbortionFacilityType = abortionFacilityType;
	}

	public int getNoofANCVisitsDone() {
		return NoofANCVisitsDone;
	}

	public void setNoofANCVisitsDone(int noofANCVisitsDone) {
		NoofANCVisitsDone = noofANCVisitsDone;
	}

	public String getLastANCVisitDate() {
		return LastANCVisitDate;
	}

	public void setLastANCVisitDate(String lastANCVisitDate) {
		LastANCVisitDate = lastANCVisitDate;
	}

	public String getTT1Date() {
		return TT1Date;
	}

	public void setTT1Date(String tT1Date) {
		TT1Date = tT1Date;
	}

	public String getTT2Date() {
		return TT2Date;
	}

	public void setTT2Date(String tT2Date) {
		TT2Date = tT2Date;
	}

	public String getTTBoosterDate() {
		return TTBoosterDate;
	}

	public void setTTBoosterDate(String tTBoosterDate) {
		TTBoosterDate = tTBoosterDate;
	}

	public String getDangerSigns() {
		return DangerSigns;
	}

	public void setDangerSigns(String dangerSigns) {
		DangerSigns = dangerSigns;
	}

	public int getRefferedYN() {
		return RefferedYN;
	}

	public void setRefferedYN(int refferedYN) {
		RefferedYN = refferedYN;
	}

	public int getDeliveryPlace() {
		return DeliveryPlace;
	}

	public void setDeliveryPlace(int deliveryPlace) {
		DeliveryPlace = deliveryPlace;
	}

	public String getDeliveryConductedBy() {
		return DeliveryConductedBy;
	}

	public void setDeliveryConductedBy(String deliveryConductedBy) {
		DeliveryConductedBy = deliveryConductedBy;
	}

	public int getDeliveryType() {
		return DeliveryType;
	}

	public void setDeliveryType(int deliveryType) {
		DeliveryType = deliveryType;
	}

	public String getDeliveryComplication() {
		return DeliveryComplication;
	}

	public void setDeliveryComplication(String deliveryComplication) {
		DeliveryComplication = deliveryComplication;
	}

	public int getDeliveryOutcome() {
		return DeliveryOutcome;
	}

	public void setDeliveryOutcome(int deliveryOutcome) {
		DeliveryOutcome = deliveryOutcome;
	}

	public String getDTMFacilityDischarge() {
		return DTMFacilityDischarge;
	}

	public void setDTMFacilityDischarge(String dTMFacilityDischarge) {
		DTMFacilityDischarge = dTMFacilityDischarge;
	}

	public String getPaymentRecieved() {
		return PaymentRecieved;
	}

	public void setPaymentRecieved(String paymentRecieved) {
		PaymentRecieved = paymentRecieved;
	}

}
