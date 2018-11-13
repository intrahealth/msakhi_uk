package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_DatesEd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint({"SimpleDateFormat", "InflateParams"})
public class FamilyDetails extends Activity {

    Button btnSave, btnClose;
    EditText etUID, etFamilyMemb, etAgeYearsTillApril, etAgeMonthTillApril,
            etMotherName, et_Other, et_Phone, et_other1,
            et_otheridentificationname, et_otheridentificationno;
    CheckBox chk1, chk2, chk3, chk4, chk5, chk6;
    Spinner SpinRelation, SpinGender, SpinEducation, SpinMaritalStatus,
            SpinTarget, SpinDOBAvailable, SpinFamilyMemberStatus,
            Spin_Occupation, Spin_Rsby_Beneficiary, Spin_Health_Condition,
            Spin_Any_PhysicalInability, Spin_OtherIdentification;
    Global global;
    Dialog datepic;
    TextView tvDateOfBirth, tvUID, tvAgeasonYear, tv_Registration_Date;
    TableRow tblDOB, tblAgeYearsTillApril, tblAgeMonthTillApril,
            tbltvAgeasonYear, tbl_Other, tbl_Other1, tbl_Any_HealthIssue,
            tbl_Registration_Date, tbl_Target, tbl_MotherName, tbl_otheridname,
            tbl_otheridno;
    DataProvider dataProvider;
    ArrayAdapter<String> adapter;
    int statusid = 0, statecode = 0, relationid = 0, agender = 0, diffInDays = 0, fillcount = 0;
    String Aadharold = "", DOB = "", auid = "", aname = "";
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();
    LinearLayout ll_jharkhand, ll_jharkhand1, ll_identificationjh;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.familydetails);
        fillcount = 0;
        btnSave = (Button) findViewById(R.id.btnSave);
        btnClose = (Button) findViewById(R.id.btnClose);
        etUID = (EditText) findViewById(R.id.etUID);
        etFamilyMemb = (EditText) findViewById(R.id.etFamilyMemb);
        tvDateOfBirth = (TextView) findViewById(R.id.tvDateOfBirth);
        SpinDOBAvailable = (Spinner) findViewById(R.id.SpinDOBAvailable);
        SpinFamilyMemberStatus = (Spinner) findViewById(R.id.SpinFamilyMemberStatus);
        SpinEducation = (Spinner) findViewById(R.id.SpinEducation);
        tvAgeasonYear = (TextView) findViewById(R.id.tvAgeasonYear);
        tvUID = (TextView) findViewById(R.id.tvUID);
        tblDOB = (TableRow) findViewById(R.id.tblDOB);
        tblAgeYearsTillApril = (TableRow) findViewById(R.id.tblAgeYearsTillApril);
        tblAgeMonthTillApril = (TableRow) findViewById(R.id.tblAgeMonthTillApril);
        tbltvAgeasonYear = (TableRow) findViewById(R.id.tbltvAgeasonYear);
        // add jitendra
        chk1 = (CheckBox) findViewById(R.id.chk1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);
        chk5 = (CheckBox) findViewById(R.id.chk5);
        chk6 = (CheckBox) findViewById(R.id.chk6);
        tv_Registration_Date = (TextView) findViewById(R.id.tv_Registration_Date);
        et_Other = (EditText) findViewById(R.id.et_other);
        et_Phone = (EditText) findViewById(R.id.et_Phone);
        et_other1 = (EditText) findViewById(R.id.et_other1);
        tbl_Other = (TableRow) findViewById(R.id.tbl_Other);
        tbl_Other1 = (TableRow) findViewById(R.id.tbl_Other1);
        tbl_otheridname = (TableRow) findViewById(R.id.tbl_otheridname);
        tbl_otheridno = (TableRow) findViewById(R.id.tbl_otheridno);
        Spin_Occupation = (Spinner) findViewById(R.id.Spin_Occupation);
        Spin_Rsby_Beneficiary = (Spinner) findViewById(R.id.Spin_Rsby_Beneficiary);
        Spin_Health_Condition = (Spinner) findViewById(R.id.Spin_Health_Condition);

        Spin_Any_PhysicalInability = (Spinner) findViewById(R.id.Spin_Any_PhysicalInability);
        Spin_OtherIdentification = (Spinner) findViewById(R.id.Spin_OtherIdentification);
        tbl_Any_HealthIssue = (TableRow) findViewById(R.id.tbl_Any_HealthIssue);
        tbl_Registration_Date = (TableRow) findViewById(R.id.tbl_Registration_Date);
        tbl_Target = (TableRow) findViewById(R.id.tbl_Target);
        tbl_MotherName = (TableRow) findViewById(R.id.tbl_MotherName);
        et_otheridentificationname = (EditText) findViewById(R.id.et_otheridentificationname);
        et_otheridentificationno = (EditText) findViewById(R.id.et_otheridentificationno);
        ll_jharkhand = (LinearLayout) findViewById(R.id.ll_jharkhand);
        ll_jharkhand1 = (LinearLayout) findViewById(R.id.ll_jharkhand1);
        ll_identificationjh = (LinearLayout) findViewById(R.id.ll_identificationjh);

        final Calendar c = Calendar.getInstance();
        int mTMYear = c.get(Calendar.YEAR);
        tvAgeasonYear.setText(String.valueOf(mTMYear));
        // tvAgeasonYear.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // ShowDatePicker1(tvAgeasonYear);
        // }
        // });
        tvDateOfBirth.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvDateOfBirth);

            }
        });
        tv_Registration_Date.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tv_Registration_Date);
            }
        });
        etAgeYearsTillApril = (EditText) findViewById(R.id.etAgeYearsTillApril);
        etAgeMonthTillApril = (EditText) findViewById(R.id.etAgeMonthTillApril);
        etMotherName = (EditText) findViewById(R.id.etMotherName);
        SpinRelation = (Spinner) findViewById(R.id.SpinRelation);
        SpinGender = (Spinner) findViewById(R.id.SpinGender);
        SpinMaritalStatus = (Spinner) findViewById(R.id.SpinMaritalStatus);
        SpinTarget = (Spinner) findViewById(R.id.SpinTarget);
        dataProvider = new DataProvider(this);

        etAgeMonthTillApril.addTextChangedListener(new TextWatcher() {

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
                if (s.length() > 0) {
                    int monthage = Integer.valueOf(etAgeMonthTillApril
                            .getText().toString());
                    if (monthage > 12) {
                        etAgeMonthTillApril.setText("");
                    }

                }
            }
        });
        etAgeYearsTillApril.addTextChangedListener(new TextWatcher() {

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
                if (s != null && s.length() > 0) {
                    int age = Integer.valueOf(etAgeYearsTillApril.getText()
                            .toString());
                    if (age < 12) {
                        tblAgeMonthTillApril.setVisibility(View.VISIBLE);
                    } else {
                        tblAgeMonthTillApril.setVisibility(View.GONE);
                        etAgeMonthTillApril.setText("");
                    }

                }
            }
        });
        global = (Global) this.getApplicationContext();
        Tracker t = ((Global) getApplication())
                .getTracker(TrackerName.APP_TRACKER);
        t.setScreenName("Family Details");
        String dimensionValue = "5";
        if (global.getsGlobalUserName() != null
                && global.getsGlobalUserName().length() > 0) {
            dimensionValue = global.getsGlobalUserName();
        }

        t.set("&userid", dimensionValue);
        t.enableAutoActivityTracking(true);

        t.send(new HitBuilders.EventBuilder().setCategory(dimensionValue)
                .setAction("User Sign In").setNewSession()
                .setCustomDimension(1, dimensionValue).build());
        t.send(new HitBuilders.ScreenViewBuilder().build());
        btnSave.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                int icheck = sCheckValidation();
                if (icheck == 1) {
                    CustomAlertSave(getResources().getString(R.string.saveque));

                } else {
                    showAlert(icheck);
                }

            }

        });

        btnClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                finish();

            }
        });

        tvAgeasonYear.addTextChangedListener(new TextWatcher() {

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
                if (s.length() == 4) {
                    int year = Integer.valueOf(s.toString());
                    final Calendar c = Calendar.getInstance();
                    int mTMYear = c.get(Calendar.YEAR);
                    if (year > mTMYear) {
                        CustomAlert(getResources()
                                .getString(R.string.validdate));
                        tvAgeasonYear.setText("2017");
                    }

                }
            }
        });

        fillCommonRecord(SpinGender, 4, global.getLanguage());
        fillCommonRecord(SpinRelation, 6, global.getLanguage());
        fillCommonRecord(SpinFamilyMemberStatus, 9, global.getLanguage());
        fillCommonRecord(SpinMaritalStatus, 5, global.getLanguage());
        fillCommonRecord(SpinTarget, 8, global.getLanguage());
        fillCommonRecord(SpinDOBAvailable, 7, global.getLanguage());
        fillCommonRecord(SpinEducation, 104, global.getLanguage());
        fillCommonRecord(Spin_Occupation, 38, global.getLanguage());
        fillCommonRecord(Spin_Rsby_Beneficiary, 7, global.getLanguage());
        fillCommonRecord(Spin_Health_Condition, 36, global.getLanguage());

        fillCommonRecord(Spin_Any_PhysicalInability, 7, global.getLanguage());
        fillCommonRecord(Spin_OtherIdentification, 39, global.getLanguage());

        SpinFamilyMemberStatus.setSelection(1);
        SpinFamilyMemberStatus.setEnabled(false);

        SpinDOBAvailable
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 1) {

                            tblDOB.setVisibility(View.VISIBLE);
                            tbltvAgeasonYear.setVisibility(View.GONE);
                            tvAgeasonYear.setText("");
                            tblAgeYearsTillApril.setVisibility(View.GONE);
                            etAgeYearsTillApril.setText("");
                            tblAgeMonthTillApril.setVisibility(View.GONE);
                            etAgeMonthTillApril.setText("");

                        } else if (pos == 2) {
                            tblDOB.setVisibility(View.GONE);
                            tvDateOfBirth.setText("");
                            tblAgeYearsTillApril.setVisibility(View.VISIBLE);
                            // tbltvAgeasonYear.setVisibility(View.VISIBLE);
                            final Calendar c = Calendar.getInstance();
                            int mTMYear = c.get(Calendar.YEAR);
                            tvAgeasonYear.setText(String.valueOf(mTMYear));
                            // tblAgeMonthTillApril.setVisibility(View.VISIBLE);
                        } else {
                            tblDOB.setVisibility(View.GONE);
                            tvDateOfBirth.setText("");
                            tbltvAgeasonYear.setVisibility(View.GONE);
                            tvAgeasonYear.setText("");
                            tblAgeYearsTillApril.setVisibility(View.GONE);
                            etAgeYearsTillApril.setText("");
                            tblAgeMonthTillApril.setVisibility(View.GONE);
                            etAgeMonthTillApril.setText("");
                        }

                    }

                    public void onNothingSelected(AdapterView<?> arg0) {

                    }
                });
        SpinGender.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                try {
                    if (position == 1) {
                        fillCommongender(SpinRelation, 6, global.getLanguage(),
                                position);
                    } else if (position == 2) {
                        fillCommongender(SpinRelation, 6, global.getLanguage(),
                                position);

                    } else {
                        fillCommonRecord(SpinRelation, 6, global.getLanguage());
                    }
                    if (fillcount == 1) {
                        SpinRelation.setSelection(relationid);
                        fillcount = 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        tvUID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    // start the scanning activity from the
                    // com.google.zxing.client.android.SCAN intent
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    // on catch, show the download dialog
                    showDialog(FamilyDetails.this,
                            getResources().getString(R.string.NoScannerFound),
                            getResources().getString(R.string.Downloadscanner),
                            getResources().getString(R.string.Yes),
                            getResources().getString(R.string.no)).show();
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

        Spin_Occupation.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                if (pos == 5) {
                    tbl_Other.setVisibility(view.VISIBLE);
                } else {
                    tbl_Other.setVisibility(view.GONE);
                    et_Other.setText("");
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
        Spin_OtherIdentification
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int pos, long id) {
                        if (pos == 4) {
                            tbl_otheridname.setVisibility(view.VISIBLE);
                            tbl_otheridno.setVisibility(view.VISIBLE);
                        } else if (pos == 0) {
                            tbl_otheridname.setVisibility(view.GONE);
                            tbl_otheridno.setVisibility(view.GONE);
                            et_otheridentificationname.setText("");
                            et_otheridentificationno.setText("");

                        } else {
                            tbl_otheridno.setVisibility(view.VISIBLE);
                            tbl_otheridname.setVisibility(view.GONE);
                            et_otheridentificationname.setText("");
                            et_otheridentificationno.setText("");
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

                statecode = Integer.valueOf(global.getStateCode());
            }
            if (statecode == 20) {
                tbl_Target.setVisibility(View.GONE);
                tbl_MotherName.setVisibility(View.GONE);
                ll_jharkhand1.setVisibility(View.VISIBLE);
                ll_jharkhand.setVisibility(View.VISIBLE);
                tbl_Registration_Date.setVisibility(View.VISIBLE);
                ll_identificationjh.setVisibility(View.VISIBLE);

            } else {
                ll_jharkhand.setVisibility(View.GONE);
                tbl_Registration_Date.setVisibility(View.GONE);
                ll_jharkhand1.setVisibility(View.GONE);
                ll_identificationjh.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (global.getGlobalHHFamilyMemberGUID() != null
                && global.getGlobalHHFamilyMemberGUID().length() > 0) {
            filldata();

        }

    }

    private int Weekcalculation() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date CurrDate, DOB;
        String dob1 = "", date = "";
        date = tvDateOfBirth.getText().toString();
        if (date != null && date.length() > 0) {
            dob1 = Validate.changeDateFormat(date);
        }
        // String CDob = "26-01-2016";
        try {
            CurrDate = sdf.parse(Validate.getcurrentdate());
            DOB = sdf.parse(dob1);
            // DOB = sdf.parse(CDob.toString());
            /*
             * LmpDate = sdf.parse("22-01-2016"); RegDate =
			 * sdf.parse("22-03-2016");
			 */
            diffInDays = (int) ((CurrDate.getTime() - DOB.getTime()) / (1000 * 60 * 60 * 24));
            diffInDays = diffInDays / 365;

            return diffInDays;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } // Yeh !! It's my date of birth :-)

    }

    private int agecalculation(String ss, String ss1) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date CurrDate, DOB;
        String dob1 = "", date = "";

        if (ss != null && ss.length() > 0) {
            dob1 = ss;
        }
        if (ss1 != null && ss1.length() > 0) {
            date = ss1;
        }
        // String CDob = "26-01-2016";
        try {
            CurrDate = sdf.parse(date);
            DOB = sdf.parse(dob1);
            // DOB = sdf.parse(CDob.toString());
            /*
             * LmpDate = sdf.parse("22-01-2016"); RegDate =
			 * sdf.parse("22-03-2016");
			 */
            diffInDays = (int) ((CurrDate.getTime() - DOB.getTime()) / (1000 * 60 * 60 * 24));
            int year = diffInDays / 365;

            return year;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } // Yeh !! It's my date of birth :-)

    }

    private int agecalculation(String ss, int ss1, int flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date CurrDate, DOB;
        String dob1 = "", date = "";

        if (ss != null && ss.length() > 0) {
            dob1 = ss;
        }

        // String CDob = "26-01-2016";
        try {
            CurrDate = sdf.parse(Validate.getcurrentdate());
            DOB = sdf.parse(dob1);
            // DOB = sdf.parse(CDob.toString());
            /*
             * LmpDate = sdf.parse("22-01-2016"); RegDate =
			 * sdf.parse("22-03-2016");
			 */
            diffInDays = (int) ((CurrDate.getTime() - DOB.getTime()) / (1000 * 60 * 60 * 24));
            int year = diffInDays / 365;
            if (flag == 1) {
                year = year - ss1;
            } else if (flag == 2) {
                year = ss1 - year;
            }


            return year;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } // Yeh !! It's my date of birth :-)

    }

    public void filldata() {
        try {
            HHFamilyMember = dataProvider.getHHFamilymemberData(
                    global.getGlobalHHSurveyGUID(),
                    global.getGlobalHHFamilyMemberGUID(), 2);
            if (HHFamilyMember != null && HHFamilyMember.size() > 0) {
                fillcount++;
                etUID.setText(String.valueOf(HHFamilyMember.get(0)
                        .getUniqueIDNumber()));
                etFamilyMemb.setText(String.valueOf(HHFamilyMember.get(0)
                        .getFamilyMemberName()));
                etAgeYearsTillApril.setText(String.valueOf(HHFamilyMember
                        .get(0).getAprilAgeYear()));
                etAgeMonthTillApril.setText(String.valueOf(HHFamilyMember
                        .get(0).getAprilAgeMonth()));
                etMotherName.setText(String.valueOf(HHFamilyMember.get(0)
                        .getMotherGUID()));
                tvDateOfBirth.setText(Validate.changeDateFormat(String
                        .valueOf(HHFamilyMember.get(0).getDateOfBirth())));
                tvAgeasonYear.setText(String.valueOf(HHFamilyMember.get(0)
                        .getAgeAsOnYear()));

                SpinGender.setSelection(returnPos(HHFamilyMember.get(0)
                        .getGenderID(), 4));
                relationid = returnPos1(HHFamilyMember.get(0).getRelationID(),
                        6, SpinGender.getSelectedItemPosition());
                SpinRelation.setSelection(relationid);
                SpinMaritalStatus.setSelection(returnPos(HHFamilyMember.get(0)
                        .getMaritialStatusID(), 5));
                SpinEducation.setSelection(returnPos(HHFamilyMember.get(0)
                        .getEducation(), 104));
                SpinTarget.setSelection(returnPos(HHFamilyMember.get(0)
                        .getTargetID(), 8));
                SpinDOBAvailable.setSelection(returnPos(HHFamilyMember.get(0)
                        .getDOBAvailable(), 7));
                SpinFamilyMemberStatus.setSelection(returnPos(HHFamilyMember
                        .get(0).getStatusID(), 9));
                statusid = returnPos(HHFamilyMember.get(0).getStatusID(), 9);
                Aadharold = String.valueOf(HHFamilyMember.get(0)
                        .getUniqueIDNumber());
                DOB = String.valueOf(HHFamilyMember.get(0).getDateOfBirth());
                tv_Registration_Date.setText(Validate
                        .changeDateFormat(HHFamilyMember.get(0)
                                .getRegistration_Date()));
                Spin_Occupation.setSelection(returnPos(HHFamilyMember.get(0)
                        .getOccupation(), 38));
                if (returnPos(HHFamilyMember.get(0).getOccupation(), 38) == 5) {
                    tbl_Other.setVisibility(View.VISIBLE);
                    et_Other.setText(String.valueOf(HHFamilyMember.get(0)
                            .getOccupation_Other()));
                }

                Spin_Health_Condition.setSelection(returnPos(HHFamilyMember
                        .get(0).getHealth_Condition(), 36));
                if (returnPos(HHFamilyMember.get(0).getHealth_Condition(), 36) == 2) {
                    tbl_Any_HealthIssue.setVisibility(View.VISIBLE);

                }

                Spin_Rsby_Beneficiary.setSelection(returnPos(HHFamilyMember
                        .get(0).getRsby_Beneficiary(), 7));
                Spin_Any_PhysicalInability.setSelection(returnPos(
                        HHFamilyMember.get(0).getAny_PhysicalInability(), 7));
                Spin_OtherIdentification.setSelection(returnPos(HHFamilyMember
                        .get(0).getOther_Id_Type(), 39));

                et_otheridentificationname.setText(HHFamilyMember.get(0)
                        .getOther_Id_Name());
                et_otheridentificationno.setText(HHFamilyMember.get(0)
                        .getOther_Id());
                et_Phone.setText(HHFamilyMember.get(0).getPhone_No());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private int returnPos1(int ID, int iFlag, int pos1) {
        // TODO Auto-generated method stub
        int pos = 0;
        Common = dataProvider.getCommonRecord1(1, iFlag, pos1);
        if (Common != null && Common.size() > 0) {
            for (int i = 0; i < Common.size(); i++) {
                if (Common.get(i).getID() == ID) {
                    pos = i + 1;
                }
            }
        }
        return pos;
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        finish();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, global.getsGlobalAshaName()).setShowAsAction(
                MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // menu.add(0, 1, 0, "History").setIcon(R.drawable.logout1)
        // .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        //

        return true;
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

    private void fillCommongender(Spinner spin, int iflag, int Language, int pos) {
        Common = dataProvider.getCommonRecord1(Language, iflag, pos);

        String sValue[] = new String[Common.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < Common.size(); i++) {
            sValue[i + 1] = Common.get(i).getValue();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spin.setAdapter(adapter);
    }

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

    }

    public int returnid1(int POS, int iFlag, int Language, int pos) {
        Common = dataProvider.getCommonRecord1(Language, iFlag, pos);

        return Common.get(POS).getID();

    }

    public void ShowDatePicker(final TextView tVvisit) {
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
        try {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -99);
            date = cal.getTime();
            datetext.setMinDate((long) date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void SaveFamilyDetails() {
        String UID = "";
        String FamilyMemb = "";
        String DateOfBirth = "";
        int DOBAvailable = 0;
        int AgeYearTillApril = 0;
        int AgeMonthTillApril = 0;
        int AgeAsOnYear = 0;
        String MotherName = "";
        int Relationid = 0;
        int Genderid = 0;
        int MaritalStatusid = 0;
        int Targetid = 0;
        String UpdatedOn = "";
        int Education = 0;

        UpdatedOn = Validate.getcurrentdate();
        int FamilyMemberStatus = 0;
        String CurrentDate = "";
        int AshaID = 0;
        int ANMID = 0;
        // add jitendra
        String Registration_Date = "";
        int Occupation = 0;
        int Rsby_Beneficiary = 0;
        int Health_Condition = 0;
        String Any_HealthIssue = "";
        int Any_PhysicalInability = 0;
        String Occupation_Other = "";
        String Phone_No = "";
        String Any_HealthIssue_Other = "";
        int Other_Id_Type = 0;
        String Other_Id_Name = "";
        String Other_Id = "";
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
        }
        CurrentDate = Validate.getcurrentdate();

        if (etUID.getText().toString().length() > 0) {
            UID = etUID.getText().toString();
        }
        if (etFamilyMemb.getText().length() > 0) {
            FamilyMemb = String.valueOf(etFamilyMemb.getText());
        }
        if (tvDateOfBirth.getText().toString().length() > 0) {
            DateOfBirth = Validate.changeDateFormat(tvDateOfBirth.getText()
                    .toString());
        }
        if (etAgeYearsTillApril.getText().toString().length() > 0) {
            AgeYearTillApril = Integer.valueOf(etAgeYearsTillApril.getText()
                    .toString());
        }
        if (etAgeMonthTillApril.getText().toString().length() > 0) {
            AgeMonthTillApril = Integer.valueOf(etAgeMonthTillApril.getText()
                    .toString());
        }
        if (tvAgeasonYear.getText().toString().length() > 0) {
            AgeAsOnYear = Integer.valueOf(tvAgeasonYear.getText().toString());
        }
        if (etMotherName.getText().length() > 0) {
            MotherName = String.valueOf(etMotherName.getText());
        }

        if (SpinRelation.getSelectedItemPosition() > 0) {
            Relationid = returnid1(SpinRelation.getSelectedItemPosition() - 1,
                    6, global.getLanguage(),
                    SpinGender.getSelectedItemPosition());
        }
        if (SpinGender.getSelectedItemPosition() > 0) {
            Genderid = returnid(SpinGender.getSelectedItemPosition() - 1, 4,
                    global.getLanguage());
        }
        if (SpinDOBAvailable.getSelectedItemPosition() > 0) {
            DOBAvailable = returnid(
                    SpinDOBAvailable.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }

        if (SpinEducation.getSelectedItemPosition() > 0) {
            Education = returnid(SpinEducation.getSelectedItemPosition() - 1,
                    104, global.getLanguage());
        }

        if (SpinFamilyMemberStatus.getSelectedItemPosition() > 0) {
            FamilyMemberStatus = returnid(
                    SpinFamilyMemberStatus.getSelectedItemPosition() - 1, 9,
                    global.getLanguage());
        }
        if (SpinMaritalStatus.getSelectedItemPosition() > 0) {
            MaritalStatusid = returnid(
                    SpinMaritalStatus.getSelectedItemPosition() - 1, 5,
                    global.getLanguage());
        }
        if (SpinTarget.getSelectedItemPosition() > 0) {
            Targetid = returnid(SpinTarget.getSelectedItemPosition() - 1, 8,
                    global.getLanguage());
        }

        Registration_Date = Validate.changeDateFormat(tv_Registration_Date
                .getText().toString());
        if (Spin_Occupation.getSelectedItemPosition() > 0) {
            Occupation = returnid(
                    Spin_Occupation.getSelectedItemPosition() - 1, 38,
                    global.getLanguage());
        }
        if (Spin_Rsby_Beneficiary.getSelectedItemPosition() > 0) {
            Rsby_Beneficiary = returnid(
                    Spin_Rsby_Beneficiary.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (Spin_Health_Condition.getSelectedItemPosition() > 0) {
            Health_Condition = returnid(
                    Spin_Health_Condition.getSelectedItemPosition() - 1, 36,
                    global.getLanguage());
        }

        Any_HealthIssue = savecheckbox();

        if (Spin_Any_PhysicalInability.getSelectedItemPosition() > 0) {
            Any_PhysicalInability = returnid(
                    Spin_Any_PhysicalInability.getSelectedItemPosition() - 1,
                    7, global.getLanguage());
        }
        if (Spin_OtherIdentification.getSelectedItemPosition() > 0) {
            Other_Id_Type = returnid(
                    Spin_OtherIdentification.getSelectedItemPosition() - 1, 39,
                    global.getLanguage());
        }
        Occupation_Other = et_Other.getText().toString();
        Other_Id_Name = et_otheridentificationname.getText().toString();
        Other_Id = et_otheridentificationno.getText().toString();
        if (et_Phone.getText().toString().length() > 0) {
            Phone_No = et_Phone.getText().toString();
        }
        Any_HealthIssue_Other = et_other1.getText().toString();
        Calendar c1 = Calendar.getInstance();
        if (global.getGlobalHHSurveyGUID() != null
                && global.getGlobalHHSurveyGUID().length() > 0) {
            if (global.getGlobalHHFamilyMemberGUID().length() == 0) {
                String FamilyGUID = Validate.random();
                dataProvider.InsertFamilyDetails(Education, AshaID, ANMID,
                        CurrentDate, UID, FamilyMemb, DOBAvailable,
                        DateOfBirth, AgeYearTillApril, AgeMonthTillApril,
                        AgeAsOnYear, MotherName, Relationid,
                        FamilyMemberStatus, Genderid, MaritalStatusid,
                        Targetid, UpdatedOn, global.getGlobalHHSurveyGUID(),
                        FamilyGUID, global.getsGlobalUserID(),
                        Registration_Date, Occupation, Rsby_Beneficiary,
                        Health_Condition, Any_HealthIssue,
                        Any_PhysicalInability, Occupation_Other, Phone_No,
                        Any_HealthIssue_Other, Other_Id_Type, Other_Id_Name,
                        Other_Id);
                // global.setGlobalHHFamilyMemberGUID(FamilyGUID);
            } else {
                dataProvider.UpdateFamilyDetails(Education, AshaID, ANMID,
                        CurrentDate, UID, FamilyMemb, DOBAvailable,
                        DateOfBirth, AgeYearTillApril, AgeMonthTillApril,
                        AgeAsOnYear, MotherName, Relationid,
                        FamilyMemberStatus, Genderid, MaritalStatusid,
                        Targetid, UpdatedOn, global.getGlobalHHSurveyGUID(),
                        global.getGlobalHHFamilyMemberGUID(),
                        global.getsGlobalUserID(), Registration_Date,
                        Occupation, Rsby_Beneficiary, Health_Condition,
                        Any_HealthIssue, Any_PhysicalInability,
                        Occupation_Other, Phone_No, Any_HealthIssue_Other,
                        Other_Id_Type, Other_Id_Name, Other_Id);
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.US);
            String formattedDate = df.format(c1.getTime());
            String sql = "insert into tblhhupdate_Log(hhsurveyguid,hhmemberguid,UserID,StatusId_Old,StatusId_New,Aadhar_Old,Aadhar_New,DOB_Old,DOB_New,UpdatedOn,IsEdited)values('"
                    + global.getGlobalHHSurveyGUID()
                    + "','"
                    + global.getGlobalHHFamilyMemberGUID()
                    + "',"
                    + global.getsGlobalUserID()
                    + ","
                    + statusid
                    + ","
                    + FamilyMemberStatus
                    + ",'"
                    + Aadharold
                    + "','"
                    + UID
                    + "','"
                    + DOB
                    + "','"
                    + DateOfBirth
                    + "','"
                    + formattedDate
                    + "',1)";

            dataProvider.executeSql(sql);
        }
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

    public void CustomAlert(String msg) {
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
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void CustomAlerts(String msg) {
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
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                finish();

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void CustomAlertSave(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
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

        btnyes.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                SaveFamilyDetails();
                CustomAlerts(getResources()
                        .getString(R.string.savesuccessfully));

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

    public int sCheckValidation() {
        int age = 0;
        if (etUID.getText().toString() != null
                && etUID.getText().toString().length() >= 0
                && etUID.getText().toString().length() != 12) {
            return 6;
        }
        if (Spin_OtherIdentification.getSelectedItemPosition() == 4
                && et_otheridentificationname.getText().toString().length() == 0) {
            return 11;
        }
        if (Spin_OtherIdentification.getSelectedItemPosition() > 0
                && et_otheridentificationno.getText().toString().length() == 0) {
            return 12;
        }
        if (etFamilyMemb.getText().toString() != null
                && etFamilyMemb.getText().toString().length() == 0) {
            return 8;
        }

        if (SpinGender.getSelectedItemPosition() == 0) {
            return 9;
        }
        if (SpinRelation.getSelectedItemPosition() == 0) {
            return 10;
        }
        if (SpinRelation.getSelectedItemPosition() > 0) {
            int relation = returnid1(
                    SpinRelation.getSelectedItemPosition() - 1, 6,
                    global.getLanguage(), SpinGender.getSelectedItemPosition());
            if (relation == 1) {

                if (global.getGlobalHHFamilyMemberGUID() != null
                        && global.getGlobalHHFamilyMemberGUID().length() > 0) {

                    String sql = "Select RelationID  from  Tbl_HHFamilyMember where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID()
                            + "' and RelationID= " + relation + " and StatusID!=2";
                    int count = dataProvider.getMaxRecord(sql);
                    String rfid = "Select RelationID from Tbl_HHFamilyMember where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID()
                            + "' and HHFamilyMemberGUID = '"
                            + global.getGlobalHHFamilyMemberGUID() + "' and StatusID!=2 ";
                    int RFid = dataProvider.getMaxRecord(rfid);
                    if (count != RFid && count > 0) {
                        return 10;
                    }
                } else {
                    String sql = "Select RelationID  from  Tbl_HHFamilyMember where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID()
                            + "' and RelationID= " + relation + " ";
                    int count = dataProvider.getMaxRecord(sql);
                    if (global.getGlobalHHFamilyMemberGUID().length() == 0
                            && count > 0) {
                        return 10;
                    }
                }
            }
        }
        if (SpinMaritalStatus.getSelectedItemPosition() == 0) {
            return 17;
        }
        if (etAgeYearsTillApril.getText() != null
                && etAgeYearsTillApril.getText().length() > 0) {
            age = Integer.valueOf(etAgeYearsTillApril.getText().toString());
        }
        if (tblDOB.getVisibility() == View.VISIBLE
                && tvDateOfBirth.getText().toString().length() == 0) {
            return 2;
        }
        if (tblAgeYearsTillApril.getVisibility() == View.VISIBLE
                && etAgeYearsTillApril.getText().length() == 0) {

            return 4;
        }
        if (SpinDOBAvailable.getSelectedItemPosition() == 2
                && tvAgeasonYear.getText().toString().length() == 0) {
            return 5;
        }
        if (SpinDOBAvailable.getSelectedItemPosition() == 0) {
            return 2;
        }

        if (age < 12 && tblAgeMonthTillApril.getVisibility() == View.VISIBLE
                && etAgeMonthTillApril.getText().length() == 0) {
            return 7;
        }
        if (SpinRelation.getSelectedItemPosition() > 0) {
            int relation = returnid1(
                    SpinRelation.getSelectedItemPosition() - 1, 6,
                    global.getLanguage(), SpinGender.getSelectedItemPosition());
            if (relation == 1) {

                if (SpinDOBAvailable.getSelectedItemPosition() == 2 && age < 20) {

                    return 13;
                }
                if (SpinDOBAvailable.getSelectedItemPosition() == 1) {
                    int year = Weekcalculation();
                    if (year < 20) {
                        return 13;
                    }
                }


            }
            String sql = "Select RelationID  from  Tbl_HHFamilyMember where HHSurveyGUID='"
                    + global.getGlobalHHSurveyGUID()
                    + "' and RelationID= 1 and StatusID!=2";
            int count = dataProvider.getMaxRecord(sql);
            if (count >= 1) {


                if (relation == 2 || relation == 3 || relation == 4 || relation == 5) {
                    String sqldob = "Select DOBAvailable  from  Tbl_HHFamilyMember where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID()
                            + "' and RelationID= 1 and StatusID!=2";
                    int DOBAvailable = dataProvider.getMaxRecord(sqldob);
                    if (DOBAvailable == 1 && SpinDOBAvailable.getSelectedItemPosition() == 1) {
                        String sdob = "Select DateOfBirth  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + "  and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        String DOB = dataProvider.getRecord(sdob);
                        int year = agecalculation(Validate.changeDateFormat(tvDateOfBirth.getText().toString()), DOB);
                        if (year < 15) {
                            return 14;
                        }
                    } else if (DOBAvailable == 2 && SpinDOBAvailable.getSelectedItemPosition() == 2) {
                        String sdob = "Select AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear)  as age  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        int year = dataProvider.getMaxRecord(sdob);
                        int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        if ((ageyear - year) < 15) {
                            return 14;
                        }
                    }
                    if (DOBAvailable == 1 && SpinDOBAvailable.getSelectedItemPosition() == 2) {
                        String sdob = "Select DateOfBirth  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        String DOB = dataProvider.getRecord(sdob);
                        int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        int year = agecalculation(DOB, ageyear, 2);
                        if (year < 15) {
                            return 14;
                        }

                    } else if (DOBAvailable == 2 && SpinDOBAvailable.getSelectedItemPosition() == 1) {
                        String sdob = "Select AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear)  as age  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        int ageyear = dataProvider.getMaxRecord(sdob);
                        // int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        int year = agecalculation(Validate.changeDateFormat(tvDateOfBirth.getText().toString()), ageyear, 1);
                        if (year < 15) {
                            return 14;
                        }
                    } else if (DOBAvailable == 0) {
                        return 15;
                    }
                } else if (relation == 9 || relation == 10 || relation == 11 || relation == 12) {
                    String sqldob = "Select DOBAvailable  from  Tbl_HHFamilyMember where HHSurveyGUID='"
                            + global.getGlobalHHSurveyGUID()
                            + "' and RelationID= 1 and StatusID!=2";
                    int DOBAvailable = dataProvider.getMaxRecord(sqldob);
                    if (DOBAvailable == 1 && SpinDOBAvailable.getSelectedItemPosition() == 1) {
                        String sdob = "Select DateOfBirth  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + "  and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        String DOB = dataProvider.getRecord(sdob);
                        int year = agecalculation(DOB, Validate.changeDateFormat(tvDateOfBirth.getText().toString()));
                        if (year < 15) {
                            return 16;
                        }
                    } else if (DOBAvailable == 2 && SpinDOBAvailable.getSelectedItemPosition() == 2) {
                        String sdob = "Select AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear)  as age  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        int year = dataProvider.getMaxRecord(sdob);
                        int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        if ((year - ageyear) < 15) {
                            return 16;
                        }
                    }
                    if (DOBAvailable == 1 && SpinDOBAvailable.getSelectedItemPosition() == 2) {
                        String sdob = "Select DateOfBirth  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        String DOB = dataProvider.getRecord(sdob);
                        int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        int year = agecalculation(DOB, ageyear, 1);
                        if (year < 15) {
                            return 16;
                        }

                    } else if (DOBAvailable == 2 && SpinDOBAvailable.getSelectedItemPosition() == 1) {
                        String sdob = "Select AprilAgeYear+(strftime('%Y', 'now')-AgeAsOnYear)  as age  from  Tbl_HHFamilyMember where DOBAvailable=" + DOBAvailable + " and HHSurveyGUID='"
                                + global.getGlobalHHSurveyGUID()
                                + "' and RelationID= 1 and StatusID!=2";
                        int ageyear = dataProvider.getMaxRecord(sdob);
                        // int ageyear = Integer.valueOf(etAgeYearsTillApril.getText().toString());
                        int year = agecalculation(Validate.changeDateFormat(tvDateOfBirth.getText().toString()), ageyear, 2);
                        if (year < 15) {
                            return 16;
                        }
                    } else if (DOBAvailable == 0) {
                        return 15;
                    }
                }
            }


        }

        return 1;
    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlert(getResources().getString(R.string.enterdob));
        } else if (iCheck == 4) {
            CustomAlert(getResources().getString(R.string.dateenterage));
        } else if (iCheck == 5) {
            CustomAlert(getResources().getString(R.string.enterage));
        } else if (iCheck == 6) {
            CustomAlert(getResources().getString(R.string.enter12digituid));
        } else if (iCheck == 7) {
            CustomAlert(getResources().getString(R.string.dateenteragemonth));
        } else if (iCheck == 8) {
            CustomAlert(getResources().getString(R.string.enterfmname));

        } else if (iCheck == 9) {
            CustomAlert(getResources().getString(R.string.selectgender));
        } else if (iCheck == 10) {
            CustomAlert(getResources().getString(R.string.selectotherrelation));
        } else if (iCheck == 11) {
            CustomAlert(getResources().getString(
                    R.string.enterIdentificationName));
        } else if (iCheck == 12) {
            CustomAlert(getResources()
                    .getString(R.string.enterIdentificationNo));
        } else if (iCheck == 13) {
            CustomAlert(getResources().getString(R.string.validationdob));
        } else if (iCheck == 14) {
            CustomAlert(getResources().getString(
                    R.string.househealdheadagegreatertahn15));
        } else if (iCheck == 15) {
            CustomAlert(getResources()
                    .getString(R.string.househealdheadage));
        } else if (iCheck == 16) {
            CustomAlert(getResources()
                    .getString(R.string.househealdheadagelesstahn15));
        } else if (iCheck == 17) {
            CustomAlert(getString(R.string.PleaseselectMaritalStatus));
        }
    }

    public void ShowDatePicker1(final TextView txt) {
        datepic = new Dialog(this, R.style.CustomTheme);
        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);
        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        Button btnCancle = (Button) datepic.findViewById(R.id.cancle);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        Date date;
        Calendar c = Calendar.getInstance(Locale.ENGLISH);
        btnCancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });
        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        if (txt != null && txt.getText() != ""
                && txt.getText().toString().length() > 0) {

            try {
                datetext.clearFocus();
                date = sdf.parse(txt.getText().toString());
                c.setTime(date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // set current date into datepicker
            datetext.init(year, month, day, null);

        }
        btnset.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("unused")
            @Override
            public void onClick(View v) {
                datetext.clearFocus();
                int Mdate = datetext.getDayOfMonth();

                int month = datetext.getMonth();
                int year = datetext.getYear();
                Date date = null;
                String sSellectedDate = String.valueOf(year);
                txt.setText(sSellectedDate);

                // TODO Auto-generated

                datepic.dismiss();

            }
        });
        datepic.show();

    }

    // alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act,
                                          CharSequence title, CharSequence message, CharSequence buttonYes,
                                          CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://search?q=pname:"
                                + "com.google.zxing.client.android");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            act.startActivity(intent);
                        } catch (ActivityNotFoundException anfe) {

                        }
                    }
                });
        downloadDialog.setNegativeButton(buttonNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return downloadDialog.show();
    }

    // on ActivityResult method
    @SuppressWarnings("unused")
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // get the extras that are returned from the intent
                String UID = "";
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                // etUID.setText(show(contents));
                show(contents);
            }
        }
    }

    private void show(String XMLData) {
        String Value1 = "";
        try {
            String Value = "";
            if (XMLData.length() > 0) {
                XMLData.replace("/>", "");

                String[] Cdt = XMLData.split(" ");
                for (int i = 0; i < Cdt.length; i++) {
                    if (Cdt[i].contains("uid")) {
                        Value = Cdt[i];
                        String[] Cdt1 = Value.split("=");
                        Value1 = Cdt1[1].replace("\"", "");
                        etUID.setText(Value1);
                    }
                    if (Cdt[i].contains("name")) {
                        Value = Cdt[i];
                        String[] Cdt1 = Value.split("=");
                        Value1 = Cdt1[1].replace("\"", "");
                        if (!Cdt[i + 1].contains("=")) {
                            Value1 = Value1 + " "
                                    + Cdt[i + 1].replace("\"", "");
                        }
                        etFamilyMemb.setText(Value1);
                    }
                    if (Cdt[i].contains("gender")) {
                        Value = Cdt[i];
                        String[] Cdt1 = Value.split("=");
                        Value1 = Cdt1[1];
                        if (Value1.contains("M")) {
                            SpinGender.setSelection(1);
                        } else if (Value1.contains("F")) {
                            SpinGender.setSelection(2);
                        }
                    }
                    if (Cdt[i].contains("yob")) {
                        Value = Cdt[i];
                        String[] Cdt1 = Value.split("=");
                        SpinDOBAvailable.setSelection(2);
                        Value1 = Cdt1[1].replace("\"", "");
                        Calendar now = Calendar.getInstance(); // Gets the
                        // current date
                        // and time
                        int year = now.get(Calendar.YEAR);
                        year = year - (Integer.parseInt(Value1));
                        etAgeYearsTillApril.setText("" + year);

                    }
                    if (Cdt[i].contains("dob")) {
                        Value = Cdt[i];
                        String[] Cdt1 = Value.split("=");

                        SpinDOBAvailable.setSelection(1);
                        Value1 = Cdt1[1].replace("/>", "");
                        Value1 = Value1.replace("/", "-");
                        Value1 = Value1.replace("\"", "");
                        tvDateOfBirth.setText(Validate.chkDate(Value1));
                        etAgeYearsTillApril.setText("");

                    }
                    // String date = YYYY + "-" + MM + "-" + DD ;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
