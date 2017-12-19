package com.microware.intrahealth;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.Incentives_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_Incentive;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Incentives extends Activity {

	GridView Grid_incentives;
	DataProvider dataProvider;
	Spinner SpinMonth;
	ArrayAdapter<String> adapter;
	ArrayList<tbl_Incentive> incentive = new ArrayList<tbl_Incentive>();
	ArrayList<tblChild> child = new ArrayList<tblChild>();
	String Monthhh[];
	int AshaID = 136;
	Global global;
	TextView tv_ashaname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.incentives);
		global = (Global) getApplication();
		setTitle(global.getVersionName());
		dataProvider = new DataProvider(this);
		Grid_incentives = (GridView) findViewById(R.id.VHND_rcrd_grid);
		SpinMonth = (Spinner) findViewById(R.id.SpinMonth);
		Monthhh = getResources().getStringArray(R.array.incentivemonth);
		tv_ashaname = (TextView) findViewById(R.id.tv_ashaname);
		tv_ashaname.setText(global.getsGlobalAshaName());
		SelectHousehold();
		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String localTime = date1.format(currentLocalTime);
		String Incentives = Validate.random();
		global.setIncentives(String.valueOf(Incentives));
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateStrings = sdf.format(date);
		dataProvider.getUserLogin(Incentives, global.getUserID(), "Incentive", "Incentive",
				localTime, dateStrings);
		// fillIncentives(0);

	}

	public void SelectHousehold() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		// Date date = new Date();
		// String datee = dateFormat.format(date);
		// int month, Year;
		// month = Integer.valueOf(datee.substring(3, 5));
		// Year = Integer.valueOf(datee.substring(6, 10));
		// String sComboValue[] = { "", "" };
		// if (month == 1) {
		//
		// }else{
		// sComboValue[0] = "Current Month";
		// sComboValue[1] = "Previous Month";
		// sComboValue[2] = "Select Month";
		// }

		// String sComboValue[] = new String[combo1.size()];
		//
		// for (int i = 0; i < combo1.size(); i++) {
		// sComboValue[i] = combo1.get(i).getHhidnew() + "-"
		// + combo1.get(i).getA2_HHName();
		// }
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Monthhh);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
		SpinMonth.setAdapter(adapter);
		SpinMonth.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos == 0) {
					fillIncentives(0);
				} else if (pos == 1) {
					fillIncentives(1);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	public void fillIncentives(int Flag) {
		incentive = dataProvider.gettIncentives(123, global.getLanguage());
		child = dataProvider.gettblChild("","", 6,0);
		String currentDate = "", Date1 = "", pastDate = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		if (Flag == 0) {
			Date1 = dateFormat.format(date);
			String[] dt_array = Date1.split("-");
			String yyyy = dt_array[0];
			String mm = dt_array[1];
			String mm1 = String.valueOf(Integer.valueOf(dt_array[1]) - 1);
			String dd = "25";
			String dd1 = "26";
			if (mm1.length() == 1) {
				mm1 = "0" + mm1;
			}
			if (dd.length() == 1) {
				dd = "0" + dd;
			}
			if (dd1.length() == 1) {
				dd1 = "0" + dd1;
			}
			currentDate = yyyy + "-" + mm + "-" + dd;
			pastDate = yyyy + "-" + mm1 + "-" + dd1;
		} else if (Flag == 1) {
			// Date = dateFormat.format(cal.getTime());
			Date1 = dateFormat.format(cal.getTime());
			String[] dt_array = Date1.split("-");
			String yyyy = dt_array[0];
			String mm = dt_array[1];
			String mm1 = String.valueOf(Integer.valueOf(dt_array[1]) - 1);
			if (mm1.length() == 1) {
				mm1 = "0" + mm1;
			}
			String dd = "25";
			if (dd.length() == 1) {
				dd = "0" + dd;
			}
			String dd1 = "26";
			if (dd1.length() == 1) {
				dd1 = "0" + dd1;
			}
			currentDate = yyyy + "-" + mm + "-" + dd;
			pastDate = yyyy + "-" + mm1 + "-" + dd1;
		}

		int Count = 0;
		if (incentive != null) {
			Count = incentive.size();
			android.view.ViewGroup.LayoutParams params = Grid_incentives
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					90, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Grid_incentives.setLayoutParams(params);
			Grid_incentives.setAdapter(new Incentives_Adapter(this, incentive,
					child, currentDate, pastDate, AshaID));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String endTime = date1.format(currentLocalTime);
		dataProvider.getUserLoginUpdate(global.getIncentives(), endTime);
		
		if (global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(Incentives.this, AshaListForAnm.class);
			finish();

			i.putExtra("Flag", 5);
			startActivity(i);
		} else {
			Intent i = new Intent(Incentives.this, Dashboard_Activity.class);
			finish();
			startActivity(i);
		}}

}
