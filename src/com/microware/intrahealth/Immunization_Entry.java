package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

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
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

@SuppressLint({ "SimpleDateFormat", "InflateParams" })
public class Immunization_Entry extends Activity {

	TextView tvfathername, tVmothername, tvChildname, TXTBCG, txtopv1,
			txtDPT1Pentavalent1, tvHepatitisB1, tvOPV2, tvDPT2Pentavalent2,
			tvHepatitisB2, tvOPV3, tvDPT3Pentavalent3, tvHepatitisB3,
			txtMeasles1, txtVitaminA, txtPentavalent1, txtPentavalent2,
			txtPentavalent3, txtopv0, tvHepatitisB, txtipv, txttt,
			txtVitaminAtwo, txtMeasles2, txtDPTBooster, txtopvbooster,
			txtDPTBoostertwo, tv_JEVaccine1, tv_JEVaccine2, tv_vitamin3,
			tv_vitamin4, tv_vitamin5, tv_vitamin6, tv_vitamin7, tv_vitamin8,
			tv_vitamin9, tv_TT2;
	ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
	Dialog datepic;
	Button btnSubmit;
	String sChildGUID;
	DataProvider dataProvider;
	String sDOBCHILD = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imunization_entery);

		btnSubmit = (Button) findViewById(R.id.btnSave);
		dataProvider = new DataProvider(this);

		tvfathername = (TextView) findViewById(R.id.tvfathername);
		txtMeasles2 = (TextView) findViewById(R.id.txtMeasles2);
		txtDPTBooster = (TextView) findViewById(R.id.txtDPTBooster);
		txtopvbooster = (TextView) findViewById(R.id.txtopvbooster);
		txtDPTBoostertwo = (TextView) findViewById(R.id.txtDPTBoostertwo);
		tVmothername = (TextView) findViewById(R.id.tVmothername);
		txttt = (TextView) findViewById(R.id.txttt);
		txtVitaminAtwo = (TextView) findViewById(R.id.txtVitaminAtwo);
		tvChildname = (TextView) findViewById(R.id.tvChildname);
		TXTBCG = (TextView) findViewById(R.id.TXTBCG);
		txtopv1 = (TextView) findViewById(R.id.txtopv1);
		txtDPT1Pentavalent1 = (TextView) findViewById(R.id.txtDPT1Pentavalent1);
		tvHepatitisB1 = (TextView) findViewById(R.id.tvHepatitisB1);
		tvOPV2 = (TextView) findViewById(R.id.tvOPV2);
		tvDPT2Pentavalent2 = (TextView) findViewById(R.id.tvDPT2Pentavalent2);
		tvHepatitisB2 = (TextView) findViewById(R.id.tvHepatitisB2);
		tvOPV3 = (TextView) findViewById(R.id.tvOPV3);
		tvDPT3Pentavalent3 = (TextView) findViewById(R.id.tvDPT3Pentavalent3);
		tvHepatitisB3 = (TextView) findViewById(R.id.tvHepatitisB3);
		txtMeasles1 = (TextView) findViewById(R.id.txtMeasles1);
		txtVitaminA = (TextView) findViewById(R.id.txtVitaminA);
		txtPentavalent3 = (TextView) findViewById(R.id.txtPentavalent3);
		txtPentavalent1 = (TextView) findViewById(R.id.txtPentavalent1);
		txtPentavalent2 = (TextView) findViewById(R.id.txtPentavalent2);
		txtopv0 = (TextView) findViewById(R.id.txtopv0);
		tvHepatitisB = (TextView) findViewById(R.id.tvHepatitisB);
		txtipv = (TextView) findViewById(R.id.txtipv);
		tv_JEVaccine1 = (TextView) findViewById(R.id.tv_JEVaccine1);
		tv_JEVaccine2 = (TextView) findViewById(R.id.tv_JEVaccine2);
		tv_vitamin3 = (TextView) findViewById(R.id.tv_vitamin3);
		tv_vitamin4 = (TextView) findViewById(R.id.tv_vitamin4);
		tv_vitamin5 = (TextView) findViewById(R.id.tv_vitamin5);
		tv_vitamin6 = (TextView) findViewById(R.id.tv_vitamin6);
		tv_vitamin7 = (TextView) findViewById(R.id.tv_vitamin7);
		tv_vitamin8 = (TextView) findViewById(R.id.tv_vitamin8);
		tv_vitamin9 = (TextView) findViewById(R.id.tv_vitamin9);
		tv_TT2 = (TextView) findViewById(R.id.tv_TT2);
		btnSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savedata();
				String msg = getResources().getString(R.string.successfully);
				CustomAlertSave(msg);

			}
		});
		txtopv1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(txtopv1, txtopv0,
						getResources().getString(R.string.opv2), getResources()
								.getString(R.string.opv1));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvOPV2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvOPV2, txtopv1,
						getResources().getString(R.string.opv3), getResources()
								.getString(R.string.opv2));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvDPT2Pentavalent2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvDPT2Pentavalent2, txtDPT1Pentavalent1,
						getResources().getString(R.string.dpt2), getResources()
								.getString(R.string.dpt1));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvDPT3Pentavalent3.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvDPT3Pentavalent3, tvDPT2Pentavalent2, getResources()
						.getString(R.string.dpt3),
						getResources().getString(R.string.dpt2));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		txtPentavalent2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(txtPentavalent2, txtPentavalent1, getResources()
						.getString(R.string.Pentavalent2), getResources()
						.getString(R.string.Pentavalent1));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		txtPentavalent3.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(txtPentavalent3, txtPentavalent2, getResources()
						.getString(R.string.Pentavalent3), getResources()
						.getString(R.string.Pentavalent2));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvHepatitisB1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvHepatitisB1, tvHepatitisB,
						getResources().getString(R.string.hep0), getResources()
								.getString(R.string.hep1));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvHepatitisB2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvHepatitisB2, tvHepatitisB1, getResources()
						.getString(R.string.hep2),
						getResources().getString(R.string.hep0));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		tvHepatitisB3.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				validate(tvHepatitisB3, tvHepatitisB2, getResources()
						.getString(R.string.hep3),
						getResources().getString(R.string.hep2));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		Intent intent = getIntent();
		String smothername = "";
		smothername = intent.getExtras().getString("MotherName");
		String sfathername = "";
		String sChildName = "";
		sfathername = intent.getExtras().getString("HusbandName");
		sChildName = intent.getExtras().getString("ChildName");
		sChildGUID = intent.getExtras().getString("ChildGUID");
		sDOBCHILD = intent.getExtras().getString("DOB");

		tvChildname.setText(sChildName);

		tvfathername.setText(sfathername);
		tVmothername.setText(smothername);
		showDateCheckValidation();
		showData();
	}

	public void txtclick(View view) {
		switch (view.getId()) {

		case R.id.TXTBCG:
			// do your code
			ShowDatePicker(TXTBCG, 0);

			break;

		case R.id.txtopv0:
			// do your code
			ShowDatePicker(txtopv0, 0);

			break;

		case R.id.tvHepatitisB:
			// do your code
			ShowDatePicker(tvHepatitisB, 0);

			break;

		case R.id.txtopv1:
			// do your code
			ShowDatePicker(txtopv1, 0);
			break;

		case R.id.txtDPT1Pentavalent1:
			ShowDatePicker(txtDPT1Pentavalent1, 1);
			// do your code

			break;
		case R.id.tvHepatitisB1:
			// do your code
			ShowDatePicker(tvHepatitisB1, 2);
			break;
		case R.id.tvOPV2:
			// do your code
			ShowDatePicker(tvOPV2, 0);

			break;
		case R.id.tvDPT2Pentavalent2:
			// do your code
			ShowDatePicker(tvDPT2Pentavalent2, 4);

			break;
		case R.id.tvHepatitisB2:
			// do your code
			ShowDatePicker(tvHepatitisB2, 5);

			break;
		case R.id.tvOPV3:
			// do your code
			ShowDatePicker(tvOPV3, 0);

			break;
		case R.id.tvDPT3Pentavalent3:
			// do your code
			ShowDatePicker(tvDPT3Pentavalent3, 7);
			break;

		case R.id.tvHepatitisB3:
			// do your code
			ShowDatePicker(tvHepatitisB3, 8);
			break;

		case R.id.txtMeasles1:
			// do your code
			ShowDatePicker(txtMeasles1, 0);
			break;
		case R.id.txtVitaminA:
			// do your code
			ShowDatePicker(txtVitaminA, 0);
			break;
		case R.id.txtPentavalent1:
			// do your code
			ShowDatePicker(txtPentavalent1, 3);
			break;
		case R.id.txtPentavalent2:
			// do your code
			ShowDatePicker(txtPentavalent2, 6);
			break;
		case R.id.txtPentavalent3:
			// do your code
			ShowDatePicker(txtPentavalent3, 9);
			break;
		case R.id.txtipv:
			// do your code
			ShowDatePicker(txtipv, 0);
			break;
		case R.id.txttt:
			// do your code
			ShowDatePicker(txttt, 0);
			break;
		case R.id.txtMeasles2:
			// do your code
			ShowDatePicker(txtMeasles2, 0);
			break;
		case R.id.txtDPTBooster:
			// do your code
			ShowDatePicker(txtDPTBooster, 0);
			break;
		case R.id.txtDPTBoostertwo:
			// do your code
			ShowDatePicker(txtDPTBoostertwo, 0);
			break;
		case R.id.txtopvbooster:
			// do your code
			ShowDatePicker(txtopvbooster, 0);
			break;
		case R.id.txtVitaminAtwo:
			// do your code
			ShowDatePicker(txtVitaminAtwo, 0);
			break;
		case R.id.tv_JEVaccine1:
			ShowDatePicker(tv_JEVaccine1, 0);
			break;
		case R.id.tv_JEVaccine2:
			ShowDatePicker(tv_JEVaccine2, 0);
			break;
		case R.id.tv_vitamin3:
			ShowDatePicker(tv_vitamin3, 0);
			break;
		case R.id.tv_vitamin4:
			ShowDatePicker(tv_vitamin4, 0);
			break;
		case R.id.tv_vitamin5:
			ShowDatePicker(tv_vitamin5, 0);
			break;
		case R.id.tv_vitamin6:
			ShowDatePicker(tv_vitamin6, 0);
			break;
		case R.id.tv_vitamin7:
			ShowDatePicker(tv_vitamin7, 0);
			break;
		case R.id.tv_vitamin8:
			ShowDatePicker(tv_vitamin8, 0);
			break;
		case R.id.tv_vitamin9:
			ShowDatePicker(tv_vitamin9, 0);
			break;
		case R.id.tv_TT2:
			ShowDatePicker(tv_TT2, 0);
			break;

		default:
			break;

		}
	}

	public void showDateCheckValidation() {

		Calendar c = Calendar.getInstance();
		System.out.println("Current time => " + c.getTime());

		SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = dfDate.format(c.getTime());

		java.util.Date d = null;
		java.util.Date d1 = null;
		// Calendar cal = Calendar.getInstance();
		try {
			d = dfDate.parse(sDOBCHILD);
			d1 = dfDate.parse(formattedDate);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
		System.out.println(diffInDays);

		// sBCGDATE = TXTBCG.getText().toString();
		// stxtopv1 = txtopv1.getText().toString();
		// stxtDPT1Pentavalent1 = txtDPT1Pentavalent1.getText().toString();
		// stvHepatitisB1 = tvHepatitisB1.getText().toString();
		// stvOPV2 = tvOPV2.getText().toString();
		// stvDPT2Pentavalent2 = tvDPT2Pentavalent2.getText().toString();
		// stvHepatitisB2 = tvHepatitisB2.getText().toString();
		//
		// stvOPV3 = tvOPV3.getText().toString();
		// stvDPT3Pentavalent3 = tvDPT3Pentavalent3.getText().toString();
		// stvHepatitisB3 = tvHepatitisB3.getText().toString();
		// stxtMeasles1 = txtMeasles1.getText().toString();
		// stxtVitaminA = txtVitaminA.getText().toString();

		// /////********For Date Opv1*********/////
		if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
			tvOPV3.setEnabled(true);
			tvOPV2.setEnabled(true);
			txtopv1.setEnabled(true);

		} else if (diffInDays >= 42 && diffInDays >= 70) {
			tvOPV2.setEnabled(true);
			txtopv1.setEnabled(true);
		} else if (diffInDays >= 42) {

			txtopv1.setEnabled(true);
		}
		if (diffInDays >= 98) {
			txtipv.setEnabled(true);
		} else {
			txtipv.setEnabled(false);
		}
		// /////********For Date dpt*********/////

		if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
			tvDPT3Pentavalent3.setEnabled(true);
			tvDPT2Pentavalent2.setEnabled(true);
			txtDPT1Pentavalent1.setEnabled(true);

		} else if (diffInDays >= 42 && diffInDays >= 70) {
			tvDPT2Pentavalent2.setEnabled(true);
			txtDPT1Pentavalent1.setEnabled(true);
		} else if (diffInDays >= 42) {

			txtDPT1Pentavalent1.setEnabled(true);

		}

		// /////********For Date hep*********/////
		if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
			tvHepatitisB3.setEnabled(true);
			tvHepatitisB2.setEnabled(true);
			tvHepatitisB1.setEnabled(true);

		} else if (diffInDays >= 42 && diffInDays >= 70) {
			tvHepatitisB2.setEnabled(true);
			tvHepatitisB1.setEnabled(true);
		} else if (diffInDays >= 42) {

			tvHepatitisB1.setEnabled(true);
		}

		// //////////***********valid for patvalent *******//////////////

		if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
			txtPentavalent1.setEnabled(true);
			txtPentavalent2.setEnabled(true);
			txtPentavalent3.setEnabled(true);

		} else if (diffInDays >= 42 && diffInDays >= 70) {
			txtPentavalent1.setEnabled(true);
			txtPentavalent2.setEnabled(true);
		} else if (diffInDays >= 42) {

			txtPentavalent1.setEnabled(true);
		}

		// //////*******Valid FOr measles********/////

		if (diffInDays >= 270 && diffInDays <= 365) {
			txtMeasles1.setEnabled(true);

		}

		// //////////********* VITAMION A***************////////////

		if (diffInDays >= 270 && diffInDays <= 365) {
			txtVitaminA.setEnabled(true);
			tv_JEVaccine1.setEnabled(true);

		}
		if (diffInDays >= 366 && diffInDays <= 547) {
			tv_vitamin4.setEnabled(true);
		}
		if (diffInDays >= 480 && diffInDays <= 730) {
			tv_JEVaccine2.setEnabled(true);
			if (diffInDays >= 548 && diffInDays <= 730) {
				tv_vitamin3.setEnabled(true);
			}

		}
		if (diffInDays > 730 && diffInDays <= 912) {
			tv_vitamin4.setEnabled(true);
		}
		if (diffInDays >= 913 && diffInDays <= 1095) {
			tv_vitamin5.setEnabled(true);
		}
		if (diffInDays >= 1096 && diffInDays <= 1277) {
			tv_vitamin6.setEnabled(true);
		}
		if (diffInDays >= 1278 && diffInDays <= 1460) {
			tv_vitamin7.setEnabled(true);
		}
		if (diffInDays >= 1461 && diffInDays <= 1642) {
			tv_vitamin8.setEnabled(true);
		}
		if (diffInDays >= 1642 && diffInDays <= 1825) {
			tv_vitamin9.setEnabled(true);
		}
		if (diffInDays >= 3650 && diffInDays <= 5840) {
			txttt.setEnabled(true);
		}
		if (diffInDays >= (16*365) ) {
			tv_TT2.setEnabled(true);
		}

	}

	@SuppressWarnings("unused")
	public void savedata() {

		String sBCGDATE = "";
		String stxtopv1 = "";
		String stxtDPT1Pentavalent1 = "";
		String stvHepatitisB1 = "";
		String stvOPV2 = "";
		String stvDPT2Pentavalent2 = "";
		String stvHepatitisB2 = "";
		String stvOPV3 = "";
		String stvDPT3Pentavalent3 = "";
		String stvHepatitisB3 = "";
		String stxtMeasles1 = "";
		String stxtVitaminA = "";
		String sPentavalent1 = "";
		String sPentavalent2 = "";
		String sPentavalent3 = "";
		String sopv0 = "";
		String sHepatitisB = "";
		String IPV = "";
		String Pentavalent1 = "";
		String DPTBooster = "";
		String OPVBooster = "";
		String MeaslesTwoDose = "";
		String VitaminAtwo = "";
		String DPTBoostertwo = "";
		String ChildTT = "";
		// add jitendra
		String JEVaccine1 = "";
		String JEVaccine2 = "";
		String VitaminA3 = "";
		String VitaminA4 = "";
		String VitaminA5 = "";
		String VitaminA6 = "";
		String VitaminA7 = "";
		String VitaminA8 = "";
		String VitaminA9 = "";
		String TT2 = "";

		sBCGDATE = Validate.changeDateFormat(TXTBCG.getText().toString());
		stxtopv1 = Validate.changeDateFormat(txtopv1.getText().toString());
		stxtDPT1Pentavalent1 = Validate.changeDateFormat(txtDPT1Pentavalent1
				.getText().toString());
		stvHepatitisB1 = Validate.changeDateFormat(tvHepatitisB1.getText()
				.toString());
		stvOPV2 = Validate.changeDateFormat(tvOPV2.getText().toString());
		stvDPT2Pentavalent2 = Validate.changeDateFormat(tvDPT2Pentavalent2
				.getText().toString());
		stvHepatitisB2 = Validate.changeDateFormat(tvHepatitisB2.getText()
				.toString());

		stvOPV3 = Validate.changeDateFormat(tvOPV3.getText().toString());
		stvDPT3Pentavalent3 = Validate.changeDateFormat(tvDPT3Pentavalent3
				.getText().toString());
		stvHepatitisB3 = Validate.changeDateFormat(tvHepatitisB3.getText()
				.toString());
		stxtMeasles1 = Validate.changeDateFormat(txtMeasles1.getText()
				.toString());
		stxtVitaminA = Validate.changeDateFormat(txtVitaminA.getText()
				.toString());
		sPentavalent1 = Validate.changeDateFormat(txtPentavalent1.getText()
				.toString());
		sPentavalent2 = Validate.changeDateFormat(txtPentavalent2.getText()
				.toString());
		sPentavalent3 = Validate.changeDateFormat(txtPentavalent3.getText()
				.toString());
		sopv0 = Validate.changeDateFormat(txtopv0.getText().toString());
		sHepatitisB = Validate.changeDateFormat(tvHepatitisB.getText()
				.toString());
		DPTBooster = Validate.changeDateFormat(txtDPTBooster.getText()
				.toString());
		OPVBooster = Validate.changeDateFormat(txtopvbooster.getText()
				.toString());
		MeaslesTwoDose = Validate.changeDateFormat(txtMeasles2.getText()
				.toString());
		VitaminAtwo = txtVitaminAtwo.getText().toString();
		DPTBoostertwo = Validate.changeDateFormat(txtDPTBoostertwo.getText()
				.toString());
		ChildTT = Validate.changeDateFormat(txttt.getText().toString());
		IPV = Validate.changeDateFormat(txtipv.getText().toString());

		JEVaccine1 = tv_JEVaccine1.getText().toString();
		JEVaccine2 = tv_JEVaccine2.getText().toString();
		VitaminA3 = tv_vitamin3.getText().toString();
		VitaminA4 = tv_vitamin4.getText().toString();
		VitaminA5 = tv_vitamin5.getText().toString();
		VitaminA6 = tv_vitamin6.getText().toString();
		VitaminA7 = tv_vitamin7.getText().toString();
		VitaminA8 = tv_vitamin8.getText().toString();
		VitaminA9 = tv_vitamin9.getText().toString();
		TT2 = tv_TT2.getText().toString();
		try {

			String Sql = "";
			Sql = "update tblChild set bcg='" + sBCGDATE + "',opv2='"
					+ stxtopv1 + "',dpt1='" + stxtDPT1Pentavalent1
					+ "',hepb2='" + stvHepatitisB1 + "',opv3='" + stvOPV2
					+ "',dpt2='" + stvDPT2Pentavalent2 + "',hepb3='"
					+ stvHepatitisB2 + "',opv4='" + stvOPV3 + "',dpt3='"
					+ stvDPT3Pentavalent3 + "',hepb4='" + stvHepatitisB3
					+ "',measeals='" + stxtMeasles1 + "',vitaminA='"
					+ stxtVitaminA + "',Pentavalent1='" + sPentavalent1
					+ "',Pentavalent2='" + sPentavalent2 + "',Pentavalent3='"
					+ sPentavalent3 + "',opv1='" + sopv0 + "',hepb1='"
					+ sHepatitisB + "',IPV='" + IPV + "',JEVaccine1='"
					+ JEVaccine1 + "',JEVaccine2='" + JEVaccine2
					+ "',VitaminA3='" + VitaminA3 + "',VitaminA4='" + VitaminA4
					+ "',VitaminA5='" + VitaminA5 + "',VitaminA6='" + VitaminA6
					+ "',VitaminA7='" + VitaminA7 + "',VitaminA8='" + VitaminA8
					+ "',VitaminA9='" + VitaminA9 + "',TT2='" + TT2
					+ "',IsEdited=1 where ChildGUID='" + sChildGUID + "'";
			dataProvider.executeSql(Sql);

		} catch (Exception e) {
			// TODO: handle exception
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

		Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
		btnok.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {

				dialog.dismiss();
				Intent i = new Intent(Immunization_Entry.this,
						ImunizationChildList.class);
				finish();
				startActivity(i);

			}
		});

		// Display the dialog
		dialog.show();

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

	public void validate(TextView tt1, TextView tt2, String msg1, String msg2) {
		if (tt1.getText().toString().length() > 0
				&& tt2.getText().toString().length() > 0) {

			String date = tt1.getText().toString();
			String dat2 = tt2.getText().toString();
			SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");
			Date d = null;
			Date d1 = null;

			try {
				d = dfDate.parse(date);
				d1 = dfDate.parse(dat2);
				int diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
				System.out.println(diffInDays);
				if (diffInDays < 28) {
					tt1.setText("");
					CustomAlert(msg2 + " "
							+ getResources().getString(R.string.msg) + " "
							+ msg1 + " "
							+ getResources().getString(R.string.msgdate));
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

		}

	}

	public void showData() {
		ChildImmun = dataProvider.ShowCHildImmunizationdata(sChildGUID);

		if (ChildImmun != null && ChildImmun.size() > 0) {
			TXTBCG.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
					.get(0).getBcg())));
			txtopv1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
					.get(0).getOpv2())));
			txtDPT1Pentavalent1.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getDpt1())));
			tvHepatitisB1.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getHepb2())));
			tvOPV2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
					.get(0).getOpv3())));
			tvDPT2Pentavalent2.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getDpt2())));
			tvHepatitisB2.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getHepb3())));
			tvOPV3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
					.get(0).getOpv4())));
			tvDPT3Pentavalent3.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getDpt3())));
			tvHepatitisB3.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getHepb4())));
			txtMeasles1.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getMeaseals())));
			txtVitaminA.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getVitaminA())));

			txtPentavalent1.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getPentavalent1())));
			txtPentavalent2.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getPentavalent2())));
			txtPentavalent3.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getPentavalent3())));

			txtopv0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
					.get(0).getOpv1())));
			tvHepatitisB.setText(String.valueOf(Validate
					.changeDateFormat(ChildImmun.get(0).getHepb1())));
			if (String.valueOf(ChildImmun.get(0).getDpt1()).length() > 0
					|| String.valueOf(ChildImmun.get(0).getHepb2()).length() > 0) {
				txtPentavalent1.setEnabled(false);
				txtDPT1Pentavalent1.setEnabled(true);
				tvHepatitisB1.setEnabled(true);
			} else if (String.valueOf(ChildImmun.get(0).getPentavalent1())
					.length() > 0) {
				txtPentavalent1.setEnabled(true);
				txtDPT1Pentavalent1.setEnabled(false);
				tvHepatitisB1.setEnabled(false);
			}

			if (String.valueOf(ChildImmun.get(0).getDpt2()).length() > 0
					|| String.valueOf(ChildImmun.get(0).getHepb3()).length() > 0) {
				txtPentavalent2.setEnabled(false);
				tvDPT2Pentavalent2.setEnabled(true);
				tvHepatitisB2.setEnabled(true);
			} else if (String.valueOf(ChildImmun.get(0).getPentavalent2())
					.length() > 0) {
				txtPentavalent2.setEnabled(true);
				tvDPT2Pentavalent2.setEnabled(false);
				tvHepatitisB2.setEnabled(false);
			}

			if (String.valueOf(ChildImmun.get(0).getDpt3()).length() > 0
					|| String.valueOf(ChildImmun.get(0).getHepb4()).length() > 0) {
				txtPentavalent3.setEnabled(false);
				tvDPT3Pentavalent3.setEnabled(true);
				tvHepatitisB3.setEnabled(true);
			} else if (String.valueOf(ChildImmun.get(0).getPentavalent3())
					.length() > 0) {
				txtPentavalent3.setEnabled(true);
				tvDPT3Pentavalent3.setEnabled(false);
				tvHepatitisB3.setEnabled(false);
			}
			tv_JEVaccine1.setText(ChildImmun.get(0).getJEVaccine1());
			tv_JEVaccine2.setText(ChildImmun.get(0).getJEVaccine2());
			tv_vitamin3.setText(ChildImmun.get(0).getVitaminA3());
			tv_vitamin4.setText(ChildImmun.get(0).getVitaminA4());
			tv_vitamin5.setText(ChildImmun.get(0).getVitaminA5());
			tv_vitamin6.setText(ChildImmun.get(0).getVitaminA6());
			tv_vitamin7.setText(ChildImmun.get(0).getVitaminA7());
			tv_vitamin8.setText(ChildImmun.get(0).getVitaminA8());
			tv_vitamin9.setText(ChildImmun.get(0).getVitaminA9());
			tv_TT2.setText(ChildImmun.get(0).getTT2());

		}
	}

	public void ShowDatePicker(final TextView tVvisit, final int itextboxvalue) {

		// TODO Auto-generated method stub
		datepic = new Dialog(this, R.style.CustomTheme);

		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker1);
		Button btnset = (Button) datepic.findViewById(R.id.set);
		// Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
		Button btncancel = (Button) datepic.findViewById(R.id.cancle);
		Button btnclear = (Button) datepic.findViewById(R.id.clear);
		btnclear.setVisibility(View.VISIBLE);

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

				if (itextboxvalue == 1 || itextboxvalue == 2
						|| itextboxvalue == 3) {
					if (txtDPT1Pentavalent1.getText().toString().length() > 0
							|| tvHepatitisB1.getText().toString().length() > 0) {

						txtPentavalent1.setEnabled(false);
						txtDPT1Pentavalent1.setEnabled(true);
						tvHepatitisB1.setEnabled(true);
					} else if (txtPentavalent1.getText().toString().length() > 0) {
						txtDPT1Pentavalent1.setEnabled(false);
						tvHepatitisB1.setEnabled(false);
						txtPentavalent1.setEnabled(true);

					}
				} else if (itextboxvalue == 4 || itextboxvalue == 5
						|| itextboxvalue == 6) {
					if (tvDPT2Pentavalent2.getText().toString().length() > 0
							|| tvHepatitisB2.getText().toString().length() > 0) {

						txtPentavalent2.setEnabled(false);
						tvDPT2Pentavalent2.setEnabled(true);
						tvHepatitisB2.setEnabled(true);
					} else if (txtPentavalent2.getText().toString().length() > 0) {
						tvDPT2Pentavalent2.setEnabled(false);
						tvHepatitisB2.setEnabled(false);
						txtPentavalent2.setEnabled(true);

					}

				}

				else if (itextboxvalue == 7 || itextboxvalue == 8
						|| itextboxvalue == 9) {
					if (tvDPT3Pentavalent3.getText().toString().length() > 0
							|| tvHepatitisB3.getText().toString().length() > 0) {

						txtPentavalent3.setEnabled(false);
						tvDPT3Pentavalent3.setEnabled(true);
						tvHepatitisB3.setEnabled(true);
					} else if (txtPentavalent3.getText().toString().length() > 0) {
						tvDPT3Pentavalent3.setEnabled(false);
						tvHepatitisB3.setEnabled(false);
						txtPentavalent3.setEnabled(true);

					}

				}

				datepic.dismiss();
			}
		});
		btnclear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				tVvisit.setText("");
				if (itextboxvalue == 1 || itextboxvalue == 2) {
					if (txtDPT1Pentavalent1.getText().toString().length() == 0
							&& tvHepatitisB1.getText().toString().length() == 0) {
						txtPentavalent1.setEnabled(true);
					}

				} else if (itextboxvalue == 3) {
					txtDPT1Pentavalent1.setEnabled(true);
					tvHepatitisB1.setEnabled(true);
				}

				if (itextboxvalue == 4 || itextboxvalue == 5) {
					if (tvDPT2Pentavalent2.getText().toString().length() == 0
							&& tvHepatitisB2.getText().toString().length() == 0) {
						txtPentavalent2.setEnabled(true);
					}

				} else if (itextboxvalue == 6) {
					tvDPT2Pentavalent2.setEnabled(true);
					tvHepatitisB2.setEnabled(true);
				}

				if (itextboxvalue == 7 || itextboxvalue == 8) {
					if (tvDPT3Pentavalent3.getText().toString().length() == 0
							&& tvHepatitisB3.getText().toString().length() == 0) {
						txtPentavalent3.setEnabled(true);
					}

				} else if (itextboxvalue == 9) {
					tvDPT3Pentavalent3.setEnabled(true);
					tvHepatitisB3.setEnabled(true);
				}

				datepic.dismiss();

			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

}