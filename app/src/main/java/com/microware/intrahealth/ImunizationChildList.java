package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.Child_Imunnization_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;
import com.microware.intrahealth.object.MstVillage;

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

public class ImunizationChildList extends Activity {
    TextView totalcount;
    GridView ImunizationGrid;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    DataProvider dataProvider;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    ArrayAdapter<String> adapter;
    Global global;
    Button btn_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imunazation);
        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        setTitle(global.getVersionName());
        btn_report = (Button) findViewById(R.id.btn_report);
        ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
        totalcount = (TextView) findViewById(R.id.totalcount);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (global.getVHND_flag() == 1) {
                } else {
                    if (pos > 0) {
                        VillageID = Village.get(
                                spinVillageName.getSelectedItemPosition() - 1)
                                .getVillageID();
                        fillgrid(0);

                    } else {
                        VillageID = 0;
                        fillgrid(2);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (global.getVHND_flag() == 1) {
            fillgrid(1);
        } else {
            fillgrid(2);
        }

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


    public void onclickButton(View view) {

        Intent in;
        switch (view.getId()) {

            case R.id.btn_report:

                Intent i = new Intent(ImunizationChildList.this, Reportindicator_Hnbclist.class);
                i.putExtra("flag", 3);
                finish();
                startActivity(i);

                break;

            default:
                break;
        }
    }

    public void fillgrid(int flag) {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (flag == 0) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 1,String.valueOf(VillageID));
        } if (flag == 2) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 3,"");
        }
        if (flag == 1) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 2, global.getsGlobalChildGUID());
        }

        if (ChildImmun != null) {
            android.view.ViewGroup.LayoutParams params = ImunizationGrid
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * ChildImmun.size();
            params.height = gridHeight1;
            ImunizationGrid.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + ChildImmun.size());
            ImunizationGrid.setAdapter(new Child_Imunnization_adapter(this,
                    ChildImmun));
        } else {
            ImunizationGrid.setAdapter(new Child_Imunnization_adapter(this,
                    ChildImmun));
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (global.getVHND_flag() == 1) {
            global.setVHND_flag(0);
            global.setsGlobalChildGUID("");
            Intent i = new Intent(ImunizationChildList.this, VHND_DueList_new.class);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(ImunizationChildList.this, ImmunizationDashboard.class);
            finish();
            startActivity(i);
        }

    }
}