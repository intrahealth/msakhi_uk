package com.microware.intrahealth;

import android.annotation.SuppressLint;
import android.net.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class Validate {

	public static String PregMETHOD_NAME = "PostDataPW";
	public static String AdolMETHOD_NAME = "PostDataAG";
	public static String SOAP_ACTION = "http://rfapps.microwarecomp.com/PostData";
	public static String AdolSOAP_ACTION = "http://rfapps.microwarecomp.com/PostDataAG";
	public static String PregSOAP_ACTION = "http://rfapps.microwarecomp.com/PostDataPW";
	public static String METHOD_NAME = "PostData";
	public static String NAMESPACE = "http://rfapps.microwarecomp.com/";
	public static String URL = "http://rfapps.microwarecomp.com/ExportdataWebservice.asmx";

	// public static String PutImage="http://rfapps.microwarecomp.com/PutImage";
	public static String PutImage = "http://tempuri.org/PutImage";
	public static String NAMESPACE1 = "http://tempuri.org/";

	// public static String URL =
	// "http://192.168.1.35/RFApps/Exportdatawebservice.asmx";
	public static String getcurrentdate() {
		String languageToLoad = "en"; // your language
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateandTime = sdf.format(new Date());
		return currentDateandTime;
	}

	public static String random() {
		char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String languageToLoad = "en"; // your language
		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");

		Date date = new Date();
		String SDateString = dateFormat.format(date);
		return sb.toString() + SDateString;
	}

	public static int checkmobileno(String mobileno) {

		if (mobileno != null && !mobileno.equalsIgnoreCase("null")
				&& mobileno.length() > 0) {
			String s = mobileno; // get your editext value here
			Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");

			Matcher matcher = pattern.matcher(s);
			// Check if pattern matches
			if (matcher.matches()) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;

	}

	public static int checkifsc(String ifsc) {
		if (ifsc != null && !ifsc.equalsIgnoreCase("null") && ifsc.length() > 0) {
			String s = ifsc; // get your editext value here
			Pattern pattern = Pattern.compile("[A-Z|a-z]{4}[0]{1}[0-9]{6}");

			Matcher matcher = pattern.matcher(s);
			// Check if pattern matches
			if (matcher.matches()) {
				return 1;
			} else {
				return 4;
			}
		}
		return 4;

	}

	public static long Daybetween(String date1, String date2, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
		Date Date1 = null, Date2 = null;
		try {
			Date1 = sdf.parse(date1);
			Date2 = sdf.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Date2.getTime() - Date1.getTime()) / (24 * 60 * 60 * 1000);
	}

	@SuppressWarnings("unused")
	public static int CalculateYM(int Year) {
		int newdiff = 0;
		final Calendar c = Calendar.getInstance();
		int mTMYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH) + 1;
		int cDay = c.get(Calendar.DAY_OF_MONTH);
		// int newmonth=
		// int month=mMonth-04;
		int newyear = mTMYear - Year;
		newdiff = newyear;
		return newdiff;

	}

	@SuppressWarnings("unused")
	public static int CountAge(String sDob) {
		int age = 0;

		SimpleDateFormat df;
		if (sDob != null && !sDob.equalsIgnoreCase("null")
				&& (sDob.trim()).length() > 0) {
			String[] Cdt = sDob.split("-");
			int Day = Integer.valueOf(Cdt[2]);
			int month = Integer.valueOf(Cdt[1]);
			int intyear = Integer.valueOf(Cdt[0]);
			int idx = sDob.lastIndexOf("-");
			final Calendar c = Calendar.getInstance();
			int mTMYear = c.get(Calendar.YEAR);
			int mMonth = c.get(Calendar.MONTH) + 1;
			int cDay = c.get(Calendar.DAY_OF_MONTH);
			int totYear = mTMYear - intyear;
			if ((month > mMonth) || (month == mMonth && Day > cDay)) {
				totYear = totYear - 1;
			}
			age = totYear;

		}

		return age;
	}

	public static String changeDateFormat(String Dt) {
		String date = "";
		if (Dt != null && Dt.length() > 0) {
			Dt.toString().trim();
			System.out.println(Dt);
			String[] Cdt = Dt.split("-");
			String DD = Cdt[0];
			String MM = Cdt[1];
			String YYYY = Cdt[2];

			try {
				date = YYYY.trim() + "-" + MM.trim() + "-" + DD.trim();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

			return date;
		} else {
			return date;
		}
	}

	public static String changeDateFormatpadding(String Dt) {
		String date = "";
		if (Dt != null && Dt.length() > 0) {
			try {
			Dt.toString().trim();
			System.out.println(Dt);
			String[] Cdt = Dt.split("-");
			String DD = Cdt[0];
			String MM = Cdt[1];
			String YYYY = Cdt[2];
			if (DD.length() == 1) {
				DD = "0" + Cdt[0];
			}
			if (MM.length() == 1) {
				MM = "0" + Cdt[1];
			}
			if (YYYY.length() == 1) {
				YYYY = "0" + Cdt[2];
			}
			
				date =  DD.trim() + "-" + MM.trim() + "-" + YYYY.trim();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}

			return date;
		} else {
			return date;
		}
	}

	@SuppressWarnings("unused")
	public static String changeDateTimeFormat(String Dt) {
		if (Dt != null && Dt.length() > 0) {
			String[] Ct = Dt.split("T");
			String Date1 = Ct[0];
			String Time1 = Ct[1];
			String[] Cdt = Date1.split("-");
			String YYYY = Cdt[0];
			String MM = Cdt[1];
			String DD = Cdt[2];
			String date = DD + "-" + MM + "-" + YYYY;
			return date;
		} else {
			return "";
		}
	}

	public static String getContractMonth(String DOB, String currentdate) {
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
		String months = "0";
		try {
			Date startDate = dfDate.parse(DOB);
			Date endDate = dfDate.parse(currentdate);

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endDate);

			int diffYear = endCalendar.get(Calendar.YEAR)
					- startCalendar.get(Calendar.YEAR);
			int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH)
					- startCalendar.get(Calendar.MONTH);
			int diffdays = endCalendar.get(Calendar.DAY_OF_MONTH)
					- startCalendar.get(Calendar.DAY_OF_MONTH);
			months = diffMonth + "/" + diffdays;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return months;
	}

	public static String changeDateFormat2(String Dt) {
		if (Dt != null && Dt.length() > 0) {
			String[] Cdt = Dt.split("-");
			String YYYY = Cdt[0];
			String MM = Cdt[1];
			String DD = Cdt[2];
			String date = DD + "-" + MM + "-" + YYYY;
			return date;
		} else {
			return "";
		}
	}

	public static int getDate(String date1) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		try {
			Calendar c = Calendar.getInstance(Locale.US);
			String current = getDate(c);
			Date today = sdf.parse(current);
			Date d1 = sdf.parse(date1);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(d1);
			cal2.setTime(today);
			if (cal1.after(cal2)) {
				return 3;
			} else if (cal1.before(cal2)) {
				return 1;
			}
			if (cal1.equals(cal2)) {
				return 2;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public static int getDate(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		try {
			Date d2 = sdf.parse(date2);
			Date d1 = sdf.parse(date1);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(d1);
			cal2.setTime(d2);
			if (cal1.after(cal2)) {
				return 3;
			} else if (cal1.before(cal2)) {
				return 1;
			}
			if (cal1.equals(cal2)) {
				return 2;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public static String getdateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateandTime = sdf.format(new Date());
		return currentDateandTime;
	}

	/*
	 * public static int checkmobileno(String mobileno) {
	 * 
	 * if (mobileno != null && !mobileno.equalsIgnoreCase("null") &&
	 * mobileno.length() > 0) { String s = mobileno; // get your editext value
	 * here Pattern pattern = Pattern.compile("[7-9]{1}[0-9]{9}");
	 * 
	 * Matcher matcher = pattern.matcher(s); // Check if pattern matches if
	 * (matcher.matches()) { return 1; } else { return 0; } } return 0;
	 * 
	 * }
	 */

	public static int getDatecompare(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		try {
			java.util.Date d2 = sdf.parse(date2);
			java.util.Date d1 = sdf.parse(date1);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(d1);
			cal2.setTime(d2);
			if (cal1.after(cal2)) {
				return 3;
			} else if (cal1.before(cal2)) {
				return 1;
			}
			if (cal1.equals(cal2)) {
				return 2;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return 0;
	}

	public static String getDate(Calendar cal) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		String date = sdf.format(cal.getTime());

		return date;
	}

	public static int dateyear(String year) {

		if (year != null && !year.equalsIgnoreCase("null") && year.length() > 0) {
			String s = year;
			Pattern pattern = Pattern.compile("[1-2]{1}[][5-9]{1}[0-9]{1}");
			Matcher matcher = pattern.matcher(s);
			// Check if pattern matches
			if (matcher.matches()) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;

	}

	public static boolean isTextValid(String text) {
		boolean iValue = false;
		String[] blackList = { "--", ";--", "<script>", "waitfor delay", "/*",
				"*/", "@@", " alter ", "alter ", " begin ", "begin ",
				" create table ", "create table ", " cursor ", "cursor ",
				" declare ", "declare ", " delete ", "delete ",
				" delete table ", "delete table ", " drop ", "drop ",
				"select ", " drop table ", "drop table ", "<script>", " exec ",
				"exec ", " execute ", "execute ", " fetch ", "fetch ", "=",
				" or ", " insert into ", "insert into ", " kill ", "kill ",
				" sys.", "sys. ", "sysobjects", " table ", "table ",
				" update ", "update ", "&lt;", "&gt;", "&#40;", "&#41;",
				"&#35;", "&amp;", "&quot;", " or ", " && ", "!", "?", ":", ";",
				"||", "==" };
		for (int i = 0; i < blackList.length; i++) {
			if (text.toUpperCase().contains(blackList[i].toUpperCase())) {
				iValue = true;
				break;
			}
		}
		return iValue;

	}

	@SuppressWarnings("unused")
	public static long diffdays(String Date) {
		long diffDays = 0;
		if (Date != null && Date.length() > 0) {

			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			Date date = new Date();
			String datenew = Date;
			System.out.println(dateFormat.format(date));
			String newdate = getcurrentdate();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = null;
			Date d2 = null;

			try {
				d1 = dateFormat.parse(newdate);
				d2 = dateFormat.parse(datenew);

				// in milliseconds
				long diff = d2.getTime() - d1.getTime();

				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000) % 24;
				diffDays = diff / (24 * 60 * 60 * 1000);

			} catch (Exception e) {

			}
		}
		return diffDays;
	}

	@SuppressWarnings("unused")
	private static String convertToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (byte b : data) {
			int halfbyte = (b >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte)
						: (char) ('a' + (halfbyte - 10)));
				halfbyte = b & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	/*
	 * public static String SHA1(String text) throws NoSuchAlgorithmException,
	 * UnsupportedEncodingException { MessageDigest md =
	 * MessageDigest.getInstance("SHA-1");
	 * md.update(text.getBytes("iso-8859-1"), 0, text.length()); byte[] sha1hash
	 * = md.digest(); return convertToHex(sha1hash); }
	 */

	// private static final String ALGORITHM = "AES";
	// private static final byte[] keyValue =
	// new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e',
	// 't', 'K', 'e', 'y' };
	//
	// public static String encrypt(String valueToEnc) throws Exception {
	// Key key = generateKey();
	// Cipher c = Cipher.getInstance(ALGORITHM);
	// c.init(Cipher.ENCRYPT_MODE, key);
	// byte[] encValue = c.doFinal(valueToEnc.getBytes());
	// String encryptedValue = new BASE64Encoder().encode(encValue);
	// return encryptedValue;
	// }
	//
	// public static String decrypt(String encryptedValue) throws Exception {
	// Key key = generateKey();
	// Cipher c = Cipher.getInstance(ALGORITHM);
	// c.init(Cipher.DECRYPT_MODE, key);
	// byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
	// byte[] decValue = c.doFinal(decordedValue);
	// String decryptedValue = new String(decValue);
	// return decryptedValue;
	// }
	// private static Key generateKey() throws Exception {
	// Key key = new SecretKeySpec(keyValue, ALGORITHM);
	// // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
	// // key = keyFactory.generateSecret(new DESKeySpec(keyValue));
	// return key;
	// }
	//
	//

	public static boolean isDeviceRooted() {
		return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
	}

	private static boolean checkRootMethod1() {
		String buildTags = android.os.Build.TAGS;
		return buildTags != null && buildTags.contains("test-keys");
	}

	private static boolean checkRootMethod2() {
		String[] paths = { "/system/app/Superuser.apk", "/sbin/su",
				"/system/bin/su", "/system/xbin/su", "/data/local/xbin/su",
				"/data/local/bin/su", "/system/sd/xbin/su",
				"/system/bin/failsafe/su", "/data/local/su" };
		for (String path : paths) {
			if (new File(path).exists())
				return true;
		}
		return false;
	}

	private static boolean checkRootMethod3() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "/system/xbin/which", "su" });
			BufferedReader in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			if (in.readLine() != null)
				return true;
			return false;
		} catch (Throwable t) {
			return false;
		} finally {
			if (process != null)
				process.destroy();
		}
	}

}
