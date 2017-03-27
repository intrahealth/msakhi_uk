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
	private static final int DATABASE_VERSION = 3;
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
	 * @param aContext
	 *            : application context
	 * @param name
	 *            : database name
	 * @param factory
	 *            : Cursor Factory
	 * @param version
	 *            : DB version
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
	 * @param aContext
	 *            : application context
	 * @param databaseName
	 *            : database name
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
	 * @param aContext
	 *            : application context
	 * @param databaseName
	 *            : database name
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
	 * @param databaseName
	 *            : database name
	 * @throws IOException
	 *             : exception if file does not exists
	 */
	public void copyDatabase(String databaseName) throws IOException {
		copyDatabase(context, databaseName);
	}

	/**
	 * Copy database from assets directory to application's data directory
	 * 
	 * @param aContext
	 *            : application context
	 * @param databaseName
	 *            : database name
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
	 * @param databaseName
	 *            : database name
	 * @return : boolean (true if exists)
	 */
	public boolean checkDatabase(String databaseName) {
		return checkDatabase(context, databaseName);
	}

	/**
	 * check original copy of the database
	 * 
	 * @param aContext
	 *            : application context
	 * @param databaseName
	 *            : database name
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

			  if (oldVersion < 3) {
				  if (oldVersion < 2) {

						String sql1 = "ALTER TABLE Tbl_HHFamilyMember ADD AshaID  INTEGER ";
						String sql2 = "ALTER TABLE Tbl_HHFamilyMember ADD ANMID  INTEGER ";
						String sql3 = "ALTER TABLE tblChild ADD AshaID  INTEGER ";
						String sql4 = "ALTER TABLE tblChild ADD ANMID  INTEGER";

						String sql5 = "ALTER TABLE tblPNChomevisit_ANS ADD AshaID  INTEGER";
						String sql6 = "ALTER TABLE tblPNChomevisit_ANS ADD ANMID  INTEGER ";

						String sql7 = "ALTER TABLE tblmstFPAns ADD AshaID  INTEGER";
						String sql8 = "ALTER TABLE tblmstFPAns ADD ANMID  INTEGER ";
						String sql9 = "ALTER TABLE tblANCVisit ADD TT1TT2last2yr INTEGER ";

						String sql10 = "ALTER TABLE tblmstFPFDetail ADD AshaID  INTEGER ";
						String sql11 = "ALTER TABLE tblmstFPFDetail ADD ANMID  INTEGER ";

						String sql12 = "ALTER TABLE tblmstimmunizationANS ADD AshaID  INTEGER ";
						String sql13 = "ALTER TABLE tblmstimmunizationANS ADD ANMID  INTEGER ";
						String sql14 = "CREATE TABLE `tbl_DatesEd` (`UID`	INTEGER PRIMARY KEY AUTOINCREMENT,`HHSurveyGUID`	TEXT,	`HHFamilyMemberGUID`	TEXT,	`PWGUID`	TEXT,`LmpDate`	TEXT,`EDD`	TEXT,`TT1Date`	TEXT,`TT2Date`	TEXT,`AncCheckupdate1`	TEXT,`AncCheckupdate3`	TEXT,`AncCheckupdate2`	TEXT,`AncCheckupdate4`	TEXT,`IsEdited`	INTEGER,`IsDeleted`	INTEGER,`CreatedOn`	TEXT,`CreatedBy`	INTEGER,`UpdatedOn`	TEXT,`UpdatedBy`	INTEGER,`AshaID`	INTEGER,`ANMID`	INTEGER)";
						String sql15 = "ALTER TABLE tblPregnant_woman ADD Education  INTEGER ";
						String sql16 = "ALTER TABLE Tbl_HHFamilyMember ADD Education  INTEGER ";
						String sql17 = "ALTER TABLE Tbl_HHSurvey ADD MigrateInDate  TEXT ";
						String sql18 = "ALTER TABLE Tbl_HHSurvey ADD MigrateOutDate  TEXT ";
						String sql19 = "ALTER TABLE Tbl_HHFamilyMember ADD DateOfDeath  TEXT ";
						String sql20 = "ALTER TABLE Tbl_HHFamilyMember ADD PlaceOfDeath  INTEGER ";
						String sql21 = "ALTER TABLE Tbl_HHFamilyMember ADD NameofDeathplace  TEXT ";
						String sql22 = "ALTER TABLE Tbl_HHFamilyMember ADD DeathVillage  INTEGER ";
						String sql23 = "CREATE TABLE `tblMigration` (	`UID`	INTEGER,	`HHFamilyMemberGUID`	TEXT,	`DateOfMigrationIn`	TEXT,	`DateOfMigrationOut`	TEXT,	`IsEdited`	INTEGER,`IsUploaded`	INTEGER,`CreatedBy`	INTEGER,`CreatedOn`	TEXT,`UpdatedBy`	INTEGER,`UpdatedOn`	TEXT,`IsDeleted`	INTEGER,`MigrationGUID`	TEXT);";
						String sql24 = "CREATE TABLE `tblhhupdate_Log` (`UID`	int,`UserID`	int,`hhsurveyguid`	TEXT,	`hhmemberguid`	TEXT,	`StatusId_Old`	int,	`StatusId_New`	int,	`Aadhar_Old`	TEXT,	`Aadhar_New`	TEXT,	`PhoneNo_Old`	TEXT,	`PhoneNo_New`	TEXT,	`DOB_Old`	TEXT,	`DOB_New`	TEXT,	`AccountNo_Old`	TEXT,	`AccountNo_New`	TEXT,	`IFSCCode_Old`	TEXT,`IFSCCode_New`	TEXT,`UpdatedOn`	TEXT,`IsEdited`	INTEGER);";
						String sql25 = "ALTER TABLE tblmstFPFDetail  RENAME TO tblFP_followup";
						String sql26 = "ALTER TABLE tblmstFPAns  RENAME TO tblFP_visit";
						String sql27 = "ALTER TABLE tblPregnant_woman ADD AltMobileNo  TEXT ";

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
						db.execSQL(sql16);
						db.execSQL(sql17);
						db.execSQL(sql18);
						db.execSQL(sql19);
						db.execSQL(sql20);
						db.execSQL(sql21);
						db.execSQL(sql22);
						db.execSQL(sql23);
						db.execSQL(sql24);
						db.execSQL(sql25);
						db.execSQL(sql26);
						db.execSQL(sql27);

					}
				String sql1 = "ALTER TABLE tblFP_followup add  CreatedBy INTEGER; ";
				String sql2 = "ALTER TABLE tblFP_visit add  CreatedBy INTEGER;";
				String sql3 = "ALTER TABLE tblPregnant_woman ADD Abortion_FacilityName	INTEGER ";
				String sql14 = "ALTER TABLE tblFP_followup add CreatedOn TEXT;";
				String sql16 = "ALTER TABLE tblFP_visit add CreatedOn TEXT;";
				String sql15 = "ALTER TABLE tblFP_followup add MethodadoptedDate TEXT;";

				String sql4 = "ALTER TABLE tblChild ADD JEVaccine1	TEXT;";
				String sql5 = "ALTER TABLE tblChild ADD JEVaccine2	TEXT;";
				String sql6 = "ALTER TABLE tblChild ADD VitaminA3	TEXT;";
				String sql7 = "ALTER TABLE tblChild ADD VitaminA4	TEXT;";
				String sql8 = "ALTER TABLE tblChild ADD VitaminA5	TEXT;";
				String sql9 = "ALTER TABLE tblChild ADD VitaminA6	TEXT;";
				String sql10 = "ALTER TABLE tblChild ADD VitaminA7	TEXT;";
				String sql11 = "ALTER TABLE tblChild ADD VitaminA8	TEXT;";
				String sql12 = "ALTER TABLE tblChild ADD VitaminA9	TEXT;";
				String sql13 = "ALTER TABLE tblChild ADD TT2	TEXT;";
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
				db.execSQL(sql16);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
