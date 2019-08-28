/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.sitiosweb.persistence;

import co.edu.uniandes.csw.sitiosweb.entities.ProviderEntity;
import co.edu.uniandes.csw.sitiosweb.entities.RequestEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Andres Martinez Silva 
 */
@Stateless
public class ProviderPersistence {
    private static final Logger LOGGER = Logger.getLogger(IterationPersistence.class.getName());

    @PersistenceContext(unitName = "sitioswebPU")
    protected EntityManager em;

    
    public ProviderEntity create (ProviderEntity provider){
        LOGGER.log(Level.INFO, "Creando un proeevedor nuevo");
        em.persist(provider);
        LOGGER.log(Level.INFO, "Proveedor creado");
        return provider;
    }
    
    
    public List<ProviderEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los proveedores");

        TypedQuery query = em.createQuery("select u from ProviderEntity u", ProviderEntity.class);
       
        return query.getResultList();
    }
    
        public ProviderEntity find(Long authorsId) {
        LOGGER.log(Level.INFO, "Consultando el proveedor con id={0}", authorsId);

        return em.find(ProviderEntity.class, authorsId);
    }
        
        public ProviderEntity update(ProviderEntity authorEntity) {
        LOGGER.log(Level.INFO, "Actualizando el proveedor con id={0}", authorEntity.getId());
        return em.merge(authorEntity);
    }
        
            public void delete(Long authorsId) {

        LOGGER.log(Level.INFO, "Borrando el proveedor con id={0}", authorsId);
        ProviderEntity authorEntity = em.find(ProviderEntity.class, authorsId);
        em.remove(authorEntity);
    }
}
