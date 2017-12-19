package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.adapter.NewbornGridAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tblChild;

public class newbornGrid extends Activity {

    Button btnAdd, btn_report;
    GridView NewbornGrid;
    DataProvider dataProvider;
    Global global;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayList<tblChild> Child = new ArrayList<tblChild>();
    TextView totalcount, tv_name, tv_Boy, tv_Girl;
    int boycount = 0, girlcount = 0, VillageID = 0;
    ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newbornactivity);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btn_report = (Button) findViewById(R.id.btn_report);
        NewbornGrid = (GridView) findViewById(R.id.NewbornGrid);
        totalcount = (TextView) findViewById(R.id.totalcount);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_Boy = (TextView) findViewById(R.id.tv_Boy);
        tv_Girl = (TextView) findViewById(R.id.tv_Girl);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        String new_GUID = Validate.random();
        global.setNew_GUID(String.valueOf(new_GUID));
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStrings = sdf.format(date);
        dataProvider.getUserLogin(new_GUID, global.getUserID(), "Newborn", "Newborn",
                localTime, dateStrings);
        tv_name.setText(getResources().getString(R.string.childname1) + "/" + getResources().getString(R.string.gridedob));
        btnAdd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                global.setsGlobalChildGUID("");
                Intent i = new Intent(newbornGrid.this, WomenListHH.class);

                finish();
                startActivity(i);
            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(newbornGrid.this, Reportindicator_Hnbclist.class);
                i.putExtra("flag", 1);
                finish();
                startActivity(i);
            }
        });

        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    VillageID = Village.get(
                            spinVillageName.getSelectedItemPosition() - 1)
                            .getVillageID();
                    fillgrid(0);

                } else {
                    VillageID = 0;
                    fillgrid(1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //fillgrid();

    }

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public void fillgrid(int flag) {
        try {
            if (flag == 0) {
                Child = dataProvider.gettblChild("", global.getsGlobalAshaCode(), 2, VillageID);
            }
            if (flag == 1) {
                Child = dataProvider.gettblChild("", global.getsGlobalAshaCode(), 9, VillageID);
            }
            if (Child != null) {
                // android.view.ViewGroup.LayoutParams params = NewbornGrid
                // .getLayoutParams();
                // Resources r = getResources();
                // float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                // 100, r.getDisplayMetrics());
                // int hi = Math.round(px);
                // int gridHeight1 = 0;
                // gridHeight1 = hi * Child.size();
                // params.height = gridHeight1;
                // NewbornGrid.setLayoutParams(params);
                NewbornGrid.setAdapter(new NewbornGridAdapter(this, Child));
                totalcount.setText(getResources().getString(
                        R.string.totalchild)
                        + " -" + String.valueOf(Child.size()));
                String sqlboycount = "", sqlgirlcount = "";
                if (VillageID > 0) {
                    sqlboycount = "Select count(*) from  tblChild inner join Tbl_HHSurvey on tblChild.HHGUID = Tbl_HHSurvey.HHSurveyGUID      where Tbl_HHSurvey.VillageID = " + VillageID + " and ashaid='" + global.getsGlobalAshaCode()
                            + "'" +
                            "and Gender=2 ";
                    sqlgirlcount = "Select count(*) from  tblChild inner join Tbl_HHSurvey on tblChild.HHGUID = Tbl_HHSurvey.HHSurveyGUID   where Tbl_HHSurvey.VillageID = " + VillageID + " and ashaid='" + global.getsGlobalAshaCode()
                            + "' and Gender=1 ";
                    boycount = dataProvider.getMaxRecord(sqlboycount);
                    girlcount = dataProvider.getMaxRecord(sqlgirlcount);
                } else {
                    sqlboycount = "Select count(*) from tblChild where ashaid='" + global.getsGlobalAshaCode()
                            + "'" +
                            "and Gender=2 ";
                    sqlgirlcount = "Select count(*) from tblChild where ashaid='" + global.getsGlobalAshaCode()
                            + "' and Gender=1 ";
                    boycount = dataProvider.getMaxRecord(sqlboycount);
                    girlcount = dataProvider.getMaxRecord(sqlgirlcount);
                }
                tv_Boy.setText(getResources().getString(
                        R.string.boy)
                        + " -" + String.valueOf(boycount));
                tv_Girl.setText(getResources().getString(
                        R.string.girl)
                        + " -" + String.valueOf(girlcount));
            } else {
                NewbornGrid.setAdapter(new NewbornGridAdapter(this, Child));
//                totalcount.setText(getResources().getString(
//                        R.string.totalchild)
//                        + " -" + String.valueOf(Child.size()));
//                tv_Boy.setText(getResources().getString(
//                        R.string.boy)
//                        + " -" + String.valueOf(boycount));
//                tv_Girl.setText(getResources().getString(
//                        R.string.girl)
//                        + " -" + String.valueOf(girlcount));
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getNew_GUID(), endTime);
        Intent i = new Intent(newbornGrid.this, MCH_Dashboard.class);
        finish();
        startActivity(i);
    }

}
