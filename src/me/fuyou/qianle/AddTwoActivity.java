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
	 * ��ȡ���
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
		 * ��ʼ�����
		 */

		// ��ȡ���������
		Record record = Grobal.record;

		numTv = (TextView) findViewById(R.id.atNumTv);
		numTv.setText("��" + Utills.decimalFormat(record.getNum()));
		// ������֣�Ҳ�˻ص���һ����
		numTv.setOnClickListener(new BackClickListener());

		// �Ӹ�˭?
		whoEt = (EditText) findViewById(R.id.atWhoEt);
		whoEt.setText(record.getWho());

		// ��ע
		descEt = (EditText) findViewById(R.id.atDescEt);
		descEt.setText(record.getDesc());

		// ��ɰ�ť
		doneBtn = (Button) findViewById(R.id.atDoneBtn);
		doneBtn.setOnClickListener(new DoneClickListener());

		// ���ذ�ť
		backTv = (TextView) findViewById(R.id.atBackTv);
		backTv.setOnClickListener(new BackClickListener());

		if (record.getType() == 2) {

			numTv.setTextColor(Color.rgb(253, 75, 61));
			whoEt.setHint("��˭��?");
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		/*
		 * ����"���ؼ�"
		 */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			// ��ȡ���������
			Grobal.record.setWho(whoEt.getText().toString());
			Grobal.record.setDesc(descEt.getText().toString());

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ������������Ƿ�ˡ���ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class DoneClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// ��ȡ���������
			Grobal.record.setWho(whoEt.getText().toString());
			Grobal.record.setDesc(descEt.getText().toString());
			Grobal.record.setDate(Utills.getToday());

			if (Grobal.isupdate == false) {

				// �������Ӳ���

				// �������ݿ�
				recordService.add(Grobal.record);

				Grobal.records.add(0, Grobal.record);

			} else {

				// ������޸Ĳ���,���滻����

				for (int i = 0; i < Grobal.records.size(); i++) {

					if (Grobal.records.get(i).getId() == Grobal.record.getId()) {

						// �������ݿ�
						recordService.update(Grobal.record);

						Grobal.records.set(i, Grobal.record);

						break;
					}
				}

			}

			Grobal.record = null;
			Grobal.isupdate = false;

			/*
			 * �ر����Activity
			 */
			if (Grobal.ADDONE != null) {

				Grobal.ADDONE.finish();
				Grobal.ADDONE = null;
			}
			AddTwoActivity.this.finish();
		}
	}

	/**
	 * �������һ������ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class BackClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// ��ȡ���������
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
