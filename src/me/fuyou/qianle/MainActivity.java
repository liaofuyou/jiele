package me.fuyou.qianle;

import me.fuyou.qianle.entity.Record;
import me.fuyou.qianle.service.RecordService;
import me.fuyou.qianle.utils.Grobal;
import me.fuyou.qianle.utils.Utills;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

/**
 * ��Activity
 * 
 * @author Daniel
 * 
 */
public class MainActivity extends Activity {
	/*
	 * service
	 */
	RecordService recordService = null;

	/*
	 * ��ȡ���
	 */
	private Button addBtn;
	private ListView recordLv;
	private RecordAdapter recordAdapter;
	private RelativeLayout settingRl;
	private TextView settingAboutTv;
	private ImageView settingTagIv;
	private ImageView nullTishiIv;

	// �Ի���
	AlertDialog.Builder recordOptBuilder;
	Record currOptRecord;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recordService = new RecordService(this);

		/*
		 * ��ʼ�����ݡ�
		 */
		Grobal.records = recordService.findList();

		/*
		 * ��ʼ�����
		 */
		addBtn = (Button) findViewById(R.id.mAddBtn);
		addBtn.setOnClickListener(new AddClickListener());

		// ����
		SettingTagClickListener settingTagClickListener = new SettingTagClickListener();
		SettingClickListener settingClickListener = new SettingClickListener();

		// "..."
		settingTagIv = (ImageView) findViewById(R.id.mSettingIv);
		settingTagIv.setOnClickListener(settingTagClickListener);

		// Ϊ����ʾ
		nullTishiIv = (ImageView) findViewById(R.id.mNullTishiIv);

		// ����app
		settingAboutTv = (TextView) findViewById(R.id.mAboutTv);
		settingAboutTv.setOnClickListener(settingClickListener);

		// ������
		// settingAuthorTv = (TextView) findViewById(R.id.mAuthorTv);
		// settingAuthorTv.setOnClickListener(settingClickListener);

		// ���
		settingRl = (RelativeLayout) findViewById(R.id.mSettingRl);
		settingRl.setOnClickListener(settingTagClickListener);

		// �б�
		recordLv = (ListView) findViewById(R.id.mRecordLv);
		recordAdapter = new RecordAdapter();
		recordLv.setAdapter(recordAdapter);
		recordLv.setOnItemClickListener(new RecordItemClickListener());
		recordLv.setOnItemLongClickListener(new RecordItemLongClickListener());

		// ʵ����Record�����Ի���
		recordOptBuilder = new AlertDialog.Builder(this).setItems(new String[] { "�༭", "ɾ��" }, new RecordDialogListener());

		// andorid �е㷴���ࡣ�����������ĺܺã�����ȴ����
		// View optView = View.inflate(this, R.layout.opt_record, null);
		// recordOptBuilder = new
		// AlertDialog.Builder(this,R.style.MyDialogTheme).setView(optView);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		recordAdapter.notifyDataSetChanged();
		recordLv.setAdapter(recordAdapter);

		// ���û�м�¼�ˡ���ʾһ�¡�
		if (Grobal.records.size() == 0) {

			nullTishiIv.setVisibility(View.VISIBLE);
		} else {

			nullTishiIv.setVisibility(View.GONE);
		}
	}

	/**
	 * ���ĳ����¼��ֱ�ӽ����޸�ҳ��
	 * 
	 * @author Administrator
	 * 
	 */
	private class RecordItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int possion, long id) {
			// TODO Auto-generated method stub

			// ��¼��Ҫ�޸ĵļ�¼
			currOptRecord = Grobal.records.get(possion);

			// ��¡Ҫ�޸ĵļ�¼��sorry�����Ƚϱ���
			Grobal.isupdate = true;
			Grobal.record = new Record(currOptRecord.getId(), currOptRecord.getType(), currOptRecord.getNum(), currOptRecord.getWho(), currOptRecord.getDesc(), currOptRecord.getDate());
			currOptRecord = null;

			/*
			 * ������ӽ��档���޸Ĳ�����
			 */
			startActivity(new Intent(MainActivity.this, AddOneActivity.class));

		}

	}

	/**
	 * ������¼�����������Ի���
	 * 
	 * @author Daniel
	 * 
	 */
	private class RecordItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int possion, long id) {
			// TODO Auto-generated method stub

			// ��¼��Ҫ�޸ĵļ�¼
			currOptRecord = Grobal.records.get(possion);

			// �����Ի���
			recordOptBuilder.show();

			return false;
		}

	}

	/**
	 * �Ի���ѡ�������
	 * 
	 * @author Administrator
	 * 
	 */
	private class RecordDialogListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

			if (which == 0) {

				// ��¡Ҫ�޸ĵļ�¼��sorry�����Ƚϱ���
				Grobal.isupdate = true;
				Grobal.record = new Record(currOptRecord.getId(), currOptRecord.getType(), currOptRecord.getNum(), currOptRecord.getWho(), currOptRecord.getDesc(), currOptRecord.getDate());
				currOptRecord = null;

				/*
				 * ������ӽ��档���޸Ĳ�����
				 */
				startActivity(new Intent(MainActivity.this, AddOneActivity.class));

			} else {

				// �޸����ݿ�
				recordService.delete(currOptRecord.getId());

				// �Ƴ���¼��ˢ����ʾ
				Grobal.records.remove(currOptRecord);
				onResume();
				currOptRecord = null;

				// ��ʾ�ɹ�
				toast("ɾ���ɹ�");
			}
		}
	}

	/**
	 * �����Ӱ�ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class AddClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// ��ʾΪ���
			Grobal.isupdate = false;

			/*
			 * ������ӽ��档
			 */
			startActivity(new Intent(MainActivity.this, AddOneActivity.class));
		}

	}

	/**
	 * ������������ߡ�������App����ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class SettingClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			if (view.getId() == settingAboutTv.getId()) {

				startActivity(new Intent(MainActivity.this, AboutActivity.class));
			} else {

				startActivity(new Intent(MainActivity.this, AuthorActivity.class));
			}

			settingRl.setVisibility(View.GONE);
		}

	}

	/**
	 * �����...����ťʱ�����������¼���
	 * 
	 * @author Daniel
	 * 
	 */
	private class SettingTagClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if (settingRl.getVisibility() == View.VISIBLE) {

				settingRl.setVisibility(View.GONE);
			} else {

				settingRl.setVisibility(View.VISIBLE);
			}
		}
	}

	/*
	 * �б�������
	 */
	private class RecordAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Grobal.records.size();
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return Grobal.records.get(index);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {

				Record record = Grobal.records.get(position);

				convertView = View.inflate(MainActivity.this, R.layout.item_record, null);

				// ��˭��
				TextView whoTv = (TextView) convertView.findViewById(R.id.mRecordWho);

				// �۸�
				TextView numTv = (TextView) convertView.findViewById(R.id.mRecordNum);
				numTv.setText("��" + Utills.decimalFormat3(record.getNum() + ""));

				// ��ע
				TextView descTv = (TextView) convertView.findViewById(R.id.mRecordDesc);
				descTv.setText(record.getDesc());

				// ����
				TextView dateTv = (TextView) convertView.findViewById(R.id.mRecordDate);
				// ��ʽ��һ����
				dateTv.setText(Utills.formatDay(record.getDate()));

				/*
				 * ������ʽ
				 */
				if (record.getType() == 2) {

					whoTv.setText(record.getWho() + "�����");
					numTv.setTextColor(Color.rgb(253, 75, 61));
				} else {

					whoTv.setText("�ҽ��" + record.getWho());
				}

			}
			return convertView;
		}
	}

	public void toast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
