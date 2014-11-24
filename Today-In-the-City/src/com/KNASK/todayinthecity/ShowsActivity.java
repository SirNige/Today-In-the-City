package com.KNASK.todayinthecity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ShowsActivity extends Activity {

	private String loginUserName;
	private String loginUserEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shows);
		
		 //get passed intent 
        Intent intent = getIntent();
 
        //get message value from intent
        loginUserName = intent.getStringExtra("LOGINUSER");
        loginUserEmail = intent.getStringExtra("LOGINEMAIL");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shows, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if (id == R.id.action_createband) {
			createBand();

		}
		else if (id == R.id.action_createshow) {
			createShow();

		}			
		return super.onOptionsItemSelected(item);
	}
	
	public void createShow() {
		Intent i = new Intent(getApplicationContext(), CreateActivity.class);
		startActivity(i);
	}
	
	public void createBand() {
		Intent i = new Intent(getApplicationContext(), CreateBand.class);
		startActivity(i);
	}
}
