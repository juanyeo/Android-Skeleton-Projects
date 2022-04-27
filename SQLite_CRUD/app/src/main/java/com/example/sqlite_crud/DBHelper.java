package com.example.sqlite_crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// 데이터 베이스의 이름, 버전을 선택한다.
// 버전 업데이트: onCreate, onUpgrade/case version: 에 새로운 테이블 생성 코드를 넣는다
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Price.db", null, 1);
    }

    // Database 가 생성될 때 처음 1번만 호출. 테이블 생성, 초기 데이터 세팅 등 수행.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table FruitTable( " +
                "idx integer primary key autoincrement, " +
                "name text not null, " +
                "price integer not null, " +
                "soldAt date not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion) {
            case 1:
                // 1에서 2버전 형태로 테이블 구조를 변경시키는 작업을 한다.
            case 2:
                // 2에서 3버전 형태로 테이블 구조를 변경시키는 작업을 한다.
            case 3:
                // 3에서 4버전 형태로 테이블 구조를 변경시키는 작업을 한다.
        }
    }
}
