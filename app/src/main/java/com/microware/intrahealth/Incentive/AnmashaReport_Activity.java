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
import com.microware.intrahealth.IncentiveAdapter.Anmashareport_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/20/2017.
 */

public class AnmashaReport_Activity extends Activity {
    DataProvider dataProvider;
    Global global;

    ArrayAdapter<String> adapter;

    LinearLayoutManager layoutManager;
    Anmashareport_Adapter anmreport_adapter;

    TextView tv_year, tv_Claimed, tv_Amountreceived, tv_notapproved, tv_pending;

    RecyclerView rv_anmreport;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    ArrayList<HashMap<String, String>> data = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anmashareport_activity);
        global = (Global) getApplicationContext();
        dataProvider = new DataProvider(this);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_Claimed = (TextView) findViewById(R.id.tv_claimed);
        tv_Amountreceived = (TextView) findViewById(R.id.tv_amntrcvd);
        tv_notapproved = (TextView) findViewById(R.id.tv_notapproved);
        tv_pending = (TextView) findViewById(R.id.tv_pending);

        rv_anmreport = (RecyclerView) findViewById(R.id.rv_anmreport);

        fillRecycler();
        showwdata();


    }



    public void showwdata() {
        try {
            tv_year.setText(String.valueOf(global.getGlobalYear()));

            ArrayList<HashMap<String, String>> dataupdate = null;
            String sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved from tblincentivesurvey where  AnmId='" + global.getsGlobalANMCODE() + "' and Year="+global.getGlobalYear()+" Group by Year ";
            dataupdate = dataProvider.getDynamicVal(sql);
            if (dataupdate != null) {
                int pending = Integer.valueOf(dataupdate.get(0).get("Claim")) - (Integer.valueOf(dataupdate.get(0).get("AmtRecieved")) + Integer.valueOf(dataupdate.get(0).get("NotApproved")));


                tv_Claimed.setText(dataupdate.get(0).get("Claim"));
                tv_Amountreceived.setText(dataupdate.get(0).get("AmtRecieved"));
                tv_notapproved.setText(dataupdate.get(0).get("NotApproved"));
                tv_pending.setText("0");

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    public void fillRecycler() {

        String sql = "select tblincentivesurvey.* ,MSTASHA.ASHAName as name from tblincentivesurvey inner join Mstasha on MSTASHA.ASHAID=tblincentivesurvey.AshaID where   AnmId='" + global.getsGlobalANMCODE() + "' and Year='" + global.getGlobalYear() + "' and MSTASHA.LanguageID=" + global.getLanguage() + "  order By tblincentivesurvey.AshaID,Month";
        data = dataProvider.getDynamicVal(sql);
        if (data != null) {
            anmreport_adapter = new Anmashareport_Adapter(this, data);
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

        Intent i = new Intent(AnmashaReport_Activity.this, Anmreport_AA_Activity.class);
        finish();
        global.setGlobalYear(0);
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
