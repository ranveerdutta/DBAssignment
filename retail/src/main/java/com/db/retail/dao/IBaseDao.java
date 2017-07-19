package com.db.retail.dao;

import java.util.Set;

/**
 * Interface representing the basic data base layer operation
 * @author Ranveer
 *
 * @param <Class name representing the entity>
 */
public interface IBaseDao<T> {

	void addEntity(T t);
	
	Set<T> getAll();
	
	void cleanAll();
}
