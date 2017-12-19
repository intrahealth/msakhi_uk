package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import com.microware.intrahealth.FP_AA;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentives;
import com.microware.intrahealth.MCH_Dashboard;
import com.microware.intrahealth.NCD_AA;
import com.microware.intrahealth.NCD_Report;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Survey_Activity;
import com.microware.intrahealth.VHND_DateRecord;
import com.microware.intrahealth.VHND_Record;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class AshaListFormAnm_Adapter extends BaseAdapter {

    Context context;
    ArrayList<MstASHA> ashaname = new ArrayList<MstASHA>();
    DataProvider dataProvider;
    Global global;
    int flag = 0;

    public AshaListFormAnm_Adapter(Context context,
                                   ArrayList<MstASHA> ashaname, int flag) {

        this.context = context;
        this.ashaname = ashaname;
        this.flag = flag;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ashaname.size();
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

    @SuppressWarnings("static-access")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.ashalistform_adapter,
                    null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);

        String sql = "select count(*) from tblPregnant_woman where AshaID="
                + ashaname.get(position).getASHACode() + "";
        int iAshaCount = dataProvider.getMaxRecord(sql);

        TextView txtAshaName = (TextView) gridview
                .findViewById(R.id.txtAshaName);
        TextView tvNumberOfPregWomen = (TextView) gridview
                .findViewById(R.id.tvNumberOfPregWomen);

        txtAshaName.setText(String
                .valueOf(ashaname.get(position).getASHAName()));
        tvNumberOfPregWomen.setText(String.valueOf(iAshaCount));

        txtAshaName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                global.setsGlobalAshaCode(String.valueOf(ashaname.get(position)
                        .getASHAID()));
                global.setsGlobalAshaName(ashaname.get(position).getASHAName());

                if (flag == 1) {
                    Intent i = new Intent(context, Survey_Activity.class);
                    context.startActivity(i);
                } else if (flag == 2) {
                    Intent i = new Intent(context, MCH_Dashboard.class);
                    context.startActivity(i);
                } else if (flag == 3) {
                    Intent i = new Intent(context, FP_AA.class);
                    context.startActivity(i);
                } else if (flag == 4) {
                    Intent i = new Intent(context, VHND_DateRecord.class);
                    context.startActivity(i);
                } else if (flag == 5) {
                    Intent i = new Intent(context, Incentives.class);
                    context.startActivity(i);
                } else if (flag == 6) {
                    Intent i = new Intent(context, NCD_AA.class);
                    context.startActivity(i);
                } else if (flag == 7) {
                    Intent i = new Intent(context, NCD_Report.class);
                    context.startActivity(i);
                }

            }
        });

        return gridview;
    }

}
