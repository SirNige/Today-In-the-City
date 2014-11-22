package com.KNASK.todayinthecityDAO;
import android.os.AsyncTask;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Show;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ShowDAO implements IShowDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.21.16.55/rest/show";
	private static final XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private static final JacksonXmlModule module = new JacksonXmlModule();
	private static final XmlMapper xmlMapper = new XmlMapper(f, module);

	@Override
	public int create(Show show) {
		try {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(show));
			postRequest.addHeader("content-type", "application/xml");
			postRequest.setEntity(input);
			HttpResponse response = httpClient.execute(postRequest);
			
			System.out.println(response.getStatusLine().getStatusCode());
			
			BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return 0;
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
			show = xmlMapper.readValue(currentURL, com.KNASK.todayinthecitymodel.Show.class);
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
	public List<Band> getBands(Show show) {
	    URL currentURL = null;
		try {
			currentURL = new URL(REST_SERVICE_URL + "/" + show.getShowID() + "/bands");
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
		return false;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

}
