package me.fuyou.qianle.entity;

/**
 * 输入的记录
 * 
 * @author Daniel
 * 
 */
public class Record {

	/**
	 * id
	 */
	private int id;

	/**
	 * 类型：借给:1 欠了：2
	 */
	private int type;

	/**
	 * 多少钱
	 */
	private double num;

	/**
	 * 给谁
	 */
	private String who;
	/**
	 * 备注
	 */
	private String desc;

	/**
	 * 日期
	 */
	private String date;

	public Record() {
		// TODO Auto-generated constructor stub
	}

	public Record(int id, int type, double num, String who, String desc, String date) {
		super();
		this.id = id;
		this.type = type;
		this.num = num;
		this.who = who;
		this.desc = desc;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public String getWho() {
		return who;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", type=" + type + ", num=" + num + ", who=" + who + ", desc=" + desc + ", date=" + date + "]";
	}

}
