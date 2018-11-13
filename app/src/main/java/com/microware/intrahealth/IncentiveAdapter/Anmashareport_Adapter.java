package com.microware.intrahealth.IncentiveAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by microware on 29-09-2017.
 */

public class Anmashareport_Adapter extends RecyclerView.Adapter<Anmashareport_Adapter.MyViewHolder> {
    Context context;
    DataProvider dataprovider;
    Global global;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    ArrayList<HashMap<String, String>> data;

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv_year, tv_month,tv_claimed, tv_amntrcvd, tv_notapproved, tv_pending;


        public MyViewHolder(View view) {
            super(view);

            tv_month = (TextView) view.findViewById(R.id.tv_month);
            tv_year = (TextView) view.findViewById(R.id.tv_year);
            tv_claimed = (TextView) view.findViewById(R.id.tv_claimed);
            tv_amntrcvd = (TextView) view.findViewById(R.id.tv_amntrcvd);
            tv_notapproved = (TextView) view.findViewById(R.id.tv_notapproved);
            tv_pending = (TextView) view.findViewById(R.id.tv_pending);


        }

    }

    public Anmashareport_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
        //setHasStableIds(true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anmashareport_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder,  int position) {

//        holder.idimage.setBackgroundResource(Icon[position]);


        int pending = Integer.valueOf(data.get(position).get("Claim")) - (Integer.valueOf(data.get(position).get("AmtRecieved")) + Integer.valueOf(data.get(position).get("NotApproved")));

        holder.tv_year.setText(data.get(position).get("name"));
        holder.tv_month.setText(month[Integer.valueOf(data.get(position).get("Month"))]);
        holder.tv_claimed.setText(data.get(position).get("Claim"));
        holder.tv_amntrcvd.setText(data.get(position).get("AmtRecieved"));
        holder.tv_notapproved.setText(data.get(position).get("NotApproved"));
        holder.tv_pending.setText("0");

        if (((position + 1) % 2) != 0) {
            holder.tv_year.setBackgroundResource(R.drawable.gridbox);
            holder.tv_month.setBackgroundResource(R.drawable.gridbox);
            holder.tv_claimed.setBackgroundResource(R.drawable.gridbox);
            holder.tv_amntrcvd.setBackgroundResource(R.drawable.gridbox);
            holder.tv_notapproved.setBackgroundResource(R.drawable.gridbox);
            holder.tv_pending.setBackgroundResource(R.drawable.gridbox);

        } else {

            holder.tv_year.setBackgroundResource(R.drawable.textbox1);
            holder.tv_month.setBackgroundResource(R.drawable.textbox1);
            holder.tv_claimed.setBackgroundResource(R.drawable.textbox1);
            holder.tv_amntrcvd.setBackgroundResource(R.drawable.textbox1);
            holder.tv_notapproved.setBackgroundResource(R.drawable.textbox1);
            holder.tv_pending.setBackgroundResource(R.drawable.textbox1);

        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
