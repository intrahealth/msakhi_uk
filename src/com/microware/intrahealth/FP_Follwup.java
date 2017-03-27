package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint({ "ClickableViewAccessibility", "SimpleDateFormat",
		"InflateParams" })
public class FP_Follwup extends Activity {

	Button btnSave;
	Dialog datepic;
	DataProvider dataProvider;
	Global global;
	TextView tv_Previousadvice, tv_Currentadvice, tv_WomenName, tv_husbandname,
			tv_methodadopteddate;
	Spinner spin_MethodAdopted, spin_Pregnant, spin_Pregnanttest;
	ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
	ArrayList<tblmstFPAns> FP_Ans = new ArrayList<tblmstFPAns>();
	ArrayList<tblmstFPFDetail> FPFDetail = new ArrayList<tblmstFPFDetail>();

	ArrayAdapter<String> adapter;
	int spinpos = 0;
	Calendar c = Calendar.getInstance(Locale.ENGLISH);
	String CurrentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fp_followup);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();

		btnSave = (Button) findViewById(R.id.btnSave);
		tv_WomenName = (TextView) findViewById(R.id.tv_WomenName);
		tv_husbandname = (TextView) findViewById(R.id.tv_husbandname);
		tv_Previousadvice = (TextView) findViewById(R.id.tv_Previousadvice);
		tv_methodadopteddate = (TextView) findViewById(R.id.tv_methodadopteddate);
		tv_Currentadvice = (TextView) findViewById(R.id.tv_Currentadvice);
		spin_MethodAdopted = (Spinner) findViewById(R.id.spin_MethodAdopted);
		spin_Pregnant = (Spinner) findViewById(R.id.spin_Pregnant);
		spin_Pregnanttest = (Spinner) findViewById(R.id.spin_Pregnanttest);
		tv_WomenName.setText(global.getsGlobalWomenName().toString());
		if (global.getsGlobalHusbandName() != null
				&& global.getsGlobalHusbandName().length() > 0) {
			tv_husbandname.setText(String.valueOf(global
					.getsGlobalHusbandName().toString()));
		}
		FP_Ans = dataProvider
				.getFP_Ans(global.getGlobalHHFamilyMemberGUID(), 0);
		if (FP_Ans != null && FP_Ans.size() > 0) {
			tv_Previousadvice.setText(String.valueOf(Validate
					.changeDateFormat(FP_Ans.get(0).getVisitDate())));
		}
		tv_Currentadvice.setText(CurrentDate);
		tv_Currentadvice.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tv_Currentadvice);
			}
		});
		tv_methodadopteddate.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tv_methodadopteddate);
			}
		});
		fillCommonRecord(spin_MethodAdopted, 29, global.getLanguage());
		fillCommonRecord(spin_Pregnanttest, 7, global.getLanguage());
		fillCommonRecord(spin_Pregnant, 7, global.getLanguage());
		spin_Pregnant.setSelection(2);
		spin_Pregnanttest.setSelection(2);
		spin_MethodAdopted
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						// TODO Auto-generated method stub
						if(position==0||position==spinpos){
							tv_methodadopteddate.setEnabled(false);
						}else{
							tv_methodadopteddate.setEnabled(true);
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String sql = "select count(*) from tblFP_followup where womenName_Guid='"
						+ global.getGlobalHHFamilyMemberGUID()
						+ "' and CreatedOn='" + CurrentDate + "'";
				int aa = dataProvider.getMaxRecord(sql);
				if (aa == 0) {
					int diffInDays = 0;
					diffInDays = Weekcalculation();
					if (diffInDays == 0 || diffInDays > 30) {
						saveFPDetail();
					} else {
						CustomAlerts(getResources().getString(
								R.string.VisitExist));
					}
				} else {
					CustomAlerts(getResources().getString(R.string.VisitExist));
				}

			}
		});
		update();
	}

	private int Weekcalculation() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String CurrentDate = sdf.format(new Date());
		Date date, visitdate1;
		int diffInDays = 0;
		String VisitDate = CurrentDate;
		FPFDetail = dataProvider.getFP_Detail(
				global.getGlobalHHFamilyMemberGUID(), 1);

		if (FPFDetail != null && FPFDetail.size() > 0) {
			VisitDate = String.valueOf(FPFDetail.get(0).getCurrF_date());
		}

		try {
			date = sdf.parse(CurrentDate);
			visitdate1 = sdf.parse(VisitDate);

			diffInDays = (int) ((date.getTime() - visitdate1.getTime()) / (1000 * 60 * 60 * 24));
			return diffInDays;
		} catch (ParseException e) {

			e.printStackTrace();
			return 1;
		}

	}

	public void update() {
		try {
			FPFDetail = dataProvider.getFP_Detail(
					global.getGlobalHHFamilyMemberGUID(), 1);
			if (FPFDetail != null && FPFDetail.size() > 0) {
				spin_MethodAdopted.setSelection(FPFDetail.get(0)
						.getMethodAdopted());
				spinpos = FPFDetail.get(0).getMethodAdopted();
				tv_methodadopteddate.setText(String.valueOf(Validate.changeDateFormat(FPFDetail.get(0)
						.getMethodadoptedDate())));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveFPDetail() {
		String WomenName = "";
		String Hushband_name = "";
		String PF_date = "";
		String CurrF_date = "";
		int MethodAdopted = 0;
		int Pregnant = 0;
		int Pregnant_test = 0;
		String FPF_Guid = "";
		String MehodadoptedDate = "";
		String WomenName_Guid = "";
		String Hushband_Guid = "";
		String flag = "";
		int ANMID = 0;
		int AshaID = 0;
		if (global.getsGlobalANMCODE() != null
				&& global.getsGlobalANMCODE().length() > 0) {
			ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		}
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		}
		WomenName = tv_WomenName.getText().toString();
		MehodadoptedDate = Validate.changeDateFormat(tv_methodadopteddate.getText().toString());
		Hushband_name = tv_husbandname.getText().toString();
		PF_date = Validate.changeDateFormat(tv_Previousadvice.getText()
				.toString());
		CurrF_date = Validate.changeDateFormat(tv_Currentadvice.getText()
				.toString());
		if (spin_MethodAdopted.getSelectedItemPosition() > 0) {
			MethodAdopted = returnid(
					spin_MethodAdopted.getSelectedItemPosition() - 1, 29,
					global.getLanguage());
		}
		if (spin_Pregnant.getSelectedItemPosition() > 0) {
			Pregnant = returnid(spin_Pregnant.getSelectedItemPosition() - 1, 7,
					global.getLanguage());
		}
		if (spin_Pregnanttest.getSelectedItemPosition() > 0) {
			Pregnant_test = returnid(
					spin_Pregnanttest.getSelectedItemPosition() - 1, 7,
					global.getLanguage());
		}

		FPF_Guid = Validate.random();
		WomenName_Guid = global.getGlobalHHFamilyMemberGUID();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			Hushband_Guid = extras.getString("husbandguid");
		}
		String sql = "select count(*) from tblFP_followup where womenName_Guid='"
				+ WomenName_Guid + "' and CreatedOn='" + Validate.getcurrentdate() + "'";
		int aa = dataProvider.getMaxRecord(sql);
		if (aa == 0) {
			flag = "I";
		} else {
			flag = "U";
		}

		int ireturn = 0;
		ireturn = dataProvider.InsertFPDetails(ANMID, AshaID, WomenName,
				Hushband_name, PF_date, CurrF_date, MethodAdopted, Pregnant,
				Pregnant_test, FPF_Guid, WomenName_Guid, Hushband_Guid, flag,
				MehodadoptedDate,global.getsGlobalUserID());
		if (ireturn > 0) {
			CustomAlerts(getResources().getString(R.string.savesuccessfully));
			Intent i = new Intent(FP_Follwup.this, FP_AA.class);
			finish();
			startActivity(i);

		} else {
			// CustomAlerts(getResources().getString(R.string.no));
			System.out.print("not saved");

		}

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

				// finish();

				dialog.dismiss();
			}
		});

		// Display the dialog
		dialog.show();

	}

	public int returnid(int POS, int iFlag, int Language) {
		Common = dataProvider.getCommonRecord(Language, iFlag);

		return Common.get(POS).getID();

	}

	private void fillCommonRecord(Spinner spin, int iflag, int Language) {
		Common = dataProvider.getCommonRecord(Language, iflag);

		String sValue[] = new String[Common.size() + 1];
		if (Language == 1)
			sValue[0] = "Select";
		else
			sValue[0] = getResources().getString(R.string.select);
		;
		for (int i = 0; i < Common.size(); i++) {
			sValue[i + 1] = Common.get(i).getValue();
		}
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, sValue);
		spin.setAdapter(adapter);
	}

	public void ShowDatePicker(final TextView txt) {
		datepic = new Dialog(this);
		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker);
		datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		Button btnset = (Button) datepic.findViewById(R.id.set);
		Button btnCancle = (Button) datepic.findViewById(R.id.cancle);
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",
					Locale.ENGLISH);
			Date date;
			Calendar c = Calendar.getInstance(Locale.ENGLISH);
			try {
				date = sdf.parse(txt.getText().toString());
				c.setTime(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			Date date1 = new Date();
			datetext.setMaxDate((long) date1.getTime());
			// set current date into datepicker
			datetext.init(year, month, day, null);
		}
		btnset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				datetext.clearFocus();
				int Mdate = datetext.getDayOfMonth();

				int month = datetext.getMonth() + 1;
				int year = datetext.getYear();
				String date = "" + Mdate;
				String mnth = "" + month;
				if (Mdate < 10) {
					date = "0" + Mdate;
				}
				if (month < 10) {
					mnth = "0" + month;
				}

				String sSellectedDate = date + "-" + mnth + "-"
						+ String.valueOf(year);
				// String sSellectedDate = String.valueOf(Mdate) + "-"
				// + String.valueOf(month + 1) + "-"
				// + String.valueOf(year);
				txt.setText(sSellectedDate);

				// TODO Auto-generated

				datepic.dismiss();

			}
		});
		datepic.show();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(FP_Follwup.this, FP_AA.class);
		finish();
		startActivity(i);
	}

}
