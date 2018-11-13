package com.microware.intrahealth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({"SimpleDateFormat", "InflateParams"})
public class Immunization_Entry extends Activity {

    TextView tvfathername, tVmothername, tvChildname, TXTBCG, txtopv1,
            txtDPT1Pentavalent1, tvHepatitisB1, tvOPV2, tvDPT2Pentavalent2,
            tvHepatitisB2, tvOPV3, tvDPT3Pentavalent3, tvHepatitisB3,
            txtMeasles1, txtVitaminA, txtPentavalent1, txtPentavalent2,
            txtPentavalent3, txtopv0, tvHepatitisB, txtipv, txttt,
            txtVitaminAtwo, txtMeasles2, txtDPTBooster, txtopvbooster,
            txtDPTBoostertwo, tv_JEVaccine1, tv_JEVaccine2, tv_vitamin3,
            tv_vitamin4, tv_vitamin5, tv_vitamin6, tv_vitamin7, tv_vitamin8,
            tv_vitamin9, tv_TT2, tv_MeaslesRubella, txtipv2;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    Dialog datepic;
    Button btnSubmit,btnNotverified, btnVerifiedOk, btnVerifiedNotOk;
    String sChildGUID;
    DataProvider dataProvider;
    Validate validate;
    String sDOBCHILD = "", textdate = "";
    Global g;
    LinearLayout llVerification, llSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imunization_entery);

        btnSubmit = (Button) findViewById(R.id.btnSave);
        btnNotverified = (Button) findViewById(R.id.btnNotverified);
        btnVerifiedOk = (Button) findViewById(R.id.btnVerifiedOk);
        btnVerifiedNotOk = (Button) findViewById(R.id.btnVerifiedNotOk);
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        g = (Global) getApplicationContext();
        tvfathername = (TextView) findViewById(R.id.tvfathername);
        txtMeasles2 = (TextView) findViewById(R.id.txtMeasles2);
        tv_MeaslesRubella = (TextView) findViewById(R.id.tv_MeaslesRubella);
        txtDPTBooster = (TextView) findViewById(R.id.txtDPTBooster);
        txtopvbooster = (TextView) findViewById(R.id.txtopvbooster);
        txtDPTBoostertwo = (TextView) findViewById(R.id.txtDPTBoostertwo);
        tVmothername = (TextView) findViewById(R.id.tVmothername);
        txttt = (TextView) findViewById(R.id.txttt);
        txtVitaminAtwo = (TextView) findViewById(R.id.txtVitaminAtwo);
        tvChildname = (TextView) findViewById(R.id.tvChildname);
        TXTBCG = (TextView) findViewById(R.id.TXTBCG);
        txtopv1 = (TextView) findViewById(R.id.txtopv1);
        txtDPT1Pentavalent1 = (TextView) findViewById(R.id.txtDPT1Pentavalent1);
        tvHepatitisB1 = (TextView) findViewById(R.id.tvHepatitisB1);
        tvOPV2 = (TextView) findViewById(R.id.tvOPV2);
        tvDPT2Pentavalent2 = (TextView) findViewById(R.id.tvDPT2Pentavalent2);
        tvHepatitisB2 = (TextView) findViewById(R.id.tvHepatitisB2);
        tvOPV3 = (TextView) findViewById(R.id.tvOPV3);
        tvDPT3Pentavalent3 = (TextView) findViewById(R.id.tvDPT3Pentavalent3);
        tvHepatitisB3 = (TextView) findViewById(R.id.tvHepatitisB3);
        txtMeasles1 = (TextView) findViewById(R.id.txtMeasles1);
        txtVitaminA = (TextView) findViewById(R.id.txtVitaminA);
        txtPentavalent3 = (TextView) findViewById(R.id.txtPentavalent3);
        txtPentavalent1 = (TextView) findViewById(R.id.txtPentavalent1);
        txtPentavalent2 = (TextView) findViewById(R.id.txtPentavalent2);
        txtopv0 = (TextView) findViewById(R.id.txtopv0);
        tvHepatitisB = (TextView) findViewById(R.id.tvHepatitisB);
        txtipv = (TextView) findViewById(R.id.txtipv);
        txtipv2 = (TextView) findViewById(R.id.txtipv2);
        tv_JEVaccine1 = (TextView) findViewById(R.id.tv_JEVaccine1);
        tv_JEVaccine2 = (TextView) findViewById(R.id.tv_JEVaccine2);
        tv_vitamin3 = (TextView) findViewById(R.id.tv_vitamin3);
        tv_vitamin4 = (TextView) findViewById(R.id.tv_vitamin4);
        tv_vitamin5 = (TextView) findViewById(R.id.tv_vitamin5);
        tv_vitamin6 = (TextView) findViewById(R.id.tv_vitamin6);
        tv_vitamin7 = (TextView) findViewById(R.id.tv_vitamin7);
        tv_vitamin8 = (TextView) findViewById(R.id.tv_vitamin8);
        tv_vitamin9 = (TextView) findViewById(R.id.tv_vitamin9);
        tv_TT2 = (TextView) findViewById(R.id.tv_TT2);
        llVerification = (LinearLayout) findViewById(R.id.llVerification);
        llSave = (LinearLayout) findViewById(R.id.llSave);
        llVerification.setVisibility(View.GONE);
        llSave.setVisibility(View.GONE);
        if (g.getiGlobalRoleID() == 4) {
            llVerification.setVisibility(View.VISIBLE);
            llSave.setVisibility(View.GONE);
        } else {
            llVerification.setVisibility(View.GONE);
            llSave.setVisibility(View.VISIBLE);
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                savedata();
                String msg = getResources().getString(R.string.successfully);
                CustomAlertSave(msg);

            }
        });
        btnNotverified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.buttonorange));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                Validate.verification(g.getUserID(), 3, g.getsGlobalChildGUID(), 1, g.getsGlobalAshaCode(), g.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"), btnNotverified, btnVerifiedOk, btnVerifiedNotOk);
            }
        });

        btnVerifiedOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                Validate.verification(g.getUserID(), 3, g.getsGlobalChildGUID(), 2, g.getsGlobalAshaCode(), g.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"), btnNotverified, btnVerifiedOk, btnVerifiedNotOk);
            }
        });
        btnVerifiedNotOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.DarkRed));
                Validate.verification(g.getUserID(), 3, g.getsGlobalChildGUID(), 3, g.getsGlobalAshaCode(), g.getAnmidasAnmCode(), validate.RetriveSharepreferenceString("AFID"), btnNotverified, btnVerifiedOk, btnVerifiedNotOk);
            }
        });
        txtopv1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtopv1, txtopv0,
                        getResources().getString(R.string.opv2), getResources()
                                .getString(R.string.opv1));
                int value1 = validate1(txtopv1, tvOPV2);
                int value2 = validate1(txtopv1, tvOPV3);
                int value3 = validate1(tvOPV2, txtopvbooster);
                if (value1 == 0 || value2 == 0 || value3 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvOPV2.setText("");
                                    tvOPV3.setText("");
                                    txtopvbooster.setText("");

                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtopv1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tvOPV2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tvOPV2, txtopv0,
                        getResources().getString(R.string.opv3), getResources()
                                .getString(R.string.opv1));
                validate(tvOPV2, txtopv1,
                        getResources().getString(R.string.opv3), getResources()
                                .getString(R.string.opv2));

                int value2 = validate1(tvOPV2, tvOPV3);
                int value3 = validate1(tvOPV2, txtopvbooster);
                if (value2 == 0 || value3 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtopvbooster.setText("");
                                    tvOPV3.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvOPV2.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tvOPV3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tvOPV3, txtopv0,
                        getResources().getString(R.string.opv4), getResources()
                                .getString(R.string.opv1));
                validate(tvOPV3, txtopv1,
                        getResources().getString(R.string.opv4), getResources()
                                .getString(R.string.opv2));
                validate(tvOPV3, tvOPV2, getResources()
                                .getString(R.string.opv4),
                        getResources().getString(R.string.opv3));

                int value3 = validate1(tvOPV3, txtopvbooster);
                if (value3 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtopvbooster.setText("");

                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvOPV3.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        txtopvbooster.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtopvbooster, txtopv0,
                        getResources().getString(R.string.opvbooster),
                        getResources().getString(R.string.opv1));
                validate(txtopvbooster, txtopv1,
                        getResources().getString(R.string.opvbooster),
                        getResources().getString(R.string.opv2));
                validate(txtopvbooster, tvOPV2,
                        getResources().getString(R.string.opvbooster),
                        getResources().getString(R.string.opv3));
                validate(txtopvbooster, tvOPV3,
                        getResources().getString(R.string.opvbooster),
                        getResources().getString(R.string.opv3));

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
        txtDPT1Pentavalent1.addTextChangedListener(new TextWatcher() {

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
                int value2 = validate1(txtDPT1Pentavalent1, tvDPT2Pentavalent2);
                int value1 = validate1(txtDPT1Pentavalent1, tvDPT3Pentavalent3);
                int value3 = validate1(txtDPT1Pentavalent1, txtDPTBooster);
                int value4 = validate1(txtDPT1Pentavalent1, txtDPTBoostertwo);
                if (value2 == 0 || value1 == 0 || value3 == 0 || value4 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tvDPT2Pentavalent2.setText("");
                                    tvDPT3Pentavalent3.setText("");
                                    txtDPTBoostertwo.setText("");
                                    txtDPTBooster.setText("");

                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtDPT1Pentavalent1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });
        tvDPT2Pentavalent2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tvDPT2Pentavalent2, txtDPT1Pentavalent1,
                        getResources().getString(R.string.dpt2), getResources()
                                .getString(R.string.dpt1));

                // TODO Auto-generated method stub

                int value1 = validate1(tvDPT2Pentavalent2, tvDPT3Pentavalent3);
                int value3 = validate1(tvDPT2Pentavalent2, txtDPTBooster);
                int value4 = validate1(tvDPT2Pentavalent2, txtDPTBoostertwo);
                if (value1 == 0 || value3 == 0 || value4 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tvDPT3Pentavalent3.setText("");
                                    txtDPTBooster.setText("");
                                    txtDPTBoostertwo.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvDPT2Pentavalent2.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tvDPT3Pentavalent3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    validate(tvDPT3Pentavalent3, txtDPT1Pentavalent1,
                            getResources().getString(R.string.dpt3),
                            getResources().getString(R.string.dpt1));
                    validate(tvDPT3Pentavalent3, tvDPT2Pentavalent2,
                            getResources().getString(R.string.dpt3),
                            getResources().getString(R.string.dpt2));

                    int value3 = validate1(tvDPT3Pentavalent3, txtDPTBooster);
                    int value4 = validate1(tvDPT3Pentavalent3, txtDPTBoostertwo);
                    if (value3 == 0 || value4 == 0) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                Immunization_Entry.this);
                        builder1.setMessage(getResources().getString(
                                R.string.alertmsg1));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        txtDPTBooster.setText("");
                                        txtDPTBoostertwo.setText("");
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                getResources().getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        tvDPT3Pentavalent3.setText(textdate);
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
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
        txtDPTBooster.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    validate(txtDPTBooster, txtDPT1Pentavalent1, getResources()
                            .getString(R.string.dptbooster), getResources()
                            .getString(R.string.dpt1));
                    validate(txtDPTBooster, tvDPT2Pentavalent2, getResources()
                            .getString(R.string.dptbooster), getResources()
                            .getString(R.string.dpt2));
                    validate(txtDPTBooster, tvDPT3Pentavalent3, getResources()
                            .getString(R.string.dptbooster), getResources()
                            .getString(R.string.dpt3));

                    int value4 = validate1(txtDPTBooster, txtDPTBoostertwo);
                    if (value4 == 0) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(
                                Immunization_Entry.this);
                        builder1.setMessage(getResources().getString(
                                R.string.alertmsg1));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        txtDPTBoostertwo.setText("");
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                getResources().getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        txtDPTBooster.setText(textdate);
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                }
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
        txtDPTBoostertwo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
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
                    validate(
                            txtDPTBoostertwo,
                            txtDPT1Pentavalent1,
                            getResources().getString(R.string.dptboostersecond),
                            getResources().getString(R.string.dpt1));
                    validate(
                            txtDPTBoostertwo,
                            tvDPT2Pentavalent2,
                            getResources().getString(R.string.dptboostersecond),
                            getResources().getString(R.string.dpt2));
                    validate(
                            txtDPTBoostertwo,
                            tvDPT3Pentavalent3,
                            getResources().getString(R.string.dptboostersecond),
                            getResources().getString(R.string.dpt3));
                    validate(txtDPTBoostertwo, txtDPTBooster, getResources()
                                    .getString(R.string.dptboostersecond),
                            getResources().getString(R.string.dptbooster));
                }

            }
        });
        txtPentavalent1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                int value1 = validate1(txtPentavalent1, txtPentavalent2);
                int value2 = validate1(txtPentavalent1, txtPentavalent3);
                if (value1 == 0 || value2 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    txtPentavalent2.setText("");
                                    txtPentavalent3.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtPentavalent1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        txtPentavalent2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtPentavalent2, txtPentavalent1, getResources()
                        .getString(R.string.Pentavalent2), getResources()
                        .getString(R.string.Pentavalent1));

                // TODO Auto-generated method stub

                int value2 = validate1(txtPentavalent2, txtPentavalent3);
                if (value2 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    txtPentavalent3.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtPentavalent2.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        txtPentavalent3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtPentavalent3, txtPentavalent1, getResources()
                        .getString(R.string.Pentavalent3), getResources()
                        .getString(R.string.Pentavalent1));
                validate(txtPentavalent3, txtPentavalent2, getResources()
                        .getString(R.string.Pentavalent3), getResources()
                        .getString(R.string.Pentavalent2));

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
        tvHepatitisB1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                validate(tvHepatitisB1, tvHepatitisB,
                        getResources().getString(R.string.hep1), getResources()
                                .getString(R.string.hep0));

                int value1 = validate1(tvHepatitisB1, tvHepatitisB2);
                int value2 = validate1(tvHepatitisB1, tvHepatitisB3);
                if (value1 == 0 || value2 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tvHepatitisB2.setText("");
                                    tvHepatitisB3.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvHepatitisB1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tvHepatitisB2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tvHepatitisB2, tvHepatitisB,
                        getResources().getString(R.string.hep2), getResources()
                                .getString(R.string.hep0));
                validate(tvHepatitisB2, tvHepatitisB1, getResources()
                                .getString(R.string.hep2),
                        getResources().getString(R.string.hep0));

                int value2 = validate1(tvHepatitisB2, tvHepatitisB3);
                if (value2 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tvHepatitisB3.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tvHepatitisB2.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tvHepatitisB3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tvHepatitisB3, tvHepatitisB,
                        getResources().getString(R.string.hep3), getResources()
                                .getString(R.string.hep0));
                validate(tvHepatitisB3, tvHepatitisB1, getResources()
                                .getString(R.string.hep3),
                        getResources().getString(R.string.hep0));
                validate(tvHepatitisB3, tvHepatitisB2, getResources()
                                .getString(R.string.hep3),
                        getResources().getString(R.string.hep2));

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
        txtMeasles1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                int value1 = validate1(txtMeasles1, txtMeasles2);
                if (value1 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    txtMeasles2.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtMeasles1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        txtMeasles2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtMeasles2, txtMeasles1,
                        getResources().getString(R.string.measles2),
                        getResources().getString(R.string.measles));

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
        tv_JEVaccine1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                int value1 = validate1(tv_JEVaccine1, tv_JEVaccine2);
                if (value1 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_JEVaccine2.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_JEVaccine1.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_JEVaccine2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_JEVaccine2, tv_JEVaccine1, getResources()
                        .getString(R.string.JEVaccine2), getResources()
                        .getString(R.string.JEVaccine1));

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
        txttt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                int value1 = validate1(txttt, tv_TT2);
                if (value1 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_TT2.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txttt.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_JEVaccine2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_JEVaccine2, tv_JEVaccine1, getResources()
                        .getString(R.string.JEVaccine2), getResources()
                        .getString(R.string.JEVaccine1));

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
        txtVitaminA.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

                int value1 = validate1(txtVitaminA, txtVitaminAtwo);
                int value2 = validate1(txtVitaminA, tv_vitamin3);
                int value3 = validate1(txtVitaminA, tv_vitamin4);
                int value4 = validate1(txtVitaminA, tv_vitamin5);
                int value5 = validate1(txtVitaminA, tv_vitamin6);
                int value6 = validate1(txtVitaminA, tv_vitamin7);
                int value7 = validate1(txtVitaminA, tv_vitamin8);
                int value8 = validate1(txtVitaminA, tv_vitamin9);

                if (value1 == 0 || value2 == 0 || value3 == 0 || value4 == 0
                        || value5 == 0 || value6 == 0 || value7 == 0
                        || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    txtVitaminAtwo.setText("");
                                    tv_vitamin3.setText("");
                                    tv_vitamin4.setText("");
                                    tv_vitamin5.setText("");
                                    tv_vitamin6.setText("");
                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtVitaminA.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        txtVitaminAtwo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(txtVitaminAtwo, txtVitaminA,
                        getResources().getString(R.string.vitamintwo),
                        getResources().getString(R.string.vitamin));

                int value2 = validate1(txtVitaminAtwo, tv_vitamin3);
                int value3 = validate1(txtVitaminAtwo, tv_vitamin4);
                int value4 = validate1(txtVitaminAtwo, tv_vitamin5);
                int value5 = validate1(txtVitaminAtwo, tv_vitamin6);
                int value6 = validate1(txtVitaminAtwo, tv_vitamin7);
                int value7 = validate1(txtVitaminAtwo, tv_vitamin8);
                int value8 = validate1(txtVitaminAtwo, tv_vitamin9);

                if (value2 == 0 || value3 == 0 || value4 == 0 || value5 == 0
                        || value6 == 0 || value7 == 0 || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin3.setText("");
                                    tv_vitamin4.setText("");
                                    tv_vitamin5.setText("");
                                    tv_vitamin6.setText("");
                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    txtVitaminAtwo.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin3.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin3, txtVitaminA,
                        getResources().getString(R.string.vitamin3),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin3, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin3),
                        getResources().getString(R.string.vitamintwo));

                int value3 = validate1(tv_vitamin3, tv_vitamin4);
                int value4 = validate1(tv_vitamin3, tv_vitamin5);
                int value5 = validate1(tv_vitamin3, tv_vitamin6);
                int value6 = validate1(tv_vitamin3, tv_vitamin7);
                int value7 = validate1(tv_vitamin3, tv_vitamin8);
                int value8 = validate1(tv_vitamin3, tv_vitamin9);

                if (value3 == 0 || value4 == 0 || value5 == 0 || value6 == 0
                        || value7 == 0 || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin4.setText("");
                                    tv_vitamin5.setText("");
                                    tv_vitamin6.setText("");
                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin3.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin4.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin4, txtVitaminA,
                        getResources().getString(R.string.vitamin4),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin4, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin4),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin4, tv_vitamin3,
                        getResources().getString(R.string.vitamin4),
                        getResources().getString(R.string.vitamin3));

                int value4 = validate1(tv_vitamin4, tv_vitamin5);
                int value5 = validate1(tv_vitamin4, tv_vitamin6);
                int value6 = validate1(tv_vitamin4, tv_vitamin7);
                int value7 = validate1(tv_vitamin4, tv_vitamin8);
                int value8 = validate1(tv_vitamin4, tv_vitamin9);

                if (value4 == 0 || value5 == 0 || value6 == 0 || value7 == 0
                        || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin5.setText("");
                                    tv_vitamin6.setText("");
                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin4.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin5.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin5, txtVitaminA,
                        getResources().getString(R.string.vitamin5),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin5, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin5),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin5, tv_vitamin3,
                        getResources().getString(R.string.vitamin5),
                        getResources().getString(R.string.vitamin3));
                validate(tv_vitamin5, tv_vitamin4,
                        getResources().getString(R.string.vitamin5),
                        getResources().getString(R.string.vitamin4));

                int value5 = validate1(tv_vitamin5, tv_vitamin6);
                int value6 = validate1(tv_vitamin5, tv_vitamin7);
                int value7 = validate1(tv_vitamin5, tv_vitamin8);
                int value8 = validate1(tv_vitamin5, tv_vitamin9);

                if (value5 == 0 || value6 == 0 || value7 == 0 || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin6.setText("");
                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin5.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin6.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin6, txtVitaminA,
                        getResources().getString(R.string.vitamin6),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin6, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin6),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin6, tv_vitamin3,
                        getResources().getString(R.string.vitamin6),
                        getResources().getString(R.string.vitamin3));
                validate(tv_vitamin6, tv_vitamin4,
                        getResources().getString(R.string.vitamin6),
                        getResources().getString(R.string.vitamin4));

                validate(tv_vitamin6, tv_vitamin5,
                        getResources().getString(R.string.vitamin6),
                        getResources().getString(R.string.vitamin5));

                int value6 = validate1(tv_vitamin6, tv_vitamin7);
                int value7 = validate1(tv_vitamin6, tv_vitamin8);
                int value8 = validate1(tv_vitamin6, tv_vitamin9);

                if (value6 == 0 || value7 == 0 || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin7.setText("");
                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin6.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin7.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin7, txtVitaminA,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin7, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin7, tv_vitamin3,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamin3));
                validate(tv_vitamin7, tv_vitamin4,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamin4));

                validate(tv_vitamin7, tv_vitamin5,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamin5));
                validate(tv_vitamin7, tv_vitamin6,
                        getResources().getString(R.string.vitamin7),
                        getResources().getString(R.string.vitamin6));

                int value7 = validate1(tv_vitamin7, tv_vitamin8);
                int value8 = validate1(tv_vitamin7, tv_vitamin9);

                if (value7 == 0 || value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin8.setText("");
                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin7.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin8.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin8, txtVitaminA,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin8, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin8, tv_vitamin3,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin3));
                validate(tv_vitamin8, tv_vitamin4,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin4));

                validate(tv_vitamin8, tv_vitamin5,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin5));
                validate(tv_vitamin8, tv_vitamin6,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin6));
                validate(tv_vitamin8, tv_vitamin7,
                        getResources().getString(R.string.vitamin8),
                        getResources().getString(R.string.vitamin7));

                int value8 = validate1(tv_vitamin8, tv_vitamin9);

                if (value8 == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            Immunization_Entry.this);
                    builder1.setMessage(getResources().getString(
                            R.string.alertmsg1));
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    tv_vitamin9.setText("");
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    tv_vitamin8.setText(textdate);
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

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
        tv_vitamin9.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                validate(tv_vitamin9, txtVitaminA,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin));
                validate(tv_vitamin9, txtVitaminAtwo,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamintwo));
                validate(tv_vitamin9, tv_vitamin3,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin3));
                validate(tv_vitamin9, tv_vitamin4,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin4));

                validate(tv_vitamin9, tv_vitamin5,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin5));
                validate(tv_vitamin9, tv_vitamin6,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin6));
                validate(tv_vitamin9, tv_vitamin7,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin7));
                validate(tv_vitamin9, tv_vitamin8,
                        getResources().getString(R.string.vitamin9),
                        getResources().getString(R.string.vitamin8));
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String smothername = "";
            smothername = extras.getString("MotherName");
            String sfathername = "";
            String sChildName = "";
            sfathername = extras.getString("HusbandName");
            sChildName = extras.getString("ChildName");
            sChildGUID = extras.getString("ChildGUID");
            sDOBCHILD = extras.getString("DOB");

            tvChildname.setText(Validate.returnStringValue(sChildName));

            tvfathername.setText(sfathername);
            tVmothername.setText(smothername);
            showDateCheckValidation();
            showData();
        }


    }

    public void txtclick(View view) {
        switch (view.getId()) {

            case R.id.TXTBCG:
                // do your code
                ShowDatePicker(TXTBCG, 0);

                break;

            case R.id.txtopv0:
                // do your code
                ShowDatePicker(txtopv0, 0);

                break;

            case R.id.tvHepatitisB:
                // do your code
                ShowDatePicker(tvHepatitisB, 0);

                break;

            case R.id.txtopv1:
                // do your code
                ShowDatePicker(txtopv1, 0);
                break;

            case R.id.txtDPT1Pentavalent1:
                ShowDatePicker(txtDPT1Pentavalent1, 1);
                // do your code

                break;
            case R.id.tvHepatitisB1:
                // do your code
                ShowDatePicker(tvHepatitisB1, 2);
                break;
            case R.id.tvOPV2:
                // do your code
                ShowDatePicker(tvOPV2, 0);

                break;
            case R.id.tvDPT2Pentavalent2:
                // do your code
                ShowDatePicker(tvDPT2Pentavalent2, 4);

                break;
            case R.id.tvHepatitisB2:
                // do your code
                ShowDatePicker(tvHepatitisB2, 5);

                break;
            case R.id.tvOPV3:
                // do your code
                ShowDatePicker(tvOPV3, 0);

                break;
            case R.id.tvDPT3Pentavalent3:
                // do your code
                ShowDatePicker(tvDPT3Pentavalent3, 7);
                break;

            case R.id.tvHepatitisB3:
                // do your code
                ShowDatePicker(tvHepatitisB3, 8);
                break;

            case R.id.txtMeasles1:
                // do your code
                ShowDatePicker(txtMeasles1, 0);
                break;
            case R.id.txtVitaminA:
                // do your code
                ShowDatePicker(txtVitaminA, 0);
                break;
            case R.id.txtPentavalent1:
                // do your code
                ShowDatePicker(txtPentavalent1, 3);
                break;
            case R.id.txtPentavalent2:
                // do your code
                ShowDatePicker(txtPentavalent2, 6);
                break;
            case R.id.txtPentavalent3:
                // do your code
                ShowDatePicker(txtPentavalent3, 9);
                break;
            case R.id.txtipv:
                // do your code
                ShowDatePicker(txtipv, 0);
                break;
            case R.id.txtipv2:
                // do your code
                ShowDatePicker(txtipv2, 0);
                break;
            case R.id.txttt:
                // do your code
                ShowDatePicker(txttt, 0);
                break;
            case R.id.txtMeasles2:
                // do your code
                ShowDatePicker(txtMeasles2, 0);
                break;
            case R.id.txtDPTBooster:
                // do your code
                ShowDatePicker(txtDPTBooster, 0);
                break;
            case R.id.txtDPTBoostertwo:
                // do your code
                ShowDatePicker(txtDPTBoostertwo, 0);
                break;
            case R.id.txtopvbooster:
                // do your code
                ShowDatePicker(txtopvbooster, 0);
                break;
            case R.id.txtVitaminAtwo:
                // do your code
                ShowDatePicker(txtVitaminAtwo, 0);
                break;
            case R.id.tv_JEVaccine1:
                ShowDatePicker(tv_JEVaccine1, 0);
                break;
            case R.id.tv_JEVaccine2:
                ShowDatePicker(tv_JEVaccine2, 0);
                break;
            case R.id.tv_vitamin3:
                ShowDatePicker(tv_vitamin3, 0);
                break;
            case R.id.tv_vitamin4:
                ShowDatePicker(tv_vitamin4, 0);
                break;
            case R.id.tv_vitamin5:
                ShowDatePicker(tv_vitamin5, 0);
                break;
            case R.id.tv_vitamin6:
                ShowDatePicker(tv_vitamin6, 0);
                break;
            case R.id.tv_vitamin7:
                ShowDatePicker(tv_vitamin7, 0);
                break;
            case R.id.tv_vitamin8:
                ShowDatePicker(tv_vitamin8, 0);
                break;
            case R.id.tv_vitamin9:
                ShowDatePicker(tv_vitamin9, 0);
                break;
            case R.id.tv_TT2:
                ShowDatePicker(tv_TT2, 0);
                break;
            case R.id.tv_MeaslesRubella:
                ShowDatePicker(tv_MeaslesRubella, 0);
                break;

            default:
                break;

        }
    }

    public void showDateCheckValidation() {

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = dfDate.format(c.getTime());

        Date d = null;
        Date d1 = null;
        // Calendar cal = Calendar.getInstance();
        try {
            d = dfDate.parse(sDOBCHILD);
            d1 = dfDate.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int diffInDays = (int) ((d1.getTime() - d.getTime()) / (1000 * 60 * 60 * 24));
        System.out.println(diffInDays);

        // sBCGDATE = TXTBCG.getText().toString();
        // stxtopv1 = txtopv1.getText().toString();
        // stxtDPT1Pentavalent1 = txtDPT1Pentavalent1.getText().toString();
        // stvHepatitisB1 = tvHepatitisB1.getText().toString();
        // stvOPV2 = tvOPV2.getText().toString();
        // stvDPT2Pentavalent2 = tvDPT2Pentavalent2.getText().toString();
        // stvHepatitisB2 = tvHepatitisB2.getText().toString();
        //
        // stvOPV3 = tvOPV3.getText().toString();
        // stvDPT3Pentavalent3 = tvDPT3Pentavalent3.getText().toString();
        // stvHepatitisB3 = tvHepatitisB3.getText().toString();
        // stxtMeasles1 = txtMeasles1.getText().toString();
        // stxtVitaminA = txtVitaminA.getText().toString();

        // /////********For Date Opv1*********/////
        if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
            tvOPV3.setEnabled(true);
            tvOPV2.setEnabled(true);
            txtopv1.setEnabled(true);

        } else if (diffInDays >= 42 && diffInDays >= 70) {
            tvOPV2.setEnabled(true);
            txtopv1.setEnabled(true);
        } else if (diffInDays >= 42) {

            txtopv1.setEnabled(true);
        }
        if (diffInDays >= 45) {
            txtipv.setEnabled(true);
        } else {
            txtipv.setEnabled(false);
        }
        if (diffInDays >= 105) {
            txtipv2.setEnabled(true);
        } else {
            txtipv2.setEnabled(false);
        }
        // /////********For Date dpt*********/////

//        if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
//            tvDPT3Pentavalent3.setEnabled(true);
//            tvDPT2Pentavalent2.setEnabled(true);
//            txtDPT1Pentavalent1.setEnabled(true);
//
//        } else if (diffInDays >= 42 && diffInDays >= 70) {
//            tvDPT2Pentavalent2.setEnabled(true);
//            txtDPT1Pentavalent1.setEnabled(true);
//        } else if (diffInDays >= 42) {
//
//            txtDPT1Pentavalent1.setEnabled(true);
//
//        }
//

        // /////********For Date hep*********/////
        if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
            tvHepatitisB3.setEnabled(true);
            tvHepatitisB2.setEnabled(true);
            tvHepatitisB1.setEnabled(true);

        } else if (diffInDays >= 42 && diffInDays >= 70) {
            tvHepatitisB2.setEnabled(true);
            tvHepatitisB1.setEnabled(true);
        } else if (diffInDays >= 42) {

            tvHepatitisB1.setEnabled(true);
        }

        // //////////***********valid for patvalent *******//////////////

        if (diffInDays >= 42 && diffInDays >= 70 && diffInDays >= 98) {
            txtPentavalent1.setEnabled(true);
            txtPentavalent2.setEnabled(true);
            txtPentavalent3.setEnabled(true);

        } else if (diffInDays >= 42 && diffInDays >= 70) {
            txtPentavalent1.setEnabled(true);
            txtPentavalent2.setEnabled(true);
        } else if (diffInDays >= 42) {

            txtPentavalent1.setEnabled(true);
        }

        // //////*******Valid FOr measles********/////

        if (diffInDays >= 270) {
            txtMeasles1.setEnabled(true);
            tv_MeaslesRubella.setEnabled(true);

        }

        // //////////********* VITAMION A***************////////////

        if (diffInDays >= 480) {
            txtDPTBooster.setEnabled(true);
            txtopvbooster.setEnabled(true);
            txtMeasles2.setEnabled(true);

        }

        if (diffInDays >= 270) {
            txtVitaminA.setEnabled(true);
            tv_JEVaccine1.setEnabled(true);

        }
        if (diffInDays >= 366) {
            txtVitaminAtwo.setEnabled(true);
        }
        if (diffInDays >= 480) {
            tv_JEVaccine2.setEnabled(true);
            if (diffInDays >= 548) {
                tv_vitamin3.setEnabled(true);
            }

        }
        if (diffInDays > 730) {
            tv_vitamin4.setEnabled(true);
        }
        if (diffInDays >= 913) {
            tv_vitamin5.setEnabled(true);
        }
        if (diffInDays >= 1096) {
            tv_vitamin6.setEnabled(true);
        }
        if (diffInDays >= 1278) {
            tv_vitamin7.setEnabled(true);
        }
        if (diffInDays >= 1461) {
            tv_vitamin8.setEnabled(true);
        }
        if (diffInDays >= 1825) {
            txtDPTBoostertwo.setEnabled(true);

        }
        if (diffInDays >= 1642) {
            tv_vitamin9.setEnabled(true);
        }
        if (diffInDays >= 3650) {
            txttt.setEnabled(true);
        }
        if (diffInDays >= (16 * 365)) {
            tv_TT2.setEnabled(true);
        }

    }

    @SuppressWarnings("unused")
    public void savedata() {

        String sBCGDATE = "";
        String stxtopv1 = "";
        String stxtDPT1Pentavalent1 = "";
        String stvHepatitisB1 = "";
        String stvOPV2 = "";
        String stvDPT2Pentavalent2 = "";
        String stvHepatitisB2 = "";
        String stvOPV3 = "";
        String stvDPT3Pentavalent3 = "";
        String stvHepatitisB3 = "";
        String stxtMeasles1 = "";
        String stxtVitaminA = "";
        String sPentavalent1 = "";
        String sPentavalent2 = "";
        String sPentavalent3 = "";
        String sopv0 = "";
        String sHepatitisB = "";
        String IPV = "", IPV2 = "";
        String Pentavalent1 = "";
        String DPTBooster = "";
        String OPVBooster = "";
        String MeaslesTwoDose = "";
        String VitaminAtwo = "";
        String DPTBoostertwo = "";
        String ChildTT = "";
        // add jitendra
        String JEVaccine1 = "";
        String JEVaccine2 = "";
        String VitaminA3 = "";
        String VitaminA4 = "";
        String VitaminA5 = "";
        String VitaminA6 = "";
        String VitaminA7 = "";
        String VitaminA8 = "";
        String VitaminA9 = "";
        String TT2 = "";
        String MeaslesRubella = "";

        sBCGDATE = Validate.changeDateFormat(TXTBCG.getText().toString());
        stxtopv1 = Validate.changeDateFormat(txtopv1.getText().toString());
        stxtDPT1Pentavalent1 = Validate.changeDateFormat(txtDPT1Pentavalent1
                .getText().toString());
        stvHepatitisB1 = Validate.changeDateFormat(tvHepatitisB1.getText()
                .toString());
        stvOPV2 = Validate.changeDateFormat(tvOPV2.getText().toString());
        stvDPT2Pentavalent2 = Validate.changeDateFormat(tvDPT2Pentavalent2
                .getText().toString());
        stvHepatitisB2 = Validate.changeDateFormat(tvHepatitisB2.getText()
                .toString());

        stvOPV3 = Validate.changeDateFormat(tvOPV3.getText().toString());
        stvDPT3Pentavalent3 = Validate.changeDateFormat(tvDPT3Pentavalent3
                .getText().toString());
        stvHepatitisB3 = Validate.changeDateFormat(tvHepatitisB3.getText()
                .toString());
        stxtMeasles1 = Validate.changeDateFormat(txtMeasles1.getText()
                .toString());
        stxtVitaminA = Validate.changeDateFormat(txtVitaminA.getText()
                .toString());
        sPentavalent1 = Validate.changeDateFormat(txtPentavalent1.getText()
                .toString());
        sPentavalent2 = Validate.changeDateFormat(txtPentavalent2.getText()
                .toString());
        sPentavalent3 = Validate.changeDateFormat(txtPentavalent3.getText()
                .toString());
        sopv0 = Validate.changeDateFormat(txtopv0.getText().toString());
        sHepatitisB = Validate.changeDateFormat(tvHepatitisB.getText()
                .toString());
        DPTBooster = Validate.changeDateFormat(txtDPTBooster.getText()
                .toString());
        OPVBooster = Validate.changeDateFormat(txtopvbooster.getText()
                .toString());
        MeaslesTwoDose = Validate.changeDateFormat(txtMeasles2.getText()
                .toString());
        VitaminAtwo = Validate.changeDateFormat(txtVitaminAtwo.getText()
                .toString());
        DPTBoostertwo = Validate.changeDateFormat(txtDPTBoostertwo.getText()
                .toString());
        ChildTT = Validate.changeDateFormat(txttt.getText().toString());
        IPV = Validate.changeDateFormat(txtipv.getText().toString());
        IPV2 = Validate.changeDateFormat(txtipv2.getText().toString());

        JEVaccine1 = Validate.changeDateFormat(tv_JEVaccine1.getText()
                .toString());
        JEVaccine2 = Validate.changeDateFormat(tv_JEVaccine2.getText()
                .toString());
        VitaminA3 = Validate.changeDateFormat(tv_vitamin3.getText().toString());
        VitaminA4 = Validate.changeDateFormat(tv_vitamin4.getText().toString());
        VitaminA5 = Validate.changeDateFormat(tv_vitamin5.getText().toString());
        VitaminA6 = Validate.changeDateFormat(tv_vitamin6.getText().toString());
        VitaminA7 = Validate.changeDateFormat(tv_vitamin7.getText().toString());
        VitaminA8 = Validate.changeDateFormat(tv_vitamin8.getText().toString());
        VitaminA9 = Validate.changeDateFormat(tv_vitamin9.getText().toString());
        TT2 = Validate.changeDateFormat(tv_TT2.getText().toString());
        MeaslesRubella = Validate.changeDateFormat(tv_MeaslesRubella.getText().toString());
        try {

            String Sql = "";
            Sql = "update tblChild set bcg='" + sBCGDATE + "',opv2='"
                    + stxtopv1 + "',dpt1='" + stxtDPT1Pentavalent1
                    + "',hepb2='" + stvHepatitisB1 + "',opv3='" + stvOPV2
                    + "',dpt2='" + stvDPT2Pentavalent2 + "',hepb3='"
                    + stvHepatitisB2 + "',opv4='" + stvOPV3 + "',dpt3='"
                    + stvDPT3Pentavalent3 + "',hepb4='" + stvHepatitisB3
                    + "',measeals='" + stxtMeasles1 + "',vitaminA='"
                    + stxtVitaminA + "',Pentavalent1='" + sPentavalent1
                    + "',Pentavalent2='" + sPentavalent2 + "',Pentavalent3='"
                    + sPentavalent3 + "',opv1='" + sopv0 + "',hepb1='"
                    + sHepatitisB + "',IPV='" + IPV + "',JEVaccine1='"
                    + JEVaccine1 + "',JEVaccine2='" + JEVaccine2
                    + "',VitaminA3='" + VitaminA3 + "',VitaminA4='" + VitaminA4
                    + "',VitaminA5='" + VitaminA5 + "',VitaminA6='" + VitaminA6
                    + "',VitaminA7='" + VitaminA7 + "',VitaminA8='" + VitaminA8
                    + "',VitaminA9='" + VitaminA9 + "',TT2='" + TT2
                    + "',IsEdited=1 ,ChildTT='" + ChildTT
                    + "',DPTBoostertwo = '" + DPTBoostertwo
                    + "',VitaminAtwo = '" + VitaminAtwo
                    + "',MeaslesTwoDose = '" + MeaslesTwoDose
                    + "',OPVBooster='" + OPVBooster + "',DPTBooster = '"
                    + DPTBooster + "',MeaslesRubella = '"
                    + MeaslesRubella + "',modified_by='"
                    + g.getsGlobalUserID() + "',modified_on='"
                    + Validate.getcurrentdate() + "',IPV2='"
                    + IPV2 + "' where ChildGUID='" + sChildGUID + "'";
            dataProvider.executeSql(Sql);

        } catch (Exception e) {
            // TODO: handle exception
        }

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
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();
                Intent i = new Intent(Immunization_Entry.this,
                        ImunizationChildList.class);
                finish();
                startActivity(i);

            }
        });

        // Display the dialog
        dialog.show();

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
        btnok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        // Display the dialog
        dialog.show();

    }

    public void validate(TextView tt1, TextView tt2, String msg1, String msg2) {
        if (tt1.getText().toString().length() > 0
                && tt2.getText().toString().length() > 0) {

            String date = tt1.getText().toString();
            String date2 = tt2.getText().toString();
            SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.US);
            Date d = null;
            Date d1 = null;

            try {
                d = dfDate.parse(date);
                d1 = dfDate.parse(date2);
                int diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));

                if (diffInDays < 28 || diffInDays < 0) {
                    tt1.setText("");
                    CustomAlert(msg1 + " "
                            + getResources().getString(R.string.msg) + " "
                            + msg2 + " "
                            + getResources().getString(R.string.msgdate));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    public int validate1(TextView tt1, TextView tt2) {
        int diffInDays = -1;
        if (tt1.getText().toString().length() > 0
                && tt2.getText().toString().length() > 0) {

            String date = tt2.getText().toString();
            String date2 = tt1.getText().toString();
            SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.US);
            Date d = null;
            Date d1 = null;

            try {
                d = dfDate.parse(date);
                d1 = dfDate.parse(date2);
                diffInDays = (int) ((d.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
                if (diffInDays <= 30) {
                    diffInDays = 0;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return diffInDays;

    }

    public void showData() {
        ChildImmun = dataProvider.ShowCHildImmunizationdata(sChildGUID);

        if (ChildImmun != null && ChildImmun.size() > 0) {
            TXTBCG.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                    .get(0).getBcg())));
            txtopv1.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                    .get(0).getOpv2())));
            txtDPT1Pentavalent1.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getDpt1())));
            tvHepatitisB1.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getHepb2())));
            tvOPV2.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                    .get(0).getOpv3())));
            tvDPT2Pentavalent2.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getDpt2())));
            tvHepatitisB2.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getHepb3())));
            tvOPV3.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                    .get(0).getOpv4())));
            tvDPT3Pentavalent3.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getDpt3())));
            tvHepatitisB3.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getHepb4())));
            txtMeasles1.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getMeaseals())));
            txtVitaminA.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getVitaminA())));

            txtPentavalent1.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getPentavalent1())));
            txtPentavalent2.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getPentavalent2())));
            txtPentavalent3.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getPentavalent3())));

            txtopv0.setText(String.valueOf(Validate.changeDateFormat(ChildImmun
                    .get(0).getOpv1())));
            tvHepatitisB.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getHepb1())));
            txtipv.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getIPV())));
            txtipv2.setText(String.valueOf(Validate
                    .changeDateFormat(ChildImmun.get(0).getIPV2())));
            if (String.valueOf(ChildImmun.get(0).getDpt1()).length() > 0
                    || String.valueOf(ChildImmun.get(0).getHepb2()).length() > 0) {
                txtPentavalent1.setEnabled(false);
                //   txtDPT1Pentavalent1.setEnabled(true);
                tvHepatitisB1.setEnabled(true);
            } else if (String.valueOf(ChildImmun.get(0).getPentavalent1())
                    .length() > 0) {
                txtPentavalent1.setEnabled(true);
                txtDPT1Pentavalent1.setEnabled(false);
                tvHepatitisB1.setEnabled(false);
            }

            if (String.valueOf(ChildImmun.get(0).getDpt2()).length() > 0
                    || String.valueOf(ChildImmun.get(0).getHepb3()).length() > 0) {
                txtPentavalent2.setEnabled(false);
                //tvDPT2Pentavalent2.setEnabled(true);
                tvHepatitisB2.setEnabled(true);
            } else if (String.valueOf(ChildImmun.get(0).getPentavalent2())
                    .length() > 0) {
                txtPentavalent2.setEnabled(true);
                tvDPT2Pentavalent2.setEnabled(false);
                tvHepatitisB2.setEnabled(false);
            }

            if (String.valueOf(ChildImmun.get(0).getDpt3()).length() > 0
                    || String.valueOf(ChildImmun.get(0).getHepb4()).length() > 0) {
                txtPentavalent3.setEnabled(false);
                ///  tvDPT3Pentavalent3.setEnabled(true);
                tvHepatitisB3.setEnabled(true);
            } else if (String.valueOf(ChildImmun.get(0).getPentavalent3())
                    .length() > 0) {
                txtPentavalent3.setEnabled(true);
                tvDPT3Pentavalent3.setEnabled(false);
                tvHepatitisB3.setEnabled(false);
            }
            txtMeasles2.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getMeaslesTwoDose()));
            txtVitaminAtwo.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminAtwo()));
            tv_JEVaccine1.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getJEVaccine1()));
            tv_JEVaccine2.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getJEVaccine2()));
            tv_vitamin3.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA3()));
            tv_vitamin4.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA4()));
            tv_vitamin5.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA5()));
            tv_vitamin6.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA6()));
            tv_vitamin7.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA7()));
            tv_vitamin8.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA8()));
            tv_vitamin9.setText(Validate.changeDateFormat(ChildImmun.get(0)
                    .getVitaminA9()));
            tv_TT2.setText(Validate
                    .changeDateFormat(ChildImmun.get(0).getTT2()));
            tv_MeaslesRubella.setText(Validate
                    .changeDateFormat(ChildImmun.get(0).getMeaslesRubella()));

        }

        if (g.getiGlobalRoleID() == 4) {
            String sVerify = "Select Verify from tblAFVerify where ModuleGUID='" + g.getsGlobalChildGUID() + "'";
            if (dataProvider.getRecord(sVerify).equals("1")) {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.buttonorange));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
            } else if (dataProvider.getRecord(sVerify).equals("2")) {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.DarkGreen));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
            }  else if (dataProvider.getRecord(sVerify).equals("3"))  {
                btnNotverified.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedOk.setBackgroundColor(getResources().getColor(R.color.lightgray));
                btnVerifiedNotOk.setBackgroundColor(getResources().getColor(R.color.DarkRed));
            }
        }

    }

    public void ShowDatePicker(final TextView tVvisit, final int itextboxvalue) {

        // TODO Auto-generated method stub
        datepic = new Dialog(this, R.style.CustomTheme);

        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker1);
        Button btnset = (Button) datepic.findViewById(R.id.set);
        // Button btnclear = (Button) datepic.findViewById(R.id.btnclear);
        Button btncancel = (Button) datepic.findViewById(R.id.cancle);
        Button btnclear = (Button) datepic.findViewById(R.id.clear);
        btnclear.setVisibility(View.VISIBLE);

        datepic.show();

        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        Date date = new Date();
        datetext.setMaxDate((long) date.getTime());
        try {

            Date date1;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date1 = formatter.parse(sDOBCHILD);
            long minDate = date1.getTime();
            datetext.setMinDate(minDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
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
                textdate = tVvisit.getText().toString();
                String sSellectedDate = date + "-" + mnth + "-"
                        + String.valueOf(year);
                tVvisit.setText(sSellectedDate);
                // if(itextboxvalue == 1 ){
                // showvalidation();
                // }

                if (itextboxvalue == 1 || itextboxvalue == 2
                        || itextboxvalue == 3) {
                    if (txtDPT1Pentavalent1.getText().toString().length() > 0
                            || tvHepatitisB1.getText().toString().length() > 0) {

                        txtPentavalent1.setEnabled(false);
                        //    txtDPT1Pentavalent1.setEnabled(true);
                        tvHepatitisB1.setEnabled(true);
                    } else if (txtPentavalent1.getText().toString().length() > 0) {
                        txtDPT1Pentavalent1.setEnabled(false);
                        tvHepatitisB1.setEnabled(false);
                        txtPentavalent1.setEnabled(true);

                    }
                } else if (itextboxvalue == 4 || itextboxvalue == 5
                        || itextboxvalue == 6) {
                    if (tvDPT2Pentavalent2.getText().toString().length() > 0
                            || tvHepatitisB2.getText().toString().length() > 0) {

                        txtPentavalent2.setEnabled(false);
                        //   tvDPT2Pentavalent2.setEnabled(true);
                        tvHepatitisB2.setEnabled(true);
                    } else if (txtPentavalent2.getText().toString().length() > 0) {
                        tvDPT2Pentavalent2.setEnabled(false);
                        tvHepatitisB2.setEnabled(false);
                        txtPentavalent2.setEnabled(true);

                    }

                } else if (itextboxvalue == 7 || itextboxvalue == 8
                        || itextboxvalue == 9) {
                    if (tvDPT3Pentavalent3.getText().toString().length() > 0
                            || tvHepatitisB3.getText().toString().length() > 0) {

                        txtPentavalent3.setEnabled(false);
                        // tvDPT3Pentavalent3.setEnabled(true);
                        tvHepatitisB3.setEnabled(true);
                    } else if (txtPentavalent3.getText().toString().length() > 0) {
                        tvDPT3Pentavalent3.setEnabled(false);
                        tvHepatitisB3.setEnabled(false);
                        txtPentavalent3.setEnabled(true);

                    }

                }

                datepic.dismiss();
            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                tVvisit.setText("");
                if (itextboxvalue == 1 || itextboxvalue == 2) {
                    if (txtDPT1Pentavalent1.getText().toString().length() == 0
                            && tvHepatitisB1.getText().toString().length() == 0) {
                        txtPentavalent1.setEnabled(true);
                    }

                } else if (itextboxvalue == 3) {
                    // txtDPT1Pentavalent1.setEnabled(true);
                    tvHepatitisB1.setEnabled(true);
                }

                if (itextboxvalue == 4 || itextboxvalue == 5) {
                    if (tvDPT2Pentavalent2.getText().toString().length() == 0
                            && tvHepatitisB2.getText().toString().length() == 0) {
                        txtPentavalent2.setEnabled(true);
                    }

                } else if (itextboxvalue == 6) {
                    //  tvDPT2Pentavalent2.setEnabled(true);
                    tvHepatitisB2.setEnabled(true);
                }

                if (itextboxvalue == 7 || itextboxvalue == 8) {
                    if (tvDPT3Pentavalent3.getText().toString().length() == 0
                            && tvHepatitisB3.getText().toString().length() == 0) {
                        txtPentavalent3.setEnabled(true);
                    }

                } else if (itextboxvalue == 9) {
                    // tvDPT3Pentavalent3.setEnabled(true);
                    tvHepatitisB3.setEnabled(true);
                }

                datepic.dismiss();

            }
        });

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (validate.RetriveSharepreferenceInt("QR") == 1) {
            validate.SaveSharepreferenceInt("QR", 0);
            Intent i = new Intent(Immunization_Entry.this,
                    Dashboard_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(Immunization_Entry.this,
                    ImunizationChildList.class);
            finish();
            startActivity(i);
        }
    }


}