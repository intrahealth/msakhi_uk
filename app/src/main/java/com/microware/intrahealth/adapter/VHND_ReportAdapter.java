package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.ImunizationChildList;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tblVHNDDuelist;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPFDetail;

public class VHND_ReportAdapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tblVHNDDuelist> VHNDDuelist;

	// String Date = "";
	int AshaID = 0;
	Global global;
	String Date;

	int flag = 0;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
	int RefreshFlag = 0;
	String ActivityName = "";

	// String sql44 = "", sql55 = "";

	public VHND_ReportAdapter(Context context,
			ArrayList<tblVHNDDuelist> VHNDDuelist, int AshaID, String Date,
			int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHNDDuelist = VHNDDuelist;
		this.AshaID = AshaID;
		this.Date = Date;
		this.flag = flag;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int value = 0;
		if (flag == 1) {
			value = VHNDDuelist.size();
		} else if (flag == 2) {
			value = VHNDDuelist.size();
		}
		return value;
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
			gridview = layoutInflater.inflate(R.layout.vhndreportadapter, null);
			TextView Name = (TextView) gridview.findViewById(R.id.Name);
			ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
			Name.setText(VHNDDuelist.get(position).getName());
			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();
			String Date = VHNDDuelist.get(position).getActual_VHNDDate();

			String guid = VHNDDuelist.get(position).getBeneficiaryGUID();
			if (flag == 1) {
				String sql = "";
				sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on   a.pwguid=b.pwguid where checkupvisitdate ='"
						+ Date
						+ "' and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
						+ VHNDDuelist.get(position).getAshaID()
						+ " and a.AshaID="
						+ VHNDDuelist.get(position).getAshaID()
						+ " and a.pwguid='" + guid + "'";

				int retunr = dataProvider.getMaxRecord(sql);
				if (retunr > 0) {
					btnEdit.setImageResource(R.drawable.yes);
				} else {
					btnEdit.setImageResource(R.drawable.no);
				}

			} else if (flag == 2) {
				String sql = "";
				sql = "Select count(*) from tblCHild where ((bcg='" + Date
						+ "' ) or (hepb1='" + Date + "')  or ( hepb2='" + Date
						+ "')  or ( hepb3='" + Date + "') or (opv1='" + Date
						+ "') or (opv2='" + Date + "') or (opv3='" + Date
						+ "')  or ( dpt1='" + Date + "') or ( dpt2='" + Date
						+ "') or (dpt3='" + Date + "') or ( Pentavalent1='"
						+ Date + "') or( Pentavalent2='" + Date
						+ "') or ( Pentavalent3='" + Date + "')or ( measeals='"
						+ Date + "') or ( vitaminA='" + Date
						+ "') or ( VitaminAtwo='" + Date
						+ "') or (OPVBooster='" + Date + "') or ( DPTBooster='"
						+ Date + "') or (DPTBoostertwo='" + Date
						+ "')  or (JEVaccine1='" + Date
						+ "') or ( JEVaccine2='" + Date + "') ) and AshaID="
						+ VHNDDuelist.get(position).getAshaID()
						+ " and childguid='" + guid + "'";
				int retunr = dataProvider.getMaxRecord(sql);
				if (retunr > 0) {
					btnEdit.setImageResource(R.drawable.yes);
				} else {
					btnEdit.setImageResource(R.drawable.no);
				}

			}

		} else {
			gridview = convertView;
		}
		return gridview;
	}
}