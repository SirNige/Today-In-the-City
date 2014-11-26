package com.KNASK.todayinthecityDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Show;
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ShowDAO implements IShowDAO {
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.25.21.42/rest/show";
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();
	
	private XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private JacksonXmlModule module = new JacksonXmlModule();
	private XmlMapper xmlMapper = new XmlMapper(f, module);
	
	public ShowDAO() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
		dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
		xmlMapper.setDateFormat(dateFormat);
		xmlMapper.setSerializationInclusion(Include.NON_NULL);
	}

	@Override
	public int create(Show show) {
		int id = 0;
		try {
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(show));
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
	public Show get(int id) {
	    
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + id);
		} catch (MalformedURLException e) {
		}
		
		Show show = null;

	    try {
			show = xmlMapper.readValue(currentURL, Show.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return show;
	}

	@Override
	public List<Show> getList(int offset, int limit) {
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/list/" + offset + "/" + limit);
		} catch (MalformedURLException e) {
		}
		
		List<Show> show = null;

	    try {
			show = xmlMapper.readValue(currentURL, new TypeReference<List<Show>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return show;
	    
	}
	
	@Override
	public List<Band> getBands(int id) {
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + id + "/bands");
		} catch (MalformedURLException e) {
		}
		
		List<Band> band = null;

	    try {
			band = xmlMapper.readValue(currentURL, new TypeReference<List<Band>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return band;
	}

	@Override
	public boolean update(Show show) {
		boolean success = false;
		try {
			
			HttpPut postRequest = new HttpPut(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(show));
			postRequest.addHeader("Content-Type", "application/xml");
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
