package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstCommon;
import com.microware.intrahealth.object.tblMigration;

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
public class MigrationForm extends Activity {
	DataProvider dataProvider;
	Global global;
	Button btnSave;
	Dialog datepic;
	int count=0;
	ArrayAdapter<String> adapter;
	Spinner spinReason;
	TextView tvMigrateout,tvMigratein,username;
	TableRow tblMigrationout,tblMigrationin,tblReason;
	public ArrayList<MstCommon> common = new ArrayList<MstCommon>();
	ArrayList<tblMigration> migration= new ArrayList<tblMigration>();
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		dataProvider = new DataProvider(this);
		global = (Global)getApplicationContext();
		setContentView(R.layout.migration);
		tvMigratein=(TextView) findViewById(R.id.tvMigratein);
		spinReason=(Spinner) findViewById(R.id.spinReason);
		tvMigrateout=(TextView) findViewById(R.id.tvMigrateout);
		username=(TextView) findViewById(R.id.username);
		tblReason=(TableRow) findViewById(R.id.tblReason);
		tblMigrationout=(TableRow) findViewById(R.id.tblMigrationout);
		tblMigrationin=(TableRow) findViewById(R.id.tblMigrationin);
		btnSave=(Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				savedata();
				CustomAlertSave(getResources().getString(R.string.savesuccessfully));
			}
		});
		tvMigratein.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvMigratein);
			}
		});
		tvMigrateout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowDatePicker(tvMigrateout);
			}
		});
		String sql1="select FamilyMemberName from Tbl_HHFamilyMember where HHFamilyMemberGUID='"+global.getGlobalHHFamilyMemberGUID()+"'";	
		String name=dataProvider.getRecord(sql1);
		username.setText(name);
		String sql="select StatusID from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"+global.getGlobalHHFamilyMemberGUID()+"'";
				count =dataProvider.getMaxRecord(sql);
				if(count==3){
					tblMigrationout.setVisibility(View.GONE);
					tblMigrationin.setVisibility(View.VISIBLE);
//					tblReason.setVisibility(View.GONE);
				}else{
					tblMigrationout.setVisibility(View.VISIBLE);
					tblMigrationin.setVisibility(View.GONE);
//					tblReason.setVisibility(View.VISIBLE);
				}
		if(global.getMigrationGUID()!=null && global.getMigrationGUID().length()>0){
			showdata();
		}
}
	public void fillspiners(Spinner spin, int iFlag,int languageID) {
		common = dataProvider.getCommonRecord(languageID,iFlag);

		String Where[] = new String[common.size() + 1];
		Where[0] = getResources().getString(R.string.select);

		for (int i = 1; i < common.size() + 1; i++) {
			Where[i] = common.get(i - 1).getValue();
			// iBlockID[i] = Block.get(i).getBlockID();

		}

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, Where);

		spin.setAdapter(adapter);

	}
	public int returnPosition(int flag, int ID) {
		int ipos = 0;
		ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
		combobox = dataProvider.getCommonRecord(global.getLanguage(),flag);
		for (int i = 0; i < combobox.size(); i++) {
			if (ID == combobox.get(i).getID()) {
				ipos = i+1;
			}
		}

		return ipos;
	}
	public int returnID(int flag, int pos) {
		int ipos = 0;
		ArrayList<MstCommon> combobox = new ArrayList<MstCommon>();
		combobox = dataProvider.getCommonRecord(global.getLanguage(),flag);
		if (pos > 0) {
			ipos = combobox.get(pos - 1).getID();
		}
		return ipos;
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
				finish();
				dialog.dismiss();
				
			}
		});
	
	// Display the dialog
		dialog.show();

	}
	public void ShowDatePicker(final TextView tVvisit)
	{

		String languageToLoad  = "en"; // your language
	    Locale locale = new Locale(languageToLoad); 
	    Locale.setDefault(locale);
		// TODO Auto-generated method stub
		datepic = new Dialog(this, R.style.CustomTheme);

		Window window = datepic.getWindow();
		window.requestFeature(Window.FEATURE_NO_TITLE);
		datepic.setContentView(R.layout.datetimepicker);
		Button btnset = (Button) datepic.findViewById(R.id.set);
		Button btncancel = (Button) datepic.findViewById(R.id.cancle);

		datepic.show();

		datepic.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		final DatePicker datetext = (DatePicker) datepic
				.findViewById(R.id.idfordate);
		Date date=new Date();
		datetext.setMaxDate((long) date.getTime());
	

		btncancel.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated

				datepic.dismiss();

			}
		});


		btnset.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unused")
			public void onClick(View v) {
				String[] monthName = { "January", "February", "March", "April",
						"May", "June", "July", "August", "September",
						"October", "November", "December" };
				int dt = datetext.getDayOfMonth();
				int month = datetext.getMonth()+1;
				int year = datetext.getYear();
				String date = "" + dt;
				String mnth = "" + month;
				if (dt < 10) {
					date = "0" + dt;
				}
				if (month < 10) {
					mnth = "0" + month;
				}

				String sSellectedDate = date + "-" + mnth + "-"
						+ String.valueOf(year);
				tVvisit.setText(sSellectedDate);
				
				datepic.dismiss();
			}
		});


	}
	public void showdata(){
		migration=dataProvider.geMigrationData(1,global.getGlobalHHFamilyMemberGUID(),global.getMigrationGUID());
		if(migration!=null && migration.size()>0){
			if(migration.get(0).getDateOfMigrationIn()!=null &&migration.get(0).getDateOfMigrationIn().length()>0){
				tvMigratein.setText(Validate.changeDateFormat(migration.get(0).getDateOfMigrationIn()));
				tblMigrationin.setVisibility(View.GONE);
				tblMigrationout.setVisibility(View.VISIBLE);
//				tblReason.setVisibility(View.VISIBLE);
			}else{
				tblMigrationin.setVisibility(View.VISIBLE);
				tblMigrationout.setVisibility(View.GONE);
//				tblReason.setVisibility(View.GONE);
			}
			if(migration.get(0).getDateOfMigrationOut()!=null &&migration.get(0).getDateOfMigrationOut().length()>0){
				tvMigrateout.setText(Validate.changeDateFormat(migration.get(0).getDateOfMigrationOut()));
				tblMigrationin.setVisibility(View.VISIBLE);
				tblMigrationout.setVisibility(View.GONE);
//				tblReason.setVisibility(View.GONE);
			}else{
				tblMigrationin.setVisibility(View.GONE);
				tblMigrationout.setVisibility(View.VISIBLE);
//				tblReason.setVisibility(View.VISIBLE);
			}
		}
	}
	
