package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class OpenListadapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data = null;
    TableRow GridRow;
    private static final String IMAGE_DIRECTORY_NAME1 = "mSakhi/PDFReport/";

    public OpenListadapter(Context context,
                           ArrayList<HashMap<String, String>> data) {
        this.data = data;
        this.context = context;

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
            gridview = layoutInflater
                    .inflate(R.layout.openlistadapter, null);
        } else {
            gridview = convertView;
        }
        GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        TextView tv2 = (TextView) gridview.findViewById(R.id.tv2);
        TextView tv1 = (TextView) gridview.findViewById(R.id.tv1);
        TextView tv3 = (TextView) gridview.findViewById(R.id.tv3);
        tv1.setText(position+1+"");
        tv2.setText(data.get(position).get("FileName"));
        tv3.setText(data.get(position).get("Date"));
        if (position % 2 == 0) {
            GridRow.setBackgroundColor(context.getResources().getColor(R.color.LightPowderBlue));
        }
        GridRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();// .Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                if(Build.VERSION.SDK_INT>=24){
                    try{
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME1 + data.get(position).get("FileName"));
                Uri path = Uri.fromFile(file);
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(path, "application/pdf");
                try {
                    context.startActivity(pdfOpenintent);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        return gridview;
    }


}
