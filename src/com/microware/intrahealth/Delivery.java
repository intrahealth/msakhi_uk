package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class Delivery extends Activity {
	TableRow tblAddchild, tbldate, tblplace, tblcause, tblabortion,
			tbldateabortion, tbldatend, tbldatesb, tblcause1,
			tblanyOthercause1, tblanyOthercause, tblfacilitytype, tblfacility,
			tblanyotherplace, tbl_abortionfacility;
	RadioButton radioAbortion, radioLivebirth, radioStillbirth, radioInduced,
			radioSpontaneous, radioMaternaldeath, radiomdcs,
			radioNeonataldeath;
	// RadioGroup radioOutcome,radioAbortionlist,radioDeliverylist;
	DataProvider dataProvider;
	EditText etAnyOthercause1, etAnyOthercause, etAnyother;
	Spinner spincause, spinplace, spincause1, spinfacility,
			spin_abortionfacility;
	Global global;
	Button btnSave, btnChild;
	Dialog datepic;
	DatePicker datetext;
	ArrayAdapter<String> adapter;
	TextView etdate, etdateab, etdatestillb, etdatend, etfacility, tv_place;
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	ArrayList<tbl_pregnantwomen> record = new ArrayList<tbl_pregnantwomen>();
	ConnectivityManager connMgrCheckConnection;
	NetworkInfo networkInfoCheckConnection;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.delivery);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		btnSave = (Button) findViewById(R.id.btnSave);
		btnChild = (Button) findViewById(R.id.btnChild);
		tblAddchild = (TableRow) findViewById(R.id.tblAddchild);
		tbldate = (TableRow) findViewById(R.id.tbldate);
		tblplace = (TableRow) findViewById(R.id.tblplace);
		tbldateabortion = (TableRow) findViewById(R.id.tbldateabortion);
		tblanyOthercause1 = (TableRow) findViewById(R.id.tblanyOthercause1);
		tblanyOthercause = (TableRow) findViewById(R.id.tblanyOthercause);
		tblanyotherplace = (TableRow) findViewById(R.id.tblanyotherplace);
		tblcause = (TableRow) findViewById(R.id.tblcause);
		tblcause1 = (TableRow) findViewById(R.id.tblcause1);
		tbldatend = (TableRow) findViewById(R.id.tbldatend);
		tbldatesb = (TableRow) findViewById(R.id.tbldatesb);
		spincause = (Spinner) findViewById(R.id.spincause);
		spincause1 = (Spinner) findViewById(R.id.spincause1);
		spinfacility = (Spinner) findViewById(R.id.spinfacility);
		etfacility = (EditText) findViewById(R.id.etfacility);
		spinplace = (Spinner) findViewById(R.id.spinplace);
		etdate = (TextView) findViewById(R.id.etdate);
		etAnyother = (EditText) findViewById(R.id.etAnyother);
		etdatend = (TextView) findViewById(R.id.etdatend);
		etdatestillb = (TextView) findViewById(R.id.etdatestillb);
		etdateab = (TextView) findViewById(R.id.etdateab);
		etAnyOthercause1 = (EditText) findViewById(R.id.etAnyOthercause1);
		etAnyOthercause = (EditText) findViewById(R.id.etAnyOthercause);
		tblfacilitytype = (TableRow) findViewById(R.id.tblfacilitytype);
		tblfacility = (TableRow) findViewById(R.id.tblfacility);
		// tblDelivery=(TableRow)findViewById(R.id.tblDelivery);
		tblabortion = (TableRow) findViewById(R.id.tblabortion);
		radioAbortion = (RadioButton) findViewById(R.id.radioAbortion);
		radioLivebirth = (RadioButton) findViewById(R.id.radioLivebirth);
		radioMaternaldeath = (RadioButton) findViewById(R.id.radioMaternaldeath);
		radioInduced = (RadioButton) findViewById(R.id.radioInduced);
		radioSpontaneous = (RadioButton) findViewById(R.id.radioSpontaneous);
		radioStillbirth = (RadioButton) findViewById(R.id.radioStillbirth);
		radioNeonataldeath = (RadioButton) findViewById(R.id.radioNeonataldeath);
		tblfacilitytype.setVisibility(View.GONE);
		tblfacility.setVisibility(View.GONE);
		tblanyotherplace.setVisibility(View.GONE);
		spin_abortionfacility = (Spinner) findViewById(R.id.spin_abortionfacility);
		tbl_abortionfacility = (TableRow) findViewById(R.id.tbl_abortionfacility);
		tv_place = (TextView) findViewById(R.id.tv_place);

		fillCommonRecord(spincause, 19, global.getLanguage());
		fillCommonRecord(spincause1, 28, global.getLanguage());
		fillCommonRecord(spinfacility, 27, global.getLanguage());
		fillCommonRecord(spinplace, 18, global.getLanguage());
		fillCommonRecord(spin_abortionfacility, 27, global.getLanguage());
		connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			try {
				Tracker t = ((Global) getApplication())
						.getTracker(TrackerName.APP_TRACKER);
				t.setScreenName("Delivery");
				t.send(new HitBuilders.AppViewBuilder().build());
			} catch (Exception e) {

			}
		}
		spinfacility.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					if (returnid(position - 1, 27, global.getLanguage()) == 2) {
						tblfacility.setVisibility(View.VISIBLE);
					} else {
						tblfacility.setVisibility(View.GONE);
						etfacility.setText("");
					}

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spinplace.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					if (returnid(position - 1, 18, global.getLanguage()) == 2) {
						tblfacility.setVisibility(View.VISIBLE);
						tblfacilitytype.setVisibility(View.VISIBLE);
					} else if (returnid(position - 1, 18, global.getLanguage()) == 3) {
						tblanyotherplace.setVisibility(View.VISIBLE);

						tblfacility.setVisibility(View.GONE);
						tblfacilitytype.setVisibility(View.GONE);
						etfacility.setText("");
						spinfacility.setSelection(0);
					}
				} else {
					tblfacility.setVisibility(View.GONE);
					tblfacilitytype.setVisibility(View.GONE);
					etfacility.setText("");
					spinfacility.setSelection(0);
					tblanyotherplace.setVisibility(View.GONE);
					etAnyother.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		btnChild.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setsGlobalChildGUID("");
				Intent i = new Intent(Delivery.this, childActivity.class);
				finish();
				startActivity(i);
			}
		});
		spincause.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (position > 0) {
					if (returnid(position - 1, 19, global.getLanguage()) == 5) {

						tblanyOthercause.setVisibility(View.VISIBLE);
					} else {
						// tblcause.setVisibility(View.GONE);
						tblanyOthercause.setVisibility(View.GONE);
						etAnyOthercause.setText("");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spincause1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// if (position > 0) {
				// if (returnid(position-1, 28, global.getLanguage()) == 5) {
				//
				// tblanyOthercause1.setVisibility(View.VISIBLE);
				// } else {
				//
				// tblanyOthercause1.setVisibility(View.GONE);
				// etAnyOthercause1.setText("");
				// }
				// }
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savedata();
				CustomAlertSave(getResources().getString(
						R.string.savesuccessfully));
			}
		});
		etdate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(etdate);
			}
		});
		etdateab.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(etdateab);
			}
		});
		etdateab.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etdateab.getText() != null
						&& etdateab.getText().toString().length() > 0) {
					String sql = "Select LMPDate from tblPregnant_woman where PWGUID='"
							+ global.getsGlobalPWGUID() + "'";
					String LmpDate = dataProvider.getRecord(sql);
					if (LmpDate != null && LmpDate.length() > 0) {

						String tt1 = etdateab.getText().toString();
						String lmp = Validate.changeDateFormat(LmpDate);
						SimpleDateFormat dfDate = new SimpleDateFormat(
								"dd-MM-yyyy");
						java.util.Date d = null;
						java.util.Date d1 = null;
						int diffInDays = 0;
						// Calendar cal = Calendar.getInstance();
						try {
							d = dfDate.parse(tt1);
							d1 = dfDate.parse(lmp);
							diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						System.out.println(diffInDays);
						if (diffInDays <= 0) {
							etdateab.setText("");
							CustomAlertSave2(getResources().getString(
									R.string.validdatead));
						}
					}
				}
			}
		});
		etdatestillb.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etdatestillb.getText() != null
						&& etdatestillb.getText().toString().length() > 0) {
					String sql = "Select LMPDate from tblPregnant_woman where PWGUID='"
							+ global.getsGlobalPWGUID() + "'";
					String LmpDate = dataProvider.getRecord(sql);
					if (LmpDate != null && LmpDate.length() > 0) {

						String tt1 = etdatestillb.getText().toString();
						String lmp = Validate.changeDateFormat(LmpDate);
						SimpleDateFormat dfDate = new SimpleDateFormat(
								"dd-MM-yyyy");
						java.util.Date d = null;
						java.util.Date d1 = null;
						int diffInDays = 0;
						// Calendar cal = Calendar.getInstance();
						try {
							d = dfDate.parse(tt1);
							d1 = dfDate.parse(lmp);
							diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
						} catch (java.text.ParseException e) {
							e.printStackTrace();
						}

						System.out.println(diffInDays);
						if (diffInDays <= 0) {
							etdatestillb.setText("");
							CustomAlertSave2(getResources().getString(
									R.string.validdatesb));
						}
					}
				}
			}
		});
		etdatend.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// if (etdatend.getText() != null
				// && etdatend.getText().toString().length() > 0) {
				// String sql =
				// "Select LMPDate from tblPregnant_woman where PWGUID='"
				// + global.getsGlobalPWGUID() + "'";
				// String LmpDate = dataProvider.getRecord(sql);
				// if (LmpDate != null && LmpDate.length() > 0) {
				//
				// String tt1 = etdatend.getText().toString();
				// String lmp = Validate.changeDateFormat(LmpDate);
				// SimpleDateFormat dfDate = new SimpleDateFormat(
				// "dd-MM-yyyy");
				// java.util.Date d = null;
				// java.util.Date d1 = null;
				// int diffInDays = 0;
				// // Calendar cal = Calendar.getInstance();
				// try {
				// d = dfDate.parse(tt1);
				// d1 = dfDate.parse(lmp);
				// diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60
				// * 60 * 24));
				// } catch (java.text.ParseException e) {
				// e.printStackTrace();
				// }
				//
				// System.out.println(diffInDays);
				// if (diffInDays <= 0) {
				// etdatend.setText("");
				// CustomAlertSave2(getResources().getString(R.string.validdatesb));
				// }
				// }
				// }
			}
		});
		etdatend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(etdatend);
			}
		});
		etdatestillb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(etdatestillb);
			}
		});
		if (global.getsGlobalPWGUID() != null
				&& global.getsGlobalPWGUID().length() > 0) {
			Showdata();
		}

	}

	private void fillCommonRecord(Spinner spin, int iflag, int Language) {
		Common = dataProvider.getCommonRecord(Language, iflag);

		String sValue[] = new String[Common.size() + 1];
		sValue[0] = getResources().getString(R.string.select);
		for (int i = 0; i < Common.size(); i++) {
			sValue[i + 1] = Common.get(i).getValue();
		}
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
	}

	private int returnPos(int ID, int iFlag) {
		// TODO Auto-generated method stub
		int pos = 0;
		Common = dataProvider.getCommonRecord(1, iFlag);
		if (Common != null && Common.size() > 0) {
			for (int i = 0; i < Common.size(); i++) {
				if (Common.get(i).getID() == ID) {
					pos = i + 1;
				}
			}
		}
		return pos;
	}

	public int returnid(int POS, int iFlag, int Language) {
		Common = dataProvider.getCommonRecord(Language, iFlag);

		return Common.get(POS).getID();

	}

	public void CustomAlertSave2(String msg) {
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
				dialog.dismiss();

			}
		});

		// Display the dialog
		dialog.show();

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
				dialog.dismiss();

			}
		});

		// Display the dialog
		dialog.show();

	}

	public void Showdata() {
		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		record = dataProvider.getPregnantWomendata(global.getsGlobalPWGUID(),
				1, ashaid);
		if (record != null && record.size() > 0) {

			if (record.get(0).getDeliveryType() == 1) {
				radioLivebirth.setChecked(true);
				btnSave.setVisibility(View.VISIBLE);
			} else if (record.get(0).getDeliveryType() == 2) {
				radioStillbirth.setChecked(true);
				btnSave.setVisibility(View.VISIBLE);
				tbldatesb.setVisibility(View.VISIBLE);
				if (record.get(0).getDeliveryDateTime() != null
						&& record.get(0).getDeliveryDateTime().length() > 0) {
					etdatestillb.setText(Validate.changeDateFormat(record
							.get(0).getDeliveryDateTime()));
				}
			} else if (record.get(0).getDeliveryType() == 3) {
				radioNeonataldeath.setChecked(true);
				btnSave.setVisibility(View.VISIBLE);
				tbldatend.setVisibility(View.VISIBLE);
				if (record.get(0).getDeliveryDateTime() != null
						&& record.get(0).getDeliveryDateTime().length() > 0) {
					etdatend.setText(Validate.changeDateFormat(record.get(0)
							.getDeliveryDateTime()));
				}
				tblcause1.setVisibility(View.VISIBLE);
				if (record.get(0).getChildDeathCause() > 0) {
					spincause1.setSelection(returnPos(record.get(0)
							.getChildDeathCause(), 19));
				}
			}
			if (record.get(0).getISAbortion() > 0) {
				radioAbortion.setChecked(true);
				tblabortion.setVisibility(View.VISIBLE);
				tbldateabortion.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
				if (record.get(0).getDeliveryDateTime() != null
						&& record.get(0).getDeliveryDateTime().length() > 0) {
					etdateab.setText(Validate.changeDateFormat(record.get(0)
							.getDeliveryDateTime()));
				}

			}
			if (record.get(0).getAbortionFacilityType() == 1) {
				radioInduced.setChecked(true);
				tblabortion.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			} else if (record.get(0).getAbortionFacilityType() == 2) {
				radioSpontaneous.setChecked(true);
				tblabortion.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			}
			if (record.get(0).getMaternalDeath() == 1) {

				radioMaternaldeath.setChecked(true);
				tblAddchild.setVisibility(View.GONE);
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);

			}
			if (record.get(0).getPlaceofDeath() > 0) {
				spinplace.setSelection(returnPos(record.get(0)
						.getPlaceofDeath(), 18));

			}

			if (record.get(0).getMotherDeathCause() > 0) {
				spincause.setSelection(returnPos(record.get(0)
						.getMotherDeathCause(), 19));
				radioMaternaldeath.setChecked(true);
				tblAddchild.setVisibility(View.GONE);
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
				if (record.get(0).getChildDeathCause() > 0) {
					spincause.setSelection(returnPos(record.get(0)
							.getMotherDeathCause(), 19));
				}

			}
			if (record.get(0).getDateofDeath() != null
					&& record.get(0).getDateofDeath().length() > 0) {
				etdate.setText(Validate.changeDateFormat(record.get(0)
						.getDateofDeath()));
				radioMaternaldeath.setChecked(true);
				tblAddchild.setVisibility(View.GONE);
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			}

			if (record.get(0).getDeliveryType() == 1) {
				tblAddchild.setVisibility(View.VISIBLE);
			}
			if (record.get(0).getMotherDCOther() != null
					&& record.get(0).getMotherDCOther().length() > 0) {
				tblanyOthercause.setVisibility(View.VISIBLE);
				etAnyOthercause.setText(String.valueOf(record.get(0)
						.getMotherDCOther()));
			}
			if (record.get(0).getOtherPlaceofDeath() != null
					&& record.get(0).getOtherPlaceofDeath().length() > 0) {
				tblanyotherplace.setVisibility(View.VISIBLE);
				etAnyother.setText(String.valueOf(record.get(0)
						.getOtherPlaceofDeath()));
			}

		}
	}

	public void savedata() {
		int PlaceofDeath = 0;
		String DateofDeath = "";
		String OtherPlaceofDeath = "";
		int DeliveryType = 0;
		String DeliveryDateTime = "";
		int MotherDeathCause = 0;
		int ChildDeathCause = 0;
		int ISAbortion = 0;
		int AbortionFacilityType = 0;
		int MaternalDeath = 0;
		String MotherDCOther = "";
		int Abortion_FacilityName = 0;

		String PWGUID = global.getsGlobalPWGUID();
		if (spinplace.getSelectedItemPosition() > 0) {
			PlaceofDeath = returnid(spinplace.getSelectedItemPosition() - 1,
					18, global.getLanguage());
		}
		if (spincause.getSelectedItemPosition() > 0) {
			MotherDeathCause = returnid(
					spincause.getSelectedItemPosition() - 1, 19,
					global.getLanguage());
		}
		if (spincause1.getSelectedItemPosition() > 0) {
			ChildDeathCause = returnid(
					spincause1.getSelectedItemPosition() - 1, 19,
					global.getLanguage());
		}

		if (etdate.getText() != null
				&& etdate.getText().toString().length() > 0) {
			DateofDeath = Validate
					.changeDateFormat(etdate.getText().toString());
		}
		if (etAnyOthercause.getText() != null
				&& etAnyOthercause.getText().toString().length() > 0) {
			MotherDCOther = etAnyOthercause.getText().toString();
		}
		if (etAnyother.getText() != null
				&& etAnyother.getText().toString().length() > 0) {
			OtherPlaceofDeath = etAnyother.getText().toString();
		}
		if (etdateab.getText() != null
				&& etdateab.getText().toString().length() > 0) {
			DeliveryDateTime = Validate.changeDateFormat(etdateab.getText()
					.toString());
		}
		if (etdatestillb.getText() != null
				&& etdatestillb.getText().toString().length() > 0) {
			DeliveryDateTime = Validate.changeDateFormat(etdatestillb.getText()
					.toString());
		}
		if (etdatend.getText() != null
				&& etdatend.getText().toString().length() > 0) {
			DeliveryDateTime = Validate.changeDateFormat(etdatend.getText()
					.toString());
		}
		if (radioAbortion.isChecked()) {
			if (spin_abortionfacility.getSelectedItemPosition() > 0) {
				Abortion_FacilityName = returnid(
						spin_abortionfacility.getSelectedItemPosition() - 1,
						27, global.getLanguage());
			}
			ISAbortion = 1;
			if (etdateab.getText().toString().equalsIgnoreCase("null")
					|| etdateab.getText().toString().length() == 0) {
				DeliveryDateTime = Validate.getcurrentdate();
			}
			String sql1 = "update tblPregnant_woman set  IsPregnant=0,Abortion_FacilityName="
					+ Abortion_FacilityName + "  where PWGUID='" + PWGUID + "'";
			dataProvider.executeSql(sql1);
		}
		if (radioMaternaldeath.isChecked()) {
			MaternalDeath = 1;
			String sql1 = "update Tbl_HHFamilyMember set StatusID=2 where HHFamilyMemberGUID='"
					+ global.getGlobalHHFamilyMemberGUID() + "'";
			dataProvider.executeSql(sql1);
		}
		if (radioLivebirth.isChecked()) {
			DeliveryType = 1;
		}
		if (radioStillbirth.isChecked()) {
			DeliveryType = 2;

		}
		if (radioNeonataldeath.isChecked()) {
			DeliveryType = 3;
			String sql1 = "update tblPregnant_woman set  IsPregnant=0  where PWGUID='"
					+ PWGUID + "'";
			dataProvider.executeSql(sql1);
		}
		if (radioInduced.isChecked()) {
			DeliveryDateTime = Validate.getcurrentdate();
			AbortionFacilityType = 1;
		}
		if (radioSpontaneous.isChecked()) {
			DeliveryDateTime = Validate.getcurrentdate();
			AbortionFacilityType = 2;
		}
		dataProvider.saveDeliveryData(PWGUID, DeliveryDateTime, MaternalDeath,
				PlaceofDeath, DateofDeath, OtherPlaceofDeath, DeliveryType,
				MotherDeathCause, ISAbortion, AbortionFacilityType,
				ChildDeathCause, MotherDCOther);
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radioAbortion:
			if (checked)
				radioNeonataldeath.setChecked(false);
			radioMaternaldeath.setChecked(false);
			radioLivebirth.setChecked(false);
			radioStillbirth.setChecked(false);
			tblabortion.setVisibility(View.VISIBLE);
			tbl_abortionfacility.setVisibility(View.VISIBLE);
			tbldatend.setVisibility(View.GONE);
			etdateab.setText("");
			etdatend.setText("");
			etdatestillb.setText("");
			tblcause1.setVisibility(View.GONE);
			// tblDelivery.setVisibility(View.GONE);
			tbldateabortion.setVisibility(View.VISIBLE);
			tblAddchild.setVisibility(View.GONE);
			tbldate.setVisibility(View.GONE);
			tblplace.setVisibility(View.GONE);
			tblcause.setVisibility(View.GONE);
			btnSave.setVisibility(View.VISIBLE);
			tbldatesb.setVisibility(View.GONE);
			// Pirates are the best
			break;
		case R.id.radioLivebirth:
			if (checked)
				// radioMaternaldeath.setChecked(false);
				radioAbortion.setChecked(false);
			radioStillbirth.setChecked(false);
			radioNeonataldeath.setChecked(false);
			radioInduced.setChecked(false);
			radioSpontaneous.setChecked(false);
			tblabortion.setVisibility(View.GONE);
			tbl_abortionfacility.setVisibility(View.GONE);

			tbldateabortion.setVisibility(View.GONE);
			// tblDelivery.setVisibility(View.VISIBLE);

			tblAddchild.setVisibility(View.VISIBLE);
			tbldate.setVisibility(View.GONE);
			tblplace.setVisibility(View.GONE);
			tblcause.setVisibility(View.GONE);
			btnSave.setVisibility(View.VISIBLE);
			tbldatesb.setVisibility(View.GONE);
			tblcause1.setVisibility(View.GONE);
			tbldatend.setVisibility(View.GONE);
			if (radioMaternaldeath.isChecked()) {
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.radioStillbirth:
			if (checked)
				// radioMaternaldeath.setChecked(false);
				radioAbortion.setChecked(false);
			radioLivebirth.setChecked(false);
			radioNeonataldeath.setChecked(false);
			tblabortion.setVisibility(View.GONE);
			tbl_abortionfacility.setVisibility(View.GONE);

			tbldateabortion.setVisibility(View.GONE);
			tblcause1.setVisibility(View.GONE);
			// tblDelivery.setVisibility(View.GONE);
			radioInduced.setChecked(false);
			radioSpontaneous.setChecked(false);
			tbldatesb.setVisibility(View.VISIBLE);
			tblAddchild.setVisibility(View.GONE);
			tbldate.setVisibility(View.GONE);
			tblplace.setVisibility(View.GONE);
			tblcause.setVisibility(View.GONE);
			btnSave.setVisibility(View.VISIBLE);
			etdateab.setText("");
			etdatend.setText("");
			etdatestillb.setText("");
			// Ninjas rule
			tbldatend.setVisibility(View.GONE);
			if (radioMaternaldeath.isChecked()) {
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			}
			break;

		case R.id.radioMaternaldeath:
			if (checked)
				radioAbortion.setChecked(false);
			tbldatesb.setVisibility(View.GONE);
			tblabortion.setVisibility(View.GONE);
			tbl_abortionfacility.setVisibility(View.GONE);

			radioInduced.setChecked(false);
			radioSpontaneous.setChecked(false);
			// tblDelivery.setVisibility(View.GONE);
			tbldateabortion.setVisibility(View.GONE);
			// tblAddchild.setVisibility(View.GONE);
			// tblcause1.setVisibility(View.GONE);
			tbldate.setVisibility(View.VISIBLE);
			tblplace.setVisibility(View.VISIBLE);
			tblcause.setVisibility(View.VISIBLE);
			btnSave.setVisibility(View.VISIBLE);
			// Ninjas rule
			// tbldatend.setVisibility(View.GONE);
			break;
		case R.id.radioNeonataldeath:
			if (checked)
				// radioMaternaldeath.setChecked(false);
				radioAbortion.setChecked(false);
			radioLivebirth.setChecked(false);
			radioStillbirth.setChecked(false);
			radioInduced.setChecked(false);
			radioSpontaneous.setChecked(false);
			tblabortion.setVisibility(View.GONE);
			tbl_abortionfacility.setVisibility(View.GONE);

			tblcause1.setVisibility(View.VISIBLE);
			// tblDelivery.setVisibility(View.GONE);
			tbldateabortion.setVisibility(View.GONE);
			etdateab.setText("");
			etdatend.setText("");
			etdatestillb.setText("");
			tblAddchild.setVisibility(View.GONE);
			tbldate.setVisibility(View.GONE);
			tblplace.setVisibility(View.GONE);
			tblcause.setVisibility(View.GONE);
			btnSave.setVisibility(View.VISIBLE);
			// Ninjas rule
			tbldatend.setVisibility(View.VISIBLE);
			tbldatesb.setVisibility(View.GONE);
			if (radioMaternaldeath.isChecked()) {
				tbldate.setVisibility(View.VISIBLE);
				tblplace.setVisibility(View.VISIBLE);
				tblcause.setVisibility(View.VISIBLE);
				btnSave.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.radioInduced:
			if (checked)
				tv_place.setText(getResources().getString(
						R.string.placeofabortion));
			break;
		case R.id.radioSpontaneous:
			if (checked)
				tv_place.setText(getResources().getString(
						R.string.placeofcheckup));
			break;
		}
	}

	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			try {
				GoogleAnalytics.getInstance(Delivery.this).reportActivityStart(
						this);
			} catch (Exception e) {

			}
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			try {
				GoogleAnalytics.getInstance(Delivery.this).reportActivityStop(
						this);
			} catch (Exception e) {

			}
		}
	}

	public void ShowDatePicker(final TextView tVvisit) {

		// TODO Auto-generated method stub
		datepic = new Dialog(this, R.style.CustomTheme);

		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker);
		Button btnset = (Button) datepic.findViewById(R.id.set);
		// Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
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

		// btnclear.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated
		//
		// tVvisit.setText(Datenew);
		// datepic.dismiss();
		//
		// }
		// });
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

	// public void onDeliveryClicked(View view) {
	// // Is the button now checked?
	// boolean checked = ((RadioButton) view).isChecked();
	//
	// // Check which radio button was clicked
	// switch(view.getId()) {
	// case R.id.radiobs:
	// if (checked)
	//
	//
	// tblAddchild.setVisibility(View.VISIBLE);
	// tbldate.setVisibility(View.GONE);
	// tblplace.setVisibility(View.GONE);
	// tblcause.setVisibility(View.GONE);
	//
	// // Pirates are the best
	// break;
	// case R.id.radiomdcs:
	// if (checked)
	//
	//
	// tblAddchild.setVisibility(View.VISIBLE);
	// tbldate.setVisibility(View.VISIBLE);
	// tblplace.setVisibility(View.VISIBLE);
	// tblcause.setVisibility(View.VISIBLE);
	//
	//
	// break;
	// case R.id.radiomscd:
	// if (checked)
	//
	//
	// tblAddchild.setVisibility(View.GONE);
	// tbldate.setVisibility(View.GONE);
	// tblplace.setVisibility(View.GONE);
	// tblcause.setVisibility(View.GONE);
	//
	// // Ninjas rule
	// break;
	// }
	// }
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(Delivery.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}