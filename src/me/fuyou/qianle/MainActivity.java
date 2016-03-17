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
 * 主Activity
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
	 * 获取组件
	 */
	private Button addBtn;
	private ListView recordLv;
	private RecordAdapter recordAdapter;
	private RelativeLayout settingRl;
	private TextView settingAboutTv;
	private ImageView settingTagIv;
	private ImageView nullTishiIv;

	// 对话框
	AlertDialog.Builder recordOptBuilder;
	Record currOptRecord;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		recordService = new RecordService(this);

		/*
		 * 初始化数据。
		 */
		Grobal.records = recordService.findList();

		/*
		 * 初始化组件
		 */
		addBtn = (Button) findViewById(R.id.mAddBtn);
		addBtn.setOnClickListener(new AddClickListener());

		// 设置
		SettingTagClickListener settingTagClickListener = new SettingTagClickListener();
		SettingClickListener settingClickListener = new SettingClickListener();

		// "..."
		settingTagIv = (ImageView) findViewById(R.id.mSettingIv);
		settingTagIv.setOnClickListener(settingTagClickListener);

		// 为空提示
		nullTishiIv = (ImageView) findViewById(R.id.mNullTishiIv);

		// 关于app
		settingAboutTv = (TextView) findViewById(R.id.mAboutTv);
		settingAboutTv.setOnClickListener(settingClickListener);

		// 开发者
		// settingAuthorTv = (TextView) findViewById(R.id.mAuthorTv);
		// settingAuthorTv.setOnClickListener(settingClickListener);

		// 外宽
		settingRl = (RelativeLayout) findViewById(R.id.mSettingRl);
		settingRl.setOnClickListener(settingTagClickListener);

		// 列表
		recordLv = (ListView) findViewById(R.id.mRecordLv);
		recordAdapter = new RecordAdapter();
		recordLv.setAdapter(recordAdapter);
		recordLv.setOnItemClickListener(new RecordItemClickListener());
		recordLv.setOnItemLongClickListener(new RecordItemLongClickListener());

		// 实例化Record操作对话框
		recordOptBuilder = new AlertDialog.Builder(this).setItems(new String[] { "编辑", "删除" }, new RecordDialogListener());

		// andorid 有点反人类。明明可以做的很好，但它却不。
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

		// 如果没有记录了。提示一下。
		if (Grobal.records.size() == 0) {

			nullTishiIv.setVisibility(View.VISIBLE);
		} else {

			nullTishiIv.setVisibility(View.GONE);
		}
	}

	/**
	 * 点击某条记录，直接进入修改页面
	 * 
	 * @author Administrator
	 * 
	 */
	private class RecordItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int possion, long id) {
			// TODO Auto-generated method stub

			// 记录将要修改的记录
			currOptRecord = Grobal.records.get(possion);

			// 克隆要修改的记录，sorry这样比较笨。
			Grobal.isupdate = true;
			Grobal.record = new Record(currOptRecord.getId(), currOptRecord.getType(), currOptRecord.getNum(), currOptRecord.getWho(), currOptRecord.getDesc(), currOptRecord.getDate());
			currOptRecord = null;

			/*
			 * 启动添加界面。做修改操作。
			 */
			startActivity(new Intent(MainActivity.this, AddOneActivity.class));

		}

	}

	/**
	 * 长按记录，启动操作对话框
	 * 
	 * @author Daniel
	 * 
	 */
	private class RecordItemLongClickListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int possion, long id) {
			// TODO Auto-generated method stub

			// 记录将要修改的记录
			currOptRecord = Grobal.records.get(possion);

			// 启动对话框。
			recordOptBuilder.show();

			return false;
		}

	}

	/**
	 * 对话框选择监听类
	 * 
	 * @author Administrator
	 * 
	 */
	private class RecordDialogListener implements DialogInterface.OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

			if (which == 0) {

				// 克隆要修改的记录，sorry这样比较笨。
				Grobal.isupdate = true;
				Grobal.record = new Record(currOptRecord.getId(), currOptRecord.getType(), currOptRecord.getNum(), currOptRecord.getWho(), currOptRecord.getDesc(), currOptRecord.getDate());
				currOptRecord = null;

				/*
				 * 启动添加界面。做修改操作。
				 */
				startActivity(new Intent(MainActivity.this, AddOneActivity.class));

			} else {

				// 修改数据库
				recordService.delete(currOptRecord.getId());

				// 移除记录、刷新显示
				Grobal.records.remove(currOptRecord);
				onResume();
				currOptRecord = null;

				// 提示成功
				toast("删除成功");
			}
		}
	}

	/**
	 * 点击添加按钮时候，所触发的事件。
	 * 
	 * @author Daniel
	 * 
	 */
	private class AddClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub

			// 表示为添加
			Grobal.isupdate = false;

			/*
			 * 启动添加界面。
			 */
			startActivity(new Intent(MainActivity.this, AddOneActivity.class));
		}

	}

	/**
	 * 点击“关于作者”“关于App”按钮时候，所触发的事件。
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
	 * 点击“...”按钮时候，所触发的事件。
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
	 * 列表适配器
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

				// 给谁？
				TextView whoTv = (TextView) convertView.findViewById(R.id.mRecordWho);

				// 价格
				TextView numTv = (TextView) convertView.findViewById(R.id.mRecordNum);
				numTv.setText("￥" + Utills.decimalFormat3(record.getNum() + ""));

				// 备注
				TextView descTv = (TextView) convertView.findViewById(R.id.mRecordDesc);
				descTv.setText(record.getDesc());

				// 日期
				TextView dateTv = (TextView) convertView.findViewById(R.id.mRecordDate);
				// 格式化一下下
				dateTv.setText(Utills.formatDay(record.getDate()));

				/*
				 * 区分样式
				 */
				if (record.getType() == 2) {

					whoTv.setText(record.getWho() + "借给我");
					numTv.setTextColor(Color.rgb(253, 75, 61));
				} else {

					whoTv.setText("我借给" + record.getWho());
				}

			}
			return convertView;
		}
	}

	public void toast(String text) {

		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
}
