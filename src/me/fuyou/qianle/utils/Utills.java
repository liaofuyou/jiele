package me.fuyou.qianle.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utills {

	/**
	 * ȡ�õ�ǰ���ڡ�
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

		// 1��=86400000����
		date.setTime(date.getTime() - 86400000);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(date);
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDay(String date) {

		if (date.equals(getToday())) {
			
			return "����";
		} else if (date.equals(getYesterday())) {
			
			return "����";
		} else {
			
			return date;
		}
	}

	/**
	 * ��ʽ������
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

			// ���û��С����
			decimalFormat = new DecimalFormat("#,##0");
		} else if (currNum.endsWith(".")) {

			// �����������С����
			decimalFormat = new DecimalFormat("#,##0.");
		} else if (currNum.substring(currNum.indexOf(".")).length() == 2) {

			// С�������һλ
			decimalFormat = new DecimalFormat("#,##0.0");
		} else {

			// С������ж�λ
			decimalFormat = new DecimalFormat("#,##0.00");
		}

		return decimalFormat.format(Double.parseDouble(currNum));
	}

	public static String decimalFormat3(String text) {

		DecimalFormat decimalFormat = null;

		if (text.endsWith(".0")) {

			// ���û��С����
			decimalFormat = new DecimalFormat("#,##0");
		} else {

			// �����С����
			decimalFormat = new DecimalFormat("#,##0.00");
		}

		return decimalFormat.format(Double.parseDouble(text));
	}
}
