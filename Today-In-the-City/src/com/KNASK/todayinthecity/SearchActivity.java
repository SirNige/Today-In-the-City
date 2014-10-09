package com.KNASK.todayinthecity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class SearchActivity extends FragmentActivity implements LocationListener {

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
				  {"Algonquin College", "1385 Woodroffe Ave, Ottawa"},
				  {"Mayfair Theatre Ottawa", "1074 Bank Street, Ottawa"},
				  {"Canadian Film Institute", "395 Rue Wellington, Ottawa"},
				  {"Landmark 7 Ottawa",   "111 Albert Street, Ottawa"},
				  {"Ottawa Family Cinema",   "710 Broadview Ave, Ottawa"},
				  {"Cineplex Odeon South Keys",   "2214 Bank Street, Ottawa"}
				};
		
		for(int i = 0; i < data.length ; i++) {
			ShowEvent showEvent = new ShowEvent();
			
			showEvent.locationName = data[i][0];
			showEvent.locationAddress = data[i][1];
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
	    	for (ShowEvent showEvent: showEvents) {
	            try {
	            	System.out.println("Name=" + showEvent.locationName + ", Location=" + showEvent.locationAddress);
	                addressList = geoCoder.getFromLocationName(showEvent.locationAddress, 1);
	                if (addressList == null || addressList.isEmpty() || addressList.equals("")) {
	                    addressList = geoCoder.getFromLocationName("Algonquin College", 1);
	                }
	                latitude = addressList.get(0).getLatitude();
	                longitude = addressList.get(0).getLongitude();
	                
	            	System.out.println("Name=" + showEvent.locationName + ", Location=" + showEvent.locationAddress);
	                
	            	googleMap.addMarker(new MarkerOptions()
	                          .position(new LatLng(latitude, longitude))
	                          .title(showEvent.locationName)
	                          .snippet(showEvent.locationAddress)
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
