package com.microware.intrahealth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;

import java.util.AbstractCollection;
import java.util.ArrayList;

/**
 * Created by Microware on 10/9/2017.
 */

public class Ncd_Cbac extends Activity {


    TextView tv_name, tv_score, tv_age, tv_Age_score, tv_Consumegutka_score, tv_Consumealcohol_score, tv_Waist_score, tv_Physicalactivity, tv_Familyhistory;
    Spinner spin_Consumegutka, spin_Consumealcohol, spin_Physicalactivity, spin_Familyhistory, spin_shortnessbreath, spin_coughing,
            spin_Bloodsputum, spin_historyfits, spin_Openingmouth, spin_Ulcers, spin_Toneofvoice, spin_Lumpinbreast, spin_stainednipple,
            spin_shapesizebreast, spin_bleedingbwperiods, spin_Bleedingmenopause, spin_Bleedingintercourse, spin_Foulsmelling;
    EditText et_Waist, et_Waistinch;
    Button btnSave;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    Global global;
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    LinearLayout ll_women;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ncd_cbac);
        dataProvider = new DataProvider(this);
        global = (Global) getApplicationContext();
        ll_women = (LinearLayout) findViewById(R.id.ll_women);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_age = (TextView) findViewById(R.id.tv_age);
        tv_Age_score = (TextView) findViewById(R.id.tv_Age_score);
        tv_Consumegutka_score = (TextView) findViewById(R.id.tv_Consumegutka_score);
        tv_Consumealcohol_score = (TextView) findViewById(R.id.tv_Consumealcohol_score);
        tv_Waist_score = (TextView) findViewById(R.id.tv_Waist_score);
        tv_Physicalactivity = (TextView) findViewById(R.id.tv_Physicalactivity);
        tv_Familyhistory = (TextView) findViewById(R.id.tv_Familyhistory);

        et_Waist = (EditText) findViewById(R.id.et_Waist);
        et_Waistinch = (EditText) findViewById(R.id.et_Waistinch);
        spin_Consumegutka = (Spinner) findViewById(R.id.spin_Consumegutka);
        spin_Consumealcohol = (Spinner) findViewById(R.id.spin_Consumealcohol);
        spin_Physicalactivity = (Spinner) findViewById(R.id.spin_Physicalactivity);
        spin_Familyhistory = (Spinner) findViewById(R.id.spin_Familyhistory);
        spin_shortnessbreath = (Spinner) findViewById(R.id.spin_shortnessbreath);
        spin_coughing = (Spinner) findViewById(R.id.spin_coughing);
        spin_Bloodsputum = (Spinner) findViewById(R.id.spin_Bloodsputum);
        spin_historyfits = (Spinner) findViewById(R.id.spin_historyfits);
        spin_Openingmouth = (Spinner) findViewById(R.id.spin_Openingmouth);
        spin_Ulcers = (Spinner) findViewById(R.id.spin_Ulcers);
        spin_Toneofvoice = (Spinner) findViewById(R.id.spin_Toneofvoice);
        spin_Lumpinbreast = (Spinner) findViewById(R.id.spin_Lumpinbreast);
        spin_stainednipple = (Spinner) findViewById(R.id.spin_stainednipple);
        spin_shapesizebreast = (Spinner) findViewById(R.id.spin_shapesizebreast);
        spin_bleedingbwperiods = (Spinner) findViewById(R.id.spin_bleedingbwperiods);
        spin_Bleedingmenopause = (Spinner) findViewById(R.id.spin_Bleedingmenopause);
        spin_Bleedingintercourse = (Spinner) findViewById(R.id.spin_Bleedingintercourse);
        spin_Foulsmelling = (Spinner) findViewById(R.id.spin_Foulsmelling);
        btnSave = (Button) findViewById(R.id.btnSave);
        fillCommonRecord(spin_Consumegutka, 45, global.getLanguage());
        fillCommonRecord(spin_Consumealcohol, 7, global.getLanguage());
        fillCommonRecord(spin_Physicalactivity, 46, global.getLanguage());
        fillCommonRecord(spin_Familyhistory, 7, global.getLanguage());
        fillCommonRecord(spin_shortnessbreath, 7, global.getLanguage());
        fillCommonRecord(spin_coughing, 7, global.getLanguage());
        fillCommonRecord(spin_Bloodsputum, 7, global.getLanguage());
        fillCommonRecord(spin_historyfits, 7, global.getLanguage());
        fillCommonRecord(spin_Openingmouth, 7, global.getLanguage());
        fillCommonRecord(spin_Ulcers, 7, global.getLanguage());
        fillCommonRecord(spin_Toneofvoice, 7, global.getLanguage());
        fillCommonRecord(spin_Lumpinbreast, 7, global.getLanguage());
        fillCommonRecord(spin_stainednipple, 7, global.getLanguage());
        fillCommonRecord(spin_shapesizebreast, 7, global.getLanguage());
        fillCommonRecord(spin_bleedingbwperiods, 7, global.getLanguage());
        fillCommonRecord(spin_Bleedingmenopause, 7, global.getLanguage());
        fillCommonRecord(spin_Bleedingintercourse, 7, global.getLanguage());
        fillCommonRecord(spin_Foulsmelling, 7, global.getLanguage());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int icheck = 0;

                icheck = sCheckValidation();
                if (icheck == 1) {
                    saveNCDDCBAC();

                } else {
                    showAlert(icheck);
                }
            }
        });
        filldata();
        spin_Consumegutka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if (pos == 1) {
                    tv_Consumegutka_score.setText("" + 0);

                } else if (pos == 2) {
                    tv_Consumegutka_score.setText("" + 1);
                } else if (pos == 3) {
                    tv_Consumegutka_score.setText("" + 2);
                } else {
                    tv_Consumegutka_score.setText("" + 0);
                }
                tv_score.setText("" + totalscore());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spin_Consumealcohol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if (pos == 1) {
                    tv_Consumealcohol_score.setText("" + 1);

                } else if (pos == 2) {
                    tv_Consumealcohol_score.setText("" + 0);
                } else {
                    tv_Consumealcohol_score.setText("" + 0);
                }
                tv_score.setText("" + totalscore());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spin_Physicalactivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if (pos == 1) {
                    tv_Physicalactivity.setText("" + 0);

                } else if (pos == 2) {
                    tv_Physicalactivity.setText("" + 1);
                } else {
                    tv_Physicalactivity.setText("" + 0);
                }
                tv_score.setText("" + totalscore());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spin_Familyhistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if (pos == 1) {
                    tv_Familyhistory.setText("" + 2);

                } else if (pos == 2) {
                    tv_Familyhistory.setText("" + 0);
                } else {
                    tv_Familyhistory.setText("" + 0);
                }
                tv_score.setText("" + totalscore());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        String sqlgender = "select  GenderID from Tbl_hhfamilymember  where HHFamilyMemberGUID='"
                + global.getGlobalHHFamilyMemberGUID()
                + "'";
        final int Gender = dataProvider.getMaxRecord(sqlgender);
        if (Gender == 2) {
            ll_women.setVisibility(View.VISIBLE);
        }
        et_Waist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before,
                                          int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    Float value = Float.valueOf(s.toString());
                    if (value <= 200) {
                        if (Gender == 1) {
                            if (value < 90) {
                                tv_Waist_score.setText("" + 0);
                            } else if (value > 89 && value < 101) {
                                tv_Waist_score.setText("" + 1);
                            } else {
                                tv_Waist_score.setText("" + 2);
                            }
                            totalscore();

                        } else if (Gender == 2) {
                            if (value < 80) {
                                tv_Waist_score.setText("" + 0);
                            } else if (value > 79 && value < 91) {
                                tv_Waist_score.setText("" + 1);
                            } else {
                                tv_Waist_score.setText("" + 2);
                            }
                            totalscore();
                        }
                    } else {
                        et_Waist.setText("");
                        et_Waistinch.setText("");
                        tv_Waist_score.setText("" + 0);

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    tv_Waist_score.setText("" + 0);
                }
            }
        }); et_Waistinch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before,
                                          int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    et_Waist.setText(""+calculateheight());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = s.toString();
                if (search.length() == 0) {
                    et_Waist.setText("");
                }
            }
        });
