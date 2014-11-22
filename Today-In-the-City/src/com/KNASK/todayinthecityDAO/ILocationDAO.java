package com.KNASK.todayinthecityDAO;
import com.KNASK.todayinthecitymodel.Location;
import java.util.List;

public interface ILocationDAO {
	public int create(Location location);
	public Location get(int id);
	public boolean update(Location location);
	public boolean delete(int id);
}
