/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Request->Requester.
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequestRequesterLogic 
{
    // Constants
    
    /**
     * The RequestRequesterLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RequestRequesterLogic.class.getName());

    // Attributes
    
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
     * Replaces the requester of a request.
     * @param requestId The id of the request whose requester will be replaced.
     * @param requesterId The id of the requester that will become the new requester.
     * @return The request with the updated requester.
     */
    public RequestEntity replaceRequester(Long requestId, Long requesterId)
    {
        LOGGER.log(Level.INFO, "Replacing the requester of the request with id = {0}.", requestId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requesterId);
        requestEntity.setRequester(requesterEntity);
        LOGGER.log(Level.INFO, "Exiting the replacement of the requester of the request with id = {0}.", requestId);
        return requestEntity;
    }
    
    /**
     * Removes the requester of a given request and deletes this request
     * from the requester's request list.
     * @param requestId The request whose requester will be deleted.
     * @throws BusinessLogicException 
     */
    public void removeRequest(Long requestId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Deleting the requester of the request with id = {0}.", requestId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        if(requestEntity == null)
        {
            LOGGER.log(Level.INFO, "No requester was deleted since the request doesn't exist.");
            throw new BusinessLogicException("No requester was deleted since the request doesn't exit.");
        }
        RequesterEntity requesterEntity = requesterPersistence.getRequester(requestEntity.getRequester().getId());
        if(requesterEntity != null)
        {
            // This will not delete the request from the DB (lazy fetch):
            requestEntity.setRequester(null);
            requesterEntity.getRequests().remove(requestEntity);
        }
        else
        {
            LOGGER.log(Level.INFO, "No requester was removed since there wasn't one.");
            throw new BusinessLogicException("No requester was removed since there wasn't one.");
        }
        LOGGER.log(Level.INFO, "Exiting the deletion of the requester of the request with id = {0}.", requestId);
    }
}
