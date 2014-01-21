package com.example.exam2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
public class ListActivity extends Activity {
    TextView carWashName;
    LayoutInflater inflater;
    ListView listView;
    Dao scheduleDao;
    Button addNew;
    DBHelper helper;
    private List values;

    @Override
    public void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.list);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        carWashName = (TextView) findViewById(R.id.textView);
        carWashName.setText(Main.REAL_NAME);
        listView = (ListView) findViewById(R.id.list);
        helper = DB.getInstance().getHelper();

        Log.i("numberofboxes", String.valueOf(Main.NUMBER_OF_BOXES));
        for (int i = 0; i < 28; i++) {
            ArrayList<Integer> boxes = new ArrayList<Integer>();
            for (int j = 0; j < Main.NUMBER_OF_BOXES; j++) {
                boxes.add(j);
            }
            Main.freeTime.put(i, boxes);
        }

        try {
            scheduleDao = helper.getEntriesDao();
        } catch (SQLException e) {
            Log.e("error", "error", e);
        }
        try {
            values = scheduleDao.queryBuilder().orderBy("scheduleTime", true).orderBy("boxNumber", true).query();
        } catch (SQLException e) {
            Log.e("error", "error", e);
        }
        for (Object entry : values) {
            Booking entry1 = (Booking) entry;
            Main.freeTime.get(entry1.getScheduleTime()).remove((Integer) entry1.getBoxNumber());
            if (Main.freeTime.get(entry1.getScheduleTime()).size() == 0) {
                Main.freeTime.remove(entry1.getScheduleTime());
            }
        }
        listView.setAdapter(new ScheduleEntryAdapter(this, android.R.layout.simple_list_item_1, values));
        addNew = (Button) findViewById(R.id.button);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddingNew.class);
                startActivity(intent);
            }
        });
    }

    public class ScheduleEntryAdapter extends ArrayAdapter<Booking> {

        public ScheduleEntryAdapter(Context context, int resource, List<Booking> objects) {
            super(context, resource, objects);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            Booking entry = getItem(pos);

            LinearLayout view = (LinearLayout) inflater.inflate(R.layout.entry, null);
            TextView brandName = (TextView) view.findViewById(R.id.textView);
            TextView color = (TextView) view.findViewById(R.id.textView2);
            TextView time = (TextView) view.findViewById(R.id.textView4);
            TextView boxNumber = (TextView) view.findViewById(R.id.textView3);
            brandName.setText(entry.getBrandName());
            color.setText(entry.getColor());
            time.setText(Main.formatTime(entry.getScheduleTime()));
            boxNumber.setText(String.valueOf(entry.getBoxNumber() + 1));
            return view;
        }
    }
}