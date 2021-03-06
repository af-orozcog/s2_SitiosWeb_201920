/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequesterEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequestLogic
{
    // Constants
    
    /**
     * The RequestLogic's logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UnitLogic.class.getName());
    
    // Attributes
    
    /**
     * This dependance allows allocation for database request conection.
     */
    @Inject
    private RequestPersistence persistence;
    
    // Methods
    
    /**
     * Method that creates a request entity through the persistence.
     * BUSINESS LOGIC RULES: 
     *  - Not one parameter can be null 
     *  - Every field should have a non-empty value.
     *  - End and due dates cannot be asigned before today's date.
     *  - The project's budget cannot be negative.
     * @param request Request to create.
     * @return The created request.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public RequestEntity createRequest(RequestEntity request) throws BusinessLogicException
    {
        // Project and requester may be initialized as null and added later due to the logical steps of request creation:
        // 1) Create the request, 2) associate the requester with the request (synchronized process)
        // 3) approve the request 4) create the project and 5) associate the project with the request.
        // Today's date.
        Date today = Calendar.getInstance().getTime();
        if(request.getName() == null || request.getName().isEmpty())
            throw new BusinessLogicException("El nombre del proyecto solicitado está vacío.");
        else if(request.getPurpose() == null || request.getPurpose().isEmpty())
            throw new BusinessLogicException("El propósito del proyecto solicitado está vacío.");
        else if(request.getEndDate() == null)
            throw new BusinessLogicException("No se eligió una fecha de terminación del proyecto.");
        else if(request.getEndDate().before(today))
            throw new BusinessLogicException("La fecha de terminación es anterior a la fecha de hoy.");
        else if(request.getDueDate() == null)
            throw new BusinessLogicException("No se eligió una fecha de entrega del proyecto.");
        else if(request.getDueDate().before(today))
            throw new BusinessLogicException("La fecha de entrega es anterior a la fecha de hoy.");
        else if(request.getBeginDate() == null)
            throw new BusinessLogicException("No se eligió una fecha de comienzo del proyecto.");
        else if(request.getBeginDate().before(today))
            throw new BusinessLogicException("La fecha de comienzo es anterior a la fecha de hoy.");
        else if(request.getBudget() == null)
            throw new BusinessLogicException("El presupuesto del proyecto está vacío.");
        else if(request.getBudget() < 0)
            throw new BusinessLogicException("El presupuesto del proyecto no puede ser negativo.");
        else if(request.getDescription() == null || request.getDescription().isEmpty())
            throw new BusinessLogicException("La descripción del proyecto solicitado está vacía.");
        else if(request.getStatus() == null)
            throw new BusinessLogicException("No se eligió estado para la solicitud.");
        else if(request.getWebCategory() == null)
            throw new BusinessLogicException("No se eligió categoría web para el proyecto.");
        else if(request.getRequestType() == null)
            throw new BusinessLogicException("No se eligió el tipo de la solicitud.");
        LOGGER.log(Level.INFO, "Creating a new logic request.");
        request = persistence.create(request);
        LOGGER.log(Level.INFO, "Exiting the creaton of the logic request.");
        return request;
    }
    
    /**
     * Finds all the requests in the database.
     * @return A list with all the requests.
     */
    public List<RequestEntity> getRequests()
    {
        LOGGER.log(Level.INFO, "Consulting al requests.");
        List<RequestEntity> list = persistence.findAll();
        LOGGER.log(Level.INFO, "Exiting the consult of all requests.");
        return list;
    }
    
    /**
     * Finds a specific request in the database.
     * @param requestId Id of the request to find.
     * @return The specific request. Null if it doesn't exist.
     */
    public RequestEntity getRequest(Long requestId)
    {
        LOGGER.log(Level.INFO, "Consulting request with id = {0}.", requestId);
        RequestEntity requestEntity = persistence.find(requestId);
        if(requestEntity == null)
            LOGGER.log(Level.SEVERE, "The request with id = {0} does not exist.", requestId);
        LOGGER.log(Level.INFO, "Exiting the consult of the request with id = {0}.", requestId);
        return requestEntity;
    }
    
     /**
     * Updates a request in the database.
     * @param requestId  The request's id.
     * @param requestEntity The request to update.
     * @return The updated request.
     */
    public RequestEntity updateRequest(Long requestId, RequestEntity requestEntity)
    {
        LOGGER.log(Level.INFO, "Updating request with id = {0}.", requestId);
        RequestEntity newRequestEntity = persistence.update(requestEntity);
        LOGGER.log(Level.INFO, "Exiting the update of the request with id = {0}.", requestId);
        return newRequestEntity;
    }
    
    /**
     * Deletes the request with the given id.
     * BUSINESS LOGIC RULES:
     *  - A request with assigned requesters may not be deleted (must be deleted from the requester's
     *    side first, through the .
     * @param requestId The request's id.
     * @throws co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException
     */
    public void deleteRequest(Long requestId) throws BusinessLogicException
    {
        // The logical process through a request deletion (and thus, project deletion) is the following:
        // 1) a deletion request is approved, 2) the project is deleted (with it's all its dependencies
        // including the request, which requires the requesters to be removed first), 4) the project is deleted.
        LOGGER.log(Level.INFO, "Deleting request with id = {0}.", requestId);
        RequesterEntity requester = getRequest(requestId).getRequester();
        if(requester != null)
            throw new BusinessLogicException("No se puede borrar el request con id = " + requestId + ".");
        persistence.delete(requestId);
        LOGGER.log(Level.INFO, "Exiting the deletion of the request with id = {0}.", requestId);
    }
}
