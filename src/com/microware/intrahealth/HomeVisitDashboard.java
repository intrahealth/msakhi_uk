package com.microware.intrahealth;

import java.util.ArrayList;
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
//		btnaddanc = (Button) findViewById(R.id.btnaddanc);
//		btncloseanc = (Button) findViewById(R.id.btncloseanc);
		btnpregnantwomen = (Button) findViewById(R.id.btnpregnantwomen);
		btnpnc = (Button) findViewById(R.id.btnpnc);
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
}
