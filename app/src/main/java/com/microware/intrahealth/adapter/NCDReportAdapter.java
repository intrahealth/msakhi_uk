package com.microware.intrahealth.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.microware.intrahealth.FP_Follwup;
import com.microware.intrahealth.FP_MemberVisit;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblncdfollowup;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class NCDReportAdapter extends BaseAdapter {
	Context context;
	ArrayList<tblncdfollowup> ncdfollowup;
	Global global;
	DataProvider dataProvider;
	int flag = 0;
	TableRow GridRow;

	public NCDReportAdapter(Context context,
			ArrayList<tblncdfollowup> ncdfollowup, int flag) {
		this.ncdfollowup = ncdfollowup;
		this.context = context;
		this.flag = flag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ncdfollowup.size();
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

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater
					.inflate(R.layout.ncdreportadapter, null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);
		GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
		TextView tv2 = (TextView) gridview.findViewById(R.id.tv2);
		TextView tv1 = (TextView) gridview.findViewById(R.id.tv1);
		TextView tv3 = (TextView) gridview.findViewById(R.id.tv3);
		TextView tv4 = (TextView) gridview.findViewById(R.id.tv4);
		if (flag == 4||flag == 5) {
			tv4.setVisibility(View.GONE);
		}
		tv1.setText(String.valueOf(position+1));
		tv2.setText(Validate.changeDateFormat(ncdfollowup.get(position).getRegistrationDate()));

		tv3.setText(ncdfollowup.get(position).getRBS());
		tv4.setText(ncdfollowup.get(position).getBP());
		if((position+1)%2==0){
			GridRow.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
		}
		
		return gridview;
	}

	

}
