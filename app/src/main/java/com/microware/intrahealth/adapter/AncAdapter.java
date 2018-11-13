package com.microware.intrahealth.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Delivery;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.PncWomenList;
import com.microware.intrahealth.PregnantWomen;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class AncAdapter extends BaseAdapter {

    ArrayList<tbl_pregnantwomen> pregnant;
    Context context;
    String imageUri;
    Global global;
    @SuppressWarnings("unused")
    private static final String IMAGE_DIRECTORY_NAME = "mSakhi";
    DataProvider dataProvider;
    Validate validate;
    ArrayList<TblANCVisit> VisitANC = new ArrayList<TblANCVisit>();
    ArrayList<Tbl_HHFamilyMember> masterEntery1 = new ArrayList<Tbl_HHFamilyMember>();
    ArrayList<TblANCVisit> VisitANC2 = new ArrayList<TblANCVisit>();

    Dialog PicVideo_PreviewPopUp;
    TextView anc_date1, anc_date2, anc_date3, anc_date4, weight1, weight2, weight3, weight4, bp1, bp2, bp3, bp4, hb1, hb2,
            hb3, hb4, us1, us2, us3, us4, ua1, ua2, ua3, ua4, BMI1, BMI2, BMI3,
            BMI4, tt1, tt2, tt3, tt4, tt21, tt22, tt23, tt24, IFA1, IFA2, IFA3, IFA4, calcium1,
            calcium2, calcium3, calcium4, dangersigns1, dangersigns2,
            dangersigns3, dangersigns4, HRP, HRP1, HRP2, HRP3, HRP4, Height1,
            Height2, Height3, Height4;
    String arr[] = {"अत्याधिक/गंभीर एनीमिया",
            "गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
            "पेट में तेज दर्द", "दौरे आना या फिट्स पड़ना"};
    String arr1[] = {"अत्याधिक/गंभीर एनीमिया",
            "गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
            "पेट में तेज दर्द", "सिर में तेज दर्द के साथ  धुंधला दिखना",
            "चेहरे, हाथों, पांवों में सूजन ", "दौरे आना या फिट्स पड़ना",};

    public AncAdapter(Context context, ArrayList<tbl_pregnantwomen> pregnant) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.pregnant = pregnant;
        // this.iflag = iflag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pregnant.size();
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.ancgridadapter, null);
        } else {
            gridview = convertView;
        }

        dataProvider = new DataProvider(context);
        validate = new Validate(context);
        global = (Global) context.getApplicationContext();

        TextView tvwname = (TextView) gridview.findViewById(R.id.tvwname);
        TextView tv_count = (TextView) gridview.findViewById(R.id.tv_count);

        ImageView report = (ImageView) gridview.findViewById(R.id.report);

        final TextView tvGridhide = (TextView) gridview
                .findViewById(R.id.tvGridhide);
        final GridView gridVisits = (GridView) gridview
                .findViewById(R.id.gridVisits);
        int count = 0;
        // tvwname.setEnabled(false);
        String sql = "select count(*) from tblancvisit where PWGUID='"
                + pregnant.get(position).getPWGUID()
                + "' and checkupVisitDate!=''";
        count = dataProvider.getMaxRecord(sql);

        if (count > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + count);
        } else {
            tv_count.setVisibility(View.INVISIBLE);
        }
        ImageView ivedit = (ImageView) gridview.findViewById(R.id.imageancedit);
        // ImageView imagePreg = (ImageView)
        // gridview.findViewById(R.id.imagePreg);
        ImageView ivoutcome = (ImageView) gridview
                .findViewById(R.id.imageancaddvisit);
        ImageView btnhousehold = (ImageView) gridview
                .findViewById(R.id.btnhousehold);
        if (global.getVHND_flag() == 1) {
            ivoutcome.setVisibility(View.INVISIBLE);
        } else {
            ivoutcome.setVisibility(View.VISIBLE);
        }
        if (global.getiGlobalRoleID() == 4) {
            // ((AncActivity) context).fillgrid(3, "");
            btnhousehold.setEnabled(false);
            ivoutcome.setEnabled(false);
            String sVerify = "Select Verify from tblAFVerify where ModuleGUID='" + pregnant.get(position).getPWGUID() + "'";
            String verifyValue = dataProvider.getRecord(sVerify);

            if (verifyValue.length() > 0) {
                int iVerify = Integer.valueOf(verifyValue);
                if (iVerify == 1) {
                    tvwname.setBackgroundResource(R.drawable.aforangesheet);
                } else if (iVerify == 2) {
                    tvwname.setBackgroundResource(R.drawable.afgreensheet);
                } else if (iVerify == 3) {
                    tvwname.setBackgroundResource(R.drawable.afredsheet);
                }
            }
        } else {
            btnhousehold.setEnabled(true);
            ivoutcome.setEnabled(true);
            String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
                    + pregnant.get(position).getHHFamilyMemberGUID() + "'";
            String stat = dataProvider.getRecord(status);
            if (Validate.returnIntegerValue(stat) == 3) {
                tvwname.setBackgroundResource(R.drawable.yellowsheet);
                // report.setBackgroundResource(R.drawable.yellowsheet);
            } else {
                tvwname.setBackgroundResource(R.drawable.whitesheet);
                // report.setBackgroundResource(R.drawable.whitesheet);
            }
        }


        ivoutcome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
                        .getHHFamilyMemberGUID());
                global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
                global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
                global.setsGlobalLMPDate(pregnant.get(position).getLMPDate());
                Intent i = new Intent(context, Delivery.class);
                context.startActivity(i);
            }
        });
        //
        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
                        .getHHFamilyMemberGUID());
                global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
                global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
                global.setsGlobalLMPDate(pregnant.get(position).getLMPDate());
                showreport(pregnant.get(position).getPWGUID(), position);
            }
        });
        ivedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
                        .getHHFamilyMemberGUID());
                global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
                masterEntery1 = dataProvider.getAllmalename(global
                        .getGlobalHHFamilyMemberGUID());
                if (masterEntery1 != null && masterEntery1.size() > 0) {
                    global.setsGlobalSpouseGUID(masterEntery1.get(0)
                            .getSpouse());
                }

                global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
                Intent i = new Intent(context, PregnantWomen.class);

                context.startActivity(i);
            }
        });
        String name = "", husbandname = "";

        String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + pregnant.get(position).getHHFamilyMemberGUID() + "'";
        String sql2 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID =(select Spouse from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + pregnant.get(position).getHHFamilyMemberGUID() + "')";
        name = Validate.returnStringValue(dataProvider.getRecord(sql1));
        husbandname = Validate.returnStringValue(dataProvider.getRecord(sql2));
        if (husbandname.length() > 0) {
            tvwname.setText(name + " " + "w/o" + " " + husbandname);
        } else if (pregnant.get(position).getPWName().length() > 0) {
            tvwname.setText(name);
        }
        String sqlhrpcount = "Select count(*) from tblancvisit where PWGUID='" + pregnant.get(position).getPWGUID() + "' and HighRisk=1";

        int hrpcount = dataProvider.getMaxRecord(sqlhrpcount);
        if (pregnant.get(position).getHighRisk() == 1 || hrpcount > 0) {
            tvwname.setTextColor(Color.RED);
        } else {
            tvwname.setTextColor(context.getResources().getColor(R.color.Blue));
        }


        tvwname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String anc_guid = pregnant.get(position).getPWGUID();
                // global.setsGlobalANCGUID(pregnant.get(position).getPW);
                ((AncActivity) context).openExpendableGridView(position);
                if (Validate.returnIntegerValue(tvGridhide.getText().toString()) == 0) {
                    gridVisits.setVisibility(View.VISIBLE);
                    tvGridhide.setText("1");
                    fillvisitdata(gridVisits, anc_guid);
                } else if (Integer.valueOf(tvGridhide.getText().toString()) == 1) {
                    gridVisits.setVisibility(View.GONE);
                    tvGridhide.setText("0");
                }

            }
        });
        btnhousehold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID(pregnant.get(position)
                        .getHHGUID());
                i.putExtra("flag", 4);
                context.startActivity(i);

            }
        });

        return gridview;
    }

    @SuppressWarnings("unused")
    public void showreport(String PWGUID, int position) {
        PicVideo_PreviewPopUp = new Dialog(context);
        Window window = PicVideo_PreviewPopUp.getWindow();
        // PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
        // PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
        // new ColorDrawable(Color.TRANSPARENT));
        //window.requestFeature(Window.FEATURE_NO_TITLE);
        PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
                color.white);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * 20.2;
        Double height = metrics.heightPixels * 5.3;
        Window win = PicVideo_PreviewPopUp.getWindow();
        win.setLayout(width.intValue(), height.intValue());


        PicVideo_PreviewPopUp.setContentView(R.layout.popupcheckupanc);
        PicVideo_PreviewPopUp.setCancelable(true);
        PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
        PicVideo_PreviewPopUp.show();
        PicVideo_PreviewPopUp.setTitle(context.getResources().getString(R.string.report));
        weight1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight1);
        weight2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight2);
        weight3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight3);
        weight4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.weight4);
        bp1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp1);
        bp2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp2);
        bp3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp3);
        bp4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bp4);
        hb1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb1);
        hb2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb2);
        hb3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb3);
        hb4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hb4);
        us1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us1);
        us2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us2);
        us3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us3);
        us4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.us4);
        ua1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua1);
        ua2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua2);
        ua3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua3);
        ua4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.ua4);
        BMI1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI1);
        BMI2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI2);
        BMI3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI3);
        BMI4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.BMI4);
        tt1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt1);
        tt2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt2);
        tt3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt3);
        tt4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt4);
        tt21 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt21);
        tt22 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt22);
        tt23 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt23);
        tt24 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tt24);
        IFA1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA1);
        IFA2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA2);
        IFA3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA3);
        IFA4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.IFA4);
        calcium1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium1);
        calcium2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium2);
        calcium3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium3);
        calcium4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.calcium4);
        TextView tv_lmp = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_lmp);
        TextView tv_height = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_height);
        TextView tv_edd = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_edd);
        TextView tv_name = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_name);
        TextView tv_age = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_age);
        final ImageView btn_Img1 = (ImageView) PicVideo_PreviewPopUp.findViewById(R.id.btn_Img1);
        final ImageView btn_Img2 = (ImageView) PicVideo_PreviewPopUp.findViewById(R.id.btn_Img2);
        final ImageView btn_Img3 = (ImageView) PicVideo_PreviewPopUp.findViewById(R.id.btn_Img3);
        final ImageView btn_Img4 = (ImageView) PicVideo_PreviewPopUp.findViewById(R.id.btn_Img4);
        final TextView tv_img1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_img1);
        final TextView tv_img2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_img2);
        final TextView tv_img3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_img3);
        final TextView tv_img4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_img4);
        TableRow tbl_af = (TableRow) PicVideo_PreviewPopUp.findViewById(R.id.tbl_af);
        if (global.getiGlobalRoleID() == 4) {
            tbl_af.setVisibility(View.VISIBLE);
        } else {
            tbl_af.setVisibility(View.GONE);
        }
        dangersigns1 = (TextView) PicVideo_PreviewPopUp
                .findViewById(R.id.dangersigns1);
        dangersigns2 = (TextView) PicVideo_PreviewPopUp
                .findViewById(R.id.dangersigns2);
        dangersigns3 = (TextView) PicVideo_PreviewPopUp
                .findViewById(R.id.dangersigns3);
        dangersigns4 = (TextView) PicVideo_PreviewPopUp
                .findViewById(R.id.dangersigns4);
        HRP1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP1);
        HRP2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP2);
        HRP3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP3);
        HRP4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP4);
        Height1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height1);
        Height2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height2);
        Height3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height3);
        Height4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Height4);
        anc_date1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.anc_date1);
        anc_date2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.anc_date2);
        anc_date3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.anc_date3);
        anc_date4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.anc_date4);
        HRP = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.HRP);
        if (pregnant.get(position).getPWHeight() > 0) {
            Height1.setText(String
                    .valueOf(pregnant.get(position).getPWHeight()));
            Height2.setText(String
                    .valueOf(pregnant.get(position).getPWHeight()));
            Height3.setText(String
                    .valueOf(pregnant.get(position).getPWHeight()));
            Height4.setText(String
                    .valueOf(pregnant.get(position).getPWHeight()));
            tv_height.setText(context.getResources().getString(R.string.Height) + " : " + String.valueOf(pregnant.get(position).getPWHeight()));
            tv_height.setText(context.getResources().getString(R.string.Height) + " : " + String.valueOf(pregnant.get(position).getPWHeight()));
        }
        tv_lmp.setText(context.getResources().getString(R.string.lmp) + " : " + Validate.changeDateFormat(pregnant.get(position).getLMPDate()));
        tv_edd.setText(context.getResources().getString(R.string.edd1) + " : " + Validate.changeDateFormat(pregnant.get(position).getEDDDate()));
        String name = "";

        String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + pregnant.get(position).getHHFamilyMemberGUID() + "'";

        name = Validate.returnStringValue(dataProvider.getRecord(sql1));
        tv_name.setText(name);
        tv_age.setText(context.getResources().getString(R.string.age) + " : " + pregnant.get(position).getPWAgeYears());

        String hr = "";
        if (pregnant.get(position).getHighRisk() == 1) {
            hr = context.getResources().getString(R.string.yes);
        } else {
            hr = context.getResources().getString(R.string.no);
        }

        HRP.setText(String.valueOf(hr));
        HRP1.setText(String.valueOf(hr));
        HRP2.setText(String.valueOf(hr));
        HRP3.setText(String.valueOf(hr));
        HRP4.setText(String.valueOf(hr));
        String sql = "Select VisitGUID from tblANCVisit where PWGUID='" + PWGUID
                + "' order by Visit_No";
        final ArrayList<HashMap<String, String>> data = dataProvider.getDynamicVal(sql);
        btn_Img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_img1.getText().toString().equals("1")) {
                    tv_img1.setText("2");
                    btn_Img1.setImageResource(R.drawable.afgreen_128dp);
                } else if (tv_img1.getText().toString().equals("2")) {
                    tv_img1.setText("3");
                    btn_Img1.setImageResource(R.drawable.afred_128dp);
                } else {
                    tv_img1.setText("1");
                    btn_Img1.setImageResource(R.drawable.aforange_128dp);
                }
                if (data != null && data.size() > 0) {
                    Validate.verificationanc(global.getUserID(), 4, data.get(0).get("VisitGUID"), Validate.returnIntegerValue(tv_img1.getText().toString()), global.getsGlobalAshaCode(), global.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"));
                }

            }
        });
        btn_Img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_img2.getText().toString().equals("1")) {
                    tv_img2.setText("2");
                    btn_Img2.setImageResource((R.drawable.afgreen_128dp));
                } else if (tv_img2.getText().toString().equals("2")) {
                    tv_img2.setText("3");
                    btn_Img2.setImageResource((R.drawable.afred_128dp));
                } else {
                    tv_img2.setText("1");
                    btn_Img2.setImageResource((R.drawable.aforange_128dp));
                }
                if (data != null && data.size() > 1) {
                    Validate.verificationanc(global.getUserID(), 4, data.get(1).get("VisitGUID"), Validate.returnIntegerValue(tv_img2.getText().toString()), global.getsGlobalAshaCode(), global.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"));
                }

            }
        });
        btn_Img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_img3.getText().toString().equals("1")) {
                    tv_img3.setText("2");
                    btn_Img3.setImageResource((R.drawable.afgreen_128dp));
                } else if (tv_img3.getText().toString().equals("2")) {
                    tv_img3.setText("3");
                    btn_Img3.setImageResource((R.drawable.afred_128dp));
                } else {
                    tv_img3.setText("1");
                    btn_Img3.setImageResource((R.drawable.aforange_128dp));
                }
                if (data != null && data.size() > 2) {
                    Validate.verificationanc(global.getUserID(), 4, data.get(2).get("VisitGUID"), Validate.returnIntegerValue(tv_img3.getText().toString()), global.getsGlobalAshaCode(), global.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"));
                }

            }
        });
        btn_Img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv_img4.getText().toString().equals("1")) {
                    tv_img4.setText("2");
                    btn_Img4.setImageResource((R.drawable.afgreen_128dp));
                } else if (tv_img4.getText().toString().equals("2")) {
                    tv_img4.setText("3");
                    btn_Img4.setImageResource((R.drawable.afred_128dp));
                } else {
                    tv_img4.setText("1");
                    btn_Img4.setImageResource((R.drawable.aforange_128dp));
                }
                if (data != null && data.size() > 3) {
                    Validate.verificationanc(global.getUserID(), 4, data.get(3).get("VisitGUID"), Validate.returnIntegerValue(tv_img4.getText().toString()), global.getsGlobalAshaCode(), global.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"));
                }

            }
        });
        filldata(PWGUID, pregnant.get(position).getHighRisk());
    }

    public void filldata(String PWGUID, int highrisk) {
        try {
            VisitANC2 = dataProvider.getTbl_VisitANCData(PWGUID, "0", 0);
            DecimalFormat df = new DecimalFormat("####0.00");

            if (VisitANC2 != null && VisitANC2.size() > 0) {
                if (VisitANC2.get(0).getBirthWeight() > 0) {
                    weight1.setText(String.valueOf(VisitANC2.get(0)
                            .getBirthWeight()));
                    double bmiCalculated = 0;
                    bmiCalculated = bmi(weight1.getText().toString(), Height1
                            .getText().toString());
                    BMI1.setText(String.valueOf(df.format(bmiCalculated)));
                } else {
                    weight1.setText("");
                }
                if (VisitANC2.get(1).getBirthWeight() > 0) {
                    weight2.setText(String.valueOf(VisitANC2.get(1)
                            .getBirthWeight()));
                    double bmiCalculated = 0;
                    bmiCalculated = bmi(weight2.getText().toString(), Height2
                            .getText().toString());

                    BMI2.setText(String.valueOf(df.format(bmiCalculated)));
                } else {
                    weight2.setText("");
                }
                if (VisitANC2.get(2).getBirthWeight() > 0) {
                    weight3.setText(String.valueOf(VisitANC2.get(2)
                            .getBirthWeight()));
                    double bmiCalculated = 0;
                    bmiCalculated = bmi(weight3.getText().toString(), Height3
                            .getText().toString());

                    BMI3.setText(String.valueOf(df.format(bmiCalculated)));
                } else {
                    weight3.setText("");
                }
                if (VisitANC2.get(3).getBirthWeight() > 0) {
                    weight4.setText(String.valueOf(VisitANC2.get(3)
                            .getBirthWeight()));
                    double bmiCalculated = 0;
                    bmiCalculated = bmi(weight4.getText().toString(), Height4
                            .getText().toString());
                    BMI4.setText(String.valueOf(df.format(bmiCalculated)));
                } else {
                    weight4.setText("");
                }
                if (VisitANC2.get(0).getBPResult() != null
                        && VisitANC2.get(0).getBPResult().length() > 0) {
                    String ss = VisitANC2.get(0).getBPResult();
                    ss = ss.replace(",", "/");
                    bp1.setText(ss);
                } else {
                    bp1.setText("");
                }
                if (VisitANC2.get(1).getBPResult() != null
                        && VisitANC2.get(1).getBPResult().length() > 0) {
                    String ss = VisitANC2.get(1).getBPResult();
                    ss = ss.replace(",", "/");
                    bp2.setText(ss);
                } else {
                    bp2.setText("");
                }
                if (VisitANC2.get(2).getBPResult() != null
                        && VisitANC2.get(2).getBPResult().length() > 0) {
                    String ss = VisitANC2.get(2).getBPResult();
                    ss = ss.replace(",", "/");
                    bp3.setText(ss);
                } else {
                    bp3.setText("");
                }
                if (VisitANC2.get(3).getBPResult() != null
                        && VisitANC2.get(3).getBPResult().length() > 0) {
                    String ss = VisitANC2.get(3).getBPResult();
                    ss = ss.replace(",", "/");
                    bp4.setText(ss);
                } else {
                    bp4.setText("");
                }
                if (VisitANC2.get(0).getHemoglobin() > 0) {
                    hb1.setText(String
                            .valueOf(VisitANC2.get(0).getHemoglobin()));
                } else {
                    hb1.setText("");
                }
                if (VisitANC2.get(1).getHemoglobin() > 0) {
                    hb2.setText(String
                            .valueOf(VisitANC2.get(1).getHemoglobin()));
                } else {
                    hb2.setText("");
                }
                if (VisitANC2.get(2).getHemoglobin() > 0) {
                    hb3.setText(String
                            .valueOf(VisitANC2.get(2).getHemoglobin()));
                } else {
                    hb3.setText("");
                }
                if (VisitANC2.get(3).getHemoglobin() > 0) {
                    hb4.setText(String
                            .valueOf(VisitANC2.get(3).getHemoglobin()));
                } else {
                    hb4.setText("");
                }
                if (VisitANC2.get(0).getCheckupVisitDate().length() > 0) {
                    anc_date1.setText(Validate.changeDateFormat(VisitANC2.get(0).getCheckupVisitDate()));
                } else {
                    anc_date1.setText("");
                }
                if (VisitANC2.get(1).getCheckupVisitDate().length() > 0) {
                    anc_date2.setText(Validate.changeDateFormat(VisitANC2.get(1).getCheckupVisitDate()));
                } else {
                    anc_date2.setText("");
                }
                if (VisitANC2.get(2).getCheckupVisitDate().length() > 0) {
                    anc_date3.setText(Validate.changeDateFormat(VisitANC2.get(2).getCheckupVisitDate()));
                } else {
                    anc_date3.setText("");
                }
                if (VisitANC2.get(3).getCheckupVisitDate().length() > 0) {
                    anc_date4.setText(Validate.changeDateFormat(VisitANC2.get(3).getCheckupVisitDate()));
                } else {
                    anc_date4.setText("");
                }
                if (VisitANC2.get(0).getUrineSugar() == 1) {
                    us1.setText("+ve");

                } else if (VisitANC2.get(0).getUrineSugar() == 2) {
                    us1.setText("-ve");
                }
                if (VisitANC2.get(1).getUrineSugar() == 1) {
                    us2.setText("+ve");

                } else if (VisitANC2.get(1).getUrineSugar() == 2) {
                    us2.setText("-ve");
                }
                if (VisitANC2.get(2).getUrineSugar() == 1) {
                    us3.setText("+ve");

                } else if (VisitANC2.get(2).getUrineSugar() == 2) {
                    us3.setText("-ve");
                }
                if (VisitANC2.get(3).getUrineSugar() == 1) {
                    us4.setText("+ve");

                } else if (VisitANC2.get(3).getUrineSugar() == 2) {
                    us4.setText("-ve");
                }
                if (VisitANC2.get(0).getUrineAlbumin() == 1) {
                    ua1.setText("+ve");

                } else if (VisitANC2.get(0).getUrineAlbumin() == 2) {
                    ua1.setText("-ve");
                }
                if (VisitANC2.get(1).getUrineAlbumin() == 1) {
                    ua2.setText("+ve");

                } else if (VisitANC2.get(1).getUrineAlbumin() == 2) {
                    ua2.setText("-ve");
                }
                if (VisitANC2.get(2).getUrineAlbumin() == 1) {
                    ua3.setText("+ve");

                } else if (VisitANC2.get(2).getUrineAlbumin() == 2) {
                    ua3.setText("-ve");
                }
                if (VisitANC2.get(3).getUrineAlbumin() == 1) {
                    ua4.setText("+ve");

                } else if (VisitANC2.get(3).getUrineAlbumin() == 2) {
                    ua4.setText("-ve");
                }

                if (VisitANC2.get(0).getTTfirstDoseDate().length() > 0) {
                    tt1.setText(Validate.changeDateFormat(VisitANC2.get(0).getTTfirstDoseDate()));

                } else {
                    tt1.setText("");
                }
                if (VisitANC2.get(1).getTTfirstDoseDate().length() > 0 && tt1.getText().toString().length() == 0 && tt3.getText().toString().length() == 0 && tt4.getText().toString().length() == 0) {
                    tt2.setText(Validate.changeDateFormat(VisitANC2.get(1).getTTfirstDoseDate()));

                } else {
                    tt2.setText("");
                }
                if (VisitANC2.get(2).getTTfirstDoseDate().length() > 0 && tt1.getText().toString().length() == 0 && tt2.getText().toString().length() == 0 && tt4.getText().toString().length() == 0) {
                    tt3.setText(Validate.changeDateFormat(VisitANC2.get(2).getTTfirstDoseDate()));

                } else {
                    tt3.setText("");
                }
                if (VisitANC2.get(3).getTTfirstDoseDate().length() > 0 && tt1.getText().toString().length() == 0 && tt2.getText().toString().length() == 0 && tt3.getText().toString().length() == 0) {
                    tt4.setText(Validate.changeDateFormat(VisitANC2.get(3).getTTfirstDoseDate()));

                } else {
                    tt4.setText("");
                }
                if (VisitANC2.get(0).getTTBoosterDate().length() > 0 && tt1.getText().toString().length() == 0) {
                    tt1.setText(Validate.changeDateFormat(VisitANC2.get(0).getTTBoosterDate()));

                }
                if (VisitANC2.get(1).getTTBoosterDate().length() > 0 && tt1.getText().toString().length() == 0 && tt2.getText().toString().length() == 0 && tt3.getText().toString().length() == 0 && tt4.getText().toString().length() == 0) {
                    tt2.setText(Validate.changeDateFormat(VisitANC2.get(1).getTTBoosterDate()));

                }
                if (VisitANC2.get(2).getTTBoosterDate().length() > 0 && tt3.getText().toString().length() == 0 && tt1.getText().toString().length() == 0 && tt2.getText().toString().length() == 0 && tt4.getText().toString().length() == 0) {
                    tt3.setText(Validate.changeDateFormat(VisitANC2.get(2).getTTBoosterDate()));

                }
                if (VisitANC2.get(3).getTTBoosterDate().length() > 0 && tt4.getText().toString().length() == 0 && tt1.getText().toString().length() == 0 && tt2.getText().toString().length() == 0 && tt3.getText().toString().length() == 0) {
                    tt4.setText(Validate.changeDateFormat(VisitANC2.get(3).getTTBoosterDate()));

                }
                if (VisitANC2.get(0).getTTsecondDoseDate().length() > 0) {
                    tt21.setText(Validate.changeDateFormat(VisitANC2.get(0).getTTsecondDoseDate()));

                } else {
                    tt21.setText("");
                }
                if (VisitANC2.get(1).getTTsecondDoseDate().length() > 0 && tt21.getText().toString().length() == 0 && tt23.getText().toString().length() == 0 && tt24.getText().toString().length() == 0) {
                    tt22.setText(Validate.changeDateFormat(VisitANC2.get(1).getTTsecondDoseDate()));

                } else {
                    tt22.setText("");
                }
                if (VisitANC2.get(2).getTTsecondDoseDate().length() > 0 && tt21.getText().toString().length() == 0 && tt22.getText().toString().length() == 0 && tt24.getText().toString().length() == 0) {
                    tt23.setText(Validate.changeDateFormat(VisitANC2.get(2).getTTsecondDoseDate()));

                } else {
                    tt23.setText("");
                }
                if (VisitANC2.get(3).getTTsecondDoseDate().length() > 0 && tt21.getText().toString().length() == 0 && tt22.getText().toString().length() == 0 && tt23.getText().toString().length() == 0) {
                    tt24.setText(Validate.changeDateFormat(VisitANC2.get(3).getTTsecondDoseDate()));

                } else {
                    tt24.setText("");
                }
                if (VisitANC2.get(0).getIFARecieved() > 0) {
                    IFA1.setText(String.valueOf(VisitANC2.get(0)
                            .getNumberIFARecieved()));

                } else {
                    IFA1.setText("");
                }
                if (VisitANC2.get(1).getIFARecieved() > 0) {
                    IFA2.setText(String.valueOf(VisitANC2.get(1)
                            .getNumberIFARecieved()));

                } else {
                    IFA2.setText("");
                }
                if (VisitANC2.get(2).getIFARecieved() > 0) {
                    IFA3.setText(String.valueOf(VisitANC2.get(2)
                            .getNumberIFARecieved()));

                } else {
                    IFA3.setText("");
                }
                if (VisitANC2.get(3).getIFARecieved() > 0) {
                    IFA4.setText(String.valueOf(VisitANC2.get(3)
                            .getNumberIFARecieved()));

                } else {
                    IFA4.setText("");
                }
                if (VisitANC2.get(0).getCalciumReceived() > 0) {
                    calcium1.setText(String.valueOf(VisitANC2.get(0)
                            .getCalciumTablet()));

                } else {
                    calcium1.setText("");
                }
                if (VisitANC2.get(1).getCalciumReceived() > 0) {
                    calcium2.setText(String.valueOf(VisitANC2.get(1)
                            .getCalciumTablet()));

                } else {
                    calcium2.setText("");
                }
                if (VisitANC2.get(2).getCalciumReceived() > 0) {
                    calcium3.setText(String.valueOf(VisitANC2.get(2)
                            .getCalciumTablet()));

                } else {
                    calcium3.setText("");
                }
                if (VisitANC2.get(3).getCalciumReceived() > 0) {
                    calcium4.setText(String.valueOf(VisitANC2.get(3)
                            .getCalciumTablet()));

                } else {
                    calcium4.setText("");
                }
                if (VisitANC2.get(0).getDangerSign() > 0) {
                    if (Integer.valueOf(VisitANC2.get(0).getDangerSign()) <= arr.length)
                        dangersigns1.setText(arr[Integer.valueOf(VisitANC2.get(
                                0).getDangerSign()) - 1]);

                } else {
                    dangersigns1.setText("");
                }
                if (VisitANC2.get(1).getDangerSign() > 0) {
                    if (Integer.valueOf(VisitANC2.get(1).getDangerSign()) <= arr1.length)
                        dangersigns2.setText(arr1[Integer.valueOf(VisitANC2
                                .get(1).getDangerSign()) - 1]);

                } else {
                    dangersigns2.setText("");
                }
                if (VisitANC2.get(2).getDangerSign() > 0) {
                    if (Integer.valueOf(VisitANC2.get(2).getDangerSign()) <= arr1.length)
                        dangersigns3.setText(arr1[Integer.valueOf(VisitANC2
                                .get(2).getDangerSign()) - 1]);

                } else {
                    dangersigns3.setText("");
                }
                if (VisitANC2.get(3).getDangerSign() > 0) {
                    if (Integer.valueOf(VisitANC2.get(3).getDangerSign()) <= arr1.length)
                        dangersigns4.setText(arr1[Integer.valueOf(VisitANC2
                                .get(3).getDangerSign()) - 1]);

                } else {
                    dangersigns4.setText("");
                }
                if (highrisk == 0) {

                    if (VisitANC2.get(0).getHighRisk() == 1) {
                        HRP1.setText(context.getResources().getString(R.string.yes));
                        HRP.setText(context.getResources().getString(R.string.yes));

                    } else {
                        HRP1.setText(context.getResources().getString(R.string.no));
                        HRP.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(1).getDangerSign() == 1) {
                        HRP2.setText(context.getResources().getString(R.string.yes));
                        HRP.setText(context.getResources().getString(R.string.yes));

                    } else {
                        HRP2.setText(context.getResources().getString(R.string.no));
                        HRP.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(2).getDangerSign() == 1) {

                        HRP3.setText(context.getResources().getString(R.string.yes));
                        HRP.setText(context.getResources().getString(R.string.yes));
                    } else {
                        HRP3.setText(context.getResources().getString(R.string.no));
                        HRP.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(3).getDangerSign() == 1) {
                        HRP4.setText(context.getResources().getString(R.string.yes));
                        HRP.setText(context.getResources().getString(R.string.yes));

                    } else {
                        HRP4.setText(context.getResources().getString(R.string.no));
                        HRP.setText(context.getResources().getString(R.string.no));
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double bmi(String weight, String height) {

        // Double height = pregnant.get(0).getPWHeight();
        double Bmi = 0;
        double ht = 1;
        if (!weight.equalsIgnoreCase("") && weight.length() > 0
                && !height.equalsIgnoreCase("") && height.length() > 0) {
            if (!height.equalsIgnoreCase("null")) {
                ht = Double.valueOf(height);
            }
            double newHeight = Double.valueOf(ht * ht);
            Bmi = (Double.valueOf(weight) / newHeight) * 10000;
        }
        return Bmi;
    }

    public String returnPosition(int flag, int ID) {
        String ipos = "";
        ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
        combobox = dataProvider.getCommonRecord(1, flag);
        for (int i = 0; i < combobox.size(); i++) {
            if (ID == combobox.get(i).getID()) {
                ipos = combobox.get(i).getValue();
            }
        }

        return ipos;
    }


    public void fillvisitdata(GridView gridVisits, String sPWGUID) {
        VisitANC = dataProvider.getTbl_VisitANCData(sPWGUID, "", 0);
        if (VisitANC != null && VisitANC.size() > 0) {

            android.view.ViewGroup.LayoutParams params = gridVisits
                    .getLayoutParams();
            Resources r = context.getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * VisitANC.size();
            params.height = gridHeight1;
            gridVisits.setLayoutParams(params);
            gridVisits.setAdapter(new AncVisitAdapter(context, VisitANC));

        }
    }
}