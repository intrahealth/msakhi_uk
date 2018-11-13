package com.microware.intrahealth.IncentiveAdapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.Incentive.AnmReport_Activity;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microware on 11/20/2017.
 */

public class AnmReport_Adapter extends RecyclerView.Adapter<AnmReport_Adapter.MyViewHolder> {

    Context context;
    DataProvider dataprovider;
    Global global;
    ArrayList<HashMap<String, String>> data;

    public AnmReport_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.anmreport_adapter, parent, false);
        dataprovider = new DataProvider(context);
        global = (Global) context.getApplicationContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int AMStatus = Integer.valueOf(data.get(position).get("ANMStatus"));
        int BCPMStatus = Integer.valueOf(data.get(position).get("BCPMStatus"));
        holder.tv_Activitycode.setText(data.get(position).get("ActivitySrno"));
        holder.tv_claimed.setText(data.get(position).get("ActivityTotal"));
        if (AMStatus == 1) {
            holder.tv_ANM.setText("A");
            holder.tv_ANM.setBackgroundColor(context.getResources().getColor(R.color.Green));
        } else if (AMStatus == 2) {
            holder.tv_ANM.setText("NA");
            holder.tv_claimed.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
            holder.tv_ANM.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
        }
        if (BCPMStatus == 1) {
            holder.tv_BCPM.setText("A");
            holder.tv_BCPM.setBackgroundColor(context.getResources().getColor(R.color.Green));
        } else if (BCPMStatus == 2) {
            holder.tv_BCPM.setText("NA");
            holder.tv_BCPM.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
        }

        holder.tv_ANM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_ANM.getText().toString().equalsIgnoreCase("A")) {
                    CustomAlert(position, holder.tv_ANM, holder.tv_claimed);

                } else  if (holder.tv_ANM.getText().toString().equalsIgnoreCase("NA")) {
                    String sqlNotApproved = "select  NotApproved from tblincentivesurvey  where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' ";
                    String NotApproved = dataprovider.getRecord(sqlNotApproved);
                    int NotApprovedamt = Integer.valueOf(NotApproved);
                    if (NotApprovedamt > 0 && holder.tv_ANM.getText().toString().equalsIgnoreCase("NA")) {
                        NotApprovedamt = NotApprovedamt - Integer.valueOf(data.get(position).get("ActivityTotal"));
                        String sqlupdate = "update  tblincentivesurvey set NotApproved='" + NotApprovedamt + "' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' ";
                        dataprovider.executeSql(sqlupdate);
                    }

                    String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=0,RemarkAMStatus='' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' and IncentiveGUID='" + data.get(position).get("IncentiveGUID") + "' ";
                    dataprovider.executeSql(sqlupdateincentive);
                    holder.tv_ANM.setText("");
                    holder.tv_ANM.setBackgroundColor(context.getResources().getColor(R.color.Silver));
                    //Toast.makeText(context, context.getResources().getString(R.string.partiallyapproved), Toast.LENGTH_SHORT).show();


                }else{
                    String sqlupdate = "update  tblincentivesurvey set ANMStatus=1 ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' ";
                    dataprovider.executeSql(sqlupdate);
                    String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=1,RemarkAMStatus='' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(position).get("IncentiveSurveyGUID") + "' and IncentiveGUID='" + data.get(position).get("IncentiveGUID") + "' ";
                    dataprovider.executeSql(sqlupdateincentive);
                    holder.tv_ANM.setText("A");
                    holder.tv_ANM.setBackgroundColor(context.getResources().getColor(R.color.Green));
                    Toast.makeText(context, context.getResources().getString(R.string.partiallyapproved), Toast.LENGTH_SHORT).show();
                }
                ((AnmReport_Activity)context).showwdata();
            }
        });
        holder.tv_Activitycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sql = "select Activity from mstashaactivity where  LangaugeID=1 and AreaType='R' and Qsrno='" + holder.tv_Activitycode.getText().toString() + "' ";
                String sqlactivity = dataprovider.getRecord(sql);
                CustomAlert(sqlactivity);
            }
        });
        holder.tv_claimed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_ANM.getText().toString().equalsIgnoreCase("NA")) {
                    String sql = "select RemarkAMStatus from tblashaincentivedetail where   IncentiveGUID='" + data.get(position).get("IncentiveGUID") + "' ";
                    String sqlactivity = dataprovider.getRecord(sql);
                    CustomAlert(sqlactivity);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Activitycode, tv_claimed, tv_AF, tv_ANM, tv_BCPM, tv_BAM;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_Activitycode = (TextView) itemView.findViewById(R.id.tv_Activitycode);
            tv_claimed = (TextView) itemView.findViewById(R.id.tv_claimed);

            tv_ANM = (TextView) itemView.findViewById(R.id.tv_ANM);
            tv_BCPM = (TextView) itemView.findViewById(R.id.tv_BCPM);

        }
    }

    public void CustomAlert(final int pos, final TextView txt, final TextView txt1) {

        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.status_alert, null, false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        // ImageView image=(ImageView)findViewById(R.id.img_dialog_icon);
        // image.setVisibility(false);

        // Retrieve views from the inflated dialog layout and update their
        // values
        RelativeLayout rel_edit = (RelativeLayout) dialog
                .findViewById(R.id.et_layout_alert_content);

        Button btnyes = (Button) dialog.findViewById(R.id.btn_yes);

        TextView txtTitle = (TextView) dialog
                .findViewById(R.id.txt_alert_message);
        final EditText et_remark = (EditText) dialog
                .findViewById(R.id.et_remark);
        //txtTitle.setText(context.getResources().getString(R.string.Doyouwanttologout));

        // TextView txtMessage = (TextView)
        // dialog.findViewById(R.id.txt_dialog_message);
        // txtMessage.setText("Do you want to Leave the page ?");


        btnyes.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                String sqlNotApproved = "select  NotApproved from tblincentivesurvey  where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(pos).get("IncentiveSurveyGUID") + "' ";
                String NotApproved = dataprovider.getRecord(sqlNotApproved);
                int NotApprovedamt = Integer.valueOf(NotApproved) + Integer.valueOf(data.get(pos).get("ActivityTotal"));
                String sqlupdate = "update  tblincentivesurvey set NotApproved='" + NotApprovedamt + "' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(pos).get("IncentiveSurveyGUID") + "' ";
                dataprovider.executeSql(sqlupdate);
                String sqlupdateincentive = "update  tblashaincentivedetail set ANMStatus=2 ,RemarkAMStatus='" + et_remark.getText().toString() + "' ,IsEdited=1, UpdatedBy=" + global.getsGlobalUserID() + ", UpdatedOn='" + Validate.getcurrentdate() + "' where  AnmId='" + global.getsGlobalANMCODE() + "' and  Year='" + global.getGlobalYear() + "' and month='" + global.getGlobalMonth() + "' and AshaID='" + global.getsGlobalAshaCode() + "' and IncentiveSurveyGUID='" + data.get(pos).get("IncentiveSurveyGUID") + "' and IncentiveGUID='" + data.get(pos).get("IncentiveGUID") + "' ";
                dataprovider.executeSql(sqlupdateincentive);
                txt.setText("NA");
                txt1.setTextColor(context.getResources().getColor(R.color.pastelRed));
                ((AnmReport_Activity)context).showwdata();
                txt.setBackgroundColor(context.getResources().getColor(R.color.pastelRed));
                Toast.makeText(context, context.getResources().getString(R.string.rejected), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        // Display the dialog
        dialog.show();

    }

    public void CustomAlert(String msg) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) context
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

}
