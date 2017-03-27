package com.microware.intrahealth;

import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MCH_Dashboard extends Activity {
	RelativeLayout anc;
	Button anclist,newborn,btnhv,immunized;
	DataProvider dataProvider;
	Global global;
	String sCurrentLanguage = "";
	@SuppressWarnings("unused")
	private Locale myLocale;
	int iRoleID=0;
	@SuppressWarnings("unused")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

//		 requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.mch_dashboard);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		anc=(RelativeLayout) findViewById(R.id.anc);
	
		anclist=(Button) findViewById(R.id.anclist);
		newborn=(Button) findViewById(R.id.newborn);
		btnhv=(Button) findViewById(R.id.btnhv);
		immunized=(Button) findViewById(R.id.immunized);
		iRoleID = global.getiGlobalRoleID();
		anclist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
					Intent i = new Intent(MCH_Dashboard.this, AncActivity.class);
					startActivity(i);
					
			}
		});
		if (global.getLanguage() == 1) {
			
			global.setLanguage(1);
			

		} else if (global.getLanguage() == 2) {
			
			global.setLanguage(2);
			

		} else {
			String sLanguage = "";
			sLanguage = "hi";
			sCurrentLanguage = "en";
			
			global.setLanguage(2);
			
		}
		newborn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MCH_Dashboard.this,
						newbornGrid.class);
				
				startActivity(i);
			}
		});
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Mch Dashboard");
		t.send(new HitBuilders.AppViewBuilder().build());
		immunized.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MCH_Dashboard.this,
						ImmunizationDashboard.class);
				
				startActivity(i);
			}
		});
		btnhv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MCH_Dashboard.this,
						HomeVisitDashboard.class);
			
				startActivity(i);
			}
		});
	}
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		if(global.getiGlobalRoleID() == 3) {
			Intent i = new Intent(MCH_Dashboard.this, AshaListForAnm.class);
			finish();

			i.putExtra("Flag", 2);
			startActivity(i);
		} else {
			Intent i = new Intent(MCH_Dashboard.this, Dashboard_Activity.class);
			finish();
			startActivity(i);
		}
	}
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		GoogleAnalytics.getInstance(MCH_Dashboard.this).reportActivityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		GoogleAnalytics.getInstance(MCH_Dashboard.this).reportActivityStop(this);
	}
}
