/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel del Castillo
 */
@Stateless
public class RequestPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(RequestPersistence.class.getName());
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;
    
    public RequestEntity create(RequestEntity request)
    {
        LOGGER.log(Level.INFO, "Creating a new request.");
        em.persist(request);
        LOGGER.log(Level.INFO, "Exiting the creation of the request.");
        return request;
    }
    
    public List<RequestEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consulting all requests.");
        TypedQuery query = em.createQuery("select u from RequestEntity u", RequestEntity.class);
        return query.getResultList();
    }
    
    public RequestEntity find(Long requestId)
    {
        LOGGER.log(Level.INFO, "Consulting editorial con id = " + requestId, requestId);
        return em.find(RequestEntity.class, requestId);
    }
    
    public RequestEntity update(RequestEntity requestEntity)
    {
        LOGGER.log(Level.INFO, "Updating request with id = " + requestEntity.getId(), requestEntity.getId());
        LOGGER.log(Level.INFO, "Exiting the update of the request with id = " + requestEntity.getId(), requestEntity.getId());
        return em.merge(requestEntity);
    } 
    
    public void delete(Long requestId)
    {
        LOGGER.log(Level.INFO, "Deleting request with id = " + requestId, requestId);
        RequestEntity entity = em.find(RequestEntity.class, requestId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Exiting the deletion of the request with id = " + requestId, requestId);
    }
}
