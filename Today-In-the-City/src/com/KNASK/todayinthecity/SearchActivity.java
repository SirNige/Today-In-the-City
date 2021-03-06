package com.KNASK.todayinthecity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.KNASK.todayinthecityDAO.BandsDAO;
import com.KNASK.todayinthecityDAO.LocationDAO;
import com.KNASK.todayinthecityDAO.SearchDAO;
import com.KNASK.todayinthecityDAO.ShowDAO;
import com.KNASK.todayinthecitymodel.Search;
import com.KNASK.todayinthecitymodel.SearchResult;
import com.KNASK.todayinthecitymodel.Show;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends FragmentActivity implements LocationListener {

	private GoogleMap googleMap;
	
	List<Show> showEvents;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new
				StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
				StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
			
		showEvents = (ArrayList<Show>) getIntent().getSerializableExtra("SHOWEVENT");
		//LoadShowList();
		
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_search.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
 
            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);
 
            if(location!=null){
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            
            
            addMarkersToMap();
            
            
            googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
            	 
                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }
     
                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker marker) {
     
                    // Getting view from the layout file info_window_layout
                    View v = getLayoutInflater().inflate(R.layout.show_info_contents, null);
        
                    // Getting reference to the TextView to set show name
                    TextView tvTitle = (TextView) v.findViewById(R.id.showname);
                    // Getting reference to the TextView to set show date
                    TextView tvDate = (TextView) v.findViewById(R.id.showdate);
                    // Getting reference to the TextView to set show place
                    TextView tvPlace = (TextView) v.findViewById(R.id.place);
     
                    int id = Integer.parseInt(marker.getSnippet());
                    
                    Show showEvent = null;
                    
                    for(Show show : showEvents) {
                    	if(show.getShowID() == id) {
                    		showEvent = show;
                    		break;
                    	}
                    }
                    
                    
                    // Setting the ShowTitle
                    tvTitle.setText(showEvent.getName());
     
                    // Setting the ShowDate
                    SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    if(showEvent.getDate() != null) {
                    	tvDate.setText(formatDate.format(showEvent.getDate()));
                    }
     
                    // Setting the LocationName
                    tvPlace.setText(showEvent.getLocation().getName());
     
                    // Returning the view containing InfoWindow contents
                    return v;
     
                }
            });
            
            //info window click event
            googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
         	   @Override

        	   public void onInfoWindowClick(Marker marker) {
                   int id = Integer.parseInt(marker.getSnippet());
                   
                   Show showEvent = null;
                   
                   for(Show show : showEvents) {
                   	if(show.getShowID() == id) {
                   		showEvent = show;
                   		break;
                   	}
                   }
				
					Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);
					
					intent.putExtra("SHOWEVENT", showEvent);
					startActivity(intent);		  

        	   }
            });
            
        }
	}

	@Override
	public void onLocationChanged(Location location) {
        // Getting latitude of the current location
        double latitude = location.getLatitude();
 
        // Getting longitude of the current location
        double longitude = location.getLongitude();
 
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
 
        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
 
        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		
        MenuItem searchItem = menu.findItem(R.id.action_search2);
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
	

	
	// convert address to lng, lat and add markers to map
	public void addMarkersToMap() {
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
		
		showEvents = new ArrayList<Show>();
		
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
		
		Location location = locationManager.getLastKnownLocation(provider);
		
		Search search = Search.SearchBuilder.create(Search.SearchType.Show).lat(location.getLatitude()).lon(location.getLongitude()).build();
		
		SearchDAO searchDAO = new SearchDAO();
		
		List<SearchResult> results = searchDAO.search(search);
		
		LocationDAO locDAO = new LocationDAO();
		
		for(SearchResult result : results) {
			result.getShow().setLocation(locDAO.get(result.getShow().getLocationID()));
			showEvents.add(result.getShow());
		}
		
		googleMap.clear();
		
	    List<Address> addressList;
	    Double latitude, longitude;
	    
	    if (showEvents.size() > 0) {
	    	for (Show showEvent: showEvents ) {
	            try {
	            	if(showEvent.getLocation() == null || (showEvent.getLocation().getLat() == 0.0 || showEvent.getLocation().getLat() == 0.0)) {
		                addressList = geoCoder.getFromLocationName(showEvent.getLocation().getAddress(), 1);
		                if (addressList == null || addressList.isEmpty() || addressList.equals("")) {
		                    addressList = geoCoder.getFromLocationName("Algonquin College", 1);
		                }
		                latitude = addressList.get(0).getLatitude();
		                longitude = addressList.get(0).getLongitude();
	            	} else {
		                latitude = showEvent.getLocation().getLat();
		                longitude = showEvent.getLocation().getLon();
	            	}
	                           
	            	googleMap.addMarker(new MarkerOptions()
	                          .position(new LatLng(latitude, longitude))
	                          .title((showEvent.getLocation()).getName())
	                          .snippet(""+ showEvent.getShowID())
	                          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
	                          .alpha(0.7f)
	                );

	            } catch (Exception e) {
	                e.printStackTrace();
	            } // end catch
	        }
	    }
	} //end addMarkersToMap
	
    /**
     * Load show list from database
     */
    private void LoadShowList() {
    	
    }
}
