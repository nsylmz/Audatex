package com.audatex.movie.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface CommonDaoI<T, ID extends Serializable> {
	
	public Class<T> getEntityClass();
	
	public EntityManager getEntityManager();

    public T findById(final ID id);

    public List<T> findAll();

    public List<T> findByExample(final T exampleInstance);
    
    public List<T> findByCriteria(String alias, Order[] orders, Criterion... criterion);

    public List<T> findByCriteria(String alias, int firstResult, int maxResults,
			Order[] orders, Criterion... criterion);

    public List<T> findByCriteria(Order[] orders, Criterion... criterion);

    public List<T> findByCriteria(int firstResult, int maxResults, Order[] orders, Criterion... criterion);
    
    public List<T> findByCriteria(String alias, final Criterion... criterion);
	
	public List<T> findByCriteria(String alias, final int firstResult, final int maxResults, final Criterion... criterion);
    
    public List<T> findByCriteria(final Criterion... criterion);
    
    public List<T> findByCriteria(ProjectionList projections, final Criterion... criterion);

    public List<T> findByNamedQuery(final String queryName, Object... params);

    public List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?> params);

    public int countAll();

    public int countByExample(final T exampleInstance);

    public void merge(final T entity);
    
    public void persist(T entity);

    public void delete(final T entity);
    
    public void flush();

}
