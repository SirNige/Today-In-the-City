package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.expandGroup(0);
             
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
