package com.microware.intrahealth;

import java.util.ArrayList;

import com.microware.intrahealth.adapter.PregnantWomen_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PregWomenList extends Activity {

    GridView gridMasterRecord;
    DataProvider dataprovider;
    Global global;
    TextView totalcount;
    ImageView btnsearchFilter;
    EditText etFamilyIDSearch;
    ArrayList<Tbl_HHFamilyMember> record = new ArrayList<Tbl_HHFamilyMember>();

    ImageButton btnAddNew;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.preg_reglist);
        gridMasterRecord = (GridView) findViewById(R.id.HHSurveyGrid);
        btnsearchFilter = (ImageView) findViewById(R.id.btnsearchFilter);
        totalcount = (TextView) findViewById(R.id.totalcount);
        etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
        dataprovider = new DataProvider(this);
        global = (Global) getApplication();
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
                    fillGridView();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        fillGridView();
    }

    public void fillGridView() {
        int ashacode = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashacode = Integer.valueOf(global.getsGlobalAshaCode());
        }
        record = dataprovider.getAllWomenName("", 0, ashacode);
        if (record != null) {
            global.setsGlobalWomenName("");
            android.view.ViewGroup.LayoutParams params = gridMasterRecord
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * record.size();
            params.height = gridHeight1;
            gridMasterRecord.setLayoutParams(params);
            totalcount.setText(getResources().getString(
                    R.string.totalcountwomen)
                    + "-" + record.size());
            gridMasterRecord
                    .setAdapter(new PregnantWomen_adapter(this, record));

        } else {
            totalcount.setText(getResources().getString(
                    R.string.totalcountwomen)
                    + "-" + 0);
            gridMasterRecord
                    .setAdapter(new PregnantWomen_adapter(this, record));
        }

    }

    public void fillgrid(String Woman_name) {
        // TODO Auto-generated method stub
        String name = "%" + Woman_name + "%";
        int ashacode = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashacode = Integer.valueOf(global.getsGlobalAshaCode());
        }
        record = dataprovider.getAllWomenName(name, 4, ashacode);

        if (record != null && record.size() > 0) {
            android.view.ViewGroup.LayoutParams params = gridMasterRecord
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * record.size();
            params.height = gridHeight1;
            gridMasterRecord.setLayoutParams(params);
            totalcount.setText(getResources().getString(
                    R.string.totalcountwomen)
                    + "-" + record.size());
            gridMasterRecord
                    .setAdapter(new PregnantWomen_adapter(this, record));

        } else {
            totalcount.setText(getResources().getString(
                    R.string.totalcountwomen)
                    + "-" + 0);
            gridMasterRecord
                    .setAdapter(new PregnantWomen_adapter(this, record));
        }
    }

    public int SumOFlag(String GUID) {
        return 0;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent in = new Intent(PregWomenList.this, AncActivity.class);
        finish();
        startActivity(in);
    }

}
