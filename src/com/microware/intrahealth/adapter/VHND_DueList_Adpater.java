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

public class VHND_DueList_Adpater extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<MstVHND_DueListItems> VHND_Duelist;
	ArrayList<tbl_pregnantwomen> Pregnant_woman;
	ArrayList<tblChild> child;
	// String Date = "";
	int AshaID = 0;
	Global global;
	String Date;
	int Village_ID = 0;

	// String sql44 = "", sql55 = "";

	public VHND_DueList_Adpater(Context context,
			ArrayList<MstVHND_DueListItems> VHND_Duelist, int AshaID,
			String Date, int Village_ID) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.VHND_Duelist = VHND_Duelist;
		this.AshaID = AshaID;
		this.Date = Date;
		this.Village_ID = Village_ID;

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
			String sql1 = "", sql2 = "", sql3 = "", sql4 = "";
			final int ID = VHND_Duelist.get(position).getID();
			int Count1 = 0, Count2 = 0;
			try {
				if (ID == 1 || ID == 2 || ID == 3 || ID == 4) {
					sql1 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
							+ Date
							+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and b.visit_no='"
							+ ID + "' order by b.pwguid";
					sql2 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
							+ Date
							+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate !=null and ispregnant=1 and b.visit_no='"
							+ ID + "' order by b.pwguid";
				} else {
					switch (ID) {
					case 5:
						sql1 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTfirstDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate='')";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
								+ Date
								+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate !=null and ispregnant=1 and b.visit_no='"
								+ ID + "' order by b.pwguid";// sql2 for 5 and 6
																// ID need to
																// check
						break;
					case 6:
						sql1 = "tblPregnant_woman a where pwguid in (SELECT distinct PWGUID from tblANCVisit where TTfirstDoseDate!='' and a.lmpdate!='' and a.lmpdate is not null and TT1TT2last2yr!=1 and TTBoosterDate='')";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('"
								+ Date
								+ "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate !=null and ispregnant=1 and b.visit_no='"
								+ ID + "' order by b.pwguid";
					case 7:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.IFARecieved=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.IFARecieved=0";
						break;
					case 8:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.CalciumReceived=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.CalciumReceived=0";
						break;
					case 9:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BirthWeight=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BirthWeight=0";
						break;
					case 10:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BP=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.BP=0";
						break;
					case 11:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.Hemoglobin=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.Hemoglobin=0";
						break;
					case 12:
						sql1 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.UrineTest=0";
						sql2 = "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and a.ispregnant=1 and b.UrineTest=0";
						break;
					case 13:
						// sql1 =
						// "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and ispregnant=1 and UrineTest=0";
						// sql2 =
						// "tblPregnant_woman a inner join tblANCVisit b on a.pwguid=b.pwguid where checkupvisitdate in('',null) and ispregnant=1 and UrineTest=0";
						// break;
					case 14:
						sql1 = "tblCHild where bcg='' or bcg==null";
						sql2 = "tblCHild where bcg='' or bcg==null";
						break;
					case 15:
						sql1 = "tblCHild where (opv1='' or opv1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						sql2 = "tblCHild where (opv1='' or opv1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						break;
					case 16:
						sql1 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						sql2 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						break;
					case 17:
						sql1 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==nul or opv3='' or opv3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						sql2 = "tblCHild where (opv2='' or opv2==null or opv1='' or opv1==null or opv3='' or opv3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						break;
					case 18:
						sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						sql2 = "tblCHild where (Pentavalent1='' or Pentavalent1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						break;
					case 19:
						sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						sql2 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						break;
					case 20:
						sql1 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null or Pentavalent3='' or Pentavalent3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						sql2 = "tblCHild where (Pentavalent1='' or Pentavalent1==null or Pentavalent2='' or Pentavalent2==null or Pentavalent3='' or Pentavalent3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						break;
					case 21:
						sql1 = "tblCHild where (hepb1='' or hepb1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						sql2 = "tblCHild where (hepb1='' or hepb1==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=42";
						break;
					case 22:
						sql1 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						sql2 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=70";
						break;
					case 23:
						sql1 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						sql2 = "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						break;
					case 24:
						// sql1 =
						// "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						// sql2 =
						// "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						break;
					case 25:
						// sql1 =
						// "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						// sql2 =
						// "tblCHild where (hepb1='' or hepb1==null or hepb2='' or hepb2==null or hepb3='' or hepb3==null) and cast(round(julianday(Date)-julianday(Child_dob))as int)>=98";
						break;
					case 26:
						sql1 = "tblCHild where (measeals='' or measeals==null) and cast(round(julianday(Date)-julianday(Child_dob))as int) between 270 and 365";
						sql2 = "tblCHild where (measeals='' or measeals==null) and cast(round(julianday(Date)-julianday(Child_dob))as int) between 270 and 365";
						break;
					case 27:
						sql1 = "tblCHild where (vitaminA='' or vitaminA==null) and cast(round(julianday(Date)-julianday(Child_dob))as int) between 270 and 365";
						sql2 = "tblCHild where (vitaminA='' or vitaminA==null) and cast(round(julianday(Date)-julianday(Child_dob))as int) between 270 and 365";
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
				} else if (ID > 13) {
					sql3 = "select count(distinct ChildGUID) from " + sql1;
					sql4 = "select count(distinct ChildGUID) from " + sql2;
				}
				Count1 = dataProvider.getMaxRecordnew(sql3);
				Count2 = dataProvider.getMaxRecordnew(sql4);
				final String sql44, sql55, sql66, sql77;
				// if (ID <= 13) {
				sql44 = "Select PWName from tblPregnant_woman where pwguid in (select distinct a.pwguid from "
						+ sql1 + ")";
				sql55 = "Select PWName from tblPregnant_woman where pwguid in (select distinct a.pwguid from "
						+ sql2 + ")";
				// }

				sql66 = "select ChildGUID from  " + sql1 + "";
				sql77 = "Select ChildGUID from " + sql2 + "";
				Noduetoreceive_tx.setText(String.valueOf(Count1));
				NoduetoreceiveVHND_tx.setText(String.valueOf(Count2));
				Noleftout_tx.setText(String.valueOf(Count1 - Count2));
				Noduetoreceive_tx
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (ID <= 13) {
									memberlistshow(sql44, ID);
								} else if (ID > 13) {
									memberlistshow(sql66, ID);
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
								} else if (ID > 13) {
									memberlistshow(sql77, ID);
								}
							}
						});

			} catch (Exception exception) {
				Log.e("DataProvider",
						"Error in tbl_Incentive :: " + exception.getMessage());
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
			} else if (ID > 13) {
				child = dataProvider.getChildGUID(sql, 0);
				int aa = child.size();
				memberlists = new String[aa];
				for (int i = 0; i < aa; i++) {
					memberlists[i] = child.get(i).getChildGUID();
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