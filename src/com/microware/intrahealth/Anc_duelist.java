package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.Anc_DueListadapter;
import com.microware.intrahealth.adapter.FP_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class Anc_duelist extends Activity {

	GridView gridanc;
	DataProvider dataProvider;
	Global global;
	ArrayList<tbl_pregnantwomen> member = new ArrayList<tbl_pregnantwomen>();
	TextView pwid, pwname, lmp, edd1;
	int visit = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ancduelist);

		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		pwid = (TextView) findViewById(R.id.pwid);
		pwname = (TextView) findViewById(R.id.pwname);
		lmp = (TextView) findViewById(R.id.lmp);
		edd1 = (TextView) findViewById(R.id.edd1);

		gridanc = (GridView) findViewById(R.id.gridanc);
		Intent intent = getIntent();
		int iFlag = 0;
		iFlag = intent.getExtras().getInt("Flag");
		if (iFlag == 4) {
			edd1.setVisibility(View.GONE);
			pwid.setText(getResources().getString(R.string.childid));
			pwname.setText(getResources().getString(R.string.childname));
			lmp.setText(getResources().getString(R.string.DateOfBirth));
			visit = intent.getExtras().getInt("visit");
			
		}else if (iFlag == 5) {
			edd1.setVisibility(View.GONE);
			pwid.setText(getResources().getString(R.string.srno));
			pwname.setText(getResources().getString(R.string.FamilyMemb));
			lmp.setText(getResources().getString(R.string.husbandname));
			
			
		}
		fillgrid(iFlag);

	}

	public void fillgrid(int flag) {

		if (flag == 0) {

			member = dataProvider.getdataanc(0, 0);
		} else if (flag == 1) {
			member = dataProvider.getdataanc(1, 0);
		} else if (flag == 2) {
			member = dataProvider.getdataanc(2, 0);
		} else if (flag == 4) {
			member = dataProvider.getdataanc(4, visit);
		}else if (flag == 5) {
			member = dataProvider.getdataanc(5, 0);
		}

		if (member != null) {

			android.view.ViewGroup.LayoutParams params = gridanc
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					300, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight1 = 0;
			gridHeight1 = hi * member.size();
			params.height = gridHeight1;
			gridanc.setLayoutParams(params);
			gridanc.setAdapter(new Anc_DueListadapter(this, member, flag));
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(Anc_duelist.this, Dashboard_Activity.class);
		finish();
		startActivity(i);

	}

}
