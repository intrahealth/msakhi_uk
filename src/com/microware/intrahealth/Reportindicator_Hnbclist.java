package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.dataprovider.DataProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class Reportindicator_Hnbclist extends Activity {

	TextView textView1, textView2, textView3, textView4, textView5, textView6;
	DataProvider dataprovider;
	RadioButton radio1Month, radio3month;
	Global global;
	int ashaid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_indicatore_hnbclist);
		dataprovider = new DataProvider(this);

		global = (Global) getApplication();
		textView1 = (TextView) findViewById(R.id.textview1);
		textView2 = (TextView) findViewById(R.id.textview2);
		textView3 = (TextView) findViewById(R.id.textview3);
		textView4 = (TextView) findViewById(R.id.textview4);
		textView5 = (TextView) findViewById(R.id.textview5);
		textView6 = (TextView) findViewById(R.id.textview6);
		radio1Month = (RadioButton) findViewById(R.id.Radio1);
		radio3month = (RadioButton) findViewById(R.id.Radio2);

		if(global.getsGlobalAshaCode() != null && global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		radio1Month.setChecked(true);
		showdatalastMonth();
	}

	public void clickRadio(View view) {
		switch (view.getId()) {
		case R.id.Radio1:
			radio3month.setChecked(false);
			showdatalastMonth();
			break;
		case R.id.Radio2:
			radio1Month.setChecked(false);
			ShowLastThreemOnthdate();
			break;

		default:
			break;
		}
	}

	public void showdatalastMonth() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar aCalendar = Calendar.getInstance();
		// add -1 month to current month
		aCalendar.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		aCalendar.set(Calendar.DATE, 1);

		Date firstDateOfPreviousMonth = aCalendar.getTime();
		String lastmonthStartDate = dateFormat.format(firstDateOfPreviousMonth);

		// set actual maximum date of previous month
		aCalendar.set(Calendar.DATE,
				aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		// read it
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		String lastMonthEndDate = dateFormat.format(lastDateOfPreviousMonth);

		String Sql1 = "";
		Sql1 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=7) and place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue1 = 0;
		iValue1 = dataprovider.getMaxRecord(Sql1);

		String Sql1query = "";
		Sql1query = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue1query = 0;
		iValue1query = dataprovider.getMaxRecord(Sql1query);
		textView1.setText(String.valueOf(iValue1) +'/'+ String.valueOf(iValue1query));

		String Sql2 = "";
		Sql2 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=6) and place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue2 = 0;
		iValue2 = dataprovider.getMaxRecord(Sql2);

		String Sql2query = "";
		Sql2query = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue2query = 0;
		iValue2query = dataprovider.getMaxRecord(Sql2query);
		textView2.setText(String.valueOf(iValue2) +'/'+ String.valueOf(iValue2query));

		String Sql3 = "";
		Sql3 = "select  count(*) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=2) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth = 1 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
		+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue3 = 0;
		iValue3 = dataprovider.getMaxRecord(Sql3);

		String Sql3query = "";
		Sql3query = "select  count(*) from tblChild where AshaID = "+ashaid+" and place_of_birth = 1 and (Date(Child_dob) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue3query = 0;
		iValue3query = dataprovider.getMaxRecord(Sql3query);
		textView3.setText(String.valueOf(iValue3) +'/'+ String.valueOf(iValue3query));

		String Sql4 = "";
		Sql4 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=3) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue4 = 0;
		iValue4 = dataprovider.getMaxRecord(Sql4);
		textView4.setText(String.valueOf(iValue4) +'/'+ String.valueOf(iValue3query));

		String Sql5 = "";
		Sql5 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=2) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue5 = 0;
		iValue5 = dataprovider.getMaxRecord(Sql5);
		textView5.setText(String.valueOf(iValue5) +'/'+ String.valueOf(iValue3query));

		String Sql6 = "";
		Sql6 = "select  count(*) from tblChild  where Wt_of_child < 2.5 and AshaID = "+ashaid+" and (Date(Child_dob) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue6 = 0;
		iValue6 = dataprovider.getMaxRecord(Sql6);

		String sql6query = "";
		sql6query = "select  count(*) from tblChild where Wt_of_child != 'null' and Wt_of_child != 0 and AshaID = "+ashaid+" and (Date(Child_dob) Between Date('"
				+lastmonthStartDate+"') and Date('"+lastMonthEndDate+"'))";
		int iValue6query = 0;
		iValue6query = dataprovider.getMaxRecord(sql6query);

		textView6.setText(String.valueOf(iValue6) +'/'+ String.valueOf(iValue6query));

	}

	public void ShowLastThreemOnthdate() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Date dt = new Date();

		Calendar aCalendar1 = Calendar.getInstance();
		// add -1 month to current month
		aCalendar1.add(Calendar.MONTH, -3);
		// set DATE to 1, so first date of previous month
		aCalendar1.set(Calendar.DATE, 1);

		Date firstDateOfPreviousMonth1 = aCalendar1.getTime();
		String currentDatefirstDateOfPreviousMonth1 = dateFormat
				.format(firstDateOfPreviousMonth1);

		Calendar aCalendar = Calendar.getInstance();
		// add -1 month to current month
		aCalendar.add(Calendar.MONTH, -1);
		// set DATE to 1, so first date of previous month
		aCalendar.set(Calendar.DATE, 1);

		// set actual maximum date of previous month
		aCalendar.set(Calendar.DATE,
				aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		// read it
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		String currentDatelastDateOfPreviousMonth = dateFormat
				.format(lastDateOfPreviousMonth);

		String Sql1 = "";
		Sql1 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=7) and place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue1 = 0;
		iValue1 = dataprovider.getMaxRecord(Sql1);

		String Sql1query = "";
		Sql1query = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue1query = 0;
		iValue1query = dataprovider.getMaxRecord(Sql1query);
		textView1.setText(String.valueOf(iValue1) +'/'+ String.valueOf(iValue1query));

		String Sql2 = "";
		Sql2 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=6) and place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue2 = 0;
		iValue2 = dataprovider.getMaxRecord(Sql2);

		String Sql2query = "";
		Sql2query = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue2query = 0;
		iValue2query = dataprovider.getMaxRecord(Sql2query);
		textView2.setText(String.valueOf(iValue2) +'/'+ String.valueOf(iValue2query));

		String Sql3 = "";
		Sql3 = "select  count(*) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=2) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth = 1 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
		+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue3 = 0;
		iValue3 = dataprovider.getMaxRecord(Sql3);

		String Sql3query = "";
		Sql3query = "select  count(*) from tblChild where AshaID = "+ashaid+" and place_of_birth = 1 and (Date(Child_dob) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue3query = 0;
		iValue3query = dataprovider.getMaxRecord(Sql3query);

		textView3.setText(String.valueOf(iValue3) +'/'+ String.valueOf(iValue3query));

		String Sql4 = "";
		Sql4 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=3) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth=1 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue4 = 0;
		iValue4 = dataprovider.getMaxRecord(Sql4);
		textView4.setText(String.valueOf(iValue4) +'/'+ String.valueOf(iValue3query));

		String Sql5 = "";
		Sql5 = "select  count(*) from tblPNChomevisit_ANS  inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID  where ((select count(VisitNo) from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID) >=2) and (date(Child_dob) < (select date(Child_dob,'+0 month','+7 day') from tblPNChomevisit_ANS inner join tblChild on tblPNChomevisit_ANS.ChildGUID=tblChild.ChildGUID)) and place_of_birth=2 and tblPNChomevisit_ANS.AshaID = "
		+ashaid+" and (Date(Q_0) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue5 = 0;
		iValue5 = dataprovider.getMaxRecord(Sql5);
		textView5.setText(String.valueOf(iValue5) +'/'+ String.valueOf(iValue3query));

		String Sql6 = "";
		Sql6 = "select  count(*) from tblChild  where Wt_of_child < 2.5 and AshaID = "+ashaid+" and (Date(Child_dob) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue6 = 0;
		iValue6 = dataprovider.getMaxRecord(Sql6);

		String sql6query = "";
		sql6query = "select  count(*) from tblChild where Wt_of_child != 'null' and Wt_of_child != 0 and AshaID = "+ashaid+" and (Date(Child_dob) Between Date('"
				+currentDatefirstDateOfPreviousMonth1+"') and Date('"+currentDatelastDateOfPreviousMonth+"'))";
		int iValue6query = 0;
		iValue6query = dataprovider.getMaxRecord(sql6query);

		textView6.setText(String.valueOf(iValue6) +'/'+ String.valueOf(iValue6query));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if(global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(Reportindicator_Hnbclist.this,
					AshaDashboardListHnbc.class);
			finish();
			startActivity(i);
		} else {
			Intent i = new Intent(Reportindicator_Hnbclist.this,
					Report_Module.class);
			finish();
			startActivity(i);
		}
	}

}
