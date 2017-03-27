package com.microware.intrahealth;

import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;

public class Global extends Application {

	private int Language;
	private String TokenCode;
	private String sGlobalANMName;
	private String GlobalHHSurveyGUID;
	private String DateMonth;
	private int iCurrentActiveTab;
	private String sGlobalUserName;
	private String sGlobalPassword;
	private String sGlobalUserID;
	private int iGlobalLangID;
	private int iGlobalEnableFragment;
	public int iGlobalGenderID;
	private String sGlobalCurrentVisitDate;
	private String GlobalHHFamilyMemberGUID;
	private String sGlobalAshaName;
	private String sGlobalLongitude;
	private String sGlobalLatitude;
	private String sGlobalLocation;
	private String sGlobalHusbandName;
	private String sGlobalWomenName;
	private String sGlobalPWGUID;
	private String sGlobalANCVisitGUID;
	private String sGlobalChildGUID;
	private String sGlobalSpouseGUID;
	private String ImmunizationGUID;
	private String sGlobalLMPDate;
	private String sPncGUID;
	private int visitno;
	private int HishRisk;
	private int iGlobalRoleID;
	private String spinPastIllness;
	private String sGlobalANMCODE;
	private String sGlobalAshaCode;
	private String MigrationGUID;
	private String anmidasAnmCode;
	private String StateCode;
	private String StateName;
	private String VHND_Composite_Flag;
	private String VHND_ID;
	private int ButtonclickCount;

	private String VHND_Date;
	private String VHND_VillageID;
	private String VHND_MonthField;

	public String getStateCode() {
		return StateCode;
	}

	public void setStateCode(String stateCode) {
		StateCode = stateCode;
	}

	public String getStateName() {
		return StateName;
	}

	public void setStateName(String stateName) {
		StateName = stateName;
	}

	public String getVHND_Composite_Flag() {
		return VHND_Composite_Flag;
	}

	public void setVHND_Composite_Flag(String vHND_Composite_Flag) {
		VHND_Composite_Flag = vHND_Composite_Flag;
	}

	public String getVHND_ID() {
		return VHND_ID;
	}

	public void setVHND_ID(String vHND_ID) {
		VHND_ID = vHND_ID;
	}

	public int getButtonclickCount() {
		return ButtonclickCount;
	}

	public void setButtonclickCount(int buttonclickCount) {
		ButtonclickCount = buttonclickCount;
	}

	public String getAnmidasAnmCode() {
		return anmidasAnmCode;
	}

	public void setAnmidasAnmCode(String anmidasAnmCode) {
		this.anmidasAnmCode = anmidasAnmCode;
	}

	public String getMigrationGUID() {
		return MigrationGUID;
	}

	public void setMigrationGUID(String migrationGUID) {
		MigrationGUID = migrationGUID;
	}

	public String getsGlobalANMName() {
		return sGlobalANMName;
	}

	public void setsGlobalANMName(String sGlobalANMName) {
		this.sGlobalANMName = sGlobalANMName;
	}

	public int getiGlobalRoleID() {
		return iGlobalRoleID;
	}

	public void setiGlobalRoleID(int iGlobalRoleID) {
		this.iGlobalRoleID = iGlobalRoleID;
	}

	public String getsGlobalANMCODE() {
		return sGlobalANMCODE;
	}

	public void setsGlobalANMCODE(String sGlobalANMCODE) {
		this.sGlobalANMCODE = sGlobalANMCODE;
	}

	public String getsGlobalAshaCode() {
		return sGlobalAshaCode;
	}

	public void setsGlobalAshaCode(String sGlobalAshaCode) {
		this.sGlobalAshaCode = sGlobalAshaCode;
	}

	public String getDateMonth() {
		return DateMonth;
	}

	public void setDateMonth(String dateMonth) {
		DateMonth = dateMonth;
	}

	public String getImmunizationGUID() {
		return ImmunizationGUID;
	}

	public void setImmunizationGUID(String immunizationGUID) {
		ImmunizationGUID = immunizationGUID;
	}

