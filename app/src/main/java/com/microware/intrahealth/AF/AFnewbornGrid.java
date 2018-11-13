package com.microware.intrahealth.AF;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.microware.intrahealth.AFAdapter.AFNewbornGridAdapter;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HbncReport;
import com.microware.intrahealth.MCH_Dashboard;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.WomenListHH;
import com.microware.intrahealth.adapter.NewbornGridAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.newbornGrid;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tblChild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aditya on 10/4/2018.
 */

public class AFnewbornGrid extends Activity {

    Button btnAdd, btn_report;
    GridView NewbornGrid;
    DataProvider dataProvider;
    Validate validate;
    Global global;
    EditText etFamily;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayList<tblChild> Child = new ArrayList<tblChild>();
    TextView totalcount, tv_name, tv_Boy, tv_Girl;
    int boycount = 0, girlcount = 0, VillageID = 0;
    ArrayAdapter<String> adapter;
    ImageView Img_info;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.af_newbornactivity);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        btnAdd = (Button) findViewById(R.id.btnAdd);
        Img_info = (ImageView) findViewById(R.id.Img_info);
        btn_report = (Button) findViewById(R.id.btn_report);
        NewbornGrid = (GridView) findViewById(R.id.NewbornGrid);
        totalcount = (TextView) findViewById(R.id.totalcount);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_Boy = (TextView) findViewById(R.id.tv_Boy);
        tv_Girl = (TextView) findViewById(R.id.tv_Girl);
        etFamily = (EditText) findViewById(R.id.etFamily);
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
                Intent i = new Intent(AFnewbornGrid.this, WomenListHH.class);

                finish();
                startActivity(i);
            }
        });
        Img_info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CustomAlert();
            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(AFnewbornGrid.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 1);
                    finish();
                    startActivity(i);
                }else if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(AFnewbornGrid.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 1);
                    finish();
                    startActivity(i);
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
            } else if (flag == 1) {
                Child = dataProvider.gettblChild("", global.getsGlobalAshaCode(), 11, VillageID);
            } else if (flag == 2) {
                Child = dataProvider.gettblChild(etFamily.getText().toString(), global.getsGlobalAshaCode(), 10, VillageID);
            }
            if (Child != null && Child.size() > 0) {
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
                NewbornGrid.setAdapter(new AFNewbornGridAdapter(this, Child));
                totalcount.setText(getResources().getString(
                        R.string.totalchild)
                        + " -" + String.valueOf(Child.size()));
                String sqlboycount = "", sqlgirlcount = "";
                if (VillageID > 0) {
                    if (flag == 2) {
                        sqlboycount = "Select count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID   inner join Tbl_HHFamilyMember member on tblChild.ChildGUID= member.HHFamilyMemberGUID where   tblChild.ashaid='" + global.getsGlobalAshaCode()
                                + "' and member.FamilyMemberName like '%" + etFamily.getText().toString() + "%' and tblChild.Gender=2  and ((julianday('now')- julianday(tblChild.child_dob))/365) between 0 and 5  ";
                        sqlgirlcount = "Select count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID   inner join Tbl_HHFamilyMember member on tblChild.ChildGUID= member.HHFamilyMemberGUID where   tblChild.ashaid='" + global.getsGlobalAshaCode()
                                + "' and member.FamilyMemberName like '%" + etFamily.getText().toString() + "%' and tblChild.Gender=1  and ((julianday('now')- julianday(tblChild.child_dob))/365) between 0 and 5 ";
                    } else {
                        sqlboycount = "Select count(*) from  tblChild inner join Tbl_HHSurvey on tblChild.HHGUID = Tbl_HHSurvey.HHSurveyGUID      where Tbl_HHSurvey.VillageID = " + VillageID + " and ashaid='" + global.getsGlobalAshaCode()
                                + "'" +
                                "and Gender=2  and ((julianday('now')- julianday(child_dob))/365) between 0 and 5 ";
                        sqlgirlcount = "Select count(*) from  tblChild inner join Tbl_HHSurvey on tblChild.HHGUID = Tbl_HHSurvey.HHSurveyGUID   where Tbl_HHSurvey.VillageID = " + VillageID + " and ashaid='" + global.getsGlobalAshaCode()
                                + "' and Gender=1 and ((julianday('now')- julianday(child_dob))/365) between 0 and 5";
                    }
                    boycount = dataProvider.getMaxRecord(sqlboycount);
                    girlcount = dataProvider.getMaxRecord(sqlgirlcount);
                } else {
                    if (flag == 2) {
                        sqlboycount = "Select count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID   inner join Tbl_HHFamilyMember member on tblChild.ChildGUID= member.HHFamilyMemberGUID where   tblChild.ashaid='" + global.getsGlobalAshaCode()
                                + "' and member.FamilyMemberName like '%" + etFamily.getText().toString() + "%' and tblChild.Gender=2  and ((julianday('now')- julianday(tblChild.child_dob))/365) between 0 and 5";
                        sqlgirlcount = "Select count(*) from tblChild   inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID   inner join Tbl_HHFamilyMember member on tblChild.ChildGUID= member.HHFamilyMemberGUID where   tblChild.ashaid='" + global.getsGlobalAshaCode()
                                + "' and member.FamilyMemberName like '%" + etFamily.getText().toString() + "%' and tblChild.Gender=1  and ((julianday('now')- julianday(tblChild.child_dob))/365) between 0 and 5";
                    } else {
                        sqlboycount = "Select count(*) from tblChild  inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID where ashaid='" + global.getsGlobalAshaCode()
                                + "'" +
                                "and Gender=2   and ((julianday('now')- julianday(child_dob))/365) between 0 and 5";
                        sqlgirlcount = "Select count(*) from tblChild  inner join Tbl_HHSurvey on tblChild.HHGUID=Tbl_HHSurvey.HHSurveyGUID where ashaid='" + global.getsGlobalAshaCode()
                                + "' and Gender=1  and ((julianday('now')- julianday(child_dob))/365) between 0 and 5";
                    }
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
                NewbornGrid.setAdapter(new AFNewbornGridAdapter(this, Child));
                totalcount.setText(getResources().getString(
                        R.string.totalchild)
                        + " -" + 0);
                tv_Boy.setText(getResources().getString(
                        R.string.boy)
                        + " -" + 0);
                tv_Girl.setText(getResources().getString(
                        R.string.girl)
                        + " -" + 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
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

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getNew_GUID(), endTime);
        Intent i = new Intent(AFnewbornGrid.this, MCH_Dashboard.class);
        finish();
        startActivity(i);
    }

}
