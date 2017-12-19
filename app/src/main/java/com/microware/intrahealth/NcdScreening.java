package com.microware.intrahealth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstBlock;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NcdScreening extends Activity {
    EditText et_registration_no, et_camp_no, et_name, et_address, et_age,
            edit_mobile, edit_height, edit_weight, et_Bmi, edit_Bloodglucose,
            et_Systolic, et_Diastolic, et_occupationother,
            et_anyothercomplication, et_other1, et_Othersuspected;
    CheckBox chk_Diabetes, chk_Hypertension, chk_Both, chk_Anyother, chk1,
            chk2, chk3, chk4, chk5, chk6;
    Spinner spin_Sex, spin_Referredto, spin_Referredby, spin_Village,
            spin_Occupation, spin_Block, Spin_Health_Condition,
            spin_Anyothecompli, spin_feet, spin_inch;
    String[] feet = {"4", "5", "6"};
    String[] inch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    TextView tv_Date, tv_lastscreeningdate, tv_totalscreening;
    DataProvider dataProvider;
    Global global;
    Dialog datepic;
    TableRow tbl_occupationother, tbl_Other1, tbl_Any_HealthIssue,
            tbl_anyothercomplication, tblOther2, tbl_Bmi;
    Button btnSave;
    LinearLayout ll_suspected;
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayList<MstBlock> Block = new ArrayList<MstBlock>();
    ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdscreening);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
        et_registration_no = (EditText) findViewById(R.id.et_registration_no);
        et_camp_no = (EditText) findViewById(R.id.et_camp_no);
        et_other1 = (EditText) findViewById(R.id.et_other1);
        et_occupationother = (EditText) findViewById(R.id.et_occupationother);
        et_anyothercomplication = (EditText) findViewById(R.id.et_anyothercomplication);
        tbl_occupationother = (TableRow) findViewById(R.id.tbl_occupationother);
        tbl_anyothercomplication = (TableRow) findViewById(R.id.tbl_anyothercomplication);
        tbl_Bmi = (TableRow) findViewById(R.id.tbl_Bmi);
        ll_suspected = (LinearLayout) findViewById(R.id.ll_suspected);
        et_anyothercomplication = (EditText) findViewById(R.id.et_anyothercomplication);
        spin_Anyothecompli = (Spinner) findViewById(R.id.spin_Anyothecompli);
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_age = (EditText) findViewById(R.id.et_age);
        edit_mobile = (EditText) findViewById(R.id.edit_mobile);
        spin_Occupation = (Spinner) findViewById(R.id.spin_Occupation);
        Spin_Health_Condition = (Spinner) findViewById(R.id.Spin_Health_Condition);
        spin_feet = (Spinner) findViewById(R.id.spin_feet);
        spin_inch = (Spinner) findViewById(R.id.spin_inch);
        edit_height = (EditText) findViewById(R.id.edit_height);
        edit_weight = (EditText) findViewById(R.id.edit_weight);
        et_Bmi = (EditText) findViewById(R.id.et_Bmi);
        edit_Bloodglucose = (EditText) findViewById(R.id.edit_Bloodglucose);
        et_Systolic = (EditText) findViewById(R.id.et_Systolic);
        et_Diastolic = (EditText) findViewById(R.id.et_Diastolic);
        chk_Diabetes = (CheckBox) findViewById(R.id.chk_Diabetes);
        chk_Hypertension = (CheckBox) findViewById(R.id.chk_Hypertension);
        chk_Both = (CheckBox) findViewById(R.id.chk_Both);
        chk_Anyother = (CheckBox) findViewById(R.id.chk_Anyother);
        spin_Sex = (Spinner) findViewById(R.id.spin_Sex);
        spin_Referredto = (Spinner) findViewById(R.id.spin_Referredto);
        spin_Referredby = (Spinner) findViewById(R.id.spin_Referredby);
        spin_Village = (Spinner) findViewById(R.id.spin_Village);
        spin_Block = (Spinner) findViewById(R.id.spin_Block);
        tv_Date = (TextView) findViewById(R.id.tv_Date);
        tv_totalscreening = (TextView) findViewById(R.id.tv_totalscreening);
        tv_lastscreeningdate = (TextView) findViewById(R.id.tv_lastscreeningdate);
        tbl_Other1 = (TableRow) findViewById(R.id.tbl_Other1);
        tbl_Any_HealthIssue = (TableRow) findViewById(R.id.tbl_Any_HealthIssue);
        chk1 = (CheckBox) findViewById(R.id.chk1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);
        chk5 = (CheckBox) findViewById(R.id.chk5);
        chk6 = (CheckBox) findViewById(R.id.chk6);
        btnSave = (Button) findViewById(R.id.btnSave);
        et_Othersuspected = (EditText) findViewById(R.id.et_Othersuspected);
        tblOther2 = (TableRow) findViewById(R.id.tblOther2);
        tv_Date.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tv_Date);
            }
        });
        chk_Both.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (chk_Both.isChecked()) {
                    chk_Diabetes.setChecked(true);
                    chk_Hypertension.setChecked(true);
                } else {
                    chk_Diabetes.setChecked(false);
                    chk_Hypertension.setChecked(false);
                }

            }
        });
        chk_Both.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (chk_Both.isChecked()) {
                    chk_Diabetes.setChecked(true);
                    chk_Hypertension.setChecked(true);
                } else {
                    chk_Diabetes.setChecked(false);
                    chk_Hypertension.setChecked(false);
                }

            }
        });
        chk_Hypertension.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (chk_Hypertension.isChecked() && chk_Diabetes.isChecked()) {
                    chk_Both.setChecked(true);

                } else {
                    chk_Both.setChecked(false);

                }

            }
        });
        chk_Diabetes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (chk_Hypertension.isChecked() && chk_Diabetes.isChecked()) {
                    chk_Both.setChecked(true);

                } else {
                    chk_Both.setChecked(false);

                }

            }
        });

        fillVillageName(global.getLanguage());
        fillBlockName(global.getLanguage());
        fillCommonRecord(spin_Sex, 4, global.getLanguage());
        fillCommonRecord(spin_Occupation, 38, global.getLanguage());
        fillCommonRecord(spin_Referredby, 42, global.getLanguage());
        fillCommonRecord(spin_Referredto, 41, global.getLanguage());
        fillCommonRecord(Spin_Health_Condition, 36, global.getLanguage());
        fillCommonRecord(spin_Anyothecompli, 7, global.getLanguage());
        spin_Occupation.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 5) {
                    tbl_occupationother.setVisibility(view.VISIBLE);
                } else {
                    tbl_occupationother.setVisibility(view.GONE);
                    et_occupationother.setText("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        Spin_Health_Condition
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 2) {
                            tbl_Any_HealthIssue.setVisibility(view.VISIBLE);

                        } else {
                            tbl_Any_HealthIssue.setVisibility(view.GONE);
                            chk1.setChecked(false);
                            chk2.setChecked(false);
                            chk3.setChecked(false);
                            chk4.setChecked(false);
                            chk5.setChecked(false);
                            chk6.setChecked(false);
                            tbl_Other1.setVisibility(view.GONE);
                            et_other1.setText("");

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        Spin_Health_Condition.setEnabled(false);
        spin_Anyothecompli
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        // TODO Auto-generated method stub
                        if (pos == 1) {
                            tbl_anyothercomplication
                                    .setVisibility(View.VISIBLE);

                        } else if (pos == 2) {

                            tbl_anyothercomplication.setVisibility(View.GONE);

                            et_anyothercomplication.setText("");

                        } else {
                            tbl_anyothercomplication.setVisibility(View.GONE);

                            et_anyothercomplication.setText("");
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }

                });
        edit_weight.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                calculateBMI();

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

                    Float weight = Float.valueOf(s.toString());
                    if (weight > 150) {
                        edit_weight.setText("");
                        et_Bmi.setText("" + 0);
                        CustomAlert(
                                getResources().getString(R.string.enterweight),
                                0);
                    }

                }

            }
        });
        chk6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk6.isChecked()) {
                    tbl_Other1.setVisibility(View.VISIBLE);
                } else {
                    tbl_Other1.setVisibility(View.GONE);
                    et_other1.setText("");
                }
            }
        });
        edit_height.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                calculateBMI();

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

                    Float height = Float.valueOf(s.toString());
                    if (height > 200) {
                        edit_height.setText("");
                        et_Bmi.setText("" + 0);
                        CustomAlert(
                                getResources().getString(R.string.enterheight),
                                0);
                    }

                }
            }
        });
