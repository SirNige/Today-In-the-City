package com.KNASK.todayinthecity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.KNASK.todayinthecityDAO.BandsDAO;
import com.KNASK.todayinthecityDAO.ShowDAO;
import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Location;
import com.KNASK.todayinthecitymodel.Show;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Show>> listDataChild;
	
	// Calling Application class (see application tag in AndroidManifest.xml)
    List<Show> 	showEvents;   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		    
		//
		//showEvents 		= (List<Show>) getApplicationContext(); 	
		
		//create show array list
        showEvents 	= new ArrayList<Show>();

        //Read Show list from database
        LoadShowList();
        
        //listener for child row click
        expListView.setOnChildClickListener(myListItemClicked);
        //listener for group heading click
        expListView.setOnGroupClickListener(myListGroupClicked);
    
	}
	
	//our child listener
	private OnChildClickListener myListItemClicked = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		
			// get the group header
			String headerString = listDataHeader.get(groupPosition);
			// get the child info
			Show show = listDataChild.get(headerString).get(childPosition);

			// display it at the details activity	
			Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
			
			intent.putExtra("SHOWEVENT", show);
			startActivity(intent);	
			
			return false;
		}

	};
	
	// our group listener
	private OnGroupClickListener myListGroupClicked = new OnGroupClickListener() {

		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

			return false;
		}

	};
		  
	public void nearMe(View view) {
		Intent i = new Intent(getApplicationContext(), SearchActivity.class);
		i.putExtra("SHOWEVENT", (ArrayList)showEvents);
		startActivity(i);
	}
	
	public void myShows(View view) {
		Intent i = new Intent(getApplicationContext(), ShowsActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        
        if(searchView != null){
	        searchView.setOnQueryTextListener(new OnQueryTextListener() {
	    	    @Override
	    	    public boolean onQueryTextSubmit(String query) {
	    			Intent i = new Intent(getApplicationContext(), SearchActivity.class);
	    			startActivity(i);
	    	        return true;
	    	    }
	
	    		@Override
	    		public boolean onQueryTextChange(String arg0) {
	    			return true;
	    		}
	    	});
        } 
		
		return true;
	}

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Show>>();

        //today's show
        List<Show> todayShow = new ArrayList<Show>();
        //tomorrow's show
        List<Show> tomorrowShow = new ArrayList<Show>();
        //up coming show
        List<Show> upcomingShow = new ArrayList<Show>();

        //classify shows depend on the date
        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
        
        //get current date
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        //calculate tomorrow's date
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        
        String currentDate = formatDate.format(today);
        String tomorrowDate = formatDate.format(tomorrow);
        
        String showDate;
        for(Show show : showEvents) {
            showDate = formatDate.format(show.getDate().getTime());        	
        	
        	if(showDate.equals(currentDate)) {
        		todayShow.add(show);
        	}
        	else if(showDate.equals(tomorrowDate)) {
        		tomorrowShow.add(show);
        	}
        	else {
        		upcomingShow.add(show);
        	}
        }
        
        listDataHeader.add("Today(" + currentDate + ")");
        listDataHeader.add("Tomorrow(" + tomorrowDate + ")");
        listDataHeader.add("Upcoming");
 
        listDataChild.put(listDataHeader.get(0), todayShow); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tomorrowShow);
        listDataChild.put(listDataHeader.get(2), upcomingShow);
    }
    
    /**
     * Load show list from database
     */
    private void LoadShowList() {
    	
    	try {
	    	ShowDAO showDAO = new ShowDAO();
//	        BandsDAO bandDAO = new BandsDAO();
	        
	    	Show show = null;
	    	
	    	try {
	    		show = showDAO.get(50);
	    	} catch(NullPointerException e) {
	    		Toast.makeText(getApplicationContext(), "Null Pointer 1", Toast.LENGTH_LONG).show();
	    	}
	    	
	    	try {
	    		Toast.makeText(getApplicationContext(), show.getName(), Toast.LENGTH_LONG).show();
	    	} catch(NullPointerException e) {
	    		Toast.makeText(getApplicationContext(), "Null Pointer!", Toast.LENGTH_LONG).show();
	    	}
    	} catch(Exception ex) {
    		ex.printStackTrace();
   		
    	}	
	        
    		////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////
    		////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////
    		// TEST 
    		String[][] data = {
  				  {"Crazy Halloween Night!", "2014-11-18 19:30:00", "Algonquin College", "1385 Woodroffe Ave, Ottawa"},
  				  {"Raging Nathans Finderskeepers and Dead Weights", "2014-11-18 19:30:00", "Mayfair Theatre Ottawa", "1074 Bank Street, Ottawa"},
  				  {"Loreena McKennitt", "2014-11-19 19:30:00", "Canadian Film Institute", "395 Rue Wellington, Ottawa"},
  				};
  		
	  		for(int i = 0; i < data.length ; i++) {
	  			Show showEvent = new Show();
	  			showEvent.setShowID(i);
	  			showEvent.setName(data[i][0]);
				showEvent.setDate(Timestamp.valueOf(data[i][1]));
	
				Location location = new Location();
				location.setLocationID(i);
				location.setName(data[i][2]);
				location.setAddress(data[i][3]);
	  			showEvent.setLocation(location);
	
	  			showEvents.add(showEvent);
	  		}
    		////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////
    		////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////    		
	  		////////////////////////////////////////////////////////////////////////////
    		///////////////////////////////////////////////////////////////////////////	  		
    		
        
	        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
	        prepareListData();
	        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	        expListView.setAdapter(listAdapter);
	        expListView.expandGroup(0);
	  
    }
    
    
   
}