//        et_Waistinch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    et_Waist.setText(""+calculateheight());
//                }
//            }
//        });

    }

    public double calculateheight() {
        double height = 0;
        try {
            if (et_Waistinch.getText().toString().length() > 0) {
                int inch = Integer.valueOf(et_Waistinch.getText().toString());
                height = (inch) * 2.54;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return height;
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

    private void filldata() {
        String sql = "select Familymembername from Tbl_HHFamilyMember where HHFamilyMemberGUID='"
                + global.getGlobalHHFamilyMemberGUID()
                + "'";
        String aa = dataProvider.getRecord(sql);
        String sqlage = "select  case   DOBAvailable when 1 then  cast(round((julianday('NOW')-julianday(DateOfBirth))/365+.5)  as int) when 2 then AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear) end age from Tbl_hhfamilymember  where HHFamilyMemberGUID='"
                + global.getGlobalHHFamilyMemberGUID()
                + "'";
        String age = dataProvider.getRecord(sqlage);
        if (age != null && age.length() > 0) {
            int iage = Integer.valueOf(age);
            if (iage > 29 && iage < 40) {
                tv_Age_score.setText("" + 0);
            } else if (iage > 39 && iage < 50) {
                tv_Age_score.setText("" + 1);
            } else if (iage >= 50) {
                tv_Age_score.setText("" + 2);
            }
            tv_score.setText("" + totalscore());
        }
        tv_name.setText(aa);
        tv_age.setText(age);

    }

    private int totalscore() {
        int score = 0;
        if (tv_Age_score.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Age_score.getText().toString());
        }

        if (tv_Consumegutka_score.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Consumegutka_score.getText().toString());
        }
        if (tv_Consumealcohol_score.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Consumealcohol_score.getText().toString());
        }
        if (tv_Waist_score.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Waist_score.getText().toString());
        }
        if (tv_Physicalactivity.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Physicalactivity.getText().toString());
        }
        if (tv_Familyhistory.getText().length() > 0) {
            score = score + Integer.valueOf(tv_Familyhistory.getText().toString());
        }

        return score;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent i = new Intent(Ncd_Cbac.this, NCD_AA.class);
        finish();
        startActivity(i);
    }

    public int sCheckValidation() {
        try {
            if (et_Waist.getText().toString().length() > 0) {
                Float waist ;
                waist = Float.valueOf(et_Waist.getText().toString());
                if (waist < 50) {
                    return 2;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;

    }

    public void saveNCDDCBAC() {
        try {

            int BlockID = 0;
            int VillageID = 0;
            String HHFamilyMemberGUID = "";
            String HHSurveyGUID = "";
            String NCDCBACGUID = "";
            int Age = 0;
            int Smoke = 0;
            int Alcohol = 0;
            String Waist = "";
            int PhysicalActivity = 0;
            int FamilyHistory = 0;
            int Score = 0;
            int Breath = 0;
            int Coughing = 0;
            int BloodinSputum = 0;
            int Fits = 0;
            int OpeningMouth = 0;
            int Ulcers = 0;
            int AnyChangeTone = 0;
            int LumpinBreast = 0;
            int BloodStainedNipple = 0;
            int BreastSize = 0;
            int BleedingPeriods = 0;
            int BleedingMenopause = 0;
            int BleedingIntercourse = 0;
            int VaginalDischarge = 0;
            int Status = 0;

            int AshaID = 0;
            int ANMID = 0;

//            UID = global.getsGlobalUserID();
            HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
            HHSurveyGUID = global.getGlobalHHSurveyGUID();

            if (global.getsGlobalANMCODE() != null
                    && global.getsGlobalANMCODE().length() > 0) {
                ANMID = Integer.valueOf(global.getsGlobalANMCODE());
            }

            if (global.getsGlobalAshaCode() != null
                    && global.getsGlobalAshaCode().length() > 0) {
                AshaID = Integer.valueOf(global.getsGlobalAshaCode());
            }
            if (tv_age.getText().toString().length() > 0) {
                Age = Integer.valueOf(tv_age.getText().toString());
            }
            if (spin_Consumegutka.getSelectedItemPosition() > 0) {
                Smoke = returnid(
                        spin_Consumegutka.getSelectedItemPosition() - 1, 45,
                        global.getLanguage());
            }
            if (spin_Consumealcohol.getSelectedItemPosition() > 0) {
                Alcohol = returnid(
                        spin_Consumealcohol.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }
            if (et_Waist.getText().toString().length() > 0) {
                Waist = et_Waist.getText().toString();
            }
            if (tv_age.getText().toString().length() > 0) {
                Age = Integer.valueOf(tv_age.getText().toString());
            }
            if (tv_score.getText().toString().length() > 0) {
                Score = Integer.valueOf(tv_score.getText().toString());
            }


            if (spin_Physicalactivity.getSelectedItemPosition() > 0) {
                PhysicalActivity = returnid(
                        spin_Physicalactivity.getSelectedItemPosition() - 1, 46,
                        global.getLanguage());
            }
            if (spin_Familyhistory.getSelectedItemPosition() > 0) {
                FamilyHistory = returnid(
                        spin_Familyhistory.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }


            if (spin_shortnessbreath.getSelectedItemPosition() > 0) {
                Breath = returnid(
                        spin_shortnessbreath.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_coughing.getSelectedItemPosition() > 0) {
                Coughing = returnid(
                        spin_coughing.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Bloodsputum.getSelectedItemPosition() > 0) {
                BloodinSputum = returnid(
                        spin_Bloodsputum.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_historyfits.getSelectedItemPosition() > 0) {
                Fits = returnid(
                        spin_historyfits.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Openingmouth.getSelectedItemPosition() > 0) {
                OpeningMouth = returnid(
                        spin_Openingmouth.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Ulcers.getSelectedItemPosition() > 0) {
                Ulcers = returnid(
                        spin_Ulcers.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Toneofvoice.getSelectedItemPosition() > 0) {
                AnyChangeTone = returnid(
                        spin_Toneofvoice.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Lumpinbreast.getSelectedItemPosition() > 0) {
                LumpinBreast = returnid(
                        spin_Lumpinbreast.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_stainednipple.getSelectedItemPosition() > 0) {
                BloodStainedNipple = returnid(
                        spin_stainednipple.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_shapesizebreast.getSelectedItemPosition() > 0) {
                BreastSize = returnid(
                        spin_shapesizebreast.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_bleedingbwperiods.getSelectedItemPosition() > 0) {
                BleedingPeriods = returnid(
                        spin_bleedingbwperiods.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }

            if (spin_Bleedingmenopause.getSelectedItemPosition() > 0) {
                BleedingMenopause = returnid(
                        spin_Bleedingmenopause.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }
            if (spin_Bleedingintercourse.getSelectedItemPosition() > 0) {
                BleedingIntercourse = returnid(
                        spin_Bleedingintercourse.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }
            if (spin_Foulsmelling.getSelectedItemPosition() > 0) {
                VaginalDischarge = returnid(
                        spin_Foulsmelling.getSelectedItemPosition() - 1, 7,
                        global.getLanguage());
            }
            if (Score > 4 || Breath == 1 || Coughing == 1 || BloodinSputum == 1 || Fits == 1 || OpeningMouth == 1 || Ulcers == 1 || AnyChangeTone == 1 || LumpinBreast == 1 || BloodStainedNipple == 1 || BreastSize == 1 || BleedingPeriods == 1 || BleedingMenopause == 1 || BleedingIntercourse == 1 || VaginalDischarge == 1) {
                Status = 1;
            }
            String flag = "";
            String sql = "select count(*) from tblncdcbac where HHFamilyMemberGUID='"
                    + HHFamilyMemberGUID
                    + "' and CreatedOn='"
                    + Validate.getcurrentdate() + "'";
            int aa = dataProvider.getMaxRecord(sql);
            if (aa == 0) {
                NCDCBACGUID = Validate.random();
                flag = "I";
            } else {
                flag = "U";
                String sqlguid = "select NCDCBACGUID from tblncdcbac where HHFamilyMemberGUID='"
                        + HHFamilyMemberGUID
                        + "' and CreatedOn='"
                        + Validate.getcurrentdate() + "'";
                NCDCBACGUID = dataProvider.getRecord(sqlguid);
            }

            int ireturn = 0;
            ireturn = dataProvider.InsertNCDCBAC(VillageID, HHFamilyMemberGUID, HHSurveyGUID, NCDCBACGUID, Age,
                    Smoke, Alcohol, Waist, PhysicalActivity, FamilyHistory, Score,
                    Breath, Coughing, BloodinSputum, Fits, OpeningMouth, Ulcers,
                    AnyChangeTone, LumpinBreast, BloodStainedNipple, BreastSize, BleedingPeriods, BleedingMenopause,
                    BleedingIntercourse, VaginalDischarge,
                    Status, AshaID, ANMID, global.getUserID(), flag);
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
        btnok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (flag == 1) {
                    Intent i = new Intent(Ncd_Cbac.this, NCD_AA.class);
                    startActivity(i);
                }
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlertEdit(et_Waist, getResources().getString(R.string.enterwaist));
        } else if (iCheck == 3) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 4) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 5) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 6) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));

        } else if (iCheck == 7) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 8) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 9) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        } else if (iCheck == 10) {
            CustomAlertSpinner(spin_Consumegutka, getResources().getString(R.string.selectgender));
        }
    }

    public void CustomAlertEdit(final EditText et, String msg) {
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
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
                et.performClick();
                et.requestFocus();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void CustomAlertSpinner(final Spinner spin, String msg) {
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
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
                spin.performClick();
                spin.requestFocus();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

    }

}
