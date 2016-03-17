package me.fuyou.qianle;

import me.fuyou.qianle.entity.Record;
import me.fuyou.qianle.utils.Grobal;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

/**
 * 添加第一步
 * 
 * @author Daniel
 * 
 */
public class AddOneActivity extends Activity {

	/*
	 * 获取组件
	 */
	private GridView keybordsGv;
	private TextView backTv;
	private TextView showNumTv;
	private Button goBtn1;
	private Button goBtn2;
	private Button delBtn;

	/*
	 * 当前输入的数字
	 */
	private String currNum = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_one);

		// 记住Record
		if (Grobal.isupdate == false) {

			// 如果是添加操作，实例化一个新的Record对象；

			Grobal.record = new Record();
			currNum = "0";
		} else {

			// 如果是修改操作，Grobal.record会在MainActivity实例化。

			currNum = Grobal.record.getNum() + "";
			if (currNum.endsWith(".0")) {

				currNum = currNum.substring(0, currNum.length() - 2);
			}
		}

		/*
		 * 初始化组件
		 */

		// 借给按钮
		NextClickListener nextClickListener = new NextClickListener();
		goBtn1 = (Button) findViewById(R.id.aoGo1);
		goBtn1.setOnClickListener(nextClickListener);

		// 欠了按钮
		goBtn2 = (Button) findViewById(R.id.aoGo2);
		goBtn2.setOnClickListener(nextClickListener);

		// 键盘
		keybordsGv = (GridView) findViewById(R.id.aoKeybordsGv);
		keybordsGv.setAdapter(new KeybordsAdapter());

		// 返回
		backTv = (TextView) findViewById(R.id.aoBackTv);
		backTv.setOnClickListener(new BackClickListener());

		// 显示输入
		showNumTv = (TextView) findViewById(R.id.aoShowNumTv);
		showNumTv.setText("￥" + currNum);

		// 输入回退
		delBtn = (Button) findViewById(R.id.aoDelBtn);
		delBtn.setOnClickListener(new DelClickListener());

		// 记住Activity
		Grobal.ADDONE = this;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		/*
		 * 按下"返回键"
		 */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			// 将存放的Record置空。还原isupdate
			Grobal.record = null;
			Grobal.isupdate = false;

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 输入键盘适配器。
	 * 
	 * @author Daniel
	 * 
	 */
	private class KeybordsAdapter extends BaseAdapter {

		/*
		 * 键们
		 */
		String[] keys = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "." };

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return keys.length;
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return keys[index];
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			convertView = View.inflate(AddOneActivity.this, R.layout.item_aokey, null);
			((Button) convertView).setText(keys[position]);
			convertView.setOnClickListener(new KeyClickListener());

			return convertView;
		}

	}

	/**
	 * 点击“借给”或“欠了”按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class NextClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			if (((Button) view).getText().equals("借出")) {

				Grobal.record.setType(1);
			} else {

				Grobal.record.setType(2);
			}

			// 多少钱
			Grobal.record.setNum(Double.parseDouble(currNum));

			/*
			 * 启动添加界面。
			 */
			startActivity(new Intent(AddOneActivity.this, AddTwoActivity.class));
		}

	}

	/**
	 * 点击“返回”按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class BackClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 将存放的Record置空。还原isupdate
			Grobal.record = null;
			Grobal.isupdate = false;

			// 关闭
			AddOneActivity.this.finish();
		}

	}

	/**
	 * 键盘点击事件
	 * 
	 * @author Daniel
	 * 
	 */
	private class KeyClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 如果超过小数点保留位数,就不让输入了。
			if (currNum.indexOf(".") != -1 && currNum.substring(currNum.indexOf(".")).length() == 3) {

				return;
			}

			// 取得输入的内容
			String value = ((Button) view).getText().toString();

			if (currNum.equals("0")) {

				/*
				 * 第一次按下
				 */

				if (value.equals("0")) {

					currNum = "0";
				} else if (value.equals("")) {

					currNum = "0";
				} else if (value.equals(".")) {

					currNum = "0.";
				} else {

					currNum = value;
				}
			} else {

				/*
				 * 不是第一次按下了
				 */

				if (value.equals(".")) {

					// 如果已经输入过小数点了。
					if (currNum.indexOf(".") != -1) {

						return;
					}
				}

				// 输入长度限制。
				if (currNum.indexOf(".") == -1 && value.equals(".") == false && currNum.length() > 9) {

					return;
				}

				currNum += value;
			}

			// 显示
			showNumTv.setText("￥" + currNum);
		}
	}

	/**
	 * 点击“del”按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class DelClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 如果已经是"0"了，就不用退格了。
			if (currNum.equals("0")) {

				return;
			}
			// 退格
			if (currNum.length() > 1) {

				currNum = currNum.substring(0, currNum.length() - 1);
			} else {

				currNum = "0";
			}

			// 显示
			showNumTv.setText("￥" + currNum);
		}

	}

	public void toast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
