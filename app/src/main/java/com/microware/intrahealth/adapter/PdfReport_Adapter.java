package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.PdfReport;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class PdfReport_Adapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data = null;
    ArrayList<HashMap<String, String>> data1 = new ArrayList<HashMap<String, String>>();

    Global global;
    DataProvider dataProvider;
    TextView tv_Reprtname, tv_date;
    ImageView btn_download, btn_folder;
    GridView gridreport;
    int flag = 0;
    private static final String IMAGE_DIRECTORY_NAME1 = "mSakhi/PDFReport/";

    public PdfReport_Adapter(Context context, ArrayList<HashMap<String, String>> data, int flag) {
        this.context = context;
        this.data = data;
        this.flag = flag;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings({"static-access", "unused"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.pdfreport_adapter, null);
        } else {
            gridview = convertView;
        }

        btn_download = (ImageView) gridview.findViewById(R.id.btn_download);
        btn_folder = (ImageView) gridview.findViewById(R.id.btn_folder);
        tv_Reprtname = (TextView) gridview.findViewById(R.id.tv_Reprtname);
        tv_date = (TextView) gridview.findViewById(R.id.tv_date);
        if (flag == 2) {
           // btn_download.setImageResource(R.drawable.download_black_128dp);
            btn_download.setImageResource(R.mipmap.download);
        } else {
            btn_download.setImageResource(R.mipmap.download);
        }

        tv_Reprtname.setText(data.get(position).get("ReportName"));
        tv_date.setText(data.get(position).get("LastDownload"));
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PdfReport) context).getpdf(data.get(position).get("ReportID"));
            }
        });
        btn_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME1 + data.get(position).get("LastReportName"));
//                Uri path = Uri.fromFile(file);
//                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pdfOpenintent.setDataAndType(path, "application/pdf");
//                try {
//                    context.startActivity(pdfOpenintent);
//                } catch (ActivityNotFoundException e) {
//
//                }
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();// .Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                openlist();
            }
        });
        if (data.get(position).get("LastReportName").toString().length() > 0) {
            btn_folder.setBackgroundResource(R.drawable.ic_folder_24dp);
        } else {
            btn_folder.setBackgroundResource(R.drawable.ic_folder_gray_24dp);
        }


        return gridview;
    }

    public void openlist() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.openlistgriddialog, null,
                false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        gridreport = (GridView) dialog.findViewById(R.id.gridreport);


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        fillgrid();

        // code comes here

        // Display the dialog
        dialog.show();

    }

    public void fillgrid() {
        int flag = 0;

        String path = Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY_NAME1;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        data1.clear();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                HashMap<String, String> params = new HashMap<String, String>();
               // params.clear();

                if (listOfFiles[i].isFile()) {
                    params.put("FileName", listOfFiles[i].getName());
                }
                try {
                    File file = new File(path + listOfFiles[i].getName());
                    Date lastModDate = new Date(file.lastModified());
                    SimpleDateFormat date1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String endTime = date1.format(lastModDate);
                    params.put("Date", endTime);
                } catch (Exception e) {

                }
                data1.add(params);
            }
        }

        if (data1 != null && data1.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridreport
                    .getLayoutParams();
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * data1.size();
            params.height = gridHeight1;
            gridreport.setLayoutParams(params);
            gridreport.setAdapter(new OpenListadapter(context, data1));
        }
    }

}
