package com.cui.mymineclearance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

@SuppressLint("all")
class DBMan {
    public static void main(String[] args) {
      DBMan.initDb();
    }

    public static  void  initDb() {
        Success_Fail_Scene success_fail_scene = new Success_Fail_Scene(context);
        db = success_fail_scene.getWritableDatabase();
    }
    public static SQLiteDatabase db;
    static Context context;

    public static ArrayList<RankBean> getListByDifficultId(int difficultId) {
        ArrayList<RankBean> rankBeans = new ArrayList<>();
        String sql = "select * from where difficultId = " +difficultId+" order by 0+timeUse asc" ;
        Cursor cursor = db.rawQuery(sql, null);
        int sum = 0;
        while (cursor.moveToNext()) {
            sum++;
//            String fljsdf = cursor.getString(cursor.getColumnIndex("fljsdf"));
//            String difficultId1 = cursor.getString(cursor.getColumnIndex("difficultId"));
            String User = cursor.getString(cursor.getColumnIndex("User"));
            String UseTime = cursor.getString(cursor.getColumnIndex("UseTime"));
            RankBean rankBean = new RankBean(sum+"", User, UseTime);

            rankBeans.add(rankBean);

        }
        return rankBeans;
    }


    public static void insert(RankBean bean) {
        ContentValues values = new ContentValues();
        values.put("difficultId", bean.getDifficult());
        values.put("User", bean.getUser());
        values.put("UseTime", bean.getYs());
        String difficult = bean.getDifficult();
        String user = bean.getUser();
        String ys = bean.getYs();
//       insert into table1(field1,field2) values(value1,value2)
        String sql = "insert into ranktb(difficultId,User,UseTime) values(" + difficult + "," + user + "," + ys + ")";
        db.insert("ranktb", null, values);
        db.execSQL(sql);
    }

}

public class Success_Fail_Scene extends SQLiteOpenHelper {
    public Success_Fail_Scene(@Nullable Context context) {
        super(context, "RankDataBase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table ranktb(id integer primary key autoincrement,difficultId integer,User varchar(10),UseTime varchar(10))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}