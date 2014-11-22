package com.KNASK.todayinthecityDAO;
import com.KNASK.todayinthecitymodel.Location;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class LocationDAO implements ILocationDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.21.16.55/rest/Location";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);

	@Override
	public int create(Location location) {
		try {
			
			URL currentURL = null;
			
			try {
				currentURL = new URL(REST_SERVICE_URL);
			} catch (MalformedURLException e) {
				
			}
			
	        HttpURLConnection conn = (HttpURLConnection) currentURL.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/xml");
	        
	        OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
		    
	        xmlMapper.writeValue(osw, location);
	        
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
	 
			conn.disconnect();
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return 0;
	}

	@Override
	public Location get(int id) {
	    
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + id);
		} catch (MalformedURLException e) {
		}
		
		Location location = null;

	    try {
			location = xmlMapper.readValue(currentURL, Location.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return location;
	}

	@Override
	public boolean update(Location Location) {
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}
