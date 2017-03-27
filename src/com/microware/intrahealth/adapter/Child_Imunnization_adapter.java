package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Immunization_Entry;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class Child_Imunnization_adapter extends BaseAdapter {

	Context context;
	ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
	ArrayList<Child_Imunization_Object> ChildImmun2 = new ArrayList<Child_Imunization_Object>();
	Global global;
	DataProvider dataProvider;

	String fathername = "", mothername = "";

	public Child_Imunnization_adapter(Context context,
			ArrayList<Child_Imunization_Object> ChildImmun) {
		this.ChildImmun = ChildImmun;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ChildImmun.size();
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
			gridview = layoutInflater.inflate(
					R.layout.immunization_grid_adapter, null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);
		TextView tvparentsname = (TextView) gridview
				.findViewById(R.id.tvParentsname);
		TextView tvGender = (TextView) gridview.findViewById(R.id.tvGendername);
		TextView tvChildDob = (TextView) gridview.findViewById(R.id.tvChildDob);

		String Sparentsname = "";
		if (ChildImmun.get(position).getWomenname() != null
				&& ChildImmun.get(position).getWomenname().length() > 0) {

			mothername = ChildImmun.get(position).getWomenname();
			fathername = ChildImmun.get(position).getHusbanname();
			Sparentsname = String.valueOf(String.valueOf(ChildImmun.get(
					position).getChild_name()))
					+ " /" + String.valueOf(mothername);

			tvparentsname.setText(Sparentsname);
		} else {

			ChildImmun2 = dataProvider.gettblCHildImmunization();
			if (ChildImmun2 != null && ChildImmun2.size() > 0) {

				for (int i = 0; i < ChildImmun2.size(); i++) {
					if ((ChildImmun.get(position).getChildGUID())
							.equalsIgnoreCase(ChildImmun2.get(i).getChildGUID())) {
						mothername = ChildImmun2.get(i).getFamilyMemberName();

						if (ChildImmun2.get(i).getSpouse() != null
								&& ChildImmun2.get(i).getSpouse().length() > 0) {

							String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
									+ ChildImmun2.get(i).getSpouse() + "'";
							fathername = dataProvider.getRecord(sql1);
						}
					}
				}
			}

			Sparentsname = String.valueOf(ChildImmun.get(position)
					.getChild_name()) + " / " + String.valueOf(mothername);
			tvparentsname.setText(Sparentsname);
		}

		int iGender = ChildImmun.get(position).getGender();
		if (iGender == 1) {
			tvGender.setText(context.getResources().getString(R.string.girl));
		}

		else if (iGender == 2) {
			tvGender.setText(context.getResources().getString(R.string.boy));
		} else {
			tvGender.setText("");
		}
		tvChildDob.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
				.get(position).getDob())));

		tvparentsname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), Immunization_Entry.class);

				if (ChildImmun.get(position).getWomenname() != null
						&& ChildImmun.get(position).getWomenname().length() > 0) {

					mothername = ChildImmun.get(position).getWomenname();
					fathername = ChildImmun.get(position).getHusbanname();

				} else {
					ChildImmun2 = dataProvider.gettblCHildImmunization();
					if (ChildImmun2 != null && ChildImmun2.size() > 0) {

						for (int j = 0; j < ChildImmun2.size(); j++) {
							if ((ChildImmun.get(position).getChildGUID())
									.equalsIgnoreCase(ChildImmun2.get(j)
											.getChildGUID())) {
								mothername = ChildImmun2.get(j)
										.getFamilyMemberName();

								if (ChildImmun2.get(j).getSpouse() != null
										&& ChildImmun2.get(j).getSpouse()
												.length() > 0) {

									String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
											+ ChildImmun2.get(j).getSpouse()
											+ "'";
									fathername = dataProvider.getRecord(sql1);
								}else{
									fathername="";
								}
							}
						}
					}
				}

				i.putExtra("MotherName", mothername);
				i.putExtra("HusbandName", fathername);
				i.putExtra("ChildGUID",
						String.valueOf(ChildImmun.get(position).getChildGUID()));
				i.putExtra("ChildName", String.valueOf(ChildImmun.get(position)
						.getChild_name()));
				i.putExtra("DOB",
						String.valueOf(ChildImmun.get(position).getDob()));

				context.startActivity(i);

			}
		});

		return gridview;
	}
}