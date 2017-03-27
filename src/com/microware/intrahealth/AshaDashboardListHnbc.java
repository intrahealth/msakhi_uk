package com.microware.intrahealth;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;

import com.microware.intrahealth.adapter.AshaDashboardListFormHnbc_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

public class AshaDashboardListHnbc extends Activity {

	GridView ImunizationGrid;
	ArrayList<MstASHA> ashaname = new ArrayList<MstASHA>();
	DataProvider dataProvider;
	Global global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ashalistforanm);
		global = (Global) getApplication();
		dataProvider = new DataProvider(this);
		ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
		fillgrid();

	}

	public void fillgrid() {
		// global.setsGlobalANMCODE(tblAnmcode.get(0)
		// .getANMCode());
		ashaname = dataProvider.getashaListForAnm(global.getAnmidasAnmCode(),
				global.getLanguage());
		if (ashaname != null) {
			android.view.ViewGroup.LayoutParams params = ImunizationGrid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					100, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * ashaname.size();
			params.height = gridHeight1;
			ImunizationGrid.setLayoutParams(params);
			ImunizationGrid.setAdapter(new AshaDashboardListFormHnbc_Adapter(this,
					ashaname));
		} else {
			ImunizationGrid.setAdapter(new AshaDashboardListFormHnbc_Adapter(this,
					ashaname));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(AshaDashboardListHnbc.this, Reportindicator_anmHnbclist.class);
		finish();
		startActivity(i);
	}

}
