package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.VHND_DueList_Adpater;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tbl_VHND_DueList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VHND_DueList extends Activity {

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
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	Date date = new Date();
	int FlagNo = 0;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
	int RefreshFlag = 0;
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
		FlagNo = intent.getExtras().getInt("NoFlag");
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
		String CurrentDate = dateFormat.format(date);
		mon = CurrentDate.split("-");
		int Year = 0, monthi = 0;
		Year = Integer.valueOf(mon[0]);
		monthi = Integer.valueOf(mon[1]) - 1;
		String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
				"Sep", "Oct", "Nov", "Dec" };
		String MonthNo_C = "", MonthNo_A = "", MonthNo_P = "";
		if (monthi == 11) {
			MonthNo_C = aa[10];
			MonthNo_A = aa[11];
			MonthNo_P = aa[0];
		} else {
			MonthNo_C = aa[monthi];
			MonthNo_A = aa[monthi + 1];
			MonthNo_P = aa[monthi - 1];
		}

		String sql = "";
		if (FlagNo == 1) {
			sql = "Select AW_Name from VHND_Schedule where ASHA_ID="
					+ global.getsGlobalAshaCode() + " and Year='" + Year
					+ "' and " + global.getVHND_MonthField().toString() + "='"
					+ global.getVHND_Date() + "' and Village_ID='" + Village_ID
					+ "'";
			spinVillageName
					.setText(String.valueOf(dataProvider.getRecord(sql)));
		} else {
			sql = "Select AW_Name from VHND_Schedule where ASHA_ID="
					+ global.getsGlobalAshaCode() + " and Year='" + Year
					+ "' and (" + MonthNo_C + "='" + global.getVHND_Date()
					+ "' or " + MonthNo_A + "='" + global.getVHND_Date()
					+ "' or " + MonthNo_P + "='" + global.getVHND_Date()
					+ "') and Village_ID='" + Village_ID + "'";
			spinVillageName
					.setText(String.valueOf(dataProvider.getRecord(sql)));
		}

		txt_date.setText(String.valueOf(global.getVHND_Date()));
		txt_date_show.setText(Validate.changeDateFormat(String.valueOf(global
				.getVHND_Date())));

		// String sl = "select count(*) from tbl_VHND_DueList where VillageId='"
		// + Village_ID + "' and AshaID='" + global.getsGlobalAshaCode()
		// + "' and Date='" + global.getVHND_Date() + "'";
		// int countt = Integer.valueOf(dataProvider.getRecord(sl));
		// if (countt == 0) {
		if (!txt_date.getText().toString().equalsIgnoreCase("")
				&& Village_ID != 0) {
			fillVHNDDUelist(txt_date.getText().toString(), Village_ID, FlagNo,
					0);
		}

		// Update and Refresh
		if (FlagNo == 1) {
			Update.setText(getResources().getString(R.string.save));
		} else {
			Update.setText(getResources().getString(R.string.Refresh));
		}
		Update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!txt_date.getText().toString().equalsIgnoreCase("")
						&& Village_ID != 0) {
					if (FlagNo != 1) {
						RefreshFlag = 1;
						fillVHNDDUelist(txt_date.getText().toString(),
								Village_ID, FlagNo, RefreshFlag);
					}
					if (FlagNo == 1) {
						SavePerform(global.getVHND_ID(), Village_ID,
								Integer.valueOf(global.getsGlobalAshaCode()),
								global.getVHND_Date(), "I");
						RefreshFlag = 1;
					} else if (FlagNo != 1 && RefreshFlag == 1) {
						SavePerform(global.getVHND_ID(), Village_ID,
								Integer.valueOf(global.getsGlobalAshaCode()),
								global.getVHND_Date(), "U");
					}
				}
			}
		});
		String sl = "select count(*) from tbl_VHND_DueList where VillageId='"
				+ Village_ID + "' and AshaID='" + global.getsGlobalAshaCode()
				+ "' and Date='" + global.getVHND_Date() + "'";
		int countt = Integer.valueOf(dataProvider.getRecord(sl));
		int DayCountLeft = 1;
		if (countt != 0) {
			String ss = "Select cast(round(julianday(Date)-julianday('NOW')) as int) from tbl_VHND_DueList where AshaID="
					+ global.getsGlobalAshaCode()
					+ " and VillageId="
					+ Village_ID
					+ " and (cast(round(julianday(Date)-julianday('NOW'))  as int) <=7)";
			DayCountLeft = Integer.valueOf(String.valueOf(dataProvider
					.getRecord(ss)));
		}
		if (DayCountLeft <= 0 && FlagNo != 1) {
			Update.setEnabled(false);
			AddPerform.setEnabled(true);
			Update.setBackgroundDrawable(this.getResources().getDrawable(
					R.color.gray));
			AddPerform.setBackgroundDrawable(this.getResources().getDrawable(
					R.drawable.btn_bg_custom));
		} else {
			 Update.setEnabled(true);
			 AddPerform.setEnabled(false);
			 AddPerform.setBackgroundDrawable(this.getResources().getDrawable(
			 R.color.gray));
		}
		// AddPerform.setEnabled(true);ss
		// AddPerform.setBackgroundDrawable(this.getResources().getDrawable(
		// R.drawable.btn_bg_custom));
		AddPerform.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				global.setVHND_Composite_Flag("U");
				// global.setVHND_ID(global.getVHND_ID());
				Intent i = new Intent(VHND_DueList.this, VHND_Performance.class);
				finish();
				startActivity(i);

			}
		});

		// TextShowing
		if (ActivityName.equalsIgnoreCase("Dashboard_Activity")) {
			NoduetoreceiveVHND_txt.setVisibility(View.GONE);
			Noleftout_txt.setVisibility(View.GONE);
		}
	}

	public void SavePerform(String VHND_id, int Villagid, int AHSAid,
			String date, String FLAG) {

		int iSize = VHND_Duelist_grid.getChildCount();
		String VHND_ID = VHND_id;
		int SS_ID = 0;
		int VillageId = Villagid;
		int AshaID = AHSAid;
		int AWWID = 0;
		int ANMID = 0;
		String Date = date;
		String Question_ID = "";
		String NoDueReceive_1st = "";
		String NoDue_1st = "";
		String NoDueReceive_2nd = "";
		String NoDue_2nd = "";
		String CreatedBy = "";
		if (global.getsGlobalUserID() != null
				&& global.getsGlobalUserID().length() > 0) {
			// CreatedBy = Integer.valueOf(global.getsGlobalUserID());
			CreatedBy = String.valueOf(global.getsGlobalUserID());
		}
		for (int i = 0; i < iSize; i++) {
			ViewGroup gridChild = (ViewGroup) VHND_Duelist_grid.getChildAt(i);
			TextView Slno_tx = (TextView) gridChild.findViewById(R.id.Slno_tx);
			TextView Noduetoreceive_tx = (TextView) gridChild
					.findViewById(R.id.Noduetoreceive_tx);
			TextView NoduetoreceiveVHND_tx = (TextView) gridChild
					.findViewById(R.id.NoduetoreceiveVHND_tx);
			String Question_id = Slno_tx.getText().toString();
			String NoDueReceive_1 = Noduetoreceive_tx.getText().toString();
			String NoDue_1 = NoduetoreceiveVHND_tx.getText().toString();
			String NoDueReceive_2 = Noduetoreceive_tx.getText().toString();
			String NoDue_2 = NoduetoreceiveVHND_tx.getText().toString();
			if (Integer.valueOf(Question_id) == 1) {
				Question_ID = Question_id;
			} else if (Integer.valueOf(Question_id) != 1) {
				Question_ID = Question_ID + "," + Question_id;
			}
			if (FlagNo != 1) {
				NoDueReceive_1st = U_NoDueReceive_1st;
				NoDue_1st = U_NoDue_1st;
			}
			if (Integer.valueOf(Question_id) == 1 && FlagNo == 1) {
				NoDueReceive_1st = NoDueReceive_1;
				NoDue_1st = NoDue_1;
			} else if (Integer.valueOf(Question_id) != 1 && FlagNo == 1) {
				NoDueReceive_1st = NoDueReceive_1st + "," + NoDueReceive_1;
				NoDue_1st = NoDue_1st + "," + NoDue_1;
			} else if (Integer.valueOf(Question_id) == 1 && FlagNo != 1) {
				NoDueReceive_2nd = NoDueReceive_2;
				NoDue_2nd = NoDue_2;
			} else if (Integer.valueOf(Question_id) != 1 && FlagNo != 1) {
				NoDueReceive_2nd = NoDueReceive_2nd + "," + NoDueReceive_2;
				NoDue_2nd = NoDue_2nd + "," + NoDue_2;
			}
			// if (Integer.valueOf(Question_id) == 1 && FlagNo == 1) {
			// // NoDue_1st = NoDue_1;
			// } else if (Integer.valueOf(Question_id) != 1 && FlagNo == 1) {
			// NoDue_1st = NoDue_1st + "," + NoDue_1;
			// } else if (Integer.valueOf(Question_id) == 1 && FlagNo == 1) {
			// NoDue_2nd = NoDueReceive_2;
			// } else if (Integer.valueOf(Question_id) != 1 && FlagNo != 1) {
			// NoDue_2nd = NoDue_2nd + "," + NoDue_2;
			// }
		}
		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_DueList(VHND_ID, SS_ID, VillageId,
				AshaID, AWWID, ANMID, Date, Question_ID, NoDueReceive_1st,
				NoDue_1st, NoDueReceive_2nd, NoDue_2nd, CreatedBy, FLAG);
		if (Returnvalue > 0) {
			if (FlagNo == 1) {
				Save_VHND(VillageId, Date, "I");
				FlagNo = 2;
				Update.setText(getResources().getString(R.string.Refresh));
			}
			alerttoast(R.string.savesuccessfully);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	public void Save_VHND(int Village_ID, String Datee, String VHND_Flag) {
		String VHND_ID = global.getVHND_ID();
		int SS_ID = 0;
		int VillageId = Village_ID;
		int AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		int AWWID = 0;
		int ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		String Date = Datee;
		String CreatedBy = "";
		if (global.getsGlobalUserID() != null
				&& global.getsGlobalUserID().length() > 0) {
			// CreatedBy = Integer.valueOf(global.getsGlobalUserID());
			CreatedBy = String.valueOf(global.getsGlobalUserID());
		}

		int Returnvalue = 0;
		Returnvalue = dataProvider.VHND_Perform(VHND_ID, SS_ID, VillageId,
				AshaID, AWWID, ANMID, Date, CreatedBy, VHND_Flag);
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
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		String sl = "select count(*) from tbl_VHND_DueList where VillageId='"
				+ Village_ID + "' and AshaID='" + global.getsGlobalAshaCode()
				+ "' and Date='" + global.getVHND_Date() + "'";
		int countt = Integer.valueOf(dataProvider.getMaxRecord(sl));
		if (countt == 0) {
			SavePerform(global.getVHND_ID(), Village_ID,
					Integer.valueOf(global.getsGlobalAshaCode()),
					global.getVHND_Date(), "I");
		} else if (FlagNo != 1 && RefreshFlag == 1) {
			SavePerform(global.getVHND_ID(), Village_ID,
					Integer.valueOf(global.getsGlobalAshaCode()),
					global.getVHND_Date(), "U");
		}
		Intent i = new Intent(VHND_DueList.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}
}
