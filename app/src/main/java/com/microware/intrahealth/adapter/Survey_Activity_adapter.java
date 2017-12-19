package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Survey_Activity;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHSurvey;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class Survey_Activity_adapter extends BaseAdapter {
	Context context;
	ArrayList<Tbl_HHSurvey> hhsurvey;
	Global global;
	DataProvider dataProvider;
	int StatusId = 0;

	public Survey_Activity_adapter(Context context,
			ArrayList<Tbl_HHSurvey> hhsurvey) {
		this.hhsurvey = hhsurvey;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hhsurvey.size();
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

	@SuppressWarnings({ "static-access" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View gridview = null;
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(context.LAYOUT_INFLATER_SERVICE);
			gridview = new View(context);
			gridview = layoutInflater.inflate(R.layout.survey_activity_adapter,
					null);
		} else {
			gridview = convertView;
		}

		global = (Global) context.getApplicationContext();
		dataProvider = new DataProvider(context);
		TextView tvHHCode = (TextView) gridview.findViewById(R.id.tvHHCode);
		TextView tvHHHeadName = (TextView) gridview
				.findViewById(R.id.tvHHHeadName);
		TableRow GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
		ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
		ImageView btnDelete = (ImageView) gridview.findViewById(R.id.btnDelete);
		tvHHCode.setText(String.valueOf(hhsurvey.get(position).getHHCode()));

		String status = "Select HHStatusID from Tbl_HHSurvey where HHSurveyGUID='"
				+ hhsurvey.get(position).getHHSurveyGUID() + "'";
		String stat = "";
		stat = dataProvider.getRecord(status);
		if (stat != null && !stat.equalsIgnoreCase("null") && stat.length() > 0) {
			StatusId = Integer.valueOf(stat);
		}

		if (StatusId == 3) {
			tvHHCode.setBackgroundResource(R.drawable.orangesheet);
			tvHHHeadName.setBackgroundResource(R.drawable.orangesheet);
			GridRow.setBackgroundResource(R.drawable.orangesheet);
		} else if (StatusId == 2) {
			tvHHCode.setBackgroundResource(R.drawable.yellowsheet);
			tvHHHeadName.setBackgroundResource(R.drawable.yellowsheet);

			// GridRow.setBackgroundResource(R.drawable.yellowsheet);
			GridRow.setBackgroundColor(context.getResources().getColor(
					R.color.Yellow));
		} else {
			tvHHCode.setBackgroundResource(R.drawable.whitesheet);
			tvHHHeadName.setBackgroundResource(R.drawable.whitesheet);

			GridRow.setBackgroundColor(context.getResources().getColor(
					R.color.White));
		}
		String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
				+ hhsurvey.get(position).getHHSurveyGUID()
				+ "'  and RelationID=1 and StatusID!=2";
		String newdata = dataProvider.getRecord(sql);
		if (newdata == null) {
			newdata = "";
		}
		tvHHHeadName.setText(newdata);

		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setGlobalHHSurveyGUID(hhsurvey.get(position)
						.getHHSurveyGUID());
				String GUID = hhsurvey.get(position).getHHSurveyGUID();
				String sql = "Delete from Tbl_HHSurvey where HHSurveyGUID='"
						+ GUID + "'";
				dataProvider.deleteRecord(sql);
				Intent in = new Intent(context, Survey_Activity.class);
				context.startActivity(in);

			}
		});
		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(),
						Intrahealth_Tab_Activity.class);
				global.setGlobalHHSurveyGUID(hhsurvey.get(position)
						.getHHSurveyGUID());

				context.startActivity(i);
			}
		});
		return gridview;
	}

}