	public String getsGlobalSpouseGUID() {
		return sGlobalSpouseGUID;
	}

	public void setsGlobalSpouseGUID(String sGlobalSpouseGUID) {
		this.sGlobalSpouseGUID = sGlobalSpouseGUID;
	}

	public String getSpinPastIllness() {
		return spinPastIllness;
	}

	public void setSpinPastIllness(String spinPastIllness) {
		this.spinPastIllness = spinPastIllness;
	}

	public String getsPncGUID() {
		return sPncGUID;
	}

	public void setsPncGUID(String sPncGUID) {
		this.sPncGUID = sPncGUID;
	}

	public int getHishRisk() {
		return HishRisk;
	}

	public void setHishRisk(int hishRisk) {
		HishRisk = hishRisk;
	}

	public String getsGlobalLMPDate() {
		return sGlobalLMPDate;
	}

	public void setsGlobalLMPDate(String sGlobalLMPDate) {
		this.sGlobalLMPDate = sGlobalLMPDate;
	}

	public int getVisitno() {
		return visitno;
	}

	public void setVisitno(int visitno) {
		this.visitno = visitno;
	}

	public String getsGlobalChildGUID() {
		return sGlobalChildGUID;
	}

	public void setsGlobalChildGUID(String sGlobalChildGUID) {
		this.sGlobalChildGUID = sGlobalChildGUID;
	}

	public String getsGlobalANCVisitGUID() {
		return sGlobalANCVisitGUID;
	}

	public void setsGlobalANCVisitGUID(String sGlobalANCVisitGUID) {
		this.sGlobalANCVisitGUID = sGlobalANCVisitGUID;
	}

	public String getsGlobalPWGUID() {
		return sGlobalPWGUID;
	}

	public void setsGlobalPWGUID(String sGlobalPWGUID) {
		this.sGlobalPWGUID = sGlobalPWGUID;
	}

	public static int getGENERAL_TRACKER() {
		return GENERAL_TRACKER;
	}

	public static void setGENERAL_TRACKER(int gENERAL_TRACKER) {
		GENERAL_TRACKER = gENERAL_TRACKER;
	}

	public HashMap<TrackerName, Tracker> getmTrackers() {
		return mTrackers;
	}

	public void setmTrackers(HashMap<TrackerName, Tracker> mTrackers) {
		this.mTrackers = mTrackers;
	}

	public static String getPropertyId() {
		return PROPERTY_ID;
	}

	public String getsGlobalWomenName() {
		return sGlobalWomenName;
	}

	public void setsGlobalWomenName(String sGlobalWomenName) {
		this.sGlobalWomenName = sGlobalWomenName;
	}

	public String getsGlobalHusbandName() {
		return sGlobalHusbandName;
	}

	public void setsGlobalHusbandName(String sGlobalHusbandName) {
		this.sGlobalHusbandName = sGlobalHusbandName;
	}

	public String getsGlobalLocation() {
		return sGlobalLocation;
	}

	public void setsGlobalLocation(String sGlobalLocation) {
		this.sGlobalLocation = sGlobalLocation;
	}

	public String getsGlobalLongitude() {
		return sGlobalLongitude;
	}

	public void setsGlobalLongitude(String sGlobalLongitude) {
		this.sGlobalLongitude = sGlobalLongitude;
	}

	public String getsGlobalLatitude() {
		return sGlobalLatitude;
	}

	public void setsGlobalLatitude(String sGlobalLatitude) {
		this.sGlobalLatitude = sGlobalLatitude;
	}

	public String getsGlobalAshaName() {
		return sGlobalAshaName;
	}

	public void setsGlobalAshaName(String sGlobalAshaName) {
		this.sGlobalAshaName = sGlobalAshaName;
	}

	public String getGlobalHHFamilyMemberGUID() {
		return GlobalHHFamilyMemberGUID;
	}

	public void setGlobalHHFamilyMemberGUID(String globalHHFamilyMemberGUID) {
		GlobalHHFamilyMemberGUID = globalHHFamilyMemberGUID;
	}

