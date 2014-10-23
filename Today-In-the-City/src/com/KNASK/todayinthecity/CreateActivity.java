package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.KNASK.todayinthecitymodel.ShowEvent;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateActivity extends Activity implements OnClickListener {

	ShowEvent showEvents;
	
	//widget GUI
	ImageButton btnCalendar, btnTimePicker;
	EditText 	txtDate, txtTime;
	
	// Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
		showEvents = (ShowEvent) getApplicationContext();
		
		// put Date Picker Dialog box  and Time Picker Dialog box code in Activity class
        btnCalendar = (ImageButton) findViewById(R.id.imageCalendar);
        btnTimePicker = (ImageButton) findViewById(R.id.imageTime);
 
        txtDate = (EditText) findViewById(R.id.editDate);
        txtTime = (EditText) findViewById(R.id.editTime);
 
        btnCalendar.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        
		
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
    public void onClick(View v) {
 
        if (v == btnCalendar) {
 
            // Process to get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
 
            // Launch Date Picker Dialog
            DatePickerDialog dpd = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
 
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                int monthOfYear, int dayOfMonth) {
                            // Display Selected date in textbox
                            txtDate.setText((monthOfYear + 1) + "/"
                                    + dayOfMonth + "/" + year);
 
                        }
                    }, mYear, mMonth, mDay);
            dpd.show();
        }
        if (v == btnTimePicker) {
 
            // Process to get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
 
            // Launch Time Picker Dialog
            TimePickerDialog tpd = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
 
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                int minute) {
                            // Display Selected time in textbox
                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            tpd.show();
        }
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
