package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Reportindicator_Hnbclist;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

@SuppressLint("InflateParams")
public class AshaDashboardListFormHnbc_Adapter extends BaseAdapter {

	Context context;
	ArrayList<MstASHA> ashaname = new ArrayList<MstASHA>();
	DataProvider dataProvider;
	Global global;

	public AshaDashboardListFormHnbc_Adapter(Context context,
			ArrayList<MstASHA> ashaname) {

		this.context = context;
		this.ashaname = ashaname;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ashaname.size();
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

	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.ashalistform_adapter,
					null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);

		String sql = "select count(*) from tblPregnant_woman where AshaID="
				+ ashaname.get(position).getASHAID() + "";
		int iAshaCount = dataProvider.getMaxRecord(sql);

		TextView txtAshaName = (TextView) gridview
				.findViewById(R.id.txtAshaName);
		TextView tvNumberOfPregWomen = (TextView) gridview
				.findViewById(R.id.tvNumberOfPregWomen);

		txtAshaName.setText(String
				.valueOf(ashaname.get(position).getASHAName()));
		tvNumberOfPregWomen.setText(String.valueOf(iAshaCount));

		txtAshaName.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				global.setsGlobalAshaCode(String.valueOf(ashaname.get(position)
						.getASHAID()));
				Intent i = new Intent(context, Reportindicator_Hnbclist.class);
				context.startActivity(i);

			}
		});

		return gridview;
	}

}
