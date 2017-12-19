package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVHND_DueListItems;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPFDetail;

public class VHND_DueList_Adpater extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<MstVHND_DueListItems> VHND_Duelist;
	ArrayList<tbl_pregnantwomen> Pregnant_woman;
	ArrayList<tblmstFPFDetail> FP_duleist;
	ArrayList<tblChild> child;
	// String Date = "";
	int AshaID = 0;
	Global global;
	String Date;
	int Village_ID = 0;
	int flagcountno = 0;
	String U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
			U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
	int RefreshFlag = 0;
	String ActivityName = "";

	// String sql44 = "", sql55 = "";

	public VHND_DueList_Adpater(Context context,
			ArrayList<MstVHND_DueListItems> VHND_Duelist, int AshaID,
			String Date, int Village_ID, int flagcountno, String U_Question_ID,
			String U_NoDueReceive_1st, String U_NoDue_1st,
			String U_NoDueReceive_2nd, String U_NoDue_2nd, int RefreshFlag,
			String ActivityName) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHND_Duelist = VHND_Duelist;
		this.AshaID = AshaID;
		this.Date = Date;
		this.Village_ID = Village_ID;
		this.flagcountno = flagcountno;
		this.U_Question_ID = U_Question_ID;
		this.U_NoDueReceive_1st = U_NoDueReceive_1st;
		this.U_NoDue_1st = U_NoDue_1st;
		this.U_NoDueReceive_2nd = U_NoDueReceive_2nd;
		this.U_NoDue_2nd = U_NoDue_2nd;
		this.RefreshFlag = RefreshFlag;
		this.ActivityName = ActivityName;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return VHND_Duelist.size();
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
			gridview = layoutInflater.inflate(R.layout.vhnd_duelist_adpater,
					null);
			TextView Slno_tx = (TextView) gridview.findViewById(R.id.Slno_tx);
			TextView Service_tx = (TextView) gridview
					.findViewById(R.id.Service_tx);
			TextView Noduetoreceive_tx = (TextView) gridview
					.findViewById(R.id.Noduetoreceive_tx);
			TextView NoduetoreceiveVHND_tx = (TextView) gridview
					.findViewById(R.id.NoduetoreceiveVHND_tx);
			TextView Noleftout_tx = (TextView) gridview
					.findViewById(R.id.Noleftout_tx);
			dataProvider = new DataProvider(context);
			global = (Global) context.getApplicationContext();
			Slno_tx.setText(String.valueOf(VHND_Duelist.get(position).getID()));
			Service_tx.setText(String.valueOf(VHND_Duelist.get(position)
					.getItems()));
			if ((flagcountno == 1 && RefreshFlag != 1)
					|| (RefreshFlag == 1 && flagcountno != 1)) {
				// if (flagcountno == 1) {
				// Slno_tx.setText(String.valueOf(VHND_Duelist.get(position)
				// .getID()));
				// Service_tx.setText(String.valueOf(VHND_Duelist.get(position)
				// .getItems()));
				String sql1 = "", sql2 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "", sql7 = "", sql8 = "";
				final int ID = VHND_Duelist.get(position).getID();
				int Count1 = 0, Count2 = 0;
				try {
					if (ID == 1 || ID == 2 || ID == 3 || ID == 4) {
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
								+ Date
								+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no='"
								+ ID
								+ "' and b.ByAshaID="
								+ AshaID
								+ " and a.AshaID="
								+ AshaID
								+ " order by b.pwguid";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
								+ Date
								+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate !=null and ispregnant=1 and b.visit_no='"
								+ ID
								+ "' and b.ByAshaID="
								+ AshaID
								+ " and a.AshaID="
								+ AshaID
								+ " order by b.pwguid";
					} else {
						switch (ID) {
						case 5:
							sql1 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTfirstDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate='' and ByAshaID="
									+ AshaID + ")";
							sql2 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTfirstDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate!='' and ByAshaID="
									+ AshaID + ")";
							break;
						case 6:
							sql1 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTsecondDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate='' and ByAshaID="
									+ AshaID + ")";
							sql2 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTsecondDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate!='' and ByAshaID="
									+ AshaID + ")";
						case 7:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.IFARecieved=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.IFARecieved=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 8:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.CalciumReceived=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.CalciumReceived=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 9:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BirthWeight=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.BirthWeight=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 10:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BP=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.BP=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 11:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.Hemoglobin=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.Hemoglobin=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 12:
							sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.UrineTest=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate !=null and a.ispregnant=1 and b.UrineTest=0 and b.ByAshaID="
									+ AshaID + " and a.AshaID=" + AshaID + "";
							break;
						case 13:
							// sql1 =
							// "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and ispregnant=1 and UrineTest=0";
							// sql2 =
							// "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and ispregnant=1 and UrineTest=0";
							break;
						case 14:
							sql1 = "tblCHild where bcg='' or bcg==null and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where bcg!=null and AshaID="
									+ AshaID + "";
							break;
						case 15:
							sql1 = "tblCHild where (opv1='' or opv1==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (opv1!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							break;
						case 16:
							sql1 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (opv2!=null or opv1!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							break;
						case 17:
							sql1 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==null or opv3='' or opv3==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (opv2!=null or opv1!=null or opv3!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							break;
						case 18:
							sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (Pentavalent1!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							break;
						case 19:
							sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (Pentavalent1!=null or Pentavalent2!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							break;
						case 20:
							sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null or Pentavalent3='' or Pentavalent3==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (Pentavalent1!=null or Pentavalent2!=null or Pentavalent3!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							break;
						case 21:
							sql1 = "tblCHild where (hepb1='' or hepb1==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (hepb1!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=42 and AshaID="
									+ AshaID + "";
							break;
						case 22:
							sql1 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (hepb1!=null or hepb2!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=70 and AshaID="
									+ AshaID + "";
							break;
						case 23:
							sql1 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (hepb1!=null or hepb2!=null or hepb3!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98 and AshaID="
									+ AshaID + "";
							break;
						case 24:
							sql1 = "tblCHild where (IPV='' or IPV==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98";
							sql2 = "tblCHild where (IPV!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int)>=98";
							break;
						case 25:
							sql1 = "tblCHild where (JEVaccine1='' or JEVaccine1==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (JEVaccine1!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							sql5 = "tblCHild where (JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 480 and 730 and AshaID="
									+ AshaID + "";
							sql6 = "tblCHild where (JEVaccine2!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 480 and 730 and AshaID="
									+ AshaID + "";
							break;
						case 26:
							sql1 = "tblCHild where (measeals='' or measeals==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (measeals!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							break;
						case 27:
							sql1 = "tblCHild where (vitaminA='' or vitaminA==null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							sql2 = "tblCHild where (vitaminA!=null) and cast(round(julianday('"
									+ Date
									+ "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									+ AshaID + "";
							break;
						case 28:
							sql1 = "tblFP_followup where (methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) and AshaID="
									+ AshaID + "";
							sql2 = "tblFP_followup where (methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) and AshaID="
									+ AshaID + "";// Alternateive condition
							break;
						default:
							Noduetoreceive_tx.setText(String.valueOf(0));
							NoduetoreceiveVHND_tx.setText(String.valueOf(0));
							Noleftout_tx.setText(String.valueOf(0 - 0));

						}
					}
					if (ID <= 13) {
						sql3 = "select count(distinct a.pwguid) from " + sql1;
						sql4 = "select count(distinct a.pwguid) from " + sql2;
					} else if (ID > 13 && ID != 25 && ID != 28) {
						sql3 = "select count(distinct ChildGUID) from " + sql1;
						sql4 = "select count(distinct ChildGUID) from " + sql2;
					} else if (ID == 25) {
						sql3 = "select count(distinct ChildGUID) from " + sql1;
						sql4 = "select count(distinct ChildGUID) from " + sql2;
						sql7 = "select count(distinct ChildGUID) from " + sql5;
						sql8 = "select count(distinct ChildGUID) from " + sql6;
					} else if (ID == 28) {
						sql3 = "select count(distinct Womenname) from " + sql1;
						sql4 = "select count(distinct Womenname) from " + sql2;
					}
					if (ID == 25) {
						Count1 = Integer.valueOf(dataProvider
								.getMaxRecordnew(sql3))
								+ Integer.valueOf(dataProvider
										.getMaxRecordnew(sql7));
						Count1 = Integer.valueOf(dataProvider
								.getMaxRecordnew(sql4))
								+ Integer.valueOf(dataProvider
										.getMaxRecordnew(sql8));
					} else {
						Count1 = dataProvider.getMaxRecordnew(sql3);
						Count2 = dataProvider.getMaxRecordnew(sql4);
					}
					// Count1 = dataProvider.getMaxRecordnew(sql3);
					// Count2 = dataProvider.getMaxRecordnew(sql4);
					final String sql44, sql55, sql66, sql77;
					// if (ID <= 13) {
					sql44 = "Select PWName from tblPregnant_woman where pwguid in (select distinct a.pwguid from "
							+ sql1 + ")";
					sql55 = "Select PWName from tblPregnant_woman where pwguid in (select distinct a.pwguid from "
							+ sql2 + ")";
					// }

					sql66 = "select child_name from  " + sql1 + "";
					sql77 = "Select child_name from " + sql2 + "";
					Noduetoreceive_tx.setText(String.valueOf(Count1));
					NoduetoreceiveVHND_tx.setText(String.valueOf(Count2));
					Noleftout_tx.setText(String.valueOf(Count1 - Count2));
					Noduetoreceive_tx
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

									// sql2 =
									// "tblCHild where (JEVaccine1!=null) and cast(round(julianday('"+
									// Date+
									// "')-julianday(Child_dob))as int) between 270 and 365 and AshaID="
									// + AshaID + "";
									// sql5 =
									// "tblCHild where (JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"+
									// Date+
									// "')-julianday(Child_dob))as int) between 480 and 730 and AshaID="
									// + AshaID + "";
									if (ID <= 13) {
										memberlistshow(sql44, ID);
									} else if (ID > 13 && ID != 25 && ID != 28) {
										memberlistshow(sql66, ID);
									} else if (ID == 25) {
										memberlistshow(
												"select child_name from tblCHild where ((JEVaccine1='' or JEVaccine1==null) or cast(round(julianday('"
														+ Date
														+ "')-julianday(Child_dob))as int) between 270 and 365) and ((JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
														+ Date
														+ "')-julianday(Child_dob))as int) between 480 and 730) and AshaID="
														+ AshaID + "", ID);
									} else if (ID == 28) {
										memberlistshow(
												"select Womenname from tblFP_followup where (methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) and AshaID="
														+ AshaID + "", ID);
									}
								}
							});
					NoduetoreceiveVHND_tx
							.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									if (ID <= 13) {
										memberlistshow(sql55, ID);
									} else if (ID > 13 && ID != 25 && ID != 28) {
										memberlistshow(sql77, ID);
									} else if (ID == 25) {
										memberlistshow(
												"select child_name from tblCHild where ((JEVaccine1!=null) and cast(round(julianday('"
														+ Date
														+ "')-julianday(Child_dob))as int) between 270 and 365) or ((JEVaccine2!=null) and cast(round(julianday('"
														+ Date
														+ "')-julianday(Child_dob))as int) between 480 and 730) and AshaID="
														+ AshaID + "", ID);
									} else if (ID == 28) {
										memberlistshow(
												"select Womenname from tblFP_followup where (methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) or (methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid)) and AshaID="
														+ AshaID + "", ID);// Alter
																			// Condition
									}
								}
							});

				} catch (Exception exception) {
					Log.e("DataProvider", "Error in tbl_Incentive :: "
							+ exception.getMessage());
				}
			} else if (flagcountno != 1
					&& (U_NoDueReceive_2nd == null || U_NoDueReceive_2nd.trim()
							.equalsIgnoreCase(""))
					&& (U_NoDue_2nd == null || U_NoDue_2nd.trim()
							.equalsIgnoreCase("")) && RefreshFlag != 1) {
				// Update Method
				// U_Question_ID, U_NoDueReceive_1st = "", U_NoDue_1st = "",
				// U_NoDueReceive_2nd = "", U_NoDue_2nd = "";
				// Service_tx,Noduetoreceive_tx ,NoduetoreceiveVHND_tx
				// ,Noleftout_tx
				String NoDueReceive[] = {}, NoDue[] = {};
				int QuestionID = Integer.valueOf(Slno_tx.getText().toString());
				NoDueReceive = U_NoDueReceive_1st.split(",");
				NoDue = U_NoDue_1st.split(",");
				Noduetoreceive_tx.setText(String
						.valueOf(NoDueReceive[QuestionID - 1]));
				NoduetoreceiveVHND_tx.setText(String
						.valueOf(NoDue[QuestionID - 1]));
				Noleftout_tx.setText(String.valueOf(Integer
						.valueOf(NoDueReceive[QuestionID - 1])
						- Integer.valueOf(NoDue[QuestionID - 1])));
			} else if (flagcountno != 1
					&& (U_NoDueReceive_2nd != null || !U_NoDueReceive_2nd
							.trim().equalsIgnoreCase(""))
					&& (U_NoDue_2nd != null || !U_NoDue_2nd.trim()
							.equalsIgnoreCase("")) && RefreshFlag != 1) {
				String Ques_id[] = {}, NoDueReceive[] = {}, NoDue[] = {};
				Ques_id = U_Question_ID.split(",");
				int QuestionID = Integer.valueOf(Slno_tx.getText().toString());
				NoDueReceive = U_NoDueReceive_2nd.split(",");
				NoDue = U_NoDue_2nd.split(",");
				Noduetoreceive_tx.setText(String
						.valueOf(NoDueReceive[QuestionID - 1]));
				NoduetoreceiveVHND_tx.setText(String
						.valueOf(NoDue[QuestionID - 1]));
				Noleftout_tx.setText(String.valueOf(Integer
						.valueOf(NoDueReceive[QuestionID - 1])
						- Integer.valueOf(NoDue[QuestionID - 1])));

			}

			if (ActivityName.equalsIgnoreCase("Dashboard_Activity")) {
				NoduetoreceiveVHND_tx.setVisibility(View.GONE);
				Noleftout_tx.setVisibility(View.GONE);
			} else if (ActivityName.equalsIgnoreCase("Report_Module")) {
				NoduetoreceiveVHND_tx.setVisibility(View.VISIBLE);
				Noleftout_tx.setVisibility(View.VISIBLE);
			}
		} else {
			gridview = convertView;
		}
		return gridview;
	}

	public void memberlistshow(String sql, final int ID) {
		// Create custom dialog object
		final Dialog dialog = new Dialog(context);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.memberlist, null, false);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		try {
			dataProvider = new DataProvider(context);
			String memberlists[] = {};
			if (ID <= 13) {
				Pregnant_woman = dataProvider.getMemberName(sql, 0);
				int aa = Pregnant_woman.size();
				memberlists = new String[aa];
				for (int i = 0; i < aa; i++) {
					memberlists[i] = Pregnant_woman.get(i).getPWName();
				}
			} else if (ID > 13 && ID != 28) {
				child = dataProvider.getChildGUID(sql, 0);
				int aa = child.size();
				memberlists = new String[aa];
				for (int i = 0; i < aa; i++) {
					memberlists[i] = child.get(i).getChild_name();
				}
			} else if (ID == 28) {
				FP_duleist = dataProvider.getFP_Duelist(sql, 0);
				int aa = FP_duleist.size();
				memberlists = new String[aa];
				for (int i = 0; i < aa; i++) {
					memberlists[i] = FP_duleist.get(i).getWomenName();
				}
			}
			ListView memberlst = (ListView) dialog
					.findViewById(R.id.memberlist);
			ArrayAdapter adapter = new ArrayAdapter(context,
					android.R.layout.simple_list_item_1, android.R.id.text1,
					memberlists);
			memberlst.setAdapter(adapter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// Display the dialog
		dialog.show();
		// }

	}
}