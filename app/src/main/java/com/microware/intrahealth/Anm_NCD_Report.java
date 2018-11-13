package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;


public class Anm_NCD_Report extends Activity {
    TextView tvmale2,tvmale3,tvmale4,tvmale5,tvmale6,tvmale7,tvmale8,tvmale9,tvfemale2,tvfemale3,tvfemale4,tvfemale5,
            tvfemale6,tvfemale7,tvfemale8,tvfemale9,tvtotal,tvtotal2,tvtotal3,tvtotal4,tvtotal5,tvtotal6,tvtotal7,tvtotal8,tvtotal9,
            tvfemaleBP6, tvfemaleBP7, tvfemaleBP8, tvfemaleBP9, tvtotalBP6, tvtotalBP7, tvtotalBP8, tvtotalBP9, tvmaleBP6, tvmaleBP7, tvmaleBP8, tvmaleBP9, tv_fromdate, tv_todate;

    Button btndetails,btnshow;
    Global global;

    int anmid=0;
    DataProvider dataProvider;
    TableRow tbl_btn,tbl_spin,tbl_date;
    Validate validate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdreport);
        btndetails=(Button) findViewById(R.id.btndetails);
        global=(Global) getApplicationContext();
        dataProvider=new DataProvider(this);
        validate=new Validate(this);
        tbl_btn = (TableRow) findViewById(R.id.tbl_btn);
        tbl_spin = (TableRow) findViewById(R.id.tbl_spin);
        tbl_date = (TableRow) findViewById(R.id.tbl_date);
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
        tvfemaleBP6 = (TextView) findViewById(R.id.tvfemaleBP6);
        tvfemaleBP7 = (TextView) findViewById(R.id.tvfemaleBP7);
        tvfemaleBP8 = (TextView) findViewById(R.id.tvfemaleBP8);
        tvfemaleBP9 = (TextView) findViewById(R.id.tvfemaleBP9);
        tvtotalBP6 = (TextView) findViewById(R.id.tvtotalBP6);
        tvtotalBP7 = (TextView) findViewById(R.id.tvtotalBP7);
        tvtotalBP8 = (TextView) findViewById(R.id.tvtotalBP8);
        tvtotalBP9 = (TextView) findViewById(R.id.tvtotalBP9);
        tvmaleBP6 = (TextView) findViewById(R.id.tvmaleBP6);
        tvmaleBP7 = (TextView) findViewById(R.id.tvmaleBP7);
        tvmaleBP8 = (TextView) findViewById(R.id.tvmaleBP8);
        tvmaleBP9 = (TextView) findViewById(R.id.tvmaleBP9);
        tv_fromdate = (TextView) findViewById(R.id.tv_fromdate);
        tv_todate = (TextView) findViewById(R.id.tv_todate);
        btnshow = (Button) findViewById(R.id.btnshow);
        tbl_btn.setVisibility(View.VISIBLE);
        tbl_spin.setVisibility(View.GONE);
        tbl_date.setVisibility(View.GONE);
        btndetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Anm_NCD_Report.this,
                        AshaListForAnm.class);
                finish();

                i.putExtra("Flag", 7);
                startActivity(i);
            }
        });
        try {
            if (global.getsGlobalANMCODE() != null
                    && global.getsGlobalANMCODE().length() > 0) {
                anmid = Validate.returnIntegerValue(global.getsGlobalANMCODE());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        showdata();

    }
    public  void showdata(){
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where IsActive=1 and  ANMID= " + anmid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));
            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=1 and b.ANMID= " + anmid + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));
            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=2 and b.ANMID= " + anmid + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));
            tvtotal2.setText(String.valueOf(ivalue1a + ivalue1b));
            String sql3A = "";
            sql3A = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=1 and b.ANMID= " + anmid + " and age>29";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=2 and b.ANMID= " + anmid + " and age>29";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3a + ivalue3b));


            String sql4A = "";
            sql4A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1  and c.ANMID= " + anmid + " ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2  and c.ANMID= " + anmid + " ";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));

            tvtotal4.setText(String.valueOf(ivalue4a + ivalue4b));

            String sql5A = "";
            sql5A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1  and c.ANMID= " + anmid + " ";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2  and c.ANMID= " + anmid + " ";
            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));


            tvtotal5.setText(String.valueOf(ivalue5a + ivalue5b));

            String sql6A = "";
            sql6A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and RBS>140 and c.ANMID= " + anmid + " ";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and RBS>140 and c.ANMID= " + anmid + " ";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));


            tvtotal6.setText(String.valueOf(ivalue6a + ivalue6b));
            String sqlbp6A = "";
            sqlbp6A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp6a = 0;
            ivaluebp6a = dataProvider.getMaxRecord(sqlbp6A);
            tvmaleBP6.setText(String.valueOf(ivaluebp6a));

            String sqlbp6B = "";
            sqlbp6B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp6b = 0;
            ivaluebp6b = dataProvider.getMaxRecord(sqlbp6B);
            tvfemaleBP6.setText(String.valueOf(ivaluebp6b));


            tvtotalBP6.setText(String.valueOf(ivaluebp6a + ivaluebp6b));
            String sql7A = "";
            sql7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and c.ANMID= " + anmid + " ";
            int ivalue7a = 0;
            ivalue7a = dataProvider.getMaxRecord(sql7A);
            tvmale7.setText(String.valueOf(ivalue7a));

            String sql7B = "";
            sql7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and c.ANMID= " + anmid + " ";
            int ivalue7b = 0;
            ivalue7b = dataProvider.getMaxRecord(sql7B);
            tvfemale7.setText(String.valueOf(ivalue7b));


            tvtotal7.setText(String.valueOf(ivalue7a + ivalue7b));
            String sqlbp7A = "";
            sqlbp7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp7a = 0;
            ivaluebp7a = dataProvider.getMaxRecord(sqlbp7A);
            tvmaleBP7.setText(String.valueOf(ivaluebp7a));

            String sqlbp7B = "";
            sqlbp7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp7b = 0;
            ivaluebp7b = dataProvider.getMaxRecord(sqlbp7B);
            tvfemaleBP7.setText(String.valueOf(ivaluebp7b));


            tvtotalBP7.setText(String.valueOf(ivaluebp7a + ivaluebp7b));

            String sql8A = "";
            sql8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and RBS>140 and c.ANMID= " + anmid + "";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));
            String sql8B = "";
            sql8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and RBS>140 and c.ANMID= " + anmid + " ";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));
            tvtotal8.setText(String.valueOf(ivalue8a + ivalue8b));
            String sqlbp8A = "";
            sqlbp8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp8a = 0;
            ivaluebp8a = dataProvider.getMaxRecord(sqlbp8A);
            tvmaleBP8.setText(String.valueOf(ivaluebp8a));
            String sqlbp8B = "";
            sqlbp8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and c.ANMID= " + anmid + " ";
            int ivaluebp8b = 0;
            ivaluebp8b = dataProvider.getMaxRecord(sqlbp8B);
            tvfemaleBP8.setText(String.valueOf(ivaluebp8b));
            tvtotalBP8.setText(String.valueOf(ivaluebp8a + ivaluebp8b));

            String sql9A = "";
            sql9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=1 and c.ANMID= " + anmid + " ";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));
            String sql9B = "";
            sql9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=2 and c.ANMID= " + anmid + "";
            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));
            tvtotal9.setText(String.valueOf(ivalue9a + ivalue9b));
            String sqlBP9A = "";
            sqlBP9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=1 and c.ANMID= " + anmid + " ";
            int ivalueBP9a = 0;
            ivalueBP9a = dataProvider.getMaxRecord(sqlBP9A);
            tvmaleBP9.setText(String.valueOf(ivalueBP9a));
            String sqlBP9B = "";
            sqlBP9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=2 and c.ANMID= " + anmid + " ";
            int ivalueBP9b = 0;
            ivalueBP9b = dataProvider.getMaxRecord(sqlBP9B);
            tvfemaleBP9.setText(String.valueOf(ivalueBP9b));
            tvtotalBP9.setText(String.valueOf(ivalueBP9a + ivalueBP9b));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 1, validate.RetriveSharepreferenceString("name")).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getOrder()) {

            case 1:
                if (item.getTitle().equals(validate.RetriveSharepreferenceString("Username"))) {
                    item.setTitle(validate.RetriveSharepreferenceString("name"));

                } else {
                    item.setTitle(validate.RetriveSharepreferenceString("Username"));

                }
                break;


            default:
                break;
        }

        return true;
    }
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Anm_NCD_Report.this, Report_Module.class);
        finish();
        startActivity(i);
    }
}
