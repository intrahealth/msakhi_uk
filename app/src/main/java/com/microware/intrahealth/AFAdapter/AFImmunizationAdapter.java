package com.microware.intrahealth.AFAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.microware.intrahealth.ImmunizationQuestionActivity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

import java.util.ArrayList;

/**
 * Created by Aditya on 10/4/2018.
 */

@SuppressLint("InflateParams")
public class AFImmunizationAdapter extends BaseAdapter {

    Context context;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    ArrayList<Child_Imunization_Object> ChildImmun1 = new ArrayList<Child_Imunization_Object>();
    ArrayList<Child_Imunization_Object> ChildImmun2 = new ArrayList<Child_Imunization_Object>();
    Global global;
    TextView tv_IPV2,bcg, hep1, opv0, opv1, opv2, opv3, dpt1, dpt2, dpt3, hep0, hep2, hep3,
            Pentavalent1, Pentavalent2, Pentavalent3, measles, vitamin, tv_ipv, tv_JEVaccine1, tv_opvbooster, tv_DPTBooster, tv_measles2, tv_JEVaccine2, tv_vitamintwo, tv_vitamin3, tv_vitamin4, tv_vitamin5, tv_vitamin6, tv_vitamin7, tv_vitamin8, tv_vitamin9, tv_DPTBoostersecond, tv_tt, tv_tt2;
    DataProvider dataProvider;
    Dialog PicVideo_PreviewPopUp;

    String mothername = "";

    public AFImmunizationAdapter(Context context,
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
                    R.layout.af_immunizationgridadapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        TextView tvparentsname = (TextView) gridview
                .findViewById(R.id.tvParentsname);
        TextView tvAge = (TextView) gridview.findViewById(R.id.tvAge);
        TextView tv_count = (TextView) gridview.findViewById(R.id.tv_count);
        ImageView btncounselling = (ImageView) gridview.findViewById(R.id.btncounselling);
        ImageView btnimmune = (ImageView) gridview.findViewById(R.id.btnimmune);

        String Sparentsname = "";
        String sql1 = "select FamilyMemberName from tbl_HHFamilyMember where HHFamilyMemberGUID = '"
                + ChildImmun.get(position).getHHFamilyMemberGUID() + "'";

        mothername = dataProvider.getRecord(sql1);
//        String sname = "";
        int count = 0;
        String sqlcount = "select count(*) from tblmstimmunizationANS where ChildGUID = '"
                + ChildImmun.get(position).getChildGUID() + "'";

        count = dataProvider.getMaxRecord(sqlcount);
        if (count > 0) {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(""+count);
        } else {
            tv_count.setVisibility(View.INVISIBLE);
        }
//        tvcounselling.setText(sname);

//        if (ChildImmun.get(position).getWomenname() != null && ChildImmun.get(position).getWomenname().length() > 0) {
//
//            mothername = ChildImmun.get(position).getWomenname();
//
//        } else {
//
//            ChildImmun2 = dataProvider.gettblCHildImmunization();
//            if (ChildImmun2 != null && ChildImmun2.size() > 0) {
//
//                for (int i = 0; i < ChildImmun2.size(); i++) {
//                    if ((ChildImmun.get(position).getChildGUID()).equalsIgnoreCase(ChildImmun2.get(i).getChildGUID())) {
//                        mothername = ChildImmun2.get(i).getFamilyMemberName();
//
//                    }
//                }
//            }
//        }

        Sparentsname = String.valueOf(ChildImmun.get(position).getChild_name() + "/" + Validate.returnStringValue(mothername));

        tvparentsname.setText(Sparentsname);
        String currentdate = Validate.getcurrentdate();
        String age = Validate.getContractMonth(ChildImmun.get(position).getDob(), currentdate);
        tvAge.setText(String.valueOf(age));
        global.setDateMonth(age);

        btncounselling.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String sql = "select ImmunizationGUID from tblmstimmunizationAns where ChildGUID='" + ChildImmun.get(position).getChildGUID() + "'";
                String guid = dataProvider.getRecord(sql);
                global.setImmunizationGUID(guid);
                global.setsGlobalChildGUID(ChildImmun.get(position).getChildGUID());
                Intent i = new Intent(v.getContext(),
                        ImmunizationQuestionActivity.class);
                context.startActivity(i);
            }
        });
        long ageint = Validate.Daybetween(ChildImmun.get(position).getDob(), Validate.getcurrentdate());
        if (ageint <= 28) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet1);
            tvAge.setBackgroundResource(R.drawable.childsheet1);
            // tvcounselling.setBackgroundResource(R.drawable.childsheet1);
        } else if (ageint <= 365) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet2);
            tvAge.setBackgroundResource(R.drawable.childsheet2);
            // tvcounselling.setBackgroundResource(R.drawable.childsheet2);
        } else if (ageint <= 730) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet3);
            tvAge.setBackgroundResource(R.drawable.childsheet3);
            // tvcounselling.setBackgroundResource(R.drawable.childsheet3);
        } else if (ageint <= 1825) {
            tvparentsname.setBackgroundResource(R.drawable.childsheet4);
            tvAge.setBackgroundResource(R.drawable.childsheet4);
            // tvcounselling.setBackgroundResource(R.drawable.childsheet4);
        } else {
            tvparentsname.setBackgroundResource(R.drawable.childsheet5);
            tvAge.setBackgroundResource(R.drawable.childsheet5);
            //tvcounselling.setBackgroundResource(R.drawable.childsheet5);
        }
