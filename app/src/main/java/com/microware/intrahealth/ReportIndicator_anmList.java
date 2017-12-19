package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class ReportIndicator_anmList extends Activity {

    TextView tvCurrentlypregnant, tvinfirsttrimester, tvinsecondtrimester, tvinthirdtrimester, tv1stAnc, tv2ndAnc, tv3rdAnc, tv4rthAnc, tvTT2Boster, tvHRP;

    Button btndetails;
    Global global;
    DataProvider dataprovider;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    int anmid = 0, VillageID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportindicator_anm_list);
        dataprovider = new DataProvider(this);

        global = (Global) getApplication();
        setTitle(global.getVersionName());
        tvCurrentlypregnant = (TextView) findViewById(R.id.tvCurrentlypregnant);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        tvinfirsttrimester = (TextView) findViewById(R.id.tvinfirsttrimester);
        tvinsecondtrimester = (TextView) findViewById(R.id.tvinsecondtrimester);
        tvinthirdtrimester = (TextView) findViewById(R.id.tvinthirdtrimester);
        tv1stAnc = (TextView) findViewById(R.id.tv1stAnc);
        tv2ndAnc = (TextView) findViewById(R.id.tv2ndAnc);
        tv3rdAnc = (TextView) findViewById(R.id.tv3rdAnc);
        tv4rthAnc = (TextView) findViewById(R.id.tv4rthAnc);
        tvTT2Boster = (TextView) findViewById(R.id.tvTT2Boster);
        tvHRP = (TextView) findViewById(R.id.tvHRP);

        btndetails = (Button) findViewById(R.id.btndetails);


        btndetails.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(ReportIndicator_anmList.this,
                        AshaDashboardList.class);
                finish();
                startActivity(i);
            }
        });

        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            anmid = Integer.valueOf(global.getsGlobalANMCODE());
        }

