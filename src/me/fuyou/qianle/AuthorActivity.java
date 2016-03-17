package me.fuyou.qianle;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AuthorActivity extends Activity {

	/*
	 * 页面组件
	 */
	private TextView backTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_author);

		backTv = (TextView) findViewById(R.id.auBackTv);
		backTv.setOnClickListener(new BackClickListener());

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

			AuthorActivity.this.finish();
		}

	}
}
