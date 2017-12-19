package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.VHND_DueList_Adpater;
import com.microware.intrahealth.adapter.VHND_DueList_Adpater_new;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_VHND_DueList;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPFDetail;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class VHND_DueList_new extends Activity {

	DataProvider dataProvider;
	Global global;
	ArrayAdapter<String> adapter;
	Dialog datepic;
	TextView txt_date;
	TextView spinVillageName;
	ArrayList<MstVHND_DueListItems> VHND_Duelist = new ArrayList<MstVHND_DueListItems>();
	ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
	ArrayList<tbl_VHND_DueList> VNHDDuelistss = new ArrayList<tbl_VHND_DueList>();
	int Village_ID = 0;
	Button btnSave;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	Date date = new Date();
	int FlagNo = 0;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
	int RefreshFlag = 0;
	String ActivityName = "";

	// Date Show
	TextView txt_date_show;
	String VHND_Date = "";

	ArrayList<tbl_pregnantwomen> Pregnant_woman;
	ArrayList<tblmstFPFDetail> FP_duleist;
	ArrayList<tblChild> child;
	LinearLayout tblrow_ANC, tblrow_Immunization;
	TextView txt_ANC, txt_Immunization;
	GridView Gridview_ANC, Gridview_Immunization;

	int formflag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vhnd_duelist_new);
		dataProvider = new DataProvider(this);
		global = (Global) this.getApplicationContext();

		// Intent intent = getIntent();
		// FlagNo = intent.getExtras().getInt("NoFlag");
		// ActivityName = intent.getExtras().getString("ActivityName");

		txt_date_show = (TextView) findViewById(R.id.txt_date_show);
		txt_date = (TextView) findViewById(R.id.txt_date);
		spinVillageName = (TextView) findViewById(R.id.spinVillageName);
		btnSave = (Button) findViewById(R.id.btnSave);

		// TableRow
		tblrow_ANC = (LinearLayout) findViewById(R.id.tblrow_ANC);
		tblrow_Immunization = (LinearLayout) findViewById(R.id.tblrow_Immunization);
		// TextView
		txt_ANC = (TextView) findViewById(R.id.txt_ANC);
		txt_Immunization = (TextView) findViewById(R.id.txt_Immunization);
		// GridView
		Gridview_ANC = (GridView) findViewById(R.id.Gridview_ANC);
		Gridview_Immunization = (GridView) findViewById(R.id.Gridview_Immunization);

		Village_ID = Integer.valueOf(global.getVHND_VillageID());
		VHND_Date = String.valueOf(global.getVHND_Date());
		txt_date.setText(VHND_Date);
		try {
			txt_date_show.setText(Validate.changeDateFormat(String
					.valueOf(global.getVHND_Date())));

			String mon[] = {};
			String CurrentDate = dateFormat.format(date);
			mon = CurrentDate.split("-");
			int Year = 0, monthi = 0;
			Year = Integer.valueOf(mon[0]);
			monthi = Integer.valueOf(mon[1]) - 1;
			String aa[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
					"Aug", "Sep", "Oct", "Nov", "Dec" };
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
						+ "' and " + global.getVHND_MonthField().toString()
						+ "='" + global.getVHND_Date() + "' and Village_ID='"
						+ Village_ID + "'";
				spinVillageName.setText(String.valueOf(dataProvider
						.getRecord(sql)));
			} else {
				sql = "Select AW_Name from VHND_Schedule where ASHA_ID="
						+ global.getsGlobalAshaCode() + " and Year='" + Year
						+ "' and (" + MonthNo_C + "='" + global.getVHND_Date()
						+ "' or " + MonthNo_A + "='" + global.getVHND_Date()
						+ "' or " + MonthNo_P + "='" + global.getVHND_Date()
						+ "') and Village_ID='" + Village_ID + "'";
				spinVillageName.setText(String.valueOf(dataProvider
						.getRecord(sql)));
			}

			if (!txt_date.getText().toString().equalsIgnoreCase("")
					&& Village_ID != 0) {
				FillVHNDDueList_ANC(VHND_Date, global.getsGlobalAshaCode(),
						Village_ID);
				FillVHNDDueList_Immunization(VHND_Date,
						global.getsGlobalAshaCode(), Village_ID);
			}
			btnSave.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SaveVHNDDuelist();
				}
			});
			if (global.getiGlobalRoleID() == 3) {
				btnSave.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void SaveVHNDDuelist() {

		String MemberGUID = "";
		String HHGUID = "";
		String MemberName = "";
		String VaccineNames = "";
		String VHNDDate = global.getVHND_Date();
		int VillageID = Integer.valueOf(global.getVHND_VillageID());
		int AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		int ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		// String CreatedOn = "";
		String CreatedBy = global.getsGlobalUserID();
		// String ModifyOn = "";
		String ModifyBy = "";
		String Flag = "";
		String namess = "", vacciness = "", name = "";
		String Names[] = {};
		int iSizeANC = Gridview_ANC.getChildCount();
		int count = 0;
		String sql = "";
		int returnvalue = 0;
		for (int i = 0; i < iSizeANC; i++) {
			try {
				ViewGroup gridChild = (ViewGroup) Gridview_ANC.getChildAt(i);
				// TextView Name = (TextView) gridChild.findViewById(R.id.Name);
				TextView ANCItems = (TextView) gridChild
						.findViewById(R.id.ANCItems);
				TextView GUID = (TextView) gridChild.findViewById(R.id.GUID);
				TextView Name = (TextView) gridChild.findViewById(R.id.Name);

				namess = GUID.getText().toString();
				Names = namess.split(",");
				vacciness = ANCItems.getText().toString();
				name = Name.getText().toString();
				MemberGUID = Names[0];
				if (Names.length > 1) {
					HHGUID = Names[1];
				} else {
					HHGUID = "";

				}
				if (Names.length > 2) {
					MemberName = Names[2];
				} else {
					MemberName = "";

				}
				VaccineNames = String.valueOf(vacciness.replace("/", ","))
						.trim();
				sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
						+ VHNDDate + "' and VillageID='" + VillageID
						+ "' and BeneficiaryGUID='" + MemberGUID + "'";
				count = dataProvider.getMaxRecordnew(sql);
				if (count > 0) {
					Flag = "U";
				} else {
					Flag = "I";
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			returnvalue = dataProvider.VHND_DueListNew(MemberGUID, HHGUID,
					MemberName, VaccineNames, VHNDDate, VillageID, AshaID,
					ANMID, CreatedBy, ModifyBy, Flag, 1, name);

		}
		// Gridview_ANC, // Gridview_Immunization
		int iSizeImmunization = Gridview_Immunization.getChildCount();
		for (int i = 0; i < iSizeImmunization; i++) {
			try {
				ViewGroup gridChild = (ViewGroup) Gridview_Immunization
						.getChildAt(i);
				// TextView Name = (TextView) gridChild.findViewById(R.id.Name);
				TextView ANCItems = (TextView) gridChild
						.findViewById(R.id.ANCItems);
				TextView GUID = (TextView) gridChild.findViewById(R.id.GUID);
				TextView Name = (TextView) gridChild.findViewById(R.id.Name);
				namess = GUID.getText().toString();
				Names = namess.split(",");
				vacciness = ANCItems.getText().toString();
				name = Name.getText().toString();
				MemberGUID = Names[0];

				if (Names.length > 1) {
					HHGUID = Names[1];
				} else {
					HHGUID = "";

				}
				if (Names.length > 2) {
					MemberName = Names[2];
				} else {
					MemberName = "";

				}
				VaccineNames = String.valueOf(vacciness.replace("/", ","))
						.trim();
				sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
						+ VHNDDate + "' and VillageID='" + VillageID
						+ "' and BeneficiaryGUID='" + MemberGUID + "'";
				count = dataProvider.getMaxRecordnew(sql);
				if (count > 0) {
					Flag = "U";
				} else {
					Flag = "I";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			returnvalue = dataProvider.VHND_DueListNew(MemberGUID, HHGUID,
					MemberName, VaccineNames, VHNDDate, VillageID, AshaID,
					ANMID, CreatedBy, ModifyBy, Flag, 2, name);
		}
		if (returnvalue > 0) {
			alerttoast(R.string.savesuccessfully);
		} else {
			alerttoast(R.string.NotSave);
		}
	}

	private void alerttoast(int i) {
		Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
				.show();
	}

	public void FillVHNDDueList_ANC(String Date, String AshaID, int VillageID) {
		String sql = "";
		int sizecount = 0;

		sql = "Select * from tblPregnant_woman a inner join tblANCVisit b on cast(round((julianday('"
				+ Date
				+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
				+ AshaID + " and a.AshaID=" + AshaID + " order by b.pwguid";

		// Pregnant_woman = dataProvider.getMemberName(sql, 0);
		Pregnant_woman = dataProvider.getPregnantWomendata(sql, 6, 00);
		if (Pregnant_woman != null) {
			sizecount = Pregnant_woman.size();
		}
		SetVisibletableRow(tblrow_ANC, sizecount);

		int Count = 0;
		if (Pregnant_woman != null) {
			Count = Pregnant_woman.size();
			txt_ANC.setText(getResources().getString(R.string.ancPregnant)
					+ " - " + Pregnant_woman.size());
			android.view.ViewGroup.LayoutParams params = Gridview_ANC
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Gridview_ANC.setLayoutParams(params);
			Gridview_ANC.setAdapter(new VHND_DueList_Adpater_new(this,
					Pregnant_woman,
					Integer.valueOf(global.getsGlobalAshaCode()), Date,
					Village_ID, 1));
		}

	}

	public void FillVHNDDueList_Immunization(String Date, String AshaID,
			int VillageID) {
		String sql = "";
		int sizecount = 0;
		sql = "Select * from tblCHild where ((bcg='' or bcg==null and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)<365) or ((hepb1='' or hepb1==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 42 and 69) or ((hepb2='' or hepb2==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)between 70 and 97) or ((hepb3='' or hepb3==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)>=98) or ((opv1='' or opv1==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 42 and 69) or ((opv2='' or opv2==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 70 and 97) or ((opv3='' or opv3==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)>=98) or ((dpt1='' or dpt1==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 42 and 69) or ((dpt2='' or dpt2==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 70 and 97) or ((dpt3='' or dpt3==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)>=98) or ((Pentavalent1='' or Pentavalent1==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 42 and 69) or ((Pentavalent2='' or Pentavalent2==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 70 and 97) or ((Pentavalent3='' or Pentavalent3==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int)>=98) or ((measeals='' or measeals==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 270 and 365) or ((vitaminA='' or vitaminA==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 270 and 365) or ((VitaminAtwo='' or VitaminAtwo==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 540 and 720) or ((OPVBooster='' or OPVBooster==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBooster='' or DPTBooster==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBoostertwo='' or DPTBoostertwo==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 1800 and 2160) or ((JEVaccine1='' or JEVaccine1==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 270 and 365) or ((JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
				+ Date
				+ "')-julianday(Child_dob))as int) between 480 and 730)) and AshaID="
				+ AshaID + "";

		child = dataProvider.gettblChild("",sql, 7,0);
		if (child != null) {
			sizecount = child.size();
		}
		SetVisibletableRow(tblrow_Immunization, sizecount);
		int Count = 0;
		if (child != null) {
			Count = child.size();
			txt_Immunization.setText(getResources().getString(R.string.Immunization)
					+ " - " + child.size());
			android.view.ViewGroup.LayoutParams params = Gridview_Immunization
					.getLayoutParams();
			Resources r = getResources();
			float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
					60, r.getDisplayMetrics());
			int hi = Math.round(px);
			int gridHeight = 0;
			gridHeight = hi * Count;
			params.height = gridHeight;
			Gridview_Immunization.setLayoutParams(params);
			Gridview_Immunization.setAdapter(new VHND_DueList_Adpater_new(this,
					child, Integer.valueOf(global.getsGlobalAshaCode()), Date,
					Village_ID, 2, ""));
		}

	}

	public void SetVisibletableRow(LinearLayout tbrow, int size) {
		if (size > 0) {
			tbrow.setVisibility(View.VISIBLE);
		} else {
			tbrow.setVisibility(View.GONE);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(VHND_DueList_new.this, VHND_DateRecord.class);
		finish();
		startActivity(i);
	}
}
