package com.microware.intrahealth;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.microware.intrahealth.object.tblmstimmunizationQues;
import com.google.gson.Gson;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.Tbl_HHSurvey;
import com.microware.intrahealth.object.tblMigration;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.tbl_DatesEd;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tblhhupdate_Log;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblmstimmunizationANS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class Synchronization extends Activity {
	String hhguid = "";
	LinearLayout Lineardownloadmaster, lineardownloadHH, lineardownloadMNCH,
			linearuploadHH, linearuploadMNCH, linearuploadFP, lineardownloadFP,
			lineardownloadncd, linearuploadncd, ll_mnch, ll_fp, ll_ncd;
	DataProvider dataProvider;
	ProgressDialog progressDialog;
	Global global;
	String downloadmsg = "Sucess";
	JSONArray MstUserArray = null;
	JSONArray MstRoleArray = null;
	JSONArray MstStateArray = null;
	JSONArray MstDistrictArray = null;
	JSONArray MstBlockArray = null;
	JSONArray MstPanchayatArray = null;
	JSONArray MstVillageArray = null;
	JSONArray MstCommonArray = null;
	JSONArray MstANMArray = null;
	JSONArray MstASHAArray = null;
	JSONArray MstSubCenterArray = null;
	JSONArray HHSurveyData = null;
	JSONArray HHSurveyFamilyData = null;
	JSONArray MigrationArray = null;

	JSONArray MstCatchmentSupervisorArray = null;
	JSONArray MstPHCArray = null;
	JSONArray MstSubCenterVillageMappingArray = null;
	JSONArray MstCatchmentAreaArray = null;

	JSONArray tblChildArray = null;
	JSONArray TblANCVisitArray = null;
	JSONArray tblpregnantwomenArray = null;

	int iDataUpload;
	public ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();
	public ArrayList<Tbl_HHSurvey> HHSurvey = new ArrayList<Tbl_HHSurvey>();
	public ArrayList<Tbl_HHSurvey> HHSurveycount = new ArrayList<Tbl_HHSurvey>();
	public ArrayList<MstASHA> MstASHA = new ArrayList<MstASHA>();

	public ArrayList<tblChild> Child = new ArrayList<tblChild>();
	public ArrayList<tbl_pregnantwomen> pregnantwomen = new ArrayList<tbl_pregnantwomen>();
	public ArrayList<TblANCVisit> ANCVisit = new ArrayList<TblANCVisit>();
	int iFPDataDownload;
	public ArrayList<tblmstFPAns> mstFPAns = new ArrayList<tblmstFPAns>();
	JSONArray tblmstFPAnsArray = null;
	public ArrayList<tblmstANCQues> mstANCQues = new ArrayList<tblmstANCQues>();
	JSONArray tblmstANCQuesArray = null;
	public ArrayList<tbl_DatesEd> tblDates = new ArrayList<tbl_DatesEd>();
	JSONArray tbl_DatesEdArray = null;
	public ArrayList<tblhhupdate_Log> tblupdate = new ArrayList<tblhhupdate_Log>();
	JSONArray tblupdateArray = null;

	public ArrayList<tblmstFPFDetail> mstFPFDetail = new ArrayList<tblmstFPFDetail>();
	JSONArray tblmstFPFDetailArray = null;
	public ArrayList<tblMigration> migration = new ArrayList<tblMigration>();
	JSONArray tblMigrationArray = null;

	JSONArray tblmstFPQuesArray = null;
	public ArrayList<tblmstFPQues> tblmstFPQues = new ArrayList<tblmstFPQues>();
	JSONArray tblmstimmunizationANSArray = null;
	public ArrayList<tblmstimmunizationANS> tblmstimmunizationANS = new ArrayList<tblmstimmunizationANS>();
	JSONArray tblPNChomevisit_ANSArray = null;
	public ArrayList<tblPNChomevisit_ANS> tblPNChomevisit_ANS = new ArrayList<tblPNChomevisit_ANS>();
	JSONArray tblmstimmunizationQuesArray = null;
	public ArrayList<tblmstimmunizationQues> tblmstimmunizationQues = new ArrayList<tblmstimmunizationQues>();
	int iDataUploadImmu;
	public ArrayList<q_bank> q_bank = new ArrayList<q_bank>();
	JSONArray q_bankArray = null;
	int iDataUploadfp;
	int iDataUploadfpans;
	int iDataUploadpncans;

	int iDataUploadMNCH;
	int iMNCHDataDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.synchronization);
		global = (Global) getApplicationContext();
		dataProvider = new DataProvider(this);

		Lineardownloadmaster = (LinearLayout) findViewById(R.id.lineardownloadmaster);
		lineardownloadHH = (LinearLayout) findViewById(R.id.lineardownloadHH);
		lineardownloadMNCH = (LinearLayout) findViewById(R.id.lineardownloadMNCH);
		linearuploadHH = (LinearLayout) findViewById(R.id.linearuploadHH);
		linearuploadMNCH = (LinearLayout) findViewById(R.id.linearuploadMNCH);
		linearuploadFP = (LinearLayout) findViewById(R.id.linearuploadFP);
		lineardownloadFP = (LinearLayout) findViewById(R.id.lineardownloadFP);
		lineardownloadncd = (LinearLayout) findViewById(R.id.lineardownloadncd);
		linearuploadncd = (LinearLayout) findViewById(R.id.linearuploadncd);
		ll_mnch = (LinearLayout) findViewById(R.id.ll_mnch);
		ll_fp = (LinearLayout) findViewById(R.id.ll_fp);
		ll_ncd = (LinearLayout) findViewById(R.id.ll_ncd);

		try {
			int statecode = 0;
			if (global.getStateCode() != null
					&& global.getStateCode().length() > 0) {

				statecode = Integer.valueOf(global.getStateCode());
			}
			if (statecode == 20) {
				ll_mnch.setVisibility(View.GONE);
				ll_fp.setVisibility(View.GONE);
				ll_ncd.setVisibility(View.VISIBLE);
			} else {
				ll_ncd.setVisibility(View.GONE);
				ll_fp.setVisibility(View.VISIBLE);
				ll_mnch.setVisibility(View.VISIBLE);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Lineardownloadmaster.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				downloadMasterdata(global.getsGlobalUserName(),
						global.getsGlobalPassword());

			}
		});
		linearuploadFP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.uploaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();

										exportFamilyPlanning();
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});
		lineardownloadFP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.downloaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										importAnswer(
												global.getsGlobalUserName(),
												global.getsGlobalPassword());
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});

		lineardownloadHH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.downloaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										downloaddata(
												global.getsGlobalUserName(),
												global.getsGlobalPassword());
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});

		lineardownloadMNCH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.downloaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();

										downloadMNCHdata(
												global.getsGlobalUserName(),
												global.getsGlobalPassword());
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});

		linearuploadHH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.uploaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();

										downloadmsg = "Sucess";
										exportHH();
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});
		linearuploadMNCH.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.uploaddata));
						builder1.setCancelable(false);
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();

										downloadmsg = "Sucess";
										exportMNCH();
									}
								});

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			}
		});

	}

	/*
	 * @Override protected void onStart() { // TODO Auto-generated method stub
	 * super.onStart();
	 * GoogleAnalytics.getInstance(Synchronization.this).reportActivityStart
	 * (this); }
	 * 
	 * 
	 * @Override protected void onStop() { // TODO Auto-generated method stub
	 * super.onStop();
	 * GoogleAnalytics.getInstance(Synchronization.this).reportActivityStop
	 * (this); }
	 */
	public void downloadMasterdata(final String sUserName,
			final String sPassword) {
		try {

			if (isNetworkconn()) {

				Synchronization.this.runOnUiThread(new Runnable() {
					public void run() {
						AlertDialog.Builder builder1 = new AlertDialog.Builder(
								Synchronization.this);
						builder1.setMessage(getResources().getString(
								R.string.downloaddata));
						builder1.setCancelable(false);

						builder1.setNegativeButton(
								getResources().getString(R.string.no),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
						builder1.setPositiveButton(
								getResources().getString(R.string.yes),
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										importmaster(sUserName, sPassword);

									}
								});

						AlertDialog alert11 = builder1.create();
						alert11.show();
					}
				});

			} else
				showNewWorkError();

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(Synchronization.this, e.getMessage().toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	public void downloaddata(String sUserName, String sPassword) {
		try {

			if (isNetworkconn()) {
				importdata(sUserName, sPassword);

			} else
				showNewWorkError();

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(Synchronization.this, e.getMessage().toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	public void downloadMNCHdata(String sUserName, String sPassword) {
		try {

			if (isNetworkconn()) {
				importMNCHdata(sUserName, sPassword);

			} else
				showNewWorkError();

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(Synchronization.this, e.getMessage().toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	private boolean isNetworkconn() {
		ConnectivityManager localConnectivityManager = (ConnectivityManager) getSystemService("connectivity");
		if ((localConnectivityManager.getActiveNetworkInfo() != null)
				&& (localConnectivityManager.getActiveNetworkInfo()
						.isAvailable())
				&& (localConnectivityManager.getActiveNetworkInfo()
						.isConnected()))

			return true;
		else
			return false;

	}

	@SuppressWarnings("deprecation")
	private void showNewWorkError() {

		AlertDialog alertDialog = new AlertDialog.Builder(Synchronization.this)
				.create();
		alertDialog.setTitle("mSakhi");
		alertDialog.setMessage("No Network access.");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		alertDialog.show();
		// will close the app if the device does't have camera

	}

	public void importmaster(final String sUserName, final String sPassword) {

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.DataLoading));
		new Thread() {

			@Override
			public void run() {

				try {

					importMasterdata(sUserName, sPassword);
					importQuestion(sUserName, sPassword);

				} catch (Exception exp) {
					downloadmsg = exp.getMessage();
					progressDialog.dismiss();
				}

				progressDialog.dismiss();

			}

		}.start();

	}

	public void importAnswer(final String sUserName, final String sPassword) {

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.DataLoading));
		new Thread() {

			@Override
			public void run() {
				try {
					// importAns(sUserName, sPassword);ll
					if (global.getiGlobalRoleID() == 3) {
						MstASHA = dataProvider.getMstASHAname(1);
						if ((MstASHA != null && MstASHA.size() > 0)) {
							for (int i = 0; i < MstASHA.size(); i++) {

								importtblmstFPAns(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
								importtblmstFPFDetail(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));

								importtblmstimmunizationANS(sUserName,
										sPassword, String.valueOf(MstASHA
												.get(i).getASHAID()));
								importtblPNChomevisit_ANS(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
							}

						}

					} else {

						// importSurveydata(sUserName, sPassword);
						importtblmstFPAns(sUserName, sPassword, "0");
						importtblmstFPFDetail(sUserName, sPassword, "0");

						importtblmstimmunizationANS(sUserName, sPassword, "0");
						importtblPNChomevisit_ANS(sUserName, sPassword, "0");

					}

				} catch (Exception exp) {
					progressDialog.dismiss();
				}

				progressDialog.dismiss();

			}

		}.start();

	}

	public void importdata(final String sUserName, final String sPassword) {

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.DataLoading));
		new Thread() {

			@Override
			public void run() {
				try {
					if (global.getiGlobalRoleID() == 3) {
						MstASHA = dataProvider.getMstASHAname(1);
						if ((MstASHA != null && MstASHA.size() > 0)) {
							for (int i = 0; i < MstASHA.size(); i++) {
								// importSurveydata1(sUserName, sPassword,
								// String.valueOf(MstASHA.get(i)
								// .getASHAID()));
								importtblHHSurvey(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
								importtblHHFamilyMember(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));

								importtblMigration(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
							}

						}

					} else {

						// importSurveydata(sUserName, sPassword);
						importtblHHSurvey(sUserName, sPassword, "0");
						importtblHHFamilyMember(sUserName, sPassword, "0");

						importtblMigration(sUserName, sPassword, "0");

					}

				} catch (Exception exp) {
					downloadmsg = exp.getMessage();
					progressDialog.dismiss();
				}

				progressDialog.dismiss();

			}

		}.start();

	}

	public void importMNCHdata(final String sUserName, final String sPassword) {

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.DataLoading));
		new Thread() {

			@Override
			public void run() {
				try {

					// importMNCHSurveydata(sUserName, sPassword);
					if (global.getiGlobalRoleID() == 3) {
						MstASHA = dataProvider.getMstASHAname(1);
						if ((MstASHA != null && MstASHA.size() > 0)) {
							for (int i = 0; i < MstASHA.size(); i++) {

								importtblPregnant_woman(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
								importtblChild(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));

								importtblANCVisit(sUserName, sPassword,
										String.valueOf(MstASHA.get(i)
												.getASHAID()));
							}

						}

					} else {

						// importSurveydata(sUserName, sPassword);
						importtblPregnant_woman(sUserName, sPassword, "0");
						importtblChild(sUserName, sPassword, "0");

						importtblANCVisit(sUserName, sPassword, "0");

					}

				} catch (Exception exp) {
					progressDialog.dismiss();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (iMNCHDataDownload == 1) {
							Toast.makeText(Synchronization.this,
									"Data download successfully",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(Synchronization.this,
									"Data downloading failed",
									Toast.LENGTH_LONG).show();
						}
					}
				});

				progressDialog.dismiss();

			}

		}.start();

	}

	@SuppressWarnings("unused")
	public void importMasterdata(String UserName, String Password) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = GlobalString.METHOD_NAME_DOWNLOAD_MASTER;

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "Master");

		request.addProperty("AuthenticationToken", UserName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			ClearMasterData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			MstUserArray = jsonObj.getJSONArray("aspnet_Users");
			for (int i = 0; i < MstUserArray.length(); i++) {
				JSONObject user = MstUserArray.getJSONObject(i);
				int iUserID = 0;
				int iRoleID = 1;
				String sPassword = null;
				String sUserName = null;
				int iIsDeleted = 0;
				if (user.getString("UserUID") != null
						&& user.getString("UserUID").length() > 0
						&& !user.getString("UserUID").equalsIgnoreCase("null")) {
					iUserID = Integer.valueOf(user.getInt("UserUID"));
				}
				if (user.getString("RoleID") != null
						&& user.getString("RoleID").length() > 0
						&& !user.getString("RoleID").equalsIgnoreCase("null")) {
					iRoleID = Integer.valueOf(user.getInt("RoleID"));
				}
				sUserName = user.getString("UserName");
				sPassword = user.getString("Password");

				String sql = "";
				sql = "Insert into MstUser(UserID,UserName,RoleID,Password,IsDeleted)values("
						+ iUserID
						+ ",'"
						+ sUserName
						+ "',"
						+ iRoleID
						+ ",'"
						+ sPassword + "'," + iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstRoleArray = jsonObj.getJSONArray("aspnet_Role");
			for (int i = 0; i < MstRoleArray.length(); i++) {
				JSONObject Role = MstRoleArray.getJSONObject(i);
				int iRoleID = 0;
				String sRole = "";
				int iIsDeleted = 0;
				if (Role.getString("RoleID") != null
						&& Role.getString("RoleID").length() > 0
						&& !Role.getString("RoleID").equalsIgnoreCase("null")) {
					iRoleID = Integer.valueOf(Role.getInt("RoleID"));
				}
				sRole = Role.getString("RoleName");
				String sql = "";
				sql = "Insert into MstRole(RoleID,Role,IsDeleted)values("
						+ iRoleID + ",'" + sRole + "'," + iIsDeleted + ")";
				dataProvider.executeSql(sql);
			}

			MstStateArray = jsonObj.getJSONArray("Mststate");
			for (int i = 0; i < MstStateArray.length(); i++) {
				JSONObject state = MstStateArray.getJSONObject(i);
				int iStateID = 0;
				String sStateCode = "";
				String sState = "";
				int iLanguageID = 1;
				int iIsDeleted = 0;
				if (state.getString("StateID") != null
						&& state.getString("StateID").length() > 0
						&& !state.getString("StateID").equalsIgnoreCase("null")) {
					iStateID = Integer.valueOf(state.getInt("StateID"));
				}
				sStateCode = state.getString("StateCode");

				sState = state.getString("StateName");
				if (state.getString("LanguageID") != null
						&& state.getString("LanguageID").length() > 0
						&& !state.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer.valueOf(state.getInt("LanguageID"));
				}
				String sql = "";
				sql = "Insert into Mststate(StateID,StateCode,StateName,LanguageID,IsDeleted)values("
						+ iStateID
						+ ",'"
						+ sStateCode
						+ "','"
						+ sState
						+ "',"
						+ iLanguageID + "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);
			}
			MstDistrictArray = jsonObj.getJSONArray("MSTDistrict");
			for (int i = 0; i < MstDistrictArray.length(); i++) {
				JSONObject district = MstDistrictArray.getJSONObject(i);
				int iDistrictID = 0;
				String sStateCode = "";
				String sDistrictCode = "";
				String sDistrict = "";
				int iLanguageID = 0;
				int iIsDeleted = 0;
				if (district.getString("DistrictID") != null
						&& district.getString("DistrictID").length() > 0
						&& !district.getString("DistrictID").equalsIgnoreCase(
								"null")) {
					iDistrictID = Integer
							.valueOf(district.getInt("DistrictID"));
				}
				sStateCode = district.getString("StateCode");
				sDistrictCode = district.getString("DistrictCode");
				sDistrict = district.getString("DistrictName");
				if (district.getString("LanguageID") != null
						&& district.getString("LanguageID").length() > 0
						&& !district.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer
							.valueOf(district.getInt("LanguageID"));
				}

				String sql = "";
				sql = "Insert into MSTDistrict(DistrictID,StateCode,DistrictCode,DistrictName,LanguageID,IsDeleted)values("
						+ iDistrictID
						+ ",'"
						+ sStateCode
						+ "','"
						+ sDistrictCode
						+ "','"
						+ sDistrict
						+ "',"
						+ iLanguageID + "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);
			}

			MstBlockArray = jsonObj.getJSONArray("MSTBlock");
			for (int i = 0; i < MstBlockArray.length(); i++) {
				JSONObject block = MstBlockArray.getJSONObject(i);
				int iBlockID = 0;
				String sStateCode = "";
				String sDistrictCode = "";
				String sBlockCode = "";
				String sBlock = null;
				int iLanguageID = 0;
				int iIsDeleted = 0;

				if (block.getString("BlockID") != null
						&& block.getString("BlockID").length() > 0
						&& !block.getString("BlockID").equalsIgnoreCase("null")) {
					iBlockID = Integer.valueOf(block.getInt("BlockID"));
				}

				sStateCode = block.getString("StateCode");
				sDistrictCode = block.getString("DistrictCode");
				sBlockCode = block.getString("BlockCode");
				sBlock = block.getString("BlockName");
				if (block.getString("LanguageID") != null
						&& block.getString("LanguageID").length() > 0
						&& !block.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer.valueOf(block.getInt("LanguageID"));
				}

				String sql = "";
				sql = "Insert into MSTBlock(BlockID,StateCode,DistrictCode,BlockCode,BlockName,LanguageID,IsDeleted)values("
						+ iBlockID
						+ ",'"
						+ sStateCode
						+ "','"
						+ sDistrictCode
						+ "','"
						+ sBlockCode
						+ "','"
						+ sBlock
						+ "',"
						+ iLanguageID + "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstPanchayatArray = jsonObj.getJSONArray("MstPanchayat");
			for (int i = 0; i < MstPanchayatArray.length(); i++) {
				JSONObject panchayat = MstPanchayatArray.getJSONObject(i);
				int iPanchayatID = 0;
				String sStateCode = "";
				String sDistrictCode = "";
				String sBlockCode = "";
				String sPanchayatCode = "";
				String sPanchayat = null;
				int iLanguageID = 0;
				int iIsDeleted = 0;

				if (panchayat.getString("PanchayatID") != null
						&& panchayat.getString("PanchayatID").length() > 0
						&& !panchayat.getString("PanchayatID")
								.equalsIgnoreCase("null")) {
					iPanchayatID = Integer.valueOf(panchayat
							.getInt("PanchayatID"));
				}

				sStateCode = panchayat.getString("StateCode");
				sDistrictCode = panchayat.getString("DistrictCode");
				sBlockCode = panchayat.getString("BlockCode");
				sPanchayatCode = panchayat.getString("PanchayatCode");
				sPanchayat = panchayat.getString("PanchayatName");
				if (panchayat.getString("LanguageID") != null
						&& panchayat.getString("LanguageID").length() > 0
						&& !panchayat.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer.valueOf(panchayat
							.getInt("LanguageID"));
				}

				String sql = "";
				sql = "Insert into MstPanchayat(PanchayatID,StateCode,DistrictCode,BlockCode,PanchayatCode,PanchayatName,LanguageID,IsDeleted)values("
						+ iPanchayatID
						+ ",'"
						+ sStateCode
						+ "','"
						+ sDistrictCode
						+ "','"
						+ sBlockCode
						+ "','"
						+ sPanchayatCode
						+ "','"
						+ sPanchayat
						+ "',"
						+ iLanguageID + "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstVillageArray = jsonObj.getJSONArray("MstVillage");
			for (int i = 0; i < MstVillageArray.length(); i++) {
				JSONObject village = MstVillageArray.getJSONObject(i);
				int iVillageID = 0;
				String sStateCode = "";
				String sDistrictCode = "";
				String sBlockCode = "";
				String sPanchayatCode = "";
				String sVillageCode = "";
				String sVillage = "";
				int iLanguageID = 0;
				int iIsDeleted = 0;
				if (village.getString("VillageID") != null
						&& village.getString("VillageID").length() > 0
						&& !village.getString("VillageID").equalsIgnoreCase(
								"null")) {
					iVillageID = Integer.valueOf(village.getInt("VillageID"));
				}
				sStateCode = village.getString("StateCode");
				sDistrictCode = village.getString("DistrictCode");
				sBlockCode = village.getString("BlockCode");
				sPanchayatCode = village.getString("PanchayatCode");
				sVillageCode = village.getString("VillageCode");
				sVillage = village.getString("VillageName");
				if (village.getString("LanguageID") != null
						&& village.getString("LanguageID").length() > 0
						&& !village.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer.valueOf(village.getInt("LanguageID"));
				}

				String sql = "";
				sql = "Insert into MstVillage(VillageID,StateCode,DistrictCode,BlockCode,PanchayatCode,VillageCode,VillageName,LanguageID,IsDeleted)values("
						+ iVillageID
						+ ",'"
						+ sStateCode
						+ "','"
						+ sDistrictCode
						+ "','"
						+ sBlockCode
						+ "','"
						+ sPanchayatCode
						+ "','"
						+ sVillageCode
						+ "','"
						+ sVillage
						+ "',"
						+ iLanguageID
						+ ","
						+ iIsDeleted
						+ ")";
				dataProvider.executeSql(sql);
			}

			MstCommonArray = jsonObj.getJSONArray("MstCommon");
			for (int i = 0; i < MstCommonArray.length(); i++) {
				JSONObject MstCommon = MstCommonArray.getJSONObject(i);
				int iUID = 0;
				int iID = 0;
				String sValue = "";
				String sValueCode = "";
				int iLanguageID = 0;
				int iFlag = 0;
				int iIsDeleted = 0;
				if (MstCommon.getString("UID") != null
						&& MstCommon.getString("UID").length() > 0
						&& !MstCommon.getString("UID").equalsIgnoreCase("null")) {
					iUID = Integer.valueOf(MstCommon.getInt("UID"));
				}
				if (MstCommon.getString("ID") != null
						&& MstCommon.getString("ID").length() > 0
						&& !MstCommon.getString("ID").equalsIgnoreCase("null")) {
					iID = Integer.valueOf(MstCommon.getInt("ID"));
				}
				sValue = MstCommon.getString("Value");
				sValueCode = MstCommon.getString("ValueCode");
				if (MstCommon.getString("LanguageID") != null
						&& MstCommon.getString("LanguageID").length() > 0
						&& !MstCommon.getString("LanguageID").equalsIgnoreCase(
								"null")) {
					iLanguageID = Integer.valueOf(MstCommon
							.getInt("LanguageID"));
				}
				if (MstCommon.getString("Flag") != null
						&& MstCommon.getString("Flag").length() > 0
						&& !MstCommon.getString("Flag")
								.equalsIgnoreCase("null")) {
					iFlag = Integer.valueOf(MstCommon.getInt("Flag"));
				}

				String sql = "";
				sql = "Insert into MstCommon(UID,ID,Value,ValueCode,LanguageID,Flag,IsDeleted)values("
						+ iUID
						+ ","
						+ iID
						+ ",'"
						+ sValue
						+ "','"
						+ sValueCode
						+ "',"
						+ iLanguageID
						+ ","
						+ iFlag
						+ ","
						+ iIsDeleted
						+ ")";
				dataProvider.executeSql(sql);
			}

			MstANMArray = jsonObj.getJSONArray("MstANM");
			for (int i = 0; i < MstANMArray.length(); i++) {
				JSONObject mothertongue = MstANMArray.getJSONObject(i);
				int iANMID = 0;
				String sANMCode = null;
				String sANMName = null;
				int iIsDeleted = 0;
				int iLanguageID = 0;
				iANMID = Integer.valueOf(mothertongue.getInt("ANMID"));
				sANMCode = mothertongue.getString("ANMCode");
				sANMName = mothertongue.getString("ANMName");
				if (mothertongue.getString("LanguageID") != null
						&& mothertongue.getString("LanguageID").length() > 0
						&& !mothertongue.getString("LanguageID")
								.equalsIgnoreCase("null")) {
					iLanguageID = Integer.valueOf(mothertongue
							.getInt("LanguageID"));
				}
				String sql = "";
				sql = "Insert into MstANM(ANMID,ANMCode,ANMName,LanguageID,IsDeleted)values("
						+ iANMID
						+ ",'"
						+ sANMCode
						+ "','"
						+ sANMName
						+ "',"
						+ iLanguageID + "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstASHAArray = jsonObj.getJSONArray("MstASHA");
			for (int i = 0; i < MstASHAArray.length(); i++) {
				JSONObject mothertongue = MstASHAArray.getJSONObject(i);
				int iASHAID = 0;
				String sANMCode = "";
				String sASHACode = "";
				String sASHAName = "";
				int iLanguageID = 0;
				int iIsDeleted = 0;
				int iCHS_ID = 0;

				iASHAID = Integer.valueOf(mothertongue.getInt("ASHAID"));
				sANMCode = mothertongue.getString("ANMCode");
				sASHACode = mothertongue.getString("ASHACode");
				sASHAName = mothertongue.getString("ASHAName");
				if (mothertongue.getString("LanguageID") != null
						&& mothertongue.getString("LanguageID").length() > 0
						&& !mothertongue.getString("LanguageID")
								.equalsIgnoreCase("null")) {
					iLanguageID = Integer.valueOf(mothertongue
							.getInt("LanguageID"));
				}
				iCHS_ID = Integer.valueOf(mothertongue.getInt("CHS_ID"));
				String sql = "";
				sql = "Insert into MstASHA(ASHAID,ANMCode,ASHACode,ASHAName,LanguageID,CHS_ID,IsDeleted)values("
						+ iASHAID
						+ ",'"
						+ sANMCode
						+ "','"
						+ sASHACode
						+ "','"
						+ sASHAName
						+ "',"
						+ iLanguageID
						+ ","
						+ iCHS_ID
						+ ","
						+ iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstSubCenterArray = jsonObj.getJSONArray("MstSubcenter");
			for (int i = 0; i < MstSubCenterArray.length(); i++) {
				JSONObject mothertongue = MstSubCenterArray.getJSONObject(i);
				int iSubCenterID = 0;
				String sSubCenterCode = null;
				String sSubCenterName = null;
				int iLanguageID = 0;
				int iIsDeleted = 0;
				int iPHC_id = 0;
				iSubCenterID = Integer.valueOf(mothertongue
						.getInt("SubCenterID"));
				sSubCenterCode = mothertongue.getString("SubCenterCode");
				sSubCenterName = mothertongue.getString("SubCenterName");
				if (mothertongue.getString("LanguageID") != null
						&& mothertongue.getString("LanguageID").length() > 0
						&& !mothertongue.getString("LanguageID")
								.equalsIgnoreCase("null")) {
					iLanguageID = Integer.valueOf(mothertongue
							.getInt("LanguageID"));
				}
				iPHC_id = Integer.valueOf(mothertongue.getInt("PHC_id"));
				String sql = "";
				sql = "Insert into MstSubCenter(SubCenterID,SubCenterCode,SubCenterName,LanguageID,PHC_id,IsDeleted)values("
						+ iSubCenterID
						+ ",'"
						+ sSubCenterCode
						+ "','"
						+ sSubCenterName
						+ "',"
						+ iLanguageID
						+ ","
						+ iPHC_id
						+ "," + iIsDeleted + ")";
				dataProvider.executeSql(sql);

			}
			MstCatchmentSupervisorArray = jsonObj
					.getJSONArray("MstCatchmentSupervisor");
			for (int i = 0; i < MstCatchmentSupervisorArray.length(); i++) {
				JSONObject mothertongue = MstCatchmentSupervisorArray
						.getJSONObject(i);

				int LanguageID = 0;
				int CHS_ID = 0;
				int SubCenterID = 0;
				String SupervisorCode = "";
				String SupervisorName = "";
				int IsDeleted = 0;

				LanguageID = Integer.valueOf(mothertongue.getInt("LanguageID"));
				CHS_ID = Integer.valueOf(mothertongue.getInt("CHS_ID"));
				SubCenterID = Integer.valueOf(mothertongue
						.getInt("SubCenterID"));
				SupervisorCode = mothertongue.getString("SupervisorCode");
				SupervisorName = mothertongue.getString("SupervisorName");
				IsDeleted = Integer.valueOf(mothertongue.getInt("IsDeleted"));

				String sql = "";
				sql = "Insert into MstCatchmentSupervisor(LanguageID,CHS_ID,SubCenterID,SupervisorCode,SupervisorName,IsDeleted)values("
						+ LanguageID
						+ ","
						+ CHS_ID
						+ ","
						+ SubCenterID
						+ ",'"
						+ SupervisorCode
						+ "','"
						+ SupervisorName
						+ "',"
						+ IsDeleted + ")";
				dataProvider.executeSql(sql);

			}

			MstPHCArray = jsonObj.getJSONArray("MstPHC");
			for (int i = 0; i < MstPHCArray.length(); i++) {
				JSONObject mothertongue = MstPHCArray.getJSONObject(i);
				int PHC_id = 0;
				int Block_id = 0;
				String PHC_Code = null;
				String PHC_Name = null;
				int LanguageID = 0;
				int IsDeleted = 0;

				if (mothertongue.getString("PHC_id") != null
						&& mothertongue.getString("PHC_id").length() > 0
						&& !mothertongue.getString("PHC_id").equalsIgnoreCase(
								"null")) {
					PHC_id = Integer.valueOf(mothertongue.getInt("PHC_id"));
				}
				if (mothertongue.getString("Block_id") != null
						&& mothertongue.getString("Block_id").length() > 0
						&& !mothertongue.getString("Block_id")
								.equalsIgnoreCase("null")) {
					Block_id = Integer.valueOf(mothertongue.getInt("Block_id"));
				}
				PHC_Code = mothertongue.getString("PHC_Code");
				PHC_Name = mothertongue.getString("PHC_Name");
				if (mothertongue.getString("LanguageID") != null
						&& mothertongue.getString("LanguageID").length() > 0
						&& !mothertongue.getString("LanguageID")
								.equalsIgnoreCase("null")) {
					LanguageID = Integer.valueOf(mothertongue
							.getInt("LanguageID"));

				}
				String sql = "";
				sql = "Insert into MstPHC(PHC_id,Block_id,PHC_Code,PHC_Name,LanguageID,IsDeleted)values("
						+ PHC_id
						+ ","
						+ Block_id
						+ ",'"
						+ PHC_Code
						+ "','"
						+ PHC_Name + "'," + LanguageID + "," + IsDeleted + ")";
				dataProvider.executeSql(sql);
			}

			MstSubCenterVillageMappingArray = jsonObj
					.getJSONArray("MstSubCenterVillageMapping");
			for (int i = 0; i < MstSubCenterVillageMappingArray.length(); i++) {
				JSONObject mothertongue = MstSubCenterVillageMappingArray
						.getJSONObject(i);
				int SCVillageId = 0;
				int SubCenterID = 0;
				int VillageID = 0;

				if (mothertongue.getString("SCVillageId") != null
						&& mothertongue.getString("SCVillageId").length() > 0
						&& !mothertongue.getString("SCVillageId")
								.equalsIgnoreCase("null")) {
					SCVillageId = Integer.valueOf(mothertongue
							.getInt("SCVillageId"));
				}
				if (mothertongue.getString("SubCenterID") != null
						&& mothertongue.getString("SubCenterID").length() > 0
						&& !mothertongue.getString("SubCenterID")
								.equalsIgnoreCase("null")) {
					SubCenterID = Integer.valueOf(mothertongue
							.getInt("SubCenterID"));
				}
				if (mothertongue.getString("VillageID") != null
						&& mothertongue.getString("VillageID").length() > 0
						&& !mothertongue.getString("VillageID")
								.equalsIgnoreCase("null")) {
					VillageID = Integer.valueOf(mothertongue
							.getInt("VillageID"));

				}
				String sql = "";
				sql = "Insert into MstSubCenterVillageMapping(SCVillageId,SubCenterID,VillageID)values("
						+ SCVillageId
						+ ","
						+ SubCenterID
						+ ","
						+ VillageID
						+ ")";
				dataProvider.executeSql(sql);
			}

			// MstVersionArray = jsonObj.getJSONArray("MstVersion");
			// for (int i = 0; i < MstVersionArray.length(); i++) {
			// JSONObject mothertongue = MstVersionArray.getJSONObject(i);
			// int ApkVersionID = 0;
			// String Version = null;
			// String VersionCode = null;
			// int IsActive = 0;
			// int IsDeleted = 0;
			// if (mothertongue.getString("ApkVersionID") != null
			// && mothertongue.getString("ApkVersionID").length() > 0
			// && !mothertongue.getString("ApkVersionID")
			// .equalsIgnoreCase("null")) {
			// ApkVersionID = Integer.valueOf(mothertongue
			// .getInt("ApkVersionID"));
			// }
			// Version = mothertongue.getString("Version");
			// VersionCode = mothertongue.getString("VersionCode");
			// if (mothertongue.getString("IsActive") != null
			// && mothertongue.getString("IsActive").length() > 0
			// && !mothertongue.getString("IsActive")
			// .equalsIgnoreCase("null")) {
			// IsActive = Integer.valueOf(mothertongue
			// .getInt("IsActive"));
			// }
			//
			//
			//
			// String sql = "";
			// sql =
			// "Insert into MstVersion(SCVillageId,SubCenterID,VillageID)values("
			// + ApkVersionID
			// + ","
			// + Version
			// + ","
			// + VersionCode
			// + ","
			// + IsActive
			// + ","
			// + IsDeleted
			// + ")";
			// dataProvider.executeSql(sql);
			// }
			//
			// MstVersionTrackArray = jsonObj.getJSONArray("MstVersionTrack");
			// for (int i = 0; i < MstVersionTrackArray.length(); i++) {
			// JSONObject mothertongue = MstVersionTrackArray.getJSONObject(i);
			// int VersionTrackId = 0;
			// int ApkVersionID = 0;
			// int UserID = 0;
			// String LastSync = null;
			//
			// if (mothertongue.getString("VersionTrackId") != null
			// && mothertongue.getString("VersionTrackId").length() > 0
			// && !mothertongue.getString("VersionTrackId")
			// .equalsIgnoreCase("null")) {
			// VersionTrackId = Integer.valueOf(mothertongue
			// .getInt("VersionTrackId"));
			// }
			// if (mothertongue.getString("ApkVersionID") != null
			// && mothertongue.getString("ApkVersionID").length() > 0
			// && !mothertongue.getString("ApkVersionID")
			// .equalsIgnoreCase("null")) {
			// ApkVersionID = Integer.valueOf(mothertongue
			// .getInt("ApkVersionID"));
			// }
			// if (mothertongue.getString("UserID") != null
			// && mothertongue.getString("UserID").length() > 0
			// && !mothertongue.getString("UserID")
			// .equalsIgnoreCase("null")) {
			// UserID = Integer.valueOf(mothertongue
			// .getInt("UserID"));
			// }
			// LastSync = mothertongue.getString("LastSync");
			// String sql = "";
			// sql =
			// "Insert into MstVersionTrack(VersionTrackId,ApkVersionID,UserID,LastSync)values("
			// + VersionTrackId
			// + ","
			// + ApkVersionID
			// + ","
			// + UserID
			// + ",'"
			// + LastSync
			// + "')";
			// dataProvider.executeSql(sql);
			// }

			MstCatchmentAreaArray = jsonObj.getJSONArray("MstCatchmentArea");
			for (int i = 0; i < MstCatchmentAreaArray.length(); i++) {
				JSONObject mothertongue = MstCatchmentAreaArray
						.getJSONObject(i);
				int ChAreaID = 0;
				String StateCode = null;
				String DistrictCode = null;
				String BlockCode = null;
				String VillageCode = null;
				String ChAreaCode = null;
				String ChAreaName = null;
				int LanguageID = 0;
				int IsDeleted = 0;

				if (mothertongue.getString("ChAreaID") != null
						&& mothertongue.getString("ChAreaID").length() > 0
						&& !mothertongue.getString("ChAreaID")
								.equalsIgnoreCase("null")) {
					ChAreaID = Integer.valueOf(mothertongue.getInt("ChAreaID"));
				}
				StateCode = mothertongue.getString("StateCode");
				DistrictCode = mothertongue.getString("DistrictCode");
				BlockCode = mothertongue.getString("BlockCode");
				VillageCode = mothertongue.getString("VillageCode");
				ChAreaCode = mothertongue.getString("ChAreaCode");
				ChAreaName = mothertongue.getString("ChAreaName");
				if (mothertongue.getString("LanguageID") != null
						&& mothertongue.getString("LanguageID").length() > 0
						&& !mothertongue.getString("LanguageID")
								.equalsIgnoreCase("null")) {
					LanguageID = Integer.valueOf(mothertongue
							.getInt("LanguageID"));
				}

				String sql = "";
				sql = "Insert into MstCatchmentArea(ChAreaID,StateCode,DistrictCode,BlockCode,VillageCode,ChAreaCode,ChAreaName,LanguageID,IsDeleted)values("
						+ ChAreaID
						+ ",'"
						+ StateCode
						+ "','"
						+ DistrictCode
						+ "','"
						+ BlockCode
						+ "','"
						+ VillageCode
						+ "','"
						+ ChAreaCode
						+ "','"
						+ ChAreaName
						+ "',"
						+ LanguageID
						+ "," + IsDeleted + ")";
				dataProvider.executeSql(sql);
			}

			// iDownloadMaster = 1;
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	@SuppressWarnings("unused")
	public void importSurveydata(String UserName, String Password) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = GlobalString.METHOD_NAME_DOWNLOAD_MASTER;

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "Data");

		request.addProperty("AuthenticationToken", UserName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 500000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearSurveyData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			HHSurveyData = jsonObj.getJSONArray("tblHHSurvey");
			for (int i = 0; i < HHSurveyData.length(); i++) {
				JSONObject SurveyData = HHSurveyData.getJSONObject(i);
				String HHSurveyGUID = "";
				int SubCenterID = 0;
				int ANMID = 0;
				int VillageID = 0;
				int ASHAID = 0;
				int FamilyCode = 0;
				int CasteID = 0;
				int FinancialStatusID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int HHStatusID = 0;
				String HHCode = "";
				int Verified = 0;
				int CHS_ID = 0;
				int ChAreaID = 0;
				String Latitude = "";
				String Longitude = "";
				String Location = "";
				String MigrateInDate = "";
				String MigrateOutDate = "";
				HHCode = SurveyData.getString("HHCode");
				HHSurveyGUID = SurveyData.getString("HHSurveyGUID");
				MigrateInDate = SurveyData.getString("MigrateInDate");
				MigrateOutDate = SurveyData.getString("MigrateOutDate");

				// if (SurveyData.getString("Latitude") != null
				// && SurveyData.getString("Latitude").length() > 0
				// && !SurveyData.getString("Latitude")
				// .equalsIgnoreCase("null")) {
				// Latitude = SurveyData.getString("Latitude");
				// }
				if (SurveyData.getString("ChAreaID") != null
						&& SurveyData.getString("ChAreaID").length() > 0
						&& !SurveyData.getString("ChAreaID").equalsIgnoreCase(
								"null")) {
					ChAreaID = Integer.valueOf(SurveyData.getInt("ChAreaID"));
				}
				// if (SurveyData.getString("Longitude") != null
				// && SurveyData.getString("Longitude").length() > 0
				// && !SurveyData.getString("Longitude")
				// .equalsIgnoreCase("null")) {
				// Longitude = SurveyData.getString("Longitude");
				// }
				// if (SurveyData.getString("Location") != null
				// && SurveyData.getString("Location").length() > 0
				// && !SurveyData.getString("Location")
				// .equalsIgnoreCase("null")) {
				// Location =SurveyData.getString("Location");
				// }
				if (SurveyData.getString("SubCenterID") != null
						&& SurveyData.getString("SubCenterID").length() > 0
						&& !SurveyData.getString("SubCenterID")
								.equalsIgnoreCase("null")) {
					SubCenterID = Integer.valueOf(SurveyData
							.getInt("SubCenterID"));
				}

				if (SurveyData.getString("ANMID") != null
						&& SurveyData.getString("ANMID").length() > 0
						&& !SurveyData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(SurveyData.getInt("ANMID"));
				}

				if (SurveyData.getString("VillageID") != null
						&& SurveyData.getString("VillageID").length() > 0
						&& !SurveyData.getString("VillageID").equalsIgnoreCase(
								"null")) {
					VillageID = Integer.valueOf(SurveyData.getInt("VillageID"));
				}

				if (SurveyData.getString("ServiceProviderID") != null
						&& SurveyData.getString("ServiceProviderID").length() > 0
						&& !SurveyData.getString("ServiceProviderID")
								.equalsIgnoreCase("null")) {
					ASHAID = Integer.valueOf(SurveyData
							.getInt("ServiceProviderID"));
				}
				if (SurveyData.getString("FamilyCode") != null
						&& SurveyData.getString("FamilyCode").length() > 0
						&& !SurveyData.getString("FamilyCode")
								.equalsIgnoreCase("null")) {
					FamilyCode = Integer.valueOf(SurveyData
							.getInt("FamilyCode"));
				}

				if (SurveyData.getString("CasteID") != null
						&& SurveyData.getString("CasteID").length() > 0
						&& !SurveyData.getString("CasteID").equalsIgnoreCase(
								"null")) {
					CasteID = Integer.valueOf(SurveyData.getInt("CasteID"));
				}
				// if (SurveyData.getString("CHS_ID") != null
				// && SurveyData.getString("CHS_ID").length() > 0
				// && !SurveyData.getString("CHS_ID").equalsIgnoreCase(
				// "null")) {
				// CHS_ID = Integer.valueOf(SurveyData.getInt("CHS_ID"));
				// }

				if (SurveyData.getString("FinancialStatusID") != null
						&& SurveyData.getString("FinancialStatusID").length() > 0
						&& !SurveyData.getString("FinancialStatusID")
								.equalsIgnoreCase("null")) {
					FinancialStatusID = Integer.valueOf(SurveyData
							.getInt("FinancialStatusID"));
				}

				if (SurveyData.getString("CreatedBy") != null
						&& SurveyData.getString("CreatedBy").length() > 0
						&& !SurveyData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(SurveyData.getInt("CreatedBy"));
				}
				CreatedOn = SurveyData.getString("CreatedOn");

				if (SurveyData.getString("UploadedBy") != null
						&& SurveyData.getString("UploadedBy").length() > 0
						&& !SurveyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyData.getString("UploadedOn");
				if (SurveyData.getString("HHStatusID") != null
						&& SurveyData.getString("HHStatusID").length() > 0
						&& !SurveyData.getString("HHStatusID")
								.equalsIgnoreCase("null")) {
					HHStatusID = Integer.valueOf(SurveyData
							.getInt("HHStatusID"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("IsDeleted") != null
						&& SurveyData.getString("IsDeleted").length() > 0
						&& !SurveyData.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(SurveyData.getInt("IsDeleted"));
				}
				if (SurveyData.getString("IsTablet") != null
						&& SurveyData.getString("IsTablet").length() > 0
						&& !SurveyData.getString("IsTablet").equalsIgnoreCase(
								"null")) {
					IsTablet = Integer.valueOf(SurveyData.getInt("IsTablet"));
				}
				// if (SurveyData.getString("Latitude") != null
				// && SurveyData.getString("Latitude").length() > 0
				// && !SurveyData.getString("Latitude")
				// .equalsIgnoreCase("null")) {
				// Latitude = SurveyData.getString("Latitude");
				// }
				// if (SurveyData.getString("Longitude") != null
				// && SurveyData.getString("Longitude").length() > 0
				// && !SurveyData.getString("Longitude")
				// .equalsIgnoreCase("null")) {
				// Longitude = SurveyData.getString("Longitude");
				// }
				//
				// if (SurveyData.getString("Location") != null
				// && SurveyData.getString("Location").length() > 0
				// && !SurveyData.getString("Location")
				// .equalsIgnoreCase("null")) {
				// Location = SurveyData.getString("Location");
				// }
				String sqlcount = "select count(*) from Tbl_HHSurvey where  HHSurveyGUID='"
						+ HHSurveyGUID + "' ";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHSurvey where HHSurveyGUID='"
						+ HHSurveyGUID + "' and  IsEdited= 1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHSurvey(Latitude,Longitude,Location,MigrateOutDate,MigrateInDate,ChAreaID,CHS_ID,HHSurveyGUID,SubCenterID,ANMID,VillageID,ServiceProviderID,FamilyCode,CasteID,FinancialStatusID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,HHStatusID,HHCode,Verified)values('"
							+ Latitude
							+ "','"
							+ Longitude
							+ "','"
							+ Location
							+ "','"
							+ MigrateOutDate
							+ "','"
							+ MigrateInDate
							+ "',"
							+ ChAreaID
							+ ","
							+ CHS_ID
							+ ",'"
							+ HHSurveyGUID
							+ "',"
							+ SubCenterID
							+ ","
							+ ANMID
							+ ","
							+ VillageID
							+ ","
							+ ASHAID
							+ ","
							+ FamilyCode
							+ ","
							+ CasteID
							+ ","
							+ FinancialStatusID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,"
							+ HHStatusID
							+ ",'"
							+ HHCode
							+ "'," + Verified + ")";

				} else {
					if (count1 == 0) {
						sql = "update  Tbl_HHSurvey set Latitude='" + Latitude
								+ "',Longitude='" + Longitude + "',Location='"
								+ Location + "',ChAreaID=" + ChAreaID
								+ ",CHS_ID=" + CHS_ID + ",SubCenterID="
								+ SubCenterID + ",ANMID=" + ANMID
								+ ",VillageID=" + VillageID
								+ ",ServiceProviderID=" + ASHAID
								+ ",FamilyCode=" + FamilyCode + ",CasteID="
								+ CasteID + ",FinancialStatusID="
								+ FinancialStatusID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted + ",IsEdited=0,MigrateInDate='"
								+ MigrateInDate + "',MigrateOutDate='"
								+ MigrateOutDate + "',IsUploaded=0,HHStatusID="
								+ HHStatusID + ",HHCode='" + HHCode
								+ "',Verified=" + Verified
								+ " where HHSurveyGUID='" + HHSurveyGUID + "'";
					}

				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {

					// TODO: handle exception
					downloadmsg = e.getMessage();
				}
			}
			MigrationArray = jsonObj.getJSONArray("tblMigration");
			for (int i = 0; i < MigrationArray.length(); i++) {
				JSONObject MigrationData = MigrationArray.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String DateOfMigrationIn = "";
				String DateOfMigrationOut = "";
				String MigrationGUID = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int UpdatedBy = 0;
				HHFamilyMemberGUID = MigrationData
						.getString("HHFamilyMemberGUID");
				DateOfMigrationIn = MigrationData
						.getString("DateOfMigrationIn");
				DateOfMigrationOut = MigrationData
						.getString("DateOfMigrationOut");
				MigrationGUID = MigrationData.getString("MigrationGUID");
				CreatedOn = MigrationData.getString("CreatedOn");

				if (MigrationData.getString("CreatedBy") != null
						&& MigrationData.getString("CreatedBy").length() > 0
						&& !MigrationData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(MigrationData
							.getInt("CreatedBy"));
				}
				if (MigrationData.getString("UpdatedBy") != null
						&& MigrationData.getString("UpdatedBy").length() > 0
						&& !MigrationData.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(MigrationData
							.getInt("UpdatedBy"));
				}

				String sqlcount = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblMigration (HHFamilyMemberGUID, DateOfMigrationIn,DateOfMigrationOut,MigrationGUID,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) Values('"
							+ HHFamilyMemberGUID
							+ "', '"
							+ DateOfMigrationIn
							+ "','"
							+ DateOfMigrationOut
							+ "','"
							+ MigrationGUID
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ")";

				} else {
					if (count1 == 0) {
						sql = "update tblMigration set DateOfMigrationOut='"
								+ DateOfMigrationOut + "', DateOfMigrationIn='"
								+ DateOfMigrationIn + "' ,UpdatedOn='"
								+ UpdatedOn + "',UpdatedBy=" + UpdatedBy
								+ ",CreatedOn='" + CreatedOn + "',CreatedBy="
								+ CreatedBy + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "' and MigrationGUID='"
								+ MigrationGUID + "'";
					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception
					downloadmsg = e.getMessage();
				}

			}

			HHSurveyFamilyData = jsonObj.getJSONArray("tblHHFamilyMember");
			for (int i = 0; i < HHSurveyFamilyData.length(); i++) {
				JSONObject SurveyFamilyData = HHSurveyFamilyData
						.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String HHSurveyGUID = "";
				String HHFamilyMemberCode = "";
				String UniqueIDNumber = "";
				int StatusID = 0;
				int RelationID = 0;
				int GenderID = 0;
				int MaritialStatusID = 0;
				int DOBAvailable = 0;
				String DateOfBirth = "";
				String AgeAsOnYear = "";
				int AprilAgeYear = 0;
				int AprilAgeMonth = 0;
				String MotherGUID = "";
				int TargetID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int AshaID = 0;
				int ANMID = 0;
				String DateOfDeath = "";
				int PlaceOfDeath = 0;
				int DeathVillage = 0;
				String FamilyMemberName = "";
				String NameofDeathplace = "";
				int Education = 0;
				HHFamilyMemberGUID = SurveyFamilyData
						.getString("HHFamilyMemberGUID");
				NameofDeathplace = SurveyFamilyData
						.getString("NameofDeathplace");
				HHSurveyGUID = SurveyFamilyData.getString("HHSurveyGUID");
				HHFamilyMemberCode = SurveyFamilyData
						.getString("HHFamilyMemberCode");
				UniqueIDNumber = SurveyFamilyData.getString("UniqueIDNumber");
				if (SurveyFamilyData.getString("StatusID") != null
						&& SurveyFamilyData.getString("StatusID").length() > 0
						&& !SurveyFamilyData.getString("StatusID")
								.equalsIgnoreCase("null")) {
					StatusID = Integer.valueOf(SurveyFamilyData
							.getInt("StatusID"));
				}
				if (SurveyFamilyData.getString("RelationID") != null
						&& SurveyFamilyData.getString("RelationID").length() > 0
						&& !SurveyFamilyData.getString("RelationID")
								.equalsIgnoreCase("null")) {
					RelationID = Integer.valueOf(SurveyFamilyData
							.getInt("RelationID"));
				}
				if (SurveyFamilyData.getString("GenderID") != null
						&& SurveyFamilyData.getString("GenderID").length() > 0
						&& !SurveyFamilyData.getString("GenderID")
								.equalsIgnoreCase("null")) {
					GenderID = Integer.valueOf(SurveyFamilyData
							.getInt("GenderID"));
				}
				if (SurveyFamilyData.getString("MaritialStatusID") != null
						&& SurveyFamilyData.getString("MaritialStatusID")
								.length() > 0
						&& !SurveyFamilyData.getString("MaritialStatusID")
								.equalsIgnoreCase("null")) {
					MaritialStatusID = Integer.valueOf(SurveyFamilyData
							.getInt("MaritialStatusID"));
				}
				if (SurveyFamilyData.getString("DOBAvailable") != null
						&& SurveyFamilyData.getString("DOBAvailable").length() > 0
						&& !SurveyFamilyData.getString("DOBAvailable")
								.equalsIgnoreCase("null")) {
					DOBAvailable = Integer.valueOf(SurveyFamilyData
							.getInt("DOBAvailable"));
				}
				if (SurveyFamilyData.getString("DateOfBirth") != null
						&& SurveyFamilyData.getString("DateOfBirth").length() > 0
						&& !SurveyFamilyData.getString("DateOfBirth")
								.equalsIgnoreCase("null")) {

					DateOfBirth = SurveyFamilyData.getString("DateOfBirth");
				}
				AgeAsOnYear = SurveyFamilyData.getString("AgeAsOnYear");
				if (SurveyFamilyData.getString("AprilAgeYear") != null
						&& SurveyFamilyData.getString("AprilAgeYear").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeYear")
								.equalsIgnoreCase("null")) {
					AprilAgeYear = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeYear"));
				}
				if (SurveyFamilyData.getString("AprilAgeMonth") != null
						&& SurveyFamilyData.getString("AprilAgeMonth").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeMonth")
								.equalsIgnoreCase("null")) {
					AprilAgeMonth = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeMonth"));
				}
				MotherGUID = SurveyFamilyData.getString("MotherGUID");

				if (SurveyFamilyData.getString("TargetID") != null
						&& SurveyFamilyData.getString("TargetID").length() > 0
						&& !SurveyFamilyData.getString("TargetID")
								.equalsIgnoreCase("null")) {
					TargetID = Integer.valueOf(SurveyFamilyData
							.getInt("TargetID"));
				}
				if (SurveyFamilyData.getString("CreatedBy") != null
						&& SurveyFamilyData.getString("CreatedBy").length() > 0
						&& !SurveyFamilyData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(SurveyFamilyData
							.getInt("CreatedBy"));
				}
				CreatedOn = SurveyFamilyData.getString("CreatedOn");
				if (SurveyFamilyData.getString("UploadedBy") != null
						&& SurveyFamilyData.getString("UploadedBy").length() > 0
						&& !SurveyFamilyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyFamilyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyFamilyData.getString("UploadedOn");
				if (SurveyFamilyData.getString("IsTablet") != null
						&& SurveyFamilyData.getString("IsTablet").length() > 0
						&& !SurveyFamilyData.getString("IsTablet")
								.equalsIgnoreCase("null")) {
					IsTablet = Integer.valueOf(SurveyFamilyData
							.getInt("IsTablet"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("AshaID") != null
						&& SurveyFamilyData.getString("AshaID").length() > 0
						&& !SurveyFamilyData.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(SurveyFamilyData.getInt("AshaID"));
				}
				if (SurveyFamilyData.getString("ANMID") != null
						&& SurveyFamilyData.getString("ANMID").length() > 0
						&& !SurveyFamilyData.getString("ANMID")
								.equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(SurveyFamilyData.getInt("ANMID"));
				}
				if (SurveyFamilyData.getString("Education") != null
						&& SurveyFamilyData.getString("Education").length() > 0
						&& !SurveyFamilyData.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(SurveyFamilyData
							.getInt("Education"));
				}
				if (SurveyFamilyData.getString("DeathVillage") != null
						&& SurveyFamilyData.getString("DeathVillage").length() > 0
						&& !SurveyFamilyData.getString("DeathVillage")
								.equalsIgnoreCase("null")) {
					DeathVillage = Integer.valueOf(SurveyFamilyData
							.getInt("DeathVillage"));
				}
				FamilyMemberName = SurveyFamilyData
						.getString("FamilyMemberName");
				String sqlcount = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHFamilyMember(HHFamilyMemberGUID,HHSurveyGUID,Education,AshaID,ANMID,HHFamilyMemberCode,UniqueIDNumber,StatusID,RelationID,GenderID,MaritialStatusID,DOBAvailable,DateOfBirth,AgeAsOnYear,AprilAgeYear,AprilAgeMonth,MotherGUID,TargetID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,FamilyMemberName,DateOfDeath,PlaceOfDeath,NameofDeathplace,DeathVillage)values('"
							+ HHFamilyMemberGUID
							+ "','"
							+ HHSurveyGUID
							+ "',"
							+ Education
							+ ","
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ HHFamilyMemberCode
							+ "','"
							+ UniqueIDNumber
							+ "',"
							+ StatusID
							+ ","
							+ RelationID
							+ ","
							+ GenderID
							+ ","
							+ MaritialStatusID
							+ ","
							+ DOBAvailable
							+ ",'"
							+ DateOfBirth
							+ "','"
							+ AgeAsOnYear
							+ "',"
							+ AprilAgeYear
							+ ","
							+ AprilAgeMonth
							+ ",'"
							+ MotherGUID
							+ "',"
							+ TargetID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,'"
							+ FamilyMemberName
							+ "','"
							+ DateOfDeath
							+ "',"
							+ PlaceOfDeath
							+ ",'"
							+ NameofDeathplace
							+ "',"
							+ DeathVillage + ")";

				} else {
					if (count1 == 0) {
						sql = "update Tbl_HHFamilyMember set Education="
								+ Education + ", HHFamilyMemberCode='"
								+ HHFamilyMemberCode + "',UniqueIDNumber='"
								+ UniqueIDNumber + "',StatusID=" + StatusID
								+ ",RelationID=" + RelationID + ",GenderID="
								+ GenderID + ",MaritialStatusID="
								+ MaritialStatusID + ",DOBAvailable="
								+ DOBAvailable + ",DateOfBirth='" + DateOfBirth
								+ "',AgeAsOnYear='" + AgeAsOnYear
								+ "',AprilAgeYear=" + AprilAgeYear
								+ ",AprilAgeMonth=" + AprilAgeMonth
								+ ",MotherGUID='" + MotherGUID + "',TargetID="
								+ TargetID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted
								+ ",IsEdited=0,IsUploaded=0,FamilyMemberName='"
								+ FamilyMemberName + "',AshaID=" + AshaID
								+ ",ANMID=" + ANMID + ",DeathVillage="
								+ DeathVillage + ",NameofDeathplace='"
								+ NameofDeathplace + "',DateOfDeath='"
								+ DateOfDeath + "',PlaceOfDeath="
								+ PlaceOfDeath + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "'and HHSurveyGUID='"
								+ HHSurveyGUID + "'";

					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception

					downloadmsg = e.getMessage();
				}
			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	// TODO Auto-generated method stub
	public void importtblHHSurvey(String UserName, String Password,
			String ashaid) {
		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblHHSurvey");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 500000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearSurveyData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			HHSurveyData = jsonObj.getJSONArray("tblHHSurvey");
			for (int i = 0; i < HHSurveyData.length(); i++) {
				JSONObject SurveyData = HHSurveyData.getJSONObject(i);
				String HHSurveyGUID = "";
				int SubCenterID = 0;
				int ANMID = 0;
				int VillageID = 0;
				int ASHAID = 0;
				int FamilyCode = 0;
				int CasteID = 0;
				int FinancialStatusID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int HHStatusID = 0;
				String HHCode = "";
				int Verified = 0;
				int CHS_ID = 0;
				int ChAreaID = 0;
				String Latitude = "";
				String Longitude = "";
				String Location = "";
				String MigrateInDate = "";
				String MigrateOutDate = "";
				HHCode = SurveyData.getString("HHCode");
				HHSurveyGUID = SurveyData.getString("HHSurveyGUID");
				MigrateInDate = SurveyData.getString("MigrateInDate");
				MigrateOutDate = SurveyData.getString("MigrateOutDate");

				if (SurveyData.getString("ChAreaID") != null
						&& SurveyData.getString("ChAreaID").length() > 0
						&& !SurveyData.getString("ChAreaID").equalsIgnoreCase(
								"null")) {
					ChAreaID = Integer.valueOf(SurveyData.getInt("ChAreaID"));
				}

				if (SurveyData.getString("SubCenterID") != null
						&& SurveyData.getString("SubCenterID").length() > 0
						&& !SurveyData.getString("SubCenterID")
								.equalsIgnoreCase("null")) {
					SubCenterID = Integer.valueOf(SurveyData
							.getInt("SubCenterID"));
				}

				if (SurveyData.getString("ANMID") != null
						&& SurveyData.getString("ANMID").length() > 0
						&& !SurveyData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(SurveyData.getInt("ANMID"));
				}

				if (SurveyData.getString("VillageID") != null
						&& SurveyData.getString("VillageID").length() > 0
						&& !SurveyData.getString("VillageID").equalsIgnoreCase(
								"null")) {
					VillageID = Integer.valueOf(SurveyData.getInt("VillageID"));
				}

				if (SurveyData.getString("ServiceProviderID") != null
						&& SurveyData.getString("ServiceProviderID").length() > 0
						&& !SurveyData.getString("ServiceProviderID")
								.equalsIgnoreCase("null")) {
					ASHAID = Integer.valueOf(SurveyData
							.getInt("ServiceProviderID"));
				}
				if (SurveyData.getString("FamilyCode") != null
						&& SurveyData.getString("FamilyCode").length() > 0
						&& !SurveyData.getString("FamilyCode")
								.equalsIgnoreCase("null")) {
					FamilyCode = Integer.valueOf(SurveyData
							.getInt("FamilyCode"));
				}

				if (SurveyData.getString("CasteID") != null
						&& SurveyData.getString("CasteID").length() > 0
						&& !SurveyData.getString("CasteID").equalsIgnoreCase(
								"null")) {
					CasteID = Integer.valueOf(SurveyData.getInt("CasteID"));
				}

				if (SurveyData.getString("FinancialStatusID") != null
						&& SurveyData.getString("FinancialStatusID").length() > 0
						&& !SurveyData.getString("FinancialStatusID")
								.equalsIgnoreCase("null")) {
					FinancialStatusID = Integer.valueOf(SurveyData
							.getInt("FinancialStatusID"));
				}

				if (SurveyData.getString("CreatedBy") != null
						&& SurveyData.getString("CreatedBy").length() > 0
						&& !SurveyData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(SurveyData.getInt("CreatedBy"));
				}
				CreatedOn = SurveyData.getString("CreatedOn");

				if (SurveyData.getString("UploadedBy") != null
						&& SurveyData.getString("UploadedBy").length() > 0
						&& !SurveyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyData.getString("UploadedOn");
				if (SurveyData.getString("HHStatusID") != null
						&& SurveyData.getString("HHStatusID").length() > 0
						&& !SurveyData.getString("HHStatusID")
								.equalsIgnoreCase("null")) {
					HHStatusID = Integer.valueOf(SurveyData
							.getInt("HHStatusID"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("IsDeleted") != null
						&& SurveyData.getString("IsDeleted").length() > 0
						&& !SurveyData.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(SurveyData.getInt("IsDeleted"));
				}
				if (SurveyData.getString("IsTablet") != null
						&& SurveyData.getString("IsTablet").length() > 0
						&& !SurveyData.getString("IsTablet").equalsIgnoreCase(
								"null")) {
					IsTablet = Integer.valueOf(SurveyData.getInt("IsTablet"));
				}

				String sqlcount = "select count(*) from Tbl_HHSurvey where  HHSurveyGUID='"
						+ HHSurveyGUID + "' ";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHSurvey where HHSurveyGUID='"
						+ HHSurveyGUID + "' and  IsEdited= 1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHSurvey(Latitude,Longitude,Location,MigrateOutDate,MigrateInDate,ChAreaID,CHS_ID,HHSurveyGUID,SubCenterID,ANMID,VillageID,ServiceProviderID,FamilyCode,CasteID,FinancialStatusID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,HHStatusID,HHCode,Verified)values('"
							+ Latitude
							+ "','"
							+ Longitude
							+ "','"
							+ Location
							+ "','"
							+ MigrateOutDate
							+ "','"
							+ MigrateInDate
							+ "',"
							+ ChAreaID
							+ ","
							+ CHS_ID
							+ ",'"
							+ HHSurveyGUID
							+ "',"
							+ SubCenterID
							+ ","
							+ ANMID
							+ ","
							+ VillageID
							+ ","
							+ ASHAID
							+ ","
							+ FamilyCode
							+ ","
							+ CasteID
							+ ","
							+ FinancialStatusID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,"
							+ HHStatusID
							+ ",'"
							+ HHCode
							+ "'," + Verified + ")";

				} else {
					if (count1 == 0) {
						sql = "update  Tbl_HHSurvey set Latitude='" + Latitude
								+ "',Longitude='" + Longitude + "',Location='"
								+ Location + "',ChAreaID=" + ChAreaID
								+ ",CHS_ID=" + CHS_ID + ",SubCenterID="
								+ SubCenterID + ",ANMID=" + ANMID
								+ ",VillageID=" + VillageID
								+ ",ServiceProviderID=" + ASHAID
								+ ",FamilyCode=" + FamilyCode + ",CasteID="
								+ CasteID + ",FinancialStatusID="
								+ FinancialStatusID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted + ",IsEdited=0,MigrateInDate='"
								+ MigrateInDate + "',MigrateOutDate='"
								+ MigrateOutDate + "',IsUploaded=0,HHStatusID="
								+ HHStatusID + ",HHCode='" + HHCode
								+ "',Verified=" + Verified
								+ " where HHSurveyGUID='" + HHSurveyGUID + "'";
					}

				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {

					// TODO: handle exception
					downloadmsg = e.getMessage();
				}
			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblHHFamilyMember(String UserName, String Password,
			String ashaid) {
		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblHHFamilyMember");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 500000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearSurveyData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			HHSurveyFamilyData = jsonObj.getJSONArray("tblHHFamilyMember");
			for (int i = 0; i < HHSurveyFamilyData.length(); i++) {
				JSONObject SurveyFamilyData = HHSurveyFamilyData
						.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String HHSurveyGUID = "";
				String HHFamilyMemberCode = "";
				String UniqueIDNumber = "";
				int StatusID = 0;
				int RelationID = 0;
				int GenderID = 0;
				int MaritialStatusID = 0;
				int DOBAvailable = 0;
				String DateOfBirth = "";
				String AgeAsOnYear = "";
				int AprilAgeYear = 0;
				int AprilAgeMonth = 0;
				String MotherGUID = "";
				int TargetID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int AshaID = 0;
				int ANMID = 0;
				String DateOfDeath = "";
				int PlaceOfDeath = 0;
				int DeathVillage = 0;
				String FamilyMemberName = "";
				String NameofDeathplace = "";
				int Education = 0;
				HHFamilyMemberGUID = SurveyFamilyData
						.getString("HHFamilyMemberGUID");
				NameofDeathplace = SurveyFamilyData
						.getString("NameofDeathplace");
				HHSurveyGUID = SurveyFamilyData.getString("HHSurveyGUID");
				HHFamilyMemberCode = SurveyFamilyData
						.getString("HHFamilyMemberCode");
				UniqueIDNumber = SurveyFamilyData.getString("UniqueIDNumber");
				if (SurveyFamilyData.getString("StatusID") != null
						&& SurveyFamilyData.getString("StatusID").length() > 0
						&& !SurveyFamilyData.getString("StatusID")
								.equalsIgnoreCase("null")) {
					StatusID = Integer.valueOf(SurveyFamilyData
							.getInt("StatusID"));
				}
				if (SurveyFamilyData.getString("RelationID") != null
						&& SurveyFamilyData.getString("RelationID").length() > 0
						&& !SurveyFamilyData.getString("RelationID")
								.equalsIgnoreCase("null")) {
					RelationID = Integer.valueOf(SurveyFamilyData
							.getInt("RelationID"));
				}
				if (SurveyFamilyData.getString("GenderID") != null
						&& SurveyFamilyData.getString("GenderID").length() > 0
						&& !SurveyFamilyData.getString("GenderID")
								.equalsIgnoreCase("null")) {
					GenderID = Integer.valueOf(SurveyFamilyData
							.getInt("GenderID"));
				}
				if (SurveyFamilyData.getString("MaritialStatusID") != null
						&& SurveyFamilyData.getString("MaritialStatusID")
								.length() > 0
						&& !SurveyFamilyData.getString("MaritialStatusID")
								.equalsIgnoreCase("null")) {
					MaritialStatusID = Integer.valueOf(SurveyFamilyData
							.getInt("MaritialStatusID"));
				}
				if (SurveyFamilyData.getString("DOBAvailable") != null
						&& SurveyFamilyData.getString("DOBAvailable").length() > 0
						&& !SurveyFamilyData.getString("DOBAvailable")
								.equalsIgnoreCase("null")) {
					DOBAvailable = Integer.valueOf(SurveyFamilyData
							.getInt("DOBAvailable"));
				}
				if (SurveyFamilyData.getString("DateOfBirth") != null
						&& SurveyFamilyData.getString("DateOfBirth").length() > 0
						&& !SurveyFamilyData.getString("DateOfBirth")
								.equalsIgnoreCase("null")) {

					DateOfBirth = SurveyFamilyData.getString("DateOfBirth");
				}
				AgeAsOnYear = SurveyFamilyData.getString("AgeAsOnYear");
				if (SurveyFamilyData.getString("AprilAgeYear") != null
						&& SurveyFamilyData.getString("AprilAgeYear").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeYear")
								.equalsIgnoreCase("null")) {
					AprilAgeYear = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeYear"));
				}
				if (SurveyFamilyData.getString("AprilAgeMonth") != null
						&& SurveyFamilyData.getString("AprilAgeMonth").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeMonth")
								.equalsIgnoreCase("null")) {
					AprilAgeMonth = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeMonth"));
				}
				MotherGUID = SurveyFamilyData.getString("MotherGUID");

				if (SurveyFamilyData.getString("TargetID") != null
						&& SurveyFamilyData.getString("TargetID").length() > 0
						&& !SurveyFamilyData.getString("TargetID")
								.equalsIgnoreCase("null")) {
					TargetID = Integer.valueOf(SurveyFamilyData
							.getInt("TargetID"));
				}
				if (SurveyFamilyData.getString("CreatedBy") != null
						&& SurveyFamilyData.getString("CreatedBy").length() > 0
						&& !SurveyFamilyData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(SurveyFamilyData
							.getInt("CreatedBy"));
				}
				CreatedOn = SurveyFamilyData.getString("CreatedOn");
				if (SurveyFamilyData.getString("UploadedBy") != null
						&& SurveyFamilyData.getString("UploadedBy").length() > 0
						&& !SurveyFamilyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyFamilyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyFamilyData.getString("UploadedOn");
				if (SurveyFamilyData.getString("IsTablet") != null
						&& SurveyFamilyData.getString("IsTablet").length() > 0
						&& !SurveyFamilyData.getString("IsTablet")
								.equalsIgnoreCase("null")) {
					IsTablet = Integer.valueOf(SurveyFamilyData
							.getInt("IsTablet"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("AshaID") != null
						&& SurveyFamilyData.getString("AshaID").length() > 0
						&& !SurveyFamilyData.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(SurveyFamilyData.getInt("AshaID"));
				}
				if (SurveyFamilyData.getString("ANMID") != null
						&& SurveyFamilyData.getString("ANMID").length() > 0
						&& !SurveyFamilyData.getString("ANMID")
								.equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(SurveyFamilyData.getInt("ANMID"));
				}
				if (SurveyFamilyData.getString("Education") != null
						&& SurveyFamilyData.getString("Education").length() > 0
						&& !SurveyFamilyData.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(SurveyFamilyData
							.getInt("Education"));
				}
				if (SurveyFamilyData.getString("DeathVillage") != null
						&& SurveyFamilyData.getString("DeathVillage").length() > 0
						&& !SurveyFamilyData.getString("DeathVillage")
								.equalsIgnoreCase("null")) {
					DeathVillage = Integer.valueOf(SurveyFamilyData
							.getInt("DeathVillage"));
				}
				FamilyMemberName = SurveyFamilyData
						.getString("FamilyMemberName");
				String sqlcount = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHFamilyMember(HHFamilyMemberGUID,HHSurveyGUID,Education,AshaID,ANMID,HHFamilyMemberCode,UniqueIDNumber,StatusID,RelationID,GenderID,MaritialStatusID,DOBAvailable,DateOfBirth,AgeAsOnYear,AprilAgeYear,AprilAgeMonth,MotherGUID,TargetID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,FamilyMemberName,DateOfDeath,PlaceOfDeath,NameofDeathplace,DeathVillage)values('"
							+ HHFamilyMemberGUID
							+ "','"
							+ HHSurveyGUID
							+ "',"
							+ Education
							+ ","
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ HHFamilyMemberCode
							+ "','"
							+ UniqueIDNumber
							+ "',"
							+ StatusID
							+ ","
							+ RelationID
							+ ","
							+ GenderID
							+ ","
							+ MaritialStatusID
							+ ","
							+ DOBAvailable
							+ ",'"
							+ DateOfBirth
							+ "','"
							+ AgeAsOnYear
							+ "',"
							+ AprilAgeYear
							+ ","
							+ AprilAgeMonth
							+ ",'"
							+ MotherGUID
							+ "',"
							+ TargetID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,'"
							+ FamilyMemberName
							+ "','"
							+ DateOfDeath
							+ "',"
							+ PlaceOfDeath
							+ ",'"
							+ NameofDeathplace
							+ "',"
							+ DeathVillage + ")";

				} else {
					if (count1 == 0) {
						sql = "update Tbl_HHFamilyMember set Education="
								+ Education + ", HHFamilyMemberCode='"
								+ HHFamilyMemberCode + "',UniqueIDNumber='"
								+ UniqueIDNumber + "',StatusID=" + StatusID
								+ ",RelationID=" + RelationID + ",GenderID="
								+ GenderID + ",MaritialStatusID="
								+ MaritialStatusID + ",DOBAvailable="
								+ DOBAvailable + ",DateOfBirth='" + DateOfBirth
								+ "',AgeAsOnYear='" + AgeAsOnYear
								+ "',AprilAgeYear=" + AprilAgeYear
								+ ",AprilAgeMonth=" + AprilAgeMonth
								+ ",MotherGUID='" + MotherGUID + "',TargetID="
								+ TargetID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted
								+ ",IsEdited=0,IsUploaded=0,FamilyMemberName='"
								+ FamilyMemberName + "',AshaID=" + AshaID
								+ ",ANMID=" + ANMID + ",DeathVillage="
								+ DeathVillage + ",NameofDeathplace='"
								+ NameofDeathplace + "',DateOfDeath='"
								+ DateOfDeath + "',PlaceOfDeath="
								+ PlaceOfDeath + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "'and HHSurveyGUID='"
								+ HHSurveyGUID + "'";

					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception

					downloadmsg = e.getMessage();
				}
			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblMigration(String UserName, String Password,
			String ashaid) {
		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblMigration");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 500000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearSurveyData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			MigrationArray = jsonObj.getJSONArray("tblMigration");
			for (int i = 0; i < MigrationArray.length(); i++) {
				JSONObject MigrationData = MigrationArray.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String DateOfMigrationIn = "";
				String DateOfMigrationOut = "";
				String MigrationGUID = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int UpdatedBy = 0;
				HHFamilyMemberGUID = MigrationData
						.getString("HHFamilyMemberGUID");
				DateOfMigrationIn = MigrationData
						.getString("DateOfMigrationIn");
				DateOfMigrationOut = MigrationData
						.getString("DateOfMigrationOut");
				MigrationGUID = MigrationData.getString("MigrationGUID");
				CreatedOn = MigrationData.getString("CreatedOn");

				if (MigrationData.getString("CreatedBy") != null
						&& MigrationData.getString("CreatedBy").length() > 0
						&& !MigrationData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(MigrationData
							.getInt("CreatedBy"));
				}
				if (MigrationData.getString("UpdatedBy") != null
						&& MigrationData.getString("UpdatedBy").length() > 0
						&& !MigrationData.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(MigrationData
							.getInt("UpdatedBy"));
				}

				String sqlcount = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblMigration (HHFamilyMemberGUID, DateOfMigrationIn,DateOfMigrationOut,MigrationGUID,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) Values('"
							+ HHFamilyMemberGUID
							+ "', '"
							+ DateOfMigrationIn
							+ "','"
							+ DateOfMigrationOut
							+ "','"
							+ MigrationGUID
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ")";

				} else {
					if (count1 == 0) {
						sql = "update tblMigration set DateOfMigrationOut='"
								+ DateOfMigrationOut + "', DateOfMigrationIn='"
								+ DateOfMigrationIn + "' ,UpdatedOn='"
								+ UpdatedOn + "',UpdatedBy=" + UpdatedBy
								+ ",CreatedOn='" + CreatedOn + "',CreatedBy="
								+ CreatedBy + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "' and MigrationGUID='"
								+ MigrationGUID + "'";
					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception
					downloadmsg = e.getMessage();
				}

			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	@SuppressWarnings("unused")
	public void importSurveydata1(String UserName, String Password,
			String ashaid) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = GlobalString.METHOD_NAME_DOWNLOAD_MASTER;

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "Data");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 1000000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearSurveyData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			HHSurveyData = jsonObj.getJSONArray("tblHHSurvey");
			for (int i = 0; i < HHSurveyData.length(); i++) {
				JSONObject SurveyData = HHSurveyData.getJSONObject(i);
				String HHSurveyGUID = "";
				int SubCenterID = 0;
				int ANMID = 0;
				int VillageID = 0;
				int ASHAID = 0;
				int FamilyCode = 0;
				int CasteID = 0;
				int FinancialStatusID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int HHStatusID = 0;
				String HHCode = "";
				int Verified = 0;
				int CHS_ID = 0;
				int ChAreaID = 0;
				String Latitude = "";
				String Longitude = "";
				String Location = "";
				String MigrateInDate = "";
				String MigrateOutDate = "";
				HHCode = SurveyData.getString("HHCode");
				HHSurveyGUID = SurveyData.getString("HHSurveyGUID");
				MigrateInDate = SurveyData.getString("MigrateInDate");
				MigrateOutDate = SurveyData.getString("MigrateOutDate");

				// if (SurveyData.getString("Latitude") != null
				// && SurveyData.getString("Latitude").length() > 0
				// && !SurveyData.getString("Latitude")
				// .equalsIgnoreCase("null")) {
				// Latitude = SurveyData.getString("Latitude");
				// }
				if (SurveyData.getString("ChAreaID") != null
						&& SurveyData.getString("ChAreaID").length() > 0
						&& !SurveyData.getString("ChAreaID").equalsIgnoreCase(
								"null")) {
					ChAreaID = Integer.valueOf(SurveyData.getInt("ChAreaID"));
				}
				// if (SurveyData.getString("Longitude") != null
				// && SurveyData.getString("Longitude").length() > 0
				// && !SurveyData.getString("Longitude")
				// .equalsIgnoreCase("null")) {
				// Longitude = SurveyData.getString("Longitude");
				// }
				// if (SurveyData.getString("Location") != null
				// && SurveyData.getString("Location").length() > 0
				// && !SurveyData.getString("Location")
				// .equalsIgnoreCase("null")) {
				// Location =SurveyData.getString("Location");
				// }
				if (SurveyData.getString("SubCenterID") != null
						&& SurveyData.getString("SubCenterID").length() > 0
						&& !SurveyData.getString("SubCenterID")
								.equalsIgnoreCase("null")) {
					SubCenterID = Integer.valueOf(SurveyData
							.getInt("SubCenterID"));
				}

				if (SurveyData.getString("ANMID") != null
						&& SurveyData.getString("ANMID").length() > 0
						&& !SurveyData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(SurveyData.getInt("ANMID"));
				}

				if (SurveyData.getString("VillageID") != null
						&& SurveyData.getString("VillageID").length() > 0
						&& !SurveyData.getString("VillageID").equalsIgnoreCase(
								"null")) {
					VillageID = Integer.valueOf(SurveyData.getInt("VillageID"));
				}

				if (SurveyData.getString("ServiceProviderID") != null
						&& SurveyData.getString("ServiceProviderID").length() > 0
						&& !SurveyData.getString("ServiceProviderID")
								.equalsIgnoreCase("null")) {
					ASHAID = Integer.valueOf(SurveyData
							.getInt("ServiceProviderID"));
				}
				if (SurveyData.getString("FamilyCode") != null
						&& SurveyData.getString("FamilyCode").length() > 0
						&& !SurveyData.getString("FamilyCode")
								.equalsIgnoreCase("null")) {
					FamilyCode = Integer.valueOf(SurveyData
							.getInt("FamilyCode"));
				}

				if (SurveyData.getString("CasteID") != null
						&& SurveyData.getString("CasteID").length() > 0
						&& !SurveyData.getString("CasteID").equalsIgnoreCase(
								"null")) {
					CasteID = Integer.valueOf(SurveyData.getInt("CasteID"));
				}
				// if (SurveyData.getString("CHS_ID") != null
				// && SurveyData.getString("CHS_ID").length() > 0
				// && !SurveyData.getString("CHS_ID").equalsIgnoreCase(
				// "null")) {
				// CHS_ID = Integer.valueOf(SurveyData.getInt("CHS_ID"));
				// }

				if (SurveyData.getString("FinancialStatusID") != null
						&& SurveyData.getString("FinancialStatusID").length() > 0
						&& !SurveyData.getString("FinancialStatusID")
								.equalsIgnoreCase("null")) {
					FinancialStatusID = Integer.valueOf(SurveyData
							.getInt("FinancialStatusID"));
				}

				if (SurveyData.getString("CreatedBy") != null
						&& SurveyData.getString("CreatedBy").length() > 0
						&& !SurveyData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(SurveyData.getInt("CreatedBy"));
				}
				CreatedOn = SurveyData.getString("CreatedOn");

				if (SurveyData.getString("UploadedBy") != null
						&& SurveyData.getString("UploadedBy").length() > 0
						&& !SurveyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyData.getString("UploadedOn");
				if (SurveyData.getString("HHStatusID") != null
						&& SurveyData.getString("HHStatusID").length() > 0
						&& !SurveyData.getString("HHStatusID")
								.equalsIgnoreCase("null")) {
					HHStatusID = Integer.valueOf(SurveyData
							.getInt("HHStatusID"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("Verified") != null
						&& SurveyData.getString("Verified").length() > 0
						&& !SurveyData.getString("Verified").equalsIgnoreCase(
								"null")) {
					Verified = Integer.valueOf(SurveyData.getInt("Verified"));
				}
				if (SurveyData.getString("IsDeleted") != null
						&& SurveyData.getString("IsDeleted").length() > 0
						&& !SurveyData.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(SurveyData.getInt("IsDeleted"));
				}
				if (SurveyData.getString("IsTablet") != null
						&& SurveyData.getString("IsTablet").length() > 0
						&& !SurveyData.getString("IsTablet").equalsIgnoreCase(
								"null")) {
					IsTablet = Integer.valueOf(SurveyData.getInt("IsTablet"));
				}
				// if (SurveyData.getString("Latitude") != null
				// && SurveyData.getString("Latitude").length() > 0
				// && !SurveyData.getString("Latitude")
				// .equalsIgnoreCase("null")) {
				// Latitude = SurveyData.getString("Latitude");
				// }
				// if (SurveyData.getString("Longitude") != null
				// && SurveyData.getString("Longitude").length() > 0
				// && !SurveyData.getString("Longitude")
				// .equalsIgnoreCase("null")) {
				// Longitude = SurveyData.getString("Longitude");
				// }
				//
				// if (SurveyData.getString("Location") != null
				// && SurveyData.getString("Location").length() > 0
				// && !SurveyData.getString("Location")
				// .equalsIgnoreCase("null")) {
				// Location = SurveyData.getString("Location");
				// }
				String sqlcount = "select count(*) from Tbl_HHSurvey where  HHSurveyGUID='"
						+ HHSurveyGUID + "' ";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHSurvey where HHSurveyGUID='"
						+ HHSurveyGUID + "' and  IsEdited= 1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHSurvey(Latitude,Longitude,Location,MigrateOutDate,MigrateInDate,ChAreaID,CHS_ID,HHSurveyGUID,SubCenterID,ANMID,VillageID,ServiceProviderID,FamilyCode,CasteID,FinancialStatusID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,HHStatusID,HHCode,Verified)values('"
							+ Latitude
							+ "','"
							+ Longitude
							+ "','"
							+ Location
							+ "','"
							+ MigrateOutDate
							+ "','"
							+ MigrateInDate
							+ "',"
							+ ChAreaID
							+ ","
							+ CHS_ID
							+ ",'"
							+ HHSurveyGUID
							+ "',"
							+ SubCenterID
							+ ","
							+ ANMID
							+ ","
							+ VillageID
							+ ","
							+ ASHAID
							+ ","
							+ FamilyCode
							+ ","
							+ CasteID
							+ ","
							+ FinancialStatusID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,"
							+ HHStatusID
							+ ",'"
							+ HHCode
							+ "'," + Verified + ")";

				} else {
					if (count1 == 0) {
						sql = "update  Tbl_HHSurvey set Latitude='" + Latitude
								+ "',Longitude='" + Longitude + "',Location='"
								+ Location + "',ChAreaID=" + ChAreaID
								+ ",CHS_ID=" + CHS_ID + ",SubCenterID="
								+ SubCenterID + ",ANMID=" + ANMID
								+ ",VillageID=" + VillageID
								+ ",ServiceProviderID=" + ASHAID
								+ ",FamilyCode=" + FamilyCode + ",CasteID="
								+ CasteID + ",FinancialStatusID="
								+ FinancialStatusID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted + ",IsEdited=0,MigrateInDate='"
								+ MigrateInDate + "',MigrateOutDate='"
								+ MigrateOutDate + "',IsUploaded=0,HHStatusID="
								+ HHStatusID + ",HHCode='" + HHCode
								+ "',Verified=" + Verified
								+ " where HHSurveyGUID='" + HHSurveyGUID + "'";
					}

				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {

					// TODO: handle exception
					downloadmsg = e.getMessage();
				}
			}
			MigrationArray = jsonObj.getJSONArray("tblMigration");
			for (int i = 0; i < MigrationArray.length(); i++) {
				JSONObject MigrationData = MigrationArray.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String DateOfMigrationIn = "";
				String DateOfMigrationOut = "";
				String MigrationGUID = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int UpdatedBy = 0;
				HHFamilyMemberGUID = MigrationData
						.getString("HHFamilyMemberGUID");
				DateOfMigrationIn = MigrationData
						.getString("DateOfMigrationIn");
				DateOfMigrationOut = MigrationData
						.getString("DateOfMigrationOut");
				MigrationGUID = MigrationData.getString("MigrationGUID");
				CreatedOn = MigrationData.getString("CreatedOn");

				if (MigrationData.getString("CreatedBy") != null
						&& MigrationData.getString("CreatedBy").length() > 0
						&& !MigrationData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(MigrationData
							.getInt("CreatedBy"));
				}
				if (MigrationData.getString("UpdatedBy") != null
						&& MigrationData.getString("UpdatedBy").length() > 0
						&& !MigrationData.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(MigrationData
							.getInt("UpdatedBy"));
				}

				String sqlcount = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblMigration where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID
						+ "'and MigrationGUID='"
						+ MigrationGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblMigration (HHFamilyMemberGUID, DateOfMigrationIn,DateOfMigrationOut,MigrationGUID,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy) Values('"
							+ HHFamilyMemberGUID
							+ "', '"
							+ DateOfMigrationIn
							+ "','"
							+ DateOfMigrationOut
							+ "','"
							+ MigrationGUID
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ")";

				} else {
					if (count1 == 0) {
						sql = "update tblMigration set DateOfMigrationOut='"
								+ DateOfMigrationOut + "', DateOfMigrationIn='"
								+ DateOfMigrationIn + "' ,UpdatedOn='"
								+ UpdatedOn + "',UpdatedBy=" + UpdatedBy
								+ ",CreatedOn='" + CreatedOn + "',CreatedBy="
								+ CreatedBy + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "' and MigrationGUID='"
								+ MigrationGUID + "'";
					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception
					downloadmsg = e.getMessage();
				}

			}

			HHSurveyFamilyData = jsonObj.getJSONArray("tblHHFamilyMember");
			for (int i = 0; i < HHSurveyFamilyData.length(); i++) {
				JSONObject SurveyFamilyData = HHSurveyFamilyData
						.getJSONObject(i);
				String HHFamilyMemberGUID = "";
				String HHSurveyGUID = "";
				String HHFamilyMemberCode = "";
				String UniqueIDNumber = "";
				int StatusID = 0;
				int RelationID = 0;
				int GenderID = 0;
				int MaritialStatusID = 0;
				int DOBAvailable = 0;
				String DateOfBirth = "";
				String AgeAsOnYear = "";
				int AprilAgeYear = 0;
				int AprilAgeMonth = 0;
				String MotherGUID = "";
				int TargetID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UploadedBy = 0;
				String UploadedOn = "";
				int IsTablet = 0;
				int IsDeleted = 0;
				int AshaID = 0;
				int ANMID = 0;
				String DateOfDeath = "";
				int PlaceOfDeath = 0;
				int DeathVillage = 0;
				String FamilyMemberName = "";
				String NameofDeathplace = "";
				int Education = 0;
				HHFamilyMemberGUID = SurveyFamilyData
						.getString("HHFamilyMemberGUID");
				NameofDeathplace = SurveyFamilyData
						.getString("NameofDeathplace");
				HHSurveyGUID = SurveyFamilyData.getString("HHSurveyGUID");
				HHFamilyMemberCode = SurveyFamilyData
						.getString("HHFamilyMemberCode");
				UniqueIDNumber = SurveyFamilyData.getString("UniqueIDNumber");
				if (SurveyFamilyData.getString("StatusID") != null
						&& SurveyFamilyData.getString("StatusID").length() > 0
						&& !SurveyFamilyData.getString("StatusID")
								.equalsIgnoreCase("null")) {
					StatusID = Integer.valueOf(SurveyFamilyData
							.getInt("StatusID"));
				}
				if (SurveyFamilyData.getString("RelationID") != null
						&& SurveyFamilyData.getString("RelationID").length() > 0
						&& !SurveyFamilyData.getString("RelationID")
								.equalsIgnoreCase("null")) {
					RelationID = Integer.valueOf(SurveyFamilyData
							.getInt("RelationID"));
				}
				if (SurveyFamilyData.getString("GenderID") != null
						&& SurveyFamilyData.getString("GenderID").length() > 0
						&& !SurveyFamilyData.getString("GenderID")
								.equalsIgnoreCase("null")) {
					GenderID = Integer.valueOf(SurveyFamilyData
							.getInt("GenderID"));
				}
				if (SurveyFamilyData.getString("MaritialStatusID") != null
						&& SurveyFamilyData.getString("MaritialStatusID")
								.length() > 0
						&& !SurveyFamilyData.getString("MaritialStatusID")
								.equalsIgnoreCase("null")) {
					MaritialStatusID = Integer.valueOf(SurveyFamilyData
							.getInt("MaritialStatusID"));
				}
				if (SurveyFamilyData.getString("DOBAvailable") != null
						&& SurveyFamilyData.getString("DOBAvailable").length() > 0
						&& !SurveyFamilyData.getString("DOBAvailable")
								.equalsIgnoreCase("null")) {
					DOBAvailable = Integer.valueOf(SurveyFamilyData
							.getInt("DOBAvailable"));
				}
				if (SurveyFamilyData.getString("DateOfBirth") != null
						&& SurveyFamilyData.getString("DateOfBirth").length() > 0
						&& !SurveyFamilyData.getString("DateOfBirth")
								.equalsIgnoreCase("null")) {

					DateOfBirth = SurveyFamilyData.getString("DateOfBirth");
				}
				AgeAsOnYear = SurveyFamilyData.getString("AgeAsOnYear");
				if (SurveyFamilyData.getString("AprilAgeYear") != null
						&& SurveyFamilyData.getString("AprilAgeYear").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeYear")
								.equalsIgnoreCase("null")) {
					AprilAgeYear = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeYear"));
				}
				if (SurveyFamilyData.getString("AprilAgeMonth") != null
						&& SurveyFamilyData.getString("AprilAgeMonth").length() > 0
						&& !SurveyFamilyData.getString("AprilAgeMonth")
								.equalsIgnoreCase("null")) {
					AprilAgeMonth = Integer.valueOf(SurveyFamilyData
							.getInt("AprilAgeMonth"));
				}
				MotherGUID = SurveyFamilyData.getString("MotherGUID");

				if (SurveyFamilyData.getString("TargetID") != null
						&& SurveyFamilyData.getString("TargetID").length() > 0
						&& !SurveyFamilyData.getString("TargetID")
								.equalsIgnoreCase("null")) {
					TargetID = Integer.valueOf(SurveyFamilyData
							.getInt("TargetID"));
				}
				if (SurveyFamilyData.getString("CreatedBy") != null
						&& SurveyFamilyData.getString("CreatedBy").length() > 0
						&& !SurveyFamilyData.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(SurveyFamilyData
							.getInt("CreatedBy"));
				}
				CreatedOn = SurveyFamilyData.getString("CreatedOn");
				if (SurveyFamilyData.getString("UploadedBy") != null
						&& SurveyFamilyData.getString("UploadedBy").length() > 0
						&& !SurveyFamilyData.getString("UploadedBy")
								.equalsIgnoreCase("null")) {
					UploadedBy = Integer.valueOf(SurveyFamilyData
							.getInt("UploadedBy"));
				}
				UploadedOn = SurveyFamilyData.getString("UploadedOn");
				if (SurveyFamilyData.getString("IsTablet") != null
						&& SurveyFamilyData.getString("IsTablet").length() > 0
						&& !SurveyFamilyData.getString("IsTablet")
								.equalsIgnoreCase("null")) {
					IsTablet = Integer.valueOf(SurveyFamilyData
							.getInt("IsTablet"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("IsDeleted") != null
						&& SurveyFamilyData.getString("IsDeleted").length() > 0
						&& !SurveyFamilyData.getString("IsDeleted")
								.equalsIgnoreCase("null")) {
					IsDeleted = Integer.valueOf(SurveyFamilyData
							.getInt("IsDeleted"));
				}
				if (SurveyFamilyData.getString("AshaID") != null
						&& SurveyFamilyData.getString("AshaID").length() > 0
						&& !SurveyFamilyData.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(SurveyFamilyData.getInt("AshaID"));
				}
				if (SurveyFamilyData.getString("ANMID") != null
						&& SurveyFamilyData.getString("ANMID").length() > 0
						&& !SurveyFamilyData.getString("ANMID")
								.equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(SurveyFamilyData.getInt("ANMID"));
				}
				if (SurveyFamilyData.getString("Education") != null
						&& SurveyFamilyData.getString("Education").length() > 0
						&& !SurveyFamilyData.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(SurveyFamilyData
							.getInt("Education"));
				}
				if (SurveyFamilyData.getString("DeathVillage") != null
						&& SurveyFamilyData.getString("DeathVillage").length() > 0
						&& !SurveyFamilyData.getString("DeathVillage")
								.equalsIgnoreCase("null")) {
					DeathVillage = Integer.valueOf(SurveyFamilyData
							.getInt("DeathVillage"));
				}
				FamilyMemberName = SurveyFamilyData
						.getString("FamilyMemberName");
				String sqlcount = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from Tbl_HHFamilyMember where  HHFamilyMemberGUID='"
						+ HHFamilyMemberGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into Tbl_HHFamilyMember(HHFamilyMemberGUID,HHSurveyGUID,Education,AshaID,ANMID,HHFamilyMemberCode,UniqueIDNumber,StatusID,RelationID,GenderID,MaritialStatusID,DOBAvailable,DateOfBirth,AgeAsOnYear,AprilAgeYear,AprilAgeMonth,MotherGUID,TargetID,CreatedBy,CreatedOn,UploadedBy,UploadedOn,IsTablet,IsDeleted,IsEdited,IsUploaded,FamilyMemberName,DateOfDeath,PlaceOfDeath,NameofDeathplace,DeathVillage)values('"
							+ HHFamilyMemberGUID
							+ "','"
							+ HHSurveyGUID
							+ "',"
							+ Education
							+ ","
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ HHFamilyMemberCode
							+ "','"
							+ UniqueIDNumber
							+ "',"
							+ StatusID
							+ ","
							+ RelationID
							+ ","
							+ GenderID
							+ ","
							+ MaritialStatusID
							+ ","
							+ DOBAvailable
							+ ",'"
							+ DateOfBirth
							+ "','"
							+ AgeAsOnYear
							+ "',"
							+ AprilAgeYear
							+ ","
							+ AprilAgeMonth
							+ ",'"
							+ MotherGUID
							+ "',"
							+ TargetID
							+ ","
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "',"
							+ UploadedBy
							+ ",'"
							+ UploadedOn
							+ "',"
							+ IsTablet
							+ ","
							+ IsDeleted
							+ ",0,0,'"
							+ FamilyMemberName
							+ "','"
							+ DateOfDeath
							+ "',"
							+ PlaceOfDeath
							+ ",'"
							+ NameofDeathplace
							+ "',"
							+ DeathVillage + ")";

				} else {
					if (count1 == 0) {
						sql = "update Tbl_HHFamilyMember set Education="
								+ Education + ", HHFamilyMemberCode='"
								+ HHFamilyMemberCode + "',UniqueIDNumber='"
								+ UniqueIDNumber + "',StatusID=" + StatusID
								+ ",RelationID=" + RelationID + ",GenderID="
								+ GenderID + ",MaritialStatusID="
								+ MaritialStatusID + ",DOBAvailable="
								+ DOBAvailable + ",DateOfBirth='" + DateOfBirth
								+ "',AgeAsOnYear='" + AgeAsOnYear
								+ "',AprilAgeYear=" + AprilAgeYear
								+ ",AprilAgeMonth=" + AprilAgeMonth
								+ ",MotherGUID='" + MotherGUID + "',TargetID="
								+ TargetID + ",CreatedBy=" + CreatedBy
								+ ",CreatedOn='" + CreatedOn + "',UploadedBy="
								+ UploadedBy + ",UploadedOn='" + UploadedOn
								+ "',IsTablet=" + IsTablet + ",IsDeleted="
								+ IsDeleted
								+ ",IsEdited=0,IsUploaded=0,FamilyMemberName='"
								+ FamilyMemberName + "',AshaID=" + AshaID
								+ ",ANMID=" + ANMID + ",DeathVillage="
								+ DeathVillage + ",NameofDeathplace='"
								+ NameofDeathplace + "',DateOfDeath='"
								+ DateOfDeath + "',PlaceOfDeath="
								+ PlaceOfDeath + " where HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID + "'and HHSurveyGUID='"
								+ HHSurveyGUID + "'";

					}
				}
				try {

					dataProvider.executeSql(sql);
				} catch (Exception e) {
					// TODO: handle exception

					downloadmsg = e.getMessage();
				}
			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	@SuppressWarnings("unused")
	public void importMNCHSurveydata(String UserName, String Password) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = GlobalString.METHOD_NAME_DOWNLOAD_MASTER;

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "MNCH");

		request.addProperty("AuthenticationToken", UserName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearMNCHData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblChildArray = jsonObj.getJSONArray("tblChild");
			for (int i = 0; i < tblChildArray.length(); i++) {
				JSONObject Child = tblChildArray.getJSONObject(i);

				String pw_GUID = "";
				String HHGUID = "";
				String HHFamilyMemberGUID = "";
				int Child_ID = 0;
				String ChildGUID = "";
				String MotherGUID = "";
				String Date_Of_Registration = "";
				String Child_dob = "";
				String Birth_time = "";
				int Gender = 0;
				float Wt_of_child = 0;
				int place_of_birth = 0;
				String preTerm_fullTerm = "";
				int mother_status = 0;
				int child_status = 0;
				String mother_death_dt = "";
				String child_death_dt = "";
				String child_mcts_id = "";
				String child_name = "";
				String cried_after_birth = "";
				int breastfeeding_within1H = 0;
				String Exclusive_BF = "";
				String complementry_BF = "";
				String bcg = "";
				String opv1 = "";
				String dpt1 = "";
				String hepb1 = "";
				String opv2 = "";
				String dpt2 = "";
				String hepb2 = "";
				String opv3 = "";
				String dpt3 = "";
				String hepb3 = "";
				String measeals = "";
				String vitaminA = "";
				String created_on = "";
				int created_by = 0;
				String modified_on = "";
				int modified_by = 0;
				int AshaID = 0;
				int ANMID = 0;
				int IsEdited = 0;
				int IsUploaded = 0;
				int FacilityType = 0;
				String Facility = "";
				String opv4 = "";
				String hepb4 = "";
				String Pentavalent1 = "";
				String Pentavalent2 = "";
				String Pentavalent3 = "";
				String IPV = "";
				String DPTBooster = "";
				String OPVBooster = "";
				String MeaslesTwoDose = "";
				String VitaminAtwo = "";
				String DPTBoostertwo = "";
				String ChildTT = "";
				// add jitendra
				String JEVaccine1 = "";
				String JEVaccine2 = "";
				String VitaminA3 = "";
				String VitaminA4 = "";
				String VitaminA5 = "";
				String VitaminA6 = "";
				String VitaminA7 = "";
				String VitaminA8 = "";
				String VitaminA9 = "";
				String TT2 = "";

				Facility = Child.getString("Facility");
				opv4 = Child.getString("opv4");
				hepb4 = Child.getString("hepb4");
				Pentavalent1 = Child.getString("Pentavalent1");
				Pentavalent2 = Child.getString("Pentavalent2");
				Pentavalent3 = Child.getString("Pentavalent3");
				IPV = Child.getString("IPV");
				DPTBooster = Child.getString("DPTBooster");
				OPVBooster = Child.getString("OPVBooster");
				MeaslesTwoDose = Child.getString("MeaslesTwoDose");
				VitaminAtwo = Child.getString("VitaminAtwo");
				DPTBoostertwo = Child.getString("DPTBoostertwo");
				ChildTT = Child.getString("ChildTT");
				pw_GUID = Child.getString("pw_GUID");
				HHGUID = Child.getString("HHGUID");
				HHFamilyMemberGUID = Child.getString("HHFamilyMemberGUID");
				ChildGUID = Child.getString("ChildGUID");
				MotherGUID = Child.getString("MotherGUID");
				Date_Of_Registration = Child.getString("Date_Of_Registration");
				Child_dob = Child.getString("Child_dob");
				Birth_time = Child.getString("Birth_time");
				preTerm_fullTerm = Child.getString("preTerm_fullTerm");
				mother_death_dt = Child.getString("mother_death_dt");
				child_death_dt = Child.getString("child_death_dt");
				child_mcts_id = Child.getString("child_mcts_id");
				child_name = Child.getString("child_name");
				cried_after_birth = Child.getString("cried_after_birth");
				Exclusive_BF = Child.getString("Exclusive_BF");
				complementry_BF = Child.getString("complementry_BF");
				bcg = Child.getString("bcg");
				opv1 = Child.getString("opv1");
				dpt1 = Child.getString("dpt1");
				hepb1 = Child.getString("hepb1");
				opv2 = Child.getString("opv2");
				dpt2 = Child.getString("dpt2");
				hepb2 = Child.getString("hepb2");
				opv3 = Child.getString("opv3");
				dpt3 = Child.getString("dpt3");
				hepb3 = Child.getString("hepb3");
				measeals = Child.getString("measeals");
				vitaminA = Child.getString("vitaminA");
				created_on = Child.getString("created_on");
				modified_on = Child.getString("modified_on");
				JEVaccine1 = Child.getString("JEVaccine1");
				JEVaccine2 = Child.getString("JEVaccine2");
				VitaminA3 = Child.getString("VitaminA3");
				VitaminA4 = Child.getString("VitaminA4");
				VitaminA5 = Child.getString("VitaminA5");
				VitaminA6 = Child.getString("VitaminA6");
				VitaminA7 = Child.getString("VitaminA7");
				VitaminA8 = Child.getString("VitaminA8");
				VitaminA9 = Child.getString("VitaminA9");
				TT2 = Child.getString("TT2");

				if (Child.getString("Child_ID") != null
						&& Child.getString("Child_ID").length() > 0
						&& !Child.getString("Child_ID")
								.equalsIgnoreCase("null")) {
					Child_ID = Integer.valueOf(Child.getInt("Child_ID"));
				}
				if (Child.getString("Gender") != null
						&& Child.getString("Gender").length() > 0
						&& !Child.getString("Gender").equalsIgnoreCase("null")) {
					Gender = Integer.valueOf(Child.getInt("Gender"));
				}
				if (Child.getString("Wt_of_child") != null
						&& Child.getString("Wt_of_child").length() > 0
						&& !Child.getString("Wt_of_child").equalsIgnoreCase(
								"null")) {
					Wt_of_child = Float.valueOf(Child.getInt("Wt_of_child"));
				}
				if (Child.getString("place_of_birth") != null
						&& Child.getString("place_of_birth").length() > 0
						&& !Child.getString("place_of_birth").equalsIgnoreCase(
								"null")) {
					place_of_birth = Integer.valueOf(Child
							.getInt("place_of_birth"));
				}
				if (Child.getString("mother_status") != null
						&& Child.getString("mother_status").length() > 0
						&& !Child.getString("mother_status").equalsIgnoreCase(
								"null")) {
					mother_status = Integer.valueOf(Child
							.getInt("mother_status"));
				}
				if (Child.getString("child_status") != null
						&& Child.getString("child_status").length() > 0
						&& !Child.getString("child_status").equalsIgnoreCase(
								"null")) {
					child_status = Integer
							.valueOf(Child.getInt("child_status"));
				}
				if (Child.getString("breastfeeding_within1H") != null
						&& Child.getString("breastfeeding_within1H").length() > 0
						&& !Child.getString("breastfeeding_within1H")
								.equalsIgnoreCase("null")) {
					breastfeeding_within1H = Integer.valueOf(Child
							.getInt("breastfeeding_within1H"));
				}
				if (Child.getString("created_by") != null
						&& Child.getString("created_by").length() > 0
						&& !Child.getString("created_by").equalsIgnoreCase(
								"null")) {
					created_by = Integer.valueOf(Child.getInt("created_by"));
				}
				if (Child.getString("modified_by") != null
						&& Child.getString("modified_by").length() > 0
						&& !Child.getString("modified_by").equalsIgnoreCase(
								"null")) {
					modified_by = Integer.valueOf(Child.getInt("modified_by"));
				}
				if (Child.getString("AshaID") != null
						&& Child.getString("AshaID").length() > 0
						&& !Child.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(Child.getInt("AshaID"));
				}
				if (Child.getString("ANMID") != null
						&& Child.getString("ANMID").length() > 0
						&& !Child.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(Child.getInt("ANMID"));
				}
				if (Child.getString("FacilityType") != null
						&& Child.getString("FacilityType").length() > 0
						&& !Child.getString("FacilityType").equalsIgnoreCase(
								"null")) {
					FacilityType = Integer
							.valueOf(Child.getInt("FacilityType"));
				}
				String sqlcount = "select count(*) from tblChild  where ChildGUID='"
						+ ChildGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblChild  where ChildGUID='"
						+ ChildGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tblChild(AshaID,ANMID,pw_GUID,Facility,opv4,hepb4,Pentavalent1,Pentavalent2,Pentavalent3,IPV,DPTBooster,OPVBooster,MeaslesTwoDose,VitaminAtwo,DPTBoostertwo,ChildTT,FacilityType,HHGUID,HHFamilyMemberGUID,Child_ID,ChildGUID,MotherGUID,Date_Of_Registration,Child_dob,Birth_time,Gender,Wt_of_child,place_of_birth,preTerm_fullTerm,mother_status,child_status,mother_death_dt,child_death_dt,child_mcts_id,child_name,cried_after_birth,breastfeeding_within1H,Exclusive_BF,complementry_BF,bcg,opv1,dpt1,hepb1,opv2,dpt2,hepb2,opv3,dpt3,hepb3,measeals,vitaminA,created_on,created_by,modified_on,modified_by,IsEdited,IsUploaded,JEVaccine1,JEVaccine2,VitaminA3,VitaminA4,VitaminA5,VitaminA6,VitaminA7,VitaminA8,VitaminA9,TT2)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ pw_GUID
							+ "',	'"
							+ Facility
							+ "',	'"
							+ opv4
							+ "',	'"
							+ hepb4
							+ "','"
							+ Pentavalent1
							+ "',	'"
							+ Pentavalent2
							+ "',	'"
							+ Pentavalent3
							+ "',		'"
							+ IPV
							+ "',	'"
							+ DPTBooster
							+ "','"
							+ OPVBooster
							+ "',	'"
							+ MeaslesTwoDose
							+ "','"
							+ VitaminAtwo
							+ "',	'"
							+ DPTBoostertwo
							+ "',	'"
							+ ChildTT
							+ "',"
							+ FacilityType
							+ ",'"
							+ HHGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "',"
							+ Child_ID
							+ ",'"
							+ ChildGUID
							+ "','"
							+ MotherGUID
							+ "','"
							+ Date_Of_Registration
							+ "','"
							+ Child_dob
							+ "','"
							+ Birth_time
							+ "',"
							+ Gender
							+ ","
							+ Wt_of_child
							+ ","
							+ place_of_birth
							+ ",'"
							+ preTerm_fullTerm
							+ "',"
							+ mother_status
							+ ","
							+ child_status
							+ ",'"
							+ mother_death_dt
							+ "','"
							+ child_death_dt
							+ "','"
							+ child_mcts_id
							+ "','"
							+ child_name
							+ "','"
							+ cried_after_birth
							+ "',"
							+ breastfeeding_within1H
							+ ",'"
							+ Exclusive_BF
							+ "','"
							+ complementry_BF
							+ "','"
							+ bcg
							+ "','"
							+ opv1
							+ "','"
							+ dpt1
							+ "','"
							+ hepb1
							+ "','"
							+ opv2
							+ "','"
							+ dpt2
							+ "','"
							+ hepb2
							+ "','"
							+ opv3
							+ "','"
							+ dpt3
							+ "','"
							+ hepb3
							+ "','"
							+ measeals
							+ "','"
							+ vitaminA
							+ "','"
							+ created_on
							+ "',"
							+ created_by
							+ ",'"
							+ modified_on
							+ "',"
							+ modified_by
							+ ",0,0, '"
							+ JEVaccine1
							+ "','"
							+ JEVaccine2
							+ "','"
							+ VitaminA3
							+ "','"
							+ VitaminA4
							+ "','"
							+ VitaminA5
							+ "','"
							+ VitaminA6
							+ "','"
							+ VitaminA7
							+ "','"
							+ VitaminA8
							+ "','"
							+ VitaminA9
							+ "','"
							+ TT2
							+ "')";
					dataProvider.executeSql(sql);

				} else {
					if (count1 == 0) {
						sql = "update  tblChild set pw_GUID='" + pw_GUID
								+ "',HHGUID='" + HHGUID
								+ "',HHFamilyMemberGUID='" + HHFamilyMemberGUID
								+ "',Child_ID=" + Child_ID + ",MotherGUID='"
								+ MotherGUID + "',Date_Of_Registration='"
								+ Date_Of_Registration + "',Child_dob='"
								+ Child_dob + "',Birth_time='" + Birth_time
								+ "',Gender=" + Gender + ",Wt_of_child="
								+ Wt_of_child + ",place_of_birth="
								+ place_of_birth + ",preTerm_fullTerm='"
								+ preTerm_fullTerm + "',mother_status="
								+ mother_status + ",child_status="
								+ child_status + ",mother_death_dt='"
								+ mother_death_dt + "',child_death_dt='"
								+ child_death_dt + "',child_mcts_id='"
								+ child_mcts_id + "',child_name='" + child_name
								+ "',cried_after_birth='" + cried_after_birth
								+ "',breastfeeding_within1H="
								+ breastfeeding_within1H + ",Exclusive_BF='"
								+ Exclusive_BF + "',complementry_BF='"
								+ complementry_BF + "',bcg='" + bcg
								+ "',opv1='" + opv1 + "',dpt1='" + dpt1
								+ "',hepb1='" + hepb1 + "',opv2='" + opv2
								+ "',dpt2='" + dpt2 + "',hepb2='" + hepb2
								+ "',opv3='" + opv3 + "',dpt3='" + dpt3
								+ "',hepb3='" + hepb3 + "',measeals='"
								+ measeals + "',vitaminA='" + vitaminA
								+ "',created_on='" + created_on
								+ "',created_by=" + created_by
								+ ",modified_on='" + modified_on
								+ "',modified_by=" + modified_by + " ,AshaID="
								+ AshaID + ",Facility='" + Facility
								+ "',	opv4='" + opv4 + "',	hepb4='" + hepb4
								+ "',Pentavalent1='" + Pentavalent1
								+ "',	Pentavalent2='" + Pentavalent2
								+ "',	Pentavalent3='" + Pentavalent3
								+ "',		IPV='" + IPV + "',	DPTBooster='"
								+ DPTBooster + "',OPVBooster='" + OPVBooster
								+ "',MeaslesTwoDose=	'" + MeaslesTwoDose
								+ "',VitaminAtwo='" + VitaminAtwo
								+ "',	DPTBoostertwo='" + DPTBoostertwo
								+ "',ChildTT=	'" + ChildTT + "',ANMID=" + ANMID
								+ ",FacilityType=" + FacilityType
								+ ",JEVaccine1='" + JEVaccine1
								+ "',JEVaccine2='" + JEVaccine2
								+ "',VitaminA3='" + VitaminA3 + "',VitaminA4='"
								+ VitaminA4 + "',VitaminA5='" + VitaminA5
								+ "',VitaminA6='" + VitaminA6 + "',VitaminA7='"
								+ VitaminA7 + "',VitaminA8='" + VitaminA8
								+ "',VitaminA9='" + VitaminA9 + "',TT2='" + TT2
								+ "',IsEdited=0,IsUploaded=0 where ChildGUID='"
								+ ChildGUID + "'";
						dataProvider.executeSql(sql);
					}
				}
			}
			TblANCVisitArray = jsonObj.getJSONArray("tblANCVisit");
			for (int i = 0; i < TblANCVisitArray.length(); i++) {
				JSONObject ANCVisit = TblANCVisitArray.getJSONObject(i);

				String PWGUID = "";
				String VisitGUID = "";
				int ByANMID = 0;
				int ByAshaID = 0;
				String MCTSID = "";
				int Visit_No = 0;
				int Trimester = 0;
				String VisitDueDate = "";
				String CheckupVisitDate = "";
				int CheckupPlace = 0;
				double BirthWeight = 0;
				int BP = 0;
				String BPResult = "";
				double Hemoglobin = 0;
				int UrineTest = 0;
				int UrineSugar = 0;
				int UrineAlbumin = 0;
				String TTfirstDoseDate = "";
				String TTsecondDoseDate = "";
				String TTBoosterDate = "";
				int VDRLTest = 0;
				int HIVTest = 0;
				int UltraSound = 0;
				int UltraSoundConductedby = 0;
				int IFARecieved = 0;
				int NumberIFARecieved = 0;
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				int UltrasoundResult = 0;
				String HomeVisitDate = "";
				int TT1TT2last2yr = 0;
				PWGUID = ANCVisit.getString("PWGUID");
				VisitGUID = ANCVisit.getString("VisitGUID");
				MCTSID = ANCVisit.getString("MCTSID");
				VisitDueDate = ANCVisit.getString("VisitDueDate");
				CheckupVisitDate = ANCVisit.getString("CheckupVisitDate");
				BPResult = ANCVisit.getString("BPResult");
				TTfirstDoseDate = ANCVisit.getString("TTfirstDoseDate");
				TTsecondDoseDate = ANCVisit.getString("TTsecondDoseDate");
				TTBoosterDate = ANCVisit.getString("TTboosterDate");
				CreatedOn = ANCVisit.getString("CreatedOn");
				UpdatedOn = ANCVisit.getString("UpdatedOn");
				HomeVisitDate = ANCVisit.getString("HomeVisitDate");

				if (ANCVisit.getString("ByANMID") != null
						&& ANCVisit.getString("ByANMID").length() > 0
						&& !ANCVisit.getString("ByANMID").equalsIgnoreCase(
								"null")) {
					ByANMID = Integer.valueOf(ANCVisit.getInt("ByANMID"));
				}
				if (ANCVisit.getString("ByAshaID") != null
						&& ANCVisit.getString("ByAshaID").length() > 0
						&& !ANCVisit.getString("ByAshaID").equalsIgnoreCase(
								"null")) {
					ByAshaID = Integer.valueOf(ANCVisit.getInt("ByAshaID"));
				}
				if (ANCVisit.getString("Visit_No") != null
						&& ANCVisit.getString("Visit_No").length() > 0
						&& !ANCVisit.getString("Visit_No").equalsIgnoreCase(
								"null")) {
					Visit_No = Integer.valueOf(ANCVisit.getInt("Visit_No"));
				}
				if (ANCVisit.getString("Trimester") != null
						&& ANCVisit.getString("Trimester").length() > 0
						&& !ANCVisit.getString("Trimester").equalsIgnoreCase(
								"null")) {
					Trimester = Integer.valueOf(ANCVisit.getInt("Trimester"));
				}
				if (ANCVisit.getString("CheckupPlace") != null
						&& ANCVisit.getString("CheckupPlace").length() > 0
						&& !ANCVisit.getString("CheckupPlace")
								.equalsIgnoreCase("null")) {
					CheckupPlace = Integer.valueOf(ANCVisit
							.getInt("CheckupPlace"));
				}
				if (ANCVisit.getString("BirthWeight") != null
						&& ANCVisit.getString("BirthWeight").length() > 0
						&& !ANCVisit.getString("BirthWeight").equalsIgnoreCase(
								"null")) {
					BirthWeight = Double
							.valueOf(ANCVisit.getInt("BirthWeight"));
				}
				if (ANCVisit.getString("BP") != null
						&& ANCVisit.getString("BP").length() > 0
						&& !ANCVisit.getString("BP").equalsIgnoreCase("null")) {
					BP = Integer.valueOf(ANCVisit.getInt("BP"));
				}
				if (ANCVisit.getString("Hemoglobin") != null
						&& ANCVisit.getString("Hemoglobin").length() > 0
						&& !ANCVisit.getString("Hemoglobin").equalsIgnoreCase(
								"null")) {
					Hemoglobin = Double.valueOf(ANCVisit.getInt("Hemoglobin"));
				}
				if (ANCVisit.getString("UrineTest") != null
						&& ANCVisit.getString("UrineTest").length() > 0
						&& !ANCVisit.getString("UrineTest").equalsIgnoreCase(
								"null")) {
					UrineTest = Integer.valueOf(ANCVisit.getInt("UrineTest"));
				}
				if (ANCVisit.getString("UrineSugar") != null
						&& ANCVisit.getString("UrineSugar").length() > 0
						&& !ANCVisit.getString("UrineSugar").equalsIgnoreCase(
								"null")) {
					UrineSugar = Integer.valueOf(ANCVisit.getInt("UrineSugar"));
				}
				if (ANCVisit.getString("UrineAlbumin") != null
						&& ANCVisit.getString("UrineAlbumin").length() > 0
						&& !ANCVisit.getString("UrineAlbumin")
								.equalsIgnoreCase("null")) {
					UrineAlbumin = Integer.valueOf(ANCVisit
							.getInt("UrineAlbumin"));
				}
				if (ANCVisit.getString("VDRLTest") != null
						&& ANCVisit.getString("VDRLTest").length() > 0
						&& !ANCVisit.getString("VDRLTest").equalsIgnoreCase(
								"null")) {
					VDRLTest = Integer.valueOf(ANCVisit.getInt("VDRLTest"));
				}
				if (ANCVisit.getString("HIVTest") != null
						&& ANCVisit.getString("HIVTest").length() > 0
						&& !ANCVisit.getString("HIVTest").equalsIgnoreCase(
								"null")) {
					HIVTest = Integer.valueOf(ANCVisit.getInt("HIVTest"));
				}
				if (ANCVisit.getString("UltraSound") != null
						&& ANCVisit.getString("UltraSound").length() > 0
						&& !ANCVisit.getString("UltraSound").equalsIgnoreCase(
								"null")) {
					UltraSound = Integer.valueOf(ANCVisit.getInt("UltraSound"));
				}
				if (ANCVisit.getString("UltraSoundConductedby") != null
						&& ANCVisit.getString("UltraSoundConductedby").length() > 0
						&& !ANCVisit.getString("UltraSoundConductedby")
								.equalsIgnoreCase("null")) {
					UltraSoundConductedby = Integer.valueOf(ANCVisit
							.getInt("UltraSoundConductedby"));
				}
				if (ANCVisit.getString("IFARecieved") != null
						&& ANCVisit.getString("IFARecieved").length() > 0
						&& !ANCVisit.getString("IFARecieved").equalsIgnoreCase(
								"null")) {
					IFARecieved = Integer.valueOf(ANCVisit
							.getInt("IFARecieved"));
				}
				if (ANCVisit.getString("NumberIFARecieved") != null
						&& ANCVisit.getString("NumberIFARecieved").length() > 0
						&& !ANCVisit.getString("NumberIFARecieved")
								.equalsIgnoreCase("null")) {
					NumberIFARecieved = Integer.valueOf(ANCVisit
							.getInt("NumberIFARecieved"));
				}
				if (ANCVisit.getString("CreatedBy") != null
						&& ANCVisit.getString("CreatedBy").length() > 0
						&& !ANCVisit.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(ANCVisit.getInt("CreatedBy"));
				}
				if (ANCVisit.getString("UpdatedBy") != null
						&& ANCVisit.getString("UpdatedBy").length() > 0
						&& !ANCVisit.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(ANCVisit.getInt("UpdatedBy"));
				}
				if (ANCVisit.getString("UltrasoundResult") != null
						&& ANCVisit.getString("UltrasoundResult").length() > 0
						&& !ANCVisit.getString("UltrasoundResult")
								.equalsIgnoreCase("null")) {
					UltrasoundResult = Integer.valueOf(ANCVisit
							.getInt("UltrasoundResult"));
				}
				if (ANCVisit.getString("TT1TT2last2yr") != null
						&& ANCVisit.getString("TT1TT2last2yr").length() > 0
						&& !ANCVisit.getString("TT1TT2last2yr")
								.equalsIgnoreCase("null")) {
					TT1TT2last2yr = Integer.valueOf(ANCVisit
							.getInt("TT1TT2last2yr"));
				}
				String sqlcount = "select count(*) from TblANCVisit  where VisitGUID='"
						+ VisitGUID + "' and PWGUID='" + PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from TblANCVisit  where VisitGUID='"
						+ VisitGUID
						+ "' and PWGUID='"
						+ PWGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into TblANCVisit(TT1TT2last2yr,PWGUID,VisitGUID,ByANMID,ByAshaID,MCTSID,Visit_No,Trimester,VisitDueDate,CheckupVisitDate,CheckupPlace,BirthWeight,BP,BPResult,Hemoglobin,UrineTest,UrineSugar,UrineAlbumin,TTfirstDoseDate,TTsecondDoseDate,TTBoosterDate,VDRLTest,HIVTest,UltraSound,UltraSoundConductedby,IFARecieved,NumberIFARecieved,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,UltrasoundResult,HomeVisitDate,IsEdited,IsUploaded)values("
							+ TT1TT2last2yr
							+ ",'"
							+ PWGUID
							+ "','"
							+ VisitGUID
							+ "',"
							+ ByANMID
							+ ","
							+ ByAshaID
							+ ",'"
							+ MCTSID
							+ "',"
							+ Visit_No
							+ ","
							+ Trimester
							+ ",'"
							+ VisitDueDate
							+ "','"
							+ CheckupVisitDate
							+ "',"
							+ CheckupPlace
							+ ","
							+ BirthWeight
							+ ","
							+ BP
							+ ",'"
							+ BPResult
							+ "',"
							+ Hemoglobin
							+ ","
							+ UrineTest
							+ ","
							+ UrineSugar
							+ ","
							+ UrineAlbumin
							+ ",'"
							+ TTfirstDoseDate
							+ "','"
							+ TTsecondDoseDate
							+ "','"
							+ TTBoosterDate
							+ "',"
							+ VDRLTest
							+ ","
							+ HIVTest
							+ ","
							+ UltraSound
							+ ","
							+ UltraSoundConductedby
							+ ","
							+ IFARecieved
							+ ","
							+ NumberIFARecieved
							+ ",'"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ","
							+ UltrasoundResult
							+ ",'"
							+ HomeVisitDate
							+ "',0,0)";
					dataProvider.executeSql(sql);
				} else {
					if (count1 == 0) {
						sql = "update TblANCVisit set TT1TT2last2yr="
								+ TT1TT2last2yr + ", ByANMID=" + ByANMID
								+ ",ByAshaID=" + ByAshaID + ",MCTSID='"
								+ MCTSID + "',Visit_No=" + Visit_No
								+ ",Trimester=" + Trimester + ",VisitDueDate='"
								+ VisitDueDate + "',CheckupVisitDate='"
								+ CheckupVisitDate + "',CheckupPlace="
								+ CheckupPlace + ",BirthWeight=" + BirthWeight
								+ ",BP=" + BP + ",BPResult='" + BPResult
								+ "',Hemoglobin=" + Hemoglobin + ",UrineTest="
								+ UrineTest + ",UrineSugar=" + UrineSugar
								+ ",UrineAlbumin=" + UrineAlbumin
								+ ",TTfirstDoseDate='" + TTfirstDoseDate
								+ "',TTsecondDoseDate='" + TTsecondDoseDate
								+ "',TTBoosterDate='" + TTBoosterDate
								+ "',VDRLTest=" + VDRLTest + ",HIVTest="
								+ HIVTest + ",UltraSound=" + UltraSound
								+ ",UltraSoundConductedby="
								+ UltraSoundConductedby + ",IFARecieved="
								+ IFARecieved + ",NumberIFARecieved="
								+ NumberIFARecieved + ",CreatedOn='"
								+ CreatedOn + "',CreatedBy=" + CreatedBy
								+ ",UpdatedOn='" + UpdatedOn
								+ "',IsEdited=0,IsUploaded=0,UpdatedBy="
								+ UpdatedBy + ",UltrasoundResult="
								+ UltrasoundResult + ",HomeVisitDate='"
								+ HomeVisitDate + "' where PWGUID='" + PWGUID
								+ "' and VisitGUID='" + VisitGUID + "'";
						dataProvider.executeSql(sql);
					}
				}
			}

			tblpregnantwomenArray = jsonObj.getJSONArray("tblPregnant_woman");
			for (int i = 0; i < tblpregnantwomenArray.length(); i++) {
				JSONObject pregnantwomen = tblpregnantwomenArray
						.getJSONObject(i);
				String AltMobileNo = "";
				String PWGUID = "";
				String HHGUID = "";
				String HHFamilyMemberGUID = "";
				String PWName = "";
				int ANMID = 0;
				String PWImage = "";
				int AshaID = 0;
				String LMPDate = "";
				String EDDDate = "";
				String PWRegistrationDate = "";
				int Regwithin12weeks = 0;
				int RegweeksElaspsed = 0;
				String HusbandName = "";
				String Husband_GUID = "";
				String MobileNo = "";
				String MotherMCTSID = "";
				String IFSCCode = "";
				String Accountno = "";
				int JSYBenificiaryYN = 0;
				String JSYRegDate = "";
				int JSYPaymentReceivedYN = 0;
				String PWDOB = "";
				int PWAgeYears = 0;
				String PWAgeRefDate = "";
				double PWWeight = 0;
				int PWBloodGroup = 0;
				String PastIllnessYN = "";
				int TotalPregnancy = 0;
				int LastPregnancyResult = 0;
				int LastPregnancyComplication = 0;
				int LTLPregnancyResult = 0;
				int LTLPregnancyomplication = 0;
				double PWHeight = 0;
				int LastPregDeliveryPlace = 0;
				int LasttolastPregDeliveryPlace = 0;
				String ExpFacilityforDelivery = "";
				String ExpFacilityforDeliveryName = "";
				int VDRLTestYN = 0;
				String VDRLResult = "";
				int HIVTestYN = 0;
				String HIVResult = "";
				String Visit1Date = "";
				String Visit2Date = "";
				String Visit3Date = "";
				String Visit4Date = "";
				int ISAbortion = 0;
				int IsPregnant = 0;
				int AbortionFacilityType = 0;
				int NoofANCVisitsDone = 0;
				String LastANCVisitDate = "";
				String TT1Date = "";
				String TT2Date = "";
				String TTBoosterDate = "";
				String DangerSigns = "";
				int RefferedYN = 0;
				String DeliveryDateTime = "";
				int DeliveryPlace = 0;
				String DeliveryConductedBy = "";
				int DeliveryType = 0;
				String DeliveryComplication = "";
				int MotherDeathCause = 0;
				int DeliveryOutcome = 0;
				String DTMFacilityDischarge = "";
				String PaymentRecieved = "";
				int PlaceofDeath = 0;
				String DateofDeath = "";
				String OtherPlaceofDeath = "";
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				int Education = 0;
				int Abortion_FacilityName = 0;
				PWGUID = pregnantwomen.getString("PWGUID");
				AltMobileNo = pregnantwomen.getString("AltMobileNo");
				HHGUID = pregnantwomen.getString("HHGUID");
				HHFamilyMemberGUID = pregnantwomen
						.getString("HHFamilyMemberGUID");
				PWName = pregnantwomen.getString("PWName");
				PWImage = pregnantwomen.getString("PWImage");
				LMPDate = pregnantwomen.getString("LMPDate");
				EDDDate = pregnantwomen.getString("EDDDate");
				PWRegistrationDate = pregnantwomen
						.getString("PWRegistrationDate");
				HusbandName = pregnantwomen.getString("HusbandName");
				Husband_GUID = pregnantwomen.getString("Husband_GUID");
				MobileNo = pregnantwomen.getString("MobileNo");
				MotherMCTSID = pregnantwomen.getString("MotherMCTSID");
				IFSCCode = pregnantwomen.getString("IFSCCode");
				Accountno = pregnantwomen.getString("Accountno");
				JSYRegDate = pregnantwomen.getString("JSYRegDate");
				PWDOB = pregnantwomen.getString("PWDOB");
				PWAgeRefDate = pregnantwomen.getString("PWAgeRefDate");
				ExpFacilityforDelivery = pregnantwomen
						.getString("ExpFacilityforDelivery");
				ExpFacilityforDeliveryName = pregnantwomen
						.getString("ExpFacilityforDeliveryName");
				VDRLResult = pregnantwomen.getString("VDRLResult");
				HIVResult = pregnantwomen.getString("HIVResult");
				Visit1Date = pregnantwomen.getString("Visit1Date");
				Visit2Date = pregnantwomen.getString("Visit2Date");
				Visit3Date = pregnantwomen.getString("Visit3Date");
				Visit4Date = pregnantwomen.getString("Visit4Date");
				LastANCVisitDate = pregnantwomen.getString("LastANCVisitDate");
				TT1Date = pregnantwomen.getString("TT1Date");
				TT2Date = pregnantwomen.getString("TT2Date");

				TTBoosterDate = pregnantwomen.getString("TTBoosterDate");
				DangerSigns = pregnantwomen.getString("DangerSigns");
				DeliveryDateTime = pregnantwomen.getString("DeliveryDateTime");
				DeliveryConductedBy = pregnantwomen
						.getString("DeliveryConductedBy");
				DeliveryComplication = pregnantwomen
						.getString("DeliveryComplication");
				DTMFacilityDischarge = pregnantwomen
						.getString("DTMFacilityDischarge");
				PaymentRecieved = pregnantwomen.getString("PaymentRecieved");
				DateofDeath = pregnantwomen.getString("DateofDeath");
				OtherPlaceofDeath = pregnantwomen
						.getString("OtherPlaceofDeath");
				CreatedOn = pregnantwomen.getString("CreatedOn");
				UpdatedOn = pregnantwomen.getString("UpdatedOn");

				if (pregnantwomen.getString("ANMID") != null
						&& pregnantwomen.getString("ANMID").length() > 0
						&& !pregnantwomen.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(pregnantwomen.getInt("ANMID"));
				}
				if (pregnantwomen.getString("AshaID") != null
						&& pregnantwomen.getString("AshaID").length() > 0
						&& !pregnantwomen.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(pregnantwomen.getInt("AshaID"));
				}
				if (pregnantwomen.getString("Regwithin12weeks") != null
						&& pregnantwomen.getString("Regwithin12weeks").length() > 0
						&& !pregnantwomen.getString("Regwithin12weeks")
								.equalsIgnoreCase("null")) {
					Regwithin12weeks = Integer.valueOf(pregnantwomen
							.getInt("Regwithin12weeks"));
				}
				if (pregnantwomen.getString("RegweeksElaspsed") != null
						&& pregnantwomen.getString("RegweeksElaspsed").length() > 0
						&& !pregnantwomen.getString("RegweeksElaspsed")
								.equalsIgnoreCase("null")) {
					RegweeksElaspsed = Integer.valueOf(pregnantwomen
							.getInt("RegweeksElaspsed"));
				}
				if (pregnantwomen.getString("JSYBenificiaryYN") != null
						&& pregnantwomen.getString("JSYBenificiaryYN").length() > 0
						&& !pregnantwomen.getString("JSYBenificiaryYN")
								.equalsIgnoreCase("null")) {
					JSYBenificiaryYN = Integer.valueOf(pregnantwomen
							.getInt("JSYBenificiaryYN"));
				}
				if (pregnantwomen.getString("JSYPaymentReceivedYN") != null
						&& pregnantwomen.getString("JSYPaymentReceivedYN")
								.length() > 0
						&& !pregnantwomen.getString("JSYPaymentReceivedYN")
								.equalsIgnoreCase("null")) {
					JSYPaymentReceivedYN = Integer.valueOf(pregnantwomen
							.getInt("JSYPaymentReceivedYN"));
				}
				if (pregnantwomen.getString("PWAgeYears") != null
						&& pregnantwomen.getString("PWAgeYears").length() > 0
						&& !pregnantwomen.getString("PWAgeYears")
								.equalsIgnoreCase("null")) {
					PWAgeYears = Integer.valueOf(pregnantwomen
							.getInt("PWAgeYears"));
				}
				if (pregnantwomen.getString("PWWeight") != null
						&& pregnantwomen.getString("PWWeight").length() > 0
						&& !pregnantwomen.getString("PWWeight")
								.equalsIgnoreCase("null")) {
					PWWeight = Double.valueOf(pregnantwomen.getInt("PWWeight"));
				}
				if (pregnantwomen.getString("PWBloodGroup") != null
						&& pregnantwomen.getString("PWBloodGroup").length() > 0
						&& !pregnantwomen.getString("PWBloodGroup")
								.equalsIgnoreCase("null")) {
					PWBloodGroup = Integer.valueOf(pregnantwomen
							.getInt("PWBloodGroup"));
				}

				PastIllnessYN = pregnantwomen.getString("PastIllnessYN");

				if (pregnantwomen.getString("TotalPregnancy") != null
						&& pregnantwomen.getString("TotalPregnancy").length() > 0
						&& !pregnantwomen.getString("TotalPregnancy")
								.equalsIgnoreCase("null")) {
					TotalPregnancy = Integer.valueOf(pregnantwomen
							.getInt("TotalPregnancy"));
				}
				if (pregnantwomen.getString("LastPregnancyResult") != null
						&& pregnantwomen.getString("LastPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LastPregnancyResult")
								.equalsIgnoreCase("null")) {
					LastPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyResult"));
				}
				if (pregnantwomen.getString("LastPregnancyComplication") != null
						&& pregnantwomen.getString("LastPregnancyComplication")
								.length() > 0
						&& !pregnantwomen
								.getString("LastPregnancyComplication")
								.equalsIgnoreCase("null")) {
					LastPregnancyComplication = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyComplication"));
				}
				if (pregnantwomen.getString("LTLPregnancyResult") != null
						&& pregnantwomen.getString("LTLPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyResult")
								.equalsIgnoreCase("null")) {
					LTLPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyResult"));
				}
				if (pregnantwomen.getString("LTLPregnancyomplication") != null
						&& pregnantwomen.getString("LTLPregnancyomplication")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyomplication")
								.equalsIgnoreCase("null")) {
					LTLPregnancyomplication = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyomplication"));
				}
				if (pregnantwomen.getString("PWHeight") != null
						&& pregnantwomen.getString("PWHeight").length() > 0
						&& !pregnantwomen.getString("PWHeight")
								.equalsIgnoreCase("null")) {
					PWHeight = Double.valueOf(pregnantwomen.getInt("PWHeight"));
				}
				if (pregnantwomen.getString("LastPregDeliveryPlace") != null
						&& pregnantwomen.getString("LastPregDeliveryPlace")
								.length() > 0
						&& !pregnantwomen.getString("LastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("LasttolastPregDeliveryPlace") != null
						&& pregnantwomen.getString(
								"LasttolastPregDeliveryPlace").length() > 0
						&& !pregnantwomen.getString(
								"LasttolastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LasttolastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LasttolastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("VDRLTestYN") != null
						&& pregnantwomen.getString("VDRLTestYN").length() > 0
						&& !pregnantwomen.getString("VDRLTestYN")
								.equalsIgnoreCase("null")) {
					VDRLTestYN = Integer.valueOf(pregnantwomen
							.getInt("VDRLTestYN"));
				}
				if (pregnantwomen.getString("HIVTestYN") != null
						&& pregnantwomen.getString("HIVTestYN").length() > 0
						&& !pregnantwomen.getString("HIVTestYN")
								.equalsIgnoreCase("null")) {
					HIVTestYN = Integer.valueOf(pregnantwomen
							.getInt("HIVTestYN"));
				}
				if (pregnantwomen.getString("ISAbortion") != null
						&& pregnantwomen.getString("ISAbortion").length() > 0
						&& !pregnantwomen.getString("ISAbortion")
								.equalsIgnoreCase("null")) {
					ISAbortion = Integer.valueOf(pregnantwomen
							.getInt("ISAbortion"));
				}
				if (pregnantwomen.getString("IsPregnant") != null
						&& pregnantwomen.getString("IsPregnant").length() > 0
						&& !pregnantwomen.getString("IsPregnant")
								.equalsIgnoreCase("null")) {
					IsPregnant = Integer.valueOf(pregnantwomen
							.getInt("IsPregnant"));
				}
				if (pregnantwomen.getString("AbortionFacilityType") != null
						&& pregnantwomen.getString("AbortionFacilityType")
								.length() > 0
						&& !pregnantwomen.getString("AbortionFacilityType")
								.equalsIgnoreCase("null")) {
					AbortionFacilityType = Integer.valueOf(pregnantwomen
							.getInt("AbortionFacilityType"));
				}
				if (pregnantwomen.getString("NoofANCVisitsDone") != null
						&& pregnantwomen.getString("NoofANCVisitsDone")
								.length() > 0
						&& !pregnantwomen.getString("NoofANCVisitsDone")
								.equalsIgnoreCase("null")) {
					NoofANCVisitsDone = Integer.valueOf(pregnantwomen
							.getInt("NoofANCVisitsDone"));
				}
				if (pregnantwomen.getString("RefferedYN") != null
						&& pregnantwomen.getString("RefferedYN").length() > 0
						&& !pregnantwomen.getString("RefferedYN")
								.equalsIgnoreCase("null")) {
					RefferedYN = Integer.valueOf(pregnantwomen
							.getInt("RefferedYN"));
				}
				if (pregnantwomen.getString("DeliveryPlace") != null
						&& pregnantwomen.getString("DeliveryPlace").length() > 0
						&& !pregnantwomen.getString("DeliveryPlace")
								.equalsIgnoreCase("null")) {
					DeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("DeliveryPlace"));
				}
				if (pregnantwomen.getString("DeliveryType") != null
						&& pregnantwomen.getString("DeliveryType").length() > 0
						&& !pregnantwomen.getString("DeliveryType")
								.equalsIgnoreCase("null")) {
					DeliveryType = Integer.valueOf(pregnantwomen
							.getInt("DeliveryType"));
				}
				if (pregnantwomen.getString("MotherDeathCause") != null
						&& pregnantwomen.getString("MotherDeathCause").length() > 0
						&& !pregnantwomen.getString("MotherDeathCause")
								.equalsIgnoreCase("null")) {
					MotherDeathCause = Integer.valueOf(pregnantwomen
							.getInt("MotherDeathCause"));
				}
				if (pregnantwomen.getString("DeliveryOutcome") != null
						&& pregnantwomen.getString("DeliveryOutcome").length() > 0
						&& !pregnantwomen.getString("DeliveryOutcome")
								.equalsIgnoreCase("null")) {
					DeliveryOutcome = Integer.valueOf(pregnantwomen
							.getInt("DeliveryOutcome"));
				}
				if (pregnantwomen.getString("PlaceofDeath") != null
						&& pregnantwomen.getString("PlaceofDeath").length() > 0
						&& !pregnantwomen.getString("PlaceofDeath")
								.equalsIgnoreCase("null")) {
					PlaceofDeath = Integer.valueOf(pregnantwomen
							.getInt("PlaceofDeath"));
				}
				if (pregnantwomen.getString("CreatedBy") != null
						&& pregnantwomen.getString("CreatedBy").length() > 0
						&& !pregnantwomen.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(pregnantwomen
							.getInt("CreatedBy"));
				}
				if (pregnantwomen.getString("UpdatedBy") != null
						&& pregnantwomen.getString("UpdatedBy").length() > 0
						&& !pregnantwomen.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(pregnantwomen
							.getInt("UpdatedBy"));
				}
				if (pregnantwomen.getString("Education") != null
						&& pregnantwomen.getString("Education").length() > 0
						&& !pregnantwomen.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(pregnantwomen
							.getInt("Education"));
				}
				if (pregnantwomen.getString("Abortion_FacilityName") != null
						&& pregnantwomen.getString("Abortion_FacilityName")
								.length() > 0
						&& !pregnantwomen.getString("Abortion_FacilityName")
								.equalsIgnoreCase("null")) {
					Abortion_FacilityName = Integer.valueOf(pregnantwomen
							.getInt("Abortion_FacilityName"));
				}

				String sqlcount = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into tblPregnant_woman(Education,AltMobileNo,IsPregnant,PWGUID,HHGUID,HHFamilyMemberGUID,PWName,ANMID,PWImage,AshaID,LMPDate,EDDDate,PWRegistrationDate,Regwithin12weeks,RegweeksElaspsed,HusbandName,Husband_GUID,MobileNo,MotherMCTSID,IFSCCode,Accountno,JSYBenificiaryYN,JSYRegDate,JSYPaymentReceivedYN,PWDOB,PWAgeYears,PWAgeRefDate,PWWeight,PWBloodGroup,PastIllnessYN,TotalPregnancy,LastPregnancyResult,LastPregnancyComplication,LTLPregnancyResult,LTLPregnancyomplication,PWHeight,LastPregDeliveryPlace,LasttolastPregDeliveryPlace,ExpFacilityforDelivery,ExpFacilityforDeliveryName,VDRLTestYN,VDRLResult,HIVTestYN,HIVResult,Visit1Date,Visit2Date,Visit3Date,Visit4Date,ISAbortion,AbortionFacilityType,NoofANCVisitsDone,LastANCVisitDate,TT1Date,TT2Date,TTBoosterDate,DangerSigns,RefferedYN,DeliveryDateTime,DeliveryPlace,DeliveryConductedBy,DeliveryType,DeliveryComplication,MotherDeathCause,DeliveryOutcome,DTMFacilityDischarge,PaymentRecieved,PlaceofDeath,DateofDeath,OtherPlaceofDeath,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,IsEdited,IsUploaded,Abortion_FacilityName)values("
							+ Education
							+ ",'"
							+ AltMobileNo
							+ "',"
							+ IsPregnant
							+ ",'"
							+ PWGUID
							+ "','"
							+ HHGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWName
							+ "',"
							+ ANMID
							+ ",'"
							+ PWImage
							+ "',"
							+ AshaID
							+ ",'"
							+ LMPDate
							+ "','"
							+ EDDDate
							+ "','"
							+ PWRegistrationDate
							+ "',"
							+ Regwithin12weeks
							+ ","
							+ RegweeksElaspsed
							+ ",'"
							+ HusbandName
							+ "','"
							+ Husband_GUID
							+ "','"
							+ MobileNo
							+ "','"
							+ MotherMCTSID
							+ "','"
							+ IFSCCode
							+ "','"
							+ Accountno
							+ "',"
							+ JSYBenificiaryYN
							+ ",'"
							+ JSYRegDate
							+ "',"
							+ JSYPaymentReceivedYN
							+ ",'"
							+ PWDOB
							+ "',"
							+ PWAgeYears
							+ ",'"
							+ PWAgeRefDate
							+ "',"
							+ PWWeight
							+ ","
							+ PWBloodGroup
							+ ",'"
							+ PastIllnessYN
							+ "',"
							+ TotalPregnancy
							+ ","
							+ LastPregnancyResult
							+ ","
							+ LastPregnancyComplication
							+ ","
							+ LTLPregnancyResult
							+ ","
							+ LTLPregnancyomplication
							+ ","
							+ PWHeight
							+ ","
							+ LastPregDeliveryPlace
							+ ","
							+ LasttolastPregDeliveryPlace
							+ ",'"
							+ ExpFacilityforDelivery
							+ "','"
							+ ExpFacilityforDeliveryName
							+ "',"
							+ VDRLTestYN
							+ ",'"
							+ VDRLResult
							+ "',"
							+ HIVTestYN
							+ ",'"
							+ HIVResult
							+ "','"
							+ Visit1Date
							+ "','"
							+ Visit2Date
							+ "','"
							+ Visit3Date
							+ "','"
							+ Visit4Date
							+ "',"
							+ ISAbortion
							+ ","
							+ AbortionFacilityType
							+ ","
							+ NoofANCVisitsDone
							+ ",'"
							+ LastANCVisitDate
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ TTBoosterDate
							+ "','"
							+ DangerSigns
							+ "',"
							+ RefferedYN
							+ ",'"
							+ DeliveryDateTime
							+ "',"
							+ DeliveryPlace
							+ ",'"
							+ DeliveryConductedBy
							+ "',"
							+ DeliveryType
							+ ",'"
							+ DeliveryComplication
							+ "',"
							+ MotherDeathCause
							+ ","
							+ DeliveryOutcome
							+ ",'"
							+ DTMFacilityDischarge
							+ "','"
							+ PaymentRecieved
							+ "',"
							+ PlaceofDeath
							+ ",'"
							+ DateofDeath
							+ "','"
							+ OtherPlaceofDeath
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ",0,0," + Abortion_FacilityName + ")";
					dataProvider.executeSql(sql);
				} else {
					if (count1 == 0) {
						sql = "update tblPregnant_woman set Education="
								+ Education
								+ ", IsPregnant="
								+ IsPregnant
								+ ",HHGUID='"
								+ HHGUID
								+ "',HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID
								+ "',AltMobileNo='"
								+ AltMobileNo
								+ "',PWName='"
								+ PWName
								+ "',ANMID="
								+ ANMID
								+ ",PWImage='"
								+ PWImage
								+ "',AshaID="
								+ AshaID
								+ ",LMPDate='"
								+ LMPDate
								+ "',EDDDate='"
								+ EDDDate
								+ "',PWRegistrationDate='"
								+ PWRegistrationDate
								+ "',Regwithin12weeks="
								+ Regwithin12weeks
								+ ",RegweeksElaspsed="
								+ RegweeksElaspsed
								+ ",HusbandName='"
								+ HusbandName
								+ "',Husband_GUID='"
								+ Husband_GUID
								+ "',MobileNo='"
								+ MobileNo
								+ "',MotherMCTSID='"
								+ MotherMCTSID
								+ "',IFSCCode='"
								+ IFSCCode
								+ "',Accountno='"
								+ Accountno
								+ "',JSYBenificiaryYN="
								+ JSYBenificiaryYN
								+ ",JSYRegDate='"
								+ JSYRegDate
								+ "',JSYPaymentReceivedYN="
								+ JSYPaymentReceivedYN
								+ ",PWDOB='"
								+ PWDOB
								+ "',PWAgeYears="
								+ PWAgeYears
								+ ",PWAgeRefDate='"
								+ PWAgeRefDate
								+ "',PWWeight="
								+ PWWeight
								+ ",PWBloodGroup="
								+ PWBloodGroup
								+ ",PastIllnessYN='"
								+ PastIllnessYN
								+ "',TotalPregnancy="
								+ TotalPregnancy
								+ ",LastPregnancyResult="
								+ LastPregnancyResult
								+ ",LastPregnancyComplication="
								+ LastPregnancyComplication
								+ ",LTLPregnancyResult="
								+ LTLPregnancyResult
								+ ",LTLPregnancyomplication="
								+ LTLPregnancyomplication
								+ ",PWHeight="
								+ PWHeight
								+ ",LastPregDeliveryPlace="
								+ LastPregDeliveryPlace
								+ ",LasttolastPregDeliveryPlace="
								+ LasttolastPregDeliveryPlace
								+ ",ExpFacilityforDelivery='"
								+ ExpFacilityforDelivery
								+ "',ExpFacilityforDeliveryName='"
								+ ExpFacilityforDeliveryName
								+ "',VDRLTestYN="
								+ VDRLTestYN
								+ ",VDRLResult='"
								+ VDRLResult
								+ "',HIVTestYN="
								+ HIVTestYN
								+ ",HIVResult='"
								+ HIVResult
								+ "',Visit1Date='"
								+ Visit1Date
								+ "',Visit2Date='"
								+ Visit2Date
								+ "',Visit3Date='"
								+ Visit3Date
								+ "',Visit4Date='"
								+ Visit4Date
								+ "',ISAbortion="
								+ ISAbortion
								+ ",AbortionFacilityType="
								+ AbortionFacilityType
								+ ",NoofANCVisitsDone="
								+ NoofANCVisitsDone
								+ ",LastANCVisitDate='"
								+ LastANCVisitDate
								+ "',TT1Date='"
								+ TT1Date
								+ "',TT2Date='"
								+ TT2Date
								+ "',TTBoosterDate='"
								+ TTBoosterDate
								+ "',DangerSigns='"
								+ DangerSigns
								+ "',RefferedYN="
								+ RefferedYN
								+ ",DeliveryDateTime='"
								+ DeliveryDateTime
								+ "',DeliveryPlace="
								+ DeliveryPlace
								+ ",DeliveryConductedBy='"
								+ DeliveryConductedBy
								+ "',DeliveryType="
								+ DeliveryType
								+ ",DeliveryComplication='"
								+ DeliveryComplication
								+ "',MotherDeathCause="
								+ MotherDeathCause
								+ ",DeliveryOutcome="
								+ DeliveryOutcome
								+ ",DTMFacilityDischarge='"
								+ DTMFacilityDischarge
								+ "',PaymentRecieved='"
								+ PaymentRecieved
								+ "',PlaceofDeath="
								+ PlaceofDeath
								+ ",DateofDeath='"
								+ DateofDeath
								+ "',OtherPlaceofDeath='"
								+ OtherPlaceofDeath
								+ "',CreatedOn='"
								+ CreatedOn
								+ "',CreatedBy="
								+ CreatedBy
								+ ",UpdatedOn='"
								+ UpdatedOn
								+ "',UpdatedBy="
								+ UpdatedBy
								+ ",IsEdited=0,IsUploaded=0,Abortion_FacilityName="
								+ Abortion_FacilityName + " where PWGUID='"
								+ PWGUID + "'";
					}
				}
			}
			tbl_DatesEdArray = jsonObj.getJSONArray("tbl_DatesEd");
			for (int i = 0; i < tbl_DatesEdArray.length(); i++) {
				JSONObject DatesEd = tbl_DatesEdArray.getJSONObject(i);

				String HHSurveyGUID = "";
				String HHFamilyMemberGUID = "";
				String PWGUID = "";
				String LmpDate = "";
				String EDD = "";
				String TT1Date = "";
				String TT2Date = "";
				String AncCheckupdate1 = "";
				String AncCheckupdate3 = "";
				String AncCheckupdate2 = "";
				String AncCheckupdate4 = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int IsEdited = 0;
				int IsDeleted = 0;
				int UpdatedBy = 0;
				int AshaID = 0;
				int ANMID = 0;
				HHSurveyGUID = DatesEd.getString("HHSurveyGUID");
				HHFamilyMemberGUID = DatesEd.getString("HHFamilyMemberGUID");
				PWGUID = DatesEd.getString("PWGUID");
				LmpDate = DatesEd.getString("LmpDate");
				EDD = DatesEd.getString("EDD");
				TT1Date = Validate.changeDateFormat(DatesEd
						.getString("TT1Date"));
				TT2Date = Validate.changeDateFormat(DatesEd
						.getString("TT2Date"));
				AncCheckupdate1 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate1"));
				AncCheckupdate3 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate2"));
				AncCheckupdate2 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate3"));
				AncCheckupdate4 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate4"));
				CreatedOn = DatesEd.getString("CreatedOn");
				UpdatedOn = DatesEd.getString("UpdatedOn");
				if (DatesEd.getString("CreatedBy") != null
						&& DatesEd.getString("CreatedBy").length() > 0
						&& !DatesEd.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(DatesEd.getInt("CreatedBy"));
				}
				// if (DatesEd.getString("IsEdited") != null
				// && DatesEd.getString("IsEdited").length() > 0
				// && !DatesEd.getString("IsEdited").equalsIgnoreCase(
				// "null")) {
				// IsEdited = Integer.valueOf(DatesEd.getInt("IsEdited"));
				// }
				if (DatesEd.getString("IsDeleted") != null
						&& DatesEd.getString("IsDeleted").length() > 0
						&& !DatesEd.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(DatesEd.getInt("IsDeleted"));
				}
				if (DatesEd.getString("UpdatedBy") != null
						&& DatesEd.getString("UpdatedBy").length() > 0
						&& !DatesEd.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(DatesEd.getInt("UpdatedBy"));
				}
				if (DatesEd.getString("AshaID") != null
						&& DatesEd.getString("AshaID").length() > 0
						&& !DatesEd.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(DatesEd.getInt("AshaID"));
				}
				if (DatesEd.getString("ANMID") != null
						&& DatesEd.getString("ANMID").length() > 0
						&& !DatesEd.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(DatesEd.getInt("ANMID"));
				}

				String sqlcount = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tbl_DatesEd( HHSurveyGUID ,HHFamilyMemberGUID ,PWGUID ,LmpDate ,EDD ,TT1Date ,TT2Date ,AncCheckupdate1 ,AncCheckupdate3 ,AncCheckupdate2 ,AncCheckupdate4 ,CreatedOn ,UpdatedOn ,CreatedBy , IsEdited , IsDeleted , UpdatedBy , AshaID , ANMID)values( '"
							+ HHSurveyGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWGUID
							+ "','"
							+ LmpDate
							+ "','"
							+ EDD
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ AncCheckupdate1
							+ "','"
							+ AncCheckupdate3
							+ "','"
							+ AncCheckupdate2
							+ "','"
							+ AncCheckupdate4
							+ "','"
							+ CreatedOn
							+ "','"
							+ UpdatedOn
							+ "',"
							+ CreatedBy
							+ ","
							+ IsEdited
							+ ","
							+ IsDeleted
							+ ","
							+ UpdatedBy + "," + AshaID + "," + ANMID + ")";

				} else {
					if (count1 == 0) {
						sql = "update tbl_DatesEd set  HHSurveyGUID ='"
								+ HHSurveyGUID + "',HHFamilyMemberGUID ='"
								+ HHFamilyMemberGUID + "',LmpDate ='" + LmpDate
								+ "',EDD ='" + EDD + "',TT1Date ='" + TT1Date
								+ "',TT2Date ='" + TT2Date
								+ "',AncCheckupdate1 ='" + AncCheckupdate1
								+ "',AncCheckupdate3 ='" + AncCheckupdate2
								+ "',AncCheckupdate2 ='" + AncCheckupdate3
								+ "',AncCheckupdate4 ='" + AncCheckupdate4
								+ "',CreatedOn ='" + CreatedOn
								+ "',UpdatedOn ='" + UpdatedOn
								+ "',CreatedBy =" + CreatedBy + ", IsEdited ="
								+ IsEdited + ", IsDeleted =" + IsDeleted
								+ ", UpdatedBy =" + UpdatedBy + ", AshaID ="
								+ AshaID + ", ANMID  =" + ANMID
								+ " where PWGUID='" + PWGUID + "'";

					}
				}
				dataProvider.executeSql(sql);
			}

			iMNCHDataDownload = 1;
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();
		}
	}

	public void importtblChild(String UserName, String Password, String ashaid) {

		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblChild");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearMNCHData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblChildArray = jsonObj.getJSONArray("tblChild");
			for (int i = 0; i < tblChildArray.length(); i++) {
				JSONObject Child = tblChildArray.getJSONObject(i);

				String pw_GUID = "";
				String HHGUID = "";
				String HHFamilyMemberGUID = "";
				int Child_ID = 0;
				String ChildGUID = "";
				String MotherGUID = "";
				String Date_Of_Registration = "";
				String Child_dob = "";
				String Birth_time = "";
				int Gender = 0;
				float Wt_of_child = 0;
				int place_of_birth = 0;
				String preTerm_fullTerm = "";
				int mother_status = 0;
				int child_status = 0;
				String mother_death_dt = "";
				String child_death_dt = "";
				String child_mcts_id = "";
				String child_name = "";
				String cried_after_birth = "";
				int breastfeeding_within1H = 0;
				String Exclusive_BF = "";
				String complementry_BF = "";
				String bcg = "";
				String opv1 = "";
				String dpt1 = "";
				String hepb1 = "";
				String opv2 = "";
				String dpt2 = "";
				String hepb2 = "";
				String opv3 = "";
				String dpt3 = "";
				String hepb3 = "";
				String measeals = "";
				String vitaminA = "";
				String created_on = "";
				int created_by = 0;
				String modified_on = "";
				int modified_by = 0;
				int AshaID = 0;
				int ANMID = 0;
				int IsEdited = 0;
				int IsUploaded = 0;
				int FacilityType = 0;
				String Facility = "";
				String opv4 = "";
				String hepb4 = "";
				String Pentavalent1 = "";
				String Pentavalent2 = "";
				String Pentavalent3 = "";
				String IPV = "";
				String DPTBooster = "";
				String OPVBooster = "";
				String MeaslesTwoDose = "";
				String VitaminAtwo = "";
				String DPTBoostertwo = "";
				String ChildTT = "";
				// add jitendra
				String JEVaccine1 = "";
				String JEVaccine2 = "";
				String VitaminA3 = "";
				String VitaminA4 = "";
				String VitaminA5 = "";
				String VitaminA6 = "";
				String VitaminA7 = "";
				String VitaminA8 = "";
				String VitaminA9 = "";
				String TT2 = "";

				Facility = Child.getString("Facility");
				opv4 = Child.getString("opv4");
				hepb4 = Child.getString("hepb4");
				Pentavalent1 = Child.getString("Pentavalent1");
				Pentavalent2 = Child.getString("Pentavalent2");
				Pentavalent3 = Child.getString("Pentavalent3");
				IPV = Child.getString("IPV");
				DPTBooster = Child.getString("DPTBooster");
				OPVBooster = Child.getString("OPVBooster");
				MeaslesTwoDose = Child.getString("MeaslesTwoDose");
				VitaminAtwo = Child.getString("VitaminAtwo");
				DPTBoostertwo = Child.getString("DPTBoostertwo");
				ChildTT = Child.getString("ChildTT");
				pw_GUID = Child.getString("pw_GUID");
				HHGUID = Child.getString("HHGUID");
				HHFamilyMemberGUID = Child.getString("HHFamilyMemberGUID");
				ChildGUID = Child.getString("ChildGUID");
				MotherGUID = Child.getString("MotherGUID");
				Date_Of_Registration = Child.getString("Date_Of_Registration");
				Child_dob = Child.getString("Child_dob");
				Birth_time = Child.getString("Birth_time");
				preTerm_fullTerm = Child.getString("preTerm_fullTerm");
				mother_death_dt = Child.getString("mother_death_dt");
				child_death_dt = Child.getString("child_death_dt");
				child_mcts_id = Child.getString("child_mcts_id");
				child_name = Child.getString("child_name");
				cried_after_birth = Child.getString("cried_after_birth");
				Exclusive_BF = Child.getString("Exclusive_BF");
				complementry_BF = Child.getString("complementry_BF");
				bcg = Child.getString("bcg");
				opv1 = Child.getString("opv1");
				dpt1 = Child.getString("dpt1");
				hepb1 = Child.getString("hepb1");
				opv2 = Child.getString("opv2");
				dpt2 = Child.getString("dpt2");
				hepb2 = Child.getString("hepb2");
				opv3 = Child.getString("opv3");
				dpt3 = Child.getString("dpt3");
				hepb3 = Child.getString("hepb3");
				measeals = Child.getString("measeals");
				vitaminA = Child.getString("vitaminA");
				created_on = Child.getString("created_on");
				modified_on = Child.getString("modified_on");
				JEVaccine1 = Child.getString("JEVaccine1");
				JEVaccine2 = Child.getString("JEVaccine2");
				VitaminA3 = Child.getString("VitaminA3");
				VitaminA4 = Child.getString("VitaminA4");
				VitaminA5 = Child.getString("VitaminA5");
				VitaminA6 = Child.getString("VitaminA6");
				VitaminA7 = Child.getString("VitaminA7");
				VitaminA8 = Child.getString("VitaminA8");
				VitaminA9 = Child.getString("VitaminA9");
				TT2 = Child.getString("TT2");

				if (Child.getString("Child_ID") != null
						&& Child.getString("Child_ID").length() > 0
						&& !Child.getString("Child_ID")
								.equalsIgnoreCase("null")) {
					Child_ID = Integer.valueOf(Child.getInt("Child_ID"));
				}
				if (Child.getString("Gender") != null
						&& Child.getString("Gender").length() > 0
						&& !Child.getString("Gender").equalsIgnoreCase("null")) {
					Gender = Integer.valueOf(Child.getInt("Gender"));
				}
				if (Child.getString("Wt_of_child") != null
						&& Child.getString("Wt_of_child").length() > 0
						&& !Child.getString("Wt_of_child").equalsIgnoreCase(
								"null")) {
					Wt_of_child = Float.valueOf(Child.getInt("Wt_of_child"));
				}
				if (Child.getString("place_of_birth") != null
						&& Child.getString("place_of_birth").length() > 0
						&& !Child.getString("place_of_birth").equalsIgnoreCase(
								"null")) {
					place_of_birth = Integer.valueOf(Child
							.getInt("place_of_birth"));
				}
				if (Child.getString("mother_status") != null
						&& Child.getString("mother_status").length() > 0
						&& !Child.getString("mother_status").equalsIgnoreCase(
								"null")) {
					mother_status = Integer.valueOf(Child
							.getInt("mother_status"));
				}
				if (Child.getString("child_status") != null
						&& Child.getString("child_status").length() > 0
						&& !Child.getString("child_status").equalsIgnoreCase(
								"null")) {
					child_status = Integer
							.valueOf(Child.getInt("child_status"));
				}
				if (Child.getString("breastfeeding_within1H") != null
						&& Child.getString("breastfeeding_within1H").length() > 0
						&& !Child.getString("breastfeeding_within1H")
								.equalsIgnoreCase("null")) {
					breastfeeding_within1H = Integer.valueOf(Child
							.getInt("breastfeeding_within1H"));
				}
				if (Child.getString("created_by") != null
						&& Child.getString("created_by").length() > 0
						&& !Child.getString("created_by").equalsIgnoreCase(
								"null")) {
					created_by = Integer.valueOf(Child.getInt("created_by"));
				}
				if (Child.getString("modified_by") != null
						&& Child.getString("modified_by").length() > 0
						&& !Child.getString("modified_by").equalsIgnoreCase(
								"null")) {
					modified_by = Integer.valueOf(Child.getInt("modified_by"));
				}
				if (Child.getString("AshaID") != null
						&& Child.getString("AshaID").length() > 0
						&& !Child.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(Child.getInt("AshaID"));
				}
				if (Child.getString("ANMID") != null
						&& Child.getString("ANMID").length() > 0
						&& !Child.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(Child.getInt("ANMID"));
				}
				if (Child.getString("FacilityType") != null
						&& Child.getString("FacilityType").length() > 0
						&& !Child.getString("FacilityType").equalsIgnoreCase(
								"null")) {
					FacilityType = Integer
							.valueOf(Child.getInt("FacilityType"));
				}
				String sqlcount = "select count(*) from tblChild  where ChildGUID='"
						+ ChildGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblChild  where ChildGUID='"
						+ ChildGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tblChild(AshaID,ANMID,pw_GUID,Facility,opv4,hepb4,Pentavalent1,Pentavalent2,Pentavalent3,IPV,DPTBooster,OPVBooster,MeaslesTwoDose,VitaminAtwo,DPTBoostertwo,ChildTT,FacilityType,HHGUID,HHFamilyMemberGUID,Child_ID,ChildGUID,MotherGUID,Date_Of_Registration,Child_dob,Birth_time,Gender,Wt_of_child,place_of_birth,preTerm_fullTerm,mother_status,child_status,mother_death_dt,child_death_dt,child_mcts_id,child_name,cried_after_birth,breastfeeding_within1H,Exclusive_BF,complementry_BF,bcg,opv1,dpt1,hepb1,opv2,dpt2,hepb2,opv3,dpt3,hepb3,measeals,vitaminA,created_on,created_by,modified_on,modified_by,IsEdited,IsUploaded,JEVaccine1,JEVaccine2,VitaminA3,VitaminA4,VitaminA5,VitaminA6,VitaminA7,VitaminA8,VitaminA9,TT2)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ pw_GUID
							+ "',	'"
							+ Facility
							+ "',	'"
							+ opv4
							+ "',	'"
							+ hepb4
							+ "','"
							+ Pentavalent1
							+ "',	'"
							+ Pentavalent2
							+ "',	'"
							+ Pentavalent3
							+ "',		'"
							+ IPV
							+ "',	'"
							+ DPTBooster
							+ "','"
							+ OPVBooster
							+ "',	'"
							+ MeaslesTwoDose
							+ "','"
							+ VitaminAtwo
							+ "',	'"
							+ DPTBoostertwo
							+ "',	'"
							+ ChildTT
							+ "',"
							+ FacilityType
							+ ",'"
							+ HHGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "',"
							+ Child_ID
							+ ",'"
							+ ChildGUID
							+ "','"
							+ MotherGUID
							+ "','"
							+ Date_Of_Registration
							+ "','"
							+ Child_dob
							+ "','"
							+ Birth_time
							+ "',"
							+ Gender
							+ ","
							+ Wt_of_child
							+ ","
							+ place_of_birth
							+ ",'"
							+ preTerm_fullTerm
							+ "',"
							+ mother_status
							+ ","
							+ child_status
							+ ",'"
							+ mother_death_dt
							+ "','"
							+ child_death_dt
							+ "','"
							+ child_mcts_id
							+ "','"
							+ child_name
							+ "','"
							+ cried_after_birth
							+ "',"
							+ breastfeeding_within1H
							+ ",'"
							+ Exclusive_BF
							+ "','"
							+ complementry_BF
							+ "','"
							+ bcg
							+ "','"
							+ opv1
							+ "','"
							+ dpt1
							+ "','"
							+ hepb1
							+ "','"
							+ opv2
							+ "','"
							+ dpt2
							+ "','"
							+ hepb2
							+ "','"
							+ opv3
							+ "','"
							+ dpt3
							+ "','"
							+ hepb3
							+ "','"
							+ measeals
							+ "','"
							+ vitaminA
							+ "','"
							+ created_on
							+ "',"
							+ created_by
							+ ",'"
							+ modified_on
							+ "',"
							+ modified_by
							+ ",0,0, '"
							+ JEVaccine1
							+ "','"
							+ JEVaccine2
							+ "','"
							+ VitaminA3
							+ "','"
							+ VitaminA4
							+ "','"
							+ VitaminA5
							+ "','"
							+ VitaminA6
							+ "','"
							+ VitaminA7
							+ "','"
							+ VitaminA8
							+ "','"
							+ VitaminA9
							+ "','"
							+ TT2
							+ "')";
					dataProvider.executeSql(sql);

				} else {
					if (count1 == 0) {
						sql = "update  tblChild set pw_GUID='" + pw_GUID
								+ "',HHGUID='" + HHGUID
								+ "',HHFamilyMemberGUID='" + HHFamilyMemberGUID
								+ "',Child_ID=" + Child_ID + ",MotherGUID='"
								+ MotherGUID + "',Date_Of_Registration='"
								+ Date_Of_Registration + "',Child_dob='"
								+ Child_dob + "',Birth_time='" + Birth_time
								+ "',Gender=" + Gender + ",Wt_of_child="
								+ Wt_of_child + ",place_of_birth="
								+ place_of_birth + ",preTerm_fullTerm='"
								+ preTerm_fullTerm + "',mother_status="
								+ mother_status + ",child_status="
								+ child_status + ",mother_death_dt='"
								+ mother_death_dt + "',child_death_dt='"
								+ child_death_dt + "',child_mcts_id='"
								+ child_mcts_id + "',child_name='" + child_name
								+ "',cried_after_birth='" + cried_after_birth
								+ "',breastfeeding_within1H="
								+ breastfeeding_within1H + ",Exclusive_BF='"
								+ Exclusive_BF + "',complementry_BF='"
								+ complementry_BF + "',bcg='" + bcg
								+ "',opv1='" + opv1 + "',dpt1='" + dpt1
								+ "',hepb1='" + hepb1 + "',opv2='" + opv2
								+ "',dpt2='" + dpt2 + "',hepb2='" + hepb2
								+ "',opv3='" + opv3 + "',dpt3='" + dpt3
								+ "',hepb3='" + hepb3 + "',measeals='"
								+ measeals + "',vitaminA='" + vitaminA
								+ "',created_on='" + created_on
								+ "',created_by=" + created_by
								+ ",modified_on='" + modified_on
								+ "',modified_by=" + modified_by + " ,AshaID="
								+ AshaID + ",Facility='" + Facility
								+ "',	opv4='" + opv4 + "',	hepb4='" + hepb4
								+ "',Pentavalent1='" + Pentavalent1
								+ "',	Pentavalent2='" + Pentavalent2
								+ "',	Pentavalent3='" + Pentavalent3
								+ "',		IPV='" + IPV + "',	DPTBooster='"
								+ DPTBooster + "',OPVBooster='" + OPVBooster
								+ "',MeaslesTwoDose=	'" + MeaslesTwoDose
								+ "',VitaminAtwo='" + VitaminAtwo
								+ "',	DPTBoostertwo='" + DPTBoostertwo
								+ "',ChildTT=	'" + ChildTT + "',ANMID=" + ANMID
								+ ",FacilityType=" + FacilityType
								+ ",JEVaccine1='" + JEVaccine1
								+ "',JEVaccine2='" + JEVaccine2
								+ "',VitaminA3='" + VitaminA3 + "',VitaminA4='"
								+ VitaminA4 + "',VitaminA5='" + VitaminA5
								+ "',VitaminA6='" + VitaminA6 + "',VitaminA7='"
								+ VitaminA7 + "',VitaminA8='" + VitaminA8
								+ "',VitaminA9='" + VitaminA9 + "',TT2='" + TT2
								+ "',IsEdited=0,IsUploaded=0 where ChildGUID='"
								+ ChildGUID + "'";
						dataProvider.executeSql(sql);
					}
				}
			}

			iMNCHDataDownload = 1;
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();
		}

	}

	public void importtblANCVisit(String UserName, String Password,
			String ashaid) {

		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblANCVisit");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearMNCHData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			TblANCVisitArray = jsonObj.getJSONArray("tblANCVisit");
			for (int i = 0; i < TblANCVisitArray.length(); i++) {
				JSONObject ANCVisit = TblANCVisitArray.getJSONObject(i);

				String PWGUID = "";
				String VisitGUID = "";
				int ByANMID = 0;
				int ByAshaID = 0;
				String MCTSID = "";
				int Visit_No = 0;
				int Trimester = 0;
				String VisitDueDate = "";
				String CheckupVisitDate = "";
				int CheckupPlace = 0;
				double BirthWeight = 0;
				int BP = 0;
				String BPResult = "";
				double Hemoglobin = 0;
				int UrineTest = 0;
				int UrineSugar = 0;
				int UrineAlbumin = 0;
				String TTfirstDoseDate = "";
				String TTsecondDoseDate = "";
				String TTBoosterDate = "";
				int VDRLTest = 0;
				int HIVTest = 0;
				int UltraSound = 0;
				int UltraSoundConductedby = 0;
				int IFARecieved = 0;
				int NumberIFARecieved = 0;
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				int UltrasoundResult = 0;
				String HomeVisitDate = "";
				int TT1TT2last2yr = 0;
				PWGUID = ANCVisit.getString("PWGUID");
				VisitGUID = ANCVisit.getString("VisitGUID");
				MCTSID = ANCVisit.getString("MCTSID");
				VisitDueDate = ANCVisit.getString("VisitDueDate");
				CheckupVisitDate = ANCVisit.getString("CheckupVisitDate");
				BPResult = ANCVisit.getString("BPResult");
				TTfirstDoseDate = ANCVisit.getString("TTfirstDoseDate");
				TTsecondDoseDate = ANCVisit.getString("TTsecondDoseDate");
				TTBoosterDate = ANCVisit.getString("TTboosterDate");
				CreatedOn = ANCVisit.getString("CreatedOn");
				UpdatedOn = ANCVisit.getString("UpdatedOn");
				HomeVisitDate = ANCVisit.getString("HomeVisitDate");

				if (ANCVisit.getString("ByANMID") != null
						&& ANCVisit.getString("ByANMID").length() > 0
						&& !ANCVisit.getString("ByANMID").equalsIgnoreCase(
								"null")) {
					ByANMID = Integer.valueOf(ANCVisit.getInt("ByANMID"));
				}
				if (ANCVisit.getString("ByAshaID") != null
						&& ANCVisit.getString("ByAshaID").length() > 0
						&& !ANCVisit.getString("ByAshaID").equalsIgnoreCase(
								"null")) {
					ByAshaID = Integer.valueOf(ANCVisit.getInt("ByAshaID"));
				}
				if (ANCVisit.getString("Visit_No") != null
						&& ANCVisit.getString("Visit_No").length() > 0
						&& !ANCVisit.getString("Visit_No").equalsIgnoreCase(
								"null")) {
					Visit_No = Integer.valueOf(ANCVisit.getInt("Visit_No"));
				}
				if (ANCVisit.getString("Trimester") != null
						&& ANCVisit.getString("Trimester").length() > 0
						&& !ANCVisit.getString("Trimester").equalsIgnoreCase(
								"null")) {
					Trimester = Integer.valueOf(ANCVisit.getInt("Trimester"));
				}
				if (ANCVisit.getString("CheckupPlace") != null
						&& ANCVisit.getString("CheckupPlace").length() > 0
						&& !ANCVisit.getString("CheckupPlace")
								.equalsIgnoreCase("null")) {
					CheckupPlace = Integer.valueOf(ANCVisit
							.getInt("CheckupPlace"));
				}
				if (ANCVisit.getString("BirthWeight") != null
						&& ANCVisit.getString("BirthWeight").length() > 0
						&& !ANCVisit.getString("BirthWeight").equalsIgnoreCase(
								"null")) {
					BirthWeight = Double
							.valueOf(ANCVisit.getInt("BirthWeight"));
				}
				if (ANCVisit.getString("BP") != null
						&& ANCVisit.getString("BP").length() > 0
						&& !ANCVisit.getString("BP").equalsIgnoreCase("null")) {
					BP = Integer.valueOf(ANCVisit.getInt("BP"));
				}
				if (ANCVisit.getString("Hemoglobin") != null
						&& ANCVisit.getString("Hemoglobin").length() > 0
						&& !ANCVisit.getString("Hemoglobin").equalsIgnoreCase(
								"null")) {
					Hemoglobin = Double.valueOf(ANCVisit.getInt("Hemoglobin"));
				}
				if (ANCVisit.getString("UrineTest") != null
						&& ANCVisit.getString("UrineTest").length() > 0
						&& !ANCVisit.getString("UrineTest").equalsIgnoreCase(
								"null")) {
					UrineTest = Integer.valueOf(ANCVisit.getInt("UrineTest"));
				}
				if (ANCVisit.getString("UrineSugar") != null
						&& ANCVisit.getString("UrineSugar").length() > 0
						&& !ANCVisit.getString("UrineSugar").equalsIgnoreCase(
								"null")) {
					UrineSugar = Integer.valueOf(ANCVisit.getInt("UrineSugar"));
				}
				if (ANCVisit.getString("UrineAlbumin") != null
						&& ANCVisit.getString("UrineAlbumin").length() > 0
						&& !ANCVisit.getString("UrineAlbumin")
								.equalsIgnoreCase("null")) {
					UrineAlbumin = Integer.valueOf(ANCVisit
							.getInt("UrineAlbumin"));
				}
				if (ANCVisit.getString("VDRLTest") != null
						&& ANCVisit.getString("VDRLTest").length() > 0
						&& !ANCVisit.getString("VDRLTest").equalsIgnoreCase(
								"null")) {
					VDRLTest = Integer.valueOf(ANCVisit.getInt("VDRLTest"));
				}
				if (ANCVisit.getString("HIVTest") != null
						&& ANCVisit.getString("HIVTest").length() > 0
						&& !ANCVisit.getString("HIVTest").equalsIgnoreCase(
								"null")) {
					HIVTest = Integer.valueOf(ANCVisit.getInt("HIVTest"));
				}
				if (ANCVisit.getString("UltraSound") != null
						&& ANCVisit.getString("UltraSound").length() > 0
						&& !ANCVisit.getString("UltraSound").equalsIgnoreCase(
								"null")) {
					UltraSound = Integer.valueOf(ANCVisit.getInt("UltraSound"));
				}
				if (ANCVisit.getString("UltraSoundConductedby") != null
						&& ANCVisit.getString("UltraSoundConductedby").length() > 0
						&& !ANCVisit.getString("UltraSoundConductedby")
								.equalsIgnoreCase("null")) {
					UltraSoundConductedby = Integer.valueOf(ANCVisit
							.getInt("UltraSoundConductedby"));
				}
				if (ANCVisit.getString("IFARecieved") != null
						&& ANCVisit.getString("IFARecieved").length() > 0
						&& !ANCVisit.getString("IFARecieved").equalsIgnoreCase(
								"null")) {
					IFARecieved = Integer.valueOf(ANCVisit
							.getInt("IFARecieved"));
				}
				if (ANCVisit.getString("NumberIFARecieved") != null
						&& ANCVisit.getString("NumberIFARecieved").length() > 0
						&& !ANCVisit.getString("NumberIFARecieved")
								.equalsIgnoreCase("null")) {
					NumberIFARecieved = Integer.valueOf(ANCVisit
							.getInt("NumberIFARecieved"));
				}
				if (ANCVisit.getString("CreatedBy") != null
						&& ANCVisit.getString("CreatedBy").length() > 0
						&& !ANCVisit.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(ANCVisit.getInt("CreatedBy"));
				}
				if (ANCVisit.getString("UpdatedBy") != null
						&& ANCVisit.getString("UpdatedBy").length() > 0
						&& !ANCVisit.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(ANCVisit.getInt("UpdatedBy"));
				}
				if (ANCVisit.getString("UltrasoundResult") != null
						&& ANCVisit.getString("UltrasoundResult").length() > 0
						&& !ANCVisit.getString("UltrasoundResult")
								.equalsIgnoreCase("null")) {
					UltrasoundResult = Integer.valueOf(ANCVisit
							.getInt("UltrasoundResult"));
				}
				if (ANCVisit.getString("TT1TT2last2yr") != null
						&& ANCVisit.getString("TT1TT2last2yr").length() > 0
						&& !ANCVisit.getString("TT1TT2last2yr")
								.equalsIgnoreCase("null")) {
					TT1TT2last2yr = Integer.valueOf(ANCVisit
							.getInt("TT1TT2last2yr"));
				}
				String sqlcount = "select count(*) from TblANCVisit  where VisitGUID='"
						+ VisitGUID + "' and PWGUID='" + PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from TblANCVisit  where VisitGUID='"
						+ VisitGUID
						+ "' and PWGUID='"
						+ PWGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into TblANCVisit(TT1TT2last2yr,PWGUID,VisitGUID,ByANMID,ByAshaID,MCTSID,Visit_No,Trimester,VisitDueDate,CheckupVisitDate,CheckupPlace,BirthWeight,BP,BPResult,Hemoglobin,UrineTest,UrineSugar,UrineAlbumin,TTfirstDoseDate,TTsecondDoseDate,TTBoosterDate,VDRLTest,HIVTest,UltraSound,UltraSoundConductedby,IFARecieved,NumberIFARecieved,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,UltrasoundResult,HomeVisitDate,IsEdited,IsUploaded)values("
							+ TT1TT2last2yr
							+ ",'"
							+ PWGUID
							+ "','"
							+ VisitGUID
							+ "',"
							+ ByANMID
							+ ","
							+ ByAshaID
							+ ",'"
							+ MCTSID
							+ "',"
							+ Visit_No
							+ ","
							+ Trimester
							+ ",'"
							+ VisitDueDate
							+ "','"
							+ CheckupVisitDate
							+ "',"
							+ CheckupPlace
							+ ","
							+ BirthWeight
							+ ","
							+ BP
							+ ",'"
							+ BPResult
							+ "',"
							+ Hemoglobin
							+ ","
							+ UrineTest
							+ ","
							+ UrineSugar
							+ ","
							+ UrineAlbumin
							+ ",'"
							+ TTfirstDoseDate
							+ "','"
							+ TTsecondDoseDate
							+ "','"
							+ TTBoosterDate
							+ "',"
							+ VDRLTest
							+ ","
							+ HIVTest
							+ ","
							+ UltraSound
							+ ","
							+ UltraSoundConductedby
							+ ","
							+ IFARecieved
							+ ","
							+ NumberIFARecieved
							+ ",'"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ","
							+ UltrasoundResult
							+ ",'"
							+ HomeVisitDate
							+ "',0,0)";
					dataProvider.executeSql(sql);
				} else {
					if (count1 == 0) {
						sql = "update TblANCVisit set TT1TT2last2yr="
								+ TT1TT2last2yr + ", ByANMID=" + ByANMID
								+ ",ByAshaID=" + ByAshaID + ",MCTSID='"
								+ MCTSID + "',Visit_No=" + Visit_No
								+ ",Trimester=" + Trimester + ",VisitDueDate='"
								+ VisitDueDate + "',CheckupVisitDate='"
								+ CheckupVisitDate + "',CheckupPlace="
								+ CheckupPlace + ",BirthWeight=" + BirthWeight
								+ ",BP=" + BP + ",BPResult='" + BPResult
								+ "',Hemoglobin=" + Hemoglobin + ",UrineTest="
								+ UrineTest + ",UrineSugar=" + UrineSugar
								+ ",UrineAlbumin=" + UrineAlbumin
								+ ",TTfirstDoseDate='" + TTfirstDoseDate
								+ "',TTsecondDoseDate='" + TTsecondDoseDate
								+ "',TTBoosterDate='" + TTBoosterDate
								+ "',VDRLTest=" + VDRLTest + ",HIVTest="
								+ HIVTest + ",UltraSound=" + UltraSound
								+ ",UltraSoundConductedby="
								+ UltraSoundConductedby + ",IFARecieved="
								+ IFARecieved + ",NumberIFARecieved="
								+ NumberIFARecieved + ",CreatedOn='"
								+ CreatedOn + "',CreatedBy=" + CreatedBy
								+ ",UpdatedOn='" + UpdatedOn
								+ "',IsEdited=0,IsUploaded=0,UpdatedBy="
								+ UpdatedBy + ",UltrasoundResult="
								+ UltrasoundResult + ",HomeVisitDate='"
								+ HomeVisitDate + "' where PWGUID='" + PWGUID
								+ "' and VisitGUID='" + VisitGUID + "'";
						dataProvider.executeSql(sql);
					}
				}
			}

			tblpregnantwomenArray = jsonObj.getJSONArray("tblPregnant_woman");
			for (int i = 0; i < tblpregnantwomenArray.length(); i++) {
				JSONObject pregnantwomen = tblpregnantwomenArray
						.getJSONObject(i);
				String AltMobileNo = "";
				String PWGUID = "";
				String HHGUID = "";
				String HHFamilyMemberGUID = "";
				String PWName = "";
				int ANMID = 0;
				String PWImage = "";
				int AshaID = 0;
				String LMPDate = "";
				String EDDDate = "";
				String PWRegistrationDate = "";
				int Regwithin12weeks = 0;
				int RegweeksElaspsed = 0;
				String HusbandName = "";
				String Husband_GUID = "";
				String MobileNo = "";
				String MotherMCTSID = "";
				String IFSCCode = "";
				String Accountno = "";
				int JSYBenificiaryYN = 0;
				String JSYRegDate = "";
				int JSYPaymentReceivedYN = 0;
				String PWDOB = "";
				int PWAgeYears = 0;
				String PWAgeRefDate = "";
				double PWWeight = 0;
				int PWBloodGroup = 0;
				String PastIllnessYN = "";
				int TotalPregnancy = 0;
				int LastPregnancyResult = 0;
				int LastPregnancyComplication = 0;
				int LTLPregnancyResult = 0;
				int LTLPregnancyomplication = 0;
				double PWHeight = 0;
				int LastPregDeliveryPlace = 0;
				int LasttolastPregDeliveryPlace = 0;
				String ExpFacilityforDelivery = "";
				String ExpFacilityforDeliveryName = "";
				int VDRLTestYN = 0;
				String VDRLResult = "";
				int HIVTestYN = 0;
				String HIVResult = "";
				String Visit1Date = "";
				String Visit2Date = "";
				String Visit3Date = "";
				String Visit4Date = "";
				int ISAbortion = 0;
				int IsPregnant = 0;
				int AbortionFacilityType = 0;
				int NoofANCVisitsDone = 0;
				String LastANCVisitDate = "";
				String TT1Date = "";
				String TT2Date = "";
				String TTBoosterDate = "";
				String DangerSigns = "";
				int RefferedYN = 0;
				String DeliveryDateTime = "";
				int DeliveryPlace = 0;
				String DeliveryConductedBy = "";
				int DeliveryType = 0;
				String DeliveryComplication = "";
				int MotherDeathCause = 0;
				int DeliveryOutcome = 0;
				String DTMFacilityDischarge = "";
				String PaymentRecieved = "";
				int PlaceofDeath = 0;
				String DateofDeath = "";
				String OtherPlaceofDeath = "";
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				int Education = 0;
				int Abortion_FacilityName = 0;
				PWGUID = pregnantwomen.getString("PWGUID");
				AltMobileNo = pregnantwomen.getString("AltMobileNo");
				HHGUID = pregnantwomen.getString("HHGUID");
				HHFamilyMemberGUID = pregnantwomen
						.getString("HHFamilyMemberGUID");
				PWName = pregnantwomen.getString("PWName");
				PWImage = pregnantwomen.getString("PWImage");
				LMPDate = pregnantwomen.getString("LMPDate");
				EDDDate = pregnantwomen.getString("EDDDate");
				PWRegistrationDate = pregnantwomen
						.getString("PWRegistrationDate");
				HusbandName = pregnantwomen.getString("HusbandName");
				Husband_GUID = pregnantwomen.getString("Husband_GUID");
				MobileNo = pregnantwomen.getString("MobileNo");
				MotherMCTSID = pregnantwomen.getString("MotherMCTSID");
				IFSCCode = pregnantwomen.getString("IFSCCode");
				Accountno = pregnantwomen.getString("Accountno");
				JSYRegDate = pregnantwomen.getString("JSYRegDate");
				PWDOB = pregnantwomen.getString("PWDOB");
				PWAgeRefDate = pregnantwomen.getString("PWAgeRefDate");
				ExpFacilityforDelivery = pregnantwomen
						.getString("ExpFacilityforDelivery");
				ExpFacilityforDeliveryName = pregnantwomen
						.getString("ExpFacilityforDeliveryName");
				VDRLResult = pregnantwomen.getString("VDRLResult");
				HIVResult = pregnantwomen.getString("HIVResult");
				Visit1Date = pregnantwomen.getString("Visit1Date");
				Visit2Date = pregnantwomen.getString("Visit2Date");
				Visit3Date = pregnantwomen.getString("Visit3Date");
				Visit4Date = pregnantwomen.getString("Visit4Date");
				LastANCVisitDate = pregnantwomen.getString("LastANCVisitDate");
				TT1Date = pregnantwomen.getString("TT1Date");
				TT2Date = pregnantwomen.getString("TT2Date");

				TTBoosterDate = pregnantwomen.getString("TTBoosterDate");
				DangerSigns = pregnantwomen.getString("DangerSigns");
				DeliveryDateTime = pregnantwomen.getString("DeliveryDateTime");
				DeliveryConductedBy = pregnantwomen
						.getString("DeliveryConductedBy");
				DeliveryComplication = pregnantwomen
						.getString("DeliveryComplication");
				DTMFacilityDischarge = pregnantwomen
						.getString("DTMFacilityDischarge");
				PaymentRecieved = pregnantwomen.getString("PaymentRecieved");
				DateofDeath = pregnantwomen.getString("DateofDeath");
				OtherPlaceofDeath = pregnantwomen
						.getString("OtherPlaceofDeath");
				CreatedOn = pregnantwomen.getString("CreatedOn");
				UpdatedOn = pregnantwomen.getString("UpdatedOn");

				if (pregnantwomen.getString("ANMID") != null
						&& pregnantwomen.getString("ANMID").length() > 0
						&& !pregnantwomen.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(pregnantwomen.getInt("ANMID"));
				}
				if (pregnantwomen.getString("AshaID") != null
						&& pregnantwomen.getString("AshaID").length() > 0
						&& !pregnantwomen.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(pregnantwomen.getInt("AshaID"));
				}
				if (pregnantwomen.getString("Regwithin12weeks") != null
						&& pregnantwomen.getString("Regwithin12weeks").length() > 0
						&& !pregnantwomen.getString("Regwithin12weeks")
								.equalsIgnoreCase("null")) {
					Regwithin12weeks = Integer.valueOf(pregnantwomen
							.getInt("Regwithin12weeks"));
				}
				if (pregnantwomen.getString("RegweeksElaspsed") != null
						&& pregnantwomen.getString("RegweeksElaspsed").length() > 0
						&& !pregnantwomen.getString("RegweeksElaspsed")
								.equalsIgnoreCase("null")) {
					RegweeksElaspsed = Integer.valueOf(pregnantwomen
							.getInt("RegweeksElaspsed"));
				}
				if (pregnantwomen.getString("JSYBenificiaryYN") != null
						&& pregnantwomen.getString("JSYBenificiaryYN").length() > 0
						&& !pregnantwomen.getString("JSYBenificiaryYN")
								.equalsIgnoreCase("null")) {
					JSYBenificiaryYN = Integer.valueOf(pregnantwomen
							.getInt("JSYBenificiaryYN"));
				}
				if (pregnantwomen.getString("JSYPaymentReceivedYN") != null
						&& pregnantwomen.getString("JSYPaymentReceivedYN")
								.length() > 0
						&& !pregnantwomen.getString("JSYPaymentReceivedYN")
								.equalsIgnoreCase("null")) {
					JSYPaymentReceivedYN = Integer.valueOf(pregnantwomen
							.getInt("JSYPaymentReceivedYN"));
				}
				if (pregnantwomen.getString("PWAgeYears") != null
						&& pregnantwomen.getString("PWAgeYears").length() > 0
						&& !pregnantwomen.getString("PWAgeYears")
								.equalsIgnoreCase("null")) {
					PWAgeYears = Integer.valueOf(pregnantwomen
							.getInt("PWAgeYears"));
				}
				if (pregnantwomen.getString("PWWeight") != null
						&& pregnantwomen.getString("PWWeight").length() > 0
						&& !pregnantwomen.getString("PWWeight")
								.equalsIgnoreCase("null")) {
					PWWeight = Double.valueOf(pregnantwomen.getInt("PWWeight"));
				}
				if (pregnantwomen.getString("PWBloodGroup") != null
						&& pregnantwomen.getString("PWBloodGroup").length() > 0
						&& !pregnantwomen.getString("PWBloodGroup")
								.equalsIgnoreCase("null")) {
					PWBloodGroup = Integer.valueOf(pregnantwomen
							.getInt("PWBloodGroup"));
				}

				PastIllnessYN = pregnantwomen.getString("PastIllnessYN");

				if (pregnantwomen.getString("TotalPregnancy") != null
						&& pregnantwomen.getString("TotalPregnancy").length() > 0
						&& !pregnantwomen.getString("TotalPregnancy")
								.equalsIgnoreCase("null")) {
					TotalPregnancy = Integer.valueOf(pregnantwomen
							.getInt("TotalPregnancy"));
				}
				if (pregnantwomen.getString("LastPregnancyResult") != null
						&& pregnantwomen.getString("LastPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LastPregnancyResult")
								.equalsIgnoreCase("null")) {
					LastPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyResult"));
				}
				if (pregnantwomen.getString("LastPregnancyComplication") != null
						&& pregnantwomen.getString("LastPregnancyComplication")
								.length() > 0
						&& !pregnantwomen
								.getString("LastPregnancyComplication")
								.equalsIgnoreCase("null")) {
					LastPregnancyComplication = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyComplication"));
				}
				if (pregnantwomen.getString("LTLPregnancyResult") != null
						&& pregnantwomen.getString("LTLPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyResult")
								.equalsIgnoreCase("null")) {
					LTLPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyResult"));
				}
				if (pregnantwomen.getString("LTLPregnancyomplication") != null
						&& pregnantwomen.getString("LTLPregnancyomplication")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyomplication")
								.equalsIgnoreCase("null")) {
					LTLPregnancyomplication = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyomplication"));
				}
				if (pregnantwomen.getString("PWHeight") != null
						&& pregnantwomen.getString("PWHeight").length() > 0
						&& !pregnantwomen.getString("PWHeight")
								.equalsIgnoreCase("null")) {
					PWHeight = Double.valueOf(pregnantwomen.getInt("PWHeight"));
				}
				if (pregnantwomen.getString("LastPregDeliveryPlace") != null
						&& pregnantwomen.getString("LastPregDeliveryPlace")
								.length() > 0
						&& !pregnantwomen.getString("LastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("LasttolastPregDeliveryPlace") != null
						&& pregnantwomen.getString(
								"LasttolastPregDeliveryPlace").length() > 0
						&& !pregnantwomen.getString(
								"LasttolastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LasttolastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LasttolastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("VDRLTestYN") != null
						&& pregnantwomen.getString("VDRLTestYN").length() > 0
						&& !pregnantwomen.getString("VDRLTestYN")
								.equalsIgnoreCase("null")) {
					VDRLTestYN = Integer.valueOf(pregnantwomen
							.getInt("VDRLTestYN"));
				}
				if (pregnantwomen.getString("HIVTestYN") != null
						&& pregnantwomen.getString("HIVTestYN").length() > 0
						&& !pregnantwomen.getString("HIVTestYN")
								.equalsIgnoreCase("null")) {
					HIVTestYN = Integer.valueOf(pregnantwomen
							.getInt("HIVTestYN"));
				}
				if (pregnantwomen.getString("ISAbortion") != null
						&& pregnantwomen.getString("ISAbortion").length() > 0
						&& !pregnantwomen.getString("ISAbortion")
								.equalsIgnoreCase("null")) {
					ISAbortion = Integer.valueOf(pregnantwomen
							.getInt("ISAbortion"));
				}
				if (pregnantwomen.getString("IsPregnant") != null
						&& pregnantwomen.getString("IsPregnant").length() > 0
						&& !pregnantwomen.getString("IsPregnant")
								.equalsIgnoreCase("null")) {
					IsPregnant = Integer.valueOf(pregnantwomen
							.getInt("IsPregnant"));
				}
				if (pregnantwomen.getString("AbortionFacilityType") != null
						&& pregnantwomen.getString("AbortionFacilityType")
								.length() > 0
						&& !pregnantwomen.getString("AbortionFacilityType")
								.equalsIgnoreCase("null")) {
					AbortionFacilityType = Integer.valueOf(pregnantwomen
							.getInt("AbortionFacilityType"));
				}
				if (pregnantwomen.getString("NoofANCVisitsDone") != null
						&& pregnantwomen.getString("NoofANCVisitsDone")
								.length() > 0
						&& !pregnantwomen.getString("NoofANCVisitsDone")
								.equalsIgnoreCase("null")) {
					NoofANCVisitsDone = Integer.valueOf(pregnantwomen
							.getInt("NoofANCVisitsDone"));
				}
				if (pregnantwomen.getString("RefferedYN") != null
						&& pregnantwomen.getString("RefferedYN").length() > 0
						&& !pregnantwomen.getString("RefferedYN")
								.equalsIgnoreCase("null")) {
					RefferedYN = Integer.valueOf(pregnantwomen
							.getInt("RefferedYN"));
				}
				if (pregnantwomen.getString("DeliveryPlace") != null
						&& pregnantwomen.getString("DeliveryPlace").length() > 0
						&& !pregnantwomen.getString("DeliveryPlace")
								.equalsIgnoreCase("null")) {
					DeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("DeliveryPlace"));
				}
				if (pregnantwomen.getString("DeliveryType") != null
						&& pregnantwomen.getString("DeliveryType").length() > 0
						&& !pregnantwomen.getString("DeliveryType")
								.equalsIgnoreCase("null")) {
					DeliveryType = Integer.valueOf(pregnantwomen
							.getInt("DeliveryType"));
				}
				if (pregnantwomen.getString("MotherDeathCause") != null
						&& pregnantwomen.getString("MotherDeathCause").length() > 0
						&& !pregnantwomen.getString("MotherDeathCause")
								.equalsIgnoreCase("null")) {
					MotherDeathCause = Integer.valueOf(pregnantwomen
							.getInt("MotherDeathCause"));
				}
				if (pregnantwomen.getString("DeliveryOutcome") != null
						&& pregnantwomen.getString("DeliveryOutcome").length() > 0
						&& !pregnantwomen.getString("DeliveryOutcome")
								.equalsIgnoreCase("null")) {
					DeliveryOutcome = Integer.valueOf(pregnantwomen
							.getInt("DeliveryOutcome"));
				}
				if (pregnantwomen.getString("PlaceofDeath") != null
						&& pregnantwomen.getString("PlaceofDeath").length() > 0
						&& !pregnantwomen.getString("PlaceofDeath")
								.equalsIgnoreCase("null")) {
					PlaceofDeath = Integer.valueOf(pregnantwomen
							.getInt("PlaceofDeath"));
				}
				if (pregnantwomen.getString("CreatedBy") != null
						&& pregnantwomen.getString("CreatedBy").length() > 0
						&& !pregnantwomen.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(pregnantwomen
							.getInt("CreatedBy"));
				}
				if (pregnantwomen.getString("UpdatedBy") != null
						&& pregnantwomen.getString("UpdatedBy").length() > 0
						&& !pregnantwomen.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(pregnantwomen
							.getInt("UpdatedBy"));
				}
				if (pregnantwomen.getString("Education") != null
						&& pregnantwomen.getString("Education").length() > 0
						&& !pregnantwomen.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(pregnantwomen
							.getInt("Education"));
				}
				if (pregnantwomen.getString("Abortion_FacilityName") != null
						&& pregnantwomen.getString("Abortion_FacilityName")
								.length() > 0
						&& !pregnantwomen.getString("Abortion_FacilityName")
								.equalsIgnoreCase("null")) {
					Abortion_FacilityName = Integer.valueOf(pregnantwomen
							.getInt("Abortion_FacilityName"));
				}

				String sqlcount = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into tblPregnant_woman(Education,AltMobileNo,IsPregnant,PWGUID,HHGUID,HHFamilyMemberGUID,PWName,ANMID,PWImage,AshaID,LMPDate,EDDDate,PWRegistrationDate,Regwithin12weeks,RegweeksElaspsed,HusbandName,Husband_GUID,MobileNo,MotherMCTSID,IFSCCode,Accountno,JSYBenificiaryYN,JSYRegDate,JSYPaymentReceivedYN,PWDOB,PWAgeYears,PWAgeRefDate,PWWeight,PWBloodGroup,PastIllnessYN,TotalPregnancy,LastPregnancyResult,LastPregnancyComplication,LTLPregnancyResult,LTLPregnancyomplication,PWHeight,LastPregDeliveryPlace,LasttolastPregDeliveryPlace,ExpFacilityforDelivery,ExpFacilityforDeliveryName,VDRLTestYN,VDRLResult,HIVTestYN,HIVResult,Visit1Date,Visit2Date,Visit3Date,Visit4Date,ISAbortion,AbortionFacilityType,NoofANCVisitsDone,LastANCVisitDate,TT1Date,TT2Date,TTBoosterDate,DangerSigns,RefferedYN,DeliveryDateTime,DeliveryPlace,DeliveryConductedBy,DeliveryType,DeliveryComplication,MotherDeathCause,DeliveryOutcome,DTMFacilityDischarge,PaymentRecieved,PlaceofDeath,DateofDeath,OtherPlaceofDeath,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,IsEdited,IsUploaded,Abortion_FacilityName)values("
							+ Education
							+ ",'"
							+ AltMobileNo
							+ "',"
							+ IsPregnant
							+ ",'"
							+ PWGUID
							+ "','"
							+ HHGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWName
							+ "',"
							+ ANMID
							+ ",'"
							+ PWImage
							+ "',"
							+ AshaID
							+ ",'"
							+ LMPDate
							+ "','"
							+ EDDDate
							+ "','"
							+ PWRegistrationDate
							+ "',"
							+ Regwithin12weeks
							+ ","
							+ RegweeksElaspsed
							+ ",'"
							+ HusbandName
							+ "','"
							+ Husband_GUID
							+ "','"
							+ MobileNo
							+ "','"
							+ MotherMCTSID
							+ "','"
							+ IFSCCode
							+ "','"
							+ Accountno
							+ "',"
							+ JSYBenificiaryYN
							+ ",'"
							+ JSYRegDate
							+ "',"
							+ JSYPaymentReceivedYN
							+ ",'"
							+ PWDOB
							+ "',"
							+ PWAgeYears
							+ ",'"
							+ PWAgeRefDate
							+ "',"
							+ PWWeight
							+ ","
							+ PWBloodGroup
							+ ",'"
							+ PastIllnessYN
							+ "',"
							+ TotalPregnancy
							+ ","
							+ LastPregnancyResult
							+ ","
							+ LastPregnancyComplication
							+ ","
							+ LTLPregnancyResult
							+ ","
							+ LTLPregnancyomplication
							+ ","
							+ PWHeight
							+ ","
							+ LastPregDeliveryPlace
							+ ","
							+ LasttolastPregDeliveryPlace
							+ ",'"
							+ ExpFacilityforDelivery
							+ "','"
							+ ExpFacilityforDeliveryName
							+ "',"
							+ VDRLTestYN
							+ ",'"
							+ VDRLResult
							+ "',"
							+ HIVTestYN
							+ ",'"
							+ HIVResult
							+ "','"
							+ Visit1Date
							+ "','"
							+ Visit2Date
							+ "','"
							+ Visit3Date
							+ "','"
							+ Visit4Date
							+ "',"
							+ ISAbortion
							+ ","
							+ AbortionFacilityType
							+ ","
							+ NoofANCVisitsDone
							+ ",'"
							+ LastANCVisitDate
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ TTBoosterDate
							+ "','"
							+ DangerSigns
							+ "',"
							+ RefferedYN
							+ ",'"
							+ DeliveryDateTime
							+ "',"
							+ DeliveryPlace
							+ ",'"
							+ DeliveryConductedBy
							+ "',"
							+ DeliveryType
							+ ",'"
							+ DeliveryComplication
							+ "',"
							+ MotherDeathCause
							+ ","
							+ DeliveryOutcome
							+ ",'"
							+ DTMFacilityDischarge
							+ "','"
							+ PaymentRecieved
							+ "',"
							+ PlaceofDeath
							+ ",'"
							+ DateofDeath
							+ "','"
							+ OtherPlaceofDeath
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ",0,0," + Abortion_FacilityName + ")";
					dataProvider.executeSql(sql);
				} else {
					if (count1 == 0) {
						sql = "update tblPregnant_woman set Education="
								+ Education
								+ ", IsPregnant="
								+ IsPregnant
								+ ",HHGUID='"
								+ HHGUID
								+ "',HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID
								+ "',AltMobileNo='"
								+ AltMobileNo
								+ "',PWName='"
								+ PWName
								+ "',ANMID="
								+ ANMID
								+ ",PWImage='"
								+ PWImage
								+ "',AshaID="
								+ AshaID
								+ ",LMPDate='"
								+ LMPDate
								+ "',EDDDate='"
								+ EDDDate
								+ "',PWRegistrationDate='"
								+ PWRegistrationDate
								+ "',Regwithin12weeks="
								+ Regwithin12weeks
								+ ",RegweeksElaspsed="
								+ RegweeksElaspsed
								+ ",HusbandName='"
								+ HusbandName
								+ "',Husband_GUID='"
								+ Husband_GUID
								+ "',MobileNo='"
								+ MobileNo
								+ "',MotherMCTSID='"
								+ MotherMCTSID
								+ "',IFSCCode='"
								+ IFSCCode
								+ "',Accountno='"
								+ Accountno
								+ "',JSYBenificiaryYN="
								+ JSYBenificiaryYN
								+ ",JSYRegDate='"
								+ JSYRegDate
								+ "',JSYPaymentReceivedYN="
								+ JSYPaymentReceivedYN
								+ ",PWDOB='"
								+ PWDOB
								+ "',PWAgeYears="
								+ PWAgeYears
								+ ",PWAgeRefDate='"
								+ PWAgeRefDate
								+ "',PWWeight="
								+ PWWeight
								+ ",PWBloodGroup="
								+ PWBloodGroup
								+ ",PastIllnessYN='"
								+ PastIllnessYN
								+ "',TotalPregnancy="
								+ TotalPregnancy
								+ ",LastPregnancyResult="
								+ LastPregnancyResult
								+ ",LastPregnancyComplication="
								+ LastPregnancyComplication
								+ ",LTLPregnancyResult="
								+ LTLPregnancyResult
								+ ",LTLPregnancyomplication="
								+ LTLPregnancyomplication
								+ ",PWHeight="
								+ PWHeight
								+ ",LastPregDeliveryPlace="
								+ LastPregDeliveryPlace
								+ ",LasttolastPregDeliveryPlace="
								+ LasttolastPregDeliveryPlace
								+ ",ExpFacilityforDelivery='"
								+ ExpFacilityforDelivery
								+ "',ExpFacilityforDeliveryName='"
								+ ExpFacilityforDeliveryName
								+ "',VDRLTestYN="
								+ VDRLTestYN
								+ ",VDRLResult='"
								+ VDRLResult
								+ "',HIVTestYN="
								+ HIVTestYN
								+ ",HIVResult='"
								+ HIVResult
								+ "',Visit1Date='"
								+ Visit1Date
								+ "',Visit2Date='"
								+ Visit2Date
								+ "',Visit3Date='"
								+ Visit3Date
								+ "',Visit4Date='"
								+ Visit4Date
								+ "',ISAbortion="
								+ ISAbortion
								+ ",AbortionFacilityType="
								+ AbortionFacilityType
								+ ",NoofANCVisitsDone="
								+ NoofANCVisitsDone
								+ ",LastANCVisitDate='"
								+ LastANCVisitDate
								+ "',TT1Date='"
								+ TT1Date
								+ "',TT2Date='"
								+ TT2Date
								+ "',TTBoosterDate='"
								+ TTBoosterDate
								+ "',DangerSigns='"
								+ DangerSigns
								+ "',RefferedYN="
								+ RefferedYN
								+ ",DeliveryDateTime='"
								+ DeliveryDateTime
								+ "',DeliveryPlace="
								+ DeliveryPlace
								+ ",DeliveryConductedBy='"
								+ DeliveryConductedBy
								+ "',DeliveryType="
								+ DeliveryType
								+ ",DeliveryComplication='"
								+ DeliveryComplication
								+ "',MotherDeathCause="
								+ MotherDeathCause
								+ ",DeliveryOutcome="
								+ DeliveryOutcome
								+ ",DTMFacilityDischarge='"
								+ DTMFacilityDischarge
								+ "',PaymentRecieved='"
								+ PaymentRecieved
								+ "',PlaceofDeath="
								+ PlaceofDeath
								+ ",DateofDeath='"
								+ DateofDeath
								+ "',OtherPlaceofDeath='"
								+ OtherPlaceofDeath
								+ "',CreatedOn='"
								+ CreatedOn
								+ "',CreatedBy="
								+ CreatedBy
								+ ",UpdatedOn='"
								+ UpdatedOn
								+ "',UpdatedBy="
								+ UpdatedBy
								+ ",IsEdited=0,IsUploaded=0,Abortion_FacilityName="
								+ Abortion_FacilityName + " where PWGUID='"
								+ PWGUID + "'";
					}
				}
			}
			tbl_DatesEdArray = jsonObj.getJSONArray("tbl_DatesEd");
			for (int i = 0; i < tbl_DatesEdArray.length(); i++) {
				JSONObject DatesEd = tbl_DatesEdArray.getJSONObject(i);

				String HHSurveyGUID = "";
				String HHFamilyMemberGUID = "";
				String PWGUID = "";
				String LmpDate = "";
				String EDD = "";
				String TT1Date = "";
				String TT2Date = "";
				String AncCheckupdate1 = "";
				String AncCheckupdate3 = "";
				String AncCheckupdate2 = "";
				String AncCheckupdate4 = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int IsEdited = 0;
				int IsDeleted = 0;
				int UpdatedBy = 0;
				int AshaID = 0;
				int ANMID = 0;
				HHSurveyGUID = DatesEd.getString("HHSurveyGUID");
				HHFamilyMemberGUID = DatesEd.getString("HHFamilyMemberGUID");
				PWGUID = DatesEd.getString("PWGUID");
				LmpDate = DatesEd.getString("LmpDate");
				EDD = DatesEd.getString("EDD");
				TT1Date = Validate.changeDateFormat(DatesEd
						.getString("TT1Date"));
				TT2Date = Validate.changeDateFormat(DatesEd
						.getString("TT2Date"));
				AncCheckupdate1 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate1"));
				AncCheckupdate3 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate2"));
				AncCheckupdate2 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate3"));
				AncCheckupdate4 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate4"));
				CreatedOn = DatesEd.getString("CreatedOn");
				UpdatedOn = DatesEd.getString("UpdatedOn");
				if (DatesEd.getString("CreatedBy") != null
						&& DatesEd.getString("CreatedBy").length() > 0
						&& !DatesEd.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(DatesEd.getInt("CreatedBy"));
				}
				// if (DatesEd.getString("IsEdited") != null
				// && DatesEd.getString("IsEdited").length() > 0
				// && !DatesEd.getString("IsEdited").equalsIgnoreCase(
				// "null")) {
				// IsEdited = Integer.valueOf(DatesEd.getInt("IsEdited"));
				// }
				if (DatesEd.getString("IsDeleted") != null
						&& DatesEd.getString("IsDeleted").length() > 0
						&& !DatesEd.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(DatesEd.getInt("IsDeleted"));
				}
				if (DatesEd.getString("UpdatedBy") != null
						&& DatesEd.getString("UpdatedBy").length() > 0
						&& !DatesEd.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(DatesEd.getInt("UpdatedBy"));
				}
				if (DatesEd.getString("AshaID") != null
						&& DatesEd.getString("AshaID").length() > 0
						&& !DatesEd.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(DatesEd.getInt("AshaID"));
				}
				if (DatesEd.getString("ANMID") != null
						&& DatesEd.getString("ANMID").length() > 0
						&& !DatesEd.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(DatesEd.getInt("ANMID"));
				}

				String sqlcount = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tbl_DatesEd( HHSurveyGUID ,HHFamilyMemberGUID ,PWGUID ,LmpDate ,EDD ,TT1Date ,TT2Date ,AncCheckupdate1 ,AncCheckupdate3 ,AncCheckupdate2 ,AncCheckupdate4 ,CreatedOn ,UpdatedOn ,CreatedBy , IsEdited , IsDeleted , UpdatedBy , AshaID , ANMID)values( '"
							+ HHSurveyGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWGUID
							+ "','"
							+ LmpDate
							+ "','"
							+ EDD
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ AncCheckupdate1
							+ "','"
							+ AncCheckupdate3
							+ "','"
							+ AncCheckupdate2
							+ "','"
							+ AncCheckupdate4
							+ "','"
							+ CreatedOn
							+ "','"
							+ UpdatedOn
							+ "',"
							+ CreatedBy
							+ ","
							+ IsEdited
							+ ","
							+ IsDeleted
							+ ","
							+ UpdatedBy + "," + AshaID + "," + ANMID + ")";

				} else {
					if (count1 == 0) {
						sql = "update tbl_DatesEd set  HHSurveyGUID ='"
								+ HHSurveyGUID + "',HHFamilyMemberGUID ='"
								+ HHFamilyMemberGUID + "',LmpDate ='" + LmpDate
								+ "',EDD ='" + EDD + "',TT1Date ='" + TT1Date
								+ "',TT2Date ='" + TT2Date
								+ "',AncCheckupdate1 ='" + AncCheckupdate1
								+ "',AncCheckupdate3 ='" + AncCheckupdate2
								+ "',AncCheckupdate2 ='" + AncCheckupdate3
								+ "',AncCheckupdate4 ='" + AncCheckupdate4
								+ "',CreatedOn ='" + CreatedOn
								+ "',UpdatedOn ='" + UpdatedOn
								+ "',CreatedBy =" + CreatedBy + ", IsEdited ="
								+ IsEdited + ", IsDeleted =" + IsDeleted
								+ ", UpdatedBy =" + UpdatedBy + ", AshaID ="
								+ AshaID + ", ANMID  =" + ANMID
								+ " where PWGUID='" + PWGUID + "'";

					}
				}
				dataProvider.executeSql(sql);
			}

			iMNCHDataDownload = 1;
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();
		}

	}

	public void importtblPregnant_woman(String UserName, String Password,
			String ashaid) {

		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblPregnant_woman");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearMNCHData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblpregnantwomenArray = jsonObj.getJSONArray("tblPregnant_woman");
			for (int i = 0; i < tblpregnantwomenArray.length(); i++) {
				JSONObject pregnantwomen = tblpregnantwomenArray
						.getJSONObject(i);
				String AltMobileNo = "";
				String PWGUID = "";
				String HHGUID = "";
				String HHFamilyMemberGUID = "";
				String PWName = "";
				int ANMID = 0;
				String PWImage = "";
				int AshaID = 0;
				String LMPDate = "";
				String EDDDate = "";
				String PWRegistrationDate = "";
				int Regwithin12weeks = 0;
				int RegweeksElaspsed = 0;
				String HusbandName = "";
				String Husband_GUID = "";
				String MobileNo = "";
				String MotherMCTSID = "";
				String IFSCCode = "";
				String Accountno = "";
				int JSYBenificiaryYN = 0;
				String JSYRegDate = "";
				int JSYPaymentReceivedYN = 0;
				String PWDOB = "";
				int PWAgeYears = 0;
				String PWAgeRefDate = "";
				double PWWeight = 0;
				int PWBloodGroup = 0;
				String PastIllnessYN = "";
				int TotalPregnancy = 0;
				int LastPregnancyResult = 0;
				int LastPregnancyComplication = 0;
				int LTLPregnancyResult = 0;
				int LTLPregnancyomplication = 0;
				double PWHeight = 0;
				int LastPregDeliveryPlace = 0;
				int LasttolastPregDeliveryPlace = 0;
				String ExpFacilityforDelivery = "";
				String ExpFacilityforDeliveryName = "";
				int VDRLTestYN = 0;
				String VDRLResult = "";
				int HIVTestYN = 0;
				String HIVResult = "";
				String Visit1Date = "";
				String Visit2Date = "";
				String Visit3Date = "";
				String Visit4Date = "";
				int ISAbortion = 0;
				int IsPregnant = 0;
				int AbortionFacilityType = 0;
				int NoofANCVisitsDone = 0;
				String LastANCVisitDate = "";
				String TT1Date = "";
				String TT2Date = "";
				String TTBoosterDate = "";
				String DangerSigns = "";
				int RefferedYN = 0;
				String DeliveryDateTime = "";
				int DeliveryPlace = 0;
				String DeliveryConductedBy = "";
				int DeliveryType = 0;
				String DeliveryComplication = "";
				int MotherDeathCause = 0;
				int DeliveryOutcome = 0;
				String DTMFacilityDischarge = "";
				String PaymentRecieved = "";
				int PlaceofDeath = 0;
				String DateofDeath = "";
				String OtherPlaceofDeath = "";
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				int Education = 0;
				int Abortion_FacilityName = 0;
				PWGUID = pregnantwomen.getString("PWGUID");
				AltMobileNo = pregnantwomen.getString("AltMobileNo");
				HHGUID = pregnantwomen.getString("HHGUID");
				HHFamilyMemberGUID = pregnantwomen
						.getString("HHFamilyMemberGUID");
				PWName = pregnantwomen.getString("PWName");
				PWImage = pregnantwomen.getString("PWImage");
				LMPDate = pregnantwomen.getString("LMPDate");
				EDDDate = pregnantwomen.getString("EDDDate");
				PWRegistrationDate = pregnantwomen
						.getString("PWRegistrationDate");
				HusbandName = pregnantwomen.getString("HusbandName");
				Husband_GUID = pregnantwomen.getString("Husband_GUID");
				MobileNo = pregnantwomen.getString("MobileNo");
				MotherMCTSID = pregnantwomen.getString("MotherMCTSID");
				IFSCCode = pregnantwomen.getString("IFSCCode");
				Accountno = pregnantwomen.getString("Accountno");
				JSYRegDate = pregnantwomen.getString("JSYRegDate");
				PWDOB = pregnantwomen.getString("PWDOB");
				PWAgeRefDate = pregnantwomen.getString("PWAgeRefDate");
				ExpFacilityforDelivery = pregnantwomen
						.getString("ExpFacilityforDelivery");
				ExpFacilityforDeliveryName = pregnantwomen
						.getString("ExpFacilityforDeliveryName");
				VDRLResult = pregnantwomen.getString("VDRLResult");
				HIVResult = pregnantwomen.getString("HIVResult");
				Visit1Date = pregnantwomen.getString("Visit1Date");
				Visit2Date = pregnantwomen.getString("Visit2Date");
				Visit3Date = pregnantwomen.getString("Visit3Date");
				Visit4Date = pregnantwomen.getString("Visit4Date");
				LastANCVisitDate = pregnantwomen.getString("LastANCVisitDate");
				TT1Date = pregnantwomen.getString("TT1Date");
				TT2Date = pregnantwomen.getString("TT2Date");

				TTBoosterDate = pregnantwomen.getString("TTBoosterDate");
				DangerSigns = pregnantwomen.getString("DangerSigns");
				DeliveryDateTime = pregnantwomen.getString("DeliveryDateTime");
				DeliveryConductedBy = pregnantwomen
						.getString("DeliveryConductedBy");
				DeliveryComplication = pregnantwomen
						.getString("DeliveryComplication");
				DTMFacilityDischarge = pregnantwomen
						.getString("DTMFacilityDischarge");
				PaymentRecieved = pregnantwomen.getString("PaymentRecieved");
				DateofDeath = pregnantwomen.getString("DateofDeath");
				OtherPlaceofDeath = pregnantwomen
						.getString("OtherPlaceofDeath");
				CreatedOn = pregnantwomen.getString("CreatedOn");
				UpdatedOn = pregnantwomen.getString("UpdatedOn");

				if (pregnantwomen.getString("ANMID") != null
						&& pregnantwomen.getString("ANMID").length() > 0
						&& !pregnantwomen.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(pregnantwomen.getInt("ANMID"));
				}
				if (pregnantwomen.getString("AshaID") != null
						&& pregnantwomen.getString("AshaID").length() > 0
						&& !pregnantwomen.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(pregnantwomen.getInt("AshaID"));
				}
				if (pregnantwomen.getString("Regwithin12weeks") != null
						&& pregnantwomen.getString("Regwithin12weeks").length() > 0
						&& !pregnantwomen.getString("Regwithin12weeks")
								.equalsIgnoreCase("null")) {
					Regwithin12weeks = Integer.valueOf(pregnantwomen
							.getInt("Regwithin12weeks"));
				}
				if (pregnantwomen.getString("RegweeksElaspsed") != null
						&& pregnantwomen.getString("RegweeksElaspsed").length() > 0
						&& !pregnantwomen.getString("RegweeksElaspsed")
								.equalsIgnoreCase("null")) {
					RegweeksElaspsed = Integer.valueOf(pregnantwomen
							.getInt("RegweeksElaspsed"));
				}
				if (pregnantwomen.getString("JSYBenificiaryYN") != null
						&& pregnantwomen.getString("JSYBenificiaryYN").length() > 0
						&& !pregnantwomen.getString("JSYBenificiaryYN")
								.equalsIgnoreCase("null")) {
					JSYBenificiaryYN = Integer.valueOf(pregnantwomen
							.getInt("JSYBenificiaryYN"));
				}
				if (pregnantwomen.getString("JSYPaymentReceivedYN") != null
						&& pregnantwomen.getString("JSYPaymentReceivedYN")
								.length() > 0
						&& !pregnantwomen.getString("JSYPaymentReceivedYN")
								.equalsIgnoreCase("null")) {
					JSYPaymentReceivedYN = Integer.valueOf(pregnantwomen
							.getInt("JSYPaymentReceivedYN"));
				}
				if (pregnantwomen.getString("PWAgeYears") != null
						&& pregnantwomen.getString("PWAgeYears").length() > 0
						&& !pregnantwomen.getString("PWAgeYears")
								.equalsIgnoreCase("null")) {
					PWAgeYears = Integer.valueOf(pregnantwomen
							.getInt("PWAgeYears"));
				}
				if (pregnantwomen.getString("PWWeight") != null
						&& pregnantwomen.getString("PWWeight").length() > 0
						&& !pregnantwomen.getString("PWWeight")
								.equalsIgnoreCase("null")) {
					PWWeight = Double.valueOf(pregnantwomen.getInt("PWWeight"));
				}
				if (pregnantwomen.getString("PWBloodGroup") != null
						&& pregnantwomen.getString("PWBloodGroup").length() > 0
						&& !pregnantwomen.getString("PWBloodGroup")
								.equalsIgnoreCase("null")) {
					PWBloodGroup = Integer.valueOf(pregnantwomen
							.getInt("PWBloodGroup"));
				}

				PastIllnessYN = pregnantwomen.getString("PastIllnessYN");

				if (pregnantwomen.getString("TotalPregnancy") != null
						&& pregnantwomen.getString("TotalPregnancy").length() > 0
						&& !pregnantwomen.getString("TotalPregnancy")
								.equalsIgnoreCase("null")) {
					TotalPregnancy = Integer.valueOf(pregnantwomen
							.getInt("TotalPregnancy"));
				}
				if (pregnantwomen.getString("LastPregnancyResult") != null
						&& pregnantwomen.getString("LastPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LastPregnancyResult")
								.equalsIgnoreCase("null")) {
					LastPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyResult"));
				}
				if (pregnantwomen.getString("LastPregnancyComplication") != null
						&& pregnantwomen.getString("LastPregnancyComplication")
								.length() > 0
						&& !pregnantwomen
								.getString("LastPregnancyComplication")
								.equalsIgnoreCase("null")) {
					LastPregnancyComplication = Integer.valueOf(pregnantwomen
							.getInt("LastPregnancyComplication"));
				}
				if (pregnantwomen.getString("LTLPregnancyResult") != null
						&& pregnantwomen.getString("LTLPregnancyResult")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyResult")
								.equalsIgnoreCase("null")) {
					LTLPregnancyResult = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyResult"));
				}
				if (pregnantwomen.getString("LTLPregnancyomplication") != null
						&& pregnantwomen.getString("LTLPregnancyomplication")
								.length() > 0
						&& !pregnantwomen.getString("LTLPregnancyomplication")
								.equalsIgnoreCase("null")) {
					LTLPregnancyomplication = Integer.valueOf(pregnantwomen
							.getInt("LTLPregnancyomplication"));
				}
				if (pregnantwomen.getString("PWHeight") != null
						&& pregnantwomen.getString("PWHeight").length() > 0
						&& !pregnantwomen.getString("PWHeight")
								.equalsIgnoreCase("null")) {
					PWHeight = Double.valueOf(pregnantwomen.getInt("PWHeight"));
				}
				if (pregnantwomen.getString("LastPregDeliveryPlace") != null
						&& pregnantwomen.getString("LastPregDeliveryPlace")
								.length() > 0
						&& !pregnantwomen.getString("LastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("LasttolastPregDeliveryPlace") != null
						&& pregnantwomen.getString(
								"LasttolastPregDeliveryPlace").length() > 0
						&& !pregnantwomen.getString(
								"LasttolastPregDeliveryPlace")
								.equalsIgnoreCase("null")) {
					LasttolastPregDeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("LasttolastPregDeliveryPlace"));
				}
				if (pregnantwomen.getString("VDRLTestYN") != null
						&& pregnantwomen.getString("VDRLTestYN").length() > 0
						&& !pregnantwomen.getString("VDRLTestYN")
								.equalsIgnoreCase("null")) {
					VDRLTestYN = Integer.valueOf(pregnantwomen
							.getInt("VDRLTestYN"));
				}
				if (pregnantwomen.getString("HIVTestYN") != null
						&& pregnantwomen.getString("HIVTestYN").length() > 0
						&& !pregnantwomen.getString("HIVTestYN")
								.equalsIgnoreCase("null")) {
					HIVTestYN = Integer.valueOf(pregnantwomen
							.getInt("HIVTestYN"));
				}
				if (pregnantwomen.getString("ISAbortion") != null
						&& pregnantwomen.getString("ISAbortion").length() > 0
						&& !pregnantwomen.getString("ISAbortion")
								.equalsIgnoreCase("null")) {
					ISAbortion = Integer.valueOf(pregnantwomen
							.getInt("ISAbortion"));
				}
				if (pregnantwomen.getString("IsPregnant") != null
						&& pregnantwomen.getString("IsPregnant").length() > 0
						&& !pregnantwomen.getString("IsPregnant")
								.equalsIgnoreCase("null")) {
					IsPregnant = Integer.valueOf(pregnantwomen
							.getInt("IsPregnant"));
				}
				if (pregnantwomen.getString("AbortionFacilityType") != null
						&& pregnantwomen.getString("AbortionFacilityType")
								.length() > 0
						&& !pregnantwomen.getString("AbortionFacilityType")
								.equalsIgnoreCase("null")) {
					AbortionFacilityType = Integer.valueOf(pregnantwomen
							.getInt("AbortionFacilityType"));
				}
				if (pregnantwomen.getString("NoofANCVisitsDone") != null
						&& pregnantwomen.getString("NoofANCVisitsDone")
								.length() > 0
						&& !pregnantwomen.getString("NoofANCVisitsDone")
								.equalsIgnoreCase("null")) {
					NoofANCVisitsDone = Integer.valueOf(pregnantwomen
							.getInt("NoofANCVisitsDone"));
				}
				if (pregnantwomen.getString("RefferedYN") != null
						&& pregnantwomen.getString("RefferedYN").length() > 0
						&& !pregnantwomen.getString("RefferedYN")
								.equalsIgnoreCase("null")) {
					RefferedYN = Integer.valueOf(pregnantwomen
							.getInt("RefferedYN"));
				}
				if (pregnantwomen.getString("DeliveryPlace") != null
						&& pregnantwomen.getString("DeliveryPlace").length() > 0
						&& !pregnantwomen.getString("DeliveryPlace")
								.equalsIgnoreCase("null")) {
					DeliveryPlace = Integer.valueOf(pregnantwomen
							.getInt("DeliveryPlace"));
				}
				if (pregnantwomen.getString("DeliveryType") != null
						&& pregnantwomen.getString("DeliveryType").length() > 0
						&& !pregnantwomen.getString("DeliveryType")
								.equalsIgnoreCase("null")) {
					DeliveryType = Integer.valueOf(pregnantwomen
							.getInt("DeliveryType"));
				}
				if (pregnantwomen.getString("MotherDeathCause") != null
						&& pregnantwomen.getString("MotherDeathCause").length() > 0
						&& !pregnantwomen.getString("MotherDeathCause")
								.equalsIgnoreCase("null")) {
					MotherDeathCause = Integer.valueOf(pregnantwomen
							.getInt("MotherDeathCause"));
				}
				if (pregnantwomen.getString("DeliveryOutcome") != null
						&& pregnantwomen.getString("DeliveryOutcome").length() > 0
						&& !pregnantwomen.getString("DeliveryOutcome")
								.equalsIgnoreCase("null")) {
					DeliveryOutcome = Integer.valueOf(pregnantwomen
							.getInt("DeliveryOutcome"));
				}
				if (pregnantwomen.getString("PlaceofDeath") != null
						&& pregnantwomen.getString("PlaceofDeath").length() > 0
						&& !pregnantwomen.getString("PlaceofDeath")
								.equalsIgnoreCase("null")) {
					PlaceofDeath = Integer.valueOf(pregnantwomen
							.getInt("PlaceofDeath"));
				}
				if (pregnantwomen.getString("CreatedBy") != null
						&& pregnantwomen.getString("CreatedBy").length() > 0
						&& !pregnantwomen.getString("CreatedBy")
								.equalsIgnoreCase("null")) {
					CreatedBy = Integer.valueOf(pregnantwomen
							.getInt("CreatedBy"));
				}
				if (pregnantwomen.getString("UpdatedBy") != null
						&& pregnantwomen.getString("UpdatedBy").length() > 0
						&& !pregnantwomen.getString("UpdatedBy")
								.equalsIgnoreCase("null")) {
					UpdatedBy = Integer.valueOf(pregnantwomen
							.getInt("UpdatedBy"));
				}
				if (pregnantwomen.getString("Education") != null
						&& pregnantwomen.getString("Education").length() > 0
						&& !pregnantwomen.getString("Education")
								.equalsIgnoreCase("null")) {
					Education = Integer.valueOf(pregnantwomen
							.getInt("Education"));
				}
				if (pregnantwomen.getString("Abortion_FacilityName") != null
						&& pregnantwomen.getString("Abortion_FacilityName")
								.length() > 0
						&& !pregnantwomen.getString("Abortion_FacilityName")
								.equalsIgnoreCase("null")) {
					Abortion_FacilityName = Integer.valueOf(pregnantwomen
							.getInt("Abortion_FacilityName"));
				}

				String sqlcount = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblPregnant_woman where  PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {

					sql = "Insert into tblPregnant_woman(Education,AltMobileNo,IsPregnant,PWGUID,HHGUID,HHFamilyMemberGUID,PWName,ANMID,PWImage,AshaID,LMPDate,EDDDate,PWRegistrationDate,Regwithin12weeks,RegweeksElaspsed,HusbandName,Husband_GUID,MobileNo,MotherMCTSID,IFSCCode,Accountno,JSYBenificiaryYN,JSYRegDate,JSYPaymentReceivedYN,PWDOB,PWAgeYears,PWAgeRefDate,PWWeight,PWBloodGroup,PastIllnessYN,TotalPregnancy,LastPregnancyResult,LastPregnancyComplication,LTLPregnancyResult,LTLPregnancyomplication,PWHeight,LastPregDeliveryPlace,LasttolastPregDeliveryPlace,ExpFacilityforDelivery,ExpFacilityforDeliveryName,VDRLTestYN,VDRLResult,HIVTestYN,HIVResult,Visit1Date,Visit2Date,Visit3Date,Visit4Date,ISAbortion,AbortionFacilityType,NoofANCVisitsDone,LastANCVisitDate,TT1Date,TT2Date,TTBoosterDate,DangerSigns,RefferedYN,DeliveryDateTime,DeliveryPlace,DeliveryConductedBy,DeliveryType,DeliveryComplication,MotherDeathCause,DeliveryOutcome,DTMFacilityDischarge,PaymentRecieved,PlaceofDeath,DateofDeath,OtherPlaceofDeath,CreatedOn,CreatedBy,UpdatedOn,UpdatedBy,IsEdited,IsUploaded,Abortion_FacilityName)values("
							+ Education
							+ ",'"
							+ AltMobileNo
							+ "',"
							+ IsPregnant
							+ ",'"
							+ PWGUID
							+ "','"
							+ HHGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWName
							+ "',"
							+ ANMID
							+ ",'"
							+ PWImage
							+ "',"
							+ AshaID
							+ ",'"
							+ LMPDate
							+ "','"
							+ EDDDate
							+ "','"
							+ PWRegistrationDate
							+ "',"
							+ Regwithin12weeks
							+ ","
							+ RegweeksElaspsed
							+ ",'"
							+ HusbandName
							+ "','"
							+ Husband_GUID
							+ "','"
							+ MobileNo
							+ "','"
							+ MotherMCTSID
							+ "','"
							+ IFSCCode
							+ "','"
							+ Accountno
							+ "',"
							+ JSYBenificiaryYN
							+ ",'"
							+ JSYRegDate
							+ "',"
							+ JSYPaymentReceivedYN
							+ ",'"
							+ PWDOB
							+ "',"
							+ PWAgeYears
							+ ",'"
							+ PWAgeRefDate
							+ "',"
							+ PWWeight
							+ ","
							+ PWBloodGroup
							+ ",'"
							+ PastIllnessYN
							+ "',"
							+ TotalPregnancy
							+ ","
							+ LastPregnancyResult
							+ ","
							+ LastPregnancyComplication
							+ ","
							+ LTLPregnancyResult
							+ ","
							+ LTLPregnancyomplication
							+ ","
							+ PWHeight
							+ ","
							+ LastPregDeliveryPlace
							+ ","
							+ LasttolastPregDeliveryPlace
							+ ",'"
							+ ExpFacilityforDelivery
							+ "','"
							+ ExpFacilityforDeliveryName
							+ "',"
							+ VDRLTestYN
							+ ",'"
							+ VDRLResult
							+ "',"
							+ HIVTestYN
							+ ",'"
							+ HIVResult
							+ "','"
							+ Visit1Date
							+ "','"
							+ Visit2Date
							+ "','"
							+ Visit3Date
							+ "','"
							+ Visit4Date
							+ "',"
							+ ISAbortion
							+ ","
							+ AbortionFacilityType
							+ ","
							+ NoofANCVisitsDone
							+ ",'"
							+ LastANCVisitDate
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ TTBoosterDate
							+ "','"
							+ DangerSigns
							+ "',"
							+ RefferedYN
							+ ",'"
							+ DeliveryDateTime
							+ "',"
							+ DeliveryPlace
							+ ",'"
							+ DeliveryConductedBy
							+ "',"
							+ DeliveryType
							+ ",'"
							+ DeliveryComplication
							+ "',"
							+ MotherDeathCause
							+ ","
							+ DeliveryOutcome
							+ ",'"
							+ DTMFacilityDischarge
							+ "','"
							+ PaymentRecieved
							+ "',"
							+ PlaceofDeath
							+ ",'"
							+ DateofDeath
							+ "','"
							+ OtherPlaceofDeath
							+ "','"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ",0,0," + Abortion_FacilityName + ")";
					dataProvider.executeSql(sql);
				} else {
					if (count1 == 0) {
						sql = "update tblPregnant_woman set Education="
								+ Education
								+ ", IsPregnant="
								+ IsPregnant
								+ ",HHGUID='"
								+ HHGUID
								+ "',HHFamilyMemberGUID='"
								+ HHFamilyMemberGUID
								+ "',AltMobileNo='"
								+ AltMobileNo
								+ "',PWName='"
								+ PWName
								+ "',ANMID="
								+ ANMID
								+ ",PWImage='"
								+ PWImage
								+ "',AshaID="
								+ AshaID
								+ ",LMPDate='"
								+ LMPDate
								+ "',EDDDate='"
								+ EDDDate
								+ "',PWRegistrationDate='"
								+ PWRegistrationDate
								+ "',Regwithin12weeks="
								+ Regwithin12weeks
								+ ",RegweeksElaspsed="
								+ RegweeksElaspsed
								+ ",HusbandName='"
								+ HusbandName
								+ "',Husband_GUID='"
								+ Husband_GUID
								+ "',MobileNo='"
								+ MobileNo
								+ "',MotherMCTSID='"
								+ MotherMCTSID
								+ "',IFSCCode='"
								+ IFSCCode
								+ "',Accountno='"
								+ Accountno
								+ "',JSYBenificiaryYN="
								+ JSYBenificiaryYN
								+ ",JSYRegDate='"
								+ JSYRegDate
								+ "',JSYPaymentReceivedYN="
								+ JSYPaymentReceivedYN
								+ ",PWDOB='"
								+ PWDOB
								+ "',PWAgeYears="
								+ PWAgeYears
								+ ",PWAgeRefDate='"
								+ PWAgeRefDate
								+ "',PWWeight="
								+ PWWeight
								+ ",PWBloodGroup="
								+ PWBloodGroup
								+ ",PastIllnessYN='"
								+ PastIllnessYN
								+ "',TotalPregnancy="
								+ TotalPregnancy
								+ ",LastPregnancyResult="
								+ LastPregnancyResult
								+ ",LastPregnancyComplication="
								+ LastPregnancyComplication
								+ ",LTLPregnancyResult="
								+ LTLPregnancyResult
								+ ",LTLPregnancyomplication="
								+ LTLPregnancyomplication
								+ ",PWHeight="
								+ PWHeight
								+ ",LastPregDeliveryPlace="
								+ LastPregDeliveryPlace
								+ ",LasttolastPregDeliveryPlace="
								+ LasttolastPregDeliveryPlace
								+ ",ExpFacilityforDelivery='"
								+ ExpFacilityforDelivery
								+ "',ExpFacilityforDeliveryName='"
								+ ExpFacilityforDeliveryName
								+ "',VDRLTestYN="
								+ VDRLTestYN
								+ ",VDRLResult='"
								+ VDRLResult
								+ "',HIVTestYN="
								+ HIVTestYN
								+ ",HIVResult='"
								+ HIVResult
								+ "',Visit1Date='"
								+ Visit1Date
								+ "',Visit2Date='"
								+ Visit2Date
								+ "',Visit3Date='"
								+ Visit3Date
								+ "',Visit4Date='"
								+ Visit4Date
								+ "',ISAbortion="
								+ ISAbortion
								+ ",AbortionFacilityType="
								+ AbortionFacilityType
								+ ",NoofANCVisitsDone="
								+ NoofANCVisitsDone
								+ ",LastANCVisitDate='"
								+ LastANCVisitDate
								+ "',TT1Date='"
								+ TT1Date
								+ "',TT2Date='"
								+ TT2Date
								+ "',TTBoosterDate='"
								+ TTBoosterDate
								+ "',DangerSigns='"
								+ DangerSigns
								+ "',RefferedYN="
								+ RefferedYN
								+ ",DeliveryDateTime='"
								+ DeliveryDateTime
								+ "',DeliveryPlace="
								+ DeliveryPlace
								+ ",DeliveryConductedBy='"
								+ DeliveryConductedBy
								+ "',DeliveryType="
								+ DeliveryType
								+ ",DeliveryComplication='"
								+ DeliveryComplication
								+ "',MotherDeathCause="
								+ MotherDeathCause
								+ ",DeliveryOutcome="
								+ DeliveryOutcome
								+ ",DTMFacilityDischarge='"
								+ DTMFacilityDischarge
								+ "',PaymentRecieved='"
								+ PaymentRecieved
								+ "',PlaceofDeath="
								+ PlaceofDeath
								+ ",DateofDeath='"
								+ DateofDeath
								+ "',OtherPlaceofDeath='"
								+ OtherPlaceofDeath
								+ "',CreatedOn='"
								+ CreatedOn
								+ "',CreatedBy="
								+ CreatedBy
								+ ",UpdatedOn='"
								+ UpdatedOn
								+ "',UpdatedBy="
								+ UpdatedBy
								+ ",IsEdited=0,IsUploaded=0,Abortion_FacilityName="
								+ Abortion_FacilityName + " where PWGUID='"
								+ PWGUID + "'";
					}
				}
			}
			tbl_DatesEdArray = jsonObj.getJSONArray("tbl_DatesEd");
			for (int i = 0; i < tbl_DatesEdArray.length(); i++) {
				JSONObject DatesEd = tbl_DatesEdArray.getJSONObject(i);

				String HHSurveyGUID = "";
				String HHFamilyMemberGUID = "";
				String PWGUID = "";
				String LmpDate = "";
				String EDD = "";
				String TT1Date = "";
				String TT2Date = "";
				String AncCheckupdate1 = "";
				String AncCheckupdate3 = "";
				String AncCheckupdate2 = "";
				String AncCheckupdate4 = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int IsEdited = 0;
				int IsDeleted = 0;
				int UpdatedBy = 0;
				int AshaID = 0;
				int ANMID = 0;
				HHSurveyGUID = DatesEd.getString("HHSurveyGUID");
				HHFamilyMemberGUID = DatesEd.getString("HHFamilyMemberGUID");
				PWGUID = DatesEd.getString("PWGUID");
				LmpDate = DatesEd.getString("LmpDate");
				EDD = DatesEd.getString("EDD");
				TT1Date = Validate.changeDateFormat(DatesEd
						.getString("TT1Date"));
				TT2Date = Validate.changeDateFormat(DatesEd
						.getString("TT2Date"));
				AncCheckupdate1 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate1"));
				AncCheckupdate3 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate2"));
				AncCheckupdate2 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate3"));
				AncCheckupdate4 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate4"));
				CreatedOn = DatesEd.getString("CreatedOn");
				UpdatedOn = DatesEd.getString("UpdatedOn");
				if (DatesEd.getString("CreatedBy") != null
						&& DatesEd.getString("CreatedBy").length() > 0
						&& !DatesEd.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(DatesEd.getInt("CreatedBy"));
				}
				// if (DatesEd.getString("IsEdited") != null
				// && DatesEd.getString("IsEdited").length() > 0
				// && !DatesEd.getString("IsEdited").equalsIgnoreCase(
				// "null")) {
				// IsEdited = Integer.valueOf(DatesEd.getInt("IsEdited"));
				// }
				if (DatesEd.getString("IsDeleted") != null
						&& DatesEd.getString("IsDeleted").length() > 0
						&& !DatesEd.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(DatesEd.getInt("IsDeleted"));
				}
				if (DatesEd.getString("UpdatedBy") != null
						&& DatesEd.getString("UpdatedBy").length() > 0
						&& !DatesEd.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(DatesEd.getInt("UpdatedBy"));
				}
				if (DatesEd.getString("AshaID") != null
						&& DatesEd.getString("AshaID").length() > 0
						&& !DatesEd.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(DatesEd.getInt("AshaID"));
				}
				if (DatesEd.getString("ANMID") != null
						&& DatesEd.getString("ANMID").length() > 0
						&& !DatesEd.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(DatesEd.getInt("ANMID"));
				}

				String sqlcount = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tbl_DatesEd( HHSurveyGUID ,HHFamilyMemberGUID ,PWGUID ,LmpDate ,EDD ,TT1Date ,TT2Date ,AncCheckupdate1 ,AncCheckupdate3 ,AncCheckupdate2 ,AncCheckupdate4 ,CreatedOn ,UpdatedOn ,CreatedBy , IsEdited , IsDeleted , UpdatedBy , AshaID , ANMID)values( '"
							+ HHSurveyGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWGUID
							+ "','"
							+ LmpDate
							+ "','"
							+ EDD
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ AncCheckupdate1
							+ "','"
							+ AncCheckupdate3
							+ "','"
							+ AncCheckupdate2
							+ "','"
							+ AncCheckupdate4
							+ "','"
							+ CreatedOn
							+ "','"
							+ UpdatedOn
							+ "',"
							+ CreatedBy
							+ ","
							+ IsEdited
							+ ","
							+ IsDeleted
							+ ","
							+ UpdatedBy + "," + AshaID + "," + ANMID + ")";

				} else {
					if (count1 == 0) {
						sql = "update tbl_DatesEd set  HHSurveyGUID ='"
								+ HHSurveyGUID + "',HHFamilyMemberGUID ='"
								+ HHFamilyMemberGUID + "',LmpDate ='" + LmpDate
								+ "',EDD ='" + EDD + "',TT1Date ='" + TT1Date
								+ "',TT2Date ='" + TT2Date
								+ "',AncCheckupdate1 ='" + AncCheckupdate1
								+ "',AncCheckupdate3 ='" + AncCheckupdate2
								+ "',AncCheckupdate2 ='" + AncCheckupdate3
								+ "',AncCheckupdate4 ='" + AncCheckupdate4
								+ "',CreatedOn ='" + CreatedOn
								+ "',UpdatedOn ='" + UpdatedOn
								+ "',CreatedBy =" + CreatedBy + ", IsEdited ="
								+ IsEdited + ", IsDeleted =" + IsDeleted
								+ ", UpdatedBy =" + UpdatedBy + ", AshaID ="
								+ AshaID + ", ANMID  =" + ANMID
								+ " where PWGUID='" + PWGUID + "'";

					}
				}
				dataProvider.executeSql(sql);
			}

			iMNCHDataDownload = 1;
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();
		}

	}

	public void importtbl_DatesEd(String UserName, String Password,
			String ashaid) {

		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tbl_DatesEd");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 250000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearMNCHData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tbl_DatesEdArray = jsonObj.getJSONArray("tbl_DatesEd");
			for (int i = 0; i < tbl_DatesEdArray.length(); i++) {
				JSONObject DatesEd = tbl_DatesEdArray.getJSONObject(i);

				String HHSurveyGUID = "";
				String HHFamilyMemberGUID = "";
				String PWGUID = "";
				String LmpDate = "";
				String EDD = "";
				String TT1Date = "";
				String TT2Date = "";
				String AncCheckupdate1 = "";
				String AncCheckupdate3 = "";
				String AncCheckupdate2 = "";
				String AncCheckupdate4 = "";
				String CreatedOn = "";
				String UpdatedOn = "";
				int CreatedBy = 0;
				int IsEdited = 0;
				int IsDeleted = 0;
				int UpdatedBy = 0;
				int AshaID = 0;
				int ANMID = 0;
				HHSurveyGUID = DatesEd.getString("HHSurveyGUID");
				HHFamilyMemberGUID = DatesEd.getString("HHFamilyMemberGUID");
				PWGUID = DatesEd.getString("PWGUID");
				LmpDate = DatesEd.getString("LmpDate");
				EDD = DatesEd.getString("EDD");
				TT1Date = Validate.changeDateFormat(DatesEd
						.getString("TT1Date"));
				TT2Date = Validate.changeDateFormat(DatesEd
						.getString("TT2Date"));
				AncCheckupdate1 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate1"));
				AncCheckupdate3 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate2"));
				AncCheckupdate2 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate3"));
				AncCheckupdate4 = Validate.changeDateFormat(DatesEd
						.getString("AncCheckupDate4"));
				CreatedOn = DatesEd.getString("CreatedOn");
				UpdatedOn = DatesEd.getString("UpdatedOn");
				if (DatesEd.getString("CreatedBy") != null
						&& DatesEd.getString("CreatedBy").length() > 0
						&& !DatesEd.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(DatesEd.getInt("CreatedBy"));
				}
				// if (DatesEd.getString("IsEdited") != null
				// && DatesEd.getString("IsEdited").length() > 0
				// && !DatesEd.getString("IsEdited").equalsIgnoreCase(
				// "null")) {
				// IsEdited = Integer.valueOf(DatesEd.getInt("IsEdited"));
				// }
				if (DatesEd.getString("IsDeleted") != null
						&& DatesEd.getString("IsDeleted").length() > 0
						&& !DatesEd.getString("IsDeleted").equalsIgnoreCase(
								"null")) {
					IsDeleted = Integer.valueOf(DatesEd.getInt("IsDeleted"));
				}
				if (DatesEd.getString("UpdatedBy") != null
						&& DatesEd.getString("UpdatedBy").length() > 0
						&& !DatesEd.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(DatesEd.getInt("UpdatedBy"));
				}
				if (DatesEd.getString("AshaID") != null
						&& DatesEd.getString("AshaID").length() > 0
						&& !DatesEd.getString("AshaID")
								.equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(DatesEd.getInt("AshaID"));
				}
				if (DatesEd.getString("ANMID") != null
						&& DatesEd.getString("ANMID").length() > 0
						&& !DatesEd.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(DatesEd.getInt("ANMID"));
				}

				String sqlcount = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tbl_DatesEd  where PWGUID='"
						+ PWGUID + "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);

				String sql = "";
				if (count == 0) {

					sql = "Insert into tbl_DatesEd( HHSurveyGUID ,HHFamilyMemberGUID ,PWGUID ,LmpDate ,EDD ,TT1Date ,TT2Date ,AncCheckupdate1 ,AncCheckupdate3 ,AncCheckupdate2 ,AncCheckupdate4 ,CreatedOn ,UpdatedOn ,CreatedBy , IsEdited , IsDeleted , UpdatedBy , AshaID , ANMID)values( '"
							+ HHSurveyGUID
							+ "','"
							+ HHFamilyMemberGUID
							+ "','"
							+ PWGUID
							+ "','"
							+ LmpDate
							+ "','"
							+ EDD
							+ "','"
							+ TT1Date
							+ "','"
							+ TT2Date
							+ "','"
							+ AncCheckupdate1
							+ "','"
							+ AncCheckupdate3
							+ "','"
							+ AncCheckupdate2
							+ "','"
							+ AncCheckupdate4
							+ "','"
							+ CreatedOn
							+ "','"
							+ UpdatedOn
							+ "',"
							+ CreatedBy
							+ ","
							+ IsEdited
							+ ","
							+ IsDeleted
							+ ","
							+ UpdatedBy + "," + AshaID + "," + ANMID + ")";

				} else {
					if (count1 == 0) {
						sql = "update tbl_DatesEd set  HHSurveyGUID ='"
								+ HHSurveyGUID + "',HHFamilyMemberGUID ='"
								+ HHFamilyMemberGUID + "',LmpDate ='" + LmpDate
								+ "',EDD ='" + EDD + "',TT1Date ='" + TT1Date
								+ "',TT2Date ='" + TT2Date
								+ "',AncCheckupdate1 ='" + AncCheckupdate1
								+ "',AncCheckupdate3 ='" + AncCheckupdate2
								+ "',AncCheckupdate2 ='" + AncCheckupdate3
								+ "',AncCheckupdate4 ='" + AncCheckupdate4
								+ "',CreatedOn ='" + CreatedOn
								+ "',UpdatedOn ='" + UpdatedOn
								+ "',CreatedBy =" + CreatedBy + ", IsEdited ="
								+ IsEdited + ", IsDeleted =" + IsDeleted
								+ ", UpdatedBy =" + UpdatedBy + ", AshaID ="
								+ AshaID + ", ANMID  =" + ANMID
								+ " where PWGUID='" + PWGUID + "'";

					}
				}
				dataProvider.executeSql(sql);
			}

			iMNCHDataDownload = 1;
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();
		}

	}

	private void ClearMasterData() {

		String sQueryDeleteMstUserData = null;
		sQueryDeleteMstUserData = "Delete from  MstUser";
		dataProvider.executeSql(sQueryDeleteMstUserData);

		String sQueryDeleteMstRoleData = null;
		sQueryDeleteMstRoleData = "Delete from MstRole";
		dataProvider.executeSql(sQueryDeleteMstRoleData);

		String sQueryDeleteMstStateData = null;
		sQueryDeleteMstStateData = "Delete from MstState";
		dataProvider.executeSql(sQueryDeleteMstStateData);

		String sQueryDeleteMstDistrictData = null;
		sQueryDeleteMstDistrictData = "Delete from MstDistrict";
		dataProvider.executeSql(sQueryDeleteMstDistrictData);

		String sQueryDeleteMstBlockData = null;
		sQueryDeleteMstBlockData = "Delete from MstBlock";
		dataProvider.executeSql(sQueryDeleteMstBlockData);

		String sQueryDeleteMstPanchayatData = null;
		sQueryDeleteMstPanchayatData = "Delete from MstPanchayat";
		dataProvider.executeSql(sQueryDeleteMstPanchayatData);

		String sQueryDeleteMstVillageData = null;
		sQueryDeleteMstVillageData = "Delete from MstVillage";
		dataProvider.executeSql(sQueryDeleteMstVillageData);

		String sQueryDeleteMstCommonData = null;
		sQueryDeleteMstCommonData = "Delete from MstCommon";
		dataProvider.executeSql(sQueryDeleteMstCommonData);

		String sQueryDeleteMstANMData = null;
		sQueryDeleteMstANMData = "Delete from MstANM";
		dataProvider.executeSql(sQueryDeleteMstANMData);

		String sQueryDeleteMstASHAData = null;
		sQueryDeleteMstASHAData = "Delete from MstASHA";
		dataProvider.executeSql(sQueryDeleteMstASHAData);

		String sQueryDeleteMstSubCenterData = null;
		sQueryDeleteMstSubCenterData = "Delete from MstSubCenter";
		dataProvider.executeSql(sQueryDeleteMstSubCenterData);

		String sQueryDeleteMstCatchmentSupervisor = null;
		sQueryDeleteMstCatchmentSupervisor = "Delete from MstCatchmentSupervisor";
		dataProvider.executeSql(sQueryDeleteMstCatchmentSupervisor);

		String sQueryDeleteMstVersion = null;
		sQueryDeleteMstVersion = "Delete from MstVersion";
		dataProvider.executeSql(sQueryDeleteMstVersion);

		String sQueryDeleteMstVersionTrack = null;
		sQueryDeleteMstVersionTrack = "Delete from MstVersionTrack";
		dataProvider.executeSql(sQueryDeleteMstVersionTrack);
	}

	// private void ClearSurveyData() {
	//
	// String sQueryDeletetblHHSurvey = null;
	// sQueryDeletetblHHSurvey = "Delete from  Tbl_HHSurvey";
	// dataProvider.executeSql(sQueryDeletetblHHSurvey);
	//
	// String sQueryDeleteMstRoleData = null;
	// sQueryDeleteMstRoleData = "Delete from Tbl_HHFamilyMember";
	// dataProvider.executeSql(sQueryDeleteMstRoleData);
	//
	// }

	// private void ClearMNCHData() {
	//
	// String sQueryDeletetblChild = null;
	// sQueryDeletetblChild = "Delete from tblChild";
	// dataProvider.executeSql(sQueryDeletetblChild);
	// String sQueryDeleteTblANCVisit = null;
	// sQueryDeleteTblANCVisit = "Delete from TblANCVisit";
	// dataProvider.executeSql(sQueryDeleteTblANCVisit);
	// String sQueryDeletetbl_pregnantwomen = null;
	// sQueryDeletetbl_pregnantwomen = "Delete from tblPregnant_woman";
	// dataProvider.executeSql(sQueryDeletetbl_pregnantwomen);
	//
	// }

	public void exportHH() {
		HHSurveycount = dataProvider.getTblSurveyUploadcount();

		// TODO Auto-generated method stub

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.Uploadingdata));
		new Thread() {

			@Override
			public void run() {
				try {
					for (int i = 0; i < HHSurveycount.size(); i++) {
						if ((HHSurveycount != null && HHSurveycount.size() > 0)) {
							hhguid = HHSurveycount.get(i).getHHSurveyGUID();
							HHSurvey = dataProvider.getTblSurveyUpload(hhguid);
							HHFamilyMember = dataProvider
									.getHHFamilyMemberUpload(hhguid);
							migration = dataProvider.geMigrationData(2, "", "");
							tblupdate = dataProvider.getAlldata();
							if ((HHSurvey != null && HHSurvey.size() > 0)
									|| (HHFamilyMember != null && HHFamilyMember
											.size() > 0)
									|| (migration != null && migration.size() > 0)
									|| (tblupdate != null && tblupdate.size() > 0)) {
								ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
								NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
										.getActiveNetworkInfo();
								if (networkInfoCheckConnection != null
										&& networkInfoCheckConnection
												.isConnected()
										&& networkInfoCheckConnection
												.isAvailable()) {
									exportHHSurveydata();
								}
							} else {
								Toast.makeText(Synchronization.this,
										"No Network Access", Toast.LENGTH_LONG)
										.show();
							}
						} else {
							Toast.makeText(Synchronization.this,
									"Nothing for Upload", Toast.LENGTH_LONG)
									.show();
						}
					}
				}

				catch (Exception exp) {
					downloadmsg = exp.getMessage();
					progressDialog.dismiss();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (iDataUpload == 1) {
							Toast.makeText(Synchronization.this,
									"Data upload successfully",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(Synchronization.this, downloadmsg,
									Toast.LENGTH_LONG).show();

						}
					}
				});

				progressDialog.dismiss();
			}
		}.start();

	}

	public void exportMNCH() {

		// TODO Auto-generated method stub

		progressDialog = ProgressDialog.show(Synchronization.this, "",
				getResources().getString(R.string.Uploadingdata));
		new Thread() {

			@Override
			public void run() {
				try {
					Child = dataProvider.gettblChild("", 3);
					pregnantwomen = dataProvider.getPregnantWomendata("", 2, 0);
					ANCVisit = dataProvider.getTbl_VisitANCData("", "", 2);
					// tblDates = dataProvider.gettbl_DatesEd();
					tblupdate = dataProvider.getAlldata();
					if ((Child != null && Child.size() > 0)
							|| (pregnantwomen != null && pregnantwomen.size() > 0)
							|| (ANCVisit != null && ANCVisit.size() > 0)) {
						ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
								.getActiveNetworkInfo();
						if (networkInfoCheckConnection != null
								&& networkInfoCheckConnection.isConnected()
								&& networkInfoCheckConnection.isAvailable()) {

							exportMNCHdata();
						} else {
							Toast.makeText(Synchronization.this,
									"No Network Access", Toast.LENGTH_LONG)
									.show();
						}
					} else {
						Toast.makeText(Synchronization.this,
								"Nothing for Upload", Toast.LENGTH_LONG).show();
					}

				}

				catch (Exception exp) {
					progressDialog.dismiss();
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (iDataUploadMNCH == 1) {
							Toast.makeText(Synchronization.this,
									"Data upload successfully",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(Synchronization.this, downloadmsg,
									Toast.LENGTH_LONG).show();
						}
					}
				});

				progressDialog.dismiss();

			}
		}.start();

	}

	public void exportHHSurveydata() {
		try {
			//
			String SOAP_ACTION = "http://tempuri.org/PostDataVersion_501";
			String METHOD_NAME = "PostDataVersion_501";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "WebURL";
			// String URL
			// ="WebURL";
			SoapPrimitive resultString;
			String TAG1 = "Response";
			JSONObject jsonObjectcombined = new JSONObject();

			if (HHSurvey.size() > 0) {
				String json1 = new Gson().toJson(HHSurvey);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("Tbl_HHSurvey", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("Tbl_HHSurvey", otherJsonArray);

			}

			if (HHFamilyMember.size() > 0) {
				String json1 = new Gson().toJson(HHFamilyMember);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined
						.put("Tbl_HHFamilyMember", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("Tbl_HHFamilyMember", otherJsonArray);

			}
			if (migration.size() > 0) {
				String json1 = new Gson().toJson(migration);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("tblMigration", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblMigration", otherJsonArray);

			}
			if (tblupdate.size() > 0) {
				String json1 = new Gson().toJson(tblupdate);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("tblhhupdate_Log", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblhhupdate_Log", otherJsonArray);

			}
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

			Request.addProperty("sData", jsonObjectcombined.toString());
			Request.addProperty("AuthenticationToken",
					global.getsGlobalUserName());
			Request.addProperty("UserName", global.getsGlobalUserName());
			Request.addProperty("PassWord", global.getsGlobalPassword());
			// {"Table":[{"RetValue":10}]}
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);

			HttpTransportSE transport1 = new HttpTransportSE(URL, 240000);

			transport1.call(SOAP_ACTION, soapEnvelope);
			resultString = (SoapPrimitive) soapEnvelope.getResponse();
			Log.i(TAG1, "Result Celsius: " + resultString);
			JSONObject jsonObj = new JSONObject(resultString.toString()
					.toUpperCase());
			try {
				JSONArray Table = jsonObj.getJSONArray(("Table").toUpperCase());
				for (int i = 0; i < Table.length(); i++) {
					JSONObject data = Table.getJSONObject(i);
					String Result = data.getString("RETVALUE");
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUpload = 1;
						String sqlHHSurvey = "Update Tbl_HHSurvey set IsUploaded=1,IsEdited=0 where IsEdited=1 and HHSurveyGUID='"
								+ hhguid + "' ";
						dataProvider.executeSql(sqlHHSurvey);
					} else {
						iDataUpload = 0;
					}

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUpload = 1;
						String sqlHHFamilyMember = "Update Tbl_HHFamilyMember set IsUploaded=1,IsEdited=0 where IsEdited=1 and HHSurveyGUID='"
								+ hhguid + "' ";
						dataProvider.executeSql(sqlHHFamilyMember);
					} else {
						iDataUpload = 0;
					}
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUpload = 1;
						String sqlMigration = "Update tblMigration set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlMigration);
					} else {
						iDataUpload = 0;
					}
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUpload = 1;
						String sqlMigration = "Update tblhhupdate_Log set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlMigration);
					} else {
						iDataUpload = 0;
					}
				}
			} catch (Exception e) {
				downloadmsg = e.getMessage();
				System.out.println(e);
			}
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			System.out.println(e);
		}
	}

	public void exportMNCHdata() {
		try {
			//
			String SOAP_ACTION = "http://tempuri.org/MNCHUploadServices_V601";
			String METHOD_NAME = "MNCHUploadServices_V601";
			String NAMESPACE = "http://tempuri.org/";
				String URL = "WebURL";

			// String URL
			// ="WebURL";
			SoapPrimitive resultString;
			String TAG1 = "Response";
			JSONObject jsonObjectcombined = new JSONObject();

			if (Child.size() > 0) {
				String json1 = new Gson().toJson(Child);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("tblChild", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblChild", otherJsonArray);

			}
			if (ANCVisit.size() > 0) {
				String json1 = new Gson().toJson(ANCVisit);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("Tbl_ANCVisit", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("Tbl_ANCVisit", otherJsonArray);

			}
			if (pregnantwomen.size() > 0) {
				String json1 = new Gson().toJson(pregnantwomen);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("tbl_pregnantwomen", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tbl_pregnantwomen", otherJsonArray);

			}
			if (tblDates.size() > 0) {
				String json1 = new Gson().toJson(tblDates);
				JSONArray JSONArray_tbl_DatesEd = new JSONArray(json1);
				jsonObjectcombined.put("tbl_DatesEd", JSONArray_tbl_DatesEd);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tbl_DatesEd", otherJsonArray);

			}
			if (tblupdate.size() > 0) {
				String json1 = new Gson().toJson(tblupdate);
				JSONArray JSONArray_HHSurvey = new JSONArray(json1);
				jsonObjectcombined.put("tblhhupdate_Log", JSONArray_HHSurvey);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblhhupdate_Log", otherJsonArray);

			}
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

			Request.addProperty("sData", jsonObjectcombined.toString());
			Request.addProperty("AuthenticationToken",
					global.getsGlobalUserName());
			Request.addProperty("UserName", global.getsGlobalUserName());
			Request.addProperty("PassWord", global.getsGlobalPassword());
			// {"Table":[{"RetValue":10}]}
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);

			HttpTransportSE transport1 = new HttpTransportSE(URL, 240000);

			transport1.call(SOAP_ACTION, soapEnvelope);
			resultString = (SoapPrimitive) soapEnvelope.getResponse();
			Log.i(TAG1, "Result Celsius: " + resultString);
			JSONObject jsonObj = new JSONObject(resultString.toString()
					.toUpperCase());
			try {
				JSONArray Table = jsonObj.getJSONArray(("Table").toUpperCase());
				for (int i = 0; i < Table.length(); i++) {
					JSONObject data = Table.getJSONObject(i);
					String Result = data.getString("RETVALUE");

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadMNCH = 1;
						String sqlHHSurvey = "Update tblChild set IsUploaded=1, IsEdited=0 where IsEdited=1";
						dataProvider.executeSql(sqlHHSurvey);
					} else {
						iDataUploadMNCH = 0;
					}

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadMNCH = 1;
						String sqlHHFamilyMember = "Update tblPregnant_woman set IsUploaded=1, IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlHHFamilyMember);
					} else {
						iDataUploadMNCH = 0;
					}

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadMNCH = 1;
						String sqlHHFamilyMember = "Update TblANCVisit set IsUploaded=1, IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlHHFamilyMember);
					} else {
						iDataUploadMNCH = 0;
					}
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadMNCH = 1;
						String sqlHHFamilyMember = "Update tblhhupdate_Log set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlHHFamilyMember);
					} else {
						iDataUploadMNCH = 0;
					}
				}
			} catch (Exception e) {
				downloadmsg = e.getMessage();
				System.out.println(e);
			}
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			System.out.println(e);
		}
	}

	public void exportFamilyPlanningdata() {
		try {
			//
			String SOAP_ACTION = "http://tempuri.org/AnsUploadServices_V601";
			String METHOD_NAME = "AnsUploadServices_V601";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "WebURL";
			// String URL
			// ="WebURL";
			SoapPrimitive resultString;
			String TAG1 = "Response";
			JSONObject jsonObjectcombined = new JSONObject();

			if (mstFPAns.size() > 0) {
				String json1 = new Gson().toJson(mstFPAns);
				JSONArray JSONArray_mstFPAns = new JSONArray(json1);
				jsonObjectcombined.put("tblmstFPAns", JSONArray_mstFPAns);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblmstFPAns", otherJsonArray);

			}

			if (mstFPFDetail.size() > 0) {
				String json1 = new Gson().toJson(mstFPFDetail);
				JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
				jsonObjectcombined.put("tblmstFPFDetail",
						JSONArray_mstFPFDetail);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblmstFPFDetail", otherJsonArray);

			}
			if (tblmstimmunizationANS.size() > 0) {
				String json1 = new Gson().toJson(tblmstimmunizationANS);
				JSONArray JSONArray_mstFPAns = new JSONArray(json1);
				jsonObjectcombined.put("tblmstimmunizationANS",
						JSONArray_mstFPAns);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblmstimmunizationANS", otherJsonArray);

			}

			if (tblPNChomevisit_ANS.size() > 0) {
				String json1 = new Gson().toJson(tblPNChomevisit_ANS);
				JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
				jsonObjectcombined.put("tblPNChomevisit_ANS",
						JSONArray_mstFPFDetail);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblPNChomevisit_ANS", otherJsonArray);

			}

			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

			Request.addProperty("sData", jsonObjectcombined.toString());
			Request.addProperty("AuthenticationToken",
					global.getsGlobalUserName());
			Request.addProperty("UserName", global.getsGlobalUserName());
			Request.addProperty("PassWord", global.getsGlobalPassword());
			// {"Table":[{"RetValue":10}]}
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);

			HttpTransportSE transport1 = new HttpTransportSE(URL, 240000);

			transport1.call(SOAP_ACTION, soapEnvelope);
			resultString = (SoapPrimitive) soapEnvelope.getResponse();
			Log.i(TAG1, "Result Celsius: " + resultString);
			JSONObject jsonObj = new JSONObject(resultString.toString()
					.toUpperCase());
			try {
				JSONArray Table = jsonObj.getJSONArray(("Table").toUpperCase());
				for (int i = 0; i < Table.length(); i++) {
					JSONObject data = Table.getJSONObject(i);
					String Result = data.getString("RETVALUE");
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadfp = 1;
						String sqltblmstFPAns = "Update tblFP_followup set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstFPAns);
					} else {
						iDataUploadfp = 0;
					}

				}
				JSONArray Table3 = jsonObj.getJSONArray(("Table3")
						.toUpperCase());
				for (int i = 0; i < Table3.length(); i++) {
					JSONObject data = Table3.getJSONObject(i);
					String Result = data.getString("RETVALUE");
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadfpans = 1;
						String sqltblmstFPAns = "Update tblFP_visit set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstFPAns);
					} else {
						iDataUploadfpans = 0;
					}

				}

				JSONArray Table1 = jsonObj.getJSONArray(("Table1")
						.toUpperCase());
				for (int i = 0; i < Table1.length(); i++) {
					JSONObject data = Table1.getJSONObject(i);
					String Result = data.getString("RETVALUE");
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadImmu = 1;
						String sqltblmstFPAns = "Update tblmstimmunizationANS set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstFPAns);
					} else {
						iDataUploadImmu = 0;
					}

				}
				JSONArray Table2 = jsonObj.getJSONArray(("Table2")
						.toUpperCase());
				for (int i = 0; i < Table2.length(); i++) {
					JSONObject data = Table2.getJSONObject(i);
					String Result = data.getString("RETVALUE");

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadpncans = 1;
						String sqlmstFPFDetail = "Update tblPNChomevisit_ANS set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlmstFPFDetail);
					} else {
						iDataUploadpncans = 0;
					}
				}
			} catch (Exception e) {
				downloadmsg = e.getMessage();
				System.out.println(e);
			}
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			System.out.println(e);
		}
	}

	public void exportFamilyPlanning() {

		ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {

			// TODO Auto-generated method stub

			progressDialog = ProgressDialog.show(Synchronization.this, "",
					getResources().getString(R.string.Uploadingdata));
			new Thread() {

				@Override
				public void run() {
					try {
						mstFPAns = dataProvider.getFP_Ans("", 1);
						mstFPFDetail = dataProvider.getFP_Detail("", 2);
						tblmstimmunizationANS = dataProvider
								.gettblmstimmunizationANS("", 0);
						tblPNChomevisit_ANS = dataProvider
								.gettblPNChomevisit_ANS("", 0);
						if ((mstFPAns != null && mstFPAns.size() > 0)
								|| (mstFPFDetail != null && mstFPFDetail.size() > 0)
								|| (tblmstimmunizationANS != null && tblmstimmunizationANS
										.size() > 0)
								|| (tblPNChomevisit_ANS != null && tblPNChomevisit_ANS
										.size() > 0)) {

							exportFamilyPlanningdata();
							exportAnswerdata();
						} else {
							Toast.makeText(Synchronization.this,
									"Nothing for Upload", Toast.LENGTH_LONG)
									.show();
						}
					}

					catch (Exception exp) {
						downloadmsg = exp.getMessage();
						progressDialog.dismiss();
					}

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (iDataUploadfp == 1 && iDataUploadfpans == 1
									&& iDataUploadImmu == 1
									&& iDataUploadpncans == 1) {
								Toast.makeText(Synchronization.this,
										"Data upload successfully",
										Toast.LENGTH_LONG).show();
							} else {
								Toast.makeText(Synchronization.this,
										downloadmsg, Toast.LENGTH_LONG).show();
							}
						}
					});

					progressDialog.dismiss();

				}
			}.start();

		} else {
			Toast.makeText(Synchronization.this, "No Network Access",
					Toast.LENGTH_LONG).show();
		}

	}

	private void ClearFPData() {

		String sQueryDeletetblHHSurvey = null;
		sQueryDeletetblHHSurvey = "Delete from  tblmstFPQues";
		dataProvider.executeSql(sQueryDeletetblHHSurvey);
		String sQueryDeletetblmstimmunizationQues = null;
		sQueryDeletetblmstimmunizationQues = "Delete from  tblmstimmunizationQues";
		dataProvider.executeSql(sQueryDeletetblmstimmunizationQues);
		String sQueryDeleteq_bank = null;
		sQueryDeleteq_bank = "Delete from  tblmstPNCQues";
		dataProvider.executeSql(sQueryDeleteq_bank);
		String sQueryDeletetblmstANCQues = null;
		sQueryDeletetblmstANCQues = "Delete from  tblmstANCQues";
		dataProvider.executeSql(sQueryDeletetblmstANCQues);

		// String sQueryDeleteMstRoleData = null;
		// sQueryDeleteMstRoleData = "Delete from Tbl_HHFamilyMember";
		// dataProvider.executeSql(sQueryDeleteMstRoleData);

	}

	// private void ClearAnsData() {
	//
	// String sQueryDeletetblmstimmunizationANS = null;
	// sQueryDeletetblmstimmunizationANS = "Delete from  tblmstimmunizationANS";
	// dataProvider.executeSql(sQueryDeletetblmstimmunizationANS);
	// String sQueryDeletetblPNChomevisit_ANS = null;
	// sQueryDeletetblPNChomevisit_ANS = "Delete from  tblPNChomevisit_ANS";
	// dataProvider.executeSql(sQueryDeletetblPNChomevisit_ANS);
	//
	// }

	@SuppressWarnings("unused")
	public void importQuestion(String UserName, String Password) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "Questions");

		request.addProperty("AuthenticationToken", UserName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			ClearFPData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblmstFPQuesArray = jsonObj.getJSONArray("tblmstFPQues");
			for (int i = 0; i < tblmstFPQuesArray.length(); i++) {
				JSONObject FPQuesData = tblmstFPQuesArray.getJSONObject(i);
				int Q_id = 0;
				int grp = 0;
				int q_type = 0;
				String ftext = "";
				int y_qid = 0;
				int n_qid = 0;
				String oinfo = "";
				int def = 0;
				int finishid = 0;
				int pans = 0;
				int qvid = 0;
				int dispseq = 0;
				String oprompt = "";
				String Ans = "";

				if (FPQuesData.getString("Q_id") != null
						&& FPQuesData.getString("Q_id").length() > 0
						&& !FPQuesData.getString("Q_id").equalsIgnoreCase(
								"null")) {
					Q_id = Integer.valueOf(FPQuesData.getInt("Q_id"));
				}
				if (FPQuesData.getString("grp") != null
						&& FPQuesData.getString("grp").length() > 0
						&& !FPQuesData.getString("grp")
								.equalsIgnoreCase("null")) {
					grp = Integer.valueOf(FPQuesData.getInt("grp"));
				}
				if (FPQuesData.getString("q_type") != null
						&& FPQuesData.getString("q_type").length() > 0
						&& !FPQuesData.getString("q_type").equalsIgnoreCase(
								"null")) {
					q_type = Integer.valueOf(FPQuesData.getInt("q_type"));
				}
				if (FPQuesData.getString("y_qid") != null
						&& FPQuesData.getString("y_qid").length() > 0
						&& !FPQuesData.getString("y_qid").equalsIgnoreCase(
								"null")) {

					y_qid = Integer.valueOf(FPQuesData.getInt("y_qid"));
				}
				if (FPQuesData.getString("n_qid") != null
						&& FPQuesData.getString("n_qid").length() > 0
						&& !FPQuesData.getString("n_qid").equalsIgnoreCase(
								"null")) {
					n_qid = Integer.valueOf(FPQuesData.getInt("n_qid"));
				}
				if (FPQuesData.getString("def") != null
						&& FPQuesData.getString("def").length() > 0
						&& !FPQuesData.getString("def")
								.equalsIgnoreCase("null")) {

					def = Integer.valueOf(FPQuesData.getInt("def"));
				}
				if (FPQuesData.getString("finishid") != null
						&& FPQuesData.getString("finishid").length() > 0
						&& !FPQuesData.getString("finishid").equalsIgnoreCase(
								"null")) {
					finishid = Integer.valueOf(FPQuesData.getInt("finishid"));
				}
				if (FPQuesData.getString("pans") != null
						&& FPQuesData.getString("pans").length() > 0
						&& !FPQuesData.getString("pans").equalsIgnoreCase(
								"null")) {
					pans = Integer.valueOf(FPQuesData.getInt("pans"));
				}
				if (FPQuesData.getString("qvid") != null
						&& FPQuesData.getString("qvid").length() > 0
						&& !FPQuesData.getString("qvid").equalsIgnoreCase(
								"null")) {
					qvid = Integer.valueOf(FPQuesData.getInt("qvid"));
				}
				if (FPQuesData.getString("dispseq") != null
						&& FPQuesData.getString("dispseq").length() > 0
						&& !FPQuesData.getString("dispseq").equalsIgnoreCase(
								"null")) {
					dispseq = Integer.valueOf(FPQuesData.getInt("dispseq"));
				}

				oinfo = FPQuesData.getString("oinfo");
				ftext = FPQuesData.getString("ftext");
				oprompt = FPQuesData.getString("oprompt");
				Ans = FPQuesData.getString("Ans");

				String sql = "";
				sql = "Insert into tblmstFPQues( Q_id,grp,q_type,ftext,y_qid,n_qid,oinfo,def,finishid,pans,qvid,dispseq,oprompt,Ans)values("
						+ Q_id
						+ ","
						+ grp
						+ ","
						+ q_type
						+ ",'"
						+ ftext
						+ "',"
						+ y_qid
						+ ","
						+ n_qid
						+ ",'"
						+ oinfo
						+ "',"
						+ def
						+ ","
						+ finishid
						+ ","
						+ pans
						+ ","
						+ qvid
						+ ","
						+ dispseq
						+ ",'" + oprompt + "','" + Ans + "')";
				dataProvider.executeSql(sql);
				Log.e("IMPORT DATA", "tblmstFPQues ");

			}
			tblmstimmunizationQuesArray = jsonObj
					.getJSONArray("tblmstimmunizationQues");
			for (int i = 0; i < tblmstimmunizationQuesArray.length(); i++) {
				JSONObject ImmunizationQuesData = tblmstimmunizationQuesArray
						.getJSONObject(i);
				int Q_id = 0;
				int grp = 0;
				int q_type = 0;
				String ftext = "";
				int y_qid = 0;
				int n_qid = 0;
				String oinfo = "";
				int def = 0;
				int finishid = 0;
				int pans = 0;
				int qvid = 0;
				int dispseq = 0;
				String oprompt = "";
				String Ans = "";

				if (ImmunizationQuesData.getString("Q_id") != null
						&& ImmunizationQuesData.getString("Q_id").length() > 0
						&& !ImmunizationQuesData.getString("Q_id")
								.equalsIgnoreCase("null")) {
					Q_id = Integer.valueOf(ImmunizationQuesData.getInt("Q_id"));
				}
				if (ImmunizationQuesData.getString("grp") != null
						&& ImmunizationQuesData.getString("grp").length() > 0
						&& !ImmunizationQuesData.getString("grp")
								.equalsIgnoreCase("null")) {
					grp = Integer.valueOf(ImmunizationQuesData.getInt("grp"));
				}
				if (ImmunizationQuesData.getString("q_type") != null
						&& ImmunizationQuesData.getString("q_type").length() > 0
						&& !ImmunizationQuesData.getString("q_type")
								.equalsIgnoreCase("null")) {
					q_type = Integer.valueOf(ImmunizationQuesData
							.getInt("q_type"));
				}
				if (ImmunizationQuesData.getString("y_qid") != null
						&& ImmunizationQuesData.getString("y_qid").length() > 0
						&& !ImmunizationQuesData.getString("y_qid")
								.equalsIgnoreCase("null")) {

					y_qid = Integer.valueOf(ImmunizationQuesData
							.getInt("y_qid"));
				}
				if (ImmunizationQuesData.getString("n_qid") != null
						&& ImmunizationQuesData.getString("n_qid").length() > 0
						&& !ImmunizationQuesData.getString("n_qid")
								.equalsIgnoreCase("null")) {
					n_qid = Integer.valueOf(ImmunizationQuesData
							.getInt("n_qid"));
				}
				if (ImmunizationQuesData.getString("def") != null
						&& ImmunizationQuesData.getString("def").length() > 0
						&& !ImmunizationQuesData.getString("def")
								.equalsIgnoreCase("null")) {

					def = Integer.valueOf(ImmunizationQuesData.getInt("def"));
				}

				if (ImmunizationQuesData.getString("pans") != null
						&& ImmunizationQuesData.getString("pans").length() > 0
						&& !ImmunizationQuesData.getString("pans")
								.equalsIgnoreCase("null")) {
					pans = Integer.valueOf(ImmunizationQuesData.getInt("pans"));
				}
				if (ImmunizationQuesData.getString("qvid") != null
						&& ImmunizationQuesData.getString("qvid").length() > 0
						&& !ImmunizationQuesData.getString("qvid")
								.equalsIgnoreCase("null")) {
					qvid = Integer.valueOf(ImmunizationQuesData.getInt("qvid"));
				}
				if (ImmunizationQuesData.getString("dispseq") != null
						&& ImmunizationQuesData.getString("dispseq").length() > 0
						&& !ImmunizationQuesData.getString("dispseq")
								.equalsIgnoreCase("null")) {
					dispseq = Integer.valueOf(ImmunizationQuesData
							.getInt("dispseq"));
				}

				oinfo = ImmunizationQuesData.getString("oinfo");
				ftext = ImmunizationQuesData.getString("ftext");
				oprompt = ImmunizationQuesData.getString("oprompt");
				Ans = ImmunizationQuesData.getString("AnsField");

				String sql = "";
				sql = "Insert into tblmstimmunizationQues( Q_id,grp,q_type,ftext,y_qid,n_qid,oinfo,def,pans,qvid,dispseq,oprompt,AnsField)values("
						+ Q_id
						+ ","
						+ grp
						+ ","
						+ q_type
						+ ",'"
						+ ftext
						+ "',"
						+ y_qid
						+ ","
						+ n_qid
						+ ",'"
						+ oinfo
						+ "',"
						+ def
						+ ","
						+ pans
						+ ","
						+ qvid
						+ ","
						+ dispseq
						+ ",'"
						+ oprompt
						+ "','" + Ans + "')";
				dataProvider.executeSql(sql);
				Log.e("IMPORT DATA", "tblmstimmunizationQues ");

			}

			q_bankArray = jsonObj.getJSONArray("tblmstPNCQues");
			for (int i = 0; i < q_bankArray.length(); i++) {
				JSONObject q_bankData = q_bankArray.getJSONObject(i);
				int Q_id = 0;
				int Grp = 0;
				int q_type = 0;

				int HV1 = 0;
				int HV2 = 0;
				int HV3 = 0;
				int HV4 = 0;
				int HV5 = 0;
				int HV6 = 0;
				int HV7 = 0;
				int y_qid = 0;
				int n_qid = 0;
				String qtext = "";
				String oinfo = "";
				String AnsField = "";
				if (q_bankData.getString("Q_id") != null
						&& q_bankData.getString("Q_id").length() > 0
						&& !q_bankData.getString("Q_id").equalsIgnoreCase(
								"null")) {
					Q_id = Integer.valueOf(q_bankData.getInt("Q_id"));
				}
				if (q_bankData.getString("y_qid") != null
						&& q_bankData.getString("y_qid").length() > 0
						&& !q_bankData.getString("y_qid").equalsIgnoreCase(
								"null")) {
					y_qid = Integer.valueOf(q_bankData.getInt("y_qid"));
				}
				if (q_bankData.getString("n_qid") != null
						&& q_bankData.getString("n_qid").length() > 0
						&& !q_bankData.getString("n_qid").equalsIgnoreCase(
								"null")) {
					n_qid = Integer.valueOf(q_bankData.getInt("n_qid"));
				}
				if (q_bankData.getString("HV1") != null
						&& q_bankData.getString("HV1").length() > 0
						&& !q_bankData.getString("HV1")
								.equalsIgnoreCase("null")) {
					HV1 = Integer.valueOf(q_bankData.getInt("HV1"));
				}

				if (q_bankData.getString("HV2") != null
						&& q_bankData.getString("HV2").length() > 0
						&& !q_bankData.getString("HV2")
								.equalsIgnoreCase("null")) {
					HV2 = Integer.valueOf(q_bankData.getInt("HV2"));
				}

				if (q_bankData.getString("HV3") != null
						&& q_bankData.getString("HV3").length() > 0
						&& !q_bankData.getString("HV3")
								.equalsIgnoreCase("null")) {
					HV3 = Integer.valueOf(q_bankData.getInt("HV3"));
				}

				if (q_bankData.getString("HV4") != null
						&& q_bankData.getString("HV4").length() > 0
						&& !q_bankData.getString("HV4")
								.equalsIgnoreCase("null")) {
					HV4 = Integer.valueOf(q_bankData.getInt("HV4"));
				}

				if (q_bankData.getString("HV5") != null
						&& q_bankData.getString("HV5").length() > 0
						&& !q_bankData.getString("HV5")
								.equalsIgnoreCase("null")) {
					HV5 = Integer.valueOf(q_bankData.getInt("HV5"));
				}

				if (q_bankData.getString("HV6") != null
						&& q_bankData.getString("HV6").length() > 0
						&& !q_bankData.getString("HV6")
								.equalsIgnoreCase("null")) {
					HV6 = Integer.valueOf(q_bankData.getInt("HV6"));
				}

				if (q_bankData.getString("HV7") != null
						&& q_bankData.getString("HV7").length() > 0
						&& !q_bankData.getString("HV7")
								.equalsIgnoreCase("null")) {
					HV7 = Integer.valueOf(q_bankData.getInt("HV7"));
				}

				if (q_bankData.getString("Group") != null
						&& q_bankData.getString("Group").length() > 0
						&& !q_bankData.getString("Group").equalsIgnoreCase(
								"null")) {
					Grp = Integer.valueOf(q_bankData.getInt("Group"));
				}

				oinfo = q_bankData.getString("oinfo");
				qtext = q_bankData.getString("qtext");

				AnsField = q_bankData.getString("AnsField");

				String sql = "";
				sql = "Insert into tblmstPNCQues( Q_id,grp,q_type,Qtext,y_qid,n_qid,oinfo,AnsField,HV1,HV2,HV3,HV4,HV5,HV6,HV7)values("
						+ Q_id
						+ ","
						+ Grp
						+ ","
						+ q_type
						+ ",'"
						+ qtext
						+ "',"
						+ y_qid
						+ ","
						+ n_qid
						+ ",'"
						+ oinfo
						+ "','"
						+ AnsField
						+ "',"
						+ HV1
						+ ","
						+ HV2
						+ ","
						+ HV3
						+ ","
						+ HV4
						+ ","
						+ HV5 + "," + HV6 + "," + HV7 + ")";

				dataProvider.executeSql(sql);
				Log.e("IMPORT DATA", "tblmstPNCQues ");

			}
			tblmstANCQuesArray = jsonObj.getJSONArray("tblmstANCQues");
			for (int i = 0; i < tblmstANCQuesArray.length(); i++) {
				JSONObject q_bankData = tblmstANCQuesArray.getJSONObject(i);
				int Q_id = 0;
				int Grp = 0;
				int q_type = 0;

				int HV1 = 0;
				int HV2 = 0;
				int HV3 = 0;
				int HV4 = 0;

				int y_qid = 0;
				int n_qid = 0;
				String qtext = "";
				String ftext = "";
				String oinfo = "";
				String AnsField = "";
				String Audio_filename = "";
				Audio_filename = q_bankData.getString("audio_filename");
				if (q_bankData.getString("Q_id") != null
						&& q_bankData.getString("Q_id").length() > 0
						&& !q_bankData.getString("Q_id").equalsIgnoreCase(
								"null")) {
					Q_id = Integer.valueOf(q_bankData.getInt("Q_id"));
				}
				if (q_bankData.getString("y_qid") != null
						&& q_bankData.getString("y_qid").length() > 0
						&& !q_bankData.getString("y_qid").equalsIgnoreCase(
								"null")) {
					y_qid = Integer.valueOf(q_bankData.getInt("y_qid"));
				}
				if (q_bankData.getString("n_qid") != null
						&& q_bankData.getString("n_qid").length() > 0
						&& !q_bankData.getString("n_qid").equalsIgnoreCase(
								"null")) {
					n_qid = Integer.valueOf(q_bankData.getInt("n_qid"));
				}
				if (q_bankData.getString("HV1") != null
						&& q_bankData.getString("HV1").length() > 0
						&& !q_bankData.getString("HV1")
								.equalsIgnoreCase("null")) {
					HV1 = Integer.valueOf(q_bankData.getInt("HV1"));
				}

				if (q_bankData.getString("HV2") != null
						&& q_bankData.getString("HV2").length() > 0
						&& !q_bankData.getString("HV2")
								.equalsIgnoreCase("null")) {
					HV2 = Integer.valueOf(q_bankData.getInt("HV2"));
				}

				if (q_bankData.getString("HV3") != null
						&& q_bankData.getString("HV3").length() > 0
						&& !q_bankData.getString("HV3")
								.equalsIgnoreCase("null")) {
					HV3 = Integer.valueOf(q_bankData.getInt("HV3"));
				}

				if (q_bankData.getString("HV4") != null
						&& q_bankData.getString("HV4").length() > 0
						&& !q_bankData.getString("HV4")
								.equalsIgnoreCase("null")) {
					HV4 = Integer.valueOf(q_bankData.getInt("HV4"));
				}

				if (q_bankData.getString("grp") != null
						&& q_bankData.getString("grp").length() > 0
						&& !q_bankData.getString("grp")
								.equalsIgnoreCase("null")) {
					Grp = Integer.valueOf(q_bankData.getInt("grp"));
				}

				oinfo = q_bankData.getString("oinfo");
				qtext = q_bankData.getString("qtext");
				ftext = q_bankData.getString("ftext");

				AnsField = q_bankData.getString("AnsField");

				String sql = "";
				sql = "Insert into tblmstANCQues( Q_id,grp,q_type,Qtext,y_qid,n_qid,oinfo,AnsField,HV1,HV2,HV3,HV4,Ftext,Audio_filename)values("
						+ Q_id
						+ ","
						+ Grp
						+ ","
						+ q_type
						+ ",'"
						+ qtext
						+ "',"
						+ y_qid
						+ ","
						+ n_qid
						+ ",'"
						+ oinfo
						+ "','"
						+ AnsField
						+ "',"
						+ HV1
						+ ","
						+ HV2
						+ ","
						+ HV3
						+ ","
						+ HV4
						+ ",'"
						+ ftext + "','" + Audio_filename + "')";

				dataProvider.executeSql(sql);
				Log.e("IMPORT DATA", "tblmstANCQuesArray ");

			}

		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	@SuppressWarnings("unused")
	public void importAns(String UserName, String Password) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = GlobalString.METHOD_NAME_DOWNLOAD_MASTER;

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "counseling");

		request.addProperty("AuthenticationToken", UserName);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearAnsData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblmstimmunizationANSArray = jsonObj
					.getJSONArray("tblmstimmunizationANS");
			for (int i = 0; i < tblmstimmunizationANSArray.length(); i++) {
				JSONObject q_bankData = tblmstimmunizationANSArray
						.getJSONObject(i);
				String Q1 = "";
				int Q2 = 0;
				int Q3 = 0;
				int Q4 = 0;
				int Q5 = 0;
				int Q6 = 0;
				int Q7 = 0;
				int Q14 = 0;
				int Q25 = 0;
				int Q36 = 0;
				int Q47 = 0;
				int Q53 = 0;
				int AshaID = 0;
				int ANMID = 0;
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				String ImmunizationGUID = "";
				String ChildGUID = "";
				String TimeDuration = "";

				if (q_bankData.getString("Q4") != null
						&& q_bankData.getString("Q4").length() > 0
						&& !q_bankData.getString("Q4").equalsIgnoreCase("null")) {
					Q4 = Integer.valueOf(q_bankData.getInt("Q4"));
				}

				if (q_bankData.getString("Q2") != null
						&& q_bankData.getString("Q2").length() > 0
						&& !q_bankData.getString("Q2").equalsIgnoreCase("null")) {
					Q2 = Integer.valueOf(q_bankData.getInt("Q2"));
				}

				if (q_bankData.getString("Q3") != null
						&& q_bankData.getString("Q3").length() > 0
						&& !q_bankData.getString("Q3").equalsIgnoreCase("null")) {
					Q3 = Integer.valueOf(q_bankData.getInt("Q3"));
				}

				if (q_bankData.getString("Q5") != null
						&& q_bankData.getString("Q5").length() > 0
						&& !q_bankData.getString("Q5").equalsIgnoreCase("null")) {
					Q5 = Integer.valueOf(q_bankData.getInt("Q5"));
				}

				if (q_bankData.getString("Q6") != null
						&& q_bankData.getString("Q6").length() > 0
						&& !q_bankData.getString("Q6").equalsIgnoreCase("null")) {
					Q6 = Integer.valueOf(q_bankData.getInt("Q6"));
				}

				if (q_bankData.getString("Q7") != null
						&& q_bankData.getString("Q7").length() > 0
						&& !q_bankData.getString("Q7").equalsIgnoreCase("null")) {
					Q7 = Integer.valueOf(q_bankData.getInt("Q7"));
				}

				if (q_bankData.getString("Q14") != null
						&& q_bankData.getString("Q14").length() > 0
						&& !q_bankData.getString("Q14")
								.equalsIgnoreCase("null")) {
					Q14 = Integer.valueOf(q_bankData.getInt("Q14"));
				}

				if (q_bankData.getString("Q25") != null
						&& q_bankData.getString("Q25").length() > 0
						&& !q_bankData.getString("Q25")
								.equalsIgnoreCase("null")) {
					Q25 = Integer.valueOf(q_bankData.getInt("Q25"));
				}
				if (q_bankData.getString("Q36") != null
						&& q_bankData.getString("Q36").length() > 0
						&& !q_bankData.getString("Q36")
								.equalsIgnoreCase("null")) {
					Q36 = Integer.valueOf(q_bankData.getInt("Q36"));
				}
				if (q_bankData.getString("Q47") != null
						&& q_bankData.getString("Q47").length() > 0
						&& !q_bankData.getString("Q47")
								.equalsIgnoreCase("null")) {
					Q47 = Integer.valueOf(q_bankData.getInt("Q47"));
				}
				if (q_bankData.getString("Q53") != null
						&& q_bankData.getString("Q53").length() > 0
						&& !q_bankData.getString("Q53")
								.equalsIgnoreCase("null")) {
					Q53 = Integer.valueOf(q_bankData.getInt("Q53"));
				}
				if (q_bankData.getString("CreatedBy") != null
						&& q_bankData.getString("CreatedBy").length() > 0
						&& !q_bankData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(q_bankData.getInt("CreatedBy"));
				}
				if (q_bankData.getString("UpdatedBy") != null
						&& q_bankData.getString("UpdatedBy").length() > 0
						&& !q_bankData.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(q_bankData.getInt("UpdatedBy"));
				}
				if (q_bankData.getString("ANMID") != null
						&& q_bankData.getString("ANMID").length() > 0
						&& !q_bankData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(q_bankData.getInt("ANMID"));
				}
				if (q_bankData.getString("AshaID") != null
						&& q_bankData.getString("AshaID").length() > 0
						&& !q_bankData.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(q_bankData.getInt("AshaID"));
				}

				TimeDuration = q_bankData.getString("TimeDuration");
				ChildGUID = q_bankData.getString("ChildGUID");

				ImmunizationGUID = q_bankData.getString("ImmunizationGUID");
				CreatedOn = q_bankData.getString("CreatedOn");
				UpdatedOn = q_bankData.getString("UpdatedOn");

				String sqlcount = "select count(*) from tblmstimmunizationANS  ImmunizationGUID='"
						+ ImmunizationGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblmstimmunizationANS  ImmunizationGUID='"
						+ ImmunizationGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblmstimmunizationANS (AshaID,ANMID,	Q1,	Q2,	Q3,	Q4,	Q5,	Q6,	Q7,	Q14,	Q25,	Q36,	Q47,CreatedOn,	CreatedBy,	UpdatedOn,	UpdatedBy,	ImmunizationGUID,	ChildGUID,	Q53,	TimeDuration,IsEdited)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ Q1
							+ "',"
							+ Q2
							+ ","
							+ Q3
							+ ",	"
							+ Q4
							+ ",	"
							+ Q5
							+ ",	"
							+ Q6
							+ ",	"
							+ Q7
							+ ","
							+ Q14
							+ ","
							+ Q25
							+ ","
							+ Q36
							+ ","
							+ Q47
							+ ",'"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ",'"
							+ ImmunizationGUID
							+ "','"
							+ ChildGUID
							+ "',"
							+ Q53
							+ ",'" + TimeDuration + "',0)";

					dataProvider.executeSql(sql);
					Log.e("IMPORT DATA", "tblmstimmunizationANS ");
				} else {
					if (count1 == 0) {
						sql = "update tblmstimmunizationANS set Q1=" + Q1
								+ "',Q2=" + Q2 + ",Q3=" + Q3 + ",Q4=	" + Q4
								+ ",Q5=	" + Q5 + ",Q6=	" + Q6 + ",Q7=	" + Q7
								+ ",Q14=" + Q14 + ",Q25=" + Q25 + ",Q36=" + Q36
								+ ",Q47=" + Q47 + ",CreatedOn='" + CreatedOn
								+ "',CreatedBy=" + CreatedBy + ",UpdatedOn='"
								+ UpdatedOn + "',UpdatedBy=" + UpdatedBy
								+ ",Q53=" + Q53 + ",TimeDuration='"
								+ TimeDuration
								+ "',IsEdited=0 where ImmunizationGUID='"
								+ ImmunizationGUID + "'and ChildGUID='"
								+ ChildGUID + "'";

						dataProvider.executeSql(sql);
					}
				}

			}
			tblPNChomevisit_ANSArray = jsonObj
					.getJSONArray("tblPNChomevisit_ANS");
			for (int i = 0; i < tblPNChomevisit_ANSArray.length(); i++) {
				JSONObject q_bankData = tblPNChomevisit_ANSArray
						.getJSONObject(i);
				String PWGUID = "";
				String ChildGUID = "";
				String PNCGUID = "";
				int VisitNo = 0;
				String Q_0 = "";
				int Q_1 = 0;
				String Q_2 = "";
				String Q_3 = "";
				int Q_4 = 0;
				int Q_5 = 0;
				String Q_6 = "";
				String Q_7 = "";
				int Q_8 = 0;
				int Q_9 = 0;
				int Q_10 = 0;
				int Q_12 = 0;
				int Q_13 = 0;
				int Q_14 = 0;
				int Q_15 = 0;
				int Q_16 = 0;
				int Q_17 = 0;
				float Q_18 = 0;
				int Q_19 = 0;
				int Q_20 = 0;
				float Q_21 = 0;
				int Q_22 = 0;
				int Q_23 = 0;
				int Q_24 = 0;
				int Q_25 = 0;
				int Q_26 = 0;
				int Q_27 = 0;
				int Q_28 = 0;
				int Q_29 = 0;
				int Q_30 = 0;
				int Q_32 = 0;
				int Q_33 = 0;
				int Q_34 = 0;
				int Q_35 = 0;
				int Q_36 = 0;
				int Q_37 = 0;
				int Q_38 = 0;
				int Q_38a = 0;
				int Q_39 = 0;
				int Q_40 = 0;
				int Q_41 = 0;
				int Q_43 = 0;
				String Q_44 = "";
				int Q_45 = 0;
				int Q_46 = 0;
				int Q_47 = 0;
				String Q_48 = "";
				int Q_49 = 0;
				int Q_50 = 0;
				int Q_52 = 0;
				int Q_53 = 0;
				int Q_54 = 0;
				int Q_55 = 0;
				int Q_56 = 0;
				int Q_57 = 0;
				int Q_58 = 0;
				int Q_59 = 0;
				int Q_60 = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UpdatedBy = 0;
				int ANMID = 0;
				int AshaID = 0;
				String UpdatedOn = "";

				if (q_bankData.getString("Q_1") != null
						&& q_bankData.getString("Q_1").length() > 0
						&& !q_bankData.getString("Q_1")
								.equalsIgnoreCase("null")) {
					Q_1 = Integer.valueOf(q_bankData.getInt("Q_1"));
				}

				if (q_bankData.getString("Q_4") != null
						&& q_bankData.getString("Q_4").length() > 0
						&& !q_bankData.getString("Q_4")
								.equalsIgnoreCase("null")) {
					Q_4 = Integer.valueOf(q_bankData.getInt("Q_4"));
				}

				if (q_bankData.getString("Q_5") != null
						&& q_bankData.getString("Q_5").length() > 0
						&& !q_bankData.getString("Q_5")
								.equalsIgnoreCase("null")) {
					Q_5 = Integer.valueOf(q_bankData.getInt("Q_5"));
				}

				if (q_bankData.getString("Q_8") != null
						&& q_bankData.getString("Q_8").length() > 0
						&& !q_bankData.getString("Q_8")
								.equalsIgnoreCase("null")) {
					Q_8 = Integer.valueOf(q_bankData.getInt("Q_8"));
				}
				if (q_bankData.getString("Q_9") != null
						&& q_bankData.getString("Q_9").length() > 0
						&& !q_bankData.getString("Q_9")
								.equalsIgnoreCase("null")) {
					Q_9 = Integer.valueOf(q_bankData.getInt("Q_9"));
				}
				if (q_bankData.getString("Q_10") != null
						&& q_bankData.getString("Q_10").length() > 0
						&& !q_bankData.getString("Q_10").equalsIgnoreCase(
								"null")) {
					Q_10 = Integer.valueOf(q_bankData.getInt("Q_10"));
				}
				if (q_bankData.getString("Q_12") != null
						&& q_bankData.getString("Q_12").length() > 0
						&& !q_bankData.getString("Q_12").equalsIgnoreCase(
								"null")) {
					Q_12 = Integer.valueOf(q_bankData.getInt("Q_12"));
				}
				if (q_bankData.getString("Q_13") != null
						&& q_bankData.getString("Q_13").length() > 0
						&& !q_bankData.getString("Q_13").equalsIgnoreCase(
								"null")) {
					Q_13 = Integer.valueOf(q_bankData.getInt("Q_13"));
				}

				if (q_bankData.getString("Q_14") != null
						&& q_bankData.getString("Q_14").length() > 0
						&& !q_bankData.getString("Q_14").equalsIgnoreCase(
								"null")) {
					Q_14 = Integer.valueOf(q_bankData.getInt("Q_14"));
				}

				if (q_bankData.getString("Q_15") != null
						&& q_bankData.getString("Q_15").length() > 0
						&& !q_bankData.getString("Q_15").equalsIgnoreCase(
								"null")) {
					Q_15 = Integer.valueOf(q_bankData.getInt("Q_15"));
				}

				if (q_bankData.getString("Q_16") != null
						&& q_bankData.getString("Q_16").length() > 0
						&& !q_bankData.getString("Q_16").equalsIgnoreCase(
								"null")) {
					Q_16 = Integer.valueOf(q_bankData.getInt("Q_16"));
				}

				if (q_bankData.getString("Q_17") != null
						&& q_bankData.getString("Q_17").length() > 0
						&& !q_bankData.getString("Q_17").equalsIgnoreCase(
								"null")) {
					Q_17 = Integer.valueOf(q_bankData.getInt("Q_17"));
				}

				if (q_bankData.getString("Q_18") != null
						&& q_bankData.getString("Q_18").length() > 0
						&& !q_bankData.getString("Q_18").equalsIgnoreCase(
								"null")) {
					Q_18 = Float.valueOf(q_bankData.getString("Q_18"));
				}

				if (q_bankData.getString("Q_19") != null
						&& q_bankData.getString("Q_19").length() > 0
						&& !q_bankData.getString("Q_19").equalsIgnoreCase(
								"null")) {
					Q_19 = Integer.valueOf(q_bankData.getInt("Q_19"));
				}

				if (q_bankData.getString("Q_20") != null
						&& q_bankData.getString("Q_20").length() > 0
						&& !q_bankData.getString("Q_20").equalsIgnoreCase(
								"null")) {
					Q_20 = Integer.valueOf(q_bankData.getInt("Q_20"));
				}

				if (q_bankData.getString("Q_22") != null
						&& q_bankData.getString("Q_22").length() > 0
						&& !q_bankData.getString("Q_22").equalsIgnoreCase(
								"null")) {
					Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
				}
				if (q_bankData.getString("Q_23") != null
						&& q_bankData.getString("Q_23").length() > 0
						&& !q_bankData.getString("Q_23").equalsIgnoreCase(
								"null")) {
					Q_23 = Integer.valueOf(q_bankData.getInt("Q_23"));
				}
				if (q_bankData.getString("Q_24") != null
						&& q_bankData.getString("Q_24").length() > 0
						&& !q_bankData.getString("Q_24").equalsIgnoreCase(
								"null")) {
					Q_24 = Integer.valueOf(q_bankData.getInt("Q_24"));
				}
				if (q_bankData.getString("Q_25") != null
						&& q_bankData.getString("Q_25").length() > 0
						&& !q_bankData.getString("Q_25").equalsIgnoreCase(
								"null")) {
					Q_25 = Integer.valueOf(q_bankData.getInt("Q_25"));
				}
				if (q_bankData.getString("Q_26") != null
						&& q_bankData.getString("Q_26").length() > 0
						&& !q_bankData.getString("Q_26").equalsIgnoreCase(
								"null")) {
					Q_26 = Integer.valueOf(q_bankData.getInt("Q_26"));
				}
				if (q_bankData.getString("Q_27") != null
						&& q_bankData.getString("Q_27").length() > 0
						&& !q_bankData.getString("Q_27").equalsIgnoreCase(
								"null")) {
					Q_27 = Integer.valueOf(q_bankData.getInt("Q_27"));
				}
				if (q_bankData.getString("Q_28") != null
						&& q_bankData.getString("Q_28").length() > 0
						&& !q_bankData.getString("Q_28").equalsIgnoreCase(
								"null")) {
					Q_28 = Integer.valueOf(q_bankData.getInt("Q_28"));
				}
				if (q_bankData.getString("Q_29") != null
						&& q_bankData.getString("Q_29").length() > 0
						&& !q_bankData.getString("Q_29").equalsIgnoreCase(
								"null")) {
					Q_29 = Integer.valueOf(q_bankData.getInt("Q_29"));
				}
				if (q_bankData.getString("Q_30") != null
						&& q_bankData.getString("Q_30").length() > 0
						&& !q_bankData.getString("Q_30").equalsIgnoreCase(
								"null")) {
					Q_30 = Integer.valueOf(q_bankData.getInt("Q_30"));
				}
				if (q_bankData.getString("Q_32") != null
						&& q_bankData.getString("Q_32").length() > 0
						&& !q_bankData.getString("Q_32").equalsIgnoreCase(
								"null")) {
					Q_32 = Integer.valueOf(q_bankData.getInt("Q_32"));
				}
				if (q_bankData.getString("Q_33") != null
						&& q_bankData.getString("Q_33").length() > 0
						&& !q_bankData.getString("Q_33").equalsIgnoreCase(
								"null")) {
					Q_33 = Integer.valueOf(q_bankData.getInt("Q_33"));
				}
				if (q_bankData.getString("Q_22") != null
						&& q_bankData.getString("Q_22").length() > 0
						&& !q_bankData.getString("Q_22").equalsIgnoreCase(
								"null")) {
					Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
				}
				if (q_bankData.getString("Q_34") != null
						&& q_bankData.getString("Q_34").length() > 0
						&& !q_bankData.getString("Q_34").equalsIgnoreCase(
								"null")) {
					Q_34 = Integer.valueOf(q_bankData.getInt("Q_34"));
				}
				if (q_bankData.getString("Q_35") != null
						&& q_bankData.getString("Q_35").length() > 0
						&& !q_bankData.getString("Q_35").equalsIgnoreCase(
								"null")) {
					Q_35 = Integer.valueOf(q_bankData.getInt("Q_35"));
				}
				if (q_bankData.getString("Q_36") != null
						&& q_bankData.getString("Q_36").length() > 0
						&& !q_bankData.getString("Q_36").equalsIgnoreCase(
								"null")) {
					Q_36 = Integer.valueOf(q_bankData.getInt("Q_36"));
				}
				if (q_bankData.getString("Q_37") != null
						&& q_bankData.getString("Q_37").length() > 0
						&& !q_bankData.getString("Q_37").equalsIgnoreCase(
								"null")) {
					Q_37 = Integer.valueOf(q_bankData.getInt("Q_37"));
				}
				if (q_bankData.getString("Q_38") != null
						&& q_bankData.getString("Q_38").length() > 0
						&& !q_bankData.getString("Q_38").equalsIgnoreCase(
								"null")) {
					Q_38 = Integer.valueOf(q_bankData.getInt("Q_38"));
				}
				if (q_bankData.getString("Q_38a") != null
						&& q_bankData.getString("Q_38a").length() > 0
						&& !q_bankData.getString("Q_38a").equalsIgnoreCase(
								"null")) {
					Q_38a = Integer.valueOf(q_bankData.getInt("Q_38a"));
				}
				if (q_bankData.getString("Q_39") != null
						&& q_bankData.getString("Q_39").length() > 0
						&& !q_bankData.getString("Q_39").equalsIgnoreCase(
								"null")) {
					Q_39 = Integer.valueOf(q_bankData.getInt("Q_39"));
				}
				if (q_bankData.getString("Q_40") != null
						&& q_bankData.getString("Q_40").length() > 0
						&& !q_bankData.getString("Q_40").equalsIgnoreCase(
								"null")) {
					Q_40 = Integer.valueOf(q_bankData.getInt("Q_40"));
				}
				if (q_bankData.getString("Q_41") != null
						&& q_bankData.getString("Q_41").length() > 0
						&& !q_bankData.getString("Q_41").equalsIgnoreCase(
								"null")) {
					Q_41 = Integer.valueOf(q_bankData.getInt("Q_41"));
				}
				if (q_bankData.getString("Q_43") != null
						&& q_bankData.getString("Q_43").length() > 0
						&& !q_bankData.getString("Q_43").equalsIgnoreCase(
								"null")) {
					Q_43 = Integer.valueOf(q_bankData.getInt("Q_43"));
				}
				if (q_bankData.getString("Q_45") != null
						&& q_bankData.getString("Q_45").length() > 0
						&& !q_bankData.getString("Q_45").equalsIgnoreCase(
								"null")) {
					Q_45 = Integer.valueOf(q_bankData.getInt("Q_45"));
				}
				if (q_bankData.getString("Q_46") != null
						&& q_bankData.getString("Q_46").length() > 0
						&& !q_bankData.getString("Q_46").equalsIgnoreCase(
								"null")) {
					Q_46 = Integer.valueOf(q_bankData.getInt("Q_46"));
				}
				if (q_bankData.getString("Q_47") != null
						&& q_bankData.getString("Q_47").length() > 0
						&& !q_bankData.getString("Q_47").equalsIgnoreCase(
								"null")) {
					Q_47 = Integer.valueOf(q_bankData.getInt("Q_47"));
				}
				if (q_bankData.getString("Q_49") != null
						&& q_bankData.getString("Q_49").length() > 0
						&& !q_bankData.getString("Q_49").equalsIgnoreCase(
								"null")) {
					Q_49 = Integer.valueOf(q_bankData.getInt("Q_49"));
				}
				if (q_bankData.getString("Q_50") != null
						&& q_bankData.getString("Q_50").length() > 0
						&& !q_bankData.getString("Q_50").equalsIgnoreCase(
								"null")) {
					Q_50 = Integer.valueOf(q_bankData.getInt("Q_50"));
				}
				if (q_bankData.getString("Q_52") != null
						&& q_bankData.getString("Q_52").length() > 0
						&& !q_bankData.getString("Q_52").equalsIgnoreCase(
								"null")) {
					Q_52 = Integer.valueOf(q_bankData.getInt("Q_52"));
				}
				if (q_bankData.getString("Q_53") != null
						&& q_bankData.getString("Q_53").length() > 0
						&& !q_bankData.getString("Q_53").equalsIgnoreCase(
								"null")) {
					Q_53 = Integer.valueOf(q_bankData.getInt("Q_53"));
				}
				if (q_bankData.getString("Q_54") != null
						&& q_bankData.getString("Q_54").length() > 0
						&& !q_bankData.getString("Q_54").equalsIgnoreCase(
								"null")) {
					Q_54 = Integer.valueOf(q_bankData.getInt("Q_54"));
				}
				if (q_bankData.getString("Q_55") != null
						&& q_bankData.getString("Q_55").length() > 0
						&& !q_bankData.getString("Q_55").equalsIgnoreCase(
								"null")) {
					Q_55 = Integer.valueOf(q_bankData.getInt("Q_55"));
				}
				if (q_bankData.getString("Q_56") != null
						&& q_bankData.getString("Q_56").length() > 0
						&& !q_bankData.getString("Q_56").equalsIgnoreCase(
								"null")) {
					Q_56 = Integer.valueOf(q_bankData.getInt("Q_56"));
				}
				if (q_bankData.getString("Q_57") != null
						&& q_bankData.getString("Q_57").length() > 0
						&& !q_bankData.getString("Q_57").equalsIgnoreCase(
								"null")) {
					Q_57 = Integer.valueOf(q_bankData.getInt("Q_57"));
				}
				if (q_bankData.getString("Q_58") != null
						&& q_bankData.getString("Q_58").length() > 0
						&& !q_bankData.getString("Q_58").equalsIgnoreCase(
								"null")) {
					Q_58 = Integer.valueOf(q_bankData.getInt("Q_58"));
				}
				if (q_bankData.getString("Q_59") != null
						&& q_bankData.getString("Q_59").length() > 0
						&& !q_bankData.getString("Q_59").equalsIgnoreCase(
								"null")) {
					Q_59 = Integer.valueOf(q_bankData.getInt("Q_59"));
				}
				if (q_bankData.getString("Q_60") != null
						&& q_bankData.getString("Q_60").length() > 0
						&& !q_bankData.getString("Q_60").equalsIgnoreCase(
								"null")) {
					Q_60 = Integer.valueOf(q_bankData.getInt("Q_60"));
				}
				if (q_bankData.getString("Q_21") != null
						&& q_bankData.getString("Q_21").length() > 0
						&& !q_bankData.getString("Q_21").equalsIgnoreCase(
								"null")) {
					Q_21 = Integer.valueOf(q_bankData.getInt("Q_21"));
				}
				if (q_bankData.getString("ANMID") != null
						&& q_bankData.getString("ANMID").length() > 0
						&& !q_bankData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(q_bankData.getInt("ANMID"));
				}
				if (q_bankData.getString("AshaID") != null
						&& q_bankData.getString("AshaID").length() > 0
						&& !q_bankData.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(q_bankData.getInt("AshaID"));
				}

				if (q_bankData.getString("CreatedBy") != null
						&& q_bankData.getString("CreatedBy").length() > 0
						&& !q_bankData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(q_bankData.getInt("CreatedBy"));
				}
				if (q_bankData.getString("UpdatedBy") != null
						&& q_bankData.getString("UpdatedBy").length() > 0
						&& !q_bankData.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(q_bankData.getInt("UpdatedBy"));
				}

				Q_0 = q_bankData.getString("Q_0");
				Q_2 = q_bankData.getString("Q_2");
				Q_3 = q_bankData.getString("Q_3");

				Q_6 = q_bankData.getString("Q_6");
				Q_7 = q_bankData.getString("Q_7");
				Q_44 = q_bankData.getString("Q_44");
				Q_48 = q_bankData.getString("Q_48");
				ChildGUID = q_bankData.getString("ChildGUID");

				PWGUID = q_bankData.getString("PWGUID");
				CreatedOn = q_bankData.getString("CreatedOn");
				UpdatedOn = q_bankData.getString("UpdatedOn");

				String sqlcount = "select count(*) from tblPNChomevisit_ANS  PNCGUID='"
						+ PNCGUID + "' and ChildGUID='" + ChildGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblPNChomevisit_ANS  PNCGUID='"
						+ PNCGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblPNChomevisit_ANS (AshaID,ANMID,PWGUID,ChildGUID,PNCGUID,VisitNo,Q_0,Q_1,Q_2,Q_3,Q_4,Q_5,Q_6,Q_7,Q_8,Q_9,Q_10,Q_12,Q_13,Q_14,Q_15,Q_16,Q_17,Q_18,Q_19,Q_20,Q_21,Q_22,Q_23,Q_24,Q_25,Q_26,Q_27,Q_28,Q_29,Q_30,Q_32,Q_33,Q_34,Q_35,Q_36,Q_37,Q_38,Q_38a,Q_39,Q_40,Q_41,Q_43,Q_44,Q_45,Q_46,Q_47,Q_48,Q_49,Q_50,Q_52,Q_53,Q_54,Q_55,Q_56,Q_57,Q_58,Q_59,Q_60,CreatedBy,CreatedOn,UpdatedBy,UpdatedOn,Audio_filename,IsEdited)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ PWGUID
							+ "','"
							+ ChildGUID
							+ "',	'"
							+ PNCGUID
							+ "',	"
							+ VisitNo
							+ ",	'"
							+ Q_0
							+ "',	"
							+ Q_1
							+ ",	'"
							+ Q_2
							+ "',	'"
							+ Q_3
							+ "',	"
							+ Q_4
							+ ",	"
							+ Q_5
							+ ",	"
							+ Q_6
							+ ",	'"
							+ Q_7
							+ "',"
							+ Q_8
							+ ","
							+ Q_9
							+ ","
							+ Q_10
							+ ","
							+ Q_12
							+ ","
							+ Q_13
							+ ",	"
							+ Q_14
							+ ","
							+ Q_15
							+ ","
							+ Q_16
							+ ","
							+ Q_17
							+ ","
							+ Q_18
							+ ","
							+ Q_19
							+ ","
							+ Q_20
							+ ","
							+ Q_21
							+ ","
							+ Q_22
							+ ","
							+ Q_23
							+ ","
							+ Q_24
							+ ",	"
							+ Q_25
							+ ","
							+ Q_26
							+ ",	"
							+ Q_27
							+ ",	"
							+ Q_28
							+ ",	"
							+ Q_29
							+ ",	"
							+ Q_30
							+ ",	"
							+ Q_32
							+ ",	"
							+ Q_33
							+ ",	"
							+ Q_34
							+ ",	"
							+ Q_35
							+ ",	"
							+ Q_36
							+ ",	"
							+ Q_37
							+ ",	"
							+ Q_38
							+ ","
							+ Q_38a
							+ ",	"
							+ Q_39
							+ ",	"
							+ Q_40
							+ ",	"
							+ Q_41
							+ ",	"
							+ Q_43
							+ ",	'"
							+ Q_44
							+ "',	"
							+ Q_45
							+ ",	"
							+ Q_46
							+ ",	"
							+ Q_47
							+ ",	'"
							+ Q_48
							+ "',	"
							+ Q_49
							+ ",	"
							+ Q_50
							+ ",	"
							+ Q_52
							+ ",	"
							+ Q_53
							+ ",	"
							+ Q_54
							+ ",	"
							+ Q_55
							+ ",	"
							+ Q_56
							+ ",	"
							+ Q_57
							+ ",	"
							+ Q_58
							+ ",	"
							+ Q_59
							+ ",	"
							+ Q_60
							+ ",	"
							+ CreatedBy
							+ ",	'"
							+ CreatedOn
							+ "',	" + UpdatedBy + ",	'" + UpdatedOn + "',0)";

					dataProvider.executeSql(sql);
				} else {
					if (count == 0) {
						sql = "update tblPNChomevisit_ANS set PWGUID='"
								+ PWGUID + "',	VisitNo=" + VisitNo + ",	Q_0='"
								+ Q_0 + "',Q_1=	" + Q_1 + ",Q_2=	'" + Q_2
								+ "',Q_3=	'" + Q_3 + "',Q_4=	" + Q_4 + ",Q_5=	"
								+ Q_5 + ",Q_6=	" + Q_6 + ",Q_7=	'" + Q_7
								+ "',Q_8=" + Q_8 + ",Q_9=" + Q_9 + ",Q_10="
								+ Q_10 + ",Q_12=" + Q_12 + ",Q_13=" + Q_13
								+ ",Q_14=	" + Q_14 + ",Q_15=" + Q_15 + ",Q_16="
								+ Q_16 + ",Q_17=" + Q_17 + ",Q_18=" + Q_18
								+ ",Q_19=" + Q_19 + ",Q_20=" + Q_20 + ",Q_21="
								+ Q_21 + ",Q_22=" + Q_22 + ",Q_23=" + Q_23
								+ ",Q_24=" + Q_24 + ",	Q_25=" + Q_25 + ",Q_26="
								+ Q_26 + ",Q_27=	" + Q_27 + ",Q_28=	" + Q_28
								+ ",Q_29=	" + Q_29 + ",Q_30=	" + Q_30
								+ ",Q_32=	" + Q_32 + ",Q_33=	" + Q_33
								+ ",Q_34=	" + Q_34 + ",Q_35=	" + Q_35
								+ ",Q_36=	" + Q_36 + ",Q_37=	" + Q_37
								+ ",Q_38=	" + Q_38 + ",Q_38a=" + Q_38a
								+ ",Q_39=	" + Q_39 + ",Q_40=	" + Q_40
								+ ",	Q_41=" + Q_41 + ",Q_43=	" + Q_43
								+ ",Q_44=	'" + Q_44 + "',Q_45=	" + Q_45
								+ ",Q_46=	" + Q_46 + ",Q_47=	" + Q_47
								+ ",Q_48=	'" + Q_48 + "',Q_49=	" + Q_49
								+ ",Q_50=	" + Q_50 + ",Q_52=	" + Q_52
								+ ",Q_53=	" + Q_53 + ",Q_54=	" + Q_54
								+ ",Q_55=	" + Q_55 + ",Q_56=	" + Q_56
								+ ",Q_57=	" + Q_57 + ",Q_58=	" + Q_58
								+ ",Q_59=	" + Q_59 + ",Q_60=	" + Q_60
								+ ",CreatedBy=	" + CreatedBy + ",CreatedOn=	'"
								+ CreatedOn + "',UpdatedBy=	" + UpdatedBy
								+ ",	UpdatedOn='" + UpdatedOn + "',AshaID="
								+ ANMID + ",ANMID=" + ANMID
								+ "	,IsEdited=0 where ChildGUID='" + ChildGUID
								+ "',	PNCGUID='" + PNCGUID + "'";
					}
				}
				Log.e("IMPORT DATA", "tblPNChomevisit_ANS ");

			}
			tblmstFPAnsArray = jsonObj.getJSONArray("tblmstFPAns");
			for (int i = 0; i < tblmstFPAnsArray.length(); i++) {
				JSONObject block = tblmstFPAnsArray.getJSONObject(i);
				String FPAns_Guid = "";
				String WomenName_Guid = "";
				String VisitDate = "";
				String Q1 = "";
				String Q5 = "";
				String Q6 = "";
				String Q7 = "";
				String Q8 = "";
				String Q24 = "";
				String Q36 = "";
				String Q39 = "";
				String Q52 = "";
				String Q56 = "";
				String Q59 = "";
				String Q60 = "";
				String Q61 = "";
				String Q62 = "";
				String Q20 = "";
				String Q21 = "";
				String Q37 = "";
				String Q57 = "";
				String Q76 = "";
				int AshaID = 0;
				int ANMID = 0;
				int IsEdited = 0;
				int IsUploaded = 0;
				if (block.getString("AshaID") != null
						&& block.getString("AshaID").length() > 0
						&& !block.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(block.getInt("AshaID"));
				}
				if (block.getString("ANMID") != null
						&& block.getString("ANMID").length() > 0
						&& !block.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(block.getInt("ANMID"));
				}

				FPAns_Guid = block.getString("FPAns_Guid");
				WomenName_Guid = block.getString("WomenName_Guid");
				VisitDate = block.getString("VisitDate");
				Q1 = block.getString("Q1");
				Q5 = block.getString("Q5");
				Q6 = block.getString("Q6");
				Q7 = block.getString("Q7");
				Q8 = block.getString("Q8");
				Q24 = block.getString("Q24");
				Q36 = block.getString("Q36");
				Q39 = block.getString("Q39");
				Q52 = block.getString("Q52");
				Q56 = block.getString("Q56");
				Q59 = block.getString("Q59");
				Q60 = block.getString("Q60");
				Q61 = block.getString("Q61");
				Q62 = block.getString("Q62");
				Q20 = block.getString("Q20");
				Q21 = block.getString("Q21");
				Q37 = block.getString("Q37");
				Q57 = block.getString("Q57");
				Q76 = block.getString("Q76");

				String sql = "";
				int count = 0;
				String sql1 = "select count(*) from tblFP_visit where FPAns_Guid='"
						+ FPAns_Guid + "' ";
				count = dataProvider.getMaxRecord(sql1);

				if (count == 0) {
					sql = "Insert into tblFP_visit( FPAns_Guid,WomenName_Guid,VisitDate,Q1,Q5,Q6,Q7,Q8,Q24,Q36,Q39,Q52,Q56,Q59,Q60,Q61,Q62,Q20,Q21,Q37,Q57,Q76,AshaID,ANMID,IsEdited,IsUploaded)values('"
							+ FPAns_Guid
							+ "','"
							+ WomenName_Guid
							+ "','"
							+ VisitDate
							+ "','"
							+ Q1
							+ "','"
							+ Q5
							+ "','"
							+ Q6
							+ "','"
							+ Q7
							+ "','"
							+ Q8
							+ "','"
							+ Q24
							+ "','"
							+ Q36
							+ "','"
							+ Q39
							+ "','"
							+ Q52
							+ "','"
							+ Q56
							+ "','"
							+ Q59
							+ "','"
							+ Q60
							+ "','"
							+ Q61
							+ "','"
							+ Q62
							+ "','"
							+ Q20
							+ "','"
							+ Q21
							+ "','"
							+ Q37
							+ "','"
							+ Q57
							+ "','"
							+ Q76
							+ "',"
							+ AshaID
							+ ","
							+ ANMID + ",0,0)";
				}
				dataProvider.executeSql(sql);

			}
			tblmstFPFDetailArray = jsonObj.getJSONArray("tblmstFPFDetail");
			for (int i = 0; i < tblmstFPFDetailArray.length(); i++) {
				JSONObject block = tblmstFPFDetailArray.getJSONObject(i);
				String WomenName = "";
				String Hushband_name = "";
				String PF_date = "";
				String CurrF_date = "";
				int MethodAdopted = 0;
				int Pregnant = 0;
				int Pregnant_test = 0;
				String FPF_Guid = "";
				String WomenName_Guid = "";
				String Hushband_Guid = "";
				String MethodadoptedDate = "";

				int AshaID = 0;
				int ANMID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				if (block.getString("MethodAdopted") != null
						&& block.getString("MethodAdopted").length() > 0
						&& !block.getString("MethodAdopted").equalsIgnoreCase(
								"null")) {
					MethodAdopted = Integer.valueOf(block
							.getInt("MethodAdopted"));
				}
				if (block.getString("Pregnant") != null
						&& block.getString("Pregnant").length() > 0
						&& !block.getString("Pregnant")
								.equalsIgnoreCase("null")) {
					Pregnant = Integer.valueOf(block.getInt("Pregnant"));
				}
				if (block.getString("Pregnant_test") != null
						&& block.getString("Pregnant_test").length() > 0
						&& !block.getString("Pregnant_test").equalsIgnoreCase(
								"null")) {
					Pregnant_test = Integer.valueOf(block
							.getInt("Pregnant_test"));
				}
				if (block.getString("AshaID") != null
						&& block.getString("AshaID").length() > 0
						&& !block.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(block.getInt("AshaID"));
				}
				if (block.getString("ANMID") != null
						&& block.getString("ANMID").length() > 0
						&& !block.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(block.getInt("ANMID"));
				}
				WomenName = block.getString("WomenName");
				Hushband_name = block.getString("Hushband_name");
				PF_date = block.getString("PF_date");
				CurrF_date = block.getString("CurrF_date");
				FPF_Guid = block.getString("FPF_Guid");
				WomenName_Guid = block.getString("WomenName_Guid");
				Hushband_Guid = block.getString("Hushband_Guid");
				MethodadoptedDate = block.getString("MethodadoptedDate");
				if (block.getString("CreatedBy") != null
						&& block.getString("CreatedBy").length() > 0
						&& !block.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(block.getInt("CreatedBy"));
				}
				CreatedOn = block.getString("CreatedOn");

				int count = 0;
				String sql1 = "select count(*) from tblFP_followup where FPF_Guid='"
						+ FPF_Guid + "' ";
				count = dataProvider.getMaxRecord(sql1);
				String sql = "";
				if (count == 0) {
					sql = "Insert into tblFP_followup(WomenName,Hushband_name,PF_date,CurrF_date,MethodAdopted,Pregnant,Pregnant_test,FPF_Guid,WomenName_Guid,Hushband_Guid, AshaID,ANMID,IsEdited,IsUploaded,MethodadoptedDate)values('"
							+ WomenName
							+ "','"
							+ Hushband_name
							+ "','"
							+ PF_date
							+ "','"
							+ CurrF_date
							+ "','"
							+ MethodAdopted
							+ "','"
							+ Pregnant
							+ "','"
							+ Pregnant_test
							+ "','"
							+ FPF_Guid
							+ "','"
							+ WomenName_Guid
							+ "','"
							+ Hushband_Guid
							+ "','"
							+ AshaID
							+ "','"
							+ ANMID
							+ "',0,0,'"
							+ MethodadoptedDate + "')";
				}
				dataProvider.executeSql(sql);

			}
			downloadmsg = "data download successfully";
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblmstimmunizationANS(String UserName, String Password,
			String ashaid) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblmstimmunizationANS");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearAnsData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblmstimmunizationANSArray = jsonObj
					.getJSONArray("tblmstimmunizationANS");
			for (int i = 0; i < tblmstimmunizationANSArray.length(); i++) {
				JSONObject q_bankData = tblmstimmunizationANSArray
						.getJSONObject(i);
				String Q1 = "";
				int Q2 = 0;
				int Q3 = 0;
				int Q4 = 0;
				int Q5 = 0;
				int Q6 = 0;
				int Q7 = 0;
				int Q14 = 0;
				int Q25 = 0;
				int Q36 = 0;
				int Q47 = 0;
				int Q53 = 0;
				int AshaID = 0;
				int ANMID = 0;
				String CreatedOn = "";
				int CreatedBy = 0;
				String UpdatedOn = "";
				int UpdatedBy = 0;
				String ImmunizationGUID = "";
				String ChildGUID = "";
				String TimeDuration = "";

				int Q13 = 0;
				int Q21 = 0;
				int Q22 = 0;
				int Q24 = 0;
				int Q27 = 0;
				int Q37 = 0;
				int Q45 = 0;
				int Q51 = 0;
				int Q61 = 0;
				int Q70 = 0;
				int Q73 = 0;
				int Q75 = 0;
				int Q85 = 0;
				int Q86 = 0;
				int Q94 = 0;
				int Q111 = 0;
				int Q112 = 0;
				int Q117 = 0;
				int Q124 = 0;
				int Q125 = 0;
				int Q46 = 0;
				int Q146 = 0;
				int Q69 = 0;
				int Q48 = 0;
				int Q145 = 0;
				int Q148 = 0;

				if (q_bankData.getString("Q4") != null
						&& q_bankData.getString("Q4").length() > 0
						&& !q_bankData.getString("Q4").equalsIgnoreCase("null")) {
					Q4 = Integer.valueOf(q_bankData.getInt("Q4"));
				}

				if (q_bankData.getString("Q2") != null
						&& q_bankData.getString("Q2").length() > 0
						&& !q_bankData.getString("Q2").equalsIgnoreCase("null")) {
					Q2 = Integer.valueOf(q_bankData.getInt("Q2"));
				}

				if (q_bankData.getString("Q3") != null
						&& q_bankData.getString("Q3").length() > 0
						&& !q_bankData.getString("Q3").equalsIgnoreCase("null")) {
					Q3 = Integer.valueOf(q_bankData.getInt("Q3"));
				}

				if (q_bankData.getString("Q5") != null
						&& q_bankData.getString("Q5").length() > 0
						&& !q_bankData.getString("Q5").equalsIgnoreCase("null")) {
					Q5 = Integer.valueOf(q_bankData.getInt("Q5"));
				}

				if (q_bankData.getString("Q6") != null
						&& q_bankData.getString("Q6").length() > 0
						&& !q_bankData.getString("Q6").equalsIgnoreCase("null")) {
					Q6 = Integer.valueOf(q_bankData.getInt("Q6"));
				}

				if (q_bankData.getString("Q7") != null
						&& q_bankData.getString("Q7").length() > 0
						&& !q_bankData.getString("Q7").equalsIgnoreCase("null")) {
					Q7 = Integer.valueOf(q_bankData.getInt("Q7"));
				}
				if (q_bankData.getString("Q1") != null
						&& q_bankData.getString("Q1").length() > 0
						&& !q_bankData.getString("Q1").equalsIgnoreCase("null")) {
					Q1 = q_bankData.getString("Q1");
				}

				if (q_bankData.getString("Q14") != null
						&& q_bankData.getString("Q14").length() > 0
						&& !q_bankData.getString("Q14")
								.equalsIgnoreCase("null")) {
					Q14 = Integer.valueOf(q_bankData.getInt("Q14"));
				}

				if (q_bankData.getString("Q25") != null
						&& q_bankData.getString("Q25").length() > 0
						&& !q_bankData.getString("Q25")
								.equalsIgnoreCase("null")) {
					Q25 = Integer.valueOf(q_bankData.getInt("Q25"));
				}
				if (q_bankData.getString("Q36") != null
						&& q_bankData.getString("Q36").length() > 0
						&& !q_bankData.getString("Q36")
								.equalsIgnoreCase("null")) {
					Q36 = Integer.valueOf(q_bankData.getInt("Q36"));
				}
				if (q_bankData.getString("Q47") != null
						&& q_bankData.getString("Q47").length() > 0
						&& !q_bankData.getString("Q47")
								.equalsIgnoreCase("null")) {
					Q47 = Integer.valueOf(q_bankData.getInt("Q47"));
				}
				if (q_bankData.getString("Q53") != null
						&& q_bankData.getString("Q53").length() > 0
						&& !q_bankData.getString("Q53")
								.equalsIgnoreCase("null")) {
					Q53 = Integer.valueOf(q_bankData.getInt("Q53"));
				}
				if (q_bankData.getString("CreatedBy") != null
						&& q_bankData.getString("CreatedBy").length() > 0
						&& !q_bankData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(q_bankData.getInt("CreatedBy"));
				}
				if (q_bankData.getString("UpdatedBy") != null
						&& q_bankData.getString("UpdatedBy").length() > 0
						&& !q_bankData.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(q_bankData.getInt("UpdatedBy"));
				}
				if (q_bankData.getString("ANMID") != null
						&& q_bankData.getString("ANMID").length() > 0
						&& !q_bankData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(q_bankData.getInt("ANMID"));
				}
				if (q_bankData.getString("AshaID") != null
						&& q_bankData.getString("AshaID").length() > 0
						&& !q_bankData.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(q_bankData.getInt("AshaID"));
				}

				TimeDuration = q_bankData.getString("TimeDuration");
				ChildGUID = q_bankData.getString("ChildGUID");

				ImmunizationGUID = q_bankData.getString("ImmunizationGUID");
				CreatedOn = q_bankData.getString("CreatedOn");
				UpdatedOn = q_bankData.getString("UpdatedOn");
				if (q_bankData.getString("Q13") != null
						&& q_bankData.getString("Q13").length() > 0
						&& !q_bankData.getString("Q13")
								.equalsIgnoreCase("null")) {
					Q13 = Integer.valueOf(q_bankData.getInt("Q13"));
				}
				if (q_bankData.getString("Q21") != null
						&& q_bankData.getString("Q21").length() > 0
						&& !q_bankData.getString("Q21")
								.equalsIgnoreCase("null")) {
					Q21 = Integer.valueOf(q_bankData.getInt("Q21"));
				}
				if (q_bankData.getString("Q22") != null
						&& q_bankData.getString("Q22").length() > 0
						&& !q_bankData.getString("Q22")
								.equalsIgnoreCase("null")) {
					Q22 = Integer.valueOf(q_bankData.getInt("Q22"));
				}
				if (q_bankData.getString("Q24") != null
						&& q_bankData.getString("Q24").length() > 0
						&& !q_bankData.getString("Q24")
								.equalsIgnoreCase("null")) {
					Q24 = Integer.valueOf(q_bankData.getInt("Q24"));
				}
				if (q_bankData.getString("Q27") != null
						&& q_bankData.getString("Q27").length() > 0
						&& !q_bankData.getString("Q27")
								.equalsIgnoreCase("null")) {
					Q27 = Integer.valueOf(q_bankData.getInt("Q27"));
				}
				if (q_bankData.getString("Q37") != null
						&& q_bankData.getString("Q37").length() > 0
						&& !q_bankData.getString("Q37")
								.equalsIgnoreCase("null")) {
					Q37 = Integer.valueOf(q_bankData.getInt("Q37"));
				}
				if (q_bankData.getString("Q45") != null
						&& q_bankData.getString("Q45").length() > 0
						&& !q_bankData.getString("Q45")
								.equalsIgnoreCase("null")) {
					Q45 = Integer.valueOf(q_bankData.getInt("Q45"));
				}
				if (q_bankData.getString("Q46") != null
						&& q_bankData.getString("Q46").length() > 0
						&& !q_bankData.getString("Q46")
								.equalsIgnoreCase("null")) {
					Q46 = Integer.valueOf(q_bankData.getInt("Q46"));
				}
				if (q_bankData.getString("Q51") != null
						&& q_bankData.getString("Q51").length() > 0
						&& !q_bankData.getString("Q51")
								.equalsIgnoreCase("null")) {
					Q51 = Integer.valueOf(q_bankData.getInt("Q51"));
				}
				if (q_bankData.getString("Q61") != null
						&& q_bankData.getString("Q61").length() > 0
						&& !q_bankData.getString("Q61")
								.equalsIgnoreCase("null")) {
					Q61 = Integer.valueOf(q_bankData.getInt("Q61"));
				}
				if (q_bankData.getString("Q70") != null
						&& q_bankData.getString("Q70").length() > 0
						&& !q_bankData.getString("Q70")
								.equalsIgnoreCase("null")) {
					Q70 = Integer.valueOf(q_bankData.getInt("Q70"));
				}
				if (q_bankData.getString("Q73") != null
						&& q_bankData.getString("Q73").length() > 0
						&& !q_bankData.getString("Q73")
								.equalsIgnoreCase("null")) {
					Q73 = Integer.valueOf(q_bankData.getInt("Q73"));
				}
				if (q_bankData.getString("Q75") != null
						&& q_bankData.getString("Q75").length() > 0
						&& !q_bankData.getString("Q75")
								.equalsIgnoreCase("null")) {
					Q75 = Integer.valueOf(q_bankData.getInt("Q75"));
				}
				if (q_bankData.getString("Q85") != null
						&& q_bankData.getString("Q85").length() > 0
						&& !q_bankData.getString("Q85")
								.equalsIgnoreCase("null")) {
					Q85 = Integer.valueOf(q_bankData.getInt("Q85"));
				}
				if (q_bankData.getString("Q86") != null
						&& q_bankData.getString("Q86").length() > 0
						&& !q_bankData.getString("Q86")
								.equalsIgnoreCase("null")) {
					Q86 = Integer.valueOf(q_bankData.getInt("Q86"));
				}
				if (q_bankData.getString("Q94") != null
						&& q_bankData.getString("Q94").length() > 0
						&& !q_bankData.getString("Q94")
								.equalsIgnoreCase("null")) {
					Q94 = Integer.valueOf(q_bankData.getInt("Q94"));
				}
				if (q_bankData.getString("Q111") != null
						&& q_bankData.getString("Q111").length() > 0
						&& !q_bankData.getString("Q111").equalsIgnoreCase(
								"null")) {
					Q111 = Integer.valueOf(q_bankData.getInt("Q111"));
				}
				if (q_bankData.getString("Q112") != null
						&& q_bankData.getString("Q112").length() > 0
						&& !q_bankData.getString("Q112").equalsIgnoreCase(
								"null")) {
					Q112 = Integer.valueOf(q_bankData.getInt("Q112"));
				}
				if (q_bankData.getString("Q117") != null
						&& q_bankData.getString("Q117").length() > 0
						&& !q_bankData.getString("Q117").equalsIgnoreCase(
								"null")) {
					Q117 = Integer.valueOf(q_bankData.getInt("Q117"));
				}
				if (q_bankData.getString("Q124") != null
						&& q_bankData.getString("Q124").length() > 0
						&& !q_bankData.getString("Q124").equalsIgnoreCase(
								"null")) {
					Q124 = Integer.valueOf(q_bankData.getInt("Q124"));
				}
				if (q_bankData.getString("Q125") != null
						&& q_bankData.getString("Q125").length() > 0
						&& !q_bankData.getString("Q125").equalsIgnoreCase(
								"null")) {
					Q125 = Integer.valueOf(q_bankData.getInt("Q125"));
				}
				if (q_bankData.getString("Q146") != null
						&& q_bankData.getString("Q146").length() > 0
						&& !q_bankData.getString("Q146").equalsIgnoreCase(
								"null")) {
					Q146 = Integer.valueOf(q_bankData.getInt("Q146"));
				}
				if (q_bankData.getString("Q69") != null
						&& q_bankData.getString("Q69").length() > 0
						&& !q_bankData.getString("Q69")
								.equalsIgnoreCase("null")) {
					Q69 = Integer.valueOf(q_bankData.getInt("Q69"));
				}
				if (q_bankData.getString("Q48") != null
						&& q_bankData.getString("Q48").length() > 0
						&& !q_bankData.getString("Q48")
								.equalsIgnoreCase("null")) {
					Q48 = Integer.valueOf(q_bankData.getInt("Q48"));
				}
				if (q_bankData.getString("Q145") != null
						&& q_bankData.getString("Q145").length() > 0
						&& !q_bankData.getString("Q145").equalsIgnoreCase(
								"null")) {
					Q145 = Integer.valueOf(q_bankData.getInt("Q145"));
				}
				if (q_bankData.getString("Q148") != null
						&& q_bankData.getString("Q148").length() > 0
						&& !q_bankData.getString("Q148").equalsIgnoreCase(
								"null")) {
					Q148 = Integer.valueOf(q_bankData.getInt("Q148"));
				}

				String sqlcount = "select count(*) from tblmstimmunizationANS where ImmunizationGUID='"
						+ ImmunizationGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblmstimmunizationANS where ImmunizationGUID='"
						+ ImmunizationGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblmstimmunizationANS (AshaID,ANMID,	Q1,	Q2,	Q3,	Q4,	Q5,	Q6,	Q7,	Q14,	Q25,	Q36,	Q47,CreatedOn,	CreatedBy,	UpdatedOn,	UpdatedBy,	ImmunizationGUID,	ChildGUID,	Q53,	TimeDuration,IsEdited, Q13, Q21, Q22, Q24, Q27, Q37, Q45, Q51, Q61, Q70, Q73, Q75, Q85, Q86, Q94, Q111, Q112, Q117, Q124, Q125, Q46, Q146, Q69, Q48, Q145, Q148)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ Q1
							+ "',"
							+ Q2
							+ ","
							+ Q3
							+ ",	"
							+ Q4
							+ ",	"
							+ Q5
							+ ",	"
							+ Q6
							+ ",	"
							+ Q7
							+ ","
							+ Q14
							+ ","
							+ Q25
							+ ","
							+ Q36
							+ ","
							+ Q47
							+ ",'"
							+ CreatedOn
							+ "',"
							+ CreatedBy
							+ ",'"
							+ UpdatedOn
							+ "',"
							+ UpdatedBy
							+ ",'"
							+ ImmunizationGUID
							+ "','"
							+ ChildGUID
							+ "',"
							+ Q53
							+ ",'"
							+ TimeDuration
							+ "',0 ,"
							+ Q13
							+ ","
							+ Q21
							+ ","
							+ Q22
							+ ","
							+ Q24
							+ ","
							+ Q27
							+ ","
							+ Q37
							+ ","
							+ Q45
							+ ","
							+ Q51
							+ ","
							+ Q61
							+ ","
							+ Q70
							+ ","
							+ Q73
							+ ","
							+ Q75
							+ ","
							+ Q85
							+ ","
							+ Q86
							+ ","
							+ Q94
							+ ","
							+ Q111
							+ ","
							+ Q112
							+ ","
							+ Q117
							+ ","
							+ Q124
							+ ","
							+ Q125
							+ ","
							+ Q46
							+ ","
							+ Q146
							+ ","
							+ Q69
							+ ","
							+ Q48
							+ ","
							+ Q145
							+ ","
							+ Q148
							+ ")";

					dataProvider.executeSql(sql);
					Log.e("IMPORT DATA", "tblmstimmunizationANS ");
				} else {
					if (count1 == 0) {
						sql = "update tblmstimmunizationANS set Q1=" + Q1
								+ "',Q2=" + Q2 + ",Q3=" + Q3 + ",Q4=	" + Q4
								+ ",Q5=	" + Q5 + ",Q6=	" + Q6 + ",Q7=	" + Q7
								+ ",Q14=" + Q14 + ",Q25=" + Q25 + ",Q36=" + Q36
								+ ",Q47=" + Q47 + ",CreatedOn='" + CreatedOn
								+ "',CreatedBy=" + CreatedBy + ",UpdatedOn='"
								+ UpdatedOn + "',UpdatedBy=" + UpdatedBy
								+ ",Q53=" + Q53 + ",TimeDuration='"
								+ TimeDuration + "',IsEdited=0,Q13=" + Q13
								+ ", Q21=" + Q21 + ", Q22=" + Q22 + ", Q24="
								+ Q24 + ", Q27=" + Q27 + ", Q37=" + Q37
								+ ", Q45=" + Q45 + ", Q51=" + Q51 + ", Q61="
								+ Q61 + ", Q70=" + Q70 + ", Q73=" + Q73
								+ ", Q75=" + Q75 + ", Q85=" + Q85 + ", Q86="
								+ Q86 + ", Q94=" + Q94 + ", Q111=" + Q111
								+ ", Q112=" + Q112 + ", Q117=" + Q117
								+ ", Q124=" + Q124 + ", Q125=" + Q125
								+ ", Q46=" + Q46 + ", Q146=" + Q146 + ", Q69="
								+ Q69 + ", Q48=" + Q48 + ", Q145=" + Q145
								+ ", Q148=" + Q148
								+ " where ImmunizationGUID='"
								+ ImmunizationGUID + "'and ChildGUID='"
								+ ChildGUID + "'";

						dataProvider.executeSql(sql);
					}
				}

			}

			downloadmsg = "data download successfully";
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblPNChomevisit_ANS(String UserName, String Password,
			String ashaid) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblPNChomevisit_ANS");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearAnsData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblPNChomevisit_ANSArray = jsonObj
					.getJSONArray("tblPNChomevisit_ANS");
			for (int i = 0; i < tblPNChomevisit_ANSArray.length(); i++) {
				JSONObject q_bankData = tblPNChomevisit_ANSArray
						.getJSONObject(i);
				String PWGUID = "";
				String ChildGUID = "";
				String PNCGUID = "";
				int VisitNo = 0;
				String Q_0 = "";
				int Q_1 = 0;
				String Q_2 = "";
				String Q_3 = "";
				int Q_4 = 0;
				int Q_5 = 0;
				String Q_6 = "";
				String Q_7 = "";
				int Q_8 = 0;
				int Q_9 = 0;
				int Q_10 = 0;
				int Q_12 = 0;
				int Q_13 = 0;
				int Q_14 = 0;
				int Q_15 = 0;
				int Q_16 = 0;
				int Q_17 = 0;
				float Q_18 = 0;
				int Q_19 = 0;
				int Q_20 = 0;
				float Q_21 = 0;
				int Q_22 = 0;
				int Q_23 = 0;
				int Q_24 = 0;
				int Q_25 = 0;
				int Q_26 = 0;
				int Q_27 = 0;
				int Q_28 = 0;
				int Q_29 = 0;
				int Q_30 = 0;
				int Q_32 = 0;
				int Q_33 = 0;
				int Q_34 = 0;
				int Q_35 = 0;
				int Q_36 = 0;
				int Q_37 = 0;
				int Q_38 = 0;
				int Q_38a = 0;
				int Q_39 = 0;
				int Q_40 = 0;
				int Q_41 = 0;
				int Q_43 = 0;
				String Q_44 = "";
				int Q_45 = 0;
				int Q_46 = 0;
				int Q_47 = 0;
				String Q_48 = "";
				int Q_49 = 0;
				int Q_50 = 0;
				int Q_52 = 0;
				int Q_53 = 0;
				int Q_54 = 0;
				int Q_55 = 0;
				int Q_56 = 0;
				int Q_57 = 0;
				int Q_58 = 0;
				int Q_59 = 0;
				int Q_60 = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				int UpdatedBy = 0;
				int ANMID = 0;
				int AshaID = 0;
				String UpdatedOn = "";

				if (q_bankData.getString("Q_1") != null
						&& q_bankData.getString("Q_1").length() > 0
						&& !q_bankData.getString("Q_1")
								.equalsIgnoreCase("null")) {
					Q_1 = Integer.valueOf(q_bankData.getInt("Q_1"));
				}

				if (q_bankData.getString("Q_4") != null
						&& q_bankData.getString("Q_4").length() > 0
						&& !q_bankData.getString("Q_4")
								.equalsIgnoreCase("null")) {
					Q_4 = Integer.valueOf(q_bankData.getInt("Q_4"));
				}

				if (q_bankData.getString("Q_5") != null
						&& q_bankData.getString("Q_5").length() > 0
						&& !q_bankData.getString("Q_5")
								.equalsIgnoreCase("null")) {
					Q_5 = Integer.valueOf(q_bankData.getInt("Q_5"));
				}

				if (q_bankData.getString("Q_8") != null
						&& q_bankData.getString("Q_8").length() > 0
						&& !q_bankData.getString("Q_8")
								.equalsIgnoreCase("null")) {
					Q_8 = Integer.valueOf(q_bankData.getInt("Q_8"));
				}
				if (q_bankData.getString("Q_9") != null
						&& q_bankData.getString("Q_9").length() > 0
						&& !q_bankData.getString("Q_9")
								.equalsIgnoreCase("null")) {
					Q_9 = Integer.valueOf(q_bankData.getInt("Q_9"));
				}
				if (q_bankData.getString("Q_10") != null
						&& q_bankData.getString("Q_10").length() > 0
						&& !q_bankData.getString("Q_10").equalsIgnoreCase(
								"null")) {
					Q_10 = Integer.valueOf(q_bankData.getInt("Q_10"));
				}
				if (q_bankData.getString("Q_12") != null
						&& q_bankData.getString("Q_12").length() > 0
						&& !q_bankData.getString("Q_12").equalsIgnoreCase(
								"null")) {
					Q_12 = Integer.valueOf(q_bankData.getInt("Q_12"));
				}
				if (q_bankData.getString("Q_13") != null
						&& q_bankData.getString("Q_13").length() > 0
						&& !q_bankData.getString("Q_13").equalsIgnoreCase(
								"null")) {
					Q_13 = Integer.valueOf(q_bankData.getInt("Q_13"));
				}

				if (q_bankData.getString("Q_14") != null
						&& q_bankData.getString("Q_14").length() > 0
						&& !q_bankData.getString("Q_14").equalsIgnoreCase(
								"null")) {
					Q_14 = Integer.valueOf(q_bankData.getInt("Q_14"));
				}

				if (q_bankData.getString("Q_15") != null
						&& q_bankData.getString("Q_15").length() > 0
						&& !q_bankData.getString("Q_15").equalsIgnoreCase(
								"null")) {
					Q_15 = Integer.valueOf(q_bankData.getInt("Q_15"));
				}

				if (q_bankData.getString("Q_16") != null
						&& q_bankData.getString("Q_16").length() > 0
						&& !q_bankData.getString("Q_16").equalsIgnoreCase(
								"null")) {
					Q_16 = Integer.valueOf(q_bankData.getInt("Q_16"));
				}

				if (q_bankData.getString("Q_17") != null
						&& q_bankData.getString("Q_17").length() > 0
						&& !q_bankData.getString("Q_17").equalsIgnoreCase(
								"null")) {
					Q_17 = Integer.valueOf(q_bankData.getInt("Q_17"));
				}

				if (q_bankData.getString("Q_18") != null
						&& q_bankData.getString("Q_18").length() > 0
						&& !q_bankData.getString("Q_18").equalsIgnoreCase(
								"null")) {
					Q_18 = Float.valueOf(q_bankData.getString("Q_18"));
				}

				if (q_bankData.getString("Q_19") != null
						&& q_bankData.getString("Q_19").length() > 0
						&& !q_bankData.getString("Q_19").equalsIgnoreCase(
								"null")) {
					Q_19 = Integer.valueOf(q_bankData.getInt("Q_19"));
				}

				if (q_bankData.getString("Q_20") != null
						&& q_bankData.getString("Q_20").length() > 0
						&& !q_bankData.getString("Q_20").equalsIgnoreCase(
								"null")) {
					Q_20 = Integer.valueOf(q_bankData.getInt("Q_20"));
				}

				if (q_bankData.getString("Q_22") != null
						&& q_bankData.getString("Q_22").length() > 0
						&& !q_bankData.getString("Q_22").equalsIgnoreCase(
								"null")) {
					Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
				}
				if (q_bankData.getString("Q_23") != null
						&& q_bankData.getString("Q_23").length() > 0
						&& !q_bankData.getString("Q_23").equalsIgnoreCase(
								"null")) {
					Q_23 = Integer.valueOf(q_bankData.getInt("Q_23"));
				}
				if (q_bankData.getString("Q_24") != null
						&& q_bankData.getString("Q_24").length() > 0
						&& !q_bankData.getString("Q_24").equalsIgnoreCase(
								"null")) {
					Q_24 = Integer.valueOf(q_bankData.getInt("Q_24"));
				}
				if (q_bankData.getString("Q_25") != null
						&& q_bankData.getString("Q_25").length() > 0
						&& !q_bankData.getString("Q_25").equalsIgnoreCase(
								"null")) {
					Q_25 = Integer.valueOf(q_bankData.getInt("Q_25"));
				}
				if (q_bankData.getString("Q_26") != null
						&& q_bankData.getString("Q_26").length() > 0
						&& !q_bankData.getString("Q_26").equalsIgnoreCase(
								"null")) {
					Q_26 = Integer.valueOf(q_bankData.getInt("Q_26"));
				}
				if (q_bankData.getString("Q_27") != null
						&& q_bankData.getString("Q_27").length() > 0
						&& !q_bankData.getString("Q_27").equalsIgnoreCase(
								"null")) {
					Q_27 = Integer.valueOf(q_bankData.getInt("Q_27"));
				}
				if (q_bankData.getString("Q_28") != null
						&& q_bankData.getString("Q_28").length() > 0
						&& !q_bankData.getString("Q_28").equalsIgnoreCase(
								"null")) {
					Q_28 = Integer.valueOf(q_bankData.getInt("Q_28"));
				}
				if (q_bankData.getString("Q_29") != null
						&& q_bankData.getString("Q_29").length() > 0
						&& !q_bankData.getString("Q_29").equalsIgnoreCase(
								"null")) {
					Q_29 = Integer.valueOf(q_bankData.getInt("Q_29"));
				}
				if (q_bankData.getString("Q_30") != null
						&& q_bankData.getString("Q_30").length() > 0
						&& !q_bankData.getString("Q_30").equalsIgnoreCase(
								"null")) {
					Q_30 = Integer.valueOf(q_bankData.getInt("Q_30"));
				}
				if (q_bankData.getString("Q_32") != null
						&& q_bankData.getString("Q_32").length() > 0
						&& !q_bankData.getString("Q_32").equalsIgnoreCase(
								"null")) {
					Q_32 = Integer.valueOf(q_bankData.getInt("Q_32"));
				}
				if (q_bankData.getString("Q_33") != null
						&& q_bankData.getString("Q_33").length() > 0
						&& !q_bankData.getString("Q_33").equalsIgnoreCase(
								"null")) {
					Q_33 = Integer.valueOf(q_bankData.getInt("Q_33"));
				}
				if (q_bankData.getString("Q_22") != null
						&& q_bankData.getString("Q_22").length() > 0
						&& !q_bankData.getString("Q_22").equalsIgnoreCase(
								"null")) {
					Q_22 = Integer.valueOf(q_bankData.getInt("Q_22"));
				}
				if (q_bankData.getString("Q_34") != null
						&& q_bankData.getString("Q_34").length() > 0
						&& !q_bankData.getString("Q_34").equalsIgnoreCase(
								"null")) {
					Q_34 = Integer.valueOf(q_bankData.getInt("Q_34"));
				}
				if (q_bankData.getString("Q_35") != null
						&& q_bankData.getString("Q_35").length() > 0
						&& !q_bankData.getString("Q_35").equalsIgnoreCase(
								"null")) {
					Q_35 = Integer.valueOf(q_bankData.getInt("Q_35"));
				}
				if (q_bankData.getString("Q_36") != null
						&& q_bankData.getString("Q_36").length() > 0
						&& !q_bankData.getString("Q_36").equalsIgnoreCase(
								"null")) {
					Q_36 = Integer.valueOf(q_bankData.getInt("Q_36"));
				}
				if (q_bankData.getString("Q_37") != null
						&& q_bankData.getString("Q_37").length() > 0
						&& !q_bankData.getString("Q_37").equalsIgnoreCase(
								"null")) {
					Q_37 = Integer.valueOf(q_bankData.getInt("Q_37"));
				}
				if (q_bankData.getString("Q_38") != null
						&& q_bankData.getString("Q_38").length() > 0
						&& !q_bankData.getString("Q_38").equalsIgnoreCase(
								"null")) {
					Q_38 = Integer.valueOf(q_bankData.getInt("Q_38"));
				}
				if (q_bankData.getString("Q_38a") != null
						&& q_bankData.getString("Q_38a").length() > 0
						&& !q_bankData.getString("Q_38a").equalsIgnoreCase(
								"null")) {
					Q_38a = Integer.valueOf(q_bankData.getInt("Q_38a"));
				}
				if (q_bankData.getString("Q_39") != null
						&& q_bankData.getString("Q_39").length() > 0
						&& !q_bankData.getString("Q_39").equalsIgnoreCase(
								"null")) {
					Q_39 = Integer.valueOf(q_bankData.getInt("Q_39"));
				}
				if (q_bankData.getString("Q_40") != null
						&& q_bankData.getString("Q_40").length() > 0
						&& !q_bankData.getString("Q_40").equalsIgnoreCase(
								"null")) {
					Q_40 = Integer.valueOf(q_bankData.getInt("Q_40"));
				}
				if (q_bankData.getString("Q_41") != null
						&& q_bankData.getString("Q_41").length() > 0
						&& !q_bankData.getString("Q_41").equalsIgnoreCase(
								"null")) {
					Q_41 = Integer.valueOf(q_bankData.getInt("Q_41"));
				}
				if (q_bankData.getString("Q_43") != null
						&& q_bankData.getString("Q_43").length() > 0
						&& !q_bankData.getString("Q_43").equalsIgnoreCase(
								"null")) {
					Q_43 = Integer.valueOf(q_bankData.getInt("Q_43"));
				}
				if (q_bankData.getString("Q_45") != null
						&& q_bankData.getString("Q_45").length() > 0
						&& !q_bankData.getString("Q_45").equalsIgnoreCase(
								"null")) {
					Q_45 = Integer.valueOf(q_bankData.getInt("Q_45"));
				}
				if (q_bankData.getString("Q_46") != null
						&& q_bankData.getString("Q_46").length() > 0
						&& !q_bankData.getString("Q_46").equalsIgnoreCase(
								"null")) {
					Q_46 = Integer.valueOf(q_bankData.getInt("Q_46"));
				}
				if (q_bankData.getString("Q_47") != null
						&& q_bankData.getString("Q_47").length() > 0
						&& !q_bankData.getString("Q_47").equalsIgnoreCase(
								"null")) {
					Q_47 = Integer.valueOf(q_bankData.getInt("Q_47"));
				}
				if (q_bankData.getString("Q_49") != null
						&& q_bankData.getString("Q_49").length() > 0
						&& !q_bankData.getString("Q_49").equalsIgnoreCase(
								"null")) {
					Q_49 = Integer.valueOf(q_bankData.getInt("Q_49"));
				}
				if (q_bankData.getString("Q_50") != null
						&& q_bankData.getString("Q_50").length() > 0
						&& !q_bankData.getString("Q_50").equalsIgnoreCase(
								"null")) {
					Q_50 = Integer.valueOf(q_bankData.getInt("Q_50"));
				}
				if (q_bankData.getString("Q_52") != null
						&& q_bankData.getString("Q_52").length() > 0
						&& !q_bankData.getString("Q_52").equalsIgnoreCase(
								"null")) {
					Q_52 = Integer.valueOf(q_bankData.getInt("Q_52"));
				}
				if (q_bankData.getString("Q_53") != null
						&& q_bankData.getString("Q_53").length() > 0
						&& !q_bankData.getString("Q_53").equalsIgnoreCase(
								"null")) {
					Q_53 = Integer.valueOf(q_bankData.getInt("Q_53"));
				}
				if (q_bankData.getString("Q_54") != null
						&& q_bankData.getString("Q_54").length() > 0
						&& !q_bankData.getString("Q_54").equalsIgnoreCase(
								"null")) {
					Q_54 = Integer.valueOf(q_bankData.getInt("Q_54"));
				}
				if (q_bankData.getString("Q_55") != null
						&& q_bankData.getString("Q_55").length() > 0
						&& !q_bankData.getString("Q_55").equalsIgnoreCase(
								"null")) {
					Q_55 = Integer.valueOf(q_bankData.getInt("Q_55"));
				}
				if (q_bankData.getString("Q_56") != null
						&& q_bankData.getString("Q_56").length() > 0
						&& !q_bankData.getString("Q_56").equalsIgnoreCase(
								"null")) {
					Q_56 = Integer.valueOf(q_bankData.getInt("Q_56"));
				}
				if (q_bankData.getString("Q_57") != null
						&& q_bankData.getString("Q_57").length() > 0
						&& !q_bankData.getString("Q_57").equalsIgnoreCase(
								"null")) {
					Q_57 = Integer.valueOf(q_bankData.getInt("Q_57"));
				}
				if (q_bankData.getString("Q_58") != null
						&& q_bankData.getString("Q_58").length() > 0
						&& !q_bankData.getString("Q_58").equalsIgnoreCase(
								"null")) {
					Q_58 = Integer.valueOf(q_bankData.getInt("Q_58"));
				}
				if (q_bankData.getString("Q_59") != null
						&& q_bankData.getString("Q_59").length() > 0
						&& !q_bankData.getString("Q_59").equalsIgnoreCase(
								"null")) {
					Q_59 = Integer.valueOf(q_bankData.getInt("Q_59"));
				}
				if (q_bankData.getString("Q_60") != null
						&& q_bankData.getString("Q_60").length() > 0
						&& !q_bankData.getString("Q_60").equalsIgnoreCase(
								"null")) {
					Q_60 = Integer.valueOf(q_bankData.getInt("Q_60"));
				}
				if (q_bankData.getString("Q_21") != null
						&& q_bankData.getString("Q_21").length() > 0
						&& !q_bankData.getString("Q_21").equalsIgnoreCase(
								"null")) {
					Q_21 = Integer.valueOf(q_bankData.getInt("Q_21"));
				}
				if (q_bankData.getString("ANMID") != null
						&& q_bankData.getString("ANMID").length() > 0
						&& !q_bankData.getString("ANMID").equalsIgnoreCase(
								"null")) {
					ANMID = Integer.valueOf(q_bankData.getInt("ANMID"));
				}
				if (q_bankData.getString("AshaID") != null
						&& q_bankData.getString("AshaID").length() > 0
						&& !q_bankData.getString("AshaID").equalsIgnoreCase(
								"null")) {
					AshaID = Integer.valueOf(q_bankData.getInt("AshaID"));
				}

				if (q_bankData.getString("CreatedBy") != null
						&& q_bankData.getString("CreatedBy").length() > 0
						&& !q_bankData.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(q_bankData.getInt("CreatedBy"));
				}
				if (q_bankData.getString("UpdatedBy") != null
						&& q_bankData.getString("UpdatedBy").length() > 0
						&& !q_bankData.getString("UpdatedBy").equalsIgnoreCase(
								"null")) {
					UpdatedBy = Integer.valueOf(q_bankData.getInt("UpdatedBy"));
				}

				Q_0 = q_bankData.getString("Q_0");
				Q_2 = q_bankData.getString("Q_2");
				Q_3 = q_bankData.getString("Q_3");

				Q_6 = q_bankData.getString("Q_6");
				Q_7 = q_bankData.getString("Q_7");
				Q_44 = q_bankData.getString("Q_44");
				Q_48 = q_bankData.getString("Q_48");
				ChildGUID = q_bankData.getString("ChildGUID");

				PWGUID = q_bankData.getString("PWGUID");
				CreatedOn = q_bankData.getString("CreatedOn");
				UpdatedOn = q_bankData.getString("UpdatedOn");

				String sqlcount = "select count(*) from tblPNChomevisit_ANS  PNCGUID='"
						+ PNCGUID + "' and ChildGUID='" + ChildGUID + "'";
				int count = dataProvider.getMaxRecord(sqlcount);
				String sqlcount1 = "select count(*) from tblPNChomevisit_ANS  PNCGUID='"
						+ PNCGUID
						+ "' and ChildGUID='"
						+ ChildGUID
						+ "' and IsEdited=1";
				int count1 = dataProvider.getMaxRecord(sqlcount1);
				String sql = "";
				if (count == 0) {
					sql = "insert into tblPNChomevisit_ANS (AshaID,ANMID,PWGUID,ChildGUID,PNCGUID,VisitNo,Q_0,Q_1,Q_2,Q_3,Q_4,Q_5,Q_6,Q_7,Q_8,Q_9,Q_10,Q_12,Q_13,Q_14,Q_15,Q_16,Q_17,Q_18,Q_19,Q_20,Q_21,Q_22,Q_23,Q_24,Q_25,Q_26,Q_27,Q_28,Q_29,Q_30,Q_32,Q_33,Q_34,Q_35,Q_36,Q_37,Q_38,Q_38a,Q_39,Q_40,Q_41,Q_43,Q_44,Q_45,Q_46,Q_47,Q_48,Q_49,Q_50,Q_52,Q_53,Q_54,Q_55,Q_56,Q_57,Q_58,Q_59,Q_60,CreatedBy,CreatedOn,UpdatedBy,UpdatedOn,Audio_filename,IsEdited)values("
							+ AshaID
							+ ","
							+ ANMID
							+ ",'"
							+ PWGUID
							+ "','"
							+ ChildGUID
							+ "',	'"
							+ PNCGUID
							+ "',	"
							+ VisitNo
							+ ",	'"
							+ Q_0
							+ "',	"
							+ Q_1
							+ ",	'"
							+ Q_2
							+ "',	'"
							+ Q_3
							+ "',	"
							+ Q_4
							+ ",	"
							+ Q_5
							+ ",	"
							+ Q_6
							+ ",	'"
							+ Q_7
							+ "',"
							+ Q_8
							+ ","
							+ Q_9
							+ ","
							+ Q_10
							+ ","
							+ Q_12
							+ ","
							+ Q_13
							+ ",	"
							+ Q_14
							+ ","
							+ Q_15
							+ ","
							+ Q_16
							+ ","
							+ Q_17
							+ ","
							+ Q_18
							+ ","
							+ Q_19
							+ ","
							+ Q_20
							+ ","
							+ Q_21
							+ ","
							+ Q_22
							+ ","
							+ Q_23
							+ ","
							+ Q_24
							+ ",	"
							+ Q_25
							+ ","
							+ Q_26
							+ ",	"
							+ Q_27
							+ ",	"
							+ Q_28
							+ ",	"
							+ Q_29
							+ ",	"
							+ Q_30
							+ ",	"
							+ Q_32
							+ ",	"
							+ Q_33
							+ ",	"
							+ Q_34
							+ ",	"
							+ Q_35
							+ ",	"
							+ Q_36
							+ ",	"
							+ Q_37
							+ ",	"
							+ Q_38
							+ ","
							+ Q_38a
							+ ",	"
							+ Q_39
							+ ",	"
							+ Q_40
							+ ",	"
							+ Q_41
							+ ",	"
							+ Q_43
							+ ",	'"
							+ Q_44
							+ "',	"
							+ Q_45
							+ ",	"
							+ Q_46
							+ ",	"
							+ Q_47
							+ ",	'"
							+ Q_48
							+ "',	"
							+ Q_49
							+ ",	"
							+ Q_50
							+ ",	"
							+ Q_52
							+ ",	"
							+ Q_53
							+ ",	"
							+ Q_54
							+ ",	"
							+ Q_55
							+ ",	"
							+ Q_56
							+ ",	"
							+ Q_57
							+ ",	"
							+ Q_58
							+ ",	"
							+ Q_59
							+ ",	"
							+ Q_60
							+ ",	"
							+ CreatedBy
							+ ",	'"
							+ CreatedOn
							+ "',	" + UpdatedBy + ",	'" + UpdatedOn + "',0)";

					dataProvider.executeSql(sql);
				} else {
					if (count == 0) {
						sql = "update tblPNChomevisit_ANS set PWGUID='"
								+ PWGUID + "',	VisitNo=" + VisitNo + ",	Q_0='"
								+ Q_0 + "',Q_1=	" + Q_1 + ",Q_2=	'" + Q_2
								+ "',Q_3=	'" + Q_3 + "',Q_4=	" + Q_4 + ",Q_5=	"
								+ Q_5 + ",Q_6=	" + Q_6 + ",Q_7=	'" + Q_7
								+ "',Q_8=" + Q_8 + ",Q_9=" + Q_9 + ",Q_10="
								+ Q_10 + ",Q_12=" + Q_12 + ",Q_13=" + Q_13
								+ ",Q_14=	" + Q_14 + ",Q_15=" + Q_15 + ",Q_16="
								+ Q_16 + ",Q_17=" + Q_17 + ",Q_18=" + Q_18
								+ ",Q_19=" + Q_19 + ",Q_20=" + Q_20 + ",Q_21="
								+ Q_21 + ",Q_22=" + Q_22 + ",Q_23=" + Q_23
								+ ",Q_24=" + Q_24 + ",	Q_25=" + Q_25 + ",Q_26="
								+ Q_26 + ",Q_27=	" + Q_27 + ",Q_28=	" + Q_28
								+ ",Q_29=	" + Q_29 + ",Q_30=	" + Q_30
								+ ",Q_32=	" + Q_32 + ",Q_33=	" + Q_33
								+ ",Q_34=	" + Q_34 + ",Q_35=	" + Q_35
								+ ",Q_36=	" + Q_36 + ",Q_37=	" + Q_37
								+ ",Q_38=	" + Q_38 + ",Q_38a=" + Q_38a
								+ ",Q_39=	" + Q_39 + ",Q_40=	" + Q_40
								+ ",	Q_41=" + Q_41 + ",Q_43=	" + Q_43
								+ ",Q_44=	'" + Q_44 + "',Q_45=	" + Q_45
								+ ",Q_46=	" + Q_46 + ",Q_47=	" + Q_47
								+ ",Q_48=	'" + Q_48 + "',Q_49=	" + Q_49
								+ ",Q_50=	" + Q_50 + ",Q_52=	" + Q_52
								+ ",Q_53=	" + Q_53 + ",Q_54=	" + Q_54
								+ ",Q_55=	" + Q_55 + ",Q_56=	" + Q_56
								+ ",Q_57=	" + Q_57 + ",Q_58=	" + Q_58
								+ ",Q_59=	" + Q_59 + ",Q_60=	" + Q_60
								+ ",CreatedBy=	" + CreatedBy + ",CreatedOn=	'"
								+ CreatedOn + "',UpdatedBy=	" + UpdatedBy
								+ ",	UpdatedOn='" + UpdatedOn + "',AshaID="
								+ ANMID + ",ANMID=" + ANMID
								+ "	,IsEdited=0 where ChildGUID='" + ChildGUID
								+ "',	PNCGUID='" + PNCGUID + "'";
					}
				}
				Log.e("IMPORT DATA", "tblPNChomevisit_ANS ");

			}

			downloadmsg = "data download successfully";
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblmstFPAns(String UserName, String Password,
			String ashaid) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblmstFPAns");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearAnsData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblmstFPAnsArray = jsonObj.getJSONArray("tblmstFPAns");
			for (int i = 0; i < tblmstFPAnsArray.length(); i++) {
				JSONObject block = tblmstFPAnsArray.getJSONObject(i);
				String FPAns_Guid = "";
				String WomenName_Guid = "";
				String VisitDate = "";
				String Q1 = "";
				String Q5 = "";
				String Q6 = "";
				String Q7 = "";
				String Q8 = "";
				String Q24 = "";
				String Q36 = "";
				String Q39 = "";
				String Q52 = "";
				String Q56 = "";
				String Q59 = "";
				String Q60 = "";
				String Q61 = "";
				String Q62 = "";
				String Q20 = "";
				String Q21 = "";
				String Q37 = "";
				String Q57 = "";
				String Q76 = "";
				int AshaID = 0;
				int ANMID = 0;
				int IsEdited = 0;
				int IsUploaded = 0;
				int CreatedBy = 0;
				String CreatedOn = "";
				if (block.getString("AshaID") != null
						&& block.getString("AshaID").length() > 0
						&& !block.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(block.getInt("AshaID"));
				}
				if (block.getString("ANMID") != null
						&& block.getString("ANMID").length() > 0
						&& !block.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(block.getInt("ANMID"));
				}

				FPAns_Guid = block.getString("FPAns_Guid");
				WomenName_Guid = block.getString("WomenName_Guid");
				VisitDate = block.getString("VisitDate");
				Q1 = block.getString("Q1");
				Q5 = block.getString("Q5");
				Q6 = block.getString("Q6");
				Q7 = block.getString("Q7");
				Q8 = block.getString("Q8");
				Q24 = block.getString("Q24");
				Q36 = block.getString("Q36");
				Q39 = block.getString("Q39");
				Q52 = block.getString("Q52");
				Q56 = block.getString("Q56");
				Q59 = block.getString("Q59");
				Q60 = block.getString("Q60");
				Q61 = block.getString("Q61");
				Q62 = block.getString("Q62");
				Q20 = block.getString("Q20");
				Q21 = block.getString("Q21");
				Q37 = block.getString("Q37");
				Q57 = block.getString("Q57");
				Q76 = block.getString("Q76");
				if (block.getString("CreatedBy") != null
						&& block.getString("CreatedBy").length() > 0
						&& !block.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(block.getInt("CreatedBy"));
				}
				CreatedOn = block.getString("CreatedOn");

				String sql = "";
				int count = 0;
				String sql1 = "select count(*) from tblFP_visit where FPAns_Guid='"
						+ FPAns_Guid + "' ";
				count = dataProvider.getMaxRecord(sql1);

				if (count == 0) {
					sql = "Insert into tblFP_visit( FPAns_Guid,WomenName_Guid,VisitDate,Q1,Q5,Q6,Q7,Q8,Q24,Q36,Q39,Q52,Q56,Q59,Q60,Q61,Q62,Q20,Q21,Q37,Q57,Q76,AshaID,ANMID,IsEdited,IsUploaded,CreatedBy ,CreatedOn )values('"
							+ FPAns_Guid
							+ "','"
							+ WomenName_Guid
							+ "','"
							+ VisitDate
							+ "','"
							+ Q1
							+ "','"
							+ Q5
							+ "','"
							+ Q6
							+ "','"
							+ Q7
							+ "','"
							+ Q8
							+ "','"
							+ Q24
							+ "','"
							+ Q36
							+ "','"
							+ Q39
							+ "','"
							+ Q52
							+ "','"
							+ Q56
							+ "','"
							+ Q59
							+ "','"
							+ Q60
							+ "','"
							+ Q61
							+ "','"
							+ Q62
							+ "','"
							+ Q20
							+ "','"
							+ Q21
							+ "','"
							+ Q37
							+ "','"
							+ Q57
							+ "','"
							+ Q76
							+ "',"
							+ AshaID
							+ ","
							+ ANMID
							+ ",0,0,"
							+ CreatedBy
							+ ",'"
							+ CreatedOn
							+ "')";
				}
				dataProvider.executeSql(sql);

			}

			downloadmsg = "data download successfully";
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void importtblmstFPFDetail(String UserName, String Password,
			String ashaid) {
		// TODO Auto-generated method stub

		String URL = "WebURL";
		String NAMESPACE = GlobalString.NAMESPACE;
		String SOAP_ACTION = GlobalString.SOAP_ACTION_DOWNLOAD_MASTER;
		String METHOD_NAME = "GetMasterFullData";

		SoapPrimitive resultString;
		String TAG1 = "Response";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("UserName", UserName);
		request.addProperty("PassWord", Password);
		request.addProperty("sFlag", "tblmstFPFDetail");

		request.addProperty("AuthenticationToken", ashaid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER12);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);

		HttpTransportSE httpTransport = new HttpTransportSE(URL, 300000);

		SoapObject responses = null;
		try {

			httpTransport.call(SOAP_ACTION, envelope);
			resultString = (SoapPrimitive) envelope.getResponse();

			Log.i(TAG1, "Result Celsius: " + resultString);

			JSONObject jsonObj = new JSONObject(resultString.toString());
			// ClearAnsData();
			Log.i(TAG1, "JsonObject: " + jsonObj);

			tblmstFPFDetailArray = jsonObj.getJSONArray("tblmstFPFDetail");
			for (int i = 0; i < tblmstFPFDetailArray.length(); i++) {
				JSONObject block = tblmstFPFDetailArray.getJSONObject(i);
				String WomenName = "";
				String Hushband_name = "";
				String PF_date = "";
				String CurrF_date = "";
				int MethodAdopted = 0;
				int Pregnant = 0;
				int Pregnant_test = 0;
				String FPF_Guid = "";
				String WomenName_Guid = "";
				String Hushband_Guid = "";
				String MethodadoptedDate = "";

				int AshaID = 0;
				int ANMID = 0;
				int CreatedBy = 0;
				String CreatedOn = "";

				if (block.getString("MethodAdopted") != null
						&& block.getString("MethodAdopted").length() > 0
						&& !block.getString("MethodAdopted").equalsIgnoreCase(
								"null")) {
					MethodAdopted = Integer.valueOf(block
							.getInt("MethodAdopted"));
				}
				if (block.getString("Pregnant") != null
						&& block.getString("Pregnant").length() > 0
						&& !block.getString("Pregnant")
								.equalsIgnoreCase("null")) {
					Pregnant = Integer.valueOf(block.getInt("Pregnant"));
				}
				if (block.getString("Pregnant_test") != null
						&& block.getString("Pregnant_test").length() > 0
						&& !block.getString("Pregnant_test").equalsIgnoreCase(
								"null")) {
					Pregnant_test = Integer.valueOf(block
							.getInt("Pregnant_test"));
				}
				if (block.getString("AshaID") != null
						&& block.getString("AshaID").length() > 0
						&& !block.getString("AshaID").equalsIgnoreCase("null")) {
					AshaID = Integer.valueOf(block.getInt("AshaID"));
				}
				if (block.getString("ANMID") != null
						&& block.getString("ANMID").length() > 0
						&& !block.getString("ANMID").equalsIgnoreCase("null")) {
					ANMID = Integer.valueOf(block.getInt("ANMID"));
				}
				WomenName = block.getString("WomenName");
				Hushband_name = block.getString("Hushband_name");
				PF_date = block.getString("PF_date");
				CurrF_date = block.getString("CurrF_date");
				FPF_Guid = block.getString("FPF_Guid");
				WomenName_Guid = block.getString("WomenName_Guid");
				Hushband_Guid = block.getString("Hushband_Guid");
				MethodadoptedDate = block.getString("MethodadoptedDate");
				if (block.getString("CreatedBy") != null
						&& block.getString("CreatedBy").length() > 0
						&& !block.getString("CreatedBy").equalsIgnoreCase(
								"null")) {
					CreatedBy = Integer.valueOf(block.getInt("CreatedBy"));
				}
				CreatedOn = block.getString("CreatedOn");
				int count = 0;
				String sql1 = "select count(*) from tblFP_followup where FPF_Guid='"
						+ FPF_Guid + "' ";
				count = dataProvider.getMaxRecord(sql1);
				String sql = "";
				if (count == 0) {
					sql = "Insert into tblFP_followup(WomenName,Hushband_name,PF_date,CurrF_date,MethodAdopted,Pregnant,Pregnant_test,FPF_Guid,WomenName_Guid,Hushband_Guid, AshaID,ANMID,IsEdited,IsUploaded,MethodadoptedDate,CreatedBy,CreatedOn)values('"
							+ WomenName
							+ "','"
							+ Hushband_name
							+ "','"
							+ PF_date
							+ "','"
							+ CurrF_date
							+ "','"
							+ MethodAdopted
							+ "','"
							+ Pregnant
							+ "','"
							+ Pregnant_test
							+ "','"
							+ FPF_Guid
							+ "','"
							+ WomenName_Guid
							+ "','"
							+ Hushband_Guid
							+ "','"
							+ AshaID
							+ "','"
							+ ANMID
							+ "',0,0,'"
							+ MethodadoptedDate
							+ "',"
							+ CreatedBy
							+ ",'"
							+ CreatedOn + "')";
				}
				dataProvider.executeSql(sql);

			}
			downloadmsg = "data download successfully";
		}

		catch (Exception e) {
			downloadmsg = e.getMessage();
			e.printStackTrace();

		}
	}

	public void exportAnswer() {
		tblmstimmunizationANS = dataProvider.gettblmstimmunizationANS("", 0);
		tblPNChomevisit_ANS = dataProvider.gettblPNChomevisit_ANS("", 0);

		if ((tblmstimmunizationANS != null && tblmstimmunizationANS.size() > 0)
				|| (tblPNChomevisit_ANS != null && tblPNChomevisit_ANS.size() > 0)) {
			ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
					.getActiveNetworkInfo();
			if (networkInfoCheckConnection != null
					&& networkInfoCheckConnection.isConnected()
					&& networkInfoCheckConnection.isAvailable()) {

				// TODO Auto-generated method stub

				progressDialog = ProgressDialog.show(Synchronization.this, "",
						getResources().getString(R.string.Uploadingdata));
				new Thread() {

					@Override
					public void run() {
						try {
							exportAnswerdata();
						}

						catch (Exception exp) {
							progressDialog.dismiss();
						}

						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (iDataUploadImmu == 1) {
									Toast.makeText(Synchronization.this,
											"Data upload successfully",
											Toast.LENGTH_LONG).show();
								} else {
									Toast.makeText(Synchronization.this,
											downloadmsg, Toast.LENGTH_LONG)
											.show();
								}
							}
						});

						progressDialog.dismiss();

					}
				}.start();

			} else {
				Toast.makeText(Synchronization.this, "No Network Access",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(Synchronization.this, "Nothing for Upload",
					Toast.LENGTH_LONG).show();
		}
	}

	public void exportAnswerdata() {
		try {
			//

			String SOAP_ACTION = "http://tempuri.org/AnsUploadServices";
			String METHOD_NAME = "AnsUploadServices";
			String NAMESPACE = "http://tempuri.org/";
			String URL = "WebURL";
			// String URL
			// ="WebURL";
			SoapPrimitive resultString;
			String TAG1 = "Response";
			JSONObject jsonObjectcombined = new JSONObject();

			if (tblmstimmunizationANS.size() > 0) {
				String json1 = new Gson().toJson(tblmstimmunizationANS);
				JSONArray JSONArray_mstFPAns = new JSONArray(json1);
				jsonObjectcombined.put("tblmstimmunizationANS",
						JSONArray_mstFPAns);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblmstimmunizationANS", otherJsonArray);

			}

			if (tblPNChomevisit_ANS.size() > 0) {
				String json1 = new Gson().toJson(tblPNChomevisit_ANS);
				JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
				jsonObjectcombined.put("tblPNChomevisit_ANS",
						JSONArray_mstFPFDetail);
			} else {
				JSONArray otherJsonArray = new JSONArray();
				jsonObjectcombined.put("tblPNChomevisit_ANS", otherJsonArray);

			}
			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

			Request.addProperty("sData", jsonObjectcombined.toString());
			Request.addProperty("AuthenticationToken",
					global.getsGlobalUserName());
			Request.addProperty("UserName", global.getsGlobalUserName());
			Request.addProperty("PassWord", global.getsGlobalPassword());
			// {"Table":[{"RetValue":10}]}
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);

			HttpTransportSE transport1 = new HttpTransportSE(URL, 240000);

			transport1.call(SOAP_ACTION, soapEnvelope);
			resultString = (SoapPrimitive) soapEnvelope.getResponse();
			Log.i(TAG1, "Result Celsius: " + resultString);
			JSONObject jsonObj = new JSONObject(resultString.toString()
					.toUpperCase());
			try {
				JSONArray Table = jsonObj.getJSONArray(("Table").toUpperCase());
				for (int i = 0; i < Table.length(); i++) {
					JSONObject data = Table.getJSONObject(i);
					String Result = data.getString("RETVALUE");
					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadImmu = 1;
						String sqltblmstFPAns = "Update tblmstimmunizationANS set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstFPAns);
					} else {
						iDataUploadImmu = 0;
					}

					if (Result != null && !Result.equalsIgnoreCase("null")
							&& Result.length() > 0
							&& Integer.valueOf(Result) > 0) {
						iDataUploadImmu = 1;
						String sqlmstFPFDetail = "Update tblPNChomevisit_ANS set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlmstFPFDetail);
					} else {
						iDataUploadImmu = 0;
					}
				}
			} catch (Exception e) {
				downloadmsg = e.getMessage();
				System.out.println(e);
			}
		} catch (Exception e) {
			downloadmsg = e.getMessage();
			System.out.println(e);
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent i = new Intent(Synchronization.this, Dashboard_Activity.class);
		finish();
		startActivity(i);
	}
}
