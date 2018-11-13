package com.microware.intrahealth.Incentive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveAdapter.AnmReport_AA_Adapter;
import com.microware.intrahealth.IncentiveAdapter.AnmReport_Claim_AA_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/20/2017.
 */

public class Anmreport_AA_Activity extends Activity {
    DataProvider dataProvider;
    Global global;
    Spinner spin_year, spin_month;

    ArrayAdapter<String> adapter;
    RecyclerView rv_anmreport1, rv_anmreport2;
    LinearLayoutManager layoutManager;
    AnmReport_AA_Adapter anmreport_aa_adapter;
    AnmReport_Claim_AA_Adapter anm_report_Claima_aAdapter;
    int Year = 0,month=0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anmreport_aa_activity);
        dataProvider = new DataProvider(this);
        global = (Global) this.getApplicationContext();
        spin_year = (Spinner) findViewById(R.id.spin_year);
        spin_month = (Spinner) findViewById(R.id.spin_month);
        rv_anmreport1 = (RecyclerView) findViewById(R.id.rv_anmreport1);
        rv_anmreport2 = (RecyclerView) findViewById(R.id.rv_anmreport2);
        fillspinner(spin_month, getResources().getStringArray(R.array.monthincentive));
        fillspinner(spin_year, getResources().getStringArray(R.array.globalyear));
        // fillRecycler(0);

        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // etSearch.setText("");
                if (pos > 0) {
                    Year = Integer.valueOf(spin_year.getSelectedItem().toString());
                    fillRecycler(1);
                    fillRecyclerSecond(1);
                } else {
                    fillRecycler(0);
                    fillRecyclerSecond(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // etSearch.setText("");
                if (pos > 0 && spin_year.getSelectedItemPosition() > 0) {
                    Year = Integer.valueOf(spin_year.getSelectedItem().toString());
                    month = spin_month.getSelectedItemPosition();
                    fillRecycler(2);
                    fillRecyclerSecond(2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void fillspinner(Spinner spin, String[] ss) {

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, ss);
        spin.setAdapter(adapter);
    }

    public void fillRecycler(int flag) {
        String sql = "";
        ArrayList<HashMap<String, String>> data = null;
        if (flag == 0) {
            sql = "select Year,Month,count(AshaID) as AshaCount ,sum(Claim) as Claim,sum(NotApproved) as NotApproved from tblincentivesurvey where  AnmId='" + global.getsGlobalANMCODE() + "' Group by Year , Month order by Year DESC";
        }
        if (flag == 1) {
            sql = "select Year,Month,count(AshaID) as AshaCount ,sum(Claim) as Claim,sum(NotApproved) as NotApproved from tblincentivesurvey where   Year=" + Year + " and AnmId='" + global.getsGlobalANMCODE() + "' Group by Year , Month order by Year DESC";
        }if (flag == 2) {
            sql = "select Year,Month,count(AshaID) as AshaCount ,sum(Claim) as Claim,sum(NotApproved) as NotApproved from tblincentivesurvey where   Month=" + month + " and Year=" + Year + " and AnmId='" + global.getsGlobalANMCODE() + "' Group by Year , Month order by Year DESC";
        }
        data = dataProvider.getDynamicVal(sql);
        //"select Year,Month,count(AshaID) as AshaCount ,sum(Claim) as Claim,sum(NotApproved) as NotApproved from tblincentivesurvey where  AnmId=1 Group by Year , Month order by Year DESC"
        if (data != null) {

            anmreport_aa_adapter = new AnmReport_AA_Adapter(this, data);
            rv_anmreport1.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rv_anmreport1.setLayoutManager(layoutManager);
            rv_anmreport1.setAdapter(anmreport_aa_adapter);
            anmreport_aa_adapter.notifyDataSetChanged();
        }
    }

    public void fillRecyclerSecond(int flag) {
        String sql = "";
        ArrayList<HashMap<String, String>> data = null;
        if (flag == 0) {
            sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved from tblincentivesurvey where  AnmId='" + global.getsGlobalANMCODE() + "' Group by Year order by Year DESC";
        }
        if (flag == 1) {
            sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved from tblincentivesurvey where   Year=" + Year + " and  AnmId='" + global.getsGlobalANMCODE() + "' Group by Year order by Year DESC";
        } if (flag == 2) {

            sql = "select Year,sum(Claim) as Claim,sum(AmtRecieved) as AmtRecieved,sum(NotApproved) as NotApproved from tblincentivesurvey where  Month=" + month + " and Year=" + Year + " and  AnmId='" + global.getsGlobalANMCODE() + "' Group by Year order by Year DESC";
        }
        data = dataProvider.getDynamicVal(sql);
        if (data != null) {
            anm_report_Claima_aAdapter = new AnmReport_Claim_AA_Adapter(this, data);
            rv_anmreport2.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rv_anmreport2.setLayoutManager(layoutManager);
            rv_anmreport2.setAdapter(anm_report_Claima_aAdapter);
            anm_report_Claima_aAdapter.notifyDataSetChanged();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (global.getiGlobalRoleID() == 3) {
            menu.add(0, 0, 1, global.getsGlobalANMName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }

        return true;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Anmreport_AA_Activity.this, Dashboard_Activity.class);
        finish();
        startActivity(i);
    }

}
