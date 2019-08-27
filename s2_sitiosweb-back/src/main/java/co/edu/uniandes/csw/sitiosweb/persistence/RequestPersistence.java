/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniel del Castillo
 */
@Stateless
public class RequestPersistence 
{
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;
    
    public RequestEntity create(RequestEntity request)
    {
        em.persist(request);
        return request;
    }
}
