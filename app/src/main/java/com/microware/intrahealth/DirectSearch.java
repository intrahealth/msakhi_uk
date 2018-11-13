package com.microware.intrahealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.microware.intrahealth.adapter.MemberList_Adapter;
import com.microware.intrahealth.adapter.NCD_CHC_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DirectSearch extends Activity {


    ProgressDialog progressDialog;
    int ncddownload = 0;
    DataProvider dataProvider;
    Global global;
    int iFlag = 0;
    EditText et_Name, et_Adhar;
    String sqlasha = "", sqlSubcenter = "";
    Button btnSave;
    JSONArray HHSurveyFamilyData = null;
    GridView HHSurveyGrid;
    TextView totalcount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.directsearch);
        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        et_Name = (EditText) findViewById(R.id.et_Name);

        totalcount = (TextView) findViewById(R.id.totalcount);
        et_Adhar = (EditText) findViewById(R.id.et_Adhar);
        btnSave = (Button) findViewById(R.id.btnSave);
        HHSurveyGrid = (GridView) findViewById(R.id.HHSurveyGrid);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sCheckValidation() == 0) {
                    if (Validate.isNetworkconn(DirectSearch.this)) {
                        getlist(et_Adhar.getText().toString(), et_Name.getText().toString());
                    } else {
                        Validate.showNewWorkError(DirectSearch.this);

                    }
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    public int sCheckValidation() {
        int iCheck = 0;
        try {

            if (et_Adhar.getText().toString().length() == 0 && et_Name.getText().toString().length() ==0) {
                iCheck = 1;
                Validate.CustomAlertEdit(this, et_Name, getString(R.string.PleaseenternameorAadharno)
                );
            }
            if (et_Adhar.getText().toString().length() > 0 && et_Adhar.getText().toString().length() < 12) {
                iCheck = 1;
                Validate.CustomAlertEdit(this, et_Adhar, getResources().getString(R.string.enter12digituid));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return iCheck;
    }

    public void fillgrid() {


        if (HHSurveyFamilyData != null) {
            totalcount.setText(getResources().getString(
                    R.string.totalcountfamilymember)
                    + " -" + String.valueOf(HHSurveyFamilyData.length()));

            // android.view.ViewGroup.LayoutParams params = HHSurveyGrid
            // .getLayoutParams();
            // Resources r = getResources();
            // float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            // 300, r.getDisplayMetrics());
            // int hi = Math.round(px);
            // int gridHeight1 = 0;
            // gridHeight1 = hi * member.size();
            // params.height = gridHeight1;
            // HHSurveyGrid.setLayoutParams(params);
            HHSurveyGrid.setAdapter(new MemberList_Adapter(this, HHSurveyFamilyData));
        }
    }

    public void getlist(final String adharcard, final String name) {
        progressDialog = ProgressDialog.show(DirectSearch.this, "",
                getResources().getString(R.string.DataLoading));
        new Thread() {

            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    // replace with your url
                    HttpPost httpPost = new HttpPost("replace your URL");
                    JSONObject jsonObjectcombined = new JSONObject();


                    // Post Data
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

                    nameValuePair.add(new BasicNameValuePair("username", global.getsGlobalUserName()));
                    nameValuePair.add(new BasicNameValuePair("password", global.getsGlobalPassword()));
                    nameValuePair.add(new BasicNameValuePair("ashaid", global
                            .getsGlobalAshaCode()));

                    nameValuePair.add(new BasicNameValuePair("anmid", global
                            .getAnmidasAnmCode()));

                    nameValuePair.add(new BasicNameValuePair("userid", global.getsGlobalUserID()));
                    nameValuePair.add(new BasicNameValuePair("adharcard", adharcard));
                    nameValuePair.add(new BasicNameValuePair("name", name));

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
                                HTTP.UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        // log exception
                        e.printStackTrace();
                    }

                    // making POST request.
                    try {
                        HttpResponse response = httpClient.execute(httpPost);
                        String responseBody = EntityUtils
                                .toString(response.getEntity());
                        String ss = "";
                        JSONObject jsonObj = null;
                        try {

                            jsonObj = new JSONObject(responseBody.toString());
                            HHSurveyFamilyData = jsonObj.getJSONArray("data");

                        } catch (JSONException e1) {

                            e1.printStackTrace();
                            // Toast.makeText(this, e1.toString(), Toast.LENGTH_LONG).show();
                        }


                    } catch (ClientProtocolException e) {
                        // Log exception
                        e.printStackTrace();
                        // Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    progressDialog.dismiss();
                    // Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        fillgrid();

                    }

                });
                progressDialog.dismiss();

            }

        }.start();

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(DirectSearch.this, NCD_CHC_AA.class);
        finish();
        startActivity(i);

    }

    public void importreferaldata(final String HHSurveyGUID) {

        progressDialog = ProgressDialog.show(DirectSearch.this, "",
                getResources().getString(R.string.DataLoading));
        new Thread() {

            @Override
            public void run() {
                try {
                    importreferaltabledata(HHSurveyGUID);


                } catch (Exception exp) {
                    progressDialog.dismiss();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ncddownload == 1) {
                            Toast.makeText(
                                    DirectSearch.this,
                                    getResources().getString(
                                            R.string.DataDownloadsuccessfully),
                                    Toast.LENGTH_LONG).show();
                            dataProvider.insertdownlaoddetail(13, "DownloadcCHCHHdata", global.getUserID(), global.getsGlobalAshaCode(), global.getsGlobalANMCODE());

                        } else {
                            Toast.makeText(
                                    DirectSearch.this,
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

    public void importreferaltabledata(String HHSurveyGUID) {

        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("replace your URL");

        // Post Data
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
        nameValuePair.add(new BasicNameValuePair("username", global.getsGlobalUserName()));
        nameValuePair.add(new BasicNameValuePair("password", global.getsGlobalPassword()));
        nameValuePair.add(new BasicNameValuePair("HHSurveyGUID", HHSurveyGUID));


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
                String[] Tablename = {"Tbl_HHSurvey", "Tbl_HHFamilyMember"};
                String[] Tablename1 = {"tblhhsurvey", "tblhhfamilymember"};
                String[] condition[] = {{"HHSurveyGUID"}, {"HHSurveyGUID", "HHFamilyMemberGUID"}};
                jsonObj = new JSONObject(responseBody.toString());
                dataProvider.ImportDataInMyWay(jsonObj, Tablename, condition, Tablename1);
            } catch (JSONException e1) {

                e1.printStackTrace();
            }
            Intent i = new Intent(DirectSearch.this, NCD_CHC_AA.class);
            i.putExtra("flag", 1);
            i.putExtra("HHSurveyGUID", HHSurveyGUID);
            startActivity(i);
            ncddownload = 1;


        } catch (ClientProtocolException e) {
            // Log exception
            ncddownload = 0;
            e.printStackTrace();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            ncddownload = 0;
            e2.printStackTrace();
        }

    }


}
