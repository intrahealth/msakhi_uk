package com.microware.intrahealth.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.VHND;
import com.microware.intrahealth.VHND_DueList_Report;
import com.microware.intrahealth.VHND_DueList_new;
import com.microware.intrahealth.VHND_Performance;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.VHND_Schedule;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_VHND_DueList;

public class VHND_Record_adapter_new extends BaseAdapter {
    Context context;
    DataProvider dataProvider;
    ArrayList<VHND_Schedule> VHND_Date;
    // String Date = "";
    int flag = 0;
    Global global;

    public VHND_Record_adapter_new(Context context,
                                   ArrayList<VHND_Schedule> VHND_Date, int flag) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.VHND_Date = VHND_Date;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int ivalue = 0;
        if (flag == 1) {
            ivalue = VHND_Date.size();
        }
        return ivalue;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View gridview = null;
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            // dataProvider = new DataProvider(context);
            gridview = new View(context);
            dataProvider = new DataProvider(context);
            gridview = layoutInflater.inflate(R.layout.vhnd_record_adapter_new,
                    null);
            // Jan
            TextView village_Jan = (TextView) gridview
                    .findViewById(R.id.village_Jan);
            TextView tx_date_Jan = (TextView) gridview
                    .findViewById(R.id.tx_date_Jan);
            TextView tx_date_show_Jan = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Jan);
            Button btn_Add_Jan = (Button) gridview
                    .findViewById(R.id.btn_Add_Jan);
            Button btn_edit_Jan = (Button) gridview
                    .findViewById(R.id.btn_edit_Jan);
            // Feb
            TextView village_Feb = (TextView) gridview
                    .findViewById(R.id.village_Feb);
            TextView tx_date_Feb = (TextView) gridview
                    .findViewById(R.id.tx_date_Feb);
            TextView tx_date_show_Feb = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Feb);
            Button btn_Add_Feb = (Button) gridview
                    .findViewById(R.id.btn_Add_Feb);
            Button btn_edit_Feb = (Button) gridview
                    .findViewById(R.id.btn_edit_Feb);
            // Mar
            TextView village_Mar = (TextView) gridview
                    .findViewById(R.id.village_Mar);
            TextView tx_date_Mar = (TextView) gridview
                    .findViewById(R.id.tx_date_Mar);
            TextView tx_date_show_Mar = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Mar);
            Button btn_Add_Mar = (Button) gridview
                    .findViewById(R.id.btn_Add_Mar);
            Button btn_edit_Mar = (Button) gridview
                    .findViewById(R.id.btn_edit_Mar);
            // Apr
            TextView village_Apr = (TextView) gridview
                    .findViewById(R.id.village_Apr);
            TextView tx_date_Apr = (TextView) gridview
                    .findViewById(R.id.tx_date_Apr);
            TextView tx_date_show_Apr = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Apr);
            Button btn_Add_Apr = (Button) gridview
                    .findViewById(R.id.btn_Add_Apr);
            Button btn_edit_Apr = (Button) gridview
                    .findViewById(R.id.btn_edit_Apr);
            // May
            TextView village_May = (TextView) gridview
                    .findViewById(R.id.village_May);
            TextView tx_date_May = (TextView) gridview
                    .findViewById(R.id.tx_date_May);
            TextView tx_date_show_May = (TextView) gridview
                    .findViewById(R.id.tx_date_show_May);
            Button btn_Add_May = (Button) gridview
                    .findViewById(R.id.btn_Add_May);
            Button btn_edit_May = (Button) gridview
                    .findViewById(R.id.btn_edit_May);
            // Jun
            TextView village_Jun = (TextView) gridview
                    .findViewById(R.id.village_Jun);
            TextView tx_date_Jun = (TextView) gridview
                    .findViewById(R.id.tx_date_Jun);
            TextView tx_date_show_Jun = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Jun);
            Button btn_Add_Jun = (Button) gridview
                    .findViewById(R.id.btn_Add_Jun);
            Button btn_edit_Jun = (Button) gridview
                    .findViewById(R.id.btn_edit_Jun);
            // Jul
            TextView village_Jul = (TextView) gridview
                    .findViewById(R.id.village_Jul);
            TextView tx_date_Jul = (TextView) gridview
                    .findViewById(R.id.tx_date_Jul);
            TextView tx_date_show_Jul = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Jul);
            Button btn_Add_Jul = (Button) gridview
                    .findViewById(R.id.btn_Add_Jul);
            Button btn_edit_Jul = (Button) gridview
                    .findViewById(R.id.btn_edit_Jul);
            // Aug
            TextView village_Aug = (TextView) gridview
                    .findViewById(R.id.village_Aug);
            TextView tx_date_Aug = (TextView) gridview
                    .findViewById(R.id.tx_date_Aug);
            TextView tx_date_show_Aug = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Aug);
            Button btn_Add_Aug = (Button) gridview
                    .findViewById(R.id.btn_Add_Aug);
            Button btn_edit_Aug = (Button) gridview
                    .findViewById(R.id.btn_edit_Aug);
            // Sep
            TextView village_Sep = (TextView) gridview
                    .findViewById(R.id.village_Sep);
            TextView tx_date_Sep = (TextView) gridview
                    .findViewById(R.id.tx_date_Sep);
            TextView tx_date_show_Sep = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Sep);
            Button btn_Add_Sep = (Button) gridview
                    .findViewById(R.id.btn_Add_Sep);
            Button btn_edit_Sep = (Button) gridview
                    .findViewById(R.id.btn_edit_Sep);
            // Oct
            TextView village_Oct = (TextView) gridview
                    .findViewById(R.id.village_Oct);
            TextView tx_date_Oct = (TextView) gridview
                    .findViewById(R.id.tx_date_Oct);
            TextView tx_date_show_Oct = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Oct);
            Button btn_Add_Oct = (Button) gridview
                    .findViewById(R.id.btn_Add_Oct);
            Button btn_edit_Oct = (Button) gridview
                    .findViewById(R.id.btn_edit_Oct);
            // Nov
            TextView village_Nov = (TextView) gridview
                    .findViewById(R.id.village_Nov);
            TextView tx_date_Nov = (TextView) gridview
                    .findViewById(R.id.tx_date_Nov);
            TextView tx_date_show_Nov = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Nov);
            Button btn_Add_Nov = (Button) gridview
                    .findViewById(R.id.btn_Add_Nov);
            Button btn_edit_Nov = (Button) gridview
                    .findViewById(R.id.btn_edit_Nov);
            // Dec
            TextView village_Dec = (TextView) gridview
                    .findViewById(R.id.village_Dec);
            TextView tx_date_Dec = (TextView) gridview
                    .findViewById(R.id.tx_date_Dec);
            TextView tx_date_show_Dec = (TextView) gridview
                    .findViewById(R.id.tx_date_show_Dec);
            Button btn_Add_Dec = (Button) gridview
                    .findViewById(R.id.btn_Add_Dec);
            Button btn_edit_Dec = (Button) gridview
                    .findViewById(R.id.btn_edit_Dec);
            // tablerow
            TableRow tbl_jan, tbl_feb, tbl_mar, tbl_apr, tbl_may, tbl_jun, tbl_jul, tbl_aug, tbl_sep, tbl_oct, tbl_nov, tbl_dec;
            tbl_jan = (TableRow) gridview.findViewById(R.id.tbl_jan);
            tbl_feb = (TableRow) gridview.findViewById(R.id.tbl_feb);
            tbl_mar = (TableRow) gridview.findViewById(R.id.tbl_mar);
            tbl_apr = (TableRow) gridview.findViewById(R.id.tbl_apr);
            tbl_may = (TableRow) gridview.findViewById(R.id.tbl_may);
            tbl_jun = (TableRow) gridview.findViewById(R.id.tbl_jun);
            tbl_jul = (TableRow) gridview.findViewById(R.id.tbl_jul);
            tbl_aug = (TableRow) gridview.findViewById(R.id.tbl_aug);
            tbl_sep = (TableRow) gridview.findViewById(R.id.tbl_sep);
            tbl_oct = (TableRow) gridview.findViewById(R.id.tbl_oct);
            tbl_nov = (TableRow) gridview.findViewById(R.id.tbl_nov);
            tbl_dec = (TableRow) gridview.findViewById(R.id.tbl_dec);

            dataProvider = new DataProvider(context);
            global = (Global) context.getApplicationContext();

            // Jan
            village_Jan.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Jan.setText(String
                    .valueOf(VHND_Date.get(position).getJan()));
            tx_date_show_Jan.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getJan())));
            ButtonListioner(btn_Add_Jan, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJan(), 0, tbl_jan);
            ButtonListioner(btn_edit_Jan, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJan(), 1, tbl_jan);
            // Feb
            village_Feb.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Feb.setText(String
                    .valueOf(VHND_Date.get(position).getFeb()));
            tx_date_show_Feb.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getFeb())));
            ButtonListioner(btn_Add_Feb, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getFeb(), 0, tbl_feb);
            ButtonListioner(btn_edit_Feb, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getFeb(), 1, tbl_feb);
            // Mar
            village_Mar.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Mar.setText(String
                    .valueOf(VHND_Date.get(position).getMar()));
            tx_date_show_Mar.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getMar())));
            ButtonListioner(btn_Add_Mar, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getMar(), 0, tbl_mar);
            ButtonListioner(btn_edit_Mar, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getMar(), 1, tbl_mar);
            // Apr
            village_Apr.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Apr.setText(String
                    .valueOf(VHND_Date.get(position).getApr()));
            tx_date_show_Apr.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getApr())));
            ButtonListioner(btn_Add_Apr, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getApr(), 0, tbl_apr);
            ButtonListioner(btn_edit_Apr, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getApr(), 1, tbl_apr);
            // May
            village_May.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_May.setText(String
                    .valueOf(VHND_Date.get(position).getMay()));
            tx_date_show_May.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getMay())));
            ButtonListioner(btn_Add_May, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getMay(), 0, tbl_may);
            ButtonListioner(btn_edit_May, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getMay(), 1, tbl_may);
            // Jun
            village_Jun.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Jun.setText(String
                    .valueOf(VHND_Date.get(position).getJun()));
            tx_date_show_Jun.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getJun())));
            ButtonListioner(btn_Add_Jun, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJun(), 0, tbl_jun);
            ButtonListioner(btn_edit_Jun, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJun(), 1, tbl_jun);
            // Jul
            village_Jul.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Jul.setText(String
                    .valueOf(VHND_Date.get(position).getJul()));
            tx_date_show_Jul.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getJul())));
            ButtonListioner(btn_Add_Jul, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJul(), 0, tbl_jul);
            ButtonListioner(btn_edit_Jul, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getJul(), 1, tbl_jul);
            // Aug
            village_Aug.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Aug.setText(String
                    .valueOf(VHND_Date.get(position).getAug()));
            tx_date_show_Aug.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getAug())));
            ButtonListioner(btn_Add_Aug, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getAug(), 0, tbl_aug);
            ButtonListioner(btn_edit_Aug, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getAug(), 1, tbl_aug);
            // Sep
            village_Sep.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Sep.setText(String
                    .valueOf(VHND_Date.get(position).getSep()));
            tx_date_show_Sep.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getSep())));
            ButtonListioner(btn_Add_Sep, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getSep(), 0, tbl_sep);
            ButtonListioner(btn_edit_Sep, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getSep(), 1, tbl_sep);
            // Oct
            village_Oct.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Oct.setText(String
                    .valueOf(VHND_Date.get(position).getOct()));
            tx_date_show_Oct.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getOct())));
            ButtonListioner(btn_Add_Oct, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getOct(), 0, tbl_oct);
            ButtonListioner(btn_edit_Oct, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getOct(), 1, tbl_oct);
            // Nov
            village_Nov.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Nov.setText(String
                    .valueOf(VHND_Date.get(position).getNov()));
            tx_date_show_Nov.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getNov())));
            ButtonListioner(btn_Add_Nov, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getNov(), 0, tbl_nov);
            ButtonListioner(btn_edit_Nov, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getNov(), 1, tbl_nov);
            // Dec
            village_Dec.setText(String.valueOf(VHND_Date.get(position)
                    .getAW_Name()));
            tx_date_Dec.setText(String
                    .valueOf(VHND_Date.get(position).getDec()));
            tx_date_show_Dec.setText(Validate.changeDateFormat(String
                    .valueOf(VHND_Date.get(position).getDec())));
            ButtonListioner(btn_Add_Dec, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getDec(), 0, tbl_dec);
            ButtonListioner(btn_edit_Dec, VHND_Date.get(position).getASHA_ID(),
                    VHND_Date.get(position).getVillage_ID(),
                    VHND_Date.get(position).getDec(), 1, tbl_dec);

            String dateee[] = {};
            String aa[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                    "Aug", "Sep", "Oct", "Nov", "Dec"};
            int month = 0, Year = 0;

        } else {
            gridview = convertView;
        }
        return gridview;
    }

    public void ButtonListioner(final Button edit, final int ASHA_ID,
                                final int Village_ID, final String Date, final int flag,
                                final TableRow tbl) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                    Locale.US);
            Date date = new Date();
            String CurrentDate = "";
            CurrentDate = dateFormat.format(date);
            String dateee[] = {}, Systemdate[] = {};

            int dateday = 0, month = 0, Year = 0;
            int S_dateday = 0, S_month = 0, S_Year = 0;
            if (!Date.equalsIgnoreCase("") && Date != null) {
                dateee = Date.split("-");
                month = Validate.returnIntegerValue(dateee[1]);
                Year = Validate.returnIntegerValue(dateee[0]);
                dateday = Validate.returnIntegerValue(dateee[2]);
                String currmnthdate1 = dataProvider
                        .getRecord("select   cast(julianday('" + Date
                                + "')-julianday('" + CurrentDate
                                + "') as int ) as day");
                int daynew = Validate.returnIntegerValue(currmnthdate1);
                Systemdate = String.valueOf(CurrentDate).split("-");
                // Systemdate = String.valueOf("2017-04-07").split("-");
                S_month = Validate.returnIntegerValue(Systemdate[1]);
                S_Year = Validate.returnIntegerValue(Systemdate[0]);
                S_dateday = Validate.returnIntegerValue(Systemdate[2]);

                if (flag == 0) {
                    if ((month == S_month) && Year == S_Year) {
                        if (daynew > -7 && daynew < 1
                                && global.getiGlobalRoleID() == 2) {
                            edit.setEnabled(true);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.drawable.btn_bg_custom));
                        } else {
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.color.gray));
                            edit.setEnabled(false);
                        }
                    } else if ((month != S_month) && Year == S_Year
                            && global.getiGlobalRoleID() == 2) {

                        if (daynew > -7 && daynew < 1) {
                            edit.setEnabled(true);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.drawable.btn_bg_custom));
                        } else {
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.color.gray));
                            edit.setEnabled(false);
                        }

                    } else {

                        if (daynew > -7 && daynew < 1
                                && global.getiGlobalRoleID() == 2) {
                            edit.setEnabled(true);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.drawable.btn_bg_custom));
                        } else {
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.color.gray));
                            edit.setEnabled(false);
                        }

                    }
                } else if (flag == 1) {
                    if ((month == S_month) && Year == S_Year) {
                        if (daynew > -7 && daynew < 8) {
                            edit.setEnabled(true);
                            tbl.setVisibility(View.VISIBLE);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.drawable.btn_bg_custom));
                        } else {

                            // tbl.setVisibility(View.GONE);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.color.gray));
                            edit.setEnabled(false);
                        }
                    } else {
                        if ((((month - 1) == S_month)
                                || (month == 1 && S_month == 12)) &&(daynew > -61 && daynew < 61) ) {
                            tbl.setVisibility(View.VISIBLE);
                        } else if (((month == (S_month - 1))
                                || (month == 12 && S_month == 1))&&(daynew > -61 && daynew < 61)) {
                            tbl.setVisibility(View.VISIBLE);
                        } else {
                            tbl.setVisibility(View.GONE);
                        }
                        if (daynew > -7 && daynew < 8) {
                            edit.setEnabled(true);
                            tbl.setVisibility(View.VISIBLE);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.drawable.btn_bg_custom));
                        } else {

                            // tbl.setVisibility(View.GONE);
                            edit.setBackgroundDrawable(context.getResources()
                                    .getDrawable(R.color.gray));
                            edit.setEnabled(false);
                        }
                    }
                }

                // if (Daycount <= 0) {
                // btn_edit.setBackgroundDrawable(context.getResources()
                // .getDrawable(R.drawable.btn_bg_custom));
                // btn_edit.setEnabled(true);
                // } else {
                // btn_edit.setBackgroundDrawable(context.getResources()
                // .getDrawable(R.color.gray));
                // btn_edit.setEnabled(false);
                // }
            }

            edit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String sqql = "", VHND_ID = "";
                    if (flag == 0) {
                        global.setVHND_Date(String.valueOf(Date));
                        global.setVHND_VillageID(String.valueOf(Village_ID));
                        global.setVHND_Composite_Flag("U");
                        sqql = "Select VHND_ID from tbl_VHNDPerformance where Date='"
                                + Date
                                + "' and AshaId='"
                                + ASHA_ID
                                + "' and VillageId='" + Village_ID + "' ";
                        VHND_ID = dataProvider.getRecord(sqql);
                        global.setVHND_ID(VHND_ID);
                        Intent in = new Intent(context, VHND_Performance.class);
                        context.startActivity(in);
                    } else if (flag == 1) {
                        global.setVHND_Date(String.valueOf(Date));
                        global.setVHND_VillageID(String.valueOf(Village_ID));
                        global.setVHND_Composite_Flag("U");
                        sqql = "Select VHND_ID from tbl_VHNDPerformance where Date='"
                                + Date
                                + "' and AshaId='"
                                + ASHA_ID
                                + "' and VillageId='" + Village_ID + "' ";
                        VHND_ID = dataProvider.getRecord(sqql);
                        global.setVHND_ID(VHND_ID);
                        Intent in = new Intent(context, VHND_DueList_new.class);
                        context.startActivity(in);
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}