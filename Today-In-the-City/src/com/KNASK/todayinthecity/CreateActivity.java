package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.List;

import com.KNASK.todayinthecitymodel.ShowEvent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateActivity extends Activity {

	//ShowEvent showEvents = (ShowEvent) getApplicationContext();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		
		//this list must come from a band table of database.
		Spinner spinner = (Spinner) findViewById(R.id.spinnerBand);
		List<String> list = new ArrayList<String>();
		list.add("Band 1");
		list.add("Band 2");
		list.add("Band 3");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
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
		return super.onOptionsItemSelected(item);
	}
	
	/** Called when the user touches the Create button */
	public void clickCreateEvent(View view) {

		//insert a record
		
		//add it in ShowEvent class
		

		Toast.makeText(CreateActivity.this, "Created a New Band", Toast.LENGTH_SHORT).show();
	}
}
