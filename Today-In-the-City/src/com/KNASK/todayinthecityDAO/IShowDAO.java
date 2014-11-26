package com.KNASK.todayinthecityDAO;
import java.util.List;

import com.KNASK.todayinthecitymodel.Band;
import com.KNASK.todayinthecitymodel.Show;

public interface IShowDAO {
	public int create(Show show);
	public Show get(int id);
	public List<Show> getList(int offset, int limit);
	public boolean update(Show show);
	public boolean delete(int id);
	List<Band> getBands(int id);
}