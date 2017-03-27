package com.microware.intrahealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

@SuppressLint({ "InflateParams", "SimpleDateFormat" })
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
	TimePicker time;
	String Question = "";
	Dialog timepic;
	Button next;
	int CurrentPage = 0;
	ArrayList<tblmstFPQues> Questions = new ArrayList<tblmstFPQues>();
	ArrayList<tblmstFPQues> Questions1 = new ArrayList<tblmstFPQues>();
	// ArrayList<q_bank> child = new ArrayList<q_bank>();
	// ArrayList<q_bank> mother = new ArrayList<q_bank>();
	// ArrayList<Tbl_PncHomeVisitAns> pnc = new
	// ArrayList<Tbl_PncHomeVisitAns>();
	Button imgNext, imgEnd;

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
		Intent intent = getIntent();
		String ss = intent.getStringExtra("counselling");

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

//		TextView tvQuestions = (TextView) gridanc
//				.findViewById(R.id.tvQuestions);
//
//		TextView tvQuestions2 = (TextView) gridanc
//				.findViewById(R.id.tvQuestions2);
//		EditText etans = (EditText) gridanc.findViewById(R.id.etans);
//		TextView ansy = (TextView) gridanc.findViewById(R.id.ansy);
//		TextView ansn = (TextView) gridanc.findViewById(R.id.ansn);
		// refreshArrayList();
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final TextView ansfield = (TextView) gridanc
						.findViewById(R.id.ansfield);
				final TextView ansQ = (TextView) gridanc.findViewById(R.id.ans);
				final TextView tvdate = (TextView) gridanc
						.findViewById(R.id.tvdate);

//				TextView tvQuestions = (TextView) gridanc
//						.findViewById(R.id.tvQuestions);

				if (CurrentPage < Questions.size()) {

					int pageno = 0;
					if (ansfield.getText() != null
							&& ansfield.getText().toString().length() > 0) {
						if (CurrentPage == 62 || CurrentPage == 59) {
							if (tvdate.getText() != null
									&& tvdate.getText().toString().length() > 0) {
								if (CurrentPage == 59) {
									if(global.getiGlobalRoleID() != 3) {
										
									savedata(ansfield.getText().toString(),
											Validate.changeDateFormat(tvdate
													.getText().toString()));
									}
									CurrentPage = CurrentPage + 1;
								} else if (CurrentPage == 62) {
									if(global.getiGlobalRoleID() != 3) {
									savedata(ansfield.getText().toString(),
											Validate.changeDateFormat(tvdate
													.getText().toString()));
									}

									Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
									 finish();
									 startActivity(i);

								}

							}
						} else if (ansQ.getText() != null
								&& ansQ.getText().toString().length() > 0) {
							if(global.getiGlobalRoleID() != 3) {
							savedata(ansfield.getText().toString(), ansQ
									.getText().toString());
							}
							if (ansQ.getText().toString().equalsIgnoreCase("1")
									&& Questions.get(CurrentPage).getY_qid() > 0) {

								pageno = Integer.valueOf(Questions.get(
										CurrentPage).getY_qid());

								if (pageno == 999) {
									Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
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
									finish();
								}
								CurrentPage = pageno - 1;
							} else {
								if (pageno == 999
										|| Questions.get(CurrentPage)
												.getFinishid() == 1) {
									Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
									 finish();
									 startActivity(i);
								}

								CurrentPage = CurrentPage + 1;
							}
						}

					} else {
						if (pageno == 999
								|| Questions.get(CurrentPage).getFinishid() == 1) {
							Intent i = new Intent(FP_MemberVisit.this, FP_AA.class);
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
		String CurrentDate = new SimpleDateFormat("dd-MM-yyyy")
				.format(new Date());
		CurrentDate = Validate.changeDateFormat(CurrentDate);
		String WomenName_Guid = "";
		String flag = "";
		String dtaans = "";
		int AshaID=0;
		int ANMID=0;
		if(global.getsGlobalAshaCode()!=null && global.getsGlobalAshaCode().length()>0){
		AshaID=Integer.valueOf(global.getsGlobalAshaCode());
		}
		if(global.getsGlobalANMCODE()!=null && global.getsGlobalANMCODE().length()>0){
		ANMID=Integer.valueOf(global.getsGlobalANMCODE());
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
		ir = dataProvider.saveFP(AshaID,ANMID,dtaans, WomenName_Guid, pncguid, FPAns_Guid,
				ansfield, flag,global.getsGlobalUserID());
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
				 finish();
				 startActivity(i);
			}
		}
	}
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	// super.onBackPressed();
	//
	// Intent i = new Intent(FP_MemberVisit.this, FP_ExistMember.class);
	// finish();
	// startActivity(i);
	// }

}