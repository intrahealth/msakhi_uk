package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.dataprovider.DataProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.widget.TextView;

public class ReportIndicatorListHH extends Activity {
	DataProvider dataProvider;

	TextView textview1, textview2, textview3, textview4, textview5, textview6,
			textview7, textview8, textview9, textview10, textview11;
	Global global;
	int ashaid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportindicatorlisthh);
		dataProvider = new DataProvider(this);

		global = (Global) getApplication();
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview7 = (TextView) findViewById(R.id.textview7);
		textview8 = (TextView) findViewById(R.id.textview8);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview10 = (TextView) findViewById(R.id.textview10);
		textview11 = (TextView) findViewById(R.id.textview11);

		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}
		showData();

	}

	public void showData() {

		String sql1 = "";
		sql1 = "select  count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember  on Tbl_HHSurvey.HHSurveyGUID=Tbl_HHFamilyMember.HHSurveyGUID where Tbl_HHSurvey.ServiceProviderID = "
				+ ashaid + "";
		int ivalue1 = 0;
		ivalue1 = dataProvider.getMaxRecord(sql1);
		textview1.setText(String.valueOf(ivalue1));

		String sql2 = "";
		sql2 = "select count(*) from Tbl_HHSurvey where ServiceProviderID = "
				+ ashaid + "";
		int ivalue2 = 0;
		ivalue2 = dataProvider.getMaxRecord(sql2);
		textview2.setText(String.valueOf(ivalue2));

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date();
		String currentDate = dateFormat.format(dt);

		String sql3 = "";
		sql3 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where member.Genderid=2 and Tbl_HHSurvey.ServiceProviderID  = "
				+ ashaid
				+ " and (((Date('now') ) - (DATE(member.DateOfBirth)) >=15 and (Date('now') ) - (DATE(member.DateOfBirth)) <=49) or (AprilAgeYear >=15 and AprilAgeYear<=49) )";
		int ivalue3 = 0;
		ivalue3 = dataProvider.getMaxRecord(sql3);
		textview3.setText(String.valueOf(ivalue3));

		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String newDate = date; // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		try {
			try {
				c.setTime(sdf.parse(newDate));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, -180); // number of days to add
		newDate = Validate.changeDateFormat(sdf.format(c.getTime()));

		String sql4 = "";
		sql4 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where (julianday('now')  - julianday(member.DateOfBirth))<180 and Tbl_HHSurvey.ServiceProviderID  = "
				+ ashaid + "";
		int ivalue4 = 0;
		ivalue4 = dataProvider.getMaxRecord(sql4);
		textview4.setText(String.valueOf(ivalue4));

		String afterdate = new SimpleDateFormat("dd-MM-yyyy")
				.format(new Date());
		String afternewDate = afterdate; // Start date
		SimpleDateFormat aftersdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c1 = Calendar.getInstance();
		try {
			try {
				c1.setTime(aftersdf.parse(afternewDate));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c1.add(Calendar.DATE, -365); // number of days to add
		afternewDate = Validate.changeDateFormat(aftersdf.format(c1.getTime()));

		String sql5 = "";
		sql5 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where (julianday('now')  - julianday(member.DateOfBirth)) between 180 and 365 and Tbl_HHSurvey.ServiceProviderID  = "
				+ ashaid + "";

		int ivalue5 = 0;
		ivalue5 = dataProvider.getMaxRecord(sql5);
		textview5.setText(String.valueOf(ivalue5));

		String sql6 = "";
		sql6 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 0 and 2 ) or (AprilAgeYear >=0 and AprilAgeYear<2)) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue6 = 0;
		ivalue6 = dataProvider.getMaxRecord(sql6);
		textview6.setText(String.valueOf(ivalue6));

		String sql7 = "";
		sql7 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 1 and 3 ) or (AprilAgeYear >=1 and AprilAgeYear<3)) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue7 = 0;
		ivalue7 = dataProvider.getMaxRecord(sql7);
		textview7.setText(String.valueOf(ivalue7));

		String sql8 = "";
		sql8 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 4 and 6 ) or (AprilAgeYear >=4 and AprilAgeYear<6)) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue8 = 0;
		ivalue8 = dataProvider.getMaxRecord(sql8);
		textview8.setText(String.valueOf(ivalue8));

		String sql9 = "";
		sql9 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 0 and 6 ) or (AprilAgeYear >=0 and AprilAgeYear<6)) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue9 = 0;
		ivalue9 = dataProvider.getMaxRecord(sql9);
		textview9.setText(String.valueOf(ivalue9));

		String sql10 = "";
		sql10 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 35 and 50 ) or (AprilAgeYear >=35 and AprilAgeYear<50)) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue10 = 0;
		ivalue10 = dataProvider.getMaxRecord(sql10);
		textview10.setText(String.valueOf(ivalue10));

		String sql11 = "";
		sql11 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) >=60 ) or (AprilAgeYear >=60 )) and Tbl_HHSurvey.ServiceProviderID ="
				+ ashaid + "";
		int ivalue11 = 0;
		ivalue11 = dataProvider.getMaxRecord(sql11);
		textview11.setText(String.valueOf(ivalue11));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if (global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(ReportIndicatorListHH.this,
					AshaDashboardListHH.class);
			finish();
			startActivity(i);
		} else {
			Intent i = new Intent(ReportIndicatorListHH.this,
					Report_Module.class);
			finish();
			startActivity(i);
		}
	}

}
