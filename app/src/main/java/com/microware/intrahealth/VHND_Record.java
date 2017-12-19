package com.microware.intrahealth;

import java.util.ArrayList;
import com.microware.intrahealth.adapter.VHND_Record_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_VHND_DueList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class VHND_Record extends Activity {
	GridView VHND_rcrd_grid;
	DataProvider dataProvider;
	Spinner SpinMonth;
	ArrayAdapter<String> adapter;
	ArrayList<tbl_VHNDPerformance> VHND_perform = new ArrayList<tbl_VHNDPerformance>();
	ArrayList<tbl_VHND_DueList> VNHDDuelistss = new ArrayList<tbl_VHND_DueList>();
	String Monthhh[];
	int AshaID = 136;
	LinearLayout Addbtn;
	Global global;
	Button btnduelist;
	TextView AddPerformm;
	String ActivityName = "";
	TextView VHNDPerformance_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_record);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		Intent intent = getIntent();
		ActivityName = intent.getExtras().getString("ActivityName");

		VHNDPerformance_txt = (TextView) findViewById(R.id.VHNDPerformance_txt);
		VHND_rcrd_grid = (GridView) findViewById(R.id.VHND_rcrd_grid);
		btnduelist = (Button) findViewById(R.id.btnduelist);
		btnduelist.setVisibility(View.GONE);
		AddPerformm = (TextView) findViewById(R.id.AddPerformm);
		AddPerformm.setVisibility(View.GONE);

		Addbtn = (LinearLayout) findViewById(R.id.Addbtn);
		Addbtn.setVisibility(View.GONE);
		Addbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setVHND_ID(Validate.random());
				global.setVHND_Composite_Flag("I");
				Intent in = new Intent(VHND_Record.this, VHND.class);
				finish();
				startActivity(in);

			}
		});
		btnduelist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// global.setVHND_ID(Validate.random());
				Intent in = new Intent(VHND_Record.this, VHND_DueList.class);
				finish();
				startActivity(in);

			}
		});

		if (ActivityName.equalsIgnoreCase("Dashboard_Activity")) {
			fillVHND();
		} else if (ActivityName.equalsIgnoreCase("Report_Module")) {
			fillVHNDDuelist();
			VHNDPerformance_txt.setText(String.valueOf(getResources()
					.getString(R.string.VHNDDueList)));
		}

	}

	public void fillVHND() {
		VHND_perform = dataProvider.getVHND_Performance(
				String.valueOf(global.getsGlobalAshaCode()), "", 0);
		int Count = 0;
		if (VHND_perform != null) {
			Count = VHND_perform.size();

			android.view.ViewGroup.LayoutParams params = VHND_rcrd_grid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			VHND_rcrd_grid.setLayoutParams(params);
			VHND_rcrd_grid.setAdapter(new VHND_Record_adapter(this,
					VHND_perform, 1));
		}
	}

	public void fillVHNDDuelist() {
		VNHDDuelistss = dataProvider.getVHND_Duelist(
				String.valueOf(global.getsGlobalAshaCode()), "", 0);
		int Count = 0;
		if (VNHDDuelistss != null) {
			Count = VNHDDuelistss.size();

			android.view.ViewGroup.LayoutParams params = VHND_rcrd_grid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			VHND_rcrd_grid.setLayoutParams(params);
			VHND_rcrd_grid.setAdapter(new VHND_Record_adapter(this,
					VNHDDuelistss, 2, ""));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(VHND_Record.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}
}
