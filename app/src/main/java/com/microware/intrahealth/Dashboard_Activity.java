package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.adapter.Anc_DueListadapter;
import com.microware.intrahealth.adapter.HBNCadapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class Dashboard_Activity extends Activity {

    Button btnLanguage, hh, mnch, cd, ncd, ah, fp, incentives, sync, vhnd, rd;
    // LinearLayout layoutsurvey, layoutsync,layoutmch;
    // TextView tvDashboardSynchronisation,tvDashboardSurvey;
    DataProvider dataProvider;
    Global global;
    private PendingIntent pendingIntent;
    String sCurrentLanguage = "";
    private Locale myLocale;
    String username = "";
    int iRoleID = 0;
    @SuppressWarnings("unused")
    private Menu menu;
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    int hbnccount = 0, pregcount = 0, ancchkccount = 0, anccount = 0,
            fpcount = 0, total = 0;
    LinearLayout ll1, ll2;// Add Herojit
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    Date date = new Date();
    GridView gridanc, gridanc1, gridHrpanc;
    String CurrentDate = "", MonthNo_C = "", MonthNo_A = "", VHNDDate = "";
    ImageView uttarakhand, jharkhand;
    int statecode = 0;
    TableRow tbl_hrp1, tbl_hrp, tbl_hbnc, tbl_hbnc1, tbl_anc, tbl_anc1;
    ArrayList<tbl_pregnantwomen> Pregnant_woman;
    ArrayList<tblChild> child;
    ArrayList<tbl_pregnantwomen> member = new ArrayList<tbl_pregnantwomen>();
    ArrayList<tbl_pregnantwomen> hbncmember = new ArrayList<tbl_pregnantwomen>();
    ArrayList<tblPNChomevisit_ANS> PNChomevisit = new ArrayList<tblPNChomevisit_ANS>();
    String[] arr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec"};

    // private EasyTracker easyTracker1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.dashboard1);

        setTitle(getVersion());
        // send our data!
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        uttarakhand = (ImageView) findViewById(R.id.uttarakhand);
        jharkhand = (ImageView) findViewById(R.id.jharkhand);
        btnLanguage = (Button) findViewById(R.id.btnLanguage);
        // hh = (Button) findViewById(R.id.hh);
        mnch = (Button) findViewById(R.id.mnch);
        cd = (Button) findViewById(R.id.cd);
        ncd = (Button) findViewById(R.id.ncd);
        ah = (Button) findViewById(R.id.ah);
        fp = (Button) findViewById(R.id.fp);
        incentives = (Button) findViewById(R.id.incentives);
        // sync = (Button) findViewById(R.id.sync);
        vhnd = (Button) findViewById(R.id.vhnd);
        // rd = (Button) findViewById(R.id.rd);
        // tvDashboardSurvey = (TextView) findViewById(R.id.tvDashboardSurvey);
        // tvDashboardSynchronisation = (TextView)
        // findViewById(R.id.tvDashboardSynchronisation);
        // ll1.setVisibility(View.GONE);
        // ll2.setVisibility(View.VISIBLE);

        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        // global.setLanguage(2);
        iRoleID = global.getiGlobalRoleID();
        try {
            String IMEI = getIMEI(this);
            global.setIMEI(IMEI);

            if (global.getStateCode() != null
                    && global.getStateCode().length() > 0) {

                statecode = Integer.valueOf(global.getStateCode());
            }
            if (statecode == 20) {
                hh = (Button) findViewById(R.id.hh1);

                ncd = (Button) findViewById(R.id.ncd1);

                sync = (Button) findViewById(R.id.sync1);

                rd = (Button) findViewById(R.id.rd1);

                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.VISIBLE);
                uttarakhand.setVisibility(View.GONE);
                jharkhand.setVisibility(View.VISIBLE);
            } else if (statecode == 5) {
                hh = (Button) findViewById(R.id.hh);

                ncd = (Button) findViewById(R.id.ncd);

                sync = (Button) findViewById(R.id.sync);

                rd = (Button) findViewById(R.id.rd);
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.GONE);
                ncd.setEnabled(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            Tracker t = ((Global) getApplication())
                    .getTracker(TrackerName.APP_TRACKER);
            t.setScreenName("Dashboard");
            String dimensionValue = "5";
            if (global.getsGlobalUserName() != null
                    && global.getsGlobalUserName().length() > 0) {
                dimensionValue = global.getsGlobalUserName();
            }

            t.set("&userid", dimensionValue);
            t.enableAutoActivityTracking(true);

            t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
                    .setAction("User Sign In").setNewSession()
                    .setCustomDimension(1, dimensionValue).build());
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
        // t.send(new HitBuilders.EventBuilder()
        // .setCategory("msakhi")
        // .setAction("User Sign In")
        // .build());
        // t.send(new HitBuilders.AppViewBuilder().build());

        // String dimensionValue = "2";
        // t.set("userid", dimensionValue);
        username = global.getsGlobalUserName();
        if (global.getLanguage() == 1) {
            btnLanguage.setText("hi");
            global.setLanguage(1);
            changeLang("en");

        } else if (global.getLanguage() == 2) {
            btnLanguage.setText("en");
            global.setLanguage(2);
            changeLang("hi");

        } else {
            // String sLanguage = "";
            // sLanguage = "hi";
            sCurrentLanguage = "en";
            btnLanguage.setText(sCurrentLanguage);
            global.setLanguage(2);
            changeLang("hi");
        }

        // layoutsurvey = (LinearLayout) findViewById(R.id.layoutsurvey);
        // layoutsync = (LinearLayout) findViewById(R.id.layoutsynchronization);
        // layoutmch = (LinearLayout) findViewById(R.id.layoutmch);

        btnLanguage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String sLang = "";
                sLang = btnLanguage.getText().toString();
                if (sLang.equalsIgnoreCase("en")) {

                    global.setLanguage(1);
                    btnLanguage.setText("hi");
                    changeLang("en");

                } else if (sLang.equalsIgnoreCase("hi")) {

                    global.setLanguage(2);
                    btnLanguage.setText("en");
                    changeLang("hi");

                }

            }
        });

        hh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 1);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Dashboard_Activity.this,
                            Survey_Activity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
        mnch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 2);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Dashboard_Activity.this,
                            MCH_Dashboard.class);
                    finish();
                    startActivity(i);
                }
            }
        });
        ncd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 6);
                    startActivity(i);
                } else {
                //
                Intent i = new Intent(Dashboard_Activity.this, NCD_AA.class);
                finish();
                startActivity(i);}
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent i = new Intent(Dashboard_Activity.this,
                        Synchronization.class);
                finish();
                startActivity(i);
            }
        });
        fp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 3);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Dashboard_Activity.this, FP_AA.class);
                    finish();
                    startActivity(i);
                }

            }
        });
        rd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i = new Intent(Dashboard_Activity.this,
                        Report_Module.class);
                finish();
                startActivity(i);

            }
        });
        // add herojit
        incentives.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 5);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Dashboard_Activity.this,
                            Incentives.class);
                    finish();
                    startActivity(i);
                }
            }
        });
        vhnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {// TODO Auto-generated method stub

                if (iRoleID == 3) {
                    Intent i = new Intent(Dashboard_Activity.this,
                            AshaListForAnm.class);
                    finish();

                    i.putExtra("Flag", 4);
                    startActivity(i);
                } else {
                    // Intent i = new Intent(Dashboard_Activity.this,
                    // VHND_Record.class);
                    Intent i = new Intent(Dashboard_Activity.this,
                            VHND_DateRecord.class);
                    // i.putExtra("NoFlag", 2);
                    // finish();
                    // i.putExtra("ActivityName", "Dashboard_Activity");
                    startActivity(i);
                }

            }
        });

        String sql = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int) day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 0 and 42 and place_of_birth!=2 and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=day)";
        String sql1 = "SELECT count(c.CHILDGUID) cnt,cast(round(julianday('NOW')-julianday(CHILD_dob)+.5) as int)day  from tblchild  c left join tblPNChomevisit_ANS  p where day between 3 and 42 and place_of_birth=2 and  c.childguid not in  (select p.childguid from tblPNChomevisit_ANS where visitno=day)";

        hbnccount = dataProvider.getcountRecord(sql)
                + dataProvider.getcountRecord(sql1);
        String sql2 = "SELECT count(pwguid)  from tblPregnant_woman where  IsPregnant=1 and cast(round(julianday(EDDDate)-julianday('NOW')+.5) as int) between 0 and 2";
        pregcount = dataProvider.getMaxRecord(sql2);
        String sql3 = "SELECT count(a.pwguid) cnt,visit_no  from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where homevisitdate in('',null) and ispregnant=1 order by b.pwguid";

        String sql4 = "SELECT count(a.pwguid) cnt,visit_no  from tblPregnant_woman a inner join tblANCVisit b on  cast(round((julianday('NOW')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 order by b.pwguid";
        ancchkccount = dataProvider.getcountRecord(sql4);
        anccount = dataProvider.getcountRecord(sql3);
        String sql5 = "select count(*) from tblFP_followup where methodadopted in(1,2,3) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";
        String sql51 = "select count(*) from tblFP_followup where methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";
        String sql52 = "select count(*) from tblFP_followup where methodadopted in(6,7) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup group by womenname_guid) and cast(round((julianday('NOW')-julianday(createdon))+.5)  as int) >30";

        // String sql51 =
        // "select count(distinct(womenname_guid)) from tblfp_visit where  cast(round((julianday('NOW')-julianday(visitdate))+.5)  as int) between 30 and 32 and womenname_guid not in (select womenname_guid from tblFP_followup) ";
        fpcount = dataProvider.getMaxRecord(sql5)
                + dataProvider.getMaxRecord(sql51)
                + dataProvider.getMaxRecord(sql52);
        // + dataProvider.getMaxRecord(sql51);
        total = hbnccount + pregcount + ancchkccount + anccount + fpcount;
        // if(global.getNotification_flag()==1){
        String devicesql = "select count(* ) from  tbldevicespaceusage where cast(round((julianday('NOW')-julianday(createdon)))  as int)<7";
        int spcecount = dataProvider.getMaxRecord(devicesql);
        if (spcecount == 0) {
            space();
        }
        if (iRoleID == 2 && statecode == 5) {
            VhndDuelist();
            if (global.getNotification_flag() == 1) {
                CustomAlertANCVisitDetails();
                global.setNotification_flag(0);
            }
        }
        try {
            if (isNetworkconn()) {

                firebroadcastreceiver();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (global.getMsg_flag() == 1) {
            if (iRoleID == 2) {
                showCustomAlert(global.getsGlobalAshaName());
            } else if (iRoleID == 3) {
                showCustomAlert(global.getsGlobalANMName());
            }
            global.setMsg_flag(0);
        }

    }

    public String getVersion() {
        String mVersionNumber;
        Context mContext = getApplicationContext();
        global = (Global) getApplicationContext();
        try {
            String pkg = mContext.getPackageName();
            mVersionNumber = getResources().getString(R.string.app_name) + " " +mContext.getPackageManager()
                    .getPackageInfo(pkg, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            mVersionNumber = getResources().getString(R.string.app_name) + " " +"?";
        }
        global.setVersionName(mVersionNumber);
        return mVersionNumber;
    }

    private boolean isNetworkconn() {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((localConnectivityManager.getActiveNetworkInfo() != null)
                && (localConnectivityManager.getActiveNetworkInfo()
                .isAvailable())
                && (localConnectivityManager.getActiveNetworkInfo()
                .isConnected()))

            return true;
        else
            return false;

    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        updateTexts();
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs",
                Context.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    private void updateTexts() {
        hh.setText(R.string.HouseholdDetails);
        mnch.setText(R.string.MNCH);
        cd.setText(R.string.cd);
        ncd.setText(R.string.ncd);
        ah.setText(R.string.ah);
        fp.setText(R.string.fp);
        incentives.setText(R.string.incentives);
        sync.setText(R.string.synchronization);
        vhnd.setText(R.string.vhnd);
        rd.setText(R.string.rd);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (global.getiGlobalRoleID() == 3) {
            menu.add(0, 0, 1, global.getsGlobalANMName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menu.add(0, 1, 2, "History").setIcon(R.drawable.logout1)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        } else {
            if (statecode == 20) {
                // menu.add(0, 0, 0, global.getsGlobalAshaName())
                // .setIcon(R.drawable.bell)
                // .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                menu.add(0, 0, 1, global.getsGlobalAshaName()).setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_IF_ROOM);

                menu.add(0, 1, 2, "History").setIcon(R.drawable.logout1)
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            } else {
                if (total == 0) {
                    menu.add(0, 0, 0, global.getsGlobalAshaName())
                            .setIcon(R.drawable.bell)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                    menu.add(0, 0, 1, global.getsGlobalAshaName())
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

                    menu.add(0, 1, 2, "History").setIcon(R.drawable.logout1)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    menu.add(0, 0, 0, global.getsGlobalAshaName())
                            .setIcon(R.drawable.bell1)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                    menu.add(1, 1, 1, global.getsGlobalAshaName())
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

                    menu.add(0, 2, 2, "").setIcon(R.drawable.logout1)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                }
            }

        }

        // menu.setGroupVisible(0, false);
        //

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {
            case 0:
                CustomAlertANCVisitDetails();
                break;
            case 2:
                CustomAlert();

                break;

            default:
                break;
        }

        return true;
    }

    public void CustomAlert() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logout_alert, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(getResources().getString(R.string.Doyouwanttologout));

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                // int iLoggedin = 0;
                Calendar cal = Calendar.getInstance();
                Date currentLocalTime = cal.getTime();
                SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

                String endTime = date1.format(currentLocalTime);
                dataProvider.getUserLoginUpdate(global.getLogin_GUID(), endTime);
                Intent i = new Intent(Dashboard_Activity.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                dialog.dismiss();
            }
        });

        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        btnno.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(Dashboard_Activity.this)
                        .reportActivityStart(this);
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(Dashboard_Activity.this)
                        .reportActivityStop(this);
            } catch (Exception e) {

            }
        }
    }

    public void onResume() {
        super.onResume();
        try {
            if (android.provider.Settings.System.getInt(getContentResolver(),
                    android.provider.Settings.System.AUTO_TIME, 1) == 1) {

            } else {

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .create();
                alertDialog.setTitle(getResources().getString(R.string.app_name));
                alertDialog.setMessage(getResources().getString(R.string.EnableAutomatic));
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(
                                        android.provider.Settings.ACTION_DATE_SETTINGS));
                            }
                        });

                //alertDialog.setIcon(R.drawable.msakhi_logo);
                alertDialog.show();
                alertDialog.setCancelable(false);
                // will close the app if the device
                // does't have camera

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CustomAlert1() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.duelist_dialog, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TableRow tbl1 = (TableRow) dialog.findViewById(R.id.tbl1);
        TableRow tbl2 = (TableRow) dialog.findViewById(R.id.tbl2);
        TableRow tbl3 = (TableRow) dialog.findViewById(R.id.tbl3);
        TableRow tbl4 = (TableRow) dialog.findViewById(R.id.tbl4);
        TableRow tbl5 = (TableRow) dialog.findViewById(R.id.tbl5);
        Button btn_noofHBNC = (Button) dialog.findViewById(R.id.btn_noofHBNC);
        Button btn_VHNDDate = (Button) dialog.findViewById(R.id.btn_VHNDDate);
        TableRow tbl6 = (TableRow) dialog.findViewById(R.id.tbl6);

        TextView tv1 = (TextView) dialog.findViewById(R.id.tv1);

        TextView tv2 = (TextView) dialog.findViewById(R.id.tv2);

        TextView tv3 = (TextView) dialog.findViewById(R.id.tv3);

        TextView tv4 = (TextView) dialog.findViewById(R.id.tv4);

        TextView tv5 = (TextView) dialog.findViewById(R.id.tv5);

        Button btn_noofdeliveries = (Button) dialog
                .findViewById(R.id.btn_noofdeliveries);
        Button btn_noofanccheckup = (Button) dialog
                .findViewById(R.id.btn_noofanccheckup);
        Button btn_noofancvisit = (Button) dialog
                .findViewById(R.id.btn_noofancvisit);
        Button btn_nooffamilyplanning = (Button) dialog
                .findViewById(R.id.btn_nooffamilyplanning);
        if (hbnccount == 0) {
            tbl1.setVisibility(view.GONE);
        } else {
            tbl1.setVisibility(view.VISIBLE);
            tv1.setText(String.valueOf(hbnccount));
        }
        if (pregcount == 0) {
            tbl2.setVisibility(view.GONE);
        } else {
            tbl2.setVisibility(view.VISIBLE);
            tv2.setText(String.valueOf(pregcount));
        }
        if (ancchkccount == 0) {
            tbl3.setVisibility(view.GONE);
        } else {
            tbl3.setVisibility(view.VISIBLE);
            tv3.setText(String.valueOf(ancchkccount));
        }
        if (anccount == 0) {
            tbl4.setVisibility(view.GONE);
        } else {
            tbl4.setVisibility(view.VISIBLE);
            tv4.setText(String.valueOf(anccount));
        }
        if (fpcount == 0) {
            tbl5.setVisibility(view.GONE);
        } else {
            tbl5.setVisibility(view.VISIBLE);
            tv5.setText(String.valueOf(fpcount));
        }
        CurrentDate = dateFormat.format(date);
        String aa[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
                "Sep", "Oct", "Nov", "Dec"};
        String mon[] = {};
        mon = CurrentDate.split("-");
        final int monthi, Days, Year;
        Year = Integer.valueOf(mon[0]);
        monthi = Integer.valueOf(mon[1]) - 1;
        Days = Integer.valueOf(mon[2]);
        if (Days > 0 && monthi > 0 && monthi != 11) {
            MonthNo_C = aa[monthi];
            MonthNo_A = aa[monthi + 1];
        } else if (Days > 0 && monthi == 11) {
            MonthNo_C = aa[monthi];
            MonthNo_A = aa[1];
        }
        String sql = "";

        sql = "Select Village_ID from VHND_Schedule where ASHA_ID="
                + global.getsGlobalAshaCode()
                + " and ((cast(round(julianday("
                + MonthNo_C
                + ")-julianday('NOW'))  as int) between 1 and 7) or (cast(round(julianday("
                + MonthNo_A
                + ")-julianday('NOW'))  as int) between 1 and 7)) and Year='"
                + Year + "'";
        final String VillageID = dataProvider.getRecord(sql);
        if (VillageID == null || VillageID.equalsIgnoreCase("")) {
            tbl6.setVisibility(View.GONE);
            btn_VHNDDate.setVisibility(View.GONE);
        }

        btn_VHNDDate
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub

                        String sql1 = "", sql2 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "";
                        int count5 = 0, count6 = 0;
                        int NoofDay_C = 0, NoofDay_A = 0, count = 0;
                        sql5 = "Select count(*) from VHND_Schedule where ASHA_ID="
                                + global.getsGlobalAshaCode()
                                + " and Village_ID='"
                                + VillageID
                                + "' and (cast(round(julianday("
                                + MonthNo_A
                                + ")-julianday('NOW'))  as int) <=7) and Year='"
                                + Year + "'";
                        sql6 = "Select count(*) from VHND_Schedule where ASHA_ID="
                                + global.getsGlobalAshaCode()
                                + " and Village_ID='"
                                + VillageID
                                + "' and (cast(round(julianday("
                                + MonthNo_C
                                + ")-julianday('NOW'))  as int) <=7) and Year='"
                                + Year + "'";
                        count5 = dataProvider.getMaxRecord(sql5);
                        count6 = dataProvider.getMaxRecord(sql6);
                        if (count5 != 0) {
                            sql2 = "Select round(julianday("
                                    + MonthNo_A
                                    + ")-julianday('NOW')) from VHND_Schedule where ASHA_ID="
                                    + global.getsGlobalAshaCode()
                                    + " and Village_ID='"
                                    + VillageID
                                    + "' and (cast(round(julianday("
                                    + MonthNo_A
                                    + ")-julianday('NOW'))  as int) <=7) and Year='"
                                    + Year + "'";
                            NoofDay_C = Integer.valueOf(dataProvider
                                    .getRecord(sql2));
                        }
                        if (count6 != 0) {
                            sql3 = "Select round(julianday("
                                    + MonthNo_C
                                    + ")-julianday('NOW')) from VHND_Schedule where ASHA_ID="
                                    + global.getsGlobalAshaCode()
                                    + " and Village_ID='"
                                    + VillageID
                                    + "' and (cast(round(julianday("
                                    + MonthNo_C
                                    + ")-julianday('NOW'))  as int) <=7) and Year='"
                                    + Year + "'";
                            NoofDay_A = Integer.valueOf(dataProvider
                                    .getRecord(sql3));
                        }
                        String Date = "", Month = "";
                        if (NoofDay_C < NoofDay_A) {
                            Month = MonthNo_C;
                        } else {
                            Month = MonthNo_A;
                        }
                        sql4 = "select "
                                + Month
                                + " from VHND_Schedule where ASHA_ID="
                                + global.getsGlobalAshaCode()
                                + " and Village_ID='"
                                + VillageID
                                + "' and (cast(round(julianday("
                                + Month
                                + ")-julianday('NOW'))  as int) <=7) and Year='"
                                + Year + "'";
                        Date = dataProvider.getRecord(sql4);
                        sql1 = "select count(*) from tbl_VHND_DueList where ASHAID="
                                + global.getsGlobalAshaCode()
                                + " and VillageID='"
                                + VillageID
                                + "' and Date='" + Date + "'";
                        count = dataProvider.getMaxRecord(sql1);
                        int FlagNo = 0;
                        if (count == 0) {
                            FlagNo = 1;
                            global.setVHND_ID(Validate.random());
                            // Save_VHND(Integer.valueOf(VillageID), Date, "I");
                        } else {
                            String sqll = "select VHND_ID from tbl_VHND_DueList where ASHAID="
                                    + global.getsGlobalAshaCode()
                                    + " and VillageID='"
                                    + VillageID
                                    + "' and Date='" + Date + "'";
                            String VHNDID = dataProvider.getRecord(sqll);
                            global.setVHND_ID(VHNDID);
                            FlagNo = 2;
                            // Save_VHND(Integer.valueOf(VillageID), Date, "U");
                        }
                        // global.setVHND_ID(Validate.random());
                        global.setVHND_Date(Date);
                        global.setVHND_MonthField(Month);
                        global.setVHND_VillageID(VillageID);
                        Intent i = new Intent(Dashboard_Activity.this,
                                VHND_DueList.class);
                        i.putExtra("NoFlag", FlagNo);
                        i.putExtra("ActivityName", "Dashboard_Activity");
                        startActivity(i);
                    }
                });

        btn_noofHBNC
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(),
                                AncVisit_List.class);

                        startActivity(i);
                    }
                });
        btn_noofdeliveries
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(), Anc_duelist.class);
                        i.putExtra("Flag", 2);
                        startActivity(i);
                    }
                });
        btn_noofanccheckup
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(), Anc_duelist.class);
                        i.putExtra("Flag", 0);
                        startActivity(i);
                    }
                });
        btn_noofancvisit
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(), Anc_duelist.class);
                        i.putExtra("Flag", 1);
                        startActivity(i);
                    }
                });
        btn_nooffamilyplanning
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(), Anc_duelist.class);
                        i.putExtra("Flag", 5);
                        startActivity(i);
                    }
                });

        // Display the dialog
        dialog.show();

    }

    private void alerttoast(int i) {
        Toast.makeText(this, getResources().getString(i), Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
        //
        // Intent i = new Intent(Dashboard_Activity.this, Login.class);
        // finish();
        // startActivity(i);

        CustomAlert();
    }

    public void CustomAlertANCVisitDetails() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.anc_duevisistgriddialog, null,
                false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        gridHrpanc = (GridView) dialog.findViewById(R.id.gridHrpanc);
        gridanc = (GridView) dialog.findViewById(R.id.gridanc);
        gridanc1 = (GridView) dialog.findViewById(R.id.gridanc1);
        TextView tvVhndbaby_name = (TextView) dialog
                .findViewById(R.id.tvVhndbaby_name);

        tbl_hrp1 = (TableRow) dialog.findViewById(R.id.tbl_hrp1);
        tbl_hrp = (TableRow) dialog.findViewById(R.id.tbl_hrp);
        tbl_hbnc = (TableRow) dialog.findViewById(R.id.tbl_hbnc);
        tbl_hbnc1 = (TableRow) dialog.findViewById(R.id.tbl_hbnc1);
        tbl_anc = (TableRow) dialog.findViewById(R.id.tbl_anc);
        tbl_anc1 = (TableRow) dialog.findViewById(R.id.tbl_anc1);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        fillgrid();
        fillHBNCgrid();
        fillgridanc();
        vhnd(tvVhndbaby_name);
        // code comes here

        // Display the dialog
        dialog.show();

    }

    public void fillgridanc() {
        int flag = 0;

        member = dataProvider.getNotificationdata(2);

        if (member != null && member.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridanc1
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * member.size();
            params.height = gridHeight1;
            gridanc1.setLayoutParams(params);
            gridanc1.setAdapter(new Anc_DueListadapter(this, member, 6));
        } else {
            tbl_anc.setVisibility(View.GONE);
            tbl_anc1.setVisibility(View.GONE);
        }
    }

    public void fillgrid() {
        int flag = 0;

        member = dataProvider.getNotificationdata(0);

        if (member != null && member.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridHrpanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * member.size();
            params.height = gridHeight1;
            gridHrpanc.setLayoutParams(params);
            gridHrpanc.setAdapter(new Anc_DueListadapter(this, member, 6));
        } else {
            tbl_hrp.setVisibility(View.GONE);
            tbl_hrp1.setVisibility(View.GONE);
        }
    }

    public void fillHBNCgrid() {
        int flag = 0;
        try {
            hbncmember = dataProvider.getNotificationdata(1);
            PNChomevisit = dataProvider.getpncdata("", "", 0, 3);
            for (int i = hbncmember.size() - 1; i >= 0; i--) {
                for (int j = PNChomevisit.size() - 1; j >= 0; j--) {
                    if (PNChomevisit.get(j).getChildGUID()
                            .equals(hbncmember.get(i).getLMPDate())) {
                        hbncmember.remove(i);
                        break;
                    }
                }

            }

            if (hbncmember != null && hbncmember.size() > 0) {

                android.view.ViewGroup.LayoutParams params = gridanc
                        .getLayoutParams();
                Resources r = getResources();
                float px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
                int hi = Math.round(px);
                int gridHeight1 = 0;
                gridHeight1 = hi * hbncmember.size();
                params.height = gridHeight1;
                gridanc.setLayoutParams(params);
                gridanc.setAdapter(new HBNCadapter(this, hbncmember, 6));
            } else {
                tbl_hbnc.setVisibility(View.GONE);
                tbl_hbnc1.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCustomAlert(String name) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.welcome, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(5));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);

        txtTitle.setText(getResources().getString(R.string.Welcome) + " " + name + " " + getResources().getString(R.string.Welcomemsg));

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        btnyes.setText(getResources().getString(R.string.Welcome));
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                // int iLoggedin = 0;

                dialog.dismiss();
            }
        });


        // Display the dialog
        dialog.show();

    }

    public void vhnd(TextView txt) {

        try {
            String date = Validate.getcurrentdate();
            String[] ss = date.split("-");
            int mnth = Integer.valueOf(ss[1]);
            int yr = Integer.valueOf(ss[0]);
            if (mnth == 1 || mnth == 2 || mnth == 3) {
                yr = yr - 1;
            }
            String sql = "select " + arr[mnth - 1]
                    + " from VHND_Schedule where year=" + yr + "";
            String currmnthdate = dataProvider.getRecord(sql);
            if (mnth == 12) {
                mnth = 0;
            }

            String sql1 = "select " + arr[mnth]
                    + " from VHND_Schedule where year=" + yr + "";
            String nextmnthdate = dataProvider.getRecord(sql1);
            String currmnthdate1 = dataProvider
                    .getRecord("select   cast(julianday('" + currmnthdate
                            + "')-julianday('" + date + "') as int ) as day");
            String nextmnthdate1 = dataProvider
                    .getRecord("select   cast(julianday('" + nextmnthdate
                            + "')-julianday('" + date + "') as int ) as day");
            int day = Integer.valueOf(currmnthdate1);
            int day1 = Integer.valueOf(nextmnthdate1);
            if (day < 7 && day > 0) {
                txt.setText(getResources().getString(R.string.nextvhnd)
                        + Validate.changeDateFormat(currmnthdate));

            } else {
                if (day1 < 7 && day1 > 0) {
                    txt.setText(getResources().getString(R.string.nextvhnd)
                            + Validate.changeDateFormat(nextmnthdate));
                } else if (day < 30 && day > 0) {
                    txt.setText(getResources().getString(R.string.nextvhnd)
                            + Validate.changeDateFormat(currmnthdate));

                } else {
                    txt.setText(getResources().getString(R.string.nextvhnd)
                            + Validate.changeDateFormat(nextmnthdate));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VhndDuelist() {

        try {
            String date = Validate.getcurrentdate();
            String[] ss = date.split("-");
            int mnth = Integer.valueOf(ss[1]);
            int yr = Integer.valueOf(ss[0]);
            if (mnth == 1 || mnth == 2 || mnth == 3) {
                yr = yr - 1;
            }
            String sql = "select " + arr[mnth - 1]
                    + " from VHND_Schedule where year=" + yr + "";
            String currmnthdate = dataProvider.getRecord(sql);

            String daysql = dataProvider.getRecord("select   cast(julianday('"
                    + currmnthdate + "')-julianday('" + date
                    + "') as int ) as day");

            int day = Integer.valueOf(daysql);
            if (mnth == 12) {
                mnth = 0;
            }

            String sql1 = "select " + arr[mnth]
                    + " from VHND_Schedule where year=" + yr + "";
            String nextmnthdate = dataProvider.getRecord(sql1);

            String nextmnthdate1 = dataProvider
                    .getRecord("select   cast(julianday('" + nextmnthdate
                            + "')-julianday('" + date + "') as int ) as day");

            int day1 = Integer.valueOf(nextmnthdate1);

            if (day < 7 && day >= 0) {
                String villagesql = "select village_id from VHND_Schedule where year="
                        + yr
                        + " and "
                        + arr[mnth - 1]
                        + "='"
                        + currmnthdate
                        + "' ";
                String villageid = dataProvider.getRecord(villagesql);
                sql = "Select * from tblPregnant_woman a inner join tblANCVisit b on cast(round((julianday('"
                        + currmnthdate
                        + "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
                        + global.getsGlobalAshaCode()
                        + " and a.AshaID="
                        + global.getsGlobalAshaCode() + " order by b.pwguid";

                // Pregnant_woman = dataProvider.getMemberName(sql, 0);
                int VillageID = 0, AshaID = 0, ANMID = 0;
                if (!villageid.equalsIgnoreCase("null")
                        && villageid.length() > 0) {
                    VillageID = Integer.valueOf(villageid);
                }
                if (!global.getsGlobalAshaCode().equalsIgnoreCase("null")
                        && global.getsGlobalAshaCode().length() > 0) {
                    AshaID = Integer.valueOf(global.getsGlobalAshaCode());
                }
                if (!global.getsGlobalANMCODE().equalsIgnoreCase("null")
                        && global.getsGlobalANMCODE().length() > 0) {
                    ANMID = Integer.valueOf(global.getsGlobalANMCODE());
                }

                Pregnant_woman = dataProvider.getPregnantWomendata(sql, 6, 0);
                for (int i = 0; i < Pregnant_woman.size(); i++) {
                    String VaccineNames = "", MemberGUID = "", HHGUID = "", MemberName = "", name = "", Flag = "";
                    try {
                        String sql4 = "select HusbandName from tblpregnant_woman where PWGUID='"
                                + Pregnant_woman.get(i).getPWGUID() + "'";
                        String HushbandName = dataProvider.getRecord(sql4);
                        String sql5 = "select EDDDate from tblpregnant_woman where PWGUID='"
                                + Pregnant_woman.get(i).getPWGUID() + "'";
                        String MotherLMP = dataProvider.getRecord(sql5);
                        String Namee = Pregnant_woman.get(i).getPWName();

                        // ANCItems.setText(String.valueOf(VaccineName(
                        // Pregnant_woman.get(i).getPWGUID(), 1)));

                        name = String.valueOf(Namee
                                + " / "
                                + HushbandName
                                + " / "
                                + Validate.changeDateFormat(String
                                .valueOf(MotherLMP)));
                        MemberGUID = Pregnant_woman.get(i).getPWGUID();
                        HHGUID = Pregnant_woman.get(i).getHHGUID();
                        MemberName = Pregnant_woman.get(i).getPWName();

                        sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
                                + currmnthdate
                                + "' and VillageID='"
                                + villageid
                                + "' and BeneficiaryGUID='"
                                + MemberGUID + "'";
                        int count = dataProvider.getMaxRecordnew(sql);
                        if (count > 0) {
                            Flag = "U";
                        } else {
                            Flag = "I";
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    int returnvalue = dataProvider.VHND_DueListNew(MemberGUID,
                            HHGUID, MemberName, VaccineNames, currmnthdate,
                            VillageID, AshaID, ANMID,
                            global.getsGlobalUserID(),
                            global.getsGlobalUserID(), Flag, 1, name);

                }
                sql = "Select * from tblCHild where ((bcg='' or bcg==null and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)<365) or ((hepb1='' or hepb1==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((hepb2='' or hepb2==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)between 70 and 97) or ((hepb3='' or hepb3==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((opv1='' or opv1==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((opv2='' or opv2==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((opv3='' or opv3==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((dpt1='' or dpt1==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((dpt2='' or dpt2==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((dpt3='' or dpt3==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((Pentavalent1='' or Pentavalent1==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((Pentavalent2='' or Pentavalent2==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((Pentavalent3='' or Pentavalent3==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((measeals='' or measeals==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((vitaminA='' or vitaminA==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((VitaminAtwo='' or VitaminAtwo==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 540 and 720) or ((OPVBooster='' or OPVBooster==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBooster='' or DPTBooster==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBoostertwo='' or DPTBoostertwo==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 1800 and 2160) or ((JEVaccine1='' or JEVaccine1==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
                        + currmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 730)) and AshaID="
                        + AshaID + "";

                child = dataProvider.gettblChild("", sql, 7,0);
                for (int i = 0; i < child.size(); i++) {
                    String VaccineNames = "", MemberGUID = "", HHGUID = "", MemberName = "", name = "", Flag = "";
                    try {
                        String sql6 = "select PWName from tblPregnant_woman where PWGUID in (select pw_GUID from tblChild where ChildGUID='"
                                + child.get(i).getChildGUID() + "')";
                        String HushbandName = dataProvider.getRecord(sql6);
                        String sql7 = "select Child_dob from tblChild where ChildGUID='"
                                + child.get(i).getChildGUID() + "'";
                        String dob = dataProvider.getRecord(sql7);
                        String Namee = child.get(i).getChild_name();

                        name = String
                                .valueOf(Namee
                                        + " / "
                                        + HushbandName
                                        + " / "
                                        + Validate.changeDateFormat(String
                                        .valueOf(dob)));
                        MemberGUID = child.get(i).getChildGUID();

                        HHGUID = child.get(i).getHHGUID();

                        HHGUID = "";

                        MemberName = child.get(i).getChild_name();

                        sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
                                + currmnthdate
                                + "' and VillageID='"
                                + VillageID
                                + "' and BeneficiaryGUID='"
                                + MemberGUID + "'";
                        int count = dataProvider.getMaxRecordnew(sql);
                        if (count > 0) {
                            Flag = "U";
                        } else {
                            Flag = "I";
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    int returnvalue = dataProvider.VHND_DueListNew(MemberGUID,
                            HHGUID, MemberName, VaccineNames, currmnthdate,
                            VillageID, AshaID, ANMID,
                            global.getsGlobalUserID(),
                            global.getsGlobalUserID(), Flag, 2, name);

                }
            } else if (day1 < 7 && day1 >= 0) {

                String villagesql = "select village_id from VHND_Schedule where year="
                        + yr + " and " + arr[mnth] + "='" + nextmnthdate + "' ";
                String villageid = dataProvider.getRecord(villagesql);
                sql = "Select * from tblPregnant_woman a inner join tblANCVisit b on cast(round((julianday('"
                        + nextmnthdate
                        + "')-julianday(a.lmpdate))/90+.5)  as int) =b.visit_no and  a.pwguid=b.pwguid where checkupvisitdate in('',null)and ispregnant=1 and (b.visit_no=1 or b.visit_no=2 or b.visit_no=3 or b.visit_no=4) and b.ByAshaID="
                        + global.getsGlobalAshaCode()
                        + " and a.AshaID="
                        + global.getsGlobalAshaCode() + " order by b.pwguid";

                // Pregnant_woman = dataProvider.getMemberName(sql, 0);
                int VillageID = 0, AshaID = 0, ANMID = 0;
                if (!villageid.equalsIgnoreCase("null")
                        && villageid.length() > 0) {
                    VillageID = Integer.valueOf(villageid);
                }
                if (!global.getsGlobalAshaCode().equalsIgnoreCase("null")
                        && global.getsGlobalAshaCode().length() > 0) {
                    AshaID = Integer.valueOf(global.getsGlobalAshaCode());
                }
                if (!global.getsGlobalANMCODE().equalsIgnoreCase("null")
                        && global.getsGlobalANMCODE().length() > 0) {
                    ANMID = Integer.valueOf(global.getsGlobalANMCODE());
                }

                Pregnant_woman = dataProvider.getPregnantWomendata(sql, 6, 0);
                for (int i = 0; i < Pregnant_woman.size(); i++) {
                    String VaccineNames = "", MemberGUID = "", HHGUID = "", MemberName = "", name = "", Flag = "";
                    try {
                        String sql11 = "select HusbandName from tblpregnant_woman where PWGUID='"
                                + Pregnant_woman.get(i).getPWGUID() + "'";
                        String HushbandName = dataProvider.getRecord(sql11);
                        String sql22 = "select EDDDate from tblpregnant_woman where PWGUID='"
                                + Pregnant_woman.get(i).getPWGUID() + "'";
                        String MotherLMP = dataProvider.getRecord(sql22);
                        String Namee = Pregnant_woman.get(i).getPWName();

                        // ANCItems.setText(String.valueOf(VaccineName(
                        // Pregnant_woman.get(i).getPWGUID(), 1)));

                        name = String.valueOf(Namee
                                + " / "
                                + HushbandName
                                + " / "
                                + Validate.changeDateFormat(String
                                .valueOf(MotherLMP)));
                        MemberGUID = Pregnant_woman.get(i).getPWGUID();
                        HHGUID = Pregnant_woman.get(i).getHHGUID();
                        MemberName = Pregnant_woman.get(i).getPWName();

                        sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
                                + nextmnthdate
                                + "' and VillageID='"
                                + villageid
                                + "' and BeneficiaryGUID='"
                                + MemberGUID + "'";
                        int count = dataProvider.getMaxRecordnew(sql);
                        if (count > 0) {
                            Flag = "U";
                        } else {
                            Flag = "I";
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    int returnvalue = dataProvider.VHND_DueListNew(MemberGUID,
                            HHGUID, MemberName, VaccineNames, nextmnthdate,
                            VillageID, AshaID, ANMID,
                            global.getsGlobalUserID(),
                            global.getsGlobalUserID(), Flag, 1, name);

                }
                sql = "Select * from tblCHild where ((bcg='' or bcg==null and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)<365) or ((hepb1='' or hepb1==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((hepb2='' or hepb2==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)between 70 and 97) or ((hepb3='' or hepb3==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((opv1='' or opv1==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((opv2='' or opv2==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((opv3='' or opv3==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((dpt1='' or dpt1==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((dpt2='' or dpt2==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((dpt3='' or dpt3==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((Pentavalent1='' or Pentavalent1==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 42 and 69) or ((Pentavalent2='' or Pentavalent2==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 70 and 97) or ((Pentavalent3='' or Pentavalent3==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int)>=98) or ((measeals='' or measeals==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((vitaminA='' or vitaminA==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((VitaminAtwo='' or VitaminAtwo==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 540 and 720) or ((OPVBooster='' or OPVBooster==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBooster='' or DPTBooster==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 720) or ((DPTBoostertwo='' or DPTBoostertwo==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 1800 and 2160) or ((JEVaccine1='' or JEVaccine1==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 270 and 365) or ((JEVaccine2='' or JEVaccine2==null) and cast(round(julianday('"
                        + nextmnthdate
                        + "')-julianday(Child_dob))as int) between 480 and 730)) and AshaID="
                        + AshaID + "";

                child = dataProvider.gettblChild("", sql, 7,0);
                for (int i = 0; i < child.size(); i++) {
                    String VaccineNames = "", MemberGUID = "", HHGUID = "", MemberName = "", name = "", Flag = "";
                    try {
                        String sql23 = "select PWName from tblPregnant_woman where PWGUID in (select pw_GUID from tblChild where ChildGUID='"
                                + child.get(i).getChildGUID() + "')";
                        String HushbandName = dataProvider.getRecord(sql23);
                        String sql24 = "select Child_dob from tblChild where ChildGUID='"
                                + child.get(i).getChildGUID() + "'";
                        String dob = dataProvider.getRecord(sql24);
                        String Namee = child.get(i).getChild_name();

                        name = String
                                .valueOf(Namee
                                        + " / "
                                        + HushbandName
                                        + " / "
                                        + Validate.changeDateFormat(String
                                        .valueOf(dob)));
                        MemberGUID = child.get(i).getChildGUID();

                        HHGUID = child.get(i).getHHGUID();

                        HHGUID = "";

                        MemberName = child.get(i).getChild_name();

                        sql = "select count(*) from tblVHNDDuelist where VHNDDate='"
                                + nextmnthdate
                                + "' and VillageID='"
                                + VillageID
                                + "' and BeneficiaryGUID='"
                                + MemberGUID + "'";
                        int count = dataProvider.getMaxRecordnew(sql);
                        if (count > 0) {
                            Flag = "U";
                        } else {
                            Flag = "I";
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    int returnvalue = dataProvider.VHND_DueListNew(MemberGUID,
                            HHGUID, MemberName, VaccineNames, nextmnthdate,
                            VillageID, AshaID, ANMID,
                            global.getsGlobalUserID(),
                            global.getsGlobalUserID(), Flag, 2, name);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void space() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        long bytesAvailable = (long) stat.getBlockSize()
                * (long) stat.getAvailableBlocks();
        long megAvailable = bytesAvailable;
        long totAvailable = (long) stat.getBlockSize()
                * (long) stat.getBlockCount();
        String totalspace = "" + (totAvailable / 1024);
        String space = "" + (megAvailable / 1024);

        dataProvider.insertspace(global.getUserID(), space, totalspace,
                getIMEI(this), "");

    }

    public void firebroadcastreceiver() {

        try {

            // alarmManagerPositioning.setInexactRepeating(alarmType,
            // timetoRefresh, interval, pendingIntentPositioning);

            Intent myIntent = new Intent(Dashboard_Activity.this,
                    ReceiverPositioningAlarm.class);

            boolean alarmrunning = (PendingIntent.getBroadcast(this, 0,
                    myIntent, PendingIntent.FLAG_NO_CREATE) != null);

            if (alarmrunning == false) {
                pendingIntent = PendingIntent
                        .getBroadcast(this, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                // alarmManager.setInexactRepeating(alarmType, timetoRefresh,
                // interval, pendingIntent);
                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime(), 1200000, pendingIntent);
            }

            // alarmManager.set(AlarmManager.RTC, interval, pendingIntent);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    public String getIMEI(Activity activity) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) activity
                    .getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }


}
