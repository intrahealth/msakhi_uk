package com.microware.intrahealth;

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
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblChild;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("InflateParams")
public class NewChildActivity extends Activity {
	Button btnSave;
	Dialog datepic;
	DataProvider dataProvider;
	Global global;
	TableRow tblfacilitytype, tblfacility, tblBcg, tblOpv, tblHep;
	TextView tvDateOfBirth;
	Spinner spinChildGender, spinBirthPlace, spinfacility, spinVaccination;
	EditText etMothername, etChildName, etWeightChild, etfacility;
	CheckBox checkBcgYes, checkBcgNo, checkOpvYes, checkOpvNo, checkHepYes,
			checkHepNo;
	ImageView btnadd, btnminus;
	ArrayAdapter<String> adapternew;
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	ArrayList<tblChild> Child = new ArrayList<tblChild>();
	ArrayList<Tbl_HHFamilyMember> mother = new ArrayList<Tbl_HHFamilyMember>();
	int form = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.newchild);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		btnSave = (Button) findViewById(R.id.btnSave);
		btnadd = (ImageView) findViewById(R.id.btnadd);
		btnminus = (ImageView) findViewById(R.id.btnminus);
		spinfacility = (Spinner) findViewById(R.id.spinfacility);
		etfacility = (EditText) findViewById(R.id.etfacility);
		etMothername = (EditText) findViewById(R.id.etMothername);
		etWeightChild = (EditText) findViewById(R.id.etWeightChild);
		etChildName = (EditText) findViewById(R.id.etChildName);
		tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
		spinBirthPlace = (Spinner) findViewById(R.id.spinBirthPlace);
		spinChildGender = (Spinner) findViewById(R.id.spinChildGender);
		spinVaccination = (Spinner) findViewById(R.id.spinVaccination);

		tblfacilitytype = (TableRow) findViewById(R.id.tblfacilitytype);
		tblfacility = (TableRow) findViewById(R.id.tblfacility);
		tblBcg = (TableRow) findViewById(R.id.tblBcg);
		tblOpv = (TableRow) findViewById(R.id.tblOpv);
		tblHep = (TableRow) findViewById(R.id.tblHep);
		checkBcgYes = (CheckBox) findViewById(R.id.checkBcgYes);
		checkBcgNo = (CheckBox) findViewById(R.id.checkBcgNo);
		checkOpvYes = (CheckBox) findViewById(R.id.checkOpvYes);
		checkOpvNo = (CheckBox) findViewById(R.id.checkOpvNo);
		checkHepYes = (CheckBox) findViewById(R.id.checkHepYes);
		checkHepNo = (CheckBox) findViewById(R.id.checkHepNo);
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("New Child Registration");
		t.send(new HitBuilders.AppViewBuilder().build());
		tvDateOfBirth.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvDateOfBirth);
			}
		});
		etWeightChild.setText("0");
		fillCommonRecord(spinBirthPlace, 12);
		fillCommonRecord(spinChildGender, 26);
		fillCommonRecord(spinfacility, 27);
		fillCommonRecord(spinVaccination, 7);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int icheck = sCheckValidation();
				if (icheck == 1) {
					if (global.getsGlobalChildGUID() != null
							&& global.getsGlobalChildGUID().length() > 0) {
						String sql = "Select Child_dob from tblChild where ChildGUID ='"
								+ global.getsGlobalChildGUID() + "'";
						final String dob = dataProvider.getRecord(sql);
						if (!dob.equalsIgnoreCase(Validate
								.changeDateFormat(tvDateOfBirth.getText()
										.toString()))) {
							CustomAlert1();

						} else {
							save();

							String msg = getResources().getString(
									R.string.successfully);
							CustomAlertSave(msg, btnSave);
						}
					} else {
						save();

						String msg = getResources().getString(
								R.string.successfully);
						CustomAlertSave(msg, btnSave);
					}
				} else {
					showAlert(icheck);
				}
			}
		});
		if (global.getsGlobalWomenName() != null
				&& global.getsGlobalWomenName().length() > 0) {
			etMothername.setText(global.getsGlobalWomenName());
		}
		if (global.getGlobalHHFamilyMemberGUID() != null
				&& global.getGlobalHHFamilyMemberGUID().length() > 0) {
			setMothername();
		}
		if (global.getsGlobalChildGUID() != null
				&& global.getsGlobalChildGUID().length() > 0) {
			showdata();

		}
		spinfacility.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					if (returnid(position - 1, 27, global.getLanguage()) == 9) {
						tblfacility.setVisibility(View.VISIBLE);
					} else {
						tblfacility.setVisibility(View.GONE);
						etfacility.setText("");
					}
				} else {
					tblfacility.setVisibility(View.GONE);
					etfacility.setText("");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// btnadd.setOnTouchListener(new OnTouchListener() {
		//
		//
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		//
		//
		// return false;
		// }
		// });
		etWeightChild.addTextChangedListener(new TextWatcher() {

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
				if (etWeightChild.getText().length() > 0
						&& !etWeightChild.getText().toString()
								.equalsIgnoreCase(".")) {
					float weight = Float.valueOf(etWeightChild.getText()
							.toString());
					if (weight > 5) {
						etWeightChild.setText("5");
					}
				}
			}
		});
		btnadd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etWeightChild.getText().length() > 0
						&& !etWeightChild.getText().toString()
								.equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild.getText()
							.toString());

					etWeightChild.setText(String.valueOf(value + .10));
				} else {
					etWeightChild.setText(String.valueOf(0 + .10));
				}
			}
		});
		btnminus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etWeightChild.getText().length() > 0
						&& !etWeightChild.getText().toString()
								.equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild.getText()
							.toString());
					if (value > 0) {
						double w = value - .10;

						etWeightChild.setText(String.valueOf(w));
						// do what youwanna do on long press here
					}
				}
			}
		});
		spinBirthPlace.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
					if (returnid(position - 1, 12, global.getLanguage()) == 2) {
						tblfacility.setVisibility(View.GONE);
						tblfacilitytype.setVisibility(View.VISIBLE);
					} else {
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
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		spinVaccination.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				if (position == 1) {
					tblBcg.setVisibility(View.VISIBLE);
					tblOpv.setVisibility(View.VISIBLE);
					tblHep.setVisibility(View.VISIBLE);
				} else {
					tblBcg.setVisibility(View.GONE);
					tblOpv.setVisibility(View.GONE);
					tblHep.setVisibility(View.GONE);
					checkBcgYes.setChecked(false);
					checkBcgNo.setChecked(false);
					checkOpvYes.setChecked(false);
					checkOpvNo.setChecked(false);
					checkHepYes.setChecked(false);
					checkHepNo.setChecked(false);
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void setCheckToCheckBoxGroupBcg1(View view) {
		switch (view.getId()) {
		case R.id.checkBcgYes:
			checkBcgNo.setChecked(false);
			break;
		case R.id.checkBcgNo:
			checkBcgYes.setChecked(false);
			break;
		case R.id.checkOpvYes:
			checkOpvNo.setChecked(false);
			break;
		case R.id.checkOpvNo:
			checkOpvYes.setChecked(false);
			break;
		case R.id.checkHepYes:
			checkHepNo.setChecked(false);
			break;
		case R.id.checkHepNo:
			checkHepYes.setChecked(false);
			break;
		default:
			break;
		}
	}

	public int sCheckValidation() {
		float weight = 0;
		if (etWeightChild.getText().toString() != null
				&& etWeightChild.getText().toString().length() != 0) {
			weight = Float.valueOf(etWeightChild.getText().toString());
		}
		if (tvDateOfBirth.getText() != null
				&& tvDateOfBirth.getText().toString().length() == 0) {

			return 2;

		} else if (spinBirthPlace.getSelectedItemPosition() == 0) {
			return 3;
		} else if (etWeightChild.getText() == null
				|| etWeightChild.getText().toString().length() == 0
				|| weight < 1) {
			return 4;
		} else if (spinChildGender.getSelectedItemPosition() == 0) {
			return 5;
		}
		return 1;
	}

	public void showAlert(int iCheck) {
		if (iCheck == 2) {

			CustomAlertSave2(getResources().getString(R.string.dateenter));
		} else if (iCheck == 3) {
			CustomAlertSave2(getResources().getString(R.string.placeenter));
		} else if (iCheck == 4) {
			CustomAlertSave2(getResources().getString(R.string.weightenter));
		} else if (iCheck == 5) {
			CustomAlertSave2(getResources().getString(R.string.genderenter));
		}
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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(NewChildActivity.this).reportActivityStart(
				this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(NewChildActivity.this).reportActivityStop(
				this);
	}

	public void setMothername() {
		mother = dataProvider.getAllmalename(global
				.getGlobalHHFamilyMemberGUID());
		if (mother.size() > 0) {
			if (mother.get(0).getFamilyMemberName() != null
					&& mother.get(0).getFamilyMemberName().length() > 0) {
				etMothername.setText(mother.get(0).getFamilyMemberName());
			}
		}
	}

	public void CustomAlertSave(String msg, final TextView form) {

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

				Intent i = new Intent(NewChildActivity.this, newbornGrid.class);
				finish();
				startActivity(i);

			}
		});

		// Display the dialog
		dialog.show();

	}

	@SuppressWarnings("unused")
	public void save() {
		String pw_GUID = "";
		String HHGUID = "";
		String HHFamilyMemberGUID = "";
		// int Child_ID = 0;
		String ChildGUID = "";
		String MotherGUID = "";
		String Date_Of_Registration = Validate.getcurrentdate();
		String Child_dob = "";
		String Birth_time = "";
		int Gender = 0;
		float Wt_of_child = 0;
		int place_of_birth = 0;
		String child_name = "";
		int FacilityType = 0;
		String Facility = "";
		String created_on = Validate.getcurrentdate();
		int created_by = Integer.valueOf(global.getsGlobalUserID());
		String modified_on = Validate.getcurrentdate();
		int modified_by = Integer.valueOf(global.getsGlobalUserID());
		int AshaID = 0;
		int ANMID = 0;
		// add jitendra
		String bcg = "";
		String opv1 = "";
		// String dpt1 = "";
		String hepb1 = "";

		// add field
		final String dpt1 = "";
		final String opv2 = "";
		final String dpt2 = "";
		final String hepb2 = "";
		final String opv3 = "";
		final String dpt3 = "";
		final String hepb3 = "";
		final String measeals = "";
		final String vitaminA = "";
		final String opv4 = "";
		final String hepb4 = "";
		final String Pentavalent1 = "";
		final String Pentavalent2 = "";
		final String Pentavalent3 = "";
		final String IPV = "";
		final String DPTBooster = "";
		final String OPVBooster = "";
		final String MeaslesTwoDose = "";
		final String VitaminAtwo = "";
		final String DPTBoostertwo = "";
		final String ChildTT = "";
		final String JEVaccine1 = "";
		final String JEVaccine2 = "";
		final String VitaminA3 = "";
		final String VitaminA4 = "";
		final String VitaminA5 = "";
		final String VitaminA6 = "";
		final String VitaminA7 = "";
		final String VitaminA8 = "";
		final String VitaminA9 = "";
		final String TT2 = "";
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		}
		if (global.getsGlobalANMCODE() != null
				&& global.getsGlobalANMCODE().length() > 0) {
			ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		}
		if (global.getsGlobalPWGUID() != null
				&& global.getsGlobalPWGUID().length() > 0) {
			pw_GUID = global.getsGlobalPWGUID();
		}
		if (global.getGlobalHHSurveyGUID() != null
				&& global.getGlobalHHSurveyGUID().length() > 0) {
			HHGUID = global.getGlobalHHSurveyGUID();
		}
		if (global.getGlobalHHFamilyMemberGUID() != null
				&& global.getGlobalHHFamilyMemberGUID().length() > 0) {
			HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
			MotherGUID = global.getGlobalHHFamilyMemberGUID();
		}
		if (etWeightChild.getText().toString().length() > 0) {
			Wt_of_child = Float.valueOf(etWeightChild.getText().toString());
		}
		if (etChildName.getText().toString().length() > 0) {
			child_name = etChildName.getText().toString();
		}

		if (tvDateOfBirth.getText().toString().length() > 0) {
			Child_dob = tvDateOfBirth.getText().toString();
		}
		if (etfacility.getText().toString().length() > 0) {
			Facility = etfacility.getText().toString();
		}
		if (spinfacility.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 27);
			FacilityType = Common.get(
					spinBirthPlace.getSelectedItemPosition() - 1).getID();
		}
		if (spinBirthPlace.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(1, 12);
			place_of_birth = Common.get(
					spinBirthPlace.getSelectedItemPosition() - 1).getID();
		}
		if (spinChildGender.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(1, 26);
			Gender = Common.get(spinChildGender.getSelectedItemPosition() - 1)
					.getID();
		}
		if (checkBcgYes.isChecked()) {
			bcg = Validate.changeDateFormat(tvDateOfBirth.getText().toString());
		} else {
			bcg = "";
		}
		if (checkOpvYes.isChecked()) {
			opv1 = Validate
					.changeDateFormat(tvDateOfBirth.getText().toString());
		} else {
			opv1 = "";
		}
		if (checkHepYes.isChecked()) {
			hepb1 = Validate.changeDateFormat(tvDateOfBirth.getText()
					.toString());
		} else {
			hepb1 = "";
		}
		String flag = "I";
		ChildGUID = Validate.random();
		if (global.getsGlobalChildGUID() != null
				&& global.getsGlobalChildGUID().length() > 0) {
			String familysql = "select count(*) from Tbl_HHFamilyMember where HHSurveyGUID = '"
					+ HHGUID
					+ "' and HHFamilyMemberGUID='"
					+ global.getsGlobalChildGUID() + "'";
			int familycount = 0;
			familycount = dataProvider.getMaxRecord(familysql);
			if (familycount > 0) {
				flag = "U";
			}

			dataProvider.SavetblChildnew("U", AshaID, ANMID,
					global.getsGlobalChildGUID(), pw_GUID, HHGUID,
					HHFamilyMemberGUID, MotherGUID, Date_Of_Registration,
					Validate.changeDateFormat(Child_dob), Gender, Wt_of_child,
					place_of_birth, child_name, created_on, created_by,
					modified_on, modified_by, FacilityType, Facility, bcg,
					opv1, hepb1);
			dataProvider.InsertFamilymemberDetails(AshaID, ANMID,
					"111111111111", child_name, 1,
					Validate.changeDateFormat(Child_dob), HHFamilyMemberGUID,
					1, gender(Gender), 2, 1, HHGUID, global.getsGlobalChildGUID(), created_by,
					flag,MotherGUID);

		} else {
			dataProvider.SavetblChildnew("I", AshaID, ANMID, ChildGUID,
					pw_GUID, HHGUID, HHFamilyMemberGUID, MotherGUID,
					Date_Of_Registration, Validate.changeDateFormat(Child_dob),
					Gender, Wt_of_child, place_of_birth, child_name,
					created_on, created_by, modified_on, modified_by,
					FacilityType, Facility, bcg, opv1, hepb1);
			dataProvider.InsertFamilymemberDetails(AshaID, ANMID,
					"111111111111", child_name, 1,
					Validate.changeDateFormat(Child_dob), HHFamilyMemberGUID,
					1, gender(Gender), 2, 1, HHGUID, ChildGUID, created_by,
					flag,MotherGUID);
		}
	}

	public void showdata() {

		Child = dataProvider.gettblChild("",global.getsGlobalChildGUID(), 1,0);
		if (Child != null && Child.size() > 0) {

			if (Child.get(0).getWt_of_child() != 0
					&& Child.get(0).getWt_of_child() > 0) {
				etWeightChild.setText(String.valueOf(Child.get(0)
						.getWt_of_child()));
			} else {
				etWeightChild.setText("");
			}
			if (Child.get(0).getHHFamilyMemberGUID() != null
					&& Child.get(0).getHHFamilyMemberGUID().length() > 0) {

				String Sql1 = "Select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID = '"
						+ Child.get(0).getHHFamilyMemberGUID() + "'";
				String mothername = "";
				mothername = dataProvider.getRecord(Sql1);
				etMothername.setText(mothername);
			} else {
				etMothername.setText("");
			}
			if (Child.get(0).getChild_name() != null
					&& Child.get(0).getChild_name().length() > 0) {
				etChildName.setText(String
						.valueOf(Child.get(0).getChild_name()));
			} else {
				etChildName.setText("");
			}
			if (Child.get(0).getChild_dob() != null
					&& Child.get(0).getChild_dob().length() > 0) {
				tvDateOfBirth.setText(String.valueOf(Validate
						.changeDateFormat(Child.get(0).getChild_dob())));
			} else {
				tvDateOfBirth.setText("");
			}

			spinBirthPlace.setSelection(returnpos(Child.get(0)
					.getPlace_of_birth(), 12));
			spinChildGender
					.setSelection(returnpos(Child.get(0).getGender(), 26));
			spinfacility.setSelection(returnpos(Child.get(0).getFacilityType(),
					27));
			if ((Child.get(0).getBcg() != null && Child.get(0).getBcg()
					.length() > 0)
					|| (Child.get(0).getOpv1() != null && Child.get(0)
							.getOpv1().length() > 0)
					|| (Child.get(0).getHepb1() != null && Child.get(0)
							.getHepb1().length() > 0)) {
				spinVaccination.setSelection(returnpos(1, 7));
			} else {
				spinVaccination.setSelection(returnpos(2, 7));
			}

			if (Child.get(0).getBcg() != null
					&& Child.get(0).getBcg().length() > 0) {
				checkBcgYes.setChecked(true);
			}
			if (Child.get(0).getOpv1() != null
					&& Child.get(0).getOpv1().length() > 0) {
				checkOpvYes.setChecked(true);
			}
			if (Child.get(0).getHepb1() != null
					&& Child.get(0).getHepb1().length() > 0) {
				checkHepYes.setChecked(true);
			}

		}

	}

	public void fillCommonRecord(Spinner spin, int iFlag) {
		Common = dataProvider.getCommonRecord(global.getLanguage(), iFlag);

		String WhereHouse[] = new String[Common.size() + 1];
		WhereHouse[0] = getResources().getString(R.string.select);

		for (int i = 1; i < Common.size() + 1; i++) {
			WhereHouse[i] = Common.get(i - 1).getValue();
		}
		adapternew = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, WhereHouse);
		spin.setAdapter(adapternew);
	}

	public int returnpos(int value, int iFlag) {
		int iValue = 0;
		Common = dataProvider.getCommonRecord(1, iFlag);
		for (int i = 0; i < Common.size(); i++) {
			if (Common.get(i).getID() == value) {
				iValue = i + 1;
			}
		}
		return iValue;

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
		Button btncancel = (Button) datepic.findViewById(R.id.cancle);

		datepic.show();

		datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		final DatePicker datetext = (DatePicker) datepic
				.findViewById(R.id.idfordate);
		Date date = new Date();
		datetext.setMaxDate((long) date.getTime());
		try {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -16);
			date = cal.getTime();
			datetext.setMinDate((long) date.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public void CustomAlert1() {

		// Create custom dialog object
		final Dialog dialog = new Dialog(this);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.child_alert, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		// ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
		// image.setVisibility(false);

		// Retrieve views from the inflated dialog layout and update their
		// values
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(getResources().getString(R.string.alertmsg));

		// TextView txtMessage = (TextView)
		// dialog.findViewById(R.id.txt_dialog_message);
		// txtMessage.setText("Do you want to Leave the page ?");

		Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
		btnyes.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				// Dismiss the dialog
				// int iLoggedin = 0;

				try {

					dataProvider.updatevaccine(global.getsGlobalChildGUID(),
							"", "", "", "", "", "", "", "", "", "", "", "", "",
							"", "", "", "", "", "", "", "", "", "", "", "", "",
							"", "", "", "", "");
					save();

					String msg = getResources()
							.getString(R.string.successfully);
					CustomAlertSave(msg, btnSave);
				} catch (Exception e) {

				}

				dialog.dismiss();
			}
		});

		Button btnno = (Button) dialog.findViewById(R.id.btn_no);
		btnno.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				// Dismiss the dialog

				dialog.dismiss();
			}
		});

		// Display the dialog
		dialog.show();

	}

	public int gender(int aa) {
		if (aa == 1) {
			return 2;
		} else if (aa == 2) {
			return 1;
		}
		return aa;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent i = new Intent(this, newbornGrid.class);
		finish();
		startActivity(i);
	}
}
