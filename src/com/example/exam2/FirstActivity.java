package com.example.exam2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends Activity {
    Button startButton;
    EditText nameEdit;
    EditText numEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences(Main.PREFERENCES, Context.MODE_PRIVATE);
        Main.REAL_NAME = preferences.getString(Main.NAME, null);
        Main.NUMBER_OF_BOXES = preferences.getInt(Main.BOX_NUMBER, 0);
        final Intent intent = new Intent(this, ListActivity.class);
        DB.getInstance().init(getApplicationContext());
        if (Main.REAL_NAME == null) {
            setContentView(R.layout.main);
            startButton = (Button) findViewById(R.id.button);
            nameEdit = (EditText) findViewById(R.id.editText);
            numEdit = (EditText) findViewById(R.id.editText2);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = nameEdit.getText().toString();
                    String num = numEdit.getText().toString();
                    if (name.length() == 0 || num.length() == 0) {
                        Toast.makeText(getApplicationContext(), R.string.fill_correct, Toast.LENGTH_SHORT);
                    } else {
                        SharedPreferences.Editor editor = preferences.edit();
                        Main.REAL_NAME = name;
                        Main.NUMBER_OF_BOXES = Integer.parseInt(num);
                        editor.putString(Main.NAME, Main.REAL_NAME);
                        editor.putInt(Main.BOX_NUMBER, Main.NUMBER_OF_BOXES);
                        editor.commit();
                        startActivity(intent);
                    }
                }
            });
        } else {
            startActivity(intent);
        }
    }
}