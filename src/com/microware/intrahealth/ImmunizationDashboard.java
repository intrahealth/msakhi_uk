package com.microware.intrahealth;

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

public class ImmunizationDashboard extends Activity{
	

		Button btnimmunized,btncounselling;

		DataProvider dataProvider;
		Global global;


	//private EasyTracker easyTracker1 = null;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);

			// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.immunizationdashboard);
			
		        //send our data!
		      
			btnimmunized = (Button) findViewById(R.id.btnimmunized);
			btncounselling = (Button) findViewById(R.id.btncounselling);
		btnimmunized.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ImmunizationDashboard.this,ImunizationChildList.class);
				startActivity(i);
			}
		});
		Tracker t = ((Global) getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Immunization Dashboard");
		t.send(new HitBuilders.AppViewBuilder().build());
		btncounselling.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(ImmunizationDashboard.this,ImmunizationCounselling.class);
				startActivity(i);
			}
		});
		}
		protected void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			GoogleAnalytics.getInstance(ImmunizationDashboard.this).reportActivityStart(this);
		}

		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			GoogleAnalytics.getInstance(ImmunizationDashboard.this).reportActivityStop(this);
		}
}
