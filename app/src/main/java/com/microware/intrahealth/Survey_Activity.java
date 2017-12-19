package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.adapter.Survey_Activity_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHSurvey;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class Survey_Activity extends Activity {

    ImageView btnsearch, btnadd;
    Spinner spinVillageName;
    TextView totalcount;
    EditText etSearch;
    GridView HHSurveyGrid;
    DataProvider dataProvider;
    Global global;
    int VillageID = 0;
    ArrayList<Tbl_HHSurvey> hhsurvey = new ArrayList<Tbl_HHSurvey>();
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
Button btn_report;
    // private EasyTracker easyTracker2 = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.survey_activity);

        // easyTracker2 = EasyTracker.getInstance(Survey_Activity.this);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        btnsearch = (ImageView) findViewById(R.id.btnsearch);
        btn_report = (Button) findViewById(R.id.btn_report);
        totalcount = (TextView) findViewById(R.id.totalcount);
        btnadd = (ImageView) findViewById(R.id.btnadd);
        etSearch = (EditText) findViewById(R.id.etSearch);
        HHSurveyGrid = (GridView) findViewById(R.id.HHSurveyGrid);
        Tracker t = ((Global) getApplication())
                .getTracker(TrackerName.APP_TRACKER);
        t.setScreenName("Survey");
        String dimensionValue = "5";
        if (global.getsGlobalUserName() != null
                && global.getsGlobalUserName().length() > 0) {
            dimensionValue = global.getsGlobalUserName();
        }
        if (global.getiGlobalRoleID() == 3) {
            btnadd.setVisibility(View.GONE);
        }
        t.set("&userid", dimensionValue);
        t.enableAutoActivityTracking(true);

        t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
                .setAction("User Sign In").setNewSession()
                .setCustomDimension(1, dimensionValue).build());
        t.send(new HitBuilders.ScreenViewBuilder().build());
        etSearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0
                        && !Validate.isTextValid(etSearch.getText().toString())) {
                    if (VillageID == 0) {
                        fillgridsearch(etSearch.getText().toString(), 5, VillageID);
                    } else {
                        fillgridsearch(etSearch.getText().toString(), 3, VillageID);
                    }
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
                String search = s.toString();
                if (search.length() == 0) {
//                    AsyncTaskRunner runner = new AsyncTaskRunner(0, 0);
//                    runner.execute();
                    fillgrid1();
                }
            }
        });
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
               // etSearch.setText("");
                if (pos == 1) {
                    fillgridsearch("", 4, 0);
                } else if(pos>1) {
                    VillageID = Village.get(
                            spinVillageName.getSelectedItemPosition() - 2)
                            .getVillageID();
                    fillgridsearch("", 4, VillageID);

                }else{
                    VillageID = 0;
                    fillgridsearch("", 1, VillageID);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Survey_Activity.this, ReportIndicatorListHH.class);
                i.putExtra("flag", 1);
                finish();
                startActivity(i);
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String searchcriteria = "";
                if (!Validate.isTextValid(etSearch.getText().toString())) {
                    searchcriteria = etSearch.getText().toString();
                }
                if (searchcriteria.length() > 0) {
                    if (searchcriteria.length() > 0
                            && searchcriteria.length() == 1) {
                        searchcriteria = searchcriteria;
                    } else if (searchcriteria.length() == 2) {
                        searchcriteria = searchcriteria;
                    }
                    fillgridsearch(searchcriteria, 3, VillageID);
                }
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Survey_Activity.this,
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID("");
                finish();
                startActivity(i);
            }
        });
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        String HH_GUID = Validate.random();
        global.setHH_GUID(String.valueOf(HH_GUID));
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStrings = sdf.format(date);
        dataProvider.getUserLogin(HH_GUID, global.getUserID(), "HH", "HH",
                localTime, dateStrings);
