package com.microware.intrahealth.AFAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.NewChildActivity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.childActivity;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tblChild;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aditya on 10/4/2018.
 */

@SuppressLint("InflateParams")
public class AFNewbornGridAdapter extends BaseAdapter {
    Context context;
    ArrayList<tblChild> Child = new ArrayList<tblChild>();
    ArrayList<Tbl_HHFamilyMember> pregnant = new ArrayList<Tbl_HHFamilyMember>();
    Global global;
    DataProvider dataProvider;
    int StatusId = 0;

    ArrayList<TblANCVisit> VisitANC2 = new ArrayList<TblANCVisit>();

    Dialog PicVideo_PreviewPopUp;
    TextView weight1, weight2, weight3, weight4, bp1, bp2, bp3, bp4, hb1, hb2,
            hb3, hb4, us1, us2, us3, us4, ua1, ua2, ua3, ua4, BMI1, BMI2, BMI3,
            BMI4, tt1, tt2, tt3, tt4, tt21, tt22, tt23, tt24, IFA1, IFA2, IFA3, IFA4, calcium1,
            calcium2, calcium3, calcium4, dangersigns1, dangersigns2,
            dangersigns3, dangersigns4, HRP1, HRP2, HRP3, HRP4, Height1,
            Height2, Height3, Height4;
    String arr[] = {"अत्याधिक/गंभीर एनीमिया",
            "गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
            "पेट में तेज दर्द", "दौरे आना या फिट्स पड़ना"};
    String arr1[] = {"अत्याधिक/गंभीर एनीमिया",
            "गर्भावस्था में योनि से रक्तस्राव", "तेज बुखार",
            "पेट में तेज दर्द", "सिर में तेज दर्द के साथ  धुंधला दिखना",
            "चेहरे, हाथों, पांवों में सूजन ", "दौरे आना या फिट्स पड़ना",};

