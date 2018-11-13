package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.adapter.Pnc_WomenAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tblChild;

public class PncWomenList extends Activity {

    TextView ltvWomenID, ltvWomenName, ltvNxtvisitdate, tv_pagename, totalcount;
    Button btnsearchFilter;
    GridView gridanc;
    EditText etFamilyIDSearch;
    Global global;
    Locale myLocale;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    public ArrayList<tblChild> Child = new ArrayList<tblChild>();
    int flag = 0;
    ImageView Img_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        Img_info = (ImageView) findViewById(R.id.Img_info);
        // btnaddanc = (Button) findViewById(R.id.btnaddanc);
        // btncloseanc = (Button) findViewById(R.id.btncloseanc);
        gridanc = (GridView) findViewById(R.id.gridanc);
        btnsearchFilter = (Button) findViewById(R.id.btnsearchFilter);
        etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        // ltvWomenID = (TextView) findViewById(R.id.ltvWomenID);
        etFamilyIDSearch.setHint(R.string.childname);
        ltvWomenName = (TextView) findViewById(R.id.ltvWomenName);
        ltvWomenName.setText(getResources().getString(R.string.Mothername) + "/" + getResources().getString(R.string.childname) + "/" + getResources().getString(R.string.DateOfBirth) + "/" + getResources().getString(R.string.Gender));
        totalcount = (TextView) findViewById(R.id.totalcount);
        tv_pagename = (TextView) findViewById(R.id.tv_pagename);

        tv_pagename.setText(getResources().getString(R.string.np));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getInt("flag");
        }
        Img_info.setVisibility(View.VISIBLE);
        Img_info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CustomAlert();
            }
        });
        btnsearchFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (etFamilyIDSearch.getText().toString().length() > 0) {
                    fillgrid(etFamilyIDSearch.getText().toString(), 0);
                }
            }
        });
        etFamilyIDSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    fillgrid(etFamilyIDSearch.getText().toString(), 0);
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
                if (flag == 1) {
                } else {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (flag == 1) {
            fillgrid(global.getsGlobalChildGUID(), 1);
        } else {
            fillgrid(1);
        }

    }

    public void CustomAlert() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.color_infoalert, null, false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.RIGHT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        // Display the dialog
        dialog.show();

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
        if (flag == 0) {
            Child = dataProvider.gettblChild("", global.getsGlobalAshaCode(), 5, VillageID);
        }
        if (flag == 1) {
            Child = dataProvider.gettblChild("", global.getsGlobalAshaCode(), 9, VillageID);
        }
        if (Child != null) {
            android.view.ViewGroup.LayoutParams params = gridanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * (Child.size() + 7);
            params.height = gridHeight1;
            gridanc.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + Child.size());
            gridanc.setAdapter(new Pnc_WomenAdapter(this, Child));
        }
    }

    @SuppressWarnings("unused")
    public void fillgrid(String Woman_name, int flagsearch) {
        // TODO Auto-generated method stub
        if (flagsearch == 0) {
            Child = dataProvider.gettblChild(Woman_name, global.getsGlobalAshaCode(), 8, VillageID);
        } else if (flagsearch == 1) {
            Child = dataProvider.gettblChild("", Woman_name, 1, 0);
        }

        if (Child != null) {
//			
            android.view.ViewGroup.LayoutParams params = gridanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    90, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * (Child.size() + 7);
            params.height = gridHeight1;
            gridanc.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + Child.size());
            gridanc.setAdapter(new Pnc_WomenAdapter(this, Child));

        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (flag == 1) {
            Intent i = new Intent(PncWomenList.this, Dashboard_Activity.class);
            finish();
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Intent i = new Intent(PncWomenList.this, HomeVisitDashboard.class);
            finish();
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
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
}