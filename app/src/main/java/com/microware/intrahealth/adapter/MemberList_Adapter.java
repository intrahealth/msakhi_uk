package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.DirectSearch;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.NcdScreening;
import com.microware.intrahealth.Ncd_Cbac;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class MemberList_Adapter extends BaseAdapter {
    Context context;
    JSONArray HHSurveyFamilyData = null;

    Global global;
    DataProvider dataProvider;

    TextView tv_hhcode, tv_name,tv_hhname;
    ImageView image;
    TableRow GridRow;

    public MemberList_Adapter(Context context, JSONArray HHSurveyFamilyData) {
        this.context = context;
        this.HHSurveyFamilyData = HHSurveyFamilyData;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return HHSurveyFamilyData.length();
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
            gridview = layoutInflater.inflate(R.layout.memberlist_adapter, null);
        } else {
            gridview = convertView;
        }
        image = (ImageView) gridview.findViewById(R.id.image);
        GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        tv_hhcode = (TextView) gridview.findViewById(R.id.tv_hhcode);
        tv_hhname = (TextView) gridview.findViewById(R.id.tv_hhname);
        tv_name = (TextView) gridview.findViewById(R.id.tv_name);
        try {

            JSONObject SurveyFamilyData = HHSurveyFamilyData
                    .getJSONObject(position);
            String temp=SurveyFamilyData.getString("HHCode");
            String tempname=SurveyFamilyData.getString("FamilyMemberName");
            tv_hhcode.setText(Validate.returnStringValue(SurveyFamilyData.getString("HHCode")));
            tv_name.setText(Validate.returnStringValue(SurveyFamilyData.getString("FamilyMemberName")));
            tv_hhname.setText(Validate.returnStringValue(SurveyFamilyData.getString("HeadName")));
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   try{
                       String hhguid=HHSurveyFamilyData
                            .getJSONObject(position).getString("HHSurveyGUID");
                       ((DirectSearch)context).importreferaldata(hhguid);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
                }
            });
        } catch (JSONException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }


        return gridview;
    }


}
