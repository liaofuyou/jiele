package me.fuyou.qianle.utils;

import java.util.List;

import me.fuyou.qianle.entity.Record;
import android.app.Activity;

public class Grobal {

	public static Activity ADDONE = null;
	public static Activity ADDTWO = null;
	public static Record record = null;
	public static List<Record> records = null;

	/*
	 * 是否是修改操作？
	 */
	public static boolean isupdate = false;

	/*
	 * 数据库名
	 */
	public static String DB_NAME = "qianle.sql";
}
