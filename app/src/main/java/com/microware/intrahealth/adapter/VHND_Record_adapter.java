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
import com.microware.intrahealth.VHND_DueList_Report;
import com.microware.intrahealth.VHND_Performance;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_VHND_DueList;

public class VHND_Record_adapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tbl_VHNDPerformance> VHND_perform;
	ArrayList<tbl_VHND_DueList> VNHDDuelistss;
	// String Date = "";
	int flag = 0;
	Global global;

	public VHND_Record_adapter(Context context,
			ArrayList<tbl_VHNDPerformance> VHND_perform, int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHND_perform = VHND_perform;
		this.flag = flag;
	}

	public VHND_Record_adapter(Context context,
			ArrayList<tbl_VHND_DueList> VNHDDuelistss, int flag, String ss) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VNHDDuelistss = VNHDDuelistss;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int ivalue = 0;
		if (flag == 1) {
			ivalue = VHND_perform.size();
		} else if (flag == 2) {
			ivalue = VNHDDuelistss.size();
		}
		return ivalue;
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
			TextView tx_date_show = (TextView) gridview
					.findViewById(R.id.tx_date_show);
			Button btn_Add = (Button) gridview.findViewById(R.id.btn_Add);
			Button btn_edit = (Button) gridview.findViewById(R.id.btn_edit);
			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();

			btn_Add.setVisibility(View.GONE);

			btn_Add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// global.setVHND_Composite_Flag("U");
					// global.setVHND_ID(VHND_perform.get(position).getVHND_ID());
					// Intent in = new Intent(context,
					// VHND_Performance.class);
					// context.startActivity(in);
				}
			});
			String dateee[] = {};
			String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
					"Aug", "Sep", "Oct", "Nov", "Dec" };
			int month = 0, Year = 0;
			if (flag == 1) {
				dateee = String.valueOf(VHND_perform.get(position).getDate())
						.split("-");
			} else if (flag == 2) {
				dateee = String.valueOf(VNHDDuelistss.get(position).getDate())
						.split("-");
			}
			month = Integer.valueOf(dateee[1]);
			Year = Integer.valueOf(dateee[0]);
			String Monthn = "";
			if (dateee != null) {
				Monthn = aa[month - 1];
			}
			if (flag == 1) {
				tx_slno.setText(String.valueOf(VHND_perform.get(position)
						.getPID()));

				String VillageName = "Select AW_Name from VHND_Schedule where ASHA_ID="
						+ global.getsGlobalAshaCode()
						+ " and Year='"
						+ Year
						+ "' and "
						+ Monthn
						+ "='"
						+ VHND_perform.get(position).getDate()
						+ "' and Village_ID='"
						+ VHND_perform.get(position).getVillageId() + "'";
				village.setText(dataProvider.getRecord(VillageName));
				tx_date.setText(String.valueOf(VHND_perform.get(position)
						.getDate()));
				tx_date_show.setText(Validate.changeDateFormat(String
						.valueOf(VHND_perform.get(position).getDate())));
				String sqlll = "Select cast(round(julianday('"
						+ String.valueOf(VHND_perform.get(position).getDate())
						+ "')-julianday('NOW')) as int)";
				int Daycount = Integer.valueOf(dataProvider.getRecord(sqlll));
				if (Daycount <= 0) {
					btn_edit.setBackgroundDrawable(context.getResources()
							.getDrawable(R.drawable.btn_bg_custom));
					btn_edit.setEnabled(true);
				} else {
					btn_edit.setBackgroundDrawable(context.getResources()
							.getDrawable(R.color.gray));
					btn_edit.setEnabled(false);
				}
			} else if (flag == 2) {
				tx_slno.setText(String.valueOf(VNHDDuelistss.get(position)
						.getPID()));
				String VillageName = "Select AW_Name from VHND_Schedule where ASHA_ID="
						+ global.getsGlobalAshaCode()
						+ " and Year='"
						+ Year
						+ "' and "
						+ Monthn
						+ "='"
						+ VNHDDuelistss.get(position).getDate()
						+ "' and Village_ID='"
						+ VNHDDuelistss.get(position).getVillageId() + "'";
				village.setText(dataProvider.getRecord(VillageName));
				tx_date.setText(String.valueOf(VNHDDuelistss.get(position)
						.getDate()));
				tx_date_show.setText(Validate.changeDateFormat(String
						.valueOf(VNHDDuelistss.get(position).getDate())));
			}

			btn_edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (flag == 1) {
						global.setVHND_Date(String.valueOf(VHND_perform.get(
								position).getDate()));
						global.setVHND_VillageID(String.valueOf(VHND_perform
								.get(position).getVillageId()));
						global.setVHND_Composite_Flag("U");
						global.setVHND_ID(VHND_perform.get(position)
								.getVHND_ID());
						Intent in = new Intent(context, VHND_Performance.class);
						in.putExtra("ActivityName", "Dashboard_Activity");
						context.startActivity(in);
					} else if (flag == 2) {
						global.setVHND_Date(String.valueOf(VNHDDuelistss.get(
								position).getDate()));
						global.setVHND_VillageID(String.valueOf(VNHDDuelistss
								.get(position).getVillageId()));
						global.setVHND_ID(VNHDDuelistss.get(position)
								.getVHND_ID());
						Intent in = new Intent(context,
								VHND_DueList_Report.class);
						in.putExtra("ActivityName", "Report_Module");
						context.startActivity(in);

					}
				}
			});

		} else {
			gridview = convertView;
		}
		return gridview;
	}

}