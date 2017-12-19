	package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

public class HomeVisitDashboard extends Activity {

	
	Button  btnpnc, btnpregnantwomen;
	
	Global global;
	
	DataProvider dataProvider;
	ArrayAdapter<String> adapter;
	public ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.homevisit);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		setTitle(global.getVersionName());
//		btnaddanc = (Button) findViewById(R.id.btnaddanc);
//		btncloseanc = (Button) findViewById(R.id.btncloseanc);
		btnpregnantwomen = (Button) findViewById(R.id.btnpregnantwomen);
		btnpnc = (Button) findViewById(R.id.btnpnc);
		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String localTime = date1.format(currentLocalTime);
		String homevisit_GUID = Validate.random();
		global.setHomevisit_GUID(String.valueOf(homevisit_GUID));
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateStrings = sdf.format(date);
		dataProvider.getUserLogin(homevisit_GUID, global.getUserID(), "Homevisit", "Homevisit",
				localTime, dateStrings);
		btnpregnantwomen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(HomeVisitDashboard.this,HomeVisit.class);
				startActivity(i);
			}
		});
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Home Visit Dashboard");
		t.send(new HitBuilders.AppViewBuilder().build());
		btnpnc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(HomeVisitDashboard.this,PncWomenList.class);
				startActivity(i);
			}
		});
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(HomeVisitDashboard.this).reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(HomeVisitDashboard.this).reportActivityStop(this);
	}
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Calendar cal = Calendar.getInstance();
		Date currentLocalTime = cal.getTime();
		SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

		String endTime = date1.format(currentLocalTime);
		dataProvider.getUserLoginUpdate(global.getHomevisit_GUID(), endTime);
		Intent i = new Intent(HomeVisitDashboard.this, MCH_Dashboard.class);
		finish();
		startActivity(i);
	}
}
