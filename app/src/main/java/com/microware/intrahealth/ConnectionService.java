package com.microware.intrahealth;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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
import com.microware.intrahealth.object.tbl_DetailedModuleUsage;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_VHND_DueList;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tbldevicespaceusage;
import com.microware.intrahealth.object.tblhhupdate_Log;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblVHNDDuelist;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblmstimmunizationANS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class ConnectionService extends IntentService {
	public ConnectionService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	String hhguid = "";


	private static final String IMAGE_DIRECTORY_NAME = "mSakhi";

	DataProvider dataProvider;

	Global global;


	JSONArray VHND_Schedule = null;
	int iDataUpload;
	public ArrayList<Tbl_HHFamilyMember> HHFamilyMember = new ArrayList<Tbl_HHFamilyMember>();
	public ArrayList<Tbl_HHSurvey> HHSurvey = new ArrayList<Tbl_HHSurvey>();
	public ArrayList<tbl_DetailedModuleUsage> ModuleUsage = new ArrayList<tbl_DetailedModuleUsage>();
	public ArrayList<tbldevicespaceusage> devicespaceusage = new ArrayList<tbldevicespaceusage>();
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

	public ArrayList<tblmstimmunizationANS> tblmstimmunizationANS = new ArrayList<tblmstimmunizationANS>();
	JSONArray tblPNChomevisit_ANSArray = null;
	public ArrayList<tblPNChomevisit_ANS> tblPNChomevisit_ANS = new ArrayList<tblPNChomevisit_ANS>();
	JSONArray tblmstimmunizationQuesArray = null;
	public ArrayList<tblmstimmunizationQues> tblmstimmunizationQues = new ArrayList<tblmstimmunizationQues>();
	int iDataUploadImmu;
	public ArrayList<tblVHNDDuelist> VHNDDuelistnew = new ArrayList<tblVHNDDuelist>();

	public ArrayList<q_bank> q_bank = new ArrayList<q_bank>();
	JSONArray q_bankArray = null;
	int iDataUploadfp;
	int iDataUploadfpans;
	int iDataUploadpncans;

	int iDataUploadMNCH;
	int iMNCHDataDownload;
	private boolean isrunning;
	// Add Herojit for upload and download
	ArrayList<tbl_VHND_DueList> VHND_Duelist = new ArrayList<tbl_VHND_DueList>();
	ArrayList<tbl_VHNDPerformance> VHND_Performace = new ArrayList<tbl_VHNDPerformance>();

	Handler mHandler;// indicates whether onRebind should be used

	public ConnectionService() {
		super("ConnectionService");

		mHandler = new Handler();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		global = (Global) getApplicationContext();
		dataProvider = new DataProvider(this);
		exportHH();
		exportMNCH();
		exportUPloadVNHD();
		exportFamilyPlanning();
		this.isrunning = true;
	}

	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.isrunning = false;
	}

	public void exportHH() {
		HHSurveycount = dataProvider.getTblSurveyUploadcount();

		// TODO Auto-generated method stub
		if (HHSurveycount != null && HHSurveycount.size() > 0) {

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

							exportHHSurveydata();
							exportmoduleusage();
							expordevicedata();

						}
					}
				}
			}

			catch (Exception exp) {

			}

		}

	}

	public void exportMNCH() {
		Child = dataProvider.gettblChild("","", 3,0);
		pregnantwomen = dataProvider.getPregnantWomendata("", 2, 0);
		ANCVisit = dataProvider.getTbl_VisitANCData("", "", 2);
		tblDates = dataProvider.gettbl_DatesEd();
		tblupdate = dataProvider.getAlldata();
		tblmstimmunizationANS = dataProvider.gettblmstimmunizationANS("", 0);
		tblPNChomevisit_ANS = dataProvider.gettblPNChomevisit_ANS("", 0);

		if ((Child != null && Child.size() > 0)
				|| (pregnantwomen != null && pregnantwomen.size() > 0)
				|| (ANCVisit != null && ANCVisit.size() > 0)
				|| (tblmstimmunizationANS != null && tblmstimmunizationANS
						.size() > 0)
				|| (tblPNChomevisit_ANS != null && tblPNChomevisit_ANS.size() > 0)) {

			try {

				if ((Child != null && Child.size() > 0)
						|| (pregnantwomen != null && pregnantwomen.size() > 0)
						|| (ANCVisit != null && ANCVisit.size() > 0)
						|| (tblmstimmunizationANS != null && tblmstimmunizationANS
								.size() > 0)
						|| (tblPNChomevisit_ANS != null && tblPNChomevisit_ANS
								.size() > 0)) {

					exportMNCHdata();
					exportimage(pregnantwomen);
					exportAnswerdata();

				}
			}

			catch (Exception exp) {

			}

		}

	}

	public void exportHHSurveydata() {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {

				if (HHSurvey.size() > 0) {
					String json1 = new Gson().toJson(HHSurvey);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tbl_hhsurvey", JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_hhsurvey", otherJsonArray);

				}

				if (HHFamilyMember.size() > 0) {
					String json1 = new Gson().toJson(HHFamilyMember);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tbl_hhfamilymember",
							JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined
							.put("tbl_hhfamilymember", otherJsonArray);

				}
				if (migration.size() > 0) {
					String json1 = new Gson().toJson(migration);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tblmigration", JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblmigration", otherJsonArray);

				}
				if (tblupdate.size() > 0) {
					String json1 = new Gson().toJson(tblupdate);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tblhhupdate_log",
							JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblhhupdate_log", otherJsonArray);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {

					if (responseBody.contains("success")) {

						String sqlHHSurvey = "Update Tbl_HHSurvey set IsUploaded=1,IsEdited=0 where IsEdited=1 and HHSurveyGUID='"
								+ hhguid + "' ";
						dataProvider.executeSql(sqlHHSurvey);
						String sqlHHFamilyMember = "Update Tbl_HHFamilyMember set IsUploaded=1,IsEdited=0 where IsEdited=1 and HHSurveyGUID='"
								+ hhguid + "' ";
						dataProvider.executeSql(sqlHHFamilyMember);
						String sqlMigration = "Update tblMigration set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlMigration);
						String sqltblhhupdate_Log = "Update tblhhupdate_Log set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblhhupdate_Log);
						iDataUpload = 1;

					} else {

						iDataUpload = 0;
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public void exportmoduleusage() {
		try {
			ModuleUsage = dataProvider.getModuleusage(0);
			if (ModuleUsage != null && ModuleUsage.size() > 0) {
				HttpClient httpClient = new DefaultHttpClient();
				// replace with your url
				HttpPost httpPost = new HttpPost(
						"replace your URL");
				JSONObject jsonObjectcombined = new JSONObject();
				try {

					if (ModuleUsage.size() > 0) {
						String json1 = new Gson().toJson(ModuleUsage);
						JSONArray JSONArray_HHSurvey = new JSONArray(json1);
						jsonObjectcombined.put("tbl_detailedmoduleusage",
								JSONArray_HHSurvey);
					} else {
						JSONArray otherJsonArray = new JSONArray();
						jsonObjectcombined.put("tbl_detailedmoduleusage",
								otherJsonArray);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Post Data
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);

				nameValuePair.add(new BasicNameValuePair("username", global
						.getsGlobalUserName()));

				nameValuePair.add(new BasicNameValuePair("password", global
						.getsGlobalPassword()));

				nameValuePair.add(new BasicNameValuePair("data",
						jsonObjectcombined.toString()));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					// log exception
					e.printStackTrace();
				}

				// making POST request.
				try {
					HttpResponse response = httpClient.execute(httpPost);
					String responseBody = EntityUtils.toString(response
							.getEntity());

					try {

						if (responseBody.contains("success")) {

							String sqltblhhupdate_Log = "Update tbl_DetailedModuleUsage set IsUpload=1  where IsUpload=0 ";
							dataProvider.executeSql(sqltblhhupdate_Log);
							iDataUpload = 1;

						} else {

							iDataUpload = 0;
						}

					} catch (Exception e) {
						System.out.println(e);
					}

				} catch (ClientProtocolException e) {
					// Log exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public void expordevicedata() {
		try {
			devicespaceusage = dataProvider.getDevicedata(0);
			if (devicespaceusage != null && devicespaceusage.size() > 0) {
				HttpClient httpClient = new DefaultHttpClient();
				// replace with your url
				HttpPost httpPost = new HttpPost(
						"replace your URL");
				JSONObject jsonObjectcombined = new JSONObject();
				try {

					if (devicespaceusage.size() > 0) {
						String json1 = new Gson().toJson(devicespaceusage);
						JSONArray JSONArray_HHSurvey = new JSONArray(json1);
						jsonObjectcombined.put("tbldevicespaceusage",
								JSONArray_HHSurvey);
					} else {
						JSONArray otherJsonArray = new JSONArray();
						jsonObjectcombined.put("tbldevicespaceusage",
								otherJsonArray);

					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Post Data
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
						2);

				nameValuePair.add(new BasicNameValuePair("username", global
						.getsGlobalUserName()));

				nameValuePair.add(new BasicNameValuePair("password", global
						.getsGlobalPassword()));

				nameValuePair.add(new BasicNameValuePair("data",
						jsonObjectcombined.toString()));

				try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
							HTTP.UTF_8));
				} catch (UnsupportedEncodingException e) {
					// log exception
					e.printStackTrace();
				}

				// making POST request.
				try {
					HttpResponse response = httpClient.execute(httpPost);
					String responseBody = EntityUtils.toString(response
							.getEntity());

					try {

						if (responseBody.contains("success")) {

							String sqlspace = "delete  from  tbldevicespaceusage where cast(round((julianday('NOW')-julianday(createdon)))  as int)>30";
							dataProvider.executeSql(sqlspace);

							//
						}

					} catch (Exception e) {
						System.out.println(e);
					}

				} catch (ClientProtocolException e) {
					// Log exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public void exportimage(ArrayList<tbl_pregnantwomen> prg) {

		HttpClient httpClient = new DefaultHttpClient();
		// replace with your url
		HttpPost httpPost = new HttpPost("replace your URL");


		String TAG1 = "Response";
		if (prg != null && prg.size() > 0) {
			for (int i = 0; i < prg.size(); i++) {
				int iFlag = prg.get(i).getPWImage().length();
				if (prg.get(i).getPWImage() != null
						&& prg.get(i).getPWImage().length() > 8) {
					String ImageFieldName = "";
					String ImageName = "";

					String UID = "";
					// iFlag = prg.get(i).getImageFlag();
					UID = prg.get(i).getPWGUID();
					ImageFieldName = prg.get(i).getPWName();
					ImageName = prg.get(i).getPWImage();
					File mediaStorageDir = new File(
							Environment
									.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
							IMAGE_DIRECTORY_NAME);
					String iImagePath = mediaStorageDir.getPath()
							+ File.separator + ImageName;
					// Bitmap bitmap = BitmapFactory.decodeFile(iImagePath);
					/*
					 * File mediaStorageDir = new File( Environment
					 * .getExternalStoragePublicDirectory
					 * (Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
					 * String iImagePath = mediaStorageDir.getPath() +
					 * File.separator;
					 */

					Bitmap bm = BitmapFactory.decodeFile(iImagePath);
					String encodedImage = "";
					if (bm != null) {

						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						File source = new File(iImagePath);
						long length = source.length();
						length = length / 1024;
						if (length > 200.00) {
							bm.compress(Bitmap.CompressFormat.JPEG, 60, baos);
						} else {
							bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
						}

						byte[] b = baos.toByteArray();
						encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

						List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
								2);
						nameValuePair.add(new BasicNameValuePair("username",
								global.getsGlobalUserName()));
						nameValuePair.add(new BasicNameValuePair("password",
								global.getsGlobalPassword()));
						nameValuePair.add(new BasicNameValuePair("filebytes",
								encodedImage.toString()));
						nameValuePair.add(new BasicNameValuePair("fileName",
								ImageName));

						// Encoding POST data
						try {

							try {

								httpPost.setEntity(new UrlEncodedFormEntity(
										nameValuePair, HTTP.UTF_8));
							} catch (UnsupportedEncodingException e) {
								// log exception
								e.printStackTrace();
							}
							try {
								HttpResponse response = httpClient
										.execute(httpPost);
								String responseBody = EntityUtils
										.toString(response.getEntity());
								try {

									if (responseBody.toLowerCase().contains(
											"success")) {

									}
								} catch (Exception e) {
									// Log exception
									e.printStackTrace();
								}
							} catch (ClientProtocolException e) {
								// Log exception
								e.printStackTrace();
							} catch (Exception e) {
								// Log exception
								e.printStackTrace();
							}
						} catch (Exception e) {
							// Log exception
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void exportMNCHdata() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {
				if (Child.size() > 0) {
					String json1 = new Gson().toJson(Child);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tblchild", JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblchild", otherJsonArray);

				}
				if (ANCVisit.size() > 0) {
					String json1 = new Gson().toJson(ANCVisit);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tbl_ancvisit", JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_ancvisit", otherJsonArray);

				}
				if (pregnantwomen.size() > 0) {
					String json1 = new Gson().toJson(pregnantwomen);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tbl_pregnantwomen",
							JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_pregnantwomen", otherJsonArray);

				}
				if (tblDates.size() > 0) {
					String json1 = new Gson().toJson(tblDates);
					JSONArray JSONArray_tbl_DatesEd = new JSONArray(json1);
					jsonObjectcombined
							.put("tbl_datesed", JSONArray_tbl_DatesEd);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_datesed", otherJsonArray);

				}
				if (tblupdate.size() > 0) {
					String json1 = new Gson().toJson(tblupdate);
					JSONArray JSONArray_HHSurvey = new JSONArray(json1);
					jsonObjectcombined.put("tblhhupdate_log",
							JSONArray_HHSurvey);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblhhupdate_log", otherJsonArray);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {

					if (responseBody.contains("success")) {

						String sqltblChild = "Update tblChild set IsUploaded=1, IsEdited=0 where IsEdited=1";
						dataProvider.executeSql(sqltblChild);
						String sqltblPregnant_woman = "Update tblPregnant_woman set IsUploaded=1, IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblPregnant_woman);
						String sqlTblANCVisit = "Update TblANCVisit set IsUploaded=1, IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqlTblANCVisit);
						String sqltblhhupdate_Log = "Update tblhhupdate_Log set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblhhupdate_Log);

						iDataUploadMNCH = 1;

					} else {

						iDataUploadMNCH = 0;
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	// VHND_Duelist,VHND_Performace
	public void exportUploadVHNDoforms() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {

				if (VHND_Duelist.size() > 0) {
					String json1 = new Gson().toJson(VHND_Duelist);
					JSONArray JSONArray_mstFPAns = new JSONArray(json1);
					jsonObjectcombined.put("tbl_vhnd_dueList",
							JSONArray_mstFPAns);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_vhnd_dueList", otherJsonArray);
				}
				if (VHND_Performace.size() > 0) {
					String json1 = new Gson().toJson(VHND_Performace);
					JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
					jsonObjectcombined.put("tbl_vhndperformance",
							JSONArray_mstFPFDetail);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tbl_vhndperformance",
							otherJsonArray);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {

					if (responseBody.contains("success")) {

						iDataUploadfp = 1;
						String sqltblmstFPAns = "Update tbl_VHND_DueList set IsUploaded=1";
						dataProvider.executeSql(sqltblmstFPAns);
						String sqltblmstper = "Update tbl_VHNDPerformance set IsUploaded=1";
						dataProvider.executeSql(sqltblmstper);

					} else {
						iDataUploadfp = 1;
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

	public void exportUploadVHNDduelist() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {
				if (VHNDDuelistnew.size() > 0) {
					String json1 = new Gson().toJson(VHNDDuelistnew);
					JSONArray JSONArray_mstFPAns = new JSONArray(json1);
					jsonObjectcombined
							.put("tblvhndduelist", JSONArray_mstFPAns);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblvhndduelist", otherJsonArray);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {

					if (responseBody.contains("success")) {
						iDataUploadfp = 1;
						String sqltblmstFPAns = "Update tblVHNDDuelist set IsUploaded=1 where IsUploaded=0";
						dataProvider.executeSql(sqltblmstFPAns);

					} else {

						iDataUploadfp = 0;
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	public void exportFamilyPlanningdata() {
		try {

			HttpClient httpClient = new DefaultHttpClient();
			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {

				if (mstFPAns.size() > 0) {
					String json1 = new Gson().toJson(mstFPAns);
					JSONArray JSONArray_mstFPAns = new JSONArray(json1);
					jsonObjectcombined.put("tblfp_visit", JSONArray_mstFPAns);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblfp_visit", otherJsonArray);

				}

				if (mstFPFDetail.size() > 0) {
					String json1 = new Gson().toJson(mstFPFDetail);
					JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
					jsonObjectcombined.put("tblfp_followup",
							JSONArray_mstFPFDetail);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblfp_followup", otherJsonArray);

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {
					if (responseBody.contains("success")) {

						String sqltblmstFPAns = "Update tblFP_followup set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstFPAns);
						String sqltblFP_visit = "Update tblFP_visit set IsUploaded=1,IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblFP_visit);

						iDataUploadfp = 1;

					} else {

						iDataUploadfp = 0;

					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}
	}

	public void exportFamilyPlanning() {

		// TODO Auto-generated method stub
		mstFPAns = dataProvider.getFP_Ans("", 1);
		mstFPFDetail = dataProvider.getFP_Detail("", 2);

		if ((mstFPAns != null && mstFPAns.size() > 0)
				|| (mstFPFDetail != null && mstFPFDetail.size() > 0)) {

			new Thread() {

				@Override
				public void run() {
					try {

						exportFamilyPlanningdata();

					}

					catch (Exception exp) {

					}

				}
			}.start();
		}

	}

	public void exportUPloadVNHD() {

		// TODO Auto-generated method stub
		VHND_Duelist = dataProvider.getVHND_Duelist("", "", 2);
		VHND_Performace = dataProvider.getVHND_Performance("", "", 2);
		VHNDDuelistnew = dataProvider.getVHNDReport("", 2, "");
		if ((VHND_Duelist != null && VHND_Duelist.size() > 0)
				|| (VHND_Performace != null && VHND_Performace.size() > 0)
				|| (VHNDDuelistnew != null && VHNDDuelistnew.size() > 0)) {

			new Thread() {

				@Override
				public void run() {
					try {
						// VHND_Duelist,VHND_Performace

						exportUploadVHNDoforms();
						exportUploadVHNDduelist();

					}

					catch (Exception exp) {

					}

				}
			}.start();
		}

	}

	public void exportAnswerdata() {
		try {
			HttpClient httpClient = new DefaultHttpClient();

			// replace with your url
			HttpPost httpPost = new HttpPost("replace your URL");
			JSONObject jsonObjectcombined = new JSONObject();
			try {

				if (tblmstimmunizationANS.size() > 0) {
					String json1 = new Gson().toJson(tblmstimmunizationANS);
					JSONArray JSONArray_mstFPAns = new JSONArray(json1);
					jsonObjectcombined.put("tblmstimmunizationans",
							JSONArray_mstFPAns);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblmstimmunizationans",
							otherJsonArray);

				}

				if (tblPNChomevisit_ANS.size() > 0) {
					String json1 = new Gson().toJson(tblPNChomevisit_ANS);
					JSONArray JSONArray_mstFPFDetail = new JSONArray(json1);
					jsonObjectcombined.put("tblpnchomevisit_ans",
							JSONArray_mstFPFDetail);
				} else {
					JSONArray otherJsonArray = new JSONArray();
					jsonObjectcombined.put("tblpnchomevisit_ans",
							otherJsonArray);

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Post Data
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);

			nameValuePair.add(new BasicNameValuePair("username", global
					.getsGlobalUserName()));

			nameValuePair.add(new BasicNameValuePair("password", global
					.getsGlobalPassword()));

			nameValuePair.add(new BasicNameValuePair("data", jsonObjectcombined
					.toString()));

			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				// log exception
				e.printStackTrace();
			}

			// making POST request.
			try {
				HttpResponse response = httpClient.execute(httpPost);
				String responseBody = EntityUtils
						.toString(response.getEntity());

				try {

					if (responseBody.contains("success")) {

						String sqltblmstimmunizationANS = "Update tblmstimmunizationANS set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblmstimmunizationANS);
						String sqltblPNChomevisit_ANS = "Update tblPNChomevisit_ANS set IsEdited=0 where IsEdited=1 ";
						dataProvider.executeSql(sqltblPNChomevisit_ANS);

						iDataUploadImmu = 1;

					} else {
						iDataUploadpncans = 0;
					}

				} catch (Exception e) {
					System.out.println(e);
				}

			} catch (ClientProtocolException e) {
				// Log exception
				e.printStackTrace();
			}
		} catch (Exception e) {

			System.out.println(e);
		}

	}

}
