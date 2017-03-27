package com.microware.intrahealth;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.GridView;
import com.microware.intrahealth.adapter.ImmunizationAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

public class ImmunizationCounselling extends Activity {

	GridView ImunizationGrid;
	ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
	DataProvider dataProvider;
	Global global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.immunization_activity);
		global = (Global) getApplication();
		ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
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
			ImunizationGrid
					.setAdapter(new ImmunizationAdapter(this, ChildImmun));
		} else {
			ImunizationGrid
					.setAdapter(new ImmunizationAdapter(this, ChildImmun));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(ImmunizationCounselling.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}
