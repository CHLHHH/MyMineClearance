package com.cui.mymineclearance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHL1963
 * @version 1.0
 * @description: TODO
 * @date 2022/5/11 19:20
 */
@SuppressLint("all")
public class DBManager {
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
}
