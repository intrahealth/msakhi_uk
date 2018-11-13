package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentive.AnmReport_BB_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Microware on 11/17/2017.
 */

public class AnmReport_AA_Adapter extends RecyclerView.Adapter<AnmReport_AA_Adapter.MyViewHolder>{

    Context context;
    DataProvider dataprovider;
    Global global;
    String[] temp;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ArrayList<HashMap<String, String>> data;
    public AnmReport_AA_Adapter(Context context, ArrayList<HashMap<String, String>> data)
    {
        this.context = context;
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anmreport_aa_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        int mnth = Integer.valueOf(data.get(position).get("Month"));
        holder.tv_year.setText(data.get(position).get("Year"));
        if (mnth > 0&&mnth <= 12) {
            holder.tv_month.setText(month[mnth - 1]);
        }
        holder.tv_totalasha.setText(data.get(position).get("AshaCount"));
        holder.tv_Claimed.setText(data.get(position).get("Claim"));
        holder.tv_approved.setText(data.get(position).get("NotApproved"));

        if (((position + 1)% 2) != 0) {
            holder.tv_year.setBackgroundResource(R.drawable.gridbox);
            holder.tv_month.setBackgroundResource(R.drawable.gridbox);
            holder.tv_totalasha.setBackgroundResource(R.drawable.gridbox);
            holder.tv_Claimed.setBackgroundResource(R.drawable.gridbox);
            holder.tv_approved.setBackgroundResource(R.drawable.gridbox);

        } else {

            holder.tv_year.setBackgroundResource(R.drawable.textbox1);
            holder.tv_month.setBackgroundResource(R.drawable.textbox1);
            holder.tv_totalasha.setBackgroundResource(R.drawable.textbox1);
            holder.tv_Claimed.setBackgroundResource(R.drawable.textbox1);
            holder.tv_approved.setBackgroundResource(R.drawable.textbox1);

        }
        holder.imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                global.setGlobalMonth(Integer.valueOf(data.get(position).get("Month")));
                global.setGlobalYear(Integer.valueOf(data.get(position).get("Year")));
                Intent in = new Intent(context, AnmReport_BB_Activity.class);
                context.startActivity(in);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_year, tv_month,tv_totalasha,tv_Claimed,tv_approved;
        ImageView imageadd;
        TableRow tbl_image;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            tv_month = (TextView) itemView.findViewById(R.id.tv_month);
            tv_totalasha = (TextView) itemView.findViewById(R.id.tv_totalasha);
            tv_Claimed = (TextView) itemView.findViewById(R.id.tv_Claimed);
            tv_approved = (TextView) itemView.findViewById(R.id.tv_approved);
            imageadd = (ImageView) itemView.findViewById(R.id.imageadd);
            tbl_image = (TableRow) itemView.findViewById(R.id.tbl_image);

        }
    }
}
