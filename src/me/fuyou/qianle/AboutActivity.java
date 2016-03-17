package me.fuyou.qianle;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity extends Activity {

	/*
	 * 页面组件
	 */
	private TextView version;
	private TextView backTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		backTv = (TextView) findViewById(R.id.abBackTv);
		backTv.setOnClickListener(new BackClickListener());

		version = (TextView) findViewById(R.id.about_verison);
		version.setText(getVersion());
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

			AboutActivity.this.finish();
		}

	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		String version ="v";
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	         version += info.versionName;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return version;
	}
}
