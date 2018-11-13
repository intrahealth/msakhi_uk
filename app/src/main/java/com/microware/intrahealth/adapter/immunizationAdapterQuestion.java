package com.microware.intrahealth.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblmstimmunizationQues;

@SuppressLint({"SimpleDateFormat", "InflateParams"})
public class immunizationAdapterQuestion extends BaseAdapter {

    ArrayList<tbl_pregnantwomen> pregnant;
    Context context;
    Global global;
    DataProvider dataProvider;
    ArrayList<tblmstimmunizationQues> Questions;
    int iflag = 0;
    Dialog timepic;
    TimePicker time;
    String sDueVisits = "";
    String LMPDate = "";
    String RegistrationDate = "";
    int CurrentPage = 0;
    Dialog PicVideo_PreviewPopUp;
    TextView weight1, weight2, weight3, weight4, bp1, bp2, bp3, bp4, hb1, hb2,
            hb3, hb4, us1, us2, us3, us4, ua1, ua2, ua3, ua4;

    public immunizationAdapterQuestion(Context context,
                                       ArrayList<tblmstimmunizationQues> Questions, int currentpage) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.Questions = Questions;
        this.CurrentPage = currentpage;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(
                    R.layout.immunnizationquestion_adapter, null);
        } else {
            gridview = convertView;
        }

        dataProvider = new DataProvider(context);
        global = (Global) context.getApplicationContext();
        TextView tvQuestions = (TextView) gridview
                .findViewById(R.id.tvQuestions);
        TextView tvQuestions2 = (TextView) gridview
                .findViewById(R.id.tvQuestions2);
        TextView ansfield = (TextView) gridview.findViewById(R.id.ansfield);
        final TextView ansQ = (TextView) gridview.findViewById(R.id.ans);
        EditText etans = (EditText) gridview.findViewById(R.id.etans);
        TextView ansy = (TextView) gridview.findViewById(R.id.ansy);
        TextView ansn = (TextView) gridview.findViewById(R.id.ansn);

        final TextView tvdate = (TextView) gridview.findViewById(R.id.tvdate);
        final TextView tvtime = (TextView) gridview.findViewById(R.id.tvtime);
        final TextView tvDOB = (TextView) gridview.findViewById(R.id.tvDOB);
        RadioGroup rdgrp = (RadioGroup) gridview.findViewById(R.id.rdgrp);
        TableRow tblrdgrp2 = (TableRow) gridview.findViewById(R.id.tblrdgrp2);
        TableRow tblrdgrp3 = (TableRow) gridview.findViewById(R.id.tblrdgrp3);
        TableRow tbldatemonth = (TableRow) gridview
                .findViewById(R.id.tbldatemonth);
        TableRow tblrdgrptime = (TableRow) gridview
                .findViewById(R.id.tblrdgrptime);
        if (Questions.get(CurrentPage).getAnsField() != null
                && Questions.get(CurrentPage).getAnsField().length() > 0) {
            ansfield.setText(Questions.get(CurrentPage).getAnsField());
        } else {
            ansfield.setText("");
        }
        tblrdgrp2.setVisibility(View.GONE);
        tblrdgrp3.setVisibility(View.GONE);
        tblrdgrptime.setVisibility(View.GONE);
        tvDOB.setVisibility(View.GONE);
        String[] newage;
        if (CurrentPage == 1) {

            if (global.getDateMonth() != null
                    && global.getDateMonth().length() > 0) {
                newage = global.getDateMonth().split("/");
                tvDOB.setText(String.valueOf(newage[0] + " months " + newage[1]
                        + " days"));
                tvDOB.setVisibility(View.VISIBLE);
                tbldatemonth.setVisibility(View.VISIBLE);
            }
        } else {
            tvDOB.setVisibility(View.GONE);
            tbldatemonth.setVisibility(View.GONE);
        }
        String ans = Questions.get(CurrentPage).getOinfo();
        tvQuestions.setText(Questions.get(CurrentPage).getFtext());