//        radio1Month.setChecked(true);
//        showdatafooLastMonth();
        fillVillageName(global.getLanguage());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.Spinnerfilter, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    VillageID = Village.get(
                            spinVillageName.getSelectedItemPosition() - 1)
                            .getVillageID();
                    showdata(VillageID);

                } else {
                    VillageID = 0;
                    showAlldata();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void showdata(int VillageId) {
        String sql1 = "";
        sql1 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue1 = 0;
        ivalue1 = dataprovider.getMaxRecord(sql1);
        tvCurrentlypregnant.setText(String.valueOf(ivalue1));
        String sql2 = "";
        sql2 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=90 and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue2 = 0;
        ivalue2 = dataprovider.getMaxRecord(sql2);
        tvinfirsttrimester.setText(String.valueOf(ivalue2));
        String sql3 = "";
        sql3 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=180 and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                +"";
        int ivalue3 = 0;
        ivalue3 = dataprovider.getMaxRecord(sql3);
        tvinsecondtrimester.setText(String.valueOf(ivalue3));
        String sql4 = "";
        sql4 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=270 and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue4 = 0;
        ivalue4 = dataprovider.getMaxRecord(sql4);
        tvinthirdtrimester.setText(String.valueOf(ivalue4));
        String sql5 = "";
        sql5 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=1 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue5 = 0;
        ivalue5 = dataprovider.getMaxRecord(sql5);
        tv1stAnc.setText(String.valueOf(ivalue5));
        String sql6 = "";
        sql6 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=2 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue6 = 0;
        ivalue6 = dataprovider.getMaxRecord(sql6);
        tv2ndAnc.setText(String.valueOf(ivalue6));
        String sql7 = "";
        sql7 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=3 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue7 = 0;
        ivalue7 = dataprovider.getMaxRecord(sql7);
        tv3rdAnc.setText(String.valueOf(ivalue7));
        String sql8 = "";
        sql8 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=4 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue8 = 0;
        ivalue8 = dataprovider.getMaxRecord(sql8);
        tv4rthAnc.setText(String.valueOf(ivalue8));
        String sql9 = "";
        sql9 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where  ((tblancvisit.TTsecondDoseDate is not null and tblancvisit.TTsecondDoseDate!='') or(tblancvisit.TTBoosterDate is not null and tblancvisit.TTBoosterDate!=''))  and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                +  " Group By tblPregnant_woman.PWGUID ";
        int ivalue9 = 0;
        ivalue9 = dataprovider.getMaxRecord(sql9);
        tvTT2Boster.setText(String.valueOf(ivalue9));
        String sql10 = "";
        sql10 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblPregnant_woman.HighRisk=1 and IsPregnant=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and tblPregnant_woman.ANMID ="
                + anmid
                +  " ";
        int ivalue10 = 0;
        ivalue10 = dataprovider.getMaxRecord(sql10);
        tvHRP.setText(String.valueOf(ivalue10));
    }

    private void fillVillageName(int Language) {
        Village = dataprovider.getMstVillageName(global.getsGlobalANMCODE(), Language,1);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }


    public void showAlldata() {
        String sql1 = "";
        sql1 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID where IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue1 = 0;
        ivalue1 = dataprovider.getMaxRecord(sql1);
        tvCurrentlypregnant.setText(String.valueOf(ivalue1));
        String sql2 = "";
        sql2 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=90 and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue2 = 0;
        ivalue2 = dataprovider.getMaxRecord(sql2);
        tvinfirsttrimester.setText(String.valueOf(ivalue2));
        String sql3 = "";
        sql3 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=180 and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                +"";
        int ivalue3 = 0;
        ivalue3 = dataprovider.getMaxRecord(sql3);
        tvinsecondtrimester.setText(String.valueOf(ivalue3));
        String sql4 = "";
        sql4 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID   where cast(round(julianday('Now')-julianday(tblPregnant_woman.LMPDate) ) as int)<=270 and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue4 = 0;
        ivalue4 = dataprovider.getMaxRecord(sql4);
        tvinthirdtrimester.setText(String.valueOf(ivalue4));
        String sql5 = "";
        sql5 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=1 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue5 = 0;
        ivalue5 = dataprovider.getMaxRecord(sql5);
        tv1stAnc.setText(String.valueOf(ivalue5));
        String sql6 = "";
        sql6 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=2 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue6 = 0;
        ivalue6 = dataprovider.getMaxRecord(sql6);
        tv2ndAnc.setText(String.valueOf(ivalue6));
        String sql7 = "";
        sql7 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=3 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue7 = 0;
        ivalue7 = dataprovider.getMaxRecord(sql7);
        tv3rdAnc.setText(String.valueOf(ivalue7));
        String sql8 = "";
        sql8 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where tblancvisit.Visit_No=4 and tblancvisit.CheckupVisitDate is not null and tblancvisit.CheckupVisitDate!=''  and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                + "";
        int ivalue8 = 0;
        ivalue8 = dataprovider.getMaxRecord(sql8);
        tv4rthAnc.setText(String.valueOf(ivalue8));
        String sql9 = "";
        sql9 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  inner join tblancvisit  on  tblancvisit.PWGUID =tblPregnant_woman.PWGUID where  ((tblancvisit.TTsecondDoseDate is not null and tblancvisit.TTsecondDoseDate!='') or(tblancvisit.TTBoosterDate is not null and tblancvisit.TTBoosterDate!=''))  and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                +  " Group By tblPregnant_woman.PWGUID ";
        int ivalue9 = 0;
        ivalue9 = dataprovider.getMaxRecord(sql9);
        tvTT2Boster.setText(String.valueOf(ivalue9));
        String sql10 = "";
        sql10 = "select  count(*) from tblPregnant_woman  inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblPregnant_woman.HighRisk=1 and IsPregnant=1 and tblPregnant_woman.ANMID ="
                + anmid
                +  " ";
        int ivalue10 = 0;
        ivalue10 = dataprovider.getMaxRecord(sql10);
        tvHRP.setText(String.valueOf(ivalue10));
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(ReportIndicator_anmList.this, Report_Module.class);
        finish();
        startActivity(i);
    }

}