//        String status = "Select StatusID from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
//                + ChildImmun.get(position).getChildGUID() + "'";
//        String stat = dataProvider.getRecord(status);
//        if(Validate.returnIntegerValue(stat)==3){
//            tvparentsname.setBackgroundResource(R.drawable.yellowsheet);
//            tvAge.setBackgroundResource(R.drawable.yellowsheet);
//            tvcounselling.setBackgroundResource(R.drawable.yellowsheet);
//        }else{
//            tvparentsname.setBackgroundResource(R.drawable.whitesheet);
//            tvAge.setBackgroundResource(R.drawable.whitesheet);
//            tvcounselling.setBackgroundResource(R.drawable.whitesheet);
//        }
        btnimmune.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (ChildImmun.get(position).getChildGUID() != null && ChildImmun.get(position).getChildGUID().length() > 0) {
                    showimmune(ChildImmun.get(position).getChildGUID());
                }
            }
        });
        return gridview;
    }

    @SuppressWarnings("unused")
    public void showimmune(String ChildGUID) {
        try {
            PicVideo_PreviewPopUp = new Dialog(context);
            Window window = PicVideo_PreviewPopUp.getWindow();
            //    PicVideo_PreviewPopUp.getWindow().setLayout(900, 400);
            // PicVideo_PreviewPopUp.getWindow().setBackgroundDrawable(
            // new ColorDrawable(Color.TRANSPARENT));
            PicVideo_PreviewPopUp.getWindow().setBackgroundDrawableResource(
                    android.R.color.white);
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            Double width = metrics.widthPixels * 20.2;
            Double height = metrics.heightPixels * 5.3;
            Window win = PicVideo_PreviewPopUp.getWindow();
            win.setLayout(width.intValue(), height.intValue());
            //    window.requestFeature(Window.FEATURE_NO_TITLE);

            PicVideo_PreviewPopUp.setContentView(R.layout.vaccination);
            PicVideo_PreviewPopUp.setCancelable(true);
            PicVideo_PreviewPopUp.setCanceledOnTouchOutside(true);
            tv_IPV2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_IPV2);
            bcg = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.bcg);
            hep1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep);
            hep0 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep0);
            hep2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep2);
            hep3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.hep3);
            opv0 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv0);
            opv1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv1);
            opv2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv2);
            opv3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.opv3);
            dpt1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt1);
            dpt2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt2);
            dpt3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.dpt3);
            Pentavalent1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent1);
            Pentavalent2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent2);
            Pentavalent3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.Pentavalent3);
            measles = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.measles);
            vitamin = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.vitamin);


            tv_ipv = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_ipv);
            tv_JEVaccine1 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_JEVaccine1);
            tv_opvbooster = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_opvbooster);
            tv_DPTBooster = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_DPTBooster);
            tv_measles2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_measles2);
            tv_JEVaccine2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_JEVaccine2);
            tv_vitamintwo = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamintwo);
            tv_vitamin3 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin3);
            tv_vitamin4 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin4);
            tv_vitamin5 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin5);
            tv_vitamin6 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin6);
            tv_vitamin7 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin7);
            tv_vitamin8 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin8);
            tv_vitamin9 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_vitamin9);
            tv_DPTBoostersecond = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_DPTBoostersecond);
            tv_tt = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_tt);
            tv_tt2 = (TextView) PicVideo_PreviewPopUp.findViewById(R.id.tv_tt2);


            filldata(ChildGUID);

            PicVideo_PreviewPopUp.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub

                    //((ImmunizationCounselling) context).fillgrid(1);
                }
            });

            PicVideo_PreviewPopUp.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filldata(String childGUID) {
        // TODO Auto-generated method stub

        ChildImmun1 = dataProvider.ShowCHildImmunizationdata(childGUID);

        if (ChildImmun1 != null && ChildImmun1.size() > 0) {
            bcg.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getBcg())));
            opv1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getOpv2())));
            dpt1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getDpt1())));
            hep0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getHepb2())));
            opv2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getOpv3())));
            dpt2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getDpt2())));
            hep2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getHepb3())));
            opv3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getOpv4())));
            dpt3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getDpt3())));
            hep3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getHepb4())));
            measles.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getMeaseals())));
            vitamin.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA())));
            Pentavalent1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getPentavalent1())));
            Pentavalent2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getPentavalent2())));
            Pentavalent3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0)
                    .getPentavalent3())));
            opv0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getOpv1())));
            hep1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getHepb1())));
            tv_ipv.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getIPV())));
            tv_JEVaccine1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getJEVaccine1())));
            tv_opvbooster.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getOPVBooster())));
            tv_DPTBooster.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getDPTBooster())));
            tv_measles2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getMeaslesRubella())));
            tv_JEVaccine2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getJEVaccine2())));
            tv_vitamintwo.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminAtwo())));
            tv_vitamin3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA3())));
            tv_vitamin4.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA4())));
            tv_vitamin5.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA5())));
            tv_vitamin6.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA6())));
            tv_vitamin7.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA7())));
            tv_vitamin8.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA8())));
            tv_vitamin9.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getVitaminA9())));
            tv_DPTBoostersecond.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getDPTBoostertwo())));
            tv_tt.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getChildTT())));
            tv_tt2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getTT2())));
            tv_IPV2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun1.get(0).getIPV2())));


        }

    }

}
