package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Report_Module extends Activity {

	Button btnHousehold, btnAnc, btnHNBC;
	Global global;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		global = (Global) getApplication();
		setContentView(R.layout.reportmodulescreen);
		btnHousehold = (Button) findViewById(R.id.btnHousehold);
		btnAnc = (Button) findViewById(R.id.btnANC);
		btnHNBC = (Button) findViewById(R.id.btnHNBC);

		try {
			int statecode = 0;
			if (global.getStateCode() != null
					&& global.getStateCode().length() > 0) {

				statecode = Integer.valueOf(global.getStateCode());
			}
			if (statecode == 20) {

				btnAnc.setVisibility(View.GONE);
				btnHNBC.setVisibility(View.GONE);

			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onclickButton(View view) {

		Intent in;
		switch (view.getId()) {

		case R.id.btnHousehold:

			if (global.getiGlobalRoleID() == 3) {
				in = new Intent(Report_Module.this,
						ReportIndicator_anmListHH.class);
				finish();
				startActivity(in);
			} else {
				in = new Intent(Report_Module.this, ReportIndicatorListHH.class);
				finish();
				startActivity(in);
			}

			break;
		case R.id.btnANC:

			if (global.getiGlobalRoleID() == 3) {
				in = new Intent(Report_Module.this,
						ReportIndicator_anmList.class);
				finish();
				startActivity(in);
			} else {
				in = new Intent(Report_Module.this,
						ReportIndicator_ashaList.class);
				finish();
				startActivity(in);
			}

			break;
		case R.id.btnHNBC:

			if (global.getiGlobalRoleID() == 3) {
				in = new Intent(Report_Module.this,
						Reportindicator_anmHnbclist.class);
				finish();
				startActivity(in);
			} else {
				in = new Intent(Report_Module.this,
						Reportindicator_Hnbclist.class);
				finish();
				startActivity(in);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(Report_Module.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}

}
