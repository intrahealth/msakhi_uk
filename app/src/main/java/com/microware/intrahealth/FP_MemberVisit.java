package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.microware.intrahealth.adapter.FP_MemberVisitAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.fragment.FP_EligbleCouple;
import com.microware.intrahealth.object.tblmstFPQues;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class FP_MemberVisit extends Activity {
    // ExtendedViewPager viewPager;
    PagerAdapter adapter;
    int[] flag;
    String[] rank;
    static TextView pagenumber, tvans, title;
    ImageView imgleft, imgright;
    ViewPager mViewPager;
    DataProvider dataProvider;
    Global global;
    GridView gridanc;
    MediaPlayer clip = null;
    String Filepath = "";
    private static final String IMAGE_DIRECTORY_NAME1 = "/mSakhi/Media/";
    TimePicker time;
    String Question = "";
    Dialog timepic;
    Button next;
    int CurrentPage = 0;
    int value = 0;
    String ss;
    ArrayList<tblmstFPQues> Questions = new ArrayList<tblmstFPQues>();
    ArrayList<tblmstFPQues> Questions1 = new ArrayList<tblmstFPQues>();
    // ArrayList<q_bank> child = new ArrayList<q_bank>();
    // ArrayList<q_bank> mother = new ArrayList<q_bank>();
    // ArrayList<Tbl_PncHomeVisitAns> pnc = new
    // ArrayList<Tbl_PncHomeVisitAns>();
    Button imgNext, imgEnd, Pause, Play,rePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.fp_membervisit);

        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        pagenumber = (TextView) findViewById(R.id.pagenumber);
        title = (TextView) findViewById(R.id.title);

        mViewPager = (ViewPager) findViewById(R.id.treatmentviewpager);

        imgNext = (Button) findViewById(R.id.imgNext);
        imgEnd = (Button) findViewById(R.id.imgEnd);
        Pause = (Button) findViewById(R.id.Pause);
        Play = (Button) findViewById(R.id.Play);
        rePlay = (Button) findViewById(R.id.rePlay);
        Intent intent = getIntent();
        ss = intent.getStringExtra("counselling");

        if (ss.equalsIgnoreCase("0")) {
            Questions = dataProvider.getFP_Ques("", 0);
            // Questions1 = dataProvider.getFP_Ques("", 3);
        }
        if (ss.equalsIgnoreCase("1"))
            Questions = dataProvider.getFP_Ques("", 1);
        if (ss.equalsIgnoreCase("2"))
            Questions = dataProvider.getFP_Ques("", 2);

        gridanc = (GridView) findViewById(R.id.gridanc);
        next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final TextView ansfield = (TextView) gridanc
                        .findViewById(R.id.ansfield);
                final TextView ansQ = (TextView) gridanc.findViewById(R.id.ans);
                final TextView tvdate = (TextView) gridanc
                        .findViewById(R.id.tvdate);

                // TextView tvQuestions = (TextView) gridanc
                // .findViewById(R.id.tvQuestions);

                if (CurrentPage < Questions.size() && value != 1000) {
                    if (clip != null && clip.isPlaying()) {
                        clip.stop();
                    }
                    int pageno = 0;
                    Filepath = "";
                    if (ansfield.getText() != null
                            && ansfield.getText().toString().length() > 0) {
                        if (CurrentPage == 62 || CurrentPage == 59) {
                            if (tvdate.getText() != null
                                    && tvdate.getText().toString().length() > 0) {
                                if (CurrentPage == 59) {
                                    if (global.getiGlobalRoleID() != 3) {

                                        savedata(
                                                ansfield.getText().toString(),
                                                Validate.changeDateFormat(tvdate
                                                        .getText().toString()));
                                    }
                                    CurrentPage = CurrentPage + 1;
                                } else if (CurrentPage == 62) {
                                    if (global.getiGlobalRoleID() != 3) {
                                        savedata(
                                                ansfield.getText().toString(),
                                                Validate.changeDateFormat(tvdate
                                                        .getText().toString()));
                                    }

                                    Intent i = new Intent(FP_MemberVisit.this,
                                            FP_AA.class);
                                    finish();
                                    startActivity(i);

                                }

                            }
                        } else if (ansQ.getText() != null
                                && ansQ.getText().toString().length() > 0) {
                            if (global.getiGlobalRoleID() != 3) {
                                savedata(ansfield.getText().toString(), ansQ
                                        .getText().toString());
                            }
                            if (ansQ.getText().toString().equalsIgnoreCase("1")
                                    && Questions.get(CurrentPage).getY_qid() > 0) {

                                pageno = Integer.valueOf(Questions.get(
                                        CurrentPage).getY_qid());

                                if (pageno == 999) {
                                    Intent i = new Intent(FP_MemberVisit.this,
                                            FP_AA.class);
                                    if (clip != null && clip.isPlaying()) {
                                        clip.stop();
                                    }
                                    finish();
                                    startActivity(i);
                                }
                                CurrentPage = pageno - 1;
                            } else if (ansQ.getText().toString()
                                    .equalsIgnoreCase("2")
                                    && Questions.get(CurrentPage).getN_qid() > 0) {
                                pageno = Integer.valueOf(Questions.get(
                                        CurrentPage).getN_qid());
                                if (pageno == 999
                                        || Questions.get(CurrentPage)
                                        .getFinishid() == 1) {
                                    if (clip != null && clip.isPlaying()) {
                                        clip.stop();
                                    }
                                    finish();
                                }
                                CurrentPage = pageno - 1;
                            } else {
                                if (pageno == 999
                                        || Questions.get(CurrentPage)
                                        .getFinishid() == 1) {
                                    Intent i = new Intent(FP_MemberVisit.this,
                                            FP_AA.class);
                                    if (clip != null && clip.isPlaying()) {
                                        clip.stop();
                                    }
                                    finish();
                                    startActivity(i);
                                }

                                CurrentPage = CurrentPage + 1;
                            }
                        }

                    } else {
                        if (pageno == 999
                                || Questions.get(CurrentPage).getFinishid() == 1) {
                            Intent i = new Intent(FP_MemberVisit.this,
                                    FP_AA.class);
                            if (clip != null && clip.isPlaying()) {
                                clip.stop();
                            }
                            finish();

                            startActivity(i);
                        }
                        CurrentPage = CurrentPage + 1;
                    }

                    fillgrid();

                } else {

                    Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
                    finish();
                    startActivity(i);
                }
            }
        });

        fillgrid();
    }

    public void onclickButton(View view) {

        Intent in;
        switch (view.getId()) {

            case R.id.Pause:
                try {
                    if (clip != null && clip.isPlaying()) {
                        clip.pause();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.Play:

                try {
                    if (clip != null) {
                        clip.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.rePlay:

                try {
                    if (!Filepath .equalsIgnoreCase("")&&Filepath.length()>0) {
                        clip.reset();
                        // clip=new MediaPlayer();
                        clip.setDataSource(Filepath);
                        clip.prepare();
                        clip.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }

    public void refreshArrayList() {
        for (int i = 0; i < Questions.size(); i++) {
            for (int j = 0; j < Questions1.size(); i++) {
                if (Questions1.get(j).getFtext()
                        .equalsIgnoreCase(Questions.get(i).getFtext())) {
                    Questions.get(i).setFtext("");
                }
            }
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

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        // Display the dialog
        dialog.show();

    }

    @SuppressWarnings("unused")
    private void savedata(String ansfield, String ansQ) {
        // TODO Auto-generated method stub
        String FPAns_Guid = Validate.random();
        String CurrentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US)
                .format(new Date());
        CurrentDate = Validate.changeDateFormat(CurrentDate);
        String WomenName_Guid = "";
        String flag = "";
        String dtaans = "";
        int AshaID = 0;
        int ANMID = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
        }
        if (Questions.get(CurrentPage).getAns() != null
                && Questions.get(CurrentPage).getAns().length() > 0)
            ansfield = Questions.get(CurrentPage).getAns();
        WomenName_Guid = global.getGlobalHHFamilyMemberGUID();
        String sql = "select count(*) from tblFP_visit where womenName_Guid='"
                + WomenName_Guid + "' and VisitDate='" + CurrentDate + "'";
        int aa = dataProvider.getMaxRecord(sql);
        if (aa == 0) {
            flag = "I";
        } else {
            flag = "U";
        }
        if (ansQ != null && ansQ.length() > 0) {
            dtaans = ansQ;
        }

        String pncguid = Validate.random();
        // global.setsPncGUID(pncguid);
        int ir = 0;
        ir = dataProvider.saveFP(AshaID, ANMID, dtaans, WomenName_Guid,
                pncguid, FPAns_Guid, ansfield, flag, global.getsGlobalUserID());
        // if (ir == 1) {
        // CustomAlerts(getResources().getString(R.string.savesuccessfully));
        // } else {
        // System.out.print("Not Saved");
        // }

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
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btnok = (Button) dialog.findViewById(R.id.btn_ok);
        btnok.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View v) {

                // finish();

                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public void fillgrid() {
        // TODO Auto-generated method stub

        if (Questions != null && Questions.size() > 0) {
            if (ss.equalsIgnoreCase("2")) {
                value = 1000;
                gridanc.setAdapter(new FP_MemberVisitAdapter(this, Questions,
                        value));

            } else {
                if (CurrentPage < Questions.size()) {
                    if (Questions.get(CurrentPage).getFtext() != null
                            && Questions.get(CurrentPage).getFtext().length() > 0) {
                        gridanc.setAdapter(new FP_MemberVisitAdapter(this,
                                Questions, CurrentPage));
                    } else {
                        CurrentPage = CurrentPage + 1;
                        fillgrid();
                    }
                } else {
                    Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
                    if (clip != null && clip.isPlaying()) {
                        clip.stop();
                    }
                    finish();
                    startActivity(i);
                }
            }
        }
    }

    public void PlayAudio(String clipid) {

        try {
            if (clip != null && clip.isPlaying()) {
                clip.stop();
            }
            String filePath = Environment.getExternalStorageDirectory()
                    + IMAGE_DIRECTORY_NAME1 + clipid;
            Filepath = filePath;
            clip = new MediaPlayer();
            clip.setDataSource(filePath);
            clip.prepare();
            clip.start();

        } catch (Exception e) {
            System.out.println(e);
        }
        clip.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                try {
                    clip.stop();
                } catch (Exception e) {
                    System.out.println(e + "error in clip");
                }

            }
        });

    }

    protected void onPause() {
        super.onPause();
        if (clip != null && clip.isPlaying()) {
            clip.stop();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        if (clip != null && clip.isPlaying()) {
            clip.stop();
        }
    }

}