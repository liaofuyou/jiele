package me.fuyou.qianle.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Sqlhelper extends SQLiteOpenHelper {

	Context mContext;

	public Sqlhelper(Context context, String name) {
		super(context, name, null, 1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 如果数据库不存在，就会调用。存在就不调用。
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// 打开数据库
		db.execSQL("create table if not exists t_record(_id integer primary key autoincrement,_type integer,_num integer,_who text,_desc text,_date text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * 插入
	 */
	public int insert(String table, ContentValues contentValues) {
		// TODO Auto-generated method stub

		// 打开
		SQLiteDatabase db = getWritableDatabase();

		// 操作
		int resultId = (int) db.insert(table, null, contentValues);

		// 关闭
		db.close();

		return resultId;
	}

	/**
	 * 修改
	 */
	public void update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub

		// 打开
		SQLiteDatabase db = getWritableDatabase();

		// 操作
		db.update(table, contentValues, whereClause, whereArgs);

		// 关闭
		db.close();

	}

	/**
	 * 删除
	 */
	public void delete(String table, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub

		// 打开
		SQLiteDatabase db = getWritableDatabase();

		// 操作
		db.delete(table, whereClause, whereArgs);

		// 关闭
		db.close();

	}
}
