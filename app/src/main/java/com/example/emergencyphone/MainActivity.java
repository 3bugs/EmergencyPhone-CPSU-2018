package com.example.emergencyphone;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emergencyphone.db.DatabaseHelper;
import com.example.emergencyphone.model.PhoneItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.emergencyphone.db.DatabaseHelper.COL_ID;
import static com.example.emergencyphone.db.DatabaseHelper.COL_IMAGE;
import static com.example.emergencyphone.db.DatabaseHelper.COL_NUMBER;
import static com.example.emergencyphone.db.DatabaseHelper.COL_TITLE;
import static com.example.emergencyphone.db.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<PhoneItem> mPhoneItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new DatabaseHelper(MainActivity.this);
        mDb = mHelper.getWritableDatabase();

        Button addPhoneItemButton = findViewById(R.id.add_phone_item_button);
        addPhoneItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhoneItemActivity.class);
                startActivity(intent);
            }
        });

        Button editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(COL_TITLE, "เหตุด่วน เหตุร้าย");
                cv.put(COL_NUMBER, "191");

                mDb.update(
                        TABLE_NAME,
                        cv,
                        COL_NUMBER + " = ?",
                        new String[]{"99999"}
                );

                loadPhoneData();
                setupListView();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPhoneData();
        setupListView();
    }

    private void loadPhoneData() {
        Cursor c = mDb.query(TABLE_NAME, null, null, null, null, null, null);

        mPhoneItemList = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(COL_ID));
            String title = c.getString(c.getColumnIndex(COL_TITLE));
            String number = c.getString(c.getColumnIndex(COL_NUMBER));
            String image = c.getString(c.getColumnIndex(COL_IMAGE));

            PhoneItem item = new PhoneItem(id, title, number, image);
            mPhoneItemList.add(item);
        }
        c.close();
    }

    private void setupListView() {
        ArrayAdapter<PhoneItem> adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                mPhoneItemList
        );
        ListView lv = findViewById(R.id.result_list_view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PhoneItem item = mPhoneItemList.get(position);

                Toast t = Toast.makeText(MainActivity.this, item.number, Toast.LENGTH_SHORT);
                t.show();

                Intent intent = new Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + item.number)
                );
                startActivity(intent);

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                PhoneItem item = mPhoneItemList.get(position);
                // _id ของ item ในลิสต์ที่ถูกแตะค้าง
                long id = item._id;

                Intent intent = new Intent(MainActivity.this, AddPhoneItemActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);

                // แก้ไขเบอร์โทรเป็น 9999999999
                ContentValues cv = new ContentValues();
                cv.put(COL_NUMBER, "9999999999");

                mDb.update(
                        TABLE_NAME,
                        cv,
                        COL_ID + " = ?", // เงื่อนไขของแถวใน table ที่จะแก้ไขข้อมูล ก็คือแถวที่สัมพันธ์กับ item ในลิสต์ที่ถูกแตะค้าง
                        new String[]{String.valueOf(id)}
                );

                loadPhoneData();
                setupListView();

                return true;
            }
        });
    }
}
