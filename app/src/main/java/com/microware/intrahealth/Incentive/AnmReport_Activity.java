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
import com.microware.intrahealth.IncentiveAdapter.AnmReport_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/20/2017.
 */

public class AnmReport_Activity extends Activity {
    DataProvider dataProvider;
    Global global;

    ArrayAdapter<String> adapter;

    LinearLayoutManager layoutManager;
    AnmReport_Adapter anmreport_adapter;

    TextView tv_Asha, tv_Area, tv_Totalclaimed, tv_TotalApproved, tv_year, tv_month;

    RecyclerView rv_anmreport;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anmreport_activity);
        global = (Global) getApplicationContext();
        dataProvider = new DataProvider(this);
        tv_year = (TextView) findViewById(R.id.tv_year);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_Asha = (TextView) findViewById(R.id.tv_Asha);
        tv_Area = (TextView) findViewById(R.id.tv_Area);
        tv_Totalclaimed = (TextView) findViewById(R.id.tv_Totalclaimed);
        tv_TotalApproved = (TextView) findViewById(R.id.tv_TotalApproved);
        rv_anmreport = (RecyclerView) findViewById(R.id.rv_anmreport);


        fillRecycler();
      //  fillRecyclerreason();
        showwdata();


    }


    public void showwdata() {
        try {
            tv_year.setText(String.valueOf(global.getGlobalYear()));
            tv_month.setText(month[global.getGlobalMonth() - 1]);
            tv_Asha.setText(global.getsGlobalAshaName());
            ArrayList<HashMap<String, String>> dataupdate = null;
            String sql = "select * from tblincentivesurvey where  AshaID='" + global.getsGlobalAshaCode() + "' and Year='" + global.getGlobalYear() + "' and Month='" + global.getGlobalMonth() + "'  ";
            dataupdate = dataProvider.getDynamicVal(sql);
            if (dataupdate != null) {
                tv_Totalclaimed.setText(dataupdate.get(0).get("Claim"));
                int status = 0;
                int amount = Integer.valueOf(dataupdate.get(0).get("Claim")) - Integer.valueOf(dataupdate.get(0).get("NotApproved"));
                status = Integer.valueOf(dataupdate.get(0).get("ANMStatus"));
                if (status == 1) {
                    String sqlApproved = "select  sum(ActivityTotal) from tblashaincentiveDetail  where  ANMStatus=1 and AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "'  and IncentiveSurveyGUID='" + dataupdate.get(0).get("IncentiveSurveyGUID") + "' ";
                    String Approved = dataProvider.getRecord(sqlApproved);
                    tv_TotalApproved.setText(Approved);
                } else {
                    tv_TotalApproved.setText("0");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void fillRecycler() {

        String sql = "select * from tblashaincentivedetail where  AshaID='" + global.getsGlobalAshaCode() + "' and Year='" + global.getGlobalYear() + "' and Month='" + global.getGlobalMonth() + "'  ";
        data = dataProvider.getDynamicVal(sql);
        if (data != null) {
            anmreport_adapter = new AnmReport_Adapter(this, data);
            rv_anmreport.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rv_anmreport.setLayoutManager(layoutManager);
            rv_anmreport.setAdapter(anmreport_adapter);
            anmreport_adapter.notifyDataSetChanged();
        }
    }



    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(AnmReport_Activity.this, AnmReport_BB_Activity.class);
        finish();
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (global.getiGlobalRoleID() == 3) {
            menu.add(0, 0, 1, global.getsGlobalANMName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }

        return true;
    }
}
