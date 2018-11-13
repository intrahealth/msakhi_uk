package com.microware.intrahealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportanmHnbclist extends Activity {

    TextView tv_0dayschildren, tv_0daysHBNC, tv_0daysHBNCnot, tv_3dayschildren, tv_3daysHBNC, tv_3daysHBNCnot, tv_7dayschildren, tv_7daysHBNCnot, tv_7daysHBNC, tv_14dayschildren, tv_14daysHBNCnot, tv_14daysHBNC, tv_21dayschildren, tv_21daysHBNCnot, tv_21daysHBNC, tv_28dayschildren,
            tv_28daysHBNCnot, tv_28daysHBNC, tv_42dayschildren, tv_42daysHBNCnot, tv_42daysHBNC, tv_0dayschildren1, tv_0daysHBNC1, tv_0daysHBNCnot1, tv_3dayschildren2, tv_3daysHBNC2, tv_3daysHBNCnot2, tv_7dayschildren3, tv_7daysHBNCnot3, tv_7daysHBNC3, tv_14dayschildren4, tv_14daysHBNCnot4, tv_14daysHBNC4, tv_21dayschildren5, tv_21daysHBNCnot5, tv_21daysHBNC5, tv_28dayschildren6,
            tv_28daysHBNCnot6, tv_28daysHBNC6, tv_42dayschildren7, tv_42daysHBNCnot7, tv_42daysHBNC7, tv_village;
    DataProvider dataprovider;


    Global global;
    int anmid = 0, VillageID = 0, flag = 0;

    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    Validate validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportashahnbclist);
        dataprovider = new DataProvider(this);
        validate = new Validate(this);

        global = (Global) getApplication();
        setTitle(global.getVersionName());
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);


        tv_village = (TextView) findViewById(R.id.tv_village);
        tv_0dayschildren = (TextView) findViewById(R.id.tv_0dayschildren);
        tv_0daysHBNC = (TextView) findViewById(R.id.tv_0daysHBNC);
        tv_0daysHBNCnot = (TextView) findViewById(R.id.tv_0daysHBNCnot);
        tv_3dayschildren = (TextView) findViewById(R.id.tv_3dayschildren);
        tv_3daysHBNC = (TextView) findViewById(R.id.tv_3daysHBNC);
        tv_3daysHBNCnot = (TextView) findViewById(R.id.tv_3daysHBNCnot);
        tv_7dayschildren = (TextView) findViewById(R.id.tv_7dayschildren);
        tv_7daysHBNC = (TextView) findViewById(R.id.tv_7daysHBNC);
        tv_7daysHBNCnot = (TextView) findViewById(R.id.tv_7daysHBNCnot);
        tv_14dayschildren = (TextView) findViewById(R.id.tv_14dayschildren);
        tv_14daysHBNC = (TextView) findViewById(R.id.tv_14daysHBNC);
        tv_14daysHBNCnot = (TextView) findViewById(R.id.tv_14daysHBNCnot);
        tv_21dayschildren = (TextView) findViewById(R.id.tv_21dayschildren);
        tv_21daysHBNC = (TextView) findViewById(R.id.tv_21daysHBNC);
        tv_21daysHBNCnot = (TextView) findViewById(R.id.tv_21daysHBNCnot);
        tv_28dayschildren = (TextView) findViewById(R.id.tv_28dayschildren);
        tv_28daysHBNC = (TextView) findViewById(R.id.tv_28daysHBNC);
        tv_28daysHBNCnot = (TextView) findViewById(R.id.tv_28daysHBNCnot);
        tv_42dayschildren = (TextView) findViewById(R.id.tv_42dayschildren);
        tv_42daysHBNC = (TextView) findViewById(R.id.tv_42daysHBNC);
        tv_42daysHBNCnot = (TextView) findViewById(R.id.tv_42daysHBNCnot);
        tv_0dayschildren1 = (TextView) findViewById(R.id.tv_0dayschildren1);
        tv_0daysHBNC1 = (TextView) findViewById(R.id.tv_0daysHBNC1);
        tv_0daysHBNCnot1 = (TextView) findViewById(R.id.tv_0daysHBNCnot1);
        tv_3dayschildren2 = (TextView) findViewById(R.id.tv_3dayschildren2);
        tv_3daysHBNC2 = (TextView) findViewById(R.id.tv_3daysHBNC2);
        tv_3daysHBNCnot2 = (TextView) findViewById(R.id.tv_3daysHBNCnot2);
        tv_7dayschildren3 = (TextView) findViewById(R.id.tv_7dayschildren3);
        tv_7daysHBNC3 = (TextView) findViewById(R.id.tv_7daysHBNC3);
        tv_7daysHBNCnot3 = (TextView) findViewById(R.id.tv_7daysHBNCnot3);
        tv_14dayschildren4 = (TextView) findViewById(R.id.tv_14dayschildren4);
        tv_14daysHBNC4 = (TextView) findViewById(R.id.tv_14daysHBNC4);
        tv_14daysHBNCnot4 = (TextView) findViewById(R.id.tv_14daysHBNCnot4);
        tv_21dayschildren5 = (TextView) findViewById(R.id.tv_21dayschildren5);
        tv_21daysHBNC5 = (TextView) findViewById(R.id.tv_21daysHBNC5);
        tv_21daysHBNCnot5 = (TextView) findViewById(R.id.tv_21daysHBNCnot5);
        tv_28dayschildren6 = (TextView) findViewById(R.id.tv_28dayschildren6);
        tv_28daysHBNC6 = (TextView) findViewById(R.id.tv_28daysHBNC6);
        tv_28daysHBNCnot6 = (TextView) findViewById(R.id.tv_28daysHBNCnot6);
        tv_42dayschildren7 = (TextView) findViewById(R.id.tv_42dayschildren7);
        tv_42daysHBNC7 = (TextView) findViewById(R.id.tv_42daysHBNC7);
        tv_42daysHBNCnot7 = (TextView) findViewById(R.id.tv_42daysHBNCnot7);

        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            anmid = Validate.returnIntegerValue(global.getsGlobalANMCODE());
        }
        tv_village.setVisibility(View.GONE);
        spinVillageName.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            flag = extras.getInt("flag");
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    VillageID = Village.get(
                            spinVillageName.getSelectedItemPosition() - 1)
                            .getVillageID();
                    showdata(VillageID);
                    showdata1(VillageID);

                } else {
                    VillageID = 0;
                    showAlldata();
                    showAlldata1();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        showAlldata();
        showAlldata1();
//		radio1Month.setChecked(true);
//		showdatalastMonth();


    }

    //
    private void fillVillageName(int Language) {
        Village = dataprovider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public void showdata(int VillageId) {


        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where    tblPNChomevisit_ANS.VisitNo=1 and    Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC.setText("" + iValue5dose);
        tv_0daysHBNCnot.setText("" + (iValue5child - iValue5dose));
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=3  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren.setText("" + iValue6child);

        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=2 and    Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC.setText("" + iValue6dose);
        tv_3daysHBNCnot.setText("" + (iValue6child - iValue6dose));
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=7  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=3  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC.setText("" + iValue7dose);
        tv_7daysHBNCnot.setText("" + (iValue7child - iValue7dose));
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=14  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=4  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC.setText("" + iValue8dose);
        tv_14daysHBNCnot.setText("" + (iValue8child - iValue8dose));
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=21  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=5  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC.setText("" + iValue9dose);
        tv_21daysHBNCnot.setText("" + (iValue9child - iValue9dose));
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=28  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=6   and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC.setText("" + iValue10dose);
        tv_28daysHBNCnot.setText("" + (iValue10child - iValue10dose));
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=42  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=7  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC.setText("" + iValue11dose);
        tv_42daysHBNCnot.setText("" + (iValue11child - iValue11dose));


    }

    public void showdata1(int VillageId) {


        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren1.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where   (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and  tblPNChomevisit_ANS.VisitNo=1 and    Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC1.setText("" + iValue5dose);
        tv_0daysHBNCnot1.setText("" + countper(iValue5child, iValue5dose));
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=3  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren2.setText("" + iValue6child);
        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=2 and    Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC2.setText("" + iValue6dose);
        tv_3daysHBNCnot2.setText("" + countper(iValue6child, iValue6dose));
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=7  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren3.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=3  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC3.setText("" + iValue7dose);
        tv_7daysHBNCnot3.setText("" + countper(iValue7child, iValue7dose));
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=14  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren4.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and  tblPNChomevisit_ANS.VisitNo=4  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC4.setText("" + iValue8dose);
        tv_14daysHBNCnot4.setText("" + countper(iValue8child, iValue8dose));
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and  (julianday('Now')-julianday(tblChild.CHILD_dob) )>=21  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren5.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=5  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC5.setText("" + iValue9dose);
        tv_21daysHBNCnot5.setText("" + countper(iValue9child, iValue9dose));
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and  (julianday('Now')-julianday(tblChild.CHILD_dob) )>=28  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren6.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=6   and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC6.setText("" + iValue10dose);
        tv_28daysHBNCnot6.setText("" + countper(iValue10child, iValue10dose));
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=42  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren7.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=7  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.anmid = " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC7.setText("" + iValue11dose);
        tv_42daysHBNCnot7.setText("" + countper(iValue11child, iValue11dose));


    }

    public int countchild(int day, int villageID, int ashaid) {
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=" + day + "  and Tbl_HHSurvey.VillageID=" + villageID + " and   tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        return iValue11child;
    }

    public float countper(int tot, int child) {
        float iValue11child = 0;
        if (tot > 0) {
            iValue11child = (child * 100) / tot;
        }
        return iValue11child;
    }


    @SuppressLint("SimpleDateFormat")
    public String ReturnDate(int days, int iFlag, String date) {
        String sValue = "";
        // String sCurrentDate = Validate.getcurrentdate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (iFlag == 1) {
            c.add(Calendar.DATE, days); // number of days to add
        } else {
            c.add(Calendar.DATE, -days); // number of days to minus
        }
        sValue = sdf.format(c.getTime());

        return sValue;
    }

    public static long Number_Days(String dt1, String dt2) {
        long diff = 0;
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        // String inputString1 = "23 01 2015";
        // String inputString2 = "27 04 2015";
        try {
            Date date1 = myFormat.parse(dt1);
            Date date2 = myFormat.parse(dt2);
            diff = date2.getTime() - date1.getTime();
            // System.out.println("Days: " + TimeUnit.DAYS.convert(diff,
            // TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(ReportanmHnbclist.this,
                HbncReport.class);
        finish();
        startActivity(i);


    }

    public void showAlldata() {


        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=0 and tblChild.anmid = " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where   tblPNChomevisit_ANS.VisitNo=1 and    tblChild.anmid = " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC.setText("" + iValue5dose);
        tv_0daysHBNCnot.setText("" + (iValue5child - iValue5dose));
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=3  and tblChild.anmid = " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren.setText("" + iValue6child);
        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=2 and    tblChild.anmid = " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC.setText("" + iValue6dose);
        tv_3daysHBNCnot.setText("" + (iValue6child - iValue6dose));
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=7  and tblChild.anmid = " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=3 and     tblChild.anmid = " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC.setText("" + iValue7dose);
        tv_7daysHBNCnot.setText("" + (iValue7child - iValue7dose));
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=14   and tblChild.anmid = " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=4 and     tblChild.anmid = " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC.setText("" + iValue8dose);
        tv_14daysHBNCnot.setText("" + (iValue8child - iValue8dose));
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=21  and tblChild.anmid = " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=5 and      tblChild.anmid = " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC.setText("" + iValue9dose);
        tv_21daysHBNCnot.setText("" + (iValue9child - iValue9dose));
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=28   and tblChild.anmid = " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=6 and      tblChild.anmid = " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC.setText("" + iValue10dose);
        tv_28daysHBNCnot.setText("" + (iValue10child - iValue10dose));
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) )>=42   and tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=7  and tblChild.anmid = " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC.setText("" + iValue11dose);
        tv_42daysHBNCnot.setText("" + (iValue11child - iValue11dose));


    }

    public void showAlldata1() {


        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=0 and tblChild.anmid = " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren1.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and   tblPNChomevisit_ANS.VisitNo=1 and    tblChild.anmid = " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC1.setText("" + iValue5dose);
        tv_0daysHBNCnot1.setText("" + countper(iValue5child, iValue5dose));
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=3  and tblChild.anmid = " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren2.setText("" + iValue6child);
        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and  tblPNChomevisit_ANS.VisitNo=2 and    tblChild.anmid = " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC2.setText("" + iValue6dose);
        tv_3daysHBNCnot2.setText("" + countper(iValue6child, iValue6dose));
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=7  and tblChild.anmid = " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren3.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=3 and     tblChild.anmid = " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC3.setText("" + iValue7dose);
        tv_7daysHBNCnot3.setText("" + countper(iValue7child, iValue7dose));
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=14   and tblChild.anmid = " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren4.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=4 and     tblChild.anmid = " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC4.setText("" + iValue8dose);
        tv_14daysHBNCnot4.setText("" + countper(iValue8child, iValue8dose));
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=21  and tblChild.anmid = " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren5.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=5 and      tblChild.anmid = " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC5.setText("" + iValue9dose);
        tv_21daysHBNCnot5.setText("" + countper(iValue9child, iValue9dose));
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=28   and tblChild.anmid = " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren6.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=6 and      tblChild.anmid = " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC6.setText("" + iValue10dose);
        tv_28daysHBNCnot6.setText("" + countper(iValue10child, iValue10dose));
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and (julianday('Now')-julianday(tblChild.CHILD_dob) )>=42   and tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren7.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where (julianday('Now')-julianday(tblChild.CHILD_dob) ) between 43 and 730 and tblPNChomevisit_ANS.VisitNo=7  and tblChild.anmid = " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC7.setText("" + iValue11dose);
        tv_42daysHBNCnot7.setText("" + countper(iValue11child, iValue11dose));


    }

    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 1, validate.RetriveSharepreferenceString("name")).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {

            case 1:
                if (item.getTitle().equals(validate.RetriveSharepreferenceString("Username"))) {
                    item.setTitle(validate.RetriveSharepreferenceString("name"));

                } else {
                    item.setTitle(validate.RetriveSharepreferenceString("Username"));

                }
                break;


            default:
                break;
        }

        return true;
    }

    public int countAllchild(int day, int ashaid) {
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=" + day + " and   tblChild.anmid = " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        return iValue11child;
    }
}
