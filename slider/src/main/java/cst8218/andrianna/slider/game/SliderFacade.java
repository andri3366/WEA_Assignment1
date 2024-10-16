/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
/**
 * SliderFacade.java
 * 
 * Provides the business logic for the Slider entities 
 * It is an extension of the AbstractFacade class to allow for basic CRUD operations 
 * 
 * It is a stateless session bean, it does not maintain any state between method calls
 * 
 * @author AndriannaWardill
 * Date: October 10, 2024
 */
package cst8218.andrianna.slider.game;

import cst8218.andrianna.slider.entity.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Stateless session bean with a concrete implementation of the AbstractFacade class
 * Uses JPA's EntityManager to handle persistence operations
 */
@Stateless
public class SliderFacade extends AbstractFacade<Slider> {
    // Injects the EntityManger when interacting with the persistence context
    @PersistenceContext(unitName = "SliderPU")
    private EntityManager em;
    /**
     * Returns the EntityManager needed for database operations
     * Gets the EntityManger for the superclass (AbstractFacade)
     * 
     * @return EntityManager instance for the Slider entity
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    /**
     * Construct that passes the Slider entity class to the superclass 
     */
    public SliderFacade() {
        super(Slider.class);
    }
}
