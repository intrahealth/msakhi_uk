package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.Global;
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
    Global global;
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
            global = (Global) context.getApplicationContext();
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
                        sql = "select count(*) from tblPregnant_woman where  AshaID='" + global.getsGlobalAshaCode() + "' and  JSYBenificiaryYN=1 and IsPregnant =1 and  PWRegistrationDate!='' and PWRegistrationDate < '"
                                + currentDate
                                + "' and PWRegistrationDate >= '"
                                + pastDate + "'";
                        Counts = dataProvider.getMaxRecord(sql);
                        Count.setText(String.valueOf(Counts));
                        Amount.setText(String.valueOf(Amounts(Counts, Integer
                                .valueOf(incentives.get(position).getAmount()))));
                        break;
                    case 5:
                        sql = "select count(*) from tblPregnant_woman where AshaID='" + global.getsGlobalAshaCode() + "' and  JSYBenificiaryYN=1 and IsPregnant =1 and  PWRegistrationDate!='' and PWRegistrationDate < '"
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
                            String v_count = "Select count(*) from tblPNChomevisit_ANS where AshaID='" + global.getsGlobalAshaCode() + "' and  ChildGUID = '"
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
                        sql = "select count(*) from tblChild where AshaID='" + global.getsGlobalAshaCode() + "' and  Wt_of_child < 2.5 and Child_dob!='' and Child_dob< '"
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

                        sql = "select count(*) from tblChild where AshaID='" + global.getsGlobalAshaCode() + "' and  bcg!='' and  opv1!='' and  opv2!='' and  opv3!='' and  opv4!='' and   ((dpt2!='' and hepb2!=''  ) or Pentavalent2!='' ) and  ((dpt3!='' and hepb3!=''  ) or Pentavalent3!='' ) and hepb4!='' and  ((dpt1!='' and hepb1!=''  ) or Pentavalent1!='' ) and  measeals!='' and  vitaminA!='' and  JEVaccine1 not in ('',null) ;";
                        Counts = dataProvider.getMaxRecord(sql);
                        Count.setText(String.valueOf(Counts));
                        Amount.setText(String.valueOf(Amounts(Counts, Integer
                                .valueOf(incentives.get(position).getAmount()))));
                        break;
                    case 11:
//
                        sql = "select count(*) from tblChild where AshaID='" + global.getsGlobalAshaCode() + "' and  DPTBooster not in ('',null)  and  DPTBoostertwo not in ('',null)  and OPVBooster not in ('',null)   and MeaslesTwoDose not in ('',null)  and  JEVaccine2 not in ('',null)   and VitaminAtwo not in ('',null)  and  VitaminA3 not in ('',null)  and  VitaminA4 not in ('',null)  and  VitaminA5 not in ('',null)  and  VitaminA6 not in ('',null)  and  VitaminA7 not in ('',null)  and  VitaminA8 not in ('',null)  and  VitaminA9 not in ('',null)  and  TT2 not in ('',null) ";
                        Counts = dataProvider.getMaxRecord(sql);
                        Count.setText(String.valueOf(Counts));
                        Amount.setText(String.valueOf(Amounts(Counts, Integer
                                .valueOf(incentives.get(position).getAmount()))));
                        break;
                    case 26:
                        sql = "select count(*) from tblVHNDDuelist where AshaID='" + global.getsGlobalAshaCode() + "' and  VHNDDate!='' and VHNDDate< '"
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
