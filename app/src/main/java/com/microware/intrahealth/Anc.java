package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint({"SimpleDateFormat", "InflateParams"})
public class Anc extends Activity {
    ImageView btnadd;
    TextView tvRegistration, tvLMPdate, tvTT1date, tvvisitdate, tvTT2date,
            tvTT3date, tvMCTSID, checkup, tvBmi;
    EditText etpregname, etweight, etHemoglobin, etsystolic, etDiastolic,
            etIFAnumber, etcalciumno, etHeight;
    String[] feet = {"4", "5", "6"};
    String[] inch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    Spinner spinCheckup_place, spinBPYN, spinurineYN, spintt1, spinVDRLYN,
            spinHIVYN, spinurinesugar, spinurineAlbumin, spinultrasoundresult,
            spinUltrasoundYN, spinUltraSoundConductedby, spinIFA, spinHBYN,
            spincheckup, spindangersign, spinCalcium, spin_feet, spin_inch;
    TableRow tblbp, tblurinesugar, tblurinealbumin, tblultrasondconduct,
            tblIFA, tblultrasondresult, tblhb, tblusg, tblCalcium, tblHeight,
            tbltt2, tbltt1, tblttbooster, tbl_Bmi, tblHeightet;
    Dialog datepic;
    DataProvider dataProvider;
    Global global;
    Float actualheight;
    Button btnSave;
    ArrayAdapter<String> adapter;
    String HRP = "";
    String condition = "";
    public ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();
    public ArrayList<TblANCVisit> visit = new ArrayList<TblANCVisit>();
    public ArrayList<MstCommon> common = new ArrayList<MstCommon>();
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    int heightflag = 0;

