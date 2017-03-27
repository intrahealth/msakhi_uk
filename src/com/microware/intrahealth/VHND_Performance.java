package com.microware.intrahealth;

import java.util.ArrayList;
import com.microware.intrahealth.adapter.VHND_Perform_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_PerformanceIndicator;
import com.microware.intrahealth.object.tbl_VHNDPerformance;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class VHND_Performance extends Activity {
	GridView Grid_Preform;
	DataProvider dataProvider;
	ArrayList<MstVHND_PerformanceIndicator> VHND_Perform = new ArrayList<MstVHND_PerformanceIndicator>();
	ArrayList<tbl_VHNDPerformance> VHND_perform = new ArrayList<tbl_VHNDPerformance>();
	Global global;
	TextView txt_date, villagename_tx;
	// Spinner spin_yesno;
	Button btnsave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_performance);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		Grid_Preform = (GridView) findViewById(R.id.Grid_Preform);
		txt_date = (TextView) findViewById(R.id.txt_date);
		villagename_tx = (TextView) findViewById(R.id.villagename_tx);
		btnsave = (Button) findViewById(R.id.btnsave);
		btnsave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SavePerform();
			}
		});
		UpdateVHND();
	}

	public void UpdateVHND() {
		VHND_perform = dataProvider.getVHND_Performance(
				String.valueOf(global.getsGlobalAshaCode()),
				global.getVHND_ID(), 1);
		if (VHND_perform.size() > 0 && VHND_perform != null) {
			txt_date.setText(String.valueOf(VHND_perform.get(0).getDate()));
			String VillageName = "Select VillageName from MstVillage where VillageID='"
					+ VHND_perform.get(0).getVillageId() + "' and LanguageID=1";
			villagename_tx.setText(dataProvider.getRecord(VillageName));
			fillVHND();
		}
		fillVHND();//For Testing only ,It sud be hide
	}

	public void SavePerform() {

		int iSize = Grid_Preform.getChildCount();
		String Value = "";
		for (int i = 0; i < iSize; i++) {
			ViewGroup gridChild = (ViewGroup) Grid_Preform.getChildAt(i);
			// Spinner spin_yesno = (Spinner) gridChild
			// .findViewById(R.id.spin_yesno);
			// RadioGroup YesNo_RG = (RadioGroup) gridChild
			// .findViewById(R.id.YesNo_RG);
			RadioButton Yes_RB = (RadioButton) gridChild
					.findViewById(R.id.Yes_RB);
			RadioButton No_RB = (RadioButton) gridChild
					.findViewById(R.id.No_RB);
			RadioButton Clear_RB = (RadioButton) gridChild
					.findViewById(R.id.Clear_RB);

			int val = 0;
			if (Clear_RB.isChecked()) {
				val = 0;
			} else if (Yes_RB.isChecked()) {
				val = 1;
			} else if (No_RB.isChecked()) {
				val = 2;
			}

			Value = Value + val;
		}
		String OPT_Value = Value;
		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_Perform(global.getVHND_ID(),
				Integer.valueOf(global.getsGlobalAshaCode()), OPT_Value, "U");
		if (Returnvalue > 0) {
			alerttoast(R.string.savesuccessfully);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	private void alerttoast(int i) {
		Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
				.show();
	}

	public void fillVHND() {
		String sql = "Select OPT_Value from tbl_VHNDPerformance where VHND_ID='"
				+ global.getVHND_ID()
				+ "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		String OPT_Value = dataProvider.getRecord(sql);

		VHND_Perform = dataProvider.getVHND_Perform(123, 1);
		int Count = 0;
		if (VHND_Perform != null) {
			Count = VHND_Perform.size();
			android.view.ViewGroup.LayoutParams params = Grid_Preform
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Grid_Preform.setLayoutParams(params);
			Grid_Preform.setAdapter(new VHND_Perform_Adapter(this,
					VHND_Perform, global.getsGlobalAshaCode(), OPT_Value));
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(VHND_Performance.this, VHND_DueList.class);
		finish();
		startActivity(i);
	}

}
