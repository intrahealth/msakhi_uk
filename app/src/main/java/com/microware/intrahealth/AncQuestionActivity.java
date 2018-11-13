package com.microware.intrahealth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.microware.intrahealth.adapter.AncQuestionAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblmstANCQues;

@SuppressLint("InflateParams")
public class AncQuestionActivity extends Activity {
    // ExtendedViewPager viewPager;
    PagerAdapter adapter;
    int[] flag;
    String[] rank;
    static TextView pagenumber, tvans, title, tvtime;
    ImageView imgleft, imgright;
    ViewPager mViewPager;
    DataProvider dataProvider;
    Global global;
    GridView gridanc;
    TimePicker time;
    String Filepath = "";
    Dialog timepic;
    Button next;
    private static final String IMAGE_DIRECTORY_NAME1 = "/mSakhi/Media/";
    int noofcount = 1;
    int CurrentPage = 0;
    ArrayList<tblmstANCQues> Questions = new ArrayList<tblmstANCQues>();
    ArrayList<tblmstANCQues> Grp1 = new ArrayList<tblmstANCQues>();
    ArrayList<tblmstANCQues> Grp2 = new ArrayList<tblmstANCQues>();
    ArrayList<tblmstANCQues> Grp3 = new ArrayList<tblmstANCQues>();
    ArrayList<tblmstANCQues> Grp4 = new ArrayList<tblmstANCQues>();
    // ArrayList<tblmstimmunizationQues> Grp5 = new
    // ArrayList<tblmstimmunizationQues>();
    Date c_date = new Date();
    Button imgNext, imgEnd, Pause, Play, rePlay;
    MediaPlayer clip = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.anc_questionactivity);
        // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        // R.layout.titlebar);
        // setTaskBarColored(this);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        pagenumber = (TextView) findViewById(R.id.pagenumber);
        title = (TextView) findViewById(R.id.title);

        mViewPager = (ViewPager) findViewById(R.id.treatmentviewpager);

        // imgleft = (ImageView) findViewById(R.id.imgleft);
        // imgright = (ImageView) findViewById(R.id.imgright);
        imgNext = (Button) findViewById(R.id.imgNext);
        imgEnd = (Button) findViewById(R.id.imgEnd);
        Pause = (Button) findViewById(R.id.Pause);
        Play = (Button) findViewById(R.id.Play);
        rePlay = (Button) findViewById(R.id.rePlay);
        Questions = dataProvider.getAllQuestionsanc(0);
        Grp1 = dataProvider.getAllQuestionsanc(1);
        Grp2 = dataProvider.getAllQuestionsanc(2);
        Grp3 = dataProvider.getAllQuestionsanc(3);
        Grp4 = dataProvider.getAllQuestionsanc(4);

        if (global.getVisitno() == 1) {
            Grp1 = dataProvider.getAllQuestionsanc(1);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < Grp1.size(); j++) {
                    if (Grp1.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 2) {
            Grp2 = dataProvider.getAllQuestionsanc(2);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < Grp2.size(); j++) {
                    if (Grp2.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // Stri`ng element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 3) {
            Grp3 = dataProvider.getAllQuestionsanc(3);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < Grp3.size(); j++) {
                    if (Grp3.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 4) {
            Grp4 = dataProvider.getAllQuestionsanc(4);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < Grp4.size(); j++) {
                    if (Grp4.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        }
        gridanc = (GridView) findViewById(R.id.gridanc);
        next = (Button) findViewById(R.id.next);

        // TextView tvQuestions = (TextView) gridanc
        // .findViewById(R.id.tvQuestions);
        //
        // TextView tvQuestions2 = (TextView) gridanc
        // .findViewById(R.id.tvQuestions2);
        //
        // EditText etans = (EditText) gridanc.findViewById(R.id.etans);
        // TextView ansy = (TextView) gridanc.findViewById(R.id.ansy);
        // TextView ansn = (TextView) gridanc.findViewById(R.id.ansn);
        tvtime = (TextView) gridanc.findViewById(R.id.tvtime);
        // TextView ansetditfloat = (TextView) itemView

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                    if (clip != null && clip.isPlaying()) {
                        clip.stop();
                    }
                    Filepath = "";
                    // clip = null;
                    final TextView ansfield = (TextView) gridanc
                            .findViewById(R.id.ansfield);
                    final TextView ansQ = (TextView) gridanc
                            .findViewById(R.id.ans);
                    // TextView tvQuestions = (TextView) gridanc
                    // .findViewById(R.id.tvQuestions);
                    TextView visitdate = (TextView) gridanc
                            .findViewById(R.id.visitdate);
                    // System.out.print(ansQ.getText().toString());
                    // String ans=ansQ.getText().toString();

                    if (CurrentPage < Questions.size()) {

                        int pageno = 0;
                        if (ansfield.getText() != null
                                && ansfield.getText().toString().length() > 0) {
                            if (ansQ.getText() != null
                                    && ansQ.getText().toString().length() > 0) {
                                savedata(ansfield.getText().toString(), ansQ
                                        .getText().toString(), visitdate
                                        .getText().toString());
                                if (ansQ.getText().toString()
                                        .equalsIgnoreCase("1")
                                        && Questions.get(CurrentPage)
                                        .getY_qid() > 0) {
                                    pageno = Integer.valueOf(Questions.get(
                                            CurrentPage).getY_qid());

                                    CurrentPage = pageno - 1;
                                } else if (ansQ.getText().toString()
                                        .equalsIgnoreCase("2")
                                        && Questions.get(CurrentPage)
                                        .getN_qid() > 0) {
                                    pageno = Integer.valueOf(Questions.get(
                                            CurrentPage).getN_qid());
                                    CurrentPage = pageno - 1;

                                } else if (ansQ.getText().toString()
                                        .equalsIgnoreCase("3")
                                        && Questions.get(CurrentPage)
                                        .getN_qid() > 0) {
                                    pageno = Integer.valueOf(Questions.get(
                                            CurrentPage).getN_qid());
                                    CurrentPage = pageno - 1;
                                } else {
                                    CurrentPage = CurrentPage + 1;

                                }
                                fillgrid();
                            } else {
                                CustomAlertSave("कृपया जवाब दाखिल करें");
                            }

                        } else {
                            CurrentPage = CurrentPage + 1;
                            fillgrid();
                        }
                    } else {
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        fillgrid();
    }

    // public void removeQues(ArrayList<tblmstimmunizationQues>Grp){
    // for(int i=0;i<Questions.size();i++){
    // for(int j=0;j<Grp.size();j++){
    // if(Grp.get(j).getFtext().equalsIgnoreCase(Questions.get(i).getFtext())){
    // Questions.get(i).setFtext("");
    // }
    // }
    // }
    // }
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
        txtTitle.setText(String.valueOf(msg));

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
    private void savedata(String ansfield, String ansQ, String VisitDate) {
        // TODO Auto-generated method stub

        String dtaans = ansQ;
        String sql = "";
        String flag = "";
        String ANCGUID = global.getsGlobalANCVisitGUID();

        // int visitno = global.getVisitno();

        if ((global.getsGlobalPWGUID() != null && global.getsGlobalPWGUID()
                .length() > 0)
                && (global.getsGlobalANCVisitGUID() != null && global
                .getsGlobalANCVisitGUID().length() > 0)) {

            dataProvider.saveancvisitdata(dtaans, ANCGUID, VisitDate,
                    global.getsGlobalPWGUID(), ansfield, flag);
        }

    }

    public void PlayAudio1(final String[] ar, final int returnvalue) {
        if (returnvalue == 0) {
            try {
                if (clip != null && clip.isPlaying()) {
                    clip.stop();
                }

                String filePath = Environment.getExternalStorageDirectory()
                        + IMAGE_DIRECTORY_NAME1 + ar[0] + ".3gp";
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

                        if (returnvalue == 1) {
                            AssetFileDescriptor afd;
                            try {
                                String filePath = Environment
                                        .getExternalStorageDirectory()
                                        + IMAGE_DIRECTORY_NAME1
                                        + ar[1]
                                        + ".3gp";

                                clip = new MediaPlayer();
                                clip.setDataSource(filePath);

                                clip.prepare();

                                clip.start();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        System.out.println(e + "error in cip");
                    }
                }
            });
        }
    }

    public void PlayAudio(String clipid) {

        try {
            if (clip != null && clip.isPlaying()) {
                clip.stop();
            }
            String filePath = Environment.getExternalStorageDirectory()
                    + IMAGE_DIRECTORY_NAME1 + clipid + ".3gp";
            // AssetFileDescriptor afd = getAssets().openFd(clipid + ".3gp");
            Filepath = filePath;
            clip = new MediaPlayer();
            clip.setDataSource(filePath);

            // afd.close();
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

    public void fillgrid() {
        // TODO Auto-generated method stub

        if (Questions != null && Questions.size() > 0) {
            if (CurrentPage < Questions.size()) {
                if (Questions.get(CurrentPage).getQtext() != null
                        && Questions.get(CurrentPage).getQtext().length() > 0) {
                    gridanc.setAdapter(new AncQuestionAdapter(this, Questions,
                            CurrentPage, noofcount));
                } else {
                    CurrentPage = CurrentPage + 1;
                    fillgrid();
                }

            } else {
                finish();
            }
        }

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (clip != null && clip.isPlaying()) {
            clip.stop();
        }
        // CustomAlertExit(getResources().getString(R.string.backpress));
    }

    public void CustomAlertExit(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.backpress, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        // pwindo = new PopupWindow(view, 700, 1000, true);
        // pwindo.showAtLocation(view, Gravity.CENTER, 0, 0);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        txtTitle.setText(msg);

        Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialog.findViewById(R.id.btn_No);
        btn_yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // String
                // sql="Delete from  tblmstimmunizationANS where ImmunizationGUID='"+global.getImmunizationGUID()+"'";
                // dataProvider.executeSql(sql);
                dialog.dismiss();
                finish();
            }
        });
        btn_no.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                // finish();
            }
        });
        // Display the dialog
        dialog.show();

    }

}