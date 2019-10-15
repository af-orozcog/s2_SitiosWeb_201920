/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Requester->Request.
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequesterRequestLogic 
{
    // Constants
    
    /**
     * The RequesterRequestLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RequesterRequestLogic.class.getName());
    
    /**
     * The request's persistence.
     */
    @Inject
    private RequestLogic requestPersistence;
    
    /**
     * The requester's persistence.
     */
    @Inject
    private RequesterLogic requesterPersistence;
    
    // Methods
    
    /**
     * Adds a given request to a given requester by setting the request's requester by setting the request's requester to the one given.
     * @param requesterId The id of the request that will be asociated to the given requester.
     * @param requestId The id of the requester that will be asociated to the given request.
     * @return The request entity with the added requester.
     */
    public RequestEntity addRequest(Long requesterId, Long requestId)
    {
        LOGGER.log(Level.INFO, "Adding the request with id = {0} to the requester with id = {1}.", new Object[] {requestId, requesterId});
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requesterId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        requestEntity.setRequester(requesterEntity);
        LOGGER.log(Level.INFO, "Exiting the addition the request with id = {0} to the requester with id = {1}.", new Object[] {requestId, requesterId});
        return requestEntity;
    }
    
    /**
     * @param requesterId The id of the requester whose requests wil be retrieved.
     * @return The list of request entities of the given requester.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * If there isn't a requester with the given id.
     */
    public List<RequestEntity> getRequests(Long requesterId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the requests of the requester with id = {0}.", requesterId);
        RequesterEntity requester = requesterPersistence.getRequester(requesterId);
        if(requester == null)
            throw new BusinessLogicException("No hay solicitador con ese id.");
        LOGGER.log(Level.INFO, "Exiting the acquirement the requests of the requester with id = {0}.", requesterId);
        return requester.getRequests();
    }
    
    /**
     * @param requestId The id of the request asociated with the given requester.
     * @param requesterId The id of the requester that has the given request.
     * @return The request asociated with the given requester.
     * @throws BusinessLogicException If the requester or request doesn't exist
     * or if the given request isn't asociated with the given requester.
     */
    public RequestEntity getRequest(Long requesterId, Long requestId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the request with id = {0} of the requester with id = {1}.", new Object[] {requestId, requesterId});
        List<RequestEntity> requests = getRequests(requesterId); 
        RequestEntity request = requestPersistence.getRequest(requestId);
        if(request == null)
            throw new BusinessLogicException("No hay solicitud con ese id.");
        int index = requests.indexOf(request);
        LOGGER.log(Level.INFO, "Exiting the acquirement the request with id = {0} of the requester with id = {1}.", new Object[] {requestId, requesterId});
        if(index >= 0)
            return requests.get(index);
        throw new BusinessLogicException("No hay solicitud asociada a ese solicitador.");
    }
    
    /**
     * Replaces the given requester's id for the ones given.
     * @param requesterId The id of the requester whose requests will be replaced.
     * @param requests The list of requests that will become the requester's new requests.
     * @return The requester's new requests.
     * @throws BusinessLogicException If the requester doesn't exist.
     */
    public List<RequestEntity> replaceRequests(Long requesterId, List<RequestEntity> requests) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Updating the requests of the requester with id = {0}.", requesterId);
        RequesterEntity requester = requesterPersistence.getRequester(requesterId);
        if(requester == null)
            throw new BusinessLogicException("No hay solicitador con ese id.");
        List<RequestEntity> requestList = requestPersistence.getRequests();
        for(RequestEntity request : requestList)
        {
            if(requests.contains(request))
                request.setRequester(requester);
            else if(request.getRequester() != null && request.getRequester().equals(requester))
                request.setRequester(null);
        }
        LOGGER.log(Level.INFO, "Exiting the update of the requests of the requester with id = {0}.", requesterId);
        return requests;
    }
}
