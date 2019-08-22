/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.HardwareEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
