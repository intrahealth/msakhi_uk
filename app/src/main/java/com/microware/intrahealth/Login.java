package com.microware.intrahealth;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstANM;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.MstState;
import com.microware.intrahealth.object.TblMstuser;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.tblmstimmunizationQues;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity {
    Global global;
    DatabaseHelper dbHelper;
    @SuppressWarnings("unused")
    private static SQLiteDatabase dbIntraHealth;


    private static final String IMAGE_DIRECTORY_NAME = "msakhi/Media";

    JSONArray tblmstANCQuesArray = null;
    JSONArray tblmstFPQuesArray = null;

    JSONArray tblmstimmunizationQuesArray = null;

    public ArrayList<TblMstuser> tblUsermst = new ArrayList<TblMstuser>();
    public ArrayList<MstState> MstState = new ArrayList<MstState>();
    JSONArray Mst_tbl_Incentives = null;
    JSONArray MstVHND_PerformanceIndicator = null;
    JSONArray MstVHND_DueListItems = null;
    JSONArray VHND_Schedule = null;
    public ArrayList<MstANM> tblAnmcode = new ArrayList<MstANM>();

    public ArrayList<MstASHA> tblASHACode = new ArrayList<MstASHA>();

    JSONArray q_bankArray = null;
    JSONArray tblmediaarray = null;

    Button btnStart;
    TextView tv_fname, tv_version;
    EditText etUsername, etPassword;
    DataProvider dataProvider;
    ProgressDialog progressDialog;
    JSONArray MstUserArray = null;

    JSONArray MstuserashamappingArray = null;
    JSONArray anmashaArray = null;
    JSONArray ashavillageArray = null;
    JSONArray anmsubcenterArray = null;
    JSONArray mstpdfreportArray = null;
    JSONArray mstashaactivityArray = null;
    JSONArray mstchc = null;
    JSONArray MstRoleArray = null;
    JSONArray MstStateArray = null;
    JSONArray MstDistrictArray = null;
    JSONArray MstBlockArray = null;
    JSONArray MstPanchayatArray = null;
    JSONArray MstVillageArray = null;
    JSONArray MstCommonArray = null;
    JSONArray MstANMArray = null;
    JSONArray MstASHAArray = null;
    JSONArray MstSubCenterArray = null;
    JSONArray MstCatchmentSupervisorArray = null;
    JSONArray MstPHCArray = null;
    JSONArray MstSubCenterVillageMappingArray = null;
    JSONArray MstCatchmentAreaArray = null;
    public int iDownloadMaster = 0;
    String Downloadmsg = "";
    Activity activity;
    String mVersionNumber;
    String sUserName = "";
    String sPassword = "";
    int iRoleid = 0;
    int PERMISSION_ALL = 1;
    Validate validate;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION};
    String newVersion = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        global = (Global) getApplicationContext();
        etUsername = (EditText) findViewById(R.id.etusername);
        etPassword = (EditText) findViewById(R.id.etpassword);
        tv_fname = (TextView) findViewById(R.id.tv_fname);
        tv_version = (TextView) findViewById(R.id.tv_version);
        tv_version.setText(String.valueOf(getVersion()));
        btnStart = (Button) findViewById(R.id.btnStart);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);

        ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            Tracker t = ((Global) getApplication())
                    .getTracker(TrackerName.APP_TRACKER);
            t.setScreenName("Login Screen");
            t.send(new HitBuilders.AppViewBuilder().build());
        }


