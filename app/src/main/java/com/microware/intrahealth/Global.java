package com.microware.intrahealth;

import java.util.HashMap;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class Global extends MultiDexApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


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
    private int VHND_flag;
    private int Notification_flag;
    private int msg_flag;
    private int Notification_trackflag;
    private String NCDScreeningGUID;
    private String IMEI;
    private String VersionName;
    private int CHCID;
    private String CHCName;
    private String IncentiveSurveyGUID;
    private int GlobalMonth;
    private int GlobalYear;

    public int getGlobalMonth() {
        return GlobalMonth;
    }

    public void setGlobalMonth(int globalMonth) {
        GlobalMonth = globalMonth;
    }

    public int getGlobalYear() {
        return GlobalYear;
    }

    public void setGlobalYear(int globalYear) {
        GlobalYear = globalYear;
    }

    public String getIncentiveSurveyGUID() {
        return IncentiveSurveyGUID;
    }

    public void setIncentiveSurveyGUID(String incentiveSurveyGUID) {
        IncentiveSurveyGUID = incentiveSurveyGUID;
    }

    public static int getGeneralTracker() {
        return GENERAL_TRACKER;
    }

    public static void setGeneralTracker(int generalTracker) {
        GENERAL_TRACKER = generalTracker;
    }

    public int getCHCID() {
        return CHCID;
    }

    public void setCHCID(int CHCID) {
        this.CHCID = CHCID;
    }

    public String getCHCName() {
        return CHCName;
    }

    public void setCHCName(String CHCName) {
        this.CHCName = CHCName;
    }

    public String getVersionName() {
        return VersionName;
    }

    public void setVersionName(String versionName) {
        VersionName = versionName;
    }

    public int getNotification_trackflag() {
        return Notification_trackflag;
    }

    public void setNotification_trackflag(int notification_trackflag) {
        Notification_trackflag = notification_trackflag;
    }

    public int getMsg_flag() {
        return msg_flag;
    }

    public void setMsg_flag(int msg_flag) {
        this.msg_flag = msg_flag;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String iMEI) {
        IMEI = iMEI;
    }

    public String getNCDScreeningGUID() {
        return NCDScreeningGUID;
    }

    public void setNCDScreeningGUID(String nCDScreeningGUID) {
        NCDScreeningGUID = nCDScreeningGUID;
    }

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

    public int getNotification_flag() {
        return Notification_flag;
    }

    public void setNotification_flag(int notification_flag) {
        Notification_flag = notification_flag;
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

    public int getVHND_flag() {
        return VHND_flag;
    }

    public void setVHND_flag(int vHND_flag) {
        VHND_flag = vHND_flag;
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

    private String homevisit_GUID;

    public String getHomevisit_GUID() {
        return homevisit_GUID;
    }

    public void setHomevisit_GUID(String homevisit_GUID) {
        this.homevisit_GUID = homevisit_GUID;
    }

    private String immunize_GUID;


    public String getImmunize_GUID() {
        return immunize_GUID;
    }

    public void setImmunize_GUID(String immunize_GUID) {
        this.immunize_GUID = immunize_GUID;
    }

    private String new_GUID;

    public String getNew_GUID() {
        return new_GUID;
    }

    public void setNew_GUID(String new_GUID) {
        this.new_GUID = new_GUID;
    }

    private String anc_GUID;

    public String getAnc_GUID() {
        return anc_GUID;
    }

    public void setAnc_GUID(String anc_GUID) {
        this.anc_GUID = anc_GUID;
    }

    private String SYN_GUID;

    public String getSYN_GUID() {
        return SYN_GUID;
    }

    public void setSYN_GUID(String sYN_GUID) {
        SYN_GUID = sYN_GUID;
    }

    private String VHND_GUID;

    public String getVHND_GUID() {
        return VHND_GUID;
    }

    public void setVHND_GUID(String vHND_GUID) {
        VHND_GUID = vHND_GUID;
    }

    private String Incentives;

    public String getIncentives() {
        return Incentives;
    }

    public void setIncentives(String incentives) {
        Incentives = incentives;
    }

    private String FP_GUID;

    public String getFP_GUID() {
        return FP_GUID;
    }

    public void setFP_GUID(String fP_GUID) {
        FP_GUID = fP_GUID;
    }

    private String MCH_GUID;

    public String getMCH_GUID() {
        return MCH_GUID;
    }

    public void setMCH_GUID(String mCH_GUID) {
        MCH_GUID = mCH_GUID;
    }

    private String HH_GUID;

    public String getHH_GUID() {
        return HH_GUID;
    }

    public void setHH_GUID(String hH_GUID) {
        HH_GUID = hH_GUID;
    }

    private int UserID;

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    private String Login_GUID;

    public String getLogin_GUID() {
        return Login_GUID;
    }

    public void setLogin_GUID(String login_GUID) {
        Login_GUID = login_GUID;
    }

}
