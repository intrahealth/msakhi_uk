package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

public class Reportindicator_anmHnbclist extends Activity {

    TextView tv_malechildren, tv_femalechildren, tv_totalchildren, tv_Homedeliverymale, tv_Homedeliveryfemale, tv_Homedeliverytotal, tv_InstitutionalDeliverymale,
            tv_InstitutionalDeliveryfemale, tv_InstitutionalDeliverytotal, tv_Birthweightmale, tv_Birthweightfemale, tv_Birthweighttotal, tv_0dayschildren,
            tv_0daysHBNC, tv_3dayschildren, tv_3daysHBNC, tv_7dayschildren, tv_7daysHBNC, tv_14dayschildren, tv_14daysHBNC, tv_21dayschildren, tv_21daysHBNC, tv_28dayschildren,
            tv_28daysHBNC, tv_42dayschildren, tv_42daysHBNC, tv_BCGchildren, tv_BCGDose, tv_Poliochildren, tv_PolioDose, tv_dpt1children, tv_dpt1Dose, tv_Polioonechildren,
            tv_PoliooneDose, tv_HepB1children, tv_HepB1Dose, tv_Pentavalent1children, tv_Pentavalent1Dose, tv_dpt2children, tv_dpt2Dose, tv_Poliotwochildren, tv_PoliotwoDose,
            tv_HepBtwochildren, tv_HepBtwoDose, tv_Pentavalent2children, tv_Pentavalent2Dose, tv_dpt3children, tv_dpt3Dose, tv_hep3children,
            tv_hep3Dose, tv_Pentavalent3children, tv_Pentavalent3Dose, tv_Measleschildren, tv_MeaslesDose;

    Button btndetails;
    Global global;
    DataProvider dataprovider;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;

