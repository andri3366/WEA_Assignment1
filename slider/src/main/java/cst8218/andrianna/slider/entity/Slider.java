/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * Represents the Slider object that moves across a grid, changing direction
 * after traveling a certain distance. 
 * The entity includes validation for many constraints and implement for game logic
 * 
 * @author AndriannaWardill
 * Date: October 10, 2024
 */
package cst8218.andrianna.slider.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Slider entity that represents a game slider objects
 * Moves on a 2D grid with properties such as position, size, and movement behaviour
 */
@Entity
@XmlRootElement // allows for xml
@Table(name = "slider")
@NamedQueries({
    @NamedQuery(name = "Slider.findAll", query = "SELECT s FROM Slider s")})
public class Slider implements Serializable {
    
    // constants for slider's behavour and limits
    public static final int INITIAL_SIZE = 50;
    public static final int MAX_DIR_CHANGES = 10;
    public static final int TRAVEL_SPEED = 5;
    public static final int DECREASE_RATE = 1;
    
    // grid size and limit
    public static final int X_LIMIT = 1000;
    public static final int Y_LIMIT = 1000;
    public static final int SIZE_LIMIT = 100;
    public static final int MAX_TRAVEL_LIMIT = 200;
    
    // Serialization
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto generate an ID
    private Long id;
    
    // slider's position and size
    @NotNull
    @Min(0)
    @Max(X_LIMIT) // cosntrained by validation annotations
    private int x;
    
    @NotNull
    @Min(0)
    @Max(Y_LIMIT)
    private int y;
    
    @NotNull
    @Min(1)
    @Max(SIZE_LIMIT)
    private int size = INITIAL_SIZE; // deafualt size of the slider
    
    @NotNull
    @Min(1)
    @Max(MAX_TRAVEL_LIMIT)
    private int maxTravel = INITIAL_SIZE; // max distance before direction change
    
    @NotNull
    private int currentTravel = 0;
    private int dirChangeCount = 0; // counts how many direction changes
    private int mvtDirection = 1; // 1 for right -1 for left
    @XmlElement
    
    // getters and setters for slider properties
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @XmlElement
    public int getX() {
        return x;
    }

    public void setX(int x) {
        // validates x is within the allowed limits
        if ( x >= 0 && x <= X_LIMIT) {
            this.x = x;
        }
    }
    @XmlElement
    public int getY() {
        return y;
    }

    public void setY(int y) {
        // validates y is within the allowed limits
        if ( y >= 0 && y <= Y_LIMIT) {
            this.y = y;
        }
    }
    @XmlElement
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        // validates the size is within defined limits
        if ( size >= 1 && size <= SIZE_LIMIT) {
            this.size = size;
        }
    }
    @XmlElement
    public int getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(int maxTravel) {
        // ensures max travel distance is within the defined limits
        if ( maxTravel >= 1 && maxTravel <= MAX_TRAVEL_LIMIT) {
            this.maxTravel = maxTravel;
        }
    }
    @XmlElement
    public int getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(int currentTravel) {
         this.currentTravel = currentTravel;
    }
    @XmlElement
    public int getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(int mvtDirection) {
        this.mvtDirection = mvtDirection;
    }
    @XmlElement
    public int getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(int dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }
    
    /**
     * Updates the sliders position and behaviour in one time step
     * The slider will move based on direction and speed
     * When it reaches max travel distance it changes directions
     * After a set number of direction changes, the travel distance is reduced
     */
    public void timeStep() {
        if (maxTravel > 0) {
            currentTravel += mvtDirection * TRAVEL_SPEED;
            if (Math.abs(currentTravel) >= maxTravel) {
                // change direction when travel exceeds maxTravel
                mvtDirection = -mvtDirection;
                dirChangeCount++;
                if (dirChangeCount >= MAX_DIR_CHANGES) {
                    // reduce the max travel distance
                    maxTravel -= DECREASE_RATE;
                    dirChangeCount = 0;
                }
            }
        }
    }
    /**
     * Updates the current slider with values of another slider
     * @param newSlider instance for the newSlider values
     */
    public void update(Slider newSlider) {
        if (newSlider.getX() >= 0 && newSlider.getX() <= X_LIMIT) {
            this.x = newSlider.getX();
        }
        if (newSlider.getY() >= 0 && newSlider.getY() <= Y_LIMIT) {
            this.y = newSlider.getY();
        }
        if (newSlider.getSize() >= 1 && newSlider.getSize() <= SIZE_LIMIT) {
            this.size = newSlider.getSize();
        }
        if (newSlider.getMaxTravel() >= 1 && newSlider.getMaxTravel() <= MAX_TRAVEL_LIMIT) {
            this.maxTravel = newSlider.getMaxTravel();
        }
        
        this.currentTravel = newSlider.getCurrentTravel();
        this.mvtDirection = newSlider.getMvtDirection(); 
        this.dirChangeCount = newSlider.getDirChangeCount(); 
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Slider)) {
            return false;
        }
        Slider other = (Slider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.andrianna.slider.entity.Slider[ id=" + id + " ]";
    }
    
}
