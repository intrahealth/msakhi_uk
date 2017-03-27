package com.microware.intrahealth;

import java.util.ArrayList;
import com.microware.intrahealth.adapter.VHND_Record_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class VHND_Record extends Activity {
	GridView VHND_rcrd_grid;
	DataProvider dataProvider;
	Spinner SpinMonth;
	ArrayAdapter<String> adapter;
	ArrayList<tbl_VHNDPerformance> VHND_perform = new ArrayList<tbl_VHNDPerformance>();
	String Monthhh[];
	int AshaID = 136;
	LinearLayout Addbtn;
	Global global;
	Button btnduelist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_record);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		VHND_rcrd_grid = (GridView) findViewById(R.id.VHND_rcrd_grid);
		btnduelist = (Button) findViewById(R.id.btnduelist);

		Addbtn = (LinearLayout) findViewById(R.id.Addbtn);
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
				//global.setVHND_ID(Validate.random());
				Intent in = new Intent(VHND_Record.this, VHND_DueList.class);
				finish();
				startActivity(in);

			}
		});
		fillVHND();

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
					VHND_perform, 123));
		}
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
