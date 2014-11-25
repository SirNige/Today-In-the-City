package com.KNASK.todayinthecity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.KNASK.todayinthecity.R.menu;
import com.KNASK.todayinthecityDAO.BandsDAO;
import com.KNASK.todayinthecityDAO.ShowDAO;
import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Location;
import com.KNASK.todayinthecitymodel.Show;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Show>> listDataChild;
    
    public String loginUserName;
    public String loginUserEmail;
	
	// Calling Application class (see application tag in AndroidManifest.xml)
    List<Show> 	showEvents;   

    SearchView 	searchView;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		 //get passed intent 
        Intent intent = getIntent();
 
        //get message value from intent
        loginUserName = intent.getStringExtra("LOGINUSER");
        loginUserEmail = intent.getStringExtra("LOGINEMAIL");
		
        if(loginUserName != null)
        	Toast.makeText(this, "Google User: " + loginUserName + "(" + loginUserEmail + ")", Toast.LENGTH_SHORT).show();	
        
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
		  
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public void nearMe(View view) {
		Intent i = new Intent(getApplicationContext(), SearchActivity.class);
		i.putExtra("SHOWEVENT", (ArrayList)showEvents);
		startActivity(i);
	}
	
	public void myShows(View view) {
		//if it did not login, load login activity
		if(loginUserName == null) {
			Login();
		}
		else {
			Intent i = new Intent(getApplicationContext(), ShowsActivity.class);
		    i.putExtra("LOGINUSER", loginUserName);
		    i.putExtra("LOGINEMAIL", loginUserEmail);
			startActivity(i);				
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
		
		return true;
	}
	
    private void setupSearchView(MenuItem searchItem) {
    	 
//        if (isAlwaysExpanded()) {
//        	searchView.setIconifiedByDefault(false);
//        } else {
//            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
//                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//        }
 
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
 
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            searchView.setSearchableInfo(info);
        }
 
        searchView.setOnQueryTextListener(this);
    }
 
    public boolean onQueryTextChange(String newText) {

        return false;
    }
 
    public boolean onQueryTextSubmit(String query) {
    	Toast.makeText(getApplicationContext(), "Sorry, Under Construction!!!",
				Toast.LENGTH_LONG).show();
        return false;
    }	
	
    protected boolean isAlwaysExpanded() {
        return false;
    }

    /* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if (id == R.id.action_login) {
			Login();

		}			
		return super.onOptionsItemSelected(item);
	}
	
	public void Login() {
		Intent i = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(i);
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
        	if(show.getDate() != null) {
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


			try {
				showEvents = showDAO.getList(0, 50);
			} catch (NullPointerException e) {
				Toast.makeText(getApplicationContext(), "Null Pointer 1",
						Toast.LENGTH_LONG).show();
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	        
		// //////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////
		// TEST
//		String[][] data = {
//				{ "Crazy Halloween Night!", "2014-11-24 19:30:00",
//						"Algonquin College", "1385 Woodroffe Ave, Ottawa" },
//				{ "Raging Nathans Finderskeepers and Dead Weights",
//						"2014-11-25 19:30:00", "Mayfair Theatre Ottawa",
//						"1074 Bank Street, Ottawa" },
//				{ "Loreena McKennitt", "2014-11-25 19:30:00",
//						"Canadian Film Institute", "395 Rue Wellington, Ottawa" }, };
//
//		for (int i = 0; i < data.length; i++) {
//			Show showEvent = new Show();
//			showEvent.setShowID(i);
//			showEvent.setName(data[i][0]);
//			showEvent.setDate(Timestamp.valueOf(data[i][1]));
//
//			Location location = new Location();
//			location.setLocationID(i);
//			location.setName(data[i][2]);
//			location.setAddress(data[i][3]);
//			showEvent.setLocation(location);
//
//			showEvents.add(showEvent);
//		}
		// //////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////
		// /////////////////////////////////////////////////////////////////////////

		expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader,
				listDataChild);
		expListView.setAdapter(listAdapter);
		expListView.expandGroup(0);
	  
    }
    
    
   
}
