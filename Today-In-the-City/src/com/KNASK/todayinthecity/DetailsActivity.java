package com.KNASK.todayinthecity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Show;

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
	private Show 	showEvent;
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		try{  
        	showEvent = (Show) getIntent().getSerializableExtra("SHOWEVENT");
            
            // Getting reference to the TextView to set show name
            TextView tvTitle = (TextView) findViewById(R.id.detailEvent_showtitle);          
            // Setting the ShowTitle
            tvTitle.setText(showEvent.getName());
            
            TextView tvDate = (TextView) findViewById(R.id.detailEvent_showDate);          
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if(showEvent.getDate() != null)
            	tvDate.setText(formatDate.format(showEvent.getDate().getTime()));
            
            
            TextView tvBands = (TextView) findViewById(R.id.detailEvent_Band);  
        	StringBuilder sb = new StringBuilder();

        	List<Band> bands = new ArrayList<Band>();
        	bands = showEvent.getBands();
        	if(bands != null) {
	        	for(Band band : bands) {
	        		sb.append(band.toString());
	        		sb.append(", ");
	        	}
	            tvBands.setText(sb.toString().substring(0, sb.toString().length()-1));
        	}
            
            TextView tvLocation = (TextView) findViewById(R.id.detailEvent_locationName);    
            if(showEvent.getLocation() != null)
            	tvLocation.setText(showEvent.getLocation().getName() + System.getProperty("line.separator") + showEvent.getLocation().getAddress());           
            
            TextView tvCost = (TextView) findViewById(R.id.detailEvent_entranceFee);          
            tvCost.setText(showEvent.getCost());
            
            TextView tvEmail = (TextView) findViewById(R.id.detailEvent_email);          
            tvEmail.setText(showEvent.getContactEmail());
            
            TextView tvPhone = (TextView) findViewById(R.id.detailEvent_phone);          
            tvPhone.setText(showEvent.getContactPhone());

            TextView tvWebsite = (TextView) findViewById(R.id.detailEvent_website);          
            tvWebsite.setText(showEvent.getWebSite());
            
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
