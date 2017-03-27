package com.microware.intrahealth.dataprovider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.microware.intrahealth.DatabaseHelper;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.object.Child_Imunization_Object;
import com.microware.intrahealth.object.MstANM;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.MstCatchmentSupervisor;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstState;
import com.microware.intrahealth.object.MstSubCenter;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.MstVHND_PerformanceIndicator;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.TblMstuser;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.Tbl_HHSurvey;
import com.microware.intrahealth.object.tblMigration;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_DatesEd;
import com.microware.intrahealth.object.tbl_Incentive;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblhhupdate_Log;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.tblmstimmunizationANS;
import com.microware.intrahealth.object.tblmstimmunizationQues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataProvider {

	static final int DATABASE_VERSION = 1;
	static final String DATABASE_NAME = "IntraHealth";
	public SQLiteDatabase dbIntraHealth;
	private Cursor cursor;
	private Context context;
	private DatabaseHelper dbHelper;
	@SuppressWarnings("unused")
	private String getGlobalGUID;
	// Add Herojit
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();

	public DataProvider(Context _context) {
		try {
			this.context = _context;
			DatabaseHelper dbHelper;
			dbHelper = DatabaseHelper.getInstance(context, DATABASE_NAME);
			dbIntraHealth = dbHelper.getDatabase();
			cursor = null;
		} catch (Exception exp) {

		}

	}

	public void executeSql(String Sql) {
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			dbIntraHealth.execSQL(Sql);
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in executeSql :: " + exception.getMessage());
		}
	}

	public int getMaxRecord(String Sql) {
		int iIntegerValue = 0;
		try {
			cursor = null;
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(Sql, null);
			if (cursor != null) {
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					iIntegerValue = cursor.getInt(0);
					cursor.moveToNext();
				}
				cursor.close();
			}
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in getMaxRecord :: " + exception.getMessage());
		}
		return iIntegerValue;
	}

	public int getcountRecord(String Sql) {
		int iIntegerValue = 0;
		try {
			cursor = null;
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(Sql, null);
			if (cursor != null) {
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {

					iIntegerValue = cursor.getInt(cursor.getColumnIndex("cnt"));
					cursor.moveToNext();
				}
				cursor.close();
			}
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in getMaxRecord :: " + exception.getMessage());
		}
		return iIntegerValue;
	}

	public String getRecord(String Sql) {
		String iStringValue = null;
		try {
			cursor = null;
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(Sql, null);
			if (cursor != null) {
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					iStringValue = cursor.getString(0);
					cursor.moveToNext();
				}
				cursor.close();
			}
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in getMaxRecord :: " + exception.getMessage());
		}
		return iStringValue;
	}

	public void deleteRecord(String sql) {

		try {
			executeSql(sql);
		} catch (Exception e) {
			System.out.println("error in deletng data  " + e);
		}
	}

	/*
	 * public ArrayList<User> getUsers(String sUserName, String sPassword) {
	 * ArrayList<User> Users = null; String sql =
	 * "select UserID,UserName,Password,RoleID,Name from Users where Lower(UserName)='"
	 * + sUserName.toLowerCase() + "'  and Password= '" + sPassword +
	 * "'  and IsUserDisabled=0  ";
	 * 
	 * cursor = null; try { if (dbIntraHealth == null) { dbIntraHealth =
	 * dbHelper.getDatabase(); } cursor = dbIntraHealth.rawQuery(sql, null); if
	 * (cursor != null) { Users = new ArrayList<User>(); cursor.moveToFirst();
	 * while (cursor.isAfterLast() == false) { User user = new User();
	 * 
	 * user.setsUserID(cursor.getString(0));
	 * user.setsUserName(cursor.getString(1));
	 * user.setsPassword(cursor.getString(2));
	 * user.setsRoleID(cursor.getString(3)); user.setsUser(cursor.getString(4));
	 * Users.add(user); cursor.moveToNext(); } cursor.close(); } return Users;
	 * 
	 * } catch (Exception exception) { Log.e("DataProvider",
	 * "Error in getAdmin :: " + exception.getMessage()); } return Users;
	 * 
	 * }
	 */

	/*
	 * public ArrayList<BaselineSurvey> getBaselineSurvey() {
	 * ArrayList<BaselineSurvey> ALBaselineSurvey = null; String sql =
	 * "Select LoanProduct,LoanProductID from MSTLoanProduct Order By LoanProduct"
	 * ; cursor = null; try { if (dbIntraHealth == null) { dbIntraHealth =
	 * dbHelper.getDatabase(); } cursor = dbIntraHealth.rawQuery(sql, null); if
	 * (cursor != null) { ALBaselineSurvey = new ArrayList<BaselineSurvey>();
	 * cursor.moveToFirst(); while (cursor.isAfterLast() == false) {
	 * BaselineSurvey BLSurvey = new BaselineSurvey();
	 * 
	 * BLSurvey.setH(cursor.getString(cursor .getColumnIndex("LoanProduct")));
	 * 
	 * BLSurvey.setiLoanProductID(cursor.getInt(cursor
	 * .getColumnIndex("LoanProductID")));
	 * 
	 * ALBaselineSurvey.add(BLSurvey); cursor.moveToNext(); }
	 * 
	 * cursor.close(); } return ALBaselineSurvey; } catch (Exception e) {
	 * System.out.println(e); } return ALBaselineSurvey; }
	 */
	public ArrayList<Tbl_HHFamilyMember> getAllWomenName(
			String FamilyMemberName, int flag, int AshaID) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";
		if (flag == 4) {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode from Tbl_HHFamilyMember left join Tbl_HHSurvey on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where Tbl_HHFamilyMember.GenderID=2 and Tbl_HHFamilyMember.MaritialStatusID=1 and Tbl_HHFamilyMember.StatusID=1 and Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " and FamilyMemberName like '"
					+ FamilyMemberName + "'";
		} else if (flag == 5) {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode from Tbl_HHFamilyMember left join Tbl_HHSurvey on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where Tbl_HHFamilyMember.GenderID=2 and Tbl_HHFamilyMember.MaritialStatusID=1 and Tbl_HHFamilyMember.StatusID=1  and Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " andTbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  hhfamilymemberguid from tblPregnant_woman where IsPregnant=1 ) and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  distinct(womenname_guid) from tblFP_followup ) and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  distinct(womenname_guid) from tblFP_visit)  and FamilyMemberName like '%"
					+ FamilyMemberName + "%' ";
		} else if (flag == 6) {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode from Tbl_HHFamilyMember left join Tbl_HHSurvey on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where Tbl_HHFamilyMember.GenderID=2 and Tbl_HHFamilyMember.MaritialStatusID=1 and Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " and  Tbl_HHFamilyMember.StatusID=1  and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  hhfamilymemberguid from tblPregnant_woman where IsPregnant=1 ) and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  distinct(womenname_guid) from tblFP_followup ) and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  distinct(womenname_guid) from tblFP_visit) ";
		} else if (flag == 7) {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode  from Tbl_HHFamilyMember inner join Tbl_HHSurvey  on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where  Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " and (Tbl_HHFamilyMember.HHFamilyMemberGUID  in (select  distinct(womenname_guid) from tblFP_visit ) or  Tbl_HHFamilyMember.HHFamilyMemberGUID  in (select  distinct(womenname_guid) from tblFP_followup )) ";
		} else if (flag == 8) {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode  from Tbl_HHFamilyMember inner join Tbl_HHSurvey  on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where  Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " and (Tbl_HHFamilyMember.HHFamilyMemberGUID  in (select  distinct(womenname_guid) from tblFP_visit ) or  Tbl_HHFamilyMember.HHFamilyMemberGUID  in (select  distinct(womenname_guid) from tblFP_followup ))  and FamilyMemberName like '%"
					+ FamilyMemberName + "%' ";
		} else {
			sql = "select  FamilyMemberName,AgeAsOnYear,DateOfBirth,AprilAgeYear,AprilAgeMonth,HHFamilyMemberGUID,Spouse,Tbl_HHFamilyMember.HHSurveyGUID,Tbl_HHSurvey.hhcode as HHCode from Tbl_HHFamilyMember left join Tbl_HHSurvey on tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID where Tbl_HHFamilyMember.GenderID=2 and Tbl_HHSurvey.serviceproviderid ="
					+ AshaID
					+ " and Tbl_HHFamilyMember.MaritialStatusID=1 and Tbl_HHFamilyMember.StatusID=1 and Tbl_HHFamilyMember.HHFamilyMemberGUID Not in(select  hhfamilymemberguid from tblPregnant_woman where IsPregnant=1 ) order by Tbl_HHSurvey.HHUID ASC";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setFamilyMemberName(cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")));
					form.setHHFamilyMemberGUID(cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")));
					form.setHHSurveyGUID(cursor.getString(cursor
							.getColumnIndex("HHSurveyGUID")));
					form.setHHCode(cursor.getString(cursor
							.getColumnIndex("HHCode")));
					form.setAgeAsOnYear(cursor.getInt(cursor
							.getColumnIndex("AgeAsOnYear")));
					int gap = Validate.CalculateYM(cursor.getInt(cursor
							.getColumnIndex("AgeAsOnYear")));
					int age = cursor.getInt(cursor
							.getColumnIndex("AprilAgeYear"));
					form.setAprilAgeYear(cursor.getInt(cursor
							.getColumnIndex("AprilAgeYear")));
					form.setAprilAgeMonth(cursor.getInt(cursor
							.getColumnIndex("AprilAgeMonth")));
					String sDOB = "";
					if (cursor.getString(cursor.getColumnIndex("DateOfBirth")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfBirth"))
									.equalsIgnoreCase("null")) {
						form.setDateOfBirth("");
					} else {
						form.setDateOfBirth(cursor.getString(cursor
								.getColumnIndex("DateOfBirth")));
						sDOB = cursor.getString(cursor
								.getColumnIndex("DateOfBirth"));
					}

					form.setSpouse(cursor.getString(cursor
							.getColumnIndex("Spouse")));

					if (((age + gap) > 15 && (age + gap) < 49)
							|| (sDOB.length() > 0
									&& Validate.CountAge(sDOB) > 15 && Validate
									.CountAge(sDOB) < 49)) {
						data.add(form);
					}

					// if()

					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tbl_pregnantwomen> getdataanc(int flag, int visit) {
		ArrayList<tbl_pregnantwomen> data = null;
		String sql = "";
		if (flag == 0) {
			sql = "SELECT pwid,pwname,lmpdate,edddate from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null) and ispregnant=1 order by b.pwguid";

		} else if (flag == 1) {
			sql = "SELECT pwid,pwname,lmpdate,edddate  from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where homevisitdate in('',null) and ispregnant=1 order by b.pwguid";

		} else if (flag == 2) {
			sql = "SELECT pwid,pwname,lmpdate,edddate  from tblPregnant_woman where  IsPregnant=1 and cast(round(julianday(EDDDate)-julianday('NOW')+.5) as int) between 0 and 2 ";
		} else if (flag == 4) {
			if (visit == 1) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 0 and 1   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=1)";
			} else if (visit == 2) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 2 and 6   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=2)";
			} else if (visit == 3) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 7 and 13   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=3)";
			} else if (visit == 4) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 14 and 20   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=4)";
			} else if (visit == 5) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 21 and 627   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=5)";
			} else if (visit == 6) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 28 and 41   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=6)";
			} else if (visit == 7) {
				sql = "SELECT c.CHILD_id PWID,c.CHILD_name PWName,c.CHILD_dob LMPDate,'' EDDDate,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 42 and 43   and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=7)";
			}
		} else if (flag == 5) {
			sql = "select uid PWID,womenname PWName,hushband_name LMPDate, '' EDDDate from tblFP_followup where (methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid))";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tbl_pregnantwomen>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_pregnantwomen form = new tbl_pregnantwomen();
					form.setPWID(cursor.getInt(cursor.getColumnIndex("PWID")));
					form.setPWName(cursor.getString(cursor
							.getColumnIndex("PWName")));
					form.setLMPDate(cursor.getString(cursor
							.getColumnIndex("LMPDate")));
					form.setEDDDate(cursor.getString(cursor
							.getColumnIndex("EDDDate")));
					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<Tbl_HHFamilyMember> getAllmalename(String hhguid) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";
		sql = "select FamilyMemberName,Spouse,Education,HHSurveyGUID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
				+ hhguid + "'";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setFamilyMemberName(cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")));
					form.setHHSurveyGUID(cursor.getString(cursor
							.getColumnIndex("HHSurveyGUID")));
					form.setSpouse(cursor.getString(cursor
							.getColumnIndex("Spouse")));
					form.setEducation(cursor.getInt(cursor
							.getColumnIndex("Education")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblhhupdate_Log> getAlldata() {
		ArrayList<tblhhupdate_Log> data = null;
		String sql = "";
		sql = "select * from tblhhupdate_Log where IsEdited=1";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblhhupdate_Log>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblhhupdate_Log form = new tblhhupdate_Log();

					if (cursor.getString(cursor.getColumnIndex("hhsurveyguid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("hhsurveyguid"))
									.equalsIgnoreCase("null")) {
						form.setHhsurveyguid("");
					} else {
						form.setHhsurveyguid(cursor.getString(cursor
								.getColumnIndex("hhsurveyguid")));
					}
					if (cursor.getString(cursor.getColumnIndex("hhmemberguid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("hhmemberguid"))
									.equalsIgnoreCase("null")) {
						form.setHhmemberguid("");
					} else {
						form.setHhmemberguid(cursor.getString(cursor
								.getColumnIndex("hhmemberguid")));
					}
					if (cursor.getString(cursor.getColumnIndex("Aadhar_Old")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Aadhar_Old"))
									.equalsIgnoreCase("null")) {
						form.setAadhar_Old("");
					} else {
						form.setAadhar_Old(cursor.getString(cursor
								.getColumnIndex("Aadhar_Old")));
					}
					if (cursor.getString(cursor.getColumnIndex("Aadhar_New")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Aadhar_New"))
									.equalsIgnoreCase("null")) {
						form.setAadhar_New("");
					} else {
						form.setAadhar_New(cursor.getString(cursor
								.getColumnIndex("Aadhar_New")));
					}
					if (cursor.getString(cursor.getColumnIndex("PhoneNo_Old")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PhoneNo_Old"))
									.equalsIgnoreCase("null")) {
						form.setPhoneNo_Old("");
					} else {
						form.setPhoneNo_Old(cursor.getString(cursor
								.getColumnIndex("PhoneNo_Old")));
					}
					if (cursor.getString(cursor.getColumnIndex("PhoneNo_New")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PhoneNo_New"))
									.equalsIgnoreCase("null")) {
						form.setPhoneNo_New("");
					} else {
						form.setPhoneNo_New(cursor.getString(cursor
								.getColumnIndex("PhoneNo_New")));
					}
					if (cursor.getString(cursor.getColumnIndex("DOB_Old")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DOB_Old"))
									.equalsIgnoreCase("null")) {
						form.setDOB_Old("");
					} else {
						form.setDOB_Old(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DOB_Old"))));
					}
					if (cursor.getString(cursor.getColumnIndex("DOB_New")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DOB_New"))
									.equalsIgnoreCase("null")) {
						form.setDOB_New("");
					} else {
						form.setDOB_New(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DOB_New"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("AccountNo_Old")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AccountNo_Old"))
									.equalsIgnoreCase("null")) {
						form.setAccountNo_Old("");
					} else {
						form.setAccountNo_Old(cursor.getString(cursor
								.getColumnIndex("AccountNo_Old")));
					}
					if (cursor
							.getString(cursor.getColumnIndex("AccountNo_New")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AccountNo_New"))
									.equalsIgnoreCase("null")) {
						form.setAccountNo_New("");
					} else {
						form.setAccountNo_New(cursor.getString(cursor
								.getColumnIndex("AccountNo_New")));
					}
					if (cursor.getString(cursor.getColumnIndex("IFSCCode_Old")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IFSCCode_Old"))
									.equalsIgnoreCase("null")) {
						form.setIFSCCode_Old("");
					} else {
						form.setIFSCCode_Old(cursor.getString(cursor
								.getColumnIndex("IFSCCode_Old")));
					}
					if (cursor.getString(cursor.getColumnIndex("IFSCCode_New")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IFSCCode_New"))
									.equalsIgnoreCase("null")) {
						form.setIFSCCode_New("");
					} else {
						form.setIFSCCode_New(cursor.getString(cursor
								.getColumnIndex("IFSCCode_New")));
					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(cursor.getString(cursor
								.getColumnIndex("UpdatedOn")));
					}

					form.setStatusId_Old(cursor.getInt(cursor
							.getColumnIndex("StatusId_Old")));
					form.setStatusId_New(cursor.getInt(cursor
							.getColumnIndex("StatusId_New")));
					form.setUserID(cursor.getInt(cursor
							.getColumnIndex("UserID")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<q_bank> getAllQuestions(int flag) {
		ArrayList<q_bank> data = null;
		String sql = "";
		if (flag == 1) {
			sql = "select * from tblmstPNCQues where HV1=0 order by Q_id";
		} else if (flag == 2) {
			sql = "select * from tblmstPNCQues where HV2=0 order by Q_id";
		} else if (flag == 3) {
			sql = "select * from tblmstPNCQues where HV3=0 order by Q_id";
		} else if (flag == 4) {
			sql = "select * from tblmstPNCQues where HV4=0 order by Q_id";
		} else if (flag == 5) {
			sql = "select * from tblmstPNCQues where HV5=0 order by Q_id";
		} else if (flag == 6) {
			sql = "select * from tblmstPNCQues where HV6=0 order by Q_id";
		} else if (flag == 7) {
			sql = "select * from tblmstPNCQues where HV7=0 order by Q_id";
		} else if (flag == 8) {
			sql = "select * from tblmstPNCQues where Grp=5 or Grp=7 or Grp=9 order by Q_id";
		} else if (flag == 9) {
			sql = "select * from tblmstPNCQues where Grp=6 or Grp=8 or Grp=10 order by Q_id";
		} else {
			sql = "select * from tblmstPNCQues order by Q_id";
		}

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<q_bank>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					q_bank form = new q_bank();
					form.setQ_id(cursor.getInt(cursor.getColumnIndex("Q_id")));
					form.setQtext(cursor.getString(cursor
							.getColumnIndex("qtext")));
					form.setGrp(cursor.getInt(cursor.getColumnIndex("Grp")));
					form.setQ_type(cursor.getInt(cursor
							.getColumnIndex("q_type")));
					form.setY_qid(cursor.getInt(cursor.getColumnIndex("y_qid")));
					form.setN_qid(cursor.getInt(cursor.getColumnIndex("n_qid")));
					form.setQtext(cursor.getString(cursor
							.getColumnIndex("qtext")));
					form.setOinfo(cursor.getString(cursor
							.getColumnIndex("oinfo")));
					form.setAnsField(cursor.getString(cursor
							.getColumnIndex("AnsField")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblmstimmunizationQues> getAllQuestionsimm(int flag) {
		ArrayList<tblmstimmunizationQues> data = null;
		String sql = "";
		if (flag == 1) {
			sql = "select * from tblmstimmunizationQues  where grp!=1 and grp!=0 order by Q_id ";
		} else if (flag == 2) {
			sql = "select * from tblmstimmunizationQues  where grp!=2 and grp!=0 order by Q_id";
		} else if (flag == 3) {
			sql = "select * from tblmstimmunizationQues  where grp!=3 and grp!=0 order by Q_id ";
		} else if (flag == 4) {
			sql = "select * from tblmstimmunizationQues  where grp!=4 and grp!=0 order by Q_id";
		} else if (flag == 5) {
			sql = "select * from tblmstimmunizationQues  where grp!=5 and grp!=0 order by Q_id";
		} else {
			sql = "select * from tblmstimmunizationQues order by Q_id";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstimmunizationQues>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstimmunizationQues form = new tblmstimmunizationQues();
					form.setQ_id(cursor.getInt(cursor.getColumnIndex("Q_id")));
					form.setFtext(cursor.getString(cursor
							.getColumnIndex("Ftext")));
					form.setGrp(cursor.getInt(cursor.getColumnIndex("Grp")));
					form.setQ_type(cursor.getInt(cursor
							.getColumnIndex("Q_type")));
					form.setY_qid(cursor.getInt(cursor.getColumnIndex("Y_qid")));
					form.setN_qid(cursor.getInt(cursor.getColumnIndex("N_qid")));

					form.setOinfo(cursor.getString(cursor
							.getColumnIndex("Oinfo")));
					form.setAnsField(cursor.getString(cursor
							.getColumnIndex("AnsField")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblmstANCQues> getAllQuestionsanc(int flag) {
		ArrayList<tblmstANCQues> data = null;
		String sql = "";
		if (flag == 1) {
			sql = "select * from tblmstANCQues  where HV1=0";
		} else if (flag == 2) {
			sql = "select * from tblmstANCQues  where HV2=0";
		} else if (flag == 3) {
			sql = "select * from tblmstANCQues  where HV3=0 ";
		} else if (flag == 4) {
			sql = "select * from tblmstANCQues  where HV4=0";
		} else {
			sql = "select * from tblmstANCQues ";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstANCQues>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstANCQues form = new tblmstANCQues();
					form.setQ_id(cursor.getInt(cursor.getColumnIndex("Q_id")));
					form.setFtext(cursor.getString(cursor
							.getColumnIndex("Ftext")));
					form.setGrp(cursor.getInt(cursor.getColumnIndex("Grp")));
					form.setQ_type(cursor.getInt(cursor
							.getColumnIndex("Q_type")));
					form.setY_qid(cursor.getInt(cursor.getColumnIndex("Y_qid")));
					form.setN_qid(cursor.getInt(cursor.getColumnIndex("N_qid")));

					form.setOinfo(cursor.getString(cursor
							.getColumnIndex("Oinfo")));

					form.setAnsField(cursor.getString(cursor
							.getColumnIndex("AnsField")));

					form.setQtext(cursor.getString(cursor
							.getColumnIndex("Qtext")));
					form.setAudio_filename(cursor.getString(cursor
							.getColumnIndex("audio_filename")));
					// form.setAnsField(cursor.getString(cursor
					// .getColumnIndex("AnsField")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<Tbl_HHSurvey> gethhcode(String hhguid) {
		ArrayList<Tbl_HHSurvey> data = null;
		String sql = "";
		sql = "Select * from Tbl_HHSurvey where   HHSurveyGUID='" + hhguid
				+ "'";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHSurvey>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHSurvey form = new Tbl_HHSurvey();
					form.setHHCode(cursor.getString(cursor
							.getColumnIndex("HHCode")));
					form.setHHSurveyGUID(cursor.getString(cursor
							.getColumnIndex("HHSurveyGUID")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblMigration> geMigrationData(int Flag,
			String HHFamilyMemberGUID, String MigrationGUID) {
		ArrayList<tblMigration> data = null;
		String sql = "";
		if (Flag == 1) {
			sql = "Select * from tblMigration where   HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "' and MigrationGUID='"
					+ MigrationGUID + "'";
		} else if (Flag == 2) {
			sql = "Select * from tblMigration where IsEdited=1";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblMigration>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblMigration form = new tblMigration();
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor
							.getString(cursor.getColumnIndex("MigrationGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MigrationGUID"))
									.equalsIgnoreCase("null")) {
						form.setMigrationGUID("");
					} else {
						form.setMigrationGUID(cursor.getString(cursor
								.getColumnIndex("MigrationGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("DateOfMigrationIn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfMigrationIn"))
									.equalsIgnoreCase("null")) {
						form.setDateOfMigrationIn("");
					} else {
						form.setDateOfMigrationIn(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("DateOfMigrationIn"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("DateOfMigrationOut")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("DateOfMigrationOut"))
									.equalsIgnoreCase("null")) {
						form.setDateOfMigrationOut("");
					} else {
						form.setDateOfMigrationOut(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("DateOfMigrationOut"))));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn"))));
					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UpdatedOn"))));
					}

					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					form.setUpdatedBy(cursor.getInt(cursor
							.getColumnIndex("UpdatedBy")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in tblMigration :: " + exception.getMessage());
		}
		return data;

	}

	public void savepncdata(int AshaID, int ANMID, String dtaans,
			String ChildGUID, String pncguid, int visitno, String ansfield,
			String flag, String id) {
		String sql = "";
		if (flag == "i") {
			String CreatedOn = Validate.getcurrentdate();
			sql = "insert into tblPNChomevisit_ANS ("
					+ ansfield
					+ ",ChildGUID,PNCGUID,VisitNo,IsEdited,CreatedOn,CreatedBy,AshaID,ANMID)values('"
					+ dtaans + "','" + ChildGUID + "','" + pncguid + "',"
					+ visitno + ",1,'" + CreatedOn + "','" + id + "'," + AshaID
					+ "," + ANMID + ")";
		} else {
			String UpdatedOn = Validate.getcurrentdate();
			sql = "update tblPNChomevisit_ANS set " + ansfield + "='" + dtaans
					+ "' ,UpdatedOn='" + UpdatedOn + "',UpdatedBy='" + id
					+ "',IsEdited=1 ,ANMID=" + ANMID + ",AshaID=" + AshaID
					+ " where ChildGUID='" + ChildGUID + "' and PNCGUID='"
					+ pncguid + "' and visitno=" + visitno + "";
		}
		try {
			executeSql(sql);
			System.out.println(sql + "query");
		} catch (Exception e) {
			System.out.println(e + "error in executing ");
		}

	}

	public void saveimmunizationdata(int AshaID, int ANMID, String dtaans,
			String ChildGUID, String ImmunizationGUID, String ansfield,
			String flag, int CreatedBy, String CreatedOn) {
		String sql = "";
		if (flag == "i") {
			sql = "insert into tblmstimmunizationANS (AshaID,ANMID,"
					+ ansfield
					+ ",ChildGUID,ImmunizationGUID,IsEdited,CreatedBy,CreatedOn)values("
					+ AshaID + "," + ANMID + ",'" + dtaans + "','" + ChildGUID
					+ "','" + ImmunizationGUID + "',1,'" + CreatedBy + "','"
					+ CreatedOn + "')";
		} else {
			sql = "update tblmstimmunizationANS set AshaID=" + AshaID
					+ ",ANMID=" + ANMID + ", " + ansfield + "='" + dtaans
					+ "',IsEdited=1,UpdatedOn='" + CreatedOn
					+ "'  ,UpdatedBy='" + CreatedBy + "' where ChildGUID='"
					+ ChildGUID + "' and ImmunizationGUID='" + ImmunizationGUID
					+ "' ";
		}
		try {
			executeSql(sql);
			System.out.println(sql + "query");
		} catch (Exception e) {
			System.out.println(e + "error in executing ");
		}

	}

	public void saveancvisitdata(String dtaans, String ANCGUID,
			String VisitDate, String PWGUID, String ansfield, String flag) {
		String sql = "";

		sql = "update tblANCVisit set " + ansfield + "='" + dtaans
				+ "',HomeVisitDate='" + VisitDate
				+ "',IsEdited=1 where VisitGUID='" + ANCGUID + "' and PWGUID='"
				+ PWGUID + "' ";

		try {
			executeSql(sql);
			System.out.println(sql + "query");
		} catch (Exception e) {
			System.out.println(e + "error in executing ");
		}

	}

	public ArrayList<MstSubCenter> getMstSubCenter(int iLanguage) {

		ArrayList<MstSubCenter> data = null;
		String sql = "";
		sql = "Select * from MstSubCenter where LanguageID = " + iLanguage
				+ " ";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstSubCenter>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstSubCenter form = new MstSubCenter();
					form.setSubCenterID(cursor.getInt(cursor
							.getColumnIndex("SubCenterID")));
					form.setSubCenterCode(cursor.getString(cursor
							.getColumnIndex("SubCenterCode")));
					form.setSubCenterName(cursor.getString(cursor
							.getColumnIndex("SubCenterName")));
					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblPNChomevisit_ANS> getpncdata(String PWGUID,
			String PncGUID, int visitno, int flag) {

		ArrayList<tblPNChomevisit_ANS> data = null;
		String sql = "";
		if (flag == 1) {
			sql = "Select * from tblPNChomevisit_ANS where ChildGUID = '"
					+ PncGUID + "' and VisitNo=" + visitno + " ";
		} else if (flag == 2) {
			sql = "Select * from tblPNChomevisit_ANS where PWGUID = '" + PWGUID
					+ "' and VisitNo=" + visitno + " and PNCGUID='" + PncGUID
					+ "' ";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblPNChomevisit_ANS>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblPNChomevisit_ANS form = new tblPNChomevisit_ANS();
					form.setPNCGUID(cursor.getString(cursor
							.getColumnIndex("PNCGUID")));
					form.setVisitNo(cursor.getInt(cursor
							.getColumnIndex("VisitNo")));
					form.setQ_0(cursor.getString(cursor.getColumnIndex("Q_0")));
					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider", "Error in Tbl_PncHomeVisitAns :: "
					+ exception.getMessage());
		}
		return data;

	}

	public ArrayList<MstANM> getMstANMname(int iLanguage) {

		ArrayList<MstANM> data = null;
		String sql = "";
		sql = "Select * from MstANM where LanguageID =" + iLanguage + " ";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstANM>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstANM form = new MstANM();
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setANMCode(cursor.getString(cursor
							.getColumnIndex("ANMCode")));
					form.setANMName(cursor.getString(cursor
							.getColumnIndex("ANMName")));
					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<MstVillage> getMstVillageName(int iLanguage) {

		ArrayList<MstVillage> data = null;
		String sql = "";
		sql = "Select * from MstVillage where LanguageID =" + iLanguage + " ";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstVillage>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstVillage form = new MstVillage();
					form.setVillageID(cursor.getInt(cursor
							.getColumnIndex("VillageID")));
					form.setVillageCode(cursor.getString(cursor
							.getColumnIndex("VillageCode")));
					form.setVillageName(cursor.getString(cursor
							.getColumnIndex("VillageName")));
					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstVillageName :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<MstASHA> getMstASHAname(int LanguageID) {

		ArrayList<MstASHA> data = null;
		String sql = "";
		sql = "Select * from MstASHA where LanguageID =" + LanguageID + "  ";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstASHA>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstASHA form = new MstASHA();
					form.setASHAID(cursor.getInt(cursor
							.getColumnIndex("ASHAID")));
					form.setASHACode(cursor.getString(cursor
							.getColumnIndex("ASHACode")));
					form.setASHAName(cursor.getString(cursor
							.getColumnIndex("ASHAName")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstASHAName :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<MstCommon> getCommonRecord(int languageID, int iFlag) {

		ArrayList<MstCommon> data = null;
		String sql = "";
		sql = "select * from MStCommon where LanguageID = " + languageID
				+ " and Flag = " + iFlag + " order by ID";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstCommon>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstCommon form = new MstCommon();
					form.setID(cursor.getInt(cursor.getColumnIndex("ID")));
					form.setValue(cursor.getString(cursor
							.getColumnIndex("Value")));

					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in Relation :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<Tbl_HHSurvey> getHHSurveyData(String sHHSurveyGUID,
			int iFlag, int ashaid) {

		ArrayList<Tbl_HHSurvey> surveydata = null;
		String sql = "";
		if (iFlag == 1) {
			sql = "select * from Tbl_HHSurvey where ServiceProviderID = "
					+ ashaid + " order by HHUID ASC";
		} else if (iFlag == 2) {
			sql = "select * from Tbl_HHSurvey where HHSurveyGUID = '"
					+ sHHSurveyGUID + "'";
		} else if (iFlag == 3) {
			sql = "select * from Tbl_HHSurvey where HHCode = '" + sHHSurveyGUID
					+ "' and ServiceProviderID = " + ashaid + "";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				surveydata = new ArrayList<Tbl_HHSurvey>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHSurvey form = new Tbl_HHSurvey();
					form.setHHUID(cursor.getInt(cursor.getColumnIndex("HHUID")));
					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}
					form.setSubCenterID(cursor.getInt(cursor
							.getColumnIndex("SubCenterID")));

					int abc = cursor
							.getInt(cursor.getColumnIndex("HHStatusID"));
					form.setHHStatusID(abc);

					form.setHHCode(cursor.getString(cursor
							.getColumnIndex("HHCode")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setVillageID(cursor.getInt(cursor
							.getColumnIndex("VillageID")));
					form.setServiceProviderID(cursor.getInt(cursor
							.getColumnIndex("ServiceProviderID")));
					form.setFamilyCode(cursor.getInt(cursor
							.getColumnIndex("FamilyCode")));

					form.setCasteID(cursor.getInt(cursor
							.getColumnIndex("CasteID")));
					form.setFinancialStatusID(cursor.getInt(cursor
							.getColumnIndex("FinancialStatusID")));
					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));
					}
					form.setUploadedBy(cursor.getInt(cursor
							.getColumnIndex("UploadedBy")));
					if (cursor.getString(cursor.getColumnIndex("UploadedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UploadedOn"))
									.equalsIgnoreCase("null")) {
						form.setUploadedOn("");
					} else {
						form.setUploadedOn(cursor.getString(cursor
								.getColumnIndex("UploadedOn")));
					}
					form.setIsTablet(cursor.getInt(cursor
							.getColumnIndex("IsTablet")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setCHS_ID(cursor.getInt(cursor
							.getColumnIndex("CHS_ID")));
					if (cursor.getString(cursor.getColumnIndex("HHShortName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHShortName"))
									.equalsIgnoreCase("null")) {
						form.setHHShortName("");
					} else {
						form.setHHShortName(cursor.getString(cursor
								.getColumnIndex("HHShortName")));
					}
					form.setReligionID(cursor.getInt(cursor
							.getColumnIndex("ReligionID")));
					surveydata.add(form);
					if (cursor
							.getString(cursor.getColumnIndex("MigrateInDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MigrateInDate"))
									.equalsIgnoreCase("null")) {
						form.setMigrateInDate("");
					} else {
						form.setMigrateInDate(cursor.getString(cursor
								.getColumnIndex("MigrateInDate")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("MigrateOutDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MigrateOutDate"))
									.equalsIgnoreCase("null")) {
						form.setMigrateOutDate("");
					} else {
						form.setMigrateOutDate(cursor.getString(cursor
								.getColumnIndex("MigrateOutDate")));
					}

					cursor.moveToNext();

				}
				cursor.close();
			}
			return surveydata;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in surveydata :: " + exception.getMessage());
		}
		return surveydata;

	}

	public ArrayList<Tbl_HHSurvey> getTblSurveyUpload(String hhguid) {
		ArrayList<Tbl_HHSurvey> data = null;
		String sql = "";
		sql = "Select *  from Tbl_HHSurvey where IsEdited=1 and HHSurveyGUID='"
				+ hhguid + "' ";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHSurvey>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHSurvey form = new Tbl_HHSurvey();
					form.setHHUID(cursor.getInt(cursor.getColumnIndex("HHUID")));
					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("HHCode")) == null
							|| cursor
									.getString(cursor.getColumnIndex("HHCode"))
									.equalsIgnoreCase("null")) {
						form.setHHCode("");
					} else {
						form.setHHCode(cursor.getString(cursor
								.getColumnIndex("HHCode")));
					}
					if (cursor
							.getString(cursor.getColumnIndex("MigrateInDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MigrateInDate"))
									.equalsIgnoreCase("null")) {
						form.setMigrateInDate("");
					} else {
						form.setMigrateInDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("MigrateInDate"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("MigrateOutDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MigrateOutDate"))
									.equalsIgnoreCase("null")) {
						form.setMigrateOutDate("");
					} else {
						form.setMigrateOutDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("MigrateOutDate"))));
					}
					form.setSubCenterID(cursor.getInt(cursor
							.getColumnIndex("SubCenterID")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setVillageID(cursor.getInt(cursor
							.getColumnIndex("VillageID")));
					form.setReligionID(cursor.getInt(cursor
							.getColumnIndex("ReligionID")));
					form.setServiceProviderID(cursor.getInt(cursor
							.getColumnIndex("ServiceProviderID")));
					form.setCasteID(cursor.getInt(cursor
							.getColumnIndex("CasteID")));
					form.setFamilyCode(cursor.getInt(cursor
							.getColumnIndex("FamilyCode")));
					form.setHHStatusID(cursor.getInt(cursor
							.getColumnIndex("HHStatusID")));
					form.setFinancialStatusID(cursor.getInt(cursor
							.getColumnIndex("FinancialStatusID")));
					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn((Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn")))));
					}
					if (cursor.getString(cursor.getColumnIndex("UploadedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UploadedBy"))
									.equalsIgnoreCase("null")) {

						form.setUploadedBy(cursor.getInt(cursor
								.getColumnIndex("UploadedBy")));
					} else {
						form.setUploadedBy(0);
					}
					if (cursor.getString(cursor.getColumnIndex("HHShortName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHShortName"))
									.equalsIgnoreCase("null")) {
						form.setHHShortName("");
					} else {
						form.setHHShortName(cursor.getString(cursor
								.getColumnIndex("HHShortName")));
					}
					if (cursor.getString(cursor.getColumnIndex("Longitude")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Longitude"))
									.equalsIgnoreCase("null")) {
						form.setLongitude("");
					} else {
						form.setLongitude(cursor.getString(cursor
								.getColumnIndex("Longitude")));
					}
					if (cursor.getString(cursor.getColumnIndex("Latitude")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Latitude"))
									.equalsIgnoreCase("null")) {
						form.setLatitude("");
					} else {
						form.setLatitude(cursor.getString(cursor
								.getColumnIndex("Latitude")));
					}
					if (cursor.getString(cursor.getColumnIndex("Location")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Location"))
									.equalsIgnoreCase("null")) {
						form.setLocation("");
					} else {
						form.setLocation(cursor.getString(cursor
								.getColumnIndex("Location")));
					}
					if (cursor.getString(cursor.getColumnIndex("UploadedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UploadedOn"))
									.equalsIgnoreCase("null")) {
						form.setUploadedOn("");
					} else {
						form.setUploadedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UploadedOn"))));
					}
					form.setVerified(cursor.getInt(cursor
							.getColumnIndex("Verified")));
					form.setIsTablet(cursor.getInt(cursor
							.getColumnIndex("IsTablet")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<Tbl_HHSurvey> getTblSurveyUploadcount() {
		ArrayList<Tbl_HHSurvey> data = null;
		String sql = "";
		sql = "Select HHSurveyGUID  from Tbl_HHSurvey where IsEdited=1 ";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHSurvey>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHSurvey form = new Tbl_HHSurvey();

					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<Tbl_HHFamilyMember> getHHFamilymemberData(
			String sHHSurveyGUID, String HHFamilyMemberGUID, int Flag) {

		ArrayList<Tbl_HHFamilyMember> familydata = null;
		String sql = "";
		if (Flag == 2) {
			sql = "select * from Tbl_HHFamilyMember where HHSurveyGUID = '"
					+ sHHSurveyGUID + "' and HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "'";

		} else if (Flag == 1) {
			sql = "select * from Tbl_HHFamilyMember where HHSurveyGUID = '"
					+ sHHSurveyGUID + "' and StatusID!=2";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				familydata = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setHHFamilyMemberUID(cursor.getInt(cursor
							.getColumnIndex("HHFamilyMemberUID")));
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberCode")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberCode"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberCode("");
					} else {
						form.setHHFamilyMemberCode(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberCode")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("UniqueIDNumber")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UniqueIDNumber"))
									.equalsIgnoreCase("null")) {
						form.setUniqueIDNumber("");
					} else {
						form.setUniqueIDNumber(cursor.getString(cursor
								.getColumnIndex("UniqueIDNumber")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}

					form.setRelationID(cursor.getInt(cursor
							.getColumnIndex("RelationID")));

					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));

					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setEducation(cursor.getInt(cursor
							.getColumnIndex("Education")));
					form.setStatusID(cursor.getInt(cursor
							.getColumnIndex("StatusID")));
					form.setGenderID(cursor.getInt(cursor
							.getColumnIndex("GenderID")));
					form.setMaritialStatusID(cursor.getInt(cursor
							.getColumnIndex("MaritialStatusID")));
					form.setDOBAvailable(cursor.getInt(cursor
							.getColumnIndex("DOBAvailable")));
					if (cursor.getString(cursor.getColumnIndex("DateOfBirth")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfBirth"))
									.equalsIgnoreCase("null")) {
						form.setDateOfBirth("");
					} else {
						form.setDateOfBirth(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateOfBirth"))));
					}

					form.setAgeAsOnYear(cursor.getInt(cursor
							.getColumnIndex("AgeAsOnYear")));
					form.setAprilAgeYear(cursor.getInt(cursor
							.getColumnIndex("AprilAgeYear")));
					form.setAprilAgeMonth(cursor.getInt(cursor
							.getColumnIndex("AprilAgeMonth")));

					if (cursor.getString(cursor.getColumnIndex("MotherGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MotherGUID"))
									.equalsIgnoreCase("null")) {
						form.setMotherGUID("");
					} else {
						form.setMotherGUID(cursor.getString(cursor
								.getColumnIndex("MotherGUID")));
					}
					form.setTargetID(cursor.getInt(cursor
							.getColumnIndex("TargetID")));
					if (cursor.getString(cursor.getColumnIndex("DateOfDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfDeath"))
									.equalsIgnoreCase("null")) {
						form.setDateOfDeath("");
					} else {
						form.setDateOfDeath(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateOfDeath"))));
					}
					form.setPlaceOfDeath(cursor.getInt(cursor
							.getColumnIndex("PlaceOfDeath")));

					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn"))));
					}
					form.setUploadedBy(cursor.getInt(cursor
							.getColumnIndex("UploadedBy")));
					if (cursor.getString(cursor.getColumnIndex("UploadedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UploadedOn"))
									.equalsIgnoreCase("null")) {
						form.setUploadedOn("");
					} else {
						form.setUploadedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UploadedOn"))));
					}
					form.setIsTablet(cursor.getInt(cursor
							.getColumnIndex("IsTablet")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));

					if (cursor.getString(cursor.getColumnIndex("Father")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Father"))
									.equalsIgnoreCase("null")) {
						form.setFather("");
					} else {
						form.setFather(cursor.getString(cursor
								.getColumnIndex("Father")));
					}

					if (cursor.getString(cursor.getColumnIndex("Mother")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Mother"))
									.equalsIgnoreCase("null")) {
						form.setMother("");
					} else {
						form.setMother(cursor.getString(cursor
								.getColumnIndex("Mother")));
					}

					if (cursor.getString(cursor.getColumnIndex("Spouse")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Spouse"))
									.equalsIgnoreCase("null")) {
						form.setSpouse("");
					} else {
						form.setSpouse(cursor.getString(cursor
								.getColumnIndex("Spouse")));
					}

					if (cursor.getString(cursor.getColumnIndex("TempMember")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TempMember"))
									.equalsIgnoreCase("null")) {
						form.setTempMember("");
					} else {
						form.setTempMember(cursor.getString(cursor
								.getColumnIndex("TempMember")));
					}

					familydata.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return familydata;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in surveydata :: " + exception.getMessage());
		}
		return familydata;

	}

	public ArrayList<Tbl_HHFamilyMember> getHHFamilyMemberUpload(String hhguid) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";
		sql = "Select *  from Tbl_HHFamilyMember where IsEdited=1 and HHSurveyGUID='"
				+ hhguid + "'";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setHHFamilyMemberUID(cursor.getInt(cursor
							.getColumnIndex("HHFamilyMemberUID")));
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("DateOfDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfDeath"))
									.equalsIgnoreCase("null")) {
						form.setDateOfDeath("");
					} else {
						form.setDateOfDeath(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateOfDeath"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberCode")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberCode"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberCode("");
					} else {
						form.setHHFamilyMemberCode(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberCode")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("UniqueIDNumber")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UniqueIDNumber"))
									.equalsIgnoreCase("null")) {
						form.setUniqueIDNumber("");
					} else {
						form.setUniqueIDNumber(cursor.getString(cursor
								.getColumnIndex("UniqueIDNumber")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}

					form.setRelationID(cursor.getInt(cursor
							.getColumnIndex("RelationID")));
					form.setPlaceOfDeath(cursor.getInt(cursor
							.getColumnIndex("PlaceOfDeath")));
					form.setStatusID(cursor.getInt(cursor
							.getColumnIndex("StatusID")));
					form.setGenderID(cursor.getInt(cursor
							.getColumnIndex("GenderID")));
					form.setMaritialStatusID(cursor.getInt(cursor
							.getColumnIndex("MaritialStatusID")));
					form.setDOBAvailable(cursor.getInt(cursor
							.getColumnIndex("DOBAvailable")));
					form.setDeathVillage(cursor.getInt(cursor
							.getColumnIndex("DeathVillage")));
					if (cursor.getString(cursor.getColumnIndex("DateOfBirth")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateOfBirth"))
									.equalsIgnoreCase("null")) {
						form.setDateOfBirth("");
					} else {
						form.setDateOfBirth(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateOfBirth"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("NameofDeathplace")) == null
							|| cursor.getString(
									cursor.getColumnIndex("NameofDeathplace"))
									.equalsIgnoreCase("null")) {
						form.setNameofDeathplace("");
					} else {
						form.setNameofDeathplace(cursor.getString(cursor
								.getColumnIndex("NameofDeathplace")));
					}

					form.setAgeAsOnYear(cursor.getInt(cursor
							.getColumnIndex("AgeAsOnYear")));
					form.setAprilAgeYear(cursor.getInt(cursor
							.getColumnIndex("AprilAgeYear")));
					form.setAprilAgeMonth(cursor.getInt(cursor
							.getColumnIndex("AprilAgeMonth")));

					if (cursor.getString(cursor.getColumnIndex("MotherGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MotherGUID"))
									.equalsIgnoreCase("null")) {
						form.setMotherGUID("");
					} else {
						form.setMotherGUID(cursor.getString(cursor
								.getColumnIndex("MotherGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Father")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Father"))
									.equalsIgnoreCase("null")) {
						form.setFather("");
					} else {
						form.setFather(cursor.getString(cursor
								.getColumnIndex("Father")));
					}
					if (cursor.getString(cursor.getColumnIndex("Mother")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Mother"))
									.equalsIgnoreCase("null")) {
						form.setMother("");
					} else {
						form.setMother(cursor.getString(cursor
								.getColumnIndex("Mother")));
					}
					if (cursor.getString(cursor.getColumnIndex("Spouse")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Spouse"))
									.equalsIgnoreCase("null")) {
						form.setSpouse("");
					} else {
						form.setSpouse(cursor.getString(cursor
								.getColumnIndex("Spouse")));
					}
					if (cursor.getString(cursor.getColumnIndex("TempMember")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TempMember"))
									.equalsIgnoreCase("null")) {
						form.setTempMember("");
					} else {
						form.setTempMember(cursor.getString(cursor
								.getColumnIndex("TempMember")));
					}
					form.setTargetID(cursor.getInt(cursor
							.getColumnIndex("TargetID")));

					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn((Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn")))));
					}
					form.setUploadedBy(cursor.getInt(cursor
							.getColumnIndex("UploadedBy")));
					if (cursor.getString(cursor.getColumnIndex("UploadedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UploadedOn"))
									.equalsIgnoreCase("null")) {
						form.setUploadedOn("");
					} else {
						form.setUploadedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UploadedOn"))));
					}
					form.setIsTablet(cursor.getInt(cursor
							.getColumnIndex("IsTablet")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public void UpdateHousehold(String date, int SubCenter, int FamilySNo,
			int ANMid, int Villageid, int HHStatusID, int ServiceProviderID,
			int Casteid, int FinancialStatusid, String UpdatedOn,
			String UserID, String HHSurveyGUID, String iHHShortName,
			int ireligionid, int facilatorid, String Latitude,
			String Longitude, String Location, String MigrateInDate,
			String MigrateOutDate) {
		// TODO Auto-generated method stub
		String sql = "Update Tbl_HHSurvey set SubCenterID='" + SubCenter
				+ "',FamilyCode=" + FamilySNo + ",ANMID=" + ANMid
				+ ",VillageID=" + Villageid + ",HHStatusID=" + HHStatusID
				+ ",CasteID=" + Casteid + ",FinancialStatusID="
				+ FinancialStatusid + ",ServiceProviderID=" + ServiceProviderID
				+ ",UploadedOn='" + date + "',UploadedBy='" + UserID
				+ "',IsEdited=1 ,IsUploaded=0,HHShortName='" + iHHShortName
				+ "',ReligionID=" + ireligionid + ",  CHS_ID=" + facilatorid
				+ ",Latitude='" + Latitude + "',Longitude='" + Longitude
				+ "',Location='" + Location + "',MigrateInDate='"
				+ MigrateInDate + "',MigrateOutDate='" + MigrateOutDate
				+ "'  where HHSurveyGUID='" + HHSurveyGUID + "'";
		// String sql1="Update  Tbl_HHFamilyMember set IsEdited=1 where ";
		try {
			executeSql(sql);
		} catch (Exception e) {
			System.out.println("error in tblHHSurvey  " + e);
		}
	}

	public void SaveSurveyData(String date, int subCenter, int familySNo,
			int aNMid, int villageid, int HHStatusID, int ServiceProviderID,
			int casteid, int financialStatusid, String UserID,
			String hhSurveyGUID, String HHCode, String shortname,
			int religionid, int facilatorid, String Latitude, String Longitude,
			String Location, String MigrateInDate, String MigrateOutDate) {
		// TODO Auto-generated method stub
		String sql = "";
		sql = "Insert into Tbl_HHSurvey(SubCenterID,FamilyCode,MigrateInDate,MigrateOutDate,ANMID,VillageID,HHStatusID,ServiceProviderID,CasteID,FinancialStatusID,CreatedOn,CreatedBy,HHSurveyGUID,HHCode,IsTablet,IsEdited,IsUploaded,HHShortName,ReligionID,CHS_ID,Latitude,Longitude,Location)values('"
				+ subCenter
				+ "',"
				+ familySNo
				+ ",'"
				+ MigrateInDate
				+ "','"
				+ MigrateOutDate
				+ "',"
				+ aNMid
				+ ","
				+ villageid
				+ ","
				+ HHStatusID
				+ ","
				+ ServiceProviderID
				+ ","
				+ casteid
				+ ","
				+ financialStatusid
				+ ",'"
				+ date
				+ "','"
				+ UserID
				+ "','"
				+ hhSurveyGUID
				+ "','"
				+ HHCode
				+ "',1,1,0,'"
				+ shortname
				+ "',"
				+ religionid
				+ ","
				+ facilatorid
				+ ",'"
				+ Latitude
				+ "','" + Longitude + "','" + Location + "')";
		try {
			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage().toString();
		}
	}

	public void UpdateFamilyDetails(int Education, int AshaID, int ANMID,
			String CurrentDate, String uID, String familyMemb,
			int DOBAvailable, String dateOfBirth, int AgeYearTillApril,
			int AgeMonthTillApril, int AgeAsOnYear, String motherName,
			int relationid, int StatusID, int genderid, int maritalStatusid,
			int targetid, String updatedOn, String globalHHSurveyGUID,
			String globalHHFamilyMemberGUID, String UserID) {
		// TODO Auto-generated method stub
		String sql = "Update Tbl_HHFamilyMember set UniqueIDNumber='" + uID
				+ "', FamilyMemberName='" + familyMemb + "',DOBAvailable="
				+ DOBAvailable + ",DateOfBirth='" + dateOfBirth
				+ "',AprilAgeYear=" + AgeYearTillApril + ",Education="
				+ Education + ",AprilAgeMonth=" + AgeMonthTillApril
				+ ",AgeAsOnYear=" + AgeAsOnYear + ",MotherGUID='" + motherName
				+ "',RelationID=" + relationid + ",StatusID=" + StatusID
				+ ",GenderID=" + genderid + ",MaritialStatusID="
				+ maritalStatusid + ",TargetID=" + targetid + ",UploadedOn='"
				+ CurrentDate + "',UploadedBy='" + UserID
				+ "',IsEdited=1,AshaID=" + AshaID + ",ANMID=" + ANMID
				+ ",IsUploaded=0   where HHSurveyGUID='" + globalHHSurveyGUID
				+ "' and HHFamilyMemberGUID='" + globalHHFamilyMemberGUID + "'";
		String sql1 = "Update Tbl_HHSurvey set IsEdited=1  where HHSurveyGUID='"
				+ globalHHSurveyGUID + "'";

		try {
			dbIntraHealth.execSQL(sql1);
			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateDeathDetails(String globalHHSurveyGUID,
			String DateOfDeath, int PlaceOfDeath, String NameofDeathplace,
			int DeathVillage, String globalHHFamilyMemberGUID) {
		// TODO Auto-generated method stub
		String sql = "Update Tbl_HHFamilyMember set DateOfDeath='"
				+ DateOfDeath + "', NameofDeathplace='" + NameofDeathplace
				+ "',PlaceOfDeath=" + PlaceOfDeath + ",DeathVillage="
				+ DeathVillage
				+ ",IsEdited=1,IsUploaded=0   where HHSurveyGUID='"
				+ globalHHSurveyGUID + "' and HHFamilyMemberGUID='"
				+ globalHHFamilyMemberGUID + "'";
		String sql1 = "Update Tbl_HHSurvey set IsEdited=1  where HHSurveyGUID='"
				+ globalHHSurveyGUID + "'";
		dbIntraHealth.execSQL(sql1);
		sql1 = "update Tbl_HHFamilyMember set StatusID=2 where HHFamilyMemberGUID='"
				+ globalHHFamilyMemberGUID + "'";

		try {
			dbIntraHealth.execSQL(sql1);
			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void saveMigrationDetails(String Flag, String CreatedOn,
			int CreatedBy, String DateOfMigrationIn, String DateOfMigrationOut,
			String MigrationGUID, String HHFamilyMemberGUID) {
		String sql = "";
		String sql1 = "";
		if (Flag == "i") {
			sql = "insert into tblMigration (HHFamilyMemberGUID, DateOfMigrationIn,DateOfMigrationOut,MigrationGUID,CreatedOn,CreatedBy,IsEdited,IsUploaded) Values('"
					+ HHFamilyMemberGUID
					+ "', '"
					+ DateOfMigrationIn
					+ "','"
					+ DateOfMigrationOut
					+ "','"
					+ MigrationGUID
					+ "','"
					+ CreatedOn + "'," + CreatedBy + ",1,0)";
			sql1 = "update Tbl_HHFamilyMember set StatusID=3 where HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "'";
		} else {
			sql = "update tblMigration set DateOfMigrationIn='"
					+ DateOfMigrationIn + "', UpdatedOn='" + CreatedOn
					+ "',UpdatedBy=" + CreatedBy
					+ " ,IsEdited=1,IsUploaded=0 where HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "' and MigrationGUID='"
					+ MigrationGUID + "'";
			sql1 = "update Tbl_HHFamilyMember set StatusID=1 where HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "'";
		}
		try {

			executeSql(sql);
			executeSql(sql1);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e + "error");
		}
	}

	public void InsertFamilyDetails(int Education, int AshaID, int ANMID,
			String date, String uID, String familyMemb, int DOBAvailable,
			String dateOfBirth, int AgeYearTillApril, int AgeMonthTillApril,
			int AgeAsOnYear, String motherName, int relationid, int StatusID,
			int genderid, int maritalStatusid, int targetid, String updatedOn,
			String globalHHSurveyGUID, String familyGUID, String UserID) {
		// TODO Auto-generated method stub
		String sql = "Insert into Tbl_HHFamilyMember(Education,AshaID,ANMID,UniqueIDNumber,FamilyMemberName,DOBAvailable,DateOfBirth,AprilAgeYear,AgeAsOnYear,AprilAgeMonth,MotherGUID,RelationID,StatusID,GenderID,MaritialStatusID,TargetID,CreatedOn,CreatedBy,IsTablet,IsEdited,IsUploaded,HHFamilyMemberGUID,HHSurveyGUID) values("
				+ Education
				+ ","
				+ AshaID
				+ ","
				+ ANMID
				+ ",'"
				+ uID
				+ "','"
				+ familyMemb
				+ "',"
				+ DOBAvailable
				+ ",'"
				+ dateOfBirth
				+ "',"
				+ AgeYearTillApril
				+ ","
				+ AgeAsOnYear
				+ ","
				+ AgeMonthTillApril
				+ ",'"
				+ motherName
				+ "',"
				+ relationid
				+ ","
				+ StatusID
				+ ","
				+ genderid
				+ ","
				+ maritalStatusid
				+ ","
				+ targetid
				+ ",'"
				+ date
				+ "','"
				+ UserID
				+ "',1,1,0,'"
				+ familyGUID + "','" + globalHHSurveyGUID + "') ";
		try {
			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public ArrayList<MstCatchmentSupervisor> getCatchsupervisor(int languageID) {

		ArrayList<MstCatchmentSupervisor> data = null;
		String sql = "";
		sql = "select * from MstCatchmentSupervisor where LanguageID = "
				+ languageID + "";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<MstCatchmentSupervisor>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstCatchmentSupervisor form = new MstCatchmentSupervisor();
					form.setCHS_ID(cursor.getInt(cursor
							.getColumnIndex("CHS_ID")));
					form.setSubCenterID(cursor.getInt(cursor
							.getColumnIndex("SubCenterID")));
					form.setSupervisorCode(cursor.getString(cursor
							.getColumnIndex("SupervisorCode")));
					form.setSupervisorName(cursor.getString(cursor
							.getColumnIndex("SupervisorName")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("LanguageID")));
					data.add(form);
					cursor.moveToNext();

				}
				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in Relation :: " + exception.getMessage());
		}
		return data;

	}

	public void savedates(String flag, String HHSurveyGUID,
			String HHFamilyMemberGUID, String PWGUID, String LmpDate,
			String EDD, String TT1Date, String TT2Date, String AncCheckupdate1,
			String AncCheckupdate2, String AncCheckupdate3,
			String AncCheckupdate4, int IsEdited, String CreatedOn,
			int CreatedBy, int AshaID, int ANMID) {
		String sql = "";
		if (flag == "i") {
			sql = "insert into tbl_DatesEd( HHSurveyGUID,  HHFamilyMemberGUID, PWGUID,LmpDate, EDD, TT1Date, TT2Date,  AncCheckupdate1,  AncCheckupdate2,  AncCheckupdate3,  AncCheckupdate4,  IsEdited,  CreatedOn,  CreatedBy,   AshaID,  ANMID)values( '"
					+ HHSurveyGUID
					+ "',  '"
					+ HHFamilyMemberGUID
					+ "', '"
					+ PWGUID
					+ "','"
					+ LmpDate
					+ "','"
					+ EDD
					+ "','"
					+ TT1Date
					+ "', '"
					+ TT2Date
					+ "',  '"
					+ AncCheckupdate1
					+ "',  '"
					+ AncCheckupdate2
					+ "', '"
					+ AncCheckupdate3
					+ "',  '"
					+ AncCheckupdate4
					+ "',  "
					+ IsEdited
					+ ",  '"
					+ CreatedOn
					+ "',   "
					+ CreatedBy
					+ ",   "
					+ AshaID
					+ ",  "
					+ ANMID
					+ ")";
		} else {
			sql = "update tbl_DatesEd set LmpDate='" + LmpDate + "',EDD='"
					+ EDD + "',TT1Date='" + TT1Date + "', TT2Date='" + TT2Date
					+ "',  AncCheckupdate1='" + AncCheckupdate1
					+ "',  AncCheckupdate2='" + AncCheckupdate2
					+ "', AncCheckupdate3='" + AncCheckupdate3
					+ "',  AncCheckupdate4='" + AncCheckupdate4
					+ "',  IsEdited=" + IsEdited + ",  UpdatedOn='" + CreatedOn
					+ "',  UpdatedBy= " + CreatedBy + ",   AshaID=" + AshaID
					+ ", ANMID= " + ANMID + " where HHSurveyGUID='"
					+ HHSurveyGUID + "' and HHFamilyMemberGUID='"
					+ HHFamilyMemberGUID + "' and  PWGUID='" + PWGUID + "'";
		}
		try {
			dbIntraHealth.execSQL(sql);
		} catch (Exception e) {
			System.out.println("error in table date" + e);
		}
	}

	public ArrayList<Tbl_HHFamilyMember> showSpinnerDataFamiily(int flag,
			String iguid, String sHouseHoldSurvey) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";

		// ///flag 1 for male and marieed
		if (flag == 1) {
			sql = "Select HHFamilyMemberGUID,FamilyMemberName from Tbl_HHFamilyMember where GenderID=2 and MaritialStatusID=1 and HHSurveyGUID='"
					+ sHouseHoldSurvey + "'";
		} else if (flag == 2) {

			sql = "Select HHFamilyMemberGUID,FamilyMemberName from Tbl_HHFamilyMember where GenderID=1 and MaritialStatusID=1 and HHSurveyGUID='"
					+ sHouseHoldSurvey + "'";

		}

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setHHFamilyMemberGUID(cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")));
					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<Tbl_HHFamilyMember> showSpinnerfatherDataFamiily(
			String sHouseHoldSurvey) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";

		// ///flag 1 for male and marieed

		sql = "Select HHFamilyMemberGUID,FamilyMemberName from Tbl_HHFamilyMember where  HHSurveyGUID='"
				+ sHouseHoldSurvey
				+ "' and GenderID=1 and (MaritialStatusID=1 or MaritialStatusID=3)";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setHHFamilyMemberGUID(cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")));
					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<Tbl_HHFamilyMember> showSpinnerMotherDataFamiily(
			String sHouseHoldSurvey) {
		ArrayList<Tbl_HHFamilyMember> data = null;
		String sql = "";

		// ///flag 1 for male and marieed

		sql = "Select HHFamilyMemberGUID,FamilyMemberName from Tbl_HHFamilyMember where  HHSurveyGUID='"
				+ sHouseHoldSurvey
				+ "' and GenderID=2 and (MaritialStatusID=1 or MaritialStatusID=3)";

		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<Tbl_HHFamilyMember>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Tbl_HHFamilyMember form = new Tbl_HHFamilyMember();
					form.setHHFamilyMemberGUID(cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")));
					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	// //save spouse//

	public int savespousedata(String sHHFamilyMemberGUID, String sSpouseid) {
		String sql = "";
		int ireturnvalue = 0;
		sql = "Update Tbl_HHFamilyMember set Spouse='" + sSpouseid
				+ "',IsEdited=1 where HHFamilyMemberGUID='"
				+ sHHFamilyMemberGUID + "'";
		String sql1 = "Update Tbl_HHFamilyMember set Spouse='"
				+ sHHFamilyMemberGUID
				+ "',IsEdited=1 where HHFamilyMemberGUID='" + sSpouseid + "'";

		try {
			dbIntraHealth.execSQL(sql);
			dbIntraHealth.execSQL(sql1);
			ireturnvalue = 1;
		} catch (Exception e) {
			// TODO: handle exception
			ireturnvalue = 0;
		}
		return ireturnvalue;
	}

	public int savefathermotherdata(String sHHFamilyMemberGUID,
			String sfatherguid, String Smotherguid) {
		String sql = "";
		int ireturnvalue = 0;
		sql = "Update Tbl_HHFamilyMember set Father='" + sfatherguid
				+ "',Mother='" + Smotherguid
				+ "',IsEdited=1 where HHFamilyMemberGUID='"
				+ sHHFamilyMemberGUID + "'";

		try {
			dbIntraHealth.execSQL(sql);
			ireturnvalue = 1;
		} catch (Exception e) {
			// TODO: handle exception
			ireturnvalue = 0;
		}
		return ireturnvalue;

	}

	public void savePregWomen(String flag, String PWImage, int HishRisk,
			String AnyOtherPastIllness, String AnyOtherLastPregCom,
			String AnyOtherLTLPregCom, String PWGUID,
			String HHFamilyMemberGUID, String HHGUID, String PWName,
			String LMPDate, String EDDDate, String PWRegistrationDate,
			int Regwithin12weeks, int RegweeksElaspsed, String HusbandName,
			String Husband_GUID, String MobileNo, String MotherMCTSID,
			int JSYBenificiaryYN, String JSYRegDate, int JSYPaymentReceivedYN,
			String PWDOB, int PWAgeYears, String PWAgeRefDate, double PWWeight,
			int PWBloodGroup, String PastIllnessYN, int TotalPregnancy,
			int LastPregnancyResult, int LastPregnancyComplication,
			int LTLPregnancyResult, int LTLPregnancyomplication,
			int LastPregDeliveryPlace, int LasttolastPregDeliveryPlace,
			String IFSCCode, String Accountno, double PWHeight,
			String CreatedOn, int CreatedBy, int BankAcc, int FacitylastPreg,
			String FaciltyOtherLTL, int FacityLTL,
			String FacilityOtherLastpreg, int ANMID, int AshaID, int Education,
			String AltMobileNo) {
		String sql = "";
		if (flag == "i") {
			sql = "insert into tblPregnant_woman(PWGUID,HHFamilyMemberGUID,HHGUID,PWName,BankAcc, LMPDate, EDDDate, PWRegistrationDate, Regwithin12weeks, HusbandName, Husband_GUID, MobileNo, MotherMCTSID, JSYBenificiaryYN, JSYRegDate, JSYPaymentReceivedYN, PWAgeYears,PWWeight, PWBloodGroup, PastIllnessYN, TotalPregnancy, LastPregnancyResult, LastPregnancyComplication, LTLPregnancyResult, LTLPregnancyomplication, LastPregDeliveryPlace, LasttolastPregDeliveryPlace, IFSCCode, Accountno,CreatedOn,CreatedBy,PWImage,AnyOtherPastIllness, AnyOtherLastPregCom, AnyOtherLTLPregCom,IsEdited,HighRisk,IsPregnant,FacitylastPreg,FaciltyOtherLTL, FacityLTL, FacilityOtherLastpreg,ANMID,AshaID,Education,AltMobileNo)values('"
					+ PWGUID
					+ "','"
					+ HHFamilyMemberGUID
					+ "','"
					+ HHGUID
					+ "','"
					+ PWName
					+ "',"
					+ BankAcc
					+ ",'"
					+ LMPDate
					+ "','"
					+ EDDDate
					+ "','"
					+ PWRegistrationDate
					+ "', "
					+ Regwithin12weeks
					+ ",'"
					+ HusbandName
					+ "','"
					+ Husband_GUID
					+ "','"
					+ MobileNo
					+ "','"
					+ MotherMCTSID
					+ "', "
					+ JSYBenificiaryYN
					+ ",'"
					+ JSYRegDate
					+ "', "
					+ JSYPaymentReceivedYN
					+ ",'"
					+ PWDOB
					+ "', "
					+ PWWeight
					+ ", "
					+ PWBloodGroup
					+ ",'"
					+ PastIllnessYN
					+ "', "
					+ TotalPregnancy
					+ ","
					+ LastPregnancyResult
					+ ", "
					+ LastPregnancyComplication
					+ ","
					+ LTLPregnancyResult
					+ ","
					+ LTLPregnancyomplication
					+ ", "
					+ LastPregDeliveryPlace
					+ ","
					+ LasttolastPregDeliveryPlace
					+ ", '"
					+ IFSCCode
					+ "', '"
					+ Accountno
					+ "','"
					+ CreatedOn
					+ "',"
					+ CreatedBy
					+ ",'"
					+ PWImage
					+ "','"
					+ AnyOtherPastIllness
					+ "','"
					+ AnyOtherLastPregCom
					+ "','"
					+ AnyOtherLTLPregCom
					+ "',1,"
					+ HishRisk
					+ ",1,"
					+ FacitylastPreg
					+ ",'"
					+ FaciltyOtherLTL
					+ "',"
					+ FacityLTL
					+ ", '"
					+ FacilityOtherLastpreg
					+ "',"
					+ ANMID
					+ ","
					+ AshaID
					+ ","
					+ Education
					+ ",'"
					+ AltMobileNo
					+ "')";
			for (int i = 1; i <= 4; i++) {
				String VisitGUID = Validate.random();

				String sql2 = "insert into tblANCVisit(PWGUID,VisitGUID,MCTSID,Visit_No,CreatedOn,CreatedBy,IsEdited,ByANMID,ByAshaID)values('"
						+ PWGUID
						+ "','"
						+ VisitGUID
						+ "','"
						+ MotherMCTSID
						+ "',"
						+ i
						+ ",'"
						+ CreatedOn
						+ "',"
						+ CreatedBy
						+ ",1," + ANMID + "," + AshaID + ")";
				executeSql(sql2);
				System.out.println(sql2);
			}
		} else {
			sql = "update tblPregnant_woman set PWName='" + PWName
					+ "',LMPDate='" + LMPDate + "',EDDDate='" + EDDDate
					+ "', Regwithin12weeks=" + Regwithin12weeks
					+ ",HusbandName='" + HusbandName + "',Husband_GUID='"
					+ Husband_GUID + "',MobileNo='" + MobileNo
					+ "',MotherMCTSID='" + MotherMCTSID
					+ "',JSYBenificiaryYN= " + JSYBenificiaryYN
					+ ",JSYRegDate='" + JSYRegDate + "',IsEdited=1,Education="
					+ Education + ", JSYPaymentReceivedYN="
					+ JSYPaymentReceivedYN + ",PWAgeYears='" + PWDOB
					+ "', PWWeight=" + PWWeight + ",PWBloodGroup= "
					+ PWBloodGroup + ",PastIllnessYN='" + PastIllnessYN
					+ "',TotalPregnancy= " + TotalPregnancy
					+ ",LastPregnancyResult=" + LastPregnancyResult
					+ ", LastPregnancyComplication="
					+ LastPregnancyComplication + ",LTLPregnancyResult="
					+ LTLPregnancyResult + ",LTLPregnancyomplication="
					+ LTLPregnancyomplication + ",LastPregDeliveryPlace= "
					+ LastPregDeliveryPlace + ",LasttolastPregDeliveryPlace="
					+ LasttolastPregDeliveryPlace + ", IFSCCode='" + IFSCCode
					+ "', Accountno='" + Accountno + "',UpdatedOn='"
					+ CreatedOn + "',BankAcc=" + BankAcc + ",UpdatedBy="
					+ CreatedBy + ",PWImage='" + PWImage
					+ "',AnyOtherPastIllness='" + AnyOtherPastIllness
					+ "',AnyOtherLastPregCom='" + AnyOtherLastPregCom
					+ "',AnyOtherLTLPregCom='" + AnyOtherLTLPregCom
					+ "' ,HighRisk=" + HishRisk + ",ANMID=" + ANMID
					+ ",AshaID=" + AshaID + ",FacitylastPreg=" + FacitylastPreg
					+ ",FaciltyOtherLTL='" + FaciltyOtherLTL + "',FacityLTL="
					+ FacityLTL + ", FacilityOtherLastpreg='"
					+ FacilityOtherLastpreg + "' where PWGUID='" + PWGUID + "'";
		}
		String sql1 = "update Tbl_HHFamilyMember set Education=" + Education
				+ " where HHFamilyMemberGUID='" + HHFamilyMemberGUID + "'";
		try {
			executeSql(sql);
			executeSql(sql1);
			System.out.println(sql);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	public ArrayList<tbl_pregnantwomen> getPregnantWomendata(String PWGUID,
			int flag, int ashaid) {
		ArrayList<tbl_pregnantwomen> data = null;
		String sql = "";

		// ///flag 1 for male and marieed
		if (flag == 0) {
			sql = "Select * from tblPregnant_woman inner join Tbl_HHSurvey  on tblPregnant_woman.hhguid = Tbl_HHSurvey.HHSurveyGUID where IsPregnant=1  and serviceproviderid="
					+ ashaid + "  ";
		} else if (flag == 1) {
			sql = "Select * from tblPregnant_woman join Tbl_HHFamilyMember on tblPregnant_woman.HHFamilyMemberGUID=Tbl_HHFamilyMember.HHFamilyMemberGUID inner join Tbl_HHSurvey  on Tbl_HHFamilyMember.HHSurveyGUID = Tbl_HHSurvey.HHSurveyGUID where Tbl_HHFamilyMember.StatusID!=2  and serviceproviderid="
					+ ashaid + " and  PWGUID='" + PWGUID + "'";
		} else if (flag == 4) {
			sql = "Select * from tblPregnant_woman inner join Tbl_HHSurvey  on tblPregnant_woman.hhguid = Tbl_HHSurvey.HHSurveyGUID where IsPregnant=1  and serviceproviderid="
					+ ashaid + " and  PWName like '%" + PWGUID + "%'";
		} else if (flag == 2) {
			sql = "Select * from tblPregnant_woman where IsEdited = 1";
		} else if (flag == 5) {
			sql = "Select * from tblPregnant_woman inner join Tbl_HHSurvey  on tblPregnant_woman.hhguid = Tbl_HHSurvey.HHSurveyGUID where IsPregnant=1  and serviceproviderid="
					+ ashaid + "  ";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tbl_pregnantwomen>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_pregnantwomen form = new tbl_pregnantwomen();

					if (cursor.getString(cursor.getColumnIndex("PWGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWGUID"))
									.equalsIgnoreCase("null")) {
						form.setPWGUID("");
					} else {
						form.setPWGUID(cursor.getString(cursor
								.getColumnIndex("PWGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("HHGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("HHGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHGUID("");
					} else {
						form.setHHGUID(cursor.getString(cursor
								.getColumnIndex("HHGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("AltMobileNo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AltMobileNo"))
									.equalsIgnoreCase("null")) {
						form.setAltMobileNo("");
					} else {
						form.setAltMobileNo(cursor.getString(cursor
								.getColumnIndex("AltMobileNo")));
					}
					if (cursor.getString(cursor.getColumnIndex("PWName")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWName"))
									.equalsIgnoreCase("null")) {
						form.setPWName("");
					} else {
						form.setPWName(cursor.getString(cursor
								.getColumnIndex("PWName")));
					}

					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));
					form.setEducation(cursor.getInt(cursor
							.getColumnIndex("Education")));

					if (cursor.getString(cursor.getColumnIndex("LMPDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("LMPDate"))
									.equalsIgnoreCase("null")) {
						form.setLMPDate("");
					} else {
						form.setLMPDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("LMPDate"))));
					}
					if (cursor.getString(cursor.getColumnIndex("EDDDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("EDDDate"))
									.equalsIgnoreCase("null")) {
						form.setEDDDate("");
					} else {
						form.setEDDDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("EDDDate"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("PWRegistrationDate")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("PWRegistrationDate"))
									.equalsIgnoreCase("null")) {
						form.setPWRegistrationDate("");
					} else {
						form.setPWRegistrationDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("PWRegistrationDate"))));
					}

					form.setRegwithin12weeks(cursor.getInt(cursor
							.getColumnIndex("Regwithin12weeks")));
					form.setRegweeksElaspsed(cursor.getInt(cursor
							.getColumnIndex("RegweeksElaspsed")));
					form.setIsPregnant(cursor.getInt(cursor
							.getColumnIndex("IsPregnant")));

					if (cursor.getString(cursor.getColumnIndex("HusbandName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HusbandName"))
									.equalsIgnoreCase("null")) {
						form.setHusbandName("");
					} else {
						form.setHusbandName(cursor.getString(cursor
								.getColumnIndex("HusbandName")));
					}
					if (cursor.getString(cursor.getColumnIndex("Husband_GUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Husband_GUID"))
									.equalsIgnoreCase("null")) {
						form.setHusband_GUID("");
					} else {
						form.setHusband_GUID(cursor.getString(cursor
								.getColumnIndex("Husband_GUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("MobileNo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MobileNo"))
									.equalsIgnoreCase("null")) {
						form.setMobileNo("");
					} else {
						form.setMobileNo(cursor.getString(cursor
								.getColumnIndex("MobileNo")));
					}
					if (cursor.getString(cursor.getColumnIndex("MotherMCTSID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MotherMCTSID"))
									.equalsIgnoreCase("null")) {
						form.setMotherMCTSID("");
					} else {
						form.setMotherMCTSID(cursor.getString(cursor
								.getColumnIndex("MotherMCTSID")));
					}
					if (cursor.getString(cursor.getColumnIndex("IFSCCode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IFSCCode"))
									.equalsIgnoreCase("null")) {
						form.setIFSCCode("");
					} else {
						form.setIFSCCode(cursor.getString(cursor
								.getColumnIndex("IFSCCode")));
					}
					if (cursor.getString(cursor.getColumnIndex("Accountno")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Accountno"))
									.equalsIgnoreCase("null")) {
						form.setAccountno("");
					} else {
						form.setAccountno(cursor.getString(cursor
								.getColumnIndex("Accountno")));
					}

					form.setJSYBenificiaryYN(cursor.getInt(cursor
							.getColumnIndex("JSYBenificiaryYN")));

					if (cursor.getString(cursor.getColumnIndex("JSYRegDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("JSYRegDate"))
									.equalsIgnoreCase("null")) {
						form.setJSYRegDate("");
					} else {
						form.setJSYRegDate(cursor.getString(cursor
								.getColumnIndex("JSYRegDate")));
					}

					form.setJSYPaymentReceivedYN(cursor.getInt(cursor
							.getColumnIndex("JSYPaymentReceivedYN")));

					if (cursor.getString(cursor.getColumnIndex("PWDOB")) == null
							|| cursor.getString(cursor.getColumnIndex("PWDOB"))
									.equalsIgnoreCase("null")) {
						form.setPWDOB("");
					} else {
						form.setPWDOB(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("PWDOB"))));
					}

					form.setPWAgeYears(cursor.getInt(cursor
							.getColumnIndex("PWAgeYears")));

					if (cursor.getString(cursor.getColumnIndex("PWAgeRefDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PWAgeRefDate"))
									.equalsIgnoreCase("null")) {
						form.setPWAgeRefDate("");
					} else {
						form.setPWAgeRefDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("PWAgeRefDate"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("PastIllnessYN")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PastIllnessYN"))
									.equalsIgnoreCase("null")) {
						form.setPastIllnessYN("");
					} else {
						form.setPastIllnessYN(cursor.getString(cursor
								.getColumnIndex("PastIllnessYN")));
					}

					form.setPWWeight(cursor.getDouble(cursor
							.getColumnIndex("PWWeight")));
					form.setMaternalDeath(cursor.getInt(cursor
							.getColumnIndex("MaternalDeath")));
					form.setPWHeight(cursor.getDouble(cursor
							.getColumnIndex("PWHeight")));
					form.setPWBloodGroup(cursor.getInt(cursor
							.getColumnIndex("PWBloodGroup")));

					form.setTotalPregnancy(cursor.getInt(cursor
							.getColumnIndex("TotalPregnancy")));
					form.setLastPregnancyResult(cursor.getInt(cursor
							.getColumnIndex("LastPregnancyResult")));
					form.setLastPregnancyComplication(cursor.getInt(cursor
							.getColumnIndex("LastPregnancyComplication")));
					form.setLTLPregnancyResult(cursor.getInt(cursor
							.getColumnIndex("LTLPregnancyResult")));
					form.setLTLPregnancyomplication(cursor.getInt(cursor
							.getColumnIndex("LTLPregnancyomplication")));
					form.setLastPregDeliveryPlace(cursor.getInt(cursor
							.getColumnIndex("LastPregDeliveryPlace")));
					form.setLasttolastPregDeliveryPlace(cursor.getInt(cursor
							.getColumnIndex("LasttolastPregDeliveryPlace")));

					if (cursor.getString(cursor
							.getColumnIndex("ExpFacilityforDelivery")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("ExpFacilityforDelivery"))
									.equalsIgnoreCase("null")) {
						form.setExpFacilityforDelivery("");
					} else {
						form.setExpFacilityforDelivery(cursor.getString(cursor
								.getColumnIndex("ExpFacilityforDelivery")));
					}

					if (cursor
							.getString(cursor.getColumnIndex("MotherDCOther")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MotherDCOther"))
									.equalsIgnoreCase("null")) {
						form.setMotherDCOther("");
					} else {
						form.setMotherDCOther(cursor.getString(cursor
								.getColumnIndex("MotherDCOther")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("DeathPlaceOther")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DeathPlaceOther"))
									.equalsIgnoreCase("null")) {
						form.setDeathPlaceOther("");
					} else {
						form.setDeathPlaceOther(cursor.getString(cursor
								.getColumnIndex("DeathPlaceOther")));
					}

					if (cursor.getString(cursor
							.getColumnIndex("AnyOtherPastIllness")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("AnyOtherPastIllness"))
									.equalsIgnoreCase("null")) {
						form.setAnyOtherPastIllness("");
					} else {
						form.setAnyOtherPastIllness(cursor.getString(cursor
								.getColumnIndex("AnyOtherPastIllness")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AnyOtherLastPregCom")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("AnyOtherLastPregCom"))
									.equalsIgnoreCase("null")) {
						form.setAnyOtherLastPregCom("");
					} else {
						form.setAnyOtherLastPregCom(cursor.getString(cursor
								.getColumnIndex("AnyOtherLastPregCom")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AnyOtherLTLPregCom")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("AnyOtherLTLPregCom"))
									.equalsIgnoreCase("null")) {
						form.setAnyOtherLTLPregCom("");
					} else {
						form.setAnyOtherLTLPregCom(cursor.getString(cursor
								.getColumnIndex("AnyOtherLTLPregCom")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("ExpFacilityforDeliveryName")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("ExpFacilityforDeliveryName"))
									.equalsIgnoreCase("null")) {
						form.setExpFacilityforDeliveryName("");
					} else {
						form.setExpFacilityforDeliveryName(cursor.getString(cursor
								.getColumnIndex("ExpFacilityforDeliveryName")));
					}

					form.setVDRLTestYN(cursor.getInt(cursor
							.getColumnIndex("VDRLTestYN")));

					if (cursor.getString(cursor.getColumnIndex("VDRLResult")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VDRLResult"))
									.equalsIgnoreCase("null")) {
						form.setVDRLResult("");
					} else {
						form.setVDRLResult(cursor.getString(cursor
								.getColumnIndex("VDRLResult")));
					}

					form.setHIVTestYN(cursor.getInt(cursor
							.getColumnIndex("HIVTestYN")));

					if (cursor.getString(cursor.getColumnIndex("HIVResult")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HIVResult"))
									.equalsIgnoreCase("null")) {
						form.setHIVResult("");
					} else {
						form.setHIVResult(cursor.getString(cursor
								.getColumnIndex("HIVResult")));
					}
					if (cursor.getString(cursor.getColumnIndex("Visit1Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Visit1Date"))
									.equalsIgnoreCase("null")) {
						form.setVisit1Date("");
					} else {
						form.setVisit1Date(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("Visit1Date"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Visit2Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Visit2Date"))
									.equalsIgnoreCase("null")) {
						form.setVisit2Date("");
					} else {
						form.setVisit2Date(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("Visit2Date"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Visit3Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Visit3Date"))
									.equalsIgnoreCase("null")) {
						form.setVisit3Date("");
					} else {
						form.setVisit3Date(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("Visit3Date"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Visit4Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Visit4Date"))
									.equalsIgnoreCase("null")) {
						form.setVisit4Date("");
					} else {
						form.setVisit4Date(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("Visit4Date"))));
					}

					form.setISAbortion(cursor.getInt(cursor
							.getColumnIndex("ISAbortion")));
					form.setAbortionFacilityType(cursor.getInt(cursor
							.getColumnIndex("AbortionFacilityType")));
					form.setNoofANCVisitsDone(cursor.getInt(cursor
							.getColumnIndex("NoofANCVisitsDone")));

					if (cursor.getString(cursor
							.getColumnIndex("LastANCVisitDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("LastANCVisitDate"))
									.equalsIgnoreCase("null")) {
						form.setLastANCVisitDate("");
					} else {
						form.setLastANCVisitDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("LastANCVisitDate"))));
					}
					if (cursor.getString(cursor.getColumnIndex("TT1Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT1Date"))
									.equalsIgnoreCase("null")) {
						form.setTT1Date("");
					} else {
						form.setTT1Date(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("TT1Date"))));
					}
					if (cursor.getString(cursor.getColumnIndex("TT2Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT2Date"))
									.equalsIgnoreCase("null")) {
						form.setTT2Date("");
					} else {
						form.setTT2Date(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("TT2Date"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("TTBoosterDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TTBoosterDate"))
									.equalsIgnoreCase("null")) {
						form.setTTBoosterDate("");
					} else {
						form.setTTBoosterDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTBoosterDate"))));
					}
					if (cursor.getString(cursor.getColumnIndex("DangerSigns")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DangerSigns"))
									.equalsIgnoreCase("null")) {
						form.setDangerSigns("");
					} else {
						form.setDangerSigns(cursor.getString(cursor
								.getColumnIndex("DangerSigns")));
					}

					form.setRefferedYN(cursor.getInt(cursor
							.getColumnIndex("RefferedYN")));
					form.setIsPregnant(cursor.getInt(cursor
							.getColumnIndex("IsPregnant")));

					if (cursor.getString(cursor
							.getColumnIndex("DeliveryDateTime")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DeliveryDateTime"))
									.equalsIgnoreCase("null")) {
						form.setDeliveryDateTime("");
					} else {
						form.setDeliveryDateTime(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("DeliveryDateTime"))));
					}

					form.setDeliveryPlace(cursor.getInt(cursor
							.getColumnIndex("DeliveryPlace")));
					form.setUpdatedBy(cursor.getInt(cursor
							.getColumnIndex("UpdatedBy")));

					if (cursor.getString(cursor
							.getColumnIndex("DeliveryConductedBy")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("DeliveryConductedBy"))
									.equalsIgnoreCase("null")) {
						form.setDeliveryConductedBy("");
					} else {
						form.setDeliveryConductedBy(cursor.getString(cursor
								.getColumnIndex("DeliveryConductedBy")));
					}

					form.setDeliveryType(cursor.getInt(cursor
							.getColumnIndex("DeliveryType")));

					if (cursor.getString(cursor
							.getColumnIndex("DeliveryComplication")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("DeliveryComplication"))
									.equalsIgnoreCase("null")) {
						form.setDeliveryComplication("");
					} else {
						form.setDeliveryComplication(cursor.getString(cursor
								.getColumnIndex("DeliveryComplication")));
					}

					form.setMotherDeathCause(cursor.getInt(cursor
							.getColumnIndex("MotherDeathCause")));
					form.setChildDeathCause(cursor.getInt(cursor
							.getColumnIndex("ChildDeathCause")));
					form.setDeliveryOutcome(cursor.getInt(cursor
							.getColumnIndex("DeliveryOutcome")));

					if (cursor.getString(cursor
							.getColumnIndex("DTMFacilityDischarge")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("DTMFacilityDischarge"))
									.equalsIgnoreCase("null")) {
						form.setDTMFacilityDischarge("");
					} else {
						form.setDTMFacilityDischarge(cursor.getString(cursor
								.getColumnIndex("DTMFacilityDischarge")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("PaymentRecieved")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PaymentRecieved"))
									.equalsIgnoreCase("null")) {
						form.setPaymentRecieved("");
					} else {
						form.setPaymentRecieved(cursor.getString(cursor
								.getColumnIndex("PaymentRecieved")));
					}

					form.setPlaceofDeath(cursor.getInt(cursor
							.getColumnIndex("PlaceofDeath")));

					if (cursor.getString(cursor.getColumnIndex("DateofDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateofDeath"))
									.equalsIgnoreCase("null")) {
						form.setDateofDeath("");
					} else {
						form.setDateofDeath(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateofDeath"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("OtherPlaceofDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("OtherPlaceofDeath"))
									.equalsIgnoreCase("null")) {
						form.setOtherPlaceofDeath("");
					} else {
						form.setOtherPlaceofDeath(cursor.getString(cursor
								.getColumnIndex("OtherPlaceofDeath")));
					}
					if (cursor.getString(cursor.getColumnIndex("DateofDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DateofDeath"))
									.equalsIgnoreCase("null")) {
						form.setDateofDeath("");
					} else {
						form.setDateofDeath(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("DateofDeath"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("OtherPlaceofDeath")) == null
							|| cursor.getString(
									cursor.getColumnIndex("OtherPlaceofDeath"))
									.equalsIgnoreCase("null")) {
						form.setOtherPlaceofDeath("");
					} else {
						form.setOtherPlaceofDeath(cursor.getString(cursor
								.getColumnIndex("OtherPlaceofDeath")));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));
					}
					if (cursor.getString(cursor.getColumnIndex("PWImage")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PWImage"))
									.equalsIgnoreCase("null")) {
						form.setPWImage("");
					} else {
						form.setPWImage(cursor.getString(cursor
								.getColumnIndex("PWImage")));
					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UpdatedOn"))));
					}

					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					form.setUpdatedBy(cursor.getInt(cursor
							.getColumnIndex("UpdatedBy")));
					form.setHighRisk(cursor.getInt(cursor
							.getColumnIndex("HighRisk")));
					form.setFacitylastPreg(cursor.getInt(cursor
							.getColumnIndex("FacitylastPreg")));
					form.setFacityLTL(cursor.getInt(cursor
							.getColumnIndex("FacityLTL")));
					form.setBankAcc(cursor.getInt(cursor
							.getColumnIndex("BankAcc")));

					if (cursor.getString(cursor
							.getColumnIndex("FaciltyOtherLTL")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FaciltyOtherLTL"))
									.equalsIgnoreCase("null")) {
						form.setFaciltyOtherLTL("");
					} else {
						form.setFaciltyOtherLTL(cursor.getString(cursor
								.getColumnIndex("FaciltyOtherLTL")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("FacilityOtherLastpreg")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("FacilityOtherLastpreg"))
									.equalsIgnoreCase("null")) {
						form.setFacilityOtherLastpreg("");
					} else {

						form.setFacilityOtherLastpreg(cursor.getString(cursor
								.getColumnIndex("FacilityOtherLastpreg")));
					}
					form.setAbortion_FacilityName(cursor.getInt(cursor
							.getColumnIndex("Abortion_FacilityName")));

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<TblANCVisit> getTbl_VisitANCData(String PWGUID,
			String ANCVisitGUID, int flag) {
		ArrayList<TblANCVisit> data = null;
		String sql = "";
		String Date1 = "";
		String Date2 = "";
		String Date3 = "";
		if (flag == 0) {
			sql = "Select * from tblANCVisit where PWGUID='" + PWGUID
					+ "' order by Visit_No";
		} else if (flag == 1) {
			sql = "Select * from tblANCVisit where PWGUID='" + PWGUID
					+ "' and VisitGUID='" + ANCVisitGUID + "'";
		} else if (flag == 2) {
			sql = "Select * from tblANCVisit where IsEdited = 1";
		} else if (flag == 3) {
			sql = "Select * from tblANCVisit where PWGUID='" + PWGUID
					+ "' order by visit_no";
		} else if (flag == 4) {
			sql = "Select * from tblANCVisit where Visit_No='" + ANCVisitGUID
					+ "' and PWGUID='" + PWGUID + "' ";
		} else if (flag == 6) {
			sql = "select *,tblpregnant_woman.PWName as PWName from tblANCVisit inner join  tblpregnant_woman On  tblANCVisit.PWGUID=tblpregnant_woman.PWGUID  Limit 1 ";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<TblANCVisit>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					TblANCVisit form = new TblANCVisit();

					if (cursor.getString(cursor.getColumnIndex("PWGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWGUID"))
									.equalsIgnoreCase("null")) {
						form.setPWGUID("");
					} else {
						form.setPWGUID(cursor.getString(cursor
								.getColumnIndex("PWGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("VisitGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VisitGUID"))
									.equalsIgnoreCase("null")) {
						form.setVisitGUID("");
					} else {
						form.setVisitGUID(cursor.getString(cursor
								.getColumnIndex("VisitGUID")));
					}
					form.setByANMID(cursor.getInt(cursor
							.getColumnIndex("ByANMID")));
					form.setByAshaID(cursor.getInt(cursor
							.getColumnIndex("ByAshaID")));

					if (cursor.getString(cursor.getColumnIndex("MCTSID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("MCTSID"))
									.equalsIgnoreCase("null")) {
						form.setMCTSID("");
					} else {
						form.setMCTSID(cursor.getString(cursor
								.getColumnIndex("MCTSID")));
					}
					form.setVisit_No(cursor.getInt(cursor
							.getColumnIndex("Visit_No")));
					form.setCalciumReceived(cursor.getInt(cursor
							.getColumnIndex("CalciumReceived")));
					form.setCalciumTablet(cursor.getInt(cursor
							.getColumnIndex("CalciumTablet")));
					form.setTrimester(cursor.getInt(cursor
							.getColumnIndex("Trimester")));
					form.setDangerSign(cursor.getInt(cursor
							.getColumnIndex("DangerSign")));
					if (cursor.getString(cursor.getColumnIndex("VisitDueDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VisitDueDate"))
									.equalsIgnoreCase("null")) {
						form.setVisitDueDate("");
					} else {
						form.setVisitDueDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("VisitDueDate"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("CheckupVisitDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CheckupVisitDate"))
									.equalsIgnoreCase("null")) {
						form.setCheckupVisitDate("");
					} else {
						form.setCheckupVisitDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("CheckupVisitDate"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("HomeVisitDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HomeVisitDate"))
									.equalsIgnoreCase("null")) {
						form.setHomeVisitDate("");
					} else {
						form.setHomeVisitDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("HomeVisitDate"))));
					}
					form.setCheckupPlace(cursor.getInt(cursor
							.getColumnIndex("CheckupPlace")));
					form.setBirthWeight(cursor.getDouble(cursor
							.getColumnIndex("BirthWeight")));
					form.setBP(cursor.getInt(cursor.getColumnIndex("BP")));

					if (cursor.getString(cursor.getColumnIndex("BPResult")) == null
							|| cursor.getString(
									cursor.getColumnIndex("BPResult"))
									.equalsIgnoreCase("null")) {
						form.setBPResult("");
					} else {
						form.setBPResult(cursor.getString(cursor
								.getColumnIndex("BPResult")));
					}
					form.setTT1TT2last2yr(cursor.getInt(cursor
							.getColumnIndex("TT1TT2last2yr")));

					form.setHemoglobin(cursor.getDouble(cursor
							.getColumnIndex("Hemoglobin")));
					form.setUrineTest(cursor.getInt(cursor
							.getColumnIndex("UrineTest")));
					form.setUrineSugar(cursor.getInt(cursor
							.getColumnIndex("UrineSugar")));
					form.setUrineAlbumin(cursor.getInt(cursor
							.getColumnIndex("UrineAlbumin")));
					if (cursor.getString(cursor
							.getColumnIndex("TTfirstDoseDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TTfirstDoseDate"))
									.equalsIgnoreCase("null")) {
						form.setTTfirstDoseDate("");
					} else {
						form.setTTfirstDoseDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTfirstDoseDate"))));
						Date1 = Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTfirstDoseDate")));

					}

					if (cursor.getString(cursor
							.getColumnIndex("TTsecondDoseDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TTsecondDoseDate"))
									.equalsIgnoreCase("null")) {
						form.setTTsecondDoseDate("");
					} else {
						form.setTTsecondDoseDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTsecondDoseDate"))));
						Date2 = Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTsecondDoseDate")));

					}
					if (cursor
							.getString(cursor.getColumnIndex("TTboosterDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TTboosterDate"))
									.equalsIgnoreCase("null")) {
						form.setTTBoosterDate("");
					} else {
						form.setTTBoosterDate(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTboosterDate"))));
						Date3 = Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("TTboosterDate")));
					}

					form.setVDRLTest(cursor.getInt(cursor
							.getColumnIndex("VDRLTest")));
					form.setHIVTest(cursor.getInt(cursor
							.getColumnIndex("HIVTest")));
					form.setUltraSound(cursor.getInt(cursor
							.getColumnIndex("UltraSound")));
					form.setUltraSoundConductedby(cursor.getInt(cursor
							.getColumnIndex("UltraSoundConductedby")));
					form.setIFARecieved(cursor.getInt(cursor
							.getColumnIndex("IFARecieved")));
					form.setNumberIFARecieved(cursor.getInt(cursor
							.getColumnIndex("NumberIFARecieved")));

					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn"))));
					}

					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));

					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UpdatedOn"))));
					}

					form.setUpdatedBy(cursor.getInt(cursor
							.getColumnIndex("UpdatedBy")));
					form.setUltrasoundResult(cursor.getInt(cursor
							.getColumnIndex("UltrasoundResult")));
					form.setPregWomenReg(cursor.getInt(cursor
							.getColumnIndex("PregWomenReg")));
					form.setMcpCard(cursor.getInt(cursor
							.getColumnIndex("McpCard")));
					form.setTT1(cursor.getInt(cursor.getColumnIndex("TT1")));
					form.setTT2(cursor.getInt(cursor.getColumnIndex("TT2")));
					form.setTTbooster(cursor.getInt(cursor
							.getColumnIndex("TTbooster")));
					form.setHB1(cursor.getInt(cursor.getColumnIndex("HB1")));

					form.setUrineTestsugar1(cursor.getInt(cursor
							.getColumnIndex("UrineTestsugar1")));

					form.setUrineTestAl1(cursor.getInt(cursor
							.getColumnIndex("UrineTestAl1")));

					form.setIronTablet1(cursor.getInt(cursor
							.getColumnIndex("IronTablet1")));

					form.setWeight1YN(cursor.getInt(cursor
							.getColumnIndex("Weight1YN")));

					form.setBP1YN(cursor.getInt(cursor.getColumnIndex("BP1YN")));

					form.setHB1YN(cursor.getInt(cursor.getColumnIndex("HB1YN")));

					form.setUrineTestsugar1YN(cursor.getInt(cursor
							.getColumnIndex("UrineTestsugar1YN")));

					form.setUrineTestAl1YN(cursor.getInt(cursor
							.getColumnIndex("UrineTestAl1YN")));

					form.setIronTablet1YN(cursor.getInt(cursor
							.getColumnIndex("IronTablet1YN")));

					form.setAncCheckup1YN(cursor.getInt(cursor
							.getColumnIndex("AncCheckup1YN")));

					form.setDeliveryONhospYN(cursor.getInt(cursor
							.getColumnIndex("DeliveryONhospYN")));
					form.setFamilyPlanning(cursor.getInt(cursor
							.getColumnIndex("FamilyPlanning")));
					if (cursor.getString(cursor.getColumnIndex("TT1date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT1date"))
									.equalsIgnoreCase("null")) {
						form.setTT1date("");
					} else {
						form.setTT1date(Validate.changeDateFormatpadding(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("TT1date")))));
					}
					if (cursor.getString(cursor.getColumnIndex("TT2date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT2date"))
									.equalsIgnoreCase("null")) {
						form.setTT2date("");
					} else {
						form.setTT2date(Validate.changeDateFormatpadding(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("TT2date")))));
					}

					if (cursor.getString(cursor
							.getColumnIndex("TTboosterDate1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TTboosterDate1"))
									.equalsIgnoreCase("null")) {
						form.setTTboosterDate1("");
					} else {
						form.setTTboosterDate1(Validate.changeDateFormatpadding(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("TTboosterDate1")))));
					}

					if (cursor.getString(cursor.getColumnIndex("AncCheckup1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AncCheckup1"))
									.equalsIgnoreCase("null")) {
						form.setAncCheckup1("");
					} else {
						form.setAncCheckup1(cursor.getString(cursor
								.getColumnIndex("AncCheckup1")));
					}

					form.setWeight1(cursor.getFloat(cursor
							.getColumnIndex("Weight1")));

					form.setBP1(cursor.getFloat(cursor.getColumnIndex("BP1")));
					if (flag == 6) {
						if (Date1.equalsIgnoreCase("")
								&& Date2.equalsIgnoreCase("")
								&& Date3.equalsIgnoreCase("")) {
							if (cursor.getString(cursor
									.getColumnIndex("PWName")) == null
									|| cursor.getString(
											cursor.getColumnIndex("PWName"))
											.equalsIgnoreCase("null")) {
								form.setPWName("");
							} else {
								form.setPWName(cursor.getString(cursor
										.getColumnIndex("PWName")));
							}

							data.add(form);
						}
					} else {
						data.add(form);
					}
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in tblanc :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<tbl_DatesEd> gettbl_DatesEd() {
		ArrayList<tbl_DatesEd> DatesEd = null;
		String sql = "";

		sql = "select * from tbl_DatesEd";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				DatesEd = new ArrayList<tbl_DatesEd>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_DatesEd form = new tbl_DatesEd();

					form.setIsEdited(cursor.getInt(cursor
							.getColumnIndex("IsEdited")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					form.setUpdatedBy(cursor.getInt(cursor
							.getColumnIndex("UpdatedBy")));
					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));

					if (cursor.getString(cursor.getColumnIndex("HHSurveyGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("HHSurveyGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHSurveyGUID("");
					} else {
						form.setHHSurveyGUID(cursor.getString(cursor
								.getColumnIndex("HHSurveyGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("PWGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWGUID"))
									.equalsIgnoreCase("null")) {
						form.setPWGUID("");
					} else {
						form.setPWGUID(cursor.getString(cursor
								.getColumnIndex("PWGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("LmpDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("LmpDate"))
									.equalsIgnoreCase("null")) {
						form.setLmpDate("");
					} else {
						form.setLmpDate(cursor.getString(cursor
								.getColumnIndex("LmpDate")));
					}
					if (cursor.getString(cursor.getColumnIndex("EDD")) == null
							|| cursor.getString(cursor.getColumnIndex("EDD"))
									.equalsIgnoreCase("null")) {
						form.setEDD("");
					} else {
						form.setEDD(cursor.getString(cursor
								.getColumnIndex("EDD")));
					}
					if (cursor.getString(cursor.getColumnIndex("TT1Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT1Date"))
									.equalsIgnoreCase("null")) {
						form.setTT1Date("");
					} else {
						form.setTT1Date(Validate.changeDateFormat(cursor
								.getString(cursor.getColumnIndex("TT1Date"))));
					}
					if (cursor.getString(cursor.getColumnIndex("TT2Date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TT2Date"))
									.equalsIgnoreCase("null")) {
						form.setTT2Date("");
					} else {
						form.setTT2Date(Validate.changeDateFormat(cursor
								.getString(cursor.getColumnIndex("TT2Date"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AncCheckupdate1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AncCheckupdate1"))
									.equalsIgnoreCase("null")) {
						form.setAncCheckupdate1("");
					} else {
						form.setAncCheckupdate1(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("AncCheckupdate1"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AncCheckupdate2")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AncCheckupdate2"))
									.equalsIgnoreCase("null")) {
						form.setAncCheckupdate2("");
					} else {
						form.setAncCheckupdate2(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("AncCheckupdate2"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AncCheckupdate3")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AncCheckupdate3"))
									.equalsIgnoreCase("null")) {
						form.setAncCheckupdate3("");
					} else {
						form.setAncCheckupdate3(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("AncCheckupdate3"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("AncCheckupdate4")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AncCheckupdate4"))
									.equalsIgnoreCase("null")) {
						form.setAncCheckupdate4("");
					} else {
						form.setAncCheckupdate4(Validate
								.changeDateFormat(cursor.getString(cursor
										.getColumnIndex("AncCheckupdate4"))));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));
					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(cursor.getString(cursor
								.getColumnIndex("UpdatedOn")));
					}

					DatesEd.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return DatesEd;
		} catch (Exception e) {
			Log.e("DataProvider",
					"Error in gettbl_DatesEd :: " + e.getMessage());
		}
		return DatesEd;

	}

	public void UpdatetblANCVisit(ContentValues data, String PWGUID,
			String ANCVisitGUID) {
		if (dbIntraHealth == null) {
			dbIntraHealth = dbHelper.getDatabase();
		}
		int aaa = dbIntraHealth.update("tblANCVisit", data,
				"PWGUID=? and VisitGUID=?",
				new String[] { PWGUID, ANCVisitGUID });
		if (aaa > 0) {
			Log.e("DataProvider", "Error in gettbl_DatesEd :: ");
		}

	}

	public ArrayList<tblChild> gettblChild(String ChildGUID, int Flag) {
		ArrayList<tblChild> Child = null;
		String sql = "";
		if (Flag == 1) {
			sql = "Select * from tblChild where ChildGUID = '" + ChildGUID
					+ "' ";
		} else if (Flag == 2) {
			sql = "Select * from tblChild ORDER BY date(child_dob) DESC ";
		} else if (Flag == 3) {
			sql = "Select * from tblChild where IsEdited = 1";
		} else if (Flag == 4) {
			sql = "Select * from tblChild where pw_GUID='" + ChildGUID + "'";
		} else if (Flag == 5) {
			sql = "Select * from tblChild  ORDER BY date(child_dob) DESC ";
		}

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<tblChild>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblChild form = new tblChild();

					form.setChild_ID(cursor.getInt(cursor
							.getColumnIndex("Child_ID")));
					form.setWt_of_child(cursor.getFloat(cursor
							.getColumnIndex("Wt_of_child")));
					form.setMother_status(cursor.getInt(cursor
							.getColumnIndex("mother_status")));
					form.setChild_status(cursor.getInt(cursor
							.getColumnIndex("child_status")));
					form.setBreastfeeding_within1H(cursor.getInt(cursor
							.getColumnIndex("breastfeeding_within1H")));
					form.setCreated_by(cursor.getInt(cursor
							.getColumnIndex("created_by")));
					form.setModified_by(cursor.getInt(cursor
							.getColumnIndex("modified_by")));
					form.setGender(cursor.getInt(cursor
							.getColumnIndex("Gender")));
					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					form.setPlace_of_birth(cursor.getInt(cursor
							.getColumnIndex("place_of_birth")));
					form.setFacilityType(cursor.getInt(cursor
							.getColumnIndex("FacilityType")));

					if (cursor.getString(cursor.getColumnIndex("pw_GUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("pw_GUID"))
									.equalsIgnoreCase("null")) {
						form.setPw_GUID("");
					} else {
						form.setPw_GUID(cursor.getString(cursor
								.getColumnIndex("pw_GUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Facility")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Facility"))
									.equalsIgnoreCase("null")) {
						form.setFacility("");
					} else {
						form.setFacility(cursor.getString(cursor
								.getColumnIndex("Facility")));
					}
					if (cursor.getString(cursor.getColumnIndex("HHGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("HHGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHGUID("");
					} else {
						form.setHHGUID(cursor.getString(cursor
								.getColumnIndex("HHGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("HHFamilyMemberGUID")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("HHFamilyMemberGUID"))
									.equalsIgnoreCase("null")) {
						form.setHHFamilyMemberGUID("");
					} else {
						form.setHHFamilyMemberGUID(cursor.getString(cursor
								.getColumnIndex("HHFamilyMemberGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("MotherGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MotherGUID"))
									.equalsIgnoreCase("null")) {
						form.setMotherGUID("");
					} else {
						form.setMotherGUID(cursor.getString(cursor
								.getColumnIndex("MotherGUID")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("Date_Of_Registration")) == null
							|| cursor
									.getString(
											cursor.getColumnIndex("Date_Of_Registration"))
									.equalsIgnoreCase("null")) {
						form.setDate_Of_Registration("");
					} else {
						form.setDate_Of_Registration(Validate.changeDateFormatpadding(cursor.getString(cursor
								.getColumnIndex("Date_Of_Registration"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Child_dob")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Child_dob"))
									.equalsIgnoreCase("null")) {
						form.setChild_dob("");
					} else {
						form.setChild_dob(Validate
								.changeDateFormatpadding((cursor
										.getString(cursor
												.getColumnIndex("Child_dob")))));
					}
					if (cursor.getString(cursor.getColumnIndex("Birth_time")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Birth_time"))
									.equalsIgnoreCase("null")) {
						form.setBirth_time("");
					} else {
						form.setBirth_time(cursor.getString(cursor
								.getColumnIndex("Birth_time")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("preTerm_fullTerm")) == null
							|| cursor.getString(
									cursor.getColumnIndex("preTerm_fullTerm"))
									.equalsIgnoreCase("null")) {
						form.setPreTerm_fullTerm("");
					} else {
						form.setPreTerm_fullTerm(cursor.getString(cursor
								.getColumnIndex("preTerm_fullTerm")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("mother_death_dt")) == null
							|| cursor.getString(
									cursor.getColumnIndex("mother_death_dt"))
									.equalsIgnoreCase("null")) {
						form.setMother_death_dt("");
					} else {
						form.setMother_death_dt(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("mother_death_dt"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("child_death_dt")) == null
							|| cursor.getString(
									cursor.getColumnIndex("child_death_dt"))
									.equalsIgnoreCase("null")) {
						form.setChild_death_dt("");
					} else {
						form.setChild_death_dt(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("child_death_dt"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("child_mcts_id")) == null
							|| cursor.getString(
									cursor.getColumnIndex("child_mcts_id"))
									.equalsIgnoreCase("null")) {
						form.setChild_mcts_id("");
					} else {
						form.setChild_mcts_id(cursor.getString(cursor
								.getColumnIndex("child_mcts_id")));
					}
					if (cursor.getString(cursor.getColumnIndex("child_name")) == null
							|| cursor.getString(
									cursor.getColumnIndex("child_name"))
									.equalsIgnoreCase("null")) {
						form.setChild_name("");
					} else {
						form.setChild_name(cursor.getString(cursor
								.getColumnIndex("child_name")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("cried_after_birth")) == null
							|| cursor.getString(
									cursor.getColumnIndex("cried_after_birth"))
									.equalsIgnoreCase("null")) {
						form.setCried_after_birth("");
					} else {
						form.setCried_after_birth(cursor.getString(cursor
								.getColumnIndex("cried_after_birth")));
					}
					if (cursor.getString(cursor.getColumnIndex("Exclusive_BF")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Exclusive_BF"))
									.equalsIgnoreCase("null")) {
						form.setExclusive_BF("");
					} else {
						form.setExclusive_BF(cursor.getString(cursor
								.getColumnIndex("Exclusive_BF")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("complementry_BF")) == null
							|| cursor.getString(
									cursor.getColumnIndex("complementry_BF"))
									.equalsIgnoreCase("null")) {
						form.setComplementry_BF("");
					} else {
						form.setComplementry_BF(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("complementry_BF"))));
					}
					if (cursor.getString(cursor.getColumnIndex("bcg")) == null
							|| cursor.getString(cursor.getColumnIndex("bcg"))
									.equalsIgnoreCase("null")) {
						form.setBcg("");
					} else {
						form.setBcg(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("bcg"))));
					}
					if (cursor.getString(cursor.getColumnIndex("opv1")) == null
							|| cursor.getString(cursor.getColumnIndex("opv1"))
									.equalsIgnoreCase("null")) {
						form.setOpv1("");
					} else {
						form.setOpv1(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("opv1"))));
					}
					if (cursor.getString(cursor.getColumnIndex("dpt1")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt1"))
									.equalsIgnoreCase("null")) {
						form.setDpt1("");
					} else {
						form.setDpt1(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("dpt1"))));
					}
					if (cursor.getString(cursor.getColumnIndex("hepb1")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb1"))
									.equalsIgnoreCase("null")) {
						form.setHepb1("");
					} else {
						form.setHepb1(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("hepb1"))));
					}
					if (cursor.getString(cursor.getColumnIndex("opv2")) == null
							|| cursor.getString(cursor.getColumnIndex("opv2"))
									.equalsIgnoreCase("null")) {
						form.setOpv2("");
					} else {
						form.setOpv2(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("opv2"))));
					}
					if (cursor.getString(cursor.getColumnIndex("dpt2")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt2"))
									.equalsIgnoreCase("null")) {
						form.setDpt2("");
					} else {
						form.setDpt2(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("dpt2"))));
					}
					if (cursor.getString(cursor.getColumnIndex("hepb2")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb2"))
									.equalsIgnoreCase("null")) {
						form.setHepb2("");
					} else {
						form.setHepb2(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("hepb2"))));
					}
					if (cursor.getString(cursor.getColumnIndex("opv3")) == null
							|| cursor.getString(cursor.getColumnIndex("opv3"))
									.equalsIgnoreCase("null")) {
						form.setOpv3("");
					} else {
						form.setOpv3(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("opv3"))));
					}
					if (cursor.getString(cursor.getColumnIndex("dpt3")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt3"))
									.equalsIgnoreCase("null")) {
						form.setDpt3("");
					} else {
						form.setDpt3(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("dpt3"))));
					}
					if (cursor.getString(cursor.getColumnIndex("hepb3")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb3"))
									.equalsIgnoreCase("null")) {
						form.setHepb3("");
					} else {
						form.setHepb3(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("hepb3"))));
					}
					if (cursor.getString(cursor.getColumnIndex("measeals")) == null
							|| cursor.getString(
									cursor.getColumnIndex("measeals"))
									.equalsIgnoreCase("null")) {
						form.setMeaseals("");
					} else {
						form.setMeaseals(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("measeals"))));
					}
					if (cursor.getString(cursor.getColumnIndex("vitaminA")) == null
							|| cursor.getString(
									cursor.getColumnIndex("vitaminA"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA("");
					} else {
						form.setVitaminA(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("vitaminA"))));
					}
					if (cursor.getString(cursor.getColumnIndex("created_on")) == null
							|| cursor.getString(
									cursor.getColumnIndex("created_on"))
									.equalsIgnoreCase("null")) {
						form.setCreated_on("");
					} else {
						form.setCreated_on(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("created_on"))));
					}
					if (cursor.getString(cursor.getColumnIndex("modified_on")) == null
							|| cursor.getString(
									cursor.getColumnIndex("modified_on"))
									.equalsIgnoreCase("null")) {
						form.setModified_on("");
					} else {
						form.setModified_on(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("modified_on"))));
					}
					if (cursor.getString(cursor.getColumnIndex("FacilityType")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FacilityType"))
									.equalsIgnoreCase("null")) {
						form.setFacilityType(0);
					} else {
						form.setFacilityType(cursor.getInt(cursor
								.getColumnIndex("FacilityType")));
					}
					if (cursor.getString(cursor.getColumnIndex("Facility")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Facility"))
									.equalsIgnoreCase("null")) {
						form.setFacility("");
					} else {
						form.setFacility(cursor.getString(cursor
								.getColumnIndex("Facility")));
					}
					if (cursor.getString(cursor.getColumnIndex("opv4")) == null
							|| cursor.getString(cursor.getColumnIndex("opv4"))
									.equalsIgnoreCase("null")) {
						form.setOpv4("");
					} else {
						form.setOpv4(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("opv4"))));
					}
					if (cursor.getString(cursor.getColumnIndex("hepb4")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb4"))
									.equalsIgnoreCase("null")) {
						form.setHepb4("");
					} else {
						form.setHepb4(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("hepb4"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Pentavalent1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent1"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent1("");
					} else {
						form.setPentavalent1(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("Pentavalent1"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Pentavalent2")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent2"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent2("");
					} else {
						form.setPentavalent2(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("Pentavalent2"))));
					}
					if (cursor.getString(cursor.getColumnIndex("Pentavalent3")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent3"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent3("");
					} else {
						form.setPentavalent3(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("Pentavalent3"))));
					}
					if (cursor.getString(cursor.getColumnIndex("IPV")) == null
							|| cursor.getString(cursor.getColumnIndex("IPV"))
									.equalsIgnoreCase("null")) {
						form.setIPV("");
					} else {
						form.setIPV(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("IPV"))));
					}
					if (cursor.getString(cursor.getColumnIndex("DPTBooster")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DPTBooster"))
									.equalsIgnoreCase("null")) {
						form.setDPTBooster("");
					} else {
						form.setDPTBooster(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("DPTBooster"))));
					}
					if (cursor.getString(cursor.getColumnIndex("OPVBooster")) == null
							|| cursor.getString(
									cursor.getColumnIndex("OPVBooster"))
									.equalsIgnoreCase("null")) {
						form.setOPVBooster("");
					} else {
						form.setOPVBooster(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("OPVBooster"))));
					}
					if (cursor.getString(cursor
							.getColumnIndex("MeaslesTwoDose")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MeaslesTwoDose"))
									.equalsIgnoreCase("null")) {
						form.setMeaslesTwoDose("");
					} else {
						form.setMeaslesTwoDose(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("MeaslesTwoDose"))));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminAtwo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminAtwo"))
									.equalsIgnoreCase("null")) {
						form.setVitaminAtwo("");
					} else {
						form.setVitaminAtwo(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("VitaminAtwo"))));
					}
					if (cursor
							.getString(cursor.getColumnIndex("DPTBoostertwo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DPTBoostertwo"))
									.equalsIgnoreCase("null")) {
						form.setDPTBoostertwo("");
					} else {
						form.setDPTBoostertwo(Validate.changeDateFormatpadding(cursor
								.getString(cursor
										.getColumnIndex("DPTBoostertwo"))));
					}
					if (cursor.getString(cursor.getColumnIndex("ChildTT")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildTT"))
									.equalsIgnoreCase("null")) {
						form.setChildTT("");
					} else {
						form.setChildTT(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("ChildTT"))));
					}
					if (cursor.getString(cursor.getColumnIndex("JEVaccine1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("JEVaccine1"))
									.equalsIgnoreCase("null")) {
						form.setJEVaccine1("");
					} else {
						form.setJEVaccine1(cursor.getString(cursor
								.getColumnIndex("JEVaccine1")));
					}
					if (cursor.getString(cursor.getColumnIndex("JEVaccine2")) == null
							|| cursor.getString(
									cursor.getColumnIndex("JEVaccine2"))
									.equalsIgnoreCase("null")) {
						form.setJEVaccine2("");
					} else {
						form.setJEVaccine2(cursor.getString(cursor
								.getColumnIndex("JEVaccine2")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA3")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA3"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA3("");
					} else {
						form.setVitaminA3(cursor.getString(cursor
								.getColumnIndex("VitaminA3")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA4")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA4"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA4("");
					} else {
						form.setVitaminA4(cursor.getString(cursor
								.getColumnIndex("VitaminA4")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA5")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA5"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA5("");
					} else {
						form.setVitaminA5(cursor.getString(cursor
								.getColumnIndex("VitaminA5")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA6")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA6"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA6("");
					} else {
						form.setVitaminA6(cursor.getString(cursor
								.getColumnIndex("VitaminA6")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA7")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA7"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA7("");
					} else {
						form.setVitaminA7(cursor.getString(cursor
								.getColumnIndex("VitaminA7")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA8")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA8"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA8("");
					} else {
						form.setVitaminA8(cursor.getString(cursor
								.getColumnIndex("VitaminA8")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA9")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA9"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA9("");
					} else {
						form.setVitaminA9(cursor.getString(cursor
								.getColumnIndex("VitaminA9")));
					}
					if (cursor.getString(cursor.getColumnIndex("TT2")) == null
							|| cursor.getString(cursor.getColumnIndex("TT2"))
									.equalsIgnoreCase("null")) {
						form.setTT2("");
					} else {
						form.setTT2(cursor.getString(cursor
								.getColumnIndex("TT2")));
					}
					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	public void SavetblChild(String Flag, int AshaID, int ANMID,
			String ChildGUID, String pw_GUID, String HHGUID,
			String HHFamilyMemberGUID, String MotherGUID,
			String Date_Of_Registration, String Child_dob, int Gender,
			float Wt_of_child, int place_of_birth, String child_mcts_id,
			String child_name, int breastfeeding_within1H, String bcg,
			String opv1, String hepb1, String created_on, int created_by,
			String modified_on, int modified_by, int FacilityType,
			String Facility) {
		// TODO Auto-generated method stub

		String sql = "";
		if (Flag == "I") {
			sql = "Insert into tblChild(AshaID,ANMID,pw_GUID,HHGUID,HHFamilyMemberGUID,ChildGUID,MotherGUID,Date_Of_Registration,Child_dob,Gender,Wt_of_child,place_of_birth,child_mcts_id,child_name,breastfeeding_within1H,bcg,opv1,hepb1,created_on,created_by,modified_on,modified_by,IsEdited,FacilityType,Facility)values("
					+ AshaID
					+ ","
					+ ANMID
					+ ",'"
					+ pw_GUID
					+ "','"
					+ HHGUID
					+ "','"
					+ HHFamilyMemberGUID
					+ "','"
					+ ChildGUID
					+ "','"
					+ MotherGUID
					+ "','"
					+ Date_Of_Registration
					+ "','"
					+ Child_dob
					+ "',"
					+ Gender
					+ ","
					+ Wt_of_child
					+ ","
					+ place_of_birth
					+ ",'"
					+ child_mcts_id
					+ "','"
					+ child_name
					+ "',"
					+ breastfeeding_within1H
					+ ",'"
					+ bcg
					+ "','"
					+ opv1
					+ "','"
					+ hepb1
					+ "','"
					+ created_on
					+ "',"
					+ created_by
					+ ",'"
					+ modified_on
					+ "',"
					+ modified_by
					+ ",1," + FacilityType + ",'" + Facility + "')";
			String sql1 = "update tblPregnant_woman set  IsPregnant=0  where PWGUID='"
					+ pw_GUID + "'";
			dbIntraHealth.execSQL(sql1);
		} else if (Flag == "U") {
			sql = "Update tblChild set Child_dob = '" + Child_dob
					+ "', Gender = " + Gender + ", Wt_of_child = "
					+ Wt_of_child + ", place_of_birth = '" + place_of_birth
					+ "',FacilityType=" + FacilityType + ",Facility='"
					+ Facility + "', child_mcts_id = '" + child_mcts_id
					+ "', child_name = '" + child_name + "',AshaID=" + AshaID
					+ ",ANMID=" + ANMID + ", breastfeeding_within1H = "
					+ breastfeeding_within1H + ", modified_on = '"
					+ modified_on + "', modified_by = " + modified_by
					+ ", IsEdited = 1,bcg='" + bcg + "',opv1='" + opv1
					+ "',hepb1='" + hepb1 + "' where ChildGUID = '" + ChildGUID
					+ "'";
		}
		try {

			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DataProvider", "Error in SavetblChild :: " + e.getMessage());

		}

	}

	public void SavetblChildnew(String Flag, int AshaID, int ANMID,
			String ChildGUID, String pw_GUID, String HHGUID,
			String HHFamilyMemberGUID, String MotherGUID,
			String Date_Of_Registration, String Child_dob, int Gender,
			float Wt_of_child, int place_of_birth, String child_name,
			String created_on, int created_by, String modified_on,
			int modified_by, int FacilityType, String Facility, String bcg,
			String opv1, String hepb1) {
		String sql = "";
		if (Flag == "I") {
			sql = "Insert into tblChild(AshaID,ANMID,pw_GUID,HHGUID,HHFamilyMemberGUID,ChildGUID,MotherGUID,Date_Of_Registration,Child_dob,Gender,Wt_of_child,place_of_birth,child_name,created_on,created_by,modified_on,modified_by,IsEdited,FacilityType,Facility,bcg,opv1,hepb1)values("
					+ AshaID
					+ ","
					+ ANMID
					+ ",'"
					+ pw_GUID
					+ "','"
					+ HHGUID
					+ "','"
					+ HHFamilyMemberGUID
					+ "','"
					+ ChildGUID
					+ "','"
					+ MotherGUID
					+ "','"
					+ Date_Of_Registration
					+ "','"
					+ Child_dob
					+ "',"
					+ Gender
					+ ","
					+ Wt_of_child
					+ ","
					+ place_of_birth
					+ ",'"
					+ child_name
					+ "','"
					+ created_on
					+ "',"
					+ created_by
					+ ",'"
					+ modified_on
					+ "',"
					+ modified_by
					+ ",1,"
					+ FacilityType
					+ ",'"
					+ Facility
					+ "','" + bcg + "','" + opv1 + "','" + hepb1 + "')";
		} else if (Flag == "U") {
			sql = "Update tblChild set FacilityType=" + FacilityType
					+ ",Facility='" + Facility + "',  Child_dob = '"
					+ Child_dob + "', Gender = " + Gender + ", Wt_of_child = "
					+ Wt_of_child + ", place_of_birth = '" + place_of_birth
					+ "', child_name = '" + child_name + "', modified_on = '"
					+ modified_on + "', modified_by = " + modified_by
					+ ",AshaID=" + AshaID + ",ANMID=" + ANMID
					+ ", IsEdited = 1 ,bcg='" + bcg + "',opv1='" + opv1
					+ "',hepb1='" + hepb1 + "' where ChildGUID = '" + ChildGUID
					+ "'";
		}
		try {

			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DataProvider", "Error in SavetblChild :: " + e.getMessage());

		}
	}

	public void saveDeliveryData(String PWGUID, String DeliveryDateTime,
			int MaternalDeath, int PlaceofDeath, String DateofDeath,
			String OtherPlaceofDeath, int DeliveryType, int MotherDeathCause,
			int ISAbortion, int AbortionFacilityType, int ChildDeathCause,
			String MotherDCOther) {
		String sql = "";
		sql = "update tblPregnant_woman set PlaceofDeath=" + PlaceofDeath
				+ ", DateofDeath='" + DateofDeath + "', OtherPlaceofDeath='"
				+ OtherPlaceofDeath + "',MotherDCOther='" + MotherDCOther
				+ "',ChildDeathCause='" + ChildDeathCause + "', DeliveryType="
				+ DeliveryType + ", MotherDeathCause=" + MotherDeathCause
				+ ", ISAbortion=" + ISAbortion + ", AbortionFacilityType="
				+ AbortionFacilityType + ",MaternalDeath=" + MaternalDeath
				+ ",DeliveryDateTime='" + DeliveryDateTime + "'where PWGUID='"
				+ PWGUID + "'";
		try {

			executeSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DataProvider", "Error in DeliveryData :: " + e.getMessage());

		}
	}

	public ArrayList<tblmstFPFDetail> getFP_Detail(String FPF_Guid, int flag) {
		ArrayList<tblmstFPFDetail> data = null;
		String sql = "";

		if (flag == 0) {
			sql = "select * from tblFP_followup  ";
		} else if (flag == 1) {
			sql = "select * from tblFP_followup where WomenName_Guid='"
					+ FPF_Guid + "'  order by uid desc ";
		} else if (flag == 2) {
			sql = "Select * from tblFP_followup where IsEdited = 1";
		} else if (flag == 3) {
			sql = "select * from tblFP_followup where WomenName_Guid='"
					+ FPF_Guid
					+ "'  and (MethodAdopted='4'or MethodAdopted='5') ";
		} else if (flag == 4) {
			sql = "select * from tblFP_followup where WomenName_Guid='"
					+ FPF_Guid
					+ "'  and (MethodAdopted='6'or MethodAdopted='7') ";
		} else if (flag == 5) {
			sql = "select * from tblFP_followup where WomenName_Guid='"
					+ FPF_Guid
					+ "'  and (MethodAdopted='6'or MethodAdopted='7' or MethodAdopted='4'or MethodAdopted='5') ";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstFPFDetail>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstFPFDetail form = new tblmstFPFDetail();

					if (cursor.getString(cursor.getColumnIndex("WomenName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("WomenName"))
									.equalsIgnoreCase("null")) {
						form.setWomenName("");
					} else {
						form.setWomenName(cursor.getString(cursor
								.getColumnIndex("WomenName")));

					}
					if (cursor
							.getString(cursor.getColumnIndex("Hushband_name")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Hushband_name"))
									.equalsIgnoreCase("null")) {
						form.setHushband_name("");
					} else {
						form.setHushband_name(cursor.getString(cursor
								.getColumnIndex("Hushband_name")));

					}
					if (cursor.getString(cursor.getColumnIndex("PF_date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PF_date"))
									.equalsIgnoreCase("null")) {
						form.setPF_date("");
					} else {
						form.setPF_date(Validate.changeDateFormatpadding(cursor
								.getString(cursor.getColumnIndex("PF_date"))));

					}
					if (cursor.getString(cursor.getColumnIndex("CurrF_date")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CurrF_date"))
									.equalsIgnoreCase("null")) {
						form.setCurrF_date("");
					} else {
						form.setCurrF_date(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CurrF_date"))));

					}
					if (cursor
							.getString(cursor.getColumnIndex("MethodAdopted")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MethodAdopted"))
									.equalsIgnoreCase("null")) {
						form.setMethodAdopted(0);
					} else {
						form.setMethodAdopted(cursor.getInt(cursor
								.getColumnIndex("MethodAdopted")));

					}

					if (cursor.getString(cursor.getColumnIndex("Pregnant")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pregnant"))
									.equalsIgnoreCase("null")) {
						form.setPregnant(0);
					} else {
						form.setPregnant(cursor.getInt(cursor
								.getColumnIndex("Pregnant")));

					}
					if (cursor
							.getString(cursor.getColumnIndex("Pregnant_test")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pregnant_test"))
									.equalsIgnoreCase("null")) {
						form.setPregnant_test(0);
					} else {
						form.setPregnant_test(cursor.getInt(cursor
								.getColumnIndex("Pregnant_test")));

					}
					if (cursor.getString(cursor.getColumnIndex("FPF_Guid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FPF_Guid"))
									.equalsIgnoreCase("null")) {
						form.setFPF_Guid("");
					} else {
						form.setFPF_Guid(cursor.getString(cursor
								.getColumnIndex("FPF_Guid")));

					}
					if (cursor.getString(cursor
							.getColumnIndex("WomenName_Guid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("WomenName_Guid"))
									.equalsIgnoreCase("null")) {
						form.setWomenName_Guid("");
					} else {
						form.setWomenName_Guid(cursor.getString(cursor
								.getColumnIndex("WomenName_Guid")));

					}
					if (cursor
							.getString(cursor.getColumnIndex("Hushband_Guid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Hushband_Guid"))
									.equalsIgnoreCase("null")) {
						form.setHushband_Guid("");
					} else {
						form.setHushband_Guid(cursor.getString(cursor
								.getColumnIndex("Hushband_Guid")));

					}
					if (cursor.getString(cursor.getColumnIndex("UID")) == null
							|| cursor.getString(cursor.getColumnIndex("UID"))
									.equalsIgnoreCase("null")) {
						form.setUID(0);
					} else {
						form.setUID(cursor.getInt(cursor.getColumnIndex("UID")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsEdited")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsEdited"))
									.equalsIgnoreCase("null")) {
						form.setIsEdited(0);
					} else {
						form.setIsEdited(cursor.getInt(cursor
								.getColumnIndex("IsEdited")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsUploaded")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsUploaded"))
									.equalsIgnoreCase("null")) {
						form.setIsUploaded(0);
					} else {
						form.setIsUploaded(cursor.getInt(cursor
								.getColumnIndex("IsUploaded")));

					}
					form.setAshaID(cursor.getInt(cursor
							.getColumnIndex("AshaID")));
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));
					if (cursor.getString(cursor
							.getColumnIndex("MethodadoptedDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MethodadoptedDate"))
									.equalsIgnoreCase("null")) {
						form.setMethodadoptedDate("");
					} else {
						form.setMethodadoptedDate(cursor.getString(cursor
								.getColumnIndex("MethodadoptedDate")));

					}
					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));

					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in tblFP_followup :: " + exception.getMessage());
		}
		return data;
	}

	public int InsertFPDetails(int ANMID, int AshaID, String womenName,
			String hushband_name, String pF_date, String currF_date,
			int methodAdopted, int pregnant, int pregnant_test,
			String fPF_Guid, String womenName_Guid, String hushband_Guid,
			String flag, String MehodadoptedDate, String CreatedBy) {
		String sql = "";
		String CurrentDate = Validate.getcurrentdate();
		if (flag.equalsIgnoreCase("I")) {
			sql = "Insert into tblFP_followup(WomenName,ANMID,AshaID,Hushband_name,PF_date,CurrF_date,MethodAdopted,Pregnant,Pregnant_test,FPF_Guid,WomenName_Guid,Hushband_Guid,IsEdited,IsUploaded,MethodadoptedDate,CreatedBy,CreatedOn) values('"
					+ womenName
					+ "',"
					+ ANMID
					+ ","
					+ AshaID
					+ ",'"
					+ hushband_name
					+ "','"
					+ pF_date
					+ "','"
					+ currF_date
					+ "',"
					+ methodAdopted
					+ ","
					+ pregnant
					+ ","
					+ pregnant_test
					+ ",'"
					+ fPF_Guid
					+ "','"
					+ womenName_Guid
					+ "','"
					+ hushband_Guid
					+ "',1,1,'"
					+ MehodadoptedDate
					+ "','" + CreatedBy + "','" + CurrentDate + "') ";
		}
		try {
			executeSql(sql);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public ArrayList<tblmstFPQues> getFP_Ques(String FPF_Guid, int flag) {
		ArrayList<tblmstFPQues> data = null;
		String sql = "";

		if (flag == 0) {
			sql = "select * from tblmstFPQues where grp!=7 and grp!=8 ";
		} else if (flag == 1) {
			sql = "select * from tblmstFPQues where grp=7 ";
		} else if (flag == 2) {
			sql = "select * from tblmstFPQues where  grp=8";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstFPQues>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstFPQues form = new tblmstFPQues();

					if (cursor.getString(cursor.getColumnIndex("ftext")) == null
							|| cursor.getString(cursor.getColumnIndex("ftext"))
									.equalsIgnoreCase("null")) {
						form.setFtext("");
					} else {
						form.setFtext(cursor.getString(cursor
								.getColumnIndex("ftext")));

					}
					if (cursor.getString(cursor.getColumnIndex("oinfo")) == null
							|| cursor.getString(cursor.getColumnIndex("oinfo"))
									.equalsIgnoreCase("null")) {
						form.setOinfo("");
					} else {
						form.setOinfo(cursor.getString(cursor
								.getColumnIndex("oinfo")));

					}
					if (cursor.getString(cursor.getColumnIndex("Ans")) == null
							|| cursor.getString(cursor.getColumnIndex("Ans"))
									.equalsIgnoreCase("null")) {
						form.setAns("");
					} else {
						form.setAns(cursor.getString(cursor
								.getColumnIndex("Ans")));

					}
					if (cursor.getString(cursor.getColumnIndex("oprompt")) == null
							|| cursor.getString(
									cursor.getColumnIndex("oprompt"))
									.equalsIgnoreCase("null")) {
						form.setOprompt("");
					} else {
						form.setOprompt(cursor.getString(cursor
								.getColumnIndex("oprompt")));

					}

					if (cursor.getString(cursor.getColumnIndex("Q_id")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_id"))
									.equalsIgnoreCase("null")) {
						form.setQ_id(0);
					} else {
						form.setQ_id(cursor.getInt(cursor
								.getColumnIndex("Q_id")));

					}

					if (cursor.getString(cursor.getColumnIndex("grp")) == null
							|| cursor.getString(cursor.getColumnIndex("grp"))
									.equalsIgnoreCase("null")) {
						form.setGrp(0);
					} else {
						form.setGrp(cursor.getInt(cursor.getColumnIndex("grp")));

					}
					if (cursor.getString(cursor.getColumnIndex("q_type")) == null
							|| cursor
									.getString(cursor.getColumnIndex("q_type"))
									.equalsIgnoreCase("null")) {
						form.setQ_type(0);
					} else {
						form.setQ_type(cursor.getInt(cursor
								.getColumnIndex("q_type")));

					}
					if (cursor.getString(cursor.getColumnIndex("y_qid")) == null
							|| cursor.getString(cursor.getColumnIndex("y_qid"))
									.equalsIgnoreCase("null")) {
						form.setY_qid(0);
					} else {
						form.setY_qid(cursor.getInt(cursor
								.getColumnIndex("y_qid")));

					}
					if (cursor.getString(cursor.getColumnIndex("n_qid")) == null
							|| cursor.getString(cursor.getColumnIndex("n_qid"))
									.equalsIgnoreCase("null")) {
						form.setN_qid(0);
					} else {
						form.setN_qid(cursor.getInt(cursor
								.getColumnIndex("n_qid")));

					}
					if (cursor.getString(cursor.getColumnIndex("def")) == null
							|| cursor.getString(cursor.getColumnIndex("def"))
									.equalsIgnoreCase("null")) {
						form.setDef(0);
					} else {
						form.setDef(cursor.getInt(cursor.getColumnIndex("def")));

					}
					if (cursor.getString(cursor.getColumnIndex("finishid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("finishid"))
									.equalsIgnoreCase("null")) {
						form.setFinishid(0);
					} else {
						form.setFinishid(cursor.getInt(cursor
								.getColumnIndex("finishid")));

					}
					if (cursor.getString(cursor.getColumnIndex("pans")) == null
							|| cursor.getString(cursor.getColumnIndex("pans"))
									.equalsIgnoreCase("null")) {
						form.setPans(0);
					} else {
						form.setPans(cursor.getInt(cursor
								.getColumnIndex("pans")));

					}
					if (cursor.getString(cursor.getColumnIndex("qvid")) == null
							|| cursor.getString(cursor.getColumnIndex("qvid"))
									.equalsIgnoreCase("null")) {
						form.setQvid(0);
					} else {
						form.setQvid(cursor.getInt(cursor
								.getColumnIndex("qvid")));

					}
					if (cursor.getString(cursor.getColumnIndex("dispseq")) == null
							|| cursor.getString(
									cursor.getColumnIndex("dispseq"))
									.equalsIgnoreCase("null")) {
						form.setDispseq(0);
					} else {
						form.setDispseq(cursor.getInt(cursor
								.getColumnIndex("dispseq")));

					}
					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public int saveFP(int AshaID, int ANMID, String dtaans,
			String womenName_Guid, String pncguid, String fPAns_Guid,
			String ansfield, String flag, String CreatedBy) {
		// TODO Auto-generated method stub
		String sql = "";
		String CurrentDate = Validate.getcurrentdate();
		if (flag.equalsIgnoreCase("I")) {
			sql = "Insert into tblFP_visit(AshaID,ANMID,FPAns_Guid,"
					+ ansfield
					+ ",WomenName_Guid,VisitDate,IsEdited,IsUploaded,CreatedBy,CreatedOn) values("
					+ AshaID + "," + ANMID + ",'" + fPAns_Guid + "','" + dtaans
					+ "','" + womenName_Guid + "','" + CurrentDate + "',1,0,'"
					+ CreatedBy + "','" + CurrentDate + "') ";
		} else {
			sql = "update tblFP_visit set  " + ansfield + "='" + dtaans
					+ "',IsEdited=1 where womenName_Guid='" + womenName_Guid
					+ "' and VisitDate='" + CurrentDate + "' ";
		}
		try {
			executeSql(sql);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
	}

	public ArrayList<tblmstFPAns> getFP_Ans(String WomenName_Guid, int flag) {
		ArrayList<tblmstFPAns> data = null;
		String sql = "";

		if (flag == 0) {
			sql = "select * from tblFP_visit where WomenName_Guid='"
					+ WomenName_Guid + "'  order by uid desc ";
		} else if (flag == 1) {
			sql = "Select * from tblFP_visit where IsEdited = 1";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstFPAns>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstFPAns form = new tblmstFPAns();

					if (cursor.getString(cursor.getColumnIndex("FPAns_Guid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FPAns_Guid"))
									.equalsIgnoreCase("null")) {
						form.setFPAns_Guid("");
					} else {
						form.setFPAns_Guid(cursor.getString(cursor
								.getColumnIndex("FPAns_Guid")));

					}
					if (cursor.getString(cursor
							.getColumnIndex("WomenName_Guid")) == null
							|| cursor.getString(
									cursor.getColumnIndex("WomenName_Guid"))
									.equalsIgnoreCase("null")) {
						form.setWomenName_Guid("");
					} else {
						form.setWomenName_Guid(cursor.getString(cursor
								.getColumnIndex("WomenName_Guid")));

					}
					if (cursor.getString(cursor.getColumnIndex("VisitDate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VisitDate"))
									.equalsIgnoreCase("null")) {
						form.setVisitDate("");
					} else {
						form.setVisitDate(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("VisitDate"))));

					}
					if (cursor.getString(cursor.getColumnIndex("Q1")) == null
							|| cursor.getString(cursor.getColumnIndex("Q1"))
									.equalsIgnoreCase("null")) {
						form.setQ1("");
					} else {
						form.setQ1(cursor.getString(cursor.getColumnIndex("Q1")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q5")) == null
							|| cursor.getString(cursor.getColumnIndex("Q5"))
									.equalsIgnoreCase("null")) {
						form.setQ5("");
					} else {
						form.setQ5(cursor.getString(cursor.getColumnIndex("Q5")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q6")) == null
							|| cursor.getString(cursor.getColumnIndex("Q6"))
									.equalsIgnoreCase("null")) {
						form.setQ6("");
					} else {
						form.setQ6(cursor.getString(cursor.getColumnIndex("Q6")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q7")) == null
							|| cursor.getString(cursor.getColumnIndex("Q7"))
									.equalsIgnoreCase("null")) {
						form.setQ7("");
					} else {
						form.setQ7(cursor.getString(cursor.getColumnIndex("Q7")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q8")) == null
							|| cursor.getString(cursor.getColumnIndex("Q8"))
									.equalsIgnoreCase("null")) {
						form.setQ8("");
					} else {
						form.setQ8(cursor.getString(cursor.getColumnIndex("Q8")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q24")) == null
							|| cursor.getString(cursor.getColumnIndex("Q24"))
									.equalsIgnoreCase("null")) {
						form.setQ24("");
					} else {
						form.setQ24(cursor.getString(cursor
								.getColumnIndex("Q24")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q36")) == null
							|| cursor.getString(cursor.getColumnIndex("Q36"))
									.equalsIgnoreCase("null")) {
						form.setQ36("");
					} else {
						form.setQ36(cursor.getString(cursor
								.getColumnIndex("Q36")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q39")) == null
							|| cursor.getString(cursor.getColumnIndex("Q39"))
									.equalsIgnoreCase("null")) {
						form.setQ39("");
					} else {
						form.setQ39(cursor.getString(cursor
								.getColumnIndex("Q39")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q52")) == null
							|| cursor.getString(cursor.getColumnIndex("Q52"))
									.equalsIgnoreCase("null")) {
						form.setQ52("");
					} else {
						form.setQ52(cursor.getString(cursor
								.getColumnIndex("Q52")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q56")) == null
							|| cursor.getString(cursor.getColumnIndex("Q56"))
									.equalsIgnoreCase("null")) {
						form.setQ56("");
					} else {
						form.setQ56(cursor.getString(cursor
								.getColumnIndex("Q56")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q59")) == null
							|| cursor.getString(cursor.getColumnIndex("Q59"))
									.equalsIgnoreCase("null")) {
						form.setQ59("");
					} else {
						form.setQ59(cursor.getString(cursor
								.getColumnIndex("Q59")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q60")) == null
							|| cursor.getString(cursor.getColumnIndex("Q60"))
									.equalsIgnoreCase("null")) {
						form.setQ60("");
					} else {
						form.setQ60(cursor.getString(cursor
								.getColumnIndex("Q60")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q61")) == null
							|| cursor.getString(cursor.getColumnIndex("Q61"))
									.equalsIgnoreCase("null")) {
						form.setQ61("");
					} else {
						form.setQ61(cursor.getString(cursor
								.getColumnIndex("Q61")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q62")) == null
							|| cursor.getString(cursor.getColumnIndex("Q62"))
									.equalsIgnoreCase("null")) {
						form.setQ62("");
					} else {
						form.setQ62(cursor.getString(cursor
								.getColumnIndex("Q62")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q20")) == null
							|| cursor.getString(cursor.getColumnIndex("Q20"))
									.equalsIgnoreCase("null")) {
						form.setQ20("");
					} else {
						form.setQ20(cursor.getString(cursor
								.getColumnIndex("Q20")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q21")) == null
							|| cursor.getString(cursor.getColumnIndex("Q21"))
									.equalsIgnoreCase("null")) {
						form.setQ21("");
					} else {
						form.setQ21(cursor.getString(cursor
								.getColumnIndex("Q21")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q37")) == null
							|| cursor.getString(cursor.getColumnIndex("Q37"))
									.equalsIgnoreCase("null")) {
						form.setQ37("");
					} else {
						form.setQ37(cursor.getString(cursor
								.getColumnIndex("Q37")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q57")) == null
							|| cursor.getString(cursor.getColumnIndex("Q57"))
									.equalsIgnoreCase("null")) {
						form.setQ57("");
					} else {
						form.setQ57(cursor.getString(cursor
								.getColumnIndex("Q57")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q76")) == null
							|| cursor.getString(cursor.getColumnIndex("Q76"))
									.equalsIgnoreCase("null")) {
						form.setQ76("");
					} else {
						form.setQ76(cursor.getString(cursor
								.getColumnIndex("Q76")));

					}

					if (cursor.getString(cursor.getColumnIndex("UID")) == null
							|| cursor.getString(cursor.getColumnIndex("UID"))
									.equalsIgnoreCase("null")) {
						form.setUID(0);
					} else {
						form.setUID(cursor.getInt(cursor.getColumnIndex("UID")));

					}

					if (cursor.getString(cursor.getColumnIndex("AshaID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("AshaID"))
									.equalsIgnoreCase("null")) {
						form.setAshaID(0);
					} else {
						form.setAshaID(cursor.getInt(cursor
								.getColumnIndex("AshaID")));

					}

					if (cursor.getString(cursor.getColumnIndex("ANMID")) == null
							|| cursor.getString(cursor.getColumnIndex("ANMID"))
									.equalsIgnoreCase("null")) {
						form.setANMID(0);
					} else {
						form.setANMID(cursor.getInt(cursor
								.getColumnIndex("ANMID")));

					}
					form.setCreatedBy(cursor.getInt(cursor
							.getColumnIndex("CreatedBy")));
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));

					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblmstimmunizationANS> gettblmstimmunizationANS(
			String FPF_Guid, int flag) {
		ArrayList<tblmstimmunizationANS> data = null;
		String sql = "";

		if (flag == 0) {
			sql = "select * from tblmstimmunizationANS where IsEdited=1";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblmstimmunizationANS>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblmstimmunizationANS form = new tblmstimmunizationANS();

					if (cursor.getString(cursor.getColumnIndex("Q1")) == null
							|| cursor.getString(cursor.getColumnIndex("Q1"))
									.equalsIgnoreCase("null")) {
						form.setQ1("");
					} else {
						form.setQ1(cursor.getString(cursor.getColumnIndex("Q1")));

					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn"))));

					}
					
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UpdatedOn"))));

					}

					if (cursor.getString(cursor
							.getColumnIndex("ImmunizationGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ImmunizationGUID"))
									.equalsIgnoreCase("null")) {
						form.setImmunizationGUID("");
					} else {
						form.setImmunizationGUID(cursor.getString(cursor
								.getColumnIndex("ImmunizationGUID")));

					}
					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));

					}
					if (cursor.getString(cursor.getColumnIndex("TimeDuration")) == null
							|| cursor.getString(
									cursor.getColumnIndex("TimeDuration"))
									.equalsIgnoreCase("null")) {
						form.setTimeDuration("");
					} else {
						form.setTimeDuration(cursor.getString(cursor
								.getColumnIndex("TimeDuration")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q2")) == null
							|| cursor.getString(cursor.getColumnIndex("Q2"))
									.equalsIgnoreCase("null")) {
						form.setQ2(0);
					} else {
						form.setQ2(cursor.getInt(cursor.getColumnIndex("Q2")));

					}

					if (cursor.getString(cursor.getColumnIndex("Q3")) == null
							|| cursor.getString(cursor.getColumnIndex("Q3"))
									.equalsIgnoreCase("null")) {
						form.setQ3(0);
					} else {
						form.setQ3(cursor.getInt(cursor.getColumnIndex("Q3")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q4")) == null
							|| cursor.getString(cursor.getColumnIndex("Q4"))
									.equalsIgnoreCase("null")) {
						form.setQ4(0);
					} else {
						form.setQ4(cursor.getInt(cursor.getColumnIndex("Q4")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q5")) == null
							|| cursor.getString(cursor.getColumnIndex("Q5"))
									.equalsIgnoreCase("null")) {
						form.setQ5(0);
					} else {
						form.setQ5(cursor.getInt(cursor.getColumnIndex("Q5")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q6")) == null
							|| cursor.getString(cursor.getColumnIndex("Q6"))
									.equalsIgnoreCase("null")) {
						form.setQ6(0);
					} else {
						form.setQ6(cursor.getInt(cursor.getColumnIndex("Q6")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q7")) == null
							|| cursor.getString(cursor.getColumnIndex("Q7"))
									.equalsIgnoreCase("null")) {
						form.setQ7(0);
					} else {
						form.setQ7(cursor.getInt(cursor.getColumnIndex("Q7")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q14")) == null
							|| cursor.getString(cursor.getColumnIndex("Q14"))
									.equalsIgnoreCase("null")) {
						form.setQ14(0);
					} else {
						form.setQ14(cursor.getInt(cursor.getColumnIndex("Q14")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q25")) == null
							|| cursor.getString(cursor.getColumnIndex("Q25"))
									.equalsIgnoreCase("null")) {
						form.setQ25(0);
					} else {
						form.setQ25(cursor.getInt(cursor.getColumnIndex("Q25")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q36")) == null
							|| cursor.getString(cursor.getColumnIndex("Q36"))
									.equalsIgnoreCase("null")) {
						form.setQ36(0);
					} else {
						form.setQ36(cursor.getInt(cursor.getColumnIndex("Q36")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q47")) == null
							|| cursor.getString(cursor.getColumnIndex("Q47"))
									.equalsIgnoreCase("null")) {
						form.setQ47(0);
					} else {
						form.setQ47(cursor.getInt(cursor.getColumnIndex("Q47")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsDeleted")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsDeleted"))
									.equalsIgnoreCase("null")) {
						form.setIsDeleted(0);
					} else {
						form.setIsDeleted(cursor.getInt(cursor
								.getColumnIndex("IsDeleted")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsEdited")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsEdited"))
									.equalsIgnoreCase("null")) {
						form.setIsEdited(0);
					} else {
						form.setIsEdited(cursor.getInt(cursor
								.getColumnIndex("IsEdited")));

					}
					if (cursor.getString(cursor.getColumnIndex("CreatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedBy"))
									.equalsIgnoreCase("null")) {
						form.setCreatedBy(0);
					} else {
						form.setCreatedBy(cursor.getInt(cursor
								.getColumnIndex("CreatedBy")));

					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedBy"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedBy(0);
					} else {
						form.setUpdatedBy(cursor.getInt(cursor
								.getColumnIndex("UpdatedBy")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q53")) == null
							|| cursor.getString(cursor.getColumnIndex("Q53"))
									.equalsIgnoreCase("null")) {
						form.setQ53(0);
					} else {
						form.setQ53(cursor.getInt(cursor.getColumnIndex("Q53")));

					}
					if (cursor.getString(cursor.getColumnIndex("AshaID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("AshaID"))
									.equalsIgnoreCase("null")) {
						form.setAshaID(0);
					} else {
						form.setAshaID(cursor.getInt(cursor
								.getColumnIndex("AshaID")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q13")) == null
							|| cursor.getString(cursor.getColumnIndex("Q13"))
									.equalsIgnoreCase("null")) {
						form.setQ13(0);
					} else {
						form.setQ13(cursor.getInt(cursor.getColumnIndex("Q13")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q21")) == null
							|| cursor.getString(cursor.getColumnIndex("Q21"))
									.equalsIgnoreCase("null")) {
						form.setQ21(0);
					} else {
						form.setQ21(cursor.getInt(cursor.getColumnIndex("Q21")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q22")) == null
							|| cursor.getString(cursor.getColumnIndex("Q22"))
									.equalsIgnoreCase("null")) {
						form.setQ22(0);
					} else {
						form.setQ22(cursor.getInt(cursor.getColumnIndex("Q22")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q24")) == null
							|| cursor.getString(cursor.getColumnIndex("Q24"))
									.equalsIgnoreCase("null")) {
						form.setQ24(0);
					} else {
						form.setQ24(cursor.getInt(cursor.getColumnIndex("Q24")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q27")) == null
							|| cursor.getString(cursor.getColumnIndex("Q27"))
									.equalsIgnoreCase("null")) {
						form.setQ27(0);
					} else {
						form.setQ27(cursor.getInt(cursor.getColumnIndex("Q27")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q37")) == null
							|| cursor.getString(cursor.getColumnIndex("Q37"))
									.equalsIgnoreCase("null")) {
						form.setQ37(0);
					} else {
						form.setQ37(cursor.getInt(cursor.getColumnIndex("Q37")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q45")) == null
							|| cursor.getString(cursor.getColumnIndex("Q45"))
									.equalsIgnoreCase("null")) {
						form.setQ45(0);
					} else {
						form.setQ45(cursor.getInt(cursor.getColumnIndex("Q45")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q46")) == null
							|| cursor.getString(cursor.getColumnIndex("Q46"))
									.equalsIgnoreCase("null")) {
						form.setQ46(0);
					} else {
						form.setQ46(cursor.getInt(cursor.getColumnIndex("Q46")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q51")) == null
							|| cursor.getString(cursor.getColumnIndex("Q51"))
									.equalsIgnoreCase("null")) {
						form.setQ51(0);
					} else {
						form.setQ51(cursor.getInt(cursor.getColumnIndex("Q51")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q61")) == null
							|| cursor.getString(cursor.getColumnIndex("Q61"))
									.equalsIgnoreCase("null")) {
						form.setQ61(0);
					} else {
						form.setQ61(cursor.getInt(cursor.getColumnIndex("Q61")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q70")) == null
							|| cursor.getString(cursor.getColumnIndex("Q70"))
									.equalsIgnoreCase("null")) {
						form.setQ70(0);
					} else {
						form.setQ70(cursor.getInt(cursor.getColumnIndex("Q70")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q73")) == null
							|| cursor.getString(cursor.getColumnIndex("Q73"))
									.equalsIgnoreCase("null")) {
						form.setQ73(0);
					} else {
						form.setQ73(cursor.getInt(cursor.getColumnIndex("Q73")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q75")) == null
							|| cursor.getString(cursor.getColumnIndex("Q75"))
									.equalsIgnoreCase("null")) {
						form.setQ75(0);
					} else {
						form.setQ75(cursor.getInt(cursor.getColumnIndex("Q75")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q85")) == null
							|| cursor.getString(cursor.getColumnIndex("Q85"))
									.equalsIgnoreCase("null")) {
						form.setQ85(0);
					} else {
						form.setQ85(cursor.getInt(cursor.getColumnIndex("Q85")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q86")) == null
							|| cursor.getString(cursor.getColumnIndex("Q86"))
									.equalsIgnoreCase("null")) {
						form.setQ86(0);
					} else {
						form.setQ86(cursor.getInt(cursor.getColumnIndex("Q86")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q94")) == null
							|| cursor.getString(cursor.getColumnIndex("Q94"))
									.equalsIgnoreCase("null")) {
						form.setQ94(0);
					} else {
						form.setQ94(cursor.getInt(cursor.getColumnIndex("Q94")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q111")) == null
							|| cursor.getString(cursor.getColumnIndex("Q111"))
									.equalsIgnoreCase("null")) {
						form.setQ111(0);
					} else {
						form.setQ111(cursor.getInt(cursor
								.getColumnIndex("Q111")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q112")) == null
							|| cursor.getString(cursor.getColumnIndex("Q112"))
									.equalsIgnoreCase("null")) {
						form.setQ112(0);
					} else {
						form.setQ112(cursor.getInt(cursor
								.getColumnIndex("Q112")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q117")) == null
							|| cursor.getString(cursor.getColumnIndex("Q117"))
									.equalsIgnoreCase("null")) {
						form.setQ117(0);
					} else {
						form.setQ117(cursor.getInt(cursor
								.getColumnIndex("Q117")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q124")) == null
							|| cursor.getString(cursor.getColumnIndex("Q124"))
									.equalsIgnoreCase("null")) {
						form.setQ124(0);
					} else {
						form.setQ124(cursor.getInt(cursor
								.getColumnIndex("Q124")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q125")) == null
							|| cursor.getString(cursor.getColumnIndex("Q125"))
									.equalsIgnoreCase("null")) {
						form.setQ125(0);
					} else {
						form.setQ125(cursor.getInt(cursor
								.getColumnIndex("Q125")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q146")) == null
							|| cursor.getString(cursor.getColumnIndex("Q146"))
									.equalsIgnoreCase("null")) {
						form.setQ146(0);
					} else {
						form.setQ146(cursor.getInt(cursor
								.getColumnIndex("Q146")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q69")) == null
							|| cursor.getString(cursor.getColumnIndex("Q69"))
									.equalsIgnoreCase("null")) {
						form.setQ69(0);
					} else {
						form.setQ69(cursor.getInt(cursor.getColumnIndex("Q69")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q48")) == null
							|| cursor.getString(cursor.getColumnIndex("Q48"))
									.equalsIgnoreCase("null")) {
						form.setQ48(0);
					} else {
						form.setQ48(cursor.getInt(cursor.getColumnIndex("Q48")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q145")) == null
							|| cursor.getString(cursor.getColumnIndex("Q145"))
									.equalsIgnoreCase("null")) {
						form.setQ145(0);
					} else {
						form.setQ145(cursor.getInt(cursor.getColumnIndex("Q145")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q148")) == null
							|| cursor.getString(cursor.getColumnIndex("Q148"))
									.equalsIgnoreCase("null")) {
						form.setQ148(0);
					} else {
						form.setQ148(cursor.getInt(cursor.getColumnIndex("Q148")));

					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	public ArrayList<tblPNChomevisit_ANS> gettblPNChomevisit_ANS(
			String FPF_Guid, int flag) {
		ArrayList<tblPNChomevisit_ANS> data = null;
		String sql = "";

		if (flag == 0) {
			sql = "select * from tblPNChomevisit_ANS where IsEdited=1";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tblPNChomevisit_ANS>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblPNChomevisit_ANS form = new tblPNChomevisit_ANS();

					if (cursor.getString(cursor.getColumnIndex("PWGUID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWGUID"))
									.equalsIgnoreCase("null")) {
						form.setPWGUID("");
					} else {
						form.setPWGUID(cursor.getString(cursor
								.getColumnIndex("PWGUID")));

					}
					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));

					}
					if (cursor.getString(cursor.getColumnIndex("PNCGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("PNCGUID"))
									.equalsIgnoreCase("null")) {
						form.setPNCGUID("");
					} else {
						form.setPNCGUID(cursor.getString(cursor
								.getColumnIndex("PNCGUID")));

					}

					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("CreatedOn"))));

					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedOn"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedOn("");
					} else {
						form.setUpdatedOn(Validate
								.changeDateFormatpadding(cursor
										.getString(cursor
												.getColumnIndex("UpdatedOn"))));

					}

					if (cursor.getString(cursor.getColumnIndex("VisitNo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VisitNo"))
									.equalsIgnoreCase("null")) {
						form.setVisitNo(0);
					} else {
						form.setVisitNo(cursor.getInt(cursor
								.getColumnIndex("VisitNo")));

					}

					if (cursor.getString(cursor.getColumnIndex("Q_0")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_0"))
									.equalsIgnoreCase("null")) {
						form.setQ_0("");
					} else {
						form.setQ_0(cursor.getString(cursor
								.getColumnIndex("Q_0")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_1")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_1"))
									.equalsIgnoreCase("null")) {
						form.setQ_1(0);
					} else {
						form.setQ_1(cursor.getInt(cursor.getColumnIndex("Q_1")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_2")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_2"))
									.equalsIgnoreCase("null")) {
						form.setQ_2("");
					} else {
						form.setQ_2(cursor.getString(cursor
								.getColumnIndex("Q_2")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_3")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_3"))
									.equalsIgnoreCase("null")) {
						form.setQ_3("");
					} else {
						form.setQ_3(cursor.getString(cursor
								.getColumnIndex("Q_3")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_4")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_4"))
									.equalsIgnoreCase("null")) {
						form.setQ_4(0);
					} else {
						form.setQ_4(cursor.getInt(cursor.getColumnIndex("Q_4")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_5")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_5"))
									.equalsIgnoreCase("null")) {
						form.setQ_5(0);
					} else {
						form.setQ_5(cursor.getInt(cursor.getColumnIndex("Q_5")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_6")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_6"))
									.equalsIgnoreCase("null")) {
						form.setQ_6("");
					} else {
						form.setQ_6(cursor.getString(cursor
								.getColumnIndex("Q_6")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_7")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_7"))
									.equalsIgnoreCase("null")) {
						form.setQ_7("");
					} else {
						form.setQ_7(cursor.getString(cursor
								.getColumnIndex("Q_7")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_8")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_8"))
									.equalsIgnoreCase("null")) {
						form.setQ_8(0);
					} else {
						form.setQ_8(cursor.getInt(cursor.getColumnIndex("Q_8")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_9")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_9"))
									.equalsIgnoreCase("null")) {
						form.setQ_9(0);
					} else {
						form.setQ_9(cursor.getInt(cursor.getColumnIndex("Q_9")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_10")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_10"))
									.equalsIgnoreCase("null")) {
						form.setQ_10(0);
					} else {
						form.setQ_10(cursor.getInt(cursor
								.getColumnIndex("Q_10")));

					}
					// if (cursor.getString(cursor.getColumnIndex("Q_11")) ==
					// null
					// || cursor.getString(cursor.getColumnIndex("Q_11"))
					// .equalsIgnoreCase("null")) {
					// form.setQ_11(0);
					// } else {
					// form.setQ_11(cursor.getInt(cursor
					// .getColumnIndex("Q_11")));
					//
					// }
					if (cursor.getString(cursor.getColumnIndex("Q_12")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_12"))
									.equalsIgnoreCase("null")) {
						form.setQ_12(0);
					} else {
						form.setQ_12(cursor.getInt(cursor
								.getColumnIndex("Q_12")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_13")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_13"))
									.equalsIgnoreCase("null")) {
						form.setQ_13(0);
					} else {
						form.setQ_13(cursor.getInt(cursor
								.getColumnIndex("Q_13")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_14")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_14"))
									.equalsIgnoreCase("null")) {
						form.setQ_14(0);
					} else {
						form.setQ_14(cursor.getInt(cursor
								.getColumnIndex("Q_14")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_15")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_15"))
									.equalsIgnoreCase("null")) {
						form.setQ_15(0);
					} else {
						form.setQ_15(cursor.getInt(cursor
								.getColumnIndex("Q_15")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_16")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_16"))
									.equalsIgnoreCase("null")) {
						form.setQ_16(0);
					} else {
						form.setQ_16(cursor.getInt(cursor
								.getColumnIndex("Q_16")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_17")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_17"))
									.equalsIgnoreCase("null")) {
						form.setQ_17(0);
					} else {
						form.setQ_17(cursor.getInt(cursor
								.getColumnIndex("Q_17")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_18")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_18"))
									.equalsIgnoreCase("null")) {
						form.setQ_18(0);
					} else {
						form.setQ_18(cursor.getInt(cursor
								.getColumnIndex("Q_18")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_19")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_19"))
									.equalsIgnoreCase("null")) {
						form.setQ_19(0);
					} else {
						form.setQ_19(cursor.getInt(cursor
								.getColumnIndex("Q_19")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_20")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_20"))
									.equalsIgnoreCase("null")) {
						form.setQ_20(0);
					} else {
						form.setQ_20(cursor.getInt(cursor
								.getColumnIndex("Q_20")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_21")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_21"))
									.equalsIgnoreCase("null")) {
						form.setQ_21(0);
					} else {
						form.setQ_21(cursor.getInt(cursor
								.getColumnIndex("Q_21")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_22")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_22"))
									.equalsIgnoreCase("null")) {
						form.setQ_22(0);
					} else {
						form.setQ_22(cursor.getInt(cursor
								.getColumnIndex("Q_22")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_23")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_23"))
									.equalsIgnoreCase("null")) {
						form.setQ_23(0);
					} else {
						form.setQ_23(cursor.getInt(cursor
								.getColumnIndex("Q_23")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_24")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_24"))
									.equalsIgnoreCase("null")) {
						form.setQ_24(0);
					} else {
						form.setQ_24(cursor.getInt(cursor
								.getColumnIndex("Q_24")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_25")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_25"))
									.equalsIgnoreCase("null")) {
						form.setQ_25(0);
					} else {
						form.setQ_25(cursor.getInt(cursor
								.getColumnIndex("Q_25")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_26")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_26"))
									.equalsIgnoreCase("null")) {
						form.setQ_26(0);
					} else {
						form.setQ_26(cursor.getInt(cursor
								.getColumnIndex("Q_26")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_27")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_27"))
									.equalsIgnoreCase("null")) {
						form.setQ_27(0);
					} else {
						form.setQ_27(cursor.getInt(cursor
								.getColumnIndex("Q_27")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_28")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_28"))
									.equalsIgnoreCase("null")) {
						form.setQ_28(0);
					} else {
						form.setQ_28(cursor.getInt(cursor
								.getColumnIndex("Q_28")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_29")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_29"))
									.equalsIgnoreCase("null")) {
						form.setQ_29(0);
					} else {
						form.setQ_29(cursor.getInt(cursor
								.getColumnIndex("Q_29")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_30")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_30"))
									.equalsIgnoreCase("null")) {
						form.setQ_30(0);
					} else {
						form.setQ_30(cursor.getInt(cursor
								.getColumnIndex("Q_30")));

					}
					// if (cursor.getString(cursor.getColumnIndex("Q_31")) ==
					// null
					// || cursor.getString(cursor.getColumnIndex("Q_31"))
					// .equalsIgnoreCase("null")) {
					// form.setQ_31(0);
					// } else {
					// form.setQ_31(cursor.getInt(cursor
					// .getColumnIndex("Q_31")));
					//
					// }
					if (cursor.getString(cursor.getColumnIndex("Q_32")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_32"))
									.equalsIgnoreCase("null")) {
						form.setQ_32(0);
					} else {
						form.setQ_32(cursor.getInt(cursor
								.getColumnIndex("Q_32")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_33")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_33"))
									.equalsIgnoreCase("null")) {
						form.setQ_33(0);
					} else {
						form.setQ_33(cursor.getInt(cursor
								.getColumnIndex("Q_33")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_34")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_34"))
									.equalsIgnoreCase("null")) {
						form.setQ_34(0);
					} else {
						form.setQ_34(cursor.getInt(cursor
								.getColumnIndex("Q_34")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_35")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_35"))
									.equalsIgnoreCase("null")) {
						form.setQ_35(0);
					} else {
						form.setQ_35(cursor.getInt(cursor
								.getColumnIndex("Q_35")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_36")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_36"))
									.equalsIgnoreCase("null")) {
						form.setQ_36(0);
					} else {
						form.setQ_36(cursor.getInt(cursor
								.getColumnIndex("Q_36")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_37")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_37"))
									.equalsIgnoreCase("null")) {
						form.setQ_37(0);
					} else {
						form.setQ_37(cursor.getInt(cursor
								.getColumnIndex("Q_37")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_38")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_38"))
									.equalsIgnoreCase("null")) {
						form.setQ_38(0);
					} else {
						form.setQ_38(cursor.getInt(cursor
								.getColumnIndex("Q_38")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_38a")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_38a"))
									.equalsIgnoreCase("null")) {
						form.setQ_38a(0);
					} else {
						form.setQ_38a(cursor.getInt(cursor
								.getColumnIndex("Q_38a")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_39")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_39"))
									.equalsIgnoreCase("null")) {
						form.setQ_39(0);
					} else {
						form.setQ_39(cursor.getInt(cursor
								.getColumnIndex("Q_39")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_40")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_40"))
									.equalsIgnoreCase("null")) {
						form.setQ_40(0);
					} else {
						form.setQ_40(cursor.getInt(cursor
								.getColumnIndex("Q_40")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_41")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_41"))
									.equalsIgnoreCase("null")) {
						form.setQ_41(0);
					} else {
						form.setQ_41(cursor.getInt(cursor
								.getColumnIndex("Q_41")));

					}
					// if (cursor.getString(cursor.getColumnIndex("Q_42")) ==
					// null
					// || cursor.getString(cursor.getColumnIndex("Q_42"))
					// .equalsIgnoreCase("null")) {
					// form.setQ_42(0);
					// } else {
					// form.setQ_42(cursor.getInt(cursor
					// .getColumnIndex("Q_42")));
					//
					// }
					if (cursor.getString(cursor.getColumnIndex("Q_43")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_43"))
									.equalsIgnoreCase("null")) {
						form.setQ_43(0);
					} else {
						form.setQ_43(cursor.getInt(cursor
								.getColumnIndex("Q_43")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_44")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_44"))
									.equalsIgnoreCase("null")) {
						form.setQ_44("");
					} else {
						form.setQ_44(cursor.getString(cursor
								.getColumnIndex("Q_44")));

					}

					if (cursor.getString(cursor.getColumnIndex("Q_45")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_45"))
									.equalsIgnoreCase("null")) {
						form.setQ_45(0);
					} else {
						form.setQ_45(cursor.getInt(cursor
								.getColumnIndex("Q_45")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_46")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_46"))
									.equalsIgnoreCase("null")) {
						form.setQ_46(0);
					} else {
						form.setQ_46(cursor.getInt(cursor
								.getColumnIndex("Q_46")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_47")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_47"))
									.equalsIgnoreCase("null")) {
						form.setQ_47(0);
					} else {
						form.setQ_47(cursor.getInt(cursor
								.getColumnIndex("Q_47")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_48")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_48"))
									.equalsIgnoreCase("null")) {
						form.setQ_48("");
					} else {
						form.setQ_48(cursor.getString(cursor
								.getColumnIndex("Q_48")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_49")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_49"))
									.equalsIgnoreCase("null")) {
						form.setQ_49(0);
					} else {
						form.setQ_49(cursor.getInt(cursor
								.getColumnIndex("Q_49")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_50")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_50"))
									.equalsIgnoreCase("null")) {
						form.setQ_50(0);
					} else {
						form.setQ_50(cursor.getInt(cursor
								.getColumnIndex("Q_50")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_52")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_52"))
									.equalsIgnoreCase("null")) {
						form.setQ_52(0);
					} else {
						form.setQ_52(cursor.getInt(cursor
								.getColumnIndex("Q_52")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_53")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_53"))
									.equalsIgnoreCase("null")) {
						form.setQ_53(0);
					} else {
						form.setQ_53(cursor.getInt(cursor
								.getColumnIndex("Q_53")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_54")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_54"))
									.equalsIgnoreCase("null")) {
						form.setQ_54(0);
					} else {
						form.setQ_54(cursor.getInt(cursor
								.getColumnIndex("Q_54")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_55")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_55"))
									.equalsIgnoreCase("null")) {
						form.setQ_55(0);
					} else {
						form.setQ_55(cursor.getInt(cursor
								.getColumnIndex("Q_55")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_56")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_56"))
									.equalsIgnoreCase("null")) {
						form.setQ_56(0);
					} else {
						form.setQ_56(cursor.getInt(cursor
								.getColumnIndex("Q_56")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_57")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_57"))
									.equalsIgnoreCase("null")) {
						form.setQ_57(0);
					} else {
						form.setQ_57(cursor.getInt(cursor
								.getColumnIndex("Q_57")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_58")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_58"))
									.equalsIgnoreCase("null")) {
						form.setQ_58(0);
					} else {
						form.setQ_58(cursor.getInt(cursor
								.getColumnIndex("Q_58")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_59")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_59"))
									.equalsIgnoreCase("null")) {
						form.setQ_59(0);
					} else {
						form.setQ_59(cursor.getInt(cursor
								.getColumnIndex("Q_59")));

					}
					if (cursor.getString(cursor.getColumnIndex("Q_60")) == null
							|| cursor.getString(cursor.getColumnIndex("Q_60"))
									.equalsIgnoreCase("null")) {
						form.setQ_60(0);
					} else {
						form.setQ_60(cursor.getInt(cursor
								.getColumnIndex("Q_60")));

					}
					if (cursor.getString(cursor.getColumnIndex("CreatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedBy"))
									.equalsIgnoreCase("null")) {
						form.setCreatedBy(0);
					} else {
						form.setCreatedBy(cursor.getInt(cursor
								.getColumnIndex("CreatedBy")));

					}
					if (cursor.getString(cursor.getColumnIndex("UpdatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UpdatedBy"))
									.equalsIgnoreCase("null")) {
						form.setUpdatedBy(0);
					} else {
						form.setUpdatedBy(cursor.getInt(cursor
								.getColumnIndex("UpdatedBy")));

					}

					if (cursor.getString(cursor.getColumnIndex("IsDeleted")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsDeleted"))
									.equalsIgnoreCase("null")) {
						form.setIsDeleted(0);
					} else {
						form.setIsDeleted(cursor.getInt(cursor
								.getColumnIndex("IsDeleted")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsEdited")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsEdited"))
									.equalsIgnoreCase("null")) {
						form.setIsEdited(0);
					} else {
						form.setIsEdited(cursor.getInt(cursor
								.getColumnIndex("IsEdited")));

					}
					if (cursor.getString(cursor.getColumnIndex("IsEdited")) == null
							|| cursor.getString(
									cursor.getColumnIndex("IsEdited"))
									.equalsIgnoreCase("null")) {
						form.setIsEdited(0);
					} else {
						form.setIsEdited(cursor.getInt(cursor
								.getColumnIndex("IsEdited")));

					}
					if (cursor.getString(cursor.getColumnIndex("AshaID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("AshaID"))
									.equalsIgnoreCase("null")) {
						form.setAshaID(0);
					} else {
						form.setAshaID(cursor.getInt(cursor
								.getColumnIndex("AshaID")));

					}
					if (cursor.getString(cursor.getColumnIndex("ANMID")) == null
							|| cursor.getString(cursor.getColumnIndex("ANMID"))
									.equalsIgnoreCase("null")) {
						form.setANMID(0);
					} else {
						form.setANMID(cursor.getInt(cursor
								.getColumnIndex("ANMID")));

					}

					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in MstANMname :: " + exception.getMessage());
		}
		return data;

	}

	// /////// show asha data/////

	public ArrayList<MstASHA> getashaListForAnm(String sANMcode,
			int iLangugaeFlag) {
		ArrayList<MstASHA> Child = null;
		String sql = "";

		sql = "select * from MstASHA where ANMCode='" + sANMcode
				+ "' and LanguageID=" + iLangugaeFlag + "";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<MstASHA>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstASHA form = new MstASHA();
					form.setASHAID(cursor.getInt(cursor
							.getColumnIndex("ASHAID")));

					if (cursor.getString(cursor.getColumnIndex("ANMCode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ANMCode"))
									.equalsIgnoreCase("null")) {
						form.setANMCode("");
					} else {
						form.setANMCode(cursor.getString(cursor
								.getColumnIndex("ANMCode")));
					}
					if (cursor.getString(cursor.getColumnIndex("ASHACode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ASHACode"))
									.equalsIgnoreCase("null")) {
						form.setASHACode("");
					} else {
						form.setASHACode(cursor.getString(cursor
								.getColumnIndex("ASHACode")));
					}

					if (cursor.getString(cursor.getColumnIndex("ASHAName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ASHAName"))
									.equalsIgnoreCase("null")) {
						form.setASHAName("");
					} else {
						form.setASHAName(cursor.getString(cursor
								.getColumnIndex("ASHAName")));
					}

					form.setLanguageID(cursor.getInt(cursor
							.getColumnIndex("LanguageID")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setCHS_ID(cursor.getInt(cursor
							.getColumnIndex("CHS_ID")));

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	// /////// show user data/////

	public ArrayList<TblMstuser> tblmstUser(String sUserName, String sPassword) {
		ArrayList<TblMstuser> Child = null;
		String sql = "";

		sql = "select * from MstUser where UserName='" + sUserName
				+ "' and Password='" + sPassword + "'";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<TblMstuser>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					TblMstuser form = new TblMstuser();
					form.setUserID(cursor.getInt(cursor
							.getColumnIndex("UserID")));

					if (cursor.getString(cursor.getColumnIndex("UserName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("UserName"))
									.equalsIgnoreCase("null")) {
						form.setUserNameL("");
					} else {
						form.setUserNameL(cursor.getString(cursor
								.getColumnIndex("UserName")));
					}
					if (cursor.getString(cursor.getColumnIndex("Password")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Password"))
									.equalsIgnoreCase("null")) {
						form.setPassword("");
					} else {
						form.setPassword(cursor.getString(cursor
								.getColumnIndex("Password")));
					}

					form.setRoleID(cursor.getInt(cursor
							.getColumnIndex("RoleID")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	// add jitendra
	public ArrayList<MstState> getstate(int langauageid) {
		ArrayList<MstState> state = null;
		String sql = "";

		sql = "select * from MstState where LanguageID='" + langauageid + "' ";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				state = new ArrayList<MstState>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstState form = new MstState();

					if (cursor.getString(cursor.getColumnIndex("StateCode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("StateCode"))
									.equalsIgnoreCase("null")) {
						form.setStateCode("");
					} else {
						form.setStateCode(cursor.getString(cursor
								.getColumnIndex("StateCode")));
					}
					if (cursor.getString(cursor.getColumnIndex("StateName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("StateName"))
									.equalsIgnoreCase("null")) {
						form.setStateName("");
					} else {
						form.setStateName(cursor.getString(cursor
								.getColumnIndex("StateName")));
					}

					state.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return state;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in state :: " + e.getMessage());
		}
		return state;

	}

	// ////// getting AnmCode from MSTANM////////

	public ArrayList<MstANM> getMstANM(int iLangid) {
		ArrayList<MstANM> Child = null;
		String sql = "";

		sql = "select * from MstANM where languageID=" + iLangid + "";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<MstANM>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstANM form = new MstANM();
					form.setANMID(cursor.getInt(cursor.getColumnIndex("ANMID")));

					if (cursor.getString(cursor.getColumnIndex("ANMCode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ANMCode"))
									.equalsIgnoreCase("null")) {
						form.setANMCode("");
					} else {
						form.setANMCode(cursor.getString(cursor
								.getColumnIndex("ANMCode")));
					}
					if (cursor.getString(cursor.getColumnIndex("ANMName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ANMName"))
									.equalsIgnoreCase("null")) {
						form.setANMName("");
					} else {
						form.setANMName(cursor.getString(cursor
								.getColumnIndex("ANMName")));
					}

					form.setLanguageID(cursor.getInt(cursor
							.getColumnIndex("LanguageID")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	// // get Asha Code and name

	public ArrayList<MstASHA> getashanameandCode(int iLangugaeFlag) {
		ArrayList<MstASHA> Child = null;
		String sql = "";

		sql = "select * from MstASHA where  LanguageID=" + iLangugaeFlag + "";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<MstASHA>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstASHA form = new MstASHA();
					form.setASHAID(cursor.getInt(cursor
							.getColumnIndex("ASHAID")));

					if (cursor.getString(cursor.getColumnIndex("ANMCode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ANMCode"))
									.equalsIgnoreCase("null")) {
						form.setANMCode("");
					} else {
						form.setANMCode(cursor.getString(cursor
								.getColumnIndex("ANMCode")));
					}
					if (cursor.getString(cursor.getColumnIndex("ASHACode")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ASHACode"))
									.equalsIgnoreCase("null")) {
						form.setASHACode("");
					} else {
						form.setASHACode(cursor.getString(cursor
								.getColumnIndex("ASHACode")));
					}

					if (cursor.getString(cursor.getColumnIndex("ASHAName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ASHAName"))
									.equalsIgnoreCase("null")) {
						form.setASHAName("");
					} else {
						form.setASHAName(cursor.getString(cursor
								.getColumnIndex("ASHAName")));
					}

					form.setLanguageID(cursor.getInt(cursor
							.getColumnIndex("LanguageID")));
					form.setIsDeleted(cursor.getInt(cursor
							.getColumnIndex("IsDeleted")));
					form.setCHS_ID(cursor.getInt(cursor
							.getColumnIndex("CHS_ID")));

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	public ArrayList<Child_Imunization_Object> gettblCHildImmunizationdata(
			int ashaid) {
		ArrayList<Child_Imunization_Object> Child = null;
		String sql = "";

		sql = "select tblPregnant_woman.PWName as womenname ,tblPregnant_woman.HusbandName as husbandname,Child_dob,ChildGUID,Gender,child_name from tblChild left join tblPregnant_woman on tblChild.pw_GUID=tblPregnant_woman.PWGUID left join  Tbl_HHSurvey on  tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID where Tbl_HHSurvey.ServiceProviderID="
				+ ashaid + "";
		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<Child_Imunization_Object>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Child_Imunization_Object form = new Child_Imunization_Object();

					if (cursor.getString(cursor.getColumnIndex("womenname")) == null
							|| cursor.getString(
									cursor.getColumnIndex("womenname"))
									.equalsIgnoreCase("null")) {
						form.setWomenname("");
					} else {
						form.setWomenname(cursor.getString(cursor
								.getColumnIndex("womenname")));
					}
					if (cursor.getString(cursor.getColumnIndex("husbandname")) == null
							|| cursor.getString(
									cursor.getColumnIndex("husbandname"))
									.equalsIgnoreCase("null")) {
						form.setHusbanname("");
					} else {
						form.setHusbanname(cursor.getString(cursor
								.getColumnIndex("husbandname")));
					}

					if (cursor.getString(cursor.getColumnIndex("Child_dob")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Child_dob"))
									.equalsIgnoreCase("null")) {
						form.setDob("");
					} else {
						form.setDob(cursor.getString(cursor
								.getColumnIndex("Child_dob")));
					}

					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));
					}
					form.setGender(cursor.getInt(cursor
							.getColumnIndex("Gender")));

					form.setChild_name(cursor.getString(cursor
							.getColumnIndex("child_name")));

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	public ArrayList<Child_Imunization_Object> gettblCHildImmunization() {
		ArrayList<Child_Imunization_Object> Child = null;
		String sql = "";

		sql = "select tbl_HHFamilyMember.FamilyMemberName, Spouse ,tblChild.ChildGUID from tblChild left join tbl_HHFamilyMember where tblchild.HHFamilyMemberGUID=tbl_HHFamilyMember.HHFamilyMemberGUID";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<Child_Imunization_Object>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Child_Imunization_Object form = new Child_Imunization_Object();

					if (cursor.getString(cursor
							.getColumnIndex("FamilyMemberName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("FamilyMemberName"))
									.equalsIgnoreCase("null")) {
						form.setFamilyMemberName("");
					} else {
						form.setFamilyMemberName(cursor.getString(cursor
								.getColumnIndex("FamilyMemberName")));
					}
					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Spouse")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Spouse"))
									.equalsIgnoreCase("null")) {
						form.setSpouse("");
					} else {
						form.setSpouse(cursor.getString(cursor
								.getColumnIndex("Spouse")));
					}

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	public ArrayList<Child_Imunization_Object> ShowCHildImmunizationdata(
			String sChildGUID) {
		ArrayList<Child_Imunization_Object> Child = null;
		String sql = "";

		sql = "Select  bcg,opv2,dpt1,hepb2,opv3,dpt2,hepb3,opv4,dpt3,hepb4,measeals,vitaminA,Pentavalent1,Pentavalent2,Pentavalent3,hepb1,DPTBooster,OPVBooster,MeaslesTwoDose,VitaminAtwo,DPTBoostertwo,ChildTT,opv1,IPV,  JEVaccine1,JEVaccine2,VitaminA3,VitaminA4,VitaminA5,VitaminA6,VitaminA7,VitaminA8,VitaminA9,TT2 from tblChild  where ChildGUID='"
				+ sChildGUID + "'";

		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<Child_Imunization_Object>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					Child_Imunization_Object form = new Child_Imunization_Object();

					if (cursor.getString(cursor.getColumnIndex("bcg")) == null
							|| cursor.getString(cursor.getColumnIndex("bcg"))
									.equalsIgnoreCase("null")) {
						form.setBcg("");
					} else {
						form.setBcg(cursor.getString(cursor
								.getColumnIndex("bcg")));
					}

					if (cursor.getString(cursor.getColumnIndex("opv2")) == null
							|| cursor.getString(cursor.getColumnIndex("opv2"))
									.equalsIgnoreCase("null")) {
						form.setOpv2("");
					} else {
						form.setOpv2(cursor.getString(cursor
								.getColumnIndex("opv2")));
					}

					if (cursor.getString(cursor.getColumnIndex("dpt1")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt1"))
									.equalsIgnoreCase("null")) {
						form.setDpt1("");
					} else {
						form.setDpt1(cursor.getString(cursor
								.getColumnIndex("dpt1")));
					}
					if (cursor.getString(cursor.getColumnIndex("hepb2")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb2"))
									.equalsIgnoreCase("null")) {
						form.setHepb2("");
					} else {
						form.setHepb2(cursor.getString(cursor
								.getColumnIndex("hepb2")));
					}
					if (cursor.getString(cursor.getColumnIndex("opv3")) == null
							|| cursor.getString(cursor.getColumnIndex("opv3"))
									.equalsIgnoreCase("null")) {
						form.setOpv3("");
					} else {
						form.setOpv3(cursor.getString(cursor
								.getColumnIndex("opv3")));
					}

					if (cursor.getString(cursor.getColumnIndex("dpt2")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt2"))
									.equalsIgnoreCase("null")) {
						form.setDpt2("");
					} else {
						form.setDpt2(cursor.getString(cursor
								.getColumnIndex("dpt2")));
					}

					if (cursor.getString(cursor.getColumnIndex("hepb3")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb3"))
									.equalsIgnoreCase("null")) {
						form.setHepb3("");
					} else {
						form.setHepb3(cursor.getString(cursor
								.getColumnIndex("hepb3")));
					}

					if (cursor.getString(cursor.getColumnIndex("opv4")) == null
							|| cursor.getString(cursor.getColumnIndex("opv4"))
									.equalsIgnoreCase("null")) {
						form.setOpv4("");
					} else {
						form.setOpv4(cursor.getString(cursor
								.getColumnIndex("opv4")));
					}

					if (cursor.getString(cursor.getColumnIndex("opv4")) == null
							|| cursor.getString(cursor.getColumnIndex("opv4"))
									.equalsIgnoreCase("null")) {
						form.setOpv4("");
					} else {
						form.setOpv4(cursor.getString(cursor
								.getColumnIndex("opv4")));
					}

					if (cursor.getString(cursor.getColumnIndex("dpt3")) == null
							|| cursor.getString(cursor.getColumnIndex("dpt3"))
									.equalsIgnoreCase("null")) {
						form.setDpt3("");
					} else {
						form.setDpt3(cursor.getString(cursor
								.getColumnIndex("dpt3")));
					}

					if (cursor.getString(cursor.getColumnIndex("hepb4")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb4"))
									.equalsIgnoreCase("null")) {
						form.setHepb4("");
					} else {
						form.setHepb4(cursor.getString(cursor
								.getColumnIndex("hepb4")));
					}

					if (cursor.getString(cursor.getColumnIndex("measeals")) == null
							|| cursor.getString(
									cursor.getColumnIndex("measeals"))
									.equalsIgnoreCase("null")) {
						form.setMeaseals("");
					} else {
						form.setMeaseals(cursor.getString(cursor
								.getColumnIndex("measeals")));
					}

					if (cursor.getString(cursor.getColumnIndex("vitaminA")) == null
							|| cursor.getString(
									cursor.getColumnIndex("vitaminA"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA("");
					} else {
						form.setVitaminA(cursor.getString(cursor
								.getColumnIndex("vitaminA")));
					}
					if (cursor.getString(cursor.getColumnIndex("Pentavalent1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent1"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent1("");
					} else {
						form.setPentavalent1(cursor.getString(cursor
								.getColumnIndex("Pentavalent1")));
					}

					if (cursor.getString(cursor.getColumnIndex("Pentavalent2")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent2"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent2("");
					} else {
						form.setPentavalent2(cursor.getString(cursor
								.getColumnIndex("Pentavalent2")));
					}

					if (cursor.getString(cursor.getColumnIndex("Pentavalent3")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Pentavalent3"))
									.equalsIgnoreCase("null")) {
						form.setPentavalent3("");
					} else {
						form.setPentavalent3(cursor.getString(cursor
								.getColumnIndex("Pentavalent3")));
					}
					if (cursor.getString(cursor.getColumnIndex("IPV")) == null
							|| cursor.getString(cursor.getColumnIndex("IPV"))
									.equalsIgnoreCase("null")) {
						form.setIPV("");
					} else {
						form.setIPV(cursor.getString(cursor
								.getColumnIndex("IPV")));
					}

					if (cursor.getString(cursor.getColumnIndex("opv1")) == null
							|| cursor.getString(cursor.getColumnIndex("opv1"))
									.equalsIgnoreCase("null")) {
						form.setOpv1("");
					} else {
						form.setOpv1(cursor.getString(cursor
								.getColumnIndex("opv1")));
					}

					if (cursor.getString(cursor.getColumnIndex("hepb1")) == null
							|| cursor.getString(cursor.getColumnIndex("hepb1"))
									.equalsIgnoreCase("null")) {
						form.setHepb1("");
					} else {
						form.setHepb1(cursor.getString(cursor
								.getColumnIndex("hepb1")));
					}
					if (cursor.getString(cursor.getColumnIndex("DPTBooster")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DPTBooster"))
									.equalsIgnoreCase("null")) {
						form.setDPTBooster("");
					} else {
						form.setDPTBooster(cursor.getString(cursor
								.getColumnIndex("DPTBooster")));
					}
					if (cursor.getString(cursor.getColumnIndex("OPVBooster")) == null
							|| cursor.getString(
									cursor.getColumnIndex("OPVBooster"))
									.equalsIgnoreCase("null")) {
						form.setOPVBooster("");
					} else {
						form.setOPVBooster(cursor.getString(cursor
								.getColumnIndex("OPVBooster")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("MeaslesTwoDose")) == null
							|| cursor.getString(
									cursor.getColumnIndex("MeaslesTwoDose"))
									.equalsIgnoreCase("null")) {
						form.setMeaslesTwoDose("");
					} else {
						form.setMeaslesTwoDose(cursor.getString(cursor
								.getColumnIndex("MeaslesTwoDose")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminAtwo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminAtwo"))
									.equalsIgnoreCase("null")) {
						form.setVitaminAtwo("");
					} else {
						form.setVitaminAtwo(cursor.getString(cursor
								.getColumnIndex("VitaminAtwo")));
					}
					if (cursor
							.getString(cursor.getColumnIndex("DPTBoostertwo")) == null
							|| cursor.getString(
									cursor.getColumnIndex("DPTBoostertwo"))
									.equalsIgnoreCase("null")) {
						form.setDPTBoostertwo("");
					} else {
						form.setDPTBoostertwo(cursor.getString(cursor
								.getColumnIndex("DPTBoostertwo")));
					}
					if (cursor.getString(cursor.getColumnIndex("ChildTT")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildTT"))
									.equalsIgnoreCase("null")) {
						form.setChildTT("");
					} else {
						form.setChildTT(cursor.getString(cursor
								.getColumnIndex("ChildTT")));
					}
					if (cursor.getString(cursor.getColumnIndex("JEVaccine1")) == null
							|| cursor.getString(
									cursor.getColumnIndex("JEVaccine1"))
									.equalsIgnoreCase("null")) {
						form.setJEVaccine1("");
					} else {
						form.setJEVaccine1(cursor.getString(cursor
								.getColumnIndex("JEVaccine1")));
					}
					if (cursor.getString(cursor.getColumnIndex("JEVaccine2")) == null
							|| cursor.getString(
									cursor.getColumnIndex("JEVaccine2"))
									.equalsIgnoreCase("null")) {
						form.setJEVaccine2("");
					} else {
						form.setJEVaccine2(cursor.getString(cursor
								.getColumnIndex("JEVaccine2")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA3")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA3"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA3("");
					} else {
						form.setVitaminA3(cursor.getString(cursor
								.getColumnIndex("VitaminA3")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA4")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA4"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA4("");
					} else {
						form.setVitaminA4(cursor.getString(cursor
								.getColumnIndex("VitaminA4")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA5")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA5"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA5("");
					} else {
						form.setVitaminA5(cursor.getString(cursor
								.getColumnIndex("VitaminA5")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA6")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA6"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA6("");
					} else {
						form.setVitaminA6(cursor.getString(cursor
								.getColumnIndex("VitaminA6")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA7")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA7"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA7("");
					} else {
						form.setVitaminA7(cursor.getString(cursor
								.getColumnIndex("VitaminA7")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA8")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA8"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA8("");
					} else {
						form.setVitaminA8(cursor.getString(cursor
								.getColumnIndex("VitaminA8")));
					}
					if (cursor.getString(cursor.getColumnIndex("VitaminA9")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VitaminA9"))
									.equalsIgnoreCase("null")) {
						form.setVitaminA9("");
					} else {
						form.setVitaminA9(cursor.getString(cursor
								.getColumnIndex("VitaminA9")));
					}
					if (cursor.getString(cursor.getColumnIndex("TT2")) == null
							|| cursor.getString(cursor.getColumnIndex("TT2"))
									.equalsIgnoreCase("null")) {
						form.setTT2("");
					} else {
						form.setTT2(cursor.getString(cursor
								.getColumnIndex("TT2")));
					}

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	// Add Herojit
	public ArrayList<tbl_Incentive> gettIncentives(int ASHA_ID, int flag) {
		ArrayList<tbl_Incentive> incentives = null;
		String sql = "";
		sql = "select * from tbl_Incentive";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				incentives = new ArrayList<tbl_Incentive>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_Incentive form = new tbl_Incentive();

					if (cursor.getString(cursor.getColumnIndex("IUID")) == null
							|| cursor.getString(cursor.getColumnIndex("IUID"))
									.equalsIgnoreCase("null")) {
						form.setIUID(0);
					} else {
						form.setIUID(cursor.getInt(cursor
								.getColumnIndex("IUID")));
					}
					if (cursor.getString(cursor.getColumnIndex("ASHA_ID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ASHA_ID"))
									.equalsIgnoreCase("null")) {
						form.setASHA_ID(0);
					} else {
						form.setASHA_ID(cursor.getInt(cursor
								.getColumnIndex("ASHA_ID")));
					}
					if (cursor.getString(cursor.getColumnIndex("ShortName")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ShortName"))
									.equalsIgnoreCase("null")) {
						form.setShortName("");
					} else {
						form.setShortName(cursor.getString(cursor
								.getColumnIndex("ShortName")));
					}
					if (cursor.getString(cursor.getColumnIndex("Description")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Description"))
									.equalsIgnoreCase("null")) {
						form.setDescription("");
					} else {
						form.setDescription(cursor.getString(cursor
								.getColumnIndex("Description")));
					}
					if (cursor.getString(cursor.getColumnIndex("Amount")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Amount"))
									.equalsIgnoreCase("null")) {
						form.setAmount("");
					} else {
						form.setAmount(cursor.getString(cursor
								.getColumnIndex("Amount")));
					}
					if (cursor.getString(cursor.getColumnIndex("Documents")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Documents"))
									.equalsIgnoreCase("null")) {
						form.setDocuments("");
					} else {
						form.setDocuments(cursor.getString(cursor
								.getColumnIndex("Documents")));
					}
					if (cursor.getString(cursor
							.getColumnIndex("Effect_Fromdate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Effect_Fromdate"))
									.equalsIgnoreCase("null")) {
						form.setEffect_Fromdate("");
					} else {
						form.setEffect_Fromdate(cursor.getString(cursor
								.getColumnIndex("Effect_Fromdate")));
					}
					if (cursor
							.getString(cursor.getColumnIndex("Effect_todate")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Effect_todate"))
									.equalsIgnoreCase("null")) {
						form.setEffect_todate("");
					} else {
						form.setEffect_todate(cursor.getString(cursor
								.getColumnIndex("Effect_todate")));
					}
					if (cursor.getString(cursor.getColumnIndex("Status")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Status"))
									.equalsIgnoreCase("null")) {
						form.setStatus(0);
					} else {
						form.setStatus(cursor.getInt(cursor
								.getColumnIndex("Status")));
					}
					if (cursor.getString(cursor.getColumnIndex("AreaType")) == null
							|| cursor.getString(
									cursor.getColumnIndex("AreaType"))
									.equalsIgnoreCase("null")) {
						form.setAreaType("");
					} else {
						form.setAreaType(cursor.getString(cursor
								.getColumnIndex("AreaType")));
					}
					if (cursor.getString(cursor.getColumnIndex("Query")) == null
							|| cursor.getString(cursor.getColumnIndex("Query"))
									.equalsIgnoreCase("null")) {
						form.setQuery("");
					} else {
						form.setQuery(cursor.getString(cursor
								.getColumnIndex("Query")));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedBy"))
									.equalsIgnoreCase("null")) {
						form.setCreatedBy("");
					} else {
						form.setCreatedBy(cursor.getString(cursor
								.getColumnIndex("CreatedBy")));
					}
					if (cursor.getString(cursor.getColumnIndex("Item_id")) == null
							|| cursor.getString(
									cursor.getColumnIndex("Item_id"))
									.equalsIgnoreCase("null")) {
						form.setItem_id(0);
					} else {
						form.setItem_id(cursor.getInt(cursor
								.getColumnIndex("Item_id")));
					}

					incentives.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return incentives;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return incentives;
	}

	public ArrayList<MstVHND_PerformanceIndicator> getVHND_Perform(int ASHA_ID,
			int flag) {
		ArrayList<MstVHND_PerformanceIndicator> VHND = null;
		String sql = "";
		sql = "select * from MstVHND_PerformanceIndicator";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				VHND = new ArrayList<MstVHND_PerformanceIndicator>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstVHND_PerformanceIndicator form = new MstVHND_PerformanceIndicator();

					if (cursor.getString(cursor.getColumnIndex("ID")) == null
							|| cursor.getString(cursor.getColumnIndex("ID"))
									.equalsIgnoreCase("null")) {
						form.setID(0);
					} else {
						form.setID(cursor.getInt(cursor.getColumnIndex("ID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Item")) == null
							|| cursor.getString(cursor.getColumnIndex("Item"))
									.equalsIgnoreCase("null")) {
						form.setItem("");
					} else {
						form.setItem(cursor.getString(cursor
								.getColumnIndex("Item")));
					}
					if (cursor.getString(cursor.getColumnIndex("LanguageId")) == null
							|| cursor.getString(
									cursor.getColumnIndex("LanguageId"))
									.equalsIgnoreCase("null")) {
						form.setLanguageId(0);
					} else {
						form.setLanguageId(cursor.getInt(cursor
								.getColumnIndex("LanguageId")));
					}
					if (cursor.getString(cursor.getColumnIndex("Status")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Status"))
									.equalsIgnoreCase("null")) {
						form.setStatus(0);
					} else {
						form.setStatus(cursor.getInt(cursor
								.getColumnIndex("Status")));
					}
					VHND.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return VHND;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return VHND;
	}

	public ArrayList<MstVHND_DueListItems> getVHND_Duelist(int LanguageID,
			int flag) {
		ArrayList<MstVHND_DueListItems> VHND = null;
		String sql = "";
		sql = "select * from MstVHND_DueListItems where LanguageId='"
				+ LanguageID + "'";
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				VHND = new ArrayList<MstVHND_DueListItems>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					MstVHND_DueListItems form = new MstVHND_DueListItems();

					if (cursor.getString(cursor.getColumnIndex("ID")) == null
							|| cursor.getString(cursor.getColumnIndex("ID"))
									.equalsIgnoreCase("null")) {
						form.setID(0);
					} else {
						form.setID(cursor.getInt(cursor.getColumnIndex("ID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Items")) == null
							|| cursor.getString(cursor.getColumnIndex("Items"))
									.equalsIgnoreCase("null")) {
						form.setItems("");
					} else {
						form.setItems(cursor.getString(cursor
								.getColumnIndex("Items")));
					}
					if (cursor.getString(cursor.getColumnIndex("LanguageId")) == null
							|| cursor.getString(
									cursor.getColumnIndex("LanguageId"))
									.equalsIgnoreCase("null")) {
						form.setLanguageId(0);
					} else {
						form.setLanguageId(cursor.getInt(cursor
								.getColumnIndex("LanguageId")));
					}
					if (cursor.getString(cursor.getColumnIndex("Status")) == null
							|| cursor
									.getString(cursor.getColumnIndex("Status"))
									.equalsIgnoreCase("null")) {
						form.setStatus(0);
					} else {
						form.setStatus(cursor.getInt(cursor
								.getColumnIndex("Status")));
					}
					VHND.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return VHND;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return VHND;
	}

	public ArrayList<tbl_VHNDPerformance> getVHND_Performance(String ASHA_ID,
			String VHND_ID, int flag) {
		ArrayList<tbl_VHNDPerformance> VHND = null;
		String sql = "";
		if (flag == 0) {
			sql = "select * from tbl_VHNDPerformance where AshaID='" + ASHA_ID
					+ "'";
		} else if (flag == 1) {
			sql = "select * from tbl_VHNDPerformance where AshaID='" + ASHA_ID
					+ "' and VHND_ID='" + VHND_ID + "'";
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				VHND = new ArrayList<tbl_VHNDPerformance>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_VHNDPerformance form = new tbl_VHNDPerformance();

					if (cursor.getString(cursor.getColumnIndex("PID")) == null
							|| cursor.getString(cursor.getColumnIndex("PID"))
									.equalsIgnoreCase("null")) {
						form.setPID(0);
					} else {
						form.setPID(cursor.getInt(cursor.getColumnIndex("PID")));
					}
					if (cursor.getString(cursor.getColumnIndex("VHND_ID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VHND_ID"))
									.equalsIgnoreCase("null")) {
						form.setVHND_ID("");
					} else {
						form.setVHND_ID(cursor.getString(cursor
								.getColumnIndex("VHND_ID")));
					}
					if (cursor.getString(cursor.getColumnIndex("SS_ID")) == null
							|| cursor.getString(cursor.getColumnIndex("SS_ID"))
									.equalsIgnoreCase("null")) {
						form.setSS_ID(0);
					} else {
						form.setSS_ID(cursor.getInt(cursor
								.getColumnIndex("SS_ID")));
					}
					if (cursor.getString(cursor.getColumnIndex("VillageId")) == null
							|| cursor.getString(
									cursor.getColumnIndex("VillageId"))
									.equalsIgnoreCase("null")) {
						form.setVillageId(0);
					} else {
						form.setVillageId(cursor.getInt(cursor
								.getColumnIndex("VillageId")));
					}
					if (cursor.getString(cursor.getColumnIndex("AshaID")) == null
							|| cursor
									.getString(cursor.getColumnIndex("AshaID"))
									.equalsIgnoreCase("null")) {
						form.setAshaID(0);
					} else {
						form.setAshaID(cursor.getInt(cursor
								.getColumnIndex("AshaID")));
					}
					if (cursor.getString(cursor.getColumnIndex("AWWID")) == null
							|| cursor.getString(cursor.getColumnIndex("AWWID"))
									.equalsIgnoreCase("null")) {
						form.setAWWID(0);
					} else {
						form.setAWWID(cursor.getInt(cursor
								.getColumnIndex("AWWID")));
					}
					if (cursor.getString(cursor.getColumnIndex("ANMID")) == null
							|| cursor.getString(cursor.getColumnIndex("ANMID"))
									.equalsIgnoreCase("null")) {
						form.setANMID(0);
					} else {
						form.setANMID(cursor.getInt(cursor
								.getColumnIndex("ANMID")));
					}
					if (cursor.getString(cursor.getColumnIndex("Date")) == null
							|| cursor.getString(cursor.getColumnIndex("Date"))
									.equalsIgnoreCase("null")) {
						form.setDate("");
					} else {
						form.setDate(cursor.getString(cursor
								.getColumnIndex("Date")));
					}
					if (cursor.getString(cursor.getColumnIndex("OPT_Value")) == null
							|| cursor.getString(
									cursor.getColumnIndex("OPT_Value"))
									.equalsIgnoreCase("null")) {
						form.setOPT_Value("");
					} else {
						form.setOPT_Value(cursor.getString(cursor
								.getColumnIndex("OPT_Value")));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedOn"))
									.equalsIgnoreCase("null")) {
						form.setCreatedOn("");
					} else {
						form.setCreatedOn(cursor.getString(cursor
								.getColumnIndex("CreatedOn")));
					}
					if (cursor.getString(cursor.getColumnIndex("CreatedBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("CreatedBy"))
									.equalsIgnoreCase("null")) {
						form.setCreatedBy("");
					} else {
						form.setCreatedBy(cursor.getString(cursor
								.getColumnIndex("CreatedBy")));
					}
					if (cursor.getString(cursor.getColumnIndex("ModifyOn")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ModifyOn"))
									.equalsIgnoreCase("null")) {
						form.setModifyOn("");
					} else {
						form.setModifyOn(cursor.getString(cursor
								.getColumnIndex("ModifyOn")));
					}
					if (cursor.getString(cursor.getColumnIndex("ModifyBy")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ModifyBy"))
									.equalsIgnoreCase("null")) {
						form.setModifyBy("");
					} else {
						form.setModifyBy(cursor.getString(cursor
								.getColumnIndex("ModifyBy")));
					}

					VHND.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return VHND;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return VHND;
	}

	public int VHND_Perform(String VHND_ID, int SS_ID, int VillageId,
			int AshaID, int AWWID, int ANMID, String Date, String Flag) {
		// TODO Auto-generated method stub
		int re = 0;
		String sql = "";
		if (Flag == "I") {
			sql = "Insert into tbl_VHNDPerformance(VHND_ID,SS_ID,VillageId,AshaID,AWWID,ANMID,Date,CreatedOn,ModifyOn,IsUploaded)values('"
					+ VHND_ID
					+ "','"
					+ SS_ID
					+ "','"
					+ VillageId
					+ "','"
					+ AshaID
					+ "','"
					+ AWWID
					+ "','"
					+ ANMID
					+ "','"
					+ Date
					+ "','"
					+ dateFormat.format(date)
					+ "','"
					+ dateFormat.format(date) + "',0)";
		} else if (Flag == "U") {
			sql = "Update tbl_VHNDPerformance set  VHND_ID='" + VHND_ID
					+ "',SS_ID='" + SS_ID + "',VillageId='" + VillageId
					+ "',AshaID='" + AshaID + "',AWWID='" + AWWID + "',ANMID='"
					+ ANMID + "',Date='" + Date + "',ModifyOn='"
					+ dateFormat.format(date) + "',IsUploade=0 where VHND_ID='"
					+ VHND_ID + "' and AshaID='" + AshaID + "'";
		}
		try {
			executeSql(sql);
			re = 1;
		} catch (Exception e) {
			// TODO: handle exception
			re = 0;
			Log.e("DataProvider", "Error in SavetblChild :: " + e.getMessage());
		}
		return re;
	}

	public int VHND_Perform(String VHND_ID, int AshaID, String OPT_Value,
			String Flag) {
		// TODO Auto-generated method stub
		int re = 0;
		String sql = "";
		if (Flag == "U") {
			sql = "Update tbl_VHNDPerformance set  OPT_Value='" + OPT_Value
					+ "',IsUploaded=0 where VHND_ID='" + VHND_ID
					+ "' and AshaID='" + AshaID + "'";
		}
		try {
			executeSql(sql);
			re = 1;
		} catch (Exception e) {
			// TODO: handle exception
			re = 0;
			Log.e("DataProvider", "Error in SavetblChild :: " + e.getMessage());
		}
		return re;
	}

	public ArrayList<tbl_pregnantwomen> getMemberName(String PWGUID, int flag) {
		ArrayList<tbl_pregnantwomen> data = null;
		String sql = "";
		if (flag == 0) {
			sql = PWGUID;
		}
		cursor = null;
		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				data = new ArrayList<tbl_pregnantwomen>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tbl_pregnantwomen form = new tbl_pregnantwomen();
					if (cursor.getString(cursor.getColumnIndex("PWName")) == null
							|| cursor
									.getString(cursor.getColumnIndex("PWName"))
									.equalsIgnoreCase("null")) {
						form.setPWName("");
					} else {
						form.setPWName(cursor.getString(cursor
								.getColumnIndex("PWName")));
					}
					data.add(form);
					cursor.moveToNext();

				}

				cursor.close();
			}
			return data;
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in TblSurveyUpload :: " + exception.getMessage());
		}
		return data;
	}

	public ArrayList<tblChild> getChildGUID(String sql1, int Flag) {
		ArrayList<tblChild> Child = null;
		String sql = "";
		if (Flag == 0) {
			sql = sql1;
		}
		cursor = null;

		try {
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(sql, null);
			if (cursor != null) {
				Child = new ArrayList<tblChild>();
				cursor.moveToFirst();
				while (cursor.isAfterLast() == false) {
					tblChild form = new tblChild();
					if (cursor.getString(cursor.getColumnIndex("ChildGUID")) == null
							|| cursor.getString(
									cursor.getColumnIndex("ChildGUID"))
									.equalsIgnoreCase("null")) {
						form.setChildGUID("");
					} else {
						form.setChildGUID(cursor.getString(cursor
								.getColumnIndex("ChildGUID")));
					}

					Child.add(form);
					cursor.moveToNext();
				}
				cursor.close();

			}
			return Child;
		} catch (Exception e) {
			Log.e("DataProvider", "Error in gettblChild :: " + e.getMessage());
		}
		return Child;

	}

	public int getMaxRecordnew(String Sql) {
		int iIntegerValue = 0;
		try {
			cursor = null;
			if (dbIntraHealth == null) {
				dbIntraHealth = dbHelper.getDatabase();
			}
			cursor = dbIntraHealth.rawQuery(Sql, null);
			if (cursor != null) {
				cursor.moveToFirst();

				while (cursor.isAfterLast() == false) {
					iIntegerValue = cursor.getInt(0);
					cursor.moveToNext();
				}
				cursor.close();
			}
		} catch (Exception exception) {
			Log.e("DataProvider",
					"Error in getMaxRecord :: " + exception.getMessage());
		}

		return iIntegerValue;
	}
}
