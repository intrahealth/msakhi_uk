package com.microware.intrahealth;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.adapter.MedicineList_Adapter;
import com.microware.intrahealth.adapter.NCD_CHC_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstBlock;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class NcdScreeningCHC extends Activity {
    EditText et_registration_no, et_camp_no, et_name, et_address, et_age,
            edit_mobile, edit_height, edit_weight, et_Bmi,
            et_Systolic, et_Diastolic, et_occupationother,
            et_anyothercomplication, et_other1,
            edit_FBS, edit_pptest, edit_HbA1C;
    CheckBox chk1,
            chk2, chk3, chk4, chk5, chk6;
    Spinner spin_Sex, spin_Village,
            spin_Occupation, spin_Block, Spin_Health_Condition,
            spin_Anyothecompli, spin_feet, spin_inch, spin_Urine_Test, spin_DiabeticStatus, spin_Diabetic;
    String[] feet = {"4", "5", "6"};
    String[] inch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    TextView tv_Date, tv_lastscreeningdate, tv_totalscreening;
    DataProvider dataProvider;
    Global global;
    Dialog datepic;
    TableRow tbl_occupationother, tbl_Other1, tbl_Any_HealthIssue,
            tbl_anyothercomplication, tbl_Bmi;
    Button btnSave;
    LinearLayout Linearcheckbox;

    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    ArrayList<MstBlock> Block = new ArrayList<MstBlock>();
    ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();
    ArrayAdapter<String> adapter;
    int SelectionValue = 0;
    String sqlUrineTest = "", MedicineId = "", MedicineQuantity = "";
    String sqlDiabeticStatus = "";
    String sqlDiabetic = "", sqlDiabeticStatusfill = "";
    GridView OtherGridView;
    int flag = 0;
    String hhguid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncdscreeningchc);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
        et_registration_no = (EditText) findViewById(R.id.et_registration_no);
        et_camp_no = (EditText) findViewById(R.id.et_camp_no);
        et_other1 = (EditText) findViewById(R.id.et_other1);
        et_occupationother = (EditText) findViewById(R.id.et_occupationother);
        et_anyothercomplication = (EditText) findViewById(R.id.et_anyothercomplication);
        edit_FBS = (EditText) findViewById(R.id.edit_FBS);
        edit_pptest = (EditText) findViewById(R.id.edit_pptest);
        edit_HbA1C = (EditText) findViewById(R.id.edit_HbA1C);
        spin_Urine_Test = (Spinner) findViewById(R.id.spin_Urine_Test);
        spin_DiabeticStatus = (Spinner) findViewById(R.id.spin_DiabeticStatus);
        spin_Diabetic = (Spinner) findViewById(R.id.spin_Diabetic);
        OtherGridView = (GridView) findViewById(R.id.OtherGridView);
        tbl_occupationother = (TableRow) findViewById(R.id.tbl_occupationother);
        tbl_anyothercomplication = (TableRow) findViewById(R.id.tbl_anyothercomplication);
        tbl_Bmi = (TableRow) findViewById(R.id.tbl_Bmi);
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

        et_Systolic = (EditText) findViewById(R.id.et_Systolic);
        et_Diastolic = (EditText) findViewById(R.id.et_Diastolic);
        spin_Sex = (Spinner) findViewById(R.id.spin_Sex);
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
        //  Linearcheckbox = (LinearLayout) findViewById(R.id.Linearcheckbox);
        tv_Date.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tv_Date);
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getInt("flag");
            hhguid = extras.getString("HHSurveyGUID");
        }
        fillVillageName(global.getLanguage());
        fillBlockName(global.getLanguage());
        fillCommonRecord(spin_Sex, 4, global.getLanguage());
        fillCommonRecord(spin_Occupation, 38, global.getLanguage());
        fillCommonRecord(Spin_Health_Condition, 36, global.getLanguage());
        fillCommonRecord(spin_Anyothecompli, 7, global.getLanguage());
        sqlUrineTest = "select * from mstCommon where flag=102 and LanguageID=" + global.getLanguage() + "";
        sqlDiabeticStatus = "select * from mstCommon where flag=48 and LanguageID=" + global.getLanguage() + "";

        sqlDiabetic = "select * from mstCommon where flag=49 and LanguageID=" + global.getLanguage() + "";

        Validate.fillspinner(NcdScreeningCHC.this, spin_Urine_Test, sqlUrineTest, "Value");
        Validate.fillspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "Value");
        Validate.fillspinner(NcdScreeningCHC.this, spin_Diabetic, sqlDiabetic, "Value");
