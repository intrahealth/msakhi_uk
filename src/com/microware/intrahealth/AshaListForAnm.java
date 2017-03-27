package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.AshaListFormAnm_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

public class AshaListForAnm extends Activity {

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
		
		Intent intent = getIntent();
		int iFlag = 0;
		iFlag = intent.getExtras().getInt("Flag");
		
		fillgrid(iFlag);

	}

	public void fillgrid(int flag) {
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
			ImunizationGrid.setAdapter(new AshaListFormAnm_Adapter(this,
					ashaname, flag));
		} else {
			ImunizationGrid.setAdapter(new AshaListFormAnm_Adapter(this,
					ashaname, flag));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		if(global.getiGlobalRoleID() == 3) {
			menu.add(0, 0, 0, global.getsGlobalANMName()).setShowAsAction(
					MenuItem.SHOW_AS_ACTION_IF_ROOM);	
		} else {
		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}

//		menu.add(0, 1, 1, "History").setIcon(R.drawable.logout1)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		//

		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(AshaListForAnm.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}

}
