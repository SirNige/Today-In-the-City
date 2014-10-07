package com.KNASK.todayinthecity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		Thread logoTimer = new Thread() {
			public void run() {
				try {
					int logoTimer = 0;
					while(logoTimer < 5000) {
						sleep(100);
						logoTimer += 100;
					}
					startActivity(new Intent("com.KNASK.todayinthecity.CLEARSCREEN"));
				}
				catch(InterruptedException e) {
					e.printStackTrace();
				}
				finally {
					finish();
				}
			}
		};
		
		logoTimer.start();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
