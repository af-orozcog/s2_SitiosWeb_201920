/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Andres Martinez Silva 
 */
@Stateless
public class ProviderPersistence {
    
    @PersistenceContext(unitName = "sitiosweb")
    protected EntityManager em;

    
    public ProviderEntity create (ProviderEntity provider){
        //throw new java.lang.UnsupportedOperationException("Not supported yet.");
        em.persist(provider);

        return provider;
    }
}
