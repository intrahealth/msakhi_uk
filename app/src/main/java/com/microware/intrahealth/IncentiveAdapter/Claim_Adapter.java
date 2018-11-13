package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by microware on 29-09-2017.
 */

public class Claim_Adapter extends RecyclerView.Adapter<Claim_Adapter.MyViewHolder> {
    Context context;
    DataProvider dataprovider;
    Global global;


    ArrayList<HashMap<String, String>> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_year, tv_claimed, tv_amntrcvd, tv_notapproved, tv_pending;
        LinearLayout llpending;


        public MyViewHolder(View view) {
            super(view);

            tv_year = (TextView) view.findViewById(R.id.tv_year);
            tv_claimed = (TextView) view.findViewById(R.id.tv_claimed);
            tv_amntrcvd = (TextView) view.findViewById(R.id.tv_amntrcvd);
            tv_notapproved = (TextView) view.findViewById(R.id.tv_notapproved);
            tv_pending = (TextView) view.findViewById(R.id.tv_pending);
            llpending = (LinearLayout) view.findViewById(R.id.llpending);


        }

    }

    public Claim_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.claim_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_year.setText(data.get(position).get("Year"));
        holder.tv_claimed.setText(data.get(position).get("Claim"));
        holder.tv_amntrcvd.setText(data.get(position).get("AmtRecieved"));
        holder.tv_notapproved.setText(data.get(position).get("NotApproved"));
        int  status = Integer.valueOf(data.get(position).get("ANMStatus"));
        if (status == 1) {
            String sqlApproved = "select  sum(ActivityTotal) from tblashaincentiveDetail  where  ANMStatus=1 and AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + data.get(position).get("Year") + "' and month='" + data.get(position).get("Month") + "' and AshaID='" + global.getsGlobalAshaCode() + "'  and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' ";
            String Approved = dataprovider.getRecord(sqlApproved);
            holder.tv_pending.setText(Approved);
        } else {
            holder.tv_pending.setText("0");
        }
       // holder.tv_pending.setText("" + aa);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
