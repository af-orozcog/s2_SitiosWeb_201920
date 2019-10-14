/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Request->Project.
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequestProjectLogic 
{
    // Constants
    
    /**
     * The RequestProjectLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RequestProjectLogic.class.getName());
    
    // Attributes
    
    /**
     * The request's persistence.
     */
    @Inject
    private RequestLogic requestPersistence;
    
    /**
     * The project's persistence.
     */
    @Inject
    private ProjectLogic projectPersistence;
    
    // Methods
    
    /**
     * Replaces the project of a request.
     * @param requestId The id of the request whose project will be replaced.
     * @param projectId The id of the project that will become the new project.
     * @return The request entity with the updated request.
     */
    public RequestEntity replaceProject(Long requestId, Long projectId)
    {
        LOGGER.log(Level.INFO, "Replacing the project of the request with id = {0}.", requestId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        ProjectEntity projectEntity = projectPersistence.getProject(projectId);
        requestEntity.setProject(projectEntity);
        LOGGER.log(Level.INFO, "Exiting the replacement of the project of the request with id = {0}.", requestId);
        return requestEntity;
    }
    
    /**
     * Removes the project of a given request and deletes this request
     * from the project's request list and request table (DB), i.e., the
     * request entity will be deleted.
     * @param requestId The request whose project will be deleted.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     * if the request or request's project doesn't exist.
     */
    public void removeRequest(Long requestId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Deleting the project of the request with id = {0}.", requestId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        if(requestEntity == null)
        {
            LOGGER.log(Level.INFO, "No project was deleted since the request doesn't exist.");
            throw new BusinessLogicException("No project was deleted since the request doesn't exit.");
        }
        ProjectEntity projectEntity = projectPersistence.getProject(requestEntity.getProject().getId());
        if(projectEntity != null)
        {
            // This will also delete the request from the DB:
            requestEntity.setProject(null);
            projectEntity.getRequests().remove(requestEntity);
        }
        LOGGER.log(Level.INFO, "Exiting the deletion of the project of the request with id = {0}.", requestId);
    }
}