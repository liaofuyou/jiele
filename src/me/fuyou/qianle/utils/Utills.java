package me.fuyou.qianle.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utills {

	/**
	 * 取得当前日期。
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getToday() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(new Date());
	}

	@SuppressLint("SimpleDateFormat")
	public static String getYesterday() {
		Date date = new Date();

		// 1天=86400000毫秒
		date.setTime(date.getTime() - 86400000);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDay(String date) {

		if (date.equals(getToday())) {
			
			return "今天";
		} else if (date.equals(getYesterday())) {
			
			return "昨天";
		} else {
			
			return date;
		}
	}

	/**
	 * 格式化数字
	 * 
	 * @param obj
	 * @return
	 */
	public static String decimalFormat(double d) {

		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		return decimalFormat.format(d);
	}

	public static String decimalFormat2(String currNum) {

		DecimalFormat decimalFormat = null;

		if (currNum.indexOf(".") == -1) {

			// 如果没有小数点
			decimalFormat = new DecimalFormat("#,##0");
		} else if (currNum.endsWith(".")) {

			// 如果正好输入小数点
			decimalFormat = new DecimalFormat("#,##0.");
		} else if (currNum.substring(currNum.indexOf(".")).length() == 2) {

			// 小数点后有一位
			decimalFormat = new DecimalFormat("#,##0.0");
		} else {

			// 小数点后有二位
			decimalFormat = new DecimalFormat("#,##0.00");
		}

		return decimalFormat.format(Double.parseDouble(currNum));
	}

	public static String decimalFormat3(String text) {

		DecimalFormat decimalFormat = null;

		if (text.endsWith(".0")) {

			// 如果没有小数点
			decimalFormat = new DecimalFormat("#,##0");
		} else {

			// 如果有小数点
			decimalFormat = new DecimalFormat("#,##0.00");
		}

		return decimalFormat.format(Double.parseDouble(text));
	}
}
