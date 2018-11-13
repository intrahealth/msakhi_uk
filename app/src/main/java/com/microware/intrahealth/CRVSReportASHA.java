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

public class CRVSReportASHA extends Activity {

    TextView tv_year1, tv_month1, tv_birth1, tv_death1, tv_sbirth1, tv_year2, tv_month2, tv_birth2, tv_death2, tv_sbirth2, tv_year3, tv_month3, tv_birth3, tv_death3, tv_sbirth3, tv_year4, tv_month4, tv_birth4, tv_death4, tv_sbirth4, tv_year5, tv_month5, tv_birth5, tv_death5, tv_sbirth5, tv_year6, tv_month6, tv_birth6, tv_death6, tv_sbirth6, tv_year7, tv_month7, tv_birth7, tv_death7, tv_sbirth7, tv_year8, tv_month8, tv_birth8, tv_death8, tv_sbirth8, tv_year9, tv_month9, tv_birth9, tv_death9, tv_sbirth9, tv_year10, tv_month10, tv_birth10, tv_death10, tv_sbirth10, tv_year11, tv_month11, tv_birth11, tv_death11, tv_sbirth11, tv_year12, tv_month12, tv_birth12, tv_death12, tv_sbirth12;
    DataProvider dataprovider;


    Global global;
    int ashaid = 0, VillageID = 0, flag = 0;

    Spinner spinVillageName, spin_year;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    Validate validate;
    String[] year = {"2016", "2017", "2018", "2019", "2020"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crvsreportasha);
        dataprovider = new DataProvider(this);
        validate = new Validate(this);

        global = (Global) getApplication();
        setTitle(global.getVersionName());
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        spin_year = (Spinner) findViewById(R.id.spin_year);

        tv_year1 = (TextView) findViewById(R.id.tv_year1);
        tv_month1 = (TextView) findViewById(R.id.tv_month1);
        tv_birth1 = (TextView) findViewById(R.id.tv_birth1);
        tv_death1 = (TextView) findViewById(R.id.tv_death1);
        tv_sbirth1 = (TextView) findViewById(R.id.tv_sbirth1);
        tv_year2 = (TextView) findViewById(R.id.tv_year2);
        tv_month2 = (TextView) findViewById(R.id.tv_month2);
        tv_birth2 = (TextView) findViewById(R.id.tv_birth2);
        tv_death2 = (TextView) findViewById(R.id.tv_death2);
        tv_sbirth2 = (TextView) findViewById(R.id.tv_sbirth2);
        tv_year3 = (TextView) findViewById(R.id.tv_year3);
        tv_month3 = (TextView) findViewById(R.id.tv_month3);
        tv_birth3 = (TextView) findViewById(R.id.tv_birth3);
        tv_death3 = (TextView) findViewById(R.id.tv_death3);
        tv_sbirth3 = (TextView) findViewById(R.id.tv_sbirth3);
        tv_year4 = (TextView) findViewById(R.id.tv_year4);
        tv_month4 = (TextView) findViewById(R.id.tv_month4);
        tv_birth4 = (TextView) findViewById(R.id.tv_birth4);
        tv_death4 = (TextView) findViewById(R.id.tv_death4);
        tv_sbirth4 = (TextView) findViewById(R.id.tv_sbirth4);
        tv_year5 = (TextView) findViewById(R.id.tv_year5);
        tv_month5 = (TextView) findViewById(R.id.tv_month5);
        tv_birth5 = (TextView) findViewById(R.id.tv_birth5);
        tv_death5 = (TextView) findViewById(R.id.tv_death5);
        tv_sbirth5 = (TextView) findViewById(R.id.tv_sbirth5);
        tv_year6 = (TextView) findViewById(R.id.tv_year6);
        tv_month6 = (TextView) findViewById(R.id.tv_month6);
        tv_birth6 = (TextView) findViewById(R.id.tv_birth6);
        tv_death6 = (TextView) findViewById(R.id.tv_death6);
        tv_sbirth6 = (TextView) findViewById(R.id.tv_sbirth6);
        tv_year7 = (TextView) findViewById(R.id.tv_year7);
        tv_month7 = (TextView) findViewById(R.id.tv_month7);
        tv_birth7 = (TextView) findViewById(R.id.tv_birth7);
        tv_death7 = (TextView) findViewById(R.id.tv_death7);
        tv_sbirth7 = (TextView) findViewById(R.id.tv_sbirth7);
        tv_year8 = (TextView) findViewById(R.id.tv_year8);
        tv_month8 = (TextView) findViewById(R.id.tv_month8);
        tv_birth8 = (TextView) findViewById(R.id.tv_birth8);
        tv_death8 = (TextView) findViewById(R.id.tv_death8);
        tv_sbirth8 = (TextView) findViewById(R.id.tv_sbirth8);
        tv_year9 = (TextView) findViewById(R.id.tv_year9);
        tv_month9 = (TextView) findViewById(R.id.tv_month9);
        tv_birth9 = (TextView) findViewById(R.id.tv_birth9);
        tv_death9 = (TextView) findViewById(R.id.tv_death9);
        tv_sbirth9 = (TextView) findViewById(R.id.tv_sbirth9);
        tv_year10 = (TextView) findViewById(R.id.tv_year10);
        tv_month10 = (TextView) findViewById(R.id.tv_month10);
        tv_birth10 = (TextView) findViewById(R.id.tv_birth10);
        tv_death10 = (TextView) findViewById(R.id.tv_death10);
        tv_sbirth10 = (TextView) findViewById(R.id.tv_sbirth10);
        tv_year11 = (TextView) findViewById(R.id.tv_year11);
        tv_month11 = (TextView) findViewById(R.id.tv_month11);
        tv_birth11 = (TextView) findViewById(R.id.tv_birth11);
        tv_death11 = (TextView) findViewById(R.id.tv_death11);
        tv_sbirth11 = (TextView) findViewById(R.id.tv_sbirth11);
        tv_year12 = (TextView) findViewById(R.id.tv_year12);
        tv_month12 = (TextView) findViewById(R.id.tv_month12);
        tv_birth12 = (TextView) findViewById(R.id.tv_birth12);
        tv_death12 = (TextView) findViewById(R.id.tv_death12);
        tv_sbirth12 = (TextView) findViewById(R.id.tv_sbirth12);

        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            flag = extras.getInt("flag");
        fillVillageName(global.getLanguage());
        fillyear();
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    VillageID = Village.get(spinVillageName.getSelectedItemPosition() - 1).getVillageID();
                    countbirth("31", spin_year.getSelectedItem().toString(), "01", tv_birth1, VillageID);
                    countbirth("28", spin_year.getSelectedItem().toString(), "02", tv_birth2, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "03", tv_birth3, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "04", tv_birth4, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "05", tv_birth5, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "06", tv_birth6, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "07", tv_birth7, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "08", tv_birth8, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "09", tv_birth9, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "10", tv_birth10, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "11", tv_birth11, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "12", tv_birth12, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "01", tv_death1, VillageID);
                    countdeath("28", spin_year.getSelectedItem().toString(), "02", tv_death2, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "03", tv_death3, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "04", tv_death4, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "05", tv_death5, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "06", tv_death6, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "07", tv_death7, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "08", tv_death8, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "09", tv_death9, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "10", tv_death10, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "11", tv_death11, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "12", tv_death12, VillageID);

