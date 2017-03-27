package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.VHND;
import com.microware.intrahealth.VHND_Performance;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_VHNDPerformance;

public class VHND_Record_adapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tbl_VHNDPerformance> VHND_perform;
	// String Date = "";
	int AshaID = 0;
	Global global;

	public VHND_Record_adapter(Context context,
			ArrayList<tbl_VHNDPerformance> VHND_perform, int AshaID) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHND_perform = VHND_perform;
		this.AshaID = AshaID;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VHND_perform.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			// dataProvider = new DataProvider(context);
			gridview = new View(context);
			dataProvider = new DataProvider(context);
			gridview = layoutInflater.inflate(R.layout.vhnd_record_adapter,
					null);
			TextView tx_slno = (TextView) gridview.findViewById(R.id.tx_slno);
			TextView village = (TextView) gridview.findViewById(R.id.village);
			TextView tx_date = (TextView) gridview.findViewById(R.id.tx_date);
			Button btn_Add = (Button) gridview.findViewById(R.id.btn_Add);
			Button btn_edit = (Button) gridview.findViewById(R.id.btn_edit);
			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();
			tx_slno.setText(String.valueOf(VHND_perform.get(position).getPID()));

			String VillageName = "Select VillageName from MstVillage where VillageID='"
					+ VHND_perform.get(position).getVillageId()
					+ "' and LanguageID=1";
			village.setText(String.valueOf(dataProvider.getRecord(VillageName)));
			tx_date.setText(String
					.valueOf(VHND_perform.get(position).getDate()));

			btn_Add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					global.setVHND_Composite_Flag("U");
					global.setVHND_ID(VHND_perform.get(position).getVHND_ID());
					Intent in = new Intent(context, VHND_Performance.class);
					context.startActivity(in);
				}
			});
			btn_edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					global.setVHND_Composite_Flag("U");
					global.setVHND_ID(VHND_perform.get(position).getVHND_ID());
					Intent in = new Intent(context, VHND.class);
					context.startActivity(in);
				}
			});

		} else {
			gridview = convertView;
		}
		return gridview;
	}

}