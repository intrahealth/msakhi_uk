package com.microware.intrahealth.AFAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.microware.intrahealth.Immunization_Entry;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Aditya on 10/4/2018.
 */

@SuppressLint("InflateParams")
public class AFChild_Imunnization_adapter extends BaseAdapter {

    Context context;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    ArrayList<Child_Imunization_Object> ChildImmun2 = new ArrayList<Child_Imunization_Object>();
    Global global;
    DataProvider dataProvider;

    String fathername = "", mothername = "";

    public AFChild_Imunnization_adapter(Context context,
                                      ArrayList<Child_Imunization_Object> ChildImmun) {
        this.ChildImmun = ChildImmun;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return ChildImmun.size();
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
            gridview = layoutInflater.inflate(
                    R.layout.af_immunization_grid_adapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        TextView tvparentsname = (TextView) gridview
                .findViewById(R.id.tvParentsname);
        ImageView btn_gender = (ImageView) gridview.findViewById(R.id.btn_gender);
        ImageView btn_status = (ImageView) gridview.findViewById(R.id.btn_status);
        TextView tvChildDob = (TextView) gridview.findViewById(R.id.tvChildDob);
        ImageView btnhousehold = (ImageView) gridview
                .findViewById(R.id.btnhousehold);

        String Sparentsname = "";
        tvparentsname.setTextColor(context.getResources().getColor(R.color.Blue));

        String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + ChildImmun.get(position).getHHFamilyMemberGUID() + "'";
        String sql2 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID =(select Spouse from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + ChildImmun.get(position).getHHFamilyMemberGUID() + "')";
        mothername = dataProvider.getRecord(sql1);
        fathername = dataProvider.getRecord(sql2);
        Sparentsname = Validate.returnStringValue(ChildImmun.get(
                position).getChild_name())
                + " /" + Validate.returnStringValue(mothername);

        tvparentsname.setText(Sparentsname);
//        if (ChildImmun.get(position).getWomenname() != null
//                && ChildImmun.get(position).getWomenname().length() > 0) {
//
//            mothername = ChildImmun.get(position).getWomenname();
//            fathername = ChildImmun.get(position).getHusbanname();
//            Sparentsname = String.valueOf(String.valueOf(ChildImmun.get(
//                    position).getChild_name()))
//                    + " /" + String.valueOf(mothername);
//
//            tvparentsname.setText(Sparentsname);
//        } else {
//
//            ChildImmun2 = dataProvider.gettblCHildImmunization();
//            if (ChildImmun2 != null && ChildImmun2.size() > 0) {
//
//                for (int i = 0; i < ChildImmun2.size(); i++) {
//                    if ((ChildImmun.get(position).getChildGUID())
//                            .equalsIgnoreCase(ChildImmun2.get(i).getChildGUID())) {
//                        mothername = ChildImmun2.get(i).getFamilyMemberName();
//
//                        if (ChildImmun2.get(i).getSpouse() != null
//                                && ChildImmun2.get(i).getSpouse().length() > 0) {
//
//                            String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
//                                    + ChildImmun2.get(i).getSpouse() + "'";
//                            fathername = dataProvider.getRecord(sql1);
//                        }
//                    }
//                }
//            }
//
//            Sparentsname = String.valueOf(ChildImmun.get(position)
//                    .getChild_name()) + " / " + String.valueOf(mothername);
//            tvparentsname.setText(Sparentsname);
//        }

        int iGender = ChildImmun.get(position).getGender();
        if (iGender == 1) {
            btn_gender.setBackgroundResource(R.drawable.girl);
        } else if (iGender == 2) {
            btn_gender.setBackgroundResource(R.drawable.boy);
            //tvGender.setText(context.getResources().getString(R.string.boy));
        } else {
            btn_gender.setBackgroundResource(R.drawable.blankgender);
            // tvGender.setText("");
        }
        try {
            if (Validate.returnStringValue(ChildImmun.get(position).getWt_of_child()).length() > 0) {
                if (Float.valueOf(Validate.returnStringValue(ChildImmun.get(position).getWt_of_child())) < 2.5) {
                    btn_status.setBackgroundResource(R.drawable.lbw);
                } else {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "select count(*) from tblchild where bcg is not null and bcg!='' and opv1 is not null and opv1!='' and opv2 is not null and opv2!='' and  opv3 is not null and opv3!='' and Pentavalent1 is not null and Pentavalent1!='' and Pentavalent2 is not null and Pentavalent2!='' and Pentavalent3 is not null and Pentavalent3!='' and measeals is not null and measeals!='' and ChildGUID='" + ChildImmun.get(position).getChildGUID() + "'";
        int counthappy = dataProvider.getMaxRecord(sql);
        if (counthappy > 0) {
            btn_status.setBackgroundResource(R.drawable.happychild);
        } else {

        }
        tvChildDob.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                .get(position).getDob())));
        long age = Validate.Daybetween(ChildImmun.get(position).getDob(), Validate.getcurrentdate());
        if (age <= 28) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet1);
            //tvGender.setBackgroundResource(R.drawable.childsheet1);
            tvChildDob.setBackgroundResource(R.drawable.childsheet1);
        } else if (age <= 365) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet2);
            // tvGender.setBackgroundResource(R.drawable.childsheet2);
            tvChildDob.setBackgroundResource(R.drawable.childsheet2);
        } else if (age <= 730) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet3);
            //tvGender.setBackgroundResource(R.drawable.childsheet3);
            tvChildDob.setBackgroundResource(R.drawable.childsheet3);
        } else if (age <= 1825) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet4);
            // tvGender.setBackgroundResource(R.drawable.childsheet4);
            tvChildDob.setBackgroundResource(R.drawable.childsheet4);
        } else {
            tvparentsname.setBackgroundResource(R.drawable.childsheet5);
            //tvGender.setBackgroundResource(R.drawable.childsheet5);
            tvChildDob.setBackgroundResource(R.drawable.childsheet5);
        }
