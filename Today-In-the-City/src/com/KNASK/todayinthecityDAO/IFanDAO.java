package com.KNASK.todayinthecityDAO;

import java.util.List;

import com.KNASK.todayinthecitymodel.Fan;

public interface IFanDAO {
	public int create(Fan fan);
	public Fan get(int id);
	public List<Fan> getList(int offset, int limit);
	public boolean update(Fan fan);
	public boolean delete(int id);
}