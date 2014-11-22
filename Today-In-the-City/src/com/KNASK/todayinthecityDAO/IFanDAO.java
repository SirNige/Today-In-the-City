package com.KNASK.todayinthecityDAO;
import com.KNASK.todayinthecitymodel.Fan;
import java.util.List;

public interface IFanDAO {
	public int create(Fan fan);
	public Fan get(int id);
	public boolean update(Fan fan);
	public boolean delete(int id);
}