	public int getiCurrentActiveTab() {
		return iCurrentActiveTab;
	}

	public void setiCurrentActiveTab(int iCurrentActiveTab) {
		this.iCurrentActiveTab = iCurrentActiveTab;
	}

	public String getsGlobalUserName() {
		return sGlobalUserName;
	}

	public void setsGlobalUserName(String sGlobalUserName) {
		this.sGlobalUserName = sGlobalUserName;
	}

	public String getsGlobalPassword() {
		return sGlobalPassword;
	}

	public void setsGlobalPassword(String sGlobalPassword) {
		this.sGlobalPassword = sGlobalPassword;
	}

	public String getsGlobalUserID() {
		return sGlobalUserID;
	}

	public void setsGlobalUserID(String sGlobalUserID) {
		this.sGlobalUserID = sGlobalUserID;
	}

	public int getiGlobalLangID() {
		return iGlobalLangID;
	}

	public void setiGlobalLangID(int iGlobalLangID) {
		this.iGlobalLangID = iGlobalLangID;
	}

	public int getiGlobalEnableFragment() {
		return iGlobalEnableFragment;
	}

	public void setiGlobalEnableFragment(int iGlobalEnableFragment) {
		this.iGlobalEnableFragment = iGlobalEnableFragment;
	}

	public int getiGlobalGenderID() {
		return iGlobalGenderID;
	}

	public void setiGlobalGenderID(int iGlobalGenderID) {
		this.iGlobalGenderID = iGlobalGenderID;
	}

	public String getsGlobalCurrentVisitDate() {
		return sGlobalCurrentVisitDate;
	}

	public void setsGlobalCurrentVisitDate(String sGlobalCurrentVisitDate) {
		this.sGlobalCurrentVisitDate = sGlobalCurrentVisitDate;
	}

	public String getTokenCode() {
		return TokenCode;
	}

	public void setTokenCode(String tokenCode) {
		TokenCode = tokenCode;
	}

	public String getGlobalHHSurveyGUID() {
		return GlobalHHSurveyGUID;
	}

	public void setGlobalHHSurveyGUID(String globalHHSurveyGUID) {
		GlobalHHSurveyGUID = globalHHSurveyGUID;
	}

	public int getLanguage() {
		return Language;
	}

	public void setLanguage(int language) {
		Language = language;
	}

	private int globalWebViewBypass;

	public int getGlobalWebViewBypass() {
		return globalWebViewBypass;
	}

	public void setGlobalWebViewBypass(int globalWebViewBypass) {
		this.globalWebViewBypass = globalWebViewBypass;
	}

	public String getVHND_Date() {
		return VHND_Date;
	}

	public void setVHND_Date(String vHND_Date) {
		VHND_Date = vHND_Date;
	}

	public String getVHND_VillageID() {
		return VHND_VillageID;
	}

	public void setVHND_VillageID(String vHND_VillageID) {
		VHND_VillageID = vHND_VillageID;
	}

	public String getVHND_MonthField() {
		return VHND_MonthField;
	}

	public void setVHND_MonthField(String vHND_MonthField) {
		VHND_MonthField = vHND_MonthField;
	}

	// change the following line
	// private static final String PROPERTY_ID = "UA-62532404-1";
	// Hero Changes
	private static final String PROPERTY_ID = "UA-87487816-1";

	public static int GENERAL_TRACKER = 0;

	public enum TrackerName {
		APP_TRACKER, GLOBAL_TRACKER, ECOMMERCE_TRACKER,
	}

	public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

	public Global() {
		// TODO Auto-generated constructor stub

		super();
	}

	public synchronized Tracker getTracker(TrackerName appTracker) {
		if (!mTrackers.containsKey(appTracker)) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics
					.newTracker(PROPERTY_ID)
					: (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics
							.newTracker(R.xml.global_tracker) : analytics
							.newTracker(R.xml.ecommerce_tracker);
			mTrackers.put(appTracker, t);
		}
		return mTrackers.get(appTracker);
	}

}
