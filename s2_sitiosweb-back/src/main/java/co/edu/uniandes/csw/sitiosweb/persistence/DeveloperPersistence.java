/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.DeveloperEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolás Abondano nf.abondano 201812467
 */
@Stateless
public class DeveloperPersistence {
    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public DeveloperEntity create (DeveloperEntity developer){
        LOGGER.log(Level.INFO, "Creando un desarrollador nuevo");
        em.persist(developer);
        
        LOGGER.log(Level.INFO, "Desarrollador creado");
        return developer;
    }
    
    
    public List<DeveloperEntity> findAll() {
       LOGGER.log(Level.INFO, "Consultando todos los desarrolladores");
        Query q = em.createQuery("select u from DeveloperEntity u");
        return q.getResultList();
    }
    
        public DeveloperEntity find(Long developersId) {
        LOGGER.log(Level.INFO, "Consultando el desarrollador con id={0}", developersId);

        return em.find(DeveloperEntity.class, developersId);
    }
        
        public DeveloperEntity update(DeveloperEntity developerEntity) {
        LOGGER.log(Level.INFO, "Actualizando el desarrollador con id={0}", developerEntity.getId());
        
        return em.merge(developerEntity);
    }
        
        public void delete(Long developersId) {
        LOGGER.log(Level.INFO, "Borrando el desarrollador con id={0}", developersId);
        
        DeveloperEntity developerEntity = em.find(DeveloperEntity.class, developersId);
        em.remove(developerEntity);
    }
        
        public DeveloperEntity findByLogin(String login) {
        LOGGER.log(Level.INFO, "Consultando desarrollador por login ", login);
        TypedQuery query = em.createQuery("Select e From DeveloperEntity e where e.login = :login", DeveloperEntity.class);
        query = query.setParameter("login", login);
        List<DeveloperEntity> sameLogin = query.getResultList();
        DeveloperEntity result;
        if (sameLogin == null) {
            result = null;
        } else if (sameLogin.isEmpty()) {
            result = null;
        } else {
            result = sameLogin.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar desarrollador por login ", login);
        return result;
    }
}
