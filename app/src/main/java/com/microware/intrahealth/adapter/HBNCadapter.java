package com.microware.intrahealth.adapter;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.PncWomenList;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

public class HBNCadapter extends BaseAdapter {
    Context context;
    ArrayList<tbl_pregnantwomen> hhsurvey;
    Global global;
    DataProvider dataProvider;
    int flag = 0;
    TableRow GridRow;

    public HBNCadapter(Context context, ArrayList<tbl_pregnantwomen> hhsurvey,
                       int flag) {
        this.hhsurvey = hhsurvey;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hhsurvey.size();
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

    @SuppressWarnings({"static-access", "unused"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater
                    .inflate(R.layout.anc_duelistadapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        TextView tv2 = (TextView) gridview.findViewById(R.id.tv2);
        TextView tv1 = (TextView) gridview.findViewById(R.id.tv1);
        TextView tv3 = (TextView) gridview.findViewById(R.id.tv3);
        TextView tv4 = (TextView) gridview.findViewById(R.id.tv4);
        if (flag == 4 || flag == 5) {
            tv4.setVisibility(View.GONE);
        }
        int visit = 1;
        tv1.setText(String.valueOf(hhsurvey.get(position).getHusbandName()));
        tv2.setText(hhsurvey.get(position).getPWName());

        tv3.setText(String.valueOf(hhsurvey.get(position).getPWAgeYears()));
        if (hhsurvey.get(position).getPWAgeYears() == 1) {
            visit = 1;
        } else if (hhsurvey.get(position).getPWAgeYears() == 3) {
            visit = 2;

        } else if (hhsurvey.get(position).getPWAgeYears() == 7) {
            visit = 3;

        } else if (hhsurvey.get(position).getPWAgeYears() == 14) {
            visit = 4;

        } else if (hhsurvey.get(position).getPWAgeYears() == 21) {
            visit = 5;

        } else if (hhsurvey.get(position).getPWAgeYears() == 28) {
            visit = 6;

        } else if (hhsurvey.get(position).getPWAgeYears() == 42) {
            visit = 7;

        } else {
            visit = 0;
        }
        tv4.setText("" + visit);
        if (position % 2 == 0) {
            GridRow.setBackgroundColor(context.getResources().getColor(
                    R.color.lightgray));
        }
        GridRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 6) {
                    global.setsGlobalChildGUID(hhsurvey
                            .get(position).getPWGUID());
                    Intent i = new Intent(context, PncWomenList.class);
                    i.putExtra("flag", 1);
                    context.startActivity(i);
                }
            }
        });

        return gridview;
    }

    public void CustomAlerts(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                // finish();

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

}
