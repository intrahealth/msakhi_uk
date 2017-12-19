package com.microware.intrahealth;

import java.util.ArrayList;

import org.xml.sax.DTDHandler;

import com.microware.intrahealth.adapter.VHND_DueList_Adpater_new;
import com.microware.intrahealth.adapter.VHND_Record_adapter_new;
import com.microware.intrahealth.adapter.VHND_ReportAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.VHND_Schedule;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tblVHNDDuelist;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstFPFDetail;

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
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class VHND_Report extends Activity {
    GridView VHND_rcrd_grid;
    DataProvider dataProvider;
    Spinner SpinMonth;
    ArrayAdapter<String> adapter;
    ArrayList<tblVHNDDuelist> VHND_Date = new ArrayList<tblVHNDDuelist>();
    String Monthhh[];
    // int AshaID = 136;
    // LinearLayout Addbtn;
    Global global;
    ArrayList<tbl_pregnantwomen> Pregnant_woman;
    Spinner spin_month;
    ArrayList<tblChild> child;
    TextView txt_ANC, txt_Immunization, txt_date, tv_ancdone, tv_ancdue,
            tv_immudone, tv_immudue, tv_Actual_VHNDDate;
    GridView Gridview_ANC, Gridview_Immunization;
    // LinearLayout tblrow_ANC, tblrow_Immunization;
    String[] month = {"Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
            "Dec", "Jan", "Feb", "Mar"};
    TableRow tbl_actualvhnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vhnd_report);
        dataProvider = new DataProvider(this);
        global = (Global) this.getApplicationContext();
        setTitle(global.getVersionName());

        // Intent intent = getIntent();
        // ActivityName = intent.getExtras().getString("ActivityName");
        spin_month = (Spinner) findViewById(R.id.spin_month);
        // TableRow
        // tblrow_ANC = (LinearLayout) findViewById(R.id.tblrow_ANC);
        // tblrow_Immunization = (LinearLayout)
        // findViewById(R.id.tblrow_Immunization);
        // TextView
        tbl_actualvhnd = (TableRow) findViewById(R.id.tbl_actualvhnd);
        tv_ancdone = (TextView) findViewById(R.id.tv_ancdone);
        tv_ancdue = (TextView) findViewById(R.id.tv_ancdue);
        tv_immudone = (TextView) findViewById(R.id.tv_immudone);
        tv_immudue = (TextView) findViewById(R.id.tv_immudue);
        txt_date = (TextView) findViewById(R.id.txt_date);
        tv_Actual_VHNDDate = (TextView) findViewById(R.id.tv_Actual_VHNDDate);
        txt_ANC = (TextView) findViewById(R.id.txt_ANC);
        txt_Immunization = (TextView) findViewById(R.id.txt_Immunization);
        // GridView
        Gridview_ANC = (GridView) findViewById(R.id.Gridview_ANC);
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
                FillVHNDDueList_ANC(currmnthdate, global.getsGlobalAshaCode());
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

    public void FillVHNDDueList_ANC(String Date, String AshaID) {
        String sql = "";
        int sizecount = 0;

        VHND_Date = dataProvider.getVHNDReport(Date, 0, AshaID);

        int Count = 0;

        if (VHND_Date != null && VHND_Date.size() > 0) {
            Count = VHND_Date.size();
            android.view.ViewGroup.LayoutParams params = Gridview_ANC
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight = 0;
            gridHeight = hi * Count;
            params.height = gridHeight;
            Gridview_ANC.setLayoutParams(params);
            Gridview_ANC.setAdapter(new VHND_ReportAdapter(this, VHND_Date,
                    Integer.valueOf(global.getsGlobalAshaCode()), Date, 1));
            sql = "Select count(*) from tblPregnant_woman a inner join tblANCVisit b on   a.pwguid=b.pwguid where checkupvisitdate ='"
                    + VHND_Date.get(0).getActual_VHNDDate().toString()
                    + "' and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
                    + AshaID + " and a.AshaID=" + AshaID + "  ";

            int retunr = dataProvider.getMaxRecord(sql);
            tv_ancdue.setText("" + VHND_Date.size());
            tv_ancdone.setText("" + retunr);
        } else {
            Gridview_ANC.setAdapter(new VHND_ReportAdapter(this, VHND_Date,
                    Integer.valueOf(global.getsGlobalAshaCode()), Date, 1));
            tv_ancdue.setText("" + VHND_Date.size());
            tv_ancdone.setText("" + 0);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(VHND_Report.this, Report_Module.class);
        finish();
        startActivity(i);
    }
}
