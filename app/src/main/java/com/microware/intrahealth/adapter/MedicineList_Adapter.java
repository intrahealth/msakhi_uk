package com.microware.intrahealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.microware.intrahealth.DirectSearch;
import com.microware.intrahealth.Global;
import com.microware.intrahealth.R;
import com.microware.intrahealth.Validate;
import com.microware.intrahealth.dataprovider.DataProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint({"InflateParams", "SimpleDateFormat"})
public class MedicineList_Adapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> data = null;

    Global global;
    DataProvider dataProvider;
    TextView txt;
    CheckBox chk_Medicine;
    EditText edit_value;
    // TableRow GridRow;

    public MedicineList_Adapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;

    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @SuppressWarnings({"static-access", "unused"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View gridview = null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            gridview = new View(context);
            gridview = layoutInflater.inflate(R.layout.adapterother_medicine, null);
        } else {
            gridview = convertView;
        }

        //  GridRow = (TableRow) gridview.findViewById(R.id.GridRow);
        chk_Medicine = (CheckBox) gridview.findViewById(R.id.chk_Medicine);
        txt = (TextView) gridview.findViewById(R.id.txt);
        edit_value = (EditText) gridview.findViewById(R.id.edit_value);
        chk_Medicine.setText(data.get(position).get("Value"));
        txt.setText(data.get(position).get("ID"));


        return gridview;
    }


}
