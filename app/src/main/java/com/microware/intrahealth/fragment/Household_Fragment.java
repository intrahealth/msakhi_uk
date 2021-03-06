package com.microware.intrahealth.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.microware.intrahealth.GPSTracker;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Survey_Activity;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.object.MstANM;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.MstCatchmentSupervisor;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstHamlet;
import com.microware.intrahealth.object.MstSubCenter;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHSurvey;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.dataprovider.DataProvider;

@SuppressLint({"ClickableViewAccessibility", "SimpleDateFormat",
        "InflateParams"})
public class Household_Fragment extends Fragment {

    EditText etFamilySNo, etSNoFamily, etHHShortName, et_Hamlet;
    Spinner spinANMname, spinVillageName, spinASHAname, SpinCaste,
            SpinFinancialStatus, spinSubCenter, spinHHstatus,
            spin_ashafacilator, spinReligionID, Spin_AnyprematureDeath,
            Spin_Anypremature_DeathYes, spin_Toilet, Spin_Waste_Disposal,
            Spin_Electricity, Spin_House_Type;
    CheckBox Drinkchk1, Drinkchk2, Drinkchk3, Drinkchk4, Drinkchk5, Drinkchk6,
            Drinkchk7, chk1, chk2,chk3, chk4, chk5, chk6;
    LinearLayout ll_jharkhand;
    TableRow tblDateOfMigrate, tblDOR, tbl_AnyprematureDeathYes, tbl_Hamlet;
    TextView tvDateOfMigrate, tvDateOfReturn, tv_ashafacilitator;
    Button btnSave, btnClose, btnSave_Verified;
    Dialog datepic;
    DatePicker datetext;
    Global global;
    GPSTracker gps;
    Geocoder geocoder;
    List<Address> addresses;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    ArrayList<MstANM> ANM = new ArrayList<MstANM>();
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayList<MstHamlet> Hamlet = new ArrayList<MstHamlet>();
    ArrayList<MstASHA> ASHA = new ArrayList<MstASHA>();
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    ArrayList<MstCatchmentSupervisor> catchment = new ArrayList<MstCatchmentSupervisor>();
    ArrayList<MstSubCenter> MstSubCenter = new ArrayList<MstSubCenter>();
    ArrayList<Tbl_HHSurvey> hhsurvey = new ArrayList<Tbl_HHSurvey>();
    String HHCode = "";
    double longitude;
    double latitude;
    int statecode = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.household_activity,
                container, false);

        spinSubCenter = (Spinner) rootView.findViewById(R.id.spinSubCenter);
        etFamilySNo = (EditText) rootView.findViewById(R.id.etFamilySNo);
        etSNoFamily = (EditText) rootView.findViewById(R.id.etSNoFamily);
        spinReligionID = (Spinner) rootView.findViewById(R.id.spinReligionID);
        etHHShortName = (EditText) rootView.findViewById(R.id.etHHShortName);

        spinANMname = (Spinner) rootView.findViewById(R.id.spinANMname);
        spinVillageName = (Spinner) rootView.findViewById(R.id.spinVillageName);
        spinASHAname = (Spinner) rootView.findViewById(R.id.spinASHAname);
        SpinCaste = (Spinner) rootView.findViewById(R.id.SpinCaste);
        spinHHstatus = (Spinner) rootView.findViewById(R.id.spinHHstatus);
        SpinFinancialStatus = (Spinner) rootView
                .findViewById(R.id.SpinFinancialStatus);
        spin_ashafacilator = (Spinner) rootView
                .findViewById(R.id.spin_ashafacilator);
        tblDateOfMigrate = (TableRow) rootView
                .findViewById(R.id.tblDateOfMigrate);
        tblDOR = (TableRow) rootView.findViewById(R.id.tblDOR);
        tvDateOfMigrate = (TextView) rootView
                .findViewById(R.id.tvDateOfMigrate);
        tvDateOfReturn = (TextView) rootView.findViewById(R.id.tvDateOfReturn);

        tv_ashafacilitator = (TextView) rootView
                .findViewById(R.id.tv_ashafacilitator);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        btnSave_Verified = (Button) rootView
                .findViewById(R.id.btnSave_Verified);
        btnClose = (Button) rootView.findViewById(R.id.btnClose);
        dataProvider = new DataProvider(getActivity());
        global = (Global) getActivity().getApplicationContext();
        // add jharkhand fields jitendra
        chk1 = (CheckBox) rootView.findViewById(R.id.chk1);
        chk2 = (CheckBox) rootView.findViewById(R.id.chk2);

        chk3 = (CheckBox) rootView.findViewById(R.id.chk3);
        chk4 = (CheckBox) rootView.findViewById(R.id.chk4);
        chk5 = (CheckBox) rootView.findViewById(R.id.chk5);
        chk6 = (CheckBox) rootView.findViewById(R.id.chk6);

        Drinkchk1 = (CheckBox) rootView.findViewById(R.id.Drinkchk1);
        Drinkchk2 = (CheckBox) rootView.findViewById(R.id.Drinkchk2);
        Drinkchk3 = (CheckBox) rootView.findViewById(R.id.Drinkchk3);
        Drinkchk4 = (CheckBox) rootView.findViewById(R.id.Drinkchk4);
        Drinkchk5 = (CheckBox) rootView.findViewById(R.id.Drinkchk5);
        Drinkchk6 = (CheckBox) rootView.findViewById(R.id.Drinkchk6);
        Drinkchk7 = (CheckBox) rootView.findViewById(R.id.Drinkchk7);
        tbl_AnyprematureDeathYes = (TableRow) rootView
                .findViewById(R.id.tbl_AnyprematureDeathYes);
        ll_jharkhand = (LinearLayout) rootView.findViewById(R.id.ll_jharkhand);
        tbl_Hamlet = (TableRow) rootView.findViewById(R.id.tbl_Hamlet);
        et_Hamlet = (EditText) rootView.findViewById(R.id.et_Hamlet);
        Spin_AnyprematureDeath = (Spinner) rootView
                .findViewById(R.id.Spin_AnyprematureDeath);
        Spin_Anypremature_DeathYes = (Spinner) rootView
                .findViewById(R.id.Spin_Anypremature_DeathYes);
        spin_Toilet = (Spinner) rootView.findViewById(R.id.spin_Toilet);
        Spin_Waste_Disposal = (Spinner) rootView
                .findViewById(R.id.Spin_Waste_Disposal);

        Spin_Electricity = (Spinner) rootView
                .findViewById(R.id.Spin_Electricity);
        Spin_House_Type = (Spinner) rootView.findViewById(R.id.Spin_House_Type);
        if (global.getiGlobalRoleID() == 3) {
            btnSave.setVisibility(View.INVISIBLE);
            btnSave_Verified.setVisibility(View.INVISIBLE);
        }

        etFamilySNo.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        etHHShortName.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // int icheck = checkValidation();
                // if (icheck == 1 && CheckValidText()==null) {
                int icheck = sCheckValidation();
                if (icheck == 1) {
                    CustomAlertSave(getResources().getString(R.string.saveque));
                } else {
                    showAlert(icheck);
                }
                // } else {
                // if(CheckValidText()!=null)
                // {
                // showtextAlert(CheckValidText());
                // }else{
                // showAlert(icheck);
                // }
                // }

            }
        });
        btnSave_Verified.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // int icheck = checkValidation();
                // if (icheck == 1 && CheckValidText()==null) {
                int icheck = sCheckValidation();
                if (icheck == 1) {
                    CustomAlertSave_verified(getResources().getString(
                            R.string.saveque));
                } else {
                    showAlert(icheck);
                }

                // } else {
                // if(CheckValidText()!=null)
                // {
                // showtextAlert(CheckValidText());
                // }else{
                // showAlert(icheck);
                // }
                // }

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Intent i = new Intent(getActivity(), Survey_Activity.class);
                getActivity().finish();
                getActivity().startActivity(i);

            }
        });
        tvDateOfMigrate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvDateOfMigrate);
            }
        });
        tvDateOfReturn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvDateOfReturn);
            }
        });
        spinHHstatus.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0) {
                    String sql = "select HHStatusID  from  Tbl_HHSurvey where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID() + "'";
                    int status = dataProvider.getMaxRecord(sql);
                    if (status == 2) {
                        if (returnid(position - 1, 10, global.getLanguage()) == 1) {
                            tblDateOfMigrate.setVisibility(View.GONE);
                            tblDOR.setVisibility(View.VISIBLE);
                            tvDateOfMigrate.setText("");
                        } else if (returnid(position - 1, 10,
                                global.getLanguage()) == 2) {
                            tblDateOfMigrate.setVisibility(View.VISIBLE);
                            tblDOR.setVisibility(View.GONE);
                            tvDateOfReturn.setText("");
                        }
                    } else {
                        // if(returnid(position-1, 10,
                        // global.getLanguage())==1){
                        // tblDateOfMigrate.setVisibility(View.VISIBLE);
                        // tblDOR.setVisibility(View.GONE);
                        // tvDateOfReturn.setText("");
                        // }else
                        if (returnid(position - 1, 10, global.getLanguage()) == 2) {
                            tblDateOfMigrate.setVisibility(View.VISIBLE);
                            tblDOR.setVisibility(View.GONE);
                            tvDateOfMigrate.setText("");
                        } else {
                            tblDateOfMigrate.setVisibility(View.GONE);
                            tblDOR.setVisibility(View.GONE);
                            tvDateOfReturn.setText("");
                            tvDateOfMigrate.setText("");
                        }
                    }

                } else {
                    tblDateOfMigrate.setVisibility(View.GONE);
                    tblDOR.setVisibility(View.GONE);
                    tvDateOfReturn.setText("");
                    tvDateOfMigrate.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        try {

            if (global.getStateCode() != null
                    && global.getStateCode().length() > 0) {

                statecode = Validate.returnIntegerValue(global.getStateCode());
            }
            if (statecode == 20) {

                ll_jharkhand.setVisibility(View.VISIBLE);
                tbl_Hamlet.setVisibility(View.VISIBLE);
                tv_ashafacilitator.setText(getResources().getString(
                        R.string.jhfacilator));
                chk2.setVisibility(View.GONE);
                chk6.setVisibility(View.VISIBLE);

            } else {
                ll_jharkhand.setVisibility(View.GONE);
                tbl_Hamlet.setVisibility(View.GONE);
                chk2.setVisibility(View.VISIBLE);
                chk6.setVisibility(View.GONE);
                chk2.setText(R.string.Cowdungcake);
                chk1.setText(R.string.Cropremains);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        fillASHAF(spin_ashafacilator);
        fillSubcenter(global.getLanguage());
        fillANMname(global.getLanguage());
        fillVillageName(global.getLanguage());
        fillASHAname(global.getLanguage());
        fillCommonRecord(SpinCaste, 2);
        fillCommonRecord(SpinFinancialStatus, 3);
        if (statecode == 20) {
            fillCommonRecord(spinHHstatus, 40);
        } else {
            fillCommonRecord(spinHHstatus, 10);
        }
        fillCommonRecord(spinReligionID, 11);
        // add jitendra

        fillCommonRecord(Spin_AnyprematureDeath, 7);
        fillCommonRecord(Spin_Anypremature_DeathYes, 32);
        fillCommonRecord(spin_Toilet, 7);
        fillCommonRecord(Spin_Waste_Disposal, 7);
        // fillCommonRecord(Spin_Drinking_Water, 33);
        fillCommonRecord(Spin_Electricity, 7);
        fillCommonRecord(Spin_House_Type, 35);

        if (global.getGlobalHHSurveyGUID() != null
                && global.getGlobalHHSurveyGUID().length() > 0) {
            filldata();
        } else {
            spinHHstatus.setSelection(returnpos(1, 10));
            if (ANM != null && ANM.size() > 0) {
                spinANMname.setSelection(1);
            }
            if (Village != null && Village.size() > 0) {
                spinVillageName.setSelection(1);
            }
            if (ASHA != null && ASHA.size() > 0) {
                spinASHAname.setSelection(1);
            }
            if (MstSubCenter != null && MstSubCenter.size() > 0) {
                spinSubCenter.setSelection(1);
            }
            filldata();
        }
        spinVillageName.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos > 0) {
                    if (global.getGlobalHHSurveyGUID().length() == 0) {
                        int VillageID = Village.get(pos - 1).getVillageID();
                        String count = "Select max(cast(HHcode as int)) from Tbl_HHSurvey where VillageID = "
                                + VillageID + " and CreatedBy=" + global.getUserID() + "";
                        int icount = dataProvider.getMaxRecord(count) + 1;
                        String SNo = String.valueOf(icount);
                        if (SNo.length() == 1) {
                            HHCode = "00" + SNo;
                        } else if (SNo.length() == 2) {
                            HHCode = "0" + SNo;
                        } else {
                            HHCode = SNo;
                        }
                        etFamilySNo.setText(String.valueOf(icount));

                        // bvghjbkjbn
                    }
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        Spin_AnyprematureDeath
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 1) {
                            tbl_AnyprematureDeathYes
                                    .setVisibility(view.VISIBLE);
                        } else {
                            tbl_AnyprematureDeathYes.setVisibility(view.GONE);
                            Spin_Anypremature_DeathYes.setSelection(0);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        etFamilySNo.setBackgroundColor(getResources()
                .getColor(R.color.lightbluencd));

        return rootView;

    }

    @SuppressWarnings("unused")
    public int sCheckValidation() {
        int age = 0;
        if (spinVillageName.getSelectedItemPosition() == 0) {
            return 2;
        }
        if (spinASHAname.getSelectedItemPosition() == 0) {
            return 3;
        }
        return 1;
    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlert(getResources().getString(R.string.villagename1));
        }
        if (iCheck == 3) {
            CustomAlert(getResources().getString(R.string.selectasha));
        }
    }

    public void CustomAlert(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
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

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void ShowDatePicker(final TextView tVvisit) {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        // TODO Auto-generated method stub
        datepic = new Dialog(getActivity(), R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        // Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);

        datepic.show();

        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMaxDate((long) date.getTime());
        // Calendar calendar = Calendar.getInstance();
        // int daysInMonth = calendar.getActualMinimum(Calendar.YEAR);
        // int daysMonth = calendar.getActualMinimum(Calendar.MONTH);
        // int maxdaysInMonth = calendar.getActualMaximum(Calendar.YEAR);
        // int maxdaysMonth = calendar.getActualMaximum(Calendar.MONTH);
        // datetext.setMaxDate(maxdaysInMonth);
        // datetext.setMaxDate(maxdaysMonth);
        // datetext.setMinDate(daysInMonth);
        // datetext.setMinDate(daysMonth);

        btncancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });

        // btnclear.setOnClickListener(new View.OnClickListener() {
        //
        // public void onClick(View v) {
        // // TODO Auto-generated
        //
        // tVvisit.setText(Datenew);
        // datepic.dismiss();
        //
        // }
        // });
        btnset.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                @SuppressWarnings("unused")
                String[] monthName = {"January", "February", "March", "April",
                        "May", "June", "July", "August", "September",
                        "October", "November", "December"};
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

    private void filldata() {
        // TODO Auto-generated method stub
        hhsurvey = dataProvider.getHHSurveyData(global.getGlobalHHSurveyGUID(),
                2, 0, 0);
        try {
            if (hhsurvey != null && hhsurvey.size() > 0) {
                etFamilySNo
                        .setText(String.valueOf(hhsurvey.get(0).getHHCode()));
                spinANMname.setSelection(returnANMname(hhsurvey.get(0)
                        .getANMID()));
                spinVillageName.setSelection(returnVillageName(hhsurvey.get(0)
                        .getVillageID()));
                spinASHAname.setSelection(returnASHAname(hhsurvey.get(0)
                        .getServiceProviderID()));
                SpinCaste.setSelection(returnpos(hhsurvey.get(0).getCasteID(),
                        2));
                SpinFinancialStatus.setSelection(returnpos(hhsurvey.get(0)
                        .getFinancialStatusID(), 3));
                if (statecode == 20) {
                    spinHHstatus.setSelection(returnpos(hhsurvey.get(0)
                            .getHHStatusID(), 40));
                } else {
                    spinHHstatus.setSelection(returnpos(hhsurvey.get(0)
                            .getHHStatusID(), 10));
                }
                spinSubCenter.setSelection(returnSubCenterPos(hhsurvey.get(0)
                        .getSubCenterID()));
                spinReligionID.setSelection(returnpos(hhsurvey.get(0)
                        .getReligionID(), 11));
                etHHShortName.setText(String.valueOf(hhsurvey.get(0)
                        .getHHShortName()));
                tvDateOfMigrate
                        .setText(String.valueOf(Validate
                                .changeDateFormat(hhsurvey.get(0)
                                        .getMigrateOutDate())));
                tvDateOfReturn.setText(String.valueOf(Validate
                        .changeDateFormat(hhsurvey.get(0).getMigrateInDate())));
                spin_ashafacilator.setSelection(returnSupervisorname(hhsurvey
                        .get(0).getCHS_ID()));
                et_Hamlet.setText(hhsurvey.get(0).getHamlet());
                Spin_AnyprematureDeath.setSelection(returnpos(hhsurvey.get(0)
                        .getAnypremature_Death(), 7));
                Spin_Anypremature_DeathYes.setSelection(returnpos(
                        hhsurvey.get(0).getAnypremature_DeathYes(), 32));
                spin_Toilet.setSelection(returnpos(hhsurvey.get(0).getToilet(),
                        7));
                Spin_Waste_Disposal.setSelection(returnpos(hhsurvey.get(0)
                        .getWaste_Disposal(), 7));

                Spin_Electricity.setSelection(returnpos(hhsurvey.get(0)
                        .getElectricity(), 7));
                Spin_House_Type.setSelection(returnpos(hhsurvey.get(0)
                        .getHouse_Type(), 35));

                String[] arr1 = hhsurvey.get(0).getCooking_Fuel().split(",");

                for (int i = 0; i < arr1.length; i++) {
                    for (int j = 0; j < arr1.length; j++) {

                        if (arr1[i].equalsIgnoreCase("1")) {
                            chk1.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("2")) {
                            chk3.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("3")) {
                            chk4.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("4")) {
                            chk5.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("5")) {
                            chk6.setChecked(true);
                            break;
                        }else if (arr1[i].equalsIgnoreCase("6")) {
                            chk2.setChecked(true);
                            break;
                        }

                    }
                }
                String[] arr2 = hhsurvey.get(0).getDrinking_Water().split(",");

                for (int i = 0; i < arr2.length; i++) {
                    for (int j = 0; j < arr2.length; j++) {

                        if (arr2[i].equalsIgnoreCase("1")) {
                            Drinkchk1.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("2")) {
                            Drinkchk2.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("3")) {
                            Drinkchk3.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("4")) {
                            Drinkchk4.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("5")) {
                            Drinkchk5.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("6")) {
                            Drinkchk6.setChecked(true);
                            break;
                        } else if (arr2[i].equalsIgnoreCase("7")) {
                            Drinkchk7.setChecked(true);
                            break;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int returnSubCenterPos(int subCenterID) {
        // TODO Auto-generated method stub

        int subCenter = 0;
        if (subCenterID > 0) {
            for (int i = 0; i < MstSubCenter.size(); i++) {
                if (subCenterID == MstSubCenter.get(i).getSubCenterID()) {
                    subCenter = i + 1;
                }
            }
        }
        return subCenter;

    }

    private void fillSubcenter(int Language) {
        // TODO Auto-generated method stub

        MstSubCenter = dataProvider.getMstSubCenter(Language);

        String sValue[] = new String[MstSubCenter.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < MstSubCenter.size(); i++) {
            sValue[i + 1] = MstSubCenter.get(i).getSubCenterName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinSubCenter.setAdapter(adapter);

    }

    private void fillANMname(int Language) {
        ANM = dataProvider.getMstANMname(Language);

        String sValue[] = new String[ANM.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < ANM.size(); i++) {
            sValue[i + 1] = ANM.get(i).getANMName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinANMname.setAdapter(adapter);
    }

    public int returnANMname(int ANMid) {
        int iAKM = 0;
        if (ANMid > 0) {
            for (int i = 0; i < ANM.size(); i++) {
                if (ANMid == ANM.get(i).getANMID()) {
                    iAKM = i + 1;
                }
            }
        }
        return iAKM;

    }

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }

    public int returnVillageName(int Villageid) {
        int iVillage = 0;
        if (Villageid > 0) {
            for (int i = 0; i < Village.size(); i++) {
                if (Villageid == Village.get(i).getVillageID()) {
                    iVillage = i + 1;
                }
            }
        }
        return iVillage;

    }

    private void fillASHAname(int Language) {

        ASHA = dataProvider.getMstASHAname(Language, global.getsGlobalAshaCode(), 2);

        String sValue[] = new String[ASHA.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < ASHA.size(); i++) {
            sValue[i + 1] = ASHA.get(i).getASHAName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinASHAname.setAdapter(adapter);
    }

    public int returnASHAname(int ASHAid) {
        int iASHA = 0;
        if (ASHAid > 0) {
            for (int i = 0; i < ASHA.size(); i++) {
                if (ASHAid == ASHA.get(i).getASHAID()) {
                    iASHA = i + 1;
                }
            }
        }
        return iASHA;

    }

    public int returnSupervisorname(int supervisorid) {
        int iASHA = 0;
        if (supervisorid > 0) {
            for (int i = 0; i < catchment.size(); i++) {
                if (supervisorid == catchment.get(i).getCHS_ID()) {
                    iASHA = i + 1;
                }
            }
        }
        return iASHA;

    }

    public void fillASHAF(Spinner spin) {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Validate.returnIntegerValue(global.getsGlobalAshaCode());
        }
        catchment = dataProvider.getCatchsupervisor(global.getLanguage(), ashaid);

        String WhereHouse[] = new String[catchment.size() + 1];
        WhereHouse[0] = getResources().getString(R.string.select);

        for (int i = 1; i < catchment.size() + 1; i++) {
            WhereHouse[i] = catchment.get(i - 1).getSupervisorName();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, WhereHouse);
        spin.setAdapter(adapter);
    }

    public void fillCommonRecord(Spinner spin, int iFlag) {
        Common = dataProvider.getCommonRecord(global.getLanguage(), iFlag);

        String WhereHouse[] = new String[Common.size() + 1];
        WhereHouse[0] = getResources().getString(R.string.select);

        for (int i = 1; i < Common.size() + 1; i++) {
            WhereHouse[i] = Common.get(i - 1).getValue();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, WhereHouse);
        spin.setAdapter(adapter);
    }

    public int returnpos(int value, int iFlag) {
        int iValue = 0;
        Common = dataProvider.getCommonRecord(global.getLanguage(), iFlag);
        for (int i = 0; i < Common.size(); i++) {
            if (Common.get(i).getID() == value) {
                iValue = i + 1;
            }
        }
        return iValue;

    }

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

    }

    public void SaveHousehold() {
        String CurrentDate = "";
        int SubCenter = 0;
        int FamilySNo = 0;
        // int SNoFamily=0;
        int ANMid = 0;
        int Villageid = 0;
        int ASHAid = 0;
        int Casteid = 0;
        String MigrateInDate = "";
        String MigrateOutDate = "";
        int FinancialStatusid = 0;
        int HHstatus = 0;
        int ireligionid = 0;
        int facilatorid = 0;
        String Latitude = "";
        String Longitude = "";
        String Location = "";

        String Hamlet = "";
        int Anypremature_Death = 0;
        int Anypremature_DeathYes = 0;
        int Toilet = 0;
        int Waste_Disposal = 0;
        String Drinking_Water = "";
        int Electricity = 0;
        String Cooking_Fuel = "";
        int House_Type = 0;

        if (global.getsGlobalLocation() != null) {
            Location = global.getsGlobalLocation();
        }
        if (global.getsGlobalLatitude() != null) {
            Latitude = global.getsGlobalLatitude();
        }
        if (global.getsGlobalLongitude() != null) {
            Longitude = global.getsGlobalLongitude();
        }
        String iHHShortName = "";
        CurrentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                .format(new Date());

        if (spinSubCenter.getSelectedItemPosition() > 0) {
            SubCenter = MstSubCenter.get(
                    spinSubCenter.getSelectedItemPosition() - 1)
                    .getSubCenterID();
        }
        if (etFamilySNo.getText().toString().length() > 0) {
            FamilySNo = Validate.returnIntegerValue(etFamilySNo.getText().toString());
        }
        if (etHHShortName.getText().toString().length() > 0) {
            iHHShortName = etHHShortName.getText().toString();
        }
        if (tvDateOfMigrate.getText().toString().length() > 0) {
            MigrateOutDate = Validate.changeDateFormat(tvDateOfMigrate
                    .getText().toString());
        }
        if (tvDateOfReturn.getText().toString().length() > 0) {
            MigrateInDate = Validate.changeDateFormat(tvDateOfReturn.getText()
                    .toString());
        }

        if (spinReligionID.getSelectedItemPosition() > 0) {
            Common = dataProvider.getCommonRecord(global.getLanguage(), 11);
            ireligionid = Common.get(
                    spinReligionID.getSelectedItemPosition() - 1).getID();
        }

        if (etSNoFamily.getText().toString().length() > 0) {
            // SNoFamily = Validate.returnIntegerValue(etSNoFamily.getText().toString());
        }
        if (spinANMname.getSelectedItemPosition() > 0) {
            ANMid = ANM.get(spinANMname.getSelectedItemPosition() - 1)
                    .getANMID();
        }
        if (spinVillageName.getSelectedItemPosition() > 0) {
            Villageid = Village.get(
                    spinVillageName.getSelectedItemPosition() - 1)
                    .getVillageID();
        }
        if (spinASHAname.getSelectedItemPosition() > 0) {
            ASHAid = ASHA.get(spinASHAname.getSelectedItemPosition() - 1)
                    .getASHAID();
            String AshaCode = ASHA.get(
                    spinASHAname.getSelectedItemPosition() - 1).getASHACode();
            global.setsGlobalAshaCode(String.valueOf(ASHAid));
        }
        if (SpinCaste.getSelectedItemPosition() > 0) {
            Common = dataProvider.getCommonRecord(global.getLanguage(), 2);
            Casteid = Common.get(SpinCaste.getSelectedItemPosition() - 1)
                    .getID();
        }

        if (spinHHstatus.getSelectedItemPosition() > 0) {
            if (statecode == 20) {
                Common = dataProvider.getCommonRecord(global.getLanguage(), 40);
            } else {
                Common = dataProvider.getCommonRecord(global.getLanguage(), 10);
            }
            HHstatus = Common.get(spinHHstatus.getSelectedItemPosition() - 1)
                    .getID();
        }

        if (SpinFinancialStatus.getSelectedItemPosition() > 0) {
            Common = dataProvider.getCommonRecord(global.getLanguage(), 3);
            FinancialStatusid = Common.get(
                    SpinFinancialStatus.getSelectedItemPosition() - 1).getID();

        }
        if (spin_ashafacilator.getSelectedItemPosition() > 0) {
            //catchment = dataProvider.getCatchsupervisor(global.getLanguage());
            facilatorid = catchment.get(
                    spin_ashafacilator.getSelectedItemPosition() - 1)
                    .getCHS_ID();

        }

        Hamlet = et_Hamlet.getText().toString();

        if (Spin_AnyprematureDeath.getSelectedItemPosition() > 0) {
            Anypremature_Death = Spin_AnyprematureDeath
                    .getSelectedItemPosition();

        }
        if (Spin_Anypremature_DeathYes.getSelectedItemPosition() > 0) {
            Anypremature_DeathYes = Spin_Anypremature_DeathYes
                    .getSelectedItemPosition();

        }
        if (spin_Toilet.getSelectedItemPosition() > 0) {
            Toilet = spin_Toilet.getSelectedItemPosition();

        }
        if (Spin_Waste_Disposal.getSelectedItemPosition() > 0) {
            Waste_Disposal = Spin_Waste_Disposal.getSelectedItemPosition();

        }

        Drinking_Water = savecheckboxwater();

        if (Spin_Electricity.getSelectedItemPosition() > 0) {
            Electricity = Spin_Electricity.getSelectedItemPosition();

        }
        if (Spin_House_Type.getSelectedItemPosition() > 0) {
            House_Type = Spin_House_Type.getSelectedItemPosition();

        }

        Cooking_Fuel = savecheckbox();

        if (global.getGlobalHHSurveyGUID().length() == 0) {
            String hhSurveyGUID = Validate.random();
            dataProvider.SaveSurveyData(CurrentDate, SubCenter, FamilySNo,
                    ANMid, Villageid, HHstatus, ASHAid, Casteid,
                    FinancialStatusid, global.getsGlobalUserID(), hhSurveyGUID,
                    HHCode, iHHShortName, ireligionid, facilatorid, Latitude,
                    Longitude, Location, MigrateInDate, MigrateOutDate, Hamlet,
                    Anypremature_Death, Anypremature_DeathYes, Toilet,
                    Waste_Disposal, Drinking_Water, Electricity, Cooking_Fuel,
                    House_Type);
            global.setGlobalHHSurveyGUID(hhSurveyGUID);

        } else {
            dataProvider.UpdateHousehold(CurrentDate, SubCenter, FamilySNo,
                    ANMid, Villageid, HHstatus, ASHAid, Casteid,
                    FinancialStatusid, CurrentDate, global.getsGlobalUserID(),
                    global.getGlobalHHSurveyGUID(), iHHShortName, ireligionid,
                    facilatorid, Latitude, Longitude, Location, MigrateInDate,
                    MigrateOutDate, Hamlet, Anypremature_Death,
                    Anypremature_DeathYes, Toilet, Waste_Disposal,
                    Drinking_Water, Electricity, Cooking_Fuel, House_Type);
            String status = "";
            String statusmember = "", sqlmembermigin = "", sqlmembermigout = "";
            statusmember = "select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + global.getGlobalHHSurveyGUID() + "' and StatusID=1";
            sqlmembermigout = "select HHFamilyMemberGUID from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + global.getGlobalHHSurveyGUID() + "' and StatusID=1";
            sqlmembermigin = "select HHFamilyMemberGUID from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + global.getGlobalHHSurveyGUID() + "' and StatusID=3";
            int memcount = dataProvider.getMaxRecord(statusmember);
            ArrayList<HashMap<String, String>> data = null;


            if (HHstatus == 2 || HHstatus == 3) {
                data = dataProvider.getDynamicVal(sqlmembermigout);
                status = "Update Tbl_HHFamilyMember set StatusID=3 where HHSurveyGUID='"
                        + global.getGlobalHHSurveyGUID() + "'";
                String CreatedOn = "", MigrationGUID = "";
                CreatedOn = Validate.getcurrentdate();
                for (int i = 0; i < data.size(); i++) {
                    MigrationGUID = Validate.random();
                    dataProvider.saveMigrationDetails("i", CreatedOn, global.getUserID(), "", CreatedOn, MigrationGUID, data.get(i).get("HHFamilyMemberGUID"));

                }

            } else if (HHstatus == 1 && memcount == 0) {
                data = dataProvider.getDynamicVal(sqlmembermigin);
                status = "Update Tbl_HHFamilyMember set StatusID=1 where HHSurveyGUID='"
                        + global.getGlobalHHSurveyGUID() + "'";
                String CreatedOn = "";
                CreatedOn = Validate.getcurrentdate();
                for (int i = 0; i < data.size(); i++) {
                    String sql = "update tblMigration set DateOfMigrationIn='"
                            + CreatedOn + "', UpdatedOn='" + CreatedOn
                            + "',UpdatedBy=" + global.getUserID()
                            + " ,IsEdited=1,IsUploaded=0 where HHFamilyMemberGUID='"
                            + data.get(i).get("HHFamilyMemberGUID") + "' and (DateOfMigrationIn='' or DateOfMigrationIn is null)";
                    dataProvider.executeSql(sql);
                }

            }
            dataProvider.executeSql(status);
            // ((Intrahealth_Tab_Activity) getActivity()).swapscreen(1);

        }

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (global.getGlobalHHSurveyGUID() != null
                && global.getGlobalHHSurveyGUID().length() > 0) {
            filldata();
        }
    }

    public void CustomAlertS(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                ((Intrahealth_Tab_Activity) getActivity()).swapscreen(1);
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public String savecheckbox() {
        String ss = "";
        if (chk1.isChecked()) {
            ss = ss + "1,";
        }
         if (chk3.isChecked()) {
            ss = ss + "2,";
        }
        if (chk4.isChecked()) {
            ss = ss + "3,";
        }
        if (chk5.isChecked()) {
            ss = ss + "4,";
        }
        if (chk6.isChecked()) {
            ss = ss + "5,";
        }if (chk2.isChecked()) {
            ss = ss + "6";
        }

        return ss;
    }

    public String savecheckboxwater() {
        String ss = "";
        if (Drinkchk1.isChecked()) {
            ss = ss + "1,";
        }
        if (Drinkchk2.isChecked()) {
            ss = ss + "2,";
        }
        if (Drinkchk3.isChecked()) {
            ss = ss + "3,";
        }
        if (Drinkchk4.isChecked()) {
            ss = ss + "4,";
        }
        if (Drinkchk5.isChecked()) {
            ss = ss + "5,";
        }
        if (Drinkchk6.isChecked()) {
            ss = ss + "6,";
        }
        if (Drinkchk7.isChecked()) {
            ss = ss + "7";
        }
        return ss;
    }

    public void CustomAlertSave(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_checklayout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        gps = new GPSTracker(getActivity());
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();
                if (gps.isGPSEnabled == true && gps.isNetworkEnabled == true) {
                    global.setsGlobalLatitude("");
                    global.setsGlobalLongitude("");
                    if (gps.canGetLocation()) {
                        gps.getLatitude(); // returns latitude
                        gps.getLongitude();
                        // gps.showSettingsAlert();
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        String lat = String.valueOf(latitude);
                        String lon = String.valueOf(longitude);
                        // String loc=String.valueOf(location);
                        global.setsGlobalLatitude(lat);
                        global.setsGlobalLongitude(lon);
                        //
                        System.out
                                .println("Both lat and Long" + lat + "" + lon);

                        SaveHousehold();
                        CustomAlertS(getResources().getString(
                                R.string.savesuccessfully));
                    }
                } else {
                    showSettingsAlert();
                }

            }
        });

        btnno.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Display the dialog
        dialog.show();

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle(getResources().getString(R.string.app_name));

        // Setting Dialog Message
        alertDialog
                .setMessage(getResources().getString(R.string.Gpsenabled));

        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.Settings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        /*
                         * Intent intent1 = new
						 * Intent(Settings.ACTION_APPLICATION_SETTINGS);
						 * mContext.startActivity(intent1);
						 */
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        // alertDialog.setIcon(R.drawable.msakhi_logo);
        alertDialog.show();
    }

    public void CustomAlertSave_verified(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_checklayout, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        gps = new GPSTracker(getActivity());
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

                if (gps.isGPSEnabled == true && gps.isNetworkEnabled == true) {
                    global.setsGlobalLatitude("");
                    global.setsGlobalLongitude("");
                    if (gps.canGetLocation()) {
                        gps.getLatitude(); // returns latitude
                        gps.getLongitude();
                        // gps.showSettingsAlert();
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();

                        String lat = String.valueOf(latitude);
                        String lon = String.valueOf(longitude);
                        // String loc=String.valueOf(location);
                        global.setsGlobalLatitude(lat);
                        global.setsGlobalLongitude(lon);
                        //
                        System.out
                                .println("Both lat and Long" + lat + "" + lon);

                        SaveHousehold();
                        String verify = "Update Tbl_HHSurvey set Verified =1 where HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID() + "'";
                        dataProvider.executeSql(verify);
                        CustomAlertS(getResources().getString(
                                R.string.savesuccessfully_verify));
                    }
                } else {
                    showSettingsAlert();
                }

            }
        });

        btnno.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Display the dialog
        dialog.show();

    }

}
