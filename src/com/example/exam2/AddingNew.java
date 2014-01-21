package com.example.exam2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.exam2.orm.Booking;
import com.j256.ormlite.dao.Dao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Xottab
 * Date: 14.01.14
 */
public class AddingNew extends Activity {
    EditText brand;
    EditText color;
    EditText carNumber;
    EditText phoneNumber;
    Spinner time;
    Button addNew;
    DBHelper helper;
    Dao scheduleDao;
    String[] dataForSpeed;
    int selectedSpeed = 0;
    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.add_new);
        dataForSpeed = getResources().getStringArray(R.array.speedof);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataForSpeed);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setPrompt("Скорость доставки");
        spinner2.setSelection(0);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                selectedSpeed = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                selectedSpeed = 0;
            }
        });



        helper = DB.getInstance().getHelper();
        try {
            scheduleDao = helper.getEntriesDao();
        } catch (SQLException e) {
            Log.e("error", "error", e);
        }
        brand = (EditText) findViewById(R.id.editText3);
        color = (EditText) findViewById(R.id.editText4);
        carNumber = (EditText) findViewById(R.id.editText);
        phoneNumber = (EditText) findViewById(R.id.editText2);
        addNew = (Button) findViewById(R.id.button);
        time = (Spinner) findViewById(R.id.spinner);
        time.setAdapter(new TimeChooseAdapter(this, android.R.layout.simple_spinner_item, new ArrayList<Integer>(Main.freeTime.keySet())));
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer selectedTime = (Integer) time.getSelectedItem();
                for (int i = 0; i <= selectedSpeed; i++)
                {
                    Booking entry = new Booking(
                            brand.getText().toString(),
                            color.getText().toString(),
                            carNumber.getText().toString(),
                            phoneNumber.getText().toString(),
                            selectedTime-i-1,
                            Main.freeTime.get(selectedTime-i-1).remove(0));
                    try {
                        scheduleDao.create(entry);
                    } catch (SQLException e) {
                        Context context = getApplicationContext();
                        CharSequence text = "Выберите допустимое время с учётом доставки";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        //Log.e("error", "error", e);
                    }
                }
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    public class TimeChooseAdapter extends ArrayAdapter<Integer> {

        public TimeChooseAdapter(Context context, int resource, List<Integer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView textView = new TextView(getContext());
            textView.setTextSize(15);
            int time = getItem(position);
            textView.setText(Main.formatTime(time - 1));
            return textView;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getContext());
            int time = getItem(pos);
            textView.setText(Main.formatTime(time - 1));
            return textView;
        }
    }
}