package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.NewChildActivity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.childActivity;
import com.microware.intrahealth.newbornGrid;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblChild;

@SuppressLint("InflateParams")
public class NewbornGridAdapter extends BaseAdapter {
	Context context;
	ArrayList<tblChild> Child=new ArrayList<tblChild>();
	ArrayList<Tbl_HHFamilyMember> pregnant=new ArrayList<Tbl_HHFamilyMember>();
	Global global;
	DataProvider dataProvider;
	int StatusId=0;

	public NewbornGridAdapter(Context context,
			ArrayList<tblChild> Child) {
		this.Child = Child;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Child.size();
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
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.newbornadapter,
					null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);
		TextView tvChild = (TextView) gridview.findViewById(R.id.tvChild);
		ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
		ImageView btnDelete = (ImageView) gridview
				.findViewById(R.id.btnDelete);
		
	if(Child.get(position).getPw_GUID()!=null && Child.get(position).getPw_GUID().length()>0){
		pregnant=dataProvider.getAllmalename(Child.get(position).getHHFamilyMemberGUID());
		if(pregnant!=null && pregnant.size()>0){
		tvChild.setText(String.valueOf(Child.get(position).getChild_name()+"/"+pregnant.get(0).getFamilyMemberName()+"/"+Validate.changeDateFormat(Child.get(position).getChild_dob())));
//		 tvChild.setBackgroundColor(Color.parseColor("#55FF0000"));
		 tvChild.setTextColor(Color.RED);
		}
	}else{
		pregnant=dataProvider.getAllmalename(Child.get(position).getHHFamilyMemberGUID());
		if(pregnant!=null && pregnant.size()>0){
		tvChild.setText(String.valueOf(Child.get(position).getChild_name()+"/"+pregnant.get(0).getFamilyMemberName()+"/"+Validate.changeDateFormat(Child.get(position).getChild_dob())));
		 tvChild.setTextColor(Color.BLUE);
//		tvChild.setBackgroundColor(color.holo_orange_light);
		}
	}
		
		
		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setsGlobalChildGUID(Child.get(position)
						.getChildGUID());
				if(Child.get(position).getPw_GUID()!=null && Child.get(position).getPw_GUID().length()>0){
				Intent i = new Intent(v.getContext(),
						childActivity.class);
				context.startActivity(i);
				}else{
					Intent i = new Intent(v.getContext(),
							NewChildActivity.class);
					context.startActivity(i);
				}
			}
		});
		
		btnDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setsGlobalChildGUID(Child.get(position)
						.getChildGUID());
				String ChildGUID = Child.get(position).getChildGUID();
				String sql="Delete from tblChild where ChildGUID='"+ChildGUID+"'";
				dataProvider.deleteRecord(sql);
				Intent in = new Intent(context, newbornGrid.class);
				context.startActivity(in);
				
			}
		});
		return gridview;
	}

}
