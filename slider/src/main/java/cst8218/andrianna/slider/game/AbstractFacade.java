/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * AbstractFacade.java
 * 
 * The following class provides a generic CRUD function for an entity manager. 
 * It allows for its subclasses to define the entity class type and methods for interacting
 * with the database.
 * 
 * @author AndriannaWardill
 * Date: October 10, 2024
 */

package cst8218.andrianna.slider.game;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Generic abstract class with basic CRUD operations for the enetities in the database
 * 
 * @param <T> entity type managed
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass; // entity class type
    /**
     * Constructor to initialize class type for the entity
     * @param entityClass 
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    /**
     * Provides an EntityManager for subclasses when called
     * 
     * @return EntityManager instance
     */
    protected abstract EntityManager getEntityManager();
    /**
     * CRUD operation for creating a new entity
     * 
     * @param entity to be created
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    /**
     * CRUD operations for updating entities
     * 
     * @param entity to edit entity
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    /**
     * CRUD operation for deleting an entity
     * 
     * @param entity to remove entity
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    /**
     * Find entities using the id field (primary key)
     * 
     * @param id to remove entity
     * @return entity if found, if not then null
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    /**
     * Retrieves all entities of the managed type from database
     * 
     * @return list of all entities
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        // selects all entities of the given class
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }
    /**
     * Range of entities from the database
     * 
     * @param range array containing the start index and the last (end) index
     * @return list of entities in range
     */
    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        // select all entities of the given class
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        // set the number of results to find (ie. the range)
        q.setMaxResults(range[1] - range[0]);
        // sets the starting point (first index)
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    /**
     * Counts the total number of entities of a manageable type 
     * 
     * @return total count of entities
     */
    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        // the base query entity (the root)
        Root<T> rt = cq.from(entityClass);
        // selects the count
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue(); // convert long to int
    }

}
