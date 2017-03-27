package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({ "SimpleDateFormat", "InflateParams" })
public class Deathform extends Activity {
	DataProvider dataProvider;
	Global global;
	Spinner spindeathsite, spindeathvillage;
	TextView tvdeathdate, username;
	Dialog datepic;
	Button btnSave;
	int count = 0;
	EditText etdeathvillagename;
	TableRow tblname;
	ArrayAdapter<String> adapter;
	public ArrayList<MstCommon> common = new ArrayList<MstCommon>();
	ConnectivityManager connMgrCheckConnection;
	NetworkInfo networkInfoCheckConnection;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		dataProvider = new DataProvider(this);
		global = (Global) getApplicationContext();
		setContentView(R.layout.death);
		spindeathsite = (Spinner) findViewById(R.id.spindeathsite);
		spindeathvillage = (Spinner) findViewById(R.id.spindeathvillage);
		etdeathvillagename = (EditText) findViewById(R.id.etdeathvillagename);
		username = (TextView) findViewById(R.id.username);
		tblname = (TableRow) findViewById(R.id.tblname);
		tvdeathdate = (TextView) findViewById(R.id.tvdeathdate);
		btnSave = (Button) findViewById(R.id.btnSave);
		fillspiners(spindeathsite, 105, global.getLanguage());
		fillspiners(spindeathvillage, 7, global.getLanguage());
		connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			Tracker t = ((Global) getApplication())
					.getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("Death form");
			t.send(new HitBuilders.AppViewBuilder().build());
		}
		spindeathvillage
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							if (returnID(7, position) == 2) {
								tblname.setVisibility(View.VISIBLE);
							} else {
								tblname.setVisibility(View.GONE);
							}
						} else {
							tblname.setVisibility(View.GONE);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		String sql = "select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
				+ global.getGlobalHHFamilyMemberGUID() + "'";
		String name = dataProvider.getRecord(sql);
		username.setText(name);
		tvdeathdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvdeathdate);
			}
		});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int iCheck = sCheckValidation();
				String sql = "select StatusID from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ global.getGlobalHHFamilyMemberGUID() + "'";
				count = dataProvider.getMaxRecord(sql);
				if (iCheck == 1) {
					savedata();
					CustomAlertSave(getResources().getString(
							R.string.savesuccessfully));
				} else {
					showAlert(iCheck);
				}
			}
		});
	}

	public int sCheckValidation() {

		if (tvdeathdate.getText() != null
				&& tvdeathdate.getText().toString().length() == 0) {
			return 2;
		} else if (spindeathsite.getSelectedItemPosition() == 0) {
			return 3;
		} else if (spindeathvillage.getSelectedItemPosition() == 0) {

			return 4;
		} else if (returnID(7, spindeathvillage.getSelectedItemPosition() - 1) == 2
				&& etdeathvillagename.getText().toString() != null
				&& etdeathvillagename.getText().toString().length() == 0) {

			return 5;

		}
		return 1;

	}

	public void showAlert(int iCheck) {
		if (iCheck == 2) {
			CustomAlert(getResources().getString(R.string.enterdeathdate));
		} else if (iCheck == 3) {
			CustomAlert(getResources().getString(R.string.enterplacedate));
		} else if (iCheck == 4) {
			CustomAlert(getResources().getString(R.string.enterDieddate));
		} else if (iCheck == 5) {
			CustomAlert(getResources().getString(R.string.enterDiedvillage));
		}
	}

	public void CustomAlert(String msg) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_layout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
		btnok.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {

				dialog.dismiss();
			}
		});

		// Display the dialog
		dialog.show();

	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			GoogleAnalytics.getInstance(Deathform.this).reportActivityStart(
					this);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			GoogleAnalytics.getInstance(Deathform.this)
					.reportActivityStop(this);
		}
	}

	public void CustomAlertSave(String msg) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_layout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {
				finish();
				dialog.dismiss();

			}
		});

		// Display the dialog
		dialog.show();

	}

	public void savedata() {
		String DateOfDeath = "";
		int PlaceOfDeath = 0;
		String NameofDeathplace = "";
		int DeathVillage = 0;
		if (tvdeathdate.getText().toString() != null
				&& tvdeathdate.getText().toString().length() > 0) {
			DateOfDeath = Validate.changeDateFormat(tvdeathdate.getText()
					.toString());
		}
		if (spindeathsite.getSelectedItemPosition() > 0) {
			PlaceOfDeath = returnID(105,
					spindeathsite.getSelectedItemPosition() - 1);
		}
		if (spindeathvillage.getSelectedItemPosition() > 0) {
			DeathVillage = returnID(7,
					spindeathvillage.getSelectedItemPosition() - 1);
		}
		if (etdeathvillagename.getText().toString() != null
				&& etdeathvillagename.getText().toString().length() > 0) {
			NameofDeathplace = etdeathvillagename.getText().toString();
		}
		dataProvider.updateDeathDetails(global.getGlobalHHSurveyGUID(),
				DateOfDeath, PlaceOfDeath, NameofDeathplace, DeathVillage,
				global.getGlobalHHFamilyMemberGUID());
		Calendar c1 = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = df.format(c1.getTime());
		String sql = "insert into  tblhhupdate_Log (StatusId_Old,StatusId_New,hhsurveyguid,hhmemberguid,UpdatedOn)values("
				+ count
				+ ",2,'"
				+ global.getGlobalHHSurveyGUID()
				+ "','"
				+ global.getGlobalHHFamilyMemberGUID()
				+ "','"
				+ formattedDate
				+ "') ";
		dataProvider.executeSql(sql);
	}

	public void fillspiners(Spinner spin, int iFlag, int languageID) {
		common = dataProvider.getCommonRecord(languageID, iFlag);

		String Where[] = new String[common.size() + 1];
		Where[0] = getResources().getString(R.string.select);

		for (int i = 1; i < common.size() + 1; i++) {
			Where[i] = common.get(i - 1).getValue();
			// iBlockID[i] = Block.get(i).getBlockID();

		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, Where);

		spin.setAdapter(adapter);

	}

	public int returnPosition(int flag, int ID) {
		int ipos = 0;
		ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
		combobox = dataProvider.getCommonRecord(global.getLanguage(), flag);
		for (int i = 0; i < combobox.size(); i++) {
			if (ID == combobox.get(i).getID()) {
				ipos = i + 1;
			}
		}

		return ipos;
	}

	public int returnID(int flag, int pos) {
		int ipos = 0;
		ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
		combobox = dataProvider.getCommonRecord(global.getLanguage(), flag);
		if (pos > 0) {
			ipos = combobox.get(pos - 1).getID();
		}
		return ipos;
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

			@SuppressWarnings("unused")
			public void onClick(View v) {
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

}
