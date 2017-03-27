package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint({ "SimpleDateFormat", "InflateParams" })
public class FamilyDetails extends Activity {

	Button btnSave, btnClose;
	EditText etUID, etFamilyMemb, etAgeYearsTillApril, etAgeMonthTillApril,
			etMotherName;
	Spinner SpinRelation, SpinGender, SpinEducation, SpinMaritalStatus,
			SpinTarget, SpinDOBAvailable, SpinFamilyMemberStatus;
	Global global;
	Dialog datepic;
	TextView tvDateOfBirth, tvUID, tvAgeasonYear;
	TableRow tblDOB, tblAgeYearsTillApril, tblAgeMonthTillApril,
			tbltvAgeasonYear;
	DataProvider dataProvider;
	ArrayAdapter<String> adapter;
	int statusid = 0;
	String Aadharold = "", DOB = "";
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
	ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.familydetails);

		btnSave = (Button) findViewById(R.id.btnSave);
		btnClose = (Button) findViewById(R.id.btnClose);
		etUID = (EditText) findViewById(R.id.etUID);
		etFamilyMemb = (EditText) findViewById(R.id.etFamilyMemb);
		tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
		SpinDOBAvailable = (Spinner) findViewById(R.id.SpinDOBAvailable);
		SpinFamilyMemberStatus = (Spinner) findViewById(R.id.SpinFamilyMemberStatus);
		SpinEducation = (Spinner) findViewById(R.id.SpinEducation);
		tvAgeasonYear = (TextView) findViewById(R.id.tvAgeasonYear);
		tvUID = (TextView) findViewById(R.id.tvUID);
		tblDOB = (TableRow) findViewById(R.id.tblDOB);
		tblAgeYearsTillApril = (TableRow) findViewById(R.id.tblAgeYearsTillApril);
		tblAgeMonthTillApril = (TableRow) findViewById(R.id.tblAgeMonthTillApril);
		tbltvAgeasonYear = (TableRow) findViewById(R.id.tbltvAgeasonYear);
		final Calendar c = Calendar.getInstance();
		int mTMYear = c.get(Calendar.YEAR);
		tvAgeasonYear.setText(String.valueOf(mTMYear));
		// tvAgeasonYear.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ShowDatePicker1(tvAgeasonYear);
		// }
		// });
		tvDateOfBirth.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvDateOfBirth);
			}
		});

		etAgeYearsTillApril = (EditText) findViewById(R.id.etAgeYearsTillApril);
		etAgeMonthTillApril = (EditText) findViewById(R.id.etAgeMonthTillApril);
		etMotherName = (EditText) findViewById(R.id.etMotherName);
		SpinRelation = (Spinner) findViewById(R.id.SpinRelation);
		SpinGender = (Spinner) findViewById(R.id.SpinGender);
		SpinMaritalStatus = (Spinner) findViewById(R.id.SpinMaritalStatus);
		SpinTarget = (Spinner) findViewById(R.id.SpinTarget);
		dataProvider = new DataProvider(this);
		etAgeMonthTillApril.addTextChangedListener(new TextWatcher() {

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
				if (s.length() > 0) {
					int monthage = Integer.valueOf(etAgeMonthTillApril
							.getText().toString());
					if (monthage > 12) {
						etAgeMonthTillApril.setText("");
					}
				}
			}
		});
		etAgeYearsTillApril.addTextChangedListener(new TextWatcher() {

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
				if (s != null && s.length() > 0) {
					int age = Integer.valueOf(etAgeYearsTillApril.getText()
							.toString());
					if (age < 12) {
						tblAgeMonthTillApril.setVisibility(View.VISIBLE);
					} else {
						tblAgeMonthTillApril.setVisibility(View.GONE);
						etAgeMonthTillApril.setText("");
					}
				}
			}
		});
		global = (Global) this.getApplicationContext();
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Family Details");
		String dimensionValue = "5";
		if (global.getsGlobalUserName() != null
				&& global.getsGlobalUserName().length() > 0) {
			dimensionValue = global.getsGlobalUserName();
		}

		t.set("&userid", dimensionValue);
		t.enableAutoActivityTracking(true);

		t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
				.setAction("User Sign In").setNewSession()
				.setCustomDimension(1, dimensionValue).build());
		t.send(new HitBuilders.ScreenViewBuilder().build());
		btnSave.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int icheck = sCheckValidation();
				if (icheck == 1) {
					CustomAlertSave("Do You Want To Save?");

				} else {
					showAlert(icheck);
				}

			}

		});

		btnClose.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		tvAgeasonYear.addTextChangedListener(new TextWatcher() {

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
				if (s.length() == 4) {
					int year = Integer.valueOf(s.toString());
					final Calendar c = Calendar.getInstance();
					int mTMYear = c.get(Calendar.YEAR);
					if (year > mTMYear) {
						CustomAlert(getResources()
								.getString(R.string.validdate));
						tvAgeasonYear.setText("2017");
					}

				}
			}
		});

		fillCommonRecord(SpinRelation, 6, global.getLanguage());
		fillCommonRecord(SpinGender, 4, global.getLanguage());
		fillCommonRecord(SpinFamilyMemberStatus, 9, global.getLanguage());
		fillCommonRecord(SpinMaritalStatus, 5, global.getLanguage());
		fillCommonRecord(SpinTarget, 8, global.getLanguage());
		fillCommonRecord(SpinDOBAvailable, 7, global.getLanguage());
		fillCommonRecord(SpinEducation, 104, global.getLanguage());

		SpinFamilyMemberStatus.setSelection(1);
		SpinFamilyMemberStatus.setEnabled(false);

		SpinDOBAvailable
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> parent,
							View view, int pos, long id) {
						if (pos == 1) {

							tblDOB.setVisibility(View.VISIBLE);
							tbltvAgeasonYear.setVisibility(View.GONE);
							tvAgeasonYear.setText("");
							tblAgeYearsTillApril.setVisibility(View.GONE);
							etAgeYearsTillApril.setText("");
							tblAgeMonthTillApril.setVisibility(View.GONE);
							etAgeMonthTillApril.setText("");

						} else if (pos == 2) {
							tblDOB.setVisibility(View.GONE);
							tvDateOfBirth.setText("");
							tblAgeYearsTillApril.setVisibility(View.VISIBLE);
							tbltvAgeasonYear.setVisibility(View.VISIBLE);
							final Calendar c = Calendar.getInstance();
							int mTMYear = c.get(Calendar.YEAR);
							tvAgeasonYear.setText(String.valueOf(mTMYear));
							// tblAgeMonthTillApril.setVisibility(View.VISIBLE);
						} else {
							tblDOB.setVisibility(View.GONE);
							tvDateOfBirth.setText("");
							tbltvAgeasonYear.setVisibility(View.GONE);
							tvAgeasonYear.setText("");
							tblAgeYearsTillApril.setVisibility(View.GONE);
							etAgeYearsTillApril.setText("");
							tblAgeMonthTillApril.setVisibility(View.GONE);
							etAgeMonthTillApril.setText("");
						}

					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
		tvUID.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					// start the scanning activity from the
					// com.google.zxing.client.android.SCAN intent
					Intent intent = new Intent(ACTION_SCAN);
					intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intent, 0);
				} catch (ActivityNotFoundException anfe) {
					// on catch, show the download dialog
					showDialog(FamilyDetails.this, "No Scanner Found",
							"Download a scanner code activity?", "Yes", "No")
							.show();
				}
			}
		});
		if (global.getGlobalHHFamilyMemberGUID() != null
				&& global.getGlobalHHFamilyMemberGUID().length() > 0) {
			filldata();
		}

	}

	public void filldata() {
		HHFamilyMember = dataProvider.getHHFamilymemberData(
				global.getGlobalHHSurveyGUID(),
				global.getGlobalHHFamilyMemberGUID(), 2);
		if (HHFamilyMember != null && HHFamilyMember.size() > 0) {
			etUID.setText(String.valueOf(HHFamilyMember.get(0)
					.getUniqueIDNumber()));
			etFamilyMemb.setText(String.valueOf(HHFamilyMember.get(0)
					.getFamilyMemberName()));
			etAgeYearsTillApril.setText(String.valueOf(HHFamilyMember.get(0)
					.getAprilAgeYear()));
			etAgeMonthTillApril.setText(String.valueOf(HHFamilyMember.get(0)
					.getAprilAgeMonth()));
			etMotherName.setText(String.valueOf(HHFamilyMember.get(0)
					.getMotherGUID()));
			tvDateOfBirth.setText(Validate.changeDateFormat(String
					.valueOf(HHFamilyMember.get(0).getDateOfBirth())));
			tvAgeasonYear.setText(String.valueOf(HHFamilyMember.get(0)
					.getAgeAsOnYear()));
			SpinRelation.setSelection(returnPos(HHFamilyMember.get(0)
					.getRelationID(), 6));
			SpinGender.setSelection(returnPos(HHFamilyMember.get(0)
					.getGenderID(), 4));
			SpinMaritalStatus.setSelection(returnPos(HHFamilyMember.get(0)
					.getMaritialStatusID(), 5));
			SpinEducation.setSelection(returnPos(HHFamilyMember.get(0)
					.getEducation(), 104));
			SpinTarget.setSelection(returnPos(HHFamilyMember.get(0)
					.getTargetID(), 8));
			SpinDOBAvailable.setSelection(returnPos(HHFamilyMember.get(0)
					.getDOBAvailable(), 7));
			SpinFamilyMemberStatus.setSelection(returnPos(HHFamilyMember.get(0)
					.getStatusID(), 9));
			statusid = returnPos(HHFamilyMember.get(0).getStatusID(), 9);
			Aadharold = String.valueOf(HHFamilyMember.get(0)
					.getUniqueIDNumber());
			DOB = String.valueOf(HHFamilyMember.get(0).getDateOfBirth());
		}
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

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);
		// menu.add(0, 1, 0, "History").setIcon(R.drawable.logout1)
		// .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//

		return true;
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

	public int returnid(int POS, int iFlag, int Language) {
		Common = dataProvider.getCommonRecord(Language, iFlag);

		return Common.get(POS).getID();

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

	public void SaveFamilyDetails() {
		String UID = "";
		String FamilyMemb = "";
		String DateOfBirth = "";
		int DOBAvailable = 0;
		int AgeYearTillApril = 0;
		int AgeMonthTillApril = 0;
		int AgeAsOnYear = 0;
		String MotherName = "";
		int Relationid = 0;
		int Genderid = 0;
		int MaritalStatusid = 0;
		int Targetid = 0;
		String UpdatedOn = "";
		int Education = 0;
		final Calendar c = Calendar.getInstance();
		int mTMYear = c.get(Calendar.YEAR);
		int mTMMonth = c.get(Calendar.MONTH) + 1;
		int mTMDay = c.get(Calendar.DAY_OF_MONTH);
		UpdatedOn = String.valueOf(mTMDay) + "-" + String.valueOf(mTMMonth)
				+ "-" + String.valueOf(mTMYear);
		int FamilyMemberStatus = 0;
		String CurrentDate = "";
		int AshaID = 0;
		int ANMID = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		}
		if (global.getsGlobalANMCODE() != null
				&& global.getsGlobalANMCODE().length() > 0) {
			ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		}
		CurrentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		if (etUID.getText().toString().length() > 0) {
			UID = etUID.getText().toString();
		}
		if (etFamilyMemb.getText().length() > 0) {
			FamilyMemb = String.valueOf(etFamilyMemb.getText());
		}
		if (tvDateOfBirth.getText().toString().length() > 0) {
			DateOfBirth = Validate.changeDateFormat(tvDateOfBirth.getText()
					.toString());
		}
		if (etAgeYearsTillApril.getText().toString().length() > 0) {
			AgeYearTillApril = Integer.valueOf(etAgeYearsTillApril.getText()
					.toString());
		}
		if (etAgeMonthTillApril.getText().toString().length() > 0) {
			AgeMonthTillApril = Integer.valueOf(etAgeMonthTillApril.getText()
					.toString());
		}
		if (tvAgeasonYear.getText().toString().length() > 0) {
			AgeAsOnYear = Integer.valueOf(tvAgeasonYear.getText().toString());
		}
		if (etMotherName.getText().length() > 0) {
			MotherName = String.valueOf(etMotherName.getText());
		}

		if (SpinRelation.getSelectedItemPosition() > 0) {
			Relationid = returnid(SpinRelation.getSelectedItemPosition() - 1,
					6, global.getLanguage());
		}
		if (SpinGender.getSelectedItemPosition() > 0) {
			Genderid = returnid(SpinGender.getSelectedItemPosition() - 1, 4,
					global.getLanguage());
		}
		if (SpinDOBAvailable.getSelectedItemPosition() > 0) {
			DOBAvailable = returnid(
					SpinDOBAvailable.getSelectedItemPosition() - 1, 7,
					global.getLanguage());
		}

		if (SpinEducation.getSelectedItemPosition() > 0) {
			Education = returnid(SpinEducation.getSelectedItemPosition() - 1,
					104, global.getLanguage());
		}

		if (SpinFamilyMemberStatus.getSelectedItemPosition() > 0) {
			FamilyMemberStatus = returnid(
					SpinFamilyMemberStatus.getSelectedItemPosition() - 1, 9,
					global.getLanguage());
		}
		if (SpinMaritalStatus.getSelectedItemPosition() > 0) {
			MaritalStatusid = returnid(
					SpinMaritalStatus.getSelectedItemPosition() - 1, 5,
					global.getLanguage());
		}
		if (SpinTarget.getSelectedItemPosition() > 0) {
			Targetid = returnid(SpinTarget.getSelectedItemPosition() - 1, 8,
					global.getLanguage());
		}
		Calendar c1 = Calendar.getInstance();
		if (global.getGlobalHHSurveyGUID() != null
				&& global.getGlobalHHSurveyGUID().length() > 0) {
			if (global.getGlobalHHFamilyMemberGUID().length() == 0) {
				String FamilyGUID = Validate.random();
				dataProvider.InsertFamilyDetails(Education, AshaID, ANMID,
						CurrentDate, UID, FamilyMemb, DOBAvailable,
						DateOfBirth, AgeYearTillApril, AgeMonthTillApril,
						AgeAsOnYear, MotherName, Relationid,
						FamilyMemberStatus, Genderid, MaritalStatusid,
						Targetid, UpdatedOn, global.getGlobalHHSurveyGUID(),
						FamilyGUID, global.getsGlobalUserID());
				// global.setGlobalHHFamilyMemberGUID(FamilyGUID);
			} else {
				dataProvider.UpdateFamilyDetails(Education, AshaID, ANMID,
						CurrentDate, UID, FamilyMemb, DOBAvailable,
						DateOfBirth, AgeYearTillApril, AgeMonthTillApril,
						AgeAsOnYear, MotherName, Relationid,
						FamilyMemberStatus, Genderid, MaritalStatusid,
						Targetid, UpdatedOn, global.getGlobalHHSurveyGUID(),
						global.getGlobalHHFamilyMemberGUID(),
						global.getsGlobalUserID());
			}

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formattedDate = df.format(c1.getTime());
			String sql = "insert into tblhhupdate_Log(hhsurveyguid,hhmemberguid,UserID,StatusId_Old,StatusId_New,Aadhar_Old,Aadhar_New,DOB_Old,DOB_New,UpdatedOn,IsEdited)values('"
					+ global.getGlobalHHSurveyGUID()
					+ "','"
					+ global.getGlobalHHFamilyMemberGUID()
					+ "',"
					+ global.getsGlobalUserID()
					+ ","
					+ statusid
					+ ","
					+ FamilyMemberStatus
					+ ",'"
					+ Aadharold
					+ "','"
					+ UID
					+ "','"
					+ DOB
					+ "','"
					+ DateOfBirth
					+ "','"
					+ formattedDate
					+ "',1)";

			dataProvider.executeSql(sql);
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

	public void CustomAlerts(String msg) {
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

				finish();

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
		View view = inflater.inflate(R.layout.dialog_checklayout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
		Button btnno = (Button) dialog.findViewById(R.id.btn_no);

		btnyes.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {

				SaveFamilyDetails();
				CustomAlerts(getResources()
						.getString(R.string.savesuccessfully));

			}
		});

		btnno.setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View v) {

				dialog.dismiss();

			}
		});

		// Display the dialog
		dialog.show();

	}

	public int sCheckValidation() {
		int age = 0;
		if (etAgeYearsTillApril.getText() != null
				&& etAgeYearsTillApril.getText().length() > 0) {
			age = Integer.valueOf(etAgeYearsTillApril.getText().toString());
		}
		if (tblDOB.getVisibility() == View.VISIBLE
				&& tvDateOfBirth.getText().toString().length() == 0) {
			return 2;
		} else if (tblAgeYearsTillApril.getVisibility() == View.VISIBLE
				&& etAgeYearsTillApril.getText().length() == 0) {

			return 4;
		} else if (tbltvAgeasonYear.getVisibility() == View.VISIBLE
				&& tvAgeasonYear.getText().toString().length() == 0) {
			return 5;
		} else if (etUID.getText().toString() != null
				&& etUID.getText().toString().length() > 0
				&& etUID.getText().toString().length() != 12) {
			return 6;
		} else if (age < 12
				&& tblAgeMonthTillApril.getVisibility() == View.VISIBLE
				&& etAgeMonthTillApril.getText().length() == 0) {
			return 7;
		}
		return 1;

	}

	public void showAlert(int iCheck) {
		if (iCheck == 2) {
			CustomAlert(getResources().getString(R.string.enterdob));
		} else if (iCheck == 4) {
			CustomAlert(getResources().getString(R.string.dateenterage));
		} else if (iCheck == 5) {
			CustomAlert(getResources().getString(R.string.enterage));
		} else if (iCheck == 6) {
			CustomAlert(getResources().getString(R.string.enter12digituid));
		} else if (iCheck == 7) {
			CustomAlert(getResources().getString(R.string.dateenteragemonth));
		}
	}

	public void ShowDatePicker1(final TextView txt) {
		datepic = new Dialog(this, R.style.CustomTheme);
		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker);
		datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);

		Button btnset = (Button) datepic.findViewById(R.id.set);
		Button btnCancle = (Button) datepic.findViewById(R.id.cancle);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		Date date;
		Calendar c = Calendar.getInstance(Locale.ENGLISH);
		btnCancle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated

				datepic.dismiss();

			}
		});
		final DatePicker datetext = (DatePicker) datepic
				.findViewById(R.id.idfordate);
		if (txt != null && txt.getText() != ""
				&& txt.getText().toString().length() > 0) {

			try {
				datetext.clearFocus();
				date = sdf.parse(txt.getText().toString());
				c.setTime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// set current date into datepicker
			datetext.init(year, month, day, null);

		}
		btnset.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				datetext.clearFocus();
				int Mdate = datetext.getDayOfMonth();

				int month = datetext.getMonth();
				int year = datetext.getYear();
				Date date = null;
				String sSellectedDate = String.valueOf(year);
				txt.setText(sSellectedDate);

				// TODO Auto-generated

				datepic.dismiss();

			}
		});
		datepic.show();

	}

	// alert dialog for downloadDialog
	private static AlertDialog showDialog(final Activity act,
			CharSequence title, CharSequence message, CharSequence buttonYes,
			CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
						Uri uri = Uri.parse("market://search?q=pname:"
								+ "com.google.zxing.client.android");
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						try {
							act.startActivity(intent);
						} catch (ActivityNotFoundException anfe) {

						}
					}
				});
		downloadDialog.setNegativeButton(buttonNo,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface, int i) {
					}
				});
		return downloadDialog.show();
	}

	// on ActivityResult method
	@SuppressWarnings("unused")
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				// get the extras that are returned from the intent
				String UID = "";
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				etUID.setText(show(contents));
			}
		}
	}

	private String show(String XMLData) {
		String Value1 = "";
		try {
			String Value = "";

			if (XMLData.length() > 0) {
				String[] Cdt = XMLData.split(" ");
				for (int i = 0; i < Cdt.length; i++) {
					if (Cdt[i].contains("uid")) {
						Value = Cdt[i];
						String[] Cdt1 = Value.split("=");
						Value1 = Cdt1[1].replace("\"", "");
						break;
					}
					// String date = YYYY + "-" + MM + "-" + DD ;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return Value1;

	}
}
