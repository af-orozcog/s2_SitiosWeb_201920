/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.ejb;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import co.edu.uniandes.csw.sitiosweb.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.sitiosweb.persistence.RequestPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author Daniel del Castillo A.
 */
@Stateless
public class RequestLogic 
{
    // Attributes
    
    @Inject
    private RequestPersistence persistence;
    
    // Methods
    
    /**
     * Method that creates a request entity through the persistence.
     * @param request Request to create.
     * @return The created request.
     */
    public RequestEntity createRequest(RequestEntity request) throws BusinessLogicException
    {
        if(request.getName() == null)
            throw new BusinessLogicException("El nombre del proyecto solicitado está vacío.");
        request = persistence.create(request);
        return request;
    }
    
    
}
