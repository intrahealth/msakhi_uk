package com.microware.intrahealth;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.adapter.homeVisitPncAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.q_bank;

@SuppressLint("InflateParams")
public class HomeVisitPnc extends Activity {
    // ExtendedViewPager viewPager;
    PagerAdapter adapter;
    int[] flag;
    String[] rank;
    static TextView pagenumber, tvans, title;
    ImageView imgleft, imgright;
    ViewPager mViewPager;
    DataProvider dataProvider;
    Global global;
    int PreviousPage = 0;
    GridView gridanc;
    TimePicker time;
    String Question = "";
    Dialog timepic;
    MediaPlayer clip = null;
    String Filepath = "";
    Button next;
    int CurrentPage = 0;
    ArrayList<q_bank> Questions = new ArrayList<q_bank>();
    ArrayList<q_bank> child = new ArrayList<q_bank>();
    ArrayList<q_bank> mother = new ArrayList<q_bank>();
    ArrayList<q_bank> visit1 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit2 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit3 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit4 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit5 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit6 = new ArrayList<q_bank>();
    ArrayList<q_bank> visit7 = new ArrayList<q_bank>();
    ArrayList<tblPNChomevisit_ANS> pnc = new ArrayList<tblPNChomevisit_ANS>();
    private static final String IMAGE_DIRECTORY_NAME1 = "/mSakhi/Media/";
    Button imgNext, imgEnd, previous, Pause, Play, rePlay;
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.pnc_activity);
        // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
        // R.layout.titlebar);
        // setTaskBarColored(this);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        pagenumber = (TextView) findViewById(R.id.pagenumber);
        title = (TextView) findViewById(R.id.title);

        mViewPager = (ViewPager) findViewById(R.id.treatmentviewpager);
        connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoCheckConnection = connMgrCheckConnection
                .getActiveNetworkInfo();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                Tracker t = ((Global) getApplication())
                        .getTracker(TrackerName.APP_TRACKER);
                t.setScreenName("Home Visit PNC");
                t.send(new HitBuilders.AppViewBuilder().build());
            } catch (Exception e) {

            }
        }
        // imgleft = (ImageView) findViewById(R.id.imgleft);
        // imgright = (ImageView) findViewById(R.id.imgright);
        imgNext = (Button) findViewById(R.id.imgNext);
        imgEnd = (Button) findViewById(R.id.imgEnd);
        Pause = (Button) findViewById(R.id.Pause);
        Play = (Button) findViewById(R.id.Play);
        rePlay = (Button) findViewById(R.id.rePlay);
        Questions = dataProvider.getAllQuestions(0);
        child = dataProvider.getAllQuestions(8);
        mother = dataProvider.getAllQuestions(9);
        if (global.getVisitno() == 1) {
            visit1 = dataProvider.getAllQuestions(1);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit1.size(); j++) {
                    if (visit1.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 2) {
            visit2 = dataProvider.getAllQuestions(2);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit2.size(); j++) {
                    if (visit2.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 3) {
            visit2 = dataProvider.getAllQuestions(3);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit3.size(); j++) {
                    if (visit3.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 4) {
            visit4 = dataProvider.getAllQuestions(4);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit4.size(); j++) {
                    if (visit4.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 5) {
            visit5 = dataProvider.getAllQuestions(5);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit5.size(); j++) {
                    if (visit5.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 6) {
            visit6 = dataProvider.getAllQuestions(6);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit6.size(); j++) {
                    if (visit6.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        } else if (global.getVisitno() == 7) {
            visit7 = dataProvider.getAllQuestions(7);
            for (int i = 0; i < Questions.size(); i++) {
                for (int j = 0; j < visit7.size(); j++) {
                    if (visit7.get(j).getQtext()
                            .equalsIgnoreCase((Questions).get(i).getQtext())) {

                        // String element="";
                        Questions.get(i).setQtext("");
                        // Questions.remove(i+1);
                    }
                }
            }
        }
        System.out.println("Array list" + mother);
        gridanc = (GridView) findViewById(R.id.gridanc);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        // TextView tvQuestions = (TextView) gridanc
        // .findViewById(R.id.tvQuestions);
        //
        // TextView tvQuestions2 = (TextView) gridanc
        // .findViewById(R.id.tvQuestions2);

        // EditText etans = (EditText) gridanc.findViewById(R.id.etans);
        // TextView ansy = (TextView) gridanc.findViewById(R.id.ansy);
        // TextView ansn = (TextView) gridanc.findViewById(R.id.ansn);
        // TextView ansetditfloat = (TextView) itemView
        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CurrentPage = PreviousPage;
                fillgrid();
                Filepath = "";
            }
        });
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final TextView ansfield = (TextView) gridanc
                        .findViewById(R.id.ansfield);
                final TextView ansQ = (TextView) gridanc.findViewById(R.id.ans);
                TextView tvQuestions = (TextView) gridanc
                        .findViewById(R.id.tvQuestions);
                PreviousPage = CurrentPage;
                if (CurrentPage > 0) {
                    previous.setVisibility(View.VISIBLE);
                } else {
                    previous.setVisibility(View.GONE);
                }
                if (CurrentPage < Questions.size()) {
                    Filepath = "";
                    int pageno = 0;
                    if (ansfield.getText() != null
                            && ansfield.getText().toString().length() > 0) {
                        if (ansQ.getText() != null
                                && ansQ.getText().toString().length() > 0) {
                            savedata(ansfield.getText().toString(), ansQ
                                    .getText().toString());
                            if (ansQ.getText().toString().equalsIgnoreCase("1")
                                    && Questions.get(CurrentPage).getY_qid() > 0) {
                                pageno = Integer.valueOf(Questions.get(
                                        CurrentPage).getY_qid());

                                CurrentPage = pageno;
                            } else if (ansQ.getText().toString()
                                    .equalsIgnoreCase("2")
                                    && Questions.get(CurrentPage).getN_qid() > 0) {
                                pageno = Integer.valueOf(Questions.get(
                                        CurrentPage).getN_qid());

                                if (ansQ.getText().toString()
                                        .equalsIgnoreCase("2")
                                        && tvQuestions.getText() != null
                                        && tvQuestions
                                        .getText()
                                        .toString()
                                        .equalsIgnoreCase(
                                                "क्या शिशु जीवित है?")) {
                                    for (int i = 0; i < Questions.size(); i++) {
                                        for (int j = 0; j < child.size(); j++) {
                                            if (child
                                                    .get(j)
                                                    .getQtext()
                                                    .equalsIgnoreCase(
                                                            (Questions).get(i)
                                                                    .getQtext())) {

                                                // String element="";
                                                Questions.get(i).setQtext("");
                                                // Questions.remove(i+1);
                                            }
                                        }
                                    }
                                    CurrentPage = pageno;
                                } else if (ansQ.getText().toString()
                                        .equalsIgnoreCase("2")
                                        && tvQuestions.getText() != null
                                        && tvQuestions
                                        .getText()
                                        .toString()
                                        .equalsIgnoreCase(
                                                "क्या माँ जीवित है?")) {
                                    for (int i = 0; i < Questions.size(); i++) {
                                        for (int j = 0; j < mother.size(); j++) {
                                            if (mother
                                                    .get(j)
                                                    .getQtext()
                                                    .equalsIgnoreCase(
                                                            (Questions).get(i)
                                                                    .getQtext())) {
                                                // Questions.remove(i);
                                                // String element="";
                                                Questions.get(i).setQtext("");
                                            }
                                        }
                                    }
                                    System.out.printf(
                                            String.valueOf(Questions), "Array");

                                    CurrentPage = pageno;
                                } else {
                                    CurrentPage = pageno;
                                }
                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "नवजात शिशु को जन्म के बाद पहली बार माँ का दूध कब पिलाया गया?")
                                    && (ansQ.getText().toString()
                                    .equalsIgnoreCase("2")
                                    || ansQ.getText().toString()
                                    .equalsIgnoreCase("3") || ansQ
                                    .getText().toString()
                                    .equalsIgnoreCase("4"))) {
                                CurrentPage = 63;

                            } else if (ansQ.getText().toString()
                                    .equalsIgnoreCase("1")
                                    && tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "क्या माँ निप्पल में दरारें,स्तन में दर्द या कढ़े स्तन की शिकायत करती है?")) {
                                CurrentPage = 83;
                            } else if (ansQ.getText().toString()
                                    .equalsIgnoreCase("2")
                                    && tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "क्या माँ निप्पल में दरारें,स्तन में दर्द या कढ़े स्तन की शिकायत करती है?")) {
                                CurrentPage = 85;
                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "शिशु का तापमान नापे एवं दर्ज करें")) {

                                float newans = Float.valueOf(ansQ.getText()
                                        .toString());
                                if (newans < 91) {
                                    CustomAlertSave(getString(R.string.Valuegreaterthan91f));
                                } else if (newans > 106) {
                                    CustomAlertSave(getString(R.string.Valuelessthan106F));
                                } else if (newans < 97) {
                                    CurrentPage = 27;
                                } else if (newans >= 97.1 && newans <= 98.9) {
                                    CurrentPage = 29;
                                } else if (newans > 99) {
                                    CurrentPage = 28;
                                } else {
                                    CurrentPage = CurrentPage + 1;
                                }
                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "माँ का तापमान नापे एवं दर्ज करें")) {

                                float newans = Float.valueOf(ansQ.getText()
                                        .toString());
                                if (newans < 91) {
                                    CustomAlertSave(getString(R.string.Valuegreaterthan91f));
                                } else if (newans > 106) {
                                    CustomAlertSave(getString(R.string.Valuelessthan106F));
                                } else if (newans > 99 && newans <= 102) {
                                    CurrentPage = 60;
                                } else if (newans > 102) {
                                    CurrentPage = 61;
                                } else {
                                    CurrentPage = CurrentPage + 3;
                                }

                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "वजन (किलग्राम/ग्राम)")) {
                                float newans = Float.valueOf(ansQ.getText()
                                        .toString());

                                if (newans > 8) {
                                    CustomAlertSave(getString(R.string.Valuelessthan8kg));
                                } else if (newans < 1.8) {
                                    CurrentPage = 90;
                                } else if (newans >= 1.8 && newans <= 2.4) {
                                    CurrentPage = 34;
                                } else {
                                    CurrentPage = 36;
                                }

                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "24 घंटे में कितनी बार आप पूरा भोजन खाती हैं?")) {
                                if (ansQ.getText()
                                        .toString().length() == 1) {
                                    int newans = Integer.valueOf(ansQ.getText()
                                            .toString());
                                    if (newans < 4) {
                                        CurrentPage = 56;
                                    } else {
                                        CurrentPage = CurrentPage + 2;
                                    }
                                } else {
                                    CustomAlertSave(getString(R.string.valuelesstan10));
                                }
                            } else if (tvQuestions.getText() != null
                                    && tvQuestions
                                    .getText()
                                    .toString()
                                    .equalsIgnoreCase(
                                            "एक दिन में कितने पेड बदलती हैं?")) {
                                if (ansQ.getText()
                                        .toString().length() == 1) {
                                    int newans = Integer.valueOf(ansQ.getText()
                                            .toString());
                                    if (newans > 5) {
                                        CurrentPage = 95;
                                    } else {
                                        CurrentPage = CurrentPage + 2;
                                    }
                                } else {
                                    CustomAlertSave(getString(R.string.valuelesstan10));
                                }

                            } else {
                                CurrentPage = CurrentPage + 1;
                            }

                            fillgrid();
                        } else {
                            CustomAlertSave("कृपया जवाब दाखिल करें");
                        }
                    } else {
                        if (CurrentPage == 27 || CurrentPage == 34) {
                            CurrentPage = CurrentPage + 2;
                        } else {
                            CurrentPage = CurrentPage + 1;
                        }
                        fillgrid();
                    }
                } else {
                    if (clip != null && clip.isPlaying()) {
                        clip.stop();
                    }
                    finish();
                }
            }
        });

        // imgEnd.setOnClickListener(new View.OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // // TODO Auto-generated method stub
        //
        //
        // Intent i = new Intent(HomeVisitPnc.this, PncWomenList.class);
        // // finish();
        // startActivity(i);
        // }
        // });
        //
        fillgrid();
    }

    public void onclickButton(View view) {


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
                    if (!Filepath.equalsIgnoreCase("") && Filepath.length() > 0) {
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

    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (networkInfoCheckConnection != null
                && networkInfoCheckConnection.isConnected()
                && networkInfoCheckConnection.isAvailable()) {
            try {
                GoogleAnalytics.getInstance(HomeVisitPnc.this)
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
                GoogleAnalytics.getInstance(HomeVisitPnc.this)
                        .reportActivityStop(this);

            } catch (Exception e) {

            }
        }

    }

    @SuppressWarnings("unused")
    private void savedata(String ansfield, String ansQ) {
        // TODO Auto-generated method stub
        String dtaans = "";
        String sql = "";
        String flag = "";
        String ChildGUID = global.getsGlobalChildGUID();

        int visitno = global.getVisitno();
        int AshaID = 0;
        int ANMID = 0;
        if (global.getsGlobalANMCODE() != null
                && global.getsGlobalANMCODE().length() > 0) {
            ANMID = Integer.valueOf(global.getsGlobalANMCODE());
        }
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            AshaID = Integer.valueOf(global.getsGlobalAshaCode());
        }
        String count = "Select count(*) from  tblPNChomevisit_ANS where ChildGUID='"
                + global.getsGlobalChildGUID()
                + "' and VisitNo="
                + global.getVisitno() + "";
        int number = Integer.valueOf(dataProvider.getMaxRecord(count));
        if (ansQ != null && ansQ.length() > 0) {
            dtaans = ansQ;
        }
        if (number == 0) {
            String pncguid = Validate.random();
            global.setsPncGUID(pncguid);
            flag = "i";
            dataProvider.savepncdata(AshaID, ANMID, dtaans, ChildGUID, pncguid,
                    visitno, ansfield, flag, global.getsGlobalUserID());

        } else {
            if ((global.getsPncGUID() != null && global.getsPncGUID().length() > 0)
                    && (global.getsGlobalChildGUID() != null && global
                    .getsGlobalChildGUID().length() > 0)) {
                flag = "u";
                dataProvider.savepncdata(AshaID, ANMID, dtaans, ChildGUID,
                        global.getsPncGUID(), visitno, ansfield, flag,
                        global.getsGlobalUserID());

            } else {
                String pncguid = Validate.random();
                global.setsPncGUID(pncguid);
                flag = "i";
                dataProvider.savepncdata(AshaID, ANMID, dtaans, ChildGUID,
                        pncguid, visitno, ansfield, flag,
                        global.getsGlobalUserID());
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

    public void fillgrid() {
        // TODO Auto-generated method stub

        if (Questions != null && Questions.size() > 0) {
            if (CurrentPage < Questions.size()) {
                if (Questions.get(CurrentPage).getQtext() != null
                        && Questions.get(CurrentPage).getQtext().length() > 0) {
                    gridanc.setAdapter(new homeVisitPncAdapter(this, Questions,
                            CurrentPage));
                } else {
                    CurrentPage = CurrentPage + 1;
                    fillgrid();
                }
            } else {
                if (clip != null && clip.isPlaying()) {
                    clip.stop();
                }
                finish();
            }
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (clip != null && clip.isPlaying()) {
            clip.pause();
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (clip != null && clip.isPlaying()) {
            clip.stop();
        }
    }

}