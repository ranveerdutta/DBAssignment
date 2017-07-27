package com.db.retail.utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collection to hold unique elements in thread safe way.
 * @author Ranveer
 *
 * @param <K>
 */
public class ConcurrentHashSet<K>{
	
	private Map<K, K> map;
	
	public ConcurrentHashSet() {
		map = new ConcurrentHashMap<>();
	}
	
	/**
	 * Inserts  the element k into the Set if not present and returns null
	 * Replaces the element k in the Set if already present and returns the previous version of the k
	 * @param k
	 * @return K
	 */
	public K insertOrReplace(K k) {
		return map.put(k, k);
	}
	
	/**
	 * Returns all the elements from the Set
	 * @return Set<K>
	 */
	public Set<K> getAll(){
		return map.keySet();
	}
	
	/**
	 * Removes all the elements from the Set
	 */
	public void cleanAll() {
		map.clear();
	}
	
}
