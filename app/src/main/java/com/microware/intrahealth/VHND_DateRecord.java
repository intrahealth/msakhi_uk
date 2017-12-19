package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.adapter.VHND_Record_adapter;
import com.microware.intrahealth.adapter.VHND_Record_adapter_new;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.VHND_Schedule;
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

public class VHND_DateRecord extends Activity {
	GridView VHND_rcrd_grid;
	DataProvider dataProvider;
	Spinner SpinMonth;
	ArrayAdapter<String> adapter;
	ArrayList<VHND_Schedule> VHND_Date = new ArrayList<VHND_Schedule>();
	String Monthhh[];
	// int AshaID = 136;
	// LinearLayout Addbtn;
	Global global;
	// Button btnduelist;
	// TextView AddPerformm;
	// String ActivityName = "";
	TextView VHNDPerformance_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_daterecord);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();
		setTitle(global.getVersionName());

		// Intent intent = getIntent();
		// ActivityName = intent.getExtras().getString("ActivityName");

		VHNDPerformance_txt = (TextView) findViewById(R.id.VHNDPerformance_txt);
		VHND_rcrd_grid = (GridView) findViewById(R.id.VHND_rcrd_grid);

		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String localTime = date1.format(currentLocalTime);
		String VHND_GUID = Validate.random();
		global.setVHND_GUID(String.valueOf(VHND_GUID));
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateStrings = sdf.format(date);
		dataProvider.getUserLogin(VHND_GUID, global.getUserID(), "VHND",
				"VHND", localTime, dateStrings);
		fillVHND();
	}

	public void fillVHND() {
		VHND_Date = dataProvider.getVHND_Schedule(
				String.valueOf(global.getsGlobalAshaCode()), 0);
		int Count = 0;
		if (VHND_Date != null) {
			// Count = VHND_Date.size();
			//
			// android.view.ViewGroup.LayoutParams params = VHND_rcrd_grid
			// .getLayoutParams();
			// Resources r = getResources();
			// float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
			// 90, r.getDisplayMetrics());
			// int hi = Math.round(px);
			// int gridHeight = 0;
			// gridHeight = hi * Count;
			// params.height = gridHeight;
			// VHND_rcrd_grid.setLayoutParams(params);
			VHND_rcrd_grid.setAdapter(new VHND_Record_adapter_new(this,
					VHND_Date, 1));
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
		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String endTime = date1.format(currentLocalTime);
		dataProvider.getUserLoginUpdate(global.getVHND_GUID(), endTime);
		if (global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(VHND_DateRecord.this, AshaListForAnm.class);
			finish();

			i.putExtra("Flag", 4);
			startActivity(i);
		} else {
			Intent i = new Intent(VHND_DateRecord.this,
					Dashboard_Activity.class);
			finish();
			startActivity(i);
		}
	}
}
