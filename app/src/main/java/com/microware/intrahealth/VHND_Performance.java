package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.VHND_Perform_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_PerformanceIndicator;
import com.microware.intrahealth.object.tbl_VHNDPerformance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class VHND_Performance extends Activity {
	GridView Grid_Preform;
	DataProvider dataProvider;
	ArrayList<MstVHND_PerformanceIndicator> VHND_Perform = new ArrayList<MstVHND_PerformanceIndicator>();
	ArrayList<tbl_VHNDPerformance> VHND_perform = new ArrayList<tbl_VHNDPerformance>();
	Global global;
	TextView txt_date, villagename_tx;
	// Spinner spin_yesno;
	Button btnsave;
	TextView txt_date_show, txt_date_show1;
	Dialog datepic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_performance);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		Grid_Preform = (GridView) findViewById(R.id.Grid_Preform);
		txt_date = (TextView) findViewById(R.id.txt_date);
		txt_date_show1 = (TextView) findViewById(R.id.txt_date_show1);
		villagename_tx = (TextView) findViewById(R.id.villagename_tx);
		btnsave = (Button) findViewById(R.id.btnsave);

		txt_date_show = (TextView) findViewById(R.id.txt_date_show);
		txt_date_show1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(txt_date_show1);
			}
		});
		String sqql = "Select Actual_VHNDDate from tblVHNDDuelist where VHNDDate='"
				+ global.getVHND_Date()
				+ "' and AshaID='"
				+ global.getsGlobalAshaCode()
				+ "' and VillageID='"
				+ global.getVHND_VillageID() + "' ";
		String date = dataProvider.getRecord(sqql);
		txt_date_show1.setText(Validate.changeDateFormat(date));
		btnsave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SavePerform();
			}
		});
		try {
			UpdateVHND();

			String sql = "select cast(round(julianday('Now')-julianday('"
					+ txt_date.getText().toString() + "'))as int)";
			int Day = Integer.valueOf(dataProvider.getRecord(sql));
			if (Day > 7) {
				btnsave.setEnabled(false);
				btnsave.setBackgroundDrawable(this.getResources().getDrawable(
						R.color.gray));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ShowDatePicker(final TextView tVvisit) {
		String languageToLoad = "en"; // your language
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		// TODO Auto-generated method stub
		datepic = new Dialog(this, R.style.CustomTheme);

		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker);
		Button btnset = (Button) datepic.findViewById(R.id.set);
		Button btncancel = (Button) datepic.findViewById(R.id.cancle);

		datepic.show();

		datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		final DatePicker datetext = (DatePicker) datepic
				.findViewById(R.id.idfordate);
		Date date = new Date();
		datetext.setMaxDate((long) date.getTime());
		// Calendar calendar = Calendar.getInstance();
		// int daysInMonth = calendar.getActualMinimum(Calendar.YEAR);
		// int daysMonth = calendar.getActualMinimum(Calendar.MONTH);
		// int maxdaysInMonth = calendar.getActualMaximum(Calendar.YEAR);
		// int maxdaysMonth = calendar.getActualMaximum(Calendar.MONTH);
		// datetext.setMaxDate(maxdaysInMonth);
		// datetext.setMaxDate(maxdaysMonth);
		// datetext.setMinDate(daysInMonth);
		// datetext.setMinDate(daysMonth);

		btncancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated

				datepic.dismiss();

			}
		});

		btnset.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				int dt = datetext.getDayOfMonth();
				int month = datetext.getMonth() + 1;
				int year = datetext.getYear();
				String date = "" + dt;
				String mnth = "" + month;
				if (dt < 10) {
					date = "0" + dt;
				}
				if (month < 10) {
					mnth = "0" + month;
				}

				String sSellectedDate = date + "-" + mnth + "-"
						+ String.valueOf(year);
				tVvisit.setText(sSellectedDate);
				datepic.dismiss();
			}
		});

	}

	public void UpdateVHND() {
		VHND_perform = dataProvider.getVHND_Performance(
				String.valueOf(global.getsGlobalAshaCode()),
				global.getVHND_ID(), 1);
		if (VHND_perform.size() > 0 && VHND_perform != null) {
			// txt_date.setText(String.valueOf(VHND_perform.get(0).getDate()));
			// String VillageName =
			// "Select VillageName from MstVillage where VillageID='"
			// + VHND_perform.get(0).getVillageId() + "' and LanguageID=1";
			// villagename_tx.setText(dataProvider.getRecord(VillageName));
			txt_date.setText(String.valueOf(global.getVHND_Date()));
			txt_date_show.setText(Validate.changeDateFormat(String
					.valueOf(global.getVHND_Date())));
			String dateee[] = {};
			String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
					"Aug", "Sep", "Oct", "Nov", "Dec" };
			int month = 0, Year = 0;
			dateee = String.valueOf(global.getVHND_Date()).split("-");
			month = Integer.valueOf(dateee[1]);
			Year = Integer.valueOf(dateee[0]);
			String Monthn = "";
			if (dateee != null) {
				Monthn = aa[month - 1];
			}
			String VillageName = "Select AW_Name from VHND_Schedule where ASHA_ID="
					+ global.getsGlobalAshaCode()
					+ " and Year='"
					+ Year
					+ "' and "
					+ Monthn
					+ "='"
					+ global.getVHND_Date()
					+ "' and Village_ID='" + global.getVHND_VillageID() + "'";
			villagename_tx.setText(dataProvider.getRecord(VillageName));

			fillVHND();
		}
		fillVHND();// For Testing only ,It sud be hide
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public void SavePerform() {

		int iSize = Grid_Preform.getChildCount();
		String Value = "";
		for (int i = 0; i < iSize; i++) {
			ViewGroup gridChild = (ViewGroup) Grid_Preform.getChildAt(i);

			RadioButton Yes_RB = (RadioButton) gridChild
					.findViewById(R.id.Yes_RB);
			RadioButton No_RB = (RadioButton) gridChild
					.findViewById(R.id.No_RB);
			// RadioButton Clear_RB = (RadioButton) gridChild
			// .findViewById(R.id.Clear_RB);

			int val = 0;
			// if (Clear_RB.isChecked()) {
			// val = 0;
			// } else
			if (Yes_RB.isChecked()) {
				val = 1;
			} else if (No_RB.isChecked()) {
				val = 0;
			}

			Value = Value + val;
		}
		String OPT_Value = Value;
		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_Perform(global.getVHND_ID(),
				Integer.valueOf(global.getsGlobalAshaCode()), OPT_Value, "U");
		if (Returnvalue > 0) {
			alerttoast(R.string.savesuccessfully);
			String sqql = "update   tblVHNDDuelist set Actual_VHNDDate='"
					+ Validate.changeDateFormat(txt_date_show1.getText()
							.toString()) + "'  where VHNDDate='"
					+ global.getVHND_Date() + "' and AshaID='"
					+ global.getsGlobalAshaCode() + "' and VillageID='"
					+ global.getVHND_VillageID() + "' ";
			 dataProvider.executeSql(sqql);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	private void alerttoast(int i) {
		Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
				.show();
	}

	public void fillVHND() {
		String sql = "Select OPT_Value from tbl_VHNDPerformance where VHND_ID='"
				+ global.getVHND_ID()
				+ "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		String OPT_Value = dataProvider.getRecord(sql);

		VHND_Perform = dataProvider.getVHND_Perform(123, 1,
				global.getLanguage());
		int Count = 0;
		if (VHND_Perform != null) {
			Count = VHND_Perform.size();
			android.view.ViewGroup.LayoutParams params = Grid_Preform
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					90, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Grid_Preform.setLayoutParams(params);
			Grid_Preform.setAdapter(new VHND_Perform_Adapter(this,
					VHND_Perform, global.getsGlobalAshaCode(), OPT_Value));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// Intent i = new Intent(VHND_Performance.this, VHND_Record.class);
		// finish();
		Intent i = new Intent(VHND_Performance.this, VHND_DateRecord.class);
		i.putExtra("ActivityName", "Dashboard_Activity");
		startActivity(i);
	}

}
