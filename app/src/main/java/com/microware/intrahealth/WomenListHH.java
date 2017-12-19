package com.microware.intrahealth;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.adapter.AllhhWomen_Adapter;
import com.microware.intrahealth.adapter.PregnantWomen_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

public class WomenListHH extends Activity {

	GridView gridMasterRecord;
	DataProvider dataprovider;
	Global global;
	TextView totalcount;
	ImageView btnsearchFilter;
	EditText etFamilyIDSearch;
	ArrayList<Tbl_HHFamilyMember> record = new ArrayList<Tbl_HHFamilyMember>();

	ImageButton btnAddNew;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.womenlistchild);
		gridMasterRecord = (GridView) findViewById(R.id.HHSurveyGrid);

		dataprovider = new DataProvider(this);
		global = (Global) getApplication();
		btnsearchFilter = (ImageView) findViewById(R.id.btnsearchFilter);
		totalcount = (TextView) findViewById(R.id.totalcount);
		etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
		// btnAddNew=(ImageButton)findViewById(R.id.btnAddNew);
		// final Animation animAlpha = AnimationUtils.loadAnimation(this,
		// R.anim.anim_alpha);
		//
		// btnAddNew.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// global.setsGlobalBaselinesurveyGUID("");
		// Intent in =new Intent(GetDataGrid1.this,VST.class);
		// finish();
		// startActivity(in);
		//
		// }
		// });
		// fillGridView();
		btnsearchFilter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (etFamilyIDSearch.getText().toString().length() > 0) {
					fillgrid(etFamilyIDSearch.getText().toString());
				}
			}
		});
		etFamilyIDSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method
				if(s.length()>0){
					fillgrid(etFamilyIDSearch.getText().toString());
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (etFamilyIDSearch.getText().toString().length() == 0) {
					fillGridView();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	public void fillgrid(String Woman_name) {
		// TODO Auto-generated method stub
		String name = "%" + Woman_name + "%";
		int ashacode = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashacode = Integer.valueOf(global.getsGlobalAshaCode());
		}
		record = dataprovider.getAllWomenName(name, 4, ashacode);

		if (record != null && record.size() > 0) {
			// android.view.ViewGroup.LayoutParams params =
			// gridMasterRecord.getLayoutParams();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 300,
			// r.getDisplayMetrics());
			// int hi = Math.round(px);
			// int gridHeight1 = 0;
			// gridHeight1 = hi * record.size();
			// params.height = gridHeight1;
			// gridMasterRecord.setLayoutParams(params);
			gridMasterRecord
					.setAdapter(new PregnantWomen_adapter(this, record));

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fillGridView();
	}

	public void fillGridView() {
		int ashacode = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashacode = Integer.valueOf(global.getsGlobalAshaCode());
		}
		record = dataprovider.getAllWomenName("", 0, ashacode);
		if (record != null) {
			global.setsGlobalWomenName("");
			// android.view.ViewGroup.LayoutParams params =
			// gridMasterRecord.getLayoutParams();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 300,
			// r.getDisplayMetrics());
			// int hi = Math.round(px);
			// int gridHeight1 = 0;
			// gridHeight1 = hi * record.size();
			// params.height = gridHeight1;
			// gridMasterRecord.setLayoutParams(params);
			totalcount.setText(getResources().getString(R.string.totalcount)
					+ "-" + record.size());
			gridMasterRecord.setAdapter(new AllhhWomen_Adapter(this, record));

		}

	}

	public int SumOFlag(String GUID) {
		return 0;
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(WomenListHH.this, newbornGrid.class);
		finish();
		startActivity(in);
	}

}
