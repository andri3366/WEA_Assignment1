/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/SingletonEjbClass.java to edit this template
 */
/**
 * Represents the game logic for updating the slider entities
 * Implements a Singleton session bean that starts when the application starts
 * allowing for the game to run indefinitely in the background
 * 
 * @author AndriannaWardill
 * Date: OCtober 10, 2024
 */
package cst8218.andrianna.slider.game;

import jakarta.ejb.Singleton;
//import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;
import java.util.List;

import cst8218.andrianna.slider.entity.Slider;

//import jakarta.ejb.LocalBean;

/**
 * Singleton EJB that starts at application startup
 * Runs the game loop, while updating all entities of the Slider
 */
@Singleton
@Startup
//@LocalBean
public class SliderGame {

    // times per second for the game to update the sliders (regular intervals)
    public static final int CHANGE_RATE = 60;
    // injects the SliderFacade for slider to access and manipulate entities
    @EJB
    private SliderFacade sliderFacade;
    /**
     * Go method that starts a background thread to update all sliders
     * Will run indefinitely, using the CHANGE_RATE for periodic updates
     */
    //@PostContruct
    public void go() {
        new Thread(() -> {
            //the game runs indefinitely
            while (true) {
                //update all the slider and save changes to the database
                List<Slider> sliders = sliderFacade.findAll();
                for (Slider slider : sliders) {
                    // calls timeStep emthod to update each slider's state
                    slider.timeStep();
                    // saves the updated sliders to the database
                    sliderFacade.edit(slider);
                }
                //sleep while waiting to process the next frame of the animation
                try {
                    // wake up roughly CHANGE_RATE times per second
                    Thread.sleep((long)(1.0/CHANGE_RATE*1000));
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }) .start(); // starts the game loop thread
}
}
