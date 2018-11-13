package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.microware.intrahealth.AncActivity;
import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Login;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class Userlistadapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data;
    Global global;
    DataProvider dataProvider;


    public Userlistadapter(Context context,
                           ArrayList<HashMap<String, String>> data) {
        this.data = data;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
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

    @SuppressWarnings({"static-access", "unused"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater
                    .inflate(R.layout.userlistadapter, null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);

        TextView Name = (TextView) gridview.findViewById(R.id.Name);
        ImageView btnDelete = (ImageView) gridview.findViewById(R.id.btnDelete);
        Name.setText(data.get(position).get("ASHAName") + "(" + data.get(position).get("UserName") + ")");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stemp = data.get(position).get("is_temp");
                int is_temp = 0;
                if (stemp != null && stemp.length() > 0) {
                    is_temp = Integer.valueOf(stemp);
                }
                if (global.getiGlobalRoleID() == 2) {
                    if (is_temp != 0) {
                        CustomAlert(Integer.valueOf(data.get(position).get("ASHAID")), Integer.valueOf(data.get(position).get("UserID")), is_temp);
                    } else {
                        Toast.makeText(context, R.string.notatemporaryuser, Toast.LENGTH_LONG).show();
                    }
                } else  if (global.getiGlobalRoleID() == 3){
                    CustomAlert(Integer.valueOf(data.get(position).get("ASHAID")), Integer.valueOf(data.get(position).get("UserID")), is_temp);
                }else  if (global.getiGlobalRoleID() == 11){
                    CustomAlert(Integer.valueOf(data.get(position).get("ASHAID")), Integer.valueOf(data.get(position).get("UserID")), is_temp);
                }
            }
        });
        return gridview;
    }

    public void CustomAlert(final int Ashaid, final int UserID, final int is_temp) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.logout_alert, null, false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        if (global.getiGlobalRoleID() == 2) {
            txtTitle.setText(R.string.DoyouwanttodeleteUser);
        } else {
            txtTitle.setText(context.getResources().getString(R.string.DoyouwanttodeleteUser) + " " + context.getResources().getString(R.string.loginotheruser));
        }

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                // int iLoggedin = 0;
                backup(UserID);
                if (global.getiGlobalRoleID() == 2) {
                    if (countashawise(Ashaid) == 0 || is_temp == 2) {

                        deleteashawise(Ashaid, UserID);
                        Intent i = new Intent(context, Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    } else {
                        Toast.makeText(context, R.string.firstupoloadthendelete, Toast.LENGTH_LONG).show();
                    }
                } else if (global.getiGlobalRoleID() == 3) {
                    if (count() == 0 || is_temp == 2) {
                        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE))
                                    .clearApplicationUserData(); // note: it has a return value!
                            Intent i = new Intent(context, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        } else {
                            // use old hacky way, which can be removed
                            // once minSdkVersion goes above 19 in a few years.
                        }
                    } else {
                        Toast.makeText(context, R.string.firstupoloadthendelete, Toast.LENGTH_LONG).show();
                    }
                } else if (global.getiGlobalRoleID() == 11) {
                    if (count() == 0 || is_temp == 2) {
                        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                            ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE))
                                    .clearApplicationUserData(); // note: it has a return value!
                            Intent i = new Intent(context, Login.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        } else {
                            // use old hacky way, which can be removed
                            // once minSdkVersion goes above 19 in a few years.
                        }
                    } else {
                        Toast.makeText(context, R.string.firstupoloadthendelete, Toast.LENGTH_LONG).show();
                    }
                }
                dialog.dismiss();
            }
        });

        Button btnno = (Button) dialog.findViewById(R.id.btn_no);
        btnno.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public int count() {
        int total = 0;
        try {
            String sql1 = "", sql2 = "", sql11 = "", sql12 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "", sql7 = "", sql8 = "", sql9 = "", sql10 = "", sql13 = "";
            sql1 = "Select count(*) from Tbl_HHSurvey where IsEdited=1 ";
            sql2 = "Select count(*)  from Tbl_HHFamilyMember where IsEdited=1 ";
            sql3 = "Select count(*) from tblChild where IsEdited = 1";
            sql4 = "Select count(*) from tblPregnant_woman where IsEdited = 1";
            sql5 = "Select count(*) from tblANCVisit where IsEdited = 1";
            sql6 = "Select count(*)  from tblFP_followup where IsEdited=1 ";
            sql7 = "Select count(*) from tblFP_visit where IsEdited = 1";
            sql8 = "Select count(*) from tblPNChomevisit_ANS where IsEdited = 1";
            sql9 = "Select count(*) from tblmstimmunizationANS where IsEdited = 1";
            sql10 = "Select count(*) from tblVHNDDuelist where IsUploaded = 0";
            sql11 = "Select count(*) from tblncdscreening where IsEdited = 1";
            sql12 = "Select count(*) from tblncdfollowup where IsEdited = 1";
            sql13 = "Select count(*) from tblncdcbac where IsEdited = 1";
            int fpcount = dataProvider.getMaxRecord(sql6)
                    + dataProvider.getMaxRecord(sql7);
            int hhcount = dataProvider.getMaxRecord(sql1)
                    + dataProvider.getMaxRecord(sql2);
            int mnchcount = dataProvider.getMaxRecord(sql9)
                    + dataProvider.getMaxRecord(sql3)
                    + dataProvider.getMaxRecord(sql4)
                    + dataProvider.getMaxRecord(sql5)
                    + dataProvider.getMaxRecord(sql8);
            int vhndcount = dataProvider.getMaxRecord(sql10);
            int ncdcount = dataProvider.getMaxRecord(sql11)
                    + dataProvider.getMaxRecord(sql12) + dataProvider.getMaxRecord(sql13);
            total = fpcount + hhcount + mnchcount + vhndcount + ncdcount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int countashawise(int ashaid) {
        int total = 0;
        try {
            String sql1 = "", sql2 = "", sql11 = "", sql12 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "", sql7 = "", sql8 = "", sql9 = "", sql10 = "", sql13 = "";
            sql1 = "Select count(*) from Tbl_HHSurvey where ServiceProviderID=" + ashaid + " and IsEdited=1 ";
            sql2 = "Select count(*)  from Tbl_HHFamilyMember where AshaID=" + ashaid + " and IsEdited=1 ";
            sql3 = "Select count(*) from tblChild where AshaID=" + ashaid + " and IsEdited = 1";
            sql4 = "Select count(*) from tblPregnant_woman where AshaID=" + ashaid + " and  IsEdited = 1";
            sql5 = "Select count(*) from tblANCVisit where ByAshaID=" + ashaid + " and IsEdited = 1";
            sql6 = "Select count(*)  from tblFP_followup where AshaID=" + ashaid + " and IsEdited=1 ";
            sql7 = "Select count(*) from tblFP_visit where AshaID=" + ashaid + " and IsEdited = 1";
            sql8 = "Select count(*) from tblPNChomevisit_ANS where AshaID=" + ashaid + " and IsEdited = 1";
            sql9 = "Select count(*) from tblmstimmunizationANS where AshaID=" + ashaid + " and IsEdited = 1";
            sql10 = "Select count(*) from tblVHNDDuelist where AshaID=" + ashaid + " and IsUploaded = 0";
            sql11 = "Select count(*) from tblncdscreening where AshaID=" + ashaid + " and IsEdited = 1";
            sql12 = "Select count(*) from tblncdfollowup where AshaID=" + ashaid + " and IsEdited = 1";
            sql13 = "Select count(*) from tblncdcbac where AshaID=" + ashaid + " and IsEdited = 1";
            int fpcount = dataProvider.getMaxRecord(sql6)
                    + dataProvider.getMaxRecord(sql7);
            int hhcount = dataProvider.getMaxRecord(sql1)
                    + dataProvider.getMaxRecord(sql2);
            int mnchcount = dataProvider.getMaxRecord(sql9)
                    + dataProvider.getMaxRecord(sql3)
                    + dataProvider.getMaxRecord(sql4)
                    + dataProvider.getMaxRecord(sql5)
                    + dataProvider.getMaxRecord(sql8);
            int vhndcount = dataProvider.getMaxRecord(sql10);
            int ncdcount = dataProvider.getMaxRecord(sql11)
                    + dataProvider.getMaxRecord(sql12) + dataProvider.getMaxRecord(sql13);
            total = fpcount + hhcount + mnchcount + vhndcount + ncdcount;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public int deleteashawise(int ashaid, int UserID) {
        int total = 0;
        try {
            String sql15 = "", sql16 = "", sql17 = "", sql1 = "", sql14 = "", sql2 = "", sql11 = "", sql12 = "", sql3 = "", sql4 = "", sql5 = "", sql6 = "", sql7 = "", sql8 = "", sql9 = "", sql10 = "", sql13 = "";
            sql1 = "Delete from Tbl_HHSurvey where ServiceProviderID=" + ashaid + "";
            sql2 = "Delete  from Tbl_HHFamilyMember where AshaID=" + ashaid + "";
            sql3 = "Delete from tblChild where AshaID=" + ashaid + " ";
            sql4 = "Delete from tblPregnant_woman where AshaID=" + ashaid + " ";
            sql5 = "Delete from tblANCVisit where ByAshaID=" + ashaid + " ";
            sql6 = "Delete  from tblFP_followup where AshaID=" + ashaid + "";
            sql7 = "Delete from tblFP_visit where AshaID=" + ashaid + " ";
            sql8 = "Delete from tblPNChomevisit_ANS where AshaID=" + ashaid + " ";
            sql9 = "Delete from tblmstimmunizationANS where AshaID=" + ashaid + " ";
            sql10 = "Delete from tblVHNDDuelist where AshaID=" + ashaid + " ";
            sql11 = "Delete from tblncdscreening where AshaID=" + ashaid + " ";
            sql12 = "Delete from tblncdfollowup where AshaID=" + ashaid + " ";
            sql13 = "Delete from tblncdcbac where AshaID=" + ashaid + " ";
            sql14 = "Delete from VHND_Schedule where ASHA_ID=" + ashaid + " ";
            sql16 = "Delete from tbl_VHNDPerformance where AshaID=" + ashaid + " ";
            sql17 = "Delete from MstASHA where ASHAID=" + ashaid + " ";
            sql15 = "Delete from MstUser where UserID=" + UserID + " ";
            dataProvider.executeSql(sql6);
            dataProvider.executeSql(sql7);
            dataProvider.executeSql(sql1);
            dataProvider.executeSql(sql2);
            dataProvider.executeSql(sql9);
            dataProvider.executeSql(sql3);
            dataProvider.executeSql(sql4);
            dataProvider.executeSql(sql5);
            dataProvider.executeSql(sql8);
            dataProvider.executeSql(sql10);
            dataProvider.executeSql(sql11);
            dataProvider.executeSql(sql12);
            dataProvider.executeSql(sql13);
            dataProvider.executeSql(sql14);
            dataProvider.executeSql(sql15);
            dataProvider.executeSql(sql16);
            dataProvider.executeSql(sql17);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public void backup(int user) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
            String ss = sdf.format(new Date());
            ss = "msu" + user + "IntraHealthdb" + ss;
            String raw = null;
            raw = String.valueOf(Environment.getExternalStorageDirectory());
            File sdcard = new File(raw + "/msakhiArchie/");
            File outputFile = new File(sdcard, ss);

            sdcard.mkdirs();

            if (!outputFile.exists()) {
                // sdcard.createNewFile();
                outputFile.createNewFile();

            }

            File data = Environment.getDataDirectory();
            File inputFile = new File(data, "data/" + context.getPackageName()
                    + "/databases/" + "IntraHealth");
            InputStream input = new FileInputStream(inputFile);
            OutputStream output = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            // throw new Error("Copying Failed");
        }
    }


}
