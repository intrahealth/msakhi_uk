package com.microware.intrahealth;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;

import android.widget.GridView;

import com.microware.intrahealth.adapter.Userlistadapter;
import com.microware.intrahealth.dataprovider.DataProvider;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserDetail extends Activity {


    GridView gridUser;
    DataProvider dataProvider;
    Global global;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userdialog);
        dataProvider = new DataProvider(this);
        global = (Global) getApplication();
        setTitle(global.getVersionName());

        gridUser = (GridView) findViewById(R.id.gridUser);

        fillUser(gridUser);


    }

    public void fillUser(GridView grid) {

        String sql = "";
        ArrayList<HashMap<String, String>> data = null;
        if (global.getiGlobalRoleID() == 2) {
            sql = "select MstUser.UserID as UserID,MstUser.is_temp as is_temp,MstASHA.ASHAID as ASHAID,MstASHA.ASHAName as ASHAName,MstUser.UserName as UserName from MstUser inner join userashamapping on userashamapping.UserID= MstUser.UserID inner join MstASHA  on userashamapping.AshaID= MstASHA.ASHAID and MstASHA.LanguageID=" + global.getLanguage() + " ";
        }
        if (global.getiGlobalRoleID() == 3) {
            sql = "select MstUser.UserID as UserID,MstUser.is_temp as is_temp,0 as ASHAID,MstANM.ANMName as ASHAName,MstUser.UserName as UserName from MstUser inner join MstANM where  MstANM.LanguageID=" + global.getLanguage() + " ";

        } if (global.getiGlobalRoleID() == 11) {
            sql = "select MstUser.UserID as UserID,MstUser.is_temp as is_temp,0 as ASHAID,mstchc.CHCName as ASHAName,MstUser.UserName as UserName from MstUser inner join mstchc where  mstchc.LanguageID=" + global.getLanguage() + " ";

        }
        data = dataProvider.getDynamicVal(sql);
        if (data != null && data.size() > 0) {

            android.view.ViewGroup.LayoutParams params = grid
                    .getLayoutParams();
            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    50, r.getDisplayMetrics());
            int hi = Math.round(px);
            int gridHeight1 = 0;
            gridHeight1 = hi * data.size();
            params.height = gridHeight1;
            grid.setLayoutParams(params);
            grid.setAdapter(new Userlistadapter(this, data));
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        SimpleDateFormat date1 = new SimpleDateFormat("HH:mm a");

        String endTime = date1.format(currentLocalTime);
        dataProvider.getUserLoginUpdate(global.getNew_GUID(), endTime);
        Intent i = new Intent(UserDetail.this, Dashboard_Activity.class);
        finish();
        startActivity(i);
    }

}
