/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.CatalogueEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class CataloguePersistence {
    
    @PersistenceContext(unitName = "SitiosWeb")
    protected EntityManager em;
    
    public CatalogueEntity create ( CatalogueEntity catalogue ){
        em.persist(catalogue);
        
        return catalogue;
    }
}