//        fillCommonRecord(spin_Urine_Test, 102, global.getLanguage());
//        fillCommonRecord(spin_DiabeticStatus, 48, global.getLanguage());
//        fillCommonRecord(spin_Diabetic, 49, global.getLanguage());
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
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, edit_weight, getString(R.string.enterweight));
                    }

                }

            }
        });
        edit_FBS.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (!hasFocus) {
                    if (edit_FBS.getText().toString() != null && edit_FBS.getText().toString().length() > 0) {


                        SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);


                        //  spin_DiabeticStatus.setSelection(SelectionValue);
                        Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                        if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                            spin_Diabetic.setEnabled(true);
                        } else {
                            spin_Diabetic.setEnabled(false);
                            spin_Diabetic.setSelection(0);
                        }


                    }
                }

            }
        });
        edit_pptest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (!hasFocus) {
                    if (edit_pptest.getText().toString() != null && edit_pptest.getText().toString().length() > 0) {


                        SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                        Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                        if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                            spin_Diabetic.setEnabled(true);
                        } else {
                            spin_Diabetic.setEnabled(false);
                            spin_Diabetic.setSelection(0);
                        }
                    }
                }

            }
        });
        edit_HbA1C.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (!hasFocus) {
                    if (edit_HbA1C.getText().toString() != null && edit_HbA1C.getText().toString().length() > 0) {


                        SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                        Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                        if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                            Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");
                        } else {
                            spin_Diabetic.setEnabled(false);
                            spin_Diabetic.setSelection(0);
                        }
                    }
                }

            }
        });
        spin_Urine_Test
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 1) {

                            SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                            Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                            if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                                spin_Diabetic.setEnabled(true);
                            } else {
                                spin_Diabetic.setEnabled(false);
                                spin_Diabetic.setSelection(0);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

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
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, edit_height, getString(R.string.enterheight));
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
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, et_Systolic, getString(R.string.Systolic));
                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                    if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                        spin_Diabetic.setEnabled(true);
                    } else {
                        spin_Diabetic.setEnabled(false);
                        spin_Diabetic.setSelection(0);
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
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Integer.valueOf(s.toString());
                    if (count > 150) {
                        et_Diastolic.setText("");
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, et_Diastolic, getString(R.string.Diastolic));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                    if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                        spin_Diabetic.setEnabled(true);
                    } else {
                        spin_Diabetic.setEnabled(false);
                        spin_Diabetic.setSelection(0);
                    }
                }

            }
        });
        edit_FBS.addTextChangedListener(new TextWatcher() {

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
                    int count = Validate.returnIntegerValue(s.toString());
                    if (count > 400) {
                        //et_Diastolic.setText("");
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, edit_FBS, getString(R.string.PleaseEnterFBSvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                    if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                        spin_Diabetic.setEnabled(true);
                    } else {
                        spin_Diabetic.setEnabled(false);
                        spin_Diabetic.setSelection(0);
                    }
                }

            }
        });
        edit_pptest.addTextChangedListener(new TextWatcher() {

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
                    int count = Validate.returnIntegerValue(s.toString());
                    if (count > 500) {
                        // et_Diastolic.setText("");
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, edit_pptest, getString(R.string.PleaseEnterpptvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                    if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                        spin_Diabetic.setEnabled(true);
                    } else {
                        spin_Diabetic.setEnabled(false);
                        spin_Diabetic.setSelection(0);
                    }
                }

            }
        });
        edit_HbA1C.addTextChangedListener(new TextWatcher() {

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
                    Float count = Float.valueOf(s.toString());
                    if (count > 12) {
                        //et_Diastolic.setText("");
                        Validate.CustomAlertEdit(NcdScreeningCHC.this, edit_HbA1C, getString(R.string.PleaseEnterHbA1cvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                    if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                        spin_Diabetic.setEnabled(true);
                    } else {
                        spin_Diabetic.setEnabled(false);
                        spin_Diabetic.setSelection(0);
                    }
                }

            }
        });
        //SSQL("",Linearcheckbox, global.getLanguage(), 47);

        btnSave.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub


                if (sCheckValidation() == 0) {
                    saveNCDDetail();

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
        spin_Diabetic.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                if (pos == 1 || pos == 2) {
                    CustomeMedicine();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_Village.setEnabled(false);
        spin_DiabeticStatus.setEnabled(false);
        spin_DiabeticStatus.setBackgroundColor(getResources().getColor(R.color.AliceBlue));
        Validate.Setspinner(this, spin_DiabeticStatus, sqlDiabeticStatus, "ID", "1");
        spin_Diabetic.setEnabled(false);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }

    public int sCheckValidation() {
        try {
            if (et_name.getText().toString().length() == 0) {
                Validate.CustomAlertEdit(this, et_name, getString(R.string.Pleaseentername));
                return 1;

            }
            if (spin_Sex.getSelectedItemPosition() == 0) {
                Validate.CustomAlertSpinner(this, spin_Sex, getResources().getString(R.string.selectgender));
                return 1;
            }
            if (edit_height.getText().toString() != null
                    && edit_height.getText().toString().length() > 0) {
                int height = (int) Float.parseFloat(edit_height.getText().toString());
                if (height < 121) {
                    Validate.CustomAlertEdit(this, edit_height, getString(R.string.enterheight));
                    return 1;
                } else if (height > 200) {
                    Validate.CustomAlertEdit(this, edit_height, getString(R.string.enterheight));
                    return 1;
                }

            }
            if (edit_weight.getText().toString() != null
                    && edit_weight.getText().toString().length() > 0
                    && !edit_weight.getText().toString().equalsIgnoreCase(".")) {
                float weight = Float.parseFloat(edit_weight.getText()
                        .toString());
                if (weight < 30) {
                    Validate.CustomAlertEdit(this, edit_weight, getString(R.string.enterweight));
                    return 1;
                } else if (weight > 150) {
                    Validate.CustomAlertEdit(this, edit_weight, getString(R.string.enterweight));
                    return 1;
                }

            }
            if (et_Systolic.getText().toString().length() == 0 || (et_Systolic.getText().toString().length() > 0 && (et_Systolic.getText().toString() != null && Integer.valueOf(et_Systolic.getText()
                    .toString()) < 90 || Integer.valueOf(et_Systolic.getText().toString()) > 200))) {
                Validate.CustomAlertEdit(this, et_Systolic, getString(R.string.Systolic));
                return 1;
            }
            if (et_Diastolic.getText().toString().length() == 0 || (et_Diastolic.getText().toString().length() > 0 && (Integer.valueOf(et_Diastolic.getText()
                    .toString()) < 50 || Integer.valueOf(et_Diastolic.getText().toString()) > 150))) {
                Validate.CustomAlertEdit(this, et_Diastolic, getString(R.string.Diastolic));
                return 1;
            } if (Validate.returnIntegerValue(edit_FBS.getText().toString()) < 40 && edit_FBS.getText().toString().length() > 0) {
                Validate.CustomAlertEdit(this, edit_FBS, getString(R.string.PleaseEnterFBSvalue));
                return 1;
            }
            if (Validate.returnIntegerValue(edit_pptest.getText().toString()) < 50 && edit_pptest.getText().toString().length() > 0) {
                Validate.CustomAlertEdit(this, edit_pptest, getString(R.string.PleaseEnterpptvalue));
                return 1;
            }
            if (Validate.returnStringValue(edit_HbA1C.getText().toString()).length() > 0 && !edit_pptest.getText().toString().equalsIgnoreCase(".")) {
                Double value = Double.valueOf(edit_HbA1C.getText().toString());
                if (value < 3) {
                    Validate.CustomAlertEdit(this, edit_HbA1C, getString(R.string.PleaseEnterHbA1cvalue));
                    return 1;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

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
            int FBS = 0;
            int PPGTest = 0;
            String HbA1C = "";
            int UrineTest = 0;
            int DiabeticStatus = 0;
            int Diabetic = 0;

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
                Height = (int) Float.parseFloat(edit_height.getText().toString());
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

            if (et_Systolic.getText().length() > 0
                    || et_Diastolic.getText().length() > 0) {
                BP = et_Systolic.getText().toString() + "/"
                        + et_Diastolic.getText().toString();
            }
            Othercomplication = et_anyothercomplication.getText().toString();
            // SuspectedFor = savecheckbox();

            Status = visiblesuspected(et_Systolic, et_Diastolic);
            if (spin_Anyothecompli.getSelectedItemPosition() > 0) {
                AnyOthcomplicationYes = returnid(
                        spin_Anyothecompli.getSelectedItemPosition() - 1, 4,
                        global.getLanguage());
            }
            HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
            HHSurveyGUID = global.getGlobalHHSurveyGUID();
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
            FBS = Validate.returnIntegerValue(edit_FBS.getText().toString());
            PPGTest = Validate.returnIntegerValue(edit_pptest.getText().toString());
            HbA1C = Validate.returnStringValue(edit_HbA1C.getText().toString());


            UrineTest = Validate.returnID(NcdScreeningCHC.this, spin_Urine_Test, sqlUrineTest, "ID");

            DiabeticStatus = Validate.returnID(NcdScreeningCHC.this, spin_DiabeticStatus, sqlDiabeticStatus, "ID");

            Diabetic = Validate.returnID(NcdScreeningCHC.this, spin_Diabetic, sqlDiabetic, "ID");
            if (DiabeticStatus == 3 || DiabeticStatus == 5 || DiabeticStatus == 6) {
                Status = 1;
            }

            int ireturn = 0;
            ireturn = dataProvider.InsertNCDCHC(RegistrationNo, CampNo,
                    RegistrationDate, BlockCode, VillageCode,
                    HHFamilyMemberGUID, HHSurveyGUID, NCDScreeningGUID, Name,
                    Age, Sex, Address, Mobileno, Occupation, Occupationother,
                    Anypastillness, Height, Weight, BMI, RBS, BP,
                    Othercomplication, SuspectedFor, Referredto, ReferredBy,
                    Status, AshaID, ANMID, global.getUserID(), flag,
                    AnyOthcomplicationYes, SuspectedAnyOther, FBS, PPGTest, HbA1C, UrineTest, DiabeticStatus, Diabetic);
            if (ireturn > 0) {
                if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                    String sqlguid = "select NCDScreeningGUID from tblncdscreening where HHFamilyMemberGUID='"
                            + HHFamilyMemberGUID
                            + "' and CreatedOn='"
                            + Validate.getcurrentdate() + "'";
                    String guid = dataProvider.getRecord(sqlguid);

                    if (guid != null && guid.length() > 0) {
                        String sqldelete = "delete from tblncdscreeningmedicine where NCDScreeningGUID='" + guid + "'";
                        dataProvider.executeSql(sqldelete);
                    } else {
                        guid = NCDScreeningGUID;
                    }

                    String[] meicineid = MedicineId.split(",");
                    String[] meicineValue = MedicineQuantity.split(",");
                    for (int i = 0; i < meicineid.length; i++) {
                        dataProvider.insertMedicine(HHFamilyMemberGUID, guid, meicineid[i], meicineValue[i], AshaID, ANMID, global.getUserID());
                    }
                    CustomAlert(
                            getResources().getString(R.string.savesuccessfully), 1);
                } else if (SelectionValue == 1) {
                    CustomAlert(getResources().getString(R.string.savesuccessfully), 2);
                } else if (SelectionValue == 2 || SelectionValue == 3 || SelectionValue == 4) {
                    CustomAlert(getString(R.string.adequatefood) + "<br>" + getString(R.string.Physicalactivity) + "<br>" + getString(R.string.Reducingphysical) + "<br>" + getString(R.string.reduceexcessiveweight) + "<br>" + getString(R.string.medicineshavebeengiven) + "<br>", 2);
                } else {
                    CustomAlert(
                            getResources().getString(R.string.savesuccessfully), 1);
                }
            } else {
                // CustomAlerts(getResources().getString(R.string.no));
                System.out.print("not saved");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CustomAlert(String msg, final int iflag) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(Html.fromHtml(msg));

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (iflag == 1) {
                    if (flag == 1) {
                        Intent i = new Intent(NcdScreeningCHC.this, NCD_CHC_AA.class);
                        i.putExtra("flag", 1);
                        i.putExtra("HHSurveyGUID", hhguid);
                        finish();
                        startActivity(i);
                    } else {
                        Intent i = new Intent(NcdScreeningCHC.this, NCD_CHC_AA.class);

                        finish();
                        startActivity(i);
                    }
                } else {
                    CustomAlert(
                            getResources().getString(R.string.savesuccessfully), 1);
                }
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void SaveMedicinelist() {
        MedicineId = "";
        MedicineQuantity = "";
        String HHFamilyMemberGUID = "";
        String NCDScreeningGUID = "";

        int iSizeANC = OtherGridView.getChildCount();

        for (int i = 0; i < iSizeANC; i++) {
            try {
                ViewGroup gridChild = (ViewGroup) OtherGridView.getChildAt(i);
                TextView txt = (TextView) gridChild.findViewById(R.id.txt);
                CheckBox chk_Medicine = (CheckBox) gridChild.findViewById(R.id.chk_Medicine);
                EditText edit_value = (EditText) gridChild.findViewById(R.id.edit_value);
                if (chk_Medicine.isChecked()) {
                    MedicineId = MedicineId + Validate.returnIntegerValue(txt.getText().toString()) + ",";
                    MedicineQuantity = MedicineQuantity + Validate.returnIntegerValue(edit_value.getText().toString()) + ",";
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }
        // Gridview_ANC, // Gridview_Immunization

    }

    public int visiblesuspected(EditText et1, EditText et2) {
        int count = 0, count1 = 0, count2 = 0;
        try {
            if (et1.length() > 0 && !et1.toString().equalsIgnoreCase(".")) {
                count = Integer.valueOf(et1.getText().toString());
            }
            if (et2.length() > 0 && !et2.toString().equalsIgnoreCase(".")) {
                count2 = Integer.valueOf(et2.getText().toString());
            }
            if (count > 120 || count2 > 90) {
                count1 = 1;
            } else {
                count1 = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count1;
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

        btncancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });

        btnset.setOnClickListener(new OnClickListener() {

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

    public int Diabetic(EditText et1, EditText et2, EditText et3, int pos, EditText et4, EditText et5) {
        try {
            int count = 0, count1 = 0, count3 = 0, count4 = 0;
            double count2 = 0;
            count = Validate.returnIntegerValue(et1.getText().toString());
            count1 = Validate.returnIntegerValue(et2.getText().toString());
            count3 = Validate.returnIntegerValue(et4.getText().toString());
            count4 = Validate.returnIntegerValue(et5.getText().toString());
            if (Validate.returnStringValue(et3.getText().toString()).length() > 0) {
                count2 = Double.valueOf(et3.getText().toString());
            }


            if ((count >= 126 || count1 > 180 || count2 >= 6.5 || pos == 1) && (count3 > 139 || count4 > 89)) {
                return 7;
            } else if (count3 > 139 || count4 > 89) {
                return 6;
            } else if (count >= 126 || count1 > 180 || count2 >= 6.5 || pos == 1) {
                return 5;
            } else if ((count >= 100 || count2 >= 5.7) && (count3 > 120 || count4 > 80)) {
                return 4;
            } else if (count >= 100 || count2 >= 5.7) {
                return 3;
            } else if (count3 > 120 || count4 > 80) {
                return 2;
            } else {
                return 1;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
//    public void SSQL(String value,LinearLayout ll, int LanguageID, int Flag) {
//        String SQL = "select * from MSTCommon where LanguageID='" + LanguageID + "' and Flag='" + Flag + "'";
//        FillCheckBox(value,SQL, ll);
//    }
//
//    public void FillCheckBox(String value,String sql, LinearLayout ll) {
//        ArrayList<HashMap<String, String>> commonradio = null;
//        commonradio = dataProvider.getDynamicVal(sql);
//        if (commonradio != null && commonradio.size() > 0) {
//
//            Validate.FillCheckBox(value,ll,commonradio,NcdScreeningCHC.this);
//        }
//    }

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

    public void CustomeMedicine() {

        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.medicine_custome, null,
                false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        OtherGridView = (GridView) dialog.findViewById(R.id.OtherGridView);

        Button btn_save = (Button) dialog.findViewById(R.id.btn_save);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        fillgrid();
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMedicinelist();
                dialog.dismiss();
            }
        });

    }

    public void fillgrid() {
        ArrayList<HashMap<String, String>> data = null;
        String sql = "select * from mstCommon where flag=47 and LanguageID=" + global.getLanguage() + "";
        data = dataProvider.getDynamicVal(sql);
        if (data != null) {
            android.view.ViewGroup.LayoutParams params = OtherGridView
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * data.size();
            params.height = gridHeight1;
            OtherGridView.setLayoutParams(params);
            OtherGridView.setAdapter(new MedicineList_Adapter(NcdScreeningCHC.this, data));
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (flag == 1) {
            Intent i = new Intent(NcdScreeningCHC.this, NCD_CHC_AA.class);
            i.putExtra("flag", 1);
            i.putExtra("HHSurveyGUID", hhguid);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(NcdScreeningCHC.this, NCD_CHC_AA.class);

            finish();
            startActivity(i);
        }

    }

}
