package com.microware.intrahealth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.microware.intrahealth.adapter.MemberList_Adapter;
import com.microware.intrahealth.adapter.PdfReport_Adapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PdfReport extends Activity {
    private static final String IMAGE_DIRECTORY_NAME1 = "mSakhi/PDFReport";
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    ProgressDialog progressDialog;
    int pdfdownload = 0;
    DataProvider dataProvider;
    Global global;
    int range = 1;
    Button btnSave;
    GridView ReprtGrid;
    TextView totalcount;
    String fromdate, todate;
    Validate validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pdfreprt);
        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        totalcount = (TextView) findViewById(R.id.totalcount);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        ReprtGrid = (GridView) findViewById(R.id.ReprtGrid);
        fillgrid(1);
        //  radio1.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radio1.isChecked()) {
                    range = 1;
                    fillDate(1);
                } else if (radio2.isChecked()) {
                    range = 2;
                    fillDate(2);
                } else if (radio3.isChecked()) {
                    range = 3;
                    fillDate(3);
                }

            }
        });
        fillDate(1);

    }


    public void fillDate(int flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Calendar aCalendar = Calendar.getInstance();
        if (flag == 1) {
            aCalendar.set(Calendar.DATE, 1);
            fromdate = sdf.format(aCalendar.getTime());
            todate = Validate.getcurrentdate();
        } else if (flag == 2) {
            aCalendar.add(Calendar.MONTH, -1);
            aCalendar.set(Calendar.DATE, 1);
            fromdate = sdf.format(aCalendar.getTime());
            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            todate = sdf.format(aCalendar.getTime());
        } else if (flag == 3) {
            aCalendar.add(Calendar.MONTH, -3);
            aCalendar.set(Calendar.DATE, 1);
            fromdate = sdf.format(aCalendar.getTime());
            aCalendar.add(Calendar.MONTH, 2);
            aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            todate = sdf.format(aCalendar.getTime());
        }
    }

    public void fillgrid(int flag) {
        ArrayList<HashMap<String, String>> data = null;
        String sqlreport = "select * from mstpdfreport where IsActive=1";
        data = dataProvider.getDynamicVal(sqlreport);
        if (data != null) {
            totalcount.setText(getResources().getString(
                    R.string.totalcountfamilymember)
                    + " -" + String.valueOf(data.size()));

//            android.view.ViewGroup.LayoutParams params = ReprtGrid
//                    .getLayoutParams();
//            Resources r = getResources();
//            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    90, r.getDisplayMetrics());
//            int hi = Math.round(px);
//            int gridHeight1 = 0;
//            gridHeight1 = hi * data.size();
//            params.height = gridHeight1;
//            ReprtGrid.setLayoutParams(params);
            ReprtGrid.setAdapter(new PdfReport_Adapter(this, data, flag));
        }
    }

    public void getpdf(final String reportid) {
        progressDialog = ProgressDialog.show(PdfReport.this, "",
                getString(R.string.Downloadingreport));
        new Thread() {

            @Override
            public void run() {
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    // replace with your url
                    HttpPost httpPost = new HttpPost("replace your URL");
                    // Post Data
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

                    nameValuePair.add(new BasicNameValuePair("username", global.getsGlobalUserName()));
                    nameValuePair.add(new BasicNameValuePair("password", global.getsGlobalPassword()));
                    nameValuePair.add(new BasicNameValuePair("ashaid", global.getsGlobalAshaCode()));
                    nameValuePair.add(new BasicNameValuePair("anmid", global.getsGlobalANMCODE()));
                    nameValuePair.add(new BasicNameValuePair("report_id", reportid));
                    nameValuePair.add(new BasicNameValuePair("date_from", fromdate));
                    nameValuePair.add(new BasicNameValuePair("date_to", todate));

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
                            String filename = jsonObj.optString("filename");
                            if (filename != null) {
                                Downloadfile(filename, reportid);
                            }

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

                        if (pdfdownload == 1) {
                            Toast.makeText(PdfReport.this, R.string.ReportDowloadSuccessfully, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(PdfReport.this, R.string.ErrorinReportDowload, Toast.LENGTH_LONG).show();
                        }
                        fillgrid(2);

                    }

                });
                progressDialog.dismiss();

            }

        }.start();

    }


    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 1, validate.RetriveSharepreferenceString("name")).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {

            case 1:
                if (item.getTitle().equals(validate.RetriveSharepreferenceString("Username"))) {
                    item.setTitle(validate.RetriveSharepreferenceString("name"));

                } else {
                    item.setTitle(validate.RetriveSharepreferenceString("Username"));

                }
                break;


            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(PdfReport.this, Report_Module.class);
        finish();
        startActivity(i);

    }

    private void Downloadfile(String file, String reportid) {

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
                    IMAGE_DIRECTORY_NAME1);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("", "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME1 + " directory");

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
            pdfdownload = 1;
            String updatesql = "update mstpdfreport set LastDownload='" + Validate.getcurrentdatetimeddmmyyyy() + "',LastReportName='" + file + "' where ReportID='" + reportid + "' ";
            dataProvider.executeSql(updatesql);
        } catch (Exception e) {
            pdfdownload = 0;
            Log.e("Error: ", e.getMessage());
        }
    }


}
