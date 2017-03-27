package com.microware.intrahealth.fragment;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.ImmunizationCounselling;
import com.microware.intrahealth.R;
import com.microware.intrahealth.adapter.FP_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.R.color;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class FP_User extends Fragment {

	Button btn_search, btn_page;
	EditText et_Search;
	GridView HHSurveyGrid;
	DataProvider dataProvider;
	Dialog PicVideo_PreviewPopUp;

	Global global;
	ArrayList<Tbl_HHFamilyMember> member = new ArrayList<Tbl_HHFamilyMember>();
	TextView totalcount, pink, orange;
	int cocount = 0, occount = 0, eccount = 0, IU380count = 0, IU375count = 0,
			femalecount = 0, malecount = 0;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fp_existmember, container,
				false);

		dataProvider = new DataProvider(getActivity());
		global = (Global) getActivity().getApplication();

		btn_search = (Button) rootView.findViewById(R.id.btn_search);
		btn_page = (Button) rootView.findViewById(R.id.btn_page);
		et_Search = (EditText) rootView.findViewById(R.id.et_Search);
		HHSurveyGrid = (GridView) rootView.findViewById(R.id.HHSurveyGrid);
		totalcount = (TextView) rootView.findViewById(R.id.totalcount);
		pink = (TextView) rootView.findViewById(R.id.pink);
		orange = (TextView) rootView.findViewById(R.id.orange);
		if (global.getiGlobalRoleID() == 3) {
			btn_page.setVisibility(View.GONE);
		} else {
			btn_page.setVisibility(View.VISIBLE);
		}
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (et_Search.getText().toString().length() > 0) {
					fillgrid(1);
				}
			}
		});
		btn_page.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				count();
				report();
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

			member = dataProvider.getAllWomenName("", 7, ashaid);
		} else if (flag == 1) {
			member = dataProvider.getAllWomenName(et_Search.getText()
					.toString(), 8, ashaid);
		}

		if (member != null) {
			totalcount.setText(getResources().getString(
					R.string.totalcountfamily)
					+ " -" + String.valueOf(member.size()));

			android.view.ViewGroup.LayoutParams params = HHSurveyGrid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					300, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * member.size();
			params.height = gridHeight1;
			HHSurveyGrid.setLayoutParams(params);
			HHSurveyGrid.setAdapter(new FP_ExistAdapter(getActivity(), member));
		}
	}

	public void count() {
		String sql = "select count(*) from tblFP_followup where methodadopted=1  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql1 = "select count(*) from tblFP_followup where methodadopted=2  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql2 = "select count(*) from tblFP_followup where methodadopted=3  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql3 = "select count(*) from tblFP_followup where methodadopted=4  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql4 = "select count(*) from tblFP_followup where methodadopted=5  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql5 = "select count(*) from tblFP_followup where methodadopted=6  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql6 = "select count(*) from tblFP_followup where methodadopted=7  and uid in(select max(uid) from tblFP_followup group by womenname_guid)";
		String sql41 = "select count(distinct(womenname_guid)) from tblFP_visit where q61=1 and womenname_guid not in(select distinct(womenname_guid) from tblFP_followup)";

		String sql51 = "select count(distinct(womenname_guid)) from tblFP_visit where q61=2 and womenname_guid not in(select distinct(womenname_guid) from tblFP_followup)";
		String sql61 = "select count(distinct(womenname_guid)) from tblFP_visit where q61=3 and womenname_guid not in(select distinct(womenname_guid) from tblFP_followup )";
		cocount = dataProvider.getMaxRecord(sql);
		occount = dataProvider.getMaxRecord(sql1);
		eccount = dataProvider.getMaxRecord(sql2);
		IU380count = dataProvider.getMaxRecord(sql3)
				+ dataProvider.getMaxRecord(sql41);
		IU375count = dataProvider.getMaxRecord(sql4);
		femalecount = dataProvider.getMaxRecord(sql5)
				+ dataProvider.getMaxRecord(sql51);
		malecount = dataProvider.getMaxRecord(sql6)
				+ dataProvider.getMaxRecord(sql61);

	}

	public void report() {

		PicVideo_PreviewPopUp = new Dialog(getActivity());
		Window window = PicVideo_PreviewPopUp.getWindow();
		// PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
		// PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
		// new ColorDrawable(Color.TRANSPARENT));
		PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
				color.white);
		WindowManager wm = (WindowManager) getActivity().getSystemService(
				Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		Double width = metrics.widthPixels * 20.2;
		Double height = metrics.heightPixels * 5.3;
		Window win = PicVideo_PreviewPopUp.getWindow();
		win.setLayout(width.intValue(), height.intValue());
		// window.requestFeature(Window.FEATURE_NO_TITLE);

		TextView ECPills, Condom, OCPills, IUCDCU380A, IUCDCU375, FemaleSterilization, MaleSterilization;
		PicVideo_PreviewPopUp.setContentView(R.layout.fp_report);
		PicVideo_PreviewPopUp.setCancelable(true);
		PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
		Condom = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Condom);
		ECPills = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ECPills);
		OCPills = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.OCPills);
		IUCDCU380A = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.IUCDCU380A);
		IUCDCU375 = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.IUCDCU375);
		FemaleSterilization = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.FemaleSterilization);
		MaleSterilization = (TextView) PicVideo_PreviewPopUp
				.findViewById(R.id.MaleSterilization);
		ECPills.setText(String.valueOf(eccount));
		Condom.setText(String.valueOf(cocount));
		OCPills.setText(String.valueOf(occount));
		IUCDCU380A.setText(String.valueOf(IU380count));
		IUCDCU375.setText(String.valueOf(IU375count));
		FemaleSterilization.setText(String.valueOf(femalecount));
		MaleSterilization.setText(String.valueOf(malecount));

		PicVideo_PreviewPopUp.show();

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
