package com.microware.intrahealth;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.adapter.Incentives_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
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
import android.widget.AdapterView.OnItemSelectedListener;

public class Incentives extends Activity {

	GridView Grid_incentives;
	DataProvider dataProvider;
	Spinner SpinMonth;
	ArrayAdapter<String> adapter;
	ArrayList<tbl_Incentive> incentive = new ArrayList<tbl_Incentive>();
	String Monthhh[];
	int AshaID = 136;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.incentives);
		dataProvider = new DataProvider(this);
		Grid_incentives = (GridView) findViewById(R.id.VHND_rcrd_grid);
		SpinMonth = (Spinner) findViewById(R.id.SpinMonth);
		Monthhh = getResources().getStringArray(R.array.incentivemonth);
		SelectHousehold();
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
		incentive = dataProvider.gettIncentives(123, 1);
		String Date = "", Date1 = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		if (Flag == 0) {
			Date1 = dateFormat.format(date);
			Date = Date1.substring(0, 7);
		} else if (Flag == 1) {
			// Date = dateFormat.format(cal.getTime());
			Date1 = dateFormat.format(cal.getTime());
			Date = Date1.substring(0, 7);
		}

		int Count = 0;
		if (incentive != null) {
			Count = incentive.size();
			android.view.ViewGroup.LayoutParams params = Grid_incentives
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Grid_incentives.setLayoutParams(params);
			Grid_incentives.setAdapter(new Incentives_Adapter(this, incentive,
					Date, AshaID));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(Incentives.this, Dashboard_Activity.class);
		finish();
		startActivity(i);

	}

}
