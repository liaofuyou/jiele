package me.fuyou.qianle;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SplashActivity extends Activity {

	private TextView version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		version = (TextView) findViewById(R.id.splash_verison);
		version.setText(getVersion());

		new Handler().postDelayed(new Runnable() {

			public void run() {

				// 延时执行。

				SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
				SplashActivity.this.finish();
			}

		}, 2000);
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
