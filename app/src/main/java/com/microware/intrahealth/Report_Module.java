package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.microware.intrahealth.dataprovider.DataProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Report_Module extends Activity {

    Button btncrvs, btnHousehold, btnhrpReport, btnAnc, btnHNBC, btn_VHNDANCduelist, btn_VHNDImmunizationduelist, btn_ANCduelist, btn_Immunizationduelist, btnReport, btnashaPdfReport;
    Global global;

    Validate validate;
    DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        setTitle(global.getVersionName());
        setContentView(R.layout.reportmodulescreen);
        btnHousehold = (Button) findViewById(R.id.btnHousehold);
        btnAnc = (Button) findViewById(R.id.btnANC);
        btnHNBC = (Button) findViewById(R.id.btnHNBC);
        btncrvs = (Button) findViewById(R.id.btncrvs);
        btn_Immunizationduelist = (Button) findViewById(R.id.btn_Immunizationduelist);
        btn_ANCduelist = (Button) findViewById(R.id.btn_ANCduelist);
        btn_VHNDImmunizationduelist = (Button) findViewById(R.id.btn_VHNDImmunizationduelist);
        btn_VHNDANCduelist = (Button) findViewById(R.id.btn_VHNDANCduelist);
        btnReport = (Button) findViewById(R.id.btnReport);
        btnhrpReport = (Button) findViewById(R.id.btnhrpReport);
        btnashaPdfReport = (Button) findViewById(R.id.btnashaPdfReport);

        try {
            int statecode = 0;
            if (global.getStateCode() != null
                    && global.getStateCode().length() > 0) {

                statecode = Integer.valueOf(global.getStateCode());
            }
            if (statecode == 20) {

                btnAnc.setVisibility(View.GONE);
                btnHNBC.setVisibility(View.GONE);
                btncrvs.setVisibility(View.GONE);
                btn_ANCduelist.setVisibility(View.GONE);
                btn_Immunizationduelist.setVisibility(View.GONE);
                btn_VHNDANCduelist.setVisibility(View.GONE);
                btn_VHNDImmunizationduelist.setVisibility(View.GONE);
                btnReport.setVisibility(View.VISIBLE);
                btnhrpReport.setVisibility(View.GONE);
                btnashaPdfReport.setVisibility(View.GONE);

            } else {
                // btnReport.setVisibility(View.GONE);
                if (global.getiGlobalRoleID() != 2) {
                    btnashaPdfReport.setVisibility(View.GONE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createpdf() {

        String[] Page2 = new String[6];
        Page2[0] = "aknjk1";
        Page2[1] = "alklka2";
        Page2[2] = "amit3";
        Page2[3] = "laika4";
        Page2[4] = "ajjj5";
        Page2[5] = "malik6";

        String[] Header = getResources().getStringArray(R.array.pdfcoloumn);
        ArrayList<HashMap<String, String>> data;
        String sqldata = "select MstASHA.ASHAName,MstVillage.VillageName, tblPregnant_woman.PWName,tblPregnant_woman.MotherMCTSID,tblPregnant_woman.HusbandName ,tblANCVisit.CheckupVisitDate,tblANCVisit.DangerSign,tblANCVisit.CheckupPlace from tblPregnant_woman inner join Tbl_HHSurvey on tblPregnant_woman.HHGUID=Tbl_HHSurvey.HHSurveyGUID inner join MstVillage on MstVillage.VillageID=Tbl_HHSurvey.VillageID inner join  MstASHA on tblPregnant_woman.AshaID=MstASHA.ASHAID inner join tblANCVisit on tblPregnant_woman.PWGUID=tblANCVisit.PWGUID where (tblANCVisit.CheckupVisitDate is not null and tblANCVisit.CheckupVisitDate!='')  and  tblPregnant_woman.HighRisk=1 and  tblPregnant_woman.IsPregnant=1 and MstVillage.LanguageID=" + global.getLanguage() + " and  MstASHA.LanguageID=" + global.getLanguage() + " Group by tblANCVisit.PWGUID";
        data = dataProvider.getDynamicVal(sqldata);

        Createpdf2 fop = new Createpdf2();

        String filename = Validate.getcurrentdate() + global.getsGlobalAshaName();
        try {
            if (data.size() > 0) {
                if (fop.write(this, filename, Header, data, Page2, global.getLanguage())) {
                    Toast.makeText(this,
                            filename + getString(R.string.pdfcreated), Toast.LENGTH_SHORT)
                            .show();


                } else {
                    Toast.makeText(this, R.string.ioerror,
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, R.string.nodata,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onclickButton(View view) {

        Intent in;
        switch (view.getId()) {

            case R.id.btnHousehold:

                if (global.getiGlobalRoleID() == 3) {
                    in = new Intent(Report_Module.this,
                            ReportIndicator_anmListHH.class);
                    finish();
                    startActivity(in);
                } else {
                    in = new Intent(Report_Module.this, ReportIndicatorListHH.class);
                    finish();
                    startActivity(in);
                }

                break;
            case R.id.btnANC:

                if (global.getiGlobalRoleID() == 3) {
                    in = new Intent(Report_Module.this,
                            ReportIndicator_anmList.class);
                    finish();
                    startActivity(in);
                } else {
                    in = new Intent(Report_Module.this,
                            ReportIndicator_ashaList.class);
                    finish();
                    startActivity(in);
                }

                break;
            case R.id.btnHNBC:

                if (global.getiGlobalRoleID() == 2 || global.getiGlobalRoleID() == 3) {
                    in = new Intent(Report_Module.this,
                            HbncReport.class);
                    finish();
                    startActivity(in);
                }
                break;
            case R.id.btncrvs:

                if (global.getiGlobalRoleID() == 2) {
                    in = new Intent(Report_Module.this,
                            CRVSReportASHA.class);
                    finish();
                    startActivity(in);
                }
                break;
            case R.id.btn_Immunizationduelist:
                if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(Report_Module.this, Immunization_DueListAnm.class);
                    finish();
                    startActivity(i);

                } else if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(Report_Module.this, Immunization_DueList.class);
                    finish();
                    startActivity(i);

                }
                break;
            case R.id.btn_ANCduelist:
                if (global.getiGlobalRoleID() == 3) {

                    Intent i = new Intent(Report_Module.this, AncReport_DueListAnm.class);
                    finish();
                    startActivity(i);

                } else if (global.getiGlobalRoleID() == 2) {

                    Intent i = new Intent(Report_Module.this, AncReport_DueList.class);
                    finish();
                    startActivity(i);

                }
                break;
            case R.id.btn_VHNDImmunizationduelist:
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(Report_Module.this, VHND_Immunizatiom.class);
                    finish();
                    startActivity(i);

                }
                break;
            case R.id.btn_VHNDANCduelist:
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(Report_Module.this, VHND_ANC.class);
                    finish();
                    startActivity(i);

                }
                break;
            case R.id.btnhrpReport:
                createpdf();
                break;
            case R.id.btnReport:
                if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(Report_Module.this,
                            Anm_NCD_Report.class);
                    finish();
                    startActivity(i);
                } else if (global.getiGlobalRoleID() == 2) {
                    in = new Intent(Report_Module.this,
                            NCD_Report.class);
                    finish();
                    startActivity(in);
                }
                break;
            case R.id.btnashaPdfReport:
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(Report_Module.this,
                            PdfReport.class);
                    finish();
                    startActivity(i);
                } else {

                }
                break;
            default:
                break;
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Intent i = new Intent(Report_Module.this, Dashboard_Activity.class);
        finish();
        startActivity(i);
    }

}