    @SuppressLint("SimpleDateFormat")
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.ancform);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
        tvRegistration = (TextView) findViewById(R.id.tvRegistration);
        checkup = (TextView) findViewById(R.id.checkup);
        tvLMPdate = (TextView) findViewById(R.id.tvLMPdate);
        tvBmi = (TextView) findViewById(R.id.tvBmi);
        tvTT1date = (TextView) findViewById(R.id.tvTT1date);
        tvvisitdate = (TextView) findViewById(R.id.tvvisitdate);
        tvTT2date = (TextView) findViewById(R.id.tvTT2date);
        tvTT3date = (TextView) findViewById(R.id.tvTT3date);
        etpregname = (EditText) findViewById(R.id.etpregname);
        tvMCTSID = (TextView) findViewById(R.id.tvMCTSID);
        etweight = (EditText) findViewById(R.id.etweight);
        etcalciumno = (EditText) findViewById(R.id.etcalciumno);
        etHemoglobin = (EditText) findViewById(R.id.etHemoglobin);
        spinCheckup_place = (Spinner) findViewById(R.id.spinCheckup_place);
        spinBPYN = (Spinner) findViewById(R.id.spinBPYN);
        spintt1 = (Spinner) findViewById(R.id.spintt1);
        spinCalcium = (Spinner) findViewById(R.id.spinCalcium);
        spinurineYN = (Spinner) findViewById(R.id.spinurineYN);
        spinVDRLYN = (Spinner) findViewById(R.id.spinVDRLYN);
        spinHIVYN = (Spinner) findViewById(R.id.spinHIVYN);
        spincheckup = (Spinner) findViewById(R.id.spincheckup);
        spindangersign = (Spinner) findViewById(R.id.spindangersign);
        spinultrasoundresult = (Spinner) findViewById(R.id.spinultrasoundresult);
        spin_inch = (Spinner) findViewById(R.id.spin_inch);
        spin_feet = (Spinner) findViewById(R.id.spin_feet);
        etsystolic = (EditText) findViewById(R.id.etsystolic);
        etDiastolic = (EditText) findViewById(R.id.etDiastolic);
        etIFAnumber = (EditText) findViewById(R.id.etIFAnumber);
        tblbp = (TableRow) findViewById(R.id.tblbp);
        tblHeight = (TableRow) findViewById(R.id.tblHeight);
        tblHeightet = (TableRow) findViewById(R.id.tblHeightet);
        tbl_Bmi = (TableRow) findViewById(R.id.tbl_Bmi);
        tblCalcium = (TableRow) findViewById(R.id.tblCalcium);
        tblhb = (TableRow) findViewById(R.id.tblhb);
        tbltt2 = (TableRow) findViewById(R.id.tbltt2);
        tbltt1 = (TableRow) findViewById(R.id.tbltt1);
        tblttbooster = (TableRow) findViewById(R.id.tblttbooster);
        tblusg = (TableRow) findViewById(R.id.tblusg);
        tblIFA = (TableRow) findViewById(R.id.tblIFA);
        spinurinesugar = (Spinner) findViewById(R.id.spinurinesugar);
        spinurineAlbumin = (Spinner) findViewById(R.id.spinurineAlbumin);
        spinUltrasoundYN = (Spinner) findViewById(R.id.spinUltrasoundYN);
        spinUltraSoundConductedby = (Spinner) findViewById(R.id.spinUltrasoundConductedby);
        spinIFA = (Spinner) findViewById(R.id.spinIFA);
        spinHBYN = (Spinner) findViewById(R.id.spinHBYN);
        tblurinesugar = (TableRow) findViewById(R.id.tblurinesugar);
        tblultrasondresult = (TableRow) findViewById(R.id.tblultrasondresult);
        tblurinealbumin = (TableRow) findViewById(R.id.tblurinealbumin);
        tblultrasondconduct = (TableRow) findViewById(R.id.tblultrasondconduct);
        etHeight = (EditText) findViewById(R.id.etHeight);
        btnSave = (Button) findViewById(R.id.btnSave);
        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            Tracker t = ((Global) getApplication())
                    .getTracker(TrackerName.APP_TRACKER);
            t.setScreenName("Anc Checkup");
            t.send(new HitBuilders.AppViewBuilder().build());
        }
        if (global.getVisitno() > 0) {
            checkup.setText(getResources().getString(R.string.checkupmsg) + "-"
                    + String.valueOf(global.getVisitno()));

        }
        tvRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvRegistration, 1);
            }
        });
        // BMI = ( Weight in Kilograms
        // (Height in centimeters) x (Height in centimeters) ) x 10,000

        tvTT2date.setEnabled(false);
        tvLMPdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvLMPdate, 0);
            }
        });
        etweight.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    Float weight = Float.valueOf(s.toString());
                    if (weight > 150) {
                        etweight.setText("");
                        CustomAlert(getResources().getString(
                                R.string.enterweight));
                    }
                    if (etHeight.getText() != null
                            && etHeight.getText().toString().length() > 0
                            && !etweight.getText().toString()
                            .equalsIgnoreCase(".") && !etHeight.getText().toString()
                            .equalsIgnoreCase(".")) {

                        Float height = Float.valueOf(etHeight.getText()
                                .toString());
                        Float newHeight = height * height;
                        Float Bmi = (weight / newHeight) * 10000;
                        if (Bmi > 18.5 && Bmi <= 25) {
                            tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.Green));
                            tvBmi.setBackgroundColor(getResources().getColor(R.color.Green));
                        } else {
                            tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.White));
                            tvBmi.setBackgroundResource(R.drawable.textbox);
                        }
                        tvBmi.setText(String.valueOf(Bmi));
                    }
                } else {
                    tvBmi.setText("" + 0);
                }

            }
        });
        etHeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    Float height = Float.valueOf(s.toString());
                    if (height > 200) {
                        etHeight.setText("");
                        CustomAlert(getResources().getString(
                                R.string.enterheight));
                    }
                    if (etweight.getText() != null
                            && etweight.getText().toString().length() > 0 && !etweight.getText().toString()
                            .equalsIgnoreCase(".")) {
                        Double weight = Double.valueOf(etweight.getText()
                                .toString());
                        Float newHeight = height * height;
                        Double Bmi = (weight / newHeight) * 10000;
                        if (Bmi > 18.5 && Bmi <= 25) {
                            tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.Green));
                            tvBmi.setBackgroundColor(getResources().getColor(R.color.Green));
                        } else {
                            tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.White));
                            tvBmi.setBackgroundColor(getResources().getColor(R.color.White));
                        }
                        tvBmi.setText(String.valueOf(Bmi));
                        actualheight = height;
                    }
                } else {
                    tvBmi.setText("" + 0);
                }
            }
        });
        etIFAnumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 250) {
                        etIFAnumber.setText("");
                        CustomAlert(getResources().getString(R.string.enterifa));
                    }
                }

            }
        });
        etHemoglobin.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")
                        && !s.toString().equalsIgnoreCase(".")) {
                    float count = Float.valueOf(s.toString());
                    if (count > 16) {
                        etHemoglobin.setText("");
                        CustomAlert(getResources().getString(
                                R.string.enterhemoglobin));
                    }
                }

            }
        });
        etcalciumno.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 400) {
                        etcalciumno.setText("");
                        CustomAlert(getResources().getString(
                                R.string.entercalcium));

                    }
                }

            }
        });
        etsystolic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 200) {
                        etsystolic.setText("");
                        CustomAlert(getResources().getString(R.string.Systolic));
                    }
                }

            }
        });
        etDiastolic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 150) {
                        etDiastolic.setText("");
                        CustomAlert(getResources()
                                .getString(R.string.Diastolic));

                    }
                }

            }
        });
        tvTT1date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvTT1date, 1);
            }
        });
        tvvisitdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvvisitdate, 1);
            }
        });
        tvTT2date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvTT2date, 1);
            }
        });
        tvTT3date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvTT3date, 1);
            }
        });
        tvTT1date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (tvTT1date.getText() != null
                        && tvTT1date.getText().toString().length() > 0) {
                    if (tvLMPdate.getText() != null
                            && tvLMPdate.getText().toString().length() > 0) {
                        String tt1 = tvTT1date.getText().toString();
                        String lmp = tvLMPdate.getText().toString();
                        tvTT2date.setEnabled(true);
                        SimpleDateFormat dfDate = new SimpleDateFormat(
                                "dd-MM-yyyy", Locale.US);
                        Date d = null;
                        Date d1 = null;
                        String lmpdate = "";
                        if (lmp != null && lmp.length() > 0) {
                            String[] Cdt = lmp.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            lmpdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        String visitdate = "";
                        if (tt1 != null && tt1.length() > 0) {
                            String[] Cdt = tt1.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            visitdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        // Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(lmpdate);
                            d1 = dfDate.parse(visitdate);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        System.out.println(diffInDays);
                        if (diffInDays <= 0) {
                            tvTT1date.setText("");
                            CustomAlertSave(getResources().getString(
                                    R.string.tt1));
                        }
                    }
                }
            }
        });

        tvvisitdate.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                int diffInDays = 0;
                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy",
                        Locale.US);
                java.util.Date d = null;
                java.util.Date d1 = null;
                // Calendar cal = Calendar.getInstance();
                if (tvvisitdate.getText() != null
                        && tvvisitdate.getText().toString().length() > 0) {
                    if (tvLMPdate.getText() != null
                            && tvLMPdate.getText().toString().length() > 0) {
                        String tt1 = tvvisitdate.getText().toString();
                        String lmp = tvLMPdate.getText().toString();
                        String lmpdate = "";
                        if (lmp != null && lmp.length() > 0) {
                            String[] Cdt = lmp.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            lmpdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        String visitdate = "";
                        if (tt1 != null && tt1.length() > 0) {
                            String[] Cdt = tt1.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            visitdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        try {
                            d = dfDate.parse(lmpdate);
                            d1 = dfDate.parse(visitdate);
                            diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        System.out.println(diffInDays);
                        if (diffInDays <= 0) {
                            tvvisitdate.setText("");
                            CustomAlertSave(getResources().getString(
                                    R.string.Visitdatelmp));
                        } else {
                            int visitno = global.getVisitno();
                            int diff = visitno - 1;
                            if (visitno == 2 || visitno == 3 || visitno == 4) {
                                String sql = "select CheckupVisitDate from tblANCVisit where PWGUID='"
                                        + global.getsGlobalPWGUID()
                                        + "' and Visit_No=" + diff + "";
                                String result = dataProvider.getRecord(sql);
                                String visit2 = tvvisitdate.getText()
                                        .toString();
                                String visit1 = Validate
                                        .changeDateFormat(result);

                                try {
                                    d = dfDate.parse(visit1);
                                    d1 = dfDate.parse(visit2);
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }

                                diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                                System.out.println(diffInDays);
                                if (diffInDays <= 30) {
                                    tvvisitdate.setText("");

                                    CustomAlertSave(getResources().getString(
                                            R.string.visitdate)
                                            + " "
                                            + global.getVisitno()
                                            + " "
                                            + getResources().getString(
                                            R.string.mustbegreater)
                                            + "");
                                }
                            }
                        }

                    }
                }
            }
        });
        tvTT2date.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (tvTT2date.getText() != null
                        && tvTT2date.getText().toString().length() > 0) {
                    if (tvTT1date.getText() != null
                            && tvTT1date.getText().toString().length() > 0) {
                        String tt2 = tvTT2date.getText().toString();
                        String tt1 = tvTT1date.getText().toString();
                        SimpleDateFormat dfDate = new SimpleDateFormat(
                                "dd-MM-yyyy", Locale.US);
                        java.util.Date d = null;
                        java.util.Date d1 = null;
                        String lmpdate = "";
                        if (tt2 != null && tt2.length() > 0) {
                            String[] Cdt = tt2.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            lmpdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        String visitdate = "";
                        if (tt1 != null && tt1.length() > 0) {
                            String[] Cdt = tt1.split("-");
                            String DD = Cdt[0];
                            String MM = Cdt[1];
                            String YYYY = Cdt[2];
                            if (MM.length() == 1) {
                                MM = "0" + Cdt[1];
                            }
                            visitdate = DD.trim() + "-" + MM.trim() + "-"
                                    + YYYY.trim();

                        }
                        // Calendar cal = Calendar.getInstance();
                        try {
                            d = dfDate.parse(visitdate);
                            d1 = dfDate.parse(lmpdate);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
                        System.out.println(diffInDays);
                        if (diffInDays < 28) {
                            tvTT2date.setText("");
                            CustomAlertSave(getResources().getString(
                                    R.string.tt2));
                        }
                    }
                }
            }
        });
        spinBPYN.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblbp.setVisibility(View.VISIBLE);
                } else {
                    tblbp.setVisibility(View.GONE);
                    etsystolic.setText("");
                    etDiastolic.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spincheckup.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 2) {
                    tblusg.setVisibility(View.VISIBLE);
                } else {
                    tblusg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinHBYN.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblhb.setVisibility(View.VISIBLE);

                } else {
                    tblhb.setVisibility(View.GONE);
                    etHemoglobin.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spinurineYN.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblurinealbumin.setVisibility(View.VISIBLE);
                    tblurinesugar.setVisibility(View.VISIBLE);
                } else {
                    tblurinealbumin.setVisibility(View.GONE);
                    tblurinesugar.setVisibility(View.GONE);
                    spinurinesugar.setSelection(0);
                    spinurineAlbumin.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinUltrasoundYN
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position == 1) {

                            tblultrasondresult.setVisibility(View.VISIBLE);
                        } else {
                            tblultrasondconduct.setVisibility(View.GONE);
                            tblultrasondresult.setVisibility(View.GONE);
                            spinUltraSoundConductedby.setSelection(0);
                            spinultrasoundresult.setSelection(0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        spinIFA.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblIFA.setVisibility(View.VISIBLE);
                } else {
                    tblIFA.setVisibility(View.GONE);
                    etIFAnumber.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinCalcium.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblCalcium.setVisibility(View.VISIBLE);
                } else {
                    tblCalcium.setVisibility(View.GONE);
                    etcalciumno.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinultrasoundresult
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position == 1) {
                            ((TextView) parent.getChildAt(0))
                                    .setTextColor(Color.GREEN);
                        } else if (position == 2) {
                            ((TextView) parent.getChildAt(0))
                                    .setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hrp = checkhrp();
                int icheck = 0;
                if (hrp == 1) {
                    icheck = sCheckValidation();
                    if (icheck == 1) {
                        sSavedata();
                        CustomAlertSave2(getResources().getString(
                                R.string.savesuccessfully));

                    } else {
                        showAlert(icheck);
                    }
                } else {
                    if (global.getHishRisk() != 1) {
                        global.setHishRisk(1);
                    }
                    CustomAlertHRP(HRP + getResources().getString(R.string.HRP));
                }
            }
        });
        spintt1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0) {
                    if (returnID(7, position) == 1) {
                        tblttbooster.setVisibility(View.VISIBLE);
                        tbltt1.setVisibility(View.GONE);
                        tbltt2.setVisibility(View.GONE);
                        tvTT1date.setText("");
                        tvTT2date.setText("");
                    } else {
                        tblttbooster.setVisibility(View.GONE);
                        tbltt1.setVisibility(View.VISIBLE);
                        tbltt2.setVisibility(View.VISIBLE);
                        tvTT3date.setText("");
                    }
                } else {
                    tblttbooster.setVisibility(View.GONE);
                    tbltt1.setVisibility(View.GONE);
                    tbltt2.setVisibility(View.GONE);
                    tvTT1date.setText("");
                    tvTT2date.setText("");
                    tvTT3date.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        fillspiners(spinBPYN, 7, global.getLanguage());
        fillspiners(spintt1, 7, global.getLanguage());
        fillspiners(spinHBYN, 7, global.getLanguage());
        fillspiners(spinHIVYN, 7, global.getLanguage());
        fillspiners(spinurineYN, 7, global.getLanguage());
        fillspiners(spinVDRLYN, 21, global.getLanguage());
        fillspiners(spinCheckup_place, 101, global.getLanguage());
        fillspiners(spinurinesugar, 102, global.getLanguage());
        fillspiners(spinurineAlbumin, 102, global.getLanguage());
        fillspiners(spinUltrasoundYN, 7, global.getLanguage());
        fillspiners(spinIFA, 7, global.getLanguage());
        fillspiners(spinCalcium, 7, global.getLanguage());
        fillspiners(spincheckup, 103, global.getLanguage());
        fillspiners(spinultrasoundresult, 13, global.getLanguage());
        fillspiners(spindangersign, 24, global.getLanguage());


        if (global.getVisitno() == 1) {
            fillspiners(spindangersign, 30, global.getLanguage());
            tvLMPdate.setEnabled(true);
            fillspinnerheight();
            spin_feet.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub
                    if (heightflag == 0)
                        etHeight.setText(String.valueOf(calculateheight()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });
            spin_inch.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub
                    if (heightflag == 0) {
                        etHeight.setText(String.valueOf(calculateheight()));
                    } else {
                        heightflag = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });
        } else if (global.getVisitno() == 2) {
            fillspiners(spindangersign, 31, global.getLanguage());
            tvLMPdate.setEnabled(false);
        } else {
            fillspiners(spindangersign, 24, global.getLanguage());
            tvLMPdate.setEnabled(false);
        }
        if (global.getsGlobalPWGUID().length() > 0) {
            showwomandata();
        }
        if (global.getsGlobalPWGUID().length() > 0
                && global.getsGlobalANCVisitGUID().length() > 0) {
            showdata();

        }
        if (global.getVHND_flag() == 1) {
            spinCheckup_place.setSelection(1);
        }
        int visitno = global.getVisitno() + 1;
        if (global.getVisitno() <= 4) {
            String sql = "select CheckupVisitDate  from tblANCVisit where Visit_No="
                    + visitno
                    + " and PWGUID='"
                    + global.getsGlobalPWGUID()
                    + "'";
            String CheckupVisitDate = dataProvider.getRecord(sql);
            if (CheckupVisitDate != null && CheckupVisitDate.length() > 0) {
                btnSave.setVisibility(View.GONE);

            }
            if (global.getVisitno() == 1) {
                String sql1 = "select CheckupVisitDate  from tblANCVisit where Visit_No=2 and PWGUID='"
                        + global.getsGlobalPWGUID() + "'";
                String CheckupVisitDate1 = dataProvider.getRecord(sql1);
                if (CheckupVisitDate1 != null && CheckupVisitDate1.length() > 0) {
                    tvTT1date.setEnabled(false);
                    tvTT1date.setEnabled(false);
                }
            } else if (global.getVisitno() > 1) {

            }
        }

    }

    public void fillspinnerheight() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, feet);
        spin_feet.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, inch);
        spin_inch.setAdapter(adapter);
    }

    public double calculateheight() {
        double height = 0;
        try {
            int feet = Integer.valueOf(spin_feet.getSelectedItem().toString());
            int inch = Integer.valueOf(spin_inch.getSelectedItem().toString());
            height = ((feet * 12) + inch) * 2.54;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public int sCheckValidation() {
        try {
            if (!tvvisitdate.getText().toString().equalsIgnoreCase("null")
                    && tvvisitdate.getText().toString().length() == 0) {
                return 7;
            }
            if (etweight.getText().toString() != null
                    && etweight.getText().toString().length() > 0
                    && !etweight.getText().toString().equalsIgnoreCase(".")) {
                float weight = Float.parseFloat(etweight.getText().toString());
                if (weight < 30) {
                    return 3;
                } else if (weight > 150) {
                    return 3;
                }

            }
            if (etHeight.getText().toString().length() == 0 && tblHeightet.getVisibility() == View.VISIBLE) {
                return 2;
            }
            if (tblHeightet.getVisibility() == View.VISIBLE && etHeight.getText().toString() != null
                    && etHeight.getText().toString().length() > 0) {
                Float height = Float.valueOf(etHeight.getText().toString());
                if (height < 121) {
                    return 2;
                } else if (height > 200) {
                    return 2;
                }

            }
            if (etsystolic.getText().toString() != null
                    && etsystolic.getText().toString().length() > 0) {
                int systolic = Integer
                        .parseInt(etsystolic.getText().toString());
                if (systolic < 90) {
                    return 8;
                } else if (systolic > 200) {
                    return 8;
                }

            }
            if (etDiastolic.getText().toString() != null
                    && etDiastolic.getText().toString().length() > 0) {
                int Diastolic = Integer.parseInt(etDiastolic.getText()
                        .toString());
                if (Diastolic < 50) {
                    return 9;
                } else if (Diastolic > 150) {
                    return 9;
                }

            }
            if (etHemoglobin.getText().toString() != null
                    && etHemoglobin.getText().toString().length() > 0) {
                int hemoglobin = (int) (Math.round(Float.valueOf(etHemoglobin
                        .getText().toString()) - .5));
                if (hemoglobin < 4) {
                    return 6;
                } else if (hemoglobin > 16) {
                    return 6;
                }
            }

            if (etIFAnumber.getText().toString() != null
                    && etIFAnumber.getText().toString().length() > 0
                    && etIFAnumber.getText().toString().length() < 2) {
                return 4;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlert(getResources().getString(R.string.enterheight));
        } else if (iCheck == 3) {
            CustomAlert(getResources().getString(R.string.enterweight));
        } else if (iCheck == 4) {
            CustomAlert(getResources().getString(R.string.enterifa));
        } else if (iCheck == 5) {
            CustomAlert(getResources().getString(R.string.entercalcium));
        } else if (iCheck == 6) {
            CustomAlert(getResources().getString(R.string.enterhemoglobin));
        } else if (iCheck == 7) {
            CustomAlert(getResources().getString(R.string.entervisitdate));
        } else if (iCheck == 8) {
            CustomAlert(getResources().getString(R.string.Systolic));
        } else if (iCheck == 9) {
            CustomAlert(getResources().getString(R.string.Diastolic));
        }
    }

    public void CustomAlert(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public int checkhrp() {
        HRP = "";
        if (spindangersign.getSelectedItemPosition() > 0) {
            condition = returnString(
                    spindangersign.getSelectedItemPosition() - 1, 24,
                    global.getLanguage());
            HRP = condition;

        }
        double Height = 0;
        if (etHeight.getText().toString() != null
                && etHeight.getText().toString().length() > 0) {
            Height = Double.valueOf(etHeight.getText().toString());
        }
        if (etHeight.getText().toString().length() > 0 && Height < 145) {
            condition = getResources().getString(R.string.lessheight);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + getResources().getString(R.string.lessheight);
                ;
            }
        }

        if (etweight.getText().toString().length() > 0
                && Float.valueOf(etweight.getText().toString()) < 45) {
            condition = getResources().getString(R.string.lessWeight);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + getResources().getString(R.string.lessWeight);
            }
        }
        if (etsystolic.getText().toString().length() > 0
                && Integer.valueOf(etsystolic.getText().toString()) >= 140) {
            condition = getResources().getString(R.string.moresys);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + getResources().getString(R.string.moresys);
            }
        }
        if (etDiastolic.getText().toString().length() > 0
                && Integer.valueOf(etDiastolic.getText().toString()) >= 90) {
            condition = getResources().getString(R.string.moredia);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + getResources().getString(R.string.moredia);
            }
        }
        if (etHemoglobin.getText().toString().length() > 0
                && Float.valueOf(etHemoglobin.getText().toString()) < 7) {
            condition = getResources().getString(R.string.Severeanaemia);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + ","
                        + getResources().getString(R.string.Severeanaemia);
            }
        }
        if (spinVDRLYN.getSelectedItemPosition() > 0
                && spinVDRLYN.getSelectedItemPosition() == 2) {
            condition = getResources().getString(R.string.vdrlpos);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + getResources().getString(R.string.vdrlpos);
            }
        }
        // if(spinHIVYN.getSelectedItemPosition()>0 &&
        // spinHIVYN.getSelectedItemPosition()==2){
        // condition=getResources().getString(
        // R.string.hivpos);
        // if(HRP==null || HRP.equalsIgnoreCase("null")||HRP.length()==0){
        // HRP=condition;
        // }else{
        // HRP=HRP+","+getResources().getString(
        // R.string.hivpos);
        // }
        // }
        if (HRP.length() > 7) {
            return 8;
        }
        return 1;
    }

    public void CustomAlertHRP(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

                int icheck = 0;

                icheck = sCheckValidation();
                if (icheck == 1) {
                    String sql = "update tblPregnant_woman set HighRisk=1,IsEdited=1 where PWGUID='" + global.getsGlobalPWGUID() + "'";
                    dataProvider.executeSql(sql);
                    sSavedata();
                    CustomAlertSave2(getResources().getString(
                            R.string.savesuccessfully));

                } else {
                    showAlert(icheck);
                }

            }
        });

        // Display the dialog
        dialog.show();

    }

    public String returnString(int POS, int iFlag, int Language) {
        common = dataProvider.getCommonRecord(Language, iFlag);

        return common.get(POS).getValue();

    }

    public void showwomandata() {
        try {
            int ashaid = 0;
            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                ashaid = Integer.valueOf(global.getsGlobalAshaCode());
            }

            pregnant = dataProvider.getPregnantWomendata(
                    global.getsGlobalPWGUID(), 1, ashaid);
            if (pregnant != null && pregnant.size() > 0) {
                if (pregnant.get(0).getPWName().length() > 0) {
                    etpregname.setText(pregnant.get(0).getPWName());
                } else {
                    etpregname.setText("");
                }
                if (pregnant.get(0).getPWRegistrationDate().length() > 0) {
                    tvRegistration.setText(Validate.changeDateFormat(pregnant
                            .get(0).getPWRegistrationDate()));
                } else {
                    tvRegistration.setText("");
                }
                if (pregnant.get(0).getLMPDate().length() > 0) {
                    tvLMPdate.setText(Validate.changeDateFormat(pregnant.get(0)
                            .getLMPDate()));

                } else {
                    tvLMPdate.setText("");
                }
                if (pregnant.get(0).getMotherMCTSID().length() > 0) {
                    tvMCTSID.setText(pregnant.get(0).getMotherMCTSID());
                } else {
                    tvMCTSID.setText("");
                }

                if (pregnant.get(0).getPWHeight() > 0) {
                    etHeight.setText(String.valueOf(pregnant.get(0)
                            .getPWHeight()));
                    heightflag = 1;
                } else {
                    etHeight.setText("");
                }
                if (global.getVisitno() == 1) {
                    tblHeight.setVisibility(View.VISIBLE);
                    tblHeightet.setVisibility(View.VISIBLE);
                } else {
                    tblHeight.setVisibility(View.GONE);
                    tblHeightet.setVisibility(View.GONE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showdata() {
        try {
            String dt = "";
            visit = dataProvider.getTbl_VisitANCData(global.getsGlobalPWGUID(),
                    global.getsGlobalANCVisitGUID(), 1);
            if (visit != null && visit.size() > 0) {
                if (visit.get(0).getTTfirstDoseDate() != null
                        && visit.get(0).getTTfirstDoseDate().length() > 0) {
                    tvTT1date.setText(Validate.changeDateFormat(visit.get(0)
                            .getTTfirstDoseDate()));
                    if (global.getVisitno() == 1) {
                        tvTT1date.setEnabled(true);
                    } else {
                        tvTT1date.setEnabled(false);
                    }
                } else {
                    tvTT1date.setText("");
                }
                dt = visit.get(0).getCheckupVisitDate();
                if (visit.get(0).getCheckupVisitDate() != null
                        && visit.get(0).getCheckupVisitDate().length() > 0) {
                    dt = visit.get(0).getCheckupVisitDate();
                    tvvisitdate.setText(Validate.changeDateFormat(dt)
                            .toString());
                } else {
                    tvvisitdate.setText("");
                }
                if (visit.get(0).getTTsecondDoseDate() != null
                        && visit.get(0).getTTsecondDoseDate().length() > 0) {
                    tvTT2date.setText(Validate.changeDateFormat(visit.get(0)
                            .getTTsecondDoseDate()));

                    if (global.getVisitno() == 1) {
                        tvTT2date.setEnabled(true);
                    } else {
                        tvTT2date.setEnabled(false);
                    }
                } else {
                    tvTT2date.setText("");

                }
                if (visit.get(0).getTTBoosterDate() != null
                        && visit.get(0).getTTBoosterDate().length() > 0) {
                    tvTT3date.setText(Validate.changeDateFormat(visit.get(0)
                            .getTTBoosterDate()));
                    tvTT3date.setEnabled(false);
                } else {
                    tvTT3date.setText("");
                }
                if (visit.get(0).getBirthWeight() > 0) {
                    etweight.setText(String.valueOf(visit.get(0)
                            .getBirthWeight()));
                } else {
                    etweight.setText("");
                }
                if (visit.get(0).getHemoglobin() > 0) {
                    spinHBYN.setSelection(1);
                    etHemoglobin.setText(String.valueOf(visit.get(0)
                            .getHemoglobin()));
                } else {
                    etHemoglobin.setText("");
                }
                if (visit.get(0).getBP() > 0) {
                    spinBPYN.setSelection(returnPosition(7, visit.get(0)
                            .getBP()));
                } else {
                    spinBPYN.setSelection(0);
                }
                if (visit.get(0).getVDRLTest() > 0) {
                    spinVDRLYN.setSelection(returnPosition(21, visit.get(0)
                            .getVDRLTest()));
                } else {
                    spinVDRLYN.setSelection(0);
                }
                if (visit.get(0).getUrineTest() > 0) {
                    spinurineYN.setSelection(returnPosition(7, visit.get(0)
                            .getUrineTest()));
                } else {
                    spinurineYN.setSelection(0);
                }
                if (visit.get(0).getTT1TT2last2yr() > 0) {
                    spintt1.setSelection(returnPosition(7, visit.get(0)
                            .getTT1TT2last2yr()));
                } else {
                    spintt1.setSelection(0);
                }
                if (visit.get(0).getDangerSign() > 0) {
                    if (global.getVisitno() == 1) {
                        spindangersign.setSelection(returnPosition(30, visit
                                .get(0).getDangerSign()));
                    } else if (global.getVisitno() == 2) {
                        spindangersign.setSelection(returnPosition(31, visit
                                .get(0).getDangerSign()));
                    } else {
                        spindangersign.setSelection(returnPosition(24, visit
                                .get(0).getDangerSign()));
                    }
                } else {
                    spindangersign.setSelection(0);
                }
                if (visit.get(0).getUrineSugar() > 0) {
                    spinurinesugar.setSelection(returnPosition(102, visit
                            .get(0).getUrineSugar()));
                } else {
                    spinurinesugar.setSelection(0);
                }
                if (visit.get(0).getUrineAlbumin() > 0) {
                    spinurineAlbumin.setSelection(returnPosition(102, visit
                            .get(0).getUrineAlbumin()));
                } else {
                    spinurineAlbumin.setSelection(0);
                }
                if (visit.get(0).getHIVTest() > 0) {
                    spinHIVYN.setSelection(returnPosition(7, visit.get(0)
                            .getHIVTest()));
                } else {
                    spinHIVYN.setSelection(0);
                }
                if (visit.get(0).getCheckupPlace() > 0) {
                    spinCheckup_place.setSelection(returnPosition(101, visit
                            .get(0).getCheckupPlace()));
                } else {
                    spinCheckup_place.setSelection(0);
                }
                if (visit.get(0).getUltraSound() > 0) {
                    spinUltrasoundYN.setSelection(returnPosition(7, visit
                            .get(0).getUltraSound()));
                } else {
                    spinUltrasoundYN.setSelection(0);
                }
                if (visit.get(0).getBPResult().length() > 0) {
                    String[] bpvalue = visit.get(0).getBPResult().split(",");
                    etsystolic.setText(String.valueOf(bpvalue[0]));
                    etDiastolic.setText(String.valueOf(bpvalue[1]));
                } else {
                    etsystolic.setText("");
                    etDiastolic.setText("");
                }
                if (visit.get(0).getUltraSoundConductedby() > 0) {
                    spincheckup.setSelection(returnPosition(103, visit.get(0)
                            .getUltraSoundConductedby()));
                } else {
                    spincheckup.setSelection(0);
                }
                if (visit.get(0).getUltrasoundResult() > 0) {
                    spinultrasoundresult.setSelection(returnPosition(13, visit
                            .get(0).getUltrasoundResult()));
                } else {
                    spinultrasoundresult.setSelection(0);
                }
                if (visit.get(0).getIFARecieved() > 0) {
                    spinIFA.setSelection(returnPosition(7, visit.get(0)
                            .getIFARecieved()));
                } else {
                    spinIFA.setSelection(0);
                }
                if (visit.get(0).getNumberIFARecieved() > 0) {
                    etIFAnumber.setText(String.valueOf(visit.get(0)
                            .getNumberIFARecieved()));
                } else {
                    etIFAnumber.setText("");
                }
                if (visit.get(0).getCalciumReceived() > 0) {
                    spinCalcium.setSelection(returnPosition(7, visit.get(0)
                            .getCalciumReceived()));
                } else {
                    spinCalcium.setSelection(0);
                }
                if (visit.get(0).getCalciumTablet() > 0) {
                    etcalciumno.setText(String.valueOf(visit.get(0)
                            .getCalciumTablet()));
                } else {
                    etcalciumno.setText("");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sSavedata() {
        ContentValues data = new ContentValues();
        String TT1dt = "";
        String TT2dt = "";
        String TTbooster = "";
        String visitdt = "";
        double weight = 0;
        double Hemogloabin = 0;
        int BPYN = 0;
        int VDRLYN = 0;
        int UrineYN = 0;
        int UrineSugar = 0;
        int UrineAlbumin = 0;
        int HIVYN = 0;
        int Checkup_place = 0;
        int UltraSound = 0;
        int UltraSoundConductedby = 0;
        int IFARecieved = 0;
        int NumberIFARecieved = 0;
        String BPResult = "";
        int UltrasoundResult = 0;
        String CurrentDate = "";
        int DangerSign = 0;
        int CalciumReceived = 0;
        int CalciumTablet = 0;
        double PWHeight = 0;
        int TT1TT2last2yr = 0;
        if (global.getVisitno() == 1) {
            if (etHeight.getText().toString().length() > 0) {
                PWHeight = Double.valueOf(etHeight.getText().toString());
            }
        }
        CurrentDate = Validate.getcurrentdate();
        if (tvTT1date.getText().toString().length() > 0) {
            TT1dt = Validate.changeDateFormat(tvTT1date.getText().toString());
        }
        data.put("TTfirstDoseDate", TT1dt);
        if (tvvisitdate.getText().toString().length() > 0) {
            visitdt = Validate.changeDateFormat(tvvisitdate.getText()
                    .toString());
        }
        data.put("CheckupVisitDate", visitdt);
        if (tvTT2date.getText().toString().length() > 0) {
            TT2dt = Validate.changeDateFormat(tvTT2date.getText().toString());
        }
        data.put("TTsecondDoseDate", TT2dt);
        if (tvTT3date.getText().toString().length() > 0) {
            TTbooster = Validate.changeDateFormat(tvTT3date.getText()
                    .toString());
        }
        data.put("TTBoosterDate", TTbooster);
        if (etweight.getText().toString().length() > 0) {
            weight = Double.valueOf(etweight.getText().toString());
        }
        data.put("BirthWeight", weight);
        if (etHemoglobin.getText().toString().length() > 0) {
            Hemogloabin = Double.valueOf(etHemoglobin.getText().toString());
        }
        data.put("Hemoglobin", Hemogloabin);
        if (spinBPYN.getSelectedItemPosition() > 0) {
            BPYN = returnID(7, spinBPYN.getSelectedItemPosition());
        }
        data.put("BP", BPYN);
        if (spintt1.getSelectedItemPosition() > 0) {
            TT1TT2last2yr = returnID(7, spintt1.getSelectedItemPosition());
        }
        data.put("TT1TT2last2yr", TT1TT2last2yr);
        if (etsystolic.getText().toString().length() > 0
                && etDiastolic.getText().toString().length() > 0) {
            BPResult = etsystolic.getText().toString() + ","
                    + etDiastolic.getText().toString();
        } else {
            BPResult = "0" + "," + "0";
        }
        data.put("BPResult", BPResult);
        if (spinVDRLYN.getSelectedItemPosition() > 0) {
            VDRLYN = returnID(21, spinVDRLYN.getSelectedItemPosition());
        }
        data.put("VDRLTest", VDRLYN);
        if (spindangersign.getSelectedItemPosition() > 0) {
            if (global.getVisitno() == 1) {
                DangerSign = returnID(30,
                        spindangersign.getSelectedItemPosition());
            } else if (global.getVisitno() == 2) {
                DangerSign = returnID(31,
                        spindangersign.getSelectedItemPosition());
            } else {
                DangerSign = returnID(24,
                        spindangersign.getSelectedItemPosition());
            }
        }
        data.put("DangerSign", DangerSign);
        if (spinurineYN.getSelectedItemPosition() > 0) {

            UrineYN = returnID(7, spinurineYN.getSelectedItemPosition());

        }
        data.put("UrineTest", UrineYN);
        if (spinurinesugar.getSelectedItemPosition() > 0) {
            UrineSugar = returnID(102, spinurinesugar.getSelectedItemPosition());
        }
        data.put("UrineSugar", UrineSugar);
        if (spinurineAlbumin.getSelectedItemPosition() > 0) {
            UrineAlbumin = returnID(102,
                    spinurineAlbumin.getSelectedItemPosition());
        }
        data.put("UrineAlbumin", UrineAlbumin);
        if (spinHIVYN.getSelectedItemPosition() > 0) {
            HIVYN = returnID(7, spinHIVYN.getSelectedItemPosition());
        }
        data.put("HIVTest", HIVYN);
        if (spinCheckup_place.getSelectedItemPosition() > 0) {
            Checkup_place = returnID(101,
                    spinCheckup_place.getSelectedItemPosition());
        }
        data.put("CheckupPlace", Checkup_place);
        if (spinUltrasoundYN.getSelectedItemPosition() > 0) {
            UltraSound = returnID(7, spinUltrasoundYN.getSelectedItemPosition());
        }
        data.put("UltraSound", UltraSound);
        if (spincheckup.getSelectedItemPosition() > 0) {
            UltraSoundConductedby = returnID(103,
                    spincheckup.getSelectedItemPosition());
        }
        data.put("UltraSoundConductedby", UltraSoundConductedby);
        if (spinultrasoundresult.getSelectedItemPosition() > 0) {
            UltrasoundResult = returnID(13,
                    spinultrasoundresult.getSelectedItemPosition());
        }
        data.put("UltrasoundResult", UltrasoundResult);
        if (spinIFA.getSelectedItemPosition() > 0) {
            IFARecieved = returnID(7, spinIFA.getSelectedItemPosition());
        }
        data.put("IFARecieved", IFARecieved);
        if (etIFAnumber.getText().toString().length() > 0) {
            NumberIFARecieved = Integer.valueOf(etIFAnumber.getText()
                    .toString());
        } else {
            NumberIFARecieved = 0;
        }
        if (spinCalcium.getSelectedItemPosition() > 0) {
            CalciumReceived = returnID(7, spinCalcium.getSelectedItemPosition());
        }
        data.put("CalciumReceived", CalciumReceived);
        if (etcalciumno.getText().toString().length() > 0) {
            CalciumTablet = Integer.valueOf(etcalciumno.getText().toString());
        } else {
            CalciumTablet = 0;
        }
        data.put("CalciumTablet", CalciumTablet);
        data.put("NumberIFARecieved", NumberIFARecieved);
        data.put("PWGUID", global.getsGlobalPWGUID());
        data.put("VisitGUID", global.getsGlobalANCVisitGUID());
        data.put("IsEdited", 1);
        data.put("IsUploaded", 1);
        data.put("UpdatedOn", CurrentDate);
        String LMPDATE = Validate.changeDateFormat(tvLMPdate.getText()
                .toString());

        dataProvider.UpdatetblANCVisit(data, global.getsGlobalPWGUID(),
                global.getsGlobalANCVisitGUID());
        if (global.getVisitno() == 1) {
            String sql = "Update  tblPregnant_woman set LMPDate='" + LMPDATE
                    + "', PWHeight=" + PWHeight + ", HighRisk="
                    + global.getHishRisk() + ",IsEdited=1 ,UpdatedOn='" + Validate.getcurrentdate() + "' where PWGUID='"
                    + global.getsGlobalPWGUID() + "'";

            dataProvider.executeSql(sql);
        }
        int visitno = global.getVisitno() + 1;
        if (global.getVisitno() < 4) {
            String sql1 = "update tblANCVisit set TT1TT2last2yr="
                    + TT1TT2last2yr + ", TTfirstDoseDate='" + TT1dt
                    + "',TTsecondDoseDate='" + TT2dt + "',TTBoosterDate='"
                    + TTbooster + "',IsEdited=1 where PWGUID='"
                    + global.getsGlobalPWGUID() + "' and VisitGUID='" + global.getsGlobalANCVisitGUID()
                    + "'";
            dataProvider.executeSql(sql1);
        }
    }

    public void ShowDatePicker(final TextView tVvisit, final int flag) {

        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        // TODO Auto-generated method stub
        datepic = new Dialog(this, R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);
        Button btnset = (Button) datepic.findViewById(R.id.set);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);

        datepic.show();

        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMaxDate((long) date.getTime());
        if (flag == 0) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.YEAR, -1);
                date = cal.getTime();
                datetext.setMinDate((long) date.getTime());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == 1) {
            if (tvLMPdate.getText().toString().length() > 0) {
                try {
                    Date date1;
                    SimpleDateFormat formatter = new SimpleDateFormat(
                            "dd-MM-yyyy");
                    date1 = formatter.parse(tvLMPdate.getText().toString());
                    long minDate = date1.getTime();
                    datetext.setMinDate(minDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        btncancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });

        btnset.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("unused")
            public void onClick(View v) {
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
                if (flag == 0) {
                    tvvisitdate.setText("");
                    tvTT1date.setText("");
                    tvTT2date.setText("");
                    tvTT3date.setText("");
                    tvRegistration.setText("");
                } else {
                    if (tvTT1date.getText() != null
                            && tvTT1date.getText().toString().length() > 0
                            || tvTT2date.getText() != null
                            && tvTT2date.getText().toString().length() > 0) {
                        tvTT3date.setText("");
                        tvTT3date.setEnabled(false);
                    } else if (tvTT3date.getText() != null
                            && tvTT3date.getText().toString().length() > 0) {
                        tvTT1date.setText("");
                        tvTT2date.setText("");
                        tvTT2date.setEnabled(false);
                        tvTT1date.setEnabled(false);
                    }
                    if (tvTT1date.getText() != null
                            && tvTT1date.getText().toString().length() > 0) {
                        tvTT2date.setEnabled(true);
                    } else {
                        tvTT2date.setEnabled(false);
                    }
                }
                datepic.dismiss();
            }
        });

    }

    public void fillspiners(Spinner spin, int iFlag, int languageID) {
        common = dataProvider.getCommonRecord(languageID, iFlag);

        String Where[] = new String[common.size() + 1];
        Where[0] = getResources().getString(R.string.select);

        for (int i = 1; i < common.size() + 1; i++) {
            Where[i] = common.get(i - 1).getValue();
            // iBlockID[i] = Block.get(i).getBlockID();

        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Where);

        spin.setAdapter(adapter);

    }

    public int returnPosition(int flag, int ID) {
        int ipos = 0;
        ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
        combobox = dataProvider.getCommonRecord(global.getLanguage(), flag);
        for (int i = 0; i < combobox.size(); i++) {
            if (ID == combobox.get(i).getID()) {
                ipos = i + 1;
            }
        }

        return ipos;
    }

    public int returnID(int flag, int pos) {
        int ipos = 0;
        ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
        combobox = dataProvider.getCommonRecord(global.getLanguage(), flag);
        if (pos > 0) {
            ipos = combobox.get(pos - 1).getID();
        }
        return ipos;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Anc.this, AncActivity.class);
        finish();
        startActivity(i);
    }

    public void CustomAlertSave(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        // Display the dialog
        dialog.show();

    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            GoogleAnalytics.getInstance(Anc.this).reportActivityStart(this);
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            GoogleAnalytics.getInstance(Anc.this).reportActivityStop(this);
        }
    }

    public void CustomAlertSave2(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(Anc.this, AncActivity.class);
                startActivity(i);

            }
        });

        // Display the dialog
        dialog.show();

    }
}
