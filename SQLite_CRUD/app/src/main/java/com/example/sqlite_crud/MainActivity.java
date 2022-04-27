package com.example.sqlite_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView);
    }

    public void selectData(View view) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "select * from FruitTable";

        Cursor c = db.rawQuery(sql, null);
        text1.setText("데이터베이스 검색 결과\n\n");
        while(c.moveToNext()) {
            int idx_position = c.getColumnIndex("idx");
            int name_position = c.getColumnIndex("name");
            int price_position = c.getColumnIndex("price");
            int soldAt_position = c.getColumnIndex("soldAt");

            int idx = c.getInt(idx_position);
            String name = c.getString(name_position);
            int price = c.getInt(price_position);
            String soldAt = c.getString(soldAt_position);

            text1.append("idx : " + idx + "\n");
            text1.append("name : " + name + "\n");
            text1.append("price : " + price + "\n");
            text1.append("soldAt : " + soldAt + "\n\n");
        }

        db.close();
    }

    public void insertData(View view) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "insert into FruitTable (name, price, soldAt) values (?, ?, ?)";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String date = sdf.format(new Date());

        String [] arg1 = {"Apple", "24", date};
        String [] arg2 = {"Banana", "8", date};
        String [] arg3 = {"Cherry", "52", date};

        db.execSQL(sql, arg1);
        db.execSQL(sql, arg2);
        db.execSQL(sql, arg3);

        db.close();
    }

    public void updateData(View view) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "update FruitTable set price = ? where idx = ?";
        String [] args = {"12", "1"};

        db.execSQL(sql, args);

        db.close();
    }

    public void deleteData(View view) {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "delete from FruitTable";

        db.execSQL(sql);

        db.close();
    }
}