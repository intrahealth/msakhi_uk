package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentive.AnmashaReport_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/18/2017.
 */

public class AnmReport_Claim_AA_Adapter extends RecyclerView.Adapter<AnmReport_Claim_AA_Adapter.MyViewHolde>{

    Context context;
    DataProvider dataprovider;
    Global global;
    String[] temp;
    ArrayList<HashMap<String, String>> data;
public AnmReport_Claim_AA_Adapter(Context context, ArrayList<HashMap<String, String>> data)
{
    this.context = context;
    this.data = data;
}
    @Override
    public MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anmreport_aa_claim_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolde(itemView);
}

    @Override
    public void onBindViewHolder(MyViewHolde holder, final int position) {

        holder.tv_Year.setText(data.get(position).get("Year"));

        holder.tv_Claimed.setText(data.get(position).get("Claim"));
        holder.tv_Amountreceived.setText(data.get(position).get("AmtRecieved"));
        holder.tv_Notapproved.setText(data.get(position).get("NotApproved"));
        holder.tv_Pending.setText("0");
        holder.tv_Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global.setGlobalYear(Integer.valueOf(data.get(position).get("Year")));
                Intent in = new Intent(context, AnmashaReport_Activity.class);
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolde extends RecyclerView.ViewHolder{
        TextView tv_Year, tv_Claimed,tv_Amountreceived,tv_Notapproved,tv_Pending;
        public MyViewHolde(View itemView) {
            super(itemView);
            tv_Year = (TextView) itemView.findViewById(R.id.tv_Year);
            tv_Claimed = (TextView) itemView.findViewById(R.id.tv_Claimed);
            tv_Amountreceived = (TextView) itemView.findViewById(R.id.tv_Amountreceived);
            tv_Notapproved = (TextView) itemView.findViewById(R.id.tv_Notapproved);
            tv_Pending = (TextView) itemView.findViewById(R.id.tv_Pending);
        }
    }
}
