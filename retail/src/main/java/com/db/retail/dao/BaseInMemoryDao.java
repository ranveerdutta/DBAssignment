package com.db.retail.dao;

import java.util.Set;

import com.db.retail.utils.ConcurrentHashSet;

/**
 * Abstract class having DB operations for in-memory database
 * @author ranveer
 *
 * @param <T>
 */
public abstract class BaseInMemoryDao<T> implements IBaseDao<T>{
	
	private ConcurrentHashSet<T> inMemoryObjects;

	{
		if(null == inMemoryObjects) {
			inMemoryObjects = new ConcurrentHashSet<>();
		}
	}
	
	@Override
	public T saveNewEntityAndReturnOldEntityIfAny(T t) {
		return inMemoryObjects.insertOrReplace(t);
		
	}
	
	@Override
	public Set<T> getAll() {
		return inMemoryObjects.getAll();
	}
	
	@Override
	public void cleanAll() {
		inMemoryObjects.cleanAll();
	}
	
	
}