//        edit_height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    calculateheightft(edit_height);
//                }
//            }
//        });
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
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());

                    if (count > 200) {
                        et_Systolic.setText("");
                        CustomAlert(
                                getResources().getString(R.string.Systolic), 0);
                    }
                    visiblesuspected(et_Systolic, edit_Bloodglucose, et_Diastolic);
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
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 150) {
                        et_Diastolic.setText("");
                        CustomAlert(getResources()
                                .getString(R.string.Diastolic), 0);

                    }
                    visiblesuspected(et_Systolic, edit_Bloodglucose, et_Diastolic);
                }

            }
        });
        edit_Bloodglucose.addTextChangedListener(new TextWatcher() {

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
                        edit_Bloodglucose.setText("");
                        CustomAlert(
                                getResources().getString(R.string.bloodglucose),
                                0);

                    }
                    visiblesuspected(et_Systolic, edit_Bloodglucose, et_Diastolic);
                }

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                int icheck = 0;

                icheck = sCheckValidation();
                if (icheck == 1) {
                    saveNCDDetail();

                } else {
                    showAlert(icheck);
                }
            }
        });
        chk_Anyother.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chk_Anyother.isChecked()) {
                    tblOther2.setVisibility(View.VISIBLE);

                } else {
                    tblOther2.setVisibility(View.GONE);
                    et_Othersuspected.setText("");
                }

            }
        });
        tv_lastscreeningdate.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        tv_totalscreening.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        et_name.setBackgroundColor(getResources()
                .getColor(R.color.lightbluencd));
        et_age.setBackgroundColor(getResources().getColor(R.color.lightbluencd));
        edit_mobile.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Occupation.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Sex.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Village.setBackgroundColor(getResources().getColor(
                R.color.lightbluencd));
        spin_Occupation.setEnabled(false);
        spin_Sex.setEnabled(false);
        showmemberdata();
        fillspinnerheight();
        spin_feet.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                edit_height.setText(String.valueOf(calculateheight()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spin_inch.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                edit_height.setText(String.valueOf(calculateheight()));
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spin_Village.setEnabled(false);

    }

    public void fillspinnerheight() {
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, feet);
        spin_feet.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, inch);
        spin_inch.setAdapter(adapter);
    }

    public double calculateheight() {
        double height = 0;
        try {
            int feet = Integer.valueOf(spin_feet.getSelectedItem().toString());
            int inch = Integer.valueOf(spin_inch.getSelectedItem().toString());
            height = ((feet * 12) + inch) * 2.54;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public double calculateheightft(EditText et) {
        int height = 0, ftvalue = 0, invalue = 0;
        try {
            if (et.getText() != null
                    && et.getText().toString().length() > 0
                    && !et.getText().toString().equalsIgnoreCase(".")) {
                double value = Double.valueOf(et.getText().toString());
                height = (int) (value / 2.54);
            }
            int ft = height / 12;
            int in = height % 12;

            for (int i = 0; i < feet.length; i++) {

                if (ft == Integer.valueOf(feet[i])) {
                    ftvalue = i;
                }
            }
            for (int i = 0; i < inch.length; i++) {

                if (in == Integer.valueOf(inch[i])) {
                    invalue = i;
                }
            }

            spin_feet.setSelection(ftvalue);
            spin_inch.setSelection(invalue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
    }

    public void calculateBMI() {
        if (edit_height.getText() != null
                && edit_height.getText().toString().length() > 0
                && edit_weight.getText() != null
                && edit_weight.getText().toString().length() > 0
                && !edit_weight.getText().toString().equalsIgnoreCase(".") && !edit_height.getText().toString().equalsIgnoreCase(".")) {

            Float height = Float.valueOf(edit_height.getText().toString());
            Float weight = Float.valueOf(edit_weight.getText().toString());
            Float newHeight = height * height;
            Float Bmi = (weight / newHeight) * 10000;
            DecimalFormat Form = new DecimalFormat("#.##");
            Bmi = Float.valueOf(Form.format(Bmi));
            if (Bmi > 18.5 && Bmi <= 25) {
                tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.Green));
                et_Bmi.setBackgroundColor(getResources().getColor(R.color.Green));
            } else {
                tbl_Bmi.setBackgroundColor(getResources().getColor(R.color.White));
                et_Bmi.setBackgroundResource(R.drawable.textbox);
            }
            et_Bmi.setText(String.valueOf(Bmi));
        } else {
            et_Bmi.setText("" + 0);
        }

    }

    public void showmemberdata() {

        try {
            HHFamilyMember = dataProvider.getHHFamilymemberData(
                    global.getGlobalHHSurveyGUID(),
                    global.getGlobalHHFamilyMemberGUID(), 2);
            if (HHFamilyMember != null && HHFamilyMember.size() > 0) {
                et_name.setText(String.valueOf(HHFamilyMember.get(0)
                        .getFamilyMemberName()));
                spin_Sex.setSelection(returnPos(HHFamilyMember.get(0)
                        .getGenderID(), 4));
                if (HHFamilyMember.get(0).getDOBAvailable() == 1) {
                    et_age.setText(""
                            + Validate.CountAge(HHFamilyMember.get(0)
                            .getDateOfBirth()));
                } else if (HHFamilyMember.get(0).getDOBAvailable() == 2) {
                    // int gap =
                    // Validate.CalculateYM(data.get(0).getAgeAsOnYear());
                    et_age.setText(""
                            + (Validate.CalculateYM(HHFamilyMember.get(0)
                            .getAgeAsOnYear()) + HHFamilyMember.get(0)
                            .getAprilAgeYear()));
                }
                spin_Occupation.setSelection(returnPos(HHFamilyMember.get(0)
                        .getOccupation(), 38));

                edit_mobile.setText(HHFamilyMember.get(0).getPhone_No());
                Spin_Health_Condition.setSelection(returnPos(HHFamilyMember
                        .get(0).getHealth_Condition(), 36));
                if (returnPos(HHFamilyMember.get(0).getHealth_Condition(), 36) == 2) {
                    tbl_Any_HealthIssue.setVisibility(View.VISIBLE);

                }
                String[] arr1 = HHFamilyMember.get(0).getAny_HealthIssue()
                        .split(",");

                for (int i = 0; i < arr1.length; i++) {
                    for (int j = 0; j < arr1.length; j++) {

                        if (arr1[i].equalsIgnoreCase("1")) {
                            chk1.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("2")) {
                            chk2.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("3")) {
                            chk3.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("4")) {
                            chk4.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("5")) {
                            chk5.setChecked(true);
                            break;
                        } else if (arr1[i].equalsIgnoreCase("6")) {
                            chk6.setChecked(true);
                            tbl_Other1.setVisibility(View.VISIBLE);
                            et_other1.setText(String.valueOf(HHFamilyMember
                                    .get(0).getAny_HealthIssue_Other()));
                            break;
                        }

                    }
                }

            }
            String sql = "Select count(*) from tblncdscreening where HHFamilyMemberGUID='"
                    + global.getGlobalHHFamilyMemberGUID() + "' ";
            int aa = dataProvider.getMaxRecord(sql);
            tv_totalscreening.setText("" + aa);
            String sql1 = "Select RegistrationDate from tblncdscreening where HHFamilyMemberGUID='"
                    + global.getGlobalHHFamilyMemberGUID()
                    + "'  order by CreatedOn DESC";
            String guid = dataProvider.getRecord(sql1);
            tv_lastscreeningdate.setText(Validate.changeDateFormat(guid));
            String sql2 = "Select VillageID from Tbl_HHSurvey where HHSurveyGUID='"
                    + global.getGlobalHHSurveyGUID()
                    + "'  ";

            String village = dataProvider.getRecord(sql2);
            int ivillage = 0;
            if (village != null && village.length() > 0) {
                ivillage = Integer.parseInt(village);
            }
            spin_Village.setSelection(returnVillageName(ivillage));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int sCheckValidation() {
        try {
            if (et_name.getText().toString().length() == 0) {
                return 8;

            }
            if (et_name.getText().toString().length() == 0) {
                return 9;

            }
            if (spin_Sex.getSelectedItemPosition() == 0) {
                return 10;
            }
            if (edit_height.getText().toString() != null
                    && edit_height.getText().toString().length() > 0) {
                int height = Integer.parseInt(edit_height.getText().toString());
                if (height < 121) {
                    return 2;
                } else if (height > 200) {
                    return 2;
                }

            }
            if (edit_weight.getText().toString() != null
                    && edit_weight.getText().toString().length() > 0
                    && !edit_weight.getText().toString().equalsIgnoreCase(".")) {
                float weight = Float.parseFloat(edit_weight.getText()
                        .toString());
                if (weight < 30) {
                    return 5;
                } else if (weight > 150) {
                    return 5;
                }

            }
//
            if (edit_Bloodglucose.getText().toString().length() == 0 || (edit_Bloodglucose.getText().toString().length() > 0 && (Integer.valueOf(edit_Bloodglucose.getText()
                    .toString()) < 50 || Integer.valueOf(edit_Bloodglucose.getText().toString()) > 500))) {

                return 7;
            }

            if (et_Systolic.getText().toString().length() == 0 || (et_Systolic.getText().toString().length() > 0 && (et_Systolic.getText().toString() != null && Integer.valueOf(et_Systolic.getText()
                    .toString()) < 90 || Integer.valueOf(et_Systolic.getText().toString()) > 200))) {

                return 3;
            }
            if (et_Diastolic.getText().toString().length() == 0 || (et_Diastolic.getText().toString().length() > 0 && (Integer.valueOf(et_Diastolic.getText()
                    .toString()) < 50 || Integer.valueOf(et_Diastolic.getText().toString()) > 150))) {

                return 4;
            }
            if (ll_suspected.getVisibility() == View.VISIBLE) {
                if (chk_Diabetes.isChecked() || chk_Hypertension.isChecked()
                        || chk_Both.isChecked() || chk_Anyother.isChecked()) {

                } else {
                    return 6;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public void showAlert(int iCheck) {
        if (iCheck == 5) {
            CustomAlert(getResources().getString(R.string.enterweight), 0);
        } else if (iCheck == 2) {
            CustomAlert(getResources().getString(R.string.enterheight), 0);
        } else if (iCheck == 3) {
            CustomAlert(getResources().getString(R.string.Systolic), 0);
        } else if (iCheck == 4) {
            CustomAlert(getResources().getString(R.string.Diastolic), 0);
        } else if (iCheck == 6) {
            CustomAlert(getResources().getString(R.string.pleaseselectillness),
                    0);
        } else if (iCheck == 7) {
            CustomAlert(getResources().getString(R.string.bloodglucose), 0);
        } else if (iCheck == 8) {
            CustomAlert(getResources().getString(R.string.enterfmname), 0);
        } else if (iCheck == 9) {
            CustomAlert(getResources().getString(R.string.enterage), 0);
        } else if (iCheck == 10) {
            CustomAlert(getResources().getString(R.string.selectgender), 0);
        }
    }

    public void saveNCDDetail() {
        try {
            String RegistrationNo = "";
            String CampNo = "";
            String RegistrationDate = "";
            int BlockCode = 0;
            int VillageCode = 0;
            String HHFamilyMemberGUID = "";
            String HHSurveyGUID = "";
            String NCDScreeningGUID = "";
            String Name = "";
            int Age = 0;
            int Sex = 0;
            String Address = "";
            String Mobileno = "";
            int Occupation = 0;
            String Occupationother = "";
            String Anypastillness = "";
            int Height = 0;
            String Weight = "";
            String BMI = "";
            String RBS = "";
            String BP = "";
            String Othercomplication = "";
            String SuspectedFor = "";
            int Referredto = 0;
            int ReferredBy = 0;
            int Status = 0;

            int ANMID = 0;
            int AshaID = 0;
            int AnyOthcomplicationYes = 0;
            String SuspectedAnyOther = "";

            if (global.getsGlobalANMCODE() != null
                    && global.getsGlobalANMCODE().length() > 0) {
                ANMID = Integer.valueOf(global.getsGlobalANMCODE());
            }
            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                AshaID = Integer.valueOf(global.getsGlobalAshaCode());
            }
            if (!et_age.getText().toString().equalsIgnoreCase("null")
                    && et_age.getText().toString().length() > 0) {
                Age = Integer.valueOf(et_age.getText().toString());
            }
            if (!edit_height.getText().toString().equalsIgnoreCase("null")
                    && edit_height.getText().toString().length() > 0) {
                Height = Integer.valueOf(edit_height.getText().toString());
            }
            if (!edit_mobile.getText().toString().equalsIgnoreCase("null")
                    && edit_mobile.getText().toString().length() > 0) {
                Mobileno = edit_mobile.getText().toString();
            }
            RegistrationDate = Validate.changeDateFormat(tv_Date.getText()
                    .toString());
            if (spin_Village.getSelectedItemPosition() > 0) {
                VillageCode = Village.get(
                        spin_Village.getSelectedItemPosition() - 1)
                        .getVillageID();
            }
            if (spin_Block.getSelectedItemPosition() > 0) {
                BlockCode = Block.get(spin_Block.getSelectedItemPosition() - 1)
                        .getBlockID();
            }
            if (spin_Occupation.getSelectedItemPosition() > 0) {
                Occupation = returnid(
                        spin_Occupation.getSelectedItemPosition() - 1, 38,
                        global.getLanguage());
            }
            if (spin_Referredby.getSelectedItemPosition() > 0) {
                ReferredBy = returnid(
                        spin_Referredby.getSelectedItemPosition() - 1, 42,
                        global.getLanguage());
            }
            if (spin_Referredto.getSelectedItemPosition() > 0) {
                Referredto = returnid(
                        spin_Referredto.getSelectedItemPosition() - 1, 41,
                        global.getLanguage());
            }
            if (spin_Sex.getSelectedItemPosition() > 0) {
                Sex = returnid(spin_Sex.getSelectedItemPosition() - 1, 4,
                        global.getLanguage());
            }
            RegistrationNo = et_registration_no.getText().toString();
            CampNo = et_camp_no.getText().toString();

            Name = et_name.getText().toString();

            Address = et_address.getText().toString();

            Occupationother = et_occupationother.getText().toString();
            // Anypastillness = et_illness.getText().toString();

            Weight = edit_weight.getText().toString();
            BMI = et_Bmi.getText().toString();
            RBS = edit_Bloodglucose.getText().toString();
            if (et_Systolic.getText().length() > 0
                    || et_Diastolic.getText().length() > 0) {
                BP = et_Systolic.getText().toString() + "/"
                        + et_Diastolic.getText().toString();
            }
            Othercomplication = et_anyothercomplication.getText().toString();
            SuspectedFor = savecheckbox();
            SuspectedAnyOther = et_Othersuspected.getText().toString();

            Status = 0;
            if (spin_Anyothecompli.getSelectedItemPosition() > 0) {
                AnyOthcomplicationYes = returnid(
                        spin_Anyothecompli.getSelectedItemPosition() - 1, 4,
                        global.getLanguage());
            }
            HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
            HHSurveyGUID = global.getGlobalHHSurveyGUID();
            if (ll_suspected.getVisibility() == View.VISIBLE) {
                Status = 1;
            }
            String flag = "";
            String sql = "select count(*) from tblncdscreening where HHFamilyMemberGUID='"
                    + HHFamilyMemberGUID
                    + "' and CreatedOn='"
                    + Validate.getcurrentdate() + "'";
            int aa = dataProvider.getMaxRecord(sql);
            if (aa == 0) {
                NCDScreeningGUID = Validate.random();
                flag = "I";
            } else {
                flag = "U";
            }

            int ireturn = 0;
            ireturn = dataProvider.InsertNCDetails(RegistrationNo, CampNo,
                    RegistrationDate, BlockCode, VillageCode,
                    HHFamilyMemberGUID, HHSurveyGUID, NCDScreeningGUID, Name,
                    Age, Sex, Address, Mobileno, Occupation, Occupationother,
                    Anypastillness, Height, Weight, BMI, RBS, BP,
                    Othercomplication, SuspectedFor, Referredto, ReferredBy,
                    Status, AshaID, ANMID, global.getUserID(), flag,
                    AnyOthcomplicationYes, SuspectedAnyOther);
            if (ireturn > 0) {
                CustomAlert(
                        getResources().getString(R.string.savesuccessfully), 1);

            } else {
                // CustomAlerts(getResources().getString(R.string.no));
                System.out.print("not saved");

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
                    Intent i = new Intent(NcdScreening.this, NCD_AA.class);
                    startActivity(i);
                }
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public String savecheckbox() {
        String ss = "";
        if (chk_Diabetes.isChecked()) {
            ss = ss + "1,";
        }
        if (chk_Hypertension.isChecked()) {
            ss = ss + "2,";
        }
        if (chk_Both.isChecked()) {
            ss = ss + "3,";
        }
        if (chk_Anyother.isChecked()) {
            ss = ss + "4";
        }

        return ss;
    }

    public void visiblesuspected(EditText et1, EditText et2, EditText et3) {
        try {
            int count = 0, count1 = 0, count2 = 0;
            if (et1.length() > 0 && !et1.toString().equalsIgnoreCase(".")) {
                count = Integer.valueOf(et1.getText().toString());
            }
            if (et3.length() > 0 && !et3.toString().equalsIgnoreCase(".")) {
                count2 = Integer.valueOf(et3.getText().toString());
            }
            if (et2.length() > 0 && !et2.toString().equalsIgnoreCase(".")) {
                count1 = Integer.valueOf(et2.getText().toString());
            }
            if (count > 120 || count2 > 90 || count1 > 140) {
                ll_suspected.setVisibility(View.VISIBLE);
            } else {
                ll_suspected.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

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

    private void fillBlockName(int Language) {
        Block = dataProvider.getMstBlockName(Language);

        String sValue[] = new String[Block.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < Block.size(); i++) {
            sValue[i + 1] = Block.get(i).getBlockName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spin_Block.setAdapter(adapter);
    }

    public int returnBlockName(int Villageid) {
        int iVillage = 0;
        if (Villageid > 0) {
            for (int i = 0; i < Block.size(); i++) {
                if (Villageid == Block.get(i).getBlockID()) {
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

        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

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

    private int returnPos(int ID, int iFlag) {
        // TODO Auto-generated method stub
        int pos = 0;
        Common = dataProvider.getCommonRecord(1, iFlag);
        if (Common != null && Common.size() > 0) {
            for (int i = 0; i < Common.size(); i++) {
                if (Common.get(i).getID() == ID) {
                    pos = i + 1;
                }
            }
        }
        return pos;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(NcdScreening.this, NCD_AA.class);
        finish();
        startActivity(i);

    }

}
