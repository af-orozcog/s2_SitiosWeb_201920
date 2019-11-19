/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.ProjectEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Class tha implements the conection with the persistence for the
 * entity relationship Project->Request.
 * @author Daniel del Castillo A.
 */
@Stateless
public class ProjectRequestLogic 
{
    // Constants
    
    /**
     * The ProjectRequestLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ProjectRequestLogic.class.getName());
    
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
     * Adds a given request to a given project by setting the request's project to the one given.
     * @param requestId The id of the request that will be asociated to the given project.
     * @param projectId The id of the project that will be asociated to the given request.
     * @return The request entity with the added project.
     */
    public RequestEntity addRequest(Long projectId, Long requestId)
    {
        LOGGER.log(Level.INFO, "Adding the request with id = {0} to the project with id = {1}.", new Object[] {requestId, projectId});
        ProjectEntity projectEntity = projectPersistence.getProject(projectId);
        RequestEntity requestEntity = requestPersistence.getRequest(requestId);
        requestEntity.setProject(projectEntity);
        LOGGER.log(Level.INFO, "Exiting the addition the request with id = {0} to the project with id = {1}.", new Object[] {requestId, projectId});
        return requestEntity;
    }
    
    /**
     * @param projectId The id of the project whose requests will be retrieved.
     * @return The list of request entities of the given project.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException 
     * If there isn't a project with the given id.
     */
    public List<RequestEntity> getRequests(Long projectId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the requests of the project with id = {0}.", projectId);
        ProjectEntity project = projectPersistence.getProject(projectId);
        if(project == null)
            throw new BusinessLogicException("No hay proyecto con ese id.");
        LOGGER.log(Level.INFO, "Exiting the acquirement the requests of the project with id = {0}.", projectId);
        return project.getRequests();
    }
    
    /**
     * @param requestId The id of the request asociated with the given project.
     * @param projectId The id of the project that has the given request.
     * @return The request asociated with the given project.
     * @throws BusinessLogicException If the project or request doesn't exist
     * or if the given request isn't asociated with the given project.
     */
    public RequestEntity getRequest(Long projectId, Long requestId) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Getting the request with id = {0} of the project with id = {1}.", new Object[] {requestId, projectId});
        List<RequestEntity> requests = getRequests(projectId); 
        RequestEntity request = requestPersistence.getRequest(requestId);
        if(request == null)
            throw new BusinessLogicException("No hay solicitud con ese id.");
        int index = requests.indexOf(request);
        LOGGER.log(Level.INFO, "Exiting the acquirement the request with id = {0} of the project with id = {1}.", new Object[] {requestId, projectId});
        if(index >= 0)
            return requests.get(index);
        throw new BusinessLogicException("No hay solicitud asociada a ese proyecto.");
    }
    
    /**
     * Replaces the given project's id for the ones given.
     * @param projectId The id of the project whose requests will be replaced.
     * @param requests The list of requests that will become the project's new requests.
     * @return The project's new requests.
     * @throws BusinessLogicException If the project doesn't exist.
     */
    public List<RequestEntity> replaceRequests(Long projectId, List<RequestEntity> requests) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Updating the requests of the project with id = {0}.", projectId);
        ProjectEntity project = projectPersistence.getProject(projectId);
        if(project == null)
            throw new BusinessLogicException("No hay proyecto con ese id.");
        List<RequestEntity> requestList = requestPersistence.getRequests();
        for(RequestEntity request : requestList)
        {
            if(requests.contains(request))
                request.setProject(project);
            else if(request.getProject() != null && request.getProject().equals(project))
                request.setProject(null);
        }
        LOGGER.log(Level.INFO, "Exiting the update of the requests of the project with id = {0}.", projectId);
        return requests;
    }
}