    int anmid = 0, VillageID = 0;
    Validate validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_indicatore_anmhnbclist);
        dataprovider = new DataProvider(this);
        validate = new Validate(this);

        global = (Global) getApplication();
        setTitle(global.getVersionName());
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);

        tv_malechildren = (TextView) findViewById(R.id.tv_malechildren);
        tv_femalechildren = (TextView) findViewById(R.id.tv_femalechildren);
        tv_totalchildren = (TextView) findViewById(R.id.tv_totalchildren);
        tv_Homedeliverymale = (TextView) findViewById(R.id.tv_Homedeliverymale);
        tv_Homedeliveryfemale = (TextView) findViewById(R.id.tv_Homedeliveryfemale);
        tv_Homedeliverytotal = (TextView) findViewById(R.id.tv_Homedeliverytotal);
        tv_InstitutionalDeliverymale = (TextView) findViewById(R.id.tv_InstitutionalDeliverymale);
        tv_InstitutionalDeliveryfemale = (TextView) findViewById(R.id.tv_InstitutionalDeliveryfemale);
        tv_InstitutionalDeliverytotal = (TextView) findViewById(R.id.tv_InstitutionalDeliverytotal);
        tv_Birthweightmale = (TextView) findViewById(R.id.tv_Birthweightmale);
        tv_Birthweightfemale = (TextView) findViewById(R.id.tv_Birthweightfemale);
        tv_Birthweighttotal = (TextView) findViewById(R.id.tv_Birthweighttotal);
        tv_0dayschildren = (TextView) findViewById(R.id.tv_0dayschildren);
        tv_0daysHBNC = (TextView) findViewById(R.id.tv_0daysHBNC);
        tv_3dayschildren = (TextView) findViewById(R.id.tv_3dayschildren);
        tv_3daysHBNC = (TextView) findViewById(R.id.tv_3daysHBNC);
        tv_7dayschildren = (TextView) findViewById(R.id.tv_7dayschildren);
        tv_7daysHBNC = (TextView) findViewById(R.id.tv_7daysHBNC);
        tv_14dayschildren = (TextView) findViewById(R.id.tv_14dayschildren);
        tv_14daysHBNC = (TextView) findViewById(R.id.tv_14daysHBNC);
        tv_21dayschildren = (TextView) findViewById(R.id.tv_21dayschildren);
        tv_21daysHBNC = (TextView) findViewById(R.id.tv_21daysHBNC);
        tv_28dayschildren = (TextView) findViewById(R.id.tv_28dayschildren);
        tv_28daysHBNC = (TextView) findViewById(R.id.tv_28daysHBNC);
        tv_42dayschildren = (TextView) findViewById(R.id.tv_42dayschildren);
        tv_42daysHBNC = (TextView) findViewById(R.id.tv_42daysHBNC);
        tv_BCGchildren = (TextView) findViewById(R.id.tv_BCGchildren);
        tv_BCGDose = (TextView) findViewById(R.id.tv_BCGDose);
        tv_Poliochildren = (TextView) findViewById(R.id.tv_Poliochildren);
        tv_PolioDose = (TextView) findViewById(R.id.tv_PolioDose);
        tv_dpt1children = (TextView) findViewById(R.id.tv_dpt1children);
        tv_dpt1Dose = (TextView) findViewById(R.id.tv_dpt1Dose);
        tv_Polioonechildren = (TextView) findViewById(R.id.tv_Polioonechildren);
        tv_PoliooneDose = (TextView) findViewById(R.id.tv_PoliooneDose);
        tv_HepB1children = (TextView) findViewById(R.id.tv_HepB1children);
        tv_HepB1Dose = (TextView) findViewById(R.id.tv_HepB1Dose);
        tv_Pentavalent1children = (TextView) findViewById(R.id.tv_Pentavalent1children);
        tv_Pentavalent1Dose = (TextView) findViewById(R.id.tv_Pentavalent1Dose);
        tv_dpt2children = (TextView) findViewById(R.id.tv_dpt2children);
        tv_dpt2Dose = (TextView) findViewById(R.id.tv_dpt2Dose);
        tv_Poliotwochildren = (TextView) findViewById(R.id.tv_Poliotwochildren);
        tv_PoliotwoDose = (TextView) findViewById(R.id.tv_PoliotwoDose);
        tv_HepBtwochildren = (TextView) findViewById(R.id.tv_HepBtwochildren);
        tv_HepBtwoDose = (TextView) findViewById(R.id.tv_HepBtwoDose);
        tv_Pentavalent2children = (TextView) findViewById(R.id.tv_Pentavalent2children);
        tv_Pentavalent2Dose = (TextView) findViewById(R.id.tv_Pentavalent2Dose);
        tv_dpt3children = (TextView) findViewById(R.id.tv_dpt3children);
        tv_dpt3Dose = (TextView) findViewById(R.id.tv_dpt3Dose);

        tv_hep3children = (TextView) findViewById(R.id.tv_hep3children);
        tv_hep3Dose = (TextView) findViewById(R.id.tv_hep3Dose);
        tv_Pentavalent3children = (TextView) findViewById(R.id.tv_Pentavalent3children);
        tv_Pentavalent3Dose = (TextView) findViewById(R.id.tv_Pentavalent3Dose);
        tv_Measleschildren = (TextView) findViewById(R.id.tv_Measleschildren);
        tv_MeaslesDose = (TextView) findViewById(R.id.tv_MeaslesDose);


        btndetails = (Button) findViewById(R.id.btndetails);


        btndetails.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Reportindicator_anmHnbclist.this,
                        AshaDashboardListHnbc.class);
                finish();
                startActivity(i);
            }
        });

        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            anmid = Integer.valueOf(global.getsGlobalANMCODE());
        }

