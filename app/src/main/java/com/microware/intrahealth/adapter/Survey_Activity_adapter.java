package com.microware.intrahealth.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.microware.intrahealth.Dashboard_Activity;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Intrahealth_Tab_Activity;
import com.microware.intrahealth.Login;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Survey_Activity;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Tbl_HHSurvey;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
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

@SuppressLint("InflateParams")
public class Survey_Activity_adapter extends BaseAdapter {
    Context context;
    ArrayList<Tbl_HHSurvey> hhsurvey;
    Global global;
    DataProvider dataProvider;
    int StatusId = 0;

    public Survey_Activity_adapter(Context context,
                                   ArrayList<Tbl_HHSurvey> hhsurvey) {
        this.hhsurvey = hhsurvey;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return hhsurvey.size();
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

    @SuppressWarnings({"static-access"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.survey_activity_adapter,
                    null);
        } else {
            gridview = convertView;
        }

        global = (Global) context.getApplicationContext();
        dataProvider = new DataProvider(context);
        TextView tvHHCode = (TextView) gridview.findViewById(R.id.tvHHCode);
        TextView tvHHHeadName = (TextView) gridview
                .findViewById(R.id.tvHHHeadName);
        TableRow GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        ImageView btnEdit = (ImageView) gridview.findViewById(R.id.btnEdit);
        ImageView btnDelete = (ImageView) gridview.findViewById(R.id.btnDelete);
        tvHHCode.setText(String.valueOf(hhsurvey.get(position).getHHCode()));

        String status = "Select HHStatusID from Tbl_HHSurvey where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID() + "'";
        String stat = "";
        stat = dataProvider.getRecord(status);
        if (stat != null && !stat.equalsIgnoreCase("null") && stat.length() > 0) {
            StatusId = Validate.returnIntegerValue(stat);
        }

        if (StatusId == 3) {
            tvHHCode.setBackgroundResource(R.drawable.orangesheet);
            tvHHHeadName.setBackgroundResource(R.drawable.orangesheet);
            //GridRow.setBackgroundResource(R.drawable.orangesheet);
        } else if (StatusId == 2) {
            tvHHCode.setBackgroundResource(R.drawable.yellowsheet);
            tvHHHeadName.setBackgroundResource(R.drawable.yellowsheet);

            // GridRow.setBackgroundResource(R.drawable.yellowsheet);
//			GridRow.setBackgroundColor(context.getResources().getColor(
//					R.color.Yellow));
        } else {
            tvHHCode.setBackgroundResource(R.drawable.whitesheet);
            tvHHHeadName.setBackgroundResource(R.drawable.whitesheet);

            GridRow.setBackgroundColor(context.getResources().getColor(
                    R.color.White));
        }
        String sqlmember = "Select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID()
                + "'";
        String sqlage = "Select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID()
                + "'  and GenderID=0 ";
        String sqlname = "Select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID()
                + "'  and FamilyMemberName=''";
        String sqlrelation = "Select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID()
                + "'  and RelationID=0";
        int cage = 0, cmember = 0, cname = 0, crelation = 0;
        cage = dataProvider.getMaxRecord(sqlage);
        cmember = dataProvider.getMaxRecord(sqlmember);
        cname = dataProvider.getMaxRecord(sqlname);
        crelation = dataProvider.getMaxRecord(sqlrelation);
        if (cage > 0 || cname > 0 || cmember == 0 || crelation > 0) {
            btnEdit.setImageResource(R.drawable.editcount);
        } else {
            btnEdit.setImageResource(R.drawable.editicon);
        }
        String sql = "Select FamilyMemberName from Tbl_HHFamilyMember where HHSurveyGUID='"
                + hhsurvey.get(position).getHHSurveyGUID()
                + "'  and RelationID=1 and StatusID!=2";
        String newdata = dataProvider.getRecord(sql);
        if (newdata == null) {
            newdata = "";
        }
        tvHHHeadName.setText(newdata);

        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (countdata(hhsurvey.get(position).getHHSurveyGUID()) == 0) {
                    CustomAlert(hhsurvey.get(position).getHHSurveyGUID());
                } else {
                    Toast.makeText(context, R.string.RecordDeleted, Toast.LENGTH_LONG).show();
                }

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(v.getContext(),
                        Intrahealth_Tab_Activity.class);
                global.setGlobalHHSurveyGUID(hhsurvey.get(position)
                        .getHHSurveyGUID());

                context.startActivity(i);
            }
        });
        return gridview;
    }

    public int countdata(String GUID) {
        int total = 0;
        try {
            String sqltblChild = "select count(*) from  from tblChild where HHGUID='"
                    + GUID + "'";
            String sqltblPregnant_woman = "select count(*)  from tblPregnant_woman where HHGUID='"
                    + GUID + "'";
            String sqltblncdcbac = "select count(*)  from tblncdcbac where HHSurveyGUID='"
                    + GUID + "'";
            String sqltblncdfollowup = "select count(*)  from tblncdfollowup where HHSurveyGUID='"
                    + GUID + "'";
            String sqltblncdscreening = "select count(*)  from tblncdscreening where HHSurveyGUID='"
                    + GUID + "'";
            String sqltblFP_followup = "select count(*)  from tblFP_followup where HHSurveyGUID='"
                    + GUID + "'";
            String sql = "select count(*) from Tbl_HHSurvey where HHSurveyGUID='"
                    + GUID + "' and IsEdited=0";
            String sqlTbl_HHFamilyMember = "select count(*) from Tbl_HHFamilyMember where HHSurveyGUID='"
                    + GUID + "' and IsEdited=0";
            total = dataProvider.getMaxRecord(sql) + dataProvider.getMaxRecord(sqlTbl_HHFamilyMember) + dataProvider.getMaxRecord(sqltblChild) + dataProvider.getMaxRecord(sqltblPregnant_woman) + dataProvider.getMaxRecord(sqltblncdcbac) + dataProvider.getMaxRecord(sqltblncdfollowup) + dataProvider.getMaxRecord(sqltblncdscreening) + dataProvider.getMaxRecord(sqltblFP_followup);
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return total;
        }

    }

    public void deletedata(String GUID) {
        String sql = "Delete from Tbl_HHSurvey where HHSurveyGUID='"
                + GUID + "'";
        dataProvider.deleteRecord(sql);
        String sqlTbl_HHFamilyMember = "Delete from Tbl_HHFamilyMember where HHSurveyGUID='"
                + GUID + "'";
        dataProvider.deleteRecord(sqlTbl_HHFamilyMember);
        Toast.makeText(context, R.string.RecordDeletedSuccessfully, Toast.LENGTH_LONG).show();

        Intent in = new Intent(context, Survey_Activity.class);
        context.startActivity(in);
    }

    public void CustomAlert(final String guid) {

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
        txtTitle.setText(R.string.AreyousureforDeleteRecord);

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                // Dismiss the dialog
                // int iLoggedin = 0;
                deletedata(guid);
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

}
