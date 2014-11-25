package com.KNASK.todayinthecity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.KNASK.todayinthecitymodel.Location;
import com.KNASK.todayinthecitymodel.Show;
import com.KNASK.todayinthecity.PullToRefreshListView.OnRefreshListener;
import com.KNASK.todayinthecityDAO.ShowDAO;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowsActivity extends ListActivity  {

	private String loginUserName;
	private String loginUserEmail;
	
	private List<Show> 	showEvents; 
    private List<String> mListItems;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shows);
		
		 //get passed intent 
        Intent intent = getIntent();
 
        //get message value from intent
        loginUserName = intent.getStringExtra("LOGINUSER");
        loginUserEmail = intent.getStringExtra("LOGINEMAIL");
        
		//create show array list
        showEvents 	= new ArrayList<Show>();
        
        LoadMyShow();
        
        // Set a listener to be invoked when the list should be refreshed.
		((PullToRefreshListView)getListView())
				.setOnRefreshListener(new OnRefreshListener() {
					@Override
					public void onRefresh() {
						// Do work to refresh the list here.
						new GetDataTask().execute();
					}
				});

		ArrayAdapter<Show> adapter = new ArrayAdapter<Show>(this, android.R.layout.simple_list_item_1, showEvents);

		setListAdapter(adapter);
        
        
	}

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
	     // ListView Clicked item index
        int itemPosition     = position;
        
        // ListView Clicked item value
        Show  itemShow  = (Show) l.getItemAtPosition(position);
           
		// display it at the details activity	
		Intent intent = new Intent(ShowsActivity.this, DetailsActivity.class);
		
		intent.putExtra("SHOWEVENT", itemShow);
		startActivity(intent);	
	}

	private class GetDataTask extends AsyncTask<Void, Void, List<Show>> {

		@Override
		protected List<Show> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				LoadMyShow();
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			return showEvents;
		}

		@Override
		protected void onPostExecute(List<Show> result) {
			// Call onRefreshComplete when the list has been refreshed.
			((PullToRefreshListView)getListView()).onRefreshComplete();

			super.onPostExecute(result);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shows, menu);
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
			createBand();

		}
		else if (id == R.id.action_createshow) {
			createShow();

		}			
		return super.onOptionsItemSelected(item);
	}
	
	public void createShow() {
		Intent i = new Intent(getApplicationContext(), CreateActivity.class);
		startActivity(i);
	}
	
	public void createBand() {
		Intent i = new Intent(getApplicationContext(), CreateBand.class);
		startActivity(i);
	}
	
	private void LoadMyShow() {
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
				
	}
	
}
