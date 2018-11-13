package com.microware.intrahealth.Incentive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveAdapter.AshaData_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kundanrawat on 18/11/17.
 */

public class AshaData extends Activity {

    DataProvider dataProvider;
    Global global;

    ArrayAdapter<String> adapter;
    RecyclerView recyclerview_ashadata;
    LinearLayoutManager layoutManager;
    TextView tv_totalclaimed, tv_totalapproved, tv_year, tv_month;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    AshaData_Adapter ashadata_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ashamonthlydata);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
//        spin_year = (Spinner) findViewById(R.id.spin_year);
//        spin_month = (Spinner) findViewById(R.id.spin_month);
        recyclerview_ashadata = (RecyclerView) findViewById(R.id.recyclerview_ashadata);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_totalclaimed = (TextView) findViewById(R.id.tv_totalclaimed);
        tv_totalapproved = (TextView) findViewById(R.id.tv_totalapproved);

        fillRecycler();
        showwdata();
    }


    public void showwdata() {
        try {
            tv_year.setText(String.valueOf(global.getGlobalYear()));
            tv_month.setText(month[global.getGlobalMonth() - 1]);
            ArrayList<HashMap<String, String>> data = null;
            String sql = "select * from tblincentivesurvey where  IncentiveSurveyGUID='" + global.getIncentiveSurveyGUID() + "'  ";
            data = dataProvider.getDynamicVal(sql);
            if (data != null && data.size() > 0) {
                tv_totalclaimed.setText(data.get(0).get("Claim"));
               int  status = Integer.valueOf(data.get(0).get("ANMStatus"));
                if (status == 1) {
                    String sqlApproved = "select  sum(ActivityTotal) from tblashaincentiveDetail  where  ANMStatus=1 and AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "'  and IncentiveSurveyGUID='" + data.get(0).get("IncentiveSurveyGUID") + "' ";
                    String Approved = dataProvider.getRecord(sqlApproved);
                    tv_totalapproved.setText(Approved);
                } else {
                    tv_totalapproved.setText("0");
                }
                //tv_totalapproved.setText(data.get(0).get("AmtRecieved"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void fillRecycler() {

        ArrayList<HashMap<String, String>> data = null;
        String sql = "select * from tblashaincentivedetail where  AshaID='" + global.getsGlobalAshaCode() + "' and Year='" + global.getGlobalYear() + "' and Month='" + global.getGlobalMonth() + "'  ";
        data = dataProvider.getDynamicVal(sql);
        if (data != null && data.size() > 0) {
            ashadata_adapter = new AshaData_Adapter(this, data);
            recyclerview_ashadata.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerview_ashadata.setLayoutManager(layoutManager);
            recyclerview_ashadata.setAdapter(ashadata_adapter);
            ashadata_adapter.notifyDataSetChanged();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub


        Intent i = new Intent(AshaData.this,
                Incentive_Tab_Activity.class);
        finish();
        startActivity(i);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (global.getiGlobalRoleID() == 2) {
            menu.add(0, 0, 1, global.getsGlobalAshaName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }

        return true;
    }
}
