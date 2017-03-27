package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.microware.intrahealth.R;
import com.microware.intrahealth.Report_Module;
import com.microware.intrahealth.dataprovider.DataProvider;

public class ReportIndicator_ashaList extends Activity {

	TextView textview1, textview2, textview3, textview4, textview5, textview6,
			textview7, textview8;
	DataProvider dataprovider;
	RadioButton radio1Month, radio3month;
	Global global;
	int ashaid = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reportindicator_asha_list);
		dataprovider = new DataProvider(this);

		global = (Global) getApplication();
		textview1 = (TextView) findViewById(R.id.textview1);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview5 = (TextView) findViewById(R.id.textview5);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview7 = (TextView) findViewById(R.id.textview7);
		textview8 = (TextView) findViewById(R.id.textview8);
		radio1Month = (RadioButton) findViewById(R.id.Radio1);
		radio3month = (RadioButton) findViewById(R.id.Radio2);

		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		radio1Month.setChecked(true);
		showdatafooLastMonth();

	}

	public void clickRadio(View view) {
		switch (view.getId()) {
		case R.id.Radio1:
			radio3month.setChecked(false);
			showdatafooLastMonth();
			break;
		case R.id.Radio2:
			radio1Month.setChecked(false);
			showlastthreeMonthData();
			break;

		default:
			break;
		}
	}

	public void showdatafooLastMonth() {

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

		String sql1 = "";
		sql1 = "select  count(*) from tblPregnant_woman where IsPregnant=1 and AshaID = "
				+ ashaid
				+ " and (Date(PWRegistrationDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "'))";
		int ivalue1 = 0;
		ivalue1 = dataprovider.getMaxRecord(sql1);
		textview1.setText(String.valueOf(ivalue1));

		String sql2 = "";
		sql2 = "select  count(*) from tblPregnant_woman where IsPregnant=1 and AshaID = "
				+ ashaid
				+ " and (Date(PWRegistrationDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "')) and Regwithin12weeks = 1";
		int ivalue2 = 0;
		ivalue2 = dataprovider.getMaxRecord(sql2);
		textview2.setText(String.valueOf(ivalue2) + '/'
				+ String.valueOf(ivalue1));

		String sql3 = "";
		sql3 = "select  count(*) from tblANCVisit where Visit_No=1 and CheckupVisitDate is not null and ByAshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "'))";
		int ivalue3 = 0;
		ivalue3 = dataprovider.getMaxRecord(sql3);

		String sql3Query = "";
		sql3Query = "select  count(*) from tblPregnant_woman where where IsPregnant=0 and AshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "'))";
		int ivalue3Query = 0;
		ivalue3Query = dataprovider.getMaxRecord(sql3Query);

		textview3.setText(String.valueOf(ivalue3) + '/'
				+ String.valueOf(ivalue3Query));

		String sql4 = "";
		sql4 = "select  count(*) from tblANCVisit where Visit_No=2 and CheckupVisitDate is not null and ByAshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "') )";
		int ivalue4 = 0;
		ivalue4 = dataprovider.getMaxRecord(sql4);
		textview4.setText(String.valueOf(ivalue4) + '/'
				+ String.valueOf(ivalue3Query));

		String sql5 = "";
		sql5 = "select  count(*) from tblANCVisit where Visit_No=3 and CheckupVisitDate is not null and ByAshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "') )";
		int ivalue5 = 0;
		ivalue5 = dataprovider.getMaxRecord(sql5);
		textview5.setText(String.valueOf(ivalue5) + '/'
				+ String.valueOf(ivalue3Query));

		String sql6 = "";
		sql6 = "select  count(*) from tblANCVisit where Visit_No=4 and CheckupVisitDate is not null and ByAshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "') )";
		int ivalue6 = 0;
		ivalue6 = dataprovider.getMaxRecord(sql6);
		textview6.setText(String.valueOf(ivalue6) + '/'
				+ String.valueOf(ivalue3Query));

		String sql7 = "";
		sql7 = "select  count(*) from tblANCVisit where TTsecondDoseDate is not null or TTboosterDate is not null and ByAshaID = "
				+ ashaid
				+ " and ((Date(TTsecondDoseDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "')) or (Date(TTboosterDate) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "')))";
		int ivalue7 = 0;
		ivalue7 = dataprovider.getMaxRecord(sql7);
		textview7.setText(String.valueOf(ivalue7) + '/'
				+ String.valueOf(ivalue3Query));

		String Sql8 = "";
		Sql8 = "select  count(*) from tblPregnant_woman where DeliveryPlace=2 and AshaID = "
				+ ashaid
				+ " and (Date(DeliveryDateTime) Between Date('"
				+ lastmonthStartDate
				+ "') and Date('"
				+ lastMonthEndDate
				+ "'))";
		int iValue8 = 0;
		iValue8 = dataprovider.getMaxRecord(Sql8);
		textview8.setText(String.valueOf(iValue8) + '/'
				+ String.valueOf(ivalue3Query));

	}

	public void showlastthreeMonthData() {
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

		String sql1 = "";
		sql1 = "select  count(*) from tblPregnant_woman where IsPregnant=1 and AshaID = "
				+ ashaid
				+ " and (Date(PWRegistrationDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue1 = 0;
		ivalue1 = dataprovider.getMaxRecord(sql1);
		textview1.setText(String.valueOf(ivalue1));

		String sql2 = "";
		sql2 = "select  count(*) from tblPregnant_woman where IsPregnant=1 and AshaID = "
				+ ashaid
				+ " and (Date(PWRegistrationDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth
				+ "')) and Regwithin12weeks = 1";
		int ivalue2 = 0;
		ivalue2 = dataprovider.getMaxRecord(sql2);
		textview2.setText(String.valueOf(ivalue2) + '/'
				+ String.valueOf(ivalue1));

		String sql3 = "";
		sql3 = "select  count(*) from tblANCVisit where Visit_No=1 and ByAshaID = "
				+ ashaid
				+ " and CheckupVisitDate is not null and (Date(CheckupVisitDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue3 = 0;
		ivalue3 = dataprovider.getMaxRecord(sql3);

		String sql3Query = "";
		sql3Query = "select  count(*) from tblPregnant_woman where where IsPregnant=0 and AshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue3Query = 0;
		ivalue3Query = dataprovider.getMaxRecord(sql3Query);

		textview3.setText(String.valueOf(ivalue3) + '/'
				+ String.valueOf(ivalue3Query));

		String sql4 = "";
		sql4 = "select  count(*) from tblANCVisit where Visit_No=2 and ByAshaID = "
				+ ashaid
				+ " and CheckupVisitDate is not null and ByAshaID = "
				+ ashaid
				+ " and (Date(CheckupVisitDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue4 = 0;
		ivalue4 = dataprovider.getMaxRecord(sql4);
		textview4.setText(String.valueOf(ivalue4) + '/'
				+ String.valueOf(ivalue3Query));

		String sql5 = "";
		sql5 = "select  count(*) from tblANCVisit where Visit_No=3 and ByAshaID = "
				+ ashaid
				+ " and CheckupVisitDate is not null and (Date(CheckupVisitDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue5 = 0;
		ivalue5 = dataprovider.getMaxRecord(sql5);
		textview5.setText(String.valueOf(ivalue5) + '/'
				+ String.valueOf(ivalue3Query));

		String sql6 = "";
		sql6 = "select  count(*) from tblANCVisit where Visit_No=4 and ByAshaID = "
				+ ashaid
				+ " and CheckupVisitDate is not null  and (Date(CheckupVisitDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int ivalue6 = 0;
		ivalue6 = dataprovider.getMaxRecord(sql6);
		textview6.setText(String.valueOf(ivalue6) + '/'
				+ String.valueOf(ivalue3Query));

		String sql7 = "";
		sql7 = "select  count(*) from tblANCVisit where TTsecondDoseDate is not null or TTboosterDate is not null and ByAshaID = "
				+ ashaid
				+ " and ((Date(TTsecondDoseDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth
				+ "')) or (Date(TTboosterDate) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "')))";
		int ivalue7 = 0;
		ivalue7 = dataprovider.getMaxRecord(sql7);
		textview7.setText(String.valueOf(ivalue7) + '/'
				+ String.valueOf(ivalue3Query));

		String Sql8 = "";
		Sql8 = "select  count(*) from tblPregnant_woman where DeliveryPlace=2 and AshaID = "
				+ ashaid
				+ " and (Date(DeliveryDateTime) Between Date('"
				+ currentDatefirstDateOfPreviousMonth1
				+ "') and Date('"
				+ currentDatelastDateOfPreviousMonth + "'))";
		int iValue8 = 0;
		iValue8 = dataprovider.getMaxRecord(Sql8);
		textview8.setText(String.valueOf(iValue8) + '/'
				+ String.valueOf(ivalue3Query));

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if (global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(ReportIndicator_ashaList.this,
					AshaDashboardList.class);
			finish();
			startActivity(i);
		} else {
			Intent i = new Intent(ReportIndicator_ashaList.this,
					Report_Module.class);
			finish();
			startActivity(i);
		}
	}

}
