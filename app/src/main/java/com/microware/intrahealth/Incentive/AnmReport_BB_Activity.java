package com.microware.intrahealth.Incentive;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.microware.intrahealth.Global;
import com.microware.intrahealth.IncentiveAdapter.AnmReport_BB_Adapter;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/20/2017.
 */

public class AnmReport_BB_Activity extends Activity {
    DataProvider dataProvider;
    Global global;
    TextView tv_year, tv_month;
    ArrayAdapter<String> adapter;
    RecyclerView rv_afreport;
    LinearLayoutManager layoutManager;
    AnmReport_BB_Adapter afreport_adapter;
    String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    Button btn_Approveall, btn_Approveselected, btn_notapproved;
    ArrayList<HashMap<String, String>> data = null;
    int checkcount = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anmreport_bb_activity);
        dataProvider = new DataProvider(this);
        global = (Global) this.getApplicationContext();
        btn_Approveall = (Button) findViewById(R.id.btn_Approveall);
        btn_Approveselected = (Button) findViewById(R.id.btn_Approveselected);
        btn_notapproved = (Button) findViewById(R.id.btn_notapproved);
        tv_year = (TextView) findViewById(R.id.tv_year);
        tv_month = (TextView) findViewById(R.id.tv_month);
        rv_afreport = (RecyclerView) findViewById(R.id.rv_afreport);
        btn_Approveall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sqlupdate = "update  tblincentivesurvey set ANMStatus=1 ,NotApproved=0,IsEdited=1,RemarkAMStatus='', UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' ";
                dataProvider.executeSql(sqlupdate);
                String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=1 ,IsEdited=1,RemarkAMStatus='', UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' ";
                dataProvider.executeSql(sqlupdateincentive);
                CustomAlert(getResources().getString(R.string.approvedsuccessfully));
            }
        });
        btn_Approveselected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
                if (checkcount == 0) {
                    CustomAlert(getString(R.string.pleaseselectoneasha));
                } else {
                    CustomAlert(getResources().getString(R.string.approvedsuccessfully));
                }
            }
        });
        btn_notapproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int icount = chkvalidation();
                if (icount == 1) {
                    savedatanotapproved();
                } else {
                    CustomAlert(getString(R.string.pleaseselectashaforapproved));
                }

            }
        });

        showwdata();
        fillRecycler();

    }

    public void showwdata() {
        try {
            tv_year.setText(String.valueOf(global.getGlobalYear()));
            if (global.getGlobalMonth() > 0)
                tv_month.setText(month[global.getGlobalMonth() - 1]);
            // ArrayList<HashMap<String, String>> data = null;


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void savedata() {
        try {

            checkcount = 0;
            int iSizeANC = rv_afreport.getChildCount();

            for (int i = 0; i < iSizeANC; i++) {
                try {
                    ViewGroup Child = (ViewGroup) rv_afreport.getChildAt(i);


                    CheckBox checkbox = (CheckBox) Child.findViewById(R.id.checkbox);
                    if (checkbox.isChecked()) {
                        if (data != null) {
                            checkcount = 1;
                            String sqlupdate = "update  tblincentivesurvey set ANMStatus=1 ,NotApproved=0,IsEdited=1,RemarkAMStatus='', UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + data.get(i).get("AshaId") + "' ";
                            dataProvider.executeSql(sqlupdate);
                            String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=1 ,IsEdited=1,RemarkAMStatus='', UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + data.get(i).get("AshaId") + "' ";
                            dataProvider.executeSql(sqlupdateincentive);
                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public void savedatanotapproved() {
        try {

            checkcount = 0;
            int iSizeANC = rv_afreport.getChildCount();

            for (int i = 0; i < iSizeANC; i++) {
                try {
                    ViewGroup Child = (ViewGroup) rv_afreport.getChildAt(i);


                    CheckBox checkbox = (CheckBox) Child.findViewById(R.id.checkbox);
                    if (checkbox.isChecked()) {
                        if (data != null) {
                            checkcount = 1;
                            CustomAlert(i);
                        }
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    public void fillRecycler() {

        //String ashaid = global.getsGlobalANMCODE();


        String sql = "select Claim as Claim,MSTASHA.ASHAName as name ,MSTASHA.AshaId as AshaId, tblincentivesurvey.ANMStatus as status,NotApproved,IncentiveSurveyGUID from tblincentivesurvey inner join Mstasha on MSTASHA.ASHAID=tblincentivesurvey.AshaID where  AnmId='" + global.getsGlobalANMCODE() + "' and MSTASHA.LanguageID=" + global.getLanguage() + " and Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "'   ";
        data = dataProvider.getDynamicVal(sql);
        //"select Year,Month,count(AshaID) as AshaCount ,sum(Claim) as Claim,sum(NotApproved) as NotApproved from tblincentivesurvey where  AnmId=1 Group by Year , Month order by Year DESC"
        if (data != null && data.size() > 0) {

            afreport_adapter = new AnmReport_BB_Adapter(this, data);
            rv_afreport.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rv_afreport.setLayoutManager(layoutManager);
            rv_afreport.setAdapter(afreport_adapter);
            afreport_adapter.notifyDataSetChanged();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(AnmReport_BB_Activity.this, Anmreport_AA_Activity.class);
        finish();
        startActivity(i);
    }

    public void CustomAlert(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
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
                Intent in = getIntent();
                finish();
                startActivity(in);
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void CustomAlert(final int pos) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.status_alert, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        RelativeLayout rel_edit = (RelativeLayout) dialog
                .findViewById(R.id.et_layout_alert_content);

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);

        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        final EditText et_remark = (EditText) dialog
                .findViewById(R.id.et_remark);
        //txtTitle.setText(getResources().getString(R.string.Doyouwanttologout));

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");


        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {


                String sqlupdate = "update  tblincentivesurvey set ANMStatus=2,NotApproved='" + data.get(pos).get("Claim") + "' ,RemarkAMStatus='" + et_remark.getText().toString() + "',IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + data.get(pos).get("AshaId") + "' and IncentiveSurveyGUID='" + data.get(pos).get("IncentiveSurveyGUID") + "' ";
                dataProvider.executeSql(sqlupdate);
                String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=2 ,RemarkAMStatus='" + et_remark.getText().toString() + "' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + data.get(pos).get("AshaId") + "' and IncentiveSurveyGUID='" + data.get(pos).get("IncentiveSurveyGUID") + "'";
                dataProvider.executeSql(sqlupdateincentive);
                CustomAlert(getString(R.string.rejected));
                dialog.dismiss();
            }
        });


        // Display the dialog
        dialog.show();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (global.getiGlobalRoleID() == 3) {
            menu.add(0, 0, 1, global.getsGlobalANMName()).setShowAsAction(
                    MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }

        return true;
    }

    public int chkvalidation() {
        int count = 0;
        try {
            int iSizeANC = rv_afreport.getChildCount();
            for (int i = 0; i < iSizeANC; i++) {
                try {
                    ViewGroup Child = (ViewGroup) rv_afreport.getChildAt(i);
                    CheckBox checkbox = (CheckBox) Child.findViewById(R.id.checkbox);
                    if (checkbox.isChecked()) {
                        count++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;

    }
}
