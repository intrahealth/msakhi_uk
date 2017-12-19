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
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblncdfollowup;
import com.microware.intrahealth.object.tblncdscreening;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
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

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class NCD_ScreeningAdapter extends BaseAdapter {
    Context context;

    Global global;
    DataProvider dataProvider;

    ArrayList<tblncdscreening> ncdscreening;

    public ArrayList<tblncdscreening> ncdscreening1 = new ArrayList<tblncdscreening>();

    GridView gridfolloup;
    int diffInDays = 0;
    ArrayList<tblncdfollowup> ncdfollowup = new ArrayList<tblncdfollowup>();

    public NCD_ScreeningAdapter(Context context,
                                ArrayList<tblncdscreening> ncdscreening) {
        this.ncdscreening = ncdscreening;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ncdscreening.size();
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
        btn_page.setImageResource(R.drawable.page);
        tvHHCode.setText("" + (position + 1));
        tvHHHeadName.setText(ncdscreening.get(position).getName());
        String guid = "";

        if (ncdscreening.get(position).getHHSurveyGUID() != null
                && ncdscreening.get(position).getHHSurveyGUID().length() > 0) {
            String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + ncdscreening.get(position).getHHSurveyGUID()
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
                String sql = "Select count(*) from tblncdfollowup where HHFamilyMemberGUID='"
                        + ncdscreening.get(position).getHHFamilyMemberGUID()
                        + "'  and   cast(round((julianday('NOW')-julianday(RegistrationDate))+.5)  as int) between 2 and 15 ";
                int aa = dataProvider.getMaxRecord(sql);
                if (aa == 0) {
                    Intent i = new Intent(v.getContext(), NCDFollowUp.class);
                    global.setGlobalHHFamilyMemberGUID(ncdscreening.get(
                            position).getHHFamilyMemberGUID());
                    global.setGlobalHHSurveyGUID(ncdscreening.get(position)
                            .getHHSurveyGUID());
                    global.setNCDScreeningGUID(ncdscreening.get(position)
                            .getNCDScreeningGUID());

                    context.startActivity(i);
                } else {
                    CustomAlerts(context.getResources().getString(
                            R.string.follwupdone));
                }

            }
        });
        btn_page.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                global.setGlobalHHFamilyMemberGUID(ncdscreening.get(position)
                        .getHHFamilyMemberGUID());
                global.setGlobalHHSurveyGUID(ncdscreening.get(position)
                        .getHHSurveyGUID());
                global.setNCDScreeningGUID(ncdscreening.get(position)
                        .getNCDScreeningGUID());
                CustomAlertfollowup();
            }
        });

        return gridview;
    }

    public void CustomAlertfollowup() { // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ncdfolloupreport, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        gridfolloup = (GridView) dialog.findViewById(R.id.gridfolloup);
        TextView tv_name = (TextView) dialog.findViewById(R.id.tv_name);
        TableRow tbl_Row = (TableRow) dialog.findViewById(R.id.tbl_Row);
        TextView tv2 = (TextView) dialog.findViewById(R.id.tv2);
        TextView tv1 = (TextView) dialog.findViewById(R.id.tv1);
        TextView tv3 = (TextView) dialog.findViewById(R.id.tv3);
        TextView tv4 = (TextView) dialog.findViewById(R.id.tv4);
        String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
                + global.getGlobalHHFamilyMemberGUID() + "'  and StatusID!=2";
        String guid = dataProvider.getRecord(sql);

        if (guid != null && guid.length() > 0) {
            tv_name.setText(guid);
        }
        ncdscreening1 = dataProvider.getncdscreeningdata(global.getGlobalHHFamilyMemberGUID(), 4, 0,0);
        if (ncdscreening1 != null && ncdscreening1.size() > 0) {
            tv1.setText("" + 1);
            tv2.setText(Validate.changeDateFormat(ncdscreening1.get(0).getRegistrationDate()));
            tv3.setText(ncdscreening1.get(0).getRBS());
            tv4.setText(ncdscreening1.get(0).getBP());

        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        fillgrid();



        // Display the dialog
        dialog.show();
    }

    public void fillgrid() {
        int flag = 0;

        ncdfollowup = dataProvider.getncdfollowup(
                global.getGlobalHHFamilyMemberGUID(), 1, 0);

        if (ncdfollowup != null && ncdfollowup.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridfolloup
                    .getLayoutParams();
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * ncdfollowup.size();
            params.height = gridHeight1;
            gridfolloup.setLayoutParams(params);
            gridfolloup.setAdapter(new NCDReportAdapter(context, ncdfollowup,
                    flag));
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
