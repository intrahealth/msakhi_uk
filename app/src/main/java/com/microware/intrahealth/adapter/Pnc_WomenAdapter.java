package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.PncWomenList;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblChild;

@SuppressLint("InflateParams")
public class Pnc_WomenAdapter extends BaseAdapter {

    ArrayList<tblChild> Child;
    ArrayList<Tbl_HHFamilyMember> pregnant;
    Context context;
    Global global;
    DataProvider dataProvider;
    ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
    int iflag = 0;
    String sDueVisits = "";
    String LMPDate = "";
    String RegistrationDate = "";
    String CurrentDate = "";

    public Pnc_WomenAdapter(Context context, ArrayList<tblChild> Child) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.Child = Child;
        // this.iflag = iflag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Child.size();
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

    @SuppressWarnings("unused")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View gridview = null;
        try {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                gridview = new View(context);
                gridview = layoutInflater
                        .inflate(R.layout.pncgridadapter, null);
            } else {
                gridview = convertView;
            }

            dataProvider = new DataProvider(context);
            global = (Global) context.getApplicationContext();

            TextView tvwname = (TextView) gridview.findViewById(R.id.tvwname);

            final TextView tvGridhide = (TextView) gridview
                    .findViewById(R.id.tvGridhide);
            final GridView gridVisits = (GridView) gridview
                    .findViewById(R.id.gridVisits);


            ImageView btnhousehold = (ImageView) gridview
                    .findViewById(R.id.btnhousehold);

            btnhousehold.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(v.getContext(),
                            Intrahealth_Tab_Activity.class);
                    global.setGlobalHHSurveyGUID(Child.get(position)
                            .getHHGUID());
                    i.putExtra("flag", 2);
                    context.startActivity(i);

                }
            });
            tvwname.setTextColor(context.getResources().getColor(R.color.Blue));
            String mothername = "";
            if (Child.get(position).getPw_GUID() != null
                    && Child.get(position).getPw_GUID().length() > 0) {
                pregnant = dataProvider.getAllmalename(Child.get(position)
                        .getHHFamilyMemberGUID());

                if (pregnant.size() > 0) {
                    mothername = pregnant.get(0)
                            .getFamilyMemberName();
                }
                String gender="";
                if( Child.get(position).getGender()==1){
                    gender=context.getResources().getString(R.string.G);
                } else if( Child.get(position).getGender()==2){
                    gender=context.getResources().getString(R.string.B);
                }
                tvwname.setText(mothername
                        + " / "
                        + Child.get(position).getChild_name() + " / "
                        + Validate.changeDateFormat(Child.get(position).getChild_dob() )+ " / "
                        + gender);
                // tvChild.setBackgroundColor(Color.parseColor("#55FF0000"));
                // tvChild.setTextColor(Color.RED);
            } else {
                pregnant = dataProvider.getAllmalename(Child.get(position)
                        .getHHFamilyMemberGUID());
                if (pregnant.size() > 0) {
                    mothername = pregnant.get(0)
                            .getFamilyMemberName();
                }
                String gender="";
                if( Child.get(position).getGender()==1){
                    gender=context.getResources().getString(R.string.G);
                } else if( Child.get(position).getGender()==2){
                    gender=context.getResources().getString(R.string.B);
                }
                tvwname.setText(mothername
                        + " / "
                        + Child.get(position).getChild_name() + " / "
                        + Validate.changeDateFormat(Child.get(position).getChild_dob() )+ " / "
                        + gender);
                // tvChild.setTextColor(Color.BLUE);
                // tvChild.setBackgroundColor(color.holo_orange_light);
            }
            tvwname.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    ((PncWomenList) context).openExpendableGridView(position);
                    String anc_guid = Child.get(position).getPw_GUID();
                    String childguid = Child.get(position).getChildGUID();
                    // global.setsGlobalANCGUID(pregnant.get(position).getPW);
                    if (Integer.valueOf(tvGridhide.getText().toString()) == 0) {
                        gridVisits.setVisibility(View.VISIBLE);
                        tvGridhide.setText("1");
                        fillvisitdata(gridVisits, anc_guid, childguid);
                    } else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
                        gridVisits.setVisibility(View.GONE);
                        tvGridhide.setText("0");
                    }

                }
            });

			/*
             * if (iflag == 1) { ivaddvisit.setVisibility(View.GONE); }
			 */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridview;
    }

    public void fillvisitdata(GridView gridVisits, String sPWGUID,
                              String childguid) {
        // VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID,"",3);

        android.view.ViewGroup.LayoutParams params = gridVisits
                .getLayoutParams();
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                r.getDisplayMetrics());
        int hi = Math.round(px);
        int gridHeight1 = 0;
        gridHeight1 = hi * 7;
        params.height = gridHeight1;
        gridVisits.setLayoutParams(params);
        gridVisits
                .setAdapter(new Pnc_VisitAdapter(context, sPWGUID, childguid));

    }
}