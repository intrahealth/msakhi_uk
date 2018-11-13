package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentive.AnmReport_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Microware on 11/18/2017.
 */

public class AnmReport_BB_Adapter extends RecyclerView.Adapter<AnmReport_BB_Adapter.MyViewHolder> {

    Context context;
    DataProvider dataprovider;
    Global global;
    String[] temp;
    ArrayList<HashMap<String, String>> data;

    public AnmReport_BB_Adapter(Context context, ArrayList<HashMap<String, String>> data) {

        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anmreport_bb_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String sqlApproved = "select  sum(ActivityTotal) from tblashaincentiveDetail  where  ANMStatus=1 and AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + data.get(position).get("AshaId") + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' ";
        String Approved = dataprovider.getRecord(sqlApproved);
        int amount = Integer.valueOf(data.get(position).get("Claim")) - Integer.valueOf(data.get(position).get("NotApproved"));
        int status = Integer.valueOf(data.get(position).get("status"));
        holder.tv_ASHA.setText(data.get(position).get("name"));
        holder.tv_Claime.setText(data.get(position).get("Claim"));
        if (status == 1) {
            holder.tv_Approval.setText(Approved);
        } else {
            holder.tv_Approval.setText("0");
        }

        if (((position + 1) % 2) != 0) {
            holder.tv_ASHA.setBackgroundResource(R.drawable.gridbox);
            holder.tv_Claime.setBackgroundResource(R.drawable.gridbox);
            holder.tv_Approval.setBackgroundResource(R.drawable.gridbox);

        } else {
            holder.tv_ASHA.setBackgroundResource(R.drawable.textbox1);
            holder.tv_Claime.setBackgroundResource(R.drawable.textbox1);
            holder.tv_Approval.setBackgroundResource(R.drawable.textbox1);


        }
        holder.imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                global.setGlobalMonth(Integer.valueOf(data.get(position).get("Month")));
                global.setsGlobalAshaName(data.get(position).get("name"));
                global.setsGlobalAshaCode(data.get(position).get("AshaId"));
                Intent in = new Intent(context, AnmReport_Activity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView tv_ASHA, tv_Claime, tv_Approval;
        ImageView imageadd;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            tv_ASHA = (TextView) itemView.findViewById(R.id.tv_ASHA);

            tv_Claime = (TextView) itemView.findViewById(R.id.tv_Claime);
            tv_Approval = (TextView) itemView.findViewById(R.id.tv_Approval);
            imageadd = (ImageView) itemView.findViewById(R.id.imageadd);

        }
    }
}
