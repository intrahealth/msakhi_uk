package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.Tbl_HHSurvey;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class PregnantWomen_adapter extends BaseAdapter {
	Context context;
	Global global;
	DataProvider dataprovider;

	ArrayList<Tbl_HHFamilyMember> masterEntery = new ArrayList<Tbl_HHFamilyMember>();
	ArrayList<Tbl_HHFamilyMember> masterEntery1 = new ArrayList<Tbl_HHFamilyMember>();
	ArrayList<Tbl_HHSurvey> masterEntery2 = new ArrayList<Tbl_HHSurvey>();

	public PregnantWomen_adapter(Context context,
			ArrayList<Tbl_HHFamilyMember> masterEntery) {
		this.context = context;
		this.masterEntery = masterEntery;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return masterEntery.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings({ "static-access", "unused" })
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.gridview, null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataprovider = new DataProvider(context);
		// String hhguid=masterEntery.get(position).getHHSurveyGUID();
		String spouse = masterEntery.get(position).getSpouse();

		masterEntery1 = dataprovider.getAllmalename(spouse);
		// masterEntery2=dataprovider.gethhcode(hhguid);

		TextView tvText = (TextView) gridview.findViewById(R.id.womenname);
		TextView tvText1 = (TextView) gridview.findViewById(R.id.hhcode);
		TextView tvText3 = (TextView) gridview.findViewById(R.id.husbandname);
		// tvText1.setText(masterEntery.get(position).getHHCode());
		// if(masterEntery2!=null && masterEntery2.size()>0){
		tvText1.setText(masterEntery.get(position).getHHCode());
		// }
		if (masterEntery1 != null && masterEntery1.size() > 0) {
			tvText3.setText(masterEntery1.get(0).getFamilyMemberName());
			tvText.setText(masterEntery.get(position).getFamilyMemberName());
			global.setsGlobalHusbandName(masterEntery1.get(0)
					.getFamilyMemberName());
		} else {
			tvText3.setText("");
			global.setsGlobalHusbandName("");
			tvText.setText(masterEntery.get(position).getFamilyMemberName());

		}
		String txt = tvText.getText().toString();
		// ImageView idbtnedit = (ImageView)
		// gridview.findViewById(R.id.idbtnedit);
		ImageView idbtnadd = (ImageView) gridview
				.findViewById(R.id.idbtnadd);
		// idbtnedit.setOnClickListener(new View.OnClickListener() {
		//
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// global.setsGlobalBaselinesurveyGUID(masterEntery.get(position).getBaseLineGUID());
		// Intent in = new Intent(context, VST.class);
		// context.startActivity(in);
		// }
		//
		// });
		//
		idbtnadd.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String sql1 = "";
				global.setGlobalHHSurveyGUID(masterEntery.get(position)
						.getHHSurveyGUID());
				sql1 = "Select count(*) from tblPregnant_woman where HHFamilyMemberGUID='"
						+ masterEntery.get(position).getHHFamilyMemberGUID()
						+ "' and IsPregnant=1";
				int count = dataprovider.getMaxRecord(sql1);

				
				if (count == 0) {

					global.setsGlobalPWGUID("");
					global.setsGlobalWomenName(masterEntery.get(position)
							.getFamilyMemberName());
					global.setsGlobalSpouseGUID(masterEntery.get(position)
							.getSpouse());
					global.setGlobalHHFamilyMemberGUID(masterEntery.get(
							position).getHHFamilyMemberGUID());
					Intent in = new Intent(context, PregnantWomen.class);
					context.startActivity(in);
				} else {

					AlertDialog alertDialog = new AlertDialog.Builder(context)
							.create();
					alertDialog.setTitle("mSakhi");
					alertDialog.setMessage("Already registered");
					alertDialog.setButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});

					alertDialog.show();
				}
				// }else {
				// String sql2 = "";
				// sql2 =
				// "Select  tblChild.Child_dob from tblChild inner join  tblPregnant_woman where tblChild.HHGUID='"+global.getGlobalHHSurveyGUID()+"' and tblPregnant_woman.HHGUID='"+global.getGlobalHHSurveyGUID()+"' and  tblPregnant_woman.IsPregnant=0";
				// String dob=dataprovider.getRecord(sql2);
				// long diff=Validate.diffdays(dob);
				// if(diff<42){
				// AlertDialog alertDialog = new
				// AlertDialog.Builder(context).create();
				// alertDialog.setTitle("mSakhi");
				// alertDialog.setMessage("Cannot register before 42 days");
				// alertDialog.setButton("OK", new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int which) {
				//
				// }
				// });
				//
				// alertDialog.show();
				// }else{
				// Intent in = new Intent(context, PregnantWomen.class);
				// context.startActivity(in);
				// }
				// }
			}

		});

		// dataprovider = new DataProvider(context);

		//
		// final TextView textvisibilty = (TextView) gridview
		// .findViewById(R.id.textvisibilty);

		// final LinearLayout Linear = (LinearLayout) gridview
		// .findViewById(R.id.Linear);

		return gridview;
	}

}