public void savedata(){
	String DateOfMigrationIn="";
	String DateOfMigrationOut="";
	String MigrationGUID="";
	String Flag="";
	String CreatedOn=Validate.getcurrentdate();
	int CreatedBy=0;
	if(global.getsGlobalUserID()!=null && global.getsGlobalUserID().length()>0){
		CreatedBy=Integer.valueOf(global.getsGlobalUserID());
	}
	if(tvMigrateout.getText().toString()!=null && tvMigrateout.getText().toString().length()>0){
		DateOfMigrationOut=Validate.changeDateFormat(tvMigrateout.getText().toString());
	}
	if(tvMigratein.getText().toString()!=null && tvMigratein.getText().toString().length()>0){
		DateOfMigrationIn=Validate.changeDateFormat(tvMigratein.getText().toString());
	}
	if(global.getMigrationGUID()!=null && global.getMigrationGUID().length()>0){
		Flag="u";
		dataProvider.saveMigrationDetails(Flag,CreatedOn,CreatedBy,DateOfMigrationIn,DateOfMigrationOut,global.getMigrationGUID(),global.getGlobalHHFamilyMemberGUID());
	}else{
		Flag="i";
		MigrationGUID=Validate.random();
		dataProvider.saveMigrationDetails(Flag,CreatedOn,CreatedBy,DateOfMigrationIn,DateOfMigrationOut,MigrationGUID,global.getGlobalHHFamilyMemberGUID());
	}
	 Calendar c1 = Calendar.getInstance();
	 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c1.getTime());
	 String sql="insert into  tblhhupdate_Log (StatusId_Old,StatusId_New,hhsurveyguid,hhmemberguid,UpdatedOn)values("+count+",3,'"+global.getGlobalHHSurveyGUID()+"','"+global.getGlobalHHFamilyMemberGUID()+"','"+formattedDate+"') ";
		dataProvider.executeSql(sql);
	
}
}
