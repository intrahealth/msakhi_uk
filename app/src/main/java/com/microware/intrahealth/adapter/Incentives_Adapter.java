package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tbl_Incentive;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Incentives_Adapter extends BaseAdapter {
	Context context;
	DataProvider dataProvider;
	ArrayList<tbl_Incentive> incentives;
	ArrayList<tblChild> child;
	String currentDate = "", pastDate = "";
	int AshaID = 0;
	int childhealthcount = 0;

	public Incentives_Adapter(Context context,
			ArrayList<tbl_Incentive> incentives, ArrayList<tblChild> child,
			String currentDate, String pastDate, int AshaID) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.incentives = incentives;
		this.currentDate = currentDate;
		this.pastDate = pastDate;
		this.AshaID = AshaID;
		this.child = child;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return incentives.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridview = null;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			// dataProvider = new DataProvider(context);
			gridview = new View(context);
			dataProvider = new DataProvider(context);
			gridview = layoutInflater.inflate(R.layout.incentive_adapter, null);
			TextView SlNo = (TextView) gridview.findViewById(R.id.SlNo);
			TextView Activities = (TextView) gridview
					.findViewById(R.id.Activities);
			TextView Count = (TextView) gridview.findViewById(R.id.Count);
			TextView Amount = (TextView) gridview.findViewById(R.id.Amount);
			dataProvider = new DataProvider(context);
			SlNo.setText(String.valueOf(position + 1));
			Activities.setText(String.valueOf(incentives.get(position)
					.getShortName()));
			String sql = "";
			int Item_id = incentives.get(position).getItem_id();
			int Counts = 0;
			try {
				dataProvider = new DataProvider(context);
				switch (Item_id) {
				case 1:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ pastDate + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 2:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ pastDate + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 3:
					sql = "select count(*) from tblANCVisit where HIVTest=1 and CheckUpVisitdate!='' and substr(CheckUpVisitdate,1,7)='"
							+ pastDate + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 4:
					sql = "select count(*) from tblPregnant_woman where JSYBenificiaryYN=1 and IsPregnant =1 and  PWRegistrationDate!='' and PWRegistrationDate < '"
							+ currentDate
							+ "' and PWRegistrationDate >= '"
							+ pastDate + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 5:
					sql = "select count(*) from tblPregnant_woman where JSYBenificiaryYN=1 and IsPregnant =1 and  PWRegistrationDate!='' and PWRegistrationDate < '"
							+ currentDate
							+ "' and PWRegistrationDate >='"
							+ pastDate + "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 6:
					for (int i = 0; i < child.size(); i++) {
						String chidguid = child.get(i).getChildGUID();
						String v_count = "Select count(*) from tblPNChomevisit_ANS where ChildGUID = '"
								+ chidguid
								+ "' and Q_0 < '"
								+ currentDate
								+ "' and Q_0 >='" + pastDate + "'";
						int totalvisitcount = dataProvider
								.getMaxRecord(v_count);
						if ((totalvisitcount == 7 && child.get(i)
								.getPlace_of_birth() == 1)
								|| (totalvisitcount == 6 && child.get(i)
										.getPlace_of_birth() != 1)) {
							if (childhealthcount == 0) {
								childhealthcount = 1;
							} else if (childhealthcount > 0) {
								childhealthcount = childhealthcount + 1;

							}
						}

					}
					Count.setText(String.valueOf(childhealthcount));
					Amount.setText(String.valueOf(Amounts(childhealthcount,
							Integer.valueOf(incentives.get(position)
									.getAmount()))));
					break;
				case 7:
					sql = "select count(*) from tblChild where Wt_of_child < 2.5 and Child_dob!='' and Child_dob< '"
							+ currentDate
							+ "' and Child_dob>= '"
							+ pastDate
							+ "'";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 10:
					// sql =
					// "select count(*) from tblChild where bcg!='' and bcg< '"
					// + currentDate
					// + "' and bcg>= '"
					// + pastDate
					// + "' and opv1!='' and opv1< '"
					// + currentDate
					// + "' and opv1>= '"
					// + pastDate
					// + "' and opv2!='' and opv2< '"
					// + currentDate
					// + "' and opv2>= '"
					// + pastDate
					// + "' and opv3!='' and opv3< '"
					// + currentDate
					// + "' and opv3>= '"
					// + pastDate
					// + "' and opv4!='' and opv4< '"
					// + currentDate
					// + "' and opv4>= '"
					// + pastDate
					// + "' and hepb1!='' and hepb1< '"
					// + currentDate
					// + "' and hepb1>= '"
					// + pastDate
					// + "' and hepb2!='' and hepb2< '"
					// + currentDate
					// + "' and hepb2>= '"
					// + pastDate
					// + "' and hepb3!='' and hepb3< '"
					// + currentDate
					// + "' and hepb3>= '"
					// + pastDate
					// + "' and hepb4!='' and hepb4< '"
					// + currentDate
					// + "' and hepb4>= '"
					// + pastDate
					// + "' and dpt1!='' and dpt1< '"
					// + currentDate
					// + "' and dpt1>= '"
					// + pastDate
					// + "' and dpt2!='' and dpt2< '"
					// + currentDate
					// + "' and dpt2>= '"
					// + pastDate
					// + "' and dpt3!='' and dpt3< '"
					// + currentDate
					// + "' and dpt3>= '"
					// + pastDate
					// + "' and Pentavalent1!='' and Pentavalent1< '"
					// + currentDate
					// + "' and Pentavalent1>= '"
					// + pastDate
					// + "' and Pentavalent2!='' and Pentavalent2< '"
					// + currentDate
					// + "' and Pentavalent2>= '"
					// + pastDate
					// + "' and Pentavalent3!='' and Pentavalent3< '"
					// + currentDate
					// + "' and Pentavalent3>= '"
					// + pastDate
					// + "' and measeals!='' and measeals< '"
					// + currentDate
					// + "' and measeals>= '"
					// + pastDate
					// + "' and vitaminA!='' and vitaminA< '"
					// + currentDate
					// + "' and vitaminA>= '"
					// + pastDate
					// + "' and JEVaccine1!='' and JEVaccine1< '"
					// + currentDate
					// + "' and JEVaccine1>= '"
					// + pastDate
					// + "'";
					sql = "select count(*) from tblChild where bcg!='' and  opv1!='' and  opv2!='' and  opv3!='' and  opv4!='' and   ((dpt2!='' and hepb2!=''  ) or Pentavalent2!='' ) and  ((dpt3!='' and hepb3!=''  ) or Pentavalent3!='' ) and hepb4!='' and  ((dpt1!='' and hepb1!=''  ) or Pentavalent1!='' ) and  measeals!='' and  vitaminA!='' and  JEVaccine1 not in ('',null) ;";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 11:
//					sql = "select count(*) from tblChild where DPTBooster!='' and DPTBooster< '"
//							+ currentDate
//							+ "' and DPTBooster>='"
//							+ pastDate
//							+ "' and DPTBoostertwo!='' and DPTBoostertwo< '"
//							+ currentDate
//							+ "' and DPTBoostertwo>='"
//							+ pastDate
//							+ "' and OPVBooster!='' and OPVBooster< '"
//							+ currentDate
//							+ "' and OPVBooster>= '"
//							+ pastDate
//							+ "' and MeaslesTwoDose!='' and MeaslesTwoDose<'"
//							+ currentDate
//							+ "' and MeaslesTwoDose>='"
//							+ pastDate
//							+ "' and JEVaccine2!='' and JEVaccine2< '"
//							+ currentDate
//							+ "' and JEVaccine2>= '"
//							+ pastDate
//							+ "' and VitaminAtwo!='' and VitaminAtwo< '"
//							+ currentDate
//							+ "' and VitaminAtwo>= '"
//							+ pastDate
//							+ "' and VitaminA3!='' and VitaminA3< '"
//							+ currentDate
//							+ "' and VitaminA3>= '"
//							+ pastDate
//							+ "' and VitaminA4!='' and VitaminA4< '"
//							+ currentDate
//							+ "' and VitaminA4>= '"
//							+ pastDate
//							+ "' and VitaminA5!='' and VitaminA5< '"
//							+ currentDate
//							+ "' and VitaminA5>= '"
//							+ pastDate
//							+ "' and VitaminA6!='' and VitaminA6< '"
//							+ currentDate
//							+ "' and VitaminA6>= '"
//							+ pastDate
//							+ "' and VitaminA7!='' and VitaminA7< '"
//							+ currentDate
//							+ "' and VitaminA7>= '"
//							+ pastDate
//							+ "' and VitaminA8!='' and VitaminA8< '"
//							+ currentDate
//							+ "' and VitaminA8>= '"
//							+ pastDate
//							+ "' and VitaminA9!='' and VitaminA9< '"
//							+ currentDate
//							+ "' and VitaminA9>= '"
//							+ pastDate
//							+ "' and TT2!='' and TT2< '"
//							+ currentDate
//							+ "' and TT2>= '" + pastDate + "'";
					sql="select count(*) from tblChild where DPTBooster not in ('',null)  and  DPTBoostertwo not in ('',null)  and OPVBooster not in ('',null)   and MeaslesTwoDose not in ('',null)  and  JEVaccine2 not in ('',null)   and VitaminAtwo not in ('',null)  and  VitaminA3 not in ('',null)  and  VitaminA4 not in ('',null)  and  VitaminA5 not in ('',null)  and  VitaminA6 not in ('',null)  and  VitaminA7 not in ('',null)  and  VitaminA8 not in ('',null)  and  VitaminA9 not in ('',null)  and  TT2 not in ('',null) ";
					Counts = dataProvider.getMaxRecord(sql);
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				case 26:
					sql = "select count(*) from tblVHNDDuelist where VHNDDate!='' and VHNDDate< '"
							+ currentDate
							+ "' and VHNDDate>='"
							+ pastDate
							+ "' ";
					Counts = dataProvider.getMaxRecord(sql);
					if (Counts > 0) {
						Counts = 1;
					}
					Count.setText(String.valueOf(Counts));
					Amount.setText(String.valueOf(Amounts(Counts, Integer
							.valueOf(incentives.get(position).getAmount()))));
					break;
				default:
					Count.setText("0");
					Amount.setText(String.valueOf(Amounts(0, Integer
							.valueOf(incentives.get(position).getAmount()))));

				}

			} catch (Exception exception) {
				Log.e("DataProvider",
						"Error in tbl_Incentive :: " + exception.getMessage());
			}

		} else {
			gridview = convertView;
		}

		return gridview;
	}

	public String Amounts(int count, int Aoumt) {
		String Amount = "";
		Amount = String.valueOf(Aoumt) + " * " + String.valueOf(count) + " = "
				+ String.valueOf(Aoumt * count);

		return Amount;
	}
}
