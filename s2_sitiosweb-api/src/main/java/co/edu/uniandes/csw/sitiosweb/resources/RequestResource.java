/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.resources;

import co.edu.uniandes.csw.sitiosweb.dtos.RequestDTO;
import co.edu.uniandes.csw.sitiosweb.ejb.RequestLogic;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 * @author Daniel del Castillo A.
 */
@Path("requests")
@Consumes("application/json")
@Produces("application/json")
@RequestScoped
public class RequestResource 
{
    // Attributes
    
    /**
     * The resource's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RequestResource.class.getName());
    
    /**
     * The resource's logic.
     */
    @Inject
    private RequestLogic requestLogic;
    
    // Methods
    
    /**
     * Creates and returns a new request with the given one.
     * @param request The request to create.
     * @return The created request.
     * @throws BusinessLogicException Logic error when the request couldn't be created.
     */
    @POST
    public RequestDTO createRequest(RequestDTO request) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RequestResource: input: {0}.", request);
        RequestDTO requestDTO = new RequestDTO(requestLogic.createRequest(request.toEntity()));
        LOGGER.log(Level.INFO, "RequestResource: output: {0}.", requestDTO);
        return requestDTO;
    }
    
    /**
     * Finds and returns all the requests in the application.
     * @return JSON {@Link RequestDTO} - The list of requests in the database.
     */
    @GET
    public List<RequestDTO> getRequests()
    {
        LOGGER.log(Level.INFO, "RequestResource getRequests: input: void.");
        List<RequestDTO> listRequests = listEntityToDTO(requestLogic.getRequests());
        LOGGER.log(Level.INFO, "RequestResource getRequests: output: {0}.");
        return listRequests;
    }
    
    /**
     * Finds and returns the request with the given id.
     * @param requestId The id of the request.
     * @return JSON {@Link RequestDTO} The request, if it exists.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Logic error that generates when the request can't be found.
     */
    @GET
    @Path("{requestId: \\d+}")
    public RequestDTO getRequest(@PathParam("requestId") Long requestId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "RequestResource getRequest: input: {0}.", requestId);
        RequestEntity requestEntity = requestLogic.getRequest(requestId);
        if(requestEntity == null)
            throw new WebApplicationException("The resource /requests/" + requestId + " doesn't exist.", 404);
        RequestDTO requestDTO = new RequestDTO(requestEntity);
        LOGGER.log(Level.INFO, "RequestResource getRequest: output: {0}.", requestDTO);
        return requestDTO;
    }
    
    /**
     * Finds and updates the request with the given id for the given request.
     * @param requestId The id of the request to update.
     * @param request The request with the new information.
     * @return JSON {@Link RequestDTO} The updated request, if it exists.
     * @throws WebApplicationException {@Link WebApplicationExceptionMapper}
     * Logic error that generates when the request can't be found.
     */
    @PUT
    @Path("{requestId: \\d+}")
    public RequestDTO updateRequest(@PathParam("requestId") Long requestId, RequestDTO request) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "RequestResource updateRequest: input: requestId: {0}, request: {1}.", new Object[]{requestId, request});
        request.setId(requestId);
        if(requestLogic.getRequest(requestId) == null)
            throw new WebApplicationException("The resource /requests/" + requestId + " doesn't exist.", 404);
        RequestDTO requestDTO = new RequestDTO(requestLogic.updateRequest(requestId, request.toEntity()));
        LOGGER.log(Level.INFO, "RequestResource updateRequest: output: {0}.", requestDTO);
        return requestDTO;
    }
    
    /**
     * Finds and deletes the request with the given id.
     * @param requestId The request to delete.
     * @throws BusinessLogicException Logic error when the request couldn't be deleted.
     * @throws WebApplicationException {@Link WebApplicationExceptionMapper}
     * Logic error that generates when the request can't be found. 
     */
    @DELETE
    @Path("{requestId: \\d+}")
    public void deleteRequest(@PathParam("requestId") Long requestId) throws BusinessLogicException, WebApplicationException
    {
        LOGGER.log(Level.INFO, "RequestResource deleteRequest: input: {0}.");
        if(requestLogic.getRequest(requestId) == null)
            throw new WebApplicationException("The resource /requests/" + requestId + " doesn't exist.", 404);
        requestLogic.deleteRequest(requestId);
        LOGGER.log(Level.INFO, "RequestResource deleteRequest: output: void.");
    }
    
    // Auxiliar methods
    
    /**
     * Transforms a list of request entity objects into a list of request DTO objects.
     * @param entityList The list of request entities. 
     * @return The list of request DTOs.
     */
    private List<RequestDTO> listEntityToDTO(List<RequestEntity> entityList)
    {
        List<RequestDTO> list = new ArrayList<>();
        for(RequestEntity entity : entityList)
            list.add(new RequestDTO(entity));
        return list;
    }
}