//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat
//                    .requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//        }
        // startActivityForResult(new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE),
        // READ_REQUEST_CODE);
        String sql1 = "";
        try {


            int roleid = 0;
            sql1 = "select count(UserID) from MstUser  ";

            int iCount1 = dataProvider.getMaxRecord(sql1);
            if (iCount1 == 1) {
                String sql = "select RoleID as cnt from MstUser  ";
                roleid = dataProvider.getcountRecord(sql);

                if (roleid == 3) {
                    String fname = "Select ANMName from MstANM  where languageId=2";
                    String fullname = dataProvider.getRecord(fname);
                    if (fullname.length() > 0) {
                        String uname = fullname;
                        tv_fname.setText(uname);
                        global.setsGlobalANMName(uname);
                    }

                } else if (roleid == 2) {
                    String fname = "Select ASHAName from MstASHA  where languageId=2 ";
                    String fullname = dataProvider.getRecord(fname);
                    if (fullname.length() > 0) {
                        String uname = fullname;
                        tv_fname.setText(uname);
                        global.setsGlobalAshaName(uname);
                    }

                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();// .Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (validate.RetriveSharepreferenceString("name").toString().length() > 0) {
            String name = validate.RetriveSharepreferenceString("name") + "/" + validate.RetriveSharepreferenceString("Username");
            tv_fname.setText(name);
        }
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                backup();
                backup1();
                if (etUsername != null
                        && etUsername.getText() != null
                        && !Validate.isTextValid(etUsername.getText()
                        .toString())) {
                    sUserName = etUsername.getText().toString();
                }

                if (etPassword != null
                        && etPassword.getText() != null
                        && !Validate.isTextValid(etPassword.getText()
                        .toString())) {
                    sPassword = etPassword.getText().toString();
                }
                global.setLanguage(2);
                String rolesql = "Select RoleID from MstUser";
                String sql = "Select count(UserID) from MstUser";
                String usersql = "select count(UserID) from MstUser where UserName='"
                        + sUserName
                        + "' and Password='"
                        + sPassword
                        + "' ";
                int iCount = dataProvider.getMaxRecord(sql);
                String sRoleid = dataProvider.getRecord(rolesql);
                if (sRoleid != null && sRoleid.length() > 0) {
                    iRoleid = Validate.returnIntegerValue(sRoleid);
                }
                int userCount = dataProvider.getMaxRecord(usersql);
                if (iCount < 4) {
                    if (iCount == 0) {

                        downloaddata(sUserName, sPassword, 1);


                    } else if (iCount <= 2 && iCount > 0 && userCount == 0 && iRoleid == 2) {
                        Login.this.runOnUiThread(new Runnable() {
                            public void run() {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                        Login.this);
                                builder1.setMessage(R.string.addanotheruser);
                                builder1.setCancelable(false);
                                builder1.setPositiveButton(
                                        getResources().getString(R.string.yes),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                                try {

                                                    if (isNetworkconn()) {
                                                        downloaddata(sUserName, sPassword, iRoleid);
                                                    } else
                                                        showNewWorkError();

                                                } catch (Exception e) {
                                                    // TODO: handle exception
                                                    Toast.makeText(
                                                            Login.this,
                                                            e.getMessage().toString(),
                                                            Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                                builder1.setNegativeButton(
                                        getResources().getString(R.string.no),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        });

                    } else if (iCount > 0 && userCount > 0) {
                        tblUsermst = dataProvider.tblmstUser(sUserName,
                                sPassword);

                        if (tblUsermst != null && tblUsermst.size() > 0) {

                            int iroleid = 0;
                            global.setsGlobalUserName(tblUsermst.get(0)
                                    .getUserNameL());
                            global.setsGlobalPassword(tblUsermst.get(0)
                                    .getPassword());
                            global.setsGlobalUserID(String.valueOf(tblUsermst
                                    .get(0).getUserID()));
                            validate.SaveSharepreferenceString("Username", tblUsermst
                                    .get(0).getUserNameL());
                            global.setUserID(tblUsermst.get(0).getUserID());
                            global.setiGlobalRoleID(tblUsermst.get(0)
                                    .getRoleID());
                            MstState = dataProvider.getstate(1);
                            if (MstState != null && MstState.size() > 0) {
                                global.setStateCode(MstState.get(0)
                                        .getStateCode());
                                global.setStateName(MstState.get(0)
                                        .getStateName());
                            }
                            iroleid = tblUsermst.get(0).getRoleID();

                            if (iroleid == 4) {
                                global.setiGlobalRoleID(iroleid);
                                ArrayList<HashMap<String, String>> data;
                                String sqlaf = "select * from MstCatchmentSupervisor where LanguageID='" + global.getLanguage() + "'";
                                data = dataProvider.getDynamicVal(sqlaf);
                                if (data != null && data.size() > 0) {
                                    validate.SaveSharepreferenceString("name", data.get(0).get("SupervisorName"));
                                    validate.SaveSharepreferenceString("AFID", data.get(0).get("CHS_ID"));

                                }
                                global.setiGlobalRoleID(iroleid);
                                Intent intent = new Intent(Login.this, AFAshaList.class);
                                startActivity(intent);
                            } else {
                                if (iroleid == 3) {

                                    tblAnmcode = dataProvider.getMstANM(2);
                                    if (tblAnmcode != null && tblAnmcode.size() > 0) {
                                        global.setsGlobalANMCODE(String
                                                .valueOf(tblAnmcode.get(0)
                                                        .getANMID()));
                                        global.setsGlobalANMName(tblAnmcode.get(0)
                                                .getANMName());
                                        global.setAnmidasAnmCode(String
                                                .valueOf(tblAnmcode.get(0)
                                                        .getANMCode()));
                                        validate.SaveSharepreferenceString("name", tblAnmcode.get(0)
                                                .getANMName());

                                    }

                                } else if (iroleid == 11) {
                                    ArrayList<HashMap<String, String>> chcdata = null;
                                    String sqlchc = "Select * from mstchc where LanguageID=2";
                                    chcdata = dataProvider.getDynamicVal(sqlchc);
                                    if (chcdata != null && chcdata.size() > 0) {
                                        global.setCHCID(Validate.returnIntegerValue(chcdata.get(0).get("CHCID")));
                                        global.setCHCName(tblUsermst.get(0).getFirst_name() + " " + tblUsermst.get(0).getLast_name());
                                        validate.SaveSharepreferenceString("name", tblUsermst.get(0).getFirst_name() + " " + tblUsermst.get(0).getLast_name());
                                    }

                                } else if (iroleid == 2) {
                                    tblASHACode = dataProvider
                                            .getashanameandCode(2, global.getUserID());
                                    if (tblASHACode != null
                                            && tblASHACode.size() > 0) {
                                        global.setsGlobalAshaCode(String
                                                .valueOf(tblASHACode.get(0)
                                                        .getASHAID()));
                                        global.setsGlobalAshaName(tblASHACode.get(0)
                                                .getASHAName());
                                        validate.SaveSharepreferenceString("name", tblASHACode.get(0)
                                                .getASHAName());

                                    }
                                    tblAnmcode = dataProvider.getMstANM(2);
                                    if (tblAnmcode != null && tblAnmcode.size() > 0) {
                                        global.setsGlobalANMCODE(String
                                                .valueOf(tblAnmcode.get(0)
                                                        .getANMID()));
                                        global.setsGlobalANMName(tblAnmcode.get(0)
                                                .getANMName());
                                        global.setAnmidasAnmCode(String
                                                .valueOf(tblAnmcode.get(0)
                                                        .getANMCode()));

                                    }
                                    //tv_fname.setText(global.getsGlobalAshaName());
                                }


                                Calendar cal = Calendar.getInstance();
                                Date currentLocalTime = cal.getTime();
                                SimpleDateFormat date1 = new SimpleDateFormat(
                                        "HH:mm a");

                                String localTime = date1.format(currentLocalTime);
                                String Login_GUID = Validate.random();
                                global.setLogin_GUID(String.valueOf(Login_GUID));

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "dd-MM-yyyy");
                                String dateStrings = sdf.format(date);
                                dataProvider.getUserLogin(Login_GUID, tblUsermst
                                                .get(0).getUserID(), "Login", "Login",
                                        localTime, dateStrings);
                                Intent in = new Intent(Login.this,
                                        Dashboard_Activity.class);
                                global.setNotification_flag(1);
                                global.setMsg_flag(1);
                                finish();
                                startActivity(in);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid user", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Multiuser limit Exceeded", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Multiuser limit Exceeded", Toast.LENGTH_SHORT).show();
                }


            }
        });
//        if (isNetworkconn()) {
////            GetVersionCode runner = new GetVersionCode();
////            runner.execute();
//        }

    }

    public String getVersion() {

        Context mContext = getApplicationContext();
        try {
            String pkg = mContext.getPackageName();
            mVersionNumber = mContext.getPackageManager()
                    .getPackageInfo(pkg, 0).versionName;
        } catch (NameNotFoundException e) {
            mVersionNumber = "?";
        }
        return " Version " + mVersionNumber;
    }

//    public void initializeFirebase() {
//        if (FirebaseApp.getApps(getApplicationContext()).isEmpty()) {
//            FirebaseApp.initializeApp(getApplicationContext(), FirebaseOptions.fromResource(getApplicationContext()));
//        }
//        final FirebaseRemoteConfig config = FirebaseRemoteConfig.getInstance();
//        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
//                .setDeveloperModeEnabled(BuildConfig.DEBUG)
//                .build();
//        config.setConfigSettings(configSettings);
//    }

    private class GetVersionCode extends AsyncTask<Void, String, String> {
        ProgressDialog dialog = new ProgressDialog(Login.this);

        protected void onPreExecute() {
            super.onPreExecute();
            // TODO Auto-generated method stub

            dialog.setMessage("Data Fetching...");
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
        protected String doInBackground(Void... voids) {
            String onlineVersion = null;

            try {
                newVersion = getApplicationContext().getPackageManager()
                        .getPackageInfo(Login.this.getPackageName(), 0).versionName;
                onlineVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + Login.this.getPackageName() + "&hl=it")
                        .timeout(5000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
                return onlineVersion;
            } catch (Exception e) {
                return onlineVersion;
            }
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            dialog.dismiss();
            try {
                if (onlineVersion != null && !onlineVersion.isEmpty() && newVersion != null && !newVersion.isEmpty()) {
                    if (!Validate.returnStringValue(onlineVersion).equalsIgnoreCase(Validate.returnStringValue(newVersion))) {
                        showUpdateDialog();
                    }
                }
                Log.d("update", "Current version " + newVersion + "playstore version " + onlineVersion);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id="
                                + getPackageName())));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                finish();
                startActivity(intent);
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

    public void onResume() {
        super.onResume();
        try {
            if (isNetworkconn()) {
                GetVersionCode runner = new GetVersionCode();
                runner.execute();
            }
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat
                        .requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
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

                //alertDialog.setIcon(R.drawable.3);
                alertDialog.show();
                alertDialog.setCancelable(false);
                // will close the app if the device
                // does't have camera

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void trackevent() {

    }

    private void ClearFPData() {

        String sQueryDeletetblHHSurvey = null;
        sQueryDeletetblHHSurvey = "Delete from  tblmstFPQues";
        dataProvider.executeSql(sQueryDeletetblHHSurvey);
        String sQueryDeletetblmstimmunizationQues = null;
        sQueryDeletetblmstimmunizationQues = "Delete from  tblmstimmunizationQues";
        dataProvider.executeSql(sQueryDeletetblmstimmunizationQues);
        String sQueryDeleteq_bank = null;
        sQueryDeleteq_bank = "Delete from  tblmstPNCQues";
        dataProvider.executeSql(sQueryDeleteq_bank);
        String sQueryDeletetblmstANCQues = null;
        sQueryDeletetblmstANCQues = "Delete from  tblmstANCQues";
        dataProvider.executeSql(sQueryDeletetblmstANCQues);
    }

    private void ClearMasterData() {

        String sQueryDeleteMstUserData = null;
        sQueryDeleteMstUserData = "Delete from  MstUser";
        dataProvider.executeSql(sQueryDeleteMstUserData);

        String sQueryDeleteMstRoleData = null;
        sQueryDeleteMstRoleData = "Delete from MstRole";
        dataProvider.executeSql(sQueryDeleteMstRoleData);

        String sQueryDeleteMstStateData = null;
        sQueryDeleteMstStateData = "Delete from MstState";
        dataProvider.executeSql(sQueryDeleteMstStateData);

        String sQueryDeleteMstDistrictData = null;
        sQueryDeleteMstDistrictData = "Delete from MstDistrict";
        dataProvider.executeSql(sQueryDeleteMstDistrictData);

        String sQueryDeleteMstBlockData = null;
        sQueryDeleteMstBlockData = "Delete from MstBlock";
        dataProvider.executeSql(sQueryDeleteMstBlockData);

        String sQueryDeleteMstPanchayatData = null;
        sQueryDeleteMstPanchayatData = "Delete from MstPanchayat";
        dataProvider.executeSql(sQueryDeleteMstPanchayatData);

        String sQueryDeleteMstVillageData = null;
        sQueryDeleteMstVillageData = "Delete from MstVillage";
        dataProvider.executeSql(sQueryDeleteMstVillageData);

        String sQueryDeleteMstCommonData = null;
        sQueryDeleteMstCommonData = "Delete from MstCommon";
        dataProvider.executeSql(sQueryDeleteMstCommonData);

        String sQueryDeleteMstANMData = null;
        sQueryDeleteMstANMData = "Delete from MstANM";
        dataProvider.executeSql(sQueryDeleteMstANMData);

        String sQueryDeleteMstASHAData = null;
        sQueryDeleteMstASHAData = "Delete from MstASHA";
        dataProvider.executeSql(sQueryDeleteMstASHAData);

        String sQueryDeleteMstSubCenterData = null;
        sQueryDeleteMstSubCenterData = "Delete from MstSubCenter";
        dataProvider.executeSql(sQueryDeleteMstSubCenterData);
        String sQueryDeletetblmstANCQues = null;
        sQueryDeletetblmstANCQues = "Delete from  tblmstANCQues";
        dataProvider.executeSql(sQueryDeletetblmstANCQues);
        String sQueryDeletetblmedia = null;
        sQueryDeletetblmedia = "Delete from tblmedia";
        dataProvider.executeSql(sQueryDeletetblmedia);

        String sQueryDeleteuserashamapping = "";
        sQueryDeleteuserashamapping = "Delete from userashamapping";
        dataProvider.executeSql(sQueryDeleteuserashamapping);

        String sQueryDeleteashavillage = "";
        sQueryDeleteashavillage = "Delete from ashavillage";
        dataProvider.executeSql(sQueryDeleteashavillage);
        String sQueryDeleteanmasha = null;
        sQueryDeleteanmasha = "Delete from anmasha";
        dataProvider.executeSql(sQueryDeleteanmasha);
    }

    public void downloaddata(String sUserName, String sPassword, int flag) {
        try {

            if (isNetworkconn()) {
                importmaster(sUserName, sPassword, flag);

            } else
                showNewWorkError();

        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(Login.this, e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("unused")
    public void importQuestion(String UserName, String Password) {
        // TODO Auto-generated method stub

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("replace your URL");

        // Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
        nameValuePair.add(new BasicNameValuePair("username", UserName));
        nameValuePair.add(new BasicNameValuePair("password", Password));
        nameValuePair.add(new BasicNameValuePair("sFlag", "Questions"));

        // Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        // making POST request.
        try {
            String responseBody = "";
            HttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());

            JSONObject jsonObj = null;
            try {

                jsonObj = new JSONObject(responseBody.toString());

            } catch (JSONException e1) {

                e1.printStackTrace();
            }

            try {

                tblmstFPQuesArray = jsonObj.getJSONArray("tblmstfpques");
                for (int i = 0; i < tblmstFPQuesArray.length(); i++) {
                    JSONObject FPQuesData = tblmstFPQuesArray.getJSONObject(i);
                    int Q_id = 0;
                    int grp = 0;
                    int q_type = 0;
                    String ftext = "";
                    int y_qid = 0;
                    int n_qid = 0;
                    String oinfo = "";
                    int def = 0;
                    int finishid = 0;
                    int pans = 0;
                    int qvid = 0;
                    int dispseq = 0;
                    String oprompt = "";
                    String Ans = "";
                    String Audio_FileName = "";

                    if (FPQuesData.getString("Q_id") != null
                            && FPQuesData.getString("Q_id").length() > 0
                            && !FPQuesData.getString("Q_id").equalsIgnoreCase(
                            "null")) {
                        Q_id = Integer.valueOf(FPQuesData.getInt("Q_id"));
                    }
                    if (FPQuesData.getString("grp") != null
                            && FPQuesData.getString("grp").length() > 0
                            && !FPQuesData.getString("grp").equalsIgnoreCase(
                            "null")) {
                        grp = Integer.valueOf(FPQuesData.getInt("grp"));
                    }
                    if (FPQuesData.getString("q_type") != null
                            && FPQuesData.getString("q_type").length() > 0
                            && !FPQuesData.getString("q_type")
                            .equalsIgnoreCase("null")) {
                        q_type = Integer.valueOf(FPQuesData.getInt("q_type"));
                    }
                    if (FPQuesData.getString("y_qid") != null
                            && FPQuesData.getString("y_qid").length() > 0
                            && !FPQuesData.getString("y_qid").equalsIgnoreCase(
                            "null")) {

                        y_qid = Integer.valueOf(FPQuesData.getInt("y_qid"));
                    }
                    if (FPQuesData.getString("n_qid") != null
                            && FPQuesData.getString("n_qid").length() > 0
                            && !FPQuesData.getString("n_qid").equalsIgnoreCase(
                            "null")) {
                        n_qid = Integer.valueOf(FPQuesData.getInt("n_qid"));
                    }
                    if (FPQuesData.getString("def") != null
                            && FPQuesData.getString("def").length() > 0
                            && !FPQuesData.getString("def").equalsIgnoreCase(
                            "null")) {

                        def = Integer.valueOf(FPQuesData.getInt("def"));
                    }
                    if (FPQuesData.getString("finishid") != null
                            && FPQuesData.getString("finishid").length() > 0
                            && !FPQuesData.getString("finishid")
                            .equalsIgnoreCase("null")) {
                        finishid = Integer.valueOf(FPQuesData
                                .getInt("finishid"));
                    }
                    if (FPQuesData.getString("pans") != null
                            && FPQuesData.getString("pans").length() > 0
                            && !FPQuesData.getString("pans").equalsIgnoreCase(
                            "null")) {
                        pans = Integer.valueOf(FPQuesData.getInt("pans"));
                    }
                    if (FPQuesData.getString("qvid") != null
                            && FPQuesData.getString("qvid").length() > 0
                            && !FPQuesData.getString("qvid").equalsIgnoreCase(
                            "null")) {
                        qvid = Integer.valueOf(FPQuesData.getInt("qvid"));
                    }
                    if (FPQuesData.getString("dispseq") != null
                            && FPQuesData.getString("dispseq").length() > 0
                            && !FPQuesData.getString("dispseq")
                            .equalsIgnoreCase("null")) {
                        dispseq = Integer.valueOf(FPQuesData.getInt("dispseq"));
                    }
                    if (FPQuesData.getString("Audio_FileName") != null
                            && FPQuesData.getString("Audio_FileName").length() > 0
                            && !FPQuesData.getString("Audio_FileName")
                            .equalsIgnoreCase("null")) {
                        Audio_FileName = FPQuesData.getString("Audio_FileName");
                    }

                    oinfo = FPQuesData.getString("oinfo");
                    ftext = FPQuesData.getString("ftext");
                    oprompt = FPQuesData.getString("oprompt");
                    Ans = FPQuesData.getString("Ans");

                    String sql = "";
                    sql = "Insert into tblmstFPQues( Q_id,Grp,Q_type,Ftext,Y_qid,N_qid,Oinfo,def,finishid,pans,qvid,dispseq,oprompt,Ans,Audio_FileName)values("
                            + Q_id
                            + ","
                            + grp
                            + ","
                            + q_type
                            + ",'"
                            + ftext
                            + "',"
                            + y_qid
                            + ","
                            + n_qid
                            + ",'"
                            + oinfo
                            + "',"
                            + def
                            + ","
                            + finishid
                            + ","
                            + pans
                            + ","
                            + qvid
                            + ","
                            + dispseq
                            + ",'"
                            + oprompt
                            + "','"
                            + Ans
                            + "','" + Audio_FileName + "')";
                    dataProvider.executeSql(sql);

                }
                tblmstimmunizationQuesArray = jsonObj
                        .getJSONArray("tblmstimmunizationques");
                for (int i = 0; i < tblmstimmunizationQuesArray.length(); i++) {
                    JSONObject ImmunizationQuesData = tblmstimmunizationQuesArray
                            .getJSONObject(i);
                    int Q_id = 0;
                    int grp = 0;
                    int q_type = 0;
                    String ftext = "";
                    int y_qid = 0;
                    int n_qid = 0;
                    String oinfo = "";
                    int def = 0;
                    int finishid = 0;
                    int pans = 0;
                    int qvid = 0;
                    int dispseq = 0;
                    String oprompt = "";
                    String Ans = "";

                    if (ImmunizationQuesData.getString("Q_id") != null
                            && ImmunizationQuesData.getString("Q_id").length() > 0
                            && !ImmunizationQuesData.getString("Q_id")
                            .equalsIgnoreCase("null")) {
                        Q_id = Integer.valueOf(ImmunizationQuesData
                                .getInt("Q_id"));
                    }
                    if (ImmunizationQuesData.getString("grp") != null
                            && ImmunizationQuesData.getString("grp").length() > 0
                            && !ImmunizationQuesData.getString("grp")
                            .equalsIgnoreCase("null")) {
                        grp = Integer.valueOf(ImmunizationQuesData
                                .getInt("grp"));
                    }
                    if (ImmunizationQuesData.getString("q_type") != null
                            && ImmunizationQuesData.getString("q_type")
                            .length() > 0
                            && !ImmunizationQuesData.getString("q_type")
                            .equalsIgnoreCase("null")) {
                        q_type = Integer.valueOf(ImmunizationQuesData
                                .getInt("q_type"));
                    }
                    if (ImmunizationQuesData.getString("y_qid") != null
                            && ImmunizationQuesData.getString("y_qid").length() > 0
                            && !ImmunizationQuesData.getString("y_qid")
                            .equalsIgnoreCase("null")) {

                        y_qid = Integer.valueOf(ImmunizationQuesData
                                .getInt("y_qid"));
                    }
                    if (ImmunizationQuesData.getString("n_qid") != null
                            && ImmunizationQuesData.getString("n_qid").length() > 0
                            && !ImmunizationQuesData.getString("n_qid")
                            .equalsIgnoreCase("null")) {
                        n_qid = Integer.valueOf(ImmunizationQuesData
                                .getInt("n_qid"));
                    }
                    if (ImmunizationQuesData.getString("def") != null
                            && ImmunizationQuesData.getString("def").length() > 0
                            && !ImmunizationQuesData.getString("def")
                            .equalsIgnoreCase("null")) {

                        def = Integer.valueOf(ImmunizationQuesData
                                .getInt("def"));
                    }

                    if (ImmunizationQuesData.getString("pans") != null
                            && ImmunizationQuesData.getString("pans").length() > 0
                            && !ImmunizationQuesData.getString("pans")
                            .equalsIgnoreCase("null")) {
                        pans = Integer.valueOf(ImmunizationQuesData
                                .getInt("pans"));
                    }
                    if (ImmunizationQuesData.getString("qvid") != null
                            && ImmunizationQuesData.getString("qvid").length() > 0
                            && !ImmunizationQuesData.getString("qvid")
                            .equalsIgnoreCase("null")) {
                        qvid = Integer.valueOf(ImmunizationQuesData
                                .getInt("qvid"));
                    }
                    if (ImmunizationQuesData.getString("dispseq") != null
                            && ImmunizationQuesData.getString("dispseq")
                            .length() > 0
                            && !ImmunizationQuesData.getString("dispseq")
                            .equalsIgnoreCase("null")) {
                        dispseq = Integer.valueOf(ImmunizationQuesData
                                .getInt("dispseq"));
                    }

                    oinfo = ImmunizationQuesData.getString("oinfo");
                    ftext = ImmunizationQuesData.getString("ftext");
                    oprompt = ImmunizationQuesData.getString("oprompt");
                    Ans = ImmunizationQuesData.getString("AnsField");

                    String sql = "";
                    sql = "Insert into tblmstimmunizationQues( Q_id,grp,q_type,ftext,y_qid,n_qid,oinfo,def,pans,qvid,dispseq,oprompt,AnsField)values("
                            + Q_id
                            + ","
                            + grp
                            + ","
                            + q_type
                            + ",'"
                            + ftext
                            + "',"
                            + y_qid
                            + ","
                            + n_qid
                            + ",'"
                            + oinfo
                            + "',"
                            + def
                            + ","
                            + pans
                            + ","
                            + qvid
                            + ","
                            + dispseq
                            + ",'" + oprompt + "','" + Ans + "')";
                    dataProvider.executeSql(sql);

                }

                q_bankArray = jsonObj.getJSONArray("tblmstpncques");
                for (int i = 0; i < q_bankArray.length(); i++) {
                    JSONObject q_bankData = q_bankArray.getJSONObject(i);
                    int Q_id = 0;
                    int Grp = 0;
                    int q_type = 0;

                    int HV1 = 0;
                    int HV2 = 0;
                    int HV3 = 0;
                    int HV4 = 0;
                    int HV5 = 0;
                    int HV6 = 0;
                    int HV7 = 0;
                    int y_qid = 0;
                    int n_qid = 0;
                    String qtext = "";
                    String oinfo = "";
                    String AnsField = "";
                    String Audio_FileName = "";
                    if (q_bankData.getString("Audio_FileName") != null
                            && q_bankData.getString("Audio_FileName").length() > 0
                            && !q_bankData.getString("Audio_FileName")
                            .equalsIgnoreCase("null")) {
                        Audio_FileName = q_bankData.getString("Audio_FileName");
                    }

                    if (q_bankData.getString("Q_id") != null
                            && q_bankData.getString("Q_id").length() > 0
                            && !q_bankData.getString("Q_id").equalsIgnoreCase(
                            "null")) {
                        Q_id = Integer.valueOf(q_bankData.getInt("Q_id"));
                    }
                    if (q_bankData.getString("y_qid") != null
                            && q_bankData.getString("y_qid").length() > 0
                            && !q_bankData.getString("y_qid").equalsIgnoreCase(
                            "null")) {
                        y_qid = Integer.valueOf(q_bankData.getInt("y_qid"));
                    }
                    if (q_bankData.getString("n_qid") != null
                            && q_bankData.getString("n_qid").length() > 0
                            && !q_bankData.getString("n_qid").equalsIgnoreCase(
                            "null")) {
                        n_qid = Integer.valueOf(q_bankData.getInt("n_qid"));
                    }
                    if (q_bankData.getString("HV1") != null
                            && q_bankData.getString("HV1").length() > 0
                            && !q_bankData.getString("HV1").equalsIgnoreCase(
                            "null")) {
                        HV1 = Integer.valueOf(q_bankData.getInt("HV1"));
                    }

                    if (q_bankData.getString("HV2") != null
                            && q_bankData.getString("HV2").length() > 0
                            && !q_bankData.getString("HV2").equalsIgnoreCase(
                            "null")) {
                        HV2 = Integer.valueOf(q_bankData.getInt("HV2"));
                    }

                    if (q_bankData.getString("HV3") != null
                            && q_bankData.getString("HV3").length() > 0
                            && !q_bankData.getString("HV3").equalsIgnoreCase(
                            "null")) {
                        HV3 = Integer.valueOf(q_bankData.getInt("HV3"));
                    }

                    if (q_bankData.getString("HV4") != null
                            && q_bankData.getString("HV4").length() > 0
                            && !q_bankData.getString("HV4").equalsIgnoreCase(
                            "null")) {
                        HV4 = Integer.valueOf(q_bankData.getInt("HV4"));
                    }

                    if (q_bankData.getString("HV5") != null
                            && q_bankData.getString("HV5").length() > 0
                            && !q_bankData.getString("HV5").equalsIgnoreCase(
                            "null")) {
                        HV5 = Integer.valueOf(q_bankData.getInt("HV5"));
                    }

                    if (q_bankData.getString("HV6") != null
                            && q_bankData.getString("HV6").length() > 0
                            && !q_bankData.getString("HV6").equalsIgnoreCase(
                            "null")) {
                        HV6 = Integer.valueOf(q_bankData.getInt("HV6"));
                    }

                    if (q_bankData.getString("HV7") != null
                            && q_bankData.getString("HV7").length() > 0
                            && !q_bankData.getString("HV7").equalsIgnoreCase(
                            "null")) {
                        HV7 = Integer.valueOf(q_bankData.getInt("HV7"));
                    }

                    if (q_bankData.getString("Group") != null
                            && q_bankData.getString("Group").length() > 0
                            && !q_bankData.getString("Group").equalsIgnoreCase(
                            "null")) {
                        Grp = Integer.valueOf(q_bankData.getInt("Group"));
                    }

                    oinfo = q_bankData.getString("oinfo");
                    qtext = q_bankData.getString("qtext");

                    AnsField = q_bankData.getString("AnsField");

                    String sql = "";
                    sql = "Insert into tblmstPNCQues( Q_id,grp,q_type,Qtext,y_qid,n_qid,oinfo,AnsField,HV1,HV2,HV3,HV4,HV5,HV6,HV7,Audio_FileName)values("
                            + Q_id
                            + ","
                            + Grp
                            + ","
                            + q_type
                            + ",'"
                            + qtext
                            + "',"
                            + y_qid
                            + ","
                            + n_qid
                            + ",'"
                            + oinfo
                            + "','"
                            + AnsField
                            + "',"
                            + HV1
                            + ","
                            + HV2
                            + ","
                            + HV3
                            + ","
                            + HV4
                            + ","
                            + HV5
                            + ","
                            + HV6
                            + ","
                            + HV7
                            + ",'" + Audio_FileName + "')";

                    dataProvider.executeSql(sql);

                }

                tblmstANCQuesArray = jsonObj.getJSONArray("tblmstancques");
                for (int i = 0; i < tblmstANCQuesArray.length(); i++) {
                    JSONObject q_bankData = tblmstANCQuesArray.getJSONObject(i);
                    int Q_id = 0;
                    int Grp = 0;
                    int q_type = 0;

                    int HV1 = 0;
                    int HV2 = 0;
                    int HV3 = 0;
                    int HV4 = 0;

                    int y_qid = 0;
                    int n_qid = 0;
                    String qtext = "";
                    String ftext = "";
                    String oinfo = "";
                    String AnsField = "";
                    String Audio_filename = "";
                    Audio_filename = q_bankData.getString("audio_filename");
                    if (q_bankData.getString("Q_id") != null
                            && q_bankData.getString("Q_id").length() > 0
                            && !q_bankData.getString("Q_id").equalsIgnoreCase(
                            "null")) {
                        Q_id = Integer.valueOf(q_bankData.getInt("Q_id"));
                    }
                    if (q_bankData.getString("y_qid") != null
                            && q_bankData.getString("y_qid").length() > 0
                            && !q_bankData.getString("y_qid").equalsIgnoreCase(
                            "null")) {
                        y_qid = Integer.valueOf(q_bankData.getInt("y_qid"));
                    }
                    if (q_bankData.getString("n_qid") != null
                            && q_bankData.getString("n_qid").length() > 0
                            && !q_bankData.getString("n_qid").equalsIgnoreCase(
                            "null")) {
                        n_qid = Integer.valueOf(q_bankData.getInt("n_qid"));
                    }
                    if (q_bankData.getString("HV1") != null
                            && q_bankData.getString("HV1").length() > 0
                            && !q_bankData.getString("HV1").equalsIgnoreCase(
                            "null")) {
                        HV1 = Integer.valueOf(q_bankData.getInt("HV1"));
                    }

                    if (q_bankData.getString("HV2") != null
                            && q_bankData.getString("HV2").length() > 0
                            && !q_bankData.getString("HV2").equalsIgnoreCase(
                            "null")) {
                        HV2 = Integer.valueOf(q_bankData.getInt("HV2"));
                    }

                    if (q_bankData.getString("HV3") != null
                            && q_bankData.getString("HV3").length() > 0
                            && !q_bankData.getString("HV3").equalsIgnoreCase(
                            "null")) {
                        HV3 = Integer.valueOf(q_bankData.getInt("HV3"));
                    }

                    if (q_bankData.getString("HV4") != null
                            && q_bankData.getString("HV4").length() > 0
                            && !q_bankData.getString("HV4").equalsIgnoreCase(
                            "null")) {
                        HV4 = Integer.valueOf(q_bankData.getInt("HV4"));
                    }

                    if (q_bankData.getString("grp") != null
                            && q_bankData.getString("grp").length() > 0
                            && !q_bankData.getString("grp").equalsIgnoreCase(
                            "null")) {
                        Grp = Integer.valueOf(q_bankData.getInt("grp"));
                    }

                    oinfo = q_bankData.getString("oinfo");
                    qtext = q_bankData.getString("qtext");
                    ftext = q_bankData.getString("ftext");

                    AnsField = q_bankData.getString("AnsField");

                    String sql = "";
                    sql = "Insert into tblmstANCQues( Q_id,grp,q_type,Qtext,y_qid,n_qid,oinfo,AnsField,HV1,HV2,HV3,HV4,Ftext,Audio_filename)values("
                            + Q_id
                            + ","
                            + Grp
                            + ","
                            + q_type
                            + ",'"
                            + qtext
                            + "',"
                            + y_qid
                            + ","
                            + n_qid
                            + ",'"
                            + oinfo
                            + "','"
                            + AnsField
                            + "',"
                            + HV1
                            + ","
                            + HV2
                            + ","
                            + HV3
                            + ","
                            + HV4
                            + ",'"
                            + ftext
                            + "','"
                            + Audio_filename
                            + "')";

                    dataProvider.executeSql(sql);

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }

    }

    private void Downloadfile(String file) {

        try {
            String iImagePath, imageurl;
            imageurl = "replace your URL" + file;
            URL url = new URL(imageurl);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            // int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),
                    conection.getContentLength());
            File mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory(),
                    IMAGE_DIRECTORY_NAME);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory");

                }
            }
            iImagePath = mediaStorageDir.getPath() + File.separator;
            // Output stream to write file
            OutputStream output = new FileOutputStream(iImagePath + file);

            byte data[] = new byte[conection.getContentLength()];

            // long total = 0;

            // writing data to file

            int length;

            while ((length = input.read(data)) != -1) {
                output.write(data, 0, length);
            }

            // flushing output

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    private boolean isNetworkconn() {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if ((localConnectivityManager.getActiveNetworkInfo() != null)
                && (localConnectivityManager.getActiveNetworkInfo()
                .isAvailable())
                && (localConnectivityManager.getActiveNetworkInfo()
                .isConnected()))

            return true;
        else
            return false;

    }

    @SuppressWarnings("deprecation")
    private void showNewWorkError() {

        AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
        alertDialog.setTitle(getResources().getString(R.string.mSakhi));
        alertDialog.setMessage(getResources().getString(
                R.string.NoNetworkAccess));
        alertDialog.setButton(getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();
        // will close the app if the device does't have camera

    }

    public void importmaster(final String sUserName, final String sPassword, final int flag) {

        progressDialog = ProgressDialog.show(Login.this, "", getResources()
                .getString(R.string.DataLoading));
        new Thread() {

            @Override
            public void run() {
                try {
                    if (flag == 1) {
                        importMasterdata(sUserName, sPassword);
                        importQuestion(sUserName, sPassword);
                    } else if (flag == 2) {
                        String anmidsql = "Select ANMID from MstANM limit 1";

                        String anmid = dataProvider.getRecord(anmidsql);
                        importMasterdata(sUserName, sPassword, flag, anmid);
                    }

                } catch (Exception exp) {
                    progressDialog.dismiss();
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub

                        if (iDownloadMaster == 1) {
                            tblUsermst = dataProvider.tblmstUser(sUserName,
                                    sPassword);

                            if (tblUsermst != null && tblUsermst.size() > 0) {

                                int iroleid = 0;

                                global.setsGlobalUserName(tblUsermst.get(0)
                                        .getUserNameL());
                                global.setsGlobalPassword(tblUsermst.get(0)
                                        .getPassword());
                                global.setsGlobalUserID(String.valueOf(tblUsermst
                                        .get(0).getUserID()));
                                global.setUserID(tblUsermst.get(0).getUserID());
                                global.setiGlobalRoleID(tblUsermst.get(0)
                                        .getRoleID());
                                validate.SaveSharepreferenceString("Username", tblUsermst
                                        .get(0).getUserNameL());
                                MstState = dataProvider.getstate(1);
                                if (MstState != null && MstState.size() > 0) {
                                    global.setStateCode(MstState.get(0)
                                            .getStateCode());
                                    global.setStateName(MstState.get(0)
                                            .getStateName());
                                }
                                iroleid = tblUsermst.get(0).getRoleID();


                                if (iroleid == 4) {
                                    global.setiGlobalRoleID(iroleid);
                                    ArrayList<HashMap<String, String>> data;
                                    String sqlaf = "select * from MstCatchmentSupervisor where LanguageID=2";
                                    data = dataProvider.getDynamicVal(sqlaf);
                                    if (data != null && data.size() > 0) {
                                        validate.SaveSharepreferenceString("name", data.get(0).get("SupervisorName"));
                                        validate.SaveSharepreferenceString("AFID", data.get(0).get("CHS_ID"));

                                    }

                                    Intent intent = new Intent(Login.this, AFAshaList.class);
                                    intent.putExtra("flag",1);
                                    finish();
                                    startActivity(intent);
                                } else {
                                    if (iroleid == 3) {

                                        tblAnmcode = dataProvider.getMstANM(2);
                                        if (tblAnmcode != null && tblAnmcode.size() > 0) {
                                            global.setsGlobalANMCODE(String
                                                    .valueOf(tblAnmcode.get(0)
                                                            .getANMID()));
                                            global.setsGlobalANMName(tblAnmcode.get(0)
                                                    .getANMName());
                                            global.setAnmidasAnmCode(String
                                                    .valueOf(tblAnmcode.get(0)
                                                            .getANMCode()));

                                            validate.SaveSharepreferenceString("name", tblAnmcode.get(0)
                                                    .getANMName());
                                        }

                                    } else if (iroleid == 11) {
                                        ArrayList<HashMap<String, String>> chcdata = null;
                                        String sqlchc = "Select * from mstchc where LanguageID=2";
                                        chcdata = dataProvider.getDynamicVal(sqlchc);
                                        if (chcdata != null && chcdata.size() > 0) {
                                            global.setCHCID(Validate.returnIntegerValue(chcdata.get(0).get("CHCID")));

                                            validate.SaveSharepreferenceString("name", tblUsermst.get(0).getFirst_name() + " " + tblUsermst.get(0).getLast_name());
                                            global.setCHCName(tblUsermst.get(0).getFirst_name() + " " + tblUsermst.get(0).getLast_name());
                                        }

                                    } else if (iroleid == 2) {
                                        tblASHACode = dataProvider
                                                .getashanameandCode(2, global.getUserID());
                                        if (tblASHACode != null
                                                && tblASHACode.size() > 0) {
                                            global.setsGlobalAshaCode(String
                                                    .valueOf(tblASHACode.get(0)
                                                            .getASHAID()));
                                            validate.SaveSharepreferenceString("name", tblASHACode.get(0)
                                                    .getASHAName());
                                        }
                                        tblAnmcode = dataProvider.getMstANM(2);
                                        if (tblAnmcode != null && tblAnmcode.size() > 0) {
                                            global.setsGlobalANMCODE(String
                                                    .valueOf(tblAnmcode.get(0)
                                                            .getANMID()));
                                            global.setsGlobalANMName(tblAnmcode.get(0)
                                                    .getANMName());
                                            global.setAnmidasAnmCode(String
                                                    .valueOf(tblAnmcode.get(0)
                                                            .getANMCode()));
                                        }

                                    }

                                    String fname = "Select ASHAName from MstASHA where languageId=2";
                                    String fullname = dataProvider.getRecord(fname);
                                    if (fullname.length() > 0) {
                                        String uname = fullname;
                                        tv_fname.setText(uname);
                                        global.setsGlobalAshaName(uname);
                                    }

                                    Calendar cal = Calendar.getInstance();
                                    Date currentLocalTime = cal.getTime();
                                    SimpleDateFormat date1 = new SimpleDateFormat(
                                            "HH:mm a");

                                    String localTime = date1.format(currentLocalTime);
                                    String Login_GUID = Validate.random();
                                    global.setLogin_GUID(String.valueOf(Login_GUID));

                                    long date = System.currentTimeMillis();
                                    SimpleDateFormat sdf = new SimpleDateFormat(
                                            "dd-MM-yyyy");
                                    String dateStrings = sdf.format(date);
                                    dataProvider.getUserLogin(Login_GUID, tblUsermst
                                                    .get(0).getUserID(), "Login", "Login",
                                            localTime, dateStrings);
                                    Intent in = new Intent(Login.this,
                                            Dashboard_Activity.class);
                                    global.setNotification_flag(1);
                                    global.setMsg_flag(1);
                                    finish();
                                    startActivity(in);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Invalid user", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    Downloadmsg, Toast.LENGTH_LONG).show();
                        }
                    }
                });
                progressDialog.dismiss();

            }

        }.start();

    }


    @SuppressWarnings("unused")
    public void importMasterdata(String UserName, String Password) {
        // TODO Auto-generated method stub

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("replace your URL");

        // Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("username", UserName));
        nameValuePair.add(new BasicNameValuePair("password", Password));
        nameValuePair.add(new BasicNameValuePair("user_role", "0"));
        nameValuePair.add(new BasicNameValuePair("anmid", ""));
        nameValuePair.add(new BasicNameValuePair("imei", getIMEI(this)));

        // Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        // making POST request.
        try {
            String responseBody = "";
            HttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());

            JSONObject jsonObj = null;
            try {

                jsonObj = new JSONObject(responseBody.toString());

            } catch (JSONException e1) {
                Downloadmsg = responseBody;
                iDownloadMaster = 0;
                e1.printStackTrace();
            }

            // JSONObject jsonObj = new JSONObject(resultString.toString());
            ClearMasterData();
            ClearFPData();
            // Log.i(TAG1, "JsonObject: " + jsonObj);

            try {

                MstRoleArray = jsonObj.getJSONArray("mstrole");
                for (int i = 0; i < MstRoleArray.length(); i++) {
                    JSONObject Role = MstRoleArray.getJSONObject(i);
                    int iRoleID = 0;
                    String sRole = "";
                    int iIsDeleted = 0;
                    if (Role.getString("RoleID") != null
                            && Role.getString("RoleID").length() > 0
                            && !Role.getString("RoleID").equalsIgnoreCase(
                            "null")) {
                        iRoleID = Integer.valueOf(Role.getInt("RoleID"));
                    }
                    sRole = Role.getString("RoleName");
                    String sql = "";
                    sql = "Insert into MstRole(RoleID,Role,IsDeleted)values("
                            + iRoleID + ",'" + sRole + "'," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);
                }

                MstStateArray = jsonObj.getJSONArray("mststate");
                for (int i = 0; i < MstStateArray.length(); i++) {
                    JSONObject state = MstStateArray.getJSONObject(i);
                    int iStateID = 0;
                    String sStateCode = "";
                    String sState = "";
                    int iLanguageID = 1;
                    int iIsDeleted = 0;
                    if (state.getString("StateID") != null
                            && state.getString("StateID").length() > 0
                            && !state.getString("StateID").equalsIgnoreCase(
                            "null")) {
                        iStateID = Integer.valueOf(state.getInt("StateID"));
                    }
                    sStateCode = state.getString("StateCode");

                    sState = state.getString("StateName");
                    if (state.getString("LanguageID") != null
                            && state.getString("LanguageID").length() > 0
                            && !state.getString("LanguageID").equalsIgnoreCase(
                            "null")) {
                        iLanguageID = Integer.valueOf(state
                                .getInt("LanguageID"));
                    }
                    String sql = "";
                    sql = "Insert into Mststate(StateID,StateCode,StateName,LanguageID,IsDeleted)values("
                            + iStateID
                            + ",'"
                            + sStateCode
                            + "','"
                            + sState
                            + "'," + iLanguageID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);
                }
                MstDistrictArray = jsonObj.getJSONArray("mstdistrict");
                for (int i = 0; i < MstDistrictArray.length(); i++) {
                    JSONObject district = MstDistrictArray.getJSONObject(i);
                    int iDistrictID = 0;
                    String sStateCode = "";
                    String sDistrictCode = "";
                    String sDistrict = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    if (district.getString("DistrictID") != null
                            && district.getString("DistrictID").length() > 0
                            && !district.getString("DistrictID")
                            .equalsIgnoreCase("null")) {
                        iDistrictID = Integer.valueOf(district
                                .getInt("DistrictID"));
                    }
                    sStateCode = district.getString("StateCode");
                    sDistrictCode = district.getString("DistrictCode");
                    sDistrict = district.getString("DistrictName");
                    if (district.getString("LanguageID") != null
                            && district.getString("LanguageID").length() > 0
                            && !district.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(district
                                .getInt("LanguageID"));
                    }

                    String sql = "";
                    sql = "Insert into MSTDistrict(DistrictID,StateCode,DistrictCode,DistrictName,LanguageID,IsDeleted)values("
                            + iDistrictID
                            + ",'"
                            + sStateCode
                            + "','"
                            + sDistrictCode
                            + "','"
                            + sDistrict
                            + "',"
                            + iLanguageID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);
                }

                MstBlockArray = jsonObj.getJSONArray("mstblock");
                for (int i = 0; i < MstBlockArray.length(); i++) {
                    JSONObject block = MstBlockArray.getJSONObject(i);
                    int iBlockID = 0;
                    String sStateCode = "";
                    String sDistrictCode = "";
                    String sBlockCode = "";
                    String sBlock = null;
                    int iLanguageID = 0;
                    int iIsDeleted = 0;

                    if (block.getString("BlockID") != null
                            && block.getString("BlockID").length() > 0
                            && !block.getString("BlockID").equalsIgnoreCase(
                            "null")) {
                        iBlockID = Integer.valueOf(block.getInt("BlockID"));
                    }

                    sStateCode = block.getString("StateCode");
                    sDistrictCode = block.getString("DistrictCode");
                    sBlockCode = block.getString("BlockCode");
                    sBlock = block.getString("BlockName");
                    if (block.getString("LanguageID") != null
                            && block.getString("LanguageID").length() > 0
                            && !block.getString("LanguageID").equalsIgnoreCase(
                            "null")) {
                        iLanguageID = Integer.valueOf(block
                                .getInt("LanguageID"));
                    }

                    String sql = "";
                    sql = "Insert into MSTBlock(BlockID,StateCode,DistrictCode,BlockCode,BlockName,LanguageID,IsDeleted)values("
                            + iBlockID
                            + ",'"
                            + sStateCode
                            + "','"
                            + sDistrictCode
                            + "','"
                            + sBlockCode
                            + "','"
                            + sBlock
                            + "',"
                            + iLanguageID
                            + ","
                            + iIsDeleted
                            + ")";
                    dataProvider.executeSql(sql);

                }

                MstPanchayatArray = jsonObj.getJSONArray("mstpanchayat");
                for (int i = 0; i < MstPanchayatArray.length(); i++) {
                    JSONObject panchayat = MstPanchayatArray.getJSONObject(i);
                    int iPanchayatID = 0;
                    String sStateCode = "";
                    String sDistrictCode = "";
                    String sBlockCode = "";
                    String sPanchayatCode = "";
                    String sPanchayat = null;
                    int iLanguageID = 0;
                    int iIsDeleted = 0;

                    if (panchayat.getString("PanchayatID") != null
                            && panchayat.getString("PanchayatID").length() > 0
                            && !panchayat.getString("PanchayatID")
                            .equalsIgnoreCase("null")) {
                        iPanchayatID = Integer.valueOf(panchayat
                                .getInt("PanchayatID"));
                    }

                    sStateCode = panchayat.getString("StateCode");
                    sDistrictCode = panchayat.getString("DistrictCode");
                    sBlockCode = panchayat.getString("BlockCode");
                    sPanchayatCode = panchayat.getString("PanchayatCode");
                    sPanchayat = panchayat.getString("PanchayatName");
                    if (panchayat.getString("LanguageID") != null
                            && panchayat.getString("LanguageID").length() > 0
                            && !panchayat.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(panchayat
                                .getInt("LanguageID"));
                    }

                    String sql = "";
                    sql = "Insert into MstPanchayat(PanchayatID,StateCode,DistrictCode,BlockCode,PanchayatCode,PanchayatName,LanguageID,IsDeleted)values("
                            + iPanchayatID
                            + ",'"
                            + sStateCode
                            + "','"
                            + sDistrictCode
                            + "','"
                            + sBlockCode
                            + "','"
                            + sPanchayatCode
                            + "','"
                            + sPanchayat
                            + "',"
                            + iLanguageID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);

                }

                MstVillageArray = jsonObj.getJSONArray("mstvillage");
                for (int i = 0; i < MstVillageArray.length(); i++) {
                    JSONObject village = MstVillageArray.getJSONObject(i);
                    int iVillageID = 0;
                    String sStateCode = "";
                    String sDistrictCode = "";
                    String sBlockCode = "";
                    String sPanchayatCode = "";
                    String sVillageCode = "";
                    String sVillage = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    if (village.getString("VillageID") != null
                            && village.getString("VillageID").length() > 0
                            && !village.getString("VillageID")
                            .equalsIgnoreCase("null")) {
                        iVillageID = Integer.valueOf(village
                                .getInt("VillageID"));
                    }
                    sStateCode = village.getString("StateCode");
                    sDistrictCode = village.getString("DistrictCode");
                    sBlockCode = village.getString("BlockCode");
                    sPanchayatCode = village.getString("PanchayatCode");
                    sVillageCode = village.getString("VillageCode");
                    sVillage = village.getString("VillageName");
                    if (village.getString("LanguageID") != null
                            && village.getString("LanguageID").length() > 0
                            && !village.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(village
                                .getInt("LanguageID"));
                    }

                    String sql = "";
                    sql = "Insert into MstVillage(VillageID,StateCode,DistrictCode,BlockCode,PanchayatCode,VillageCode,VillageName,LanguageID,IsDeleted)values("
                            + iVillageID
                            + ",'"
                            + sStateCode
                            + "','"
                            + sDistrictCode
                            + "','"
                            + sBlockCode
                            + "','"
                            + sPanchayatCode
                            + "','"
                            + sVillageCode
                            + "','"
                            + sVillage
                            + "',"
                            + iLanguageID
                            + ","
                            + iIsDeleted
                            + ")";
                    dataProvider.executeSql(sql);
                }

                MstCommonArray = jsonObj.getJSONArray("mstcommon");
                for (int i = 0; i < MstCommonArray.length(); i++) {
                    JSONObject MstCommon = MstCommonArray.getJSONObject(i);
                    int iUID = 0;
                    int iID = 0;
                    String sValue = "";
                    String sValueCode = "";
                    int iLanguageID = 0;
                    int iFlag = 0;
                    int iIsDeleted = 0;
                    if (MstCommon.getString("UID") != null
                            && MstCommon.getString("UID").length() > 0
                            && !MstCommon.getString("UID").equalsIgnoreCase(
                            "null")) {
                        iUID = Integer.valueOf(MstCommon.getInt("UID"));
                    }
                    if (MstCommon.getString("ID") != null
                            && MstCommon.getString("ID").length() > 0
                            && !MstCommon.getString("ID").equalsIgnoreCase(
                            "null")) {
                        iID = Integer.valueOf(MstCommon.getInt("ID"));
                    }
                    sValue = MstCommon.getString("Value");
                    sValueCode = MstCommon.getString("ValueCode");
                    if (MstCommon.getString("LanguageID") != null
                            && MstCommon.getString("LanguageID").length() > 0
                            && !MstCommon.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(MstCommon
                                .getInt("LanguageID"));
                    }
                    if (MstCommon.getString("Flag") != null
                            && MstCommon.getString("Flag").length() > 0
                            && !MstCommon.getString("Flag").equalsIgnoreCase(
                            "null")) {
                        iFlag = Integer.valueOf(MstCommon.getInt("Flag"));
                    }

                    String sql = "";
                    sql = "Insert into MstCommon(UID,ID,Value,ValueCode,LanguageID,Flag,IsDeleted)values("
                            + iUID
                            + ","
                            + iID
                            + ",'"
                            + sValue
                            + "','"
                            + sValueCode
                            + "',"
                            + iLanguageID
                            + ","
                            + iFlag
                            + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);
                }

                MstANMArray = jsonObj.getJSONArray("mstanm");
                for (int i = 0; i < MstANMArray.length(); i++) {
                    JSONObject mothertongue = MstANMArray.getJSONObject(i);
                    int iANMID = 0;
                    String sANMCode = null;
                    String sANMName = null;
                    int iIsDeleted = 0;
                    int iLanguageID = 0;
                    iANMID = Integer.valueOf(mothertongue.getInt("ANMID"));
                    sANMCode = mothertongue.getString("ANMCode");
                    sANMName = mothertongue.getString("ANMName");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));
                    }
                    String sql = "";
                    sql = "Insert into MstANM(ANMID,ANMCode,ANMName,LanguageID,IsDeleted)values("
                            + iANMID
                            + ",'"
                            + sANMCode
                            + "','"
                            + sANMName
                            + "'," + iLanguageID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);

                }

                MstASHAArray = jsonObj.getJSONArray("mstasha");
                for (int i = 0; i < MstASHAArray.length(); i++) {
                    JSONObject mothertongue = MstASHAArray.getJSONObject(i);
                    int iASHAID = 0;
                    String sANMCode = "";
                    String sASHACode = "";
                    String sASHAName = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    int iCHS_ID = 0;

                    iASHAID = Integer.valueOf(mothertongue.getInt("ASHAID"));
                    sANMCode = mothertongue.getString("ANMCode");
                    sASHACode = mothertongue.getString("ASHACode");
                    sASHAName = mothertongue.getString("ASHAName");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));
                    }
                    if (mothertongue.getString("CHS_ID") != null
                            && mothertongue.getString("CHS_ID").length() > 0
                            && !mothertongue.getString("CHS_ID")
                            .equalsIgnoreCase("null")) {
                        iCHS_ID = Integer
                                .valueOf(mothertongue.getInt("CHS_ID"));
                    }

                    String sql = "";
                    sql = "Insert into MstASHA(ASHAID,ANMCode,ASHACode,ASHAName,LanguageID,CHS_ID,IsDeleted)values("
                            + iASHAID
                            + ",'"
                            + sANMCode
                            + "','"
                            + sASHACode
                            + "','"
                            + sASHAName
                            + "',"
                            + iLanguageID
                            + ","
                            + iCHS_ID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);

                }

                MstSubCenterArray = jsonObj.getJSONArray("mstsubcenter");
                for (int i = 0; i < MstSubCenterArray.length(); i++) {
                    JSONObject mothertongue = MstSubCenterArray
                            .getJSONObject(i);
                    int iSubCenterID = 0;
                    String sSubCenterCode = "";
                    String sSubCenterName = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    int iPHC_id = 0;
                    iSubCenterID = Integer.valueOf(mothertongue
                            .getInt("SubCenterID"));
                    sSubCenterCode = mothertongue.getString("SubCenterCode");
                    sSubCenterName = mothertongue.getString("SubCenterName");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));
                    }
                    if (mothertongue.getString("PHC_id") != null
                            && mothertongue.getString("PHC_id").length() > 0
                            && !mothertongue.getString("PHC_id")
                            .equalsIgnoreCase("null")) {
                        iPHC_id = Integer
                                .valueOf(mothertongue.getInt("PHC_id"));
                    }

                    String sql = "";
                    sql = "Insert into MstSubCenter(SubCenterID,SubCenterCode,SubCenterName,LanguageID,PHC_id,IsDeleted)values("
                            + iSubCenterID
                            + ",'"
                            + sSubCenterCode
                            + "','"
                            + sSubCenterName
                            + "',"
                            + iLanguageID
                            + ","
                            + iPHC_id + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);

                }
                MstCatchmentSupervisorArray = jsonObj
                        .getJSONArray("mstcatchmentsupervisor");
                for (int i = 0; i < MstCatchmentSupervisorArray.length(); i++) {
                    JSONObject mothertongue = MstCatchmentSupervisorArray
                            .getJSONObject(i);

                    int LanguageID = 0;
                    int CHS_ID = 0;
                    int SubCenterID = 0;
                    String SupervisorCode = "";
                    String SupervisorName = "";
                    int IsDeleted = 0;

                    LanguageID = Integer.valueOf(mothertongue
                            .getInt("LanguageID"));
                    CHS_ID = Integer.valueOf(mothertongue.getInt("CHS_ID"));
                    SubCenterID = Integer.valueOf(mothertongue
                            .getInt("SubCenterID"));
                    SupervisorCode = mothertongue.getString("SupervisorCode");
                    SupervisorName = mothertongue.getString("SupervisorName");
                    IsDeleted = Integer.valueOf(mothertongue
                            .getInt("IsDeleted"));

                    String sql = "";
                    sql = "Insert into MstCatchmentSupervisor(LanguageID,CHS_ID,SubCenterID,SupervisorCode,SupervisorName,IsDeleted)values("
                            + LanguageID
                            + ","
                            + CHS_ID
                            + ","
                            + SubCenterID
                            + ",'"
                            + SupervisorCode
                            + "','"
                            + SupervisorName
                            + "'," + IsDeleted + ")";
                    dataProvider.executeSql(sql);

                }

                MstPHCArray = jsonObj.getJSONArray("mstphc");
                for (int i = 0; i < MstPHCArray.length(); i++) {
                    JSONObject mothertongue = MstPHCArray.getJSONObject(i);
                    int PHC_id = 0;
                    int Block_id = 0;
                    String PHC_Code = null;
                    String PHC_Name = null;
                    int LanguageID = 0;
                    int IsDeleted = 0;

                    if (mothertongue.getString("PHC_id") != null
                            && mothertongue.getString("PHC_id").length() > 0
                            && !mothertongue.getString("PHC_id")
                            .equalsIgnoreCase("null")) {
                        PHC_id = Integer.valueOf(mothertongue.getInt("PHC_id"));
                    }
                    if (mothertongue.getString("Block_id") != null
                            && mothertongue.getString("Block_id").length() > 0
                            && !mothertongue.getString("Block_id")
                            .equalsIgnoreCase("null")) {
                        Block_id = Integer.valueOf(mothertongue
                                .getInt("Block_id"));
                    }
                    PHC_Code = mothertongue.getString("PHC_Code");
                    PHC_Name = mothertongue.getString("PHC_Name");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        LanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));

                    }
                    String sql = "";
                    sql = "Insert into MstPHC(PHC_id,Block_id,PHC_Code,PHC_Name,LanguageID,IsDeleted)values("
                            + PHC_id
                            + ","
                            + Block_id
                            + ",'"
                            + PHC_Code
                            + "','"
                            + PHC_Name
                            + "',"
                            + LanguageID
                            + ","
                            + IsDeleted
                            + ")";
                    dataProvider.executeSql(sql);
                }

                MstSubCenterVillageMappingArray = jsonObj
                        .getJSONArray("mstsubcentervillagemapping");
                for (int i = 0; i < MstSubCenterVillageMappingArray.length(); i++) {
                    JSONObject mothertongue = MstSubCenterVillageMappingArray
                            .getJSONObject(i);
                    int SCVillageId = 0;
                    int SubCenterID = 0;
                    int VillageID = 0;

                    if (mothertongue.getString("SCVillageId") != null
                            && mothertongue.getString("SCVillageId").length() > 0
                            && !mothertongue.getString("SCVillageId")
                            .equalsIgnoreCase("null")) {
                        SCVillageId = Integer.valueOf(mothertongue
                                .getInt("SCVillageId"));
                    }
                    if (mothertongue.getString("SubCenterID") != null
                            && mothertongue.getString("SubCenterID").length() > 0
                            && !mothertongue.getString("SubCenterID")
                            .equalsIgnoreCase("null")) {
                        SubCenterID = Integer.valueOf(mothertongue
                                .getInt("SubCenterID"));
                    }
                    if (mothertongue.getString("VillageID") != null
                            && mothertongue.getString("VillageID").length() > 0
                            && !mothertongue.getString("VillageID")
                            .equalsIgnoreCase("null")) {
                        VillageID = Integer.valueOf(mothertongue
                                .getInt("VillageID"));

                    }
                    String sql = "";
                    sql = "Insert into MstSubCenterVillageMapping(SCVillageId,SubCenterID,VillageID)values("
                            + SCVillageId
                            + ","
                            + SubCenterID
                            + ","
                            + VillageID
                            + ")";
                    dataProvider.executeSql(sql);
                }

                // MstVersionArray = jsonObj.getJSONArray("MstVersion");
                // for (int i = 0; i < MstVersionArray.length(); i++) {
                // JSONObject mothertongue = MstVersionArray.getJSONObject(i);
                // int ApkVersionID = 0;
                // String Version = null;
                // String VersionCode = null;
                // int IsActive = 0;
                // int IsDeleted = 0;
                // if (mothertongue.getString("ApkVersionID") != null
                // && mothertongue.getString("ApkVersionID").length() > 0
                // && !mothertongue.getString("ApkVersionID")
                // .equalsIgnoreCase("null")) {
                // ApkVersionID = Integer.valueOf(mothertongue
                // .getInt("ApkVersionID"));
                // }
                // Version = mothertongue.getString("Version");
                // VersionCode = mothertongue.getString("VersionCode");
                // if (mothertongue.getString("IsActive") != null
                // && mothertongue.getString("IsActive").length() > 0
                // && !mothertongue.getString("IsActive")
                // .equalsIgnoreCase("null")) {
                // IsActive = Integer.valueOf(mothertongue
                // .getInt("IsActive"));
                // }
                //
                //
                //
                // String sql = "";
                // sql =
                // "Insert into MstVersion(SCVillageId,SubCenterID,VillageID)values("
                // + ApkVersionID
                // + ","
                // + Version
                // + ","
                // + VersionCode
                // + ","
                // + IsActive
                // + ","
                // + IsDeleted
                // + ")";
                // dataProvider.executeSql(sql);
                // }
                //

                MstCatchmentAreaArray = jsonObj
                        .getJSONArray("mstcatchmentarea");
                for (int i = 0; i < MstCatchmentAreaArray.length(); i++) {
                    JSONObject mothertongue = MstCatchmentAreaArray
                            .getJSONObject(i);
                    int ChAreaID = 0;
                    String StateCode = null;
                    String DistrictCode = null;
                    String BlockCode = null;
                    String VillageCode = null;
                    String ChAreaCode = null;
                    String ChAreaName = null;
                    int LanguageID = 0;
                    int IsDeleted = 0;

                    if (mothertongue.getString("ChAreaID") != null
                            && mothertongue.getString("ChAreaID").length() > 0
                            && !mothertongue.getString("ChAreaID")
                            .equalsIgnoreCase("null")) {
                        ChAreaID = Integer.valueOf(mothertongue
                                .getInt("ChAreaID"));
                    }
                    StateCode = mothertongue.getString("StateCode");
                    DistrictCode = mothertongue.getString("DistrictCode");
                    BlockCode = mothertongue.getString("BlockCode");
                    VillageCode = mothertongue.getString("VillageCode");
                    ChAreaCode = mothertongue.getString("ChAreaCode");
                    ChAreaName = mothertongue.getString("ChAreaName");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        LanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));
                    }

                    String sql = "";
                    sql = "Insert into MstCatchmentArea(ChAreaID,StateCode,DistrictCode,BlockCode,VillageCode,ChAreaCode,ChAreaName,LanguageID,IsDeleted)values("
                            + ChAreaID
                            + ",'"
                            + StateCode
                            + "','"
                            + DistrictCode
                            + "','"
                            + BlockCode
                            + "','"
                            + VillageCode
                            + "','"
                            + ChAreaCode
                            + "','"
                            + ChAreaName
                            + "',"
                            + LanguageID
                            + ","
                            + IsDeleted
                            + ")";
                    dataProvider.executeSql(sql);
                }
                // Herojit

                Mst_tbl_Incentives = jsonObj.getJSONArray("tbl_incentive");
                for (int i = 0; i < Mst_tbl_Incentives.length(); i++) {
                    JSONObject Incentive = Mst_tbl_Incentives.getJSONObject(i);
                    int IUID = 0;
                    int ASHA_ID = 0;
                    String ShortName = null;
                    String Description = null;
                    String Amount = null;
                    String Documents = null;
                    String Effect_Fromdate = null;
                    String Effect_todate = null;
                    int Status = 0;
                    String AreaType = null;
                    String Query = null;
                    String CreatedOn = null;
                    String CreatedBy = null;
                    int Language_Id = 0;
                    int Item_id = 0;

                    if (Incentive.getString("ASHA_ID") != null
                            && Incentive.getString("ASHA_ID").length() > 0
                            && !Incentive.getString("ASHA_ID")
                            .equalsIgnoreCase("null")) {
                        ASHA_ID = Integer.valueOf(Incentive.getInt("ASHA_ID"));
                    }
                    ShortName = Incentive.getString("ShortName");
                    Description = Incentive.getString("Description");
                    Amount = Incentive.getString("Amount");
                    Documents = Incentive.getString("Documents");
                    Effect_Fromdate = Incentive.getString("Effect_Fromdate");
                    Effect_todate = Incentive.getString("Effect_todate");
                    if (Incentive.getString("Status") != null
                            && Incentive.getString("Status").length() > 0
                            && !Incentive.getString("Status").equalsIgnoreCase(
                            "null")) {
                        Status = Integer.valueOf(Incentive.getInt("Status"));
                    }
                    AreaType = Incentive.getString("AreaType");
                    Query = Incentive.getString("Query");
                    CreatedOn = Incentive.getString("CreatedOn");
                    CreatedBy = Incentive.getString("CreatedBy");
                    if (Incentive.getString("Language_Id") != null
                            && Incentive.getString("Language_Id").length() > 0
                            && !Incentive.getString("Language_Id")
                            .equalsIgnoreCase("null")) {
                        Language_Id = Integer.valueOf(Incentive
                                .getInt("Language_Id"));
                    }
                    if (Incentive.getString("item_id") != null
                            && Incentive.getString("item_id").length() > 0
                            && !Incentive.getString("item_id")
                            .equalsIgnoreCase("null")) {
                        Item_id = Integer.valueOf(Incentive.getInt("item_id"));
                    }

                    String sql = "";
                    sql = "Insert into tbl_Incentive(ASHA_ID,ShortName,Description,Amount,Documents,Effect_Fromdate,Effect_todate,Status,AreaType,Query,CreatedOn,CreatedBy,Language_Id,Item_id)values("
                            + ASHA_ID
                            + ",'"
                            + ShortName
                            + "','"
                            + Description
                            + "','"
                            + Amount
                            + "','"
                            + Documents
                            + "','"
                            + Effect_Fromdate
                            + "','"
                            + Effect_todate
                            + "',"
                            + Status
                            + ",'"
                            + AreaType
                            + "','"
                            + Query
                            + "','"
                            + CreatedOn
                            + "','"
                            + CreatedBy
                            + "','"
                            + Language_Id + "'," + Item_id + ")";
                    dataProvider.executeSql(sql);
                }
                MstVHND_PerformanceIndicator = jsonObj
                        .getJSONArray("mstvhnd_performanceindicator");
                for (int i = 0; i < MstVHND_PerformanceIndicator.length(); i++) {
                    JSONObject VHND_Perform = MstVHND_PerformanceIndicator
                            .getJSONObject(i);
                    int ID = 0;
                    String Item = null;
                    int LanguageId = 0;
                    int Status = 0;
                    if (VHND_Perform.getString("ID") != null
                            && VHND_Perform.getString("ID").length() > 0
                            && !VHND_Perform.getString("ID").equalsIgnoreCase(
                            "null")) {
                        ID = Integer.valueOf(VHND_Perform.getInt("ID"));
                    }
                    Item = VHND_Perform.getString("Item");
                    if (VHND_Perform.getString("LanguageId") != null
                            && VHND_Perform.getString("LanguageId").length() > 0
                            && !VHND_Perform.getString("LanguageId")
                            .equalsIgnoreCase("null")) {
                        LanguageId = Integer.valueOf(VHND_Perform
                                .getInt("LanguageId"));
                    }
                    if (VHND_Perform.getString("Status") != null
                            && VHND_Perform.getString("Status").length() > 0
                            && !VHND_Perform.getString("Status")
                            .equalsIgnoreCase("null")) {
                        Status = Integer.valueOf(VHND_Perform.getInt("Status"));
                    }
                    String sql = "";
                    sql = "Insert into MstVHND_PerformanceIndicator(ID,Item,LanguageId,status)values("
                            + ID
                            + ",'"
                            + Item
                            + "','"
                            + LanguageId
                            + "','"
                            + Status + "')";
                    dataProvider.executeSql(sql);
                }
                MstVHND_DueListItems = jsonObj
                        .getJSONArray("mstvhnd_duelistitems");
                for (int i = 0; i < MstVHND_DueListItems.length(); i++) {
                    JSONObject VHND_Duelist = MstVHND_DueListItems
                            .getJSONObject(i);
                    int ID = 0;
                    String Items = null;
                    int LanguageId = 0;
                    int Status = 0;
                    if (VHND_Duelist.getString("ID") != null
                            && VHND_Duelist.getString("ID").length() > 0
                            && !VHND_Duelist.getString("ID").equalsIgnoreCase(
                            "null")) {
                        ID = Integer.valueOf(VHND_Duelist.getInt("ID"));
                    }
                    Items = VHND_Duelist.getString("Items");
                    if (VHND_Duelist.getString("LanguageId") != null
                            && VHND_Duelist.getString("LanguageId").length() > 0
                            && !VHND_Duelist.getString("LanguageId")
                            .equalsIgnoreCase("null")) {
                        LanguageId = Integer.valueOf(VHND_Duelist
                                .getInt("LanguageId"));
                    }
                    if (VHND_Duelist.getString("Status") != null
                            && VHND_Duelist.getString("Status").length() > 0
                            && !VHND_Duelist.getString("Status")
                            .equalsIgnoreCase("null")) {
                        Status = Integer.valueOf(VHND_Duelist.getInt("Status"));
                    }
                    String sql = "";
                    sql = "Insert into MstVHND_DueListItems(ID,Items,LanguageId,LanguageId)values("
                            + ID
                            + ",'"
                            + Items
                            + "','"
                            + LanguageId
                            + "','"
                            + LanguageId + "')";
                    dataProvider.executeSql(sql);
                }
                VHND_Schedule = jsonObj.getJSONArray("vhnd_schedule");
                for (int i = 0; i < VHND_Schedule.length(); i++) {
                    JSONObject VHND_DuelistSchedule = VHND_Schedule
                            .getJSONObject(i);
                    int Schedule_ID = 0;
                    int SubCenter_ID = 0;
                    int ANM_ID = 0;
                    int ASHA_ID = 0;
                    int Village_ID = 0;
                    String AW_Name = null;
                    String Frequency = null;
                    int Year = 0;
                    int Occurence = 0;
                    int Days = 0;
                    String Jan = "";
                    String Feb = "";
                    String Mar = "";
                    String Apr = "";
                    String May = "";
                    String Jun = "";
                    String Jul = "";
                    String Aug = "";
                    String Sep = "";
                    String Oct = "";
                    String Nov = "";
                    String Dec = "";
                    String active = null;
                    String createdOn = null;
                    int createdBy = 0;
                    String updatedOn = null;
                    int updatedBy = 0;
                    String Year_Type = null;
                    if (VHND_DuelistSchedule.getString("Schedule_ID") != null
                            && VHND_DuelistSchedule.getString("Schedule_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Schedule_ID")
                            .equalsIgnoreCase("null")) {
                        Schedule_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Schedule_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("SubCenter_ID") != null
                            && VHND_DuelistSchedule.getString("SubCenter_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("SubCenter_ID")
                            .equalsIgnoreCase("null")) {
                        SubCenter_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("SubCenter_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("ANM_ID") != null
                            && VHND_DuelistSchedule.getString("ANM_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("ANM_ID")
                            .equalsIgnoreCase("null")) {
                        ANM_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("ANM_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("ASHA_ID") != null
                            && VHND_DuelistSchedule.getString("ASHA_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("ASHA_ID")
                            .equalsIgnoreCase("null")) {
                        ASHA_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("ASHA_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("Village_ID") != null
                            && VHND_DuelistSchedule.getString("Village_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Village_ID")
                            .equalsIgnoreCase("null")) {
                        Village_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Village_ID"));
                    }
                    AW_Name = VHND_DuelistSchedule.getString("AW_Name");
                    Frequency = VHND_DuelistSchedule.getString("Frequency");
                    if (VHND_DuelistSchedule.getString("Year") != null
                            && VHND_DuelistSchedule.getString("Year").length() > 0
                            && !VHND_DuelistSchedule.getString("Year")
                            .equalsIgnoreCase("null")) {
                        Year = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Year"));
                    }
                    if (VHND_DuelistSchedule.getString("Occurence") != null
                            && VHND_DuelistSchedule.getString("Occurence")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Occurence")
                            .equalsIgnoreCase("null")) {
                        Occurence = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Occurence"));
                    }
                    if (VHND_DuelistSchedule.getString("Days") != null
                            && VHND_DuelistSchedule.getString("Days").length() > 0
                            && !VHND_DuelistSchedule.getString("Days")
                            .equalsIgnoreCase("null")) {
                        Days = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Days"));
                    }
                    Jan = VHND_DuelistSchedule.getString("Jan");
                    Feb = VHND_DuelistSchedule.getString("Feb");
                    Mar = VHND_DuelistSchedule.getString("Mar");
                    Apr = VHND_DuelistSchedule.getString("Apr");
                    May = VHND_DuelistSchedule.getString("May");
                    Jun = VHND_DuelistSchedule.getString("Jun");
                    Jul = VHND_DuelistSchedule.getString("Jul");
                    Aug = VHND_DuelistSchedule.getString("Aug");
                    Sep = VHND_DuelistSchedule.getString("Sep");
                    Oct = VHND_DuelistSchedule.getString("Oct");
                    Nov = VHND_DuelistSchedule.getString("Nov");
                    Dec = VHND_DuelistSchedule.getString("Dec");
                    active = VHND_DuelistSchedule.getString("active");
                    createdOn = VHND_DuelistSchedule.getString("createdOn");
                    if (VHND_DuelistSchedule.getString("createdBy") != null
                            && VHND_DuelistSchedule.getString("createdBy")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("createdBy")
                            .equalsIgnoreCase("null")) {
                        createdBy = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("createdBy"));
                    }
                    updatedOn = VHND_DuelistSchedule.getString("updatedOn");
                    if (VHND_DuelistSchedule.getString("updatedBy") != null
                            && VHND_DuelistSchedule.getString("updatedBy")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("updatedBy")
                            .equalsIgnoreCase("null")) {
                        updatedBy = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("updatedBy"));
                    }
                    Year_Type = VHND_DuelistSchedule.getString("Year_Type");
                    String sql = "", sql1 = "";
                    sql = "Insert into VHND_Schedule(Schedule_ID,SubCenter_ID,ANM_ID,ASHA_ID,Village_ID,AW_Name,Frequency,Occurence,Days,Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec,active,createdOn,createdBy,updatedOn,updatedBy,Year_Type)values('"
                            + Schedule_ID
                            + "','"
                            + SubCenter_ID
                            + "','"
                            + ANM_ID
                            + "','"
                            + ASHA_ID
                            + "','"
                            + Village_ID
                            + "','"
                            + AW_Name
                            + "','"
                            + Frequency
                            + "','"
                            + Occurence
                            + "','"
                            + Days
                            + "','"
                            + Year
                            + "','"
                            + Jan
                            + "','"
                            + Feb
                            + "','"
                            + Mar
                            + "','"
                            + Apr
                            + "','"
                            + May
                            + "','"
                            + Jun
                            + "','"
                            + Jul
                            + "','"
                            + Aug
                            + "','"
                            + Sep
                            + "','"
                            + Oct
                            + "','"
                            + Nov
                            + "','"
                            + Dec
                            + "','"
                            + active
                            + "','"
                            + createdOn
                            + "','"
                            + createdBy
                            + "','"
                            + updatedOn
                            + "','"
                            + updatedBy + "','" + Year_Type + "')";
                    dataProvider.executeSql(sql);
                    for (int k = 0; k <= 11; k++) {
                        String VHND_ID = Validate.random();
                        String Month[] = {Jan, Feb, Mar, Apr, May, Jun, Jul,
                                Aug, Sep, Oct, Nov, Dec};
                        sql1 = "Insert into tbl_VHNDPerformance(VHND_ID,SS_ID,VillageId,AshaID,ANMID,Date,CreatedOn,CreatedBy,ModifyOn,ModifyBy,Isuploaded)values('"
                                + VHND_ID
                                + "','"
                                + SubCenter_ID
                                + "','"
                                + Village_ID
                                + "','"
                                + ASHA_ID
                                + "','"
                                + ANM_ID
                                + "','"
                                + Month[k]
                                + "','"
                                + createdOn
                                + "','"
                                + createdBy
                                + "','"
                                + updatedOn
                                + "','"
                                + updatedBy + "',0)";
                        dataProvider.executeSql(sql1);
                    }

                }
                String sql1 = "select StateCode as cnt from MstState limit 1";
                int stateid = dataProvider.getcountRecord(sql1);

                if (stateid == 5) {
                    tblmediaarray = jsonObj.getJSONArray("tblmedia");
                    for (int i = 0; i < tblmediaarray.length(); i++) {
                        JSONObject tblmedia = tblmediaarray.getJSONObject(i);
                        int ID = 0;
                        String FileName = "";

                        if (tblmedia.getString("FileName") != null
                                && tblmedia.getString("FileName").length() > 0
                                && !tblmedia.getString("FileName")
                                .equalsIgnoreCase("null")) {
                            FileName = tblmedia.getString("FileName").trim();
                        }

                        String sql = "";
                        sql = "Insert into tblmedia(FileName)values('"
                                + FileName + "')";

                        dataProvider.executeSql(sql);
                        File mediaStorageDir = new File(
                                Environment.getExternalStorageDirectory(),
                                IMAGE_DIRECTORY_NAME + File.separator + FileName);
                        if (!mediaStorageDir.exists()) {
                            Downloadfile(FileName);
                        }

                    }
                }
                MstuserashamappingArray = jsonObj.getJSONArray("userashamapping");
                for (int i = 0; i < MstuserashamappingArray.length(); i++) {
                    JSONObject Mstuserashamapping = MstuserashamappingArray.getJSONObject(i);
                    int AshaUserUID = 0;
                    int AshaID = 0;
                    int UserID = 0;
                    String AshaName = "";
                    String SubCenterName = "";

                    if (Mstuserashamapping.getString("AshaUserUID") != null
                            && Mstuserashamapping.getString("AshaUserUID").length() > 0
                            && !Mstuserashamapping.getString("AshaUserUID")
                            .equalsIgnoreCase("null")) {
                        AshaUserUID = Mstuserashamapping.getInt("AshaUserUID");
                    }
                    if (Mstuserashamapping.getString("AshaID") != null
                            && Mstuserashamapping.getString("AshaID").length() > 0
                            && !Mstuserashamapping.getString("AshaID")
                            .equalsIgnoreCase("null")) {
                        AshaID = Mstuserashamapping.getInt("AshaID");
                    }
                    if (Mstuserashamapping.getString("UserID") != null
                            && Mstuserashamapping.getString("UserID").length() > 0
                            && !Mstuserashamapping.getString("UserID")
                            .equalsIgnoreCase("null")) {
                        UserID = Mstuserashamapping.getInt("UserID");
                    }
                    AshaName = Mstuserashamapping.getString("AshaName");
                    SubCenterName = Mstuserashamapping.getString("SubCenterName");


                    String sql = "";
                    sql = "Insert into userashamapping(AshaUserUID,AshaID,UserID,AshaName,SubCenterName)values("
                            + AshaUserUID
                            + ","
                            + AshaID
                            + ","
                            + UserID
                            + ",'"
                            + AshaName
                            + "','"
                            + SubCenterName
                            + "')";
                    dataProvider.executeSql(sql);

                }
                anmashaArray = jsonObj.getJSONArray("anmasha");
                for (int i = 0; i < anmashaArray.length(); i++) {
                    JSONObject anmasha = anmashaArray.getJSONObject(i);
                    int ANMAshaUID = 0;
                    int ANMID = 0;
                    int ASHAID = 0;
                    if (anmasha.getString("ANMAshaUID") != null
                            && anmasha.getString("ANMAshaUID").length() > 0
                            && !anmasha.getString("ANMAshaUID")
                            .equalsIgnoreCase("null")) {
                        ANMAshaUID = anmasha.getInt("ANMAshaUID");
                    }
                    if (anmasha.getString("ANMID") != null
                            && anmasha.getString("ANMID").length() > 0
                            && !anmasha.getString("ANMID")
                            .equalsIgnoreCase("null")) {
                        ANMID = anmasha.getInt("ANMID");
                    }
                    if (anmasha.getString("ASHAID") != null
                            && anmasha.getString("ASHAID").length() > 0
                            && !anmasha.getString("ASHAID")
                            .equalsIgnoreCase("null")) {
                        ASHAID = anmasha.getInt("ASHAID");
                    }

                    String sql = "";
                    sql = "Insert into anmasha(ANMAshaUID,ANMID,ASHAID)values("
                            + ANMAshaUID
                            + ","
                            + ANMID
                            + ","
                            + ASHAID
                            + ")";
                    dataProvider.executeSql(sql);
                }
                ashavillageArray = jsonObj.getJSONArray("ashavillage");
                for (int i = 0; i < ashavillageArray.length(); i++) {
                    JSONObject ashavillage = ashavillageArray.getJSONObject(i);
                    int AshaVillageUID = 0;
                    int ASHAID = 0;
                    int VillageID = 0;

                    if (ashavillage.getString("AshaVillageUID") != null
                            && ashavillage.getString("AshaVillageUID").length() > 0
                            && !ashavillage.getString("AshaVillageUID")
                            .equalsIgnoreCase("null")) {
                        AshaVillageUID = ashavillage.getInt("AshaVillageUID");
                    }

                    if (ashavillage.getString("ASHAID") != null
                            && ashavillage.getString("ASHAID").length() > 0
                            && !ashavillage.getString("ASHAID")
                            .equalsIgnoreCase("null")) {
                        ASHAID = ashavillage.getInt("ASHAID");
                    }
                    if (ashavillage.getString("VillageID") != null
                            && ashavillage.getString("VillageID").length() > 0
                            && !ashavillage.getString("VillageID")
                            .equalsIgnoreCase("null")) {
                        VillageID = ashavillage.getInt("VillageID");
                    }


                    String sql = "";
                    sql = "Insert into ashavillage(AshaVillageUID,ASHAID,VillageID)values("
                            + AshaVillageUID
                            + ","
                            + ASHAID
                            + ","
                            + VillageID
                            + ")";
                    dataProvider.executeSql(sql);

                }
                mstchc = jsonObj.getJSONArray("mstchc");
                for (int i = 0; i < mstchc.length(); i++) {
                    JSONObject mstchcobj = mstchc.getJSONObject(i);
                    int UID = 0;
                    int CHCID = 0;
                    String CHCCode = "";
                    String CHCName = "";
                    int LanguageID = 0;
                    int IsDeleted = 0;

                    UID = Validate.returnIntegerValue(mstchcobj.getString("UID"));
                    CHCID = Validate.returnIntegerValue(mstchcobj.getString("CHCID"));
                    LanguageID = Validate.returnIntegerValue(mstchcobj.getString("LanguageID"));
                    IsDeleted = Validate.returnIntegerValue(mstchcobj.getString("IsDeleted"));
                    CHCCode = mstchcobj.getString("CHCCode");
                    CHCName = mstchcobj.getString("CHCName");
                    String sql = "";
                    sql = "Insert into mstchc(UID,CHCID,CHCCode,CHCName,LanguageID,IsDeleted)values("
                            + UID
                            + ",'"
                            + CHCID
                            + "','"
                            + CHCCode
                            + "','"
                            + CHCName
                            + "',"
                            + LanguageID
                            + ","
                            + IsDeleted + ")";
                    dataProvider.executeSql(sql);

                }
                anmsubcenterArray = jsonObj.getJSONArray("anmsubcenter");
                for (int i = 0; i < anmsubcenterArray.length(); i++) {
                    JSONObject anmsubcenter = anmsubcenterArray.getJSONObject(i);
                    int ANMSubCenterUID = 0;
                    int ANMID = 0;
                    int SubCenterID = 0;
                    int SubCenterCode = 0;
                    ANMSubCenterUID = Validate.returnIntegerValue(anmsubcenter.getString("ANMSubCenterUID"));
                    ANMID = Validate.returnIntegerValue(anmsubcenter.getString("ANMID"));
                    SubCenterID = Validate.returnIntegerValue(anmsubcenter.getString("SubCenterID"));
                    SubCenterCode = Validate.returnIntegerValue(anmsubcenter.getString("SubCenterCode"));


                    String sql = "";
                    sql = "Insert into anmsubcenter(ANMSubCenterUID,ANMID,SubCenterID,SubCenterCode)values("
                            + ANMSubCenterUID
                            + ",'"
                            + ANMID
                            + "','"
                            + SubCenterID
                            + "',"
                            + SubCenterCode
                            + ")";
                    dataProvider.executeSql(sql);

                }
                mstpdfreportArray = jsonObj.getJSONArray("mstpdfreport");
                for (int i = 0; i < mstpdfreportArray.length(); i++) {
                    JSONObject mstpdfreport = mstpdfreportArray.getJSONObject(i);
                    int UID = 0;
                    int ReportID = 0;
                    String ReportName = "";
                    int IsActive = 0;
                    UID = Validate.returnIntegerValue(mstpdfreport.optString("UID"));
                    ReportID = Validate.returnIntegerValue(mstpdfreport.optString("ReportID"));
                    ReportName = Validate.returnStringValue(mstpdfreport.optString("ReportName"));
                    IsActive = Validate.returnIntegerValue(mstpdfreport.optString("IsActive"));


                    String sql = "";
                    sql = "Insert into mstpdfreport(UID,ReportID,ReportName,IsActive)values("
                            + UID
                            + ",'"
                            + ReportID
                            + "','"
                            + ReportName
                            + "',"
                            + IsActive
                            + ")";
                    dataProvider.executeSql(sql);

                }
                mstashaactivityArray = jsonObj.getJSONArray("mstashaactivity");
                for (int i = 0; i < mstashaactivityArray.length(); i++) {
                    JSONObject mstashaactivity = mstashaactivityArray.getJSONObject(i);
                    int UID;
                    int SeqID;
                    String Qsrno;
                    int QuesID;
                    String Activity;
                    String Createdon;
                    String Updatedon;
                    String AreaType;
                    int Amount;
                    int CreatedBy;
                    int LangaugeID;
                    int Qtype;
                    UID = Validate.returnIntegerValue(mstashaactivity.optString("UID"));
                    SeqID = Validate.returnIntegerValue(mstashaactivity.optString("SeqID"));
                    Qsrno = Validate.returnStringValue(mstashaactivity.optString("Qsrno"));
                    QuesID = Validate.returnIntegerValue(mstashaactivity.optString("QuesID"));
                    Activity = Validate.returnStringValue(mstashaactivity.optString("Activity"));
                    Createdon = Validate.returnStringValue(mstashaactivity.optString("Createdon"));
                    Updatedon = Validate.returnStringValue(mstashaactivity.optString("Updatedon"));
                    AreaType = Validate.returnStringValue(mstashaactivity.optString("AreaType"));
                    Amount = Validate.returnIntegerValue(mstashaactivity.optString("Amount"));
                    CreatedBy = Validate.returnIntegerValue(mstashaactivity.optString("CreatedBy"));
                    LangaugeID = Validate.returnIntegerValue(mstashaactivity.optString("LangaugeID"));
                    Qtype = Validate.returnIntegerValue(mstashaactivity.optString("Qtype"));


                    String sql = "";
                    sql = "Insert into mstashaactivity(UID,SeqID,Qsrno,QuesID,Activity,Createdon,Updatedon,AreaType,Amount,CreatedBy,LangaugeID,Qtype)values("
                            + UID
                            + ",'"
                            + SeqID
                            + "','"
                            + Qsrno
                            + "','"
                            + QuesID
                            + "','"
                            + Activity
                            + "','"
                            + Createdon
                            + "','"
                            + Updatedon
                            + "','"
                            + AreaType
                            + "','"
                            + Amount
                            + "','"
                            + CreatedBy
                            + "','"
                            + LangaugeID
                            + "','"
                            + Qtype
                            + "')";
                    dataProvider.executeSql(sql);

                }
                MstUserArray = jsonObj.getJSONArray("tblusers");
                for (int i = 0; i < MstUserArray.length(); i++) {
                    JSONObject user = MstUserArray.getJSONObject(i);
                    int iUserID = 0;
                    int iRoleID = 1;
                    String sPassword = null;
                    String sUserName = null;

                    int iIsDeleted = 0;
                    int is_temp = 0;
                    String firstname = null;
                    String lastname = null;
                    if (user.getString("user_id") != null
                            && user.getString("user_id").length() > 0
                            && !user.getString("user_id").equalsIgnoreCase(
                            "null")) {
                        iUserID = Integer.valueOf(user.getInt("user_id"));
                    }
                    if (user.getString("is_temp") != null
                            && user.getString("is_temp").length() > 0
                            && !user.getString("is_temp").equalsIgnoreCase(
                            "null")) {
                        is_temp = Integer.valueOf(user.getInt("is_temp"));
                    }
                    if (user.getString("user_role") != null
                            && user.getString("user_role").length() > 0
                            && !user.getString("user_role").equalsIgnoreCase(
                            "null")) {
                        iRoleID = Integer.valueOf(user.getInt("user_role"));
                    }
                    sUserName = user.getString("user_name");
                    sPassword = Password;
                    firstname = Validate.returnStringValue(user.optString("first_name"));
                    lastname = Validate.returnStringValue(user.optString("last_name"));
                    String sql = "";
                    sql = "Insert into MstUser(UserID,UserName,RoleID,Password,IsDeleted,is_temp,first_name,last_name)values("
                            + iUserID
                            + ",'"
                            + sUserName
                            + "',"
                            + iRoleID
                            + ",'" + sPassword + "'," + iIsDeleted + "," + is_temp + ",'" + firstname + "','" + lastname + "')";
                    dataProvider.executeSql(sql);

                }

                iDownloadMaster = 1;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Downloadmsg = responseBody;
                iDownloadMaster = 0;
            }
            iDownloadMaster = 1;
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            Downloadmsg = e.toString();
            iDownloadMaster = 0;
        }
    }

    public String getIMEI(Activity activity) {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager telephonyManager = (TelephonyManager) activity
                        .getSystemService(Context.TELEPHONY_SERVICE);

                return telephonyManager.getDeviceId();
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    public void importMasterdata(String UserName, String Password, int roleid, String anmid) {
        // TODO Auto-generated method stub

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("replace your URL");

        // Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("username", UserName));
        nameValuePair.add(new BasicNameValuePair("password", Password));
        nameValuePair.add(new BasicNameValuePair("user_role", ("" + roleid)));
        nameValuePair.add(new BasicNameValuePair("anmid", anmid));
        nameValuePair.add(new BasicNameValuePair("imei", getIMEI(this)));
        // Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        } catch (UnsupportedEncodingException e) {
            // log exception
            e.printStackTrace();
        }

        // making POST request.
        try {
            String responseBody = "";
            HttpResponse response = httpClient.execute(httpPost);
            responseBody = EntityUtils.toString(response.getEntity());

            JSONObject jsonObj = null;
            try {

                jsonObj = new JSONObject(responseBody.toString());

            } catch (JSONException e1) {
                Downloadmsg = responseBody;
                iDownloadMaster = 0;
                e1.printStackTrace();
            }

            try {
                VHND_Schedule = jsonObj.getJSONArray("vhnd_schedule");
                for (int i = 0; i < VHND_Schedule.length(); i++) {
                    JSONObject VHND_DuelistSchedule = VHND_Schedule
                            .getJSONObject(i);
                    int Schedule_ID = 0;
                    int SubCenter_ID = 0;
                    int ANM_ID = 0;
                    int ASHA_ID = 0;
                    int Village_ID = 0;
                    String AW_Name = null;
                    String Frequency = null;
                    int Year = 0;
                    int Occurence = 0;
                    int Days = 0;
                    String Jan = "";
                    String Feb = "";
                    String Mar = "";
                    String Apr = "";
                    String May = "";
                    String Jun = "";
                    String Jul = "";
                    String Aug = "";
                    String Sep = "";
                    String Oct = "";
                    String Nov = "";
                    String Dec = "";
                    String active = null;
                    String createdOn = null;
                    int createdBy = 0;
                    String updatedOn = null;
                    int updatedBy = 0;
                    String Year_Type = null;
                    if (VHND_DuelistSchedule.getString("Schedule_ID") != null
                            && VHND_DuelistSchedule.getString("Schedule_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Schedule_ID")
                            .equalsIgnoreCase("null")) {
                        Schedule_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Schedule_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("SubCenter_ID") != null
                            && VHND_DuelistSchedule.getString("SubCenter_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("SubCenter_ID")
                            .equalsIgnoreCase("null")) {
                        SubCenter_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("SubCenter_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("ANM_ID") != null
                            && VHND_DuelistSchedule.getString("ANM_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("ANM_ID")
                            .equalsIgnoreCase("null")) {
                        ANM_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("ANM_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("ASHA_ID") != null
                            && VHND_DuelistSchedule.getString("ASHA_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("ASHA_ID")
                            .equalsIgnoreCase("null")) {
                        ASHA_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("ASHA_ID"));
                    }
                    if (VHND_DuelistSchedule.getString("Village_ID") != null
                            && VHND_DuelistSchedule.getString("Village_ID")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Village_ID")
                            .equalsIgnoreCase("null")) {
                        Village_ID = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Village_ID"));
                    }
                    AW_Name = VHND_DuelistSchedule.getString("AW_Name");
                    Frequency = VHND_DuelistSchedule.getString("Frequency");
                    if (VHND_DuelistSchedule.getString("Year") != null
                            && VHND_DuelistSchedule.getString("Year").length() > 0
                            && !VHND_DuelistSchedule.getString("Year")
                            .equalsIgnoreCase("null")) {
                        Year = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Year"));
                    }
                    if (VHND_DuelistSchedule.getString("Occurence") != null
                            && VHND_DuelistSchedule.getString("Occurence")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("Occurence")
                            .equalsIgnoreCase("null")) {
                        Occurence = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Occurence"));
                    }
                    if (VHND_DuelistSchedule.getString("Days") != null
                            && VHND_DuelistSchedule.getString("Days").length() > 0
                            && !VHND_DuelistSchedule.getString("Days")
                            .equalsIgnoreCase("null")) {
                        Days = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("Days"));
                    }
                    Jan = VHND_DuelistSchedule.getString("Jan");
                    Feb = VHND_DuelistSchedule.getString("Feb");
                    Mar = VHND_DuelistSchedule.getString("Mar");
                    Apr = VHND_DuelistSchedule.getString("Apr");
                    May = VHND_DuelistSchedule.getString("May");
                    Jun = VHND_DuelistSchedule.getString("Jun");
                    Jul = VHND_DuelistSchedule.getString("Jul");
                    Aug = VHND_DuelistSchedule.getString("Aug");
                    Sep = VHND_DuelistSchedule.getString("Sep");
                    Oct = VHND_DuelistSchedule.getString("Oct");
                    Nov = VHND_DuelistSchedule.getString("Nov");
                    Dec = VHND_DuelistSchedule.getString("Dec");
                    active = VHND_DuelistSchedule.getString("active");
                    createdOn = VHND_DuelistSchedule.getString("createdOn");
                    if (VHND_DuelistSchedule.getString("createdBy") != null
                            && VHND_DuelistSchedule.getString("createdBy")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("createdBy")
                            .equalsIgnoreCase("null")) {
                        createdBy = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("createdBy"));
                    }
                    updatedOn = VHND_DuelistSchedule.getString("updatedOn");
                    if (VHND_DuelistSchedule.getString("updatedBy") != null
                            && VHND_DuelistSchedule.getString("updatedBy")
                            .length() > 0
                            && !VHND_DuelistSchedule.getString("updatedBy")
                            .equalsIgnoreCase("null")) {
                        updatedBy = Integer.valueOf(VHND_DuelistSchedule
                                .getInt("updatedBy"));
                    }
                    Year_Type = VHND_DuelistSchedule.getString("Year_Type");
                    String sql = "", sql1 = "";
                    sql = "Insert into VHND_Schedule(Schedule_ID,SubCenter_ID,ANM_ID,ASHA_ID,Village_ID,AW_Name,Frequency,Occurence,Days,Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec,active,createdOn,createdBy,updatedOn,updatedBy,Year_Type)values('"
                            + Schedule_ID
                            + "','"
                            + SubCenter_ID
                            + "','"
                            + ANM_ID
                            + "','"
                            + ASHA_ID
                            + "','"
                            + Village_ID
                            + "','"
                            + AW_Name
                            + "','"
                            + Frequency
                            + "','"
                            + Occurence
                            + "','"
                            + Days
                            + "','"
                            + Year
                            + "','"
                            + Jan
                            + "','"
                            + Feb
                            + "','"
                            + Mar
                            + "','"
                            + Apr
                            + "','"
                            + May
                            + "','"
                            + Jun
                            + "','"
                            + Jul
                            + "','"
                            + Aug
                            + "','"
                            + Sep
                            + "','"
                            + Oct
                            + "','"
                            + Nov
                            + "','"
                            + Dec
                            + "','"
                            + active
                            + "','"
                            + createdOn
                            + "','"
                            + createdBy
                            + "','"
                            + updatedOn
                            + "','"
                            + updatedBy + "','" + Year_Type + "')";
                    dataProvider.executeSql(sql);
                    for (int k = 0; k <= 11; k++) {
                        String VHND_ID = Validate.random();
                        String Month[] = {Jan, Feb, Mar, Apr, May, Jun, Jul,
                                Aug, Sep, Oct, Nov, Dec};
                        sql1 = "Insert into tbl_VHNDPerformance(VHND_ID,SS_ID,VillageId,AshaID,ANMID,Date,CreatedOn,CreatedBy,ModifyOn,ModifyBy,Isuploaded)values('"
                                + VHND_ID
                                + "','"
                                + SubCenter_ID
                                + "','"
                                + Village_ID
                                + "','"
                                + ASHA_ID
                                + "','"
                                + ANM_ID
                                + "','"
                                + Month[k]
                                + "','"
                                + createdOn
                                + "','"
                                + createdBy
                                + "','"
                                + updatedOn
                                + "','"
                                + updatedBy + "',0)";
                        dataProvider.executeSql(sql1);
                    }

                }

                MstVillageArray = jsonObj.getJSONArray("mstvillage");
                for (int i = 0; i < MstVillageArray.length(); i++) {
                    JSONObject village = MstVillageArray.getJSONObject(i);
                    int iVillageID = 0;
                    String sStateCode = "";
                    String sDistrictCode = "";
                    String sBlockCode = "";
                    String sPanchayatCode = "";
                    String sVillageCode = "";
                    String sVillage = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    if (village.getString("VillageID") != null
                            && village.getString("VillageID").length() > 0
                            && !village.getString("VillageID")
                            .equalsIgnoreCase("null")) {
                        iVillageID = Integer.valueOf(village
                                .getInt("VillageID"));
                    }
                    sStateCode = village.getString("StateCode");
                    sDistrictCode = village.getString("DistrictCode");
                    sBlockCode = village.getString("BlockCode");
                    sPanchayatCode = village.getString("PanchayatCode");
                    sVillageCode = village.getString("VillageCode");
                    sVillage = village.getString("VillageName");
                    if (village.getString("LanguageID") != null
                            && village.getString("LanguageID").length() > 0
                            && !village.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(village
                                .getInt("LanguageID"));
                    }
                    String sqlcount = "Select count(*) from MstVillage where VillageID=" + iVillageID + " and LanguageID=" + iLanguageID + "";

                    int iCount = dataProvider.getMaxRecord(sqlcount);
                    if (iCount == 0) {
                        String sql = "";
                        sql = "Insert into MstVillage(VillageID,StateCode,DistrictCode,BlockCode,PanchayatCode,VillageCode,VillageName,LanguageID,IsDeleted)values("
                                + iVillageID
                                + ",'"
                                + sStateCode
                                + "','"
                                + sDistrictCode
                                + "','"
                                + sBlockCode
                                + "','"
                                + sPanchayatCode
                                + "','"
                                + sVillageCode
                                + "','"
                                + sVillage
                                + "',"
                                + iLanguageID
                                + ","
                                + iIsDeleted
                                + ")";
                        dataProvider.executeSql(sql);
                    }
                }

                MstCatchmentSupervisorArray = jsonObj
                        .getJSONArray("mstcatchmentsupervisor");
                for (int i = 0; i < MstCatchmentSupervisorArray.length(); i++) {
                    JSONObject mothertongue = MstCatchmentSupervisorArray
                            .getJSONObject(i);

                    int LanguageID = 0;
                    int CHS_ID = 0;
                    int SubCenterID = 0;
                    String SupervisorCode = "";
                    String SupervisorName = "";
                    int IsDeleted = 0;

                    LanguageID = Integer.valueOf(mothertongue
                            .getInt("LanguageID"));
                    CHS_ID = Integer.valueOf(mothertongue.getInt("CHS_ID"));
                    SubCenterID = Integer.valueOf(mothertongue
                            .getInt("SubCenterID"));
                    SupervisorCode = mothertongue.getString("SupervisorCode");
                    SupervisorName = mothertongue.getString("SupervisorName");
                    IsDeleted = Integer.valueOf(mothertongue
                            .getInt("IsDeleted"));
                    String sqlcount = "Select count(*) from MstCatchmentSupervisor where CHS_ID=" + CHS_ID + " and LanguageID=" + LanguageID + "";

                    int iCount = dataProvider.getMaxRecord(sqlcount);
                    if (iCount == 0) {
                        String sql = "";
                        sql = "Insert into MstCatchmentSupervisor(LanguageID,CHS_ID,SubCenterID,SupervisorCode,SupervisorName,IsDeleted)values("
                                + LanguageID
                                + ","
                                + CHS_ID
                                + ","
                                + SubCenterID
                                + ",'"
                                + SupervisorCode
                                + "','"
                                + SupervisorName
                                + "'," + IsDeleted + ")";
                        dataProvider.executeSql(sql);

                    }
                }
                MstASHAArray = jsonObj.getJSONArray("mstasha");
                for (int i = 0; i < MstASHAArray.length(); i++) {
                    JSONObject mothertongue = MstASHAArray.getJSONObject(i);
                    int iASHAID = 0;
                    String sANMCode = "";
                    String sASHACode = "";
                    String sASHAName = "";
                    int iLanguageID = 0;
                    int iIsDeleted = 0;
                    int iCHS_ID = 0;

                    iASHAID = Integer.valueOf(mothertongue.getInt("ASHAID"));
                    sANMCode = mothertongue.getString("ANMCode");
                    sASHACode = mothertongue.getString("ASHACode");
                    sASHAName = mothertongue.getString("ASHAName");
                    if (mothertongue.getString("LanguageID") != null
                            && mothertongue.getString("LanguageID").length() > 0
                            && !mothertongue.getString("LanguageID")
                            .equalsIgnoreCase("null")) {
                        iLanguageID = Integer.valueOf(mothertongue
                                .getInt("LanguageID"));
                    }
                    if (mothertongue.getString("CHS_ID") != null
                            && mothertongue.getString("CHS_ID").length() > 0
                            && !mothertongue.getString("CHS_ID")
                            .equalsIgnoreCase("null")) {
                        iCHS_ID = Integer
                                .valueOf(mothertongue.getInt("CHS_ID"));
                    }

                    String sql = "";
                    sql = "Insert into MstASHA(ASHAID,ANMCode,ASHACode,ASHAName,LanguageID,CHS_ID,IsDeleted)values("
                            + iASHAID
                            + ",'"
                            + sANMCode
                            + "','"
                            + sASHACode
                            + "','"
                            + sASHAName
                            + "',"
                            + iLanguageID
                            + ","
                            + iCHS_ID + "," + iIsDeleted + ")";
                    dataProvider.executeSql(sql);

                }
                MstUserArray = jsonObj.getJSONArray("tblusers");
                for (int i = 0; i < MstUserArray.length(); i++) {
                    JSONObject user = MstUserArray.getJSONObject(i);
                    int iUserID = 0;
                    int iRoleID = 1;
                    String sPassword = null;
                    String sUserName = null;
                    String firstname = null;
                    String lastname = null;
                    int iIsDeleted = 0;
                    int is_temp = 0;
                    if (user.getString("user_id") != null
                            && user.getString("user_id").length() > 0
                            && !user.getString("user_id").equalsIgnoreCase(
                            "null")) {
                        iUserID = Integer.valueOf(user.getInt("user_id"));
                    }
                    if (user.getString("user_role") != null
                            && user.getString("user_role").length() > 0
                            && !user.getString("user_role").equalsIgnoreCase(
                            "null")) {
                        iRoleID = Integer.valueOf(user.getInt("user_role"));
                    }
                    if (user.getString("is_temp") != null
                            && user.getString("is_temp").length() > 0
                            && !user.getString("is_temp").equalsIgnoreCase(
                            "null")) {
                        is_temp = Integer.valueOf(user.getInt("is_temp"));
                    }
                    sUserName = user.getString("user_name");
                    sPassword = Password;
                    firstname = Validate.returnStringValue(user.optString("firs_tname"));
                    lastname = Validate.returnStringValue(user.optString("last_name"));
                    String sql = "";
                    sql = "Insert into MstUser(UserID,UserName,RoleID,Password,IsDeleted,is_temp,first_name,last_name)values("
                            + iUserID
                            + ",'"
                            + sUserName
                            + "',"
                            + iRoleID
                            + ",'" + sPassword + "'," + iIsDeleted + "," + is_temp + ",'" + firstname + "','" + lastname + "')";
                    dataProvider.executeSql(sql);

                }

                iDownloadMaster = 1;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Downloadmsg = responseBody;
                iDownloadMaster = 0;
            }
            iDownloadMaster = 1;
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
            Downloadmsg = e.toString();
            iDownloadMaster = 0;
        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            GoogleAnalytics.getInstance(Login.this).reportActivityStart(this);
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            GoogleAnalytics.getInstance(Login.this).reportActivityStop(this);
        }
    }

    public void backup() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
            String ss = sdf.format(new Date());
            ss = "IntraHealthdb" + ss;
            String raw = null;
            if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                raw = String.valueOf(Environment.getExternalStorageDirectory());

            } else {
                File[] fs = this.getExternalFilesDirs(null);
                String extPath = "";
                // at index 0 you have the internal storage and at index 1 the
                // real
                // external...

                if (fs != null && fs.length >= 2) {

                    try {

                        extPath = fs[1].getAbsolutePath();
                    } catch (Exception e) {
                        extPath = fs[0].getAbsolutePath();
                        e.printStackTrace();
                    }

                    // Log.e("SD Path", fs[1].getAbsolutePath());
                }

                if (new File(extPath).exists()) {
                    raw = extPath;
                }
            }

            File sdcard = new File(raw + "/msakhi/");
            File outputFile = new File(sdcard, ss + "_1");
            File outputFile1 = new File(sdcard, ss);
            sdcard.mkdirs();

            if (!outputFile.exists()) {
                // sdcard.createNewFile();
                outputFile.createNewFile();
                outputFile1.createNewFile();
                File data = Environment.getDataDirectory();
                File inputFile = new File(data, "data/" + getPackageName()
                        + "/databases/" + "IntraHealth");
                InputStream input1 = new FileInputStream(inputFile);
                OutputStream output1 = new FileOutputStream(outputFile1);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = input1.read(buffer)) > 0) {
                    output1.write(buffer, 0, length);
                }
                output1.flush();
                output1.close();
                input1.close();
            }

            File data = Environment.getDataDirectory();
            File inputFile = new File(data, "data/" + getPackageName()
                    + "/databases/" + "IntraHealth");
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            // throw new Error("Copying Failed");
        }
    }

    public void backup1() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
            String ss = sdf.format(new Date());
            ss = "IntraHealthdb" + ss;
            String raw = null;

            raw = String.valueOf(Environment.getExternalStorageDirectory());


            File sdcard = new File(raw + "/msakhi/");
            File outputFile = new File(sdcard, ss + "_1");
            File outputFile1 = new File(sdcard, ss);
            if (!sdcard.exists()) {
                sdcard.mkdirs();
            }

            if (!outputFile.exists()) {
                // sdcard.createNewFile();
                outputFile.createNewFile();
                outputFile1.createNewFile();
                File data = Environment.getDataDirectory();
                File inputFile = new File(data, "data/" + getPackageName()
                        + "/databases/" + "IntraHealth");
                InputStream input1 = new FileInputStream(inputFile);
                OutputStream output1 = new FileOutputStream(outputFile1);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = input1.read(buffer)) > 0) {
                    output1.write(buffer, 0, length);
                }
                output1.flush();
                output1.close();
                input1.close();
            }

            File data = Environment.getDataDirectory();
            File inputFile = new File(data, "data/" + getPackageName()
                    + "/databases/" + "IntraHealth");
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            // throw new Error("Copying Failed");
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        finish();
        startActivity(intent);
    }

}
