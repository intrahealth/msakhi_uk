package com.microware.intrahealth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TextView;

import com.microware.intrahealth.adapter.Immunization_DueList_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AncReport_DueListAnm extends Activity {

    DataProvider dataProvider;

    Global global;

    TextView tv_Header, totalcount, tv_date,tv_Headername;
    GridView Duelist_Grid;
    Validate validate;
    Button btnSave;
    Dialog datepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anc_duelist);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        global = (Global) this.getApplicationContext();
        setTitle(global.getVersionName());

        btnSave = (Button) findViewById(R.id.btnSave);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_Headername = (TextView) findViewById(R.id.tv_Headername);
        tv_Header = (TextView) findViewById(R.id.tv_Header);
        totalcount = (TextView) findViewById(R.id.totalcount);

        // GridView
        tv_Headername.setText(R.string.ANCduelist);
        tv_Header.setText(R.string.ANCduelist);
        Duelist_Grid = (GridView) findViewById(R.id.Duelist_Grid);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePicker(tv_date);
            }
        });

        FillDueList_ANC(Validate.getcurrentdate());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_date.getText().toString().length() == 0){
                    tv_date.requestFocus();
                }else{
                    FillDueList_ANC(Validate.changeDateFormat(tv_date.getText().toString()));
                }
            }
        });

    }


    public void FillDueList_ANC(String currmnthdate) {
        String sql = "";
        ArrayList<HashMap<String, String>> data = null;
        sql = "Select * from tblPregnant_woman a inner join tblANCVisit b on cast(round((julianday('"
                + currmnthdate
                + "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
                + global.getsGlobalAshaCode()
                + " and a.AnmID="
                + global.getsGlobalANMCODE() + " order by b.pwguid";
        data = dataProvider.getDynamicVal(sql);

        int Count = 0;
        if (data != null) {
            Count = data.size();
//            android.view.ViewGroup.LayoutParams params = Duelist_Grid
//                    .getLayoutParams();
//            Resources r = getResources();
//            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    60, r.getDisplayMetrics());
//            int hi = Math.round(px);
//            int gridHeight = 0;
//            gridHeight = hi * Count;
//            params.height = gridHeight;
            // Duelist_Grid.setLayoutParams(params);
            Duelist_Grid.setAdapter(new Immunization_DueList_Adapter(this, data, 2));
            totalcount.setText(getResources().getString(R.string.totalcount)+" - " + data.size());

        }

    }

    public void ShowDatePicker(final TextView tVvisit) {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        // TODO Auto-generated method stub
        datepic = new Dialog(this, R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        // Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);

        datepic.show();

        datepic.getWindow().setLayout(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMinDate(System.currentTimeMillis()-1000);


        btncancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });

        // btnclear.setOnClickListener(new View.OnClickListener() {
        //
        // public void onClick(View v) {
        // // TODO Auto-generated
        //
        // tVvisit.setText(Datenew);
        // datepic.dismiss();
        //
        // }
        // });
        btnset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                @SuppressWarnings("unused")
                String[] monthName = {"January", "February", "March", "April",
                        "May", "June", "July", "August", "September",
                        "October", "November", "December"};
                int dt = datetext.getDayOfMonth();
                int month = datetext.getMonth() + 1;
                int year = datetext.getYear();

                String date = "" + dt;
                String mnth = "" + month;
                if (dt < 10) {
                    date = "0" + dt;
                }
                if (month < 10) {
                    mnth = "0" + month;
                }

                String sSellectedDate = date + "-" + mnth + "-"
                        + String.valueOf(year);
                tVvisit.setText(sSellectedDate);
                datepic.dismiss();
            }
        });

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
        Intent i = new Intent(AncReport_DueListAnm.this, Report_Module.class);
        finish();
        startActivity(i);
    }
}
