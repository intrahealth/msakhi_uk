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
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.microware.intrahealth.adapter.AshaListFormAnm_Adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

import java.util.ArrayList;

public class AshaListForCHC extends Activity {

    GridView ImunizationGrid;
    ArrayList<MstASHA> ashaname = new ArrayList<MstASHA>();
    DataProvider dataProvider;
    Global global;
    int iFlag = 0;
    Spinner spin_Subcenter, spin_ASHA;
    String sqlasha = "", sqlSubcenter = "";
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ashalistforchc);
        global = (Global) getApplication();
        spin_Subcenter = (Spinner) findViewById(R.id.spin_Subcenter);
        btnSave = (Button) findViewById(R.id.btnSave);
        spin_ASHA = (Spinner) findViewById(R.id.spin_ASHA);
        spin_Subcenter = (Spinner) findViewById(R.id.spin_Subcenter);
        sqlSubcenter = "Select * from MstSubCenter where LanguageID ='" + global.getLanguage() + "' ";
        //sqlasha = "Select * from Mstasha inner join anmsubcenter on anmsubcenter.ANMID=MstASHA.ANMCode where MstASHA.LanguageID ='" + global.getLanguage() + "' and anmsubcenter.SubCenterID=" + global.getsGlobalAshaCode() + "";
        Validate.fillspinner(this, spin_Subcenter, sqlSubcenter, "SubCenterName");
        spin_Subcenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    int ID = Validate.returnID(AshaListForCHC.this, spin_Subcenter, sqlSubcenter, "SubCenterID");
                    sqlasha = "Select * from Mstasha inner join anmsubcenter on anmsubcenter.ANMID=MstASHA.ANMCode where MstASHA.LanguageID ='" + global.getLanguage() + "' and anmsubcenter.SubCenterID=" + ID + "";
                    Validate.fillspinner(AshaListForCHC.this, spin_ASHA, sqlasha, "ASHAName");

                } else {
                    sqlasha = "Select * from Mstasha inner join anmsubcenter on anmsubcenter.ANMID=MstASHA.ANMCode where MstASHA.LanguageID ='" + global.getLanguage() + "' and anmsubcenter.SubCenterID=0";
                    Validate.fillspinner(AshaListForCHC.this, spin_ASHA, sqlasha, "ASHAName");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ashaid = 0, anmid = 0;
                if (sCheckValidation() == 0) {
                    ashaid = Validate.returnID(AshaListForCHC.this, spin_ASHA, sqlasha, "ASHAID");
                    anmid = Validate.returnID(AshaListForCHC.this, spin_ASHA, sqlasha, "ANMCode");
                    String ashaname = Validate.returnIDstr(AshaListForCHC.this, spin_ASHA, sqlasha, "ASHAName");
                    global.setsGlobalAshaCode("" + ashaid);
                    global.setAnmidasAnmCode("" + anmid);
                    global.setsGlobalANMCODE("" + anmid);
                    global.setsGlobalAshaName(ashaname);
                    Intent i = new Intent(AshaListForCHC.this,
                            NCD_CHC_AA.class);
                    finish();
                    startActivity(i);
                }
            }
        });

    }

    public int sCheckValidation() {
        try {

            if (spin_Subcenter.getSelectedItemPosition() == 0) {
                Validate.CustomAlertSpinner(this, spin_Subcenter, getResources().getString(R.string.select_subcenter));
                return 1;
            }
            if (spin_ASHA.getSelectedItemPosition() == 0) {
                Validate.CustomAlertSpinner(this, spin_ASHA, getResources().getString(R.string.select_asha));
                return 1;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (global.getiGlobalRoleID() == 3) {
            menu.add(0, 0, 0, global.getsGlobalANMName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else if (global.getiGlobalRoleID() == 2) {
            menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else if (global.getiGlobalRoleID() == 11) {
            menu.add(0, 0, 0, global.getCHCName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

//		menu.add(0, 1, 1, "History").setIcon(R.drawable.logout1)
//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //

        return true;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (iFlag == 7) {
            Intent i = new Intent(AshaListForCHC.this,
                    Anm_NCD_Report.class);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(AshaListForCHC.this, Dashboard_Activity.class);
            finish();
            startActivity(i);
        }
    }

}