//        AsyncTaskRunner runner = new AsyncTaskRunner(0, 0);
//        runner.execute();
                fillgrid1();

    }

    public void fillgridsearch(String HHCode, int flag, int VillageID) {
        hhsurvey.clear();
        hhsurvey = dataProvider.getHHSurveyData("%" + HHCode + "%", flag,
                Integer.valueOf(global.getsGlobalAshaCode()), VillageID);
        if (hhsurvey != null) {
            // android.view.ViewGroup.LayoutParams params = HHSurveyGrid
            // .getLayoutParams();
            // Resources r = getResources();
            // float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            // 100, r.getDisplayMetrics());
            // int hi = Math.round(px);
            // int gridHeight1 = 0;
            // gridHeight1 = hi * hhsurvey.size();
            // params.height = gridHeight1;
            // HHSurveyGrid.setLayoutParams(params);

            HHSurveyGrid
                    .setAdapter(new Survey_Activity_adapter(this, hhsurvey));
            totalcount.setText(getResources().getString(
                    R.string.totalcountfamily)
                    + " -" + String.valueOf(hhsurvey.size()));
        } else {
            HHSurveyGrid
                    .setAdapter(new Survey_Activity_adapter(this, hhsurvey));
        }
    }
    public void fillgrid1() {
        hhsurvey.clear();
        hhsurvey = dataProvider.getHHSurveyData("", 1,
                Integer.valueOf(global.getsGlobalAshaCode()), 0);
        if (hhsurvey != null) {
            // android.view.ViewGroup.LayoutParams params = HHSurveyGrid
            // .getLayoutParams();
            // Resources r = getResources();
            // float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            // 100, r.getDisplayMetrics());
            // int hi = Math.round(px);
            // int gridHeight1 = 0;
            // gridHeight1 = hi * hhsurvey.size();
            // params.height = gridHeight1;
            // HHSurveyGrid.setLayoutParams(params);

            HHSurveyGrid
                    .setAdapter(new Survey_Activity_adapter(this, hhsurvey));
            totalcount.setText(getResources().getString(
                    R.string.totalcountfamily)
                    + " -" + String.valueOf(hhsurvey.size()));
        } else {
//            totalcount.setText(getResources().getString(
//                    R.string.totalcountfamily)
//                    + " -" + String.valueOf(hhsurvey.size()));
            HHSurveyGrid
                    .setAdapter(new Survey_Activity_adapter(this, hhsurvey));
        }
    }

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language,0);

        String sValue[] = new String[Village.size() + 2];
        sValue[0] = getResources().getString(R.string.select);
        sValue[1] = getResources().getString(R.string.villagenotlinked);
        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 2] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public int returnVillageName(int Villageid) {
        int iVillage = 0;
        if (Villageid > 0) {
            for (int i = 0; i < Village.size(); i++) {
                if (Villageid == Village.get(i).getVillageID()) {
                    iVillage = i + 1;
                }
            }
        }
        return iVillage;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        GoogleAnalytics.getInstance(Survey_Activity.this).reportActivityStart(
                this);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        GoogleAnalytics.getInstance(Survey_Activity.this).reportActivityStop(
                this);
    }

    private class AsyncTaskRunner extends AsyncTask<Integer, Integer, Void> {
        int s_flag;
        ProgressDialog dialog = new ProgressDialog(Survey_Activity.this);
        int radioflag;

        AsyncTaskRunner(int s_flag, int radioflag) {
            this.s_flag = s_flag;
            this.radioflag = radioflag;
        }

        @SuppressWarnings("static-access")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO Auto-generated method stub

            dialog.setMessage(getResources().getString(R.string.DataLoading));
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            // dialog.setMax(100);
            dialog.setIndeterminate(true);
            dialog.show();

            // dialog.show(Survey_Activity.this,
            //
            // getResources().getString(R.string.app_name), getResources()
            // .getString(R.string.DataLoading), true, true);

        }

        @Override
        protected Void doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            try {
                hhsurvey.clear();
                hhsurvey = dataProvider.getHHSurveyData("", 1,
                        Integer.valueOf(global.getsGlobalAshaCode()), 0);

            } catch (Exception e) {

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            // dialog.dismiss();
            // TODO Auto-generated method stub

            // dialog.show(getApplicationContext(), "jkl","jkl", false, false);
            if (hhsurvey != null && hhsurvey.size() > 0) {
                // android.view.ViewGroup.LayoutParams params = HHSurveyGrid
                // .getLayoutParams();
                // Resources r = getResources();
                // float px = TypedValue.applyDimension(
                // TypedValue.COMPLEX_UNIT_DIP, 1000,
                // r.getDisplayMetrics());
                // int hi = Math.round(px);
                // int gridHeight1 = 0;
                // gridHeight1 = hi * hhsurvey.size();
                // params.height = gridHeight1;
                // HHSurveyGrid.setLayoutParams(params);

                HHSurveyGrid.setAdapter(new Survey_Activity_adapter(
                        Survey_Activity.this, hhsurvey));
                totalcount.setText(getResources().getString(
                        R.string.totalcountfamily)
                        + " - " + hhsurvey.size());

            } else {

                totalcount.setText(getResources().getString(
                        R.string.totalcountfamily)
                        + " -" + String.valueOf(hhsurvey.size()));
                HHSurveyGrid.setAdapter(new Survey_Activity_adapter(
                        Survey_Activity.this, hhsurvey));

            }
            dialog.dismiss();

        }

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getHH_GUID(), endTime);
        if (global.getiGlobalRoleID() == 3) {
            Intent i = new Intent(Survey_Activity.this, AshaListForAnm.class);
            finish();
            i.putExtra("Flag", 1);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            Intent i = new Intent(Survey_Activity.this,
                    Dashboard_Activity.class);

            finish();
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }

}
