package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

import java.util.ArrayList;


public class NCD_Report extends Activity {

    TextView tvmale2, tvmale3, tvmale4, tvmale5, tvmale6, tvmale7, tvmale8, tvmale9, tvfemale2, tvfemale3, tvfemale4, tvfemale5,
            tvfemale6, tvfemale7, tvfemale8, tvfemale9, tvtotal, tvtotal2, tvtotal3, tvtotal4, tvtotal5, tvtotal6, tvtotal7, tvtotal8, tvtotal9;
    Button btndetails;
    Global global;
    int ashaid = 0,VillageID = 0;
    int anmi = 0;
    DataProvider dataProvider;
    TableRow tbl_btn,tbl_spin;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdreport);
        global = (Global) getApplicationContext();
        dataProvider = new DataProvider(this);
        btndetails = (Button) findViewById(R.id.btndetails);
        tbl_btn = (TableRow) findViewById(R.id.tbl_btn);
        tbl_spin = (TableRow) findViewById(R.id.tbl_spin);
        tvmale2 = (TextView) findViewById(R.id.tvmale2);
        tvmale3 = (TextView) findViewById(R.id.tvmale3);
        tvmale4 = (TextView) findViewById(R.id.tvmale4);
        tvmale5 = (TextView) findViewById(R.id.tvmale5);
        tvmale6 = (TextView) findViewById(R.id.tvmale6);
        tvmale7 = (TextView) findViewById(R.id.tvmale7);
        tvmale8 = (TextView) findViewById(R.id.tvmale8);
        tvmale9 = (TextView) findViewById(R.id.tvmale9);

        tvfemale2 = (TextView) findViewById(R.id.tvfemale2);
        tvfemale3 = (TextView) findViewById(R.id.tvfemale3);
        tvfemale4 = (TextView) findViewById(R.id.tvfemale4);
        tvfemale5 = (TextView) findViewById(R.id.tvfemale5);
        tvfemale6 = (TextView) findViewById(R.id.tvfemale6);
        tvfemale7 = (TextView) findViewById(R.id.tvfemale7);
        tvfemale8 = (TextView) findViewById(R.id.tvfemale8);
        tvfemale9 = (TextView) findViewById(R.id.tvfemale9);
        tvtotal = (TextView) findViewById(R.id.tvtotal);
        tvtotal2 = (TextView) findViewById(R.id.tvtotal2);
        tvtotal3 = (TextView) findViewById(R.id.tvtotal3);
        tvtotal4 = (TextView) findViewById(R.id.tvtotal4);
        tvtotal5 = (TextView) findViewById(R.id.tvtotal5);
        tvtotal6 = (TextView) findViewById(R.id.tvtotal6);
        tvtotal7 = (TextView) findViewById(R.id.tvtotal7);
        tvtotal8 = (TextView) findViewById(R.id.tvtotal8);
        tvtotal9 = (TextView) findViewById(R.id.tvtotal9);
        tbl_btn.setVisibility(View.GONE);
        tbl_spin.setVisibility(View.VISIBLE);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);

        try {
            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                ashaid = Integer.valueOf(global.getsGlobalAshaCode());
            }

            int statecode = 0;
            if (global.getStateCode() != null
                    && global.getStateCode().length() > 0) {

                statecode = Integer.valueOf(global.getStateCode());
            }
            if (statecode == 20) {


            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (pos > 0) {
                    VillageID = Village.get(
                            spinVillageName.getSelectedItemPosition() - 1)
                            .getVillageID();
                    showdata(VillageID);

                } else {
                    VillageID = 0;
                    showALLdata();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public void showALLdata() {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where ServiceProviderID=" + ashaid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));

            String sql1C = "";
            sql1C = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1c = 0;
            ivalue1c = dataProvider.getMaxRecord(sql1C);
            tvtotal2.setText(String.valueOf(ivalue1c));
            String sql3A = "";
            sql3A = "";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3c));


            String sql4A = "";
            sql4A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID where b.GenderId=1 and a.status=1 and a.AshaID='" + global.getsGlobalAshaCode() + "' and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID) ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where b.GenderId=2 and a.status=1 and a.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));

            String sql4C = "";
            sql4C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and a.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID) ";
            int ivalue4c = 0;
            ivalue4c = dataProvider.getMaxRecord(sql4C);
            tvtotal4.setText(String.valueOf(ivalue4c));

            String sql5A = "";
            sql5A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and b.GenderID=1 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and b.GenderID=2 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));

            String sql5C = "";
            sql5C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1  and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue5c = 0;
            ivalue5c = dataProvider.getMaxRecord(sql5C);
            tvtotal5.setText(String.valueOf(ivalue5c));

            String sql6A = "";
            sql6A = "";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));

            String sql6C = "";
            sql6C = "";
            int ivalue6c = 0;
            ivalue6c = dataProvider.getMaxRecord(sql6C);
            tvtotal6.setText(String.valueOf(ivalue6c));
            String sql8A = "";
            sql8A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where b.GenderId=1 and a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID )";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));

            String sql8B = "";
            sql8B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where b.GenderId=2 and a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));

            String sql8C = "";
            sql8C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID )";
            int ivalue8c = 0;
            ivalue8c = dataProvider.getMaxRecord(sql8C);
            tvtotal8.setText(String.valueOf(ivalue8c));
            String sql9A = "";
            sql9A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and b.GenderID=1 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));


            String sql9B = "";
            sql9B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1 and b.GenderID=2 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));

            String sql9C = "";
            sql9C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.status=1  and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue9c = 0;
            ivalue9c = dataProvider.getMaxRecord(sql9C);
            tvtotal9.setText(String.valueOf(ivalue9c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showdata(int villageID) {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where Tbl_HHSurvey.VillageID='" + villageID + "' and ServiceProviderID=" + ashaid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=1 and b.VillageID='" + villageID + "' and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.GenderID=2 and b.VillageID='" + villageID + "' and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));

            String sql1C = "";
            sql1C = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.VillageID='" + villageID + "' and  ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1c = 0;
            ivalue1c = dataProvider.getMaxRecord(sql1C);
            tvtotal2.setText(String.valueOf(ivalue1c));
            String sql3A = "";
            sql3A = "";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3c));


            String sql4A = "";
            sql4A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID where a.VillageID='" + villageID + "' and  b.GenderId=1 and a.status=1 and a.AshaID='" + global.getsGlobalAshaCode() + "' and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID) ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and b.GenderId=2 and a.status=1 and a.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));

            String sql4C = "";
            sql4C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and a.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID Not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID) ";
            int ivalue4c = 0;
            ivalue4c = dataProvider.getMaxRecord(sql4C);
            tvtotal4.setText(String.valueOf(ivalue4c));

            String sql5A = "";
            sql5A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and b.GenderID=1 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and b.GenderID=2 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));

            String sql5C = "";
            sql5C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1  and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID not in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue5c = 0;
            ivalue5c = dataProvider.getMaxRecord(sql5C);
            tvtotal5.setText(String.valueOf(ivalue5c));

            String sql6A = "";
            sql6A = "";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));

            String sql6C = "";
            sql6C = "";
            int ivalue6c = 0;
            ivalue6c = dataProvider.getMaxRecord(sql6C);
            tvtotal6.setText(String.valueOf(ivalue6c));
            String sql8A = "";
            sql8A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and b.GenderId=1 and a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID )";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));

            String sql8B = "";
            sql8B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and b.GenderId=2 and a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));

            String sql8C = "";
            sql8C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and b.ashaid=" + ashaid + " and a.RBS>140 and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID )";
            int ivalue8c = 0;
            ivalue8c = dataProvider.getMaxRecord(sql8C);
            tvtotal8.setText(String.valueOf(ivalue8c));
            String sql9A = "";
            sql9A = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and b.GenderID=1 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));


            String sql9B = "";
            sql9B = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1 and b.GenderID=2 and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));

            String sql9C = "";
            sql9C = "select count(*) from tblncdscreening a inner join tbl_HHFamilyMember b on a.hhfamilymemberGUID=b.hhfamilymemberGUID where a.VillageID='" + villageID + "' and a.status=1  and b.ashaid=" + ashaid + " and  (SUBSTR(BP,1, (instr(BP,'/')-1)) >120 or (SUBSTR((BP), (instr(BP,'/')+1), 3))<80) and a.NCDScreeningGUID  in (select NCDScreeningGUID from tblncdfollowup group by NCDScreeningGUID)";

            int ivalue9c = 0;
            ivalue9c = dataProvider.getMaxRecord(sql9C);
            tvtotal9.setText(String.valueOf(ivalue9c));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (global.getiGlobalRoleID() == 3) {
            Intent i = new Intent(NCD_Report.this,
                    AshaListForAnm.class);
            finish();

            i.putExtra("Flag", 7);
            startActivity(i);
        } else {
            Intent i = new Intent(NCD_Report.this, Report_Module.class);
            finish();
            startActivity(i);
        }
    }
}
