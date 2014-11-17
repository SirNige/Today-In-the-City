package com.KNASK.todayinthecityDAO;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Show;

public class ShowDAO implements IShowDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.06.19.28/rest/show/";
	private static final Client client = ClientBuilder.newClient();

	@Override
	public int create(Show show) {
		Response response = client.target(REST_SERVICE_URL)
				.request()
				.post(Entity.entity(show, MediaType.APPLICATION_XML), Response.class);
		return Integer.parseInt(response.getHeaderString("id"));
	}

	@Override
	public Show get(int id) {
		return client.target(REST_SERVICE_URL)
				.path(Integer.toString(id))
				.request()
				.get(Show.class);
	}

	@Override
	public List<Show> getList(int offset, int limit) {
		return client.target(REST_SERVICE_URL).path("list/" + offset + "/" + limit).request().get(new GenericType<List<Show>>(){});
	}
	
	@Override
	public List<Band> getBands(Show show) {
		System.out.println(REST_SERVICE_URL + Integer.toString(show.getShowID()) + "/bands");
		return client.target(REST_SERVICE_URL).path(Integer.toString(show.getShowID()) + "/bands").request().get(new GenericType<List<Band>>(){});
	}

	@Override
	public boolean update(Show show) {
		Response response = client.target(REST_SERVICE_URL)
				.path(Integer.toString(show.getShowID()))
				.request()
				.put(Entity.entity(show, MediaType.APPLICATION_XML), Response.class);
		
		System.out.println(Integer.toString(show.getShowID()));
		
		return response.getStatus() == 201;
	}

	@Override
	public boolean delete(Show show) {
		Response response = client.target(REST_SERVICE_URL)
				.path(Integer.toString(show.getShowID()))
				.request()
				.delete();
		return response.getStatus() == 201;
	}

}
