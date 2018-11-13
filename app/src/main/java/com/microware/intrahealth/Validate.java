package com.microware.intrahealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class Validate {
    String MyPREFERENCES = "msakhi";
    static SharedPreferences sharedpreferences;
    static Context context;
    static DataProvider dataProvider;


    public Validate(Context context) {
        this.context = context;
        dataProvider = new DataProvider(context);
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public static void SaveSharepreferenceString(String key, String Value) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, Value);
        editor.commit();
    }

    public String RetriveSharepreferenceString(String key) {
        return sharedpreferences.getString(key, "");
    }

    public void SaveSharepreferenceInt(String key, int iValue) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, iValue);
        editor.commit();
    }

    public int RetriveSharepreferenceInt(String key) {
        return sharedpreferences.getInt(key, 0);
    }

    public void ClearSharedPrefrence() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static String getcurrentdate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String gettime() {

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        return localTime;
    }

    public static String getcurrentdatetime() {

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String endTime = date1.format(currentLocalTime);
        return endTime;
    }

    public static String getcurrentdatetimeddmmyyyy() {

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String endTime = date1.format(currentLocalTime);
        return endTime;
    }

    public static String returnStringValue(String myString) {
        String iValue = "";
        if (myString != null
                && !myString.equalsIgnoreCase("null") && myString.length() > 0) {
            iValue = myString;
        }
        return iValue;

    }

    public static String random() {
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
                .toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS",
                Locale.US);

        Date date = new Date();
        String SDateString = dateFormat.format(date);
        return sb.toString() + SDateString;
    }

    public static int checkmobileno(String mobileno) {

        if (mobileno != null && !mobileno.equalsIgnoreCase("null")
                && mobileno.length() > 0) {
            String s = mobileno; // get your editext value here
            Pattern pattern = Pattern.compile("[6-9]{1}[0-9]{9}");

            Matcher matcher = pattern.matcher(s);
            // Check if pattern matches
            if (matcher.matches()) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;

    }

    public static int checktext(String text) {

        if (text != null && !text.equalsIgnoreCase("null")
                && text.length() > 0) {
            String s = text; // get your editext value here
            Pattern ps = Pattern.compile("^[a-zA-Z ]+$");

            Matcher matcher = ps.matcher(s);
            // Check if pattern matches
            if (matcher.matches()) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;

    }

    public static int checkifsc(String ifsc) {
        if (ifsc != null && !ifsc.equalsIgnoreCase("null") && ifsc.length() > 0) {
            String s = ifsc; // get your editext value here
            Pattern pattern = Pattern.compile("[A-Z|a-z]{4}[0]{1}[0-9]{6}");

            Matcher matcher = pattern.matcher(s);
            // Check if pattern matches
            if (matcher.matches()) {
                return 1;
            } else {
                return 4;
            }
        }
        return 4;

    }

    public static long Daybetween(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date Date1 = null, Date2 = null;
        long day = 0;
        try {
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
            day = (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    @SuppressWarnings("unused")
    public static int CalculateYM(int Year) {
        int newdiff = 0;
        if (Year > 0) {
            final Calendar c = Calendar.getInstance();
            int mTMYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH) + 1;
            int cDay = c.get(Calendar.DAY_OF_MONTH);
            // int newmonth=
            // int month=mMonth-04;
            int newyear = mTMYear - Year;
            newdiff = newyear;
        }
        return newdiff;

    }

    @SuppressWarnings("unused")
    public static int CountAge(String sDob) {
        int age = 0;

        SimpleDateFormat df;
        if (sDob != null && !sDob.equalsIgnoreCase("null")
                && (sDob.trim()).length() > 0) {
            String[] Cdt = sDob.split("-");
            int Day = Integer.valueOf(Cdt[2]);
            int month = Integer.valueOf(Cdt[1]);
            int intyear = Integer.valueOf(Cdt[0]);
            int idx = sDob.lastIndexOf("-");
            final Calendar c = Calendar.getInstance();
            int mTMYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH) + 1;
            int cDay = c.get(Calendar.DAY_OF_MONTH);
            int totYear = mTMYear - intyear;
            if ((month > mMonth) || (month == mMonth && Day > cDay)) {
                totYear = totYear - 1;
            }
            age = totYear;

        }

        return age;
    }

    public static int returnIntegerValue(String myString) {
        int iValue = 0;
        if (myString != null && !myString.equalsIgnoreCase("null") && myString.length() > 0 && isInt(myString)) {
            iValue = Integer.valueOf(myString);
        }
        return iValue;

    }

    public static boolean isInt(String val) {
        for (int i = 0; i < 10; i++) {
            if (val.contains(i + "")) {
                return true;
            }
        }
        return false;
    }

    public static String changeDateFormat(String Dt) {
        String date = "";
        if (Dt != null && Dt.length() > 0 && Dt.contains("-")) {
            Dt.toString().trim();
            System.out.println(Dt);
            String[] Cdt = Dt.split("-");
            String DD = Cdt[0];
            String MM = Cdt[1];
            String YYYY = Cdt[2];

            try {
                date = YYYY.trim() + "-" + MM.trim() + "-" + DD.trim();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }

            return date;
        } else {
            return date;
        }
    }

    public static String changeDateFormatpadding(String Dt) {
        String date = "";
        if (Dt != null && Dt.length() > 0) {
            try {
                Dt.toString().trim();
                System.out.println(Dt);
                String[] Cdt = Dt.split("-");
                String DD = Cdt[0];
                String MM = Cdt[1];
                String YYYY = Cdt[2];
                if (DD.length() == 1) {
                    DD = "0" + Cdt[0];
                }
                if (MM.length() == 1) {
                    MM = "0" + Cdt[1];
                }
                if (YYYY.length() == 1) {
                    YYYY = "0" + Cdt[2];
                }

                date = DD.trim() + "-" + MM.trim() + "-" + YYYY.trim();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e);
            }

            return date;
        } else {
            return date;
        }
    }

    @SuppressWarnings("unused")
    public static String changeDateTimeFormat(String Dt) {
        if (Dt != null && Dt.length() > 0) {
            String[] Ct = Dt.split("T");
            String Date1 = Ct[0];
            String Time1 = Ct[1];
            String[] Cdt = Date1.split("-");
            String YYYY = Cdt[0];
            String MM = Cdt[1];
            String DD = Cdt[2];
            String date = DD + "-" + MM + "-" + YYYY;
            return date;
        } else {
            return "";
        }
    }

    public static String chkDateTimeFormat(String Dt) {
        if (Dt != null && Dt.length() > 0) {

            String[] Cdt = Dt.split("-");
            String YYYY = Cdt[0];
            if (YYYY.length() < 2) {
                YYYY = 0 + YYYY;
            }
            String MM = Cdt[1];
            if (MM.length() < 2) {
                MM = 0 + MM;
            }
            String DD = Cdt[2];
            if (DD.length() < 2) {
                DD = 0 + DD;
            }
            String date = DD + "-" + MM + "-" + YYYY;
            return date;
        } else {
            return "";
        }
    }

    public static String chkDateblank(String Dt) {
        if (Dt != null && Dt.length() > 0) {

            String[] Cdt = Dt.split("-");
            String YYYY = Cdt[0];
            if (YYYY.length() == 1) {
                YYYY = 0 + YYYY;
                if (YYYY.length() == 0) {
                    YYYY = 01 + "";
                }
            }
            String MM = Cdt[1];
            if (MM.length() == 1) {
                MM = 0 + MM;
                if (MM.length() == 0) {
                    MM = 01 + "";
                }
            }
            String DD = Cdt[2];
            if (DD.length() == 1) {
                DD = 0 + DD;
                if (DD.length() == 0) {
                    DD = 01 + "";
                }
            }
            String date = DD + "-" + MM + "-" + YYYY;
            return date;
        } else {
            return "";
        }
    }

    public static String chkDate(String Dt) {
        if (Dt != null && Dt.length() > 0) {
            String date = "";
            String[] Cdt = Dt.split("-");
            String YYYY = Cdt[2];

            String MM = Cdt[1];

            String DD = Cdt[0];
            if (YYYY.length() == 4)
                date = DD + "-" + MM + "-" + YYYY;
            else
                date = YYYY + "-" + MM + "-" + DD;
            return date;
        } else {
            return "";
        }
    }

    public static String getContractMonth(String DOB, String currentdate) {
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String months = "0";
        int diffInDays = 0, month = 0, day = 0;

        try {
            // add jitendra
            Date startDate = dfDate.parse(DOB);
            Date endDate = dfDate.parse(currentdate);

            diffInDays = (int) Math.round(((endDate.getTime() - startDate
                    .getTime()) / (1000 * 60 * 60 * 24) + .5));
            month = diffInDays / 30;
            day = diffInDays % 30;
            months = month + "/" + day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return months;
    }

    public static String changeDateFormat2(String Dt) {
        if (Dt != null && Dt.length() > 0) {
            String[] Cdt = Dt.split("-");
            String YYYY = Cdt[0];
            String MM = Cdt[1];
            String DD = Cdt[2];
            String date = DD + "-" + MM + "-" + YYYY;
            return date;
        } else {
            return "";
        }
    }

    public static int getDate(String date1) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Calendar c = Calendar.getInstance(Locale.US);
            String current = getDate(c);
            Date today = sdf.parse(current);
            Date d1 = sdf.parse(date1);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(d1);
            cal2.setTime(today);
            if (cal1.after(cal2)) {
                return 3;
            } else if (cal1.before(cal2)) {
                return 1;
            }
            if (cal1.equals(cal2)) {
                return 2;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static int getDate(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Date d2 = sdf.parse(date2);
            Date d1 = sdf.parse(date1);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(d1);
            cal2.setTime(d2);
            if (cal1.after(cal2)) {
                return 3;
            } else if (cal1.before(cal2)) {
                return 1;
            }
            if (cal1.equals(cal2)) {
                return 2;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static String getdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.US);
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

	/*
     * public static int checkmobileno(String mobileno) {
	 *
	 * if (mobileno != null && !mobileno.equalsIgnoreCase("null") &&
	 * mobileno.length() > 0) { String s = mobileno; // get your editext value
	 * here Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");
	 *
	 * Matcher matcher = pattern.matcher(s); // Check if pattern matches if
	 * (matcher.matches()) { return 1; } else { return 0; } } return 0;
	 *
	 * }
	 */

    public static int getDatecompare(String date1, String date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            java.util.Date d2 = sdf.parse(date2);
            java.util.Date d1 = sdf.parse(date1);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(d1);
            cal2.setTime(d2);
            if (cal1.after(cal2)) {
                return 3;
            } else if (cal1.before(cal2)) {
                return 1;
            }
            if (cal1.equals(cal2)) {
                return 2;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }


    public static String getDate(Calendar cal) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String date = sdf.format(cal.getTime());

        return date;
    }

    public static boolean isNetworkconn(Activity activity) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        if ((localConnectivityManager.getActiveNetworkInfo() != null)
                && (localConnectivityManager.getActiveNetworkInfo()
                .isAvailable())
                && (localConnectivityManager.getActiveNetworkInfo()
                .isConnected()))

            return true;
        else
            return false;

    }

    public static void CustomAlertEdit(final Activity activity, final EditText et, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                et.setText("");
                et.requestFocus();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public static void CustomAlertTextview(final Activity activity, final TextView tv, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                tv.performClick();
                tv.requestFocus();
            }
        });

        // Display the dialog
        dialog.show();

    }

    public static void CustomAlertSpinner(final Activity activity, final Spinner spin, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static void CustomAlertButton(final Activity activity, final Button bt, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                bt.performClick();
                bt.requestFocus();

            }
        });

        // Display the dialog
        dialog.show();

    }

    public static void CustomAlertSave(final Activity activity, String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(activity);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_layout, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
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

            }
        });

        // Display the dialog
        dialog.show();

    }

    @SuppressWarnings("deprecation")
    public static void showNewWorkError(Activity activity) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(activity.getResources().getString(R.string.mSakhi));
        alertDialog.setMessage(activity.getResources().getString(
                R.string.NoNetworkAccess));
        alertDialog.setButton(activity.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();
        // will close the app if the device does't have camera

    }

    public static int dateyear(String year) {

        if (year != null && !year.equalsIgnoreCase("null") && year.length() > 0) {
            String s = year;
            Pattern pattern = Pattern.compile("[1-2]{1}[5-9]{1}[0-9]{1}");
            Matcher matcher = pattern.matcher(s);
            // Check if pattern matches
            if (matcher.matches()) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;

    }

    public static boolean isTextValid(String text) {
        boolean iValue = false;
        String[] blackList = {"--", ";--", "<script>", "waitfor delay", "/*",
                "*/", "@@", " alter ", "alter ", " begin ", "begin ",
                " create table ", "create table ", " cursor ", "cursor ",
                " declare ", "declare ", " delete ", "delete ",
                " delete table ", "delete table ", " drop ", "drop ",
                "select ", " drop table ", "drop table ", "<script>", " exec ",
                "exec ", " execute ", "execute ", " fetch ", "fetch ", "=",
                " or ", " insert into ", "insert into ", " kill ", "kill ",
                " sys.", "sys. ", "sysobjects", " table ", "table ",
                " update ", "update ", "&lt;", "&gt;", "&#40;", "&#41;",
                "&#35;", "&amp;", "&quot;", " or ", " && ", "!", "?", ":", ";",
                "||", "==", "'", ","};
        for (int i = 0; i < blackList.length; i++) {
            if (text.toUpperCase().contains(blackList[i].toUpperCase())) {
                iValue = true;
                break;
            }
        }
        return iValue;

    }

    @SuppressWarnings("unused")
    public static long diffYrs(String Date) {
        long diffDays = 0;
        if (Date != null && Date.length() > 0) {

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            // Date date = new Date();
            String datenew = Date;

            String newdate = Validate.changeDateFormat(getcurrentdate());

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = dateFormat.parse(datenew);
                d2 = dateFormat.parse(newdate);

                // in milliseconds
                long diff = d2.getTime() - d1.getTime();
                diffDays = (diff / 1000 / 60 / 60 / 24) / 365;
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
//                long diffHours = diff / (60 * 60 * 1000) % 24;
//                diffDays = diff / (24 * 60 * 60 * 1000*365);
//
            } catch (Exception e) {

            }
        }
        return diffDays;
    }

    @SuppressWarnings("unused")
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
                        : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static boolean isDeviceRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su",
                "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su",
                "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su"};
        for (String path : paths) {
            if (new File(path).exists())
                return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(
                    new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            if (in.readLine() != null)
                return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null)
                process.destroy();
        }
    }

    public static int returnID(final Activity activity, Spinner spin, String sql, String ColumnName) {

        int pos = spin.getSelectedItemPosition();
        int id = 0;
        DataProvider dataProvider;
        dataProvider = new DataProvider(activity);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        data = dataProvider.getDynamicVal(sql);

        if (data != null && data.size() > 0) {
            if (pos > 0) {
                if (data.get(pos - 1).get(ColumnName).length() > 0) {
                    id = Integer.valueOf(data.get(pos - 1).get(ColumnName));
                }
            }
        }
        return id;
    }

    public static String returnIDstr(final Activity activity, Spinner spin, String sql, String ColumnName) {

        int pos = spin.getSelectedItemPosition();
        String id = "";
        DataProvider dataProvider;
        dataProvider = new DataProvider(activity);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        data = dataProvider.getDynamicVal(sql);

        if (data != null && data.size() > 0) {
            if (pos > 0) {
                if (data.get(pos - 1).get(ColumnName).length() > 0) {
                    id = data.get(pos - 1).get(ColumnName);
                }
            }
        }
        return id;
    }

    public static void fillspinner(final Activity activity, Spinner spin, String sql, String ColumnName) {
        DataProvider dataProvider;
        dataProvider = new DataProvider(activity);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        data = dataProvider.getDynamicVal(sql);

        ArrayAdapter<String> adapter;
        if (data != null && data.size() > 0) {
            int isize = data.size();
            String sValue[] = new String[isize + 1];
            sValue[0] = activity.getResources().getString(R.string.select);
            for (int i = 0; i < data.size(); i++) {
                sValue[i + 1] = data.get(i).get(ColumnName);
            }


            adapter = new ArrayAdapter<String>(activity,
                    android.R.layout.simple_spinner_dropdown_item, sValue);
            spin.setAdapter(adapter);
        }

    }

    public static void Setspinner(final Activity activity, Spinner spin, String sql, String ColumnName, String sMatchValue) {
        DataProvider dataProvider;
        dataProvider = new DataProvider(activity);
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        data = dataProvider.getDynamicVal(sql);

        ArrayAdapter<String> adapter;
        if (data != null && data.size() > 0) {
            int id = 0;
            for (int i = 0; i < data.size(); i++) {
                if (sMatchValue != null && ((data.get(i).get(ColumnName)).equalsIgnoreCase(sMatchValue))) {
                    id = i + 1;
                    break;
                }
            }
            spin.setSelection(id);
        }

    }

    public static void verification(int Userid, int ModuleID, String ModuleGUID, int Verify, String AshaID, String ANMID, String AFID, Button btnNotverified, Button btnVerifiedOk, Button btnVerifiedNotOk) {

        String sCount = "Select count(*) from tblafverify where ModuleGUID='" + ModuleGUID + "'";
        int iCount = dataProvider.getMaxRecord(sCount);
        if (iCount == 0) {
            String sqlInsert = "Insert into tblafverify (ModuleID,ModuleGUID,Verify,VerifyOn,AshaID,ANMID,AFID,CreatedBy,CreatedOn,IsEdited) values (" + ModuleID + ",'" + ModuleGUID + "'," + Verify + ",'" + getcurrentdate() + "'," + AshaID + "," +
                    ANMID + "," + AFID + "," + Userid + ",'" + getcurrentdate() + "',1)";
            dataProvider.executeSql(sqlInsert);

        } else {
            String sqlUpdate = "Update tblafverify set IsEdited = 1,Verify=" + Verify + ", VerifyOn='" + getcurrentdate() + "', UpdatedOn='" + getcurrentdate() + "',UpdatedBy=" + Userid + " where ModuleGUID='" + ModuleGUID + "'";
            dataProvider.executeSql(sqlUpdate);
        }

        String sVerify = "Select Verify from tblafverify where ModuleGUID='" + ModuleGUID + "'";
        if (dataProvider.getRecord(sVerify).equals("1")) {
            btnNotverified.setBackgroundColor(context.getResources().getColor(R.color.buttonorange));
            btnVerifiedOk.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
            btnVerifiedNotOk.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
        } else if (dataProvider.getRecord(sVerify).equals("2")) {
            btnNotverified.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
            btnVerifiedOk.setBackgroundColor(context.getResources().getColor(R.color.DarkGreen));
            btnVerifiedNotOk.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
        } else {
            btnNotverified.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
            btnVerifiedOk.setBackgroundColor(context.getResources().getColor(R.color.lightgray));
            btnVerifiedNotOk.setBackgroundColor(context.getResources().getColor(R.color.DarkRed));
        }
    }

    public static void verificationanc(int Userid, int ModuleID, String ModuleGUID, int Verify, String AshaID, String ANMID, String AFID) {

        String sCount = "Select count(*) from tblafverify where ModuleGUID='" + ModuleGUID + "'";
        int iCount = dataProvider.getMaxRecord(sCount);
        if (iCount == 0) {
            String sqlInsert = "Insert into tblafverify (ModuleID,ModuleGUID,Verify,VerifyOn,AshaID,ANMID,AFID,CreatedBy,CreatedOn,IsEdited) values (" + ModuleID + ",'" + ModuleGUID + "'," + Verify + ",'" + getcurrentdate() + "'," + AshaID + "," +
                    ANMID + "," + AFID + "," + Userid + ",'" + getcurrentdate() + "',1)";
            dataProvider.executeSql(sqlInsert);

        } else {
            String sqlUpdate = "Update tblafverify set IsEdited = 1,Verify=" + Verify + ", VerifyOn='" + getcurrentdate() + "', UpdatedOn='" + getcurrentdate() + "',UpdatedBy=" + Userid + " where ModuleGUID='" + ModuleGUID + "'";
            dataProvider.executeSql(sqlUpdate);
        }


    }

}
