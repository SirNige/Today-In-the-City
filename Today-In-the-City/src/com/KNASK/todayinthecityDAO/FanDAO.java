package com.KNASK.todayinthecityDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.KNASK.todayinthecitymodel.Fan;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class FanDAO implements IFanDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.25.18.56/rest/fan";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();
	
	@Override
	public int create(Fan fan) {
		int id = 0;
		try {
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(fan));
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
	public Fan get(int id) {
	    
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + id);
		} catch (MalformedURLException e) {
		}
		
		Fan fan = null;

	    try {
			fan = xmlMapper.readValue(currentURL, Fan.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return fan;
	}

	@Override
	public boolean update(Fan fan) {
		boolean success = false;
		try {
			
			HttpPut postRequest = new HttpPut(REST_SERVICE_URL + "/");
			StringEntity input;
				input = new StringEntity(xmlMapper.writeValueAsString(fan));
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
