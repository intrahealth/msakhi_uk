package com.microware.intrahealth;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.microware.intrahealth.adapter.immunizationAdapterQuestion;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.tblmstimmunizationQues;

@SuppressLint("InflateParams")
public class ImmunizationQuestionActivity extends Activity {
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
	String Question = "";
	Dialog timepic;
	Button next;
	int CurrentPage = 0;
	int prevPage = 0;
	ArrayList<tblmstimmunizationQues> Questions = new ArrayList<tblmstimmunizationQues>();
	ArrayList<tblmstimmunizationQues> Grp1 = new ArrayList<tblmstimmunizationQues>();
	ArrayList<tblmstimmunizationQues> Grp2 = new ArrayList<tblmstimmunizationQues>();
	ArrayList<tblmstimmunizationQues> Grp3 = new ArrayList<tblmstimmunizationQues>();
	ArrayList<tblmstimmunizationQues> Grp4 = new ArrayList<tblmstimmunizationQues>();
	ArrayList<tblmstimmunizationQues> Grp5 = new ArrayList<tblmstimmunizationQues>();
	Date c_date = new Date();
	Button imgNext, imgEnd, Previous;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.immunizationques_activity);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.titlebar);
		// setTaskBarColored(this);
		dataProvider = new DataProvider(this);
		global = (Global) getApplication();
		pagenumber = (TextView) findViewById(R.id.pagenumber);
		title = (TextView) findViewById(R.id.title);

		mViewPager = (ViewPager) findViewById(R.id.treatmentviewpager);

		Previous = (Button) findViewById(R.id.Previous);
		// imgright = (ImageView) findViewById(R.id.imgright);
		imgNext = (Button) findViewById(R.id.imgNext);
		imgEnd = (Button) findViewById(R.id.imgEnd);
		Questions = dataProvider.getAllQuestionsimm(0);
		Grp1 = dataProvider.getAllQuestionsimm(1);
		Grp2 = dataProvider.getAllQuestionsimm(2);
		Grp3 = dataProvider.getAllQuestionsimm(3);
		Grp4 = dataProvider.getAllQuestionsimm(4);
		Grp5 = dataProvider.getAllQuestionsimm(5);

		gridanc = (GridView) findViewById(R.id.gridanc);
		next = (Button) findViewById(R.id.next);

		tvtime = (TextView) gridanc.findViewById(R.id.tvtime);

		next.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("unused")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final TextView ansfield = (TextView) gridanc
						.findViewById(R.id.ansfield);
				final TextView ansQ = (TextView) gridanc.findViewById(R.id.ans);
				TextView tvQuestions = (TextView) gridanc
						.findViewById(R.id.tvQuestions);
				//System.out.print(ansQ.getText().toString());
				// String ans=ansQ.getText().toString();
				if (CurrentPage < Questions.size()) {
					prevPage = CurrentPage;
					int pageno = 0;

					if (ansfield.getText() != null
							&& ansfield.getText().toString().length() > 0
							&& !ansfield.getText().toString()
									.equalsIgnoreCase("null")) {
						if (ansQ.getText().toString() != null
								&& ansQ.getText().toString().length() > 0) {
							savedata(ansfield.getText().toString(), ansQ
									.getText().toString());
							if (ansQ.getText().toString().equalsIgnoreCase("1")
									&& tvQuestions
											.getText()
											.toString()
											.equalsIgnoreCase(
													"नवजात / शिशु / बच्चे की आयु ")) {
								for (int i = 0; i < Questions.size(); i++) {
									for (int j = 0; j < Grp1.size(); j++) {
										if (Grp1.get(j)
												.getFtext()
												.equalsIgnoreCase(
														Questions.get(i)
																.getFtext())
												&& Questions.get(i).getGrp() != 1) {
											Questions.get(i).setFtext("");
										}
									}
								}
								CurrentPage = 7;

							} else if (ansQ.getText().toString()
									.equalsIgnoreCase("2")
									&& tvQuestions
											.getText()
											.toString()
											.equalsIgnoreCase(
													"नवजात / शिशु / बच्चे की आयु ")) {
								for (int i = 0; i < Questions.size(); i++) {
									for (int j = 0; j < Grp2.size(); j++) {
										if (Grp2.get(j)
												.getFtext()
												.equalsIgnoreCase(
														Questions.get(i)
																.getFtext())
												&& Questions.get(i).getGrp() != 2) {
											Questions.get(i).setFtext("");
										}
									}
								}
								CurrentPage = 25;
							} else if (ansQ.getText().toString()
									.equalsIgnoreCase("3")
									&& tvQuestions
											.getText()
											.toString()
											.equalsIgnoreCase(
													"नवजात / शिशु / बच्चे की आयु ")) {
								for (int i = 0; i < Questions.size(); i++) {
									for (int j = 0; j < Grp3.size(); j++) {
										if (Grp3.get(j)
												.getFtext()
												.equalsIgnoreCase(
														Questions.get(i)
																.getFtext())
												&& Questions.get(i).getGrp() != 3) {
											Questions.get(i).setFtext("");
										}
									}
								}
								CurrentPage = 49;
							} else if (ansQ.getText().toString()
									.equalsIgnoreCase("4")
									&& tvQuestions
											.getText()
											.toString()
											.equalsIgnoreCase(
													"नवजात / शिशु / बच्चे की आयु ")) {

								for (int i = 0; i < Questions.size(); i++) {
									for (int j = 0; j < Grp4.size(); j++) {
										if (Grp4.get(j)
												.getFtext()
												.equalsIgnoreCase(
														Questions.get(i)
																.getFtext())
												&& Questions.get(i).getGrp() != 4) {
											Questions.get(i).setFtext("");
										}
									}
								}
								CurrentPage = 102;
							} else if (ansQ.getText().toString()
									.equalsIgnoreCase("5")
									&& tvQuestions
											.getText()
											.toString()
											.equalsIgnoreCase(
													"नवजात / शिशु / बच्चे की आयु ")) {
								for (int i = 0; i < Questions.size(); i++) {
									for (int j = 0; j < Grp5.size(); j++) {
										if (Grp5.get(j)
												.getFtext()
												.equalsIgnoreCase(
														Questions.get(i)
																.getFtext())
												&& Questions.get(i).getGrp() != 5) {
											Questions.get(i).setFtext("");
										}
									}
								}
								CurrentPage = 115;

							} else if (ansQ.getText().toString()
									.equalsIgnoreCase("2")
									|| ansQ.getText().toString()
											.equalsIgnoreCase("1")
									|| Questions.get(CurrentPage).getQ_type() == 2) {

								// if (global.getiGlobalRoleID() != 3) {
								// savedata(ansfield.getText().toString(),
								// ansQ.getText().toString());
								// }
								if (Questions.get(CurrentPage).getQ_type() == 2) {
									CurrentPage = CurrentPage + 1;
								} else {
									if (ansQ.getText().toString()
											.equalsIgnoreCase("1")
											&& Questions.get(CurrentPage)
													.getN_qid() > 0) {

										pageno = Integer.valueOf(Questions.get(
												CurrentPage).getN_qid());

										if (pageno == 999) {
											Intent i = new Intent(
													ImmunizationQuestionActivity.this,
													ImmunizationCounselling.class);
											finish();
											startActivity(i);
										}
										CurrentPage = pageno - 1;
									} else if (ansQ.getText().toString()
											.equalsIgnoreCase("2")
											&& Questions.get(CurrentPage)
													.getY_qid() > 0) {
										pageno = Integer.valueOf(Questions.get(
												CurrentPage).getY_qid());
										if (pageno == 999) {
											Intent i = new Intent(
													ImmunizationQuestionActivity.this,
													ImmunizationCounselling.class);
											finish();
											startActivity(i);
										}
										CurrentPage = pageno - 1;
									}

								}// CurrentPage = 14;
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
					Date saved_date = new Date();

					long diffInMillisec = c_date.getTime()
							- saved_date.getTime();
					long diffInSec = TimeUnit.MILLISECONDS
							.toSeconds(diffInMillisec);
					long seconds = diffInSec % 60;
					diffInSec /= 60;
					long minutes = diffInSec % 60;
					diffInSec /= 60;
					long hours = diffInSec % 24;
					diffInSec /= 24;
					long days = diffInSec;
					String diff = minutes + ":" + seconds;
					tvtime.setText(diff);
					savedata("TimeDuration", tvtime.getText().toString());
					finish();
				}
			}
		});
		Previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CurrentPage = prevPage;
				fillgrid();

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

	public void removeQues(ArrayList<tblmstimmunizationQues> Grp) {
		for (int i = 0; i < Questions.size(); i++) {
			for (int j = 0; j < Grp.size(); j++) {
				if (Grp.get(j).getFtext()
						.equalsIgnoreCase(Questions.get(i).getFtext())) {
					Questions.get(i).setFtext("");
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void savedata(String ansfield, String ansQ) {
		// TODO Auto-generated method stub

		String dtaans = ansQ;
		String sql = "";
		String flag = "";
		String ChildGUID = global.getsGlobalChildGUID();
		int AshaID = 0;
		int ANMID = 0;
		int CreatedBy=0;
		String CreatedOn=Validate.getcurrentdate();
		if (global.getsGlobalAshaCode() != null
				&& global.getsGlobalAshaCode().length() > 0) {
			AshaID = Integer.valueOf(global.getsGlobalAshaCode());
		}
		if (global.getsGlobalANMCODE() != null
				&& global.getsGlobalANMCODE().length() > 0) {
			ANMID = Integer.valueOf(global.getsGlobalANMCODE());
		}
		// int visitno = global.getVisitno();
		if(global.getsGlobalUserID()!=null && global.getsGlobalUserID().length()>0){
			CreatedBy=Integer.valueOf(global.getsGlobalUserID());
		}
		if ((global.getImmunizationGUID() != null
				&& global.getImmunizationGUID().length() > 0 && !global
				.getImmunizationGUID().equalsIgnoreCase("null"))
				&& (global.getsGlobalChildGUID() != null && global
						.getsGlobalChildGUID().length() > 0)) {
			flag = "u";

			dataProvider.saveimmunizationdata(AshaID, ANMID, dtaans, ChildGUID,
					global.getImmunizationGUID(), ansfield, flag,CreatedBy,CreatedOn);
		} else {
			String ImmunizationGUID = Validate.random();
			global.setImmunizationGUID(ImmunizationGUID);
			flag = "i";
			dataProvider.saveimmunizationdata(AshaID, ANMID, dtaans, ChildGUID,
					ImmunizationGUID, ansfield, flag,CreatedBy,CreatedOn);
		}

	}

	@SuppressWarnings("unused")
	public void fillgrid() {
		// TODO Auto-generated method stub

		if (Questions != null && Questions.size() > 0) {
			if (CurrentPage < Questions.size()) {
				if (Questions.get(CurrentPage).getFtext() != null
						&& Questions.get(CurrentPage).getFtext().length() > 0) {
					gridanc.setAdapter(new immunizationAdapterQuestion(this,
							Questions, CurrentPage));
				} else {
					CurrentPage = CurrentPage + 1;
					fillgrid();
				}

			} else {

				Date saved_date = new Date();

				long diffInMillisec = c_date.getTime() - saved_date.getTime();
				long diffInSec = TimeUnit.MILLISECONDS
						.toSeconds(diffInMillisec);
				long seconds = diffInSec % 60;
				diffInSec /= 60;
				long minutes = diffInSec % 60;
				diffInSec /= 60;
				long hours = diffInSec % 24;
				diffInSec /= 24;
				long days = diffInSec;
				String diff = minutes + ":" + seconds;

				savedata("TimeDuration", diff);
				finish();
			}
		}
	}

	@Override
	public void onBackPressed() {

		CustomAlertExit(getResources().getString(R.string.backpress));
	}

	public void CustomAlertSave(String msg) {
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

	public void CustomAlertExit(String msg) {

		try {
			final Dialog dialog = new Dialog(this);
			// hide to default title for Dialog
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// inflate the layout dialog_layout.xml and set it as contentView
			LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.backpress, null, false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setContentView(view);

			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
			TextView txtTitle = (TextView) dialog
					.findViewById(R.id.txt_alert_message);
			txtTitle.setText(String.valueOf(msg));

			Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
			Button btn_no = (Button) dialog.findViewById(R.id.btn_No);
			btn_yes.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String sql = "Delete from  tblmstimmunizationANS where ImmunizationGUID='"
							+ global.getImmunizationGUID() + "'";
					dataProvider.executeSql(sql);
					Intent i = new Intent(ImmunizationQuestionActivity.this,
							ImmunizationCounselling.class);
					finish();
					startActivity(i);
					dialog.dismiss();
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
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
