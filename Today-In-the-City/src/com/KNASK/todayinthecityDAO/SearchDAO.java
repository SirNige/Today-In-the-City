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

import com.KNASK.todayinthecitymodel.Search;
import com.KNASK.todayinthecitymodel.SearchResult;
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

public class SearchDAO implements ISearchDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.25.21.42/rest/search";
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();
	
	private XmlFactory f = new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl());
	private JacksonXmlModule module = new JacksonXmlModule();
	private XmlMapper xmlMapper = new XmlMapper(f, module);
	
	public SearchDAO() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
		dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
		xmlMapper.setDateFormat(dateFormat);
		xmlMapper.setSerializationInclusion(Include.NON_NULL);
	}

	@Override
	public List<SearchResult> search(Search search) {
		List<SearchResult> searchList = new ArrayList<SearchResult>();
		try {
			HttpPost postRequest = new HttpPost(REST_SERVICE_URL);
			StringEntity input = new StringEntity(xmlMapper.writeValueAsString(search));
			postRequest.setHeader("Content-Type","application/xml");
			postRequest.setEntity(input);
			
			HttpResponse response = httpClient.execute(postRequest);
			
			//System.out.println(EntityUtils.toString(response.getEntity()));

			searchList = xmlMapper.readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<SearchResult>>() {});
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return searchList;
	    
	}

}
