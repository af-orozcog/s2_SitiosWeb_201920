/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
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
public class HardwarePersistence {
    
    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    public HardwareEntity create(HardwareEntity hardware){
        em.persist(hardware);
        return hardware;
    }
    
    public List<HardwareEntity> findAll() {
        TypedQuery query = em.createQuery("select u from HardwareEntity u", HardwareEntity.class);
        return query.getResultList();
    }
    
    public HardwareEntity find(Long hardwaresId) {
        return em.find(HardwareEntity.class, hardwaresId);
    }
    
    public HardwareEntity update(HardwareEntity hardware) {
        return em.merge(hardware);
    }
    
    public void delete(Long hardwaresId) {
        HardwareEntity entity = em.find(HardwareEntity.class, hardwaresId);
        em.remove(entity);
    }



}
