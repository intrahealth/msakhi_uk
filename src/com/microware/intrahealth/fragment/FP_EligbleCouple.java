package com.microware.intrahealth.fragment;

import java.util.ArrayList;

import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.ImmunizationCounselling;
import com.microware.intrahealth.MCH_Dashboard;
import com.microware.intrahealth.R;
import com.microware.intrahealth.adapter.FP_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class FP_EligbleCouple extends Fragment {

	Button btn_search;
	EditText et_Search;
	GridView HHSurveyGrid;
	DataProvider dataProvider;
	Global global;
	ArrayList<Tbl_HHFamilyMember> member = new ArrayList<Tbl_HHFamilyMember>();
	TextView totalcount, pink, orange;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fp_existmember, container,
				false);

		dataProvider = new DataProvider(getActivity());
		global = (Global) getActivity().getApplication();

		btn_search = (Button) rootView.findViewById(R.id.btn_search);
		// btnadd = (ImageView) rootView.findViewById(R.id.btnadd);
		et_Search = (EditText) rootView.findViewById(R.id.et_Search);
		HHSurveyGrid = (GridView) rootView.findViewById(R.id.HHSurveyGrid);
		totalcount = (TextView) rootView.findViewById(R.id.totalcount);
		pink = (TextView) rootView.findViewById(R.id.pink);
		orange = (TextView) rootView.findViewById(R.id.orange);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_Search.getText().toString().length() > 0) {
					fillgrid(1);
				}
			}
		});

		fillgrid(0);

		return rootView;
	}

	public void fillgrid(int flag) {
		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}
		if (flag == 0) {

			member = dataProvider.getAllWomenName("", 6, ashaid);
		} else if (flag == 1) {
			member = dataProvider.getAllWomenName(et_Search.getText()
					.toString(), 5, ashaid);
		}

		if (member != null) {
			totalcount.setText(getResources().getString(
					R.string.totalcountfamily)
					+ " -" + String.valueOf(member.size()));

			// android.view.ViewGroup.LayoutParams params = HHSurveyGrid
			// .getLayoutParams();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 300, r.getDisplayMetrics());
			// int hi = Math.round(px);
			// int gridHeight1 = 0;
			// gridHeight1 = hi * member.size();
			// params.height = gridHeight1;
			// HHSurveyGrid.setLayoutParams(params);
			HHSurveyGrid.setAdapter(new FP_ExistAdapter(getActivity(), member));
		}
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	// menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
	// MenuItem.SHOW_AS_ACTION_IF_ROOM);
	//
	// return true;
	// }
	//
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	//
	// if (global.getiGlobalRoleID() == 3) {
	// Intent i = new Intent(FP_EligbleCouple.this, AshaListForAnm.class);
	// finish();
	//
	// i.putExtra("Flag", 3);
	// startActivity(i);
	// } else {
	// Intent i = new Intent(FP_EligbleCouple.this,
	// Dashboard_Activity.class);
	// finish();
	// startActivity(i);
	// }
	// }

	
	

}