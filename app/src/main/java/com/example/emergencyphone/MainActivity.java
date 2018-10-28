package com.example.emergencyphone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.emergencyphone.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper helper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                Cursor c = db.query("phone", null, null, null, null, null, null);

                c.moveToNext();
                String title = c.getString(c.getColumnIndex("title"));
                String number = c.getString(c.getColumnIndex("number"));
                Log.i("abc", "Title: " + title + ", Number: " + number);

                c.moveToNext();
                title = c.getString(c.getColumnIndex("title"));
                number = c.getString(c.getColumnIndex("number"));
                Log.i("abc", "Title: " + title + ", Number: " + number);

                c.close();
            }
        });
    }
}
