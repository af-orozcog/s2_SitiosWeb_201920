/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.IterationEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andrés Felipe Orozco Gonzalez
 */
@Stateless
public class IterationPersistence {
    @PersistenceContext(unitName = "sitiosweb")
    protected EntityManager em;
    
    public IterationEntity create (IterationEntity iteration){
        em.persist(iteration);
        
        return iteration;
    }
}
