package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.NcdScreeningCHC;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class Immunization_DueList_Adapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data;
    Global global;
    DataProvider dataProvider;
    int flag = 0;

    public Immunization_DueList_Adapter(Context context,
                                        ArrayList<HashMap<String, String>> data, int flag) {
        this.data = data;
        this.context = context;
        this.flag = flag;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
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
            gridview = layoutInflater.inflate(R.layout.ancduelist_adapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        // ChildGUID HHFamilyMemberGUID
        TextView tv_Name = (TextView) gridview
                .findViewById(R.id.tv_Name);
        TableRow GridRow = (TableRow) gridview
                .findViewById(R.id.GridRow);
        if(position%2==0){
            GridRow.setBackgroundColor(context.getResources().getColor(R.color.AliceBlue));
        }else{
            GridRow.setBackgroundColor(context.getResources().getColor(R.color.White));
        }
        if (flag == 1) {
            String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                    + data.get(position).get("HHFamilyMemberGUID") + "'";
            String mothername = dataProvider.getRecord(sql1);
            tv_Name.setText(data.get(position).get("child_name") + "/" + mothername + "/" + Validate.changeDateFormat(data.get(position).get("Child_dob")));
        } else if(flag==2) {
            String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                    + data.get(position).get("HHFamilyMemberGUID") + "'";
            String sql2 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID =(select Spouse from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                    + data.get(position).get("HHFamilyMemberGUID") + "')";
            String mothername = dataProvider.getRecord(sql1);
            String fathername = dataProvider.getRecord(sql2);
            tv_Name.setText(mothername+fathername);
        }

        return gridview;
    }


}
