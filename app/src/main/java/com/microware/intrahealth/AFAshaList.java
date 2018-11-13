package com.microware.intrahealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
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

/**
 * Created by Aditya on 10/4/2018.
 */

public class AFAshaList extends Activity {

    Button btn_Changeasha, btn_Continue;
    Global global;
    TextView tvAsha, tvdownloaddata, tv_Ashamsg, tv_dateaf, tv_uploadData, tv_dateuploadaf;
    private Locale myLocale;
    String sCurrentLanguage = "";
    Spinner spinAsha;
    DataProvider dataProvider;
    ArrayList<MstASHA> ASHA = new ArrayList<MstASHA>();
    ArrayAdapter<String> adapter;
    LinearLayout lineardownloadData, linearuploadData;
    ProgressDialog progressDialog;
    int iDataDownload = 0, flag = 1, iDataUpload = 0;
    public ArrayList<MstASHA> MstASHA = new ArrayList<MstASHA>();
    Validate validate;
    String downloadmsg;
    JSONArray HHSurveyData = null;
    JSONArray HHSurveyFamilyData = null;
    private static final String IMAGE_DIRECTORY_NAME = "mSakhi";
    JSONArray tblChildArray = null;
    JSONArray TblANCVisitArray = null;
    JSONArray tblpregnantwomenArray = null;
    JSONArray tblPNChomevisit_ANSArray = null;
    JSONArray tblverifyArray = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.af_asha_list);
        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        btn_Changeasha = (Button) findViewById(R.id.btn_Changeasha);
        btn_Continue = (Button) findViewById(R.id.btn_Continue);
        spinAsha = (Spinner) findViewById(R.id.spinAsha);
        lineardownloadData = (LinearLayout) findViewById(R.id.lineardownloadData);
        linearuploadData = (LinearLayout) findViewById(R.id.linearuploadData);
        tvAsha = (TextView) findViewById(R.id.tvAsha);

        tv_dateaf = (TextView) findViewById(R.id.tv_dateaf);
        tv_uploadData = (TextView) findViewById(R.id.tv_uploadData);
        tv_dateuploadaf = (TextView) findViewById(R.id.tv_dateuploadaf);
        tv_Ashamsg = (TextView) findViewById(R.id.tv_Ashamsg);
        tvdownloaddata = (TextView) findViewById(R.id.tvdownloaddata);
        lineardownloadData.setVisibility(View.GONE);


        spinAsha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    lineardownloadData.setVisibility(View.VISIBLE);
                    linearuploadData.setVisibility(View.VISIBLE);
                } else {
                    lineardownloadData.setVisibility(View.GONE);
                    linearuploadData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lineardownloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql1 = "";
                sql1 = "Select count(*) from tblafverify where IsEdited =1";
                int uploadcount = dataProvider.getMaxRecord(sql1);
                if (uploadcount == 0) {
                    AFAshaList.this.runOnUiThread(new Runnable() {
                        public void run() {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                    AFAshaList.this);
                            builder1.setMessage(getResources().getString(
                                    R.string.downloaddata));
                            builder1.setCancelable(false);
                            builder1.setPositiveButton(
                                    getResources().getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            dialog.cancel();
                                            try {

                                                if (isNetworkconn()) {
                                                    downloaddata(
                                                            global.getsGlobalUserName(),
                                                            global.getsGlobalPassword());
                                                } else
                                                    showNewWorkError();

                                            } catch (Exception e) {
                                                // TODO: handle exception
                                                Toast.makeText(
                                                        AFAshaList.this,
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
                } else {
                    Toast.makeText(
                            AFAshaList.this,
                            R.string.PleasefirstUploadData,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        linearuploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AFAshaList.this.runOnUiThread(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                AFAshaList.this);
                        builder1.setMessage(getResources().getString(
                                R.string.uploaddata));
                        builder1.setCancelable(false);
                        builder1.setPositiveButton(
                                getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.cancel();
                                        try {

                                            if (isNetworkconn()) {
                                                export();
                                            } else
                                                showNewWorkError();

                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            Toast.makeText(
                                                    AFAshaList.this,
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
            }
        });

        btn_Continue.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (validate.RetriveSharepreferenceInt("ASHAID") != 0) {

                    global.setsGlobalAshaCode(String.valueOf(validate.RetriveSharepreferenceInt("ASHAID")));
                    //global.setsGlobalAshaName(spinAsha.getSelectedItem().toString());
                    ArrayList<HashMap<String, String>> data;
                    String sqlaf = "select ANMID from anmasha where ASHAID='" + validate.RetriveSharepreferenceInt("ASHAID") + "'";
                    data = dataProvider.getDynamicVal(sqlaf);
                    if (data != null && data.size() > 0) {
                        global.setsGlobalANMCODE(data.get(0).get("ANMID"));
                        global.setAnmidasAnmCode(data.get(0).get("ANMID"));
                        validate.SaveSharepreferenceInt("ANMID", Validate.returnIntegerValue(data.get(0).get("ANMID")));

                    }
                    Intent intent = new Intent(AFAshaList.this, Dashboard_Activity.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(AFAshaList.this, R.string.datacontinue, Toast.LENGTH_LONG).show();
                }


            }
        });
        fillASHAname(global.getLanguage());
        if (validate.RetriveSharepreferenceInt("ASHAID") != 0) {
            spinAsha.setSelection(returnashaname(validate.RetriveSharepreferenceInt("ASHAID")));
            tv_Ashamsg.setText(Html.fromHtml(getString(R.string.informationofasha) + "&nbsp;" + validate.RetriveSharepreferenceString("ASHA") + "&nbsp;" + getString(R.string.inyourphone)));
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getInt("flag");
        }
        uploaddate();
        count();

    }

    public int returnashaname(int supervisorid) {
        int iASHA = 0;
        if (supervisorid > 0) {
            for (int i = 0; i < ASHA.size(); i++) {
                if (supervisorid == ASHA.get(i).getASHAID()) {
                    iASHA = i + 1;
                }
            }
        }
        return iASHA;

    }

    public void exportdata(ArrayList<HashMap<String, String>> data) {
        try {

            if (data != null && data.size() > 0) {
                HttpClient httpClient = new DefaultHttpClient();

                // replace with your url
                HttpPost httpPost = new HttpPost("replace your URL");
                JSONObject jsonObjectcombined = new JSONObject();
                try {

                    if (data.size() > 0) {
                        String json1 = new Gson().toJson(data);
                        JSONArray JSONArray_mstFPAns = new JSONArray(json1);
                        jsonObjectcombined.put("tblafverify",
                                JSONArray_mstFPAns);
                    } else {
                        JSONArray otherJsonArray = new JSONArray();
                        jsonObjectcombined.put("tblafverify",
                                otherJsonArray);

                    }


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Post Data
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

                nameValuePair.add(new BasicNameValuePair("username", global
                        .getsGlobalUserName()));

                nameValuePair.add(new BasicNameValuePair("password", global
                        .getsGlobalPassword()));

                nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
                        .toString()));
                nameValuePair.add(new BasicNameValuePair("IMEI", global.getIMEI()));
                nameValuePair.add(new BasicNameValuePair("VersionName", global.getVersionName()));

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
                            HTTP.UTF_8));
                } catch (UnsupportedEncodingException e) {
                    // log exception
                    e.printStackTrace();
                }


                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    String responseBody = EntityUtils
                            .toString(response.getEntity());

                    try {

                        if (responseBody.contains("success")) {

                            String sqltblmstimmunizationANS = "Update tblafverify set IsEdited=0 where  IsEdited=1 ";
                            dataProvider.executeSql(sqltblmstimmunizationANS);

                            iDataUpload = 1;

                        } else {
                            iDataUpload = 0;
                            downloadmsg = responseBody;
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                } catch (ClientProtocolException e) {
                    // Log exception
                    e.printStackTrace();
                }
            } else {
            }
        } catch (Exception e) {
            downloadmsg = e.getMessage();
            System.out.println(e);
        }

    }

    public void export() {
        final ArrayList<HashMap<String, String>> data;
        String sqlaf = "select * from tblafverify where IsEdited=1";
        data = dataProvider.getDynamicVal(sqlaf);
        if (isNetworkconn()) {
            if (data != null && data.size() > 0) {
                progressDialog = ProgressDialog.show(AFAshaList.this, "",
                        getResources().getString(R.string.Uploadingdata));
                new Thread() {

                    @Override
                    public void run() {
                        try {
                            exportdata(data);
                        } catch (Exception exp) {
                            progressDialog.dismiss();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (iDataUpload == 1) {
                                    Toast.makeText(
                                            AFAshaList.this,
                                            getResources()
                                                    .getString(
                                                            R.string.Datauploadsuccessfully),
                                            Toast.LENGTH_LONG).show();
                                    dataProvider.insertdownlaoddetail(15, "Uploadaf", global.getUserID(), global.getsGlobalAshaCode(), global.getsGlobalANMCODE());
                                    uploaddate();

                                    count();

                                } else {
                                    Toast.makeText(AFAshaList.this,
                                            downloadmsg, Toast.LENGTH_LONG)
                                            .show();

                                }

                            }
                        });

                        progressDialog.dismiss();

                    }
                }.start();
            } else {
                Toast.makeText(AFAshaList.this,
                        getResources().getString(R.string.NothingforUpload),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(AFAshaList.this,
                    getResources().getString(R.string.NoNetworkAccess),
                    Toast.LENGTH_LONG).show();
        }

    }

    public void uploaddate() {
        try {
            String sql1 = "", sqldate1 = "", sql2 = "", sqldate2 = "";
            sql1 = "Select CreatedOn from tblDowloadDetail where ModuleID=15 and CreatedBy=" + global.getUserID() + " order by UID DESC limit 1 ";
            sqldate1 = dataProvider.getRecord(sql1);
            sql2 = "Select CreatedOn from tblDowloadDetail where ModuleID=16 and CreatedBy=" + global.getUserID() + " order by UID DESC limit 1 ";
            sqldate2 = dataProvider.getRecord(sql2);


            tv_dateuploadaf.setText(getString(R.string.lastupload) + "  " + Validate.changeDateFormat(sqldate1));
            tv_dateaf.setText(getString(R.string.lastdownload) + "  " + Validate.changeDateFormat(sqldate2));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void count() {
        try {
            String sql1 = "";
            sql1 = "Select count(*) from tblafverify where IsEdited =1";
            int ncdcount = dataProvider.getMaxRecord(sql1);
            tv_uploadData.setText(getResources().getString(R.string.UploadData)
                    + " (" + ncdcount + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (flag == 1) {
            Intent i = new Intent(AFAshaList.this, Login.class);
            finish();
            startActivity(i);
        } else if (flag == 2) {
            Intent i = new Intent(AFAshaList.this, Dashboard_Activity.class);
            finish();
            startActivity(i);
        }
    }

    private void fillASHAname(int Language) {

        ASHA = dataProvider.getMstASHAname(Language, "", 1);

        String sValue[] = new String[ASHA.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < ASHA.size(); i++) {
            sValue[i + 1] = ASHA.get(i).getASHAName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinAsha.setAdapter(adapter);
    }

    public void downloaddata(final String sUserName, final String sPassword) {
        progressDialog = ProgressDialog.show(AFAshaList.this, "", getResources().getString(R.string.DataLoading));
        new Thread() {

            @Override
            public void run() {
                try {
                    importData(sUserName,
                            sPassword, String.valueOf(ASHA.get(spinAsha.getSelectedItemPosition() - 1).getASHAID()));
                } catch (Exception exp) {
                    progressDialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (iDataDownload == 1) {
                            Toast.makeText(AFAshaList.this, getResources().getString(
                                    R.string.DataDownloadsuccessfully), Toast.LENGTH_LONG).show();

                            global.setsGlobalAshaCode(String.valueOf(ASHA.get(spinAsha.getSelectedItemPosition() - 1).getASHAID()));
                            //global.setsGlobalAshaName(spinAsha.getSelectedItem().toString());
                            validate.SaveSharepreferenceString("ASHA", spinAsha.getSelectedItem().toString());
                            validate.SaveSharepreferenceInt("ASHAID", ASHA.get(spinAsha.getSelectedItemPosition() - 1).getASHAID());
                            ArrayList<HashMap<String, String>> data;
                            String sqlaf = "select ANMID from anmasha where ASHAID='" + ASHA.get(spinAsha.getSelectedItemPosition() - 1).getASHAID() + "'";
                            data = dataProvider.getDynamicVal(sqlaf);
                            if (data != null && data.size() > 0) {
                                global.setsGlobalANMCODE(data.get(0).get("ANMID"));
                                global.setAnmidasAnmCode(data.get(0).get("ANMID"));
                                validate.SaveSharepreferenceInt("ANMID", Validate.returnIntegerValue(data.get(0).get("ANMID")));

                            }
                            dataProvider.insertdownlaoddetail(16, "Downloadaf", global.getUserID(), global.getsGlobalAshaCode(), global.getsGlobalANMCODE());
                            uploaddate();
                            tv_Ashamsg.setText(Html.fromHtml(getString(R.string.informationofasha) + "&nbsp;" + validate.RetriveSharepreferenceString("ASHA") + "&nbsp;" + getString(R.string.inyourphone)));

                        } else {
                            validate.SaveSharepreferenceInt("ASHAID", 0);
                            validate.SaveSharepreferenceString("ASHA", "");

                            Toast.makeText(
                                    AFAshaList.this,
                                    getResources().getString(
                                            R.string.DatanotDownloadsuccessfully),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                progressDialog.dismiss();

            }

        }.start();

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

    @SuppressWarnings("deprecation")
    private void showNewWorkError() {

        AlertDialog alertDialog = new AlertDialog.Builder(AFAshaList.this)
                .create();
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


    public void importdata(String UserName, String Password,
                           String ashaid) {
        iDataDownload = 0;
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("replace your URL");

        // Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
        nameValuePair.add(new BasicNameValuePair("username", UserName));
        nameValuePair.add(new BasicNameValuePair("password", Password));
        nameValuePair.add(new BasicNameValuePair("asha_id", ashaid));

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
                if (responseBody.contains("tblhhsurvey")) {
                    ClearAshaData();
//                    String[] Tablename = {"Tbl_HHSurvey", "Tbl_HHFamilyMember", "tblPregnant_woman", "tblChild", "TblANCVisit", "tblPNChomevisit_ANS", "tblafverify"};
//                    String[] condition[] = {{"HHSurveyGUID"}, {"HHSurveyGUID", "HHFamilyMemberGUID"}, {"PWGUID"}, {"childGUID"}, {"PWGUID", "VisitGUID"}, {"ChildGUID", "PNCGUID", "visitno"}, {"ModuleGUID"}};
//                    String MasterTag[] = {"tblhhsurvey", "tblhhfamilymember", "tblpregnant_woman", "tblchild", "tblancvisit", "tblpnchomevisit_ans", "tblafverify"};
//
                    jsonObj = new JSONObject(responseBody.toString());
//                    //   global.setsGlobalPWGUID(jsonObj.getJSONArray("tblpregnant_woman").getJSONObject(0).getString("PWGUID"));
//                    dataProvider.ImportDataInMyWay(jsonObj, Tablename, condition, MasterTag);


                    HHSurveyData = jsonObj.getJSONArray("tblhhsurvey");
                    for (int i = 0; i < HHSurveyData.length(); i++) {
                        JSONObject SurveyData = HHSurveyData.getJSONObject(i);
                        String HHSurveyGUID = "";
                        int SubCenterID = 0;
                        int ANMID = 0;
                        int VillageID = 0;
                        int ASHAID = 0;
                        int FamilyCode = 0;
                        int CasteID = 0;
                        int FinancialStatusID = 0;
                        int CreatedBy = 0;
                        String CreatedOn = "";
                        int UploadedBy = 0;
                        String UploadedOn = "";
                        int IsTablet = 0;
                        int IsDeleted = 0;
                        int HHStatusID = 0;
                        String HHCode = "";
                        int Verified = 0;
                        int CHS_ID = 0;
                        int ChAreaID = 0;
                        String Latitude = "";
                        String Longitude = "";
                        String Location = "";
                        String MigrateInDate = "";
                        String MigrateOutDate = "";
                        String Hamlet = "";
                        // add jitendra
                        int Anypremature_Death = 0;
                        int Anypremature_DeathYes = 0;
                        int Toilet = 0;
                        int Waste_Disposal = 0;
                        String Drinking_Water = "";
                        int Electricity = 0;
                        String Cooking_Fuel = "";
                        int House_Type = 0;
                        int ReligionID = 0;
                        int IsActive = 0;
                        HHCode = SurveyData.getString("HHCode");
                        HHSurveyGUID = SurveyData.getString("HHSurveyGUID");
                        MigrateInDate = SurveyData.getString("MigrateInDate");
                        MigrateOutDate = SurveyData.getString("MigrateOutDate");
                        CHS_ID = Validate.returnIntegerValue(SurveyData.getString("CHS_ID"));

                        if (SurveyData.getString("ChAreaID") != null
                                && SurveyData.getString("ChAreaID").length() > 0
                                && !SurveyData.getString("ChAreaID")
                                .equalsIgnoreCase("null")) {
                            ChAreaID = Integer.valueOf(SurveyData
                                    .getInt("ChAreaID"));
                        }

                        if (SurveyData.getString("SubCenterID") != null
                                && SurveyData.getString("SubCenterID").length() > 0
                                && !SurveyData.getString("SubCenterID")
                                .equalsIgnoreCase("null")) {
                            SubCenterID = Integer.valueOf(SurveyData
                                    .getInt("SubCenterID"));
                        }

                        if (SurveyData.getString("ANMID") != null
                                && SurveyData.getString("ANMID").length() > 0
                                && !SurveyData.getString("ANMID").equalsIgnoreCase(
                                "null")) {
                            ANMID = Integer.valueOf(SurveyData.getInt("ANMID"));
                        }

                        if (SurveyData.getString("VillageID") != null
                                && SurveyData.getString("VillageID").length() > 0
                                && !SurveyData.getString("VillageID")
                                .equalsIgnoreCase("null")) {
                            VillageID = Integer.valueOf(SurveyData
                                    .getInt("VillageID"));
                        }

                        if (SurveyData.getString("ServiceProviderID") != null
                                && SurveyData.getString("ServiceProviderID")
                                .length() > 0
                                && !SurveyData.getString("ServiceProviderID")
                                .equalsIgnoreCase("null")) {
                            ASHAID = Integer.valueOf(SurveyData
                                    .getInt("ServiceProviderID"));
                        }
                        if (SurveyData.getString("FamilyCode") != null
                                && SurveyData.getString("FamilyCode").length() > 0
                                && !SurveyData.getString("FamilyCode")
                                .equalsIgnoreCase("null")) {
                            FamilyCode = Integer.valueOf(SurveyData
                                    .getInt("FamilyCode"));
                        }

                        if (SurveyData.getString("CasteID") != null
                                && SurveyData.getString("CasteID").length() > 0
                                && !SurveyData.getString("CasteID")
                                .equalsIgnoreCase("null")) {
                            CasteID = Integer.valueOf(SurveyData.getInt("CasteID"));
                        }

                        if (SurveyData.getString("FinancialStatusID") != null
                                && SurveyData.getString("FinancialStatusID")
                                .length() > 0
                                && !SurveyData.getString("FinancialStatusID")
                                .equalsIgnoreCase("null")) {
                            FinancialStatusID = Integer.valueOf(SurveyData
                                    .getInt("FinancialStatusID"));
                        }

                        if (SurveyData.getString("CreatedBy") != null
                                && SurveyData.getString("CreatedBy").length() > 0
                                && !SurveyData.getString("CreatedBy")
                                .equalsIgnoreCase("null")) {
                            CreatedBy = Integer.valueOf(SurveyData
                                    .getInt("CreatedBy"));
                        }
                        CreatedOn = SurveyData.getString("CreatedOn");

                        if (SurveyData.getString("UploadedBy") != null
                                && SurveyData.getString("UploadedBy").length() > 0
                                && !SurveyData.getString("UploadedBy")
                                .equalsIgnoreCase("null")) {
                            UploadedBy = Integer.valueOf(SurveyData
                                    .getInt("UploadedBy"));
                        }
                        UploadedOn = SurveyData.getString("UploadedOn");
                        if (SurveyData.getString("HHStatusID") != null
                                && SurveyData.getString("HHStatusID").length() > 0
                                && !SurveyData.getString("HHStatusID")
                                .equalsIgnoreCase("null")) {
                            HHStatusID = Integer.valueOf(SurveyData
                                    .getInt("HHStatusID"));
                        }
                        if (SurveyData.getString("Verified") != null
                                && SurveyData.getString("Verified").length() > 0
                                && !SurveyData.getString("Verified")
                                .equalsIgnoreCase("null")) {
                            Verified = Integer.valueOf(SurveyData
                                    .getInt("Verified"));
                        }
                        if (SurveyData.getString("Verified") != null
                                && SurveyData.getString("Verified").length() > 0
                                && !SurveyData.getString("Verified")
                                .equalsIgnoreCase("null")) {
                            Verified = Integer.valueOf(SurveyData
                                    .getInt("Verified"));
                        }
                        if (SurveyData.getString("IsDeleted") != null
                                && SurveyData.getString("IsDeleted").length() > 0
                                && !SurveyData.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(SurveyData
                                    .getInt("IsDeleted"));
                        }
                        if (SurveyData.getString("IsTablet") != null
                                && SurveyData.getString("IsTablet").length() > 0
                                && !SurveyData.getString("IsTablet")
                                .equalsIgnoreCase("null")) {
                            IsTablet = Integer.valueOf(SurveyData
                                    .getInt("IsTablet"));
                        }
                        if (SurveyData.getString("Hamlet") != null
                                && SurveyData.getString("Hamlet").length() > 0
                                && !SurveyData.getString("Hamlet")
                                .equalsIgnoreCase("null")) {
                            Hamlet = SurveyData.getString("Hamlet");
                        }
                        if (SurveyData.getString("Anypremature_Death") != null
                                && SurveyData.getString("Anypremature_Death")
                                .length() > 0
                                && !SurveyData.getString("Anypremature_Death")
                                .equalsIgnoreCase("null")) {
                            Anypremature_Death = Integer.valueOf(SurveyData
                                    .getInt("Anypremature_Death"));
                        }
                        if (SurveyData.getString("Anypremature_DeathYes") != null
                                && SurveyData.getString("Anypremature_DeathYes")
                                .length() > 0
                                && !SurveyData.getString("Anypremature_DeathYes")
                                .equalsIgnoreCase("null")) {
                            Anypremature_DeathYes = Integer.valueOf(SurveyData
                                    .getInt("Anypremature_DeathYes"));
                        }
                        if (SurveyData.getString("Toilet") != null
                                && SurveyData.getString("Toilet").length() > 0
                                && !SurveyData.getString("Toilet")
                                .equalsIgnoreCase("null")) {
                            Toilet = Integer.valueOf(SurveyData.getInt("Toilet"));
                        }
                        if (SurveyData.getString("Waste_Disposal") != null
                                && SurveyData.getString("Waste_Disposal").length() > 0
                                && !SurveyData.getString("Waste_Disposal")
                                .equalsIgnoreCase("null")) {
                            Waste_Disposal = Integer.valueOf(SurveyData
                                    .getInt("Waste_Disposal"));
                        }
                        if (SurveyData.getString("Drinking_Water") != null
                                && SurveyData.getString("Drinking_Water").length() > 0
                                && !SurveyData.getString("Drinking_Water")
                                .equalsIgnoreCase("null")) {
                            Drinking_Water = SurveyData.getString("Drinking_Water");
                        }
                        if (SurveyData.getString("Electricity") != null
                                && SurveyData.getString("Electricity").length() > 0
                                && !SurveyData.getString("Electricity")
                                .equalsIgnoreCase("null")) {
                            Electricity = Integer.valueOf(SurveyData
                                    .getInt("Electricity"));
                        }
                        if (SurveyData.getString("House_Type") != null
                                && SurveyData.getString("House_Type").length() > 0
                                && !SurveyData.getString("House_Type")
                                .equalsIgnoreCase("null")) {
                            House_Type = Integer.valueOf(SurveyData
                                    .getInt("House_Type"));
                        }
                        if (SurveyData.getString("ReligionID") != null
                                && SurveyData.getString("ReligionID").length() > 0
                                && !SurveyData.getString("ReligionID")
                                .equalsIgnoreCase("null")) {
                            ReligionID = Integer.valueOf(SurveyData
                                    .getInt("ReligionID"));
                        }
                        if (SurveyData.getString("IsDeleted") != null
                                && SurveyData.getString("IsDeleted").length() > 0
                                && !SurveyData.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(SurveyData
                                    .getInt("IsDeleted"));
                        }
                        if (SurveyData.getString("IsActive") != null
                                && SurveyData.getString("IsActive").length() > 0
                                && !SurveyData.getString("IsActive")
                                .equalsIgnoreCase("null")) {
                            IsActive = Integer.valueOf(SurveyData
                                    .getInt("IsActive"));
                        }
                        Cooking_Fuel = SurveyData.getString("Cooking_Fuel");

                        String sqlcount = "select count(*) from Tbl_HHSurvey where  HHSurveyGUID='"
                                + HHSurveyGUID + "' ";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from Tbl_HHSurvey where HHSurveyGUID='"
                                + HHSurveyGUID + "' and  IsEdited= 1";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);
                        String sql = "";
                        if (IsDeleted == 1 && count1 == 0) {
                            String sqldelete = "delete from Tbl_HHSurvey where HHSurveyGUID='"
                                    + HHSurveyGUID + "' ";
                            dataProvider.executeSql(sqldelete);

                            String sqldelete1 = "delete from Tbl_HHFamilyMember where HHSurveyGUID='"
                                    + HHSurveyGUID + "' ";
                            dataProvider.executeSql(sqldelete1);

                        } else {
                            if (count == 0) {

                                sql = "Insert into Tbl_HHSurvey(Latitude,Longitude,Location,MigrateOutDate,MigrateInDate,ChAreaID,CHS_ID,HHSurveyGUID,SubCenterID,ANMID,VillageID,ServiceProviderID,FamilyCode,CasteID,FinancialStatusID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,HHStatusID,HHCode,Verified,Hamlet,Anypremature_Death,Anypremature_DeathYes,Toilet,Waste_Disposal,Drinking_Water,Electricity,Cooking_Fuel,House_Type,ReligionID,IsActive)values('"
                                        + Latitude
                                        + "','"
                                        + Longitude
                                        + "','"
                                        + Location
                                        + "','"
                                        + MigrateOutDate
                                        + "','"
                                        + MigrateInDate
                                        + "',"
                                        + ChAreaID
                                        + ","
                                        + CHS_ID
                                        + ",'"
                                        + HHSurveyGUID
                                        + "',"
                                        + SubCenterID
                                        + ","
                                        + ANMID
                                        + ","
                                        + VillageID
                                        + ","
                                        + ASHAID
                                        + ","
                                        + FamilyCode
                                        + ","
                                        + CasteID
                                        + ","
                                        + FinancialStatusID
                                        + ","
                                        + CreatedBy
                                        + ",'"
                                        + CreatedOn
                                        + "',"
                                        + UploadedBy
                                        + ",'"
                                        + UploadedOn
                                        + "',"
                                        + IsTablet
                                        + ","
                                        + IsDeleted
                                        + ",0,0,"
                                        + HHStatusID
                                        + ",'"
                                        + HHCode
                                        + "',"
                                        + Verified
                                        + ",'"
                                        + Hamlet
                                        + "','"
                                        + Anypremature_Death
                                        + "','"
                                        + Anypremature_DeathYes
                                        + "','"
                                        + Toilet
                                        + "','"
                                        + Waste_Disposal
                                        + "','"
                                        + Drinking_Water
                                        + "','"
                                        + Electricity
                                        + "','"
                                        + Cooking_Fuel
                                        + "','"
                                        + House_Type
                                        + "','" + ReligionID + "'," + IsActive + ")";

                            } else {
                                if (count1 == 0) {
                                    sql = "update  Tbl_HHSurvey set Latitude='"
                                            + Latitude + "',Longitude='"
                                            + Longitude + "',Location='" + Location
                                            + "',ChAreaID=" + ChAreaID + ",CHS_ID="
                                            + CHS_ID + ",SubCenterID="
                                            + SubCenterID + ",ANMID=" + ANMID
                                            + ",VillageID=" + VillageID
                                            + ",ServiceProviderID=" + ASHAID
                                            + ",FamilyCode=" + FamilyCode
                                            + ",CasteID=" + CasteID
                                            + ",FinancialStatusID="
                                            + FinancialStatusID + ",CreatedBy="
                                            + CreatedBy + ",CreatedOn='"
                                            + CreatedOn + "',UploadedBy="
                                            + UploadedBy + ",UploadedOn='"
                                            + UploadedOn + "',IsTablet=" + IsTablet
                                            + ",IsDeleted=" + IsDeleted
                                            + ",IsEdited=0,MigrateInDate='"
                                            + MigrateInDate + "',MigrateOutDate='"
                                            + MigrateOutDate
                                            + "',IsUploaded=0,HHStatusID="
                                            + HHStatusID + ",HHCode='" + HHCode
                                            + "',Verified=" + Verified
                                            + ",Hamlet='" + Hamlet
                                            + "',Anypremature_Death='"
                                            + Anypremature_Death
                                            + "',Anypremature_DeathYes='"
                                            + Anypremature_DeathYes + "',Toilet='"
                                            + Toilet + "',Waste_Disposal='"
                                            + Waste_Disposal + "',Drinking_Water='"
                                            + Drinking_Water + "',Electricity='"
                                            + Electricity + "',Cooking_Fuel='"
                                            + Cooking_Fuel + "',House_Type='"
                                            + House_Type + "',ReligionID='"
                                            + ReligionID + "',IsActive="
                                            + IsActive + " where HHSurveyGUID='"
                                            + HHSurveyGUID + "'";
                                }

                            }
                            try {


                                dataProvider.executeSql(sql);

                            } catch (Exception e) {

                                // TODO: handle exception

                                downloadmsg = e.getMessage();
                            }
                        }
                    }

                    HHSurveyFamilyData = jsonObj.getJSONArray("tblhhfamilymember");
                    for (int i = 0; i < HHSurveyFamilyData.length(); i++) {
                        JSONObject SurveyFamilyData = HHSurveyFamilyData
                                .getJSONObject(i);
                        String HHFamilyMemberGUID = "";
                        String HHSurveyGUID = "";
                        String HHFamilyMemberCode = "";
                        String UniqueIDNumber = "";
                        int StatusID = 0;
                        int RelationID = 0;
                        int GenderID = 0;
                        int MaritialStatusID = 0;
                        int DOBAvailable = 0;
                        String DateOfBirth = "";
                        String AgeAsOnYear = "";
                        int AprilAgeYear = 0;
                        int AprilAgeMonth = 0;
                        String MotherGUID = "";
                        int TargetID = 0;
                        int CreatedBy = 0;
                        String CreatedOn = "";
                        int UploadedBy = 0;
                        String UploadedOn = "";
                        int IsTablet = 0;
                        int IsDeleted = 0;
                        int AshaID = 0;
                        int ANMID = 0;
                        String DateOfDeath = "";
                        int PlaceOfDeath = 0;
                        int DeathVillage = 0;
                        String FamilyMemberName = "";
                        String NameofDeathplace = "";
                        int Education = 0;
                        // add jitendra
                        String Registration_Date = "";
                        int Occupation = 0;
                        int Rsby_Beneficiary = 0;
                        int Health_Condition = 0;
                        String Any_HealthIssue = "";
                        int Any_PhysicalInability = 0;
                        String Occupation_Other = "";
                        String Phone_No = "";
                        String Any_HealthIssue_Other = "";
                        String Father = "";
                        String Mother = "";
                        String Spouse = "";
                        String TempMember = "";
                        int other_Id_Type = 0;
                        String other_Id_Name = "";
                        String other_Id = "";
                        HHFamilyMemberGUID = SurveyFamilyData
                                .getString("HHFamilyMemberGUID");
                        NameofDeathplace = SurveyFamilyData
                                .getString("NameofDeathplace");
                        HHSurveyGUID = SurveyFamilyData.getString("HHSurveyGUID");
                        HHFamilyMemberCode = SurveyFamilyData
                                .getString("HHFamilyMemberCode");
                        UniqueIDNumber = SurveyFamilyData
                                .getString("UniqueIDNumber");
                        if (SurveyFamilyData.getString("StatusID") != null
                                && SurveyFamilyData.getString("StatusID").length() > 0
                                && !SurveyFamilyData.getString("StatusID")
                                .equalsIgnoreCase("null")) {
                            StatusID = Integer.valueOf(SurveyFamilyData
                                    .getInt("StatusID"));
                        }
                        if (SurveyFamilyData.getString("RelationID") != null
                                && SurveyFamilyData.getString("RelationID")
                                .length() > 0
                                && !SurveyFamilyData.getString("RelationID")
                                .equalsIgnoreCase("null")) {
                            RelationID = Integer.valueOf(SurveyFamilyData
                                    .getInt("RelationID"));
                        }
                        if (SurveyFamilyData.getString("GenderID") != null
                                && SurveyFamilyData.getString("GenderID").length() > 0
                                && !SurveyFamilyData.getString("GenderID")
                                .equalsIgnoreCase("null")) {
                            GenderID = Integer.valueOf(SurveyFamilyData
                                    .getInt("GenderID"));
                        }
                        if (SurveyFamilyData.getString("MaritialStatusID") != null
                                && SurveyFamilyData.getString("MaritialStatusID")
                                .length() > 0
                                && !SurveyFamilyData.getString("MaritialStatusID")
                                .equalsIgnoreCase("null")) {
                            MaritialStatusID = Integer.valueOf(SurveyFamilyData
                                    .getInt("MaritialStatusID"));
                        }
                        if (SurveyFamilyData.getString("DOBAvailable") != null
                                && SurveyFamilyData.getString("DOBAvailable")
                                .length() > 0
                                && !SurveyFamilyData.getString("DOBAvailable")
                                .equalsIgnoreCase("null")) {
                            DOBAvailable = Integer.valueOf(SurveyFamilyData
                                    .getInt("DOBAvailable"));
                        }
                        if (SurveyFamilyData.getString("DateOfBirth") != null
                                && SurveyFamilyData.getString("DateOfBirth")
                                .length() > 0
                                && !SurveyFamilyData.getString("DateOfBirth")
                                .equalsIgnoreCase("null")) {

                            DateOfBirth = SurveyFamilyData.getString("DateOfBirth");
                        }
                        AgeAsOnYear = SurveyFamilyData.getString("AgeAsOnYear");
                        if (SurveyFamilyData.getString("AprilAgeYear") != null
                                && SurveyFamilyData.getString("AprilAgeYear")
                                .length() > 0
                                && !SurveyFamilyData.getString("AprilAgeYear")
                                .equalsIgnoreCase("null")) {
                            AprilAgeYear = Integer.valueOf(SurveyFamilyData
                                    .getInt("AprilAgeYear"));
                        }
                        if (SurveyFamilyData.getString("AprilAgeMonth") != null
                                && SurveyFamilyData.getString("AprilAgeMonth")
                                .length() > 0
                                && !SurveyFamilyData.getString("AprilAgeMonth")
                                .equalsIgnoreCase("null")) {
                            AprilAgeMonth = Integer.valueOf(SurveyFamilyData
                                    .getInt("AprilAgeMonth"));
                        }
                        MotherGUID = SurveyFamilyData.getString("MotherGUID");

                        if (SurveyFamilyData.getString("TargetID") != null
                                && SurveyFamilyData.getString("TargetID").length() > 0
                                && !SurveyFamilyData.getString("TargetID")
                                .equalsIgnoreCase("null")) {
                            TargetID = Integer.valueOf(SurveyFamilyData
                                    .getInt("TargetID"));
                        }
                        if (SurveyFamilyData.getString("CreatedBy") != null
                                && SurveyFamilyData.getString("CreatedBy").length() > 0
                                && !SurveyFamilyData.getString("CreatedBy")
                                .equalsIgnoreCase("null")) {
                            CreatedBy = Integer.valueOf(SurveyFamilyData
                                    .getInt("CreatedBy"));
                        }
                        CreatedOn = SurveyFamilyData.getString("CreatedOn");
                        if (SurveyFamilyData.getString("UploadedBy") != null
                                && SurveyFamilyData.getString("UploadedBy")
                                .length() > 0
                                && !SurveyFamilyData.getString("UploadedBy")
                                .equalsIgnoreCase("null")) {
                            UploadedBy = Integer.valueOf(SurveyFamilyData
                                    .getInt("UploadedBy"));
                        }
                        UploadedOn = SurveyFamilyData.getString("UploadedOn");
                        if (SurveyFamilyData.getString("IsTablet") != null
                                && SurveyFamilyData.getString("IsTablet").length() > 0
                                && !SurveyFamilyData.getString("IsTablet")
                                .equalsIgnoreCase("null")) {
                            IsTablet = Integer.valueOf(SurveyFamilyData
                                    .getInt("IsTablet"));
                        }
                        if (SurveyFamilyData.getString("IsDeleted") != null
                                && SurveyFamilyData.getString("IsDeleted").length() > 0
                                && !SurveyFamilyData.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(SurveyFamilyData
                                    .getInt("IsDeleted"));
                        }
                        if (SurveyFamilyData.getString("IsDeleted") != null
                                && SurveyFamilyData.getString("IsDeleted").length() > 0
                                && !SurveyFamilyData.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(SurveyFamilyData
                                    .getInt("IsDeleted"));
                        }
                        if (SurveyFamilyData.getString("AshaID") != null
                                && SurveyFamilyData.getString("AshaID").length() > 0
                                && !SurveyFamilyData.getString("AshaID")
                                .equalsIgnoreCase("null")) {
                            AshaID = Integer.valueOf(SurveyFamilyData
                                    .getInt("AshaID"));
                        }
                        if (SurveyFamilyData.getString("ANMID") != null
                                && SurveyFamilyData.getString("ANMID").length() > 0
                                && !SurveyFamilyData.getString("ANMID")
                                .equalsIgnoreCase("null")) {
                            ANMID = Integer.valueOf(SurveyFamilyData
                                    .getInt("ANMID"));
                        }
                        if (SurveyFamilyData.getString("Education") != null
                                && SurveyFamilyData.getString("Education").length() > 0
                                && !SurveyFamilyData.getString("Education")
                                .equalsIgnoreCase("null")) {
                            Education = Integer.valueOf(SurveyFamilyData
                                    .getInt("Education"));
                        }
                        if (SurveyFamilyData.getString("DeathVillage") != null
                                && SurveyFamilyData.getString("DeathVillage")
                                .length() > 0
                                && !SurveyFamilyData.getString("DeathVillage")
                                .equalsIgnoreCase("null")) {
                            DeathVillage = Integer.valueOf(SurveyFamilyData
                                    .getInt("DeathVillage"));
                        }
                        FamilyMemberName = SurveyFamilyData
                                .getString("FamilyMemberName");

                        // add jitendra
                        Father = SurveyFamilyData.getString("Father");
                        Mother = SurveyFamilyData.getString("Mother");
                        Spouse = SurveyFamilyData.getString("Spouse");
                        TempMember = SurveyFamilyData.getString("TempMember");
                        Registration_Date = SurveyFamilyData
                                .getString("Registration_Date");
                        if (SurveyFamilyData.getString("Occupation") != null
                                && SurveyFamilyData.getString("Occupation")
                                .length() > 0
                                && !SurveyFamilyData.getString("Occupation")
                                .equalsIgnoreCase("null")) {
                            Occupation = Integer.valueOf(SurveyFamilyData
                                    .getInt("Occupation"));
                        }
                        if (SurveyFamilyData.getString("Rsby_Beneficiary") != null
                                && SurveyFamilyData.getString("Rsby_Beneficiary")
                                .length() > 0
                                && !SurveyFamilyData.getString("Rsby_Beneficiary")
                                .equalsIgnoreCase("null")) {
                            Rsby_Beneficiary = Integer.valueOf(SurveyFamilyData
                                    .getInt("Rsby_Beneficiary"));
                        }
                        if (SurveyFamilyData.getString("Any_HealthIssue") != null
                                && SurveyFamilyData.getString("Any_HealthIssue")
                                .length() > 0
                                && !SurveyFamilyData.getString("Any_HealthIssue")
                                .equalsIgnoreCase("null")) {
                            Any_HealthIssue = SurveyFamilyData
                                    .getString("Any_HealthIssue");
                        }
                        if (SurveyFamilyData.getString("Any_PhysicalInability") != null
                                && SurveyFamilyData.getString(
                                "Any_PhysicalInability").length() > 0
                                && !SurveyFamilyData.getString(
                                "Any_PhysicalInability").equalsIgnoreCase(
                                "null")) {
                            Any_PhysicalInability = Integer
                                    .valueOf(SurveyFamilyData
                                            .getInt("Any_PhysicalInability"));
                        }
                        if (SurveyFamilyData.getString("DeathVillage") != null
                                && SurveyFamilyData.getString("DeathVillage")
                                .length() > 0
                                && !SurveyFamilyData.getString("DeathVillage")
                                .equalsIgnoreCase("null")) {
                            DeathVillage = Integer.valueOf(SurveyFamilyData
                                    .getInt("DeathVillage"));
                        }
                        if (SurveyFamilyData.getString("Health_Condition") != null
                                && SurveyFamilyData.getString("Health_Condition")
                                .length() > 0
                                && !SurveyFamilyData.getString("Health_Condition")
                                .equalsIgnoreCase("null")) {
                            Health_Condition = Integer.valueOf(SurveyFamilyData
                                    .getInt("Health_Condition"));
                        }
                        if (SurveyFamilyData.getString("Other_Id_Type") != null
                                && SurveyFamilyData.getString("Other_Id_Type")
                                .length() > 0
                                && !SurveyFamilyData.getString("Other_Id_Type")
                                .equalsIgnoreCase("null")) {
                            other_Id_Type = Integer.valueOf(SurveyFamilyData
                                    .getInt("Other_Id_Type"));
                        }
                        if (SurveyFamilyData.getString("IsDeleted") != null
                                && SurveyFamilyData.getString("IsDeleted").length() > 0
                                && !SurveyFamilyData.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(SurveyFamilyData
                                    .getInt("IsDeleted"));
                        }

                        other_Id_Name = SurveyFamilyData.getString("Other_Id_Name");
                        other_Id = SurveyFamilyData.getString("Other_Id");
                        Occupation_Other = SurveyFamilyData
                                .getString("Occupation_Other");
                        Phone_No = SurveyFamilyData.getString("Phone_No");
                        DateOfDeath = SurveyFamilyData.getString("DateOfDeath");
                        Any_HealthIssue_Other = SurveyFamilyData
                                .getString("Any_HealthIssue_Other");
                        String sqlcount = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
                                + HHFamilyMemberGUID + "'";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
                                + HHFamilyMemberGUID + "' and IsEdited=1";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);
                        String sql = "";
                        if (IsDeleted == 1 && count1 == 0) {
                            String sqldelete1 = "delete from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
                                    + HHFamilyMemberGUID + "' ";
                            dataProvider.executeSql(sqldelete1);

                        } else {
                            if (count == 0) {

                                sql = "Insert into Tbl_HHFamilyMember(HHFamilyMemberGUID,HHSurveyGUID,Education,AshaID,ANMID,HHFamilyMemberCode,UniqueIDNumber,StatusID,RelationID,GenderID,MaritialStatusID,DOBAvailable,DateOfBirth,AgeAsOnYear,AprilAgeYear,AprilAgeMonth,MotherGUID,TargetID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,FamilyMemberName,DateOfDeath,PlaceOfDeath,NameofDeathplace,DeathVillage,Registration_Date,Occupation,Rsby_Beneficiary,Health_Condition,Any_HealthIssue,Any_PhysicalInability,Occupation_Other,Phone_No,Any_HealthIssue_Other,Father,Mother,Spouse,TempMember,Other_Id_Type,Other_Id_Name,Other_Id)values('"
                                        + HHFamilyMemberGUID
                                        + "','"
                                        + HHSurveyGUID
                                        + "',"
                                        + Education
                                        + ","
                                        + AshaID
                                        + ","
                                        + ANMID
                                        + ",'"
                                        + HHFamilyMemberCode
                                        + "','"
                                        + UniqueIDNumber
                                        + "',"
                                        + StatusID
                                        + ","
                                        + RelationID
                                        + ","
                                        + GenderID
                                        + ","
                                        + MaritialStatusID
                                        + ","
                                        + DOBAvailable
                                        + ",'"
                                        + DateOfBirth
                                        + "','"
                                        + AgeAsOnYear
                                        + "',"
                                        + AprilAgeYear
                                        + ","
                                        + AprilAgeMonth
                                        + ",'"
                                        + MotherGUID
                                        + "',"
                                        + TargetID
                                        + ","
                                        + CreatedBy
                                        + ",'"
                                        + CreatedOn
                                        + "',"
                                        + UploadedBy
                                        + ",'"
                                        + UploadedOn
                                        + "',"
                                        + IsTablet
                                        + ","
                                        + IsDeleted
                                        + ",0,0,'"
                                        + FamilyMemberName
                                        + "','"
                                        + DateOfDeath
                                        + "',"
                                        + PlaceOfDeath
                                        + ",'"
                                        + NameofDeathplace
                                        + "',"
                                        + DeathVillage
                                        + ",'"
                                        + Registration_Date
                                        + "','"
                                        + Occupation
                                        + "','"
                                        + Rsby_Beneficiary
                                        + "','"
                                        + Health_Condition
                                        + "','"
                                        + Any_HealthIssue
                                        + "','"
                                        + Any_PhysicalInability
                                        + "','"
                                        + Occupation_Other
                                        + "','"
                                        + Phone_No
                                        + "','"
                                        + Any_HealthIssue_Other
                                        + "','"
                                        + Father
                                        + "','"
                                        + Mother
                                        + "','"
                                        + Spouse
                                        + "','"
                                        + TempMember
                                        + "','"
                                        + other_Id_Type
                                        + "','"
                                        + other_Id_Name
                                        + "','" + other_Id + "')";

                            } else {
                                if (count1 == 0) {
                                    sql = "update Tbl_HHFamilyMember set Education="
                                            + Education
                                            + ", HHFamilyMemberCode='"
                                            + HHFamilyMemberCode
                                            + "',UniqueIDNumber='"
                                            + UniqueIDNumber
                                            + "',StatusID="
                                            + StatusID
                                            + ",RelationID="
                                            + RelationID
                                            + ",GenderID="
                                            + GenderID
                                            + ",MaritialStatusID="
                                            + MaritialStatusID
                                            + ",DOBAvailable="
                                            + DOBAvailable
                                            + ",DateOfBirth='"
                                            + DateOfBirth
                                            + "',AgeAsOnYear='"
                                            + AgeAsOnYear
                                            + "',AprilAgeYear="
                                            + AprilAgeYear
                                            + ",AprilAgeMonth="
                                            + AprilAgeMonth
                                            + ",MotherGUID='"
                                            + MotherGUID
                                            + "',TargetID="
                                            + TargetID
                                            + ",CreatedBy="
                                            + CreatedBy
                                            + ",CreatedOn='"
                                            + CreatedOn
                                            + "',UploadedBy="
                                            + UploadedBy
                                            + ",UploadedOn='"
                                            + UploadedOn
                                            + "',IsTablet="
                                            + IsTablet
                                            + ",IsDeleted="
                                            + IsDeleted
                                            + ",IsEdited=0,IsUploaded=0,FamilyMemberName='"
                                            + FamilyMemberName
                                            + "',AshaID="
                                            + AshaID
                                            + ",ANMID="
                                            + ANMID
                                            + ",DeathVillage="
                                            + DeathVillage
                                            + ",NameofDeathplace='"
                                            + NameofDeathplace
                                            + "',DateOfDeath='"
                                            + DateOfDeath
                                            + "',PlaceOfDeath="
                                            + PlaceOfDeath
                                            + ",Registration_Date='"
                                            + Registration_Date
                                            + "',Occupation='"
                                            + Occupation
                                            + "',Rsby_Beneficiary='"
                                            + Rsby_Beneficiary
                                            + "',Health_Condition='"
                                            + Health_Condition
                                            + "',Any_HealthIssue='"
                                            + Any_HealthIssue
                                            + "',Any_PhysicalInability='"
                                            + Any_PhysicalInability
                                            + "',Occupation_Other='"
                                            + Occupation_Other
                                            + "',Phone_No='"
                                            + Phone_No
                                            + "',Any_HealthIssue_Other='"
                                            + Any_HealthIssue_Other
                                            + "',Father='"
                                            + Father
                                            + "',Mother='"
                                            + Mother
                                            + "',Spouse='"
                                            + Spouse
                                            + "',TempMember='"
                                            + TempMember
                                            + "',Other_Id_Type='"
                                            + other_Id_Type
                                            + "' ,Other_Id_Name='"
                                            + other_Id_Name
                                            + "' ,Other_Id='"
                                            + other_Id
                                            + "' where HHFamilyMemberGUID='"
                                            + HHFamilyMemberGUID
                                            + "' and HHSurveyGUID='"
                                            + HHSurveyGUID
                                            + "'";

                                }
                            }
                            try {

                                dataProvider.executeSql(sql);

                            } catch (Exception e) {
                                // TODO: handle exception


                                downloadmsg = e.getMessage();
                            }
                        }
                    }


                    tblpregnantwomenArray = jsonObj
                            .getJSONArray("tblpregnant_woman");
                    for (int i = 0; i < tblpregnantwomenArray.length(); i++) {
                        JSONObject pregnantwomen = tblpregnantwomenArray
                                .getJSONObject(i);
                        String AltMobileNo = "";
                        String PWGUID = "";
                        String HHGUID = "";
                        String HHFamilyMemberGUID = "";
                        String PWName = "";
                        int ANMID = 0;
                        String PWImage = "";
                        int AshaID = 0;
                        String LMPDate = "";
                        String EDDDate = "";
                        String PWRegistrationDate = "";
                        int Regwithin12weeks = 0;
                        int RegweeksElaspsed = 0;
                        String HusbandName = "";
                        String Husband_GUID = "";
                        String MobileNo = "";
                        String MotherMCTSID = "";
                        String IFSCCode = "";
                        String Accountno = "";
                        int JSYBenificiaryYN = 0;
                        String JSYRegDate = "";
                        int JSYPaymentReceivedYN = 0;
                        String PWDOB = "";
                        int PWAgeYears = 0;
                        String PWAgeRefDate = "";
                        double PWWeight = 0;
                        int PWBloodGroup = 0;
                        String PastIllnessYN = "";
                        int TotalPregnancy = 0;
                        int LastPregnancyResult = 0;
                        int LastPregnancyComplication = 0;
                        int LTLPregnancyResult = 0;
                        int LTLPregnancyomplication = 0;
                        int PWHeight = 0;
                        int LastPregDeliveryPlace = 0;
                        int LasttolastPregDeliveryPlace = 0;
                        String ExpFacilityforDelivery = "";
                        String ExpFacilityforDeliveryName = "";
                        int VDRLTestYN = 0;
                        String VDRLResult = "";
                        int HIVTestYN = 0;
                        String HIVResult = "";
                        String Visit1Date = "";
                        String Visit2Date = "";
                        String Visit3Date = "";
                        String Visit4Date = "";
                        int ISAbortion = 0;
                        int IsPregnant = 0;
                        int AbortionFacilityType = 0;
                        int NoofANCVisitsDone = 0;
                        String LastANCVisitDate = "";
                        String TT1Date = "";
                        String TT2Date = "";
                        String TTBoosterDate = "";
                        String DangerSigns = "";
                        int RefferedYN = 0;
                        String DeliveryDateTime = "";
                        int DeliveryPlace = 0;
                        String DeliveryConductedBy = "";
                        int DeliveryType = 0;
                        String DeliveryComplication = "";
                        int MotherDeathCause = 0;
                        int DeliveryOutcome = 0;
                        String DTMFacilityDischarge = "";
                        String PaymentRecieved = "";
                        int PlaceofDeath = 0;
                        String DateofDeath = "";
                        String OtherPlaceofDeath = "";
                        String CreatedOn = "";
                        int CreatedBy = 0;
                        String UpdatedOn = "";
                        int UpdatedBy = 0;
                        int Education = 0;
                        int Abortion_FacilityName = 0;

                        String AnyOtherPastIllness = "";
                        String AnyOtherLastPregCom = "";
                        String AnyOtherLTLPregCom = "";
                        int HighRisk = 0;
                        int MaternalDeath = 0;
                        int BankAcc = 0;
                        int ChildDeathCause = 0;
                        String MotherDCOther = "";
                        String DeathPlaceOther = "";
                        int FacitylastPreg = 0;
                        String FaciltyOtherLTL = "";
                        int FacityLTL = 0;
                        String FacilityOtherLastpreg = "";
                        int IsDeleted = 0;

                        AnyOtherPastIllness = pregnantwomen
                                .getString("AnyOtherPastIllness");
                        AnyOtherLastPregCom = pregnantwomen
                                .getString("AnyOtherLastPregCom");
                        AnyOtherLTLPregCom = pregnantwomen
                                .getString("AnyOtherLTLPregCom");
                        if (pregnantwomen.getString("HighRisk") != null
                                && pregnantwomen.getString("HighRisk").length() > 0
                                && !pregnantwomen.getString("HighRisk")
                                .equalsIgnoreCase("null")) {
                            HighRisk = Integer.valueOf(pregnantwomen
                                    .getInt("HighRisk"));
                        }
                        if (pregnantwomen.getString("MaternalDeath") != null
                                && pregnantwomen.getString("MaternalDeath")
                                .length() > 0
                                && !pregnantwomen.getString("MaternalDeath")
                                .equalsIgnoreCase("null")) {
                            MaternalDeath = Integer.valueOf(pregnantwomen
                                    .getInt("MaternalDeath"));
                        }
                        if (pregnantwomen.getString("BankAcc") != null
                                && pregnantwomen.getString("BankAcc").length() > 0
                                && !pregnantwomen.getString("BankAcc")
                                .equalsIgnoreCase("null")) {
                            BankAcc = Integer.valueOf(pregnantwomen
                                    .getInt("BankAcc"));
                        }
                        if (pregnantwomen.getString("ChildDeathCause") != null
                                && pregnantwomen.getString("ChildDeathCause")
                                .length() > 0
                                && !pregnantwomen.getString("ChildDeathCause")
                                .equalsIgnoreCase("null")) {
                            ChildDeathCause = Integer.valueOf(pregnantwomen
                                    .getInt("ChildDeathCause"));
                        }
                        MotherDCOther = pregnantwomen.getString("MotherDCOther");
                        DeathPlaceOther = pregnantwomen
                                .getString("DeathPlaceOther");
                        if (pregnantwomen.getString("FacitylastPreg") != null
                                && pregnantwomen.getString("FacitylastPreg")
                                .length() > 0
                                && !pregnantwomen.getString("FacitylastPreg")
                                .equalsIgnoreCase("null")) {
                            FacitylastPreg = Integer.valueOf(pregnantwomen
                                    .getInt("FacitylastPreg"));
                        }
                        FaciltyOtherLTL = pregnantwomen
                                .getString("FaciltyOtherLTL");
                        if (pregnantwomen.getString("FacityLTL") != null
                                && pregnantwomen.getString("FacityLTL").length() > 0
                                && !pregnantwomen.getString("FacityLTL")
                                .equalsIgnoreCase("null")) {
                            FacityLTL = Integer.valueOf(pregnantwomen
                                    .getInt("FacityLTL"));
                        }
                        FacilityOtherLastpreg = pregnantwomen
                                .getString("FacilityOtherLastpreg");

                        PWGUID = pregnantwomen.getString("PWGUID");
                        AltMobileNo = pregnantwomen.getString("AltMobileNo");
                        HHGUID = pregnantwomen.getString("HHGUID");
                        HHFamilyMemberGUID = pregnantwomen
                                .getString("HHFamilyMemberGUID");
                        PWName = pregnantwomen.getString("PWName");
                        PWImage = pregnantwomen.getString("PWImage");
                        LMPDate = pregnantwomen.getString("LMPDate");
                        EDDDate = pregnantwomen.getString("EDDDate");
                        PWRegistrationDate = pregnantwomen
                                .getString("PWRegistrationDate");
                        HusbandName = pregnantwomen.getString("HusbandName");
                        Husband_GUID = pregnantwomen.getString("Husband_GUID");
                        MobileNo = pregnantwomen.getString("MobileNo");
                        MotherMCTSID = pregnantwomen.getString("MotherMCTSID");
                        IFSCCode = pregnantwomen.getString("IFSCCode");
                        Accountno = pregnantwomen.getString("Accountno");
                        JSYRegDate = pregnantwomen.getString("JSYRegDate");
                        PWDOB = pregnantwomen.getString("PWDOB");
                        PWAgeRefDate = pregnantwomen.getString("PWAgeRefDate");
                        ExpFacilityforDelivery = pregnantwomen
                                .getString("ExpFacilityforDelivery");
                        ExpFacilityforDeliveryName = pregnantwomen
                                .getString("ExpFacilityforDeliveryName");
                        VDRLResult = pregnantwomen.getString("VDRLResult");
                        HIVResult = pregnantwomen.getString("HIVResult");
                        Visit1Date = pregnantwomen.getString("Visit1Date");
                        Visit2Date = pregnantwomen.getString("Visit2Date");
                        Visit3Date = pregnantwomen.getString("Visit3Date");
                        Visit4Date = pregnantwomen.getString("Visit4Date");
                        LastANCVisitDate = pregnantwomen
                                .getString("LastANCVisitDate");
                        TT1Date = pregnantwomen.getString("TT1Date");
                        TT2Date = pregnantwomen.getString("TT2Date");

                        TTBoosterDate = pregnantwomen.getString("TTBoosterDate");
                        DangerSigns = pregnantwomen.getString("DangerSigns");
                        DeliveryDateTime = pregnantwomen
                                .getString("DeliveryDateTime");
                        DeliveryConductedBy = pregnantwomen
                                .getString("DeliveryConductedBy");
                        DeliveryComplication = pregnantwomen
                                .getString("DeliveryComplication");
                        DTMFacilityDischarge = pregnantwomen
                                .getString("DTMFacilityDischarge");
                        PaymentRecieved = pregnantwomen
                                .getString("PaymentRecieved");
                        DateofDeath = pregnantwomen.getString("DateofDeath");
                        OtherPlaceofDeath = pregnantwomen
                                .getString("OtherPlaceofDeath");
                        CreatedOn = pregnantwomen.getString("CreatedOn");
                        UpdatedOn = pregnantwomen.getString("UpdatedOn");

                        if (pregnantwomen.getString("ANMID") != null
                                && pregnantwomen.getString("ANMID").length() > 0
                                && !pregnantwomen.getString("ANMID")
                                .equalsIgnoreCase("null")) {
                            ANMID = Integer.valueOf(pregnantwomen.getInt("ANMID"));
                        }
                        if (pregnantwomen.getString("AshaID") != null
                                && pregnantwomen.getString("AshaID").length() > 0
                                && !pregnantwomen.getString("AshaID")
                                .equalsIgnoreCase("null")) {
                            AshaID = Integer
                                    .valueOf(pregnantwomen.getInt("AshaID"));
                        }
                        if (pregnantwomen.getString("Regwithin12weeks") != null
                                && pregnantwomen.getString("Regwithin12weeks")
                                .length() > 0
                                && !pregnantwomen.getString("Regwithin12weeks")
                                .equalsIgnoreCase("null")) {
                            Regwithin12weeks = Integer.valueOf(pregnantwomen
                                    .getInt("Regwithin12weeks"));
                        }
                        if (pregnantwomen.getString("RegweeksElaspsed") != null
                                && pregnantwomen.getString("RegweeksElaspsed")
                                .length() > 0
                                && !pregnantwomen.getString("RegweeksElaspsed")
                                .equalsIgnoreCase("null")) {
                            RegweeksElaspsed = Integer.valueOf(pregnantwomen
                                    .getInt("RegweeksElaspsed"));
                        }
                        if (pregnantwomen.getString("JSYBenificiaryYN") != null
                                && pregnantwomen.getString("JSYBenificiaryYN")
                                .length() > 0
                                && !pregnantwomen.getString("JSYBenificiaryYN")
                                .equalsIgnoreCase("null")) {
                            JSYBenificiaryYN = Integer.valueOf(pregnantwomen
                                    .getInt("JSYBenificiaryYN"));
                        }
                        if (pregnantwomen.getString("JSYPaymentReceivedYN") != null
                                && pregnantwomen.getString("JSYPaymentReceivedYN")
                                .length() > 0
                                && !pregnantwomen.getString("JSYPaymentReceivedYN")
                                .equalsIgnoreCase("null")) {
                            JSYPaymentReceivedYN = Integer.valueOf(pregnantwomen
                                    .getInt("JSYPaymentReceivedYN"));
                        }
                        if (pregnantwomen.getString("PWAgeYears") != null
                                && pregnantwomen.getString("PWAgeYears").length() > 0
                                && !pregnantwomen.getString("PWAgeYears")
                                .equalsIgnoreCase("null")) {
                            PWAgeYears = Integer.valueOf(pregnantwomen
                                    .getInt("PWAgeYears"));
                        }
                        if (pregnantwomen.getString("PWWeight") != null
                                && pregnantwomen.getString("PWWeight").length() > 0
                                && !pregnantwomen.getString("PWWeight")
                                .equalsIgnoreCase("null")) {
                            PWWeight = Double.valueOf(pregnantwomen
                                    .getInt("PWWeight"));
                        }
                        if (pregnantwomen.getString("PWBloodGroup") != null
                                && pregnantwomen.getString("PWBloodGroup").length() > 0
                                && !pregnantwomen.getString("PWBloodGroup")
                                .equalsIgnoreCase("null")) {
                            PWBloodGroup = Integer.valueOf(pregnantwomen
                                    .getInt("PWBloodGroup"));
                        }

                        PastIllnessYN = pregnantwomen.getString("PastIllnessYN");

                        if (pregnantwomen.getString("TotalPregnancy") != null
                                && pregnantwomen.getString("TotalPregnancy")
                                .length() > 0
                                && !pregnantwomen.getString("TotalPregnancy")
                                .equalsIgnoreCase("null")) {
                            TotalPregnancy = Integer.valueOf(pregnantwomen
                                    .getInt("TotalPregnancy"));
                        }
                        if (pregnantwomen.getString("LastPregnancyResult") != null
                                && pregnantwomen.getString("LastPregnancyResult")
                                .length() > 0
                                && !pregnantwomen.getString("LastPregnancyResult")
                                .equalsIgnoreCase("null")) {
                            LastPregnancyResult = Integer.valueOf(pregnantwomen
                                    .getInt("LastPregnancyResult"));
                        }
                        if (pregnantwomen.getString("LastPregnancyComplication") != null
                                && pregnantwomen.getString(
                                "LastPregnancyComplication").length() > 0
                                && !pregnantwomen.getString(
                                "LastPregnancyComplication")
                                .equalsIgnoreCase("null")) {
                            LastPregnancyComplication = Integer
                                    .valueOf(pregnantwomen
                                            .getInt("LastPregnancyComplication"));
                        }
                        if (pregnantwomen.getString("LTLPregnancyResult") != null
                                && pregnantwomen.getString("LTLPregnancyResult")
                                .length() > 0
                                && !pregnantwomen.getString("LTLPregnancyResult")
                                .equalsIgnoreCase("null")) {
                            LTLPregnancyResult = Integer.valueOf(pregnantwomen
                                    .getInt("LTLPregnancyResult"));
                        }
                        if (pregnantwomen.getString("LTLPregnancyomplication") != null
                                && pregnantwomen.getString(
                                "LTLPregnancyomplication").length() > 0
                                && !pregnantwomen.getString(
                                "LTLPregnancyomplication")
                                .equalsIgnoreCase("null")) {
                            LTLPregnancyomplication = Integer.valueOf(pregnantwomen
                                    .getInt("LTLPregnancyomplication"));
                        }
                        if (pregnantwomen.getString("PWHeight") != null
                                && pregnantwomen.getString("PWHeight").length() > 0
                                && !pregnantwomen.getString("PWHeight")
                                .equalsIgnoreCase("null")) {
                            PWHeight = Integer.valueOf(pregnantwomen
                                    .getInt("PWHeight"));
                        }
                        if (pregnantwomen.getString("LastPregDeliveryPlace") != null
                                && pregnantwomen.getString("LastPregDeliveryPlace")
                                .length() > 0
                                && !pregnantwomen
                                .getString("LastPregDeliveryPlace")
                                .equalsIgnoreCase("null")) {
                            LastPregDeliveryPlace = Integer.valueOf(pregnantwomen
                                    .getInt("LastPregDeliveryPlace"));
                        }
                        if (pregnantwomen.getString("LasttolastPregDeliveryPlace") != null
                                && pregnantwomen.getString(
                                "LasttolastPregDeliveryPlace").length() > 0
                                && !pregnantwomen.getString(
                                "LasttolastPregDeliveryPlace")
                                .equalsIgnoreCase("null")) {
                            LasttolastPregDeliveryPlace = Integer
                                    .valueOf(pregnantwomen
                                            .getInt("LasttolastPregDeliveryPlace"));
                        }
                        if (pregnantwomen.getString("VDRLTestYN") != null
                                && pregnantwomen.getString("VDRLTestYN").length() > 0
                                && !pregnantwomen.getString("VDRLTestYN")
                                .equalsIgnoreCase("null")) {
                            VDRLTestYN = Integer.valueOf(pregnantwomen
                                    .getInt("VDRLTestYN"));
                        }
                        if (pregnantwomen.getString("HIVTestYN") != null
                                && pregnantwomen.getString("HIVTestYN").length() > 0
                                && !pregnantwomen.getString("HIVTestYN")
                                .equalsIgnoreCase("null")) {
                            HIVTestYN = Integer.valueOf(pregnantwomen
                                    .getInt("HIVTestYN"));
                        }
                        if (pregnantwomen.getString("ISAbortion") != null
                                && pregnantwomen.getString("ISAbortion").length() > 0
                                && !pregnantwomen.getString("ISAbortion")
                                .equalsIgnoreCase("null")) {
                            ISAbortion = Integer.valueOf(pregnantwomen
                                    .getInt("ISAbortion"));
                        }
                        if (pregnantwomen.getString("IsPregnant") != null
                                && pregnantwomen.getString("IsPregnant").length() > 0
                                && !pregnantwomen.getString("IsPregnant")
                                .equalsIgnoreCase("null")) {
                            IsPregnant = Integer.valueOf(pregnantwomen
                                    .getInt("IsPregnant"));
                        }
                        if (pregnantwomen.getString("AbortionFacilityType") != null
                                && pregnantwomen.getString("AbortionFacilityType")
                                .length() > 0
                                && !pregnantwomen.getString("AbortionFacilityType")
                                .equalsIgnoreCase("null")) {
                            AbortionFacilityType = Integer.valueOf(pregnantwomen
                                    .getInt("AbortionFacilityType"));
                        }
                        if (pregnantwomen.getString("NoofANCVisitsDone") != null
                                && pregnantwomen.getString("NoofANCVisitsDone")
                                .length() > 0
                                && !pregnantwomen.getString("NoofANCVisitsDone")
                                .equalsIgnoreCase("null")) {
                            NoofANCVisitsDone = Integer.valueOf(pregnantwomen
                                    .getInt("NoofANCVisitsDone"));
                        }
                        if (pregnantwomen.getString("RefferedYN") != null
                                && pregnantwomen.getString("RefferedYN").length() > 0
                                && !pregnantwomen.getString("RefferedYN")
                                .equalsIgnoreCase("null")) {
                            RefferedYN = Integer.valueOf(pregnantwomen
                                    .getInt("RefferedYN"));
                        }
                        if (pregnantwomen.getString("DeliveryPlace") != null
                                && pregnantwomen.getString("DeliveryPlace")
                                .length() > 0
                                && !pregnantwomen.getString("DeliveryPlace")
                                .equalsIgnoreCase("null")) {
                            DeliveryPlace = Integer.valueOf(pregnantwomen
                                    .getInt("DeliveryPlace"));
                        }
                        if (pregnantwomen.getString("DeliveryType") != null
                                && pregnantwomen.getString("DeliveryType").length() > 0
                                && !pregnantwomen.getString("DeliveryType")
                                .equalsIgnoreCase("null")) {
                            DeliveryType = Integer.valueOf(pregnantwomen
                                    .getInt("DeliveryType"));
                        }
                        if (pregnantwomen.getString("MotherDeathCause") != null
                                && pregnantwomen.getString("MotherDeathCause")
                                .length() > 0
                                && !pregnantwomen.getString("MotherDeathCause")
                                .equalsIgnoreCase("null")) {
                            MotherDeathCause = Integer.valueOf(pregnantwomen
                                    .getInt("MotherDeathCause"));
                        }
                        if (pregnantwomen.getString("DeliveryOutcome") != null
                                && pregnantwomen.getString("DeliveryOutcome")
                                .length() > 0
                                && !pregnantwomen.getString("DeliveryOutcome")
                                .equalsIgnoreCase("null")) {
                            DeliveryOutcome = Integer.valueOf(pregnantwomen
                                    .getInt("DeliveryOutcome"));
                        }
                        if (pregnantwomen.getString("PlaceofDeath") != null
                                && pregnantwomen.getString("PlaceofDeath").length() > 0
                                && !pregnantwomen.getString("PlaceofDeath")
                                .equalsIgnoreCase("null")) {
                            PlaceofDeath = Integer.valueOf(pregnantwomen
                                    .getInt("PlaceofDeath"));
                        }
                        if (pregnantwomen.getString("CreatedBy") != null
                                && pregnantwomen.getString("CreatedBy").length() > 0
                                && !pregnantwomen.getString("CreatedBy")
                                .equalsIgnoreCase("null")) {
                            CreatedBy = Integer.valueOf(pregnantwomen
                                    .getInt("CreatedBy"));
                        }
                        if (pregnantwomen.getString("UpdatedBy") != null
                                && pregnantwomen.getString("UpdatedBy").length() > 0
                                && !pregnantwomen.getString("UpdatedBy")
                                .equalsIgnoreCase("null")) {
                            UpdatedBy = Integer.valueOf(pregnantwomen
                                    .getInt("UpdatedBy"));
                        }
                        if (pregnantwomen.getString("Education") != null
                                && pregnantwomen.getString("Education").length() > 0
                                && !pregnantwomen.getString("Education")
                                .equalsIgnoreCase("null")) {
                            Education = Integer.valueOf(pregnantwomen
                                    .getInt("Education"));
                        }
                        if (pregnantwomen.getString("Abortion_FacilityName") != null
                                && pregnantwomen.getString("Abortion_FacilityName")
                                .length() > 0
                                && !pregnantwomen
                                .getString("Abortion_FacilityName")
                                .equalsIgnoreCase("null")) {
                            Abortion_FacilityName = Integer.valueOf(pregnantwomen
                                    .getInt("Abortion_FacilityName"));
                        }
                        if (pregnantwomen.getString("IsDeleted") != null
                                && pregnantwomen.getString("IsDeleted").length() > 0
                                && !pregnantwomen.getString("IsDeleted")
                                .equalsIgnoreCase("null")) {
                            IsDeleted = Integer.valueOf(pregnantwomen
                                    .getInt("IsDeleted"));
                        }
                        String sqlcount = "select count(*) from tblPregnant_woman where  PWGUID='"
                                + PWGUID + "'";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from tblPregnant_woman where  PWGUID='"
                                + PWGUID + "' and IsEdited=1";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);
                        String sql = "";
                        if (IsDeleted == 1 && count1 == 0) {
                            String sqldelete = "delete from tblPregnant_woman where  PWGUID='"
                                    + PWGUID + "' ";
                            dataProvider.executeSql(sqldelete);

                            String sqldelete1 = "delete from tblANCVisit where PWGUID='"
                                    + PWGUID + "' ";
                            dataProvider.executeSql(sqldelete1);
                            String sqldelete2 = "delete from tblChild where pw_GUID='"
                                    + PWGUID + "' ";
                            dataProvider.executeSql(sqldelete2);
                            String sqldelete3 = "delete from tblPNChomevisit_ANS where PWGUID='"
                                    + PWGUID + "' ";
                            dataProvider.executeSql(sqldelete3);

                        } else {
                            if (count == 0) {

                                sql = "Insert into tblPregnant_woman(Education,AltMobileNo,IsPregnant,PWGUID,HHGUID,HHFamilyMemberGUID,PWName,ANMID,PWImage,AshaID,LMPDate,EDDDate,PWRegistrationDate,Regwithin12weeks,RegweeksElaspsed,HusbandName,Husband_GUID,MobileNo,MotherMCTSID,IFSCCode,Accountno,JSYBenificiaryYN,JSYRegDate,JSYPaymentReceivedYN,PWDOB,PWAgeYears,PWAgeRefDate,PWWeight,PWBloodGroup,PastIllnessYN,TotalPregnancy,LastPregnancyResult,LastPregnancyComplication,LTLPregnancyResult,LTLPregnancyomplication,PWHeight,LastPregDeliveryPlace,LasttolastPregDeliveryPlace,ExpFacilityforDelivery,ExpFacilityforDeliveryName,VDRLTestYN,VDRLResult,HIVTestYN,HIVResult,Visit1Date,Visit2Date,Visit3Date,Visit4Date,ISAbortion,AbortionFacilityType,NoofANCVisitsDone,LastANCVisitDate,TT1Date,TT2Date,TTBoosterDate,DangerSigns,RefferedYN,DeliveryDateTime,DeliveryPlace,DeliveryConductedBy,DeliveryType,DeliveryComplication,MotherDeathCause,DeliveryOutcome,DTMFacilityDischarge,PaymentRecieved,PlaceofDeath,DateofDeath,OtherPlaceofDeath,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,IsEdited,IsUploaded,Abortion_FacilityName, AnyOtherPastIllness,AnyOtherLastPregCom,AnyOtherLTLPregCom,HighRisk,MaternalDeath,BankAcc,ChildDeathCause,MotherDCOther,DeathPlaceOther,FacitylastPreg,FaciltyOtherLTL,FacityLTL,FacilityOtherLastpreg)values("
                                        + Education
                                        + ",'"
                                        + AltMobileNo
                                        + "',"
                                        + IsPregnant
                                        + ",'"
                                        + PWGUID
                                        + "','"
                                        + HHGUID
                                        + "','"
                                        + HHFamilyMemberGUID
                                        + "','"
                                        + PWName
                                        + "',"
                                        + ANMID
                                        + ",'"
                                        + PWImage
                                        + "',"
                                        + AshaID
                                        + ",'"
                                        + LMPDate
                                        + "','"
                                        + EDDDate
                                        + "','"
                                        + PWRegistrationDate
                                        + "',"
                                        + Regwithin12weeks
                                        + ","
                                        + RegweeksElaspsed
                                        + ",'"
                                        + HusbandName
                                        + "','"
                                        + Husband_GUID
                                        + "','"
                                        + MobileNo
                                        + "','"
                                        + MotherMCTSID
                                        + "','"
                                        + IFSCCode
                                        + "','"
                                        + Accountno
                                        + "',"
                                        + JSYBenificiaryYN
                                        + ",'"
                                        + JSYRegDate
                                        + "',"
                                        + JSYPaymentReceivedYN
                                        + ",'"
                                        + PWDOB
                                        + "',"
                                        + PWAgeYears
                                        + ",'"
                                        + PWAgeRefDate
                                        + "',"
                                        + PWWeight
                                        + ","
                                        + PWBloodGroup
                                        + ",'"
                                        + PastIllnessYN
                                        + "',"
                                        + TotalPregnancy
                                        + ","
                                        + LastPregnancyResult
                                        + ","
                                        + LastPregnancyComplication
                                        + ","
                                        + LTLPregnancyResult
                                        + ","
                                        + LTLPregnancyomplication
                                        + ","
                                        + PWHeight
                                        + ","
                                        + LastPregDeliveryPlace
                                        + ","
                                        + LasttolastPregDeliveryPlace
                                        + ",'"
                                        + ExpFacilityforDelivery
                                        + "','"
                                        + ExpFacilityforDeliveryName
                                        + "',"
                                        + VDRLTestYN
                                        + ",'"
                                        + VDRLResult
                                        + "',"
                                        + HIVTestYN
                                        + ",'"
                                        + HIVResult
                                        + "','"
                                        + Visit1Date
                                        + "','"
                                        + Visit2Date
                                        + "','"
                                        + Visit3Date
                                        + "','"
                                        + Visit4Date
                                        + "',"
                                        + ISAbortion
                                        + ","
                                        + AbortionFacilityType
                                        + ","
                                        + NoofANCVisitsDone
                                        + ",'"
                                        + LastANCVisitDate
                                        + "','"
                                        + TT1Date
                                        + "','"
                                        + TT2Date
                                        + "','"
                                        + TTBoosterDate
                                        + "','"
                                        + DangerSigns
                                        + "',"
                                        + RefferedYN
                                        + ",'"
                                        + DeliveryDateTime
                                        + "',"
                                        + DeliveryPlace
                                        + ",'"
                                        + DeliveryConductedBy
                                        + "',"
                                        + DeliveryType
                                        + ",'"
                                        + DeliveryComplication
                                        + "',"
                                        + MotherDeathCause
                                        + ","
                                        + DeliveryOutcome
                                        + ",'"
                                        + DTMFacilityDischarge
                                        + "','"
                                        + PaymentRecieved
                                        + "',"
                                        + PlaceofDeath
                                        + ",'"
                                        + DateofDeath
                                        + "','"
                                        + OtherPlaceofDeath
                                        + "','"
                                        + CreatedOn
                                        + "',"
                                        + CreatedBy
                                        + ",'"
                                        + UpdatedOn
                                        + "',"
                                        + UpdatedBy
                                        + ",0,0,"
                                        + Abortion_FacilityName
                                        + ",'"
                                        + AnyOtherPastIllness
                                        + "','"
                                        + AnyOtherLastPregCom
                                        + "','"
                                        + AnyOtherLTLPregCom
                                        + "',"
                                        + HighRisk
                                        + ","
                                        + MaternalDeath
                                        + ","
                                        + BankAcc
                                        + ","
                                        + ChildDeathCause
                                        + ",'"
                                        + MotherDCOther
                                        + "','"
                                        + DeathPlaceOther
                                        + "',"
                                        + FacitylastPreg
                                        + ",'"
                                        + FaciltyOtherLTL
                                        + "',"
                                        + FacityLTL
                                        + ",'"
                                        + FacilityOtherLastpreg + "')";
                                dataProvider.executeSql(sql);
                                importimage(PWImage);
                            } else {
                                if (count1 == 0) {
                                    importimage(PWImage);
                                    sql = "update tblPregnant_woman set Education="
                                            + Education
                                            + ", IsPregnant="
                                            + IsPregnant
                                            + ",HHGUID='"
                                            + HHGUID
                                            + "',HHFamilyMemberGUID='"
                                            + HHFamilyMemberGUID
                                            + "',AltMobileNo='"
                                            + AltMobileNo
                                            + "',PWName='"
                                            + PWName
                                            + "',ANMID="
                                            + ANMID
                                            + ",PWImage='"
                                            + PWImage
                                            + "',AshaID="
                                            + AshaID
                                            + ",LMPDate='"
                                            + LMPDate
                                            + "',EDDDate='"
                                            + EDDDate
                                            + "',PWRegistrationDate='"
                                            + PWRegistrationDate
                                            + "',Regwithin12weeks="
                                            + Regwithin12weeks
                                            + ",RegweeksElaspsed="
                                            + RegweeksElaspsed
                                            + ",HusbandName='"
                                            + HusbandName
                                            + "',Husband_GUID='"
                                            + Husband_GUID
                                            + "',MobileNo='"
                                            + MobileNo
                                            + "',MotherMCTSID='"
                                            + MotherMCTSID
                                            + "',IFSCCode='"
                                            + IFSCCode
                                            + "',Accountno='"
                                            + Accountno
                                            + "',JSYBenificiaryYN="
                                            + JSYBenificiaryYN
                                            + ",JSYRegDate='"
                                            + JSYRegDate
                                            + "',JSYPaymentReceivedYN="
                                            + JSYPaymentReceivedYN
                                            + ",PWDOB='"
                                            + PWDOB
                                            + "',PWAgeYears="
                                            + PWAgeYears
                                            + ",PWAgeRefDate='"
                                            + PWAgeRefDate
                                            + "',PWWeight="
                                            + PWWeight
                                            + ",PWBloodGroup="
                                            + PWBloodGroup
                                            + ",PastIllnessYN='"
                                            + PastIllnessYN
                                            + "',TotalPregnancy="
                                            + TotalPregnancy
                                            + ",LastPregnancyResult="
                                            + LastPregnancyResult
                                            + ",LastPregnancyComplication="
                                            + LastPregnancyComplication
                                            + ",LTLPregnancyResult="
                                            + LTLPregnancyResult
                                            + ",LTLPregnancyomplication="
                                            + LTLPregnancyomplication
                                            + ",PWHeight="
                                            + PWHeight
                                            + ",LastPregDeliveryPlace="
                                            + LastPregDeliveryPlace
                                            + ",LasttolastPregDeliveryPlace="
                                            + LasttolastPregDeliveryPlace
                                            + ",ExpFacilityforDelivery='"
                                            + ExpFacilityforDelivery
                                            + "',ExpFacilityforDeliveryName='"
                                            + ExpFacilityforDeliveryName
                                            + "',VDRLTestYN="
                                            + VDRLTestYN
                                            + ",VDRLResult='"
                                            + VDRLResult
                                            + "',HIVTestYN="
                                            + HIVTestYN
                                            + ",HIVResult='"
                                            + HIVResult
                                            + "',Visit1Date='"
                                            + Visit1Date
                                            + "',Visit2Date='"
                                            + Visit2Date
                                            + "',Visit3Date='"
                                            + Visit3Date
                                            + "',Visit4Date='"
                                            + Visit4Date
                                            + "',ISAbortion="
                                            + ISAbortion
                                            + ",AbortionFacilityType="
                                            + AbortionFacilityType
                                            + ",NoofANCVisitsDone="
                                            + NoofANCVisitsDone
                                            + ",LastANCVisitDate='"
                                            + LastANCVisitDate
                                            + "',TT1Date='"
                                            + TT1Date
                                            + "',TT2Date='"
                                            + TT2Date
                                            + "',TTBoosterDate='"
                                            + TTBoosterDate
                                            + "',DangerSigns='"
                                            + DangerSigns
                                            + "',RefferedYN="
                                            + RefferedYN
                                            + ",DeliveryDateTime='"
                                            + DeliveryDateTime
                                            + "',DeliveryPlace="
                                            + DeliveryPlace
                                            + ",DeliveryConductedBy='"
                                            + DeliveryConductedBy
                                            + "',DeliveryType="
                                            + DeliveryType
                                            + ",DeliveryComplication='"
                                            + DeliveryComplication
                                            + "',MotherDeathCause="
                                            + MotherDeathCause
                                            + ",DeliveryOutcome="
                                            + DeliveryOutcome
                                            + ",DTMFacilityDischarge='"
                                            + DTMFacilityDischarge
                                            + "',PaymentRecieved='"
                                            + PaymentRecieved
                                            + "',PlaceofDeath="
                                            + PlaceofDeath
                                            + ",DateofDeath='"
                                            + DateofDeath
                                            + "',OtherPlaceofDeath='"
                                            + OtherPlaceofDeath
                                            + "',CreatedOn='"
                                            + CreatedOn
                                            + "',CreatedBy="
                                            + CreatedBy
                                            + ",UpdatedOn='"
                                            + UpdatedOn
                                            + "',UpdatedBy="
                                            + UpdatedBy
                                            + ",IsEdited=0,IsUploaded=0,Abortion_FacilityName="
                                            + Abortion_FacilityName
                                            + ",AnyOtherPastIllness='"
                                            + AnyOtherPastIllness
                                            + "',AnyOtherLastPregCom='"
                                            + AnyOtherLastPregCom
                                            + "',AnyOtherLTLPregCom='"
                                            + AnyOtherLTLPregCom + "',HighRisk="
                                            + HighRisk + ",MaternalDeath="
                                            + MaternalDeath + ",BankAcc=" + BankAcc
                                            + ",ChildDeathCause=" + ChildDeathCause
                                            + ",MotherDCOther='" + MotherDCOther
                                            + "',DeathPlaceOther='"
                                            + DeathPlaceOther + "',FacitylastPreg="
                                            + FacitylastPreg + ",FaciltyOtherLTL='"
                                            + FaciltyOtherLTL + "',FacityLTL="
                                            + FacityLTL
                                            + ",FacilityOtherLastpreg='"
                                            + FacilityOtherLastpreg
                                            + "' where PWGUID='" + PWGUID + "'";
                                    dataProvider.executeSql(sql);
                                }
                            }

                        }
                    }

                    tblChildArray = jsonObj.getJSONArray("tblchild");
                    for (int i = 0; i < tblChildArray.length(); i++) {
                        JSONObject Child = tblChildArray.getJSONObject(i);
                        String pw_GUID = "";
                        String HHGUID = "";
                        String HHFamilyMemberGUID = "";
                        int Child_ID = 0;
                        String ChildGUID = "";
                        String MotherGUID = "";
                        String Date_Of_Registration = "";
                        String Child_dob = "";
                        String Birth_time = "";
                        int Gender = 0;
                        float Wt_of_child = 0;
                        int place_of_birth = 0;
                        String preTerm_fullTerm = "";
                        int mother_status = 0;
                        int child_status = 0;
                        String mother_death_dt = "";
                        String child_death_dt = "";
                        String child_mcts_id = "";
                        String child_name = "";
                        String cried_after_birth = "";
                        int breastfeeding_within1H = 0;
                        String Exclusive_BF = "";
                        String complementry_BF = "";
                        String bcg = "";
                        String opv1 = "";
                        String dpt1 = "";
                        String hepb1 = "";
                        String opv2 = "";
                        String dpt2 = "";
                        String hepb2 = "";
                        String opv3 = "";
                        String dpt3 = "";
                        String hepb3 = "";
                        String measeals = "";
                        String vitaminA = "";
                        String created_on = "";
                        int created_by = 0;
                        String modified_on = "";
                        int modified_by = 0;
                        int AshaID = 0;
                        int ANMID = 0;
                        int IsDeleted = 0;
                        int IsUploaded = 0;
                        int FacilityType = 0;
                        String Facility = "";
                        String opv4 = "";
                        String hepb4 = "";
                        String Pentavalent1 = "";
                        String Pentavalent2 = "";
                        String Pentavalent3 = "";
                        String IPV = "";
                        String DPTBooster = "";
                        String OPVBooster = "";
                        String MeaslesTwoDose = "";
                        String VitaminAtwo = "";
                        String DPTBoostertwo = "";
                        String ChildTT = "";
                        // add jitendra
                        String JEVaccine1 = "";
                        String JEVaccine2 = "";
                        String VitaminA3 = "";
                        String VitaminA4 = "";
                        String VitaminA5 = "";
                        String VitaminA6 = "";
                        String VitaminA7 = "";
                        String VitaminA8 = "";
                        String VitaminA9 = "";
                        String TT2 = "";
                        String MeaslesRubella = "";
                        String IPV2 = "";
                        int DeliveryType = 0;
                        Facility = Child.getString("Facility");
                        opv4 = Child.getString("opv4");
                        hepb4 = Child.getString("hepb4");
                        Pentavalent1 = Child.getString("Pentavalent1");
                        Pentavalent2 = Child.getString("Pentavalent2");
                        Pentavalent3 = Child.getString("Pentavalent3");
                        IPV = Child.getString("IPV");
                        DPTBooster = Child.getString("DPTBooster");
                        OPVBooster = Child.getString("OPVBooster");
                        MeaslesTwoDose = Child.getString("MeaslesTwoDose");
                        VitaminAtwo = Child.getString("VitaminAtwo");
                        DPTBoostertwo = Child.getString("DPTBoostertwo");
                        ChildTT = Child.getString("ChildTT");
                        pw_GUID = Child.getString("pw_GUID");
                        HHGUID = Child.getString("HHGUID");
                        HHFamilyMemberGUID = Child.getString("HHFamilyMemberGUID");
                        ChildGUID = Child.getString("childGUID");
                        MotherGUID = Child.getString("motherGUID");
                        Date_Of_Registration = Child
                                .getString("Date_Of_Registration");
                        Child_dob = Child.getString("child_dob");
                        Birth_time = Child.getString("birth_time");
                        preTerm_fullTerm = Child.getString("preTerm_fullTerm");
                        mother_death_dt = Child.getString("mother_death_dt");
                        child_death_dt = Child.getString("child_death_dt");
                        child_mcts_id = Child.getString("child_mcts_id");
                        child_name = Child.getString("child_name");
                        cried_after_birth = Child.getString("cried_after_birth");
                        Exclusive_BF = Child.getString("Exclusive_BF");
                        complementry_BF = Child.getString("complementry_BF");
                        bcg = Child.getString("bcg");
                        opv1 = Child.getString("opv1");
                        dpt1 = Child.getString("dpt1");
                        hepb1 = Child.getString("hepb1");
                        opv2 = Child.getString("opv2");
                        dpt2 = Child.getString("dpt2");
                        hepb2 = Child.getString("hepb2");
                        opv3 = Child.getString("opv3");
                        dpt3 = Child.getString("dpt3");
                        hepb3 = Child.getString("hepb3");
                        measeals = Child.getString("measeals");
                        vitaminA = Child.getString("vitaminA");
                        created_on = Child.getString("created_on");
                        modified_on = Child.getString("modified_on");
                        JEVaccine1 = Child.getString("JEVaccine1");
                        JEVaccine2 = Child.getString("JEVaccine2");
                        VitaminA3 = Child.getString("VitaminA3");
                        VitaminA4 = Child.getString("VitaminA4");
                        VitaminA5 = Child.getString("VitaminA5");
                        VitaminA6 = Child.getString("VitaminA6");
                        VitaminA7 = Child.getString("VitaminA7");
                        VitaminA8 = Child.getString("VitaminA8");
                        VitaminA9 = Child.getString("VitaminA9");
                        TT2 = Child.getString("TT2");
                        MeaslesRubella = Child.getString("MeaslesRubella");
                        IPV2 = Child.getString("IPV2");
                        DeliveryType = Validate.returnIntegerValue(Child.getString("DeliveryType"));

                        if (Child.getString("child_id") != null
                                && Child.getString("child_id").length() > 0
                                && !Child.getString("child_id").equalsIgnoreCase(
                                "null")) {
                            Child_ID = Integer.valueOf(Child.getInt("child_id"));
                        }
                        if (Child.getString("Gender") != null
                                && Child.getString("Gender").length() > 0
                                && !Child.getString("Gender").equalsIgnoreCase(
                                "null")) {
                            Gender = Integer.valueOf(Child.getInt("Gender"));
                        }
                        if (Child.getString("Wt_of_child") != null
                                && Child.getString("Wt_of_child").length() > 0
                                && !Child.getString("Wt_of_child")
                                .equalsIgnoreCase("null")) {
                            Wt_of_child = Float
                                    .valueOf(Child.getInt("Wt_of_child"));
                        }
                        if (Child.getString("place_of_birth") != null
                                && Child.getString("place_of_birth").length() > 0
                                && !Child.getString("place_of_birth")
                                .equalsIgnoreCase("null")) {
                            place_of_birth = Integer.valueOf(Child
                                    .getInt("place_of_birth"));
                        }
                        if (Child.getString("mother_status") != null
                                && Child.getString("mother_status").length() > 0
                                && !Child.getString("mother_status")
                                .equalsIgnoreCase("null")) {
                            mother_status = Integer.valueOf(Child
                                    .getInt("mother_status"));
                        }
                        if (Child.getString("child_status") != null
                                && Child.getString("child_status").length() > 0
                                && !Child.getString("child_status")
                                .equalsIgnoreCase("null")) {
                            child_status = Integer.valueOf(Child
                                    .getInt("child_status"));
                        }
                        if (Child.getString("breastfeeding_within1H") != null
                                && Child.getString("breastfeeding_within1H")
                                .length() > 0
                                && !Child.getString("breastfeeding_within1H")
                                .equalsIgnoreCase("null")) {
                            breastfeeding_within1H = Integer.valueOf(Child
                                    .getInt("breastfeeding_within1H"));
                        }
                        if (Child.getString("created_by") != null
                                && Child.getString("created_by").length() > 0
                                && !Child.getString("created_by").equalsIgnoreCase(
                                "null")) {
                            created_by = Integer
                                    .valueOf(Child.getInt("created_by"));
                        }
                        if (Child.getString("modified_by") != null
                                && Child.getString("modified_by").length() > 0
                                && !Child.getString("modified_by")
                                .equalsIgnoreCase("null")) {
                            modified_by = Integer.valueOf(Child
                                    .getInt("modified_by"));
                        }
                        if (Child.getString("AshaID") != null
                                && Child.getString("AshaID").length() > 0
                                && !Child.getString("AshaID").equalsIgnoreCase(
                                "null")) {
                            AshaID = Integer.valueOf(Child.getInt("AshaID"));
                        }
                        if (Child.getString("ANMID") != null
                                && Child.getString("ANMID").length() > 0
                                && !Child.getString("ANMID").equalsIgnoreCase(
                                "null")) {
                            ANMID = Integer.valueOf(Child.getInt("ANMID"));
                        }
                        if (Child.getString("FacilityType") != null
                                && Child.getString("FacilityType").length() > 0
                                && !Child.getString("FacilityType")
                                .equalsIgnoreCase("null")) {
                            FacilityType = Integer.valueOf(Child
                                    .getInt("FacilityType"));
                        }
                        if (Child.getString("IsDeleted") != null
                                && Child.getString("IsDeleted").length() > 0
                                && !Child.getString("IsDeleted").equalsIgnoreCase(
                                "null")) {
                            IsDeleted = Integer.valueOf(Child.getInt("IsDeleted"));
                        }
                        String sqlcount = "select count(*) from tblChild  where ChildGUID='"
                                + ChildGUID + "'";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from tblChild  where ChildGUID='"
                                + ChildGUID + "' ";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);

                        String sql = "";
                        if (IsDeleted == 1 && count1 == 0) {

                            String sqldelete2 = "delete from tblChild where ChildGUID='"
                                    + ChildGUID + "' ";
                            dataProvider.executeSql(sqldelete2);
                            String sqldelete3 = "delete from tblPNChomevisit_ANS where ChildGUID='"
                                    + ChildGUID + "' ";
                            dataProvider.executeSql(sqldelete3);

                        } else {
                            if (count == 0) {

                                sql = "Insert into tblChild(AshaID,ANMID,pw_GUID,Facility,opv4,hepb4,Pentavalent1,Pentavalent2,Pentavalent3,IPV,DPTBooster,OPVBooster,MeaslesTwoDose,VitaminAtwo,DPTBoostertwo,ChildTT,FacilityType,HHGUID,HHFamilyMemberGUID,Child_ID,ChildGUID,MotherGUID,Date_Of_Registration,Child_dob,Birth_time,Gender,Wt_of_child,place_of_birth,preTerm_fullTerm,mother_status,child_status,mother_death_dt,child_death_dt,child_mcts_id,child_name,cried_after_birth,breastfeeding_within1H,Exclusive_BF,complementry_BF,bcg,opv1,dpt1,hepb1,opv2,dpt2,hepb2,opv3,dpt3,hepb3,measeals,vitaminA,created_on,created_by,modified_on,modified_by,IsEdited,IsUploaded,JEVaccine1,JEVaccine2,VitaminA3,VitaminA4,VitaminA5,VitaminA6,VitaminA7,VitaminA8,VitaminA9,TT2,MeaslesRubella,IPV2,DeliveryType)values("
                                        + AshaID
                                        + ","
                                        + ANMID
                                        + ",'"
                                        + pw_GUID
                                        + "',	'"
                                        + Facility
                                        + "',	'"
                                        + opv4
                                        + "',	'"
                                        + hepb4
                                        + "','"
                                        + Pentavalent1
                                        + "',	'"
                                        + Pentavalent2
                                        + "',	'"
                                        + Pentavalent3
                                        + "',		'"
                                        + IPV
                                        + "',	'"
                                        + DPTBooster
                                        + "','"
                                        + OPVBooster
                                        + "',	'"
                                        + MeaslesTwoDose
                                        + "','"
                                        + VitaminAtwo
                                        + "',	'"
                                        + DPTBoostertwo
                                        + "',	'"
                                        + ChildTT
                                        + "',"
                                        + FacilityType
                                        + ",'"
                                        + HHGUID
                                        + "','"
                                        + HHFamilyMemberGUID
                                        + "',"
                                        + Child_ID
                                        + ",'"
                                        + ChildGUID
                                        + "','"
                                        + MotherGUID
                                        + "','"
                                        + Date_Of_Registration
                                        + "','"
                                        + Child_dob
                                        + "','"
                                        + Birth_time
                                        + "',"
                                        + Gender
                                        + ","
                                        + Wt_of_child
                                        + ","
                                        + place_of_birth
                                        + ",'"
                                        + preTerm_fullTerm
                                        + "',"
                                        + mother_status
                                        + ","
                                        + child_status
                                        + ",'"
                                        + mother_death_dt
                                        + "','"
                                        + child_death_dt
                                        + "','"
                                        + child_mcts_id
                                        + "','"
                                        + child_name
                                        + "','"
                                        + cried_after_birth
                                        + "',"
                                        + breastfeeding_within1H
                                        + ",'"
                                        + Exclusive_BF
                                        + "','"
                                        + complementry_BF
                                        + "','"
                                        + bcg
                                        + "','"
                                        + opv1
                                        + "','"
                                        + dpt1
                                        + "','"
                                        + hepb1
                                        + "','"
                                        + opv2
                                        + "','"
                                        + dpt2
                                        + "','"
                                        + hepb2
                                        + "','"
                                        + opv3
                                        + "','"
                                        + dpt3
                                        + "','"
                                        + hepb3
                                        + "','"
                                        + measeals
                                        + "','"
                                        + vitaminA
                                        + "','"
                                        + created_on
                                        + "',"
                                        + created_by
                                        + ",'"
                                        + modified_on
                                        + "',"
                                        + modified_by
                                        + ",0,0, '"
                                        + JEVaccine1
                                        + "','"
                                        + JEVaccine2
                                        + "','"
                                        + VitaminA3
                                        + "','"
                                        + VitaminA4
                                        + "','"
                                        + VitaminA5
                                        + "','"
                                        + VitaminA6
                                        + "','"
                                        + VitaminA7
                                        + "','"
                                        + VitaminA8
                                        + "','"
                                        + VitaminA9
                                        + "','"
                                        + TT2 + "','"
                                        + MeaslesRubella + "','"
                                        + IPV2 + "','"
                                        + DeliveryType + "')";
                                dataProvider.executeSql(sql);

                            } else {
                                if (count1 == 0) {
                                    sql = "update  tblChild set pw_GUID='"
                                            + pw_GUID
                                            + "',HHGUID='"
                                            + HHGUID
                                            + "',HHFamilyMemberGUID='"
                                            + HHFamilyMemberGUID
                                            + "',Child_ID="
                                            + Child_ID
                                            + ",MotherGUID='"
                                            + MotherGUID
                                            + "',Date_Of_Registration='"
                                            + Date_Of_Registration
                                            + "',Child_dob='"
                                            + Child_dob
                                            + "',Birth_time='"
                                            + Birth_time
                                            + "',Gender="
                                            + Gender
                                            + ",Wt_of_child="
                                            + Wt_of_child
                                            + ",place_of_birth="
                                            + place_of_birth
                                            + ",preTerm_fullTerm='"
                                            + preTerm_fullTerm
                                            + "',mother_status="
                                            + mother_status
                                            + ",child_status="
                                            + child_status
                                            + ",mother_death_dt='"
                                            + mother_death_dt
                                            + "',child_death_dt='"
                                            + child_death_dt
                                            + "',child_mcts_id='"
                                            + child_mcts_id
                                            + "',child_name='"
                                            + child_name
                                            + "',cried_after_birth='"
                                            + cried_after_birth
                                            + "',breastfeeding_within1H="
                                            + breastfeeding_within1H
                                            + ",Exclusive_BF='"
                                            + Exclusive_BF
                                            + "',complementry_BF='"
                                            + complementry_BF
                                            + "',bcg='"
                                            + bcg
                                            + "',opv1='"
                                            + opv1
                                            + "',dpt1='"
                                            + dpt1
                                            + "',hepb1='"
                                            + hepb1
                                            + "',opv2='"
                                            + opv2
                                            + "',dpt2='"
                                            + dpt2
                                            + "',hepb2='"
                                            + hepb2
                                            + "',opv3='"
                                            + opv3
                                            + "',dpt3='"
                                            + dpt3
                                            + "',hepb3='"
                                            + hepb3
                                            + "',measeals='"
                                            + measeals
                                            + "',vitaminA='"
                                            + vitaminA
                                            + "',created_on='"
                                            + created_on
                                            + "',created_by="
                                            + created_by
                                            + ",modified_on='"
                                            + modified_on
                                            + "',modified_by="
                                            + modified_by
                                            + " ,AshaID="
                                            + AshaID
                                            + ",Facility='"
                                            + Facility
                                            + "',	opv4='"
                                            + opv4
                                            + "',	hepb4='"
                                            + hepb4
                                            + "',Pentavalent1='"
                                            + Pentavalent1
                                            + "',	Pentavalent2='"
                                            + Pentavalent2
                                            + "',	Pentavalent3='"
                                            + Pentavalent3
                                            + "',IPV='"
                                            + IPV
                                            + "',	DPTBooster='"
                                            + DPTBooster
                                            + "',OPVBooster='"
                                            + OPVBooster
                                            + "',MeaslesTwoDose=	'"
                                            + MeaslesTwoDose
                                            + "',VitaminAtwo='"
                                            + VitaminAtwo
                                            + "',	DPTBoostertwo='"
                                            + DPTBoostertwo
                                            + "',ChildTT=	'"
                                            + ChildTT
                                            + "',ANMID="
                                            + ANMID
                                            + ",FacilityType="
                                            + FacilityType
                                            + ",JEVaccine1='"
                                            + JEVaccine1
                                            + "',JEVaccine2='"
                                            + JEVaccine2
                                            + "',VitaminA3='"
                                            + VitaminA3
                                            + "',VitaminA4='"
                                            + VitaminA4
                                            + "',VitaminA5='"
                                            + VitaminA5
                                            + "',VitaminA6='"
                                            + VitaminA6
                                            + "',VitaminA7='"
                                            + VitaminA7
                                            + "',VitaminA8='"
                                            + VitaminA8
                                            + "',VitaminA9='"
                                            + VitaminA9
                                            + "',TT2='"
                                            + TT2
                                            + "',MeaslesRubella='"
                                            + MeaslesRubella
                                            + "',IPV2='"
                                            + IPV2
                                            + "',DeliveryType='"
                                            + DeliveryType
                                            + "',IsEdited=0,IsUploaded=0 where ChildGUID='"
                                            + ChildGUID + "'";
                                    dataProvider.executeSql(sql);
                                }
                            }
                        }
                    }


                    TblANCVisitArray = jsonObj.getJSONArray("tblancvisit");
                    for (int i = 0; i < TblANCVisitArray.length(); i++) {
                        JSONObject ANCVisit = TblANCVisitArray.getJSONObject(i);

                        String PWGUID = "";
                        String VisitGUID = "";
                        int ByANMID = 0;
                        int ByAshaID = 0;
                        String MCTSID = "";
                        int Visit_No = 0;
                        int Trimester = 0;
                        String VisitDueDate = "";
                        String CheckupVisitDate = "";
                        int CheckupPlace = 0;
                        double BirthWeight = 0;
                        int BP = 0;
                        String BPResult = "";
                        double Hemoglobin = 0;
                        int UrineTest = 0;
                        int UrineSugar = 0;
                        int UrineAlbumin = 0;
                        String TTfirstDoseDate = "";
                        String TTsecondDoseDate = "";
                        String TTBoosterDate = "";
                        int VDRLTest = 0;
                        int HIVTest = 0;
                        int UltraSound = 0;
                        int UltraSoundConductedby = 0;
                        int IFARecieved = 0;
                        int NumberIFARecieved = 0;
                        String CreatedOn = "";
                        int CreatedBy = 0;
                        String UpdatedOn = "";
                        int UpdatedBy = 0;
                        int UltrasoundResult = 0;
                        String HomeVisitDate = "";
                        int TT1TT2last2yr = 0;

                        int AncVisitID = 0;
                        int PregWomenReg = 0;
                        int McpCard = 0;
                        int TT1 = 0;
                        String TT1date = "";
                        int TT2 = 0;
                        String TT2date = "";
                        int TTbooster = 0;
                        String TTboosterDate1 = "";
                        float Weight1 = 0;
                        float BP1 = 0;
                        int HB1 = 0;
                        int UrineTestsugar1 = 0;
                        int UrineTestAl1 = 0;
                        int IronTablet1 = 0;
                        String AncCheckup1 = "";
                        int Weight1YN = 0;
                        int BP1YN = 0;
                        int HB1YN = 0;
                        int UrineTestsugar1YN = 0;
                        int UrineTestAl1YN = 0;
                        int IronTablet1YN = 0;
                        int AncCheckup1YN = 0;
                        int DeliveryONhospYN = 0;
                        int FamilyPlanning = 0;
                        int DangerSign = 0;
                        int CalciumReceived = 0;
                        int CalciumTablet = 0;
                        int HighRisk = 0;
                        String HighRiskDate = "";

                        if (ANCVisit.getString("AncVisitID") != null
                                && ANCVisit.getString("AncVisitID").length() > 0
                                && !ANCVisit.getString("AncVisitID")
                                .equalsIgnoreCase("null")) {
                            AncVisitID = Integer.valueOf(ANCVisit
                                    .getInt("AncVisitID"));
                        }
                        if (ANCVisit.getString("PregWomenReg") != null
                                && ANCVisit.getString("PregWomenReg").length() > 0
                                && !ANCVisit.getString("PregWomenReg")
                                .equalsIgnoreCase("null")) {
                            PregWomenReg = Integer.valueOf(ANCVisit
                                    .getInt("PregWomenReg"));
                        }
                        if (ANCVisit.getString("McpCard") != null
                                && ANCVisit.getString("McpCard").length() > 0
                                && !ANCVisit.getString("McpCard").equalsIgnoreCase(
                                "null")) {
                            McpCard = Integer.valueOf(ANCVisit.getInt("McpCard"));
                        }
                        if (ANCVisit.getString("TT1") != null
                                && ANCVisit.getString("TT1").length() > 0
                                && !ANCVisit.getString("TT1").equalsIgnoreCase(
                                "null")) {
                            TT1 = Integer.valueOf(ANCVisit.getInt("TT1"));
                        }
                        TT1date = ANCVisit.getString("TT1date");
                        if (ANCVisit.getString("TT2") != null
                                && ANCVisit.getString("TT2").length() > 0
                                && !ANCVisit.getString("TT2").equalsIgnoreCase(
                                "null")) {
                            TT2 = Integer.valueOf(ANCVisit.getInt("TT2"));
                        }
                        TT2date = ANCVisit.getString("TT2date");
                        if (ANCVisit.getString("TTbooster") != null
                                && ANCVisit.getString("TTbooster").length() > 0
                                && !ANCVisit.getString("TTbooster")
                                .equalsIgnoreCase("null")) {
                            TTbooster = Integer.valueOf(ANCVisit
                                    .getInt("TTbooster"));
                        }
                        TTboosterDate1 = ANCVisit.getString("TTboosterDate1");
                        if (ANCVisit.getString("Weight1") != null
                                && ANCVisit.getString("Weight1").length() > 0
                                && !ANCVisit.getString("Weight1").equalsIgnoreCase(
                                "null")) {
                            Weight1 = Float.valueOf(ANCVisit.getInt("Weight1"));
                        }
                        if (ANCVisit.getString("BP1") != null
                                && ANCVisit.getString("BP1").length() > 0
                                && !ANCVisit.getString("BP1").equalsIgnoreCase(
                                "null")) {
                            BP1 = Float.valueOf(ANCVisit.getInt("BP1"));
                        }
                        if (ANCVisit.getString("HB1") != null
                                && ANCVisit.getString("HB1").length() > 0
                                && !ANCVisit.getString("HB1").equalsIgnoreCase(
                                "null")) {
                            HB1 = Integer.valueOf(ANCVisit.getInt("HB1"));
                        }
                        if (ANCVisit.getString("UrineTestsugar1") != null
                                && ANCVisit.getString("UrineTestsugar1").length() > 0
                                && !ANCVisit.getString("UrineTestsugar1")
                                .equalsIgnoreCase("null")) {
                            UrineTestsugar1 = Integer.valueOf(ANCVisit
                                    .getInt("UrineTestsugar1"));
                        }
                        if (ANCVisit.getString("UrineTestAl1") != null
                                && ANCVisit.getString("UrineTestAl1").length() > 0
                                && !ANCVisit.getString("UrineTestAl1")
                                .equalsIgnoreCase("null")) {
                            UrineTestAl1 = Integer.valueOf(ANCVisit
                                    .getInt("UrineTestAl1"));
                        }
                        if (ANCVisit.getString("IronTablet1") != null
                                && ANCVisit.getString("IronTablet1").length() > 0
                                && !ANCVisit.getString("IronTablet1")
                                .equalsIgnoreCase("null")) {
                            IronTablet1 = Integer.valueOf(ANCVisit
                                    .getInt("IronTablet1"));
                        }
                        AncCheckup1 = ANCVisit.getString("AncCheckup1");
                        if (ANCVisit.getString("Weight1YN") != null
                                && ANCVisit.getString("Weight1YN").length() > 0
                                && !ANCVisit.getString("Weight1YN")
                                .equalsIgnoreCase("null")) {
                            Weight1YN = Integer.valueOf(ANCVisit
                                    .getInt("Weight1YN"));
                        }
                        if (ANCVisit.getString("BP1YN") != null
                                && ANCVisit.getString("BP1YN").length() > 0
                                && !ANCVisit.getString("BP1YN").equalsIgnoreCase(
                                "null")) {
                            BP1YN = Integer.valueOf(ANCVisit.getInt("BP1YN"));
                        }
                        if (ANCVisit.getString("HB1YN") != null
                                && ANCVisit.getString("HB1YN").length() > 0
                                && !ANCVisit.getString("HB1YN").equalsIgnoreCase(
                                "null")) {
                            HB1YN = Integer.valueOf(ANCVisit.getInt("HB1YN"));
                        }
                        if (ANCVisit.getString("UrineTestsugar1YN") != null
                                && ANCVisit.getString("UrineTestsugar1YN").length() > 0
                                && !ANCVisit.getString("UrineTestsugar1YN")
                                .equalsIgnoreCase("null")) {
                            UrineTestsugar1YN = Integer.valueOf(ANCVisit
                                    .getInt("UrineTestsugar1YN"));
                        }
                        if (ANCVisit.getString("UrineTestAl1YN") != null
                                && ANCVisit.getString("UrineTestAl1YN").length() > 0
                                && !ANCVisit.getString("UrineTestAl1YN")
                                .equalsIgnoreCase("null")) {
                            UrineTestAl1YN = Integer.valueOf(ANCVisit
                                    .getInt("UrineTestAl1YN"));
                        }
                        if (ANCVisit.getString("IronTablet1YN") != null
                                && ANCVisit.getString("IronTablet1YN").length() > 0
                                && !ANCVisit.getString("IronTablet1YN")
                                .equalsIgnoreCase("null")) {
                            IronTablet1YN = Integer.valueOf(ANCVisit
                                    .getInt("IronTablet1YN"));
                        }
                        if (ANCVisit.getString("AncCheckup1YN") != null
                                && ANCVisit.getString("AncCheckup1YN").length() > 0
                                && !ANCVisit.getString("AncCheckup1YN")
                                .equalsIgnoreCase("null")) {
                            AncCheckup1YN = Integer.valueOf(ANCVisit
                                    .getInt("AncCheckup1YN"));
                        }
                        if (ANCVisit.getString("DeliveryONhospYN") != null
                                && ANCVisit.getString("DeliveryONhospYN").length() > 0
                                && !ANCVisit.getString("DeliveryONhospYN")
                                .equalsIgnoreCase("null")) {
                            DeliveryONhospYN = Integer.valueOf(ANCVisit
                                    .getInt("DeliveryONhospYN"));
                        }
                        if (ANCVisit.getString("FamilyPlanning") != null
                                && ANCVisit.getString("FamilyPlanning").length() > 0
                                && !ANCVisit.getString("FamilyPlanning")
                                .equalsIgnoreCase("null")) {
                            FamilyPlanning = Integer.valueOf(ANCVisit
                                    .getInt("FamilyPlanning"));
                        }
                        if (ANCVisit.getString("DangerSign") != null
                                && ANCVisit.getString("DangerSign").length() > 0
                                && !ANCVisit.getString("DangerSign")
                                .equalsIgnoreCase("null")) {
                            DangerSign = Integer.valueOf(ANCVisit
                                    .getInt("DangerSign"));
                        }
                        if (ANCVisit.getString("CalciumReceived") != null
                                && ANCVisit.getString("CalciumReceived").length() > 0
                                && !ANCVisit.getString("CalciumReceived")
                                .equalsIgnoreCase("null")) {
                            CalciumReceived = Integer.valueOf(ANCVisit
                                    .getInt("CalciumReceived"));
                        }
                        if (ANCVisit.getString("CalciumTablet") != null
                                && ANCVisit.getString("CalciumTablet").length() > 0
                                && !ANCVisit.getString("CalciumTablet")
                                .equalsIgnoreCase("null")) {
                            CalciumTablet = Integer.valueOf(ANCVisit
                                    .getInt("CalciumTablet"));
                        }

                        PWGUID = ANCVisit.getString("PWGUID");
                        VisitGUID = ANCVisit.getString("VisitGUID");
                        MCTSID = ANCVisit.getString("MCTSID");
                        VisitDueDate = ANCVisit.getString("VisitDueDate");
                        CheckupVisitDate = ANCVisit.getString("CheckupVisitDate");
                        BPResult = ANCVisit.getString("BPResult");
                        TTfirstDoseDate = ANCVisit.getString("TTfirstDoseDate");
                        TTsecondDoseDate = ANCVisit.getString("TTsecondDoseDate");
                        TTBoosterDate = ANCVisit.getString("TTboosterDate");
                        CreatedOn = ANCVisit.getString("CreatedOn");
                        UpdatedOn = ANCVisit.getString("UpdatedOn");
                        HomeVisitDate = ANCVisit.getString("HomeVisitDate");

                        if (ANCVisit.getString("ByANMID") != null
                                && ANCVisit.getString("ByANMID").length() > 0
                                && !ANCVisit.getString("ByANMID").equalsIgnoreCase(
                                "null")) {
                            ByANMID = Integer.valueOf(ANCVisit.getInt("ByANMID"));
                        }
                        if (ANCVisit.getString("ByAshaID") != null
                                && ANCVisit.getString("ByAshaID").length() > 0
                                && !ANCVisit.getString("ByAshaID")
                                .equalsIgnoreCase("null")) {
                            ByAshaID = Integer.valueOf(ANCVisit.getInt("ByAshaID"));
                        }
                        if (ANCVisit.getString("Visit_No") != null
                                && ANCVisit.getString("Visit_No").length() > 0
                                && !ANCVisit.getString("Visit_No")
                                .equalsIgnoreCase("null")) {
                            Visit_No = Integer.valueOf(ANCVisit.getInt("Visit_No"));
                        }
                        if (ANCVisit.getString("Trimester") != null
                                && ANCVisit.getString("Trimester").length() > 0
                                && !ANCVisit.getString("Trimester")
                                .equalsIgnoreCase("null")) {
                            Trimester = Integer.valueOf(ANCVisit
                                    .getInt("Trimester"));
                        }
                        if (ANCVisit.getString("CheckupPlace") != null
                                && ANCVisit.getString("CheckupPlace").length() > 0
                                && !ANCVisit.getString("CheckupPlace")
                                .equalsIgnoreCase("null")) {
                            CheckupPlace = Integer.valueOf(ANCVisit
                                    .getInt("CheckupPlace"));
                        }
                        if (ANCVisit.getString("BirthWeight") != null
                                && ANCVisit.getString("BirthWeight").length() > 0
                                && !ANCVisit.getString("BirthWeight")
                                .equalsIgnoreCase("null")) {
                            BirthWeight = Double.valueOf(ANCVisit
                                    .getInt("BirthWeight"));
                        }
                        if (ANCVisit.getString("BP") != null
                                && ANCVisit.getString("BP").length() > 0
                                && !ANCVisit.getString("BP").equalsIgnoreCase(
                                "null")) {
                            BP = Integer.valueOf(ANCVisit.getInt("BP"));
                        }
                        if (ANCVisit.getString("Hemoglobin") != null
                                && ANCVisit.getString("Hemoglobin").length() > 0
                                && !ANCVisit.getString("Hemoglobin")
                                .equalsIgnoreCase("null")) {
                            Hemoglobin = Double.valueOf(ANCVisit
                                    .getInt("Hemoglobin"));
                        }
                        if (ANCVisit.getString("UrineTest") != null
                                && ANCVisit.getString("UrineTest").length() > 0
                                && !ANCVisit.getString("UrineTest")
                                .equalsIgnoreCase("null")) {
                            UrineTest = Integer.valueOf(ANCVisit
                                    .getInt("UrineTest"));
                        }
                        if (ANCVisit.getString("UrineSugar") != null
                                && ANCVisit.getString("UrineSugar").length() > 0
                                && !ANCVisit.getString("UrineSugar")
                                .equalsIgnoreCase("null")) {
                            UrineSugar = Integer.valueOf(ANCVisit
                                    .getInt("UrineSugar"));
                        }
                        if (ANCVisit.getString("UrineAlbumin") != null
                                && ANCVisit.getString("UrineAlbumin").length() > 0
                                && !ANCVisit.getString("UrineAlbumin")
                                .equalsIgnoreCase("null")) {
                            UrineAlbumin = Integer.valueOf(ANCVisit
                                    .getInt("UrineAlbumin"));
                        }
                        if (ANCVisit.getString("VDRLTest") != null
                                && ANCVisit.getString("VDRLTest").length() > 0
                                && !ANCVisit.getString("VDRLTest")
                                .equalsIgnoreCase("null")) {
                            VDRLTest = Integer.valueOf(ANCVisit.getInt("VDRLTest"));
                        }
                        if (ANCVisit.getString("HIVTest") != null
                                && ANCVisit.getString("HIVTest").length() > 0
                                && !ANCVisit.getString("HIVTest").equalsIgnoreCase(
                                "null")) {
                            HIVTest = Integer.valueOf(ANCVisit.getInt("HIVTest"));
                        }
                        if (ANCVisit.getString("UltraSound") != null
                                && ANCVisit.getString("UltraSound").length() > 0
                                && !ANCVisit.getString("UltraSound")
                                .equalsIgnoreCase("null")) {
                            UltraSound = Integer.valueOf(ANCVisit
                                    .getInt("UltraSound"));
                        }
                        if (ANCVisit.getString("UltraSoundConductedby") != null
                                && ANCVisit.getString("UltraSoundConductedby")
                                .length() > 0
                                && !ANCVisit.getString("UltraSoundConductedby")
                                .equalsIgnoreCase("null")) {
                            UltraSoundConductedby = Integer.valueOf(ANCVisit
                                    .getInt("UltraSoundConductedby"));
                        }
                        if (ANCVisit.getString("IFARecieved") != null
                                && ANCVisit.getString("IFARecieved").length() > 0
                                && !ANCVisit.getString("IFARecieved")
                                .equalsIgnoreCase("null")) {
                            IFARecieved = Integer.valueOf(ANCVisit
                                    .getInt("IFARecieved"));
                        }
                        if (ANCVisit.getString("NumberIFARecieved") != null
                                && ANCVisit.getString("NumberIFARecieved").length() > 0
                                && !ANCVisit.getString("NumberIFARecieved")
                                .equalsIgnoreCase("null")) {
                            NumberIFARecieved = Integer.valueOf(ANCVisit
                                    .getInt("NumberIFARecieved"));
                        }
                        if (ANCVisit.getString("CreatedBy") != null
                                && ANCVisit.getString("CreatedBy").length() > 0
                                && !ANCVisit.getString("CreatedBy")
                                .equalsIgnoreCase("null")) {
                            CreatedBy = Integer.valueOf(ANCVisit
                                    .getInt("CreatedBy"));
                        }
                        if (ANCVisit.getString("UpdatedBy") != null
                                && ANCVisit.getString("UpdatedBy").length() > 0
                                && !ANCVisit.getString("UpdatedBy")
                                .equalsIgnoreCase("null")) {
                            UpdatedBy = Integer.valueOf(ANCVisit
                                    .getInt("UpdatedBy"));
                        }
                        if (ANCVisit.getString("UltrasoundResult") != null
                                && ANCVisit.getString("UltrasoundResult").length() > 0
                                && !ANCVisit.getString("UltrasoundResult")
                                .equalsIgnoreCase("null")) {
                            UltrasoundResult = Integer.valueOf(ANCVisit
                                    .getInt("UltrasoundResult"));
                        }
                        if (ANCVisit.getString("TT1TT2last2yr") != null
                                && ANCVisit.getString("TT1TT2last2yr").length() > 0
                                && !ANCVisit.getString("TT1TT2last2yr")
                                .equalsIgnoreCase("null")) {
                            TT1TT2last2yr = Integer.valueOf(ANCVisit
                                    .getInt("TT1TT2last2yr"));
                        }
                        if (ANCVisit.getString("HighRisk") != null
                                && ANCVisit.getString("HighRisk").length() > 0
                                && !ANCVisit.getString("HighRisk")
                                .equalsIgnoreCase("null")) {
                            HighRisk = ANCVisit
                                    .getInt("HighRisk");
                        }
                        if (ANCVisit.getString("HighRiskDate") != null
                                && ANCVisit.getString("HighRiskDate").length() > 0
                                && !ANCVisit.getString("HighRiskDate")
                                .equalsIgnoreCase("null")) {
                            HighRiskDate = ANCVisit
                                    .getString("HighRiskDate");
                        }

                        String sqlcount = "select count(*) from TblANCVisit  where VisitGUID='"
                                + VisitGUID + "' and PWGUID='" + PWGUID + "'";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from TblANCVisit  where VisitGUID='"
                                + VisitGUID
                                + "' and PWGUID='"
                                + PWGUID
                                + "' and IsEdited=1";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);

                        String sql = "";
                        if (count == 0) {

                            sql = "Insert into TblANCVisit(TT1TT2last2yr,PWGUID,VisitGUID,ByANMID,ByAshaID,MCTSID,Visit_No,Trimester,VisitDueDate,CheckupVisitDate,CheckupPlace,BirthWeight,BP,BPResult,Hemoglobin,UrineTest,UrineSugar,UrineAlbumin,TTfirstDoseDate,TTsecondDoseDate,TTBoosterDate,VDRLTest,HIVTest,UltraSound,UltraSoundConductedby,IFARecieved,NumberIFARecieved,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,UltrasoundResult,HomeVisitDate,IsEdited,IsUploaded,AncVisitID,PregWomenReg,McpCard,TT1,TT1date,TT2,TT2date,TTbooster,TTboosterDate1,Weight1,BP1,HB1,UrineTestsugar1,UrineTestAl1,IronTablet1,AncCheckup1,Weight1YN,BP1YN,HB1YN,UrineTestsugar1YN,UrineTestAl1YN,IronTablet1YN,AncCheckup1YN,DeliveryONhospYN,FamilyPlanning,DangerSign,CalciumReceived,CalciumTablet,HighRisk,HighRiskDate)values("
                                    + TT1TT2last2yr
                                    + ",'"
                                    + PWGUID
                                    + "','"
                                    + VisitGUID
                                    + "',"
                                    + ByANMID
                                    + ","
                                    + ByAshaID
                                    + ",'"
                                    + MCTSID
                                    + "',"
                                    + Visit_No
                                    + ","
                                    + Trimester
                                    + ",'"
                                    + VisitDueDate
                                    + "','"
                                    + CheckupVisitDate
                                    + "',"
                                    + CheckupPlace
                                    + ","
                                    + BirthWeight
                                    + ","
                                    + BP
                                    + ",'"
                                    + BPResult
                                    + "',"
                                    + Hemoglobin
                                    + ","
                                    + UrineTest
                                    + ","
                                    + UrineSugar
                                    + ","
                                    + UrineAlbumin
                                    + ",'"
                                    + TTfirstDoseDate
                                    + "','"
                                    + TTsecondDoseDate
                                    + "','"
                                    + TTBoosterDate
                                    + "',"
                                    + VDRLTest
                                    + ","
                                    + HIVTest
                                    + ","
                                    + UltraSound
                                    + ","
                                    + UltraSoundConductedby
                                    + ","
                                    + IFARecieved
                                    + ","
                                    + NumberIFARecieved
                                    + ",'"
                                    + CreatedOn
                                    + "',"
                                    + CreatedBy
                                    + ",'"
                                    + UpdatedOn
                                    + "',"
                                    + UpdatedBy
                                    + ","
                                    + UltrasoundResult
                                    + ",'"
                                    + HomeVisitDate
                                    + "',0,0,"
                                    + AncVisitID
                                    + ","
                                    + PregWomenReg
                                    + ","
                                    + McpCard
                                    + ","
                                    + TT1
                                    + ",'"
                                    + TT1date
                                    + "',"
                                    + TT2
                                    + ",'"
                                    + TT2date
                                    + "',"
                                    + TTbooster
                                    + ",'"
                                    + TTboosterDate1
                                    + "',"
                                    + Weight1
                                    + ","
                                    + BP1
                                    + ","
                                    + HB1
                                    + ","
                                    + UrineTestsugar1
                                    + ","
                                    + UrineTestAl1
                                    + ","
                                    + IronTablet1
                                    + ",'"
                                    + AncCheckup1
                                    + "',"
                                    + Weight1YN
                                    + ","
                                    + BP1YN
                                    + ","
                                    + HB1YN
                                    + ","
                                    + UrineTestsugar1YN
                                    + ","
                                    + UrineTestAl1YN
                                    + ","
                                    + IronTablet1YN
                                    + ","
                                    + AncCheckup1YN
                                    + ","
                                    + DeliveryONhospYN
                                    + ","
                                    + FamilyPlanning
                                    + ","
                                    + DangerSign
                                    + ","
                                    + CalciumReceived
                                    + "," + CalciumTablet + "," + HighRisk + ",'" + HighRiskDate + "')";
                            dataProvider.executeSql(sql);
                        } else {
                            if (count1 == 0) {
                                sql = "update TblANCVisit set TT1TT2last2yr="
                                        + TT1TT2last2yr + ", ByANMID=" + ByANMID
                                        + ",ByAshaID=" + ByAshaID + ",MCTSID='"
                                        + MCTSID + "',Visit_No=" + Visit_No
                                        + ",Trimester=" + Trimester
                                        + ",VisitDueDate='" + VisitDueDate
                                        + "',CheckupVisitDate='" + CheckupVisitDate
                                        + "',CheckupPlace=" + CheckupPlace
                                        + ",BirthWeight=" + BirthWeight + ",BP="
                                        + BP + ",BPResult='" + BPResult
                                        + "',Hemoglobin=" + Hemoglobin
                                        + ",UrineTest=" + UrineTest
                                        + ",UrineSugar=" + UrineSugar
                                        + ",UrineAlbumin=" + UrineAlbumin
                                        + ",TTfirstDoseDate='" + TTfirstDoseDate
                                        + "',TTsecondDoseDate='" + TTsecondDoseDate
                                        + "',TTBoosterDate='" + TTBoosterDate
                                        + "',VDRLTest=" + VDRLTest + ",HIVTest="
                                        + HIVTest + ",UltraSound=" + UltraSound
                                        + ",UltraSoundConductedby="
                                        + UltraSoundConductedby + ",IFARecieved="
                                        + IFARecieved + ",NumberIFARecieved="
                                        + NumberIFARecieved + ",CreatedOn='"
                                        + CreatedOn + "',CreatedBy=" + CreatedBy
                                        + ",UpdatedOn='" + UpdatedOn
                                        + "',IsEdited=0,IsUploaded=0,UpdatedBy="
                                        + UpdatedBy + ",UltrasoundResult="
                                        + UltrasoundResult + ",HomeVisitDate='"
                                        + HomeVisitDate + "',AncVisitID="
                                        + AncVisitID + ",PregWomenReg="
                                        + PregWomenReg + ",McpCard=" + McpCard
                                        + ",TT1=" + TT1 + ",TT1date='" + TT1date
                                        + "',TT2=" + TT2 + ",TT2date='" + TT2date
                                        + "',TTbooster=" + TTbooster
                                        + ",TTboosterDate1='" + TTboosterDate1
                                        + "',Weight1=" + Weight1 + ",BP1=" + BP1
                                        + ",HB1=" + HB1 + ",UrineTestsugar1="
                                        + UrineTestsugar1 + ",UrineTestAl1="
                                        + UrineTestAl1 + ",IronTablet1="
                                        + IronTablet1 + ",AncCheckup1='"
                                        + AncCheckup1 + "',Weight1YN=" + Weight1YN
                                        + ",BP1YN=" + BP1YN + ",HB1YN=" + HB1YN
                                        + ",UrineTestsugar1YN=" + UrineTestsugar1YN
                                        + ",UrineTestAl1YN=" + UrineTestAl1YN
                                        + ",IronTablet1YN=" + IronTablet1YN
                                        + ",AncCheckup1YN=" + AncCheckup1YN
                                        + ",DeliveryONhospYN=" + DeliveryONhospYN
                                        + ",FamilyPlanning=" + FamilyPlanning
                                        + ",DangerSign=" + DangerSign
                                        + ",CalciumReceived=" + CalciumReceived
                                        + ",CalciumTablet=" + CalciumTablet
                                        + ",HighRisk=" + HighRisk
                                        + ",HighRiskDate='" + HighRiskDate
                                        + "' where PWGUID='" + PWGUID
                                        + "' and VisitGUID='" + VisitGUID + "'";
                                dataProvider.executeSql(sql);
                            }
                        }
                    }


                    tblPNChomevisit_ANSArray = jsonObj
                            .getJSONArray("tblpnchomevisit_ans");
                    for (int i = 0; i < tblPNChomevisit_ANSArray.length(); i++) {
                        JSONObject q_bankData = tblPNChomevisit_ANSArray
                                .getJSONObject(i);
                        String PWGUID = "";
                        String ChildGUID = "";
                        String PNCGUID = "";
                        int VisitNo = 0;
                        String Q_0 = "";
                        int Q_1 = 0;
                        String Q_2 = "";
                        String Q_3 = "";
                        int Q_4 = 0;
                        int Q_5 = 0;
                        String Q_6 = "";
                        String Q_7 = "";
                        int Q_8 = 0;
                        int Q_9 = 0;
                        int Q_10 = 0;
                        int Q_12 = 0;
                        int Q_13 = 0;
                        int Q_14 = 0;
                        int Q_15 = 0;
                        int Q_16 = 0;
                        int Q_17 = 0;
                        float Q_18 = 0;
                        int Q_19 = 0;
                        int Q_20 = 0;
                        float Q_21 = 0;
                        int Q_22 = 0;
                        int Q_23 = 0;
                        int Q_24 = 0;
                        int Q_25 = 0;
                        int Q_26 = 0;
                        int Q_27 = 0;
                        int Q_28 = 0;
                        int Q_29 = 0;
                        int Q_30 = 0;
                        int Q_32 = 0;
                        int Q_33 = 0;
                        int Q_34 = 0;
                        int Q_35 = 0;
                        int Q_36 = 0;
                        int Q_37 = 0;
                        int Q_38 = 0;
                        int Q_38a = 0;
                        int Q_39 = 0;
                        int Q_40 = 0;
                        int Q_41 = 0;
                        int Q_43 = 0;
                        String Q_44 = "";
                        int Q_45 = 0;
                        int Q_46 = 0;
                        int Q_47 = 0;
                        String Q_48 = "";
                        int Q_49 = 0;
                        int Q_50 = 0;
                        int Q_52 = 0;
                        int Q_53 = 0;
                        int Q_54 = 0;
                        int Q_55 = 0;
                        int Q_56 = 0;
                        int Q_57 = 0;
                        int Q_58 = 0;
                        int Q_59 = 0;
                        int Q_60 = 0;
                        int CreatedBy = 0;
                        String CreatedOn = "";
                        int UpdatedBy = 0;
                        int ANMID = 0;
                        int AshaID = 0;
                        String UpdatedOn = "";
                        String Time = "";

                        if (q_bankData.getString("Q_1") != null
                                && q_bankData.getString("Q_1").length() > 0
                                && !q_bankData.getString("Q_1").equalsIgnoreCase(
                                "null")) {
                            Q_1 = Integer.valueOf(q_bankData.getInt("Q_1"));
                        }

                        if (q_bankData.getString("Q_4") != null
                                && q_bankData.getString("Q_4").length() > 0
                                && !q_bankData.getString("Q_4").equalsIgnoreCase(
                                "null")) {
                            Q_4 = Integer.valueOf(q_bankData.getInt("Q_4"));
                        }

                        if (q_bankData.getString("Q_5") != null
                                && q_bankData.getString("Q_5").length() > 0
                                && !q_bankData.getString("Q_5").equalsIgnoreCase(
                                "null")) {
                            Q_5 = Integer.valueOf(q_bankData.getInt("Q_5"));
                        }

                        if (q_bankData.getString("Q_8") != null
                                && q_bankData.getString("Q_8").length() > 0
                                && !q_bankData.getString("Q_8").equalsIgnoreCase(
                                "null")) {
                            Q_8 = Integer.valueOf(q_bankData.getInt("Q_8"));
                        }
                        if (q_bankData.getString("Q_9") != null
                                && q_bankData.getString("Q_9").length() > 0
                                && !q_bankData.getString("Q_9").equalsIgnoreCase(
                                "null")) {
                            Q_9 = Integer.valueOf(q_bankData.getInt("Q_9"));
                        }
                        if (q_bankData.getString("Q_10") != null
                                && q_bankData.getString("Q_10").length() > 0
                                && !q_bankData.getString("Q_10").equalsIgnoreCase(
                                "null")) {
                            Q_10 = Integer.valueOf(q_bankData.getInt("Q_10"));
                        }
                        if (q_bankData.getString("Q_12") != null
                                && q_bankData.getString("Q_12").length() > 0
                                && !q_bankData.getString("Q_12").equalsIgnoreCase(
                                "null")) {
                            Q_12 = Integer.valueOf(q_bankData.getInt("Q_12"));
                        }
                        if (q_bankData.getString("Q_13") != null
                                && q_bankData.getString("Q_13").length() > 0
                                && !q_bankData.getString("Q_13").equalsIgnoreCase(
                                "null")) {
                            Q_13 = Integer.valueOf(q_bankData.getInt("Q_13"));
                        }

                        if (q_bankData.getString("Q_14") != null
                                && q_bankData.getString("Q_14").length() > 0
                                && !q_bankData.getString("Q_14").equalsIgnoreCase(
                                "null")) {
                            Q_14 = Integer.valueOf(q_bankData.getInt("Q_14"));
                        }

                        if (q_bankData.getString("Q_15") != null
                                && q_bankData.getString("Q_15").length() > 0
                                && !q_bankData.getString("Q_15").equalsIgnoreCase(
                                "null")) {
                            Q_15 = Integer.valueOf(q_bankData.getInt("Q_15"));
                        }

                        if (q_bankData.getString("Q_16") != null
                                && q_bankData.getString("Q_16").length() > 0
                                && !q_bankData.getString("Q_16").equalsIgnoreCase(
                                "null")) {
                            Q_16 = Integer.valueOf(q_bankData.getInt("Q_16"));
                        }

                        if (q_bankData.getString("Q_17") != null
                                && q_bankData.getString("Q_17").length() > 0
                                && !q_bankData.getString("Q_17").equalsIgnoreCase(
                                "null")) {
                            Q_17 = Integer.valueOf(q_bankData.getInt("Q_17"));
                        }

                        if (q_bankData.getString("Q_18") != null
                                && q_bankData.getString("Q_18").length() > 0
                                && !q_bankData.getString("Q_18").equalsIgnoreCase(
                                "null")) {
                            Q_18 = Float.valueOf(q_bankData.getString("Q_18"));
                        }

                        if (q_bankData.getString("Q_19") != null
                                && q_bankData.getString("Q_19").length() > 0
                                && !q_bankData.getString("Q_19").equalsIgnoreCase(
                                "null")) {
                            Q_19 = Integer.valueOf(q_bankData.getInt("Q_19"));
                        }

                        if (q_bankData.getString("Q_20") != null
                                && q_bankData.getString("Q_20").length() > 0
                                && !q_bankData.getString("Q_20").equalsIgnoreCase(
                                "null")) {
                            Q_20 = Integer.valueOf(q_bankData.getInt("Q_20"));
                        }

                        if (q_bankData.getString("Q_22") != null
                                && q_bankData.getString("Q_22").length() > 0
                                && !q_bankData.getString("Q_22").equalsIgnoreCase(
                                "null")) {
                            Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
                        }
                        if (q_bankData.getString("Q_23") != null
                                && q_bankData.getString("Q_23").length() > 0
                                && !q_bankData.getString("Q_23").equalsIgnoreCase(
                                "null")) {
                            Q_23 = Integer.valueOf(q_bankData.getInt("Q_23"));
                        }
                        if (q_bankData.getString("Q_24") != null
                                && q_bankData.getString("Q_24").length() > 0
                                && !q_bankData.getString("Q_24").equalsIgnoreCase(
                                "null")) {
                            Q_24 = Integer.valueOf(q_bankData.getInt("Q_24"));
                        }
                        if (q_bankData.getString("Q_25") != null
                                && q_bankData.getString("Q_25").length() > 0
                                && !q_bankData.getString("Q_25").equalsIgnoreCase(
                                "null")) {
                            Q_25 = Integer.valueOf(q_bankData.getInt("Q_25"));
                        }
                        if (q_bankData.getString("Q_26") != null
                                && q_bankData.getString("Q_26").length() > 0
                                && !q_bankData.getString("Q_26").equalsIgnoreCase(
                                "null")) {
                            Q_26 = Integer.valueOf(q_bankData.getInt("Q_26"));
                        }
                        if (q_bankData.getString("Q_27") != null
                                && q_bankData.getString("Q_27").length() > 0
                                && !q_bankData.getString("Q_27").equalsIgnoreCase(
                                "null")) {
                            Q_27 = Integer.valueOf(q_bankData.getInt("Q_27"));
                        }
                        if (q_bankData.getString("Q_28") != null
                                && q_bankData.getString("Q_28").length() > 0
                                && !q_bankData.getString("Q_28").equalsIgnoreCase(
                                "null")) {
                            Q_28 = Integer.valueOf(q_bankData.getInt("Q_28"));
                        }
                        if (q_bankData.getString("Q_29") != null
                                && q_bankData.getString("Q_29").length() > 0
                                && !q_bankData.getString("Q_29").equalsIgnoreCase(
                                "null")) {
                            Q_29 = Integer.valueOf(q_bankData.getInt("Q_29"));
                        }
                        if (q_bankData.getString("Q_30") != null
                                && q_bankData.getString("Q_30").length() > 0
                                && !q_bankData.getString("Q_30").equalsIgnoreCase(
                                "null")) {
                            Q_30 = Integer.valueOf(q_bankData.getInt("Q_30"));
                        }
                        if (q_bankData.getString("Q_32") != null
                                && q_bankData.getString("Q_32").length() > 0
                                && !q_bankData.getString("Q_32").equalsIgnoreCase(
                                "null")) {
                            Q_32 = Integer.valueOf(q_bankData.getInt("Q_32"));
                        }
                        if (q_bankData.getString("Q_33") != null
                                && q_bankData.getString("Q_33").length() > 0
                                && !q_bankData.getString("Q_33").equalsIgnoreCase(
                                "null")) {
                            Q_33 = Integer.valueOf(q_bankData.getInt("Q_33"));
                        }
                        if (q_bankData.getString("Q_22") != null
                                && q_bankData.getString("Q_22").length() > 0
                                && !q_bankData.getString("Q_22").equalsIgnoreCase(
                                "null")) {
                            Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
                        }
                        if (q_bankData.getString("Q_34") != null
                                && q_bankData.getString("Q_34").length() > 0
                                && !q_bankData.getString("Q_34").equalsIgnoreCase(
                                "null")) {
                            Q_34 = Integer.valueOf(q_bankData.getInt("Q_34"));
                        }
                        if (q_bankData.getString("Q_35") != null
                                && q_bankData.getString("Q_35").length() > 0
                                && !q_bankData.getString("Q_35").equalsIgnoreCase(
                                "null")) {
                            Q_35 = Integer.valueOf(q_bankData.getInt("Q_35"));
                        }
                        if (q_bankData.getString("Q_36") != null
                                && q_bankData.getString("Q_36").length() > 0
                                && !q_bankData.getString("Q_36").equalsIgnoreCase(
                                "null")) {
                            Q_36 = Integer.valueOf(q_bankData.getInt("Q_36"));
                        }
                        if (q_bankData.getString("Q_37") != null
                                && q_bankData.getString("Q_37").length() > 0
                                && !q_bankData.getString("Q_37").equalsIgnoreCase(
                                "null")) {
                            Q_37 = Integer.valueOf(q_bankData.getInt("Q_37"));
                        }
                        if (q_bankData.getString("Q_38") != null
                                && q_bankData.getString("Q_38").length() > 0
                                && !q_bankData.getString("Q_38").equalsIgnoreCase(
                                "null")) {
                            Q_38 = Integer.valueOf(q_bankData.getInt("Q_38"));
                        }
                        if (q_bankData.getString("Q_38A") != null
                                && q_bankData.getString("Q_38A").length() > 0
                                && !q_bankData.getString("Q_38A").equalsIgnoreCase(
                                "null")) {
                            Q_38a = Integer.valueOf(q_bankData.getInt("Q_38A"));
                        }
                        if (q_bankData.getString("Q_39") != null
                                && q_bankData.getString("Q_39").length() > 0
                                && !q_bankData.getString("Q_39").equalsIgnoreCase(
                                "null")) {
                            Q_39 = Integer.valueOf(q_bankData.getInt("Q_39"));
                        }
                        if (q_bankData.getString("Q_40") != null
                                && q_bankData.getString("Q_40").length() > 0
                                && !q_bankData.getString("Q_40").equalsIgnoreCase(
                                "null")) {
                            Q_40 = Integer.valueOf(q_bankData.getInt("Q_40"));
                        }
                        if (q_bankData.getString("Q_41") != null
                                && q_bankData.getString("Q_41").length() > 0
                                && !q_bankData.getString("Q_41").equalsIgnoreCase(
                                "null")) {
                            Q_41 = Integer.valueOf(q_bankData.getInt("Q_41"));
                        }
                        if (q_bankData.getString("Q_43") != null
                                && q_bankData.getString("Q_43").length() > 0
                                && !q_bankData.getString("Q_43").equalsIgnoreCase(
                                "null")) {
                            Q_43 = Integer.valueOf(q_bankData.getInt("Q_43"));
                        }
                        if (q_bankData.getString("Q_45") != null
                                && q_bankData.getString("Q_45").length() > 0
                                && !q_bankData.getString("Q_45").equalsIgnoreCase(
                                "null")) {
                            Q_45 = Integer.valueOf(q_bankData.getInt("Q_45"));
                        }
                        if (q_bankData.getString("Q_46") != null
                                && q_bankData.getString("Q_46").length() > 0
                                && !q_bankData.getString("Q_46").equalsIgnoreCase(
                                "null")) {
                            Q_46 = Integer.valueOf(q_bankData.getInt("Q_46"));
                        }
                        if (q_bankData.getString("Q_47") != null
                                && q_bankData.getString("Q_47").length() > 0
                                && !q_bankData.getString("Q_47").equalsIgnoreCase(
                                "null")) {
                            Q_47 = Integer.valueOf(q_bankData.getInt("Q_47"));
                        }
                        if (q_bankData.getString("Q_49") != null
                                && q_bankData.getString("Q_49").length() > 0
                                && !q_bankData.getString("Q_49").equalsIgnoreCase(
                                "null")) {
                            Q_49 = Integer.valueOf(q_bankData.getInt("Q_49"));
                        }
                        if (q_bankData.getString("Q_50") != null
                                && q_bankData.getString("Q_50").length() > 0
                                && !q_bankData.getString("Q_50").equalsIgnoreCase(
                                "null")) {
                            Q_50 = Integer.valueOf(q_bankData.getInt("Q_50"));
                        }
                        if (q_bankData.getString("Q_52") != null
                                && q_bankData.getString("Q_52").length() > 0
                                && !q_bankData.getString("Q_52").equalsIgnoreCase(
                                "null")) {
                            Q_52 = Integer.valueOf(q_bankData.getInt("Q_52"));
                        }
                        if (q_bankData.getString("Q_53") != null
                                && q_bankData.getString("Q_53").length() > 0
                                && !q_bankData.getString("Q_53").equalsIgnoreCase(
                                "null")) {
                            Q_53 = Integer.valueOf(q_bankData.getInt("Q_53"));
                        }
                        if (q_bankData.getString("Q_54") != null
                                && q_bankData.getString("Q_54").length() > 0
                                && !q_bankData.getString("Q_54").equalsIgnoreCase(
                                "null")) {
                            Q_54 = Integer.valueOf(q_bankData.getInt("Q_54"));
                        }
                        if (q_bankData.getString("Q_55") != null
                                && q_bankData.getString("Q_55").length() > 0
                                && !q_bankData.getString("Q_55").equalsIgnoreCase(
                                "null")) {
                            Q_55 = Integer.valueOf(q_bankData.getInt("Q_55"));
                        }
                        if (q_bankData.getString("Q_56") != null
                                && q_bankData.getString("Q_56").length() > 0
                                && !q_bankData.getString("Q_56").equalsIgnoreCase(
                                "null")) {
                            Q_56 = Integer.valueOf(q_bankData.getInt("Q_56"));
                        }
                        if (q_bankData.getString("Q_57") != null
                                && q_bankData.getString("Q_57").length() > 0
                                && !q_bankData.getString("Q_57").equalsIgnoreCase(
                                "null")) {
                            Q_57 = Integer.valueOf(q_bankData.getInt("Q_57"));
                        }
                        if (q_bankData.getString("Q_58") != null
                                && q_bankData.getString("Q_58").length() > 0
                                && !q_bankData.getString("Q_58").equalsIgnoreCase(
                                "null")) {
                            Q_58 = Integer.valueOf(q_bankData.getInt("Q_58"));
                        }
                        if (q_bankData.getString("Q_59") != null
                                && q_bankData.getString("Q_59").length() > 0
                                && !q_bankData.getString("Q_59").equalsIgnoreCase(
                                "null")) {
                            Q_59 = Integer.valueOf(q_bankData.getInt("Q_59"));
                        }
                        if (q_bankData.getString("Q_60") != null
                                && q_bankData.getString("Q_60").length() > 0
                                && !q_bankData.getString("Q_60").equalsIgnoreCase(
                                "null")) {
                            Q_60 = Integer.valueOf(q_bankData.getInt("Q_60"));
                        }
                        if (q_bankData.getString("Q_21") != null
                                && q_bankData.getString("Q_21").length() > 0
                                && !q_bankData.getString("Q_21").equalsIgnoreCase(
                                "null")) {
                            Q_21 = Integer.valueOf(q_bankData.getInt("Q_21"));
                        }
                        if (q_bankData.getString("ANMID") != null
                                && q_bankData.getString("ANMID").length() > 0
                                && !q_bankData.getString("ANMID").equalsIgnoreCase(
                                "null")) {
                            ANMID = Integer.valueOf(q_bankData.getInt("ANMID"));
                        }
                        if (q_bankData.getString("AshaID") != null
                                && q_bankData.getString("AshaID").length() > 0
                                && !q_bankData.getString("AshaID")
                                .equalsIgnoreCase("null")) {
                            AshaID = Integer.valueOf(q_bankData.getInt("AshaID"));
                        }

                        if (q_bankData.getString("CreatedBy") != null
                                && q_bankData.getString("CreatedBy").length() > 0
                                && !q_bankData.getString("CreatedBy")
                                .equalsIgnoreCase("null")) {
                            CreatedBy = Integer.valueOf(q_bankData
                                    .getInt("CreatedBy"));
                        }
                        if (q_bankData.getString("UpdatedBy") != null
                                && q_bankData.getString("UpdatedBy").length() > 0
                                && !q_bankData.getString("UpdatedBy")
                                .equalsIgnoreCase("null")) {
                            UpdatedBy = Integer.valueOf(q_bankData
                                    .getInt("UpdatedBy"));
                        }
                        if (q_bankData.getString("VisitNo") != null
                                && q_bankData.getString("VisitNo").length() > 0
                                && !q_bankData.getString("VisitNo")
                                .equalsIgnoreCase("null")) {
                            VisitNo = Integer.valueOf(q_bankData.getInt("VisitNo"));
                        }

                        Q_0 = q_bankData.getString("Q_0");
                        Q_2 = q_bankData.getString("Q_2");
                        Q_3 = q_bankData.getString("Q_3");

                        Q_6 = q_bankData.getString("Q_6");
                        Q_7 = q_bankData.getString("Q_7");
                        Q_44 = q_bankData.getString("Q_44");
                        Q_48 = q_bankData.getString("Q_48");
                        ChildGUID = q_bankData.getString("ChildGUID");
                        PNCGUID = q_bankData.getString("PNCGUID");

                        PWGUID = q_bankData.getString("PWGUID");
                        CreatedOn = q_bankData.getString("CreatedOn");
                        UpdatedOn = q_bankData.getString("UpdatedOn");
                        Time = q_bankData.getString("Createtime");

                        String sqlcount = "select count(*) from tblPNChomevisit_ANS where PNCGUID='"
                                + PNCGUID
                                + "' and ChildGUID='"
                                + ChildGUID
                                + "' and VisitNo=" + VisitNo + "";
                        int count = dataProvider.getMaxRecord(sqlcount);
                        String sqlcount1 = "select count(*) from tblPNChomevisit_ANS where PNCGUID='"
                                + PNCGUID
                                + "' and ChildGUID='"
                                + ChildGUID
                                + "' and IsEdited=1 and visitno=" + VisitNo + "";
                        int count1 = dataProvider.getMaxRecord(sqlcount1);
                        String sql = "";
                        if (count == 0) {
                            sql = "insert into tblPNChomevisit_ANS (AshaID,ANMID,PWGUID,ChildGUID,PNCGUID,VisitNo,Q_0,Q_1,Q_2,Q_3,Q_4,Q_5,Q_6,Q_7,Q_8,Q_9,Q_10,Q_12,Q_13,Q_14,Q_15,Q_16,Q_17,Q_18,Q_19,Q_20,Q_21,Q_22,Q_23,Q_24,Q_25,Q_26,Q_27,Q_28,Q_29,Q_30,Q_32,Q_33,Q_34,Q_35,Q_36,Q_37,Q_38,Q_38a,Q_39,Q_40,Q_41,Q_43,Q_44,Q_45,Q_46,Q_47,Q_48,Q_49,Q_50,Q_52,Q_53,Q_54,Q_55,Q_56,Q_57,Q_58,Q_59,Q_60,CreatedBy,CreatedOn,UpdatedBy,UpdatedOn,IsEdited,Createtime)values("
                                    + AshaID
                                    + ","
                                    + ANMID
                                    + ",'"
                                    + PWGUID
                                    + "','"
                                    + ChildGUID
                                    + "',	'"
                                    + PNCGUID
                                    + "',	"
                                    + VisitNo
                                    + ",	'"
                                    + Q_0
                                    + "',	"
                                    + Q_1
                                    + ",	'"
                                    + Q_2
                                    + "',	'"
                                    + Q_3
                                    + "',	"
                                    + Q_4
                                    + ",	"
                                    + Q_5
                                    + ",	'"
                                    + Q_6
                                    + "',	'"
                                    + Q_7
                                    + "',"
                                    + Q_8
                                    + ","
                                    + Q_9
                                    + ","
                                    + Q_10
                                    + ","
                                    + Q_12
                                    + ","
                                    + Q_13
                                    + ",	"
                                    + Q_14
                                    + ","
                                    + Q_15
                                    + ","
                                    + Q_16
                                    + ","
                                    + Q_17
                                    + ","
                                    + Q_18
                                    + ","
                                    + Q_19
                                    + ","
                                    + Q_20
                                    + ","
                                    + Q_21
                                    + ","
                                    + Q_22
                                    + ","
                                    + Q_23
                                    + ","
                                    + Q_24
                                    + ",	"
                                    + Q_25
                                    + ","
                                    + Q_26
                                    + ",	"
                                    + Q_27
                                    + ",	"
                                    + Q_28
                                    + ",	"
                                    + Q_29
                                    + ",	"
                                    + Q_30
                                    + ",	"
                                    + Q_32
                                    + ",	"
                                    + Q_33
                                    + ",	"
                                    + Q_34
                                    + ",	"
                                    + Q_35
                                    + ",	"
                                    + Q_36
                                    + ",	"
                                    + Q_37
                                    + ",	"
                                    + Q_38
                                    + ","
                                    + Q_38a
                                    + ",	"
                                    + Q_39
                                    + ",	"
                                    + Q_40
                                    + ",	"
                                    + Q_41
                                    + ",	"
                                    + Q_43
                                    + ",	'"
                                    + Q_44
                                    + "',	"
                                    + Q_45
                                    + ",	"
                                    + Q_46
                                    + ",	"
                                    + Q_47
                                    + ",	'"
                                    + Q_48
                                    + "',	"
                                    + Q_49
                                    + ",	"
                                    + Q_50
                                    + ",	"
                                    + Q_52
                                    + ",	"
                                    + Q_53
                                    + ",	"
                                    + Q_54
                                    + ",	"
                                    + Q_55
                                    + ",	"
                                    + Q_56
                                    + ",	"
                                    + Q_57
                                    + ",	"
                                    + Q_58
                                    + ",	"
                                    + Q_59
                                    + ",	"
                                    + Q_60
                                    + ",	"
                                    + CreatedBy
                                    + ",	'"
                                    + CreatedOn
                                    + "',	"
                                    + UpdatedBy
                                    + ",	'"
                                    + UpdatedOn + "',0,	'"
                                    + Time + "')";

                        } else {
                            if (count1 == 0) {
                                sql = "update tblPNChomevisit_ANS set PWGUID='"
                                        + PWGUID + "',	Q_0='" + Q_0 + "',Q_1=	"
                                        + Q_1 + ",Q_2=	'" + Q_2 + "',Q_3=	'" + Q_3
                                        + "',Q_4=	" + Q_4 + ",Q_5=	" + Q_5
                                        + ",Q_6=	" + Q_6 + ",Q_7=	'" + Q_7
                                        + "',Q_8=" + Q_8 + ",Q_9=" + Q_9 + ",Q_10="
                                        + Q_10 + ",Q_12=" + Q_12 + ",Q_13=" + Q_13
                                        + ",Q_14=	" + Q_14 + ",Q_15=" + Q_15
                                        + ",Q_16=" + Q_16 + ",Q_17=" + Q_17
                                        + ",Q_18=" + Q_18 + ",Q_19=" + Q_19
                                        + ",Q_20=" + Q_20 + ",Q_21=" + Q_21
                                        + ",Q_22=" + Q_22 + ",Q_23=" + Q_23
                                        + ",Q_24=" + Q_24 + ",	Q_25=" + Q_25
                                        + ",Q_26=" + Q_26 + ",Q_27=	" + Q_27
                                        + ",Q_28=	" + Q_28 + ",Q_29=	" + Q_29
                                        + ",Q_30=	" + Q_30 + ",Q_32=	" + Q_32
                                        + ",Q_33=	" + Q_33 + ",Q_34=	" + Q_34
                                        + ",Q_35=	" + Q_35 + ",Q_36=	" + Q_36
                                        + ",Q_37=	" + Q_37 + ",Q_38=	" + Q_38
                                        + ",Q_38a=" + Q_38a + ",Q_39=	" + Q_39
                                        + ",Q_40=	" + Q_40 + ",	Q_41=" + Q_41
                                        + ",Q_43=	" + Q_43 + ",Q_44=	'" + Q_44
                                        + "',Q_45=	" + Q_45 + ",Q_46=	" + Q_46
                                        + ",Q_47=	" + Q_47 + ",Q_48=	'" + Q_48
                                        + "',Q_49=	" + Q_49 + ",Q_50=	" + Q_50
                                        + ",Q_52=	" + Q_52 + ",Q_53=	" + Q_53
                                        + ",Q_54=	" + Q_54 + ",Q_55=	" + Q_55
                                        + ",Q_56=	" + Q_56 + ",Q_57=	" + Q_57
                                        + ",Q_58=	" + Q_58 + ",Q_59=	" + Q_59
                                        + ",Q_60=	" + Q_60 + ",CreatedBy=	"
                                        + CreatedBy + ",CreatedOn=	'" + CreatedOn
                                        + "',UpdatedBy=	" + UpdatedBy
                                        + ",	UpdatedOn='" + UpdatedOn + "',	Createtime='" + Time + "',AshaID="
                                        + ANMID + ",ANMID=" + ANMID
                                        + "	,IsEdited=0 where ChildGUID='"
                                        + ChildGUID + "',	PNCGUID='" + PNCGUID
                                        + "' and visitno=" + VisitNo + "";
                            }
                        }
                        dataProvider.executeSql(sql);


                    }
                    tblverifyArray = jsonObj.getJSONArray("tblafverify");
                    for (int i = 0; i < tblverifyArray.length(); i++) {
                        JSONObject user = tblverifyArray.getJSONObject(i);
                        int UID;
                        String AFGUID;
                        int ModuleID;
                        String ModuleGUID;
                        int Verify;
                        String VerifyOn;
                        int AshaID;
                        int ANMID;
                        int AFID;
                        int CreatedBy;
                        String CreatedOn;
                        int UpdatedBy;
                        String UpdatedOn;
                        UID = Validate.returnIntegerValue(user.optString("UID"));
                        ModuleID = Validate.returnIntegerValue(user.optString("ModuleID"));
                        Verify = Validate.returnIntegerValue(user.optString("Verify"));
                        AshaID = Validate.returnIntegerValue(user.optString("AshaID"));
                        ANMID = Validate.returnIntegerValue(user.optString("ANMID"));
                        AFID = Validate.returnIntegerValue(user.optString("AFID"));
                        CreatedBy = Validate.returnIntegerValue(user.optString("CreatedBy"));
                        UpdatedBy = Validate.returnIntegerValue(user.optString("UpdatedBy"));
                        AFGUID = Validate.returnStringValue(user.optString("AFGUID"));
                        ModuleGUID = Validate.returnStringValue(user.optString("ModuleGUID"));
                        VerifyOn = Validate.returnStringValue(user.optString("VerifyOn"));
                        CreatedOn = Validate.returnStringValue(user.optString("CreatedOn"));
                        UpdatedOn = Validate.returnStringValue(user.optString("UpdatedOn"));
                        String sql = "";
                        sql = "Insert into tblafverify(UID, AFGUID,  ModuleID, ModuleGUID,  Verify, VerifyOn,  AshaID,  ANMID,  AFID,  CreatedBy, CreatedOn,  UpdatedBy, UpdatedOn)values("
                                + UID
                                + ",'"
                                + AFGUID
                                + "',"
                                + ModuleID
                                + ",'" + ModuleGUID + "','" + Verify + "','" + VerifyOn + "'," + AshaID + "," + ANMID + ",'" + AFID + "','" + CreatedBy + "','" + CreatedOn + "','" + UpdatedBy + "','" + UpdatedOn + "')";
                        dataProvider.executeSql(sql);

                    }


                    iDataDownload = 1;
                }
            } catch (JSONException e1) {
                iDataDownload = 0;
                e1.printStackTrace();
            }


        } catch (ClientProtocolException e) {
            // Log exception
            iDataDownload = 0;
            e.printStackTrace();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            iDataDownload = 0;
            e2.printStackTrace();
        }

    }

    public void importData(String UserName, String Password,
                           String ashaid) {
        try {

            importdata(UserName, Password, ashaid);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void DownloadImage(String strURLImage, String imgName) {
        try {
            String iImagePath, imageurl;
            imageurl = strURLImage + imgName;
            URL url = new URL(imageurl);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            // int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),
                    conection.getContentLength());
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
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
            OutputStream output = new FileOutputStream(iImagePath + imgName);

            byte data[] = new byte[conection.getContentLength()];

            // long total = 0;

            // writing data to file

            int length;

            while ((length = input.read(data)) != -1) {
                output.write(data, 0, length);
            }
            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
    }

    public void importimage(String imagename) {

        String ImageName = "";
        String WebUrl = "replace your URL";
        if (imagename != null && imagename.length() > 10) {

            ImageName = imagename;
            DownloadImage(WebUrl, ImageName);

        }
    }

    private void ClearAshaData() {
        String sQueryDeletetblHHSurvey = null;
        sQueryDeletetblHHSurvey = "Delete from  Tbl_HHSurvey";
        dataProvider.executeSql(sQueryDeletetblHHSurvey);
        String sQueryDeletetblFamilyMember = null;
        sQueryDeletetblFamilyMember = "Delete from  Tbl_HHFamilyMember";
        dataProvider.executeSql(sQueryDeletetblFamilyMember);
        String sQueryDeletetblPregnantWoman = null;
        sQueryDeletetblPregnantWoman = "Delete from  tblPregnant_woman";
        dataProvider.executeSql(sQueryDeletetblPregnantWoman);
        String sQueryDeletetblChild = null;
        sQueryDeletetblChild = "Delete from  tblChild";
        dataProvider.executeSql(sQueryDeletetblChild);
        String sQueryDeletetblANCVisit = null;
        sQueryDeletetblANCVisit = "Delete from  TblANCVisit";
        dataProvider.executeSql(sQueryDeletetblANCVisit);
        String sQueryDeletetblPNCHomeVisit = null;
        sQueryDeletetblPNCHomeVisit = "Delete from  tblPNChomevisit_ANS";
        dataProvider.executeSql(sQueryDeletetblPNCHomeVisit);
        String sQueryDeletetblafverify = "Delete from  tblafverify";
        dataProvider.executeSql(sQueryDeletetblafverify);
        validate.SaveSharepreferenceInt("ASHAID", 0);
        validate.SaveSharepreferenceString("ASHA", "");
    }


}