                    countsbirth("31", spin_year.getSelectedItem().toString(), "01", tv_sbirth1, VillageID);
                    countsbirth("28", spin_year.getSelectedItem().toString(), "02", tv_sbirth2, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "03", tv_sbirth3, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "04", tv_sbirth4, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "05", tv_sbirth5, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "06", tv_sbirth6, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "07", tv_sbirth7, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "08", tv_sbirth8, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "09", tv_sbirth9, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "10", tv_sbirth10, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "11", tv_sbirth11, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "12", tv_sbirth12, VillageID);


                } else {
                    VillageID = 0;
                    countbirth("31", spin_year.getSelectedItem().toString(), "01", tv_birth1);
                    countbirth("28", spin_year.getSelectedItem().toString(), "02", tv_birth2);
                    countbirth("31", spin_year.getSelectedItem().toString(), "03", tv_birth3);
                    countbirth("30", spin_year.getSelectedItem().toString(), "04", tv_birth4);
                    countbirth("31", spin_year.getSelectedItem().toString(), "05", tv_birth5);
                    countbirth("30", spin_year.getSelectedItem().toString(), "06", tv_birth6);
                    countbirth("31", spin_year.getSelectedItem().toString(), "07", tv_birth7);
                    countbirth("31", spin_year.getSelectedItem().toString(), "08", tv_birth8);
                    countbirth("30", spin_year.getSelectedItem().toString(), "09", tv_birth9);
                    countbirth("31", spin_year.getSelectedItem().toString(), "10", tv_birth10);
                    countbirth("30", spin_year.getSelectedItem().toString(), "11", tv_birth11);
                    countbirth("31", spin_year.getSelectedItem().toString(), "12", tv_birth12);
                    countdeath("31", spin_year.getSelectedItem().toString(), "01", tv_death1);
                    countdeath("28", spin_year.getSelectedItem().toString(), "02", tv_death2);
                    countdeath("31", spin_year.getSelectedItem().toString(), "03", tv_death3);
                    countdeath("30", spin_year.getSelectedItem().toString(), "04", tv_death4);
                    countdeath("31", spin_year.getSelectedItem().toString(), "05", tv_death5);
                    countdeath("30", spin_year.getSelectedItem().toString(), "06", tv_death6);
                    countdeath("31", spin_year.getSelectedItem().toString(), "07", tv_death7);
                    countdeath("31", spin_year.getSelectedItem().toString(), "08", tv_death8);
                    countdeath("30", spin_year.getSelectedItem().toString(), "09", tv_death9);
                    countdeath("31", spin_year.getSelectedItem().toString(), "10", tv_death10);
                    countdeath("30", spin_year.getSelectedItem().toString(), "11", tv_death11);
                    countdeath("31", spin_year.getSelectedItem().toString(), "12", tv_death12);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "01", tv_sbirth1);
                    countsbirth("28", spin_year.getSelectedItem().toString(), "02", tv_sbirth2);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "03", tv_sbirth3);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "04", tv_sbirth4);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "05", tv_sbirth5);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "06", tv_sbirth6);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "07", tv_sbirth7);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "08", tv_sbirth8);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "09", tv_sbirth9);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "10", tv_sbirth10);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "11", tv_sbirth11);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "12", tv_sbirth12);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                tv_year1.setText(spin_year.getSelectedItem().toString());
                tv_year2.setText(spin_year.getSelectedItem().toString());
                tv_year3.setText(spin_year.getSelectedItem().toString());
                tv_year4.setText(spin_year.getSelectedItem().toString());
                tv_year5.setText(spin_year.getSelectedItem().toString());
                tv_year6.setText(spin_year.getSelectedItem().toString());
                tv_year7.setText(spin_year.getSelectedItem().toString());
                tv_year8.setText(spin_year.getSelectedItem().toString());
                tv_year9.setText(spin_year.getSelectedItem().toString());
                tv_year10.setText(spin_year.getSelectedItem().toString());
                tv_year11.setText(spin_year.getSelectedItem().toString());
                tv_year12.setText(spin_year.getSelectedItem().toString());
                if (VillageID > 0) {
                    countbirth("31", spin_year.getSelectedItem().toString(), "01", tv_birth1, VillageID);
                    countbirth("28", spin_year.getSelectedItem().toString(), "02", tv_birth2, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "03", tv_birth3, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "04", tv_birth4, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "05", tv_birth5, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "06", tv_birth6, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "07", tv_birth7, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "08", tv_birth8, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "09", tv_birth9, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "10", tv_birth10, VillageID);
                    countbirth("30", spin_year.getSelectedItem().toString(), "11", tv_birth11, VillageID);
                    countbirth("31", spin_year.getSelectedItem().toString(), "12", tv_birth12, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "01", tv_death1, VillageID);
                    countdeath("28", spin_year.getSelectedItem().toString(), "02", tv_death2, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "03", tv_death3, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "04", tv_death4, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "05", tv_death5, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "06", tv_death6, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "07", tv_death7, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "08", tv_death8, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "09", tv_death9, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "10", tv_death10, VillageID);
                    countdeath("30", spin_year.getSelectedItem().toString(), "11", tv_death11, VillageID);
                    countdeath("31", spin_year.getSelectedItem().toString(), "12", tv_death12, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "01", tv_sbirth1, VillageID);
                    countsbirth("28", spin_year.getSelectedItem().toString(), "02", tv_sbirth2, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "03", tv_sbirth3, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "04", tv_sbirth4, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "05", tv_sbirth5, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "06", tv_sbirth6, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "07", tv_sbirth7, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "08", tv_sbirth8, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "09", tv_sbirth9, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "10", tv_sbirth10, VillageID);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "11", tv_sbirth11, VillageID);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "12", tv_sbirth12, VillageID);

                } else

                {

                    countbirth("31", spin_year.getSelectedItem().toString(), "01", tv_birth1);
                    countbirth("28", spin_year.getSelectedItem().toString(), "02", tv_birth2);
                    countbirth("31", spin_year.getSelectedItem().toString(), "03", tv_birth3);
                    countbirth("30", spin_year.getSelectedItem().toString(), "04", tv_birth4);
                    countbirth("31", spin_year.getSelectedItem().toString(), "05", tv_birth5);
                    countbirth("30", spin_year.getSelectedItem().toString(), "06", tv_birth6);
                    countbirth("31", spin_year.getSelectedItem().toString(), "07", tv_birth7);
                    countbirth("31", spin_year.getSelectedItem().toString(), "08", tv_birth8);
                    countbirth("30", spin_year.getSelectedItem().toString(), "09", tv_birth9);
                    countbirth("31", spin_year.getSelectedItem().toString(), "10", tv_birth10);
                    countbirth("30", spin_year.getSelectedItem().toString(), "11", tv_birth11);
                    countbirth("31", spin_year.getSelectedItem().toString(), "12", tv_birth12);
                    countdeath("31", spin_year.getSelectedItem().toString(), "01", tv_death1);
                    countdeath("28", spin_year.getSelectedItem().toString(), "02", tv_death2);
                    countdeath("31", spin_year.getSelectedItem().toString(), "03", tv_death3);
                    countdeath("30", spin_year.getSelectedItem().toString(), "04", tv_death4);
                    countdeath("31", spin_year.getSelectedItem().toString(), "05", tv_death5);
                    countdeath("30", spin_year.getSelectedItem().toString(), "06", tv_death6);
                    countdeath("31", spin_year.getSelectedItem().toString(), "07", tv_death7);
                    countdeath("31", spin_year.getSelectedItem().toString(), "08", tv_death8);
                    countdeath("30", spin_year.getSelectedItem().toString(), "09", tv_death9);
                    countdeath("31", spin_year.getSelectedItem().toString(), "10", tv_death10);
                    countdeath("30", spin_year.getSelectedItem().toString(), "11", tv_death11);
                    countdeath("31", spin_year.getSelectedItem().toString(), "12", tv_death12);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "01", tv_sbirth1);
                    countsbirth("28", spin_year.getSelectedItem().toString(), "02", tv_sbirth2);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "03", tv_sbirth3);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "04", tv_sbirth4);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "05", tv_sbirth5);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "06", tv_sbirth6);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "07", tv_sbirth7);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "08", tv_sbirth8);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "09", tv_sbirth9);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "10", tv_sbirth10);
                    countsbirth("30", spin_year.getSelectedItem().toString(), "11", tv_sbirth11);
                    countsbirth("31", spin_year.getSelectedItem().toString(), "12", tv_sbirth12);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void fillyear() {


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, year);
        spin_year.setAdapter(adapter);
    }

    public void showdata(int VillageId) {

    }




    public void countbirth(String last, String yr, String mnth, TextView txt, int VillageId) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(tblChild.CHILD_dob)  between Date('" + start + "') and Date('" + lastd + "') and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblChild.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }

    public void countbirth(String last, String yr, String mnth, TextView txt) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(tblChild.CHILD_dob)  between Date('" + start + "') and Date('" + lastd + "')  and   tblChild.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }

    public void countdeath(String last, String yr, String mnth, TextView txt, int VillageId) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from Tbl_HHFamilyMember   inner join Tbl_HHSurvey on Tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(Tbl_HHFamilyMember.DateOfDeath)  between Date('" + start + "') and Date('" + lastd + "') and Tbl_HHSurvey.VillageID=" + VillageId + " and   Tbl_HHFamilyMember.StatusID=2 and   Tbl_HHFamilyMember.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }

    public void countdeath(String last, String yr, String mnth, TextView txt) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from Tbl_HHFamilyMember   inner join Tbl_HHSurvey on Tbl_HHFamilyMember.HHSurveyGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(Tbl_HHFamilyMember.DateOfDeath)  between Date('" + start + "') and Date('" + lastd + "')  and   Tbl_HHFamilyMember.StatusID=2 and   Tbl_HHFamilyMember.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }

    public void countsbirth(String last, String yr, String mnth, TextView txt, int VillageId) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from tblPregnant_woman   inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(tblPregnant_woman.DateofDeath)  between Date('" + start + "') and Date('" + lastd + "') and Tbl_HHSurvey.VillageID=" + VillageId + " and   tblPregnant_woman.DeliveryType=2 and   tblPregnant_woman.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }

    public void countsbirth(String last, String yr, String mnth, TextView txt) {
        String start = "", lastd = "";
        start = yr + "-" + mnth + "-" + "01";
        lastd = yr + "-" + mnth + "-" + last;
        String Sql5child = "";
        Sql5child = "select  count(*) from tblPregnant_woman   inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID  where Date(tblPregnant_woman.DateofDeath)  between Date('" + start + "') and Date('" + lastd + "')  and   tblPregnant_woman.DeliveryType=2 and   tblPregnant_woman.AshaID = " + ashaid + "";
        int iValue5child = 0;
        iValue5child = dataprovider.getMaxRecord(Sql5child);
        txt.setText("" + iValue5child);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(CRVSReportASHA.this,
                Report_Module.class);
        finish();
        startActivity(i);


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


}
