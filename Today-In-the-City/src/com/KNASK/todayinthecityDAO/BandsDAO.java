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

import com.KNASK.todayinthecitymodel.Band;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class BandsDAO implements IBandsDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.25.21.42/rest/band";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();

	@Override
	public int create(Band band) {
		int id = 0;
		try {
			
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(band));
			postRequest.setHeader("Content-Type","application/xml");
			postRequest.setEntity(input);
			
			HttpResponse response = httpClient.execute(postRequest);
			
			id = Integer.parseInt(response.getFirstHeader("id").getValue());
			
			BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
			
		
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return id;
	}

	@Override
	public Band get(int id) {
	    
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + id);
		} catch (MalformedURLException e) {
		}
		
		Band band = null;

	    try {
			band = xmlMapper.readValue(currentURL, Band.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return band;
	}

	@Override
	public List<Band> getList(int offset, int limit) {
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/list/" + offset + "/" + limit);
		} catch (MalformedURLException e) {
		}
		
		List<Band> Band = null;

	    try {
			Band = xmlMapper.readValue(currentURL, new TypeReference<List<Band>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return Band;
	    
	}

	@Override
	public boolean update(Band band) {
		boolean success = false;
		try {
			
			HttpPut postRequest = new HttpPut(REST_SERVICE_URL + "/");
			StringEntity input;
				input = new StringEntity(xmlMapper.writeValueAsString(band));
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
