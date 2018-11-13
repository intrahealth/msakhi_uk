package com.microware.intrahealth.AF;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.microware.intrahealth.AFAdapter.AFChild_Imunnization_adapter;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.HbncReport;
import com.microware.intrahealth.ImmunizationDashboard;
import com.microware.intrahealth.Immunization_Entry;
import com.microware.intrahealth.ImunizationChildList;
import com.microware.intrahealth.R;
import com.microware.intrahealth.VHND_DueList_new;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.adapter.Child_Imunnization_adapter;
import com.microware.intrahealth.dataprovider.DataProvider;
import com.microware.intrahealth.object.Child_Imunization_Object;
import com.microware.intrahealth.object.MstVillage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aditya on 10/4/2018.
 */

public class AFImunizationChildList extends Activity {
    TextView totalcount;
    GridView ImunizationGrid;
    ArrayList<Child_Imunization_Object> ChildImmun = new ArrayList<Child_Imunization_Object>();
    DataProvider dataProvider;
    Validate validate;
    Spinner spinVillageName;
    ArrayList<MstVillage> Village = new ArrayList<MstVillage>();
    int VillageID = 0;
    ArrayAdapter<String> adapter;
    Global global;
    Button btn_report;
    EditText etFamily;
    ImageView btnCapture;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ImageView Img_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.af_imunazation);
        global = (Global) getApplication();
        dataProvider = new DataProvider(this);
        validate = new Validate(this);
        setTitle(global.getVersionName());
        btn_report = (Button) findViewById(R.id.btn_report);
        Img_info = (ImageView) findViewById(R.id.Img_info);
        ImunizationGrid = (GridView) findViewById(R.id.ImunizationGrid);
        totalcount = (TextView) findViewById(R.id.totalcount);
        spinVillageName = (Spinner) findViewById(R.id.spinVillageName);
        etFamily = (EditText) findViewById(R.id.etFamily);
        btnCapture = (ImageView) findViewById(R.id.btnCapture);
        fillVillageName(global.getLanguage());
        spinVillageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (global.getVHND_flag() == 1) {
                } else {
                    if (pos > 0) {
                        VillageID = Village.get(
                                spinVillageName.getSelectedItemPosition() - 1)
                                .getVillageID();
                        fillgrid(0);

                    } else {
                        VillageID = 0;
                        fillgrid(2);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Img_info.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CustomAlert();
            }
        });
        etFamily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    fillgrid(3);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etFamily.getText().toString().length() == 0) {
                    fillgrid(2);
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
                    showDialog(AFImunizationChildList.this,
                            getResources().getString(R.string.NoScannerFound),
                            getResources().getString(R.string.Downloadscanner),
                            getResources().getString(R.string.Yes),
                            getResources().getString(R.string.no)).show();
                }
            }
        });
        if (global.getVHND_flag() == 1) {
            fillgrid(1);
        } else {
            fillgrid(2);
        }

    }

    public void CustomAlert() {

        // Create custom dialog object
        final Dialog dialog = new Dialog(this);
        // hide to default title for Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // inflate the layout dialog_layout.xml and set it as contentView
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.color_infoalert, null, false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.RIGHT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));


        // Display the dialog
        dialog.show();

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
        String Value1 = "", mothername = "", fathername = "", ChildGUID = "", ChildName = "", DOB = "";
        try {
            JSONObject jsonObj = new JSONObject(XMLData);
            JSONArray data = jsonObj.getJSONArray("data");
            String Value = "";
            if (data != null && data.length() > 0) {
                JSONObject Form = data.getJSONObject(0);
                ChildGUID = Form.optString("ChildGUID");
                String sql = "";
                sql = "Select count(*) from tblChild  where ChildGUID='"
                        + ChildGUID + "'";
                int count = dataProvider.getMaxRecord(sql);
                if (count > 0) {
                    mothername = Form.optString("PWName");
                    fathername = Form.optString("HusbandName");
                    ChildName = Form.optString("child_name");
                    DOB = Form.optString("child_dob");

                    Intent intent = new Intent(AFImunizationChildList.this, Immunization_Entry.class);
                    intent.putExtra("MotherName", mothername);
                    intent.putExtra("HusbandName", fathername);
                    intent.putExtra("ChildGUID", ChildGUID);
                    intent.putExtra("ChildName", ChildName);
                    intent.putExtra("DOB", DOB);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.childdoesnotexist, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, R.string.QRCodeisinvalid, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(this, R.string.QRCodeisinvalid, Toast.LENGTH_LONG).show();

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


    public void onclickButton(View view) {

        Intent in;
        switch (view.getId()) {

            case R.id.btn_report:
                if (global.getiGlobalRoleID() == 2) {
                    Intent i = new Intent(AFImunizationChildList.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 3);
                    finish();
                    startActivity(i);
                } else if (global.getiGlobalRoleID() == 3) {
                    Intent i = new Intent(AFImunizationChildList.this, HbncReport.class);
                    validate.SaveSharepreferenceInt("flag", 3);
                    finish();
                    startActivity(i);
                }
                break;

            default:
                break;
        }
    }

    public void fillgrid(int flag) {
        int ashaid = 0;
        if (global.getsGlobalAshaCode() != null
                && global.getsGlobalAshaCode().length() > 0) {
            ashaid = Validate.returnIntegerValue(global.getsGlobalAshaCode());
        }
        if (flag == 0) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 1, String.valueOf(VillageID));
        } else if (flag == 2) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 3, "");
        } else if (flag == 1) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 2, global.getsGlobalChildGUID());
        } else if (flag == 3) {
            ChildImmun = dataProvider.gettblCHildImmunizationdata(ashaid, 4, etFamily.getText().toString());
        }

        if (ChildImmun != null) {
            android.view.ViewGroup.LayoutParams params = ImunizationGrid
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    60, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * ChildImmun.size();
            params.height = gridHeight1;
            ImunizationGrid.setLayoutParams(params);
            totalcount.setText(getResources().getString(R.string.totalcount)
                    + "-" + ChildImmun.size());
            ImunizationGrid.setAdapter(new AFChild_Imunnization_adapter(this,
                    ChildImmun));
        } else {
            ImunizationGrid.setAdapter(new AFChild_Imunnization_adapter(this,
                    ChildImmun));
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (global.getVHND_flag() == 1) {
            global.setVHND_flag(0);
            global.setsGlobalChildGUID("");
            Intent i = new Intent(AFImunizationChildList.this, VHND_DueList_new.class);
            finish();
            startActivity(i);
        } else {
            Intent i = new Intent(AFImunizationChildList.this, ImmunizationDashboard.class);
            finish();
            startActivity(i);
        }

    }
}
