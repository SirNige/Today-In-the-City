package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.ShowEvent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	
	// Calling Application class (see application tag in AndroidManifest.xml)
    ShowEvent showEvents;   
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		//Read show list from database 
		showEvents = (ShowEvent) getApplicationContext(); 
		
        showEvents.showEventList = new ArrayList<ShowEvent>();
        
        //this method is for test only.
        addTestEvent();
		//
        
        
        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
             
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// It should be retrieved from Database.
	public void addTestEvent() {
		String[][] data = {
//				  {"Crazy Halloween Night!", "7:00PM Fri Oct 31, 2014", "Algonquin College", "1385 Woodroffe Ave, Ottawa"},
//				  {"Raging Nathans Finderskeepers and Dead Weights", "2:00PM Tue Oct 14, 2014", "Mayfair Theatre Ottawa", "1074 Bank Street, Ottawa"},
//				  {"Loreena McKennitt", "7:00PM Sun Oct 31, 2014", "Canadian Film Institute", "395 Rue Wellington, Ottawa"},
				  {"LIGHTS", "7:00PM Wed Nov 30, 2014", "Landmark 7 Ottawa",   "111 Albert Street, Ottawa"},
				  {"Audible Obsession", "7:00PM Sat Oct 16, 2014", "Ottawa Family Cinema",   "710 Broadview Ave, Ottawa"},
				  {"Unearth", "7:00PM Mon Oct 27, 2014", "Cineplex Odeon South Keys",   "2214 Bank Street, Ottawa"}
				};
		
		for(int i = 0; i < data.length ; i++) {
			ShowEvent showEvent = new ShowEvent();
			
			showEvent.setShowID(i);
			showEvent.setShowTitle(data[i][0]);
			showEvent.setShowDate(data[i][1]);
			showEvent.setLocationName(data[i][2]);
			showEvent.setLocationAddress(data[i][3]);

			showEvents.addShowEvent(showEvent);
		}
		
	}
	
	public void nearMe(View view) {
		Intent i = new Intent(getApplicationContext(), SearchActivity.class);
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
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Today");
        listDataHeader.add("Tomorrow");
        listDataHeader.add("Upcoming");

        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
 
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
	
}
