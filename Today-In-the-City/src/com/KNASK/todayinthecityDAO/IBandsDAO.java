package com.KNASK.todayinthecityDAO;
import java.util.List;

import com.KNASK.todayinthecitymodel.Band;

public interface IBandsDAO {
	public int create(Band band);
	public Band get(int id);
	public List<Band> getList(int offset, int limit);
	public boolean update(Band band);
	public boolean delete(int id);
	
}