    public AFNewbornGridAdapter(Context context,
                              ArrayList<tblChild> Child) {
        this.Child = Child;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Child.size();
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

    @SuppressWarnings("static-access")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.af_newbornadapter,
                    null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        TextView tvChild = (TextView) gridview.findViewById(R.id.tvChild);
        ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
        ImageView report = (ImageView) gridview.findViewById(R.id.report);
        ImageView btnhousehold = (ImageView) gridview
                .findViewById(R.id.btnhousehold);

        if (Child.get(position).getPw_GUID() != null && Child.get(position).getPw_GUID().length() > 0) {
            // String name = Child.get(position).getChild_name();
            pregnant = dataProvider.getAllmalename(Child.get(position).getHHFamilyMemberGUID());
            if (pregnant != null && pregnant.size() > 0) {
                tvChild.setText(String.valueOf(Child.get(position).getChild_name() + "/" + pregnant.get(0).getFamilyMemberName() + "/" + Validate.changeDateFormat(Child.get(position).getChild_dob())));
//		 tvChild.setBackgroundColor(Color.parseColor("#55FF0000"));

            } else {
                tvChild.setText(Child.get(position).getChild_name() + "/" + "/" + Validate.changeDateFormat(Child.get(position).getChild_dob()));
            }
            int count = 0;
            // tvwname.setEnabled(false);
//            String sql = "select count(*) from tblancvisit where PWGUID='"
//                    + Child.get(position).getPw_GUID()
//                    + "' and checkupVisitDate!=''";
//            count = dataProvider.getMaxRecord(sql);
//            report.setText(context.getResources().getString(R.string.report) + "  "
//                    + count);
            report.setVisibility(View.VISIBLE);
            // tvChild.setTextColor(Color.RED);
        } else {
            //String name = Child.get(position).getChild_name();
            pregnant = dataProvider.getAllmalename(Child.get(position).getHHFamilyMemberGUID());
            if (pregnant != null && pregnant.size() > 0) {
                tvChild.setText(String.valueOf(Child.get(position).getChild_name() + "/" + pregnant.get(0).getFamilyMemberName() + "/" + Validate.changeDateFormat(Child.get(position).getChild_dob())));
                // tvChild.setTextColor(Color.BLUE);
//		tvChild.setBackgroundColor(color.holo_orange_light);
            } else {
                tvChild.setText(Child.get(position).getChild_name() + "/" + "/" + Validate.changeDateFormat(Child.get(position).getChild_dob()));
            }
            report.setVisibility(View.GONE);
            tvChild.setTextColor(Color.BLACK);
        }
        long age = Validate.Daybetween(Child.get(position).getChild_dob(), Validate.getcurrentdate());
        if (age <= 28) {
            tvChild.setBackgroundResource(R.drawable.childsheet1);
            report.setBackgroundResource(R.drawable.childsheet1);
        } else if (age <= 365) {
            tvChild.setBackgroundResource(R.drawable.childsheet2);
            report.setBackgroundResource(R.drawable.childsheet2);
        } else if (age <= 730) {
            tvChild.setBackgroundResource(R.drawable.childsheet3);
            report.setBackgroundResource(R.drawable.childsheet3);
        } else if (age <= 1825) {
            tvChild.setBackgroundResource(R.drawable.childsheet4);
            report.setBackgroundResource(R.drawable.childsheet4);
        } else {
            tvChild.setBackgroundResource(R.drawable.childsheet5);
            report.setBackgroundResource(R.drawable.childsheet5);
        }
//        String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
//                + Child.get(position).getChildGUID() + "'";
//        String stat = dataProvider.getRecord(status);
//        if(Validate.returnIntegerValue(stat)==3){
//            tvChild.setBackgroundResource(R.drawable.yellowsheet);
//
//        }else{
//            tvChild.setBackgroundResource(R.drawable.whitesheet);
//
//        }
        report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                global.setGlobalHHFamilyMemberGUID(pregnant.get(position)
//                        .getHHFamilyMemberGUID());
//                global.setGlobalHHSurveyGUID(pregnant.get(position).getHHGUID());
//                global.setsGlobalPWGUID(pregnant.get(position).getPWGUID());
//                global.setsGlobalLMPDate(pregnant.get(position).getLMPDate());
                showreport(Child.get(position).getPw_GUID());
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                global.setsGlobalChildGUID(Child.get(position)
                        .getChildGUID());
                global.setGlobalHHSurveyGUID(Child.get(position)
                        .getHHGUID());
                global.setGlobalHHFamilyMemberGUID(Child.get(position).getHHFamilyMemberGUID());
                if (Child.get(position).getPw_GUID() != null && Child.get(position).getPw_GUID().length() > 0) {
                    Intent i = new Intent(v.getContext(),
                            childActivity.class);
                    global.setsGlobalPWGUID(Child.get(position).getPw_GUID());
                    context.startActivity(i);
                } else {
                    Intent i = new Intent(v.getContext(),
                            NewChildActivity.class);
                    context.startActivity(i);
                }
            }
        });

        btnhousehold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID(Child.get(position)
                        .getHHGUID());
                i.putExtra("flag", 1);
                context.startActivity(i);

            }
        });
        return gridview;
    }

    public void showreport(String PWGUID) {
        PicVideo_PreviewPopUp = new Dialog(context);
        Window window = PicVideo_PreviewPopUp.getWindow();
        // PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
        // PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
        // new ColorDrawable(Color.TRANSPARENT));
        PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
                android.R.color.white);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        Double width = metrics.widthPixels * 20.2;
        Double height = metrics.heightPixels * 5.3;
        Window win = PicVideo_PreviewPopUp.getWindow();
        win.setLayout(width.intValue(), height.intValue());
        // window.requestFeature(Window.FEATURE_NO_TITLE);

        PicVideo_PreviewPopUp.setContentView(R.layout.popupcheckup);
        PicVideo_PreviewPopUp.setCancelable(true);
        PicVideo_PreviewPopUp.setTitle(context.getResources().getString(R.string.Anccheckup));
        PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
        PicVideo_PreviewPopUp.show();

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
        ArrayList<HashMap<String, String>> data = null;
        String sql = "";
        sql = "select PWHeight,HighRisk from tblPregnant_woman where PWGUID='" + PWGUID + "'";
        data = dataProvider.getDynamicVal(sql);
        if (data != null && data.size() > 0) {
            Height1.setText(data.get(0).get("PWHeight"));
            Height2.setText(data.get(0).get("PWHeight"));
            Height3.setText(data.get(0).get("PWHeight"));
            Height4.setText(data.get(0).get("PWHeight"));
            String hr = "";
            if (Validate.returnIntegerValue(data.get(0).get("HighRisk")) == 1) {
                hr = context.getResources().getString(R.string.yes);
            } else {
                hr = context.getResources().getString(R.string.no);
            }

            HRP1.setText(String.valueOf(hr));
            HRP2.setText(String.valueOf(hr));
            HRP3.setText(String.valueOf(hr));
            HRP4.setText(String.valueOf(hr));

            filldata(PWGUID, Validate.returnIntegerValue(data.get(0).get("HighRisk")));
        }


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
                if (VisitANC2.get(0).getUrineSugar() > 0) {
                    us1.setText(returnPosition(102, VisitANC2.get(0)
                            .getUrineSugar()));

                } else {
                    us1.setText("");
                }
                if (VisitANC2.get(1).getUrineSugar() > 0) {
                    us2.setText(returnPosition(102, VisitANC2.get(1)
                            .getUrineSugar()));

                } else {
                    us2.setText("");
                }
                if (VisitANC2.get(2).getUrineSugar() > 0) {
                    us3.setText(returnPosition(102, VisitANC2.get(2)
                            .getUrineSugar()));

                } else {
                    us3.setText("");
                }
                if (VisitANC2.get(3).getUrineSugar() > 0) {
                    us4.setText(returnPosition(102, VisitANC2.get(3)
                            .getUrineSugar()));

                } else {
                    us4.setText("");
                }
                if (VisitANC2.get(0).getUrineAlbumin() > 0) {
                    ua1.setText(returnPosition(102, VisitANC2.get(0)
                            .getUrineAlbumin()));

                } else {
                    ua1.setText("");
                }
                if (VisitANC2.get(1).getUrineAlbumin() > 0) {
                    ua2.setText(returnPosition(102, VisitANC2.get(1)
                            .getUrineAlbumin()));

                } else {
                    ua2.setText("");
                }
                if (VisitANC2.get(2).getUrineAlbumin() > 0) {
                    ua3.setText(returnPosition(102, VisitANC2.get(2)
                            .getUrineAlbumin()));

                } else {
                    ua3.setText("");
                }
                if (VisitANC2.get(3).getUrineAlbumin() > 0) {
                    ua4.setText(returnPosition(102, VisitANC2.get(3)
                            .getUrineAlbumin()));

                } else {
                    ua4.setText("");
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

                    } else {
                        HRP1.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(1).getDangerSign() == 1) {
                        HRP2.setText(context.getResources().getString(R.string.yes));

                    } else {
                        HRP2.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(2).getDangerSign() == 1) {

                        HRP3.setText(context.getResources().getString(R.string.yes));
                    } else {
                        HRP3.setText(context.getResources().getString(R.string.no));
                    }
                    if (VisitANC2.get(3).getDangerSign() == 1) {
                        HRP4.setText(context.getResources().getString(R.string.yes));

                    } else {
                        HRP4.setText(context.getResources().getString(R.string.no));
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


}
