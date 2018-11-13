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
public class FP_ExistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Tbl_HHFamilyMember> hhsurvey;
    Global global;
    DataProvider dataProvider;
    int j = 1, flag = 0;
    ArrayList<Tbl_HHFamilyMember> hushbandArrayList = new ArrayList<Tbl_HHFamilyMember>();
    ArrayList<tblmstFPAns> FP_Ans = new ArrayList<tblmstFPAns>();
    int diffInDays = 0;
    ArrayList<tblmstFPFDetail> tblFp_followArrayList = new ArrayList<tblmstFPFDetail>();

    public FP_ExistAdapter(Context context,
                           ArrayList<Tbl_HHFamilyMember> hhsurvey) {
        this.hhsurvey = hhsurvey;
        this.context = context;
    }

    public FP_ExistAdapter(Context context,
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
            gridview = layoutInflater.inflate(R.layout.fp_adapter, null);
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
        ImageView btn_followup = (ImageView) gridview
                .findViewById(R.id.btn_followup);

        tvHHCode.setText(hhsurvey.get(position).getHHCode());
        tvHHHeadName.setText(hhsurvey.get(position).getFamilyMemberName());
        String guid = "";
        String womenguid = hhsurvey.get(position).getHHFamilyMemberGUID();
        if (womenguid != null && womenguid.length() > 0) {
            tblFp_followArrayList = dataProvider.getFP_Detail(womenguid, 3);
            if (tblFp_followArrayList != null
                    && tblFp_followArrayList.size() > 0) {
                tvHHHeadName.setTextColor(Color.parseColor("#FFA500"));

            }
        } else {
            if (womenguid != null && womenguid.length() > 0) {
                tblFp_followArrayList = dataProvider.getFP_Detail(womenguid, 4);
                if (tblFp_followArrayList != null
                        && tblFp_followArrayList.size() > 0) {
                    tvHHHeadName.setTextColor(Color.parseColor("#FF69B4"));

                }
            } else {
                womenguid = "";
                tvHHHeadName.setTextColor(Color.parseColor("#000000"));

            }
        }

        if (hhsurvey.get(position).getSpouse() != null
                && hhsurvey.get(position).getSpouse().length() > 0) {
            guid = hhsurvey.get(position).getSpouse();
            hushbandArrayList = dataProvider.getAllmalename(guid);
            if (hushbandArrayList != null && hushbandArrayList.size() > 0) {
                tvhusbandname.setText(hushbandArrayList.get(0)
                        .getFamilyMemberName());
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
                global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                        .getHHFamilyMemberGUID());
                if (flag == 1) {
                    CustomAlert1(position);
                } else {
                    CustomAlert();
                }

            }
        });
        if (flag != 1) {
            btn_followup.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    followup(position, v);

                    // tblFp_followArrayList = dataProvider.getFP_Detail(
                    // hhsurvey.get(position).getHHFamilyMemberGUID(), 5);
                    // if (tblFp_followArrayList != null
                    // && tblFp_followArrayList.size() > 0) {
                    // CustomAlerts(context.getResources().getString(
                    // R.string.alreadymethodadopted));
                    // } else {
                    // global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                    // .getHHFamilyMemberGUID());
                    // global.setsGlobalWomenName(hhsurvey.get(position)
                    // .getFamilyMemberName());
                    // String guid = hhsurvey.get(position).getSpouse();
                    //
                    // if (guid != null && guid.length() > 0) {
                    // hushbandArrayList = dataProvider.getAllmalename(guid);
                    // if (hushbandArrayList != null
                    // && hushbandArrayList.size() > 0) {
                    // global.setsGlobalHusbandName(hushbandArrayList.get(
                    // 0).getFamilyMemberName());
                    // }
                    // } else {
                    // global.setsGlobalHusbandName("");
                    // }
                    // Intent i = new Intent(v.getContext(), FP_Follwup.class);
                    // i.putExtra("husbandguid", guid);
                    // context.startActivity(i);
                    // }
                }
            });
        } else {
//			btn_followup.setBackgroundColor(context.getResources().getColor(
//					R.color.Gray));
        }
        return gridview;
    }

    public void followup(int position, View v) {

        // TODO Auto-generated method stub
        String sql = "select count(*) from tblFP_followup where WomenName_Guid='"
                + hhsurvey.get(position).getHHFamilyMemberGUID()
                + "' and ((methodadopted in(1,2,6) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,2,3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39,41,43,45,47,49,51,53,55,57,59,61) and uid in(select max(uid) from tblFP_followup where WomenName_Guid='"
                + hhsurvey.get(position).getHHFamilyMemberGUID()
                + "')) or (methodadopted in(4,5) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4,7,10,13,16,19,21,24,27,30,33,36,39,42,45,48,51,54,57,60) and uid in(select max(uid) from tblFP_followup where WomenName_Guid='"
                + hhsurvey.get(position).getHHFamilyMemberGUID()
                + "')) or (methodadopted in(7,9) and cast(round((julianday('NOW')-julianday(methodadopteddate))/30+.5)  as int) in(1,4) and uid in(select max(uid) from tblFP_followup where WomenName_Guid='"
                + hhsurvey.get(position).getHHFamilyMemberGUID() + "')) );";

        String sql1 = "select count(*) from tblFP_followup where WomenName_Guid='"
                + hhsurvey.get(position).getHHFamilyMemberGUID() + "' ";
        int count1 = 0, count2 = 0;
        count1 = dataProvider.getMaxRecord(sql1);
        count2 = dataProvider.getMaxRecord(sql);
        if (count1 == 0) {

            global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                    .getHHFamilyMemberGUID());
            global.setsGlobalWomenName(hhsurvey.get(position)
                    .getFamilyMemberName());
            String guid = hhsurvey.get(position).getSpouse();

            if (guid != null && guid.length() > 0) {
                hushbandArrayList = dataProvider.getAllmalename(guid);
                if (hushbandArrayList != null && hushbandArrayList.size() > 0) {
                    global.setsGlobalHusbandName(hushbandArrayList.get(0)
                            .getFamilyMemberName());
                }
            } else {
                global.setsGlobalHusbandName("");
            }
            Intent i = new Intent(v.getContext(), FP_Follwup.class);
            i.putExtra("husbandguid", guid);
            context.startActivity(i);

        } else {
            if (count2 > 0) {

                global.setGlobalHHFamilyMemberGUID(hhsurvey.get(position)
                        .getHHFamilyMemberGUID());
                global.setsGlobalWomenName(hhsurvey.get(position)
                        .getFamilyMemberName());
                String guid = hhsurvey.get(position).getSpouse();

                if (guid != null && guid.length() > 0) {
                    hushbandArrayList = dataProvider.getAllmalename(guid);
                    if (hushbandArrayList != null
                            && hushbandArrayList.size() > 0) {
                        global.setsGlobalHusbandName(hushbandArrayList.get(0)
                                .getFamilyMemberName());
                    }
                } else {
                    global.setsGlobalHusbandName("");
                }
                Intent i = new Intent(v.getContext(), FP_Follwup.class);
                i.putExtra("husbandguid", guid);
                context.startActivity(i);

            } else {
                CustomAlerts(context.getResources().getString(
                        R.string.alreadymethodadopted));
            }
        }
    }

    public void CustomAlert() {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fp_dialog, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        Button btn_counselling = (Button) dialog
                .findViewById(R.id.btn_counselling);

        Button btn_emergencypills = (Button) dialog
                .findViewById(R.id.btn_emergencypills);
        Button btn_Ashaincentive = (Button) dialog
                .findViewById(R.id.btn_Ashaincentive);
        btn_counselling
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        diffInDays = Weekcalculation();
                        if (diffInDays == 0 || diffInDays > 30) {
                            Intent i = new Intent(v.getContext(),
                                    FP_MemberVisit.class);
                            // global.setRadioid(0);
                            i.putExtra("counselling", "0");
                            context.startActivity(i);
                        } else {
                            CustomAlerts(context.getResources().getString(
                                    R.string.VisitExist));
                        }
                    }
                });
        btn_emergencypills
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(),
                                FP_MemberVisit.class);
                        i.putExtra("counselling", "1");
                        context.startActivity(i);
                    }
                });
        btn_Ashaincentive
                .setOnClickListener(new android.view.View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(v.getContext(),
                                FP_MemberVisit.class);
                        i.putExtra("counselling", "2");
                        context.startActivity(i);
                    }
                });

        // Display the dialog
        dialog.show();

    }

    public void CustomAlert1(final int position) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logout_alert, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(context.getResources().getString(
                R.string.currentlymethod));

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                // int iLoggedin = 0;

                followup(position, v);
                dialog.dismiss();
            }
        });

        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        btnno.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                CustomAlert();
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

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
