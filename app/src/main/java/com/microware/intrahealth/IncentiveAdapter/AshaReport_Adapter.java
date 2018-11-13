package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentive.AshaData;
import com.microware.intrahealth.IncentiveFragment.AshaReport;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by microware on 29-09-2017.
 */

public class AshaReport_Adapter extends RecyclerView.Adapter<AshaReport_Adapter.MyViewHolder> {
    Context context;
    DataProvider dataprovider;
    Global global;
    AshaReport fragment;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ArrayList<HashMap<String, String>> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_year, tv_month, tv_claim, tv_status, tv_status1, tv_amount;
        ImageView imageadd;
        LinearLayout llamount;

        public MyViewHolder(View view) {
            super(view);

            tv_year = (TextView) view.findViewById(R.id.tv_year);
            tv_month = (TextView) view.findViewById(R.id.tv_month);
            tv_claim = (TextView) view.findViewById(R.id.tv_claim);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_status1 = (TextView) view.findViewById(R.id.tv_status1);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            imageadd = (ImageView) view.findViewById(R.id.imageadd);
            llamount = (LinearLayout) view.findViewById(R.id.llamount);


        }

    }

    public AshaReport_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ashareport_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        holder.idimage.setBackgroundResource(Icon[position]);

        int status = Integer.valueOf(data.get(position).get("ANMStatus"));
        int mnth = Integer.valueOf(data.get(position).get("Month"));
        holder.tv_year.setText(data.get(position).get("Year"));
        if (mnth > 0&&mnth <= 12) {
            holder.tv_month.setText(month[mnth - 1]);
        }
        if (status == 0) {
            holder.tv_status.setText("Not Appproved");
            holder.tv_status1.setText("NA");
        }
        holder.tv_claim.setText(data.get(position).get("Claim"));

        holder.tv_amount.setText(data.get(position).get("AmtRecieved"));

        if (((position + 1) % 2) != 0) {
            holder.tv_year.setBackgroundResource(R.drawable.gridbox);
            holder.tv_month.setBackgroundResource(R.drawable.gridbox);
            holder.tv_claim.setBackgroundResource(R.drawable.gridbox);
            holder.tv_status.setBackgroundResource(R.drawable.gridbox);
            holder.tv_status1.setBackgroundResource(R.drawable.gridbox);
            holder.tv_amount.setBackgroundResource(R.drawable.gridbox);
            holder.llamount.setBackgroundResource(R.drawable.gridbox);
        } else {

            holder.tv_year.setBackgroundResource(R.drawable.textbox1);
            holder.tv_month.setBackgroundResource(R.drawable.textbox1);
            holder.tv_claim.setBackgroundResource(R.drawable.textbox1);
            holder.tv_status.setBackgroundResource(R.drawable.textbox1);
            holder.tv_status1.setBackgroundResource(R.drawable.textbox1);
            holder.tv_amount.setBackgroundResource(R.drawable.textbox1);
            holder.llamount.setBackgroundResource(R.drawable.textbox1);
        }
        holder. imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.setIncentiveSurveyGUID(data.get(position).get("IncentiveSurveyGUID"));
                global.setGlobalMonth(Integer.valueOf(data.get(position).get("Month")));
                global.setGlobalYear(Integer.valueOf(data.get(position).get("Year")));
                Intent in = new Intent(context, AshaData.class);
                context.startActivity(in);
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
