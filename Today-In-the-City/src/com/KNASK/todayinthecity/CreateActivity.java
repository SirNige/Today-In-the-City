package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Genre;
import com.KNASK.todayinthecitymodel.Location;
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
        
		
        /////////////////////////////////////////////////////////////////////////////////////////////
		//this list must come from a location table of database.
		Spinner spinnerLoc = (Spinner) findViewById(R.id.spinnerLocation);
		List<Location> listLoc = new ArrayList<Location>();
		
		//data for TEST only
		String[][] data = {
				  {"Crazy Halloween Night!", "7:00PM Fri Oct 31, 2014", "Algonquin College", "1385 Woodroffe Ave, Ottawa"},
				  {"Raging Nathans Finderskeepers and Dead Weights", "2:00PM Tue Oct 14, 2014", "Mayfair Theatre Ottawa", "1074 Bank Street, Ottawa"},
				  {"Loreena McKennitt", "7:00PM Sun Oct 31, 2014", "Canadian Film Institute", "395 Rue Wellington, Ottawa"},
				  {"LIGHTS", "7:00PM Wed Nov 30, 2014", "Landmark 7 Ottawa",   "111 Albert Street, Ottawa"},
				  {"Audible Obsession", "7:00PM Sat Oct 16, 2014", "Ottawa Family Cinema",   "710 Broadview Ave, Ottawa"},
				  {"Unearth", "7:00PM Mon Oct 27, 2014", "Cineplex Odeon South Keys",   "2214 Bank Street, Ottawa"}
				};
		
		for(int i = 0; i < data.length ; i++) {
			Location loc = new Location();
			
			loc.setLocatonID(i);
			loc.setLocationName(data[i][2]);
			loc.setLocationAddress(data[i][3]);
			listLoc.add(loc);
		}

		ArrayAdapter<Location> dataAdapterLoc = new ArrayAdapter<Location>(this, android.R.layout.simple_spinner_item, listLoc);
		dataAdapterLoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerLoc.setAdapter(dataAdapterLoc);
		
        /////////////////////////////////////////////////////////////////////////////////////////////
		//this list must come from a band table of database.
		Spinner spinnerBand = (Spinner) findViewById(R.id.spinnerBand);
		List<Band> listBand = new ArrayList<Band>();
		
		//data for TEST only
		String[][] dataBand = {
				  {"Metallica", "Good Band"},
				  {"The Beatles", "Nice Band"},
				  {"Led Zeppelin", "Awesome Band"},
				  {"Queen", "Great Band"},
				  {"Radiohead", "So Good Band"}
				};
		for(int i = 0; i < dataBand.length ; i++) {
			Band band = new Band();
			
			band.setBandID(i);
			band.setBandName(dataBand[i][0]);
			band.setGenre(Genre.values()[i]);
			band.setDescription(dataBand[i][1]);

			listBand.add(band);
		}

		ArrayAdapter<Band> dataAdapterBand = new ArrayAdapter<Band>(this, android.R.layout.simple_spinner_item, listBand);
		dataAdapterBand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerBand.setAdapter(dataAdapterBand);
		
        /////////////////////////////////////////////////////////////////////////////////////////////
		//this list must get from Genre enumeration
		Spinner spinnerGenre = (Spinner) findViewById(R.id.spinnerGenre);
		List<String> listGenre = new ArrayList<String>();		
		for(int i = 0 ; i < Genre.values().length ; i++ ) {	
			listGenre.add(Genre.values()[i].toString());
		}	
		ArrayAdapter<String> dataAdapterGenre = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, listGenre);
		dataAdapterGenre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerGenre.setAdapter(dataAdapterGenre);
		
		
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
		//validate data
		if(ValidateShowEntry()) {
			//insert a record to database
			int showID = InsertShowData();
			
			//add it in ShowEvent class
			ShowEvent showEvent = new ShowEvent();
			
			showEvent.setShowID(showID);
			showEvent.setShowTitle(((EditText)findViewById(R.id.editTitle)).getText().toString().trim());
			showEvent.setShowDate(((EditText)findViewById(R.id.editDate)).getText().toString().trim() + " " + ((EditText)findViewById(R.id.editTime)).getText().toString().trim());
			
			int genre = ((Spinner) findViewById(R.id.spinnerGenre)).getSelectedItemPosition();
			showEvent.setGenre(Genre.values()[genre]);
			
			Location location = (Location)((Spinner) findViewById(R.id.spinnerLocation)).getSelectedItem();
			showEvent.setLocationID(location.getLocatonID());
			showEvent.setLocationName(location.getLocationName());
			showEvent.setLocationAddress(location.getLocationAddress());
			
			Band band = (Band)((Spinner) findViewById(R.id.spinnerBand)).getSelectedItem();
			showEvent.addBand(band);
			
			showEvent.setEntranceFee(((EditText)findViewById(R.id.editPrice)).getText().toString().trim());
			showEvent.setContactEmail(((EditText)findViewById(R.id.editEmail)).getText().toString().trim());
			showEvent.setContactPhone(((EditText)findViewById(R.id.editPhone)).getText().toString().trim());
			showEvent.setWebSite(((EditText)findViewById(R.id.editWebsite)).getText().toString().trim());
			showEvent.setDescription(((EditText)findViewById(R.id.editDescription)).getText().toString().trim());
	
			showEvents.addShowEvent(showEvent);
			
			Toast.makeText(this, "Created a New Band", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * check if EditText is empty.
	 * Title, Date and Time of show are required, the others are optional.
	 * @return true if all validated, otherwise false
	 */
	private boolean ValidateShowEntry() {
		//check if title is empty
		if(!hasContent((EditText) findViewById(R.id.editTitle))) {
			Toast.makeText(this, "Enter the title of show.", Toast.LENGTH_SHORT).show();
			return false;
		}	
		//check if date is empty, and does not have to past date.
		if(!hasContent((EditText) findViewById(R.id.editDate))) {
			Toast.makeText(this, "Enter the date of show.", Toast.LENGTH_SHORT).show();
			return false;
		}
		//check if time is empty
		if(!hasContent((EditText) findViewById(R.id.editTime))) {
			Toast.makeText(this, "Enter the time of show.", Toast.LENGTH_SHORT).show();
			return false;
		}	
		
		return true;
	}
	
	/**
	 * Check if an EditText is empty
	 * @param edit - EditText control
	 * @return true if has content, otherwise false.
	 */
	private boolean hasContent(EditText edit) {
	    // Always assume false until proven otherwise
	    boolean bHasContent = false; 

	    if (edit.getText().toString().trim().length() > 0) {
	        // Got content
	        bHasContent = true;
	    }
	    return bHasContent;
	}
	
	/**
	 * 
	 * @return - return the show ID after insert a record
	 */
	private int InsertShowData() {
		int showID = 0;
		
		return showID;
	}
}
