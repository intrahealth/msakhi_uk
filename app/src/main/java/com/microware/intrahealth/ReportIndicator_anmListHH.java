package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

public class ReportIndicator_anmListHH extends Activity {

    TextView textview1, textview2, tv_village,
            tv_totalpopulationmale, tv_totalpopulationfemale, tv_totalhhmale, tv_totalhhfemale, tv_0_6_monthsmale, tv_0_6_monthsfemale, tv_0_6_monthstotal, tv_7_12_monthsmale, tv_7_12_monthsfemale, tv_7_12_monthstotal, tv_1_2_yrsmale, tv_1_2_yrsfemale, tv_1_2_yrstotal, tv_3_5_yrsmale, tv_3_5_yrsfemale, tv_3_5_yrstotal, tv_5_6_yrsmale, tv_5_6_yrsfemale, tv_5_6_yrstotal, tv_7_14_yrsmale, tv_7_14_yrsfemale, tv_7_14_yrstotal, tv_15_49_yrsmale, tv_15_49_yrsfemale, tv_15_49_yrstotal, tv_50_60_yrsmale, tv_50_60_yrsfemale, tv_50_60_yrstotal, tv_60yearmale, tv_60yearfemale, textview11, tv_migratedmale, tv_migratedfemale, tv_migratedtot, tv_deathmale, tv_deathfemale, tv_deathtot;

    Button btndetails;
    Global global;
    DataProvider dataProvider;
    LinearLayout ll_jhhide;
    int anmid = 0, VillageID = 0;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    TableRow tbl_btndetails;
    Validate validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportindicatorlisthh);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);

        global = (Global) getApplication();
        setTitle(global.getVersionName());
        ll_jhhide = (LinearLayout) findViewById(R.id.ll_jhhide);
        btndetails = (Button) findViewById(R.id.btndetails);
        tbl_btndetails = (TableRow) findViewById(R.id.tbl_btndetails);
        tv_village = (TextView) findViewById(R.id.tv_village);
        tv_deathmale = (TextView) findViewById(R.id.tv_deathmale);
        tv_deathfemale = (TextView) findViewById(R.id.tv_deathfemale);
        tv_deathtot = (TextView) findViewById(R.id.tv_deathtot);
        tv_migratedmale = (TextView) findViewById(R.id.tv_migratedmale);
        tv_migratedfemale = (TextView) findViewById(R.id.tv_migratedfemale);
        tv_migratedtot = (TextView) findViewById(R.id.tv_migratedtot);
        textview1 = (TextView) findViewById(R.id.textview1);
        textview2 = (TextView) findViewById(R.id.textview2);
        tv_totalhhmale = (TextView) findViewById(R.id.tv_totalhhmale);
        tv_totalhhfemale = (TextView) findViewById(R.id.tv_totalhhfemale);
        tv_0_6_monthsmale = (TextView) findViewById(R.id.tv_0_6_monthsmale);
        tv_0_6_monthsfemale = (TextView) findViewById(R.id.tv_0_6_monthsfemale);
        tv_0_6_monthstotal = (TextView) findViewById(R.id.tv_0_6_monthstotal);
        tv_7_12_monthsmale = (TextView) findViewById(R.id.tv_7_12_monthsmale);
        tv_7_12_monthsfemale = (TextView) findViewById(R.id.tv_7_12_monthsfemale);
        tv_7_12_monthstotal = (TextView) findViewById(R.id.tv_7_12_monthstotal);
        tv_1_2_yrsmale = (TextView) findViewById(R.id.tv_1_2_yrsmale);
        tv_1_2_yrsfemale = (TextView) findViewById(R.id.tv_1_2_yrsfemale);
        tv_1_2_yrstotal = (TextView) findViewById(R.id.tv_1_2_yrstotal);
        tv_3_5_yrsmale = (TextView) findViewById(R.id.tv_3_5_yrsmale);
        tv_3_5_yrsfemale = (TextView) findViewById(R.id.tv_3_5_yrsfemale);
        tv_3_5_yrstotal = (TextView) findViewById(R.id.tv_3_5_yrstotal);
        tv_5_6_yrsmale = (TextView) findViewById(R.id.tv_5_6_yrsmale);
        tv_5_6_yrsfemale = (TextView) findViewById(R.id.tv_5_6_yrsfemale);
        tv_5_6_yrstotal = (TextView) findViewById(R.id.tv_5_6_yrstotal);
        tv_7_14_yrsmale = (TextView) findViewById(R.id.tv_7_14_yrsmale);
        tv_7_14_yrsfemale = (TextView) findViewById(R.id.tv_7_14_yrsfemale);
        tv_7_14_yrstotal = (TextView) findViewById(R.id.tv_7_14_yrstotal);
        tv_15_49_yrsmale = (TextView) findViewById(R.id.tv_15_49_yrsmale);
        tv_15_49_yrsfemale = (TextView) findViewById(R.id.tv_15_49_yrsfemale);
        tv_15_49_yrstotal = (TextView) findViewById(R.id.tv_15_49_yrstotal);
        tv_50_60_yrsmale = (TextView) findViewById(R.id.tv_50_60_yrsmale);
        tv_50_60_yrsfemale = (TextView) findViewById(R.id.tv_50_60_yrsfemale);
        tv_50_60_yrstotal = (TextView) findViewById(R.id.tv_50_60_yrstotal);
        textview11 = (TextView) findViewById(R.id.textview11);
        tv_totalpopulationmale = (TextView) findViewById(R.id.tv_totalpopulationmale);
        tv_totalpopulationfemale = (TextView) findViewById(R.id.tv_totalpopulationfemale);
        tv_60yearmale = (TextView) findViewById(R.id.tv_60yearmale);
        tv_60yearfemale = (TextView) findViewById(R.id.tv_60yearfemale);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        tbl_btndetails.setVisibility(View.VISIBLE);
        spinVillageName.setVisibility(View.GONE);
        tv_village.setVisibility(View.GONE);
        btndetails.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(ReportIndicator_anmListHH.this,
                        AshaDashboardListHH.class);
                finish();
                startActivity(i);
            }
        });

        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            anmid = Integer.valueOf(global.getsGlobalANMCODE());
        }
        int statecode = 0;
        if (global.getStateCode() != null
                && global.getStateCode().length() > 0) {

            statecode = Integer.valueOf(global.getStateCode());
        }
        if (statecode == 20) {

            ll_jhhide.setVisibility(View.GONE);

        }
