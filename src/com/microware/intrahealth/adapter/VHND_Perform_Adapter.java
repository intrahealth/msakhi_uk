package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_PerformanceIndicator;
import com.microware.intrahealth.object.tbl_Incentive;

public class VHND_Perform_Adapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<MstVHND_PerformanceIndicator> VHND_per;
	String Date = "";
	String AshaID = "";
	Global global;
	String OPT_Value = "";

	public VHND_Perform_Adapter(Context context,
			ArrayList<MstVHND_PerformanceIndicator> VHND_per, String AshaID,
			String OPT_Value) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHND_per = VHND_per;
		this.AshaID = AshaID;
		this.OPT_Value = OPT_Value;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VHND_per.size();
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
			global = (Global) context.getApplicationContext();

			gridview = layoutInflater.inflate(R.layout.vhnd_perform_adpter,
					null);
			TextView ID = (TextView) gridview.findViewById(R.id.ID);
			TextView Item = (TextView) gridview.findViewById(R.id.Item);
			// final Button Item_Id = (Button)
			// gridview.findViewById(R.id.Item_Id);
			dataProvider = new DataProvider(context);
			ID.setText(String.valueOf(VHND_per.get(position).getID()));
			Item.setText(String.valueOf(VHND_per.get(position).getItem()));
			// Spinner spin_yesno = (Spinner) gridview
			// .findViewById(R.id.spin_yesno);
			// if (OPT_Value != null && !OPT_Value.equalsIgnoreCase("")
			// && OPT_Value.length() == VHND_per.size())// &&
			// {
			// spin_yesno.setSelection(Integer.valueOf(OPT_Value.substring(
			// position, position + 1)));
			// }

//			RadioGroup YesNo_RG = (RadioGroup) gridview
//					.findViewById(R.id.YesNo_RG);
			RadioButton Yes_RB = (RadioButton) gridview
					.findViewById(R.id.Yes_RB);
			RadioButton No_RB = (RadioButton) gridview.findViewById(R.id.No_RB);
			RadioButton Clear_RB = (RadioButton) gridview
					.findViewById(R.id.Clear_RB);
			if (OPT_Value != null && !OPT_Value.equalsIgnoreCase("")
					&& OPT_Value.length() == VHND_per.size()) {
				int Value = Integer.valueOf(OPT_Value.substring(position,
						position + 1));
				if (Value == 0) {
					Clear_RB.setChecked(true);
				} else if (Value == 1) {
					Yes_RB.setChecked(true);
				} else if (Value == 2) {
					No_RB.setChecked(true);
				}
			}

		} else {
			gridview = convertView;
		}

		return gridview;
	}
}
