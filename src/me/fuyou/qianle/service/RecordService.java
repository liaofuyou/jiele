package me.fuyou.qianle.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import me.fuyou.qianle.entity.Record;
import me.fuyou.qianle.sql.Sqlhelper;
import me.fuyou.qianle.utils.Grobal;

public class RecordService {

	Sqlhelper sh;
	String table = "t_record";

	public RecordService(Context context) {
		// TODO Auto-generated constructor stub

		// ���ݿ������
		sh = new Sqlhelper(context, Grobal.DB_NAME);

	}

	/**
	 * ���
	 */
	public void add(Record record) {
		// TODO Auto-generated method stub

		ContentValues contentValues = new ContentValues();

		contentValues.put("_type", record.getType());
		contentValues.put("_num", record.getNum());
		contentValues.put("_who", record.getWho());
		contentValues.put("_desc", record.getDesc());
		contentValues.put("_date", record.getDate());

		// ����
		int id = sh.insert(table, contentValues);

		// ����ID��
		record.setId(id);
	}

	/**
	 * �޸�
	 */
	public void update(Record record) {
		// TODO Auto-generated method stub

		ContentValues contentValues = new ContentValues();

		contentValues.put("_type", record.getType());
		contentValues.put("_num", record.getNum());
		contentValues.put("_who", record.getWho());
		contentValues.put("_desc", record.getDesc());
		contentValues.put("_date", record.getDate());

		sh.update(table, contentValues, "_id=?", new String[] { record.getId() + "" });
	}

	/*
	 * ɾ��
	 */
	public void delete(int id) {
		// TODO Auto-generated method stub

		sh.delete(table, "_id=?", new String[] { id + "" });
	}

	/**
	 * �����б�
	 */
	public List<Record> findList() {

		// ����
		SQLiteDatabase db = sh.getReadableDatabase();

		// ��ѯ���У�û������ʲô��û�С�
		Cursor cursor = db.query(table, null, null, null, null, null, "_id desc");

		List<Record> records = new ArrayList<Record>();

		while (cursor.moveToNext()) {

			// �������
			Record record = new Record();

			record.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			record.setType(cursor.getInt(cursor.getColumnIndex("_type")));
			record.setNum(cursor.getInt(cursor.getColumnIndex("_num")));
			record.setWho(cursor.getString(cursor.getColumnIndex("_who")));
			record.setDesc(cursor.getString(cursor.getColumnIndex("_desc")));
			record.setDate(cursor.getString(cursor.getColumnIndex("_date")));

			records.add(record);
		}

		// �ر�
		db.close();

		return records;
	}

}
