package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.TextView;
import com.microware.intrahealth.adapter.VHND_DueList_Adpater;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tbl_VHND_DueList;

public class VHND_DueList_Report extends Activity {
	GridView VHND_Duelist_grid;
	DataProvider dataProvider;
	Global global;
	ArrayAdapter<String> adapter;
	Dialog datepic;
	TextView txt_date;
	// Spinner spinVillageName;
	TextView spinVillageName;
	ArrayList<MstVHND_DueListItems> VHND_Duelist = new ArrayList<MstVHND_DueListItems>();
	ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
	ArrayList<tbl_VHND_DueList> VNHDDuelistss = new ArrayList<tbl_VHND_DueList>();
	int Village_ID = 0;
	Button Update, AddPerform;
	Date date = new Date();
	int FlagNo = 2;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";

	TextView NoduetoreceiveVHND_txt, Noleftout_txt;
	String ActivityName = "";
	// Date Show
	TextView txt_date_show;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_duelist);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		Intent intent = getIntent();
		ActivityName = intent.getExtras().getString("ActivityName");

		txt_date_show = (TextView) findViewById(R.id.txt_date_show);
		NoduetoreceiveVHND_txt = (TextView) findViewById(R.id.NoduetoreceiveVHND_txt);
		Noleftout_txt = (TextView) findViewById(R.id.Noleftout_txt);

		VHND_Duelist_grid = (GridView) findViewById(R.id.VHND_Duelist_grid);
		txt_date = (TextView) findViewById(R.id.txt_date);
		// spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
		spinVillageName = (TextView) findViewById(R.id.spinVillageName);
		Update = (Button) findViewById(R.id.Update);
		AddPerform = (Button) findViewById(R.id.AddPerform);
		Village_ID = Integer.valueOf(global.getVHND_VillageID());
		String mon[] = {};
		String CurrentDate = global.getVHND_Date();
		mon = CurrentDate.split("-");
		int Year = 0, monthi = 0;
		Year = Integer.valueOf(mon[0]);
		monthi = Integer.valueOf(mon[1]) - 1;
		String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" };
		String MonthNo = "";
		MonthNo = aa[monthi];
		String sql = "";
		sql = "Select AW_Name from VHND_Schedule where ASHA_ID="
				+ global.getsGlobalAshaCode() + " and Year='" + Year
				+ "' and (" + MonthNo + "='" + global.getVHND_Date()
				+ "') and Village_ID='" + Village_ID + "'";

		spinVillageName.setText(String.valueOf(dataProvider.getRecord(sql)));
		txt_date.setText(String.valueOf(global.getVHND_Date()));
		txt_date_show.setText(Validate.changeDateFormat(String.valueOf(global
				.getVHND_Date())));
		if (!txt_date.getText().toString().equalsIgnoreCase("")
				&& Village_ID != 0) {
			fillVHNDDUelist(txt_date.getText().toString(), Village_ID, 2, 0);
		}
		Update.setVisibility(View.GONE);
		AddPerform.setVisibility(View.GONE);

	}

	public void fillVHNDDUelist(String Date, int VillageID, int flagnoo,
			int RefreshFlag) {
		String sql0 = "", sql1 = "", sql2 = "", sql3 = "", sql4 = "";
		sql0 = "Select Question_ID from tbl_VHND_DueList where VHND_ID='"
				+ global.getVHND_ID() + "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		sql1 = "Select NoDueReceive_1st from tbl_VHND_DueList where VHND_ID='"
				+ global.getVHND_ID() + "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		sql2 = "Select NoDue_1st from tbl_VHND_DueList where VHND_ID='"
				+ global.getVHND_ID() + "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		sql3 = "Select NoDueReceive_2nd from tbl_VHND_DueList where VHND_ID='"
				+ global.getVHND_ID() + "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		sql4 = "Select NoDue_2nd from tbl_VHND_DueList where VHND_ID='"
				+ global.getVHND_ID() + "' and AshaID='"
				+ global.getsGlobalAshaCode() + "'";
		U_Question_ID = dataProvider.getRecord(sql0);
		U_NoDueReceive_1st = dataProvider.getRecord(sql1);
		U_NoDue_1st = dataProvider.getRecord(sql2);
		U_NoDueReceive_2nd = dataProvider.getRecord(sql3);
		U_NoDue_2nd = dataProvider.getRecord(sql4);
		// String U_NoDueReceive_1st = "", U_NoDue_1st = "", U_NoDueReceive_2nd
		// = "",
		// U_NoDue_2nd = "";
		VHND_Duelist = dataProvider.getVHND_Duelist(global.getLanguage(), 0);
		int Count = 0;
		if (VHND_Duelist != null) {
			Count = VHND_Duelist.size();
			android.view.ViewGroup.LayoutParams params = VHND_Duelist_grid
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			VHND_Duelist_grid.setLayoutParams(params);
			VHND_Duelist_grid.setAdapter(new VHND_DueList_Adpater(this,
					VHND_Duelist, Integer.valueOf(global.getsGlobalAshaCode()),
					Date, Village_ID, FlagNo, U_Question_ID,
					U_NoDueReceive_1st, U_NoDue_1st, U_NoDueReceive_2nd,
					U_NoDue_2nd, RefreshFlag, ActivityName));
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
		Intent i = new Intent(VHND_DueList_Report.this,
				Dashboard_Activity.class);
		finish();
		startActivity(i);
	}
}
