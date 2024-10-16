/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 * SliderFacade.java
 * RESTful web service operations for managing the slider entities
 * Provides standard CRUD operation and retrieve Slider objects using HTTP methods
 */
package service;

import cst8218.andrianna.slider.entity.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Stateless
@Path("cst8218.andrianna.slider.entity.slider")
public class SliderFacadeREST extends AbstractFacade<Slider> {
    @PersistenceContext(unitName = "SliderPU")
    private EntityManager em;
    /**
     * Override method from Abstract facade to get the EntityManager
     * @return EntityManager instance for the slider entity
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    } 
    /**
     * Constructor for passing the slider class to abstract face 
     */
    public SliderFacadeREST() {
        super(Slider.class);
    }
    /**
     * POST /slider
     * Creates a new slider or updates an existing one
     * 
     * @param entity slider object
     * @param uriInfo context object to retrieve URI
     * @return HTTP response for success or failure of operations
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPost(Slider entity, @Context UriInfo uriInfo) {
        if (entity.getId() != null) {
            // checks if the ID exists
            Slider existingSlider = super.find(entity.getId());
            if (existingSlider != null) {
                // update the existing slider
                existingSlider.update(entity);
                super.edit(existingSlider);
                return Response.status(Response.Status.OK).entity(existingSlider).build();
            } else {
                // returns bad request if no id is found
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Slider with ID " + entity.getId() + " does not exist.")
                        .build();
            }
        } else {
            // creates a new Slider if Id is null
            super.create(entity);
            URI location = URI.create(uriInfo.getRequestUri().getPath() + "/" + entity.getId());
            return Response.created(location).entity(entity).build();
        }
    }
    /**
     * PUT /slider/{id}
     * Updates a slider if it exists or creates a new one
     * 
     * @param id Id of slider to update or create
     * @param entity Slider object
     * @return HTTP response of success or failure of operations
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editOrCreate(@PathParam("id") Long id, Slider entity) {
        // validates that id matchces the id in the url
        if (entity.getId() == null || !entity.getId().equals(id)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID in the request body must match the ID in the URL.")
                    .build();
        }

        Slider existingSlider = super.find(id); // finds the id
        if (existingSlider == null) {
            // creates a slider if it does not exist
            entity.setId(id);
            super.create(entity);
            //URI location = URI.create(uriInfo.getRequestUri().getPath());
            return Response.status(Response.Status.OK).entity(entity).build();
        } else {
            // updates the existing slider
            existingSlider.update(entity);
            super.edit(existingSlider);
            return Response.status(Response.Status.OK).entity(existingSlider).build();
        }
    }
    /**
     * DELETE /slider/{id}
     * Deletes a slider by Id
     * 
     * @param id Id of the slider
     * @return HTTP response of success or failure of the operation
     */
    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") Long id) {
        Slider slider = super.find(id);
        if (slider == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with ID " + id + " not found.")
                    .build(); // 404 not found
        }
        super.remove(slider);
        return Response.noContent().build(); // 204 no content
    }
    /**
     * GET /slider/{id}
     * retrieves all sliders by id
     * 
     * @param id Id of the slider
     * @return HTTP response of operations
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id) {
        Slider slider = super.find(id);
        if (slider == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Slider with ID " + id + " not found.")
                    .build(); // 404 not found
        }
        return Response.ok(slider).build(); // 200 OK
    }
    /**
     * GET /slider
     * Retrieves all sliders
     * 
     * @return List of all sliders
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Slider> findAll() {
        return super.findAll();
    }
    /**
     * GET /slider/{from}/{to}
     * Retrieves a range of slider between specified indices
     * 
     * @param from start index
     * @param to end index
     * @return list of all sliders in the range
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Slider> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    /**
     * GET /slider/count
     * Returns total count of sliders
     * 
     * @return String with total count of sliders
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
}
