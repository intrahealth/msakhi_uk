package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.adapter.VHND_ReportAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tblVHNDDuelist;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import java.util.ArrayList;

public class VHND_Immunizatiom extends Activity {
    GridView VHND_rcrd_grid;
    DataProvider dataProvider;
    Spinner SpinMonth;
    ArrayAdapter<String> adapter;
    ArrayList<tblVHNDDuelist> VHND_Date = new ArrayList<tblVHNDDuelist>();
    String Monthhh[];
    Validate validate;
    Global global;
    ArrayList<tbl_pregnantwomen> Pregnant_woman;
    Spinner spin_month;
    ArrayList<tblChild> child;
    TextView  txt_Immunization, txt_date,
            tv_immudone, tv_immudue, tv_Actual_VHNDDate;
    GridView  Gridview_Immunization;
    // LinearLayout tblrow_ANC, tblrow_Immunization;
    String[] month = {"Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
            "Dec", "Jan", "Feb", "Mar"};
    TableRow tbl_actualvhnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vhnd_immunization);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        global = (Global) this.getApplicationContext();
        setTitle(global.getVersionName());


        spin_month = (Spinner) findViewById(R.id.spin_month);

        tbl_actualvhnd = (TableRow) findViewById(R.id.tbl_actualvhnd);

        tv_immudone = (TextView) findViewById(R.id.tv_immudone);
        tv_immudue = (TextView) findViewById(R.id.tv_immudue);
        txt_date = (TextView) findViewById(R.id.txt_date);
        tv_Actual_VHNDDate = (TextView) findViewById(R.id.tv_Actual_VHNDDate);
              txt_Immunization = (TextView) findViewById(R.id.txt_Immunization);


        Gridview_Immunization = (GridView) findViewById(R.id.Gridview_Immunization);

        //
        fillmonth(spin_month);
        spin_month.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stubtry {
                String date = Validate.getcurrentdate();
                String[] ss = date.split("-");
                int mnth = Integer.valueOf(ss[1]);
                int yr = Integer.valueOf(ss[0]);
                if (mnth == 1 || mnth == 2 || mnth == 3) {
                    yr = yr - 1;
                }
                String sql = "select " + month[position]
                        + " from VHND_Schedule where year=" + yr + "";
                String currmnthdate = dataProvider.getRecord(sql);
                String sql1 = "select Actual_VHNDDate from tblVHNDDuelist where VHNDDate='"
                        + currmnthdate
                        + "' and  AshaID='"
                        + global.getsGlobalAshaCode() + "'";
                String actualvhnd = dataProvider.getRecord(sql1);
                if (actualvhnd != null) {
                    tv_Actual_VHNDDate.setText(Validate
                            .changeDateFormat(actualvhnd));
                    tbl_actualvhnd.setVisibility(view.VISIBLE);
                } else {
                    tbl_actualvhnd.setVisibility(view.GONE);
                }

                txt_date.setText(Validate.changeDateFormat(currmnthdate));
               // FillVHNDDueList_ANC(currmnthdate, global.getsGlobalAshaCode());
                FillVHNDDueList_Immunization(currmnthdate,
                        global.getsGlobalAshaCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void fillmonth(Spinner spin) {
        String[] montharr = getResources().getStringArray(R.array.globalmonth);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, montharr);
        spin.setAdapter(adapter);
    }

    public void SetVisibletableRow(LinearLayout tbrow, int size) {
        if (size > 0) {
            tbrow.setVisibility(View.VISIBLE);
        } else {
            tbrow.setVisibility(View.GONE);
        }
    }


    public void FillVHNDDueList_Immunization(String Date, String AshaID) {

        int sizecount = 0;

        VHND_Date = dataProvider.getVHNDReport(Date, 1, AshaID);

        int Count = 0;
        if (VHND_Date != null && VHND_Date.size() > 0) {
            Count = VHND_Date.size();
            android.view.ViewGroup.LayoutParams params = Gridview_Immunization
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight = 0;
            gridHeight = hi * Count;
            params.height = gridHeight;
            Gridview_Immunization.setLayoutParams(params);
            Gridview_Immunization.setAdapter(new VHND_ReportAdapter(this,
                    VHND_Date, Integer.valueOf(global.getsGlobalAshaCode()),
                    Date, 2));
            String sql = "", Date1 = "";
            Date1 = VHND_Date.get(0).getActual_VHNDDate().toString();
            sql = "Select count(*) from tblCHild where ((bcg='" + Date1
                    + "' ) or (hepb1='" + Date1 + "')  or ( hepb2='" + Date1
                    + "')  or ( hepb3='" + Date1 + "') or (opv1='" + Date1
                    + "') or (opv2='" + Date1 + "') or (opv3='" + Date1
                    + "')  or ( dpt1='" + Date1 + "') or ( dpt2='" + Date1
                    + "') or (dpt3='" + Date1 + "') or ( Pentavalent1='"
                    + Date1 + "') or( Pentavalent2='" + Date1
                    + "') or ( Pentavalent3='" + Date1 + "')or ( measeals='"
                    + Date1 + "') or ( vitaminA='" + Date1
                    + "') or ( VitaminAtwo='" + Date1 + "') or (OPVBooster='"
                    + Date1 + "') or ( DPTBooster='" + Date1
                    + "') or (DPTBoostertwo='" + Date1 + "')  or (JEVaccine1='"
                    + Date1 + "') or ( JEVaccine2='" + Date1
                    + "') ) and AshaID=" + AshaID + " ";
            int retunr = dataProvider.getMaxRecord(sql);
            tv_immudue.setText("" + VHND_Date.size());
            tv_immudone.setText("" + retunr);
        } else {
            Gridview_Immunization.setAdapter(new VHND_ReportAdapter(this,
                    VHND_Date, Integer.valueOf(global.getsGlobalAshaCode()),
                    Date, 2));
            tv_immudue.setText("" + VHND_Date.size());
            tv_immudone.setText("" + 0);
        }

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
        Intent i = new Intent(VHND_Immunizatiom.this, Report_Module.class);
        finish();
        startActivity(i);
    }
}
