package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({ "SimpleDateFormat", "InflateParams" })
public class childActivity extends Activity {

	Button btnSave;

	EditText etmctsID, etmctsmID, etWeightChild, etChildName, etmctsID2,
			etWeightChild2, etChildName2, etmctsID3, etWeightChild3,
			etChildName3, etfacility;
	TextView tvDateOfBirth;
	Spinner spinBirthPlace, spinChildGender, spinBreastFeeding,
			spinVaccination, spinnumberofnewborn, spinChildGender2,
			spinBreastFeeding2, spinVaccination2, spinChildGender3,
			spinBreastFeeding3, spinVaccination3, spinfacility;
	CheckBox checkBcgYes, checkBcgNo, checkOpvYes, checkOpvNo, checkHepYes,
			checkHepNo, checkBcgYes2, checkBcgNo2, checkOpvYes2, checkOpvNo2,
			checkHepYes2, checkHepNo2, checkBcgYes3, checkBcgNo3, checkOpvYes3,
			checkOpvNo3, checkHepYes3, checkHepNo3;
	TableRow tblBcg, tblOpv, tblHep, tblBcg2, tblOpv2, tblHep2, tblBcg3,
			tblOpv3, tblHep3, tblnumberofnewborn, tblfacilitytype, tblfacility,
			tblheading;
	LinearLayout linear2, linear3;
	ImageView btnadd, btnminus, btnadd2, btnminus2, btnadd3, btnminus3;
	ArrayAdapter<String> adapternew;
	Dialog datepic;
	DataProvider dataProvider;
	Global global;
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	ArrayList<tblChild> Child = new ArrayList<tblChild>();
	ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.newbornchild);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		btnSave = (Button) findViewById(R.id.btnSave);
		etmctsID = (EditText) findViewById(R.id.etmctsID);
		etmctsmID = (EditText) findViewById(R.id.etmctsmID);
		etWeightChild = (EditText) findViewById(R.id.etWeightChild);
		etChildName = (EditText) findViewById(R.id.etChildName);
		etfacility = (EditText) findViewById(R.id.etfacility);
		tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
		spinBirthPlace = (Spinner) findViewById(R.id.spinBirthPlace);
		spinChildGender = (Spinner) findViewById(R.id.spinChildGender);
		spinBreastFeeding = (Spinner) findViewById(R.id.spinBreastFeeding);
		spinVaccination = (Spinner) findViewById(R.id.spinVaccination);
		spinnumberofnewborn = (Spinner) findViewById(R.id.spinnumberofnewborn);
		spinfacility = (Spinner) findViewById(R.id.spinfacility);
		checkBcgYes = (CheckBox) findViewById(R.id.checkBcgYes);
		checkBcgNo = (CheckBox) findViewById(R.id.checkBcgNo);
		checkOpvYes = (CheckBox) findViewById(R.id.checkOpvYes);
		checkOpvNo = (CheckBox) findViewById(R.id.checkOpvNo);
		checkHepYes = (CheckBox) findViewById(R.id.checkHepYes);
		checkHepNo = (CheckBox) findViewById(R.id.checkHepNo);
		tblBcg = (TableRow) findViewById(R.id.tblBcg);
		tblOpv = (TableRow) findViewById(R.id.tblOpv);
		tblHep = (TableRow) findViewById(R.id.tblHep);
		tblheading = (TableRow) findViewById(R.id.tblheading);
		tblfacilitytype = (TableRow) findViewById(R.id.tblfacilitytype);
		tblfacility = (TableRow) findViewById(R.id.tblfacility);
		tblnumberofnewborn = (TableRow) findViewById(R.id.tblnumberofnewborn);
		tblBcg.setVisibility(View.GONE);
		tblOpv.setVisibility(View.GONE);
		tblHep.setVisibility(View.GONE);
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("New Born Registration");
		t.send(new HitBuilders.AppViewBuilder().build());
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		etmctsID2 = (EditText) findViewById(R.id.etmctsID2);
		etWeightChild2 = (EditText) findViewById(R.id.etWeightChild2);
		etChildName2 = (EditText) findViewById(R.id.etChildName2);
		spinChildGender2 = (Spinner) findViewById(R.id.spinChildGender2);
		spinBreastFeeding2 = (Spinner) findViewById(R.id.spinBreastFeeding2);
		spinVaccination2 = (Spinner) findViewById(R.id.spinVaccination2);
		checkBcgYes2 = (CheckBox) findViewById(R.id.checkBcgYes2);
		checkBcgNo2 = (CheckBox) findViewById(R.id.checkBcgNo2);
		checkOpvYes2 = (CheckBox) findViewById(R.id.checkOpvYes2);
		checkOpvNo2 = (CheckBox) findViewById(R.id.checkOpvNo2);
		checkHepYes2 = (CheckBox) findViewById(R.id.checkHepYes2);
		checkHepNo2 = (CheckBox) findViewById(R.id.checkHepNo2);
		tblBcg2 = (TableRow) findViewById(R.id.tblBcg2);
		tblOpv2 = (TableRow) findViewById(R.id.tblOpv2);
		tblHep2 = (TableRow) findViewById(R.id.tblHep2);
		tblBcg2.setVisibility(View.GONE);
		tblOpv2.setVisibility(View.GONE);
		tblHep2.setVisibility(View.GONE);
		btnadd = (ImageView) findViewById(R.id.btnadd);
		btnminus = (ImageView) findViewById(R.id.btnminus);
		btnadd2 = (ImageView) findViewById(R.id.btnadd2);
		btnminus2 = (ImageView) findViewById(R.id.btnminus2);
		btnadd3 = (ImageView) findViewById(R.id.btnadd3);
		btnminus3 = (ImageView) findViewById(R.id.btnminus3);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		etmctsID3 = (EditText) findViewById(R.id.etmctsID3);
		etWeightChild3 = (EditText) findViewById(R.id.etWeightChild3);
		etChildName3 = (EditText) findViewById(R.id.etChildName3);
		spinChildGender3 = (Spinner) findViewById(R.id.spinChildGender3);
		spinBreastFeeding3 = (Spinner) findViewById(R.id.spinBreastFeeding3);
		spinVaccination3 = (Spinner) findViewById(R.id.spinVaccination3);
		checkBcgYes3 = (CheckBox) findViewById(R.id.checkBcgYes3);
		checkBcgNo3 = (CheckBox) findViewById(R.id.checkBcgNo3);
		checkOpvYes3 = (CheckBox) findViewById(R.id.checkOpvYes3);
		checkOpvNo3 = (CheckBox) findViewById(R.id.checkOpvNo3);
		checkHepYes3 = (CheckBox) findViewById(R.id.checkHepYes3);
		checkHepNo3 = (CheckBox) findViewById(R.id.checkHepNo3);
		tblBcg3 = (TableRow) findViewById(R.id.tblBcg3);
		tblOpv3 = (TableRow) findViewById(R.id.tblOpv3);
		tblHep3 = (TableRow) findViewById(R.id.tblHep3);
		tblBcg3.setVisibility(View.GONE);
		tblOpv3.setVisibility(View.GONE);
		tblHep3.setVisibility(View.GONE);

		linear2.setVisibility(View.GONE);
		linear3.setVisibility(View.GONE);
		etWeightChild.setText("0");
		etWeightChild2.setText("0");
		etWeightChild3.setText("0");
		btnadd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etWeightChild.getText() != null
						&& etWeightChild.getText().toString().length() > 0
						&& !etWeightChild.getText().toString()
								.equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild.getText()
							.toString());

					etWeightChild.setText(String.valueOf(value + .1));
				} else {
					float value = Float.valueOf(0);

					etWeightChild.setText(String.valueOf(value + .1));
				}
			}
		});
		btnminus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!etWeightChild.getText().toString().equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild.getText()
							.toString());
					if (value > 0) {
						double w = value - .1;

						etWeightChild.setText(String.valueOf(w));
						// do what youwanna do on long press here
					}
				}
			}
		});
		btnadd2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etWeightChild2.getText() != null
						&& etWeightChild2.getText().toString().length() > 0
						&& !etWeightChild2.getText().toString()
								.equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild2.getText()
							.toString());

					etWeightChild2.setText(String.valueOf(value + .1));
				} else {
					float value = Float.valueOf(0);

					etWeightChild2.setText(String.valueOf(value + .1));
				}

			}
		});
		btnminus2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!etWeightChild2.getText().toString().equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild2.getText()
							.toString());
					if (value > 0) {
						double w = value - .1;

						etWeightChild2.setText(String.valueOf(w));
						// do what youwanna do on long press here
					}
				}
			}
		});
		btnadd3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etWeightChild3.getText() != null
						&& etWeightChild3.getText().toString().length() > 0
						&& !etWeightChild3.getText().toString()
								.equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild3.getText()
							.toString());

					etWeightChild3.setText(String.valueOf(value + .1));
				} else {
					float value = Float.valueOf(0);

					etWeightChild3.setText(String.valueOf(value + .1));
				}
			}
		});
		btnminus3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!etWeightChild3.getText().toString().equalsIgnoreCase(".")) {
					float value = Float.valueOf(etWeightChild3.getText()
							.toString());
					if (value > 0) {
						double w = value - .1;

						etWeightChild3.setText(String.valueOf(w));
						// do what youwanna do on long press here
					}
				}
			}
		});

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int icheck = sCheckValidation();
				if (icheck == 1) {
					save();
					String msg = getResources()
							.getString(R.string.successfully);
					CustomAlertSave(msg, btnSave);
				} else {
					showAlert(icheck);
				}
			}
		});

		spinVaccination.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (position > 0) {
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
		spinVaccination2
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							if (position == 1) {
								tblBcg2.setVisibility(View.VISIBLE);
								tblOpv2.setVisibility(View.VISIBLE);
								tblHep2.setVisibility(View.VISIBLE);
							} else {
								tblBcg2.setVisibility(View.GONE);
								tblOpv2.setVisibility(View.GONE);
								tblHep2.setVisibility(View.GONE);
								checkBcgYes2.setChecked(false);
								checkBcgNo2.setChecked(false);
								checkOpvYes2.setChecked(false);
								checkOpvNo2.setChecked(false);
								checkHepYes2.setChecked(false);
								checkHepNo2.setChecked(false);
							}
						} else {
							tblBcg2.setVisibility(View.GONE);
							tblOpv2.setVisibility(View.GONE);
							tblHep2.setVisibility(View.GONE);
							checkBcgYes2.setChecked(false);
							checkBcgNo2.setChecked(false);
							checkOpvYes2.setChecked(false);
							checkOpvNo2.setChecked(false);
							checkHepYes2.setChecked(false);
							checkHepNo2.setChecked(false);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		spinVaccination3
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							if (position == 1) {
								tblBcg3.setVisibility(View.VISIBLE);
								tblOpv3.setVisibility(View.VISIBLE);
								tblHep3.setVisibility(View.VISIBLE);
							} else {
								tblBcg3.setVisibility(View.GONE);
								tblOpv3.setVisibility(View.GONE);
								tblHep3.setVisibility(View.GONE);
								checkBcgYes3.setChecked(false);
								checkBcgNo3.setChecked(false);
								checkOpvYes3.setChecked(false);
								checkOpvNo3.setChecked(false);
								checkHepYes3.setChecked(false);
								checkHepNo3.setChecked(false);
							}
						} else {
							tblBcg3.setVisibility(View.GONE);
							tblOpv3.setVisibility(View.GONE);
							tblHep3.setVisibility(View.GONE);
							checkBcgYes3.setChecked(false);
							checkBcgNo3.setChecked(false);
							checkOpvYes3.setChecked(false);
							checkOpvNo3.setChecked(false);
							checkHepYes3.setChecked(false);
							checkHepNo3.setChecked(false);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

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
		spinnumberofnewborn
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if (position > 0) {
							if (position == 2) {
								tblheading.setVisibility(View.VISIBLE);
								linear2.setVisibility(View.VISIBLE);
								linear3.setVisibility(View.GONE);
								etmctsID3.setText("");
								etWeightChild3.setText("");
								etChildName3.setText("");
								spinChildGender3.setSelection(0);
								spinBreastFeeding3.setSelection(0);
								spinVaccination3.setSelection(0);
							} else if (position == 3) {
								tblheading.setVisibility(View.VISIBLE);
								linear2.setVisibility(View.VISIBLE);
								linear3.setVisibility(View.VISIBLE);
							} else {
								tblheading.setVisibility(View.GONE);
								linear2.setVisibility(View.GONE);
								linear3.setVisibility(View.GONE);
								etmctsID2.setText("");
								etWeightChild2.setText("");
								etChildName2.setText("");
								etmctsID3.setText("");
								etWeightChild3.setText("");
								etChildName3.setText("");
								spinChildGender2.setSelection(0);
								spinBreastFeeding2.setSelection(0);
								spinVaccination2.setSelection(0);
								spinChildGender3.setSelection(0);
								spinBreastFeeding3.setSelection(0);
								spinVaccination3.setSelection(0);
							}
						} else {
							tblheading.setVisibility(View.GONE);
							linear2.setVisibility(View.GONE);
							linear3.setVisibility(View.GONE);
							etmctsID2.setText("");
							etWeightChild2.setText("");
							etChildName2.setText("");
							etmctsID3.setText("");
							etWeightChild3.setText("");
							etChildName3.setText("");
							spinChildGender2.setSelection(0);
							spinBreastFeeding2.setSelection(0);
							spinVaccination2.setSelection(0);
							spinChildGender3.setSelection(0);
							spinBreastFeeding3.setSelection(0);
							spinVaccination3.setSelection(0);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		tvDateOfBirth.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvDateOfBirth);
			}
		});
		tvDateOfBirth.addTextChangedListener(new TextWatcher() {

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
				if (tvDateOfBirth.getText() != null
						&& tvDateOfBirth.getText().toString().length() > 0) {
					String sql = "Select LMPDate from tblPregnant_woman where PWGUID='"
							+ global.getsGlobalPWGUID() + "'";
					String LmpDate = dataProvider.getRecord(sql);
					if (LmpDate != null && LmpDate.length() > 0) {

						String tt1 = tvDateOfBirth.getText().toString();
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
							tvDateOfBirth.setText("");
							CustomAlertSave2(getResources().getString(
									R.string.dobvalid));
						}
					}
				}
			}
		});
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
		etWeightChild2.addTextChangedListener(new TextWatcher() {

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
				if (etWeightChild2.getText().length() > 0
						&& !etWeightChild2.getText().toString()
								.equalsIgnoreCase(".")) {
					float weight = Float.valueOf(etWeightChild2.getText()
							.toString());
					if (weight > 5) {
						etWeightChild2.setText("5");
					}
				}
			}
		});
		etWeightChild3.addTextChangedListener(new TextWatcher() {

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
				if (etWeightChild3.getText().length() > 0
						&& !etWeightChild3.getText().toString()
								.equalsIgnoreCase(".")) {
					float weight = Float.valueOf(etWeightChild3.getText()
							.toString());
					if (weight > 5) {
						etWeightChild3.setText("5");
					}
				}
			}
		});

		fillCommonRecord(spinBirthPlace, 12);
		fillCommonRecord(spinChildGender, 26);
		fillCommonRecord(spinBreastFeeding, 7);
		fillCommonRecord(spinVaccination, 7);
		fillCommonRecord(spinChildGender2, 26);
		fillCommonRecord(spinBreastFeeding2, 7);
		fillCommonRecord(spinVaccination2, 7);
		fillCommonRecord(spinChildGender3, 26);
		fillCommonRecord(spinBreastFeeding3, 7);
		fillCommonRecord(spinVaccination3, 7);
		fillCommonRecord(spinnumberofnewborn, 17);
		fillCommonRecord(spinfacility, 27);
		spinnumberofnewborn.setSelection(1);
		if (global.getsGlobalPWGUID() != null
				&& global.getsGlobalPWGUID().length() > 0) {
			setmctsid();
		}
		if (global.getsGlobalChildGUID() != null
				&& global.getsGlobalChildGUID().length() > 0) {
			showdata();
			tblnumberofnewborn.setVisibility(View.GONE);

		}

	}

	public void setmctsid() {
		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		pregnant = dataProvider.getPregnantWomendata(global.getsGlobalPWGUID(),
				1, ashaid);
		if (pregnant != null && pregnant.size() > 0) {
			if (pregnant.get(0).getMotherMCTSID() != null
					&& pregnant.get(0).getMotherMCTSID().length() > 0) {
				etmctsmID.setText(pregnant.get(0).getMotherMCTSID());

			}
		}
	}

	public int sCheckValidation() {

		if (tvDateOfBirth.getText() != null
				&& tvDateOfBirth.getText().toString().length() == 0) {

			return 2;

		} else if (spinBirthPlace.getSelectedItemPosition() == 0) {
			return 3;
		} else if (etWeightChild.getText() == null
				|| etWeightChild.getText().toString().length() == 0) {
			return 4;
		} else if (spinChildGender.getSelectedItemPosition() == 0) {
			return 5;
		} else if (spinnumberofnewborn.getSelectedItemPosition() == 2
				&& (etWeightChild2.getText() == null || etWeightChild2
						.getText().toString().length() == 0)) {
			return 4;
		} else if (spinnumberofnewborn.getSelectedItemPosition() == 2
				&& spinChildGender2.getSelectedItemPosition() == 0) {
			return 5;
		} else if (spinnumberofnewborn.getSelectedItemPosition() == 3
				&& (etWeightChild3.getText() == null || etWeightChild3
						.getText().toString().length() == 0)) {
			return 4;
		} else if (spinnumberofnewborn.getSelectedItemPosition() == 3
				&& spinChildGender3.getSelectedItemPosition() == 0) {
			return 5;
		} else if (etmctsID.getText().toString() != null
				&& etmctsID.getText().toString().length() > 0
				&& etmctsID.getText().toString().length() != 18) {
			return 6;
		} else if (etmctsID2.getText().toString() != null
				&& etmctsID2.getText().toString().length() > 0
				&& etmctsID2.getText().toString().length() != 18) {
			return 7;
		} else if (etmctsID3.getText().toString() != null
				&& etmctsID3.getText().toString().length() > 0
				&& etmctsID3.getText().toString().length() != 18) {
			return 8;
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
		} else if (iCheck == 6) {
			CustomAlertSave2(getResources()
					.getString(R.string.enter16digituid1));
		} else if (iCheck == 7) {
			CustomAlertSave2(getResources()
					.getString(R.string.enter16digituid2));
		} else if (iCheck == 8) {
			CustomAlertSave2(getResources()
					.getString(R.string.enter16digituid3));
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

	public void setCheckToCheckBoxGroupBcg1(View view) {
		switch (view.getId()) {
		case R.id.checkBcgYes:
			checkBcgNo.setChecked(false);
			break;
		case R.id.checkBcgNo:
			checkBcgYes.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupBcg2(View view) {
		switch (view.getId()) {
		case R.id.checkBcgYes2:
			checkBcgNo.setChecked(false);
			break;
		case R.id.checkBcgNo2:
			checkBcgYes.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupBcg3(View view) {
		switch (view.getId()) {
		case R.id.checkBcgYes3:
			checkBcgNo.setChecked(false);
			break;
		case R.id.checkBcgNo3:
			checkBcgYes.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupOpv1(View view) {
		switch (view.getId()) {
		case R.id.checkOpvYes:
			checkOpvNo.setChecked(false);
			break;
		case R.id.checkOpvNo:
			checkOpvYes.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupOpv2(View view) {
		switch (view.getId()) {
		case R.id.checkOpvYes2:
			checkOpvNo2.setChecked(false);
			break;
		case R.id.checkOpvNo2:
			checkOpvYes2.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupOpv3(View view) {
		switch (view.getId()) {
		case R.id.checkOpvYes3:
			checkOpvNo3.setChecked(false);
			break;
		case R.id.checkOpvNo3:
			checkOpvYes3.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupHep(View view) {
		switch (view.getId()) {
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

	public void setCheckToCheckBoxGroupHep2(View view) {
		switch (view.getId()) {
		case R.id.checkHepYes2:
			checkHepNo2.setChecked(false);
			break;
		case R.id.checkHepNo2:
			checkHepYes2.setChecked(false);
			break;
		default:
			break;
		}
	}

	public void setCheckToCheckBoxGroupHep3(View view) {
		switch (view.getId()) {
		case R.id.checkHepYes3:
			checkHepNo3.setChecked(false);
			break;
		case R.id.checkHepNo3:
			checkHepYes3.setChecked(false);
			break;
		default:
			break;
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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(childActivity.this).reportActivityStart(
				this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(childActivity.this)
				.reportActivityStop(this);
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
		Common = dataProvider.getCommonRecord(global.getLanguage(), iFlag);
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

	public void save() {

		String pw_GUID = "";
		String HHGUID = "";
		String HHFamilyMemberGUID = "";
		// int Child_ID = 0;
		String ChildGUID = "";
		String MotherGUID = "";
		String Date_Of_Registration = Validate.getcurrentdate();
		String Child_dob = "";
		// String Birth_time = "";
		int Gender = 0;
		float Wt_of_child = 0;
		int Gender2 = 0;
		float Wt_of_child2 = 0;
		int Gender3 = 0;
		float Wt_of_child3 = 0;
		int place_of_birth = 0;
		// String preTerm_fullTerm = "";
		// int mother_status = 0;
		// int child_status = 0;
		// String mother_death_dt = "";
		// String child_death_dt = "";
		String child_mcts_id = "";
		String child_name = "";
		String child_mcts_id2 = "";
		String child_name2 = "";
		String child_mcts_id3 = "";
		String child_name3 = "";
		// String cried_after_birth = "";
		int breastfeeding_within1H = 0;
		int breastfeeding_within1H2 = 0;
		int breastfeeding_within1H3 = 0;
		// String Exclusive_BF = "";
		// String complementry_BF = "";
		String bcg = "";
		String opv1 = "";
		// String dpt1 = "";
		String hepb1 = "";
		String bcgC2 = "";
		String opv1C2 = "";
		String hepb1C2 = "";
		String bcgC3 = "";
		String opv1C3 = "";
		String hepb1C3 = "";
		// String opv2 = "";
		// String dpt2 = "";
		// String hepb2 = "";
		// String opv3 = "";
		// String dpt3 = "";
		// String hepb3 = "";
		// String measeals = "";
		// String vitaminA = "";
		int FacilityType = 0;
		String Facility = "";
		String created_on = Validate.getcurrentdate();
		int created_by = Integer.valueOf(global.getsGlobalUserID());
		String modified_on = Validate.getcurrentdate();
		int modified_by = Integer.valueOf(global.getsGlobalUserID());
		int AshaID = 0;
		int ANMID = 0;
		AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		ANMID = Integer.valueOf(global.getsGlobalANMCODE());
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
		if (etmctsID.getText().toString().length() > 0) {
			child_mcts_id = etmctsID.getText().toString();
		}
		if (etfacility.getText().toString().length() > 0) {
			Facility = etfacility.getText().toString();
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

		if (spinfacility.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 27);
			FacilityType = Common.get(
					spinBirthPlace.getSelectedItemPosition() - 1).getID();
		}
		if (spinBirthPlace.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 12);
			place_of_birth = Common.get(
					spinBirthPlace.getSelectedItemPosition() - 1).getID();
		}
		if (spinChildGender.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 26);
			Gender = Common.get(spinChildGender.getSelectedItemPosition() - 1)
					.getID();
		}
		if (spinBreastFeeding.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 7);
			breastfeeding_within1H = Common.get(
					spinBreastFeeding.getSelectedItemPosition() - 1).getID();
		}

		if (etmctsID2.getText().toString().length() > 0) {
			child_mcts_id2 = etmctsID2.getText().toString();
		}
		if (etWeightChild2.getText().toString().length() > 0) {
			Wt_of_child2 = Float.valueOf(etWeightChild2.getText().toString());
		}
		if (etChildName2.getText().toString().length() > 0) {
			child_name2 = etChildName2.getText().toString();
		}

		if (spinChildGender2.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 26);
			Gender2 = Common
					.get(spinChildGender2.getSelectedItemPosition() - 1)
					.getID();
		}
		if (spinBreastFeeding2.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 7);
			breastfeeding_within1H2 = Common.get(
					spinBreastFeeding2.getSelectedItemPosition() - 1).getID();
		}

		if (etmctsID3.getText().toString().length() > 0) {
			child_mcts_id3 = etmctsID3.getText().toString();
		}
		if (etWeightChild3.getText().toString().length() > 0) {
			Wt_of_child3 = Float.valueOf(etWeightChild3.getText().toString());
		}
		if (etChildName3.getText().toString().length() > 0) {
			child_name3 = etChildName3.getText().toString();
		}

		if (spinChildGender3.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 26);
			Gender3 = Common
					.get(spinChildGender3.getSelectedItemPosition() - 1)
					.getID();
		}
		if (spinBreastFeeding3.getSelectedItemPosition() > 0) {
			Common = dataProvider.getCommonRecord(global.getLanguage(), 7);
			breastfeeding_within1H3 = Common.get(
					spinBreastFeeding3.getSelectedItemPosition() - 1).getID();
		}

		if (checkBcgYes.isChecked()) {
			bcg = tvDateOfBirth.getText().toString();
		} else {
			bcg = "";
		}
		if (checkOpvYes.isChecked()) {
			opv1 = tvDateOfBirth.getText().toString();
		} else {
			opv1 = "";
		}
		if (checkHepYes.isChecked()) {
			hepb1 = tvDateOfBirth.getText().toString();
		} else {
			hepb1 = "";
		}

		if (checkBcgYes2.isChecked()) {
			bcgC2 = tvDateOfBirth.getText().toString();
		} else {
			bcgC2 = "";
		}
		if (checkOpvYes2.isChecked()) {
			opv1C2 = tvDateOfBirth.getText().toString();
		} else {
			opv1C2 = "";
		}
		if (checkHepYes2.isChecked()) {
			hepb1C2 = tvDateOfBirth.getText().toString();
		} else {
			hepb1C2 = "";
		}

		if (checkBcgYes3.isChecked()) {
			bcgC3 = tvDateOfBirth.getText().toString();
		} else {
			bcgC3 = "";
		}
		if (checkOpvYes3.isChecked()) {
			opv1C3 = tvDateOfBirth.getText().toString();
		} else {
			opv1C3 = "";
		}
		if (checkHepYes3.isChecked()) {
			hepb1C3 = tvDateOfBirth.getText().toString();
		} else {
			hepb1C3 = "";
		}

		ChildGUID = Validate.random();
		if (global.getsGlobalChildGUID() != null
				&& global.getsGlobalChildGUID().length() > 0) {
			dataProvider.SavetblChild("U", AshaID, ANMID,
					global.getsGlobalChildGUID(), pw_GUID, HHGUID,
					HHFamilyMemberGUID, MotherGUID, Date_Of_Registration,
					Validate.changeDateFormat(Child_dob), Gender, Wt_of_child,
					place_of_birth, child_mcts_id, child_name,
					breastfeeding_within1H, bcg, opv1, hepb1, created_on,
					created_by, modified_on, modified_by, FacilityType,
					Facility);

		} else {
			if (spinnumberofnewborn.getSelectedItemPosition() > 0) {

				if (spinnumberofnewborn.getSelectedItemPosition() == 1) {

					dataProvider.SavetblChild("I", AshaID, ANMID, ChildGUID,
							pw_GUID, HHGUID, HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender,
							Wt_of_child, place_of_birth, child_mcts_id,
							child_name, breastfeeding_within1H, bcg, opv1,
							hepb1, created_on, created_by, modified_on,
							modified_by, FacilityType, Facility);

					global.setsGlobalChildGUID(ChildGUID);
				} else if (spinnumberofnewborn.getSelectedItemPosition() == 2) {

					dataProvider.SavetblChild("I", AshaID, ANMID, ChildGUID,
							pw_GUID, HHGUID, HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender,
							Wt_of_child, place_of_birth, child_mcts_id,
							child_name, breastfeeding_within1H, bcg, opv1,
							hepb1, created_on, created_by, modified_on,
							modified_by, FacilityType, Facility);

					global.setsGlobalChildGUID(ChildGUID);

					dataProvider.SavetblChild("I", AshaID, ANMID,
							Validate.random(), pw_GUID, HHGUID,
							HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender2,
							Wt_of_child2, place_of_birth, child_mcts_id2,
							child_name2, breastfeeding_within1H2, bcgC2,
							opv1C2, hepb1C2, created_on, created_by,
							modified_on, modified_by, FacilityType, Facility);
				} else if (spinnumberofnewborn.getSelectedItemPosition() == 3) {

					dataProvider.SavetblChild("I", AshaID, ANMID, ChildGUID,
							pw_GUID, HHGUID, HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender,
							Wt_of_child, place_of_birth, child_mcts_id,
							child_name, breastfeeding_within1H, bcg, opv1,
							hepb1, created_on, created_by, modified_on,
							modified_by, FacilityType, Facility);

					global.setsGlobalChildGUID(ChildGUID);

					dataProvider.SavetblChild("I", AshaID, ANMID,
							Validate.random(), pw_GUID, HHGUID,
							HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender2,
							Wt_of_child2, place_of_birth, child_mcts_id2,
							child_name2, breastfeeding_within1H2, bcgC2,
							opv1C2, hepb1C2, created_on, created_by,
							modified_on, modified_by, FacilityType, Facility);

					dataProvider.SavetblChild("I", AshaID, ANMID,
							Validate.random(), pw_GUID, HHGUID,
							HHFamilyMemberGUID, MotherGUID,
							Date_Of_Registration,
							Validate.changeDateFormat(Child_dob), Gender3,
							Wt_of_child3, place_of_birth, child_mcts_id3,
							child_name3, breastfeeding_within1H3, bcgC3,
							opv1C3, hepb1C3, created_on, created_by,
							modified_on, modified_by, FacilityType, Facility);
				}
			}
		}
	}

	public void showdata() {

		Child = dataProvider.gettblChild(global.getsGlobalChildGUID(), 1);
		if (Child != null && Child.size() > 0) {

			if (Child.get(0).getChild_mcts_id() != null
					&& Child.get(0).getChild_mcts_id().length() > 0) {
				etmctsID.setText(String
						.valueOf(Child.get(0).getChild_mcts_id()));
			} else {
				etmctsID.setText("");
			}
			if (Child.get(0).getWt_of_child() != 0
					&& Child.get(0).getWt_of_child() > 0) {
				etWeightChild.setText(String.valueOf(Child.get(0)
						.getWt_of_child()));
			} else {
				etWeightChild.setText("");
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
			if (Child.get(0).getFacility() != null
					&& Child.get(0).getFacility().length() > 0) {
				etfacility.setText(String.valueOf(Child.get(0).getFacility()));
			} else {
				etfacility.setText("");
			}

			spinBirthPlace.setSelection(returnpos(Child.get(0)
					.getPlace_of_birth(), 12));
			spinChildGender
					.setSelection(returnpos(Child.get(0).getGender(), 26));
			spinBreastFeeding.setSelection(returnpos(Child.get(0)
					.getBreastfeeding_within1H(), 7));
			spinfacility.setSelection(returnpos(Child.get(0).getFacilityType(),
					28));

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
			} else {
				// checkBcgNo.setChecked(true);
			}
			if (Child.get(0).getOpv1() != null
					&& Child.get(0).getOpv1().length() > 0) {
				checkOpvYes.setChecked(true);
			} else {
				// checkOpvNo.setChecked(true);
			}
			if (Child.get(0).getHepb1() != null
					&& Child.get(0).getHepb1().length() > 0) {
				checkHepYes.setChecked(true);
			} else {
				// checkHepNo.setChecked(true);
			}

		}

	}

	public void CustomAlertSave(String msg, final TextView edit) {

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
				Intent i = new Intent(childActivity.this, newbornGrid.class);
				finish();
				startActivity(i);

			}
		});

		// Display the dialog
		dialog.show();

	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();

	}
}
