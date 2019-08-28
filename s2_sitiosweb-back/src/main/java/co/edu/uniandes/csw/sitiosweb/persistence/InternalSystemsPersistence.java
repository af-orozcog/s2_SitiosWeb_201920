/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.InternalSystemsEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author s.santosb
 */
@Stateless
public class InternalSystemsPersistence {
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    public InternalSystemsEntity create(InternalSystemsEntity internal){
        em.persist(internal);
        return internal;
    }
    
    public List<InternalSystemsEntity> findAll() {
        TypedQuery query = em.createQuery("select u from InternalSystemsEntity u", InternalSystemsEntity.class);
        return query.getResultList();
    }
    
    public InternalSystemsEntity find(Long internalId) {
        return em.find(InternalSystemsEntity.class, internalId);
    }
    
    public InternalSystemsEntity update(InternalSystemsEntity internal) {
        return em.merge(internal);
    }
    
    public void delete(Long internalId) {
        InternalSystemsEntity entity = em.find(InternalSystemsEntity.class, internalId);
        em.remove(entity);
    }

}
