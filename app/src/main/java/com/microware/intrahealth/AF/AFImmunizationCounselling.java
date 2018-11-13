package com.microware.intrahealth.AF;

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

import com.microware.intrahealth.AFAdapter.AFImmunizationAdapter;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HbncReport;
import com.microware.intrahealth.ImmunizationCounselling;
import com.microware.intrahealth.ImmunizationDashboard;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.adapter.ImmunizationAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;
import com.microware.intrahealth.object.MstVillage;

import java.util.ArrayList;

/**
 * Created by Aditya on 10/4/2018.
 */

public class AFImmunizationCounselling extends Activity {

    GridView ImunizationGrid;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    DataProvider dataProvider;
    Validate validate;
    Global global;
    TextView totalcount, tv_Header;
    EditText etFamily;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    ArrayAdapter<String> adapter;
    Button btn_report;
    ImageView Img_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.af_immunization_activity);
        totalcount = (TextView) findViewById(R.id.totalcount);
        tv_Header = (TextView) findViewById(R.id.tv_Header);
        btn_report = (Button) findViewById(R.id.btn_report);
        Img_info = (ImageView) findViewById(R.id.Img_info);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        etFamily = (EditText) findViewById(R.id.etFamily);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        fillVillageName(global.getLanguage());
        tv_Header.setText(getResources().getString(R.string.counselling) + getResources().getString(R.string.upto14year));
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
                        fillgrid(1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Img_info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CustomAlert();
            }
        });
        etFamily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    fillgrid(2);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFamily.getText().toString().length() == 0) {
                    fillgrid(1);
                }
            }
        });

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
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(AFImmunizationCounselling.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 2);
                    finish();
                    startActivity(i);
                }else if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(AFImmunizationCounselling.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 2);
                    finish();
                    startActivity(i);
                }
                break;

            default:
                break;
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

    public void fillgrid(int flag) {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (flag == 0) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 1, String.valueOf(VillageID));
        } else if (flag == 1) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 3, "");
        } else if (flag == 2) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 4, etFamily.getText().toString());
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
            // ImunizationGrid
            // .setAdapter(new ImmunizationAdapter(this, ChildImmun));
            // } else {
            // ImunizationGrid
            // .setAdapter(new ImmunizationAdapter(this, ChildImmun));
            // }

            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + ChildImmun.size());
            ImunizationGrid
                    .setAdapter(new AFImmunizationAdapter(this, ChildImmun));
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(AFImmunizationCounselling.this,
                ImmunizationDashboard.class);
        finish();
        startActivity(i);
    }
}
