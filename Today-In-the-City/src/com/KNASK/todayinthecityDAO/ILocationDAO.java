package com.KNASK.todayinthecityDAO;
import java.util.List;

import com.KNASK.todayinthecitymodel.Location;

public interface ILocationDAO {
	public int create(Location location);
	public Location get(int id);
	public boolean update(Location location);
	public boolean delete(int id);
}
