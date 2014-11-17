package com.KNASK.todayinthecityDAO;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.KNASK.todayinthecitymodel.Location;

public class LocationDAO implements ILocationDAO {
	
	private static final String REST_SERVICE_URL = "http://rnguy.no-ip.biz:8080/com.KNASK.TodayInTheCity.11.05.18.46/rest";
	private static final Client client = ClientBuilder.newClient();

	@Override
	public int create(Location location) {
		Response response = client.target(REST_SERVICE_URL)
				.request()
				.post(Entity.entity(location, MediaType.APPLICATION_XML), Response.class);
		return Integer.parseInt(response.getHeaderString("id"));
	}

	@Override
	public Location get(int id) {
		return client.target(REST_SERVICE_URL).path(Integer.toString(id)).request().get(new GenericType<Location>(){});
	}

	@Override
	public List<Location> getList(int offset, int limit) {
		return client.target(REST_SERVICE_URL).path("/list/" + offset + "/" + limit).request().get(new GenericType<List<Location>>(){});
	}

	@Override
	public boolean update(Location location) {
		Response response = client.target(REST_SERVICE_URL)
				.path(Integer.toString(location.getLocatonID()))
				.request()
				.put(Entity.entity(location, MediaType.APPLICATION_XML), Response.class);
		
		return response.getStatus() == 201;
	}

	@Override
	public boolean delete(int id) {
		Response response = client.target(REST_SERVICE_URL)
				.path(Integer.toString(id))
				.request()
				.delete();
		return response.getStatus() == 201;
	}

}
