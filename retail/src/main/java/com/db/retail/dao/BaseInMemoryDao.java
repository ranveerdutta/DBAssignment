package com.db.retail.dao;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Abstract class having DB operations for in-memory database
 * @author ranveer
 *
 * @param <T>
 */
public abstract class BaseInMemoryDao<T> implements IBaseDao<T>{
	
	private Set<T> inMemoryObjects;

	{
		if(null == inMemoryObjects) {
			inMemoryObjects = new LinkedHashSet<>();
		}
	}
	
	public void addEntity(T t) {
		inMemoryObjects.add(t);
	}
	
	public Set<T> getAll() {
		return inMemoryObjects;
	}
	
	public void cleanAll() {
		inMemoryObjects.clear();
	}
	
	
}
