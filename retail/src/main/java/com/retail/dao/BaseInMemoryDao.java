package com.retail.dao;

import java.util.LinkedList;
import java.util.List;

public abstract class BaseInMemoryDao<T> {
	
	private List<T> inMemoryObjects;

	{
		if(null == inMemoryObjects) {
			inMemoryObjects = new LinkedList<>();
		}
	}
	
	public void addEntity(T t) {
		inMemoryObjects.add(t);
	}
	
	public List<T> getAll() {
		return inMemoryObjects;
	}
	
	
}
