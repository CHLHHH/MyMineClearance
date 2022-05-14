package com.cui.mymineclearance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 19:03
 */
@SuppressLint("all")
public class DBOpenHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;

    /**
     * 初始化数据库
     *
     * @return void
     * @description: TODO     哈哈哈哈哈
     * @Param [context]
     */
    public static void initDB(Context context) {
        DBOpenHelper dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();// 得到数据库对象
    }

    public static void insertItemToAccounttb(RankBean bean) {
        ContentValues values = new ContentValues();
        values.put("difficutId", bean.getDifficult());
        values.put("user", bean.getUser());
        values.put("timeUse", bean.getYs());
        db.insert("ranktb", null, values);

    }
    public static ArrayList<RankBean> getRankList(int difficutId) {
        ArrayList<RankBean> list = new ArrayList<>();
        String sql = "select * from ranktb where difficutId = " + difficutId + " order by 0+timeUse asc";
        Cursor cursor = db.rawQuery(sql, null);// 没有占位符
//循环的读取游标内容,存贮到对象中
        int sum = 0;
        while (cursor.moveToNext()) {
            sum++;
             String difficutI = cursor.getString(cursor.getColumnIndex("difficutId"));
            String user = cursor.getString(cursor.getColumnIndex("user"));
            String timeUse = cursor.getString(cursor.getColumnIndex("timeUse"));
            RankBean rankBean = new RankBean(sum + "", user, timeUse);
            list.add(rankBean);
            Log.d("adffa", "getRankList: " + timeUse);
        }
        return list;
    }

    public DBOpenHelper(@Nullable Context context) {
        super(context, "rankDataBase.db", null, 2);
    }

    // 项目第一次运行会调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table ranktb(id integer primary key autoincrement,difficutId integer,user varchar(10),timeUse varchar(10))";
        db.execSQL(sql);
//        insertRank(db);
    }

//    //  向ranktb表中插入元素
//    private void insertRank(SQLiteDatabase db) {
//        String sql = "insert into ranktb (rank,user,timeUse) values (?,?,?)";
//        db.execSQL(sql,new Object[]{"","",""});
//    }

    //  数据库版本更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
