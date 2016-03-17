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
	 * ������ݿⲻ���ڣ��ͻ���á����ھͲ����á�
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// �����ݿ�
		db.execSQL("create table if not exists t_record(_id integer primary key autoincrement,_type integer,_num integer,_who text,_desc text,_date text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * ����
	 */
	public int insert(String table, ContentValues contentValues) {
		// TODO Auto-generated method stub

		// ��
		SQLiteDatabase db = getWritableDatabase();

		// ����
		int resultId = (int) db.insert(table, null, contentValues);

		// �ر�
		db.close();

		return resultId;
	}

	/**
	 * �޸�
	 */
	public void update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub

		// ��
		SQLiteDatabase db = getWritableDatabase();

		// ����
		db.update(table, contentValues, whereClause, whereArgs);

		// �ر�
		db.close();

	}

	/**
	 * ɾ��
	 */
	public void delete(String table, String whereClause, String[] whereArgs) {
		// TODO Auto-generated method stub

		// ��
		SQLiteDatabase db = getWritableDatabase();

		// ����
		db.delete(table, whereClause, whereArgs);

		// �ر�
		db.close();

	}
}
