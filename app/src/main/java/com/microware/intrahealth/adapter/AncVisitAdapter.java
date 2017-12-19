package com.microware.intrahealth.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.Anc;
import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class AncVisitAdapter extends BaseAdapter {

	ArrayList<TblANCVisit> VisitANC;
	Context context;
	Global global;
	DataProvider dataProvider;
	ArrayList<tbl_pregnantwomen> pregnant;
	ArrayList<Tbl_HHFamilyMember> spouse;
	int ashaid = 0;

	public AncVisitAdapter(Context context, ArrayList<TblANCVisit> VisitANC) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VisitANC = VisitANC;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VisitANC.size();
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
			gridview = layoutInflater.inflate(R.layout.visit_gridadapter, null);
		} else {
			gridview = convertView;
		}

		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();
		TextView tvsno = (TextView) gridview.findViewById(R.id.tvsno);
		TextView tvlastvisit = (TextView) gridview
				.findViewById(R.id.tvlastvisit);

		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}

		pregnant = dataProvider.getPregnantWomendata(VisitANC.get(position)
				.getPWGUID(), 1, ashaid);
		TableRow tablrowvisit = (TableRow) gridview
				.findViewById(R.id.tablrowvisit);
		ImageView imageedit = (ImageView) gridview.findViewById(R.id.imageedit);
		// ImageView imagedelete = (ImageView) gridview
		// .findViewById(R.id.imagedelete);
		if ((VisitANC.get(position).getVisit_No()) > 0) {

			// imagedelete.setEnabled(false);
		}

		tvsno.setText(context.getResources().getString(R.string.anccheckup)
				+ "-" + String.valueOf(VisitANC.get(position).getVisit_No()));
		tvlastvisit.setText(Validate.changeDateFormat(VisitANC.get(position)
				.getCheckupVisitDate()));

		if (pregnant != null && pregnant.size() > 0) {
			if (pregnant.get(0).getPWRegistrationDate() != null
					&& pregnant.get(0).getPWRegistrationDate().length() > 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
				Date date = new Date();
				String datenew = Validate.getcurrentdate();
				System.out.println(dateFormat.format(date));
				String newdate = String.valueOf(pregnant.get(0).getLMPDate());
				String lmpdate = "";
				if (newdate != null && newdate.length() > 0) {
					String[] Cdt = newdate.split("-");
					String DD = Cdt[0];
					String MM = Cdt[1];
					String YYYY = Cdt[2];
					if (MM.length() == 1) {
						MM = "0" + Cdt[1];
					}
					lmpdate = DD + "-" + MM + "-" + YYYY;

				}
				Date d1 = null;
				Date d2 = null;

				try {
					d1 = dateFormat.parse(lmpdate);
					d2 = dateFormat.parse(datenew);

					// in milliseconds
					long diff = d2.getTime() - d1.getTime();

					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					if (position == 0) {
						if (diffDays >= 0 && diffDays <= 98) {
							tvsno.setBackgroundColor(Color.BLUE);
							tvlastvisit.setBackgroundColor(Color.BLUE);
							// imageedit.setEnabled(true);
						} else if (diffDays > 98
								&& VisitANC.get(position).getCheckupVisitDate() == null
								|| VisitANC.get(position).getCheckupVisitDate()
										.length() == 0) {
							tvsno.setBackgroundColor(context.getResources()
									.getColor(R.color.Orange));
							tvlastvisit.setBackgroundColor(context
									.getResources().getColor(R.color.Orange));
							// imageedit.setEnabled(false);
						}
						if (VisitANC.get(position).getCheckupVisitDate() != null
								&& VisitANC.get(position).getCheckupVisitDate()
										.length() > 0) {
							tvsno.setBackgroundColor(Color.GREEN);
							tvlastvisit.setBackgroundColor(Color.GREEN);
							// imageedit.setEnabled(true);
						}
					} else if (position == 1) {
						if (diffDays < 91) {
							imageedit.setEnabled(false);
						}
						if (diffDays >= 91 && diffDays <= 196) {
							tvsno.setBackgroundColor(Color.BLUE);
							tvlastvisit.setBackgroundColor(Color.BLUE);
							imageedit.setEnabled(true);
						} else if (diffDays > 196
								&& (VisitANC.get(position)
										.getCheckupVisitDate() == null || VisitANC
										.get(position).getCheckupVisitDate()
										.length() == 0)) {
							tvsno.setBackgroundColor(context.getResources()
									.getColor(R.color.Orange));
							tvlastvisit.setBackgroundColor(context
									.getResources().getColor(R.color.Orange));
							// imageedit.setEnabled(false);
						}
						if (VisitANC.get(position).getCheckupVisitDate() != null
								&& VisitANC.get(position).getCheckupVisitDate()
										.length() > 0) {
							tvsno.setBackgroundColor(Color.GREEN);
							tvlastvisit.setBackgroundColor(Color.GREEN);
							imageedit.setEnabled(true);
						}
					} else if (position == 2) {
						if (diffDays < 189) {
							imageedit.setEnabled(false);
						}
						if (diffDays >= 189 && diffDays <= 252) {
							tvsno.setBackgroundColor(Color.BLUE);
							tvlastvisit.setBackgroundColor(Color.BLUE);
							imageedit.setEnabled(true);
						} else if (diffDays > 252
								&& (VisitANC.get(position)
										.getCheckupVisitDate() == null || VisitANC
										.get(position).getCheckupVisitDate()
										.length() == 0)) {
							tvsno.setBackgroundColor(context.getResources()
									.getColor(R.color.Orange));
							tvlastvisit.setBackgroundColor(context
									.getResources().getColor(R.color.Orange));
							// imageedit.setEnabled(false);
						}
						if (VisitANC.get(position).getCheckupVisitDate() != null
								&& VisitANC.get(position).getCheckupVisitDate()
										.length() > 0) {
							tvsno.setBackgroundColor(Color.GREEN);
							tvlastvisit.setBackgroundColor(Color.GREEN);
							imageedit.setEnabled(true);
						}
					} else if (position == 3) {

						if (diffDays < 245) {
							imageedit.setEnabled(false);
						} else if (diffDays >= 245
								&& (VisitANC.get(position)
										.getCheckupVisitDate() == null || VisitANC
										.get(position).getCheckupVisitDate()
										.length() == 0)) {
							tvsno.setBackgroundColor(Color.BLUE);
							tvlastvisit.setBackgroundColor(Color.BLUE);
							imageedit.setEnabled(true);

						}
						if (diffDays > 245
								&& (VisitANC.get(position)
										.getCheckupVisitDate() != null && VisitANC
										.get(position).getCheckupVisitDate()
										.length() > 0)) {
							tvsno.setBackgroundColor(Color.GREEN);
							tvlastvisit.setBackgroundColor(Color.GREEN);
							imageedit.setEnabled(true);
						}
					}

				} catch (Exception e) {

				}
			}
		}

		imageedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// global.setiCurrentActiveTab(0);
				// if (pregnant.get(0).getHHFamilyMemberGUID() != null
				// && pregnant.get(0).getHHFamilyMemberGUID().length() > 0)
				if (pregnant.size() > 0) {
					spouse = dataProvider.getAllmalename(pregnant.get(0)
							.getHHFamilyMemberGUID());
				}
				if (spouse != null && spouse.size() > 0) {
					global.setsGlobalSpouseGUID(spouse.get(0).getSpouse());
				}
				global.setVisitno(VisitANC.get(position).getVisit_No());
				global.setsGlobalANCVisitGUID(VisitANC.get(position)
						.getVisitGUID());
				global.setsGlobalPWGUID(VisitANC.get(position).getPWGUID());
				Intent in = new Intent(context, Anc.class);
				((AncActivity) context).finish();
				context.startActivity(in);

			}
		});

		return gridview;

	}

}
