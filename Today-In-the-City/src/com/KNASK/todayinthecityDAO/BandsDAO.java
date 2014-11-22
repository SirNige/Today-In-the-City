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

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.KNASK.todayinthecitymodel.Band;

public class BandsDAO implements IBandsDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.21.16.55/rest/Band";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);

	@Override
	public int create(Band band) {
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
		    
	        xmlMapper.writeValue(osw, band);
	        
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
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}
