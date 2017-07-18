package com.db.retail.dao;

import java.util.List;

public interface IBaseDao<T> {

	void addEntity(T t);
	
	List<T> getAll();
	
	void cleanAll();
}
