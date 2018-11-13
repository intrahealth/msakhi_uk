package com.microware.intrahealth.IncentiveAdapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveFragment.AshaReport;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by microware on 29-09-2017.
 */

public class AshaData_Adapter extends RecyclerView.Adapter<AshaData_Adapter.MyViewHolder> {
    Context context;
    DataProvider dataprovider;
    Global global;
    AshaReport fragment;

    ArrayList<HashMap<String, String>> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_activitycode, tv_claim, tv_anm, tv_bcpm, tv_bam;

        public MyViewHolder(View view) {
            super(view);

            tv_activitycode = (TextView) view.findViewById(R.id.tv_activitycode);
            tv_claim = (TextView) view.findViewById(R.id.tv_claim);
            tv_anm = (TextView) view.findViewById(R.id.tv_anm);
            tv_bcpm = (TextView) view.findViewById(R.id.tv_bcpm);
            tv_bam = (TextView) view.findViewById(R.id.tv_bam);

        }

    }

    public AshaData_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ashadata_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        holder.idimage.setBackgroundResource(Icon[position]);


        int ANMStatus = Integer.valueOf(data.get(position).get("ANMStatus"));
        int BCPMStatus = Integer.valueOf(data.get(position).get("BCPMStatus"));

        holder.tv_activitycode.setText(data.get(position).get("ActivitySrno"));

        holder.tv_claim.setText(data.get(position).get("ActivityTotal"));


        if (ANMStatus == 1) {
            holder.tv_anm.setText("A");
            holder.tv_anm.setBackgroundColor(context.getResources().getColor(R.color.Green));
        } else if (ANMStatus == 2) {
            holder.tv_anm.setText("NA");
            holder.tv_anm.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
            holder.tv_claim.setTextColor(context.getResources().getColor(R.color.pastelRed));

        }
        if (BCPMStatus == 1) {
            holder.tv_bcpm.setText("A");
            holder.tv_bcpm.setBackgroundColor(context.getResources().getColor(R.color.Green));
        } else if (BCPMStatus == 2) {
            holder.tv_bcpm.setText("NA");
            holder.tv_bcpm.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
        }
        holder.tv_activitycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "select Activity from mstashaactivity where  LangaugeID=1 and AreaType='R' and Qsrno='" + holder.tv_activitycode.getText().toString() + "' ";
                String sqlactivity = dataprovider.getRecord(sql);
                CustomAlert(sqlactivity);
            }
        });
        holder.tv_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_anm.getText().toString().equalsIgnoreCase("NA")) {
                    String sql = "select RemarkAMStatus from tblashaincentivedetail where   IncentiveGUID='" + data.get(position).get("IncentiveGUID") + "' ";
                    String sqlactivity = dataprovider.getRecord(sql);
                    CustomAlert(sqlactivity);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void CustomAlert(String msg) {
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

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }
}
