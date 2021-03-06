package com.microware.intrahealth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

@SuppressLint("SdCardPath")
public class DatabaseHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase sqliteDb;
    private static DatabaseHelper instance;
    private static final int DATABASE_VERSION = 21;
    // the default database path is :
    // /data/data/pkgNameOfYourApplication/databases/
    private static String DB_PATH = "";
    private static String DB_PATH_PREFIX = "/data/data/";
    private static String DB_PATH_SUFFIX = "/databases/";
    private static final String TAG = "DatabaseHelper";
    private Context context;

    /**
     * Constructor
     *
     * @param aContext : application context
     * @param name     : database name
     * @param factory  : Cursor Factory
     * @param version  : DB version
     */
    public DatabaseHelper(Context aContext, String name, CursorFactory factory,
                          int version) {
        super(aContext, name, factory, version);
        // TODO Auto-generated constructor stub
        this.context = aContext;
        Log.i(TAG, "Create or Open Database : " + name);
    }

    /**
     * Initialise method
     *
     * @param aContext     : application context
     * @param databaseName : database name
     */
    private static void initialize(Context aContext, String databaseName) {
        if (instance == null) {
            // Check there is an original copy of the database in the assets
            // directory of the application
            if (!checkDatabase(aContext, databaseName)) {
                // if not then copy database from assets directory
                try {
                    copyDatabase(aContext, databaseName);
                } catch (Exception e) {
                    Log.e(TAG,
                            "Database does not exists and there is no copy of database in asset directory");
                }
            }
            // Create instance of database
            Log.i(TAG, "Creating database instance ....");
            instance = new DatabaseHelper(aContext, databaseName, null,
                    DATABASE_VERSION);
            sqliteDb = instance.getWritableDatabase();
            Log.i(TAG, "Database instance created!");
        }
    }

    /**
     * static method for getting singleton instance
     *
     * @param aContext     : application context
     * @param databaseName : database name
     * @return singleton instance
     */
    public static final DatabaseHelper getInstance(Context aContext,
                                                   String databaseName) {
        initialize(aContext, databaseName);
        return instance;
    }

    /**
     * Method to get database instance
     *
     * @return SQLiteDatabase instance
     */
    public SQLiteDatabase getDatabase() {
        return sqliteDb;
    }

    /**
     * Method for Copy the database from asset directory to application's data
     * directory
     *
     * @param databaseName : database name
     * @throws IOException : exception if file does not exists
     */
    public void copyDatabase(String databaseName) throws IOException {
        copyDatabase(context, databaseName);
    }

    /**
     * Copy database from assets directory to application's data directory
     *
     * @param aContext     : application context
     * @param databaseName : database name
     * @throws : IOException, if file does not exists
     */
    private static void copyDatabase(Context aContext, String databaseName)
            throws IOException {
        // Open your local database as the input stream
        InputStream myInput = aContext.getAssets().open(databaseName);
        // Path to the just created empty db
        String outFileName = getDatabasePath(aContext, databaseName);
        // Check if the path exists or not, if not then create
        String dataDir = DB_PATH_PREFIX + aContext.getPackageName()
                + DB_PATH_SUFFIX;
        File dbFile = new File(dataDir);
        if (!dbFile.exists()) {
            dbFile.mkdir();
        }
        // Copy the file into data directory
        Log.i(TAG, "Copying local database into : " + outFileName);

        // Open the empty database as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.i(TAG, "Database (" + databaseName + ") copied successfully!");
    }

    /**
     * Method to check if database exists in application's data directory
     *
     * @param databaseName : database name
     * @return : boolean (true if exists)
     */
    public boolean checkDatabase(String databaseName) {
        return checkDatabase(context, databaseName);
    }

    /**
     * check original copy of the database
     *
     * @param aContext     : application context
     * @param databaseName : database name
     * @return : true ? false
     */
    private static boolean checkDatabase(Context aContext, String databaseName) {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getDatabasePath(aContext, databaseName);
            Log.i(TAG, "Trying to conntect to : " + myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            Log.i(TAG, "Database " + databaseName + " found!");
            checkDB.close();
        } catch (SQLiteException e) {
            Log.i(TAG, "Database " + databaseName + " does not exists!");
        }

        return checkDB != null ? true : false;
    }

    /***
     * Method that returns database path in the application's data directory
     *
     * @param databaseName
     *            : database name
     * @return : complete path
     */
    public String getDatabasePath(String databaseName) {
        return getDatabasePath(context, databaseName);
    }

    /***
     * Static Method that returns database path in the application's data
     * directory
     *
     * @param aContext
     *            : application context
     * @param databaseName
     *            : database name
     * @return : complete path
     */
    private static String getDatabasePath(Context aContext, String databaseName) {
        DB_PATH = DB_PATH_PREFIX + aContext.getPackageName() + DB_PATH_SUFFIX
                + databaseName;
        return DB_PATH;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.i(TAG, "OnCreate :: Called ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.i(TAG, "OnCreate :: Upgrade ");
        try {
            if (oldVersion < 21) {//version 6.1.8
                if (oldVersion < 20) {//version 6.1.5
                    if (oldVersion < 19) {//version 6.1.4
                        if (oldVersion < 18) {
                            if (oldVersion < 17) {//version 6.1.3
                                if (oldVersion < 16) {
                                    if (oldVersion < 15) {
                                        if (oldVersion < 14) {
                                            if (oldVersion < 13) {
                                                if (oldVersion < 12) {
                                                    if (oldVersion < 11) {
                                                        if (oldVersion < 10) {
                                                            if (oldVersion < 9) {
                                                                if (oldVersion < 8) {
                                                                    if (oldVersion < 7) {

                                                                        String sql = "CREATE TABLE `tbl_DetailedModuleUsage` (`GUID`	TEXT,`UserID`	TEXT,`ModuleName`	TEXT,`ModulePageName`	TEXT,`StartTime`	TEXT,`EndTime`	TEXT,`Date`	TEXT,`IsUpload`	INTEGER);";
                                                                        db.execSQL(sql);
                                                                    }
                                                                    try {
                                                                        String sql = "select DateAvailable from tblFP_followup";
                                                                        db.rawQuery(sql, null);

                                                                    } catch (Exception ex) {
                                                                        String sql = "ALTER TABLE tblFP_followup ADD DateAvailable  INTEGER ";
                                                                        db.execSQL(sql);

                                                                    }
                                                                }

                                                                String sql = "CREATE TABLE `tbldevicespaceusage` (`ID`	INTEGER PRIMARY KEY AUTOINCREMENT,						`UserID`	INTEGER DEFAULT NULL,	`InternalSpace`	TEXT,	`AvialableSpace`	TEXT,						`IMEI`	TEXT,						`ExternalSpace`	TEXTL,						`CreatedOn`	TEXT		);";
                                                                db.execSQL(sql);

                                                            }
                                                            String sql1 = "alter table Tbl_HHFamilyMember add Other_Id_Type  INTEGER; ";
                                                            String sql2 = "alter table Tbl_HHFamilyMember add Other_Id  TEXT;";
                                                            String sql3 = "alter table Tbl_HHFamilyMember add Other_Id_Name  TEXT;";
                                                            db.execSQL(sql1);
                                                            db.execSQL(sql2);
                                                            db.execSQL(sql3);

                                                        }
                                                        String sql1 = "alter table tblmstPNCQues add Audio_FileName TEXT;";
                                                        String sql2 = "CREATE TABLE `tblmedia` (`ID`	INTEGER PRIMARY KEY AUTOINCREMENT,	`FileName`	TEXT);";
                                                        db.execSQL(sql1);
                                                        db.execSQL(sql2);

                                                    }
                                                    String sql1 = "alter table tblmstFPQues add Audio_FileName TEXT;";
                                                    db.execSQL(sql1);
                                                }

                                                String sql1 = "CREATE TABLE `tblncdscreening` (`Uid`  INTEGER PRIMARY KEY AUTOINCREMENT,  `RegistrationNo`  TEXT,  `CampNo`  TEXT,  `RegistrationDate`  TEXT,  `BlockID`  INTEGER,  `VillageID`  INTEGER,  `HHFamilyMemberGUID`  TEXT,  `HHSurveyGUID`  TEXT,  `NCDScreeningGUID`  TEXT,  `Name`  TEXT,  `Age`  INTEGER,  `Sex`  INTEGER,  `Address`  TEXT,  `Mobileno`  TEXT,  `Occupation`  INTEGER,  `Occupationother`  TEXT,  `Anypastillness`  TEXT,  `Height`  INTEGER,  `Weight`  TEXT,  `BMI`  TEXT,  `RBS`  TEXT,  `BP`  TEXT,  `Othercomplication`  TEXT,  `SuspectedFor`  TEXT,  `Referredto`  INTEGER,  `ReferredBy`  INTEGER,  `Status`  INTEGER,  `IsEdited`  INTEGER,  `CreatedBy`  INTEGER,  `CreatedOn`  TEXT,  `UpdatedBy`  INTEGER,  `UpdatedOn`  TEXT,  `AshaID`  INTEGER,  `ANMID`  INTEGER,  `AnyOthcomplicationYes`  INTEGER,  `SuspectedAnyOther`  TEXT);";
                                                String sql2 = "CREATE TABLE `tblncdfollowup` (`Uid` INTEGER PRIMARY KEY AUTOINCREMENT,`RegistrationNo` TEXT,`GuardianName` TEXT,`RegistrationDate` TEXT,`BlockID` INTEGER,`VillageID` INTEGER,`HHFamilyMemberGUID` TEXT,`HHSurveyGUID` TEXT,`NCDScreeningGUID` TEXT,`NCDFollowupGUID` TEXT,`Name` TEXT,`NCDDiagnosis` TEXT,`RBS` TEXT,`BP` TEXT,`GFRecieved` TEXT,`GFother` TEXT,`GFProblem` TEXT,`GFProblemother` TEXT,`GFDiagnosisReason` TEXT,`GFDiagnosisReasonoth` TEXT,`GForPFDiagnosisReason` TEXT,`GForPFDiagnosisReasonoth` TEXT,`NCDMedicine` INTEGER,`NCDMedicineYes` INTEGER,`NCDMedicineYesoth` TEXT,`NCDMedicineNo` TEXT,`NCDMedicineNooth` TEXT,`CounsellingTips` INTEGER,`Suggestion` TEXT,`IsEdited` INTEGER,`CreatedBy` INTEGER,`CreatedOn` TEXT,`UpdatedBy` INTEGER,`UpdatedOn` TEXT,`AshaID` INTEGER,`ANMID` INTEGER,`Referal` INTEGER);";
                                                db.execSQL(sql1);
                                                db.execSQL(sql2);
                                            }
                                            String sql1 = "CREATE TABLE `anmasha` (`ANMAshaUID` INTEGER,`ANMID` INTEGER NOT NULL,`ASHAID` INTEGER NOT NULL,PRIMARY KEY(ANMAshaUID));";
                                            String sql2 = "CREATE TABLE `userashamapping` ( `AshaUserUID`  INTEGER, `AshaID`  INTEGER, `UserID`  INTEGER, `AshaName`  TEXT, `SubCenterName`  TEXT);";
                                            db.execSQL(sql1);
                                            db.execSQL(sql2);
                                        }
                                        try {

                                            String sqlupdate2 = "update  Tbl_HHSurvey set IsEdited=1 ";
                                            String sqlupdate1 = "update  Tbl_hhfamilymember set IsEdited=1 where HHSurveyGUID in (select HHSurveyGUID from Tbl_HHSurvey) ";
                                            String sqlupdate3 = "update  tblANCVisit set IsEdited=1 ";
                                            String sqlupdate4 = "update  tblPregnant_woman set IsEdited=1 ";
                                            db.execSQL(sqlupdate1);
                                            db.execSQL(sqlupdate2);
                                            db.execSQL(sqlupdate3);
                                            db.execSQL(sqlupdate4);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        String sql1 = "CREATE TABLE `ashavillage` (`AshaVillageUID` INTEGER,`ASHAID` INTEGER,`VillageID` INTEGER);";
                                        String sql2 = "alter table tblncdfollowup add MedicineGiven TEXT;";
                                        String sql3 = "alter table tblncdfollowup add MedicineContinue INTEGER;";
                                        String sql4 = "alter table tblncdfollowup add MedicineWhere INTEGER;";
                                        String sql5 = "alter table tblncdfollowup add ReferralYes  INTEGER;";
                                        String sql6 = "alter table tblncdfollowup add MedicineWhereOther  TEXT;";
                                        db.execSQL(sql1);
                                        db.execSQL(sql2);
                                        db.execSQL(sql3);
                                        db.execSQL(sql4);
                                        db.execSQL(sql5);
                                        db.execSQL(sql6);
                                    }

                                    String sql1 = "CREATE TABLE `tblDowloadDetail` ( `UID`  INTEGER PRIMARY KEY AUTOINCREMENT, `ModuleID`  INTEGER, `ModuleName`  TEXT, `IsEdited`  INTEGER, `CreatedBy`  INTEGER, `CreatedOn`  TEXT, `AshaID`  INTEGER, `ANMID`  INTEGER);";
                                    String sql2 = "CREATE TABLE `tblncdcbac` ( `UID`  INTEGER PRIMARY KEY AUTOINCREMENT, `BlockID`  INTEGER, `VillageID`  INTEGER, `HHFamilyMemberGUID`  TEXT, `HHSurveyGUID`  TEXT, `NCDCBACGUID`  TEXT, `Age`  INTEGER, `Smoke`  INTEGER, `Alcohol`  INTEGER, `Waist`  TEXT, `PhysicalActivity`  INTEGER, `FamilyHistory`  INTEGER, `Score`  INTEGER, `Breath`  INTEGER, `Coughing`  INTEGER, `BloodinSputum`  INTEGER, `Fits`  INTEGER, `OpeningMouth`  INTEGER, `Ulcers`  INTEGER, `AnyChangeTone`  INTEGER, `LumpinBreast`  INTEGER, `BloodStainedNipple`  INTEGER, `BreastSize`  INTEGER, `BleedingPeriods`  INTEGER, `BleedingMenopause`  INTEGER, `BleedingIntercourse`  INTEGER, `VaginalDischarge`  INTEGER, `Status`  INTEGER, `IsEdited`  INTEGER, `CreatedBy`  INTEGER, `CreatedOn`  TEXT, `UpdatedBy`  INTEGER, `UpdatedOn`  TEXT, `AshaID`  INTEGER, `ANMID`  INTEGER);";
                                    db.execSQL(sql1);
                                    db.execSQL(sql2);
                                    try {
                                        String sql = "select IsActive from Tbl_HHSurvey";
                                        db.rawQuery(sql, null);

                                    } catch (Exception ex) {
                                        String sql = "ALTER TABLE Tbl_HHSurvey ADD IsActive  INTEGER ";
                                        String sqlisactive = "update Tbl_HHSurvey set IsActive=1 ";
                                        db.execSQL(sql);
                                        db.execSQL(sqlisactive);

                                    }
                                }

                                try {
                                    String sql = "select is_temp from MstUser";
                                    db.rawQuery(sql, null);

                                } catch (Exception ex) {
                                    String sql = "ALTER TABLE MstUser ADD is_temp INTEGER";
                                    db.execSQL(sql);
                                }
                                try {
                                    String sql = "select MeaslesRubella from tblChild";
                                    db.rawQuery(sql, null);

                                } catch (Exception ex) {
                                    String sql = "ALTER TABLE tblChild ADD MeaslesRubella TEXT";
                                    db.execSQL(sql);
                                }
                            }
                            try {
                                String sql = "select UpdatedBy from Tbl_HHFamilyMember";
                                db.rawQuery(sql, null);

                            } catch (Exception ex) {
                                String sql = "ALTER TABLE Tbl_HHFamilyMember ADD UpdatedBy INTEGER";
                                String sql1 = "ALTER TABLE Tbl_HHFamilyMember ADD UpdatedOn TEXT";
                                db.execSQL(sql);
                                db.execSQL(sql1);
                            }
                            try {
                                String sql = "select UpdatedBy from Tbl_HHSurvey";
                                db.rawQuery(sql, null);

                            } catch (Exception ex) {
                                String sql = "ALTER TABLE Tbl_HHSurvey ADD UpdatedBy INTEGER";
                                String sql1 = "ALTER TABLE Tbl_HHSurvey ADD UpdatedOn TEXT";
                                db.execSQL(sql);
                                db.execSQL(sql1);
                            }
                        }

                        String sql = "ALTER TABLE tblChild ADD IPV2 TEXT";
                        String sql1 = "ALTER TABLE tblANCVisit ADD HighRisk INTEGER";
                        String sql2 = "ALTER TABLE tblANCVisit ADD HighRiskDate TEXT";
                        String sql3 = "ALTER TABLE tblncdscreening ADD FBS INTEGER";
                        String sql4 = "ALTER TABLE tblncdscreening ADD PPGTest INTEGER";
                        String sql5 = "ALTER TABLE tblncdscreening ADD HbA1C TEXT";
                        String sql6 = "ALTER TABLE tblncdscreening ADD UrineTest INTEGER";
                        String sql7 = "ALTER TABLE tblncdscreening ADD DiabeticStatus INTEGER";
                        String sql8 = "ALTER TABLE tblncdscreening ADD Diabetic INTEGER";
                        String sql9 = "ALTER TABLE tblncdscreening ADD ActivityDate TEXT";
                        String sql10 = "CREATE TABLE `tblncdscreeningMedicine` ( `HHFamilyMemberGUID` TEXT, `NCDScreeningGUID` TEXT, `ID` INTEGER, `Quantity` INTEGER, `CreatedBy` INTEGER, `CreatedOn` TEXT, `AshaID` INTEGER, `ANMID` INTEGER, `IsEdited` INTEGER);";
                        String sql11 = "CREATE TABLE `tblncdscreeningmedicineoutside` ( `NCDScreeningGUID` TEXT, `ID` INTEGER, `Quantity` INTEGER, `CreatedBy` INTEGER, `CreatedOn` TEXT, `IsEdited` INTEGER);";
                        String sql12 = "CREATE TABLE `tblncdscreeningoutside` ( `Uid` INTEGER, `Aadharcard` TEXT, `ASHAName` TEXT, `Village` TEXT, `NCDScreeningGUID` TEXT, `Name` TEXT, `DOBAvailable` INTEGER, `DOB` TEXT, `Sex` INTEGER, `Address` TEXT, `Mobileno` TEXT, `Occupation` INTEGER, `Occupationother` TEXT, `Anypastillness` TEXT, `Height` INTEGER, `Weight` TEXT, `BMI` TEXT, `BP` TEXT, `Othercomplication` TEXT, `SuspectedFor` TEXT, `Status` INTEGER, `IsEdited` INTEGER, `CreatedBy` INTEGER, `CreatedOn` TEXT, `AnyOthcomplicationYes` INTEGER, `SuspectedAnyOther` TEXT, `FBS` INTEGER, `PPGTest` INTEGER, `HbA1C` TEXT, `UrineTest` INTEGER, `DiabeticStatus` INTEGER, `Diabetic` INTEGER);";
                        String sql13 = "CREATE TABLE `mstchc` ( `UID` INTEGER, `CHCID` INTEGER, `CHCCode` TEXT, `CHCName` TEXT, `LanguageID` INTEGER, `IsDeleted` INTEGER);";
                        String sql14 = "CREATE TABLE `mstpdfreport` ( `UID` INTEGER, `ReportID` INTEGER, `ReportName` TEXT, `LastDownload` TEXT, `IsActive` INTEGER, `RevisedOn` TEXT);";
                        String sql15 = "CREATE TABLE `anmsubcenter` ( `ANMSubCenterUID` INTEGER, `ANMID` INTEGER, `SubCenterID` INTEGER, `SubCenterCode` INTEGER, `ANMName` TEXT);";
                        db.execSQL(sql);
                        db.execSQL(sql1);
                        db.execSQL(sql2);
                        db.execSQL(sql3);
                        db.execSQL(sql4);
                        db.execSQL(sql5);
                        db.execSQL(sql6);
                        db.execSQL(sql7);
                        db.execSQL(sql8);
                        db.execSQL(sql9);
                        db.execSQL(sql10);
                        db.execSQL(sql11);
                        db.execSQL(sql12);
                        db.execSQL(sql13);
                        db.execSQL(sql14);
                        db.execSQL(sql15);

                    }
                    String sql = "ALTER TABLE tblChild ADD DeliveryType INTEGER";
                    String sql1 = "ALTER TABLE MstUser ADD first_name TEXT";
                    String sql2 = "ALTER TABLE MstUser ADD last_name TEXT";
                    String sql3 = "ALTER TABLE tblPNChomevisit_ANS ADD Createtime TEXT";
                    db.execSQL(sql);
                    db.execSQL(sql1);
                    db.execSQL(sql2);
                    db.execSQL(sql3);
                    try {
                        String sql4 = "select Abortion_FacilityName from tblPregnant_woman";
                        db.rawQuery(sql4, null);
                    } catch (Exception ex) {
                        String sql4 = "ALTER TABLE tblPregnant_woman ADD Abortion_FacilityName INTEGER";
                        db.execSQL(sql4);

                    }

                }
                String sql1 = "CREATE TABLE `tblncdcbacdiagnosis` (`UID` INTEGER,`HHFamilyMemberGUID` TEXT,`HHSurveyGUID` TEXT,`NCDScreeningGUID` TEXT,`NCD_id` INTEGER,`Det_date` TEXT,`Diagnosis` INTEGER,`PrimaryDet_date` TEXT,`Det_status` INTEGER,`Healthcenter` INTEGER,`MedicineAvailability` INTEGER,`Reasonforclose` TEXT,`IsEdited` INTEGER,`CreatedBy` INTEGER,`CreatedOn` TEXT,`UpdatedBy` INTEGER,`UpdatedOn` TEXT,`AshaID` INTEGER,`ANMID` INTEGER);";
                String sql2 = "CREATE TABLE `tblincentivesurvey` (`UID` INTEGER,`IncentiveSurveyGUID` TEXT,`Month` INTEGER,`Year` INTEGER,`AshaStatus` INTEGER,`AFStatus` INTEGER,`ANMStatus` INTEGER,`BCPMStatus` INTEGER,`RemarkAshaStatus` TEXT,`RemarkAFStatus` TEXT,`RemarkAMStatus` TEXT,`RemarkBCPMStatus` TEXT,`AmtRecieved` INTEGER,`Claim` INTEGER,`NotApproved` INTEGER,`IsEdited` INTEGER,`CreatedBy` INTEGER,`CreatedOn` TEXT,`UpdatedBy` INTEGER,`UpdatedOn` TEXT,`AshaID` INTEGER,`ANMID` INTEGER,`AFID` INTEGER);";
                String sql3 = "CREATE TABLE `tblashaincentivedetail` (`UID` INTEGER,`IncentiveSurveyGUID` TEXT,`IncentiveGUID` TEXT,`Month` INTEGER,`Year` INTEGER,`ActivityAmount` INTEGER,`ActivityTotal` INTEGER,`ActivityNo` INTEGER,`ActivitySrno` TEXT,`AshaStatus` INTEGER,`AFStatus` INTEGER,`ANMStatus` INTEGER,`BCPMStatus` INTEGER,`RemarkAshaStatus` TEXT,`RemarkAFStatus` TEXT,`RemarkAMStatus` TEXT,`RemarkBCPMStatus` TEXT,`AmtRecieved` INTEGER,`IsEdited` INTEGER,`CreatedBy` INTEGER,`CreatedOn` TEXT,`UpdatedBy` INTEGER,`UpdatedOn` TEXT,`AshaID` INTEGER,`ANMID` INTEGER,`AFID` INTEGER);";
                String sql4 = "CREATE TABLE `mstashaactivity` (`UID` INTEGER PRIMARY KEY AUTOINCREMENT,`SeqID` INTEGER,`Qsrno` TEXT,`QuesID` INTEGER,`Activity` TEXT,`Amount` INTEGER,`AreaType` INTEGER,`Createdon` TEXT,`CreatedBy` INTEGER,`LangaugeID` INTEGER,`Qtype` INTEGER,`Updatedon` TEXT,`UpdatedBy` INTEGER);";
                String sql5 = "ALTER TABLE tblncdcbac ADD IPRisk TEXT";
                db.execSQL(sql1);
                db.execSQL(sql2);
                db.execSQL(sql3);
                db.execSQL(sql5);
                db.execSQL(sql4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
