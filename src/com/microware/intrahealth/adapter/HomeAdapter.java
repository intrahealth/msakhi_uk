package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.Delivery;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tbl_pregnantwomen;

public class HomeAdapter extends BaseAdapter {

	ArrayList<tbl_pregnantwomen> pregnant;
	Context context;
	Global global;
	DataProvider dataProvider;
	ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
	int iflag = 0;
	String sDueVisits = "";
	String LMPDate = "";
	String RegistrationDate = "";
	String CurrentDate = "";

	public HomeAdapter(Context context, ArrayList<tbl_pregnantwomen> pregnant) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pregnant = pregnant;
		// this.iflag = iflag;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pregnant.size();
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
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.homegridadapter, null);
		} else {
			gridview = convertView;
		}

		dataProvider = new DataProvider(context);
		global = (Global) context.getApplicationContext();

		TextView tvwname = (TextView) gridview.findViewById(R.id.tvwname);
		/*
		 * TextView tvlastvisit = (TextView) gridview
		 * .findViewById(R.id.tvlastvisit); ImageView ivaddvisit = (ImageView)
		 * gridview .findViewById(R.id.imageancaddvisit); // ImageView ivdelete
		 * = (ImageView) gridview .findViewById(R.id.imageancdelete);
		 */
		final TextView tvGridhide = (TextView) gridview
				.findViewById(R.id.tvGridhide);
		final GridView gridVisits = (GridView) gridview
				.findViewById(R.id.gridVisits);

		// tvwname.setEnabled(false);

		ImageView ivedit = (ImageView) gridview.findViewById(R.id.imageancedit);
		ImageView ivoutcome = (ImageView) gridview
				.findViewById(R.id.imageancaddvisit);
		Button checkup = (Button) gridview.findViewById(R.id.checkup);
		ivoutcome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
						.getHHFamilyMemberGUID());
				global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
				global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
				Intent i = new Intent(context, Delivery.class);
				context.startActivity(i);
			}
		});
		ivedit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
				Intent i = new Intent(context, PregnantWomen.class);
				context.startActivity(i);
			}
		});
		// if (tblwomenData != null && tblwomenData.size() > 0) {
		// int iDiagnosis = tblwomenData.get(position).getLastDiagnosis();
		//
		// if (iDiagnosis == 1) {
		// tvwomenname.setBackgroundResource(R.drawable.yellowtextbox);
		// } else if (iDiagnosis == 2) {
		// tvwomenname.setBackgroundResource(R.drawable.orangetextbox);
		// } else if (iDiagnosis == 3) {
		// tvwomenname.setBackgroundResource(R.drawable.redtextbox);
		// } else if (iDiagnosis == 4) {
		// tvwomenname.setBackgroundResource(R.drawable.greentextbox);
		// }
		//
		// }
		//
		checkup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String anc_guid = pregnant.get(position).getPWGUID();
				// global.setsGlobalANCGUID(pregnant.get(position).getPW);
				if (Integer.valueOf(tvGridhide.getText().toString()) == 0) {
					gridVisits.setVisibility(View.VISIBLE);
					tvGridhide.setText("1");
					fillvisitdata(gridVisits, anc_guid);
				} else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
					gridVisits.setVisibility(View.GONE);
					tvGridhide.setText("0");
				}
			}
		});
		if (pregnant.get(position).getPWName().length() > 0
				&& pregnant.get(position).getHusbandName().length() > 0) {
			tvwname.setText(String.valueOf(pregnant.get(position).getPWName())
					+ " " + "w/o" + " "
					+ String.valueOf(pregnant.get(position).getHusbandName()));
		} else if (pregnant.get(position).getPWName().length() > 0
				&& pregnant.get(0).getHusbandName().length() == 0) {
			tvwname.setText(String.valueOf(pregnant.get(position).getPWName()));
		}
		tvwname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String anc_guid = pregnant.get(position).getPWGUID();
				// global.setsGlobalANCGUID(pregnant.get(position).getPW);
				if (Integer.valueOf(tvGridhide.getText().toString()) == 0) {
					gridVisits.setVisibility(View.VISIBLE);
					tvGridhide.setText("1");
					fillvisitdata(gridVisits, anc_guid);
				} else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
					gridVisits.setVisibility(View.GONE);
					tvGridhide.setText("0");
				}

			}
		});

		/*
		 * if (iflag == 1) { ivaddvisit.setVisibility(View.GONE); }
		 */
		return gridview;
	}

	/*
	 * public void CustomAlert() {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Do you want to add visit?");
	 * txtTitle.setText(context.getResources().getString(R.string.addvisit)); //
	 * TextView txtMessage = (TextView) //
	 * dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * global.setsGlobalANCVisitGUID(""); global.setiGlobalTTFlag(1);
	 * global.setiGlobalFacilityVisit(0); Intent i = new Intent(context,
	 * AncVisitTabActivity.class); ((Activity) context).finish();
	 * context.startActivity(i); dialog.dismiss();
	 * 
	 * } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 */
	/*
	 * public void CustomAlertFacility() {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Do you want to add visit?");
	 * txtTitle.setText(context.getResources().getString(R.string.Fac_visit));
	 * // TextView txtMessage = (TextView) //
	 * dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * global.setsGlobalANCVisitGUID(""); global.setiGlobalTTFlag(1);
	 * global.setiGlobalFacilityVisit(0); Intent i = new Intent(context,
	 * AncVisitTabActivity.class); ((Activity) context).finish();
	 * context.startActivity(i); dialog.dismiss();
	 * 
	 * } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 */

	/*
	 * public void CustomAlertDelete(final String sANCGUID) {
	 * 
	 * // Create custom dialog object final Dialog dialog = new Dialog(context);
	 * // hide to default title for Dialog
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * 
	 * // inflate the layout dialog_layout.xml and set it as contentView
	 * LayoutInflater inflater = (LayoutInflater) context
	 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.logout_alert, null, false);
	 * dialog.setCanceledOnTouchOutside(true); dialog.setContentView(view);
	 * dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); //
	 * ImageView image=(ImageView)findViewById(R.id.img_dialog_icon); //
	 * image.setVisibility(false);
	 * 
	 * // Retrieve views from the inflated dialog layout and update their //
	 * values TextView txtTitle = (TextView) dialog
	 * .findViewById(R.id.txt_alert_message); //
	 * txtTitle.setText("Are you sure you want to delete this record?");
	 * txtTitle.setText(context.getResources()
	 * .getString(R.string.deleterecord)); // TextView txtMessage = (TextView)
	 * // dialog.findViewById(R.id.txt_dialog_message); //
	 * txtMessage.setText("Do you want to Leave the page ?");
	 * 
	 * Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
	 * btnyes.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * 
	 * DeleteData(sANCGUID); dialog.dismiss(); } });
	 * 
	 * Button btnno = (Button) dialog.findViewById(R.id.btn_no);
	 * btnno.setOnClickListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // Dismiss the dialog
	 * dialog.dismiss(); } });
	 * 
	 * // Display the dialog dialog.show();
	 * 
	 * }
	 * 
	 * public void DeleteData(String sANCGUID) { String sqldelete =
	 * "Delete from Tbl_ANC where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqldelete); String sqloutcomedelete =
	 * "Delete from Tbl_ANCOutcome where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqloutcomedelete); String sqlvisitdelete =
	 * "Delete from Tbl_ANCVisit where ANC_GUID = '" + sANCGUID + "'";
	 * dataProvider.executeSql(sqlvisitdelete);
	 * 
	 * ((AncActivity) context).fillgrid(iflag, global.getsGlobalVillageCode());
	 * }
	 */
	public void fillvisitdata(GridView gridVisits, String sPWGUID) {
		VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID, "", 3);
		if (VisitANC != null && VisitANC.size() > 0) {

			android.view.ViewGroup.LayoutParams params = gridVisits
					.getLayoutParams();
			Resources r = context.getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					50, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * VisitANC.size();
			params.height = gridHeight1;
			gridVisits.setLayoutParams(params);
			gridVisits.setAdapter(new HomeVisitAdapter(context, VisitANC));

		}
	}
}