//        fillVillageName(global.getLanguage());
//        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//
//                if (pos > 0) {
//                    VillageID = Village.get(
//                            spinVillageName.getSelectedItemPosition() - 1)
//                            .getVillageID();
//                    showData(VillageID);
//
//                } else {
//                    VillageID = 0;
//                    showAllData();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        showAllData();
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

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalANMCODE(), Language, 1);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    /* public void showData(int villageID) {
         try {
             String sql1 = "";
             sql1 = "select  count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember  on Tbl_HHSurvey.HHSurveyGUID=Tbl_HHFamilyMember.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  Tbl_HHFamilyMember.StatusID=1 and   Tbl_HHFamilyMember.GenderID=1 and  Tbl_HHSurvey.VillageID='" + villageID + "' and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue1 = 0;
             ivalue1 = dataProvider.getMaxRecord(sql1);
             tv_totalpopulationmale.setText(String.valueOf(ivalue1));
             String sql1female = "";
             sql1female = "select  count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember  on Tbl_HHSurvey.HHSurveyGUID=Tbl_HHFamilyMember.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   Tbl_HHFamilyMember.StatusID=1 and   Tbl_HHFamilyMember.GenderID=2 and  Tbl_HHSurvey.VillageID='" + villageID + "' and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue1female = 0;
             ivalue1female = dataProvider.getMaxRecord(sql1female);
             tv_totalpopulationfemale.setText(String.valueOf(ivalue1female));
             textview1.setText(String.valueOf(ivalue1 + ivalue1female));
             String sql2 = "";
             sql2 = "select count(*) from Tbl_HHSurvey where Tbl_HHSurvey.IsActive=1 and   Tbl_HHSurvey.VillageID='" + villageID + "' and ANMID= " + anmid + "";
             int ivalue2 = 0;
             ivalue2 = dataProvider.getMaxRecord(sql2);
             textview2.setText(String.valueOf(ivalue2));


             String sql3 = "";
             sql3 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   Tbl_HHSurvey.VillageID='" + villageID + "' and  member.Genderid=2 and  member.StatusID=1 and  Tbl_HHSurvey.ANMID= " + anmid + " and (((Date('now') ) - (DATE(member.DateOfBirth)) >=15 and (Date('now') ) - (DATE(member.DateOfBirth)) <=49) or (AprilAgeYear >=15 and AprilAgeYear<=49) )";
             int ivalue3 = 0;
             ivalue3 = dataProvider.getMaxRecord(sql3);
             textview3.setText(String.valueOf(ivalue3));
             tv_15to49female.setText(String.valueOf(ivalue3));


             String sql4 = "";
             sql4 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.StatusID=1 and   member.Gender=1 and Tbl_HHSurvey.VillageID='" + villageID + "' and (julianday('now')  - julianday(member.child_dob))<=180 and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue4 = 0;
             ivalue4 = dataProvider.getMaxRecord(sql4);
             tv_0to6monthfemale.setText(String.valueOf(ivalue4));

             String sql4boy = "";
             sql4boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.StatusID=1 and    member.Gender=2 and Tbl_HHSurvey.VillageID='" + villageID + "' and (julianday('now')  - julianday(member.child_dob))<=180 and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue4boy = 0;
             ivalue4boy = dataProvider.getMaxRecord(sql4boy);
             tv_0to6monthmale.setText(String.valueOf(ivalue4boy));
             textview4.setText(String.valueOf(ivalue4 + ivalue4boy));

             String sql5 = "";
             sql5 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=1 and  Tbl_HHSurvey.VillageID='" + villageID + "' and (julianday('now')  - julianday(member.child_dob)) between 180 and 365 and Tbl_HHSurvey.ANMID= " + anmid + "";

             int ivalue5 = 0;
             ivalue5 = dataProvider.getMaxRecord(sql5);
             tv_6to1yearfemale.setText(String.valueOf(ivalue5));
             String sql5boy = "";
             sql5boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=2 and  Tbl_HHSurvey.VillageID='" + villageID + "' and (julianday('now')  - julianday(member.child_dob)) between 180 and 365 and Tbl_HHSurvey.ANMID= " + anmid + "";

             int ivalue5boy = 0;
             ivalue5boy = dataProvider.getMaxRecord(sql5boy);
             tv_6to1yearmale.setText(String.valueOf(ivalue5boy));
             textview5.setText(String.valueOf(ivalue5 + ivalue5boy));
             String sql6 = "";
             sql6 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=1 and   Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 0 and 2 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue6 = 0;
             ivalue6 = dataProvider.getMaxRecord(sql6);
             tv_0to2yearfemale.setText(String.valueOf(ivalue6));
             String sql6boy = "";
             sql6boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=2 and   Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 0 and 2 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue6boy = 0;
             ivalue6boy = dataProvider.getMaxRecord(sql6boy);
             tv_0to2yearmale.setText(String.valueOf(ivalue6boy));
             textview6.setText(String.valueOf(ivalue6 + ivalue6boy));
             String sql7 = "";
             sql7 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=1 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 1 and 3 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue7 = 0;
             ivalue7 = dataProvider.getMaxRecord(sql7);
             tv_1to3yearfemale.setText(String.valueOf(ivalue7));
             String sql7boy = "";
             sql7boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=2 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 1 and 3 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue7boy = 0;
             ivalue7boy = dataProvider.getMaxRecord(sql7boy);
             tv_1to3yearmale.setText(String.valueOf(ivalue7boy));
             textview7.setText(String.valueOf(ivalue7 + ivalue7boy));
             String sql8 = "";
             sql8 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=1 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between  4 and 6 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue8 = 0;
             ivalue8 = dataProvider.getMaxRecord(sql8);
             tv_4to6yearfemale.setText(String.valueOf(ivalue8));
             String sql8boy = "";
             sql8boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=2 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between  4 and 6 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue8boy = 0;
             ivalue8boy = dataProvider.getMaxRecord(sql8boy);
             tv_4to6yearmale.setText(String.valueOf(ivalue8boy));
             textview8.setText(String.valueOf(ivalue8 + ivalue8boy));
             String sql9 = "";
             sql9 = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=1 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 0 and 6 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue9 = 0;
             ivalue9 = dataProvider.getMaxRecord(sql9);
             tv_0to6yearfemale.setText(String.valueOf(ivalue9));
             String sql9boy = "";
             sql9boy = "select count(*) from Tbl_HHSurvey inner join tblchild member on Tbl_HHSurvey.HHSurveyGUID=member.HHGUID where Tbl_HHSurvey.IsActive=1 and   member.Gender=2 and Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.child_dob)) ) between 0 and 6 ) )  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue9boy = 0;
             ivalue9boy = dataProvider.getMaxRecord(sql9boy);
             tv_0to6yearmale.setText(String.valueOf(ivalue9boy));
             textview9.setText(String.valueOf(ivalue9 + ivalue9boy));
             String sql10 = "";
             sql10 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   member.StatusID=1 and   member.GenderID=1 and   Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 35 and 59 ) or (AprilAgeYear >=35 and AprilAgeYear<59)) and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue10 = 0;
             ivalue10 = dataProvider.getMaxRecord(sql10);
             tv_35to50yearmale.setText(String.valueOf(ivalue10));
             String sql10female = "";
             sql10female = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.StatusID=1 and    member.GenderID=2 and   Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) between 35 and 59 ) or (AprilAgeYear >=35 and AprilAgeYear<59)) and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue10female = 0;
             ivalue10female = dataProvider.getMaxRecord(sql10female);
             tv_35to50yearfemale.setText(String.valueOf(ivalue10female));
             textview10.setText(String.valueOf(ivalue10 + ivalue10female));
             String sql11 = "";
             sql11 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.StatusID=1 and    member.GenderID=1 and  Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) >=60 ) or (AprilAgeYear >=60 )) and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue11 = 0;
             ivalue11 = dataProvider.getMaxRecord(sql11);
             tv_60yearmale.setText(String.valueOf(ivalue11));
             String sql11female = "";
             sql11female = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.StatusID=1 and    member.GenderID=2 and  Tbl_HHSurvey.VillageID='" + villageID + "' and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) >=60 ) or (AprilAgeYear >=60 )) and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue11female = 0;
             ivalue11female = dataProvider.getMaxRecord(sql11female);
             tv_60yearfemale.setText(String.valueOf(ivalue11female));
             textview11.setText(String.valueOf(ivalue11 + ivalue11female));
             String sql12 = "";
             sql12 = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   member.statusId=3 and member.GenderID=1 and   Tbl_HHSurvey.VillageID='" + villageID + "'  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue12 = 0;
             ivalue12 = dataProvider.getMaxRecord(sql12);
             tv_migratedmale.setText(String.valueOf(ivalue12));
             String sql12female = "";
             sql12female = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   member.statusId=3 and member.GenderID=2 and   Tbl_HHSurvey.VillageID='" + villageID + "'  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue12female = 0;
             ivalue12female = dataProvider.getMaxRecord(sql12female);
             tv_migratedfemale.setText(String.valueOf(ivalue12female));
             tv_migratedtot.setText(String.valueOf(ivalue12 + ivalue12female));
             String sql13 = "";
             sql13 = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   member.statusId=2 and member.GenderID=1 and   Tbl_HHSurvey.VillageID='" + villageID + "'  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue13 = 0;
             ivalue13 = dataProvider.getMaxRecord(sql13);
             tv_deathmale.setText(String.valueOf(ivalue13));
             String sql13female = "";
             sql13female = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and   member.statusId=2 and member.GenderID=2 and   Tbl_HHSurvey.VillageID='" + villageID + "'  and Tbl_HHSurvey.ANMID= " + anmid + "";
             int ivalue13female = 0;
             ivalue13female = dataProvider.getMaxRecord(sql13female);
             tv_deathfemale.setText(String.valueOf(ivalue13female));
             tv_deathtot.setText(String.valueOf(ivalue13 + ivalue13female));
         } catch (Exception e) {
             e.printStackTrace();
         }
     }
    */
    public void showAllData() {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember  on Tbl_HHSurvey.HHSurveyGUID=Tbl_HHFamilyMember.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and Tbl_HHFamilyMember.StatusID=1 and   Tbl_HHFamilyMember.GenderID=1 and Tbl_HHSurvey.ANMID= " + anmid + " ";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tv_totalpopulationmale.setText(String.valueOf(ivalue1));
            String sql1female = "";
            sql1female = "select  count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember  on Tbl_HHSurvey.HHSurveyGUID=Tbl_HHFamilyMember.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  Tbl_HHFamilyMember.StatusID=1 and Tbl_HHFamilyMember.GenderID=2 and Tbl_HHSurvey.ANMID= " + anmid + " ";
            int ivalue1female = 0;
            ivalue1female = dataProvider.getMaxRecord(sql1female);
            tv_totalpopulationfemale.setText(String.valueOf(ivalue1female));
            textview1.setText(String.valueOf(ivalue1 + ivalue1female));
            String sql2 = "";
            sql2 = "select count(*) from Tbl_HHSurvey where ANMID= " + anmid + "";
            int ivalue2 = 0;
            ivalue2 = dataProvider.getMaxRecord(sql2);
            textview2.setText(String.valueOf(ivalue2));


            String sql4 = "";
            sql4 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  0 and .5";
            int ivalue4 = 0;
            ivalue4 = dataProvider.getMaxRecord(sql4);
            tv_0_6_monthsfemale.setText(String.valueOf(ivalue4));

            String sql4boy = "";
            sql4boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  0 and .5";
            int ivalue4boy = 0;
            ivalue4boy = dataProvider.getMaxRecord(sql4boy);
            tv_0_6_monthsmale.setText(String.valueOf(ivalue4boy));
            tv_0_6_monthstotal.setText(String.valueOf(ivalue4 + ivalue4boy));

            String sql5 = "";
            sql5 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  .5 and 1";

            int ivalue5 = 0;
            ivalue5 = dataProvider.getMaxRecord(sql5);
            tv_7_12_monthsfemale.setText(String.valueOf(ivalue5));
            String sql5boy = "";
            sql5boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  .5 and 1";

            int ivalue5boy = 0;
            ivalue5boy = dataProvider.getMaxRecord(sql5boy);
            tv_7_12_monthsmale.setText(String.valueOf(ivalue5boy));
            tv_7_12_monthstotal.setText(String.valueOf(ivalue5 + ivalue5boy));
            String sql6 = "";
            sql6 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  1 and 2";

            int ivalue6 = 0;
            ivalue6 = dataProvider.getMaxRecord(sql6);
            tv_1_2_yrsfemale.setText(String.valueOf(ivalue6));
            String sql6boy = "";
            sql6boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  1 and 2";

            int ivalue6boy = 0;
            ivalue6boy = dataProvider.getMaxRecord(sql6boy);
            tv_1_2_yrsmale.setText(String.valueOf(ivalue6boy));
            tv_1_2_yrstotal.setText(String.valueOf(ivalue6 + ivalue6boy));
            String sql7 = "";
            sql7 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 3 and 5";

            int ivalue7 = 0;
            ivalue7 = dataProvider.getMaxRecord(sql7);
            tv_3_5_yrsfemale.setText(String.valueOf(ivalue7));
            String sql7boy = "";
            sql7boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between  3 and 5";

            int ivalue7boy = 0;
            ivalue7boy = dataProvider.getMaxRecord(sql7boy);
            tv_3_5_yrsmale.setText(String.valueOf(ivalue7boy));
            tv_3_5_yrstotal.setText(String.valueOf(ivalue7 + ivalue7boy));
            String sql8 = "";
            sql8 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 5 and 6";
            int ivalue8 = 0;
            ivalue8 = dataProvider.getMaxRecord(sql8);
            tv_5_6_yrsfemale.setText(String.valueOf(ivalue8));
            String sql8boy = "";
            sql8boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 5 and 6";
            int ivalue8boy = 0;
            ivalue8boy = dataProvider.getMaxRecord(sql8boy);
            tv_5_6_yrsmale.setText(String.valueOf(ivalue8boy));
            tv_5_6_yrstotal.setText(String.valueOf(ivalue8 + ivalue8boy));
            String sql9 = "";
            sql9 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 7 and 14";
            int ivalue9 = 0;
            ivalue9 = dataProvider.getMaxRecord(sql9);
            tv_7_14_yrsfemale.setText(String.valueOf(ivalue9));
            String sql9boy = "";
            sql9boy = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 7 and 14";
            int ivalue9boy = 0;
            ivalue9boy = dataProvider.getMaxRecord(sql9boy);
            tv_7_14_yrsmale.setText(String.valueOf(ivalue9boy));
            tv_7_14_yrstotal.setText(String.valueOf(ivalue9 + ivalue9boy));
            String sql10 = "";
            sql10 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 15 and 49";
            int ivalue10 = 0;
            ivalue10 = dataProvider.getMaxRecord(sql10);
            tv_15_49_yrsfemale.setText(String.valueOf(ivalue10));
            String sql10female = "";
            sql10female = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 15 and 49";
            int ivalue10female = 0;
            ivalue10female = dataProvider.getMaxRecord(sql10female);
            tv_15_49_yrsmale.setText(String.valueOf(ivalue10female));
            tv_15_49_yrstotal.setText(String.valueOf(ivalue10 + ivalue10female));
            String sql14 = "";
            sql14 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=2 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 50 and 60";
            int ivalue14 = 0;
            ivalue14 = dataProvider.getMaxRecord(sql14);
            tv_50_60_yrsfemale.setText(String.valueOf(ivalue14));
            String sql14female = "";
            sql14female = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1  and  member.StatusID=1 and member.Genderid=1 and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end between 50 and 60";
            int ivalue14female = 0;
            ivalue14female = dataProvider.getMaxRecord(sql14female);
            tv_50_60_yrsmale.setText(String.valueOf(ivalue14female));
            tv_50_60_yrstotal.setText(String.valueOf(ivalue14 + ivalue14female));
            String sql11 = "";
            sql11 = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and member.StatusID=1 and   member.GenderID=1  and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) >=60 ) or (AprilAgeYear >=60 )) and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end  > 60";
            int ivalue11 = 0;
            ivalue11 = dataProvider.getMaxRecord(sql11);
            tv_60yearmale.setText(String.valueOf(ivalue11));
            String sql11female = "";
            sql11female = "select count(*) from Tbl_HHSurvey inner join Tbl_HHFamilyMember member on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.StatusID=1 and  member.GenderID=2  and ((((Date('Now') ) - (DATE(member.DateOfBirth)) ) >=60 ) or (AprilAgeYear >=60 )) and Tbl_HHSurvey.ANMID= " + anmid + " and case when member.DOBAvailable=2 then member.AprilAgeYear+(strftime('%Y', 'now')-member.AgeAsOnYear) when member.DOBAvailable=1 then (julianday('NOW')-julianday(member.DateOfBirth))/365 end  > 60";
            int ivalue11female = 0;
            ivalue11female = dataProvider.getMaxRecord(sql11female);
            tv_60yearfemale.setText(String.valueOf(ivalue11female));
            textview11.setText(String.valueOf(ivalue11 + ivalue11female));
            String sql12 = "";
            sql12 = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.statusId=3 and member.GenderID=1 and Tbl_HHSurvey.ANMID= " + anmid + "  ";
            int ivalue12 = 0;
            ivalue12 = dataProvider.getMaxRecord(sql12);
            tv_migratedmale.setText(String.valueOf(ivalue12));
            String sql12female = "";
            sql12female = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.statusId=3 and member.GenderID=2 and Tbl_HHSurvey.ANMID= " + anmid + " ";
            int ivalue12female = 0;
            ivalue12female = dataProvider.getMaxRecord(sql12female);
            tv_migratedfemale.setText(String.valueOf(ivalue12female));
            tv_migratedtot.setText(String.valueOf(ivalue12 + ivalue12female));
            String sql13 = "";
            sql13 = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.statusId=2 and member.GenderID=1 and Tbl_HHSurvey.ANMID= " + anmid + "";
            int ivalue13 = 0;
            ivalue13 = dataProvider.getMaxRecord(sql13);
            tv_deathmale.setText(String.valueOf(ivalue13));
            String sql13female = "";
            sql13female = "select count(*) from  Tbl_HHFamilyMember member inner join  Tbl_HHSurvey  on Tbl_HHSurvey.HHSurveyGUID=member.HHSurveyGUID where Tbl_HHSurvey.IsActive=1 and  member.statusId=2 and member.GenderID=2 and Tbl_HHSurvey.ANMID= " + anmid + "";
            int ivalue13female = 0;
            ivalue13female = dataProvider.getMaxRecord(sql13female);
            tv_deathfemale.setText(String.valueOf(ivalue13female));
            tv_deathtot.setText(String.valueOf(ivalue13 + ivalue13female));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(ReportIndicator_anmListHH.this,
                Report_Module.class);
        finish();
        startActivity(i);
    }

}
