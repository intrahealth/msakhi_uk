package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_Incentive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Incentives_Adapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tbl_Incentive> incentives;
	String Date = "";
	int AshaID = 0;

	public Incentives_Adapter(Context context,
			ArrayList<tbl_Incentive> incentives, String Date, int AshaID) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.incentives = incentives;
		this.Date = Date;
		this.AshaID = AshaID;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return incentives.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			// dataProvider = new DataProvider(context);
			gridview = new View(context);
			dataProvider = new DataProvider(context);
			gridview = layoutInflater.inflate(R.layout.incentive_adapter, null);
			TextView SlNo = (TextView) gridview.findViewById(R.id.SlNo);
			TextView Activities = (TextView) gridview
					.findViewById(R.id.Activities);
			TextView Count = (TextView) gridview.findViewById(R.id.Count);
			TextView Amount = (TextView) gridview.findViewById(R.id.Amount);
			dataProvider = new DataProvider(context);
			SlNo.setText(String.valueOf(incentives.get(position).getIUID()));
			Activities.setText(String.valueOf(incentives.get(position)
					.getShortName()));
			String sql = "";
			int Item_id = incentives.get(position).getItem_id();
			int Counts = 0;
			try {
				dataProvider = new DataProvider(context);
				switch (Item_id) {
				case 1:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ Date + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 2:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ Date + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 3:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ Date + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 4:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ Date + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 5:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ Date + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				default:
					Count.setText("0");
					Amount.setText(String.valueOf(Amounts(0, Integer
							.valueOf(incentives.get(position).getAmount()))));

				}

			} catch (Exception exception) {
				Log.e("DataProvider",
						"Error in tbl_Incentive :: " + exception.getMessage());
			}

		} else {
			gridview = convertView;
		}

		return gridview;
	}

	public String Amounts(int count, int Aoumt) {
		String Amount = "";
		Amount = String.valueOf(Aoumt) + " * " + String.valueOf(count) + " = "
				+ String.valueOf(Aoumt * count);

		return Amount;
	}
}
