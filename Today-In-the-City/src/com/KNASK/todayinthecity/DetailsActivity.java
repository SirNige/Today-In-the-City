package com.KNASK.todayinthecity;
import com.KNASK.todayinthecitymodel.ShowEvent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	private ShowEvent 	showEvent;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		try{  
        	showEvent = (ShowEvent) getIntent().getSerializableExtra("SHOWEVENT");
            
            // Getting reference to the TextView to set show name
            TextView tvTitle = (TextView) findViewById(R.id.detailEvent_showtitle);          
            // Setting the ShowTitle
            tvTitle.setText(showEvent.getShowTitle());
        	
            TextView tvDate = (TextView) findViewById(R.id.detailEvent_showDate);          
            tvDate.setText(showEvent.getShowDate());
            
            TextView tvLocation = (TextView) findViewById(R.id.detailEvent_locationName);          
            tvLocation.setText(showEvent.getLocationName() + System.getProperty("line.separator") + showEvent.getLocationAddress());           
            
            TextView tvCost = (TextView) findViewById(R.id.detailEvent_entranceFee);          
            tvCost.setText(showEvent.getEntranceFee());
            
//            TextView tvEmail = (TextView) findViewById(R.id.detailEvent_email);          
//            tvEmail.setText(showEvent.getContactEmail());
//            
//            TextView tvPhone = (TextView) findViewById(R.id.detailEvent_phone);          
//            tvPhone.setText(showEvent.getContactPhone());
//
//            TextView tvWebsite = (TextView) findViewById(R.id.detailEvent_website);          
//            tvWebsite.setText(showEvent.getWebSite());
            
            TextView tvDescription = (TextView) findViewById(R.id.detailEvent_showDescription);          
            tvDescription.setText(showEvent.getDescription());
            

        }catch(Exception e)
        {
        	e.printStackTrace();
        }		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
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
}
