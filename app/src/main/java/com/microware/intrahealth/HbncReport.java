package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HbncReport extends Activity {

    Button btnimmunized, btncounselling;

    DataProvider dataProvider;
    Global global;
    Validate validate;

    // private EasyTracker easyTracker1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.hbncreport);

        // send our data!
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        btnimmunized = (Button) findViewById(R.id.btnimmunized);
        btncounselling = (Button) findViewById(R.id.btncounselling);

        btnimmunized.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(HbncReport.this,
                            Reportashaimmunizationlist.class);
                    startActivity(i);
                } else if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(HbncReport.this,
                            Reportanmimmunizationlist.class);
                    startActivity(i);
                }
            }
        });
        Tracker t = ((Global) getApplication())
                .getTracker(TrackerName.APP_TRACKER);
        t.setScreenName("Report");
        t.send(new HitBuilders.AppViewBuilder().build());

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        String immunize_GUID = Validate.random();
        global.setImmunize_GUID(immunize_GUID);
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStrings = sdf.format(date);
        dataProvider.getUserLogin(immunize_GUID, global.getUserID(),
                "Immunization", "Immunization", localTime, dateStrings);
        btncounselling.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(HbncReport.this,
                            ReportashaHnbclist.class);
                    startActivity(i);
                } else if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(HbncReport.this,
                            ReportanmHnbclist.class);
                    startActivity(i);
                }
            }
        });
    }

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        GoogleAnalytics.getInstance(HbncReport.this)
                .reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        GoogleAnalytics.getInstance(HbncReport.this)
                .reportActivityStop(this);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getImmunize_GUID(), endTime);
        if (validate.RetriveSharepreferenceInt("flag") == 1) {
            Intent i = new Intent(HbncReport.this,
                    newbornGrid.class);
            finish();
            startActivity(i);
            validate.SaveSharepreferenceInt("flag", 0);
        } else if (validate.RetriveSharepreferenceInt("flag") == 2) {
            Intent i = new Intent(HbncReport.this,
                    ImmunizationCounselling.class);
            finish();
            startActivity(i);
            validate.SaveSharepreferenceInt("flag", 0);
        } else if (validate.RetriveSharepreferenceInt("flag") == 3) {
            Intent i = new Intent(HbncReport.this,
                    ImunizationChildList.class);
            finish();
            startActivity(i);
            validate.SaveSharepreferenceInt("flag", 0);
        } else {
            Intent i = new Intent(HbncReport.this, Report_Module.class);
            finish();
            startActivity(i);
        }


    }
}
