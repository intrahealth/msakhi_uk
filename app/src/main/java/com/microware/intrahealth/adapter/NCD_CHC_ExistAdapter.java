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

import com.microware.intrahealth.Global;
import com.microware.intrahealth.NcdScreening;
import com.microware.intrahealth.NcdScreeningCHC;
import com.microware.intrahealth.Ncd_Cbac;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;

import java.util.ArrayList;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class NCD_CHC_ExistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tbl_HHFamilyMember> hhsurvey;
    Global global;
    DataProvider dataProvider;
    int j = 1, flag = 0;

    public NCD_CHC_ExistAdapter(Context context,
                                ArrayList<Tbl_HHFamilyMember> hhsurvey) {
        this.hhsurvey = hhsurvey;
        this.context = context;
    }

    public NCD_CHC_ExistAdapter(Context context,
                                ArrayList<Tbl_HHFamilyMember> hhsurvey, int flag) {
        this.hhsurvey = hhsurvey;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hhsurvey.size();
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
            gridview = layoutInflater.inflate(R.layout.ncd_chc_adapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);

        TextView tvhusbandname = (TextView) gridview
                .findViewById(R.id.tvhusbandname);
        TextView tvHHCode = (TextView) gridview.findViewById(R.id.tvHHCode);
        TextView tv_score = (TextView) gridview.findViewById(R.id.tv_score);
        TextView tvHHHeadName = (TextView) gridview
                .findViewById(R.id.tvHHHeadName);
        TableRow GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);



        tvHHCode.setText(hhsurvey.get(position).getHHCode());
        tvHHHeadName.setText(hhsurvey.get(position).getFamilyMemberName());
        String guid = "";

        if (hhsurvey.get(position).getHHSurveyGUID() != null
                && hhsurvey.get(position).getHHSurveyGUID().length() > 0) {
            String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + hhsurvey.get(position).getHHSurveyGUID()
                    + "'  and RelationID=1 and StatusID!=2";
            guid = dataProvider.getRecord(sql);

            if (guid != null && guid.length() > 0) {
                tvhusbandname.setText(guid);
            } else {
                guid = "";
                tvhusbandname.setText("");
            }
        } else {
            guid = "";
            tvhusbandname.setText("");
        }


        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (flag == 1) {
                    Intent i = new Intent(v.getContext(), NcdScreeningCHC.class);
                    global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                            .getHHFamilyMemberGUID());
                    global.setGlobalHHSurveyGUID(hhsurvey.get(position)
                            .getHHSurveyGUID());
                    i.putExtra("flag",1);
                    i.putExtra("HHSurveyGUID",hhsurvey.get(position)
                            .getHHSurveyGUID());
                    context.startActivity(i);
                }else {
                    Intent i = new Intent(v.getContext(), NcdScreeningCHC.class);
                    global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                            .getHHFamilyMemberGUID());
                    global.setGlobalHHSurveyGUID(hhsurvey.get(position)
                            .getHHSurveyGUID());
                    context.startActivity(i);
                }

            }
        });



        return gridview;
    }




}
