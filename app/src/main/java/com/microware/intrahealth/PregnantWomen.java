package com.microware.intrahealth;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.adapter.DropDownListAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({"SimpleDateFormat", "DefaultLocale", "InflateParams"})
public class PregnantWomen extends Activity {
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    static String iMagename;
    String datenewcheckup4, datenewcheckup3, datenewcheckup2, datenewtt2,
            datenew, datenewcheckup1, datenewtt1;
    static TextView tvImageView;
    ArrayAdapter<String> adapter;
    Spinner spinpayrcvd, spinBeneficiary, spinlastPregPlace,
            spinlasttolastPregPlace, spinBldgrp, spinregweek,
            spinLasttoLastPregoutcome, spinLastPregoutcome,
            spinLasttoLastPregComplications, spinLastPregComplications,
            spinLastPregcause2, spinLastPregcause, spinBankAccount,
            spinfacility1, spinfacility, spinhusbandname, spinEducation;
    ArrayList<Tbl_HHFamilyMember> HHFamilyMemberspin = new ArrayList<Tbl_HHFamilyMember>();
    DataProvider dataProvider;
    Global global;
    CheckBox chk1, chk2, chk3, chk4, chk5, chk6, chk7, chk8, chk9, chk10,
            chk11;
    ImageView Imgview;
    Button btnSave;
    ImageView btnCapture;
    Dialog datepic;
    DatePicker datetext;
    TableRow tblreg, tblpay, tblaccountno, tblifsccode;
    String Accno = "";
    String Phoneno = "";
    String ifsccode = "";
    @SuppressWarnings("unused")
    private PopupWindow pwindo;
    String HRP = "";
    String condition = "";
    private static final String IMAGE_DIRECTORY_NAME = "mSakhi";
    private Uri fileUri;
    TextView tvimage, tvDate, etwomenname, tvedd, etdorjsy, tvregweek;
    Dialog ImageDialog;
    TableRow lastPreg, lasttolastPreg, lastPregOutcome, lasttolastPregOutcome,
            lastPregplace, lasttolastPregplace, tblanyother, lastPregtbl2,
            lastPregtbl, lastPregcause2, lastPregcause, tblfacilitytype,
            tblfacilitytype1, tblfacility, tblfacility1;
    EditText ethusbandname, etnoofpregnancy, etmctsid, etmobileno, etaccno,
            etifsccode, etwopg, tvage, etanyother, etAnyotherCompl2,
            etAnyotherCompl, etfacility1, etfacility, etaltmobileno;
    String newdate = "";
    GridView fillGrid;
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    int HishRisk = 0;
    ArrayList<MstCommon> Common = new ArrayList<MstCommon>();
    ArrayList<Tbl_HHFamilyMember> data = new ArrayList<Tbl_HHFamilyMember>();
    ArrayList<tbl_pregnantwomen> record = new ArrayList<tbl_pregnantwomen>();

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.preg_reg1);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        spinBeneficiary = (Spinner) findViewById(R.id.spinBeneficiary);
        spinEducation = (Spinner) findViewById(R.id.spinEducation);
        spinpayrcvd = (Spinner) findViewById(R.id.spinpayrcvd);
        spinBankAccount = (Spinner) findViewById(R.id.spinBankAccount);
        spinhusbandname = (Spinner) findViewById(R.id.spinhusbandname);
        chk1 = (CheckBox) findViewById(R.id.chk1);
        tblfacilitytype = (TableRow) findViewById(R.id.tblfacilitytype);
        tblfacility = (TableRow) findViewById(R.id.tblfacility);
        tblfacilitytype1 = (TableRow) findViewById(R.id.tblfacilitytype1);
        tblfacility1 = (TableRow) findViewById(R.id.tblfacility1);
        chk2 = (CheckBox) findViewById(R.id.chk2);
        chk3 = (CheckBox) findViewById(R.id.chk3);
        chk4 = (CheckBox) findViewById(R.id.chk4);
        chk5 = (CheckBox) findViewById(R.id.chk5);
        chk6 = (CheckBox) findViewById(R.id.chk6);
        chk7 = (CheckBox) findViewById(R.id.chk7);
        chk8 = (CheckBox) findViewById(R.id.chk8);
        chk9 = (CheckBox) findViewById(R.id.chk9);
        chk10 = (CheckBox) findViewById(R.id.chk10);
        chk11 = (CheckBox) findViewById(R.id.chk11);
        spinfacility1 = (Spinner) findViewById(R.id.spinfacility1);
        spinfacility = (Spinner) findViewById(R.id.spinfacility);
        spinlastPregPlace = (Spinner) findViewById(R.id.spinlastPregPlace);
        spinlasttolastPregPlace = (Spinner) findViewById(R.id.spinlasttolastPregPlace);
        spinLasttoLastPregoutcome = (Spinner) findViewById(R.id.spinLasttoLastPregoutcome);
        spinLastPregoutcome = (Spinner) findViewById(R.id.spinLastPregoutcome);
        spinLasttoLastPregComplications = (Spinner) findViewById(R.id.spinLasttoLastPregComplications);
        spinLastPregcause2 = (Spinner) findViewById(R.id.spinLastPregcause2);
        spinLastPregcause = (Spinner) findViewById(R.id.spinLastPregcause);
        spinLastPregComplications = (Spinner) findViewById(R.id.spinLastPregComplications);
        spinBldgrp = (Spinner) findViewById(R.id.spinBldgrp);
        tblreg = (TableRow) findViewById(R.id.tblreg);
        lastPregcause2 = (TableRow) findViewById(R.id.lastPregcause2);
        lastPregcause = (TableRow) findViewById(R.id.lastPregcause);
        lastPregtbl2 = (TableRow) findViewById(R.id.lastPregtbl2);
        lastPregtbl = (TableRow) findViewById(R.id.lastPregtbl);
        tblanyother = (TableRow) findViewById(R.id.tblanyother);
        tblaccountno = (TableRow) findViewById(R.id.tblaccountno);
        tblifsccode = (TableRow) findViewById(R.id.tblifsccode);
        tblpay = (TableRow) findViewById(R.id.tblpay);
        btnCapture = (ImageView) findViewById(R.id.btnCapture);
        btnSave = (Button) findViewById(R.id.btnSave);
        tvimage = (TextView) findViewById(R.id.tvimage);
        Imgview = (ImageView) findViewById(R.id.Imgview);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvedd = (TextView) findViewById(R.id.tvedd);
        ethusbandname = (EditText) findViewById(R.id.ethusbandname);
        etmctsid = (EditText) findViewById(R.id.etmctsid);
        etmobileno = (EditText) findViewById(R.id.etmobileno);
        etaccno = (EditText) findViewById(R.id.etaccno);
        etaltmobileno = (EditText) findViewById(R.id.etaltmobileno);
        lastPreg = (TableRow) findViewById(R.id.lastPreg);
        lasttolastPreg = (TableRow) findViewById(R.id.lasttolastPreg);
        lastPregOutcome = (TableRow) findViewById(R.id.lastPregOutcome);
        lasttolastPregOutcome = (TableRow) findViewById(R.id.lasttolastPregOutcome);
        lastPregplace = (TableRow) findViewById(R.id.lastPregplace);
        lasttolastPregplace = (TableRow) findViewById(R.id.lasttolastPregplace);
        etwomenname = (TextView) findViewById(R.id.etwomenname);
        etdorjsy = (TextView) findViewById(R.id.etdorjsy);
        tvage = (EditText) findViewById(R.id.tvage);
        spinregweek = (Spinner) findViewById(R.id.spinregweek);
        etnoofpregnancy = (EditText) findViewById(R.id.etnoofpregnancy);
        etwopg = (EditText) findViewById(R.id.etwopg);
        etifsccode = (EditText) findViewById(R.id.etifsccode);
        etfacility1 = (EditText) findViewById(R.id.etfacility1);
        etfacility = (EditText) findViewById(R.id.etfacility);
        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        etanyother = (EditText) findViewById(R.id.etanyother);
        etAnyotherCompl2 = (EditText) findViewById(R.id.etAnyotherCompl2);
        etAnyotherCompl = (EditText) findViewById(R.id.etAnyotherCompl);
        tblanyother.setVisibility(View.GONE);
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                Tracker t = ((Global) getApplication())
                        .getTracker(TrackerName.APP_TRACKER);
                t.setScreenName("Pregnant Women Registration");
                t.send(new HitBuilders.AppViewBuilder().build());
            } catch (Exception e) {
            }
        }
        fillCommonRecord(spinBeneficiary, 7, global.getLanguage());
        fillCommonRecord(spinpayrcvd, 7, global.getLanguage());
        fillCommonRecord(spinBankAccount, 7, global.getLanguage());
        fillCheckboxValues(chk1, 0, 20, global.getLanguage());
        fillCheckboxValues(chk2, 1, 20, global.getLanguage());
        fillCheckboxValues(chk3, 2, 20, global.getLanguage());
        fillCheckboxValues(chk4, 3, 20, global.getLanguage());
        fillCheckboxValues(chk5, 4, 20, global.getLanguage());
        fillCheckboxValues(chk6, 5, 20, global.getLanguage());
        fillCheckboxValues(chk7, 6, 20, global.getLanguage());
        fillCheckboxValues(chk8, 7, 20, global.getLanguage());
        fillCheckboxValues(chk9, 8, 20, global.getLanguage());
        fillCheckboxValues(chk10, 9, 20, global.getLanguage());
        fillCheckboxValues(chk11, 10, 20, global.getLanguage());
        fillCommonRecord(spinlastPregPlace, 12, global.getLanguage());
        fillCommonRecord(spinlasttolastPregPlace, 12, global.getLanguage());
        fillCommonRecord(spinBldgrp, 14, global.getLanguage());
        fillCommonRecord(spinregweek, 7, global.getLanguage());
        fillCommonRecord(spinLasttoLastPregComplications, 15,
                global.getLanguage());
        fillCommonRecord(spinfacility, 27, global.getLanguage());
        fillCommonRecord(spinfacility1, 27, global.getLanguage());
        fillCommonRecord(spinLastPregComplications, 15, global.getLanguage());
        fillCommonRecord(spinLastPregoutcome, 16, global.getLanguage());
        fillCommonRecord(spinLasttoLastPregoutcome, 16, global.getLanguage());
        fillCommonRecord(spinEducation, 104, global.getLanguage());
        spinfacility.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0) {
                    if (returnid(position - 1, 27, global.getLanguage()) == 9) {
                        tblfacility.setVisibility(View.VISIBLE);
                    } else {
                        tblfacility.setVisibility(View.GONE);
                        etfacility.setText("");
                    }
                } else {
                    tblfacility.setVisibility(View.GONE);
                    etfacility.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        if (global.getsGlobalPWGUID() != null
                && global.getsGlobalPWGUID().length() > 0) {
            String sql = "select CheckupVisitDate  from tblANCVisit where Visit_No=1 and PWGUID='"
                    + global.getsGlobalPWGUID() + "'";
            String res = dataProvider.getRecord(sql);
            if (res != null && !res.equalsIgnoreCase("null")
                    && res.length() > 0) {
                tvDate.setEnabled(false);
            }
        }
        spinfacility1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position > 0) {
                    if (returnid(position - 1, 27, global.getLanguage()) == 9) {

                        tblfacility1.setVisibility(View.VISIBLE);
                    } else {
                        tblfacility1.setVisibility(View.GONE);
                        etfacility1.setText("");
                    }
                } else {
                    tblfacility1.setVisibility(View.GONE);
                    etfacility1.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinlasttolastPregPlace
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position > 0) {
                            if (returnid(position - 1, 12, global.getLanguage()) == 2) {
                                tblfacilitytype.setVisibility(View.VISIBLE);
                            } else {
                                tblfacilitytype.setVisibility(View.GONE);
                                tblfacility.setVisibility(View.GONE);
                                etfacility.setText("");
                                spinfacility.setSelection(0);
                            }
                        } else {
                            tblfacilitytype.setVisibility(View.GONE);
                            tblfacility.setVisibility(View.GONE);
                            etfacility.setText("");
                            spinfacility.setSelection(0);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        spinlastPregPlace
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position > 0) {
                            if (returnid(position - 1, 12, global.getLanguage()) == 2) {
                                tblfacilitytype1.setVisibility(View.VISIBLE);
                            } else {
                                tblfacilitytype1.setVisibility(View.GONE);
                                tblfacility1.setVisibility(View.GONE);
                                etfacility1.setText("");
                                spinfacility1.setSelection(0);
                            }
                        } else {
                            tblfacilitytype1.setVisibility(View.GONE);
                            tblfacility1.setVisibility(View.GONE);
                            etfacility1.setText("");
                            spinfacility1.setSelection(0);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        spinLasttoLastPregComplications
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position > 0) {
                            int value = returnid(position - 1, 15,
                                    global.getLanguage());
                            if (value == 13) {
                                lastPregtbl2.setVisibility(View.VISIBLE);
                            } else {
                                lastPregtbl2.setVisibility(View.GONE);
                                etAnyotherCompl2.setText("");
                            }
                        } else {
                            lastPregtbl2.setVisibility(View.GONE);
                            etAnyotherCompl2.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        spinLastPregComplications
                .setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        if (position > 0) {
                            int value = returnid(position - 1, 15,
                                    global.getLanguage());
                            if (value == 13) {
                                lastPregtbl.setVisibility(View.VISIBLE);
                            } else {
                                lastPregtbl.setVisibility(View.GONE);
                                etAnyotherCompl.setText("");
                            }
                        } else {
                            lastPregtbl.setVisibility(View.GONE);
                            etAnyotherCompl.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int hrp = checkhrp();
                if (hrp == 1) {

                    HishRisk = 0;
                    int icheck = 0;
                    if (etifsccode.getText() != null
                            && etifsccode.getText().toString().length() > 0) {
                        icheck = Validate.checkifsc(etifsccode.getText()
                                .toString());
                    }
                    // if(value==1){
                    // showAlert(value);

                    icheck = sCheckValidation();
                    if (icheck == 1) {
                        Savedata();
                        CustomAlertSave(getResources().getString(
                                R.string.savesuccessfully));

                    } else {
                        showAlert(icheck);
                    }

                    // else
                    // {
                    // showAlert(value);
                    // }

                } else {
                    HishRisk = 1;
                    CustomAlertHRP(HRP + ""
                            + getResources().getString(R.string.HRP));
                }

            }
        });
        // spinPastIllness.setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // AddPastIllness();
        // }
        // });
        spinBeneficiary.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblreg.setVisibility(View.VISIBLE);
                    // tblpay.setVisibility(View.VISIBLE);
                } else {
                    tblreg.setVisibility(View.GONE);
                    // tblpay.setVisibility(View.GONE);
                    etdorjsy.setText("");
                    // spinpayrcvd.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        spinBankAccount.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (position == 1) {
                    tblaccountno.setVisibility(View.VISIBLE);
                    tblifsccode.setVisibility(View.VISIBLE);
                } else {
                    tblaccountno.setVisibility(View.GONE);
                    tblifsccode.setVisibility(View.GONE);
                    etaccno.setText("");
                    etifsccode.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        chk10.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk10.isChecked() == true) {
                    chk11.setChecked(false);
                    tblanyother.setVisibility(View.VISIBLE);
                } else {
                    tblanyother.setVisibility(View.GONE);
                    etanyother.setText("");
                }
            }
        });
        chk9.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk9.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk8.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk8.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk7.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk7.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk6.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk6.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk5.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk5.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk4.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk4.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk3.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk2.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk1.isChecked() == true) {
                    chk11.setChecked(false);

                }
            }
        });
        chk11.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (chk11.isChecked() == true) {
                    chk10.setChecked(false);
                    chk9.setChecked(false);
                    chk8.setChecked(false);
                    chk7.setChecked(false);
                    chk6.setChecked(false);
                    chk5.setChecked(false);
                    chk4.setChecked(false);
                    chk3.setChecked(false);
                    chk2.setChecked(false);
                    chk1.setChecked(false);

                } else {

                }

            }
        });
        etnoofpregnancy.addTextChangedListener(new TextWatcher() {
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
                    String result = "select  count(*) from tblPregnant_woman where HHFamilyMemberGUID='"
                            + global.getGlobalHHFamilyMemberGUID() + "'";
                    int count = dataProvider.getMaxRecord(result);
                    if (count > 1) {
                        lastPreg.setVisibility(View.VISIBLE);
                        lasttolastPreg.setVisibility(View.GONE);
                        lastPregOutcome.setVisibility(View.VISIBLE);
                        lasttolastPregOutcome.setVisibility(View.GONE);
                        spinLasttoLastPregoutcome.setSelection(0);
                        lastPregplace.setVisibility(View.VISIBLE);
                        lasttolastPregplace.setVisibility(View.GONE);
                        tblfacilitytype.setVisibility(View.GONE);
                        tblfacility.setVisibility(View.GONE);
                        etfacility.setText("");
                        spinfacility.setSelection(0);
                        spinLasttoLastPregComplications.setSelection(0);
                        spinlasttolastPregPlace.setSelection(0);

                    } else {
                        if (Integer.valueOf(etnoofpregnancy.getText()
                                .toString()) == 1) {
                            lastPreg.setVisibility(View.VISIBLE);
                            lasttolastPreg.setVisibility(View.GONE);
                            spinLastPregComplications.setSelection(0);
                            lastPregOutcome.setVisibility(View.VISIBLE);
                            lasttolastPregOutcome.setVisibility(View.GONE);
                            lastPregplace.setVisibility(View.VISIBLE);
                            lasttolastPregplace.setVisibility(View.GONE);
                            tblfacilitytype1.setVisibility(View.GONE);
                            tblfacility1.setVisibility(View.GONE);
                            etfacility1.setText("");
                            spinfacility1.setSelection(0);
                            spinLasttoLastPregComplications.setSelection(0);
                            spinLasttoLastPregoutcome.setSelection(0);
                            spinlasttolastPregPlace.setSelection(0);
                        } else if (Integer.valueOf(etnoofpregnancy.getText()
                                .toString()) >= 2) {
                            lastPreg.setVisibility(View.VISIBLE);
                            lasttolastPreg.setVisibility(View.VISIBLE);
                            lastPregOutcome.setVisibility(View.VISIBLE);

                            lasttolastPregOutcome.setVisibility(View.VISIBLE);
                            lastPregplace.setVisibility(View.VISIBLE);
                            lasttolastPregplace.setVisibility(View.VISIBLE);
                        }

                    }

                } else {
                    lastPreg.setVisibility(View.GONE);
                    lasttolastPreg.setVisibility(View.GONE);
                    lastPregOutcome.setVisibility(View.GONE);
                    spinLastPregComplications.setSelection(0);
                    spinLasttoLastPregoutcome.setSelection(0);
                    lasttolastPregOutcome.setVisibility(View.GONE);
                    lastPregplace.setVisibility(View.GONE);
                    lasttolastPregplace.setVisibility(View.GONE);
                    tblfacilitytype.setVisibility(View.GONE);
                    tblfacility.setVisibility(View.GONE);
                    etfacility.setText("");
                    spinfacility.setSelection(0);
                    tblfacilitytype1.setVisibility(View.GONE);
                    tblfacility1.setVisibility(View.GONE);
                    etfacility1.setText("");
                    spinfacility1.setSelection(0);
                    spinLasttoLastPregComplications.setSelection(0);
                    spinLastPregoutcome.setSelection(0);
                    spinLasttoLastPregoutcome.setSelection(0);
                    spinlastPregPlace.setSelection(0);
                    spinlasttolastPregPlace.setSelection(0);

                }
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                captureImage();
                tvImageView = tvimage;
            }
        });
        tvDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvDate);

            }
        });
        tvDate.addTextChangedListener(new TextWatcher() {

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

            @SuppressWarnings("unused")
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                            Locale.US);
                    Date date = new Date();
                    String datenew = Validate.changeDateFormat(tvDate.getText()
                            .toString());
                    // System.out.println(dateFormat.format(date));
                    newdate = Validate.getcurrentdate();
                    String currentdate = "";
                    String lmp = "";
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                            Locale.US);
                    if (newdate != null && newdate.length() > 0) {
                        String[] Cdt = newdate.split("-");
                        String DD = Cdt[0];
                        String MM = Cdt[1];
                        String YYYY = Cdt[2];
                        if (MM.length() == 1) {
                            MM = "0" + Cdt[1];
                            currentdate = DD + "-" + MM + "-" + YYYY;
                        } else {
                            currentdate = DD + "-" + MM + "-" + YYYY;
                        }

                    }

                    if (datenew != null && datenew.length() > 0) {
                        String[] Cdt = datenew.split("-");
                        String DD = Cdt[0];
                        String MM = Cdt[1];
                        String YYYY = Cdt[2];
                        if (MM.length() == 1) {
                            MM = "0" + Cdt[1];
                            lmp = DD + "-" + MM + "-" + YYYY;
                        } else {
                            lmp = DD + "-" + MM + "-" + YYYY;
                        }
                    }
                    Date d1 = null;
                    Date d2 = null;

                    try {
                        d1 = dateFormat.parse(currentdate);
                        d2 = dateFormat.parse(lmp);

                        // in milliseconds
                        long diff = d1.getTime() - d2.getTime();

                        long diffSeconds = diff / 1000 % 60;
                        long diffMinutes = diff / (60 * 1000) % 60;
                        long diffHours = diff / (60 * 60 * 1000) % 24;
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        if (diffDays <= 84) {
                            spinregweek.setSelection(1);
                        } else {
                            spinregweek.setSelection(2);
                        }
                        Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);

                        sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                        c = Calendar.getInstance();

                        try {
                            c.setTime(sdf.parse(datenew));
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        c.add(Calendar.DATE, 280);// insert the number of
                        // days
                        // you want to be added
                        // to
                        // the current date

                        Date resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenew = String.valueOf(sdf.format(resultdate));
                        tvedd.setText(String.valueOf(datenew));
                        c.add(Calendar.DATE, 28);
                        Date tt2resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenewtt2 = String.valueOf(sdf.format(tt2resultdate));
                        c.add(Calendar.DATE, 91);
                        Date checkup2resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenewcheckup2 = String.valueOf(sdf
                                .format(checkup2resultdate));
                        c.add(Calendar.DATE, 189);
                        Date checkup3resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenewcheckup3 = String.valueOf(sdf
                                .format(checkup3resultdate));
                        c.add(Calendar.DATE, 245);
                        Date checkup4resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenewcheckup4 = String.valueOf(sdf
                                .format(checkup4resultdate));
                        c.add(Calendar.DATE, 0);
                        Date tt1resultdate = new Date(c.getTimeInMillis());
                        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        datenewtt1 = String.valueOf(sdf.format(tt1resultdate));
                        datenewcheckup1 = String.valueOf(sdf
                                .format(tt1resultdate));
                    } catch (Exception e) {

                    }
                    // Convert long to String

                }
            }
        });
        etifsccode.addTextChangedListener(new TextWatcher() {

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
            public void afterTextChanged(Editable et) {
                // TODO Auto-generated method stub
                String s = et.toString();
                if (s.length() == 11) {
                    if (!s.equals(s.toUpperCase())) {
                        s = s.toUpperCase();
                        etifsccode.setText(s);
                    }
                }
            }

        });
        etdorjsy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(etdorjsy);
            }
        });
        // tvage.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        // ShowDatePicker(tvage);
        // }
        // });
        tvage.addTextChangedListener(new TextWatcher() {

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

            }
        });
        tvimage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tvimage.getText().toString().length() > 0) {
                    setImageView(tvimage);
                }
            }
        });
        // if (global.getsGlobalHusbandName() != null
        // && global.getsGlobalHusbandName().length() > 0) {
        // ethusbandname.setText(global.getsGlobalHusbandName());
        // }
        // if (global.getsGlobalWomenName() != null
        // && global.getsGlobalWomenName().length() > 0) {
        // etwomenname.setText(global.getsGlobalWomenName());
        sethusbandname();
        // }
        try {

            // final Bitmap bitmap =
            // BitmapFactory.decodeFile(getResources().getDrawable(R.drawable.images));
            final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.images2);
            if (bitmap != null) {
                // final Bitmap bitmap =
                // BitmapFactory.decodeFile(fileUri.getPath(),
                // options);

                Bitmap b = bitmap;

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                        bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
                        TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);

                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                        bitmap.getWidth() / 2, paint);
                Imgview.setImageBitmap(circleBitmap);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (global.getsGlobalPWGUID() != null
                && global.getsGlobalPWGUID().length() > 0) {
            showdata();
        }

    }

    public void sethusbandname() {
        data = dataProvider.getAllmalename(global.getsGlobalSpouseGUID());
        if (data != null && data.size() > 0) {
            ethusbandname.setText(data.get(0).getFamilyMemberName());

        }

        data = dataProvider
                .getAllmalename(global.getGlobalHHFamilyMemberGUID());
        if (data != null && data.size() > 0) {
            etwomenname.setText(data.get(0).getFamilyMemberName());
            if (data.get(0).getEducation() != 0) {
                spinEducation.setSelection(returnPos(
                        data.get(0).getEducation(), 104));
            }
            if (data.get(0).getDOBAvailable() == 1) {
                tvage.setText(""
                        + Validate.CountAge(data.get(0).getDateOfBirth()));
            } else if (data.get(0).getDOBAvailable() == 2) {
                // int gap = Validate.CalculateYM(data.get(0).getAgeAsOnYear());
                tvage.setText(""
                        + (Validate.CalculateYM(data.get(0).getAgeAsOnYear()) + data
                        .get(0).getAprilAgeYear()));
            }
        }
        if (ethusbandname.getText() != null
                && ethusbandname.getText().toString().length() > 0) {
            ethusbandname.setVisibility(View.VISIBLE);
            spinhusbandname.setVisibility(View.GONE);
        } else {
            ethusbandname.setVisibility(View.GONE);
            spinhusbandname.setVisibility(View.VISIBLE);
            fillCommonRecordhusband(spinhusbandname);
        }
    }

    private void fillCommonRecordhusband(Spinner spin) {

        HHFamilyMemberspin = dataProvider.showSpinnerDataFamiily(2, "",
                global.getGlobalHHSurveyGUID());

        String sValue[] = new String[HHFamilyMemberspin.size() + 1];
        sValue[0] = getResources().getString(R.string.select);
        for (int i = 0; i < HHFamilyMemberspin.size(); i++) {
            sValue[i + 1] = HHFamilyMemberspin.get(i).getFamilyMemberName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // if (pos > 0) {
                // sSpouseGUID = HHFamilyMemberspin.get(pos - 1)
                // .getHHFamilyMemberGUID();
                // }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

    }

    public int checkhrp() {
        HRP = "";
        if (chk1.isChecked() == true) {
            HRP = (String) chk1.getText();
        }
        if (chk2.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk2.getText();
            } else {
                HRP = HRP + "," + (String) chk2.getText();
            }
        }
        if (chk3.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk3.getText();
            } else {
                HRP = HRP + "," + (String) chk3.getText();
            }
        }
        if (chk4.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk4.getText();
            } else {
                HRP = HRP + "," + (String) chk4.getText();
            }
        }
        if (chk5.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk5.getText();
            } else {
                HRP = HRP + "," + (String) chk5.getText();
            }
        }
        if (chk6.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk6.getText();
            } else {
                HRP = HRP + "," + (String) chk6.getText();
            }
        }
        if (chk7.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk7.getText();
            } else {
                HRP = HRP + "," + (String) chk7.getText();
            }
        }
        if (chk8.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk8.getText();
            } else {
                HRP = HRP + "," + (String) chk8.getText();
            }
        }
        if (chk9.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = (String) chk9.getText();
            } else {
                HRP = HRP + "," + (String) chk9.getText();
            }
        }
        if (chk10.isChecked() == true) {
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                if (etanyother.getText().toString().length() > 0) {
                    HRP = etanyother.getText().toString();
                }
            } else {
                HRP = HRP + "," + etanyother.getText().toString();
            }
        }

        if (spinLastPregComplications.getSelectedItemPosition() > 0
                && spinLastPregComplications.getSelectedItemPosition() != 1) {
            condition = returnString(
                    spinLastPregComplications.getSelectedItemPosition() - 1,
                    15, global.getLanguage());
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + condition;
            }
        }

        int age2 = 0;
        if (tvage.getText().toString() != null
                && tvage.getText().toString().length() > 0) {
            age2 = Integer.valueOf(tvage.getText().toString());
        }
        if (tvage.getText().toString().length() > 0 && age2 < 19) {
            condition = getResources().getString(R.string.lessage);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + condition;
            }
        }
        int age = 0;
        if (tvage.getText().toString() != null
                && tvage.getText().toString().length() > 0) {
            age = Integer.valueOf(tvage.getText().toString());
        }
        if (tvage.getText().toString().length() > 0 && age > 40) {
            condition = getResources().getString(R.string.overage);
            if (HRP == null || HRP.equalsIgnoreCase("null")
                    || HRP.length() == 0) {
                HRP = condition;
            } else {
                HRP = HRP + "," + condition;
            }
        }
        if (HRP.length() > 5) {
            return 8;
        }
        return 1;
    }

    public void AddPastIllness() {
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drop_down_list_row, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(900, 400);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        fillGrid = (GridView) dialog.findViewById(R.id.pastillnessgridview);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        Common = dataProvider.getCommonRecord(global.getiGlobalLangID(), 20);

        if (Common != null) {

            fillGrid.setAdapter(new DropDownListAdapter(this, Common));

        }

        // Display the dialog
        dialog.show();

    }

    // public void showAlertHRP(int iCheck){
    //
    // if(iCheck==2){
    //
    // }else if(iCheck==3){
    //
    // }else if(iCheck==4){
    //
    // }else if(iCheck==6){
    //
    // }else if(iCheck==7){
    //
    // }
    // if(iCheck==2||iCheck==3||iCheck==4||iCheck==6||iCheck==7){
    // CustomAlertHRP(HRP);
    // }
    // }
    public int sCheckValidation() {
        int age = 0;
        if (tvage.getText().toString().length() > 0) {
            age = Integer.valueOf(tvage.getText().toString());
        }
        if (etmobileno.getText().toString().length() > 0
                && Validate.checkmobileno(etmobileno.getText().toString()) == 0) {
            return 2;
        } else if (etmctsid.getText().toString().length() > 0
                && etmctsid.getText().toString().length() != 18) {
            return 3;
        } else if (etifsccode.getText().toString().length() > 0
                && etifsccode.getText().toString().length() != 11) {

            return 4;
        } else if (tvDate.getText() != null
                && tvDate.getText().toString().length() == 0) {

            return 5;

        } else if (age < 10 || age > 50) {
            return 6;
            /*
			 * } else { return 1;
			 */
			/* } */
        } else if (etaltmobileno.getText().toString().length() > 0
                && Validate.checkmobileno(etaltmobileno.getText().toString()) == 0) {
            return 7;
        }
        return 1;
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(PregnantWomen.this)
                        .reportActivityStart(this);
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(PregnantWomen.this)
                        .reportActivityStop(this);
            } catch (Exception e) {
            }
        }
    }

    public void showAlert(int iCheck) {
        if (iCheck == 2) {
            CustomAlert(getResources().getString(R.string.entermobile));
        } else if (iCheck == 3) {
            CustomAlert(getResources().getString(R.string.enter16digituid));
        } else if (iCheck == 4) {
            CustomAlert(getResources().getString(R.string.ifsc));
        } else if (iCheck == 5) {
            CustomAlert(getResources().getString(R.string.lmpenter));
        } else if (iCheck == 6) {
            CustomAlert(getResources().getString(R.string.enteragevalid));
        } else if (iCheck == 7) {
            CustomAlert(getResources().getString(R.string.entermobile));
        }
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

    public void CustomAlertHRP(String msg) {
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
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                int icheck = 0;
                int value = 0;
                if (etifsccode.getText() != null
                        && etifsccode.getText().toString().length() > 0) {
                    value = Validate.checkifsc(etifsccode.getText().toString());
                }
                if (value == 1
                        || (etifsccode.getText() == null || etifsccode
                        .getText().toString().length() == 0)) {
                    showAlert(value);

                    icheck = sCheckValidation();
                    if (icheck == 1) {
                        Savedata();
                        CustomAlertSave(getResources().getString(
                                R.string.savesuccessfully));

                    } else {
                        showAlert(icheck);
                    }
                } else {
                    showAlert(value);
                }

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
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(PregnantWomen.this, AncActivity.class);
                startActivity(i);

            }
        });

        // Display the dialog
        dialog.show();

    }

    public static String toddMMyy(Date day) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy", Locale.US);
        String date = formatter.format(day);
        return date;
    }

    public void Savedata() {
        String PWName = "";
        String HHFamilyMemberGUID = global.getGlobalHHFamilyMemberGUID();
        String HHGUID = global.getGlobalHHSurveyGUID();
        String LMPDate = "";
        String EDDDate = "";
        String PWRegistrationDate = "";
        int Regwithin12weeks = 0;
        int RegweeksElaspsed = 0;
        String HusbandName = "";
        String Husband_GUID = global.getsGlobalSpouseGUID();
        String MobileNo = "";
        String MotherMCTSID = "";
        String IFSCCode = "";
        String Accountno = "";
        int JSYBenificiaryYN = 0;
        String JSYRegDate = "";
        int JSYPaymentReceivedYN = 0;
        String PWDOB = "";
        int PWAgeYears = 0;
        String PWAgeRefDate = Validate.getcurrentdate();
        double PWWeight = 0.0;
        int PWBloodGroup = 0;
        String PastIllnessYN = "";
        int TotalPregnancy = 0;
        int LastPregnancyResult = 0;
        int LastPregnancyComplication = 0;
        int LTLPregnancyResult = 0;
        int LTLPregnancyomplication = 0;
        int LastPregDeliveryPlace = 0;
        int LasttolastPregDeliveryPlace = 0;
        String PWGUID = "";
        double PWHeight = 0.0;
        String PWImage = "";
        String AnyOtherPastIllness = "";
        String AnyOtherLastPregCom = "";
        String AnyOtherLTLPregCom = "";
        int BankAcc = 0;
        int FacitylastPreg = 0;
        String FaciltyOtherLTL = "";
        int FacityLTL = 0;
        String AltMobileNo = "";
        String FacilityOtherLastpreg = "";
        int Education = 0;

        if (etwomenname.getText() != null
                && etwomenname.getText().toString().length() > 0) {
            PWName = etwomenname.getText().toString();
        }
        if (tvimage.getText() != null
                && tvimage.getText().toString().length() > 0) {
            PWImage = tvimage.getText().toString();
        }
        if (ethusbandname.getText() != null
                && ethusbandname.getText().toString().length() > 0) {
            HusbandName = ethusbandname.getText().toString();
        } else if (spinhusbandname.getSelectedItemPosition() > 0) {
            HusbandName = spinhusbandname.getSelectedItem().toString();
        }
        if (tvDate.getText() != null
                && tvDate.getText().toString().length() > 0) {
            LMPDate = Validate.changeDateFormat(tvDate.getText().toString());
        }
        if (tvedd.getText() != null && tvedd.getText().toString().length() > 0) {
            EDDDate = Validate.changeDateFormat(tvedd.getText().toString());
        }
        if (etmctsid.getText() != null
                && etmctsid.getText().toString().length() > 0) {
            MotherMCTSID = etmctsid.getText().toString();
        }
        if (spinEducation.getSelectedItemPosition() > 0) {
            Education = returnid(spinEducation.getSelectedItemPosition() - 1,
                    104, global.getLanguage());
        }
        if (etmobileno.getText() != null
                && etmobileno.getText().toString().length() > 0) {
            MobileNo = etmobileno.getText().toString();
            Phoneno = etmobileno.getText().toString();
        }
        if (etaltmobileno.getText() != null
                && etaltmobileno.getText().toString().length() > 0) {
            AltMobileNo = etaltmobileno.getText().toString();
            //
        }

        if (etaccno.getText() != null
                && etaccno.getText().toString().length() > 0) {
            Accountno = etaccno.getText().toString();
            Accno = etaccno.getText().toString();
        }
        if (etifsccode.getText() != null
                && etifsccode.getText().toString().length() > 0) {
            IFSCCode = etifsccode.getText().toString();
            ifsccode = etifsccode.getText().toString();
        }
        if (spinBeneficiary.getSelectedItemPosition() > 0) {

            JSYBenificiaryYN = returnid(
                    spinBeneficiary.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (etdorjsy.getText() != null
                && etdorjsy.getText().toString().length() > 0) {
            JSYRegDate = Validate.changeDateFormat(etdorjsy.getText()
                    .toString());
        }
        if (spinpayrcvd.getSelectedItemPosition() > 0) {
            JSYPaymentReceivedYN = returnid(
                    spinpayrcvd.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (spinBankAccount.getSelectedItemPosition() > 0) {
            BankAcc = returnid(spinBankAccount.getSelectedItemPosition() - 1,
                    7, global.getLanguage());
        }
        if (tvage.getText() != null && tvage.getText().toString().length() > 0) {
            PWDOB = tvage.getText().toString();
        }
        if (spinregweek.getSelectedItemPosition() > 0) {
            Regwithin12weeks = returnid(
                    spinregweek.getSelectedItemPosition() - 1, 7,
                    global.getLanguage());
        }
        if (etwopg.getText().toString().length() > 0) {
            PWWeight = Double.valueOf(etwopg.getText().toString());
        }
        if (spinBldgrp.getSelectedItemPosition() > 0) {
            PWBloodGroup = returnid(spinBldgrp.getSelectedItemPosition() - 1,
                    14, global.getLanguage());
        }

        PastIllnessYN = savecheckbox();

        if (etnoofpregnancy.getText().toString().length() > 0) {
            TotalPregnancy = Integer.valueOf(etnoofpregnancy.getText()
                    .toString());
        }

        if (spinLastPregComplications.getSelectedItemPosition() > 0) {

            LastPregnancyComplication = returnid(
                    spinLastPregComplications.getSelectedItemPosition() - 1,
                    15, global.getLanguage());
        }
        if (spinLasttoLastPregComplications.getSelectedItemPosition() > 0) {
            LTLPregnancyomplication = returnid(
                    spinLasttoLastPregComplications.getSelectedItemPosition() - 1,
                    15, global.getLanguage());
        }
        if (spinfacility1.getSelectedItemPosition() > 0) {
            FacitylastPreg = returnid(
                    spinfacility1.getSelectedItemPosition() - 1, 27,
                    global.getLanguage());
        }
        if (spinfacility.getSelectedItemPosition() > 0) {
            FacityLTL = returnid(spinfacility.getSelectedItemPosition() - 1,
                    27, global.getLanguage());
        }
        if (spinLastPregoutcome.getSelectedItemPosition() > 0) {
            LastPregnancyResult = returnid(
                    spinLastPregoutcome.getSelectedItemPosition() - 1, 16,
                    global.getLanguage());
        }
        if (spinLasttoLastPregoutcome.getSelectedItemPosition() > 0) {
            LTLPregnancyResult = returnid(
                    spinLasttoLastPregoutcome.getSelectedItemPosition() - 1,
                    16, global.getLanguage());
        }
        if (spinlastPregPlace.getSelectedItemPosition() > 0) {
            LastPregDeliveryPlace = returnid(
                    spinlastPregPlace.getSelectedItemPosition() - 1, 12,
                    global.getLanguage());
        }
        if (spinlasttolastPregPlace.getSelectedItemPosition() > 0) {
            LasttolastPregDeliveryPlace = returnid(
                    spinlasttolastPregPlace.getSelectedItemPosition() - 1, 12,
                    global.getLanguage());
        }

        if (etanyother.getText() != null
                && etanyother.getText().toString().length() > 0) {
            AnyOtherPastIllness = etanyother.getText().toString();
        }
        if (etfacility.getText() != null
                && etfacility.getText().toString().length() > 0) {
            FaciltyOtherLTL = etfacility.getText().toString();
        }
        if (etfacility1.getText() != null
                && etfacility1.getText().toString().length() > 0) {
            FacilityOtherLastpreg = etfacility1.getText().toString();
        }
        if (etAnyotherCompl.getText() != null
                && etAnyotherCompl.getText().toString().length() > 0) {
            AnyOtherLastPregCom = etAnyotherCompl.getText().toString();
        }
        if (etAnyotherCompl2.getText() != null
                && etAnyotherCompl2.getText().toString().length() > 0) {
            AnyOtherLTLPregCom = etAnyotherCompl2.getText().toString();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.US);
        Date date = new Date();
        String flag = "";
        int ANMID = 0;
        int AshaID = 0;
        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
        }
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
        }
        String CreatedOn = String.valueOf(dateFormat.format(date));
        String UpdatedOn = CreatedOn;
        int CreatedBy = Integer.valueOf(global.getsGlobalUserID());
        int UpdatedBy = Integer.valueOf(global.getsGlobalUserID());
        PWRegistrationDate = CreatedOn;
        if (global.getsGlobalPWGUID() != null
                && global.getsGlobalPWGUID().length() > 0) {
            flag = "u";
            dataProvider.savePregWomen(flag, PWImage, HishRisk,
                    AnyOtherPastIllness, AnyOtherLastPregCom,
                    AnyOtherLTLPregCom, global.getsGlobalPWGUID(),
                    HHFamilyMemberGUID, HHGUID, PWName, LMPDate, EDDDate,
                    PWRegistrationDate, Regwithin12weeks, RegweeksElaspsed,
                    HusbandName, Husband_GUID, MobileNo, MotherMCTSID,
                    JSYBenificiaryYN, JSYRegDate, JSYPaymentReceivedYN, PWDOB,
                    PWAgeYears, PWAgeRefDate, PWWeight, PWBloodGroup,
                    PastIllnessYN, TotalPregnancy, LastPregnancyResult,
                    LastPregnancyComplication, LTLPregnancyResult,
                    LTLPregnancyomplication, LastPregDeliveryPlace,
                    LasttolastPregDeliveryPlace, IFSCCode, Accountno, PWHeight,
                    UpdatedOn, UpdatedBy, BankAcc, FacitylastPreg,
                    FaciltyOtherLTL, FacityLTL, FacilityOtherLastpreg, ANMID,
                    AshaID, Education, AltMobileNo);
            dataProvider.savedates(flag, global.getGlobalHHSurveyGUID(),
                    global.getGlobalHHFamilyMemberGUID(),
                    global.getsGlobalPWGUID(), LMPDate, EDDDate, datenewtt1,
                    datenewtt2, datenewcheckup1, datenewcheckup2,
                    datenewcheckup3, datenewcheckup4, 1, CreatedOn, CreatedBy,
                    AshaID, ANMID);
        } else {
            PWGUID = Validate.random();
            flag = "i";
            dataProvider.savePregWomen(flag, PWImage, HishRisk,
                    AnyOtherPastIllness, AnyOtherLastPregCom,
                    AnyOtherLTLPregCom, PWGUID, HHFamilyMemberGUID, HHGUID,
                    PWName, LMPDate, EDDDate, PWRegistrationDate,
                    Regwithin12weeks, RegweeksElaspsed, HusbandName,
                    Husband_GUID, MobileNo, MotherMCTSID, JSYBenificiaryYN,
                    JSYRegDate, JSYPaymentReceivedYN, PWDOB, PWAgeYears,
                    PWAgeRefDate, PWWeight, PWBloodGroup, PastIllnessYN,
                    TotalPregnancy, LastPregnancyResult,
                    LastPregnancyComplication, LTLPregnancyResult,
                    LTLPregnancyomplication, LastPregDeliveryPlace,
                    LasttolastPregDeliveryPlace, IFSCCode, Accountno, PWHeight,
                    CreatedOn, CreatedBy, BankAcc, FacitylastPreg,
                    FaciltyOtherLTL, FacityLTL, FacilityOtherLastpreg, ANMID,
                    AshaID, Education, AltMobileNo);
            dataProvider.savedates(flag, global.getGlobalHHSurveyGUID(),
                    global.getGlobalHHFamilyMemberGUID(), PWGUID, LMPDate,
                    EDDDate, datenewtt1, datenewtt2, datenewcheckup1,
                    datenewcheckup2, datenewcheckup3, datenewcheckup4, 1,
                    CreatedOn, CreatedBy, AshaID, ANMID);

        }
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.US);
        String formattedDate = df.format(c.getTime());
        String sql = "insert into tblhhupdate_Log(hhsurveyguid,hhmemberguid,UserID,PhoneNo_Old,PhoneNo_New,AccountNo_Old,AccountNo_New,IFSCCode_Old,IFSCCode_New,UpdatedOn,IsEdited)values('"
                + global.getGlobalHHSurveyGUID()
                + "','"
                + global.getGlobalHHFamilyMemberGUID()
                + "',"
                + global.getsGlobalUserID()
                + ",'"
                + Phoneno
                + "','"
                + MobileNo
                + "','"
                + Accno
                + "','"
                + Accountno
                + "','"
                + ifsccode
                + "','"
                + IFSCCode + "','" + formattedDate + "',1)";

        dataProvider.executeSql(sql);
        String sql1 = "update Tbl_HHFamilyMember set Spouse='" + Husband_GUID
                + "',IsEdited=1 where HHFamilyMemberGUID='"
                + HHFamilyMemberGUID + "'";
        dataProvider.executeSql(sql1);
    }

    public void showdata() {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }

        record = dataProvider.getPregnantWomendata(global.getsGlobalPWGUID(),
                1, ashaid);
        if (record != null && record.size() > 0) {
            if (record.get(0).getPWName() != null
                    && record.get(0).getPWName().length() > 0) {
                etwomenname.setText(record.get(0).getPWName());
            }
            if (record.get(0).getHusbandName() != null
                    && record.get(0).getHusbandName().length() > 0) {
                ethusbandname.setVisibility(View.VISIBLE);
                ethusbandname.setText(String.valueOf(record.get(0)
                        .getHusbandName()));
                spinhusbandname.setVisibility(View.GONE);
            }
            if (record.get(0).getHusband_GUID() != null
                    && record.get(0).getHusband_GUID().length() > 0) {
                data = dataProvider.getAllmalename(record.get(0)
                        .getHusband_GUID());
                if (data != null && data.size() > 0) {
                    ethusbandname.setText(data.get(0).getFamilyMemberName());
                    ethusbandname.setVisibility(View.VISIBLE);
                    spinhusbandname.setVisibility(View.GONE);
                } else {
                    ethusbandname.setVisibility(View.GONE);
                    spinhusbandname.setVisibility(View.VISIBLE);
                }
            }
            if (record.get(0).getLMPDate() != null
                    && record.get(0).getLMPDate().length() > 0) {
                tvDate.setText(Validate.changeDateFormat(record.get(0)
                        .getLMPDate()));
                // tvDate.setEnabled(false);
            }

            if (record.get(0).getEDDDate() != null
                    && record.get(0).getEDDDate().length() > 0) {
                tvedd.setText(Validate.changeDateFormat(record.get(0)
                        .getEDDDate()));
            }
            if (record.get(0).getMobileNo() != null
                    && record.get(0).getMobileNo().length() > 0) {
                etmobileno.setText(record.get(0).getMobileNo());
            }
            if (record.get(0).getAltMobileNo() != null
                    && record.get(0).getAltMobileNo().length() > 0) {
                etaltmobileno.setText(record.get(0).getAltMobileNo());
            }
            if (record.get(0).getAccountno() != null
                    && record.get(0).getAccountno().length() > 0) {
                etaccno.setText(record.get(0).getAccountno());
                // spinBankAccount.setSelection(1);
            }
            if (record.get(0).getIFSCCode() != null
                    && record.get(0).getIFSCCode().length() > 0) {
                etifsccode.setText(record.get(0).getIFSCCode());
            }

            if (record.get(0).getPWImage() != null
                    && record.get(0).getPWImage().length() > 0) {
                tvimage.setText(record.get(0).getPWImage());
            }
            if (record.get(0).getJSYBenificiaryYN() > 0) {
                spinBeneficiary.setSelection(returnPos(record.get(0)
                        .getJSYBenificiaryYN(), 7));
            }
            if (record.get(0).getJSYPaymentReceivedYN() > 0) {
                spinpayrcvd.setSelection(returnPos(record.get(0)
                        .getJSYPaymentReceivedYN(), 7));
            }
            if (record.get(0).getEducation() > 0) {
                spinEducation.setSelection(returnPos(record.get(0)
                        .getEducation(), 104));
            }
            if (record.get(0).getBankAcc() > 0) {
                spinBankAccount.setSelection(returnPos(record.get(0)
                        .getBankAcc(), 7));
                tblaccountno.setVisibility(View.VISIBLE);
                tblifsccode.setVisibility(View.VISIBLE);
            }
            if (record.get(0).getRegwithin12weeks() > 0) {
                spinregweek.setSelection(returnPos(record.get(0)
                        .getRegwithin12weeks(), 7));
            }

            if (record.get(0).getPastIllnessYN().length() > 0) {
                String Past_Illness = record.get(0).getPastIllnessYN();
                String[] pastillness = Past_Illness.split(",");
                for (int i = 0; i < pastillness.length; i++) {
                    if (pastillness[i].equalsIgnoreCase("1")) {
                        chk1.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("2")) {
                        chk2.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("3")) {
                        chk3.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("4")) {
                        chk4.setChecked(true);
                    } else if (pastillness[i]
                            .equalsIgnoreCase("5")) {
                        chk5.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("6")) {
                        chk6.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("7")) {
                        chk7.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("8")) {
                        chk8.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("9")) {
                        chk9.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("10")) {
                        chk11.setChecked(true);
                    } else if (pastillness[i].equalsIgnoreCase("11")) {
                        chk10.setChecked(true);
                        tblanyother.setVisibility(View.VISIBLE);
                        if (record.get(0).getAnyOtherPastIllness() != null
                                && record.get(0).getAnyOtherPastIllness().length() > 0) {
                            etanyother.setText(record.get(0).getAnyOtherPastIllness());
                        } else {
                            etanyother.setText("");
                        }
                    }

                }
            }

            if (record.get(0).getPWBloodGroup() > 0) {
                spinBldgrp.setSelection(returnPos(record.get(0)
                        .getPWBloodGroup(), 14));
            }
            if (record.get(0).getMotherMCTSID() != null
                    && record.get(0).getMotherMCTSID().length() > 0) {
                etmctsid.setText(record.get(0).getMotherMCTSID());
            }
            if (record.get(0).getPWAgeYears() > 0) {
                tvage.setText(String.valueOf(record.get(0).getPWAgeYears()));
            }
            if (record.get(0).getTotalPregnancy() > 0) {
                etnoofpregnancy.setText(String.valueOf(record.get(0)
                        .getTotalPregnancy()));
            }
            if (record.get(0).getLastPregnancyComplication() > 0) {
                spinLastPregComplications.setSelection(returnPos(record.get(0)
                        .getLastPregnancyComplication(), 15));
            }
            if (record.get(0).getLastPregnancyResult() > 0) {
                spinLastPregoutcome.setSelection(returnPos(record.get(0)
                        .getLastPregnancyResult(), 16));
            }
            if (record.get(0).getLastPregDeliveryPlace() > 0) {
                spinlastPregPlace.setSelection(returnPos(record.get(0)
                        .getLastPregDeliveryPlace(), 12));
            }
            if (record.get(0).getLTLPregnancyomplication() > 0) {
                spinLasttoLastPregComplications.setSelection(returnPos(record
                        .get(0).getLTLPregnancyomplication(), 15));
            }
            if (record.get(0).getLTLPregnancyResult() > 0) {
                spinLasttoLastPregoutcome.setSelection(returnPos(record.get(0)
                        .getLTLPregnancyResult(), 16));
            }
            if (record.get(0).getLasttolastPregDeliveryPlace() > 0) {
                spinlasttolastPregPlace.setSelection(returnPos(record.get(0)
                        .getLasttolastPregDeliveryPlace(), 12));
            }
            if (record.get(0).getJSYRegDate() != null
                    && record.get(0).getJSYRegDate().length() > 0) {
                etdorjsy.setText(Validate.changeDateFormat(record.get(0)
                        .getJSYRegDate()));
            }
            if (record.get(0).getAnyOtherLastPregCom() != null
                    && record.get(0).getAnyOtherLastPregCom().length() > 0) {
                etAnyotherCompl.setText(record.get(0).getAnyOtherLastPregCom());
            }
            if (record.get(0).getAnyOtherLTLPregCom() != null
                    && record.get(0).getAnyOtherLTLPregCom().length() > 0) {
                etAnyotherCompl2.setText(record.get(0).getAnyOtherLTLPregCom());
            }
            if (record.get(0).getAnyOtherPastIllness() != null
                    && record.get(0).getAnyOtherPastIllness().length() > 0) {
                etanyother.setText(record.get(0).getAnyOtherPastIllness());
            }
            if (record.get(0).getPWWeight() > 0) {
                etwopg.setText(String.valueOf(record.get(0).getPWWeight()));
            }
            if (record.get(0).getFacityLTL() > 0) {
                spinfacility.setSelection(returnPos(record.get(0)
                        .getFacityLTL(), 27));
            }
            if (record.get(0).getFacitylastPreg() > 0) {
                spinfacility1.setSelection(returnPos(record.get(0)
                        .getFacitylastPreg(), 27));
            }
            if (record.get(0).getFaciltyOtherLTL() != null
                    && record.get(0).getFaciltyOtherLTL().length() > 0) {
                etfacility.setText(record.get(0).getFaciltyOtherLTL());
            }
            if (record.get(0).getFacilityOtherLastpreg() != null
                    && record.get(0).getFacilityOtherLastpreg().length() > 0) {
                etfacility1.setText(record.get(0).getFacilityOtherLastpreg());
            }
            File mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMAGE_DIRECTORY_NAME);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                            + IMAGE_DIRECTORY_NAME + " directory");

                }
            }
            String imagePath = null;
            imagePath = mediaStorageDir.getPath() + File.separator;
            String ImageName = imagePath + record.get(0).getPWImage();
            if (ImageName.length() > 10) {
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // downsizing image as it throws OutOfMemory Exception for
                    // larger
                    // images
                    // options.inSampleSize = 2;

                    final Bitmap bitmap = BitmapFactory.decodeFile(ImageName,
                            options);

                    if (bitmap != null) {

                        Bitmap b = bitmap;
                        Bitmap bMapRotate = null;
                        Matrix mat = new Matrix();
                        if (b.getWidth() > b.getHeight()) {
                            mat.postRotate(90);
                            bMapRotate = Bitmap.createBitmap(b, 0, 0,
                                    b.getWidth(), b.getHeight(), mat, true);
                            b.recycle();
                            b = null;
                            Bitmap circleBitmap = Bitmap.createBitmap(
                                    bMapRotate.getWidth(),
                                    bMapRotate.getHeight(),
                                    Bitmap.Config.ARGB_8888);

                            BitmapShader shader = new BitmapShader(bMapRotate,
                                    TileMode.CLAMP, TileMode.CLAMP);
                            Paint paint = new Paint();
                            paint.setShader(shader);

                            Canvas c = new Canvas(circleBitmap);
                            c.drawCircle(bMapRotate.getWidth() / 2,
                                    bMapRotate.getHeight() / 2,
                                    bMapRotate.getWidth() / 2, paint);
                            Imgview.setImageBitmap(circleBitmap);
                        } else {
                            Bitmap circleBitmap = Bitmap.createBitmap(
                                    bitmap.getWidth(), bitmap.getHeight(),
                                    Bitmap.Config.ARGB_8888);

                            BitmapShader shader = new BitmapShader(bitmap,
                                    TileMode.CLAMP, TileMode.CLAMP);
                            Paint paint = new Paint();
                            paint.setShader(shader);

                            Canvas c = new Canvas(circleBitmap);
                            c.drawCircle(bitmap.getWidth() / 2,
                                    bitmap.getHeight() / 2,
                                    bitmap.getWidth() / 2, paint);
                            Imgview.setImageBitmap(b);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
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

    private void fillCheckboxValues(CheckBox check, int pos, int iflag,
                                    int Language) {
        Common = dataProvider.getCommonRecord(Language, iflag);

        check.setText(Common.get(pos).getValue());

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

    public int returnid(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getID();

    }

    public String returnString(int POS, int iFlag, int Language) {
        Common = dataProvider.getCommonRecord(Language, iFlag);

        return Common.get(POS).getValue();

    }

    public void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // if the result is capturing Image
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == this.RESULT_OK) {
                    // successfully captured the image
                    // display it in image view
                    previewCapturedImage();
                } else if (resultCode == this.RESULT_CANCELED) {
                    // user cancelled Image capture
                    Toast.makeText(this, "User cancelled image capture",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // failed to capture image
                    Toast.makeText(this, "Sorry! Failed to capture image",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage() {
        try {

            // hide video preview

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            // options.inSampleSize = 2;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            Bitmap b = BitmapFactory.decodeFile(fileUri.getPath(), options);
            Bitmap bMapRotate = null;
            Matrix mat = new Matrix();
            if (b.getWidth() > b.getHeight()) {
                mat.postRotate(90);
                bMapRotate = Bitmap.createBitmap(b, 0, 0, b.getWidth(),
                        b.getHeight(), mat, true);
                b.recycle();
                b = null;
                Bitmap circleBitmap = Bitmap.createBitmap(
                        bMapRotate.getWidth(), bMapRotate.getHeight(),
                        Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bMapRotate,
                        TileMode.CLAMP, TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);

                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bMapRotate.getWidth() / 2,
                        bMapRotate.getHeight() / 2, bMapRotate.getWidth() / 2,
                        paint);
                Imgview.setImageBitmap(circleBitmap);
            } else {
                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                        bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader(bitmap, TileMode.CLAMP,
                        TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);

                Canvas c = new Canvas(circleBitmap);
                c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                        bitmap.getWidth() / 2, paint);
                Imgview.setImageBitmap(b);
            }

            // Imgview.setImageBitmap(bitmap);

            // tvImageView.setText(iMagename);
            tvimage.setText(iMagename);
            String languageToLoad = "hi"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.ENGLISH).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
            iMagename = "IMG_" + timeStamp + ".jpg";

        } else {
            return null;
        }

        return mediaFile;
    }

    public void setImageView(TextView tvImage) {
        ImageDialog = new Dialog(this, R.style.AppTheme);
        Window window = ImageDialog.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        ImageDialog.setContentView(R.layout.image_dialog);
        ImageView idImage = (ImageView) ImageDialog.findViewById(R.id.idImage);
        String ImageName = tvImage.getText().toString();
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        String path = mediaStorageDir.getPath() + File.separator + ImageName;
        Bitmap bitmap = BitmapFactory.decodeFile(path);

        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 390, 390, true);
            idImage.setImageBitmap(bitmap);

        }
        ImageDialog.show();
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
            cal.add(Calendar.YEAR, -1);
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
            ss = ss + "6,";
        }
        if (chk7.isChecked()) {
            ss = ss + "7,";
        }
        if (chk8.isChecked()) {
            ss = ss + "8,";
        }
        if (chk9.isChecked()) {
            ss = ss + "9,";
        }
        if (chk11.isChecked()) {
            ss = ss + "10,";
        }
        if (chk10.isChecked()) {
            ss = ss + "11";
        }
        return ss;
    }



}