package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.KNASK.todayinthecitymodel.ShowEvent;
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
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends FragmentActivity implements LocationListener{

	private GoogleMap googleMap;
	public  ArrayList<ShowEvent> showEvents; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		showEvents = new ArrayList<ShowEvent>();
		
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
            
            
            addTestEvent();
            
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
     
                    ShowEvent showEvent = showEvents.get(Integer.parseInt(marker.getSnippet()));
                    
                    // Setting the ShowTitle
                    tvTitle.setText(showEvent.getShowTitle());
     
                    // Setting the ShowDate
                    tvDate.setText(showEvent.getShowDate());
     
                    // Setting the LocationName
                    tvPlace.setText(showEvent.getLocationName());
     
                    // Returning the view containing InfoWindow contents
                    return v;
     
                }
            });
            
            //info window click event
            googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
         	   @Override

        	   public void onInfoWindowClick(Marker marker) {
         		   	ShowEvent showEvent = showEvents.get(Integer.parseInt(marker.getSnippet()));
				
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
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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
	
	/////////////////////////////////////////////////////////////////////////////
	// It should be retrieved from Database.
	public void addTestEvent() {
		String[][] data = {
				  {"Crazy Halloween Night!", "7:00PM Fri Oct 31, 2014", "Algonquin College", "1385 Woodroffe Ave, Ottawa"},
				  {"Raging Nathans Finderskeepers and Dead Weights", "2:00PM Tue Oct 14, 2014", "Mayfair Theatre Ottawa", "1074 Bank Street, Ottawa"},
				  {"Loreena McKennitt", "7:00PM Sun Oct 31, 2014", "Canadian Film Institute", "395 Rue Wellington, Ottawa"},
				  {"LIGHTS", "7:00PM Wed Nov 30, 2014", "Landmark 7 Ottawa",   "111 Albert Street, Ottawa"},
				  {"Audible Obsession", "7:00PM Sat Oct 16, 2014", "Ottawa Family Cinema",   "710 Broadview Ave, Ottawa"},
				  {"Unearth", "7:00PM Mon Oct 27, 2014", "Cineplex Odeon South Keys",   "2214 Bank Street, Ottawa"}
				};
		
		for(int i = 0; i < data.length ; i++) {
			ShowEvent showEvent = new ShowEvent();
			
			showEvent.setShowTitle(data[i][0]);
			showEvent.setShowDate(data[i][1]);
			showEvent.setLocationName(data[i][2]);
			showEvent.setLocationAddress(data[i][3]);

			showEvents.add(showEvent);
		}
		
	}
	
	// convert address to lng, lat and add markers to map
	public void addMarkersToMap() {
		Geocoder geoCoder = new Geocoder(this, Locale.getDefault()); ;
		
		googleMap.clear();
		
	    List<Address> addressList;
	    Double latitude, longitude;
	    
	    if (!showEvents.isEmpty()) {
	    	int num = 0;
	    	for (ShowEvent showEvent: showEvents) {
	            try {
	                addressList = geoCoder.getFromLocationName(showEvent.getLocationAddress(), 1);
	                if (addressList == null || addressList.isEmpty() || addressList.equals("")) {
	                    addressList = geoCoder.getFromLocationName("Algonquin College", 1);
	                }
	                latitude = addressList.get(0).getLatitude();
	                longitude = addressList.get(0).getLongitude();
	                           
	            	googleMap.addMarker(new MarkerOptions()
	                          .position(new LatLng(latitude, longitude))
	                          .title(showEvent.getLocationName())
	                          .snippet(""+ num++)
	                          .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
	                          .alpha(0.7f)
	                );

	            } catch (Exception e) {
	                e.printStackTrace();
	            } // end catch
	        }
	    }
	} //end addMarkersToMap
}