//        tvQuestions.setTextColor(Color.BLACK);
//        tvQuestions.setBackgroundColor(Color.GRAY);

        if (Questions.get(CurrentPage).getY_qid() > 0) {
            ansy.setText(String.valueOf(Questions.get(CurrentPage).getY_qid()));
        }
        if (Questions.get(CurrentPage).getN_qid() > 0) {
            ansn.setText(String.valueOf(Questions.get(CurrentPage).getN_qid()));

        }
        rdgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                    case 0:
                        ansQ.setText("0");
                        break;
                    case 1:
                        ansQ.setText("1");
                        break;
                    case 2:
                        ansQ.setText("2");
                        break;
                    case 3:
                        ansQ.setText("3");
                        break;
                    case 4:
                        ansQ.setText("4");
                        break;
                    case 5:
                        ansQ.setText("5");
                        break;
                    case 6:
                        ansQ.setText("6");
                        break;
                }

            }
        });
        tvdate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ShowDatePicker(tvdate, ansQ);
            }
        });
        tvtime.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                timepic = new Dialog(v.getContext(), R.style.AppTheme);
                Window window = timepic.getWindow();
                // window.requestFeature(Window.FEATURE_NO_TITLE);
                timepic.setContentView(R.layout.timepicker);
                timepic.setCanceledOnTouchOutside(true);
                Button btnset = (Button) timepic.findViewById(R.id.set);
                Button btnclear = (Button) timepic.findViewById(R.id.clear);
                Button btncancel = (Button) timepic.findViewById(R.id.cancel);

                timepic.show();
                timepic.getWindow().setLayout(LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT);
                final TimePicker timetext = (TimePicker) timepic
                        .findViewById(R.id.idfortime);
                btncancel.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        // TODO Auto-generated

                        timepic.dismiss();

                    }
                });

                btnclear.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        // TODO Auto-generated

                        tvtime.setText("");
                        timepic.dismiss();

                    }
                });
                btnset.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        int hours = timetext.getCurrentHour();
                        int mins = timetext.getCurrentMinute();

                        String hour = "";
                        if (hours < 10)
                            hour = "0" + hours;
                        else
                            hour = String.valueOf(hours);

                        String timeSet = "";
                        if (hours > 12) {
                            hours -= 12;
                            timeSet = "";
                        } else if (hours == 0) {
                            hours += 12;
                            timeSet = "";
                        } else if (hours == 12)
                            timeSet = "";
                        else
                            timeSet = "";

                        String min = "";
                        if (mins < 10)
                            min = "0" + mins;
                        else
                            min = String.valueOf(mins);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour)
                                .append(':').append(min).append(" ")
                                .append(timeSet).toString();
                        tvtime.setText(aTime);
                        ansQ.setText(aTime);
                        timepic.dismiss();
                    }

                });

            }

        });
        if (global.getsGlobalChildGUID() != null
                && global.getsGlobalChildGUID().length() > 0) {

            showdata(Questions.get(CurrentPage).getAnsField(), ans, rdgrp,
                    tvdate, ansQ, tblrdgrp2, tblrdgrp3, etans);
        }

        if (ans != null && ans.length() > 0) {
            if (ans.contains("$")) {
                tblrdgrp3.setVisibility(View.GONE);
                tblrdgrptime.setVisibility(View.GONE);
                String[] ar = ans.split("[$]");

                final RadioButton[] rb = new RadioButton[ar.length];
                if (rdgrp.getChildCount() == 0) {
                    for (int i = 0; i < ar.length; i++) {
                        rdgrp.setWeightSum(ar.length);
                        rb[i] = new RadioButton(context);
                        int id = i + 1;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                700, LayoutParams.MATCH_PARENT, 1f);
                        rb[i].setLayoutParams(params);
                        rdgrp.addView(rb[i]); // the RadioButtons
                        // are added to the
                        // radioGroup
                        // instead of the
                        // layout
                        rb[i].setText(ar[i]);
                        rb[i].setId(id);
                        rb[i].setTextColor(Color.BLACK);

                    }
                }
                // submit.setOnClickListener(new
                // View.OnClickListener() {
                // public void onClick(View v) {
                // for(int i = 0; i < 5; i++) {
                // rg.removeView(rb[i]);//now the RadioButtons
                // are in the RadioGroup
                // }
                // ll.removeView(submit);
                // Questions();
                // }
                // });
            } else if (ans.equalsIgnoreCase("Date")) {
                tblrdgrp2.setVisibility(View.VISIBLE);
                tblrdgrp3.setVisibility(View.GONE);
                tblrdgrptime.setVisibility(View.GONE);

            } else {

            }

        }


        return gridview;
    }

    @SuppressWarnings("unused")
    public void showdata(String Field, String ans, RadioGroup rdgrp,
                         TextView tvdate, TextView ansQ, TableRow tblrdgrp2,
                         TableRow tblrdgrp3, EditText etans) {
        int newvalue = 0;
        if (ans != null && ans.length() > 0) {
            if (ans.contains("$")) {

                String sql = "select " + Field
                        + " from tblmstimmunizationANS where ChildGUID='"
                        + global.getsGlobalChildGUID()
                        + "' and ImmunizationGUID='"
                        + global.getImmunizationGUID() + "'";
                tblrdgrp3.setVisibility(View.GONE);
                String count = dataProvider.getRecord(sql);
                if (count != null && count.length() > 0) {
                    int newid = Integer.valueOf(count);
                    String[] ar = ans.split("[$]");

                    final RadioButton[] rb = new RadioButton[ar.length];
                    if (rdgrp.getChildCount() == 0) {
                        for (int i = 0; i < ar.length; i++) {
                            rdgrp.setWeightSum(ar.length);
                            rb[i] = new RadioButton(context);
                            int id = i + 1;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LayoutParams.WRAP_CONTENT,
                                    LayoutParams.MATCH_PARENT, 1f);
                            rb[i].setLayoutParams(params);
                            rdgrp.addView(rb[i]); // the RadioButtons are added
                            // to the radioGroup instead
                            // of the layout
                            rb[i].setText(ar[i]);
                            rb[i].setId(id);
                            rb[i].setTextColor(Color.BLACK);
                            if (newid == i + 1) {
                                rb[i].setChecked(true);
                            } else {
                                rb[i].setChecked(false);
                            }

                        }
                    }
                    newvalue = 1;
                } else {
                    String[] ar = ans.split("[$]");

                    final RadioButton[] rb = new RadioButton[ar.length];
                    if (rdgrp.getChildCount() == 0) {
                        for (int i = 0; i < ar.length; i++) {
                            rdgrp.setWeightSum(ar.length);
                            rb[i] = new RadioButton(context);
                            int id = i + 1;
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LayoutParams.WRAP_CONTENT,
                                    LayoutParams.MATCH_PARENT, 1f);
                            rb[i].setLayoutParams(params);
                            rdgrp.addView(rb[i]); // the RadioButtons are added
                            // to the radioGroup instead
                            // of the layout
                            rb[i].setText(ar[i]);
                            rb[i].setId(id);
                            rb[i].setTextColor(Color.BLACK);

                        }
                    }

                }

            } else if (ans.equalsIgnoreCase("Date")) {
                String sql = "select " + Field
                        + " from tblmstimmunizationANS where ChildGUID='"
                        + global.getsGlobalChildGUID()
                        + "' and ImmunizationGUID='"
                        + global.getImmunizationGUID() + "'";
                String count = dataProvider.getRecord(sql);
                tblrdgrp2.setVisibility(View.VISIBLE);
                tblrdgrp3.setVisibility(View.GONE);

                if (count != null && count.length() > 0) {
                    tvdate.setText(Validate.changeDateFormat(count));
                    ansQ.setText(count);
                    newvalue = 1;
                }

            }
        }

    }

    public void ShowDatePicker(final TextView txt, final TextView datatv2) {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        final Dialog datepic = new Dialog(context);
        Window window = datepic.getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        datepic.setContentView(R.layout.datetimepicker);
        datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

        Button btnset = (Button) datepic.findViewById(R.id.set);
        Button btnCancle = (Button) datepic.findViewById(R.id.cancle);
        btnCancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated

                datepic.dismiss();

            }
        });
        final DatePicker datetext = (DatePicker) datepic
                .findViewById(R.id.idfordate);
        datetext.setMaxDate(System.currentTimeMillis());
        if (txt != null && txt.getText() != ""
                && txt.getText().toString().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date;
            Calendar c = Calendar.getInstance(Locale.US);
            try {
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

            @Override
            public void onClick(View v) {
                datetext.clearFocus();
                int Mdate = datetext.getDayOfMonth();

                int month = datetext.getMonth() + 1;
                int year = datetext.getYear();
                String day = "", mnth = "";
                if (Mdate < 10) {
                    day = "0" + Mdate;
                } else {
                    day = "" + Mdate;
                }
                if (month < 10) {
                    mnth = "0" + month;
                } else {
                    mnth = "" + month;
                }

                @SuppressWarnings("unused")
                Date date = null;
                String sSellectedDate = day + "-" + mnth + "-"
                        + String.valueOf(year);
                txt.setText(sSellectedDate);

                // datatv = Validate.changeDateFormat(sSellectedDate);
                datatv2.setText(Validate.changeDateFormat(sSellectedDate));

                // TODO Auto-generated

                datepic.dismiss();

            }
        });
        datepic.show();

    }
}