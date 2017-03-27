package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tbl_VHNDPerformance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VHND extends Activity {
	Spinner spinVillageName;
	ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
	ArrayList<tbl_VHNDPerformance> VHND_perform = new ArrayList<tbl_VHNDPerformance>();
	Global global;
	DataProvider dataProvider;
	ArrayAdapter<String> adapter;
	Dialog datepic;
	TextView txt_date;
	Button btn_save;
	String VHND_Flag = "";
	int Village_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
		txt_date = (TextView) findViewById(R.id.txt_date);
		btn_save = (Button) findViewById(R.id.btn_save);

		fillVillageName(global.getLanguage());
		VHND_Flag = global.getVHND_Composite_Flag();

		spinVillageName.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos > 0) {
					Village_ID = Integer.valueOf(Village.get(pos - 1)
							.getVillageID());
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		txt_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(txt_date);
			}
		});
		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (Village_ID == 0) {
					alerttoast(R.string.PleaseEnter, R.string.VillageName);
				} else if (txt_date.getText().toString().equalsIgnoreCase("")) {
					alerttoast(R.string.PleaseEnter, R.string.Date);
				} else {
					Save_VHND(VHND_Flag);
				}
			}
		});
		if (global.getVHND_Composite_Flag().equalsIgnoreCase("U")) {
			UpdateVHND();
		}

	}

	public void Save_VHND(String VHND_Flag) {
		// int PID = 0;
		String VHND_ID = global.getVHND_ID();
		int SS_ID = 0;
		int VillageId = Village_ID;
		int AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		int AWWID = 0;
		int ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		String Date = txt_date.getText().toString();

		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_Perform(VHND_ID, SS_ID, VillageId,
				AshaID, AWWID, ANMID, Date, VHND_Flag);
		if (Returnvalue > 0) {
			alerttoast(R.string.savesuccessfully);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	public void UpdateVHND() {
		VHND_perform = dataProvider.getVHND_Performance(
				String.valueOf(global.getsGlobalAshaCode()),
				global.getVHND_ID(), 1);
		if (VHND_perform.size() > 0 && VHND_perform != null) {
			txt_date.setText(String.valueOf(VHND_perform.get(0).getDate()));
			spinVillageName.setSelection(setpostion(String.valueOf(VHND_perform
					.get(0).getVillageId())));
		}
	}

	private void alerttoast(int i) {
		Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
				.show();
	}

	private void alerttoast(int i, int j) {
		Toast.makeText(this,
				getResources().getString(i) + " " + getResources().getString(j),
				Toast.LENGTH_LONG).show();
	}

	private void fillVillageName(int Language) {
		Village = dataProvider.getMstVillageName(1);

		String sValue[] = new String[Village.size() + 1];
		sValue[0] = getResources().getString(R.string.select);
		for (int i = 0; i < Village.size(); i++) {
			sValue[i + 1] = Village.get(i).getVillageName();
		}
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spinVillageName.setAdapter(adapter);
	}

	private int setpostion(String value) {
		int pos = 0;
		Village = dataProvider.getMstVillageName(1);
		for (int i = 0; i < Village.size(); i++) {
			if (value.length() == 0) {
				break;
			} else if (value.equalsIgnoreCase(String.valueOf(Village.get(i)
					.getVillageID()))) {
				pos = i + 1;
				break;
			}
		}
		return pos;
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
		btncancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated

				datepic.dismiss();

			}
		});

		btnset.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				@SuppressWarnings("unused")
				String[] monthName = { "January", "February", "March", "April",
						"May", "June", "July", "August", "September",
						"October", "November", "December" };
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(VHND.this, VHND_Record.class);
		finish();
		startActivity(i);
	}
}
