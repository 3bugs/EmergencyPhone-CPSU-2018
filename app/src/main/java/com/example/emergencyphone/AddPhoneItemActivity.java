package com.example.emergencyphone;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.emergencyphone.db.DatabaseHelper;

import static com.example.emergencyphone.db.DatabaseHelper.COL_NUMBER;
import static com.example.emergencyphone.db.DatabaseHelper.COL_TITLE;
import static com.example.emergencyphone.db.DatabaseHelper.TABLE_NAME;

public class AddPhoneItemActivity extends AppCompatActivity {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_item);

        mHelper = new DatabaseHelper(this);
        mDb = mHelper.getWritableDatabase();

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInsertPhoneItem();
            }
        });
    }

    private void doInsertPhoneItem() {
        EditText titleEditText = findViewById(R.id.title_edit_text);
        EditText numberEditText = findViewById(R.id.number_edit_text);

        String title = titleEditText.getText().toString();
        String number = numberEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_NUMBER, number);
        mDb.insert(TABLE_NAME, null, cv);

        finish();
    }
}
