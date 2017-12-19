package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;


public class Anm_NCD_Report extends Activity {
    TextView tvmale2,tvmale3,tvmale4,tvmale5,tvmale6,tvmale7,tvmale8,tvmale9,tvfemale2,tvfemale3,tvfemale4,tvfemale5,
            tvfemale6,tvfemale7,tvfemale8,tvfemale9,tvtotal,tvtotal2,tvtotal3,tvtotal4,tvtotal5,tvtotal6,tvtotal7,tvtotal8,tvtotal9;
    Button btndetails;
    Global global;
    int ashaid = 0;
    int anmid=0;
    DataProvider dataProvider;
    TableRow tbl_btn,tbl_spin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdreport);
        btndetails=(Button) findViewById(R.id.btndetails);
        global=(Global) getApplicationContext();
        dataProvider=new DataProvider(this);
        tbl_btn = (TableRow) findViewById(R.id.tbl_btn);
        tbl_spin = (TableRow) findViewById(R.id.tbl_spin);
        tvmale2 = (TextView) findViewById(R.id.tvmale2);
        tvmale3 = (TextView) findViewById(R.id.tvmale3);
        tvmale4 = (TextView) findViewById(R.id.tvmale4);
        tvmale5 = (TextView) findViewById(R.id.tvmale5);
        tvmale6 = (TextView) findViewById(R.id.tvmale6);
        tvmale7= (TextView) findViewById(R.id.tvmale7);
        tvmale8 = (TextView) findViewById(R.id.tvmale8);
        tvmale9 = (TextView) findViewById(R.id.tvmale9);

        tvfemale2 = (TextView) findViewById(R.id.tvfemale2);
        tvfemale3 = (TextView) findViewById(R.id.tvfemale3);
        tvfemale4 = (TextView) findViewById(R.id.tvfemale4);
        tvfemale5 = (TextView) findViewById(R.id.tvfemale5);
        tvfemale6 = (TextView) findViewById(R.id.tvfemale6);
        tvfemale7 = (TextView) findViewById(R.id.tvfemale7);
        tvfemale8 = (TextView) findViewById(R.id.tvfemale8);
        tvfemale9 = (TextView) findViewById(R.id.tvfemale9);
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        tvtotal2 = (TextView) findViewById(R.id.tvtotal2);
        tvtotal3 = (TextView) findViewById(R.id.tvtotal3);
        tvtotal4 = (TextView) findViewById(R.id.tvtotal4);
        tvtotal5 = (TextView) findViewById(R.id.tvtotal5);
        tvtotal6 = (TextView) findViewById(R.id.tvtotal6);
        tvtotal7 = (TextView) findViewById(R.id.tvtotal7);
        tvtotal8 = (TextView) findViewById(R.id.tvtotal8);
        tvtotal9 = (TextView) findViewById(R.id.tvtotal9);
        tbl_btn.setVisibility(View.VISIBLE);
        tbl_spin.setVisibility(View.GONE);
        btndetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Anm_NCD_Report.this,
                        AshaListForAnm.class);
                finish();

                i.putExtra("Flag", 7);
                startActivity(i);
            }
        });
        try {
            if (global.getsGlobalANMCODE() != null
                    && global.getsGlobalANMCODE().length() > 0) {
                anmid = Integer.valueOf(global.getsGlobalANMCODE());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        showdata();

    }
    public  void showdata(){
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where AnmID=" + anmid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=1 and a.AnmID=" + anmid + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=2 and a.AnmID=" + anmid + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));

            String sql1C = "";
            sql1C = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  a.AnmID=" + anmid + "";
            int ivalue1c = 0;
            ivalue1c = dataProvider.getMaxRecord(sql1C);
            tvtotal2.setText(String.valueOf(ivalue1c));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Anm_NCD_Report.this, Report_Module.class);
        finish();
        startActivity(i);
    }
}
