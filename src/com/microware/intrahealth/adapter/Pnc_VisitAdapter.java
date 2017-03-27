package com.microware.intrahealth.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HomeVisitPnc;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_pregnantwomen;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class Pnc_VisitAdapter extends BaseAdapter {

	ArrayList<TblANCVisit> VisitANC;
	ArrayList<tbl_pregnantwomen> Pregnant;
	ArrayList<tblPNChomevisit_ANS> pnc;
	ArrayList<tblChild> child;
	Context context;
	Global global;
	DataProvider dataProvider;
	String PWGUID = "";
	String childguid = "";

	public Pnc_VisitAdapter(Context context, String sPWGUID, String childguid) {
		// TODO Auto-generated constructor stub
		this.context = context;
		// this.VisitANC = VisitANC;
		this.PWGUID = sPWGUID;
		this.childguid = childguid;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7;
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
			gridview = layoutInflater.inflate(R.layout.pncvisitadapter, null);
		} else {
			gridview = convertView;
		}

		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();
		TextView tvsno = (TextView) gridview.findViewById(R.id.tvsno);
		TextView tvlastvisit = (TextView) gridview
				.findViewById(R.id.tvlastvisit);
		ImageView imageedit = (ImageView) gridview.findViewById(R.id.imageedit);
		pnc = dataProvider.getpncdata(PWGUID, childguid, position + 1, 1);
		child = dataProvider.gettblChild(PWGUID, 4);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String datenew = Validate.getcurrentdate();
		System.out.println(dateFormat.format(date));
		String newdate = String.valueOf(child.get(0).getChild_dob());
		String dob = "";
		if (newdate != null && newdate.length() > 0) {
			String[] Cdt = newdate.split("-");
			String DD = Cdt[0];
			String MM = Cdt[1];
			String YYYY = Cdt[2];
			if (MM.length() == 1) {
				MM = "0" + Cdt[1];
			}
			dob = DD + "-" + MM + "-" + YYYY;

		}
		Date d1 = null;
		Date d2 = null;

		try {
			d1 = dateFormat.parse(dob);
			d2 = dateFormat.parse(datenew);

			// in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			if (position == 0) {
				if (diffDays >= 0 && diffDays <= 1) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));

				}
			} else if (position == 1) {
				if (diffDays >= 2 && diffDays <= 3) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}
			} else if (position == 2) {
				if (diffDays >= 6 && diffDays <= 7) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}
			} else if (position == 3) {
				if (diffDays >= 13 && diffDays <= 14) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}
			} else if (position == 4) {
				if (diffDays >= 20 && diffDays <= 21) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}
			} else if (position == 5) {
				if (diffDays >= 27 && diffDays <= 28) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}
			} else if (position == 6) {
				if (diffDays >= 41 && diffDays <= 42) {
					imageedit.setEnabled(true);
				} else {
					imageedit.setEnabled(false);
					tvlastvisit.setBackgroundColor(context.getResources()
							.getColor(R.color.WhiteSmoke));
				}

			}
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
		if (child.get(0).getPlace_of_birth() > 0) {
			if (child.get(0).getPlace_of_birth() == 2) {
				if (position == 0) {

					tvlastvisit.setText(child.get(0).getChild_dob());
					imageedit.setEnabled(false);
				}
			}
		}
		tvsno.setText(context.getResources().getString(R.string.visitpnc) + "-"
				+ String.valueOf(position + 1));
		if (pnc != null && pnc.size() > 0) {
			if (pnc.get(0).getQ_0() != null && pnc.get(0).getQ_0().length() > 0) {
				tvlastvisit.setText(pnc.get(0).getQ_0());
			} else {
				tvlastvisit.setText(pnc.get(0).getPNCGUID());

			}
		}
		imageedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				global.setsGlobalPWGUID(PWGUID);
				global.setsGlobalChildGUID(childguid);
				pnc = dataProvider.getpncdata(PWGUID, "0", position + 1, 1);
				if (pnc != null && pnc.size() > 0) {
					global.setsPncGUID(pnc.get(0).getPNCGUID());
				}
				global.setVisitno(position + 1);
				Intent in = new Intent(context, HomeVisitPnc.class);

				// ((AncActivity) context).finish();
				context.startActivity(in);

			}
		});

		return gridview;

	}

}