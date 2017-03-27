package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.Child_Imunnization_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;
import android.widget.TextView;

public class ImunizationChildList extends Activity {
	TextView totalcount;
	GridView ImunizationGrid;
	ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
	DataProvider dataProvider;
	Global global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imunazation);
		global = (Global) getApplication();
		ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
		totalcount = (TextView) findViewById(R.id.totalcount);
		dataProvider = new DataProvider(this);
		fillgrid();

	}

	public void fillgrid() {
		int ashaid = 0;
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			ashaid = Integer.valueOf(global.getsGlobalAshaCode());
		}
		ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid);
		if (ChildImmun != null) {
			android.view.ViewGroup.LayoutParams params = ImunizationGrid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					100, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * ChildImmun.size();
			params.height = gridHeight1;
			ImunizationGrid.setLayoutParams(params);
			totalcount.setText(getResources().getString(R.string.totalcount)
					+ "-" + ChildImmun.size());
			ImunizationGrid.setAdapter(new Child_Imunnization_adapter(this,
					ChildImmun));
		} else {
			ImunizationGrid.setAdapter(new Child_Imunnization_adapter(this,
					ChildImmun));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(ImunizationChildList.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}