//		radio1Month.setChecked(true);
//		showdatalastMonth();
        fillVillageName(global.getLanguage());
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

    private void fillVillageName(int Language) {
        Village = dataprovider.getMstVillageName(global.getsGlobalANMCODE(), Language, 1);

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
        String Sql1girl = "";
        Sql1girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Gender=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue1girl = 0;
        iValue1girl = dataprovider.getMaxRecord(Sql1girl);
        tv_femalechildren.setText("" + iValue1girl);
        String Sql1boy = "";
        Sql1boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Gender=2 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue1boy = 0;
        iValue1boy = dataprovider.getMaxRecord(Sql1boy);
        tv_malechildren.setText("" + iValue1boy);
        tv_totalchildren.setText("" + (iValue1girl + iValue1boy));
        String Sql2girl = "";
        Sql2girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.place_of_birth=1 and tblChild.Gender=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue2girl = 0;
        iValue2girl = dataprovider.getMaxRecord(Sql2girl);
        tv_Homedeliveryfemale.setText("" + iValue2girl);
        String Sql2boy = "";
        Sql2boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.place_of_birth=1 and tblChild.Gender=2 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue2boy = 0;
        iValue2boy = dataprovider.getMaxRecord(Sql2boy);
        tv_Homedeliverymale.setText("" + iValue2boy);
        tv_Homedeliverytotal.setText("" + (iValue2girl + iValue2boy));
        String Sql3girl = "";
        Sql3girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.place_of_birth=2 and tblChild.Gender=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue3girl = 0;
        iValue3girl = dataprovider.getMaxRecord(Sql3girl);
        tv_InstitutionalDeliveryfemale.setText("" + iValue3girl);
        String Sql3boy = "";
        Sql3boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.place_of_birth=2 and tblChild.Gender=2 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue3boy = 0;
        iValue3boy = dataprovider.getMaxRecord(Sql3boy);
        tv_InstitutionalDeliverymale.setText("" + iValue3boy);
        tv_InstitutionalDeliverytotal.setText("" + (iValue3girl + iValue3boy));
        String Sql4girl = "";
        Sql4girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.Wt_of_child < 2.5 and tblChild.Gender=1 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue4girl = 0;
        iValue4girl = dataprovider.getMaxRecord(Sql4girl);
        tv_Birthweightfemale.setText("" + iValue4girl);
        String Sql4boy = "";
        Sql4boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Wt_of_child < 2.5 and tblChild.Gender=2 and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue4boy = 0;
        iValue4boy = dataprovider.getMaxRecord(Sql4boy);
        tv_Birthweightmale.setText("" + iValue4boy);
        tv_Birthweighttotal.setText("" + (iValue4girl + iValue4boy));
        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where   cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int) in (0,1)   and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC.setText("" + iValue5dose);
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=3  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren.setText("" + iValue6child);
        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=2 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=3  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC.setText("" + iValue6dose);
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=7  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=3 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=7   and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC.setText("" + iValue7dose);
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=14  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=4 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=14  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC.setText("" + iValue8dose);
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=21  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=5 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=21  and  Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC.setText("" + iValue9dose);
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=28  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=6 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=28   and Tbl_HHSurvey.VillageID=" + VillageId + " and  tblChild.ANMID= " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC.setText("" + iValue10dose);
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=42  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=7 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=42   and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC.setText("" + iValue11dose);
        String Sql12dosegiven = "";
        Sql12dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.bcg is not null and tblChild.bcg!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue12dose = 0;
        iValue12dose = dataprovider.getMaxRecord(Sql12dosegiven);
        tv_BCGDose.setText("" + iValue12dose);
        tv_BCGchildren.setText("" + iValue5child);
        String Sql13dosegiven = "";
        Sql13dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv1 is not null and tblChild.opv1!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue13dose = 0;
        iValue13dose = dataprovider.getMaxRecord(Sql13dosegiven);
        tv_PolioDose.setText("" + iValue13dose);
        tv_Poliochildren.setText("" + iValue5child);
        String Sql14dosegiven = "";
        Sql14dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt1 is not null and tblChild.dpt1!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue14dose = 0;
        iValue14dose = dataprovider.getMaxRecord(Sql14dosegiven);
        tv_dpt1Dose.setText("" + iValue14dose);
        tv_dpt1children.setText("" + countchild(45, VillageId, anmid));
        String Sql15dosegiven = "";
        Sql15dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv2 is not null and tblChild.opv2!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue15dose = 0;
        iValue15dose = dataprovider.getMaxRecord(Sql15dosegiven);
        tv_PoliooneDose.setText("" + iValue15dose);
        tv_Polioonechildren.setText("" + countchild(45, VillageId, anmid));
        String Sql16dosegiven = "";
        Sql16dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb1 is not null and tblChild.hepb1!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue16dose = 0;
        iValue16dose = dataprovider.getMaxRecord(Sql16dosegiven);
        tv_HepB1Dose.setText("" + iValue16dose);
        tv_HepB1children.setText("" + countchild(45, VillageId, anmid));
        String Sql17dosegiven = "";
        Sql17dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent1 is not null and tblChild.Pentavalent1!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue17dose = 0;
        iValue17dose = dataprovider.getMaxRecord(Sql17dosegiven);
        tv_Pentavalent1Dose.setText("" + iValue17dose);
        tv_Pentavalent1children.setText("" + countchild(45, VillageId, anmid));
        String Sql18dosegiven = "";
        Sql18dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt2 is not null and tblChild.dpt2!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue18dose = 0;
        iValue18dose = dataprovider.getMaxRecord(Sql18dosegiven);
        tv_dpt2Dose.setText("" + iValue18dose);
        tv_dpt2children.setText("" + countchild(75, VillageId, anmid));
        String Sql19dosegiven = "";
        Sql19dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv3 is not null and tblChild.opv3!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue19dose = 0;
        iValue19dose = dataprovider.getMaxRecord(Sql19dosegiven);
        tv_PoliotwoDose.setText("" + iValue19dose);
        tv_Poliotwochildren.setText("" + countchild(75, VillageId, anmid));
        String Sql20dosegiven = "";
        Sql20dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb2 is not null and tblChild.hepb2!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue20dose = 0;
        iValue20dose = dataprovider.getMaxRecord(Sql20dosegiven);
        tv_HepBtwoDose.setText("" + iValue20dose);
        tv_HepBtwochildren.setText("" + countchild(75, VillageId, anmid));
        String Sql21dosegiven = "";
        Sql21dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent2 is not null and tblChild.Pentavalent2!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue21dose = 0;
        iValue21dose = dataprovider.getMaxRecord(Sql21dosegiven);
        tv_Pentavalent2Dose.setText("" + iValue21dose);
        tv_Pentavalent2children.setText("" + countchild(75, VillageId, anmid));
        String Sql22dosegiven = "";
        Sql22dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt3 is not null and tblChild.dpt3!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue22dose = 0;
        iValue22dose = dataprovider.getMaxRecord(Sql22dosegiven);
        tv_dpt3Dose.setText("" + iValue22dose);
        tv_dpt3children.setText("" + countchild(105, VillageId, anmid));

        String Sql23dosegiven = "";
        Sql23dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb3 is not null and tblChild.hepb3!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue23dose = 0;
        iValue23dose = dataprovider.getMaxRecord(Sql23dosegiven);
        tv_hep3Dose.setText("" + iValue23dose);
        tv_hep3children.setText("" + countchild(105, VillageId, anmid));
        String Sql24dosegiven = "";
        Sql24dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent2 is not null and tblChild.Pentavalent2!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue24dose = 0;
        iValue24dose = dataprovider.getMaxRecord(Sql24dosegiven);
        tv_Pentavalent3Dose.setText("" + iValue24dose);
        tv_Pentavalent3children.setText("" + countchild(105, VillageId, anmid));
        String Sql25dosegiven = "";
        Sql25dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.measeals is not null and tblChild.measeals!='')  and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.ANMID= " + anmid + "";
        int iValue25dose = 0;
        iValue25dose = dataprovider.getMaxRecord(Sql25dosegiven);
        tv_MeaslesDose.setText("" + iValue25dose);
        tv_Measleschildren.setText("" + countchild(270, VillageId, anmid));


    }

    public int countchild(int day, int villageID, int ashaid) {
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=" + day + "  and Tbl_HHSurvey.VillageID=" + villageID + " and    tblChild.ANMID = " + ashaid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        return iValue11child;
    }


    public void showAlldata() {
        String Sql1girl = "";
        Sql1girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Gender=1 and tblChild.ANMID= " + anmid + "";
        int iValue1girl = 0;
        iValue1girl = dataprovider.getMaxRecord(Sql1girl);
        tv_femalechildren.setText("" + iValue1girl);
        String Sql1boy = "";
        Sql1boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Gender=2 and tblChild.ANMID= " + anmid + "";
        int iValue1boy = 0;
        iValue1boy = dataprovider.getMaxRecord(Sql1boy);
        tv_malechildren.setText("" + iValue1boy);
        tv_totalchildren.setText("" + (iValue1girl + iValue1boy));
        String Sql2girl = "";
        Sql2girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.place_of_birth=1 and tblChild.Gender=1 and tblChild.ANMID= " + anmid + "";
        int iValue2girl = 0;
        iValue2girl = dataprovider.getMaxRecord(Sql2girl);
        tv_Homedeliveryfemale.setText("" + iValue2girl);
        String Sql2boy = "";
        Sql2boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.place_of_birth=1 and tblChild.Gender=2 and tblChild.ANMID= " + anmid + "";
        int iValue2boy = 0;
        iValue2boy = dataprovider.getMaxRecord(Sql2boy);
        tv_Homedeliverymale.setText("" + iValue2boy);
        tv_Homedeliverytotal.setText("" + (iValue2girl + iValue2boy));
        String Sql3girl = "";
        Sql3girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.place_of_birth=2 and tblChild.Gender=1 and tblChild.ANMID= " + anmid + "";
        int iValue3girl = 0;
        iValue3girl = dataprovider.getMaxRecord(Sql3girl);
        tv_InstitutionalDeliveryfemale.setText("" + iValue3girl);
        String Sql3boy = "";
        Sql3boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.place_of_birth=2 and tblChild.Gender=2 and tblChild.ANMID= " + anmid + "";
        int iValue3boy = 0;
        iValue3boy = dataprovider.getMaxRecord(Sql3boy);
        tv_InstitutionalDeliverymale.setText("" + iValue3boy);
        tv_InstitutionalDeliverytotal.setText("" + (iValue3girl + iValue3boy));
        String Sql4girl = "";
        Sql4girl = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.Wt_of_child < 2.5 and tblChild.Gender=1 and tblChild.ANMID= " + anmid + "";
        int iValue4girl = 0;
        iValue4girl = dataprovider.getMaxRecord(Sql4girl);
        tv_Birthweightfemale.setText("" + iValue4girl);
        String Sql4boy = "";
        Sql4boy = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  tblChild.Wt_of_child < 2.5 and tblChild.Gender=2 and tblChild.ANMID= " + anmid + "";
        int iValue4boy = 0;
        iValue4boy = dataprovider.getMaxRecord(Sql4boy);
        tv_Birthweightmale.setText("" + iValue4boy);
        tv_Birthweighttotal.setText("" + (iValue4girl + iValue4boy));
        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where tblChild.ANMID= " + anmid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        tv_0dayschildren.setText("" + iValue5child);
        String Sql5dosegiven = "";
        Sql5dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where   cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int) in (0,1)   and tblChild.ANMID= " + anmid + "";
        int iValue5dose = 0;
        iValue5dose = dataprovider.getMaxRecord(Sql5dosegiven);
        tv_0daysHBNC.setText("" + iValue5dose);
        String Sql6child = "";
        Sql6child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=3  and tblChild.ANMID= " + anmid + "";
        int iValue6child = 0;
        iValue6child = dataprovider.getMaxRecord(Sql6child);
        tv_3dayschildren.setText("" + iValue6child);
        String Sql6dosegiven = "";
        Sql6dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=2 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=3  and  tblChild.ANMID= " + anmid + "";
        int iValue6dose = 0;
        iValue6dose = dataprovider.getMaxRecord(Sql6dosegiven);
        tv_3daysHBNC.setText("" + iValue6dose);
        String Sql7child = "";
        Sql7child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=7  and tblChild.ANMID= " + anmid + "";
        int iValue7child = 0;
        iValue7child = dataprovider.getMaxRecord(Sql7child);
        tv_7dayschildren.setText("" + iValue7child);
        String Sql7dosegiven = "";
        Sql7dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=3 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=7   and tblChild.ANMID= " + anmid + "";
        int iValue7dose = 0;
        iValue7dose = dataprovider.getMaxRecord(Sql7dosegiven);
        tv_7daysHBNC.setText("" + iValue7dose);
        String Sql8child = "";
        Sql8child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=14  and tblChild.ANMID= " + anmid + "";
        int iValue8child = 0;
        iValue8child = dataprovider.getMaxRecord(Sql8child);
        tv_14dayschildren.setText("" + iValue8child);
        String Sql8dosegiven = "";
        Sql8dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=4 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=14  and  tblChild.ANMID= " + anmid + "";
        int iValue8dose = 0;
        iValue8dose = dataprovider.getMaxRecord(Sql8dosegiven);
        tv_14daysHBNC.setText("" + iValue8dose);
        String Sql9child = "";
        Sql9child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=21  and tblChild.ANMID= " + anmid + "";
        int iValue9child = 0;
        iValue9child = dataprovider.getMaxRecord(Sql9child);
        tv_21dayschildren.setText("" + iValue9child);
        String Sql9dosegiven = "";
        Sql9dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=5 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=21  and  tblChild.ANMID= " + anmid + "";
        int iValue9dose = 0;
        iValue9dose = dataprovider.getMaxRecord(Sql9dosegiven);
        tv_21daysHBNC.setText("" + iValue9dose);
        String Sql10child = "";
        Sql10child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=28  and tblChild.ANMID= " + anmid + "";
        int iValue10child = 0;
        iValue10child = dataprovider.getMaxRecord(Sql10child);
        tv_28dayschildren.setText("" + iValue10child);
        String Sql10dosegiven = "";
        Sql10dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=6 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=28   andtblChild.ANMID= " + anmid + "";
        int iValue10dose = 0;
        iValue10dose = dataprovider.getMaxRecord(Sql10dosegiven);
        tv_28daysHBNC.setText("" + iValue10dose);
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=42  and tblChild.ANMID= " + anmid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        tv_42dayschildren.setText("" + iValue11child);
        String Sql11dosegiven = "";
        Sql11dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join tblPNChomevisit_ANS on tblChild.ChildGUID=tblPNChomevisit_ANS.ChildGUID where tblPNChomevisit_ANS.VisitNo=7 and  cast(round(julianday(tblPNChomevisit_ANS.Q_0)-julianday(tblChild.CHILD_dob) ) as int)=42   and tblChild.ANMID= " + anmid + "";
        int iValue11dose = 0;
        iValue11dose = dataprovider.getMaxRecord(Sql11dosegiven);
        tv_42daysHBNC.setText("" + iValue11dose);
        String Sql12dosegiven = "";
        Sql12dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.bcg is not null and tblChild.bcg!='')  and tblChild.ANMID= " + anmid + "";
        int iValue12dose = 0;
        iValue12dose = dataprovider.getMaxRecord(Sql12dosegiven);
        tv_BCGDose.setText("" + iValue12dose);
        tv_BCGchildren.setText("" + iValue5child);
        String Sql13dosegiven = "";
        Sql13dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv1 is not null and tblChild.opv1!='')  and tblChild.ANMID= " + anmid + "";
        int iValue13dose = 0;
        iValue13dose = dataprovider.getMaxRecord(Sql13dosegiven);
        tv_PolioDose.setText("" + iValue13dose);
        tv_Poliochildren.setText("" + iValue5child);
        String Sql14dosegiven = "";
        Sql14dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt1 is not null and tblChild.dpt1!='')  and tblChild.ANMID= " + anmid + "";
        int iValue14dose = 0;
        iValue14dose = dataprovider.getMaxRecord(Sql14dosegiven);
        tv_dpt1Dose.setText("" + iValue14dose);
        tv_dpt1children.setText("" + countAllchild(45, anmid));
        String Sql15dosegiven = "";
        Sql15dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv2 is not null and tblChild.opv2!='')  and tblChild.ANMID= " + anmid + "";
        int iValue15dose = 0;
        iValue15dose = dataprovider.getMaxRecord(Sql15dosegiven);
        tv_PoliooneDose.setText("" + iValue15dose);
        tv_Polioonechildren.setText("" + countAllchild(45, anmid));
        String Sql16dosegiven = "";
        Sql16dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb1 is not null and tblChild.hepb1!='')  and tblChild.ANMID= " + anmid + "";
        int iValue16dose = 0;
        iValue16dose = dataprovider.getMaxRecord(Sql16dosegiven);
        tv_HepB1Dose.setText("" + iValue16dose);
        tv_HepB1children.setText("" + countAllchild(4, anmid));
        String Sql17dosegiven = "";
        Sql17dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent1 is not null and tblChild.Pentavalent1!='')  and tblChild.ANMID= " + anmid + "";
        int iValue17dose = 0;
        iValue17dose = dataprovider.getMaxRecord(Sql17dosegiven);
        tv_Pentavalent1Dose.setText("" + iValue17dose);
        tv_Pentavalent1children.setText("" + countAllchild(45, anmid));
        String Sql18dosegiven = "";
        Sql18dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt2 is not null and tblChild.dpt2!='')  and tblChild.ANMID= " + anmid + "";
        int iValue18dose = 0;
        iValue18dose = dataprovider.getMaxRecord(Sql18dosegiven);
        tv_dpt2Dose.setText("" + iValue18dose);
        tv_dpt2children.setText("" + countAllchild(75, anmid));
        String Sql19dosegiven = "";
        Sql19dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.opv3 is not null and tblChild.opv3!='')  and tblChild.ANMID= " + anmid + "";
        int iValue19dose = 0;
        iValue19dose = dataprovider.getMaxRecord(Sql19dosegiven);
        tv_PoliotwoDose.setText("" + iValue19dose);
        tv_Poliotwochildren.setText("" + countAllchild(75, anmid));
        String Sql20dosegiven = "";
        Sql20dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb2 is not null and tblChild.hepb2!='')  and tblChild.ANMID= " + anmid + "";
        int iValue20dose = 0;
        iValue20dose = dataprovider.getMaxRecord(Sql20dosegiven);
        tv_HepBtwoDose.setText("" + iValue20dose);
        tv_HepBtwochildren.setText("" + countAllchild(75, anmid));
        String Sql21dosegiven = "";
        Sql21dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent2 is not null and tblChild.Pentavalent2!='')  and tblChild.ANMID= " + anmid + "";
        int iValue21dose = 0;
        iValue21dose = dataprovider.getMaxRecord(Sql21dosegiven);
        tv_Pentavalent2Dose.setText("" + iValue21dose);
        tv_Pentavalent2children.setText("" + countAllchild(75, anmid));
        String Sql22dosegiven = "";
        Sql22dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.dpt3 is not null and tblChild.dpt3!='')  and tblChild.ANMID= " + anmid + "";
        int iValue22dose = 0;
        iValue22dose = dataprovider.getMaxRecord(Sql22dosegiven);
        tv_dpt3Dose.setText("" + iValue22dose);
        tv_dpt3children.setText("" + countAllchild(105, anmid));

        String Sql23dosegiven = "";
        Sql23dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.hepb3 is not null and tblChild.hepb3!='')  and tblChild.ANMID= " + anmid + "";
        int iValue23dose = 0;
        iValue23dose = dataprovider.getMaxRecord(Sql23dosegiven);
        tv_hep3Dose.setText("" + iValue23dose);
        tv_hep3children.setText("" + countAllchild(105, anmid));
        String Sql24dosegiven = "";
        Sql24dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.Pentavalent2 is not null and tblChild.Pentavalent2!='')  and tblChild.ANMID= " + anmid + "";
        int iValue24dose = 0;
        iValue24dose = dataprovider.getMaxRecord(Sql24dosegiven);
        tv_Pentavalent3Dose.setText("" + iValue24dose);
        tv_Pentavalent3children.setText("" + countAllchild(105, anmid));
        String Sql25dosegiven = "";
        Sql25dosegiven = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where  (tblChild.measeals is not null and tblChild.measeals!='')  and tblChild.ANMID= " + anmid + "";
        int iValue25dose = 0;
        iValue25dose = dataprovider.getMaxRecord(Sql25dosegiven);
        tv_MeaslesDose.setText("" + iValue25dose);
        tv_Measleschildren.setText("" + countAllchild(270, anmid));


    }

    public int countAllchild(int day, int ashaid) {
        String Sql11child = "";
        Sql11child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where cast(round(julianday('Now')-julianday(tblChild.CHILD_dob) ) as int)>=" + day + " and    tblChild.ANMID = " + ashaid + "";
        int iValue11child = 0;
        iValue11child = dataprovider.getMaxRecord(Sql11child);
        return iValue11child;
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


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Reportindicator_anmHnbclist.this,
                Report_Module.class);
        finish();
        startActivity(i);
    }

}
