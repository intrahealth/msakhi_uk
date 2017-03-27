package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.VHND_DueList_Adpater;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.MstVillage;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VHND_DueList extends Activity {

	GridView VHND_Duelist_grid;
	DataProvider dataProvider;
	Global global;
	ArrayAdapter<String> adapter;
	Dialog datepic;
	TextView txt_date;
	// Spinner spinVillageName;
	TextView spinVillageName;
	ArrayList<MstVHND_DueListItems> VHND_Duelist = new ArrayList<MstVHND_DueListItems>();
	ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
	int Village_ID = 0;
	Button Update, AddPerform;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_duelist);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		VHND_Duelist_grid = (GridView) findViewById(R.id.VHND_Duelist_grid);
		txt_date = (TextView) findViewById(R.id.txt_date);
		// spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
		spinVillageName = (TextView) findViewById(R.id.spinVillageName);
		Update = (Button) findViewById(R.id.Update);
		AddPerform = (Button) findViewById(R.id.AddPerform);
		Village_ID = Integer.valueOf(global.getVHND_VillageID());
		// String sql = "Select VillageName from MstVillage where VillageID="
		// + Village_ID + " and LanguageID=" + global.getLanguage() + "";
		String mon[] = {};
		String CurrentDate = dateFormat.format(date);
		mon = CurrentDate.split("-");
		int Year = 0;
		Year = Integer.valueOf(mon[0]);
		String sql = "Select AW_Name from VHND_Schedule where ASHA_ID="
				+ global.getsGlobalAshaCode() + " and Year='" + Year + "' and "
				+ global.getVHND_MonthField().toString() + "='"
				+ global.getVHND_Date() + "'";
		spinVillageName.setText(String.valueOf(dataProvider.getRecord(sql)));
		txt_date.setText(String.valueOf(global.getVHND_Date()));

		// fillVillageName(global.getLanguage());
		// spinVillageName.setOnItemSelectedListener(new
		// OnItemSelectedListener() {
		//
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int pos, long id) {
		// if (pos > 0) {
		// Village_ID = Integer.valueOf(Village.get(pos - 1)
		// .getVillageID());
		// }
		// }
		//
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });
		// txt_date.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ShowDatePicker(txt_date);
		// }
		// });
		if (!txt_date.getText().toString().equalsIgnoreCase("")
				&& Village_ID != 0) {
			fillVHNDDUelist(txt_date.getText().toString(), Village_ID);
		}

		// Update and Refresh
		Update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!txt_date.getText().toString().equalsIgnoreCase("")
						&& Village_ID != 0) {
					fillVHNDDUelist(txt_date.getText().toString(), Village_ID);
				}
			}
		});

		AddPerform.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// global.setVHND_Composite_Flag("U");
				// global.setVHND_ID(VHND_perform.get(position).getVHND_ID());
				Intent i = new Intent(VHND_DueList.this, VHND_Performance.class);
				finish();
				startActivity(i);

			}
		});
	}

	// private void fillVillageName(int Language) {
	// Village = dataProvider.getMstVillageName(1);
	//
	// String sValue[] = new String[Village.size() + 1];
	// sValue[0] = getResources().getString(R.string.select);
	// for (int i = 0; i < Village.size(); i++) {
	// sValue[i + 1] = Village.get(i).getVillageName();
	// }
	// adapter = new ArrayAdapter<String>(this,
	// android.R.layout.simple_spinner_dropdown_item, sValue);
	// spinVillageName.setAdapter(adapter);
	// }

	public void fillVHNDDUelist(String Date, int VillageID) {
		VHND_Duelist = dataProvider.getVHND_Duelist(1, 0);
		int Count = 0;
		if (VHND_Duelist != null) {
			Count = VHND_Duelist.size();

			android.view.ViewGroup.LayoutParams params = VHND_Duelist_grid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			VHND_Duelist_grid.setLayoutParams(params);
			VHND_Duelist_grid.setAdapter(new VHND_DueList_Adpater(this,
					VHND_Duelist, Integer.valueOf(global.getsGlobalAshaCode()),
					Date, Village_ID));
		}
	}

	// public void ShowDatePicker(final TextView tVvisit) {
	// String languageToLoad = "en"; // your language
	// Locale locale = new Locale(languageToLoad);
	// Locale.setDefault(locale);
	// // TODO Auto-generated method stub
	// datepic = new Dialog(this, R.style.CustomTheme);
	//
	// Window window = datepic.getWindow();
	// window.requestFeature(Window.FEATURE_NO_TITLE);
	// datepic.setContentView(R.layout.datetimepicker);
	//
	// Button btnset = (Button) datepic.findViewById(R.id.set);
	// Button btncancel = (Button) datepic.findViewById(R.id.cancle);
	// datepic.show();
	// datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	//
	// final DatePicker datetext = (DatePicker) datepic
	// .findViewById(R.id.idfordate);
	// Date date = new Date();
	// datetext.setMaxDate((long) date.getTime());
	// btncancel.setOnClickListener(new View.OnClickListener() {
	//
	// public void onClick(View v) {
	// // TODO Auto-generated
	//
	// datepic.dismiss();
	//
	// }
	// });
	//
	// btnset.setOnClickListener(new View.OnClickListener() {
	//
	// public void onClick(View v) {
	// @SuppressWarnings("unused")
	// String[] monthName = { "January", "February", "March", "April",
	// "May", "June", "July", "August", "September",
	// "October", "November", "December" };
	// int dt = datetext.getDayOfMonth();
	// int month = datetext.getMonth() + 1;
	// int year = datetext.getYear();
	//
	// String date = "" + dt;
	// String mnth = "" + month;
	// if (dt < 10) {
	// date = "0" + dt;
	// }
	// if (month < 10) {
	// mnth = "0" + month;
	// }
	//
	// // String sSellectedDate = date + "-" + mnth + "-"
	// // + String.valueOf(year);
	// String sSellectedDate = String.valueOf(year) + "-" + mnth + "-"
	// + date;
	// tVvisit.setText(sSellectedDate);
	// datepic.dismiss();
	// if (!sSellectedDate.equalsIgnoreCase("") && Village_ID != 0) {
	// fillVHNDDUelist(sSellectedDate, Village_ID);
	// }
	// }
	// });
	//
	// }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(VHND_DueList.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}
}
