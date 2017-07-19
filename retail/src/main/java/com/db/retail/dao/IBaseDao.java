package com.db.retail.dao;

import java.util.Set;

public interface IBaseDao<T> {

	void addEntity(T t);
	
	Set<T> getAll();
	
	void cleanAll();
}
