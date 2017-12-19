package com.microware.intrahealth.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.microware.intrahealth.FP_MemberVisit;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HomeVisitPnc;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPQues;

@SuppressLint("InflateParams")
public class FP_MemberVisitAdapter extends BaseAdapter {

	ArrayList<tbl_pregnantwomen> pregnant;
	Context context;
	Global global;
	DataProvider dataProvider;
	ArrayList<tblmstFPQues> Questions;
	int iflag = 0;
	Dialog timepic;
	TimePicker time;
	String sDueVisits = "";
	String LMPDate = "";
	String RegistrationDate = "";
	int CurrentPage = 0;
	Dialog PicVideo_PreviewPopUp;
	String ans;
	TextView weight1, weight2, weight3, weight4, bp1, bp2, bp3, bp4, hb1, hb2,
			hb3, hb4, us1, us2, us3, us4, ua1, ua2, ua3, ua4;

	public FP_MemberVisitAdapter(Context context,
			ArrayList<tblmstFPQues> Questions, int currentpage) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.Questions = Questions;
		this.CurrentPage = currentpage;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		if (CurrentPage == 1000) {
			return Questions.size();
		} else {
			return 1;
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unused")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.fp_membervisitadapter,
					null);
		} else {
			gridview = convertView;
		}

		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();
		TextView tvQuestions = (TextView) gridview
				.findViewById(R.id.tvQuestions);
		TextView tvQuestions2 = (TextView) gridview
				.findViewById(R.id.tvQuestions2);
		TextView ansfield = (TextView) gridview.findViewById(R.id.ansfield);
		final TextView ansQ = (TextView) gridview.findViewById(R.id.ans);
		EditText etans = (EditText) gridview.findViewById(R.id.etans);
		TextView ansy = (TextView) gridview.findViewById(R.id.ansy);
		TextView ansn = (TextView) gridview.findViewById(R.id.ansn);
		// TextView ansetditfloat = (TextView) itemView
		// .findViewById(R.id.ansetditfloat);
		final TextView tvdate = (TextView) gridview.findViewById(R.id.tvdate);
		final TextView tvtime = (TextView) gridview.findViewById(R.id.tvtime);
		RadioGroup rdgrp = (RadioGroup) gridview.findViewById(R.id.rdgrp);
		final TableRow tblrdgrp2 = (TableRow) gridview
				.findViewById(R.id.tblrdgrp2);
		TableRow tblrdgrp3 = (TableRow) gridview.findViewById(R.id.tblrdgrp3);
		TableRow tblrdgrptime = (TableRow) gridview
				.findViewById(R.id.tblrdgrptime);

		tblrdgrp2.setVisibility(View.GONE);
		tblrdgrp3.setVisibility(View.GONE);
		tblrdgrptime.setVisibility(View.GONE);
		if (CurrentPage == 1000) {
			String ans = Questions.get(position).getOinfo();
			tvQuestions.setText(Questions.get(position).getFtext());
			ansfield.setText(Questions.get(position).getOinfo());
			if (Questions.get(position).getY_qid() > 0) {
				ansy.setText(String.valueOf(Questions.get(position).getY_qid()));

			}
			if (Questions.get(position).getN_qid() > 0) {
				ansn.setText(String.valueOf(Questions.get(position).getN_qid()));

			}

		} else {
			ans = Questions.get(CurrentPage).getOinfo();
			tvQuestions.setText(Questions.get(CurrentPage).getFtext());
			ansfield.setText(Questions.get(CurrentPage).getOinfo());
			if (Questions.get(CurrentPage).getY_qid() > 0) {
				ansy.setText(String.valueOf(Questions.get(CurrentPage)
						.getY_qid()));

			}
			if (Questions.get(CurrentPage).getN_qid() > 0) {
				ansn.setText(String.valueOf(Questions.get(CurrentPage)
						.getN_qid()));

			}
		}
		tvdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvdate, ansQ);
			}
		});

		if (ans != null && ans.length() > 0) {
			if (ans.contains("$")) {
				tblrdgrp3.setVisibility(View.GONE);
				// tblrdgrp2.setVisibility(View.VISIBLE);
				tblrdgrptime.setVisibility(View.GONE);
				String[] ar = ans.split("[$]");

				final RadioButton[] rb = new RadioButton[ar.length];
				if (rdgrp.getChildCount() == 0) {
					for (int i = 0; i < ar.length; i++) {
						rdgrp.setWeightSum(ar.length);
						rb[i] = new RadioButton(context);
						int id = i + 1;
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT,
								LayoutParams.MATCH_PARENT, 1f);
						rb[i].setLayoutParams(params);
						rdgrp.addView(rb[i]); // the RadioButtons
												// are added to the
												// radioGroup
												// instead of the
												// layout
						rb[i].setText(ar[i]);
						rb[i].setId(id);
						rb[i].setTextColor(Color.BLACK);

					}
				}

			} else if (ans.equalsIgnoreCase("Date")) {
				tblrdgrp2.setVisibility(View.VISIBLE);
				tblrdgrp3.setVisibility(View.GONE);
				tblrdgrptime.setVisibility(View.GONE);

			}

		}
		rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case 0:
					ansQ.setText("0");
					break;
				case 1:
					ansQ.setText("1");

					break;
				case 2:
					ansQ.setText("2");

					break;
				case 3:
					ansQ.setText("3");
					break;
				case 4:
					ansQ.setText("4");
					break;
				case 5:
					ansQ.setText("5");
					break;
				case 6:
					ansQ.setText("6");
					break;
				}

			}
		});
		try {
			if (Questions.get(CurrentPage).getAudio_FileName() != null
					&& Questions.get(CurrentPage).getAudio_FileName().length() > 0) {

				((FP_MemberVisit) context).PlayAudio(Questions.get(
						CurrentPage).getAudio_FileName());

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return gridview;
	}

	public void ShowDatePicker(final TextView txt, final TextView datatv2) {
		String languageToLoad = "en"; // your language
		final Dialog datepic;
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		// TODO Auto-generated method stub
		datepic = new Dialog(context, R.style.CustomTheme);

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
		//datetext.setMinDate((long) date.getTime());

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
				txt.setText(sSellectedDate);
				datatv2.setText(Validate.changeDateFormat(sSellectedDate));
				datepic.dismiss();
			}
		});

	}
}