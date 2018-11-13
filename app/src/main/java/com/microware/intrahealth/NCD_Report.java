package com.microware.intrahealth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class NCD_Report extends Activity {

    TextView tvmale2, tvmale3, tvmale4, tvmale5, tvmale6, tvmale7, tvmale8, tvmale9, tvfemale2, tvfemale3, tvfemale4, tvfemale5,
            tvfemale6, tvfemale7, tvfemale8, tvfemale9, tvtotal, tvtotal2, tvtotal3, tvtotal4, tvtotal5, tvtotal6, tvtotal7, tvtotal8, tvtotal9,
            tvfemaleBP6, tvfemaleBP7, tvfemaleBP8, tvfemaleBP9, tvtotalBP6, tvtotalBP7, tvtotalBP8, tvtotalBP9, tvmaleBP6, tvmaleBP7, tvmaleBP8, tvmaleBP9, tv_fromdate, tv_todate;
    Button btndetails, btnshow;
    Global global;
    int ashaid = 0, VillageID = 0;
    int anmi = 0;
    DataProvider dataProvider;
    TableRow tbl_btn, tbl_spin;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayAdapter<String> adapter;
    Dialog datepic;
    Validate validate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdreport);
        global = (Global) getApplicationContext();
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
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

        tbl_btn.setVisibility(View.GONE);
        tbl_spin.setVisibility(View.VISIBLE);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);

        try {
            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                ashaid = Validate.returnIntegerValue(global.getsGlobalAshaCode());
            }

            int statecode = 0;
            if (global.getStateCode() != null
                    && global.getStateCode().length() > 0) {

                statecode = Validate.returnIntegerValue(global.getStateCode());
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
//                tv_fromdate.setText("");
//                tv_todate.setText("");
//                tv_fromdate.setHint(R.string.from_date);
//                tv_todate.setHint(R.string.ToDate);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePicker(tv_fromdate);
            }
        });
        tv_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDatePicker(tv_todate);
            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sCheckValidation() == 0) {
                    if (VillageID == 0) {
                        showdata(Validate.changeDateFormat(tv_fromdate.getText().toString()), Validate.changeDateFormat(tv_todate.getText().toString()));
                    } else {
                        showdata(VillageID, Validate.changeDateFormat(tv_fromdate.getText().toString()), Validate.changeDateFormat(tv_todate.getText().toString()));
                    }
                }
            }
        });


    }

    public int sCheckValidation() {
        int iCheck = 0;
        try {

            if (tv_fromdate.getText().toString().length() == 0) {
                iCheck = 1;
                Validate.CustomAlertTextview(this, tv_fromdate, getString(R.string.PleaseenterDaterange));
            }
            if (tv_todate.getText().toString().length() == 0) {
                iCheck = 1;
                Validate.CustomAlertTextview(this, tv_todate, getString(R.string.PleaseenterDaterange));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return iCheck;
    }

    public void ShowDatePicker(final TextView tVvisit) {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        // TODO Auto-generated method stub
        datepic = new Dialog(this, R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker1);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        Button btnclear = (Button) datepic.findViewById(R.id.clear);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);

        datepic.show();

        datepic.getWindow().setLayout(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMaxDate((long) date.getTime());
        if (tVvisit != null && tVvisit.getText() != "" && tVvisit.getText().toString().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            java.util.Date date1;
            Calendar c = Calendar.getInstance(Locale.US);
            try {
                date1 = sdf.parse(tVvisit.getText().toString());
                c.setTime(date1);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datetext.init(year, month, day, null);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }


        btncancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated
                tVvisit.setText("");
                datepic.dismiss();

            }
        });

        btnset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                int dt = datetext.getDayOfMonth();
                int month = datetext.getMonth() + 1;
                int year = datetext.getYear();
                String date = "" + dt;
                String mnth = "" + month;
                if (dt < 10) {
                    date = "0" + dt;
                }
                if (month < 10) {
                    mnth = "0" + month;
                }

                String sSellectedDate = date + "-" + mnth + "-"
                        + String.valueOf(year);
                tVvisit.setText(sSellectedDate);
                datepic.dismiss();
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
            sql1 = "select  count(*) from Tbl_HHSurvey  where IsActive=1 and  ServiceProviderID=" + ashaid + " and (VillageID in (select VillageID from ashavillage where ashaid =" + ashaid + "  ) or VillageID=0)";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));
            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + "  and (VillageID in (select VillageID from ashavillage where ashaid =" + ashaid + "  ) or VillageID=0)";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));
            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + "  and (VillageID in (select VillageID from ashavillage where ashaid =" + ashaid + "  ) or VillageID=0)";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));
            tvtotal2.setText(String.valueOf(ivalue1a + ivalue1b));
            String sql3A = "";
            sql3A = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + " and age>=30";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.IsActive=1 and a.StatusID=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + " and age>=30";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3a + ivalue3b));


            String sql4A = "";
            sql4A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));

            tvtotal4.setText(String.valueOf(ivalue4a + ivalue4b));

            String sql5A = "";
            sql5A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));


            tvtotal5.setText(String.valueOf(ivalue5a + ivalue5b));

            String sql6A = "";
            sql6A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));


            tvtotal6.setText(String.valueOf(ivalue6a + ivalue6b));
            String sqlbp6A = "";
            sqlbp6A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6a = 0;
            ivaluebp6a = dataProvider.getMaxRecord(sqlbp6A);
            tvmaleBP6.setText(String.valueOf(ivaluebp6a));

            String sqlbp6B = "";
            sqlbp6B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6b = 0;
            ivaluebp6b = dataProvider.getMaxRecord(sqlbp6B);
            tvfemaleBP6.setText(String.valueOf(ivaluebp6b));


            tvtotalBP6.setText(String.valueOf(ivaluebp6a + ivaluebp6b));
            String sql7A = "";
            sql7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7a = 0;
            ivalue7a = dataProvider.getMaxRecord(sql7A);
            tvmale7.setText(String.valueOf(ivalue7a));

            String sql7B = "";
            sql7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7b = 0;
            ivalue7b = dataProvider.getMaxRecord(sql7B);
            tvfemale7.setText(String.valueOf(ivalue7b));


            tvtotal7.setText(String.valueOf(ivalue7a + ivalue7b));
            String sqlbp7A = "";
            sqlbp7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7a = 0;
            ivaluebp7a = dataProvider.getMaxRecord(sqlbp7A);
            tvmaleBP7.setText(String.valueOf(ivaluebp7a));

            String sqlbp7B = "";
            sqlbp7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7b = 0;
            ivaluebp7b = dataProvider.getMaxRecord(sqlbp7B);
            tvfemaleBP7.setText(String.valueOf(ivaluebp7b));


            tvtotalBP7.setText(String.valueOf(ivaluebp7a + ivaluebp7b));

            String sql8A = "";
            sql8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));
            String sql8B = "";
            sql8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));
            tvtotal8.setText(String.valueOf(ivalue8a + ivalue8b));
            String sqlbp8A = "";
            sqlbp8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8a = 0;
            ivaluebp8a = dataProvider.getMaxRecord(sqlbp8A);
            tvmaleBP8.setText(String.valueOf(ivaluebp8a));
            String sqlbp8B = "";
            sqlbp8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8b = 0;
            ivaluebp8b = dataProvider.getMaxRecord(sqlbp8B);
            tvfemaleBP8.setText(String.valueOf(ivaluebp8b));
            tvtotalBP8.setText(String.valueOf(ivaluebp8a + ivaluebp8b));

            String sql9A = "";
            sql9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));
            String sql9B = "";
            sql9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));
            tvtotal9.setText(String.valueOf(ivalue9a + ivalue9b));
            String sqlBP9A = "";
            sqlBP9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalueBP9a = 0;
            ivalueBP9a = dataProvider.getMaxRecord(sqlBP9A);
            tvmaleBP9.setText(String.valueOf(ivalueBP9a));
            String sqlBP9B = "";
            sqlBP9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  where b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
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
    public void showdata(int villageID) {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where Tbl_HHSurvey.IsActive=1 and  Tbl_HHSurvey.VillageID='" + villageID + "' and ServiceProviderID=" + ashaid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));


            tvtotal2.setText(String.valueOf(ivalue1a + ivalue1b));
            String sql3A = "";
            sql3A = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "select  count(*),case   DOBAvailable when 1 then  (julianday('NOW')-julianday(DateOfBirth))/365 when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3a + ivalue3b));


            String sql4A = "";
            sql4A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where  c.VillageID='" + villageID + "' and  c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));


            tvtotal4.setText(String.valueOf(ivalue4a + ivalue4b));

            String sql5A = "";
            sql5A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));


            tvtotal5.setText(String.valueOf(ivalue5a + ivalue5b));

            String sql6A = "";
            sql6A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));


            tvtotal6.setText(String.valueOf(ivalue6a + ivalue6b));
            String sqlbp6A = "";
            sqlbp6A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6a = 0;
            ivaluebp6a = dataProvider.getMaxRecord(sqlbp6A);
            tvmaleBP6.setText(String.valueOf(ivaluebp6a));

            String sqlbp6B = "";
            sqlbp6B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6b = 0;
            ivaluebp6b = dataProvider.getMaxRecord(sqlbp6B);
            tvfemaleBP6.setText(String.valueOf(ivaluebp6b));


            tvtotalBP6.setText(String.valueOf(ivaluebp6a + ivaluebp6b));
            String sql7A = "";
            sql7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7a = 0;
            ivalue7a = dataProvider.getMaxRecord(sql7A);
            tvmale7.setText(String.valueOf(ivalue7a));

            String sql7B = "";
            sql7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7b = 0;
            ivalue7b = dataProvider.getMaxRecord(sql7B);
            tvfemale7.setText(String.valueOf(ivalue7b));


            tvtotal7.setText(String.valueOf(ivalue7a + ivalue7b));
            String sqlbp7A = "";
            sqlbp7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7a = 0;
            ivaluebp7a = dataProvider.getMaxRecord(sqlbp7A);
            tvmaleBP7.setText(String.valueOf(ivaluebp7a));

            String sqlbp7B = "";
            sqlbp7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7b = 0;
            ivaluebp7b = dataProvider.getMaxRecord(sqlbp7B);
            tvfemaleBP7.setText(String.valueOf(ivaluebp7b));


            tvtotalBP7.setText(String.valueOf(ivaluebp7a + ivaluebp7b));

            String sql8A = "";
            sql8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));
            String sql8B = "";
            sql8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));
            tvtotal8.setText(String.valueOf(ivalue8a + ivalue8b));
            String sqlbp8A = "";
            sqlbp8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8a = 0;
            ivaluebp8a = dataProvider.getMaxRecord(sqlbp8A);
            tvmaleBP8.setText(String.valueOf(ivaluebp8a));
            String sqlbp8B = "";
            sqlbp8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8b = 0;
            ivaluebp8b = dataProvider.getMaxRecord(sqlbp8B);
            tvfemaleBP8.setText(String.valueOf(ivaluebp8b));
            tvtotalBP8.setText(String.valueOf(ivaluebp8a + ivaluebp8b));

            String sql9A = "";
            sql9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where d.VillageID='" + villageID + "' and b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));
            String sql9B = "";
            sql9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));
            tvtotal9.setText(String.valueOf(ivalue9a + ivalue9b));
            String sqlBP9A = "";
            sqlBP9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalueBP9a = 0;
            ivalueBP9a = dataProvider.getMaxRecord(sqlBP9A);
            tvmaleBP9.setText(String.valueOf(ivalueBP9a));
            String sqlBP9B = "";
            sqlBP9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalueBP9b = 0;
            ivalueBP9b = dataProvider.getMaxRecord(sqlBP9B);
            tvfemaleBP9.setText(String.valueOf(ivalueBP9b));
            tvtotalBP9.setText(String.valueOf(ivalueBP9a + ivalueBP9b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showdata(int villageID, String fromdate, String todate) {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where createdon between Date('" + fromdate + "')  and Date('" + todate + "') and Tbl_HHSurvey.IsActive=1 and  Tbl_HHSurvey.VillageID='" + villageID + "' and ServiceProviderID=" + ashaid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));


            tvtotal2.setText(String.valueOf(ivalue1a + ivalue1b));
            String sql3A = "";
            sql3A = "select  count(*),case   DOBAvailable when 1 then  cast(round((julianday('NOW')-julianday(DateOfBirth))/365)  as int) when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "select  count(*),case   DOBAvailable when 1 then  cast(round((julianday('NOW')-julianday(DateOfBirth))/365)  as int) when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and b.VillageID='" + villageID + "' and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3a + ivalue3b));


            String sql4A = "";
            sql4A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and  c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));


            tvtotal4.setText(String.valueOf(ivalue4a + ivalue4b));

            String sql5A = "";
            sql5A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));


            tvtotal5.setText(String.valueOf(ivalue5a + ivalue5b));

            String sql6A = "";
            sql6A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));


            tvtotal6.setText(String.valueOf(ivalue6a + ivalue6b));
            String sqlbp6A = "";
            sqlbp6A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6a = 0;
            ivaluebp6a = dataProvider.getMaxRecord(sqlbp6A);
            tvmaleBP6.setText(String.valueOf(ivaluebp6a));

            String sqlbp6B = "";
            sqlbp6B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6b = 0;
            ivaluebp6b = dataProvider.getMaxRecord(sqlbp6B);
            tvfemaleBP6.setText(String.valueOf(ivaluebp6b));


            tvtotalBP6.setText(String.valueOf(ivaluebp6a + ivaluebp6b));
            String sql7A = "";
            sql7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7a = 0;
            ivalue7a = dataProvider.getMaxRecord(sql7A);
            tvmale7.setText(String.valueOf(ivalue7a));

            String sql7B = "";
            sql7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7b = 0;
            ivalue7b = dataProvider.getMaxRecord(sql7B);
            tvfemale7.setText(String.valueOf(ivalue7b));


            tvtotal7.setText(String.valueOf(ivalue7a + ivalue7b));
            String sqlbp7A = "";
            sqlbp7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7a = 0;
            ivaluebp7a = dataProvider.getMaxRecord(sqlbp7A);
            tvmaleBP7.setText(String.valueOf(ivaluebp7a));

            String sqlbp7B = "";
            sqlbp7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7b = 0;
            ivaluebp7b = dataProvider.getMaxRecord(sqlbp7B);
            tvfemaleBP7.setText(String.valueOf(ivaluebp7b));


            tvtotalBP7.setText(String.valueOf(ivaluebp7a + ivaluebp7b));

            String sql8A = "";
            sql8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));
            String sql8B = "";
            sql8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));
            tvtotal8.setText(String.valueOf(ivalue8a + ivalue8b));
            String sqlbp8A = "";
            sqlbp8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8a = 0;
            ivaluebp8a = dataProvider.getMaxRecord(sqlbp8A);
            tvmaleBP8.setText(String.valueOf(ivaluebp8a));
            String sqlbp8B = "";
            sqlbp8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.VillageID='" + villageID + "' and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8b = 0;
            ivaluebp8b = dataProvider.getMaxRecord(sqlbp8B);
            tvfemaleBP8.setText(String.valueOf(ivaluebp8b));
            tvtotalBP8.setText(String.valueOf(ivaluebp8a + ivaluebp8b));

            String sql9A = "";
            sql9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and d.VillageID='" + villageID + "' and b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));
            String sql9B = "";
            sql9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));
            tvtotal9.setText(String.valueOf(ivalue9a + ivalue9b));
            String sqlBP9A = "";
            sqlBP9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalueBP9a = 0;
            ivalueBP9a = dataProvider.getMaxRecord(sqlBP9A);
            tvmaleBP9.setText(String.valueOf(ivalueBP9a));
            String sqlBP9B = "";
            sqlBP9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and d.VillageID='" + villageID + "' and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalueBP9b = 0;
            ivalueBP9b = dataProvider.getMaxRecord(sqlBP9B);
            tvfemaleBP9.setText(String.valueOf(ivalueBP9b));
            tvtotalBP9.setText(String.valueOf(ivalueBP9a + ivalueBP9b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showdata(String fromdate, String todate) {
        try {
            String sql1 = "";
            sql1 = "select  count(*) from Tbl_HHSurvey  where createdon between Date('" + fromdate + "')  and Date('" + todate + "') and Tbl_HHSurvey.IsActive=1  and ServiceProviderID=" + ashaid + "";
            int ivalue1 = 0;
            ivalue1 = dataProvider.getMaxRecord(sql1);
            tvtotal.setText(String.valueOf(ivalue1));

            String sql1A = "";
            sql1A = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where  a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1a = 0;
            ivalue1a = dataProvider.getMaxRecord(sql1A);
            tvmale2.setText(String.valueOf(ivalue1a));

            String sql1B = "";
            sql1B = "select  count(*) from Tbl_HHFamilyMember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + "";
            int ivalue1b = 0;
            ivalue1b = dataProvider.getMaxRecord(sql1B);
            tvfemale2.setText(String.valueOf(ivalue1b));


            tvtotal2.setText(String.valueOf(ivalue1a + ivalue1b));
            String sql3A = "";
            sql3A = "select  count(*),case   DOBAvailable when 1 then  cast(round((julianday('NOW')-julianday(DateOfBirth))/365)  as int) when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and b.IsActive=1 and  a.GenderID=1 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3a = 0;
            ivalue3a = dataProvider.getMaxRecord(sql3A);
            tvmale3.setText(String.valueOf(ivalue3a));

            String sql3B = "";
            sql3B = "select  count(*),case   DOBAvailable when 1 then  cast(round((julianday('NOW')-julianday(DateOfBirth))/365)  as int) when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember a inner join Tbl_HHSurvey b on a.hhsurveyguid=b.hhsurveyguid where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and b.IsActive=1 and  a.GenderID=2 and ServiceProviderID = "
                    + ashaid
                    + " and age>29";
            int ivalue3b = 0;
            ivalue3b = dataProvider.getMaxRecord(sql3B);
            tvfemale3.setText(String.valueOf(ivalue3b));

            String sql3C = "";
            sql3C = "";
            int ivalue3c = 0;
            ivalue3c = dataProvider.getMaxRecord(sql3C);
            tvtotal3.setText(String.valueOf(ivalue3a + ivalue3b));


            String sql4A = "";
            sql4A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and  c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4a = 0;
            ivalue4a = dataProvider.getMaxRecord(sql4A);
            tvmale4.setText(String.valueOf(ivalue4a));

            String sql4B = "";
            sql4B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdcbac a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue4b = 0;
            ivalue4b = dataProvider.getMaxRecord(sql4B);
            tvfemale4.setText(String.valueOf(ivalue4b));


            tvtotal4.setText(String.valueOf(ivalue4a + ivalue4b));

            String sql5A = "";
            sql5A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5a = 0;
            ivalue5a = dataProvider.getMaxRecord(sql5A);
            tvmale5.setText(String.valueOf(ivalue5a));

            String sql5B = "";
            sql5B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2  and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue5b = 0;
            ivalue5b = dataProvider.getMaxRecord(sql5B);
            tvfemale5.setText(String.valueOf(ivalue5b));


            tvtotal5.setText(String.valueOf(ivalue5a + ivalue5b));

            String sql6A = "";
            sql6A = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6a = 0;
            ivalue6a = dataProvider.getMaxRecord(sql6A);
            tvmale6.setText(String.valueOf(ivalue6a));

            String sql6B = "";
            sql6B = "select count(Distinct(a.HHFamilyMemberGUID)) from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue6b = 0;
            ivalue6b = dataProvider.getMaxRecord(sql6B);
            tvfemale6.setText(String.valueOf(ivalue6b));


            tvtotal6.setText(String.valueOf(ivalue6a + ivalue6b));
            String sqlbp6A = "";
            sqlbp6A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.IsActive=1 and b.GenderId=1   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6a = 0;
            ivaluebp6a = dataProvider.getMaxRecord(sqlbp6A);
            tvmaleBP6.setText(String.valueOf(ivaluebp6a));

            String sqlbp6B = "";
            sqlbp6B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdscreening a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2   and a.status=1  and a.Referredto>0 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp6b = 0;
            ivaluebp6b = dataProvider.getMaxRecord(sqlbp6B);
            tvfemaleBP6.setText(String.valueOf(ivaluebp6b));


            tvtotalBP6.setText(String.valueOf(ivaluebp6a + ivaluebp6b));
            String sql7A = "";
            sql7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where  a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and  c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7a = 0;
            ivalue7a = dataProvider.getMaxRecord(sql7A);
            tvmale7.setText(String.valueOf(ivalue7a));

            String sql7B = "";
            sql7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and c.IsActive=1 and b.GenderId=2 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and RBS>140 and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivalue7b = 0;
            ivalue7b = dataProvider.getMaxRecord(sql7B);
            tvfemale7.setText(String.valueOf(ivalue7b));


            tvtotal7.setText(String.valueOf(ivalue7a + ivalue7b));
            String sqlbp7A = "";
            sqlbp7A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7a = 0;
            ivaluebp7a = dataProvider.getMaxRecord(sqlbp7A);
            tvmaleBP7.setText(String.valueOf(ivaluebp7a));

            String sqlbp7B = "";
            sqlbp7B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1 and substr(NcdDiagnosis,instr(NcdDiagnosis,'7') ,1)!='7' and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID='" + global.getsGlobalAshaCode() + "' ";
            int ivaluebp7b = 0;
            ivaluebp7b = dataProvider.getMaxRecord(sqlbp7B);
            tvfemaleBP7.setText(String.valueOf(ivaluebp7b));


            tvtotalBP7.setText(String.valueOf(ivaluebp7a + ivaluebp7b));

            String sql8A = "";
            sql8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8a = 0;
            ivalue8a = dataProvider.getMaxRecord(sql8A);
            tvmale8.setText(String.valueOf(ivalue8a));
            String sql8B = "";
            sql8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and RBS>140 and a.AshaID=" + ashaid + " ";
            int ivalue8b = 0;
            ivalue8b = dataProvider.getMaxRecord(sql8B);
            tvfemale8.setText(String.valueOf(ivalue8b));
            tvtotal8.setText(String.valueOf(ivalue8a + ivalue8b));
            String sqlbp8A = "";
            sqlbp8A = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=1 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8a = 0;
            ivaluebp8a = dataProvider.getMaxRecord(sqlbp8A);
            tvmaleBP8.setText(String.valueOf(ivaluebp8a));
            String sqlbp8B = "";
            sqlbp8B = "select count(Distinct(a.HHFamilyMemberGUID))  from tblncdfollowup a inner join tbl_HHFamilyMember b on a.HHFamilyMemberGUID=b.HHFamilyMemberGUID inner join Tbl_Hhsurvey c on a.HHSurveyGUID=c.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and c.IsActive=1 and b.GenderId=2 and NCDMedicine=1 and (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) and a.AshaID=" + ashaid + " ";
            int ivaluebp8b = 0;
            ivaluebp8b = dataProvider.getMaxRecord(sqlbp8B);
            tvfemaleBP8.setText(String.valueOf(ivaluebp8b));
            tvtotalBP8.setText(String.valueOf(ivaluebp8a + ivaluebp8b));

            String sql9A = "";
            sql9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID  inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalue9a = 0;
            ivalue9a = dataProvider.getMaxRecord(sql9A);
            tvmale9.setText(String.valueOf(ivalue9a));
            String sql9B = "";
            sql9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where RBS<=140 group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  RBS>140 group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "') and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalue9b = 0;
            ivalue9b = dataProvider.getMaxRecord(sql9B);
            tvfemale9.setText(String.valueOf(ivalue9b));
            tvtotal9.setText(String.valueOf(ivalue9a + ivalue9b));
            String sqlBP9A = "";
            sqlBP9A = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and  b.CreatedOn < a.CreatedOn and c.GenderID=1 and a.ashaid=" + ashaid + " ";
            int ivalueBP9a = 0;
            ivalueBP9a = dataProvider.getMaxRecord(sqlBP9A);
            tvmaleBP9.setText(String.valueOf(ivalueBP9a));
            String sqlBP9B = "";
            sqlBP9B = "select count(*) from  (SELECT max(CreatedOn) as CreatedOn, HHFamilyMemberGUID, ashaid,HHSurveyGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)<=90 and cast(substr(BP,0,instr(BP,'/')) as int) <=120) group by HHFamilyMemberGUID)a  inner join (SELECT min(CreatedOn) as CreatedOn, HHFamilyMemberGUID FROM tblncdfollowup where  (cast(substr(BP,instr(BP,'/')+1) as int)>90 or cast(substr(BP,0,instr(BP,'/')) as int) >120) group by HHFamilyMemberGUID)b  on a.HHFamilyMemberGUID = b.HHFamilyMemberGUID inner join Tbl_HHFamilyMember c on a.HHFamilyMemberGUID = c.HHFamilyMemberGUID   inner join Tbl_HHSurvey d on a.HHSurveyGUID = d.HHSurveyGUID where a.createdon between Date('" + fromdate + "')  and Date('" + todate + "')  and  b.CreatedOn < a.CreatedOn and c.GenderID=2 and a.ashaid=" + ashaid + " ";
            int ivalueBP9b = 0;
            ivalueBP9b = dataProvider.getMaxRecord(sqlBP9B);
            tvfemaleBP9.setText(String.valueOf(ivalueBP9b));
            tvtotalBP9.setText(String.valueOf(ivalueBP9a + ivalueBP9b));
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
