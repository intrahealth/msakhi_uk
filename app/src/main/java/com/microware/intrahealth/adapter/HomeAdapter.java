package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Delivery;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HomeVisit;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tbl_pregnantwomen;

public class HomeAdapter extends BaseAdapter {

    ArrayList<tbl_pregnantwomen> pregnant;
    Context context;
    Global global;
    DataProvider dataProvider;
    ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
    int iflag = 0;
    String sDueVisits = "";
    String LMPDate = "";
    String RegistrationDate = "";
    String CurrentDate = "";

    public HomeAdapter(Context context, ArrayList<tbl_pregnantwomen> pregnant) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.pregnant = pregnant;
        // this.iflag = iflag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pregnant.size();
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
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.homegridadapter, null);
        } else {
            gridview = convertView;
        }

        dataProvider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        TextView tvwname = (TextView) gridview.findViewById(R.id.tvwname);
        ImageView btnhousehold = (ImageView) gridview
                .findViewById(R.id.btnhousehold);
        btnhousehold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID(pregnant.get(position)
                        .getHHGUID());
                i.putExtra("flag", 3);
                context.startActivity(i);

            }
        });
        /*
         * TextView tvlastvisit = (TextView) gridview
		 * .findViewById(R.id.tvlastvisit); ImageView ivaddvisit = (ImageView)
		 * gridview .findViewById(R.id.imageancaddvisit); // ImageView ivdelete
		 * = (ImageView) gridview .findViewById(R.id.imageancdelete);
		 */
        final TextView tvGridhide = (TextView) gridview
                .findViewById(R.id.tvGridhide);
        final GridView gridVisits = (GridView) gridview
                .findViewById(R.id.gridVisits);

        // tvwname.setEnabled(false);
        String name = "", husbandname = "";

        String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + pregnant.get(position).getHHFamilyMemberGUID() + "'";
        String sql2 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID =(select Spouse from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + pregnant.get(position).getHHFamilyMemberGUID() + "')";
        name = Validate.returnStringValue(dataProvider.getRecord(sql1));
        husbandname = Validate.returnStringValue(dataProvider.getRecord(sql2));

        if (husbandname.length() > 0) {
            tvwname.setText(name + " " + "w/o" + " " + husbandname);
        } else if (pregnant.get(position).getPWName().length() > 0) {
            tvwname.setText(name);
        }
        tvwname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((HomeVisit) context).openExpendableGridView(position);
                String anc_guid = pregnant.get(position).getPWGUID();
                // global.setsGlobalANCGUID(pregnant.get(position).getPW);
                if (Integer.valueOf(tvGridhide.getText().toString()) == 0) {
                    gridVisits.setVisibility(View.VISIBLE);
                    tvGridhide.setText("1");
                    fillvisitdata(gridVisits, anc_guid);
                } else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
                    gridVisits.setVisibility(View.GONE);
                    tvGridhide.setText("0");
                }

            }
        });
        String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
                + pregnant.get(position).getHHFamilyMemberGUID() + "'";
        String stat = dataProvider.getRecord(status);
        if (Validate.returnIntegerValue(stat) == 3) {
            tvwname.setBackgroundResource(R.drawable.yellowsheet);

        } else {
            tvwname.setBackgroundResource(R.drawable.whitesheet);

        }


		/*
         * if (iflag == 1) { ivaddvisit.setVisibility(View.GONE); }
		 */
        return gridview;
    }


    public void fillvisitdata(GridView gridVisits, String sPWGUID) {
        VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID, "", 3);
        if (VisitANC != null && VisitANC.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridVisits
                    .getLayoutParams();
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * VisitANC.size();
            params.height = gridHeight1;
            gridVisits.setLayoutParams(params);
            gridVisits.setAdapter(new HomeVisitAdapter(context, VisitANC));

        }
    }
}
