package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.ImunizationChildList;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPFDetail;

public class VHND_DueList_Adpater_new extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tbl_pregnantwomen> Pregnant_woman;
	ArrayList<tblChild> child;
	// String Date = "";
	int AshaID = 0;
	Global global;
	String Date;
	int Village_ID = 0;
	int flag = 0;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
	int RefreshFlag = 0;
	String ActivityName = "";

	// String sql44 = "", sql55 = "";

	public VHND_DueList_Adpater_new(Context context,
			ArrayList<tbl_pregnantwomen> Pregnant_woman, int AshaID,
			String Date, int Village_ID, int flag) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.Pregnant_woman = Pregnant_woman;
		this.AshaID = AshaID;
		this.Date = Date;
		this.Village_ID = Village_ID;
		this.flag = flag;

	}

	public VHND_DueList_Adpater_new(Context context, ArrayList<tblChild> child,
			int AshaID, String Date, int Village_ID, int flag, String aa) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.child = child;
		this.AshaID = AshaID;
		this.Date = Date;
		this.Village_ID = Village_ID;
		this.flag = flag;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int value = 0;
		if (flag == 1) {
			value = Pregnant_woman.size();
		} else if (flag == 2) {
			value = child.size();
		}
		return value;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			// dataProvider = new DataProvider(context);
			gridview = new View(context);
			dataProvider = new DataProvider(context);
			gridview = layoutInflater.inflate(
					R.layout.vhnd_duelist_adpater_new, null);
			TextView Name = (TextView) gridview.findViewById(R.id.Name);
			TextView ANCItems = (TextView) gridview.findViewById(R.id.ANCItems);
			TextView GUID = (TextView) gridview.findViewById(R.id.GUID);

			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();
			String sql1 = "", sql2 = "", HushbandName = "", MotherLMP = "";
			if (flag == 1) {
				sql1 = "select HusbandName from tblpregnant_woman where PWGUID='"
						+ Pregnant_woman.get(position).getPWGUID() + "'";
				HushbandName = dataProvider.getRecord(sql1);
				sql2 = "select EDDDate from tblpregnant_woman where PWGUID='"
						+ Pregnant_woman.get(position).getPWGUID() + "'";
				MotherLMP = dataProvider.getRecord(sql2);
				String Namee = Pregnant_woman.get(position).getPWName();
				Name.setText(String.valueOf(Namee + " / " + HushbandName
						+ " / "
						+ Validate.changeDateFormat(String.valueOf(MotherLMP))));
				ANCItems.setText(String.valueOf(VaccineName(
						Pregnant_woman.get(position).getPWGUID(), 1)));
				GUID.setText(String.valueOf(Pregnant_woman.get(position)
						.getPWGUID()
						+ ","
						+ Pregnant_woman.get(position).getHHGUID()
						+ ","
						+ Pregnant_woman.get(position).getPWName()));

			} else if (flag == 2) {
				sql1 = "select PWName from tblPregnant_woman where PWGUID in (select pw_GUID from tblChild where ChildGUID='"
						+ child.get(position).getChildGUID() + "')";
				HushbandName = dataProvider.getRecord(sql1);
				sql2 = "select Child_dob from tblChild where ChildGUID='"
						+ child.get(position).getChildGUID() + "'";
				MotherLMP = dataProvider.getRecord(sql2);
				String Namee = child.get(position).getChild_name();
				Name.setText(String.valueOf(Namee + " / " + HushbandName
						+ " / "
						+ Validate.changeDateFormat(String.valueOf(MotherLMP))));
				ANCItems.setText(String.valueOf(VaccineName(child.get(position)
						.getChildGUID(), 2)));
				GUID.setText(String.valueOf(child.get(position).getChildGUID()
						+ "," + child.get(position).getHHGUID() + ","
						+ child.get(position).getChild_name()));
			}
			if (global.getiGlobalRoleID() == 2) {
				Name.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (flag == 1) {

							global.setsGlobalPWGUID(Pregnant_woman
									.get(position).getPWGUID());
							global.setVHND_flag(1);
							Intent i = new Intent(context, AncActivity.class);
							context.startActivity(i);
						} else if (flag == 2) {

							global.setsGlobalChildGUID(child.get(position)
									.getChildGUID());
							global.setVHND_flag(1);
							Intent i = new Intent(context,
									ImunizationChildList.class);
							context.startActivity(i);
						}

					}
				});
			}
		} else {
			gridview = convertView;
		}
		return gridview;
	}

	public String VaccineName(String GUID, int flag) {
		String Name = "";
		int countno = 0;
		String ANC[] = {
				(context.getResources().getString(R.string.Anc) + " 1"),
				(context.getResources().getString(R.string.Anc) + "  2"),
				(context.getResources().getString(R.string.Anc) + "  3"),
				(context.getResources().getString(R.string.Anc) + "  4") };
		String value[] = { context.getResources().getString(R.string.bcg),
				(context.getResources().getString(R.string.hep) + " 1"),
				(context.getResources().getString(R.string.hep) + " 2"),
				(context.getResources().getString(R.string.hep) + " 3"),
				context.getResources().getString(R.string.opv2),
				context.getResources().getString(R.string.opv3),
				context.getResources().getString(R.string.opv4),
				(context.getResources().getString(R.string.dpt) + " 1"),
				(context.getResources().getString(R.string.dpt) + " 2"),
				(context.getResources().getString(R.string.dpt) + " 3"),
				context.getResources().getString(R.string.Pentavalent1),
				context.getResources().getString(R.string.Pentavalent2),
				context.getResources().getString(R.string.Pentavalent3),
				context.getResources().getString(R.string.measles),
				context.getResources().getString(R.string.vitamin),
				context.getResources().getString(R.string.vitamintwo),
				context.getResources().getString(R.string.opvbooster),
				(context.getResources().getString(R.string.dptbooster) + " 1"),
				(context.getResources().getString(R.string.dptbooster) + " 2"),
				context.getResources().getString(R.string.JEVaccine1),
				context.getResources().getString(R.string.JEVaccine2) };
		if (flag == 1) {
			for (int i = 0; i < ANC.length; i++) {
				countno = countvalue(GUID, flag, i + 1);
				if (countno > 0) {
					if (i == 0) {
						Name = ANC[0];
					} else {
						if (Name.equalsIgnoreCase("")) {
							Name = ANC[i];
						} else {
							Name = Name + " / " + ANC[i];
						}
					}
				}
			}

		} else if (flag == 2) {
			for (int i = 0; i < value.length; i++) {
				countno = countvalue(GUID, flag, i + 1);
				if (countno > 0) {
					if (i == 0) {
						Name = value[0];
					} else {
						if (Name.equalsIgnoreCase("")) {
							Name = value[i];
						} else {
							Name = Name + " / " + value[i];
						}
					}
				}
			}
		}
		return Name;
	}

	public int countvalue(String GUIDs, int flags, int SwitchID) {
		int count = 0;
		String sql = "";
		if (flags == 1) {
			switch (SwitchID) {
			case 1:
				sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
						+ Date
						+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no=1 and a.pwGUID='"
						+ GUIDs + "' order by b.pwguid";
				break;
			case 2:
				sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
						+ Date
						+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no=2 and a.pwGUID='"
						+ GUIDs + "'  order by b.pwguid";
				break;
			case 3:
				sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
						+ Date
						+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no=3 and a.pwGUID='"
						+ GUIDs + "'  order by b.pwguid";
				break;
			case 4:
				sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
						+ Date
						+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no=4 and a.pwGUID='"
						+ GUIDs + "'  order by b.pwguid";
				break;
			}
		} else if (flags == 2) {
			switch (SwitchID) {
			case 1:
				sql = "Select count(*) from tblCHild where bcg='' or bcg==null and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)<365 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 2:
				sql = "Select count(*) from tblCHild where (hepb1='' or hepb1==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 42 and 69 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 3:
				sql = "Select count(*) from tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)between 70 and 97 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 4:
				sql = "Select count(*) from tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)>=98 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 5:
				sql = "Select count(*) from tblCHild where (opv1='' or opv1==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 42 and 69 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 6:
				sql = "Select count(*) from tblCHild where (opv1='' or opv1==null or opv2='' or opv2==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 70 and 97 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 7:
				sql = "Select count(*) from tblCHild where (opv1='' or opv1==null or opv2='' or opv2==null or opv3='' or opv3==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)>=98 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 8:
				sql = "Select count(*) from tblCHild where (dpt1='' or dpt1==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 42 and 69 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 9:
				sql = "Select count(*) from tblCHild where (dpt1='' or dpt1==null or dpt2='' or dpt2==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 70 and 97 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 10:
				sql = "Select count(*) from tblCHild where (dpt1='' or dpt1==null or dpt2='' or dpt2==null or dpt3='' or dpt3==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)>=98 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 11:
				sql = "Select count(*) from tblCHild where (Pentavalent1='' or Pentavalent1==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 42 and 69 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 12:
				sql = "Select count(*) from tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 70 and 97 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 13:
				sql = "Select count(*) from tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null or Pentavalent3='' or Pentavalent3==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int)>=98 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 14:
				sql = "Select count(*) from tblCHild where (measeals='' or measeals==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 270 and 365 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 15:
				sql = "Select count(*) from tblCHild where (vitaminA='' or vitaminA==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 270 and 365 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 16:
				sql = "Select count(*) from tblCHild where (VitaminAtwo='' or VitaminAtwo==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 540 and 720 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 17:
				sql = "Select count(*) from tblCHild where (OPVBooster='' or OPVBooster==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 480 and 720 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 18:
				sql = "Select count(*) from tblCHild where (DPTBooster='' or DPTBooster==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 480 and 720 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 19:
				sql = "Select count(*) from tblCHild where (DPTBoostertwo='' or DPTBoostertwo==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 1800 and 2160 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 20:
				sql = "Select count(*) from tblCHild where (JEVaccine1='' or JEVaccine1==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 270 and 365 and ChildGUID='"
						+ GUIDs + "'";
				break;
			case 21:
				sql = "Select count(*) from tblCHild where (JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
						+ Date
						+ "')-julianday(Child_dob))as int) between 480 and 730 and ChildGUID='"
						+ GUIDs + "'";
				break;
			}
		}
		count = dataProvider.getMaxRecordnew(sql);
		return count;
	}
}