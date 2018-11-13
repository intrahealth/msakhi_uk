package com.microware.intrahealth.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.DirectSearch;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.NCD_CHC_AA;
import com.microware.intrahealth.NcdScreeningCHC;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.adapter.MedicineList_Adapter;
import com.microware.intrahealth.adapter.NCD_CHC_ExistAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstBlock;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class OutSidePatient extends Fragment {

    EditText et_name, et_address, et_age,
            edit_mobile, edit_height, edit_weight, et_Bmi,
            et_Systolic, et_Diastolic, et_occupationother,
            et_anyothercomplication, et_other1,
            edit_FBS, edit_pptest, edit_HbA1C, et_Village, et_ashaname, et_adhar_card;
    CheckBox chk1,
            chk2, chk3, chk4, chk5, chk6;
    Spinner spin_Sex,
            spin_Occupation, SpinDOBAvailable, Spin_Health_Condition,
            spin_Anyothecompli, spin_feet, spin_inch, spin_Urine_Test, spin_DiabeticStatus, spin_Diabetic;
    String[] feet = {"4", "5", "6"};
    String[] inch = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    TextView tv_dob;
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
    String sqlUrineTest = "", sqldob = "", MedicineId = "", MedicineQuantity = "";
    String sqlDiabeticStatus = "";
    String sqlDiabetic = "";
    GridView OtherGridView;
    TableRow tbldob, tblage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.outsidepatient, container,
                false);
        dataProvider = new DataProvider(getActivity());
        global = (Global) getActivity().getApplicationContext();

        tbldob = (TableRow) rootView.findViewById(R.id.tbldob);
        tblage = (TableRow) rootView.findViewById(R.id.tblage);
        et_ashaname = (EditText) rootView.findViewById(R.id.et_ashaname);
        et_adhar_card = (EditText) rootView.findViewById(R.id.et_adhar_card);
        et_Village = (EditText) rootView.findViewById(R.id.et_Village);
        et_other1 = (EditText) rootView.findViewById(R.id.et_other1);
        et_occupationother = (EditText) rootView.findViewById(R.id.et_occupationother);
        et_anyothercomplication = (EditText) rootView.findViewById(R.id.et_anyothercomplication);
        edit_FBS = (EditText) rootView.findViewById(R.id.edit_FBS);
        edit_pptest = (EditText) rootView.findViewById(R.id.edit_pptest);
        edit_HbA1C = (EditText) rootView.findViewById(R.id.edit_HbA1C);
        spin_Urine_Test = (Spinner) rootView.findViewById(R.id.spin_Urine_Test);
        spin_DiabeticStatus = (Spinner) rootView.findViewById(R.id.spin_DiabeticStatus);
        spin_Diabetic = (Spinner) rootView.findViewById(R.id.spin_Diabetic);
        OtherGridView = (GridView) rootView.findViewById(R.id.OtherGridView);
        tbl_occupationother = (TableRow) rootView.findViewById(R.id.tbl_occupationother);
        tbl_anyothercomplication = (TableRow) rootView.findViewById(R.id.tbl_anyothercomplication);
        tbl_Bmi = (TableRow) rootView.findViewById(R.id.tbl_Bmi);
        et_anyothercomplication = (EditText) rootView.findViewById(R.id.et_anyothercomplication);
        spin_Anyothecompli = (Spinner) rootView.findViewById(R.id.spin_Anyothecompli);
        et_name = (EditText) rootView.findViewById(R.id.et_name);
        et_address = (EditText) rootView.findViewById(R.id.et_address);
        et_age = (EditText) rootView.findViewById(R.id.et_age);
        edit_mobile = (EditText) rootView.findViewById(R.id.edit_mobile);
        spin_Occupation = (Spinner) rootView.findViewById(R.id.spin_Occupation);
        Spin_Health_Condition = (Spinner) rootView.findViewById(R.id.Spin_Health_Condition);
        spin_feet = (Spinner) rootView.findViewById(R.id.spin_feet);
        spin_inch = (Spinner) rootView.findViewById(R.id.spin_inch);
        edit_height = (EditText) rootView.findViewById(R.id.edit_height);
        edit_weight = (EditText) rootView.findViewById(R.id.edit_weight);
        et_Bmi = (EditText) rootView.findViewById(R.id.et_Bmi);

        et_Systolic = (EditText) rootView.findViewById(R.id.et_Systolic);
        et_Diastolic = (EditText) rootView.findViewById(R.id.et_Diastolic);
        spin_Sex = (Spinner) rootView.findViewById(R.id.spin_Sex);

        tv_dob = (TextView) rootView.findViewById(R.id.tv_dob);

        tbl_Other1 = (TableRow) rootView.findViewById(R.id.tbl_Other1);
        tbl_Any_HealthIssue = (TableRow) rootView.findViewById(R.id.tbl_Any_HealthIssue);
        chk1 = (CheckBox) rootView.findViewById(R.id.chk1);
        chk2 = (CheckBox) rootView.findViewById(R.id.chk2);
        chk3 = (CheckBox) rootView.findViewById(R.id.chk3);
        chk4 = (CheckBox) rootView.findViewById(R.id.chk4);
        chk5 = (CheckBox) rootView.findViewById(R.id.chk5);
        chk6 = (CheckBox) rootView.findViewById(R.id.chk6);
        btnSave = (Button) rootView.findViewById(R.id.btnSave);
        SpinDOBAvailable = (Spinner) rootView.findViewById(R.id.SpinDOBAvailable);
        //  Linearcheckbox = (LinearLayout) findViewById(R.id.Linearcheckbox);
        tv_dob.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tv_dob);
            }
        });


        fillCommonRecord(spin_Sex, 4, global.getLanguage());
        fillCommonRecord(spin_Occupation, 38, global.getLanguage());
        fillCommonRecord(Spin_Health_Condition, 36, global.getLanguage());
        fillCommonRecord(spin_Anyothecompli, 7, global.getLanguage());
        sqldob = "select * from mstCommon where flag=7 and LanguageID=" + global.getLanguage() + "";
        sqlUrineTest = "select * from mstCommon where flag=102 and LanguageID=" + global.getLanguage() + "";
        sqlDiabeticStatus = "select * from mstCommon where flag=48 and LanguageID=" + global.getLanguage() + "";

        sqlDiabetic = "select * from mstCommon where flag=49 and LanguageID=" + global.getLanguage() + "";

        Validate.fillspinner(getActivity(), SpinDOBAvailable, sqldob, "Value");
        Validate.fillspinner(getActivity(), spin_Urine_Test, sqlUrineTest, "Value");
        Validate.fillspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "Value");
        Validate.fillspinner(getActivity(), spin_Diabetic, sqlDiabetic, "Value");

        spin_Occupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        et_age.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                datesetason(et_age);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                datesetason(et_age);
            }
        });
        SpinDOBAvailable
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 1) {
                            tblage.setVisibility(View.GONE);
                            et_age.setText("");
                            tv_dob.setEnabled(true);
                        } else if (pos == 2) {
                            tblage.setVisibility(View.VISIBLE);
                            tv_dob.setEnabled(false);
                        } else {
                            tv_dob.setText("");
                            tblage.setVisibility(View.GONE);
                            et_age.setText("");
                            tv_dob.setEnabled(false);

                        }

                    }

                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
        Spin_Health_Condition
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

        spin_Anyothecompli
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        Validate.CustomAlertEdit(getActivity(), edit_weight, getString(R.string.enterweight));
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
                        Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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

                        Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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

                        Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

                        if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {
                            Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");
                        } else {
                            spin_Diabetic.setEnabled(false);
                            spin_Diabetic.setSelection(0);
                        }
                    }
                }

            }
        });
        spin_Urine_Test
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 1) {

                            SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                            Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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
        chk6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

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
                        Validate.CustomAlertEdit(getActivity(), edit_height, getString(R.string.enterheight));
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
                if (s.length() > 0 && !s.toString().equalsIgnoreCase(".")) {
                    int count = Validate.returnIntegerValue(s.toString());

                    if (count > 200) {
                        et_Systolic.setText("");
                        Validate.CustomAlertEdit(getActivity(), et_Systolic, getString(R.string.Systolic));
                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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
                    int count = Validate.returnIntegerValue(s.toString());
                    if (count > 150) {
                        et_Diastolic.setText("");
                        Validate.CustomAlertEdit(getActivity(), et_Diastolic, getString(R.string.Diastolic));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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
                        Validate.CustomAlertEdit(getActivity(), edit_FBS, getString(R.string.PleaseEnterFBSvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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
                        Validate.CustomAlertEdit(getActivity(), edit_pptest, getString(R.string.PleaseEnterpptvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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
                        Validate.CustomAlertEdit(getActivity(), edit_HbA1C, getString(R.string.PleaseEnterHbA1cvalue));


                    }
                    SelectionValue = Diabetic(edit_FBS, edit_pptest, edit_HbA1C, spin_Urine_Test.getSelectedItemPosition(), et_Systolic, et_Diastolic);

                    Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", SelectionValue + "");

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


        fillspinnerheight();
        spin_feet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        spin_inch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        spin_Diabetic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spin_DiabeticStatus.setEnabled(false);
        spin_DiabeticStatus.setBackgroundColor(getResources().getColor(R.color.AliceBlue));
        Validate.Setspinner(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID", "1");

        spin_Diabetic.setEnabled(false);


        return rootView;
    }

    public void fillspinnerheight() {
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, feet);
        spin_feet.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, inch);
        spin_inch.setAdapter(adapter);
    }

    private void datesetason(EditText et_Year) {
        String[] y = null;
        String year = null;
        int yeartx = 0;
        int monthx = 0;
        if (et_Year.getText().toString().trim().length() > 0)
            yeartx = Validate.returnIntegerValue(et_Year.getText().toString());


        yeartx = yeartx * 12;
        monthx = monthx + yeartx;
        year = Validate.getcurrentdate().toString();

        if (year != null
                && !year.equalsIgnoreCase("")
                && !year.equalsIgnoreCase("0")
                && ((!et_Year.getText().toString().equalsIgnoreCase("0") && et_Year
                .getText().toString().length() > 0))) {
            y = Validate.getcurrentdate().toString().split("-");
            int intyear = Validate.returnIntegerValue(y[0]);
            int month = Validate.returnIntegerValue(y[1]);
            int day = Validate.returnIntegerValue(y[2]);
            Calendar now = new GregorianCalendar(intyear, month, day);

            now.add(Calendar.MONTH, -monthx);
            String monthcal = "", DateCal = "";
            if ((now.get(Calendar.MONTH) + 1) < 10) {
                monthcal = "0" + (now.get(Calendar.MONTH));
            } else {
                monthcal = String.valueOf(now.get(Calendar.MONTH));

            }
            if ((now.get(Calendar.DATE)) < 10) {
                DateCal = "0" + (now.get(Calendar.DATE));
            } else {
                DateCal = String.valueOf(now.get(Calendar.DATE));

            }

            tv_dob.setText(DateCal + "-" + monthcal + "-"
                    + now.get(Calendar.YEAR));

            // Dobcalculation();
            // dobchk = btndob_ason.getText().toString();
        } else if (et_Year.getText().toString().length() == 0) {
            tv_dob.setText("");
        }

    }

    public double calculateheight() {
        double height = 0;
        try {
            int feet = Validate.returnIntegerValue(spin_feet.getSelectedItem().toString());
            int inch = Validate.returnIntegerValue(spin_inch.getSelectedItem().toString());
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

                if (ft == Validate.returnIntegerValue(feet[i])) {
                    ftvalue = i;
                }
            }
            for (int i = 0; i < inch.length; i++) {

                if (in == Validate.returnIntegerValue(inch[i])) {
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


    public int sCheckValidation() {
        try {
            if (et_Village.getText().toString().length() == 0) {
                Validate.CustomAlertEdit(getActivity(), et_Village, getString(R.string.villagename1));
                return 1;

            }
            if (et_name.getText().toString().length() == 0) {
                Validate.CustomAlertEdit(getActivity(), et_name, getString(R.string.Pleaseentername));
                return 1;

            }
            if (et_adhar_card.getText().toString().length() > 0 && et_adhar_card.getText().toString().length() < 12) {
                Validate.CustomAlertEdit(getActivity(), et_adhar_card, getString(R.string.enter12digituid));
                return 1;

            }
            if (et_address.getText().toString().length() == 0) {
                Validate.CustomAlertEdit(getActivity(), et_address, getString(R.string.PleaseenterAddress));
                return 1;

            }
            if (tv_dob.getText().toString().length() == 0) {
                Validate.CustomAlertTextview(getActivity(), tv_dob, getString(R.string.dateenter));
                return 1;

            }
            if (Validate.diffYrs(tv_dob.getText().toString()) < 30) {
                Validate.CustomAlertTextview(getActivity(), tv_dob, getString(R.string.dateenter));
                return 1;

            }
            if (spin_Sex.getSelectedItemPosition() == 0) {
                Validate.CustomAlertSpinner(getActivity(), spin_Sex, getResources().getString(R.string.selectgender));
                return 1;
            }
            if (edit_mobile.getText().toString().length() > 0 && Validate.checkmobileno(edit_mobile.getText().toString()) == 0) {
                Validate.CustomAlertEdit(getActivity(), edit_mobile, getString(R.string.PleasevalidphoneNo));
                return 1;

            }
            if (edit_height.getText().toString() != null
                    && edit_height.getText().toString().length() > 0) {
                int height = (int) Float.parseFloat(edit_height.getText().toString());
                if (height < 121) {
                    Validate.CustomAlertEdit(getActivity(), edit_height, getString(R.string.enterheight));
                    return 1;
                } else if (height > 200) {
                    Validate.CustomAlertEdit(getActivity(), edit_height, getString(R.string.enterheight));
                    return 1;
                }

            }
            if (edit_weight.getText().toString() != null
                    && edit_weight.getText().toString().length() > 0
                    && !edit_weight.getText().toString().equalsIgnoreCase(".")) {
                float weight = Float.parseFloat(edit_weight.getText()
                        .toString());
                if (weight < 30) {
                    Validate.CustomAlertEdit(getActivity(), edit_weight, getString(R.string.enterweight));
                    return 1;
                } else if (weight > 150) {
                    Validate.CustomAlertEdit(getActivity(), edit_weight, getString(R.string.enterweight));
                    return 1;
                }

            }
            if (et_Systolic.getText().toString().length() == 0 || (et_Systolic.getText().toString().length() > 0 && (et_Systolic.getText().toString() != null && Validate.returnIntegerValue(et_Systolic.getText()
                    .toString()) < 90 || Validate.returnIntegerValue(et_Systolic.getText().toString()) > 200))) {
                Validate.CustomAlertEdit(getActivity(), et_Systolic, getString(R.string.Systolic));
                return 1;
            }
            if (et_Diastolic.getText().toString().length() == 0 || (et_Diastolic.getText().toString().length() > 0 && (Validate.returnIntegerValue(et_Diastolic.getText()
                    .toString()) < 50 || Validate.returnIntegerValue(et_Diastolic.getText().toString()) > 150))) {
                Validate.CustomAlertEdit(getActivity(), et_Diastolic, getString(R.string.Diastolic));
                return 1;
            }
            if (Validate.returnIntegerValue(edit_FBS.getText().toString()) < 40 && edit_FBS.getText().toString().length() > 0) {
                Validate.CustomAlertEdit(getActivity(), edit_FBS, getString(R.string.PleaseEnterFBSvalue));
                return 1;
            }
            if (Validate.returnIntegerValue(edit_pptest.getText().toString()) < 50 && edit_pptest.getText().toString().length() > 0) {
                Validate.CustomAlertEdit(getActivity(), edit_pptest, getString(R.string.PleaseEnterpptvalue));
                return 1;
            }
            if (Validate.returnStringValue(edit_HbA1C.getText().toString()).length() > 0 && !edit_pptest.getText().toString().equalsIgnoreCase(".")) {
                Double value = Double.valueOf(edit_HbA1C.getText().toString());
                if (value < 3) {
                    Validate.CustomAlertEdit(getActivity(), edit_HbA1C, getString(R.string.PleaseEnterHbA1cvalue));
                    return 1;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public String savecheckbox() {
        String ss = "";
        if (chk1.isChecked()) {
            ss = ss + "1,";
        }
        if (chk2.isChecked()) {
            ss = ss + "2,";
        }
        if (chk3.isChecked()) {
            ss = ss + "3,";
        }
        if (chk4.isChecked()) {
            ss = ss + "4,";
        }
        if (chk5.isChecked()) {
            ss = ss + "5,";
        }
        if (chk6.isChecked()) {
            ss = ss + "6";
        }

        return ss;
    }

    public void saveNCDDetail() {
        try {
            String NCDScreeningGUID = "";
            String Name = "";
            String Dob = "", Village = "", ashaname = "", adharcard = "";
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
            int Status = 0;
            int AnyOthcomplicationYes = 0;
            String SuspectedAnyOther = "";
            int FBS = 0;
            int PPGTest = 0;
            String HbA1C = "";
            int UrineTest = 0;
            int DiabeticStatus = 0;
            int Diabetic = 0;
            int DOBAvailable = 0;


            Dob = Validate.changeDateFormat(tv_dob.getText().toString());
            if (!edit_height.getText().toString().equalsIgnoreCase("null")
                    && edit_height.getText().toString().length() > 0) {
                Height = (int) Float.parseFloat(edit_height.getText().toString());
            }

            Mobileno = Validate.returnStringValue(edit_mobile.getText().toString());


            if (spin_Occupation.getSelectedItemPosition() > 0) {
                Occupation = returnid(
                        spin_Occupation.getSelectedItemPosition() - 1, 38,
                        global.getLanguage());
            }

            if (spin_Sex.getSelectedItemPosition() > 0) {
                Sex = returnid(spin_Sex.getSelectedItemPosition() - 1, 4,
                        global.getLanguage());
            }


            Name = Validate.returnStringValue(et_name.getText().toString());
            ashaname = Validate.returnStringValue(et_ashaname.getText().toString());
            adharcard = Validate.returnStringValue(et_adhar_card.getText().toString());

            Address = Validate.returnStringValue(et_address.getText().toString());
            Village = Validate.returnStringValue(et_Village.getText().toString());

            Occupationother = Validate.returnStringValue(et_occupationother.getText().toString());
            // Anypastillness = et_illness.getText().toString();
            SuspectedFor = savecheckbox();
            if (chk6.isChecked()) {
                SuspectedAnyOther = et_other1.getText().toString();
            }
            Weight = Validate.returnStringValue(edit_weight.getText().toString());
            BMI = Validate.returnStringValue(et_Bmi.getText().toString());

            if (et_Systolic.getText().length() > 0
                    || et_Diastolic.getText().length() > 0) {
                BP = Validate.returnStringValue(et_Systolic.getText().toString()) + "/"
                        + Validate.returnStringValue(et_Diastolic.getText().toString());
            }
            Othercomplication = Validate.returnStringValue(et_anyothercomplication.getText().toString());
            // SuspectedFor = savecheckbox();

            Status = visiblesuspected(et_Systolic, et_Diastolic);
            if (spin_Anyothecompli.getSelectedItemPosition() > 0) {
                AnyOthcomplicationYes = returnid(
                        spin_Anyothecompli.getSelectedItemPosition() - 1, 4,
                        global.getLanguage());
            }

            FBS = Validate.returnIntegerValue(edit_FBS.getText().toString());
            PPGTest = Validate.returnIntegerValue(edit_pptest.getText().toString());
            HbA1C = Validate.returnStringValue(edit_HbA1C.getText().toString());


            UrineTest = Validate.returnID(getActivity(), spin_Urine_Test, sqlUrineTest, "ID");

            DiabeticStatus = Validate.returnID(getActivity(), spin_DiabeticStatus, sqlDiabeticStatus, "ID");

            DOBAvailable = Validate.returnID(getActivity(), SpinDOBAvailable, sqldob, "ID");
            Diabetic = Validate.returnID(getActivity(), spin_Diabetic, sqlDiabetic, "ID");
            if (DiabeticStatus == 3 || DiabeticStatus == 5 || DiabeticStatus == 6) {
                Status = 1;
            }
            NCDScreeningGUID = Validate.random();
            int ireturn = 0;
            ireturn = dataProvider.InsertNCDCHCoutside(ashaname, Village, NCDScreeningGUID, Name, adharcard,
                    DOBAvailable, Dob, Sex, Address, Mobileno, Occupation, Occupationother, Anypastillness, Height, Weight, BMI, BP,
                    Othercomplication, SuspectedFor, Status, global.getUserID(), AnyOthcomplicationYes, SuspectedAnyOther, FBS, PPGTest, HbA1C, UrineTest, DiabeticStatus, Diabetic);
            if (ireturn > 0) {
                if (SelectionValue == 5 || SelectionValue == 6 || SelectionValue == 7) {


                    String[] meicineid = MedicineId.split(",");
                    String[] meicineValue = MedicineQuantity.split(",");
                    for (int i = 0; i < meicineid.length; i++) {
                        dataProvider.insertMedicineoutside(NCDScreeningGUID, meicineid[i], meicineValue[i], global.getUserID());
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
        txtTitle.setText(Html.fromHtml(msg));

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (iflag == 1) {
                    Intent intent = getActivity().getIntent();
                    startActivity(intent);
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
                count = Validate.returnIntegerValue(et1.getText().toString());
            }
            if (et2.length() > 0 && !et2.toString().equalsIgnoreCase(".")) {
                count2 = Validate.returnIntegerValue(et2.getText().toString());
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


    private void fillCommonRecord(Spinner spin, int iflag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iflag);

        String sValue[] = new String[Common.size() + 1];

        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Common.size(); i++) {
            sValue[i + 1] = Common.get(i).getValue();
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spin.setAdapter(adapter);
    }

    public void ShowDatePicker(final TextView txt) {
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

        datepic.getWindow().setLayout(ViewPager.LayoutParams.WRAP_CONTENT,
                ViewPager.LayoutParams.WRAP_CONTENT);

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
//            Validate.FillCheckBox(value,ll,commonradio,NcdScreeningCHC.getActivity());
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

        final Dialog dialog = new Dialog(getActivity());
        // hide to default title for Dialog
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getActivity()
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
            OtherGridView.setAdapter(new MedicineList_Adapter(getActivity(), data));
        }
    }


}
