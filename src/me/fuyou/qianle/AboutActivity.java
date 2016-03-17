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
	 * ҳ�����
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
	 * ��������ء���ťʱ�����������¼���
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
	 * ��ȡ�汾��
	 * @return ��ǰӦ�õİ汾��
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
