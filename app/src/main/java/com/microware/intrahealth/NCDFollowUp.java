package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstVillage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

public class NCDFollowUp extends Activity {
    EditText et_name, et_guardian, et_panchayat, et_registrationno,
            et_Othergovernment_facility, et_Otherproblem_faced,
            et_OtherGovernment_Facility_diagnosis, et_Otherneithertogovernment,
            et_Othertaking_medicines, et_bsl, et_Systolic, et_Diastolic,
            et_AnySuggestion, et_SpinOther, et_day, et_month, et_Spinyesmedicinesother;
    CheckBox chk_Chc, chk_phc, chk_District_hospital, chk_Tertiary,
            chk_Private_doctor, chk_Private_Clinic, chk_Did, chk_doctor,
            chk_Medicines, chk_Bslcheckup, chk_BP_HTN, chk_HBA1C,
            chk_lipid_Profile, chk_Other1, chk_Doctorsnotavail,
            chk_Medicinesnotavail, chk_Doctorsnotbehave, chk_testnotavail,
            chk_Distanceproblem, chk_Notthoughfacilit, chk_Others2,
            chk_Labtechnician, chk_Medicinewerenotavail, chk_Gluco_strips,
            chk_Doctorsdidbehavproperly, chk_Doctorsnotpresent, chk_Others3,
            chk_Financialconstraint, chk_notlikego, chk_quack, chk_chemist,
            chk_Relative, chk_Communicationdistance, Chk_Others4,
            chk1_Financialconstraint, chk_madicinesnotavilatgovrnment,
            chk_notmadicines, chk_Other5, chk_Consultation;
    RadioButton radio_HighRisk, radio_Suspected;
    RadioGroup RadioGroupreferral;
    Dialog datepic;
    Spinner spin_counsellingtips, Spin_MedicineNCD, Spin_Medicinetaken,
            spin_Village, Spin_continuemedicines, Spin_wheretake, spin_referral1;
    TableRow tblQ3_1, tblQ3_2, tblQ3_3, tblQ4_1, tblQ4_2, tblQ5_1, tblQ5_2,
            tblQ6_1, tblQ6_2, tblQ7_1, tblQ7_2, tblQ9_1, tblQ10_1, tblQ10_2,
            tbl_SpinOther, tblOther1, tblOther2, tblOther3, tblOther4,
            tblOther5, tbl_green, tbl_wheretakemadicine, tbl_Spinyesmedicinesother, tblQdaymonth, tbldaymonth, tbl_continuemedicines;
    Button btnSave;
    TextView tv_Date;
    Global global;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdfollowup);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
        et_name = (EditText) findViewById(R.id.et_name);
        et_guardian = (EditText) findViewById(R.id.et_guardian);
        tv_Date = (TextView) findViewById(R.id.tv_Date);
        et_panchayat = (EditText) findViewById(R.id.et_panchayat);
        et_registrationno = (EditText) findViewById(R.id.et_registrationno);
        et_Othergovernment_facility = (EditText) findViewById(R.id.et_Othergovernment_facility);
        et_Otherproblem_faced = (EditText) findViewById(R.id.et_Otherproblem_faced);
        et_OtherGovernment_Facility_diagnosis = (EditText) findViewById(R.id.et_OtherGovernment_Facility_diagnosis);
        et_Otherneithertogovernment = (EditText) findViewById(R.id.et_Otherneithertogovernment);
        et_Othertaking_medicines = (EditText) findViewById(R.id.et_Othertaking_medicines);
        et_bsl = (EditText) findViewById(R.id.et_bsl);
        et_Systolic = (EditText) findViewById(R.id.et_Systolic);
        et_Diastolic = (EditText) findViewById(R.id.et_Diastolic);
        et_day = (EditText) findViewById(R.id.et_day);
        et_month = (EditText) findViewById(R.id.et_month);
        et_Spinyesmedicinesother = (EditText) findViewById(R.id.et_Spinyesmedicinesother);

        radio_HighRisk = (RadioButton) findViewById(R.id.radio_HighRisk);
        radio_Suspected = (RadioButton) findViewById(R.id.radio_Suspected);
        RadioGroupreferral = (RadioGroup) findViewById(R.id.RadioGroupreferral);
        et_AnySuggestion = (EditText) findViewById(R.id.et_AnySuggestion);
        et_SpinOther = (EditText) findViewById(R.id.et_SpinOther);
        chk_Chc = (CheckBox) findViewById(R.id.chk_Chc);
        chk_phc = (CheckBox) findViewById(R.id.chk_phc);
        chk_District_hospital = (CheckBox) findViewById(R.id.chk_District_hospital);
        chk_Tertiary = (CheckBox) findViewById(R.id.chk_Tertiary);
        chk_Private_doctor = (CheckBox) findViewById(R.id.chk_Private_doctor);
        chk_Private_Clinic = (CheckBox) findViewById(R.id.chk_Private_Clinic);
        chk_Did = (CheckBox) findViewById(R.id.chk_Did);
        chk_doctor = (CheckBox) findViewById(R.id.chk_doctor);
        chk_Medicines = (CheckBox) findViewById(R.id.chk_Medicines);
        chk_Bslcheckup = (CheckBox) findViewById(R.id.chk_Bslcheckup);
        chk_BP_HTN = (CheckBox) findViewById(R.id.chk_BP_HTN);
        chk_HBA1C = (CheckBox) findViewById(R.id.chk_HBA1C);
        chk_Consultation = (CheckBox) findViewById(R.id.chk_Consultation);
        chk_lipid_Profile = (CheckBox) findViewById(R.id.chk_lipid_Profile);
        chk_Other1 = (CheckBox) findViewById(R.id.chk_Other1);
        chk_Doctorsnotavail = (CheckBox) findViewById(R.id.chk_Doctorsnotavail);
        chk_Medicinesnotavail = (CheckBox) findViewById(R.id.chk_Medicinesnotavail);
        chk_Doctorsnotbehave = (CheckBox) findViewById(R.id.chk_Doctorsnotbehave);
        chk_testnotavail = (CheckBox) findViewById(R.id.chk_testnotavail);
        chk_Distanceproblem = (CheckBox) findViewById(R.id.chk_Distanceproblem);
        chk_Notthoughfacilit = (CheckBox) findViewById(R.id.chk_Notthoughfacilit);
        chk_Others2 = (CheckBox) findViewById(R.id.chk_Others2);
        chk_Labtechnician = (CheckBox) findViewById(R.id.chk_Labtechnician);
        chk_Medicinewerenotavail = (CheckBox) findViewById(R.id.chk_Medicinewerenotavail);
        chk_Gluco_strips = (CheckBox) findViewById(R.id.chk_Gluco_strips);
        chk_Doctorsdidbehavproperly = (CheckBox) findViewById(R.id.chk_Doctorsdidbehavproperly);
        chk_Doctorsnotpresent = (CheckBox) findViewById(R.id.chk_Doctorsnotpresent);
        chk_Others3 = (CheckBox) findViewById(R.id.chk_Others3);
        chk_Financialconstraint = (CheckBox) findViewById(R.id.chk_Financialconstraint);
        chk_notlikego = (CheckBox) findViewById(R.id.chk_notlikego);

        chk_quack = (CheckBox) findViewById(R.id.chk_quack);
        chk_chemist = (CheckBox) findViewById(R.id.chk_chemist);

        chk_Relative = (CheckBox) findViewById(R.id.chk_Relative);
        chk_Communicationdistance = (CheckBox) findViewById(R.id.chk_Communicationdistance);
        Chk_Others4 = (CheckBox) findViewById(R.id.Chk_Others4);

        chk1_Financialconstraint = (CheckBox) findViewById(R.id.chk1_Financialconstraint);
        chk_madicinesnotavilatgovrnment = (CheckBox) findViewById(R.id.chk_madicinesnotavilatgovrnment);
        chk_notmadicines = (CheckBox) findViewById(R.id.chk_notmadicines);

        chk_Other5 = (CheckBox) findViewById(R.id.chk_Other5);

        spin_counsellingtips = (Spinner) findViewById(R.id.spin_counsellingtips);
        Spin_Medicinetaken = (Spinner) findViewById(R.id.Spin_Medicinetaken);
        Spin_MedicineNCD = (Spinner) findViewById(R.id.Spin_MedicineNCD);
        Spin_continuemedicines = (Spinner) findViewById(R.id.Spin_continuemedicines);
        Spin_wheretake = (Spinner) findViewById(R.id.Spin_wheretake);
        spin_referral1 = (Spinner) findViewById(R.id.spin_referral1);

        spin_Village = (Spinner) findViewById(R.id.spin_Village);

        tbl_green = (TableRow) findViewById(R.id.tbl_green);
        tblQ3_1 = (TableRow) findViewById(R.id.tblQ3_1);
        tblQ3_2 = (TableRow) findViewById(R.id.tblQ3_2);
        tblQ3_3 = (TableRow) findViewById(R.id.tblQ3_3);
        tblQ4_1 = (TableRow) findViewById(R.id.tblQ4_1);
        tblQ4_2 = (TableRow) findViewById(R.id.tblQ4_2);
        tblQ5_1 = (TableRow) findViewById(R.id.tblQ5_1);
        tblQ5_2 = (TableRow) findViewById(R.id.tblQ5_2);

        tblQ6_1 = (TableRow) findViewById(R.id.tblQ6_1);
        tblQ6_2 = (TableRow) findViewById(R.id.tblQ6_2);
        tblQ7_1 = (TableRow) findViewById(R.id.tblQ7_1);
        tblQ7_2 = (TableRow) findViewById(R.id.tblQ7_2);

        tblQ9_1 = (TableRow) findViewById(R.id.tblQ9_1);

        tblQ10_1 = (TableRow) findViewById(R.id.tblQ10_1);
        tblQ10_2 = (TableRow) findViewById(R.id.tblQ10_2);
        tbl_SpinOther = (TableRow) findViewById(R.id.tbl_SpinOther);
        tblOther1 = (TableRow) findViewById(R.id.tblOther1);
        tblOther2 = (TableRow) findViewById(R.id.tblOther2);
        tblOther3 = (TableRow) findViewById(R.id.tblOther3);
        tblOther4 = (TableRow) findViewById(R.id.tblOther4);
        tblOther5 = (TableRow) findViewById(R.id.tblOther5);
        tbl_wheretakemadicine = (TableRow) findViewById(R.id.tbl_wheretakemadicine);
        tbl_Spinyesmedicinesother = (TableRow) findViewById(R.id.tbl_Spinyesmedicinesother);
        tblQdaymonth = (TableRow) findViewById(R.id.tblQdaymonth);
        tbldaymonth = (TableRow) findViewById(R.id.tbldaymonth);
        tbl_continuemedicines = (TableRow) findViewById(R.id.tbl_continuemedicines);


        btnSave = (Button) findViewById(R.id.btnSave);

        tblQ6_1.setVisibility(View.GONE);
        tblQ6_2.setVisibility(View.GONE);
        tblQ9_1.setVisibility(View.GONE);

        tblQ10_1.setVisibility(View.GONE);
        tblQ10_2.setVisibility(View.GONE);
        fillCommonRecord(spin_counsellingtips, 7, global.getLanguage());
        fillCommonRecord(Spin_MedicineNCD, 7, global.getLanguage());
        fillCommonRecord(Spin_continuemedicines, 7, global.getLanguage());
        fillCommonRecord(Spin_Medicinetaken, 43, global.getLanguage());
        fillCommonRecord(Spin_wheretake, 44, global.getLanguage());
        fillCommonRecord(spin_referral1, 7, global.getLanguage());

        tv_Date.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tv_Date);
            }
        });
        Spin_Medicinetaken
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        // TODO Auto-generated method stub
                        if (pos == 7) {
                            tbl_SpinOther.setVisibility(view.VISIBLE);

                        } else {
                            tbl_SpinOther.setVisibility(view.GONE);
                            et_SpinOther.setText("");


                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        fillVillageName(global.getLanguage());
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int icheck = 0;
                icheck = sCheckValidation();
                if (icheck == 1) {
                    saveFUPDetail();
                } else {
                    showAlert(icheck);
                }

            }
        });


        et_bsl.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 500) {
                        et_bsl.setText("");
                        CustomAlert(
                                getResources().getString(R.string.bloodglucose),
                                0);

                    }
                }

            }
        });
        et_Systolic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());

                    if (count > 200) {
                        et_Systolic.setText("");
                        CustomAlert(
                                getResources().getString(R.string.Systolic), 0);
                    }
                }

            }
        });
        et_Diastolic.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 150) {
                        et_Diastolic.setText("");
                        CustomAlert(getResources()
                                .getString(R.string.Diastolic), 0);

                    }
                }

            }
        });
        et_day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.length() > 0) {
                    count = Integer.valueOf(s.toString());
                    if (count > 90) {
                        et_day.setText("");
                        CustomAlert(getResources()
                                .getString(R.string.Daymsg), 0);

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        chk_Private_doctor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!chk_Private_Clinic.isChecked()) {
                    if (chk_Private_doctor.isChecked()) {
                        tblQ6_1.setVisibility(View.VISIBLE);
                        tblQ6_2.setVisibility(View.VISIBLE);
                        tblQ4_1.setVisibility(View.GONE);
                        tblQ4_2.setVisibility(View.GONE);
                        tblQ5_1.setVisibility(View.GONE);
                        tblQ5_2.setVisibility(View.GONE);
                        tblQdaymonth.setVisibility(View.GONE);
                        tbldaymonth.setVisibility(View.GONE);
                        // tblOther3.setBackgroundColor(R.color.Red);

                    } else {
                        tblQ6_1.setVisibility(View.GONE);
                        tblQ6_2.setVisibility(View.GONE);
                        tblQ4_1.setVisibility(View.VISIBLE);
                        tblQ4_2.setVisibility(View.VISIBLE);
                        tblQ5_1.setVisibility(View.VISIBLE);
                        tblQ5_2.setVisibility(View.VISIBLE);
                        tblQdaymonth.setVisibility(View.VISIBLE);
                        tbldaymonth.setVisibility(View.VISIBLE);
                        chk_Doctorsnotavail.setChecked(false);
                        chk_Medicinesnotavail.setChecked(false);
                        chk_Doctorsnotbehave.setChecked(false);
                        chk_testnotavail.setChecked(false);
                        chk_Distanceproblem.setChecked(false);
                        chk_Notthoughfacilit.setChecked(false);
                        chk_Others3.setChecked(false);
                        tblOther3.setVisibility(View.GONE);
                        et_OtherGovernment_Facility_diagnosis.setText("");

                    }
                }
            }
        });
        chk_Private_Clinic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!chk_Private_doctor.isChecked()) {
                    if (chk_Private_Clinic.isChecked()) {
                        tblQ6_1.setVisibility(View.VISIBLE);
                        tblQ6_2.setVisibility(View.VISIBLE);
                        tblQ4_1.setVisibility(View.GONE);
                        tblQ4_2.setVisibility(View.GONE);
                        tblQ5_1.setVisibility(View.GONE);
                        tblQ5_2.setVisibility(View.GONE);
                        tblQdaymonth.setVisibility(View.GONE);
                        tbldaymonth.setVisibility(View.GONE);

                    } else {
                        tblQ6_1.setVisibility(View.GONE);
                        tblQ6_2.setVisibility(View.GONE);
                        tblQ4_1.setVisibility(View.VISIBLE);
                        tblQ4_2.setVisibility(View.VISIBLE);
                        tblQ5_1.setVisibility(View.VISIBLE);
                        tblQ5_2.setVisibility(View.VISIBLE);
                        tblQdaymonth.setVisibility(View.VISIBLE);
                        tbldaymonth.setVisibility(View.VISIBLE);
                        chk_Doctorsnotavail.setChecked(false);
                        chk_Medicinesnotavail.setChecked(false);
                        chk_Doctorsnotbehave.setChecked(false);
                        chk_testnotavail.setChecked(false);
                        chk_Distanceproblem.setChecked(false);
                        chk_Notthoughfacilit.setChecked(false);
                        chk_Others3.setChecked(false);
                        tblOther3.setVisibility(View.GONE);
                        et_OtherGovernment_Facility_diagnosis.setText("");

                    }
                }
            }
        });
        chk_Did.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_Did.isChecked()) {
                    tblQ7_1.setVisibility(View.VISIBLE);
                    tblQ7_2.setVisibility(View.VISIBLE);
                    tblQ3_1.setVisibility(View.GONE);
                    tbl_green.setVisibility(View.GONE);
                    tblQ3_2.setVisibility(View.GONE);
                    tblQ3_3.setVisibility(View.GONE);
                    tblQ3_2.setVisibility(View.GONE);
                    tblQ4_1.setVisibility(View.GONE);
                    tblQ4_2.setVisibility(View.GONE);
                    tblQ5_1.setVisibility(View.GONE);
                    tblQ5_2.setVisibility(View.GONE);
                    tblQ6_1.setVisibility(View.GONE);
                    tblQ6_2.setVisibility(View.GONE);
                    tblOther1.setVisibility(View.GONE);
                    tblOther2.setVisibility(View.GONE);
                    tblOther3.setVisibility(View.GONE);
                    tblQdaymonth.setVisibility(View.GONE);
                    tbldaymonth.setVisibility(View.GONE);
                    tbl_continuemedicines.setVisibility(View.GONE);
                    chk_Chc.setChecked(false);
                    chk_phc.setChecked(false);
                    chk_District_hospital.setChecked(false);
                    chk_Tertiary.setChecked(false);
                    chk_Private_doctor.setChecked(false);
                    chk_Private_Clinic.setChecked(false);
                    chk_Chc.setEnabled(false);
                    chk_phc.setEnabled(false);
                    chk_District_hospital.setEnabled(false);
                    chk_Tertiary.setEnabled(false);
                    chk_Private_doctor.setEnabled(false);
                    chk_Private_Clinic.setEnabled(false);
                    et_bsl.setText("");
                    et_Diastolic.setText("");
                    et_Systolic.setText("");
                    et_Othergovernment_facility.setText("");
                    et_Otherproblem_faced.setText("");
                    et_OtherGovernment_Facility_diagnosis.setText("");

                    chk_doctor.setChecked(false);
                    chk_Medicines.setChecked(false);
                    chk_Bslcheckup.setChecked(false);
                    chk_BP_HTN.setChecked(false);
                    chk_HBA1C.setChecked(false);
                    chk_Consultation.setChecked(false);
                    chk_lipid_Profile.setChecked(false);
                    chk_Other1.setChecked(false);
                    chk_Doctorsnotavail.setChecked(false);
                    chk_Medicinesnotavail.setChecked(false);
                    chk_Doctorsnotbehave.setChecked(false);
                    chk_testnotavail.setChecked(false);
                    chk_Distanceproblem.setChecked(false);
                    chk_Notthoughfacilit.setChecked(false);
                    chk_Others2.setChecked(false);
                    chk_Labtechnician.setChecked(false);
                    chk_Medicinewerenotavail.setChecked(false);
                    chk_Gluco_strips.setChecked(false);
                    chk_Doctorsdidbehavproperly.setChecked(false);
                    chk_Doctorsnotpresent.setChecked(false);
                    chk_Others3.setChecked(false);

                } else {
                    chk_Chc.setEnabled(true);
                    chk_phc.setEnabled(true);
                    chk_District_hospital.setEnabled(true);
                    chk_Tertiary.setEnabled(true);
                    chk_Private_doctor.setEnabled(true);
                    chk_Private_Clinic.setEnabled(true);
                    tblQ7_1.setVisibility(View.GONE);
                    tblQ7_2.setVisibility(View.GONE);
                    tblOther4.setVisibility(View.GONE);
                    tbl_green.setVisibility(View.VISIBLE);
                    et_Otherneithertogovernment.setText("");
                    tblQ3_1.setVisibility(View.VISIBLE);
                    tblQ3_2.setVisibility(View.VISIBLE);
                    tblQ3_3.setVisibility(View.VISIBLE);
                    tblQ3_2.setVisibility(View.VISIBLE);
                    tblQ4_1.setVisibility(View.VISIBLE);
                    tblQ4_2.setVisibility(View.VISIBLE);
                    tblQ5_1.setVisibility(View.VISIBLE);
                    tblQ5_2.setVisibility(View.VISIBLE);
                    tblQ6_1.setVisibility(View.VISIBLE);
                    tblQ6_2.setVisibility(View.VISIBLE);
                    tblQdaymonth.setVisibility(View.VISIBLE);
                    tbldaymonth.setVisibility(View.VISIBLE);
                    tbl_continuemedicines.setVisibility(View.VISIBLE);
                    chk_Financialconstraint.setChecked(false);
                    chk_notlikego.setChecked(false);
                    chk_quack.setChecked(false);
                    chk_chemist.setChecked(false);
                    chk_Relative.setChecked(false);
                    chk_Communicationdistance.setChecked(false);
                    Chk_Others4.setChecked(false);

                }

            }
        });
        chk_Other1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_Other1.isChecked()) {
                    tblOther1.setVisibility(View.VISIBLE);

                } else {
                    tblOther1.setVisibility(View.GONE);
                    et_Othergovernment_facility.setText("");
                }

            }
        });
        chk_Others2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_Others2.isChecked()) {
                    tblOther2.setVisibility(View.VISIBLE);

                } else {
                    tblOther2.setVisibility(View.GONE);
                    et_Otherproblem_faced.setText("");
                }
            }
        });
        chk_Others3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (chk_Others3.isChecked()) {
                    tblOther3.setVisibility(View.VISIBLE);

                } else {
                    tblOther3.setVisibility(View.GONE);
                    et_OtherGovernment_Facility_diagnosis.setText("");
                }
            }
        });
        Chk_Others4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Chk_Others4.isChecked()) {
                    tblOther4.setVisibility(View.VISIBLE);

                } else {
                    tblOther4.setVisibility(View.GONE);
                    et_Otherneithertogovernment.setText("");
                }

            }
        });
        chk_Other5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_Other5.isChecked()) {
                    tblOther5.setVisibility(View.VISIBLE);

                } else {
                    tblOther5.setVisibility(View.GONE);
                    et_Othertaking_medicines.setText("");
                }

            }
        });
        Spin_MedicineNCD
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        // TODO Auto-generated method stub
                        if (pos == 1) {
                            tblQ9_1.setVisibility(View.VISIBLE);
                            tblQ10_1.setVisibility(View.GONE);
                            tblQ10_2.setVisibility(View.GONE);
                            tblOther5.setVisibility(View.GONE);
                            chk1_Financialconstraint.setChecked(false);
                            chk_madicinesnotavilatgovrnment.setChecked(false);
                            chk_notmadicines.setChecked(false);
                            chk_Other5.setChecked(false);
                            et_Othertaking_medicines.setText("");

                        } else if (pos == 2) {

                            tblQ9_1.setVisibility(View.GONE);
                            tbl_SpinOther.setVisibility(View.GONE);
                            et_SpinOther.setText("");
                            tblQ10_1.setVisibility(View.VISIBLE);
                            tblQ10_2.setVisibility(View.VISIBLE);
                            Spin_Medicinetaken.setSelection(0);
                        } else {
                            tblQ9_1.setVisibility(View.GONE);
                            tblQ10_1.setVisibility(View.GONE);
                            tblQ10_2.setVisibility(View.GONE);
                            Spin_Medicinetaken.setSelection(0);
                            tblOther5.setVisibility(View.GONE);
                            chk1_Financialconstraint.setChecked(false);
                            chk_madicinesnotavilatgovrnment.setChecked(false);
                            chk_notmadicines.setChecked(false);
                            chk_Other5.setChecked(false);
                            et_Othertaking_medicines.setText("");
                            tbl_SpinOther.setVisibility(View.GONE);
                            et_SpinOther.setText("");
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        Spin_continuemedicines.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    tbl_wheretakemadicine.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    tbl_wheretakemadicine.setVisibility(View.GONE);
                    //tbl_wheretakemadicine.setSelection(0);
                } else {
                    tbl_wheretakemadicine.setVisibility(View.GONE);
                    //tbl_wheretakemadicine.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spin_wheretake.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    tbl_Spinyesmedicinesother.setVisibility(View.VISIBLE);
                } else {
                    tbl_Spinyesmedicinesother.setVisibility(View.GONE);
                    et_Spinyesmedicinesother.setText("");
                    //tbl_wheretakemadicine.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RadioGroupreferral
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        // TODO Auto-generated method stub
                        if (radio_HighRisk.isChecked()) {
                            radio_HighRisk.setBackgroundColor(getResources()
                                    .getColor(R.color.Yellow));
                            radio_Suspected.setBackgroundColor(getResources()
                                    .getColor(R.color.White));

                        } else if (radio_Suspected.isChecked()) {
                            radio_Suspected.setBackgroundColor(getResources()
                                    .getColor(R.color.Red));
                            radio_HighRisk.setBackgroundColor(getResources()
                                    .getColor(R.color.White));

                        }

                    }
                });
        et_name.setBackgroundColor(getResources()
                .getColor(R.color.lightbluencd));
        et_guardian.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Village.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));

        et_registrationno.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Village.setEnabled(false);
        ShowData();

    }

    public void ShowData() {
        String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
                + global.getGlobalHHSurveyGUID()
                + "'  and RelationID=1 and StatusID!=2";
        String guid = dataProvider.getRecord(sql);
        String sql1 = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
                + global.getGlobalHHSurveyGUID()
                + "'  and HHFamilyMemberGUID='"
                + global.getGlobalHHFamilyMemberGUID() + "' ";
        String name = dataProvider.getRecord(sql1);

        if (guid != null && guid.length() > 0) {
            et_guardian.setText(guid);
        }
        if (name != null && name.length() > 0) {
            et_name.setText(name);
        }
        String sql2 = "Select VillageID from Tbl_HHSurvey where HHSurveyGUID='"
                + global.getGlobalHHSurveyGUID()
                + "'  ";

        String village = dataProvider.getRecord(sql2);
        String sql3 = "Select RegistrationNo from tblncdscreening where NCDScreeningGUID='"
                + global.getNCDScreeningGUID() + "' limit 1";
        String registration = dataProvider.getRecord(sql3);
        if (registration != null && registration.length() > 0) {
            et_registrationno.setText(registration);
        }
        fillVillageName(global.getLanguage());
        int ivillage = 0;
        if (village != null && village.length() > 0) {
            ivillage = Integer.parseInt(village);
        }
        spin_Village.setSelection(returnVillageName(ivillage));
    }

    public void ShowDatePicker(final TextView txt) {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        // TODO Auto-generated method stub
        datepic = new Dialog(this, R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        // Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);

        datepic.show();

        datepic.getWindow().setLayout(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMaxDate((long) date.getTime());

        btncancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });

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
                txt.setText(sSellectedDate);
                datepic.dismiss();
            }
        });

    }

    public int saveReferal() {
        int ss = 0;
        if (radio_HighRisk.isChecked()) {
            ss = 1;
        } else if (radio_Suspected.isChecked()) {
            ss = 2;
        }
        return ss;
    }

    public void saveFUPDetail() {

        String RegistrationNo = "";
        String GuardianName = "";
        String RegistrationDate = "";
        int BlockCode = 0;
        int VillageCode = 0;
        String HHFamilyMemberGUID = "";
        String HHSurveyGUID = "";
        String NCDScreeningGUID = "";
        String NCDFollowupGUID = "";
        String Name = "";
        String NCDDiagnosis = "";
        String RBS = "";
        String BP = "";
        String GFRecieved = "";
        String GFother = "";
        String GFProblem = "";
        String GFProblemother = "";
        String GFDiagnosisReason = "";
        String GFDiagnosisReasonoth = "";
        String GForPFDiagnosisReason = "";
        String GForPFDiagnosisReasonoth = "";
        int NCDMedicine = 0;
        int NCDMedicineYes = 0;
        String NCDMedicineYesoth = "";
        String NCDMedicineNo = "";
        String NCDMedicineNooth = "";
        int CounsellingTips = 0;
        String Suggestion = "";

        int AshaID = 0;
        int Referal = 0;
        int ANMID = 0;
        String MedicineGiven = "";
        int MedicineContinue = 0;
        int MedicineWhere = 0;
        int ReferralYes = 0;
        String MedicineWhereOther = "";

        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
        }
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
        }
        RegistrationNo = et_registrationno.getText().toString();
        GuardianName = et_guardian.getText().toString();

        if (spin_Village.getSelectedItemPosition() > 0) {
            VillageCode = Village.get(
                    spin_Village.getSelectedItemPosition() - 1).getVillageID();
        }
        RegistrationDate = Validate.changeDateFormat(tv_Date.getText().toString());
        Name = et_name.getText().toString();
        NCDDiagnosis = savecheckboxNCDDiagnosis();
        GFRecieved = savecheckboxGFRecieved();
        GFother = et_Othergovernment_facility.getText().toString();
        GFProblem = SavecheckboxGFProblem();
        GFProblemother = et_Otherproblem_faced.getText().toString();
        GFDiagnosisReason = SavecheckboxGFDiagnosisReason();
        GFDiagnosisReasonoth = et_OtherGovernment_Facility_diagnosis.getText()
                .toString();
        GForPFDiagnosisReason = SavecheckboxGForPFDiagnosisReason();
        NCDMedicineYesoth = et_SpinOther.getText().toString();
        GForPFDiagnosisReasonoth = et_Otherneithertogovernment.getText()
                .toString();
        if (Spin_MedicineNCD.getSelectedItemPosition() > 0) {
            NCDMedicine = returnid(
                    Spin_MedicineNCD.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (Spin_Medicinetaken.getSelectedItemPosition() > 0) {
            NCDMedicineYes = returnid(
                    Spin_Medicinetaken.getSelectedItemPosition() - 1, 43,
                    global.getLanguage());
        }
        NCDMedicineNo = SavecheckboxTakingMadicine();
        NCDMedicineNooth = et_Othertaking_medicines.getText().toString();
        if (spin_counsellingtips.getSelectedItemPosition() > 0) {
            CounsellingTips = returnid(
                    spin_counsellingtips.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        Suggestion = et_AnySuggestion.getText().toString();
        Referal = saveReferal();
        RBS = et_bsl.getText().toString();

        if (et_Systolic.getText().length() > 0
                || et_Diastolic.getText().length() > 0) {
            BP = et_Systolic.getText().toString() + "/"
                    + et_Diastolic.getText().toString();
        }

        HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
        HHSurveyGUID = global.getGlobalHHSurveyGUID();
        NCDScreeningGUID = global.getNCDScreeningGUID();
        MedicineGiven = et_day.getText().toString() + "," + et_month.getText().toString();
        if (Spin_continuemedicines.getSelectedItemPosition() > 0) {
            MedicineContinue = returnid(
                    Spin_continuemedicines.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (Spin_wheretake.getSelectedItemPosition() > 0) {
            MedicineWhere = returnid(
                    Spin_wheretake.getSelectedItemPosition() - 1, 44,
                    global.getLanguage());
        }
        if (spin_referral1.getSelectedItemPosition() > 0) {
            ReferralYes = returnid(
                    spin_referral1.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        MedicineWhereOther = et_Spinyesmedicinesother.getText().toString();


        String flag = "";
        String sql = "select count(*) from tblncdfollowup where HHFamilyMemberGUID='"
                + HHFamilyMemberGUID
                + "' and CreatedOn='"
                + Validate.getcurrentdate() + "'";
        int aa = dataProvider.getMaxRecord(sql);
        if (aa == 0) {
            NCDFollowupGUID = Validate.random();
            flag = "I";
        } else {
            flag = "U";
        }

        int ireturn = 0;
        ireturn = dataProvider.InsertNCDFollowup(RegistrationNo, GuardianName,
                RegistrationDate, VillageCode, HHFamilyMemberGUID,
                HHSurveyGUID, NCDFollowupGUID, NCDScreeningGUID, Name,
                NCDDiagnosis, RBS, BP, GFRecieved, GFother, GFProblem,
                GFProblemother, GFDiagnosisReason, GFDiagnosisReasonoth,
                GForPFDiagnosisReason, GForPFDiagnosisReasonoth, NCDMedicine,
                NCDMedicineYes, NCDMedicineYesoth, NCDMedicineNo,
                NCDMedicineNooth, CounsellingTips, Suggestion, AshaID, ANMID,
                global.getUserID(), flag, Referal, MedicineGiven, MedicineContinue, MedicineWhere, ReferralYes, MedicineWhereOther);
        if (ireturn > 0) {
            addstatus(et_Systolic, et_bsl, et_Diastolic, HHFamilyMemberGUID);
            CustomAlert(getResources().getString(R.string.savesuccessfully), 1);

        } else {
            // CustomAlerts(getResources().getString(R.string.no));
            System.out.print("not saved");

        }

    }

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

    }

    public void addstatus(EditText et1, EditText et2, EditText et3,
                          String hhmemguid) {
        try {
            int count = 0, count1 = 0, count2 = 0;
            if (et1.length() > 0 && !et1.toString().equalsIgnoreCase(".")) {
                count =  Validate.returnIntegerValue(et1.getText().toString());
            }
            if (et3.length() > 0 && !et3.toString().equalsIgnoreCase(".")) {
                count2 = Validate.returnIntegerValue(et3.getText().toString());
            }
            if (et2.length() > 0 && !et2.toString().equalsIgnoreCase(".")) {
                count1 =  Validate.returnIntegerValue(et2.getText().toString());
            }
            if (count <= 120 && count1 <= 140 && count2 <= 90 && count >= 80
                    && count1 >= 50 && count2 >= 50) {
                String sql = "update tblncdscreening set Status=0,IsEdited=1 where HHFamilyMemberGUID='"
                        + hhmemguid + "'  and Status=1 ";
                dataProvider.executeSql(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CustomAlert(String msg, final int flag) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                if (flag == 1) {
                    Intent i = new Intent(NCDFollowUp.this, NCD_AA.class);
                    startActivity(i);
                }
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public int sCheckValidation() {
        try {

            if (chk_Did.isChecked()) {
                return 1;
            } else {
                if (et_bsl.getText().toString().length() == 0 || (et_bsl.getText().toString().length() > 0 && (Integer.valueOf(et_bsl.getText()
                        .toString()) < 50 || Integer.valueOf(et_bsl.getText().toString()) > 500))) {

                    return 7;
                }

                if (et_Systolic.getText().toString().length() == 0 || (et_Systolic.getText().toString().length() > 0 && (Integer.valueOf(et_Systolic.getText()
                        .toString()) < 90 || Integer.valueOf(et_Systolic.getText().toString()) > 200))) {

                    return 3;
                }
                if (et_Diastolic.getText().toString().length() == 0 || (et_Diastolic.getText().toString().length() > 0 && (Integer.valueOf(et_Diastolic.getText()
                        .toString()) < 50 || Integer.valueOf(et_Diastolic.getText().toString()) > 150))) {

                    return 4;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public void showAlert(int iCheck) {
        if (iCheck == 3) {
            CustomAlert(getResources().getString(R.string.Systolic), 0);
        } else if (iCheck == 4) {
            CustomAlert(getResources().getString(R.string.Diastolic), 0);
        } else if (iCheck == 7) {
            CustomAlert(getResources().getString(R.string.bloodglucose), 0);
        }
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
        spin_Village.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(NCDFollowUp.this, NCD_AA.class);
        finish();
        startActivity(i);
    }

    public String savecheckboxNCDDiagnosis() {
        String ss = "";
        if (chk_Chc.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_phc.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_District_hospital.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_Tertiary.isChecked()) {
            ss = ss + "4,";
        }
        if (chk_Private_doctor.isChecked()) {
            ss = ss + "5,";
        }
        if (chk_Private_Clinic.isChecked()) {
            ss = ss + "6,";
        }
        if (chk_Did.isChecked()) {
            ss = ss + "7";
        }

        return ss;
    }

    public String savecheckboxGFRecieved() {
        String ss = "";
        if (chk_doctor.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_Medicines.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_Bslcheckup.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_BP_HTN.isChecked()) {
            ss = ss + "4,";
        }
        if (chk_HBA1C.isChecked()) {
            ss = ss + "5,";
        }
        if (chk_lipid_Profile.isChecked()) {
            ss = ss + "6,";
        }
        if (chk_Consultation.isChecked()) {
            ss = ss + "8";
        }
        if (chk_Other1.isChecked()) {
            ss = ss + "7,";
        }

        return ss;
    }

    public String SavecheckboxGFProblem() {
        String ss = "";
        if (chk_Labtechnician.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_Medicinewerenotavail.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_Gluco_strips.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_Doctorsnotpresent.isChecked()) {
            ss = ss + "4,";
        }
        if (chk_Doctorsdidbehavproperly.isChecked()) {
            ss = ss + "5,";
        }
        if (chk_Others3.isChecked()) {
            ss = ss + "6";
        }

        return ss;
    }

    public String SavecheckboxGFDiagnosisReason() {
        String ss = "";
        if (chk_Doctorsnotavail.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_Medicinesnotavail.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_Doctorsnotbehave.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_testnotavail.isChecked()) {
            ss = ss + "4,";
        }
        if (chk_Distanceproblem.isChecked()) {
            ss = ss + "5,";
        }
        if (chk_Notthoughfacilit.isChecked()) {
            ss = ss + "6,";
        }
        if (chk_Others2.isChecked()) {
            ss = ss + "7";
        }

        return ss;
    }

    public String SavecheckboxGForPFDiagnosisReason() {
        String ss = "";
        if (chk_Financialconstraint.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_notlikego.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_quack.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_chemist.isChecked()) {
            ss = ss + "4,";
        }
        if (chk_Relative.isChecked()) {
            ss = ss + "5,";
        }
        if (chk_Communicationdistance.isChecked()) {
            ss = ss + "6,";
        }
        if (Chk_Others4.isChecked()) {
            ss = ss + "7";
        }

        return ss;
    }

    public String SavecheckboxTakingMadicine() {
        String ss = "";
        if (chk1_Financialconstraint.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_madicinesnotavilatgovrnment.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_notmadicines.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_Other5.isChecked()) {
            ss = ss + "4";
        }

        return ss;
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

    private void fillCommonRecord(Spinner spin, int iflag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iflag);

        String sValue[] = new String[Common.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < Common.size(); i++) {
            sValue[i + 1] = Common.get(i).getValue();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spin.setAdapter(adapter);
    }

}
