package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.adapter.HomeAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tbl_pregnantwomen;

public class HomeVisit extends Activity {

    TextView ltvWomenID, ltvWomenName, ltvNxtvisitdate;
    Button btnanc, btnsearchFilter;
    GridView gridanc;
    EditText etFamilyIDSearch;
    Global global;
    Locale myLocale;
    TextView totalcount, tv_pagename;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    public ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        // btnaddanc = (Button) findViewById(R.id.btnaddanc);
        // btncloseanc = (Button) findViewById(R.id.btncloseanc);
        btnanc = (Button) findViewById(R.id.btnanc);
        gridanc = (GridView) findViewById(R.id.gridanc);
        totalcount = (TextView) findViewById(R.id.totalcount);
        tv_pagename = (TextView) findViewById(R.id.tv_pagename);
        btnsearchFilter = (Button) findViewById(R.id.btnsearchFilter);
        etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        // ltvWomenID = (TextView) findViewById(R.id.ltvWomenID);
        ltvWomenName = (TextView) findViewById(R.id.ltvWomenName);
        // ltvNxtvisitdate = (TextView) findViewById(R.id.ltvNxtvisitdate);
        tv_pagename.setText(getResources().getString(R.string.pregnantwomen));
        btnsearchFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (etFamilyIDSearch.getText().toString().length() > 0) {
                    fillgrid(etFamilyIDSearch.getText().toString());
                }
            }
        });
        etFamilyIDSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    fillgrid(etFamilyIDSearch.getText().toString());
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (etFamilyIDSearch.getText().toString().length() == 0) {
                    fillgrid(1);
                }
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

        // fillgrid();

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
        // TODO Auto-generated method stub
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Validate.returnIntegerValue(global.getsGlobalAshaCode());
        }
        if (flag == 0) {
            pregnant = dataProvider.getPregnantWomendataanc("", 0, ashaid, VillageID);
        } else if (flag == 1) {
            pregnant = dataProvider.getPregnantWomendataanc("", 9, ashaid, VillageID);
        }

        if (pregnant != null && pregnant.size() > 0) {
            android.view.ViewGroup.LayoutParams params = gridanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * (pregnant.size() + 4);
            params.height = gridHeight1;
            gridanc.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + pregnant.size());
            gridanc.setAdapter(new HomeAdapter(this, pregnant));

        }
    }

    public void fillgrid(String Woman_name) {
        // TODO Auto-generated method stub
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Validate.returnIntegerValue(global.getsGlobalAshaCode());
        }


        pregnant = dataProvider.getPregnantWomendataanc(Woman_name, 4, ashaid, VillageID);

        if (pregnant != null && pregnant.size() > 0) {
            android.view.ViewGroup.LayoutParams params = gridanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * (pregnant.size() + 4);
            params.height = gridHeight1;
            gridanc.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + pregnant.size());
            gridanc.setAdapter(new HomeAdapter(this, pregnant));

        }
    }

    public void openExpendableGridView(int position) {

        int size = gridanc.getChildCount();
        for (int i = 0; i < size; i++) {
            ViewGroup gridchild = (ViewGroup) gridanc.getChildAt(i);
            final TextView tvGridhide = (TextView) gridchild
                    .findViewById(R.id.tvGridhide);
            final GridView gridVisits = (GridView) gridchild
                    .findViewById(R.id.gridVisits);

            if (position != i) {
                gridVisits.setVisibility(View.GONE);
                tvGridhide.setText("0");
            }

        }

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(HomeVisit.this, HomeVisitDashboard.class);
        finish();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}