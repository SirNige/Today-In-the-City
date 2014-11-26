package com.KNASK.todayinthecityDAO;

import java.util.List;

import com.KNASK.todayinthecitymodel.Search;
import com.KNASK.todayinthecitymodel.SearchResult;

public interface ISearchDAO {
	public List<SearchResult> search(Search search);
	
}
