package com.microware.intrahealth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.microware.intrahealth.Global.TrackerName;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstANM;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.MstState;
import com.microware.intrahealth.object.TblMstuser;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.tblmstimmunizationQues;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity {
	Global global;
	DatabaseHelper dbHelper;
	@SuppressWarnings("unused")
	private static SQLiteDatabase dbIntraHealth;
	int iFPDataDownload;
	public ArrayList<tblmstFPAns> mstFPAns = new ArrayList<tblmstFPAns>();
	JSONArray tblmstFPAnsArray = null;

	public ArrayList<tblmstFPFDetail> mstFPFDetail = new ArrayList<tblmstFPFDetail>();
	JSONArray tblmstFPFDetailArray = null;
	public ArrayList<tblmstANCQues> mstANCQues = new ArrayList<tblmstANCQues>();
	JSONArray tblmstANCQuesArray = null;
	JSONArray tblmstFPQuesArray = null;
	public ArrayList<tblmstFPQues> tblmstFPQues = new ArrayList<tblmstFPQues>();
	JSONArray tblmstimmunizationQuesArray = null;
	public ArrayList<tblmstimmunizationQues> tblmstimmunizationQues = new ArrayList<tblmstimmunizationQues>();
	public ArrayList<TblMstuser> tblUsermst = new ArrayList<TblMstuser>();
	public ArrayList<MstState> MstState = new ArrayList<MstState>();
	JSONArray Mst_tbl_Incentives = null;
	JSONArray MstVHND_PerformanceIndicator = null;
	JSONArray MstVHND_DueListItems = null;
	JSONArray VHND_Schedule = null;
	public ArrayList<MstANM> tblAnmcode = new ArrayList<MstANM>();

	public ArrayList<MstASHA> tblASHACode = new ArrayList<MstASHA>();
	public ArrayList<q_bank> q_bank = new ArrayList<q_bank>();
	JSONArray q_bankArray = null;
	public static final int GRANTED = 0;
	public static final int DENIED = 1;
	public static final int BLOCKED_OR_NEVER_ASKED = 2;
	int iDataUploadfp;
	Button btnStart;
	TextView tv_fname, tv_version;
	EditText etUsername, etPassword;
	DataProvider dataProvider;
	ProgressDialog progressDialog;
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
	JSONArray MstCatchmentSupervisorArray = null;
	JSONArray MstPHCArray = null;
	JSONArray MstSubCenterVillageMappingArray = null;
	JSONArray MstCatchmentAreaArray = null;
	public int iDownloadMaster = 0;
	Activity activity;
	int REQUEST_CAMERA = 1;
	int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
	static final Integer LOCATION = 0x1;
	static final Integer WRITE_EXST = 0x3;
	static final Integer READ_EXST = 0x4;
	static final Integer Camera = 0x5;
	String mVersionNumber;

	// private EasyTracker easyTracker = null;
	/*
	 * JSONArray StudentBookArray = null; JSONArray StudentArray = null;
	 * JSONArray MstBookArray = null; JSONArray BooksAllocate = null; JSONArray
	 * MstClusterArray = null; JSONArray Mst3StaffArray = null;
	 */
	public String getVersion() {

		Context mContext = getApplicationContext();
		try {
			String pkg = mContext.getPackageName();
			mVersionNumber = mContext.getPackageManager()
					.getPackageInfo(pkg, 0).versionName;
		} catch (NameNotFoundException e) {
			mVersionNumber = "?";
		}
		return " Version " + mVersionNumber;
	}

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		// easyTracker = EasyTracker.getInstance(Login.this);
		global = (Global) getApplicationContext();
		etUsername = (EditText) findViewById(R.id.etusername);
		etPassword = (EditText) findViewById(R.id.etpassword);
		tv_fname = (TextView) findViewById(R.id.tv_fname);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText(String.valueOf(getVersion()));
		btnStart = (Button) findViewById(R.id.btnStart);
		dataProvider = new DataProvider(this);
		ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			Tracker t = ((Global) getApplication())
					.getTracker(TrackerName.APP_TRACKER);
			t.setScreenName("Login Screen");
			t.send(new HitBuilders.AppViewBuilder().build());
		}
		// Product product = new Product()
		// .setName("Dragon Food")
		// .setPrice(40.00);
		//
		// ProductAction productAction = new
		// ProductAction(ProductAction.ACTION_PURCHASE)
		// .setTransactionId("T12345");
		//
		// // Add the transaction data to the event.
		// HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder()
		// .setCategory("In-Game Store")
		// .setAction("Purchase")
		// .addProduct(product)
		// .setProductAction(productAction);

		// Send the transaction data with the event.
		// t.send(builder.build());
		/*
		 * Cursor mCursor = dbIntraHealth.rawQuery("SELECT * FROM MstUser" ,
		 * null); Boolean rowExists; if (mCursor.moveToFirst()) { String
		 * fname="Select UserName from MstUser "; String fullname =
		 * dataProvider.getRecord(fname); if(fullname.length()>0){ String uname
		 * = "Welcome"+" "+fullname; tv_fname.setText(uname); }
		 * 
		 * } else { tv_fname.setText(""); }
		 */
		// if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
		// {
		// askForPermission(Manifest.permission.ACCESS_FINE_LOCATION,LOCATION);
		// askForPermission(Manifest.permission.CAMERA,Camera);
		// askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,WRITE_EXST);
		// askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE,READ_EXST);
		// }
		String count = "SELECT count(*) FROM MstASHA";
		// Cursor mcursor = dbIntraHealth.rawQuery(count, null);
		// mcursor.moveToFirst();
		int icount = dataProvider.getMaxRecord(count);
		if (icount > 0) {
			String fname = "Select ASHAName from MstASHA ";
			String fullname = dataProvider.getRecord(fname);
			if (fullname.length() > 0) {
				String uname = fullname;
				tv_fname.setText(uname);
				global.setsGlobalAshaName(uname);
			} else {
				tv_fname.setText("");
			}
		}

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();// .Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		btnStart.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				backup();

				String sUserName = "";
				String sPassword = "";
				if (etUsername != null
						&& etUsername.getText() != null
						&& !Validate.isTextValid(etUsername.getText()
								.toString())) {
					sUserName = etUsername.getText().toString();
				}

				if (etPassword != null
						&& etPassword.getText() != null
						&& !Validate.isTextValid(etPassword.getText()
								.toString())) {
					sPassword = etPassword.getText().toString();
				}
				global.setLanguage(2);
				String sql = "Select count(UserID) from MstUser";
				int iCount = dataProvider.getMaxRecord(sql);

				if (iCount == 0) {

					downloaddata(sUserName, sPassword);
					// String sqluser = "Select UserID from MstUser";
					// String sUserID = dataProvider.getRecord(sqluser);
					// global.setsGlobalUserID(sUserID);
					// global.setsGlobalUserName(sUserName);
					// global.setsGlobalPassword(sPassword);
					Boolean isSDPresent = android.os.Environment
							.getExternalStorageState().equals(
									android.os.Environment.MEDIA_MOUNTED);
					if (isSDPresent) {
						// backup();
					}
				} else {

					Boolean isSDPresent = android.os.Environment
							.getExternalStorageState().equals(
									android.os.Environment.MEDIA_MOUNTED);
					if (isSDPresent) {
						// backup();
					}
					String sql1 = "";
					try {
						sql1 = "select count(UserID) from MstUser where UserName='"
								+ sUserName
								+ "' and Password='"
								+ sPassword
								+ "' ";
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int iCount1 = dataProvider.getMaxRecord(sql1);
					if (iCount1 == 1) {

						tblUsermst = dataProvider.tblmstUser(sUserName,
								sPassword);

						if (tblUsermst != null && tblUsermst.size() > 0) {

							int iroleid = 0;

							global.setsGlobalUserName(tblUsermst.get(0)
									.getUserNameL());
							global.setsGlobalPassword(tblUsermst.get(0)
									.getPassword());
							global.setsGlobalUserID(String.valueOf(tblUsermst
									.get(0).getUserID()));
							global.setiGlobalRoleID(tblUsermst.get(0)
									.getRoleID());

							iroleid = tblUsermst.get(0).getRoleID();
							MstState = dataProvider.getstate(1);
							if (MstState != null && MstState.size() > 0) {
								global.setStateCode(MstState.get(0)
										.getStateCode());
								global.setStateName(MstState.get(0)
										.getStateName());
							}

							if (iroleid == 3) {

								tblAnmcode = dataProvider.getMstANM(1);
								if (tblAnmcode != null && tblAnmcode.size() > 0) {
									global.setsGlobalANMCODE(String
											.valueOf(tblAnmcode.get(0)
													.getANMID()));
									global.setsGlobalANMName(tblAnmcode.get(0)
											.getANMName());
									global.setAnmidasAnmCode(String
											.valueOf(tblAnmcode.get(0)
													.getANMCode()));
								}

							} else if (iroleid == 2) {
								tblASHACode = dataProvider
										.getashanameandCode(1);
								if (tblASHACode != null
										&& tblASHACode.size() > 0) {
									global.setsGlobalAshaCode(String
											.valueOf(tblASHACode.get(0)
													.getASHAID()));
									tblAnmcode = dataProvider.getMstANM(1);
									if (tblAnmcode != null
											&& tblAnmcode.size() > 0) {
										global.setsGlobalANMCODE(String
												.valueOf(tblAnmcode.get(0)
														.getANMID()));
										global.setsGlobalANMName(tblAnmcode
												.get(0).getANMName());
										global.setAnmidasAnmCode(String
												.valueOf(tblAnmcode.get(0)
														.getANMCode()));
									}

								}
							}

							Intent in = new Intent(Login.this,
									Dashboard_Activity.class);
							finish();
							startActivity(in);
						}

						// String sqluser = "Select UserID from MstUser";
						// String sUserID = dataProvider.getRecord(sqluser);
						// global.setsGlobalUserID(sUserID);

						// if (etUsername.getText().toString().length() > 0
						// && etUsername.getText().toString() != null
						// && !Validate.isTextValid(etUsername.getText()
						// .toString())) {
						// global.setsGlobalUserName(etUsername.getText()
						// .toString());
						// } else {
						// global.setsGlobalUserName("");
						// }
						// if (etPassword.getText().toString().length() > 0
						// && etPassword.getText().toString() != null
						// && !Validate.isTextValid(etPassword.getText()
						// .toString())) {
						// global.setsGlobalPassword(etPassword.getText()
						// .toString());
						// } else {
						// global.setsGlobalPassword("");
						// }

					} else {
						Toast.makeText(getApplicationContext(), "Invalid user",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		// if(android.os.Build.VERSION.SDK_INT>23){
		// Intent intent = new Intent();
		// intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		// Uri uri = Uri.fromParts("package",this.getPackageName(), null);
		// intent.setData(uri);
		// startActivity(intent);
		// }else{
		// System.out.println("less version");
		// }

	}

	// private void askForPermission(String permission, Integer requestCode) {
	// if (this.checkSelfPermission( permission) !=
	// PackageManager.PERMISSION_GRANTED) {
	//
	// // Should we show an explanation?
	// if (this.shouldShowRequestPermissionRationale( permission)) {
	//
	// //This is called if user has denied the permission before
	// //In this case I am just asking the permission again
	// this.requestPermissions( new String[]{permission}, requestCode);
	//
	// } else {
	//
	// this.requestPermissions( new String[]{permission}, requestCode);
	// }
	// } else {
	// Toast.makeText(this, "" + permission + " is already granted.",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// @Override
	// public void onRequestPermissionsResult(int requestCode, String[]
	// permissions, int[] grantResults) {
	// super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	// if (this.checkSelfPermission( permissions[0]) ==
	// PackageManager.PERMISSION_GRANTED) {
	// switch (requestCode) {
	// //Location
	// case 1:
	// // askForGPS();
	// break;
	// //Call
	// case 2:
	// Intent callIntent = new Intent(Intent.ACTION_CALL);
	// callIntent.setData(Uri.parse("tel:" + "{This is a telephone number}"));
	// if (this.checkSelfPermission( Manifest.permission.CALL_PHONE) !=
	// PackageManager.PERMISSION_GRANTED) {
	// startActivity(callIntent);
	// }
	// break;
	// //Write external Storage
	// case 3:
	// break;
	// //Read External Storage
	// case 4:
	// Intent imageIntent = new Intent(Intent.ACTION_PICK,
	// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	// startActivityForResult(imageIntent, 11);
	// break;
	// //Camera
	// case 5:
	// Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	// startActivityForResult(takePictureIntent, 12);
	// }
	// break;
	//
	// }
	//
	// Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
	// }
	//
	// }

	public void trackevent() {
		// easyTracker.send(MapBuilder.createEvent("your_action",
		// "Login", "button_name/id", null).build());
	}

	@SuppressWarnings("unused")
	public String AuthenticateUser(String sUserName, String sPassword) {
		String sTokenCode = "";
		try {
			String URL = GlobalString.URL;
			String NAMESPACE = GlobalString.NAMESPACE;
			String SOAP_ACTION = GlobalString.SOAP_ACTION_AUTHENTICATE_USER;
			String METHOD_NAME = GlobalString.METHOD_NAME_AUTHENTICATE_USER;

			SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
			Request.addProperty("User", sUserName);
			Request.addProperty("Password", sPassword);
			SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			soapEnvelope.dotNet = true;
			soapEnvelope.setOutputSoapObject(Request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL,
					25000000);
			androidHttpTransport.debug = true;
			// this is the actual part that will call the webservice
			try {
				androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				String error = e.getMessage();
				Throwable stack = new Throwable().fillInStackTrace();
				StackTraceElement[] trace = stack.getStackTrace();

				String method = trace[0].getMethodName();
				String classname = trace[0].getClassName();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				String error = e.getMessage();
				Throwable stack = new Throwable().fillInStackTrace();
				StackTraceElement[] trace = stack.getStackTrace();

				String method = trace[0].getMethodName();
				String classname = trace[0].getClassName();

			}
			SoapObject result = (SoapObject) soapEnvelope.bodyIn;
			if (result != null) {
				sTokenCode = result.toString();
				sTokenCode = sTokenCode
						.substring(sTokenCode.indexOf("Value") + 5);
				sTokenCode = sTokenCode.substring(1, sTokenCode.indexOf(";"));

			}

		} catch (Exception e) {
			// TODO: handle exception
			String sERROR = "";
			sERROR = e.getMessage().toString();
		}
		return sTokenCode;

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
		String sQueryDeletetblmstANCQues = null;
		sQueryDeletetblmstANCQues = "Delete from  tblmstANCQues";
		dataProvider.executeSql(sQueryDeletetblmstANCQues);
	}

	public void downloaddata(String sUserName, String sPassword) {
		try {

			if (isNetworkconn()) {
				importmaster(sUserName, sPassword);

			} else
				showNewWorkError();

		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(Login.this, e.getMessage().toString(),
					Toast.LENGTH_LONG).show();
		}
	}

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
				sql = "Insert into tblmstFPQues( Q_id,Grp,Q_type,Ftext,Y_qid,N_qid,Oinfo,def,finishid,pans,qvid,dispseq,oprompt,Ans)values("
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
				Log.e("IMPORT DATA", "q_bank ");

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

			e.printStackTrace();

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

		AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
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

		progressDialog = ProgressDialog.show(Login.this, "", getResources()
				.getString(R.string.DataLoading));
		new Thread() {

			@Override
			public void run() {
				try {

					importMasterdata(sUserName, sPassword);
					importQuestion(sUserName, sPassword);

					// if (etUsername != null && etUsername.getText() != null) {
					// global.setsGlobalUserName(etUsername.getText()
					// .toString());
					// } else {
					// global.setsGlobalUserName("");
					// }
					// if (etPassword != null && etPassword.getText() != null) {
					// global.setsGlobalPassword(etPassword.getText()
					// .toString());
					// } else {
					// global.setsGlobalPassword("");
					// }
					// String sqluser = "Select UserID from MstUser";
					// String sUserID = dataProvider.getRecord(sqluser);
					// global.setsGlobalUserID(sUserID);
				} catch (Exception exp) {
					progressDialog.dismiss();
				}

				// if (iDownloadMaster == 1) {
				// Intent in = new Intent(Login.this, Dashboard_Activity.class);
				// finish();
				// startActivity(in);
				// } else {
				// /*
				// * Toast.makeText(Login.this,
				// * "Download failed.Please try again.", Toast.LENGTH_LONG);
				// */
				//
				// }
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						// if (iDownloadMaster == 1) {
						tblUsermst = dataProvider.tblmstUser(sUserName,
								sPassword);

						if (tblUsermst != null && tblUsermst.size() > 0) {

							int iroleid = 0;

							global.setsGlobalUserName(tblUsermst.get(0)
									.getUserNameL());
							global.setsGlobalPassword(tblUsermst.get(0)
									.getPassword());
							global.setsGlobalUserID(String.valueOf(tblUsermst
									.get(0).getUserID()));
							global.setiGlobalRoleID(tblUsermst.get(0)
									.getRoleID());
							MstState = dataProvider.getstate(1);
							if (MstState != null && MstState.size() > 0) {
								global.setStateCode(MstState.get(0)
										.getStateCode());
								global.setStateName(MstState.get(0)
										.getStateName());
							}
							iroleid = tblUsermst.get(0).getRoleID();
							if (iroleid == 3) {

								tblAnmcode = dataProvider.getMstANM(1);
								if (tblAnmcode != null && tblAnmcode.size() > 0) {
									global.setsGlobalANMCODE(String
											.valueOf(tblAnmcode.get(0)
													.getANMID()));
									global.setsGlobalANMName(tblAnmcode.get(0)
											.getANMName());
									global.setAnmidasAnmCode(String
											.valueOf(tblAnmcode.get(0)
													.getANMCode()));

								}

							} else if (iroleid == 2) {
								tblASHACode = dataProvider
										.getashanameandCode(1);
								if (tblASHACode != null
										&& tblASHACode.size() > 0) {
									global.setsGlobalAshaCode(String
											.valueOf(tblASHACode.get(0)
													.getASHAID()));
								}
								tblAnmcode = dataProvider.getMstANM(1);
								if (tblAnmcode != null && tblAnmcode.size() > 0) {
									global.setsGlobalANMCODE(String
											.valueOf(tblAnmcode.get(0)
													.getANMID()));
									global.setsGlobalANMName(tblAnmcode.get(0)
											.getANMName());
									global.setAnmidasAnmCode(String
											.valueOf(tblAnmcode.get(0)
													.getANMCode()));
								}

							}
							String fname = "Select ASHAName from MstASHA ";
							String fullname = dataProvider.getRecord(fname);
							if (fullname.length() > 0) {
								String uname = fullname;
								tv_fname.setText(uname);
								global.setsGlobalAshaName(uname);
							}
							Intent in = new Intent(Login.this,
									Dashboard_Activity.class);
							finish();
							startActivity(in);
						}

						else {
							Toast.makeText(getApplicationContext(),
									"Invalid user", Toast.LENGTH_SHORT).show();
						}

					}
				});
				progressDialog.dismiss();

			}

		}.start();

	}

	public void onResume() {
		super.onResume();

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
			// Herojit

			Mst_tbl_Incentives = jsonObj.getJSONArray("tbl_Incentive");
			for (int i = 0; i < Mst_tbl_Incentives.length(); i++) {
				JSONObject Incentive = Mst_tbl_Incentives.getJSONObject(i);
				int IUID = 0;
				int ASHA_ID = 0;
				String ShortName = null;
				String Description = null;
				String Amount = null;
				String Documents = null;
				String Effect_Fromdate = null;
				String Effect_todate = null;
				int Status = 0;
				String AreaType = null;
				String Query = null;
				String CreatedOn = null;
				String CreatedBy = null;
				int Item_id = 0;

				if (Incentive.getString("ASHA_ID") != null
						&& Incentive.getString("ASHA_ID").length() > 0
						&& !Incentive.getString("ASHA_ID").equalsIgnoreCase(
								"null")) {
					ASHA_ID = Integer.valueOf(Incentive.getInt("ASHA_ID"));
				}
				ShortName = Incentive.getString("ShortName");
				Description = Incentive.getString("Description");
				Amount = Incentive.getString("Amount");
				Documents = Incentive.getString("Documents");
				Effect_Fromdate = Incentive.getString("Effect_Fromdate");
				Effect_todate = Incentive.getString("Effect_todate");
				if (Incentive.getString("Status") != null
						&& Incentive.getString("Status").length() > 0
						&& !Incentive.getString("Status").equalsIgnoreCase(
								"null")) {
					Status = Integer.valueOf(Incentive.getInt("Status"));
				}
				AreaType = Incentive.getString("AreaType");
				Query = Incentive.getString("Query");
				CreatedOn = Incentive.getString("CreatedOn");
				CreatedBy = Incentive.getString("CreatedBy");
				if (Incentive.getString("item_id") != null
						&& Incentive.getString("item_id").length() > 0
						&& !Incentive.getString("item_id").equalsIgnoreCase(
								"null")) {
					Item_id = Integer.valueOf(Incentive.getInt("item_id"));
				}

				String sql = "";
				sql = "Insert into tbl_Incentive(ASHA_ID,ShortName,Description,Amount,Documents,Effect_Fromdate,Effect_todate,Status,AreaType,Query,CreatedOn,CreatedBy,Item_id)values("
						+ ASHA_ID
						+ ",'"
						+ ShortName
						+ "','"
						+ Description
						+ "','"
						+ Amount
						+ "','"
						+ Documents
						+ "','"
						+ Effect_Fromdate
						+ "','"
						+ Effect_todate
						+ "',"
						+ Status
						+ ",'"
						+ AreaType
						+ "','"
						+ Query
						+ "','"
						+ CreatedOn + "','" + CreatedBy + "'," + Item_id + ")";
				dataProvider.executeSql(sql);
			}
			MstVHND_PerformanceIndicator = jsonObj
					.getJSONArray("MstVHND_PerformanceIndicator");
			for (int i = 0; i < MstVHND_PerformanceIndicator.length(); i++) {
				JSONObject VHND_Perform = MstVHND_PerformanceIndicator
						.getJSONObject(i);
				int ID = 0;
				String Item = null;
				int LanguageId = 0;
				int Status = 0;
				if (VHND_Perform.getString("ID") != null
						&& VHND_Perform.getString("ID").length() > 0
						&& !VHND_Perform.getString("ID").equalsIgnoreCase(
								"null")) {
					ID = Integer.valueOf(VHND_Perform.getInt("ID"));
				}
				Item = VHND_Perform.getString("Item");
				if (VHND_Perform.getString("LanguageId") != null
						&& VHND_Perform.getString("LanguageId").length() > 0
						&& !VHND_Perform.getString("LanguageId")
								.equalsIgnoreCase("null")) {
					LanguageId = Integer.valueOf(VHND_Perform
							.getInt("LanguageId"));
				}
				if (VHND_Perform.getString("Status") != null
						&& VHND_Perform.getString("Status").length() > 0
						&& !VHND_Perform.getString("Status").equalsIgnoreCase(
								"null")) {
					Status = Integer.valueOf(VHND_Perform.getInt("Status"));
				}
				String sql = "";
				sql = "Insert into MstVHND_PerformanceIndicator(ID,Item,LanguageId,LanguageId)values("
						+ ID
						+ ",'"
						+ Item
						+ "','"
						+ LanguageId
						+ "','"
						+ LanguageId + "')";
				dataProvider.executeSql(sql);
			}
			MstVHND_DueListItems = jsonObj.getJSONArray("MstVHND_DueListItems");
			for (int i = 0; i < MstVHND_DueListItems.length(); i++) {
				JSONObject VHND_Duelist = MstVHND_DueListItems.getJSONObject(i);
				int ID = 0;
				String Items = null;
				int LanguageId = 0;
				int Status = 0;
				if (VHND_Duelist.getString("ID") != null
						&& VHND_Duelist.getString("ID").length() > 0
						&& !VHND_Duelist.getString("ID").equalsIgnoreCase(
								"null")) {
					ID = Integer.valueOf(VHND_Duelist.getInt("ID"));
				}
				Items = VHND_Duelist.getString("Items");
				if (VHND_Duelist.getString("LanguageId") != null
						&& VHND_Duelist.getString("LanguageId").length() > 0
						&& !VHND_Duelist.getString("LanguageId")
								.equalsIgnoreCase("null")) {
					LanguageId = Integer.valueOf(VHND_Duelist
							.getInt("LanguageId"));
				}
				if (VHND_Duelist.getString("Status") != null
						&& VHND_Duelist.getString("Status").length() > 0
						&& !VHND_Duelist.getString("Status").equalsIgnoreCase(
								"null")) {
					Status = Integer.valueOf(VHND_Duelist.getInt("Status"));
				}
				String sql = "";
				sql = "Insert into MstVHND_DueListItems(ID,Items,LanguageId,LanguageId)values("
						+ ID
						+ ",'"
						+ Items
						+ "','"
						+ LanguageId
						+ "','"
						+ LanguageId + "')";
				dataProvider.executeSql(sql);
			}
			VHND_Schedule = jsonObj.getJSONArray("VHND_Schedule");
			for (int i = 0; i < VHND_Schedule.length(); i++) {
				JSONObject VHND_DuelistSchedule = VHND_Schedule
						.getJSONObject(i);
				int Schedule_ID = 0;
				int SubCenter_ID = 0;
				int ANM_ID = 0;
				int ASHA_ID = 0;
				int Village_ID = 0;
				String AW_Name = null;
				String Frequency = null;
				int Year = 0;
				int Occurence = 0;
				int Days = 0;
				String Jan = "";
				String Feb = "";
				String Mar = "";
				String Apr = "";
				String May = "";
				String Jun = "";
				String Jul = "";
				String Aug = "";
				String Sep = "";
				String Oct = "";
				String Nov = "";
				String Dec = "";
				String active = null;
				String createdOn = null;
				int createdBy = 0;
				String updatedOn = null;
				int updatedBy = 0;
				String Year_Type = null;
				if (VHND_DuelistSchedule.getString("Schedule_ID") != null
						&& VHND_DuelistSchedule.getString("Schedule_ID")
								.length() > 0
						&& !VHND_DuelistSchedule.getString("Schedule_ID")
								.equalsIgnoreCase("null")) {
					Schedule_ID = Integer.valueOf(VHND_DuelistSchedule
							.getInt("Schedule_ID"));
				}
				if (VHND_DuelistSchedule.getString("SubCenter_Id") != null
						&& VHND_DuelistSchedule.getString("SubCenter_Id")
								.length() > 0
						&& !VHND_DuelistSchedule.getString("SubCenter_Id")
								.equalsIgnoreCase("null")) {
					SubCenter_ID = Integer.valueOf(VHND_DuelistSchedule
							.getInt("SubCenter_Id"));
				}
				if (VHND_DuelistSchedule.getString("ANM_ID") != null
						&& VHND_DuelistSchedule.getString("ANM_ID").length() > 0
						&& !VHND_DuelistSchedule.getString("ANM_ID")
								.equalsIgnoreCase("null")) {
					ANM_ID = Integer.valueOf(VHND_DuelistSchedule
							.getInt("ANM_ID"));
				}
				if (VHND_DuelistSchedule.getString("ASHA_ID") != null
						&& VHND_DuelistSchedule.getString("ASHA_ID").length() > 0
						&& !VHND_DuelistSchedule.getString("ASHA_ID")
								.equalsIgnoreCase("null")) {
					ASHA_ID = Integer.valueOf(VHND_DuelistSchedule
							.getInt("ASHA_ID"));
				}
				if (VHND_DuelistSchedule.getString("Village_ID") != null
						&& VHND_DuelistSchedule.getString("Village_ID")
								.length() > 0
						&& !VHND_DuelistSchedule.getString("Village_ID")
								.equalsIgnoreCase("null")) {
					Village_ID = Integer.valueOf(VHND_DuelistSchedule
							.getInt("Village_ID"));
				}
				AW_Name = VHND_DuelistSchedule.getString("AW_Name");
				Frequency = VHND_DuelistSchedule.getString("Frequency");
				if (VHND_DuelistSchedule.getString("Year") != null
						&& VHND_DuelistSchedule.getString("Year").length() > 0
						&& !VHND_DuelistSchedule.getString("Year")
								.equalsIgnoreCase("null")) {
					Year = Integer.valueOf(VHND_DuelistSchedule.getInt("Year"));
				}
				if (VHND_DuelistSchedule.getString("Occurence") != null
						&& VHND_DuelistSchedule.getString("Occurence").length() > 0
						&& !VHND_DuelistSchedule.getString("Occurence")
								.equalsIgnoreCase("null")) {
					Occurence = Integer.valueOf(VHND_DuelistSchedule
							.getInt("Occurence"));
				}
				if (VHND_DuelistSchedule.getString("Days") != null
						&& VHND_DuelistSchedule.getString("Days").length() > 0
						&& !VHND_DuelistSchedule.getString("Days")
								.equalsIgnoreCase("null")) {
					Days = Integer.valueOf(VHND_DuelistSchedule.getInt("Days"));
				}
				Jan = VHND_DuelistSchedule.getString("Jan");
				Feb = VHND_DuelistSchedule.getString("Feb");
				Mar = VHND_DuelistSchedule.getString("Mar");
				Apr = VHND_DuelistSchedule.getString("Apr");
				May = VHND_DuelistSchedule.getString("May");
				Jun = VHND_DuelistSchedule.getString("Jun");
				Jul = VHND_DuelistSchedule.getString("Jul");
				Aug = VHND_DuelistSchedule.getString("Aug");
				Sep = VHND_DuelistSchedule.getString("Sep");
				Oct = VHND_DuelistSchedule.getString("Oct");
				Nov = VHND_DuelistSchedule.getString("Nov");
				Dec = VHND_DuelistSchedule.getString("Dec");
				active = VHND_DuelistSchedule.getString("active");
				createdOn = VHND_DuelistSchedule.getString("createdOn");
				if (VHND_DuelistSchedule.getString("createdBy") != null
						&& VHND_DuelistSchedule.getString("createdBy").length() > 0
						&& !VHND_DuelistSchedule.getString("createdBy")
								.equalsIgnoreCase("null")) {
					createdBy = Integer.valueOf(VHND_DuelistSchedule
							.getInt("createdBy"));
				}
				updatedOn = VHND_DuelistSchedule.getString("updatedOn");
				if (VHND_DuelistSchedule.getString("updatedBy") != null
						&& VHND_DuelistSchedule.getString("updatedBy").length() > 0
						&& !VHND_DuelistSchedule.getString("updatedBy")
								.equalsIgnoreCase("null")) {
					updatedBy = Integer.valueOf(VHND_DuelistSchedule
							.getInt("updatedBy"));
				}
				Year_Type = VHND_DuelistSchedule.getString("Year_Type");
				String sql = "";
				sql = "Insert into VHND_Schedule(Schedule_ID,SubCenter_ID,ANM_ID,ASHA_ID,Village_ID,AW_Name,Frequency,Occurence,Days,Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec,active,createdOn,createdBy,updatedOn,updatedBy,Year_Type)values('"
						+ Schedule_ID
						+ "','"
						+ SubCenter_ID
						+ "','"
						+ ANM_ID
						+ "','"
						+ ASHA_ID
						+ "','"
						+ Village_ID
						+ "','"
						+ AW_Name
						+ "','"
						+ Frequency
						+ "','"
						+ Occurence
						+ "','"
						+ Days
						+ "','"
						+ Year
						+ "','"
						+ Jan
						+ "','"
						+ Feb
						+ "','"
						+ Mar
						+ "','"
						+ Apr
						+ "','"
						+ May
						+ "','"
						+ Jun
						+ "','"
						+ Jul
						+ "','"
						+ Aug
						+ "','"
						+ Sep
						+ "','"
						+ Oct
						+ "','"
						+ Nov
						+ "','"
						+ Dec
						+ "','"
						+ active
						+ "','"
						+ createdOn
						+ "','"
						+ createdBy
						+ "','"
						+ updatedOn
						+ "','"
						+ updatedBy
						+ "','"
						+ Year_Type
						+ "')";
				dataProvider.executeSql(sql);
			}

			iDownloadMaster = 1;
		}

		catch (Exception e) {

			e.printStackTrace();

		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			GoogleAnalytics.getInstance(Login.this).reportActivityStart(this);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		ConnectivityManager connMgrCheckConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfoCheckConnection = connMgrCheckConnection
				.getActiveNetworkInfo();
		if (networkInfoCheckConnection != null
				&& networkInfoCheckConnection.isConnected()
				&& networkInfoCheckConnection.isAvailable()) {
			GoogleAnalytics.getInstance(Login.this).reportActivityStop(this);
		}
	}

	public void backup() {
		try {
			File sdcard = Environment.getExternalStorageDirectory();
			File outputFile = new File(sdcard, "IntraHealth");

			if (!outputFile.exists())
				outputFile.createNewFile();

			File data = Environment.getDataDirectory();
			File inputFile = new File(data, "data/" + getPackageName()
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
			throw new Error("Copying Failed");
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		finish();
		startActivity(intent);
	}

}
