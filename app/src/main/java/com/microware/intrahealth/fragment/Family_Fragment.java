package com.microware.intrahealth.fragment;

import java.util.ArrayList;

import com.google.android.gms.internal.fa;
import com.microware.intrahealth.FamilyDetails;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.adapter.FamilyDetails_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class Family_Fragment extends Fragment {

	ImageView btnadd;
	TextView totalcount;
	GridView gridfamilydetails;
	DataProvider dataProvider;
	Global global;
	ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.family_activity, container,
				false);

		dataProvider = new DataProvider(getActivity());
		global = (Global) getActivity().getApplication();

		btnadd = (ImageView) rootView.findViewById(R.id.btnadd);
		totalcount = (TextView) rootView.findViewById(R.id.totalcount);
		gridfamilydetails = (GridView) rootView
				.findViewById(R.id.gridfamilydetails);
		if (global.getiGlobalRoleID() == 3) {
			btnadd.setVisibility(View.GONE);
		}

		btnadd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				CustomAlertAdd(getResources().getString(R.string.wantaddmember));
			}
		});

		return rootView;

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		fillGrid();
	}



	public void fillGrid() {
		HHFamilyMember = dataProvider.getHHFamilymemberData(
				global.getGlobalHHSurveyGUID(),
				global.getGlobalHHFamilyMemberGUID(), 1);

		if (HHFamilyMember != null) {
//			android.view.ViewGroup.LayoutParams params = gridfamilydetails
//					.getLayoutParams();
//			Resources r = getResources();
//			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//					60, r.getDisplayMetrics());
//			int hi = Math.round(px);
//			int gridHeight1 = 0;
//			gridHeight1 = hi * HHFamilyMember.size();
//			params.height = gridHeight1;
//			gridfamilydetails.setLayoutParams(params);
			totalcount.setText(getResources().getString(R.string.totalcountfm)
					+ "-" + String.valueOf(HHFamilyMember.size()));
			gridfamilydetails.setAdapter(new FamilyDetails_Adapter(
					getActivity(), HHFamilyMember, Family_Fragment.this));

		} else {
			totalcount.setText(getResources().getString(R.string.totalcountfm)
					+ " -" + String.valueOf(HHFamilyMember.size()));
			gridfamilydetails.setAdapter(new FamilyDetails_Adapter(
					getActivity(), HHFamilyMember, Family_Fragment.this));
		}

	}

	public void CustomAlertAdd(String msg) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(getActivity());
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialog_layout, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		TextView txtTitle = (TextView) dialog
				.findViewById(R.id.txt_alert_message);
		txtTitle.setText(msg);

		Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
		btnok.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), FamilyDetails.class);
				global.setGlobalHHFamilyMemberGUID("");
				startActivity(i);

				dialog.dismiss();
			}
		});

		// Display the dialog
		dialog.show();

	}

}
