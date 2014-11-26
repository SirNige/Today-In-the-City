package com.KNASK.todayinthecityDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.KNASK.todayinthecitymodel.Location;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class LocationDAO implements ILocationDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.25.18.56/rest/location";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();

	@Override
	public int create(Location location) {
		int id = 0;
		try {
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(location));
			postRequest.setHeader("Content-Type","application/xml");
			postRequest.setEntity(input);
			
			HttpResponse response = httpClient.execute(postRequest);
			
			id = Integer.parseInt(response.getFirstHeader("id").getValue());
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return id;
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
	public boolean update(Location location) {
		boolean success = false;
		try {
			
			HttpPut postRequest = new HttpPut(REST_SERVICE_URL + "/");
			StringEntity input;
				input = new StringEntity(xmlMapper.writeValueAsString(location));
			postRequest.addHeader("content-type", "application/xml");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			
			success = response.getStatusLine().getStatusCode() == 200;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public boolean delete(int id) {
		boolean success = false;
		try {
			
			HttpDelete postRequest = new HttpDelete(REST_SERVICE_URL + "/" + id);
			HttpResponse response = httpClient.execute(postRequest);
			
			success = response.getStatusLine().getStatusCode() == 200;

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return success;
	}

}
