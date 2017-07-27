package com.db.retail.dao;

import java.util.Set;

/**
 * Interface representing the basic data base layer operation
 * @author Ranveer
 *
 * @param <Class name representing the entity>
 */
public interface IBaseDao<T> {

	/**
	 * Saves the entity object in database if not present or overrides
	 * the existing entity if already present.
	 * @param t
	 */
	T saveNewEntityAndReturnOldEntityIfAny(T t);
	
	/**
	 * Returns all the entities of type T from the database
	 * @return Set<T>
	 */
	Set<T> getAll();
	
	/**
	 * Removes all the entities of type T from the database
	 */
	void cleanAll();
}
