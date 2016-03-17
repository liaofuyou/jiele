package me.fuyou.qianle;

import me.fuyou.qianle.entity.Record;
import me.fuyou.qianle.service.RecordService;
import me.fuyou.qianle.utils.Grobal;
import me.fuyou.qianle.utils.Utills;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;

public class AddTwoActivity extends Activity {

	/*
	 * service
	 */
	RecordService recordService = null;

	/*
	 * 获取组件
	 */
	private Button doneBtn;
	private TextView backTv;
	private EditText whoEt;
	private EditText descEt;
	private TextView numTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_two);

		recordService = new RecordService(this);

		/*
		 * 初始化组件
		 */

		// 获取输入的数据
		Record record = Grobal.record;

		numTv = (TextView) findViewById(R.id.atNumTv);
		numTv.setText("￥" + Utills.decimalFormat(record.getNum()));
		// 点击数字，也退回到上一步。
		numTv.setOnClickListener(new BackClickListener());

		// 接给谁?
		whoEt = (EditText) findViewById(R.id.atWhoEt);
		whoEt.setText(record.getWho());

		// 备注
		descEt = (EditText) findViewById(R.id.atDescEt);
		descEt.setText(record.getDesc());

		// 完成按钮
		doneBtn = (Button) findViewById(R.id.atDoneBtn);
		doneBtn.setOnClickListener(new DoneClickListener());

		// 返回按钮
		backTv = (TextView) findViewById(R.id.atBackTv);
		backTv.setOnClickListener(new BackClickListener());

		if (record.getType() == 2) {

			numTv.setTextColor(Color.rgb(253, 75, 61));
			whoEt.setHint("向谁借?");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		/*
		 * 按下"返回键"
		 */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			// 获取输入的数据
			Grobal.record.setWho(whoEt.getText().toString());
			Grobal.record.setDesc(descEt.getText().toString());

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 点击“借给”或“欠了”按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class DoneClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 获取输入的数据
			Grobal.record.setWho(whoEt.getText().toString());
			Grobal.record.setDesc(descEt.getText().toString());
			Grobal.record.setDate(Utills.getToday());

			if (Grobal.isupdate == false) {

				// 如果是添加操作

				// 更新数据库
				recordService.add(Grobal.record);

				Grobal.records.add(0, Grobal.record);

			} else {

				// 如果是修改操作,则替换过。

				for (int i = 0; i < Grobal.records.size(); i++) {

					if (Grobal.records.get(i).getId() == Grobal.record.getId()) {

						// 更新数据库
						recordService.update(Grobal.record);

						Grobal.records.set(i, Grobal.record);

						break;
					}
				}

			}

			Grobal.record = null;
			Grobal.isupdate = false;

			/*
			 * 关闭添加Activity
			 */
			if (Grobal.ADDONE != null) {

				Grobal.ADDONE.finish();
				Grobal.ADDONE = null;
			}
			AddTwoActivity.this.finish();
		}
	}

	/**
	 * 点击“上一步”按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class BackClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 获取输入的数据
			Record record = Grobal.record;
			record.setWho(whoEt.getText().toString());
			record.setDesc(descEt.getText().toString());

			AddTwoActivity.this.finish();
		}

	}

	public void toast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