//        String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
//                + ChildImmun.get(position).getChildGUID() + "'";
//        String stat = dataProvider.getRecord(status);
//        if(Validate.returnIntegerValue(stat)==3){
//            tvparentsname.setBackgroundResource(R.drawable.yellowsheet);
//            tvGender.setBackgroundResource(R.drawable.yellowsheet);
//            tvChildDob.setBackgroundResource(R.drawable.yellowsheet);
//        }else{
//            tvparentsname.setBackgroundResource(R.drawable.whitesheet);
//            tvGender.setBackgroundResource(R.drawable.whitesheet);
//            tvChildDob.setBackgroundResource(R.drawable.whitesheet);
//        }
        tvparentsname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(), Immunization_Entry.class);
                String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                        + ChildImmun.get(position).getHHFamilyMemberGUID() + "'";
                String sql2 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID =(select Spouse from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                        + ChildImmun.get(position).getHHFamilyMemberGUID() + "')";
                mothername = dataProvider.getRecord(sql1);
                fathername = dataProvider.getRecord(sql2);
                i.putExtra("MotherName", Validate.returnStringValue(mothername));
                i.putExtra("HusbandName", Validate.returnStringValue(fathername));
                i.putExtra("ChildGUID",
                        String.valueOf(ChildImmun.get(position).getChildGUID()));
                i.putExtra("ChildName", String.valueOf(ChildImmun.get(position)
                        .getChild_name()));
                i.putExtra("DOB",
                        String.valueOf(ChildImmun.get(position).getDob()));

                context.startActivity(i);

            }
        });
        btnhousehold.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID(ChildImmun.get(position)
                        .getHHGUID());
                i.putExtra("flag", 5);
                context.startActivity(i);

            }
        });
        btn_gender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showreport(ChildImmun.get(position).getChildGUID());

            }
        });

        return gridview;
    }

    public void showreport(String PWGUID) {
        Dialog PicVideo_PreviewPopUp = new Dialog(context);
        Window window = PicVideo_PreviewPopUp.getWindow();

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
        PicVideo_PreviewPopUp.setContentView(R.layout.childreport);
        PicVideo_PreviewPopUp.setCancelable(true);
        PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
        PicVideo_PreviewPopUp.show();
        TextView tv_visit1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit1);
        TextView tv_visit2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit2);
        TextView tv_visit3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit3);
        TextView tv_visit4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit4);
        TextView tv_visit5 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit5);
        TextView tv_visit6 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit6);
        TextView tv_visit7 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visit7);
        TextView tv_visitdate1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate1);
        TextView tv_visitdate2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate2);
        TextView tv_visitdate3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate3);
        TextView tv_visitdate4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate4);
        TextView tv_visitdate5 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate5);
        TextView tv_visitdate6 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate6);
        TextView tv_visitdate7 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_visitdate7);
        ArrayList<HashMap<String, String>> data, data2;
        String sql = "Select * from tblPNChomevisit_ANS where ChildGUID = '"
                + PWGUID + "' ";
        String sqlchild = "Select * from tblchild where ChildGUID = '"
                + PWGUID + "' ";
        data = dataProvider.getDynamicVal(sql);
        data2 = dataProvider.getDynamicVal(sqlchild);
        tv_visit1.setText(context.getResources().getString(R.string.visitpnc) + "-" + 1);
        tv_visit2.setText(context.getResources().getString(R.string.visitpnc) + "-" + 2);
        tv_visit3.setText(context.getResources().getString(R.string.visitpnc) + "-" + 3);
        tv_visit4.setText(context.getResources().getString(R.string.visitpnc) + "-" + 4);
        tv_visit5.setText(context.getResources().getString(R.string.visitpnc) + "-" + 5);
        tv_visit6.setText(context.getResources().getString(R.string.visitpnc) + "-" + 6);
        tv_visit7.setText(context.getResources().getString(R.string.visitpnc) + "-" + 7);
        if (data2 != null && data2.size() > 0) {
            PicVideo_PreviewPopUp.setTitle(data2.get(0).get("child_name"));
            if (Validate.returnIntegerValue(data2.get(0).get("place_of_birth")) == 2) {
                tv_visitdate1.setText(Validate.changeDateFormat(data2.get(0).get("Child_dob")));
            }
            for (int i = 0; i < data.size(); i++) {
                if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 1) {
                    tv_visitdate1.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                } if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 2) {
                    tv_visitdate2.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                } if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 3) {
                    tv_visitdate3.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                } if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 4) {
                    tv_visitdate4.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                } if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 5) {
                    tv_visitdate5.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                } if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 6) {
                    tv_visitdate6.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                }
                if (Validate.returnIntegerValue(data.get(i).get("VisitNo")) == 7) {
                    tv_visitdate7.setText(Validate.changeDateFormat(data.get(i).get("Q_0")));
                }
            }

        }


    }
}
