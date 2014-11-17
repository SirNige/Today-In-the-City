package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.List;

import com.KNASK.todayinthecityDAO.BandsDAO;
import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Genre;
import com.KNASK.todayinthecitymodel.Show;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateBand extends Activity {

	//band list
	Band 	bandList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_band);
			
        /////////////////////////////////////////////////////////////////////////////////////////////
		//this list must get from Genre enumeration
		Spinner spinnerGenre = (Spinner) findViewById(R.id.spinnerBandGenre);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_band, menu);
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
			((Button)findViewById(R.id.btnCreateBand)).callOnClick();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void clickCreateBand(View view) {
		//validate data
		if(ValidateBandEntry()) {
			//insert a record to database
			
			String bandName = ((EditText)findViewById(R.id.editBandName)).getText().toString().trim();
			int indexSpin = ((Spinner) findViewById(R.id.spinnerBandGenre)).getSelectedItemPosition();
			Genre genre = Genre.values()[indexSpin];	
			String description = ((EditText)findViewById(R.id.editBandDescription)).getText().toString().trim();
			
			Band band = new Band();
			band.setName(bandName);
			band.setGenre(genre);
			band.setDescription(description);
			
			BandsDAO BandDAO = new BandsDAO();

			int bandID = BandDAO.create(band);
			
			Toast.makeText(this, "Created a New Band", Toast.LENGTH_SHORT).show();		
		}
	}
	
	private boolean ValidateBandEntry() {
		//check if title is empty
		if(!hasContent((EditText) findViewById(R.id.editBandName))) {
			Toast.makeText(this, "Enter the name of band.", Toast.LENGTH_SHORT).show();
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
	
}
