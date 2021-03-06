package com.microware.intrahealth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microware.intrahealth.adapter.AncAdapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.MstVillage;
import com.microware.intrahealth.object.tbl_pregnantwomen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class AncActivity extends Activity {

    TextView ltvWomenID, ltvWomenName, ltvNxtvisitdate, totalcount;
    Button btnaddanc, btnanc, btnsearchFilter, btn_PregnantWomen;
    GridView gridanc;
    EditText etFamilyIDSearch;
    Global global;
    Locale myLocale;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    DataProvider dataProvider;
    Validate validate;
    ArrayAdapter<String> adapter;
    TableRow tbl_search, tbl_spinner;
    ConnectivityManager connMgrCheckConnection;
    NetworkInfo networkInfoCheckConnection;
    public ArrayList<tbl_pregnantwomen> pregnant = new ArrayList<tbl_pregnantwomen>();
    ImageView btnCapture;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.anc_activity);

        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());
        btnaddanc = (Button) findViewById(R.id.btnaddanc);

        btnanc = (Button) findViewById(R.id.btnanc);
        btn_PregnantWomen = (Button) findViewById(R.id.btn_PregnantWomen);
        tbl_search = (TableRow) findViewById(R.id.tbl_search);
        tbl_spinner = (TableRow) findViewById(R.id.tbl_spinner);
        gridanc = (GridView) findViewById(R.id.gridanc);
        btnsearchFilter = (Button) findViewById(R.id.btnsearchFilter);
        etFamilyIDSearch = (EditText) findViewById(R.id.etFamilyIDSearch);
        totalcount = (TextView) findViewById(R.id.totalcount);
        // ltvWomenID = (TextView) findViewById(R.id.ltvWomenID);
        ltvWomenName = (TextView) findViewById(R.id.ltvWomenName);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        btnCapture = (ImageView) findViewById(R.id.btnCapture);

        // ltvNxtvisitdate = (TextView) findViewById(R.id.ltvNxtvisitdate);


        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String localTime = date1.format(currentLocalTime);
        String anc_GUID = Validate.random();
        global.setAnc_GUID(String.valueOf(anc_GUID));
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStrings = sdf.format(date);
        dataProvider.getUserLogin(anc_GUID, global.getUserID(), "ANC", "ANC",
                localTime, dateStrings);
        if (global.getVHND_flag() == 1) {
            fillgrid(2, "");
            btnaddanc.setVisibility(View.INVISIBLE);
            tbl_search.setVisibility(View.GONE);
        } else {
            fillgrid(2, "");
            btnaddanc.setVisibility(View.VISIBLE);
            tbl_search.setVisibility(View.VISIBLE);
        }
        btnsearchFilter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (etFamilyIDSearch.getText().toString().length() > 0) {
                    fillgrid(1, "");
                }
            }
        });
        etFamilyIDSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub
                if (s.length() > 0) {
                    fillgrid(1, "");
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (etFamilyIDSearch.getText().toString().length() == 0) {
                    fillgrid(3, "");
                }
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    // start the scanning activity from the
                    // com.google.zxing.client.android.SCAN intent
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    // on catch, show the download dialog
                    showDialog(AncActivity.this,
                            getResources().getString(R.string.NoScannerFound),
                            getResources().getString(R.string.Downloadscanner),
                            getResources().getString(R.string.Yes),
                            getResources().getString(R.string.no)).show();
                }
            }
        });
        btnaddanc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (global.getiGlobalRoleID() == 2 || global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(AncActivity.this, PregWomenList.class);
                    finish();
                    startActivity(i);
                }
                //   createpdf();

            }
        });


        btn_PregnantWomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (global.getiGlobalRoleID() == 2 || global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(AncActivity.this, ReportIndicator_ashaList.class);
                    i.putExtra("flag", 1);
                    finish();
                    startActivity(i);
                }
            }
        });
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if ((global.getVHND_flag() == 1) || (global.getNotification_trackflag() == 1)) {
                } else {
                    if (pos > 0) {
                        VillageID = Village.get(
                                spinVillageName.getSelectedItemPosition() - 1)
                                .getVillageID();
                        fillgrid(0, "");

                    } else {
                        VillageID = 0;
                        fillgrid(3, "");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (validate.RetriveSharepreferenceInt("QR") == 1) {
            fillgrid(2, "");
            btn_PregnantWomen.setVisibility(View.INVISIBLE);
            btnaddanc.setVisibility(View.INVISIBLE);
            tbl_search.setVisibility(View.GONE);
            tbl_spinner.setVisibility(View.GONE);
        } else if (global.getVHND_flag() == 1) {
            fillgrid(2, "");
            btn_PregnantWomen.setVisibility(View.INVISIBLE);
            btnaddanc.setVisibility(View.INVISIBLE);
            tbl_search.setVisibility(View.GONE);
            tbl_spinner.setVisibility(View.GONE);
        } else if (global.getNotification_trackflag() == 1) {
            fillgrid(2, "");
            btn_PregnantWomen.setVisibility(View.INVISIBLE);
            btnaddanc.setVisibility(View.INVISIBLE);
            tbl_search.setVisibility(View.GONE);
            tbl_spinner.setVisibility(View.GONE);
        } else {
            fillgrid(0, "");
            btn_PregnantWomen.setVisibility(View.VISIBLE);
            btnaddanc.setVisibility(View.VISIBLE);
            tbl_search.setVisibility(View.VISIBLE);
            tbl_spinner.setVisibility(View.VISIBLE);
        }

    }

    private void fillVillageName(int Language) {
        Village = dataProvider.getMstVillageName(global.getsGlobalAshaCode(), Language, 0);

        String sValue[] = new String[Village.size() + 1];
        sValue[0] = getResources().getString(R.string.select);

        for (int i = 0; i < Village.size(); i++) {
            sValue[i + 1] = Village.get(i).getVillageName();
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sValue);
        spinVillageName.setAdapter(adapter);
    }


    public void fillgrid(int flag, String pwguid) {
        // TODO Auto-generated method stub
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Integer.valueOf(global.getsGlobalAshaCode());
        }
        if (flag == 0) {
            pregnant = dataProvider.getPregnantWomendataanc("", 5, ashaid, VillageID);
        } else if (flag == 1) {
            String name = etFamilyIDSearch.getText().toString();
            pregnant = dataProvider.getPregnantWomendataanc(name, 4, ashaid, VillageID);
        } else if (flag == 2) {
            String name = global.getsGlobalPWGUID();
            pregnant = dataProvider.getPregnantWomendataanc(name, 7, ashaid, VillageID);
        } else if (flag == 3) {
            pregnant = dataProvider.getPregnantWomendataanc("", 8, ashaid, VillageID);
        } else if (flag == 4) {
            pregnant = dataProvider.getPregnantWomendataanc(pwguid, 7, ashaid, VillageID);
        }

        if (pregnant != null && pregnant.size() > 0) {
            android.view.ViewGroup.LayoutParams params = gridanc
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * (pregnant.size() + 4);
            params.height = gridHeight1;
            // params.height = LayoutParams.WRAP_CONTENT;
            gridanc.setLayoutParams(params);

            totalcount.setText(getResources().getString(
                    R.string.totalcountPwomen)
                    + "-" + pregnant.size());
            gridanc.setAdapter(new AncAdapter(this, pregnant));

        } else {
            gridanc.setAdapter(new AncAdapter(this, pregnant));
        }
    }

    public void openExpendableGridView(int position) {

        int size = gridanc.getChildCount();
        for (int i = 0; i < size; i++) {
            ViewGroup gridchild = (ViewGroup) gridanc.getChildAt(i);
            final TextView tvGridhide = (TextView) gridchild
                    .findViewById(R.id.tvGridhide);
            final GridView gridVisits = (GridView) gridchild
                    .findViewById(R.id.gridVisits);

            if (position != i) {
                gridVisits.setVisibility(View.GONE);
                tvGridhide.setText("0");
            }

        }

    }

    private static AlertDialog showDialog(final Activity act,
                                          CharSequence title, CharSequence message, CharSequence buttonYes,
                                          CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse("market://search?q=pname:"
                                + "com.google.zxing.client.android");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            act.startActivity(intent);
                        } catch (ActivityNotFoundException anfe) {

                        }
                    }
                });
        downloadDialog.setNegativeButton(buttonNo,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return downloadDialog.show();
    }

    // on ActivityResult method
    @SuppressWarnings("unused")
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // get the extras that are returned from the intent

                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");


                show(contents);
            }
        }
    }

    private void show(String XMLData) {
        String PWGUID = "";
        try {
            JSONObject jsonObj = new JSONObject(XMLData);
            JSONArray data = jsonObj.getJSONArray("data");

            if (data != null && data.length() > 0) {
                JSONObject Form = data.getJSONObject(0);
                PWGUID = Form.optString("PWGUID");
                String sql = "";
                sql = "Select count(*) from tblPregnant_woman  where PWGUID='"
                        + PWGUID + "' and  IsPregnant=1  ";
                int count = dataProvider.getMaxRecord(sql);
                if (count > 0) {
                    fillgrid(4, PWGUID);
                } else {
                    Toast.makeText(this, R.string.ancrecorddoesnotexist, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, R.string.QRCodeisinvalid, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(this, R.string.QRCodeisinvalid, Toast.LENGTH_LONG).show();

        }

    }

    public void onBackPressed() {// TODO Auto-generated method stub
        super.onBackPressed();

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getAnc_GUID(), endTime);
        if (validate.RetriveSharepreferenceInt("QR") == 1) {
            validate.SaveSharepreferenceInt("QR", 0);
            global.setsGlobalPWGUID("");
            Intent i = new Intent(AncActivity.this,
                    Dashboard_Activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(i);
        } else {
            if (global.getVHND_flag() == 1) {
                global.setVHND_flag(0);
                global.setsGlobalPWGUID("");
                Intent i = new Intent(AncActivity.this, VHND_DueList_new.class);
                finish();

                startActivity(i);
            } else if (global.getNotification_trackflag() == 1) {
                global.setsGlobalPWGUID("");
                global.setNotification_trackflag(0);

                Intent i = new Intent(AncActivity.this, Dashboard_Activity.class);
                finish();
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } else {
                Intent i = new Intent(AncActivity.this, MCH_Dashboard.class);
                finish();
                startActivity(i);
            }
        }
    }
}