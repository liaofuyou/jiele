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
 * ��ӵ�һ��
 * 
 * @author Daniel
 * 
 */
public class AddOneActivity extends Activity {

	/*
	 * ��ȡ���
	 */
	private GridView keybordsGv;
	private TextView backTv;
	private TextView showNumTv;
	private Button goBtn1;
	private Button goBtn2;
	private Button delBtn;

	/*
	 * ��ǰ���������
	 */
	private String currNum = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_one);

		// ��סRecord
		if (Grobal.isupdate == false) {

			// �������Ӳ�����ʵ����һ���µ�Record����

			Grobal.record = new Record();
			currNum = "0";
		} else {

			// ������޸Ĳ�����Grobal.record����MainActivityʵ������

			currNum = Grobal.record.getNum() + "";
			if (currNum.endsWith(".0")) {

				currNum = currNum.substring(0, currNum.length() - 2);
			}
		}

		/*
		 * ��ʼ�����
		 */

		// �����ť
		NextClickListener nextClickListener = new NextClickListener();
		goBtn1 = (Button) findViewById(R.id.aoGo1);
		goBtn1.setOnClickListener(nextClickListener);

		// Ƿ�˰�ť
		goBtn2 = (Button) findViewById(R.id.aoGo2);
		goBtn2.setOnClickListener(nextClickListener);

		// ����
		keybordsGv = (GridView) findViewById(R.id.aoKeybordsGv);
		keybordsGv.setAdapter(new KeybordsAdapter());

		// ����
		backTv = (TextView) findViewById(R.id.aoBackTv);
		backTv.setOnClickListener(new BackClickListener());

		// ��ʾ����
		showNumTv = (TextView) findViewById(R.id.aoShowNumTv);
		showNumTv.setText("��" + currNum);

		// �������
		delBtn = (Button) findViewById(R.id.aoDelBtn);
		delBtn.setOnClickListener(new DelClickListener());

		// ��סActivity
		Grobal.ADDONE = this;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		/*
		 * ����"���ؼ�"
		 */
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			// ����ŵ�Record�ÿա���ԭisupdate
			Grobal.record = null;
			Grobal.isupdate = false;

		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * ���������������
	 * 
	 * @author Daniel
	 * 
	 */
	private class KeybordsAdapter extends BaseAdapter {

		/*
		 * ����
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
	 * ������������Ƿ�ˡ���ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class NextClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			if (((Button) view).getText().equals("���")) {

				Grobal.record.setType(1);
			} else {

				Grobal.record.setType(2);
			}

			// ����Ǯ
			Grobal.record.setNum(Double.parseDouble(currNum));

			/*
			 * ������ӽ��档
			 */
			startActivity(new Intent(AddOneActivity.this, AddTwoActivity.class));
		}

	}

	/**
	 * ��������ء���ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class BackClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// ����ŵ�Record�ÿա���ԭisupdate
			Grobal.record = null;
			Grobal.isupdate = false;

			// �ر�
			AddOneActivity.this.finish();
		}

	}

	/**
	 * ���̵���¼�
	 * 
	 * @author Daniel
	 * 
	 */
	private class KeyClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// �������С���㱣��λ��,�Ͳ��������ˡ�
			if (currNum.indexOf(".") != -1 && currNum.substring(currNum.indexOf(".")).length() == 3) {

				return;
			}

			// ȡ�����������
			String value = ((Button) view).getText().toString();

			if (currNum.equals("0")) {

				/*
				 * ��һ�ΰ���
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
				 * ���ǵ�һ�ΰ�����
				 */

				if (value.equals(".")) {

					// ����Ѿ������С�����ˡ�
					if (currNum.indexOf(".") != -1) {

						return;
					}
				}

				// ���볤�����ơ�
				if (currNum.indexOf(".") == -1 && value.equals(".") == false && currNum.length() > 9) {

					return;
				}

				currNum += value;
			}

			// ��ʾ
			showNumTv.setText("��" + currNum);
		}
	}

	/**
	 * �����del����ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class DelClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// ����Ѿ���"0"�ˣ��Ͳ����˸��ˡ�
			if (currNum.equals("0")) {

				return;
			}
			// �˸�
			if (currNum.length() > 1) {

				currNum = currNum.substring(0, currNum.length() - 1);
			} else {

				currNum = "0";
			}

			// ��ʾ
			showNumTv.setText("��" + currNum);
		}

	}

	public void toast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
