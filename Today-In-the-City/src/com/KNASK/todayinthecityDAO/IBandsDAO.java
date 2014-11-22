package com.KNASK.todayinthecityDAO;
import com.KNASK.todayinthecitymodel.Band;
import java.util.List;

public interface IBandsDAO {
	public int create(Band band);
	public Band get(int id);
	public List<Band> getList(int offset, int limit);
	public boolean update(Band band);
	public boolean delete(int id);
	
}
