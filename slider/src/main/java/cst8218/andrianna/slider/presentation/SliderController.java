/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * SliderController.java
 * 
 * RESTful web service controller for CRUD operations
 * Uses the SliderFacade EJB to interact with the database
 * 
 * @author AndriannaWardill
 * Date: October 10, 2024
 */
package cst8218.andrianna.slider.presentation;

import cst8218.andrianna.slider.game.SliderFacade;
import cst8218.andrianna.slider.entity.Slider;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * Uses RESTful endpoints to manage the Slider entities
 * Retrieves a slider by the Id to allow for many different operations
 */
//@Entity
@Path("sliders")
public class SliderController {

    @EJB //injecting the EJB for handling sliders
    private SliderFacade sliderFacade;
    
    /**
     * GET /sliders
     * Retrieves list of al slider from the database
     * 
     * @return list of SLider objects
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Slider> getSliders() {
        return sliderFacade.findAll(); // calls facade to find all sliders
    }
    /**
     * GET /sliders/{id}
     * Retrieves a slider by its Id
     * 
     * @param id Id of the slider
     * @return the slider if found, 404 error if not found
     */
    @GET
    @Path("{id}") // path parameter
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getSlider(@PathParam("id") Long id) {
        Slider slider = sliderFacade.find(id); // finds the slider Id
        if (slider == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // returns 404
        }
        return Response.ok(slider).build(); // return 200
    }
    /**
     * POST /slider
     * Creates a new slider and stores it
     * 
     * @param slider slider object to create
     * @return 201 created response with the created slider
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createSlider(Slider slider) {
        sliderFacade.create(slider); // calls facade to create the new slider
        return Response.status(Response.Status.CREATED).entity(slider).build();// returns 201 for created slider
    }
    /**
     * PUT /slider/{id}
     * Updates existing sliders
     * 
     * @param id id of the slider
     * @param slider updated slider object
     * @return 200 OK response with updated slider, or 404 if not found
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response editSlider(@PathParam("id") Long id, Slider slider) {
        Slider existingSlider = sliderFacade.find(id); //finds the slider using id
        if (existingSlider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        slider.setId(id); // sets id of the updated slider (perserves it) to the existing one
        sliderFacade.edit(slider); // calls facade to update the slider
        return Response.ok(slider).build(); // builds the new slider with 200 response
    }
    /**
     * DELETE /slider/{id}
     * Deletes a slider by the id
     * 
     * @param id id of slider
     * @return 204 no content response or 404 not found
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteSlider(@PathParam("id") Long id) {
        Slider existingSlider = sliderFacade.find(id); // find slider by id
        if (existingSlider == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // return 404 response
        }
        sliderFacade.remove(existingSlider); // calls facade to delete the slider
        return Response.status(Response.Status.NO_CONTENT).build(); // return 204 response
    }
    
}
