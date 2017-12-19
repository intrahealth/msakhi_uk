package com.microware.intrahealth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.ksoap2.serialization.SoapPrimitive;

import com.google.gson.Gson;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstASHA;
import com.microware.intrahealth.object.TblANCVisit;
import com.microware.intrahealth.object.Tbl_HHFamilyMember;
import com.microware.intrahealth.object.Tbl_HHSurvey;
import com.microware.intrahealth.object.q_bank;
import com.microware.intrahealth.object.tblChild;
import com.microware.intrahealth.object.tblMigration;
import com.microware.intrahealth.object.tblPNChomevisit_ANS;
import com.microware.intrahealth.object.tblVHNDDuelist;
import com.microware.intrahealth.object.tbl_DatesEd;
import com.microware.intrahealth.object.tbl_DetailedModuleUsage;
import com.microware.intrahealth.object.tbl_VHNDPerformance;
import com.microware.intrahealth.object.tbl_VHND_DueList;
import com.microware.intrahealth.object.tbl_pregnantwomen;
import com.microware.intrahealth.object.tbldevicespaceusage;
import com.microware.intrahealth.object.tblhhupdate_Log;
import com.microware.intrahealth.object.tblmstANCQues;
import com.microware.intrahealth.object.tblmstFPAns;
import com.microware.intrahealth.object.tblmstFPFDetail;
import com.microware.intrahealth.object.tblmstFPQues;
import com.microware.intrahealth.object.tblmstimmunizationANS;
import com.microware.intrahealth.object.tblmstimmunizationQues;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ReceiverPositioningAlarm extends BroadcastReceiver {

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		// Toast.makeText(context, "new request received by receiver",
		// Toast.LENGTH_SHORT).show();

		Intent in = new Intent(context, ConnectionService.class);
		context.startService(in);

	}

}
