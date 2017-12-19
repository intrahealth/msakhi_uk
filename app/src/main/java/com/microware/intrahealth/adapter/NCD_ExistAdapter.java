package com.microware.intrahealth.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.FP_Follwup;
import com.microware.intrahealth.FP_MemberVisit;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Login;
import com.microware.intrahealth.NCDFollowUp;
import com.microware.intrahealth.NcdScreening;
import com.microware.intrahealth.Ncd_Cbac;
import com.microware.intrahealth.R;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class NCD_ExistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tbl_HHFamilyMember> hhsurvey;
    Global global;
    DataProvider dataProvider;
    int j = 1, flag = 0;
    ArrayList<Tbl_HHFamilyMember> hushbandArrayList = new ArrayList<Tbl_HHFamilyMember>();
    ArrayList<tblmstFPAns> FP_Ans = new ArrayList<tblmstFPAns>();
    int diffInDays = 0;
    ArrayList<tblmstFPFDetail> tblFp_followArrayList = new ArrayList<tblmstFPFDetail>();

    public NCD_ExistAdapter(Context context,
                            ArrayList<Tbl_HHFamilyMember> hhsurvey) {
        this.hhsurvey = hhsurvey;
        this.context = context;
    }

    public NCD_ExistAdapter(Context context,
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
            gridview = layoutInflater.inflate(R.layout.ncd_adapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);

        TextView tvhusbandname = (TextView) gridview
                .findViewById(R.id.tvhusbandname);
        TextView tvHHCode = (TextView) gridview.findViewById(R.id.tvHHCode);
        TextView tvHHHeadName = (TextView) gridview
                .findViewById(R.id.tvHHHeadName);
        TableRow GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
        ImageView btn_page = (ImageView) gridview.findViewById(R.id.btn_page);
        btn_page.setVisibility(View.VISIBLE);
        btn_page.setImageResource(R.drawable.cbac);
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
        String sql = "Select count(*) from tblncdcbac where HHFamilyMemberGUID='"
                + hhsurvey.get(position).getHHFamilyMemberGUID()
                + "' and Status=1 ";
        int aa = dataProvider.getMaxRecord(sql);
        if (aa > 0) {
            tvHHHeadName.setBackgroundResource((R.drawable.yellowsheet));
            tvHHCode.setBackgroundResource((R.drawable.yellowsheet));
            tvhusbandname.setBackgroundResource((R.drawable.yellowsheet));
        } else {
            aa=0;
            tvHHHeadName.setBackgroundResource((R.drawable.whitesheet));
            tvHHCode.setBackgroundResource((R.drawable.whitesheet));
            tvhusbandname.setBackgroundResource((R.drawable.whitesheet));
        }
        if (global.getiGlobalRoleID() == 2) {
            btnEdit.setVisibility(View.GONE);
        } else if (global.getiGlobalRoleID() == 3) {
            btn_page.setVisibility(View.GONE);
        }
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (flag == 1) {
                    Intent i = new Intent(v.getContext(), NcdScreening.class);
                    global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                            .getHHFamilyMemberGUID());
                    global.setGlobalHHSurveyGUID(hhsurvey.get(position)
                            .getHHSurveyGUID());
                    context.startActivity(i);
                } else {
//					Intent i = new Intent(v.getContext(), NCDFollowUp.class);
//					// global.setRadioid(0);
//					// i.putExtra("counselling", "0");
//					context.startActivity(i);
                }

            }
        });
        btn_page.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (flag == 1) {
                    Intent i = new Intent(v.getContext(), Ncd_Cbac.class);
                    global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                            .getHHFamilyMemberGUID());
                    global.setGlobalHHSurveyGUID(hhsurvey.get(position)
                            .getHHSurveyGUID());
                    context.startActivity(i);
                } else {
//					Intent i = new Intent(v.getContext(), NCDFollowUp.class);
//					// global.setRadioid(0);
//					// i.putExtra("counselling", "0");
//					context.startActivity(i);
                }

            }
        });

        return gridview;
    }

    private int Weekcalculation() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String CurrentDate = sdf.format(new Date());
        Date date, visitdate1;
        String VisitDate = CurrentDate;
        FP_Ans = dataProvider
                .getFP_Ans(global.getGlobalHHFamilyMemberGUID(), 0);

        if (FP_Ans != null && FP_Ans.size() > 0) {
            VisitDate = String.valueOf(FP_Ans.get(0).getVisitDate());
        }

        try {
            date = sdf.parse(CurrentDate);
            visitdate1 = sdf.parse(VisitDate);

            diffInDays = (int) ((date.getTime() - visitdate1.getTime()) / (1000 * 60 * 60 * 24));
            return diffInDays;
        } catch (ParseException e) {

            e.printStackTrace();
            return 1;
        }

    }

    public void CustomAlerts(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                // finish();

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

}